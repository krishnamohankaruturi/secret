package edu.ku.cete.controller.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.configuration.SessionRulesConfiguration;
import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.SpecialCircumstance;
import edu.ku.cete.domain.StudentSpecialCircumstance;
import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.enrollment.AutoRegisteredStudentsDTO;
import edu.ku.cete.domain.testsession.AutoRegisteredTestSessionDTO;
import edu.ku.cete.domain.testsession.TestSessionRoster;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentSpecialCircumstanceService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.json.DailyAccessCodesDTOJsonConverter;
import edu.ku.cete.util.json.RecordBrowserJsonUtil;
import edu.ku.cete.util.json.TestCoordinationStudentsJsonConverter;
import edu.ku.cete.util.json.TestSessionRosterJsonConverter;
import edu.ku.cete.web.TestSessionPdfDTO;
import edu.ku.cete.web.support.DailyAccessCodeCSVGenerator;
import edu.ku.cete.web.support.DailyAccessCodePdfGenerator;

/**
 * Controller class for manageTestSession that handles requests from different
 * tabs while viewing,editing the test session and test coordination.
 * 
 * @author vittaly
 * 
 */
@Controller
public class ManageTestSessionController {

	
	@Value("classpath:reports.fop.conf.xml")
	private Resource reportConfigFile;
	
	@Value("/templates/xslt/students.xsl")
	private Resource studentsTicketsXslFile;
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ManageTestSessionController.class);
	
	private static final String VIEW_TC_TEST_SESSION_JSP = "/test/viewAutoRegisteredTestSession";
	
	/**
	 * VIEW_QC_TEST_SESSIONS_JSP
	 */
	private static final String VIEW_QC_TEST_SESSIONS_JSP = "/test/viewQCTestSessions";
	/**
	 * organizationService
	 */
	@Autowired
	private OrganizationService organizationService;
	/**
	 * enrollmentService.
	 */
	@Autowired
	private EnrollmentService enrollmentService;
	/**
	 * sessionRulesConfiguration.
	 */
	@Autowired
	private SessionRulesConfiguration sessionRulesConfiguration;
	/**
	 * permissionUtil.
	 */
	@Autowired
	private PermissionUtil permissionUtil;
	/**
	 * recordBrowserJsonUtil
	 */
	@Autowired
	private RecordBrowserJsonUtil recordBrowserJsonUtil;
	
    @Autowired
	private StudentSpecialCircumstanceService studentSpecialCircumstanceService;
    
	   /**
     * categoryService
     */
    @Autowired
	private TestSessionService testSessionService;

    @Autowired
	private StudentsTestsService studentsTestsService;
    
    @Autowired
	private StudentProfileService studentProfileService;
    
	/**
	 * categoryService
	 */
	@Autowired
	private CategoryService categoryService;
	
    @Autowired
    private AssessmentProgramService assessmentProgramService;

	/*
	 * limit the size of an in clause being sent to query
	 */
	@Value("${in.clause.split.limit}")
	private int inClauseLimit;

	@Value("${user.organizationtype.schoollevel}")
	private String ORG_TYPECODE_SCHOOL;

	/**
	 * User organization for DLM
	 */
	@Value("${user.organization.DLM}")
	private String USER_ORG_DLM;

	/**
	 * User organization for DLM
	 */
	@Value("${iti.code.itistatus}")
	private String ITI_ACCESS;

	@Value("${print.test.file.path}")
	private String printTestFilePath;

	@Autowired
	private AwsS3Service s3;

	@Autowired
    private ServletContext context; 	

	@Autowired
	private DailyAccessCodePdfGenerator dacPdfGenerator;
	
	@Autowired
	private DailyAccessCodeCSVGenerator dacCSVGenerator;

	@Value("${ismart.assessmentProgram.abbreviatedName}")
	private String ISMART_PROGRAM_ABBREVIATEDNAME;
	
	@Value("${ismart2.assessmentProgram.abbreviatedName}")
	private String ISMART_2_PROGRAM_ABBREVIATEDNAME;
	
	/**
	 * @param sourcePage
	 * @param saveGrid
	 * @return
	 */
	@RequestMapping(value = "viewTestSessions.htm")
	public final ModelAndView viewTestSessions(@RequestParam(value = "sourcePage", required = false) String sourcePage,
			@RequestParam(value = "saveGrid", required = false) String saveGrid,
			@RequestParam(value = "path", required = false, defaultValue = "test/viewTestSessions") String path) {
		LOGGER.trace("Entering the viewTestSessions() method");
		ModelAndView mav = new ModelAndView(path);

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		List<Organization> orgChildren = organizationService.getAllChildren(user.getOrganizationId(), true);
		List<Long> orgChildrenIds = AARTCollectionUtil.getIds(orgChildren);
		List<Organization> orgChildrenDLM = new ArrayList<Organization>();
		boolean checkForDLM = organizationService.checkForDLM(user.getOrganizationId(), USER_ORG_DLM);
		boolean flagForDLM = false;

		if (checkForDLM) {
			orgChildrenDLM = organizationService.getAllChildrenDLM(user.getOrganizationId(), USER_ORG_DLM);
			flagForDLM = true;
		}
		
		boolean isUserLoggedAsDLM = false;
		
		Boolean ITIStatus = Boolean.FALSE;
		// If DLM only then do this
		if (user.getCurrentAssessmentProgramName() != null 
				&& user.getCurrentAssessmentProgramName().equalsIgnoreCase("DLM")) {
			isUserLoggedAsDLM = true;
			
			Long stateId = organizationService.getStateIdByUserOrgId(user.getCurrentOrganizationId());//   .getOrganizationDetailByOrgId(user.getCurrentOrganizationId());
			List<OrganizationDetail> stateDetail = organizationService.getOrganizationDetailByOrgId(stateId, null);
			if(CollectionUtils.isNotEmpty(stateDetail)){
				for (OrganizationDetail od : stateDetail) {
					Date now = new Date();
					Date start = od.getItiStartDate();
					Date end = od.getItiEndDate();
					Date windowStart = od.getWindowEffectiveDate();
					Date windowEnd = od.getWindowExpiryDate();
					if(start != null && end != null){
						Calendar c = Calendar.getInstance();
						c.setTime(end);
						c.add(Calendar.DATE, 1);
						end = c.getTime();
						if (now.after(start) && now.before(end) && now.after(windowStart) && now.before(windowEnd)) {
							ITIStatus = true;
							break;
						}
					}
				}
			}
		}
		
		List<SpecialCircumstance> specialCircumstanceList = studentSpecialCircumstanceService
				.getCEDSByUserState(user.getContractingOrgId());
		
		List<Long> orgChildrenIdsDLM = AARTCollectionUtil.getIds(orgChildrenDLM);

		mav.addObject("orgChildrenIdsDLM", orgChildrenIdsDLM);
		mav.addObject("flagForDLM", flagForDLM);
    	mav.addObject("current", "testSession");
    	mav.addObject("orgChildrenIds", orgChildrenIds);
    	mav.addObject("user", user);
    	mav.addObject("ITIStatus", ITIStatus);
    	mav.addObject("saveGrid", saveGrid);
    	mav.addObject("specialCircumstanceList", specialCircumstanceList);

		if (sourcePage != null)
			mav.addObject("validFlag", sourcePage);
		
		//DTC, BTC role will have unrestricted access to Test Management tab, only for teacher or proctor role security agreement check is needed
    	if(((isUserLoggedAsDLM && user.isAssessmentProgramUser("DLM")) || ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(user.getCurrentAssessmentProgramName())
    			|| ISMART_2_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(user.getCurrentAssessmentProgramName())) && (user.isTeacher() || user.isProctor())) {
    		String testManagementAccessReason = testSessionService.verifyTestManagementTabAccessCriteria(user);
    		if(testManagementAccessReason.equals("valid")){
            	mav.addObject("TMTabAccessFlag",  Boolean.TRUE);
    		}else{
            	mav.addObject("TMTabAccessFlag",  Boolean.FALSE);
            	mav.addObject("TMTabAccessFailedReason",  testManagementAccessReason);
    		}
    	} else {
        	mav.addObject("TMTabAccessFlag", Boolean.TRUE);
    	}		

		LOGGER.trace("Leaving the view() method");
		return mav;
	}

	@RequestMapping(value="viewTestCoordination.htm")
	public final ModelAndView viewTestCoordination(){
		ModelAndView mav = new ModelAndView("testCoordination");
		return mav;
	}
	
	@RequestMapping(value="viewProjectedTesting.htm")
	public final ModelAndView viewProjectedTesting(){
		ModelAndView mav = new ModelAndView("projectedTesting");
		return mav;
	}
	
	@RequestMapping(value="viewOperationalTestWindow.htm")
	public final ModelAndView viewOperationalTestWindow(){
		ModelAndView mav = new ModelAndView("operationalTestWindow");
		return mav;
	}
	
	@RequestMapping(value="viewinstructionalSupport.htm")
	public final ModelAndView viewinstructionalSupport(@RequestParam(value = "sourcePage", required = false) String sourcePage,
			@RequestParam(value = "saveGrid", required = false) String saveGrid,
			@RequestParam(value = "path", required = false, defaultValue = "instructionalSupportWindow") String path){
		ModelAndView mav = new ModelAndView("instructionalSupportWindow");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		List<Organization> orgChildren = organizationService.getAllChildren(
				user.getOrganizationId(), true);
		List<Long> orgChildrenIds = AARTCollectionUtil.getIds(orgChildren);
		List<Organization> orgChildrenDLM = new ArrayList<Organization>();
		boolean checkForDLM = organizationService.checkForDLM(
				user.getOrganizationId(), USER_ORG_DLM);
		boolean flagForDLM = false;

		if (checkForDLM) {
			orgChildrenDLM = organizationService.getAllChildrenDLM(
					user.getOrganizationId(), USER_ORG_DLM);
			flagForDLM = true;
		}
		
		boolean isUserLoggedAsDLM = false;
		
		Boolean ITIStatus = Boolean.FALSE;
		// If DLM only then do this
		if (user.getCurrentAssessmentProgramName() != null 
				&& user.getCurrentAssessmentProgramName().equalsIgnoreCase("DLM")) {
			isUserLoggedAsDLM = true;
			
			Long stateId = organizationService.getStateIdByUserOrgId(user.getCurrentOrganizationId());//   .getOrganizationDetailByOrgId(user.getCurrentOrganizationId());
			List<OrganizationDetail> stateDetail = organizationService.getOrganizationDetailByOrgId(stateId, null);
			if(CollectionUtils.isNotEmpty(stateDetail)){
				for (OrganizationDetail od : stateDetail) {
					Date now = new Date();
					Date start = od.getItiStartDate();
					Date end = od.getItiEndDate();
					Date windowStart = od.getWindowEffectiveDate();
					Date windowEnd = od.getWindowExpiryDate();
					if(start != null && end != null){
						Calendar c = Calendar.getInstance();
						c.setTime(end);
						c.add(Calendar.DATE, 1);
						end = c.getTime();
						if (now.after(start) && now.before(end) && now.after(windowStart) && now.before(windowEnd)) {
							ITIStatus = true;
							break;
						}
					}
				}
			}
		}
		
		List<SpecialCircumstance> specialCircumstanceList = studentSpecialCircumstanceService.getCEDSByUserState(user.getContractingOrgId());
		
		List<Long> orgChildrenIdsDLM = AARTCollectionUtil.getIds(orgChildrenDLM);

		mav.addObject("orgChildrenIdsDLM", orgChildrenIdsDLM);
		mav.addObject("flagForDLM", flagForDLM);
    	mav.addObject("current", "testSession");
    	mav.addObject("orgChildrenIds", orgChildrenIds);
    	mav.addObject("user", user);
    	mav.addObject("ITIStatus", ITIStatus);
    	mav.addObject("saveGrid", saveGrid);
    	mav.addObject("specialCircumstanceList", specialCircumstanceList);

		if (sourcePage != null)
			mav.addObject("validFlag", sourcePage);
		
    	if(isUserLoggedAsDLM && (user.isTeacher() || user.isProctor()) 
    			&& user.isAssessmentProgramUser("DLM")) {
    		String testManagementAccessReason = testSessionService.verifyTestManagementTabAccessCriteria(user);
    		if(testManagementAccessReason.equals("valid")){
            	mav.addObject("TMTabAccessFlag",  Boolean.TRUE);
    		}else{
            	mav.addObject("TMTabAccessFlag",  Boolean.FALSE);
            	mav.addObject("TMTabAccessFailedReason",  testManagementAccessReason);
    		}
    	} else {
        	mav.addObject("TMTabAccessFlag", Boolean.TRUE);
    	}
		
		return mav;
	}
	
	@RequestMapping(value = "setupAutoRegisteredTestSession.htm")
	public final ModelAndView setupAutoRegisteredTestSession(
			@RequestParam(value = "testSessionId", required = false) final Long testSessionId,
			@RequestParam(value = "testSessionName", required=false) String testSessionName,
			@RequestParam(value = "assessmentProgramName", required=false) String assessmentProgramName,
			@RequestParam(value = "testPanelId", required=true)String testPanelId,
			@RequestParam(value = "ticketsDisplay", required=true)String ticketsDisplay,
			@RequestParam(value = "testingProgram", required=false)String testingProgramName) {
		
		LOGGER.trace("Entering the setupAutoRegisteredTestSession() method");
		ModelAndView mav = new ModelAndView(VIEW_TC_TEST_SESSION_JSP);
		if(testSessionId != null && testSessionId != -1) {
			LOGGER.debug("Test Session id is being passed "+testSessionId);
		}
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		//User user = userDetails.getUser();
		List<SpecialCircumstance> specialCircumstanceList = studentSpecialCircumstanceService
				.getCEDSByUserState(userDetails.getUser().getContractingOrgId());
		boolean hasSpecialCircumstanceApprovalPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getHighStakesSplCircumstanceApprovalPermission());
		// if a state's special circumstance requires confirmation then show the
		// approval column. At present only
		// KANSAS has this feature through statespecialcircumstance table.
		boolean specialCircumstanceApprovalVisible = false;
		boolean stateHasRestrictedCodes = false;
		for(SpecialCircumstance specialCircumstance : specialCircumstanceList){
			if(specialCircumstance.getRequireConfirmation().booleanValue()){
				stateHasRestrictedCodes = true;
				if(hasSpecialCircumstanceApprovalPermission){
					specialCircumstanceApprovalVisible = true;
				}
			}
		}
		mav.addObject("specialCircumstanceApprovalVisible", specialCircumstanceApprovalVisible);
		
		// Per US17379 Alaska's (AMP) State Assessment Administrators should
		// apply SC codes to students.
		boolean isExcludeAdmins = false;
		for(Groups groups : userDetails.getUser().getGroupsList()){
			if ((groups.getGroupCode().equals("SAAD") || groups.getGroupCode().equals("SSAD"))
					&& userDetails.getUser().getCurrentGroupsId() == groups.getGroupId()) {
				isExcludeAdmins = true;
			}
		}
		mav.addObject("isExcludeAdmins", isExcludeAdmins);
		List<String> approvalCodes = new ArrayList<String>(
				Arrays.asList("APPROVED", "NOT_APPROVED", "PENDING_FURTHER_REVIEW"));
		Map<String, Category> specialCircumstanceStatusList = categoryService
				.selectByCategoryTypeAndCategoryCodes("SPECIAL CIRCUMSTANCE STATUS", approvalCodes);
		
		Collection<Category> specialCircumstanceApprovalList = specialCircumstanceStatusList.values();
		
		LOGGER.trace("Getting AssessmentPrograms for user's current organization");
		// mav.addObject("assessmentPrograms",
		// getAssessmentProgramsForUser(user));
		TestSession testSession = testSessionService.findByPrimaryKey(testSessionId);
		mav.addObject("testSessionId", testSessionId);
		mav.addObject("testSessionName",testSessionName);
		mav.addObject("assessmentProgramName", assessmentProgramName);
		mav.addObject("highStakesFlag", true);
		mav.addObject("testCollectionSystemFlag",true);
		mav.addObject("specialCircumstanceList", specialCircumstanceList);
		mav.addObject("specialCircumstanceApprovalList",specialCircumstanceApprovalList);
		mav.addObject("hasSpecialCircumstanceApprovalPermission", hasSpecialCircumstanceApprovalPermission);
		mav.addObject("stateHasRestrictedCodes", stateHasRestrictedCodes);
		mav.addObject("ticketsDisplay", ticketsDisplay);
		mav.addObject("testingProgramName", testingProgramName);
		
		String testSessionType = (testPanelId.equals("Not Available") ? "NON-ADAPTIVE": "ADAPTIVE");
		mav.addObject("testSessionType", testSessionType);
		String currentStageName = "";
		String nextStageName = "";
		if(testSession.getStageId() != null){
			Stage currentStage =testSessionService.selectTestSessionStageByPrimaryKey(testSession.getStageId());
			Stage nextStage = studentsTestsService.getNextStageByTestSession(testSession.getId());
			if(currentStage != null) {
				currentStageName = currentStage.getName();
			}
			if(nextStage != null){
				nextStageName = nextStage.getName();
			}
		}
		mav.addObject("currentStageName",currentStageName);
		mav.addObject("nextStageName",nextStageName);
		/*
		 * if(getRubricForTestSession(testSessionId, rosterId))
		 * mav.addObject("rubricFlag",true); else
		 * mav.addObject("rubricFlag",false);
	*/	

		boolean qcPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
		LOGGER.debug("hasQCPermission : "+qcPermission);
		mav.addObject("hasQCPermission",qcPermission);

		LOGGER.trace("Leaving the setupAutoRegisteredTestSession() method");
		return mav;
	}
	
	/**
	 * @param sourcePage
	 * @return
	 */
	@RequestMapping(value = "viewQCTestSessions.htm")
	public final ModelAndView viewQCTestSessions(
			@RequestParam(value = "sourcePage", required = false) String sourcePage) {
		LOGGER.trace("Entering the viewQCTestSessions() method");
		ModelAndView mav = new ModelAndView(VIEW_QC_TEST_SESSIONS_JSP);

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		List<Organization> orgChildren = organizationService.getAllChildren(user.getOrganizationId(), true);
		List<Long> orgChildrenIds = AARTCollectionUtil.getIds(orgChildren);

		mav.addObject("current", "testSession");
		mav.addObject("orgChildrenIds", orgChildrenIds);
		mav.addObject("user", user);

		if (sourcePage != null)
			mav.addObject("validFlag", sourcePage);

		LOGGER.trace("Leaving the view() method");
		return mav;
	}

    static Comparator<AssessmentProgram> assmntPgmComparator = new Comparator<AssessmentProgram>(){
	   	public int compare(AssessmentProgram ap1, AssessmentProgram ap2){
	   		return ap1.getProgramName().compareToIgnoreCase(ap2.getProgramName());
	   	}
	};
		
	@RequestMapping(value = "getAssessmentProgramsOfUserOrganization.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgram> getAssessmentProgramsOfUserOrganization(){
		LOGGER.trace("Entering getAssessmentProgramsForUserOrganization");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		List<AssessmentProgram> assessmentPrograms = assessmentProgramService
				.findByOrganizationId(userDetails.getUser().getCurrentOrganizationId());
		Collections.sort(assessmentPrograms, assmntPgmComparator);
		
		LOGGER.trace("Leaving getAssessmentProgramsForUserOrganization");
		return assessmentPrograms;
	}
	
	/**
	 * This method finds rosters that the currently logged in user is authorized
	 * to view.
	 * 
	 * @param request
	 * @return List<Roster>
	 */
	// TODO : Will rename to actual requestMapping up on fully completing the
	// setup testsession page
	// and will remove the old code from TestController.java.
	@RequestMapping(value = "getTestSessionsByUser.htm", method = RequestMethod.POST)
	public final @ResponseBody JsonResultSet getRosterStudents(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("filters") String filters,
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("testingProgramId") Long testingProgramId, @RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeCourseId") Long gradeCourseId, @RequestParam("schoolOrgId") Long schoolOrgId,
			@RequestParam("showExpired") String showExpired,
			@RequestParam("includeCompletedTestSession") String includeCompletedTestSession) {
		LOGGER.trace("Entering the getTestSessionsByUser page for getting results");
		
		// TODO move all 3 steps to a service.
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		// this prevents a NullPointerException for teachers not having passed
		// useful parameters
		// (since they don't have the filters available)
		if (user.isTeacher()) {
			assessmentProgramId = user.getCurrentAssessmentProgramId();
			schoolOrgId = user.getCurrentOrganizationId();
			testingProgramId = null;
			gradeCourseId = null;
			contentAreaId = null;
			showExpired = "";
		}

		int currentPage = NumericUtil.parse(page, 1);
		int limitCount = NumericUtil.parse(limitCountStr, 5);
		int totalCount = 1;
		JsonResultSet jsonResultSet = null;

		Map<String, String> studentRosterCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
		TimerUtil timerUtil = TimerUtil.getInstance();
		Boolean hasQCCompletePermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
		// US12810 - if the user has qc complete permission then we want to show
		// qccomplete true and false
		// so send null as qcComplete to remove where clause item
		Boolean qcComplete = true;
		if (hasQCCompletePermission) {
			qcComplete = null;
		}
		List<TestSessionRoster> testSessionRosters =null;
		Boolean showExpiredFlag = false;
		if(showExpired != null && showExpired.equalsIgnoreCase("true")) {
			showExpiredFlag = true;
		}
		/**
		 * Bishnupriya Nayak :US19343 : Test Management TestSession Completion
		 * Status Display
		 */

		Boolean includeCompletedTestSessionsFlag=false;
		if(includeCompletedTestSession !=null && includeCompletedTestSession.equalsIgnoreCase("true") ){
			includeCompletedTestSessionsFlag=true;
		}
			
		Boolean includeInProgressTestSession=true;
		testSessionRosters = testSessionService.getTestSessionWithRoster(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount, studentRosterCriteriaMap,
				sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
				sessionRulesConfiguration.getManualEnrollmentCategory().getId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewHighStakesTestSessionsPermission()),
				qcComplete, assessmentProgramId, testingProgramId, contentAreaId, gradeCourseId, schoolOrgId,
				showExpiredFlag, hasQCCompletePermission, includeCompletedTestSessionsFlag,
				includeInProgressTestSession);
		timerUtil.resetAndLog(LOGGER, "Getting getTestSessionsByUser took ");
		/**
		 * US15563 : Test Management - Display Test Info pdf for student Added
		 * logic to receive the test resources/media i.e. pdf files associated
		 * to the test for selected students.
		 */
		if (testSessionRosters != null && testSessionRosters.size() > 0) {
			for (TestSessionRoster tsr : testSessionRosters) {
				Set<StudentTestResourceInfo> srinfomap = enrollmentService
						.getResourceByStudentIdTestSessionId(tsr.getTestSession().getId(), currentSchoolYear);
				tsr.setResourceList(srinfomap);
			}
		}
		
		if(!testSessionRosters.isEmpty()) {
			totalCount = testSessionRosters.get(0).getTotalRecords();
		}
		timerUtil.resetAndLog(LOGGER, "Counting getTestSessionsByUser took ");

		jsonResultSet = TestSessionRosterJsonConverter.convertToStudentRosterJson(testSessionRosters, totalCount,
						currentPage, limitCount);

		LOGGER.trace("Leaving the getTestSessionsByUser page.");
		return jsonResultSet;
	}

	/**
	 * @param orgChildrenIds
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @param filters
	 * @return
	 */
	@RequestMapping(value = "getQCTestSessionsByUser.htm", method = RequestMethod.POST)
	public final @ResponseBody JsonResultSet getQCRosterStudents(
			@RequestParam("orgChildrenIds") String[] orgChildrenIds, @RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("filters") String filters) {
		LOGGER.trace("Entering the getQCTestSessionsByUser page for getting results");

		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;
		JsonResultSet jsonResultSet = null;

		// TODO move all 3 steps to a service.
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		Boolean hasHighStakesPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getHighStakesPermission());
		Map<String, String> studentRosterCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
		TimerUtil timerUtil = TimerUtil.getInstance();
		List<TestSessionRoster> testSessionRosters = testSessionService.getQCTestSessionWithRoster(null,
				NumericUtil.convert(orgChildrenIds), userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				sortByColumn + ParsingConstants.BLANK_SPACE + sortType, (currentPage - 1) * limitCount, limitCount,
				studentRosterCriteriaMap, sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
				sessionRulesConfiguration.getManualEnrollmentCategory().getId(), hasHighStakesPermission);
		timerUtil.resetAndLog(LOGGER, "Getting getQCTestSessionsByUser took ");
		
		totalCount = testSessionService.countQCTestSessionWithRoster(null, NumericUtil.convert(orgChildrenIds),
				userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				studentRosterCriteriaMap, sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
				sessionRulesConfiguration.getManualEnrollmentCategory().getId(), hasHighStakesPermission);
		timerUtil.resetAndLog(LOGGER, "Counting getQCTestSessionsByUser took ");
		jsonResultSet = TestSessionRosterJsonConverter.convertToStudentRosterJson(testSessionRosters, totalCount,
						currentPage, limitCount);

		LOGGER.trace("Leaving the getQCTestSessionsByUser page.");
		return jsonResultSet;
	}
	@RequestMapping(value = "printTest.htm")
	public void printTest(@RequestParam("path") String path, final HttpServletResponse response) throws Exception {
		LOGGER.debug("printTest path: "+path);
		//create a temp file
		String fullPath = FileUtil.buildFilePath(printTestFilePath, path);
		String[] splitFullPath = fullPath.split("\\.");
		File downloadFile = File.createTempFile(splitFullPath[0], splitFullPath[1]);
		if (s3.doesObjectExist(fullPath)) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = s3.getObject(fullPath).getObjectContent();
			BufferedInputStream bis = new BufferedInputStream(is);
		byte bytes[] = new byte[2048];
		int bytesRead;
		while ((bytesRead = bis.read(bytes)) != -1) {
			baos.write(bytes, 0, bytesRead);
		}
		bis.close();
		baos.close();

		byte zip[] = baos.toByteArray();

		ServletOutputStream sos = response.getOutputStream();
		response.setContentType("application/zip");

		response.setHeader("Cache-Control", "max-age=60, must-revalidate");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFile.getName() + "\"");
		response.addCookie(new Cookie("fileDownload", "true"));
		sos.write(zip);
		sos.flush();
	}
		FileUtils.deleteQuietly(downloadFile);
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "getPDFTickets.htm")
	public final void getPDFTickets(final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {

		LOGGER.trace("Entering the getPDFForRosterAndTest");
		OutputStream out = null;
		try {
			Long testSessionId = null;
			String selectedStudents = null;
			List<Long> studentIds = null;
			String selectedTestSessions = null;
			List<Long> testSessionIds = null;
			Boolean isAutoRegistered = Boolean.parseBoolean(request.getParameter("isAutoRegistered"));
			Boolean isInterim=Boolean.parseBoolean(request.getParameter("isInterim"));
			Boolean isPredictive = Boolean.parseBoolean(request.getParameter("isPredictive"));
			selectedTestSessions = request.getParameter("selectedTestSessions");
			if (selectedTestSessions != null && selectedTestSessions.length() > 0) {
				testSessionIds = new ArrayList<Long>();
				for (int i = 0; i < selectedTestSessions.split(",").length; i++) {
					testSessionIds.add(Long.parseLong(selectedTestSessions.split(",")[i]));
				}
			}
			
			if (request.getParameter("testSessionId") != null) {
				testSessionId = Long.parseLong(request.getParameter("testSessionId"));
				testSessionIds = new ArrayList<Long>(Arrays.asList(testSessionId));
			}
			//Adding a new way for interim Methods
			

			selectedStudents = request.getParameter("selectedStudents");
			if (selectedStudents != null && selectedStudents.length() > 0) {
				studentIds = new ArrayList<Long>();
				for (int i = 0; i < selectedStudents.split(",").length; i++) {
					studentIds.add(Long.parseLong(selectedStudents.split(",")[i]));
				}
			}

			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			// INFO: For a test with multiple sections at test level ticketing,
			// the ticket will be printed with ticket numbers at each section,
			// to enable the students to take the sections separately.
			List<TestSessionPdfDTO> testSessionPdfDTOList = new ArrayList<TestSessionPdfDTO>();
			
			if(testSessionIds!=null && testSessionIds.size()>0) {
				if (isInterim) {
					testSessionPdfDTOList = testSessionService.findTestSessionTicketDetailsByIdInterim(testSessionIds,
									isAutoRegistered, isPredictive, studentIds, userDetails);
				} else {
					testSessionPdfDTOList = testSessionService.findTestSessionTicketDetailsById(testSessionIds,
								isAutoRegistered, studentIds, userDetails);
				}
			}

			out = response.getOutputStream();
			response.setContentType("application/pdf");
			String fileName = "tickets" + ".pdf";
			if (!CollectionUtils.isEmpty(testSessionPdfDTOList)) {
				fileName = testSessionPdfDTOList.get(0).getTestSessionName() + ".pdf";
				LOGGER.debug("Found students for testsession {}, roster and test combination, size is {}.",
						testSessionPdfDTOList.get(0).getTestSessionName(), testSessionPdfDTOList.size());
			}
			response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
		    String serverPath = request.getSession().getServletContext().getRealPath("/");
			setupPDFGeneration(testSessionPdfDTOList, out, serverPath);
		} catch (NumberFormatException e) {
			LOGGER.error("Caught NumberFormatException in getPDFForRosterAndTest(): {}", e);
		} catch (FOPException e) {
			LOGGER.error("Caught {} in generatePDFForRoster() method.", e);
		} catch (IOException e) {
			LOGGER.error("Caught {} in generatePDFForRoster() method.", e);
		} catch (TransformerException e) {
			LOGGER.error("Caught {} in generatePDFForRoster() method.", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * This method sets up the student login credentials PDF.
	 * 
	 * @param studentsTests
	 *            List<StudentsTests>
	 * @param out
	 *            {@link OutputStream}
	 * @throws FOPException
	 *             FOPException
	 * @throws IOException
	 *             IOException
	 * @throws TransformerException
	 *             TransformerException
	 */
	private void setupPDFGeneration(List<TestSessionPdfDTO> testSessionPdfDTOList, OutputStream out, String serverPath)
			throws Exception {

		// convert that data into xml
		XStream xstream = new XStream();
		xstream.alias("tsPdfDTO", TestSessionPdfDTO.class);
		if(CollectionUtils.isEmpty(testSessionPdfDTOList)){
			TestSessionPdfDTO testSessionPdf = new TestSessionPdfDTO();
			testSessionPdfDTOList.add(testSessionPdf);
		}
		TraxSource source = new TraxSource(testSessionPdfDTOList, xstream);
		generatePdf(out, studentsTicketsXslFile.getFile(), source);
	}
	
	protected void generatePdf(OutputStream out, File foFile, TraxSource source) throws Exception {
		try {
			out = new BufferedOutputStream(out);
	
			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(foFile.getCanonicalPath()));
	
			FopFactory fopFactory = FopFactory.newInstance(reportConfigFile.getFile());
			fopFactory.getImageManager().getCache().clearCache();
			
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,out);
	
			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());
	
			// Start XSLT transformation and FOP processing
			transformer.transform(source, res);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * @param orgChildrenIds
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @param filters
	 * @return
	 */
	@RequestMapping(value = "getAutoRegistrationTestSessionsByUser.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getAutoRegistrationTestSessionsByUser(
			@RequestParam("rows") String limitCountStr, @RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn, @RequestParam("sord") String sortType,
			@RequestParam("filters") String filters, @RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("testingProgramId") Long testingProgramId, @RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeCourseId") Long gradeCourseId, @RequestParam("schoolOrgId") Long schoolOrgId,
			@RequestParam("showExpired") String showExpired,
			@RequestParam("includeCompletedTestSession") String includeCompletedTestSession) {
		LOGGER.trace("Entering the getAutoRegistrationTestSessionsByUser page for getting results");

		Integer currentPage = -1;
		Integer limitCount = -1;
		int totalCount = 1;
		JQGridJSONModel jqGridJSONModel = null;
		UserDetailImpl userDetails = null;
		Map<String, String> testSessionCriteriaMap = null;
		//String orgType = null;
		List<AutoRegisteredTestSessionDTO> autoRegisteredTestSessions = null;
		Long userId = null;
		
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();

		userId = userDetails.getUser().getId();

		testSessionCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(filters);
		
		Boolean showExpiredFlag = false;
		if(showExpired != null && showExpired.equalsIgnoreCase("true")) {
			showExpiredFlag = true;
		}
		/**
		 * Bishnupriya Nayak :US19343 : Test Management TestSession Completion
		 * Status Display
		 */
		Boolean includeCompletedTestSessionsFlag = false;
		if(includeCompletedTestSession != null && includeCompletedTestSession.equalsIgnoreCase("true")) {
			includeCompletedTestSessionsFlag = true;
		}
		 Boolean includeInProgressTestSession=true;
		
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		
		TimerUtil timerUtil = TimerUtil.getInstance();

		//if (orgType.equals(ORG_TYPECODE_SCHOOL)) {
		if(userDetails.getUser().isTeacher()) {
			if("KELPA2".equalsIgnoreCase(ap.getAbbreviatedname()) && gradeCourseId != null){
				autoRegisteredTestSessions = testSessionService.getKELPAAutoregisteredTestSessionForActiveRosters(
						userDetails, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount,
						testSessionCriteriaMap, userId, assessmentProgramId,
						ap == null ? null : ap.getAbbreviatedname(), testingProgramId, contentAreaId, gradeCourseId,
						schoolOrgId, false);
			} else if(CommonConstants.ASSESSMENT_PROGRAM_PLTW.equalsIgnoreCase(ap.getAbbreviatedname()) && gradeCourseId == null) {
				autoRegisteredTestSessions = testSessionService.getAutoregisteredTestSessionForActiveRostersPltw(
						userDetails, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount,
						testSessionCriteriaMap, userId, assessmentProgramId,
						ap == null ? null : ap.getAbbreviatedname(), testingProgramId,  schoolOrgId,
								Boolean.valueOf(showExpired), Boolean.valueOf(includeCompletedTestSession));
			} else {
				autoRegisteredTestSessions = testSessionService.getAutoregisteredTestSessionForActiveRosters(
						userDetails, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount,
						testSessionCriteriaMap, userId, assessmentProgramId,
						ap == null ? null : ap.getAbbreviatedname(), testingProgramId, contentAreaId, gradeCourseId,
						schoolOrgId, false);
			}
			
			timerUtil.resetAndLog(LOGGER, "Getting getAutoRegistrationTestSessionsByUser took ");

//			totalCount = testSessionService
//					.countAutoregisteredTestSessionForActiveRosters(userDetails,
//							testSessionCriteriaMap, userId,
//							assessmentProgramId, testingProgramId,
//							contentAreaId, gradeCourseId, schoolOrgId, false);
			if(!autoRegisteredTestSessions.isEmpty()) {
				totalCount = autoRegisteredTestSessions.get(0).getTotalRecords();
			}
		} else {
			if("KELPA2".equalsIgnoreCase(ap.getAbbreviatedname()) && gradeCourseId != null){
				autoRegisteredTestSessions = testSessionService
						.getKELPAAutoRegisteredTestSessionsByGradeCourseGradeBand(userDetails, currentSchoolYear, 
								sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount,
								testSessionCriteriaMap, assessmentProgramId,
								ap == null ? null : ap.getAbbreviatedname(), testingProgramId, contentAreaId,
								gradeCourseId, schoolOrgId, showExpiredFlag, includeCompletedTestSessionsFlag,
								includeInProgressTestSession);
			}else{
				
				autoRegisteredTestSessions = testSessionService.getAutoRegisteredTestSessionsForExtendedStatus(
						userDetails, currentSchoolYear, sortByColumn, sortType, (currentPage - 1) * limitCount,
						limitCount, testSessionCriteriaMap, assessmentProgramId,
						ap == null ? null : ap.getAbbreviatedname(), testingProgramId, contentAreaId, gradeCourseId,
						schoolOrgId, showExpiredFlag, includeCompletedTestSessionsFlag, includeInProgressTestSession);
			}
			
			timerUtil.resetAndLog(LOGGER, "Getting getAutoRegistrationTestSessionsByUser took ");

//			totalCount = testSessionService.countAutoRegisteredTestSessions(
//					userDetails, currentSchoolYear, testSessionCriteriaMap,
//					assessmentProgramId, testingProgramId,
//					contentAreaId, gradeCourseId, schoolOrgId, showExpiredFlag);
			if(!autoRegisteredTestSessions.isEmpty()) {
				totalCount = autoRegisteredTestSessions.get(0).getTotalRecords();
			}
		}
		timerUtil.resetAndLog(LOGGER, "Counting getAutoRegistrationTestSessionsByUser took ");
		
		/**
		 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15738 : Test
		 * Coordination - enhance Test Information PDF functionality Get media
		 * files associated to a test session.
		 */
		if(autoRegisteredTestSessions != null && autoRegisteredTestSessions.size()>0){
			List<Long> testSessionIds = new ArrayList<Long>(); 
			for (AutoRegisteredTestSessionDTO obj : autoRegisteredTestSessions){
				testSessionIds.add(obj.getTestSessionId());
			}
			
			if(testSessionIds != null && testSessionIds.size()>0){
				Map<Long, Map<String, String>> printFiles = testSessionService
						.getResourceByTestSessionId(testSessionIds);
				if(printFiles !=null && printFiles.size()>0) {
					for (AutoRegisteredTestSessionDTO obj : autoRegisteredTestSessions){
						if(printFiles.get(obj.getTestSessionId()) != null){
							String fileNames = printFiles.get(obj.getTestSessionId()).get("printtestfiles");
							if (fileNames != null) {
					 			String fileNamesList[] = fileNames.split(",");							
								StringBuilder sb = new StringBuilder("");
								for (String fileName : fileNamesList) {
									if (fileName != null && fileName.length() > 0) {
										// File f = new
										// File(printTestFilePath+File.separator+fileName);
										//if (f.exists()) {
											if (sb.length() > 0) {
												sb.append(",");
											}
											sb.append(fileName);
										//}
									}
								}
								obj.setPrintTestFiles(sb.toString());
							}
						}
						
					}					
				}
			}
		}

		jqGridJSONModel = TestSessionRosterJsonConverter.convertToAutoRegisteredTestSessionsJson(
				autoRegisteredTestSessions, totalCount, currentPage, limitCount);

		LOGGER.trace("Leaving the getAutoRegistrationTestSessionsByUser page.");
		return jqGridJSONModel;
	}
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15738 : Test Coordination
	 * - enhance Test Information PDF functionality Get count of students for
	 * large print, paper pencil and spanish prints based on their PNP data and
	 * given test session id.
	 * 
	 * @param testSessionId
	 * @return
	 */
	@RequestMapping(value = "getStudentPNPValuesByTestSession.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getStudentPNPValuesByTestSession(@RequestParam("testSessionId") Long testSessionId) {
		Map<String, Long> data = studentProfileService.getStudentPNPValuesByTestSession(testSessionId);		
		return "{\"largePrintBooklet\": \"" + data.get("largePrintBooklet") + "\"" + ", \"paperAndPencil\": \""
				+ data.get("paperAndPencil") + "\"" + ", \"Language\": \"" + data.get("Language") + "\"}";
	}
	
	@RequestMapping(value = "getAutoRegisteredPrintFiles.htm", method = RequestMethod.GET)
	@ResponseBody
	public String getAutoRegisteredPrintFiles(@RequestParam("testSessionId") Long testSessionId) {
		String fileNames= testSessionService.getAutoPrintTestFiles(testSessionId);
		if (fileNames != null) {
 			String fileNamesList[] = fileNames.split(",");
		
			StringBuilder sb = new StringBuilder("");
			for (String fileName : fileNamesList) {
				if (fileName != null && fileName.length() > 0) {
					//check if the file exists in s3
					if (s3.doesObjectExist(printTestFilePath + File.separator + fileName)) {
						if (sb.length() > 0) {
							sb.append(",");
						}
						sb.append(fileName);
					}
				}
			}
			return  "{\"files\": \""+sb.toString()+"\"}";
		}
		return "{\"files\": null}";
	}

	/**
	 * @param fileterAttribute
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "getARTSAutoCompleteData.htm", method = RequestMethod.GET)
	public final @ResponseBody Set<String> getAutoRegisteredTSAutoCompleteData(
			@RequestParam("filterAttribute") String filterAttribute, @RequestParam("term") String term,
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("testingProgramId") Long testingProgramId, @RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeCourseId") Long gradeCourseId, @RequestParam("schoolOrgId") Long schoolOrgId) {
		LOGGER.trace("Entering the getPDBrowseModulesAutoCompleteData() method");

		Set<String> autoCompleteValues = new HashSet<String>();
		UserDetailImpl userDetails = null;
		Map<String, String> studentRosterCriteriaMap = null;
		List<AutoRegisteredTestSessionDTO> autoRegisteredTestSessions = null;

		userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		studentRosterCriteriaMap = recordBrowserJsonUtil.constructAutoCompleteFilterCriteria(filterAttribute, term);
		
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);

		autoRegisteredTestSessions = testSessionService.getAutoRegisteredTestSessions(userDetails, null,
				filterAttribute, null, null, null, studentRosterCriteriaMap, assessmentProgramId,
				ap == null ? null : ap.getAbbreviatedname(), testingProgramId, contentAreaId, gradeCourseId,
				schoolOrgId, null);

		for (AutoRegisteredTestSessionDTO autoRegisteredTestSession : autoRegisteredTestSessions) {
			if(filterAttribute.equals("testSessionName")) {
				autoCompleteValues.add(autoRegisteredTestSession.getTestSessionName());
			} else if(filterAttribute.equals("contentAreaName")) {
				autoCompleteValues.add(autoRegisteredTestSession.getContentAreaName());
			} else if(filterAttribute.equals("gradeCourseName")) {
				autoCompleteValues.add(autoRegisteredTestSession.getGradeCourseName());
			}
		}

		LOGGER.trace("Leaving the getPDBrowseModulesAutoCompleteData() method");
		return autoCompleteValues;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "getARTSDropdownData.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Set<String>> getAutoRegisteredTSDropdownData() {
		LOGGER.trace("Entering the getAutoRegisteredTSDropdownData() method");

		Map<String, Set<String>> browseModuleValues = new HashMap<String, Set<String>>();
		SortedSet<String> contentAreaNames = new TreeSet<String>();
		SortedSet<String> gradeCourseNames = new TreeSet<String>(new Comparator<String>() {
					public int compare(String a, String b) {
						// Check for the numeric values.
						if (a.matches("[0-9]+") && b.matches("[0-9]+")) {
					return Integer.valueOf(a).compareTo(Integer.valueOf(b));
						} else {
							return a.compareTo(b);
						}
					}
				});
		List<AutoRegisteredTestSessionDTO> autoRegisteredTestSessions = null;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		autoRegisteredTestSessions = testSessionService.getAutoRegisteredTestSessions(userDetails, null, null, null,
				null, null, (new HashMap<String, String>()), null, null, null, null, null, null, null);

		for (AutoRegisteredTestSessionDTO autoRegisteredTestSession : autoRegisteredTestSessions) {
			contentAreaNames.add(autoRegisteredTestSession.getContentAreaName());
			if(null != autoRegisteredTestSession.getGradeCourseName()) {
				gradeCourseNames.add(autoRegisteredTestSession.getGradeCourseName());
			}
		}

		browseModuleValues.put("contentAreaNames", contentAreaNames);
		browseModuleValues.put("gradeCourseNames", gradeCourseNames);

		LOGGER.trace("Leaving the getAutoRegisteredTSDropdownData() method");
		return browseModuleValues;
	}

	/**
	 * @param orgChildrenIds
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @param testSessionId
	 * @param filters
	 * @return
	 */
	@RequestMapping(value = "getAutoRegisteredTSStudents.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getAutoRegisteredTSStudents(
			@RequestParam("orgChildrenIds") String[] orgChildrenIds, @RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("testSessionId") Long testSessionId,
			@RequestParam("filters") String filters) {
		LOGGER.trace("Entering the getAutoRegisteredTSStudents page for getting results");

		Integer currentPage = -1;
		Integer limitCount = -1;
		Integer totalCount = 1;
		JQGridJSONModel jqGridJSONModel = null;
		String orderByclause = "";

		UserDetailImpl userDetails = null;
		Map<String, String> studentRosterCriteriaMap = null;
		List<AutoRegisteredStudentsDTO> autoRegisteredStudents = null;

		// Temporary fix for sorting
		if (sortByColumn.equals("stateStudentIdentifier")) {
			orderByclause = "substring(statestudentidentifier, '^[0-9]+')::bigint " + sortType
					+ ", substring(statestudentidentifier, '[^0-9]*$') " + sortType;
		} else {
			orderByclause = sortByColumn + ParsingConstants.BLANK_SPACE + sortType;
		}

		currentPage = Integer.parseInt(page);
		limitCount = Integer.parseInt(limitCountStr);
		userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long organizationId = userDetails.getUser().getCurrentOrganizationId();
		studentRosterCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(filters);

		TimerUtil timerUtil = TimerUtil.getInstance();
		AssessmentProgram ap = assessmentProgramService.findByTestSessionId(testSessionId);
		
		autoRegisteredStudents = enrollmentService.getAutoRegisteredTSStudents(null,
				NumericUtil.convert(orgChildrenIds), userDetails, orderByclause, (currentPage - 1) * limitCount,
				limitCount, studentRosterCriteriaMap, testSessionId, ap == null ? null : ap.getAbbreviatedname(), organizationId);
		timerUtil.resetAndLog(LOGGER, "Getting student records for test session took ");

		if(!autoRegisteredStudents.isEmpty()) {
			totalCount = autoRegisteredStudents.get(0).getTotalRecords();
		}
//		totalCount = enrollmentService.countAutoRegisteredTSStudents(null,
//				NumericUtil.convert(orgChildrenIds), userDetails,
//				studentRosterCriteriaMap, testSessionId);
		timerUtil.resetAndLog(LOGGER, "Counting student records for test session took ");

		jqGridJSONModel = TestCoordinationStudentsJsonConverter.convertToStudentsJson(autoRegisteredStudents,
				totalCount, currentPage, limitCount);

		LOGGER.trace("Leaving the getAutoRegisteredTSStudents page.");
		return jqGridJSONModel;
	}

	/**
	 * @param filterAttribute
	 * @param testSessionId
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "getARStudentsAutoCompleteData.htm", method = RequestMethod.GET)
	public final @ResponseBody Set<String> getARStudentsAutoCompleteData(
			@RequestParam("filterAttribute") String filterAttribute, @RequestParam("testSessionId") Long testSessionId,
			@RequestParam("term") String term) {
		LOGGER.trace("Entering the getARStudentsAutoCompleteData() method");

		Set<String> autoCompleteValues = new HashSet<String>();
		List<AutoRegisteredStudentsDTO> autoRegisteredStudents = null;
		Map<String, String> studentRosterCriteriaMap = null;
		List<Long> orgChildrenIds = null;
		List<Organization> orgChildren = null;
		User user = null;
		UserDetailImpl userDetails = null;

		userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = userDetails.getUser();
		Long organizationId = user.getCurrentOrganizationId();
		orgChildren = organizationService.getAllChildren(user.getOrganizationId(), true);
		orgChildrenIds = AARTCollectionUtil.getIds(orgChildren);

		studentRosterCriteriaMap = recordBrowserJsonUtil.constructAutoCompleteFilterCriteria(filterAttribute, term);
		
		AssessmentProgram ap = assessmentProgramService.findByTestSessionId(testSessionId);

		autoRegisteredStudents = enrollmentService.getAutoRegisteredTSStudents(null, orgChildrenIds, userDetails,
				filterAttribute, null, null, studentRosterCriteriaMap, testSessionId,
				ap == null ? null : ap.getAbbreviatedname(), organizationId);

		for (AutoRegisteredStudentsDTO autoRegisteredStudent : autoRegisteredStudents) {
			if (filterAttribute.equalsIgnoreCase("stateStudentIdentifier")) {
				autoCompleteValues.add(autoRegisteredStudent.getStudent().getStateStudentIdentifier());
			} else if (filterAttribute.equalsIgnoreCase("legalFirstName")) {
				autoCompleteValues.add(autoRegisteredStudent.getStudent().getLegalFirstName());
			} else if (filterAttribute.equalsIgnoreCase("legalLastName")) {
				autoCompleteValues.add(autoRegisteredStudent.getStudent().getLegalLastName());
			} else if (filterAttribute.equalsIgnoreCase("rosterName")) {
				autoCompleteValues.add(autoRegisteredStudent.getStudent().getStateStudentIdentifier());
			}
		}

		LOGGER.trace("Leaving the getARStudentsAutoCompleteData() method");
		return autoCompleteValues;
	}


	/**
	 * @param categoryCode
	 */
	public final @ResponseBody Boolean getITIconfiguration(@RequestParam("categoryCode") String categoryCode) {
		Boolean status = categoryService.getStatusITI(categoryCode);
		return status;
	}

	/**
	 * @param filterAttribute
	 * @param testSessionId
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "saveSpecialCircumstance.htm", method = RequestMethod.POST)
	public final @ResponseBody String saveSpecialCircumstance(@RequestParam("testSessionId") String testSessionId,
			@RequestParam("studentId") String studentId,
			@RequestParam("specialCircumstanceValue") String  specialCircumstanceValue,
			@RequestParam("requireConfirmation") boolean requireConfirmation,
			@RequestParam("specialCircumstanceApprovalValue") String specialCircumstanceApprovalValue,
			@RequestParam("isApprovalAction") boolean isApprovalAction) {
		LOGGER.trace("Entering the saveSpecialCircumstance() method");
		StringBuilder returnCode = new StringBuilder();
		TestSession testSession = testSessionService.findByPrimaryKey(Long.parseLong(testSessionId));
		List<Stage> studentTestStages = studentsTestsService.getAllStagesByTestSession(Long.parseLong(testSessionId),
				Long.parseLong(studentId), testSession.getOperationalTestWindowId());
		Category pendingTestStatus = categoryService.selectByCategoryCodeAndType("pending", "STUDENT_TEST_STATUS");
		boolean isNewRecord = false;
		List<StudentSpecialCircumstance> toUpdated = new ArrayList<StudentSpecialCircumstance>();
		// per US17380 update all existing stages with SC code only if it is not
		// an approval action
		if(!isApprovalAction){
			for(Stage stage: studentTestStages){
				if(stage.getSessionId() != null) {
					List<StudentsTests> studentTests = studentsTestsService
							.findByTestSessionAndStudent(stage.getSessionId(), Long.parseLong(studentId));
					if(studentTests != null && !studentTests.isEmpty()){
						StudentsTests studentTest = studentTests.get(0);
						Long studentTestId = studentTest.getId();
						// Do not update pending test stages
						if(!studentTest.getStatus().equals(pendingTestStatus.getId())){
							
							List<StudentSpecialCircumstance> existingrecord = studentSpecialCircumstanceService
									.selectActiveByStudentTestId(studentTestId, true);
							
							StudentSpecialCircumstance record = new StudentSpecialCircumstance();
							if(StringUtils.isNotBlank(specialCircumstanceValue)){
								record.setSpecialCircumstanceId(Long.parseLong(specialCircumstanceValue));
							} else {
								record.setSpecialCircumstanceId(null);
							}
							record.setStudentTestid(studentTestId);
							
							if(requireConfirmation){
								Category sscStatus = categoryService.selectByCategoryCodeAndType("PENDING",
										"SPECIAL CIRCUMSTANCE STATUS");
								record.setStatus(sscStatus.getId());
							} else {
								Category sscStatus = categoryService.selectByCategoryCodeAndType("SAVED",
										"SPECIAL CIRCUMSTANCE STATUS");
								record.setStatus(sscStatus.getId());
							}
							
							if(existingrecord != null && existingrecord.size() >0){
								Long existingValue= existingrecord.get(0).getSpecialCircumstanceId();
								String value = existingValue == null ? "" : existingValue.toString();
								if (StringUtils.isBlank(specialCircumstanceValue)
										|| specialCircumstanceValue != value) {
									// Per DE12779 do not update existing
									// records (Only update if it is an update
									// operation).
									toUpdated.add(record);
								}
							}else{
								studentSpecialCircumstanceService.insert(record);
								returnCode.append("created");
								if(testSessionId.equals(stage.getSessionId().toString())){
									isNewRecord = true;
								}
							}
							
							// Clear all stages which belong to the selected
							// operational test window.
							if (StringUtils.isBlank(specialCircumstanceValue)
									|| StringUtils.isBlank(specialCircumstanceApprovalValue)) {
								deleteSpecialCircumstance(stage.getSessionId().toString(), studentId,
										specialCircumstanceValue, specialCircumstanceApprovalValue,
									requireConfirmation);
							}
						}
					}
					
				}
			}
			// Per DE12779 do not update existing records (Only update if it is
			// an update operation).
			for(StudentSpecialCircumstance updateRecord: toUpdated){
				if(!isNewRecord){
					studentSpecialCircumstanceService.updateByExampleSelective(updateRecord);
					returnCode.append("updated");
				}
			}
		}
		
		if(isApprovalAction && specialCircumstanceApprovalValue != null){
			saveSpecialCircumstanceApproval(testSessionId, studentId, specialCircumstanceApprovalValue);
		}
		
		LOGGER.trace("Leaving the saveSpecialCircumstance() method");
		return returnCode.toString();
	}

	private String saveSpecialCircumstanceApproval(String testSessionId, String studentId,
			String  specialCircumstanceApprovalValue) {
		LOGGER.trace("Entering the saveSpecialCircumstanceApproval() method");
		User user = null;
		UserDetailImpl userDetails = null;
		StringBuilder newStatus = new StringBuilder();
		List<StudentSpecialCircumstance> selectedRecord = null;
		List<StudentsTests> selectedStudentTests = studentsTestsService
				.findByTestSessionAndStudent(Long.parseLong(testSessionId), Long.parseLong(studentId));
		if(selectedStudentTests != null && !selectedStudentTests.isEmpty()){
			StudentsTests selectedStudentTest = selectedStudentTests.get(0);
			selectedRecord = studentSpecialCircumstanceService.selectActiveByStudentTestId(selectedStudentTest.getId(),
					true);
		}
		TestSession testSession = testSessionService.findByPrimaryKey(Long.parseLong(testSessionId));
		List<Stage> studentTestStages = studentsTestsService.getAllStagesByTestSession(Long.parseLong(testSessionId),
				Long.parseLong(studentId), testSession.getOperationalTestWindowId());
		Category pendingTestStatus = categoryService.selectByCategoryCodeAndType("pending", "STUDENT_TEST_STATUS");
		Category sscStatus = null;
		for(Stage stage: studentTestStages){
			if(stage.getSessionId() != null) {
				List<StudentsTests> studentTests = studentsTestsService
						.findByTestSessionAndStudent(stage.getSessionId(), Long.parseLong(studentId));
				if(studentTests != null && !studentTests.isEmpty()){
					StudentsTests studentTest = studentTests.get(0);
					Long studentTestId = studentTest.getId();
					// Do not update pending test stages
					if(!studentTest.getStatus().equals(pendingTestStatus.getId())){
						List<StudentSpecialCircumstance> existingrecord = studentSpecialCircumstanceService
								.selectActiveByStudentTestId(studentTestId, true);

						StudentSpecialCircumstance record = new StudentSpecialCircumstance();
						record.setStudentTestid(studentTestId);
						
						userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
								.getPrincipal();
						user = userDetails.getUser();
						record.setApprovedBy(user.getId());
						
						if(StringUtils.isNotBlank(specialCircumstanceApprovalValue)){
							sscStatus = categoryService
									.selectByPrimaryKey(Long.valueOf(specialCircumstanceApprovalValue));
							record.setStatus(sscStatus.getId());
						}

						// Do not update if selected SC code is different than
						// this stage's SC code.
						if(existingrecord != null && existingrecord.size() >0){
							if (selectedRecord != null && !selectedRecord.isEmpty() && selectedRecord.get(0)
									.getSpecialCircumstanceId() == existingrecord.get(0).getSpecialCircumstanceId()) {
								studentSpecialCircumstanceService.updateByExampleSelective(record);
								if(sscStatus != null && StringUtils.isBlank(newStatus)){
									newStatus.append(sscStatus.getCategoryName());
								}
							}
						} else {
							LOGGER.warn("Trying to update non existent record for test session: " + testSessionId 
								+ " student: "+ studentId + " value: " + specialCircumstanceApprovalValue);
						}
					}
				}
			}
		}
		
		LOGGER.trace("Leaving the saveSpecialCircumstanceApproval() method");
		// return the status name to render corresponding icons.
		return newStatus.toString();
	}

	private String deleteSpecialCircumstance(String testSessionId, String studentId, String specialCircumstanceValue,
			String specialCircumstanceApprovalValue, boolean requireConfirmation) {
		LOGGER.trace("Entering the deleteSpecialCircumstance() method");
		String returnCode = "notdeleted";
		// Updating only the test session for which the SC code removal is
		// selected
		List<StudentsTests> studentTests = studentsTestsService
				.findByTestSessionAndStudent(Long.parseLong(testSessionId), Long.parseLong(studentId));
		for(StudentsTests studentsTest : studentTests){
			Long studentTestId = studentsTest.getId();
			Category sscStatus = null;
			if(StringUtils.isBlank(specialCircumstanceApprovalValue)){
				if(requireConfirmation){
					sscStatus = categoryService.selectByCategoryCodeAndType("PENDING", "SPECIAL CIRCUMSTANCE STATUS");
				} else {
					sscStatus = categoryService.selectByCategoryCodeAndType("SAVED", "SPECIAL CIRCUMSTANCE STATUS");
				}
				
				// Update update empty value in status column
				studentSpecialCircumstanceService.deleteApproval(studentTestId, sscStatus.getId());
				returnCode = "deleted";
			}
			
			if (StringUtils.isBlank(specialCircumstanceValue)
					&& StringUtils.isBlank(specialCircumstanceApprovalValue)) {
				studentSpecialCircumstanceService.deleteByStudentTestId(studentTestId);
				returnCode = "deleted";
			}
		}
		LOGGER.trace("Leaving the deleteSpecialCircumstance() method");
		return returnCode;
	}
	
	@RequestMapping(value = "checkTestSessionDeletable.htm", method = RequestMethod.POST)
	public final @ResponseBody String checkTestSessionDeletable(@RequestParam("testSessionId") String testSessionId) {
		LOGGER.trace("Entering the checkTestSessionDeletable() method");

		String returnMsg = "Not_Deletable";
		if(testSessionId != null && testSessionId.trim().length() > 0) {			
			Long testSessionID = Long.parseLong(testSessionId);
			if(testSessionService.isTestSessionDeletable(testSessionID)) { 
				returnMsg = "Deletable";
			} 
		}
		
		LOGGER.trace("Leaving the checkTestSessionDeletable() method");
		return "{\"result\": \""+ returnMsg +"\"}";
	}  
	
	@RequestMapping(value = "deactivateTestSession.htm", method = RequestMethod.POST)
	public final @ResponseBody String deactivateTestSession(@RequestParam("testSessionId") String testSessionId) {
		LOGGER.trace("Entering the deactivateTestSession() method");

		testSessionService.deactivateTestSession(Long.parseLong(testSessionId));
		
		LOGGER.trace("Leaving the deactivateTestSession() method");
		return "{\"result\": \"deactivated\"}";
	}
	
	@RequestMapping(value = "getDailyAccessCodesTestDays.htm", method = RequestMethod.POST)
	public final @ResponseBody List<String> getTestDaysForDailyAccessCodes(){
		LOGGER.trace("Entering getTestDayForDailyAccessCodes");
		List<String> testDays = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE MM/dd/yyyy");
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("US/Central"));
		
		int hour = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);
		int am_pm = calendar.get(Calendar.AM_PM);
		boolean showNextTestDay = false;
		
		// If the user requested time is before 2:30 PM local time, show only
		// current day for selection, if the requested time is on or after 2:30
		// PM then show current day and next day value in TEST DAY drop down.
		if(am_pm > 0){
			if(hour > 2 || (hour == 2 && minutes >=30)){
				showNextTestDay = true;
			}
		}		
		
		testDays.add(sdf.format(calendar.getTime()));
		if(showNextTestDay){
			calendar.add(Calendar.DATE, 1);
			testDays.add(sdf.format(calendar.getTime()));
		}			
		
		LOGGER.trace("Leaving getTestDayForDailyAccessCodes");
		return testDays;
	}
	
	@RequestMapping(value = "getDailyAccessCodes.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getDailyAccessCodes(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("filters") String filters,
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("testDayValue") String testDayVal) {
		LOGGER.trace("Entering the getDailyAccessCodes page for getting results");

		Integer currentPage = -1;
		Integer limitCount = -1;
		int totalCount = 1;
		JQGridJSONModel jqGridJSONModel = null;
		List<DailyAccessCode> dailyAccessCode = null;
		Map<String, String> dacCriteriaMap = null;
		dacCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(filters);
		
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		String assessmentProgramCode =userDetails.getUser().getCurrentAssessmentProgramName();
		boolean includeGradeBand=false;
		
		if(StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
			includeGradeBand=true ;
			}
		Long userId=null;
		Long currentSchoolyear=user.getContractingOrganization().getCurrentSchoolYear();
		if(user.isTeacher() || user.isProctor()) {
			userId=user.getId();
		}

		dailyAccessCode = testSessionService.getDailyAccessCodes(assessmentProgramId, testDayVal, sortByColumn,
				sortType, dacCriteriaMap, (currentPage - 1) * limitCount, limitCount, user.getContractingOrgId(),includeGradeBand, userId, currentSchoolyear);
		totalCount = testSessionService.getCountDailyAccessCode(assessmentProgramId, testDayVal,
				user.getContractingOrgId(),includeGradeBand,userId, currentSchoolyear);

		jqGridJSONModel = DailyAccessCodesDTOJsonConverter.convertToDailyAccessCodeJson(dailyAccessCode, totalCount,
				currentPage, limitCount);

		LOGGER.trace("Leaving the getDailyAccessCodes page.");
		return jqGridJSONModel;
	}
	
	
	@RequestMapping(value = "downloadDailyAccessCodesAsPDF.htm")
	public final void downloadDailyAccessCodesAsPDF(@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("testDay") Date testDay, @RequestParam("contentAreaGradeList") String contentAreaGradeList,
			final HttpServletResponse response) throws Exception {
		List<Map<Long, Long>> contentAreaGrades = new ArrayList<Map<Long, Long>>();
	    ObjectMapper om = new ObjectMapper();
		List<JsonNode> elements = om.readValue(contentAreaGradeList, new TypeReference<List<JsonNode>>() {
		});

	    for(JsonNode node : elements) {
	    	if(node.findValue("contentAreaId") != null && node.findValue("gradeCourseId") !=null) {
		    	Map<Long, Long> row = new HashMap<Long, Long>();
		    	row.put(node.findValue("contentAreaId").asLong(), node.findValue("gradeCourseId").asLong());
		    	contentAreaGrades.add(row);
	    	}
	    	if(node.findValue("contentAreaId") != null && node.findValue("gradeBandId") !=null) {
		    	Map<Long, Long> row = new HashMap<Long, Long>();
		    	row.put(node.findValue("contentAreaId").asLong(), node.findValue("gradeBandId").asLong());
		    	contentAreaGrades.add(row);
	    	}
	    }
	    List<DailyAccessCode> accessCodes = null;
	    if(contentAreaGrades.isEmpty()) {
	    	accessCodes = new ArrayList<DailyAccessCode>();
	    } else {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();
			boolean isPLTW = false;
			String currentAssessmentProgramCode	=userDetails.getUser().getCurrentAssessmentProgramName();
			if(StringUtils.equalsIgnoreCase(currentAssessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
				isPLTW=true;
			}
			accessCodes = studentsTestsService.getAccessCodes(assessmentProgramId, testDay, contentAreaGrades,
					user.getContractingOrgId(),isPLTW);		
			}
	    OutputStream out = response.getOutputStream();
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition","attachment; filename=\"Kite_SECURE_Daily_Access_Codes.pdf\"");
	    String serverPath = context.getRealPath("/");
	    
	    dacPdfGenerator.generatePdf(accessCodes, out, serverPath, TimeZone.getTimeZone("US/Central"));
	    return;
	}
	
	@RequestMapping(value = "downloadDailyAccessCodesAsCSV.htm")
	public final void downloadDailyAccessCodesAsCSV(@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("testDay") Date testDay, @RequestParam("contentAreaGradeList") String contentAreaGradeList,
			final HttpServletResponse response) throws Exception {
		List<Map<Long, Long>> contentAreaGrades = new ArrayList<Map<Long, Long>>();
	    ObjectMapper om = new ObjectMapper();
		List<JsonNode> elements = om.readValue(contentAreaGradeList, new TypeReference<List<JsonNode>>() {
		});

	    for(JsonNode node : elements) {
	    	if(node.findValue("contentAreaId") != null && node.findValue("gradeCourseId") !=null) {
		    	Map<Long, Long> row = new HashMap<Long, Long>();
		    	row.put(node.findValue("contentAreaId").asLong(), node.findValue("gradeCourseId").asLong());
		    	contentAreaGrades.add(row);
	    	}
	    }
	    List<DailyAccessCode> accessCodes = null;
	    if(contentAreaGrades.isEmpty()) {
	    	accessCodes = new ArrayList<DailyAccessCode>();
	    } else {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();
			boolean isPLTW = false;
			String currentAssessmentProgramCode	=userDetails.getUser().getCurrentAssessmentProgramName();
			if(StringUtils.equalsIgnoreCase(currentAssessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)) {
				isPLTW=true;
			}
			accessCodes = studentsTestsService.getAccessCodes(assessmentProgramId, testDay, contentAreaGrades,
					user.getContractingOrgId(),isPLTW);
	    }
	    
	    OutputStream out = response.getOutputStream();
	    String fileName= "Kite_SECURE_Daily_Access_Codes.csv";
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename="+fileName); 
		
		dacCSVGenerator.generateCSV(accessCodes, out);
        return;
	}
	
}
