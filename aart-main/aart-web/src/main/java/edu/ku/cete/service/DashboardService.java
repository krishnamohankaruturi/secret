/**
 * 
 */
package edu.ku.cete.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.report.DashboardReactivations;
import edu.ku.cete.domain.report.DashboardScoringSummary;
import edu.ku.cete.domain.report.DashboardTestingOutsideHours;
import edu.ku.cete.domain.report.DashboardTestingSummary;
import edu.ku.cete.domain.report.DashboardShortDurationTesting;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.apierrors.ApiAllErrorRecords;
import edu.ku.cete.domain.apierrors.ApiErrorsRecord;
import edu.ku.cete.ksde.kids.result.KidsAllErrorMessages;
import edu.ku.cete.ksde.kids.result.KidsDashboardRecord;
import edu.ku.cete.ksde.kids.result.KidsRecentRecords;

/**
 * @author Kiran Reddy Taduru
 * Jul 18, 2017 2:16:38 PM
 */
public interface DashboardService {

	int insertKidsDashboardRecord(KidsDashboardRecord kidsDashboardRecord);
	
	int updateKidDashboardRecord(KidsDashboardRecord kidsDashboardRecord);
	
	List<KidsDashboardRecord> getDashboardRecords(String recordType, String stateStudentIdentifier, Long aypSchoolId, Long attendanceSchoolId, String subjectCode);
	
	int updateExistingRecordsByTypeSchIdStdId(String recordType, String stateStudentIdentifier, Long aypSchoolId, Long attendanceSchoolId, String subjectCode, Long recordCommonId);

	List<KidsDashboardRecord> getDashBoardMessagesToView(Map<String, Object> criteria, String sortByColumn, String sortType,
			   int offset, Integer limitCount);

	Integer getCountOfDashBoardMessagesToView(Map<String, Object> criteria);

	List<String> getRecordTypes(Long organizationId, String stateStudentIdentifier);

	List<String> getMessageTypes(Long organizationId, String stateStudentIdentifier);

	List<KidsAllErrorMessages> getAllErrorMessages(Map<String, Object> criteria);

	List<KidsRecentRecords> geRecentKidsRecord(Map<String, Object> criteria);	
	
	List<DashboardTestingSummary> getTestingSummary(Long stateOrgId, Long districtOrgId, Long schoolOrgId, List<Long> assessmentProgramIds, Boolean showClassroomId, List<Long> classroomIds);
	
	File getTestingSummaryCSV(List<Long> stateList, Long districtOrgId, Long schoolOrgId, List<Long> assessmentProgramIds, User user, boolean hasReactivationPermission, boolean hasMiddayRunCompleted) throws IOException;	
	
	List<DashboardScoringSummary> getScoringSummary(Long orgId, List<Long> assessmentProgramIds);
	
	File getScoringSummaryCSV(Long orgId, List<Long> assessmentProgramIds, User user) throws IOException;
	
	List<DashboardReactivations> getReactivationsSummary(Long orgId, List<Long> apIds, String timeframe, String orgTimeZone, Date schoolStartDate);
	
	File getReactivationsSummaryCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException;
	
	List<DashboardReactivations> getReactivationsDetail(Long orgId, List<Long> apIds, String timeframe, Map<String, Object> criteria, String sortByColumn, String sortType, Integer skipCount, Integer limitCount, String orgTimeZone, Date schoolStartDate);
	
	Long getReactivationsDetailCount(Long orgId, List<Long> apIds, String timeframe, Map<String, Object> criteria, String sortByColumn, String sortType, String orgTimeZone, Date schoolStartDate);
	
	File getReactivationsDetailCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException;
	
	List<DashboardTestingOutsideHours> getTestingOutsideHours(Long orgId, List<Long> apIds, String timeframe, String orgTimeZone, Date schoolStartDate);
	
	File getTestingOutsideHoursCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException;

	List<DashboardShortDurationTesting> getShortDurationTesting(Long orgId, List<Long> apIds, String timeframe, String orgTimeZone, Date schoolStartDate);
	
	File getShortDurationTestingCSV(Long orgId, List<Long> assessmentProgramIds, Date schoolStartDate) throws IOException;

	boolean hasMiddayRunCompleted();
	
	Date getMostRecentRunTime();
	
	/**
	 * 
	 * Changes for F851 API Errors Dashboard 
	 */
	
	List<String> getErrorRecordTypes();
	
	List<String> getApiErrorsRequestTypes();
	
	List<ApiErrorsRecord> getApiErrorsToView(Map<String, Object> criteria, String sortByColumn, String sortType, int offset, Integer limitCount);
	
	Integer getCountOfApiErrorsToView (Map<String, Object> criteria);

	List<ApiAllErrorRecords> getAllApiErrorMessages(Map<String, Object> criteria);
	
	List<DashboardTestingSummary> getTestingSummaryAllStates(Long stateOrgId, List<Long> assessmentProgramIds, Boolean showClassroomId, List<Long> classroomIds, List<Long> districtIds);
	
}
