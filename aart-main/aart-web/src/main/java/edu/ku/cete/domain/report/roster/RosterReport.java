/**
 * @author bmohanty_sta
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
 * Domain object to hold the roster report to be displayed for DLM organization.
 */
package edu.ku.cete.domain.report.roster;

import java.util.List;
import java.util.Set;

public class RosterReport {
	private String reportDate;
	private String educatorName;
	private String schoolName;
	private String stateStudentIdentifier;
	private String studentFirstName;
	private String studentLastName;
	private String contentAreaName;
	private List<String> noPlanMessages;
	private Long schoolId;
	private String educatorId;
	
	private Long rosterId;
	private String rosterName;
	private String studentStateIds;
	private String studentIds;
	private String studentNames;
	private Long assessmentProgramId;
	private String resourcePath;
	private Long contentAreaId;
	private String dlmLogo;
	private String plusLogo;
	private String minusLogo;
	
	
	List<RosterReportGradeStudentData> gradeStudentDataList;
	
	/**
	 * @return the reportDate
	 */
	public String getReportDate() {
		return reportDate;
	}
	/**
	 * @param reportDate the reportDate to set
	 */
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
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
	 * @return the contentAreaName
	 */
	public String getContentAreaName() {
		return contentAreaName;
	}
	/**
	 * @param contentAreaName the contentAreaName to set
	 */
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}
	/**
	 * @return the schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * @return the educatorId
	 */
	public String getEducatorId() {
		return educatorId;
	}
	/**
	 * @param educatorId the educatorId to set
	 */
	public void setEducatorId(String educatorId) {
		this.educatorId = educatorId;
	}
	/**
	 * @return the gradeStudentDataList
	 */
	public List<RosterReportGradeStudentData> getGradeStudentDataList() {
		return gradeStudentDataList;
	}
	/**
	 * @param gradeStudentDataList the gradeStudentDataList to set
	 */
	public void setGradeStudentDataList(
			List<RosterReportGradeStudentData> gradeStudentDataList) {
		this.gradeStudentDataList = gradeStudentDataList;
	}
	/**
	 * @return the noPlanMessages
	 */
	public List<String> getNoPlanMessages() {
		return noPlanMessages;
	}
	/**
	 * @param noPlanMessages the noPlanMessages to set
	 */
	public void setNoPlanMessages(List<String> noPlanMessages) {
		this.noPlanMessages = noPlanMessages;
	}
	/**
	 * @return the rosterId
	 */
	public Long getRosterId() {
		return rosterId;
	}
	/**
	 * @param rosterId the rosterId to set
	 */
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
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
	 * @return the studentStateIds
	 */
	public String getStudentStateIds() {
		return studentStateIds;
	}
	/**
	 * @param studentStateIds the studentStateIds to set
	 */
	public void setStudentStateIds(String studentStateIds) {
		this.studentStateIds = studentStateIds;
	}
	/**
	 * @return the studentIds
	 */
	public String getStudentIds() {
		return studentIds;
	}
	/**
	 * @param studentStateIds the studentIds to set
	 */
	public void setStudentIds(String studentIds) {
		this.studentIds = studentIds;
	}
	/**
	 * @return the studentNames
	 */
	public String getStudentNames() {
		return studentNames;
	}
	/**
	 * @param studentNames the studentNames to set
	 */
	public void setStudentNames(String studentNames) {
		this.studentNames = studentNames;
	}
	/**
	 * @return the assessmentProgramId
	 */
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	/**
	 * @param assessmentProgramId the assessmentProgramId to set
	 */
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	/**
	 * @return the resourcePath
	 */
	public String getResourcePath() {
		return resourcePath;
	}
	/**
	 * @param resourcePath the resourcePath to set
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	/**
	 * @return the contentAreaId
	 */
	public Long getContentAreaId() {
		return contentAreaId;
	}
	/**
	 * @param contentAreaId the contentAreaId to set
	 */
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	
	public String getDlmLogo() {
		return dlmLogo;
	}
	
	public void setDlmLogo(String dlmLogo) {
		this.dlmLogo = dlmLogo;
	}
	
	public String getPlusLogo() {
		return plusLogo;
	}
	
	public void setPlusLogo(String plusLogo) {
		this.plusLogo = plusLogo;
	}
	
	public String getMinusLogo() {
		return minusLogo;
	}
	
	public void setMinusLogo(String minusLogo) {
		this.minusLogo = minusLogo;
	}
	
	
	
	
}