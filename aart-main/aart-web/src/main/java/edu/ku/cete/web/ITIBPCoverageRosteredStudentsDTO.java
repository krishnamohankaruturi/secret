package edu.ku.cete.web;

public class ITIBPCoverageRosteredStudentsDTO {

	private Long rosterId;
	private Long enrollmentId;
	private Long studentId;
	private Long enrollmentRosterId;
	private String statestudentIdentifier;
	private String studentFirstName;
	private String studentLastName;
	
	public Long getRosterId() {
		return rosterId;
	}
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getEnrollmentRosterId() {
		return enrollmentRosterId;
	}
	public void setEnrollmentRosterId(Long enrollmentRosterId) {
		this.enrollmentRosterId = enrollmentRosterId;
	}
	public String getStatestudentIdentifier() {
		return statestudentIdentifier;
	}
	public void setStatestudentIdentifier(String statestudentIdentifier) {
		this.statestudentIdentifier = statestudentIdentifier;
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
}
