package edu.ku.cete.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.Tag;
import edu.ku.cete.domain.UserModule;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.professionaldevelopment.Activity;
import edu.ku.cete.domain.professionaldevelopment.Module;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.professionaldevelopment.ModuleState;
import edu.ku.cete.domain.professionaldevelopment.UserTest;
import edu.ku.cete.domain.professionaldevelopment.UserTestResponse;
import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTask;
import edu.ku.cete.domain.professionaldevelopment.UserTestSectionTaskFoil;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ProfessionalDevelopmentService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.TagService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.json.ProfDevModuleJsonConverter;
import edu.ku.cete.util.json.RecordBrowserJsonUtil;
import edu.ku.cete.web.AssessmentProgramDto;

/**
 * Controller class for professionaldevelopment that handles requests from 
 * different tabs like modules, treanscript, admin etc.
 *  
 * @author vittaly
 * @deprecated dead code
 */

@Controller
public class ProfessionalDevelopmentController {

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ProfessionalDevelopmentController.class);
	
	/**
	 * TEST_SESSION_JSP
	 */
	private static final String PROFESSIONAL_DEVELOPMENT_JSP = "/professionaldevelopment/professionalDevelopment";	
															   

    /**	
	 * organizationService
     */
    @Autowired
	private OrganizationService organizationService;
    
    /**
     * orgAssessProgService
     */
    @Autowired
    private OrgAssessmentProgramService orgAssessProgService;
    
	/**
	 *professionalDevelopmentService 
	 */
	@Autowired
	private ProfessionalDevelopmentService professionalDevelopmentService;
	
	/**
	 *TestService 
	 */
	@Autowired
	private TestService testService;
	
    /**	
	 * AssessmentProgramDao
     */
    @Autowired
	private AssessmentProgramDao assessmentProgramDao;
    
    /**	
	 * CategoryDao
     */
    @Autowired
	private CategoryDao categoryDao;
	 
    /**
     * professionaldevelopment.testingprogram.code
     */
    @Value("${professionaldevelopment.testingprogram.code}")
    private String testingProgramCode;
    
    /**
	 * recordBrowserJsonUtil
	 */
    @Autowired
    private RecordBrowserJsonUtil recordBrowserJsonUtil;
    
    /**
     * categoryService
     */
    @Autowired
    private CategoryService categoryService;
    
    /**
     * groupsService
     */
    @Autowired
    private GroupsService groupsService;
    
    @Autowired
    private TagService tagService;
    
    @Autowired
    private PermissionUtil permissionUtil;     
    
	@Autowired(required = true)
	private ProfessionalDevelopmentReportProcessor pdReportProcessor;
    
    @Value("${professionaldevelopment.module.enrollment.code}")
    private String USER_MODULE_ENROLLMENT_CATEGORY_CODE;
    
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "professionalDevelopment.htm")
	public final ModelAndView view(final HttpServletRequest request) {
		LOGGER.trace("Entering the view() method");
		
		ModelAndView mav = new ModelAndView(PROFESSIONAL_DEVELOPMENT_JSP);
		List <Activity> activities = null;
		List <Organization> organizations = null;
		String userName = null;
		String state = null;
		String district = null;
		String school = null;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();	
		Organization organization = user.getOrganization();		
		
		userName = user.getFirstName() + " " + user.getSurName();

		activities = professionalDevelopmentService.getActivitiesByUserID(user);
		List<Test> tests = testService.findProfessionalDevelopmentTests(testingProgramCode, organization.getId());
		List<Test> tutorials = testService.findProfessionalDevelopmentTutorials(testingProgramCode, organization.getId());
		organizations = organizationService.getAllParents(user.getOrganizationId());
		organizations.add(user.getOrganization());		

		for(Organization org: organizations) {
			if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
				state = org.getDisplayIdentifier();
			} else if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_5) {
				district = org.getDisplayIdentifier();
			} else if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_7) {
				school = org.getDisplayIdentifier();
			}							
		}
		
		mav.addObject("assessmentPrograms", getAssessmentProgramsForUser(user));
		mav.addObject("roles", groupsService.getAllGroups());
		mav.addObject("tests", tests);
		mav.addObject("tutorials", tutorials);
		mav.addObject("activities", activities);
		mav.addObject("name", userName);
		mav.addObject("state", state);
		mav.addObject("district", district);
		mav.addObject("school", school);
		mav.addObject("studentid", user.getUniqueCommonIdentifier());
		
		LOGGER.trace("Leaving the view() method");
		return mav;
	}

	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getModules.htm", method = RequestMethod.GET)
	public final @ResponseBody  JsonResultSet getModules(@RequestParam("rows") String limitCountStr,
	  		@RequestParam("page") String page,
	  		@RequestParam("sidx") String sortByColumn,
	  		@RequestParam("sord") String sortType) {
		LOGGER.trace("Entering the getModules() method");
	    int currentPage = NumericUtil.parse(page, 1);
	    int limitCount = NumericUtil.parse(limitCountStr,5);
	    JsonResultSet jsonResultSet = null;
	    int totalCount = professionalDevelopmentService.countModulesForAdmin();
		List<Module> modules = professionalDevelopmentService.getModulesForAdmin(sortByColumn, sortType, (currentPage-1)*limitCount, limitCount);
		
		int totalPages = 0;
		if (totalCount > 0 ){
			totalPages = new Double(Math.ceil(totalCount/limitCount)).intValue();
		}
		if (totalPages == 0){
			totalPages = 1;
		}
		jsonResultSet = ProfDevModuleJsonConverter.convertToModuleJsonForAdmin(modules, totalCount, currentPage, limitCount);
		LOGGER.trace("Leaving the getModules() method");
		return jsonResultSet;
		
	}
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getOrgModules.htm", method = RequestMethod.GET)
	public final @ResponseBody  JsonResultSet getOrgModules(
			@RequestParam("rows") String limitCountStr,
	  		@RequestParam("page") String page,
	  		@RequestParam("sidx") String sortByColumn,
	  		@RequestParam("sord") String sortType) {
		LOGGER.trace("Entering the getOrgModules() method");
	    int currentPage = NumericUtil.parse(page, 1);
	    int limitCount = NumericUtil.parse(limitCountStr,5);
	    JsonResultSet jsonResultSet = null;
	    UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();		

	    int totalCount = professionalDevelopmentService.countModulesForStateAdmin(user);
		List<Module> modules = professionalDevelopmentService.getModulesForStateAdmin(user, sortByColumn, sortType, (currentPage-1)*limitCount, limitCount);
		
		int totalPages = 0;
		if (totalCount > 0 ){
			totalPages = new Double(Math.ceil(totalCount/limitCount)).intValue();
		}
		if (totalPages == 0){
			totalPages = 1;
		}
		jsonResultSet = ProfDevModuleJsonConverter.convertToModuleJsonForAdmin(modules, totalCount, currentPage, limitCount);
		LOGGER.trace("Leaving the getModules() method");
		return jsonResultSet;
		
	}
	/**
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @return
	 */
	@RequestMapping(value = "browseModules.htm", method = RequestMethod.GET)
	public final @ResponseBody  JQGridJSONModel browseModules(
			@RequestParam("rows") String limitCountStr,
	  		@RequestParam("page") String page,
	  		@RequestParam("sidx") String sortByColumn,
	  		@RequestParam("sord") String sortType,
	  		@RequestParam("filters") String filters,
	  		@RequestParam(value="userModulesOnly", required = false) boolean userModulesOnly) {
		LOGGER.trace("Entering the browseModules() method");
		
	    Integer currentPage;
	    Integer limitCount;
	    JQGridJSONModel  jqGridJSONModel  = null;
	    int totalCount = 0;

	    currentPage = NumericUtil.parse(page, 1);
	    limitCount = NumericUtil.parse(limitCountStr,5);

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();

		Map<String,String> testsAndTestCollectionsCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(filters);
		
		List<Category> enrollmentCategory= categoryService.selectByCategoryType(USER_MODULE_ENROLLMENT_CATEGORY_CODE);
		Map<Long, Category> enrollmentCategoryMap = new HashMap<Long, Category>();
		for(Category category :enrollmentCategory) {
			enrollmentCategoryMap.put(category.getId(), category);
			if(category.getCategoryCode().equalsIgnoreCase(CommonConstants.PD_USER_MODULE_UNENROLLED_CATEGORY_CODE) &&
					testsAndTestCollectionsCriteriaMap.containsKey(CommonConstants.PD_USER_MODULE_ENROLLMENT_STATUS_CATEGORY_NAME) && 
					testsAndTestCollectionsCriteriaMap.containsValue(CommonConstants.PERCENTILE_DELIM + category.getId() + CommonConstants.PERCENTILE_DELIM)) {
				testsAndTestCollectionsCriteriaMap.put("unenrolledStatus", "null");
			}
		}
		
		Organization organization = user.getOrganization();
		List<Module> modules = professionalDevelopmentService.getModules(organization.getId(), testsAndTestCollectionsCriteriaMap,
				sortByColumn, sortType,
				(currentPage-1)*limitCount, limitCount, user.getId(), userModulesOnly);
		totalCount = professionalDevelopmentService.countModules(organization.getId(), testsAndTestCollectionsCriteriaMap, user.getId(), userModulesOnly);
		
		jqGridJSONModel = ProfDevModuleJsonConverter.convertToModuleJson(modules, totalCount, currentPage, limitCount, enrollmentCategoryMap);
		
		LOGGER.trace("Leaving the browseModules() method");
		return jqGridJSONModel;
	}
	
	/**
	 * @param fileterAttribute
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "getPDBrowseModulesAutoCompleteData.htm", method = RequestMethod.GET)
	public final @ResponseBody Set<String> getPDBrowseModulesAutoCompleteData(
			@RequestParam("fileterAttribute") String fileterAttribute,
			@RequestParam("term") String  term,
	  		@RequestParam(value="userModulesOnly", required = false) boolean userModulesOnly) {
		LOGGER.trace("Entering the getPDBrowseModulesAutoCompleteData() method");
		
		Set<String> autoCompleteValues = new HashSet<String>();
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Organization organization = user.getOrganization();
		Map<String,String> modulesCollectionsCriteriaMap = recordBrowserJsonUtil.constructAutoCompleteFilterCriteria(fileterAttribute, term);
		
		List<Module> modules = professionalDevelopmentService.getModules(organization.getId(), modulesCollectionsCriteriaMap,
				fileterAttribute, null, null, null, user.getId(), userModulesOnly);
		
		for(Module module : modules) {
			autoCompleteValues.add(module.getName());
		}
		
		LOGGER.trace("Leaving the getPDBrowseModulesAutoCompleteData() method");
		return autoCompleteValues;
	}
	

	/**
	 * @return
	 */
	@RequestMapping(value = "getPDBrowseModulesDropdownData.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getPDBrowseModulesDropdownData(
	  		@RequestParam(value="userModulesOnly", required = false) boolean userModulesOnly) {
		LOGGER.trace("Entering the getPDBrowseModulesDropdownData() method");
		
		Map<String, Object>  browseModuleValues =  new HashMap<String, Object>();
		SortedSet<String> orgDisplayIdentifiers = new TreeSet<String>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Organization organization = user.getOrganization();
		
		List<Module> modules = professionalDevelopmentService.getModules(organization.getId(), (new HashMap<String,String>()),
				null, null, null, null, user.getId(), userModulesOnly);
		for(Module module: modules) {
			orgDisplayIdentifiers.add(module.getOrgDisplayIdentifier());
		}
		browseModuleValues.put("organizations", orgDisplayIdentifiers);
		
		List<Category> enrollmentCategory= categoryService.selectByCategoryType("USER_MODULE_STATUS");				
		if(userModulesOnly) {
			Iterator<Category> iter = enrollmentCategory.iterator();
			while (iter.hasNext()) {
				Category cat = iter.next();
			    if (cat.getCategoryCode().equals("UNENROLLED")) {
			        iter.remove();
			    }
			}
		} 
		browseModuleValues.put("enrollmentCategory", enrollmentCategory);
		
		LOGGER.trace("Leaving the getPDBrowseModulesDropdownData() method");
		return browseModuleValues;
	}


	/**
	 * 
	 */
	@RequestMapping(value = "createModules.htm", method = RequestMethod.POST)
	public final @ResponseBody  Boolean createModules(@RequestParam("testId") String testId,
			@RequestParam("tutorialId") String tutorialId,
			@RequestParam("moduleName") String moduleName,
			@RequestParam("description") String description,
			@RequestParam("suggestAudience") String suggestAudience,
			@RequestParam("passingScore") String passingScore,
			@RequestParam("assessmentProgramId") String assessmentProgramId,
			@RequestParam(value="roleIds[]", required = false) String[] roleIds,
	  		@RequestParam("state") String state,
	  		@RequestParam(value="tagIds[]", required = false) String[] tagIds,
	  		@RequestParam("requiredflag") String requiredflag) {
		LOGGER.trace("Entering the createModules() method");
		Boolean success = true;
		Date date = new Date();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User currentUser = userDetails.getUser();	
		Long userid = currentUser.getId();

		List<Module> moduleinserts = professionalDevelopmentService.getModuleByName(moduleName);
		
		if(!(moduleinserts.isEmpty())){
			success = false;
		} else {

				Module module = new Module();
				module.setTestid(Long.parseLong(testId));
				if(tutorialId!=null && tutorialId.length()>0)
					module.setTutorialid(Long.parseLong(tutorialId));
				
				module.setAssessmentprogramid(Long.parseLong(assessmentProgramId));
				module.setSuggestedaudience(suggestAudience);
				module.setName(moduleName);
				module.setDescription(description);
				if(passingScore != null && !passingScore.isEmpty()) {
					module.setPassingScore(Integer.parseInt(passingScore));
				}
				module.setCreateddate(date);
				module.setModifieddate(date);
				module.setCreateduser(userid);
				module.setModifieduser(userid);
				module.setActiveflag(success);
				
				if(requiredflag.equalsIgnoreCase("Yes")){
					module.setRequiredflag(Boolean.TRUE);
				} else {
					module.setRequiredflag(Boolean.FALSE);
				}
				
				Long statusid = null;
				if(state.equals("PUBLISHED")){
					statusid = categoryDao.getCategoryId(state, "MODULE_STATUS");					
				}else{
					statusid = categoryDao.getCategoryId(state, "MODULE_STATUS");
				}
				module.setStatusid(statusid);
				
				// capture selected roles
				if(roleIds != null && roleIds.length > 0){
					List<Long> groupIds = new ArrayList<Long>();
					for (String roleId : roleIds) {
						if(StringUtils.isNotEmpty(roleId) && StringUtils.isNumeric(roleId)) {
							groupIds.add(Long.valueOf(roleId));
						}
					}
					module.setGroupIds(groupIds);
				}
				
				// capture tags
				if(tagIds != null && tagIds.length > 0){
					List<Long> moduleTagIds = new ArrayList<Long>();
					for (String moduleTagId : tagIds) {
						if(StringUtils.isNotEmpty(moduleTagId) && StringUtils.isNumeric(moduleTagId)) {
							moduleTagIds.add(Long.valueOf(moduleTagId));
						}
					}
					module.setTagIds(moduleTagIds);
				}
				professionalDevelopmentService.createModule(module);
								
				Activity activity = new Activity();
				activity.setDescription("Created Module" + " " + moduleName);
				activity.setUserId(userid);
				activity.setModuleId(module.getId());
				activity.setCreatedDate(date);
				activity.setModifiedDate(date);
				activity.setCreatedUser(userid);
				activity.setModifiedUser(userid);
				activity.setActiveFlag(success);
				professionalDevelopmentService.createActvities(activity);
		}
	    
		return success;
	}
	
	@RequestMapping(value = "updateModule.htm", method = RequestMethod.POST)
	public final @ResponseBody  Boolean updateModule(@RequestParam("moduleId") String moduleId,
			@RequestParam("testId") String testId,
			@RequestParam("tutorialId") String tutorialId,
			@RequestParam("moduleName") String moduleName,
			@RequestParam("description") String description,
			@RequestParam("suggestAudience") String suggestAudience,
			@RequestParam(value="passingScore", required = false) String passingScore,
			@RequestParam(value="ceu", required = false) String ceu,
			@RequestParam("assessmentProgramId") String assessmentProgramId,
			@RequestParam(value="roleIds[]", required = false) String[] roleIds,
	  		@RequestParam(value="tagIds[]", required = false) String[] tagIds,
	  		@RequestParam("requiredflag") String requiredflag) {
		LOGGER.trace("Entering the updateModule() method");
		Boolean success = true;
		Date date = new Date();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User currentUser = userDetails.getUser();	
		Long userid = currentUser.getId();

		boolean releasePermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getReleaseModulePermission());
		boolean unreleasePermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getUnreleaseModulePermission());
		boolean stateCEUPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getStateCEUPermission());
		
		boolean hasCEUEditOnly = (releasePermission || unreleasePermission) &&  stateCEUPermission;
		Long moduleIdLong = Long.parseLong(moduleId);
		
		if(hasCEUEditOnly) {
			
            ModuleState moduleState = professionalDevelopmentService.getStateCEUByModuleUser(moduleIdLong, currentUser);
            if(moduleState != null) {
        		if(ceu != null && !ceu.isEmpty()) {
        			moduleState.setCeu(Integer.parseInt(ceu));
        		}            	
            	
        		if(!professionalDevelopmentService.updateModuleState(moduleState)){
        			success = false;
        		}
            } else {
            	
            	// No existing record found for the state
            	if(ceu != null && !ceu.isEmpty()) {
            		Long unreleasedStatusId = categoryDao.getCategoryId("UNRELEASED", "MODULE_STATUS");            		
            		
        			moduleState = new ModuleState();
        			moduleState.setModuleId(moduleIdLong);
        			moduleState.setStatusId(unreleasedStatusId);
        			moduleState.setCeu(Integer.parseInt(ceu));
        			if(!professionalDevelopmentService.createModuleState(moduleState, currentUser)) {
        				success = false;
        			}
            	}            	
            }

			return success;
		}
		
		List<Module> moduleinserts = professionalDevelopmentService.getModuleByName(moduleName);		
		if(!(moduleinserts.isEmpty())){

			for (Module module : moduleinserts) {				
				if(!module.getId().equals(moduleIdLong)) {
					return false;
				}
			}
		} 

		Module module = professionalDevelopmentService.getModuleDetailsById(moduleIdLong);
		module.setTestid(Long.parseLong(testId));
		if(tutorialId != null && tutorialId.length() > 0){
			module.setTutorialid(Long.parseLong(tutorialId));
		}
		module.setAssessmentprogramid(Long.parseLong(assessmentProgramId));

		if(passingScore != null && !passingScore.isEmpty()) {
			module.setPassingScore(Integer.parseInt(passingScore));
		}		
		module.setSuggestedaudience(suggestAudience);
		module.setName(moduleName);
		module.setDescription(description);
		module.setModifieddate(date);
		module.setModifieduser(userid);
		module.setActiveflag(success);
		
		if(requiredflag.equalsIgnoreCase("Yes")){
			module.setRequiredflag(Boolean.TRUE);
		} else {
			module.setRequiredflag(Boolean.FALSE);
		}
		
		// capture selected roles
		if(roleIds != null && roleIds.length > 0){
			List<Long> groupIds = new ArrayList<Long>();
			for (String roleId : roleIds) {
				if(StringUtils.isNotEmpty(roleId) && StringUtils.isNumeric(roleId)) {
					groupIds.add(Long.valueOf(roleId));
				}
			}
			module.setGroupIds(groupIds);
		} else {
			module.setGroupIds(null);
		}
		
		// capture tags
		if(tagIds != null && tagIds.length > 0){
			List<Long> moduleTagIds = new ArrayList<Long>();
			for (String moduleTagId : tagIds) {
				if(StringUtils.isNotEmpty(moduleTagId) && StringUtils.isNumeric(moduleTagId)) {
					moduleTagIds.add(Long.valueOf(moduleTagId));
				}
			}
			module.setTagIds(moduleTagIds);
		} else {
			module.setTagIds(null);
		}
		professionalDevelopmentService.updateModule(module);
	    
		return success;
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "enrollToModule.htm", method = RequestMethod.POST)
	public final @ResponseBody  Boolean enrollToModule(@RequestParam("moduleId") String moduleid
			, @RequestParam("status") String status) {
		LOGGER.trace("Entering the enrollToModule() method");
		
		boolean result = true;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User currentUser = userDetails.getUser();	
		Long userid = currentUser.getId();
		Long moduleId = Long.parseLong(moduleid);
		
		if(status.equalsIgnoreCase("UNENROLLED")){			
			result = professionalDevelopmentService.unenrollToModule(userid, moduleId);			
	    } else {
	    	result = professionalDevelopmentService.enrollToModule(moduleId, currentUser);
	    }
		
		return result;
	}
	
	/**
	    * @param user
	    *            {@link User}
	    * @return List<AssessmentProgram>
	    */
	   private List<AssessmentProgramDto> getAssessmentProgramsForUser(final User user) {
	       //List<Organization> orgs = userService.getOrganizations(user.getId());
	       // TODO change this from the current organization to a unique list of the current organization and its parents assessment
	       // programs.
	       Organization current = user.getOrganization();
	       // List<AssessmentProgram> assessmentPrograms = new ArrayList<AssessmentProgram>();
	       List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();

	       if (current != null) {
	           List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByContractingOrganizationId(current.getId());
	           for (OrgAssessmentProgram oap : orgAssessProgs) {
	               if (!assessmentPrograms.contains(oap.getAssessmentProgram())) {
	                   AssessmentProgramDto temp = new AssessmentProgramDto();
	                   temp.setAssessmentProgram(oap.getAssessmentProgram());
	                   assessmentPrograms.add(temp);
	               }
	           }
	       }

	       return assessmentPrograms;
	   }
	   
		@RequestMapping(value = "setModuleStatus.htm", method = RequestMethod.POST)
		public final @ResponseBody  Boolean setModuleStatus(@RequestParam("moduleId") String moduleid, @RequestParam("status") String status) {
			LOGGER.trace("Entering the setModuleStatus() method");
			Boolean success = true;
			Long moduleId = Long.parseLong(moduleid);
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			if(status.equalsIgnoreCase("Released")) {
				//get user contracting organization
				//get their orgassessment programs
				//mark all these 
				professionalDevelopmentService.releaseModule(moduleId, user);
			} else if(status.equalsIgnoreCase("Unreleased")) {
				professionalDevelopmentService.unreleaseModule(moduleId, user);
			} else if(status.equalsIgnoreCase("Unpublished")) {				
				professionalDevelopmentService.unpublishModule(moduleId, user);				
			} else if(status.equalsIgnoreCase("Published")) {
				Module module = professionalDevelopmentService.getModuleDetailsById(moduleId);
				Long statusid = categoryDao.getCategoryId(status, "MODULE_STATUS");
				
				Category statusCategory = categoryDao.selectByPrimaryKey(statusid);
				module.setStatus(statusCategory.getCategoryName());
				module.setStatusid(statusid);
				success = professionalDevelopmentService.updateModule(module);
			}
			
			return success;
		}

		/**
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "getModulesPDuser.htm", method = RequestMethod.GET)
		public final @ResponseBody  JQGridJSONModel getModulesPDuser(
				@RequestParam("rows") String limitCountStr,
		  		@RequestParam("page") String page,
		  		@RequestParam("sidx") String sortByColumn,
		  		@RequestParam("sord") String sortType,
		  		@RequestParam("filters") String filters,
		  		@RequestParam(value="userModulesOnly", required = false) boolean userModulesOnly) {
			LOGGER.trace("Entering the getModules() method");
	
		    UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();

		    Integer currentPage;
		    Integer limitCount;
		    JQGridJSONModel  jqGridJSONModel  = null;
		    int totalCount = 0;

		    currentPage = NumericUtil.parse(page, 1);
		    limitCount = NumericUtil.parse(limitCountStr,5);

			Map<String,String> testsAndTestCollectionsCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(filters);
			
			List<Category> enrollmentCategory= categoryService.selectByCategoryType(USER_MODULE_ENROLLMENT_CATEGORY_CODE);
			Map<Long, Category> enrollmentCategoryMap = new HashMap<Long, Category>();
			for(Category category :enrollmentCategory) {
				enrollmentCategoryMap.put(category.getId(), category);
				if(category.getCategoryCode().equalsIgnoreCase(CommonConstants.PD_USER_MODULE_UNENROLLED_CATEGORY_CODE) &&
						testsAndTestCollectionsCriteriaMap.containsKey(CommonConstants.PD_USER_MODULE_ENROLLMENT_STATUS_CATEGORY_NAME) && 
						testsAndTestCollectionsCriteriaMap.containsValue(CommonConstants.PERCENTILE_DELIM + category.getId() + CommonConstants.PERCENTILE_DELIM)) {
					testsAndTestCollectionsCriteriaMap.put("unenrolledStatus", "null");
				}
			}
			
			List<Module> modules = professionalDevelopmentService.getModulesForPDuser(testsAndTestCollectionsCriteriaMap,
					sortByColumn, sortType,
					(currentPage-1)*limitCount, limitCount, user.getId(), user);
			totalCount = professionalDevelopmentService.countModules(user.getOrganization().getId(), testsAndTestCollectionsCriteriaMap, user.getId(), userModulesOnly);
			
			jqGridJSONModel = ProfDevModuleJsonConverter.convertToModuleJson(modules, totalCount, currentPage, limitCount, enrollmentCategoryMap);
			
			LOGGER.trace("Leaving the browseModules() method");
			return jqGridJSONModel;
			
		}
		

		@RequestMapping(value = "tagAutocomplete.htm", method = RequestMethod.GET)
		public @ResponseBody List<Tag> tagAutocomplete(@RequestParam("tagName") String tagName) throws Exception {
			List<Tag> tags = tagService.getTagByName(tagName, false);

			return tags;
		}
	
		@RequestMapping(value = "addTag.htm", method = RequestMethod.POST)
		public @ResponseBody Tag addTag(@RequestParam("tagName") String tagName) throws Exception {
			
			List<Tag> tags = tagService.getTagByName(tagName, true);
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			
			Tag tag;
			if (tags.isEmpty()) {
				tag = new Tag();
				tag.setCreatedUser(userDetails.getUser().getId());				
				tag.setCreatedDate(new Date());
				tag.setTagName(tagName);
				tagService.createTag(tag);
			} else {
				tag = tags.get(0);
			}
			return tag;
		}
		
		@RequestMapping(value = "/JSON/studentTest/getTestById", method = RequestMethod.GET)
		@ResponseBody
		public final String getTestById(@RequestParam final Long testId) throws Exception {
			LOGGER.trace("Entering the getById method");
			LOGGER.debug("Parameters testId: {}", testId);
			
			return professionalDevelopmentService.getTest(testId);
		}
		
		@RequestMapping(value = "/JSON/studentTest/getById", method = RequestMethod.POST)
		@ResponseBody
		public final String getStudentTestById(@RequestParam(value="studentsTestsId") final Long studentsTestsId) throws Exception {
			return professionalDevelopmentService.getStudentTestById(studentsTestsId);
		}
		
		@RequestMapping(value = "/JSON/studentTest/checkTicket", method = RequestMethod.GET)
		@ResponseBody
		public final String checkTicket(@RequestParam final String studentsTestsId) {
			

			String result = "{\"ticketsNeeded\":false,\"ticketLevel\":\"section\",\"testSectionTickets\":[]}";
			return result;
		}
		
		@RequestMapping(value = "/JSON/studentTest/updateTestStatus", method = RequestMethod.GET)
		@ResponseBody
		public final String updateTestStatus(@RequestParam Long studentTestId,
				@RequestParam Long studentTestSectionId,
				@RequestParam String status) throws Exception {
			return professionalDevelopmentService.updateTestStatus(studentTestId, studentTestSectionId, status);
		}
		
		@RequestMapping(value = "/JSON/studentTest/saveTest", method = RequestMethod.GET)
		@ResponseBody
		public final Map<String, ? extends Object> saveTest(@RequestParam final Long studentTestId,
				@RequestParam final Long studentTestSectionId,
				@RequestParam final Long testSectionId,
				@RequestParam final String testFormatCode,
				@RequestParam final String testTypeName,
				@RequestParam(value = "interimThetaValues", required = false) String interimThetaValues,
				@RequestParam(value = "numberOfCompletedPart", required = false) Integer numberOfCompletedPart,
				@RequestParam(value = "numberOfPart", required = false) Integer numberOfPart,
				@RequestParam final String testScore,
				@RequestParam final String sectionScore,
				@RequestParam final boolean currentSectionBreak) throws Exception {
			
			return professionalDevelopmentService.saveTest(studentTestId, studentTestSectionId, testSectionId, testFormatCode, testTypeName, 
					interimThetaValues, numberOfCompletedPart, numberOfPart, testScore, sectionScore, currentSectionBreak);
		}
		
		@RequestMapping(value = "/JSON/tracker", method = RequestMethod.POST)
		@ResponseBody
		public final String addTracker(@RequestParam("appKey") Long appKey, @RequestParam("json") String json) {
			return "{}";
		}
		
		@RequestMapping(value = "/JSON/studentresponse/findByTestSection", method = RequestMethod.GET)
		@ResponseBody
		public final String findByTestSection(
				@RequestParam final Long studentsTestId, 
				@RequestParam final Long testSectionId) throws Exception {
			return professionalDevelopmentService.findByTestSection(testSectionId);
		}
		
		@RequestMapping(value = "/JSON/studentresponsehistory/saveList.htm", method = RequestMethod.POST)
		@ResponseBody
		public  Map<String, ? extends Object> saveResponseHist() {
			Map<String, String> historySaved = new HashMap<String, String>();
			return historySaved;
		}

		
		@RequestMapping(value = "/JSON/studentresponse/saveList", method = RequestMethod.POST)
		@ResponseBody
		public final Map<String, ? extends Object> saveAll( 
				final HttpServletRequest request, final HttpServletResponse response) throws Exception {

			Map<String, String> tasksSaved = new HashMap<String, String>();
			if(request.getParameter("list") != null && !"".equals(request.getParameter("list"))) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				ObjectMapper mapper = new ObjectMapper();
		
				List<UserTestResponse> studentResponses = mapper.readValue(request.getParameter("list"),
						new TypeReference<List<UserTestResponse>>() {
						});
				
				for (UserTestResponse studentResponse : studentResponses) {
					studentResponse.setUserId(userDetails.getUser().getId());
					professionalDevelopmentService.addOrUpdateResponse(studentResponse);
					tasksSaved.put(Long.toString(studentResponse.getTaskVariantId()),
							studentResponse.getUserTestSectionId() + "_" +studentResponse.getTaskVariantId());
				}
			}
			return tasksSaved;
		}
		
		@RequestMapping(value = "/pdStudentHome", method = RequestMethod.GET)
		//public final ModelAndView pdStudentHome(@RequestParam(value="testId") final Long testId, @RequestParam(value="studentTestId") final Long studentTestId) throws Exception {
		public final ModelAndView pdStudentHome(@RequestParam(value="moduleId") final Long moduleId
				, @RequestParam(value="tutorialFlag") final Boolean tutorialFlag) throws Exception {
			ModelAndView mav = new ModelAndView("pdStudentHome");
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			
			Module module = professionalDevelopmentService.getByModuleId(moduleId);
			UserTest userTest = null;
			if(module != null) {
				if(tutorialFlag){
					userTest = professionalDevelopmentService.getUserTestByModuleTestUserIds(
									moduleId, module.getTutorialid(), userDetails.getUser().getId());
					mav.addObject("testId", module.getTutorialid());
					mav.addObject("studentTestId", userTest.getId());
				} else {
					userTest = professionalDevelopmentService.getUserTestByModuleTestUserIds(
							moduleId, module.getTestid(), userDetails.getUser().getId());
					mav.addObject("testId", module.getTestid());
					mav.addObject("studentTestId", userTest.getId());
				}
				
				Long passedStatusid = categoryDao.getCategoryId("PASSED", "USER_MODULE_STATUS");
				Long attemptedStatusid = categoryDao.getCategoryId("ATTEMPTED", "USER_MODULE_STATUS");				
				Long inProgressStatusId = categoryDao.getCategoryId("INPROGRESS", "USER_MODULE_STATUS");
				UserModule userModule = professionalDevelopmentService.getUserModuleById(userTest.getUserModuleId());
				
				// Update the status to inprogress if not already in completed state && inprogress
				// Also update to inprogress if already attempted but failed the test.
				if((userModule.getEnrollmentstatusid().longValue() != passedStatusid.longValue()
						&& userModule.getEnrollmentstatusid().longValue() != attemptedStatusid.longValue()
						&& userModule.getEnrollmentstatusid().longValue() != inProgressStatusId.longValue()) 
						|| userModule.getEnrollmentstatusid().longValue() == attemptedStatusid.longValue()) {					
					userModule.setEnrollmentstatusid(inProgressStatusId);
					userModule.setModifiedDate(new Date());
					userModule.setEarnedCEU(null);
					userModule.setTestResult(null);
					userModule.setTestFinalScore(null);
					userModule.setTestCompletionDate(null);
					professionalDevelopmentService.updateUserModule(userModule);
				}				
				
				boolean wipeOutResponses = false;
				if(tutorialFlag){
					// DONOT wipeout responses of a tutorial
					//wipeOutResponses = true;
				} else {
					Long completeUserTestStatusId = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
					if(completeUserTestStatusId.longValue() == userTest.getTestingStatusId().longValue()) {
						wipeOutResponses = true;
					}
				}
				
				if(wipeOutResponses) {
					professionalDevelopmentService.removeUserTestResponses(userTest.getId());
				}
				
				// if usertest and usertestsection are in completed state, user is trying to retake the tutorial or test.
				// We should update the status on usertest and usertestsections to unused status
				Long inProgressUserTestStatusId = categoryDao.getCategoryId("inprogress", "STUDENT_TEST_STATUS");
				professionalDevelopmentService.updateUserTestStatus(inProgressUserTestStatusId, userTest.getId());
				
				Long unUsedTestSectionStatusId = categoryDao.getCategoryId("unused", "STUDENT_TESTSECTION_STATUS");
				professionalDevelopmentService.updateUserTestSectionStatus(unUsedTestSectionStatusId, userTest.getId());
				
				mav.addObject("profileAttributes", "{}");
			}
			return mav;
		}
	    
		@RequestMapping(value = "/studentHome", method = RequestMethod.GET)
		public final String endTest() {
			return "endTest";
		}
		
		@RequestMapping(value = "JSON/studentTestSection/storeTaskFoilOrder", method = RequestMethod.POST)
		@ResponseBody
		public final String finishSection(
				@RequestParam("studentTestSectionId") final Long studentTestSectionId,
				@RequestParam("testSectionId") final Long testSectionId,
				//@RequestParam("testSessionId") final Long testSessionId,
				@RequestParam("studentsTasks") final String studentsTasksFoils) throws Exception {
			List<UserTestSectionTask> studentsTasksList = new ArrayList<UserTestSectionTask>();
			
			ObjectMapper objectMapper = new ObjectMapper();
				studentsTasksList = objectMapper.readValue(studentsTasksFoils,
						new TypeReference<List<UserTestSectionTask>>() {});
			
			for (UserTestSectionTask studentsTestSectionsTask : studentsTasksList) {
				studentsTestSectionsTask.setUserTestSectionId(studentTestSectionId);
				//studentsTestSectionsTask.setTestSectionId(testSectionId);
				//studentsTestSectionsTask.setTestSessionId(testSessionId);
				
				List<UserTestSectionTaskFoil> taskFoilsList = studentsTestSectionsTask.getFoils();
				if(taskFoilsList != null && !taskFoilsList.isEmpty()) {
					for (UserTestSectionTaskFoil studentTasksFoil : taskFoilsList) {
						studentTasksFoil.setUserTestSectionId(studentTestSectionId);
						studentTasksFoil.setTaskId(studentsTestSectionsTask.getTaskId());
						//studentTasksFoil.setTestSectionId(testSectionId);
						//studentTasksFoil.setTestSessionId(testSessionId);
					}
				}
			}
			professionalDevelopmentService.saveStudentsTestSectionsTasks(studentsTasksList);
			return "{\"success\": \"done\"}";
		}
		
	    @RequestMapping(value = "/getAllModuleDetails.htm", method = RequestMethod.POST)
	    public final @ResponseBody Map<String, Object> getAllModuleDetails(final HttpServletRequest request, final HttpServletResponse response) throws ServiceException{
	        Map<String, Object> hashMap = new HashMap<String, Object>();
	        
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		
	        try {
	            long moduleId = Long.parseLong(request.getParameter("moduleId"));

	            Module module = professionalDevelopmentService.getModuleDetailsById(moduleId);
	            ModuleState moduleState = professionalDevelopmentService.getStateCEUByModuleUser(moduleId, user);
	            if(moduleState != null) {
	            	module.setStateCEU(moduleState.getCeu());
	            }
	            hashMap.put("module", module);

	        } catch (NumberFormatException e) {
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            hashMap.put("invalidParams", true);
	        }

	        return hashMap;
	    }
	    
		@RequestMapping(value = "getTranscripts.htm", method = RequestMethod.POST)
		public final @ResponseBody  JQGridJSONModel getTranscripts(@RequestParam("rows") String limitCountStr,
		  		@RequestParam("page") String page,
		  		@RequestParam("sidx") String sortByColumn,
		  		@RequestParam("sord") String sortType,
		  		@RequestParam("filters") String filters) {
			
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		
    		boolean releasePermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
    				RestrictedResourceConfiguration.getReleaseModulePermission());
    		boolean unreleasePermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
    				RestrictedResourceConfiguration.getUnreleaseModulePermission());
    		boolean publishPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
    				RestrictedResourceConfiguration.getPublishModulePermission());
    		boolean unpublishPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
    				RestrictedResourceConfiguration.getUnpublishModulePermission());
    		boolean stateCEUPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
    				RestrictedResourceConfiguration.getStateCEUPermission());
    		boolean editModulePermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
    				RestrictedResourceConfiguration.getEditModulePermission());
    		boolean viewPDAdminPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
    				RestrictedResourceConfiguration.getViewPDAdminPermission());
    		    		
    		boolean stateTranscriptsOnly = false; 
    		boolean districtTranscriptsOnly = false;
    		if(releasePermission || unreleasePermission || publishPermission 
    				|| unpublishPermission || stateCEUPermission || editModulePermission) {
    			stateTranscriptsOnly = true;
    		} else if (user != null && viewPDAdminPermission && user.getCurrentOrganizationType().equalsIgnoreCase("DT")) {
    			districtTranscriptsOnly = true;
    		}
    		
    		Map<String,String> transcriptsCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(filters);    	
    		
    		if(sortByColumn.equalsIgnoreCase("mid")){
    			sortByColumn = "m.id";
    		} else if(sortByColumn.equalsIgnoreCase("modulename")){
    			sortByColumn = "m.name";
    		}
    		
		    int currentPage = NumericUtil.parse(page, 1);
		    int limitCount = NumericUtil.parse(limitCountStr, 5);
		    
		    int totalCount = 0;
			List<UserModule> userModules = null;
			
			if(stateTranscriptsOnly || districtTranscriptsOnly) {
				totalCount = professionalDevelopmentService.countTranscripts(null, user.getOrganizationId(), transcriptsCriteriaMap);
				userModules = professionalDevelopmentService.getTranscripts(null, user.getOrganizationId(), transcriptsCriteriaMap
						, sortByColumn,  sortType, (currentPage-1)*limitCount, limitCount);							
			} else {
				totalCount = professionalDevelopmentService.countTranscripts(user.getId(), user.getOrganizationId(), transcriptsCriteriaMap);
				userModules = professionalDevelopmentService.getTranscripts(user.getId(), user.getOrganizationId(), transcriptsCriteriaMap
						, sortByColumn, sortType, (currentPage-1)*limitCount, limitCount);				
			}
			
			int totalPages = 0;
			if (totalCount > 0 ){
				totalPages = new Double(Math.ceil(totalCount/limitCount)).intValue();
			}
			if (totalPages == 0){
				totalPages = 1;
			}
			JQGridJSONModel jqGridJSONModel = ProfDevModuleJsonConverter.convertToTranscriptsJson(userModules, totalCount, currentPage, limitCount);
			
			LOGGER.trace("Leaving the getTranscripts() method");
			return jqGridJSONModel;
		}
		
		@RequestMapping(value = "getAdminReports.htm", method = RequestMethod.POST)
		public final @ResponseBody  JQGridJSONModel getAdminReports(@RequestParam("rows") String limitCountStr,
		  		@RequestParam("page") String page,
		  		@RequestParam("sidx") String sortByColumn,
		  		@RequestParam("sord") String sortType,
		  		@RequestParam("filters") String filters,
		  		@RequestParam(value="stateAdminFlag") final Boolean stateAdminFlag,
		  		@RequestParam(value="districtAdminFlag") final Boolean districtAdminFlag) {
			
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();   	
    		
    		if(sortByColumn.equalsIgnoreCase("id")){
    			sortByColumn = "mr.id";
    		} else if(sortByColumn.equalsIgnoreCase("modulename")){
    			sortByColumn = "mr.name";
    		}
    		
		    int currentPage = NumericUtil.parse(page, 1);
		    int limitCount = NumericUtil.parse(limitCountStr, 5);
			
			List<Short> reportTypeIds = new ArrayList<Short>();
			//reportTypeIds.add(DataReportTypeEnum.TRAINING_DETAILS.getId());
			//reportTypeIds.add(DataReportTypeEnum.TRAINING_STATUS.getId());
			reportTypeIds.add(DataReportTypeEnum.DLM_PD_TRAINING_LIST.getId());
			
			List<ModuleReport> moduleReports = professionalDevelopmentService.getAdminReports(user, stateAdminFlag, districtAdminFlag, reportTypeIds);
			int totalCount = professionalDevelopmentService.countAdminReports(user, stateAdminFlag, districtAdminFlag, reportTypeIds);
			
			int totalPages = 0;
			if (totalCount > 0 ){
				totalPages = new Double(Math.ceil(totalCount/limitCount)).intValue();
			}
			if (totalPages == 0){
				totalPages = 1;
			}
			
			JQGridJSONModel jqGridJSONModel = ProfDevModuleJsonConverter.convertToAdminReportsJson(moduleReports, totalCount, currentPage, limitCount,userDetails);
			
			LOGGER.trace("Leaving the getAdminReports() method");
			return jqGridJSONModel;
		}	
				
	    @RequestMapping(value = "getAdminReportCsv.htm", method = RequestMethod.GET)
	    public final void getAdminReportCsv(@RequestParam String reportName ,final HttpServletResponse response, final HttpServletRequest request) {

    		String reportCSVName = "pdreports" + java.io.File.separator + reportName;
        	File csvFile = new File(REPORT_PATH + java.io.File.separator   + reportCSVName);
        	
			if(csvFile.exists()){
				InputStream inputStream = null;
	        	try {
					response.setContentType("application/force-download");
		    		response.addHeader("Content-Disposition", "attachment; filename=" + reportName);
	                inputStream = new FileInputStream(csvFile);
	                IOUtils.copy(inputStream, response.getOutputStream());
	                response.flushBuffer();
				} catch (FileNotFoundException e) {
					LOGGER.error("PD Report CSV file not Found. File - " + reportCSVName);
					LOGGER.error("FileNotFoundException : ",e);
				} catch (IOException e) {
					LOGGER.error("Error occurred while downloading PD Report CSV file. File - ." + reportCSVName);
					LOGGER.error("IOException : ",e);
				} finally {
					if(inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							LOGGER.error("ignore IOException closing: ",e);
						}
					}
				}
			}else{
				LOGGER.debug("PD Report CSV file not Found. File - " + reportCSVName);
			}
	    }
	    
	    @SuppressWarnings("unused")
		@RequestMapping(value = "generateTrainingStatusReport.htm", method = RequestMethod.POST)
		public final @ResponseBody long generateTrainingStatusReport(@RequestParam(value="moduleReportId") final Long moduleReportId,
		  		@RequestParam(value="stateAdminFlag") final Boolean stateAdminFlag,
		  		@RequestParam(value="districtAdminFlag") final Boolean districtAdminFlag) throws Exception {
			
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		
			long newModuleReportId = professionalDevelopmentService.generatePDReport(user, 
					stateAdminFlag, districtAdminFlag, moduleReportId, DataReportTypeEnum.TRAINING_STATUS);			
			
			Future<Map<String, Object>> result = pdReportProcessor.startPDReportGeneration(user, newModuleReportId);
			
			LOGGER.trace("Leaving the generateTrainingStatusReport() method");
			return newModuleReportId;
		}
	    @SuppressWarnings("unused")
		@RequestMapping(value = "dlmPDTrainingList.htm", method = RequestMethod.POST)
		public final @ResponseBody long dlmPDTraningList(@RequestParam(value="moduleReportId") final Long moduleReportId,
		  		@RequestParam(value="stateAdminFlag") final Boolean stateAdminFlag,
		  		@RequestParam(value="districtAdminFlag") final Boolean districtAdminFlag) throws Exception {
			
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		
			long newModuleReportId = professionalDevelopmentService.generatePDReport(user, 
					stateAdminFlag, districtAdminFlag, moduleReportId, DataReportTypeEnum.DLM_PD_TRAINING_LIST);			
			
			//Future<Map<String, Object>> result = pdReportProcessor.startPDReportGeneration(user, newModuleReportId);
			
			LOGGER.trace("Leaving the generateTrainingStatusReport() method");
			return newModuleReportId;
		}	
		
		@SuppressWarnings("unused")
		@RequestMapping(value = "generateTrainingDetailsReport.htm", method = RequestMethod.POST)
		public final @ResponseBody long generateTrainingDetailsReport(@RequestParam(value="moduleReportId") final Long moduleReportId,
		  		@RequestParam(value="stateAdminFlag") final Boolean stateAdminFlag,
		  		@RequestParam(value="districtAdminFlag") final Boolean districtAdminFlag) throws Exception {
			
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		
			long newModuleReportId = professionalDevelopmentService.generatePDReport(user, 
					stateAdminFlag, districtAdminFlag, moduleReportId, DataReportTypeEnum.TRAINING_DETAILS);			
			
			Future<Map<String, Object>> result = pdReportProcessor.startPDReportGeneration(user, newModuleReportId);
			
			LOGGER.trace("Leaving the generateTrainingDetailsReport() method");
			return newModuleReportId;
		}	 
		
		@RequestMapping(value = "monitorReportGenerationStatus.htm", method = RequestMethod.POST)
		public final @ResponseBody String monitorDeploymentProgress(@RequestParam(value="moduleReportId") final Long moduleReportId) throws Exception {

			ModuleReport moduleReport = professionalDevelopmentService.getModuleReportById(moduleReportId);
			
			if(moduleReport != null) {
				return moduleReport.getStatusName();
			}
			
			return "";
		}
		
		@RequestMapping(value = "monitorDetailsReportGeneration.htm", method = RequestMethod.POST)
		public final @ResponseBody String monitorDetailsDeploymentProgress(@RequestParam(value="moduleReportId") final Long moduleReportId) throws Exception {

			ModuleReport moduleReport = professionalDevelopmentService.getModuleReportById(moduleReportId);
			
			if(moduleReport != null) {
				return moduleReport.getStatusName();
			}
			
			return "";
		}
		
}