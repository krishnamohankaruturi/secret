package edu.ku.cete.domain.report;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.enrollment.Enrollment;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 5, 2017 12:28:44 PM
 */
public class InterimStudentReport {
    
    private Long id;
    
    private Long studentId;
    
    private String stateStudentIdentifier;
    
    private String studentFirstName;
    
    private String studentLastName;
    
    private String studentMiddleName;
    
    private String stateStudentId;
    
    private Long enrollmentId;
    
    private Long gradeId;
    
    private String gradeCode;
    
    private Long contentAreaId;
    
    private String subjectName;
    
    private String contentAreaCode;
    
    private Long attendanceSchoolId;
    
    private String schoolName;
    
    private String schoolDisplayId;
    
    private Long districtId;
    
    private String districtName;
    
    private String districtDisplayId;
    
    private Long stateId;
    
    private Long schoolYear;
    
    private Long assessmentProgramId;
    
    private Long testingProgramId;
    
    private String reportCycle;
    
    private Long testId;
    
    private Long externalTestId;
    
    private BigDecimal rawScore;
    
    private Long scaleScore;
    
    private BigDecimal standardError;
    
    private Boolean status;
    
    private Long scoreRangeDisplayReasonId;
    
    private Boolean exitStatus;
    
    private Boolean transferred;
    
    private Integer totalIncludedItemCount;
    
    private Integer respondedItemCount;
    
    private Integer excludedItemCount;
    
    private String filePath;
    
    private Boolean generated;
    
    private Long batchReportProcessId;
    
    private Long operationalTestWindowId;
    
    private Date createdDate;
    
    private Date modifiedDate;
    
    private Long createdUser;
    
    private Long modifiedUser;

    private List<StudentReportTestResponses> studentTestsScore;
    
    private List<StudentReportQuestionInfo> reportQuestionInformation;
    
    private List<Enrollment> enrollments;
    
    private Long currentSchoolYear;
    
    private Long studentsTestStatus;
    
    private String assessmentProgramCode;
    
    private Boolean isProcessBySpecificStudentId;
    
    private Long StudentReportReprocessId;
    
    private boolean StudentReportReprocessStatus;
    
    private Map<Long, BigDecimal> taskVariantScoreMap = new HashMap<Long, BigDecimal>();
    
    private String reasonCode;
    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getStudentId() {
        return studentId;
    }

    
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    
    public String getStateStudentIdentifier() {
    	if(stateStudentIdentifier == null)
    		stateStudentIdentifier = stateStudentId;
		return stateStudentIdentifier;
	}


	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}


	public Long getEnrollmentId() {
        return enrollmentId;
    }

    
    public void setEnrollmentId(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
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
        this.schoolName = schoolName == null ? null : schoolName.trim();
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
        this.districtName = districtName == null ? null : districtName.trim();
    }

    
    public Long getStateId() {
        return stateId;
    }

    
    public void setStateId(Long stateId) {
        this.stateId = stateId;
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
        this.reportCycle = reportCycle == null ? null : reportCycle.trim();
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

    
    public BigDecimal getRawScore() {
        return rawScore;
    }

    
    public void setRawScore(BigDecimal rawScore) {
        this.rawScore = rawScore;
    }

    
    public Long getScaleScore() {
        return scaleScore;
    }

    
    public void setScaleScore(Long scaleScore) {
        this.scaleScore = scaleScore;
    }

    
    public BigDecimal getStandardError() {
        return standardError;
    }

    
    public void setStandardError(BigDecimal standardError) {
        this.standardError = standardError;
    }

    
    public Boolean getStatus() {
        return status;
    }

    
    public void setStatus(Boolean status) {
        this.status = status;
    }

    
    public Long getScoreRangeDisplayReasonId() {
        return scoreRangeDisplayReasonId;
    }

    
    public void setScoreRangeDisplayReasonId(Long scoreRangeDisplayReasonId) {
        this.scoreRangeDisplayReasonId = scoreRangeDisplayReasonId;
    }

    
    public Boolean getExitStatus() {
        return exitStatus;
    }

    
    public void setExitStatus(Boolean exitStatus) {
        this.exitStatus = exitStatus;
    }

    
    public Boolean getTransferred() {
        return transferred;
    }

    
    public void setTransferred(Boolean transferred) {
        this.transferred = transferred;
    }

    
    public Integer getTotalIncludedItemCount() {
        return totalIncludedItemCount;
    }

    
    public void setTotalIncludedItemCount(Integer totalIncludedItemCount) {
        this.totalIncludedItemCount = totalIncludedItemCount;
    }

    
    public Integer getRespondedItemCount() {
        return respondedItemCount;
    }

    
    public void setRespondedItemCount(Integer respondedItemCount) {
        this.respondedItemCount = respondedItemCount;
    }

    
    public Integer getExcludedItemCount() {
        return excludedItemCount;
    }

    
    public void setExcludedItemCount(Integer excludedItemCount) {
        this.excludedItemCount = excludedItemCount;
    }

    
    public String getFilePath() {
        return filePath;
    }

    
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    
    public Boolean getGenerated() {
        return generated;
    }

    
    public void setGenerated(Boolean generated) {
        this.generated = generated;
    }

    
    public Long getBatchReportProcessId() {
        return batchReportProcessId;
    }

    
    public void setBatchReportProcessId(Long batchReportProcessId) {
        this.batchReportProcessId = batchReportProcessId;
    }

    
    public Long getOperationalTestWindowId() {
        return operationalTestWindowId;
    }

    
    public void setOperationalTestWindowId(Long operationalTestWindowId) {
        this.operationalTestWindowId = operationalTestWindowId;
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


	public List<StudentReportTestResponses> getStudentTestsScore() {
		return studentTestsScore;
	}


	public void setStudentTestsScore(List<StudentReportTestResponses> studentTestsScore) {
		this.studentTestsScore = studentTestsScore;
	}


	public List<StudentReportQuestionInfo> getReportQuestionInformation() {
		return reportQuestionInformation;
	}


	public void setReportQuestionInformation(List<StudentReportQuestionInfo> reportQuestionInformation) {
		this.reportQuestionInformation = reportQuestionInformation;
	}


	public List<Enrollment> getEnrollments() {
		return enrollments;
	}


	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}


	public Long getCurrentSchoolYear() {
		return currentSchoolYear;
	}


	public void setCurrentSchoolYear(Long currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}


	public Long getStudentsTestStatus() {
		return studentsTestStatus;
	}


	public void setStudentsTestStatus(Long studentsTestStatus) {
		this.studentsTestStatus = studentsTestStatus;
	}


	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}


	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}


	public Boolean getIsProcessBySpecificStudentId() {
		return isProcessBySpecificStudentId;
	}


	public void setIsProcessBySpecificStudentId(Boolean isProcessBySpecificStudentId) {
		this.isProcessBySpecificStudentId = isProcessBySpecificStudentId;
	}


	public Long getStudentReportReprocessId() {
		return StudentReportReprocessId;
	}


	public void setStudentReportReprocessId(Long studentReportReprocessId) {
		StudentReportReprocessId = studentReportReprocessId;
	}


	public boolean isStudentReportReprocessStatus() {
		return StudentReportReprocessStatus;
	}


	public void setStudentReportReprocessStatus(boolean studentReportReprocessStatus) {
		StudentReportReprocessStatus = studentReportReprocessStatus;
	}


	public Map<Long, BigDecimal> getTaskVariantScoreMap() {
		return taskVariantScoreMap;
	}


	public void setTaskVariantScoreMap(Map<Long, BigDecimal> taskVariantScoreMap) {
		this.taskVariantScoreMap = taskVariantScoreMap;
	}


	public String getStudentFirstName() {
		return studentFirstName;
	}


	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}


	public String getStudentLastName() {
		return studentLastName;
	}


	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}


	public String getStudentMiddleName() {
		return studentMiddleName;
	}


	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
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


	public String getSchoolDisplayId() {
		return schoolDisplayId;
	}


	public void setSchoolDisplayId(String schoolDisplayId) {
		this.schoolDisplayId = schoolDisplayId;
	}


	public String getDistrictDisplayId() {
		return districtDisplayId;
	}


	public void setDistrictDisplayId(String districtDisplayId) {
		this.districtDisplayId = districtDisplayId;
	}


	public String getStateStudentId() {
		return stateStudentId;
	}


	public void setStateStudentId(String stateStudentId) {
		this.stateStudentId = stateStudentId;
	}


	public String getContentAreaCode() {
		return contentAreaCode;
	}


	public void setContentAreaCode(String contentAreaCode) {
		this.contentAreaCode = contentAreaCode;
	}


	public String getReasonCode() {
		return reasonCode;
	}


	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
}