package edu.ku.cete.batch.dlm.survey;

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

public class DLMResearchSurveyWriter implements ItemWriter<DLMResearchSurveyWriterContext>{

	private final static Log logger = LogFactory.getLog(DLMResearchSurveyWriter.class);
	
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
	public void write(List<? extends DLMResearchSurveyWriterContext> dlmResearchSurveyWriterContextList)
			throws Exception {
		if(CollectionUtils.isNotEmpty(dlmResearchSurveyWriterContextList)) {
			DLMResearchSurveyWriterContext dlmResearchSurveyWriterContext = dlmResearchSurveyWriterContextList.get(0);
			Map<TestCollection, List<Test>> testCollectionTests =  dlmResearchSurveyWriterContext.getTestCollectionTests();
			logger.warn(String.format("testCollectionTests Map size in writer - %d", testCollectionTests.size()));
			for(TestCollection testCollection: testCollectionTests.keySet()) {
				List<Test> tests = testCollectionTests.get(testCollection);
				logger.warn("Processing writer for TestCollection - "+ testCollection.getId() + ", Testids - " + AARTCollectionUtil.getIds(tests));
				String testSessioName = prepareTestSessionName(dlmResearchSurveyWriterContext.getStudentId().toString(),
						dlmResearchSurveyWriterContext.getStudentFirstName(), dlmResearchSurveyWriterContext.getStudentLastName(), 
						testCollection.getName(),dlmResearchSurveyWriterContext.getAssessmentProgramCode());
				Long otwId = testCollection.getOperationalTestWindowId();
				List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(otwId);
		        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
				TestSession testSession = new TestSession();
				testSession.setSource(SourceTypeEnum.RESEARCHSURVEY.getCode());				
				testSession.setName(testSessioName);
				testSession.setStatus(unusedSession.getId());
				testSession.setRosterId(dlmResearchSurveyWriterContext.getRosterId());
				testSession.setActiveFlag(true);
				testSession.setAuditColumnProperties();
				testSession.setAttendanceSchoolId(dlmResearchSurveyWriterContext.getAttendanceschoolId());
				testSession.setGradeCourseId(testCollection.getGradeCourse().getId());
				testSession.setTestCollectionId(testCollection.getId());
				testSession.setOperationalTestWindowId(otwId);
				testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
				Enrollment enrollment = new Enrollment();
				enrollment.setId(dlmResearchSurveyWriterContext.getEnrollmentId());
				enrollment.setStudentId(dlmResearchSurveyWriterContext.getStudentId());
				enrollment.setAttendanceSchoolId(dlmResearchSurveyWriterContext.getAttendanceschoolId());				
				testSession = studentsTestsService.addNewTestSession(enrollment, testSession, testCollection, 
	    				AARTCollectionUtil.getIds(tests), studentSessionRule, null); 
			}
		}
	}
	
	private String prepareTestSessionName(String studentId, String studentFirstName, String studentLastName, String testCollectionName, String assessmentProgramCode) {		
		return assessmentProgramCode + "-" + studentLastName+ StringUtils.EMPTY + studentFirstName + "-"  +studentId + "-" + testCollectionName;
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
