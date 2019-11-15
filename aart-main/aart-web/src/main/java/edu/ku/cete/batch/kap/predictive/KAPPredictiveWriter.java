package edu.ku.cete.batch.kap.predictive;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.support.WriterContext;
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
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

public class KAPPredictiveWriter implements ItemWriter<WriterContext> {
	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private StudentSessionRuleConverter studentSessionRuleConverter;
	
	@Autowired
	private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
	
	private Long batchRegistrationId;
	private StepExecution stepExecution;
	
	protected GradeCourse gradeCourse;
	protected TestType testType;
	protected ContentArea contentArea;
	protected String enrollmentMethod;
	protected String assessmentProgramCode;
	private Organization contractingOrganization;
	private Assessment assessment;
	
	private Category unusedSession;
	
	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;
	
	@Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;
	
	private final String PREDICTIVE_SESSION_PREFIX = "Predictive_";	
	
	@BeforeStep
	void initializeValues(StepExecution stepExecution) {
		unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
	}
	
	@Override
	public void write(List<? extends WriterContext> contexts) throws Exception {
		if (CollectionUtils.isNotEmpty(contexts)) {
			WriterContext context = contexts.get(0);
			Enrollment enrollment = context.getEnrollment();
			Map<TestCollection, List<Test>> testCollectionsTests = context.getTestCollectionTests();
			
			Long studentId = enrollment.getStudentId();
			Long operationalTestWindowId = contractingOrganization.getOperationalWindowId();
			Long currentSchoolYear = contractingOrganization.getCurrentSchoolYear();			
			
			for (TestCollection testCollection : testCollectionsTests.keySet()) {
				Boolean studentCompletedTestInPreviousSchool = false;
				
				//if student has prior enrollment
				if (enrollment.isPreviousEnrollmentExists()){
					//check if student completed test in previous school
					studentCompletedTestInPreviousSchool = studentHasCompletedPredictiveTestsInOtherSchool(enrollment, testCollection);
					
					if(studentCompletedTestInPreviousSchool){
						//update studentstest details
						updateTransferredTestSessionId(enrollment, testCollection, testCollectionsTests);
					}
				}
				
				if(!studentCompletedTestInPreviousSchool){
					List<TestSession> existingTestSessions = testSessionService.getAutoRegisteredSessions(
							assessment.getId(), testType.getId(), contentArea.getId(), gradeCourse.getId(),
							enrollment.getAttendanceSchool().getId(), currentSchoolYear, null, SourceTypeEnum.BATCHAUTO, operationalTestWindowId, PREDICTIVE_SESSION_PREFIX);
					if (CollectionUtils.isNotEmpty(existingTestSessions)) {
						for (TestSession existingTestSession : existingTestSessions) {
							List<StudentsTests> existingStudentsTests = studentsTestsService.findByTestSessionAndStudent(existingTestSession.getId(), studentId);
							if (CollectionUtils.isEmpty(existingStudentsTests)) {
								logger.debug("Enrolling student " + studentId + " to test session " + existingTestSession.getId());
								studentsTestsService.addStudentExistingSession(enrollment, existingTestSession, testCollection, context.getTestCollectionTests().get(testCollection), null);
							} else {
								logger.debug("Found active predictive test for student " + studentId + " in test session " + existingTestSession.getId() + " (" + existingTestSession.getName() + ")");
							}
						}
					} else {
						TestSession testSession = createPredictiveTestSession(enrollment, testCollectionsTests,
								operationalTestWindowId, currentSchoolYear, testCollection);
						
						logger.debug("Enrolling student " + studentId + " to new test session " + testSession.getId() + " (" + testSession.getName() + ")");
					}
				}
				
				
			}
		}
	}

	private TestSession createPredictiveTestSession(Enrollment enrollment,
			Map<TestCollection, List<Test>> testCollectionsTests, Long operationalTestWindowId, Long currentSchoolYear,
			TestCollection testCollection) throws DuplicateTestSessionNameException {
		List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(operationalTestWindowId);
        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
		
		TestSession testSession = new TestSession();
		testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
		testSession.setName(prepareTestSessionName(testCollection, enrollment));
		testSession.setStatus(unusedSession.getId());
		testSession.setActiveFlag(true);
		testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
		testSession.setTestTypeId(testType.getId());
		testSession.setGradeCourseId(gradeCourse.getId());
		testSession.setSchoolYear(currentSchoolYear);
		if(testCollection.getStage() != null) {
			testSession.setStageId(testCollection.getStage().getId());
		}
		testSession.setTestCollectionId(testCollection.getId());
		testSession.setOperationalTestWindowId(operationalTestWindowId);
		testSession.setSubjectAreaId(enrollment.getSubjectAreaId());
		
		testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
				AARTCollectionUtil.getIds(testCollectionsTests.get(testCollection)), studentSessionRule, null);
		return testSession;
	}
	
	private String prepareTestSessionName(TestCollection testCollection, Enrollment enrollment) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
		Date effectiveDate = contractingOrganization.getOperationalWindowEffectiveDate();
		String month = effectiveDate == null ? null : sdf.format(effectiveDate);
		return PREDICTIVE_SESSION_PREFIX +
				month + (month == null ? "" : "_") +
				enrollment.getAttendanceSchool().getDisplayIdentifier() + "_" +
				gradeCourse.getName() + "_" +
				contentArea.getName();
	}
	
	private boolean studentHasCompletedPredictiveTestsInOtherSchool(Enrollment enrollment, TestCollection testCollection){
		List<StudentsTests> completedTests = studentsTestsService.findCompletedPredictiveTestsInOtherSchool(assessment.getId(), testType.getId(), contentArea.getId(),
				gradeCourse.getId(), enrollment.getStudentId(), contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId(), enrollment.getId());
		if(CollectionUtils.isNotEmpty(completedTests)){
			return true;
		}
		return false;
	}
	
	private void updateTransferredTestSessionId(Enrollment enrollment, TestCollection testCollection, Map<TestCollection, List<Test>> testCollectionsTests) throws DuplicateTestSessionNameException {
		
		List<StudentsTests> completedStudentsTests = studentsTestsService.findCompletedPredictiveStudentsTests(assessment.getId(), testType.getId(), contentArea.getId(),
				gradeCourse.getId(), enrollment.getStudentId(), contractingOrganization.getCurrentSchoolYear(), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId());
		
		if(CollectionUtils.isNotEmpty(completedStudentsTests)){			
			Long enrollmentId = enrollment.getId();
			
			for(StudentsTests completedTest : completedStudentsTests){
				if(completedTest != null){
					//This is new schools' test session
					TestSession existingTestSession = getExistingTestSession(enrollment, testCollection);
					
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
						
						testSession = createPredictiveTestSession(enrollment, testCollectionsTests,
								testCollection.getOperationalTestWindowId(), contractingOrganization.getCurrentSchoolYear(), testCollection);
						
						if (testSession != null && testSession.getId() != null) {
			    			logger.debug("Added enrollment - " + enrollment.getId() + " to new Test Session with id - "	+ testSession.getId());
			    		
			        		//If not update existing records for transferredtessionsid, transferredenrollmentid
				        	logger.debug("**********************st.id: "+completedTest.getId()+" ***** transTSID: "+testSession.getId()+" ***** transEID: "+enrollment.getId());
				        	if(completedTest.getEnrollmentId()!=null && enrollment.getId() != completedTest.getEnrollmentId()){
				        		studentsTestsService.updateTransferedTestSessionId(completedTest.getId(),testSession.getId(), enrollment.getId());
				        		studentsTestsService.inactivateStudentsTestsTestBytestSessionId(testSession.getId());
				        	}
						}
			        }
				}
			}
			
			
			
		}
	}
	
	private TestSession getExistingTestSession(Enrollment enrollment, TestCollection testCollection){
		TestSession testSession = null;
		// Check if the test session already exists or not.
		List<TestSession> existingTestSessions = testSessionService.getAutoRegisteredSessions(
				assessment.getId(), testType.getId(), contentArea.getId(), gradeCourse.getId(),
				enrollment.getAttendanceSchool().getId(), contractingOrganization.getCurrentSchoolYear(), null, SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId(), PREDICTIVE_SESSION_PREFIX);
		if (CollectionUtils.isNotEmpty(existingTestSessions)) {
			testSession = existingTestSessions.get(0);
			logger.debug("Found session " + testSession.getId());
		}
		return testSession;
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
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
	
	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}
}
