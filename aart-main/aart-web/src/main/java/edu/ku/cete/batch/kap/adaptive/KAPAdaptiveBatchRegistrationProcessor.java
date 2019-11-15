
package edu.ku.cete.batch.kap.adaptive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.StudentTestInfo;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StageEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;
import net.jawr.web.util.StringUtils;

public class KAPAdaptiveBatchRegistrationProcessor implements ItemProcessor<Enrollment, KapAdaptiveContext> {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private StudentProfileService studentProfileService;

	@Autowired
	protected TestSessionService testSessionService;	

	@Autowired
	protected TestService testService;

	@Autowired
	protected TestStatusConfiguration testStatusConfiguration;

	@Autowired
	protected StudentSessionRuleConverter studentSessionRuleConverter;

	@Autowired
	protected TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;

	@Autowired
	private StudentsTestsService studentsTestsService;

	@Autowired
	private TestCollectionService tcService;
	
	@Value("${autoregistration.varianttypeid.code.eng}")
	protected String AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG;

	protected GradeCourse gradeCourse;
	protected TestType testType;
	protected List<TestCollection> testCollections;
	protected ContentArea contentArea;
	protected Long batchRegistrationId;
	private StepExecution stepExecution;
	protected String enrollmentMethod;
	protected String assessmentProgramCode;
	private Organization contractingOrganization;
	private Assessment assessment;
	private String interimFlag;
	private Long unusedTestSessionId;
	private Long completedTestSessionId;
	private StudentSessionRule studentSessionRule;
	
	@Override
	public KapAdaptiveContext process(Enrollment enrollment) throws Exception {
		logger.debug("--> process");
		KapAdaptiveContext processCtx = new KapAdaptiveContext();
		processCtx.setEnrollment(enrollment);
		
		Set<String> accessibilityFlags = getAccessibleFlags(enrollment);
		if (isInterimFlag()) {
			processForInterimStages(accessibilityFlags, enrollment, processCtx);
		} else {
			processForAllStages(processCtx, accessibilityFlags, enrollment);
		}
		return processCtx;
	}

	/**
	 * 
	 * @param accessibilityFlags
	 * @param enrollment
	 * @param enrollments
	 * @throws DuplicateTestSessionNameException
	 */
	private void processForInterimStages(Set<String> accessibilityFlags, Enrollment enrollment, KapAdaptiveContext processCtx)
			throws DuplicateTestSessionNameException {
		for (TestCollection testCollection : testCollections) {
			if (StageEnum.STAGE2.getCode().equalsIgnoreCase(testCollection.getStage().getCode())) {
				if (isTestCollectionAssignedtoOnePanel(testCollection, enrollment.getStudentId())) {
					enrollStudentToInterimTests(processCtx, enrollment, testCollection, accessibilityFlags);
				}
			}			
		}
	}

	/**
	 * @param enrollments
	 * @param enrollment
	 * @param testCollectionTests
	 * @param testCollection
	 * @throws DuplicateTestSessionNameException
	 */
	private void enrollStudentToInterimTests(KapAdaptiveContext processCtx, Enrollment enrollment,
			TestCollection testCollection, Set<String> accessibilityFlags) throws DuplicateTestSessionNameException {

		/*boolean stageCompletedTestInPreviousSchool = false;
		// verify if student has moved from different school, if so look for the studentstest has completed or not
		if (enrollment.isPreviousEnrollmentExists()){			
			List<StudentsTests>testsCompletedInOtherDistrict = getCompletedTestsInOtherSchool(enrollment, testCollection);
			if(CollectionUtils.isEmpty(testsCompletedInOtherDistrict)){
				stageCompletedTestInPreviousSchool = updateInterimTransferredTestSessionId(enrollment, testCollection);
			}else{		
				stageCompletedTestInPreviousSchool = true;
				updateInterimTransferredTestSessionId(enrollment, testCollection);
			}			
		}*/
		//if student already completed test for the current stage, do nothing else regular process
		//if (!stageCompletedTestInPreviousSchool) {
			String testSessioName = prepareTestSessionName(testCollection, enrollment);
			logger.debug("Looking for test session:" + testSessioName);
			StudentTestInfo predecessorTestInfo = null;
			if (testCollection.getStage().getPredecessorId() != null && testCollection.getStage().getPredecessorId() > 0) {
				predecessorTestInfo = getPredecessorTestInfo(enrollment, testCollection);
				if (predecessorTestInfo != null && predecessorTestInfo.getTestStatus() != null
						&& predecessorTestInfo.getTestStatus().equalsIgnoreCase("complete")) {
					TestSession testSession = getExistingTestSession(enrollment, testCollection);
					if (testSession != null && testSession.getId() > 0L) {
						assignStudentToInterimStagesForExistingTestSession(predecessorTestInfo, enrollment, testCollection,
								accessibilityFlags, testSession);
					} else {
						testSession = assignStudentToInterimStagesForNewTestSession(predecessorTestInfo, enrollment,
								testCollection, accessibilityFlags, testSession);
					}
					if (testSession != null && testSession.getId() != null) {
						logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - "
								+ testSession.getId());
						processCtx.getTestSessions().add(testSession);
					}
				}
			}
		//}
	}

	private boolean updateInterimTransferredTestSessionId(Enrollment enrollment, TestCollection testCollection) {
		boolean stageCompletedTestInPreviousSchool;
		List<StudentsTests> completeTests = getStageCompletedTest(enrollment, testCollection);
		if(completeTests == null || completeTests.isEmpty()){
			stageCompletedTestInPreviousSchool = false;
		}else{
			stageCompletedTestInPreviousSchool = true;
			//This is new schools' test session
			TestSession testSession = getExistingTestSession(enrollment, testCollection);
			if(testSession != null && completeTests.get(0) != null 
					&& (completeTests.get(0).getTransferedTestSessionId() == null 
					|| completeTests.get(0).getTransferedTestSessionId() == 0)){
				studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(), testSession.getId(), enrollment.getId());
				String errorMsg = "Assigned Student Id: " + enrollment.getStudentId() + " to Test Session Id: " + testSession.getId();
				writeReason(enrollment.getStudentId(), errorMsg);
				logger.debug(errorMsg);
			}
		}
		return stageCompletedTestInPreviousSchool;
	}

	
	/**
	 * 
	 * @param enrollment
	 * @param testCollection
	 * @return
	 */
	private StudentTestInfo getPredecessorTestInfo(Enrollment enrollment, TestCollection testCollection) {
		StudentTestInfo predecessorTestInfo = testSessionService.getPredecessorTestInfo(assessment.getId(),
				testType.getId(), contentArea.getId(), gradeCourse.getId(), enrollment.getAttendanceSchool().getId(),
				contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getPredecessorId(),
				enrollment.getStudentId(), testCollection.getOperationalTestWindowId());
		return predecessorTestInfo;
	}

	/**
	 * 
	 * @param enrollment
	 * @param testCollection
	 * @return
	 */
	private TestSession getExistingTestSession(Enrollment enrollment, TestCollection testCollection) {
		return getExistingTestSession(enrollment.getAttendanceSchool().getId(), testCollection);
	}

	/**
	 * 
	 * @param enrollment
	 * @param testCollection
	 * @return
	 */
	private TestSession getExistingTestSession(Long attendanceSchoolId, TestCollection testCollection) {
		TestSession testSession = null;
		// Check if the test session already exists or not.
		List<TestSession> existingTestSessions = testSessionService.getAutoRegisteredSessions(assessment.getId(),
				testType.getId(), contentArea.getId(), gradeCourse.getId(), attendanceSchoolId,
				contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(),
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId());
		if (CollectionUtils.isNotEmpty(existingTestSessions)) {
			testSession = existingTestSessions.get(0);
			logger.debug("Found session " + testSession.getId());
		}
		return testSession;
	}

	/**
	 * 
	 * @param predecessorTestInfo
	 * @param enrollment
	 * @param testCollection
	 * @param accessibilityFlags
	 * @param testSession
	 */
	private void assignStudentToInterimStagesForExistingTestSession(StudentTestInfo predecessorTestInfo,
			Enrollment enrollment, TestCollection testCollection, Set<String> accessibilityFlags,
			TestSession testSession) {
		logger.debug("Adding enrollment - " + enrollment.getId() + " to existing testsession - " + testSession.getId());
		List<Test> testsBasedOnThetaAndAccessbleFlags = filteredTestsBasedOnThetaAndAccessbleFlags(predecessorTestInfo,
				enrollment, testCollection, accessibilityFlags);
		if (testsBasedOnThetaAndAccessbleFlags != null && !testsBasedOnThetaAndAccessbleFlags.isEmpty()) {
			studentsTestsService.addStudentExistingSession(enrollment, testSession, testCollection,
					testsBasedOnThetaAndAccessbleFlags, null, null, predecessorTestInfo.getId());
		}

	}

	/**
	 * 
	 * @param predecessorTestInfo
	 * @param enrollment
	 * @param testCollection
	 * @param accessibilityFlags
	 * @return
	 */
	private List<Test> filteredTestsBasedOnThetaAndAccessbleFlags(StudentTestInfo predecessorTestInfo,
			Enrollment enrollment, TestCollection testCollection, Set<String> accessibilityFlags) {
		// These are the tests based on theta values not filtered on
		// accessibility flags
		List<Long> testsBasedOnTheta = testService.getPanelTestsFromThetaValues(predecessorTestInfo.getStudentId(),
				predecessorTestInfo.getTestSessionId(), predecessorTestInfo.getPanelId());
		if (testsBasedOnTheta != null && testsBasedOnTheta.size() > 0) {
			// These tests are filtered based on accessibility flags and test
			// status
			List<Test> filteredTestsBasedOnThetaAndAccessbleFlags = getTestCollectionTestsForAdaptiveStages(
					accessibilityFlags, testCollection.getId(), testsBasedOnTheta);
			if (filteredTestsBasedOnThetaAndAccessbleFlags != null
					&& filteredTestsBasedOnThetaAndAccessbleFlags.size() > 0) {
				return filteredTestsBasedOnThetaAndAccessbleFlags;
			} else {
				String errMsg = "Not able to find test to assign next test for student ["
						+ enrollment.getStudentId() + "] for completed test ID ["
						+ predecessorTestInfo.getTestId() + "] in Panel [" + predecessorTestInfo.getPanelId() + "]";
				logger.debug(errMsg);
				writeReason(enrollment.getStudentId(), errMsg);
			}
		} else {
			String errMsg = "Not able to assign test for student [" + enrollment.getStudentId()
					+ "] for completed test ID [" + predecessorTestInfo.getTestId() + "] in Panel ["
					+ predecessorTestInfo.getPanelId() + "]";
			logger.debug(errMsg);
			writeReason(enrollment.getStudentId(), errMsg);
		}
		return null;
	}

	/**
	 * 
	 * @param predecessorTestInfo
	 * @param enrollment
	 * @param testCollection
	 * @param accessibilityFlags
	 * @param testSession
	 * @return
	 */
	private TestSession assignStudentToInterimStagesForNewTestSession(StudentTestInfo predecessorTestInfo,
			Enrollment enrollment, TestCollection testCollection, Set<String> accessibilityFlags,
			TestSession testSession) throws DuplicateTestSessionNameException {
		logger.debug("Adding enrollment - " + enrollment.getId() + " to new Test Session");
		Long otwId = testCollection.getOperationalTestWindowId();
		//List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
        //StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
		// If the test session does not exists, create a new test session.
		testSession = new TestSession();
		testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
		testSession.setName(prepareTestSessionName(testCollection, enrollment));
		testSession.setStatus(unusedTestSessionId);
		testSession.setActiveFlag(true);
		testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
		testSession.setTestTypeId(testType.getId());
		testSession.setGradeCourseId(gradeCourse.getId());
		testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
		testSession.setTestPanelId(testCollection.getPanelId());
		if (testCollection.getStage() != null) {
			testSession.setStageId(testCollection.getStage().getId());
		}
		testSession.setTestCollectionId(testCollection.getTestCollectionId());
		testSession.setOperationalTestWindowId(otwId);
		testSession.setSubjectAreaId(enrollment.getSubjectAreaId());

		// If previous test status id complete, assign new test based on
		// theta values
		if (predecessorTestInfo != null && predecessorTestInfo.getTestStatus() != null
				&& predecessorTestInfo.getTestStatus().equalsIgnoreCase("complete")) {
			List<Test> testsBasedOnThetaAndAccessbleFlags = filteredTestsBasedOnThetaAndAccessbleFlags(
					predecessorTestInfo, enrollment, testCollection, accessibilityFlags);
			if (testsBasedOnThetaAndAccessbleFlags != null && !testsBasedOnThetaAndAccessbleFlags.isEmpty()) {
				testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection,
						AARTCollectionUtil.getIds(testsBasedOnThetaAndAccessbleFlags), studentSessionRule, null,
						predecessorTestInfo.getId());
			}
		}

		return testSession;
	}

	/**
	 * 
	 * @param testCollectionId
	 * @param studentId
	 * @return
	 */

	private boolean isTestCollectionAssignedtoOnePanel(TestCollection testCollection, Long studentId) {
		// Verify if test collection is associated with multiple panels		
		if (testCollection.getIsIncludedInMultiplePanels()) {
			String cErrorMsg = "Test collection [" + testCollection.getTestCollectionId() + "] is associated with multiple panels.";
			writeReason(studentId, cErrorMsg);
			logger.debug(cErrorMsg);
			logger.debug("<-- process raiseSkipError");
			throw new SkipBatchException(cErrorMsg);
		} else {
			return true;
		}
	}

	private void processForAllStages(KapAdaptiveContext kapContext, Set<String> accessibilityFlags, Enrollment enrollment)
			throws DuplicateTestSessionNameException {
		Map<TestCollection, List<Test>> testCollectionTests = new HashMap<TestCollection, List<Test>>();
		String logMsg;
		boolean stage_1_Found = false;
		String panelName = StringUtils.EMPTY;

		for (TestCollection testCollection : testCollections) {
			List<Test> tests = null;
			// Verify if test collection is associated with multiple panels
			//panelCount = tcService.getTestCollectionPanelCount(testCollection.getTestCollectionId());
			if (testCollection.getIsIncludedInMultiplePanels()) {
				String cErrorMsg = "Test collection [" + testCollection.getTestCollectionId()
						+ "] is associated with multiple panels.";
				writeReason(enrollment.getStudentId(), cErrorMsg);
				logger.debug(cErrorMsg);
				logger.debug("<-- process raiseSkipError");
				throw new SkipBatchException(cErrorMsg);
			} else {
				if (!StageEnum.STAGE2.getCode().equalsIgnoreCase(testCollection.getStage().getCode())) {
					// get tests
					tests = getTestCollectionTestsForAdaptiveStages(accessibilityFlags, testCollection.getId(), null);
					if (CollectionUtils.isNotEmpty(tests)) {
						if (StageEnum.STAGE1.getCode().equalsIgnoreCase(testCollection.getStage().getCode())) {
							stage_1_Found = true;
							testCollectionTests.put(testCollection, tests);
							panelName = testCollection.getPanelName();
						}						
						logMsg = String.format(
								"Tests found with this criteria - grade: %s(%d), testtype: %s(%d), contentarea: %s(%d), testcollection: %d, accessibleflags: %s.",
								gradeCourse.getAbbreviatedName(), gradeCourse.getId(), testType.getTestTypeCode(),
								testType.getId(), contentArea.getAbbreviatedName(), contentArea.getId(),
								testCollection.getId(), accessibilityFlags);
						logger.debug(logMsg);
						
					} else {
						logMsg = String.format(
								"No tests found with this criteria - grade: %s(%d), testtype: %s(%d), contentarea: %s(%d), testcollection: %d, accessibleflags: %s.",
								gradeCourse.getAbbreviatedName(), gradeCourse.getId(), testType.getTestTypeCode(),
								testType.getId(), contentArea.getAbbreviatedName(), contentArea.getId(),
								testCollection.getId(), accessibilityFlags);
						logger.debug(logMsg);
						writeReason(enrollment.getStudentId(), logMsg);
					}
				}
			}
		}
		if (!stage_1_Found) {
			logMsg = "Not able to find test collection to assign next test for student ["
					+ enrollment.getStudentId() + "] and Panel [" + panelName + "] for stage [Stage 1]";
			logger.debug(logMsg);
			writeReason(enrollment.getStudentId(), logMsg);
		}
		for (TestCollection testCollection : testCollections) {
			enrollStudentInTests(kapContext, enrollment, testCollection, testCollectionTests.get(testCollection),
					accessibilityFlags);
		}
	}

	/**
	 * @param enrollments
	 * @param enrollment
	 * @param testCollectionTests
	 * @param testCollection
	 * @throws DuplicateTestSessionNameException
	 */
	private void enrollStudentInTests(KapAdaptiveContext kapContext, Enrollment enrollment, TestCollection testCollection,
			List<Test> tests, Set<String> accessibilityFlags) throws DuplicateTestSessionNameException {
		boolean stageCompletedTestInPreviousSchool = false;
		// verify if student has moved from different school, if so look for the studentstest has completed or not
		if (enrollment.isPreviousEnrollmentExists()){
			List<StudentsTests>testsCompletedInOtherDistrict = getCompletedTestsInOtherSchool(enrollment, testCollection);
			if(CollectionUtils.isEmpty(testsCompletedInOtherDistrict)){
				stageCompletedTestInPreviousSchool = updateTransferredTestSessionId(kapContext, enrollment, testCollection, tests, accessibilityFlags);
			}else{
				stageCompletedTestInPreviousSchool = true;
				updateTransferredTestSessionId(kapContext, enrollment, testCollection, tests, accessibilityFlags);				
			}
		}
		//if student already completed test for the current stage, do nothing else regular process
		if (!stageCompletedTestInPreviousSchool) {
			TestSession testSession = getExistingTestSession(enrollment, testCollection);

			if (testSession != null && testSession.getId() > 0L) {
				if (testCollection.getStage().getSortOrder() == 1
						&& StageEnum.STAGE1.getCode().equals(testCollection.getStage().getCode())
						&& CollectionUtils.isNotEmpty(tests)) {
					logger.debug("Adding enrollment - " + enrollment.getId() + " to existing testsession - "
							+ testSession.getId());
					studentsTestsService.addStudentExistingSession(enrollment, testSession, testCollection, tests,
							null);
					kapContext.getTestSessions().add(testSession);
				} else if (testCollection.getStage().getPredecessorId() != null
						&& testCollection.getStage().getPredecessorId() > 0) {
					StudentTestInfo predecessorTestInfo = getPredecessorTestInfo(enrollment, testCollection);
					if (predecessorTestInfo != null && predecessorTestInfo.getTestStatus() != null
							&& predecessorTestInfo.getTestStatus().equalsIgnoreCase("complete")) {
						assignStudentToInterimStagesForExistingTestSession(predecessorTestInfo, enrollment,
								testCollection, accessibilityFlags, testSession);
					}
				}

			} else {
				logger.debug("Adding enrollment - " + enrollment.getId() + " to new Test Session");
				
				testSession = createNewKapTestSession(kapContext, testCollection, enrollment, unusedTestSessionId, tests, null, null, accessibilityFlags);
	        	if (testSession != null && testSession.getId() != null) {
	    			logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - "
	    					+ testSession.getId());
	    			kapContext.getTestSessions().add(testSession);
	    		}
			}
		}
		//insert data here...
		
	}
	
	private boolean updateTransferredTestSessionId(KapAdaptiveContext kapContext, Enrollment enrollment, TestCollection testCollection, List<Test> tests, Set<String> accessibilityFlags) throws DuplicateTestSessionNameException {
		boolean stageCompletedTestInPreviousSchool;
		List<StudentsTests> completeTests = getStageCompletedTest(enrollment, testCollection);
		if(completeTests == null || completeTests.isEmpty()){
			stageCompletedTestInPreviousSchool = false;
		}else{
			stageCompletedTestInPreviousSchool = true;
			//This is new schools' test session
			TestSession existingTestSession = getExistingTestSession(enrollment, testCollection);
			
			/**
			  DE15115
			  Where there are no active existingtestsessions for the studentstests records with all completed testsessions that need to get transferred 
			  are not getting the transferredtessionsid, transferredenrollmentid, previousstudentstestsid columns updated. 
			  
			  	1. create new testsessions (or get the inactivated test sessions --> Not doing this because the testsession name is also getting changed. Do not want to change the legacy code. So creating new)
				2. update new studentstests records and to-be-transferring studentstests records with the transferredtessionsid, transferredenrollmentid, previousstudentstestsid 
			  
			 */
			/**
			 *  CURRENT CODE:
				if(testSession != null && completeTests.get(0) != null 
						&& (completeTests.get(0).getTransferedTestSessionId() == null 
						|| completeTests.get(0).getTransferedTestSessionId() == 0)){
					studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(), testSession.getId(), enrollment.getId());
					String errorMsg = "Assigned Student Id: " + enrollment.getStudentId() + " to Test Session Id: " + testSession.getId();
					writeReason(enrollment.getStudentId(), errorMsg);
					logger.debug(errorMsg);
				}
			*/
			
			Long enrollmentId = enrollment.getId();
			if(completeTests.get(0) != null){
				
				if(existingTestSession != null){ 
					if(
				     (completeTests.get(0).getTransferedTestSessionId() == null || completeTests.get(0).getTransferedTestSessionId() == 0
					 	|| (existingTestSession.getId() != completeTests.get(0).getTransferedTestSessionId() 
					 		&& !existingTestSession.getId().equals(completeTests.get(0).getTransferedTestSessionId()))
					 ) &&
					 (enrollmentId!= completeTests.get(0).getEnrollmentId() && !enrollmentId.equals(completeTests.get(0).getEnrollmentId()))
					 ){
						
						if(existingTestSession.getId()!=completeTests.get(0).getTestSessionId() && !existingTestSession.getId().equals(completeTests.get(0).getTestSessionId())){
							studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(),existingTestSession.getId(), enrollment.getId());
							
							String errorMsg = "Assigned Student Id: " + enrollment.getStudentId() + " to existing Test Session Id: " + existingTestSession.getId();
							writeReason(enrollment.getStudentId(), errorMsg);
							logger.debug(errorMsg);
						}
					}	
					else
						studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(),null, enrollment.getId());
				}
				else{
					
					//For the schools which do not have active testsessions, creating new ones to update the studentstests 
					//These new testsessions will be in unused state 
					TestSession testSession = null;
					logger.debug("Adding enrollment - " + enrollment.getId() + " with completed tests to new Test Session");
					testSession = createNewKapTestSession(kapContext, testCollection, enrollment, unusedTestSessionId, tests, completedTestSessionId, null, accessibilityFlags);
					if (testSession != null && testSession.getId() != null) {
		    			logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - "
		    					+ testSession.getId());
		    			kapContext.getTestSessions().add(testSession);
		    		
		        		//If not update existing records for transferredtessionsid, transferredenrollmentid, previousstudentstestsid
			        	logger.debug("**********************st.id: "+completeTests.get(0).getId()+" ***** transTSID: "+testSession.getId()+" ***** transEID: "+enrollment.getId());
			        	if(completeTests.get(0).getEnrollmentId()!=null && enrollment.getId() != completeTests.get(0).getEnrollmentId()){
			        		studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(),testSession.getId(), enrollment.getId());
			        		studentsTestsService.inactivateStudentsTestsTestBytestSessionId(testSession.getId());
			        	}
					}
		        }
			}
			/** DE15115 End of Code change **/
			
		}
		return stageCompletedTestInPreviousSchool;
	}
	
	private TestSession createNewKapTestSession(KapAdaptiveContext kapContext, TestCollection testCollection, Enrollment enrollment, Long testSessionStatus, List<Test> tests, 
			Long studentsTestsStatusId, Long previousstudentstestid, Set<String> accessibilityFlags) throws DuplicateTestSessionNameException{
		String testSessioName = prepareTestSessionName(testCollection, enrollment);
		logger.debug("Creating test session:" + testSessioName);
		
		TestSession testSession = null;
		
		Long otwId = testCollection.getOperationalTestWindowId();
		//List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
        //StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
        
		// If the test session does not exists, create a new test
		// session.
		testSession = new TestSession();
		testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
		testSession.setName(testSessioName);
		testSession.setStatus(unusedTestSessionId);
		testSession.setActiveFlag(true);
		testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
		testSession.setTestTypeId(testType.getId());
		testSession.setGradeCourseId(gradeCourse.getId());
		testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
		testSession.setTestPanelId(testCollection.getPanelId());
		if (testCollection.getStage() != null) {
			testSession.setStageId(testCollection.getStage().getId());
		}
		testSession.setTestCollectionId(testCollection.getTestCollectionId());
		testSession.setOperationalTestWindowId(otwId);
		testSession.setSubjectAreaId(enrollment.getSubjectAreaId());

		
		if (testCollection.getStage().getSortOrder() == 1
				&& StageEnum.STAGE1.getCode().equals(testCollection.getStage().getCode())
				&& CollectionUtils.isNotEmpty(tests)) {
			testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection,
					AARTCollectionUtil.getIds(tests), studentSessionRule, studentsTestsStatusId, previousstudentstestid);
		}else if (testCollection.getStage().getPredecessorId() != null
				&& testCollection.getStage().getPredecessorId() > 0) {
			StudentTestInfo predecessorTestInfo = getPredecessorTestInfo(enrollment, testCollection);
			// If previous test status id complete, assign new test based on theta values
			if (predecessorTestInfo != null && predecessorTestInfo.getTestStatus() != null
					&& predecessorTestInfo.getTestStatus().equalsIgnoreCase("complete")) {
				List<Test> testsBasedOnThetaAndAccessbleFlags = filteredTestsBasedOnThetaAndAccessbleFlags(
						predecessorTestInfo, enrollment, testCollection, accessibilityFlags);
				if (testsBasedOnThetaAndAccessbleFlags != null
						&& !testsBasedOnThetaAndAccessbleFlags.isEmpty()) {
					if(previousstudentstestid==null)
						previousstudentstestid = predecessorTestInfo.getId();
					testSession = studentsTestsService.addNewTestSession(enrollment, testSession,
							testCollection, AARTCollectionUtil.getIds(testsBasedOnThetaAndAccessbleFlags),
							studentSessionRule, studentsTestsStatusId, previousstudentstestid);

				}
			}
		}
		
		return testSession;
	}

	private String prepareTestSessionName(TestCollection testCollection, Enrollment enrollment) {
		return String.format("%d_%s_%s_%s_%s", contractingOrganization.getCurrentSchoolYear(),
				enrollment.getAttendanceSchool().getDisplayIdentifier(), gradeCourse.getName(), contentArea.getName(),
				testCollection.getStage().getName());

	}

	private List<Test> getTestCollectionTestsForAdaptiveStages(Set<String> accessibilityFlags, Long testCollectionId,
			List<Long> thetaTests) {
		List<Test> tests = null;
		if (CollectionUtils.isEmpty(accessibilityFlags)) {
			tests = testService.findPanelStageTestsByTestCollectionAndStatusAndAccFlags(testCollectionId,
					testStatusConfiguration.getPublishedTestStatusCategory().getId(),
					AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, null, null, thetaTests);
		} else {

			if (accessibilityFlags.contains("spanish")) {
				if (accessibilityFlags.contains("read_aloud")) {// spanish &&
																// spoken
					tests = testService.findPanelStageTestsByTestCollectionAndStatusAndAccFlags(testCollectionId,
							testStatusConfiguration.getPublishedTestStatusCategory().getId(),
							AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, true, accessibilityFlags, thetaTests);
					if (tests == null || tests.isEmpty()) {
						/*
						 * If no form with accessibility flag = Spanish And
						 * Spoken = True is found, the student will be randomly
						 * assigned to a form with Spoken = True if that form exists
						 */
						Set<String> newAccessibilityFlag = new HashSet<String>();
						newAccessibilityFlag.addAll(accessibilityFlags);
						newAccessibilityFlag.remove("spanish");

						if (newAccessibilityFlag.size() > 0) {
							tests = testService.findPanelStageTestsByTestCollectionAndStatusAndAccFlags(
									testCollectionId, testStatusConfiguration.getPublishedTestStatusCategory().getId(),
									AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, true, newAccessibilityFlag, thetaTests);
						}

					}

				} else {// has spanish but not spoken
					tests = testService.findPanelStageTestsByTestCollectionAndStatusAndAccFlags(testCollectionId,
							testStatusConfiguration.getPublishedTestStatusCategory().getId(),
							AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, true, accessibilityFlags, thetaTests);

					if ((tests == null || tests.isEmpty()) && accessibilityFlags.size() == 1) {
						// If no form with accessibility flag = Spanish is
						// found, the student will be randomly assigned to any
						// form in the test collection
						tests = testService.findPanelStageTestsByTestCollectionAndStatusAndAccFlags(testCollectionId,
								testStatusConfiguration.getPublishedTestStatusCategory().getId(),
								AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, null, null, thetaTests);
					}
				}
			} else {// does not have spanish
				tests = testService.findPanelStageTestsByTestCollectionAndStatusAndAccFlags(testCollectionId,
						testStatusConfiguration.getPublishedTestStatusCategory().getId(),
						AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, true, accessibilityFlags, thetaTests);
			}
		}

		logger.debug("<-- getTestCollectionTestsForAdaptiveStages");
		return tests;
	}

	@SuppressWarnings("unchecked")
	private void writeReason(Long studentId, String msg) {
		logger.debug(msg);

		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setStudentId(studentId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext()
				.get("jobMessages")).add(brReason);
	}

	private Set<String> getAccessibleFlags(Enrollment enrollment) {
		List<String> itemAttributeList = new ArrayList<String>();
		itemAttributeList.add("keywordTranslationDisplay");
		itemAttributeList.add("onscreenKeyboard"); // Single Switches
		itemAttributeList.add("Spoken");
		itemAttributeList.add("Braille");
		itemAttributeList.add("Signing");
		itemAttributeList.add("Magnification");
		List<StudentProfileItemAttributeDTO> pnpAttribuites = studentProfileService
				.getStudentProfileItemAttribute(enrollment.getStudentId(), itemAttributeList);
		itemAttributeList.clear();
		itemAttributeList.add("supportsTwoSwitch");// Two switch system
		itemAttributeList.add("paperAndPencil");// Alternate Form - Paper and Pencil
		itemAttributeList.add("largePrintBooklet");// Alternate Form - Large print booklet
		itemAttributeList.add("SpokenSourcePreference");// Voice Source
		itemAttributeList.add("UserSpokenPreference");// Spoken Preference
		itemAttributeList.add("Language");// Language
		itemAttributeList.add("SigningType");
		pnpAttribuites.addAll(
				studentProfileService.getStudentProfileItemContainer(enrollment.getStudentId(), itemAttributeList));
		Map<String, String> pnpAttributeMap = new HashMap<String, String>();
		for (StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites) {
			if (pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0)
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), "false");
			else
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), pnpAttribute.getSelectedValue());
		}
		return pnpConditionCheck(pnpAttributeMap);
	}

	private Set<String> pnpConditionCheck(Map<String, String> pnpAttributeMap) {
		logger.debug("--> pnpConditionCheck");
		Set<String> accessibilityFlag = new HashSet<String>();
		if (!pnpAttributeMap.isEmpty()) {

			boolean language = pnpAttributeMap.get("Language") != null
					&& pnpAttributeMap.get("Language").equalsIgnoreCase("spa");
			boolean keywordTranslationDisplay = pnpAttributeMap.get("keywordTranslationDisplay") != null
					&& pnpAttributeMap.get("keywordTranslationDisplay").equalsIgnoreCase("true");
			boolean spoken = pnpAttributeMap.get("Spoken") != null
					&& pnpAttributeMap.get("Spoken").equalsIgnoreCase("true");
			boolean onscreenKeyboard = pnpAttributeMap.get("onscreenKeyboard") != null
					&& pnpAttributeMap.get("onscreenKeyboard").equalsIgnoreCase("true");
			boolean supportsTwoSwitch = pnpAttributeMap.get("supportsTwoSwitch") != null
					&& pnpAttributeMap.get("supportsTwoSwitch").equalsIgnoreCase("true");
			boolean paperAndPencil = pnpAttributeMap.get("paperAndPencil") != null
					&& pnpAttributeMap.get("paperAndPencil").equalsIgnoreCase("true");
			boolean largePrintBooklet = pnpAttributeMap.get("largePrintBooklet") != null
					&& pnpAttributeMap.get("largePrintBooklet").equalsIgnoreCase("true");
			boolean braille = pnpAttributeMap.get("Braille") != null
					&& pnpAttributeMap.get("Braille").equalsIgnoreCase("true");
			boolean signing = "true".equalsIgnoreCase(pnpAttributeMap.get("Signing"));
			boolean signingIsASL = "asl".equalsIgnoreCase(pnpAttributeMap.get("SigningType"));
			boolean magnification = "true".equalsIgnoreCase(pnpAttributeMap.get("Magnification"));
			boolean userSpokenPreference = pnpAttributeMap.get("UserSpokenPreference") != null
					&& (pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("nonvisual")
							|| pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("textandgraphics")
							|| pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("textonly"));

			// Students With Spanish Accommodation
			// Students With Spanish and Spoken Accommodations
			if (language && keywordTranslationDisplay) {
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
						+ contentArea.getAbbreviatedName() + " 'spanish' PNP valid");
				accessibilityFlag.add("spanish");

				// Spoken = true
				if (spoken) {
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
							+ contentArea.getAbbreviatedName() + " 'Spoken' PNP valid");
					accessibilityFlag.add("read_aloud");
				}

			}

			// Students With Switch Accommodations
			if (onscreenKeyboard || supportsTwoSwitch) {
				accessibilityFlag.add("switch");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
						+ contentArea.getAbbreviatedName() + " PNP 'Switch' valid");

				// Students With Switch and Spoken Accommodations
				if (spoken) {
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
							+ contentArea.getAbbreviatedName() + " PNP 'Switch', PNP 'Spoken' valid");
				}
			}

			// Students With Paper Pencil Accommodations
			if (paperAndPencil) {
				accessibilityFlag.add("paper");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
						+ contentArea.getAbbreviatedName() + " PNP 'paperAndPencil' valid");

				// Students With Paper Pencil Accommodations and Spoken Accommodations
				if (spoken) {
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
							+ contentArea.getAbbreviatedName() + " PNP - paperAndPencil - PNP 'Spoken' valid");
				}
			}

			// Students With Large Print Accommodations
			if (largePrintBooklet || magnification) {
				accessibilityFlag.add("large_print");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
						+ contentArea.getAbbreviatedName() + " PNP 'largePrintBooklet' or 'magnification' valid");

				// Students With Paper Pencil Accommodations and Spoken Accommodations
				if (spoken) {
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
							+ contentArea.getAbbreviatedName()
							+ " PNP - largePrintBooklet or magnification - PNP 'Spoken' valid");
				}
			}

			// Students With Braille Accommodations
			if (braille) {
				accessibilityFlag.add("braille");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
						+ contentArea.getAbbreviatedName() + " PNP 'Braille' valid");

				// Students With Paper Pencil Accommodations and Spoken Accommodations
				if (spoken) {
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
							+ contentArea.getAbbreviatedName() + " PNP - Braille - PNP 'Spoken' valid");
				}
			}

			// Students With Only Spoken Accommodations
			if (spoken) {
				if (userSpokenPreference) {
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
							+ contentArea.getAbbreviatedName()
							+ " PNP 'Spoken', UserSpokenPreference - textandgraphics or nonvisual valid");
				}
			}

			if (signing && signingIsASL) {
				accessibilityFlag.add("signed");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - "
						+ contentArea.getAbbreviatedName() + " PNP 'Signing' valid");
			}
		}
		return accessibilityFlag;
	}
	
	private List<StudentsTests> getStageCompletedTest(Enrollment enrollment, TestCollection testCollection){
		List<StudentsTests> completedTests = studentsTestsService.getCompletedStudentsTests(assessment.getId(), testType.getId(), contentArea.getId(),
				gradeCourse.getId(), enrollment.getStudentId(), contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId());
		return completedTests;
	}
	
	private List<StudentsTests> getCompletedTestsInOtherSchool(Enrollment enrollment, TestCollection testCollection){
		List<StudentsTests> completedTests = studentsTestsService.getCompletedTestsInOtherSchool(assessment.getId(), testType.getId(), contentArea.getId(),
				gradeCourse.getId(), enrollment.getStudentId(), contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId(), enrollment.getId());
		return completedTests;
	}
	
	public GradeCourse getGradeCourse() {
		return gradeCourse;
	}

	public void setGradeCourse(GradeCourse gradeCourse) {
		this.gradeCourse = gradeCourse;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public List<TestCollection> getTestCollections() {
		return testCollections;
	}

	public void setTestCollections(List<TestCollection> testCollections) {
		this.testCollections = testCollections;
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public String getInterimFlag() {
		return interimFlag;
	}

	public void setInterimFlag(String interimFlag) {
		this.interimFlag = interimFlag;
	}

	private boolean isInterimFlag() {
		if (interimFlag != null && interimFlag.equalsIgnoreCase("true")) {
			return true;
		} else {
			return false;
		}
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public Long getUnusedTestSessionId() {
		return unusedTestSessionId;
	}

	public void setUnusedTestSessionId(Long unusedTestSessionId) {
		this.unusedTestSessionId = unusedTestSessionId;
	}

	public Long getCompletedTestSessionId() {
		return completedTestSessionId;
	}

	public void setCompletedTestSessionId(Long completedTestSessionId) {
		this.completedTestSessionId = completedTestSessionId;
	}

	public StudentSessionRule getStudentSessionRule() {
		return studentSessionRule;
	}

	public void setStudentSessionRule(StudentSessionRule studentSessionRule) {
		this.studentSessionRule = studentSessionRule;
	}

	

}
