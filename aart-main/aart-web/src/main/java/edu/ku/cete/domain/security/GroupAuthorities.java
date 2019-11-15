/**
 * 
 */
package edu.ku.cete.domain.security;

import java.io.Serializable;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author neil.howerton
 *
 */
public class GroupAuthorities extends AuditableDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long groupId;
	private long authorityId;
	private long organizationId;
	private long assessmentProgramId;

	private String orgName;
	private String assessmentProgram;
	private String groupName;
	private String authorityName;

	/**
	 * @return the groupAuthoritiesId
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param groupAuthoritiesId
	 *            the groupAuthoritiesId to set
	 */
	public final void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public final long getGroupId() {
		return groupId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public final void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the groupId
	 */
	public final long getAuthorityId() {
		return authorityId;
	}

	/**
	 * @param groupId
	 *            the groupId to set
	 */
	public final void setAuthorityId(long authorityId) {
		this.authorityId = authorityId;
	}

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	public long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
