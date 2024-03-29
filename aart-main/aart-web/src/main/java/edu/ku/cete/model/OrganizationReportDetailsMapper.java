package edu.ku.cete.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.report.domain.OrganizationReportResults;
import edu.ku.cete.web.SchoolAndDistrictReportDTO;

public interface OrganizationReportDetailsMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table organizationreportdetails
	 * @mbggenerated  Tue Jun 06 15:34:39 CDT 2017
	 */
	int insert(OrganizationReportDetails record);


	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table organizationreportdetails
	 * @mbggenerated  Tue Jun 06 15:34:39 CDT 2017
	 */
	int insertSelective(OrganizationReportDetails record);


	int updateOrganizationReportDetails(@Param("assessmentProgramId") Long assessmentProgramId, @Param("contentAreaId") Long contentAreaId, 
			@Param("gradeId") Long gradeId, @Param("schoolYear") Long schoolYear, @Param("detailedReportPath") String detailedReportPath,
			@Param("schoolReportPdfPath") String schoolReportPdfPath, @Param("schoolreportzipsize") Long schoolreportzipsize, 
			@Param("schoolreportpdfsize") Long schoolreportpdfsize,	@Param("batchreportprocessid") Long batchreportprocessid,
			@Param("organizationId") Long organizationId);
	
	
	int deleteOrganizationReportDetails(OrganizationReportDetails record);
	
	Long countByCriteria(Map<String, Object> parameters);
	
	List<SchoolAndDistrictReportDTO> selectByCriteria(Map<String, Object> criteria);
	
	int deleteAllOrgsInOrganizationReportDetails(@Param("assessmentProgramId")Long assessmentProgramId, @Param("contentAreaId")Long contentAreaId, 
			@Param("gradeId")Long gradeId, @Param("schoolYear")Long schoolYear, @Param("reportTypeSummary")List<String> reportTypeSummary);
	
	SchoolAndDistrictReportDTO getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(@Param("id") Long id, @Param("userOrgId") Long userOrgId);

	List<OrganizationReportDetails> getAllStudentsReports(Map<String, Object> criteria);
	
	OrganizationReportDetails getSchoolAndDistrictReport(@Param("id") Long id);
	
	List<Long> getSchoolIdsForDistrict(@Param("districtId") Long districtId, Long reportYear);	
	
	List<Long> getSchoolIdsForDistrictDLMandCpass(@Param("districtId") Long districtId, @Param("reportTypeCode") String reportTypeCode,@Param("reportYear") Long reportYear);

	List<Long> getSchoolIdsFromOrgDetailReportByStateIdAssmntProgIdAndSchoolYear(@Param("contractingOrganizationId") Long contractingOrganizationId, @Param("assessmentProgramId")Long assessmentProgramId,
			@Param("schoolYear") Long schoolYear, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize, @Param("reportType") String reportType);


	List<OrganizationReportDetails> getOrganizationReportDetailsForZip(@Param("assessmentProgramId") Long assessmentProgramId, @Param("organizationId") Long organizationId, @Param("schoolYear") Long schoolYear, @Param("reportType") String reportType);

	SchoolAndDistrictReportDTO getSchoolSummaryReportFile(@Param("id") Long id, @Param("userOrgId") Long userOrgId);
	List<SchoolAndDistrictReportDTO> getSchoolSummaryReports(Map<String, Object> criteria);
	
	Long districtSummaryReportCount(Map<String, Object> parameters);
	List<SchoolAndDistrictReportDTO> getDistrictSummaryReport(Map<String, Object> parameters);
	
	SchoolAndDistrictReportDTO getDlmDistrictSummaryReport(Map<String, Object> parameters);
	
	SchoolAndDistrictReportDTO getDlmStateSummaryReport(Map<String, Object> parameters);
	
	SchoolAndDistrictReportDTO getDistrictSummaryReportByPrimaryKeyAndUserOrgId(@Param("id") Long id, @Param("userOrgId") Long userOrgId);
	
	SchoolAndDistrictReportDTO getDlmSummaryReportByPrimaryKeyAndUserOrgId(@Param("id") Long id, @Param("userOrgId") Long userOrgId, @Param("reportType") String reportType);
	
	List<OrganizationReportDetails> selectAllOrgReportDetailsByAssessmentProgramIdContentAreaIdGradeId(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("gradeId") Long gradeId, @Param("contentAreaId") Long contentAreaId, @Param("schoolYear") Long schoolYear, @Param("reportTypeSummary")List<String> reportTypeSummary);


	void deleteAllSchoolBundleReports(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("reportType") String reportType);
    
	void deleteAllSchoolBundleReportsForUserState(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("organizationId") Long organizationId, @Param("reportType") String reportType);
	
	void deleteAllDistrictBundleReportsForUserState(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("organizationId") Long organizationId, @Param("reportType") String reportType);
	

	List<OrganizationReportDetails> getAllSchoolReportBundleReportsByAssessmentProgramId(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("reportType") String reportType);	
    
	
	List<OrganizationReportDetails> getAllSchoolReportBundleReportsByAssessmentProgramIdForUserState(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("organizationId") Long organizationId, @Param("reportType") String reportType);
	
	List<OrganizationReportDetails> getAllDistrictReportBundleReportsByAssessmentProgramIdForUserState(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("organizationId") Long organizationId, @Param("reportType") String reportType);
	
	List<SchoolAndDistrictReportDTO> getELPAStudentsScoreFileReport(Map<String, Object> params);


	void deleteOrganizationBundleReportsByOrganization(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("organizationId") Long organizationId, @Param("reportType") String reportType);


	List<OrganizationReportDetails> OrganizationBundleReportFilesByOrganization(@Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") Long schoolYear, @Param("organizationId") Long organizationId, @Param("reportType") String reportType);
	
	List<OrganizationReportDetails> getAllStudentSummaryBundledReportsByAssessmentProgramIdForUserState(@Param("assessmentProgramId") Long assessmentProgramId, 
									@Param("schoolYear") Long schoolYear, 
									@Param("organizationId") Long organizationId,
									@Param("reportType") String reportType);
	
	void deleteAllStudentSummaryBundleReportsForUserState(@Param("assessmentProgramId") Long assessmentProgramId, 
									@Param("schoolYear") Long schoolYear, 
									@Param("organizationId") Long organizationId,
									@Param("reportType") String reportType);
	
	List<OrganizationReportDetails> getAllDistrictLevelStudentSummaryBundleReports(@Param("assessmentProgramId") Long assessmentProgramId, 
									@Param("schoolYear") Long schoolYear, 
									@Param("organizationId") Long organizationId, 
									@Param("reportType") String reportType);
	
	void deleteAllDistrictBundleStudentSummaryReports(@Param("assessmentProgramId") Long assessmentProgramId, 
									@Param("schoolYear") Long schoolYear, 
									@Param("organizationId") Long organizationId,
									@Param("reportType") String reportType);

	List<OrganizationReportDetails> getAllDistrictLevelSchoolSummaryBundleReports(@Param("assessmentProgramId") Long assessmentProgramId, 
			@Param("schoolYear") Long schoolYear, 
			@Param("organizationId") Long organizationId, 
			@Param("reportType") String reportType);
	
	void deleteAllDistrictBundleSchoolSummaryReports(@Param("assessmentProgramId") Long assessmentProgramId, 
			@Param("schoolYear") Long schoolYear, 
			@Param("organizationId") Long organizationId,
			@Param("reportType") String reportType);
	
	List<Long> getDistrictIdsForSchoolSummaryBundledReports(@Param("stateId") Long stateId, @Param("assessmentProgramId") Long assessmentProgramId, 
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("reportType") String reportType, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
	
	List<OrganizationReportDetails> getSchoolReportsForSchoolSummaryDistrictBundledReport(
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("schoolYear") Long schoolYear,
			@Param("assessmentProgramCode") String assessmentProgramCode,
			@Param("districtId") Long districtId,
			@Param("reportType") String reportType);
	


	void deleteOrganizationReportsByOrganizationIdReportType(@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear, @Param("stateId") Long stateId,@Param("reportTypes") List<String> reportTypes);
	
	List<OrganizationReportDetails> getAllDistrictReportsPdfFileByStateId(@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear, @Param("stateId") Long stateId,@Param("reportTypes") List<String> reportTypes);
	
	List<OrganizationReportDetails> getAllStudentSummaryBundledReports(Map<String, Object> criteria);
	
	List<Long> getSchoolIdsInDistrictOfSummaryBundledReports(Map<String, Object> criteria);
	
	List<OrganizationReportDetails> getAllSchoolSummaryBundledReportByDistrictId(Map<String, Object> criteria);
	
	
	int  updateForCsv(@Param("csvPath") String csvPath, @Param("batchProcessId") Long batchProcessId, @Param("modifiedUser") Long modifiedUser, @Param("modifiedDate") Date modifiedDate, @Param("id") Long id);
	
	List<OrganizationReportDetails> selectClassroomReport(@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear, @Param("stateId") Long stateId, @Param("teacherId") Long teacherId, @Param("schoolId")Long schoolId,@Param("contentAreaId")Long contentAreaId, @Param("gradeId") Long gradeId);
	
	List<OrganizationReportDetails> selectSchoolReport(@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear, @Param("stateId") Long stateId, @Param("schoolId")Long schoolId);
	
	int deleteByStateSchoolYearAssessmentProgramReportType(@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear, @Param("stateId") Long stateId,@Param("reportType") String reportType);
	
	List<OrganizationReportDetails> getAllSchoolSummaryReports(Map<String, Object> criteria);
	
	List<OrganizationReportDetails> getAllTeacherNamesForClassroomReports(Map<String, Object> criteria);


	int inserResults(OrganizationReportResults organizationReportDetail);


	int deleteOrganizationReportDetailForCpassReport(@Param("stateId") Long stateId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId, @Param("schoolYear") Long schoolYear,@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle, @Param("reportType") String reportType);


	Integer updateResults(OrganizationReportResults organizationReportDetail);
	
	List<OrganizationReportDetails> getOrganizationReportDetailByReportCycle(@Param("stateId") Long stateId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("subjectId") Long subjectId,@Param("gradeId") Long gradeId, @Param("schoolYear") Long schoolYear,@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle, @Param("reportType") String reportType);
	

	void clearOrganizationReportDetail(@Param("stateId") Long stateId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("subjectId") Long subjectId,@Param("schoolYear") Long schoolYear,@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle, @Param("reportType") String reportType, @Param("uploadedUser") Long uploadedUser);


	List<OrganizationReportDetails> getOrganizationReportDetails(
			@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear,@Param("stateId") Long stateId);


	void deleteAllOrganizationReportDetails(@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear,@Param("stateId") Long stateId);


	//test
	Long getStudentsCountOfDistrictlLevel(@Param("stateId")Long stateId, 
			@Param("assessmentProgramId")Long assessmentProgramId, 
			@Param("reportYear")Long reportYear, @Param("reportType")String reportType);
	
	Long getexternelStudentsCountOfDistrictlLevel(@Param("stateId")Long stateId, 
			@Param("assessmentProgramId")Long assessmentProgramId, 
			@Param("reportYear")Long reportYear, @Param("reportType")String reportType);
	
	String getStateName(@Param("stateId")Long stateId);
	
	Long getStudentsCountOfSchoolLevel(@Param("stateId")Long stateId, 
			@Param("assessmentProgramId")Long assessmentProgramId, 
			@Param("reportYear")Long reportYear, @Param("reportType")String reportType);


	Long getexternelStudentsCountOfSchoolLevel(@Param("stateId")Long stateId, 
			@Param("assessmentProgramId")Long assessmentProgramId, 
			@Param("reportYear")Long reportYear, @Param("reportType")String reportType);
	
	Long getSchoolSummaryBundleCountOfDistrictlLevel(@Param("stateId")Long stateId, 
			@Param("assessmentProgramId")Long assessmentProgramId, 
			@Param("reportYear")Long reportYear, @Param("reportType")String reportType);
	
	Long getSchoolSummaryCountOfDistrictlLevel(@Param("stateId")Long stateId, 
			@Param("assessmentProgramId")Long assessmentProgramId, 
			@Param("reportYear")Long reportYear, @Param("reportType")String reportType);
}