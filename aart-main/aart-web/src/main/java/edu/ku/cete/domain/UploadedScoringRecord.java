package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.property.ValidateableRecord;

public class UploadedScoringRecord extends ValidateableRecord implements
                Serializable {
	/**
	 * Added By Sudhansu.b
	 * Feature: f430
	 * Scoring Upload
	 */
	private static final long serialVersionUID = 1L;
	private Long documentId;
	private Long testId;
	private String legalMiddleName;
	private String assignmentName;
	private String stage;
	private String subject;
	private String grade;
	private String stateStudentIdentifier;
	private String lastName;
	private String firstName;
	private String educatorLastName;
	private String educatorFirstName;
	private String educatorIdentifier;
	private String schoolID;
	private String districtID;
	private String state;
	private Long createdUser;
	private Long modifieduser;
	private Map<String, Object> items = new HashMap<String, Object>();
	private CcqScore ccqScore;
	private List<StudentsTestScore> studentsScores = new ArrayList<StudentsTestScore>();
	private List<StudentsTestScore> removedStudentsScores = new ArrayList<StudentsTestScore>();
	private String assessmentProgramCode;	

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public CcqScore getCcqScore() {
		return ccqScore;
	}

	public void setCcqScore(CcqScore ccqScore) {
		this.ccqScore = ccqScore;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Long getModifieduser() {
		return modifieduser;
	}

	public void setModifieduser(Long modifieduser) {
		this.modifieduser = modifieduser;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getLegalMiddleName() {
		return legalMiddleName;
	}

	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}

	
	public String getStage() {
		return stage;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEducatorLastName() {
		return educatorLastName;
	}

	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}

	public String getEducatorFirstName() {
		return educatorFirstName;
	}

	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}

	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}

	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
	}

	public String getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(String schoolID) {
		this.schoolID = schoolID;
	}

	public String getDistrictID() {
		return districtID;
	}

	public void setDistrictID(String districtID) {
		this.districtID = districtID;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, Object> getItems() {
		return items;
	}

	public void setItems(Map<String, Object> items) {
		this.items = items;
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StudentsTestScore> getStudentsScores() {
		return studentsScores;
	}

	public void setStudentsScores(List<StudentsTestScore> studentsScores) {
		this.studentsScores = studentsScores;
	}

	public List<StudentsTestScore> getRemovedStudentsScores() {
		return removedStudentsScores;
	}

	public void setRemovedStudentsScores(List<StudentsTestScore> removedStudentsScores) {
		this.removedStudentsScores = removedStudentsScores;
	}

}
