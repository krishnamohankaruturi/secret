package edu.ku.cete.web;

public class PermissionsAndRolesDTO {

	private String assessmentProgram;
	private String state;
	private String tab;
	private String grouping;
	private String label;
	private String permission;
	private String roles;
	private Long assessmentProgramId;
	private Long stateId;
	private Long permissionId;

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
