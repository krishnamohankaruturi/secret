package edu.ku.cete.dataextract.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.ku.cete.dataextract.service.DataReportDetailService;
import edu.ku.cete.dataextracts.model.DataReportDetailsMapper;
import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.SchoolAndDistrictReportDTO;
import edu.ku.cete.web.StudentReportDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class DataReportDetailsServiceImpl implements DataReportDetailService{

	@Autowired
	DataReportDetailsMapper dataReportDetailsMapper;
	//For Report Year
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getCurrentReportYear(Long organizationId, Long assessmentProgId,String reportType, String reportCode,int currentYear) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getCurrentReportYear(organizationId,assessmentProgId,reportType,reportCode,currentYear);
	}
	
	//For Subject
	@Override
	public List<ContentArea> getContentAreasWhereReportsHaveProcessed(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getContentAreasWhereReportsHaveProcessed(params);
	}
	//For Grade
	@Override
	public List<GradeCourse> getGradesWhereReportsHaveProcessed(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getGradesWhereReportsHaveProcessed(params);
	}
	//For Teacher
	@Override
	public List<OrganizationReportDetails> getAllTeacherNamesForClassroomReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getAllTeacherNamesForClassroomReports(params);
	}
	@Override
	public Long countStudentReports(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.countStudentReports(params);
	}
	@Override
	public List<StudentReportDTO> getStudentReports(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getStudentReports(params);
	}
	@Override
	public List<Long> getSchoolIdsForDistrictDLMandCpass(Long districtId,
			String individualReportType, Long reportYear) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getSchoolIdsForDistrictDLMandCpass(districtId,individualReportType,reportYear);
	}
	@Override
	public List<Long> getSchoolIdsForDistrict(Long districtId, Long reportYear) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getSchoolIdsForDistrict(districtId,reportYear);
	}
	@Override
	public List<OrganizationReportDetails> getAllStudentsReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getAllStudentsReports(params);
	}
	@Override
	public List<SchoolAndDistrictReportDTO> getSchoolSummaryReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getSchoolSummaryReports(params);
	}
	@Override
	public List<SchoolAndDistrictReportDTO> getDistrictSummaryReport(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getDistrictSummaryReport(params);
	}
	@Override
	public Long countSchoolDetailReports(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.countSchoolDetailReports(params);
	}
	@Override
	public List<SchoolAndDistrictReportDTO> getSchoolDetailReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getSchoolDetailReports(params);
	}
	@Override
	public List<ExternalStudentReportDTO> getExternalStudentReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getExternalStudentReports(params);
	}
	@Override
	public SchoolAndDistrictReportDTO getDlmStateSummaryReport(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getDlmStateSummaryReport(params);
	}
	@Override
	public SchoolAndDistrictReportDTO getDlmDistrictSummaryReport(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getDlmDistrictSummaryReport(params);
	}
	@Override
	public List<OrganizationReportDetails> getAllSchoolSummaryReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getAllSchoolSummaryReports(params);
	}
	@Override
	public List<Long> getSchoolIdsInDistrictOfSummaryBundledReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getSchoolIdsInDistrictOfSummaryBundledReports(params);
	}
	@Override
	public List<OrganizationReportDetails> getAllStudentSummaryBundledReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getAllStudentSummaryBundledReports(params);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getByTypeAndUserIdInParentByReportYear(
			String organizationTypeCode, Long userId, Long parentId,Long reportYear) {
		return dataReportDetailsMapper.getByTypeAndUserIdInParentByReportYear(organizationTypeCode, userId, parentId,reportYear);
	}

	@Override
	public StudentReport getByPrimaryKeyAndUserOrg(Long id, Long userOrgId) {
		return dataReportDetailsMapper.selectByPrimaryKeyAndUserOrg(id, userOrgId);
	}
	@Override
	public OrganizationReportDetails getSchoolAndDistrictReport(Long id){
		return dataReportDetailsMapper.getSchoolAndDistrictReport(id);
	}
	@Override
	public SchoolAndDistrictReportDTO getSchoolSummaryReportFile(Long id, Long userOrgId) {
		return dataReportDetailsMapper.getSchoolSummaryReportFile(id, userOrgId);
	}
	@Override
	public SchoolAndDistrictReportDTO getDistrictSummaryReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId) {
		return dataReportDetailsMapper.getDistrictSummaryReportByPrimaryKeyAndUserOrgId(id, userOrgId);
	}
	@Override
	public Externalstudentreports getExternalReportFileDetailsByPrimaryKey(Long id, Long userOrgId) {
		return dataReportDetailsMapper.selectByPrimaryKeyAndUserOrgCpass(id, userOrgId);
	}
	@Override
	public SchoolAndDistrictReportDTO getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId) {
		return dataReportDetailsMapper.getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(id, userOrgId);
	}
	@Override
	public SchoolAndDistrictReportDTO getDlmSummaryReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId,String reportType) {
		return dataReportDetailsMapper.getDlmSummaryReportByPrimaryKeyAndUserOrgId(id, userOrgId,reportType);
	}

	@Override
	public Long countOfStudentSummaryReports(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.countOfStudentSummaryReports(params);
	}

	@Override
	public List<ExternalStudentReportDTO> getExternalStudentSummaryReports(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getExternalStudentSummaryReports(params);
	}
	
	@Override
	public Long countExternalStudentReports(Map<String, Object> parameters) {
		return dataReportDetailsMapper.countByCriteria(parameters);
	}

	@Override
	public List<OrganizationReportDetails> getAllSchoolSummaryBundledReportByDistrictId(
			Map<String, Object> params) {
		// TODO Auto-generated method stub
		return dataReportDetailsMapper.getAllSchoolSummaryBundledReportByDistrictId(params);
	}
}
