package edu.ku.cete.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.report.TestCutScores;

public class ExternalStudentReportDTO extends Externalstudentreports {
	private String stateStudentIdentifier;
	private String legalFirstName;
	private String legalMiddleName;
	private String legalLastName;
    private String gradeName;
    private String subjectName;
    private String schoolName;
    private Long rosterId;
   	
    private String assessmentProgram; 
    private String schoolDisplayIdentifier; 
    private String districtDisplayIdentifier; 
    private String stateDisplayIdentifier; 
    private String gradeCode; 
    private String gradeLevel;
    private String contentAreaCode;    
    private String assessmentCode;    
    private String assessmentName;
    private Boolean completed;
    private String stateName;    
    private String achievementLevel;    
    private Long studentScore;    
    private BigDecimal standardError;    
    private BigDecimal stateStandardError;    
    private BigDecimal districtStandardError;    
    private BigDecimal allStateStandardError;    
    private Long districtAverageScore;    
    private Long stateAverageScore;
    private Long allStatesAvgScore;    
    private String reportCycle;    
    private String reportDate;    
    private Boolean generated;
    private String schoolShortName;   
    private String districtShortName;   
    
    private List<TestCutScores> testCutScores = new ArrayList<TestCutScores>();
    
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getLegalFirstName() {
		return legalFirstName;
	}
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	public String getLegalMiddleName() {
		return legalMiddleName;
	}
	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}
	public String getLegalLastName() {
		return legalLastName;
	}
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	public List<String> buildAllStudentReportSSIDSearchJsonRowForDLMOrCPASS() {
		List<String> cells = new ArrayList<String>();
		cells.add(""+this.getSchoolYear());
		cells.add(this.getSchoolName());
		cells.add(this.getSubjectName());
		cells.add(this.getGradeName());
		cells.add("");//empty for report link
		
		return cells;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getContentAreaCode() {
		return contentAreaCode;
	}
	public void setContentAreaCode(String contentAreaCode) {
		this.contentAreaCode = contentAreaCode;
	}
	public String getAssessmentCode() {
		return assessmentCode;
	}
	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}
	public Boolean getCompleted() {
		return completed;
	}
	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getAchievementLevel() {
		return achievementLevel;
	}
	public void setAchievementLevel(String achievementLevel) {
		this.achievementLevel = achievementLevel;
	}
	public Long getStudentScore() {
		return studentScore;
	}
	public void setStudentScore(Long studentScore) {
		this.studentScore = studentScore;
	}
	public BigDecimal getStandardError() {
		return standardError;
	}
	public void setStandardError(BigDecimal standardError) {
		this.standardError = standardError;
	}
	public BigDecimal getStateStandardError() {
		return stateStandardError;
	}
	public void setStateStandardError(BigDecimal stateStandardError) {
		this.stateStandardError = stateStandardError;
	}
	public BigDecimal getDistrictStandardError() {
		return districtStandardError;
	}
	public void setDistrictStandardError(BigDecimal districtStandardError) {
		this.districtStandardError = districtStandardError;
	}
	public BigDecimal getAllStateStandardError() {
		return allStateStandardError;
	}
	public void setAllStateStandardError(BigDecimal allStateStandardError) {
		this.allStateStandardError = allStateStandardError;
	}
	public Long getDistrictAverageScore() {
		return districtAverageScore;
	}
	public void setDistrictAverageScore(Long districtAverageScore) {
		this.districtAverageScore = districtAverageScore;
	}
	public Long getStateAverageScore() {
		return stateAverageScore;
	}
	public void setStateAverageScore(Long stateAverageScore) {
		this.stateAverageScore = stateAverageScore;
	}
	public Long getAllStatesAvgScore() {
		return allStatesAvgScore;
	}
	public void setAllStatesAvgScore(Long allStatesAvgScore) {
		this.allStatesAvgScore = allStatesAvgScore;
	}
	public String getReportCycle() {
		return reportCycle;
	}
	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public Boolean getGenerated() {
		return generated;
	}
	public void setGenerated(Boolean generated) {
		this.generated = generated;
	}
	public List<TestCutScores> getTestCutScores() {
		return testCutScores;
	}
	public void setTestCutScores(List<TestCutScores> testCutScores) {
		this.testCutScores = testCutScores;
	}
	public String getAssessmentProgram() {
		return assessmentProgram;
	}
	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
	public String getSchoolDisplayIdentifier() {
		return schoolDisplayIdentifier;
	}
	public void setSchoolDisplayIdentifier(String schoolDisplayIdentifier) {
		this.schoolDisplayIdentifier = schoolDisplayIdentifier;
	}
	public String getDistrictDisplayIdentifier() {
		return districtDisplayIdentifier;
	}
	public void setDistrictDisplayIdentifier(String districtDisplayIdentifier) {
		this.districtDisplayIdentifier = districtDisplayIdentifier;
	}
	public String getStateDisplayIdentifier() {
		return stateDisplayIdentifier;
	}
	public void setStateDisplayIdentifier(String stateDisplayIdentifier) {
		this.stateDisplayIdentifier = stateDisplayIdentifier;
	}
	public String getSchoolShortName() {
		return schoolShortName;
	}
	public void setSchoolShortName(String schoolShortName) {
		this.schoolShortName = schoolShortName;
	}
	public String getDistrictShortName() {
		return districtShortName;
	}
	public void setDistrictShortName(String districtShortName) {
		this.districtShortName = districtShortName;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public String getAssessmentName() {
		return assessmentName;
	}
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}
	
}