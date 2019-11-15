package edu.ku.cete.web;

import java.io.Serializable;

public class TestingReadinessEnrollSubjects implements Serializable {

	private static final long serialVersionUID = 3974237850575282801L;

	private Long studentId;
	private String assessmentProgram;
	private String subjectCode;
	private Long schoolId;
	private String testTypeCode;
	private String subjectName;
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getTestTypeCode() {
		return testTypeCode;
	}

	public void setTestTypeCode(String testTypeCode) {
		this.testTypeCode = testTypeCode;
	}

	public String getSubjectName() {
		return this.subjectName;
	}
	
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}
