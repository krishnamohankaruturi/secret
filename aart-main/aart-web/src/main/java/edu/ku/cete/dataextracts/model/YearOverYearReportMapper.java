/**
 * 
 */
package edu.ku.cete.dataextracts.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.web.KAPStudentScoreDTO;
import edu.ku.cete.web.StudentReportDTO;

/**
 * @author ktaduru_sta
 *
 */
public interface YearOverYearReportMapper {
	
	
	List<KAPStudentScoreDTO> generateKAPStudentScoreExtract(@Param("organizationId")Long orgId, @Param("orgType")String orgType, @Param("contentAreaId")List<Long> contentAreaId, 
			@Param("schoolYears")List<Long> schoolYears, @Param("gradeIds")List<String> gradeIds, @Param("includeSubscore")String includeSubscores, @Param("currentSchoolYear")Long currentSchoolYear,
			@Param("stateStudentIdentifier") String stateStudentIdentifier);
	
	List<StudentReport> selectStudentReportsByStudentIdSubjSchYrGrade(@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("contentAreaId")List<Long> contentAreaId, @Param("schoolYears")List<Long> schoolYears, @Param("gradeIds")List<Long> gradeIds);	
	int getStudentReportCountByStudentIdSubject(@Param("stateStudentIdentifier") String stateStudentIdentifier,	@Param("contentAreaId")List<Long> contentAreaId);
	
	int getStudentReportCountByStudentIdSubjectUserOrg(@Param("stateStudentIdentifier") String stateStudentIdentifier,	@Param("contentAreaId")List<Long> contentAreaId, @Param("organizationId")Long orgId, @Param("orgType")String orgType);
	
	
	int getBySsidAndUserOrgId(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("organizationId") Long orgId, @Param("currentSchoolYear") int currentSchoolYear, @Param("orgType") String orgType);
	
	List<StudentReportDTO> searchByStateStudentIdForDLMOrCPASS(
			@Param("stateStudentIdentifier") String stateStudentIdentifier, 
			@Param("userId") Long userId, 
			@Param("currentUserLevel") String currentUserLevel, 
			@Param("orgId") Long orgId, 
			@Param("apAbbrNames") List<String> apAbbrNames,	
			@Param("isEnrolledInUserOrg") Boolean isEnrolledInUserOrg, 
			@Param("groupId") Long groupId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("reportYear") Long reportYear,
			@Param("authorityId") Long authorityId,
			@Param("groupCode") String groupCode);

	List<StudentReportDTO> searchByStudentLastName(
			@Param("studentLastName")String studentLastName, 
			@Param("userId")Long userId, 
			@Param("currentUserLevel") String currentUserLevel, 
			@Param("orgId") Long orgId, 
			@Param("limitCount") Integer limitCount,
			@Param("offset") Integer offset, 
			@Param("groupId") Long groupId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("reportYear") Long reportYear,
			@Param("authorityId") Long authorityId,
			@Param("groupCode") String groupCode);
	
	List<StudentReportDTO> searchByStateStudentIdForKAP(
			@Param("stateStudentIdentifier") String stateStudentIdentifier, 
			@Param("userId") Long userId, 
			@Param("currentUserLevel") String currentUserLevel, 
			@Param("orgId") Long orgId, 
			@Param("isEnrolledInUserOrg") Boolean isEnrolledInUserOrg, 
			@Param("groupId") Long groupId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("reportYear") Long reportYear,
			@Param("authorityId") Long authorityId);
	
	StudentReportDTO getStudentInfoForAllStudentsReports(
			@Param("stateStudentIdentifier") String stateStudentIdentifier, 
			@Param("userId") Long userId,
			@Param("currentUserLevel") String currentUserLevel, 
			@Param("orgId") Long orgId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("groupId") Long groupId);
	
	int countReportsForStudent(
			@Param("stateStudentIdentifier") String stateStudentIdentifier, 
			@Param("userId") Long userId, 
			@Param("currentUserLevel") String currentUserLevel,
			@Param("orgId") Long orgId, 
			@Param("apAbbrNames") List<String> apAbbrNames,			
			@Param("isEnrolledInUserOrg") Boolean isEnrolledInUserOrg, 
			@Param("groupId") Long groupId, 
			@Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("reportYear") Long reportYear,
			@Param("authorityId") Long authorityId,
			@Param("groupCode") String groupCode);
	
	Boolean isStudentCurrentlyEnrolledInUserOrgHierarchy(
			@Param("stateStudentIdentifier") String stateStudentIdentifier, 
			@Param("currentUserLevel") String currentUserLevel, 
			@Param("orgId") Long orgId, 
			@Param("currentSchoolYear") Long currentSchoolYear);
	
	Boolean doesSSIDExist(
			@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("stateId") Long stateId,
			@Param("currentSchoolYear") Long currentSchoolYear);
	
	StudentReport selectStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(
			@Param("id") Long id, 
			@Param("orgId") Long orgId,
			@Param("isStudentInUserOrgHierarchy") Boolean isStudentInUserOrgHierarchy);
	
    Externalstudentreports selectExternalStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(
    		@Param("id") Long id, 
			@Param("orgId") Long orgId,
			@Param("isStudentInUserOrgHierarchy") Boolean isStudentInUserOrgHierarchy);
	
    List<OrganizationTreeDetail> getOrganizationDetailByStudentId(@Param("studentId")Long studentId, @Param("schoolYear") Long schoolYear, @Param("subjectCode") String subjectCode, @Param("transferred") Boolean transferred);

  
	

}
