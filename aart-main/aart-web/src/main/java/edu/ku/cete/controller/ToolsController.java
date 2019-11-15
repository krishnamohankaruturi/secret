package edu.ku.cete.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.configuration.SessionRulesConfiguration;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.testsession.TestSessionRoster;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.AuditType;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AuditHistoryViewService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GroupAuthoritiesService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.json.RecordBrowserJsonUtil;
import edu.ku.cete.util.json.TestSessionRosterJsonConverter;

@Controller
public class ToolsController {

	private static final Logger logger = LoggerFactory.getLogger(ToolsController.class);

	private static final String VIEW_TOOLS_TAB_JSP = "toolstab";

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private OrganizationTypeService organizationTypeService;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupsService groupsService;
	@Autowired
	private EnrollmentService enrollmentService;
	/**
	 * permissionUtil
	 */
	@Autowired
	private PermissionUtil permissionUtil;
	/**
	 * recordBrowserJsonUtil
	 */
	@Autowired
	private RecordBrowserJsonUtil recordBrowserJsonUtil;
	
	/**
	 * sessionRulesConfiguration.
	 */
	@Autowired
	private SessionRulesConfiguration sessionRulesConfiguration;
	
	@Autowired
	private TestSessionService testSessionService;

	@Autowired
	private AuditHistoryViewService auditHistoryViewService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private GroupAuthoritiesService groupAuthoritiesService;
	
	/*@RequestMapping(value = "viewToolsTab.htm")
	public final ModelAndView viewToolsTab() {
		logger.trace("--> viewToolsTab");
		ModelAndView mv = new ModelAndView(VIEW_TOOLS_TAB_JSP);
		logger.trace("<-- viewToolsTab");
		return mv;

	}*/
	
	/**
	 * Create help tab under tools section
	 * @return
	 */
	@RequestMapping(value = "helpContent.htm") 
	public final ModelAndView getHelpContent() {
		return new ModelAndView("tools/helpContent");
	}
	
	@RequestMapping(value = "viewToolUserManagement.htm")
	public final ModelAndView getviewToolUserManagementConfiguration() {
		return new ModelAndView("tools/viewToolUserManagement");
	}
	
	
	@RequestMapping(value = "studentInformationMgmt.htm")
	public final ModelAndView getStudentInformationMgmt() {
		return new ModelAndView("tools/viewStudentManagement");
	}
	
	
	
	@RequestMapping(value = "viewToolPermissionsRoles.htm")
	public final ModelAndView getviewToolPermissionsRolesConfiguration() {
		return new ModelAndView("tools/viewToolPermissionsRoles");
	}
	
	@RequestMapping(value = "toolsOrganizationManagement.htm")
	public final ModelAndView gettoolsOrganizationManagementConfiguration() {
		return new ModelAndView("tools/toolsOrganizationManagement");
	}

	@RequestMapping(value = "toolsTestReset.htm")
	public final ModelAndView getToolsTestResetConfiguration() {
		return new ModelAndView("tools/testReset");
	}
	
	@RequestMapping(value = "toolsMiscellaneous.htm")
	public final ModelAndView getToolsMiscellaneousConfiguration() {
		return new ModelAndView("tools/miscellaneous");
	}
	
	@RequestMapping(value = "getSchoolsForManagement.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Organization> getSchoolsForManagement() {
		logger.debug("Entered getSchoolsForManagement.htm ");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		List<Organization> schools = organizationService.getAllChildrenByOrgTypeCode(user.getCurrentOrganizationId(),
				CommonConstants.ORGANIZATION_SCHOOL_CODE);
		logger.debug("Exited getSchoolsForManagement.htm");
		return schools;
	}

	@RequestMapping(value = "getDistrictsForManagement.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Organization> getDistrictsForManagement(
			@RequestParam(value = "organizationId", required = false) Long contractingOrgId) {
		logger.debug("Entered getDistrictsForManagement.htm ");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		List<Organization> districts = organizationService.getAllChildrenByOrgTypeCode(user.getCurrentOrganizationId(),
				CommonConstants.ORGANIZATION_DISTRICT_CODE);
		logger.debug("Exited getDistrictsForManagement.htm");
		return districts;
	}

	@RequestMapping(value = "getOrganizationTypes.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationType> getOrganizationTypes(
			@RequestParam(value = "organizationId", required = false) Long contractingOrgId) {
		logger.debug("Entered getOrganizationTypes.htm");
		List<OrganizationType> organizationTypes = organizationTypeService.getAll();
		logger.debug("Exited getOrganizationTypes.htm");
		return organizationTypes;
	}
	
	@RequestMapping(value = "getSummaryForOrgManagement.htm", method = RequestMethod.POST)
	public final @ResponseBody String getSummaryForOrgManagement(@RequestParam(value = "organizationTypeId") Long organizationTypeId,
			@RequestParam(value = "organizationId") Long organizationId,
			@RequestParam(value = "organizationName") String organizationName) throws Exception {
		logger.debug("--> getSummaryForOrgManagement");
		try {
			String orgTypeCode = organizationTypeService.get(organizationTypeId).getTypeCode();
			if (!organizationService.validateOrganizationType(organizationId, orgTypeCode)) {
				throw new Exception(organizationName + " is not a valid organization type ");
			} else {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				User user = userDetails.getUser();
				Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();

				Map<String, Object> model = new HashMap<String, Object>();

				ObjectMapper mapper = new ObjectMapper();
				mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
				mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

				Long studentCount = enrollmentService.getCountByOrganizationId(organizationId, schoolYear);
				Long userCount = userService.getCountByOrganizationId(organizationId);
				Long rosterCount = rosterService.getCountByOrganizationId(organizationId, schoolYear);
				
				model.put("status", "success");
				model.put("successMessage", organizationName + " has : ");
				model.put("studentCount", studentCount);
				model.put("userCount", userCount);
				model.put("rosterCount", rosterCount);
				String modelJson = mapper.writeValueAsString(model);
				logger.debug("<-- getSummaryForOrgManagement");
				return modelJson;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while merging school: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getSummaryForDeactivateOrgManagement.htm", method = RequestMethod.POST)
	public final @ResponseBody String getSummaryForDeactivateOrgManagement(@RequestParam(value = "organizationTypeId") Long organizationTypeId,
			@RequestParam(value = "organizationId") Long organizationId,
			@RequestParam(value = "organizationName") String organizationName) throws Exception {
		logger.debug("--> getSummaryForDeactivateOrgManagement");
		try {
			String orgTypeCode = organizationTypeService.get(organizationTypeId).getTypeCode();
			if (!organizationService.validateOrganizationType(organizationId, orgTypeCode)) {
				throw new Exception(organizationName + " is not a valid organization type ");
			} else {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				User user = userDetails.getUser();
				Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();

				Map<String, Object> model = new HashMap<String, Object>();

				ObjectMapper mapper = new ObjectMapper();
				mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
				mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

				Long studentCount = enrollmentService.getDeactivateCountByOrganizationId(organizationId, schoolYear);
				Long userCount = userService.getDeactivateCountByOrganizationId(organizationId);
				Long rosterCount = rosterService.getDeactivateCountByOrganizationId(organizationId, schoolYear);
				
				model.put("status", "success");
				model.put("successMessage", organizationName + " has : ");
				model.put("studentCount", studentCount);
				model.put("userCount", userCount);
				model.put("rosterCount", rosterCount);
				String modelJson = mapper.writeValueAsString(model);
				logger.debug("<-- getSummaryForDeactivateOrgManagement");
				return modelJson;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while merging school: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	
	@RequestMapping(value = "mergeSchool.htm", method = RequestMethod.POST)
	public final @ResponseBody String mergeSchool(@RequestParam(value = "sourceSchool") Long sourceSchool,
			@RequestParam(value = "destinationSchool") Long destinationSchool,
			@RequestParam(value = "sourceSchoolName") String sourceSchoolName,
			@RequestParam(value = "destinationSchoolName") String destinationSchoolName) throws Exception {
		logger.debug("--> mergeSchool");
		try {
			if (sourceSchool.equals(destinationSchool)) {
				throw new Exception(
						sourceSchoolName + " and " + destinationSchoolName + " are same. Merging is not possible.");
			} else {

				Map<String, Object> model = new HashMap<String, Object>();

				ObjectMapper mapper = new ObjectMapper();
				mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
				mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

				organizationService.mergeSchool(sourceSchool, destinationSchool);

				model.put("status", "success");
				model.put("successMessage", "Successfully merged school <b>" + sourceSchoolName +"</b> with <b>" + destinationSchoolName+"</b>");
				String modelJson = mapper.writeValueAsString(model);
				logger.debug("<-- mergeSchool");
				return modelJson;
			}
		} catch (Exception e) {
			logger.error("Exception occurred while merging school: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "moveSchool.htm", method = RequestMethod.POST)
	public final @ResponseBody String moveSchool(@RequestParam(value = "sourceSchool") Long sourceSchool,
			@RequestParam(value = "destinationDistrict") Long destinationDistrict,
			@RequestParam(value = "sourceSchoolName") String sourceSchoolName,
			@RequestParam(value = "destinationDistrictName") String destinationDistrictName) throws Exception {
		logger.debug("--> moveSchool");
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

			organizationService.moveSchool(sourceSchool, destinationDistrict);

			model.put("status", "success");
			model.put("successMessage", "Successfully moved school <b>" + sourceSchoolName +"</b> to district <b>" + destinationDistrictName +"</b>");
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("<-- moveSchool");
			return modelJson;
		} catch (Exception e) {
			logger.error("Exception occurred while moving school: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "disableSchool.htm", method = RequestMethod.POST)
	public final @ResponseBody String disableSchool(@RequestParam(value = "schoolId") Long schoolId,
			@RequestParam("organizationName") String organizationName) throws Exception {
		logger.debug("--> disableSchool");
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

			organizationService.disableSchool(schoolId);

			model.put("status", "success");
			model.put("successMessage", "Successfully disabled school <b>" + organizationName + "</b>");
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("<-- disableSchool");
			return modelJson;
		} catch (Exception e) {
			logger.error("Exception occurred while disabling school: " + organizationName, e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "enableSchool.htm", method = RequestMethod.POST)
	public final @ResponseBody String enableSchool(@RequestParam(value = "schoolId") Long schoolId,
			@RequestParam("organizationName") String organizationName) throws Exception {
		logger.debug("--> enableSchool");
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

			organizationService.enableSchool(schoolId);

			model.put("status", "success");
			model.put("successMessage", "Successfully enabled school <b>" + organizationName + "</b>");
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("<-- enableSchool");
			return modelJson;
		} catch (Exception e) {
			logger.error("Exception occurred while enabling school: " + organizationName, e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}
	
	@RequestMapping(value = "disableDistrict.htm", method = RequestMethod.POST)
	public final @ResponseBody String disableDistrict(@RequestParam(value = "districtId") Long districtId,
			@RequestParam("organizationName") String organizationName)
			throws Exception {
		logger.debug("--> disableDistrict");
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

			organizationService.disableDistrict(districtId);

			model.put("status", "success");
			model.put("successMessage", "Successfully disabled district " + organizationName );
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("<-- disableDistrict");
			return modelJson;
		} catch (Exception e) {
			logger.error("Exception occurred while disabling district: " + organizationName, e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}
	
	@RequestMapping(value = "downloadAuditHistory.htm")
	public final void downloadAuditHistory(final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {
		logger.debug("Entered into downloadAuditHistory method");
		auditHistoryViewService.downloadAuditHistory(response, request);
		logger.debug("Exited from downloadAuditHistory method");
	}
	
	@RequestMapping(value = "downloadKAPCustomExtract.htm")
	public final void downloadKAPCustomExtract(final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {
		logger.debug("Entered into downloadAuditHistory method");
		auditHistoryViewService.downloadKAPCustomExtract(response, request);
		logger.debug("Exited from viewAuditHistory method");
	}


	@RequestMapping(value = "auditHistoryTableList.htm", method = RequestMethod.GET)
	public final @ResponseBody List<AuditType> getAuditHistoryTableList() {
		logger.debug("Entered into auditHistoryTableList method");
		Map<String, String> auditHistoryTableList = new HashMap<String, String>();
		List<AuditType> auditTypeList=auditHistoryViewService.getAuditTypeList();
		logger.debug("displaying auditHistoryTableList:" + auditHistoryTableList);
		logger.debug("Exited from  auditHistoryTableList");
		return auditTypeList;
	}

	@RequestMapping(value = "getTestsessionsForStudent.htm", method = RequestMethod.POST)
	public final @ResponseBody
	JsonResultSet getRosterStudents(
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam("filters") String filters,
			@RequestParam("studentId") Long studentId)
	{
		int currentPage = -1;
		int limitCount = -1;
		int totalCount = 1;
		JsonResultSet jsonResultSet = null;
		Boolean showExpired = true;
		Boolean includeCompletedTestSession=true;
		Boolean includeInProgressTestSession=true;


		// TODO move all 3 steps to a service.
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Long assessmentProgramId=userDetails.getUser().getCurrentAssessmentProgramId();
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);

		Map<String, String> studentRosterCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
		TimerUtil timerUtil = TimerUtil.getInstance();
		Boolean hasQCCompletePermission = permissionUtil.hasPermission(
				userDetails.getAuthorities(), RestrictedResourceConfiguration
						.getQualityControlCompletePermission());
		// US12810 - if the user has qc complete permission then we want to show
		// qccomplete true and false
		// so send null as qcComplete to remove where clause item
		Boolean qcComplete = true;
		if (hasQCCompletePermission) {
			qcComplete = null;
		}
		
		List<TestSessionRoster> testSessionRosters =null;
			testSessionRosters = testSessionService.getTestSessionsByStudentId(
				userDetails, permissionUtil.hasPermission(
						userDetails.getAuthorities(),
						RestrictedResourceConfiguration
								.getViewAllRostersPermissionCode()),
				sortByColumn, sortType,
				(currentPage - 1) * limitCount, limitCount,
				studentRosterCriteriaMap, sessionRulesConfiguration
						.getSystemEnrollmentCategory().getId(),
				sessionRulesConfiguration.getManualEnrollmentCategory()
						.getId(), permissionUtil.hasPermission(userDetails
						.getAuthorities(), RestrictedResourceConfiguration
						.getViewHighStakesTestSessionsPermission()), qcComplete,
				assessmentProgramId, showExpired, hasQCCompletePermission, includeCompletedTestSession,includeInProgressTestSession,studentId);
		timerUtil
				.resetAndLog(logger, "Getting TestSessions ByStudent took ");
		if(!testSessionRosters.isEmpty()) {
			totalCount = testSessionRosters.get(0).getTotalRecords();
		}
		timerUtil.resetAndLog(logger,
				"Counting getTestSessionsByStudent took ");

		jsonResultSet = TestSessionRosterJsonConverter
				.convertToStudentRosterJson(testSessionRosters, totalCount,
						currentPage, limitCount);

		logger.trace("Leaving the getting TestSessions ByStudent page.");
		return jsonResultSet;
	}
	
	@RequestMapping(value = "resetDLMTestlet.htm", method = RequestMethod.POST)
	public final @ResponseBody	String resetDLMTestlet(
			@RequestParam("studentId") Long studentId,
			@RequestParam("testSessionId") Long testSessionId){
		logger.debug("Entered into resetDLMTestlet method");
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			
			Long contentAreaId=testSessionService.getContentAreaIdByTestSession(testSessionId, studentId);
			testSessionService.resetDLMTestlet(studentId,testSessionId,contentAreaId);
			testSessionService.deleteCurrentTest(testSessionId,studentId);
		
			model.put("status", "success");
			model.put("successMessage", "Successfully reset Testlet");
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("Exited from resetDLMTestlet method");
			return modelJson;
		} catch (Exception e) {
			logger.error("Exception occurred while reset Testlet ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
   }
	
	
	@RequestMapping(value = "getTestsessionsForStudentLCS.htm", method = RequestMethod.POST)
	public final @ResponseBody
	JsonResultSet getRosterStudentsForLCS(
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam("filters") String filters,
			@RequestParam("studentId") Long studentId)
	{
		int currentPage = -1;
		int limitCount = -1;
		int totalCount = 1;
		JsonResultSet jsonResultSet = null;
		Boolean showExpired = true;
		Boolean includeCompletedTestSession=true;
		Boolean includeInProgressTestSession=true;


		// TODO move all 3 steps to a service.
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Long assessmentProgramId=userDetails.getUser().getCurrentAssessmentProgramId();
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);

		Map<String, String> studentRosterCriteriaMap = recordBrowserJsonUtil
				.constructRecordBrowserFilterCriteria(filters);
		TimerUtil timerUtil = TimerUtil.getInstance();
		Boolean hasQCCompletePermission = permissionUtil.hasPermission(
				userDetails.getAuthorities(), RestrictedResourceConfiguration
						.getQualityControlCompletePermission());
		// US12810 - if the user has qc complete permission then we want to show
		// qccomplete true and false
		// so send null as qcComplete to remove where clause item
		Boolean qcComplete = true;
		if (hasQCCompletePermission) {
			qcComplete = null;
		}
		
		List<TestSessionRoster> testSessionRosters =null;
			testSessionRosters = testSessionService.getTestSessionsByStudentIdForLCS(
				userDetails, permissionUtil.hasPermission(
						userDetails.getAuthorities(),
						RestrictedResourceConfiguration
								.getViewAllRostersPermissionCode()),
				sortByColumn, sortType,
				(currentPage - 1) * limitCount, limitCount,
				studentRosterCriteriaMap, sessionRulesConfiguration
						.getSystemEnrollmentCategory().getId(),
				sessionRulesConfiguration.getManualEnrollmentCategory()
						.getId(), permissionUtil.hasPermission(userDetails
						.getAuthorities(), RestrictedResourceConfiguration
						.getViewHighStakesTestSessionsPermission()), qcComplete,
				assessmentProgramId, showExpired, hasQCCompletePermission, includeCompletedTestSession,includeInProgressTestSession,studentId);
		timerUtil
				.resetAndLog(logger, "Getting TestSessions ByStudent For Lcs took ");
		if(!testSessionRosters.isEmpty()) {
			totalCount = testSessionRosters.get(0).getTotalRecords();
		}
		timerUtil.resetAndLog(logger,
				"Counting getTestSessionsByStudent For Lcs  took ");

		jsonResultSet = TestSessionRosterJsonConverter
				.convertToStudentRosterJson(testSessionRosters, totalCount,
						currentPage, limitCount);

		logger.trace("Leaving the getting TestSessions ByStudent For Lcs page.");
		return jsonResultSet;
	}
	
	@RequestMapping(value = "breakLCSConnection.htm", method = RequestMethod.POST)
	public final @ResponseBody String breakLCSConnection(
			@RequestParam(value = "lcsOnly", required = false) boolean lcsOnly,
			@RequestParam(value = "studentOnly", required = false) boolean studentOnly,
			@RequestParam(value = "studentId", required = false) Long studentId,
			@RequestParam(value = "testSessionId", required = false) Long testSessionId,
			@RequestParam(value = "lcsId", required = false) String lcsId,
			@RequestParam(value = "stateStudentIdentifier", required = false) String stateStudentIdentifier) {
		try {
			logger.debug("Entered into breakLCSConnection method");
			if (studentOnly) {
				boolean isStudentPresent = false;
				List<Long> studentIdsForStudentOnly = null;
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal();
				User user = userDetails.getUser();
				Organization currentOrganization = userDetails.getUser().getOrganization();

				if (stateStudentIdentifier != null) {
					if (currentOrganization.getOrganizationType().getTypeCode().equalsIgnoreCase("CONS")) {
						studentIdsForStudentOnly = testSessionService.checkIsStudentPresent(stateStudentIdentifier,
								null);
					} else {
						studentIdsForStudentOnly = testSessionService.checkIsStudentPresent(stateStudentIdentifier,
								user.getContractingOrgId());
					}
					if (studentIdsForStudentOnly != null && !studentIdsForStudentOnly.isEmpty()) {
						isStudentPresent = true;
					}
				}
				if (isStudentPresent) {
					for (Long studentIdForStudentOnly : studentIdsForStudentOnly) {
						testSessionService.deactivateLcsStudentsTestsForStudentOnly(studentIdForStudentOnly);
					}
				} else {
					throw new Exception(
							"Student is not present for stateStudentIdentifier : " + stateStudentIdentifier);
				}
			} else if (lcsOnly) {
				boolean isLcsPresent = false;
				isLcsPresent = testSessionService.isLCSIdPresent(lcsId);
				if (isLcsPresent) {
					testSessionService.deactivateLcsStudentsTestsForLcsOnly(lcsId);
				} else {
					throw new Exception("Lcs is not present for the id : " + lcsId);
				}
			} else if (studentOnly == false && lcsOnly == false) {
				testSessionService.deactivateLcsTests(studentId, testSessionId);
			}

			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			model.put("status", "success");
			model.put("successMessage", "Successfully break LCS connection");
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("Exited from breakLCSConnection method");
			return modelJson;
		} catch (Exception e) {
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage())
					+ "\"}";
		}
	}
	
	@RequestMapping(value = "enableDistrict.htm", method = RequestMethod.POST)
	public final @ResponseBody String enableDistrict(@RequestParam(value = "districtId") Long districtId,
			@RequestParam("organizationName") String organizationName)
			throws Exception {
		logger.debug("--> enableDistrict");
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

			organizationService.enableDistrict(districtId);

			model.put("status", "success");
			model.put("successMessage", "Successfully enabled district " + organizationName );
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("<-- enableDistrict");
			return modelJson;
		} catch (Exception e) {
			logger.error("Exception occurred while enabling district: " + organizationName, e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getInternalUserDetails.htm", method = RequestMethod.POST)
	public final @ResponseBody String getInternalUserDetails(@RequestParam(value = "userId") Long userId,
			@RequestParam("isInternalUserFlag") Boolean internalUserFlag) throws Exception {
		logger.debug("--> getInternalUserDetails");
		try {
			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));

			Boolean isUserUpdate = userService.updateInternalUserFlag(userId, internalUserFlag);
			if (isUserUpdate) {
				model.put("success", "Successfully Updated User");
			} else {
				model.put("error", "failed");
			}
			model.put("successMessage", "Successfully Updated User " + userId);
			String modelJson = mapper.writeValueAsString(model);
			logger.debug("<-- getInternalUserDetails");
			return modelJson;
		} catch (Exception e) {
			logger.error("Exception occurred while updating user: " + userId, e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}
	
	@RequestMapping(value = "getStatesOrgsForaudithistoryByUser.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Organization> getStatesOrgsForaudithistoryByUser(){
		logger.trace("Entering the getStatesOrgsForaudithistoryByUser() method");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();	       
		List<Organization> stateForAuditHistoryLog = organizationService.getStateByUserIdForAuditHistoryLog(userId);	        
		logger.trace("Leaving the getStatesOrgsForaudithistoryByUser() method");
		return stateForAuditHistoryLog;
		
	}
	
	@RequestMapping(value = "getRolesOrgsForaudithistoryByUser.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Groups> getRolesOrgsForaudithistoryByUser(){
		logger.trace("Entering the getStatesOrgsForaudithistoryByUser() method");        
		List<Groups> rolesForAuditHistoryLog = groupsService.getRolesForNotifications();      
	
		logger.trace("Leaving the getRolesOrgsForaudithistoryByUser() method");
		return rolesForAuditHistoryLog;
		
	}
	
	@RequestMapping(value = "getAssessmentProgramsByUser.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgram> getAssessmentProgramsByUser() {
		logger.trace("Entering the getAssessmentProgramsByUser() method");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Long userId = user.getId();	
		List<AssessmentProgram> assessmentPrograms = new ArrayList<AssessmentProgram>();		
		assessmentPrograms = assessmentProgramService.getAllAssessmentProgramByUserId(userId);
		Collections.sort(assessmentPrograms, assessmentProgramComparator);	
		logger.trace("Leaving the getAssessmentProgramsByUser() method");
		return assessmentPrograms;
	}
	
     
     @RequestMapping(value = "generateStudentPassword.htm", method=RequestMethod.GET)
     public final @ResponseBody Map<String,String> generateStudentPassword(){
    	 Map<String,String> returnMap= new HashMap <>();
    	 returnMap.put("generatedPassword",studentService.generateStudentPassword() ) ;
    	 return returnMap;
     }
     
     @RequestMapping(value = "getStudentData.htm", method=RequestMethod.GET)
     public final @ResponseBody Student getStudentData(@RequestParam(value = "studentId") Long studentId){
    	 return studentService.getByStudentID(studentId);
     }
	
    static Comparator<AssessmentProgram> assessmentProgramComparator = new Comparator<AssessmentProgram>(){
	   	public int compare(AssessmentProgram ap1, AssessmentProgram ap2){
	   		return ap1.getProgramName().compareToIgnoreCase(ap2.getProgramName());
	   	}
	};
	
	@RequestMapping(value = "generatePermissionsAndRolesExtract.htm")
	public final void generatePermissionsAndRolesExtract(final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {
		logger.debug("Entered into downloadPermissionsAndRolesExtract method");
		List<String> stateIds= Arrays.asList(request.getParameter("stateIds").split(","));
		List<String> assessmentprogramIds= Arrays.asList(request.getParameter("assessmentprogramIds").split(","));

		groupAuthoritiesService.generatePermissionsAndRolesExtract(response, request,stateIds,assessmentprogramIds);
		logger.debug("Exited from downloadPermissionsAndRolesExtract method");
	}
	
	@RequestMapping(value = "getPermittedStateIds.htm", method=RequestMethod.GET)
    public final @ResponseBody List<Organization> getPermittedStateIds(){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long orgId = userDetails.getUser().getOrganizationId();
		if(userDetails.getUser().getGroupCode().equals("GSAD")){
			return organizationService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
		}else {
			return organizationService.getPermittedStateIds(userDetails.getUserId());
		}
    }
	
	@RequestMapping(value = "getPermittedAPsBySelectedStateIds.htm", method = RequestMethod.GET)
	public final @ResponseBody List<AssessmentProgram> getPermittedAPsBySelectedStateIds(
			@RequestParam(value = "stateIds[]") List<Long> stateIds) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if(userDetails.getUser().getGroupCode().equals("GSAD")){
			return assessmentProgramService.getPermittedAPsBySelectedStateIds(stateIds, null);
		}else {
			return assessmentProgramService.getPermittedAPsBySelectedStateIds(stateIds, userDetails.getUserId());
		}
	}
}
