package edu.ku.cete.domain;

import java.util.Date;

public class UploadScCodeFile {
	private Long id;
	private Long  studentId;
	private String state;
	private Long stateId;
	private String stateStudentIdentifier;
	private String legalFirstName;
	private String legalMiddleName;
	private String legalLastName;
	private String generationCode;
	private Date dateOfBirth;
	private String essentialElement;
	private String assessment;
	private String specialCircumstanceLabel;
	private String ksdeScCode;
	private Long specialCircumstanceCode;
	private Long batchUploadId;
	private Long uploadedUserId;
	private String lineNumber;
	private Long reportYear;
	private boolean activeFlag;
	private Date createdDate;
	private Date scCodeCreatedDate;
	private boolean includeInExtract;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}
	/**
	 * @param sateId the stateId to set
	 */
	public void setStateId(Long sateId) {
		this.stateId = sateId;
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
	 * @return the legalFirstName
	 */
	public String getLegalFirstName() {
		return legalFirstName;
	}
	/**
	 * @param legalFirstName the legalFirstName to set
	 */
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
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	/**
	 * @return the generationCode
	 */
	public String getGenerationCode() {
		return generationCode;
	}
	/**
	 * @param generationCode the generationCode to set
	 */
	public void setGenerationCode(String generationCode) {
		this.generationCode = generationCode;
	}
	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the essentialElement
	 */
	public String getEssentialElement() {
		return essentialElement;
	}
	/**
	 * @param essentialElement the essentialElement to set
	 */
	public void setEssentialElement(String essentialElement) {
		this.essentialElement = essentialElement;
	}
	/**
	 * @return the assessment
	 */
	public String getAssessment() {
		return assessment;
	}
	/**
	 * @param assessment the assessment to set
	 */
	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
	/**
	 * @return the specialCircumstanceLabel
	 */
	public String getSpecialCircumstanceLabel() {
		return specialCircumstanceLabel;
	}
	/**
	 * @param specialCircumstanceLabel the specialCircumstanceLabel to set
	 */
	public void setSpecialCircumstanceLabel(String specialCircumstanceLabel) {
		this.specialCircumstanceLabel = specialCircumstanceLabel;
	}
	/**
	 * @return the ksdeScCode
	 */
	public String getKsdeScCode() {
		return ksdeScCode;
	}
	/**
	 * @param ksdeScCode the ksdeScCode to set
	 */
	public void setKsdeScCode(String ksdeScCode) {
		this.ksdeScCode = ksdeScCode;
	}
	/**
	 * @return the specialCircumstanceCode
	 */
	public Long getSpecialCircumstanceCode() {
		return specialCircumstanceCode;
	}
	/**
	 * @param specialCircumstanceCode the specialCircumstanceCode to set
	 */
	public void setSpecialCircumstanceCode(Long specialCircumstanceCode) {
		this.specialCircumstanceCode = specialCircumstanceCode;
	}
	/**
	 * @return the batchUploadId
	 */
	public Long getBatchUploadId() {
		return batchUploadId;
	}
	/**
	 * @param batchUploadId the batchUploadId to set
	 */
	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}
	/**
	 * @return the uploadedUserId
	 */
	public Long getUploadedUserId() {
		return uploadedUserId;
	}
	/**
	 * @param uploadedUserId the uploadedUserId to set
	 */
	public void setUploadedUserId(Long uploadedUserId) {
		this.uploadedUserId = uploadedUserId;
	}
	/**
	 * @return the lineNumber
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return the reportYear
	 */
	public Long getReportYear() {
		return reportYear;
	}
	/**
	 * @param reportYear the reportYear to set
	 */
	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	/**
	 * @return the activeFlag
	 */
	public boolean isActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Date getScCodeCreatedDate() {
		return scCodeCreatedDate;
	}
	public void setScCodeCreatedDate(Date scCodeCreatedDate) {
		this.scCodeCreatedDate = scCodeCreatedDate;
	}
	public boolean isIncludeInExtract() {
		return includeInExtract;
	}
	public void setIncludeInExtract(boolean includeInExtract) {
		this.includeInExtract = includeInExtract;
	}	
}
