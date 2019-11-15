package edu.ku.cete.report;

import java.util.List;

public class StudentResponseReport {
	
	private SectionName sectionName;
	private ChartNumber chartNumber;
	private String studentFirstName;
	private String studentMiddleName;
	private String studentLastName;
	private List<QuestionAndResponse> qAndRs;

	public SectionName getSectionName() {
		return sectionName;
	}

	public void setSectionName(SectionName sectionName) {
		this.sectionName = sectionName;
	}
	
	public ChartNumber getChartNumber() {
		return chartNumber;
	}

	public void setChartNumber(ChartNumber chartNumber) {
		this.chartNumber = chartNumber;
	}

	public List<QuestionAndResponse> getqAndRs() {
		return qAndRs;
	}

	public void setqAndRs(List<QuestionAndResponse> qAndRs) {
		this.qAndRs = qAndRs;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentMiddleName() {
		return studentMiddleName;
	}

	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public List<QuestionAndResponse> getQAndRs() {
		return qAndRs;
	}

	public void setQAndRs(List<QuestionAndResponse> qAndRs) {
		this.qAndRs = qAndRs;
	}
}
