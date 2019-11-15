package edu.ku.cete.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserAudit;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.ExpirePasswordService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserAuditService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.MD5Base64EncoderUtil;

/**
 * Login form controller.
 */
@Controller
@RequestMapping("/logIn")
public class LogInFormController extends BaseController {

	@Value("${logout.success.url}")
	private String ssoReturnUrl;
	
    /** Logger for this class and subclasses. */
    private final Log logger = LogFactory.getLog(getClass());

    /** Form validator. */
    @SuppressWarnings("unused")
    private Validator validator;

    /**
     * Validator setter.
     *
     * @param newValidator the new validator
     */
    public final void setValidator(final Validator newValidator) {
        this.validator = newValidator;
    }
    
    @Value("${application.images.folderName}")
    private String imageFolderName;

    /** UserService holder. */
    private UserService userService;

    /** UserAuditService holder. */
    private UserAuditService userAuditService;
    
    private AppConfigurationService appConfigurationService;
    
    private APIDashboardErrorService apiDashboardErrorService;
    
    @Autowired
    private ExpirePasswordService expirePasswordService;
    
    @Value("${password.excludedUserList}")
	private String excludedUserList;

    /**
     * Get the Service wired.
     * @param newUserService {@link UserService}
     * @param newUserAuditService {@link UserAuditService}
     * @param newAppConfigurationService {@link AppConfigurationService}
     * @param newAPIDashboardErrorService {@link APIDashboardErrorService}
     */
    @Autowired
    public LogInFormController(final UserService newUserService, final UserAuditService newUserAuditService, 
    		final AppConfigurationService newAppConfigurationService, final APIDashboardErrorService newAPIDashboardErrorService) {
        this.userService = newUserService;
        this.userAuditService = newUserAuditService;
        this.appConfigurationService = newAppConfigurationService;
        this.apiDashboardErrorService = newAPIDashboardErrorService;

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    /**
     * This method is called by the Get request an results in the jsp being displayed.
     * @param request {@link HttpServletRequest}
     * @return String JSP to return.
     * 
     * Changed for US-14985
     */
    @RequestMapping(method = RequestMethod.GET)
    public final String setupForm(final HttpServletRequest request) throws Exception {
    	
        logger.debug("Start of setupForm (GET)");
    	List<String> userArrayList = new ArrayList<String>(); 
    	String[] userList = excludedUserList.split(",");
    	Collections.addAll(userArrayList, userList);
    	
    	//sending param to mainNoNaavigation to set logo
    	request.getSession().setAttribute("imageFolderName",imageFolderName);
    	
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        UserDetailImpl userDetailImpl = null;
        boolean isSSO = false;
        if (principal instanceof UserDetails) {
        	username = ((UserDetails) principal).getUsername();
        	//This is the only implementation in AART.
        	userDetailImpl = (UserDetailImpl) principal;
        	isSSO = userDetailImpl.isSSO();
        	logger.debug("UserDetailImpl retrieved" + userDetailImpl);
        } else {
          username = principal.toString();
        }
        logger.debug("Found userame: " + username);
        //This is for the logout case.
        if (username.equals("anonymousUser")) {
            return "logIn";
        } else {
        	User user = null;
            String userAgent = request.getHeader("user-agent");
            logger.debug("LogIn Succeded, OS=" + userAgent);

            if(userDetailImpl != null) {
            	logger.debug("User already loaded "+ username);
            	user = userDetailImpl.getUser();
            	logger.debug("CURRENT ORGANIZATION TYPE IN LOGIN CONTROLLER: "+ user.getCurrentOrganizationType());
            } else {
            	user = userService.getByUserName(username);	
            }
            if (user == null) {
                logger.debug("LogIn failed, returning from LogInForm");

                return "logIn";
            } else {
            	long defaultOrgId = user.getDefaultOrganizationId();
            	//no password management for SSO users
            	//skip password update and expired password code
            	if (!isSSO) {
	            	boolean lastUserPasswordUpdate = false; 
	           		if(!userArrayList.contains(username))
	           			lastUserPasswordUpdate = expirePasswordService.lastUserPasswordUpdate(user);
				
	            	if(lastUserPasswordUpdate)
	            	{
	            		logger.debug("LogIn unsuccessful - expired password");
	                    String enc = MD5Base64EncoderUtil.encodeWithSalt(user.getEmail(), user.getId().toString());
	                    String ah = null;
	                    String encodedUserName = null;
						try {
							ah = URLEncoder.encode(enc, "UTF-8");
							encodedUserName = URLEncoder.encode(username, "UTF-8");
						} catch (UnsupportedEncodingException e) {
							logger.error(e.getMessage());
						}
						try {
							expirePasswordService.createUserPasswordReset(user.getId(), enc, true);
							return "redirect:forgotPassword/resetPassword.htm?user="+encodedUserName+"&ah="+ah+"&expiredByOrg=true";
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
	            	}
	          	
	            	Organization contractingOrg =  organizationService.getContractingOrganization(defaultOrgId);
	
	            	if (contractingOrg != null && contractingOrg.getExpirePasswords()){
	            		boolean expiredPassword = expirePasswordService.expirePassword(user, defaultOrgId);
	
	            		if (expiredPassword){
	            			logger.debug("LogIn unsuccessful - expired password");
	
	                        String enc = MD5Base64EncoderUtil.encodeWithSalt(user.getEmail(), user.getId().toString());
	                        String ah = null;
	                        String encodedUserName = null;
							try {
								ah = URLEncoder.encode(enc, "UTF-8");
								encodedUserName = URLEncoder.encode(username, "UTF-8");
							} catch (UnsupportedEncodingException e) {
								logger.error(e.getMessage());
							}
							expirePasswordService.createUserPasswordReset(user.getId(), enc, true);
	            			return "redirect:forgotPassword/resetPassword.htm?user="+encodedUserName+"&ah="+ah+"&expiredByOrg=true";
	            		}
	            	}
            	}
                logger.debug("LogIn successful, going to UserHome");
                
                List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(defaultOrgId);
				childrenOrgIds.add(defaultOrgId);
				userDetailImpl.getUser().setAllChilAndSelfOrgIds(childrenOrgIds);
				if (isSSO) {
					boolean isValid = validateUserForSSO(isSSO, user);
					if (!isValid) {
						return "redirect:"+ssoReturnUrl;
					}
				}
					
            	HttpSession session = request.getSession();
            	session.setAttribute("user", user);                

                Date ctime = (new Date());
                // create object to hold the audit record
                UserAudit toAdd = new UserAudit();
                // populate the audit object
                toAdd.setUserId(user.getId());
                toAdd.setUserOS(userAgent);
                toAdd.setLoginTime(ctime);

                try {
                    // save the audit object in the DB
                    userAuditService.add(toAdd);

                    logger.debug("Saving the UserAudit record succeded, returning to UserHome page");

                    return "redirect:/userHome.htm";
                } catch (ServiceException e) {
                    logger.error("Saving the UserAudit record failed for:: UserId=" + user.getId() + " Browser\\OS="
                            + userAgent + " login Time=" + ctime.toString() + " exception=" + e.getMessage());

                    return "logIn";
                }
            }
        }
    }
    
    private boolean validateUserForSSO(boolean isSSO, User user) {
    	boolean isValid =  false;
		if (isSSO) {
			Map<String, AppConfiguration> appConfigurationMap =  appConfigurationService.selectIdMapByAttributeType(CommonConstants.APP_CONFIGURATION_ATTR_TYPE_FOR_SSO_ERROR_MESSAGES);
			//add validation for SSO - currently PLTW rules
        	//is user inactive?
        	Boolean activeUser = user.getActiveFlag();
    		String name = user.getSurName() + ", " + user.getFirstName();
        	if (!activeUser) {
        		//log error and do not allow login
        		AppConfiguration inactiveUserMessage = appConfigurationMap.get(CommonConstants.APP_CONFIGURATION_SSO_INACTIVE_USER);
        		String formattedInactiveUserMessage = String.format(inactiveUserMessage.getAttributeValue(), user.getEmail());
        		logger.error(formattedInactiveUserMessage);
        		APIDashboardError error = apiDashboardErrorService.buildDashboardError(ApiRequestTypeEnum.AUTH, ApiRecordTypeEnum.USER, user.getUserName(), name, null, null, null, formattedInactiveUserMessage);
        		List<APIDashboardError> errors = new ArrayList<>();
        		errors.add(error);
        		apiDashboardErrorService.insertErrors(errors);
        		
        		return isValid;
        	}
        	//does user have an active organization?
        	boolean activeOrg = false;
        	for (Organization org :user.getOrganizations()) {
        		if (org.getActiveFlag() != null && org.getActiveFlag()) {
        			activeOrg = true;
        			break;
        		}
        	}
        	if (!activeOrg) {
        		//log error and do not allow login
        		AppConfiguration inactiveOrgsMessage = appConfigurationMap.get(CommonConstants.APP_CONFIGURATION_SSO_INACTIVE_ORGS);
        		String formattedInactiveOrgsMessage = String.format(inactiveOrgsMessage.getAttributeValue(), user.getEmail());
        		logger.error(formattedInactiveOrgsMessage);
        		APIDashboardError error = apiDashboardErrorService.buildDashboardError(ApiRequestTypeEnum.AUTH, ApiRecordTypeEnum.USER, user.getUserName(), name, null, null, null, formattedInactiveOrgsMessage);
        		List<APIDashboardError> errors = new ArrayList<>();
        		errors.add(error);
        		apiDashboardErrorService.insertErrors(errors);
        		
        		return isValid;
        	}
        	isValid = true;
		}
		return isValid;
    }
}
