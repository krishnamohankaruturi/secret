package edu.ku.cete.batch.dlm.auto.multiassign;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.TestSession;
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
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

public class DLMMultiAssignBatchRegistrationWriter implements ItemWriter<DLMMultiAssignAutoWriterContext> {
	
	private final static Log logger = LogFactory.getLog(DLMMultiAssignBatchRegistrationWriter.class);
    
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
	public void write(List<? extends DLMMultiAssignAutoWriterContext> dlmAutoWriterContextList) throws Exception {
		logger.debug("--> write  - batchRegistrationId : " + batchRegistrationId);
		if(!dlmAutoWriterContextList.isEmpty()) {
			Enrollment enrollment = dlmAutoWriterContextList.get(0).getEnrollment();
			ComplexityBand complexityBand = dlmAutoWriterContextList.get(0).getComplexityBand();
			Map<TestCollection, List<Test>> testCollectionTests =  dlmAutoWriterContextList.get(0).getTestCollectionTests();
			logger.warn(String.format("testCollectionTests Map size in writer - %d", testCollectionTests.size()));
			GradeCourse gradeCourse = dlmAutoWriterContextList.get(0).getGradeCourse();
			for(TestCollection testCollection: testCollectionTests.keySet()) {
				List<Test> tests = testCollectionTests.get(testCollection);
				logger.warn("Processing writer for TestCollection - "+ testCollection.getId() + ", Testids - " + AARTCollectionUtil.getIds(tests));
	 			String testSessioName = prepareTestSessionName(testCollection, enrollment, complexityBand);
	 			Long otwId = testCollection.getOperationalTestWindowId();
	 			List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
		        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
		        TestSession testSession = new TestSession();
				testSession.setSource(SourceTypeEnum.MABATCH.getCode());
				testSession.setRosterId(dlmAutoWriterContextList.get(0).getRosterId());
				testSession.setName(testSessioName);
				testSession.setStatus(unusedSession.getId());
				testSession.setActiveFlag(true);
				testSession.setAttendanceSchoolId(enrollment.getAttendanceSchool().getId());
				testSession.setGradeCourseId(gradeCourse.getId());
	 			testSession.setTestCollectionId(testCollection.getTestCollectionId());
				testSession.setOperationalTestWindowId(otwId);
				testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
		    	testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
				    				AARTCollectionUtil.getIds(tests), studentSessionRule, null); 
				dlmAutoWriterContextList.get(0).getTestSessions().add(testSession);
				logger.debug("Added enrollment - " + enrollment.getId() +" to new Test Session with id - " +  testSession.getId());
			}
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
