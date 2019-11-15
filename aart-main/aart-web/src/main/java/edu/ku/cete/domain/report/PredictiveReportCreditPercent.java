package edu.ku.cete.domain.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 25, 2017 4:06:15 PM
 */
public class PredictiveReportCreditPercent {
    
    private Long id;
    
    private Long schoolYear;
    
    private Long assessmentProgramId;
    
    private Long testingProgramId;
    
    private String reportCycle;
    
    private Long organizationId;
    
    private Long organizationTypeId;
    
    private Long gradeId;
    
    private Long contentAreaId;
    
    private Long testId;
    
    private Long externalTestId;
    
    private Long taskVariantId;
    
    private Long taskVariantExternalId;
    
    private Integer taskVariantPosition;
    
    private Long questionInformationId;
    
    private Long creditTypeId;
    
    private Integer fullCreditPercent;
    
    private Integer fullCreditStudentCount;

    private Integer testAttemptedStudentCount;
    
    private Integer unAnsweredStudentCount;
    
    private Long batchReportProcessId;
    
    private Long createdUser;
    
    private Long modifiedUser;
    
    private Date createdDate;
    
    private Date modifiedDate;
    
    private String gradeCode;
    
    private String subjectName;

    private Long attendanceSchoolId;
    
    private String schoolName;
    
    private String schoolDisplayId;
    
    private Long districtId;
    
    private String districtName;
    
    private String districtDisplayId;
    
    private List<StudentReportQuestionInfo> reportQuestionInformation = new ArrayList<StudentReportQuestionInfo>();
    
    private String shortOrgName;
        
    private String interimSummaryHeaderSchoolName;
    
    private String interimSummaryHeaderDistrictName;
    
    private String predictiveSchoolYear;
    
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

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
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

	public Long getTaskVariantId() {
		return taskVariantId;
	}

	public void setTaskVariantId(Long taskVariantId) {
		this.taskVariantId = taskVariantId;
	}

	public Long getTaskVariantExternalId() {
		return taskVariantExternalId;
	}

	public void setTaskVariantExternalId(Long taskVariantExternalId) {
		this.taskVariantExternalId = taskVariantExternalId;
	}

	public Integer getTaskVariantPosition() {
		return taskVariantPosition;
	}

	public void setTaskVariantPosition(Integer taskVariantPosition) {
		this.taskVariantPosition = taskVariantPosition;
	}

	public Long getQuestionInformationId() {
		return questionInformationId;
	}

	public void setQuestionInformationId(Long questionInformationId) {
		this.questionInformationId = questionInformationId;
	}

	public Long getCreditTypeId() {
		return creditTypeId;
	}

	public void setCreditTypeId(Long creditTypeId) {
		this.creditTypeId = creditTypeId;
	}

	public Integer getFullCreditPercent() {
		return fullCreditPercent;
	}

	public void setFullCreditPercent(Integer fullCreditPercent) {
		this.fullCreditPercent = fullCreditPercent;
	}

	public Integer getFullCreditStudentCount() {
		return fullCreditStudentCount;
	}

	public void setFullCreditStudentCount(Integer fullCreditStudentCount) {
		this.fullCreditStudentCount = fullCreditStudentCount;
	}

	public Integer getTestAttemptedStudentCount() {
		return testAttemptedStudentCount;
	}

	public void setTestAttemptedStudentCount(Integer testAttemptedStudentCount) {
		this.testAttemptedStudentCount = testAttemptedStudentCount;
	}

	public Integer getUnAnsweredStudentCount() {
		return unAnsweredStudentCount;
	}

	public void setUnAnsweredStudentCount(Integer unAnsweredStudentCount) {
		this.unAnsweredStudentCount = unAnsweredStudentCount;
	}

	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolDisplayId() {
		return schoolDisplayId;
	}

	public void setSchoolDisplayId(String schoolDisplayId) {
		this.schoolDisplayId = schoolDisplayId;
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

	public String getDistrictDisplayId() {
		return districtDisplayId;
	}

	public void setDistrictDisplayId(String districtDisplayId) {
		this.districtDisplayId = districtDisplayId;
	}

	public List<StudentReportQuestionInfo> getReportQuestionInformation() {
		return reportQuestionInformation;
	}

	public void setReportQuestionInformation(
			List<StudentReportQuestionInfo> reportQuestionInformation) {
		this.reportQuestionInformation = reportQuestionInformation;
	}

	public String getShortOrgName() {
		return shortOrgName;
	}

	public void setShortOrgName(String shortOrgName) {
		this.shortOrgName = shortOrgName;
	}

	public String getInterimSummaryHeaderSchoolName() {
		return interimSummaryHeaderSchoolName;
	}

	public void setInterimSummaryHeaderSchoolName(
			String interimSummaryHeaderSchoolName) {
		this.interimSummaryHeaderSchoolName = interimSummaryHeaderSchoolName;
	}

	public String getInterimSummaryHeaderDistrictName() {
		return interimSummaryHeaderDistrictName;
	}

	public void setInterimSummaryHeaderDistrictName(
			String interimSummaryHeaderDistrictName) {
		this.interimSummaryHeaderDistrictName = interimSummaryHeaderDistrictName;
	}

	public String getPredictiveSchoolYear() {
		return this.predictiveSchoolYear;
	}

	public void setPredictiveSchoolYear(String predictiveSchoolYear) {
		this.predictiveSchoolYear = predictiveSchoolYear;
	}
    
}