package edu.ku.cete.domain.test;

import java.io.Serializable;
import java.util.List;

public class TestSectionTaskVariantDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2687621017203312985L;
	
	private Long testSectionTd;
	private Long sectionOrder;
	private String taskVariantId;
	private String taskVariantPosition;
	private String variantStatus;
	private String variantScore;
	private String response;
	private String testletId;
	private boolean testletStatus;
	private boolean PositionChanged;
	private boolean clusterScoring;
	
	private String taskTypeCode;
	private String rubricMinScore;
	private String rubricMaxScore;
	private String variantNonScoreReason;
	
	private Boolean stimulusFlag;
	
	
	
	public String getTaskTypeCode() {
		return taskTypeCode;
	}

	public void setTaskTypeCode(String taskTypeCode) {
		this.taskTypeCode = taskTypeCode;
	}

	public String getRubricMinScore() {
		return rubricMinScore;
	}


	public void setRubricMinScore(String rubricMinScore) {
		this.rubricMinScore = rubricMinScore;
	}


	public String getRubricMaxScore() {
		return rubricMaxScore;
	}


	public void setRubricMaxScore(String rubricMaxScore) {
		this.rubricMaxScore = rubricMaxScore;
	}


	public void setVariantStatus(String variantStatus) {
		this.variantStatus = variantStatus;
	}


	public boolean isClusterScoring() {
		return clusterScoring;
	}

	public void setClusterScoring(boolean clusterScoring) {
		this.clusterScoring = clusterScoring;
	}


	public boolean isPositionChanged() {
		return PositionChanged;
	}


	public void setPositionChanged(boolean positionChanged) {
		PositionChanged = positionChanged;
	}


	public String getVariantStatus() {
		if(this.variantScore!=null && !this.variantScore.isEmpty()){
			this.variantStatus="_Scored";
		} 
		if((this.response != null && !this.response.isEmpty()) && (this.variantScore ==null || this.variantScore.isEmpty())){
			this.variantStatus="_ReadyToScore";
		} 
		if((this.response == null || this.response.isEmpty()) && (this.variantScore ==null || this.variantScore.isEmpty())){
			this.variantStatus="_NoResponse";
		}
		
		return variantStatus;
	}
	
	
	

	public String getVariantScore() {
		return variantScore;
	}


	public void setVariantScore(String variantScore) {
		this.variantScore = variantScore;
	}


	public String getResponse() {
		return response;
	}


	public void setResponse(String response) {
		this.response = response;
	}


	public Long getTestSectionTd() {
		return testSectionTd;
	}
	public void setTestSectionTd(Long testSectionTd) {
		this.testSectionTd = testSectionTd;
	}
	public Long getSectionOrder() {
		return sectionOrder;
	}
	public void setSectionOrder(Long sectionOrder) {
		this.sectionOrder = sectionOrder;
	}
	public String getTaskVariantId() {
		return taskVariantId;
	}
	public void setTaskVariantId(String taskVariantId) {
		this.taskVariantId = taskVariantId;
	}
	public String getTaskVariantPosition() {
		return taskVariantPosition;
	}
	public void setTaskVariantPosition(String taskVariantPosition) {
		this.taskVariantPosition = taskVariantPosition;
	}


	public String getTestletId() {
		return testletId;
	}


	public void setTestletId(String testletId) {
		this.testletId = testletId;
	}


	public boolean isTestletStatus() {
		return testletStatus;
	}


	public void setTestletStatus(boolean testletStatus) {
		this.testletStatus = testletStatus;
	}

	public String getVariantNonScoreReason() {
		return variantNonScoreReason;
	}

	public void setVariantNonScoreReason(String variantNonScoreReason) {
		this.variantNonScoreReason = variantNonScoreReason;
	}

	/**
	 * @return the stimulusFlag
	 */
	public Boolean getStimulusFlag() {
		return stimulusFlag;
	}

	/**
	 * @param stimulusFlag the stimulusFlag to set
	 */
	public void setStimulusFlag(Boolean stimulusFlag) {
		this.stimulusFlag = stimulusFlag;
	}
	

}
