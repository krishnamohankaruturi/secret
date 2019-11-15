package edu.ku.cete.web;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CreateMessageDto implements Serializable{
	
	private String messageTitle;
	private String messageContent;
	private int messageId;
	private String assessmentName;
	private int assessmentId;
	private int assessmentProgramId;
	private int stateProgramId;
	private List<Integer> stateProgramIdList;
	private String status;
	private String displayMessageDate;
	private String expireMessageDate;
	private String messageStatus;
	private List<CreateMessageDto> stateList;
	private String displayMessagetime;
	private String expireMessagetime;
	private Date createdDate;
	private Timestamp displayDate;
	private Timestamp expiryDate;
	private int createdUser;
	private int organizationId;
	private boolean activeFlag;
	
	public List<Integer> getStateProgramIdList() {
		return stateProgramIdList;
	}
	
	public void setStateProgramIdList(List<Integer> stateProgramIdList) {
		this.stateProgramIdList = stateProgramIdList;
	}
	
	public boolean isActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	public int getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}
	public int getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(int createdUser) {
		this.createdUser = createdUser;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public List<CreateMessageDto> getStateList() {
		return stateList;
	}
	public void setStateList(List<CreateMessageDto> stateList) {
		this.stateList = stateList;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public int getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(int assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public int getStateProgramId() {
		return stateProgramId;
	}
	public void setStateProgramId(int stateProgramId) {
		this.stateProgramId = stateProgramId;
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
	public int getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(int assessmentId) {
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
    
    
}

