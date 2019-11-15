package edu.ku.cete.web;

import java.util.Date;

public class ScoreTestTestSessionDTO {
	
	private Long id;
	private Long scoringAssignmentId;
	private Long scorerId;
	private String testsessionName;
	private Date createdDate;
	private String testingProgramName;
	private int studentCount;
	private int scorercount;
	private int studentsscoredcount;
	private String ccqTestName;
	private String subject;
	private String grade;
	private String districtName;
	private String schoolName;
	private Integer totalrecords;	
	
	public Integer getTotalrecords() {
		return totalrecords;
	}
	public void setTotalrecords(Integer totalrecords) {
		this.totalrecords = totalrecords;
	}
	public String getCcqTestName() {
		return ccqTestName;
	}
	public void setCcqTestName(String ccqTestName) {
		this.ccqTestName = ccqTestName;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getScoringAssignmentId() {
		return scoringAssignmentId;
	}
	public void setScoringAssignmentId(Long scoringAssignmentId) {
		this.scoringAssignmentId = scoringAssignmentId;
	}
	public String getTestsessionName() {
		return testsessionName;
	}
	public void setTestsessionName(String testsessionName) {
		this.testsessionName = testsessionName;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getTestingProgramName() {
		return testingProgramName;
	}
	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}
	public int getScorercount() {
		return scorercount;
	}
	public void setScorercount(int scorercount) {
		this.scorercount = scorercount;
	}
	public int getStudentsscoredcount() {
		return studentsscoredcount;
	}
	public void setStudentsscoredcount(int studentsscoredcount) {
		this.studentsscoredcount = studentsscoredcount;
	}
	public Long getScorerId() {
		return scorerId;
	}
	public void setScorerId(Long scorerId) {
		this.scorerId = scorerId;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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
}
