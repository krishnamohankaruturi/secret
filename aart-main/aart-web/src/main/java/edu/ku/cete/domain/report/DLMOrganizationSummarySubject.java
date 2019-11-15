package edu.ku.cete.domain.report;

public class DLMOrganizationSummarySubject {
	
	private Long gradeId;
	private Long subjectId;
	private String subjectName;
	private Long noOfStudentsTested;
	private Long numberOfEmerging;
	private Long numberOfApproachingTarget;
	private Long numberOfAtTarget;
	private Long numberOfAdvanced;
	private int percentageAtTargetAdvanced;
	
	
	public Long getGradeId() {
		return gradeId;
	}
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Long getNoOfStudentsTested() {
		return noOfStudentsTested;
	}
	public void setNoOfStudentsTested(Long noOfStudentsTested) {
		this.noOfStudentsTested = noOfStudentsTested;
	}
	public Long getNumberOfEmerging() {
		return numberOfEmerging;
	}
	public void setNumberOfEmerging(Long numberOfEmerging) {
		this.numberOfEmerging = numberOfEmerging;
	}
	public Long getNumberOfApproachingTarget() {
		return numberOfApproachingTarget;
	}
	public void setNumberOfApproachingTarget(Long numberOfApproachingTarget) {
		this.numberOfApproachingTarget = numberOfApproachingTarget;
	}	
	public Long getNumberOfAtTarget() {
		return numberOfAtTarget;
	}
	public void setNumberOfAtTarget(Long numberOfAtTarget) {
		this.numberOfAtTarget = numberOfAtTarget;
	}
	public Long getNumberOfAdvanced() {
		return numberOfAdvanced;
	}
	public void setNumberOfAdvanced(Long numberOfAdvanced) {
		this.numberOfAdvanced = numberOfAdvanced;
	}
	public int getPercentageAtTargetAdvanced() {
		return percentageAtTargetAdvanced;
	}
	public void setPercentageAtTargetAdvanced(int percentageAtTargetAdvanced) {
		this.percentageAtTargetAdvanced = percentageAtTargetAdvanced;
	}
	

}