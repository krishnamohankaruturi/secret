package edu.ku.cete.web;

import java.sql.Date;

/**
 * US15251: User Data extraction.
 * @author Venkata Krishna Jagarlamudi
 *
 */
public class UserDetailsAndRolesDTO {
	
	private String state;	
	private String legalFirstName;
	private String legalLasttName;
	private String educatorIdentifier;
	private String email;
	private String organizationId;
	private String organizationLevel;
	private String organizationName;	
	private String userStatus;
	private String roles;
	// Added during US16425
	private String assessmentProgram;
	private Date createdDate;
	private Boolean activeFlag;
	private String districtOrgId;
	
	public String getDistrictOrgId() {
		return districtOrgId;
	}
	public void setDistrictOrgId(String districtOrgId) {
		this.districtOrgId = districtOrgId;
	}
	public String getAssessmentProgram() {
		return assessmentProgram;
	}
	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
	public Boolean getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
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
	 * @return the legalLasttName
	 */
	public String getLegalLasttName() {
		return legalLasttName;
	}
	/**
	 * @param legalLasttName the legalLasttName to set
	 */
	public void setLegalLasttName(String legalLasttName) {
		this.legalLasttName = legalLasttName;
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
	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the organizationId
	 */
	public String getOrganizationId() {
		return organizationId;
	}
	/**
	 * @param organizationId the organizationId to set
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	/**
	 * @return the organizationLevel
	 */
	public String getOrganizationLevel() {
		return organizationLevel;
	}
	/**
	 * @param organizationLevel the organizationLevel to set
	 */
	public void setOrganizationLevel(String organizationLevel) {
		this.organizationLevel = organizationLevel;
	}
	/**
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}
	/**
	 * @param organizationName the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}
	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	/**
	 * @return the roles
	 */
	public String getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(String roles) {
		this.roles = roles;
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
	
}
