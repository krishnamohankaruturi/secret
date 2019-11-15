/**
 * @author bmohanty_sta
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15536 : Student Tracker - Simple Version 1 (preliminary)
 * Service class to implement student tracker batch process related functionality.
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.StudentTrackerBluePrintStatus;
import edu.ku.cete.domain.TestSpecification;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.report.domain.BatchRegistrationReason;

@Service
public interface StudentTrackerService {
	List<StudentTracker> getUntrackedStudents(Organization contractingOrganization, ContentArea contentArea, Long operationalTestWindowId, 
			Integer offset, Integer limitCount);

	List<StudentTracker> getTrackedStudents(
			Organization contractingOrganization, ContentArea contentArea, Long operationalWindowId, boolean isInterim,
			Integer offset, Integer limitCount);

	StudentTrackerBand processStudentForMultiEE(StudentTracker trackerStudent, ContentArea contentArea, 
			List<ComplexityBand> allBands, List<BatchRegistrationReason> reasons, String orgPoolType, Long operationalWindowId, Organization contractingOrganization, User user);
	
	StudentTrackerBand processStudentForSingleEE(StudentTracker trackerStudent, ContentArea contentArea, 
			List<ComplexityBand> allBands, List<BatchRegistrationReason> reasons, String orgPoolType, Long currentSchoolYear, Long operationalWindowId, User user);

	void addBandRecommendation(StudentTracker trackerStudent);

	List<TestSpecification> getTestSpecByStudentTracker(Long studentTrackerId, Long operationalWindowId);

	TestSpecification getTestSpecByPoolGradeContentArea(String poolType, String gradeCode, Long contentAreaId, Long operationalWindowId);

	List<String> getContentCodesByTestSpecAndRandking(Long testSpecificationId, Long ranking);

	void updateBandTestSession(StudentTracker trackerStudent);

	StudentTracker getTrackerByStudentAndContentArea(Long studentId, Long contentAreaId, Long operationalWindowId, Long courseId, Long schoolYear);

	int getCountOfBandsByStudentTracker(Long studentTrackerId, Long testSpecificationId, Long operationalWindowId);

	List<String> getEEsByStudentTracker(Long studentTrackerId, Long testSpecificationId, Long operationalWindowId);	

	List<StudentTrackerBand> getStudentTrackerBandByStudentId(Long studentId);
	
	List<StudentTrackerBand> getStudentTrackerBandByStudentIdWithActiveOTW(Long studentId);
	
	int clearTestSessionByStudentIdAndTestSessionId(Long studentId, Long testSessionId, Long userId);

	List<StudentTracker> getOnlyUntrackedStudentsFromStudentTracker(
			Organization contractingOrganization, ContentArea contentArea, Long operationalTestWindowId,
			Integer offset, Integer limitCount);

	StudentTrackerBand getLatestTrackerBand(Long studentTrackerId);

	int insertStudentTrackerBluePrintStatus(StudentTrackerBluePrintStatus studentTrackerBluePrintStatus);

	StudentTrackerBluePrintStatus selectStudentTrackerBluePrintStatus(StudentTrackerBluePrintStatus studentTrackerBluePrintStatus);

	List<Long> findStudentTrackerBandsByStudentAndTestSession(Long studentId, List<Long> testSessionIds, boolean activeFlag);

	void deactivate(List<Long> studentTrackerBandIdsForDeactivation, Long modifiedUserId);
	void reactivate(List<Long> studentTrackerBandIdsForDeactivation, Long modifiedUserId);

	void changeStatusToUntrackedByIds(Set<Long> studentTrackerIds, Long modifiedUserId);

	List<StudentTrackerBand> getStudentTrackerBandsByIds(List<Long> studentTrackerBandIds);

	int deleteCompletedStudentTrackerBlueprintStatus(Long studentTrackerId, Long operationalWindowId);

	List<StudentTrackerBand> getStudentTrackerBandByFieldTestEEs(Long studentTrackerId, List<ContentFrameworkDetail> listOfContentCodesInFieldTests);

	boolean didStudentMeetSpecificBlueprintCriteria(Long studentId, Long contentAreaId, String gradeCode,
			int schoolYear, Long criteriaNumber);
}
