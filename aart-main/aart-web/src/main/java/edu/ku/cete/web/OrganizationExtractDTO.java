package edu.ku.cete.web;

import java.util.Date;

public class OrganizationExtractDTO {
	
	private String orgDisplayIdentifier;
	private String organizationName;
	private String orgLevel;
	private String parentOrgDisplayIdentifier;
	private String parentOrganizationName;
	private String mergedOrgDisplayIdentifier;
	private Date startDate;	
	private Date endDate;
	private String assessmentPrograms;
	private String abbreviatedProgramNames;
	private String testingModel;
	private String status;
	private int reportYear;
	private Date lastModifiedDate;
	private String lastModifiedName;
	
	public String getOrgDisplayIdentifier() {
		return orgDisplayIdentifier;
	}
	public void setOrgDisplayIdentifier(String orgDisplayIdentifier) {
		this.orgDisplayIdentifier = orgDisplayIdentifier;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public String getOrgLevel() {
		return orgLevel;
	}
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}
	public String getParentOrgDisplayIdentifier() {
		return parentOrgDisplayIdentifier;
	}
	public void setParentOrgDisplayIdentifier(String parentOrgDisplayIdentifier) {
		this.parentOrgDisplayIdentifier = parentOrgDisplayIdentifier;
	}
	public String getParentOrganizationName() {
		return parentOrganizationName;
	}
	public void setParentOrganizationName(String parentOrganizationName) {
		this.parentOrganizationName = parentOrganizationName;
	}
	public String getMergedOrgDisplayIdentifier() {
		return mergedOrgDisplayIdentifier;
	}
	public void setMergedOrgDisplayIdentifier(String mergedOrgDisplayIdentifier) {
		this.mergedOrgDisplayIdentifier = mergedOrgDisplayIdentifier;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAssessmentPrograms() {
		return assessmentPrograms;
	}
	public void setAssessmentPrograms(String assessmentPrograms) {
		this.assessmentPrograms = assessmentPrograms;
	}
	public String getAbbreviatedProgramNames() {
		return abbreviatedProgramNames;
	}
	public void setAbbreviatedProgramNames(String abbreviatedProgramNames) {
		this.abbreviatedProgramNames = abbreviatedProgramNames;
	}
	public String getTestingModel() {
		return testingModel;
	}
	public void setTestingModel(String testingModel) {
		this.testingModel = testingModel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getReportYear() {
		return reportYear;
	}
	public void setReportYear(int reportYear) {
		this.reportYear = reportYear;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getLastModifiedName() {
		return lastModifiedName;
	}
	public void setLastModifiedName(String lastModifiedName) {
		this.lastModifiedName = lastModifiedName;
	}
}
