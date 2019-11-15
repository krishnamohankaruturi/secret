package edu.ku.cete.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.domain.UserAssessmentPrograms;
import edu.ku.cete.domain.UserSecurityAgreement;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.domain.user.UserRequest;
import edu.ku.cete.model.UserDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UploadFileRecordService;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.exception.NoAccessToResourceException;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CategoryUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.StringUtil;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.UserStatus;
import edu.ku.cete.util.json.UsersJsonConverter;
import edu.ku.cete.web.DLMPDTrainingDTO;
import edu.ku.cete.web.SecurityAgreementDTO;
/**
 *
 * @author neil.howerton
 *
 */
@Controller
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserOrganizationsGroupsService userOrganizationsGroupsService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private GroupsService groupsService;
        
    @Autowired
    private OrgAssessmentProgramService orgAssessProgService;
    
	@Autowired
	private UploadSpecification uploadSpecification;    
	
	@Autowired
	private CategoryUtil categoryUtil;
    
    @Autowired
    private EmailService emailService;
    
    @Value("${report.upload.categorytype.code}")
	private String reportUploadCategoryTypeCode;
    
    @Value("${security.agreement.expiration.mmdd}")
 	private String securityAgreementExpirationMMDD;
    
    @Autowired
	private UploadFileProcessor uploadFileProcessor;
    
    @Autowired
	private UploadFileRecordService uploadFileRecordService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;

	private SimpleDateFormat dateFormat;
    
    /**
     * This method gets triggered when a user requests to activate their account (i.e set their password).
     * @param activationNo {@link String}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "activate.htm", method = RequestMethod.GET)
    public final ModelAndView activate(@RequestParam(value = "an", required = false) String activationNo) {
        logger.trace("Entering the activate method.");
        logger.debug("activateNo: {}", activationNo);
        ModelAndView mav = new ModelAndView("user/activateUser");
        mav.setViewName("activateUser");
        UserOrganizationsGroups userGroup = userOrganizationsGroupsService.getByActivationNo(activationNo);
        //Change During US16245 : to not allow inactive user to activate by using Activation link
        if(userGroup != null){
            mav.addObject("activeUser", isActiveUser(userGroup));
            mav.addObject("inactiveUser",isInactiveUser(userGroup));
        } else {
        	mav.addObject("activeUser", false);
        	mav.addObject("inactiveUser", false);
        }

        if (userGroup == null || (userGroup != null && userGroup.getActivationNoExpirationDate().before(new Date()))) {
            mav.addObject("activationExpired", true);
        } else {
            mav.addObject("activationExpired", false);
        }

        mav.addObject("userGroup", userGroup);

        logger.trace("Leaving the activate method.");
        return mav;
    }

    /**
     * This method gets triggered when the user submits their new password and confirmation saying that they want to activate their
     * account.
     * @param userOrganizationsGroups {@link UserOrganizationsGroups}
     * @param errors {@link BindingResult}
     * @param request {@link HttpServletRequest}
     * @return {@link ModelAndView}
     * @throws ServiceException ServiceException
     */
    @RequestMapping(value = "/activateUser.htm", method = RequestMethod.POST)
    public final ModelAndView activateUser(UserOrganizationsGroups userOrganizationsGroups, BindingResult errors,
            HttpServletRequest request) throws ServiceException{
        //TODO update the status on the UserGroup table to active
        //update the user to active
        logger.trace("Entering the activateUser method.");
        ModelAndView mav = null;
        String confirmPassword = request.getParameter("confirmPassword");
        String password = request.getParameter("password_field");
        userOrganizationsGroups.getUser().setPassword(password);
        if (isValidActivation(userOrganizationsGroups, confirmPassword)) {
            logger.debug("User activation is valid.");
            mav = new ModelAndView("user/activateSuccess");
            mav.setViewName("activateSuccess");
            UserOrganizationsGroups userGroup = userOrganizationsGroupsService.
                    getUserOrganizationsGroups(userOrganizationsGroups.getId()); 
            userGroup.setStatus(UserStatus.ACTIVE);
            userGroup.setCurrentContextUserId(userGroup.getUserId());
            userOrganizationsGroupsService.updateUserOrganizationsGroups(userGroup);

            //set the password on the user.
            User user = userService.get(userOrganizationsGroups.getUserId());
            user.setPassword(userOrganizationsGroups.getUser().getPassword());
            user.setCurrentContextUserId(userGroup.getUserId());
            userService.updateWithEncodedPassword(user, false, null);

            List<UserOrganizationsGroups> otherUserOrganizationsGroups = userOrganizationsGroupsService.
                    getByUserAndStatus(userOrganizationsGroups.getUserId(), UserStatus.PENDING);

            for (UserOrganizationsGroups ug : otherUserOrganizationsGroups) {
                ug.setStatus(UserStatus.ACTIVE);
                userOrganizationsGroupsService.updateUserOrganizationsGroups(ug);
            }
            mav.addObject(userOrganizationsGroups.getUser());
        } else {
            logger.debug("User activation is invalid.");
            mav = new ModelAndView("user/activateUser");
            UserOrganizationsGroups userGroup = userOrganizationsGroupsService.
                    getByActivationNo(userOrganizationsGroups.getActivationNo());
            mav.setViewName("activateUser");
            mav.addObject("activationExpired", userGroup.getActivationNoExpirationDate().before(new Date()));
            List<UserOrganizationsGroups> userOrganizationsGroupsList = userOrganizationsGroupsService.
                    getByUserAndStatus(userGroup.getUserId(), UserStatus.ACTIVE);
            mav.addObject("activeUser", (userOrganizationsGroupsList.size() > 0));
            mav.addObject("userGroup", userGroup);
            mav.addObject("invalidPasswords", true);
        }

        logger.trace("Leaving the activateUser method.");
        return mav;
    }

    /**
     *
     * @param userOrganizationsGroups {@link UserOrganizationsGroups}
     * @param confirmedPassword {@link String}
     * @return boolean
     */
    private boolean isValidActivation(UserOrganizationsGroups userOrganizationsGroups, String confirmedPassword) {
        boolean isValidActivation = false;
        if (userOrganizationsGroups != null && userOrganizationsGroups.getUser() != null) {
            isValidActivation = userOrganizationsGroups.getUser().getPassword().equals(confirmedPassword)
                    && !StringUtils.isEmpty(userOrganizationsGroups.getUser().getPassword())
                    && !StringUtils.isEmpty(confirmedPassword);
        }

        return isValidActivation;
    }

    /**
     *
     * @param userGroup {@link UserOrganizationsGroups}
     * @return boolean
     */
    private boolean isActiveUser(UserOrganizationsGroups userGroup) {
        List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService.
                getByUserAndStatus(userGroup.getUserId(), UserStatus.ACTIVE);

        return (userOrganizationsGroups != null && CollectionUtils.isNotEmpty(userOrganizationsGroups));
    }
    
    //Added During US16245 : To check wheather user is inactivated or not    
    private boolean isInactiveUser(UserOrganizationsGroups userGroup) {
		List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService
				.getByUserAndStatus(userGroup.getUserId(), UserStatus.INACTIVE);

		return (userOrganizationsGroups != null && CollectionUtils
				.isNotEmpty(userOrganizationsGroups));
	}
    
    /**
     * Biyatpragyan Mohanty : US14307 : User Management: Add Users Manually
     * This method returns all available groups/roles per selected organization (level).
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/getRoles.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getExistingRoles(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
        logger.trace("Entering the view method.");
        Map<String, Object> hashMap = new HashMap<String, Object>();
        List<Organization> organizations = new ArrayList<Organization>();
        
        try {
            long organizationId = Long.parseLong(request.getParameter("organizationId"));
            organizations.add(new Organization(organizationId));

            List<Groups> allGroups = groupsService.getByDefaultRoleAndOrganizationTree(false, organizations.get(0).getId());
            hashMap.put("allGroups", allGroups);

        } catch (NumberFormatException e) {
            logger.debug("Failed to parse organizationId parameter: {}", request.getParameter("organizationId"));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            hashMap.put("invalidParams", true);
        }

        return hashMap;
    }
    
    /**
     * Biyatpragyan Mohanty : US14307 : User Management: Add Users Manually
     * This method returns all available groups/roles to show what groups/roles the user could be added to.
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/getAllRoles.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getAllRoles(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
        logger.trace("Entering the view method.");
        Map<String, Object> hashMap = new HashMap<String, Object>();
        UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentGroupId=loggedinUser.getUser().getCurrentGroupsId();
        Groups group=groupsService.getGroup(currentGroupId);
        logger.debug("orgType : "+loggedinUser.getUser().getOrganization().getOrganizationTypeId());
        List<Groups> allGroups = groupsService.getAllGroups();
        if(allGroups != null && allGroups.size() > 0) {
        	// if sys admin, can assign any role.
        	if(loggedinUser.getUser().isSysAdmin()){
        		 for(Groups ag: allGroups) {
 		        	ag.setAllowedToAssign(true);
        		 }
        	} else {

        		List<Groups> groups = groupsService.getAllGroupsBelowLevel(loggedinUser.getUser().getCurrentGroupsId());
        		// Per US18906 - Allow DUS user creation for DTC users
        		if( group.getGroupCode().equals("DTC")){
        			List<Groups> groupsByGroupCode=	groupsService.getGroupsByGroupCode("DUS");
        			List<Groups> groupsBySuperCode=	groupsService.getGroupsByGroupCode("SUP");
        			groups.addAll(groupsByGroupCode);
        			groups.addAll(groupsBySuperCode);
        		}
        		for(Groups ag: allGroups) {
		        	ag.setAllowedToAssign(false);
		        	if(groups != null && groups.size() > 0) {
			        	for(Groups g: groups) {
			        		if(g.getGroupId() == ag.getGroupId()) {
			        			ag.setAllowedToAssign(true);
			        			break;
			        		}
			        	}
		        	}
	        	}
	        }
	    }
        hashMap.put("allGroups", allGroups);

        return hashMap;
    }
        
    
    /**
     * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
     * Get users list by selected organization.
     * @param orgChildrenIds
     * @param limitCountStr
     * @param page
     * @param sortByColumn
     * @param sortType
     * @param filters
     * @return
     * @throws IOException 
     * @throws JsonProcessingException 
     */
    @RequestMapping(value = "getUsersByOrganization.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getOrgsToView(
			@RequestParam("orgChildrenIds") String[] orgChildrenIds,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam("requestFor") String requestFor,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "showInactiveUsers", required = false) String showInactiveUsers) throws JsonProcessingException, IOException, NoAccessToResourceException {
		    	
		JQGridJSONModel jqGridJSONModel =  new JQGridJSONModel();
		Integer currentPage = null;
		Integer limitCount = null;
		//Long organizationId = null;
		List<User> users;
		Integer totalCount = -1;
		
		Boolean showInactiveUsersFlag = false;
		if(showInactiveUsers != null && showInactiveUsers.equalsIgnoreCase("checked")) {
			showInactiveUsersFlag = true;
		}

		//organizationId = Long.parseLong(orgChildrenIds[0]);
		currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
		UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
        long filterOrgId =  NumericUtil.parse(orgChildrenIds[0], -1);
		if(childrenOrgIds.contains(filterOrgId)){
			Map<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("organizationId", NumericUtil.parse(orgChildrenIds[0], -1));
			criteria.put("isSysAdmin", userDetails.getUser().isSysAdmin());
			criteria.put("requestFor", requestFor);
			criteria.put("assessmenProgramId", userDetails.getUser().getCurrentAssessmentProgramId());
			criteria.put("currentGroupId", userDetails.getUser().getCurrentGroupsId());
			criteria.put("showInactiveUsers", showInactiveUsersFlag);
			
			populateCriteria(criteria, filters);
	        
	        users = userService.getByOrganization(criteria,
					sortByColumn, sortType, (currentPage-1)*limitCount,
					limitCount);
			        
			totalCount = userService.countAllUsersToView(criteria);

			jqGridJSONModel = UsersJsonConverter.convertToUserJson(
					users,totalCount, currentPage, limitCount);
		}else{
			throw new NoAccessToResourceException("Access to this information is restricted.");
		}
		return jqGridJSONModel;
	}
    
    private void populateCriteria(Map<String, Object> criteria, String filters)
			throws JsonProcessingException, IOException {

		if (null != filters && !filters.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(filters);

			final JsonNode results = rootNode.get("rules");
			
			for (final JsonNode element : results) {
				criteria.put(element.get("field").asText(), element
						.get("data").asText());
			}
		}
	}
    
    
    /**
     * Biyatpragyan Mohanty : US11240 : User Management ViewEdit User
     * Get User details along with organization hierarchy.
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
	@RequestMapping(value = "/getAllUserDetails.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getAllUserDetails(final HttpServletRequest request,
			final HttpServletResponse response) throws ServiceException {
		logger.trace("Entering the view method.");
		Map<String, Object> hashMap = new HashMap<String, Object>();

		try {
			UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User loggedInUser = userDetails.getUser();
	        
			long userId = Long.parseLong(request.getParameter("userId"));

			User user = userService.getAllDetails(userId);
			UserAssessmentPrograms userDefaultAssessmentProgram = userService.getUserDefaultAssessmentProgram(userId);
			if (userDefaultAssessmentProgram != null) {
				user.setDefaultAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
			}
			hashMap.put("user", user);

			List<UserAssessmentPrograms> userRoles = userService.getUserRoles(userId,CommonConstants.ORGANIZATION_STATE_CODE,CommonConstants.ORGANIZATION_DISTRICT_CODE,CommonConstants.ORGANIZATION_SCHOOL_CODE);
			Boolean doesUserHaveHighRoles = userService.doesUserHaveHighRoles(userId, loggedInUser.getCurrentGroupsId(),
					loggedInUser.getCurrentOrganizationId(), loggedInUser.getCurrentAssessmentProgramId());
			hashMap.put("doesUserHaveHighRoles", doesUserHaveHighRoles);

			hashMap.put("userRoles", userRoles);
		} catch (NumberFormatException e) {
			logger.debug("Failed to parse organizationId parameter: {}", request.getParameter("organizationId"));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			hashMap.put("invalidParams", true);
		}

		return hashMap;
	}
    /**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
    
    @RequestMapping(value = "getAllMergingUserDetails.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getAllMergingUserDetails(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
          logger.trace("Entering the view method.");
          Map<String, Object> hashMap = new HashMap<String, Object>();
        
        try {
            long userId = Long.parseLong(request.getParameter("userId"));
        	User user = userService.getAllDetails(userId);
        	List<AssessmentProgram> userAssessmentPrograms = assessmentProgramService.getAllAssessmentProgramByUserId(userId);
       
        	hashMap.put("assessmentPrograms", userAssessmentPrograms);
            hashMap.put("user", user);
        } catch (NumberFormatException e) {
            logger.debug("Failed to parse organizationId parameter: {}", request.getParameter("organizationId"));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            hashMap.put("invalidParams", true);
        }
        return hashMap;
    }
     
    /**
     * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
     * Resend user account activation email.
     * @param request {@link HttpServletRequest}
     * @throws ServiceException ServiceException
     */
    @RequestMapping(value = "resendUserActivationEmails.htm", method = RequestMethod.POST)
    public final @ResponseBody boolean resendUserActivationEmails(final HttpServletRequest request) throws ServiceException {
        logger.trace("Entering the resendUserActivationEmails method.");
        String[] userIdsParam = request.getParameterValues("ids[]");
        HashSet<Long> userIds = null;
        if(userIdsParam != null && userIdsParam.length>0){
        	userIds = new HashSet<Long>();
        	for(int i=0;i<userIdsParam.length;i++){
        		userIds.add(new Long(userIdsParam[i]));
        	}
        }

        List<Long> sendingUserIds = new ArrayList<Long>();
        List<UserOrganizationsGroups> userGroups = null;
        try{
        	userGroups = userOrganizationsGroupsService.getUserOrganizationsGroupsByUserIds(userIds);
        	if (userGroups != null && userGroups.size()>0) {
        		for(UserOrganizationsGroups userGroup : userGroups){
        			if (!sendingUserIds.contains(userGroup.getUserId())) {
        				logger.debug("Re-sending Activation email for userGroup {}", userGroup);
                        emailService.resendUserActivationEmail(userGroup);
                        sendingUserIds.add(userGroup.getUserId());
        			}
        			
        		}
        	}        	
        }catch(Exception e){
        		logger.error(e.getMessage(), e);
        }
        
        logger.trace("Leaving the resendUserActivationEmails method.");
        return true;
    }
    
    /**
	 * US16245 : User Management: View Users in Record Browser View Activate
	 * Change User Status manually.
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @throws ServiceException
	 *             ServiceException
	 */
	@RequestMapping(value = "chageUserStatusManually.htm", method = RequestMethod.POST)	
	public final @ResponseBody String activateUserManually(final HttpServletRequest request)throws ServiceException {		
		logger.trace("Entering the changeUserStatusManually method.");
		String[] userIdsParam = request.getParameterValues("ids[]");		
		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashSet<Long> userIds = null;
		int status=1;
		if (userIdsParam != null && userIdsParam.length > 0) {
			userIds = new HashSet<Long>();
			for (int i = 0; i < userIdsParam.length; i++) {
				userIds.add(new Long(userIdsParam[i]));
			}
		}
		
		List<Long> sendingUserIds = new ArrayList<Long>();
	    List<UserOrganizationsGroups> userGroups = null;
	       
	    if(StringUtil.compare("active", request.getParameter("status"))){
			status = UserStatus.PENDING;
		}else{
			status = UserStatus.INACTIVE;
		}		
		try{			
		 userOrganizationsGroupsService.changeUserStatusByIds(userIds,status,loggedinUser.getUser().getId(),
				 loggedinUser.getUser().getCurrentGroupsId(), loggedinUser.getUser().getCurrentAssessmentProgramId().longValue());
			 
		 //Added to fix DE-10152 - To send activation mail to the user by click on activation button
		 if(status == UserStatus.PENDING){
	   	 userGroups = userOrganizationsGroupsService.getUserOrganizationsGroupsByUserIds(userIds);
    	 if (userGroups != null && userGroups.size()>0) {
    		for(UserOrganizationsGroups userGroup : userGroups){
    			if (!sendingUserIds.contains(userGroup.getUserId())) {
    				logger.debug("Re-sending Activation email for userGroup {}", userGroup);
                    emailService.resendUserActivationEmail(userGroup);
                    sendingUserIds.add(userGroup.getUserId());
    			}    			
    		}
    	 }
		} 

		}catch(ServiceException e){
			logger.debug("selected User is Unathorized");
			return "{\"unAuthorized\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"permit\":\"false\"}";
		}
		logger.trace("Leaving the chageUserStatusManually method.");
		return "{\"permit\":\"true\"}";
	} 
    
    /**
     * Biyatpragyan Mohanty : US14307 : User Management: Add Users Manually
     * Save User information including user, organization and roles data.
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/saveUser.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> saveUser(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
        logger.trace("Entering the view method.");
        
        UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        String overwriteParam = request.getParameter("overwrite");
        String isAdminOperation = request.getParameter("isAdminOperation");
        
        boolean overwrite = false;
        if(overwriteParam != null && !"".equals(overwriteParam.trim())){
        	overwrite = Boolean.parseBoolean(overwriteParam);
        }
        
        List<UserRequest> users = getUsersFromRequest(request, response);
        Map<String, Object> hashMap = new HashMap<String, Object>();
        
        if(users != null && users.size()>0){
        	UserRequest userToSave = users.get(0);
        	
        	// This need to be executed for admin tools operaiton only 
            if(StringUtils.isNotBlank(isAdminOperation) && isAdminOperation.equals("true")){
            	if(userToSave.getId() != null){
            		userService.deleteInvalidUserContext(userToSave.getId());
            	}
            }
            
        	userToSave.setCreateModifierId(loggedinUser.getUserId());
        	UserRequest user = userService.saveUser(userToSave, overwrite, false);
        	hashMap.put("user", user);
        }
        return hashMap;
    }
    
    /**
     * Added for F451(save claim Users)
     */
	@RequestMapping(value = "/claimUser.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> claimUser(final HttpServletRequest request,
			final HttpServletResponse response) throws ServiceException {
		logger.trace("Entering the claimUser method.");

		
		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
			String overwriteParam = request.getParameter("overwrite");
			Boolean isFindUser = Boolean.parseBoolean((request.getParameter("isFindUser")));
			Long claimUserId = Long.parseLong(request.getParameter("userId"));

			boolean overwrite = false;
			if (overwriteParam != null && !"".equals(overwriteParam.trim())) {
				overwrite = Boolean.parseBoolean(overwriteParam);
			}

			List<UserRequest> users = getUsersFromRequest(request, response);
			Map<String, Object> hashMap = new HashMap<String, Object>();

			if (users != null && users.size() > 0) {
				UserRequest userToSave = users.get(0);

					if (claimUserId != null) {
						userService.updateInvalidUserContext(claimUserId, loggedinUser.getUserId());
					}

				userToSave.setCreateModifierId(loggedinUser.getUserId());
				UserRequest user = userService.saveUser(userToSave, overwrite, isFindUser);
				hashMap.put("user", user);
			}
		return hashMap;
	}
	
    /**
     * Sudhansu.b : Added for US_16821(provide UI TO move Users)
     */
	@RequestMapping(value = "/moveUser.htm", method = RequestMethod.POST)
	public final @ResponseBody String moveUser(@RequestParam("userIds[]") List<Long> userIds,
			@RequestParam("newOrganizationId") Long newOrganizationId,
			@RequestParam("oldOrganizationId") Long oldOrganizationId) throws ServiceException {
		logger.trace("Entering the moveUser method.");
		boolean updatedAll = true;
		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			
			for (Long userId : userIds) {
				boolean updated = userService.moveUser(userId, newOrganizationId, oldOrganizationId, loggedinUser);
				updatedAll = updatedAll && updated;
			}
		} catch (Exception e) {
			logger.error("Exception while moving user : ", e);
			return "{\"errorFound\":\"true\"}";
		}

		return "{\"success\":\"true\", \"updatedAll\":\""+updatedAll+"\"}";
	}
    
    /**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
    @RequestMapping(value = "/moveRoster.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> moveRoster(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
        logger.trace("Entering the move Roster method.");
        UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String overwriteParam = request.getParameter("overwrite");
        String rosterMoveuserIdParam = request.getParameter("rosterMoveuserId");
        boolean overwrite = false;
        if(overwriteParam != null && !"".equals(overwriteParam.trim())){
        	overwrite = Boolean.parseBoolean(overwriteParam);
        }
        
        List<UserRequest> users = getUsersFromRequest(request, response);
        Map<String, Object> hashMap = new HashMap<String, Object>();
        
        if(users != null && users.size()>0){
        	UserRequest userRosterToMove = users.get(0);
        	userRosterToMove.setCreateModifierId(loggedinUser.getUserId());
        	int userRequest;
			try {
				userRequest = userService.moveRoster(userRosterToMove, overwrite,rosterMoveuserIdParam);
				hashMap.put("user", userRequest);
			
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				hashMap.put("roleErrorMessage", e.getLocalizedMessage());
			}
        }
        return hashMap;
    }
    
    @RequestMapping(value = "/purgeUser.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> purgeUser(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
        logger.trace("Entering the purgeUser method.");
    
        String purgeUserId = request.getParameter("purgeUserId");
        
        Map<String, Object> hashMap = new HashMap<String, Object>();
    	UserRequest userRequest = new UserRequest();
       	userRequest.setId(Long.parseLong(purgeUserId));
        //US17687 - User audit history
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
			try {				
				userService.purgeUser(purgeUserId,userDetails);
				hashMap.put("user", userRequest);
			
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				hashMap.put("roleErrorMessage", e.getLocalizedMessage());
			}
        return hashMap;
    }
    
    /**
     * Biyatpragyan Mohanty : US14308 : User Management Upload Users CSV
     * Wrapper methid to reuse user upload functionality with added validations.
     * @param request
     * @param response
     * @return
     * @throws ServiceException 
     * @throws IOException 
     * 
     * @deprecated  Looks like this needs to be removed.  I can't find that it is used any longer.
     */
    @RequestMapping(value = "uploadUser.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadUser(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="continueOnWarning", required=false) Boolean continueOnWarning) throws IOException, ServiceException {    
    	try {
    		Long districtId = null;
    		Long stateId = Long.parseLong(request.getParameter("stateId"));
    		if(request.getParameter("districtId") != null && request.getParameter("districtId").length() > 0)
    			districtId = Long.parseLong(request.getParameter("districtId"));
			
    		//Long schoolId = Long.parseLong(request.getParameter("schoolId"));
    		Iterator<String> itr =  request.getFileNames();
    		MultipartFile mpf = request.getFile(itr.next());
    		CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
    		File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));
    		logger.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
    		cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
         
    		Category userRecordType = categoryUtil.getCSVUploadRecordTypes(uploadSpecification, uploadSpecification.getUserRecordType());
		
    		UploadFile uploadFile = new UploadFile();
    		uploadFile.setSelectedRecordTypeId(userRecordType.getId());
    		uploadFile.setRosterUpload(0);
    		uploadFile.setStateId(stateId);
    		if(districtId != null)
    			uploadFile.setDistrictId(districtId);
    		else 
    			uploadFile.setDistrictId(0l);

    		Long selectedOrgId = null;
    		String selectedOrgIdStr = request.getParameter("selectedOrgId");
			if(selectedOrgIdStr != null && selectedOrgIdStr.length() > 0) {
				selectedOrgId = Long.parseLong(selectedOrgIdStr);
			}
		
			if(selectedOrgId != null)
				uploadFile.setSelectedOrgId(selectedOrgId);
			else 
			uploadFile.setSelectedOrgId(0l);		
		
			uploadFile.setContinueOnWarning(continueOnWarning);
			if(null != request.getParameter("schoolId") && !"".equals(request.getParameter("schoolId"))) {
				uploadFile.setSchoolId(Long.parseLong(request.getParameter("schoolId")));
			} else {
			uploadFile.setSchoolId(0l);
			}
			uploadFile.setFile(destFile);
			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(uploadFile, reportUploadCategoryTypeCode);
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			UploadFileRecord uploadFileRecord = buildUploadFileRecord(
					user, cmpf.getOriginalFilename(), inProgressStatus.getId());
			uploadFileRecordService.insertUploadFile(uploadFileRecord);
			
			Future<Map<String,Object>> futureMav = uploadFileProcessor.create(uploadFile, errors, uploadFileRecord.getId());
			//request.getSession().setAttribute("uploadUserFileProcess", futureMav);
			
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("uploadFileRecordId", uploadFileRecord.getId());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
			String modelJson = mapper.writeValueAsString(model);
			return modelJson;
    	} catch(Exception e) {
			logger.error("Exception ooccured while uploading User file: " + e.getMessage());			
			return "{\"errorFound\":\"true\"}";
		}
	}
    
    /**
     * Biyatpragyan Mohanty : US14308 : User Management Upload Users CSV
     * Wrapper methid to reuse user upload functionality with added validations.
     * @param request
     * @param response
     * @return
     * @throws ServiceException 
     * @throws IOException 
     * 
     * @deprecated PD Training is no longer used.  Probably need to remove
     */
    @RequestMapping(value = "uploadPDTrainingResults.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadPDTrainingResults(MultipartHttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="continueOnWarning", required=false) Boolean continueOnWarning) throws IOException, ServiceException {    
    	try {
    		Iterator<String> itr =  request.getFileNames();
    		MultipartFile mpf = request.getFile(itr.next());
    		CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
    		File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));
    		logger.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
    		cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
         
    		Category userRecordType = categoryUtil.getCSVUploadRecordTypes(uploadSpecification, uploadSpecification.getPdTrainingUploadType());
    		logger.debug("userRecordType ID", userRecordType.getId());
    		UploadFile uploadFile = new UploadFile();
    		uploadFile.setSelectedRecordTypeId(userRecordType.getId());
    		uploadFile.setRosterUpload(0);
    		
		
			uploadFile.setContinueOnWarning(continueOnWarning);
			uploadFile.setFile(destFile);
			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(uploadFile, reportUploadCategoryTypeCode);
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			UploadFileRecord uploadFileRecord = buildUploadFileRecord(
					user, cmpf.getOriginalFilename(), inProgressStatus.getId());
			uploadFileRecordService.insertUploadFile(uploadFileRecord);
			
			Future<Map<String,Object>> futureMav = uploadFileProcessor.create(uploadFile, errors, uploadFileRecord.getId());
			//request.getSession().setAttribute("uploadUserFileProcess", futureMav);
			
			String responsejson= uploadFileRecordService.getUploadFile(uploadFileRecord.getId());
			while(responsejson == null) {
				responsejson= uploadFileRecordService.getUploadFile(uploadFileRecord.getId());
				Thread.sleep(1000);
			}
			JSONObject jsonObj = new JSONObject(responsejson);
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("uploadFileRecordId", uploadFileRecord.getId());
			model.put("recordsRejectedCount",jsonObj.get("recordsRejectedCount"));
			model.put("recordsSkippedCount",jsonObj.get("recordsSkippedCount"));
			model.put("uploadFileStatus",jsonObj.get("uploadFileStatus"));
			model.put("recordsUpdatedCount",jsonObj.get("recordsUpdatedCount"));
			model.put("recordsCreatedCount",jsonObj.get("recordsCreatedCount"));
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
			String modelJson = mapper.writeValueAsString(model);
			return modelJson;
    	} catch(Exception e) {
			logger.error("Exception ooccured while uploading PD Training Results file: " + e.getMessage());			
			return "{\"errorFound\":\"true\"}";
		}
	}
    
    private UploadFileRecord buildUploadFileRecord(User user, String fileName, Long statusId) {
		UploadFileRecord uploadFileRecord = new UploadFileRecord();
		uploadFileRecord.setFileName(fileName);
		uploadFileRecord.setCreatedUser(user.getId());
		uploadFileRecord.setCreatedDate(new Date());
		uploadFileRecord.setModifiedUser(user.getId());
		uploadFileRecord.setModifiedDate(new Date());
		uploadFileRecord.setStatusId(statusId);
		return uploadFileRecord;
	}
    /**
     * 
     * @param request
     * @param response
     * @param uploadFileRecordId
     * @return
     * @throws Exception
     * @deprecated not used any longer that I can find
     */
	@RequestMapping(value = "monitorUploadUserFile.htm", method = RequestMethod.GET)
	public @ResponseBody String monitorUploadUserFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uploadFileRecordId") Long uploadFileRecordId) throws Exception { 
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		UploadFileRecord uploadFileRecord = uploadFileRecordService.selectByPrimaryKeyAndCreateUserId(
				uploadFileRecordId, user.getId());
		Category status = categoryService.selectByPrimaryKey(uploadFileRecord.getStatusId());
		
		if (status.getCategoryCode().equals("COMPLETED") || status.getCategoryCode().equals("FAILED")) {
			return uploadFileRecord.getJsonData();
		}
		return "{\"uploadFileStatus\": \"IN_PROGRESS\"}";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param uploadFileRecordId
	 * @return
	 * @throws Exception
	 * @deprecated  PD training is no longer used this should be removed
	 */
	@RequestMapping(value = "monitorUploadPDTrainingResultsFile.htm", method = RequestMethod.GET)
	public @ResponseBody String monitorUploadPDTrainingResultsFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uploadFileRecordId") Long uploadFileRecordId) throws Exception { 
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		UploadFileRecord uploadFileRecord = uploadFileRecordService.selectByPrimaryKeyAndCreateUserId(
				uploadFileRecordId, user.getId());
		Category status = categoryService.selectByPrimaryKey(uploadFileRecord.getStatusId());
		
		if (status.getCategoryCode().equals("COMPLETED") || status.getCategoryCode().equals("FAILED")) {
			return uploadFileRecord.getJsonData();
		}
		return "{\"uploadFileStatus\": \"IN_PROGRESS\"}";
	}
	/**
	 * 
	 * @param request
	 * @param response
	 * @param uploadFileRecordId
	 * @throws Exception
	 * 
	 * @deprecated PD Training is no longer used.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "downloadFailedPDTrainingRecords.htm", method = RequestMethod.GET)
	public void  downloadFailedRecords(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uploadFileRecordId") Long uploadFileRecordId) throws Exception { 
		
		logger.debug("uploadFileRecordId "+uploadFileRecordId);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		UploadFileRecord uploadFileRecord = uploadFileRecordService.selectByPrimaryKeyAndCreateUserId(
				uploadFileRecordId, user.getId());

		String jsonData = uploadFileRecord.getJsonData();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties

		Map<String,Object> invalidRecordMap = objectMapper.readValue(jsonData, Map.class);
		
		ArrayList<Map<String, Object>> invalidRecords = (ArrayList<Map<String, Object>>) invalidRecordMap.get("inValidRecords");
		
		List<User> failedRecords = new ArrayList<User>();
		for(Map<String, Object> invalidRecord: invalidRecords){
			User uploadedUser = new User();
			List<Object> invalidDetails = (List<Object>) invalidRecord.get("inValidDetails");
			String firstName = (String)invalidRecord.get("firstName");
			String lastName = (String)invalidRecord.get("surName");
			String uniqueIdNumber = (String)invalidRecord.get("uniqueCommonIdentifier");
			String schoolName = (String)invalidRecord.get("schoolName");
			String schoolID = (String)invalidRecord.get("schoolID");
			String state = (String)invalidRecord.get("state");
			String districtName = (String)invalidRecord.get("districtName");
			String districtID = (String)invalidRecord.get("districtID");
			Boolean rtComplete = (Boolean)invalidRecord.get("rtComplete");
			if(null != invalidRecord.get("rtCompleteDate")){
				Date rtCompleteDate = new Date((Long)invalidRecord.get("rtCompleteDate"));
				uploadedUser.setRtCompleteDate(rtCompleteDate);	
			}
			String email = (String)invalidRecord.get("email");
			String failedReason = (String)invalidRecord.get("failedReason");
			
			uploadedUser.setFirstName(firstName);
			uploadedUser.setSurName(lastName);
			uploadedUser.setEmail(email);
			uploadedUser.setUniqueCommonIdentifier(uniqueIdNumber);
			uploadedUser.setSchoolName(schoolName);
			uploadedUser.setSchoolID(schoolID);
			uploadedUser.setState(state);
			uploadedUser.setDistrictID(districtID);
			uploadedUser.setDistrictName(districtName);
			uploadedUser.setRtComplete(rtComplete);
			
			StringBuilder failedReasons = new StringBuilder();
			for(Object value : invalidDetails){
				Map<String, Object> invalidDetail = (Map<String, Object>)value;
				String fieldName = (String) invalidDetail.get("fieldName");
				if("ID Number".equalsIgnoreCase(fieldName)){
					String fieldValue = (String) invalidDetail.get("fieldValue");
					if(StringUtils.isNotBlank(fieldValue)){
						uploadedUser.setId(Long.valueOf(fieldValue));
					}
				}
				boolean isRejected = (boolean) invalidDetail.get("rejectRecord");
				if(!isRejected || (!"FirstName".equalsIgnoreCase(fieldName) && !"LastName".equalsIgnoreCase(fieldName) 
					&& !"First Name".equalsIgnoreCase(fieldName) && !"Last Name".equalsIgnoreCase(fieldName)
					&& !("ID Number".equalsIgnoreCase(fieldName)))){
					if("Rt Complete Date".equals(fieldName)){
						failedReasons.append("The record is rejected because ").append(fieldName);
						failedReasons.append(" ").append((String)invalidDetail.get("formattedFieldValue")).append("\n");
					} else if ("RT Complete Date".equals((String)invalidDetail.get("actualFieldName"))) {
						failedReasons.append("The record is rejected because ").append((String)invalidDetail.get("actualFieldName"));
						failedReasons.append(" ").append((String)invalidDetail.get("reason")).append("\n");
					} else {
						failedReasons.append("The record is rejected because ").append(fieldName);
						failedReasons.append((String)invalidDetail.get("reason")).append("\n");
					}
					
				}
			}
			if(null != failedReason){
				failedReasons.append(failedReason);
			}
			uploadedUser.setFailedReason(failedReasons.toString());
			failedRecords.add(uploadedUser);	
		}
		writePDTrainingInvalidData(failedRecords, response, "PD_Training_Results_CSV_Failed_Records.csv");
	}
    
	
	private void writePDTrainingInvalidData(List<User> trainingDetailsDtos, HttpServletResponse response, String fileName) throws IOException {
    	
    	CSVWriter csvWriter = null;
    	 try {

			response.setContentType("application/force-download");
    		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
    		
	        csvWriter = new CSVWriter(response.getWriter(), ',');
	        List<String[]> data  = pdTrainingFailedRecordsData(trainingDetailsDtos);
	        csvWriter.writeAll(data);
	
    	 } catch (IOException ex) {
    		 throw ex;
    	 } finally {
         	if(csvWriter != null) {
         		csvWriter.close();
         	}
         }
    }


	private List<String[]> pdTrainingFailedRecordsData(List<User> reportDtos) {
	    List<String[]> records = new ArrayList<String[]>();
	    //add header record
	    records.add(new String[]{"FirstName","LastName",
	    				"Email","idnumber",
	    				"State","DistrictName","DistrictID",
	    				"SchoolName","SchoolID",
	    				"RTComplete","RTComplete Date", "Failure Reason"});        
	
	    Iterator<User> it = reportDtos.iterator();
	    SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	    
	    while(it.hasNext()){
	    	User dto = it.next();        	
	    	
	        String rtCompleteDateStr = null == dto.getRtCompleteDate() ? "" : df2.format(dto.getRtCompleteDate());
	        String isRTCompleteStr = (dto.isRtComplete() ? "Yes" : "No");
	        String userId = dto.getId() == null ? "" : dto.getId().toString();
	        records.add(new String[]{dto.getFirstName(), dto.getSurName(),
	        		dto.getEmail(),userId,
	        		dto.getState(),dto.getDistrictName(),dto.getDistrictID(),
	        		dto.getSchoolName(),dto.getSchoolID(),
	        		isRTCompleteStr,rtCompleteDateStr, dto.getFailedReason()});
	    }
	    return records;
	}
	
    /**
     * Biyatpragyan Mohanty : US14307 : User Management: Add Users Manually
     * User request to parse required information out of it and populate required bean.
     * @param request
     * @param response
     * @return
     */
    private List<UserRequest> getUsersFromRequest(final HttpServletRequest request, final HttpServletResponse response) {
    	logger.trace("Entering UserController : getUsersFromRequest");
    	logger.debug("users: " + request.getParameter("users"));
        String usersParam = request.getParameter("users");

        List<UserRequest> users = new ArrayList<UserRequest>();
        if (usersParam != null && !"".equals(usersParam)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
            	users = mapper.readValue(usersParam, new TypeReference<List<UserRequest>>() { });
    	    } catch (Exception e) {
    	    		logger.error(e.getMessage(), e);
    	    }
        }
        if(users != null){
        	for(UserRequest user : users ){
        		if(user.getDefaultOrgId() == null){
        			user.setDefaultOrgId(new Long(0));
                }
        	}
            
        	
        }
        logger.trace("Leaving UserController : getUsersFromRequest");
        return users;
    }
    
    
    /**
     * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
	 * Retrieve user security agreement details for given user and assessment.
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
	@RequestMapping(value = "/getSecurityAgreementInfo.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getSecurityAgreementInfo(final HttpServletRequest request,
			final HttpServletResponse response) throws ServiceException {
		logger.trace("Entering the view method.");
		Map<String, Object> hashMap = new HashMap<String, Object>();
		boolean securityAgreementRequired = false;
		SecurityAgreementDTO sadto = new SecurityAgreementDTO();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		if (user.getCurrentOrganizationType().equals("CONS")) {
			securityAgreementRequired = false;
		} else {

			Organization contractingOrganization = null;

			List<Organization> contractingOrganizations = new ArrayList<Organization>();
			UserSecurityAgreement userSecurityAgreement = null;
			SimpleDateFormat dateFormat = null;
			Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();

			/* find contracting organization i.e. state */
			// contractingOrganizations =
			// organizationService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE,
			// user.getId());
			List<Organization> userOrgs = user.getOrganizations();

			for (Organization userOrg : userOrgs) {
				List<Organization> organizationHierarchy = organizationService
						.getLoggedInUserOrganizationHierarchy(userOrg.getId(), CommonConstants.ORGANIZATION_STATE_CODE);
				if (organizationHierarchy != null) {
					contractingOrganizations.addAll(organizationHierarchy);
				}
			}

			/*
			 * From all contracting organization hierarchy, find if the
			 * assessment program is DLM or KELPA2 or CPASS or KAP
			 */
			if (contractingOrganizations != null && contractingOrganizations.size() > 0) {
				boolean foundDLM = false;
				for (Organization org : contractingOrganizations) {
					List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(org.getId());
					if (orgAssessProgs != null && orgAssessProgs.size() > 0) {
						for (OrgAssessmentProgram oap : orgAssessProgs) {
							if (oap.getAssessmentProgram() != null && (CommonConstants.ASSESSMENT_PROGRAM_DLM
									.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
									|| CommonConstants.ASSESSMENT_PROGRAM_KELPA2.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
									|| CommonConstants.ASSESSMENT_PROGRAM_CPASS.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
									||  CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
									|| CommonConstants.ASSESSMENT_PROGRAM_PLTW.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname()))) {
								securityAgreementRequired = true;
								sadto.setDlm(true);
								sadto.setExpired(true);
								Organization orgDetails = organizationService.get(org.getId());
								org.setSchoolEndDate(orgDetails.getSchoolEndDate());
								contractingOrganization = org;
								List<UserSecurityAgreement> userSecurityAgreementList = userService.getSecurityAgreementInfo(user.getId(), user.getCurrentAssessmentProgramId(),
												currentSchoolYear);
								if (userSecurityAgreementList != null && userSecurityAgreementList.size() > 0) {
									userSecurityAgreement = userSecurityAgreementList.get(0);
									sadto.setSignerName(userSecurityAgreement.getSignerName());
									Date agreementSignedDate = userSecurityAgreement.getAgreementSignedDate();
									dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
									sadto.setAgreementElection(userSecurityAgreement.getAgreementElection());
									if (agreementSignedDate != null) {
										sadto.setAgreementSignedDate(DateUtil.convertDatetoSpecificTimeZoneStringFormat(
												agreementSignedDate, "CST", "MM/dd/yyyy hh:mm:ss a"));
										Calendar usersecAgreementSignedDate = Calendar.getInstance();
										// here inputDate is date given by the
										// user.
										usersecAgreementSignedDate.setTime(agreementSignedDate);
										Calendar schoolStartDate = Calendar.getInstance();
										schoolStartDate.setTime(orgDetails.getSchoolStartDate());
										if (usersecAgreementSignedDate.after(schoolStartDate)) {
											sadto.setExpired(false);
											foundDLM = true;
											break;
										}
									}

								}

							}
						}
					}
					if (foundDLM)
						break;
				}

				Date endDate = null;
				if (contractingOrganization != null) {
					endDate = contractingOrganization.getSchoolEndDate();
				}
				dateFormat = new SimpleDateFormat("yyyy");
				String year = "";
				if (endDate != null) {
					try {
						year = dateFormat.format(endDate);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				sadto.setSchoolYear(year);
				if (!"".equals(year) && sadto.isAgreementElection()) {
					sadto.setExpireDate((securityAgreementExpirationMMDD == null) ? ""
							: securityAgreementExpirationMMDD.trim() + "/" + sadto.getSchoolYear());
				} else {
					sadto.setExpireDate("");
					// sadto.setExpired(false);
				}
			}
		}

		if (securityAgreementRequired) {
			if (sadto.isExpired()) {
				sadto.setSignerName("");
				sadto.setExpireDate("");
				sadto.setAgreementElection(false);
				sadto.setAgreementSignedDate("");
			}
			hashMap.put("securityAgreement", sadto);
		}

		return hashMap;
	}
    
    /**
     * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
	 * Save user security agreement details for assessment program.
     * @param request
     * @param response
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = "/saveSecurityAgreementInfo.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> saveSecurityAgreementInfo(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
        logger.trace("Entering the view method.");
                
        String signerName = request.getParameter("signerName");
        
        String agreementElectionParam = request.getParameter("agreementElection");
        boolean agreementElection = false;
        if(agreementElectionParam != null && !"".equals(agreementElectionParam.trim())){
        	agreementElection = Boolean.parseBoolean(agreementElectionParam);
        }
        Date agreementSignedDate = new Date();
        String expireDate = "";
		String year = "";
        Long schoolYear = new Long(0);
        Long assessmentProgramId = new Long(0);
        
        Map<String, Object> hashMap = new HashMap<String, Object>();        
		Organization contractingOrganization = null;
		List<Organization> contractingOrganizations = new ArrayList<Organization>();
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		/* find contracting organization i.e. state */
		List<Organization> userOrgs = user.getOrganizations();
		for(Organization userOrg : userOrgs){
			List<Organization> organizationHierarchy = organizationService.getLoggedInUserOrganizationHierarchy(userOrg.getId(), CommonConstants.ORGANIZATION_STATE_CODE);
			if(organizationHierarchy != null){
				contractingOrganizations.addAll(organizationHierarchy);
			}
		}
		/* From all contracting organization hierarchy, find if the assessment program is DLM*/
		if(contractingOrganizations!=null && contractingOrganizations.size()>0){			
			for(Organization org : contractingOrganizations){
				List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(org.getId());
				if(orgAssessProgs != null && orgAssessProgs.size() > 0){
					for (OrgAssessmentProgram oap : orgAssessProgs) {
						if(oap.getAssessmentProgram() != null && (
								CommonConstants.ASSESSMENT_PROGRAM_DLM.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
								|| CommonConstants.ASSESSMENT_PROGRAM_KELPA2.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
								|| CommonConstants.ASSESSMENT_PROGRAM_CPASS.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
								||  CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
								|| CommonConstants.ASSESSMENT_PROGRAM_PLTW.equalsIgnoreCase(oap.getAssessmentProgram().getAbbreviatedname())
								)){
							
							Organization orgDetails = organizationService.get(org.getId());
							org.setSchoolEndDate(orgDetails.getSchoolEndDate());
							contractingOrganization = org;
							assessmentProgramId = oap.getAssessmentProgram().getId();
						}
			        }
				}
			}
			
			Date endDate = null;
			if(contractingOrganization != null){
				endDate = contractingOrganization.getSchoolEndDate();				
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			if(endDate != null){
				try{
					year = dateFormat.format(endDate);
					schoolYear = new Long(year);
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
			}
			if(!"".equals(year)){
				expireDate = securityAgreementExpirationMMDD + "/" + year;				
			} else{
				expireDate = "";
			}
		}
		
		/* save user security agreement information */
		Integer result = userService.saveSecurityAgreementInfo(user.getId(), user.getCurrentAssessmentProgramId(), user.getContractingOrganization().getCurrentSchoolYear(), agreementElection, agreementSignedDate, expireDate, signerName); 
		if(result>0){
			hashMap.put("success", true);			
		}

        return hashMap;
    }
    /*
     * Added during US16351-To populate the value in assessment programs dropdown in addStudentManually page 
     */
    @RequestMapping(value = "getCurrentUserAssessmentPrograms.htm", method = RequestMethod.POST)
    public final @ResponseBody List<AssessmentProgram> getAssessmentPrograms() {
		logger.trace("Entering the getCurrentUserAssessmentPrograms() method");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 		User user = userDetails.getUser();
        
        List<AssessmentProgram> assessmentPrograms = new ArrayList<AssessmentProgram>();
        
        assessmentPrograms=assessmentProgramService.getAllAssessmentProgramByUserId(user.getId());
        
        logger.trace("Leaving the getAssessmentPrograms() method");
        return assessmentPrograms;        
    }
    //Added during DE10302
    @RequestMapping(value = "checkRosterforUser.htm", method = RequestMethod.POST)
    public final @ResponseBody boolean checkRosterforUser(@RequestParam("puserId") String puserId) {
      boolean hasRoster = false;
      	if(userService.checkRosterByUserId(puserId)){
      		hasRoster = true;
      	}
    	
    	return hasRoster;        
    }
    
    
    
    @RequestMapping(value = "getGroupsOrgTypeMap.htm", method = RequestMethod.GET)
	public @ResponseBody String getGroupsOrgTypeMap() throws Exception {
		List<Groups> groups= groupsService.getAllGroups();
		Map<Long, Long> groupsOrgTypeMap = new HashMap<Long, Long>();
		Map<Long, Long> groupsRoleTypeMap = new HashMap<Long, Long>();
		Map<String, Long> groupsNameMap = new HashMap<String, Long>();
		Map<Long, String> groupsCodeMap = new HashMap<Long, String>();
		Map<Long, Long> groupsHierarchyMap = new HashMap<Long, Long>();
		for(Groups group: groups){
			groupsOrgTypeMap.put(group.getGroupId(), group.getOrganizationTypeId());
			groupsRoleTypeMap.put(group.getGroupId(), group.getRoleorgtypeid());
			groupsNameMap.put(group.getGroupName(), group.getGroupId());
			groupsCodeMap.put(group.getGroupId(),group.getGroupCode());
			groupsHierarchyMap.put(group.getGroupId(),group.getHierarchy());
		}
			
		Map<String,Object> model = new HashMap<String,Object>();
	    model.put("groupsOrgMap", groupsOrgTypeMap);
	    model.put("groupsRoleTypeMap", groupsRoleTypeMap);
	    model.put("groupsNameMap", groupsNameMap);
	    model.put("groupsCodeMap", groupsCodeMap);
	    model.put("groupsHierarchyMap", groupsHierarchyMap);
	    
	    ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
		String modelJson = mapper.writeValueAsString(model);
		return modelJson;
	}
    
    
	@RequestMapping(value = "getInactiveUsersForAdmin.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getInactiveUsersForAdmin(
			@RequestParam("rows") String limitCountStr, @RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn, @RequestParam("sord") String sortType,
			@RequestParam("requestFor") String requestFor,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value = "showInactiveUsers", required = false) String showInactiveUsers)
			throws JsonProcessingException, IOException, NoAccessToResourceException {

		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		Integer currentPage = null;
		Integer limitCount = null;
		List<User> users;
		Integer totalCount = -1;

		Boolean showInactiveUsersFlag = true;
		
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("isSysAdmin", userDetails.getUser().isSysAdmin());
		criteria.put("requestFor", requestFor);
		criteria.put("showInactiveUsers", showInactiveUsersFlag);

		populateCriteria(criteria, filters);

		users = userService.getInactiveUsersForAdmin(criteria, sortByColumn, sortType, (currentPage - 1) * limitCount,
				limitCount);

		totalCount = userService.getInactiveUsersCountForAdmin(criteria);

		jqGridJSONModel = UsersJsonConverter.convertToUserJson(users, totalCount, currentPage, limitCount);
		
		return jqGridJSONModel;
	}
	
	/**
     * Added for F451(provide UI TO claim Users)
     */
	@RequestMapping(value = "getUsersForClaim.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getUsersForClaim(
			@RequestParam("rows") String limitCountStr, @RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn, @RequestParam("sord") String sortType,
			@RequestParam("requestFor") String requestFor,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam("userFirstName") String firstName,
			@RequestParam("userLastName") String lastName,
			@RequestParam("educatorId") String educatorId,
			@RequestParam(value = "showInactiveUsers", required = false) String showInactiveUsers)
			throws JsonProcessingException, IOException, NoAccessToResourceException {

		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		Integer currentPage = null;
		Integer limitCount = null;
		List<User> users;
		Integer totalCount = -1;

		Boolean showInactiveUsersFlag = true;
		
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
        Long userOrgId = userDetails.getUser().getOrganization().getId();
        Long stateId = organizationService.getStateIdByUserOrgId(userOrgId);
		
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("organizationId", stateId);
		criteria.put("isSysAdmin", userDetails.getUser().isSysAdmin());
		criteria.put("requestFor", requestFor);
		criteria.put("userFirstName", firstName);
		criteria.put("userLastName", lastName);
		criteria.put("educatorId", educatorId);
		criteria.put("currentGroupId", userDetails.getUser().getCurrentGroupsId());
		criteria.put("showInactiveUsers", showInactiveUsersFlag);

		populateCriteria(criteria, filters);

		users = userService.getAllUsersBySearch(criteria, sortByColumn, sortType, (currentPage - 1) * limitCount,
				limitCount);

		totalCount = userService.getCountForFindUsers(criteria);

		jqGridJSONModel = UsersJsonConverter.convertToUserJson(users, totalCount, currentPage, limitCount);
		
		return jqGridJSONModel;
	}

	@RequestMapping(value = "getUserOrganizationsForUserClaim.htm", method = RequestMethod.POST)
	public final @ResponseBody String getUserOrganizationsForUserClaim(
			@RequestParam("userId") Long userId) throws ServiceException, JsonProcessingException{
			
		try{
			Map<String, String> model = new HashMap<String, String>();
	        ObjectMapper mapper = getObjectMapper();
			HashSet<String> districts = new HashSet<String>();
			List<Organization> userOrg = userService.getUsersOrganizationsByUserId(userId);
			for (Organization org : userOrg) {
				if (org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_7) {
					String districtNames = userService.getDistrictNames(org.getId());
					districts.add(districtNames);
				}
				if (org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_5) {
					districts.add(org.getOrganizationName());
				}
			}
			
			model.put("districts", districts.toString());

            String modelJson = mapper.writeValueAsString(model);
            return modelJson;
		} catch(Exception e) {
			logger.error("Exception occured while getting User organizations: " + e.getMessage(),e);			
			return "{\"errorFound\":\"true\"}";
		}
	}

	private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
        return mapper;
    }
	/**
     * As part of F451 
     * this method sends email to all DTC's for particular user
     */
	
	@RequestMapping(value = "/sendEmailToDTCUsers.htm", method = RequestMethod.POST)
	public final @ResponseBody String sendEmailToDTCUsers(@RequestParam("userId") Long claimUserId) throws ServiceException {
		logger.trace("Entering the sendEmailToDTCUsers method.");
		HashSet<String> districts = new HashSet<String>();
		try {
			HashSet<Long> userIds = new HashSet<Long>();
			
			List<Organization> userOrg = userService.getUsersOrganizationsByUserId(claimUserId);
			for (Organization org : userOrg) {
				if (org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_7) {
					String districtNames = userService.getDistrictNames(org.getId());
					districts.add(districtNames);
				}
				if (org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_5) {
					districts.add(org.getOrganizationName());
				}
			}

			for (String districtName : districts) {
				List<Long> aartUserIds = userService.getDTCUserIdsByOrganizationName(districtName);
				userIds.addAll(aartUserIds);
			}
			if(!userIds.isEmpty()){
				emailService.sendEmailToDTCUsers(userIds, claimUserId);
				userService.auditClaimUserDetails(claimUserId);
				return "{\"success\":\"Email Sent Successfully\"}";
			}else{
				return "{\"errorFound\":\"error occured while sending email\"}";
			}
		} catch (Exception e) {
			logger.error("error occured while sending email", e);
			return "{\"errorFound\":\"error occured while sending email\"}";
		}   
	}
	
	
	@RequestMapping(value = "/getAllInactiveUserDetailsForAdmin.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getAllInactiveUserDetailsForAdmin(final HttpServletRequest request,
			final HttpServletResponse response) throws ServiceException {
		logger.trace("Entering the getAllInactiveUserDetailsForAdmin method.");
		Map<String, Object> hashMap = new HashMap<String, Object>();

		try {
			long userId = Long.parseLong(request.getParameter("userId"));

			User user = userService.getAllInactiveUserDetailsForAdmin(userId);
			hashMap.put("user", user);

			List<UserAssessmentPrograms> userRoles = new ArrayList<UserAssessmentPrograms>();
			// We do not need the data from store, We are going to clean the store and update new data.
			//List<UserAssessmentPrograms> userRoles = userService.getInactiveUserRolesForAdmin(userId);
			hashMap.put("userRoles", userRoles);
		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			hashMap.put("invalidParams", true);
		}

		return hashMap;
	}
	
	@RequestMapping(value = "getInternalUsersByOrganization.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getInternalUsersByOrganization(
			@RequestParam("orgChildrenIds") String[] orgChildrenIds, @RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("requestFor") String requestFor,
			@RequestParam(value = "filters", required = false) String filters)
			throws JsonProcessingException, IOException, NoAccessToResourceException {

		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		Integer currentPage = null;
		Integer limitCount = null;
		List<User> users;
		Integer totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		long filterOrgId = NumericUtil.parse(orgChildrenIds[0], -1);
		if (childrenOrgIds.contains(filterOrgId)) {
			Map<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("organizationId", NumericUtil.parse(orgChildrenIds[0], -1));
			criteria.put("isSysAdmin", userDetails.getUser().isSysAdmin());
			criteria.put("requestFor", requestFor);
			criteria.put("assessmenProgramId", userDetails.getUser().getCurrentAssessmentProgramId());
			criteria.put("currentGroupId", userDetails.getUser().getCurrentGroupsId());

			populateCriteria(criteria, filters);

			users = userService.getUsersByUserType(criteria, sortByColumn, sortType,
					(currentPage - 1) * limitCount, limitCount);

			totalCount = userService.countAllInternalUsersToView(criteria);

			jqGridJSONModel = UsersJsonConverter.convertToUserJson(users, totalCount, currentPage, limitCount);
		} else {
			throw new NoAccessToResourceException("Access to this information is restricted.");
		}
		return jqGridJSONModel;
	}
	

	@RequestMapping(value = "getDistinctGroupCodesBasedOnUserIds.htm", method = RequestMethod.POST)
	public final @ResponseBody  Map<String, Object> getDistinctGroupCodesBasedOnUserIds(
			@RequestParam("userIds[]") String[] userIds){
		
			Map<String, Object> resultMap = new HashMap<String, Object>();		
			
			if (userIds != null	&& ArrayUtils.isNotEmpty(userIds)) {
				List<Long> userIDs =  AARTCollectionUtil.getListAsLongType(userIds);		
				List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService.getDistinctGroupCodesBasedOnUserIds(userIDs);			
				resultMap.put("userOrganizationsGroups", userOrganizationsGroups);
			}
			
			return resultMap;
	}
	
	@RequestMapping(value = "getSecurityAgreement.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getSecurityAgreement(final HttpServletRequest request,
			final HttpServletResponse response) throws ServiceException {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		dateFormat = null;
		try {
			long userId = Long.parseLong(request.getParameter("id"));
			List<UserSecurityAgreement> userSecurityAgreementList = userService.getSecurityAgreement(userId);
			if(userSecurityAgreementList.size()>=1) {
		    Date strDate= userSecurityAgreementList.get(0).getAgreementSignedDate();
		    //DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z"); 
		    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		    String expireDate = dateFormat.format(strDate); 
		//    String expireDate = dateFormat.format(strDate);
		    String signerName= userSecurityAgreementList.get(0).getSignerName();
		    
		    boolean isAgreed = userSecurityAgreementList.get(0).getAgreementElection();
				
		    hashMap.put("expiredate",expireDate);
		    hashMap.put("signername", signerName);
		    hashMap.put("isagreed", isAgreed);
			}
		} catch (NumberFormatException e) {
			//logger.debug("Failed to parse organizationId parameter: {}", request.getParameter("organizationId"));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			hashMap.put("invalidParams", true);
		}
		return hashMap;
	}
	
	@RequestMapping(value="getTrainingCompletion.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String,Object> getTrainigCompletion(final HttpServletRequest request,
			final HttpServletResponse response)
	{
		Map<String, Object> hashMap = new HashMap<String, Object>();
		long userId = Long.parseLong(request.getParameter("id"));
		List<DLMPDTrainingDTO> trainingList =userService.getTrainingDetails(userId);
		if(trainingList.size()>=1) {
			
		String isAgreed= trainingList.get(0).getRtComplete();
		Date agreedOn= trainingList.get(0).getRtCompleteDate();
		DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z"); 
		String agreedDate = dateFormat.format(agreedOn);
		hashMap.put("isagreed", isAgreed);
		hashMap.put("agreedate", agreedDate);	
	}
		return hashMap;
	}

}
