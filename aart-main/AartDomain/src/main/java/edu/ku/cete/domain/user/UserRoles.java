package edu.ku.cete.domain.user;

import java.io.Serializable;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * Sudhansu.b : US17270 :To bring user organization and groups 
 */
public class UserRoles extends AuditableDomain implements Serializable {


	private static final long serialVersionUID = 7543299672365889663L;
	private Long id;
	private Long groupId;
	private String groupName;
	private boolean isDefault;
	private Long userOrganizationId;
	private int status;
	

	public Long getId() {
		return id;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Long getUserOrganizationId() {
		return userOrganizationId;
	}

	public void setUserOrganizationId(Long userOrganizationId) {
		this.userOrganizationId = userOrganizationId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
