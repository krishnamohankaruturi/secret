package edu.ku.cete.web;

public class TECExtractDTO {
	private String state;
	private String district;
	private String school;
	private String stateStudentIdentifier;
	private String attendanceSchoolIdentifier;
	private String testTypeCode;
	private String subjectCode;
	private Integer currentSchoolYear;
	//Added for US18891
	private String grade;
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getAttendanceSchoolIdentifier() {
		return attendanceSchoolIdentifier;
	}
	public void setAttendanceSchoolIdentifier(String attendanceSchoolIdentifier) {
		this.attendanceSchoolIdentifier = attendanceSchoolIdentifier;
	}
	public String getTestTypeCode() {
		return testTypeCode;
	}
	public void setTestTypeCode(String testTypeCode) {
		this.testTypeCode = testTypeCode;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public Integer getCurrentSchoolYear() {
		return currentSchoolYear;
	}
	public void setCurrentSchoolYear(Integer currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}
}