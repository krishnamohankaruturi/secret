/**
 * 
 */
package edu.ku.cete.dataextract.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ku.cete.domain.BluePrintCriteriaDescription;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.web.DLMTestStatusExtractDTO;
import edu.ku.cete.web.FCSAnswer;
import edu.ku.cete.web.FCSDataExtractDTO;
import edu.ku.cete.web.FCSHeader;
import edu.ku.cete.web.ISMARTTestAdminExtractDTO;
import edu.ku.cete.web.ITIBPCoverageExtractRostersDTO;
import edu.ku.cete.web.ITIBPCoverageRosteredStudentsDTO;
import edu.ku.cete.web.KAPStudentScoreDTO;
import edu.ku.cete.web.KELPATestAdministrationDTO;
import edu.ku.cete.web.MonitorScoringExtractDTO;
import edu.ku.cete.web.OrganizationExtractDTO;
import edu.ku.cete.web.PNPAbridgedExtractDTO;
import edu.ku.cete.web.RosterExtractDTO;
import edu.ku.cete.web.StudentExitExtractDTO;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;
import edu.ku.cete.web.StudentReportDTO;
import edu.ku.cete.web.StudentSpecialCircumstanceDTO;
import edu.ku.cete.web.StudentTestDTO;
import edu.ku.cete.web.StudentTestSessionInfoDTO;
import edu.ku.cete.web.TECExtractDTO;
import edu.ku.cete.web.TestFormAssignmentsInfoDTO;
import edu.ku.cete.web.TestFormMediaResourceDTO;
import edu.ku.cete.web.TestingReadinessEnrollSubjects;
import edu.ku.cete.web.TestingReadinessExtractDTO;
import edu.ku.cete.web.UserDetailsAndRolesDTO;
import edu.ku.cete.web.UserSecurityAgreemntDTO;
import edu.ku.cete.web.ViewStudentDTO;
import edu.ku.cete.web.KELPA2StudentScoreDTO;

public interface DataExtractService {
	
	List<Long> getStudentReportSchoolYearsBySubject(List<Long> contentAreaId,String assessmentCode);
	
	List<KAPStudentScoreDTO> generateKAPStudentScoreExtract(Long orgId, String orgType, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeIds, String includeSubscores, Long currentSchoolYear, String stateStudentIdentifier);
	
	List<StudentReport> selectStudentReportsByStudentIdSubjSchYrGrade(String stateStudentIdentifier, List<Long> contentAreaId, List<Long> schoolYears, List<Long> gradeIds);

	List<KAPStudentScoreDTO> generateKAPStudentScoreCurrentExtractForDistrictUser(
			List<Long> orgIds,  Boolean isDistict,Boolean isSchool, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeAbbreviatedNames,Long currentSchoolYear, Long currentReportYear,int offset, int limit,Long stateId, String dataExtractReportStudent,Long assessmentProgramId );

	List<KAPStudentScoreDTO> generateKAPStudentScoreSpecifiedExtractForDistrictUser(List<Long> orgIds,Boolean isState, Boolean isDistict,Boolean isSchool,
			Long currentSchoolYear, String stateIdStudentIdentifier, Long currentReportYear,boolean  isCurrentEnrolled ,Long assessmentProgramId);
	
	List<KAPStudentScoreDTO> generateKAPStudentScoreTestedExtractForDistrictUser(List<Long> orgIds,Long currentOrganizationId,Long districtId,boolean isState, boolean isDistict,boolean isSchool,
			List<Long> contentAreaIds, List<Long> schoolYears, List<String> gradeAbbreviatedNames, Long currentSchoolYear,
			Long currentReportYear,int offset, int limit,String extractStudent,Long stateId,Long assessmentProgramId);
	
	int getStudentReportCountByStudentIdSubject(String stateStudentIdentifier, List<Long> contentAreaId);
	
	int getStudentReportCountByStudentIdSubjectUserOrg(String stateStudentIdentifier, List<Long> contentAreaId, Long orgId, String orgType);
	
	Organization get(Long organizationId);
	
	int getEnrollmentCountBySsidOrgId(String stateStudentIdentifier, Long orgId, int currentSchoolYear, String orgType);
	
	//dataExtractService.getOrganization
	//Supportive queries in the extracts
	Organization getOrganization(Long organizationId);
	List<Organization> getAllChildrenByType(Long requestedOrgId, String type);
	List<Organization> getAllChildrenWithParentByType(Long organizationId, String organizationTypeCode);
	List<Long> getAllChildrenIdsByType(Long orgId, String typeCode);
	List<Organization> getAllParents(long organizationId);
	List<Long> getAllChildrenOrgIds(Long organizationId);
	
	AssessmentProgram findByAbbreviatedName(String abbreviatedName);
	AssessmentProgram findAssessmentProgramByStudentId(Long id, User user);
	AssessmentProgram findByAssessmentProgramId(long assessmentProgramId);
	List<AssessmentProgram> getAllAssessmentProgramByUserId(Long userId);
	
	Student findByStudentId(long studentId);
	
	User getActiveOrInactiveUser(long id);
	
	Long getCategoryId(String categoryCode, String categoryTypeCode);
    	
	//1. Case 2: Enrollment Extract
	List<ViewStudentDTO> getViewStudentInformationRecordsExtract(Long orgId, boolean shouldOnlySeeRosteredStudents, Long userId, int currentSchoolYear, List<Long> assessmentPrograms);

	//2. case 3: PNP Settings
	List<Long> getActiveStudentIdsWithPNPEnrolledInOrg(Long orgId, boolean onlyRostered, Long teacherId, int limit, int offset, int currentSchoolYear, List<Long> assessmentPrograms);
	List<Enrollment> getCurrentEnrollmentsByStudentId(Long studentId, Long orgId, int currentSchoolYear); 
	//3. case 4: Roster Extract
	List<RosterExtractDTO> getRosterDataExtractForOrg(Long orgId, boolean shouldOnlySeeRosteredStudents, Long userId, int currentSchoolYear, List<Long> assessmentPrograms);
	//4. Case 5: User Extract 
	List<UserDetailsAndRolesDTO> getUserDetailsAndRolesByOrgId(Long orgId, List<Long> assessmentPrograms, boolean includeInternalUsers);
	//5. Case 6: Questar 
	List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainers(Long studentId, User user);
	//6. case 7: TEC (Test Records)
	List<TECExtractDTO> getTECExtractByOrg(Long orgId, boolean shouldOnlySeeRosteredStudents, Long userId, int currentSchoolYear, List<Long> assessmentPrograms);
	//7. case 8: DLM_TEST_STATUS (DLM Test Administration Monitoring)
	List<DLMTestStatusExtractDTO> dlmTestStatusReport(Long orgId, boolean shouldOnlySeeRosteredStudents, 
			Long userId, int currentSchoolYear, Date iniToDate, Date iniFromDate, Date eoyToDate, Date eoyFromDate, Long userOrgId);

	//8. case 10: PNP_SUMMARY (PNP Setting Counts)
	List<Long> getAllStudentIdsByOrgIdAssessmentProgram(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId, int currentSchoolYear, String assessmentProgramCode, int offset, int limit);
	List<Long> getNonDLMStudentIdsEnrolledInOrg(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId, int currentSchoolYear, int offset, int limit, List<Integer> assessmentPrograms, List<Long> alternateAssessmentProgramIds);
	
	//10. case 13: TEST_ADMIN_KAP_AMP (KAP Test Administration Monitoring)
	//11. case 14: Test Tickets for KAP
	List<StudentTestSessionInfoDTO> getStudentsTestsInfo(Long organionId, Long currentSchoolYear, Boolean isTeacher, Long educatorId, 
    		String assessmentProgramCode, int offset, int limit, List<Long> assessmentPrograms , boolean isPltw);
		
	List<StudentTestDTO> getAllStudentsTestSectionDetails(List<Long> studentTestIds);
	
	//12. case 19: Test Form Assignments to Test Collections
	List<TestFormAssignmentsInfoDTO> getTestFormAssignmentsExtracts(List<Long> assessmentPrograms, String qcCompleteStatus, String beginDate, String toDate);

	//13. case 20:  Test Media Resource (Test Form Media Resource File QC)
	List<TestFormMediaResourceDTO> getTestFormMediaResource(String fromDate, String toDate, List<Long> assessmentPrograms, String qcStatus, String media);
	
	//14. case 25: Student Login Usernames/Passwords
	List<ViewStudentDTO> getKAPStudentUserNamePasswordExtract(Long orgChildrenById, boolean isTeacher, Long educatorId, Integer currentSchoolYear, Long gradeId, Long subjectId);
	
	//15. case 26: //Restricted Special Circumstance Code
	List<StudentSpecialCircumstanceDTO> getStudentSpecialCircumstanceInfo(Long orgChildrenById, Integer currentSchoolYear, List<Long> assessmentPrograms);
	
	//16. case 34: //DLM_PD_TRAINING_STATUS(Training Status)
	
	
	//17. case 35: //Security Agreement Completion
	List<UserSecurityAgreemntDTO> getUserSecurityAgreementDetails(List<Long> organizationIds, List<Long> assessmentPrograms, Long currentSchoolYear, boolean includeInternalUsers);
	
	//18. case 36: //KELPA2 Test Administration Monitoring
	List<KELPATestAdministrationDTO> getKELPATestAdministrationExtract(Long orgId, Long currentSchoolYear, Long assessmentProgramId, int offset, int limit);
	
	//19. case 37: //Monitor Scoring
	List<MonitorScoringExtractDTO> getMonitorScoringExtractByOrg(Long orgId, List<Long> assessmentPrograms, Long currentSchoolYear, int offset, int limit);

	
	//20. case 38: //KAP Student Scores
	
	
	//21. case 39: //DLM Blueprint Coverage Summary
	List<ITIBPCoverageExtractRostersDTO> getrosterDetalsGroupByTeacherForITIBP(Long orgId, Long gradeId, Long subjectId, int currentSchoolYear, 
			boolean isTeacher, Long teacherId);
	
	List<ITIBPCoverageExtractRostersDTO> getRosterDetailsForITIBluePrintExtract(Long orgId, Long gradeId, Long subjectId, 
			int currentSchoolYear, List<Long> teacherIds);	
	
	List<BluePrintCriteriaDescription> getBluePrintCriteriaDescByGradeAndSub(Long contentAreaId, String gradeCourseAbbrName);

	List<OrganizationTreeDetail> getOrganizationDetailByStudentId(Long studentId, Long schoolYear, String subjectCode, Boolean transferred);

	int getNumberOfStudentsMetITIBPCrietria(Long criteria, Long contentAreaId, String gradeCourseAbbrName, List<ITIBPCoverageRosteredStudentsDTO> rosteredStudentsDetailsList,
			int schoolYear, Long operationalTestWindowID);
	
	List<StudentReportDTO> getWritingStudents(Map<String, Object> params);
	
	List<StudentReportDTO> getWritingResponseForReports(Map<String, Object> params);

	List<FindEnrollments> findStudentEnrollment(String studentStateId,
			Long stateId, Integer currentSchoolYear, Long educatorId,
			Boolean isTeacher);
	
	// 44. Case 44: Organization Extract
	List<OrganizationExtractDTO> getOrganizationsExtractByOrg(Long orgId, boolean includeInternalUsers,
			String typeLevelCode);

	// 45 case 45:First Contact Survey Extract
	List<FCSDataExtractDTO> getFirstContactSurveyDetails(Long orgId, List<Long> assessmentPrograms,
			Long currentSchoolYear,boolean isTeacher,Long userId);

	List<FCSHeader> getDynamicFCSHeaders();

	List<FCSAnswer> getFCSAnswers(Long studentId);

	// 46: Student Testing Readiness Extract
	List<TestingReadinessExtractDTO> getStudentReadinessExtractDetails(List<Long> allOrgIds, List<Integer> assessmentPrograms,
			Long currentSchoolYear, boolean onlyRostered, Long teacherId);

	List<TestingReadinessEnrollSubjects> getTestRecordsForExtract(Set<Long> studentIds, Long currentSchoolYear);
	//47.Student Exit extract
	List<StudentExitExtractDTO> getDLMStudentExitExtractForGrf(Long orgId, int year, String assesmentProgram, Boolean isStateHaveSpecificExitCode);
	//41. SpecialCircumstanceExtract
	List<UploadScCodeFile> getDLMSpecialCircumstanceExtract(Long orgId, int year,String stateCode);

	List<UploadGrfFile> getDLMGeneralResearchExtract(Long stateId,
			Long districtId, Long schoolId, Long subject, Long grade, int year, int offset, int limit);

	List<Organization> getOrganizationByTypeForGRF(Long stateId, Long districtId,
			String organizationDistrictCode, int reportYear);

	List<ContentArea> getContentAreasforGRF(Long stateId, Long districtId,
			Long schoolId, int reportYear);

	List<GradeCourse> getGradeCourseByGRF(Long stateId, Long districtId,
			Long schoolId, Long contentAreaId, int reportYear);

	List<UploadIncidentFile> getDLMIncidentExtract(Long orgId, int year,
			int offset, int limit);

	Enrollment findStudentBasedOnStateStudentIdentifier(String stateStudentIdentifier, Long organizationId);	

	//case 50: ISMART_TEST_STATUS (ISMART Test Administration Monitoring)
	List<ISMARTTestAdminExtractDTO> getISmartTestStatusRecords(Long orgId, boolean shouldOnlySeeRosteredStudents, Long userId, int currentSchoolYear, Long userOrgId, List<Long> assessmentProgramIds, List<String> ismartContentAreas);

	List<PNPAbridgedExtractDTO> getActiveStudentsWithPNPEnrolledInOrg(
			Long orgId,
			boolean onlyRostered,
			Long teacherId,
			int limit,
			int offset,
			int currentSchoolYear,
			List<Long> assessmentPrograms,
			boolean includeAllStudents);


	
	List<GradeCourse> getGradesByOrgIdForStudentScoreTestedExtract(boolean isDistict,boolean isSchool, List<Long> asList,
			Long assessmentProgramId, Long organizationId,String displayIdentifier,Long reportYear);
	List<GradeCourse> getGradesByOrgIdForStudentScoreExtract(boolean isDistict,boolean isSchool, List<Long> orgId, int schoolYear,
			String displayIdentifier,Long assessmentProgramId);
	
	
	List<ContentArea> getSubjectsForStudentScoreExtract(Long schoolYear, Long assessmentProgramId,boolean isDistict,boolean isSchool, List<Long> orgList);
	List<ContentArea> getSubjectsForTestedStudentScoreExtract(Long reportYear, Long assessmentProgramId,boolean isDistict,boolean isSchool, List<Long> orgList);

	
	List<PNPAbridgedExtractDTO> getActiveStudentsWithPNPEnrolledInOrgPLTW(
			Long orgId,
			boolean onlyRostered,
			Long teacherId,
			int currentSchoolYear,
			List<Long> assessmentPrograms,
			boolean includeAllStudents);	
	List<KELPA2StudentScoreDTO> generateKELPA2StudentScoreCurrentExtractForDistrictUser(
			List<Long> orgIds, Boolean isDistict,Boolean isSchool, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeAbbreviatedNames,
			Long currentSchoolYear, Long currentReportYear,int offset, int limit,Long stateId, String dataExtractReportStudent,Long assessmentProgramId );

	List<Long> getStudentReportSchoolYearsBySubjectForKelpa(String kelpa2StudentReportAssessmentcode);
	
	Long getKAPCurrentStudentCount(List<Long> orgIds, boolean isDistict, boolean isSchool, Long currentSchoolYear,Long stateId, Long assessmentProgramId);
	
	List<GradeCourse> getGradesByOrgIdForKelpaStudentScoreExtract(boolean isDistict, boolean isSchool, List<Long> orgId, int schoolYear, String displayIdentifier,
			Long assessmentProgramId);
}
