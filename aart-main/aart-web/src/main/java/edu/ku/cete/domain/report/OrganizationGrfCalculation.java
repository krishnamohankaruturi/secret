package edu.ku.cete.domain.report;

import java.io.Serializable;
import java.util.List;
import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;

public class OrganizationGrfCalculation extends AuditableDomain implements Serializable, Identifiable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private Long id;
	private Long batchUploadProcessId;
	private Long assessmentProgramId;
	private Long stateId;
	private String stateName;
	private Long districtId;
	private String districtName;
	private String districtShortName;
	private String districtCode;
	private Long reportYear;	
	private Long gradeId;
	private String gradeLevel;
	private Long subjectId;
	private String subjectName;
	private Long noOfStudentsTested;
	private Long numberOfEmerging;
	private Long numberOfApproachingTarget;
	private Long numberOfAtTarget;
	private Long numberOfAdvanced;
	private int percentageAtTargetAdvanced;
	private List<DLMOrganizationSummaryGrade> orgSummaryGradeLists;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBatchUploadProcessId() {
		return batchUploadProcessId;
	}
	public void setBatchUploadProcessId(Long batchUploadProcessId) {
		this.batchUploadProcessId = batchUploadProcessId;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}	
	public String getDistrictShortName() {
		return districtShortName;
	}
	public void setDistrictShortName(String districtShortName) {
		this.districtShortName = districtShortName;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}	
	public Long getReportYear() {
		return reportYear;
	}
	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	
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
	@Override
	public Long getId(int order) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStringIdentifier(int order) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<DLMOrganizationSummaryGrade> getOrgSummaryGradeLists() {
		return orgSummaryGradeLists;
	}
	public void setOrgSummaryGradeLists(
			List<DLMOrganizationSummaryGrade> orgSummaryGradeLists) {
		this.orgSummaryGradeLists = orgSummaryGradeLists;
	}	

}