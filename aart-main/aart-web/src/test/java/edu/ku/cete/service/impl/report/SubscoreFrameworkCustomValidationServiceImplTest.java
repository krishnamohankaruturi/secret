package edu.ku.cete.service.impl.report;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.SubscoreFramework;
import edu.ku.cete.domain.report.SubscoresDescription;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.impl.GradeCourseServiceImpl;
import edu.ku.cete.test.BaseTest;

public class SubscoreFrameworkCustomValidationServiceImplTest extends BaseTest{

	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testCustomValidation() {
		//fail("Not yet implemented"); // TODO
	}

	
	@Test
	public void testSchoolYear() {
		SubscoreFramework subscoreFramework1 = getSubscoreFrameworkDataObject(2015L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		SubscoreFramework subscoreFramework2 = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_2_all", "AKCT-ELA", "ELA.3.C2", "All", "All");
		assertNotEquals("All school years in the file are not the same school year.", subscoreFramework1.getSchoolYear(), subscoreFramework2.getSchoolYear());
	}
	
	@Test
	public void testSchoolYearNotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(null, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNull("Value not specified for field School_Year", subscoreFramework.getSchoolYear());
		
	}
	
	@Test
	public void testAssessmentProgramNotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field Assessment_Program", subscoreFramework.getAssessmentProgram());
	}
	
	@Test
	public void testSubjectNotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field Subject", subscoreFramework.getSubjectId());
	}
	@Test
	public void testGradeNotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field Subject", subscoreFramework.getSubjectId());
	}
	
	@Test
	public void testSubScoreDefinitionName() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field SubScoreDefinitionName", subscoreFramework.getSubScoreDefinitionName());
	}
	
	@Test
	public void testFrameworkNotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field Framework", subscoreFramework.getFramework());
	}
	@Test
	public void testFrameworkLevel1NotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field FrameworkLevel1", subscoreFramework.getFrameworkLevel1());
	}
	@Test
	public void testFrameworkLevel2NotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field FrameworkLevel2", subscoreFramework.getFrameworkLevel2());
	}
	@Test
	public void testFrameworkLevel3NotNull() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		assertNotNull("Value not specified for field FrameworkLevel3", subscoreFramework.getFrameworkLevel3());
	}
		
	@Test
	public void validateAssessmentProgram() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		String assessmentProgramCodeOnUI = "KAP";
		assertEquals("Assessment program is invalid", assessmentProgramCodeOnUI, subscoreFramework.getAssessmentProgram());
	}
		
	@Test
	public void validateSubject() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 37L, 3L, 133L,"Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		String subjectIdOnUI = "ELA";
		assertEquals("Subject is invalid", subjectIdOnUI, subscoreFramework.getSubject());
	}
	
	@Test
	public void validateGradeCourse() {
		SubscoreFramework subscoreFramework = getSubscoreFrameworkDataObject(2016L, 12L, 3L, 133L, "Claim_1_all", "AKCT-ELA", "ELA.3.C1", "All", "All");
		GradeCourse gradeCourse = null;
		Long subjectId = 3L; 
		gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, subscoreFramework.getGrade());
		assertNotNull("Grade is invalid for subject specified", gradeCourse);
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
