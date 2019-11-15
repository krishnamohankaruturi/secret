/**
 * 
 */
package edu.ku.service;

import java.util.Date;
import java.util.List;
import junit.framework.Assert;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.student.survey.SurveyPageStatus;
import edu.ku.cete.domain.student.survey.SurveyPageStatusExample;
import edu.ku.cete.model.student.survey.SurveyPageStatusDao;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class SurveyPageStatusDaoTest {
	
	@Autowired
	private SurveyPageStatusDao surveyPageStatusDao;
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
	 * selectPageStatuses
	 */
	@Test
	public final void selectPageStatuses() {
		
		System.out.println("selectPageStatuses");
		List<SurveyPageStatus> surveyPageStatuses
		= surveyPageStatusDao.selectByExample(null);
		for(SurveyPageStatus
				surveyPageStatus : surveyPageStatuses) {
			Assert.assertNotNull("Empty page status", surveyPageStatus);
			Assert.assertNotNull("Empty global page num", surveyPageStatus.getGlobalPageNum());
			Assert.assertTrue("Empty global page num", surveyPageStatus.getGlobalPageNum()>0);
		}
		Assert.assertNotNull("No results found", surveyPageStatuses);
		Assert.assertTrue("No results found", surveyPageStatuses.size() > 0);
		System.out.println("surveyPageStatuses"
		+ surveyPageStatuses);
	}
	/**
	 * selectPageStatuses
	 */
	@Test
	public final void selectByExample() {
		
		System.out.println("selectByExample");
		SurveyPageStatusExample surveyPageStatusExample = new SurveyPageStatusExample();
		SurveyPageStatusExample.Criteria surveyPageStatusCriteria = surveyPageStatusExample.createCriteria();
		surveyPageStatusCriteria.andSurveyIdEqualTo((long) 1);
		surveyPageStatusCriteria.andGlobalPageNumEqualTo(1);
		List<SurveyPageStatus> surveyPageStatuses
		= surveyPageStatusDao.selectByExample(surveyPageStatusExample);
		for(SurveyPageStatus
				surveyPageStatus : surveyPageStatuses) {
			Assert.assertNotNull("Empty page status", surveyPageStatus);
			Assert.assertNotNull("Empty global page num", surveyPageStatus.getGlobalPageNum());
			Assert.assertTrue("Empty global page num", surveyPageStatus.getGlobalPageNum()>0);
		}
		Assert.assertNotNull("No results found", surveyPageStatuses);
		Assert.assertTrue("No results found", surveyPageStatuses.size() == 1);
		System.out.println("surveyPageStatuses"
		+ surveyPageStatuses);
	}
	/**
	 * selectPageStatuses
	 */
	@Test
	public final void testUpdate() {
		
		System.out.println("createOrUpdate");
		SurveyPageStatusExample surveyPageStatusExample = new SurveyPageStatusExample();
		SurveyPageStatusExample.Criteria surveyPageStatusCriteria = surveyPageStatusExample.createCriteria();
		surveyPageStatusCriteria.andSurveyIdEqualTo((long) 1);
		surveyPageStatusCriteria.andGlobalPageNumEqualTo(1);
		List<SurveyPageStatus> surveyPageStatuses
		= surveyPageStatusDao.selectByExample(surveyPageStatusExample);
		for(SurveyPageStatus
				surveyPageStatus : surveyPageStatuses) {
			Assert.assertNotNull("Empty page status", surveyPageStatus);
			Assert.assertNotNull("Empty global page num", surveyPageStatus.getGlobalPageNum());
			Assert.assertTrue("Empty global page num", surveyPageStatus.getGlobalPageNum()>0);
			surveyPageStatus.setIsCompleted(false);
			surveyPageStatusDao.updateByPrimaryKey(surveyPageStatus);
		}
		Assert.assertNotNull("No results found", surveyPageStatuses);
		Assert.assertTrue("No results found", surveyPageStatuses.size() ==1);
		System.out.println("surveyPageStatuses"
		+ surveyPageStatuses);
	}	
	/**
	 * selectPageStatuses
	 */
	@Test
	public final void testPageWiseStatus() {
		
		System.out.println("testPageWiseStatus");
		Boolean isComplete
		= surveyPageStatusDao.checkCompletionBySurveyAndPageNum(1, (long) 1, 35999L, true);
		Assert.assertNull("No results found", isComplete);
		//Assert.assertTrue("No results found", isNotComplete);
		System.out.println("isComplete"
		+ isComplete);
	}		

	/**
	 * selectPageStatuses
	 */
	@Test
	public final void checkPageWiseStatus() {
		
		System.out.println("testPageWiseStatus");
		Boolean isComplete
		= surveyPageStatusDao.checkCompletionBySurveyAndPageNum(1, (long) 1, 35999L, true);
		Assert.assertNull("No results found", isComplete);
		//Assert.assertTrue("No results found", isComplete);
		System.out.println("isComplete"
		+ isComplete);
		System.out.println("createOrUpdate");
		
		SurveyPageStatusExample surveyPageStatusExample = new SurveyPageStatusExample();
		SurveyPageStatusExample.Criteria surveyPageStatusCriteria = surveyPageStatusExample.createCriteria();
		surveyPageStatusCriteria.andSurveyIdEqualTo((long) 1);
		surveyPageStatusCriteria.andGlobalPageNumEqualTo(1);
		List<SurveyPageStatus> surveyPageStatuses
		= surveyPageStatusDao.selectByExample(surveyPageStatusExample);	
		if(surveyPageStatuses != null
				&& CollectionUtils.isNotEmpty(surveyPageStatuses)
				&& surveyPageStatuses.size() == 1) {
			SurveyPageStatus surveyPageStatus = surveyPageStatuses.get(0);
			if(surveyPageStatus.getIsCompleted().equals(isComplete)) {
				//NO updates needed
				System.out.println("No updates needed.");
			} else {
				System.out.println("Update complete flag to "+isComplete);
				surveyPageStatus.setIsCompleted(isComplete);
				surveyPageStatusDao.updateByPrimaryKey(surveyPageStatus);
			}
		} else if(surveyPageStatuses == null
				|| CollectionUtils.isEmpty(surveyPageStatuses)) {
			System.out.println("insert with complete flag as "+isComplete);
			SurveyPageStatus newSurveyPageStatus = new SurveyPageStatus();
			newSurveyPageStatus.setActiveFlag(true);
			newSurveyPageStatus.setGlobalPageNum(1);
			newSurveyPageStatus.setCreatedUser(12L);
			newSurveyPageStatus.setCreatedDate(new Date());
			newSurveyPageStatus.setModifiedUser(12L);
			newSurveyPageStatus.setModifiedDate(new Date());
			newSurveyPageStatus.setIsCompleted(isComplete);
			newSurveyPageStatus.setSurveyId((long) 1);
			surveyPageStatusDao.insert(newSurveyPageStatus);
		}
		else {
			System.err.println("Multiple statuses found");
		}
		
	}		
	
}
