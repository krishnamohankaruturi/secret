package edu.ku.cete.model.professionaldevelopment;

import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTask;
import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTaskKey;

public interface UserTestSectionTaskMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontask
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int insert(UserTestSectionTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontask
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int insertSelective(UserTestSectionTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontask
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	UserTestSectionTask selectByPrimaryKey(UserTestSectionTaskKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontask
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int updateByPrimaryKeySelective(UserTestSectionTask record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontask
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int updateByPrimaryKey(UserTestSectionTask record);
}