package edu.ku.cete.report.domain;

public class AuditType {
	private Long id ;
	private String auditName;
	private String dispalyName;
	private boolean activeFlag;
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
	 * @return the auditName
	 */
	public String getAuditName() {
		return auditName;
	}
	/**
	 * @param auditName the auditName to set
	 */
	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}
	/**
	 * @return the dispalyName
	 */
	public String getDispalyName() {
		return dispalyName;
	}
	/**
	 * @param dispalyName the dispalyName to set
	 */
	public void setDispalyName(String dispalyName) {
		this.dispalyName = dispalyName;
	}
	/**
	 * @return the activeFlag
	 */
	public boolean isActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
}
