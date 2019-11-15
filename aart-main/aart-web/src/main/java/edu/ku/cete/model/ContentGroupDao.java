package edu.ku.cete.model;

import org.apache.ibatis.annotations.Param;

public interface ContentGroupDao {

	void createContentGroupForInterimTest(@Param("testId") Long testId, @Param("testSectionId") Long testSectionId,
			@Param("originationCode") String originationCode, @Param("createdUserId") Long createdUserId,
			@Param("modifiedUserId") Long modifiedUserId);

}
