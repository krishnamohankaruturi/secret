package edu.ku.cete.web;


/**
 * @author sureshmuthu
 * This is for ticket pdf generation in manage test session
 */
public class TestSessionPdfDTO {

	private long testSessionId;
	
	private String stateStudentIdentifier;
	
	private long studentTestId;
	
	private String testSessionName;
	
	private String educatorUserName;
	
	private String educatorFirstName;
	
	private String educatorLastName;
	
	private String rosterName;
	
	private String testCollectionName;
	
	private String testName;
	
	private String randomizationType; 
	
	private String studentFirstName;
	
	private String studentLastName;
	
	private String studentUserName;
	
	private String studentPassword;
	
	private String testSectionName;
	
	private String ticketNumber;
	
	private String assessmentProgramName;
	
	private String testSectionTicketNumber;
	
	private Long testSectionCount;
	
	private Boolean hardBreak;	
	
	private Integer sectionOrder;
	
	private String groupingindicator1;
	
	private String groupingindicator2;
	
	/**
	 * @return String
	 */
	public String getGroupingindicator1() {
		return groupingindicator1;
	}
	
	/**
	 * @param groupingindicator1
	 */
	public void setGroupingindicator1(String groupingindicator1) {
		this.groupingindicator1 = groupingindicator1;
	}
	
	/**
	 * @return String
	 */
	public String getGroupingindicator2() {
		return groupingindicator2;
	}
	
	/**
	 * @param groupingindicator2
	 */
	public void setGroupingindicator2(String groupingindicator2) {
		this.groupingindicator2 = groupingindicator2;
	}

	/**
	 * @return long
	 */
	public long getTestSessionId() {
		return testSessionId;
	}

	/**
	 * @param testSessionId {@link long}
	 */
	public void setTestSessionId(long testSessionId) {
		this.testSessionId = testSessionId;
	}
	
	/**
	 * @return String
	 */
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	/**
	 * @param {@link String}
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	/**
	 * @return long
	 */
	public long getStudentTestId() {
		return studentTestId;
	}

	/**
	 * @param studentTestId {@link long}
	 */	
	public void setStudentTestId(long studentTestId) {
		this.studentTestId = studentTestId;
	}

	/**
	 * @return {@link String}
	 */
	public String getTestSessionName() {
		return testSessionName;
	}

	/**
	 * @param testSessionName {@link String}
	 */
	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}
	 
	/**
	 * @return {@link String}
	 */
	public String getRosterName() {
		return rosterName;
	}

	/**
	 * @param rosterName {@link String}
	 */
	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}

	/**
	 * @return {@link String}
	 */
	public String getTestCollectionName() {
		return testCollectionName;
	}

	/**
	 * @param testCollectionName {@link String}
	 */
	public void setTestCollectionName(String testCollectionName) {
		this.testCollectionName = testCollectionName;
	}

	/**
	 * @return {@link String}
	 */
	public String getRandomizationType() {
		return randomizationType;
	}

	/**
	 * @param randomizationType {@link String}
	 */
	public void setRandomizationType(String randomizationType) {
		this.randomizationType = randomizationType;
	}

	/**
	 * @return {@link String}
	 */
	public String getEducatorUserName() {
		return educatorUserName;
	}

	/**
	 * @param educatorUserName {@link String}
	 */
	public void setEducatorUserName(String educatorUserName) {
		this.educatorUserName = educatorUserName;
	}

	/**
	 * @return {@link String}
	 */
	public String getEducatorFirstName() {
		return educatorFirstName;
	}

	/**
	 * @param educatorFirstName {@link String}
	 */
	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}

	/**
	 * @return {@link String}
	 */
	public String getEducatorLastName() {
		return educatorLastName;
	}

	/**
	 * @param educatorLastName {@link String}
	 */
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}

	/**
	 * @return {@link String}
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * @param testName {@link String}
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

	/**
	 * @return {@link String}
	 */
	public String getStudentFirstName() {
		return studentFirstName;
	}

	/**
	 * @param studentFirstName {@link String}
	 */
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	/**
	 * @return {@link String}
	 */
	public String getStudentLastName() {
		return studentLastName;
	}

	/**
	 * @param studentLastName {@link String}
	 */
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	/**
	 * @return {@link String}
	 */
	public String getStudentUserName() {
		return studentUserName;
	}

	/**
	 * @param studentUserName {@link String}
	 */
	public void setStudentUserName(String studentUserName) {
		this.studentUserName = studentUserName;
	}

	/**
	 * @return {@link String}
	 */
	public String getStudentPassword() {
		return studentPassword;
	}

	/**
	 * @param studentPassword {@link String}
	 */
	public void setStudentPassword(String studentPassword) {
		this.studentPassword = studentPassword;
	}

	/**
	 * @return {@link String}
	 */
	public String getTestSectionName() {
		return testSectionName;
	}

	/**
	 * @param testSectionName {@link String}
	 */
	public void setTestSectionName(String testSectionName) {
		this.testSectionName = testSectionName;
	}

	/**
	 * @return {@link String}
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}

	/**
	 * @param ticketNumber {@link String}
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @return {@link String}
	 */
	public String getAssessmentProgramName() {
		return assessmentProgramName;
	}

	/**
	 * @param assessmentProgramName {@link String}
	 */
	public void setAssessmentProgramName(String assessmentProgramName) {
		this.assessmentProgramName = assessmentProgramName;
	}

	/**
	 * @return {@link String}
	 */
	public String getTestSectionTicketNumber() {
		return testSectionTicketNumber;
	}

	/**
	 * @param testSectionTicketNumber {@link String}
	 */
	public void setTestSectionTicketNumber(String testSectionTicketNumber) {
		this.testSectionTicketNumber = testSectionTicketNumber;
	}

	/**
	 * @return long
	 */	
	public Long getTestSectionCount() {
		return testSectionCount;
	}

	/**
	 * @param testSectionCount {@link long}
	 */	
	public void setTestSectionCount(Long testSectionCount) {
		this.testSectionCount = testSectionCount;
	}

	public Boolean getHardBreak() {
		return hardBreak;
	}

	public void setHardBreak(Boolean hardBreak) {
		this.hardBreak = hardBreak;
	}

	public Integer getSectionOrder() {
		return sectionOrder;
	}

	public void setSectionOrder(Integer sectionOrder) {
		this.sectionOrder = sectionOrder;
	}
	
}
