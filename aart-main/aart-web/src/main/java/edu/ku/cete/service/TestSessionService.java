/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.StudentTestInfo;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.TestSessionExample;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.testsession.AutoRegisteredTestSessionDTO;
import edu.ku.cete.domain.testsession.TestSessionRoster;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.web.AssignScorerTestSessionDTO;
import edu.ku.cete.web.KELPATestAdministrationDTO;
import edu.ku.cete.web.ScorerTestSessionStudentDTO;
import edu.ku.cete.web.StudentTestSessionInfoDTO;
import edu.ku.cete.web.TestFormMediaResourceDTO;
import edu.ku.cete.web.TestSessionPdfDTO;

/**
 * @author neil.howerton
 *
 */
public interface TestSessionService {

    /**
     * Give a total of the number of test session records that match the example.
     * @param example {@link TestSessionExample}
     * @return int - the number of records that match the example.
     */
    int countByExample(TestSessionExample example);

    /**
     * Delete all test session records that match the example.
     * @param example {@link TestSessionExample}
     * @return int - the number of records deleted.
     */
    int deleteByExample(TestSessionExample example);

    /**
     * Create a new test session record.
     * @param record {@link TestSession}
     * @return int - the number of records inserted, should always return 1. Anything else would be an error case.
     * @throws DuplicateTestSessionNameException DuplicateTestSessionNameException
     */
    TestSession insert(TestSession record) throws DuplicateTestSessionNameException;

    /**
     * Insert a new test session record, but only set values that exist on the test session parameter.
     * @param record {@link TestSession}
     * @return int - the number of records inserted, should always return 1. Anything else would be an error case.
     */
    int insertSelective(TestSession record);

    /**
     * Find all test session records that match the example.
     * @param example {@link TestSessionExample}
     * @param categoryId 
     * @return List<TestSession> - the list of records that match the example.
     */
    List<TestSession> selectByExample(TestSessionExample example);
    
    List<TestSession> selectByExampleAndCategory(Long rosterId,Long categoryId);
    
    List<TestSession> selectByRosterAndCategory(Long rosterId, Long... categoryIds);

    /**
     * Update all test sessions that match the example with the values specified in the TestSession object.
     * Will only update the values that the exist in the TestSession object.
     * @param record {@link TestSession}
     * @param example {@link TestSessionExample}
     * @return int - the number of records updated.
     */
    int updateByExampleSelective(TestSession record, TestSessionExample example);

    /**
     * Update all test sessions that match the example with the values specified in the TestSession object.
     * @param record {@link TestSession}
     * @param example {@link TestSessionExample}
     * @return int - the number of records updated.
     */
    int updateByExample(TestSession record, TestSessionExample example);

    /**
     * This is a utility method for removing test sessions for a roster
     * that have had all their students removed from the session.
     * @param rosterId long
     */
    void removeEmptySessions(long rosterId);

    /**
     * This method makes a call to TDE to cancel the specified test session.
     * @param testSessionId long
     * @param user User
     * @return boolean - whether the call completed successfully or not.
     */
    boolean cancelTestSession(long testSessionId, User user);

    /**
     * This method finds a test session record based on it's primary key.
     * @param testSessionId long
     * @return TestSession - the test session object with the given primary key.
     */
    TestSession findByPrimaryKey(@Param("testSessionId")long testSessionId);

    /**
     * Returns the TestSession with associated objects.
     * @param testSessionId long
     * @return {@link TestSession}
     */
    TestSession findWithAssociationsByPrimaryKey(long testSessionId);

    /**
     * @param testSessionId {@link long}
     * @return {@link List<TestSessionPdfDTO>}
     */
    List<TestSessionPdfDTO> findTestSessionTicketDetailsById(
    		List<Long> testSessionIds, Boolean isAutoRegistered,
    		List<Long> studentIds, UserDetailImpl userDetails);

    /**
     * 
     * @param userDetails
     * @param hasViewAllPermission
     * @param orderByClause
     * @param offset
     * @param limitCount
     * @param studentRosterCriteriaMap
     * @param systemEnrollmentRuleId
     * @param manualEnrollmentRuleId
     * @param hasViewHighStakesTestSessionsPermission
     * @param qcComplete
     * @return
     */
	/**
	 * Bishnupriya Nayak :US19343 : Test Management TestSession Completion
	 * Status Display
	 */
	List<TestSessionRoster> getTestSessionWithRoster(UserDetailImpl userDetails, 
			boolean hasViewAllPermission, String sortByColumn, String sortType, int offset,
    		int limitCount,	Map<String,String> studentRosterCriteriaMap, Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId, Boolean hasViewHighStakesTestSessionsPermission, Boolean qcComplete,
    		Long assessmentProgramId, Long testingProgramId, Long contentAreaId, 
    		Long gradeCourseId, Long schoolOrgId, Boolean showExpiredFlag, Boolean hasQCCompletePermission, Boolean includeCompletedTestSessionsFlag, Boolean includeInProgressTestSession);	

	/**
	 * 
	 * @param userDetails
	 * @param hasViewAllPermission
	 * @param studentRosterCriteriaMap
	 * @param systemEnrollmentRuleId
	 * @param manualEnrollmentRuleId
	 * @param hasViewHighStakesTestSessionsPermission
	 * @param qcComplete
	 * @return
	 */
	int countTestSessionWithRoster(UserDetailImpl userDetails,
			boolean hasViewAllPermission,
			Map<String, String> studentRosterCriteriaMap,Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId, Boolean hasViewHighStakesTestSessionsPermission, Boolean qcComplete,
    		Long assessmentProgramId, Long testingProgramId, Long contentAreaId, 
    		Long gradeCourseId, Long schoolOrgId, Boolean showExpiredFlag, Boolean hasQCCompletePermission);

	boolean isTestSessionDeletable(Long testSessionId);
	
	List<TestSessionRoster> getQCTestSessionWithRoster(List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
			UserDetailImpl userDetails, boolean hasViewAllPermission, String orderByClause, int offset,
    		int limitCount,	Map<String,String> studentRosterCriteriaMap, Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId,
    		Boolean hasHighStakesPermission);	


	int countQCTestSessionWithRoster(List<Long> attendanceSchoolIds,
			List<Long> userOrganizationIds, UserDetailImpl userDetails,
			boolean hasViewAllPermission,
			Map<String, String> studentRosterCriteriaMap,Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId,
    		Boolean hasHighStakesPermission);	
	
	TestSession insertTestSessionForAutoRegistration(TestSession record) throws DuplicateTestSessionNameException;

	/**
	 * @param testSessionName
	 * @param studentId
	 * @return
	 */
	List<Long> selectTestSessionByStudentId(String testSessionName, Long studentId);
	
	/**
	 * @param userDetails
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @param studentRosterCriteriaMap
	 * @return
	 */
	List<AutoRegisteredTestSessionDTO> getAutoRegisteredTestSessions(UserDetailImpl userDetails,
			Long currentSchoolYear, String sortByColumn, String sortType, Integer offset,
			Integer limitCount,	Map<String,String> testSessionCriteriaMap,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, Long contentAreaId, 
			Long gradeCourseId, Long schoolOrgId, Boolean showExpired);

	/**
	 * @param userDetails
	 * @param studentRosterCriteriaMap
	 * @return
	 */
	Integer countAutoRegisteredTestSessions(UserDetailImpl userDetails, Long currentSchoolYear,  
			Map<String, String> testSessionCriteriaMap, Long assessmentProgramId, 
			Long testingProgramId, Long contentAreaId, 
			Long gradeCourseId, Long schoolOrgId, Boolean showExpired);

	int countAutoregisteredTestSessionForActiveRosters(UserDetailImpl userDetails,
			Map<String, String> testSessionCriteriaMap, Long userId,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long contentAreaId, Long gradeCourseId, Long schoolOrgId, Boolean showExpired);

	List<AutoRegisteredTestSessionDTO> getAutoregisteredTestSessionForActiveRosters(
			UserDetailImpl userDetails,
			String sortByColumn, String sortType, Integer offset, Integer limitCount,
			Map<String, String> testSessionCriteriaMap, Long userId,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long contentAreaId, Long gradeCourseId, Long schoolOrgId, Boolean showExpired);			

	/**
	 * 
	 * @param studentId
	 * @param gradeCourseCode
	 * @param contentAreaCode
	 * @param name
	 * @param limit
	 * @return
	 */
	List<TestSession> selectForStudentGradeSubjectAndPartialName(
			Long studentId, String gradeCourseCode, String contentAreaCode, String name, Integer limit);

	void deactivateTestSession(Long testSessionId);

	boolean fcUnenrollTestSession(long testSessionId, User user);
	
	boolean fcMidUnenrollTestSession(long testSessionId, User user);

	String getAutoPrintTestFiles(Long testsessionId);
	
	String verifyTestManagementTabAccessCriteria(User user);

	List<TestSession> getAutoRegisteredSessions(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long attendanceSchoolId, Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId);
	
	List<TestSession> getAutoRegisteredSessions(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long attendanceSchoolId, Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId,
			String testSessionPrefix);
	
	String getPredecessorStageStatus(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long attendanceSchoolId, Long currentSchoolYear, Long stageId, Long studentId, Long operationalTestWindowId);
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15738 : Test Coordination - enhance Test Information PDF functionality
     * Get media files associated to a test session.
	 * @param testsessionId
	 * @return
	 */
	Map<Long, Map<String,String>> getResourceByTestSessionId(List<Long> testsessionId);
		
	List<StudentTestSessionInfoDTO> getTestAdminExtract(Long organionId, Long currentSchoolYear, 
			boolean shouldOnlySeeRosteredStudents, Long userId, String assesmentProgramCode, int offset, int limit, List<Long> assessmentPrograms, boolean isPltw  );
		
	List<StudentTestSessionInfoDTO> getTicketDetailsExtract(Long organionId, Long currentSchoolYear, 
			boolean shouldOnlySeeRosteredStudents, Long userId, String assesmentProgramCode, int offset, int limit, List<Long> assessmentPrograms);
	
	boolean pnpUnenrollTestSession(long testSessionId, User user);
	
	/**
	 * @param testSessionName
	 * @param studentId
	 * @return
	 */
	List<Long> selectTestSessionByStudentIdAndSource(String source, Long studentId);
	
	List<TestSession> selectTestSessionByStudentIdAndSourceWithActiveOTW(String source, Long studentId, String assessmentProgramAbbrName);
	
	List<Long> selectTestSessionByStudentIdAndSourceAndAssessmentProgramWithActiveOTW(String source, Long studentId,
			Long assessmentProgramId);
	
	boolean unenrollTestSession(long testSessionId, String statusCode, Long userId);

	List<TestFormMediaResourceDTO> getTestFormMediaResource(String fromDate,
			String toDate, List<Long> assesmentProgramId, String qcStatus,String media);
	
	List<AssignScorerTestSessionDTO> getTestSessionAndStudentCountForScorer(Long stateId, 
			Long districtId, Long schoolId, Long assessmentProgramId,
			String assessmentPrgCode, Long testingProgramId,Long contentAreaId,
			Long gradeId, String sortByColumn, String sortType,
			Integer i, Integer limitCount,
			Map<String,String> testsessionRecordCriteriaMap);
	
	Integer getCountTestSessionAndStudentCountForScorer(Long stateId,Long districtId,Long schoolId,
			Long assessmentProgramId, String assessmentPrgCode, Long testingProgramId,
			Long contentAreaId, Long gradeId, String sortByColumn,
			String sortType,
			Map<String,String> testsessionRecordCriteriaMap);
	
	List<ScorerTestSessionStudentDTO> getTestSectionStudents(Long testSessionId,Long subjectId, Long schoolyear, Long gradeId);
	
	StudentTestInfo getPredecessorTestInfo(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long attendanceSchoolId, Long currentSchoolYear, Long stageId, Long studentId, Long operationalTestWindowId);

	Stage selectTestSessionStageByPrimaryKey(Long testSessionStageId);
	
	List<Stage> selectStageByContentAreaTestTypeAssessment(Long contentAreaId, Long testTypeId, Long assessmentId);
	
	List<DailyAccessCode> getDailyAccessCodes(Long assessmentProgramId, String testDate, String sortByColumn, String sortType, 
			Map<String, String> dacCriteriaMap, Integer offset, Integer limitCount, Long stateId,Boolean includeGradeBand, Long userId, Long currentSchoolyear );
	
	int getCountDailyAccessCode(Long assessmentProgramId, String testDate, Long stateId, Boolean includeGradeBand, Long userId, Long currentSchoolyear);

	List<TestSessionPdfDTO> findTestSessionTicketDetailsByIdInterim(List<Long> testSessionIds, Boolean isAutoRegistered, Boolean isPredictive,
			List<Long> studentIds, UserDetailImpl userDetails);
	
	List<TestSession> selectTestSessionByStudentIdTestCollectionSource(Long studentId, String source, Long testCollectionId);
 
	boolean transferTestSessionToNewRoster(Long testSessionId, Long newRosterId, Long modifiedUserId, Long currentEnrollmentId);
	
	List<Long> findTestSessionsInactivatedBy(String inactivationType, Long studentId, Integer schoolYear, Long attendanceSchoolId, Long gradeId);

	List<Long> findTestSessionsToDeactivateForGradeChange(Long studentId, Integer currentSchoolYearEnrollment,
			Long attendanceSchoolId, Long oldGrade, Long newGrade);

	void deactivateWithStatus(List<Long> testSessionIds, String newStatusPrefix, Long modifiedUserId);

	void deactivateForGradeChange(List<Long> testSessionIds, Long modifiedUserId);
	
	boolean doesStudentHaveTestSessionsForCurrentGrade(Long studentId, Integer currentSchoolYear, Long attendanceSchoolId, Long gradeId);

	void reactivateForGradeChange(List<Long> inactivatedTestSessionIds, Long modifiedUserId);
	
	List<AutoRegisteredTestSessionDTO> getKELPAAutoRegisteredTestSessionsByGradeCourseGradeBand(UserDetailImpl userDetails,
			Long currentSchoolYear, String sortByColumn, String sortType, Integer offset,
			Integer limitCount,	Map<String,String> testSessionCriteriaMap,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, Long contentAreaId, 
			Long gradeCourseId, Long schoolOrgId, Boolean showExpired, Boolean includeCompletedTestSession, Boolean includeInProgressTestSession);
	
	List<AutoRegisteredTestSessionDTO> getKELPAAutoregisteredTestSessionForActiveRosters(
			UserDetailImpl userDetails,
			String sortByColumn, String sortType, Integer offset, Integer limitCount,
			Map<String, String> testSessionCriteriaMap, Long userId,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long contentAreaId, Long gradeCourseId, Long schoolOrgId, Boolean showExpired);

	List<TestSession> getSessionsForAutoScoring(Long assessmentProgramId);

	List<KELPATestAdministrationDTO> getKELPATestAdministrationExtract(Long orgId, Long currentSchoolYear,
			List<Integer> assessmentPrograms);

	/**
	 * Bishnupriya Nayak :US19343 : Test Management TestSession Completion
	 * Status Display
	 */
	List<AutoRegisteredTestSessionDTO> getAutoRegisteredTestSessionsForExtendedStatus(UserDetailImpl userDetails,
			Long currentSchoolYear, String sortByColumn, String sortType, Integer offset,
			Integer limitCount,	Map<String,String> testSessionCriteriaMap,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, Long contentAreaId, 
			Long gradeCourseId, Long schoolOrgId, Boolean showExpired, Boolean includeCompletedTestSession, Boolean includeInProgressTestSession);

	List<TestSessionRoster> getTestSessionsByStudentId(
			UserDetailImpl userDetails, boolean hasViewAllPermission,
			String sortByColumn, String sortType, int offset, int limitCount,
			Map<String, String> studentRosterCriteriaMap,
			Long systemEnrollmentRuleId, Long manualEnrollmentRuleId,
			boolean hasViewHighStakesTestSessionsPermission,
			Boolean qcComplete, Long assessmentProgramId, Boolean showExpired,
			Boolean hasQCCompletePermission,
			Boolean includeCompletedTestSession,
			Boolean includeInProgressTestSession, Long studentId);
	List<TestSessionRoster> getTestSessionsByStudentIdForLCS(UserDetailImpl userDetails, boolean hasViewAllPermission,
			String sortByColumn, String sortType, int offset, int limitCount,Map<String, String> studentRosterCriteriaMap,
			Long systemEnrollmentRuleId, Long manualEnrollmentRuleId,boolean hasViewHighStakesTestSessionsPermission,
			Boolean qcComplete, Long assessmentProgramId, Boolean showExpired,Boolean hasQCCompletePermission,
			Boolean includeCompletedTestSession,Boolean includeInProgressTestSession, Long studentId);

	void deactivateLcsStudentsTestsForStudentOnly(Long studentId);

	void deactivateLcsStudentsTestsForLcsOnly(String lcsId);

	void deactivateLcsTests(Long studentId, Long testSessionId);

	void resetDLMTestlet(Long studentId, Long testSessionId, Long contentAreaId);

	List<Long> checkIsStudentPresent(String stateStudentIdentifier, Long contractingOrgId);

	Long getContentAreaIdByTestSession(Long testSessionId, Long studentId);


	List<Long> getStudentsIdsByIncompleteTestSessionId(Long testSessionId, Long currentSchoolYear);

	void deleteCurrentTest(Long testSessionId, Long studentId);

	TestSession findByPrimaryKeyWithStage(Long testSessionId);

	Boolean isLCSIdPresent(String lcsId);

	List<TestSession> findTestSessionByGradeBandContentArea(Long contentAreaId, Long gradeBandId,
			Long attendanceSchoolId, Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId, Long rosterId, String testSessionName);
	
	String getPredecessorStageStudentsTestStatus(Long contentAreaId, Long gradeBandId, Long currentSchoolYear, Long stageId, Long studentId, Long operationalTestWindowId);
	
		List<AutoRegisteredTestSessionDTO> getAutoregisteredTestSessionForActiveRostersPltw(
			UserDetailImpl userDetails,
			String sortByColumn, String sortType, Integer offset, Integer limitCount,
			Map<String, String> testSessionCriteriaMap, Long userId,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long schoolOrgId, Boolean showExpired, Boolean includeCompletedTestSession);

	
}