package edu.ku.cete.report.domain;

import java.util.Date;

public class ActivationTemplateAuditTrailHistory {
	private Long id;
	private String eventName;
	private Long modifiedUser;
	private Long affectedemailtemplateid;
	private String beforeValue;
	private String currentValue;
	private Date createdDate;
	private Long domainAuditHistoryId;
	private String createddateStr;
	/**
	  * uday
	  * F424
	  * For audit history 	
	  */
	private String stateIds;
	private String stateNames;
	private String assessmentProgram;
	private String templateName;
	private Long assessmentProgramId;
	private String modifiedUserFirstName;
	private String modifiedUserLastName;
	private String modifiedUserEducatorIdentifier;
	private String modifiedUserName;
	
	/**
	 * @return the stateIds
	 */
	public String getStateIds() {
		return stateIds;
	}
	/**
	 * @param stateIds the stateIds to set
	 */
	public void setStateIds(String stateIds) {
		this.stateIds = stateIds;
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
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Long getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public Long getAffectedemailtemplateid() {
		return affectedemailtemplateid;
	}
	public void setAffectedemailtemplateid(Long affectedemailtemplateid) {
		this.affectedemailtemplateid = affectedemailtemplateid;
	}
	public String getBeforeValue() {
		return beforeValue;
	}
	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}
	
	public String getCurrentValue() {
		return currentValue;
	}
	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getDomainAuditHistoryId() {
		return domainAuditHistoryId;
	}
	public void setDomainAuditHistoryId(Long domainAuditHistoryId) {
		this.domainAuditHistoryId = domainAuditHistoryId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the templateName
	 */
	public String getTemplateName() {
		return templateName;
	}
	/**
	 * @param templateName the templateName to set
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	/**
	 * @return the assessmentProgram
	 */
	public String getAssessmentProgram() {
		return assessmentProgram;
	}
	/**
	 * @param assessmentProgram the assessmentProgram to set
	 */
	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	
	/**
	 * @return the stateNames
	 */
	public String getStateNames() {
		return stateNames;
	}
	/**
	 * @param stateNames the stateNames to set
	 */
	public void setStateNames(String stateNames) {
		this.stateNames = stateNames;
	}
	

	/**
	 * @return the modifiedUserFirstName
	 */
	public String getModifiedUserFirstName() {
		return modifiedUserFirstName;
	}
	/**
	 * @param modifiedUserFirstName the modifiedUserFirstName to set
	 */
	public void setModifiedUserFirstName(String modifiedUserFirstName) {
		this.modifiedUserFirstName = modifiedUserFirstName;
	}
	/**
	 * @return the modifiedUserLastName
	 */
	public String getModifiedUserLastName() {
		return modifiedUserLastName;
	}
	/**
	 * @param modifiedUserLastName the modifiedUserLastName to set
	 */
	public void setModifiedUserLastName(String modifiedUserLastName) {
		this.modifiedUserLastName = modifiedUserLastName;
	}
	/**
	 * @return the modifiedUserEducatorIdentifier
	 */
	public String getModifiedUserEducatorIdentifier() {
		return modifiedUserEducatorIdentifier;
	}
	/**
	 * @param modifiedUserEducatorIdentifier the modifiedUserEducatorIdentifier to set
	 */
	public void setModifiedUserEducatorIdentifier(
			String modifiedUserEducatorIdentifier) {
		this.modifiedUserEducatorIdentifier = modifiedUserEducatorIdentifier;
	}
	/**
	 * @return the modifiedUserName
	 */
	public String getModifiedUserName() {
		return modifiedUserName;
	}
	/**
	 * @param modifiedUserName the modifiedUserName to set
	 */
	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}
	public void validate() {
		// TODO Auto-generated method stub
		if (this.id == null) {
			this.id = 0L;
		}
		if (this.eventName == null) {
			this.eventName = " ";
		}
		if (this.modifiedUser == null) {
			this.modifiedUser = 0L;
		}
		if (this.affectedemailtemplateid == null) {
			this.affectedemailtemplateid = 0L;
		}
		if (this.beforeValue == null) {
			this.beforeValue = " ";
		}
		if (this.currentValue == null) {
			this.currentValue = " ";
		}
		
		if (this.domainAuditHistoryId == null) {
			this.domainAuditHistoryId = 0L;
		}
		if (this.id == null) {
			this.id = 0L;
		}
		if (this.stateNames == null) {
			this.stateNames = " ";
		}
		if (this.assessmentProgram == null) {
			this.assessmentProgram = " ";
		}
		if (this.templateName == null) {
			this.templateName = " ";
		}
		if(this.modifiedUserFirstName==null){
			this.modifiedUserFirstName="";
		}
		if(this.modifiedUserLastName==null){
			this.modifiedUserLastName="";
		}
		if(this.modifiedUserName==null){
			this.modifiedUserName="";
		}
		if(this.modifiedUserEducatorIdentifier==null){
			this.modifiedUserEducatorIdentifier="";
		}
	}
	public String getCreateddateStr() {
		return createddateStr;
	}
	public void setCreateddateStr(String createddateStr) {
		this.createddateStr = createddateStr;
	}
	
}
