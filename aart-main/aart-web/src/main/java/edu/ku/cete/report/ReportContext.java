package edu.ku.cete.report;

import java.util.Collection;
import java.util.List;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.report.StudentReport;

public class ReportContext {
	private Object data;
	private Integer dataSize;
	private String logoPath;
	private String footerLogoPath;
	private String unusedAreaIconPath;
	private String studentMeter;
	private String schoolMeter;
	private String districtMeter;
	private String stateMeter;
	private String percentLevelChart;
	private int suppressLevels=0;
	
	private String studentSubScoreChart;
	private String schoolSubScoreChart;
	private String districtSubScoreChart;
	private String stateSubScoreChart;
	private String axisSubScoreChart;
	private String schoolPrivacyStmtFlag;
	private String districtPrivacyStmtFlag;
	private String statePrivacyStmtFlag;
	private String axisPercentLevelChart;
	
	private int percentLevelChartWidth;
	private int percentLevelChartHeight;
	
	private int studentMeterWidth;
	private int studentMeterHeight;
	private int smallMeterWidth;
	private int smallMeterHeight;
	private int subScoreChartWidth;
	private int subScoreChartHeight;

	private String exitStatusIconPath;
	private String incompleteStatusIconPath;
	private List<String> allGrades;
	private List<String> gradeMedianCharts;
	private List<String> gradePercentLevelCharts;
	private List<String> mainPercentLevelCharts;
	private List<String> combinedPercentLevelCharts;
	private List<String> mdptPercentLevelCharts;
	

	private String scoreStarPerformanceLevelPath;	
	private String onDemandWritingTaskScorePath;
	private String footerNextPageArrowPath;
	private String iconExceedsPath;
	private String iconInsufficientPath;
	private String iconMeetsPath;
	private String iconBelowPath;
	//US18542 add variable
	private String transferredStudentPath;
	private String incompleteScorePath;
	private String responseNotScoredPath;
	private List<ReportStandardError> standardErrorList;
	
	private String summaryLevel;
	
	//KELPA2 Student Report Changes
	private List<StudentReport> domainScoreList;
	private String kelpaReportIntroParagraph;
	private List<Category> domainPerformanceLevelCategoryLists;
	private String domainNotTestedLabel;
    private String progressStatus;
	
  //F1004 DLM Monitoring Summary report
    private Boolean isIEState;
    private String testingCycleName1;
    private String testingCycleName2;
    
    
	public String getResponseNotScoredPath() {
		return responseNotScoredPath;
	}

	public void setResponseNotScoredPath(String responseNotScoredPath) {
		this.responseNotScoredPath = responseNotScoredPath;
	}

	public String getIncompleteScorePath() {
		return incompleteScorePath;
	}

	public void setIncompleteScorePath(String incompleteScorePath) {
		this.incompleteScorePath = incompleteScorePath;
	}

	public String getTransferredStudentPath() {
		return transferredStudentPath;
	}

	public void setTransferredStudentPath(String transferredStudentPath) {
		this.transferredStudentPath = transferredStudentPath;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
		
		if (data instanceof Collection<?>) {
			this.dataSize = ((Collection<?>) data).size();
		} else {
			this.dataSize = null;
		}
	}

	public Integer getDataSize() {
		return dataSize;
	}

	public void setDataSize(Integer dataSize) {
		this.dataSize = dataSize;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getStudentMeter() {
		return studentMeter;
	}

	public void setStudentMeter(String studentMeter) {
		this.studentMeter = studentMeter;
	}

	public String getDistrictMeter() {
		return districtMeter;
	}

	public void setDistrictMeter(String districtMeter) {
		this.districtMeter = districtMeter;
	}

	public String getStateMeter() {
		return stateMeter;
	}

	public void setStateMeter(String stateMeter) {
		this.stateMeter = stateMeter;
	}

	public String getSchoolMeter() {
		return schoolMeter;
	}

	public void setSchoolMeter(String schoolMeter) {
		this.schoolMeter = schoolMeter;
	}

	public int getStudentMeterWidth() {
		return studentMeterWidth;
	}

	public void setStudentMeterWidth(int studentMeterWidth) {
		this.studentMeterWidth = studentMeterWidth;
	}

	public int getStudentMeterHeight() {
		return studentMeterHeight;
	}

	public void setStudentMeterHeight(int studentMeterHeight) {
		this.studentMeterHeight = studentMeterHeight;
	}

	public int getSmallMeterWidth() {
		return smallMeterWidth;
	}

	public void setSmallMeterWidth(int smallMeterWidth) {
		this.smallMeterWidth = smallMeterWidth;
	}

	public int getSmallMeterHeight() {
		return smallMeterHeight;
	}

	public void setSmallMeterHeight(int smallMeterHeight) {
		this.smallMeterHeight = smallMeterHeight;
	}

	public String getPercentLevelChart() {
		return percentLevelChart;
	}

	public void setPercentLevelChart(String percentLevelChart) {
		this.percentLevelChart = percentLevelChart;
	}

	public int getPercentLevelChartWidth() {
		return percentLevelChartWidth;
	}

	public void setPercentLevelChartWidth(int percentLevelChartWidth) {
		this.percentLevelChartWidth = percentLevelChartWidth;
	}

	public int getPercentLevelChartHeight() {
		return percentLevelChartHeight;
	}

	public void setPercentLevelChartHeight(int percentLevelChartHeight) {
		this.percentLevelChartHeight = percentLevelChartHeight;
	}

	public String getFooterLogoPath() {
		return footerLogoPath;
	}

	public void setFooterLogoPath(String footerLogoPath) {
		this.footerLogoPath = footerLogoPath;
	}

	public int getSubScoreChartWidth() {
		return subScoreChartWidth;
	}

	public void setSubScoreChartWidth(int subScoreChartWidth) {
		this.subScoreChartWidth = subScoreChartWidth;
	}

	public int getSubScoreChartHeight() {
		return subScoreChartHeight;
	}

	public void setSubScoreChartHeight(int subScoreChartHeight) {
		this.subScoreChartHeight = subScoreChartHeight;
	}

	public String getStudentSubScoreChart() {
		return studentSubScoreChart;
	}

	public void setStudentSubScoreChart(String studentSubScoreChart) {
		this.studentSubScoreChart = studentSubScoreChart;
	}

	public String getSchoolSubScoreChart() {
		return schoolSubScoreChart;
	}

	public void setSchoolSubScoreChart(String schoolSubScoreChart) {
		this.schoolSubScoreChart = schoolSubScoreChart;
	}

	public String getDistrictSubScoreChart() {
		return districtSubScoreChart;
	}

	public void setDistrictSubScoreChart(String districtSubScoreChart) {
		this.districtSubScoreChart = districtSubScoreChart;
	}

	public String getStateSubScoreChart() {
		return stateSubScoreChart;
	}

	public void setStateSubScoreChart(String stateSubScoreChart) {
		this.stateSubScoreChart = stateSubScoreChart;
	}

	public String getAxisSubScoreChart() {
		return axisSubScoreChart;
	}

	public void setAxisSubScoreChart(String axisSubScoreChart) {
		this.axisSubScoreChart = axisSubScoreChart;
	}

	public String getExitStatusIconPath() {
		return exitStatusIconPath;
	}

	public void setExitStatusIconPath(String exitStatusIconPath) {
		this.exitStatusIconPath = exitStatusIconPath;
	}

	public String getIncompleteStatusIconPath() {
		return incompleteStatusIconPath;
	}

	public void setIncompleteStatusIconPath(String incompleteStatusIconPath) {
		this.incompleteStatusIconPath = incompleteStatusIconPath;
	}

	public int getSuppressLevels() {
		return suppressLevels;
	}

	public void setSuppressLevels(int suppressLevels) {
		this.suppressLevels = suppressLevels;
	}

	public String getSchoolPrivacyStmtFlag() {
		return schoolPrivacyStmtFlag;
	}

	public void setSchoolPrivacyStmtFlag(String schoolPrivacyStmtFlag) {
		this.schoolPrivacyStmtFlag = schoolPrivacyStmtFlag;
	}

	public String getDistrictPrivacyStmtFlag() {
		return districtPrivacyStmtFlag;
	}

	public void setDistrictPrivacyStmtFlag(String districtPrivacyStmtFlag) {
		this.districtPrivacyStmtFlag = districtPrivacyStmtFlag;
	}

	public String getStatePrivacyStmtFlag() {
		return statePrivacyStmtFlag;
	}

	public void setStatePrivacyStmtFlag(String statePrivacyStmtFlag) {
		this.statePrivacyStmtFlag = statePrivacyStmtFlag;
	}
	
	public String getUnusedAreaIconPath() {
		return unusedAreaIconPath;
	}

	public void setUnusedAreaIconPath(String unusedAreaIconPath) {
		this.unusedAreaIconPath = unusedAreaIconPath;
	}

	public List<String> getGradePercentLevelCharts() {
		return gradePercentLevelCharts;
	}

	public void setGradePercentLevelCharts(List<String> gradePercentLevelCharts) {
		this.gradePercentLevelCharts = gradePercentLevelCharts;
	}

	public List<String> getGradeMedianCharts() {
		return gradeMedianCharts;
	}

	public void setGradeMedianCharts(List<String> gradeMedianCharts) {
		this.gradeMedianCharts = gradeMedianCharts;
	}

	public List<String> getAllGrades() {
		return allGrades;
	}

	public void setAllGrades(List<String> allGrades) {
		this.allGrades = allGrades;
	}

	public String getAxisPercentLevelChart() {
		return axisPercentLevelChart;
	}

	public void setAxisPercentLevelChart(String axisPercentLevelChart) {
		this.axisPercentLevelChart = axisPercentLevelChart;
	}
	public String getOnDemandWritingTaskScorePath() {
		return onDemandWritingTaskScorePath;
	}

	public void setOnDemandWritingTaskScorePath(
			String onDemandWritingTaskScorePath) {
		this.onDemandWritingTaskScorePath = onDemandWritingTaskScorePath;
	}
	
	public String getScoreStarPerformanceLevelPath() {
		return scoreStarPerformanceLevelPath;
	}

	public void setScoreStarPerformanceLevelPath(
			String scoreStarPerformanceLevelPath) {
		this.scoreStarPerformanceLevelPath = scoreStarPerformanceLevelPath;
	}
		
	public String getFooterNextPageArrowPath() {
		return footerNextPageArrowPath;
	}

	public void setFooterNextPageArrowPath(String footerNextPageArrowPath) {
		this.footerNextPageArrowPath = footerNextPageArrowPath;
	}

	public String getIconExceedsPath() {
		return iconExceedsPath;
	}

	public void setIconExceedsPath(String iconExceedsPath) {
		this.iconExceedsPath = iconExceedsPath;
	}

	public String getIconInsufficientPath() {
		return iconInsufficientPath;
	}

	public void setIconInsufficientPath(String iconInsufficientPath) {
		this.iconInsufficientPath = iconInsufficientPath;
	}

	public String getIconMeetsPath() {
		return iconMeetsPath;
	}

	public void setIconMeetsPath(String iconMeetsPath) {
		this.iconMeetsPath = iconMeetsPath;
	}

	public String getIconBelowPath() {
		return iconBelowPath;
	}

	public void setIconBelowPath(String iconBelowPath) {
		this.iconBelowPath = iconBelowPath;
	}
	
	public List<String> getMainPercentLevelCharts() {
		return mainPercentLevelCharts;
	}

	public void setMainPercentLevelCharts(List<String> mainPercentLevelCharts) {
		this.mainPercentLevelCharts = mainPercentLevelCharts;
	}

	public List<String> getCombinedPercentLevelCharts() {
		return combinedPercentLevelCharts;
	}

	public void setCombinedPercentLevelCharts(
			List<String> combinedPercentLevelCharts) {
		this.combinedPercentLevelCharts = combinedPercentLevelCharts;
	}

	public List<String> getMdptPercentLevelCharts() {
		return mdptPercentLevelCharts;
	}

	public void setMdptPercentLevelCharts(List<String> mdptPercentLevelCharts) {
		this.mdptPercentLevelCharts = mdptPercentLevelCharts;
	}


	public List<ReportStandardError> getStandardErrorList() {
		return standardErrorList;
	}

	public void setStandardErrorList(List<ReportStandardError> standardErrorList) {
		this.standardErrorList = standardErrorList;
	}

	public String getSummaryLevel() {
		return summaryLevel;
	}

	public void setSummaryLevel(String summaryLevel) {
		this.summaryLevel = summaryLevel;
	}

	public List<StudentReport> getDomainScoreList() {
		return domainScoreList;
	}

	public void setDomainScoreList(List<StudentReport> domainScoreList) {
		this.domainScoreList = domainScoreList;
	}

	public String getKelpaReportIntroParagraph() {
		return kelpaReportIntroParagraph;
	}

	public void setKelpaReportIntroParagraph(String kelpaReportIntroParagraph) {
		this.kelpaReportIntroParagraph = kelpaReportIntroParagraph;
	}

	public List<Category> getDomainPerformanceLevelCategoryLists() {
		return domainPerformanceLevelCategoryLists;
	}

	public void setDomainPerformanceLevelCategoryLists(
			List<Category> domainPerformanceLevelCategoryLists) {
		this.domainPerformanceLevelCategoryLists = domainPerformanceLevelCategoryLists;
	}

	public String getDomainNotTestedLabel() {
		return domainNotTestedLabel;
	}

	public void setDomainNotTestedLabel(String domainNotTestedLabel) {
		this.domainNotTestedLabel = domainNotTestedLabel;
	}

	public String getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(String progressStatus) {
		this.progressStatus = progressStatus;
	}
	
	public Boolean getIsIEState() {
		return isIEState;
	}

	public void setIsIEState(Boolean isIEState) {
		this.isIEState = isIEState;
	}

	public String getTestingCycleName1() {
		return testingCycleName1;
	}

	public void setTestingCycleName1(String testingCycleName1) {
		this.testingCycleName1 = testingCycleName1;
	}

	public String getTestingCycleName2() {
		return testingCycleName2;
	}

	public void setTestingCycleName2(String testingCycleName2) {
		this.testingCycleName2 = testingCycleName2;
	}

	
}
