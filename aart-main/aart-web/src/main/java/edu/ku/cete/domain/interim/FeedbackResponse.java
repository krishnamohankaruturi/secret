package edu.ku.cete.domain.interim;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FeedbackResponse{
	
	private Long responseId;
	private Long testletFeedbackId;
	private Long feedbackQuestionId;
	private Long userId;
	private String response;
	private Boolean responseActive;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date responseCreatedDate;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date responseModifiedDate;
	
	public Long getResponseId() {
		return responseId;
	}
	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}
	public Long getTestletFeedbackId() {
		return testletFeedbackId;
	}
	public void setTestletFeedbackId(Long testletFeedbackId) {
		this.testletFeedbackId = testletFeedbackId;
	}
	public Long getFeedbackQuestionId() {
		return feedbackQuestionId;
	}
	public void setFeedbackQuestionId(Long feedbackQuestionId) {
		this.feedbackQuestionId = feedbackQuestionId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Boolean getResponseActive() {
		return responseActive;
	}
	public void setResponseActive(Boolean responseActive) {
		this.responseActive = responseActive;
	}
	public Date getResponseCreatedDate() {
		return responseCreatedDate;
	}
	public void setResponseCreatedDate(Date responseCreatedDate) {
		this.responseCreatedDate = responseCreatedDate;
	}
	public Date getResponseModifiedDate() {
		return responseModifiedDate;
	}
	public void setResponseModifiedDate(Date responseModifiedDate) {
		this.responseModifiedDate = responseModifiedDate;
	}
	
}