package edu.ku.cete.domain.report;

import java.util.List;

public class AlternateAggregateStudents {
	
	private String legalFirstName;
	private String legalLastName;
	private String educatorFirstName;
	private String educatorLastName;
	private String currentGradelevel;
	private Long studentId;
	private List<AlternateAggregateSubject> alternateAggregateSubject;
	
	public String getLegalFirstName() {
		return legalFirstName;
	}
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	public String getLegalLastName() {
		return legalLastName;
	}
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}	
	public String getEducatorFirstName() {
		return educatorFirstName;
	}
	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}
	public String getEducatorLastName() {
		return educatorLastName;
	}
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}
	public String getCurrentGradelevel() {
		return currentGradelevel;
	}
	public void setCurrentGradelevel(String currentGradelevel) {
		this.currentGradelevel = currentGradelevel;
	}
	public List<AlternateAggregateSubject> getAlternateAggregateSubject() {
		return alternateAggregateSubject;
	}
	public void setAlternateAggregateSubject(
			List<AlternateAggregateSubject> alternateAggregateSubject) {
		this.alternateAggregateSubject = alternateAggregateSubject;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
}
