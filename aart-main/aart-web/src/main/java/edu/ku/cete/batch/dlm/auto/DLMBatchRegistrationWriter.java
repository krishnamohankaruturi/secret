package edu.ku.cete.batch.dlm.auto;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

public class DLMBatchRegistrationWriter implements ItemWriter<DLMAutoWriterContext> {
	
	private final static Log logger = LogFactory.getLog(DLMBatchRegistrationWriter.class);
    
	@Autowired
	private StudentsTestsService studentsTestsService;
	@Autowired
	private StudentTrackerService trackerService;
	
	@Autowired
	private TestSessionService testSessionService;

	@Autowired
    private CategoryService categoryService;
	
	@Autowired
	private StudentSessionRuleConverter studentSessionRuleConverter;
	
	@Autowired
	private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
    
	@Value("${autoregistration.varianttypeid.code.eng}")
    private String AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG;
	
	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;
	
	@Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;

	private Category unusedSession;
	
	private Long batchRegistrationId;
	private ContentArea contentArea;
	private Organization contractingOrganization;

	@BeforeStep
	public void initializeValues(StepExecution stepExecution) {
		unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
	}
	
	@Override
	public void write(List<? extends DLMAutoWriterContext> dlmAutoWriterContextList) throws Exception {
		logger.debug("--> write  - batchRegistrationId : " + batchRegistrationId);
		if(!dlmAutoWriterContextList.isEmpty()) {
			StudentTracker studentTracker = dlmAutoWriterContextList.get(0).getStudentTracker();
			ComplexityBand complexityBand = dlmAutoWriterContextList.get(0).getComplexityBand();
			Enrollment enrollment = studentTracker.getEnrollment();
			TestCollection testCollection = dlmAutoWriterContextList.get(0).getTestCollection();
			List<Test> tests = dlmAutoWriterContextList.get(0).getTests();
 			String testSessioName = prepareTestSessionName(testCollection, enrollment, complexityBand);
 			
 			Long otwId = testCollection.getOperationalTestWindowId();
	        List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
	        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
	        TestSession testSession = new TestSession();
			testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
			testSession.setRosterId(dlmAutoWriterContextList.get(0).getRosterId());
			testSession.setName(testSessioName);
			testSession.setStatus(unusedSession.getId());
			testSession.setActiveFlag(true);
			testSession.setCurrentTestNumber(dlmAutoWriterContextList.get(0).getCurrentTestNumber());
			testSession.setNumberOfTestsRequired(dlmAutoWriterContextList.get(0).getNumberOfTestsRequired());			
			testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
			testSession.setGradeCourseId(studentTracker.getGradeCourseId());
 			testSession.setTestCollectionId(testCollection.getTestCollectionId());
 			testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
	    	testSession.setOperationalTestWindowId(otwId);
			testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
			    				AARTCollectionUtil.getIds(tests), studentSessionRule, null); 
			dlmAutoWriterContextList.get(0).getStudentTracker().getRecommendedBand().setTestSessionId(testSession.getId());
			trackerService.updateBandTestSession(dlmAutoWriterContextList.get(0).getStudentTracker());
			dlmAutoWriterContextList.get(0).setTestSession(testSession);
			logger.debug("Added enrollment - " + enrollment.getId() +" to new Test Session with id - " +  testSession.getId());
		}
		logger.debug("<-- write  - batchRegistrationId : " + batchRegistrationId);
	}
	
	private String prepareTestSessionName(TestCollection testCollection, Enrollment enrollment, ComplexityBand complexityBand) {
		return "DLM-" + enrollment.getLegalLastName() + enrollment.getLegalFirstName() + "-" + enrollment.getStudentId() + "-" + testCollection.getName();
	}
	 

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}
	
	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}
	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}
}
