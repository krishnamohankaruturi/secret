/**
 * 
 */
package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.SubscoreFramework;
import edu.ku.cete.service.report.SubscoreFrameworkService;

/**
 * @author n466k239
 *
 */
public class SubscoreFrameworkServiceImplTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreFrameworkServiceImpl#deleteAllSubscoreFramework(java.lang.Long)}.
	 */
	@Test
	public final void testDeleteAllSubscoreFramework() {
		//fail("Not yet implemented");
		SubscoreFrameworkService subscoreFrameworkServiceMock = mock(SubscoreFrameworkServiceImpl.class);
		when(subscoreFrameworkServiceMock.deleteAllSubscoreFramework(2016L)).thenReturn(1);
		int rowsDeleted = subscoreFrameworkServiceMock.deleteAllSubscoreFramework(2016L);
		assertEquals(1, rowsDeleted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreFrameworkServiceImpl#deleteSubscoreFrameworks(java.lang.Long, java.lang.Long, java.lang.Long)}.
	 */
	@Test
	public final void testDeleteSubscoreFrameworks() {
		//fail("Not yet implemented");
		SubscoreFrameworkService subscoreFrameworkServiceMock = mock(SubscoreFrameworkServiceImpl.class);
		when(subscoreFrameworkServiceMock.deleteSubscoreFrameworks(12L, 3L, 2016L)).thenReturn(1);
		int rowsDeleted = subscoreFrameworkServiceMock.deleteSubscoreFrameworks(12L, 3L, 2016L);
		assertEquals(1, rowsDeleted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreFrameworkServiceImpl#insertSelectiveSubscoreFramework(edu.ku.cete.domain.report.SubscoreFramework)}.
	 */
	@Test
	public final void testInsertSelectiveSubscoreFramework() {
		//fail("Not yet implemented"); // TODO
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,
				"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		
		SubscoreFrameworkService subscoreFrameworkServiceMock = mock(SubscoreFrameworkServiceImpl.class);
		when(subscoreFrameworkServiceMock.insertSelectiveSubscoreFramework(subscoreFramework)).thenReturn(1);
		int rowsInserted = subscoreFrameworkServiceMock.insertSelectiveSubscoreFramework(subscoreFramework);
		assertEquals(1, rowsInserted);
	}

	/**
	 * Test method for {@link edu.ku.cete.service.impl.report.SubscoreFrameworkServiceImpl#selectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testSelectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2() {
		//fail("Not yet implemented"); // TODO
		//fail("Not yet implemented");
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		
		SubscoreFrameworkService subscoreFrameworkServiceMock = mock(SubscoreFrameworkServiceImpl.class);
		when(subscoreFrameworkServiceMock.selectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2
				(2016L, 37L, 3L, 133L, "Claim_2_all", "AKCT-ELA", "ELA.3.C2", "All", "All")).thenReturn(subscoreFramework);
		SubscoreFramework subscoreFramework_duplicate = subscoreFrameworkServiceMock.selectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2
				(2016L, 37L, 3L, 133L, "Claim_2_all", "AKCT-ELA", "ELA.3.C2", "All", "All");
		assertNotNull("Duplicates found", subscoreFramework_duplicate);
	}

	private SubscoreFramework getSubscoreFrameworkDataObject(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeid,  
				String subScoreDefinitionName, String framework, String frameworkLevel1, String frameworkLevel2, String frameworkLevel3){
		SubscoreFramework subscoreFramework = new SubscoreFramework();
		
		subscoreFramework.setSchoolYear(schoolYear);
		subscoreFramework.setAssessmentProgram("KAP");
		subscoreFramework.setAssessmentProgramId(assessmentProgramId);
		subscoreFramework.setSubjectId(subjectId);
		subscoreFramework.setSubject("ELA");
		subscoreFramework.setGrade("3");
		subscoreFramework.setGradeId(gradeid);
		subscoreFramework.setLineNumber("2");
		subscoreFramework.setSubScoreDefinitionName(subScoreDefinitionName);
		subscoreFramework.setFramework(framework);
		subscoreFramework.setFrameworkLevel1(frameworkLevel1);
		subscoreFramework.setFrameworkLevel2(frameworkLevel2);
		subscoreFramework.setFrameworkLevel3(frameworkLevel3);
		
		
		return subscoreFramework;
	}

}
