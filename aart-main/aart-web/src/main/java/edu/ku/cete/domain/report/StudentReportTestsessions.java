package edu.ku.cete.domain.report;

import java.util.List;

public class StudentReportTestsessions {
	private Long testsessionId;
	
	List<StudentReportTestResponses> StudentReportTestResponses;

	public Long getTestsessionId() {
		return testsessionId;
	}

	public void setTestsessionId(Long testsessionId) {
		this.testsessionId = testsessionId;
	}

	public List<StudentReportTestResponses> getStudentReportTestResponses() {
		return StudentReportTestResponses;
	}

	public void setStudentReportTestResponses(List<StudentReportTestResponses> studentReportTestResponses) {
		StudentReportTestResponses = studentReportTestResponses;
	}
	
	 
}
