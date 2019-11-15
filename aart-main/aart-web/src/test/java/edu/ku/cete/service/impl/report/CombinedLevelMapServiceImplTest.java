/**
 * 
 */
package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.service.report.CombinedLevelMapService;


/**
 * @author n466k239
 *
 */
public class CombinedLevelMapServiceImplTest {

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
	 * Test method for {@link edu.ku.cete.service.impl.report.CombinedLevelMapServiceImpl#insertCombinedLevelMap(edu.ku.cete.domain.CombinedLevelMap)}.
	 */
	@Test
	public final void testInsertCombinedLevelMap() {
		//fail("Not yet implemented"); // TODO
		
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject(2016L, 12L, 3L, 133L, new BigDecimal("1"), 180L, 184L);
		
		CombinedLevelMapService combinedLevelMapServiceMock = mock(CombinedLevelMapServiceImpl.class);
		when(combinedLevelMapServiceMock.insertCombinedLevelMap(combinedLevelMap)).thenReturn(1);
		int rowsInserted = combinedLevelMapServiceMock.insertCombinedLevelMap(combinedLevelMap);
		assertEquals(1, rowsInserted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.CombinedLevelMapServiceImpl#deleteCombinedLevelMap(java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testDeleteCombinedLevelMap() {
		//fail("Not yet implemented");
		CombinedLevelMapService combinedLevelMapServiceMock = mock(CombinedLevelMapServiceImpl.class);
		when(combinedLevelMapServiceMock.deleteCombinedLevelMap(12L, 3L, 2016L)).thenReturn(1);
		int rowsDeleted = combinedLevelMapServiceMock.deleteCombinedLevelMap(12L, 3L, 2016L);
		assertEquals(1, rowsDeleted);	
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.CombinedLevelMapServiceImpl#deleteAllCombinedLevelMap(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteAllCombinedLevelMap() {
		//fail("Not yet implemented");
		CombinedLevelMapService combinedLevelMapServiceMock = mock(CombinedLevelMapServiceImpl.class);
		when(combinedLevelMapServiceMock.deleteAllCombinedLevelMap(2016L)).thenReturn(1);
		int rowsDeleted = combinedLevelMapServiceMock.deleteAllCombinedLevelMap(2016L);
		assertEquals(1, rowsDeleted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.CombinedLevelMapServiceImpl#getCombinedLevelBasedOnAssessmentProgramSubjectGradeYear(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testGetCombinedLevelBasedOnAssessmentProgramSubjectGradeYear() {
		//fail("Not yet implemented"); // TODO
		CombinedLevelMap combinedLevelMap = getCombinedLevelMapObject(2016L, 12L, 3L, 133L, new BigDecimal("1"), 180L, 184L);
				
		CombinedLevelMapService combinedLevelMapServiceMock = mock(CombinedLevelMapServiceImpl.class);
		List<CombinedLevelMap> combinedLevelMapList = combinedLevelMapServiceMock.getCombinedLevelBasedOnAssessmentProgramSubjectGradeYear(combinedLevelMap.getAssessmentProgramId(), combinedLevelMap.getSubjectId(), combinedLevelMap.getGradeId(), combinedLevelMap.getSchoolYear());
		assertEquals(true, combinedLevelMapList.size()>0);
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

}
