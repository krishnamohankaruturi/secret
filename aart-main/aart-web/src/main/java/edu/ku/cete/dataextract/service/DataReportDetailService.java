package edu.ku.cete.dataextract.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.SchoolAndDistrictReportDTO;
import edu.ku.cete.web.StudentReportDTO;

public interface DataReportDetailService {

	List<Long> getCurrentReportYear(Long organizationId, Long assessmentProgId, String reportType, String reportCode, int currentYear);

	
	List<ContentArea> getContentAreasWhereReportsHaveProcessed(
			Map<String, Object> params);

	List<GradeCourse> getGradesWhereReportsHaveProcessed(
			Map<String, Object> params);

	List<OrganizationReportDetails> getAllTeacherNamesForClassroomReports(
			Map<String, Object> params);

	Long countStudentReports(Map<String, Object> params);

	List<StudentReportDTO> getStudentReports(Map<String, Object> params);

	List<Long> getSchoolIdsForDistrictDLMandCpass(Long districtId,
			String individualReportType, Long reportYear);

	List<Long> getSchoolIdsForDistrict(Long districtId, Long reportYear);

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

	Collection<? extends Organization> getByTypeAndUserIdInParentByReportYear(
			String organizationSchoolCode, Long userId, Long id, Long reportYear);

	StudentReport getByPrimaryKeyAndUserOrg(Long id, Long organizationId);

	OrganizationReportDetails getSchoolAndDistrictReport(Long id);

	SchoolAndDistrictReportDTO getSchoolSummaryReportFile(Long id,
			Long organizationId);

	SchoolAndDistrictReportDTO getDistrictSummaryReportByPrimaryKeyAndUserOrgId(
			Long id, Long organizationId);

	Externalstudentreports getExternalReportFileDetailsByPrimaryKey(Long id,
			Long organizationId);

	SchoolAndDistrictReportDTO getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(
			Long id, Long organizationId);

	SchoolAndDistrictReportDTO getDlmSummaryReportByPrimaryKeyAndUserOrgId(
			Long id, Long organizationId, String string);


	Long countOfStudentSummaryReports(Map<String, Object> params);


	List<ExternalStudentReportDTO> getExternalStudentSummaryReports(
			Map<String, Object> params);


	Long countExternalStudentReports(Map<String, Object> parameters);


	List<OrganizationReportDetails> getAllSchoolSummaryBundledReportByDistrictId(
			Map<String, Object> params);

}
