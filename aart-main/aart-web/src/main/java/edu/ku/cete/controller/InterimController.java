package edu.ku.cete.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.InterimGroup;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.interim.FeedbackQuestionAndResponse;
import edu.ku.cete.domain.interim.FeedbackResponse;
import edu.ku.cete.domain.interim.InterimPredictiveStudentScore;
import edu.ku.cete.domain.interim.InterimTest;
import edu.ku.cete.domain.interim.StudentActivityReport;
import edu.ku.cete.domain.report.PredictiveReportCreditPercent;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.model.TestDao;
import edu.ku.cete.model.TestingCycleMapper;
import edu.ku.cete.report.InterimOrganizationSummaryReportGenerator;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.InterimFeedbackService;
import edu.ku.cete.service.InterimGroupService;
import edu.ku.cete.service.InterimService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.InterimConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;

@Controller
public class InterimController extends BaseController {

	@Autowired
	private ContentAreaService contentAreaService;
	@Autowired
	private GradeCourseService gradeCourseService;
	@Autowired
	private InterimService interimService;
	@Autowired
	private InterimGroupService interimGroupService;
	@Autowired
	private InterimReportService interimReportService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupsDao groupsDao;
	@Autowired
	private TestingCycleMapper testingCycleMapper;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private TestDao testDao;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private InterimFeedbackService interimFeedbackService;
	@Autowired
	private TestService testService;
	
	@Autowired
	private PermissionUtil permissionUtil;
	
	@Autowired
	private InterimOrganizationSummaryReportGenerator interimSummaryReportGenerator;

	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	protected AppConfigurationService appConfigService;
	
	private Logger LOGGER = LoggerFactory.getLogger(InterimController.class);

	@RequestMapping(value = "interim.htm")
	public final ModelAndView assemble() {
		LOGGER.debug("Entered interim.htm ");
		ModelAndView mav = new ModelAndView("interim");
		LOGGER.debug("Exited interim.htm");
		return mav;
	}

	@RequestMapping(value = "assign.htm")
	public final ModelAndView assign() {
		LOGGER.debug("Entered assign.htm ");
		ModelAndView mav = new ModelAndView("assign");
		LOGGER.debug("Entered assign.htm ");
		return mav;
	}

	@RequestMapping(value = "getPurpose.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Category> selectTestPurposeForInterim(
			@RequestParam(value = "isInterim", required = false) String isInterimStr) {
		LOGGER.debug("Entered interimsub.htm ");
		Boolean isInterim = Boolean.parseBoolean(isInterimStr);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long organizationId = user.getCurrentOrganizationId();
		Long assessmentProgramId = user.getCurrentAssessmentProgramId();
		Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		List<Category> assembleNames = interimService.selectTestPurposeForInterim(isInterim, organizationId,
				assessmentProgramId, schoolYear);
		LOGGER.debug("Exited interimsub.htm ");
		return assembleNames;
	}

	@RequestMapping(value = "interimsub.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentArea> getInterimSubjectNames(
			@RequestParam(value = "isInterim", required = false) String isInterimStr) {
		LOGGER.debug("Entered interimsub.htm ");
		Boolean isInterim = Boolean.parseBoolean(isInterimStr);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long organizationId = user.getCurrentOrganizationId();
		Long assessmentProgramId = user.getCurrentAssessmentProgramId();
		boolean isTeacher = user.isTeacher() || user.isProctor();
		String assessmentProgramName = user.getCurrentAssessmentProgramName();
		boolean isUserLoggedAsPLTW = false;
		if (assessmentProgramName != null 
				&& CommonConstants.PLTW.equalsIgnoreCase(assessmentProgramName)) {
			isUserLoggedAsPLTW = true;
		}
		// getting purposeId from DataBase for Instructional only
		Long purposeId = categoryService.getCategoryIdByName(InterimConstants.INTERIM_ALLOWED_PURPOSE);

		List<ContentArea> assembleNames = contentAreaService.getInterimSubjectNames(purposeId, isInterim,
				organizationId,assessmentProgramId,isTeacher,isUserLoggedAsPLTW,user.getId());
		LOGGER.debug("Exited interimsub.htm ");
		return assembleNames;
	}

	@RequestMapping(value = "interimalign.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentFrameworkDetail> getInterimAlignment(
			@RequestParam(value = "gradeCourseId", required = false) Long gradeCourseId,
			@RequestParam(value = "contentAreaId", required = false) Long contentAreaId,
			@RequestParam(value = "isInterim", required = false) String isInterimStr) {
		LOGGER.debug("Entered interimalign.htm ");
		Boolean isInterim = Boolean.parseBoolean(isInterimStr);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long organizationId = user.getCurrentOrganizationId();
		// getting purposeId from DataBase for Instructional only
		Long purposeId = categoryService.getCategoryIdByName(InterimConstants.INTERIM_ALLOWED_PURPOSE);
		Long assessmentProgramId = user.getCurrentAssessmentProgramId();
		Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		List<ContentFrameworkDetail> assembleNames = contentAreaService.getInterimAlignment(contentAreaId,
				gradeCourseId, isInterim, purposeId, organizationId, assessmentProgramId, schoolYear);
		LOGGER.debug("Exited interimallign.htm");
		return assembleNames;
	}

	@RequestMapping(value = "getSchoolNames.htm", method = RequestMethod.GET)
	public final @ResponseBody List<String> getSchoolNames() {
		LOGGER.debug("Entered interimalign.htm ");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		List<String> assembleNames = interimService.getSchoolNames(user.getCurrentOrganizationId(), schoolYear);
		LOGGER.debug("Exited interimallign.htm");
		return assembleNames;
	}

	@RequestMapping(value = "getTestNameUnique.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> getTestNameUnique(
			@RequestParam(value = "testName", required = false) String testName) {
		LOGGER.debug("Entered interimalign.htm ");
		Map<String, String> testStatus = new HashMap<String, String>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		Long orgId=user.getCurrentOrganizationId();
		Boolean status = interimService.isTestNameUnique(testName, user.getCurrentContextUserId(), schoolYear,orgId);
		LOGGER.debug("Exited interimallign.htm");
		testStatus.put("Status", status.toString());
		return testStatus;
	}

	@RequestMapping(value = "interimgrade.htm", method = RequestMethod.GET)
	public final @ResponseBody List<GradeCourse> getGradeCourse(
			@RequestParam(value = "contentAreaId", required = false) Long contentAreaId,
			@RequestParam(value = "isInterim", required = false) String isInterimStr) {
		LOGGER.debug("Entered interimgrade.htm ");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long organizationId = user.getCurrentOrganizationId();
		Long assessmentProgramId = user.getCurrentAssessmentProgramId();
		// getting purposeId from DataBase for Instructional only
		Long purposeId = categoryService.getCategoryIdByName(InterimConstants.INTERIM_ALLOWED_PURPOSE);

		Boolean isInterim = Boolean.parseBoolean(isInterimStr);
		List<GradeCourse> assembleNames = gradeCourseService.getGradeCourseInterim(contentAreaId, purposeId, isInterim,
				organizationId,assessmentProgramId);
		LOGGER.debug("Exited interimgrade.htm ");
		return assembleNames;
	}

	@RequestMapping(value = "getMiniTests.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Test> getMiniTests(
			@RequestParam(value = "gradeCourseId",required = false) Long gradeCourseId,
			@RequestParam(value = "contentAreaId") Long contentAreaId,
			@RequestParam(value = "isInterim") String isInterimStr,
			@RequestParam(value = "createdBy", required = false) String createdBy, 
			@RequestParam(value = "schoolName", required = false) String schoolName,
			@RequestParam(value = "contentCode", required = false) String contentCode,
			@RequestParam(value = "testName", required = false) String testName) throws Exception {
		LOGGER.debug("Entered getMiniTests.htm ");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long organizationId = user.getCurrentOrganizationId();
		Long assessmentProgramId = user.getCurrentAssessmentProgramId();
		Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		// getting purpose from DataBase for Instructional only
		Long purpose = categoryService.getCategoryIdByName(InterimConstants.INTERIM_ALLOWED_PURPOSE);

		LOGGER.debug("getting user ids" + user.getCurrentContextUserId());
		Boolean isInterim = Boolean.parseBoolean(isInterimStr);
		List<Test> miniTests = null;
		try {
			miniTests = interimService.getMiniTests(purpose, contentAreaId, gradeCourseId, isInterim, createdBy,
					schoolName, contentCode, testName, organizationId,assessmentProgramId,schoolYear);
		} catch (Exception e) {
			LOGGER.error("getMiniTests.htm Error in getting tests", e);
			throw e;
		}
		return miniTests;
	}

	@RequestMapping(value = "/saveInterimTest.htm", method = RequestMethod.POST)
	public final @ResponseBody InterimTest saveInterimTest(
			@RequestParam(value = "gradeCourseId", required = false) Long gradeCourseId,
			@RequestParam(value = "contentAreaId") Long contentAreaId,
			@RequestParam(value = "testIds[]") List<String> testIds,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "isTestCopied", required = false) String isTestCopiedStr,
			User interimUser,
			Long orgId) {
		LOGGER.info("Inside Save Interim Test Controller");
		InterimTest intTest = new InterimTest();
		try{
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		//This is so that api requests use the logged in user, but reassign can create on behalf of another user
		User user = (interimUser!=null && interimUser.getId() != null) ? interimUser : userDetails.getUser();
		LOGGER.debug(user.getContractingOrganization().toString());
		if(interimUser != null) {
			user.setCurrentContextUserId(user.getId());
		}

		Long organizationId = (orgId != null) ? orgId : user.getCurrentOrganizationId();
		// getting purposeId from DataBase for Instructional only
		Long purposeId = categoryService.getCategoryIdByName(InterimConstants.INTERIM_ALLOWED_PURPOSE);

		Boolean isTestCopied = false;
		if (isTestCopiedStr != null) {
			isTestCopied = Boolean.parseBoolean(isTestCopiedStr);
		}

		// We will pass the False Value for Mini tests.
		Long[] testIdArray = new Long[testIds.size()];
		for (int i = 0; i < testIds.size(); i++) {
			testIdArray[i] = Long.valueOf(testIds.get(i));
		}

		if (isTestCopied == Boolean.TRUE) {
			InterimTest it = interimService.getInterimTestByTestTestId(testIdArray[0]);
			List<Test> testList = interimService.getMiniTestsByInterimTestId(it.getId());
			testIdArray = null;
			testIdArray = new Long[testList.size()];
			int j = 0;
			for (Test t : testList) {
				testIdArray[j] = Long.valueOf(t.getId());
				j++;
			}
		}

		/*
		 * Code Change Author: vkrishna_sta F560 Removing logic to create a
		 * testcollection for interim everytime and rather use testcollection of
		 * minitests
		 * 
		 */
		/*
		 * Long createdTestCollectionId =
		 * interimService.createTestCollection(name, new Date(),
		 * user.getCurrentContextUserId(), user.getCurrentContextUserId(), new
		 * Date(), gradeCourseId, contentAreaId, "CB", "login");
		 * LOGGER.debug("Created TestCollection with id" +
		 * createdTestCollectionId);
		 */

		Long testCollectionId = interimService.getTestCollectionId(testIdArray[0]);

			/*Long createdTestId = interimService.createTest(name, 1l, "aart", "genTest", 64l, gradeCourseId, contentAreaId,
					new Date(), user.getCurrentContextUserId(), user.getCurrentContextUserId(), new Date(), Boolean.TRUE,
					Boolean.TRUE, purposeId);
			LOGGER.debug("Created Test with id" + createdTestId);

			InterimTest it = interimService.createInterimTest(testIdArray, name, description, gradeCourseId, contentAreaId,
					user.getCurrentContextUserId(), user.getCurrentContextUserId(), new Date(), new Date(), createdTestId,
					user.getDisplayName(), testCollectionId, organizationId, isTestCopied,
					user.getContractingOrganization().getCurrentSchoolYear());
			it.setTestCollectionId(testCollectionId);
			LOGGER.debug("Created InterimTest with id" + it);

			interimService.createInterimTestCollectionTests(testCollectionId, createdTestId, new Date(),
					user.getCurrentContextUserId(), user.getCurrentContextUserId(), new Date());
			LOGGER.debug("Created InterimTestCollections with id" + it.getId());

			interimService.createTestSection(new Date(), user.getCurrentContextUserId(), user.getCurrentContextUserId(),
					new Date(), createdTestId, true, 1, name, testIdArray);*/
			
			intTest = interimService.createInterimTestandTestSection(name, description, gradeCourseId, contentAreaId, 
					purposeId, user, testCollectionId, testIdArray, organizationId,  isTestCopied);

			
		}catch(Exception e){
			e.printStackTrace();
			LOGGER.debug("Interim Test Controoler errorThrown: "+e.getMessage());
		}
		
		return intTest;
	}
	
	
	@RequestMapping(value = "/getFeedbackQuestionsAndResponses.htm", method = RequestMethod.GET)
	public final @ResponseBody List<FeedbackQuestionAndResponse> getFeedbackQuesionsAndResponses(
			@RequestParam(value = "testId") Long testId) {
		LOGGER.debug("Entered getFeedbackQuesionsAndResponses.htm");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long assessmentProgramId = user.getCurrentAssessmentProgramId();
		Date startDate = organizationService.get(user.getCurrentOrganizationId()).getSchoolStartDate();
		
		List<Long> testletExternalIds = interimFeedbackService.getTestletsByTest(testId);
		
		List<FeedbackQuestionAndResponse> questions = 
				interimFeedbackService.getFeedbackQuestionsAndResponses(assessmentProgramId, testletExternalIds, user.getId(), startDate);
		
		return questions;
	}
	
	@RequestMapping(value = "/saveInterimFeedback.htm", method = RequestMethod.POST
			,consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody Boolean saveInterimFeedback(@RequestBody String body) {
		
		LOGGER.debug("Enter saveInterimFeedback.htm");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		List<FeedbackResponse> responses = null;
		try {
			responses = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, FeedbackResponse.class));
		} catch (Exception e) {
			LOGGER.error("Error reading body JSON into list of POJOs:", e);
			return false;
		}

		for(FeedbackResponse response: responses) {
			response.setUserId(user.getId());
			response.setResponseModifiedDate(new Date());
			if(response.getResponseActive() == null) {
				response.setResponseActive(true);
			}
		}
		
		try {
			interimFeedbackService.saveFeedbackResponses(responses);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		
		return true;
	}

	@RequestMapping(value = "getInterimTests.htm", method = RequestMethod.GET)
	public final @ResponseBody List<InterimTest> getInterimTests() {

		LOGGER.debug("Entered getInterimTests.htm ");

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		LOGGER.debug("getting user ids" + user.getCurrentContextUserId());

		// We will pass the False Value for Mini tests.
		List<InterimTest> interimTests = interimService.getInterimTestsByUser(user.getCurrentContextUserId());
		return interimTests;
	}

	@RequestMapping(value = "getEnrolledStudentsCount.htm", method = RequestMethod.GET)
	public final @ResponseBody Long getInterimtTestsCount(@RequestParam(value = "testSessionId") Long testSessionId) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		return interimService.getInterimTestsCount(user.getId());
	}

	@RequestMapping(value = "deleteInterimTest.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> deleteInterimTest(
			@RequestParam(value = "interimTestId") Long interimTestId,
			@RequestParam(value = "testSessionId", required = false) Long testSessionId) {

		LOGGER.debug("Entered deleteInterimTests.htm ");
		Map<String, String> returnMap = new HashMap<>();
		interimService.deleteInterimTest(interimTestId, testSessionId);
		returnMap.put("Result", "Successfull");
		return returnMap;
	}

	@RequestMapping(value = "getMiniTestsByInterimTestId.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getMiniTestsByInterimTestId(
			@RequestParam(value = "interimTestId") Long interimTestId) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userDetails.getUser();
		String assessmentProgramCode = currentUser.getCurrentAssessmentProgramName();
		Map<String, Object> map = new HashMap<>();
		InterimTest it = interimService.getInterimTestByTestTestId(interimTestId);
		// interimService.getMiniTestsByInterimTestId(it.getId());
		Test t = testDao.findById(interimTestId);
		ContentArea ca = contentAreaService.selectByPrimaryKey(t.getContentAreaId());
		if (!assessmentProgramCode.equalsIgnoreCase(CommonConstants.PLTW)) {
			GradeCourse gc = gradeCourseService.selectByPrimaryKey(t.getGradeCourseId());
			map.put("gradeCourseId", gc);
		}

		map.put("contentAreaId", ca);
		map.put("purpose", "Instructional");
		map.put("interimTest", it);
		map.put("miniTests", interimService.getMiniTestsByInterimTestId(it.getId()));
		return map;
	}

	@RequestMapping(value = "assignView.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> assignView(
			@RequestParam(value = "interimTestId", required = false) Long interimTestId) {

		LOGGER.debug("Entered assignView.htm ");

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userDetails.getUser();
		Long userId = currentUser.getCurrentContextUserId();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Long organizationId = currentUser.getCurrentOrganizationId();
		organizationService.get(organizationId);
		Long assessmentProgramId = currentUser.getCurrentAssessmentProgramId();
		Long schoolYear = null;
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		try {
			schoolYear = currentUser.getContractingOrganization().getCurrentSchoolYear();
		} catch (Exception e) {
			LOGGER.info("Cannot find current school year for User with ID:"
					+ currentUser.getCurrentContextUserId().toString());
		}
		Boolean isTeacher = Boolean.FALSE;
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> roster = new ArrayList<String>();
		List<String> subject = new ArrayList<String>();
		try {
			if (groups != null && roleOrgTypeId != null && groups.getGroupCode().equalsIgnoreCase("TEA")) {
				isTeacher = Boolean.TRUE;
			}
		} catch (Exception e) {

		}
		if (!isTeacher) {
			roster.addAll(interimService.getRosterNameByUserAndOrgList(null, organizationId, schoolYear, assessmentProgramId));
			
			subject.addAll(interimService.getSubjectNameByUserAndOrgList(null, organizationId, schoolYear, assessmentProgramId));
		} else {
			roster.addAll(interimService.getRosterName(userId, schoolYear, organizationId, assessmentProgramId, interimTestId));
		}
		List<String> grade = new ArrayList<String>();
		if (isTeacher) {
			grade = interimService.getRostergrade(organizationId, userId, schoolYear, assessmentProgramId);
		} else {
			grade = interimService.getRostergrade(organizationId, null, schoolYear, assessmentProgramId);
		}
		map.put("rostername", roster);

		map.put("gradename", grade);

		map.put("subjectname", subject);

		LOGGER.debug("getting map ids" + map);

		return map;

	}

	@RequestMapping(value = "/updateInterimTest.htm", method = RequestMethod.POST)
	public final @ResponseBody InterimTest updateInterimTest(
			@RequestParam(value = "gradeCourseId", required = false) Long gradeCourseId,
			@RequestParam(value = "contentAreaId", required = false) Long contentAreaId,
			@RequestParam(value = "testIds[]") List<String> testIds,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "interimTestId", required = false) Long interimTestId,
			@RequestParam(value = "testSessionId", required = false) String testSessionIdStr) {

		LOGGER.debug("Entered updateInterimTest.htm ");
		try {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();
			// We will pass the False Value for Mini tests.
			Long testSessionId = null;
			if (testSessionIdStr != null && !testSessionIdStr.equalsIgnoreCase("null")) {
				try {
					testSessionId = new Long(testSessionIdStr);
				} catch (Exception e) {
					testSessionId = null;
				}
			}
			InterimTest interimTest = new InterimTest();
			Long[] newTestIds = new Long[testIds.size()];
			for (int i = 0; i < testIds.size(); i++) {
				newTestIds[i] = Long.valueOf(testIds.get(i));

			}
			interimTest = interimService.getInterimTest(interimTestId);
			if (interimTestId != null && testSessionId == null) {
				interimService.softDeleteInterimTestByInterimTestID(interimTestId);
				interimTest.setMiniTestIds(newTestIds);
				interimTest.setName(name);
				interimTest.setDescription(description);
				interimTest.setGradeCourseId((gradeCourseId == null) ? interimTest.getGradeCourseId() : gradeCourseId);
				interimTest.setContentAreaId(contentAreaId == null ? interimTest.getContentAreaId() : contentAreaId);
				interimTest.setCreatedUser(user.getCurrentContextUserId());
				interimTest.setModifiedUser(user.getCurrentContextUserId());
				interimTest.setModifiedDate(new Date());
				interimTest.setCreatedDate(new Date());
				// return Long.valueOf(interimService.createTest(interimTest));
				InterimTest it = interimService.updateTest(interimTest);
				LOGGER.debug("getting interimTest ids" + it);
				return it;
			} else if (interimTestId != null && testSessionId != null) {
				Map<String, Object> map = getMiniTestsByInterimTestId(interimTest.getTestTestId());
				ContentArea ca = (ContentArea) map.get("contentAreaId");
				GradeCourse gc = (GradeCourse) map.get("gradeCourseId");
				contentAreaId = ca.getId();
				if (gradeCourseId == null) {
					gradeCourseId = gc.getId();
				}
				List <Student> students= getStudentByInterimTestSession(testSessionId.toString());
				List<Long> studentIds = new ArrayList<Long>();
				for(Student stu : students) {
					studentIds.add(stu.getId());
				}				
				InterimTest upIt = saveInterimTest(gradeCourseId, contentAreaId, testIds, name, description, "false",
						user, null);
				interimService.updateInterim(name, upIt.getId(), upIt.getTestCollectionId(), upIt.getTestTestId(),
						testSessionId);
				//assign the students to test being updated
				assignInterimTestToStudent(studentIds, upIt.getTestTestId());
				interimService.cleanUpOldTest(interimTest);
				return upIt;
			}
		} catch (Exception e) {
			LOGGER.error("getting error while updating interimTest id " + interimTestId, e);
			return new InterimTest();
		}
		return null;
	}

	@RequestMapping(value = "/reassignInterimTest.htm", method = RequestMethod.POST)
	public final @ResponseBody ResponseEntity<Map<String, Object>> reassignInterimTest(
			@RequestParam(value = "newTestIds[]") List<String> newTestIds,
			@RequestParam(value = "interimTestId") Long interimTestId) {

		LOGGER.debug("Entered reassignInterimTest.htm ");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Long groupId = user.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		
		if(!groups.getGroupCode().equalsIgnoreCase("GSAD") && !groups.getGroupCode().equalsIgnoreCase("SSAD")){
			LOGGER.info("user trying to reassign interim tests is not a GSAD or SSAD");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}else if(interimTestId == null || newTestIds == null) {
			LOGGER.info("interimTestId or newTestId was null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		Long[] newTestIdsL = new Long[newTestIds.size()];
		for (int i = 0; i < newTestIds.size(); i++) {
			newTestIdsL[i] = Long.valueOf(newTestIds.get(i));

		}
		
		InterimTest interimTest = interimService.getInterimTest(interimTestId);
		
		//update name of old test
		InterimTest oldInterimTest = interimService.getInterimTest(interimTestId);
		oldInterimTest.setName(oldInterimTest.getName()+"_orig");
		interimService.updateById(oldInterimTest);
		Test oldTest = testService.findById(oldInterimTest.getTestTestId());
		oldTest.setTestName(oldTest.getTestName()+"_orig");
		testService.updateByPrimaryKeySelective(oldTest);
		
		
		String oldTestSessionId = interimTest.getTestSessionId();
		LOGGER.debug(oldTestSessionId);
		
		User originalUser = userService.get(interimTest.getCreatedUser());
		originalUser.setContractingOrganization(organizationService.getContractingOrganization(interimTest.getOrganizationId()));
		originalUser.getContractingOrganization().setCurrentSchoolYear(interimTest.getCurrentSchoolYear());
		
		interimTest.setMiniTestIds(newTestIdsL);
		
		
		InterimTest upIt = saveInterimTest(interimTest.getGradeCourseId(), interimTest.getContentAreaId(), newTestIds, 
				interimTest.getName(), interimTest.getDescription(), "false", originalUser, interimTest.getOrganizationId());

		List<Long> studentIds = interimService.unassignUnusedStudentsTestsByInterimTestId(interimTestId);
		
		Long testSessionId = interimService.insertTestSession(upIt.getTestTestId(), upIt.getTestCollectionId(), originalUser,
				upIt.getName(), upIt.getCurrentSchoolYear());

		interimService.insertStudentTestsRecords(testSessionId, studentIds, upIt.getTestTestId(),
				upIt.getTestCollectionId(), upIt.getId(), upIt.getCurrentSchoolYear());
		
		Map<String, Object> responseMap = new HashMap<String,Object>();
		
		responseMap.put("oldInterimTestId", interimTestId);
		responseMap.put("newInterimTestId", upIt.getId());
		responseMap.put("oldTestId", interimTest.getTestTestId());
		responseMap.put("newTestId", upIt.getTestTestId());
		responseMap.put("movedStudents", studentIds);
		
		LOGGER.info("Student Ids reassigned from " + interimTest.getTestTestId() 
		+ " to " + upIt.getTestTestId()
		+ ": "+ StringUtils.join(studentIds, ','));
		
		return ResponseEntity.ok(responseMap);
	}
	
	@RequestMapping(value = "getstudentGridNew.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Student> getStudentInfoNew(
			@RequestParam(value = "rosterId[]", required = false) List<String> rosterIdStrList,
			@RequestParam(value = "gradeCourseId[]", required = false) List<String> gradeCourseIdStrList) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userDetails.getUser();
		Long groupId = currentUser.getCurrentGroupsId();
		List<Long> orgIds = currentUser.getAllChilAndSelfOrgIds();
		Long assessmentProgramId = currentUser.getCurrentAssessmentProgramId();
		List<Long> userList = new ArrayList<Long>();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		LOGGER.info("getStudentInfoNew Group Code for User with ID" + currentUser.getCurrentContextUserId().toString()
				+ "is " + groups.getGroupCode());
		try {
			if (groups != null && groups.getGroupCode() != null && groups.getGroupCode().equalsIgnoreCase("tea")) {
				isTeacher = Boolean.TRUE;
			}
		} catch (Exception e) {

		}
		if (isTeacher) {
			userList.add(currentUser.getCurrentContextUserId());
		}
		List<Long> rosterId = new ArrayList<Long>();
		List<Long> gradeCourseId = new ArrayList<Long>();
		Long schoolYear = null;
		try {
			schoolYear = currentUser.getContractingOrganization().getCurrentSchoolYear();
		} catch (Exception e) {
			LOGGER.info("getStudentInfoNew Cannot find current school year for User with ID:"
					+ currentUser.getCurrentContextUserId().toString());
		}
		try {
			for (String rosterIdStr : rosterIdStrList) {
				if (rosterIdStr != null && (!rosterIdStr.equalsIgnoreCase("select") && !rosterIdStr.equalsIgnoreCase("")
						&& !rosterIdStr.equalsIgnoreCase("null") && !rosterIdStr.equalsIgnoreCase("undefined"))) {
					rosterId.add(Long.parseLong(rosterIdStr));
				}
			}
		} catch (Exception e) {
			LOGGER.info("getStudentInfoNew Unable to Parse Roster " + currentUser.getCurrentContextUserId().toString());

		}
		try {
			for (String gradeCourseIdStr : gradeCourseIdStrList) {
				if (gradeCourseIdStr != null && (!gradeCourseIdStr.equalsIgnoreCase("select")
						&& !gradeCourseIdStr.equalsIgnoreCase("null") && !gradeCourseIdStr.equalsIgnoreCase("undefined")
						&& !gradeCourseIdStr.equalsIgnoreCase(""))) {
					gradeCourseId.add(Long.parseLong(gradeCourseIdStr));
				}
			}
		} catch (Exception e) {
			LOGGER.info("getStudentInfoNew Unable to Parse Grade " + currentUser.getCurrentContextUserId().toString());
		}
		LOGGER.info("getStudentInfoNew Unable to Parse Roster " + currentUser.getCurrentContextUserId().toString());
		if (isTeacher) {

			LOGGER.info("getStudentInfoNew Calling Service method with Params RosterId" + rosterId + "Grade"
					+ gradeCourseId + "Org ID" + orgIds.get(0).toString() + "User Id" + userList.get(0).toString()
					+ "SchoolYear" + schoolYear);
		}
		List<Student> students = interimService.getStudentInfoNew(rosterId, gradeCourseId, orgIds, userList,
				schoolYear,assessmentProgramId);

		return students;

	}

	@RequestMapping(value = "getTotalTests.htm", method = RequestMethod.POST)
	private final @ResponseBody Map<String, Object> getTotalTests() throws JsonProcessingException, IOException {
		LOGGER.debug("Entered getTotalTests.htm ");
		List<InterimTestDTO> messageList = new ArrayList<InterimTestDTO>();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long groupId = user.getCurrentGroupsId();
		Long organizationId = user.getCurrentOrganizationId();
		Organization org = organizationService.get(organizationId);
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isBTC= Boolean.FALSE;
		Boolean isDTC= Boolean.FALSE;
		Boolean isTeacher = Boolean.FALSE;
		Long schoolYear = null;
		Long assessmentProgId = user.getCurrentAssessmentProgramId();
		try {
			schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		} catch (Exception e) {
			LOGGER.info(
					"Cannot find current school year for User with ID:" + user.getCurrentContextUserId().toString());
		}
		try {
			
			if(groups != null && groups.getGroupCode() != null ){
				if (groups.getGroupCode().equalsIgnoreCase("BTC")) {
					isBTC = Boolean.TRUE;
				}else if (groups.getGroupCode().equalsIgnoreCase("DTC")) {
					isDTC = Boolean.TRUE;
				}else if (groups.getGroupCode().equalsIgnoreCase("TEA")) {
					isTeacher = Boolean.TRUE;
				}
			}
		} catch (Exception e) {

		}
		if (isBTC || isDTC) {
			List<Long> orgIds = user.getAllChilAndSelfOrgIds();
			if (isBTC) {
				Organization parentOrgDetails = organizationService.getParentOrgDetailsById(organizationId);
				orgIds.add(parentOrgDetails.getId());
			}
			List<Long> orgIdsWithoutDuplicates = new ArrayList<>(new HashSet<>(orgIds));
            for (Long orgId : orgIdsWithoutDuplicates) {
				List<Long> userList = userService.getUsersByOrgIdForInterim(orgId, assessmentProgId);
				if (!userList.isEmpty()) {
					messageList.addAll(interimService.getTotalTests(userList, orgId, schoolYear, null, assessmentProgId));
				}
			}

		} else if (isTeacher) {

			List<InterimTestDTO> teacherTests = interimService.getTotalTestsForTeacher(user, schoolYear, null, assessmentProgId);
			for (InterimTestDTO teacherTest : teacherTests) {
				if (!messageList.contains(teacherTest) && (teacherTest.getStudentsAssigned() != null && (teacherTest
						.getStudentsAssigned().longValue() > new Long(0).longValue()
						|| (teacherTest.getCreatedUser().equals(user.getId()) && (teacherTest.getSchoolName() != null
								&& teacherTest.getSchoolName().equalsIgnoreCase(org.getOrganizationName())))))) {
					messageList.add(teacherTest);
				}
			}

		} else {
			List<Long> userList = new ArrayList<>();
			userList.add(user.getId());
			List<Long> orgIds = user.getAllChilAndSelfOrgIds();
			messageList.addAll(interimService.getTotalTestsForOrgs(userList, schoolYear, assessmentProgId, orgIds));
		}

		//DE18472 - sort tests by create date 
		//since multiple queries may be ran to populate the list each addition is order by date
		//but not the whole list - reorder before sending back to the client.
		messageList.sort((p1, p2) -> p2.getCreateDate().compareTo(p1.getCreateDate()));
		
		Long serial = 1l;
		for (InterimTestDTO it : messageList) {
			if (it.getCreatedUser() != null && it.getCreatedUser().equals(user.getId())) {
				if (it.getSchoolName() != null && it.getSchoolName().equalsIgnoreCase(org.getOrganizationName())) {
					it.setAccess(Boolean.TRUE);
				} else {
					it.setAccess(Boolean.FALSE);
				}
			} else {
				it.setAccess(Boolean.FALSE);
			}
			it.setSerial(serial);
			serial++;
		}

		responseMap.put("rows", messageList);
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValueAsString(responseMap);
		LOGGER.trace("Leaving the getTotalTests() method.");
		LOGGER.debug("getting TotalTest ids" + responseMap);
		return responseMap;
	}

	@RequestMapping(value = "/assignInterimTestToStudent.htm", method = RequestMethod.POST)
	public final @ResponseBody List<Long> assignInterimTestToStudent(
			@RequestParam(value = "studentIds[]") List<Long> studentIds,
			@RequestParam(value = "interimTestId") Long testId) {

		// removed schedule page date and time code in the part of F560
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long schoolYear = null;
		schoolYear = user.getContractingOrganization().getCurrentSchoolYear();

		InterimTest iTest = interimService.getInterimTestByTestTestId(testId);
		if (iTest == null) {
			iTest = interimService.getInterimTest(testId);
		}

		Long testSessionId = interimService.insertTestSession(iTest.getTestTestId(), iTest.getTestCollectionId(), user,
				iTest.getName(), schoolYear);

		interimService.insertStudentTestsRecords(testSessionId, studentIds, iTest.getTestTestId(),
				iTest.getTestCollectionId(), iTest.getId(), schoolYear);
		return studentIds;

	}

	@RequestMapping(value = "/updateStudentsTestsTest.htm", method = RequestMethod.POST)
	public final @ResponseBody TestSession updateTestsessionAndStuTest(
			@RequestParam(value = "studentIds[]", required = false) List<Long> studentIds,
			@RequestParam(value = "testSessionId", required = false) Long testSessionId,
			@RequestParam(value = "interimTestId", required = false) Long testId) throws Exception {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		Long schoolYear = null;
		schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		if (schoolYear == null) {
			throw new Exception(
					"Interim Service, Update Test Session, School Year Null for User" + user.getCurrentContextUserId());
		}
		TestSession testsession = interimService.getTestSessionByTestSessionId(testSessionId);

		// removed schedule page date and time code in the part of F560
		interimService.softDeleteStudentsTestsByTestSessionId(testSessionId, studentIds,
				user.getCurrentContextUserId());
		interimService.updateTestsession(testSessionId);
		InterimTest iTest = interimService.getInterimTestByTestTestId(testId);
		interimService.insertStudentTestsRecords(testSessionId, studentIds, testId, iTest.getTestCollectionId(),
				iTest.getId(), schoolYear);

		return testsession;

	}

	@RequestMapping(value = "getTestSessionsByInterimTestId.htm", method = RequestMethod.GET)
	public final List<TestSession> getTestSessionsByInterimTestId(
			@RequestParam(value = "interimTestId") Long interimTestId) {
		InterimTest iTest = interimService.getInterimTest(interimTestId);
		List<TestSession> ts = interimService.getInterimTestSession(iTest.getTestTestId());
		return ts;
	}

	@RequestMapping(value = "getTestSessionsByInterimTest.htm", method = RequestMethod.GET)
	public final @ResponseBody List<TestSession> getStudentByGroups(
			@RequestParam(value = "interimTestId") Long interimTestId) {
		InterimTest iTest = interimService.getInterimTest(interimTestId);
		Long testId = iTest.getTestTestId();
		List<TestSession> testSessionList = interimService.getTestSessionByTestID(testId);
		return testSessionList;

	}

	@RequestMapping(value = "getTestCompletedStudents.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Student> getTestCompletedStudents(
			@RequestParam(value = "testTestId") Long testTestId) {

		// List<Student> students = interimService.getStudentId(testSessionId);
		return interimService.getStudentIdByTestsessionId(testTestId);

	}

	@RequestMapping(value = "getStudentByInterimTestSession.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Student> getStudentByInterimTestSession(
			@RequestParam(value = "testSessionId") String testSessionIdStr) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long groupId = user.getCurrentGroupsId();
		Long organizationId = user.getCurrentOrganizationId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		Long testSessionId = null;
		List<Student> students = new ArrayList<Student>();

		try {
			if (groups != null && groups.getGroupCode() != null && groups.getGroupCode().equalsIgnoreCase("tea")) {
				isTeacher = Boolean.TRUE;
			}
			testSessionId = new Long(testSessionIdStr);
		} catch (Exception e) {

		}
		students = interimService.getStudentIdByTestsessionIdInterim(testSessionId, organizationId,
					user.getCurrentContextUserId(), isTeacher,currentSchoolYear);

		return students;

	}

	@RequestMapping(value = "getReportByStudentCSVByGrid.htm")
	public final @ResponseBody List<String[]> getReportByStudentCSVByGrid(
			@RequestParam(value = "testSessionId") Long testSessionId) throws IOException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		return interimReportService.studentReportCSVByGrid(testSessionId, user);
	}

	@RequestMapping(value = "getReportByClassCSVByGrid.htm")
	public final @ResponseBody List<String[]> getReportByClassCSVByGrid(
			@RequestParam(value = "testSessionId") Long testSessionId) throws IOException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		return interimReportService.classReportCSVByGrid(testSessionId, user);
	}

	@RequestMapping(value = "getReportByTaskCSVByGrid.htm")
	public final @ResponseBody List<String[]> getReportByTaskCSVByGrid(
			@RequestParam(value = "testSessionId") Long testSessionId) throws IOException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		return interimReportService.itemReportCSVByGrid(testSessionId, user);
	}

	@RequestMapping(value = "createInterimGroup.htm", method = RequestMethod.POST)
	public final @ResponseBody Long createInterimGroup(@RequestParam(value = "studentId[]") List<Long> studentIds,
			@RequestParam(value = "groupName") String groupName) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long interimGroupId = interimGroupService.createInterimGroup(studentIds, groupName,
				user.getCurrentContextUserId());
		interimGroupService.addInterimGroupStudents(studentIds, interimGroupId, user);

		return interimGroupId;
	}

	@RequestMapping(value = "updateStudentGroup.htm", method = RequestMethod.POST)
	public final @ResponseBody List<Long> updateStudentGroup(@RequestParam(value = "studentId[]") List<Long> studentIds,
			@RequestParam(value = "InterimGroupId") Long InterimGroupId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		interimGroupService.deleteStudentGroup(InterimGroupId);
		interimGroupService.addInterimGroupStudents(studentIds, InterimGroupId, user);
		return studentIds;

	}

	@RequestMapping(value = "editStudentGroup.htm", method = RequestMethod.POST)
	public final @ResponseBody InterimGroup editStudentGroup(@RequestParam(value = "groupName") String groupName,
			@RequestParam(value = "interimGroupId") Long interimGroupId) {

		interimGroupService.updateGroup(interimGroupId, groupName);
		InterimGroup ig = new InterimGroup();
		ig.setGroupName(groupName);
		ig.setId(interimGroupId);
		return ig;
	}

	@RequestMapping(value = "deleteStudentGroup.htm", method = RequestMethod.POST)
	public final @ResponseBody void deleteStudentGroup(@RequestParam(value = "interimGroupId") Long interimGroupId) {

		interimGroupService.deleteGroup(interimGroupId);
	}

	@RequestMapping(value = "getStudentsByGroup.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Student> getStudentsByGroup(
			@RequestParam(value = "interimGroupId") Long interimGroupId) {

		return interimGroupService.getStudentsByGroup(interimGroupId);
	}

	@RequestMapping(value = "getGroups.htm", method = RequestMethod.GET)
	public final @ResponseBody List<InterimGroup> getGropus(
			@RequestParam(value = "groupName", required = false) String groupName,
			@RequestParam(value = "userName", required = false) String userName) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long groupId = user.getCurrentGroupsId();
		user.getCurrentOrganizationId();
		Groups groups = groupsDao.getGroup(groupId);
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		List<Long> orgIds = user.getAllChilAndSelfOrgIds();
		Long assessmentProgramId = user.getCurrentAssessmentProgramId();
		List<InterimGroup> igroups = new ArrayList<>();
		Boolean isBTCOrDTC = false;
		try {
			if (groups != null && roleOrgTypeId != null && (roleOrgTypeId.equals(5l) || roleOrgTypeId.equals(6l))) {
				isBTCOrDTC = Boolean.TRUE;
			}
		} catch (Exception e) {

		}
		if (isBTCOrDTC) {
			for (Long orgId : orgIds) {
				List<Long> userList = userService.getUsersByOrgIdForInterim(orgId, assessmentProgramId);
				if (!userList.isEmpty()) {
					if ((groupName != null && groupName != "") || (userName != null && groupName != "")) {
						igroups.addAll(interimGroupService.getGroupsByGroupNameAndUserName(groupName, userName,
								userList, orgId));
					} else {
						igroups.addAll(interimGroupService.getGroupsByUser(userList, orgId));
					}
				}
			}
		} else {
			List<Long> l = new ArrayList<>();
			l.add(user.getId());
			if ((groupName != null && groupName != "") || (userName != null && groupName != "")) {
				igroups.addAll(interimGroupService.getGroupsByGroupNameAndUserName(groupName, userName, l,
						user.getCurrentOrganizationId()));
			} else {
				igroups.addAll(interimGroupService.getGroupsByUser(l, user.getCurrentOrganizationId()));
			}

		}

		for (InterimGroup grp : igroups) {
			if (grp.getCreatedUser().equals(user.getId())) {
				grp.setAccess(Boolean.TRUE);
			} else {
				grp.setAccess(Boolean.FALSE);
			}

		}
		return igroups;
	}

	@RequestMapping(value = "getTotalTestSessionDetails.htm", method = RequestMethod.GET)
	public final @ResponseBody InterimTestDTO getTotalTestSessionDetails(
			@RequestParam(value = "testSessionId", required = false) Long testSessionId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userDetails.getUser();
		Long assessmentProgId = currentUser.getCurrentAssessmentProgramId();
		return interimService.getTotalTestSessionDetails(testSessionId,assessmentProgId);
	}

	@RequestMapping(value = "getTestsForReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<InterimTestDTO> getTestsForReports() {

		LOGGER.debug("Entered getTestsForReports.htm ");
		List<InterimTestDTO> messageList = new ArrayList<InterimTestDTO>();
		Map<String, Object> responseMap = new HashMap<String, Object>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long groupId = user.getCurrentGroupsId();
		Long organizationId = user.getCurrentOrganizationId();
		organizationService.get(organizationId);
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isBTCOrDTC = Boolean.FALSE;
		Boolean isTeacher = Boolean.FALSE;
		Long totalCount = 0l;
		Long schoolYear = null;
		Long assessmentProgId = user.getCurrentAssessmentProgramId();
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		Boolean forReports = Boolean.TRUE;
		schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		try {
			if (groups != null && roleOrgTypeId != null && (roleOrgTypeId.equals(5l) || roleOrgTypeId.equals(6l))) {
				isBTCOrDTC = Boolean.TRUE;
			}
			if (groups != null && roleOrgTypeId != null && roleOrgTypeId.equals(7l)) {
				isTeacher = Boolean.TRUE;
			}
		} catch (Exception e) {

		}
		if (isBTCOrDTC) {
			List<Long> orgIds = user.getAllChilAndSelfOrgIds();
			for (Long orgId : orgIds) {
				List<Long> userList = userService.getUsersByOrgIdForInterim(orgId, assessmentProgId);
				if (!userList.isEmpty()) {
					messageList.addAll(interimService.getTotalTests(userList, orgId, schoolYear, forReports, assessmentProgId));
				}
			}

		} else if (isTeacher) {

			List<InterimTestDTO> teacherTests = interimService.getTotalTestsForTeacher(user, schoolYear, forReports, assessmentProgId);
			for (InterimTestDTO teacherTest : teacherTests) {
				if (!messageList.contains(teacherTest) && (teacherTest.getStudentsAssigned() != null
						&& teacherTest.getStudentsAssigned() > new Long(0))) {
					// teacherTest.setAccess(false);
					messageList.add(teacherTest);
				}
			}

		} else {
			List<Long> userList = new ArrayList<>();

			userList.add(user.getId());
			messageList.addAll(interimService.getTotalTests(userList, organizationId, schoolYear, forReports, assessmentProgId));
			totalCount = totalCount + interimService.getInterimTestsCount(user.getId());
		}

		responseMap.put("rows", messageList);
		responseMap.put("total", totalCount);
		LOGGER.trace("Leaving the getTestsForReports.htm method.");
		LOGGER.debug("getting TotalTest ids" + responseMap);
		List<InterimTestDTO> response = new ArrayList<InterimTestDTO>();
		for (InterimTestDTO msg : messageList) {

			/*
			 * if (!msg.getStudentsAssigned().equals(new Long(0))) {
			 * response.add(msg); }
			 */

			if (!msg.getStudentsAttempted().equals(new Long(0))) {
				response.add(msg);
			}

		}
		return response;

	}

	@RequestMapping(value = "getTestSessionDetailsByTestSessionId.htm", method = RequestMethod.GET)
	public final @ResponseBody TestSession getTestSessionDetailsByTestSessionId(
			@RequestParam(value = "testSessionId") Long testSessionId) {

		TestSession testSession = interimService.getTestSessionDetailsByTestSessionId(testSessionId);
		return testSession;

	}

	@RequestMapping(value = "getQuestionsForStudentByTest", method = RequestMethod.GET)
	public final @ResponseBody List<TaskVariant> getQuestionsForScoring(
			@RequestParam(value = "testTestId") Long testTestId) {

		return interimService.getQuestionsForScoring(testTestId);

	}

	@RequestMapping(value = "suspendTestWindow.htm", method = RequestMethod.POST)
	public final @ResponseBody Integer suspendTestWindow(@RequestParam(value = "suspend") Boolean suspend,
			@RequestParam(value = "testSessionId") Long testSessionId) {

		Integer testSession = interimService.suspendTestWindow(suspend, testSessionId);

		return testSession;

	}

	/**
	 * Author: syechuri_sta, vkrishna_sta Added for (F560 Interim – Build,
	 * Assign, Schedule enhancements) Idea is to add an entry in a DB Table, And
	 * Assign Students part of given Roster, Subject and Grades
	 */
	@RequestMapping(value = "/autoAssignInterim.htm", method = RequestMethod.POST)
	public final @ResponseBody Long autoAssignInterim(
			@RequestParam(value = "gradeCourseIds[]", required = false) List<Long> gradeCourseIds,
			@RequestParam(value = "contentAreaIds[]", required = false) List<Long> contentAreaIds,
			@RequestParam(value = "rosterIds[]", required = false) List<Long> rosterIds,
			@RequestParam(value = "interimTestId") Long testId) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long schoolYear = null;
		schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		Long groupId = user.getCurrentGroupsId();
		List<Long> orgIds = user.getAllChilAndSelfOrgIds();
		Long assessmentProgramId= user.getCurrentAssessmentProgramId();
		List<Long> userList = new ArrayList<Long>();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		LOGGER.info("getStudentInfoNew Group Code for User with ID" + user.getCurrentContextUserId().toString() + "is "
				+ groups.getGroupCode());
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		if (groups != null && roleOrgTypeId != null && roleOrgTypeId.equals(7l)) {
			isTeacher = Boolean.TRUE;
		}

		if (isTeacher) {
			userList.add(user.getCurrentContextUserId());
		}
		InterimTest iTest = interimService.getInterimTestByTestTestId(testId);
		if (iTest == null) {
			iTest = interimService.getInterimTest(testId);
		}
		Long testSessionId = interimService.insertTestSession(iTest.getTestTestId(), iTest.getTestCollectionId(), user,
				iTest.getName(), schoolYear);
		List<Long> studentIds = interimService.getStudentsForAutoAssign(rosterIds, gradeCourseIds, contentAreaIds,
				orgIds, userList, schoolYear,assessmentProgramId);
		interimService.insertStudentTestsRecords(testSessionId, studentIds, iTest.getTestTestId(),
				iTest.getTestCollectionId(), iTest.getId(), schoolYear);
		return interimService.insertAutoAssignInterim(gradeCourseIds, contentAreaIds, rosterIds, testSessionId);

	}

	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	@RequestMapping(value = "/getStudentReportActivityDetails.htm", method = RequestMethod.POST)
	public final @ResponseBody List<StudentActivityReport> getStudentReportActivityDetails(
			@RequestParam(value = "studentIds[]") List<Long> studentIds) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long schoolYear = null;
		schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		List<Long> orgIds = user.getAllChilAndSelfOrgIds();
		String assessmentProgramName = user.getCurrentAssessmentProgramName();
		boolean isPLTWUser = false;
		if (assessmentProgramName != null 
				&& CommonConstants.PLTW.equalsIgnoreCase(assessmentProgramName)) {
			isPLTWUser = true;
		}
		Long groupId = user.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		
		if (groups != null && "TEA".equalsIgnoreCase(groups.getGroupCode())) {
			isTeacher = Boolean.TRUE;
		}
		
		return interimService.getStudentReportActivityDetails(studentIds, orgIds, schoolYear, isPLTWUser, isTeacher, user.getId());
	}

	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	@RequestMapping(value = "/getInterimPredictiveStudentScores.htm", method = RequestMethod.POST)
	public final @ResponseBody List<InterimPredictiveStudentScore> getInterimPredictiveStudentScores(
			@RequestParam(value = "studentIds[]") List<Long> studentIds,
			@RequestParam(value = "contentAreaIds[]") List<Long> contentAreaIds,
			@RequestParam(value= "gradeCourseIds[]", required = false) List<Long> gradeCourseIds,
			@RequestParam(value= "orgIds[]") List<Long> orgIds) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long schoolYear = null;
		schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		Long assessmentProgramId= user.getCurrentAssessmentProgramId();
		Long groupId = user.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		
		if (groups != null && "TEA".equalsIgnoreCase(groups.getGroupCode())) {
			isTeacher = Boolean.TRUE;
		}
		
		return interimService.getInterimPredictiveStudentScores(studentIds, orgIds, schoolYear, contentAreaIds,gradeCourseIds,
				assessmentProgramId, isTeacher, user.getId());
	}
	
	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	@RequestMapping(value = "/getTestingCycles.htm", method = RequestMethod.GET)
	public final @ResponseBody List<TestingCycle> getTestingCycles(
			) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long schoolYear = null;
		schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		user.getCurrentAssessmentProgramId();
		return testingCycleMapper.getTestingCyclesByProgramCodeSchoolYear("KAP", schoolYear, "F");
	}
	
	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	/**
	 * vkrishna_sta, Code change for DE16775, This method will be called only for District Level Users and State level users
	 * For District Level users, District Id will not be passed from UI and will not be considered in code,
	 *  whereas for State we will be passing a selected district id
	 */
	@RequestMapping(value = "/getOrganizationDropDownsData.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getOrganizationDropDownsData(
			@RequestParam(value="districtId", required=false) Long districtId,
			@RequestParam(value= "predictiveStudentScore") Boolean predictiveStudentScore) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Organization> organizations = new ArrayList<>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userDetails.getUser();
		Long schoolYear = currentUser.getContractingOrganization().getCurrentSchoolYear();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		Long assessmentProgramId= currentUser.getCurrentAssessmentProgramId();
		/*
		 * For State Above District Level, Pass DistrictId from UI
		 */
		if (roleOrgTypeId.compareTo(5l) < 0) {
			if (districtId == null) {
				throw new Exception("For User Id " + currentUser.getId().toString() + " No District Id Sent");
			}
			organizations = interimService.getInterimSchoolsInDistrict(districtId, schoolYear,predictiveStudentScore,assessmentProgramId );
		} else if (roleOrgTypeId.equals(5l)) {
			organizations = interimService.getInterimSchoolsInDistrict(currentUser.getCurrentOrganizationId(),
					schoolYear,predictiveStudentScore,assessmentProgramId);
		} else {
			throw new Exception("For User Id " + currentUser.getId().toString() + "And Group" + groups.getGroupCode()
					+ " This method should not be called");
		}
		map.put("organizations", organizations);
		return map;
	}

	
	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	/**
	 * vkrishna_sta, Code change for DE16775, This method will be called only for State Level Users to populate interim districts
	 *  
	 *  Throws Exception if any other user type calls this function
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getDistrictDropDownsData.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getDistrictDropDownsData(
			@RequestParam(value= "predictiveStudentScore") Boolean predictiveStudentScore) throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<Organization> organizations = new ArrayList<>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userDetails.getUser();
		Long schoolYear = currentUser.getContractingOrganization().getCurrentSchoolYear();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		Long assessmentProgramId= currentUser.getCurrentAssessmentProgramId();
		
		if (roleOrgTypeId.compareTo(5l) < 0) {
			organizations = interimService.getInterimDistrictsInState(currentUser.getCurrentOrganizationId(),schoolYear,predictiveStudentScore,assessmentProgramId);
		} else {
			throw new Exception("For User Id " + currentUser.getId().toString() + "And Group" + groups.getGroupCode()
					+ " This method should not be called");
		}
		map.put("organizations", organizations);
		return map;
	}

	
	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	@RequestMapping(value = "/getSubjectDropDownsData.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getSubjectDropDownsData(
			@RequestParam(value = "organizationIds[]") List<Long> organizationIds,
			@RequestParam(value= "predictiveStudentScore") Boolean predictiveStudentScore) {
		Map<String, Object> map = new HashMap<>();
		List<ContentArea> subjects = new ArrayList<>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User currentUser = userDetails.getUser();
		Long userId = currentUser.getCurrentContextUserId();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		Long assessmentProgramId= currentUser.getCurrentAssessmentProgramId();
		if (groups != null && roleOrgTypeId != null && roleOrgTypeId.equals(7l)) {
			subjects.addAll(
					interimService.getSubjectNamesByRosterAndOrgList(organizationIds, currentSchoolYear, userId,predictiveStudentScore,assessmentProgramId));
		} else {
			subjects.addAll(interimService.getSubjectNamesByRosterAndOrgList(organizationIds, currentSchoolYear, null,predictiveStudentScore,assessmentProgramId));
		}
		map.put("subjects", subjects);
		return map;
	}

	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	@RequestMapping(value = "/getGradeDropDownsData.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getGradeDropDownsData(
			@RequestParam(value = "organizationIds[]") List<Long> organizationIds,
			@RequestParam(value = "subjectIds[]") List<Long> subjectIds,
			@RequestParam(value= "predictiveStudentScore") Boolean predictiveStudentScore) {

		Map<String, Object> map = new HashMap<>();
		List<GradeCourse> grades = new ArrayList<>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		Long groupId = userDetails.getUser().getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		Long assessmentProgramId= userDetails.getUser().getCurrentAssessmentProgramId();

		if (groups != null && roleOrgTypeId != null && roleOrgTypeId.equals(7l)) {
			grades.addAll(interimService.getGradesBySubjectsAndOrgList(organizationIds, currentSchoolYear, subjectIds,
					userDetails.getUser().getCurrentContextUserId(),predictiveStudentScore, assessmentProgramId));
		} else {
			grades.addAll(
					interimService.getGradesBySubjectsAndOrgList(organizationIds, currentSchoolYear, subjectIds, null,predictiveStudentScore,assessmentProgramId));
		}

		map.put("grades", grades);
		return map;
	}

	/**
	 * Added for (F564 KAP Interim - Improve instructional reports)
	 */
	@RequestMapping(value = "/getStudentDropDownsData.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, Object> getStudentDropDownsData(
			@RequestParam(value = "organizationIds[]") List<Long> organizationIds,
			@RequestParam(value = "subjectIds[]") List<Long> subjectIds,
			@RequestParam(value = "gradeIds[]", required = false) List<Long> gradeIds,
			@RequestParam(value= "predictiveStudentScore") Boolean predictiveStudentScore) {
		Map<String, Object> map = new HashMap<>();
		List<Student> students = new ArrayList<>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		User currentUser = userDetails.getUser();
		Long userId = currentUser.getCurrentContextUserId();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Long roleOrgTypeId = groups.getRoleorgtypeid();
		Long assessmentProgramId=currentUser.getCurrentAssessmentProgramId();
		if (groups != null && roleOrgTypeId != null && roleOrgTypeId.equals(7l)) {
			students.addAll(interimService.getStudentDetailsByGradeAndSubjectAndRoster(organizationIds,
					currentSchoolYear, subjectIds, gradeIds, userId,predictiveStudentScore,assessmentProgramId));
		} else {
			students.addAll(interimService.getStudentDetailsByGradeAndSubjectAndRoster(organizationIds,
					currentSchoolYear, subjectIds, gradeIds, null,predictiveStudentScore,assessmentProgramId));
		}
		map.put("students", students);
		return map;
	}

	@RequestMapping(value = "monitorInterimTestSession.htm")
	public final ModelAndView monitorInterimTestSession(
			@RequestParam(value = "testSessionId", required = false) final Long testSessionId,
			@RequestParam(value = "testSessionName", required = false) String testSessionName,
			@RequestParam(value = "rosterId", required = false) final Long rosterId,
			@RequestParam(value = "testingProgram", required = true) String testingProgramName) {

		LOGGER.trace("Entering the monitorInterimTestSession() method");
		ModelAndView mav = new ModelAndView("monitoring");
		mav.addObject("testSessionId", testSessionId);
		mav.addObject("testSessionName", testSessionName);
		mav.addObject("testingProgramName", testingProgramName);
		mav.addObject("rosterId", rosterId);
		mav.addObject("suppressSelectColumn", true);
		mav.addObject("interimPage", true);
		LOGGER.trace("Leaving the monitorInterimTestSession() method");
		return mav;
	}
	
	@RequestMapping(value="getInterimPredictiveReportBytestsessionId.htm")
	public ResponseEntity<byte[]> getPDF(@RequestParam(value = "testSessionId", required = false) final Long testSessionId,
			                             @RequestParam(value = "testName", required = false) final String testName
			                             ) {
	    
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		User currentUser = userDetails.getUser();
		Long userId = currentUser.getCurrentContextUserId();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		Long organizationId = currentUser.getCurrentOrganizationId();
		
		if (groups != null && groups.getRoleorgtypeid() == 7l) {
			isTeacher = Boolean.TRUE;
		}
		
		ByteArrayOutputStream baOut = interimService.generatePredictiveBundleByTestsessionAndUser(testSessionId,isTeacher,userId,currentSchoolYear,organizationId);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    String filename = testName+"_"+currentUser.getSurName()+".pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(baOut.toByteArray(), headers, HttpStatus.OK);
	   
	    return response;
	}
	@RequestMapping(value = "/isReportGenerated.htm", method = RequestMethod.GET)
	public final @ResponseBody boolean isReportGenerated(@RequestParam(value = "testSessionId", required = false) final Long testSessionId){
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		User currentUser = userDetails.getUser();
		Long userId = currentUser.getCurrentContextUserId();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		Long organizationId = currentUser.getCurrentOrganizationId();
		
		if (groups != null && groups.getRoleorgtypeid() == 7l) {
			isTeacher = Boolean.TRUE;
		}
		int count = interimService.reportCountByTestsessionAndUser(testSessionId,isTeacher,userId,currentSchoolYear,organizationId);
		if(count > 0){
		    return true;
		}else{
			return false;	
		}
	}
	
	/*
	 * F658  -- Adding a Call, to get if Predictive Permission is enabled.
	 */
	@RequestMapping(value="hasViewPredictiveStudentScore.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String,Object> hasViewPredictiveStudentScore()
	{
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Boolean hasViewPredictiveStudentScore =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getViewPredictiveStudentScorePermission());
		 Map<String,Object> ret= new HashMap<>();
		 ret.put("hasViewPredictiveStudentScore", hasViewPredictiveStudentScore);
		return ret;
		
	}
	
	@RequestMapping(value="getInterimSummaryPredictiveReportBytestsessionId.htm")
	public void getInterimSummaryPredictiveReport(HttpServletResponse response,
			@RequestParam(value = "testSessionId", required = false) final Long testSessionId,                             
			@RequestParam(value = "orgTypeCode", required = false) final String orgTypeCode
	) throws Exception {

		LOGGER.trace("Entering the getInterimSummaryPredictiveReport");
		
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
			User currentUser = userDetails.getUser();
			Long assessmentProgId = currentUser.getCurrentAssessmentProgramId();
			PredictiveReportCreditPercent data = batchReportProcessService.selectByTestIdandOrganizationId(orgTypeCode,currentSchoolYear,assessmentProgId,testSessionId);		
			
			OutputStream out = response.getOutputStream();
     		response.setContentType("application/pdf");
			
     		String fileName = "DSUMMARY_Interim_" + sanitizeForPath(data.getDistrictName()) + ".pdf";
			if(data.getAttendanceSchoolId() != null) {
				if(data.getShortOrgName()!=null && !data.getShortOrgName().equals("")){
					data.setInterimSummaryHeaderSchoolName(data.getShortOrgName());
				}else{
	 				data.setInterimSummaryHeaderSchoolName(data.getSchoolName());
	 			}	 				
				fileName = "SSUMMARY_Interim_" + sanitizeForPath(data.getSchoolName()) + ".pdf";
			}else{
				if(data.getShortOrgName()!=null && !data.getShortOrgName().equals("")){
					data.setInterimSummaryHeaderDistrictName(data.getShortOrgName());
				}else{
	 				data.setInterimSummaryHeaderDistrictName(data.getDistrictName());
	 			}
			}
			
			List<AppConfiguration> predictiveSchoolYears = appConfigService.selectByAttributeTypeAndAssessmentProgramId(CommonConstants.PredictiveSchoolYear, assessmentProgId);
     		
     		if (CollectionUtils.isNotEmpty(predictiveSchoolYears) && predictiveSchoolYears.size()==1) {
     			data.setPredictiveSchoolYear(predictiveSchoolYears.get(0).getAttributeValue());
			} else {
				LOGGER.error("None or More than one record found. Please check the PredictiveSchoolYear for the assesment program id : "+ assessmentProgId);
			}
			
			response.setContentType(MimeConstants.MIME_PDF);
			response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
			
			try {
				interimSummaryReportGenerator.generateReportFile(data, out);
     		} catch (Exception e) {
     			LOGGER.error("Caught {} in getInterimSummaryPredictiveReportBytestsessionId() method.", e);
     		}finally {
     			if (out != null) {
     				out.close();
     			}
     		}			
		    
		
	}
	
	protected String sanitizeForPath(String stateStudentIdentifier) {
		return stateStudentIdentifier.replaceAll("[^a-zA-Z0-9.-]", "_");
	}
	
	
	
	@RequestMapping(value="getInterimPredictiveQuestionCSVBytestsessionId.htm")
	public ResponseEntity<byte[]> getQuestionCSV(@RequestParam(value = "testSessionId", required = false) final Long testSessionId) {
	    
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		User currentUser = userDetails.getUser();
		Long userId = currentUser.getCurrentContextUserId();
		Long groupId = currentUser.getCurrentGroupsId();
		Groups groups = groupsDao.getGroup(groupId);
		Boolean isTeacher = Boolean.FALSE;
		Long organizationId = currentUser.getCurrentOrganizationId();
		Boolean districtUser = Boolean.FALSE;
		
		if (groups != null && "TEA".equalsIgnoreCase(groups.getGroupCode())) {
			isTeacher = Boolean.TRUE;
		}else if(groups != null && groups.getOrganizationTypeId().longValue() == CommonConstants.ORGANIZATION_TYPE_ID_5.longValue()) {
			districtUser = Boolean.TRUE;
		}
		
		HttpHeaders headers = new HttpHeaders();
		ByteArrayOutputStream baOut = interimService.getInterimPredictiveQuestionCSVBytestsessionAndUser(testSessionId,isTeacher,districtUser,userId,currentSchoolYear,organizationId, headers);	    
	    ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(baOut.toByteArray(), headers, HttpStatus.OK);
	   
	    return response;
	}
}
