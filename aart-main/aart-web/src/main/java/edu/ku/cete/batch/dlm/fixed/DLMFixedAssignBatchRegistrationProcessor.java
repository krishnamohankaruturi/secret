package edu.ku.cete.batch.dlm.fixed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;

public class DLMFixedAssignBatchRegistrationProcessor implements ItemProcessor<StudentRoster,DLMFixedAssignAutoWriterContext>{

	private final static Log logger = LogFactory .getLog(DLMFixedAssignBatchRegistrationProcessor.class);
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private TestStatusConfiguration testStatusConfiguration;
	
	private Random random = new Random();
	
	private String enrollmentMethod;	
	private StepExecution stepExecution;	
	protected Long batchRegistrationId;	
	private Organization contractingOrganization;
	private ContentArea contentArea;
	private String gradeAbbrName;
	private Long assessmentProgramId;
	private Long operationalTestWindowId;
	
	@Override
	public DLMFixedAssignAutoWriterContext process(StudentRoster studentRoster)
			throws Exception {
		DLMFixedAssignAutoWriterContext ctx = processFixedAssign(studentRoster);
		if(ctx == null) {
			stepExecution.setProcessSkipCount(stepExecution.getProcessSkipCount()+1);
		}
		return ctx;
	}

	@SuppressWarnings("unchecked")
	private DLMFixedAssignAutoWriterContext processFixedAssign(StudentRoster studentRoster) {
		List<BatchRegistrationReason> reasons = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
		
		//DE12518
		List<StudentsTests> existingSessions = studentsTestsService.findExistingFixedAssignTestSessions(studentRoster.getStudentId(),studentRoster.getEnrollmentId(), contentArea.getId(), studentRoster.getGradeCourse().getAbbreviatedName(), operationalTestWindowId);
		if(!existingSessions.isEmpty()) {
			writeReason(studentRoster.getStudentId(), String.format("TestSession already exists skipping - contentareaId- "
					+ "%d, gradeId-%d, studenttestid-%d, studentid-%d,enrollmentid-%d", contentArea.getId(), studentRoster.getGradeCourse().getId(),existingSessions.get(0).getId(),
					studentRoster.getStudentId(),studentRoster.getEnrollmentId()), reasons);
			return null;
		}
		
		Map<TestCollection, List<Test>> testCollectionTests = new HashMap<TestCollection, List<Test>>();
		
		List<TestCollection> testCollections = getTestCollections(studentRoster.getStudentId(),studentRoster.getEnrollmentId(), contentArea.getId(), studentRoster.getGradeCourse().getAbbreviatedName(), operationalTestWindowId);
		if(CollectionUtils.isEmpty(testCollections)) {
			writeReason(studentRoster.getStudentId(), String.format("Test collection not found for assessmentProgramId - (%d), contentareaId- "
					+ "%d, gradeId-%d, studentid-%d,enrollmentid-%d", assessmentProgramId, contentArea.getId(), studentRoster.getGradeCourse().getId(), studentRoster.getStudentId(),studentRoster.getEnrollmentId()					
					), reasons);
			return null;
		}
		for(TestCollection testCollection: testCollections) {
			List<Test> tests = getTests(testCollection);
			if(CollectionUtils.isEmpty(tests)){
				String cErrorMsg = String.format("No tests found with criteria - grade: %s(%d), contentarea: %s(%d), testcollections: %s(%d), studentid-%d,enrollmentid-%d.", 
						studentRoster.getGradeCourse().getAbbreviatedName(), studentRoster.getGradeCourse().getId(), contentArea.getAbbreviatedName(), contentArea.getId(), testCollection.getName(),
						testCollection.getId(),studentRoster.getStudentId(),studentRoster.getEnrollmentId());
				writeReason(studentRoster.getStudentId(), cErrorMsg, reasons);
			}else{
				testCollectionTests.put(testCollection, tests);
			}
		}
		
		DLMFixedAssignAutoWriterContext wCtx = new DLMFixedAssignAutoWriterContext();
		wCtx.setAttendanceschoolId(studentRoster.getAttendanceSchoolId());
		wCtx.setAttSchDisplayIdentifer(studentRoster.getAttendanceSchool().getDisplayIdentifier());
		wCtx.setEnrollmentId(studentRoster.getEnrollmentId());
		wCtx.setGradeCourseAbbrName(studentRoster.getGradeCourse().getAbbreviatedName());
		wCtx.setRosterId(studentRoster.getRoster().getId());
		wCtx.setStudentId(studentRoster.getStudentId());
		wCtx.setGradeCourseId(studentRoster.getGradeCourse().getId());
		wCtx.setStatestudentidentifier(studentRoster.getStudent().getStateStudentIdentifier());
		wCtx.setTestCollectionTests(testCollectionTests);
		wCtx.setContentAreaAbbrName(contentArea.getAbbreviatedName());
		wCtx.setStudentFirstName(studentRoster.getStudent().getLegalFirstName());
		wCtx.setStudentLastName(studentRoster.getStudent().getLegalLastName());
		wCtx.setContentAreaId(contentArea.getId());		
		return wCtx;
	}

	private List<Test> getTests(TestCollection testCollection) {
		logger.debug("--> getTests" );
		List<Test> tests = testService.findTestsForDLMFixedAssign(testCollection.getTestCollectionId(), 
        		testStatusConfiguration.getPublishedTestStatusCategory().getId());
		logger.debug("<-- getTests" );
		return tests;
	}

	private List<TestCollection> getTestCollections(Long studentId, Long enrollmentId,Long contentAreaId, 
			String gradeCourseAbbrName, Long operationalTestWindowId) {
		List<TestCollection> testCollections = testCollectionService.getFixedAssignTestCollections(gradeCourseAbbrName, contentAreaId, null, operationalTestWindowId);	
		
		//limit to one random test collection
		if(!testCollections.isEmpty()) {
			int index = random.nextInt(testCollections.size());
			
			return Arrays.asList(testCollections.get(index));
		}
		return testCollections;
	}

	private void writeReason(Long studentId, String msg, List<BatchRegistrationReason> reasons) {
		logger.debug("writeReason: studentid: "+studentId+", "+msg);
		BatchRegistrationReason reason = new BatchRegistrationReason();
		reason.setStudentId(studentId);
		reason.setReason(msg);
		reasons.add(reason);
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

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
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

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}	

	public String getGradeAbbrName() {
		return gradeAbbrName;
	}

	public void setGradeAbbrName(String gradeAbbrName) {
		this.gradeAbbrName = gradeAbbrName;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}		
}
