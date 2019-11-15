/**
 * 
 */
package edu.ku.cete.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.domain.user.UserPasswordReset;
import edu.ku.cete.model.validation.PasswordValidator;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.ExpirePasswordService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.UserStatus;

/**
 * @author neil.howerton
 *
 */
@Controller
public class ForgotPasswordController extends BaseController {

    private static final String FORGOT_PWD_VIEW = "forgotPassword";
    private static final String RESET_PWD_VIEW = "resetPassword";
    private final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrganizationsGroupsService userOrgsGroupsService;
    
	@Autowired
	private PasswordEncoder passwordEncoder;
	
    @Autowired
    private ExpirePasswordService expirePasswordService;
    
    @Autowired
    private OrganizationService orgService;
    
    @Autowired
   	private PasswordValidator passwordValidator;

    /**
     * This method renders the initial forgot password page.
     * @return {@link ModelAndView}
     */
    @RequestMapping("forgotPassword/forgotPassword.htm")
    public final ModelAndView viewForgotPassword() {
        logger.trace("Entering the viewForgotPassword method.");
        logger.debug("Leaving the viewForgotPassword method. Routing user to view with name {}", FORGOT_PWD_VIEW);
        return new ModelAndView(FORGOT_PWD_VIEW);
    }

    /**
     * This method maps the request from the reset password email link sent to a user's email account.
     * @param authToken {@link String}
     * @param username {@link String}
     * @return {@link ModelAndView}
     */
    @RequestMapping("forgotPassword/resetPassword.htm")
    public final ModelAndView viewResetPassword(@RequestParam("ah") String authToken, @RequestParam("user") String username, @RequestParam("expiredByOrg") Boolean expiredByOrg) {
        logger.trace("Entering the viewResetPassword method.");
        ModelAndView mav = new ModelAndView(RESET_PWD_VIEW);
        logger.debug("Received password reset request for username: {} with authToken: {}", username, authToken);
        mav.addObject("allowReset", "N");
        mav.addObject("authToken", authToken);

        if (username != null) {
            logger.debug("authToken: '" + authToken + "'");
            logger.debug("Retrieving user by username: {}", username);
            User user = userService.getByUserName(username);

            if (user != null) {
                logger.debug("User with username {} was found.", username);

                UserPasswordReset userPasswordReset = new UserPasswordReset();
                userPasswordReset.setAartUserId(user.getId());
                userPasswordReset.setAuthToken(authToken);
                List<UserPasswordReset> userPasswordResetList = userService.getUserPasswordResetDetails(userPasswordReset);
                if (!CollectionUtils.isEmpty(userPasswordResetList)) {
                    userPasswordReset = null;

                    for (UserPasswordReset userPwdReset : userPasswordResetList) {
                        if (userPwdReset.getActiveFlag()) {
                            userPasswordReset = userPwdReset;
                            break;
                        }
                    }

                    if (userPasswordReset == null) {
                        userPasswordReset = userPasswordResetList.get(0);
                    }

                    logger.debug("userPasswordReset expiration date {}.", userPasswordReset.getPasswordExpirationDate());
                    Date currentDate = new Date();
                    logger.debug("userPasswordReset expiration date {}.",
                            currentDate.compareTo(userPasswordReset.getPasswordExpirationDate()));

                    if (!userPasswordReset.getActiveFlag()) {
                        logger.debug("The link is already used by user {}.", username);
                        mav.addObject("linkUsed", true);
                    } else if (currentDate.compareTo(userPasswordReset.getPasswordExpirationDate()) > 0) {
                            logger.debug("The link is expired for user {}.", username);
                        mav.addObject("linkExpired", true);
                    } else if (currentDate.compareTo(userPasswordReset.getPasswordExpirationDate())
                            <= 0 && userPasswordReset.getActiveFlag()) {
                            mav.addObject("username", username);
                            mav.addObject("allowReset", "Y");
                            user.setPassword(null);
                            if (expiredByOrg != null && expiredByOrg == true){
                            	mav.addObject("passwordExpired", true);
                            }
                    }
                } else {
                    logger.debug("The link is expired for user {}.", username);
                    mav.addObject("linkExpired", true);
                }
            } else {
                //this means that the username is was not found in the system.
                logger.debug("The username {} was not found in the system.", username);
                mav.addObject("parameterError", true);
            }
        }

        logger.trace("Leaving the viewResetPassword method.");
        return mav;
    }

    /**
     * This is an AJAX request handler method, that takes finds a user by their username, and then send them a reset password
     * email to the email on file for that user.
     * @param username - the username of the user who wishes to reset their password.
     * @return Map<String, Object>
     */
    @RequestMapping("forgotPassword/sendResetEmail.htm")
    public final @ResponseBody Map<String, Object> sendResetEmail(@RequestParam("username") String username) {
        logger.trace("Entering the sendResetEmail method.");
        Map<String, Object> map = new HashMap<String, Object>();
        logger.debug("Received a password reset request for username {}", username);
        boolean isActive = false;
        try {
            User user = userService.getByUserName(username);
            List<UserOrganizationsGroups> userOrgGroups = userOrgsGroupsService.getUserOrganizationsGroupsByUserId(user.getId());
            //check for if the user has an active group or not.
            for (UserOrganizationsGroups userOrgGroup : userOrgGroups) {
            	//TODO remove hard coded ids.
                if (userOrgGroup.getStatus() != null && UserStatus.ACTIVE == userOrgGroup.getStatus().intValue()) {
                    isActive = true;
                    break;
                }
            }

            if (user != null && isActive) {
                //this means that the user exists in the system and we can send the reset email.
                try {
                    logger.debug("User with username {} was found.", username);
                    user.setCurrentContextUserId(user.getId());
                    emailService.sendPasswordResetEmail(user);
                    map.put("success", true);
                } catch (ServiceException e) {
                    logger.error("There was an error sending a reset email to user {}", user);
                    map.put("success", false);
                    map.put("serviceError", true);
                }
            } else if (!isActive) {
                map.put("success", false);
                map.put("inactive", true);
            }
        } catch (UsernameNotFoundException e) {
            //Yes it is bad catching an unchecked exception.
            //This is a stop gap measure until the impact of changing the service method is determined.
            map.put("parameterError", true);
            map.put("success", false);
            logger.debug("User with username {} was not found in the system", username);
            logger.debug(e.getMessage(), e);
        } catch (Exception e) {
            map.put("serviceError", true);
            map.put("success", false);
            logger.error("Caught exception {} while trying to send reset email.", e);
            logger.error(e.getMessage(), e);
        }

        logger.trace("Leaving the sendResetEmail method.");
        return map;
    }

    /**
     * This method handles the changing of a user's password.
     * @param authToken {@link String} - the token created for this particular password reset request.
     * @param username {@link String} - the username of the user to change the password for.
     * @param password {@link String} - the password to change to
     * @param confirmPassword {@link String} - the confirmation of the password.
     * @param model {@link Model}
     * @return {@link String} - the view to route the user to.
     * 
     * Changed for US-14985
     */
    @RequestMapping("forgotPassword/changePassword.htm")
    public final String changePassword(@RequestParam("authToken") String authToken, @RequestParam("username")String username,
            @RequestParam("password")String password, @RequestParam("confirmPassword")String confirmPassword, Model model, @RequestParam("passwordExpired")Boolean passwordExpired) {
        logger.trace("Entering the changePassword method.");
        String view = "";
        logger.debug("Received a password change request for user {}", username);
        if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(confirmPassword)
                && password.equals(confirmPassword)) {
            User user = userService.getByUserName(username);
            String updateErrorMsg=null;
            String[] updateMsg =null;
            if (user != null) {
        		String encodedPassword = passwordEncoder.encodePassword(password, user.getSalt());
            	if (!encodedPassword.equals(user.getPassword())){
	                user.setPassword(password);
	                try {
	                    user.setCurrentContextUserId(user.getId());
	                    logger.debug("authToken: '" + authToken + "'");
	                    if(passwordValidator.validate(user))
	                    {
	                    	userService.updateWithEncodedPassword(user, true, authToken);
		                  
		                	long defaultOrgId = user.getDefaultOrganizationId();
		                	Organization contractingOrg =  orgService.getContractingOrganization(defaultOrgId);
		                	if (contractingOrg != null && contractingOrg.getExpirePasswords()){
		                    
			                    boolean expiredPassword = expirePasswordService.expirePassword(user, defaultOrgId);
			                    if (expiredPassword){
			                    	userService.updateLastExpiredPasswordResetDate(user.getId());
			                    }
		                	}
		                	
		                    view = RESET_PWD_VIEW;
		                    model.addAttribute("updateSuccess", true);
	                    }
	                } catch (ServiceException e) {
	                	updateErrorMsg=e.getMessage();
	                	updateErrorMsg = updateErrorMsg.replaceAll("\"", "");
	                	updateErrorMsg = updateErrorMsg.replace("}", "");
	                	updateMsg=updateErrorMsg.split(":");
	                	//logger.warn("Caught ServiceException in changePassword method.", e);
	                    view = RESET_PWD_VIEW;
	                    model.addAttribute("username", username);
	                    model.addAttribute("updateError", true);
	                    model.addAttribute("updateErrorMsg", updateMsg[1]);
	                    model.addAttribute("authToken", authToken);
	                    model.addAttribute("passwordExpired", passwordExpired);
	                }
            	} else {
                    logger.debug("Password matches the previous password.");
                    view = RESET_PWD_VIEW;
                    model.addAttribute("username", username);
                    model.addAttribute("duplicatePasswordError", true);
            	}
            } else {
                logger.debug("Username {} was not found in the system.", username);
                view = RESET_PWD_VIEW;
                model.addAttribute("username", username);
                model.addAttribute("parameterError", true);
            }
        } else {
            view = RESET_PWD_VIEW;
            model.addAttribute("username", username);
            model.addAttribute("parameterError", true);
            if (StringUtils.isEmpty(username)) {
                logger.debug("Username is empty. The username must be valid in order to change the password.");
            } else if (StringUtils.isEmpty(password) || StringUtils.isEmpty(confirmPassword)){
                logger.debug("One of the password fields was left blank.");
            } else {
                logger.debug("The passwords did not match, the password and the confirmation must match in order for" +
                        " the password change to work.");
            }
        }
        model.addAttribute("passwordExpired", passwordExpired);

        logger.trace("Leaving the changePassword method.");
        return view;
    }
}
