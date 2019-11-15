package edu.ku.cete.domain.announcements;

import java.sql.Timestamp;
import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;

public class CommunicationMessage extends AuditableDomain {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;
	private String messageTitle;
	private String messageContent;
	private int messageId;
	private String assessmentName;
	private Long assessmentId;
	private String stateName;
	private Long stateId;
	private Long assessmentProgramId;
	private int stateProgramId;
	private int rolesId;
	private List<Long> stateProgramIdList;
	private List<Long> rolesIdList;
	private String status;
	private String displayMessageDate;
	private String expireMessageDate;
	private String messageStatus;

	private String displayMessagetime;
	private String expireMessagetime;
	private Timestamp displayDate;
	private Timestamp expiryDate;
	private int organizationId;

	
	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public List<Long> getStateProgramIdList() {
		return stateProgramIdList;
	}

	public void setStateProgramIdList(List<Long> stateProgramIdList) {
		this.stateProgramIdList = stateProgramIdList;
	}

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public Timestamp getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(Timestamp displayDate) {
		this.displayDate = displayDate;
	}

	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getDisplayMessagetime() {
		return displayMessagetime;
	}

	public void setDisplayMessagetime(String displayMessagetime) {
		this.displayMessagetime = displayMessagetime;
	}

	public String getExpireMessagetime() {
		return expireMessagetime;
	}

	public void setExpireMessagetime(String expireMessagetime) {
		this.expireMessagetime = expireMessagetime;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getDisplayMessageDate() {
		return displayMessageDate;
	}

	public void setDisplayMessageDate(String displayMessageDate) {
		this.displayMessageDate = displayMessageDate;
	}

	public String getExpireMessageDate() {
		return expireMessageDate;
	}

	public void setExpireMessageDate(String expireMessageDate) {
		this.expireMessageDate = expireMessageDate;
	}

	public String getAssessmentName() {
		return assessmentName;
	}

	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}

	public Long getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Long> getRolesIdList() {
		return rolesIdList;
	}

	public void setRolesIdList(List<Long> rolesIdList) {
		this.rolesIdList = rolesIdList;
	}

	public int getStateProgramId() {
		return stateProgramId;
	}

	public void setStateProgramId(int stateProgramId) {
		this.stateProgramId = stateProgramId;
	}

	public int getRolesId() {
		return rolesId;
	}

	public void setRolesId(int rolesId) {
		this.rolesId = rolesId;
	}

}
