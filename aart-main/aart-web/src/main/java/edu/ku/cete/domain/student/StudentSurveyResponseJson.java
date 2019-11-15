package edu.ku.cete.domain.student;


import java.util.Date;


public class StudentSurveyResponseJson {
	private Long globalPageNumber;	
	private String responseText;
	private Date createdDate;
	private Date modifiedDate;
	private Long createdUserId;
	private Long modifiedUserId;
	private String responseValue;
	private Long responseOrder;
	private String responseLabel;
	private String labelNumber;
	private String label;
	private boolean responseActiveFlag;
	private String labelType;
	
	public Long getGlobalPageNumber() {
		return globalPageNumber;
	}
	public void setGlobalPageNumber(Long globalPageNumber) {
		this.globalPageNumber = globalPageNumber;
	}
	public String getResponseText() {
		return responseText;
	}
	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}
	public Long getModifiedUserId() {
		return modifiedUserId;
	}
	public void setModifiedUserId(Long modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
	public String getResponseValue() {
		return responseValue;
	}
	public void setResponseValue(String responseValue) {
		this.responseValue = responseValue;
	}
	public Long getResponseOrder() {
		return responseOrder;
	}
	public void setResponseOrder(Long responseOrder) {
		this.responseOrder = responseOrder;
	}
	public String getResponseLabel() {
		return responseLabel;
	}
	public void setResponseLabel(String responseLabel) {
		this.responseLabel = responseLabel;
	}
	public String getLabelNumber() {
		return labelNumber;
	}
	public void setLabelNumber(String labelNumber) {
		this.labelNumber = labelNumber;
	}	
	public boolean isResponseActiveFlag() {
		return responseActiveFlag;
	}
	public void setResponseActiveFlag(boolean responseActiveFlag) {
		this.responseActiveFlag = responseActiveFlag;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabelType() {
		return labelType;
	}
	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}	
}
