package edu.ku.cete.model.enrollment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.enrollment.EnrollmentsOrganizationInfo;
import edu.ku.cete.domain.enrollment.StudentTestSessionDto;
import edu.ku.cete.domain.report.ActScoringDescription;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.KELPAReport;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.student.StudentExitCodesDTO;
import edu.ku.cete.domain.student.StudentInformationRecord;
import edu.ku.cete.domain.student.StudentJson;
import edu.ku.cete.domain.student.StudentRosterITIRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.web.IAPStudentTestStatusDTO;
import edu.ku.cete.web.ViewStudentDTO;

/** Student Dao. */
/**
 * @author mahesh
 *
 */
public interface StudentDao {

	/**
	 * Create a new student.
	 * 
	 * @param toAdd
	 *            object to add.
	 * @return number of rows affected.
	 */
	int add(Student toAdd);

	/**
	 * @param id
	 *            Id of the Student.
	 * @return number of rows affected.
	 */
	int delete(@Param("id") long id);

	/**
	 * Get student by id.
	 *
	 * @param studentId
	 *            Id of the student.
	 * @return {@link Student}
	 */
	Student findById(@Param("studentId") long studentId);

	/**
	 * @param enrollmentId:
	 *            Id of the Enrollment.
	 * @return number of rows affected.
	 */
	int checkIfDLMByEnrollmentId(@Param("enrollmentId") long enrollmentId);

	/**
	 * @param rosterId
	 * @return
	 */
	List<Student> findByRoster(@Param("rosterId") long rosterId);

	/**
	 * 
	 * @param rosterIds
	 * @param testSessionId
	 * @param completedStatusId
	 * @param assessmentProgramAbbr
	 * @param currentSchoolYear
	 * @return
	 */
	List<StudentTestSessionDto> findByRosterAndTestSession(@Param("rosterIds") List<Long> rosterIds,
			@Param("testSessionId") Long testSessionId, @Param("completedStatusId") Long completedStatusId,
			@Param("assessmentProgramAbbr") String assessmentProgramAbbr,
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("organizationId") Long organizationId);

	/**
	 * @param testSessionId
	 * @return
	 */
	List<StudentsTestSections> findByTestSession(@Param("testSessionId") Long testSessionId);
	/**
	 * Get list of student's organizations.
	 * 
	 * @param studentId
	 * @return
	 */
	// List<Organization> getOrganizations(@Param("studentId") long studentId);

	/**
	 * Get all students.
	 * 
	 * @return List of {@link Student}
	 */
	List<Student> getAll();

	/**
	 * TODO change it to take both state student identifier and attendanceSchool
	 * id. Check a student's login.
	 * 
	 * @param stateStudentIdentifier
	 *            state id of the student.
	 * @return {@link Student}
	 */
	Student getByStateStudentIdentifier(@Param("stateStudentIdentifier") String stateStudentIdentifier);

	/**
	 * @param student
	 *            {@link StudentExample}
	 * @return {@link List}
	 */
	List<Student> getByCriteria(StudentExample student);

	/**
	 * @param student
	 *            {@link StudentExample}
	 * @return {@link List}
	 */
	List<Student> getByCriteriaForResync(StudentExample student);

	/**
	 * @param toUpdate
	 *            the object to update.
	 * @return number of rows affected.
	 */
	int update(Student toUpdate);

	/**
	 * @param record
	 *            {@link Student}
	 * @param example
	 *            {@link StudentExample}
	 * @return
	 */
	int updateByExampleSelective(@Param("record") Student record, @Param("example") StudentExample example);

	/**
	 * @param stateStudentIdentifer
	 * @param attendanceSchoolIds
	 * @return
	 */
	Boolean verifyStateStudentIdentifier(@Param("stateStudentIdentifier") String stateStudentIdentifer,
			@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds);

	/**
	 * pass a given set of state identifiers and it returns the in-valid ones
	 * alone. The invalid state identifiers are presumably used by organizations
	 * not in user's visibility.
	 * 
	 * @param stateStudentIdentifiers
	 * @param differentialOrgIds
	 * @return
	 */
	List<String> verifyStateStudentIdentifiers(
			@Param("stateStudentIdentifiers") Collection<String> stateStudentIdentifiers,
			@Param("attendanceSchoolIds") Collection<Long> differentialOrgIds);

	/**
	 * @param stateStudentIdentifiers
	 * @param userOrganizationIds
	 * @return
	 */
	List<Student> verifyStudentsExist(@Param("stateStudentIdentifiers") List<String> stateStudentIdentifiers,
			@Param("attendanceSchoolIds") Collection<Long> userOrganizationIds);

	long lastid();

	/**
	 * Pass the given username and get the username modified for uniqueness.
	 * 
	 * @param username
	 * @return
	 */
	String getUsername(@Param("username") String username);

	/**
	 * Find students who are not synced to TDE.
	 * 
	 * @param studentIds
	 * @param synced
	 * @return
	 */
	List<Student> findByIdAndSynced(@Param("studentIds") List<Long> studentIds, @Param("synced") boolean synced);

	/**
	 * Returns all students, their enrolled school along with each roster they
	 * are part of if any.
	 * 
	 * i/p 1: selected organization (typically school), optional.Used as
	 * attendance school in search. i/p 2: user organizations.Search for all
	 * students with in the users organization tree. i/p 3: educator id. Search
	 * for all rosters, for the given teacher. i/p 4:
	 * hasViewAllRosterPermission. If true all rosters in the selected school(s)
	 * or users organization tree will be returned. If false then
	 * 
	 * @param limitCount
	 * @param enrollment
	 * @return
	 */
	List<StudentInformationRecord> getStudentInformationRecords(
			@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds,
			@Param("orgChildrenById") Long orgChildrenById, @Param("educatorId") Long educatorId,
			@Param("hasViewAllPermission") boolean hasViewAllPermission, @Param("orderByClause") String orderByClause,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear);

	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15234 : Data extract -
	 * Enrollment
	 * 
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param orgChildrenById
	 * @param isTeacher
	 * @param educatorId
	 * @param assessmentPrograms
	 * @param hasViewAllPermission
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @param studentInformationRecordCriteriaMap
	 * @return
	 */
	List<ViewStudentDTO> getViewStudentInformationRecordsExtract(@Param("orgChildrenById") Long orgChildrenById,
			@Param("isTeacher") boolean isTeacher, @Param("educatorId") Long educatorId,
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("assessmentPrograms") List<Long> assessmentPrograms);

	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15460 : View Student - new
	 * detail overlay Get student details including enrollment end roster
	 * information by given student id
	 * 
	 * @param studentInformationRecordCriteriaMap
	 * @return
	 */
	List<ViewStudentDTO> getStudentDetailsByStudentId(
			@Param("studentInformationRecordCriteriaMap") Map<String, Long> studentInformationRecordCriteriaMap,
			@Param("isTeacher") boolean isTeacher, @Param("currentSchoolYear") int currentSchoolYear);

	/**
	 * Returns all students, their enrolled school along with each roster they
	 * are part of if any.
	 * 
	 * i/p 1: selected organization (typically school), optional.Used as
	 * attendance school in search. i/p 2: user organizations.Search for all
	 * students with in the users organization tree. i/p 3: educator id. Search
	 * for all rosters, for the given teacher. i/p 4:
	 * hasViewAllRosterPermission. If true all rosters in the selected school(s)
	 * or users organization tree will be returned. If false then
	 * 
	 * @param limitCount
	 * @param enrollment
	 * @return
	 */
	List<ViewStudentDTO> getViewStudentInformationRecords(@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds,
			@Param("orgChildrenById") Long orgChildrenById, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long educatorId, @Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("userCurrentAssessmentProgramId") Long userCurrentAssessmentProgramId);

	List<ViewStudentDTO> getViewStudentInformationRecordsForDLM(
			@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds,
			@Param("orgChildrenById") Long orgChildrenById, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long educatorId, @Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("userCurrentAssessmentProgramId") Long userCurrentAssessmentProgramId);

	List<ViewStudentDTO> getViewStudentInformationRecordsInLcs(
			@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds,
			@Param("orgChildrenById") Long orgChildrenById, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long educatorId, @Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("userCurrentAssessmentProgramId") Long userCurrentAssessmentProgramId);

	/**
	 * count students for the given parameters.
	 * 
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param educatorId
	 * @param hasViewAllPermission
	 * @return
	 */
	int getViewStudentInformationRecordsCount(@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds,
			@Param("orgChildrenById") Long orgChildrenById, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long educatorId, @Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("userCurrentAssessmentProgramId") Long userCurrentAssessmentProgramId);

	/**
	 * count students for the given parameters.
	 * 
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param educatorId
	 * @param hasViewAllPermission
	 * @return
	 */
	int getStudentInformationRecordsCount(@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds,
			@Param("orgChildrenById") Long orgChildrenById, @Param("educatorId") Long educatorId,
			@Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear);

	/**
	 * @param enrollmentRosterIds
	 * @return
	 */
	List<Long> getStudentIdsByEnrollmentRosterIds(@Param("enrollmentRosterIds") List<Long> enrollmentRosterIds);

	/**
	 * @param testSessionId
	 * @param completedStatusId
	 * @return
	 */
	Integer getCompletedStudentsCount(@Param("testSessionId") Long testSessionId,
			@Param("completedStatusId") Long completedStatusId);

	/**
	 * @param testSessionId
	 * @param completedStatusId
	 * @return
	 */
	Integer getCompletedAndEnrolledStudentsCount(@Param("testSessionId") Long testSessionId,
			@Param("completedStatusId") Long completedStatusId);

	List<StudentRosterITIRecord> getStudentInformationRecordsForDLM(
			@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds, @Param("educatorId") Long educatorId,
			@Param("hasViewAllPermission") boolean hasViewAllPermission, @Param("orderByClause") String orderByClause,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap);

	/**
	 * count students for the given parameters.
	 * 
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param educatorId
	 * @param hasViewAllPermission
	 * @return
	 */
	int getStudentInformationRecordsCountForDLM(@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds, @Param("educatorId") Long educatorId,
			@Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap);

	/**
	 * count students for the given parameters if orgId's exceeds 50 in number.
	 * 
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param educatorId
	 * @param hasViewAllPermission
	 * @param studentInformationRecordCriteriaMap
	 * @param simplifiedLimit
	 * @return
	 */
	int getSimplifiedStudentInformationRecordsCountForDLM(@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds, @Param("educatorId") Long educatorId,
			@Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("simplifiedLimit") Integer simplifiedLimit);

	List<Student> getBySsidAndState(@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("stateId") Long stateId);

	int updateIgnoreFinalBands(Student toUpdate);

	int updateNullableFieldsForEditStudent(Student student);

	String findMappedComprehensiveRaceCode(@Param("ksdeCompRaceCode") String ksdeCompRaceCode);

	/**
	 * @author bmohanty_sta Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500
	 *         : DLM Class Roster Report - online report Get list of student ids
	 *         by passing a list of state student identifiers.
	 * @param studentStateIds
	 * @return
	 */
	List<String> getStudentIdsByStateIdentifiers(@Param("studentStateIds") List<String> studentStateIds);

	Student getStudentByStateStuIdAndStateId(@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("stateId") Long StateId);

	List<Long> getNonDLMStudentIdsInOrg(@Param("organizationId") Long orgId, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId, @Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("offset") int offset, @Param("limit") int limit,
			@Param("assessmentPrograms") List<Long> assessmentPrograms);

	List<Long> getDLMStudentIdsInOrg(@Param("organizationId") Long orgId, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long teacherId, @Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("offset") int offset, @Param("limit") int limit);

	Student getByStudentID(@Param("studentID") Long studentID);
	
	Student getByStudentIDWithFirstContact(@Param("studentID") Long studentID);

	List<Long> getActiveStudentIdsWithPNPInOrg(@Param("organizationId") Long orgId,
			@Param("isTeacher") boolean isTeacher, @Param("educatorId") Long teacherId,
			@Param("currentSchoolYear") Integer currentSchoolYear, @Param("limit") int limit,
			@Param("offset") int offset, @Param("assessmentPrograms") List<Long> assessmentPrograms);

	int updateProfileStatusByStudentId(@Param("studentId") long studentId, @Param("profileStatus") String profileStatus,
			@Param("modifiedDate") Date modifiedDate, @Param("modifiedUser") long modifiedUser);

	List<StudentReport> getStudentsForReportProcess(@Param("studentId") Long studentId,
			@Param("contractOrgId") Long contractOrgId, @Param("assessmentId") Long assessmentId,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("assessmentProgramCode") String assessmentProgramCode, @Param("gradeId") Long gradeId,
			@Param("contentAreaId") Long contentAreaId, @Param("currentSchoolYear") Long currentSchoolYear,
			@Param("rawScaleExternalTestIds") List<Long> rawScaleExternalTestIds,
			@Param("testsStatusIds") List<Long> testsStatusIds, @Param("studentIdList") List<Long> studentIdList,
			@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

	List<StudentReport> getStudentsForReportGeneration(@Param("studentId") Long studentId,
			@Param("contractOrgId") Long contractOrgId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("gradeId") Long gradeId, @Param("contentAreaId") Long contentAreaId,
			@Param("schoolYear") Long schoolYear, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize,
			@Param("processByStudentId") String processByStudentId,
			@Param("reprocessEntireDistrict") String reprocessEntireDistrict);

	ArrayList<Category> getViewStudentInformationRecordsForFirstLanguage();

	ArrayList<Category> getViewStudentInformationRecordsForComprehensiveRace();

	List<ViewStudentDTO> getCreateTestRecordStudentsGridData(@Param("organizationId") Long orgId,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear, @Param("assessmentProgrmId") Long assessmentProgramId);

	Integer getCreateTestRecordStudentsGridDataCount(@Param("organizationId") Long orgId,
			@Param("assessmentProgrmId") Long assessmentProgramId, @Param("currentSchoolYear") int currentSchoolYear,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap);

	List<ViewStudentDTO> getClearTestRecordStudentsGridData(@Param("organizationId") Long orgId,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("testTypeCode") String testTypeCode, @Param("subjectAreaId") Long subjectAreaId);

	Integer getClearTestRecordStudentsGridDataCount(@Param("organizationId") Long orgId,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("currentSchoolYear") int currentSchoolYear,
			@Param("testTypeCode") String testTypeCode, @Param("subjectAreaId") Long subjectAreaId,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap);

	List<ViewStudentDTO> getTestRecordByStudentsData(@Param("studentId") Long studentId,
			@Param("organizationId") Long orgId, @Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType, @Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("currentSchoolYear") Integer currentSchoolYear);

	Integer getTestRecordByStudentsDataGridCount(@Param("studentId") Long studentId,
			@Param("organizationId") Long orgId,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("currentSchoolYear") Integer currentSchoolYear);

	// To get school name and district name of the student based on state
	// student identifier
	List<EnrollmentsOrganizationInfo> getOrganizationsByStateStudentId(
			@Param("stateStudentId") String stateStudentIdentifier, @Param("stateId") Long stateId,
			@Param("currentSchoolYear") int currentSchoolYear);

	List<Student> findStudentsByIds(@Param("studentIds") List<Long> studentIds);

	List<ViewStudentDTO> getStudentUserNamePasswordExtract(@Param("orgChildrenById") Long orgChildrenById,
			@Param("isTeacher") boolean isTeacher, @Param("educatorId") Long educatorId,
			@Param("currentSchoolYear") Integer currentSchoolYear, @Param("grade") String gradeAbbreviatedName,
			@Param("contentAreaAssessment") List<Map<Long, Long>> contentAreaAssessment, @Param("assessmentProgramIds") List<Integer> assessmentProgramIds);

	/*
	 * Added for US18184 :For student audit
	 */
	StudentJson getStudentjsonData(@Param("studentId") Long studentId);

	String getStudentStateName(@Param("studentId") Long studentId);

	Student getStudentDetailsById(@Param("studentId") Long studentId);

	String getPasswordByStudentId(@Param("studentId") Long studentId);

	void updateStudentPassword(@Param("studentId") Long studentId, @Param("newPassword") String newPassword,@Param("userId") Long userId, Date date);

	String checkStudentUsername(@Param("newUsername") String newUsername,@Param("id") Long id);

	void updateStudentUsername(@Param("studentId") Long studentId, @Param("newUsername") String newUsername,@Param("userId") Long userId, Date date);

	Student getStateStudentIdFromELPAPreId(@Param("studentId") String studentId);

	String getStateStudentIdfrompSeudoId(@Param("pSeudoId") String pSeudoId);

	List<KELPAReport> getKELPAStateStudents(@Param("districtId") Long districtId, @Param("schoolYear") int schoolYear);

	List<ViewStudentDTO> getViewDuplicateStudentInformationRecords(
			@Param("attendanceSchoolIds") List<Long> attendanceSchoolIds,
			@Param("userOrganizationIds") List<Long> userOrganizationIds,
			@Param("orgChildrenById") Long orgChildrenById, @Param("isTeacher") boolean isTeacher,
			@Param("educatorId") Long educatorId, @Param("hasViewAllPermission") boolean hasViewAllPermission,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType,
			@Param("offset") Integer offset, @Param("limit") Integer limitCount,
			@Param("studentInformationRecordCriteriaMap") Map<String, String> studentInformationRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("userCurrentAssessmentProgramId") Long userCurrentAssessmentProgramId);

	void mergeStudents(@Param("studentToRetain") Long studentToRetain, @Param("studentToRemove") Long studentToRemove,
			@Param("modifiedDate") Date modifiedDate, @Param("modifiedUser") Long modifiedUser, 
			@Param("selectedStudentTestIds") List<Long>  selectedStudentTestIds, @Param("unSelectedStudentTestIds") List<Long>  unSelectedStudentTestIds);

	void removeDestPnpSettings(@Param("studentPnpRemove") Long studentPnpRemove,
			@Param("modifiedDate") Date modifiedDate, @Param("modifiedUser") Long modifiedUser);

	void removeSourcePnpSettings(@Param("studentPnpToRetain") Long studentIdForPnp,
			@Param("studentPnpRemove") Long studentPnpRemove, @Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	void removeSourceFcsSettings(@Param("studentFcsToRetain") Long studentIdForFcs,
			@Param("studentFcsRemove") Long studentFcsRemove, @Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	void removeDestinationRosters(@Param("studentRostersRemove") Long studentRostersRemove,
			@Param("modifiedDate") Date modifiedDate, @Param("modifiedUser") Long modifiedUser);

	void removeSourceRosters(@Param("studentRosterToRetain") Long studentIdForRoster,
			@Param("studentRostersRemove") Long studentRostersRemove, @Param("modifiedDate") Date modifiedDate,
			@Param("modifiedUser") Long modifiedUser);

	List<ViewStudentDTO> getInactiveStudentDetailsByStudentId(
			@Param("studentInformationRecordCriteriaMap") Map<String, Long> studentInformationRecordCriteriaMap,
			@Param("isTeacher") boolean isTeacher, @Param("currentSchoolYear") int currentSchoolYear);

	List<Student> findByRosterInCurrentYear(@Param("id") Long id, @Param("currentSchoolYear") Long currentSchoolYear);

	List<Long> getStudentsForAutoAssign(@Param("rosterIds") List<Long> rosterIds,
			@Param("gradeCourseIds") List<Long> gradeCourseIds, @Param("contentAreaIds") List<Long> contentAreaIds,
			@Param("orgIds") List<Long> orgIds, @Param("userIds") List<Long> userList,
			@Param("schoolYear") Long schoolYear,@Param("assessmentProgramId") Long assessmentProgramId);

	void resetStudentPasswordOnAnnualReset(@Param("stateId")Long stateId,@Param("passwordLength") Integer passwordLength,@Param("offset") int offset,@Param("limit") int limit);

	int getStudentCountForPasswordReset(@Param("stateId")Long stateId);
	
	List<InterimStudentReport> getStudentsForPredictiveReportProcess(@Param("studentId") Long studentId,
			@Param("contractOrgId") Long contractOrgId, 
			@Param("assessmentId") Long assessmentId,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("assessmentProgramCode") String assessmentProgramCode, 
			@Param("gradeId") Long gradeId,
			@Param("contentAreaId") Long contentAreaId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("rawScaleExternalTestIds") List<Long> rawScaleExternalTestIds,
			@Param("testsStatusIds") List<Long> testsStatusIds, 
			@Param("studentIdList") List<Long> studentIdList,
			@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle,
			@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,
			@Param("jobStartTime") Date jobStartTime);
	
	int updateOnEditOrActivate(@Param("record") Student record);
	
	Student getByExternalId(@Param("externalId") String externalId);

	List<ViewStudentDTO> getStudentUserNamePasswordExtractForDlmAndPltw(@Param("orgChildrenById") Long orgChildrenById,
			@Param("isTeacher") boolean isTeacher, @Param("educatorId") Long educatorId,
			@Param("currentSchoolYear") Integer currentSchoolYear, @Param("grade") String gradeAbbreviatedName,
			@Param("contentAreaAssessmentForDlmAndPltw") List<Map<Long, Long>> contentAreaAssessmentForDlmAndPltw, @Param("assessmentProgramIds") List<Integer> assessmentProgramIds);
	
	List<ActScoringDescription> getActScoreByOrgIdsAssessmentContentGradeSchoolYear( 
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("gradeId") Long gradeId,
			@Param("contentAreaId") Long contentAreaId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("levelId") Long levelId); 
	
	List<ActScoringDescription> getActScoreDescriptionByOrgIdsAssessmentContentGradeSchoolYear( 
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("gradeId") Long gradeId,
			@Param("contentAreaId") Long contentAreaId, 
			@Param("currentSchoolYear") Long currentSchoolYear); 

	boolean isAddStudentDemographicValueExists(
			@Param("legalFirstName") String legalFirstName, 
			@Param("legalLastName") String legalLastName,
			@Param("gender") Long gender,
			@Param("dobDates") Date dobDates,
			@Param("stateId") Long stateId
			);
	
	boolean isEditStudentDemographicValueExists(
			@Param("stateStudentId") String stateStudentId,
			@Param("legalFirstName") String legalFirstName, 
			@Param("legalLastName") String legalLastName,
			@Param("gender") Long gender,
			@Param("dobDates") Date dobDates,
			@Param("stateId") Long stateId
			);
	
	Long getStudentStateIdBySchoolId(
			@Param("schoolId") Long schoolId
			);
	
	List<Long> getActLevelsForActScoring();

	String getStateStudentIdentifierLength(@Param("userStateId") Long userStateId);
	
	List<Student> getStudentBySchoolIDandGradeIDandSchoolyear(@Param("schoolId") Long schoolId, @Param("schoolYear") Long schoolYear, 
			@Param("grades") Long[] grades , @Param("assesmentProgramID") Long assesmentProgramID, @Param("teacherID") Long teacherID);
	
	List<User> getTeacherByStudentIDandSchoolIDandGradeID(@Param("schoolId") Long schoolId, @Param("schoolYear") Long schoolYear, 
			@Param("grades") Long[] grades , @Param("assesmentProgramID") Long assesmentProgramID,@Param("studentIds") Long[] studentIDs);
	
	List<IAPStudentTestStatusDTO> iapHomeStudentsTestStatusRecords(@Param("schoolYear") Long schoolYear, @Param("stateId") Long stateId, @Param("schoolID") Long schoolID,
			@Param("educatorIds") Long[] educatorIds,
			@Param("studentIDs") Long[] studentIDs, @Param("stateStudentID") String stateStudentID, @Param("grades") Long[] grades,
			@Param("offSet") Long offSet,@Param("paginationLimit") Long paginationLimit, @Param("contentareaid") Long contentareaid,
			@Param("operationalTestWindowID") Long operationalTestWindowID);
    
    int updateStudentsTestSectionsByRosterId(@Param("rosterId") Long rosterId,@Param("modifiedUserId") Long modifiedUserId); // added for Roster API
 
    boolean hasCompletedTestByExtId(@Param("externalId") String uniqueStudentId);
    
    boolean isEnrolledInSameSchoolBefore(@Param("externalId") String uniqueStudentId, @Param("schoolId") Long schoolID, @Param("schoolYear") int schoolYear);
    
    Long countStateStudentIdentifierLength(@Param("userStateId") Long userStateId);
 
    List<StudentExitCodesDTO> getExitCodesByState(@Param("assessmentProgramId") Long assessmentProgramId, @Param("organizationId") long organizationId,
            @Param("schoolYear") Long schoolYear, @Param("isState") Boolean isState);
 
    List<Integer> getStateOrAssesPgmSpecificExitCodes(@Param("organizationId") Long organizationId, @Param("assessmentProgramId") Long assessmentProgramId, 
            @Param("schoolYear") Long schoolYear);
 
    Boolean checkStateHaveSpecificExitCodes(@Param("organizationId") Long organizationId, @Param("assessmentProgramId") Long assessmentProgramId, @Param("schoolYear") int reportYear);
 
    List<Integer> getStateSpecificExitCodesForKids(@Param("organizationId") Long organizationId, @Param("assessmentProgramIds") List<Long> assessmentProgramIds, 
            @Param("schoolYear") Long schoolYear);    

}