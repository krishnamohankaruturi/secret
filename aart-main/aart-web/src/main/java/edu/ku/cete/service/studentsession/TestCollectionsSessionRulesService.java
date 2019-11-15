package edu.ku.cete.service.studentsession;

import java.util.List;

import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRulesKey;

/**
 * @author vittaly
 *
 */
public interface TestCollectionsSessionRulesService {
	   
	/**
	 * @param testCollectionsSessionRules
	 * @return
	 */
	TestCollectionsSessionRules insert(TestCollectionsSessionRules testCollectionsSessionRules);
	
	/**
	 * @param testCollectionsSessionRules
	 * @return
	 */
	TestCollectionsSessionRules updateByPrimaryKeySelective(TestCollectionsSessionRules testCollectionsSessionRules);
	
	/**
	 * @param testCollectionsSessionRulesKey
	 * @return
	 */
	TestCollectionsSessionRules selectByPrimaryKey(TestCollectionsSessionRulesKey testCollectionsSessionRulesKey);
	
	/**
	 * @param testCollectionsSessionRulesKey
	 * @return
	 */
	void deleteByPrimaryKey(TestCollectionsSessionRulesKey testCollectionsSessionRulesKey);
	
	/**
	 * @param testCollectionIds
	 * @param sessionRuleId
	 * @return
	 */
	List<Long> selectByTestCollectionIdsAndSessionRuleId(List<Long> testCollectionIds, Long sessionRuleId);
	
	/**
	 * @param testIds
	 * @param sessionRuleId
	 * @return
	 */
	List<Long> selectByTestIdsAndSessionRuleId(List<Long> testIds, Long sessionRuleId);

}
