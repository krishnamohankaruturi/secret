package edu.ku.cete.report.domain;

public class OrganizationReportResults extends ExternalUploadResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6647540684400871234L;
	private Long standardError;
	private Long scaleScore;	
	private String assessmentProgram;
	private Long assessmentProgramId;
	private Long organizationId;
	/**
	 * @return the standardError
	 */
	public Long getStandardError() {
		return standardError;
	}
	/**
	 * @param standardError the standardError to set
	 */
	public void setStandardError(Long standardError) {
		this.standardError = standardError;
	}
	/**
	 * @return the scaleScore
	 */
	public Long getScaleScore() {
		return scaleScore;
	}
	/**
	 * @param scaleScore the scaleScore to set
	 */
	public void setScaleScore(Long scaleScore) {
		this.scaleScore = scaleScore;
	}
	/**
	 * @return the assessmentProgram
	 */
	public String getAssessmentProgram() {
		return assessmentProgram;
	}
	/**
	 * @param assessmentProgram the assessmentProgram to set
	 */
	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	
}
