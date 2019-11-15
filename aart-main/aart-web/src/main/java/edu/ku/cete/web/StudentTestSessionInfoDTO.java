package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Venkata Krishna Jagarlamudi
 * US15630: Data extract - Test administration for KAP and AMP
 * US15741: Data Extract - Test Tickets for KAP and AMP
 * Common for both extracts.
 *
 */
public class StudentTestSessionInfoDTO {
	
	private String state;
	private String district;
	private String schoolName;
	private String schoolIdentifier;
	private Long attendanceSchoolId;
	
	private String grdae;
	private String grouping1;
	private String grouping2;
	private String subject;
	
	private String testSessionName;
	private String testCollectionName;
	private String testSatus;
	private Long studentTestId;
	
	private String specialCircumstances;
	private String specialCircumstanceStatus;
	private Date lastReactivatedDate;
	private String stage;
	
	private String studentFirstName;	
	private String studentLastName;
	private String studentMiddleName;
	private String stateStudentIdentifier;
	private String localStudentIdentifier;
	private String studentLoginUserName;
	private String studentLoginPassword;
	
	private String rosterName;
	private String educatorFirstName;
	private String educatorLastName;
	private String educatorIdentifier;
	private Date schoolEntryDate;

	
	public String getSpecialCircumstanceStatus() {
		return specialCircumstanceStatus;
	}

	public void setSpecialCircumstanceStatus(String specialCircumstanceStatus) {
		this.specialCircumstanceStatus = specialCircumstanceStatus;
	}
	private List<TestAdminPartDetails> partsDetails = new ArrayList<TestAdminPartDetails>();
	
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}
	
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}
	
	/**
	 * @return the partsDetails
	 */
	public List<TestAdminPartDetails> getPartsDetails() {
		return partsDetails;
	}
	/**
	 * @param partsDetails the partsDetails to set
	 */
	public void setPartsDetails(List<TestAdminPartDetails> partsDetails) {
		this.partsDetails = partsDetails;
	}
	/**
	 * @return the rosterName
	 */
	public String getRosterName() {
		return rosterName;
	}
	/**
	 * @param rosterName the rosterName to set
	 */
	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}
	/**
	 * @return the educatorFirstName
	 */
	public String getEducatorFirstName() {
		return educatorFirstName;
	}
	/**
	 * @param educatorFirstName the educatorFirstName to set
	 */
	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}
	/**
	 * @return the educatorLastName
	 */
	public String getEducatorLastName() {
		return educatorLastName;
	}
	/**
	 * @param educatorLastName the educatorLastName to set
	 */
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}
	/**
	 * @return the educatotIdentifier
	 */
	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}
	/**
	 * @param educatorIdentifier the educatotIdentifier to set
	 */
	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}
	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * @return the schoolIdentifier
	 */
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	/**
	 * @param schoolIdentifier the schoolIdentifier to set
	 */
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	/**
	 * @return the grdae
	 */
	public String getGrdae() {
		return grdae;
	}
	/**
	 * @param grdae the grdae to set
	 */
	public void setGrdae(String grdae) {
		this.grdae = grdae;
	}
	/**
	 * @return the grouping1
	 */
	public String getGrouping1() {
		return grouping1;
	}
	/**
	 * @param grouping1 the grouping1 to set
	 */
	public void setGrouping1(String grouping1) {
		this.grouping1 = grouping1;
	}
	/**
	 * @return the grouping2
	 */
	public String getGrouping2() {
		return grouping2;
	}
	/**
	 * @param grouping2 the grouping2 to set
	 */
	public void setGrouping2(String grouping2) {
		this.grouping2 = grouping2;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the testSessionName
	 */
	public String getTestSessionName() {
		return testSessionName;
	}
	/**
	 * @param testSessionName the testSessionName to set
	 */
	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}
	/**
	 * @return the testCollectionName
	 */
	public String getTestCollectionName() {
		return testCollectionName;
	}
	/**
	 * @param testCollectionName the testCollectionName to set
	 */
	public void setTestCollectionName(String testCollectionName) {
		this.testCollectionName = testCollectionName;
	}
	/**
	 * @return the testSatus
	 */
	public String getTestSatus() {
		return testSatus;
	}
	/**
	 * @param testSatus the testSatus to set
	 */
	public void setTestSatus(String testSatus) {
		this.testSatus = testSatus;
	}
	/**
	 * @return the studentTestId
	 */
	public Long getStudentTestId() {
		return studentTestId;
	}
	/**
	 * @param studentTestId the studentTestId to set
	 */
	public void setStudentTestId(Long studentTestId) {
		this.studentTestId = studentTestId;
	}
	/**
	 * @return the specialCircumstances
	 */
	public String getSpecialCircumstances() {
		return specialCircumstances;
	}
	/**
	 * @param specialCircumstances the specialCircumstances to set
	 */
	public void setSpecialCircumstances(String specialCircumstances) {
		this.specialCircumstances = specialCircumstances;
	}
	/**
	 * @return the lastReactivatedDate
	 */
	public Date getLastReactivatedDate() {
		return lastReactivatedDate;
	}
	/**
	 * @param lastReactivatedDate the lastReactivatedDate to set
	 */
	public void setLastReactivatedDate(Date lastReactivatedDate) {
		this.lastReactivatedDate = lastReactivatedDate;
	}
	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}
	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}
	/**
	 * @return the studentFirstName
	 */
	public String getStudentFirstName() {
		return studentFirstName;
	}
	/**
	 * @param studentFirstName the studentFirstName to set
	 */
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	/**
	 * @return the studentLastName
	 */
	public String getStudentLastName() {
		return studentLastName;
	}
	/**
	 * @param studentLastName the studentLastName to set
	 */
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	/**
	 * @return the studentMiddleName
	 */
	public String getStudentMiddleName() {
		return studentMiddleName;
	}
	/**
	 * @param studentMiddleName the studentMiddleName to set
	 */
	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}
	/**
	 * @return the stateStudentIdentifier
	 */
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	/**
	 * @param stateStudentIdentifier the stateStudentIdentifier to set
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	/**
	 * @return the localStudentIdentifier
	 */
	public String getLocalStudentIdentifier() {
		return localStudentIdentifier;
	}
	/**
	 * @param localStudentIdentifier the localStudentIdentifier to set
	 */
	public void setLocalStudentIdentifier(String localStudentIdentifier) {
		this.localStudentIdentifier = localStudentIdentifier;
	}
	/**
	 * @return the studentLoginUserName
	 */
	public String getStudentLoginUserName() {
		return studentLoginUserName;
	}
	/**
	 * @param studentLoginUserName the studentLoginUserName to set
	 */
	public void setStudentLoginUserName(String studentLoginUserName) {
		this.studentLoginUserName = studentLoginUserName;
	}
	/**
	 * @return the studentLoginPassword
	 */
	public String getStudentLoginPassword() {
		return studentLoginPassword;
	}
	/**
	 * @param studentLoginPassword the studentLoginPassword to set
	 */
	public void setStudentLoginPassword(String studentLoginPassword) {
		this.studentLoginPassword = studentLoginPassword;
	}

	public Date getSchoolEntryDate() {
		return schoolEntryDate;
	}

	public void setSchoolEntryDate(Date schoolEntryDate) {
		this.schoolEntryDate = schoolEntryDate;
	}
		
}
