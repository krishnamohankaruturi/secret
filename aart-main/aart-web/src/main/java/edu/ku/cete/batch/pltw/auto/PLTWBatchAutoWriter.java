/**
 * 
 */
package edu.ku.cete.batch.pltw.auto;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.WriterContext;
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.domain.DashboardMessage;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.service.DashboardMessageService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * @author Kiran Reddy Taduru
 *
 * Aug 28, 2018 9:51:06 AM
 */
public class PLTWBatchAutoWriter implements ItemWriter<WriterContext> {
	private final static Log logger = LogFactory.getLog(PLTWBatchAutoWriter.class);
	
	private Long batchRegistrationId;
	protected String assessmentProgramCode;
	protected Long assessmentProgramId;
	private Organization contractingOrganization;
	protected GradeCourse gradeBand;
	protected ContentArea contentArea;
	protected String enrollmentMethod;
	private StepExecution stepExecution;
	private StudentSessionRule studentSessionRule;
	private Long unusedTestSessionId;
	private Stage stage;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private StudentsTestsService studentsTestsService;		
	
	@Autowired
	private StudentsTestsStatusConfiguration studentsTestsStatus;
	
	@Autowired
	private DashboardMessageService dashboardMessageService;
	
	@Override
	public void write(List<? extends WriterContext> enrollments) throws Exception {

		if(!enrollments.isEmpty()) {
			Enrollment enrollment = enrollments.get(0).getEnrollment();
			Map<TestCollection, List<Test>> testCollectionTests =  enrollments.get(0).getTestCollectionTests();
			for(TestCollection testCollection: testCollectionTests.keySet()) {
				
				boolean stageCompletedTestInPreviousSchool = false;
				// verify if student has moved from different school, if so look for the studentstest is completed or not
				if (enrollment.isPreviousEnrollmentExists() 
					&& studentHasCompletedStageInOtherSchool(enrollment, testCollection)){
						stageCompletedTestInPreviousSchool = true;
				}
				
				Long studentsTestsStatusId = null;
				
				//Check prior stage students test status to determine next stage status, if prior stage is complete assign next stage in pending status otherwise it will use unused status
				if(testCollection.getStage() != null && testCollection.getStage().getPredecessorId() != null && testCollection.getStage().getPredecessorId() > 0) {
					String predecessorTestStatus = testSessionService.getPredecessorStageStudentsTestStatus(contentArea.getId(), gradeBand.getGradeBandId(), 
							contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getPredecessorId(), enrollment.getStudentId(), contractingOrganization.getOperationalWindowId());
					if(!"complete".equalsIgnoreCase(predecessorTestStatus)) {
						studentsTestsStatusId = studentsTestsStatus.getPendingStudentsTestsStatusCategory().getId();
					}
				}
				
				//Student did  not complete this stage in other school
				if (!stageCompletedTestInPreviousSchool) {
					//construct testsession name
					String testSessionName = prepareTestSessionName(testCollection, enrollment);					
					TestSession testSession = getExistingTestSession(enrollment, testCollection, testSessionName);
					
					if(testSession != null && testSession.getId() > 0L) {//Existing test session found
						logger.debug("Adding enrollment - " + enrollment.getId() + " to existing testsession - " + testSession.getId());
						
						//add student to existing session
						studentsTestsService.addStudentExistingSession(enrollment, testSession, testCollection, testCollectionTests.get(testCollection),
								studentsTestsStatusId);
					}else {
						testSession = createTestSession(enrollment, testCollection, testCollectionTests.get(testCollection), testSessionName, studentsTestsStatusId);
						
						logger.debug("Enrolling student " + enrollment.getStudentId() + " to new test session " + testSession.getId() + " (" + testSession.getName() + ")");
					}
					
					enrollments.get(0).getTestSessions().add(testSession);
					
					//Inactivate existing dashboard messages for this student
					int rowsUpdated = inactivateDashboardMessages(enrollment);
					logger.debug("StudentId: " + enrollment.getStudentId() + " - ContentArea: " + contentArea.getAbbreviatedName() + "(" + contentArea.getId() + ")"+" - Rows updated: "+ rowsUpdated);
				} else {
					//Update transferred testsession id on studentstests record for transferred students when student completed test in prior school
					updateTransferredTestSessionId(enrollment, testCollection, testCollectionTests.get(testCollection), studentsTestsStatusId);
				}
				
			}
		}
		
	}

	
	/**
	 * Checking for completed tests in other school for a stage
	 * @param enrollment
	 * @param testCollection
	 * @return
	 */
	private boolean studentHasCompletedStageInOtherSchool(Enrollment enrollment, TestCollection testCollection){
		List<StudentsTests> completedTests = studentsTestsService.findCompletedTestsInOtherSchoolByGradeBand(contentArea.getId(),
				gradeBand.getGradeBandId(), enrollment.getStudentId(), contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId(), enrollment.getId());
		if(CollectionUtils.isNotEmpty(completedTests)){
			return true;
		}
		return false;
	}
	
	private String prepareTestSessionName(TestCollection testCollection, Enrollment enrollment) {		
		
		return String.format("%d_%s_%s_%s_%s", enrollment.getCurrentSchoolYear(), enrollment.getAttendanceSchool().getDisplayIdentifier(),
				gradeBand.getGradeBandAbbrName(), enrollment.getRoster().getCourseSectionName(), testCollection.getStage().getName());
			
	}
	
	private TestSession createTestSession(Enrollment enrollment, TestCollection testCollection, List<Test> tests, String testSessionName, Long studentsTestsStatusId) throws DuplicateTestSessionNameException {
		TestSession testSession = new TestSession();
		testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
		if(StringUtils.isBlank(testSessionName)) {
			testSession.setName(prepareTestSessionName(testCollection, enrollment));
		}else {
			testSession.setName(testSessionName);
		}
		
		testSession.setStatus(unusedTestSessionId);
		testSession.setActiveFlag(true);
		testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
		testSession.setGradeBandId(gradeBand.getGradeBandId());
		testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
		if(testCollection.getStage() != null) {
			testSession.setStageId(testCollection.getStage().getId());
		}
		testSession.setTestCollectionId(testCollection.getId());
		testSession.setOperationalTestWindowId(contractingOrganization.getOperationalWindowId());
		testSession.setSubjectAreaId(contentArea.getId());
		testSession.setRosterId((enrollment.getRoster() != null && enrollment.getRoster().getId() != null) ? enrollment.getRoster().getId() : null);
		testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
				AARTCollectionUtil.getIds(tests), studentSessionRule, studentsTestsStatusId);
		return testSession;
	}
	
	private TestSession getExistingTestSession(Enrollment enrollment, TestCollection testCollection, String testSessionName){
		TestSession testSession = null;
		if(StringUtils.isBlank(testSessionName)) {
			testSessionName = prepareTestSessionName(testCollection, enrollment);
		}
		
		//Get existing sessions in the school for this stage by using passed in criteria
		List<TestSession> existingTestSessions = testSessionService.findTestSessionByGradeBandContentArea(contentArea.getId(), gradeBand.getGradeBandId(), 
				enrollment.getAttendanceSchool().getId(), contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId(), enrollment.getRoster().getId(), testSessionName);
	
		if(CollectionUtils.isNotEmpty(existingTestSessions)) {
			testSession = existingTestSessions.get(0);
			logger.debug("Found session "+testSession.getId());
		}			
		
		return testSession;
	}
	
	private void updateTransferredTestSessionId(Enrollment enrollment, TestCollection testCollection, List<Test> tests, Long studentsTestsStatusId) throws DuplicateTestSessionNameException {
		
		List<StudentsTests> completedStudentsTests = studentsTestsService.findCompletedTestsInOtherSchoolByGradeBand(contentArea.getId(),
				gradeBand.getGradeBandId(), enrollment.getStudentId(), contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId(), enrollment.getId());
		
		if(CollectionUtils.isNotEmpty(completedStudentsTests)){			
			Long enrollmentId = enrollment.getId();
			
			for(StudentsTests completedTest : completedStudentsTests){
				if(completedTest != null){
					//This is new schools' test session
					TestSession existingTestSession = getExistingTestSession(enrollment, testCollection, null);
					
					if(existingTestSession != null){ 
						if((completedTest.getTransferedTestSessionId() == null || completedTest.getTransferedTestSessionId() == 0
						 	|| (existingTestSession.getId() != completedTest.getTransferedTestSessionId() 
						 		&& !existingTestSession.getId().equals(completedTest.getTransferedTestSessionId()))
						 ) &&
						 (enrollmentId != completedTest.getEnrollmentId() && !enrollmentId.equals(completedTest.getEnrollmentId()))){
							
							if(existingTestSession.getId() != completedTest.getTestSessionId() && !existingTestSession.getId().equals(completedTest.getTestSessionId())){
								studentsTestsService.updateTransferedTestSessionId(completedTest.getId(),existingTestSession.getId(), enrollment.getId());
								
								String errorMsg = "Assigned Student Id: " + enrollment.getStudentId() + " to existing Test Session Id: " + existingTestSession.getId();
								logger.debug(errorMsg);
							}
						}else{
							studentsTestsService.updateTransferedTestSessionId(completedTest.getId(),null, enrollment.getId());
						}
							
					} else{
						
						//For the schools which do not have active testsessions, creating new ones to update the studentstests 
						//These new testsessions will be in unused state 
						TestSession testSession = null;
						logger.debug("Adding enrollment - " + enrollment.getId() + " with completed tests to new Test Session");						
						
						testSession = createTestSession(enrollment, testCollection, tests, null, studentsTestsStatusId);
						
						if (testSession != null && testSession.getId() != null) {
							logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - "	+ testSession.getId());
						
							//If not update existing records for transferredtestsessionid, transferredenrollmentid
							logger.debug("**********************studentsTests.id: "+completedTest.getId()+" ***** transferredTestSessionID: "+testSession.getId()+" ***** transferredEnrollmentID: "+enrollment.getId());
							if(completedTest.getEnrollmentId()!=null && enrollment.getId() != completedTest.getEnrollmentId()){
								studentsTestsService.updateTransferedTestSessionId(completedTest.getId(),testSession.getId(), enrollment.getId());
								studentsTestsService.inactivateStudentsTestsTestBytestSessionId(testSession.getId());
							}
						}
					}
				}
			}
			
			//Inactivate existing dashboard messages for this student
			int rowsUpdated = inactivateDashboardMessages(enrollment);
			logger.debug("StudentId: " + enrollment.getStudentId() + " - ContentArea: " + contentArea.getAbbreviatedName() + "(" + contentArea.getId() + ")"+" - Rows updated: "+ rowsUpdated);
					
		}
	}

	
	/**
	 * Inactivates existing dashboard messages if present upon successfull test assignment process
	 * @param enrollment
	 * @return
	 */
	private int  inactivateDashboardMessages(Enrollment enrollment) {
		DashboardMessage dbMsg = new DashboardMessage();
		dbMsg.setBatchRegistrationId(batchRegistrationId);
		dbMsg.setRecordType(CommonConstants.DASHBOARD_MESSAGE_RECORD_TYPE);
		dbMsg.setStudentId(enrollment.getStudentId());
		dbMsg.setEnrollmentId(enrollment.getId());
		dbMsg.setAssessmentProgramId(assessmentProgramId);
		dbMsg.setContentAreaId(contentArea.getId());
		dbMsg.setGradeBandId(gradeBand.getGradeBandId());
		dbMsg.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
		dbMsg.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
		dbMsg.setRosterId(enrollment.getRoster().getId());
		dbMsg.setClassroomId(enrollment.getRoster().getClassroomId());
		dbMsg.setAuditColumnPropertiesForUpdate();
		return dashboardMessageService.inactivateDashboardMessages(dbMsg);
	}
	
	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public GradeCourse getGradeBand() {
		return gradeBand;
	}

	public void setGradeBand(GradeCourse gradeBand) {
		this.gradeBand = gradeBand;
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}	

	public Long getUnusedTestSessionId() {
		return unusedTestSessionId;
	}

	public void setUnusedTestSessionId(Long unusedTestSessionId) {
		this.unusedTestSessionId = unusedTestSessionId;
	}	

	public StudentSessionRule getStudentSessionRule() {
		return studentSessionRule;
	}

	public void setStudentSessionRule(StudentSessionRule studentSessionRule) {
		this.studentSessionRule = studentSessionRule;
	}
	
	public Stage getStageCode() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
