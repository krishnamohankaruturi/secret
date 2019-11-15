package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.model.RawToScaleScoresMapper;
import edu.ku.cete.service.report.RawToScaleScoresService;
import edu.ku.cete.test.BaseTest;

public class RawToScaleScoresServiceImplTest extends BaseTest {

	final static Log logger = LogFactory.getLog(RawToScaleScoresServiceImplTest.class);

	@Autowired
	private RawToScaleScoresMapper rawToScaleScoresMapper;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;

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
	public final void testDeleteAllRawToScaleScores() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDeleteRawToScaleScores() {
		rawToScaleScoresService.deleteRawToScaleScores(12L, 3L, 2016L,18L,null);
		logger.debug("Delete records successfully");
	}

	@Test
	public final void testInsertSelectiveRawToScaleScores() {
		//fail("Not yet implemented"); // TODO
		RawToScaleScores rawToScaleScores = new RawToScaleScores();
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
		rawToScaleScores.setPerformanceItemWeight(new BigDecimal("1.00"));
		
		rawToScaleScores.setRawScore(new BigDecimal("20"));
		rawToScaleScores.setScaleScore(275L);
		rawToScaleScores.setStandardError(new BigDecimal("2.5"));
		rawToScaleScoresService.insertSelectiveRawToScaleScores(rawToScaleScores);
		logger.debug("Inserted successfully");
	}
	
	@Test
	public final void testCheckDuplicateTestIdsWithRawScore() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDistinctTestId1TestId2() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetByTestId1TestId2RawScore() {
		fail("Not yet implemented"); // TODO
	}
}
