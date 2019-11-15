/**
 * 
 */
package edu.ku.cete.domain.user;

import java.io.Serializable;
import java.util.Date;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;

/**
 * @author neil.howerton
 *
 */
public class UserOrganizationsGroups extends AuditableDomain implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7054737946107426816L;
	private long id;
    private long userId;
    private Long groupId;
    private long userOrganizationId;
    private Organization organization;
    private Integer status;
    private String activationNo;
    private Date activationNoExpirationDate;
    
    private Groups group;
    private User user;
    private boolean isdefault;
    private boolean userOrganizationDefault;
    /**
     * @return the id
     */
    public final long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(long id) {
        this.id = id;
    }
    /**
     * @return the userId
     */
    public final long getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public final void setUserId(long userId) {
        this.userId = userId;
    }
    /**
     * @return the groupId
     */
    public final Long getGroupId() {
        return groupId;
    }
    /**
     * @param groupId the groupId to set
     */
    public final void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public long getUserOrganizationId() {
		return userOrganizationId;
	}
	public void setUserOrganizationId(long userOrganizationId) {
		this.userOrganizationId = userOrganizationId;
	}
	public Long getOrganizationId() {
    	Organization org = getOrganization();
    	Long organizationId = null;
    	if(org != null) {
    		organizationId = org.getId();
    	}
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		if(organization == null) {
			setOrganization(new Organization());
		}
		organization.setId(organizationId);
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	/**
     *
     *@return {@link User}
     */
    public final User getUser() {
         return this.user;
    }
    
    public void setUser(final User user){
    	this.user = user;
    }

    /**
     * 
     *@return {@link Groups}
     */
    public final Groups getGroup() {
        return this.group;
    }
	
    public void setGroup(final Groups groups){
    	this.group = groups;
    }
    
    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getActivationNo() {
		return activationNo;
	}
	public void setActivationNo(String activationNo) {
		this.activationNo = activationNo;
	}
	public Date getActivationNoExpirationDate() {
		return activationNoExpirationDate;
	}
	public void setActivationNoExpirationDate(Date activationNoExpirationDate) {
		this.activationNoExpirationDate = activationNoExpirationDate;
	}
	/**
	 * @return the isdefault
	 */
	public boolean isIsdefault() {
		return isdefault;
	}
	/**
	 * @param isdefault the isdefault to set
	 */
	public void setIsdefault(boolean isdefault) {
		this.isdefault = isdefault;
	}
	public boolean isUserOrganizationDefault() {
		return userOrganizationDefault;
	}
	public void setUserOrganizationDefault(boolean userOrganizationDefault) {
		this.userOrganizationDefault = userOrganizationDefault;
	}
	
	
    
}
