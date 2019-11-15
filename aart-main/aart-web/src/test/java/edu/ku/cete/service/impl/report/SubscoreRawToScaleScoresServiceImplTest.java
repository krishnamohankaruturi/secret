/**
 * 
 */
package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;


/**
 * @author n466k239
 *
 */
public class SubscoreRawToScaleScoresServiceImplTest {

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

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreRawToScaleScoresServiceImpl#deleteAllSubscoreRawToScaleScores(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteAllSubscoreRawToScaleScores() {
		//fail("Not yet implemented"); // TODO
		SubscoreRawToScaleScoresService subscoreRawToScaleScoresServiceMock = mock(SubscoreRawToScaleScoresServiceImpl.class);
		when(subscoreRawToScaleScoresServiceMock.deleteAllSubscoreRawToScaleScores(2016L)).thenReturn(1);
		int rowsDeleted = subscoreRawToScaleScoresServiceMock.deleteAllSubscoreRawToScaleScores(2016L);
		assertEquals(1, rowsDeleted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreRawToScaleScoresServiceImpl#deleteSubscoreRawToScaleScores(java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testDeleteSubscoreRawToScaleScores() {
		//fail("Not yet implemented"); // TODO
		SubscoreRawToScaleScoresService subscoreRawToScaleScoresServiceMock = mock(SubscoreRawToScaleScoresServiceImpl.class);
		when(subscoreRawToScaleScoresServiceMock.deleteSubscoreRawToScaleScores(12L, 3L, 2016L)).thenReturn(1);
		int rowsDeleted = subscoreRawToScaleScoresServiceMock.deleteSubscoreRawToScaleScores(12L, 3L, 2016L);
		assertEquals(1, rowsDeleted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreRawToScaleScoresServiceImpl#insertSelectiveRawToScaleScores(edu.ku.cete.domain.report.SubscoreRawToScaleScores)}.
	 */
	@Test
	public final void testInsertSelectiveRawToScaleScores() {
		//fail("Not yet implemented"); // TODO
		
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject(2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, 5283L, "Claim_2_all", 
				new BigDecimal("4"), 2, new BigDecimal("0.6"));
		
		SubscoreRawToScaleScoresService subscoreRawToScaleScoresServiceMock = mock(SubscoreRawToScaleScoresServiceImpl.class);
		when(subscoreRawToScaleScoresServiceMock.insertSelectiveRawToScaleScores(subscoreRawToScaleScores)).thenReturn(1);
		int rowsInserted = subscoreRawToScaleScoresServiceMock.insertSelectiveRawToScaleScores(subscoreRawToScaleScores);
		assertEquals(1, rowsInserted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreRawToScaleScoresServiceImpl#checkIfSubscoreDefinitionNameExists(java.lang.String, java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testCheckIfSubscoreDefinitionNameExists() {
		//fail("Not yet implemented"); // TODO
		SubscoreRawToScaleScores subscoreRawToScaleScores = getSubscoreRawToScaleScoresDataObject(2016L, 12L, 3L, 133L, 5248L, 5766L, 6146L, 5758L, 5283L, "Claim_2_all", 
				new BigDecimal("4"), 2, new BigDecimal("0.6"));
		
		SubscoreRawToScaleScoresService subscoreRawToScaleScoresServiceMock = mock(SubscoreRawToScaleScoresServiceImpl.class);
		boolean subscoreRawToScaleScores_duplicate = subscoreRawToScaleScoresServiceMock.checkIfSubscoreDefinitionNameExists(
				subscoreRawToScaleScores.getSubScoreDefinitionName(), subscoreRawToScaleScores.getGradeId(), subscoreRawToScaleScores.getSubjectId(), subscoreRawToScaleScores.getAssessmentProgramId());
		
		
		assertEquals(false, subscoreRawToScaleScores_duplicate);
	}

	/*
	@Test
	public final void testGetDistinctSubscoreMapping() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDistinctTestId1TestId2() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetScaleScoreForTotalRawScore() {
		fail("Not yet implemented"); // TODO
	}
	 */
	
	private SubscoreRawToScaleScores getSubscoreRawToScaleScoresDataObject(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeid, Long testId1, Long testId2, 
			Long testId3, Long testId4, Long performanceTestId, String subScoreDefinitionName, BigDecimal rawScore, int rating, BigDecimal minimumPercentResponses){
		SubscoreRawToScaleScores subscoreRawToScaleScores = new SubscoreRawToScaleScores();
		
		subscoreRawToScaleScores.setSchoolYear(schoolYear);
		subscoreRawToScaleScores.setAssessmentProgram("KAP");
		subscoreRawToScaleScores.setAssessmentProgramId(assessmentProgramId);
		subscoreRawToScaleScores.setLineNumber("2");
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
		subscoreRawToScaleScores.setRawScore(rawScore);
		subscoreRawToScaleScores.setRating(rating);
		subscoreRawToScaleScores.setMinimumPercentResponses(minimumPercentResponses);
		
		return subscoreRawToScaleScores;
	}
}
