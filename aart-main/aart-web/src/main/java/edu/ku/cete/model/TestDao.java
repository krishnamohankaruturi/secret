/**
 * 
 */
package edu.ku.cete.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestExample;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.domain.student.Student;

/**
 * @author neil.howerton
 *
 */
public interface TestDao {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int countByExample(TestExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int deleteByExample(TestExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int insert(Test record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int insertSelective(Test record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	List<Test> selectByExample(TestExample example);

	/**
	 *
	 * @param example
	 *            {@link TestExample}
	 * @param assessmentProgramId
	 *            long
	 * @return List<Test>
	 */
	List<Test> selectByExampleAndAssessmentProgram(@Param("example") TestExample example,
			@Param("assessmentProgramId") long assessmentProgramId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	Test selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int updateByExampleSelective(@Param("record") Test record, @Param("example") TestExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int updateByExample(@Param("record") Test record, @Param("example") TestExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int updateByPrimaryKeySelective(Test record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table public.test
	 *
	 * @mbggenerated Fri Jun 22 10:27:08 CDT 2012
	 */
	int updateByPrimaryKey(Test record);

	/**
	 *
	 * @param keyword
	 *            {@link String}
	 * @param assessmentProgramId
	 *            long
	 * @return List<Test>
	 */
	List<Test> findByKeyword(@Param("keyword") String keyword, @Param("assessmentProgramId") long assessmentProgramId);

	/**
	 *
	 * @param testId
	 *            long
	 * @return {@link Test}
	 */
	Test findById(@Param("testId") long testId);

	/**
	 *
	 * @param testCollectionId
	 *            long
	 * @param testStatusId
	 *            {@link Long}
	 * @return List<Test>
	 */
	List<Test> findByTestCollectionAndStatus(@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId);

	/**
	 * 
	 * @param testCollectionId
	 * @param testStatusId
	 * @param hasQCCompletePermission
	 * @return List<Test>
	 */
	List<Test> findByTestCollectionAndStatusAndQC(@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId, @Param("qcComplete") Boolean qcComplete);

	/**
	 *
	 * @param testId
	 *            long
	 * @param testStatusId
	 *            {@link Long}
	 * @return List<Test>
	 */
	List<Test> findByTestAndStatus(@Param("testId") long testId, @Param("testStatusId") long testStatusId);

	/**
	 *
	 * @return List<Test>
	 */
	List<Test> getAll();

	/**
	 * @param testTypeCode
	 * @return
	 */
	String getTestTypeName(@Param("testTypeCode") String testTypeCode);

	/**
	 *
	 * @param testCollectionId
	 *            long
	 * @param testStatusId
	 *            {@link Long}
	 * @return List<Test>
	 */
	List<Test> findQCTestsByTestCollectionAndStatus(@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId, @Param("accessibleForm") Boolean accessibleForm,
			@Param("accessibilityFlagCode") String accessibilityFlagCode);

	List<Test> findQCTestsByTestCollectionAndStatusAndAccFlags(@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId, @Param("accessibleForm") Boolean accessibleForm,
			@Param("accessibilityFlagCode") Set<String> accessibilityFlagCode,
			@Param("accessibilityFlagCounter") int accessibilityFlagCounter);

	List<Test> findQCTestsAccFlagsByTestCollectionAndStatus(@Param("testCollectionId") Long testCollectionId,
			@Param("testStatusId") Long testStatusId, @Param("eElement") List<String> eElement,
			@Param("testSpecificationId") Long testSpecificationId);

	List<Test> findProfessionalDevelopmentTests(@Param("testingProgramCode") String testingProgramCode,
			@Param("organizationId") long organizationId);

	List<Test> findProfessionalDevelopmentTutorials(@Param("testingProgramCode") String testingProgramCode,
			@Param("organizationId") long organizationId);

	List<Test> findByTestCollectionAndStatusByAccessibleForm(@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId, @Param("accessibleForm") Boolean accessibleForm);

	/**
	 * @return
	 */
	List<Long> getAllIds();

	/**
	 * @param newTestId
	 * @return
	 */
	Integer publishRepublishingTest(@Param("newTestId") Long newTestId);

	Integer updateQCComplete(@Param("testIds") Long[] testIds, @Param("userId") Long userId);
	
	Integer removeQCComplete(@Param("testIds") Long[] testIds, @Param("userId") Long userId);

	Integer getCountOfTestCollectionsWithPerformanceTests(@Param("testId") Long testId);

	List<Test> getActiveTestByExternalIdGradeIdContentIdAssessmentId(@Param("testId") Long testId,
			@Param("gradeCourseAbbreviatedName") String gradeCourseAbbreviatedName,
			@Param("contentAreaId") Long contentAreaId, @Param("assessmentId") Long assessmentId, @Param("testingProgramName") String testingProgramName);

	List<StudentReportTestResponses> getKelpaTestsScoreByStudentIdExternalTestIds(@Param("studentId") Long studentId,
			@Param("externalTestIds") List<Long> externalTestIds, @Param("enrollmentIds") List<Long> enrollmentId,
			@Param("testsStatusIds") List<Long> testsStatusIds,
			@Param("specialCircumtanceStatusIds") List<Long> specialCircumstanceStatusIds, @Param("stateId") Long contractOrgId);

	
	List<StudentReportTestResponses> getTestsScoreByStudentIdExternalTestIds(@Param("studentId") Long studentId,
			@Param("externalTestIds") List<Long> externalTestIds, @Param("enrollmentIds") List<Long> enrollmentId,
			@Param("testsStatusIds") List<Long> testsStatusIds,
			@Param("specialCircumtanceStatusIds") List<Long> specialCircumstanceStatusIds, @Param("stateId") Long contractOrgId, @Param("scoringStatusCompletedId") Long scoringStatusCompletedId);

	Test findLatestByExternalId(@Param("externalId") Long externalId);

	Long mapExternalId(@Param("externalId") Long externalId);

	List<Long> getPanelTestsFromThetaValues(@Param("studentId") Long studentId,
			@Param("previousTestSessionId") Long previousTestSessionId, @Param("panelId") Long panelId);

	List<Test> findThetaBasedTestsByTestCollectionAndStatusAndAccFlags(@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId, @Param("accessibleForm") Boolean accessibleForm,
			@Param("accessibilityFlagCode") Set<String> accessibilityFlagCode,
			@Param("accessibilityFlagCounter") int accessibilityFlagCounter,
			@Param("testsBasedOnTheta") List<Long> testsBasedOnTheta);

	List<Test> findTestsForDLMFixedAssign(@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId);

	/*
	 * List<Test> getInterimTest(@Param("gradeCourseId") Long
	 * gradeCourseId, @Param("contentAreaId") Long contentAreaId);
	 */

	List<Test> getInterimTest(@Param("contentAreaId") Long contentAreaId, @Param("gradeCourseId") Long gradeCourseId,
			@Param("isInterim") Boolean isInterim, @Param("createdBy") String createdBy,
			@Param("schoolName") String schoolName, @Param("contentCode") String contentCode,
			@Param("purpose") Long purpose, @Param("testName") String testName,
			@Param("organizationId") Long organizationId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("schoolYear") Long schoolYear);

	List<String> getRosterName(@Param("userId") Long userId, @Param("currentSchoolYear") Long currentSchoolYear,
			@Param("organizationId") Long organizationId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("interimTestId") Long interimTestId);

	List<String> getRosterSubject();

	List<String> getRosterGrade(@Param("organizationId") Long organizationId, @Param("userId") Long userId,
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("assessmentProgramId") Long assessmentProgramId);

	List<Student> getStudentinfo(@Param("rosterIds") List<Long> rosterIds, @Param("gradeCourseIds") List<Long> gradeCourseIds,
			@Param("organizationIds") List<Long> orgIds,@Param("userIds") List<Long> userList,
			@Param("schoolYear") Long schoolYear,@Param("assessmentProgramId") Long assessmentProgramId);

	
	int insertInterim(Test testCreate);

	List<Student> getStudentinfoInterim(@Param("rosterId") Long rosterId, @Param("contentAreaId") Long contentAreaId,
			@Param("gradeCourseId") Long gradeCourseId, @Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType, @Param("offset") int offset, @Param("limitCount") int limitCount,
			@Param("criteria") Map<String, Object> criteria);

	int testCountDetails(Long rosterId, Long contentAreaId, Long gradeCourseId, Map<String, Object> criteria);

	List<Test> getMiniTestsByInterimTestId(@Param("interimTestId") Long interimTestId);

	void softDeleteByPrimaryKey(Long id);

	List<String> getRosterNameByUserAndOrgList(@Param("userIds") List<Long> userList,
			@Param("organizationId") Long organizationId, @Param("currentSchoolYear") Long currentSchoolYear,
			@Param("assessmentProgramId") Long assessmentProgramId);

	Long getTestStaus(@Param("selectedTestId") Long selectedTestId );

	List<Test> findTestsForDLMResearchSurvey(@Param("testCollectionId")Long testCollectionId,
			@Param("testStatusId")Long testStatusId);

	int getCountOfTestCollectionsWithCorrectStageTests(@Param("contentAreaId") Long contentAreaId, @Param("gradeAbbrName") String gradeAbbrName, 
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("externalTestId") Long externalTestId, @Param("stageCode") String stageCode);

	Long getTestIdBySchoolYearAndExternalId(@Param("schoolYear") Long schoolYear, @Param("externalTestId") Long externalTestId);
	
	List<String> getSubjectNameByUserAndOrgList(@Param("userIds") List<Long> userList,
			@Param("organizationId") Long organizationId, @Param("currentSchoolYear") Long currentSchoolYear,
			@Param("assessmentProgramId") Long assessmentProgramId);

	List<ContentArea> getSubjectNamesByRosterAndOrgListInterim(@Param("organizationIds") List<Long> orgIds, @Param("currentSchoolYear") Long currentSchoolYear,
			@Param("teacherId") Long teacherId,@Param("predictiveStudentScore") Boolean predictiveStudentScore,@Param("assessmentProgramId") Long assessmentProgramId);

	List<GradeCourse> getGradesBySubjectsAndOrgList(@Param("organizationIds") List<Long> orgIds,
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("subjectIds") List<Long> subjectIds,
			@Param("teacherId") Long teacherId, @Param("predictiveStudentScore") Boolean predictiveStudentScore,
			@Param("assessmentProgramId") Long assessmentProgramId);

	List<Student> getStudentDetailsByGradeAndSubjectAndRoster(@Param("organizationIds") List<Long> orgIds, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("subjectIds") List<Long> subjectIds, @Param("gradeIds")  List<Long> gradeIds,
			@Param("teacherId") Long teacherId, @Param("predictiveStudentScore")Boolean predictiveStudentScore,@Param("assessmentProgramId") Long assessmentProgramId);

	List<Test> getPredictiveTestsInCollection(@Param("testCollectionId") Long testCollectionId, @Param("testStatusId") Long publishedTestStatusId);
	
	List<StudentReportTestResponses> getPredictiveTestsScoreByStudentIdExternalTestIds(@Param("studentId") Long studentId,
			@Param("externalTestIds") List<Long> externalTestIds, 
			@Param("enrollmentIds") List<Long> enrollmentId,
			@Param("testsStatusIds") List<Long> testsStatusIds);
	
	List<Long> getAllPublishedTestByExternalId(@Param("externalTestId") Long externalTestId, @Param("deployedStatusId") Long deployedStatusId);
	
	List<Test> gtAllQCTestsAccFlagsByTestCollectionIdAndTestStatus(@Param("testCollectionId") Long testCollectionId, @Param("testStatusId") Long testStatusId);

	List<Test> findQCTestsByTestCollectionAndStatusAndAccFlagsForPLTW(
			@Param("testCollectionId") long testCollectionId,
			@Param("testStatusId") long testStatusId,
			@Param("accessibleForm") Boolean accessibleForm,
			@Param("accessibilityFlagCode") Set<String> accessibilityFlagCode,
			@Param("accessibilityFlagCounter") int accessibilityFlagCounter,
			@Param("operationalTestWindowId") Long operationalTestWindowId,
			@Param("stagePredecessorId") Long stagePredecessorId,
			@Param("studentId") Long studentId);
	
}
