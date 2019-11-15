package edu.ku.cete.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.professionaldevelopment.DataDetailDto;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.StateSpecificFile;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.web.DLMBlueprintCoverageReportDTO;
import edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO;

@Service
public interface DataReportService {
	ModuleReport getModuleReportById(Long moduleReportId);
	
	public List<DataDetailDto> getReportsByTypeWithDataDictionaryDetail(User user, List<Short> typeIds, Long assessmentprogramid,Long organizationId);
	
	public List<ModuleReport> getReportsByType(User user, List<Short> typeIds);

	public List<ModuleReport> getReportsByTypeForExitOrSC(User user, List<Short> typeIds);
	
	public int countReportsByType(User user, List<Short> typeIds);
	
	public long generateNewExtract(User user, DataReportTypeEnum type, 
			Long moduleReportId, Long orgId, Long orgTypeId, Map<String, Object> params) throws JsonProcessingException ;
	
	public boolean startAccessibilityExtractGeneration(UserDetailImpl userDetail, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15234 : Data extract - Enrollment
	 * This method is responsible for getting th data and putting it into the csv file for enrollment extract.
	 * @param user
	 * @param moduleReportId
	 * @param orgId
	 * @param typeName 
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	public boolean startEnrollmentExtractGeneration(UserDetailImpl userDetail, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams,String typeName) throws IOException, ParseException;
	
	public boolean startUserDataExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean startRosterExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean startQuestarDataExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean startTECExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean startDLMTestStatusExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean startPNPSummaryExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean startKSDETestAndTascRecordsExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean validateKSDETestAndTascStudentStateID(UserDetailImpl userDetails, String studentStateID);
	
	public boolean checkKAPExtract(Long organizationId);
	
	public boolean startTestAdminExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId, Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public void updateModuleReportStatusToFailed(Long moduleReportId);

	public boolean startTestTicketsExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId, Map<String, Object> additionalParams, String typeName) throws IOException;

	public ModuleReport getMostRecentReportByTypeId(User user, short typeId);
	
	public ModuleReport getMostRecentGRFExtractReportByTypeId(User user, short typeId);
	
	public ModuleReport getMostRecentCompletedReportByTypeId(User user, short typeId);
	
	public boolean generateKSDEReturnFile(UserDetailImpl userDetails,
			Long moduleReportId, List<String> subjects, Map<String, Object> additionalParams) throws IOException;

	public void updateKSDEExtractStatusToComplete(Long moduleReportId);
	
	/*
     * Added during US16344 : for Extracting reports on TEst Form assign to TestCollection for quality check
     */
	public boolean startTestAssignmentsExtract(UserDetailImpl userDetails,Long moduleReportId, Long orgId,Map<String, Object> additionalParams, String typeName)throws IOException;
	/*
     * Added during US16343 : for Extracting reports on TEst Form assign to TestCollection for quality check
     */
	public boolean startFormMediaExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId, Map<String, Object> additionalParams, String typeName) throws IOException;
	
	/**
	 * US16739 : Kiran Reddy Taduru : 09/04/2015
	 * This method is being used in ModuleReportScheduler to delete inactive extracts from the server
	 */
	public void deleteModuleReports();
	
	public ModuleReport getQueuedReport(Long inqueueStatusId, Long inprogressStatusId);
	
	public int updateModuleReportStatus(ModuleReport moduleReport, Long statusId);
		
		
	public boolean startUserNamePasswordExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
		Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean generateAmpDataExtract(UserDetailImpl userDetails, Long moduleReportId, List<Long> operationalTestWindowIds) throws IOException;

	public void updateReportStatusToComplete(Long moduleReportId);
	
	public boolean startRestrictedSpecialCircumstanceCode(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws Exception ;

	public boolean startDLMPDStatus(UserDetailImpl userDetails, Long moduleReportId,Long orgId,
			Map<String, Object> additionalParams, String typeName)throws Exception;
	
	public boolean startDLMPDTrainingListExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws Exception;

	public boolean startSecurityAgreementExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;	
	public boolean startMonitorScoringExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,Map<String, Object> additionalParams, String typeName) throws IOException;
		

	HashMap<String, Object> getDlmTestAdminMonitoringSummary(
			String summaryLevel, Organization state, List<Long> districtIds, List<Long> schoolIds, User user);

	public boolean startKELPATestAdministrationMonitoringExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean startKAPStudentScoreCurrentExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean startITIBlueprintCoverageSummaryExtract(UserDetailImpl userDetails,Long moduleReportId, Long orgId, 
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public List<DLMBlueprintCoverageReportDTO> getBlueprintCoverageReport(Long schoolId, List<Long> teacherIds, List<Long> contentAreaIds,
			List<Long> gradeIds, Long schoolYear, Boolean groupByTeacher,Long testCycleID);
	
	List<ModuleReport> getQueuedReports(Long inQueueStatusId, Long inProgressStatusId, String organizationSchoolCode);

	int moveReportToInProgress(Long inQueueStatusId, Long inProgressStatusId, Long reportId);

	public boolean startDLMGeneralResearchFile(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

    public boolean startDLMSpecialCircumstanceExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean startDLMIncidentExtract(UserDetailImpl userDetails,
			Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	List<UploadGrfFile> getDistrictForGRF(Long stateId, int reportYear);

	List<UploadGrfFile> getSchoolsInDistrictForGRF(Long districtId,
			Long stateId);

	List<UploadGrfFile> getContentAreasforGRF(Long stateId, Long districtId, Long schoolId);

	List<UploadGrfFile> getGradeCourseByGRF(Long stateId,
			Long districtId, Long schoolId, Long contentAreaId);

	boolean startOrganizationSummaryExtract(UserDetailImpl userDetails,Long moduleReportId, Long orgId,Map<String, Object> additionalParams, String typeName)
			throws IOException;

	public boolean firstContactSurveyExtractGeneration(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean generateStudentCombinedReadinessExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean generateStudentExitExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public StateSpecificFile getStateSpecificFileById(Long stateSpecificFileId) ;
	
	public boolean startISmartTestAdministrationMonitoringExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean generatePNPAbridgedExtract(Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException, ScriptException;

	public boolean startKAPStudentScoreSpecifiedExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean startKAPStudentScoreTestedExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean startPLTWTestAdministrationDataExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId, 
			Map<String, Object> additionalParams, String typeName) throws IOException;
	
	public boolean generatePLTWStudentCombinedReadinessExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

	public boolean startKELPA2StudentScoreCurrentExtract(UserDetailImpl userDetails, Long moduleReportId, Long orgId,
			Map<String, Object> additionalParams, String typeName) throws IOException;

}