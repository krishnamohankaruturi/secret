package edu.ku.cete.batch.processor;

import java.util.HashMap;
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
import edu.ku.cete.batch.support.WriterContext;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.util.StageEnum;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

public abstract class BatchRegistrationProcessor implements ItemProcessor<Enrollment,WriterContext> {
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
    
	@Value("${autoregistration.varianttypeid.code.eng}")
	protected String AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG;
    		
	protected GradeCourse gradeCourse;
	protected TestType testType;
	protected List<TestCollection> testCollections;

	protected ContentArea contentArea;
	protected Long batchRegistrationId;

	protected StepExecution stepExecution;
	
	protected String enrollmentMethod;
	private static final String ENROLLMENT_ADAPTIVE = "ADP";
	protected String assessmentProgramCode;
	
	@SuppressWarnings("unchecked")
	@Override
	public WriterContext process(Enrollment enrollment) throws Exception {
		logger.debug("--> process" );
		WriterContext writerCtx = new WriterContext();
 		Set<String> accessibilityFlags = getAccessibleFlags(enrollment);
 		Map<TestCollection, List<Test>> testCollectionTests = new HashMap<TestCollection, List<Test>>();
		for(TestCollection testCollection: testCollections) {
			List<Test> tests = null;
			//For KAP adaptive, stage 2 and 3, it is not required to get tests here
			if(assessmentProgramCode.equalsIgnoreCase("KAP") 
					&& ENROLLMENT_ADAPTIVE.equalsIgnoreCase(enrollmentMethod) 
					&& (StageEnum.STAGE2.getCode().equalsIgnoreCase(testCollection.getStage().getCode()) 
							|| StageEnum.STAGE3.getCode().equalsIgnoreCase(testCollection.getStage().getCode()))){
					testCollectionTests.put(testCollection, tests);
			}else{
				tests = getTests(enrollment, testCollection, accessibilityFlags);
				if(CollectionUtils.isNotEmpty(tests)) {
					String logMsg = String.format("Tests found with this criteria - grade: %s(%d), testtype: %s(%d), contentarea: %s(%d), testcollection: %d, accessibleflgs: %s.", 
							gradeCourse.getAbbreviatedName(), gradeCourse.getId(), testType.getTestTypeCode(), testType.getId(),
							contentArea.getAbbreviatedName(), contentArea.getId(), testCollection.getId(),accessibilityFlags);
					logger.debug(logMsg);
					
					if(assessmentProgramCode.equalsIgnoreCase("KAP") && ENROLLMENT_ADAPTIVE.equalsIgnoreCase(enrollmentMethod) 
							&& StageEnum.STAGE4.getCode().equalsIgnoreCase(testCollection.getStage().getCode())){
						if(!accessibilityFlags.contains("braille") && !accessibilityFlags.contains("signed")){
							testCollectionTests.put(testCollection, tests);
						}else{
							logMsg = String.format("KAP Adaptive Stage4 test assignment is skipped for student because of PNP Braille or ASL setting - EnrollmentId: %d, testcollection: %d, accessibleflgs: %s.", 
									enrollment.getId(), testCollection.getId(),accessibilityFlags);
							logger.debug(logMsg);
						}
					}else{
						testCollectionTests.put(testCollection, tests);
					}
					
				} else {
					String cErrorMsg = String.format("No tests found with this criteria - grade: %s(%d), testtype: %s(%d), contentarea: %s(%d), testcollection: %d, accessibleflgs: %s.", 
								gradeCourse.getAbbreviatedName(), gradeCourse.getId(), testType.getTestTypeCode(), testType.getId(),
								contentArea.getAbbreviatedName(), contentArea.getId(), testCollection.getId(),accessibilityFlags);
					logger.debug(cErrorMsg);
					BatchRegistrationReason brReason = new BatchRegistrationReason();
					brReason.setBatchRegistrationId(batchRegistrationId);
					brReason.setStudentId(enrollment.getStudentId());
					brReason.setReason(cErrorMsg);
					((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
				}
			}
		}
		if(testCollectionTests.size() == testCollections.size()) {
			logger.debug("Found tests for all test collections");
			writerCtx.setEnrollment(enrollment);
			writerCtx.setTestCollectionTests(testCollectionTests);
			writerCtx.setAccessibilityFlags(accessibilityFlags);
			return writerCtx;
		} else if(testCollectionTests.size() > 0) {
			logger.debug("Found tests for some test collections");
			writerCtx.setEnrollment(enrollment);
			writerCtx.setTestCollectionTests(testCollectionTests);
			writerCtx.setAccessibilityFlags(accessibilityFlags);
			return writerCtx;
		} else {
			StringBuilder errorMessage = new StringBuilder("[");
			int i=0;
			for(TestCollection testCollection: testCollections) {
				if(i > 0) {
					errorMessage.append(",");
				}
				errorMessage.append(testCollection.getId());
				i=1;
			}
			errorMessage.append("]");
			String cErrorMsg = String.format("No tests found with criteria - grade: %s(%d), testtype: %s(%d), contentarea: %s(%d), testcollections: %s, accessibleflgs: %s.", 
					gradeCourse.getAbbreviatedName(), gradeCourse.getId(), testType.getTestTypeCode(), testType.getId(),
					contentArea.getAbbreviatedName(), contentArea.getId(), errorMessage.toString(),accessibilityFlags);
			logger.debug(cErrorMsg);
			logger.debug("<-- process raiseSkipError");
			throw new SkipBatchException(cErrorMsg);
		}
	}
	
	protected abstract List<Test> getTests(Enrollment enrollment, TestCollection testCollection, Set<String> accessibilityFlags);
	protected abstract Set<String> getAccessibleFlags(Enrollment enrollment);

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
	
	
}
