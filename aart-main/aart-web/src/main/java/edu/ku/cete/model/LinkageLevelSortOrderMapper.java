package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.LinkageLevelSortOrder;

public interface LinkageLevelSortOrderMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table linkagelevelsortorder
	 * @mbg.generated  Mon Apr 15 15:55:01 CDT 2019
	 */
	int insert(LinkageLevelSortOrder record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table linkagelevelsortorder
	 * @mbg.generated  Mon Apr 15 15:55:01 CDT 2019
	 */
	int insertSelective(LinkageLevelSortOrder record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table linkagelevelsortorder
	 * @mbg.generated  Mon Apr 15 15:55:01 CDT 2019
	 */
	LinkageLevelSortOrder selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table linkagelevelsortorder
	 * @mbg.generated  Mon Apr 15 15:55:01 CDT 2019
	 */
	int updateByPrimaryKeySelective(LinkageLevelSortOrder record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table linkagelevelsortorder
	 * @mbg.generated  Mon Apr 15 15:55:01 CDT 2019
	 */
	int updateByPrimaryKey(LinkageLevelSortOrder record);
	
	List<LinkageLevelSortOrder> getLevelsByContentAreaAndName(
		@Param("contentAreaId") Long contentAreaId,
		@Param("linkageLevelNames") List<String> linkageLevelNames);
}