/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import edu.ku.cete.domain.RubricReportDto;
import edu.ku.cete.domain.content.Foil;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.content.TestExample;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.lm.LmNode;
import edu.ku.cete.domain.report.StudentReportTestResponses;

/**
 * @author neil.howerton
 *
 */
public interface TestService {

    /**
     *
     *@param example {@link edu.ku.cete.domain.content.TestExample}
     *@return List<Test>
     */
    @PreAuthorize("hasRole('PERM_TEST_SEARCH')")
    List<Test> selectByExample(TestExample example);

    /**
     *
     *@param example {@link TestExample}
     *@param assessmentProgramId long
     *@return List<Test>
     */
    @PreAuthorize("hasRole('PERM_TEST_SEARCH')")
    List<Test> selectByExampleAndAssessmentProgram(TestExample example, long assessmentProgramId);

    /**
    *
    *@param keyword {@link String}
    *@param assessmentProgramId long
    *@return List<Test>
    */
    @PreAuthorize("hasRole('PERM_TEST_SEARCH')")
   List<Test> findByKeyword(String keyword, long assessmentProgramId);

   /**
    *
    *@param testId long
    *@return {@link Test}
    */
   Test findById(Long testId);

   List<TestSection> findTestSectionByTest(Long testId);
   
   /**
    *
    *@param testCollectionId long
    *@return List<Test>
    */
   List<Test> findByTestCollectionAndStatus(long testCollectionId, long statusId);
   /**
   *
   *@param testCollectionId long
   *@param testId long
   *@return Test
   */
  TestCollection findContentByTestCollectionAndStatus(long testCollectionId, long testId, long statusId);
   /**
    *
    *@return List<Test>
    */
   List<Test> getAll();
	
	/**
	 * @param testId
	 * @return
	 */
	Test findTestAndSectionById(Long testId);
	
	/**
	 * @param testCollectionId
	 * @param statusId
	 * @return
	 */
	TestCollection findNodeInfoByTestCollectionAndStatus(long testCollectionId, long statusId);
	
	/**
	 * 1. sets the lm nodes.
	 * 2. returns the test collection.
	 * @param testCollection test collection with tests, context stimulus and task variants set on it.
	 * @param nodeKeys
	 * @return
	 */
	TestCollection findNodeInformation(TestCollection testCollection,
			Set<String> nodeKeys);
	/**
	 * Find the available node information for the given set of nodeIds
	 * @param nodeKeys
	 * @return
	 */
	Set<LmNode> findNodeInformation(Set<String> nodeKeys);
	
	Set<LmNode> findNodeInformationForNodeResponse(Set<String> nodeKeys);
	
	/**
	 * @param testTypeCode
	 * @return
	 */
	String getTestTypeName(String testTypeCode);
	
	/**
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Test record);
	
	/**
	 * @param testCollectionId
	 * @param statusId
	 * @return
	 */
	List<Test> findQCTestsByTestCollectionAndStatus(
				long testCollectionId, long statusId,
				String variantTypeCode, Boolean accessibleForm, String accessibilityFlagCode);
	
	List<Test> findQCTestsByTestCollectionAndStatusAndAccFlags(
			long testCollectionId, long statusId,
			String variantTypeCode, Boolean accessibleForm, Set<String> accessibilityFlagCode);
	
	List<Test> findQCTestsAccFlagsByTestCollectionAndStatus(Long testCollectionId, Long statusId, List<String> eElements, Long testSpecificationId);
	
	List<Test> findProfessionalDevelopmentTests(@Param("testingProgramCode") String testingProgramCode, Long organizationId);
	
	List<Test> findProfessionalDevelopmentTutorials(@Param("testingProgramCode") String testingProgramCode, Long organizationId);
	
	/**
	 * @return
	 */
	List<Long> getAllIds();
	
	/**
	 * @param newTestId
	 * @return
	 */
	Integer publishRepublishingTest(Long newTestId);
	
	List<TestSection> getTestSectionsWithMaxNumberOfItemsByTestSessionId(Long testSessionId);
	
	Integer updateQCComplete(Long[] testIds, Long userId);
	
	Integer removeQCComplete(Long[] testIds, Long userId);
	
	String getTaskTypeCodeByTaskVariant(Long taskVariantId);
	
	String getTaskTypeCode(Long taskTypeId);
	
	TaskVariant getTaskVariantById(Long taskVariantId);
	
	List<RubricReportDto> getRubricByTaskVariant(Long taskVariantId);
	
	List<String> getTaskVariantAndSectionByTestId(@Param("testId") Long testId, 
    		@Param("includeSectionName") boolean includeSectionName);
	
	List<Long> getTaskVariantsByTestId(@Param("testId") Long testId);
	
	String getTaskLayoutFormatLettersById(long id);
	
	Integer getTaskVariantFoilOrder(long taskVariantId, long foilId);

	List<TaskVariant> getTaskVariantByExternalIdGradeIdContentId(Long contentAreaId, String gradeCourseAbbreviatedName, Long assessmentProgramId, Long externalId, String testingProgramName);

	List<Test> getActiveTestByExternalIdGradeIdContentIdAssessmentId(Long contentAreaId, String gradeCourseAbbreviatedName, Long assessmentProgramId,Long testId, String testingProgramName);

	
	Test findLatestByExternalId(Long testExternalId);
	
	List<TaskVariant> findTaskVariantsInTestSection(Long testSectionId);
	
	List<Foil> getFoilsForTaskVariant(Long id);

	String getTaskTypeCodeByTaskVariantExternalIdAndTestExternalId(Long tvExternalId,
			Long testExternalId);

	List<StudentReportTestResponses> getKelpaTestsScoreByStudentIdExternalTestIds(
			Long studentId, List<Long> externalTestIds, List<Long> enrollmentIds, List<Long> testsStatusIds, List<Long> specialCircumstanceStatusIds, Long contractOrgId);
	
	List<StudentReportTestResponses> getTestsScoreByStudentIdExternalTestIds(
			Long studentId, List<Long> externalTestIds, List<Long> enrollmentIds, List<Long> testsStatusIds, List<Long> specialCircumstanceStatusIds, Long contractOrgId, Long scoringStatusCompletedId);
	
	List<Long> getPanelTestsFromThetaValues(Long studentId, Long previousTestSessionId, Long panelId);
	
	List<Test> findPanelStageTestsByTestCollectionAndStatusAndAccFlags(
			long testCollectionId, long statusId,
			String variantTypeCode, Boolean accessibleForm, Set<String> accessibilityFlagCode, List<Long> testsBasedOnTheta);

	List<Test> findTestsForDLMFixedAssign(long testCollectionId, long testStatusId);
	
	boolean isTestIdFromTestCollectionsWithPerformanceTests(Long testId);
	
/*	List<Test> getInterimTest(Long gradeCourseId, Long contentAreaId);
*/
	int createTest(Test testCreate);

	int createInterimTest(Test testCreate);

	void softDeleteById(Long id);

	List<TaskVariant> getPromptAndStudentResponse(Long[] taskVariantId,Long studentId, Long studentsTestsId);

	List<TaskVariant> getItemsWithPositionByTestId(Long testId);

	int findCountScorableTaskVariants(Long testId, Long ccqScoreId);
	
	Long getTestStaus(Long selectedTestId);

	List<Test> findTestsForDLMResearchSurvey(Long testCollectionId, Long testStatusId);

	boolean isTestIdFromTestCollectionsWithCorrectStageTests(Long subjectId, String gradeAbbrName, Long assessmentProgramId, Long testId,
			String stageCode);

	Long getTestIdBySchoolYearAndExternalId(Long schoolYear, Long testId);
	
	List<Test> findPredictiveTestsInTestCollection(Long testCollectionId, Long publishedTestStatusId);
	
	List<StudentReportTestResponses> getPredictiveTestsScoreByStudentIdExternalTestIds(
			Long studentId, List<Long> externalTestIds, List<Long> enrollmentIds, List<Long> testsStatusIds);
	
	List<Long> getAllPublishedTestByExternalId(Long externalTestId, Long deployedStatusId);
	
	List<Test> gtAllQCTestsAccFlagsByTestCollectionIdAndTestStatus(Long testCollectionId, Long statusId);

	List<Test> findQCTestsByTestCollectionAndStatusAndAccFlagsForPLTW(Long testCollectionId, Long statusId,
			String variantTypeCode, Boolean accessibleForm,
			Set<String> accessibilityFlagsParam, Long operationalTestWindowId, Long stagePredecessorId,
			Long studentId);
	
}
