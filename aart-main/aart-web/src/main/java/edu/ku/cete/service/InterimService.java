package edu.ku.cete.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;

import edu.ku.cete.controller.InterimTestDTO;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.interim.InterimTest;
import edu.ku.cete.domain.interim.StudentActivityReport;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.interim.InterimPredictiveStudentScore;

/**
 * @author venkat
 *
 */
public interface InterimService {

	/**
	 * This will be used to create Interim Test, which does most of the work
	 * including test, testsection,
	 * testsectionrules,testsectiontools,testsectiontaskvariants with new
	 * Interim Test ID.
	 * 
	 * @param interimTest
	 *            this should contain the TestID's as well for the Interim Test.
	 * @return The Generated Id for Interim Table.
	 */
	public InterimTest createTest(InterimTest interimTest);

	/**
	 * This method will be used get the Interim Test based on the ID.
	 * 
	 * @returns Interim Test Object with Mini Tests
	 */
	public InterimTest getInterimTest(Long interimTestId);

	/**
	 * This method will be used get the Interim Tests based on the Selection
	 * Criteria.
	 * 
	 * @param schoolName
	 * @param createdBy
	 * 
	 * @returns List of Interim Tests Matching the Criteria
	 */

	/**
	 * This method will be used get the Interim Tests based on the Logged In
	 * User.
	 * 
	 * @returns List of Interim Tests for the Logged In User
	 */
	public List<InterimTest> getInterimTestsByUser(long userid);

	public List<Student> getStudentInfo(Long rosterId, Long contentAreaId, Long gradeCourseId, String sortByColumn,
			String sortType, int i, int limitCount, Map<String, Object> criteria);

	public List<String> getRosterName(Long userId, Long currentSchoolYear, Long organizationId, Long assessmentProgramId, Long interimTestId);

	public List<String> getRosterSubject();

	public List<String> getRostergrade(Long organizationId, Long userId, Long currentSchoolYear, Long assessmentProgramId);

	public InterimTest updateTest(InterimTest interimTest);

	public void deleteInterimTestTestByInterimTestID(Long interimTestId);

	public void softDeleteInterimTestByInterimTestID(Long interimTestId);

	public List<InterimTestDTO> getTotalTests(Long organizationId, String sortByColumn, String sortType, int offset,
			int limitCount, Map<String, Object> criteria , Long schoolYear);

	public Long createTestCollection(String name, Date date, Long currentContextUserId, Long currentContextUserId2,
			Date date2, Long gradeCourseId, Long contentAreaId, String string, String string2);

	public InterimTest createInterimTest(Long[] testIdArray, String name, String description, Long gradeCourseId,
			Long contentAreaId, Long currentContextUserId, Long currentContextUserId2, Date date, Date date2,
			Long createdTestId, String displayName, Long testCollectionId, Long orgId,Boolean isCopy,Long currentSchoolYear);

	public void createInterimTestCollectionTests(Long createdTestCollectionId, Long createdTestId, Date date,
			Long currentContextUserId, Long currentContextUserId2, Date date2);


	public List<Long> createTestSection(Date createdDate, Long createdUser, Long modifiedUser, Date modifiedDate,
			Long createdTestId, Boolean hardBreak, int sectionOrder, String testSectionName, Long[] testIdArray);

	public Long createTest(String name, Long assessmentId, String originationCode, String uiTypeCode, Long status,
			Long gradeCourseId, Long contentAreaId, Date createdDate, Long createdUser, Long modifiedUser,
			Date modifiedDate, Boolean isInterimTest, Boolean qcComplete,Long testspecificationid);

	public String deleteInterimTest(Long interimTestId, Long testSessionId);

	public Long getInterimTestsCount(Long long1);

	public Long insertTestSession(Long interimTestId, Long testCollectionId, User user, String testName,Long schoolYear);

	public void insertStudentTestsRecords(Long testSessionId, List<Long> studentIds, Long testId, Long testCollectionId,
			Long interimTestId, Long schoolYear);

	public int getStudentCount(Long rosterId, Long contentAreaId, Long gradeCourseId, Map<String, Object> criteria);

	public int testCountDetails(Map<String, Object> criteria);

	public List<Student> getStudentInfoNew(List<Long> rosterId, List<Long> gradeCourseId, List<Long> orgIds,
			List<Long> userList, Long schoolYear,Long assessmentProgramId);

	public List<TestSession> getInterimTestSession(Long testTestId);

	public List<TestSession> getTestSessionByTestID(Long testId);

	public List<Student> getStudentId(Long testSessionId);

	public InterimTest getInterimTestByTestTestId(Long testId);

	public List<InterimTest> getTestsForReports(Long id);


	public List<Test> getMiniTestsByInterimTestId(Long interimTestId);


	public List<Student> getStudentIdByTestsessionId(Long testSessionId);

	List<Test> getMiniTests(Long purpose, Long contentAreaId, Long gradeCourseId, Boolean isInterim, String createdBy,
			String schoolName, String contentCode, String testName, Long organizationId, Long assessmentProgramId,
			Long schoolYear);

	public void updateTestsession(Long testSessionId);


	public TestSession getTestSessionDetailsByTestSessionId(Long testSessionId);


	public TestSession getTestSessionByTestSessionId(Long testSessionId);

	List<TaskVariant> getQuestionsForScoring(Long testTestId);

	public List<String> getSchoolNames(Long contractingOrgId, Long schoolYear);

	List<Student> getStudentIdByTestsessionIdInterim(Long testSessionId);

	public List<Category> selectTestPurposeForInterim(Boolean isInterim, Long organizationId, Long assessmentProgramId,
			Long schoolYear);

	public Boolean isTestNameUnique(String testName, Long currentContextUserId,Long schoolYear, Long orgId);

	public List<InterimTestDTO> getTotalTestsForTeacher(User user, Long schoolYear, Boolean forReports, Long assessmentProgramId);

	List<InterimTestDTO> getTotalTests(List<Long> userIds, Long organizationId, Long schoolYear, Boolean forReports, Long assessmentProgramId);

	public List<String> getRosterNameByUserAndOrgList(List<Long> userList, Long organizationId, Long currentSchoolYear, Long assessmentProgramId);

	List<Student> getStudentIdByTestsessionIdInterim(Long testSessionId, Long orgId, Long userId, Boolean isTeacher,Long currentSchoolYear);

	public int updateInterim(String name, Long interimTestId, Long testCollectionId, Long testTestId, Long testSessionId);

	public void cleanUpOldTest(InterimTest interimTest);

	public InterimTestDTO getTotalTestSessionDetails(Long testSessionId, Long assessmentProgId);

	public void softDeleteStudentsTestsByTestSessionId(Long testSessionId, List<Long> studentIds, Long userId);

	public Integer suspendTestWindow(Boolean suspend, Long testSessionId);
	
	public Long insertAutoAssignInterim(List<Long> gradeCourseIds, List<Long> contentAreaIds, List<Long> rosterIds, Long testSessionId);

    public List<String> getSubjectNameByUserAndOrgList(List<Long> userList, Long organizationId, Long schoolYear, Long assessmentProgramId);

	public List<Long> getStudentsForAutoAssign(List<Long> rosterIds, List<Long> gradeCourseIds,
			List<Long> contentAreaIds, List<Long> orgIds, List<Long> userList, Long schoolYear, Long assessmentProgramId);
	
	Long getTestCollectionId(Long testId);

	public List<StudentActivityReport> getStudentReportActivityDetails(List<Long> studentIds, List<Long> orgIds, Long schoolYear, Boolean isPLTWUser, Boolean isTeacher, Long userId);


	public List<ContentArea> getSubjectNamesByRosterAndOrgList(List<Long> orgIds,
			Long currentSchoolYear, Long teachetId, Boolean predictiveStudentScore, Long assessmentProgramId);




	void autoAssignKIDSInterim(Enrollment enrollment, Roster roster);

	public void exitStudentRosterKIDSInterim(Enrollment enrollment, Roster roster) ;

	public void exitStudentKIDSInterim(Enrollment enrollment, Roster roster);

	public ByteArrayOutputStream generatePredictiveBundleByTestsessionAndUser(
			Long testSessionId, Boolean isTeacher, Long userId, Long currentSchoolYear,  Long organizationId);

	public int reportCountByTestsessionAndUser(Long testSessionId,
			Boolean isTeacher, Long userId, Long currentSchoolYear,
			Long organizationId);

	public InterimTest createInterimTestandTestSection(String name,
			String description, Long gradeCourseId, Long contentAreaId,
			Long testspecificationid, User user, Long testCollectionId,
			Long[] testIdArray, Long organizationId, boolean isTestCopied);


	public List<Organization> getInterimSchoolsInDistrict(Long districtId, Long currentSchoolYear, Boolean predictiveStudentScore, Long assessmentProgramId);

	public List<Organization> getInterimDistrictsInState(Long stateId,Long schoolYear, Boolean predictiveStudentScore, Long assessmentProgramId);


	public List<InterimPredictiveStudentScore> getInterimPredictiveStudentScores(List<Long> studentIds,
			List<Long> orgIds, Long schoolYear, List<Long> contentAreaIds, List<Long> gradeCourseIds, 
			Long assessmentProgramId, Boolean isTeacher, Long userId);

	List<GradeCourse> getGradesBySubjectsAndOrgList(List<Long> orgIds, Long currentSchoolYear, List<Long> subjectIds,
			Long teacherId, Boolean predictiveStudentScore, Long assessmentProgramId);

	List<Student> getStudentDetailsByGradeAndSubjectAndRoster(List<Long> orgIds, Long currentSchoolYear,
			List<Long> subjectIds, List<Long> gradeIds, Long teacherId, Boolean predictiveStudentScore,
			Long assessmentProgramId);

	public ByteArrayOutputStream getInterimPredictiveQuestionCSVBytestsessionAndUser(Long testSessionId,
			Boolean isTeacher, Boolean districtUser, Long userId, Long currentSchoolYear, Long organizationId, HttpHeaders headers);

	public Collection<? extends InterimTestDTO> getTotalTestsForOrgs(List<Long> userList, Long schoolYear,
			Long assessmentProgId, List<Long> orgIds);
	
	public List<Long> unassignUnusedStudentsTestsByInterimTestId(Long interimTestId);

	void updateById(InterimTest interimTest);
}
