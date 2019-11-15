package edu.ku.cete.batch.kap.predictive;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.processor.BatchRegistrationProcessor;
import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.batch.support.WriterContext;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

public class KAPPredictiveProcessor extends BatchRegistrationProcessor {
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private StudentProfileService studentProfileService;

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
	private StudentsTestsService studentsTestsService;

	@Autowired
	private TestCollectionService tcService;

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
	private StepExecution stepExecution;
	protected String enrollmentMethod;
	protected String assessmentProgramCode;
	private Organization contractingOrganization;
	private Assessment assessment;
	
	private Map<TestCollection, List<Test>> testCollectionsTests;

	@Override
	public WriterContext process(Enrollment enrollment) throws Exception {
		WriterContext writerContext = new WriterContext();
		
		Set<String> accessibleFlags = getAccessibleFlags(enrollment);
		
		/*Map<TestCollection, List<Test>> testCollectionsTests = new HashMap<TestCollection, List<Test>>();
		for (TestCollection testCollection : testCollections) {
			List<Test> tests = getTests(enrollment, testCollection, accessibleFlags);
			if (CollectionUtils.isNotEmpty(tests)) {
				testCollectionsTests.put(testCollection, tests);
			} else {
				logger.debug("No published and QC complete tests found in test collection " + testCollection.getId());
			}
		}
		
		if (testCollectionsTests.isEmpty()) {
			String msg = "No tests found in any collections for " + contentArea.getName() + " grade " + gradeCourse.getAbbreviatedName();
			throw new SkipBatchException(msg);
		}*/
		
		writerContext.setEnrollment(enrollment);
		writerContext.setAccessibilityFlags(accessibleFlags);
		writerContext.setTestCollectionTests(testCollectionsTests);
		return writerContext;
	}
	
	/**
	 * This is included in this class to satisfy inheritance. Initial implementation includes no accessibility flag checks.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected Set<String> getAccessibleFlags(Enrollment enrollment) {
		return Collections.EMPTY_SET;
	}
	
	@Override
	protected List<Test> getTests(Enrollment enrollment, TestCollection testCollection, Set<String> accessibilityFlags) {
		return testService.findPredictiveTestsInTestCollection(testCollection.getId(), testStatusConfiguration.getPublishedTestStatusCategory().getId());
	}
	
	/*@SuppressWarnings("unchecked")
	private void writeReason(Long studentId, String msg) {
		logger.debug(msg);

		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setStudentId(studentId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext()
				.get("jobMessages")).add(brReason);
	}*/
	
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
	
	public Map<TestCollection, List<Test>> getTestCollectionsTests() {
		return testCollectionsTests;
	}
	
	public void setTestCollectionsTests(Map<TestCollection, List<Test>> testCollectionsTests) {
		this.testCollectionsTests = testCollectionsTests;
	}
}