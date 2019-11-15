package edu.ku.cete.domain.enrollment;

public class ViewMergeStudentDetailsDTO {

	//private int id;
	
	private int studentTestId;
	private String testName;
	private String stateStudentIdentifier;
	private String tcGradeCourseName;
	private String categoryCode;
	private boolean activeFlag;
	private String testSessionName;
	private String subject;
	private String testStatus;
	private long testSessionId;
	private String assessmentCode;
	
	
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public int getStudentTestId() {
		return studentTestId;
	}

	public void setStudentTestId(int studentTestId) {
		this.studentTestId = studentTestId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	
	public String getTcGradeCourseName() {
		return tcGradeCourseName;
	}

	public void setTcGradeCourseName(String tcGradeCourseName) {
		this.tcGradeCourseName = tcGradeCourseName;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public boolean isActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getTestSessionName() {
		return testSessionName;
	}

	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public long getTestSessionId() {
		return testSessionId;
	}

	public void setTestSessionId(long testSessionId) {
		this.testSessionId = testSessionId;
	}

	public String getAssessmentCode() {
		return assessmentCode;
	}

	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}
}
