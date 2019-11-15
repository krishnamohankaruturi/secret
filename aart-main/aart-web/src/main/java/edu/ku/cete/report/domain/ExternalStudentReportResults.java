package edu.ku.cete.report.domain;

public class ExternalStudentReportResults extends ExternalUploadResult {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5741320896928794131L;
	private String assessmentProgram;
	private Long assessmentProgramId;
	private Long scaleScore;
	private Float standardError;
	private Long achievementLevel;
	private Boolean completedFlag;
	private String stateStudentIdentifier;
	private Long studentId;
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
	 * @return the standardError
	 */
	public Float getStandardError() {
		return standardError;
	}
	/**
	 * @param standardError the standardError to set
	 */
	public void setStandardError(Float standardError) {
		this.standardError = standardError;
	}
	/**
	 * @return the achievementLevel
	 */
	public Long getAchievementLevel() {
		return achievementLevel;
	}
	/**
	 * @param achievementLevel the achievementLevel to set
	 */
	public void setAchievementLevel(Long achievementLevel) {
		this.achievementLevel = achievementLevel;
	}
	/**
	 * @return the completedFlag
	 */
	public Boolean getCompletedFlag() {
		return completedFlag;
	}
	/**
	 * @param completedFlag the completedFlag to set
	 */
	public void setCompletedFlag(Boolean completedFlag) {
		this.completedFlag = completedFlag;
	}
	/**
	 * @return the stateStudentIdentifier
	 */
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	/**
	 * @param stateStudentIdentifier the stateStudentIdentifier to set
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

}

