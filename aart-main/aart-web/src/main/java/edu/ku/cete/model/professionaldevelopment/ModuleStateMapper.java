package edu.ku.cete.model.professionaldevelopment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.professionaldevelopment.ModuleState;
import edu.ku.cete.domain.professionaldevelopment.ModuleStateKey;

public interface ModuleStateMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulestate
	 * @mbggenerated  Thu Jul 03 11:00:06 CDT 2014
	 */
	int insert(ModuleState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulestate
	 * @mbggenerated  Thu Jul 03 11:00:06 CDT 2014
	 */
	int insertSelective(ModuleState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulestate
	 * @mbggenerated  Thu Jul 03 11:00:06 CDT 2014
	 */
	ModuleState selectByPrimaryKey(ModuleStateKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulestate
	 * @mbggenerated  Thu Jul 03 11:00:06 CDT 2014
	 */
	int updateByPrimaryKeySelective(ModuleState record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulestate
	 * @mbggenerated  Thu Jul 03 11:00:06 CDT 2014
	 */
	int updateByPrimaryKey(ModuleState record);
	
	List<ModuleState> selectByModule(@Param("moduleId")Long moduleId);
}