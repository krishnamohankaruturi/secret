package edu.ku.cete.model;
/* sudhansu.b
 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
 */	
public class AutoScoringStudentstest {

	private Long rosterId;
	private Long StudentstestId;
	private Long studentId;
	private Long enrollmentId;
	private String rosterName;
	private String educatorIdentifier;
	private Long testId;
	private Long ScorerId;
	private String testSessionName;
	private Long testSessionId;
	private String aypSchoolIdentifier;
	
	public String getAypSchoolIdentifier() {
		return aypSchoolIdentifier;
	}

	public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
		this.aypSchoolIdentifier = aypSchoolIdentifier;
	}

	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}

	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
	}

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	public Long getStudentstestId() {
		return StudentstestId;
	}

	public void setStudentstestId(Long studentstestId) {
		StudentstestId = studentstestId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getRosterName() {
		return rosterName;
	}

	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Long getScorerId() {
		return ScorerId;
	}

	public void setScorerId(Long scorerId) {
		ScorerId = scorerId;
	}

	public String getTestSessionName() {
		return testSessionName;
	}

	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}

	public Long getTestSessionId() {
		return testSessionId;
	}

	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}
}
