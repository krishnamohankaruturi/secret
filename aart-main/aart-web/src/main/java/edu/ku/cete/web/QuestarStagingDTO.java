package edu.ku.cete.web;

import java.util.List;

import edu.ku.cete.report.domain.QuestarStagingRecord;
import edu.ku.cete.report.domain.QuestarStagingResponse;

public class QuestarStagingDTO extends QuestarStagingRecord {
	private String assessmentRefId;
	private String assessmentName;
	private List<QuestarStagingResponse> responses;
	
	public String getAssessmentRefId() {
		return assessmentRefId;
	}
	public void setAssessmentRefId(String assessmentRefId) {
		this.assessmentRefId = assessmentRefId;
	}
	public String getAssessmentName() {
		return assessmentName;
	}
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}
	public List<QuestarStagingResponse> getResponses() {
		return responses;
	}
	public void setResponses(List<QuestarStagingResponse> responses) {
		this.responses = responses;
	}
}