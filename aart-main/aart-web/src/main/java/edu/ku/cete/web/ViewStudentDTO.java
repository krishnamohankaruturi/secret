package edu.ku.cete.web;

import java.util.Date;

import edu.ku.cete.domain.student.Student;


public class ViewStudentDTO extends Student{

	private static final long serialVersionUID = 1L;
	private String programName;
	private String localStudentIdentifiers;
	private String attendanceSchoolDisplayIdentifiers;
	private String attendanceSchoolNames;
	private String rosterIds;
	private String rosterNames;
	private String currentSchoolYears;
	private String residenceDistrictIdentifiers;
	private String genderString;
	private String accessProfileStatus;
	private String status;
	private String gradeCourseId;
	private String gradeCourseName;
	
	private String schoolEntryDate;
	private String districtEntryDate;
	private String stateEntryDate;
	private Boolean giftedStudent;
	
	private String stateName;
	private String districtName;
	private String schoolName;
	private String schoolId;
	private String aypSchoolId;
	private String firstLanguageCode;
	
	private String subjectName;
	private String courseName;
	private String educatorName;
	private String rosterName;
	
	private Date enrlModifiedDate;
	private Long enrlModifiedUser;
	
	private Integer totalRecords;
	
	/**
	 *  US16241 Added Fields
	 */
	private String hispanicEthnicity;
	private String esolParticipationCode;
	
	
	/**
	 * Added for US16773
	 */
	  private String testTypeCode;
	  private String subjectCode;
	  private String subjectCodeId;
	  private String testTypeName;
	  /**
	   * Added during US16942
	   */
	  private String aypSchoolNames;
	  
	  private String groupingIndicator1;
	  private String groupingIndicator2;	  
	
	  private String lastModifiedEnrlUserName;
	  private String accountabilityDistrictIdentifier;
	  private String accountabilityDistrictName;
	  
	  private Date extractSchoolEntryDate;
	  private Date extractDistrictEntryDate;
	  private Date extractStateEntryDate;
	  
	  
	public String getGroupingIndicator1() {
		return groupingIndicator1 == null ? "" : groupingIndicator1;
	}
	public void setGroupingIndicator1(String groupingIndicator1) {
		this.groupingIndicator1 = groupingIndicator1;
	}
	public String getGroupingIndicator2() {
		return groupingIndicator2 == null ? "" : groupingIndicator2;
	}
	public void setGroupingIndicator2(String groupingIndicator2) {
		this.groupingIndicator2 = groupingIndicator2;
	}
	public String getHispanicEthnicity() {
		return hispanicEthnicity;
	}
	public void setHispanicEthnicity(String hispanicEthnicity) {
		if(hispanicEthnicity.equalsIgnoreCase("Yes")||hispanicEthnicity.equalsIgnoreCase("1")||hispanicEthnicity.equalsIgnoreCase("true")) {	
			this.hispanicEthnicity = "Yes";
		}
		else if(hispanicEthnicity.equalsIgnoreCase("No")||hispanicEthnicity.equalsIgnoreCase("0")||hispanicEthnicity.equalsIgnoreCase("false")) {
			this.hispanicEthnicity = "No";
		}
		else {
			this.hispanicEthnicity = hispanicEthnicity;
		}
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getSubjectCodeId() {
		return subjectCodeId;
	}
	public void setSubjectCodeId(String subjectCodeId) {
		this.subjectCodeId = subjectCodeId;
	}
	/**
	 * @return the esolParticipationCode
	 */
	public String getEsolParticipationCode() {
		return esolParticipationCode;
	}
	/**
	 *  @param esolParticipationCode the esolParticipationCode to set
	 * 
	 */
	public void setEsolParticipationCode(String esolParticipationCode) {
		this.esolParticipationCode = esolParticipationCode;
	}
	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName == null ? "" : subjectName;
	}
	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * @return the courseName
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * @param courseName the courseName to set
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * @return the educatorName
	 */
	public String getEducatorName() {
		return educatorName;
	}
	/**
	 * @param educatorName the educatorName to set
	 */
	public void setEducatorName(String educatorName) {
		this.educatorName = educatorName;
	}
	/**
	 * @return the rosterName
	 */
	public String getRosterName() {
		return rosterName == null ? "" : rosterName;
	}
	/**
	 * @param rosterName the rosterName to set
	 */
	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}
	/**
	 * @return the firstLanguageCode
	 */
	public String getFirstLanguageCode() {
		return firstLanguageCode;
	}
	/**
	 * @param firstLanguageCode the firstLanguageCode to set
	 */
	public void setFirstLanguageCode(String firstLanguageCode) {
		this.firstLanguageCode = firstLanguageCode;
	}
	/**
	 * @return the aypSchoolId
	 */
	public String getAypSchoolId() {
		return aypSchoolId;
	}
	/**
	 * @param aypSchoolId the aypSchoolId to set
	 */
	public void setAypSchoolId(String aypSchoolId) {
		this.aypSchoolId = aypSchoolId;
	}
	/**
	 * @return the schoolId
	 */
	public String getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}
	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}
	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
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
	 * @return the schoolEntryDate
	 */
	public String getSchoolEntryDate() {
		return schoolEntryDate;
	}
	/**
	 * @param schoolEntryDate the schoolEntryDate to set
	 */
	public void setSchoolEntryDate(String schoolEntryDate) {
		this.schoolEntryDate = schoolEntryDate;
	}
	/**
	 * @return the districtEntryDate
	 */
	public String getDistrictEntryDate() {
		return districtEntryDate;
	}
	/**
	 * @param districtEntryDate the districtEntryDate to set
	 */
	public void setDistrictEntryDate(String districtEntryDate) {
		this.districtEntryDate = districtEntryDate;
	}
	/**
	 * @return the stateEntryDate
	 */
	public String getStateEntryDate() {
		return stateEntryDate;
	}
	/**
	 * @param stateEntryDate the stateEntryDate to set
	 */
	public void setStateEntryDate(String stateEntryDate) {
		this.stateEntryDate = stateEntryDate;
	}
	/**
	 * @return the giftedStudent
	 */
	public Boolean getGiftedStudent() {
		return giftedStudent;
	}
	/**
	 * @param giftedStudent the giftedStudent to set
	 */
	public void setGiftedStudent(Boolean giftedStudent) {
		this.giftedStudent = giftedStudent;
	}
	
	public String getGiftedStudentStr() {
		if(giftedStudent == null)
			return "";
		else if(giftedStudent)
			return "Yes";
		else
			return "No";
	}
	
	public String getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(String gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public String getGradeCourseName() {
		
		return gradeCourseName==null ? "" : gradeCourseName;
	}
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccessProfileStatus() {
		return accessProfileStatus;
	}
	public void setAccessProfileStatus(String accessProfileStatus) {
		this.accessProfileStatus = accessProfileStatus;
	}
	public String getGenderString() {
		return genderString;
	}
	public void setGenderString(String genderString) {
		this.genderString = genderString;
	}
	public String getResidenceDistrictIdentifiers() {
		return residenceDistrictIdentifiers;
	}
	public void setResidenceDistrictIdentifiers(String residenceDistrictIdentifiers) {
		this.residenceDistrictIdentifiers = residenceDistrictIdentifiers;
	}
	public String getCurrentSchoolYears() {
		return currentSchoolYears;
	}
	public void setCurrentSchoolYears(String currentSchoolYears) {
		this.currentSchoolYears = currentSchoolYears;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getLocalStudentIdentifiers() {
		return localStudentIdentifiers;
	}
	public void setLocalStudentIdentifiers(String localStudentIdentifiers) {
		this.localStudentIdentifiers = localStudentIdentifiers;
	}
	public String getAttendanceSchoolDisplayIdentifiers() {
		return attendanceSchoolDisplayIdentifiers;
	}
	public void setAttendanceSchoolDisplayIdentifiers(
			String attendanceSchoolDisplayIdentifiers) {
		this.attendanceSchoolDisplayIdentifiers = attendanceSchoolDisplayIdentifiers;
	}
	public String getAttendanceSchoolNames() {
		return attendanceSchoolNames;
	}
	public void setAttendanceSchoolNames(String attendanceSchoolNames) {
		this.attendanceSchoolNames = attendanceSchoolNames;
	}
	public String getRosterIds() {
		return rosterIds;
	}
	public void setRosterIds(String rosterIds) {
		this.rosterIds = rosterIds;
	}
	public String getTestTypeCode() {
		return testTypeCode;
	}
	public void setTestTypeCode(String testTypeCode) {
		this.testTypeCode = testTypeCode;
	}
	public String getTestTypeName() {
		return testTypeName;
	}
	public void setTestTypeName(String testTypeName) {
		this.testTypeName = testTypeName;
	}
	public String getAypSchoolNames() {
		return aypSchoolNames;
	}
	public void setAypSchoolNames(String aypSchoolNames) {
		this.aypSchoolNames = aypSchoolNames;
	}
	public Date getEnrlModifiedDate() {
		return enrlModifiedDate;
	}
	public void setEnrlModifiedDate(Date enrlModifiedDate) {
		this.enrlModifiedDate = enrlModifiedDate;
	}
	public Long getEnrlModifiedUser() {
		return enrlModifiedUser;
	}
	public void setEnrlModifiedUser(Long enrlModifiedUser) {
		this.enrlModifiedUser = enrlModifiedUser;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getLastModifiedEnrlUserName() {
		return lastModifiedEnrlUserName;
	}
	public void setLastModifiedEnrlUserName(String lastModifiedEnrlUserName) {
		this.lastModifiedEnrlUserName = lastModifiedEnrlUserName;
	}
	/**
	 * @return the accountabilityDistrictIdentifier
	 */
	public String getAccountabilityDistrictIdentifier() {
		return accountabilityDistrictIdentifier;
	}
	/**
	 * @param accountabilityDistrictIdentifier the accountabilityDistrictIdentifier to set
	 */
	public void setAccountabilityDistrictIdentifier(
			String accountabilityDistrictIdentifier) {
		this.accountabilityDistrictIdentifier = accountabilityDistrictIdentifier;
	}
	public String getAccountabilityDistrictName() {
		return accountabilityDistrictName;
	}
	public void setAccountabilityDistrictName(String accountabilityDistrictName) {
		this.accountabilityDistrictName = accountabilityDistrictName;
	}
	
	public Date getExtractSchoolEntryDate() {
		return extractSchoolEntryDate;
	}
	public void setExtractSchoolEntryDate(Date extractSchoolEntryDate) {
		this.extractSchoolEntryDate = extractSchoolEntryDate;
	}
	public Date getExtractDistrictEntryDate() {
		return extractDistrictEntryDate;
	}
	public void setExtractDistrictEntryDate(Date extractDistrictEntryDate) {
		this.extractDistrictEntryDate = extractDistrictEntryDate;
	}
	public Date getExtractStateEntryDate() {
		return extractStateEntryDate;
	}
	public void setExtractStateEntryDate(Date extractStateEntryDate) {
		this.extractStateEntryDate = extractStateEntryDate;
	}
	public String getRosterNames() {
		return rosterNames;
	}
	public void setRosterNames(String rosterNames) {
		this.rosterNames = rosterNames;
	}
}