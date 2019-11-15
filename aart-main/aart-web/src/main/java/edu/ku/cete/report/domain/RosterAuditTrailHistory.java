package edu.ku.cete.report.domain;

import java.util.Date;

public class RosterAuditTrailHistory {
	private Long id;
	private String eventName;
	private Long modifiedUser;
	private Long affectedroster;
	private String beforeValue;
	private String currentValue;
	private Date createdDate;
	private Long domainAuditHistoryId;
	private String subject;
	private Long  subjectId;
	private String educatorId;
	private String educatorName;
	private Long schoolId;
	private String school;
	private Long  districtId;
	private String district;
	private Long  stateId;
	private String state;
	private String rosterName;
	private String modifiedUserFirstName;
	private String modifiedUserLastName;
	private String modifiedUserEducatorIdentifier;
	private String modifiedUserName;
	private Long  educatorInternalId;
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
	public Long getAffectedroster() {
		return affectedroster;
	}
	public void setAffectedroster(Long affectedroster) {
		this.affectedroster = affectedroster;
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
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
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
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
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
	 * @return the educatorInternalId
	 */
	public Long getEducatorInternalId() {
		return educatorInternalId;
	}
	/**
	 * @param educatorInternalId the educatorInternalId to set
	 */
	public void setEducatorInternalId(Long educatorInternalId) {
		this.educatorInternalId = educatorInternalId;
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
		if (this.affectedroster == null) {
			this.affectedroster = 0L;
		}
		if (this.beforeValue == null) {
			this.beforeValue = " ";
		}
		if (this.currentValue == null) {
			this.currentValue = " ";
		}
		if (this.subject == null) {
			this.subject = " ";
		}
		if (this.educatorId == null) {
			this.educatorId = " ";
		}
		if (this.educatorName == null) {
			this.educatorName = " ";
		}
		if (this.school == null) {
			this.school = " ";
		}
		if (this.district == null) {
			this.district = " ";
		}
		if (this.state == null) {
			this.state = " ";
		}
		if (this.rosterName == null) {
			this.rosterName = " ";
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
