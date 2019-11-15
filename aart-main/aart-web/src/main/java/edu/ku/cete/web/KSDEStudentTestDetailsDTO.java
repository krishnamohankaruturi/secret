package edu.ku.cete.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KSDEStudentTestDetailsDTO {
	private Long studentid;
	private String stateStudentIdentifier;
	private Long enrollmentId;
	private String aypSchoolIdentifier;
	private String testGrade;
	private String studentGrade;
	private String abbrSubject;
	private String testTypeCode;
	private Long contentAreaId;
	private Long gradeId;
	private Long attendanceSchoolId;
	private Long scaleScore;
	private BigDecimal elaCombinedPrfrmLevel;
	private BigDecimal mdptScore;
	private Boolean mdptScorableFlag;
	private Long performanceLevel;
	
	private Long stage1_testId;
	private Long stage2_testId;
	private Long stage3_testId;
	private Long perf_testId;
	private int ksdeFileCode;	
	private Long stage1_questionCount;
	private Long stage2_questionCount;
	private Long stage3_questionCount;
	private Long performanceStageQuestionCount;	
	private Long stage1_respondedCount;
	private Long stage2_respondedCount;
	private Long stage3_respondedCount;
	private Long perf_respondedCount;	
	private String elaMdptScorableFlag;	
	private String stage1_ksdeSCCode;
	private String stage2_ksdeSCCode;
	private String stage3_ksdeSCCode;
	private String perf_ksdeSCCode;
	private String stage1Status;
	private String stage2Status;
	private String stage3Status;
	private String perfStatus;	
	private int computerTest;	
	private Date stage1_testBeginTime;
	private Date stage2_testBeginTime;
	private Date stage3_testBeginTime;
	private Date perf_testBeginTime;	
	private Date stage1_testEndTime;
	private Date stage2_testEndTime;
	private Date stage3_testEndTime;
	private Date perf_testEndTime;	
	private int scholYear;	
		
	private List<KSDEStudentTestDTO> studentTestDetails = new ArrayList<KSDEStudentTestDTO>();

	/**
	 * @return the studentid
	 */
	public Long getStudentid() {
		return studentid;
	}

	/**
	 * @param studentid the studentid to set
	 */
	public void setStudentid(Long studentid) {
		this.studentid = studentid;
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
	 * @return the enrollmentId
	 */
	public Long getEnrollmentId() {
		return enrollmentId;
	}

	/**
	 * @param enrollmentId the enrollmentId to set
	 */
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	/**
	 * @return the aypSchoolIdentifier
	 */
	public String getAypSchoolIdentifier() {
		return aypSchoolIdentifier;
	}

	/**
	 * @param aypSchoolIdentifier the aypSchoolIdentifier to set
	 */
	public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
		this.aypSchoolIdentifier = aypSchoolIdentifier;
	}
	
	

	public String getTestGrade() {
		return testGrade;
	}

	public void setTestGrade(String testGrade) {
		this.testGrade = testGrade;
	}

	/**
	 * @return the abbrSubject
	 */
	public String getAbbrSubject() {
		return abbrSubject;
	}

	/**
	 * @param abbrSubject the abbrSubject to set
	 */
	public void setAbbrSubject(String abbrSubject) {
		this.abbrSubject = abbrSubject;
	}		
	
	/**
	 * @return the testTypeCode
	 */
	public String getTestTypeCode() {
		return testTypeCode;
	}

	/**
	 * @param testTypeCode the testTypeCode to set
	 */
	public void setTestTypeCode(String testTypeCode) {
		this.testTypeCode = testTypeCode;
	}

	/**
	 * @return the contentAreaId
	 */
	public Long getContentAreaId() {
		return contentAreaId;
	}

	/**
	 * @param contentAreaId the contentAreaId to set
	 */
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	/**
	 * @return the gradeId
	 */
	public Long getGradeId() {
		return gradeId;
	}

	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
		
	/**
	 * @return the attendanceSchoolId
	 */
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	/**
	 * @param attendanceSchoolId the attendanceSchoolId to set
	 */
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}

	/**
	 * @return the studentTestDetails
	 */
	public List<KSDEStudentTestDTO> getStudentTestDetails() {
		return studentTestDetails;
	}

	/**
	 * @param studentTestDetails the studentTestDetails to set
	 */
	public void setStudentTestDetails(List<KSDEStudentTestDTO> studentTestDetails) {
		this.studentTestDetails = studentTestDetails;
	}

	public String getStudentGrade() {
		return studentGrade;
	}

	public void setStudentGrade(String studentGrade) {
		this.studentGrade = studentGrade;
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
	 * @return the elaCombinedPrfrmLevel
	 */
	public BigDecimal getElaCombinedPrfrmLevel() {
		return elaCombinedPrfrmLevel;
	}

	/**
	 * @param elaCombinedPrfrmLevel the elaCombinedPrfrmLevel to set
	 */
	public void setElaCombinedPrfrmLevel(BigDecimal elaCombinedPrfrmLevel) {
		this.elaCombinedPrfrmLevel = elaCombinedPrfrmLevel;
	}

	/**
	 * @return the mdptScore
	 */
	public BigDecimal getMdptScore() {
		return mdptScore;
	}

	/**
	 * @param mdptScore the mdptScore to set
	 */
	public void setMdptScore(BigDecimal mdptScore) {
		this.mdptScore = mdptScore;
	}

	/**
	 * @return the mdptScorableFlag
	 */
	public Boolean getMdptScorableFlag() {
		return mdptScorableFlag;
	}

	/**
	 * @param mdptScorableFlag the mdptScorableFlag to set
	 */
	public void setMdptScorableFlag(Boolean mdptScorableFlag) {
		this.mdptScorableFlag = mdptScorableFlag;
	}

	/**
	 * @return the performanceLevel
	 */
	public Long getPerformanceLevel() {
		return performanceLevel;
	}

	/**
	 * @param performanceLevel the performanceLevel to set
	 */
	public void setPerformanceLevel(Long performanceLevel) {
		this.performanceLevel = performanceLevel;
	}

	/**
	 * @return the stage1_testId
	 */
	public Long getStage1_testId() {
		return stage1_testId;
	}

	/**
	 * @param stage1_testId the stage1_testId to set
	 */
	public void setStage1_testId(Long stage1_testId) {
		this.stage1_testId = stage1_testId;
	}

	/**
	 * @return the stage2_testId
	 */
	public Long getStage2_testId() {
		return stage2_testId;
	}

	/**
	 * @param stage2_testId the stage2_testId to set
	 */
	public void setStage2_testId(Long stage2_testId) {
		this.stage2_testId = stage2_testId;
	}

	/**
	 * @return the stage3_testId
	 */
	public Long getStage3_testId() {
		return stage3_testId;
	}

	/**
	 * @param stage3_testId the stage3_testId to set
	 */
	public void setStage3_testId(Long stage3_testId) {
		this.stage3_testId = stage3_testId;
	}

	/**
	 * @return the perf_testId
	 */
	public Long getPerf_testId() {
		return perf_testId;
	}

	/**
	 * @param perf_testId the perf_testId to set
	 */
	public void setPerf_testId(Long perf_testId) {
		this.perf_testId = perf_testId;
	}

	/**
	 * @return the ksdeFileCode
	 */
	public int getKsdeFileCode() {
		return ksdeFileCode;
	}

	/**
	 * @param ksdeFileCode the ksdeFileCode to set
	 */
	public void setKsdeFileCode(int ksdeFileCode) {
		this.ksdeFileCode = ksdeFileCode;
	}

	/**
	 * @return the stage1_questionCount
	 */
	public Long getStage1_questionCount() {
		return stage1_questionCount;
	}

	/**
	 * @param stage1_questionCount the stage1_questionCount to set
	 */
	public void setStage1_questionCount(Long stage1_questionCount) {
		this.stage1_questionCount = stage1_questionCount;
	}

	/**
	 * @return the stage2_questionCount
	 */
	public Long getStage2_questionCount() {
		return stage2_questionCount;
	}

	/**
	 * @param stage2_questionCount the stage2_questionCount to set
	 */
	public void setStage2_questionCount(Long stage2_questionCount) {
		this.stage2_questionCount = stage2_questionCount;
	}

	/**
	 * @return the stage3_questionCount
	 */
	public Long getStage3_questionCount() {
		return stage3_questionCount;
	}

	/**
	 * @param stage3_questionCount the stage3_questionCount to set
	 */
	public void setStage3_questionCount(Long stage3_questionCount) {
		this.stage3_questionCount = stage3_questionCount;
	}

	/**
	 * @return the performanceStageQuestionCount
	 */
	public Long getPerformanceStageQuestionCount() {
		return performanceStageQuestionCount;
	}

	/**
	 * @param performanceStageQuestionCount the performanceStageQuestionCount to set
	 */
	public void setPerformanceStageQuestionCount(Long performanceStageQuestionCount) {
		this.performanceStageQuestionCount = performanceStageQuestionCount;
	}

	/**
	 * @return the stage1_respondedCount
	 */
	public Long getStage1_respondedCount() {
		return stage1_respondedCount;
	}

	/**
	 * @param stage1_respondedCount the stage1_respondedCount to set
	 */
	public void setStage1_respondedCount(Long stage1_respondedCount) {
		this.stage1_respondedCount = stage1_respondedCount;
	}

	/**
	 * @return the stage2_respondedCount
	 */
	public Long getStage2_respondedCount() {
		return stage2_respondedCount;
	}

	/**
	 * @param stage2_respondedCount the stage2_respondedCount to set
	 */
	public void setStage2_respondedCount(Long stage2_respondedCount) {
		this.stage2_respondedCount = stage2_respondedCount;
	}

	/**
	 * @return the stage3_respondedCount
	 */
	public Long getStage3_respondedCount() {
		return stage3_respondedCount;
	}

	/**
	 * @param stage3_respondedCount the stage3_respondedCount to set
	 */
	public void setStage3_respondedCount(Long stage3_respondedCount) {
		this.stage3_respondedCount = stage3_respondedCount;
	}

	/**
	 * @return the perf_respondedCount
	 */
	public Long getPerf_respondedCount() {
		return perf_respondedCount;
	}

	/**
	 * @param perf_respondedCount the perf_respondedCount to set
	 */
	public void setPerf_respondedCount(Long perf_respondedCount) {
		this.perf_respondedCount = perf_respondedCount;
	}

	/**
	 * @return the elaMdptScorableFlag
	 */
	public String getElaMdptScorableFlag() {
		return elaMdptScorableFlag;
	}

	/**
	 * @param elaMdptScorableFlag the elaMdptScorableFlag to set
	 */
	public void setElaMdptScorableFlag(String elaMdptScorableFlag) {
		this.elaMdptScorableFlag = elaMdptScorableFlag;
	}

	/**
	 * @return the stage1_ksdeSCCode
	 */
	public String getStage1_ksdeSCCode() {
		return stage1_ksdeSCCode;
	}

	/**
	 * @param stage1_ksdeSCCode the stage1_ksdeSCCode to set
	 */
	public void setStage1_ksdeSCCode(String stage1_ksdeSCCode) {
		this.stage1_ksdeSCCode = stage1_ksdeSCCode;
	}

	/**
	 * @return the stage2_ksdeSCCode
	 */
	public String getStage2_ksdeSCCode() {
		return stage2_ksdeSCCode;
	}

	/**
	 * @param stage2_ksdeSCCode the stage2_ksdeSCCode to set
	 */
	public void setStage2_ksdeSCCode(String stage2_ksdeSCCode) {
		this.stage2_ksdeSCCode = stage2_ksdeSCCode;
	}

	/**
	 * @return the stage3_ksdeSCCode
	 */
	public String getStage3_ksdeSCCode() {
		return stage3_ksdeSCCode;
	}

	/**
	 * @param stage3_ksdeSCCode the stage3_ksdeSCCode to set
	 */
	public void setStage3_ksdeSCCode(String stage3_ksdeSCCode) {
		this.stage3_ksdeSCCode = stage3_ksdeSCCode;
	}

	/**
	 * @return the perf_ksdeSCCode
	 */
	public String getPerf_ksdeSCCode() {
		return perf_ksdeSCCode;
	}

	/**
	 * @param perf_ksdeSCCode the perf_ksdeSCCode to set
	 */
	public void setPerf_ksdeSCCode(String perf_ksdeSCCode) {
		this.perf_ksdeSCCode = perf_ksdeSCCode;
	}

	/**
	 * @return the stage1Status
	 */
	public String getStage1Status() {
		return stage1Status;
	}

	/**
	 * @param stage1Status the stage1Status to set
	 */
	public void setStage1Status(String stage1Status) {
		this.stage1Status = stage1Status;
	}

	/**
	 * @return the stage2Status
	 */
	public String getStage2Status() {
		return stage2Status;
	}

	/**
	 * @param stage2Status the stage2Status to set
	 */
	public void setStage2Status(String stage2Status) {
		this.stage2Status = stage2Status;
	}

	/**
	 * @return the stage3Status
	 */
	public String getStage3Status() {
		return stage3Status;
	}

	/**
	 * @param stage3Status the stage3Status to set
	 */
	public void setStage3Status(String stage3Status) {
		this.stage3Status = stage3Status;
	}

	/**
	 * @return the perfStatus
	 */
	public String getPerfStatus() {
		return perfStatus;
	}

	/**
	 * @param perfStatus the perfStatus to set
	 */
	public void setPerfStatus(String perfStatus) {
		this.perfStatus = perfStatus;
	}

	/**
	 * @return the computerTest
	 */
	public int getComputerTest() {
		return computerTest;
	}

	/**
	 * @param computerTest the computerTest to set
	 */
	public void setComputerTest(int computerTest) {
		this.computerTest = computerTest;
	}

	/**
	 * @return the stage1_testBeginTime
	 */
	public Date getStage1_testBeginTime() {
		return stage1_testBeginTime;
	}

	/**
	 * @param stage1_testBeginTime the stage1_testBeginTime to set
	 */
	public void setStage1_testBeginTime(Date stage1_testBeginTime) {
		this.stage1_testBeginTime = stage1_testBeginTime;
	}

	/**
	 * @return the stage2_testBeginTime
	 */
	public Date getStage2_testBeginTime() {
		return stage2_testBeginTime;
	}

	/**
	 * @param stage2_testBeginTime the stage2_testBeginTime to set
	 */
	public void setStage2_testBeginTime(Date stage2_testBeginTime) {
		this.stage2_testBeginTime = stage2_testBeginTime;
	}

	/**
	 * @return the stage3_testBeginTime
	 */
	public Date getStage3_testBeginTime() {
		return stage3_testBeginTime;
	}

	/**
	 * @param stage3_testBeginTime the stage3_testBeginTime to set
	 */
	public void setStage3_testBeginTime(Date stage3_testBeginTime) {
		this.stage3_testBeginTime = stage3_testBeginTime;
	}

	/**
	 * @return the perf_testBeginTime
	 */
	public Date getPerf_testBeginTime() {
		return perf_testBeginTime;
	}

	/**
	 * @param perf_testBeginTime the perf_testBeginTime to set
	 */
	public void setPerf_testBeginTime(Date perf_testBeginTime) {
		this.perf_testBeginTime = perf_testBeginTime;
	}

	/**
	 * @return the stage1_testEndTime
	 */
	public Date getStage1_testEndTime() {
		return stage1_testEndTime;
	}

	/**
	 * @param stage1_testEndTime the stage1_testEndTime to set
	 */
	public void setStage1_testEndTime(Date stage1_testEndTime) {
		this.stage1_testEndTime = stage1_testEndTime;
	}

	/**
	 * @return the stage2_testEndTime
	 */
	public Date getStage2_testEndTime() {
		return stage2_testEndTime;
	}

	/**
	 * @param stage2_testEndTime the stage2_testEndTime to set
	 */
	public void setStage2_testEndTime(Date stage2_testEndTime) {
		this.stage2_testEndTime = stage2_testEndTime;
	}

	/**
	 * @return the stage3_testEndTime
	 */
	public Date getStage3_testEndTime() {
		return stage3_testEndTime;
	}

	/**
	 * @param stage3_testEndTime the stage3_testEndTime to set
	 */
	public void setStage3_testEndTime(Date stage3_testEndTime) {
		this.stage3_testEndTime = stage3_testEndTime;
	}

	/**
	 * @return the perf_testEndTime
	 */
	public Date getPerf_testEndTime() {
		return perf_testEndTime;
	}

	/**
	 * @param perf_testEndTime the perf_testEndTime to set
	 */
	public void setPerf_testEndTime(Date perf_testEndTime) {
		this.perf_testEndTime = perf_testEndTime;
	}

	/**
	 * @return the scholYear
	 */
	public int getScholYear() {
		return scholYear;
	}

	/**
	 * @param scholYear the scholYear to set
	 */
	public void setScholYear(int scholYear) {
		this.scholYear = scholYear;
	}
}
