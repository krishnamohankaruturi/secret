package edu.ku.cete.web;

import java.util.List;

import edu.ku.cete.domain.StudentsResponses;

public class QuestarDTO {
	private Long studentId;
	private Long studentsTestsId;
	private Long testSessionId;
	private Long studentsTestSectionsId;
	private List<StudentsResponses> responses;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getStudentsTestsId() {
		return studentsTestsId;
	}
	public void setStudentsTestsId(Long studentsTestsId) {
		this.studentsTestsId = studentsTestsId;
	}
	public Long getTestSessionId() {
		return testSessionId;
	}
	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}
	public Long getStudentsTestSectionsId() {
		return studentsTestSectionsId;
	}
	public void setStudentsTestSectionsId(Long studentsTestSectionsId) {
		this.studentsTestSectionsId = studentsTestSectionsId;
	}
	public List<StudentsResponses> getResponses() {
		return responses;
	}
	public void setResponses(List<StudentsResponses> responses) {
		this.responses = responses;
	}
}