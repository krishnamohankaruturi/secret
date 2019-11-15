/**
 * 
 */
package edu.ku.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.student.survey.StudentSurveyResponse;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseExample;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabel;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabelExample;
import edu.ku.cete.model.student.survey.StudentSurveyResponseDao;
import edu.ku.cete.model.student.survey.StudentSurveyResponseLabelDao;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class SurveyDaoTest {
	@Autowired
	private StudentSurveyResponseDao studentSurveyResponseDao;
	@Autowired
	private StudentSurveyResponseLabelDao studentSurveyResponseLabelDao;
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
	 * getStudentSurveyResponsesForSurvey
	 */
	@Test
	public final void getStudentSurveyResponsesForSurvey() {
		System.out.println("getStudentSurveyResponsesForSurvey");
		StudentSurveyResponseExample studentSurveyResponseExample = new StudentSurveyResponseExample();
		StudentSurveyResponseExample.Criteria
		studentSurveyResponseCriteria = studentSurveyResponseExample.createCriteria(
				).andActiveFlagEqualTo(true);
		studentSurveyResponseCriteria.andSurveyIdEqualTo((long) 1);
		List<StudentSurveyResponse> studentSurveyResponses
		= studentSurveyResponseDao.selectByExample(studentSurveyResponseExample);
		Assert.assertNotNull("No studentSurveyResponses found" + studentSurveyResponses,studentSurveyResponses);
		Assert.assertTrue("No studentSurveyResponses found" + studentSurveyResponses, studentSurveyResponses.size() > 0);
		Assert.assertTrue("Unexpected number of studentSurveyResponses found" + studentSurveyResponses, studentSurveyResponses.size() == 1);
	}


	@Test
	/**
	 * getStudentSurveyResult.
	 */
	public final void getStudentSurveyResult() {
		System.out.println("getStudentSurveyResult");
		List<StudentSurveyResponseLabel> studentSurveyResponseLabels
		= studentSurveyResponseLabelDao.selectSurveyResult((long) 1);
		Assert.assertNotNull("No studentSurveyResponses found"
		+ studentSurveyResponseLabels,studentSurveyResponseLabels);
		Assert.assertTrue("No studentSurveyResponses found"
		+ studentSurveyResponseLabels, studentSurveyResponseLabels.size() > 0);
		Assert.assertTrue("Unexpected number of studentSurveyResponses found"
		+ studentSurveyResponseLabels.size(), studentSurveyResponseLabels.size() == 284);
	}
	

	/**
	 * markStudentSurveyResult
	 */
	@Test
	public final void markStudentSurveyResult() {
		System.out.println("markStudentSurveyResult");
		List<StudentSurveyResponseLabel> existingStudentSurveyResponseLabels
		= studentSurveyResponseLabelDao.selectSurveyResult((long) 1);
		Map<String,String> givenLabelResponseMap = new HashMap<String, String>();
		givenLabelResponseMap.put("Q13_1", "1");
		for(StudentSurveyResponseLabel
				existingStudentSurveyResponseLabel: existingStudentSurveyResponseLabels) {
			existingStudentSurveyResponseLabel.setGivenLabelResponseMap(givenLabelResponseMap);
			if(existingStudentSurveyResponseLabel.isGiven()) {
				System.out.println("The student response is given during upload "
			+ existingStudentSurveyResponseLabel);
			}
		}
		for(StudentSurveyResponseLabel
				existingStudentSurveyResponseLabel: existingStudentSurveyResponseLabels) {
			if(existingStudentSurveyResponseLabel.getLabelNumber().equalsIgnoreCase("Q13_1")
					&& existingStudentSurveyResponseLabel.getResponseLabel().equalsIgnoreCase("1")) {
				Assert.assertTrue("Geven flag is not set",
						existingStudentSurveyResponseLabel.isGiven());
			} else{
				Assert.assertFalse("Geven flag is set unexpectedly",
						existingStudentSurveyResponseLabel.isGiven());				
			}
		}
	}	
	/**
	 * markStudentSurveyResult
	 */
	@Test
	public final void markActionStudentSurveyResult() {
		System.out.println("markActionStudentSurveyResult");
		List<StudentSurveyResponseLabel> existingStudentSurveyResponseLabels
		= studentSurveyResponseLabelDao.selectSurveyResult((long) 1);
		List<StudentSurveyResponseLabel>
		toBeAddedStudentSurveyResponseLabels = new ArrayList<StudentSurveyResponseLabel>();
		List<StudentSurveyResponseLabel>
		toBeRemovedStudentSurveyResponseLabels = new ArrayList<StudentSurveyResponseLabel>();
		Map<String,String> givenLabelResponseMap = new HashMap<String, String>();
		givenLabelResponseMap.put("Q14_1", "1");
		givenLabelResponseMap.put("Q37", "1");
		for(StudentSurveyResponseLabel
				existingStudentSurveyResponseLabel: existingStudentSurveyResponseLabels) {
			existingStudentSurveyResponseLabel.setGivenLabelResponseMap(givenLabelResponseMap);
			if(existingStudentSurveyResponseLabel.doAdd()) {
				toBeAddedStudentSurveyResponseLabels.add(existingStudentSurveyResponseLabel);
				System.out.println("The student response label need to be inserted" + existingStudentSurveyResponseLabel);
			}
			if(existingStudentSurveyResponseLabel.doRemove()) {
				toBeRemovedStudentSurveyResponseLabels.add(existingStudentSurveyResponseLabel);
				System.out.println("The student response label need to be removed" + existingStudentSurveyResponseLabel);
			}
		}
		Assert.assertTrue("toBeAddedList is empty", toBeAddedStudentSurveyResponseLabels.size() > 0);
		Assert.assertTrue("toBeRemovedList is empty", toBeRemovedStudentSurveyResponseLabels.size() > 0);
	}
	/**
	 * addOrRemoveStudentSurveyResult
	 */
	@Test
	public final void addOrRemoveStudentSurveyResult() {
		System.out.println("addOrRemoveStudentSurveyResult");
		List<StudentSurveyResponseLabel> existingStudentSurveyResponseLabels
		= studentSurveyResponseLabelDao.selectSurveyResult((long) 1);
		Map<String,String> givenLabelResponseMap = new HashMap<String, String>();
		givenLabelResponseMap.put("Q14_1", "1");
		givenLabelResponseMap.put("Q37", "1");
		for(StudentSurveyResponseLabel
				existingStudentSurveyResponseLabel: existingStudentSurveyResponseLabels) {
			existingStudentSurveyResponseLabel.setGivenLabelResponseMap(givenLabelResponseMap);
			if(existingStudentSurveyResponseLabel.doAdd()) {
				StudentSurveyResponse toAdd = existingStudentSurveyResponseLabel.getStudentSurveyResponse((long) 1);
				toAdd.setCreatedDate(new Date());
				toAdd.setModifiedDate(new Date());
				toAdd.setCurrentContextUserId((long) 12);
				toAdd.setCreatedUser((long) 12);
				toAdd.setModifiedUser((long) 12);
				studentSurveyResponseDao.insert(toAdd);
				System.out.println("The student response label need to be inserted" + existingStudentSurveyResponseLabel);
			}
			if(existingStudentSurveyResponseLabel.doRemove()) {
				StudentSurveyResponse toRemove = existingStudentSurveyResponseLabel.getStudentSurveyResponse((long) 1);
				toRemove.setCreatedUser((long) 12);
				toRemove.setModifiedUser((long) 12);
				toRemove.setCreatedDate(new Date());
				toRemove.setModifiedDate(new Date());
				toRemove.setCurrentContextUserId((long) 12);
				toRemove.setActiveFlag(false);
				studentSurveyResponseDao.updateByPrimaryKey(toRemove);
				System.out.println("The student response label need to be removed" + existingStudentSurveyResponseLabel);
			}
		}
	}	
}
