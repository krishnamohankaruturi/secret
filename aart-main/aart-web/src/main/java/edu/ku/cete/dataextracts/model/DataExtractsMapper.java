package edu.ku.cete.dataextracts.model;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.dlm.iti.BPCriteriaAndGroups;
import edu.ku.cete.domain.BluePrintCriteriaDescription;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.web.DLMTestStatusExtractDTO;
import edu.ku.cete.web.FCSAnswer;
import edu.ku.cete.web.FCSDataExtractDTO;
import edu.ku.cete.web.FCSHeader;
import edu.ku.cete.web.ISMARTTestAdminExtractDTO;
import edu.ku.cete.web.ITIBPCoverageExtractRostersDTO;
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

public interface DataExtractsMapper {

	//1. Case 2: Enrollment Extract
	List<ViewStudentDTO> getViewStudentInformationRecordsExtract(
    		@Param("orgChildrenById") Long orgChildrenById,
    		@Param("isTeacher") boolean isTeacher,
    		@Param("educatorId") Long educatorId, @Param("currentSchoolYear") Integer currentSchoolYear, @Param("assessmentPrograms") List<Long> assessmentPrograms);
    
	//2. case 3: PNP Settings
	List<Long> getActiveStudentIdsWithPNPInOrg(@Param("organizationId") Long orgId, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId, @Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("limit") int limit, @Param("offset") int offset, @Param("assessmentPrograms") List<Long> assessmentPrograms);
	
	List<PNPAbridgedExtractDTO> getActiveStudentsWithPNPInOrg(
			@Param("organizationId") Long orgId,
			@Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId,
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("limit") int limit,
			@Param("offset") int offset,
			@Param("assessmentPrograms") List<Long> assessmentPrograms,
			@Param("includeAllStudents") boolean includeAllStudents);
	
	List<Enrollment> getCurrentEnrollmentsByStudentId(@Param("studentId") Long studentId, @Param("organizationId") Long orgId,
 			@Param("currentSchoolYear") int currentSchoolYear);
	
	//3. case 4: Roster Extract
	List<RosterExtractDTO> getRosterDataExtractForOrg(@Param("organizationId") Long orgId, @Param("isTeacher") Boolean isTeacher,
			@Param("educatorId") Long educatorId, @Param("currentSchoolYear") int currentSchoolYear,@Param("assessmentPrograms") List<Long> assessmentPrograms);
 	
	//4. Case 5: User Extract  
	List<UserDetailsAndRolesDTO> getUserDetailsAndRolesByOrgId(@Param("organizationId") Long organizationId,@Param("assessmentPrograms") List<Long> assessmentPrograms,@Param("includeInternalUsers") boolean includeInternalUsers);

	//5. Case 6: Questar 
	List<StudentProfileItemAttributeDTO> selectAllAttributesDataAndStudentSelection(@Param("studentId") Long studentId, @Param("currentSchoolYear") int currentSchoolYear,@Param("currentAssessmentProgramId") Long currentAssessmentProgramId);
 
	//6. case 7: TEC (Test Records)
	List<TECExtractDTO> getTECExtractByOrg(@Param("organizationId") Long orgId, @Param("isTeacher") Boolean isTeacher, @Param("educatorId") Long educatorId, @Param("currentSchoolYear") int currentSchoolYear, @Param("assessmentPrograms") List<Long> assessmentPrograms);

	//7. case 8: DLM_TEST_STATUS (DLM Test Administration Monitoring)
	List<DLMTestStatusExtractDTO> dlmTestStatusReport(@Param("organizationId") Long orgId
			, @Param("isTeacher") Boolean isTeacher, @Param("educatorId") Long educatorId
			, @Param("currentSchoolYear") int currentSchoolYear, @Param("iniToDate") Date iniToDate, @Param("iniFromDate") Date iniFromDate,
			@Param("eoyToDate") Date eoyToDate,@Param("eoyFromDate") Date eoyFromDate, @Param("contractOrgId") Long contractOrgId);

	//8. case 10: PNP_SUMMARY (PNP Setting Counts)
	List<Long> getAllStudentIdsByOrgIdAssessmentProgram(@Param("organizationId") Long orgId, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId, 
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("assessmentProgramCode") String assessmentProgramCode,
			@Param("offset") int offset, 
			@Param("limit") int limit);

	List<Long> getNonDLMStudentIdsInOrg(@Param("organizationId") Long orgId, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId, @Param("currentSchoolYear") Integer currentSchoolYear, @Param("offset") int offset, @Param("limit") int limit, 
			@Param("assessmentPrograms") List<Integer> assessmentPrograms,
			@Param("alternateAssessmentProgramIds") List<Long> alternateAssessmentProgramIds);
	
	//10. case 13: TEST_ADMIN_KAP_AMP (KAP Test Administration Monitoring)
	//11. case 14: Test Tickets for KAP
	List<StudentTestSessionInfoDTO> getStudentsTestsInfo(@Param("organionId") Long organionId, @Param("currentSchoolYear") Long currentSchoolYear,
    		@Param("isTeacher") Boolean isTeacher, @Param("educatorId") Long educatorId, 
    		@Param("assessmentProgramCode") String assessmentProgramCode, @Param("offset") int offset, @Param("limit") int limit, @Param("assessmentPrograms") List<Long> assessmentPrograms,@Param("isPltw") boolean isPltw);
   
	List<StudentTestDTO> getAllStudentsTestSectionDetails(
			@Param("studentTestIds") List<Long> studentTestIds);
	
	
	//12. case 19: Test Form Assignments to Test Collections
	List<TestFormAssignmentsInfoDTO> getTestFormAssignmentsExtracts(@Param("assessmentPrograms") List<Long> assessmentPrograms,
			@Param("qcCompleteStatus") String qcCompleteStatus,@Param("beginDate") String beginDate, @Param("toDate") String toDate);
	
	//13. case 20:  Test Media Resource (Test Form Media Resource File QC)
	List<TestFormMediaResourceDTO> getTestFormMediaResource(@Param("fromDate") String fromDate,
			@Param("toDate") String toDate,@Param("assessmentPrograms") List<Long> assessmentPrograms,@Param("qcStatus") String qcStatus,@Param("media") String media);
	
	//14. case 25: Student Login Usernames/Passwords
	List<ViewStudentDTO> getKAPStudentUserNamePasswordExtract(
    		@Param("orgChildrenById") Long orgChildrenById,
    		@Param("isTeacher") boolean isTeacher,
    		@Param("educatorId") Long educatorId, 
    		@Param("currentSchoolYear") Integer currentSchoolYear,
    		@Param("gradeId") Long gradeId, 
    		@Param("subjectId") Long subjectId);
	
	//15. case 26: //Restricted Special Circumstance Code
	List<StudentSpecialCircumstanceDTO> getStudentSpecialCircumstanceInfo(@Param("orgChildrenById") Long orgChildrenById, 
	    	@Param("currentSchoolYear")Integer currentSchoolYear, @Param("assessmentPrograms") List<Long> assessmentPrograms);
	
	//17. case 35: //Security Agreement Completion
	List<UserSecurityAgreemntDTO> getUserSecurityAgreementDetails(@Param("organizationIds") List<Long> organizationIds,
			@Param("assessmentPrograms") List<Long> assessmentPrograms, @Param("currentSchoolYear") Long currentSchoolYear, @Param("includeInternalUsers") boolean includeInternalUsers);
		
	//18. case 36: //KELPA2 Test Administration Monitoring
	List<KELPATestAdministrationDTO> getKELPATestAdministrationExtract(@Param("orgId")Long orgId, @Param("currentSchoolYear")Long currentSchoolYear,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("offset")int offset, @Param("limit")int limit);
	
	//19. case 37: //Monitor Scoring
	List<MonitorScoringExtractDTO> getMonitorScoringExtractByOrg(@Param("orgId")Long orgId,
			 @Param("assessmentProgIds")List<Long> assessmentPrograms, @Param("currentSchoolYear") Long currentSchoolYear, @Param("offset") int offset, @Param("limit") int limit);

	//21. case 39: //DLM Blueprint Coverage Summary
	List<ITIBPCoverageExtractRostersDTO> getrosterDetalsGroupByTeacherForITIBP(@Param("orgId") Long orgId, @Param("gradeId") Long gradeId, @Param("subjectId") Long subjectId, 
			@Param("currentSchoolYear") int currentSchoolYear, @Param("isTeacher") boolean isTeacher, @Param("teacherId") Long teacherId);

	List<ITIBPCoverageExtractRostersDTO> getRosterDetailsForITIBluePrintExtract(@Param("orgId") Long orgId, @Param("gradeId") Long gradeId, @Param("subjectId") Long subjectId, 
			@Param("currentSchoolYear") int currentSchoolYear, @Param("teacherIds") List<Long> teacherIds);	
	
	List<BluePrintCriteriaDescription> getBluePrintCriteriaDescByGradeAndSub(@Param("contentAreaId") Long contentAreaId, 
			@Param("gradeCourseAbbrName") String gradeCourseAbbrName);
	
	List<BPCriteriaAndGroups> getBluePrintCriteriasByGradeAndSubAndCriteria(@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseAbbrName") String gradeCourseAbbrName, 
			@Param("criteria") Long criteria);
	
	List<ContentFrameworkDetail> getStudentITITestsForSubGradeAndCriteria(@Param("criteria") Long criteria, @Param("gradeCourseAbbrName") String gradeCourseAbbrName, 
			@Param("contentAreaId") Long contentAreaId, @Param("studentId") Long studentId, @Param("schoolYear") int schoolYear, @Param("operationalTestWindowID") Long operationalTestWindowID);
	
	List<StudentReportDTO> selectWritingStudentsByCriteria(Map<String, Object> criteria);

	List<StudentReportDTO> selectWritingResponses(Map<String, Object> params);

	// Case 44: Organization Extract
	List<OrganizationExtractDTO> getOrganizationsExtractByOrg(@Param("organizationId") Long organizationId,
			@Param("includeInactiveOrganizations") boolean includeInactiveOrganizations,
			@Param("typeLevelCode") String typeLevelCode);

	// Case 45: First Contact Survey Extract
	List<FCSDataExtractDTO> getFirstContactSurveyDetails(@Param("organizationId") Long organizationId,
			@Param("assessmentPrograms") List<Long> assessmentPrograms,
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("isTeacher") boolean isTeacher,
			@Param("userId")Long userId);

	List<FCSHeader> getDynamicFCSHeaders();

	List<FCSAnswer> getFCSAnswers(@Param("studentId") Long studentId);

	List<TestingReadinessExtractDTO> getStudentReadinessExtractDetails(@Param("orgIds") List<Long> allOrgIds,
			@Param("assessmentPrograms") List<Integer> assessmentPrograms,
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId);

	List<TestingReadinessEnrollSubjects> getTestRecordsForExtract(@Param("studentIds") Set<Long> studentIds, @Param("currentSchoolYear") Long currentSchoolYear);

	List<StudentExitExtractDTO> getDLMStudentExitExtractForGrf(@Param("orgId") Long orgId,
			@Param("currentSchoolYear") int year, @Param("assesmentProgram") String assesmentProgram, @Param("isStateHaveSpecificExitCode") Boolean isStateHaveSpecificExitCode);

	List<UploadScCodeFile> getDLMSpecialCircumstanceExtract(@Param("orgId") Long orgId,
			@Param("currentSchoolYear") int year,@Param("stateCode") String stateCode);

	List<UploadGrfFile> getDLMGeneralResearchExtract(@Param("stateId") Long stateId,
			@Param("districtId") Long districtId, @Param("schoolId") Long schoolId, @Param("subjectId") Long subject, @Param("gradeId") Long grade, @Param("year") int year,@Param("offset") int offset,@Param("limit") int limit);

	List<Organization> getOrganizationByTypeForGRF(@Param("stateId") Long stateId,@Param("districtId") Long districtId,
			@Param("orgTypeCode") String orgTypeCode,@Param("reportYear")  int reportYear);

	List<ContentArea> getContentAreasforGRF(@Param("stateId") Long stateId, @Param("districtId") Long districtId,
			@Param("schoolId") Long schoolId,@Param("reportYear") int reportYear);

	List<GradeCourse> getGradeCourseByGRF(@Param("stateId") Long stateId,@Param("districtId") Long districtId,
			@Param("schoolId") Long schoolId,@Param("contentAreaId")  Long contentAreaId, @Param("reportYear") int reportYear);

	List<UploadIncidentFile> getDLMIncidentExtract(@Param("stateId") Long orgId,@Param("year") int year,
			@Param("offset") int offset,@Param("limit") int limit);
	


	//case 50: ISMART_TEST_STATUS (ISMART/ISMART2 Test Administration Monitoring)
	List<ISMARTTestAdminExtractDTO> getISmartTestStatusRecords(@Param("organizationId") Long orgId, 
			@Param("isTeacher") Boolean isTeacher, 
			@Param("educatorId") Long educatorId, 
			@Param("currentSchoolYear") int currentSchoolYear, 
			@Param("contractOrgId") Long contractOrgId, 
			@Param("assessmentProgramIds")List<Long> assessmentProgramIds,
			@Param("contentAreas") List<String> ismartContentAreas);
	
	  List<KAPStudentScoreDTO> generateKAPStudentScoreCurrentExtractForDistrictUser(@Param("organizationIds") List<Long> orgIds, @Param("isDistict") Boolean isDistict,@Param("isSchool")Boolean isSchool, @Param("contentAreaId")List<Long> contentAreaIds, 
				@Param("schoolYears")List<Long> schoolYears, @Param("gradeAbbreviatedNames")List<String> gradeIds,@Param("currentSchoolYear")Long currentSchoolYear,@Param("currentReportYear")Long currentReportYear,@Param("offset") int offset,@Param("limit") int limit
				,@Param("stateId") Long stateId,@Param("dataExtractReportStudent") String dataExtractReportStudent,@Param("assessmentProgramId") Long assessmentProgramId);
		

		List<KAPStudentScoreDTO> generateKAPStudentScoreTestedExtractForDistrictUser(@Param("organizationIds")List<Long> orgIds,@Param("currentOrganizationId")Long currentOrganizationId,@Param("districtId")Long districtId,@Param("isState")boolean isState, @Param("isDistict") boolean isDistict,@Param("isSchool")boolean isSchool, @Param("contentAreaId")List<Long> contentAreaIds, 
				@Param("schoolYears")List<Long> schoolYears, @Param("gradeAbbreviatedNames")List<String> gradeIds, @Param("currentSchoolYear")Long currentSchoolYear,@Param("currentReportYear")Long currentReportYear,@Param("offset") int offset,@Param("limit") int limit
				,@Param("extractStudent") String extractStudent,@Param("stateId") Long stateId,@Param("assessmentProgramId") Long assessmentProgramId);
		
		List<KAPStudentScoreDTO> generateKAPStudentScoreSpecifiedExtractForDistrictUser(@Param("organizationIds")List<Long> orgIds,@Param("isState") Boolean isState, @Param("isDistict") Boolean isDistict,@Param("isSchool")Boolean isSchool, @Param("currentSchoolYear")Long currentSchoolYear,
				@Param("stateStudentIdentifier") String stateStudentIdentifier ,@Param("currentReportYear") Long currentReportYear,@Param("isCurrentEnrolled") boolean isCurrentEnrolled,@Param("assessmentProgramId") Long assessmentProgramId);
		
	List<PNPAbridgedExtractDTO> getActiveStudentsWithPNPInOrgPLTW(
			@Param("organizationId") Long orgId,
			@Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId,
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("assessmentPrograms") List<Long> assessmentPrograms,
			@Param("includeAllStudents") boolean includeAllStudents);
	
	List<KELPA2StudentScoreDTO> generateKELPA2StudentScoreCurrentExtractForDistrictUser(
			@Param("organizationIds") List<Long> orgIds, 
			@Param("isDistict") Boolean isDistict,
			@Param("isSchool")Boolean isSchool, 
			@Param("contentAreaId")List<Long> contentAreaIds, 
			@Param("schoolYears")List<Long> schoolYears, 
			@Param("gradeAbbreviatedNames")List<String> gradeIds,
			@Param("currentSchoolYear")Long currentSchoolYear,
			@Param("currentReportYear")Long currentReportYear,
			@Param("offset") int offset,
			@Param("limit") int limit,
			@Param("stateId") Long stateId,
			@Param("dataExtractReportStudent") String dataExtractReportStudent,
			@Param("assessmentProgramId") Long assessmentProgramId);
	
	Long getKAPCurrentStudentCount(@Param("organizationIds") List<Long> orgIds, 
			@Param("isDistict") Boolean isDistict,
			@Param("isSchool")Boolean isSchool,  
			@Param("currentSchoolYear")Long currentSchoolYear,
			@Param("stateId") Long stateId,
			@Param("assessmentProgramId") Long assessmentProgramId);
	
}
