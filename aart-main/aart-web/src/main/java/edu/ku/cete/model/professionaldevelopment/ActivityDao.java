package edu.ku.cete.model.professionaldevelopment;

import edu.ku.cete.domain.professionaldevelopment.Activity;
import edu.ku.cete.domain.professionaldevelopment.ActivityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ActivityDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int countByExample(ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int deleteByExample(ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int insert(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int insertSelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    List<Activity> selectByExample(ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    Activity selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int updateByExampleSelective(@Param("record") Activity record, @Param("example") ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int updateByExample(@Param("record") Activity record, @Param("example") ActivityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int updateByPrimaryKeySelective(Activity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.activity
     *
     * @mbggenerated Tue Aug 27 14:31:24 CDT 2013
     */
    int updateByPrimaryKey(Activity record);

    /**
     * @param userId
     */
    List<Activity> getActivitiesByUserId(@Param("userId") Long userId);
    
}