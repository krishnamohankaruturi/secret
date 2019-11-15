package edu.ku.cete.domain.report;

public class ReportAssessmentProgramGroups {
	 
	private Long id;
	
	private Long reportAssessmentProgramId; 
	
	private Long groupId;
	
	private Boolean activeFlag;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getReportAssessmentProgramId() {
		return reportAssessmentProgramId;
	}
	public void setReportAssessmentProgramId(Long reportAssessmentProgramId) {
		this.reportAssessmentProgramId = reportAssessmentProgramId;
	}
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	public Boolean getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	
}
