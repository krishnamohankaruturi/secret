/**
 * 
 */
package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author neil.howerton
 *
 */
public class Groups extends AuditableDomain implements Serializable, Comparable<Groups> {

	/**
	 *
	 */
	private static final long serialVersionUID = 5271283974602043391L;
	private long groupId;
	private long organizationId;
	private String groupName;
	private boolean defaultGroup;
	private Organization organization;
	private List<Authorities> authoritiesList;
	private Long organizationTypeId;
	private OrganizationType organizationType;
	private boolean fromParent;
	private boolean allowedToAssign;
	private long roleOrgTypeId;
	private boolean isDefault;
	private boolean singleUser;
	// Changed during - US16550
	private String groupCode;
	private Long hierarchy;
	
	private boolean isdepreciated;

	public boolean isSingleUser() {
		return singleUser;
	}

	public void setSingleUser(boolean singleUser) {
		this.singleUser = singleUser;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Groups() {
		groupId = 0;
		organizationId = 0;
		groupName = "";
		organizationType = new OrganizationType();
	}

	public Groups(long organizationId, String groupName) {
		this.organizationId = organizationId;
		this.groupName = groupName;
	}

	public Groups(long grId) {
		this.groupId = grId;
	}

	/**
	 * @return the id
	 */
	public final long getGroupId() {
		return groupId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the organizationId
	 */
	public final long getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId
	 *            the organizationId to set
	 */
	public final void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the groupName
	 */
	public final String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public final void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the defaultRole
	 */
	public boolean isDefaultGroup() {
		return defaultGroup;
	}

	/**
	 * @param defaultRole
	 *            the defaultRole to set
	 */
	public void setDefaultRole(boolean defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	/**
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization
	 *            the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the fromParent
	 */
	public boolean isFromParent() {
		return fromParent;
	}

	/**
	 * @param fromParent
	 *            the fromParent to set
	 */
	public void setFromParent(boolean fromParent) {
		this.fromParent = fromParent;
	}

	public int compareTo(Groups o) {
		int result = -1;
		if (o != null && o.getGroupId() > 0 && this.getGroupId() > 0) {
			result = (int) (this.getGroupId() - o.getGroupId());
		}
		return result;
	}

	public List<String> buildJSONRowForRolesPage() {
		List<String> cells = new ArrayList<String>();

		if (getGroupId() > 0) {
			cells.add(ParsingConstants.BLANK + getGroupId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if (getGroupName() != null) {
			cells.add(ParsingConstants.BLANK + getGroupName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if (getModifiedDate() != null) {
			cells.add(DateUtil.format(getModifiedDate(), "MM-dd-yyyy hh:mm a"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		return cells;
	}

	public List<Authorities> getAuthoritiesList() {
		return authoritiesList;
	}

	public void setAuthoritiesList(List<Authorities> authoritiesList) {
		this.authoritiesList = authoritiesList;
	}

	public void setDefaultGroup(boolean defaultGroup) {
		this.defaultGroup = defaultGroup;
	}

	public Long getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(Long organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public OrganizationType getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(OrganizationType organizationType) {
		this.organizationType = organizationType;
	}

	public boolean isAllowedToAssign() {
		return allowedToAssign;
	}

	public void setAllowedToAssign(boolean allowedToAssign) {
		this.allowedToAssign = allowedToAssign;
	}

	public long getRoleorgtypeid() {
		return roleOrgTypeId;
	}

	public void setRoleorgtypeid(long roleOrgTypeId) {
		this.roleOrgTypeId = roleOrgTypeId;
	}

	public boolean isIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isIsdepreciated() {
		return isdepreciated;
	}

	public void setIsdepreciated(boolean isdepreciated) {
		this.isdepreciated = isdepreciated;
	}

	public Long getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Long hierarchy) {
		this.hierarchy = hierarchy;
	}
}
