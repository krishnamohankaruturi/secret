package edu.ku.cete.domain.report;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.report.StudentReportQuestionInfo;

public class InterimReportContext {

	private Object data;
	private String footerNextPageArrowPath;
	private String logoPath;
	private String footerLogoPath;
	private String questionUnansweredPath;
	private String noCreditPath;
	private String partialCreditPath;
	private String fullCreditPath;
	private List<StudentReportQuestionInfo> reportQuestionInformation = new ArrayList<StudentReportQuestionInfo>();
	private List<LevelDescription> levelDescriptions = new ArrayList<LevelDescription>();
	private int reportCycleLevelChartWidth;
	private int reportCycleLevelChartHeight;
	private List<TestCutScores> testCutScores;
	private List<String> reportCycleCharts = new ArrayList<String>();
	private List<String> reportCycles = new ArrayList<String>();
	private String reportCycleScoreRangeCharts;
	private boolean showRangeBar;
	private boolean isAvailable;
	private boolean generateLevelRangeMarker;
	private List<String> reasonCodes = new ArrayList<String>();
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getFooterLogoPath() {
		return footerLogoPath;
	}

	public void setFooterLogoPath(String footerLogoPath) {
		this.footerLogoPath = footerLogoPath;
	}

	public String getFooterNextPageArrowPath() {
		return footerNextPageArrowPath;
	}

	public void setFooterNextPageArrowPath(String footerNextPageArrowPath) {
		this.footerNextPageArrowPath = footerNextPageArrowPath;
	}

	public String getQuestionUnansweredPath() {
		return questionUnansweredPath;
	}

	public void setQuestionUnansweredPath(String questionUnansweredPath) {
		this.questionUnansweredPath = questionUnansweredPath;
	}

	public String getNoCreditPath() {
		return noCreditPath;
	}

	public void setNoCreditPath(String noCreditPath) {
		this.noCreditPath = noCreditPath;
	}

	public String getPartialCreditPath() {
		return partialCreditPath;
	}

	public void setPartialCreditPath(String partialCreditPath) {
		this.partialCreditPath = partialCreditPath;
	}

	public String getFullCreditPath() {
		return fullCreditPath;
	}

	public void setFullCreditPath(String fullCreditPath) {
		this.fullCreditPath = fullCreditPath;
	}

	public List<StudentReportQuestionInfo> getReportQuestionInformation() {
		return reportQuestionInformation;
	}

	public void setReportQuestionInformation(
			List<StudentReportQuestionInfo> reportQuestionInformation) {
		this.reportQuestionInformation = reportQuestionInformation;
	}

	public List<TestCutScores> getTestCutScores() {
		return testCutScores;
	}

	public void setTestCutScores(List<TestCutScores> testCutScores) {
		this.testCutScores = testCutScores;
	}

	public List<String> getReportCycleCharts() {
		return reportCycleCharts;
	}

	public void setReportCycleCharts(List<String> reportCycleCharts) {
		this.reportCycleCharts = reportCycleCharts;
	}

	public int getReportCycleLevelChartWidth() {
		return reportCycleLevelChartWidth;
	}

	public void setReportCycleLevelChartWidth(int reportCycleLevelChartWidth) {
		this.reportCycleLevelChartWidth = reportCycleLevelChartWidth;
	}

	public int getReportCycleLevelChartHeight() {
		return reportCycleLevelChartHeight;
	}

	public void setReportCycleLevelChartHeight(int reportCycleLevelChartHeight) {
		this.reportCycleLevelChartHeight = reportCycleLevelChartHeight;
	}

	public List<String> getReportCycles() {
		return reportCycles;
	}

	public void setReportCycles(List<String> reportCycles) {
		this.reportCycles = reportCycles;
	}
	public String getReportCycleScoreRangeCharts() {
		return reportCycleScoreRangeCharts;
	}

	public void setReportCycleScoreRangeCharts(String reportCycleScoreRangeCharts) {
		this.reportCycleScoreRangeCharts = reportCycleScoreRangeCharts;
	}

	public boolean isShowRangeBar() {
		return showRangeBar;
	}

	public void setShowRangeBar(boolean showRangeBar) {
		this.showRangeBar = showRangeBar;
	}

	public boolean isGenerateLevelRangeMarker() {
		return generateLevelRangeMarker;
	}

	public void setGenerateLevelRangeMarker(boolean generateLevelRangeMarker) {
		this.generateLevelRangeMarker = generateLevelRangeMarker;
	}

	public List<LevelDescription> getLevelDescriptions() {
		return levelDescriptions;
	}

	public void setLevelDescriptions(List<LevelDescription> levelDescriptions) {
		this.levelDescriptions = levelDescriptions;
	}

	public List<String> getReasonCodes() {
		return reasonCodes;
	}

	public void setReasonCodes(List<String> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}
