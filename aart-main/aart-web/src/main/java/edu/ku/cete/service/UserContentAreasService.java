package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.user.UserContentArea;

public interface UserContentAreasService {
	public List<UserContentArea> getUserContentAreas(Long userId);
	public void insertUserContentAreas(List<UserContentArea> userContentAreas);
	public void activateUserContentAreas(List<Long> userContentAreaIds);
	public void deactivateUserContentAreas(List<Long> userContentAreaIds);
	
	public Boolean setUserContentAreas(Long userId, List<String> contentAreaCodes, Long modifiedUserId);
}