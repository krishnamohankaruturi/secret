package edu.ku.cete.service.report;

import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.ReportProcess;
import edu.ku.cete.domain.ReportTestLevelSubscores;
import edu.ku.cete.domain.StudentReportTestScores;
import edu.ku.cete.domain.SuppressedLevel;

import edu.ku.cete.domain.report.ActScoringDescription;
import edu.ku.cete.domain.report.ActScoringLevel;

import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.PredictiveReportCreditPercent;
import edu.ku.cete.domain.report.PredictiveReportOrganization;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.ReportProcessRecordCounts;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.report.SubScoreMedianScore;
import edu.ku.cete.web.ExternalStudentReportDTO;

public interface BatchReportProcessService {

	public int selectDuplicateCountBatchReportProcess(Long assessmentProgramId, Long subjectId, Long gradeId, String process);
	
	public int updatePreviousToInactiveBatchReport(Long assessmentProgramId, Long subjectId, Long gradeId, String process);
	
	public int insertBatchReport(ReportProcess record);

	List<StudentReport> getStudentsForReportProcess(Long studentId, Long contractOrgId, Long assessmentId, Long assessmentProgramId, String assessmentProgramCode, Long gradeId, Long contentAreaId, Long currentSchoolYear, List<Long> rawScaleExternalTestIds,List<Long> testsStatusIds, List<Long> studentIdList, Integer pageSize, Integer offset);


	int updateByPrimaryKeySelectiveBatchReportProcess(ReportProcess record);

	void insertSelectiveReportProcessReasons(List<ReportProcessReason> reportProcessReasonList);

	
	public List<ReportProcess> getBatchReportingHistory(Date fromDate, Date toDate, String orderByColumn, String order, Integer limit, Integer offset,  List<Long> apIds);
	public int countBatchReportingHistory(Date fromDate, Date toDate, List<Long> apIds);

	int insertSelectiveStudentReport(StudentReport studentReport);
	
	int insertSelectiveReportSubscores(ReportSubscores reportSubscores);
	
	void insertReportSubscoresList(List<ReportSubscores> subscores);

	int deleteSpecificStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long studentId, Long schoolYear);
	
	int deleteReportSubscores(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);
	
	int deleteReportSubscoresByStudentId(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long studentId, Long schoolYear);
	
	int deleteAllOrgsInOrganizationReportDetails(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, List<String> reportTypeSummary);

	public List<ReportProcessReason> findReportProcessReasonsForId(Long reportProcessId);

	List<ReportsMedianScore> getDistinctSchoolIdsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
												Integer offset, Integer pageSize);
	
	List<ReportsMedianScore> getDistinctStateIdsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Integer offset, Integer pageSize);
	
	List<ReportsMedianScore> getDistinctDistrictIdsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Integer offset, Integer pageSize);
	
	List<StudentReport> getStudentsBySchoolAssessmentGradeSubject(Long schoolId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);
	
	List<StudentReport> getStudentsByDistrictAssessmentGradeSubject(Long districtId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);

	List<StudentReport> getStudentsByStateAssessmentGradeSubject(Long stateId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);


	int insertReportMedianScore(ReportsMedianScore record);

	void insertReportsPercentByLevel(List<ReportsPercentByLevel> records);

	int updateByPrimaryKeySelectiveStudentReport(StudentReport studentReport);

	int deleteReportsPercentByLevel(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear);

	int deleteReportMedianScore(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear);


	List<StudentReport> getStudentsForReportGeneration(Long studentId, Long contractOrgId,
			Long assessmentProgramId, Long gradeId, Long contentAreaId, Long schoolYear,
			Integer pageSize, Integer offset,String processByStudentId,String reprocessEntireDistrict);
	
	List<ReportsMedianScore> getMedainScoreByOrgIdsAssessmentContentGradeSchoolYear(List<Long> organizationIds,
			Long assessmentProgramId, Long contentAreaId, List<Long> gradeId, Long schoolYear);
	
	List<ActScoringDescription> getActScoreByOrgIdsAssessmentContentGradeSchoolYear(Long assessmentProgramId,  Long gradeId, Long contentAreaId, Long currentSchoolYear, Long levelId);
	
	List<ActScoringDescription> getActScoreDescriptionByOrgIdsAssessmentContentGradeSchoolYear(Long assessmentProgramId,  Long gradeId, Long contentAreaId, Long currentSchoolYear);

	int  updateStudentReportFilePath(String filePath, Boolean generated, Long id, Long batchReportProcessId, String attendanceSchoolName, String districtName, String progressionText);

	SubScoreMedianScore getStudentsBySchoolAssmntGradeSubjectSubScoreDef(
			Long schoolId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear);

	SubScoreMedianScore getStudentsByDistrictAssmntGradeSubjectSubScoreDef(
			Long districtId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear);

	SubScoreMedianScore getStudentsByStateAssmntGradeSubjectSubScoreDef(
			Long stateId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear);
	
	Long calcMedianScore(List<Long> studentScaleScores, int studentCount);
	
	double calcStandardDeviation(List<Long> studentScaleScores);

	List<Long> getDistinctOrganizationIdFromMedianScore(Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Long schoolYear, Long contractOrgId, Integer pageSize, Integer offset);
	
	List<ReportSubscores> selectSubscoresDetailsByStudentReportIdAndReportType(Long studentId, String reportType, Long contentareaId,Long attendanceSchoolid);
	
	List<ReportSubscores> selectSubscoresMediansByOrganizationIdAndReportType(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, Long orgId, String reportType);
	
	int insertOrganizationReportDetails(OrganizationReportDetails record);
	
	List<ReportsPercentByLevel> selectReportPercentByOrganization(Long assessmentProgramId, Long contentAreaId,
			List<Long> gradeIds, Long schoolYear, List<Long> organizationIds);

	public List<StudentReport> getStudentReportsForSchoolReportPdf(Long assessmentProgramId, String gradeCourseAbbrName, Long schoolYear, Long attendanceSchoolId);	

	public List<Long> getSchoolIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(Long stateId, Long assessmentProgramId, Long currentSchoolYear, String gradeCourseAbbrName, Integer pageSize, Integer offset);
	
	public List<Long> getDistrictIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(Long stateId, Long assessmentProgramId, Long currentSchoolYear, Integer pageSize, Integer offset);

	public List<String> geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYear(Long stateId, Long assessmentProgramId, int currentSchoolYear);

	public int insertSchoolReportOfStudentFilesPdf(OrganizationReportDetails schoolReportPdfOfStudentReoports);

	public List<Long> getSchoolIdsFromOrgDetailReportByStateIdAssmntProgIdAndSchoolYear(Long contractingOrganizationId, Long assessmentProgramId,Long schoolYear, Integer pageSize, Integer offset, String reportType);

	public List<OrganizationReportDetails> getOrganizationReportDetailsForZip(Long assessmentProgramId, Long organizationId, Long schoolYear, String reportType);

	public int insertSchoolReportZip(OrganizationReportDetails schoolZipReportPeoports);	
	
	public List<ReportProcessRecordCounts>  getRecordCountForBatchReportScoreCalcJob(Long batchReportProcessId);
	
	public List<ReportProcessRecordCounts>  getRecordCountForBatchStudentReportJob(Long batchReportProcessId);
	
	public List<ReportProcessRecordCounts>  getRecordCountForOrganizationReportJob(Long batchReportProcessId, String organizationTypeCode);

	public void insertBatchReportCounts(List<ReportProcessRecordCounts> reportProcessRecordCounts, String process, Long batchReportProcessId);
	
	public void deleteAllStudentReportFiles(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long studentId, Long schoolYear);

	void deleteAllOrganizationReportFiles(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long schoolYear, List<String> reportTypeSummary);

	List<StudentReport> getAllStudentReportByAssessmentProgramIdContentAreaIdGradeIdStudentId(Long assessmentProgramId,Long gradeId, Long contentAreaId, Long studentId, Long schoolYear);

	List<OrganizationReportDetails> getAllOrgReportDetailsByAssessmentProgramIdContentAreaIdGradeId(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long schoolYear, List<String> reportTypeSummary);


	public void deleteAllSchoolBundleReports(Long assessmentProgramId, Long schoolYear, String reportType);

	public void deleteAllSchoolBundleReportFiles(Long assessmentProgramId, Long schoolYear, String reportType);

	
	public void deleteAllSchoolBundleReportsForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId , String reportType);
	
	public void deleteAllSchoolBundleReportFilesForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId , String reportType);
	
	public void deleteAllDistrictBundleReportFilesForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType);
	
	public void deleteAllDistrictBundleReportsForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId ,String reportType);


	List<ReportsMedianScore> getAllOrgsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Integer offset, Integer pageSize);

	List<Long> getDistinctGradeIdsInOrgFromMedains(Long assessmentProgramId, Long contentAreaId,
			Long schoolYear, Long organizationId);		
	
	SuppressedLevel getSuppressedLevel(Long contentAreaId, Long gradeCourseId);

	public StudentReport getPreviousYearReport(StudentReport studentReport);
	
	int insertSelectiveStudentReportTestScores(StudentReportTestScores studentReportTestScores);
	
	int deleteSpecificStudentReportTestScores(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long studentId, Long schoolYear);
	
	List<ReportSubscores> getItemCountBySubscoreDefinitionNameByTestId(Long testId, Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeId);
	
	int insertSelectiveTestLevelSubscore(ReportTestLevelSubscores record);
	
	int deleteTestLevelStudentSubscores(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long studentId, Long schoolYear);

    List<Long> getSchoolIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(
			Long stateId, Long assessmentProgramId, Long currentSchoolYear,
			String gradeCourseAbbrName, Integer offset, Integer pageSize, String  reportType);
    
    List<Long> getDistrictIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(
			Long stateId, Long assessmentProgramId, Long currentSchoolYear,
			Integer offset, Integer pageSize, String reportType);

	List<StudentReport> getExternalStudentReportsForSchoolReportPdf(
			Long assessmentProgramId, String gradeCourseAbbrName,
			Long schoolYear, Long attendanceSchoolId, String reportType);

    List<String> geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYearForExternalReport(
			Long id, Long assessmentProgramId, int reportYear, String reportType);
    
    List<StudentReport> getStudentsBySchoolAssessmentGradeSubjectForMdptLevel(Long schoolId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);
	List<StudentReport> getStudentsByDistrictAssessmentGradeSubjectForMdptLevel(Long districtId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);
	List<StudentReport> getStudentsByStateAssessmentGradeSubjectForMdptLevel(Long stateId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);

	List<StudentReport> getStudentsBySchoolAssessmentGradeSubjectForCombinedLevel(Long schoolId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);
	List<StudentReport> getStudentsByDistrictAssessmentGradeSubjectForCombinedLevel(Long districtId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);
	List<StudentReport> getStudentsByStateAssessmentGradeSubjectForCombinedLevel(Long stateId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear);

	public List<ReportSubscores> selectSubscoresDetailsForOrganization(
			Long orgId, Long assessmentProgramId, Long id,
			List<Long> orgGradeIds, Long schoolYear,String reportType);

	public Boolean checkNonScorableMDPT(Long studentId,
			Long studentPerformanceTestId);

	public List<StudentReport> getExternalStudentReportsForDistrictReportPdf(
			Long assessmentProgramId,
			Long schoolYear, String assessmentProgramCode, Long districtId, String reportType);

	public List<StudentReport> getStudentReportsForDistrictReportPdf(
			Long assessmentProgramId, Long schoolYear, Long districtId);


	public List<StudentReport> getStudentReportsForDynamicBundleReport(
			List<Long> subjectIds, List<Long> schoolIds, List<Long> gradeIds,Long assessmentProgramId,
			String assessmentProgramCode, Long schoolYear, String sort1,
			String sort2, String sort3);

	List<Long> getStudentsForReportProcessByStudentId(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, Long testingProgramId);
	
	int updateStudentReportReprocessByStudentId(StudentReport record);

	public void deleteOrganizationBundleReportsByOrganization(
			Long attendanceSchoolId, Long assessmentProgramId, Long schoolYear, String reportType);

	int updateStudentReportReprocessByStudentIdByIsrOption(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, String isrByStudentIdOption, Long testingProgramId);
	int UpdateStudentReportReprocessStatusById(Long studentReportReprocessId,Boolean studentReportReprocessStatus);


	public void deleteOrganizationBundleReportFilesByOrganization(
			Long attendanceSchoolId, Long assessmentProgramId, Long schoolYear, String reportType);

	public Integer getCountOfStudentReports(List<Long> subjectIds, List<Long> schoolIds, List<Long> gradeIds,Long assessmentProgramId,
			String assessmentProgramCode, Long schoolYear, String sort1,
			String sort2, String sort3);
	
	public void deleteAllStudentSummaryBundleReportFiles(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType);
	public void deleteAllStudentSummaryBundleReports(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType);

	List<Long> getSchoolIdsForStudentSummaryBundledReports(
				Long stateId, Long assessmentProgramId, Long currentSchoolYear,
				String gradeCourseAbbrName, String reportType, Integer offset, Integer pageSize);
	 
	List<String> geGradeCoursesAbbrNamesForStudentSummaryBundledReports(
				Long stateId, Long assessmentProgramId, int reportYear, String reportType);
	 
	List<Long> getDistrictIdsForStudentSummaryBundledReports(Long stateId, Long assessmentProgramId, Long currentSchoolYear, String reportType, Integer offset, Integer pageSize);
	
	List<StudentReport> getExternalStudentReportsForStudentSummaryBundledReport(Long assessmentProgramId, String gradeCourseAbbrName, Long schoolYear, 
			Long attendanceSchoolId, String reportType);

	List<StudentReport> getExternalStudentReportsForStudentSummaryDistrictBundledReport(Long assessmentProgramId, Long schoolYear, String assessmentProgramCode, Long districtId, String reportType);
	
	public void deleteAllDistrictBundleReportFilesForStudentSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType);
	
	public void deleteAllDistrictBundleReportsForStudentSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType);
	
	public void deleteAllDistrictBundleReportFilesForSchoolSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType);
	
	public void deleteAllDistrictBundleReportsForSchoolSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType);
	
	List<Long> getDistrictIdsForSchoolSummaryBundledReports(Long stateId, Long assessmentProgramId, Long currentSchoolYear, String reportType, Integer offset, Integer pageSize);
	
	List<OrganizationReportDetails> getExternalSchoolReportsForDistrictBundledReport(Long assessmentProgramId, Long schoolYear, String assessmentProgramCode, Long districtId, String reportType);
	
	void deleteOrganizationReportsByOrganizationIdReportType(Long assessmentProgramId,Long schoolYear, Long stateId,List<String> reportTypes);

	public int insertOrganizationSummaryReportFiles(OrganizationReportDetails orgReportDetails);
	
	public List<OrganizationReportDetails> getAllDistrictReportsPdfFileByStateId(Long assessmentProgramId,Long schoolYear,Long stateId,List<String> reportTypes);	
	
	public List<StudentReport> getStudentSummaryReportsForDynamicBundleReport(List<Long> schoolIds, List<Long> gradeIds,Long assessmentProgramId,
			String assessmentProgramCode, Long schoolYear, String sort1,
			String sort2, String sort3, String reportType);
	
	public Integer getCountOfStudentSummaryReports(List<Long> schoolIds, List<Long> gradeIds,Long assessmentProgramId,
			String assessmentProgramCode, Long schoolYear, String sort1,
			String sort2, String sort3, String reportType);
	
	public void deleteAllPredictiveReportFiles(Long assessmentProgramId, String reportCycle, Long gradeId, Long contentAreaId, Long studentId, Long schoolYear);
	
	int deleteInterimStudentReports(Long assessmentProgramId, String reportCycle, Long contentAreaId, Long gradeId, Long schoolYear, Long studentId);	
	
	int deleteStudentReportQuestionInfo(Long assessmentProgramId, String reportCycle, Long contentAreaId, Long gradeId, Long schoolYear, Long studentId);
	
	List<InterimStudentReport> getAllInterimStudentReports(Long assessmentProgramId,Long gradeId, Long contentAreaId, Long schoolYear, String reportCycle, Long studentId);
	
	List<TestingCycle> getCurrentTestCycleDetails(Long assessmentProgramId, Long schoolYear, Long testingProgramId, String testingCycle);

	public List<Long> getContentAreaIdsFromInterimStudentReport(
			Long assessmentProgramId, Long schoolYear, String reportCycle,
			Long testingProgramId);
	
	public List<Long> getGradeIdsFromInterimStudentReport(Long assessmentProgramId,
			Long schoolYear, Long contentAreaId, String reportCycle, Long testingProgramId);
	
	public List<Long> getInterimStudentIdsForReportGeneration(Long gradeId,
			Long contentAreaId, Long assessmentProgramId, Long schoolYear, String reportCycle, Long testingProgramId, String processByStudentId, String reprocessEntireDistrict, int offset,
			int pageSize);

	public List<InterimStudentReport> getInterimStudentsForReportGeneration(
			Long assessmentProgramId, Long gradeId, Long contentAreaId,
			Long studentId, Long schoolYear, Long testingProgramId, String processByStudentId);

	public List<TestingCycle> getTestingCyclesBySchoolYear(
			Long assessmentProgramId, Long schoolYear, Long testingProgramId);

	public void updateInterimStudentReportFilePath(
			InterimStudentReport interimStudentReport);
	
	List<InterimStudentReport> getStudentsForPredictiveReportProcess(Long studentId, Long contractOrgId, Long assessmentId, Long assessmentProgramId, String assessmentProgramCode, 
			Long gradeId, Long contentAreaId, Long currentSchoolYear, List<Long> rawScaleExternalTestIds,List<Long> testsStatusIds, List<Long> studentIdList,
			Long testingProgramId,
			String reportCycle,
			Integer pageSize, Integer offset, Date jobStartTime);
	
	int insertInterimPreditiveStudentReport(InterimStudentReport record);
	
	int insertPredictiveStudentReportQuestionInfo(StudentReportQuestionInfo record);
	
	int updatePreditviceStudentReportReprocessByStudentId(InterimStudentReport record);
	
	List<TestingCycle> getTestingCyclesByProgramIdSchoolYear(Long assessmentProgramId, Long schoolYear, Long testingProgramId);
	
	TestingCycle getTestingCycleByTestingProgramId(Long testingProgramId);
	
	public void updateStudentReportReprocessByStudentIdByIsrOption(Long studentId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, String generateSpecificISROption, Long testingProgramId);
	
	List<PredictiveReportOrganization> getAllOrgsFromInterimStudentReport(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long schoolYear, Long contentAreaId, Long gradeId, Integer offset, Integer pageSize);
	
	List<PredictiveReportCreditPercent> getQuestionCreditPercentCountByOrganizatonId(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long schoolYear, Long contentAreaId, Long gradeId, Long testId, Long organizationId, String orgTypeCode, Long creditTypeId, List<Long> testsStatusIds);
	
	Integer getTestAttemptedStudentCount(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long schoolYear, Long contentAreaId, Long gradeId, Long organizationId, String orgTypeCode, Long externalTestId, List<Long> testsStatusIds);
	
	Integer getUnAnsweredStudentCount(Long assessmentProgramId,	Long testingProgramId, String reportCycle, Long schoolYear,	Long contentAreaId, Long gradeId, Long organizationId, String orgTypeCode, Long externalTestId);
	
	int insertPredictiveReportCreditPercent(PredictiveReportCreditPercent predictiveReportCreditPercent);
	
	int deletePredictiveSchoolDistrictSummaryCalculations(Long assessmentProgramId, Long testingProgramId, String reportCycle,	Long schoolYear, Long contentAreaId, Long gradeId);

	PredictiveReportCreditPercent selectByTestIdandOrganizationId(String orgTypeCode,Long currentSchoolYear,Long assessmentProgramId,Long testSessionId);

	void updateAllExternalStudentReportFilePath(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, String reportCycle, Long testingProgramId, Long organizationId, String reportType, Long userId);

	void deleteAllExternalStudentReportFiles(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long organizationId, Long schoolYear, String reportCycle,Long testingProgramId,  String reportType);

	List<ExternalStudentReportDTO> getAllExternalStudentReports(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long organizationId, Long schoolYear, String reportCycle, Long testingProgramId, String reportType);

	public void deleteExternalSchoolDetailReports(Long assessmentProgramId,
			Long stateId, Long subjectId, Long gradeId, Long schoolYear,
			String reportCycle, Long testingProgramId,
			String cpassSchoolDetailsReportType, Long userId);

	public void deleteAllOrganizationReportsOnGRFUpload(Long stateId, Long reportYear,
			Long assessmentProgramId);

	public void deleteAllStudentReportsOnGRFUpload(Long stateId,
			Long reportYear, Long assessmentProgramId);

	public GrfStateApproveAudit getGRFRecentStatus(Long stateId,
			Long assessmentProgramId, Long reportYear);
 public Long getStudentsCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType);
	
	public Long getexternelStudentsCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType);
	
	public String getStateName(Long stateId);
	
	public Long getStudentsCountOfSchoolLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType);
	
	public Long getexternelStudentsCountOfSchoolLevel(Long stateId, Long assessmentProgramId, Long reportYear,
			String reportType);

	public List<Long> getActLevelsForActScoring();

	public Long getSchoolSummaryBundleCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType);
	
	public Long getSchoolSummaryCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType);

}
