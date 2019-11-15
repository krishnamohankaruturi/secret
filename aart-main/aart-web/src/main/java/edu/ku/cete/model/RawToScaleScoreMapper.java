package edu.ku.cete.model;

import edu.ku.cete.domain.RawToScaleScore;
import edu.ku.cete.domain.RawToScaleScoreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RawToScaleScoreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int countByExample(RawToScaleScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int deleteByExample(RawToScaleScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int insert(RawToScaleScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int insertSelective(RawToScaleScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    List<RawToScaleScore> selectByExample(RawToScaleScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    RawToScaleScore selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int updateByExampleSelective(@Param("record") RawToScaleScore record, @Param("example") RawToScaleScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int updateByExample(@Param("record") RawToScaleScore record, @Param("example") RawToScaleScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int updateByPrimaryKeySelective(RawToScaleScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table rawtoscalescore
     *
     * @mbggenerated Wed May 07 22:25:13 CDT 2014
     */
    int updateByPrimaryKey(RawToScaleScore record);
}