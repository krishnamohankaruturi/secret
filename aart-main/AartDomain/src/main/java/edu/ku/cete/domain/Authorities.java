package edu.ku.cete.domain;

import java.io.Serializable;

import edu.ku.cete.domain.audit.AuditableDomain;

public class Authorities extends AuditableDomain implements Comparable<Authorities>, Serializable {

	private static final long serialVersionUID = 2409930541350917376L;

	private long authoritiesId;
	private String authority;
	private String displayName;
	// As per F579
	private String tabName;
	private String groupingName;
	private String labelName;
	private Long level;
	private Long sortOrder;

	/**
	 * Default Constructor.
	 */
	public Authorities() {
		authoritiesId = 0;
		authority = "";
	}

	/**
	 * Parameterized Constructor.
	 * 
	 * @param authoritiesId
	 * @param authority
	 */
	public Authorities(long authoritiesId, String authority) {
		this.authoritiesId = authoritiesId;
		this.authority = authority;
	}

	/**
	 * @return the authoritiesId
	 */
	public final long getAuthoritiesId() {
		return authoritiesId;
	}

	/**
	 * @param authoritiesId
	 *            the authoritiesId to set
	 */
	public final void setAuthoritiesId(long authoritiesId) {
		this.authoritiesId = authoritiesId;
	}

	/**
	 * @return the authority
	 */
	public final String getAuthority() {
		return authority;
	}

	/**
	 * @param authority
	 *            the authority to set
	 */
	public final void setAuthority(String authority) {
		this.authority = authority;
	}

	/**
	 * @return the displayName
	 */
	public final String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public final void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int compareTo(Authorities o) {
		int result = -1;
		if (o != null && this.getSortOrder() != null && o.getSortOrder() != null && o.getSortOrder() > 0
				&& this.getSortOrder() > 0) {
			result = (int) (this.getSortOrder() - o.getSortOrder());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (authoritiesId ^ (authoritiesId >>> 32));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authorities other = (Authorities) obj;
		if (authoritiesId != other.authoritiesId)
			return false;
		return true;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getGroupingName() {
		return groupingName;
	}

	public void setGroupingName(String groupingName) {
		this.groupingName = groupingName;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

}
