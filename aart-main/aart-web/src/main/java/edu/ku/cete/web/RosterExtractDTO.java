package edu.ku.cete.web;

import java.util.Date;

public class RosterExtractDTO {
	private Long rosterId;
    private String state;
    private String district;
    private String school;
    private Date modifiedDate;
    private Long modifiedUser;
    private String rosterName;
    private String contentAreaName;
    private String gradeCourseName;
    private String schoolIdentifier;
    private Integer currentSchoolYear;
    private String stateStudentIdentifier;
    private String localStudentIdentifier;
    private String studentFirstName;
    private String studentLastName;
    private String teacherId;
    private String teacherFirstName;
    private String teacherLastName;
    private String lastModifiedEnrlUserName;
    private String studentCurrentGrade;
    /*
	 * added for US-18883
	 */
    private String studentAssesmentPrograms;
   
    
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
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
	public String getRosterName() {
		return rosterName;
	}
	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}
	public String getContentAreaName() {
		return contentAreaName;
	}
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}
	public String getGradeCourseName() {
		return gradeCourseName;
	}
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	public Integer getCurrentSchoolYear() {
		return currentSchoolYear;
	}
	public void setCurrentSchoolYear(Integer currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getLocalStudentIdentifier() {
		return localStudentIdentifier;
	}
	public void setLocalStudentIdentifier(String localStudentIdentifier) {
		this.localStudentIdentifier = localStudentIdentifier;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherFirstName() {
		return teacherFirstName;
	}
	public void setTeacherFirstName(String teacherFirstName) {
		this.teacherFirstName = teacherFirstName;
	}
	public String getTeacherLastName() {
		return teacherLastName;
	}
	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public String getLastModifiedEnrlUserName() {
		return lastModifiedEnrlUserName;
	}
	public void setLastModifiedEnrlUserName(String lastModifiedEnrlUserName) {
		this.lastModifiedEnrlUserName = lastModifiedEnrlUserName;
	}
	public String getStudentAssesmentPrograms() {
		return studentAssesmentPrograms;
	}
	public void setStudentAssesmentPrograms(String studentAssesmentPrograms) {
		this.studentAssesmentPrograms = studentAssesmentPrograms;
	}
	public String getStudentCurrentGrade() {
		return studentCurrentGrade;
	}
	public void setStudentCurrentGrade(String studentCurrentGrade) {
		this.studentCurrentGrade = studentCurrentGrade;
	}
	
}