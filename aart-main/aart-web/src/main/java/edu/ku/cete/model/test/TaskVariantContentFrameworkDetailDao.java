package edu.ku.cete.model.test;

import edu.ku.cete.domain.test.TaskVariantContentFrameworkDetail;
import edu.ku.cete.domain.test.TaskVariantContentFrameworkDetailExample;
import edu.ku.cete.domain.test.TaskVariantContentFrameworkDetailKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskVariantContentFrameworkDetailDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int countByExample(TaskVariantContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int deleteByExample(TaskVariantContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int deleteByPrimaryKey(TaskVariantContentFrameworkDetailKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int insert(TaskVariantContentFrameworkDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int insertSelective(TaskVariantContentFrameworkDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    List<TaskVariantContentFrameworkDetail> selectByExample(TaskVariantContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    TaskVariantContentFrameworkDetail selectByPrimaryKey(TaskVariantContentFrameworkDetailKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int updateByExampleSelective(@Param("record") TaskVariantContentFrameworkDetail record, @Param("example") TaskVariantContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int updateByExample(@Param("record") TaskVariantContentFrameworkDetail record, @Param("example") TaskVariantContentFrameworkDetailExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int updateByPrimaryKeySelective(TaskVariantContentFrameworkDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.taskvariantcontentframeworkdetail
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    int updateByPrimaryKey(TaskVariantContentFrameworkDetail record);

    /**
     * @param taskVariantContentFrameworkDetailExample {@link TaskVariantContentFrameworkDetailExample}
     * @return {@link List}
     */
    List<TaskVariantContentFrameworkDetail> selectExtendedByExample(
			TaskVariantContentFrameworkDetailExample taskVariantContentFrameworkDetailExample);
}