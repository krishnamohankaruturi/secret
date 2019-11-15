/**
 * 
 */
package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ktaduru_sta
 *
 */
public class KAPStudentScoreDTO {

	private Long studentId;
	private String stateStudentIdentifier;
	private String studentLegalLastName;
	private String studentLegalFirstName;
	private String studentLegalMiddleName;
	private String subject;
	private String subjectCode;
	private String currentEnrolledDistrictIdentifier;
	private String currentEnrolledDistrictName;
	private String currentEnrolledAypSchoolIdentifier;
	private String currentEnrolledAypSchoolName;
	private String currentEnrolledATTSchoolIdentifier;
	private String currentEnrolledATTSchoolName;
	private String currentEnrolledGradeLevel;
	private String reportSchoolIdentifier;
	private Long reportYear;
	
	private List<StudentScoreDTO> studentScores = new ArrayList<StudentScoreDTO>();

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

	public String getStudentLegalLastName() {
		return studentLegalLastName;
	}

	public void setStudentLegalLastName(String studentLegalLastName) {
		this.studentLegalLastName = studentLegalLastName;
	}

	public String getStudentLegalFirstName() {
		return studentLegalFirstName;
	}

	public void setStudentLegalFirstName(String studentLegalFirstName) {
		this.studentLegalFirstName = studentLegalFirstName;
	}

	public String getStudentLegalMiddleName() {
		return studentLegalMiddleName;
	}

	public void setStudentLegalMiddleName(String studentLegalMiddleName) {
		this.studentLegalMiddleName = studentLegalMiddleName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getCurrentEnrolledDistrictIdentifier() {
		return currentEnrolledDistrictIdentifier;
	}

	public void setCurrentEnrolledDistrictIdentifier(String currentEnrolledDistrictIdentifier) {
		this.currentEnrolledDistrictIdentifier = currentEnrolledDistrictIdentifier;
	}

	public String getCurrentEnrolledDistrictName() {
		return currentEnrolledDistrictName;
	}

	public void setCurrentEnrolledDistrictName(String currentEnrolledDistrictName) {
		this.currentEnrolledDistrictName = currentEnrolledDistrictName;
	}

	public String getCurrentEnrolledAypSchoolIdentifier() {
		return currentEnrolledAypSchoolIdentifier;
	}

	public void setCurrentEnrolledAypSchoolIdentifier(String currentEnrolledAypSchoolIdentifier) {
		this.currentEnrolledAypSchoolIdentifier = currentEnrolledAypSchoolIdentifier;
	}

	public String getCurrentEnrolledAypSchoolName() {
		return currentEnrolledAypSchoolName;
	}

	public void setCurrentEnrolledAypSchoolName(String currentEnrolledAypSchoolName) {
		this.currentEnrolledAypSchoolName = currentEnrolledAypSchoolName;
	}

	public String getCurrentEnrolledATTSchoolIdentifier() {
		return currentEnrolledATTSchoolIdentifier;
	}

	public void setCurrentEnrolledATTSchoolIdentifier(String currentEnrolledATTSchoolIdentifier) {
		this.currentEnrolledATTSchoolIdentifier = currentEnrolledATTSchoolIdentifier;
	}

	public String getCurrentEnrolledATTSchoolName() {
		return currentEnrolledATTSchoolName;
	}

	public void setCurrentEnrolledATTSchoolName(String currentEnrolledATTSchoolName) {
		this.currentEnrolledATTSchoolName = currentEnrolledATTSchoolName;
	}

	public String getCurrentEnrolledGradeLevel() {
		return currentEnrolledGradeLevel;
	}

	public void setCurrentEnrolledGradeLevel(String currentEnrolledGradeLevel) {
		this.currentEnrolledGradeLevel = currentEnrolledGradeLevel;
	}

	public String getReportSchoolIdentifier() {
		return reportSchoolIdentifier;
	}

	public void setReportSchoolIdentifier(String reportSchoolIdentifier) {
		this.reportSchoolIdentifier = reportSchoolIdentifier;
	}

	public Long getReportYear() {
		return reportYear;
	}

	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}

	public List<StudentScoreDTO> getStudentScores() {
		return studentScores;
	}

	public void setStudentScores(List<StudentScoreDTO> studentScores) {
		this.studentScores = studentScores;
	}
	
	
}
