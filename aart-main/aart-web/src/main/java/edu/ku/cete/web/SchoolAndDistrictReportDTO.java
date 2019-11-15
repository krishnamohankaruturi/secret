package edu.ku.cete.web;

import edu.ku.cete.domain.report.OrganizationReportDetails;

public class SchoolAndDistrictReportDTO extends OrganizationReportDetails {
	private String gradeName;
	private String subjectName;
	private String assessmentProgramCode;
	private String organizationname;
	private String action;
	
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}
	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
	public String getOrganizationname() {
		return organizationname;
	}
	public void setOrganizationname(String organizationname) {
		this.organizationname = organizationname;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}