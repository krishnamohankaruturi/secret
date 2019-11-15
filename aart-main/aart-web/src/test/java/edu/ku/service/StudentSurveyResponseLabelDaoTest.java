/**
 * 
 */
package edu.ku.service;

import java.util.List;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabel;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabelInfo;
import edu.ku.cete.domain.student.survey.SurveySectionComposite;
import edu.ku.cete.model.student.survey.StudentSurveyResponseLabelDao;
import edu.ku.cete.model.student.survey.SurveySectionDao;
import edu.ku.cete.service.student.FirstContactService;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class StudentSurveyResponseLabelDaoTest {

	@Autowired
	private StudentSurveyResponseLabelDao studentSurveyResponseLabelDao;
	@Autowired
	private SurveySectionDao surveySectionDao;
	@Autowired
	private FirstContactService firstContactService;
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
	 * selectAllowedStudentSurveyResponseLabels
	 */
	@Test
	public final void selectAllowedStudentSurveyResponseLabels() {
		
		System.out.println("selectAllowedStudentSurveyResponseLabels");
		List<StudentSurveyResponseLabelInfo> studentSurveyResponseLabelInfo
		= studentSurveyResponseLabelDao.selectAllowedStudentSurveyResponseLabels((long) 1,1);
		for(StudentSurveyResponseLabel
				existingStudentSurveyResponseLabel : studentSurveyResponseLabelInfo) {
			Assert.assertNotNull("Empty student survey", existingStudentSurveyResponseLabel);
			Assert.assertNotNull("Empty pagenum",
					existingStudentSurveyResponseLabel.getGlobalPageNum());
			Assert.assertNotNull("Empty optional flag",
					existingStudentSurveyResponseLabel.isOptional());
			Assert.assertTrue("Empty optional flag",
					existingStudentSurveyResponseLabel.isOptional());
		}
		Assert.assertNotNull("No results found", studentSurveyResponseLabelInfo);
		Assert.assertTrue("No results found", studentSurveyResponseLabelInfo.size() > 0);
		System.out.println("existingStudentSurveyResponseLabels"
		+ studentSurveyResponseLabelInfo);
	}	
	/**
	 * selectSurveySections
	 */
	@Test
	public final void selectSurveySections() {
		
		System.out.println("selectSurveySections");
		List<SurveySectionComposite> surveySections
		= surveySectionDao.selectCompositeByExample(null);
		Assert.assertNotNull("No Survey sections are found " + surveySections);
		Assert.assertEquals("No Survey sections are found " , 15, surveySections.size() );
		for(SurveySectionComposite surveySection:surveySections) {
			if(!surveySection.getSurveySectionCode().equalsIgnoreCase("STUDENT_ID")
					&& !surveySection.getSurveySectionCode().equalsIgnoreCase("STATE")
					&& !surveySection.getSurveySectionCode().equalsIgnoreCase("COMMUNICATION")
					&& !surveySection.getSurveySectionCode().equalsIgnoreCase("AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION")
					&& !surveySection.getSurveySectionCode().equalsIgnoreCase("ACADEMIC")
					&& !surveySection.getSurveySectionCode().equalsIgnoreCase("ATTENTION_UNDERSTANDING_INSTRUCTION_HEALTH")
					) {
				Assert.assertNotNull("Parent is not set "
					+ surveySection,surveySection.getParentSurveySectionId());
			}
		}

	}		
	/**
	 * selectSurveySectionsWithParent
	 */
	@Test
	public final void selectSurveySectionsWithParent() {
		
		System.out.println("selectSurveySectionsWithParent");
		List<SurveySectionComposite> surveySections
		= surveySectionDao.selectCompositeByExample(null);
		for(SurveySectionComposite surveySection:surveySections) {
			surveySection.addChildSurveySection(surveySections);
		}
		for(SurveySectionComposite existingSurveySection:surveySections) {
			if(existingSurveySection.getParentSurveySectionId() == null
					&& !existingSurveySection.getSurveySectionCode().equalsIgnoreCase("STUDENT_ID")
					&& !existingSurveySection.getSurveySectionCode().equalsIgnoreCase("STATE")
					&& !existingSurveySection.getSurveySectionCode().equalsIgnoreCase("AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION")
					) {
				Assert.assertNotNull("Child sections are not set.",
						existingSurveySection.getChildSurveySections());
				Assert.assertTrue("Child sections are not set." + existingSurveySection,
						existingSurveySection.getChildSurveySections().size() > 0);
				System.out.println("For the section "
						+ existingSurveySection + "children are "
						+ existingSurveySection.getChildSurveySections());
				}
		}

	}	
	
	/**
	 * addOrRemoveStudentSurveyResult
	 */
	@Test
	public final void selectFCSurveySections() {
		
		System.out.println("selectFCSurveySections");
		List<SurveySectionComposite> surveySections
		= firstContactService.selectAllSurveySections();

		for(SurveySectionComposite existingSurveySection:surveySections) {
			if(existingSurveySection.getParentSurveySectionId() == null
					&& !existingSurveySection.getSurveySectionCode().equalsIgnoreCase("STUDENT_ID")
					&& !existingSurveySection.getSurveySectionCode().equalsIgnoreCase("STATE")
					&& !existingSurveySection.getSurveySectionCode().equalsIgnoreCase("AUGMENTATIVE_OR_ALTERNATE_COMMUNICATION")
					) {
				Assert.assertNotNull("Child sections are not set.",
						existingSurveySection.getChildSurveySections());
				Assert.assertTrue("Child sections are not set." + existingSurveySection,
						existingSurveySection.getChildSurveySections().size() > 0);
				Assert.assertTrue("Child sections are not set." + existingSurveySection,
						existingSurveySection.getChildSurveySections().size() == 3);
				System.out.println("For the section "
						+ existingSurveySection + "children are "
						+ existingSurveySection.getChildSurveySections());
				}
		}

	}	
}
