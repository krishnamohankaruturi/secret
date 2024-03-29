package edu.ku.cete.model.studentsession;

import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRulesExample;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRulesKey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TestCollectionsSessionRulesDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int countByExample(TestCollectionsSessionRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int deleteByExample(TestCollectionsSessionRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int deleteByPrimaryKey(TestCollectionsSessionRulesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int insert(TestCollectionsSessionRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int insertSelective(TestCollectionsSessionRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    List<TestCollectionsSessionRules> selectByExample(TestCollectionsSessionRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    TestCollectionsSessionRules selectByPrimaryKey(TestCollectionsSessionRulesKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int updateByExampleSelective(@Param("record") TestCollectionsSessionRules record, @Param("example") TestCollectionsSessionRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int updateByExample(@Param("record") TestCollectionsSessionRules record, @Param("example") TestCollectionsSessionRulesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int updateByPrimaryKeySelective(TestCollectionsSessionRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.test_collections_session_rules
     *
     * @mbggenerated Tue Feb 12 14:26:51 CST 2013
     */
    int updateByPrimaryKey(TestCollectionsSessionRules record);
    
    /**
     * @param testSessionId
     * @return
     */
    List<TestCollectionsSessionRules> selectByTestSession(
    		@Param("testSessionId") Long testSessionId);

	/**
	 * @param testCollectionId
	 * @return
	 */
	List<TestCollectionsSessionRules> selectByTestCollection(
			@Param("testCollectionId")
			long testCollectionId);
	
	/**
	 * @param operationalTestWindowId
	 * @return
	 */	
	List<TestCollectionsSessionRules> selectByOperationalTestWindowId(
			@Param("operationalTestWindowId")
			long operationalTestWindowId);
	
	/**
	 * @param testCollectionIds
	 * @param sessionRuleId
	 * @return
	 */
	List<Long> selectByTestCollectionIdsAndSessionRuleId(
			@Param("testCollectionIds")
			List<Long> testCollectionIds,
			@Param("sessionRuleId")Long sessionRuleId);
	
	/**
	 * @param testIds
	 * @param sessionRuleId
	 * @return
	 */
	List<Long> selectByTestIdsAndSessionRuleId(
			@Param("testIds")
			List<Long> testIds,
			@Param("sessionRuleId")Long sessionRuleId);

	List<Long> filterTestIdsOnPNP(List<Long> testIds, String pnpAttribute);

	void deleteByOperationalWindowId(@Param("operationalTestWindowId") Long operationalTestWindowId);

	List<String> selectCategoryCodeByOperationalTestWindowId(@Param("windowId") Long windowId);
	
	List<TestCollectionsSessionRules> selectSessionRulesWithCatagoryCode(
			@Param("operationalTestWindowId")
			long operationalTestWindowId);
	
}