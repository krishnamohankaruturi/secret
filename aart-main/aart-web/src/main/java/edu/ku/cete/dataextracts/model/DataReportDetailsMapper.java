package edu.ku.cete.dataextracts.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.SchoolAndDistrictReportDTO;
import edu.ku.cete.web.StudentReportDTO;

public interface DataReportDetailsMapper {

	List<Long> getCurrentReportYear(@Param("organizationId") Long organizationId,@Param("assessmentProgId") Long assessmentProgId, @Param("reportType") String reportType, @Param("reportCode") String reportCode,@Param("currentYear") int currentYear);

	
	List<ContentArea> getContentAreasWhereReportsHaveProcessed(
			Map<String, Object> params);

	List<GradeCourse> getGradesWhereReportsHaveProcessed(
			Map<String, Object> params);

	List<OrganizationReportDetails> getAllTeacherNamesForClassroomReports(
			Map<String, Object> params);

	Long countStudentReports(Map<String, Object> params);

	List<StudentReportDTO> getStudentReports(Map<String, Object> params);

	List<Long> getSchoolIdsForDistrictDLMandCpass(@Param("districtId") Long districtId,
			@Param("individualReportType") String individualReportType,@Param("reportYear") Long reportYear);

	List<Long> getSchoolIdsForDistrict(@Param("districtId") Long districtId,@Param("reportYear") Long reportYear);

	List<OrganizationReportDetails> getAllStudentsReports(
			Map<String, Object> params);

	List<SchoolAndDistrictReportDTO> getSchoolSummaryReports(
			Map<String, Object> params);

	List<SchoolAndDistrictReportDTO> getDistrictSummaryReport(
			Map<String, Object> params);

	Long countSchoolDetailReports(Map<String, Object> params);

	List<SchoolAndDistrictReportDTO> getSchoolDetailReports(
			Map<String, Object> params);

	List<ExternalStudentReportDTO> getExternalStudentReports(
			Map<String, Object> params);

	SchoolAndDistrictReportDTO getDlmStateSummaryReport(
			Map<String, Object> params);

	SchoolAndDistrictReportDTO getDlmDistrictSummaryReport(
			Map<String, Object> params);

	List<OrganizationReportDetails> getAllSchoolSummaryReports(
			Map<String, Object> params);

	List<Long> getSchoolIdsInDistrictOfSummaryBundledReports(
			Map<String, Object> params);

	List<OrganizationReportDetails> getAllStudentSummaryBundledReports(
			Map<String, Object> params);


	List<Organization> getByTypeAndUserIdInParentByReportYear(@Param("organizationTypeCode") String organizationTypeCode, @Param("userId") Long userId,
			@Param("parentId") Long parentId, @Param("reportYear") Long reportYear);

	StudentReport selectByPrimaryKeyAndUserOrg(@Param("id") Long id, @Param("userOrgId") Long userOrgId);
	
	OrganizationReportDetails getSchoolAndDistrictReport(@Param("id") Long id);
	
	SchoolAndDistrictReportDTO getSchoolSummaryReportFile(@Param("id") Long id, @Param("userOrgId") Long userOrgId);

	SchoolAndDistrictReportDTO getDistrictSummaryReportByPrimaryKeyAndUserOrgId(@Param("id") Long id, @Param("userOrgId") Long userOrgId);

	Externalstudentreports selectByPrimaryKeyAndUserOrgCpass(@Param("id") Long id, @Param("userOrgId") Long userOrgId);
	
	SchoolAndDistrictReportDTO getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(@Param("id") Long id, @Param("userOrgId") Long userOrgId);
	
	SchoolAndDistrictReportDTO getDlmSummaryReportByPrimaryKeyAndUserOrgId(@Param("id") Long id, @Param("userOrgId") Long userOrgId, @Param("reportType") String reportType);


	Long countOfStudentSummaryReports(Map<String, Object> params);


	List<ExternalStudentReportDTO> getExternalStudentSummaryReports(
			Map<String, Object> params);


	 Long countByCriteria(Map<String, Object> parameters);


	List<OrganizationReportDetails> getAllSchoolSummaryBundledReportByDistrictId(
			Map<String, Object> params);
	
	

}
