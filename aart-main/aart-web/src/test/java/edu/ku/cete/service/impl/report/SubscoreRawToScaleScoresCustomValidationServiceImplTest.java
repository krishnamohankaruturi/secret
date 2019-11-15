/**
 * 
 */
package edu.ku.cete.service.impl.report;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;
import edu.ku.cete.test.BaseTest;

/**
 * @author n466k239
 *
 */
public class SubscoreRawToScaleScoresCustomValidationServiceImplTest extends BaseTest{

	final static Log logger = LogFactory.getLog(SubscoreRawToScaleScoresCustomValidationServiceImplTest.class);

	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestService testService;
	
	@Test
	public void testSchoolYear() {
		SubscoreRawToScaleScores subscoreRawToScaleScores1 = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6"));
		SubscoreRawToScaleScores subscoreRawToScaleScores2 = getSubscoreRawToScaleScoresDataObject("3", 2017L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6"));
		assertNotEquals("All school years in the file are not the same school year.", subscoreRawToScaleScores1.getSchoolYear(), subscoreRawToScaleScores2.getSchoolYear());
	}
	
	@Test
	public void testSchoolYearNotNull() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", null, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6"));
		assertNull("Value not specified for field School_Year", subscoreRawToScaleScores.getSchoolYear());
		
	}
	
	@Test
	public void testAssessmentProgramNotNull() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, null, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6"));
		assertNotNull("Value not specified for field Assessment_Program", subscoreRawToScaleScores.getAssessmentProgram());
		
	}
	
	@Test
	public void testSubjectNotNull() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, null, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6"));
		assertNull("Value not specified for field Subject", subscoreRawToScaleScores.getSubjectId());
		
	}
	
	
	@Test
	public void validateAssessmentProgram() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6"));
		String assessmentProgramCodeOnUI = "AMP";
		assertNotEquals("Assessment program is invalid", assessmentProgramCodeOnUI, subscoreRawToScaleScores.getAssessmentProgram());
	}
		
	@Test
	public void validateSubject() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 440L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6"));
		String subjectIdOnUI = "ELA";
		assertEquals("Subject is invalid", subjectIdOnUI, subscoreRawToScaleScores.getSubject());
	}
	
	@Test
	public void testSubscoreDefinitionNameNotNull() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, null, new BigDecimal("17"), 1, new BigDecimal("0.6"));
		assertNull("Value not specified for field SubscoreDefinitionName", subscoreRawToScaleScores.getSubScoreDefinitionName());
	}
	
	@Test
	public void testSubscoreRatingNotNull() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), null, new BigDecimal("0.6"));
		assertNull("Value not specified for field Rating", subscoreRawToScaleScores.getRating());
	}
	
	@Test
	public void testSubscoreRawScoreNotNull() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", null, 1, new BigDecimal("0.6"));
		assertNull("Value not specified for field RawScore", subscoreRawToScaleScores.getRawScore());
	}
	
	@Test
	public void testSubscoreMinimumPercentResponsesNotNull() {
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, null);
		assertNull("Value not specified for field MinimumPercentResponses", subscoreRawToScaleScores.getMinimumPercentResponses());
	}
	
	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreRawToScaleScoresCustomValidationServiceImpl#customValidation(org.springframework.validation.BeanPropertyBindingResult, java.lang.Object, java.util.Map, java.util.Map)}.
	 */
	@Test
	public void testCustomValidation() {
		//fail("Not yet implemented");
		SubscoreRawToScaleScores target = new SubscoreRawToScaleScores();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "KAP");
		params.put("subjectCodeOnUI", "ELA");
		params.put("assessmentProgramIdOnUI", (Long) 12L);
		params.put("subjectIdOnUI", (Long) 3L);
		
		Map<String, String> mappedFieldNames = new HashMap<String, String>();
		mappedFieldNames.put("schoolYear", "School_Year");
		mappedFieldNames.put("assessmentProgram", "Assessment_Program");
		mappedFieldNames.put("subject", "subject");
		mappedFieldNames.put("grade", "Grade");
		mappedFieldNames.put("testId1", "TestID_1");
		mappedFieldNames.put("testId2", "TestID_2");
		mappedFieldNames.put("testId3", "TestID_3");
		mappedFieldNames.put("testId4", "TestID_4");
		mappedFieldNames.put("performanceTestId", "Performance_TestID");
		mappedFieldNames.put("subScoreDefinitionName", "Sub-score_Definition_Name");
		mappedFieldNames.put("rawScore", "Raw_Score");
		mappedFieldNames.put("rating", "Rating");
		mappedFieldNames.put("minimumPercentResponses", "Minimum_Percent_Responses");
		
		List<SubscoreRawToScaleScores> subscoreRawToScaleScoresList =  getSubscoreRawToScaleScoresList();
		
		BeanPropertyBindingResult validationErrors = new BeanPropertyBindingResult(target, "batchUpload"); 
		List<String> validationErrorsOfAllSubscoreRawToScaleScoreObjects = new ArrayList<String>();
		
		
		for(SubscoreRawToScaleScores subscoreObject : subscoreRawToScaleScoresList){
			Map<String, Object> customValidationResults = new HashMap<String, Object>();
			customValidationResults = customValidation(validationErrors, subscoreObject, params, mappedFieldNames);
			
			@SuppressWarnings("unchecked")
			List<ObjectError> errors = (List<ObjectError>) customValidationResults.get("errors");
			for(ObjectError error : errors)
			{
				validationErrorsOfAllSubscoreRawToScaleScoreObjects.add(subscoreObject.getLineNumber()+" "+error.getArguments()[1]+" "+error.getDefaultMessage());
			}
		}
		
		logger.debug("**********************************************************************************************************");
		for(String errorMessage : validationErrorsOfAllSubscoreRawToScaleScoreObjects){
			logger.debug(errorMessage);
		}
		logger.debug("**********************************************************************************************************");
		
	}
	
	private List<SubscoreRawToScaleScores> getSubscoreRawToScaleScoresList()
	{
		List<SubscoreRawToScaleScores> subscoreRawToScaleScoresList =  new ArrayList<SubscoreRawToScaleScores>();
		
		//Case 1: Valid case (5248L, 5766L, 6146L)
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("2", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("17"), 1, new BigDecimal("0.6")));
		//Case 2: Rating: <1; Must be 1, 2 or 3 only  
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("3", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("27"), -1, new BigDecimal("0.6")));
		//Case 3: Invalid testid1
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("4", 2016L, 12L, 3L, 133L, 1234L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("25"), 1, new BigDecimal("0.6")));
		//Case 4: Valid case (5761L, 5743L, 6146L)
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("5", 2016L, 12L, 3L, 133L, 5761L, 5743L, 6146L, null, null, "Claim_1_all", new BigDecimal("20"), 3, new BigDecimal("0.6")));
		//Case 5: Invalid subscoreDefinitionName
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("6", 2016L, 12L, 3L, 133L, 5761L, 5743L, 6146L, 5758L, null, "Reading_Overall", new BigDecimal("24"), 2, new BigDecimal("0.6")));
		//Case 6: Valid case (5761L, 5743L, 6146L, 5758L)
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("7", 2016L, 12L, 3L, 133L, 5761L, 5743L, 6146L, 5758L, null, "Claim_2_all", new BigDecimal("8"), 2, new BigDecimal("0.6")));
		//Case 7: Valid case (5248L, 5766L, 6146L, 5758L, 5283L)
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("8", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, 5283L, "Claim_2_all", new BigDecimal("4"), 2, new BigDecimal("0.6")));
		//Case 8: Valid case (5248L, 5766L, 6146L, 5758L)
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("9", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_2_all", new BigDecimal("12"), null, new BigDecimal("0.6")));
		//Case 9: Fail: Rating required
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("10", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("15"), 1, null));
		//Case 10: Fail: minimumPercentResponses required
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("11", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("18"), 2, new BigDecimal("0.6")));
		//Case 11: Fail: TestId1 required
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("12", 2016L, 12L, 3L, 133L, null, 5766L, 6146L, null, null, "Claim_1_all", new BigDecimal("19"), 1, new BigDecimal("0.6")));
		//Case 12: Rating: >3;allowable values only 1,2,3. 
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("13", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("14"), 4, new BigDecimal("0.6")));
		//Case 13: Rating: <1
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("14", 2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("11"), 0, new BigDecimal("0.6")));
		//Case 14: Rating: Integer expected. Found decimal
		//subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject(2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("16"), 1.234, new BigDecimal("0.6")));
		//Case 15: Minimum_percent_responses: >1 or more than 2 decimals
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("15",2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("13"), 2, new BigDecimal("1.234")));
		//Case 16: Minimum_percent_responses: more than 2 decimals
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("16",2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("12"), 2, new BigDecimal("0.567")));
		//Case 17: Minimum_percent_responses: < 0
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("17",2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("5"), 2, new BigDecimal("-1")));
		//Case 18: Minimum_percent_responses: >1  
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("18",2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("6"), 2, new BigDecimal("2")));
		//Case 20: PerformanceTestID: doesn't belong to subject: ELA  (Belongs to Math)
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("19",2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, 5085L, "Claim_3_All", new BigDecimal("9"), 2, new BigDecimal("0.6")));
		//Case 21: Minimum_percent_responses: >1 or more than 2 decimals
		subscoreRawToScaleScoresList.add(getSubscoreRawToScaleScoresDataObject("20",2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, null, "Claim_3_All", new BigDecimal("7"), 2, new BigDecimal("1234.111")));
		
		return subscoreRawToScaleScoresList;
	}
	
	private SubscoreRawToScaleScores getSubscoreRawToScaleScoresDataObject(String lineNumber, Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeid, Long testId1, Long testId2, 
			Long testId3, Long testId4, Long performanceTestId, String subScoreDefinitionName, Object obj_rawScore, Object obj_rating, Object obj_minimumPercentResponses){
		SubscoreRawToScaleScores subscoreRawToScaleScores = new SubscoreRawToScaleScores();
		
		BigDecimal rawScore = null;
		if(obj_rawScore!=null)
			rawScore = (BigDecimal) obj_rawScore;
		
		int rating = 0;
		if(obj_rating!=null)
			rating = (int) obj_rating;
		
		BigDecimal minimumPercentResponses = null;
		if(obj_minimumPercentResponses != null)
			minimumPercentResponses = (BigDecimal) obj_minimumPercentResponses;
		
		subscoreRawToScaleScores.setSchoolYear(schoolYear);
		subscoreRawToScaleScores.setAssessmentProgram("KAP");
		subscoreRawToScaleScores.setAssessmentProgramId(assessmentProgramId);
		subscoreRawToScaleScores.setLineNumber(lineNumber);
		subscoreRawToScaleScores.setGrade("3");
		subscoreRawToScaleScores.setGradeId(gradeid);
		subscoreRawToScaleScores.setSubjectId(subjectId);
		subscoreRawToScaleScores.setSubject("ELA");
		subscoreRawToScaleScores.setTestId1(testId1);
		subscoreRawToScaleScores.setTestId2(testId2);
		subscoreRawToScaleScores.setTestId3(testId3);
		subscoreRawToScaleScores.setTestId4(testId4);
		subscoreRawToScaleScores.setPerformanceTestId(performanceTestId);
		subscoreRawToScaleScores.setSubScoreDefinitionName(subScoreDefinitionName);
		
		if(obj_rawScore!=null)
			subscoreRawToScaleScores.setRawScore(rawScore);
		if(obj_rating!=null)
			subscoreRawToScaleScores.setRating(rating);
		if(obj_minimumPercentResponses != null)
			subscoreRawToScaleScores.setMinimumPercentResponses(minimumPercentResponses);
		
		return subscoreRawToScaleScores;
	}
	
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params,Map<String, String> mappedFieldNames) {
		
		logger.debug("Started custom validation for SubscoreRawToScaleScores Batch Upload");
		SubscoreRawToScaleScores subscoreRawToScaleScores = (SubscoreRawToScaleScores) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber =subscoreRawToScaleScores.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		if(!subscoreRawToScaleScores.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg="Assessment Program is invalid.";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		}else{
			//validate subject
			if(!subscoreRawToScaleScores.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
				String errMsg="Subject is invalid.";
				logger.debug(errMsg);
				validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
				validationPassed = false;
			}else{
				//validate grade
				gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, subscoreRawToScaleScores.getGrade());
				if(gradeCourse == null){
					String errMsg="grade is invalid for subject specified.";
					logger.debug(errMsg);
					validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
					validationPassed = false;
				}
				else{
					//validate testid1: Must be a valid testID in assessment program, grade and subject
					List<edu.ku.cete.domain.content.Test> validTestOnRecord = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId1(), "Summative");
					if(CollectionUtils.isEmpty(validTestOnRecord)){
						String errMsg = "TestId1 is invalid for assessment program, grade and subject specified.";
						logger.debug(errMsg);
						validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
						validationPassed = false;
					}
					//validate testid1: Cannot be a test in test collection associated with stage = Performance
					if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId1()))
					{
						String errMsg = "TestId1 belongs to Test collection associated with Performance stage tests.";
						logger.debug(errMsg);
						validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
						validationPassed = false;
					}
					//TestID2 not required
					if(subscoreRawToScaleScores.getTestId2() != null){
						if(subscoreRawToScaleScores.getTestId2().equals(subscoreRawToScaleScores.getTestId1()))
						{
							//Cannot be the same testID as other testIDs.
							String errMsg = "Duplicate TestId2.";
							logger.debug(errMsg);
							validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
							validationPassed = false;
						}
						else
						{
							//validate testid2: Must be a valid testID in assessment program, grade and subject
							List<edu.ku.cete.domain.content.Test> validTestOnRecordForTest2 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId2(), "Summative");
							if(CollectionUtils.isEmpty(validTestOnRecordForTest2)){
								String errMsg = "TestId2 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
								validationPassed = false;
							}else{
								//validate testid2: Cannot be a test in teest collection associated with stage = Performance
								if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId2()))
								{
									String errMsg = "TestId2 belongs to Test collection associated with Performance stage tests.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
									validationPassed = false;
								}
							}
						}
					}
					//TestID3 not required
					if(subscoreRawToScaleScores.getTestId3() != null){
						if(subscoreRawToScaleScores.getTestId3().equals(subscoreRawToScaleScores.getTestId1()) || subscoreRawToScaleScores.getTestId3().equals(subscoreRawToScaleScores.getTestId2()))
						{
							//Cannot be the same testID as other testIDs.
							String errMsg = "Duplicate TestId3.";
							logger.debug(errMsg);
							validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
							validationPassed = false;
						}
						else
						{
							//validate testid3: Must be a valid testID in assessment program, grade and subject
							List<edu.ku.cete.domain.content.Test> validTestOnRecordForTest3 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId3(), "Summative");
							if(CollectionUtils.isEmpty(validTestOnRecordForTest3)){
								String errMsg = "TestId3 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
								validationPassed = false;
							}else{
								//validate testid3: Cannot be a test in teest collection associated with stage = Performance
								if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId3()))
								{
									String errMsg = "TestId3 belongs to Test collection associated with Performance stage tests.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
									validationPassed = false;
								}
							}
						}
					}
					//TestID4 not required
					if(subscoreRawToScaleScores.getTestId4() != null){
						if(subscoreRawToScaleScores.getTestId4().equals(subscoreRawToScaleScores.getTestId1()) || subscoreRawToScaleScores.getTestId4().equals(subscoreRawToScaleScores.getTestId2())
								|| subscoreRawToScaleScores.getTestId4().equals(subscoreRawToScaleScores.getTestId3()))
						{
							//Cannot be the same testID as other testIDs.
							String errMsg = "Duplicate TestId4.";
							logger.debug(errMsg);
							validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
							validationPassed = false;
						}
						else
						{
							//validate testid3: Must be a valid testID in assessment program, grade and subject
							List<edu.ku.cete.domain.content.Test> validTestOnRecordForTest4 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId4(), "Summative");
							if(CollectionUtils.isEmpty(validTestOnRecordForTest4)){
								String errMsg = "TestId4 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
								validationPassed = false;
							}else{
								//validate testid4: Cannot be a test in test collection associated with stage = Performance
								if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId1()))
								{
									String errMsg = "TestId4 belongs to Test collection associated with Performance stage tests.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
									validationPassed = false;
								}
							}
						}
					}
					
					//If performanceTestId is provided, then the following must be checked
					if(subscoreRawToScaleScores.getPerformanceTestId() != null){
						
						//check if performance TestID is valid
						List<edu.ku.cete.domain.content.Test> validTestOnRecordForPerformanceTest = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(null, 
									gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getPerformanceTestId(), "Summative");
						if(CollectionUtils.isEmpty(validTestOnRecordForPerformanceTest)){
							String errMsg = "Performance TestID is invalid for assessment program, grade and performance subject specified.";
							logger.debug(errMsg);
							validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
							validationPassed = false;
							
						}
						else
						{
							//validate PerformanceTestId: Must be a test in test collection associated with stage = Performance
							if(!testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getPerformanceTestId()))
							{
								String errMsg = "Performance TestID must belong to Test collection associated with Performance stage tests.";
								logger.debug(errMsg);
								validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
								validationPassed = false;
							}
						}
					}
					SubscoreRawToScaleScoresService subscoreRawToScaleScoresService = mock(SubscoreRawToScaleScoresServiceImpl.class);
					
					//check if subscore definition name already exists -- Created in the Upload Score Framework definitions
					boolean subscoreDefinitionNameExists = subscoreRawToScaleScoresService.checkIfSubscoreDefinitionNameExists(subscoreRawToScaleScores.getSubScoreDefinitionName(), gradeCourse.getId(), subjectId, assessmentProgramId);
					if(!subscoreDefinitionNameExists){
						String errMsg="Subscore definition name ["+subscoreRawToScaleScores.getSubScoreDefinitionName()+"] does not exist for Assessment Program, Subject, Grade";
						logger.debug(errMsg);
						validationErrors.rejectValue("subScoreDefinitionName", "", new String[]{lineNumber, mappedFieldNames.get("subScoreDefinitionName")}, errMsg);
						validationPassed = false;
					}
					
					
					if(subscoreRawToScaleScores.getMinimumPercentResponses()!=null && subscoreRawToScaleScores.getMinimumPercentResponses().compareTo(new BigDecimal("0")) != 0 )
					{
						int tempLength = subscoreRawToScaleScores.getMinimumPercentResponses().stripTrailingZeros().toPlainString().length();
						int index = subscoreRawToScaleScores.getMinimumPercentResponses().stripTrailingZeros().toPlainString().indexOf(".");
						int numOfDecimals = 0;
						if(index>0)
							numOfDecimals = tempLength - index - 1;
					    
					    if(numOfDecimals > 2)
					    {
					    	String errMsg = "Minimum percent responses field can have upto 2 decimals places only.";
							logger.debug(errMsg);
							validationErrors.rejectValue("minimumPercentResponses", "", new String[]{lineNumber, mappedFieldNames.get("minimumPercentResponses")}, errMsg);
							validationPassed = false;
					    }
					}
					
					
				}
			}
		}
		
		if(validationPassed){
				logger.debug("Custom Validation passed. Setting Params to domain object.");
				subscoreRawToScaleScores.setAssessmentProgramId(assessmentProgramId); 
				subscoreRawToScaleScores.setSubjectId(subjectId);
				subscoreRawToScaleScores.setGradeId(gradeCourse.getId());
		}
		subscoreRawToScaleScores.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", subscoreRawToScaleScores);
		logger.debug("Completed validation completed.");
		return customValidationResults;
 		
	}
}
