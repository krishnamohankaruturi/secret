package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserPasswordReset;
import edu.ku.cete.service.ExpirePasswordService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserService;
@Service
public class ExpirePasswordServiceImpl implements ExpirePasswordService {

	  private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private OrganizationService orgService;
	
	@Autowired
	private UserService userService;
    
    @Value("${org.password.expiration.start.date}")
    private String startDateCode;
    
    @Value("${org.password.expiration.end.date}")
    private String endDateCode;
	
	@Override
	public boolean expirePassword(User user, long orgId) {
		boolean expiredPassword = false;

    	Organization contractingOrg = orgService.getContractingOrganization(orgId);

    	if (contractingOrg.getExpirePasswords()){
    		Date lastExpiredPasswordResetDate = userService.selectLastExpiredPasswordResetDateById(user.getId());
    		Date today = new Date();
    		if (lastExpiredPasswordResetDate == null){
	    		if (startDateCode.equals(contractingOrg.getExpirationDateTypeString())){
	    			if (today.equals(contractingOrg.getSchoolStartDate()) || today.after(contractingOrg.getSchoolStartDate())){
	    				expiredPassword = true;
	    			}
	    		}else if (endDateCode.equals(contractingOrg.getExpirationDateTypeString())){
	    			if (today.equals(contractingOrg.getSchoolEndDate()) || today.after(contractingOrg.getSchoolEndDate())){
	    				expiredPassword = true;
	    			}
	    		}
    		}else{
	    		if (startDateCode.equals(contractingOrg.getExpirationDateTypeString())){
	    			Date startDate = contractingOrg.getSchoolStartDate();
	    			if (lastExpiredPasswordResetDate.after(startDate)){
	    				expiredPassword = false;
	    			}else if  (today.equals(contractingOrg.getSchoolStartDate()) || today.after(contractingOrg.getSchoolStartDate())){
	    				expiredPassword = true;
	    			}
	    		}else if (endDateCode.equals(contractingOrg.getExpirationDateTypeString())){
	    			Date endDate = contractingOrg.getSchoolEndDate();
	    			if (lastExpiredPasswordResetDate.after(endDate) ){
	    				expiredPassword = false;
	    			}else if (today.equals(contractingOrg.getSchoolEndDate()) || today.after(contractingOrg.getSchoolEndDate())){
	    				expiredPassword = true;
	    			}
	    		}
    		}
    	}
		return expiredPassword;
	}

	@Override
	public void createUserPasswordReset(Long userId, String authToken, boolean activeFlag) {
        UserPasswordReset userPasswordReset = new UserPasswordReset();
        userPasswordReset.setAartUserId(userId);
        userPasswordReset.setAuthToken(authToken);
        userPasswordReset.setActiveFlag(activeFlag);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date()); 
        cal.add(Calendar.MINUTE, 1); 
        Date nowPlusOneMinute = cal.getTime(); 
        userPasswordReset.setPasswordExpirationDate(nowPlusOneMinute);
        userService.insertUserPasswordResetDetails(userPasswordReset);
	}

	/**
	 * Added for US-14985
	 */
	@Override
	public boolean lastUserPasswordUpdate(User user) {
		ArrayList userList = null;
		try {
			userList = userService.lastUserPasswordUpdate(user);
			if(userList.size() > 0 && userList != null)
			{
				return false;
				
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage());
		}
		return true;
	}
}
