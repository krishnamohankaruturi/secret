package edu.ku.cete.model.professionaldevelopment;

import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTaskFoil;
import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTaskFoilKey;

public interface UserTestSectionTaskFoilMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontaskfoil
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int insert(UserTestSectionTaskFoil record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontaskfoil
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int insertSelective(UserTestSectionTaskFoil record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontaskfoil
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	UserTestSectionTaskFoil selectByPrimaryKey(UserTestSectionTaskFoilKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontaskfoil
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int updateByPrimaryKeySelective(UserTestSectionTaskFoil record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table usertestsectiontaskfoil
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	int updateByPrimaryKey(UserTestSectionTaskFoil record);
}