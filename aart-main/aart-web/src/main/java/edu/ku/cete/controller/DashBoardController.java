package edu.ku.cete.controller;

/**
 * @author v673j085
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.apierrors.ApiAllErrorRecords;
import edu.ku.cete.domain.apierrors.ApiErrorsRecord;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.dashboard.Attr;
import edu.ku.cete.domain.dashboard.CourseAttr;
import edu.ku.cete.domain.dashboard.ReactivationsDetail;
import edu.ku.cete.domain.dashboard.ReactivationsSummary;
import edu.ku.cete.domain.dashboard.TestingOutsideHours;
import edu.ku.cete.domain.dashboard.ShortDurationTesting;
import edu.ku.cete.domain.dashboard.TestingSummary;
import edu.ku.cete.domain.report.DashboardReactivations;
import edu.ku.cete.domain.report.DashboardScoringSummary;
import edu.ku.cete.domain.report.DashboardTestingOutsideHours;
import edu.ku.cete.domain.report.DashboardShortDurationTesting;
import edu.ku.cete.domain.report.DashboardTestingSummary;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecordDTO;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganization;
import edu.ku.cete.ksde.kids.result.KidsAllErrorMessages;
import edu.ku.cete.ksde.kids.result.KidsDashboardRecord;
import edu.ku.cete.ksde.kids.result.KidsRecentRecords;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.DashboardMessageService;
import edu.ku.cete.service.DashboardService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.impl.DashboardServiceImpl;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.json.ApiErrorsJsonConverter;
import edu.ku.cete.util.json.KidsDashboardJsonConverter;
import edu.ku.cete.util.json.KidsRecentRecordsJsonConvertor;
import edu.ku.cete.util.json.TestAssignmentJsonConverter;

@Controller
public class DashBoardController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashBoardController.class);
	
	private static final String VIEW_DASHBOARD_TAB_JSP = "dashboardtab";
	
	private static final String CSV_DATE_STRING = "yyyyMMdd-hhmm-a";
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private DashboardMessageService dashboardMessageService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
    private RosterService rosterService;
	
	@RequestMapping(value = "dashboard.htm")
	public final ModelAndView viewDashBoard() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		ModelAndView mav = new ModelAndView(VIEW_DASHBOARD_TAB_JSP);
		Date today = new Date();
		
		SimpleDateFormat labelFormat = new SimpleDateFormat("M/dd");
		SimpleDateFormat asOfStringFormat = new SimpleDateFormat("EEEEEE M/dd/yyyy hh:mm a z");
		
		TimeZone tz = orgService.getTimeZoneForOrganization(user.getCurrentOrganizationId());
		if (tz == null) {
			// default to central
			tz = TimeZone.getTimeZone("US/Central");
		}
		labelFormat.setTimeZone(tz);
		asOfStringFormat.setTimeZone(tz);
		
		Date mostRecentRunTime = dashboardService.getMostRecentRunTime();
		
		Calendar cal = new GregorianCalendar();
		cal.setTime(today);
		
		mav.addObject("asOfString", mostRecentRunTime == null ? "--" : asOfStringFormat.format(mostRecentRunTime));
		mav.addObject("today", labelFormat.format(today));
		cal.add(Calendar.DATE, -1);
		mav.addObject("priorDay", labelFormat.format(cal.getTime()));
		mav.addObject("currentSchoolYear", user.getContractingOrganization().getCurrentSchoolYear());
		for (AssessmentProgram ap : user.getUserAssessmentPrograms()) {
			mav.addObject("is" + ap.getAbbreviatedname(), true);
		}
		
		return mav;
	}
	
	@RequestMapping(value = "getSelectDashboardGridFilterData.htm", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getSelectDashboardGridFilterData(
			@RequestParam(value ="orgId", required = false) Long orgId,
			@RequestParam(value = "stateStudentIdentifier", required = false) String stateStudentIdentifier) {		
		Map<String, Object> selectRecordTypeValues = new HashMap<String, Object>();
		Long organizationId = getOrganization(orgId);
		List<String> recordTypes = dashboardService.getRecordTypes(organizationId, stateStudentIdentifier);
		List<String> messageTypes = dashboardService.getMessageTypes(organizationId, stateStudentIdentifier);
		selectRecordTypeValues.put("recordTypes", recordTypes);
		selectRecordTypeValues.put("messageTypes", messageTypes);
		return selectRecordTypeValues;
	}
	
	@RequestMapping(value = "getKidsErrorMessages.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getKidsErrorMessages(
			@RequestParam(value ="orgId", required = false) Long orgId,
			@RequestParam(value = "stateStudentIdentifier", required = false) String stateStudentIdentifier,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters) throws IOException {
		
		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		Integer totalCount = -1;
		
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		Map<String, Object> criteria = new HashMap<String, Object>();
		
		Long organizationId = getOrganization(orgId);
		criteria.put("organizationId", organizationId);
		criteria.put("ssid", stateStudentIdentifier);
		criteria.put("stateId", getContractingOrgid());
		populateCriteria(criteria, filters);
		
		List<KidsDashboardRecord> kidsErrorMessages = dashboardService.
				getDashBoardMessagesToView(criteria, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount); 
		
		totalCount = dashboardService.getCountOfDashBoardMessagesToView(criteria);
		jqGridJSONModel = KidsDashboardJsonConverter.convertToOrganizationJson(
				kidsErrorMessages, totalCount, currentPage, limitCount);
		
		return jqGridJSONModel;
	}
	
	@RequestMapping(value = "getAllErrorMessages.htm", method = RequestMethod.GET)
	public @ResponseBody String getAllErrorMessages(@RequestParam(value ="orgId", required = false) Long orgId) throws JsonProcessingException {		
		String response = StringUtils.EMPTY;		
		Map<String, Object> criteria = new HashMap<String, Object>();		
		Long organizationId = getOrganization(orgId);
		criteria.put("organizationId", organizationId);
		criteria.put("stateId", getContractingOrgid());
		ObjectMapper mapper = new ObjectMapper();
		List<KidsAllErrorMessages> kidsErrorMessages = dashboardService.getAllErrorMessages(criteria);
		response = mapper.writeValueAsString(kidsErrorMessages);
		return response;
	}
	
	@RequestMapping(value = "geRecentKidsRecord.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel geRecentKidsRecord(			
			@RequestParam(value = "stateStudentIdentifier", required = false) String stateStudentIdentifier,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters) throws IOException {
		
		JQGridJSONModel jqGridJSONModel = null;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String currentSchoolYear = String.valueOf(userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
		
		Map<String, Object> criteria = new HashMap<String, Object>();		
		criteria.put("stateStudentIdentifier", stateStudentIdentifier);
		criteria.put("currentSchoolYear", currentSchoolYear);
		criteria.put("stateId", getContractingOrgid());
		populateCriteria(criteria, filters);		
		
		List<KidsRecentRecords> kidsRecentRecords = dashboardService.geRecentKidsRecord(criteria);
		jqGridJSONModel = KidsRecentRecordsJsonConvertor.convertToOrganizationJson(kidsRecentRecords);
		return jqGridJSONModel;
	}
	
	private Long getOrganization(Long orgId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		Long organizationId = orgId != null ? orgId : user.getOrganizationId();
		return organizationId;
	}
	
	private Long getContractingOrgid() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();		
		return user.getContractingOrgId();
	}
	
	private void populateCriteria(Map<String, Object> criteria, String filters)
			throws IOException {

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
	
	@RequestMapping(value = "getTestingSummary.htm", method = RequestMethod.GET)
	public @ResponseBody String getTestingSummary(
			@RequestParam(value = "stateOrgId", required = false) Long stateOrgId, 
			@RequestParam(value = "districtOrgId", required = false) Long districtOrgId, 
			@RequestParam(value = "schoolOrgId", required = false) Long schoolOrgId, 
			@RequestParam(value = "APName", required = false) String APName) throws JsonProcessingException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_TESTING_SUMMARY_DASHBOARD"))) {
			Organization org = null;
			OrganizationTreeDetail otd = null;
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			//Added this to get the userid at teacher level, schoolyear and the current assessment program
			Long userId = user.getId();
			Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
			
			if (stateOrgId == null && districtOrgId == null && schoolOrgId == null) {
				Long userOrgId = user.getCurrentOrganizationId();
				String userOrgType = user.getCurrentOrganizationType();
				stateOrgId = user.getContractingOrgId();
				if (userOrgType.equals(CommonConstants.ORGANIZATION_STATE_CODE)){
					org = orgService.get(stateOrgId);
				} else if (userOrgType.equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
					districtOrgId = userOrgId;
					org = orgService.get(districtOrgId);
				} else if (userOrgType.equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)) {
					schoolOrgId = userOrgId;
					otd = orgService.getOrganizationDetailBySchoolId(schoolOrgId);
					districtOrgId = otd.getDistrictId();
				}
					
			}
			
			//Added for getting Details of All states for an User
			List<Organization> states = null;
			states = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, userId);
			List<Long> stateIdList = new ArrayList<Long>();
			if(states != null) {
				for(Organization st: states) {
					stateIdList.add(st.getId());
				}
			}
			
			Long checkOrgId = schoolOrgId != null ? schoolOrgId : districtOrgId != null ? districtOrgId : stateOrgId; 
			if (checkOrgId != user.getCurrentOrganizationId() && !stateIdList.contains(checkOrgId)) {
				Boolean childValid = orgService.isChildOf(user.getCurrentOrganizationId(), checkOrgId);
				if (!Boolean.TRUE.equals(childValid)) {
					return objectToJson(new Wrapper());
				}
			}
			
			List<Long> classroomIds= new ArrayList<>();
			if (user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW) && user.isTeacher()) {
				//Add a method to get the classroom ids
				classroomIds = rosterService.getClassroomIds(userId, schoolYear);
			}
			List<Long> apIds = new ArrayList<Long>();
			for (edu.ku.cete.domain.content.AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			List<DashboardTestingSummary> testingSummaries = null;
			
			//Added showClassroomId for checking whether to show Classroom ID or not.
			Boolean showClassroomId = false;
			if(user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW) && (user.isTeacher() || !(user.getCurrentOrganizationType().equals(CommonConstants.ORGANIZATION_STATE_TYPE_ID)))){
				 showClassroomId = true;
			}
			
			testingSummaries = dashboardService.getTestingSummary(stateOrgId, districtOrgId, schoolOrgId, apIds, showClassroomId, classroomIds);
			boolean hasReactivationPermission = auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_REACTIVATIONS_DASHBOARD"));
			boolean hasMiddayRunCompleted = dashboardService.hasMiddayRunCompleted();
			List<TestingSummary> dtoSummaries = TestingSummary.convertList(testingSummaries, hasReactivationPermission, hasMiddayRunCompleted, showClassroomId);
			
			//Added this section for all PLTW roles other than State level role
			if(showClassroomId) {
				Map<String, Integer> apRows = new HashMap<String, Integer>();
				Map<String, Integer> courseRows = new HashMap<String, Integer>();
				
				for (TestingSummary summary : dtoSummaries){
					//count the entries by assessment program
					if (!apRows.containsKey(summary.getAssessmentProgram())){
						apRows.put(summary.getAssessmentProgram(), 1);
					}else{
						apRows.put(summary.getAssessmentProgram(), apRows.get(summary.getAssessmentProgram())+1);
					}
					//count the entries by course by assessment program
					if (!courseRows.containsKey(summary.getAssessmentProgram()+summary.getSubject())){
						courseRows.put(summary.getAssessmentProgram()+summary.getSubject(), 1); 
					}else{
						courseRows.put(summary.getAssessmentProgram()+summary.getSubject(), courseRows.get(summary.getAssessmentProgram()+summary.getSubject())+1);
						
					}
				}
					Integer rowId=1;
				for (TestingSummary summary : dtoSummaries){
					Integer apCount = apRows.get(summary.getAssessmentProgram());
					Attr attr = null;
					if (apCount != null){
						attr =  new Attr(apCount);
						apRows.remove(summary.getAssessmentProgram());
					}else{
						attr =  new Attr(0);
					}
					summary.setAttr(attr);
					summary.setRowId("TS_"+rowId);
					
					Integer courseCount = courseRows.get(summary.getAssessmentProgram()+summary.getSubject());
					CourseAttr courseAttr = null;
					if (courseCount != null){
						courseAttr =  new CourseAttr(courseCount);
						courseRows.remove(summary.getAssessmentProgram()+summary.getSubject());
					}else{
						courseAttr =  new CourseAttr(0);
					}
					summary.setcourseAttr(courseAttr);
					rowId++;
				}
			}
			
			else {
				Map<String, Integer> apRows = new HashMap<String, Integer>();
				
				for (TestingSummary summary : dtoSummaries){
					//count the entries by assessment program
					if (!apRows.containsKey(summary.getAssessmentProgram())){
						apRows.put(summary.getAssessmentProgram(), 1);
					}else{
						apRows.put(summary.getAssessmentProgram(), apRows.get(summary.getAssessmentProgram())+1);
					}
				}
					Integer rowId=1;
				for (TestingSummary summary : dtoSummaries){
					Integer apCount = apRows.get(summary.getAssessmentProgram());
					Attr attr = null;
					if (apCount != null){
						attr =  new Attr(apCount);
						apRows.remove(summary.getAssessmentProgram());
					}else{
						attr =  new Attr(0);
					}
					summary.setAttr(attr);
					summary.setRowId("TS_"+rowId);
					rowId++;
				}
		
			}
			
			Org o = new Org();
			if (schoolOrgId != null){
				if (otd == null){
					otd = orgService.getOrganizationDetailBySchoolId(schoolOrgId);
				}
				o.setSchoolName(otd.getSchoolName());
				o.setDistrictName(otd.getDistrictName());
				o.setStateName(otd.getStateName());
			}else{
				if (districtOrgId != null){
					if (org == null){
						org = orgService.get(districtOrgId);
					}
					o.districtName=org.getOrganizationName();
				}
				org = orgService.get(stateOrgId);
				o.stateName=org.getOrganizationName();
			}
			Wrapper wrapper = new Wrapper();
			wrapper.setGridData(dtoSummaries);
			wrapper.setOrg(o);
			
			return objectToJson(wrapper);
							
				}
		return objectToJson(new Wrapper());
	}
	
	@RequestMapping(value = "getScoringSummary.htm", method = RequestMethod.GET)
	public @ResponseBody String getScoringSummary(
			@RequestParam(value = "orgId", required = false) Long requestOrgId) throws JsonProcessingException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_TESTING_SCORING_DASHBOARD"))) {
			boolean hasMiddayRunCompleted = dashboardService.hasMiddayRunCompleted();
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = requestOrgId != null ? requestOrgId : user.getCurrentOrganizationId();
			
			// check if the user has access to the org they're requesting
			if (orgId != user.getCurrentOrganizationId()) {
				Boolean childValid = orgService.isChildOf(user.getCurrentOrganizationId(), orgId);
				if (!Boolean.TRUE.equals(childValid)) {
					return objectToJson(new Wrapper());
				}
			}
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			List<DashboardScoringSummary> data = dashboardService.getScoringSummary(orgId, apIds);
			
			Map<Long, Integer> apCounts = new HashMap<Long, Integer>();
			for (DashboardScoringSummary dash : data) {
				Long apId = dash.getAssessmentProgramId();
				if (!apCounts.containsKey(apId)) {
					apCounts.put(apId, 1);
				} else {
					apCounts.put(apId, apCounts.get(apId) + 1);
				}
				
				dash.setCountSessionsScoredTodayStr(!hasMiddayRunCompleted ? "n/a" : String.valueOf(dash.getCountSessionsScoredToday()));
				DecimalFormat decimalFormatter = new DecimalFormat("##.#");
				String percentCompleted = String.valueOf(Double.valueOf(decimalFormatter.format(dash.getPercentCompletedThisYear()))) + "%";
				dash.setPercentCompletedThisYearStr(dash.getAssessmentProgramId().equals(3L) ? "n/a" : percentCompleted);
			}
			Integer rowId=1;
			for (DashboardScoringSummary dash : data) {
				Long apId = dash.getAssessmentProgramId();
				Integer count = apCounts.get(apId);
				dash.setAttr(new Attr(count == null ? 0 : count));
				apCounts.remove(apId);
				dash.setRowId("SS_"+rowId);
				rowId++;
			}
			
			OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
			Org org = new Org();
			org.setStateName(otd.getStateName());
			org.setDistrictName(otd.getDistrictName());
			org.setSchoolName(otd.getSchoolName());
			Wrapper wrapper = new Wrapper();
			wrapper.setGridData(data);
			wrapper.setOrg(org);
			return objectToJson(wrapper);
		}
		
		return objectToJson(new Wrapper());
	}
	
	@RequestMapping(value = "getReactivationsSummary.htm", method = RequestMethod.GET)
	public final @ResponseBody String getReactivationsSummary(			
			@RequestParam(value = "timeframe", required = false) String timeframe) throws JsonProcessingException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_REACTIVATIONS_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			
			//Getting the organization specific timezone to pass to the method

			TimeZone tz = orgService.getTimeZoneForOrganization(orgId);

			if (tz == null) {

				// default to central

				tz = TimeZone.getTimeZone("US/Central");
			}
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			timeframe = timeframe != null ? timeframe : "today";
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId()); 
			List<DashboardReactivations> reactivationsSummary = dashboardService.getReactivationsSummary(orgId, apIds, timeframe, tz.getID(), schoolStartDate);
			List<ReactivationsSummary> data = ReactivationsSummary.convertList(reactivationsSummary);
			
			OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
			Org org = new Org();
			org.setStateName(otd.getStateName());
			org.setDistrictName(otd.getDistrictName());
			org.setSchoolName(otd.getSchoolName());
			Wrapper wrapper = new Wrapper();
			wrapper.setGridData(data);
			wrapper.setOrg(org);
			return objectToJson(wrapper);
		}
		
		return objectToJson(new Wrapper());
	}
	
	@RequestMapping(value = "getReactivationsDetail.htm", method = RequestMethod.GET)
	public final @ResponseBody String getReactivationsDetail(			
			@RequestParam(value = "timeframe", required = false) String timeframe,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters) throws IOException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_REACTIVATIONS_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			
			//Getting the organization specific timezone to pass to the method
			TimeZone tz = orgService.getTimeZoneForOrganization(orgId);

			if (tz == null) {

				// default to central

				tz = TimeZone.getTimeZone("US/Central");
			}
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			timeframe = timeframe != null ? timeframe : "today";
			
			Integer currentPage = null;
			Integer limitCount = null;
			currentPage = NumericUtil.parse(page, 1);
			limitCount = NumericUtil.parse(limitCountStr, 5);
			Map<String, Object> criteria = new HashMap<String, Object>();
			populateCriteria(criteria, filters);		
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId());  
			List<DashboardReactivations> reactivationsDetail = dashboardService.getReactivationsDetail(orgId, apIds, timeframe,
					criteria, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount, tz.getID(),schoolStartDate);
			List<ReactivationsDetail> data = ReactivationsDetail.convertList(reactivationsDetail, orgService);
			
			Long reactivationsDetailCount = dashboardService.getReactivationsDetailCount(orgId, apIds, timeframe,
					criteria, sortByColumn, sortType, tz.getID(),schoolStartDate);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("page", page);
			result.put("total", (new Long(reactivationsDetailCount / limitCount + 1)).toString());
			result.put("records", reactivationsDetailCount.toString());
			result.put("rows", data);
			
			return objectToJson(result);
		}
		
		return objectToJson(new Wrapper());
	}
	
	@RequestMapping(value = "getTestingOutsideHours.htm", method = RequestMethod.GET)
	public final @ResponseBody String getTestingOutsideHours(			
			@RequestParam(value = "timeframe", required = false) String timeframe) throws JsonProcessingException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_TESTING_OUTHOURS_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			//Getting the organization specific timezone to pass in the method
			TimeZone tz = orgService.getTimeZoneForOrganization(orgId);
			if (tz == null) {
				// default to central
				tz = TimeZone.getTimeZone("US/Central");
			}
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			timeframe = timeframe != null ? timeframe : "today";
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId());  
	    	List<DashboardTestingOutsideHours> testingOutsideHours = dashboardService.getTestingOutsideHours(orgId, apIds, timeframe, tz.getID(),schoolStartDate);
			List<TestingOutsideHours> data = TestingOutsideHours.convertList(testingOutsideHours, orgService);
			
			
			OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
			//added Saikat
			Org org = new Org();
			org.setStateName(otd.getStateName());
			org.setDistrictName(otd.getDistrictName());
			org.setSchoolName(otd.getSchoolName());
						
			Long stateOrgId = otd.getStateId();
			String testDays = (orgService.getTestDays(orgId) != null) ? orgService.getTestDays(orgId) : orgService.getTestDays(stateOrgId);
			Date testBeginTimeTmp = (orgService.getTestBeginTime(orgId) != null) ? orgService.getTestBeginTime(orgId) : orgService.getTestBeginTime(stateOrgId);
			Date testEndTimeTmp = (orgService.getTestEndTime(orgId) != null) ? orgService.getTestEndTime(orgId) : orgService.getTestEndTime(stateOrgId);
			
			org.setTestDays(testDays);
			SimpleDateFormat formater = new SimpleDateFormat("hh:mm a");
			try {
				if (testBeginTimeTmp != null) {
					String testBeginTime = formater.format(testBeginTimeTmp);
					org.setTestBeginTime(testBeginTime);
				}
				if (testEndTimeTmp != null) {
					String testEndTime = formater.format(testEndTimeTmp);
					org.setTestEndTime(testEndTime);
				}
			} catch (Exception ex) {
				LOGGER.error("Caught in fetching Testing begin and end time. Stacktrace: {}", ex);
			}
			
			Wrapper wrapper = new Wrapper();
			wrapper.setGridData(data);
			wrapper.setOrg(org);
			return objectToJson(wrapper);
		}
		
		return objectToJson(new Wrapper());
	}
	
	//Added for short test duration
	
	@RequestMapping(value = "getShortDurationTesting.htm", method = RequestMethod.GET)
	public final @ResponseBody String getShortDurationTesting(			
			@RequestParam(value = "timeframe", required = false) String timeframe) throws JsonProcessingException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_SHORT_DURATION_TEST"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			//Getting the organization specific timezone to pass in the method
			TimeZone tz = orgService.getTimeZoneForOrganization(orgId);
			if (tz == null) {
				// default to central
				tz = TimeZone.getTimeZone("US/Central");
			}
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			timeframe = timeframe != null ? timeframe : "today";
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId());  
	    	List<DashboardShortDurationTesting> shortDurationTesting = dashboardService.getShortDurationTesting(orgId, apIds, timeframe, tz.getID(),schoolStartDate);
			List<ShortDurationTesting> data = ShortDurationTesting.convertList(shortDurationTesting, orgService);
			
			OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
			Org org = new Org();
			
			org.setStateName(otd.getStateName());
			org.setDistrictName(otd.getDistrictName());
			org.setSchoolName(otd.getSchoolName());
			Wrapper wrapper = new Wrapper();
			wrapper.setGridData(data);
			wrapper.setOrg(org);
			return objectToJson(wrapper);
		}
		
		return objectToJson(new Wrapper());
	}
	//end
	
	//Added an extra parameter to check whether multiple states details to be extracted at once
	@RequestMapping(value = "getTestingSummaryCSV.htm", method = RequestMethod.GET)
	public void getTestingSummaryCSV(final HttpServletRequest request, final HttpServletResponse response,
			@RequestParam(value = "stateOrgId", required = false) Long stateOrgId, 
			@RequestParam(value = "districtOrgId", required = false) Long districtOrgId, 
			@RequestParam(value = "schoolOrgId", required = false) Long schoolOrgId,
			@RequestParam(value = "getAllStates", required = true) Boolean getAllStates) throws IOException {	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_TESTING_SUMMARY_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long userId = user.getId();
			OrganizationTreeDetail otd = null;
			if (stateOrgId == null && districtOrgId == null && schoolOrgId == null) {
				Long userOrgId = user.getCurrentOrganizationId();
				String userOrgType = user.getCurrentOrganizationType();
				stateOrgId = user.getContractingOrgId();
				if (userOrgType.equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
					districtOrgId = userOrgId;
				} else if (userOrgType.equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)) {
					schoolOrgId = userOrgId;
					otd = orgService.getOrganizationDetailBySchoolId(schoolOrgId);
					districtOrgId = otd.getDistrictId();
				}
					
			}
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			List<Long> stateIdList = new ArrayList<Long>();
			if(getAllStates == true) {
				List<Organization> states = null;
				states = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, userId);

				for(Organization st: states) {
					stateIdList.add(st.getId());
				}
			}
			else {
				stateIdList.add(stateOrgId); 
			}

			
			Long checkOrgId = schoolOrgId != null ? schoolOrgId : districtOrgId != null ? districtOrgId : stateOrgId; 
			if (checkOrgId != user.getCurrentOrganizationId() && !stateIdList.contains(checkOrgId)) {
				Boolean childValid = orgService.isChildOf(user.getCurrentOrganizationId(), checkOrgId);
				if (!Boolean.TRUE.equals(childValid)) {
					return;
				}
			}			
			
			boolean hasReactivationPermission = auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_REACTIVATIONS_DASHBOARD"));
			boolean hasMiddayRunCompleted = dashboardService.hasMiddayRunCompleted();
			
			//Sending the array of State Ids instead of sending single state id.
			File csv = dashboardService.getTestingSummaryCSV(stateIdList, districtOrgId, schoolOrgId, apIds, user, hasReactivationPermission, hasMiddayRunCompleted);
			if (csv != null && csv.exists()) {
				// strip off pieces of the file name that were to avoid any file conflicts
				String attachmentName = csv.getName().replaceAll("_\\d+", "");
				
				// put the date in the file name
				SimpleDateFormat sdf = new SimpleDateFormat(CSV_DATE_STRING);
				Date mostRecentRunTime = dashboardService.getMostRecentRunTime();
				TimeZone tz = orgService.getTimeZoneForOrganization(checkOrgId);
				if (tz == null) {
					// default to central
					tz = TimeZone.getTimeZone("US/Central");
				}
				sdf.setTimeZone(tz);
				attachmentName = attachmentName.replace(DashboardServiceImpl.ASOFSTRING_PLACEHOLDER, mostRecentRunTime == null ? "" : ("_" + sdf.format(mostRecentRunTime)));
				
				response.setContentType("text/csv");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + attachmentName + "\"");
				IOUtils.copy(new FileInputStream(csv), response.getOutputStream());
				response.flushBuffer();
				
				// don't leave any evidence
				FileUtils.deleteQuietly(csv);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				LOGGER.error("getTestingSummaryCSV() - file does not exist (user " + user.getId() + " requested for stateOrgId " + stateOrgId 
						+ ", districtOrgId "+districtOrgId+", schoolOrgId "+schoolOrgId+" )");
			}
		}
	}	
	
	@RequestMapping(value = "getScoringSummaryCSV.htm", method = RequestMethod.GET)
	public void getScoringSummaryCSV(final HttpServletRequest request, final HttpServletResponse response,
			@RequestParam(value = "orgId", required = false) Long requestOrgId) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_TESTING_SCORING_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = requestOrgId != null ? requestOrgId : user.getCurrentOrganizationId();
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			
			File csv = dashboardService.getScoringSummaryCSV(orgId, apIds, user);
			if (csv != null && csv.exists()) {
				// strip off timestamp that was added to avoid any file conflicts
				String attachmentName = csv.getName().replaceAll("_\\d+", "");
				
				// put the date in the file name
				SimpleDateFormat sdf = new SimpleDateFormat(CSV_DATE_STRING);
				Date mostRecentRunTime = dashboardService.getMostRecentRunTime();
				TimeZone tz = orgService.getTimeZoneForOrganization(orgId);
				if (tz == null) {
					// default to central
					tz = TimeZone.getTimeZone("US/Central");
				}
				sdf.setTimeZone(tz);
				attachmentName = attachmentName.replace(DashboardServiceImpl.ASOFSTRING_PLACEHOLDER, mostRecentRunTime == null ? "" : ("_" + sdf.format(mostRecentRunTime)));
				
				response.setContentType("text/csv");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + attachmentName + "\"");
				IOUtils.copy(new FileInputStream(csv), response.getOutputStream());
				response.flushBuffer();
				
				// don't leave any evidence
				FileUtils.deleteQuietly(csv);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				LOGGER.error("getScoringSummaryCSV() - file does not exist (user " + user.getId() + " requested for org " + orgId + ")");
			}
		}
	}
	
	@RequestMapping(value = "getReactivationsSummaryCSV.htm", method = RequestMethod.GET)
	public void getReactivationsSummaryCSV(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_REACTIVATIONS_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId());
			File csv = dashboardService.getReactivationsSummaryCSV(orgId, apIds,schoolStartDate);
			if (csv != null && csv.exists()) {
				// strip off timestamp that was added to avoid any file conflicts
				String attachmentName = csv.getName().replaceAll("_\\d+", "");
				
				// put the date in the file name
				SimpleDateFormat sdf = new SimpleDateFormat(CSV_DATE_STRING);
				Date mostRecentRunTime = dashboardService.getMostRecentRunTime();
				TimeZone tz = orgService.getTimeZoneForOrganization(user.getCurrentOrganizationId());
				if (tz == null) {
					// default to central
					tz = TimeZone.getTimeZone("US/Central");
				}
				sdf.setTimeZone(tz);
				attachmentName = attachmentName.replace(DashboardServiceImpl.ASOFSTRING_PLACEHOLDER, mostRecentRunTime == null ? "" : ("_" + sdf.format(mostRecentRunTime)));
				
				response.setContentType("text/csv");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + attachmentName + "\"");
				IOUtils.copy(new FileInputStream(csv), response.getOutputStream());
				response.flushBuffer();
				
				// don't leave any evidence
				FileUtils.deleteQuietly(csv);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				LOGGER.error("getReactivationsSummaryCSV() - file does not exist (user " + user.getId() + " requested for org " + orgId + ")");
			}
		}
	}
	
	@RequestMapping(value = "getReactivationsDetailCSV.htm", method = RequestMethod.GET)
	public void getReactivationsDetailCSV(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_REACTIVATIONS_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId());
			File csv = dashboardService.getReactivationsDetailCSV(orgId, apIds,schoolStartDate);
			if (csv != null && csv.exists()) {
				// strip off timestamp that was added to avoid any file conflicts
				String attachmentName = csv.getName().replaceAll("_\\d+", "");
				
				// put the date in the file name
				SimpleDateFormat sdf = new SimpleDateFormat(CSV_DATE_STRING);
				Date mostRecentRunTime = dashboardService.getMostRecentRunTime();
				TimeZone tz = orgService.getTimeZoneForOrganization(user.getCurrentOrganizationId());
				if (tz == null) {
					// default to central
					tz = TimeZone.getTimeZone("US/Central");
				}
				sdf.setTimeZone(tz);
				attachmentName = attachmentName.replace(DashboardServiceImpl.ASOFSTRING_PLACEHOLDER, mostRecentRunTime == null ? "" : ("_" + sdf.format(mostRecentRunTime)));
				
				response.setContentType("text/csv");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + attachmentName + "\"");
				IOUtils.copy(new FileInputStream(csv), response.getOutputStream());
				response.flushBuffer();
				
				// don't leave any evidence
				FileUtils.deleteQuietly(csv);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				LOGGER.error("getReactivationsDetailCSV() - file does not exist (user " + user.getId() + " requested for org " + orgId + ")");
			}
		}
	}
	
	@RequestMapping(value = "getTestingOutsideHoursCSV.htm", method = RequestMethod.GET)
	public void getTestingOutsideHoursCSV(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_TESTING_OUTHOURS_DASHBOARD"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId());
			File csv = dashboardService.getTestingOutsideHoursCSV(orgId, apIds,schoolStartDate);
			if (csv != null && csv.exists()) {
				// strip off timestamp that was added to avoid any file conflicts
				String attachmentName = csv.getName().replaceAll("_\\d+", "");
				
				// put the date in the file name
				SimpleDateFormat sdf = new SimpleDateFormat(CSV_DATE_STRING);
				Date mostRecentRunTime = dashboardService.getMostRecentRunTime();
				TimeZone tz = orgService.getTimeZoneForOrganization(user.getCurrentOrganizationId());
				if (tz == null) {
					// default to central
					tz = TimeZone.getTimeZone("US/Central");
				}
				sdf.setTimeZone(tz);
				attachmentName = attachmentName.replace(DashboardServiceImpl.ASOFSTRING_PLACEHOLDER, mostRecentRunTime == null ? "" : ("_" + sdf.format(mostRecentRunTime)));
				
				response.setContentType("text/csv");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + attachmentName + "\"");
				IOUtils.copy(new FileInputStream(csv), response.getOutputStream());
				response.flushBuffer();
				
				// don't leave any evidence
				FileUtils.deleteQuietly(csv);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				LOGGER.error("getTestingOutsideHoursCSV() - file does not exist (user " + user.getId() + " requested for org " + orgId + ")");
			}
		}
	}
	
	//Saikat Added
	@RequestMapping(value = "getShortDurationTestingCSV.htm", method = RequestMethod.GET)
	public void getShortDurationTestingCSV(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && auth.getAuthorities().contains(new SimpleGrantedAuthority("VIEW_SHORT_DURATION_TEST"))) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long orgId = user.getCurrentOrganizationId();
			
			List<Long> apIds = new ArrayList<Long>();
			for (AssessmentProgram ap : user.getUserAssessmentPrograms()){
				apIds.add(ap.getId());
			}
			Date schoolStartDate = orgService.getSchoolStartDate(user.getContractingOrganization().getId());
			File csv = dashboardService.getShortDurationTestingCSV(orgId, apIds,schoolStartDate);
			if (csv != null && csv.exists()) {
				// strip off timestamp that was added to avoid any file conflicts
				String attachmentName = csv.getName().replaceAll("_\\d+", "");
				
				// put the date in the file name
				SimpleDateFormat sdf = new SimpleDateFormat(CSV_DATE_STRING);
				Date mostRecentRunTime = dashboardService.getMostRecentRunTime();
				TimeZone tz = orgService.getTimeZoneForOrganization(user.getCurrentOrganizationId());
				if (tz == null) {
					// default to central
					tz = TimeZone.getTimeZone("US/Central");
				}
				sdf.setTimeZone(tz);
				attachmentName = attachmentName.replace(DashboardServiceImpl.ASOFSTRING_PLACEHOLDER, mostRecentRunTime == null ? "" : ("_" + sdf.format(mostRecentRunTime)));
				
				response.setContentType("text/csv");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + attachmentName + "\"");
				IOUtils.copy(new FileInputStream(csv), response.getOutputStream());
				response.flushBuffer();
				
				// don't leave any evidence
				FileUtils.deleteQuietly(csv);
			} else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				LOGGER.error("getShortDurationTestingCSV() - file does not exist (user " + user.getId() + " requested for org " + orgId + ")");
			}
		}
	}
	
	/**
	 * 
	 * Changes for F851 API Errors Dashboard
	 */
	@RequestMapping(value = "getApiErrorMessages.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getApiErrorMessages(
			@RequestParam(value ="districtId", required = false) Long distOrgId,
			@RequestParam(value ="schoolId", required = false) Long schOrgId,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value="viewOrphanedRecords", required = false) String viewOrphanedRecords) throws IOException {
		
		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		Integer totalCount = -1;
		
		Integer viewOrphanedRecordsFlag = 0;
		if(viewOrphanedRecords != null && viewOrphanedRecords.equalsIgnoreCase("checked")) {
			viewOrphanedRecordsFlag = 1;
		}
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();		
		
		//if user is at district level then use user currentorganizationid as districtid
		if(CommonConstants.ORGANIZATION_DISTRICT_CODE.equals(user.getCurrentOrganizationType())){
			if(distOrgId == null) {
				distOrgId = user.getCurrentOrganizationId();
			}
		}else if(CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(user.getCurrentOrganizationType())){
			//if user is at school level then use user currentorganizationid as schoolid
			if(schOrgId == null) {
				schOrgId = user.getCurrentOrganizationId();
			}
		}
		
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		Map<String, Object> criteria = new HashMap<String, Object>();
		
		criteria.put("districtId", distOrgId);
		criteria.put("schoolId", schOrgId);
		criteria.put("stateId", getContractingOrgid());
		criteria.put("viewOrphanedRecordsFlag", viewOrphanedRecordsFlag);
		populateCriteria(criteria, filters);
		
		List<ApiErrorsRecord> apiErrorMessages = dashboardService.
				getApiErrorsToView(criteria, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount); 
		
		totalCount = dashboardService.getCountOfApiErrorsToView(criteria);
		jqGridJSONModel = ApiErrorsJsonConverter.convertToOrganizationJson(
				apiErrorMessages, totalCount, currentPage, limitCount);
		
		return jqGridJSONModel;
	}
	
	@RequestMapping(value = "getAllApiErrorMessages.htm", method = RequestMethod.GET) 
	public @ResponseBody String getAllApiErrorMessages(
			@RequestParam(value ="districtId", required = false) Long distOrgId,
			@RequestParam(value ="schoolId", required = false) Long schOrgId,
			@RequestParam(value = "filters", required = false) String filters,
			@RequestParam(value="viewOrphanedRecords", required = false) String viewOrphanedRecords) throws IOException {
		String response = StringUtils.EMPTY;	
		
		Integer viewOrphanedRecordsFlag = 0;
		if(viewOrphanedRecords != null && viewOrphanedRecords.equalsIgnoreCase("checked")) {
			viewOrphanedRecordsFlag = 1;
		}
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();		
		
		//if user is at district level then use user currentorganizationid as districtid
		if(CommonConstants.ORGANIZATION_DISTRICT_CODE.equals(user.getCurrentOrganizationType())){
			if(distOrgId == null) {
				distOrgId = user.getCurrentOrganizationId();
			}
		}else if(CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(user.getCurrentOrganizationType())){
			//if user is at school level then use user currentorganizationid as schoolid
			if(schOrgId == null) {
				schOrgId = user.getCurrentOrganizationId();
			}
		}
		
		
		Map<String, Object> criteria = new HashMap<String, Object>();		
		//Long organizationId = getOrganization(orgId);
		criteria.put("districtId", distOrgId);
		criteria.put("schoolId", schOrgId);
		criteria.put("stateId", getContractingOrgid());
		criteria.put("viewOrphanedRecordsFlag", viewOrphanedRecordsFlag);
		populateCriteria(criteria, filters);
		
		ObjectMapper mapper = new ObjectMapper();
		List<ApiAllErrorRecords> apiErrorMessages = dashboardService.getAllApiErrorMessages(criteria);
		response = mapper.writeValueAsString(apiErrorMessages);
		return response;
	}
	
	@RequestMapping(value="getApiErrorsGridFilterData.htm", method=RequestMethod.GET) 
	public @ResponseBody Map<String, Object> getApiErrorsGridFilterData (){
		Map<String, Object> selectRecordTypeValues = new HashMap<String, Object>();
		List<String> recordTypes = dashboardService.getErrorRecordTypes();
		List<String> requestTypes = dashboardService.getApiErrorsRequestTypes();
		selectRecordTypeValues.put("recordTypes", recordTypes);
		selectRecordTypeValues.put("requestTypes", requestTypes);
		return selectRecordTypeValues;
	}
	/**
	 * 
	 * Changes for F845 Test Assignment Errors Dashboard
	 */
	@RequestMapping(value="getTestAssignmentErrorMessages.htm", method=RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getTestAssignmentErrorMessages(
			@RequestParam(value ="orgId", required = false) Long orgId,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters) throws IOException {
			
		
		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		Integer totalCount = -1;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();		
		
		if(orgId == null) {
			orgId = user.getCurrentOrganizationId();
		}
		
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		Map<String, Object> criteria = new HashMap<String, Object>();
		
		Long organizationId = getOrganization(orgId);
		criteria.put("organizationId", organizationId);
		criteria.put("stateId", getContractingOrgid());
		criteria.put("isTeacher", user.isTeacher());
		criteria.put("curUserID", user.getId());
		populateCriteria(criteria, filters);
		
		List<TestAssignmentErrorRecord> testAssignmentErrors = dashboardMessageService.
				getTestAssignmentErrors(criteria, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount); 
		
		totalCount = dashboardMessageService.getCountOfTestAssignmentErrors(criteria);
		jqGridJSONModel = TestAssignmentJsonConverter.convertToOrganizationJson(
				testAssignmentErrors, totalCount, currentPage, limitCount);
		
		return jqGridJSONModel;
	}
	
	@RequestMapping(value = "getAllTestAssignmentErrorMessages.htm", method = RequestMethod.GET) 
	public @ResponseBody String getAllTestAssignmentErrorMessages(
			@RequestParam(value ="orgId", required = false) Long orgId) throws JsonProcessingException {
		String response = StringUtils.EMPTY;		
		Map<String, Object> criteria = new HashMap<String, Object>();		
		Long organizationId = getOrganization(orgId);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		criteria.put("organizationId", organizationId);
		criteria.put("stateId", getContractingOrgid());
		criteria.put("isTeacher", user.isTeacher());
		criteria.put("curUserID", user.getId());
		ObjectMapper mapper = new ObjectMapper();
		List<TestAssignmentErrorRecordDTO> testAssignmentErrors = dashboardMessageService.getAllTestAssignmentErrorMessages(criteria);
		response = mapper.writeValueAsString(testAssignmentErrors);
		return response;
	}
	
	private String objectToJson(Object object) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
		return mapper.writeValueAsString(object);
	}
		
	private class Org {
		private String stateName;
		private String districtName;
		private String schoolName;
		private String testBeginTime;
		private String testEndTime;
		private String testDays;
		
		public String getStateName() {
			return stateName;
		}
		public void setStateName(String stateName) {
			this.stateName = stateName;
		}
		public String getDistrictName() {
			return districtName;
		}
		public void setDistrictName(String districtName) {
			this.districtName = districtName;
		}
		public String getSchoolName() {
			return schoolName;
		}
		public void setSchoolName(String schoolName) {
			this.schoolName = schoolName;
		}
		public String getTestBeginTime() {
			return testBeginTime;
		}
		public void setTestBeginTime(String testBeginTime) {
			this.testBeginTime = testBeginTime;
		}
		public String getTestEndTime() {
			return testEndTime;
		}
		public void setTestEndTime(String testEndTime) {
			this.testEndTime = testEndTime;
		}
		public String getTestDays() {
			return testDays;
		}
		public void setTestDays(String testDays) {
			this.testDays = testDays;
		}
	}
	private class Wrapper {
		private List<?> gridData;
		private Org org;
		private String asOfString;
		public List<?> getGridData() {
			return gridData;
		}
		public void setGridData(List<?> gridData) {
			this.gridData = gridData;
		}
		public Org getOrg() {
			return org;
		}
		public void setOrg(Org org) {
			this.org = org;
		}
		public String getAsOfString() {
			return this.asOfString != null ? this.asOfString : "--";
		}
		public void setAsOfString(String asOfString) {
			this.asOfString = asOfString;
		}
	}
}
