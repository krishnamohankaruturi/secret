package edu.ku.cete.web;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.ItiTestSessionHistory;

public class DLMBlueprintCoverageReportStudentTestsCriteriaDTO {
	private Long studentId;
	private String studentFirstName;
	private String studentLastName;
	
	private Long criteria;
	private String completedCriteriaStatus;
	
	private Map<String, List<ItiTestSessionHistory>> eesToTests;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public Long getCriteria() {
		return criteria;
	}

	public void setCriteria(Long criteria) {
		this.criteria = criteria;
	}

	public String getCompletedCriteriaStatus() {
		return completedCriteriaStatus;
	}

	public void setCompletedCriteriaStatus(String completedCriteriaStatus) {
		this.completedCriteriaStatus = completedCriteriaStatus;
	}

	public Map<String, List<ItiTestSessionHistory>> getEesToTests() {
		return eesToTests;
	}

	public void setEesToTests(Map<String, List<ItiTestSessionHistory>> eesToTests) {
		this.eesToTests = eesToTests;
	}
}