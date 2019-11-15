package edu.ku.cete.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.enrollment.EnrollmentsOrganizationInfo;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.student.StudentExitCodesDTO;
import edu.ku.cete.domain.student.StudentInformationRecord;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.student.StudentRosterITIRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.web.IAPStudentTestStatusDTO;
import edu.ku.cete.web.ViewStudentDTO;

/**
 * @author nicholas.studt
 */
public interface StudentService extends Serializable {

    /**
     * @param toAdd object to add.
     * @return {@link Student}
     */
	// PreAuthorize commented as part of F848.
    //@PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_CREATE')")
    Student add(Student toAdd);

    /**
     * @param id Id of the object to delete.
     * @return boolean was the object deleted.
     */
    @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_DELETE')")
    boolean delete(Long id);
    
    /**
     *
     *@param studentId
     *@return
     */
    @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_VIEW')")
    Student findById(long studentId);

    @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_VIEW')")
    List<Student> findByRoster(long rosterId);

    /**
     * Get list of student's organizations.
     * @param studentId
     * @return
     */
    //List<Organization> getOrganizations(Long studentId);    
    /**
     * Validate student login information.
     * @param studentStateIdentifier Student userName.
     * @return {@link Student}
     */
    @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_VIEW')")
    Student getByStudentID(Long studentID);

    /**
     * Validate student login information.
     * @param studentStateIdentifier Student userName.
     * @return {@link Student}
     */
    Student getByStudentIDWithFirstContact(Long studentID);
    
    /**
     * Get all students.
     * @return List of {@link Student}
     */
    @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_VIEW')")
    List<Student> getAll();

    /**
     * @param saveOrUpdate the object to be saved or updated.
     * @param attendanceSchoolId 
     * @return {@link Student}
     */
    Student addOrUpdate(Student saveOrUpdate,Long attendanceSchoolId);

    /**
     * @param toUpdate the object to update.
     * @return {@link Student}
     * @throws ServiceException {@link ServiceException}
     */
    @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_MODIFY')")
    Student update(Student toUpdate);

	/**
	 * @param student {@link StudentExample}
	 * @return {@link Student}
	 */
    /**Commented for
     * US16352 not able invoke from spring batch
     */
  //  @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_VIEW')")
	List<Student> getByCriteria(StudentExample student);

    /**
	 * @param student {@link StudentExample}
	 * @return {@link Student}
	 */
    @PreAuthorize(value = "hasRole('PERM_STUDENTRECORD_VIEW')")
	List<Student> getByCriteriaForResync(StudentExample student);
    
	/**
	 * @param student {@link Student}
	 * @param attendanceSchoolId 
	 * @return {@link Student}
	 */
    //TODO add pre-authorize.
	Student addOrUpdateSelective(Student student, Long attendanceSchoolId);

	/**
	 *
	 *@param record {@link Student}
	 *@param example {@link StudentExample}
	 *@return int - number of records updated.
	 */
	int updateByExampleSelective(Student record, StudentExample example);

	/**
	 * @param contractingOrganizationIds
	 * @param userOrganizationIds
	 * @param differentialOrgIds
	 * @param toSaveOrUpdate
	 * @return
	 */
	Student verifyStateStudentIdentifierExists(
			List<Long> contractingOrganizationIds,
			List<Long> userOrganizationIds, List<Long> differentialOrgIds,
			Student toSaveOrUpdate);

	/**
	 * Pass a list of state student identifiers and it returns the state student
	 * identifiers that are already used and not in user's visibility
	 * 
	 * @param contractingOrganizationIds all contracting orgs with in the highest contracting org.
	 * @param userOrganizationIds all user organization ids with in the user.
	 * @param differentialOrgIds organizations with in the contracting org but not with in the user.
	 * @param students list of students that need to marked as valid or invalid with respect to state ids.
	 * @return
	 */
	List<? extends StudentRecord> verifyStateStudentIdentifiersExist(
			Collection<Long> contractingOrganizationIds,
			Collection<Long> userOrganizationIds, Collection<Long> differentialOrgIds,
			List<? extends StudentRecord> students);

	/**
	 * @param toSaveorUpdate
	 * @param userOrganizationChildrenIds
	 * @return
	 */
	Student getByStateStudentIdentifier(Student toSaveorUpdate,
			Long attendanceSchoolId);
	/**
	 * Sync the student to TDE.
	 * @param student
	 * @return
	 */
	//Student upsertStudent(Student student);
	/**
	 * @param studentIds
	 * @return
	 */
//	boolean upsertUnsyncedStudents(List<Long> studentIds);
	
	//void updateUnsyncedStudents(List<Long> studentIds);

	/**
	 * Searches for the students with in the users organization tree and the ones
	 * that are not found marks them as not found.
	 * 
	 * @param userOrganizationIds
	 * @param studentRecords
	 * @return
	 */
	List<? extends StudentRecord> verifyStudentsExist(
			Collection<Long> userOrganizationIds,
			List<? extends StudentRecord> studentRecords);
	
	/**
	 * Returns all the students, their enrolled school along with each roster they are part of if any.
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @param studentInformationRecordCriteriaMap
	 * @param educatorId 
	 * @return
	 */
	List<StudentInformationRecord> getStudentInformationRecords(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
			UserDetailImpl userDetails, boolean hasViewAllPermission, String orderByClause, Integer offset,
    		Integer limitCount,	Map<String,String> studentInformationRecordCriteriaMap, Long educatorId);

	/**
	 * Returns all the students, their enrolled school along with each roster they are part of if any.
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @param studentInformationRecordCriteriaMap
	 * @return
	 */
	List<StudentRosterITIRecord> getStudentInformationRecordsForDLM(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			UserDetailImpl userDetails, boolean hasViewAllPermission, String orderByClause, Integer offset,
    		Integer limitCount,	Map<String,String> studentInformationRecordCriteriaMap);
	/**
	 * Count students for the given parameters.
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param studentInformationRecordCriteriaMap
	 * @return
	 */
	int getStudentInformationRecordsCount(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
			UserDetailImpl userDetails, boolean hasViewAllPermission,	Map<String,String> studentInformationRecordCriteriaMap,Long educatorId);
	
	/**
	 * Count students for the given parameters.
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param studentInformationRecordCriteriaMap
	 * @return
	 */
	int getViewStudentInformationRecordsCount(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,Long orgChildrenById,
			UserDetailImpl userDetails, boolean hasViewAllPermission,	
			Map<String,String> studentInformationRecordCriteriaMap, boolean isTeacher, Long educatorId);
	
	/**
	 * Count students for the given parameters.
	 * @param attendanceSchoolIds
	 * @param userOrganizationIds
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param studentInformationRecordCriteriaMap
	 * @return
	 */
	int getStudentInformationRecordsCountForDLM(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			UserDetailImpl userDetails, boolean hasViewAllPermission,	Map<String,String> studentInformationRecordCriteriaMap);
	
	Student validateIfStudentExists(Student toSaveorUpdate);


	List<ViewStudentDTO> getViewStudentInformationRecords(
			List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			Long orgChildrenById, UserDetailImpl userDetails,
			boolean hasViewAllPermission, String sortByColumn, String sortType, Integer offset,
			Integer limitCount,
			Map<String, String> studentInformationRecordCriteriaMap,
			boolean isTeacher, Long educatorId); 
	
	List<ViewStudentDTO> getViewStudentInformationRecordsForDLMTestlets(
			List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			Long orgChildrenById, UserDetailImpl userDetails,
			boolean hasViewAllPermission, String sortByColumn, String sortType, Integer offset,
			Integer limitCount,
			Map<String, String> studentInformationRecordCriteriaMap,
			boolean isTeacher, Long educatorId); 
	
	List<ViewStudentDTO> getViewStudentInformationRecordsInLcs(
			List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			Long orgChildrenById, UserDetailImpl userDetails,
			boolean hasViewAllPermission, String sortByColumn, String sortType, Integer offset,
			Integer limitCount,
			Map<String, String> studentInformationRecordCriteriaMap,
			boolean isTeacher, Long educatorId); 
	
	List<ViewStudentDTO> getViewStudentInformationRecordsExtract(
			Long orgChildrenById, 
			boolean isTeacher, Long educatorId, Integer currentSchoolYear, List<Long> assessmentPrograms); 
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15460 : View Student - new detail overlay
	 * Get student details including enrollment end roster information by given student id
	 * @param studentInformationRecordCriteriaMap
	 * @return
	 */
	List<ViewStudentDTO> getStudentDetailsByStudentId(Map<String, Long> studentInformationRecordCriteriaMap, boolean isTeacher, int currentSchoolYear);
	
	public int updateNullableFieldsForEditStudent(Student student);

	String editStudent(Long studentId, Student newStudent, Long currentSchoolYear) throws JsonProcessingException;
	
	/**
	 * @author bmohanty_sta
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get list of student ids by passing a list of state student identifiers.
	 * @param studentStateIds
	 * @return
	 */
	List<String> getStudentIdsByStateIdentifiers(List<String> studentStateIds);
	
	boolean isStateStuIdExits(String stateStudentIdentifier, Long stateId);
	
	List<Long> getNonDLMStudentIdsEnrolledInOrg(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId, int currentSchoolYear, int offset, int limit, List<Long> assessmentPrograms);
	
	List<Long> getDLMStudentIdsEnrolledInOrg(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId, int currentSchoolYear, int offset, int limit);
	
	List<Long> getActiveStudentIdsWithPNPEnrolledInOrg(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId, int limit, int offset, int currentSchoolYear, List<Long> assessmentPrograms);
	
	List<AssessmentProgram> getStudentAssessmentProgram(Long studentId);

	Map<String, Object> getViewStudentInformationRecordsForFirstLanguageCategoryCodeName();

	List<ViewStudentDTO> getCreateTestRecordStudentsGridData(Long orgId,
			Long assessmentProgramId, UserDetailImpl userDetails, String sortByColumn, String sortType, Integer offset, Integer limitCount, Map<String, String> studentInformationRecordCriteriaMap);

	int getCreateTestRecordStudentsGridDataCount(Long orgId,
			Long assessmentProgramId, UserDetailImpl userDetails,
			Map<String, String> studentInformationRecordCriteriaMap);

	List<ViewStudentDTO> getClearTestRecordStudentsGridData(Long orgId,
			String testTypeCode, String subjectCode, Long assessmentProgramId,
			UserDetailImpl userDetails, String sortByColumn, String sortType,
			int offset, int limitCount,
			Map<String, String> studentInformationRecordCriteriaMap);

	int getClearTestRecordStudentsGridDataCount(Long orgId,
			String testTypeCode, String subjectCode, Long assessmentProgramId,
			UserDetailImpl userDetails,
			Map<String, String> studentInformationRecordCriteriaMap);

	List<ViewStudentDTO> getTestRecordByStudentsGridData(Long studentId,
			Long long1, String sortByColumn, String sortType,
			int offSet, int limitCount,
			Map<String, String> studentInformationRecordCriteriaMap,Long assessmentProgramId,Integer currentSchoolYear);

	

	int getTestRecordByStudentsGridDataCount(Long studentId, Long orgId,
			Map<String, String> studentInformationRecordCriteriaMap, Long assessmentProgramId,Integer currentSchoolYear);

	boolean isStudentAssociatedWithAssessmentProgram(long studentId, String assessmentProgramAbbreviatedName);

	void addStudentAssmntIfNotPresent(Student student);
	
	List<Student> findStudentsByIds(List<Long> ineligibleStudentIds);
		
	//To get school name and district name of the student based on state student identifier
	List<EnrollmentsOrganizationInfo> getOrganizationsByStateStudentId(
		String stateStudentIdentifier, Long stateId, int currentSchoolYear);

	List<ViewStudentDTO> getStudentUserNamePasswordExtract(Long orgChildrenById, 
		boolean isTeacher, Long educatorId, Integer currentSchoolYear, 
		String gradeAbbreviatedName, List<Map<Long, Long>> contentAreaAssessment, Long rosterId, List<Integer> assessmentProgramIds);
   
	//created for US18184 - student audit story 
	void insertIntoDomainAuditHistory(Long objectId, Long createdUserId,
			String action, String source, String userBeforUpdate,
			String userAfterUpdate);

	boolean addToGroupsAuditTrailHistory(DomainAuditHistory domainAuditHistory);

	boolean isStudentOnlyInDLM(Long studentId);

	String checkStudentPassword(Long studentId);

	void updateStudentPassword(Long studentId, String newPassword);

	String checkStudentUsername(String newStudUsername, Long studentId);

	void updateStudentUsername(Long studentId, String newUsername);
	
	String gets3Credentials(String filename) throws ServiceException;

	List<ViewStudentDTO> getViewDuplicateStudentInformationRecords(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds, 
			Long orgChildrenById, UserDetailImpl userDetails, boolean hasPermission, String sortByColumn, String sortType, 
			Integer offset, Integer limitCount, Map<String, String> studentInformationRecordCriteriaMap, boolean teacher, Long educatorId);

	void mergeStudents(Long studentToRetain,Long studentToRemove, Long studentIdForPnp, Long studentIdForFcs, Long studentIdForRoster, List<Long> selectedStudentTestIds, List<Long> unSelectedStudentTestIds);

	List<ViewStudentDTO> getInactiveStudentDetailsByStudentId(Map<String, Long> studentInformationRecordCriteriaMap,
			boolean teacher, int currentSchoolYear);
	
	boolean reactivateTestsForStudent(String inactivationType, Long studentId, Integer currentSchoolYear, Long attendanceSchoolId, Long newGradeLevel, Long modifiedUserId);

	boolean deactivateTestsForStudentGradeChange(Long studentId, Integer currentSchoolYear, Long attendanceSchoolId, Long oldGradeLevel, Long newGradeLevel, Long modifiedUserId);

	void removeAssessmentProgram(Long organizationId, Long schoolYear,
			Long assessmentProgramId, Long modifiedUserId);

	void resetStudentPasswordOnAnnualReset(Long orgId, int passwordLength,
			String[] qcStates);
	
	String generateStudentPassword();

	List<Student> getBySsidAndState(String stateStudentIdentifier, Long stateId);

	boolean isStudentInAssessmentProgram(long studentId, long assessmentProgramId);
	
	Student getByExternalId(String externalId);
	
	List<ViewStudentDTO> getStudentUserNamePasswordExtractForDlmAndPltw(Long orgChildrenById, 
			boolean isTeacher, Long educatorId, Integer currentSchoolYear, 
			String gradeAbbreviatedName, List<Map<Long, Long>> contentAreaAssessmentForDlmAndPltw, Long rosterId, List<Integer> assessmentProgramIds);

	boolean isAddStudentDemographicValueExists(String legalFirstName, String legalLastName, Long gender, Date dobDates, Long stateId);
	
	boolean isEditStudentDemographicValueExists(String stateStudentId, String legalFirstName, String legalLastName, Long gender, Date dobDates, Long stateId);
	
	List<Student> getStudentBySchoolIDandGradeIDandSchoolyear(Long schoolId, Long schoolYear, Long[] grades , Long assesmentProgramID, Long teacherID);
	
	List<User> getTeacherByStudentIDandSchoolIDandGradeID(Long schoolId, Long schoolYear, Long[] grades , Long assesmentProgramID, Long[] studentIDs);
	
	/**
	 * @author deb
	 * get result for IAP home page search
	 * The method needs either stateID and stateStudentID as required field or schoolID and schoolYear. Rest of the parameter are optional
	 * @param schoolYear, stateId, schoolID, educatorIds, studentIDs, stateStudentID, grades
	 * @return List<IAPStudentTestStatusDTO>
	 */
	
	List<IAPStudentTestStatusDTO> iapHomeStudentsTestStatusRecords(Long schoolYear, Long stateID, Long schoolID,
			Long[] educatorIDs, Long[] studentIDs, String stateStudentID, Long[] grades, Long offSet, Long paginationLimit, Long contentareaid,
			Long operationalTestWindowID);
    Long getStudentStateIdBySchoolId(Long schoolId);
    
    
    String getStateStudentIdentifierLengthByStateID();
 
    List<StudentExitCodesDTO> getExitCodesByState(Long currentAssessmentProgramId, Long currentOrganizationId,
            Long currentSchoolYear);
 
    boolean hasCompletedTestByExtId(String uniqueStudentId);
 
    List<Integer> getStateSpecificExitCodes(Long stateId, Long assessmentProgramId, Long schoolYear);
 
    List<Integer> getStateSpecificExitCodesForKids(Long stateId, List<Long> assessmentPgmIds, Long currentSchoolYear);
    
    Map<String, Object> validateStateStudentId(String studentStateId);

	boolean isEnrolledInSameSchoolBefore(String externalId, Long schoolId, int schoolYear);
    
}
