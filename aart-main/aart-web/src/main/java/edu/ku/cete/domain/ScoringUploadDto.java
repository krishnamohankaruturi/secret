package edu.ku.cete.domain;

import java.util.List;
/**
 * Added By Sudhansu.b
 * Feature: f430
 * Scoring Upload
 */
public class ScoringUploadDto {

	private Long id;
	private String assignmentName;
	private List<String> educatorIdentifiers;
	private List<String> stateStudentIdentifiers;
	private Long attendanceSchoolId;
	private List<Long> testIds;
	private Long status;
	
	

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public List<String> getEducatorIdentifiers() {
		return educatorIdentifiers;
	}

	public void setEducatorIdentifiers(List<String> educatorIdentifiers) {
		this.educatorIdentifiers = educatorIdentifiers;
	}

	public List<String> getStateStudentIdentifiers() {
		return stateStudentIdentifiers;
	}

	public void setStateStudentIdentifiers(List<String> stateStudentIdentifiers) {
		this.stateStudentIdentifiers = stateStudentIdentifiers;
	}

	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}

	public List<Long> getTestIds() {
		return testIds;
	}

	public void setTestIds(List<Long> testIds) {
		this.testIds = testIds;
	}

}
