package edu.ku.cete.report.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/*sudhansu : Created for US17687 -For user audit trail
 */
public class UserAuditTrailHistory {

	private Long id;
	private String eventName;
	private Long modifiedUser;
	private Long affectedUser;
	private String beforeValue;
	private String currentValue;
	private Date createdDate;
	private Long domainAuditHistoryId;
	private String affectedrolename;
	
	private String modifiedUserFirstName;
	private String modifiedUserLastName;
	private String modifiedUserEducatorIdentifier;
	private String modifiedUserName;
	private String userFirstName;
	private String userLastName;
	private String userEducatorIdentifier;
	private String userName;
	
	private Long stateIds;
	private String stateNames;
	private String assessmentProgram;
	private Long assessmentProgramId;
	private String createddateStr;
	
	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	public Long getDomainAuditHistoryid() {
		return domainAuditHistoryId;
	}

	public void setDomainAuditHistoryid(Long domainaudithistoryid) {
		this.domainAuditHistoryId = domainaudithistoryid;
	}

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

	public Long getAffectedUser() {
		return affectedUser;
	}

	public void setAffectedUser(Long affectedUser) {
		this.affectedUser = affectedUser;
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

	

	/**
	 * @return the domainAuditHistoryId
	 */
	public Long getDomainAuditHistoryId() {
		return domainAuditHistoryId;
	}

	/**
	 * @param domainAuditHistoryId the domainAuditHistoryId to set
	 */
	public void setDomainAuditHistoryId(Long domainAuditHistoryId) {
		this.domainAuditHistoryId = domainAuditHistoryId;
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

	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}

	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}

	/**
	 * @param userLastName the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	/**
	 * @return the userEducatorIdentifier
	 */
	public String getUserEducatorIdentifier() {
		return userEducatorIdentifier;
	}

	/**
	 * @param userEducatorIdentifier the userEducatorIdentifier to set
	 */
	public void setUserEducatorIdentifier(String userEducatorIdentifier) {
		this.userEducatorIdentifier = userEducatorIdentifier;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void validate() {
		if (this.id == null) {
			this.id = 0L;
		}
		if (this.eventName == null) {
			this.eventName = " ";
		}
		if (this.modifiedUser == null) {
			this.modifiedUser = 0L;
		}
		if (this.affectedUser == null) {
			this.affectedUser = 0L;
		}
		if (this.beforeValue == null|| StringUtils.equalsIgnoreCase(this.beforeValue, "null")) {
			this.beforeValue = " ";
		}
		if (this.currentValue == null|| StringUtils.equalsIgnoreCase(this.currentValue, "null")) {
			this.currentValue = " ";
		}
		if (this.domainAuditHistoryId == null) {
			this.domainAuditHistoryId = 0L;
		}
		if(this.modifiedUserFirstName==null){
			this.modifiedUserFirstName=" ";
		}
		if(this.modifiedUserLastName==null){
			this.modifiedUserLastName=" ";
		}
		if(this.modifiedUserName==null){
			this.modifiedUserName=" ";
		}
		if(this.modifiedUserEducatorIdentifier==null){
			this.modifiedUserEducatorIdentifier=" ";
		}
		if(this.userFirstName==null){
			this.userFirstName=" ";
		}
		if(this.userLastName==null){
			this.userLastName="";
		}
		if(this.userName==null){
			this.userName="";
		}
		if(this.userEducatorIdentifier==null){
			this.userEducatorIdentifier="";
		}
	}

	public String getAffectedrolename() {
		return affectedrolename;
	}

	public void setAffectedrolename(String affectedrolename) {
		this.affectedrolename = affectedrolename;
	}

	

	public Long getStateIds() {
		return stateIds;
	}

	public void setStateIds(Long stateIds) {
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

	public String getCreateddateStr() {
		return createddateStr;
	}

	public void setCreateddateStr(String createddateStr) {
		this.createddateStr = createddateStr;
	}

	
}
