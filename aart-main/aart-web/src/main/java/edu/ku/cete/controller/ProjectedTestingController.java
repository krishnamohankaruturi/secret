package edu.ku.cete.controller;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.ProjectedTesting;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.TestingProjectionDTO;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.ProjectedTestingService;
import edu.ku.cete.service.exception.NoAccessToResourceException;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;

@Controller
public class ProjectedTestingController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectedTestingController.class);

	@Autowired
	private AwsS3Service s3;
	
	@Autowired
	private DownloadController downloadController;
	
	@Autowired
	private ProjectedTestingService projectedTestingService;

	@Autowired
	private GradeCourseService gradeCourseService;

	@Value("${print.test.file.path}")
	private String REPORT_PATH;

	@RequestMapping(value = "/viewProjectedTestingChart.htm", method = RequestMethod.GET)
	public final @ResponseBody String getUpdatesByMinuteGraph() {
		/* Get the output stream from the response object */
		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = loggedinUser.getUser().getContractingOrganization().getCurrentSchoolYear();
		String modelJson= null;
		try {
			Collection<TestingProjectionDTO> projectedTestingChartDTOs = projectedTestingService.getProjectTestingChart(currentSchoolYear);
		    ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			modelJson = mapper.writeValueAsString(projectedTestingChartDTOs);
		} catch (Exception e) {
			logger.error("Error while generating Projected Chart", e);
			modelJson = "{\"response\":\"failed\"}";
		} 
		return modelJson;
	}

	@RequestMapping(value = "getProjectedTestingViewMyCalendar.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getViewMyCalendarRecords(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("filters") String filters)
			throws NoAccessToResourceException {
		Map<String, Object> viewMyCalendarTestingMap = new HashMap<String, Object>();

		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);

		Map<String, String> viewMyCalendarCriteriaMap = constructTestSessionRecordFilterCriteria(filters);

		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = loggedinUser.getUser().getContractingOrganization().getCurrentSchoolYear();
		viewMyCalendarCriteriaMap.put("assessmentProgramId",
				loggedinUser.getUser().getCurrentAssessmentProgramId().toString());
		viewMyCalendarCriteriaMap.put("organizationId", loggedinUser.getUser().getOrganizationId().toString());
		viewMyCalendarCriteriaMap.put("currentSchoolYear", currentSchoolYear.toString());

		List<ProjectedTesting> viewMyCalendarTestingRecords = projectedTestingService.getViewMyCalendarRecords(
				sortByColumn, sortType, viewMyCalendarCriteriaMap, (currentPage - 1) * limitCount, limitCount);

		totalCount = projectedTestingService.countViewMyCalendarRecords(viewMyCalendarCriteriaMap);

		viewMyCalendarTestingMap.put("rows", viewMyCalendarTestingRecords);
		viewMyCalendarTestingMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		viewMyCalendarTestingMap.put("page", currentPage);
		viewMyCalendarTestingMap.put("records", totalCount);

		return viewMyCalendarTestingMap;
	}

	private Map<String, String> constructTestSessionRecordFilterCriteria(String filters) {
		Map<String, String> testSessionCriteriaMap = new HashMap<String, String>();
		if (null != filters && !filters.equals("")) {
			RecordBrowserFilter recordBrowserFilter = null;
			// Parse the column filter values which the user enters on the UI
			// record browser grid.
			try {
				recordBrowserFilter = new ObjectMapper().readValue(filters, new TypeReference<RecordBrowserFilter>() {
				});
			} catch (JsonParseException e) {
				logger.error("Couldn't parse json object.", e);
			} catch (JsonMappingException e) {
				logger.error("Unexpected json mapping", e);
			} catch (SecurityException e) {
				logger.error("Unexpected exception with reflection", e);
			} catch (IllegalArgumentException e) {
				logger.error("Unexpected exception with reflection", e);
			} catch (Exception e) {
				logger.error("Unexpected error", e);
			}
			if (recordBrowserFilter != null) {
				for (RecordBrowserFilterRules recordBrowserFilterRules : recordBrowserFilter.getRules()) {
					if (recordBrowserFilterRules.getField().equals("projectionType")
							&& StringUtils.isNotBlank(recordBrowserFilterRules.getData().trim())) {
						testSessionCriteriaMap.put(recordBrowserFilterRules.getField(),
								CommonConstants.PERCENTILE_DELIM + recordBrowserFilterRules.getData().trim().charAt(0)
										+ CommonConstants.PERCENTILE_DELIM);
					} else {
						testSessionCriteriaMap.put(recordBrowserFilterRules.getField(), CommonConstants.PERCENTILE_DELIM
								+ recordBrowserFilterRules.getData().trim() + CommonConstants.PERCENTILE_DELIM);
					}

				}
			}
		}
		return testSessionCriteriaMap;
	}

	@RequestMapping(value = "savingCalendarValues.htm", method = RequestMethod.POST)
	public final @ResponseBody String savingCalendarValues(
			@RequestParam(value = "assessmentProgramId", required = true) Long assessmentProgramId,
			@RequestParam(value = "stateId", required = true) Long stateId,
			@RequestParam(value = "districtId", required = true) Long districtId,
			@RequestParam(value = "schoolId", required = true) Long schoolId,
			@RequestParam(value = "month", required = true) String month,
			@RequestParam(value = "dates[]", required = true) String[] dates,
			@RequestParam(value = "grade", required = true) Long grade,
			@RequestParam(value = "projectionType", required = true) String projectionType) {
		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			ProjectedTesting projectedTesting = new ProjectedTesting();
			projectedTesting.setAssessmentProgramId(assessmentProgramId);
			projectedTesting.setStateId(stateId);
			projectedTesting.setDistrictId(districtId);
			projectedTesting.setSchoolId(schoolId);
			projectedTesting.setMonth(month);
			projectedTesting
					.setCurrentSchoolYear(loggedinUser.getUser().getContractingOrganization().getCurrentSchoolYear());
			projectedTesting.setGradeId(grade);

			if (projectionType.equalsIgnoreCase("Testing")) {
				projectedTesting.setProjectionType("T");
			} else if (projectionType.equalsIgnoreCase("Scoring")) {
				projectedTesting.setProjectionType("S");
			} else {
				projectedTesting.setProjectionType(projectionType);
			}

			SimpleDateFormat sdate = new SimpleDateFormat("dd-MM-yyyy");
			sdate.setLenient(false);
			Date utilDate = null;
			for (String date : dates) {
				utilDate = sdate.parse(date, new ParsePosition(0));
				if (utilDate != null)// checking for invalid dates
					projectedTesting.getActualDates().add(utilDate);
			}

			projectedTestingService.addorUpdateProjectedTesting(projectedTesting);
			return "{\"response\":\"success\"}";
		} catch (Exception e) {
			return "{\"response\":\"failed\"}";
		}
	}

	public String[] jsonArrayToStringArray(JSONArray jsonArray) throws JSONException {
		int arraySize = jsonArray.length();
		String[] stringArray = new String[arraySize];

		for (int i = 0; i < arraySize; i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			stringArray[i] = (String) jsonObject.toString();
		}

		return stringArray;
	}

	@RequestMapping(value = "getValidCalendarMonthNames.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, String> getValidMonthNames() throws NoAccessToResourceException {
		Map<String, String> validMonthsMap = new HashMap<String, String>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		validMonthsMap = projectedTestingService.getValidMonthsFortesting(userDetails);

		return validMonthsMap;
	}

	@RequestMapping(value = "getGradesForProjectedScoring.htm", method = RequestMethod.GET)
	private final @ResponseBody List<GradeCourse> getGradesForProjectedScoring() {

		return gradeCourseService.getAllIndependentGrades();

	}

	@RequestMapping(value = "getDistrictSummaryReportCsv.htm", method = RequestMethod.GET)
	public final void getDistrictSummaryReportCsv(final HttpServletResponse response, final HttpServletRequest request)
			throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

			String folderPath = REPORT_PATH + java.io.File.separator + "ProjectedDistrictSummaryReport";
			String path = FileUtil.buildFilePath(folderPath, "ProjectedDistrictSummaryReport.CSV");
			if (s3.doesObjectExist(path)){
				downloadController.download(request, response, path);
			} else {
				response.sendError(404, "File not found.");
			}
		}
	}

	@RequestMapping(value = "getBarChartAssessmentPrograms.htm", method = RequestMethod.GET)
	private final @ResponseBody Map<String, Object> getBarChartAssessmentPrograms() {
		return projectedTestingService.getBarChartAssessmentPrograms();
	}
}
