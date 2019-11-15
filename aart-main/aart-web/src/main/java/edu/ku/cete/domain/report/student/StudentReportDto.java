package edu.ku.cete.domain.report.student;

import java.util.List;

public class StudentReportDto {
	
	private String reportDate;
	private String stateStudentIdentifier;
	private String studentFirstName;
	private String studentLastName;
	private String schoolName;
	private String districtName;
	private String stateName;
	private String contentAreaName;
	private String gradeName;
	private long schoolId;
	private List<StudentRptCCArea> claimsConceptualData;
	private String resourcePath;
	private int schoolYear;
	private long studentId;
	
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getContentAreaName() {
		return contentAreaName;
	}
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}
	public List<StudentRptCCArea> getClaimsConceptualData() {
		return claimsConceptualData;
	}
	public void setClaimsConceptualData(List<StudentRptCCArea> claimsConceptualData) {
		this.claimsConceptualData = claimsConceptualData;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public String getResourcePath() {
		return resourcePath;
	}
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	public int getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(int schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
	 
	 
	
}