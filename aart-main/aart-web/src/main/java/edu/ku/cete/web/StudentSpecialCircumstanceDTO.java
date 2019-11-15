package edu.ku.cete.web;

import java.util.Date;

import edu.ku.cete.domain.StudentSpecialCircumstance;

public class StudentSpecialCircumstanceDTO extends StudentSpecialCircumstance {

	private static final long serialVersionUID = 1L;

	private String stateName;
	private String districtName;
	private String schoolName;
	private String educatorLastName;
	private String educatorFirstName;
	private String studentLastName;
	private String studentFirstName;
	private String studentMiddleName;
	private String stateStudentIdentifier;
	private String assessmentProgram;
	private String subjectName;
	private String testSessionName;
	private String scCodeDescription;
	private String cedsCodeNumber;
	private String stateCodeNumber;
	private String approvalStatus;
	private String approverLastName;
	private String approverFirstName;
	private Date approvalDateTime;

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getEducatorLastName() {
		return educatorLastName;
	}

	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}

	public String getEducatorFirstName() {
		return educatorFirstName;
	}

	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentMiddleName() {
		return studentMiddleName;
	}

	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}

	public String getTestSessionName() {
		return testSessionName;
	}

	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}

	public String getScCodeDescription() {
		return scCodeDescription;
	}

	public void setScCodeDescription(String scCodeDescription) {
		this.scCodeDescription = scCodeDescription;
	}

	public String getCedsCodeNumber() {
		return cedsCodeNumber;
	}

	public void setCedsCodeNumber(String cedsCodeNumber) {
		this.cedsCodeNumber = cedsCodeNumber;
	}

	public String getStateCodeNumber() {
		return stateCodeNumber;
	}

	public void setStateCodeNumber(String stateCodeNumber) {
		this.stateCodeNumber = stateCodeNumber;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApproverLastName() {
		return approverLastName;
	}

	public void setApproverLastName(String approverLastName) {
		this.approverLastName = approverLastName;
	}

	public String getApproverFirstName() {
		return approverFirstName;
	}

	public void setApproverFirstName(String approverFirstName) {
		this.approverFirstName = approverFirstName;
	}

	public Date getApprovalDateTime() {
		return approvalDateTime;
	}

	public void setApprovalDateTime(Date approvalDateTime) {
		this.approvalDateTime = approvalDateTime;
	}

}
