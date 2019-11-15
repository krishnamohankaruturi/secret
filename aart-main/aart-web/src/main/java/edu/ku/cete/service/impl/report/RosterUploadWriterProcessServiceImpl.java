package edu.ku.cete.service.impl.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.StudentTrackerBand;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.StudentsTestsExample;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.EnrollmentsRostersExample;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.model.StudentTrackerBandMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.mapper.StudentsTestSectionsDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.SourceTypeEnum;

/**
 *  Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(Enrollment)
 */
@Service
public class RosterUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(RosterUploadWriterProcessServiceImpl.class);

	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;

	/**
	 *  Added By Udaya Kiran Jagana 
	 * User Story: US18182:
	 * rosterDao.
	 */
	@Autowired
	private RosterDao rosterDao;

	@Autowired
    private StudentsTestsDao studentsTestsDao;
	
	@Autowired
	private StudentsTestSectionsDao studentsTestSectionsDao;
	
	@Autowired
	private EnrollmentsRostersDao enrollmentsRostersDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private EnrollmentsRostersService enrollmentsRostersService;

	@Autowired
	private RosterService rosterService;
	
    @Autowired
    private TestSessionService testSessionService;

    @Autowired
	private ItiTestSessionService itiTestSessionService;
    
    @Autowired
    private StudentTrackerBandMapper studentTrackerBandMapper;
    
    @Autowired
    private StudentDao studentDao;
    
    @Autowired
    private StudentsTestsService studentsTestsService;
    
	@Value("${testsession.status.rosterunenrolled}")
	private String testsessionStatusRosterUnenrolledCategoryCode;  
	
	@Value("${ismart.assessmentProgram.abbreviatedName}")
	private String ISMART_PROGRAM_ABBREVIATEDNAME;
	
	@Value("${ismart2.assessmentProgram.abbreviatedName}")
	private String ISMART_2_PROGRAM_ABBREVIATEDNAME;

	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug(" Roster writer ");
		for( Object object : objects){
			RosterRecord rosterRecord = (RosterRecord) object;
			addorUpdateRosterUpload(rosterRecord);
		} 
	}

	@Transactional(readOnly=false,propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final void addorUpdateRosterUpload(RosterRecord rosterRecord) {
		//TODO add this to the aspect.
		
		//Following are doing via the roster upload: 
		//Create new roster with the given student/subject information
		//Update the given roster with the given student/subject information
		//Remove the given student from the roster
		
		//If DLM upload, then check for multiple rosters for the given student/subject and transfer from old to new. Else follow the following procedure as is. 
		
		Long currentContextUserId = rosterRecord.getCreatedUser();
		
		User currentUser = rosterRecord.getCurrentUser();
		Long currentUserAssessmentProgramId = currentUser.getCurrentAssessmentProgramId();
		String assessmentProgramCode = currentUser.getCurrentAssessmentProgramName();
		Long schoolYear = new Long(rosterRecord.getCurrentSchoolYear());
		boolean insert = true;
		/*
		 * UdayaKiran :Created for US18182 -To save roster json in audit table 
		 * */
		String rosterBeforejsonString = null;
		String rosterAfterjsonString = null;
		String action=EventTypeEnum.INSERT.getCode();
		//RecordSaveStatus scrsRecordSaveStatus = null;
		Enrollment newEnrollment = rosterRecord.getEnrollment();
		newEnrollment.setAttendanceSchoolId(rosterRecord.getSchool().getId());
		newEnrollment.setStudentId(rosterRecord.getStudent().getId());
		newEnrollment.setCurrentContextUserId(currentContextUserId);
		newEnrollment.setAypSchoolId(rosterRecord.getRoster().getAypSchoolId());
		Student student = rosterRecord.getStudent();
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		// if student exists in the system then try to find his enrollments for
		// the school.
		if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
			enrollments = enrollmentService.addIfNotPresentForRosterUpload(newEnrollment);
			// even if one enrollment is added, it means we have added new
			// enrollments.
			for (Enrollment enrollment : enrollments) {
				if (enrollment.getSaveStatus().equals(
						RecordSaveStatus.ENROLLMENT_ADDED)) {
					//	scrsRecordSaveStatus = RecordSaveStatus.ENROLLMENT_ADDED;
				}
			}
		}
		// DO not look for enrollments as the student is just created.
		else {
			rosterRecord.setCreated(true);
			newEnrollment = enrollmentService.add(newEnrollment);
			enrollments.add(newEnrollment);
			//scrsRecordSaveStatus = RecordSaveStatus.ENROLLMENT_ADDED;
		} 
		rosterRecord.getRoster().setCreatedUser(currentContextUserId);
		rosterRecord.getRoster().setModifiedUser(currentContextUserId);
		rosterRecord.getRoster().setAuditColumnProperties();
		//Roster newRoster = rosterService.insert(rosterRecord.getRoster());
		rosterRecord.getRoster().setSourceType(SourceTypeEnum.UPLOAD.getCode());
		List<Roster> rosters = rosterService.addIfNotPresent(rosterRecord.getRoster(), rosterRecord.getRoster().getAttendanceSchoolId());
		Roster newRoster = rosters.get(0);

		//To audit the Uploaded User
		if(!RecordSaveStatus.ROSTER_ADDED.equals(newRoster.getSaveStatus())){
			insert = false;
			/*
			 * UdayaKiran :Created for US18182 -To save roster json in audit table 
			 * */
			Roster existingRoster= rosterDao.getRosterJsonFormatData(newRoster.getId());			
			//need to add to the audit story 	    	
			existingRoster.buildJsonString();
			rosterBeforejsonString=existingRoster.getRosterJsonStr();
			action=EventTypeEnum.UPDATE.getCode();

		}
		/* addtoDomainAuditHistory(newRoster.getId(),currentContextUserId,insert);*/
		// add enrollment to roster.
		for (Enrollment enrollment : enrollments) {
			EnrollmentsRosters enrollmentsRosters = new EnrollmentsRosters(
					enrollment.getId(), newRoster.getId());

			if (null != rosterRecord.getEnrollmentStatusCode()
					&& (99 == rosterRecord.getEnrollmentStatusCode() || 4 == rosterRecord
					.getEnrollmentStatusCode())) {
				enrollmentsRosters.setActiveFlag(false);
			}
			newRoster.setSourceType(SourceTypeEnum.UPLOAD.getCode());			
			String removeFromRosterStatus = rosterRecord.getRemovefromroster();
			if ( removeFromRosterStatus != null && (removeFromRosterStatus.equalsIgnoreCase("Yes")
					|| removeFromRosterStatus.equalsIgnoreCase("Remove") )) {
				enrollmentsRosters.setActiveFlag(false);
				if (rosterRecord.getEnrollmentStatus() != null) {
					enrollmentsRosters.setCourseEnrollmentStatusId(rosterRecord
							.getEnrollmentStatus().getId());
				}
				enrollmentsRosters.setCurrentContextUserId(currentContextUserId);								
				int noOfRostersUpdated = enrollmentsRostersService.updateEnrollementRosterToInActive(enrollmentsRosters);
				rosterRecord.setSaveStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS);
				if (noOfRostersUpdated > 0) {
					rosterService.addRmStuFromRostEventToDomainAduidtHistory(enrollmentsRosters, newRoster);
					rosterRecord.setRemoved(true);
				}
				boolean isStudentDLM = (studentDao.checkIfDLMByEnrollmentId(enrollmentsRosters.getEnrollmentId()) > 0) ? true : false;
				if(isStudentDLM){	
					studentsTestsService.rosterUnEnrollStudent(enrollmentsRosters.getEnrollmentId(), enrollmentsRosters.getRosterId(), currentUser.getId());				
				}
			} else {

				if (rosterRecord.getEnrollmentStatus() != null) {
					enrollmentsRosters.setCourseEnrollmentStatusId(rosterRecord
							.getEnrollmentStatus().getId());
				}
				enrollmentsRosters.setCurrentContextUserId(currentContextUserId);
				enrollmentsRostersService.addEnrollmentToRoster(enrollmentsRosters);
				rosterService.addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, newRoster);
				
			}
		}
		
		if(currentUserAssessmentProgramId.equals(assessmentProgramService.findByAbbreviatedName("DLM").getId())
				|| assessmentProgramCode.equalsIgnoreCase(ISMART_PROGRAM_ABBREVIATEDNAME)
				|| assessmentProgramCode.equalsIgnoreCase(ISMART_2_PROGRAM_ABBREVIATEDNAME)){

			
				List<StudentRoster> studentsAlreadyOnExistingRosters = new ArrayList<StudentRoster>();
				for(Enrollment enrollment : enrollments){
					 
					Long enrollmentId = enrollment.getId();
					List<StudentRoster> studentRosterList = rosterService.checkIfRosterExistsForEnrollmentSubjectCourse(enrollmentId, rosterRecord.getStateSubjectAreaId(), rosterRecord.getStateCourseId(), schoolYear, assessmentProgramCode);
					//Student exists on Roster A
					if(studentRosterList!=null && studentRosterList.size()>0){
						//Under roster A, student has complete, pending, unused, or in progress testlets.Student belongs to DLM only.
						//Student either has saved ITI plan or active testsessions in pending, unused, inprogress, complete status. 
						for(StudentRoster studentRoster : studentRosterList){
							/*if(!studentRoster.getRoster().getId().equals(newRoster.getId()))*/
								studentsAlreadyOnExistingRosters.add(studentRoster);
						}
					}
					
					//For exited students
					List<StudentRoster> studentRostersForExitedStudentsList = rosterService.checkForTestSessionsOnExitedRostersAlso(enrollmentId, newRoster.getStateSubjectAreaId(), newRoster.getStateCoursesId(), (long) newRoster.getCurrentSchoolYear(), assessmentProgramCode);
					if(studentRostersForExitedStudentsList!=null && studentRostersForExitedStudentsList.size()>0){
						for(StudentRoster studentRosterForExitedStudents : studentRostersForExitedStudentsList){
							/*if(!studentRosterForExitedStudents.getRoster().getId().equals(newRoster.getId()))*/
								studentsAlreadyOnExistingRosters.add(studentRosterForExitedStudents);
						}
					}
				}
				
				Long modifiedUserId = currentContextUserId;
				if(studentsAlreadyOnExistingRosters.size()>0){
					
					for(StudentRoster enrollmentsRoster : studentsAlreadyOnExistingRosters){
						
						Long currentEnrollmentId = enrollmentsRoster.getStudentEnrlId(); 
						Long oldEnrollmentId = enrollmentsRoster.getEnrollmentId();
						Long oldRosterId = enrollmentsRoster.getRoster().getId();
						
		    			List<StudentsTests> studentsTests = studentsTestsDao.getTestSessionsForDLDAutomationWithinActiveOTW(oldEnrollmentId, oldRosterId);
		    			
						if(studentsTests != null && studentsTests.size() > 0) {
								Long inProgressStatusid = categoryDao.getCategoryId("inprogress", "STUDENT_TEST_STATUS");
								Long inProgressTimedOutStatusid = categoryDao.getCategoryId("inprogresstimedout", "STUDENT_TEST_STATUS");
								Long pendingStatusid = categoryDao.getCategoryId("pending", "STUDENT_TEST_STATUS");
								Long unusedStatusid = categoryDao.getCategoryId("unused", "STUDENT_TEST_STATUS");
								Long completeStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
								Long exitClearUnenrolledCompleteStatusid = categoryDao.getCategoryId("exitclearunenrolled-complete", "STUDENT_TEST_STATUS");
								Long exitClearUnenrolledInProgressStatusid = categoryDao.getCategoryId("exitclearunenrolled-inprogress", "STUDENT_TEST_STATUS");
								Long exitClearUnenrolledInProgressTimedOutStatusid = categoryDao.getCategoryId("exitclearunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
								Long exitClearUnenrolledPendingStatusid = categoryDao.getCategoryId("exitclearunenrolled-pending", "STUDENT_TEST_STATUS");
								Long exitClearUnenrolledUnusedStatusid = categoryDao.getCategoryId("exitclearunenrolled-unused", "STUDENT_TEST_STATUS");
								Long rosterUnenrolledCompleteStatusid = categoryDao.getCategoryId("rosterunenrolled-complete", "STUDENT_TEST_STATUS");
								Long rosterUnenrolledInProgressStatusid = categoryDao.getCategoryId("rosterunenrolled-inprogress", "STUDENT_TEST_STATUS");
								Long rosterUnenrolledInProgressTimedOutStatusid = categoryDao.getCategoryId("rosterunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
								Long rosterUnenrolledPendingStatusid = categoryDao.getCategoryId("rosterunenrolled-pending", "STUDENT_TEST_STATUS");
								Long rosterUnenrolledUnusedStatusid = categoryDao.getCategoryId("rosterunenrolled-unused", "STUDENT_TEST_STATUS");
								
								Map<Integer, Long> testSessionOrderMap = new HashMap<Integer, Long>();
								
								for(StudentsTests studentsTest: studentsTests) {
									
									Long oldTestSessionId = studentsTest.getTestSessionId();
									if (studentsTest.getTestSession().getSource() != null && 
											studentsTest.getTestSession().getSource().equals("BATCHAUTO")) {
										testSessionOrderMap.put(studentsTest.getCurrentTestNumber(), oldTestSessionId);
									}
									
									//Verify if test is in progress or unused
									if(studentsTest != null 
										&& (studentsTest.getStatus().equals(inProgressStatusid) 
												|| studentsTest.getStatus().equals(inProgressTimedOutStatusid)
												|| studentsTest.getStatus().equals(unusedStatusid)
												|| studentsTest.getStatus().equals(pendingStatusid)
												|| studentsTest.getStatus().equals(completeStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledCompleteStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressTimedOutStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledPendingStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledUnusedStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledCompleteStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledInProgressStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledInProgressTimedOutStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledPendingStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledUnusedStatusid))) 
									{
								
									    //Transfer test sessions (summative and field testlets in pending,unused, inprogress, completed status)
										testSessionService.transferTestSessionToNewRoster(oldTestSessionId, newRoster.getId(), modifiedUserId, currentEnrollmentId);

										if(studentsTest.getStatus().equals(exitClearUnenrolledCompleteStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressTimedOutStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledPendingStatusid)
												|| studentsTest.getStatus().equals(exitClearUnenrolledUnusedStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledCompleteStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledInProgressStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledInProgressTimedOutStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledPendingStatusid)
												|| studentsTest.getStatus().equals(rosterUnenrolledUnusedStatusid)){
										
											studentsTestsDao.reactivateByPrimaryKeyForRosterChange(studentsTest.getId(), modifiedUserId, currentEnrollmentId);
											List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(studentsTest.getId()));
											for (Long studentsTestSectionsId : studentsTestSectionIds) {
												studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
											}
										} else if (studentsTest.getStatus().equals(inProgressStatusid) 
												|| studentsTest.getStatus().equals(inProgressTimedOutStatusid)
												|| studentsTest.getStatus().equals(unusedStatusid)
												|| studentsTest.getStatus().equals(pendingStatusid)
												|| studentsTest.getStatus().equals(completeStatusid)) 
										{
											StudentsTests st = new StudentsTests();
											st.setEnrollmentId(currentEnrollmentId);
											st.setId(studentsTest.getId());
											st.setModifiedUser(modifiedUserId);
											st.setActiveFlag(true);
											studentsTestsDao.updateByPrimaryKeySelective(st);
										}
										logger.debug("The testsessions of student "+ enrollmentsRoster.getStudentId()+" with enrollment " + currentEnrollmentId+" transferred successfully from old roster "+oldRosterId+" to new roster "+newRoster.getId());
									}
								}
								Map<Integer, Long> studentTrackerBandOrderMap = new HashMap<>();
								
								List<Long> bandIds = studentTrackerBandMapper.getBandsForStudentAndContentArea(enrollmentsRoster.getStudentId(), newRoster.getStateSubjectAreaId());
								int count = 0;
								for (Long bandId : bandIds) {
									count++;
									studentTrackerBandOrderMap.put(count, bandId);
								}
								
								/** if (studentTrackerBandOrderMap.size() ==  testSessionOrderMap.size()){
									for (int i = 1; i <= testSessionOrderMap.size(); i++) {
										long bandId = studentTrackerBandOrderMap.get(i);
										long testSessionId = testSessionOrderMap.get(i);
										StudentTrackerBand band = new StudentTrackerBand();
										band.setId(bandId);
										band.setTestSessionId(testSessionId);
										band.setModifiedUser(modifiedUserId);
										studentTrackerBandMapper.updateByPrimaryKeySelective(band);
									}
								} else {
									logger.debug("The number of student tracker bands of student "+ enrollmentsRoster.getStudentId()+" did not match the number of test sessions.");
			    				} **/
						} else {
							logger.debug("The testsessions of student "+ enrollmentsRoster.getStudentId()+" with enrollment " + currentEnrollmentId+" not transferred from old roster "+oldRosterId+" to new roster "+newRoster.getId());
						}

						//Move saved ITI plan to the new roster ==> Inactive the pending plan on the old roster and insert the same iti record with the new roster id
						List<ItiTestSessionHistory> pendingITIPlansOfTheOldRoster =  itiTestSessionService.getPendingITIsByEnrlAndRosterId(oldEnrollmentId, oldRosterId);
						if(pendingITIPlansOfTheOldRoster!=null && pendingITIPlansOfTheOldRoster.size()>0){
							itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(pendingITIPlansOfTheOldRoster, currentEnrollmentId, newRoster.getId(), modifiedUserId);
							logger.debug("The pending ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
						}
						
						//Move unused, inprogress, completed ITI plans to the new roster from roster. Do not inactivate but transfer based on old rosterid and enrollmentid. 
						List<ItiTestSessionHistory> itiPlansExceptPending =  itiTestSessionService.getITIPlansExceptPendingUsingEnrlAndRosterId(oldEnrollmentId, oldRosterId);

						if(itiPlansExceptPending!=null && itiPlansExceptPending.size()>0){
							itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(itiPlansExceptPending, currentEnrollmentId, newRoster.getId(), modifiedUserId);
							Long completeStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
							for(ItiTestSessionHistory itiPlansOfTheOldRoster : itiPlansExceptPending){
								//Transfer test sessions (ITI testlets in unused, inprogress, completed status)
								testSessionService.transferTestSessionToNewRoster(itiPlansOfTheOldRoster.getTestSessionId(), newRoster.getId(), modifiedUserId, currentEnrollmentId);

					        	StudentsTestsExample example = new StudentsTestsExample();
					            StudentsTestsExample.Criteria criteria = example.createCriteria();
					            criteria.andStudentIdEqualTo(itiPlansOfTheOldRoster.getStudentId());
					            criteria.andTestSessionIdEqualTo(itiPlansOfTheOldRoster.getTestSessionId());
					            if(itiPlansOfTheOldRoster.getStudentsTestStatusId() != null && itiPlansOfTheOldRoster.getStudentsTestStatusId().equals(completeStatusid)){
					            	criteria.andIsActive();
					            } else {
					            	criteria.andIsInactive();
					            }
								List<StudentsTests> itiStudentsTests = studentsTestsDao.selectByExample(example);
								
								for (StudentsTests st : itiStudentsTests) {
									studentsTestsDao.reactivateByPrimaryKeyForRosterChange(st.getId(), modifiedUserId, currentEnrollmentId);
									List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(st.getId()));
									for (Long studentsTestSectionsId : studentsTestSectionIds) {
										studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
									}
								}
							}
							
							logger.debug("The unused, inprogress and complete ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
						}
						
						List<ItiTestSessionHistory> exitedITIPlansOfTheOldRoster =  itiTestSessionService.getUnenrolledITIsByEnrlAndRosterId(oldEnrollmentId, oldRosterId);
						
						
						if(exitedITIPlansOfTheOldRoster!=null && exitedITIPlansOfTheOldRoster.size()>0){
							itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(exitedITIPlansOfTheOldRoster, currentEnrollmentId, newRoster.getId(), modifiedUserId);
							Long exitClearUnenrolledPendingStatusid = categoryDao.getCategoryId("exitclearunenrolled-STARTED", CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
        					Long rosterUnenrolledPendingStatusid = categoryDao.getCategoryId("rosterunenrolled-STARTED", CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
        					List<Long> startedStatuses = Arrays.asList(exitClearUnenrolledPendingStatusid, rosterUnenrolledPendingStatusid);
							for(ItiTestSessionHistory itiPlansOfTheOldRoster : exitedITIPlansOfTheOldRoster){
								if (!startedStatuses.contains(itiPlansOfTheOldRoster.getStatus())) {
									//Transfer test sessions (ITI testlets in unused, inprogress, completed status)
									testSessionService.transferTestSessionToNewRoster(itiPlansOfTheOldRoster.getTestSessionId(), newRoster.getId(), modifiedUserId, currentEnrollmentId);
			
						        	StudentsTestsExample example = new StudentsTestsExample();
						            StudentsTestsExample.Criteria criteria = example.createCriteria();
						            criteria.andStudentIdEqualTo(itiPlansOfTheOldRoster.getStudentId());
						            criteria.andTestSessionIdEqualTo(itiPlansOfTheOldRoster.getTestSessionId());
						            criteria.andIsInactive();
									List<StudentsTests> itiStudentsTests = studentsTestsDao.selectByExample(example);
									
									for (StudentsTests st : itiStudentsTests) {
										studentsTestsDao.reactivateByPrimaryKeyForRosterChange(st.getId(), modifiedUserId, currentEnrollmentId);
										List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(st.getId()));
										for (Long studentsTestSectionsId : studentsTestSectionIds) {
											studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
										}
									}
								}
							}
							
							logger.debug("The exited, transfered, and unrostered ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
						}
						
						if(!oldRosterId.equals(newRoster.getId())) {
							//Inactivate EnrollmentRosters on Old Roster
			    			EnrollmentsRosters oldEnrlRoster = enrollmentsRostersDao.getEnrollmentInfoByRosterIdEnrollmentId(oldRosterId, oldEnrollmentId);
			    			if(oldEnrlRoster!=null){
				    			oldEnrlRoster.setActiveFlag(Boolean.FALSE);
				    			oldEnrlRoster.setModifiedUser(modifiedUserId);
				    			oldEnrlRoster.setModifiedDate(new Date());
			    			
			    			
				    			EnrollmentsRostersExample example = new EnrollmentsRostersExample();
				    			EnrollmentsRostersExample.Criteria criteria = example.createCriteria();
				    			criteria.andEnrollmentIdEqualTo(oldEnrollmentId);
				    			criteria.andRosterIdEqualTo(oldRosterId);
				    			
				    			enrollmentsRostersDao.updateByExampleSelective(oldEnrlRoster, example);
				    			logger.debug("Inactivated enrollmentroster: "+oldEnrollmentId+" "+oldRosterId);
			    			}
						}
		    		}
				}
		}
		
		//rosterRecord.setSaveStatus(RecordSaveStatus.SCRS_RECORD_UPLOAD_COMPLETE);
		/*
		 * UdayaKiran :Created for US18182 -To save roster json in audit table 
		 * */
		Roster roster= rosterDao.getRosterJsonFormatData(newRoster.getId());			
		//need to add to the audit story
		roster.buildJsonString();		
		rosterAfterjsonString=roster.getRosterJsonStr();
		rosterService.insertIntoDomainAuditHistory(roster.getId(), roster.getModifiedUser(), action, SourceTypeEnum.UPLOAD.getCode(), rosterBeforejsonString, rosterAfterjsonString);
		
		if(currentUserAssessmentProgramId.equals(assessmentProgramService.findByAbbreviatedName("DLM").getId())){
			if(rosterService.checkIfRosterHasEnrollments(newRoster.getId())<=0){
				rosterService.disableRoster(newRoster);
			}
		}
	}
}
