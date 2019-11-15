package edu.ku.cete.batch.dlm.fixed;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

public class DLMFixedAssignBatchRegistrationWriter implements ItemWriter<DLMFixedAssignAutoWriterContext>{
	
	private final static Log logger = LogFactory.getLog(DLMFixedAssignBatchRegistrationWriter.class);
	
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
	private String gradeAbbrName;
	
	private static final Integer MAX_NUM_OF_FIXED_TESTS_REQUIRED = 1;	
	
	@BeforeStep
	public void initializeValues(StepExecution stepExecution) {
		unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
	}
	
	@Override
	public void write(List<? extends DLMFixedAssignAutoWriterContext> dlmFixedAutoWriterContextList)
			throws Exception {
		if(CollectionUtils.isNotEmpty(dlmFixedAutoWriterContextList)) {			
			DLMFixedAssignAutoWriterContext dlmFixedAssignAutoWriterContext = dlmFixedAutoWriterContextList.get(0);
			Map<TestCollection, List<Test>> testCollectionTests =  dlmFixedAssignAutoWriterContext.getTestCollectionTests();
			logger.warn(String.format("testCollectionTests Map size in writer - %d", testCollectionTests.size()));
			for(TestCollection testCollection: testCollectionTests.keySet()) {
				List<Test> tests = testCollectionTests.get(testCollection);
				logger.warn("Processing writer for TestCollection - "+ testCollection.getId() + ", Testids - " + AARTCollectionUtil.getIds(tests));
				String testSessioName = prepareTestSessionName(dlmFixedAssignAutoWriterContext.getStudentId().toString(),
						dlmFixedAssignAutoWriterContext.getStudentFirstName(), dlmFixedAssignAutoWriterContext.getStudentLastName(), testCollection.getName());
				Long otwId = testCollection.getOperationalTestWindowId();
				List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
		        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
				TestSession testSession = new TestSession();
				testSession.setSource(SourceTypeEnum.FIXBATCH.getCode());
				testSession.setRosterId(dlmFixedAssignAutoWriterContext.getRosterId());
				testSession.setName(testSessioName);
				testSession.setStatus(unusedSession.getId());
				testSession.setActiveFlag(true);
				testSession.setAttendanceSchoolId(dlmFixedAssignAutoWriterContext.getAttendanceschoolId());
				testSession.setGradeCourseId(testCollection.getGradeCourse().getId());
				testSession.setTestCollectionId(testCollection.getId());
				testSession.setOperationalTestWindowId(otwId);
				testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
				testSession.setCurrentTestNumber(MAX_NUM_OF_FIXED_TESTS_REQUIRED);
				testSession.setNumberOfTestsRequired(MAX_NUM_OF_FIXED_TESTS_REQUIRED);
				Enrollment enrollment = new Enrollment();
				enrollment.setId(dlmFixedAssignAutoWriterContext.getEnrollmentId());
				enrollment.setStudentId(dlmFixedAssignAutoWriterContext.getStudentId());
				enrollment.setAttendanceSchoolId(dlmFixedAssignAutoWriterContext.getAttendanceschoolId());				
				testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
	    				AARTCollectionUtil.getIds(tests), studentSessionRule, null); 
			}
		}
	}

	private String prepareTestSessionName(String studentId, String studentFirstName, String studentLastName, String testCollectionName) {		
		return "DLM-" + studentLastName+ StringUtils.EMPTY + studentFirstName + "-"  +studentId + "-" + testCollectionName;
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

	public String getGradeAbbrName() {
		return gradeAbbrName;
	}

	public void setGradeAbbrName(String gradeAbbrName) {
		this.gradeAbbrName = gradeAbbrName;
	}	
}
