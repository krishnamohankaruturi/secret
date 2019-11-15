package edu.ku.cete.web;

/**
 * 
 * @author Venkata Krishna Jagarlamudi
 * US15586 : DLM Test Administration Data Extract
 */

public class DLMTestStatusExtractDTO {
	private Long studentId;
	private Long subjectId;
	private String subjectName;
	private String subjectAbbreviatedName;
	private String stateName;
	private Long districtId;
	private String districtIdentifier;
	private String districtName;
	private String schoolIdentifier;
	private String schoolName;
	private String educatorLastName;
	private String educatorFirstName;
	private String educatorId;
	private String grade;
	private Long gradeId;
	private String studentFirstName;
	private String studentLaststName;
	private String stateStudentId;
	private String localStudentId;
	private int instTestsNotStarted;
	private int instTestsInProgress;
	private int instTestscompleted;
	private int eoyTestsNotStarted;
	private int eoyTestsInProgress;
	private int eoytTestscompleted;
	private int fieldTestsCompleted;
	private String stateModel;
	private int numofEEs;
	private Long attendanceSchoolId;
	private boolean itiContentAvailable;
	private String gradeLevel;
	private String testingCycleName;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getSubjectAbbreviatedName() {
		return subjectAbbreviatedName;
	}
	public void setSubjectAbbreviatedName(String subjectAbbreviatedName) {
		this.subjectAbbreviatedName = subjectAbbreviatedName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictIdentifier() {
		return districtIdentifier;
	}
	public void setDistrictIdentifier(String districtIdentifier) {
		this.districtIdentifier = districtIdentifier;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
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
	public String getEducatorId() {
		return educatorId;
	}
	public void setEducatorId(String educatorId) {
		this.educatorId = educatorId;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLaststName() {
		return studentLaststName;
	}
	public void setStudentLaststName(String studentLaststName) {
		this.studentLaststName = studentLaststName;
	}
	public String getStateStudentId() {
		return stateStudentId;
	}
	public void setStateStudentId(String stateStudentId) {
		this.stateStudentId = stateStudentId;
	}
	public String getLocalStudentId() {
		return localStudentId;
	}
	public void setLocalStudentId(String localStudentId) {
		this.localStudentId = localStudentId;
	}
	public int getInstTestsNotStarted() {
		return instTestsNotStarted;
	}
	public void setInstTestsNotStarted(int instTestsNotStarted) {
		this.instTestsNotStarted = instTestsNotStarted;
	}
	public int getInstTestsInProgress() {
		return instTestsInProgress;
	}
	public void setInstTestsInProgress(int instTestsInProgress) {
		this.instTestsInProgress = instTestsInProgress;
	}
	public int getInstTestscompleted() {
		return instTestscompleted;
	}
	public void setInstTestscompleted(int instTestscompleted) {
		this.instTestscompleted = instTestscompleted;
	}
	public int getEoyTestsNotStarted() {
		return eoyTestsNotStarted;
	}
	public void setEoyTestsNotStarted(int eoyTestsNotStarted) {
		this.eoyTestsNotStarted = eoyTestsNotStarted;
	}
	public int getEoyTestsInProgress() {
		return eoyTestsInProgress;
	}
	public void setEoyTestsInProgress(int eoyTestsInProgress) {
		this.eoyTestsInProgress = eoyTestsInProgress;
	}
	public int getEoytTestscompleted() {
		return eoytTestscompleted;
	}
	public void setEoytTestscompleted(int eoytTestscompleted) {
		this.eoytTestscompleted = eoytTestscompleted;
	}
	public int getFieldTestsCompleted() {
		return fieldTestsCompleted;
	}
	public void setFieldTestsCompleted(int fieldTestsCompleted) {
		this.fieldTestsCompleted = fieldTestsCompleted;
	}
	public String getStateModel() {
		return stateModel;
	}
	public void setStateModel(String stateModel) {
		this.stateModel = stateModel;
	}
	public int getNumofEEs() {
		return numofEEs;
	}
	public void setNumofEEs(int numofEEs) {
		this.numofEEs = numofEEs;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public boolean isItiContentAvailable() {
		return itiContentAvailable;
	}
	public void setItiContentAvailable(boolean itiContentAvailable) {
		this.itiContentAvailable = itiContentAvailable;
	}
	public String getTestingCycleName() {
		return testingCycleName;
	}
	public void setTestingCycleName(String testingCycleName) {
		this.testingCycleName = testingCycleName;
	}	
}
