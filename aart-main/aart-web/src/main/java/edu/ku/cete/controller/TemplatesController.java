package edu.ku.cete.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.service.AppConfigurationService;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.UserAssessmentPrograms;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.model.validation.PasswordValidator;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/templates")
public class TemplatesController extends BaseController {

	/** Logger for this class and subclasses. */
	private final Log logger = LogFactory.getLog(getClass());

	/** UserService holder. */
	private UserService userService;
	
    @Value("${application.images.folderName}")
    private String currentEnvironment;
    
	@Autowired
	private AppConfigurationService appConfigurationService;

	@Autowired
	private UserOrganizationsGroupsService userOrganizationsGroupsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PasswordValidator passwordValidator;

	@Autowired
	private AssessmentProgramService assessmentProgramService;

	@Autowired
	private OrgAssessmentProgramService orgAssessmentProgramService;

	@Autowired 
	private UserHomeController userHomeController;

	@Autowired
	public TemplatesController(final UserService newUserService,
			final UserOrganizationsGroupsService newOrganizationsGroupsService) {
		
		this.userService = newUserService;
		this.userOrganizationsGroupsService = newOrganizationsGroupsService;
	}

	/**
	 * Displays the Profile page.
	 * 
	 * @param userId
	 *            {@link Long}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "profileView.htm")
	public final ModelAndView profileView() {
		logger.trace("Enterind the profileView() method.");
		UserDetailImpl userImp = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.get(userImp.getUserId());
		List<Organization> organizations = userService.getOrganizations(userImp.getUserId());

		//To display security agreement text based on current Environment
		List<AppConfiguration> securityText=appConfigurationService.selectSecurityAgreementText(currentEnvironment);
		
		List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService
				.getUserOrganizationsGroupsByUserId(userImp.getUserId());
		List<Groups> userGroups = userOrganizationsGroupsService.getGroupsByUserOrganization(user.getId());
		UserAssessmentPrograms userDefaultAssessmentProgram = userService.getUserDefaultAssessmentProgram(user.getId());

		Organization currentOrganization = userImp.getUser().getOrganization();
		List<OrgAssessmentProgram> orgAssessProgs = new ArrayList<OrgAssessmentProgram>();
		Set<AssessmentProgram> assessmentPrograms = new HashSet<AssessmentProgram>();
		if (currentOrganization != null) {
			if (currentOrganization.getOrganizationType().getTypeCode().equalsIgnoreCase("CONS")) {
				List<AssessmentProgram> aps = assessmentProgramService.getAllActive();
				for (AssessmentProgram ap : aps) {
					OrgAssessmentProgram oap = new OrgAssessmentProgram();
					oap.setAssessmentProgram(ap);
					orgAssessProgs.add(oap);
				}
			} else {
				long defaultOrgId = userImp.getUser().getDefaultOrganizationId();
				// TODO: This is a temporary fix - Please add the Permanant
				// Profile View Fix.
				if (defaultOrgId == 0) {
					defaultOrgId = currentOrganization.getId();
				}
				Long stateId = getStateIdForUserOrganization(defaultOrgId);
				// TODO:This can be changed to organizationtreedetail to make it
				// faster.
				orgAssessProgs = orgAssessmentProgramService.findByUserIdAndOrganizationId(user.getId(), stateId, defaultOrgId, user.getCurrentGroupsId());
				for (OrgAssessmentProgram oap : orgAssessProgs) {
					assessmentPrograms.add(oap.getAssessmentProgram());
				}

			}
		}

		List<String> readOnlyRoleList = new ArrayList<String>();
		Long defaultUserGroup = null;
		for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
			// set default group to true while pre-populating
			String readOnlyRole = userGroup.getGroup().getGroupName();
			if (userGroup.isIsdefault() && userGroup.isUserOrganizationDefault()) {
				defaultUserGroup = userGroup.getGroup().getGroupId();
			}
			for (Organization organization : organizations) {
				if (userGroup.getOrganizationId().equals(organization.getId())) {
					userGroup.setOrganization(organization);
					readOnlyRole = readOnlyRole + " - " + userGroup.getOrganization().getOrganizationName();
					readOnlyRoleList.add(readOnlyRole);
					break;
				}
			}
		}
		// TODO:This is been added as temporary Fix. -Need to Refactor the above
		// code.
		if (userDefaultAssessmentProgram != null) {
			defaultUserGroup = userDefaultAssessmentProgram.getGroupId();
		}
		Collections.sort(readOnlyRoleList);

		for (Groups group : userGroups) {
			if (group.getGroupId() == defaultUserGroup.longValue()) {
				group.setIsDefault(true);
			} else {
				group.setIsDefault(false);
			}
		}
		for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
			if (userGroup.getGroup().getGroupId() == defaultUserGroup.longValue()) {
				userGroup.getGroup().setIsDefault(true);
			}
		}
		Set<AssessmentProgram> assessmentProgramsList = new HashSet<AssessmentProgram>(
				assessmentProgramService.getAllAssessmentProgramByUserId(user.getId()));
		String assessmentProgramStr = "";
		boolean isFirstItr = false;
		for (AssessmentProgram assessmentProgram : assessmentProgramsList) {
			if (!isFirstItr) {
				assessmentProgramStr += assessmentProgram.getProgramName();
				isFirstItr = true;
			} else
				assessmentProgramStr += ", " + assessmentProgram.getProgramName();
		}

		if (userDefaultAssessmentProgram != null) {
			user.setDefaultAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
			user.setCurrentAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
			AssessmentProgram assessmentProgram=assessmentProgramService.findByAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
			user.setCurrentAssessmentProgramName(assessmentProgram.getAbbreviatedname());
			user.setCurrentGroupsId(userDefaultAssessmentProgram.getGroupId());
			user.setCurrentOrganizationId(userDefaultAssessmentProgram.getOrganizationId());
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("templates/profileView");
		mav.addObject("user", user);
		mav.addObject("organizations", organizations);
		mav.addObject("userOrganizationsGroups", userOrganizationsGroups);
		mav.addObject("userAssessmentPrograms", assessmentProgramStr);
		mav.addObject("orgAssessProgs", orgAssessProgs);
		mav.addObject("userGroups", userGroups);
		mav.addObject("readOnlyRoleList", readOnlyRoleList);
		mav.addObject("assessmentPrograms", assessmentPrograms);
		mav.addObject("securityText",securityText);
		logger.trace("Leaving the profileView() method");
		return mav;
	}

	/**
	 * Changed for US-14985
	 */
	@RequestMapping(value = "changepassword.htm", method = RequestMethod.POST)
	public final @ResponseBody String changePassword(final HttpServletRequest request,
			final HttpServletResponse response) throws JSONException {
		logger.trace("Entering the changePassword method.");
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String chkPasswordmsg = checkPassword(currentPassword);
		if (chkPasswordmsg.equals("true")) {
			String msg = updatePassword(newPassword);
			if (msg.equals("true"))
				return "{\"success\":\"true\"}";
			else
				return msg;
		} else
			return "{\"error\":\"Password Not updated. Check Current Password\"}";
	}

	/**
	 * Changed for US-14985
	 */
	private String checkPassword(String password) {
		UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String encodedPassword = passwordEncoder.encodePassword(password, user.getSalt());
		String savedPassword = user.getPassword();
		if (encodedPassword.equals(savedPassword))
			return "true";
		return "false";
	}

	private String updatePassword(String password) throws JSONException {
		UserDetailImpl userImp = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String msg = "";
		try {
			User user = userImp.getUser();
			user.setPassword(password);
			passwordValidator.validate(user);
			userService.updateWithEncodedPassword(userImp.getUser(), false, null);

		} catch (ServiceException e) {
			// logger.warn("updatePassword unknown error ", e);
			msg = e.getMessage();
			return msg;
		}
		return "true";
	}

	@RequestMapping(value = "editdisplayname.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean editDisplayName(final HttpServletRequest request,
			final HttpServletResponse response) {
		logger.trace("Entering the changePassword method.");
		String displayName = request.getParameter("displayName");

		String beforeUpdate = null;
		String afterUpdate = null;
		UserDetailImpl userImp = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// US17687 - User audit history
		User user = new User();
		user.setId(userImp.getUser().getId());
		user.setDisplayName(userImp.getUser().getDisplayName());
		beforeUpdate = user.buildJsonString();
		user.setDisplayName(displayName);
		afterUpdate = user.buildJsonString();
		userService.insertIntoDomainAuditHistory(user.getId(), user.getId(),
				EventTypeEnum.DISPLAY_NAME_CHANGED.getCode(), SourceTypeEnum.MANUAL.getCode(), beforeUpdate,
				afterUpdate);

		userImp.getUser().setDisplayName(displayName);
		try {
			userService.update(userImp.getUser());
		} catch (ServiceException e) {
			logger.error("editDisplayName unknown error ", e);
			return false;
		}
		return true;
	}

	@RequestMapping(value = "changeDefaultOrgAndRole.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean changeDefaultOrgAndRole(@RequestParam("groupId") Long groupId,
			@RequestParam("organizationId") Long organizationId,
			@RequestParam("assessmentProgramId") Long assessmentProgramId) {
		logger.trace("Entering the changeDefaultOrgAndRole method.");
		try {
			String beforeUpdate = null;
			String afterUpdate = null;

			UserDetailImpl userImp = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// US17687 - User audit history
			User user = new User();
			user.setId(userImp.getUser().getId());
			user.setDefaultOrganizationId(userImp.getUser().getDefaultOrganizationId());
			user.setDefaultUserGroupsId(userImp.getUser().getDefaultUserGroupsId());
			beforeUpdate = user.buildJsonString();
			List<UserOrganizationsGroups> userOrgGroups = userOrganizationsGroupsService
					.getByUserIdAndOrganization(user.getId(), organizationId);
			// update correct userorggroup
			Long userOrganizationGroupId = null;
			Long userOrganizationId = null;

			for (UserOrganizationsGroups userOrganizationsGroup : userOrgGroups) {
				if (userOrganizationsGroup.getGroupId().longValue() == groupId.longValue()) {
					userOrganizationGroupId = userOrganizationsGroup.getId();
					userOrganizationId = userOrganizationsGroup.getUserOrganizationId();
				}
			}

			userService.updateDefaultOrgAndRole(userImp.getUserId(), userOrganizationId, userOrganizationGroupId);

			UserOrganizationsGroups userGroup = userOrganizationsGroupsService
					.getUserOrganizationsGroups(userOrganizationGroupId);
			user.setDefaultOrganizationId(userGroup.getOrganizationId());
			user.setDefaultUserGroupsId(userGroup.getGroupId());

			// Set default assessment program id
			user.setAssessmentProgramId(assessmentProgramId);
			// update the default assessment program for user
			userService.updateUserAssessmentProgramToDefault(user, organizationId, groupId, true, userOrganizationGroupId);

			afterUpdate = user.buildJsonString();
			userService.insertIntoDomainAuditHistory(user.getId(), user.getId(),
					EventTypeEnum.DEFAULT_ROLE_CHANGED.getCode(), SourceTypeEnum.MANUAL.getCode(), beforeUpdate,
					afterUpdate);
		} catch (Exception e) {
			logger.error("changeDefaultOrgAndRole unknown error ", e);
			return false;
		}
		return true;
	}

	@RequestMapping(value = "changeDefaultAssessmentProgram.htm", method = RequestMethod.POST)
	public final @ResponseBody Set<AssessmentProgram> changeDefaultAssessmentProgram(
			@RequestParam("organizationId") Long organizationId) throws Exception {
		UserDetailImpl userImp = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Organization organization = organizationService.get(organizationId);
		//Organization currentOrganization = userImp.getUser().getOrganization();
		List<OrgAssessmentProgram> orgAssessProgs = new ArrayList<OrgAssessmentProgram>();
//		if (currentOrganization != null) {
//			if (currentOrganization.getOrganizationType().getTypeCode().equalsIgnoreCase("CONS")) {
//				List<AssessmentProgram> aps = assessmentProgramService.getAllActive();
//				for (AssessmentProgram ap : aps) {
//					OrgAssessmentProgram oap = new OrgAssessmentProgram();
//					oap.setAssessmentProgram(ap);
//					orgAssessProgs.add(oap);
//				}
//			} else {
				if (organization.getOrganizationType().getTypeCode().equals("CONS")) {
					List<AssessmentProgram> aps = assessmentProgramService.getAllActive();
					for (AssessmentProgram ap : aps) {
						OrgAssessmentProgram oap = new OrgAssessmentProgram();
						oap.setAssessmentProgram(ap);
						orgAssessProgs.add(oap);
					}
				} else {
					Long stateId = getStateIdForUserOrganization(organizationId);
					orgAssessProgs = orgAssessmentProgramService
					.findByUserIdAndOrganizationId(userImp.getUser().getId(), stateId, organizationId,
							userImp.getUser().getCurrentGroupsId());
				}

			//}
		//}

		Set<AssessmentProgram> assessmentPrograms = new HashSet<AssessmentProgram>();
		for (OrgAssessmentProgram orgAssessmentProgram : orgAssessProgs) {
			// Seed isDefault from user's choice to assessment program
			// returning.
			orgAssessmentProgram.getAssessmentProgram().setIsDefault(orgAssessmentProgram.getIsDefault());
			assessmentPrograms.add(orgAssessmentProgram.getAssessmentProgram());
		}
		userImp.getUser().setUserAssessmentPrograms(new ArrayList<>(assessmentPrograms));
		return assessmentPrograms;

	}

	@RequestMapping(value = "getUserOrgAssessmentProgram.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgram> getUserOrgAssessmentProgram(
			@RequestParam("organizationId") Long organizationId) throws Exception {
		List<AssessmentProgram> assessmentPrograms = new ArrayList<AssessmentProgram>();
		if (organizationId != null) {
			UserDetailImpl userImp = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Organization currentOrganization = userImp.getUser().getOrganization();
			List<OrgAssessmentProgram> orgAssessProgs = new ArrayList<OrgAssessmentProgram>();
			if (currentOrganization != null) {
				if (currentOrganization.getOrganizationType().getTypeCode().equalsIgnoreCase("CONS")) {
					List<AssessmentProgram> aps = assessmentProgramService.getAllActive();
					for (AssessmentProgram ap : aps) {
						OrgAssessmentProgram oap = new OrgAssessmentProgram();
						oap.setAssessmentProgram(ap);
						orgAssessProgs.add(oap);
					}
				} else {
					Long stateId = getStateIdForUserOrganization(organizationId);
					orgAssessProgs = orgAssessmentProgramService.findByUserIdAndOrganizationId(
							userImp.getUser().getId(), stateId, organizationId, userImp.getUser().getCurrentGroupsId());
				}
			}

			for (OrgAssessmentProgram orgAssessmentProgram : orgAssessProgs) {
				// Seed isDefault from user's choice to assessment program
				// returning.
				orgAssessmentProgram.getAssessmentProgram().setIsDefault(orgAssessmentProgram.getIsDefault());
				assessmentPrograms.add(orgAssessmentProgram.getAssessmentProgram());
			}
		}
		return assessmentPrograms;
	}

	@RequestMapping(value = "getStatesAllowedForUser.htm", method = RequestMethod.POST)
	public final @ResponseBody String getStatesAllowedForUser() throws Exception {

		List<Organization> organizationsStates = new ArrayList<Organization>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long userId = user.getId();
		Long orgId = user.getOrganization().getId();
		List<Organization> states = null;

		if (user.getOrganization().getOrganizationType().getTypeCode()
				.equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)) {
			states = organizationService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
		} else if (user.getOrganization().getOrganizationType().getTypeCode()
				.equals(CommonConstants.ORGANIZATION_STATE_CODE)) {
			// Hack for DE5725 - Org structure for DLM and ARMM are different
			if (user.getOrganization().getDisplayIdentifier().equals("DLM")
					|| user.getOrganization().getDisplayIdentifier().equals("ARMM")) {
				states = organizationService.getAllChildrenByOrgTypeCode(orgId,
						CommonConstants.ORGANIZATION_REGION_CODE);
			} else {
				states = organizationService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, userId);
			}
		}

		// Hack for DE5725 - Org structure for DLM and ARMM are different
		else if (user.getOrganization().getOrganizationType().getTypeCode()
				.equals(CommonConstants.ORGANIZATION_REGION_CODE)) {
			states = organizationService.getByTypeAndUserId(CommonConstants.ORGANIZATION_REGION_CODE, userId);
		}

		if (states != null) {
			for (Organization organizationsState : states) {
				if (organizationsState.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
					organizationsStates.add(organizationsState);
				}
			}
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("States", organizationsStates);

		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no
																									// more
																									// null-valued
																									// properties
		String modelJson = mapper.writeValueAsString(model);
		return modelJson;
	}

	private Long getStateIdForUserOrganization(Long organizationId) {
		List<Organization> parents = organizationService.getAllParents(organizationId);
		Long stateId = null;
		// in the case the user is a state level user add the state
		parents.add(organizationService.get(organizationId));
		for (Organization org : parents) {
			if (org.getOrganizationType().getTypeCode().equals("ST")) {
				stateId = org.getId();
				break;
			}
		}
		return stateId;
	}

	@RequestMapping(value = "changeDefaultOrganization.htm", method = RequestMethod.POST)
	public final @ResponseBody List<Organization> changeDefaultOrganization(@RequestParam("groupId") Long groupId)
			throws Exception {
		UserDetailImpl userImp = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userImp.getUser().setCurrentGroupsId(groupId);
		List<Organization> organizations = userService.getOrganizations(userImp.getUserId());
		List<Organization> groupOrganizations = new ArrayList<Organization>();
		List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService
				.getUserOrganizationsGroupsByUserId(userImp.getUserId());
		for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
			for (Organization organization : organizations) {
				if (userGroup.getOrganizationId().equals(organization.getId())
						&& userGroup.getGroupId().equals(groupId)) {
					groupOrganizations.add(organization);
					break;
				}
			}
		}
		return groupOrganizations;
	}
    
	@RequestMapping(value="dashboardAccess.htm", method = RequestMethod.GET)
	public final String dashboardAccess(final HttpServletRequest request,	final HttpServletResponse response){
	  	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		if (user.getCurrentAssessmentProgramName().equals("DLM")){
			List<String> apCodes = new ArrayList<String>(user.getAssessmentProgramCodes());
			apCodes.remove("DLM");
			Long orgId = null;
			Long groupId = null;
			String apCode = null;
			if (apCodes.size() > 0){
				List<String> reorderedAPCodes = new ArrayList<String>();
				if (apCodes.contains("KAP")){
					reorderedAPCodes.add("KAP");
				}
				if (apCodes.contains("KELPA2")){
					reorderedAPCodes.add("KELPA2");
				}
				if (apCodes.contains("CPASS")){
					reorderedAPCodes.add("CPASS");
				}
				boolean foundPermission = false;
				for (String code : reorderedAPCodes){
					for (UserOrganizationsGroups uog : user.getUserOrganizationsGroups()){
						List<Organization> parents = organizationService.getAllParents(uog.getOrganizationId());
				    	Long stateId = null;
				    	Organization defaultOrg = organizationService.get(uog.getOrganizationId());
						if(defaultOrg != null){
							parents.add(defaultOrg);
						}
						for (Organization org : parents){
							if (org.getOrganizationType() != null && org.getOrganizationType().getTypeCode() != null && org.getOrganizationType().getTypeCode().equals("ST")){
								stateId = org.getId();
								break;
							}
						}
						AssessmentProgram ap = assessmentProgramService.findByAbbreviatedName(code);
						List<Authorities> authorities = userService.getByUserAndGroupExcludeLockdown(user.getId(), uog.getGroupId(), uog.getOrganizationId(), stateId, ap.getId());
						
						for (Authorities auth : authorities){
							if ("VIEW_DASHBOARD_MENU".equals(auth.getAuthority())){
								foundPermission = true;
								break;
							}
						}
						if (foundPermission){
							orgId = uog.getOrganizationId();
							groupId = uog.getGroupId();
							break;
						}
					}
					if (foundPermission){
						apCode = code;
						break;
					}
				}
				if (apCode != null && orgId != null && groupId != null){
					try {
						List<Organization> orgs = changeDefaultOrganization(groupId);
						Organization defaultOrg = null;
						for (Organization org : orgs){
							if (org.getId().equals(orgId)){
								defaultOrg = org;
								break;
							}
						}
						if (defaultOrg != null){
							Set<AssessmentProgram> assessmentPrograms = changeDefaultAssessmentProgram(defaultOrg.getId());
							AssessmentProgram assessProg = null;
							for (AssessmentProgram assessmentProgram : assessmentPrograms){
								if (assessmentProgram.getAbbreviatedname().equals(apCode)){
									assessProg = assessmentProgram;
									break;
								}
							}
							if (assessProg != null){
							userHomeController.switchRole(null, defaultOrg.getId(), groupId, defaultOrg.getOrganizationType().getTypeCode(), assessProg.getId(), request);
								//In switchRole, 
								//the last parameter/argument (empty string) is part of obtaining the URL a user is on when he toggles role/organization/assessment.
							}
						}
						
					} catch (Exception e) {
						logger.info("Error while switching group, organization, and assessment program for user: "+user.getId(),e);
					}
				}
			}
		}
		HttpSession session = request.getSession();
		session.setAttribute("user", user);        
		return "redirect:/dashboard.htm";
	}
}
