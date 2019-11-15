package edu.ku.cete.domain.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.util.AuthorityUtil;
import edu.ku.cete.util.ParsingConstants;
/**
 *
 * @author mrajannan
 *
 */
public class User extends TraceableEntity implements Serializable {

	
    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 4674836870244469208L;

    /** MIN_USERNAME_LENGTH constant. */
    private static final int MIN_USERNAME_LENGTH = 6;

    /** MAX_USERNAME_LENGTH constant. */
    private static final int MAX_USERNAME_LENGTH = 32;

    /** MIN_PASSWORD_LENGTH constant. */
    private static final int MIN_PASSWORD_LENGTH = 6;

    /** MAX_PASSWORD_LENGTH constant. */
    private static final int MAX_PASSWORD_LENGTH = 16;

    private static final int MAX_NAME_EMAIL_LEN = 40;

    private static final int SALT_LEN = 16;

    /** The id. */
    private Long id;

    /** The user name. */
    @Size(min = MIN_USERNAME_LENGTH, max = MAX_USERNAME_LENGTH, message = "{error.user.username.invalid}")
    private String userName;

    /**
     * This treatment of names may be problematic for non-americanized names.
     * See [1] for more information about the way that names are written around
     * the world. -- Nicholas
     *
     * [1] http://www.w3.org/International/questions/qa-personal-names
     */

    /** The first name. */
    private String firstName;

    /** The middle name. */
    private String middleName;

    /** The surname. */
    private String surName;
    
    /** The display name. */
    private String displayName;

    /** The users state identifier. */
    private String uniqueCommonIdentifier;

    private Boolean activeFlag = true;

	/** The password. */
    @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH, message = "{error.user.password.invalid}")
    private String password;

    /** The user type. */
    private String userType;

    /** The user OS and browser. */
    private String userOS;

    /** The user's default userGroup(Role) status. */
    private long defaultUserGroupsId;
    private long defaultOrganizationId;
    private long defaultAssessmentProgramId;
    
    private long currentOrganizationId;
    private long currentGroupsId;
    private String currentOrganizationType;
    private Long currentAssessmentProgramId;
    private String currentAssessmentProgramName;
    

	@Size(max = MAX_NAME_EMAIL_LEN)
    private String email;

    private String salt;

    private Collection<GrantedAuthority> authorities;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean isEnabled = true;

	private List<Groups> groupsList;

	private List<Authorities> authoritiesList;

	private List<Organization> organizations;
	
	private Map<Long, Long> roleIdOrgIdsMap;
	private Long userOrganizationId;
	private Long userGroupId;
	private String orgName;
	private String programNames;
	private String userRoles;
	
	private Boolean isSecurityAgreementSigned;

	private String userJsonStr;
	
	private List<UserOrganization> userOrganizations;
	
	/*private Long userOrganizationId;
	private String userOrganizationName;
	private boolean userDefaultOrganization;
	private Long userGroupId;
	private String userGroupName;
	private boolean userDefaultGroup;
	private int rowspan;
	private String orgfilters;*/

	private String statusCode;
	
	private boolean rosterAssigned;
	
	private Date lastExpiredPasswordResetDate;
	
	private List<Long> allChilAndSelfOrgIds;
	
	private Organization contractingOrganization;
	
	private String contractingOrgDisplayIdentifier;
	
	private long contractingOrgId;		
	
	private String state;
    private String districtName;
    private String districtID;
    private String schoolName;
    private String schoolID;
    private boolean rtComplete;
    private Date rtCompleteDate;
    private String failedReason;
    private Long assessmentProgramId;
	private boolean systemIndicator;
	private boolean internaluserindicator;
	private String externalId;
	
	
    public boolean isSystemIndicator() {
		return systemIndicator;
	}

	public void setSystemIndicator(boolean systemIndicator) {
		this.systemIndicator = systemIndicator;
	}

	private List<String> assessmentProgramCodes;
    
    private List<Groups> userGroups;
    private List<UserOrganizationsGroups> userOrganizationsGroups;
    private List<AssessmentProgram> userAssessmentPrograms;

	private boolean iapNavStatus;
	private boolean iapModStatus;
    
    public String getUserJsonStr() {
		return userJsonStr;
	}

	public void setUserJsonStr(String userJsonStr) {
		this.userJsonStr = userJsonStr;
	}

	public List<UserOrganization> getUserOrganizations() {
		return userOrganizations;
	}

	public void setUserOrganizations(List<UserOrganization> userOrganizations) {
		this.userOrganizations = userOrganizations;
	}
    
    
    public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public String getContractingOrgDisplayIdentifier() {
		return contractingOrgDisplayIdentifier;
	}

	public void setContractingOrgDisplayIdentifier(
			String contractingOrgDisplayIdentifier) {
		this.contractingOrgDisplayIdentifier = contractingOrgDisplayIdentifier;
	}

	public long getContractingOrgId() {
		return contractingOrgId;
	}

	public void setContractingOrgId(long contractingOrgId) {
		this.contractingOrgId = contractingOrgId;
	}
    
	public Boolean isSecurityAgreementSigned() {
		return isSecurityAgreementSigned;
	}

	public void setSecurityAgreementSigned(Boolean isSecurityAgreementSigned) {
		this.isSecurityAgreementSigned = isSecurityAgreementSigned;
	}

	
	public boolean isRosterAssigned() {
		return rosterAssigned;
	}

	public void setRosterAssigned(boolean rosterAssigned) {
		this.rosterAssigned = rosterAssigned;
	}

	public User() {
        this.salt = RandomStringUtils.random(SALT_LEN,"abcdefghijklmnopqrstuvwxyz");
    }
        
    /**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}


	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	/**
	 * @return the userOrganizationId
	 */
	public Long getUserOrganizationId() {
		return userOrganizationId;
	}


	/**
	 * @param userOrganizationId the userOrganizationId to set
	 */
	public void setUserOrganizationId(Long userOrganizationId) {
		this.userOrganizationId = userOrganizationId;
	}


	/**
	 * @return the userGroupId
	 */
	public Long getUserGroupId() {
		return userGroupId;
	}

	/**
	 * @param userGroupId the userGroupId to set
	 */
	public void setUserGroupId(Long userGroupId) {
		this.userGroupId = userGroupId;
	}

	/**
	 * @return the roleIdOrgIdsMap
	 */
	public Map<Long, Long> getRoleIdOrgIdsMap() {
		return roleIdOrgIdsMap;
	}


	/**
	 * @param roleIdOrgIdsMap the roleIdOrgIdsMap to set
	 */
	public void setRoleIdOrgIdsMap(Map<Long, Long> roleIdOrgIdsMap) {
		this.roleIdOrgIdsMap = roleIdOrgIdsMap;
	}


	/**
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @param newId the id to set
     */
    public final void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * @return the userId
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * @param newUserName
     *            the userId to set
     */
    public final void setUserName(final String newUserName) {
        this.userName = newUserName;
    }

    /**
     * @return the firstName
     */
    public final String getFirstName() {
        return firstName;
    }

    /**
     * @param newFirstName
     *            the firstName to set
     */
    public final void setFirstName(final String newFirstName) {
        this.firstName = newFirstName;
    }

    /**
     * @return the middleName
     */
    public final String getMiddleName() {
        return middleName;
    }

    /**
     * @param newMiddleName the middleName to set
     */
    public final void setMiddleName(final String newMiddleName) {
        this.middleName = newMiddleName;
    }

    /**
     * @return the surName
     */
    public final String getSurName() {
        return surName;
    }

    /**
     * @param newSurname
     *            the surName to set
     */
    public final void setSurName(final String newSurname) {
        this.surName = newSurname;
    }

    /**
     * @return the displayName
     */
    public final String getDisplayName() {
        return displayName;
    }

    /**
     * @param newdisplayName
     *            the displayName to set
     */
    public final void setDisplayName(final String newDisplayName) {
        this.displayName = newDisplayName;
    }

    /**
     * @return the uniqueCommonIdentifier
     */
    public final String getUniqueCommonIdentifier() {
        return uniqueCommonIdentifier;
    }

    /**
     * @param newUniqueCommonIdentifier the uniqueCommonIdentifier to set
     */
    public final void setUniqueCommonIdentifier(final String newUniqueCommonIdentifier) {
        this.uniqueCommonIdentifier = newUniqueCommonIdentifier;
    }

    /**
     * @return the password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * @param newPassword
     *            the password to set
     */
    public final void setPassword(final String newPassword) {
        this.password = newPassword;
    }

    /**
     *
     *@return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     *@param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the userType
     */
    public final String getUserType() {
        return userType;
    }

    /**
     * @param newUserType
     *            the userType to set
     */
    public final void setUserType(final String newUserType) {
        this.userType = newUserType;
    }

    /**
     * @return the userOS
     */
    public final String getUserOS() {
        return userOS;
    }

    /**
     * @param newUserOS
     *            the userOS to set
     */
    public final void setUserOS(final String newUserOS) {
        this.userOS = newUserOS;
    }

    /**
     * @return the enabled
     */
    public final long getDefaultUserGroupsId() {
        return defaultUserGroupsId;
    }

    /**
     * @param newEnabled the enabled to set
     */
    public final void setDefaultUserGroupsId(final long defaultUserGroupsId) {
        this.defaultUserGroupsId = defaultUserGroupsId;
    }
    
	public void setCurrentAssessmentProgramName(String currentAssessmentProgramName) {
		this.currentAssessmentProgramName = currentAssessmentProgramName;
	}
    
    public String getCurrentAssessmentProgramName() {
		return currentAssessmentProgramName;
	}


    /**
     * @return the authorities
     */
    public final Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
//Added During US16351- To show assessment program column in user grid 
    public String getProgramNames() {
		return programNames;
	}

	public void setProgramNames(String programNames) {
		this.programNames = programNames;
	}

	public String getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(String userRoles) {
		this.userRoles = userRoles;
	}

	public void resetAuthorities() {
		Collection<GrantedAuthority> authorities = null;
    	if(getCurrentOrganizationId() != 0) {
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getCurrentOrganizationId())) {					
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getCurrentGroupsId()){
							authorities = AuthorityUtil.mapAuthorities(group.getAuthoritiesList());
							setAuthoritiesList(group.getAuthoritiesList());
							setCurrentOrganizationType(group.getOrganizationType().getTypeCode());
							break;
						}
					}
				}
			}
    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getDefaultOrganizationId())) {
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getDefaultUserGroupsId()){
							authorities = AuthorityUtil.mapAuthorities(group.getAuthoritiesList());
							setAuthoritiesList(group.getAuthoritiesList());
							setCurrentOrganizationType(group.getOrganizationType().getTypeCode());
							break;
						}
					}
				}
			}
    	}
		setAuthorities(authorities); 
	}
	
	public boolean isAssessmentProgramUser(String apCode) {
		if(apCode != null && getAssessmentProgramCodes() != null 
						&& (getAssessmentProgramCodes().size() > 0)){
			for (String userAPCode : getAssessmentProgramCodes()) {
				if(userAPCode != null && userAPCode.trim().equalsIgnoreCase(apCode.trim())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isTeacher() {
		boolean isteacher = false;
		if(getCurrentOrganizationId() != 0) {
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getCurrentOrganizationId())) {
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getCurrentGroupsId()
								&& !StringUtils.isEmpty(group.getGroupCode())
								&& (group.getGroupCode().equalsIgnoreCase("TEAR") 
											|| group.getGroupCode().equalsIgnoreCase("TEA"))){
							isteacher = true;
							break;
						}
					}
				}
			}
    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getDefaultOrganizationId())) {
					setCurrentOrganizationType(organization.getOrganizationType().getTypeCode());
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getDefaultUserGroupsId() 
								&& !StringUtils.isEmpty(group.getGroupCode())
								&& (group.getGroupCode().equalsIgnoreCase("TEAR") 
										|| group.getGroupCode().equalsIgnoreCase("TEA"))){
							isteacher = true;
							break;
						}
					}
				}
			}
    	} else {
    		isteacher = true;
    	}
		return isteacher;
	}
	
	// method for accessing property in JSP files
	public boolean getIsTeacher() {
		return this.isTeacher();
	}
	
	public boolean isProctor() {
		boolean isproctor = false;
		if(getCurrentOrganizationId() != 0) {
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getCurrentOrganizationId())) {
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getCurrentGroupsId()
								&& !StringUtils.isEmpty(group.getGroupCode())
								&& (group.getGroupCode().equalsIgnoreCase("PRO"))){
							isproctor = true;
							break;
						}
					}
				}
			}
    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getDefaultOrganizationId())) {
					setCurrentOrganizationType(organization.getOrganizationType().getTypeCode());
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getDefaultUserGroupsId()
								&& !StringUtils.isEmpty(group.getGroupCode())
								&& (group.getGroupCode().equalsIgnoreCase("PRO"))){
							isproctor = true;
							break;
						}
					}
				}
			}
    	} else {
    		isproctor = true;
    	}
		return isproctor;
	}
	
	// method for accessing property in JSP files
	public boolean getIsProctor() {
		return this.isProctor();
	}
	
	public int getAccessLevel() {
		int orgTypeLevel = 70;
				
		if(getCurrentOrganizationId() != 0) {
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getCurrentOrganizationId())) {
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getCurrentGroupsId()) {
							orgTypeLevel = group.getOrganizationType().getTypeLevel();
							break;
						}
					}
				}
			}
    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getDefaultOrganizationId())) {
					setCurrentOrganizationType(organization.getOrganizationType().getTypeCode());
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getDefaultUserGroupsId()) { 
							orgTypeLevel = group.getOrganizationType().getTypeLevel();
							break;
						}
					}
				}
			}
    	}
		
		return orgTypeLevel;
	}
	
	public boolean isSysAdmin() {
		boolean isSysAdmin = false;
		if(getCurrentOrganizationId() != 0) {
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getCurrentOrganizationId())) {
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getCurrentGroupsId()
								&& group.getGroupName().equalsIgnoreCase("Global System Administrator")){
							isSysAdmin = true;
							break;
						}
					}
				}
			}
    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getDefaultOrganizationId())) {
					setCurrentOrganizationType(organization.getOrganizationType().getTypeCode());
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getDefaultUserGroupsId() 
								&& group.getGroupName().equalsIgnoreCase("Global System Administrator")){
							isSysAdmin = true;
							break;
						}
					}
				}
			}
    	} else {
    		isSysAdmin = true;
    	}
		return isSysAdmin;
	}	

	public boolean isDistTestCoordinator() {
		boolean isDistTestCoordinator = false;
		if(getCurrentOrganizationId() != 0) {
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getCurrentOrganizationId())) {
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getCurrentGroupsId()
								&& group.getGroupName().equalsIgnoreCase("District Test Coordinator")){
							isDistTestCoordinator = true;
							break;
						}
					}
				}
			}
    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getDefaultOrganizationId())) {
					setCurrentOrganizationType(organization.getOrganizationType().getTypeCode());
					for (Groups group : organization.getGroupsList()){
						if(group.getGroupId() == getDefaultUserGroupsId() 
								&& group.getGroupName().equalsIgnoreCase("District Test Coordinator")){
							isDistTestCoordinator = true;
							break;
						}
					}
				}
			}
    	} else {
    		isDistTestCoordinator = true;
    	}
		return isDistTestCoordinator;
	}
	
    /**
     * @param authorities the authorities to set
     */
    public final void setAuthorities(Collection<GrantedAuthority> authorities) {
        if (authorities == null) {
            this.authorities = new ArrayList<GrantedAuthority>();
        } else {
            this.authorities = authorities;
        }
    }

    /**
     * @return the accountNonExpired
     */
    public final boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * @param accountNonExpired the accountNonExpired to set
     */
    public final void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * @return the accountNonLocked
     */
    public final boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * @param accountNonLocked the accountNonLocked to set
     */
    public final void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * @return the credentialsNonExpired
     */
    public final boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * @param credentialsNonExpired the credentialsNonExpired to set
     */
    public final void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

	@Override
	public String getIdentifier() {
		return null;
	}
    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }
    
    public List<Groups> getGroupsList() {
    	return groupsList;
    }
	/**
	 * This is only a database relation that is abstracted.
	 * @param groupList
	 */
	public void setGroupsList(List<Groups> groupList) {
		this.groupsList = groupList;
	}
	
	/**
	 * @param authoritiesList {@link List}
	 */
	public void setAuthoritiesList(List<Authorities> authoritiesList) {
		this.authoritiesList = authoritiesList;
        //convert the authorities to GrantedAuthorities
        List<GrantedAuthority> grantedAuthorities = AuthorityUtil.mapAuthorities(authoritiesList);
        //combine them into a UserDetails object
        setAuthorities(grantedAuthorities);		
	}
	/**
	 * @return
	 */
	public List<Authorities> getAuthoritiesList(){
		return authoritiesList;
	}
	
	/**
	 * @return {@link List}
	 */
	public final List<Organization> getOrganizations() {
		return organizations;
	}
	/**
	 * @param orgs {@link List}
	 */
	public final void setOrganizations(List<Organization> orgs) {
		this.organizations = orgs;
	}
	
	/**
	 * use this method untill the users cannot be part of more than one organization.
	 * TODO remove this method after "One Group per role" is fixed.
	 * @return {@link Organization}
	 */
	public final Organization getOrganization() {
		//return ((organizations != null && organizations.size() > 0 ) ? organizations.get(0) : null );
		if((organizations != null && organizations.size() > 0 )) {
			if(getCurrentOrganizationId() != 0) {
		    	for (Organization organization : getOrganizations()) {
					if(organization.getId().equals(getCurrentOrganizationId())) {
						return organization;
					}
				}
	    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
		    	for (Organization organization : getOrganizations()) {
					if(organization.getId().equals(getDefaultOrganizationId())) {
						return organization;
					}
				}
	    	}
		}
		return null;
	}

	/**
	 * TODO change here when the application is required to function for more than one organization and role.
	 * @return
	 */
	public final Long getOrganizationId() {
		Organization org = getOrganization();
		Long organizationId = null;
		if (org != null) {
			organizationId = org.getId();
		}
		return organizationId;
	}
	
    
	
	/**
	 * @return the statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * @param statusCode the statusCode to set
	 */
	
	public void setStatusCode(String statusCode) {
		
		this.isEnabled = true;
		if ("Inactive".equals(statusCode) || "Pending".equals(statusCode)) {
			this.isEnabled=false;
		}
				
		this.statusCode = statusCode;
	}
	
	public boolean isEnabled() {
		return this.isEnabled;
	}

	@Override
    public final String toString() {
        return "User [id=" + id + ", userName=" + userName + ", firstName=" + firstName + ", middleName=" + middleName
                + ", surName=" + surName + ", displayName=" + displayName + "," +
                " uniqueCommonIdentifier=" + uniqueCommonIdentifier + ", password=" + password + ", userType=" + userType
                + ", userOS=" + userOS + ", defaultUserGroupsId=" + defaultUserGroupsId +" authoritiesList = " + authoritiesList
                		+"]";
    }

	public String getOrganizationNames() {
		StringBuilder organizationBuilder = new StringBuilder();
		List<Organization> orgs = getOrganizations();

		int i = 0;
		if (orgs != null && CollectionUtils.isNotEmpty(orgs)) {
			for (Organization organization : orgs) {
				i++;
				organizationBuilder.append(organization.getOrganizationName());
				if (i != orgs.size()) {
					organizationBuilder.append(ParsingConstants.COMMA);
				}
			}
		}
		return organizationBuilder.toString();
	}
	
	public String getOrganizationName() {
		if(getCurrentOrganizationId() != 0) {
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getCurrentOrganizationId())) {
					return organization.getOrganizationName();
				}
			}
    	} else if(getDefaultOrganizationId() != 0) { //if no currentorgid return default authorities
	    	for (Organization organization : getOrganizations()) {
				if(organization.getId().equals(getDefaultOrganizationId())) {
					return organization.getOrganizationName();
				}
			}
    	}
		
		return "";
	}
	/**
	 * Get user current group(Role) name
	 * @return
	 */
	public String getGroupName(){
		List<Groups> groups = getGroupsList();
        long currentGroupId = getCurrentGroupsId();
        long defaultGroupId = getDefaultUserGroupsId();
        
        if( currentGroupId != 0 && groups != null){
	        for( Groups group : groups ){
	        	if( group.getGroupId() ==  currentGroupId ){
	        		return group.getGroupName();
	        	}
	        }	
        }	
	    if( defaultGroupId != 0 && groups != null){    
	    	for( Groups group : groups ){
		    	if( group.getGroupId() ==  defaultGroupId ){
	        		return group.getGroupName();
	        	}
	    	}	
        }
        return "";
	}

	
	/**
	 * Get user current group(Role) name
	 * @return
	 */
	public String getGroupCode(){
		List<Groups> groups = getGroupsList();
        long currentGroupId = getCurrentGroupsId();
        long defaultGroupId = getDefaultUserGroupsId();
        
        if( currentGroupId != 0 && groups != null){
	        for( Groups group : groups ){
	        	if( group.getGroupId() ==  currentGroupId ){
	        		return group.getGroupCode();
	        	}
	        }	
        }	
	    if( defaultGroupId != 0 && groups != null){    
	    	for( Groups group : groups ){
		    	if( group.getGroupId() ==  defaultGroupId ){
	        		return group.getGroupCode();
	        	}
	    	}	
        }
        return "";
	}
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : DE5223 : Web service upload page access should be restricted
	 * Since currently there is no way to find out if a user is a state level or higher we are using the organization type
	 * value of ST or CONS to decide the same.
	 * @return
	 */
	public boolean isStateorhigher(){
		boolean stateorhigher = false;
		List<Organization> userOrganizations = this.getOrganizations();
		if (userOrganizations != null){
			for(Organization org : userOrganizations){
				String orgType = org.getOrganizationType().getTypeCode();
				if("CONS".equalsIgnoreCase(orgType) || "ST".equalsIgnoreCase(orgType)){
					stateorhigher = true;
					break;
				}
			}
		}
		return stateorhigher;
	}
	
    public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();

		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add("");
		}
		
		if(getStatusCode() != null) {
			cells.add(ParsingConstants.BLANK + getStatusCode());
		} else {
			cells.add("");
		}
		
		if(getUniqueCommonIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getUniqueCommonIdentifier());
		} else {
			cells.add("");
		}
		
		if(getFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getFirstName());
		} else {
			cells.add("");
		}

		if(getSurName() != null) {
			cells.add(ParsingConstants.BLANK + getSurName());
		} else {
			cells.add("");
		}
		
		if(getEmail() != null) {
			cells.add(ParsingConstants.BLANK + getEmail());
		} else {
			cells.add("");
		}
		//Add data into Assessment Program column
		if(getProgramNames() != null) {
			cells.add(ParsingConstants.BLANK + getProgramNames());
		} else {
			cells.add("");
		}
		if(getUserRoles() != null) {
			cells.add(ParsingConstants.BLANK + getUserRoles());
		} else {
			cells.add("");
		}
		
		if(getActiveFlag() != true) {
			cells.add(ParsingConstants.BLANK + false);
		} else {
			cells.add(ParsingConstants.BLANK + true);
		}
		
		if(isRosterAssigned()){
			cells.add(ParsingConstants.BLANK + "Assigned Educator"); 
		}else{
			cells.add(ParsingConstants.BLANK + "Avaliable Educators"); 
		}
		
		if(isinternaluserindicator()) {
			cells.add(ParsingConstants.BLANK + isinternaluserindicator());
		} else {
			cells.add(ParsingConstants.BLANK + isinternaluserindicator());
		}
		
		return cells;
	}
	
	public List<String> buildJSONRowRoster() {
		List<String> cells = new ArrayList<String>();

		if(getFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getFirstName());
		} else {
			cells.add("");
		}

		if(getSurName() != null) {
			cells.add(ParsingConstants.BLANK + getSurName());
		} else {
			cells.add("");
		}

		if(getUniqueCommonIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getUniqueCommonIdentifier());
		} else {
			cells.add("");
		}

		if(getStatusCode() != null) {
			cells.add(ParsingConstants.BLANK + getStatusCode());
		} else {
			cells.add("");
		}

		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add("");
		}
		
		if(getEmail() != null) {
			cells.add(ParsingConstants.BLANK + getEmail());
		} else {
			cells.add("");
		}
		
		if(isRosterAssigned()){
			cells.add(ParsingConstants.BLANK + "Assigned Educator"); 
		}else{
			cells.add(ParsingConstants.BLANK + "Avaliable Educators"); 
		}
		
		return cells;
	}

	public long getCurrentGroupsId() {
		return currentGroupsId;
	}

	public void setCurrentGroupsId(long currentGroupsId) {
		this.currentGroupsId = currentGroupsId;
		//resetAuthorities();
	}

	public long getCurrentOrganizationId() {
		return currentOrganizationId;
	}

	public void setCurrentOrganizationId(long currentOrganizationId) {
		this.currentOrganizationId = currentOrganizationId;
		//resetAuthorities();
	}

	public String getCurrentOrganizationType() {
		return currentOrganizationType;
	}

	public void setCurrentOrganizationType(String currentOrganizationType) {
		this.currentOrganizationType = currentOrganizationType;
	}
	
	public long getDefaultOrganizationId() {
		return defaultOrganizationId;
	}

	public void setDefaultOrganizationId(long defaultOrganizationId) {
		this.defaultOrganizationId = defaultOrganizationId;
	}

	public Date getLastExpiredPasswordResetDate() {
		return lastExpiredPasswordResetDate;
	}

	public void setLastExpiredPasswordResetDate(
			Date lastExpiredPasswordResetDate) {
		this.lastExpiredPasswordResetDate = lastExpiredPasswordResetDate;
	}

	public List<Long> getAllChilAndSelfOrgIds() {
		return allChilAndSelfOrgIds;
	}

	public void setAllChilAndSelfOrgIds(List<Long> allChilAndSelfOrgIds) {
		this.allChilAndSelfOrgIds = allChilAndSelfOrgIds;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public String getDistrictID() {
		return districtID;
	}

	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}

	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public boolean isRtComplete() {
		return rtComplete;
	}

	public void setRtComplete(boolean rtComplete) {
		this.rtComplete = rtComplete;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Date getRtCompleteDate() {
		return rtCompleteDate;
	}

	public void setRtCompleteDate(Date rtCompleteDate) {
		this.rtCompleteDate = rtCompleteDate;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}

	public List<String> getAssessmentProgramCodes() {
		return assessmentProgramCodes;
	}

	public void setAssessmentProgramCodes(List<String> assessmentProgramCodes) {
		this.assessmentProgramCodes = assessmentProgramCodes;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	
	/*
	 * Sudhansu :Created for US17270 - For  User Json creation
	 * */
	public String buildJsonString(){
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			//Create Object tree for user object
			JsonNode root = mapper.createObjectNode();
			
			((ObjectNode)root).put("AffectedUserId", this.id);
			((ObjectNode)root).put("Educator Identifier",this.uniqueCommonIdentifier==null? "":this.uniqueCommonIdentifier);
			((ObjectNode)root).put("Email Address",this.email==null? "":this.email);
			((ObjectNode)root).put("First Name",this.firstName==null? "":this.firstName);
			((ObjectNode)root).put("Last Name",this.surName==null? "":this.surName);
			((ObjectNode)root).put("ModifiedDate",getModifiedDate() == null? " ":getModifiedDate().toString());
			((ObjectNode)root).put("ModifiedUser",getModifiedUser());
			((ObjectNode)root).put("CreatedDate",getCreatedDate() == null? " ":getCreatedDate().toString());
			((ObjectNode)root).put("CreatedUser",getCreatedUser());
			((ObjectNode)root).put("SourceType",getSourceType()==null? "":getSourceType());
			((ObjectNode)root).put("ActiveFlag",getActiveFlag());
			((ObjectNode)root).put("Status",getStatusCode()==null? "":getStatusCode());
			((ObjectNode)root).put("Display Name",getDisplayName()==null? "":getDisplayName());
			((ObjectNode)root).put("Default Role",getDefaultUserGroupsId());
			((ObjectNode)root).put("Default Organization",getDefaultOrganizationId());
			((ObjectNode)root).put("Security Agreement",isSecurityAgreementSigned());
			
			//Getting assessment programs
			ArrayNode assessmentNode = mapper.createArrayNode();
			if(this.assessmentProgramCodes!=null){
				for (String s : this.assessmentProgramCodes) {
					assessmentNode.add(s);			
				}
			}
			((ObjectNode)root).set("Assessment Program",assessmentNode);	
		    
			
			//Create a new ArrayNode and add to root i.e organization
            
			
			ArrayNode organizationNode = mapper.createArrayNode();
			
			if(this.userOrganizations!=null){
			for (UserOrganization userOrganization : this.userOrganizations) {
				//Creating new json object for each organization
				ObjectNode orgNode = mapper.createObjectNode();
					orgNode.put("Id",userOrganization.getOrganizationId());
					orgNode.put("OrgName",userOrganization.getOrganizationName()== null? "":userOrganization.getOrganizationName());
					orgNode.put("Default",userOrganization.isIsDefault());
					orgNode.put("CreatedDate",userOrganization.getCreatedDate() == null? " ":userOrganization.getCreatedDate().toString());
					orgNode.put("CreatedUser",userOrganization.getCreatedUser());
					orgNode.put("ModifiedDate",userOrganization.getModifiedDate() == null? " ":userOrganization.getModifiedDate().toString());
					orgNode.put("ModifiedUser",userOrganization.getModifiedUser());
										
					//Create a new ArrayNode and add to organization branch i.e roles
					ArrayNode organizationGroupNode = mapper.createArrayNode();
					
					for (UserRoles userRole : userOrganization.getRoles()) {
							//Creating new json object for each role
							ObjectNode orgGrpNode = mapper.createObjectNode();
							orgGrpNode.put("Id",userRole.getGroupId());
							orgGrpNode.put("Default",userRole.isDefault());
							orgGrpNode.put("Name",userRole.getGroupName()== null? "":userRole.getGroupName());
							orgGrpNode.put("CreatedDate",userRole.getCreatedDate() == null? " ":userRole.getCreatedDate().toString());
							orgGrpNode.put("CreatedUser",userRole.getCreatedUser() == null? 0:userRole.getCreatedUser());
							orgGrpNode.put("ModifiedDate",userRole.getModifiedDate() == null? " ":userRole.getModifiedDate().toString());
							orgGrpNode.put("ModifiedUser",userRole.getModifiedUser());
							
							//Add created roles object to roles array 	
							organizationGroupNode.add(orgGrpNode);
											
					}
				//Set all roles array to each organization  
				orgNode.set("Roles", organizationGroupNode);
				
				//Add created organization object to organization array
				organizationNode.add(orgNode);				
			 }
			
			}
			//Set all roles array to each organization
			((ObjectNode)root).set("Organization", organizationNode);
			
			
       //Convert the whole json object to string
		userJsonStr = mapper.writeValueAsString(root);
	
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return userJsonStr;
	}

	public List<UserOrganizationsGroups> getUserOrganizationsGroups() {
		return userOrganizationsGroups;
	}

	public void setUserOrganizationsGroups(List<UserOrganizationsGroups> serOrganizationsGroups) {
		this.userOrganizationsGroups = serOrganizationsGroups;
	}

	public List<AssessmentProgram> getUserAssessmentPrograms() {
		return userAssessmentPrograms;
	}

	public void setUserAssessmentPrograms(List<AssessmentProgram> userAssessmentPrograms) {
		this.userAssessmentPrograms = userAssessmentPrograms;
	}

	public List<Groups> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<Groups> userGroups) {
		this.userGroups = userGroups;
	}

	public Long getCurrentAssessmentProgramId() {
		return currentAssessmentProgramId;
	}

	public void setCurrentAssessmentProgramId(Long currentAssessmentProgramId) {
		this.currentAssessmentProgramId = currentAssessmentProgramId;
	}

	public long getDefaultAssessmentProgramId() {
		return defaultAssessmentProgramId;
	}

	public void setDefaultAssessmentProgramId(long defaultAssessmentProgramId) {
		this.defaultAssessmentProgramId = defaultAssessmentProgramId;
	}

	public boolean isinternaluserindicator() {
		return internaluserindicator;
	}

	public void setInternaluserindicator(boolean internaluserindicator) {
		this.internaluserindicator = internaluserindicator;
	}
	
	//Deb 6/25 New Attribute ITIStatus;
	public void setIAPNavigationStatus(boolean status) {
		this.iapNavStatus = status;
	}
	public boolean getIAPNavigationStatus() {
		return this.iapNavStatus;
	}
	
	public void setIAPModificationStatus(boolean status) {
		this.iapModStatus = status;
	}
	public boolean getIAPModificationStatus() {
		return this.iapModStatus;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
			
	
}
