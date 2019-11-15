package edu.ku.cete.service.impl.studentsession;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRulesKey;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.service.studentsession.TestCollectionsSessionRulesService;

/**
 * @author vittaly
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestCollectionsSessionRulesServiceImpl implements TestCollectionsSessionRulesService{

	private Logger logger = LoggerFactory.getLogger(TestCollectionsSessionRulesServiceImpl.class);

    @Autowired
    private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
    
	/**
	 * @param testCollectionsSessionRules
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestCollectionsSessionRules insert(TestCollectionsSessionRules testCollectionsSessionRules) {
		logger.debug("Entering the insert() method");
		
		testCollectionsSessionRulesDao.insert(testCollectionsSessionRules);
		
		logger.debug("Leaving the insert() method");
        return testCollectionsSessionRules;
    }
	
	/**
	 * Updates testcollectionssessionrules table based on non-null values passed 
	 * @param testCollectionsSessionRules
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final TestCollectionsSessionRules updateByPrimaryKeySelective(TestCollectionsSessionRules testCollectionsSessionRules) {
		logger.debug("Entering the updateByPrimaryKeySelective() method");
		
		testCollectionsSessionRulesDao.updateByPrimaryKeySelective(testCollectionsSessionRules);
		
		logger.debug("Leaving the updateByPrimaryKeySelective() method");
		return testCollectionsSessionRules;
	}
	
	/**
	 * Selects testcollectionssessionrules table based on primarykey values passed 
	 * @param testCollectionsSessionRulesKey
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final TestCollectionsSessionRules selectByPrimaryKey(TestCollectionsSessionRulesKey testCollectionsSessionRulesKey) {
		logger.debug("Inside selectByPrimaryKey() method");
		return testCollectionsSessionRulesDao.selectByPrimaryKey(testCollectionsSessionRulesKey);		
	}
	
	/**
	 * Deletes testcollectionssessionrules table based on primarykey values passed
	 * @param testCollectionsSessionRulesKey
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void deleteByPrimaryKey(TestCollectionsSessionRulesKey testCollectionsSessionRulesKey) {
		logger.debug("Inside deleteByPrimaryKey() method");
		testCollectionsSessionRulesDao.deleteByPrimaryKey(testCollectionsSessionRulesKey);		
	}
	
	/**
	 * Selects testcollectionssessionrules table based on testCollectionIds list and sessionRuleId values passed. 
	 * @param testCollectionIds
	 * @param sessionRuleId
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> selectByTestCollectionIdsAndSessionRuleId(List<Long> testCollectionIds, Long sessionRuleId) {
		logger.debug("Inside selectByTestCollectionIdsAndSessionRuleId() method");
		return testCollectionsSessionRulesDao.selectByTestCollectionIdsAndSessionRuleId(
    			new ArrayList<Long>(testCollectionIds), sessionRuleId);	
	}
	
	
	/**
	 * Selects testcollectionssessionrules table based on testIds list and sessionRuleId values passed. 
	 * @param testIds
	 * @param sessionRuleId
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> selectByTestIdsAndSessionRuleId(List<Long> testIds, Long sessionRuleId) {
		
		if(testIds != null &&
				CollectionUtils.isNotEmpty(testIds)) {
			return testCollectionsSessionRulesDao.selectByTestIdsAndSessionRuleId(
					new ArrayList<Long>(testIds), sessionRuleId);
		} else {
			return null;
		}
	}
	
}
