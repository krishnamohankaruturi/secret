package edu.ku.cete.domain.report;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterimClassResults {

	private String startDate;

	private String endDate;

	private String totalparticipants;

	private String totalpointsByPercentage;

	private String classHighestpointsByPercentage;

	private String classLowestPointsByPercentage;

	private List<ClassRosters> classRosters;

	private List<String> questions;

	private String byPercentCorrectFilePath;

	private String byItemsFilePath;
	private String testName;
	private String width;

	private List<InterimResultsTypes> interimResultsTypes;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTotalparticipants() {
		return totalparticipants;
	}

	public void setTotalparticipants(String totalparticipants) {
		this.totalparticipants = totalparticipants;
	}

	public String getTotalpointsByPercentage() {
		return totalpointsByPercentage;
	}

	public void setTotalpointsByPercentage(String totalpointsByPercentage) {
		this.totalpointsByPercentage = totalpointsByPercentage;
	}

	public String getClassHighestpointsByPercentage() {
		return classHighestpointsByPercentage;
	}

	public void setClassHighestpointsByPercentage(String classHighestpointsByPercentage) {
		this.classHighestpointsByPercentage = classHighestpointsByPercentage;
	}

	public String getClassLowestPointsByPercentage() {
		return classLowestPointsByPercentage;
	}

	public void setClassLowestPointsByPercentage(String classLowestPointsByPercentage) {
		this.classLowestPointsByPercentage = classLowestPointsByPercentage;
	}

	public List<ClassRosters> getClassRosters() {
		return classRosters;
	}

	public void setClassRosters(List<ClassRosters> classRosters) {
		this.classRosters = classRosters;
	}

	public List<InterimResultsTypes> getInterimResultsTypes() {
		return interimResultsTypes;
	}

	public void setInterimResultsTypes(List<InterimResultsTypes> interimResultsTypes) {
		this.interimResultsTypes = interimResultsTypes;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public String getByPercentCorrectFilePath() {
		return byPercentCorrectFilePath;
	}

	public void setByPercentCorrectFilePath(String byPercentCorrectFilePath) {
		this.byPercentCorrectFilePath = byPercentCorrectFilePath;
	}

	public String getByItemsFilePath() {
		return byItemsFilePath;
	}

	public void setByItemsFilePath(String byItemsFilePath) {
		this.byItemsFilePath = byItemsFilePath;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getWidth() {

		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

}
