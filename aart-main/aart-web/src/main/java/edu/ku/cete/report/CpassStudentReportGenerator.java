package edu.ku.cete.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.web.ExternalStudentReportDTO;

@Component
public class CpassStudentReportGenerator extends ReportGenerator {

	@Value("/images/reports/cPass_logo_registered.svg")
	private Resource cpassLogoNewFile;

	@Value("/templates/xslt/reports/cpassStudentReport.xsl")
	private Resource cpassStudentReportXslFile;
	
	@Autowired
	private AwsS3Service s3;

	public ExternalStudentReportDTO generateReportFile(
			ExternalStudentReportDTO data) throws Exception {
		List<File> percentLevelChartFiles = new ArrayList<File>();
		List<String> mainPercentLevelCharts = new ArrayList<String>();
		File pdfFile = null;
		try {
			String outDir = createStudentDir(data);
			ReportContext reportData = new ReportContext();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			dateFormat.setTimeZone(TimeZone.getTimeZone("US/Central"));
			data.setReportDate(dateFormat.format(new Date()));
			reportData.setData(data);
			reportData.setLogoPath(cpassLogoNewFile.getFile().toURI().toString());

			if(data.getStudentScore()==null) data.setStudentScore(0L);
			if(data.getStandardError()==null) data.setStandardError(new BigDecimal(0));
			if(data.getDistrictAverageScore()==null) data.setDistrictAverageScore(0L);
			if(data.getDistrictStandardError()==null) data.setDistrictStandardError(new BigDecimal(0));
			if(data.getStateAverageScore()==null) data.setStateAverageScore(0L);
			if(data.getStateStandardError()==null) data.setStateStandardError(new BigDecimal(0));
			if(data.getAllStatesAvgScore()==null) data.setAllStatesAvgScore(0L);
			if(data.getAllStateStandardError()==null) data.setAllStateStandardError(new BigDecimal(0));
			
			DefaultStatisticalCategoryDataset result = new DefaultStatisticalCategoryDataset();
			result.add(0, 0, "Series 1", " ");
			result.add(data.getStudentScore().doubleValue(), data.getStandardError(), "Series 1", "My Score");
			result.add(data.getDistrictAverageScore().doubleValue(), data.getDistrictStandardError(), "Series 1", "My District Avg.");
			result.add(data.getStateAverageScore().doubleValue(), data.getStateStandardError(), "Series 1", "My State Avg.");
			result.add(data.getAllStatesAvgScore().doubleValue(), data.getAllStateStandardError(), "Series 1", "Overall Avg.");
			File chart = generateCpassPercentLevelChart(outDir, "Main", result,	data);
			mainPercentLevelCharts.add(chart.toURI().toString());
			reportData.setMainPercentLevelCharts(mainPercentLevelCharts);
			percentLevelChartFiles.add(chart);

			XStream xstream = new XStream();
			xstream.alias("reportDetails", ReportContext.class);
			xstream.aliasField("data", ReportContext.class, "data");
			TraxSource source = new TraxSource(reportData, xstream);
			//build file path/key
			String filePath = FileUtil.buildFilePath(outDir, data.getStateDisplayIdentifier()
					+ "_" + data.getDistrictDisplayIdentifier() + "_"
					+ data.getSchoolDisplayIdentifier() + "_"
					+ data.getContentAreaCode() + "_" + data.getGradeCode()
					+ "_" + data.getLegalFirstName() + "_"
					+ data.getLegalLastName() + ".pdf");
			String[] splitFilePath = filePath.split("\\.");
			//create temp file
			pdfFile = File.createTempFile(splitFilePath[0], ".pdf");
			//generate pdf
			generatePdf(pdfFile, cpassStudentReportXslFile.getFile(), source);
			//upload pdf to S3
			s3.synchMultipartUpload(filePath, pdfFile);
			data.setModifiedUser(data.getModifiedUser());
			data.setFilePath(getPathForDB(filePath));

			data.setGenerated(true);
			return data;
		} finally {
			//delete temp pdf
			FileUtils.deleteQuietly(pdfFile);
			for (File f : percentLevelChartFiles) {
				FileUtils.deleteQuietly(f);
			}
		}
	}

	private String createStudentDir(ExternalStudentReportDTO data)
			throws IOException {
		String studentDir = FileUtil.buildFilePath(getRootOutputDir(), "reports" + File.separator
				+ "external" + File.separator + data.getAssessmentProgram()
				+ File.separator + data.getSchoolYear() + File.separator
				+ data.getReportCycle() + File.separator
				+ data.getAssessmentCode() + File.separator
				+ data.getStateDisplayIdentifier() + File.separator
				+ data.getDistrictDisplayIdentifier() + File.separator
				+ data.getSchoolDisplayIdentifier() + File.separator
				+ data.getContentAreaCode() + File.separator
				+ data.getGradeCode() + File.separator
				+ data.getStateStudentIdentifier());
		return studentDir;
	}

	private File generateCpassPercentLevelChart(String outDir, String levelType,
			DefaultStatisticalCategoryDataset dataset,
			ExternalStudentReportDTO data) throws IOException {
		JFreeChart chart = createChart(dataset, data);
		chart.setBackgroundPaint(new Color(0, 0, 0, 0));
		int width = 600;
		int height = 290;
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
		Rectangle r = new Rectangle(0, 0, width, height);
		chart.draw(g2, r);
		String filePath = FileUtil.buildFilePath(outDir, "percent_level_bar_chart_"
				+ (100000 + fileNameSeed.nextInt(900000)) + ".svg");
		String[] splitFilePath = filePath.split("\\.");
		File f = File.createTempFile(splitFilePath[0], ".svg");
		SVGUtils.writeToSVG(f, g2.getSVGElement());
		return f;
	}

	private JFreeChart createChart(CategoryDataset dataset,
			ExternalStudentReportDTO data) {
		JFreeChart chart = ChartFactory.createBarChart("Score Comparisons", "",
				"", dataset, PlotOrientation.HORIZONTAL, false, true, false);

		Rectangle rect = new Rectangle(7, 7);
		CategoryPlot plot = chart.getCategoryPlot();

		Paint levelColor = new GradientPaint(0.0f, 0.0f, new Color(245, 203,
				167), 0.0f, 0.0f, new Color(245, 203, 167));
		Paint oddLevelColor = new GradientPaint(0.0f, 0.0f, new Color(245, 203,
				167), 0.0f, 0.0f, new Color(245, 203, 167));
		Paint evenLevelColor = new GradientPaint(0.0f, 0.0f, new Color(255, 255,
				255), 0.0f, 0.0f, new Color(255, 255, 255));

		int size=1;
		for (TestCutScores testCutScore : data.getTestCutScores()) {						
			if((size%2)==0){ // even
				levelColor = evenLevelColor;				
			}else{
				levelColor = oddLevelColor;
			}
			
			IntervalMarker intervalMarker = new IntervalMarker(
					testCutScore.getLevelLowCutScore(),
					testCutScore.getLevelHighCutScore(), levelColor);
			intervalMarker.setLabel(testCutScore.getLevelName());
			intervalMarker.setLabelAnchor(RectangleAnchor.CENTER);
			intervalMarker.setLabelFont(new Font("Verdana", Font.BOLD, 9));
			plot.addRangeMarker(intervalMarker, Layer.BACKGROUND);
			size++;
		}

		if (data.getStudentScore() == null || data.getStudentScore() == 0) {
			CategoryMarker marker = new CategoryMarker("My Score", new Color(0,
					0, 255, 0), new BasicStroke(3));
			marker.setLabel("Not enough data to calculate");
			marker.setLabelFont(new Font("Verdana", Font.PLAIN, 13));
			marker.setLabelPaint(new Color(152, 72, 7));
			marker.setLabelAnchor(RectangleAnchor.LEFT);
			marker.setLabelTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
			plot.addDomainMarker(marker, Layer.FOREGROUND);
		}

		if (data.getDistrictAverageScore() == null
				|| data.getDistrictAverageScore() == 0) {
			CategoryMarker marker = new CategoryMarker("My District Avg.",
					new Color(0, 0, 255, 0), new BasicStroke(3));
			marker.setLabel("Not enough data to calculate");
			marker.setLabelFont(new Font("Verdana", Font.PLAIN, 13));
			marker.setLabelPaint(new Color(152, 72, 7));
			marker.setLabelAnchor(RectangleAnchor.LEFT);
			marker.setLabelTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
			plot.addDomainMarker(marker, Layer.FOREGROUND);
		}

		if (data.getStateAverageScore() == null
				|| data.getStateAverageScore() == 0) {
			CategoryMarker marker = new CategoryMarker("My State Avg.",
					new Color(0, 0, 255, 0), new BasicStroke(3));
			marker.setLabel("Not enough data to calculate");
			marker.setLabelFont(new Font("Verdana", Font.PLAIN, 13));
			marker.setLabelPaint(new Color(152, 72, 7));
			marker.setLabelAnchor(RectangleAnchor.LEFT);
			marker.setLabelTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
			plot.addDomainMarker(marker, Layer.FOREGROUND);
		}

		if (data.getAllStatesAvgScore() == null
				|| data.getAllStatesAvgScore() == 0) {
			CategoryMarker marker = new CategoryMarker("Overall Avg.",
					new Color(0, 0, 255, 0), new BasicStroke(3));
			marker.setLabel("Not enough data to calculate");
			marker.setLabelFont(new Font("Verdana", Font.PLAIN, 13));
			marker.setLabelPaint(new Color(152, 72, 7));
			marker.setLabelAnchor(RectangleAnchor.LEFT);
			marker.setLabelTextAnchor(TextAnchor.HALF_ASCENT_LEFT);
			plot.addDomainMarker(marker, Layer.FOREGROUND);
		}

		BarRenderer barRenderer = (BarRenderer) plot.getRenderer();
		StatisticalBarRenderer render = new StatisticalBarRenderer();
		// Spaces between bars
		render.setSeriesShape(1, rect);
		barRenderer.setItemMargin(-20);
		render.setItemMargin(-20.0);
		barRenderer.getDefaultSeriesVisibleInLegend();
		render.setMaximumBarWidth(0.09);

		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlineVisible(true);
	    plot.setOutlinePaint(new Color(134,134,134));
	    plot.setOutlineStroke(new BasicStroke(1.0f));
	    plot.setInsets(new RectangleInsets(0,0,0,0));	     
		plot.setAxisOffset(new RectangleInsets(0, 0, 0, 30));
		final NumberAxis domainAxis = (NumberAxis) plot.getRangeAxis();
		domainAxis.setRange(data.getTestCutScores().get(0)
				.getLevelLowCutScore(),
				data.getTestCutScores().get(data.getTestCutScores().size() - 1)
						.getLevelHighCutScore() + 20);
		domainAxis.setTickUnit(new NumberTickUnit(50));
		domainAxis.setVerticalTickLabels(false);
		domainAxis.setPositiveArrowVisible(false);
		domainAxis.setAxisLineVisible(false);
		domainAxis.setTickLabelPaint(new Color(152, 72, 7));
		domainAxis.setTickMarksVisible(true);		
		domainAxis.setTickMarkPaint(new Color(134,134,134));
	    domainAxis.setTickMarkStroke(new BasicStroke(1.0f));
		domainAxis.setTickMarkOutsideLength(3.50f);
		domainAxis.setLabelFont(new Font("Verdana", Font.BOLD, 13));
		domainAxis.setLowerMargin(-0.5); // percentage of space before first bar
		domainAxis.setUpperMargin(0.02);
		domainAxis.setTickLabelsVisible(true);
		domainAxis.setTickLabelFont(new Font("Verdana", Font.BOLD, 10));
		domainAxis.setTickLabelInsets(new RectangleInsets(10, -0, 0, 0));

		BasicStroke s = new BasicStroke(1.0f);
		chart.setBorderStroke(s);
 		final CustomCategoryAxis domainAxis1 =  new CustomCategoryAxis();

		domainAxis1.setLowerMargin(-0.162);
		domainAxis1.setUpperMargin(0.073);
		domainAxis1.setCategoryMargin(0.64);
		domainAxis1.setTickLabelInsets(new RectangleInsets(5, -18, 25, 25));
		domainAxis1.setAxisLineVisible(false);
		domainAxis1.setTickLabelsVisible(true);
		domainAxis1.setTickLabelPaint(new Color(152, 72, 7));
	    domainAxis1.setTickLabelFont(new Font("Verdana", Font.BOLD, 12));
	    domainAxis1.setTickMarkPaint(new Color(134,134,134));
	    domainAxis1.setTickMarkStroke(new BasicStroke(1.0f));
	    domainAxis1.setTickMarkOutsideLength(3.0f);
	    domainAxis1.setCategoryLabelPositionOffset(10);
    	domainAxis1.setLabelFont(new Font("Verdana", Font.BOLD, 13));
		domainAxis1.setCategoryLabelPositions(CategoryLabelPositions.DOWN_90);	
		
		plot.setDomainGridlinePaint(Color.BLACK);
        plot.setDomainAxis(domainAxis1);
		CategoryAxis xAxis = plot.getDomainAxis();
		xAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		plot.setRangeMinorGridlinesVisible(true);

		final Color VERY_LIGHT_RED = new Color(152, 72, 7);

		render.setSeriesPaint(0, VERY_LIGHT_RED);
		render.setErrorIndicatorPaint(Color.black);
		plot.setRenderer(render);
		CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot())
				.getRenderer();
		renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setDefaultItemLabelsVisible(true);
		// inside bar showing number value
		ItemLabelPosition position = new ItemLabelPosition(
				ItemLabelAnchor.CENTER, TextAnchor.CENTER_LEFT);
		renderer.setDefaultPositiveItemLabelPosition(position);
		renderer.setDefaultItemLabelPaint(Color.WHITE);
		renderer.setDefaultItemLabelFont(new Font("Verdana", Font.BOLD, 9));
		chart.setBorderVisible(true);
		chart.setBorderPaint(new Color(134, 134, 134));
		//chart.setBorderPaint(new Color(128, 128, 128));
		// RectangleInsets chartRectangle = new
		// RectangleInsets(TOP,LEFT,BOTTOM,RIGHT);

		RectangleInsets chartRectangle = new RectangleInsets(18F, 5F, 10F, 0F);
		chart.setPadding(chartRectangle);
		chart.getTitle().setHorizontalAlignment(HorizontalAlignment.CENTER);
		chart.getTitle().setPadding(0, 0, 9, 0);
		chart.getTitle().setFont(new Font("Verdana", Font.BOLD, 20));

		return chart;
	}
}
