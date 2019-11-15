package edu.ku.cete.domain.user;

import java.io.Serializable;
import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * Sudhansu.b : US17270 :To bring user organization and groups 
 */
public class UserOrganization extends AuditableDomain implements
		Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long userId;
	private String userName;
	private Long organizationId;
	private String organizationName;
	private boolean isDefault;
	private List<UserRoles> roles;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public boolean isIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isdefault) {
		this.isDefault = isdefault;
	}

	public List<UserRoles> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRoles> roles) {
		this.roles = roles;
	}

}
