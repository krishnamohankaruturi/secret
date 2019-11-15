package edu.ku.cete.batch;

import java.util.Arrays;
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
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
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
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StageEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

public class BatchRegistrationWriter implements ItemWriter<WriterContext> {
	
	private final static Log logger = LogFactory.getLog(BatchRegistrationWriter.class);
	
    @Autowired
    private StudentsTestsStatusConfiguration studentsTestsStatus;
    
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private TestSessionService testSessionService;

	@Autowired
    private CategoryService categoryService;
	
	@Autowired
	private StudentSessionRuleConverter studentSessionRuleConverter;
	
	@Autowired
	private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Autowired
	private EnrollmentService enrollmentService;
    
	@Value("${autoregistration.varianttypeid.code.eng}")
    private String AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG;
	
	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;
	
	@Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;

	private GradeCourse gradeCourse;
	private ContentArea contentArea;
	private TestType testType;
	private Organization contractingOrganization;
	private Long batchRegistrationId;
	private String assessmentProgramCode;
	private Assessment assessment;
	
	private StepExecution stepExecution;

	private Category unusedSession;
	
	private boolean assessmentProgramUsesStage;
	
	private String enrollmentMethod;
	
	// this is for CPASS...works differently than other processes
	private List<Organization> contractingOrganizations;
	
	@BeforeStep
	public void initializeValues(StepExecution stepExecution) {
		unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
		assessmentProgramUsesStage = Arrays.asList(new String[]{"KAP", "AMP", "CPASS"}).contains(assessmentProgramCode);
	}
	
	@Override
	public void write(List<? extends WriterContext> enrollments) throws Exception {
		logger.debug("--> write  - batchRegistrationId : " + batchRegistrationId);
		if(!enrollments.isEmpty()) {
			Enrollment enrollment = enrollments.get(0).getEnrollment();
			Map<TestCollection, List<Test>> testCollectionTests =  enrollments.get(0).getTestCollectionTests();
			
			for(TestCollection testCollection: testCollectionTests.keySet()) {
				
				boolean stageCompletedTestInPreviousSchool = false;
				// verify if student has moved from different school, if so look for the studentstest has completed or not
				if (enrollment.isPreviousEnrollmentExists() 
					&& isStageCompleted(enrollment, testCollection)){
						stageCompletedTestInPreviousSchool = true;
				}
				//if student already completed test for the current stage, do nothing else regular process
				if (!stageCompletedTestInPreviousSchool) {
				
					String testSessioName = prepareTestSessionName(testCollection, enrollment);
					logger.debug("Looking for test session:"+testSessioName);
					//Check if the test session already exists or not.
			        List<TestSession> existingTestSessions = testSessionService.getAutoRegisteredSessions(assessment.getId(), testType.getId(),
			        		contentArea == null ? testCollection.getContentAreaId() : contentArea.getId(),
			        		gradeCourse == null ? testCollection.getGradeCourseId() : gradeCourse.getId(),
			        		enrollment.getAttendanceSchool().getId(),
			        		new Long(enrollment.getCurrentSchoolYear()), (assessmentProgramUsesStage) ? testCollection.getStage().getId() : null,
			        		SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId());
			        TestSession testSession = null;
			        if(CollectionUtils.isNotEmpty(existingTestSessions)) {
			        	testSession = existingTestSessions.get(0);
			        	logger.debug("Found session "+testSession.getId());
			        }
			         
			        boolean enroll = true;
		 			if(testSession != null && testSession.getId() > 0L) {
						logger.debug("Adding enrollment - " + enrollment.getId() + " to existing testsession - " + testSession.getId());
						TestType predecessorTestType = testType;
						Long studentsTestsStatusId = null;
						if(assessmentProgramUsesStage && testCollection.getStage().getPredecessorId() != null && testCollection.getStage().getPredecessorId() > 0) {
							boolean checkForPredecessorStatus = true;
							if(("KAP".equalsIgnoreCase(assessmentProgramCode) && "SS".equalsIgnoreCase(contentArea.getAbbreviatedName())) ||
									"CPASS".equalsIgnoreCase(assessmentProgramCode)){
								checkForPredecessorStatus = false;
							}
							
							if (checkForPredecessorStatus && enroll) {
								String predecessorTestStatus = testSessionService.getPredecessorStageStatus(assessment.getId(), predecessorTestType.getId(),
										contentArea == null ? testCollection.getContentAreaId() : contentArea.getId(),
										gradeCourse == null ? testCollection.getGradeCourseId() : gradeCourse.getId(),
										enrollment.getAttendanceSchool().getId(), new Long(enrollment.getCurrentSchoolYear()),
						        		assessmentProgramUsesStage ? testCollection.getStage().getPredecessorId() : null, enrollment.getStudentId(), testCollection.getOperationalTestWindowId());
					    		if(predecessorTestStatus == null || !predecessorTestStatus.equalsIgnoreCase("complete")) {
				    				studentsTestsStatusId = studentsTestsStatus.getPendingStudentsTestsStatusCategory().getId();
					    		}
							}
						}
						
						if (enroll) {
							if (needsToEnrollToAllTestsInCollection(testCollection)) {
								studentsTestsService.addStudentExistingSessionAllTests(enrollment, testSession, testCollection, testCollectionTests.get(testCollection),
										studentsTestsStatusId);
							} else {
								studentsTestsService.addStudentExistingSession(enrollment, testSession, testCollection, testCollectionTests.get(testCollection),
										studentsTestsStatusId);
							}
						}
			        } else {
			        	logger.debug("Adding enrollment - " + enrollment.getId() +" to new Test Session");
			        	Long otwId = testCollection.getOperationalTestWindowId();
		        		List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
		        		StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
				        //If the test session does not exists, create a new test session.
				    	testSession = new TestSession();
				    	testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
				    	testSession.setName(testSessioName);
				    	testSession.setStatus(unusedSession.getId());
				    	testSession.setActiveFlag(true);
				    	testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
				    	testSession.setTestTypeId(testType.getId());
				    	testSession.setGradeCourseId(gradeCourse == null ? testCollection.getGradeCourseId() : gradeCourse.getId());
				    	testSession.setSchoolYear(new Long(enrollment.getCurrentSchoolYear()));
				    	if(testCollection.getStage() != null) {
				    		testSession.setStageId(testCollection.getStage().getId());
				    	}
				    	testSession.setTestCollectionId(testCollection.getTestCollectionId());
				    	testSession.setOperationalTestWindowId(otwId);
				    	testSession.setSubjectAreaId(enrollment.getSubjectAreaId());
				    	
				    	Long studentsTestsStatusId = null;
				    	if((assessmentProgramUsesStage) && testCollection.getStage().getPredecessorId() != null && testCollection.getStage().getPredecessorId() > 0) {
				    		TestType predecessorTestType = testType;
				    		// CPASS tests with the previous stage won't get assigned in the test session with the same stageId or test type,
				    		// so we have to cheat the logic a bit
				    		boolean checkForPredecessorStatus = true;
				    		
				    		if(("KAP".equalsIgnoreCase(assessmentProgramCode) && "SS".equalsIgnoreCase(contentArea.getAbbreviatedName())) ||
									"CPASS".equalsIgnoreCase(assessmentProgramCode)){
								checkForPredecessorStatus = false;
							}
				    		
				    		//DE9002 fix
							if (checkForPredecessorStatus && enroll) {
					    		String predecessorTestStatus = testSessionService.getPredecessorStageStatus(assessment.getId(), predecessorTestType.getId(),
						        		contentArea == null ? testCollection.getContentAreaId() : contentArea.getId(),
						        		gradeCourse == null ? testCollection.getGradeCourseId() : gradeCourse.getId(), enrollment.getAttendanceSchool().getId(),
						        		new Long(enrollment.getCurrentSchoolYear()),
						        		assessmentProgramUsesStage ? testCollection.getStage().getPredecessorId() : null, enrollment.getStudentId(), testCollection.getOperationalTestWindowId());
					    		if(predecessorTestStatus == null || !predecessorTestStatus.equalsIgnoreCase("complete")) {
					    			studentsTestsStatusId = studentsTestsStatus.getPendingStudentsTestsStatusCategory().getId();
					    		}
							}
				    	}
				    	
				    	if (enroll) {
					    	if (needsToEnrollToAllTestsInCollection(testCollection)) {
					    		logger.debug("enrolling student to ALL tests in collection " + testCollection.getId());
					    		testSession = studentsTestsService.addNewTestSessionForAllTestsInCollection(enrollment, testSession, testCollection, 
					    				AARTCollectionUtil.getIds(testCollectionTests.get(testCollection)), studentSessionRule, studentsTestsStatusId);
					    	} else {
					    		testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
					    				AARTCollectionUtil.getIds(testCollectionTests.get(testCollection)), studentSessionRule, studentsTestsStatusId);
					    	}
					    	logger.debug("Added enrollment - " + enrollment.getId() +" to new Test Session with id - " +  testSession.getId());
				    	}
			        }
		 			enrollments.get(0).getTestSessions().add(testSession);
				}
			}
		}
		logger.debug("<-- write  - batchRegistrationId : " + batchRegistrationId);
	}
	
	private String prepareTestSessionName(TestCollection testCollection, Enrollment enrollment) {
		String testTypeName = testType.getTestTypeName().substring(testType.getTestTypeName().indexOf('-') + 1).replace("/", "").trim();
		
		if(assessmentProgramUsesStage) {
			if(assessmentProgramCode.equalsIgnoreCase("KAP") && assessment.getAssessmentCode().equalsIgnoreCase("SCD")){
				return String.format("%d_%s_%s_%s_%s", enrollment.getCurrentSchoolYear(), enrollment.getAttendanceSchool().getDisplayIdentifier(),
						gradeCourse.getName(), contentArea.getName(), testTypeName);
			} else if (assessmentProgramCode.equalsIgnoreCase("CPASS")) {
				return String.format("%d_%s_%s_%s_%s_%s", enrollment.getCurrentSchoolYear(), enrollment.getAttendanceSchool().getDisplayIdentifier(),
						testTypeName, gradeCourse == null ? testCollection.getGradeCourse().getName() : gradeCourse.getName(),
						contentArea == null ? testCollection.getContentArea().getName() : contentArea.getName(), testCollection.getStage().getName());
			} else {
				return String.format("%d_%s_%s_%s_%s", enrollment.getCurrentSchoolYear(), enrollment.getAttendanceSchool().getDisplayIdentifier(),
						gradeCourse.getName(), contentArea.getName(), testCollection.getStage().getName());
			}
		} else {
			return String.format("%d_%s_%s_%s_%s", enrollment.getCurrentSchoolYear(), enrollment.getAttendanceSchool().getDisplayIdentifier(),
					gradeCourse.getName(), contentArea.getName(), testTypeName);
		}
	}
	
	/**
	 * 
	 * @param enrollment
	 * @param testCollection
	 * @return
	 */
	private boolean isStageCompleted(Enrollment enrollment, TestCollection testCollection){
		// these should only happen for CPASS
		Long caId = contentArea == null ? testCollection.getContentArea().getId() : contentArea.getId();
		Long gcId = gradeCourse == null ? testCollection.getGradeCourse().getId() : gradeCourse.getId();
		
		List<StudentsTests> completedTests = studentsTestsService.getCompletedStudentsTests(assessment.getId(), testType.getId(), caId, 
				gcId, enrollment.getStudentId(), new Long(enrollment.getCurrentSchoolYear()), testCollection.getStage().getId(), 
				SourceTypeEnum.BATCHAUTO, testCollection.getOperationalTestWindowId());
		if(completedTests != null && !completedTests.isEmpty()){
			if("CPASS".equals(assessmentProgramCode)){
				if(needsToEnrollToAllTestsInCollection(testCollection)){
					if(completedTests.size() == testCollection.getTests().size())
						return true;
					else
						return false;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
			return false;	
		}
	}
	
	private boolean needsToEnrollToAllTestsInCollection(TestCollection tc) {
		boolean ret = "CPASS".equals(assessmentProgramCode) &&
				(tc.getStage() != null && tc.getStage().getCode().equals(StageEnum.PERFORMANCE.getCode()));
		return ret;
	}

	/**
	 * CPASS logic for fudging test type and stage predecessor logic
	 */
	private TestType determineTestTypePredecessor(TestType testType) {
		List<TestType> testTypes = testTypeService.getTestTypeByAssessmentId(assessment.getId());
		String predecessorCode = "";
		String code = testType.getTestTypeCode();
		if (code.equals("AM")) {
			predecessorCode = "A";
		} else if (code.equals("DM")) {
			predecessorCode = "D";
		} else if (code.equals("HM")) {
			predecessorCode = "H";
		}
		for (TestType tt : testTypes) {
			if (tt.getTestTypeCode().equals(predecessorCode)) {
				return tt;
			}
		}
		return testType;
	}
	
	/**
	 * more CPASS logic for fudging test type and stage predecessor logic
	 */
	private boolean hasPredecessorEnrollment(Enrollment enrollment, TestType tt) {
		// CPASS query won't use gradecourse, but other assessment programs would
		for (Organization org : contractingOrganizations) {
			List<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(enrollment.getStudentId(), tt.getId(), enrollment.getContentAreaId(), null,
					org.getId(), assessment.getId(), new Long(enrollment.getCurrentSchoolYear()));
			if (CollectionUtils.isNotEmpty(enrollments)) {
				return true;
			}
		}
		return false;
	}
	
	public GradeCourse getGradeCourse() {
		return gradeCourse;
	}

	public void setGradeCourse(GradeCourse gradeCourse) {
		this.gradeCourse = gradeCourse;
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
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

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public List<Organization> getContractingOrganizations() {
		return contractingOrganizations;
	}

	public void setContractingOrganizations(List<Organization> contractingOrganizations) {
		this.contractingOrganizations = contractingOrganizations;
	}
	
	

}
