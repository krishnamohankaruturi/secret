package edu.ku.cete.model;

import edu.ku.cete.domain.StudentsTestsHistory;

public interface StudentsTestsHistoryMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studentstestshistory
	 * @mbggenerated  Mon Dec 22 14:47:20 CST 2014
	 */
	int insert(StudentsTestsHistory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studentstestshistory
	 * @mbggenerated  Mon Dec 22 14:47:20 CST 2014
	 */
	int insertSelective(StudentsTestsHistory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studentstestshistory
	 * @mbggenerated  Mon Dec 22 14:47:20 CST 2014
	 */
	StudentsTestsHistory selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studentstestshistory
	 * @mbggenerated  Mon Dec 22 14:47:20 CST 2014
	 */
	int updateByPrimaryKeySelective(StudentsTestsHistory record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studentstestshistory
	 * @mbggenerated  Mon Dec 22 14:47:20 CST 2014
	 */
	int updateByPrimaryKey(StudentsTestsHistory record);
}