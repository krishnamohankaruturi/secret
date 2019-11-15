package edu.ku.cete.service;

import edu.ku.cete.domain.user.User;

public interface ExpirePasswordService {

	public boolean expirePassword(User user, long orgId);
	
	public void createUserPasswordReset(Long userId, String authToken, boolean activeFlag);

	/**
	 * Added for US-14985
	 */
	public boolean lastUserPasswordUpdate(User user) throws ServiceException;

}
