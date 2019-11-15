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

import edu.ku.cete.domain.lm.TaskVariantLearningMapNode;
import edu.ku.cete.domain.lm.TaskVariantLearningMapNodeExample;
import edu.ku.cete.model.lm.TaskVariantLearningMapNodeDao;

/**
 * @author mahesh
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class TaskVariantLearningMapDaoTest {
	/**
	 * 
	 */
	@Autowired
	private TaskVariantLearningMapNodeDao taskVariantLearningMapDao;
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
		List<TaskVariantLearningMapNode>
		taskVariantLearningMapNodeList
		= taskVariantLearningMapDao.selectByExample(
				new TaskVariantLearningMapNodeExample());
		Assert.assertNotNull("No TaskVariantLearningMapNodes found",
				taskVariantLearningMapNodeList);
		Assert.assertTrue("No TaskVariantLearningMapNodes found",
				taskVariantLearningMapNodeList.size() > 0);
		Assert.assertTrue("Unexpected TaskVariantLearningMapNodes found "
				+  taskVariantLearningMapNodeList.size(),
				taskVariantLearningMapNodeList.size() == 1726);
		
	}
}
