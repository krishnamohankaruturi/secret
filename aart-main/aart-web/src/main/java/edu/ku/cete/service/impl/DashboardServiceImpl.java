/**
 * 
 */
package edu.ku.cete.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.text.DecimalFormat;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.warehouse.model.DashboardMapper;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.dashboard.ReactivationsDetail;
import edu.ku.cete.domain.dashboard.ReactivationsSummary;
import edu.ku.cete.domain.dashboard.TestingOutsideHours;
import edu.ku.cete.domain.report.DashboardReactivations;
import edu.ku.cete.domain.report.DashboardScoringSummary;
import edu.ku.cete.domain.report.DashboardTestingOutsideHours;
import edu.ku.cete.domain.report.DashboardTestingSummary;
import edu.ku.cete.domain.report.DashboardShortDurationTesting;
import edu.ku.cete.domain.dashboard.ShortDurationTesting;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.apierrors.ApiAllErrorRecords;
import edu.ku.cete.domain.apierrors.ApiErrorsRecord;
import edu.ku.cete.ksde.kids.result.KidsAllErrorMessages;
import edu.ku.cete.ksde.kids.result.KidsDashboardRecord;
import edu.ku.cete.ksde.kids.result.KidsRecentRecords;
import edu.ku.cete.model.APIDashboardErrorMapper;

import edu.ku.cete.report.model.KidsDashboardRecordMapper;
import edu.ku.cete.service.DashboardService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.service.AssessmentProgramService;

/**
 * @author Kiran Reddy Taduru
 * Jul 18, 2017 2:18:11 PM
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class DashboardServiceImpl implements DashboardService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServiceImpl.class);

	@Autowired
	private KidsDashboardRecordMapper kidsDashboardMapper;
	
	@Autowired
	private DashboardMapper dashboardMapper;
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
    private RosterService rosterService;
	
	@Autowired
	private APIDashboardErrorMapper apiDashboardErrorMapper;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	public static final String ASOFSTRING_PLACEHOLDER = "_ASOFSTRING";
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertKidsDashboardRecord(KidsDashboardRecord kidsDashboardRecord) {
		return kidsDashboardMapper.insertSelective(kidsDashboardRecord);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateKidDashboardRecord(KidsDashboardRecord kidsDashboardRecord) {
		return kidsDashboardMapper.updateKidsDashboardRecord(kidsDashboardRecord);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KidsDashboardRecord> getDashboardRecords(String recordType, String stateStudentIdentifier,
			Long aypSchoolId, Long attendanceSchoolId, String subjectCode) {
		return kidsDashboardMapper.getKidsDashboardRecords(recordType, stateStudentIdentifier, aypSchoolId, attendanceSchoolId, subjectCode);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateExistingRecordsByTypeSchIdStdId(String recordType, String stateStudentIdentifier, Long aypSchoolId,
			Long attendanceSchoolId, String subjectCode, Long recordCommonId) {
		return kidsDashboardMapper.updateExistingRecordsByTypeSchIdStdId(recordType, stateStudentIdentifier, aypSchoolId, attendanceSchoolId, subjectCode, recordCommonId);
	}

	@Override
	 @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	 public List<KidsDashboardRecord> getDashBoardMessagesToView(Map<String, Object> criteria, String sortByColumn, String sortType,
	   int offset, Integer limitCount) {
		  criteria.put("limit", limitCount);
		  criteria.put("offset", (offset < 0) ? 0 : offset);
		  criteria.put("sortByColumn", sortByColumn);
		  criteria.put("sortType", sortType);
		  return kidsDashboardMapper.getDashBoardMessagesToView(criteria);
	 }

	 @Override
	 @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	 public Integer getCountOfDashBoardMessagesToView(Map<String, Object> criteria) {  
		 return kidsDashboardMapper.getCountOfDashBoardMessagesToView(criteria);
	 }

	@Override 
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getRecordTypes(Long organizationId, String stateStudentIdentifier) {
		return kidsDashboardMapper.getRecordTypes(organizationId, stateStudentIdentifier);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getMessageTypes(Long organizationId, String stateStudentIdentifier) {		
		return kidsDashboardMapper.getMessageTypes(organizationId, stateStudentIdentifier);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KidsAllErrorMessages> getAllErrorMessages(Map<String, Object> criteria) {		
		return kidsDashboardMapper.getAllErrorMessages(criteria);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KidsRecentRecords> geRecentKidsRecord(Map<String, Object> criteria) {
		return kidsDashboardMapper.geRecentKidsRecord(criteria);
	}
	
	//Added showClassroomId and classroomIds parameters for PLTW
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardTestingSummary> getTestingSummary(Long stateOrgId, Long districtOrgId, Long schoolOrgId, List<Long> assessmentProgramIds, Boolean showClassroomId, List<Long> classroomIds) {
		return dashboardMapper.getTestingSummary(stateOrgId, districtOrgId, schoolOrgId, assessmentProgramIds, showClassroomId, classroomIds);
	}
	
	//Added for getting all states
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardTestingSummary> getTestingSummaryAllStates(Long stateOrgId, List<Long> assessmentProgramIds, Boolean showClassroomId, List<Long> classroomIds, List<Long> districtIds) {
		LOGGER.debug("Getting testing summary details for all states and districts");
		return dashboardMapper.getTestingSummaryAllStates(stateOrgId, assessmentProgramIds, showClassroomId, classroomIds, districtIds);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public File getTestingSummaryCSV(List<Long> stateList, Long districtOrgId, Long schoolOrgId, List<Long> assessmentProgramIds, User user, boolean hasReactivationPermission, boolean hasMiddayRunCompleted) throws IOException {
		Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		String schoolYearString = (schoolYear - 1) + "-" + schoolYear;
		Long orgId = null;
		String orgName = null;
		OrganizationTreeDetail otd = null;
		List<Organization> children = new ArrayList<Organization>();
		String childLevel=null;
		
		List<String[]> lines = new ArrayList<String[]>();
		Long userId = user.getId();
		//Add the list and method to get the classroomIds
		List<Long> classroomIds = new ArrayList<Long>();
		if (user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW) && user.isTeacher()) {
			classroomIds = rosterService.getClassroomIds(userId, schoolYear);
		}
		List<DashboardTestingSummary> data=null;
		Boolean showClassroomId = false;
		if(user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW) && (user.isTeacher() || !(user.getCurrentOrganizationType().equals(CommonConstants.ORGANIZATION_STATE_TYPE_ID)))){
			showClassroomId = true;
		}
			
		if(stateList.size()<=1) {
			if (schoolOrgId != null){
				orgId=schoolOrgId;
				otd = orgService.getOrganizationDetailById(orgId);
				orgName=otd.getSchoolName();
			}else if (districtOrgId != null){
				orgId=districtOrgId;
				otd = orgService.getOrganizationDetailById(orgId);
				childLevel=CommonConstants.ORGANIZATION_SCHOOL_CODE;
				children = orgService.getAllChildrenByOrgTypeCode(orgId, childLevel);
				orgName=otd.getDistrictName();
			}else if (stateList.get(0) != null){
				orgId=stateList.get(0);;
				otd = orgService.getOrganizationDetailById(orgId);
				childLevel=CommonConstants.ORGANIZATION_DISTRICT_CODE;
				children = orgService.getAllChildrenByOrgTypeCode(orgId, childLevel);
				orgName=otd.getStateName(); 
			}
			if(stateList.size()==0) {
				data = getTestingSummary(null, districtOrgId, schoolOrgId, assessmentProgramIds,showClassroomId, classroomIds);
			}
			else {
				data = getTestingSummary(stateList.get(0), districtOrgId, schoolOrgId, assessmentProgramIds,showClassroomId, classroomIds);
			}
			lines.addAll(populateTestingSummaryObjectsToCSVLines(data, orgName, hasReactivationPermission, hasMiddayRunCompleted, user));

			List<String[]> childLines = new ArrayList<String[]>();

			for (Organization child : children) {
				if (childLevel.equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)){
					data = getTestingSummary(otd.getStateId(), child.getId(), null, assessmentProgramIds, showClassroomId, classroomIds);
				}else if (childLevel.equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)){
					data = getTestingSummary(otd.getStateId(), otd.getDistrictId(), child.getId(), assessmentProgramIds, showClassroomId, classroomIds);
				}

				List<String[]> tmpChildLines = populateTestingSummaryObjectsToCSVLines(data, child.getOrganizationName(), hasReactivationPermission, hasMiddayRunCompleted, user);
				childLines.addAll(tmpChildLines);
			}

			if (childLines.size() > 1) {
				childLines = sortLines(childLines);
			}

			lines.addAll(childLines);
		}
		//This part is added to get the details of all states for a state level user
		else 
		{
			for(int i=0;i<stateList.size();i++) {
				orgId=stateList.get(i);
				otd = orgService.getOrganizationDetailById(orgId);
				List<Long> districtIds = new ArrayList<Long>();
				children = orgService.getImmediateChildren(orgId);
				orgName=otd.getStateName();
				
				for (Organization child : children) {
					districtIds.add(child.getId());
				}
				
				//Getting the details at State level.
				data = getTestingSummary(stateList.get(i), districtOrgId, schoolOrgId, assessmentProgramIds,showClassroomId, classroomIds);
				lines.addAll(populateTestingSummaryObjectsToCSVLines(data, orgName, hasReactivationPermission, hasMiddayRunCompleted, user));

				//Getting the details at District Level.
				data = getTestingSummaryAllStates(otd.getStateId(), assessmentProgramIds, showClassroomId, classroomIds, districtIds);
				List<String[]> childLines = populateTestingSummaryObjectsToCSVLines(data, null, hasReactivationPermission, hasMiddayRunCompleted, user);
				if (childLines.size() > 1) {
					childLines = sortLines(childLines);
				}
				lines.addAll(childLines);
				
			}
		}
		//Added the variables to show the column header as Subject or Course and Classroom Id in the csv file based on the assessment program
		String subOrcourse="Subject";
		String classroomID = "Classroom ID";
			
		if (user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW))
		{
			subOrcourse="Course";
		}
		if(!showClassroomId)
			// add these at the beginning after the sort.
			lines.add(0, new String[]{
					"Organization Name",
					"Assessment Program",
					subOrcourse,
					"Test Sessions Completed (Today)",
					"Test Sessions Completed (Prior Day)",
					"Test Sessions Completed (" + schoolYearString + ")",
					"Students Assigned (" + schoolYearString + ")",
					"Students Completed (" + schoolYearString + ")",
					"Percent Completed ("  + schoolYearString + ")",
					"Test Sessions Reactivated (Prior Day)",
					"Test Sessions Reactivated (" + schoolYearString + ")"
		});
		else 
		{
			lines.add(0, new String[]{
					"Organization Name",
					"Assessment Program",
					subOrcourse,
					classroomID,
					"Test Sessions Completed (Today)",
					"Test Sessions Completed (Prior Day)",
					"Test Sessions Completed (" + schoolYearString + ")",
					"Students Assigned (" + schoolYearString + ")",
					"Students Completed (" + schoolYearString + ")",
					"Percent Completed ("  + schoolYearString + ")",
					"Test Sessions Reactivated (Prior Day)",
					"Test Sessions Reactivated (" + schoolYearString + ")"
			});	
		}
		
		String fileName = stateList.size()>1 ? "AllStates" : orgName ;
		String userPiece = user == null ? "" : ("_" + user.getId());
		File csv = File.createTempFile(
				"TestingSummary_" +
						fileName +
						ASOFSTRING_PLACEHOLDER +
						userPiece +
						"_" + (new Date()).getTime(), ".csv");
		writeCSV(csv, lines);
		return csv;
	}
	
	private List<String[]> populateTestingSummaryObjectsToCSVLines(List<DashboardTestingSummary> data, String orgNameOverride, boolean hasReactivationPermission, boolean hasMiddayRunCompleted, User user) {
		List<String[]> lines = new ArrayList<String[]>();
		Long dlmId = null;
		Long cpassId = null;
		AssessmentProgram dlmAp = assessmentProgramService.findByAbbreviatedName(CommonConstants.ASSESSMENT_PROGRAM_DLM);
		AssessmentProgram cpassAp = assessmentProgramService.findByAbbreviatedName(CommonConstants.ASSESSMENT_PROGRAM_CPASS);
		if (dlmAp !=null && dlmAp.getId() != null) {
			dlmId = dlmAp.getId();
		}
		if (cpassAp !=null && cpassAp.getId() != null) {
			cpassId = cpassAp.getId();
		}
		
		
		if (CollectionUtils.isNotEmpty(data)) {
			for (DashboardTestingSummary dash : data) {
				String orgName = orgNameOverride;
				if (orgNameOverride == null) {
					orgName = dash.getStateName();
					if (dash.getDistrictName() != null) orgName = dash.getDistrictName();
					if (dash.getSchoolName() != null) orgName = dash.getSchoolName();
				}
		//Added the conditions to check non-PLTW or PLTW and different roles
		DecimalFormat decimalFormatter = new DecimalFormat("##.#");
		String studentsPercentCompleted = String.valueOf(Double.valueOf(decimalFormatter.format(dash.getStudentsPercentCompletedThisYear()))) + "%";			
		if (!user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW) || (user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW) && user.getCurrentOrganizationType().equals(CommonConstants.ORGANIZATION_STATE_TYPE_ID)))
		{
			lines.add(new String[]{
					orgName,
					dash.getAssessmentProgram(),
					dash.getContentArea(),
					!hasMiddayRunCompleted ? "n/a" : String.valueOf(dash.getCountSessionsCompletedToday()),
					String.valueOf(dash.getCountSessionsCompletedLastSchoolDay()),
					String.valueOf(dash.getCountSessionsCompletedThisYear()),
					String.valueOf(dash.getCountStudentsAssignedThisYear()),
					(dash.getAssessmentProgramId().equals(cpassId) 
					|| dash.getAssessmentProgram().contains("ITI") 
					|| dash.getAssessmentProgram().contains("Instructional")) ? "n/a" : String.valueOf(dash.getCountStudentsCompletedThisYear()),
					(dash.getAssessmentProgramId().equals(cpassId) 
						|| dash.getAssessmentProgram().contains("ITI") 
						|| dash.getAssessmentProgram().contains("Instructional")
						|| dash.getAssessmentProgramId().equals(dlmId)) ? "n/a" : studentsPercentCompleted,
					(!hasReactivationPermission || dash.getAssessmentProgramId().equals(dlmId)) ? "n/a" : String.valueOf(dash.getCountReactivatedLastSchoolDay()),
					(!hasReactivationPermission || dash.getAssessmentProgramId().equals(dlmId)) ? "n/a" : String.valueOf(dash.getCountReactivatedThisYear())
				});
		}
		else if 
		(user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_PLTW) || user.isTeacher())
		{
			lines.add(new String[]{
					orgName,
					dash.getAssessmentProgram(),
					dash.getContentArea(),
					null != dash.getClassroomId() ? dash.getClassroomId().toString() : "0", //To check the null value
					!hasMiddayRunCompleted ? "n/a" : String.valueOf(dash.getCountSessionsCompletedToday()),
					String.valueOf(dash.getCountSessionsCompletedLastSchoolDay()),
					String.valueOf(dash.getCountSessionsCompletedThisYear()),
					String.valueOf(dash.getCountStudentsAssignedThisYear()),
					(dash.getAssessmentProgramId().equals(cpassId) 
					|| dash.getAssessmentProgram().contains("ITI") 
					|| dash.getAssessmentProgram().contains("Instructional")) ? "n/a" : String.valueOf(dash.getCountStudentsCompletedThisYear()),
					(dash.getAssessmentProgramId().equals(cpassId) 
							|| dash.getAssessmentProgram().contains("ITI") 
							|| dash.getAssessmentProgram().contains("Instructional") 
							|| dash.getAssessmentProgramId().equals(dlmId)) ? "n/a" : studentsPercentCompleted,
					(!hasReactivationPermission || dash.getAssessmentProgramId().equals(dlmId)) ? "n/a" : String.valueOf(dash.getCountReactivatedLastSchoolDay()),
					(!hasReactivationPermission || dash.getAssessmentProgramId().equals(dlmId)) ? "n/a" : String.valueOf(dash.getCountReactivatedThisYear())
				});
		}
	}
}
		return lines;
	}

	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardScoringSummary> getScoringSummary(Long orgId, List<Long> assessmentProgramIds) {
		return dashboardMapper.getScoringSummary(orgId, assessmentProgramIds);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public File getScoringSummaryCSV(Long orgId, List<Long> assessmentProgramIds, User user) throws IOException {
		Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		String schoolYearString = (schoolYear - 1) + "-" + schoolYear;
		
		OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
		String orgName = otd.getStateName();
		if (otd.getDistrictName() != null) {
			orgName = otd.getDistrictName();
		}
		if (otd.getSchoolName() != null) {
			orgName = otd.getSchoolName();
		}
		
		List<Organization> children = new ArrayList<Organization>();
		if (otd.getSchoolId() != null) {
			// do nothing, since there are no children for a school
		} else if (otd.getDistrictId() != null) {
			children = orgService.getAllChildrenByOrgTypeCode(orgId, "SCH");
		} else if (otd.getStateId() != null) {
			children = orgService.getAllChildrenByOrgTypeCode(orgId, "DT");
		}
		
		boolean hasMiddayRunCompleted = hasMiddayRunCompleted();
		
		List<DashboardScoringSummary> data = getScoringSummary(orgId, assessmentProgramIds);
		List<String[]> lines = new ArrayList<String[]>();
		lines.addAll(populateScoringSummaryObjectsToCSVLines(data, orgName, hasMiddayRunCompleted));
		
		List<String[]> childLines = new ArrayList<String[]>();
		for (Organization child : children) {
			data = getScoringSummary(child.getId(), assessmentProgramIds);
			List<String[]> tmpChildLines = populateScoringSummaryObjectsToCSVLines(data, child.getOrganizationName(), hasMiddayRunCompleted);
			childLines.addAll(tmpChildLines);
		}
		
		if (childLines.size() > 1) {
			childLines = sortLines(childLines);
		}
		
		lines.addAll(childLines);
		
		// add these at the beginning after the sort.
		lines.add(0, new String[]{
			"Organization Name",
			"Assessment Program",
			"Subject",
			"Test Sessions Assigned (" + schoolYearString + ")",
			"Test Sessions Scored (Today)",
			"Test Sessions Scored (Prior Day)",
			"Test Sessions Scored (" + schoolYearString + ")",
			"Percent Completed (" + schoolYearString + ")",
			"Test Sessions Completed But Not Scored (" + schoolYearString + ")"
		});
		
		String userPiece = user == null ? "" : ("_" + user.getId());
		File csv = File.createTempFile(
			"ScoringSummary_" +
			orgName +
			ASOFSTRING_PLACEHOLDER +
			userPiece +
			"_" + (new Date()).getTime(), ".csv");
		writeCSV(csv, lines);
		return csv;
	}
	
	private List<String[]> populateScoringSummaryObjectsToCSVLines(List<DashboardScoringSummary> data, String orgNameOverride, boolean middayRunComplete) {
		List<String[]> lines = new ArrayList<String[]>();
		Long dlmId = assessmentProgramService.findByAbbreviatedName(CommonConstants.ASSESSMENT_PROGRAM_DLM).getId();
		if (CollectionUtils.isNotEmpty(data)) {
			for (DashboardScoringSummary dash : data) {
				String orgName = orgNameOverride;
				if (orgNameOverride == null) {
					orgName = dash.getStateName();
					if (dash.getDistrictName() != null) orgName = dash.getDistrictName();
					if (dash.getSchoolName() != null) orgName = dash.getSchoolName();
				}
				DecimalFormat decimalFormatter = new DecimalFormat("##.#");
				String percentCompleted = String.valueOf(Double.valueOf(decimalFormatter.format(dash.getPercentCompletedThisYear()))) + "%";
				
				lines.add(new String[]{
					orgName,
					dash.getAssessmentProgram(),
					dash.getContentArea(),
					String.valueOf(dash.getCountSessionsAssignedThisYear()),
					!middayRunComplete ? "n/a" : String.valueOf(dash.getCountSessionsScoredToday()),
					String.valueOf(dash.getCountSessionsScoredLastSchoolDay()),
					String.valueOf(dash.getCountSessionsScoredThisYear()),
					(dash.getAssessmentProgramId().equals(dlmId)) ? "n/a" : percentCompleted,
					String.valueOf(dash.getCountSessionsCompletedNotScored()),
				});
			}
		}
		return lines;
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardReactivations> getReactivationsSummary(Long orgId, List<Long> apIds, String timeframe, String orgTimeZone, Date schoolStartDate) {
		return dashboardMapper.getReactivationsSummary(orgId, apIds, timeframe, orgTimeZone, schoolStartDate);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public File getReactivationsSummaryCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException {
		
		OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
		String orgName = otd.getStateName();
		if (otd.getDistrictName() != null) {
			orgName = otd.getDistrictName();
		}
		if (otd.getSchoolName() != null) {
			orgName = otd.getSchoolName();
		}
		//Getting the organization specific timezone to pass to the method

		TimeZone tz = orgService.getTimeZoneForOrganization(orgId);

		if (tz == null) {

			// default to central

			tz = TimeZone.getTimeZone("US/Central");

		}
		List<DashboardReactivations> rawData = getReactivationsSummary(orgId, assessmentProgramIds, "year", tz.getID(),schoolStartDate);
		List<ReactivationsSummary> data = ReactivationsSummary.convertList(rawData);
		List<String[]> lines = new ArrayList<String[]>();
		lines.addAll(populateReactivationsSummaryObjectsToCSVLines(data));
	
		
		// add these at the beginning after the sort.
		lines.add(0, new String[]{
			"Assessment Program",
			"District",
			"School",
			"Test Name",
			"Count",
			"Reactivated By"
		});
		
		File csv = File.createTempFile(
			"ReactivationsSummary_" +
			orgName +
			ASOFSTRING_PLACEHOLDER +
			"_" + (new Date()).getTime(), ".csv");
		writeCSV(csv, lines);
		return csv;
	}
	
	private List<String[]> populateReactivationsSummaryObjectsToCSVLines(List<ReactivationsSummary> data) {
		List<String[]> lines = new ArrayList<String[]>();
		if (CollectionUtils.isNotEmpty(data)) {
			for (ReactivationsSummary dash : data) {

				lines.add(new String[]{
					dash.getAssessmentProgram(),
					dash.getDistrict(),
					dash.getSchool(),
					dash.getTestName(),
					dash.getCount().toString(),
					dash.getReactivatedBy()
				});
			}
		}
		return lines;
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardReactivations> getReactivationsDetail(Long orgId, List<Long> apIds, String timeframe, Map<String, Object> criteria, String sortByColumn, String sortType, Integer skipCount, Integer limitCount, String orgTimeZone, Date schoolStartDate) {
		return dashboardMapper.getReactivationsDetail(orgId, apIds, timeframe, criteria, sortByColumn, sortType, skipCount, limitCount, orgTimeZone, schoolStartDate);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long getReactivationsDetailCount(Long orgId, List<Long> apIds, String timeframe, Map<String, Object> criteria, String sortByColumn, String sortType, String orgTimeZone, Date schoolStartDate) {
		return dashboardMapper.getReactivationsDetailCount(orgId, apIds, timeframe, criteria, sortByColumn, sortType, orgTimeZone, schoolStartDate);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public File getReactivationsDetailCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException {
		//Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		//String schoolYearString = (schoolYear - 1) + "-" + schoolYear;
		
		OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
		String orgName = otd.getStateName();
		if (otd.getDistrictName() != null) {
			orgName = otd.getDistrictName();
		}
		if (otd.getSchoolName() != null) {
			orgName = otd.getSchoolName();
		}
		//Getting the organization specific timezone to pass in the method
		TimeZone tz = orgService.getTimeZoneForOrganization(orgId);
		if (tz == null) {
			// default to central
			tz = TimeZone.getTimeZone("US/Central");
		}
		
		List<DashboardReactivations> rawData = getReactivationsDetail(orgId, assessmentProgramIds, "year" , null, null, "asc", null, null, tz.getID(),schoolStartDate);
		List<ReactivationsDetail> data = ReactivationsDetail.convertList(rawData, orgService);
		List<String[]> lines = new ArrayList<String[]>();
		lines.addAll(populateReactivationsDetailObjectsToCSVLines(data));
	
		
		// add these at the beginning after the sort.
		lines.add(0, new String[]{
			"Assessment Program",
			"District",
			"School",
			"Test Name",
			"Student",
			"Reactivated By",
			"Date"
		});
		
		File csv = File.createTempFile(
			"ReactivationsDetail_" +
			orgName +
			ASOFSTRING_PLACEHOLDER +
			"_" + (new Date()).getTime(), ".csv");
		writeCSV(csv, lines);
		return csv;
	}
	
	private List<String[]> populateReactivationsDetailObjectsToCSVLines(List<ReactivationsDetail> data) {
		List<String[]> lines = new ArrayList<String[]>();
		if (CollectionUtils.isNotEmpty(data)) {
			for (ReactivationsDetail dash : data) {

				lines.add(new String[]{
					dash.getAssessmentProgram(),
					dash.getDistrict(),
					dash.getSchool(),
					dash.getTestName(),
					dash.getStudent(),
					dash.getReactivatedBy(),
					dash.getReactivatedDate()
				});
			}
		}
		return lines;
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardTestingOutsideHours> getTestingOutsideHours(Long orgId, List<Long> apIds, String timeframe, String orgTimeZone, Date schoolStartDate) {
		return dashboardMapper.getTestingOutsideHours(orgId, apIds, timeframe, orgTimeZone,schoolStartDate);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public File getTestingOutsideHoursCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException {
		//Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		//String schoolYearString = (schoolYear - 1) + "-" + schoolYear;
		
		OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
		String orgName = otd.getStateName();
		if (otd.getDistrictName() != null) {
			orgName = otd.getDistrictName();
		}
		if (otd.getSchoolName() != null) {
			orgName = otd.getSchoolName();
		}
		//Passing the organization specific timezone
		TimeZone tz = orgService.getTimeZoneForOrganization(orgId);
		if (tz == null) {
			// default to central
			tz = TimeZone.getTimeZone("US/Central");
		}
		
		List<DashboardTestingOutsideHours> rawData = getTestingOutsideHours(orgId, assessmentProgramIds, "year", tz.getID(),schoolStartDate);
		List<TestingOutsideHours> data = TestingOutsideHours.convertList(rawData, orgService);
		List<String[]> lines = new ArrayList<String[]>();
		lines.addAll(populateTestingOutsideHoursObjectsToCSVLines(data));
	
		
		// add these at the beginning after the sort.
		lines.add(0, new String[]{
			"Assessment Program",
			"District",
			"School",
			"Test Name",
			"Student",
			"Started",
			"Ended"
		});
		
		File csv = File.createTempFile(
			"TestingOutsideHours_" +
			orgName +
			ASOFSTRING_PLACEHOLDER +
			"_" + (new Date()).getTime(), ".csv");
		writeCSV(csv, lines);
		return csv;
	}
	
	private List<String[]> populateTestingOutsideHoursObjectsToCSVLines(List<TestingOutsideHours> data) {
		List<String[]> lines = new ArrayList<String[]>();
		if (CollectionUtils.isNotEmpty(data)) {
			for (TestingOutsideHours dash : data) {

				lines.add(new String[]{
					dash.getAssessmentProgram(),
					dash.getDistrict(),
					dash.getSchool(),
					dash.getTestName(),
					dash.getStudent(),
					dash.getStarted(),
					dash.getEnded()
				});
			}
		}
		return lines;
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardShortDurationTesting> getShortDurationTesting(Long orgId, List<Long> apIds, String timeframe, String orgTimeZone, Date schoolStartDate) {
		return dashboardMapper.getShortDurationTesting(orgId, apIds, timeframe, orgTimeZone,schoolStartDate);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public File getShortDurationTestingCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException {		
		OrganizationTreeDetail otd = orgService.getOrganizationDetailById(orgId);
		String orgName = otd.getStateName();
		if (otd.getDistrictName() != null) {
			orgName = otd.getDistrictName();
		}
		if (otd.getSchoolName() != null) {
			orgName = otd.getSchoolName();
		}
		//Passing the organization specific timezone
		TimeZone tz = orgService.getTimeZoneForOrganization(orgId);
		if (tz == null) {
			// default to central
			tz = TimeZone.getTimeZone("US/Central");
		}
		
		List<DashboardShortDurationTesting> rawData = getShortDurationTesting(orgId, assessmentProgramIds, "year", tz.getID(),schoolStartDate);
		List<ShortDurationTesting> data = ShortDurationTesting.convertList(rawData, orgService);
		List<String[]> lines = new ArrayList<String[]>();
		lines.addAll(populateShortDurationTestingObjectsToCSVLines(data));
	
		// add these at the beginning after the sort.
		lines.add(0, new String[]{
			"Assessment Program",
			"District",
			"School",
			"Teacher",
			"Roster Name",
			"Subject",
			"Grade",
			"State Student Identifier",
			"Student First Name",
			"Student Last Name",
			"Test Name",
			"Item Count",
			"All Correct",
			"Timespan",
			"Started",
			"Ended"
		});
		
		File csv = File.createTempFile(
			"TestsCompletedShortTime_" +
			orgName +
			ASOFSTRING_PLACEHOLDER +
			"_" + (new Date()).getTime(), ".csv");
		writeCSV(csv, lines);
		return csv;
	}
	
	private List<String[]> populateShortDurationTestingObjectsToCSVLines(List<ShortDurationTesting> data) {
		List<String[]> lines = new ArrayList<String[]>();
		if (CollectionUtils.isNotEmpty(data)) {
			for (ShortDurationTesting dash : data) {
				lines.add(new String[]{
					dash.getAssessmentProgram(),
					dash.getDistrict(),
					dash.getSchool(),
					dash.getTeacher(),
					dash.getRosterName(),	
					dash.getSubject(),
					dash.getGrade(),
					dash.getStateStudentIdentifier(),
					dash.getStudentFirstName(),
					dash.getStudentLastName(),
					dash.getTestName(),
					dash.getItemCount().toString(),
					dash.getAllCorrectIndicator(),
					dash.getTestTimeSpan(),
					dash.getStartedDate(),
					dash.getEndedDate()
				});
			}
		}
		return lines;
	}
	
	private void writeCSV(File csvFile, List<String[]> lines) throws IOException {
        CSVWriter csvWriter = null;
        
        try {
        	csvWriter = new CSVWriter(new FileWriter(csvFile, false), ',', '"');
        	csvWriter.writeAll(lines);
        } catch (IOException ex) {
   	 		LOGGER.error("IOException Occured:", ex);
   	 		throw ex;
        } finally {
        	if(csvWriter != null) {
        		csvWriter.close();
        	}
        }
	}

	@Override
	public boolean hasMiddayRunCompleted() {
		return Boolean.TRUE.equals(dashboardMapper.hasMiddayRunCompleted());
	}
	
	@Override
	public Date getMostRecentRunTime() {
		return dashboardMapper.getMostRecentRunTime();
	}

	/**
	 * Changes for F851 API Errors Dashboard 
	 */

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getErrorRecordTypes() {
		return apiDashboardErrorMapper.getErrorRecordTypes();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getApiErrorsRequestTypes() {
		return apiDashboardErrorMapper.getApiErrorsRequestTypes();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ApiErrorsRecord> getApiErrorsToView(Map<String, Object> criteria, String sortByColumn, String sortType,
			int offset, Integer limitCount) {
		criteria.put("limit", limitCount);
		criteria.put("offset", (offset < 0) ? 0 : offset);
		criteria.put("sortByColumn", sortByColumn);
		criteria.put("sortType", sortType);
		return apiDashboardErrorMapper.getApiErrorsToView(criteria);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getCountOfApiErrorsToView(Map<String, Object> criteria) {
		return apiDashboardErrorMapper.getCountOfApiErrorsToView(criteria);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ApiAllErrorRecords> getAllApiErrorMessages(Map<String, Object> criteria) {
		return apiDashboardErrorMapper.getAllApiErrorMessages(criteria);
	}

	private List<String[]> sortLines(List<String[]> childLines){
		Collections.sort(childLines, new Comparator<String[]>(){
			@Override
			public int compare(String[] a, String[] b) {
				// compare org name, assessment program, and subject
				int compareToIndex = 2;
				for (int varIndex = 0; varIndex < compareToIndex; varIndex++) {
					int cmpVar = a[varIndex].compareTo(b[varIndex]);
					if (cmpVar != 0) return cmpVar;
				}
				return 0;
			}
		});
		return childLines;
	}
}
