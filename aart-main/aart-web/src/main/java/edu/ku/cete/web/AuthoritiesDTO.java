package edu.ku.cete.web;

import edu.ku.cete.domain.Authorities;

public class AuthoritiesDTO {

	private Boolean assigned;
	private Authorities authority;
	private Boolean restricted;
	
	/**
	 * @return the assigned
	 */
	public Boolean getAssigned() {
		return assigned;
	}
	/**
	 * @param assigned the assigned to set
	 */
	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}
	/**
	 * @return the authority
	 */
	public Authorities getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(Authorities authority) {
		this.authority = authority;
	}
	
	public Boolean getRestricted() {
		return restricted;
	}
	public void setRestricted(Boolean restricted) {
		this.restricted = restricted;
	}
	
	@Override
	public boolean equals(Object obj) {
		AuthoritiesDTO dto = (AuthoritiesDTO) obj;
		return this.getAssigned().equals(dto.getAssigned()) 
				& this.getAuthority().getAuthority().equals(dto.getAuthority().getAuthority());
	}
}
