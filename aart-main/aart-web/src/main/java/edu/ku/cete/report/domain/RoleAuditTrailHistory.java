package edu.ku.cete.report.domain;

import java.util.Date;


public class RoleAuditTrailHistory {
	private Long id;
	private String eventName;
	private Long modifiedUser;
	private Long affectedrole;
	private String beforeValue;
	private String currentValue;
	private Date createdDate;
	private Long domainAuditHistoryId;
	private String stateIds;
	private String stateNames;
	private String assessmentProgram;
	private Long assessmentProgramId;
	private String modifiedUserFirstName;
	private String modifiedUserLastName;
	private String modifiedUserEducatorIdentifier;
	private String modifiedUserName;
	private String affectedrolename;
	private String createddateStr;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getAffectedrole() {
		return affectedrole;
	}
	public void setAffectedrole(Long affectedrole) {
		this.affectedrole = affectedrole;
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
	public void validate() {
		// TODO Auto-generated method stub
		if(this.id ==null){
			this.id=0L;
		}
		if(this.eventName ==null){
			this.eventName=" ";
		}
		if(this.modifiedUser  ==null){
			this.modifiedUser =0L;
		}
		if(this.affectedrole ==null){
			this.affectedrole=0L;
		}
		if(this.beforeValue  ==null){
			this.beforeValue =" ";
		}
		if(this.currentValue ==null){
			this.currentValue=" ";
		}
		
		if(this.domainAuditHistoryId ==null){
			this.domainAuditHistoryId=0L;
		}
		 
		
	}
	public String getStateIds() {
		return stateIds;
	}
	public void setStateIds(String stateIds) {
		this.stateIds = stateIds;
	}
	public String getStateNames() {
		return stateNames;
	}
	public void setStateNames(String stateNames) {
		this.stateNames = stateNames;
	}
	public String getAssessmentProgram() {
		return assessmentProgram;
	}
	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public String getModifiedUserFirstName() {
		return modifiedUserFirstName;
	}
	public void setModifiedUserFirstName(String modifiedUserFirstName) {
		this.modifiedUserFirstName = modifiedUserFirstName;
	}
	public String getModifiedUserLastName() {
		return modifiedUserLastName;
	}
	public void setModifiedUserLastName(String modifiedUserLastName) {
		this.modifiedUserLastName = modifiedUserLastName;
	}
	public String getModifiedUserEducatorIdentifier() {
		return modifiedUserEducatorIdentifier;
	}
	public void setModifiedUserEducatorIdentifier(
			String modifiedUserEducatorIdentifier) {
		this.modifiedUserEducatorIdentifier = modifiedUserEducatorIdentifier;
	}
	public String getModifiedUserName() {
		return modifiedUserName;
	}
	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}
	public String getAffectedrolename() {
		return affectedrolename;
	}
	public void setAffectedrolename(String affectedrolename) {
		this.affectedrolename = affectedrolename;
	}
	public String getCreateddateStr() {
		return createddateStr;
	}
	public void setCreateddateStr(String createddateStr) {
		this.createddateStr = createddateStr;
	}
	
	

}
