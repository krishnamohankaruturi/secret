package edu.ku.cete.report.domain;

import edu.ku.cete.domain.audit.AuditableDomain;

public class ExternalUploadResult extends AuditableDomain{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5104662221734969947L;
	private String 	reportCycle;
	private Long	reportCycleId;
	private String 	grade;
	private Long	gradeId;
	private String 	subject;
	private Long	subjectId;
	private String 	testType;
	private String  assessmentName;
	private String 	schoolIdentifier;
	private Long 	schoolInternalId;
	private String 	districtIdentifier;
	private Long	districtInternalId;
	private String 	state;
	private Long	stateInternalId;
	private Long 	schoolYear;
	private String  lineNumber;
	private Long   batchUploadedId;
	private String reportType;
	private String schoolName;
	private String districtName;
	private String testingProgram;
	private Long testingProgramInternalId;
	private String uploadLevel; //student Level or Organization Level
	/**
	 * @return the reportCycle
	 */
	public String getReportCycle() {
		return reportCycle;
	}
	/**
	 * @param reportCycle the reportCycle to set
	 */
	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}
	/**
	 * @return the reportCycleId
	 */
	public Long getReportCycleId() {
		return reportCycleId;
	}
	/**
	 * @param reportCycleId the reportCycleId to set
	 */
	public void setReportCycleId(Long reportCycleId) {
		this.reportCycleId = reportCycleId;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the gradeId
	 */
	public Long getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * @return the assessmentCode
	 */
	public String getTestType() {
		return testType;
	}
	/**
	 * @param assessmentCode the assessmentCode to set
	 */
	public void setTestType(String assessmentCode) {
		this.testType = assessmentCode;
	}
	/**
	 * @return the schoolIdentifier
	 */
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	/**
	 * @param schoolIdentifier the schoolIdentifier to set
	 */
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	/**
	 * @return the schoolInternalId
	 */
	public Long getSchoolInternalId() {
		return schoolInternalId;
	}
	/**
	 * @param schoolInternalId the schoolInternalId to set
	 */
	public void setSchoolInternalId(Long schoolInternalId) {
		this.schoolInternalId = schoolInternalId;
	}
	/**
	 * @return the districtIdentifier
	 */
	public String getDistrictIdentifier() {
		return districtIdentifier;
	}
	/**
	 * @param districtIdentifier the districtIdentifier to set
	 */
	public void setDistrictIdentifier(String districtIdentifier) {
		this.districtIdentifier = districtIdentifier;
	}
	/**
	 * @return the districtInternalId
	 */
	public Long getDistrictInternalId() {
		return districtInternalId;
	}
	/**
	 * @param districtInternalId the districtInternalId to set
	 */
	public void setDistrictInternalId(Long districtInternalId) {
		this.districtInternalId = districtInternalId;
	}
	/**
	 * @return the stateId
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setState(String stateId) {
		this.state = stateId;
	}
	/**
	 * @return the stateInternalId
	 */
	public Long getStateInternalId() {
		return stateInternalId;
	}
	/**
	 * @param stateInternalId the stateInternalId to set
	 */
	public void setStateInternalId(Long stateInternalId) {
		this.stateInternalId = stateInternalId;
	}
	/**
	 * @return the schoolYear
	 */
	public Long getSchoolYear() {
		return schoolYear;
	}
	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public Long getBatchUploadedId() {
		return batchUploadedId;
	}
	public void setBatchUploadedId(Long batchUploadedId) {
		this.batchUploadedId = batchUploadedId;
	}
	public String getAssessmentName() {
		return assessmentName;
	}
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getUploadLevel() {
		return uploadLevel;
	}
	public void setUploadLevel(String uploadLevel) {
		this.uploadLevel = uploadLevel;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public Long getTestingProgramInternalId() {
		return testingProgramInternalId;
	}
	public void setTestingProgramInternalId(Long testingProgramInternalId) {
		this.testingProgramInternalId = testingProgramInternalId;
	}
	public String getTestingProgram() {
		return testingProgram;
	}
	public void setTestingProgram(String testingProgram) {
		this.testingProgram = testingProgram;
	}
	
}
