package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;

public class GroupRestrictions extends AuditableDomain {
	private static final long serialVersionUID = 5317652169021433870L;
	
	// Persistent fields
	private Long groupId;
	private Long organizationId;
	private Long assessmentProgramId;
	private Boolean singleUser;
	
	// non persistent fields
	private String groupCode;
	private String organizationType;

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Boolean isSingleUser() {
		return singleUser;
	}

	public void setSingleUser(Boolean singleUser) {
		this.singleUser = singleUser;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getOrganizationType() {
		return organizationType;
	}

	public void setOrganizationType(String organizationType) {
		this.organizationType = organizationType;
	}

}
