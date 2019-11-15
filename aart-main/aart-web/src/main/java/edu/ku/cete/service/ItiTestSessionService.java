package edu.ku.cete.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.ItiMCLog;
import edu.ku.cete.domain.ItiMCLogExample;
import edu.ku.cete.domain.ItiStudentReport;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.ItiTestSessionResourceInfo;
import edu.ku.cete.domain.ItiTestSessionSensitivityTags;
import edu.ku.cete.domain.SensitivityTag;
import edu.ku.cete.domain.StudentItemInfoForMC;
import edu.ku.cete.domain.StudentNodeProbability;
import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.report.roster.ItiRosterReportEE;
import edu.ku.cete.web.IAPContentFramework;

public interface ItiTestSessionService {
	ItiTestSessionHistory selectByPrimaryKey(Long id);
	List<ItiTestSessionResourceInfo> getTestResources(long testSessionId);
	List<StudentItemInfoForMC> getStudentItemInfoForMC(Date fromDate, Date toDate);
	List<ItiTestSessionSensitivityTags> getItiHistorySensitivityTags(long itiTestSessionHistoryId);
	int addOrUpdateItiSensitivityTags(long itiTestSessionHistoryId, List<Long> sensitivityTags);
	List<ItiTestSessionHistory> selectTestidSensitivityTag(List<Long> testIds);
	List<Test> filterTestIdsOnSensitivityTags(List<Test> tests, List<Long> sensitivityTags);
	List<SensitivityTag> getSensitivityTags(long contentareaId, String essentialElement, String gradeCourseCode);
    int addItiMClog(ItiMCLog itiMcLog);
    int addStudentNodeProbability(StudentNodeProbability studentNodeProbability);
    int updateStudentNodeProbability(StudentNodeProbability studentNodeProbability);
    int updateSelectiveItiMClog(ItiMCLog itiMCLog, ItiMCLogExample itiMCLogExample);
	List<Long> filterTestIdsOnPNP(List<Long> testIds, String pnpAttribute);
	Map<String, Object> processDLMStudentsToTest(String testCollectionName, long testCollectionId, 
    		long rosterId, long studentEnrlRosterId, String action, String source, String linkageLevel, String[] sensitivityTags, 
    		long studentId, String levelDesc, String eElement, String claim, String conceptualArea, long essentialelementid);
	boolean addItiTestSessionHistory(Long testSessionId,String essentialElement, String linkageLevel, String levelDesc);
	String checkDuplicateTestsSessionExists(long studentId, String level, long rosterId, long essentialElementId);
	boolean confirmHistory(ItiTestSessionHistory record);
	boolean addOrUpdateSaveHistory(ItiTestSessionHistory record, long pendingStatus, List<Long> sensitivityTags);
	List<ItiTestSessionHistory> getStudentHistory(long studentId, long rosterId);
	ItiTestSessionHistory selectHistoryTagsByItiSessionHistoryId(long itiTestSessionHistoryId);
	List<ItiStudentReport> selectByStudentIdAndUnUsedStatus(Long studentId);
	List<ItiStudentReport> selectByStudentIdAndUnUsedStatusAndSubject(Long studentId, Long subjectId, int currentSchoolYear, Long testCycleID);
	
	/*Map<String, Map<String, String>> getDLMRosterData(Long userSelectedOrganizationId, 
			Long rosterId, List<Long> studentIds);*/
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get needed data to build the Roster Report for DLM organizations.
	 * @param userSelectedOrganizationId
	 * @param rosterId
	 * @param studentIds
	 * @param contentAreaId
	 * @return
	 */
	List<ItiRosterReportEE> getITIDLMEEDetailsByRosterIdAndStudents(Long userSelectedOrganizationId, 
			Long rosterId, List<Long> studentIds, Long contentAreaId, int currentSchoolYear);
	int inactivatePendingITISession(long itiTestSessionHistoryId, long userId);
		
	int cancelITIHistoryAndStudentTests(long itiTestSessionHistoryId, long testSessionId, long studentId);	
	void unenrollITIPlansByStudentAndTestSessionId(Long studentId, String unEnrollCategroyCode, Long testSessionId,
			Long modifiedUserId);
	void unEnrollPendingITIsByEnrollmentId(Long enrollmentId, String unEnrollCategoryCode);
	void unEnrollPendingITIsByEnrlAndRosterId(Long enrollmentId, Long rosterId, String rosterUnEnrollCode, Long modifiedUserId);
	
	void transferITIsToNewRosterByEnrlAndOldRosterId(List<ItiTestSessionHistory> pendingITIPlansOfTheOldRoster, Long enrollmentId, Long newRosterId, Long modifiedUserId);
	List<ItiTestSessionHistory> getUnenrolledITIsByEnrlAndRosterId(Long enrollmentId, Long rosterId);
	List<ItiTestSessionHistory> getPendingITIsByEnrlAndRosterId(Long enrollmentId, Long rosterId);
	List<ItiTestSessionHistory> getITIPlansExceptPendingUsingEnrlAndRosterId(Long enrollmentId, Long oldRosterId);
	
	List<Long> findInactivatedITIPlansByStudentAndTestSession(String inactivationType, Long studentId, List<Long> inactivatedTestSessionIds);
	List<Long> findInactivatedPendingITIPlansByStudent(String inactivationType, Long studentId, Long gradeCourseId);
	List<Long> findITIPlansByStudentAndTestSessionForDeactivation(Long studentId, List<Long> inactivatedTestSessionIds);
	List<Long> findPendingITIPlansByStudentForDeactivation(Long studentId, Long oldGradeLevel);
	void deactivateForGradeChange(List<Long> itiPlanIdsForDeactivation, Long modifiedUserId);
	void deactivateWithStatus(List<Long> testSessionIds, String newStatusPrefix, Long modifiedUserId);
	void reactivateForGradeChange(List<Long> itiPlanIdsForDeactivation, Long modifiedUserId);
	ItiTestSessionHistory selectITISessionHistoryByStudentIdAndTestSessionId(Long studentId, Long testSessionId);
	ItiTestSessionHistory getMostRecentlyCompletedNonWringITITestSession(Long studentId, Long contentAreaId, String gradeCourseAbbrName, Long currentSchoolYear);
	
	List<ItiTestSessionHistory> getForIAP(Long studentId, Long rosterId, Long enrollmentsRostersId,
			Long operationalTestWindowId);
	
	List<ItiTestSessionHistory> getForIAPByContentFrameworkDetailIdAndLinkageLevel(Long studentId, Long rosterId, Long enrollmentsRostersId,
			Long operationalTestWindowId, Long contentFrameworkDetailId, String linkageLevel);
	
	ItiTestSessionHistory insertITISelective(Long studentId, Long enrollmentsRostersId, Long rosterId,
			IAPContentFramework iapCF, String linkageLevel, String linkageLevelDesc, Long otwId);
	int updateByPrimaryKeySelective(ItiTestSessionHistory record);
	
	List<TestCollection> getEligibleTestCollectionsForEE(Long studentId, Long rosterId, Long contentAreaId,
			String gradeCourseCode, Long contentFrameworkDetailId, String linkageLevel);
	Map<String, Object> finishPlan(Long studentId, Long contentAreaId, String gradeCourseCode, Long rosterId,
			Long itiId, boolean assignTestlet);
	Map<String, Object> assignIAPTestlet(String testCollectionName, long testCollectionId, long rosterId,
			long studentEnrlRosterId, String linkageLevel, long studentId, Long contentAreaId, String levelDesc,
			String eElement, String claim, String conceptualArea, long essentialelementid);
	int endTestletForITI(ItiTestSessionHistory iti);
	
	List<ItiTestSessionResourceInfo> getTestResourceByTestSessionHistoryId(ItiTestSessionHistory iti);

	List<StudentTestResourceInfo> getBrailleResourceByTestSessionHistoryId(ItiTestSessionHistory iti);
}
