package edu.ku.cete.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ku.cete.domain.GroupRestrictions;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.UserStatus;
import edu.ku.cete.web.Metadata;
import edu.ku.cete.web.OrganizationDto;

/**
 *
 * @author neil.howerton
 *
 */
@Controller
public class SystemAdminController {
    private Logger logger = LoggerFactory.getLogger(SystemAdminController.class);

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserOrganizationsGroupsService userOrganizationsGroupsService;

    @Autowired
    private EmailService emailService;

    /*
     * limit the size of an in clause being sent to query
     */
    @Value("${in.clause.split.limit}")
    private int inClauseLimit;
    
    /**
     * This method is triggered when a user adds a new role in the UI.
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return List<Groups>
     */
    @RequestMapping(value = "addRole.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Groups> addRole(final HttpServletRequest request, final HttpServletResponse response) {
        logger.trace("Entering the addRole method.");
        String groupName = request.getParameter("groupName");
        String strOrgId = request.getParameter("organizationId");

        if (groupName != null && strOrgId != null && !groupName.equals("") && !strOrgId.equals("")) {
            logger.debug("Adding new role with name '{}' for organization with id {}", groupName, strOrgId);
            Groups group = new Groups();
            group.setGroupName(groupName);
            group.setOrganizationId(Long.parseLong(strOrgId));
            groupsService.addGroup(group);
            UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            /*
             * Here we are returning a list of all the new groups, including the one newly created to the front end.
             */
            List<Groups> groups = groupsService.getByDefaultRoleAndOrganizationTree(false, user.getUser().getOrganizationId());
            logger.trace("Leaving the addRole method.");
            return groups;
        } else {
            logger.debug("The parameters groupName: {} and strOrgId: {} were invalid. No group will be added, and the user will " +
                    "be informed of the failure.", groupName, strOrgId);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.trace("Leaving the addRole method.");
            return null;
        }
    }

    /**
     *
     *@param request {@link HttpServletRequest}
     *@param response {@link HttpServletResponse}
     *@return boolean whether the add completed successfully or not.
     */
    @RequestMapping(value = "addRoleToUser.htm", method = RequestMethod.POST)
    public final @ResponseBody boolean addRoleToUser(final HttpServletRequest request, final HttpServletResponse response) {
        logger.trace("Entering the addRoleToUser method.");

        String[] groups = request.getParameterValues("roles[]");
        List<Long> groupIds = new ArrayList<Long>();
        String userId = request.getParameter("user");
        String orgId = request.getParameter("organizationId");

        for (String role : groups) {
            try {
                groupIds.add(Long.parseLong(role));
            } catch (NumberFormatException e) {
                logger.debug("Failed to parse roleId: {}", role);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return false;
            }
        }

        if (userId != null && userId != "" && StringUtils.isNumeric(userId)
                && orgId != null && orgId != "" && StringUtils.isNumeric(orgId)) {
            long id = Long.parseLong(userId);
            long organizationId = Long.parseLong(orgId);
            logger.debug("Adding groups {} to user {} in organization {}", new Object[] {groupIds, userId, organizationId});

            try {
                userService.saveUserOrganizationsGroups(groupIds, id, organizationId);
            } catch (IllegalArgumentException e) {
                logger.error("Call to save a list of UserOrganizationsGroups resulted in exception: {}", e.getMessage());
            } catch (ServiceException e) {
                logger.error("Call to save a list of UserOrganizationsGroups resulted in exception: {}", e.getMessage());
            }
        }

        logger.trace("Leaving the addRoleToUser method.");
        return true;
    }

    /**
     * At this time the roles for the users are not deactivated.
     * The user itself.
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @throws ServiceException ServiceException
     * @throws SendFailedException 
     */
    @RequestMapping(value = "rejectUserRequest.htm", method = RequestMethod.POST)
    public final void rejectUserRequest(final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, SendFailedException {
        logger.trace("Entering the rejectUserRequest method.");
        String userIdStr = request.getParameter("userId");
        if (StringUtils.isNumeric(userIdStr)) {
            long userId = Long.parseLong(userIdStr);
            User user = userService.get(userId);
            user.setActiveFlag(false);
            userService.update(user);
            try {
                emailService.sendRejectUserMsg(user);
            } catch (ServiceException e) {
                logger.error("Trying to send rejection email for {}," +
                        " threw Service Exception {}", user, e.getStackTrace());
            } catch (Exception ex) {
         	   logger.error ("Send Failed Exception"+ex.toString());
        	   } 
        } else {
            logger.debug("User groups id is not numeric" + userIdStr);
        }

        logger.trace("Leaving the rejectUserRequest method.");
    }

    /**
     * Gets all the roles assigned to a user within an certain organization context.
     * @param request {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     * @return Map<String, Object>
     */
    @RequestMapping(value = "getUsersRoles.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getUsersRoles(HttpServletRequest request, HttpServletResponse response) {
        logger.trace("Entering the getUsersRoles method.");
        Map<String, Object> hashMap = new HashMap<String, Object>();
        List<Organization> organizations = new ArrayList<Organization>();
        try {
            long userId = Long.parseLong(request.getParameter("userId"));
            long organizationId = Long.parseLong(request.getParameter("organizationId"));
            organizations.add(new Organization(organizationId));

            logger.debug("Retrieving roles for user with id {} in organization with id {}", userId, organizationId);
            List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService.
                    getByUserIdAndOrganization(userId, organizationId);
            List<Groups> groups = new ArrayList<Groups>();

            for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
                if (!groups.contains(userGroup.getGroup())) {
                    groups.add(userGroup.getGroup());
                }
            }

            logger.debug("Returning user's current groups {}", groups);
            hashMap.put("currentGroups", groups);

            /*
             * This method also returns all available groups to show what groups the user could be added to.
             */
            List<Groups> allGroups = groupsService.getByDefaultRoleAndOrganizationTree(false, organizations.get(0).getId());
            hashMap.put("allGroups", allGroups);

        } catch (NumberFormatException e) {
            logger.debug("Failed to parse userId parameter: {}", request.getParameter("userId"));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            hashMap.put("invalidParams", true);
        }

        return hashMap;
    }

    /**
     *
     * @param request {@link HttpServletRequest}
     * @return Map<String, List<UserOrganizationsGroups>>
     */
    @RequestMapping(value = "getNewActiveAndPendingUsers.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, List<UserOrganizationsGroups>> getNewActiveAndPendingUsers(
            final HttpServletRequest request) {
        logger.trace("Entering the getNewActiveAndPendingUsers method.");
        UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Organization> orgs = userService.getOrganizations(user.getUserId());
        long organizationId = orgs.get(0).getId();
        Map<String, List<UserOrganizationsGroups>> hashMap = new HashMap<String, List<UserOrganizationsGroups>>();

        if (organizationId > 0) {
            orgs = organizationService.getAllChildren(organizationId);
            orgs.add(organizationService.get(organizationId));

            logger.debug("Retrieve users for children of organizationid: {}", organizationId);

            //New users will not longer be in a default group.
            hashMap.put("newUsers", getDistinctUsersByStatus(orgs,  null));
            hashMap.put("activeUsers", getDistinctUsersByStatus(orgs,  UserStatus.ACTIVE));
            hashMap.put("pendingUsers", getDistinctUsersByStatus(orgs,  UserStatus.PENDING));
        }

        logger.trace("Leaving the getNewActiveAndPendingUsers method.");
        return hashMap;
    }

    /**
     *
     * @param request {@link HttpServletRequest}
     */
    @RequestMapping(value = "saveGroupPermissions.htm", method = RequestMethod.POST)
    public final @ResponseBody void saveGroupsPermissions(final HttpServletRequest request) {
        logger.trace("Entering the saveGroupsPermissions method.");
        String length = request.getParameter("length");

        if (StringUtils.isNumeric(length)) {
            int len = Integer.parseInt(length);
            for (int i = 0; i < len; i++) {
                String gId = request.getParameter("groupAuthorities[" + i + "][groupId]");
                String[] perms = request.getParameterValues("groupAuthorities[" + i + "][authorities][]");
                long groupId = 0;

                if (StringUtils.isNumeric(gId)) {
                    groupId = Long.parseLong(gId);

                    List<Long> authorities = new ArrayList<Long>();
                    if (perms != null && perms.length > 0) {
                        for (int j = 0, num = perms.length; j < num; j++) {
                            if (StringUtils.isNumeric(perms[j])) {
                                authorities.add(Long.parseLong(perms[j]));
                            }
                        }
                    }

                    //save permissions for a group.
                    logger.debug("Saving permissions for group with id {}", groupId);
                    // TODO Check whether it is in use?
                   // gaService.saveGroupAuthoritiesList(groupId, authorities);
                }
            }
        }

        logger.trace("Leaving the saveGroupsPermissions method.");
    }

    /**
     * This method resends a user their account activation email if requested by a system administrator.
     * @param request {@link HttpServletRequest}
     * @throws ServiceException ServiceException
     */
    @RequestMapping(value = "resendActivationEmail.htm", method = RequestMethod.POST)
    public final @ResponseBody void resendActivationEmails(final HttpServletRequest request) throws ServiceException {
        logger.trace("Entering the resendActivationEmails method.");
        String[] userOrganizationsGroupsIds = request.getParameterValues("ids[]");

        List<Long> userIds = new ArrayList<Long>();
        if (userOrganizationsGroupsIds != null) {
            for (String id : userOrganizationsGroupsIds) {
                if (StringUtils.isNumeric(id)) {
                   long userOrganizationsGroupsId = Long.parseLong(id);

                   UserOrganizationsGroups userGroup = userOrganizationsGroupsService.
                           getUserOrganizationsGroups(userOrganizationsGroupsId);

                   if (!userIds.contains(userGroup.getUserId())) {
                       /*
                        * Potentially a user could be in the pending section multiple times so we check to make sure that we only
                        * send them one email.
                        */
                       logger.debug("Re-sending Activation email for userGroup {}", userGroup);
                       emailService.resendUserActivationEmail(userGroup);
                       userIds.add(userGroup.getUserId());
                   }
                }
            }
        }

        logger.trace("Leaving the resendActivationEmails method.");
    }

    /**
     * Gets users for a set of organizations by their status.
     * @param orgs List<Organization>
     * @param status int
     * @return List<UserOrganizationsGroups>
     */
    private List<UserOrganizationsGroups> getDistinctUsersByStatus(List<Organization> orgs, Integer status) {
        logger.trace("Entering the getDistinctUsersByStatus method.");
        List<UserOrganizationsGroups> userOrganizationsGroups = new ArrayList<UserOrganizationsGroups>();
        List<UserOrganizationsGroups> distinct = new ArrayList<UserOrganizationsGroups>();
        Set<Long> organizationIds = new HashSet<Long>();
        boolean contains;

        if (orgs != null && CollectionUtils.isNotEmpty(orgs)) {
            for (Organization org : orgs) {
                if (org != null && org.getId() != null) {
                    organizationIds.add(org.getId());
                }
            }
        }

        //DE5429 - added because the size of this list can be greater than the max size of an in clause
        //for the postgres java driver - it uses Integer for the size of the clause
        //splitting up the calls in  chunks
        int orgIdsLength = organizationIds.size();
        if (orgIdsLength > inClauseLimit){
	        int numberOfCalls = orgIdsLength/inClauseLimit;
	        int remainder = orgIdsLength % inClauseLimit;
	        if (remainder > 0)
	        	numberOfCalls++;
	        List<Long> idList = new ArrayList<Long>();
	        idList.addAll(organizationIds);
	        HashSet<Long> tempIds = new HashSet<Long>();
	        for (int i = 0; i < numberOfCalls; i++){
	        	int from = i * inClauseLimit;
	        	int to =  ((i+1) * inClauseLimit)-1;
	        	if (i == numberOfCalls-1)
	        		to = orgIdsLength -1;
	        	tempIds.clear();
	        	tempIds.addAll(idList.subList(from, to));
	        	userOrganizationsGroups.addAll(userOrganizationsGroupsService.getByOrganizationAndStatus(
	                     tempIds, status));
	        }
        }else{
        	userOrganizationsGroups.addAll(userOrganizationsGroupsService.getByOrganizationAndStatus(
                organizationIds, status));
        }
        for (UserOrganizationsGroups ug : userOrganizationsGroups) {
            contains = false;
            for (UserOrganizationsGroups dist : distinct) {
                if (dist.getUserId() == ug.getUserId()
                        && dist.getGroup().getOrganizationId() == ug.getGroup().getOrganizationId()) {
                    contains = true;
                }
            }

            if (!contains) {
                distinct.add(ug);
            }
        }

        logger.debug("Returning users {} for status {}", distinct, status);
        logger.trace("Leaving the getDistinctUsersByStatus method.");
        return distinct;
    }


    /**
     * @param service the groupsService to set
     */
    public final void setGroupsService(GroupsService service) {
        this.groupsService = service;
    }

    /**
     * @param service the organizationService to set
     */
    public final void setOrganizationService(OrganizationService service) {
        this.organizationService = service;
    }

    @RequestMapping(value = "getUsersOrgsHierarchy.htm", method = RequestMethod.POST)
    public final @ResponseBody List<OrganizationDto> getUsersOrgsHierarchy(@RequestParam final long orgId) {
    	logger.debug("Entering the getUsersOrgsHierarchy method with orgId - " + orgId); 
    	List<OrganizationDto> organizationDtos = new ArrayList<OrganizationDto>();
    	UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 

    	if(orgId == 0){
    		List<Organization> userOrgs = userService.getOrganizations(user.getUserId()); 
    		if (userOrgs != null && userOrgs.size() > 0) {
    			OrganizationDto orgDto = new OrganizationDto();
    			Organization highestOrg = userOrgs.get(0);
    			orgDto.setData(highestOrg.getOrganizationName() + " - " + highestOrg.getDisplayIdentifier());

    			/*
    			 * This Metadata class contains specific information about where a node exists in the tree.
    			 */
    			Metadata metadata = new Metadata();
    			metadata.setId(highestOrg.getId());
    			orgDto.setMetadata(metadata);
    			orgDto.setRelatedOrganizationId(highestOrg);  
    			organizationDtos.add(orgDto);
    		}
    	}
    	else{ 
    		Organization parentOrg = new Organization();
    		parentOrg.setId(orgId);
    		List<Organization> parentOrgList = new ArrayList<Organization>();
    		parentOrgList.add(parentOrg);
    		List<Organization> immediateChildOrgs = organizationService.getImmediateChildren(parentOrgList); 
    		if (immediateChildOrgs != null && CollectionUtils.isNotEmpty(immediateChildOrgs)) {
    			//TODO Performance Improvement
    			//form the dto for the immediate children
    			for (Organization immediateChildOrg : immediateChildOrgs) {
    				OrganizationDto immediateChildOrgDto = new OrganizationDto();
    				immediateChildOrgDto.setData(immediateChildOrg
    						.getOrganizationName() + " - " + immediateChildOrg.getDisplayIdentifier());
    				Metadata metadata = new Metadata();
    				metadata.setId(immediateChildOrg.getId());
    				immediateChildOrgDto.setMetadata(metadata);
    				immediateChildOrgDto.setRelatedOrganizationId(immediateChildOrg);
    				organizationDtos.add(immediateChildOrgDto);
    			} 
    		}
    	}

    	logger.debug("Leaving the getUsersOrgsHierarchy method.");
    	return organizationDtos;
    }
    
    @RequestMapping(value = "getUsersOrgsHierarchyTree.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Organization> getUsersOrgsHierarchyTree() {
    	logger.debug("Entering the getUsersOrgsHierarchyTree method with orgId - ");  
    	UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();  
    	List<Organization> userOrgs = userService.getOrganizations(user.getUserId()); 
    	logger.debug("Leaving the getUsersOrgsHierarchyTree method.");
    	return userOrgs;
    }
    
	// Per US18825 & US18927
	@RequestMapping(value = "limitUsers.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean limitUsers(@RequestParam("groupCode") String groupCode,
			@RequestParam("enableFlag") String enableFlag,
			@RequestParam(value = "organizationIds[]", required = true) List<Long> organizationIdList,
			@RequestParam(value = "assessmentProgramIds[]", required = true) List<Long> assessmentProgramIds)
			throws Exception {
		if (organizationIdList == null && assessmentProgramIds == null) {
			throw new Exception("No Assesment Program ID and Organization Passed");
		}
		GroupRestrictions groupRestriction = new GroupRestrictions();
		for (Long orgId : organizationIdList) {
			for (Long apId : assessmentProgramIds) {
				groupRestriction.setOrganizationId(orgId);
				groupRestriction.setAssessmentProgramId(apId);
				groupRestriction.setGroupCode(groupCode);
				boolean exists = groupsService.isUsersLimitedToOnePerRoleExists(groupCode,
						groupRestriction.getOrganizationId(), groupRestriction.getAssessmentProgramId());
				if (enableFlag.equals("OFF")) {
					groupRestriction.setSingleUser(false);
					if (exists) {
						groupsService.updateUsersLimitPerRole(groupRestriction);
					} else {
						groupsService.insertUsersLimitPerRole(groupRestriction);
					}
				} else {
					groupRestriction.setSingleUser(true);
					if (exists) {
						groupsService.updateUsersLimitPerRole(groupRestriction);
					} else {
						groupsService.insertUsersLimitPerRole(groupRestriction);
					}
					groupsService.restrictUsersLimitPerRole(groupRestriction);
				}
			}
		}
		return true;
	}

}
