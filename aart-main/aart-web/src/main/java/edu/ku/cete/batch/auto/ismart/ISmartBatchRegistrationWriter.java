/**
 * 
 */
package edu.ku.cete.batch.auto.ismart;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * @author Kiran Reddy Taduru
 * Jun 5, 2018 3:56:09 PM
 */
public class ISmartBatchRegistrationWriter implements ItemWriter<ISmartAutoWriterContext> {

	private final static Log logger = LogFactory .getLog(ISmartBatchRegistrationWriter.class);
	
	private Organization contractingOrganization;
	private ContentArea contentArea;
	private StepExecution stepExecution;
	private Long operationalTestWindowId;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	private Long testSessionUnusedStatusId;
	private StudentSessionRule studentSessionRule;	
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	private static final Integer MAX_NUM_OF_TESTS_REQUIRED = 1;
	
	@Override
	public void write(List<? extends ISmartAutoWriterContext> writerContext) throws Exception {
		logger.debug(">>>>>> Entering ISmartBatchRegistrationWriter::write()");
		
		if (CollectionUtils.isNotEmpty(writerContext)) {
			ISmartAutoWriterContext iSmartAutoWriterContext = writerContext.get(0);
			
			logger.debug(">>>>>> Inside ISmartBatchRegistrationWriter::write() - StudentId: " + iSmartAutoWriterContext.getStudentId());
			
			String testSessioName = prepareTestSessionName(iSmartAutoWriterContext.getStudentId().toString(),
					iSmartAutoWriterContext.getStudentFirstName(), iSmartAutoWriterContext.getStudentLastName(), iSmartAutoWriterContext.getTestCollection().getName());
			
			Long otwId = iSmartAutoWriterContext.getTestCollection().getOperationalTestWindowId();			
			TestSession testSession = new TestSession();
			testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
			testSession.setRosterId(iSmartAutoWriterContext.getRosterId());
			testSession.setName(testSessioName);
			testSession.setStatus(testSessionUnusedStatusId);
			testSession.setActiveFlag(true);
			testSession.setAttendanceSchoolId(iSmartAutoWriterContext.getAttendanceschoolId());
			testSession.setGradeCourseId(iSmartAutoWriterContext.getTestCollection().getGradeCourse().getId());
			testSession.setTestCollectionId(iSmartAutoWriterContext.getTestCollection().getId());
			testSession.setOperationalTestWindowId(otwId);
			testSession.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
			testSession.setNumberOfTestsRequired(MAX_NUM_OF_TESTS_REQUIRED);
			Enrollment enrollment = new Enrollment();
			enrollment.setId(iSmartAutoWriterContext.getEnrollmentId());
			enrollment.setStudentId(iSmartAutoWriterContext.getStudentId());
			enrollment.setAttendanceSchoolId(iSmartAutoWriterContext.getAttendanceschoolId());
			
			testSession = studentsTestsService.addNewTestSession(enrollment, testSession, iSmartAutoWriterContext.getTestCollection(), 
    				AARTCollectionUtil.getIds(iSmartAutoWriterContext.getTests()), studentSessionRule, null); 
			writerContext.get(0).setTestSession(testSession);
			
			logger.debug(">>>>>> Exiting ISmartBatchRegistrationWriter::write() - StudentId: " + iSmartAutoWriterContext.getStudentId());
			
		}
		
		logger.debug(">>>>>> Exiting ISmartBatchRegistrationWriter::write()");
		
	}
	
	private String prepareTestSessionName(String studentId, String studentFirstName, String studentLastName, String testCollectionName) {		
		return "ISMART-" + studentLastName+ StringUtils.EMPTY + studentFirstName + "-"  +studentId + "-" + testCollectionName;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
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

	public Long getTestSessionUnusedStatusId() {
		return testSessionUnusedStatusId;
	}

	public void setTestSessionUnusedStatusId(Long testSessionUnusedStatusId) {
		this.testSessionUnusedStatusId = testSessionUnusedStatusId;
	}

	public StudentSessionRule getStudentSessionRule() {
		return studentSessionRule;
	}

	public void setStudentSessionRule(StudentSessionRule studentSessionRule) {
		this.studentSessionRule = studentSessionRule;
	}

}
