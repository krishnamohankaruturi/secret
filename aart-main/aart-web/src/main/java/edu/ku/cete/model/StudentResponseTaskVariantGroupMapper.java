package edu.ku.cete.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.StudentResponseTaskVariantGroup;

public interface StudentResponseTaskVariantGroupMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studentresponsetaskvariantgroup
	 * @mbggenerated  Fri Feb 20 09:53:19 CST 2015
	 */
	int insert(StudentResponseTaskVariantGroup record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table studentresponsetaskvariantgroup
	 * @mbggenerated  Fri Feb 20 09:53:19 CST 2015
	 */
	int insertSelective(StudentResponseTaskVariantGroup record);

	int selectFirstGroupWithCriteria(Map<String, Object> criteria);
	
	long countExternalIdsInGroup(@Param("groupNumber") long groupNumber);
    
    int updateGroupToProcessed(@Param("groupNumber") long groupNumber);

	List<StudentResponseTaskVariantGroup> selectAllNonProcessed(@Param("taskTypes") List<String> taskTypes);
}