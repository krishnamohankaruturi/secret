package edu.ku.cete.web;

public class ReportAccessDTO {
	
	private Long id;
	private Long authorityId;
	private String reportName;
	private String permission;
	private String subject;
	private String rolesAccess;
	private String rolesHavePerm;
	private Long stateId; 
	private String organizationName; 
	private Long reporttypeId;
	private Long subjectId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRolesAccess() {
		return rolesAccess;
	}
	public void setRolesAccess(String rolesAccess) {
		this.rolesAccess = rolesAccess;
	}
	public String getRolesHavePerm() {
		return rolesHavePerm;
	}
	public void setRolesHavePerm(String rolesHavePerm) {
		this.rolesHavePerm = rolesHavePerm;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Long getReporttypeId() {
		return reporttypeId;
	}
	public void setReporttypeId(Long reporttypeId) {
		this.reporttypeId = reporttypeId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
		
	
}
