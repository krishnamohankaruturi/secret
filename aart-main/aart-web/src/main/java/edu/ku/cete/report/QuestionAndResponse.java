package edu.ku.cete.report;

public class QuestionAndResponse{
	private Long taskVariantId;
	private Integer taskIdentifier;
	private Long foilId;
	private String response;
	private boolean isCorrectResponse;
	
	public Long getTaskVariantId() {
		return taskVariantId;
	}
	public void setTaskVariantId(Long taskVariantId) {
		this.taskVariantId = taskVariantId;
	}
	public Long getFoilId() {
		return foilId;
	}
	public void setFoilId(Long foilId) {
		this.foilId = foilId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public boolean isCorrectResponse() {
		return isCorrectResponse;
	}
	public void setCorrectResponse(boolean isCorrectResponse) {
		this.isCorrectResponse = isCorrectResponse;
	}
	public Integer getTaskIdentifier() {
		return taskIdentifier;
	}
	public void setTaskIdentifier(Integer taskIdentifier) {
		this.taskIdentifier = taskIdentifier;
	}
}