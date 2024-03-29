package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.BatchJobSchedule;

public interface BatchJobScheduleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batchjobschedule
     *
     * @mbggenerated Thu Nov 12 21:45:44 CST 2015
     */
    int insert(BatchJobSchedule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batchjobschedule
     *
     * @mbggenerated Thu Nov 12 21:45:44 CST 2015
     */
    int insertSelective(BatchJobSchedule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batchjobschedule
     *
     * @mbggenerated Thu Nov 12 21:45:44 CST 2015
     */
    BatchJobSchedule selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batchjobschedule
     *
     * @mbggenerated Thu Nov 12 21:45:44 CST 2015
     */
    int updateByPrimaryKeySelective(BatchJobSchedule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table batchjobschedule
     *
     * @mbggenerated Thu Nov 12 21:45:44 CST 2015
     */
    int updateByPrimaryKey(BatchJobSchedule record);
    
    List<BatchJobSchedule> selectByServer(@Param("server") String server);
}