package edu.ku.cete.web;

public class MonitorCcqScorersDetailsDTO {
	private Long studentId;
	private Long scorerId;
	private String lastName;
	private String firstName;
	private String scored;
	private String districtName;
	private String schoolName;
	private String testingProgramName;
	private String stateStudentIdentifier;
	private Long scoringAssignmentStudentId;
	private Long scoringAssignmentScorerId;
	private String scorerFirstName;
	private String scorerLastName;
	
	public Long getScoringAssignmentStudentId() {
		return scoringAssignmentStudentId;
	}
	public void setScoringAssignmentStudentId(Long scoringAssignmentStudentId) {
		this.scoringAssignmentStudentId = scoringAssignmentStudentId;
	}
	public Long getScoringAssignmentScorerId() {
		return scoringAssignmentScorerId;
	}
	public void setScoringAssignmentScorerId(Long scoringAssignmentScorerId) {
		this.scoringAssignmentScorerId = scoringAssignmentScorerId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getScorerId() {
		return scorerId;
	}
	public void setScorerId(Long scorerId) {
		this.scorerId = scorerId;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getScored() {
		return scored;
	}
	public void setScored(String scored) {
		this.scored = scored;
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
	public String getTestingProgramName() {
		return testingProgramName;
	}
	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getScorerFirstName() {
		return scorerFirstName;
	}
	public void setScorerFirstName(String scorerFirstName) {
		this.scorerFirstName = scorerFirstName;
	}
	public String getScorerLastName() {
		return scorerLastName;
	}
	public void setScorerLastName(String scorerLastName) {
		this.scorerLastName = scorerLastName;
	}	
}
