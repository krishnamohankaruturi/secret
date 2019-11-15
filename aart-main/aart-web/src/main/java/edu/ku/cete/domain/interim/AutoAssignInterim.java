package edu.ku.cete.domain.interim;

import edu.ku.cete.domain.audit.AuditableDomain;

public class AutoAssignInterim extends AuditableDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3100184281537817331L;
	
	private Long gradeCourseId;
	private Long contentAreaId;
	private Long rosterId;
	private Long testSessionId;
	
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	public Long getTestSessionId() {
		return testSessionId;
	}
	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}

}
