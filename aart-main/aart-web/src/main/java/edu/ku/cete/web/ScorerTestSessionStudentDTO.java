package edu.ku.cete.web;

public class ScorerTestSessionStudentDTO {
	
	private Long studentId;
	private Long studentTestId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String stateStudentIdentifier;
	private Long enrollmentId;
	private String districtName;
	private String schoolName;
	private String abbreviatedName;
	private String roster;
	private Long testId;
	
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getLastFirstMiddleName(){
		return new StringBuilder("&nbsp; &nbsp; ").append(this.firstName).append(", ").append(this.lastName)
				.append(this.middleName != null && this.middleName.trim().length() > 0 ? ", "+this.middleName : "").toString();
		  
	}
	public Long getStudentTestId() {
		return studentTestId;
	}
	public void setStudentTestId(Long studentTestId) {
		this.studentTestId = studentTestId;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
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
	public String getAbbreviatedName() {
		return abbreviatedName;
	}
	public void setAbbreviatedName(String abbreviatedName) {
		this.abbreviatedName = abbreviatedName;
	}
	public String getRoster() {
		return roster;
	}
	public void setRoster(String roster) {
		this.roster = roster;
	}
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	
}
