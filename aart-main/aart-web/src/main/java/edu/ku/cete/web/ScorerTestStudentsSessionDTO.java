package edu.ku.cete.web;

import java.io.Serializable;
import java.util.List;

import edu.ku.cete.domain.test.TestSectionTaskVariantDetails;

public class ScorerTestStudentsSessionDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8393089882239319433L;
	private Long id;
	private Long scoringStudentId;
	private String firstName;
	private String mi;
	private String lastName;
	private String stateStudentIdentifier;
	private String districtName;
	private String schoolName;
	private String testingProgramName;
	private String grade;
	private String status;
	private String testName;
	private Long studentsTestsId;
	private Long testsId;
	
	//added for US19326
	private String stateDisplayIdentifier;
	private String districtDisplayIdentifier;
	private String schoolDisplayIdentifier;
	private String scoringAssignmentName;
	private String educatorIdentifier;
	private String educatorLastName;
	private String educatorFirstName;
	private String stage;
	private String subjectName;
	private String stageName;
	private String subjectAbbreviatedName;
	private String testSessionName;
	private Long scoringAssignmentStudentId;
	
	private Long scorerId;
	
	private List<TestSectionTaskVariantDetails> taskVariantDetails;
	
	public Long getScoringAssignmentStudentId() {
		return scoringAssignmentStudentId;
	}
	public void setScoringAssignmentStudentId(Long scoringAssignmentStudentId) {
		this.scoringAssignmentStudentId = scoringAssignmentStudentId;
	}
	public List<TestSectionTaskVariantDetails> getTaskVariantDetails() {
		return taskVariantDetails;
	}
	public void setTaskVariantDetails(
			List<TestSectionTaskVariantDetails> taskVariantDetails) {
		this.taskVariantDetails = taskVariantDetails;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMi() {
		return mi;
	}
	public void setMi(String mi) {
		this.mi = mi;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
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
	public String getTestingProgramName() {
		return testingProgramName;
	}
	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Long getScoringStudentId() {
		return scoringStudentId;
	}
	public void setScoringStudentId(Long scoringStudentId) {
		this.scoringStudentId = scoringStudentId;
	}
	public Long getStudentsTestsId() {
		return studentsTestsId;
	}
	public void setStudentsTestsId(Long studentsTestsId) {
		this.studentsTestsId = studentsTestsId;
	}
	public String getStatus() {
		return status;
	}
	public Long getTestsId() {
		return testsId;
	}
	public void setTestsId(Long testsId) {
		this.testsId = testsId;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStateDisplayIdentifier() {
		return stateDisplayIdentifier;
	}
	public void setStateDisplayIdentifier(String stateDisplayIdentifier) {
		this.stateDisplayIdentifier = stateDisplayIdentifier;
	}
	public String getDistrictDisplayIdentifier() {
		return districtDisplayIdentifier;
	}
	public void setDistrictDisplayIdentifier(String districtDisplayIdentifier) {
		this.districtDisplayIdentifier = districtDisplayIdentifier;
	}
	public String getSchoolDisplayIdentifier() {
		return schoolDisplayIdentifier;
	}
	public void setSchoolDisplayIdentifier(String schoolDisplayIdentifier) {
		this.schoolDisplayIdentifier = schoolDisplayIdentifier;
	}
	
	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}
	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
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
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	
	public String getSubjectAbbreviatedName() {
		return subjectAbbreviatedName;
	}
	public void setSubjectAbbreviatedName(String subjectAbbreviatedName) {
		this.subjectAbbreviatedName = subjectAbbreviatedName;
	}
	
	public String getTestSessionName() {
		return testSessionName;
	}
	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
	public String getScoringAssignmentName() {
		return scoringAssignmentName;
	}
	public void setScoringAssignmentName(String scoringAssignmentName) {
		this.scoringAssignmentName = scoringAssignmentName;
	}
	/**
	 * @return the scorerId
	 */
	public Long getScorerId() {
		return scorerId;
	}
	/**
	 * @param scorerId the scorerId to set
	 */
	public void setScorerId(Long scorerId) {
		this.scorerId = scorerId;
	}
	
	
}
