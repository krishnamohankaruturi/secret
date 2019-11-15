package edu.ku.cete.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.DistrictSummaryReport;
import edu.ku.cete.domain.ProjectedTesting;
import edu.ku.cete.domain.common.ProjectionColorComparator;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ProjectedTestingChartDTO;
import edu.ku.cete.model.ProjectedTestingDTO;
import edu.ku.cete.model.ProjectedTestingDao;
import edu.ku.cete.model.TestingProjectionDTO;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.ProjectedTestingService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.ProjectionColorCodes;

/**
 * Added By Sudhansu Feature: f183 Projected Testing
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ProjectedTestingServiceImpl implements ProjectedTestingService {

	private Logger LOGGER = LoggerFactory.getLogger(ProjectedTestingServiceImpl.class);
	private static final String[] columnNames = { "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight",
			"Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
			"Nineteen", "Twenty", "TwentyOne", "TwentyTwo", "TwentyThree", "TwentyFour", "TwentyFive", "TwentySix",
			"TwentySeven", "TwentyEight", "TwentyNine", "Thirty", "ThirtyOne" };

	@Autowired
	ProjectedTestingDao projectedTestingDao;

	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private AwsS3Service s3;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addorUpdateProjectedTesting(ProjectedTesting projectedTesting) {

		// Check whether update or insert
		List<ProjectedTestingDTO> projectedTestings = projectedTestingDao.getProjectedTesting(
				projectedTesting.getAssessmentProgramId(), projectedTesting.getSchoolId(), projectedTesting.getMonth(),
				projectedTesting.getCurrentSchoolYear(), projectedTesting.getGradeId(),
				projectedTesting.getProjectionType());

		// convert to actual dates
		// convertColumnToDates(projectedTesting);

		// Save to DB
		ProjectedTestingDTO projectedTestingDTO = new ProjectedTestingDTO();
		projectedTestingDTO.setAssessmentProgramId(projectedTesting.getAssessmentProgramId());
		projectedTestingDTO.setStateid(projectedTesting.getStateId());
		projectedTestingDTO.setDistrictId(projectedTesting.getDistrictId());
		projectedTestingDTO.setSchoolId(projectedTesting.getSchoolId());
		projectedTestingDTO.setMonth(projectedTesting.getMonth());
		projectedTestingDTO.setAuditColumnProperties();
		projectedTestingDTO.setCurrentSchoolYear(projectedTesting.getCurrentSchoolYear());
		projectedTestingDTO.setActiveFlag(true);
		// Added For F605
		projectedTestingDTO.setProjectionType(projectedTesting.getProjectionType());
		projectedTestingDTO.setGrade(projectedTesting.getGradeId());

		for (ProjectedTestingDTO projectedTestingDto : projectedTestings) {

			if (!projectedTesting.getActualDates().contains(projectedTestingDto.getTestDate())) {
				// setting flag to false if date is removed.
				if (projectedTestingDto.getActiveFlag()) {
					projectedTestingDto.setAuditColumnPropertiesForUpdate();
					projectedTestingDto.setActiveFlag(false);
					projectedTestingDao.updateByPrimaryKeySelective(projectedTestingDto);
				}
			}
		}

		Set<Date> dateList = new HashSet<Date>();

		for (Date date : projectedTesting.getActualDates()) {
			if (projectedTestings.size() != 0) {// update
				for (ProjectedTestingDTO projectedTestingDto : projectedTestings) {
					if (Days.daysBetween(new LocalDate(projectedTestingDto.getTestDate().getTime()),
							new LocalDate(date.getTime())).getDays() == 0) {
						if (!projectedTestingDto.getActiveFlag()) {
							projectedTestingDto.setAuditColumnPropertiesForUpdate();
							projectedTestingDto.setActiveFlag(true);
							projectedTestingDao.updateByPrimaryKeySelective(projectedTestingDto);
						}
						dateList.add(date);
					}
				}
			}
		}
		projectedTesting.getActualDates().removeAll(dateList);

		for (Date date : projectedTesting.getActualDates()) {
			projectedTestingDTO.setTestDate(date);
			projectedTestingDao.save(projectedTestingDTO);
		}

	}

	@Override
	public ProjectedTesting convertColumnToDates(ProjectedTesting projectedTesting) {
		String dateStr = null;
		Method method = null;
		Calendar cal = Calendar.getInstance();

		cal.setTime(projectedTesting.getSchoolStartDate());
		Integer schoolStartMonth = cal.get(Calendar.MONTH);

		Integer schoolStartYear = cal.get(Calendar.YEAR);

		cal.setTime(projectedTesting.getSchoolEndDate());
		Integer schoolEndYear = cal.get(Calendar.YEAR);

		cal.setTime(new Date());

		Date currentDate = null;
		try {
			currentDate = new SimpleDateFormat("MMM").parse(projectedTesting.getMonth());
		} catch (ParseException e) {
			LOGGER.error("Exception while parsing date " + projectedTesting.getMonth(), e);
		}

		cal.setTime(currentDate);

		Integer uploadedMonth = cal.get(Calendar.MONTH);

		currentDate = new Date();

		if (schoolEndYear.intValue() != schoolStartYear.intValue() && uploadedMonth >= schoolStartMonth) {
			projectedTesting.setSchoolYear(projectedTesting.getSchoolYear() - 1);
		}

		SimpleDateFormat sdate = null;
		Date utilDate = null;
		for (int i = 1; i <= 31; i++) {
			method = ReflectionUtils.findMethod(ProjectedTesting.class, "get" + columnNames[i]);
			Object object = ReflectionUtils.invokeMethod(method, projectedTesting);

			if (object != null && !((String) object).trim().isEmpty()) {
				dateStr = (projectedTesting.getSchoolYear()) + "-" + projectedTesting.getMonth() + "-" + i;
				sdate = new SimpleDateFormat("yyyy-MMM-dd");
				sdate.setLenient(false);
				utilDate = sdate.parse(dateStr, new ParsePosition(0));

				if (utilDate != null) {
					projectedTesting.getActualDates().add(utilDate);
				} else {
					projectedTesting.getInvalidDate().add(i);
				}

			}
		}
		projectedTesting.setMonth(cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
		return projectedTesting;
	}

	private List<ProjectedTesting> setColumnToAtrributes(List<ProjectedTestingDTO> projectedTestingDTOs) {

		List<ProjectedTesting> projectedTestings = new ArrayList<ProjectedTesting>();
		Method method = null;
		Calendar calender = Calendar.getInstance();

		Map<String, List<ProjectedTestingDTO>> relatedProjectedTestingMap = new LinkedHashMap<String, List<ProjectedTestingDTO>>();
		String key = null;
		for (ProjectedTestingDTO projectedTestingDTO : projectedTestingDTOs) {
			key = projectedTestingDTO.getDistrictId() + "-" + projectedTestingDTO.getDistrictName() + "-"
					+ projectedTestingDTO.getSchoolId() + "-" + projectedTestingDTO.getSchoolName() + "-"
					+ projectedTestingDTO.getAssessmentProgramId() + "-" + projectedTestingDTO.getMonth() + "-"
					+ projectedTestingDTO.getGrade() + "-" + projectedTestingDTO.getProjectionType();

			if (!relatedProjectedTestingMap.containsKey(key)) {
				relatedProjectedTestingMap.put(key, new ArrayList<ProjectedTestingDTO>());

			}
			relatedProjectedTestingMap.get(key).add(projectedTestingDTO);
		}

		for (Map.Entry<String, List<ProjectedTestingDTO>> entry : relatedProjectedTestingMap.entrySet()) {

			ProjectedTesting projectedTesting = new ProjectedTesting();
			projectedTesting.setAssessmentProgramId(entry.getValue().get(0).getAssessmentProgramId());
			projectedTesting.setSchoolName(entry.getValue().get(0).getSchoolName());
			projectedTesting.setDistrictName(entry.getValue().get(0).getDistrictName());
			projectedTesting.setMonth(entry.getValue().get(0).getMonth());
			projectedTesting.setSchoolId(entry.getValue().get(0).getSchoolId());
			projectedTesting.setDistrictId(entry.getValue().get(0).getDistrictId());
			projectedTesting.setStateId(entry.getValue().get(0).getStateid());
			projectedTesting.setAssessmentProgramId(entry.getValue().get(0).getAssessmentProgramId());
			projectedTesting.setGradeId(entry.getValue().get(0).getGrade());
			projectedTesting.setGradeName(entry.getValue().get(0).getGradeName());
			projectedTesting.setProjectionType(entry.getValue().get(0).getProjectionType());
			projectedTesting.setModifiedBy(entry.getValue().get(0).getModifiedBy());
			projectedTesting.setModifiedDate(entry.getValue().get(0).getModifiedDate());
			for (ProjectedTestingDTO projectedTestingDTO : entry.getValue()) {
				calender.setTime(projectedTestingDTO.getTestDate());
				method = ReflectionUtils.findMethod(ProjectedTesting.class,
						"set" + columnNames[calender.get(Calendar.DATE)], String.class);
				ReflectionUtils.invokeMethod(method, projectedTesting, "x");
			}
			projectedTesting.setSchoolYear(Long.valueOf(calender.get(Calendar.YEAR)));
			projectedTestings.add(projectedTesting);
		}

		return projectedTestings;
	}

	@Override
	public List<ProjectedTesting> getViewMyCalendarRecords(String sortByColumn, String sortType,
			Map<String, String> viewMyCalendarCriteriaMap, int offset, int limitCount) {

		List<ProjectedTestingDTO> projectedTestingDTOs = projectedTestingDao
				.getProjectedTestingForLoggedInUser(sortByColumn, sortType, viewMyCalendarCriteriaMap);

		List<ProjectedTesting> projectedTestingFull = setColumnToAtrributes(projectedTestingDTOs);
		List<ProjectedTesting> projectedTestingOffsetSelected = new ArrayList<ProjectedTesting>();

		Integer offestLimit = offset + limitCount > projectedTestingFull.size() ? projectedTestingFull.size()
				: offset + limitCount;

		for (int i = offset; i < offestLimit; i++) {
			projectedTestingOffsetSelected.add(projectedTestingFull.get(i));
		}

		return projectedTestingOffsetSelected;
	}

	@Override
	public boolean isHavingWeekends(ProjectedTesting projectedTesting) {
		return false;
	}

	@Override
	public Collection<TestingProjectionDTO> getProjectTestingChart(Long currentSchoolYear) {
		
		Map<String, TestingProjectionDTO> barChartData = new LinkedHashMap<String, TestingProjectionDTO>();
		
		Calendar cal = Calendar.getInstance();
		List<ProjectedTestingChartDTO> projectedTestingChartDTOs = projectedTestingDao
				.getProjectedTestingForChart(currentSchoolYear);
		String colName = null;
		for (ProjectedTestingChartDTO projectedTestingChartDTO : projectedTestingChartDTOs) {
			cal.setTime(projectedTestingChartDTO.getTestDate());
			colName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH).substring(0, 3) + " "
					+ (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DATE);
			projectedTestingChartDTO.setColumnName(colName);
			TestingProjectionDTO dto = barChartData.get(colName);
			if(dto == null){
				dto = new TestingProjectionDTO();
				barChartData.put(colName, dto);
			} else {
				dto = barChartData.get(colName);
			}
			dto.setTestDate(colName);
			if(projectedTestingChartDTO.getAssessmentProgram().equals(CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE)){
				Double kapCounts = calculateEstimatedTestSessions(projectedTestingChartDTO.getTestDate(),
						projectedTestingChartDTO.getDistrictId(), projectedTestingChartDTO.getProjectionType(),
						projectedTestingChartDTO.getAssessmentProgramId(), currentSchoolYear);
				dto.setKap((dto.getKap() + kapCounts));
			} else if(projectedTestingChartDTO.getAssessmentProgram().equals("CPASS")){
				Double cpassCounts = calculateEstimatedTestSessions(projectedTestingChartDTO.getTestDate(),
						projectedTestingChartDTO.getDistrictId(), projectedTestingChartDTO.getProjectionType(),
						projectedTestingChartDTO.getAssessmentProgramId(), currentSchoolYear);
				dto.setCpass((dto.getCpass() + cpassCounts));
			} else if(projectedTestingChartDTO.getAssessmentProgram().equals(CommonConstants.ASSESSMENT_PROGRAM_DLM)){
				Double dlmCounts = calculateEstimatedTestSessions(projectedTestingChartDTO.getTestDate(),
						projectedTestingChartDTO.getDistrictId(), projectedTestingChartDTO.getProjectionType(),
						projectedTestingChartDTO.getAssessmentProgramId(), currentSchoolYear);
				dto.setDlm((dto.getDlm() + dlmCounts));
			} else if(projectedTestingChartDTO.getAssessmentProgram().equals(CommonConstants.ASSESSMENT_PROGRAM_KELPA2)){
				Double kelpa2Counts = calculateEstimatedTestSessions(projectedTestingChartDTO.getTestDate(),
						projectedTestingChartDTO.getDistrictId(), projectedTestingChartDTO.getProjectionType(),
						projectedTestingChartDTO.getAssessmentProgramId(), currentSchoolYear);
				dto.setKelpa2((dto.getKelpa2() + kelpa2Counts));
			}else if(projectedTestingChartDTO.getAssessmentProgram().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
				Double pltwCounts = calculateEstimatedTestSessions(projectedTestingChartDTO.getTestDate(),
						projectedTestingChartDTO.getDistrictId(), projectedTestingChartDTO.getProjectionType(),
						projectedTestingChartDTO.getAssessmentProgramId(), currentSchoolYear);
				dto.setPltw((dto.getPltw() + pltwCounts));
			} else if(projectedTestingChartDTO.getAssessmentProgram().equals(CommonConstants.ASSESSMENT_PROGRAM_SCORING)){
				Double scoringCounts = calculateEstimatedTestSessions(projectedTestingChartDTO.getTestDate(),
						projectedTestingChartDTO.getDistrictId(), projectedTestingChartDTO.getProjectionType(),
						projectedTestingChartDTO.getAssessmentProgramId(), currentSchoolYear);
				dto.setScoring((dto.getScoring() + scoringCounts));
			}
		}
		return barChartData.values();
	}

	@Override
	public int countViewMyCalendarRecords(Map<String, String> viewMyCalendarCriteriaMap) {

		return projectedTestingDao.countViewMyCalendarRecords(viewMyCalendarCriteriaMap);
	}

	@Override
	public Map<String, String> getValidMonthsFortesting(UserDetailImpl userDetails) {

		Map<String, String> validTestingMonthsMap = new LinkedHashMap<String, String>();
		validTestingMonthsMap.put("", "Month");

		Date stateOrgEndDate = userDetails.getUser().getContractingOrganization().getSchoolEndDate();
		Date currentDate = new Date();

		DateFormat formater = new SimpleDateFormat("MMMM-yyyy");

		Calendar beginCalendar = Calendar.getInstance();
		Calendar finishCalendar = Calendar.getInstance();

		beginCalendar.setTime(currentDate);
		finishCalendar.setTime(stateOrgEndDate);

		while (beginCalendar.before(finishCalendar) || (beginCalendar.equals(finishCalendar))) {
			// add one month to date per loop
			String desiredMonths = formater.format(beginCalendar.getTime());
			beginCalendar.add(Calendar.MONTH, 1);
			validTestingMonthsMap.put(desiredMonths, desiredMonths);
		}

		return validTestingMonthsMap;
	}

	@Override
	public void generateDistrictSummaryReportCSV() throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		List<String[]> districtSummaryReportArray = new ArrayList<>();
		String date = StringUtils.EMPTY;

		Calendar cal = Calendar.getInstance();
		Long currentSchoolYear = Long.valueOf(cal.get(Calendar.YEAR) + 1L);
		String[] columnHeaders = { "Date", "Assessment Program", "State", "District",
				"Estimated Test Sessions - Testing", "Estimated Test Sessions - Scoring", "DTC Email Address" };
		districtSummaryReportArray.add(columnHeaders);
		List<DistrictSummaryReport> districtSummaryReportDetails = projectedTestingDao
				.getDistrictSummaryReportDetails(currentSchoolYear);

		for (DistrictSummaryReport districtSummaryReport : districtSummaryReportDetails) {
			List<String> districtSummaryReportList = new ArrayList<>();
			if (districtSummaryReport.getDate() != null) {
				date = " " + dateFormat.format(districtSummaryReport.getDate());
			}
			districtSummaryReportList.add(date);
			districtSummaryReportList.add(districtSummaryReport.getAssessmentProgram());
			districtSummaryReportList.add(districtSummaryReport.getState());
			districtSummaryReportList.add(districtSummaryReport.getDistrict());

			// Calculate the estimated test session count and populate.
			if (districtSummaryReport.getProjectionType().equals("T")) {
				districtSummaryReportList.add(districtSummaryReport.getEstimatedTestSessionsTesting().toString());
				districtSummaryReportList.add("0");

			} else {
				districtSummaryReportList.add("0");
				districtSummaryReportList.add(districtSummaryReport.getEstimatedTestSessionsScoring().toString());
			}
			// Get the dtc email addresses and populate
			
			String dtcMails = projectedTestingDao.getDtcEmailAddress(districtSummaryReport.getDistrictId(), districtSummaryReport.getAssessmentProgram());
			districtSummaryReportList.add(dtcMails);

			districtSummaryReportArray
					.add(districtSummaryReportList.toArray(new String[districtSummaryReportList.size()]));
		}

		CSVWriter csvWriter = null;
		try {
			String sep = java.io.File.separator;
			String folderPath = REPORT_PATH + sep + "ProjectedDistrictSummaryReport";
			String csvFile = FileUtil.buildFilePath(folderPath, "ProjectedDistrictSummaryReport.CSV");
			String[] splitFileName = csvFile.split("\\.");
			File file = File.createTempFile(splitFileName[0], "."+splitFileName[1]);
			csvWriter = new CSVWriter(new FileWriter(file), ',');		
			csvWriter.writeAll(districtSummaryReportArray);
			csvWriter.flush();
			s3.synchMultipartUpload(csvFile, file);
			FileUtils.deleteQuietly(file);
			districtSummaryReportArray.clear();
		} catch (IOException ex) {
			LOGGER.error("IOException Occured:", ex);
			throw ex;

		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}

	private Double calculateEstimatedTestSessions(Date testDate, Long districtId, String projectionType,
			Long assessmentProgramId, Long currentSchoolYear) {
		List<ProjectedTestingDTO> schoolProjections = projectedTestingDao.getSchoolProjectionsBy(testDate, districtId,
				projectionType, assessmentProgramId, currentSchoolYear);
		Float districtProjection = new Float(0.0F);
		for (ProjectedTestingDTO schoolProjection : schoolProjections) {
			Long schoolEnrollments = projectedTestingDao.getEnrollmentCountBy(schoolProjection.getSchoolId(),
					assessmentProgramId, currentSchoolYear, schoolProjection.getGrade());
			Float schoolProjectedEstimate = 0.0F;
			if (projectionType.equals("T")) {
				schoolProjectedEstimate = schoolEnrollments.floatValue();
			} else {
				Long noOfDaysProjected = projectedTestingDao.getNoOfDaysProjectedBy(schoolProjection.getSchoolId(),
						projectionType, assessmentProgramId, currentSchoolYear, schoolProjection.getGrade());
				if (schoolEnrollments.longValue() > 0 && noOfDaysProjected.longValue() > 0) {
					schoolProjectedEstimate = schoolEnrollments.floatValue() / noOfDaysProjected.floatValue();
				}
				AssessmentProgram assessmentProgram =assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
				if (assessmentProgram.getAbbreviatedname().equals("KELPA2")) {
					schoolProjectedEstimate = schoolProjectedEstimate * 2;
				}
			}
			
			districtProjection = Float.valueOf(districtProjection.floatValue() + schoolProjectedEstimate.floatValue());
		}
		return roundDecimal(districtProjection,2);
	}

	public double roundDecimal(double value, int numberOfDigitsAfterDecimalPoint) {
        BigDecimal bigDecimal = new BigDecimal(value);
        bigDecimal = bigDecimal.setScale(numberOfDigitsAfterDecimalPoint,
                BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
	
	@Override
	public Map<String, Object> getBarChartAssessmentPrograms() {
		Map<String, Object> map = new TreeMap<>();
		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long currentSchoolYear = loggedinUser.getUser().getContractingOrganization().getCurrentSchoolYear();
		List<ProjectedTestingChartDTO> projectedTestingChartDTOs = projectedTestingDao
				.getProjectedTestingForChart(currentSchoolYear);
		
		Set<String> barCharAssessmentPrograms = new HashSet<>();
		for (ProjectedTestingChartDTO projectedTestingChartDTO : projectedTestingChartDTOs) {
			barCharAssessmentPrograms.add(projectedTestingChartDTO.getAssessmentProgram());
		}
		List<String> sortedAPs = new ArrayList<>(barCharAssessmentPrograms);
		Collections.sort(sortedAPs, new ProjectionColorComparator());
		for (String barCharAssessmentProgram : sortedAPs) {
			ProjectionColorCodes code = ProjectionColorCodes.getByAssessmentCode(barCharAssessmentProgram);
			map.put(barCharAssessmentProgram, code.getColorCode());
		}
		return map;
	}
}
