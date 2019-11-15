package edu.ku.cete.domain.report;

import java.util.List;

public class InterimTestItems {
	private String testName;
	private List<InterimReportTasks> responses;
	private String InterimReportTasksme;
	private String itemName;
	private String allignment;
	private String itemType;
	private String ScoringType;
	private String totalCorrectResponses;
	private String totalCorrect;
	private String totalInCorrect;
	private String maxScore;
	private String taskDefinition;

	public String getTaskDefinition() {
		return taskDefinition;
	}

	public void setTaskDefinition(String taskDefinition) {
		this.taskDefinition = taskDefinition;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public List<InterimReportTasks> getResponses() {
		return responses;
	}

	public void setResponses(List<InterimReportTasks> responses) {
		this.responses = responses;
	}

	public String getInterimReportTasksme() {
		return InterimReportTasksme;
	}

	public void setInterimReportTasksme(String interimReportTasksme) {
		InterimReportTasksme = interimReportTasksme;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getAllignment() {
		return allignment;
	}

	public void setAllignment(String allignment) {
		this.allignment = allignment;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getScoringType() {
		return ScoringType;
	}

	public void setScoringType(String scoringType) {
		ScoringType = scoringType;
	}

	public String getTotalCorrectResponses() {
		return totalCorrectResponses;
	}

	public void setTotalCorrectResponses(String totalCorrectResponses) {
		this.totalCorrectResponses = totalCorrectResponses;
	}

	public String getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(String totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public String getTotalInCorrect() {
		return totalInCorrect;
	}

	public void setTotalInCorrect(String totalInCorrect) {
		this.totalInCorrect = totalInCorrect;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
}