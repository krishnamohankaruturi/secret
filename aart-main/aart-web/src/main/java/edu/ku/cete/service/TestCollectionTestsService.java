/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.content.TestCollectionTests;
import edu.ku.cete.domain.content.TestCollectionTestsExample;

/**
 * @author neil.howerton
 *
 */
public interface TestCollectionTestsService {
    /**
     * 
     *@param example
     *@return
     */
    int countByExample(TestCollectionTestsExample example);

    /**
     * 
     *@param example
     *@return
     */
    int deleteByExample(TestCollectionTestsExample example);

    /**
     * 
     *@param key
     *@return
     */
    int deleteByPrimaryKey(TestCollectionTests key);

    /**
     * 
     *@param record
     *@return
     */
    TestCollectionTests insert(TestCollectionTests record);

    /**
     * 
     *@param record
     *@return
     */
    TestCollectionTests insertSelective(TestCollectionTests record);

    /**
     * 
     *@param example
     *@return
     */
    List<TestCollectionTests> selectByExample(TestCollectionTestsExample example);

    /**
     * 
     *@param record
     *@param example
     *@return
     */
    int updateByExampleSelective(@Param("record") TestCollectionTests record, @Param("example") TestCollectionTestsExample example);

    /**
     * 
     *@param record
     *@param example
     *@return
     */
    int updateByExample(@Param("record") TestCollectionTests record, @Param("example") TestCollectionTestsExample example);
}
