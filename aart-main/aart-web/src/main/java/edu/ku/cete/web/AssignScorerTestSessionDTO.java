package edu.ku.cete.web;

import java.util.Date;

public class AssignScorerTestSessionDTO {
	
	private Long id;
	private String testsessionName;
	private Date createdDate;
	private int studentCount;
	private String districtName;
	private String schoolName;
	private String testingProgramName;
	
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
	public int getStudentCount() {
		return studentCount;
	}
	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

}
