package edu.ku.cete.web;

import java.util.Date;

import edu.ku.cete.domain.Externalstudentreports;

public class StudentReportsImportDTO extends ReportsImportDTO {
	private Long studentId;
	private String level1Text;
	private String level2Text;
	private Long schoolId;
	private Long districtId;
	private Long stateId;
	
    private String reportTypeStudent;
    private String dbReportTypeStudent;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getLevel1Text() {
		return level1Text;
	}
	public void setLevel1Text(String level1Text) {
		this.level1Text = level1Text;
	}
	public String getLevel2Text() {
		return level2Text;
	}
	public void setLevel2Text(String level2Text) {
		this.level2Text = level2Text;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getReportTypeStudent() {
		return reportTypeStudent;
	}
	public void setReportTypeStudent(String reportTypeStudent) {
		this.reportTypeStudent = reportTypeStudent;
	}
	public String getDbReportTypeStudent() {
		return dbReportTypeStudent;
	}
	public void setDbReportTypeStudent(String dbReportTypeStudent) {
		this.dbReportTypeStudent = dbReportTypeStudent;
	}
	public Externalstudentreports toExternalStudentReports(){
		Date date = new Date();
		Externalstudentreports reports = new Externalstudentreports();
		reports.setAssessmentProgramid(getAssessmentProgramId());
		reports.setReportProcessId(getBatchReportProcessId());
		reports.setSubjectId(getContentAreaId());
		reports.setFilePath(getPdfFilePath());
		reports.setGradeId(getGradeId());
		reports.setStateId(getStateId());
		reports.setDistrictId(getDistrictId());
		reports.setSchoolId(getSchoolId());
		reports.setSchoolYear(getSchoolYear());
		reports.setReportType(getReportType().trim().equalsIgnoreCase(reportTypeStudent) ? dbReportTypeStudent : getReportType());
		reports.setLevel1_text(getLevel1Text().trim().equals("") ? null : getLevel1Text());
		reports.setLevel2_text(getLevel2Text().trim().equals("") ? null : getLevel2Text());
		reports.setSchoolName(getCurrentSchoolName());
		reports.setDistrictName(getCurrentDistrictName());
		reports.setStudentId(getStudentId());
		reports.setCreatedUser(getCreatedUser());
		reports.setModifiedUser(getModifiedUser());
		reports.setModifiedDate(date);
		reports.setCreatedDate(date);
		return reports;
	}

}
