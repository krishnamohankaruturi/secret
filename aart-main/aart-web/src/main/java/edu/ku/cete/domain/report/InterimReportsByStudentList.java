package edu.ku.cete.domain.report;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterimReportsByStudentList {
	private List<InterimReportsByStudent> studentList;
	private String testName;

	public List<InterimReportsByStudent> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<InterimReportsByStudent> studentList) {
		this.studentList = studentList;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}
