package edu.ku.cete.report;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;
import org.jfree.chart.ui.GradientPaintTransformType;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.StandardGradientPaintTransformer;
import org.jfree.chart.ui.TextAnchor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.report.InterimReportContext;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.util.FileUtil;

@Component
public class InterimStudentReportGenerator extends ReportGenerator {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InterimStudentReportGenerator.class);

	@Value("/images/reports/interimStudentReportKAPlogo.svg")
	private Resource interimStudentReportKAPlogo;

	@Value("/templates/xslt/reports/interimStudentReport.xsl")
	private Resource interimStudentReportXslFile;

	@Value("/images/reports/reportp1Arrow.svg")
	private Resource getFooterNextPageArrowPath;

	@Value("/images/reports/interim_question_unanswered.svg")
	private Resource getQuestionUnansweredPath;

	@Value("/images/reports/interim_nocredit.svg")
	private Resource getNoCreditPath;

	@Value("/images/reports/interim_full.svg")
	private Resource getFullCreditPath;

	@Value("/images/reports/interim_partial.svg")
	private Resource getPartialCreditPath;

	@Value("/images/reports/interimStudentKAPFooterLogo.svg")
	private Resource kapFooterLogoFile;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	private AwsS3Service s3;

	public InterimStudentReport generateInterimStudentReportFile(
			Map<String, InterimStudentReport> testingCycleRecordMap,
			String reportCycle, List<StudentReportQuestionInfo> questionInfo,
			List<TestCutScores> testCutScores,
			List<LevelDescription> levelDescriptions) throws Exception {
		
			List<File> reportCycleScoreFiles = new ArrayList<File>();
			File reportCycleAxisLine = null;
			File reportCycleScoreChart = null;
			
			//New Integration logic start
			try {
			InterimReportContext reportData = new InterimReportContext();
			reportData.setData(testingCycleRecordMap.get(reportCycle));
			
			reportData.setReportQuestionInformation(questionInfo);
			reportData.setLevelDescriptions(levelDescriptions);
			reportData.setTestCutScores(testCutScores);
			
			File FilesArrow = getFooterNextPageArrowPath.getFile();
			String footerNextPageArrowPath = FilesArrow.toURI().toString();
			reportData.setFooterNextPageArrowPath(footerNextPageArrowPath);

			File fileQuestionUnanswered = getQuestionUnansweredPath.getFile();
			String questionUnansweredPath = fileQuestionUnanswered.toURI()
					.toString();
			reportData.setQuestionUnansweredPath(questionUnansweredPath);

			File fileNoCredit = getNoCreditPath.getFile();
			String noCreditdPath = fileNoCredit.toURI().toString();
			reportData.setNoCreditPath(noCreditdPath);

			File fileFullCredit = getFullCreditPath.getFile();
			String fullCreditPath = fileFullCredit.toURI().toString();
			reportData.setFullCreditPath(fullCreditPath);

			File filePartialCredit = getPartialCreditPath.getFile();
			String partialCreditPath = filePartialCredit.toURI().toString();
			reportData.setPartialCreditPath(partialCreditPath);

			String outDir = createInterimStudentDir(testingCycleRecordMap.get(reportCycle));
			reportData.setLogoPath(interimStudentReportKAPlogo.getFile()
					.toURI().toString());

			File footerLogoFile = kapFooterLogoFile.getFile();
			reportData.setFooterLogoPath(footerLogoFile.toURI().toString());
			
			reportData.setReportCycleLevelChartWidth(500);
			reportData.setReportCycleLevelChartHeight(68);
			
            List<Long> axisScoreRange = getScoreRangeValue(testCutScores, false, testingCycleRecordMap.get(reportCycle));
			reportCycleAxisLine = generateReportCycleScoreAxisRangeMarkerChart(createIntervalDataset(axisScoreRange, testingCycleRecordMap.get(reportCycle), reportCycle),createDataset(testCutScores,axisScoreRange, reportCycle, testingCycleRecordMap.get(reportCycle)), reportData, testingCycleRecordMap.get(reportCycle),  outDir);
			reportData.setReportCycleScoreRangeCharts(reportCycleAxisLine.toURI().toString());
			reportCycleScoreFiles.add(reportCycleAxisLine);
			
			boolean currentWindow = false;
			boolean notTested = false;
			List<Long> scoreRange = new ArrayList<Long>();
			CategoryDataset dataset = null;
			DefaultIntervalCategoryDataset intervalDataset = null;
			InterimStudentReport report = null;
			int i = 1;
			for (String key : testingCycleRecordMap.keySet()){
				notTested = false;
				reportData.setShowRangeBar(false);
				reportData.setAvailable(true);
				reportData.getReportCycles().add(key);
				report = testingCycleRecordMap.get(key) == null?testingCycleRecordMap.get(reportCycle):testingCycleRecordMap.get(key);
				
				if(reportCycle.equalsIgnoreCase(key))
					currentWindow = true;
				
				if(!currentWindow && testingCycleRecordMap.get(key) == null)
					notTested = true;
				
				if(testingCycleRecordMap.get(key)  == null){
					reportData.setShowRangeBar(false);
				}else{
					reportData.setAvailable(false);
					
					if(report.getScaleScore()!=null && report.getScaleScore()>0)
					reportData.setShowRangeBar(true);					
				}
						           
				reportData.setGenerateLevelRangeMarker(false);
				if (i++ == testingCycleRecordMap.size()) {
					reportData.setGenerateLevelRangeMarker(true);
				}
				
				scoreRange  = getScoreRangeValue(testCutScores, true, report);
				dataset = createDataset(testCutScores,scoreRange, key, report);
				intervalDataset = createIntervalDataset(scoreRange, report, key);
				reportCycleScoreChart = generateReportCycleScoreRangeChart(intervalDataset, dataset, reportData, report,  outDir, notTested);				
				reportData.getReportCycleCharts().add(reportCycleScoreChart.toURI().toString());
				reportCycleScoreFiles.add(reportCycleScoreChart);

	        }
			
			XStream xstream = new XStream();
			xstream.alias("interimReportDetails", InterimReportContext.class);
			TraxSource source = new TraxSource(reportData, xstream);
			File pdfFile = null;
			String filePath = FileUtil.buildFilePath(outDir, "SR_Interim_"
					+ sanitizeForPath(testingCycleRecordMap.get(reportCycle).getStateStudentIdentifier())
					+ ".pdf");
			String[] splitFilePath = filePath.split("\\.");
			pdfFile = File.createTempFile(splitFilePath[0], ".pdf");
			generatePdf(pdfFile, interimStudentReportXslFile.getFile(), source);
			s3.synchMultipartUpload(filePath, pdfFile);
			testingCycleRecordMap.get(reportCycle).setFilePath(getPathForDB(filePath));
			testingCycleRecordMap.get(reportCycle).setGenerated(true);
			FileUtils.deleteQuietly(pdfFile);
			return testingCycleRecordMap.get(reportCycle);
		} finally {
			for (File f : reportCycleScoreFiles) {
				FileUtils.deleteQuietly(f);
			}
		}		
			//New Integration logic end
	}

	private String createInterimStudentDir(InterimStudentReport data)
			throws IOException {
		String studentDir = FileUtil.buildFilePath(getRootOutputDir(), "reports" + File.separator
				+ data.getSchoolYear() + File.separator + "IPSR"
				+ File.separator +data.getReportCycle()+ File.separator + data.getStateId() 
				+ File.separator + data.getDistrictId() + File.separator
				+ data.getAttendanceSchoolId() + File.separator
				+ data.getContentAreaCode() + File.separator
				+ data.getGradeCode() + File.separator + data.getStudentId());
		return studentDir;
	}

	private File generateReportCycleScoreRangeChart(DefaultIntervalCategoryDataset intervalDataset,CategoryDataset dataset,
			InterimReportContext reportData, InterimStudentReport data, String outDir, boolean notTested) throws IOException {
		int width = reportData.getReportCycleLevelChartWidth();
		int height = reportData.getReportCycleLevelChartHeight();
		
		Category notTestedCode = categoryService.selectByCategoryCodeAndType("STUDENT_NOT_TESTED", "NO_SCORE_RANGE_REASON");
		boolean generateBar = true;
		String filename = "interim_scoreRange_bar_"+ (100000 + fileNameSeed.nextInt(900000)) + ".svg";
		
		if(!reportData.isAvailable() && data.getStatus()!=null && !data.getStatus()) {
			filename = "interim_noScaleScore_"+data.getReasonCode()+"_"+ (100000 + fileNameSeed.nextInt(900000)) + ".svg";
		 	reportData.getReasonCodes().add(data.getReasonCode());
			generateBar = false;
		}else if (!reportData.isShowRangeBar()){
			generateBar = false;
			//Added for showing text for students those who did not take test
			if(notTested){
				filename = "interim_noScaleScore_"+notTestedCode.getCategoryName()+"_"+ (100000 + fileNameSeed.nextInt(900000)) + ".svg";
			 	reportData.getReasonCodes().add(notTestedCode.getCategoryName());
			}else{
				reportData.getReasonCodes().add("");
			}			
		}else{
			reportData.getReasonCodes().add("");
		}
		
		JFreeChart chart = createChart(intervalDataset, dataset, reportData.getTestCutScores(), data,
				generateBar,false , reportData.isGenerateLevelRangeMarker());
		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderVisible(false);
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
		Rectangle r = new Rectangle(0, 0, width, height);
		chart.draw(g2, r);
		String fullPath = FileUtil.buildFilePath(outDir, filename);
		String[] splitFullPath = fullPath.split("\\.");
		File f = File.createTempFile(splitFullPath[0],"."+splitFullPath[1]);
		SVGUtils.writeToSVG(f, g2.getSVGElement());
		return f;
	}


	private File generateReportCycleScoreAxisRangeMarkerChart(DefaultIntervalCategoryDataset intervalDataset, CategoryDataset dataset,
			InterimReportContext reportData, InterimStudentReport data, String outDir) throws IOException {
		int width = reportData.getReportCycleLevelChartWidth();
		int height = 20;
		JFreeChart chart = createChart(intervalDataset, dataset, reportData.getTestCutScores(), data,
				false, true, false);
		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderVisible(false);
		SVGGraphics2D g2 = new SVGGraphics2D(width, height);
		Rectangle r = new Rectangle(0, 0, width, height);
		chart.draw(g2, r);
		String fullPath = FileUtil.buildFilePath(outDir, "xaxis_reportCycleAxis_"
				+ (100000 + fileNameSeed.nextInt(900000)) + ".svg");
		String[] splitFullPath = fullPath.split("\\.");
		File f = File.createTempFile(splitFullPath[0],"."+splitFullPath[1]);
		SVGUtils.writeToSVG(f, g2.getSVGElement());
		return f;
	}
	
	
	
	private DefaultIntervalCategoryDataset createIntervalDataset(List<Long> axisScoreRange, InterimStudentReport interimStudentReport, String reportCycle) {

		DefaultIntervalCategoryDataset dataSet = null;
		Long lowScaleScoreRange = 1l;
		Long highScaleScoreRange = 0l;
		if (interimStudentReport.getScaleScore() != null
				&& interimStudentReport.getScaleScore() > 0) {
			lowScaleScoreRange = interimStudentReport.getScaleScore()
					.longValue()
					- interimStudentReport.getStandardError().longValue();
			highScaleScoreRange = interimStudentReport.getScaleScore()
					.longValue()
					+ interimStudentReport.getStandardError().longValue();
		}
		lowScaleScoreRange = axisScoreRange.get(0)>=lowScaleScoreRange.longValue()?axisScoreRange.get(0): lowScaleScoreRange;
		highScaleScoreRange = axisScoreRange.get(axisScoreRange.size()-1)<=highScaleScoreRange.longValue()?axisScoreRange.get(axisScoreRange.size()-1): highScaleScoreRange;
		double[][] lowScore = {{ lowScaleScoreRange}};
	    double[][] highScore = {{ highScaleScoreRange}};
	    final String[] CATEGORIES = {reportCycle};
	    dataSet = new DefaultIntervalCategoryDataset(lowScore, highScore);
	    dataSet.setCategoryKeys(CATEGORIES);
		return dataSet;
	}
	
	
	private CategoryDataset createDataset(List<TestCutScores> testCutScores,
			List<Long> scoreRange, String reportCycle,InterimStudentReport interimStudentReport) {
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		ListIterator<Long> iterator1 = scoreRange.listIterator();
		Long prviousvalue = 0l;
		Long lowScaleScoreRange = 0l;
		Long highScaleScoreRange = 0l;
		if(interimStudentReport.getScaleScore()!=null && interimStudentReport.getScaleScore()>0)
		{
			 lowScaleScoreRange = interimStudentReport.getScaleScore().longValue() - interimStudentReport.getStandardError().longValue();
			 highScaleScoreRange = interimStudentReport.getScaleScore().longValue() + interimStudentReport.getStandardError().longValue();
			 
		}
		
		//if(highScaleScoreRange > scoreRange.get(scoreRange.size()-1)) lowScaleScoreRange--;
		
		while (iterator1.hasNext()) {
			if (!iterator1.hasPrevious()) {
				Long range = (Long) iterator1.next();
				result.addValue(range, "White_"+range ,
						reportCycle);
				prviousvalue = range;
				//iterator1.next() ;				
			} else {
				Long range = (Long) iterator1.next();
				//if(){	
					if ((range.longValue() >= lowScaleScoreRange+1 && range.longValue() <= highScaleScoreRange)  
							&& range.longValue() > scoreRange.get(0) && range.longValue() <= scoreRange.get(scoreRange.size()-1)) {
						result.addValue(range - prviousvalue, "Black_" + range,
								reportCycle);
					}
				//}
					else {
					for (TestCutScores cutScore : testCutScores) {
						if (range.longValue() >= cutScore.getLevelLowCutScore()
								&& range.longValue() <= cutScore
										.getLevelHighCutScore()) {
							result.addValue(range - prviousvalue, "Level "
									+ cutScore.getLevel() + "_" + range,
									reportCycle);
						}
					}
				}
				// System.out.println(range - prviousvalue);
				prviousvalue = range;
			}
		}
		return result;
	}
	
	private JFreeChart createChart(DefaultIntervalCategoryDataset intervalDataset,final CategoryDataset dataset,
			List<TestCutScores> testCutScores, InterimStudentReport data, boolean generateBar, boolean generateAxisLine,
			boolean generateLevelMarker) {

		List<Long> scoreRange = getScoreRangeValue(testCutScores, false, data);

		JFreeChart chart = ChartFactory.createStackedBarChart("", // chart
																		// title
				"", // domain axis label
				"Value", // range axis label
				dataset, // data
				PlotOrientation.HORIZONTAL, // the plot orientation
				false, // legend
				false, // tooltips
				false // urls
				);

		CustomStackedBarChart renderer = new CustomStackedBarChart(generateBar);
		renderer.setItemMargin(0.0);

		Paint level1Color = new GradientPaint(0.0f, 0.0f,
				Color.decode("#F07D4F"), 0.0f, 0.0f, Color.decode("#F07D4F"));
		Paint level2Color = new GradientPaint(0.0f, 0.0f,
				Color.decode("#EF9A36"), 0.0f, 0.0f, Color.decode("#EF9A36"));
		Paint level3Color = new GradientPaint(0.0f, 0.0f,
				Color.decode("#ECBE72"), 0.0f, 0.0f, Color.decode("#ECBE72"));
		Paint level4Color = new GradientPaint(0.0f, 0.0f,
				Color.decode("#F0E8AF"), 0.0f, 0.0f, Color.decode("#F0E8AF"));
		Paint scoreRanegColor = new GradientPaint(0.0f, 0.0f, Color.BLACK,
				0.0f, 0.0f, Color.BLACK);
		Paint whiteColor = new GradientPaint(0.0f, 0.0f, Color.WHITE,
				0.0f, 0.0f, Color.WHITE);

		int count = 0;
		for (Object rowKey : dataset.getRowKeys()) {
			if (rowKey.toString().contains("White_"))
				renderer.setSeriesPaint(count, whiteColor);
			else if (rowKey.toString().contains("Level 1"))
				renderer.setSeriesPaint(count, level1Color);
			else if (rowKey.toString().contains("Level 2"))
				renderer.setSeriesPaint(count, level2Color);
			else if (rowKey.toString().contains("Level 3"))
				renderer.setSeriesPaint(count, level3Color);
			else if (rowKey.toString().contains("Level 4"))
				renderer.setSeriesPaint(count, level4Color);
			else if (rowKey.toString().contains("Black_"))
				renderer.setSeriesPaint(count, scoreRanegColor);
			count++;
		}

		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(
				GradientPaintTransformType.HORIZONTAL));

		renderer.setSeriesStroke(2, new BasicStroke(2.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f,
				new float[] { 2.0f, 6.0f }, 0.0f));
		renderer.setBarPainter(new StandardBarPainter());
		renderer.setMaximumBarWidth(0.070);
		renderer.setDrawBarOutline(false);

		Long lowScaleScoreRange = 0l;
		Long highScaleScoreRange = 0l;
		if(data.getScaleScore() != null && data.getScaleScore() > 0l){
		lowScaleScoreRange = data.getScaleScore()
				.longValue()
				- data.getStandardError().longValue();
		highScaleScoreRange = data.getScaleScore()
				.longValue()
				+ data.getStandardError().longValue();
		}
		
        IntervalBarRenderer scoreRangeBarRender = new IntervalBarRenderer();
        scoreRangeBarRender.setSeriesPaint(0, scoreRanegColor);
        scoreRangeBarRender.setMaximumBarWidth(0.150);
        scoreRangeBarRender.setShadowVisible(false);
        scoreRangeBarRender.setDrawBarOutline(false);
        scoreRangeBarRender.setBarPainter(new StandardBarPainter());
        if(generateBar && highScaleScoreRange > scoreRange.get(0).longValue()) scoreRangeBarRender.setDefaultSeriesVisible(true);
        else  scoreRangeBarRender.setDefaultSeriesVisible(false);
       		
		double rangeMargin = 3;
		double minOfDataset = scoreRange.get(0);
		double maxOfDataset = scoreRange.get(scoreRange.size() - 1);
		double lowerLimit = minOfDataset - rangeMargin;
		double upperLimit = maxOfDataset + rangeMargin;

		ValueAxis valueAxis = new NumberAxis("");
		valueAxis.setVisible(true);	
		valueAxis.setVerticalTickLabels(true);
		valueAxis.setRange(scoreRange.get(0)-rangeMargin,
				scoreRange.get(scoreRange.size() - 1)+rangeMargin);
		valueAxis.setVerticalTickLabels(false);
		valueAxis.setUpperBound(upperLimit);
		valueAxis.setLowerBound(lowerLimit);
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRangeAxis(valueAxis);
		plot.setRangeGridlinesVisible(true);

		final CategoryAxis categoryAxis = (CategoryAxis) plot.getDomainAxis();
		categoryAxis.setAxisLineVisible(false);
		categoryAxis.setTickMarksVisible(false);
		categoryAxis.setTickLabelFont(new Font("Verdana", Font.BOLD, 10));
		categoryAxis.setTickLabelInsets(new RectangleInsets(0, 0, 0, 0));
		categoryAxis.setCategoryLabelPositionOffset(40);
		categoryAxis.setMaximumCategoryLabelLines(4);
		categoryAxis.setVisible(false);

		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setVisible(false);
		
		plot.setRenderer(renderer);
		
		plot.setDataset(1,dataset);
		plot.setRenderer(1, renderer);
		
		plot.setDataset(0,intervalDataset);
	    plot.setRenderer(0, scoreRangeBarRender);
		
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setOutlineVisible(false);
		plot.setDomainGridlinesVisible(false);
	    plot.setInsets(new RectangleInsets(-2.0, 0, 0, 0));
	    plot.setAxisOffset(RectangleInsets.ZERO_INSETS);
	    plot.getRangeAxis().setAxisLineVisible(false);
	    
		Long previousScore = 0l;
		int levelNo = 0;
		for (Long score : scoreRange) {
					Marker scoreRanegMarker = new ValueMarker(score);
					scoreRanegMarker.setStroke(new BasicStroke(
				            0.5f, BasicStroke.JOIN_ROUND, BasicStroke.JOIN_ROUND, 
				            0f, new float[] {0.5f, 0.8f}, 0.0f
				            ));
					if(generateAxisLine) scoreRanegMarker.setPaint(Color.WHITE);
					else scoreRanegMarker.setPaint(Color.decode("#929597"));
					scoreRanegMarker.setLabelFont(new Font("Verdana", Font.PLAIN, 10));
					if(generateAxisLine) scoreRanegMarker.setLabel(score.toString());
					scoreRanegMarker.setLabelOffset(new RectangleInsets(3, 0,
							0, 1.6));
					scoreRanegMarker.setLabelAnchor(RectangleAnchor.TOP);
					scoreRanegMarker.setLabelTextAnchor(TextAnchor.TOP_CENTER);
					scoreRanegMarker.setLabelBackgroundColor(Color.WHITE);
					if(!generateAxisLine) scoreRanegMarker.setLabelPaint(Color.WHITE);
					else scoreRanegMarker.setLabelPaint(Color.BLACK);
					plot.addRangeMarker(scoreRanegMarker, Layer.FOREGROUND);

					if (previousScore != 0 && generateLevelMarker) {
					Long remainingRange = (score - previousScore) / 2;			
					Marker levelRangeMarker = new ValueMarker(remainingRange
							+ previousScore);
					levelRangeMarker.setStroke(new BasicStroke(0.1f,
							BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
							0.1f, new float[] { 3.0f, 3.0f }, 0.0f));
					levelRangeMarker.setPaint(Color.WHITE);
					levelRangeMarker.setLabelFont(new Font("Verdana", Font.PLAIN, 10));
					levelRangeMarker.setLabel("Level " + levelNo);
					levelRangeMarker.setLabelOffset(new RectangleInsets(0, 0,
							12, 0));
					levelRangeMarker.setLabelAnchor(RectangleAnchor.BOTTOM);
					levelRangeMarker
							.setLabelTextAnchor(TextAnchor.BOTTOM_CENTER);
					levelRangeMarker.setLabelBackgroundColor(Color.WHITE);
					plot.addRangeMarker(levelRangeMarker, Layer.BACKGROUND);

				}
				previousScore = score;
				levelNo++;
			
		}

		chart.setBackgroundPaint(Color.WHITE);
		chart.setBorderVisible(false);
		return chart;

	}

	public static List<Long> getScoreRangeValue(
			List<TestCutScores> testCutScores, boolean withScoreRangeSorting, InterimStudentReport interimStudentReport) {
		Long lowScaleScoreRange = 0l;
		Long highScaleScoreRange = 0l;
		if(interimStudentReport.getScaleScore() != null && interimStudentReport.getScaleScore() > 0l){
		lowScaleScoreRange = interimStudentReport.getScaleScore()
				.longValue()
				- interimStudentReport.getStandardError().longValue();
		highScaleScoreRange = interimStudentReport.getScaleScore()
				.longValue()
				+ interimStudentReport.getStandardError().longValue();
		}
		List<Long> scoreRange = new ArrayList<Long>();
		ListIterator<TestCutScores> iterator = testCutScores.listIterator();
		
		while (iterator.hasNext()) {
			if (!iterator.hasPrevious()) {
				TestCutScores testCuScores = iterator.next();
				scoreRange.add(testCuScores.getLevelLowCutScore());
				scoreRange.add(testCuScores.getLevelHighCutScore());
			} else {
				TestCutScores testCuScores = iterator.next();
				scoreRange.add(testCuScores.getLevelHighCutScore());
			}

		}
		Collections.sort(scoreRange);
		
		if (withScoreRangeSorting) {
			if (!scoreRange.contains(lowScaleScoreRange) && lowScaleScoreRange > scoreRange.get(0)){
				scoreRange.add(lowScaleScoreRange);
			    Collections.sort(scoreRange);
			}
			if (!scoreRange.contains(highScaleScoreRange) && highScaleScoreRange < scoreRange.get(scoreRange.size()-1) && highScaleScoreRange > scoreRange.get(0)){
				scoreRange.add(highScaleScoreRange);
			    Collections.sort(scoreRange);
			}
		}

		Collections.sort(scoreRange);

		return scoreRange;

	}

}