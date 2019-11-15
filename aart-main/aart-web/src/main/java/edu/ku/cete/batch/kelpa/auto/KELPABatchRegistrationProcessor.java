/**
 * 
 */
package edu.ku.cete.batch.kelpa.auto;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

/**
 * @author ktaduru_sta
 *
 */
public class KELPABatchRegistrationProcessor implements ItemProcessor<Enrollment, KELPAContext> {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	protected TestSessionService testSessionService;

	@Autowired
	protected CategoryService categoryService;

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
	
	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;
	
	@Value("${testsession.status.closed}")
	private String TEST_SESSION_STATUS_COMPLETE;
	
	@Value("${testsession.status.type}")
	private String TEST_SESSION_STATUS_TYPE;

	@Value("${autoregistration.varianttypeid.code.eng}")
	protected String AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG;

	protected GradeCourse gradeCourse;
	protected TestType testType;
	protected List<TestCollection> testCollections;
	protected ContentArea contentArea;
	protected Long batchRegistrationId;
	private Category unusedSession;
	private Category completedTestSession;
	private StepExecution stepExecution;
	protected String enrollmentMethod;
	protected String assessmentProgramCode;
	private Organization contractingOrganization;
	private Assessment assessment;

	
	@Override
	public KELPAContext process(Enrollment enrollment) throws Exception {
		logger.debug("--> process");
		KELPAContext processCtx = new KELPAContext();
		processCtx.setEnrollment(enrollment);
		unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED,
				TEST_SESSION_STATUS_TYPE);
		
		completedTestSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_COMPLETE,
				TEST_SESSION_STATUS_TYPE);
		
		for (TestCollection testCollection : testCollections) {
			List<Test> tests = getTests(testCollection);
			if(CollectionUtils.isNotEmpty(tests)){
				boolean hasCompletedTestInPreviousSchool = false;
				//Check if the test session already exists or not.
		        List<TestSession> existingTestSessions = testSessionService.getAutoRegisteredSessions(assessment.getId(), testType.getId(),
		        		contentArea == null ? testCollection.getContentAreaId() : contentArea.getId(),
		        		gradeCourse == null ? testCollection.getGradeCourseId() : gradeCourse.getId(),
		        		enrollment.getAttendanceSchool().getId(),
		        		new Long(enrollment.getCurrentSchoolYear()), testCollection.getStage().getId(),
		        		SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId());
		        
				if (enrollment.isPreviousEnrollmentExists()){
					List<StudentsTests> completeTests = getCompletedStageTests(enrollment, testCollection);
					if(completeTests == null || completeTests.isEmpty()){
						hasCompletedTestInPreviousSchool = false;
					}else{
						hasCompletedTestInPreviousSchool = true;
						
						/**
						  DE15115
						  
						  Where there are no active existingtestsessions for the studentstests records with all completed testsessions that need to get transferred 
			  			  are not getting the transferredtessionsid, transferredenrollmentid, previousstudentstestsid columns updated. 
						  
						   1. create new testsessions (or get the inactivated test sessions --> Not doing this because the testsession name is also getting changed. Do not want to change the legacy code. So creating new)
						   2. update new studentstests records and to-be-transferring studentstests records with the transferredtessionsid, transferredenrollmentid, previousstudentstestsid 
						  
						 */
						/**
						 * CURRENT CODE: 
						if(existingTestSessions != null && !existingTestSessions.isEmpty()  && completeTests.get(0) != null 
								&& (completeTests.get(0).getTransferedTestSessionId() == null 
								|| completeTests.get(0).getTransferedTestSessionId() == 0
								|| existingTestSessions.get(0).getId() !=  completeTests.get(0).getTransferedTestSessionId())){
							studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(),existingTestSessions.get(0).getId(), enrollment.getId());
						}
						*/
						Long enrollmentId = enrollment.getId();
						if(completeTests.get(0) != null){
							
							if(existingTestSessions != null && !existingTestSessions.isEmpty()){ 
								if(
							     (completeTests.get(0).getTransferedTestSessionId() == null || completeTests.get(0).getTransferedTestSessionId() == 0
								 	|| (existingTestSessions.get(0).getId() != completeTests.get(0).getTransferedTestSessionId() 
								 		&& !existingTestSessions.get(0).getId().equals(completeTests.get(0).getTransferedTestSessionId()))
								 ) &&
								 (enrollmentId!= completeTests.get(0).getEnrollmentId() && !enrollmentId.equals(completeTests.get(0).getEnrollmentId()))
								 ){
									
									if(existingTestSessions.get(0).getId()!=completeTests.get(0).getTestSessionId() && !existingTestSessions.get(0).getId().equals(completeTests.get(0).getTestSessionId())){
											studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(),existingTestSessions.get(0).getId(), enrollment.getId());
									}
									else
										studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(),null, enrollment.getId());
								}	
							}
							else{
								
								//For the schools which do not have active testsessions, creating new ones to update the studentstests 
								//These new testsessions will be in unused state 
								TestSession testSession = null;
								logger.debug("Adding enrollment - " + enrollment.getId() + " with completed tests to new Test Session");
								testSession = createNewTestSession(testCollection, enrollment, unusedSession.getId(), tests, completedTestSession.getId(), null);
								
								if (testSession != null && testSession.getId() != null) {
					    			logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - "
					    					+ testSession.getId());
					    			processCtx.getTestSessions().add(testSession);
					    		
					        	    //If not update existing records for transferredtessionsid, transferredenrollmentid, previousstudentstestsid
						        	logger.debug("**********************st.id: "+completeTests.get(0).getId()+" ***** transTSID: "+testSession.getId()+" ***** transEID: "+enrollment.getId());
						        	if(completeTests.get(0).getEnrollmentId()!=null && enrollment.getId() != completeTests.get(0).getEnrollmentId()){
						        		studentsTestsService.updateTransferedTestSessionId(completeTests.get(0).getId(),testSession.getId(), enrollment.getId());
						        		studentsTestsService.inactivateStudentsTestsTestBytestSessionId(testSession.getId());
						        	}
								}
					        	//If studentstests records not available for the new enrollment, create new sessions
					        	//Get the studentstests records from the previous enrollment -->Already available in completedTests object: previousStudentsTestId = completeTests.get(0).getId()
					       
							}
						}
						/** DE15115 End of Code change **/
					
					}
				}
				
				if (!hasCompletedTestInPreviousSchool) {
					
			        TestSession testSession = null;
			        if(CollectionUtils.isNotEmpty(existingTestSessions)) {
			        	testSession = existingTestSessions.get(0);
			        	logger.debug("Found session "+testSession.getId());
			        	
			        	studentsTestsService.addStudentExistingSession(enrollment, testSession, testCollection, tests, null);
			        	processCtx.getTestSessions().add(testSession);
			        }else{
			        	logger.debug("Adding enrollment - " + enrollment.getId() + " to new Test Session");
			        	testSession = createNewTestSession(testCollection, enrollment, unusedSession.getId(), tests, null, null);
			        	if (testSession != null && testSession.getId() != null) {
			    			logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - "
			    					+ testSession.getId());
			    			processCtx.getTestSessions().add(testSession);
			    		}
			        }
				}
				
				
			}else{
				String logMsg = String.format(
						"No tests found with this criteria - grade: %s(%d), testtype: %s(%d), contentarea: %s(%d), testcollection: %d.",
						gradeCourse.getAbbreviatedName(), gradeCourse.getId(), testType.getTestTypeCode(),
						testType.getId(), contentArea.getAbbreviatedName(), contentArea.getId(),
						testCollection.getId());
				logger.debug(logMsg);
				writeReason(enrollment.getStudentId(), logMsg);
			}
			
		}
		
		return processCtx;
	}

	private TestSession createNewTestSession(TestCollection testCollection, Enrollment enrollment, Long testSessionStatus, List<Test> tests, Long studentsTestsStatusId, Long previousstudentstestid) throws DuplicateTestSessionNameException{
		
		TestSession testSession = null;
		String testSessioName = prepareTestSessionName(testCollection, enrollment);
		logger.debug("Looking for test session:"+testSessioName);
		
		Long otwId = testCollection.getOperationalTestWindowId();
		List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
        
		// If the test session does not exists, create a new test session.
		testSession = new TestSession();
		testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
		testSession.setName(testSessioName);
		testSession.setStatus(testSessionStatus);
		testSession.setActiveFlag(true);
		testSession.setAttendanceSchoolId(enrollment.getAttendanceSchoolId());
		testSession.setTestTypeId(testType.getId());
		testSession.setGradeCourseId(gradeCourse.getId());
		testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());					
		if (testCollection.getStage() != null) {
			testSession.setStageId(testCollection.getStage().getId());
		}
		testSession.setTestCollectionId(testCollection.getTestCollectionId());
		testSession.setOperationalTestWindowId(otwId);
		testSession.setSubjectAreaId(enrollment.getSubjectAreaId());
		
		testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection,
				AARTCollectionUtil.getIds(tests), studentSessionRule, studentsTestsStatusId, previousstudentstestid);
		
		return testSession;
	}
	
	private String prepareTestSessionName(TestCollection testCollection, Enrollment enrollment) {
		return String.format("%d_%s_%s_%s_%s", contractingOrganization.getCurrentSchoolYear(),
				enrollment.getAttendanceSchool().getDisplayIdentifier(), gradeCourse.getName(), contentArea.getName(),
				testCollection.getStage().getName());

	}
	
	private List<Test> getTests(TestCollection testCollection) {
		logger.debug("--> getTests" );
		
		List<Test> tests = null;
 		
 		tests = testService.findQCTestsByTestCollectionAndStatusAndAccFlags(testCollection.getId(), 
 					testStatusConfiguration.getPublishedTestStatusCategory().getId(), 
 					AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, null, null);
 		
		logger.debug("<-- getTests" );
		return tests;
	}
	
	private List<StudentsTests> getCompletedStageTests(Enrollment enrollment, TestCollection testCollection){		
		Long caId = contentArea == null ? testCollection.getContentArea().getId() : contentArea.getId();
		Long gcId = gradeCourse == null ? testCollection.getGradeCourse().getId() : gradeCourse.getId();
		
		List<StudentsTests> completedTests = studentsTestsService.getCompletedStudentsTestsForKELPAStudent(assessment.getId(), testType.getId(), caId, 
				gcId, enrollment.getStudentId(), new Long(enrollment.getCurrentSchoolYear()), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId());
		return completedTests;
//		if(completedTests != null && !completedTests.isEmpty()){			
//				return true;			
//		}else{
//			return false;	
//		}
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

	public Category getUnusedSession() {
		return unusedSession;
	}

	public void setUnusedSession(Category unusedSession) {
		this.unusedSession = unusedSession;
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
	
}
