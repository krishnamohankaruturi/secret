package edu.ku.cete.domain.interim;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FeedbackQuestionAndResponse{
	
	private Logger logger = LoggerFactory.getLogger(FeedbackQuestionAndResponse.class);

	private Long questionId;
	private Long testletFeedbackId;
	private Long testletExternalId;
	private String taskType;
	private Long questionSequence;
	private String questionText;
	private JsonNode questionOptions;
	private Boolean required;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date startDate;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date endDate;
	private Long assessmentProgramId;
	private Long contentAreaId;
	private Long questionCreatedUser;
	private Long questionModifiedUser;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date questionCreatedDate;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	private Date questionModifiedDate;
	
	private Long responseId;
	private Long userId;
	private String response;
	private Boolean responseActive;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date responseCreatedDate;
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
	private Date responseModifiedDate;
	
	public Long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	public Long getTestletFeedbackId() {
		return testletFeedbackId;
	}
	public void setTestletFeedbackId(Long testletFeedbackId) {
		this.testletFeedbackId = testletFeedbackId;
	}
	public Long getTestletExternalId() {
		return testletExternalId;
	}
	public void setTestletExternalId(Long testletExternalId) {
		this.testletExternalId = testletExternalId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Long getQuestionSequence() {
		return questionSequence;
	}
	public void setQuestionSequence(Long questionSequence) {
		this.questionSequence = questionSequence;
	}
	public String getQuestionText() {
		return questionText;
	}
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	public JsonNode getQuestionOptions() {
		return questionOptions;
	}
	public void setQuestionOptions(String questionOptions) {
		JsonNode optionsNode = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			optionsNode = mapper.readTree(questionOptions);
		}catch(JsonMappingException e) {
			
		} catch (JsonProcessingException e) {
			logger.info(e.getMessage());
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		this.questionOptions = optionsNode;
	}
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public Long getQuestionCreatedUser() {
		return questionCreatedUser;
	}
	public void setQuestionCreatedUser(Long questionCreatedUser) {
		this.questionCreatedUser = questionCreatedUser;
	}
	public Long getQuestionModifiedUser() {
		return questionModifiedUser;
	}
	public void setQuestionModifiedUser(Long questionModifiedUser) {
		this.questionModifiedUser = questionModifiedUser;
	}
	public Date getQuestionCreatedDate() {
		return questionCreatedDate;
	}
	public void setQuestionCreatedDate(Date questionCreatedDate) {
		this.questionCreatedDate = questionCreatedDate;
	}
	public Date getQuestionModifiedDate() {
		return questionModifiedDate;
	}
	public void setQuestionModifiedDate(Date questionModifiedDate) {
		this.questionModifiedDate = questionModifiedDate;
	}
	public Long getResponseId() {
		return responseId;
	}
	public void setResponseId(Long responseId) {
		this.responseId = responseId;
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