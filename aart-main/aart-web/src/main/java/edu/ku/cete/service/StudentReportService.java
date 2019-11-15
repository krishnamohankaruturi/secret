/**
 * 
 */
package edu.ku.cete.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.RubricScore;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.TaskVariantsFoils;
import edu.ku.cete.domain.enrollment.StudentTestSessionDto;
import edu.ku.cete.domain.report.NodeReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.RawScoreSectionWeights;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.KAPStudentScoreDTO;
import edu.ku.cete.web.SchoolAndDistrictReportDTO;
import edu.ku.cete.web.StudentReportDTO;


/**
 * @author m802r921
 *
 */
public interface StudentReportService {

    /**
     * @param rosterId {@link Long}
     * @param testSessionId {@link Long}
     * @return {@link List}
     */
    List<StudentTestSessionDto> getTestSessionReport(List<Long> rosterId,Long testSessionId, boolean includeFoilLessItems, Long currentSchoolYear, Long organizationId);

    /**
     * Returns the task variants foils along with task variant set on each.
     * @param taskVariantId
     * @return {@link TaskVariant}
     */
    List<TaskVariantsFoils> getTaskVariant(Long taskVariantId);

	/**
	 * @param rosterId
	 * @return
	 */
	List<StudentTestSessionDto> getTestSessionReportForPdf(Long rosterId, Long completedStatusId, Long organizationId);
	
	public	RubricScore getRubricScoreByStudent(Long studentId,Long testId,Long taskVariantId);
	
	RubricScore getRubricScore(Long studentstestId,Long taskVariantId);

	/**
	 * @param testSessionId
	 * @return
	 */
	List<StudentsTestSections> getPerformanceStatus(Long testSessionId);

	/**
	 * @param studentKeyword
	 * @param testKeyword
	 * @param organizationId 
	 * @param limitCount 
	 * @param pageCount 
	 * @param sortType 
	 * @param sortByColumn 
	 * @return
	 */
	Collection<NodeReport> selectByStudentAndTest(String studentKeyword,
			String testKeyword, Long organizationId, int limitCount, int pageCount,
			String sortByColumn, String sortType);

	/**
	 * @param studentKeyword
	 * @param testKeyword
	 * @param organizationId 
	 * @return
	 */
	int countByStudentAndTest(String studentKeyword, String testKeyword, Long organizationId);
	
	/**
	 * @param taskVariantId
	 * @return
	 */
	int countByTaskVariantId(Long taskVariantId);
	
	/**
	 * @param testSessionId
	 * @param completedStatusId
	 * @return
	 */
	Integer getCompletedStudentsCount(Long testSessionId, Long completedStatusId);	
	
	RubricScore saveRubricScore(Long rubricScoreId,Long taskVariantId,String rubricInfoIds,Long studentId,Long testId,Float score);

	Integer getCompletedAndEnrolledStudentsCount(Long testSessionId, Long completedStatusId);

	List<StudentTestSessionDto> getAutoTestSessionReport(Long testSessionId, boolean includeFoilLessItems,
			Long currentSchoolYear, Long organizationId);

	StudentReport getScaleScoreAndPerformanceLevel(Long studentid,
			Long enrollmentId, Long stage1TestId, Long stage2TestId, Long grade, Long contentAreaId);

	Long countStudentReports(Map<String, Object> parameters);
	List<StudentReportDTO> getStudentReports(Map<String, Object> parameters);
	List<ExternalStudentReportDTO> getExternalStudentReports(Map<String, Object> parameters);
	Long countExternalStudentReports(Map<String, Object> parameters);
	
	Long countSchoolDetailReports(Map<String, Object> parameters);
	Long countDistrictDetailReports(Map<String, Object> parameters);

	List<SchoolAndDistrictReportDTO> getSchoolDetailReports(Map<String, Object> parameters);
	List<SchoolAndDistrictReportDTO> getDistrictDetailReports(Map<String, Object> parameters);
	
	List<OrganizationReportDetails> getAllStudentsReports(Map<String, Object> parameters);
	
	StudentReport getByPrimaryKey(Long id);
	
	StudentReport getByPrimaryKeyAndUserOrg(Long id, Long userOrgId);
	Externalstudentreports getExternalReportFileDetailsByPrimaryKey(Long id, Long userOrgId);
	
	SchoolAndDistrictReportDTO getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId);
	
	OrganizationReportDetails getSchoolAndDistrictReport(Long id); 
	
	List<Long> getSchoolIdsForDistrict(Long districtId, Long reportYear);
	
	List<Long> getSchoolIdsForDistrictDLMandCpass(Long districtId, String reportTypeCode, Long reportYear);
	List<SchoolAndDistrictReportDTO> getSchoolSummaryReports(Map<String, Object> parameters);
	SchoolAndDistrictReportDTO getSchoolSummaryReportFile(Long id, Long userOrgId);
	
	List<SchoolAndDistrictReportDTO> getDistrictSummaryReport(Map<String, Object> parameters);
	
	SchoolAndDistrictReportDTO getDlmDistrictSummaryReport(Map<String, Object> parameters);
	
	SchoolAndDistrictReportDTO getDlmStateSummaryReport(Map<String, Object> parameters);
	
	SchoolAndDistrictReportDTO getDistrictSummaryReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId);
	
	int taskvariantCountByTestId(Long testId);
	
	List<RawScoreSectionWeights> getRawScoreSectionWeights(Long assessmentProgramId, Long subjectId, Long testId, Long schoolYear);
	
	List<Long> getStudentReportSchoolYearsBySubject(List<Long> contentAreaId);
	
	List<KAPStudentScoreDTO> generateKAPStudentScoreExtract(Long orgId, String orgType, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeIds, String includeSubscores, Long currentSchoolYear, String stateStudentIdentifier);
	
	List<StudentReport> selectStudentReportsByStudentIdSubjSchYrGrade(String stateStudentIdentifier, List<Long> contentAreaId, List<Long> schoolYears, List<Long> gradeIds);

	List<KAPStudentScoreDTO> generateKAPStudentScoreExtractForDistrictUser(Long orgId, String orgType, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeIds, String includeSubscores, Long currentSchoolYear, String stateStudentIdentifier);
	
	List<StudentReportDTO> searchByStudentLastName(String studentLastname, Long userId, String currentUserLevel, Long orgId, Integer limitCount, Integer offset, Long groupId, Long currentSchoolYear, Long reportYear, Groups groups);
	
	int getStudentReportCountByStudentIdSubject(String stateStudentIdentifier, List<Long> contentAreaId);
	
	int getStudentReportCountByStudentIdSubjectUserOrg(String stateStudentIdentifier, List<Long> contentAreaId, Long orgId, String orgType);

	List<StudentReportDTO> searchByStateStudentIdForKAP(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear);
	
	List<StudentReportDTO> searchByStateStudentIdForDLM(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear, Groups groups);
	
	List<StudentReportDTO> searchByStateStudentIdForCPASS(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear, Groups groups);

	StudentReportDTO getStudentInfoForAllStudentsReports(String stateStudentIdentifier, Long userId,
			String currentUserLevel, Long orgId, Long currentSchoolYear, Long groupId);

	int countReportsForStudent(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear, Groups group);	
	
	Boolean doesSSIDExist(String stateStudentIdentifier, Long stateId, Long currentSchoolYear);
	
	StudentReport getByPrimaryKeyForAllStudentReports(Long id, Long orgId, String userOrgLevel, Long currentSchoolYear, String stateStudentIdentifier);
	
	Externalstudentreports getExternalReportByPrimaryKeyForAllStudentReports(Long id, Long orgId, String userOrgLevel, Long currentSchoolYear, String stateStudentIdentifier);
	
	List<SchoolAndDistrictReportDTO> getELPAStudnetsScoreFileReport(
			Map<String, Object> params);

	Long countExternalStudentReportsForTeacherRoster(Map<String, Object> parameters);

	List<ExternalStudentReportDTO> getExternalStudentReportsForTeacherRoster(Map<String, Object> parameters);

	SchoolAndDistrictReportDTO getDlmSummaryReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId,String reportType);
	
	List<OrganizationReportDetails> getAllStudentSummaryBundledReports(Map<String, Object> parameters);
	
	List<Long> getSchoolIdsInDistrictOfSummaryBundledReports(Map<String, Object> parameters);
	
	List<OrganizationReportDetails> getAllSchoolSummaryBundledReportByDistrictId(Map<String, Object> parameters);
	
	List<ExternalStudentReportDTO> getExternalStudentSummaryReports(Map<String, Object> parameters);
	
	Long countOfStudentSummaryReports(Map<String, Object> parameters);
	
	List<ExternalStudentReportDTO> getStudentSummaryReportsByRosterId(Map<String, Object> parameters);
	
	Long countOfStudentSummaryReportsByRosterId(Map<String, Object> parameters);
	
	List<OrganizationReportDetails> getAllSchoolSummaryReports(Map<String, Object> parameters);
	
	List<OrganizationReportDetails> getAllTeacherNamesForClassroomReports(Map<String, Object> parameters);

	List<String> getDistinctAssessmentCode(Long assessmentProgramId,Long stateId, Long schoolYear, Long testingProgramId, String reportCycle, Long gradeCourseId, Long contentAreaId);

	List<Long> getStudentIdsForReportGeneration(Long assessmentProgramId,Long schoolYear, Long stateId, String assessmentCode, String reportCycle, Long testingProgramId, Long gradeCourseId, Long contentAreaId, String processByStudentId, String reprocessEntireDistrict, Integer pageSize, Integer offset);
	
	ExternalStudentReportDTO getStudentForReportGeneration(Long assessmentProgramId,Long schoolYear, Long stateId, String assessmentCode, String reportCycle, Long testingProgramId, String processByStudentId, String reprocessEntireDistrict, Long studentId);

	void updateStudentReportFilePath(ExternalStudentReportDTO externalstudentreport);

	List<Long> getStudentIdsFromStudentReport(Long assessmentProgramId,
			Long schoolYear, Long stateId, Long gradeCourseId,
			Long contentAreaId, String processByStudentId,
			String reprocessEntireDistrict, int offset, int pageSize);

	List<StudentReport> getStudentForKELPAReport(Long assessmentProgramId,
			Long schoolYear, Long stateId, Long studentId, Long gradeCourseId, Long contentAreaId, String processByStudentId,
			String reprocessEntireDistrict);	
}
