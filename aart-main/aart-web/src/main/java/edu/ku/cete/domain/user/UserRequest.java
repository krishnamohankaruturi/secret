/**
 * 
 */
package edu.ku.cete.domain.user;

import org.apache.commons.lang.StringUtils;

	
/**
 * @author bmohanty_sta
 *
 */
public class UserRequest {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String educatorIdentifier;
	private Long defaultOrgId;
	private Long defaultRoleId;
	private Long defaultAssessmentProgramId;
	public Long getEducatorId() {
		return educatorId;
	}

	public void setEducatorId(Long educatorId) {
		this.educatorId = educatorId;
	}



	private OrganizationRoleRequest[] organizations;
	private String errorCode;
	private Long createModifierId;
	private Long[] assessmentProgramsIds;
	private String action;
	private Long defaultUserOrgId;
	private Boolean activeflag;
	private String externalId;
    public Long getEmailId() {
		return emailId;
	}

	public void setEmailId(Long emailId) {
		this.emailId = emailId;
	}



	private Long toUpdateId;
    private Long emailId;
    private Long educatorId;
    
    public Long getToUpdateId() {
		return toUpdateId;
	}

	public void setToUpdateId(Long toUpdateId) {
		this.toUpdateId = toUpdateId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

    /**
     *
     */
    public UserRequest() {
        
    }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @return the educatorIdentifier
	 */
	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}

	/**
	 * @param educatorIdentifier the educatorIdentifier to set
	 */
	public void setEducatorIdentifier(String educatorIdentifier) {
		if (StringUtils.isBlank(educatorIdentifier)) {
			this.educatorIdentifier = null;
		} else {
			this.educatorIdentifier = educatorIdentifier;
		}
	}

	/**
	 * @return the defaultOrgId
	 */
	public Long getDefaultOrgId() {
		return defaultOrgId;
	}

	/**
	 * @param defaultOrgId the defaultOrgId to set
	 */
	public void setDefaultOrgId(Long defaultOrgId) {
		this.defaultOrgId = defaultOrgId;
	}

	/**
	 * @return the organizations
	 */
	public OrganizationRoleRequest[] getOrganizations() {
		return organizations;
	}

	/**
	 * @param organizations the organizations to set
	 */
	public void setOrganizations(OrganizationRoleRequest[] organizations) {
		this.organizations = organizations;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the createModifierId
	 */
	public Long getCreateModifierId() {
		return createModifierId;
	}

	/**
	 * @param createModifierId the createModifierId to set
	 */
	public void setCreateModifierId(Long createModifierId) {
		this.createModifierId = createModifierId;
	}
	//Added During US16351-To carry the selected Assessment programs Ids
	/*
	 * To get Assessment Program Ids
	 */
	public Long[] getAssessmentProgramsIds() {
		return assessmentProgramsIds;
	}
	
	/*
	 * To set Assessment Program Ids 
	 */
	public void setAssessmentProgramsId(Long[] assessmentProgramsIds) {
		this.assessmentProgramsIds = assessmentProgramsIds;
	}

	public Long getDefaultRoleId() {
		return defaultRoleId;
	}

	public void setDefaultRoleId(Long defaultRoleId) {
		this.defaultRoleId = defaultRoleId;
	}

	public Long getDefaultAssessmentProgramId() {
		return defaultAssessmentProgramId;
	}

	public void setDefaultAssessmentProgramId(Long defaultAssessmentProgramId) {
		this.defaultAssessmentProgramId = defaultAssessmentProgramId;
	}

	public void setAssessmentProgramsIds(Long[] assessmentProgramsIds) {
		this.assessmentProgramsIds = assessmentProgramsIds;
	}

	public void setUserOrganizationGroupID() {
		// TODO Auto-generated method stub
		
	}

	public Long getDefaultUserOrgId() {
		return defaultUserOrgId;
	}

	public void setDefaultUserOrgId(Long defaultUserOrgId) {
		this.defaultUserOrgId = defaultUserOrgId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Boolean getActiveflag() {
		return activeflag;
	}

	public void setActiveflag(Boolean activeflag) {
		this.activeflag = activeflag;
	}

}
