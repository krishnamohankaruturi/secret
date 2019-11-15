package edu.ku.cete.domain.report;

public class ReportAssessmentProgramDetails extends ReportAssessmentProgram {
	
	String programName;
	String abbreviatedName;
	String reportCode;
	private Boolean readyToView;
	private String categoryName;
	private Long assessmentProgramId;
	private Boolean access;
	
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getAbbreviatedName() {
		return abbreviatedName;
	}
	public void setAbbreviatedName(String abbreviatedName) {
		this.abbreviatedName = abbreviatedName;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	/**
	 * @return the readyToView
	 */
	public Boolean getReadyToView() {
		return readyToView;
	}
	/**
	 * @param readyToView the readyToView to set
	 */
	public void setReadyToView(Boolean readyToView) {
		this.readyToView = readyToView;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	/**
	 * @return the assessmentProgramId
	 */
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	/**
	 * @param assessmentProgramId the assessmentProgramId to set
	 */
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	/**
	 * @return the access
	 */
	public Boolean getAccess() {
		return access;
	}
	/**
	 * @param access the access to set
	 */
	public void setAccess(Boolean access) {
		this.access = access;
	}
	
}
