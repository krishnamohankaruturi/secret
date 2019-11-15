package edu.ku.cete.domain.content;

import java.util.Date;

import edu.ku.cete.domain.audit.AuditableDomain;

public class InterimGroupStudent extends AuditableDomain {

	private static final long serialVersionUID = 378864900448482193L;
	private Long studentId;
	private Long interimGroupId;
	private Date createDate;
	private Long createdUser;
	private Boolean activeFlag;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Boolean getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getInterimGroupId() {
		return interimGroupId;
	}

	public void setInterimGroupId(Long interimGroupId) {
		this.interimGroupId = interimGroupId;
	}

	@Override
	public String toString() {
		return "InterimGroupStudent [studentId=" + studentId + ", interimGroupId=" + interimGroupId + ", createDate="
				+ createDate + ", createdUser=" + createdUser + ", activeFlag=" + activeFlag + "]";
	}

}
