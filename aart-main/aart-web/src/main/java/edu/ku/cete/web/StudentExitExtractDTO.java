package edu.ku.cete.web;

import java.util.Date;

public class StudentExitExtractDTO {

	private Long studentId;
	private String stateStudentIdentifier;
	private String legalFirstName;
	private String legalMiddleName;
	private String legalLastName;
	private String generationCode;
	private Date dateOfBirth;
	private String currentGradeLevel;
	private String subject;
	private Long exitCode;
	private String exitCodeDescription;
	private Date exitDate;
	private Date createdDate;
	private Long currentEnrollmentStatus;
	private String stateName;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getLegalFirstName() {
		return legalFirstName;
	}

	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}

	public String getLegalMiddleName() {
		return legalMiddleName;
	}

	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}

	public String getLegalLastName() {
		return legalLastName;
	}

	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}

	public String getGenerationCode() {
		return generationCode;
	}

	public void setGenerationCode(String generationCode) {
		this.generationCode = generationCode;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCurrentGradeLevel() {
		return currentGradeLevel;
	}

	public void setCurrentGradeLevel(String currentGradeLevel) {
		this.currentGradeLevel = currentGradeLevel;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getExitCode() {
		return exitCode;
	}

	public void setExitCode(Long exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitCodeDescription() {
		return exitCodeDescription;
	}

	public void setExitCodeDescription(String exitCodeDescription) {
		this.exitCodeDescription = exitCodeDescription;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCurrentEnrollmentStatus() {
		return currentEnrollmentStatus;
	}

	public void setCurrentEnrollmentStatus(Long currentEnrollmentStatus) {
		this.currentEnrollmentStatus = currentEnrollmentStatus;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
