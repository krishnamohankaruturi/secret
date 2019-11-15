package edu.ku.cete.service.impl.report;


import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.model.GradeCourseDao;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.impl.GradeCourseServiceImpl;
import edu.ku.cete.service.impl.report.TestCutScoresServiceImpl;
import edu.ku.cete.service.report.TestCutScoresService;
import edu.ku.cete.test.BaseTest;

/**
 * @author n466k239
 *
 */
public class TestCutScoresCustomValidationServiceImplTest  extends BaseTest {

	
	final static Log logger = LogFactory.getLog(TestCutScoresCustomValidationServiceImplTest.class);

	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private TestCutScoresService testCutScoresService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public final void testCase() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "KAP");
		params.put("subjectCodeOnUI", "ELA");
		params.put("assessmentProgramIdOnUI", (Long) 12L);
		params.put("subjectIdOnUI", (Long) 3L);
		
		List<TestCutScores> testCutScoresList = new ArrayList<TestCutScores>();
		//Case 1: Removed all testID columns for 2016 - not needed
		//Expected Result: Pass
		TestCutScores testCutScores =  new TestCutScores();
		testCutScores.setId(11L);
		testCutScores.setSchoolYear(2016L);
		testCutScores.setAssessmentProgram("KAP");
		testCutScores.setLineNumber("2");
		testCutScores.setGrade("3");
		testCutScores.setGradeId(133L);
		testCutScores.setSubjectId(3L);
		testCutScores.setSubject("ELA");
		testCutScores.setLevel(1L);
		testCutScores.setLevelLowCutScore(180L);
		testCutScores.setLevelHighCutScore(184L);
		testCutScoresList.add(testCutScores);
		
		//Case 2: AMP DATA
		//Expected Result: Fail - Testing for KAP
		testCutScores =  new TestCutScores();
		testCutScores.setId(12L);
		testCutScores.setSchoolYear(2016L);
		testCutScores.setAssessmentProgram("AMP");
		testCutScores.setLineNumber("3");
		testCutScores.setGrade("3");
		testCutScores.setGradeId(133L);
		testCutScores.setSubjectId(3L);
		testCutScores.setSubject("ELA");
		testCutScores.setLevel(2L);
		testCutScores.setLevelLowCutScore(185L);
		testCutScores.setLevelHighCutScore(189L);
		testCutScoresList.add(testCutScores);
		
		//Case 3: High score lesser than low-cut score
		//Expected Result: Fail
		testCutScores =  new TestCutScores();
		testCutScores.setId(12L);
		testCutScores.setSchoolYear(2016L);
		testCutScores.setAssessmentProgram("KAP");
		testCutScores.setLineNumber("4");
		testCutScores.setGrade("3");
		testCutScores.setGradeId(133L);
		testCutScores.setSubjectId(3L);
		testCutScores.setSubject("ELA");
		testCutScores.setLevel(3L);
		testCutScores.setLevelLowCutScore(205L);
		testCutScores.setLevelHighCutScore(180L);
		testCutScoresList.add(testCutScores);

		//Case 3: Duplicate Level for assessment program, subject and grade
		//Expected Result: Fail 
		testCutScores =  new TestCutScores();
		testCutScores.setId(12L);
		testCutScores.setSchoolYear(2016L);
		testCutScores.setAssessmentProgram("KAP");
		testCutScores.setLineNumber("3");
		testCutScores.setGrade("3");
		testCutScores.setGradeId(133L);
		testCutScores.setSubjectId(3L);
		testCutScores.setSubject("ELA");
		testCutScores.setLevel(3L);
		testCutScores.setLevelLowCutScore(185L);
		testCutScores.setLevelHighCutScore(189L);
		testCutScoresList.add(testCutScores);

		
		testCustomValidation(params, testCutScoresList);

	}
	
	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.TestCutScoresCustomValidationServiceImpl#customValidation(org.springframework.validation.BeanPropertyBindingResult, java.lang.Object, java.util.Map, java.util.Map)}.
	 */
	private void testCustomValidation(Map<String, Object> params, List<TestCutScores> testCutScoreList) {
	//	fail("Not yet implemented"); // TODO
		logger.debug("Started custom validation for Test cut scores Batch Upload");
		GradeCourseService gradeCourseServiceMock = mock(GradeCourseServiceImpl.class);
		TestCutScoresService testCutScoresServiceMock = mock(TestCutScoresServiceImpl.class);
		for(TestCutScores testCutScore : testCutScoreList){
			
			
			Map<String, Object> customValidationResults = new HashMap<String, Object>();
			Long batchUploadId = (Long) params.get("batchUploadId");
			String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
			String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
			Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
			Long subjectId = (Long)params.get("subjectIdOnUI");
			String lineNumber =testCutScore.getLineNumber();
			GradeCourse gradeCourse = null;
			boolean validationPassed = true;
	
			if(!testCutScore.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
				String errorMsg = "Assessment Program is invalid:"+lineNumber;
				logger.debug(errorMsg);
				//validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errorMsg);
				validationPassed = false;
			}else{
				//validate subject
				if(!testCutScore.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
					logger.debug("Subject is invalid:"+lineNumber);
					//validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, "Subject is invalid.");
					validationPassed = false;
				}else{
					//validate grade
					//when(gradeCourseServiceMock.getByContentAreaAndAbbreviatedName(subjectId, testCutScore.getGrade())).thenReturn(gradeCourse);
					gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, testCutScore.getGrade());
					if(gradeCourse == null){
						logger.debug("grade is invalid for subject specified :"+lineNumber);
						//validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, "grade is invalid for subject specified.");
						validationPassed = false;
					}
				}
			}
			//high score must be greater than low score
			if(testCutScore.getLevelHighCutScore().longValue() < testCutScore.getLevelLowCutScore().longValue()){
				logger.debug("High Cut Score must be greater than Low Cut Score:"+lineNumber);
				//validationErrors.rejectValue("levelHighCutScore", "", new String[]{lineNumber, mappedFieldNames.get("levelHighCutScore")}, "High Cut Score(" + testCutScore.getLevelHighCutScore() +") must be greater than Low Cut Score(" + testCutScore.getLevelLowCutScore() + ").");
				validationPassed = false;
			}
			//check duplicate level number
			//**** This condition was used to check the level number against the testid1 and testid2. But we removed testids as per US17646
			TestCutScores testCutScoresRecord = testCutScoresService.checkDuplicateLevels(testCutScore.getLevel(),  assessmentProgramId, subjectId, testCutScore.getSchoolYear(), testCutScore.getDomain(), 18L, null);
			if(testCutScoresRecord != null){
				logger.debug("Duplicate level number for same assessment program ("+assessmentProgramCodeOnUI+") and subject ("+subjectCodeOnUI+"):"+lineNumber);
				//validationErrors.rejectValue("level", "", new String[]{lineNumber, mappedFieldNames.get("level")}, "Duplicate level number ("+testCutScore.getLevel()+") for assessment program ("+assessmentProgramCodeOnUI+") and subject ("+subjectCodeOnUI+").");
				validationPassed = false;
			}
			
			if(validationPassed){
					logger.debug("Custom Validation passed. Setting Params to domain object.");
					testCutScore.setAssessmentProgramId(assessmentProgramId); 
					testCutScore.setSubjectId(subjectId);
					testCutScore.setGradeId(gradeCourse.getId());
			}
			testCutScore.setBatchUploadId(batchUploadId);
			//customValidationResults.put("errors", validationErrors.getAllErrors());
			customValidationResults.put("rowDataObject", testCutScore);
			logger.debug("Completed validation completed.");
		}
	}
	
	
	@Test
	public void validateAssessmentProgram() {
		TestCutScores testCutScores = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		String assessmentProgramCodeOnUI = "KAP";
		assertEquals("Assessment program is invalid", assessmentProgramCodeOnUI, testCutScores.getAssessmentProgram());
	}
		
	@Test
	public void validateSubject() {
		TestCutScores testCutScores = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		String subjectIdOnUI = "ELA";
		assertEquals("Subject is invalid", subjectIdOnUI, testCutScores.getSubject());
	}
	
	@Test
	public void validateGradeCourse() {
		TestCutScores testCutScores = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		GradeCourse gradeCourse = null;
		Long subjectId = 3L; 
		gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, testCutScores.getGrade());
		assertNotNull("Grade is invalid for subject specified", gradeCourse);
	}
	
	@Test
	public void validateHighCutScoreGreaterThanLowCutScore() {
		TestCutScores testCutScores = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		assertFalse("High Cut Score must be greater than Low Cut Score", (testCutScores.getLevelHighCutScore().longValue() < testCutScores.getLevelLowCutScore().longValue()));
		
	}
	
	@Test
	public void testSchoolYear() {
		List<TestCutScores> testCutScoresList = new ArrayList<TestCutScores>();
		TestCutScores testCutScores1 = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		TestCutScores testCutScores2 = getTestCutScoresDataObject(2017L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		testCutScoresList.add(testCutScores1);
		testCutScoresList.add(testCutScores2);
		assertNotEquals("Different school years in the upload file", testCutScoresList.get(0).getSchoolYear(), testCutScoresList.get(1).getSchoolYear());
	}
	
	@Test
	public void checkDuplicateLevels() {
		//fail("Not yet implemented");
		TestCutScores testCutScores = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L, "");
		
		TestCutScoresService testCutScoresServiceeMock = mock(TestCutScoresServiceImpl.class);
		TestCutScores testCutScores_duplicate = testCutScoresServiceeMock.checkDuplicateLevels(testCutScores.getLevel(), testCutScores.getAssessmentProgramId(), testCutScores.getSubjectId(), testCutScores.getSchoolYear(), testCutScores.getDomain(), 18L, null);
		assertNull("No duplicates found", testCutScores_duplicate);
	}
	
	private TestCutScores getTestCutScoresDataObject(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeid,  Long level, Long lowCutScore, Long highCutScore, String domain){
		TestCutScores testCutScores = new TestCutScores();
		
		testCutScores.setSchoolYear(schoolYear);
		testCutScores.setAssessmentProgram("KAP");
		testCutScores.setAssessmentProgramId(assessmentProgramId);
		testCutScores.setLineNumber("2");
		testCutScores.setGrade("3");
		testCutScores.setGradeId(gradeid);
		testCutScores.setSubjectId(subjectId);
		testCutScores.setSubject("ELA");
		testCutScores.setLevel(level);
		testCutScores.setLevelLowCutScore(lowCutScore);
		testCutScores.setLevelHighCutScore(highCutScore);
		testCutScores.setDomain(domain);
		return testCutScores;
	}

}
