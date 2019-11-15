/**
 * 
 */
package edu.ku.service;

import static org.junit.Assert.*;

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

import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRulesExample;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class TestCollectionsSessionRulesDaoTest {
	/**
	 * 
	 */
	@Autowired
	private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
	@Autowired
	private StudentSessionRuleConverter studentSessionRuleConverter;

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

	@Test
	public final void test() {
		List<TestCollectionsSessionRules>
		testCollectionsSessionRulesList = testCollectionsSessionRulesDao.selectByExample(
				new TestCollectionsSessionRulesExample());
		Assert.assertNotNull("No rules found", testCollectionsSessionRulesList);
		Assert.assertTrue("No rules found", testCollectionsSessionRulesList.size() > 0);
		Assert.assertTrue("Unexpected rules found " +  testCollectionsSessionRulesList.size(),
				testCollectionsSessionRulesList.size() == 20);
		
	}
	@Test
	public final void testConversion() {
		List<TestCollectionsSessionRules>
		testCollectionsSessionRulesList = testCollectionsSessionRulesDao.selectByExample(
				new TestCollectionsSessionRulesExample());
		List<StudentSessionRule>
		studentSessionRules = studentSessionRuleConverter.convert(testCollectionsSessionRulesList);
		Assert.assertNotNull("No rules found", studentSessionRules);
		Assert.assertTrue("No rules found", studentSessionRules.size() > 0);
		Assert.assertTrue("Unexpected rules found " +  studentSessionRules.size(),
				studentSessionRules.size() == 101);		
	}
	@Test
	public final void testSelectByTestSessionConversion() {
		List<TestCollectionsSessionRules>
		testCollectionsSessionRulesList = testCollectionsSessionRulesDao.selectByTestSession(
				(long) 526);
		List<StudentSessionRule>
		studentSessionRules = studentSessionRuleConverter.convert(testCollectionsSessionRulesList);
		Assert.assertNotNull("No rules found", studentSessionRules);
		Assert.assertTrue("No rules found", studentSessionRules.size() > 0);
		Assert.assertTrue("More than 1 rule found " +  studentSessionRules.size(),
				studentSessionRules.size() == 1);		
		Assert.assertNotNull("Time out is not set " +  studentSessionRules.get(0),
				studentSessionRules.get(0).getGracePeriod());	
		Assert.assertTrue("Time out is not set " +  studentSessionRules.get(0),
				studentSessionRules.get(0).getGracePeriod() > 1);
	}	
}
