package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.user.UserContentArea;

public interface UserContentAreasDao{

	List<UserContentArea> getUserContentAreas(@Param("userId") Long userId);
	
	void insertUserContentArea(UserContentArea userContentArea);

	void activateUserContentAreas(@Param("userContentAreaIds") List<Long> userContentAreaIds);
	
	void deactivateUserContentAreas(@Param("userContentAreaIds") List<Long> userContentAreaIds);
	
}