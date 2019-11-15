package edu.ku.cete.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.security.AlternateOIDCAuthenticationToken;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.PolicyService;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.ReportService;
import edu.ku.cete.util.AuthorityUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.domain.user.User;
/**
 * User Home Controller.
 * 
 * @author nicholas
 */
@Controller
@RequestMapping("/userHome")
public class UserHomeController extends BaseController {

	/** Logger for this class and subclasses. */
	private final Log logger = LogFactory.getLog(getClass());

	/** UserService holder. */
	private UserService userService;

	@Autowired
	private UserOrganizationsGroupsService userOrganizationsGroupsService;

	/** PolicyService holder. */
	private PolicyService policyService;

    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private PermissionUtil permissionUtil;
    
    @Autowired
	private ReportService reportService;
    
	/**
	 * Get the Service wired.
	 * 
	 * @param newUserService
	 *            {@link UserService}
	 * @param newPolicyService
	 *            {@link PolicyService}
	 */
	@Autowired
	public UserHomeController(final UserService newUserService,
			final PolicyService newPolicyService) {
		this.userService = newUserService;
		this.policyService = newPolicyService;
	}
	
	
    @Value("${application.images.folderName}")
    private String imageFolderName;
	
	/**
	 * This method is called by the Get request an results in the jsp being
	 * displayed.
	 * 
	 * @param model
	 *            {@link Model}
	 */
	@RequestMapping(method = RequestMethod.GET)
	public final void setupForm(final Model model,
			final HttpServletRequest request) {

		checkDLMSpecificFilesPerm(request);
		logger.debug("SetupForm Start");
		model.addAttribute("current", "userHome");	
	}
	
	@RequestMapping(value = "switchRole.htm", method = RequestMethod.POST)
	@ResponseBody
	public final String switchRole(final Model model,
			@RequestParam("organizationId") final Long organizationId,
			@RequestParam("groupId") final Long groupId,
			@RequestParam("currentOrganizationType") final String currentOranizationType,
			@RequestParam("userAssessmentProgramId") final Long userAssessmentProgramId,
			final HttpServletRequest request){
		// Get the security context
		SecurityContext context = SecurityContextHolder.getContext();
		
 		
		// Get the current Authentication object
		if (context != null) {
			Authentication auth = context.getAuthentication();
			if (auth != null) {
				UserDetailImpl user = (UserDetailImpl) auth.getPrincipal();
				user.getUser().setCurrentOrganizationId(organizationId);
				user.getUser().setCurrentOrganizationType(currentOranizationType);
				user.getUser().setCurrentGroupsId(groupId);
				user.getUser().setCurrentAssessmentProgramId(userAssessmentProgramId);
				AssessmentProgram assessmentProgram=assessmentProgramService.findByAssessmentProgramId(userAssessmentProgramId);
				user.getUser().setCurrentAssessmentProgramName(assessmentProgram.getAbbreviatedname());
				user.getUser().resetAuthorities();
				
				
				Organization contractingOrg = organizationService.getContractingOrganization(organizationId);
				if(contractingOrg != null) {
					user.getUser().setContractingOrgDisplayIdentifier(contractingOrg.getDisplayIdentifier());
					user.getUser().setContractingOrgId(contractingOrg.getId());	
					user.getUser().setContractingOrganization(contractingOrg);
				}
				
				List<Organization> parents = organizationService.getAllParents(organizationId);
		    	Long stateId = null;
				//in the case the user is a state level user add the state
		    	Organization defaultOrg=organizationService.get(organizationId);
				if(defaultOrg!=null){
					parents.add(defaultOrg);
				}
				for (Organization org : parents) {
					if (org.getOrganizationType() != null
							&& StringUtils.isNotBlank(org.getOrganizationType().getTypeCode())
							&& org.getOrganizationType().getTypeCode().equals("ST")) {
						stateId = org.getId();
						break;
					}
				}
				
				List<Authorities> authorities = null;
				// For users with GSAD role need to be handled when they switch role.
				if(user.getUser().getCurrentOrganizationType().equals("CONS")){
					authorities = userService.getGroupExcludeLockdownForGlobalAdmin(
							user.getUser().getId(), user.getUser().getCurrentGroupsId(), user.getUser().getCurrentOrganizationId());
					user.getUser().setAuthoritiesList(authorities);
				} else {
					authorities = userService.getByUserAndGroupExcludeLockdown(
							user.getUser().getId(), groupId, organizationId, stateId, userAssessmentProgramId);
					user.getUser().setAuthoritiesList(authorities);
				}
				user.getUser().setAuthoritiesList(authorities);
				Authentication newAuth = null;
				List<GrantedAuthority> grantedAuthorities = AuthorityUtil.mapAuthorities(authorities);
				if (user.isSSO()) {
					AlternateOIDCAuthenticationToken oldAuth = (AlternateOIDCAuthenticationToken) auth;
					newAuth = new AlternateOIDCAuthenticationToken(oldAuth.getSub(),
							oldAuth.getIssuer(),
							oldAuth.getUserInfo(), grantedAuthorities,
							oldAuth.getIdToken(), oldAuth.getAccessTokenValue(), oldAuth.getRefreshTokenValue(), user);
				} else {
					newAuth = new UsernamePasswordAuthenticationToken(
							auth.getPrincipal(), auth.getCredentials(), grantedAuthorities);
				}

				context.setAuthentication(newAuth);
				
				//Long userOrgId = userDetails.getUser().getOrganization().getId();
				List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(organizationId);
				childrenOrgIds.add(organizationId);
				user.getUser().setAllChilAndSelfOrgIds(childrenOrgIds);
				userService.setITIStatusForCurrentAssementProgram(user.getUser());
				
				checkDLMSpecificFilesPerm(request);				
			}
		}

		return "{\"success\": true}";
	}

	/**
	 * This method is called by the Get request an results in the jsp being
	 * displayed.
	 * 
	 * @param model
	 *            {@link Model}
	 * @param cleanUserTestId
	 *            userTestId to clean up.
	 */
	@RequestMapping(method = RequestMethod.GET, params = { "cleanUserTestId" })
	public final void setupForm(final Model model,
			@RequestParam final Long cleanUserTestId) {
		logger.debug("SetupForm Start (with param)");
		model.addAttribute("cleanUserTestId", cleanUserTestId);
		// model.addAttribute("policies", getOrgaizationPolicies());
	}
	
	/**
	 * This method is called by the Get request an results in the jsp being
	 * displayed.
	 * 
	 * @param model
	 *            {@link Model}
	 * @param currentURL
	 *            URL to switch.
	 */
	@RequestMapping(method = RequestMethod.GET, params = { "previousURL" })
	public final void setupForm(final Model model,
			@RequestParam final String previousURL) {
		logger.debug("SetupForm Start (with param)");
		model.addAttribute("previousURL", previousURL);
		model.addAttribute("current", "userHome");		
	}

	/**
	 * The ModelAndView does what?
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @return ModelAndView {@link ModelAndView}
	 * @throws ServletException
	 *             {@link ServletException}
	 * @throws IOException
	 *             {@link IOException}
	 */
	public final ModelAndView handleRequest(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {

		String now = (new Date()).toString();
		logger.debug("Returning hello view with " + now);

		Map<String, Object> myModel = new HashMap<String, Object>();
		myModel.put("now", now);

		return new ModelAndView("userHome", "model", myModel);
	}
	
	
	public void checkDLMSpecificFilesPerm(final HttpServletRequest request) {

		SecurityContext context = SecurityContextHolder.getContext();
		// Get the current Authentication object
		if (context != null) {
			Authentication auth = context.getAuthentication();
			if (auth != null) {
				UserDetailImpl user = (UserDetailImpl) auth.getPrincipal();		
				Boolean hasPermCustomFile = false;
				if(user.getUser().getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_DLM)) {
					List<Long> groupAccessCodes = reportService.getDlmExtractAccessId(user.getUser().getCurrentGroupsId(), 
							user.getUser().getCurrentOrganizationId(), user.getUser().getCurrentAssessmentProgramId());
					if (permissionUtil.hasPermission(user.getAuthorities(), DataReportTypeEnum.DLM_CUSTOM_FILES.getPermissionCode())) {   				
		   				if(groupAccessCodes.contains((long)DataReportTypeEnum.DLM_CUSTOM_FILES.getId())) {
		   					hasPermCustomFile = true;
		   				}
					}  					
				} 				
				HttpSession session = request.getSession();
				session.setAttribute("hasPermCustomFile", hasPermCustomFile); 
		
			}
		}
		
	}
}
