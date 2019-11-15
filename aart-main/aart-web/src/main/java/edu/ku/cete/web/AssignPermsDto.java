package edu.ku.cete.web;

import java.util.List;

import edu.ku.cete.domain.Authorities;

public class AssignPermsDto {

	private List<Authorities> authorities;
	private String typeName;

	/**
	 * @return the authorities
	 */
	public final List<Authorities> getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities
	 *            the authorities to set
	 */
	public final void setAuthorities(List<Authorities> authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the typeName
	 */
	public final String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 *            the typeName to set
	 */
	public final void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
