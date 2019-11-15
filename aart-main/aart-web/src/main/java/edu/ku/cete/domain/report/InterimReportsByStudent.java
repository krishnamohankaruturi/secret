package edu.ku.cete.domain.report;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterimReportsByStudent {
	private String testSessionName;
	private String stateId;
	private String totalpointsOrpercentage;
	private String testStartDate;
	private String testEndDate;
	private String testDuration;
	private String firstName;
	private String lastName;
	private String totalPercentage;
	private String totalPoint;
	private String maxTotalScore;
	public String getMaxTotalScore() {
		return maxTotalScore;
	}

	public void setMaxTotalScore(String maxTotalScore) {
		this.maxTotalScore = maxTotalScore;
	}

	private List<InterimStudentResponse> interimStudentResponse;

	public String getTestSessionName() {
		return testSessionName;
	}

	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getTotalpointsOrpercentage() {
		return totalpointsOrpercentage;
	}

	public void setTotalpointsOrpercentage(String totalpointsOrpercentage) {
		this.totalpointsOrpercentage = totalpointsOrpercentage;
	}

	public String getTestStartDate() {
		return testStartDate;
	}

	public void setTestStartDate(String timestamp) {
		this.testStartDate = timestamp;
	}

	public String getTestEndDate() {
		return testEndDate;
	}

	public void setTestEndDate(String string) {
		this.testEndDate = string;
	}

	public String getTestDuration() {
		return testDuration;
	}

	public void setTestDuration(String testDuration) {
		this.testDuration = testDuration;
	}

	public List<InterimStudentResponse> getInterimStudentResponse() {
		return interimStudentResponse;
	}

	public void setInterimStudentResponse(List<InterimStudentResponse> interimStudentResponse) {
		this.interimStudentResponse = interimStudentResponse;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTotalPercentage() {
		return totalPercentage;
	}

	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public String getTotalPoint() {
		return totalPoint;
	}

	public void setTotalPoint(String totalPoint) {
		this.totalPoint = totalPoint;
	}
}