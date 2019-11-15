package edu.ku.cete.domain.report;

import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.StudentsResponses;

public class StudentReportTestResponses {
	private Long testId;
	private int sortOrder;
	private Long testExternalId;
	private String stageCode;
	
	private Long studentsTestsId;
	private Long studentsTestStatus;
	private List<StudentsResponses> studentResponses;
	
	private Long enrollmentId;
	
	private Date studentsTestsModifiedDate;
	private Long specialCircumstanceId;
	private Boolean ksdeRestricted;
	private Boolean reportScore;
	private Long scCodeStatus;
	
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	
	public Long getTestExternalId() {
		return testExternalId;
	}
	public void setTestExternalId(Long testExternalId) {
		this.testExternalId = testExternalId;
	}
	public List<StudentsResponses> getStudentResponses() {
		return studentResponses;
	}
	public void setStudentsResponses(List<StudentsResponses> studentResponses) {
		this.studentResponses = studentResponses;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getStageCode() {
		return stageCode;
	}
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}
	public Long getStudentsTestsId() {
		return studentsTestsId;
	}
	public void setStudentsTestsId(Long studentsTestsId) {
		this.studentsTestsId = studentsTestsId;
	}
	public Long getStudentsTestStatus() {
		return studentsTestStatus;
	}
	public void setStudentsTestStatus(Long studentsTestStatus) {
		this.studentsTestStatus = studentsTestStatus;
	}
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public Date getStudentsTestsModifiedDate() {
		return studentsTestsModifiedDate;
	}
	public void setStudentsTestsModifiedDate(Date dateStudentsTestsModified) {
		this.studentsTestsModifiedDate = dateStudentsTestsModified;
	}
	public Long getSpecialCircumstanceId() {
		return specialCircumstanceId;
	}
	public void setSpecialCircumstanceId(Long specialCircumstanceId) {
		this.specialCircumstanceId = specialCircumstanceId;
	}
	public Boolean getKsdeRestricted() {
		return ksdeRestricted;
	}
	public void setKsdeRestricted(Boolean ksdeRestricted) {
		this.ksdeRestricted = ksdeRestricted;
	}
	public Boolean getReportScore() {
		return reportScore;
	}
	public void setReportScore(Boolean reportScore) {
		this.reportScore = reportScore;
	}
	public Long getScCodeStatus() {
		return scCodeStatus;
	}
	public void setScCodeStatus(Long scCodeStatus) {
		this.scCodeStatus = scCodeStatus;
	}
}
