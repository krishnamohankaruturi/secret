package edu.ku.cete.model;

import edu.ku.cete.domain.ItiTestSessionSensitivityTags;
import edu.ku.cete.domain.ItiTestSessionSensitivityTagsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItiTestSessionSensitivityTagsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ititestsessionsensitivitytags
     *
     * @mbggenerated Mon Jul 07 18:01:28 CDT 2014
     */
    int countByExample(ItiTestSessionSensitivityTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ititestsessionsensitivitytags
     *
     * @mbggenerated Mon Jul 07 18:01:28 CDT 2014
     */
    int deleteByExample(ItiTestSessionSensitivityTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ititestsessionsensitivitytags
     *
     * @mbggenerated Mon Jul 07 18:01:28 CDT 2014
     */
    int insert(ItiTestSessionSensitivityTags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ititestsessionsensitivitytags
     *
     * @mbggenerated Mon Jul 07 18:01:28 CDT 2014
     */
    int insertSelective(ItiTestSessionSensitivityTags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ititestsessionsensitivitytags
     *
     * @mbggenerated Mon Jul 07 18:01:28 CDT 2014
     */
    List<ItiTestSessionSensitivityTags> selectByExample(ItiTestSessionSensitivityTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ititestsessionsensitivitytags
     *
     * @mbggenerated Mon Jul 07 18:01:28 CDT 2014
     */
    int updateByExampleSelective(@Param("record") ItiTestSessionSensitivityTags record, @Param("example") ItiTestSessionSensitivityTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ititestsessionsensitivitytags
     *
     * @mbggenerated Mon Jul 07 18:01:28 CDT 2014
     */
    int updateByExample(@Param("record") ItiTestSessionSensitivityTags record, @Param("example") ItiTestSessionSensitivityTagsExample example);

    int insertList(@Param("itiTestSessionHistoryId") long itiTestSessionHistoryId, @Param("sensitivityTags") List<Long> sensitivityTags);

}