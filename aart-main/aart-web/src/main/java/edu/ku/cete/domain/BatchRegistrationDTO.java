package edu.ku.cete.domain;

import edu.ku.cete.report.domain.BatchRegistration;

public class BatchRegistrationDTO extends BatchRegistration {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7900306251964693178L;
	private String gradeName;
	private String assessmentProgramName;
	private String testingProgramName;
	private String assessmentName;
	private String testTypeName;
	private String subjectName;
	private String report;
	private String contentAreaName;
	private String testWindowName;
	private String enrollmentMethodName;
	
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getAssessmentProgramName() {
		return assessmentProgramName;
	}
	public void setAssessmentProgramName(String assessmentProgramName) {
		this.assessmentProgramName = assessmentProgramName;
	}
	public String getTestingProgramName() {
		return testingProgramName;
	}
	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}
	public String getAssessmentName() {
		return assessmentName;
	}
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}
	public String getTestTypeName() {
		return testTypeName;
	}
	public void setTestTypeName(String testTypeName) {
		this.testTypeName = testTypeName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getReport() {
		return report;
	}
	public void setReport(String report) {
		this.report = report;
	}
	public String getContentAreaName() {
		return contentAreaName;
	}
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}
	public String getTestWindowName() {
		return testWindowName;
	}
	public void setTestWindowName(String testWindowName) {
		this.testWindowName = testWindowName;
	}
	public String getEnrollmentMethodName() {
		return enrollmentMethodName;
	}
	public void setEnrollmentMethodName(String enrollmentMethodName) {
		this.enrollmentMethodName = enrollmentMethodName;
	}
}
