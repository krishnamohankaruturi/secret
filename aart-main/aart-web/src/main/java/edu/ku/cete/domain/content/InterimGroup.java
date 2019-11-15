package edu.ku.cete.domain.content;

import java.util.Date;

import edu.ku.cete.domain.audit.AuditableDomain;

public class InterimGroup extends AuditableDomain {
	private static final long serialVersionUID = -2429096779740066460L;
	private Long id;
	private String groupName;
	private Date createDate;
	private Date modifiedDate;
	private Long createdUser;
	private Long modifiedUser;
	private Boolean activeFlag;
	private Boolean access;

	private Long organizationId;

	public Long getId() {
		return id;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Boolean getAccess() {
		return access;
	}

	public void setAccess(Boolean access) {
		this.access = access;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof InterimGroup))
			return false;
		InterimGroup interimGroup = (InterimGroup) obj;
		if (this.getId().equals(interimGroup.getId())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

}
