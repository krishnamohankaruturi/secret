package edu.ku.cete.web;

import java.util.Date;

public class QuestarExtractDTO {
	private String stateName;
	private String districtName;
	private String subjectAreaName;
	private String subjectAreaCode;
	private String schoolName;
	private String gradeLevel;
	private String studentLastName;
	private String studentFirstName;
	private String studentMiddleName;
	private Long studentId;
	private String stateStudentIdentifier;
	private Date studentDateOfBirth;
	private String testTypeCode;
	
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
	public String getSubjectAreaName() {
		return subjectAreaName;
	}
	public void setSubjectAreaName(String subjectAreaName) {
		this.subjectAreaName = subjectAreaName;
	}
	public String getSubjectAreaCode() {
		return subjectAreaCode;
	}
	public void setSubjectAreaCode(String subjectAreaCode) {
		this.subjectAreaCode = subjectAreaCode;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
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
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public Date getStudentDateOfBirth() {
		return studentDateOfBirth;
	}
	public void setStudentDateOfBirth(Date studentDateOfBirth) {
		this.studentDateOfBirth = studentDateOfBirth;
	}
	public String getTestTypeCode() {
		return testTypeCode;
	}
	public void setTestTypeCode(String testTypeCode) {
		this.testTypeCode = testTypeCode;
	}
}