/**
 * 
 */
package edu.ku.cete.domain.report;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 25, 2017 3:30:18 PM
 */
public class PredictiveReportOrganization{

	private Long id;
	
	private Long schoolYear;
	
	private Long assessmentProgramId;
	
	private Long testingProgramId;
	
	private String reportCycle;
	
	private Long contentAreaId;
	
	private Long gradeId;
	
	private Long organizationId;
	
	private Long organizationTypeId;
	
	private Long batchReportProcessId;

	private String orgTypeCode;
	
	private Long testId;
	
	private Long externalTestId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public String getReportCycle() {
		return reportCycle;
	}

	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getOrganizationTypeId() {
		return organizationTypeId;
	}

	public void setOrganizationTypeId(Long organizationTypeId) {
		this.organizationTypeId = organizationTypeId;
	}

	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public String getOrgTypeCode() {
		return orgTypeCode;
	}

	public void setOrgTypeCode(String orgTypeCode) {
		this.orgTypeCode = orgTypeCode;
	}

	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public Long getExternalTestId() {
		return externalTestId;
	}

	public void setExternalTestId(Long externalTestId) {
		this.externalTestId = externalTestId;
	}
	
	
}
