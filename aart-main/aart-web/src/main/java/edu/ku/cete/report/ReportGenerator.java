package edu.ku.cete.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.jfree.chart.ui.RectangleInsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.charts.ATSMeterPlot;
import edu.ku.cete.report.charts.SubScoreBarPlot;
import edu.ku.cete.util.FileUtil;

public class ReportGenerator {

	final static Log logger = LogFactory.getLog(ReportGenerator.class);
	   
	@Value("classpath:reports.fop.conf.xml")
	private Resource reportConfigFile;

	@Value("/css/fonts/verdana.ttf")
	private Resource fontsResource;
	
	//@Value("/images/reports/KAPReportLogo.png")
	//@Value("/images/reports/KAPlogo_2017-2018.png")
	@Value("/images/reports/Student_Report_KAPlogo.svg")
	private Resource kapLogoFile;
	
	@Value("/images/reports/KELPA2logo_2017-2018.png")
	private Resource kelpaLogoFile;
	
	@Value("/images/reports/AMPReportLogo.png")
	private Resource ampLogoFile;
	
	@Value("images/dlm_logo_final_registered.jpg")
	private Resource dlmLogoFile;
	
	@Value("images/dlm_logo_final_registered_312x128.png")
	private Resource dlmLogoFile312x128;
	
/*	@Value("/images/reports/KSDEfooterlogo.svg")*/
	@Value("/images/reports/new_KAPFooterLogo.svg")
	private Resource kapFooterLogoFile;
	
	@Value("/images/reports/AMPEEDlogo_48x48.svg")
	private Resource ampFooterLogoFile;
	
	@Value("/images/reports/graph_unusedarea.png")
	private Resource graphUnusedAreaFile;
	
	@Value("/images/reports/icon_exited.png")
	private Resource iconExited;
	
	@Value("/images/reports/icon_incomplete.png")
	private Resource iconIncomplete;
	
	@Value("${print.test.file.path}")
	private String outputBaseDir;
	
	@Value("${kap.subscore.range}")
	private String kapSubScoreRange = "100,250,10";
	
	@Value("${amp.subscore.range}")
	private String ampSubScoreRange = "101,119,2";
	
	protected Random fileNameSeed = new Random();
			
	public Resource getAMPLogo() {
		return ampLogoFile;
	}
	
	public Resource getKELPALogo() {
		return kelpaLogoFile;
	}
	
	public Resource getKAPLogo() {
		return kapLogoFile;
	}
	
	public Resource getDLMLogo() {
		return dlmLogoFile;
	}
	
	public Resource getSmallDLMLogo() {
		return dlmLogoFile312x128;
	}
	
	public String getRootOutputDir() {
		return outputBaseDir;
	}
	
	public FopFactory getFopFactory() throws Exception {
		FopFactory fopFactory = FopFactory.newInstance(reportConfigFile.getFile());
		fopFactory.getImageManager().getCache().clearCache();
		return fopFactory;
	}
	
	protected void generatePdf(File pdfFile, File foFile, TraxSource source) throws Exception {
		generatePdf(pdfFile, foFile, source, null);
	}
	
	protected void generatePdf(File pdfFile, File foFile, TraxSource source, Map<String, ? extends Object> parameters) throws Exception {
		OutputStream out = null;
		try {
			out = new FileOutputStream(pdfFile);
			out = new BufferedOutputStream(out);
	
			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					foFile.getCanonicalPath()));
			
			if (parameters != null) {
				for (Map.Entry<String, ? extends Object> entry : parameters.entrySet()) {
					transformer.setParameter(entry.getKey(), entry.getValue());
				}
			}
	
			FOUserAgent foUserAgent = getFopFactory().newFOUserAgent();
			// Construct fop with desired output format
			Fop fop = getFopFactory().newFop(MimeConstants.MIME_PDF, foUserAgent,
					out);
	
			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());
	
			// Start XSLT transformation and FOP processing
			transformer.transform(source, res);
		} 
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	protected String sanitizeForPath(String stateStudentIdentifier) {
		return stateStudentIdentifier.replaceAll("[^a-zA-Z0-9.-]", "_");
	}
	
	protected File generateMeter(String apCode, List<LevelDescription> levels, Long meterValue, 
				String type, String path, int width, int height) throws IOException {
		
		ATSMeterPlot plot = new ATSMeterPlot(
				new DefaultValueDataset(meterValue));
		plot.setRange(getMeterRange(levels));

		Color segmentColor = null;
		for (int i = 0; i <levels.size(); i++) {
			if (apCode.equalsIgnoreCase("AMP")) {
				if (i == 0) {
					segmentColor = new Color(246, 176, 101);
				} else if (i == 1) {
					segmentColor = new Color(243, 236, 100);
				} else if (i == 2) {
					segmentColor = new Color(167, 207, 82);
				} else {
					segmentColor = new Color(205, 191, 222);
				}
			} else {
				if (i == 0) {
					segmentColor = new Color(236, 107, 56);
				} else if (i == 1) {
					segmentColor = new Color(234, 143, 6);
				} else if (i == 2) {
					segmentColor = new Color(251, 198, 104);
				} else if (i == 3 && levels.size() == 5) {
					segmentColor = new Color(244, 217, 81); // sci
				} else {
					segmentColor = new Color(248, 238, 164);
				}
			}

			plot.addInterval(new MeterInterval("" + (i + 1), new Range(levels.get(i).getLevelLowCutScore(),levels
					.get(i).getLevelHighCutScore()), Color.BLACK,
					new BasicStroke(1f), segmentColor));
		}
		plot.setRangeLabelsVisible(false);
		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(new Color(0, 0, 0, 0));
		chart.removeLegend();
		chart.setPadding(new RectangleInsets(0,0,0,0));
		if (type.equals("student")) {
			plot.setRangeLabelsVisible(true);
			chart.setPadding(new RectangleInsets(0,14,10,0));
		}
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
		Rectangle r = new Rectangle(0, 0, width, height);
		chart.draw(g2, r);
		String fullPath = FileUtil.buildFilePath(path, type + "_meter_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
		String[] splitFullPath = fullPath.split("\\.");
		File f = File.createTempFile(splitFullPath[0], ".svg");
		SVGUtils.writeToSVG(f, g2.getSVGElement());
		return f;
	}
	
	protected Range getMeterRange(List<LevelDescription> levels) {
		Long lower = 100L;
		Long upper = 250L;
		if (!levels.isEmpty()) {
			lower = levels.get(0).getLevelLowCutScore();
			upper = levels.get(levels.size()-1).getLevelHighCutScore();
		}
		Range range = new Range(lower, upper);
		return range;
	}
	
	protected File getLogo(String assessmentProgramCode) throws IOException {
		Resource logo = getKAPLogo();
		if (assessmentProgramCode.equalsIgnoreCase("AMP")) {
			logo = getAMPLogo();
		} else if (assessmentProgramCode.equalsIgnoreCase("KELPA2")) {
			logo = getKELPALogo();
		}
		File logoFile = logo.getFile();
		return logoFile;
	}
	
	protected String getIconPath(String iconName) throws IOException {
		File iconFile = null;
		if(iconName.equalsIgnoreCase("incomplete")){
			iconFile = iconIncomplete.getFile();
		} else if(iconName.equalsIgnoreCase("exited")){
			iconFile = iconExited.getFile();
		}
		return iconFile.toURI().toString();
	}
	
	protected File getFooterLogo(String assessmentProgramCode) throws IOException {
		Resource logo = kapFooterLogoFile;
		if (assessmentProgramCode.equalsIgnoreCase("AMP")) {
			logo = ampFooterLogoFile;
		}
		File logoFile = logo.getFile();
		return logoFile;
	}
	
	protected String getPathForDB(File pdfFile) {
		String path = pdfFile.getPath().replace(getRootOutputDir(), "");
		
		if(!path.startsWith(File.separator)) {
			path = File.separator+path;
		}
		return path;
	}
	
	protected String getPathForDB(String fullFilePath) {
		String path = fullFilePath.replace(getRootOutputDir(), "");
		
		if(!path.startsWith(File.separator)) {
			path = File.separator+path;
		}
		return path;
	}
	
	protected File generateSubScoreChart(final ReportContext reportData, final StudentReport data, final String type, final String path, List<ReportSubscores> allSubScores) throws IOException {
		String fullPath = FileUtil.buildFilePath(path, "_subscore_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
		String[] splitFullPath = fullPath.split("\\."); 
		File f = File.createTempFile(splitFullPath[0], ".svg");
		if(data.getSubscoreBuckets() != null){
			DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();
			List<ReportSubscores> subScores = getSubScores(data, type);
			
			if(allSubScores != null && subScores.size() != allSubScores.size()) {
				boolean exists = false;
				for(ReportSubscores allSubScore: allSubScores) {
					exists = false;
					for(ReportSubscores subScore: subScores) {
						if(allSubScore.getSubScoreReportDisplayName().equals(subScore.getSubScoreReportDisplayName())) {
							exists = true;
							break;
						}
					}
					if(!exists) {
						ReportSubscores missingSubScore = new ReportSubscores();
						missingSubScore.setSubscoreDefinitionName(allSubScore.getSubscoreDefinitionName());
						missingSubScore.setSubScoreReportDisplayName(allSubScore.getSubScoreReportDisplayName());
						missingSubScore.setSubScoreDisplaySequence(allSubScore.getSubScoreDisplaySequence());
						missingSubScore.setSubScoreReportDescription(allSubScore.getSubScoreReportDescription());
						subScores.add(missingSubScore);
					}
				}
				//sort in display order
				Collections.sort(subScores, new Comparator<ReportSubscores>() {
					@Override
					public int compare(ReportSubscores o1, ReportSubscores o2) {
						return o1.getSubScoreDisplaySequence().compareTo(o2.getSubScoreDisplaySequence());
					}
				});
			}
			reportData.setSubScoreChartWidth(443);
			if(reportData.getSubScoreChartHeight() == 0) {
				reportData.setSubScoreChartHeight((15 * subScores.size())+6);
			}
			
			for(ReportSubscores subScore: subScores) {
				if(subScore.getSubscoreScaleScore()!=null && subScore.getSubscoreStandardError()!=null) {
					dataset.add(subScore.getSubscoreScaleScore().doubleValue(), subScore.getSubscoreStandardError().doubleValue(), type,  subScore.getSubScoreReportDisplayName());
				} else {
					dataset.add(0d, 0d, type, subScore.getSubScoreReportDisplayName());
				}
			}
			
			int width = reportData.getSubScoreChartWidth();
			int height = reportData.getSubScoreChartHeight();
			Range range = null;
			double tickUnit = 0;
			if (data.getAssessmentProgramCode().equalsIgnoreCase("AMP")) {
				String rvalues[] = ampSubScoreRange.split(",");
				range = new Range(Double.valueOf(rvalues[0]), Double.valueOf(rvalues[1]));
				tickUnit = Double.valueOf(rvalues[2]);
			} else {
				String rvalues[] = kapSubScoreRange.split(",");
				range = new Range(Double.valueOf(rvalues[0]), Double.valueOf(rvalues[1]));
				tickUnit = Double.valueOf(rvalues[2]);
			}
			boolean showNotRespondedLabel = true;
			SubScoreBarPlot plot = new SubScoreBarPlot(dataset, range, false, true, tickUnit, showNotRespondedLabel);
			
			JFreeChart chart = new JFreeChart(plot);
			chart.setBackgroundPaint(new Color(0,0,0,0));
			chart.removeLegend();
			chart.setPadding(new RectangleInsets(0,0,0,7));
			chart.setBorderVisible(false);
			SVGGraphics2D g2 = new SVGGraphics2D(width, height-2);
			Rectangle r = new Rectangle(0, 0, width, height-2);
			chart.draw(g2, r);
			SVGUtils.writeToSVG(f, g2.getSVGElement());
		}
		return f;
	}
	
	protected File generateSubScoreXAxis(final ReportContext reportData, final StudentReport data, final String path) throws IOException {
		String fullPath = FileUtil.buildFilePath(path, "xaxis_ss_"+(100000 + fileNameSeed.nextInt(900000))+".svg");
		String[] splitFullPath = fullPath.split("\\.");
		File f = File.createTempFile(splitFullPath[0], ".svg");
		int width = reportData.getSubScoreChartWidth();
		int height = 13;
		
		Range range = null;
		double tickUnit = 0;
		if (data.getAssessmentProgramCode().equalsIgnoreCase("AMP")) {
			String rvalues[] = ampSubScoreRange.split(",");
			range = new Range(Double.valueOf(rvalues[0]), Double.valueOf(rvalues[1]));
			tickUnit = Double.valueOf(rvalues[2]);
		} else {
			String rvalues[] = kapSubScoreRange.split(",");
			range = new Range(Double.valueOf(rvalues[0]), Double.valueOf(rvalues[1]));
			tickUnit = Double.valueOf(rvalues[2]);
		}
		SubScoreBarPlot plot = new SubScoreBarPlot(new DefaultStatisticalCategoryDataset(), range, true, false, tickUnit, false);
		
		JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(new Color(0,0,0,0));
		chart.removeLegend();
		chart.setPadding(new RectangleInsets(0,0,0,7));
		chart.setBorderVisible(false);
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
		Rectangle r = new Rectangle(0, 0, width, height);
		chart.draw(g2, r);
		SVGUtils.writeToSVG(f, g2.getSVGElement());
		return f;
	}

	protected List<ReportSubscores> getSubScores(final StudentReport data,
			final String type) {
		List<ReportSubscores> subScores = null;
		if(data.getSubscoreBuckets() != null){
			subScores = new ArrayList<ReportSubscores>(data.getSubscoreBuckets());
			CollectionUtils.filter(subScores, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					ReportSubscores subScore = (ReportSubscores) object;
					if(type.equals("student") && subScore.getOrganizationId() == null) {
						return true;
					} else if(type.equals("school") && data.getAttendanceSchoolId().equals(subScore.getOrganizationId())) {
						return true;
					} else if(type.equals("district") && data.getDistrictId().equals(subScore.getOrganizationId())) {
						return true;
					} else if(type.equals("state") && data.getStateId().equals(subScore.getOrganizationId())) {
						return true;
					}
					return false;
				}
			});
			//sort in display order
			Collections.sort(subScores, new Comparator<ReportSubscores>() {
				@Override
				public int compare(ReportSubscores o1, ReportSubscores o2) {
					return o1.getSubScoreDisplaySequence().compareTo(o2.getSubScoreDisplaySequence());
				}
			});
		}
		
		return subScores;
	}

	public Resource getGraphUnusedAreaFile() {
		return graphUnusedAreaFile;
	}

	public void setGraphUnusedAreaFile(Resource graphUnusedAreaFile) {
		this.graphUnusedAreaFile = graphUnusedAreaFile;
	}
	
	public void transform(Source source, File xslt, Writer out) throws IOException, FOPException, TransformerException, URISyntaxException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(xslt.getCanonicalPath()));
		transformer.transform(source, new StreamResult(out));
	}
}
