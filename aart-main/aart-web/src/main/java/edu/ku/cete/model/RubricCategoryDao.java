package edu.ku.cete.model;

import edu.ku.cete.domain.RubricCategory;
import edu.ku.cete.domain.RubricCategoryExample;
import edu.ku.cete.domain.RubricReportDto;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RubricCategoryDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int countByExample(RubricCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int deleteByExample(RubricCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int insert(RubricCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int insertSelective(RubricCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    List<RubricCategory> selectByExample(RubricCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    RubricCategory selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int updateByExampleSelective(@Param("record") RubricCategory record, @Param("example") RubricCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int updateByExample(@Param("record") RubricCategory record, @Param("example") RubricCategoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int updateByPrimaryKeySelective(RubricCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rubriccategory
     *
     * @mbggenerated Thu Sep 12 15:58:52 CDT 2013
     */
    int updateByPrimaryKey(RubricCategory record);
    
    List<RubricReportDto> getRubricByTaskVariant(@Param("taskVariantId") Long taskVariantId);

	List<RubricCategory> selectByTaskVariantId(@Param("taskVariantId") Long taskVariantId);
}