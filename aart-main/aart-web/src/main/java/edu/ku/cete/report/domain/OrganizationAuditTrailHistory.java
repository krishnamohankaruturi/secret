package edu.ku.cete.report.domain;

import java.util.Date;

public class OrganizationAuditTrailHistory {
	private Long id;
	private String eventName;
	private Long modifiedUser;
	private Long affectedorganization;
	private String beforeValue;
	private String currentValue;
	private Date createdDate;
	private Long domainAuditHistoryId;
	private String stateName;
	private Long stateId;
	private String districtName;
	private Long districtId;
	private String schoolName;
	private Long schoolId;
	private String modifiedUserFirstName;
	private String modifiedUserLastName;
	private String modifiedUserEducatorIdentifier;
	private String modifiedUserName;
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
	
	/**
	 * @return the affectedorganization
	 */
	public Long getAffectedorganization() {
		return affectedorganization;
	}
	/**
	 * @param affectedorganization the affectedorganization to set
	 */
	public void setAffectedorganization(Long affectedorganization) {
		this.affectedorganization = affectedorganization;
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
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
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
	 * @return the districtId
	 */
	public Long getDistrictId() {
		return districtId;
	}
	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
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
		if (this.affectedorganization == null) {
			this.affectedorganization = 0l;
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
