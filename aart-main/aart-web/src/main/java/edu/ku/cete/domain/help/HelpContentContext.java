package edu.ku.cete.domain.help;

import edu.ku.cete.domain.audit.AuditableDomain;

public class HelpContentContext extends AuditableDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long id;
	private long assessmentProgramId;
	private long stateId;
	private long rolesId;
	private Long helpContentId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public long getStateId() {
		return stateId;
	}

	public void setStateId(long stateId) {
		this.stateId = stateId;
	}

	public long getRolesId() {
		return rolesId;
	}

	public void setRolesId(long rolesId) {
		this.rolesId = rolesId;
	}

	public Long getHelpContentId() {
		return helpContentId;
	}

	public void setHelpContentId(Long helpContentId) {
		this.helpContentId = helpContentId;
	}

}
