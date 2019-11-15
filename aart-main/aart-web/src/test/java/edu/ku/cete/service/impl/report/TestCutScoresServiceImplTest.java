package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.model.TestCutScoresMapper;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.TestCutScoresService;
import edu.ku.cete.test.BaseTest;

public class TestCutScoresServiceImplTest extends BaseTest {

	@Autowired
	private TestCutScoresMapper testCutScoresMapper;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDeleteAllTestCutScores() {
		//fail("Not yet implemented");
		TestCutScoresService testCutScoresServiceeMock = mock(TestCutScoresServiceImpl.class);
		when(testCutScoresServiceeMock.deleteAllTestCutScores(2016L)).thenReturn(1);
		int rowsDeleted = testCutScoresServiceeMock.deleteAllTestCutScores(2016L);
		assertEquals(1, rowsDeleted);
	}

	@Test
	public void testDeleteTestCutScores() {
		//fail("Not yet implemented");
		TestCutScoresService testCutScoresServiceeMock = mock(TestCutScoresServiceImpl.class);
		when(testCutScoresServiceeMock.deleteTestCutScores(12L, 3L, 2016L, 18L, null)).thenReturn(1);
		int rowsDeleted = testCutScoresServiceeMock.deleteTestCutScores(12L, 3L, 2016L, 18L, null);
		assertEquals(1, rowsDeleted);
	}

	@Test
	public void testInsertSelectiveTestCutScores() {
		//fail("Not yet implemented");
		//Case 1: Removed all testID columns for 2016 - not needed
		//Expected Result: Pass
		TestCutScores testCutScores = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		
		TestCutScoresService testCutScoresServiceeMock = mock(TestCutScoresServiceImpl.class);
		when(testCutScoresServiceeMock.insertSelectiveTestCutScores(testCutScores)).thenReturn(1);
		int rowsInserted = testCutScoresServiceeMock.insertSelectiveTestCutScores(testCutScores);
		assertEquals(1, rowsInserted);
	}

	@Test
	public void testCheckDuplicateTestIdsWithLevel() {
		//fail("Not yet implemented");
		TestCutScores testCutScores = getTestCutScoresDataObject(2016L, 12L, 3L, 133L, 1L, 180L, 184L,"");
		
		TestCutScoresService testCutScoresServiceeMock = mock(TestCutScoresServiceImpl.class);
		TestCutScores testCutScores_duplicate = testCutScoresServiceeMock.checkDuplicateLevels(testCutScores.getLevel(), testCutScores.getAssessmentProgramId(), testCutScores.getSubjectId(), testCutScores.getSchoolYear(), testCutScores.getDomain(), 18L, null);
		assertNull("No duplicates found", testCutScores_duplicate);
	}

	@Test
	public void testGetByTestId1TestId2() {
		//fail("Not yet implemented");
		//Used in calculations. Not needed to test the upload validations
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
