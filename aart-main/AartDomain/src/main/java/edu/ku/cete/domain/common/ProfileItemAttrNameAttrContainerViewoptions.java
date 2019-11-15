package edu.ku.cete.domain.common;

import edu.ku.cete.domain.audit.AuditableDomain;

public class ProfileItemAttrNameAttrContainerViewoptions extends AuditableDomain {

	private static final long serialVersionUID = -6590896903811770986L;

	private Long id;

	private Long pianacId;

	private Long assessmentProgramid;

	private String viewOption;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPianacId() {
		return pianacId;
	}

	public void setPianacId(Long pianacId) {
		this.pianacId = pianacId;
	}

	public Long getAssessmentProgramid() {
		return assessmentProgramid;
	}

	public void setAssessmentProgramid(Long assessmentProgramid) {
		this.assessmentProgramid = assessmentProgramid;
	}

	public String getViewOption() {
		return viewOption;
	}

	public void setViewOption(String viewOption) {
		this.viewOption = viewOption;
	}

}
