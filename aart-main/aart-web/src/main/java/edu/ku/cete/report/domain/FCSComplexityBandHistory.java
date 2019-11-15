package edu.ku.cete.report.domain;

import java.util.Date;

import edu.ku.cete.domain.audit.AuditableDomain;

public class FCSComplexityBandHistory{
	
	private Long id;
	private Long studentId;
	private String studentName;
	private String compBandSubject;
	private String previousCompBand;
	private String updatedCompBand;
	private Long schoolYear;
	private Date surveyModifiedDate;
	private Long surveyModifiedUserId;

	public String getCompBandSubject() {
		return compBandSubject;
	}

	public void setCompBandSubject(String compBandSubject) {
		this.compBandSubject = compBandSubject;
	}

	public String getPreviousCompBand() {
		return previousCompBand;
	}

	public void setPreviousCompBand(String previousCompBand) {
		this.previousCompBand = previousCompBand;
	}

	public String getUpdatedCompBand() {
		return updatedCompBand;
	}

	public void setUpdatedCompBand(String updatedCompBand) {
		this.updatedCompBand = updatedCompBand;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Date getSurveyModifiedDate() {
		return surveyModifiedDate;
	}

	public void setSurveyModifiedDate(Date surveyModifiedDate) {
		this.surveyModifiedDate = surveyModifiedDate;
	}

	public Long getSurveyModifiedUserId() {
		return surveyModifiedUserId;
	}

	public void setSurveyModifiedUserId(Long surveyModifiedUserId) {
		this.surveyModifiedUserId = surveyModifiedUserId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
