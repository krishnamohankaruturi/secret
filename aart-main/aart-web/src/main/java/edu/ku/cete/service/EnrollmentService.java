/**
 * 
 */
package edu.ku.cete.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.AutoRegisteredStudentsDTO;
import edu.ku.cete.domain.enrollment.DLMStudentSurveyRosterDetailsDto;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentExample;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.enrollment.TestRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentSchoolRelationInformation;
import edu.ku.cete.domain.student.TransferStudentDTO;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.ksde.kids.result.KidRecord;

/**
 * @author m802r921
 *
 */
public interface EnrollmentService {
    /**
     * @param toAdd object to add.
     * @return {@link Enrollment}
     */
    //TODO uncomment this. @PreAuthorize(value = "hasRole('PERM_ENRL_CREATE')")
    Enrollment add(Enrollment toAdd);
    
    void addNewEnrollmentDomainAuditHistory(Enrollment newEnrollment);
    /**
     * @param toAdd {@link Enrollment}
     * @return {@link Enrollment}
     */
    //@PreAuthorize(value = "hasRole('PERM_ENRL_CREATE')")
    //Enrollment cascadeAdd(Enrollment toAdd);
    /**
     * @param toUpdate {@link Enrollment}
     * @return {@link Enrollment}
     */
    //@PreAuthorize(value = "hasRole('PERM_ENRL_MODIFY')")
    Enrollment update(Enrollment toUpdate);
    /**
     * @param stateStudentIdentifier {@link String}
     * @return {@link Enrollment}
     */
    //@PreAuthorize(value = "hasRole('PERM_ENRL_VIEW')")
    List<Enrollment> get(String stateStudentIdentifier);
	/**
	 * @param testRecord {@link TestRecord}
	 * @return {@link TestRecord}
	 */
	TestRecord cascadeAddOrUpdate(TestRecord testRecord, ContractingOrganizationTree contractingOrganizationTree);
	/**
	 * @param enrollmentRecord {@link EnrollmentRecord}
	 * @return {@link EnrollmentRecord}
	 */
	EnrollmentRecord cascadeAddOrUpdate(EnrollmentRecord enrollmentRecord, ContractingOrganizationTree contractingOrganizationTree, Long orgId, int currentSchoolYear);
	/**
	 * @param toAddOrUpdate {@link Enrollment}
	 * @return {@link Enrollment}
	 */
	//Enrollment cascadeAddOrUpdate(Enrollment toAddOrUpdate);
	/**
	 * @param newEnrollment {@link Enrollment}
	 * @return {@link Enrollment}
	 */
	Enrollment addOrUpdate(Enrollment newEnrollment);
    /**
     * @param enrollment {@link EnrollmentExample}
     * @return {@link List}
     */
	//TODO uncomment this. @PreAuthorize(value = "hasRole('PERM_ENRL_VIEW')")
    List<Enrollment> getByCriteria(EnrollmentExample enrollment);

	/**
	 * @param kid {@link KidRecord}
	 * @param user {@link User}
	 * @param isTecRecord {@link boolean}
	 * @return {@link KidRecord}
	 */
	KidRecord cascadeAddOrUpdate(KidRecord kid, ContractingOrganizationTree contractingOrganizationTree, User user, boolean isTecRecord);
	
	/**
	 * Returns the student school enrollments marking the ones that are not present.
	 * If present student id is set.
	 * TODO set organization id also when needed.
	 * @param studentSchoolRelations
	 * @return
	 */
	List<? extends StudentSchoolRelationInformation> verifyEnrollments(
			List<? extends StudentSchoolRelationInformation> studentSchoolRelations, Collection<Long> UserOrganizationIds);

	/**
	 * Returns the student, their enrolled school along with each roster they are part of.
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @param studentRosterCriteriaMap
	 * @return
	 */
	List<StudentRoster> getEnrollmentWithRoster(
			UserDetailImpl userDetails, boolean hasViewAllPermission, String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear);
	
	List<StudentRoster> getEnrollmentsForDLMStudents(
			UserDetailImpl userDetails, boolean hasViewAllPermission, String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear);
	
	/**
	 * Count enrollment for the given parameters.
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param studentRosterCriteriaMap
	 * @return
	 */
	int countEnrollmentWithRoster(
			UserDetailImpl userDetails, boolean hasViewAllPermission,	Map<String,String> studentRosterCriteriaMap,
			Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear);
	
	/**
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @param studentsCriteriaMap
	 * @param testSessionId
	 * @return
	 */
	List<AutoRegisteredStudentsDTO> getAutoRegisteredTSStudents(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			UserDetailImpl userDetails, String orderByClause, Integer offset,
    		Integer limitCount,	Map<String,String> studentsCriteriaMap,
    		Long testSessionId, String assessmentProgramAbbr, Long organizationId);
	
	/**
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param studentsCriteriaMap
	 * @param testSessionId
	 * @return
	 */
	Integer countAutoRegisteredTSStudents(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			UserDetailImpl userDetails,	Map<String,String> studentsCriteriaMap,
			Long testSessionId);
	
	/**
	 * 
	 * @param testTypeId
	 * @param subjectAreaId
	 * @param gradeCourseId
	 * @return
	 */
	List<Enrollment> getEnrollmentsForBatchRegistration(Long testTypeId, Long subjectAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize);
	
	List<Enrollment> getEnrollmentsForBatchRegistrationOnlyDLM(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize);
	
	List<Enrollment> findEnrollmentsForBatchRegistrationKSBreakDay(Long testTypeId, Long subjectAreaId, Long gradeCourseId, Long contractingOrgId);
	
	List<Long> getNoEnrollmentOrganizations(Long testTypeId, Long subjectAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear);

	/**
	 * @param organizationId
	 * @return
	 */
	List<Enrollment> getStudentsByOrgId(Long organizationId, String orderByClause, 
			Integer offset, Integer limitCount, int currentSchoolYear);
	
	List<Enrollment> getStudentsByOrgId(Map<String,Object> criteria, String orderByColumn, String order, 
			Integer offset, Integer limitCount, int currentSchoolYear);
	/**
	 * @param organizationId
	 * @return
	 */
	List<Enrollment> getCurrentYearStudentsByOrgId(Long organizationId, String orderByClause, 
			Integer offset, Integer limitCount);
	
	/**
	 * @param criteria
	 * @return
	 */
	Integer countStudentsByOrgId(Map<String,Object> criteria, int currentSchoolYear);
	
	/**
	 * @param organizationId
	 * @return
	 */
	Integer countCurrentYearStudentsByOrgId(Long organizationId);
	
	/**
	 */
	List<Enrollment> getStudentEnrollmentsByRosterId(Map<String,Object> criteria);

	 /**

	 */
	Integer countStudentEnrollmentsByRosterId(Map<String,Object> criteria);
	
	Enrollment getByEnrollmentId(Long enrollmentId);
	Enrollment getAttendanceSchoolAndDistrictByEnrollmentId(Long enrollmentId);
	List<Enrollment> allEnrollmentsWithSubjectDetailsForStudent(Long studentId, Long currentSchoolYear, Long subjectId);
	
	List<Enrollment> addIfNotPresentSTCO(Enrollment newEnrollment);
	List<Enrollment> addIfNotPresentForRosterUpload(Enrollment newEnrollment);
	
	Boolean isTeacher(Long educatorId);
	
	List<String> getStudentNamesEnrolledInTestSession(Long testSessionId);
	
	Integer getContractingOrgSchoolYear(Long organizationId);
	
	//TecRecord updateExitInfo(TecRecord tecRecord, ContractingOrganizationTree contractingOrganizationTree, Long userId);
	TecRecord cascadeAddOrUpdate(TecRecord tecRecord,
			ContractingOrganizationTree contractingOrganizationTree, User user);
	Enrollment getById(Long id);
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get all students per roster id, these will be populated on roster selection.
	 */
	List<Student> getStudentsByRosterId(Long rosterId);
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15563 : Test Management - Display Test Info pdf for test
     * Added logic to receive the test resources/media i.e. pdf files associated to the test.
	 */
	Set<StudentTestResourceInfo> getResourceByStudentIdTestSessionId(Long testSessionId, Long currentSchoolYear);
	
	List<Enrollment> getMultiAssignEnrollments(Long contentAreaId, String contentAreaCode, String testEnrollmentMethod, Long operationalTestWindowId, 
			Long schoolId, Long currentSchoolYear, Integer multiAssignLimit, boolean isInterim, Integer offset, Integer pageSize);
	
	Enrollment getEnrollmentByStudentId(Long studentId, Long contractingOrganizationId, Long currentSchoolYear, Long contentAreaId);
	
	List<Enrollment> getEnrollmentsByStudentId(Long studentId, Long currentSchoolYear);
	
	List<Enrollment> getEnrollmentBySSIDAndStateId(String ssid, Long stateId,
			int currentSchoolYear);
	
	int countEnrollmentsTestTypeSubjectArea(Long id, Long assessmentId, Long subjectAreaId, String testTypeCode);
	
	Restriction getEnrollmentRestriction(UserDetailImpl userDetails) ;
	/**
	 * Added for US16352 upload TEC Queue  
	 */
	KidRecord convertToKidRecord(TecRecord tecRecord, ContractingOrganizationTree contractingOrganizationTree);
	TecRecord convertFromKidRecord(KidRecord outKidRecord);
	boolean isDLMKid(KidRecord kidRecord);
	void processClearTestTypes(KidRecord kidRecord, Enrollment enrollment, User user,
    		Map<String, TestType> testTypesMap, Map<String, SubjectArea> subjectAreaMap, boolean isTecRecord);
	void addEnrollmentTestTypes(KidRecord kidRecord, Enrollment enrollment, User user,
    		Map<String, TestType> testTypesMap, Map<String, SubjectArea> subjectAreaMap);
	
	void undoExitRecords(User user, Enrollment e) ;
	void processExitRecord(KidRecord kidRecord, User user, Enrollment e) ;
	void processExit(String attendanceSchoolDisplayIdentifiers, ContractingOrganizationTree contractingOrganizationTree,String stateStudentIdentifier, String exitReason, Date exitDate, int currentSchoolYear, User user, long selectdOrgId);
	
	List<StudentRoster> getEnrollmentWithRosterAssessmentProgram(
			UserDetailImpl userDetails, boolean hasViewAllPermission, String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear, Long assessmentProgramId);
	int countEnrollmentWithRosterAssessmentProgram(
			UserDetailImpl userDetails, boolean hasViewAllPermission,	Map<String,String> studentRosterCriteriaMap,
			Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear, Long assessmentProgramId);
	
	void transferStudents(List<TransferStudentDTO> students, User user, Long accountabilityDistrictId, String accountabilityDistrictIdentifier);
	
	List<Enrollment> getEnrollmentsForStudent(Long studentId, Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long contractingOrgId, Long assessmentId, Long currentSchoolYear); 
	
	List<Enrollment> getEnrollmentsForKapAdaptiveRegistration(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize, Boolean isInterim, Date jobLastSubmissionDate, Long assessmentProgramId);
	
	List<Enrollment> getEnrollmentsForKapPredictiveRegistration(Map<String, Object> params);
	
	List<StudentRoster> getDlmFixedAssignEnrollments(Long contractingOrgId, Long assessmentProgramId,
			Long currentSchoolYear, Long contentAreaId, Long courseId, String gradeAbbrName, Integer offset, Integer pageSize);
	
	void addGradeChangeEventDomainAuditHistory(Enrollment newEnrollment, Long oldGradeId);
	
	List<Enrollment> getCPASSEnrollmentsForBatchRegistration(Long testTypeId,
			List<Long> orgIds, Long assessmentId,
			Long schoolYear, Integer offset, Integer pageSize);
	
	List<Enrollment> findOtherHighStakeEnrollments(Long studentId, List<Long> enrollmentsToExclude,
			Long currentSchoolYear, String assessmentProgramCode,
			String contentAreaCode, Long contractingOrgId);
	
	List<Enrollment> findReportEnrollments(Long studentId, Long contractOrgId, 
			Long currentSchoolYear, List<Long> rawScaleExternalTestIds,
			List<Long> testsStatusIds);
	
	/*
	 * 	Added for US18980 :Find Student 
	 */
	List<FindEnrollments> findStudentAvilbility(String studentStateId,String studentFirstName,String studentlastName,Long orgId,Integer currentSchoolYear,Integer offset,Integer limitcount, Map<String,String> studentInformationRecordCriteriaMap, String sortByColumn, String sortType);
	Integer findStudentAvilbilityCount(String studentStateId,String studentFirstName,String studentlastName,Long orgId,Integer currentSchoolYear,Map<String,String> studentInformationRecordCriteriaMap);
	String reActivateStudent(Long enrollmentId, Long orgDistrictId,Long orgSchoolId, Long gradeId, Long studentId,Long userAssessmentProgramId,Long restrictionId, Enrollment enrollment);
	List<FindEnrollments> findStudentEnrollment (String studentStateId,Long stateId,Integer currentSchoolYear,Long educatorId,Boolean isteacher);
	List<Enrollment> getEnrollmentsByStudentIdForTasc(Long studentId, Long currentSchoolYear);
	
	int addEnrollmentTestTypeSubjectArea(Enrollment enrollment, Long testTypeId, Long subjectAreaId);
	
	List<StudentRoster> getEnrollmentWithRosterForAssessmentPrograms(Long studentId, Integer currentSchoolYear, List<Long> assessmentProgramIds);

	List <Enrollment> getEnrollementsByOrg(Long sourceSchoolId , Long currentSchoolYear);
	
	void transferEnrollment(Enrollment enrollment, Long destinationSchool);
	
	void disableEnrollment(Enrollment enrollment);
	
	Long getCountByOrganizationId(Long organizationId, Long schoolYear);
	
	List<Enrollment> getEnrollmentsForKELPABatchRegistration(Long testTypeId, Long subjectAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize);
	List<Enrollment> getCurrentEnrollmentsByStudentId(Long studentId, Long orgId, int currentSchoolYear, boolean enrlActiveFlag);
	List<Long> getNoKELPAEnrollmentOrganizations(Long testTypeId, Long subjectAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear);
	List<Enrollment> findEnrollmentForStudentEdit(Long studentId, Long currentSchoolYear);
	Enrollment findEnrollmentForStudentActivate(Long enrollmentId);
	int getEnrollmentCountBySsidOrgId(String stateStudentIdentifier, Long orgId, int currentSchoolYear, String orgType);
	Long getDeactivateCountByOrganizationId(Long organizationId, Long schoolYear);
	List<DLMStudentSurveyRosterDetailsDto> getDLMStudentsForResearchSurvey(Organization contractingOrganization, List<ContentArea> contentAreasToLookUp, Long operationalWindowId, int offset, int pageSize);
	List<Enrollment> getStudentEnrollmentWithoutAssessmentPrograms(
			Long organizationId, Long schoolYear);
	List<Enrollment> getEnrollmentsByStudentIdInDistrictBySchool(Long studentId, Long currentSchoolYear, Long schoolId);
	
	List<Enrollment> getRosteredEnrollmentsByStudentIdSubjectSchYr(Long studentId, Long currentSchoolYear, Long subjectId);
	
	List<Enrollment> findEnrollmentsForKAPAdaptiveStage2Assignment(Long contentAreaId, Long gradeCourseId, Long contractingOrgId, Long currentSchoolYear, String assessmentProgramCode,
			Long assessmentProgramId, Long operationalTestWindowId, Long testCompletedStatusId, Long stage1Id, Date jobLastSubmissionDate, Integer offset, Integer pageSize);
	
	Enrollment findStudentBasedOnStateStudentIdentifier(String stateStudentIdentifier, Long organizationId);
	
	String findStateStudentIdentifierStatus(Enrollment enrollment, UserDetailImpl user, Long editStudentAuthorityId);
	
	int findEnrollmentBySSIDAndOrgId(String stateStudentIdentifier, Long assessmentProgramId, Long stateId,
			Long currentSchoolYear, Long organizationId);
	
	List<StudentRoster> getEnrollmentsForISmartBatchAuto(Long contractingOrgId, Long assessmentProgramId,
			Long currentSchoolYear, Long contentAreaId, String gradeAbbrName, Long operationalTestWindowId, Integer offset, Integer pageSize);
	
	int deactivateByStudentId(Long studentId, Long userId);
	
	List<Enrollment> getEnrollmentsForPLTWBatchRegistration(Map<String, Object> params);

	Enrollment updateGradeLevel(Enrollment enrollment);
	
 }
