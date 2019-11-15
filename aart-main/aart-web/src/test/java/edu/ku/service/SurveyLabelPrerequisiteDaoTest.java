/**
 * 
 */
package edu.ku.service;

import java.util.Collection;
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

import edu.ku.cete.domain.student.survey.SurveyLabelPreRequisiteInfo;
import edu.ku.cete.domain.student.survey.SurveyLabelPrerequisite;
import edu.ku.cete.model.student.survey.SurveyLabelPrerequisiteDao;
import edu.ku.cete.service.student.FirstContactService;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class SurveyLabelPrerequisiteDaoTest {
	
	@Autowired
	private SurveyLabelPrerequisiteDao surveyLabelPrerequisiteDao;
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
		List<SurveyLabelPrerequisite> surveyLabelPrerequisites
		= surveyLabelPrerequisiteDao.selectByExample(3);
		for(SurveyLabelPrerequisite
				surveyLabelPrerequisite : surveyLabelPrerequisites) {
			Assert.assertNotNull("Empty student survey", surveyLabelPrerequisite);
		}
		Assert.assertNotNull("No results found", surveyLabelPrerequisites);
		Assert.assertTrue("No results found", surveyLabelPrerequisites.size() > 0);
		System.out.println("surveyLabelPrerequisites"
		+ surveyLabelPrerequisites);
	}	
	/**
	 * selectAllowedStudentSurveyResponseLabels
	 */
	@Test
	public final void selectAllowedStudentSurveyResponseLabelInfos() {
		
		System.out.println("selectAllowedStudentSurveyResponseLabelInfos");
		List<SurveyLabelPrerequisite> surveyLabelPrerequisites
		= surveyLabelPrerequisiteDao.selectByExample(3);
		System.out.println("surveyLabelPrerequisites"
		+ surveyLabelPrerequisites);
		Map<Long,SurveyLabelPreRequisiteInfo>
		surveyLabelPreRequisiteInfoMap = new HashMap<Long, SurveyLabelPreRequisiteInfo>();
		for(SurveyLabelPrerequisite
				surveyLabelPrerequisite : surveyLabelPrerequisites) {
			if(!surveyLabelPreRequisiteInfoMap.containsKey(
					surveyLabelPrerequisite.getSurveyLabelId())) {
				SurveyLabelPreRequisiteInfo
				surveyLabelPreRequisiteInfo = SurveyLabelPreRequisiteInfo.getInstance(
						surveyLabelPrerequisite);
				surveyLabelPreRequisiteInfoMap.put(
						surveyLabelPrerequisite.getSurveyLabelId(),
						surveyLabelPreRequisiteInfo
						);
			} else {
				surveyLabelPreRequisiteInfoMap.get(
						surveyLabelPrerequisite.getSurveyLabelId()
						).
						addSurveyResponseId(surveyLabelPrerequisite);			
			}

		}
		Assert.assertNotNull("No infos are formed", surveyLabelPreRequisiteInfoMap);
		Assert.assertNotNull("No infos are formed", surveyLabelPreRequisiteInfoMap.values());
		for(SurveyLabelPreRequisiteInfo
				surveyLabelPreRequisiteInfo:surveyLabelPreRequisiteInfoMap.values(
						)) {
			Assert.assertNotNull("Blank info", surveyLabelPreRequisiteInfo);
			System.out.println("surveyLabelPreRequisiteInfo" + surveyLabelPreRequisiteInfo);
		}


	}
	/*@Test
	public final void selectFCAllowedStudentSurveyResponseLabelInfos() {
		
		System.out.println("selectAllowedStudentSurveyResponseLabelInfos");
		Collection<SurveyLabelPreRequisiteInfo> surveyLabelPrerequisiteInfos
		= firstContactService.selectAllowedStudentSurveyResponseLabelInfos();
		System.out.println("surveyLabelPrerequisites"
		+ surveyLabelPrerequisiteInfos);
		Assert.assertNotNull("No infos are formed", surveyLabelPrerequisiteInfos);
		for(SurveyLabelPreRequisiteInfo
				surveyLabelPreRequisiteInfo:surveyLabelPrerequisiteInfos) {
			Assert.assertNotNull("Blank info", surveyLabelPreRequisiteInfo);
			System.out.println("surveyLabelPreRequisiteInfo" + surveyLabelPreRequisiteInfo);
		}


	}*/	
}
