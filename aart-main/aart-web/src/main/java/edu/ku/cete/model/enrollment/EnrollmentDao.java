package edu.ku.cete.model.enrollment;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.enrollment.AutoRegisteredStudentsDTO;
import edu.ku.cete.domain.enrollment.DLMStudentSurveyRosterDetailsDto;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentExample;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentSchoolRelationDataObject;
import edu.ku.cete.domain.student.StudentSchoolRelationInformation;
import edu.ku.cete.web.TECExtractDTO;

/** enrollment Dao. */
public interface EnrollmentDao {

    /**
     * Create a new enrollment.
     * @param toAdd object to add.
     * @return number of rows affected.
     */
    int add(Enrollment toAdd);
    /**
     * @param toAdd {@link Enrollment}
     * @return {@link Integer}
     */
    int update(Enrollment toAdd);
    /**
     * @param stateStudentIdentifier {@link String}
     * @return {@link Enrollment}
     */
    List<Enrollment> get(String stateStudentIdentifier);
    
    List<Enrollment> getBySsidAndState(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("stateId") Long stateId, @Param("currentSchoolYear") int currentSchoolYear);
    
    List<Enrollment> getEnrollmentForAuto(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("subject") String subject);
    
    /**
     * @param stateStudentIdentifier {@link String}
     * @return {@link Enrollment}
     */
    List<Enrollment> getInactiveEnrollment(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("stateId") Long stateId, @Param("currentSchoolYear") int currentSchoolYear);        
    /**
     * @param enrollment {@link EnrollmentExample}
     * @return {@link Enrollment}
     */
    List<Enrollment> getByCriteria(EnrollmentExample enrollment);
    
    /**
     * Returns the student, their enrolled school along with each roster they are part of.
     * 
     * i/p 1: selected organization (typically school), optional.Used as attendance school in search.
     * i/p 2: user organizations.Search for all students with in the users organization tree.  
     * i/p 3: educator id. Search for all rosters, for the given teacher.
     * i/p 4: hasViewAllRosterPermission. If true all rosters in the selected school(s) or users organization tree will be returned.
     * If false then 
     * @param limitCount 
     * @param testSessionId 
     * @param enrollment
     * @return
     */
    List<StudentRoster> getEnrollmentWithRoster(
    		@Param("userOrganizationId") Long userOrganizationId,
    		@Param("educatorId") Long educatorId,
    		@Param("hasViewAllPermission") boolean hasViewAllPermission,
    		@Param("sortByColumn") String sortByColumn,
    		@Param("sortType") String sortType,
    		@Param("offset") Integer offset,
    		@Param("limit") Integer limitCount,
    		@Param("studentRosterCriteriaMap") Map<String,String> studentRosterCriteriaMap,
    		@Param("testSessionId") Long testSessionId,
    		@Param("schoolOrgId") Long schoolOrgId,
    		@Param("contentAreaId") Long contentAreaId,
    		@Param("currentSchoolYear") int currentSchoolYear
    		 );
    
    
    
    List<StudentRoster> DLMStudentReport_Students_Column_List(
    		@Param("userOrganizationId") Long userOrganizationId,
    		@Param("educatorId") Long educatorId,
    		@Param("hasViewAllPermission") boolean hasViewAllPermission,
    		@Param("sortByColumn") String sortByColumn,
    		@Param("sortType") String sortType,
    		@Param("offset") Integer offset,
    		@Param("limit") Integer limitCount,
    		@Param("studentRosterCriteriaMap") Map<String,String> studentRosterCriteriaMap,
    		@Param("testSessionId") Long testSessionId,
    		@Param("schoolOrgId") Long schoolOrgId,
    		@Param("contentAreaId") Long contentAreaId,
    		@Param("currentSchoolYear") int currentSchoolYear
    		 );
    /**
	 * @return {@link Long}
	 */
	long lastid();
	
	/**
	 * For the given student school enrollments return the ones
	 * that are found.
	 * @param studentSchoolRelations
	 * @return
	 */
	List<StudentSchoolRelationDataObject> getByStudentSchoolRelation(@Param("studentSchoolRelations")
			List<? extends StudentSchoolRelationInformation> studentSchoolRelations, 
			@Param("UserOrganizationIds") Collection<Long> UserOrganizationIds);
	/**
	 * count enrollment for the given parameters.
	 * 
	 * @param userOrganizationId
	 * @param educatorId
	 * @param hasViewAllPermission
	 * @return
	 */
	int countEnrollmentWithRoster(
    		@Param("userOrganizationId") Long userOrganizationId,
    		@Param("educatorId") Long educatorId,
    		@Param("hasViewAllPermission") boolean hasViewAllPermission,
    		@Param("studentRosterCriteriaMap") Map<String,String> studentRosterCriteriaMap,
    		@Param("testSessionId") Long testSessionId,
    		@Param("schoolOrgId") Long schoolOrgId,
    		@Param("contentAreaId") Long contentAreaId,
    		@Param("currentSchoolYear") int currentSchoolYear);

	/**
	 * count enrollment for the given parameters if orgId's exceeds 50 in number.
	 * 
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param educatorId
	 * @param hasViewAllPermission
	 * @param studentRosterCriteriaMap
	 * @param testSessionId
	 * @param simplifiedLimit
	 * @return
	 */
	int simplifiedCountEnrollmentWithRoster(
    		@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
    		@Param("userOrganizationIds") List<Long> userOrganizationIds,
    		@Param("educatorId") Long educatorId,
    		@Param("hasViewAllPermission") boolean hasViewAllPermission,
    		@Param("studentRosterCriteriaMap") Map<String,String> studentRosterCriteriaMap,
    		@Param("testSessionId") Long testSessionId,
    		@Param("simplifiedLimit") Integer simplifiedLimit);
	
	/**
	 * @param toAdd
	 * @return
	 */
	Integer updateByStudentInfo(Enrollment toAdd);	
	
	Integer countRostersByTeacherId(@Param("educatorId") Long educatorId);
	
	/**
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param educatorId
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @param studentsCriteriaMap
	 * @param testSessionId
	 * @param assessmentProgramAbbr
	 * @param currentSchoolYear
	 * @return
	 */
	List<AutoRegisteredStudentsDTO> getAutoRegisteredTSStudents(
    		@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
    		@Param("userOrganizationIds") List<Long> userOrganizationIds,
    		@Param("educatorId") Long educatorId,
    		@Param("isTeacher") boolean isTeacher,
    		@Param("orderByClause") String orderByClause,
    		@Param("offset") Integer offset,
    		@Param("limit") Integer limitCount,
    		@Param("studentsCriteriaMap") Map<String,String> studentsCriteriaMap,
    		@Param("testSessionId") Long testSessionId,
    		@Param("assessmentProgramAbbr") String assessmentProgramAbbr,
    		@Param("currentSchoolYear") Long currentSchoolYear,
    		@Param("organizationId") Long organizationId);
	
	/**
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param educatorId
	 * @param studentsCriteriaMap
	 * @param testSessionId
	 * @return
	 */
	Integer countAutoRegisteredTSStudents(
    		@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
    		@Param("userOrganizationIds") List<Long> userOrganizationIds,
    		@Param("educatorId") Long educatorId,
    		@Param("isTeacher") boolean isTeacher,
    		@Param("studentsCriteriaMap") Map<String,String> studentsCriteriaMap,
    		@Param("testSessionId") Long testSessionId);
	/**
	 * 
	 * @param testTypeId
	 * @param subjectAreaId
	 * @param gradeCourseId
	 * @return
	 */
	List<Enrollment> findEnrollmentsForBatchRegistration(@Param("testTypeId") Long testTypeId,
			@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId, @Param("assessmentId") Long assessmentId, @Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("dlmOnly") boolean dlmOnly, @Param("offset") Integer offset,  @Param("pageSize") Integer pageSize,  @Param("isInterim") boolean isInterim,
			@Param("jobLastSubmissionDate") Date jobLastSubmissionDate);
	
	List<Enrollment> findEnrollmentsForPredictiveBatchRegistration(Map<String, Object> params);
	
	List<Enrollment> findMultiAssignEnrollments(@Param("contentAreaId") Long contentAreaId, @Param("contentAreaAbbreviatedName") String contentAreaAbbreviatedName,
			@Param("testEnrollmentMethod") String testEnrollmentMethod, @Param("operationalTestWindowId") Long operationalTestWindowId,
			@Param("contractingOrgId") Long contractingOrgId, @Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("multiAssignLimit") Integer multiAssignLimit, @Param("isInterim") boolean isInterim,
			@Param("offset") Integer offset,  @Param("pageSize") Integer pageSize);
	
	List<Long> findNoEnrollmentOrganizations(@Param("testTypeId") Long testTypeId,
			@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId, @Param("assessmentId") Long assessmentId, @Param("currentSchoolYear") Long currentSchoolYear);
	
	List<Enrollment> findEnrollmentsForBatchRegistrationKSBreakDay(@Param("testTypeId") Long testTypeId,
			@Param("subjectAreaId") Long subjectAreaId, @Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId);
	
	/**
 	 * @param organizationId
 	 * @return
 	 */
	 List<Enrollment> getStudentsByOrgId(@Param("studentsCriteriaMap") Map<String,Object> criteria, @Param("currentSchoolYear") int currentSchoolYear);
    
 	/**
 	 * @param organizationId
 	 * @return
 	 */
 	Integer countStudentsByOrgId(@Param("studentsCriteriaMap") Map<String,Object> criteria, @Param("currentSchoolYear") int currentSchoolYear);

	/**
 	 * @param organizationId
 	 * @return
 	 */
	 List<Enrollment> getCurrentYearStudentsByOrgId(@Param("organizationId") Long organizationId, @Param("orderByClause") String orderByClause, 
 			@Param("offset") Integer offset, @Param("limitCount") Integer limitCount);
    
 	/**
 	 * @param organizationId
 	 * @return
 	 */
 	Integer countCurrentYearStudentsByOrgId(@Param("organizationId") Long organizationId);

 	
 	/**
 	 */
 	List<Enrollment> getStudentEnrollmentsByRosterId(Map<String,Object> criteria);
 	/**
 	 */
 	Integer countStudentEnrollmentsByRosterId(Map<String,Object> criteria);
 	
 	Enrollment getByEnrollmentId(@Param("enrollmentId") Long enrollmentId);
 	
 	List<String> getSubjectAreaByEnrollment(@Param("enrollmentId") Long enrollmentId);
 	
 	List<String> getStudentNamesEnrolledInTestSession(Long testSessionId);
 	
 	Integer getContractingOrgSchoolYear(@Param("organizationId") Long organizationId);
 	
 	Enrollment getBySSIDYearAndSchool(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("schoolYear") Integer schoolYear, @Param("attendanceSchoolProgramIdentifier") String attendanceSchoolProgramIdentifier, @Param("activeFlag") boolean activeFlag);
 	int updateExitInfo(@Param("exitWithdrawalDate") Date exitWithdrawalDate, @Param("exitWithdrawalType") int exitWithdrawalType, @Param("id") Long id, @Param("userId") Long userId, @Param("sourceType") String sourceType);
 	Enrollment getById(@Param("id") Long id);
 	Enrollment getAttendanceSchoolAndDistrictByEnrollmentId(@Param("id") Long enrollmentId);
 	List<Enrollment> allEnrollmentsWithSubjectDetailsForStudent(@Param("studentId") Long studentId, @Param("currentSchoolYear") Long currentSchoolYear, @Param("subjectId") Long subjectId);
 	
 	Integer adjustStudentRoster(@Param("enrollmentId") Long enrollmentId, @Param("rosterId") Long rosterId,
 			@Param("sourceCode") String sourceCode,@Param("modifiedUser") Long modifiedUser,@Param("modifiedDate") Date modifiedDate);
 	
 	List<Enrollment> getCurrentEnrollmentsByStudentId(@Param("studentId") Long studentId, @Param("organizationId") Long orgId,
 			@Param("currentSchoolYear") int currentSchoolYear, @Param("enrlActiveFlag") boolean enrollmentActiveflag);
 	
 	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get all students per roster id, these will be populated on roster selection.
	 */
 	List<Student> getStudentsByRosterId(@Param("rosterId") Long rosterId);
 	
 	List<TECExtractDTO> getTECExtractByOrg(@Param("organizationId") Long orgId, @Param("isTeacher") Boolean isTeacher, @Param("educatorId") Long educatorId, @Param("currentSchoolYear") int currentSchoolYear, @Param("assessmentPrograms") List<Long> assessmentPrograms);

 	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15563 : Test Management - Display Test Info pdf for student
     * Added logic to receive the test resources/media i.e. pdf files associated to the test for selected students.
	 */
 	List<StudentTestResourceInfo> selectResourceByStudentIdTestSessionId(@Param("testSessionId") Long testSessionId, @Param("studentIds") List<Long> studentIds);
 	
 	Enrollment getEnrollmentByStudentId(@Param("studentId") Long studentId, @Param("contractingOrganizationId") Long contractingOrganizationId, 
 			@Param("currentSchoolYear") Long currentSchoolYear, @Param("contentAreaId") Long contentAreaId );
 	
 	List<Enrollment> getEnrollmentsByStudentId(@Param("studentId") Long studentId, 
 			@Param("currentSchoolYear") Long currentSchoolYear);
 	
	int countEnrollmentsTestTypeSubjectArea(@Param("enrollmentId") Long id, @Param("assessmentId") Long assessmentId, @Param("subjectAreaId") Long subjectAreaId,
			@Param("testTypeCode") String testTypeCode);
	
	void updateOnStudentEdit(@Param("studentId") Long id,@Param("currentGrade")  Long currentGradeLevelEnrollment,
			@Param("currentSchoolYear") Integer currentSchoolYearEnrollment, @Param("modifiedUserId") Long modifiedUserId);
	
	void updateOnStudentEnrollmentEdit(@Param("studentId") Long id,
			@Param("localStudentIdentifier")  String localStudentIdentifier,
			@Param("stateEntryDateEnrollment") Date stateEntryDateEnrollment,
			@Param("districtEntryDateEnrollment") Date districtEntryDateEnrollment,
			@Param("schoolEntryDateEnrollment") Date schoolEntryDateEnrollment,
			@Param("giftedStudentEnrollment") Boolean giftedStudentEnrollment,
			@Param("currentSchoolYear") Integer currentSchoolYearEnrollment,
			@Param("modifiedUserId") Long modifiedUserId);
	
	List<Enrollment> getBySsidAndOrgId(@Param("stateStudentIdentifier") String stateStudentIdentifier,@Param("selectedOrgId")
			long selectedOrgId,@Param("currentSchoolYear") int currentSchoolYear);

	  List<StudentRoster> getEnrollmentWithRoster(
	    		@Param("userOrganizationId") Long userOrganizationId,
	    		@Param("educatorId") Long educatorId,
	    		@Param("hasViewAllPermission") boolean hasViewAllPermission,
	    		@Param("sortByColumn") String sortByColumn,
	    		@Param("sortType") String sortType,
	    		@Param("offset") Integer offset,
	    		@Param("limit") Integer limitCount,
	    		@Param("studentRosterCriteriaMap") Map<String,String> studentRosterCriteriaMap,
	    		@Param("testSessionId") Long testSessionId,
	    		@Param("schoolOrgId") Long schoolOrgId,
	    		@Param("contentAreaId") Long contentAreaId,
	    		@Param("currentSchoolYear") int currentSchoolYear,
	    		@Param("assessmentProgramId") Long assessmentProgramId
	    		 );
	  
	  int countEnrollmentWithRoster(
	    		@Param("userOrganizationId") Long userOrganizationId,
	    		@Param("educatorId") Long educatorId,
	    		@Param("hasViewAllPermission") boolean hasViewAllPermission,
	    		@Param("studentRosterCriteriaMap") Map<String,String> studentRosterCriteriaMap,
	    		@Param("testSessionId") Long testSessionId,
	    		@Param("schoolOrgId") Long schoolOrgId,
	    		@Param("contentAreaId") Long contentAreaId,
	    		@Param("currentSchoolYear") int currentSchoolYear,
	    		@Param("assessmentProgramId") Long assessmentProgramId);
	Enrollment getInactiveEnrollmentByScoolId(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("schoolId") Long schoolId, @Param("currentSchoolYear") int currentSchoolYear);
	
	List<Enrollment> findByStudentTestTypeContentAreaGradeCourseAssessmentInOrg(
			@Param("studentId") Long studentId, @Param("testTypeId") Long testTypeId, @Param("contentAreaId") Long contentAreaId,
			@Param("gradeCourseId") Long gradeCourseId, @Param("contractingOrgId") Long contractingOrgId, @Param("assessmentId") Long assessmentId,
			@Param("currentSchoolYear") Long currentSchoolYear);
	
	List<Enrollment> findEnrollmentsForKapAdaptiveRegistration(@Param("testTypeId") Long testTypeId,
			@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId, @Param("assessmentId") Long assessmentId, @Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("dlmOnly") boolean dlmOnly, @Param("offset") Integer offset,  @Param("pageSize") Integer pageSize, @Param("isInterim") boolean isInterim);
	
	List<StudentRoster> getDlmFixedAssignEnrollments(@Param("contractingOrgId") Long contractingOrgId,@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("currentSchoolYear")Long currentSchoolYear,@Param("contentAreaId")Long contentAreaId, @Param("courseId") Long courseId,
			@Param("gradeAbbrName")String gradeAbbrName, @Param("offset")Integer offset, @Param("pageSize")Integer pageSize);
	
	List<Enrollment> findCPASSEnrollmentsForBatchRegistration(@Param("testTypeId") Long testTypeId,
			@Param("contractingOrgIds") List<Long> contractingOrgIds, @Param("assessmentId") Long assessmentId, @Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("dlmOnly") boolean dlmOnly, @Param("offset") Integer offset,  @Param("pageSize") Integer pageSize,  @Param("isInterim") boolean isInterim);
	
	List<Enrollment> findOtherHighStakesEnrollments(@Param("studentId") Long studentId, @Param("enrollmentIds") List<Long> enrollmentsToExclude,
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("assessmentProgramCode") String assessmentProgramCode,
			@Param("contentAreaCode") String contentAreaCode, @Param("contractingOrgId") Long contractingOrgId);
	
	List<Enrollment> getReportEnrollments(@Param("studentId") Long studentId, @Param("contractOrgId") Long contractOrgId, 
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("rawScaleExternalTestIds") List<Long> rawScaleExternalTestIds,
			@Param("testsStatusIds") List<Long> testsStatusIds);
	
	/*
	 * 	Added for US18980 :Find Student 
	 */
	List<FindEnrollments> findStudentAvilbility(@Param("studentStateId") String studentStateId,
			@Param("studentFirstName") String studentFirstName,
			@Param("studentlastName") String studentlastName,
			@Param("orgId") Long orgId, 
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("offset") Integer offset,
			@Param("limitCount") Integer limitcount,
			@Param("enrollmentRecordCriteriaMap") Map<String, String> enrollmentRecordCriteriaMap,
			@Param("sortByColumn") String sortByColumn, 
			@Param("sortType")String sortType);
	Integer findStudentAvilbilityCount(@Param("studentStateId") String studentStateId,
			@Param("studentFirstName") String studentFirstName,
			@Param("studentlastName") String studentlastName,
			@Param("orgId") Long orgId, 
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("enrollmentRecordCriteriaMap") Map<String, String> enrollmentRecordCriteriaMap);
	List<Enrollment> getEnrollmentByStudentIdAttendanceSchoolIdandCurrentSchoolYear(
			@Param("studentId") Long studentId, 
			@Param("orgSchoolId") Long orgSchoolId, 
			@Param("currentSchoolYear") Integer currentSchoolYear);
	
	List<FindEnrollments> findStudentEnrollment(@Param("studentStateId") String studentStateId,
			@Param("stateId") Long stateId,
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("educatorId") Long educatorId,
			@Param("isTeacher") boolean isTeacher );
	Enrollment getStudentEnrollmentBySsidAndState(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("stateId") Long stateId, @Param("currentSchoolYear") Long currentSchoolYear);
	
	
	List<Enrollment> getEnrollmentsByStudentIdForTasc(@Param("studentId") Long studentId, @Param("currentSchoolYear") Long currentSchoolYear);
	
	List<Enrollment> findSubjectEnrollmentsByStudentId(
			@Param("studentId") Long studentId, 
			@Param("currentSchoolYear") Integer currentSchoolYear, 
			@Param("stateId") Long stateId);
	
	List<Enrollment> getEnrollmentsByAypAndAttendanceSchool(
			@Param("stateStudentIdentifier") String studentId,
			@Param("stateId") Long stateId,
			@Param("currentSchoolYear") Integer currentSchoolYear, 
			@Param("aypSchoolId") Long aypSchoolId,
			@Param("attendanceSchoolId") Long attendanceSchoolId);
	
	List<StudentRoster> getEnrollmentWithRosterForAssessmentPrograms(
			@Param("studentId") Long studentId, 
			@Param("schoolYear") Integer schoolYear, 
			@Param("assessmentProgramIds") List<Long> assessmentProgramIds
    		 );
	
 	int inactivateOldEnrollmentForNewEnrollmentUpload(@Param("id") Long id, @Param("userId") Long userId, @Param("sourceType") String sourceType);
 	
	List<Enrollment> getEnrollementsByOrg(@Param("sourceSchoolId") Long sourceSchoolId, @Param("currentSchoolYear") Long currentSchoolYear);
	
	void transferEnrollment(Enrollment enrollment);
	
	void disableEnrollment(Enrollment enrollment);
	
	Long getCountByOrganizationId(@Param("organizationId") Long organizationId, @Param("schoolYear") Long schoolYear);

	List<Enrollment> findEnrollmentsForKELPABatchRegistration(@Param("testTypeId") Long testTypeId,
			@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId, @Param("assessmentId") Long assessmentId, @Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("offset") Integer offset,  @Param("pageSize") Integer pageSize);
	
	List<Long> findNoKELPAEnrollmentOrganizations(@Param("testTypeId") Long testTypeId,
			@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId, @Param("assessmentId") Long assessmentId, @Param("currentSchoolYear") Long currentSchoolYear);
	List<Enrollment> findEnrollmentForStudentEdit(@Param("studentId") Long studentId, @Param("currentSchoolYear") Long currentSchoolYear);
	
	Enrollment findEnrollmentForStudentActivate(@Param("enrollmentId") Long enrollmentId);
	
	int getBySsidAndUserOrgId(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("organizationId") Long orgId, @Param("currentSchoolYear") int currentSchoolYear, @Param("orgType") String orgType);
	
	Long getDeactivateCountByOrganizationId(@Param("organizationId") Long organizationId, @Param("schoolYear") Long schoolYear);
	
	List<DLMStudentSurveyRosterDetailsDto> getDLMStudentsForResearchSurvey(@Param("contractingOrganizationId")Long contractingOrganizationId, @Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("contentAreaIds") List<Long> contentAreaIds, 
			@Param("operationalWindowId")Long operationalWindowId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
	Enrollment getEnrollmentById(@Param("id") Long id);
	List<Enrollment> getStudentEnrollmentWithoutAssessmentPrograms(@Param("organizationId") Long organizationId, @Param("schoolYear") Long schoolYear);
	List<Enrollment> getEnrollmentsByStudentIdInDistrictBySchool(@Param("studentId")Long studentId, @Param("currentSchoolYear")Long currentSchoolYear,@Param("schoolId") Long schoolId);
	
	List<Enrollment> getRosteredEnrollmentsByStudentIdSubjectSchYr(@Param("studentId") Long studentId, 
			@Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("subjectId") Long subjectId);
	
	Enrollment getByStateStudentIDSchoolYearAndSchool(@Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("schoolYear") Integer schoolYear, @Param("attendanceSchoolProgramIdentifier") String attendanceSchoolProgramIdentifier, @Param("activeFlag") boolean activeFlag);
	
	List<Enrollment> findEnrollmentsForKAPAdaptiveBatchRegistration(@Param("testTypeId") Long testTypeId,
			@Param("contentAreaId") Long contentAreaId, 
			@Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId, 
			@Param("assessmentId") Long assessmentId,
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("offset") Integer offset,  
			@Param("pageSize") Integer pageSize,  
			@Param("isInterim") boolean isInterim,
			@Param("jobLastSubmissionDate") Date jobLastSubmissionDate,
			@Param("assessmentProgramId") Long assessmentProgramId);
	
	List<Enrollment> findEnrollmentsForKAPAdaptiveStage2Assignment(@Param("contentAreaId") Long contentAreaId, 
			@Param("gradeCourseId") Long gradeCourseId,
			@Param("contractingOrgId") Long contractingOrgId, 			
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("assessmentProgramCode") String assessmentProgramCode,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("operationalTestWindowId") Long operationalTestWindowId,
			@Param("testCompletedStatusId") Long testCompletedStatusId,
			@Param("priorStageId") Long stage1Id,
			@Param("jobLastSubmissionDate") Date jobLastSubmissionDate,
			@Param("offset") Integer offset,  
			@Param("pageSize") Integer pageSize);
	
	Enrollment findStudentBasedOnStateStudentIdentifier(
			@Param("stateStudentIdentifier") String stateStudentIdentifier, 
			@Param("organizationId") Long organizationId);
	
	int findEnrollmentBySSIDAndOrgId(
			@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("stateId") Long stateId,
			@Param("currentSchoolYear") Long currentSchoolYear, 
			@Param("organizationId") Long organizationId);
	
    int updateOnActivate (Enrollment toUpdate);
    
    List<StudentRoster> getEnrollmentsForISmartBatchAuto(@Param("contractingOrgId") Long contractingOrgId,
    		@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("currentSchoolYear")Long currentSchoolYear,
			@Param("contentAreaId")Long contentAreaId, 
			@Param("gradeAbbrName")String gradeAbbrName, 
			@Param("operationalTestWindowId") Long operationalTestWindowId,
			@Param("offset")Integer offset, 
			@Param("pageSize")Integer pageSize);
			
    int deactivateByStudentId(@Param("studentId") Long studentId, @Param("userId") Long userId);
    
    List<Enrollment> getEnrollmentsForPLTWBatchRegistration(Map<String, Object> params);
	
    int updateGradeLevel(Enrollment enrollment);
    
}

