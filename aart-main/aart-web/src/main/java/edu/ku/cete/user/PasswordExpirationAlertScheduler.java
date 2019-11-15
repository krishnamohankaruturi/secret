package edu.ku.cete.user;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.ScheduledJobLauncher;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserService;
   

	/**
	 * Added for US-14985
	 */



public class PasswordExpirationAlertScheduler {
	private final static Log logger = LogFactory.getLog(ScheduledJobLauncher.class);
	
		
	@Autowired
	private UserService userService;
	 
	@Autowired
	private EmailService emailService;
	
	@Value("${password.categoryCode}")
	private String typeCode;

	@Value("${password.excludedUserList}")
	private String excludedUserList;
	
	@Value("${reset.passwordlink.period}")
    private int resetPasswordLinkPeriod;

	
	
	
	String isScheduleOn;
	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	} 
	
	
	public void run() throws ServiceException {
		
		logger.debug("--> run - Job: Password Expiration and warning Alert Scheduler triggered "+isScheduleOn );
		List<String> userArrayList = new ArrayList<String>(); 
    	String[] userList = excludedUserList.split(",");
    	Collections.addAll(userArrayList, userList);
		
		if(isScheduleOn.equalsIgnoreCase("ON")) {
			
			ArrayList<Category> userPasswordPolicyList = userService.getPasswordPolicyRules(typeCode);
			for(Category category : userPasswordPolicyList )
			{
				logger.debug("--> run - Job: Password Expiration and warning Alert Scheduler"+category.getCategoryCode() );
				Map<String, Object> map = new HashMap<String, Object>();
				if(category.getCategoryCode().equals("PASS_WARNING") && category.getActiveFlag())
	    		{
					logger.debug("--> run - Job: Password Warning Alert Scheduler for Category Code"+ category.getCategoryCode());
					logger.debug("--> run - Job: Password Warning Alert Scheduler for Category Name "+ category.getCategoryName());
					ArrayList<User> passwordWarningUsers = userService.getPasswordExpirationAlertUsers(Integer.parseInt(category.getCategoryName()));
					for(User userPasswarning :passwordWarningUsers)
					{
					//	lastUserPasswordUpdate = expirePasswordService.lastUserPasswordUpdate(user);
		           		if(!userArrayList.contains(userPasswarning.getUserName())){
							try {
								logger.debug("--> run - Job: Password warning Alert mail scheduled for user "+userPasswarning.getUserName());
								logger.debug("--> run - Job: Password warning Alert mail scheduled user Email Id"+userPasswarning.getEmail());
								userPasswarning.setCurrentContextUserId(userPasswarning.getId());
								emailService.sendPasswordExpirationWarningResetEmail(userPasswarning);
								map.put("success", true);
							} catch (ServiceException e) {
								logger.error("There was an error sending a reset email to user {}");
								map.put("success", false);
								map.put("serviceError", true);
							}
						}
					}
	    		}
				
				if(category.getCategoryCode().equals("PASS_EXPIRATION") && category.getActiveFlag())
	    		{
					logger.debug("--> run - Job: Password Expiring Alert Scheduler for category Code "+ category.getCategoryCode());
					logger.debug("--> run - Job: Password Expiring Alert Scheduler for Category Name "+ category.getCategoryName());
					int noOfDays= Integer.parseInt(category.getCategoryName());
					noOfDays = noOfDays - resetPasswordLinkPeriod;
					ArrayList<User> passwordExpiringUsers = userService.getPasswordExpirationAlertUsers(noOfDays);
					for(User userPassExpiring :passwordExpiringUsers)
					{
						logger.debug("--> run - Job: Password Expiring Alert mail scheduled for user "+userPassExpiring.getUserName());
						logger.debug("--> run - Job: Password warning Alert mail scheduled user Email Id"+userPassExpiring.getEmail());
						
						if(!userArrayList.contains(userPassExpiring.getUserName())){ 
							try {
									userPassExpiring.setCurrentContextUserId(userPassExpiring.getId());
									logger.debug("--> run - Job: Password Expiring Alert mail scheduled for user "+userPassExpiring.getUserName());
									emailService.sendPasswordExpiringAlertEmail(userPassExpiring);
									map.put("success", true);
								} catch (ServiceException e) {
									logger.error("There was an error sending a reset email to user {}");
									map.put("success", false);
									map.put("serviceError", true);
								}
							}
					}
				
	    		}
				logger.debug("<-- run - Job: " );
			}
			
		}
	}
}
