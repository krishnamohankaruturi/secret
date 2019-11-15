package edu.ku.cete.domain.interim;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = InterimPredictiveStudentScoreSerializer.class)
public class InterimPredictiveStudentScore {
	
	private Long schoolYear;
	private String stateStudentIdentifier;
	private String studentLegalLastName;
	private String studentLegalFirstName;
	private String subject;
	private String districtIdentifer;
	private String districtName;
	private String schoolIdentifier;
	private String schoolName;
	private String grade;
	private List<InterimPredictiveStudentScoreRange> interimPredictiveStudentScoreRange;
	private String summativeScaleScore;
	private String summativeLevel;
	
	public Long getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDistrictIdentifer() {
		return districtIdentifer;
	}
	public void setDistrictIdentifer(String districtIdentifer) {
		this.districtIdentifer = districtIdentifer;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public List<InterimPredictiveStudentScoreRange> getInterimPredictiveStudentScoreRange() {
		return interimPredictiveStudentScoreRange;
	}
	public void setInterimPredictiveStudentScoreRange(
			List<InterimPredictiveStudentScoreRange> interimPredictiveStudentScoreRange) {
		this.interimPredictiveStudentScoreRange = interimPredictiveStudentScoreRange;
	}
	public String getSummativeScaleScore() {
		return summativeScaleScore;
	}
	public void setSummativeScaleScore(String summativeScaleScore) {
		this.summativeScaleScore = summativeScaleScore;
	}
	public String getSummativeLevel() {
		return summativeLevel;
	}
	public void setSummativeLevel(String summativeLevel) {
		this.summativeLevel = summativeLevel;
	}
	
	
	

}
