package edu.ku.cete.controller.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.configuration.SessionRulesConfiguration;
import edu.ku.cete.configuration.StudentsTestSectionsStatusConfiguration;
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.QCTestCollectionDTO;
import edu.ku.cete.domain.RubricReportDto;
import edu.ku.cete.domain.RubricScore;
import edu.ku.cete.domain.SpecialCircumstance;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsResponsesReportDto;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.TestCollectionDTO;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.enrollment.StudentTestSessionDto;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.AsynchronousProcessService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentSpecialCircumstanceService;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.service.studentsession.TestCollectionsSessionRulesService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.json.RecordBrowserJsonUtil;
import edu.ku.cete.util.json.StudentRosterJsonConverter;
import edu.ku.cete.util.json.TestCollectionDTOJsonConverter;
import edu.ku.cete.web.AssessmentProgramDto;

/**
 * Controller class for setupTestSession that handles requests from different
 * tabs while setting-up the test session.
 * 
 * @author vittaly
 *
 */

@Controller
public class SetupTestSessionController {

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(SetupTestSessionController.class);

	/**
	 * TEST_SESSION_JSP
	 */
	private static final String SETUP_TEST_SESSION_JSP = "/test/setupTestSession";

	/**
	 * permissionUtil.
	 */
	@Autowired
	private PermissionUtil permissionUtil;

	/**
	 * enrollmentService
	 */
	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private TestSessionService testSessionService;

	/**
	 * categoryService
	 */
	@Autowired
	CategoryService categoryService;

	/**
	 * studentsTestsService
	 */
	@Autowired
	private StudentsTestsService studentsTestsService;

	@Autowired
	private AsynchronousProcessService asynchronousProcessService;
	/**
	 * orgAssessProgService
	 */
	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;

	/**
	 * testingProgramService
	 */
	@Autowired
	TestingProgramService testingProgramService;

	/**
	 * testCollectionService
	 */
	@Autowired
	private TestCollectionService testCollectionService;

	/**
	 * testStatusConfiguration
	 */
	@Autowired
	private TestStatusConfiguration testStatusConfiguration;

	/**
	 * sessionRulesConfiguration.
	 */
	@Autowired
	private SessionRulesConfiguration sessionRulesConfiguration;

	/**
	 * testCollectionsSessionRulesService
	 */
	@Autowired
	private TestCollectionsSessionRulesService testCollectionsSessionRulesService;

	/**
	 * gradeCourseService
	 */
	@Autowired
	private GradeCourseService gradeCourseService;

	/**
	 * contentAreaService
	 */
	@Autowired
	private ContentAreaService contentAreaService;

	@Autowired
	private RosterService rosterService;
	/**
	 * assessmentService
	 */
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
    private AssessmentProgramService assessmentProgramService;

	/**
	 * TEST_SESSION_STATUS_UNUSED
	 */
	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;

	/**
	 * TEST_SESSION_STATUS_TYPE
	 */
	@Value("${testsession.status.type}")
	private String TEST_SESSION_STATUS_TYPE;

	/**
	 * recordBrowserJsonUtil
	 */
	@Autowired
	private RecordBrowserJsonUtil recordBrowserJsonUtil;

	/**
	 * recordBrowserJsonUtil
	 */
	@Autowired
	private StudentsTestsStatusConfiguration studentsTestsStatusConfiguration;

	/**
	 * TASK_TYPE_CODE
	 */
	@Value("${setupTestSession.assessment.tasktype.code}")
	private String TASK_TYPE_CODE;

	/**
	 * Select students dropdown
	 */
	// DE622 - remove 1000 student limit for setup test sessions
	// @Value("${setupTestSession.dropdownsStudents.simplified.limit}")
	// private Integer limitForDropdowns;

	/**
	 * User organization for DLM
	 */
	@Value("${user.organization.DLM}")
	private String USER_ORG_DLM;

	/**
	 * studentsTestSectionsStatusConfiguration.
	 */
	@Autowired
	private StudentsTestSectionsStatusConfiguration studentsTestSectionsStatusConfiguration;

	/**
	 * studentsResponsesService
	 */
	@Autowired
	private StudentsResponsesService studentsResponsesService;

	/**
	 * studentReportService
	 */
	@Autowired
	private StudentReportService studentReportService;

	/**
	 * testService
	 */
	@Autowired
	private TestService testService;

	/**
	 * studentService
	 */
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentSpecialCircumstanceService studentSpecialCircumstanceService;
	
	private ObjectMapper mapper = new ObjectMapper();

	/**
	 *
	 * @return {@link ModelAndView}
	 */
	@SuppressWarnings("serial")
	@RequestMapping(value = "setupTestSession.htm")
	public final ModelAndView view(@RequestParam(value = "testSessionId", required = false) final Long testSessionId,
			@RequestParam(value = "testSessionName", required = false) String testSessionName,
			@RequestParam(value = "rosterId", required = false) Long rosterId,
			@RequestParam(value = "testCollectionId", required = false) final Long testCollectionId,
			@RequestParam(value = "source", required = false) final String testSessionSource,
			@RequestParam(value = "highStakesFlag", required = false) final String highStakesFlag,
			@RequestParam(value = "testingProgram", required=false)String testingProgramName,
			@RequestParam(value = "qcTest", required=false)String qcTest) {
		LOGGER.trace("Entering the view() method");
		ModelAndView mav = new ModelAndView(SETUP_TEST_SESSION_JSP);
		// TODO US12186
		// 1. testSessionId is treated like an optional field.Verify
		// functionality.
		// 2. set the testSessionId as a hidden field in the setup test session
		// page.
		// 3. Refer Node response report page to find out how parameters are
		// being passed during the JqGrid call.
		// 4. this hidden parameter testSessionId need to be passed in the the
		// jqgrid call to pull students on the selectStudents tab.
		// 5. Delete the TODOs above as and when completing it.
		if (testSessionId != null && testSessionId != -1) {
			LOGGER.debug("Test Session id is being passed " + testSessionId);
		}

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		List<Long> systemTestCollection = testCollectionsSessionRulesService
				.selectByTestCollectionIdsAndSessionRuleId((new ArrayList<Long>() {
					{
						add(testCollectionId);
					}
				}), sessionRulesConfiguration.getSystemEnrollmentCategory().getId());

		List<SpecialCircumstance> specialCircumstanceList = studentSpecialCircumstanceService.getCEDSByUserState(user.getContractingOrgId());
		List<String> approvalCodes = new ArrayList<String>(Arrays.asList("APPROVED", "NOT_APPROVED", "PENDING_FURTHER_REVIEW"));
		Map<String, Category> specialCircumstanceStatusList = categoryService.selectByCategoryTypeAndCategoryCodes("SPECIAL CIRCUMSTANCE STATUS", approvalCodes);
		boolean hasSpecialCircumstanceApprovalPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getHighStakesSplCircumstanceApprovalPermission());
		// if a state's special circumstance requires confirmation then show the approval column. At present only
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
		
		Collection<Category> specialCircumstanceApprovalList = specialCircumstanceStatusList.values();
		
		
		LOGGER.trace("Getting AssessmentPrograms for user's current organization");
		mav.addObject("assessmentPrograms", getAssessmentProgramsForUser(user));
		mav.addObject("current", "testSession");
		mav.addObject("testSessionId", testSessionId);
		mav.addObject("user", user);
		mav.addObject("testSessionName", testSessionName);
		mav.addObject("testSessionSource", testSessionSource);
		mav.addObject("rosterId", rosterId);
		if (getRubricForTestSession(testSessionId, rosterId))
			mav.addObject("rubricFlag", true);
		else
			mav.addObject("rubricFlag", false);

		if (highStakesFlag != null && highStakesFlag.equalsIgnoreCase("Y")) {
			mav.addObject("highStakesFlag", true);
		} else {
			mav.addObject("highStakesFlag", false);
		}

		if (systemTestCollection != null && CollectionUtils.isNotEmpty(systemTestCollection)) {
			mav.addObject("testCollectionSystemFlag", true);
		} else {
			mav.addObject("testCollectionSystemFlag", false);
		}

		boolean qcPermission =false;
		if(qcTest!=null && qcTest.equals("QC")) {
			qcPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),RestrictedResourceConfiguration.getQualityControlCompletePermission());
			LOGGER.debug("hasQCPermission : " + qcPermission);
		}
		mav.addObject("hasQCPermission", qcPermission);
		mav.addObject("specialCircumstanceList", specialCircumstanceList);
		mav.addObject("specialCircumstanceApprovalList",specialCircumstanceApprovalList);
		mav.addObject("hasSpecialCircumstanceApprovalPermission", hasSpecialCircumstanceApprovalPermission);
		mav.addObject("stateHasRestrictedCodes", stateHasRestrictedCodes);
		mav.addObject("testingProgramName", testingProgramName);
		LOGGER.trace("Leaving the view() method");
		return mav;
	}

	/**
	 * @param user
	 *            {@link User}
	 * @return List<AssessmentProgram>
	 */
	private List<AssessmentProgramDto> getAssessmentProgramsForUser(final User user) {
		// List<Organization> orgs = userService.getOrganizations(user.getId());
		// TODO change this from the current organization to a unique list of
		// the current organization and its parents assessment
		// programs.
		Organization current = user.getOrganization();
		// List<AssessmentProgram> assessmentPrograms = new
		// ArrayList<AssessmentProgram>();
		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();

		if (current != null) {
			List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(current.getId());
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

	static Comparator<TestingProgram> tstPrgmComparator = new Comparator<TestingProgram>() {
		public int compare(TestingProgram tstPrgm1, TestingProgram tstPrgm2) {
			return tstPrgm1.getProgramName().compareToIgnoreCase(tstPrgm2.getProgramName());
		}
	};

	/**
	 * @param assessmentProgramId
	 *            {@link long}
	 * @return List<testingPrograms>
	 */
	@RequestMapping(value = "getTestingPrograms.htm", method = RequestMethod.POST)
	private @ResponseBody List<TestingProgram> getTestingProgramByAssessmentProgramId(final long assessmentProgramId) {

		List<TestingProgram> testingPrograms = new ArrayList<TestingProgram>();
		testingPrograms = testingProgramService.getByAssessmentProgramId(assessmentProgramId);
		Collections.sort(testingPrograms, tstPrgmComparator);

		return testingPrograms;
	}

	static Comparator<ContentArea> contentAreaComparator = new Comparator<ContentArea>() {
		public int compare(ContentArea ca1, ContentArea ca2) {
			return ca1.getName().compareToIgnoreCase(ca2.getName());
		}
	};
	
	/**
	 * Added for F454(EP Drop-Down Menus Display Choices Relevant to User)
	 * @param assessmentProgramId
	 *            {@link long}
	 * @return List<testingPrograms>
	 */
	@RequestMapping(value = "getDynamicTestingPrograms.htm", method = RequestMethod.POST)
	private @ResponseBody List<TestingProgram> getDynamicTestingProgramByAssessmentProgramId(final long assessmentProgramId) {

		List<TestingProgram> testingPrograms = testingProgramService.getDynamicTestingProgramByAssessmentProgramId(assessmentProgramId);
		Collections.sort(testingPrograms, tstPrgmComparator);

		return testingPrograms;
	}
	
	/**
	 * Added for F454(EP Drop-Down Menus Display Choices Relevant to User)
	 * @param assessmentProgramId
	 * @param testingProgramId
	 *            {@link long}
	 * @return List<contentAreas>
	 */
	@RequestMapping(value = "getContentAreasByAssessmentProgramandTestProgramId.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ContentArea> getContentAreasByAssessmentProgramandTestProgramId(final Long assessmentProgramId, Long testingProgramId) {
		LOGGER.trace("Entering getContentAreasByAssessmentProgramId");

		List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgramandTestingProgramId(assessmentProgramId, testingProgramId);
		Collections.sort(contentAreas, contentAreaComparator);

		LOGGER.trace("Leaving getContentAreasByAssessmentProgramId");
		return contentAreas;
	}
	
	@RequestMapping(value = "getContentAreasByAssessmentProgram.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ContentArea> getContentAreasByAssessmentProgramId(final Long assessmentProgramId) {
		LOGGER.trace("Entering getContentAreasByAssessmentProgramId");

		List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgram(assessmentProgramId);
		Collections.sort(contentAreas, contentAreaComparator);

		LOGGER.trace("Leaving getContentAreasByAssessmentProgramId");
		return contentAreas;
	}

	/**
	 * @param assessmentProgramId
	 *            {@link long}
	 * @return List<testingPrograms>
	 */
	@RequestMapping(value = "getAssessments.htm", method = RequestMethod.POST)
	private @ResponseBody List<Assessment> getAssessmentByTestingProgramId(final long assessmentProgramId,
			final long testingProgramId) {

		LOGGER.debug("getAssessmentByTestingProgramId enter with : " + assessmentProgramId + "," + testingProgramId);
		List<Assessment> assessments = new ArrayList<Assessment>();
		assessments = assessmentService.getByAssessmentProgramIdAndTestingProgramId(assessmentProgramId,
				testingProgramId);

		return assessments;
	}

	/**
	 * This method looks at all the test collections for the chosen assessment
	 * program and returns the grades, and subjects for the test collections.
	 * 
	 * @param request
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "getGradesAndSubjectsForAssessmentId.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getGradesAndSubjectsForAssessmentId(
			final HttpServletRequest request) {
		String aId = request.getParameter("assessmentId");
		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(aId)) {
			Long assessmentId = Long.parseLong(aId);
			List<GradeCourse> gradeCourses = gradeCourseService.findByAssessmentId(assessmentId);

			Collections.sort(gradeCourses, new Comparator<GradeCourse>() {

				@Override
				public int compare(GradeCourse o1, GradeCourse o2) {
					return o1.getGradeLevel() - o2.getGradeLevel();
				}
			});
			List<ContentArea> contentAreas = contentAreaService.findByAssessmentId(assessmentId);

			map.put("grades", gradeCourses);
			map.put("subjects", contentAreas);
			map.put("valid", true);
		} else {
			// set an error message and return.
			map.put("valid", false);
		}

		return map;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "getSelectAssessmentsDropdownData.htm", method = RequestMethod.GET)
	private @ResponseBody Map<String, Object> getSelectAssessmentsDropdownData() {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Organization current = user.getOrganization();

		Map<String, Object> selectAssessmentValues = new HashMap<String, Object>();

		List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
		List<TestingProgram> testingPrograms = new ArrayList<TestingProgram>();
		List<Assessment> assessments = new ArrayList<Assessment>();
		List<GradeCourse> gradeCourses = new ArrayList<GradeCourse>();
		List<ContentArea> contentAreas = new ArrayList<ContentArea>();
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(user.getCurrentAssessmentProgramId());
		if (!assessmentPrograms.contains(ap)) {
			AssessmentProgramDto temp = new AssessmentProgramDto();
			temp.setAssessmentProgram(ap);
			assessmentPrograms.add(temp);
		}

		/**
		 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15083 : Test
		 * Management Add Test Session page - misc UI changes As part of this
		 * story found out that it was showing all testing programs in the
		 * dropdown, ideally it should show only those testing programs which
		 * are related to retrieved assessment programs by user organization,
		 * hence changed the query to reflect the same, this is used in add test
		 * session and find qc pages.
		 */
		testingPrograms = testingProgramService.selectAllTestingProgramsDropdown(user.getCurrentAssessmentProgramId());
		assessments = assessmentService.selectAllAssessments();
		gradeCourses = gradeCourseService.selectAllGradeCoursesDropdown(user.getCurrentAssessmentProgramId());
		contentAreas = contentAreaService.selectAllContentAreasDropdown(user.getCurrentAssessmentProgramId());

		selectAssessmentValues.put("assessmentPrograms", assessmentPrograms);
		selectAssessmentValues.put("testingPrograms", testingPrograms);
		selectAssessmentValues.put("assessments", assessments);
		selectAssessmentValues.put("gradeCourses", gradeCourses);
		selectAssessmentValues.put("contentAreas", contentAreas);

		return selectAssessmentValues;
	}

	/**
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @param response
	 *            {@link HttpServletResponse}
	 * @return List<TestForm>
	 */
	@RequestMapping(value = "findTestsAndTestCollections.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel findTestsAndTestCollections(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("filters") String filters) {
		LOGGER.trace("Entering the findTestsAndTestCollections() method.");

		List<TestCollectionDTO> testCollections = new ArrayList<TestCollectionDTO>();
		Boolean qcComplete = true;
		Integer currentPage = -1;
		Integer limitCount = -1;
		Integer totalCount = -1;
		JQGridJSONModel jqGridJSONModel = null;

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Organization organization = user.getOrganization();
		Boolean hasQCCompletePermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
		Boolean hasHighStakesPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getHighStakesPermission());

		Map<String, String> testsAndTestCollectionsCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
		
		testsAndTestCollectionsCriteriaMap.put("assessmentProgramCode", userDetails.getUser().getCurrentAssessmentProgramName());

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);

		// US12810 - if the user has qc complete permission then we want to show
		// qccomplete true and false
		// so send null as qcComplete to remove where clause item
		if (hasQCCompletePermission) {
			qcComplete = null;
			// If the user has QC complete permission, then pull tests only
			// rather than using
			// testcollection unionall tests query as we need to show data at
			// the tests level.
			testCollections = testCollectionService.selectTestsAndTestCollectionsForQCAdmin(organization.getId(),
					sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
					testStatusConfiguration.getPublishedTestStatusCategory().getId(), qcComplete,
					hasHighStakesPermission, testsAndTestCollectionsCriteriaMap,
					sortByColumn + ParsingConstants.BLANK_SPACE + sortType, (currentPage - 1) * limitCount, limitCount,
					TASK_TYPE_CODE);

			totalCount = testCollectionService.countTestsAndTestCollectionsForQCAdmin(
					testStatusConfiguration.getPublishedTestStatusCategory().getId(), organization.getId(), qcComplete,
					hasHighStakesPermission, testsAndTestCollectionsCriteriaMap);

		} else {

			testCollections = testCollectionService.selectTestsAndTestCollections(
					testStatusConfiguration.getPublishedTestStatusCategory().getId(),
					sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
					sessionRulesConfiguration.getManualEnrollmentCategory().getId(), organization.getId(), qcComplete,
					hasHighStakesPermission, testsAndTestCollectionsCriteriaMap,
					sortByColumn + ParsingConstants.BLANK_SPACE + sortType, (currentPage - 1) * limitCount, limitCount,
					TASK_TYPE_CODE);

			if (hasHighStakesPermission) {
				totalCount = testCollectionService.countTestsAndTestCollections(
						testStatusConfiguration.getPublishedTestStatusCategory().getId(),
						sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
						sessionRulesConfiguration.getManualEnrollmentCategory().getId(), organization.getId(),
						qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap);
			} else {

				totalCount = testCollectionService.countTestsAndManualTestCollections(
						testStatusConfiguration.getPublishedTestStatusCategory().getId(),
						sessionRulesConfiguration.getManualEnrollmentCategory().getId(), organization.getId(),
						qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap);
			}
		}

		jqGridJSONModel = TestCollectionDTOJsonConverter.convertToTestCollectionDTOJson(testCollections, totalCount,
				currentPage, limitCount, hasHighStakesPermission, hasQCCompletePermission);

		LOGGER.trace("Leaving the findTestsAndTestCollections() method.");
		return jqGridJSONModel;
	}

	/**
	 * AutoComplete ajax call request handler method from setup test session
	 * select assessments page.
	 * 
	 * @param fileterAttribute
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "getTestsAutoCompleteData.htm", method = RequestMethod.GET)
	public final @ResponseBody Set<String> getAutoCompleteData(
			@RequestParam("fileterAttribute") String fileterAttribute, @RequestParam("term") String term, @RequestParam("testingProgramName")String testingProgramName) {
		LOGGER.trace("Entering the findTests() method.");

		List<TestCollectionDTO> testCollections = new ArrayList<TestCollectionDTO>();
		Boolean qcComplete = true;
		Set<String> autoCompleteValues = new HashSet<String>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Organization organization = user.getOrganization();
		Boolean hasQCCompletePermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
		Boolean hasHighStakesPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getHighStakesPermission());

		Map<String, String> testsAndTestCollectionsCriteriaMap = recordBrowserJsonUtil
				.constructAutoCompleteFilterCriteria(fileterAttribute, term);

		testsAndTestCollectionsCriteriaMap.put("assessmentProgramCode", userDetails.getUser().getCurrentAssessmentProgramName());
		testsAndTestCollectionsCriteriaMap.put("assessmentProgramName", userDetails.getUser().getCurrentAssessmentProgramName());
		testsAndTestCollectionsCriteriaMap.put("testingProgramName", testingProgramName);
		
		// US12810 - if the user has qc complete permission then we want to show
		// qccomplete true and false
		// so send null as qcComplete to remove where clause item
		if (hasQCCompletePermission) {
			qcComplete = null;
			// If the user has QC complete permission, then pull tests only
			// rather than using
			// testcollection unionall tests query as we need to show data at
			// the tests level.

			testCollections = testCollectionService.selectTestsAndTestCollectionsForQCAdmin(organization.getId(),
					sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
					testStatusConfiguration.getPublishedTestStatusCategory().getId(), qcComplete,
					hasHighStakesPermission, testsAndTestCollectionsCriteriaMap, null, null, null, TASK_TYPE_CODE);

		} else {

			testCollections = testCollectionService.selectTestsAndTestCollections(
					testStatusConfiguration.getPublishedTestStatusCategory().getId(),
					sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
					sessionRulesConfiguration.getManualEnrollmentCategory().getId(), organization.getId(), qcComplete,
					hasHighStakesPermission, testsAndTestCollectionsCriteriaMap, null, null, null, TASK_TYPE_CODE);
		}

		for (TestCollectionDTO testCollection : testCollections) {
			autoCompleteValues.add(testCollection.getName());
		}

		return autoCompleteValues;
	}

	/**
	 * This method finds rosters that the currently logged in user is authorized
	 * to view.
	 * 
	 * @param request
	 * @return List<Roster>
	 */
	@RequestMapping(value = "getSelectRosterStudents.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getSelectRosterStudents(
			@RequestParam("testSessionId") Long testSessionId, @RequestParam("filters") String filters) {
		LOGGER.trace("Entering the getAutoCompleteSelectRosterStudents page for getting results");

		Map<String, Object> autoCompleteValues = new HashMap<String, Object>();
		// Set<String> autoCompleteValues = new HashSet<String>();
		// TODO move all 3 steps to a service.
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Map<String, String> studentRosterCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);

		int currentSchoolYearr = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();

		// DE622 - remove 1000 student limit for setup test sessions
		int count = enrollmentService.countEnrollmentWithRoster(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				studentRosterCriteriaMap, testSessionId, null, null, currentSchoolYearr);
		List<StudentRoster> studentRosters = enrollmentService.getEnrollmentWithRoster(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				null, null, null, count, studentRosterCriteriaMap, testSessionId, null, null, currentSchoolYearr);

		Set<String> currentSchoolYear = new HashSet<String>();
		Set<String> firstLanguage = new HashSet<String>();
		Set<String> comprehensiveRace = new HashSet<String>();
		Set<String> primaryDisabilityCode = new HashSet<String>();
		Set<String> gender = new HashSet<String>();
		Set<String> generationCode = new HashSet<String>();
		Set<String> gradeCourse = new HashSet<String>();
		Set<String> attendanceSchoolName = new HashSet<String>();
		Set<String> rosterId = new HashSet<String>();
		Set<String> courseEnrollmentStatus = new HashSet<String>();
		Set<String> dlmStudentStatus = new HashSet<String>();

		for (StudentRoster studentRoster : studentRosters) {
			if (((Integer.toString(studentRoster.getCurrentSchoolYear())) != null))
				currentSchoolYear.add(Integer.toString(studentRoster.getCurrentSchoolYear()));
			if (((studentRoster.getFirstLanguage()) != null))
				firstLanguage.add(studentRoster.getFirstLanguage());
			if (((studentRoster.getStudent().getComprehensiveRace()) != null))
				comprehensiveRace.add(studentRoster.getStudent().getComprehensiveRace());
			if (((studentRoster.getStudent().getPrimaryDisabilityCode()) != null))
				primaryDisabilityCode.add(studentRoster.getStudent().getPrimaryDisabilityCode());
			if (!("null".equalsIgnoreCase(studentRoster.getStudent().getGenderStr())))
				gender.add(studentRoster.getStudent().getGenderStr());
			if (((studentRoster.getStudent().getGenerationCode()) != null))
				generationCode.add(studentRoster.getStudent().getGenerationCode());
			if (((studentRoster.getGradeCourse().getName()) != null))
				gradeCourse.add(studentRoster.getGradeCourse().getName());
			if (((studentRoster.getAttendanceSchool().getOrganizationName()) != null))
				attendanceSchoolName.add(studentRoster.getAttendanceSchool().getOrganizationName());
			if (((Long.toString(studentRoster.getRoster().getId())) != null))
				rosterId.add((Long.toString(studentRoster.getRoster().getId())));
			if ((studentRoster.getCourseEnrollment().getCategoryName()) != null)
				courseEnrollmentStatus.add((studentRoster.getCourseEnrollment().getCategoryName()));
			if (studentRoster.getStudent().isDlmStudent() != null && studentRoster.getStudent().isDlmStudent()) {
				dlmStudentStatus.add("Yes");
			} else {
				dlmStudentStatus.add("No");
			}
		}
		autoCompleteValues.put("currentSchoolYear", currentSchoolYear);
		autoCompleteValues.put("firstLanguage", firstLanguage);
		autoCompleteValues.put("comprehensiveRace", comprehensiveRace);
		autoCompleteValues.put("primaryDisabilityCode", primaryDisabilityCode);
		autoCompleteValues.put("gender", gender);
		autoCompleteValues.put("generationCode", generationCode);
		autoCompleteValues.put("gradeCourse", gradeCourse);
		autoCompleteValues.put("attendanceSchoolName", attendanceSchoolName);
		autoCompleteValues.put("rosterId", rosterId);
		autoCompleteValues.put("courseEnrollmentStatus", courseEnrollmentStatus);
		autoCompleteValues.put("dlmStudentStatus", dlmStudentStatus);
		LOGGER.trace("Leaving the getRosterStudentsByTeacher page.");
		return autoCompleteValues;
	}

	/**
	 * This method finds rosters that the currently logged in user is authorized
	 * to view.
	 * 
	 * @param request
	 * @return List<Roster>
	 */
	@Deprecated // Seems this method is not in use any more.
	@RequestMapping(value = "getAutoCompleteSelectRosterStudents.htm", method = RequestMethod.GET)
	public final @ResponseBody Set<String> getAutoCompleteSelectRosterStudents(
			@RequestParam("testSessionId") Long testSessionId,
			@RequestParam("fileterAttribute") String fileterAttribute, @RequestParam("term") String term) {
		LOGGER.trace("Entering the getAutoCompleteSelectRosterStudents page for getting results");

		Set<String> autoCompleteValues = new HashSet<String>();
		// TODO move all 3 steps to a service.
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		Map<String, String> studentRosterCriteriaMap = recordBrowserJsonUtil
				.constructAutoCompleteFilterCriteria(fileterAttribute, term);

		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();

		List<StudentRoster> studentRosters = enrollmentService.getEnrollmentWithRoster(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				null, null, null, null, studentRosterCriteriaMap, testSessionId, null, null, currentSchoolYear);

		LOGGER.trace("Leaving the getRosterStudentsByTeacher page.");

		for (StudentRoster studentRoster : studentRosters) {
			if (fileterAttribute.equals("legalFirstName"))
				autoCompleteValues.add(studentRoster.getLegalFirstName());
			else if (fileterAttribute.equals("legalMiddleName"))
				autoCompleteValues.add(studentRoster.getStudent().getLegalMiddleName());
			else if (fileterAttribute.equals("legalLastName"))
				autoCompleteValues.add(studentRoster.getStudent().getLegalLastName());
			else if (fileterAttribute.equals("educatorFirstName"))
				autoCompleteValues.add(studentRoster.getEducator().getFirstName());
			else if (fileterAttribute.equals("educatorLastName"))
				autoCompleteValues.add(studentRoster.getEducator().getSurName());
			else if (fileterAttribute.equals("currentSchoolYear"))
				autoCompleteValues.add(Integer.toString(studentRoster.getCurrentSchoolYear()));
			else if (fileterAttribute.equals("firstLanguage"))
				autoCompleteValues.add(studentRoster.getFirstLanguage());
			else if (fileterAttribute.equals("comprehensiveRace"))
				autoCompleteValues.add(studentRoster.getStudent().getComprehensiveRace());
			else if (fileterAttribute.equals("primaryDisabilityCode"))
				autoCompleteValues.add(studentRoster.getStudent().getPrimaryDisabilityCode());
			else if (fileterAttribute.equals("localStudentIdentifier"))
				autoCompleteValues.add(studentRoster.getLocalStudentIdentifier());
			else if (fileterAttribute.equals("firstLanguage"))
				autoCompleteValues.add(studentRoster.getFirstLanguage());
			else if (fileterAttribute.equals("firstLanguage"))
				autoCompleteValues.add(studentRoster.getFirstLanguage());
			else if (fileterAttribute.equals("gender"))
				autoCompleteValues.add(studentRoster.getStudent().getGenderStr());
			else if (fileterAttribute.equals("generationCode"))
				autoCompleteValues.add(studentRoster.getGenerationCode());
			else if (fileterAttribute.equals("enrollmentsRostersId"))
				autoCompleteValues.add(Long.toString(studentRoster.getEnrollmentId()));
			else if (fileterAttribute.equals("stateStudentIdentifier"))
				autoCompleteValues.add((studentRoster.getStudent().getStateStudentIdentifier()));
			else if (fileterAttribute.equals("localStudentIdentifier"))
				autoCompleteValues.add((studentRoster.getLocalStudentIdentifier()));
			else if (fileterAttribute.equals("residenceDistrictIdentifier"))
				autoCompleteValues.add((studentRoster.getResidenceDistrictIdentifier()));
			else if (fileterAttribute.equals("uniqueCommonIdentifier"))
				autoCompleteValues.add((studentRoster.getEducator().getUniqueCommonIdentifier()));
			else if (fileterAttribute.equals("courseSectionName"))
				autoCompleteValues.add((studentRoster.getRoster().getCourseSectionName()));
		}

		return autoCompleteValues;
	}

	@RequestMapping(value = "findQCTest.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> findQCTest(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam(value = "filters", required = false) String filters)
					throws JsonProcessingException, IOException {
		LOGGER.trace("Entering the findTests() method.");
		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);

		List<QCTestCollectionDTO> testCollections = new ArrayList<QCTestCollectionDTO>();
		Map<String, Object> responseMap = new HashMap<String, Object>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Boolean hasHighStakesPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getHighStakesPermission());

		Map<String, Object> criteria = new HashMap<String, Object>();

		if (sortByColumn != null) {
			if (sortByColumn.contains(".")) {
				sortByColumn = "\"" + sortByColumn + "\"";
			}
			criteria.put("sortByColumn", sortByColumn);
			criteria.put("sortType", sortType);
		}
		populateCriteria(criteria, filters);

		// If the user has QC complete permission, then pull tests only rather
		// than using
		// testcollection unionall tests query as we need to show data at the
		// tests level.
		testCollections = testCollectionService.selectTestsAndTestCollectionsForQCControl(
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), user.getCurrentAssessmentProgramId(),
				hasHighStakesPermission, (currentPage - 1) * limitCount, limitCount, criteria);
		totalCount = testCollectionService.countTestsAndTestCollectionsForQCControl(
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), user.getCurrentAssessmentProgramId(),
				hasHighStakesPermission, criteria);

		responseMap.put("rows", testCollections);
		responseMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		responseMap.put("page", currentPage);
		responseMap.put("records", totalCount);
		LOGGER.trace("Leaving the findTests() method.");
		return responseMap;
	}

	/**
	 * This method is for updating the quality control complete flag on a test.
	 * 
	 * @param qcComplete
	 * @param testId
	 * @return
	 */
	@RequestMapping(value = "updateQCComplete.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean updateQCComplete(@RequestParam(value = "qcComplete") boolean qcComplete,
			@RequestParam(value = "testIds") Long[] testIds) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		boolean updated = false;
		int results = testService.updateQCComplete(testIds, userDetails.getUserId());
		if (results > 0) {
			updated = true;
			for (Long testId : testIds) {
				testService.publishRepublishingTest(testId);
			}
		}
		return updated;
	}
	
	
	/**
	 * This method is for updating the quality control complete flag on a test.
	 * 
	 * @param qcComplete
	 * @param testId
	 * @return
	 */
	@RequestMapping(value = "removeQCComplete.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean removeQCComplete(@RequestParam(value = "qcComplete") boolean qcComplete,
			@RequestParam(value = "testIds") Long[] testIds) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		boolean updated = false;
		int results = testService.removeQCComplete(testIds, userDetails.getUserId());
		if (results > 0) {
			updated = true;
			for (Long testId : testIds) {
				testService.publishRepublishingTest(testId);
			}
		}
		return updated;
	}

	/**
	 * This method finds rosters that the currently logged in user is authorized
	 * to view.
	 * 
	 * @param request
	 * @return List<Roster>
	 */
	@RequestMapping(value = "getRosterStudentsByTeacher.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getRosterStudents(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("testSessionId") Long testSessionId,
			@RequestParam("filters") String filters, @RequestParam("schoolOrgId") Long schoolOrgId,
			@RequestParam("assessmentProgramId") Long assessmentProgramId) {
		LOGGER.trace("Entering the getRosterStudentsByTeacher page for getting results");

		Integer currentPage = -1;
		Integer limitCount = -1;
		Integer totalCount = -1;
		JQGridJSONModel jqGridJSONModel = null;
		// String orderByclause = "";
		// TODO move all 3 steps to a service.
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		currentPage = Integer.parseInt(page);
		limitCount = Integer.parseInt(limitCountStr);
		Map<String, String> studentRosterCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		// Temporary fix for
		/*
		 * if(sortByColumn.equals("stateStudentIdentifier")){ orderByclause =
		 * "substring(statestudentidentifier, '^[0-9]+')::bigint "+ sortType +
		 * ", substring(statestudentidentifier, '[^0-9]*$') "+sortType; }else{
		 * orderByclause = sortByColumn + ParsingConstants.BLANK_SPACE +
		 * sortType; }
		 */

		TimerUtil timerUtil = TimerUtil.getInstance();
		List<StudentRoster> studentRosters = enrollmentService.getEnrollmentWithRosterAssessmentProgram(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount, studentRosterCriteriaMap,
				testSessionId, schoolOrgId, null, currentSchoolYear, assessmentProgramId);
		timerUtil.resetAndLog(LOGGER, "Getting student records for test session took ");

		totalCount = enrollmentService.countEnrollmentWithRosterAssessmentProgram(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				studentRosterCriteriaMap, testSessionId, schoolOrgId, null, currentSchoolYear,assessmentProgramId);
		timerUtil.resetAndLog(LOGGER, "Counting student records for test session took ");

		jqGridJSONModel = StudentRosterJsonConverter.convertToStudentRosterJson(studentRosters, totalCount, currentPage,
				limitCount, studentsTestsStatusConfiguration);

		LOGGER.trace("Leaving the getRosterStudentsByTeacher page.");
		return jqGridJSONModel;
	}

	/**
	 * This method would assign tests to students i.e setting up the testsession
	 * for students.
	 * 
	 * @param students
	 * @param testCollectionId
	 * @param testId
	 * @param sessionName
	 * @param testingProgramId
	 * @return
	 */
	@RequestMapping(value = "assignStudentsToTest.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Boolean> assignStudentsToTest(
			@RequestParam(value = "students[]") String[] students,
			@RequestParam("testCollectionId") Long testCollectionId,
			@RequestParam("arraySelectedTestId") String arraySelectedTestId,
			@RequestParam("testId[]") Long[] testIds,
			@RequestParam("sessionName") String sessionName) {

		LOGGER.trace("Entering the assignStudentsToTest() method.");

		List<Long> enrollmentRosterIds = new ArrayList<Long>();
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		boolean multipleTests=false;
		boolean isValid = false;
		if(testIds!=null && testIds.length>1){		
			asynchronousProcessService.createMultipleTestSessions(enrollmentRosterIds, arraySelectedTestId, testIds,
						sessionName, students);
			isValid = true;
			multipleTests=true;			
		}else if(testIds!=null && testIds.length==1){			
			boolean successful = false;	
			TestSession testSession = new TestSession();
			Category unusedSession = null;
			unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED,
					TEST_SESSION_STATUS_TYPE);
			testSession.setName(sessionName);
			testSession.setTestCollectionId(testCollectionId);
			testSession.setSource(SourceTypeEnum.MANUAL.getCode());
			if (unusedSession != null) {
				testSession.setStatus(unusedSession.getId());
			}
			enrollmentRosterIds = NumericUtil.convert(students);			
			
			try {
				if (enrollmentRosterIds != null && enrollmentRosterIds.size() > 0 && testCollectionId > 0) {	
					//(List<Long> students, Long testCollectionId, Long testId, TestSession testSession, List<Long> sensitivityTags)
							successful = studentsTestsService.createTestSessions(enrollmentRosterIds, testCollectionId, testIds[0],
									testSession, null, null);					
							
					if (successful) {
						isValid = true;
					}
				}					
				
					
				} catch (DuplicateTestSessionNameException e) {
						map.put("duplicateKey", true);
						isValid = false;
				}				

		}	
		map.put("valid", isValid);
		map.put("multipleTests", multipleTests);
		
		LOGGER.trace("Leaving the assignStudentsToTest() method.");
		return map;
	}

	/**
	 * This method would assign/delete students from tests after editing the
	 * testsession
	 *
	 * @param toBeEnrolledEnrollmentRosterIdStrings
	 * @param toBeUnEnrolledEnrollmentRosterIdStrings
	 * @param testSessionId
	 * @return
	 */
	@RequestMapping(value = "editTestSession.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Boolean> editTestSession(
			@RequestParam(value = "students[]", required = false) String[] toBeEnrolledEnrollmentRosterIdStrings,
			@RequestParam(value = "unenrolledStudents[]", required = false) String[] toBeUnEnrolledEnrollmentRosterIdStrings,
			@RequestParam(value = "testSessionId", required = true) Long testSessionId) {
		List<Long> toBeEnrolledRosterIds = null;
		List<Long> toBeUnEnrolledRosterIds = null;
		LOGGER.trace("Entering the editTestSession() method.");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		Boolean qcComplete = true;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Boolean hasQCCompletePermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
		if (hasQCCompletePermission) {
			qcComplete = null;
		}
		// Per US18128, save only when edit permission has. only save manual session
		Boolean hasEditTestSessionPermission = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_TESTSESSION_MODIFY");
		if(hasEditTestSessionPermission){
			if (testSessionId != null) {
			TestSession testSession = testSessionService.findByPrimaryKey(testSessionId);
				if (testSession.getSource() == null || testSession.getSource().equalsIgnoreCase(SourceTypeEnum.MANUAL.getCode())) {
					if (toBeEnrolledEnrollmentRosterIdStrings != null
							&& ArrayUtils.isNotEmpty(toBeEnrolledEnrollmentRosterIdStrings)) {
						toBeEnrolledRosterIds = AARTCollectionUtil.getListAsLongType(toBeEnrolledEnrollmentRosterIdStrings);
					}
					if (toBeUnEnrolledEnrollmentRosterIdStrings != null
							&& ArrayUtils.isNotEmpty(toBeUnEnrolledEnrollmentRosterIdStrings)) {
						toBeUnEnrolledRosterIds = AARTCollectionUtil
								.getListAsLongType(toBeUnEnrolledEnrollmentRosterIdStrings);
					}
					Boolean result = studentsTestsService.editTestSession(toBeEnrolledRosterIds, toBeUnEnrolledRosterIds,
							testSessionId, qcComplete);
					map.put("valid", result);
				} else {
					map.put("valid", false);
				}
			} else {
				map.put("valid", false);
			}
		} else {
			map.put("valid", false);
		}

		LOGGER.trace("Leaving the editTestSession() method.");
		return map;
	}
	
	/**
	 * This method save student's special circumstance codes after editing the
	 * testsession
	 *
	 * @param testSessionId
	 * @param studentSpecialCircumstances
	 * @param highStakesTest
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "saveSpecialCircumstances.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Boolean> saveSpecialCircumstances(
			@RequestParam(value = "testSessionId", required = true) Long testSessionId,
			@RequestParam(value = "studentSpecialCircumstances", required = true) String studentSpecialCircumstances,
			@RequestParam(value = "highStakesTest", required = true)boolean highStakesTest) throws Exception {
		LOGGER.trace("Entering the saveSpecialCircumstances() method.");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		// for non high stakes test this call returns true
		map.put("valid", true);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
			.getPrincipal();
		Long userId = userDetails.getUser().getId();
		if(highStakesTest) {
			if (testSessionId != null) {
				Boolean result = true; 
				List<Map<String, Object>> specialCircumstances = null;
				try {
					specialCircumstances =  mapper.readValue(studentSpecialCircumstances, List.class);
				}catch (Exception e) {
					LOGGER.error("Error while parsing student special circumstances: ", e);
					throw e;
				}
				studentSpecialCircumstanceService.saveSpecialCircumstance(testSessionId, specialCircumstances, highStakesTest, userId);
				map.put("valid", result);
			} else {
				map.put("valid", false);
			}
		}
		
		LOGGER.trace("Leaving the saveSpecialCircumstances() method.");
		return map;
	}

	/**
	 * This method is to update the studentTestSection status to reactivate the
	 * students
	 * 
	 * @param request
	 *            {@link HttpServletRequest}
	 * @return true
	 */
	@RequestMapping(value = "reactivateStudentsSection.htm", method = RequestMethod.POST)
	public final @ResponseBody String reactivateStudentsSection(
			@RequestParam(value = "studentsTestIds[]") List<String> studentsIds, @RequestParam Long testSessionId,
			@RequestParam boolean confirmReactivate) {
		List<Long> studentIds;
		Map<String, Object> responseMap = new HashMap<String, Object>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Boolean hasReactivateTestSessionPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getReactivateTestSessionPermission());

		List<Long> ineligibleStudentIds = new ArrayList<Long>();
		List<Student> ineligibleStudents = new ArrayList<Student>();
		boolean updateSuccessful = false;
		studentIds = AARTCollectionUtil.getListAsLongType(studentsIds);
		TestSession testSession = testSessionService.findByPrimaryKey(testSessionId);
    	boolean isAdaptiveTest = (testSession.getTestPanelId() != null) ? true: false;
    	if (hasReactivateTestSessionPermission){
    		if(confirmReactivate && isAdaptiveTest){
				// update & reset sections for the eligible students.
				updateSuccessful = studentsTestsService.reactivateStudentsTestSections(studentIds, testSessionId);
			} else if(!confirmReactivate && isAdaptiveTest){
				// find inEligible students for test stage reactivation and display details for user confirmation.
				ineligibleStudentIds = studentsTestsService.findInEligibleStudentsForReactivation(studentIds, testSessionId);
				if(!ineligibleStudentIds.isEmpty()){
					ineligibleStudents = studentService.findStudentsByIds(ineligibleStudentIds);
				}
				updateSuccessful = false;
			} else {
				// non-adaptive tests follow existing logic
				updateSuccessful = studentsTestsService.reactivateStudentsTestSectionsStatus(studentIds, testSessionId);
			}
    	}
		LOGGER.debug("reactivate studentsIds " + studentIds);
		
		responseMap.put("ineligibleStudents", ineligibleStudents);
		responseMap.put("status", updateSuccessful);
		responseMap.put("permission", hasReactivateTestSessionPermission);
		
		ObjectMapper mapper = new ObjectMapper();
		String response = StringUtils.EMPTY;
		try {
			response = mapper.writeValueAsString(responseMap);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while generating response", e);
		}
		return response;
	}

	@RequestMapping(value = "monitoring.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getTestSessionMonitorReport(
			@RequestParam(value = "rosterId", required = false) Long rosterId,
			@RequestParam("testSessionId") Long testSessionId) {
		LOGGER.trace("Entering the getTestSessionMonitorReport() method");

		List<StudentTestSessionDto> studentTestSessionDtoList = null;
		Long testId = 0L;
		StudentsResponsesReportDto studentsResponsesReportDto = null;

		List<String> taskVariantAndSectionsList = new ArrayList<String>();
		List<String> sectionTaskColumnHeaders = new ArrayList<String>();
		List<String> sectionTaskColumnNames = new ArrayList<String>();
		List<String> sectionTaskColumnLabels = new ArrayList<String>();
		Set<String> sectionGroupingHeaders = new TreeSet<String>();
		String taskVariantPositionAndSection = "";

		Map<String, Object> studentResponsesMap = new LinkedHashMap<String, Object>();
		List<Object> allStudentsResponsesList = new ArrayList<Object>();
		Map<String, Object> allStudentsMonitorMap = new LinkedHashMap<String, Object>();

		if (testSessionId != null) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			Long organizationId = userDetails.getUser().getCurrentOrganizationId();
			Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
			
			User user = userDetails.getUser();
			if(user.isTeacher() && rosterId == null) {
				// populate the rosters by teacherid and testsession id
				List<Long> rosterIds = rosterService.getRosterIdByTeacherIdTestSessionId(user.getId(), testSessionId);
				studentTestSessionDtoList = studentReportService.getTestSessionReport(rosterIds, testSessionId, true, currentSchoolYear, organizationId);
			} else if (rosterId != null) {
				List<Long> rosterIds = new ArrayList<Long>();
				rosterIds.add(rosterId);
				studentTestSessionDtoList = studentReportService.getTestSessionReport(rosterIds, testSessionId, true, currentSchoolYear, organizationId);
			} else {
				studentTestSessionDtoList = studentReportService.getAutoTestSessionReport(testSessionId, true, currentSchoolYear, organizationId);
			}

			Map<Long, Boolean> studentInProgressStatusMap = new HashMap<Long, Boolean>();
			List<StudentsTestSections> studentsTestSectionsList = studentReportService
					.getPerformanceStatus(testSessionId);
			if (CollectionUtils.isNotEmpty(studentsTestSectionsList) && studentsTestSectionsList.size() > 0) {
				for (StudentsTestSections studentsTestSections : studentsTestSectionsList) {
					boolean inProgressTimedOut = false;

					if (StringUtils.isNotEmpty(studentsTestSections.getStatus().getCategoryName())
							&& studentsTestSections.getStatus().getCategoryName()
									.replace(ParsingConstants.BLANK_SPACE, ParsingConstants.BLANK)
									.equalsIgnoreCase(studentsTestSectionsStatusConfiguration
											.getInProgressTimedOutStudentsTestSectionsCode())) {
						inProgressTimedOut = true;
						studentInProgressStatusMap.put(studentsTestSections.getStudentsTests().getStudent().getId(),
								inProgressTimedOut);
					} else {
						if (!studentInProgressStatusMap
								.containsKey(studentsTestSections.getStudentsTests().getStudent().getId())) {
							studentInProgressStatusMap.put(studentsTestSections.getStudentsTests().getStudent().getId(),
									inProgressTimedOut);
						}
					}
				}
			}

			if (studentTestSessionDtoList != null && CollectionUtils.isNotEmpty(studentTestSessionDtoList)) {

				List<TestSection> testSectionsWithMaxNumberOfItems = testService
						.getTestSectionsWithMaxNumberOfItemsByTestSessionId(testSessionId);
				int x = 1;
				String groupName;
				String sectionName;
				for (TestSection testSection : testSectionsWithMaxNumberOfItems) {
					int nbrOfItems = testSection.getNumberOfTestItems();
					if (nbrOfItems > 0) {
						groupName = "Section "+ x;
						for (int i = 1; i <= nbrOfItems; i++) {
							sectionName = groupName + ":" + i;
							sectionTaskColumnHeaders.add(testSection.getTestSectionName().replaceAll(" ", "_") + ":" + i);
							sectionTaskColumnNames.add(sectionName);
							sectionTaskColumnLabels.add(Integer.toString(i));
							if (i == 1) {
								sectionGroupingHeaders.add("<div style='text-align: center;'>"+groupName+"</div>"+"|"+testSection.getTestSectionName().replaceAll(" ", "_") + ":" + i+"|"+nbrOfItems);
							}
						}
					}
					x++;
				}

				// Build table rows
				Boolean found = false;
				int sectionTaskColumnHeadersSize = sectionTaskColumnHeaders.size();
				for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtoList) {
					if (studentTestSessionDto != null && studentTestSessionDto.getStudent() != null
							&& studentTestSessionDto.getStudentsResponsesReportDtos() != null) {

						studentResponsesMap = new HashMap<String, Object>();

						studentResponsesMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_STUDENTS_TEST_ID,
								studentTestSessionDto.getStudent().getId());
						studentResponsesMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_STUDENT_NAME,
								studentTestSessionDto.getStudent().getLegalFirstName() + " "
										+ studentTestSessionDto.getStudent().getLegalLastName());
						studentResponsesMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_OVERALL_STATUS,
								studentTestSessionDto.getStudentsTestStatus());

						testId = studentTestSessionDto.getStudentsTests().getTestId();

						taskVariantAndSectionsList = testService.getTaskVariantAndSectionByTestId(testId, true);

						for (String taskVariantAndSec : taskVariantAndSectionsList) {
							if(taskVariantAndSec == null || taskVariantAndSec.trim().length() == 0) {
								taskVariantAndSectionsList.remove(taskVariantAndSec);
							}
						}
						
						List<String> studentTasks = new ArrayList<String>();
						int columnCount = 0;
						int unanswered = 0;
						String sectionTaskColumnHeader = StringUtils.EMPTY;

						for (String taskVariantAndSec : taskVariantAndSectionsList) {
							// validate if sectionorder and taskvariantposition
							// are in sync with section-maxitems item
							if (columnCount < sectionTaskColumnHeadersSize) {
								sectionTaskColumnHeader = (String) sectionTaskColumnHeaders.get(columnCount);
							} else {
								break;
							}
							String modifiedTaskVariantAndSec = taskVariantAndSec.replaceAll(" ", "_").replaceFirst("_", " ");
							String thisSectionNameTaskvarintPos = taskVariantAndSec
									.substring(taskVariantAndSec.indexOf(" ") + 1, taskVariantAndSec.lastIndexOf("-")).replaceAll(" ", "_");
							while (!sectionTaskColumnHeader.equals(thisSectionNameTaskvarintPos)) {
								studentTasks.add("NA");
								studentResponsesMap.put(sectionTaskColumnHeader, "NA");
								columnCount++;
								if("complete".equalsIgnoreCase(studentTestSessionDto.getStudentsTestStatus())
									|| "in progress timed out".equalsIgnoreCase(studentTestSessionDto.getStudentsTestStatus())){
									unanswered++;
								}
								if (columnCount < sectionTaskColumnHeadersSize) {
									sectionTaskColumnHeader = (String) sectionTaskColumnHeaders.get(columnCount);
								} else {
									break;
								}
							}

							if (sectionTaskColumnHeader.equals(thisSectionNameTaskvarintPos)) {
								found = false;

								for (int i = 0; i < studentTestSessionDto.getStudentsResponsesReportDtos().size()
										&& !found; i++) {
									studentsResponsesReportDto = studentTestSessionDto.getStudentsResponsesReportDtos()
											.get(i);
									if (studentsResponsesReportDto != null) {

										StudentsResponses studentsResponse = studentsResponsesReportDto
												.getStudentsResponses();
										Long foilId = studentsResponse.getFoilId();
										String response = studentsResponse.getResponse();
										if (foilId != null || (response != null && !response.trim().equals("[]")
												&& !response.trim().equals(""))) {
											taskVariantPositionAndSection = ((studentsResponsesReportDto
													.getTestSectionsTaskVariants().getTestSectionId()).toString() + " "
													+ studentsResponsesReportDto.getTestSectionName().replaceAll(" ", "_") + ":"
													+ (studentsResponsesReportDto.getTestSectionsTaskVariants()
															.getTaskVariantPosition()).toString()
													+ "-" + (studentsResponsesReportDto.getTestSectionsTaskVariants()
															.getTaskVariantId()).toString());

											if (modifiedTaskVariantAndSec.equalsIgnoreCase(taskVariantPositionAndSection)) {

												found = true;
												studentTasks.add(taskVariantAndSec.replaceAll(" ", "_"));
												studentResponsesMap.put(thisSectionNameTaskvarintPos, "T");
											}
										}
									}
								}
								if (!found) {
									studentTasks.add(taskVariantAndSec);
									studentResponsesMap.put(thisSectionNameTaskvarintPos, "F");
									if("complete".equalsIgnoreCase(studentTestSessionDto.getStudentsTestStatus())
											|| "in progress timed out".equalsIgnoreCase(studentTestSessionDto.getStudentsTestStatus())){
											unanswered++;
									}
								}
								columnCount++;
							}
						}
						if (columnCount < sectionTaskColumnHeadersSize) {
							int diff = sectionTaskColumnHeadersSize - columnCount;
							for (int k = 0; k < diff; k++) {
								studentTasks.add("NA");
								studentResponsesMap.put(sectionTaskColumnHeaders.get(columnCount), "NA");
								columnCount++;
							}
						}
						studentResponsesMap.put("studentTasks", studentTasks);
						if (!"complete".equalsIgnoreCase(studentTestSessionDto.getStudentsTestStatus())
											& !"in progress timed out".equalsIgnoreCase(studentTestSessionDto.getStudentsTestStatus())){
							studentResponsesMap.put("unansweredCount", -1);
						}else{
							studentResponsesMap.put("unansweredCount", unanswered);
						}
						
						if (studentInProgressStatusMap != null
								&& studentInProgressStatusMap.containsKey(studentTestSessionDto.getStudent().getId())) {
							studentResponsesMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_INPROGRESSTIMEDOUT,
									studentInProgressStatusMap.get(studentTestSessionDto.getStudent().getId()));
						} else {
							studentResponsesMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_INPROGRESSTIMEDOUT,
									false);
						}

						allStudentsResponsesList.add(studentResponsesMap);
					}
				}
			}

			LOGGER.debug("All Student Responses {}", allStudentsResponsesList);
			allStudentsMonitorMap.put("students", allStudentsResponsesList);
			allStudentsMonitorMap.put("sectionStatusColumnNames", sectionTaskColumnHeaders);
			allStudentsMonitorMap.put("sectionTaskColumnLabels", sectionTaskColumnLabels);
			allStudentsMonitorMap.put("groupingHeaders", sectionGroupingHeaders);
		}

		LOGGER.trace("Leaving the getTestSessionTCMonitorReport() method");
		return allStudentsMonitorMap;
	}

	/**
	 * This method pulls all the labels and responses along with student marked
	 * responses.
	 * 
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "rubricScoringView.htm", method = RequestMethod.GET)
	public final ModelAndView rubricScoringView(final @RequestParam Long studentId,
			final @RequestParam Long taskVariantId, final @RequestParam Long testId) {
		LOGGER.trace("Entering rubricScoringView method");
		List<Integer> scores = new ArrayList<Integer>();
		Map<String, Object> questionResponseMap = getQuestionAndResponse(taskVariantId, studentId, testId);
		List<RubricReportDto> rubricScoresGuidelines = testService.getRubricByTaskVariant(taskVariantId);
		ModelAndView mav = new ModelAndView("rubricScoringView");

		for (RubricReportDto rubr : rubricScoresGuidelines) {
			Integer score = rubr.getRubricInfoScore();
			if (!(scores.contains(score))) {
				scores.add(score);
			}
		}

		mav.addObject("studentId", studentId);
		mav.addObject("taskVariantId", taskVariantId);
		mav.addObject("testId", testId);
		mav.addObject("questionResponseMap", questionResponseMap);
		mav.addObject("rubricScoresGuidelines", rubricScoresGuidelines);
		mav.addObject("scores", scores.size());
		mav.addObject("scoreList", StringUtils.join(scores, ','));
		LOGGER.trace("Leaving rubricScoringView method");
		return mav;
	}

	/**
	 * @param rosterId
	 * @param testSessionId
	 * @return
	 */
	@RequestMapping(value = "scoring.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getScoringReport(
			@RequestParam(value = "rosterId", required = false) Long rosterId,
			@RequestParam("testSessionId") Long testSessionId) {
		LOGGER.trace("Entering the getTestSessionReport() method");

		List<StudentTestSessionDto> studentTestSessionDtoList = null;
		StudentsResponsesReportDto studentsResponsesReportDto = null;

		List<String> taskVarianAndSectionsList = new ArrayList<String>();
		String taskVariantPositionAndSection = "";

		Map<String, Object> studentResponsesMap = new HashMap<String, Object>();
		List<Object> allStudentsResponsesList = new ArrayList<Object>();
		Map<String, Object> allStudentsMonitorMap = new HashMap<String, Object>();
		int n = 0;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Long organizationId = userDetails.getUser().getCurrentOrganizationId();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		
		if (testSessionId != null) {

			// US14114 Technical: Fix Test Monitor and other TDE services
			// dependency
			// Retrieve student responses.
			// studentsResponsesService.retrieveStudentsResponses(testSessionId);
			if (rosterId != null) {
				List<Long> rosterIds = new ArrayList<Long>();
				rosterIds.add(rosterId);
				studentTestSessionDtoList = studentReportService.getTestSessionReport(rosterIds, testSessionId, false, currentSchoolYear, organizationId);
			} else {
				studentTestSessionDtoList = studentReportService.getAutoTestSessionReport(testSessionId, true, null, organizationId);
			}

			if (studentTestSessionDtoList != null && CollectionUtils.isNotEmpty(studentTestSessionDtoList)) {
				Long testId = studentTestSessionDtoList.get(0).getStudentsTests().getTestId();

				taskVarianAndSectionsList = testService.getTaskVariantAndSectionByTestId(testId, false);
				
				for (String taskVariantAndSec : taskVarianAndSectionsList) {
					if(taskVariantAndSec == null || taskVariantAndSec.trim().length() == 0) {
						taskVarianAndSectionsList.remove(taskVariantAndSec);
					}
				}
				
				// Build table rows
				Boolean found = false;
				Boolean extendedresponse = false;
				for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtoList) {

					if (studentTestSessionDto != null && studentTestSessionDto.getStudent() != null
							&& studentTestSessionDto.getStudentsResponsesReportDtos() != null) {

						studentResponsesMap = new HashMap<String, Object>();

						studentResponsesMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_STUDENT_NAME,
								studentTestSessionDto.getStudent().getLegalFirstName() + " "
										+ studentTestSessionDto.getStudent().getLegalLastName());
						studentResponsesMap.put("studentId", studentTestSessionDto.getStudent().getId());
						studentResponsesMap.put("testId", studentTestSessionDto.getStudentsTests().getTestId());
						studentResponsesMap.put("rowId","row"+n);
						/*
						 * String taskVariant =
						 * taskVarianAndSectionsList.get(n); String
						 * taskvariantid1 =
						 * taskVariant.substring(taskVariant.lastIndexOf("-") +
						 * 1); Long taskVariantId1 =
						 * Long.parseLong(taskvariantid1);
						 * studentResponsesMap.put("taskVariantTestId",
						 * taskVariantId1);
						 */
						for (String taskVariantAndSec : taskVarianAndSectionsList) {
							String taskvariantid = taskVariantAndSec.substring(taskVariantAndSec.lastIndexOf("-") + 1);
							Long taskVariantId = Long.parseLong(taskvariantid);
							TaskVariant tvr = testService.getTaskVariantById(taskVariantId);
							Boolean rubricneeded = tvr.getRubricNeeded();
							String tasktype = testService.getTaskTypeCode(tvr.getTaskTypeId());
							found = false;
							for (int i = 0; i < studentTestSessionDto.getStudentsResponsesReportDtos().size()
									&& !found; i++) {
								extendedresponse = false;
								studentsResponsesReportDto = studentTestSessionDto.getStudentsResponsesReportDtos()
										.get(i);
								if (rubricneeded != null) {
									if ((tasktype.equals(TASK_TYPE_CODE)) && (rubricneeded == true)) {
										extendedresponse = true;
									}
								}
								if (studentsResponsesReportDto != null
										&& studentsResponsesReportDto.getTaskVariantsFoils() != null) {

									if (studentsResponsesReportDto.getTaskVariantsFoils()
											.getCorrectResponse() != null) {

										taskVariantPositionAndSection = ((studentsResponsesReportDto
												.getTestSectionsTaskVariants().getTestSectionId()).toString()
												+ " "
												+ (studentsResponsesReportDto.getTestSectionsTaskVariants()
														.getTaskVariantPosition()).toString()
												+ "-" + (studentsResponsesReportDto.getTestSectionsTaskVariants()
														.getTaskVariantId()).toString());
										// studentResponsesMap.put("taskVariantTestId",
										// studentsResponsesReportDto.getTestSectionsTaskVariants().getTaskVariantId());
										if (taskVariantAndSec.equalsIgnoreCase(taskVariantPositionAndSection)) {
											found = true;
											// if the task has an extended
											// response
											if (extendedresponse) {
												if ((studentTestSessionDto.getStudentsTestStatus())
														.equals("Complete")) {
													RubricScore score = studentReportService.getRubricScore(
															studentTestSessionDto.getStudentTestId(), taskVariantId);
													if (score != null) {
														studentResponsesMap.put(taskVariantAndSec,
																score.getRubricScore());
													} else {
														studentResponsesMap.put(taskVariantAndSec, "Not Scored");
													}
													extendedresponse = false;
												} else {
													studentResponsesMap.put(taskVariantAndSec, "Not Completed");
													extendedresponse = false;
												}
											} else {

												// if the response does not have
												// extended response and
												// rubricneeded flag is set to
												// false
												studentResponsesMap.put(taskVariantAndSec, "-");
											}
										}
									}
								}
							}
							if (!found) {
								if (rubricneeded != null) {
									if ((tasktype.equals(TASK_TYPE_CODE)) && (rubricneeded == true)) {
										if ((studentTestSessionDto.getStudentsTestStatus()).equals("Complete")) {
											RubricScore score = studentReportService.getRubricScore(
													studentTestSessionDto.getStudentTestId(), taskVariantId);
											if (score != null) {
												studentResponsesMap.put(taskVariantAndSec, score.getRubricScore());
											} else {
												studentResponsesMap.put(taskVariantAndSec, "Not Scored");
											}
											extendedresponse = false;
										} else {
											studentResponsesMap.put(taskVariantAndSec, "Not Completed");
											extendedresponse = false;
										}
									} else {
										studentResponsesMap.put(taskVariantAndSec, "-");
									}
								} else {
									studentResponsesMap.put(taskVariantAndSec, "-");
								}
							}
						}

						allStudentsResponsesList.add(studentResponsesMap);

					}
					n++;
				}
			}
			LOGGER.debug("All Student Responses {}", allStudentsResponsesList);
			allStudentsMonitorMap.put("students", allStudentsResponsesList);
			allStudentsMonitorMap.put("sectionStatusColumnNames", taskVarianAndSectionsList);
		}

		LOGGER.trace("Leaving the getTestSessionReport() method");
		return allStudentsMonitorMap;
	}

	/**
	 * Returns taskstem for the selected taskvariantid.
	 * 
	 * @param taskVariantId
	 * @return
	 */
	@RequestMapping(value = "getTaskVariantData.htm", method = RequestMethod.POST)
	public final ResponseEntity<String> getTaskVariantData(@RequestParam("taskVariantId") Long taskVariantId,
			HttpServletResponse response) {
		LOGGER.trace("Entering the getQuestionForTest() method");

		String taskVariant = "";

		TaskVariant tv = testService.getTaskVariantById(taskVariantId);
		if (tv != null) {
			taskVariant = tv.getTaskStem();
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");

		LOGGER.trace("Leaving the getQuestionForTest() method");
		return new ResponseEntity<String>(taskVariant, responseHeaders, HttpStatus.CREATED);
	}

	/**
	 * Returns taskstem and Response for the selected taskvariantid.
	 * 
	 * @param taskVariantId
	 * @return
	 */
	@RequestMapping(value = "saveRatings.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> saveRatings(Long rubricScoreId, Long taskVariantId,
			String rubricInfoIds, Long studentId, Long testId, Float score) {
		LOGGER.trace("Entering the getQuestionAndResponse() method");
		Map<String, Object> response = new HashMap<String, Object>();
		RubricScore rScore = studentReportService.saveRubricScore(rubricScoreId, taskVariantId, rubricInfoIds,
				studentId, testId, score);
		response.put("SUCCESS", "SUCCESS");
		response.put("score", rScore);
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
		response.put("date", dateFormat.format(rScore.getDate()));
		LOGGER.trace("Leaving the getQuestionAndResponse() method");
		return response;
	}

	/**
	 * Returns taskstem and Response for the selected taskvariantid.
	 * 
	 * @param taskVariantId
	 * @return
	 */
	@RequestMapping(value = "getRubricScoringGuidelines.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getRubricScoringGuidelines(Long taskVariantId, Long testId,
			Long studentId) {
		LOGGER.trace("Entering the getRubricScoringGuidelines() method");
		Map<String, Object> rubricScoresGuidelines = new HashMap<String, Object>();
		List<RubricReportDto> rubricReportDtos = testService.getRubricByTaskVariant(taskVariantId);
		rubricScoresGuidelines.put("rubrics", rubricReportDtos);
		RubricScore rubricScore = studentReportService.getRubricScoreByStudent(studentId, testId, taskVariantId);
		if (rubricScore != null && rubricScore.getRubricInfoIds() != null) {
			List<String> selectedIds = Arrays.asList(rubricScore.getRubricInfoIds().split(","));
			for (RubricReportDto rubricReportDto : rubricReportDtos) {
				if (selectedIds.contains(rubricReportDto.getRubricInfoId() + "")) {
					rubricReportDto.setSelected(true);
				}
			}
			rubricScoresGuidelines.put("rubricScore", rubricScore);
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
			rubricScoresGuidelines.put("date", dateFormat.format(rubricScore.getDate()));
		}

		LOGGER.trace("Leaving the getRubricScoringGuidelines() method");
		return rubricScoresGuidelines;
	}

	/**
	 * Returns taskstem for the selected taskvariantid.
	 * 
	 * @param taskVariantId
	 * @return
	 */
	public final @ResponseBody Boolean getRubricForTestSession(Long testSessionId, Long rosterId) {
		LOGGER.trace("Entering the getRubricForTestSession() method");
		List<StudentTestSessionDto> studentTestSessionDtoList = null;
		List<Long> taskVariantsList = null;
		Boolean extendedresponse = false;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Long organizationId = userDetails.getUser().getCurrentOrganizationId();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		// StudentsResponsesReportDto studentsResponsesReportDto = null;
		if (rosterId != null && testSessionId != null) {

			// US14114 Technical: Fix Test Monitor and other TDE services
			// dependency
			// Retrieve student responses.
			// studentsResponsesService.retrieveStudentsResponses(testSessionId);
			List<Long> rosterIds = new ArrayList<Long>();
			rosterIds.add(rosterId);
			studentTestSessionDtoList = studentReportService.getTestSessionReport(rosterIds, testSessionId, false, currentSchoolYear, organizationId);
			if (studentTestSessionDtoList != null && CollectionUtils.isNotEmpty(studentTestSessionDtoList)) {
				Long testId = studentTestSessionDtoList.get(0).getStudentsTests().getTestId();

				taskVariantsList = testService.getTaskVariantsByTestId(testId);
				for (Long taskVariantId : taskVariantsList) {
					if (taskVariantId != null) {
						TaskVariant tvr = testService.getTaskVariantById(taskVariantId);
						Boolean rubricneeded = tvr.getRubricNeeded();
						String tasktype = testService.getTaskTypeCode(tvr.getTaskTypeId());
						if (rubricneeded != null) {
							if ((tasktype.equals(TASK_TYPE_CODE)) && (rubricneeded == true)) {
								extendedresponse = true;
								return extendedresponse;
							}
						}
					}
				}
			}
		}
		return extendedresponse;
	}

	/**
	 * Returns taskstem and Response for the selected taskvariantid.
	 * 
	 * @param taskVariantId
	 * @return
	 */
	public final @ResponseBody Map<String, Object> getQuestionAndResponse(Long taskVariantId, Long studentId,
			Long testId) {
		LOGGER.trace("Entering the getQuestionAndResponse() method");
		Map<String, Object> questionAndResponse = new HashMap<String, Object>();
		String taskVariant = "";
		TaskVariant tv = testService.getTaskVariantById(taskVariantId);
		if (tv != null) {
			taskVariant = tv.getTaskStem();
			questionAndResponse.put("rubricQuestion", taskVariant);
		}

		StudentsResponses studentsResponses = studentsResponsesService.getStudentResponse(studentId, taskVariantId,
				testId);

		// String response = "";

		if (studentsResponses != null) {
			String response = studentsResponses.getResponse();
			questionAndResponse.put("rubricResponse", response);
		} else {
			questionAndResponse.put("rubricResponse", "Not Answered");
		}

		LOGGER.trace("Leaving the getQuestionAndResponse() method");
		return questionAndResponse;
	}

	/**
	 * @param studentIds
	 * @param testSessionId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "endStudentsTest.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean endStudentsTest(@RequestParam(value = "studentIds[]") List<String> studentIds,
			@RequestParam Long testSessionId) throws Exception {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Boolean hasEndTestSessionPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getEndTestSessionPermission());
		if (hasEndTestSessionPermission){
			List<Long> convertedStudentIds = new ArrayList<Long>();
			if (studentIds != null && CollectionUtils.isNotEmpty(studentIds) && testSessionId != null) {
				for (String studentId : studentIds) {
					convertedStudentIds.add(Long.parseLong(studentId));
				}
				TestSession testSession = testSessionService.findByPrimaryKey(testSessionId);
				boolean isAdaptiveTest = (testSession.getTestPanelId() != null) ? true: false;
				if(isAdaptiveTest){
					studentsTestsService.updateInterimThetaForAdaptiveStudents(convertedStudentIds, testSessionId, testSession.getStageId());
				}
				studentsTestsService.closeStudentsTestAtEndTestSession(convertedStudentIds, testSessionId);
				
			} else {
				throw new Exception("Input is not valid " + studentIds + " and test session id" + testSessionId);
			}
			LOGGER.debug("Closed students tests " + convertedStudentIds + " and test session id " + testSessionId);
			return true;
		}else{
			LOGGER.debug("User: "+userDetails.getUsername()+" does not have End test session permission.");
			return false;
		}
	}

	private void populateCriteria(Map<String, Object> criteria, String filters)
			throws JsonProcessingException, IOException {
		if (null != filters && !filters.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(filters);

			final JsonNode results = rootNode.get("rules");

			for (final JsonNode element : results) {
				if (element.get("field").asText().equalsIgnoreCase("TESTID")) {
					criteria.put(element.get("field").asText(), element.get("data").asText());
				} else {
					criteria.put(element.get("field").asText(), CommonConstants.PERCENTILE_DELIM
							+ element.get("data").asText() + CommonConstants.PERCENTILE_DELIM);
				}
			}
		}
	}
}
