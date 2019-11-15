package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrganizationSnapshotMapper {
	List<Long> insert(@Param("orgId") Long orgId,@Param("userId") Long userId);

	void updateParentSnapshot(@Param("ids") List<Long> ids);
}
