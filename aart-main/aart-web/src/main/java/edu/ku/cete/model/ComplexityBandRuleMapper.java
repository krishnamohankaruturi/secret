package edu.ku.cete.model;

import edu.ku.cete.domain.ComplexityBandRule;
import edu.ku.cete.domain.ComplexityBandRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ComplexityBandRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int countByExample(ComplexityBandRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int deleteByExample(ComplexityBandRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int insert(ComplexityBandRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int insertSelective(ComplexityBandRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    List<ComplexityBandRule> selectByExample(ComplexityBandRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    ComplexityBandRule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int updateByExampleSelective(@Param("record") ComplexityBandRule record, @Param("example") ComplexityBandRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int updateByExample(@Param("record") ComplexityBandRule record, @Param("example") ComplexityBandRuleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int updateByPrimaryKeySelective(ComplexityBandRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table complexitybandrules
     *
     * @mbggenerated Tue Sep 10 16:06:40 CDT 2013
     */
    int updateByPrimaryKey(ComplexityBandRule record);
}