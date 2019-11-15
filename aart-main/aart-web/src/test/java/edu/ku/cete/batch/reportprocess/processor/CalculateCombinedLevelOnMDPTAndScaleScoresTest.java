/**
 * 
 */
package edu.ku.cete.batch.reportprocess.processor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.impl.report.CombinedLevelMapServiceImpl;
import edu.ku.cete.service.report.CombinedLevelMapService;
import edu.ku.cete.test.BaseTest;

/**
 * @author n466k239
 *
 */
public class CalculateCombinedLevelOnMDPTAndScaleScoresTest extends BaseTest{

	@Autowired
	private CombinedLevelMapService combinedLevelMapService;

	final static Log logger = LogFactory.getLog(CalculateCombinedLevelOnMDPTAndScaleScoresTest.class);

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
	public final void testCase1()
	{
		logger.debug("CombinedLevel testCase1: ");
		
		//PerformanceRawscoreIncludeFlag = NULL 
		//Returns null
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(null);
		studentReport.setMdptScorableFlag(true);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		logger.debug("CombinedLevel TestCase1: "+combinedLevel);
	}
	
	@Test
	public final void testCase1_2()
	{
		logger.debug("CombinedLevel testCase1_2: ");
		
		//PerformanceRawscoreIncludeFlag = NULL 
		//Returns null
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(null);
		studentReport.setMdptScorableFlag(null);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		logger.debug("CombinedLevel testCase1_2: "+combinedLevel);
	}
	
	@Test
	public final void testCase2()
	{
		logger.debug("CombinedLevel testCase2: ");
		
		//PerformanceRawscoreIncludeFlag = true (Not null but TRUE)
		//Returns null
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(true);
		studentReport.setMdptScorableFlag(true);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		System.out.println("CombinedLevel TestCase2: "+combinedLevel);
	}
	
	@Test
	public final void testCase2_2()
	{
		logger.debug("CombinedLevel testCase2_2: ");
		
		//PerformanceRawscoreIncludeFlag = true (Not null but TRUE)
		//Returns null
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(true);
		studentReport.setMdptScorableFlag(false);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		System.out.println("CombinedLevel testCase2_2: "+combinedLevel);
	}
	
	@Test
	public final void testCase3_1()
	{
		logger.debug("CombinedLevel testCase3_1: ");
		
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//mdptscore = null
		//mdptscoreableflag = null
		//Returns null
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScorableFlag(null);
		studentReport.setMdptScore(null);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		System.out.println("CombinedLevel testCase3_1: "+combinedLevel);
	}
	
	@Test
	public final void testCase3_2()
	{
		logger.debug("CombinedLevel testCase3_2: ");
		
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//mdptscore = null
		//mdptscoreableflag = true
		//Returns -1 : Error message: Scorable; so need score 
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScore(null);
		studentReport.setMdptScorableFlag(true);
		studentReport.setScaleScore(110L);
		
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		System.out.println("CombinedLevel testCase3_2: "+combinedLevel);
	}
	
	@Test
	public final void testCase3_3()
	{
		logger.debug("CombinedLevel testCase3_3: ");
		
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//mdptscore = null
		//mdptscoreableflag = false
		//Returns 1
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScore(null);
		studentReport.setMdptScorableFlag(false);
		studentReport.setScaleScore(110L);
		
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		System.out.println("CombinedLevel testCase3_3: "+combinedLevel);
	}
	
	
	@Test
	public final void testCase3_4()
	{
		logger.debug("CombinedLevel testCase3_4: ");
		
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//mdptscore = value
		//mdptscoreableflag = null
		//Returns -2 : Error message: Do not know if the MDPT Test scorable or not but found MDPT score
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScore(new BigDecimal("1.0"));
		studentReport.setMdptScorableFlag(null);
		studentReport.setScaleScore(110L);
		
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		System.out.println("CombinedLevel testCase3_4: "+combinedLevel);
	}
	
	
	@Test
	public final void testCase3_5()
	{
		logger.debug("CombinedLevel testCase3_5: ");
		
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//mdptscore = value
		//mdptscoreableflag = false
		//Returns -3 : Error message: Non-scorable; Found value
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScorableFlag(false);
		studentReport.setMdptScore(new BigDecimal("1.0"));
		studentReport.setScaleScore(110L);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		logger.debug("CombinedLevel testCase3_5: "+combinedLevel);
	}
	
	/*
	 * low;high;perfScale;combinedLevel
	 * 100;120;1;2
	   100;120;2;3
	   121;140;1;4
	   121;140;2.1;5
	 */
	
	@Test
	public final void testCase3_6_1()
	{
		logger.debug("CombinedLevel testCase3_6_1: ");
		
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//mdptscore = value
		//mdptscoreableflag = true
		//Returns CombinedLevel = 2
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScorableFlag(true);
		studentReport.setMdptScore(new BigDecimal("1"));
		studentReport.setScaleScore(110L);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		logger.debug("CombinedLevel testCase3_6_1: "+combinedLevel);
	}
	
	@Test
	public final void testCase3_6_2()
	{
		logger.debug("CombinedLevel testCase3_6_2: ");
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//Now code implemented
		//mdptscoreableflag = true
		//Returns CombinedLevel = 5 
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScorableFlag(true);
		studentReport.setMdptScore(new BigDecimal("2.1"));
		studentReport.setScaleScore(130L);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		logger.debug("CombinedLevel testCase3_6_2: "+combinedLevel);
	}

	@Test
	public final void testCase3_6_3()
	{
		logger.debug("CombinedLevel testCase3_6_3: ");
		//PerformanceRawscoreIncludeFlag = false (Not null and false)
		//Now code implemented
		//mdptscoreableflag = true
		//Returns CombinedLevel = null because 150 is out of scale range provided in combinedlevelmap in database.
		StudentReport studentReport = getStudentReportData();
		studentReport.setPerformanceRawscoreIncludeFlag(false);
		studentReport.setMdptScorableFlag(true);
		studentReport.setMdptScore(new BigDecimal("1"));
		studentReport.setScaleScore(150L);
		BigDecimal combinedLevel = calculateCombinedLevelOnMDPTAndScaleScores(studentReport);
		logger.debug("CombinedLevel testCase3_6_3: "+combinedLevel);
	}
	
	
	private BigDecimal calculateCombinedLevelOnMDPTAndScaleScores(StudentReport studentReport)
	{
		
		//logger.info("Inside Calculate Combined Level Based On Scale Score and MDPT Score....Student - " + studentReport.getStudentId());
		Long scaleScore = studentReport.getScaleScore();
		BigDecimal mdptScore = studentReport.getMdptScore();
		BigDecimal combinedLevel = null;
		
		CombinedLevelMapService combinedLevelMapServiceMock = mock(CombinedLevelMapServiceImpl.class);
		
		//All Scenarios are only when "Include Performance Items in Raw Score" = No 
		if(studentReport.getPerformanceRawscoreIncludeFlag() != null && !studentReport.getPerformanceRawscoreIncludeFlag())
		{
			/*
			   Performance Scale Score;	MDPT Scorable Flag;	Combined Level
				Null;	null;	null
				null;	TRUE;	error
				null;	FALSE;	set to 1
			 */
			if(studentReport.getMdptScore()==null)
			{
				if(studentReport.isMdptScorableFlag()!=null){
					if(!studentReport.isMdptScorableFlag())
					{
						combinedLevel = new BigDecimal("1");
						logger.debug("isMdptScorableFlag-false; Setting to 1");
					}
					else
					{
						String msg = "Scorable but performance score not found.";
						logger.debug(msg);
						return (new BigDecimal("-1"));
					}
				}
			}
			/*
			   Performance Scale Score;	MDPT Scorable Flag;	Combined Level
			    value;	null;	error
				value;	TRUE;	calculate
				value;	FALSE;	error
			 */
			else 
			{
				if(studentReport.isMdptScorableFlag()==null)
				{
					String msg = "MDPTScoreableFlag not set but found MDPT score of "+ mdptScore +" for student "+studentReport.getStudentId();
					logger.debug(msg);
					return (new BigDecimal("-2"));
				}
				else if(studentReport.isMdptScorableFlag()!=null && !studentReport.isMdptScorableFlag())
				{
					String msg = "MDPTScoreableFlag is false. Performance test is non-scorable but found MDPT score of "+ mdptScore +" for student "+studentReport.getStudentId();
					logger.debug(msg);
					return (new BigDecimal("-3"));
				}
				else
				{	
					List<CombinedLevelMap> combinedLevelMapData = combinedLevelMapService.getCombinedLevelBasedOnAssessmentProgramSubjectGradeYear(studentReport.getAssessmentProgramId(), 
							studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getSchoolYear());
					if(CollectionUtils.isEmpty(combinedLevelMapData)){
						String msg = "No scale score value exists in CombinedLevelMap for School Year - "+studentReport.getSchoolYear()+", Assessment Program - " + studentReport.getAssessmentProgramId() + ", GradeId - " + studentReport.getGradeId() + ", SubjectId - " + studentReport.getContentAreaId()+", Student - "+studentReport.getStudentId();
						logger.debug(msg);
						return (new BigDecimal("-4"));
						
					}else{
						for(CombinedLevelMap combinedLevelMap : combinedLevelMapData){
							if(scaleScore.longValue() >= combinedLevelMap.getStagesLowScaleScore().longValue() && 
									   scaleScore.longValue() <= combinedLevelMap.getStagesHighScaleScore().longValue() && mdptScore.compareTo(combinedLevelMap.getPerformanceScaleScore()) == 0){
								combinedLevel = combinedLevelMap.getCombinedLevel();
							}
						}
						if(combinedLevel == null){
							String msg = "Could not find Combined Level for scale score of " + scaleScore  + " and MDPT score of "+ mdptScore +" from CombinedLevelMap for student "+studentReport.getStudentId();
							logger.debug(msg);
							return (new BigDecimal("-5"));
						}
					}
				}
			}
			
			studentReport.setCombinedLevel(combinedLevel);
			
		}
		logger.debug("Completed Combined Level Based On Scale Score and MDPT Score.");
		return studentReport.getCombinedLevel();
	}
	
	private StudentReport getStudentReportData() {
		StudentReport data = new StudentReport();
		data.setAssessmentProgramCode("KAP");
		data.setStudentId(1234L);
		data.setStudentFirstName("Firstname");
		data.setStudentLastName("Lastname");
		data.setStateStudentIdentifier("23232323232");
		data.setStateId(52L);
		data.setDistrictId(62L);
		data.setAttendanceSchoolName("Lakewood Elementry School");
		data.setAttendanceSchoolId(123L);
		data.setDistrictName("Bluevally School District");
		data.setDistrictDisplayIdentifier("1242");
		data.setGradeName("Grade 3");
		data.setGradeCode("3");
		data.setContentAreaName("Mathematics");
		data.setContentAreaCode("M");
		data.setCurrentSchoolYear(2016L);
		data.setSchoolYear(2016L);
		data.setAssessmentProgramId(12L);
		data.setContentAreaId(3L);
		data.setGradeId(133L);
		data.setStandardError(new BigDecimal(".545"));
		data.setScaleScore(380l);
		data.setIncompleteStatus(true);
		//data.setLevelId(3L);
		data.setPrevLevelString("3");
		
		

		data.setLevels(new ArrayList<LevelDescription>());
		data.getLevels().add(new LevelDescription());

		data.getLevels().get(data.getLevels().size()-1).setId(1L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(1L);
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Partially Meets Standards");
		if(data.getContentAreaCode().equals("ELA")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Level 1 scores are difficult to interpret. They range from no correct answers to scores that miss Level 2 by one point. There are a number of reasons a student's performance resulted in a Level 1 score; however, most students whose scores fall into Level 1 likely have difficulty reading, analyzing, and understanding complex grade-level texts. Parents/guardians and teachers are encouraged to examine other academic information and discuss possible reasons that a student's score is in Level 1.");
		} else {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Level 1 scores are difficult to interpret. They range from no correct answers to scores that miss Level 2 by one point. There may be a number of reasons a student's performance resulted in a Level 1 score; however, a student whose score falls into Level 1 may have difficulty solving multi-step problems, comparing fractions, or solving elapsed-time problems. Parents/guardians and teachers are encouraged to examine other academic information and discuss possible reasons that a student's score is in Level 1.");
		}
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(220L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(274L);

		data.getLevels().add(new LevelDescription());
		data.getLevels().get(data.getLevels().size()-1).setId(2L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(2L);
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Partially Meets Standards");
		if(data.getContentAreaCode().equals("ELA")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who score at Level 2 can read readily accessible texts to identify main ideas and explicit details, determine meanings of common words and straightforward figurative language, identify text features and structures used to organize a text, and identify relationships between parts of a text. Students can revise or edit a text to use appropriate language and conventions, to use strategies particular to a type of text, and to structure a text to support a purpose or opinion.");
		} else {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who score at Level 2 can use basic operations, properties, rules, and strategies for solving one- and two-step problems. They can express quantities as fractions and compare fractions. Students can solve elapsed-time problems in one-minute increments. They can calculate volume and mass in metric units. Students can use information from graphs to solve problems. They can calculate area and perimeter using several strategies.");
		}
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(275L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(299L);


		data.getLevels().add(new LevelDescription());
		data.getLevels().get(data.getLevels().size()-1).setId(3L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(3L);
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Meets Standards");
		if(data.getContentAreaCode().equals("ELA")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who score at Level 3 can read moderately complex, grade-appropriate texts. In addition to proficiency in skills found at Level 2, students can determine themes and purpose of a text and meanings of more difficult words and complex figurative language; and students can identify literary elements and text structures and their impact on meaning. Students can revise and edit a text to use grade-appropriate language, conventions, and techniques to elaborate upon and structure texts logically and sequentially.");
		} else {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who score at Level 3 can use basic operations, properties, rules, and strategies for solving one- and two-step problems to find an unknown number. Students can create equivalent fractions and compare fractions. They can solve elapsed-time problems. They can calculate volume, mass, length, and temperature and use appropriate units. Students can use data to create graphs and then analyze the data to solve problems. Students can calculate and compare area and perimeter of rectilinear figures");
		}
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(300L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(334L);

		data.getLevels().add(new LevelDescription());
		data.getLevels().get(data.getLevels().size()-1).setId(4L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(4L);
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Meets Standards");
		if(data.getContentAreaCode().equals("ELA")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who score at Level 4 can read highly complex, grade-appropriate texts. In addition to proficiency in skills found at Level 3, students can summarize and analyze themes, subtopics, point of view, and purpose. Students can also extend their use of language when revising and editing a text to use more challenging vocabulary and conventions. Students who score at Level 4 are able to better evaluate and incorporate implicit details when reading or revising and editing.");
		} else {
			//data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who score at Level 4 can use basic operations, properties, rules, and strategies for solving one- and two-step problems. Students can explain patterns in arithmetic and fractions. They can use problem-solving strategies to solve problems related to time. Students can compare and contrast data from multiple graphs. They can solve problems involving area and perimeter of rectilinear figures.");

			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who score at Level 4 can add, subtract, and multiply polynomials of any degree; and use linear and quadratic equations, as well as linear inequalities, to model situations. They can graph linear and quadratic equations as well as systems of equations and inequalities. Students can solve equations for a specific quantity or variable. They can create and label graphs to represent qualitative data. Students can use appropriate statistics to interpret and explain the differences in the shape, center, and spread of data.");
		}
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(335L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(380L);
		
		/*
		 * 
		 * data.setMedianScores(new ArrayList<ReportsMedianScore>());
		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(0).setOrganizationId(123L);
		data.getMedianScores().get(0).setStandardError(new BigDecimal("3.5"));
		data.getMedianScores().get(0).setStudentCount(45);
		data.getMedianScores().get(0).setScore(randLong(220,380));

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(1).setOrganizationId(62L);
		data.getMedianScores().get(1).setStandardError(new BigDecimal("2.5"));
		data.getMedianScores().get(1).setStudentCount(145);
		data.getMedianScores().get(1).setScore(randLong(220,380));

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(2).setOrganizationId(52L);
		data.getMedianScores().get(2).setStandardError(new BigDecimal("4.5"));
		data.getMedianScores().get(2).setStudentCount(4445);
		data.getMedianScores().get(2).setScore(randLong(220,380));
		int minSubScore = 101,maxSubScore = 119;
		data.setSubscoreBuckets(new ArrayList<ReportSubscores>());
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(2L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("2,3");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(110L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1-RI");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(117l);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		
		//school
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(113l);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(123L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("2,3,4");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(123L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("5,6");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(123L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1-RI");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(123L);
		
		//district
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(101l);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("2,3,4");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("5,6");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1-RI");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);
		
		//state
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(115l);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("2,3,4");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("5,6");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1-RI");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(101l);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("5,6");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(118L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("13"));
		*/
		return data;
	}
	
	private CombinedLevelMap getCombinedLevelMapObject(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeid, BigDecimal combinedLevel, Long stagesLowScaleScore, Long stagesHighScaleScore){
		CombinedLevelMap combinedLevelMap = new CombinedLevelMap();
		
		combinedLevelMap.setSchoolYear(schoolYear);
		combinedLevelMap.setAssessmentProgram("KAP");
		combinedLevelMap.setAssessmentProgramId(assessmentProgramId);
		combinedLevelMap.setLineNumber("2");
		combinedLevelMap.setGrade("3");
		combinedLevelMap.setGradeId(gradeid);
		combinedLevelMap.setSubjectId(subjectId);
		combinedLevelMap.setSubject("ELA");
		combinedLevelMap.setCombinedLevel(combinedLevel);
		combinedLevelMap.setStagesLowScaleScore(stagesLowScaleScore);
		combinedLevelMap.setStagesHighScaleScore(stagesHighScaleScore);
		
		return combinedLevelMap;
	}

	public long randLong(int min, int max) {
		return (long) (Math.floor(Math.random() * (max - min + 1)) + min);
	}
}
