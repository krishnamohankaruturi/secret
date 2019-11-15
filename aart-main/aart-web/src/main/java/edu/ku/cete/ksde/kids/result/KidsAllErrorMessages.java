package edu.ku.cete.ksde.kids.result;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.ku.cete.util.DateUtil;

public class KidsAllErrorMessages implements Serializable{
	
	private static final long serialVersionUID = 3350215087863991083L;
		
	private Date processedDate;
	private String stateStudentIdentifier;
	private String legalFirstName;
	private String legalMiddleName;
	private String legalLastName;
	private Date dateOfBirth;
	private String recordType;
	private String attendanceSchoolName;
	private String aypSchoolName;
	private String subjectArea;
	private String currentGradeLevel;
	private String educatorIdentifier;
	private String educatorFirstName;
	private String educatorLastName;
	private String messageType;
	private String reasons;
	/**
	 * @return the processedDate
	 */
	public String getProcessedDate() {
		if(this.processedDate == null) {
			return StringUtils.EMPTY;
		}		
		return DateUtil.format(this.processedDate,"MM/dd/yy hh:mm a z");
	}
	/**
	 * @param processedDate the processedDate to set
	 */
	@JsonProperty("Processed Date")
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
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
	@JsonProperty("SSID")
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	/**
	 * @return the legalFirstName
	 */
	public String getLegalFirstName() {
		return legalFirstName;
	}
	/**
	 * @param legalFirstName the legalFirstName to set
	 */
	@JsonProperty("Student First Name")
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	/**
	 * @return the legalMiddleName
	 */
	public String getLegalMiddleName() {
		return legalMiddleName;
	}
	/**
	 * @param legalMiddleName the legalMiddleName to set
	 */
	@JsonProperty("Student Middle Name")
	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}
	/**
	 * @return the legalLastName
	 */
	public String getLegalLastName() {
		return legalLastName;
	}
	/**
	 * @param legalLastName the legalLastName to set
	 */
	@JsonProperty("Student Last Name")
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		if(this.dateOfBirth == null) {
			return StringUtils.EMPTY;
		}
		return new SimpleDateFormat("MM/dd/yyyy").format(this.dateOfBirth);
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	@JsonProperty("Student Date of Birth")
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the recordType
	 */
	public String getRecordType() {
		return recordType;
	}
	/**
	 * @param recordType the recordType to set
	 */
	@JsonProperty("Record Type")
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	/**
	 * @return the attendanceSchoolName
	 */
	public String getAttendanceSchoolName() {
		return attendanceSchoolName;
	}
	/**
	 * @param attendanceSchoolName the attendanceSchoolName to set
	 */
	@JsonProperty("Attendance School Name")
	public void setAttendanceSchoolName(String attendanceSchoolName) {
		this.attendanceSchoolName = attendanceSchoolName;
	}
	/**
	 * @return the aypSchoolName
	 */
	public String getAypSchoolName() {
		return aypSchoolName;
	}
	/**
	 * @param aypSchoolName the aypSchoolName to set
	 */
	@JsonProperty("Ayp School Name")
	public void setAypSchoolName(String aypSchoolName) {
		this.aypSchoolName = aypSchoolName;
	}
	/**
	 * @return the subjectArea
	 */
	public String getSubjectArea() {
		return subjectArea;
	}
	/**
	 * @param subjectArea the subjectArea to set
	 */
	@JsonProperty("Subject Area")
	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}
	/**
	 * @return the currentGradeLevel
	 */
	public String getCurrentGradeLevel() {
		return currentGradeLevel;
	}
	/**
	 * @param currentGradeLevel the currentGradeLevel to set
	 */
	@JsonProperty("Grade")
	public void setCurrentGradeLevel(String currentGradeLevel) {
		this.currentGradeLevel = currentGradeLevel;
	}
	/**
	 * @return the educatorIdentifier
	 */
	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}
	/**
	 * @param educatorIdentifier the educatorIdentifier to set
	 */
	@JsonProperty("Educator Identifier")
	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
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
	@JsonProperty("Educator First Name")
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
	@JsonProperty("Educator Last Name")
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}
	/**
	 * @return the messageType
	 */
	public String getMessageType() {
		return messageType;
	}
	/**
	 * @param messageType the messageType to set
	 */
	@JsonProperty("Message Type")
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * @return the reasons
	 */
	public String getReasons() {
		return reasons;
	}
	/**
	 * @param reasons the reasons to set
	 */
	@JsonProperty("Reasons")
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}	
}
