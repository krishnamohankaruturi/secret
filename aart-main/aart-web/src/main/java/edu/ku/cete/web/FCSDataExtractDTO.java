package edu.ku.cete.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author amani
 */
public class FCSDataExtractDTO implements Serializable {

	private static final long serialVersionUID = 960391084198728522L;
	private Long studentId;
	private String stateName;
	private String districtName;
	private String schoolName;
	private String stateStudentIdentifier;
	private String studentLastName;
	private String studentFirstName;
	private String studentMiddleInitial;
	private String grade;
	private String surveyStatus;
	private Date lastModifiedDate;
	private String lastModifiedUser;
	private List<FCSAnswer> answers;

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getStudentLastName() {
		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}

	public String getStudentMiddleInitial() {
		return studentMiddleInitial;
	}

	public void setStudentMiddleInitial(String studentMiddleInitial) {
		this.studentMiddleInitial = studentMiddleInitial;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSurveyStatus() {
		return surveyStatus;
	}

	public void setSurveyStatus(String surveyStatus) {
		this.surveyStatus = surveyStatus;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedUser() {
		return lastModifiedUser;
	}

	public void setLastModifiedUser(String lastModifiedUser) {
		this.lastModifiedUser = lastModifiedUser;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<FCSAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<FCSAnswer> answers) {
		this.answers = answers;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

}
