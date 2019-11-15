package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.property.ValidateableRecord;

public class UploadedPermissionRecord extends ValidateableRecord implements
Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Added By Uday
	 * Feature: f885
	 * Permission Upload
	 */
	private String assessmentProgram;
	private Long assessmentProgramId;
	private String state;
	private List<Long> stateIds=new ArrayList();
	private String tab;
	private String grouping;
	private String label;
	private String permission;
	private Long permissionId;
	private Map<String, String> roles = new HashMap<String, String>();
	private String lineNumber;
	private Long userId;
	
	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	public List<Long> getStateIds() {
		return stateIds;
	}

	public void setStateIds(List<Long> stateIds) {
		this.stateIds = stateIds;
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
		
	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Map<String, String> getRoles() {
		return roles;
	}

	public void setRoles(Map<String, String> roles) {
		this.roles = roles;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}