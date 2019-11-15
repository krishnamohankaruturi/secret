package edu.ku.cete.service.impl.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;

import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.RawToScaleScoresService;
import edu.ku.cete.test.BaseTest;

public class RawToScaleScoresCustomValidationServiceImplTest extends BaseTest {

	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	@Autowired
	private TestService testService;
	
	final static Log logger = LogFactory.getLog(RawToScaleScoresCustomValidationServiceImpl.class);

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public final void testCase1() {
		//Case 1: Empty File - FAIL CASE
		//testCustomValidation(null, null);
	}
	
	
	@Test
	public final void testCase_AMP_ELA() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "AMP");
		params.put("subjectCodeOnUI", "ELA");
		params.put("assessmentProgramIdOnUI", (Long) 37L);
		params.put("subjectIdOnUI", (Long) 3L);
		
		List<RawToScaleScores> rawToScaleScoresList =  new ArrayList<RawToScaleScores>();
		
		//Case 2: No performance tests for AMP, Only 2 stages for ELA (Testid1 is required. Others are optional - Valid case
		//Expected Result: Pass
		RawToScaleScores rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(11L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("2");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5030L);
		rawToScaleScores.setTestId2(5100L);
		
		rawToScaleScores.setRawScore(new BigDecimal("25"));
		rawToScaleScores.setScaleScore(50L);
		rawToScaleScores.setStandardError(new BigDecimal("2.1"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		
		//Case 3. No performance tests for AMP, All 4 stages for M 
		//Expected Result: Pass
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(12L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("3");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5032L);
		rawToScaleScores.setTestId2(5101L);
		rawToScaleScores.setTestId3(5112L);
		rawToScaleScores.setTestId4(5047L);
		
		rawToScaleScores.setRawScore(new BigDecimal("10"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 21. KAP instead of AMP 
		//Expected Result: Fail
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(21L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("21");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5032L);
		rawToScaleScores.setTestId2(5101L);
		rawToScaleScores.setTestId3(5112L);
		rawToScaleScores.setTestId4(5047L);
		
		rawToScaleScores.setRawScore(new BigDecimal("10"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 21. M instead of ELA 
		//Expected Result: Fail
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(221L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("21");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("M");
		
		rawToScaleScores.setTestId1(5032L);
		rawToScaleScores.setTestId2(5101L);
		rawToScaleScores.setTestId3(5112L);
		rawToScaleScores.setTestId4(5047L);
		
		rawToScaleScores.setRawScore(new BigDecimal("10"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 4: All 4 stages for ELA; Performance test: ELA; Do not include performance items in RawScore
		//Expected Result: Pass
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(13L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("4");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");

		rawToScaleScores.setTestId1(5032L);
		rawToScaleScores.setTestId2(5101L);
		rawToScaleScores.setTestId3(5112L);
		rawToScaleScores.setTestId4(5047L);
		
		rawToScaleScores.setPerformanceTestId(5058L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("No");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0.00"));
		
		rawToScaleScores.setRawScore(new BigDecimal("10"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 5: All 4 stages for ELA; Performance test: ELA; Include performance items in RawScore
		//Expected Result: Pass
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(14L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("5");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");

		rawToScaleScores.setTestId1(5032L);
		rawToScaleScores.setTestId2(5101L);
		rawToScaleScores.setTestId3(5112L);
		rawToScaleScores.setTestId4(5047L);
		
		rawToScaleScores.setPerformanceTestId(5058L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("Yes");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("1.234450"));
		
		rawToScaleScores.setRawScore(new BigDecimal("12"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 10: Duplicate testids, performance testid and rawscore for scale score - fail
		//Expected Result: Fail
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(15L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("10");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");

		rawToScaleScores.setTestId1(5032L);
		rawToScaleScores.setTestId2(5101L);
		rawToScaleScores.setTestId3(5112L);
		rawToScaleScores.setTestId4(5047L);
		
		rawToScaleScores.setPerformanceTestId(5058L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("No");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0.00"));
		
		rawToScaleScores.setRawScore(new BigDecimal("10"));
		rawToScaleScores.setScaleScore(400L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		testCustomValidation(params, rawToScaleScoresList);

	}
	
	@Test
	public final void testCase_AMP_M() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "AMP");
		params.put("subjectCodeOnUI", "M");
		params.put("assessmentProgramIdOnUI", (Long) 37L);
		params.put("subjectIdOnUI", (Long) 440L);
		
		List<RawToScaleScores> rawToScaleScoresList =  new ArrayList<RawToScaleScores>();
		
		//Case 7: AMP; 4 Stages: Math; Performance test: Math; Perf includedweight: negative
		//Expected Result: Fail
		RawToScaleScores rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(16L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("7");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(440L);
		rawToScaleScores.setSubject("M");
		
		rawToScaleScores.setTestId1(5062L);
		rawToScaleScores.setTestId2(5095L);
		rawToScaleScores.setTestId3(5078L);
		rawToScaleScores.setTestId4(5070L);
		
		rawToScaleScores.setPerformanceTestId(5085L);
		rawToScaleScores.setPerformanceSubject("M");
		rawToScaleScores.setPerformanceRawscoreInclude("Yes");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("-1.00"));
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		
		//Case 19: AMP; 4 Stages: Math; Performance test: Math; Perf includedweight: positive
		//Expected Result: Pass
		rawToScaleScores.setId(17L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("19");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(440L);
		rawToScaleScores.setSubject("M");
		
		rawToScaleScores.setTestId1(5062L);
		rawToScaleScores.setTestId2(5095L);
		rawToScaleScores.setTestId3(5078L);
		rawToScaleScores.setTestId4(5070L);
		
		rawToScaleScores.setPerformanceTestId(5085L);
		rawToScaleScores.setPerformanceSubject("M");
		rawToScaleScores.setPerformanceRawscoreInclude("Yes");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("-1.00"));
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 8: AMP; 4 Stages: Math; No Performance test (This year no performance tests. But not validating with respect to assessment program. So should work
 		//Expected Result: Pass
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(18L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("8");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5062L);
		rawToScaleScores.setTestId2(5095L);
		rawToScaleScores.setTestId3(5078L);
		rawToScaleScores.setTestId4(5070L);
		
		rawToScaleScores.setRawScore(new BigDecimal("14"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 20: AMP; 4 Stages: Math; Performance test: ELA; No weight
 		//Expected Result: Pass
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(19L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("20");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5062L);
		rawToScaleScores.setTestId2(5095L);
		rawToScaleScores.setTestId3(5078L);
		rawToScaleScores.setTestId4(5070L);
		
		rawToScaleScores.setPerformanceTestId(5066L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("no");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0"));
		
		rawToScaleScores.setRawScore(new BigDecimal("14"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		
		//Case 9: Science stages; ela performance (Science not reported this year; Not failing in uploads)
		//Expected Result: Pass
		//Check case sensitivity of the subject abbreviation
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(20L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("9");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("SCI");

		rawToScaleScores.setTestId1(5136L);
		rawToScaleScores.setTestId2(5156L);
		rawToScaleScores.setTestId3(5127L);
		rawToScaleScores.setTestId4(5151L);
		
		rawToScaleScores.setPerformanceTestId(5060L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("No");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0.00"));
		
		rawToScaleScores.setRawScore(new BigDecimal("40"));
		rawToScaleScores.setScaleScore(240L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		testCustomValidation(params, rawToScaleScoresList);

	}
	
	@Test
	public final void testCase_KAP_ELA() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "KAP");
		params.put("subjectCodeOnUI", "ELA");
		params.put("assessmentProgramIdOnUI", (Long) 12L);
		params.put("subjectIdOnUI", (Long) 3L);
		
		List<RawToScaleScores> rawToScaleScoresList =  new ArrayList<RawToScaleScores>();
		
		//Case 11. NO performance columns (performancetestid is null)
		//Expected Result: PASS
		RawToScaleScores rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(23L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("11");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5254L);
		rawToScaleScores.setTestId3(5260L);
		rawToScaleScores.setTestId4(5713L);
		
		rawToScaleScores.setRawScore(new BigDecimal("15"));
		rawToScaleScores.setScaleScore(250L);
		rawToScaleScores.setStandardError(new BigDecimal("3"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 12. Missing performance columns when performancetestid is not null
		//Expected Result: FAIL
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(23L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("2");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5254L);
		rawToScaleScores.setTestId3(5260L);
		rawToScaleScores.setTestId4(5713L);
		
		rawToScaleScores.setPerformanceTestId(5283L);
		rawToScaleScores.setPerformanceSubject("ELA");
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("4"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		
		//Case 13. Only 1 stage; Duplicate testid,rawscore but valid as testid2 is null
		//Expected Result: FAIL
		rawToScaleScores.setId(17L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("3");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
				
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);

		//Case 14. Duplicate testid1 and testid2
		//Expected Result: FAIL
		rawToScaleScores.setId(17L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("4");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5248L);
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 15. Duplicate testid1 and testid3
		//Expected Result: FAIL
		rawToScaleScores.setId(17L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("5");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		rawToScaleScores.setTestId3(5248L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 16. Duplicate testid1 and performancetestid
 		//Expected Result: Pass
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(18L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("6");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		rawToScaleScores.setTestId3(6146L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setPerformanceTestId(5766L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("No");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0.00"));
		
		rawToScaleScores.setRawScore(new BigDecimal("14"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 20: valid case
 		//Expected Result: Pass
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(19L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("7");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		rawToScaleScores.setTestId3(6146L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setPerformanceTestId(5283L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("no");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0"));
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		
		rawToScaleScoresList.add(rawToScaleScores);
		
		testCustomValidation(params, rawToScaleScoresList);

	}
	
	
	@Test
	public final void testCase_US18183_validatePerformanceRawscoreFlagValue() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "KAP");
		params.put("subjectCodeOnUI", "ELA");
		params.put("assessmentProgramIdOnUI", (Long) 12L);
		params.put("subjectIdOnUI", (Long) 3L);
		
		List<RawToScaleScores> rawToScaleScoresList =  new ArrayList<RawToScaleScores>();
		
		//Row 2: KAP; 4 Stages: ELA; Performance test: ELA;
		//Include_Performance_Items_In_Raw_Score=NO
		//Expected Result: Invalid Case - Decimals more than 2 places
		RawToScaleScores rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(21L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("2");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		rawToScaleScores.setTestId3(6146L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setPerformanceTestId(5283L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("no");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("1.234"));
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		
		//Row 3: KAP; 4 Stages: ELA; Performance test: ELA;
		//Include_Performance_Items_In_Raw_Score=NO
		//Expected Result: Valid Case
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(22L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("3");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		rawToScaleScores.setTestId3(6146L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setPerformanceTestId(5283L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("no");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0"));
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		
		//Row 4: KAP; 4 Stages: ELA; NO Performance test
		//Expected Result: Valid Case
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(23L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("4");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5761L);
		rawToScaleScores.setTestId2(5743L);
		rawToScaleScores.setTestId3(5784L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setRawScore(new BigDecimal("14"));
		rawToScaleScores.setScaleScore(220L);
		rawToScaleScores.setStandardError(new BigDecimal("1.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Row 5: KAP; 4 Stages: ELA; Performance test: ELA;
		//Include_Performance_Items_In_Raw_Score=yes (Invalid case - must be no as rest above)
		//Expected Result: FAIL - Show error message
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(24L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("5");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		rawToScaleScores.setTestId3(6146L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setPerformanceTestId(5283L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("Yes");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0"));
		
		rawToScaleScores.setRawScore(new BigDecimal("25"));
		rawToScaleScores.setScaleScore(300L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Row 6: KAP; 4 Stages: ELA; Performance test: ELA;
		//Invalid case: Duplicate rawscore for testids
		//Expected Result: FAIL - Show error message
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(25L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("6");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");

		rawToScaleScores.setTestId1(5761L);
		rawToScaleScores.setTestId2(5743L);
		rawToScaleScores.setTestId3(5784L);
		rawToScaleScores.setTestId4(5758L);
	
		rawToScaleScores.setRawScore(new BigDecimal("14"));
		rawToScaleScores.setScaleScore(200L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
	
		//Row 7: KAP; 4 Stages: ELA; Performance test: ELA;
		//Invalid case: Duplicate rawscore for testids & perf testid
		//Expected Result: FAIL - Show error message
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(26L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("3");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		rawToScaleScores.setTestId3(6146L);
		rawToScaleScores.setTestId4(5758L);
		
		rawToScaleScores.setPerformanceTestId(5283L);
		rawToScaleScores.setPerformanceSubject("ELA");
		rawToScaleScores.setPerformanceRawscoreInclude("no");
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("0"));
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(235L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Row 8: KAP; 2 Stages: ELA
		//Expected Result: Valid  case
		rawToScaleScores =  new RawToScaleScores();
		rawToScaleScores.setId(27L);
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("6");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(133L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(5248L);
		rawToScaleScores.setTestId2(5766L);
		
		rawToScaleScores.setRawScore(new BigDecimal("14"));
		rawToScaleScores.setScaleScore(222L);
		rawToScaleScores.setStandardError(new BigDecimal("2.1"));
		rawToScaleScoresList.add(rawToScaleScores);
		
		//Case 21: valid case
 		//Expected Result: Pass
		rawToScaleScoresList.add(getRawToScaleScoresDataObject(20L, 2016L, 12L, 3L,133L, 5248L, 5766L, 6146L, 5758L, 5283L, "ELA", "no", new BigDecimal("0"), new BigDecimal("20"), 275L, new BigDecimal("2.5")));
		
		
		
		testCustomValidation(params, rawToScaleScoresList);

	}
	
	
	@Test
	public final void testCase_US18183_validatePerformanceRawscoreFlagValue_AfterValidationChanges() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("batchUploadId", (Long) 111L);
		params.put("assessmentProgramCodeOnUI", "KAP");
		params.put("subjectCodeOnUI", "ELA");
		params.put("assessmentProgramIdOnUI", (Long) 12L);
		params.put("subjectIdOnUI", (Long) 3L);
		
		List<RawToScaleScores> rawToScaleScoresList =  new ArrayList<RawToScaleScores>();
		
		rawToScaleScoresList.add(getRawToScaleScoresDataObject(2L, 2016L, 12L, 3L,133L, 5248L, null, null, null, null, null, null, new BigDecimal("1"), new BigDecimal("21"), 275L, new BigDecimal("2.5")));
		rawToScaleScoresList.add(getRawToScaleScoresDataObject(2L, 2016L, 12L, 3L,133L, 5248L, null, null, null, null, null, "", new BigDecimal("1"), new BigDecimal("21"), 275L, new BigDecimal("2.5")));
		rawToScaleScoresList.add(getRawToScaleScoresDataObject(3L, 2016L, 12L, 3L,133L, 5248L, 5766L, 6146L, 5758L, null, null, "yes", new BigDecimal("0"), new BigDecimal("20"), 275L, new BigDecimal("2.5")));
		rawToScaleScoresList.add(getRawToScaleScoresDataObject(4L, 2016L, 12L, 3L,133L, 5248L, 5766L, 6146L, 5758L, 5283L, "ELA", "no", new BigDecimal("0"), new BigDecimal("20"), 275L, new BigDecimal("2.5")));
		rawToScaleScoresList.add(getRawToScaleScoresDataObject(5L, 2016L, 12L, 3L,133L, 5248L, 5766L, 6146L, 5758L, 5283L, "ELA", "yes", new BigDecimal("0"), new BigDecimal("20"), 275L, new BigDecimal("2.5")));
		
		testCustomValidation(params, rawToScaleScoresList);
		
	}
	
	private RawToScaleScores getRawToScaleScoresDataObject(Long lineNumber, Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeid, 
			Long testId1, Long testId2, Long testId3, Long testId4, Long performanceTestId, String performanceSubject, String performanceRawscoreInclude, 
			BigDecimal performanceItemWeight, BigDecimal rawScore, Long scaleScore, BigDecimal standardError){
		RawToScaleScores rawToScaleScores = new RawToScaleScores();
		
		rawToScaleScores.setId(lineNumber);
		rawToScaleScores.setSchoolYear(schoolYear);
		rawToScaleScores.setAssessmentProgramId(assessmentProgramId);
		rawToScaleScores.setAssessmentProgram("KAP");
		rawToScaleScores.setLineNumber("3");
		rawToScaleScores.setGrade("3");
		rawToScaleScores.setGradeId(gradeid);
		rawToScaleScores.setSubjectId(subjectId);
		rawToScaleScores.setSubject("ELA");
		
		rawToScaleScores.setTestId1(testId1);
		rawToScaleScores.setTestId2(testId2);
		rawToScaleScores.setTestId3(testId3);
		rawToScaleScores.setTestId4(testId4);
		
		rawToScaleScores.setPerformanceTestId(performanceTestId);
		rawToScaleScores.setPerformanceSubject(performanceSubject);
		rawToScaleScores.setPerformanceRawscoreInclude(performanceRawscoreInclude);
		rawToScaleScores.setPerformanceItemWeight(performanceItemWeight);
		
		rawToScaleScores.setRawScore(rawScore);
		rawToScaleScores.setScaleScore(scaleScore);
		rawToScaleScores.setStandardError(standardError);
		
		return rawToScaleScores;
	}
		
	private void testCustomValidation(Map<String, Object> params, List<RawToScaleScores> rawToScaleScoresList) {
		String newLine = "======================================================================";
		
		RawToScaleScores target = new RawToScaleScores();
		List<String> validationErrorsOfAllRawToScaleScoreObjects = new ArrayList<String>();
		Map<String, String> mappedFieldNames = new HashMap<String, String>();
		mappedFieldNames.put("assessmentProgram", "assessment_program");
		mappedFieldNames.put("rawScore", "raw_score");
		mappedFieldNames.put("subject", "subject");
		mappedFieldNames.put("testId4", "testid_4");
		mappedFieldNames.put("testId3", "testid_3");
		mappedFieldNames.put("testId2", "testid_2");
		mappedFieldNames.put("testId1", "testid_1");
		mappedFieldNames.put("schoolYear", "school_year");
		mappedFieldNames.put("performanceRawscoreInclude", "include_performance_items_in_raw_score");
		mappedFieldNames.put("grade", "grade");
		mappedFieldNames.put("performanceTestId", "performance_testid");
		mappedFieldNames.put("performanceSubject", "performance_subject");
		mappedFieldNames.put("performanceItemWeight", "performance_item_weight");
		mappedFieldNames.put("scaleScore", "scale_score");
		
		logger.debug("Started custom validation for Raw To Scale Scores Batch Upload");
		for(RawToScaleScores rawToScaleScores : rawToScaleScoresList){
			BeanPropertyBindingResult validationErrors = new BeanPropertyBindingResult(target, "batchUpload"); 
			
			Map<String, Object> customValidationResults = new HashMap<String, Object>();
			Long batchUploadId = (Long) params.get("batchUploadId");
			String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
			String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
			Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
			Long subjectId = (Long)params.get("subjectIdOnUI");
			String lineNumber = rawToScaleScores.getLineNumber();
			GradeCourse gradeCourse = null;
			boolean validationPassed = true;
			boolean performanceTestExists = false;
			ContentArea performanceSubject = null;
			if(!rawToScaleScores.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
				String errMsg = "Assessment Program is invalid. ";
				logger.debug(newLine);
				logger.debug(errMsg);
				validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
				logger.debug(newLine);
				validationPassed = false;
			}else{
				//KAP - No Science this year. No validation needed.
				//validate subject
				if(!rawToScaleScores.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
					String errMsg = "Subject is invalid.";
					logger.debug(newLine);
					logger.debug(errMsg);
					logger.debug(newLine);
					validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, "Subject is invalid.");
					validationPassed = false;
				}else{
					//validate grade
					gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, rawToScaleScores.getGrade());
					if(gradeCourse == null){
						String errMsg = "Grade is invalid for subject specified.";
						logger.debug(newLine);
						logger.debug(errMsg);logger.debug(newLine);
						validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
						validationPassed = false;
					}
					else{
						//validate testid1: Must be a valid testID in assessment program, grade and subject
						List<edu.ku.cete.domain.content.Test> validTestOnRecord = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId1(), "Summative");
						if(CollectionUtils.isEmpty(validTestOnRecord)){
							String errMsg = "TestId1 is invalid for assessment program, grade and subject specified.";
							logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
							validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
							validationPassed = false;
						}
						//validate testid1: Cannot be a test in test collection associated with stage = Performance
						if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId1()))
						{
							String errMsg = "TestId1 belongs to Test collection associated with Performance stage tests.";
							logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
							validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
							validationPassed = false;
						}
						//TestID2 not required
						if(rawToScaleScores.getTestId2() != null){
							if(rawToScaleScores.getTestId2().equals(rawToScaleScores.getTestId1()))
							{
								//Cannot be the same testID as other testIDs.
								String errMsg = "Duplicate TestId2.";
								logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
								validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
								validationPassed = false;
							}
							else
							{
								//validate testid2: Must be a valid testID in assessment program, grade and subject
								List<edu.ku.cete.domain.content.Test> validTestOnRecordForTest2 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId2(), "Summative");
								if(CollectionUtils.isEmpty(validTestOnRecordForTest2)){
									String errMsg = "TestId2 is invalid for assessment program, grade and subject specified.";
									logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
									validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
									validationPassed = false;
								}else{
									//validate testid2: Cannot be a test in teest collection associated with stage = Performance
									if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId2()))
									{
										String errMsg = "TestId2 belongs to Test collection associated with Performance stage tests.";
										logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
										validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
										validationPassed = false;
									}
								}
							}
						}
						//TestID3 not required
						if(rawToScaleScores.getTestId3() != null){
							if(rawToScaleScores.getTestId3().equals(rawToScaleScores.getTestId1()) || rawToScaleScores.getTestId3().equals(rawToScaleScores.getTestId2()))
							{
								//Cannot be the same testID as other testIDs.
								String errMsg = "Duplicate TestId3.";
								logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
								validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
								validationPassed = false;
							}
							else
							{
								//validate testid3: Must be a valid testID in assessment program, grade and subject
								List<edu.ku.cete.domain.content.Test> validTestOnRecordForTest3 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId3(), "Summative");
								if(CollectionUtils.isEmpty(validTestOnRecordForTest3)){
									String errMsg = "TestId3 is invalid for assessment program, grade and subject specified.";
									logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
									validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
									validationPassed = false;
								}else{
									//validate testid3: Cannot be a test in teest collection associated with stage = Performance
									if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId3()))
									{
										String errMsg = "TestId3 belongs to Test collection associated with Performance stage tests.";
										logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
										validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
										validationPassed = false;
									}
								}
							}
						}
						//TestID4 not required
						if(rawToScaleScores.getTestId4() != null){
							if(rawToScaleScores.getTestId4().equals(rawToScaleScores.getTestId1()) || rawToScaleScores.getTestId4().equals(rawToScaleScores.getTestId2())
									|| rawToScaleScores.getTestId4().equals(rawToScaleScores.getTestId3()))
							{
								//Cannot be the same testID as other testIDs.
								String errMsg = "Duplicate TestId4.";
								logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
								validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
								validationPassed = false;
							}
							else
							{
								//validate testid3: Must be a valid testID in assessment program, grade and subject
								List<edu.ku.cete.domain.content.Test> validTestOnRecordForTest4 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId4(), "Summative");
								if(CollectionUtils.isEmpty(validTestOnRecordForTest4)){
									String errMsg = "TestId4 is invalid for assessment program, grade and subject specified.";
									logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
									validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
									validationPassed = false;
								}else{
									//validate testid4: Cannot be a test in teest collection associated with stage = Performance
									if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId1()))
									{
										String errMsg = "TestId4 belongs to Test collection associated with Performance stage tests.";
										logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
										validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
										validationPassed = false;
									}
								}
							}
						}
						
						
						//performanceRawscoreInclude is no longer a required field
						if(rawToScaleScores.getPerformanceRawscoreInclude()!=null && !rawToScaleScores.getPerformanceRawscoreInclude().isEmpty())
						{	
							//Irrespective of the testids/performance testids, validate if this flag has yes/no or empty. 
							//Validate that all rows in the "Upload raw score to scale score" have the same value for Include_Performance_Items_In_Raw_Score
							//	with respect to schoolyear/assessmentprogram/subject/grade
							RawToScaleScores rawToScaleObjectForFlag = rawToScaleScoresService.getFirstIncludePerformanceFlagForAssessmentProgramSubjectGrade(assessmentProgramId, subjectId, 
									gradeCourse.getId(), rawToScaleScores.getSchoolYear(), "Summative", null);
							if(rawToScaleObjectForFlag!=null){
								boolean getFirstPerformanceRawscoreFlag = rawToScaleObjectForFlag.getPerformanceRawscoreIncludeFlag();
								boolean invalidPerformanceRawscoreFlag = false;
								if ("yes".contains(rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase())) {
									if(getFirstPerformanceRawscoreFlag != Boolean.TRUE)
										invalidPerformanceRawscoreFlag = true;
								}
								else if ("no".contains(rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase())) {
									if(getFirstPerformanceRawscoreFlag != Boolean.FALSE)
										invalidPerformanceRawscoreFlag = true;
								}
								
								if(invalidPerformanceRawscoreFlag)
								{
									String errMsg = "Value of Include_Performance_Items_In_Raw_Score must be the same for all the rows for the assessment program/subject/grade.";
									logger.debug(errMsg);
									validationErrors.rejectValue("performanceRawscoreInclude", "", new String[]{lineNumber, mappedFieldNames.get("performanceRawscoreInclude")}, errMsg);
									validationPassed = false;
								}
							}
							else
							{
								if ("yes".contains(rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase())) {
									rawToScaleScores.setPerformanceRawscoreIncludeFlag(Boolean.TRUE);
								}
								else if ("no".contains(rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase())) {
									rawToScaleScores.setPerformanceRawscoreIncludeFlag(Boolean.FALSE);
								}
							}
						}
						
						//If performanceTestId is provided, then the following must be checked
						if(rawToScaleScores.getPerformanceTestId() != null){
							
								//1. performanceSubject becomes a required field
								if (rawToScaleScores.getPerformanceSubject()==null || rawToScaleScores.getPerformanceSubject().isEmpty())
								{
									String errMsg = "Performance Subject is required as Performance TestID is provided.";
									logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
									validationErrors.rejectValue("performanceSubject", "", new String[]{lineNumber, mappedFieldNames.get("performanceSubject")}, errMsg);
									validationPassed = false;
								}//End 1.
								else{
									performanceSubject = contentAreaService.findByAbbreviatedName(rawToScaleScores.getPerformanceSubject());
									
									//2. Check if performance Subject is valid 
									List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgram(assessmentProgramId);
									List<String> validSubjectAbbreviations = new ArrayList<String>(); 
									for(ContentArea subject : contentAreas)
										validSubjectAbbreviations.add(subject.getAbbreviatedName());
									
									if(performanceSubject==null || !validSubjectAbbreviations.contains(performanceSubject.getAbbreviatedName()))
									{
										String errMsg = "Performance Subject provided is not a valid subject for the given assessment program.";
										logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
										validationErrors.rejectValue("performanceSubject", "", new String[]{lineNumber, mappedFieldNames.get("performanceSubject")}, errMsg);
										validationPassed = false;
									}
									else{
										//3. check if performance TestID is valid
										List<edu.ku.cete.domain.content.Test> validTestOnRecordForPerformanceTest = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(performanceSubject.getId(), 
													gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getPerformanceTestId(), "Summative");
										if(CollectionUtils.isEmpty(validTestOnRecordForPerformanceTest)){
											String errMsg = "Performance TestID is invalid for assessment program, grade and performance subject specified.";
											logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
											validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
											validationPassed = false;
											
										}
										else
										{
											//validate PerformanceTestId: Must be a test in test collection associated with stage = Performance
											if(!testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getPerformanceTestId()))
											{
												String errMsg = "PerformanceTestId must belong to Test collection associated with Performance stage tests.";
												logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
												validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
												validationPassed = false;
											}
											else{
												performanceTestExists = true;
												//5. performanceItemWeight becomes a required field
												if(rawToScaleScores.getPerformanceItemWeight()==null || rawToScaleScores.getPerformanceRawscoreInclude().isEmpty())
												{
													String errMsg = "Performance_Item_Weight field is required for the given Performance TestID.";
													logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
													validationErrors.rejectValue("performanceItemWeight", "", new String[]{lineNumber, mappedFieldNames.get("performanceItemWeight")}, errMsg);
													validationPassed = false;
												}
												else if(rawToScaleScores.getPerformanceItemWeight().compareTo(new BigDecimal("0")) != 0 )
												{
													int tempLength = rawToScaleScores.getPerformanceItemWeight().stripTrailingZeros().toPlainString().length();
													int index = rawToScaleScores.getPerformanceItemWeight().stripTrailingZeros().toPlainString().indexOf(".");
													int numOfDecimals = 0;
													if(index>0)
														numOfDecimals = tempLength - index - 1;
												    
												    if(numOfDecimals > 2)
												    {
												    	String errMsg = "Performance_Item_Weight field can have upto 2 decimals places only.";
												    	logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
														validationErrors.rejectValue("performanceItemWeight", "", new String[]{lineNumber, mappedFieldNames.get("performanceItemWeight")}, errMsg);
														validationPassed = false;
												    }
												}
												//End 5.
											}
										}//End 3.
									}//End 2.
								//}//End 1.
								
							}//End AMP Test
						}//End Performance Test validations
					}
				}
			}
			
			//check duplicate raw score for different scale scores
			boolean rawToScaleScoresDuplicatesExist = rawToScaleScoresService.checkDuplicateTestIdsWithRawScore(rawToScaleScores.getTestId1(), rawToScaleScores.getTestId2(),
					rawToScaleScores.getTestId3(), rawToScaleScores.getTestId4(), rawToScaleScores.getPerformanceTestId(),
					rawToScaleScores.getRawScore(), rawToScaleScores.getScaleScore(), assessmentProgramId, subjectId, gradeCourse.getId(), rawToScaleScores.getDomain(), rawToScaleScores.getSchoolYear(), "Summative", null);
			if(rawToScaleScoresDuplicatesExist){
				String errMsg = "Duplicate rows exist for the same raw score "+rawToScaleScores.getRawScore()+" where scale score or SE is different.";
				logger.debug(newLine);logger.debug(errMsg);logger.debug(newLine);
				validationErrors.rejectValue("scaleScore", "", new String[]{lineNumber, mappedFieldNames.get("scaleScore")}, errMsg);
				validationPassed = false;
			}
			
			if(validationPassed){
					logger.debug(newLine);
					logger.debug("Custom Validation passed. Setting Params to domain object.");
					logger.debug(newLine);
					rawToScaleScores.setAssessmentProgramId(assessmentProgramId); 
					rawToScaleScores.setSubjectId(subjectId);
					rawToScaleScores.setGradeId(gradeCourse.getId());
					
					if(performanceTestExists){
						rawToScaleScores.setPerformanceSubjectId(performanceSubject.getId());
					}
					
			}
			rawToScaleScores.setBatchUploadId(batchUploadId);
			customValidationResults.put("errors", validationErrors.getAllErrors());
			customValidationResults.put("rowDataObject", rawToScaleScores);
			logger.debug(newLine);logger.debug("Completed validation completed.");logger.debug(newLine);
			
			
			List<ObjectError> errors = validationErrors.getAllErrors();
			for(ObjectError error : errors)
			{
				validationErrorsOfAllRawToScaleScoreObjects.add(rawToScaleScores.getLineNumber()+" "+error.getArguments()[1]+" "+error.getDefaultMessage());
			}
			
		}
		logger.debug("**********************************************************************************************************");
		for(String errorMessage : validationErrorsOfAllRawToScaleScoreObjects){
			logger.debug(errorMessage);
		}
		logger.debug("**********************************************************************************************************");
		
	}
}
