/**
 * 
 */
package edu.ku.cete.batch.reportprocess.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.StageEnum;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 7, 2017 5:00:34 PM
 */
public class BatchPredictiveStudentReportGetTests implements ItemProcessor<InterimStudentReport, Object> {
	final static Log logger = LogFactory.getLog(BatchPredictiveStudentReportGetTests.class);
	
	private StepExecution stepExecution;
    private Organization contractingOrganization;
    private Long testsCompletedStatusId;
    private Long testsInprogressStatusId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long testStatusInprogressTimedoutId;
	private String assessmentProgramCode;
    private List<Long> rawScaleExternalTestIds;
    private Boolean isTestingWindowComplete;
    private Long operationalTestWindowId;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
	@Autowired
	private TestService testService;
	
    
	@SuppressWarnings("unchecked")
	@Override
	public Object process(InterimStudentReport studentReport) throws Exception {
		logger.debug("Inside BatchPredictiveStudentReportGetTests process...for Student - " + studentReport.getStudentId());
		List<Long> testsStatusIds = new ArrayList<Long>();
		
		if(isTestingWindowComplete){
			testsStatusIds.add(testsInprogressStatusId);
			testsStatusIds.add(testStatusInprogressTimedoutId);
			testsStatusIds.add(testStatusPendingId);
			//testsStatusIds.add(testStatusUnusedId);
			testsStatusIds.add(testsCompletedStatusId);
		}else{
			testsStatusIds.add(testsCompletedStatusId);
		}
		
		studentReport.setEnrollments(enrollmentService.findReportEnrollments(studentReport.getStudentId(), contractingOrganization.getId(),
				studentReport.getSchoolYear(), rawScaleExternalTestIds, testsStatusIds));
		
		List<Long> enrollmentIds = new ArrayList<Long>();
		for (Enrollment e : studentReport.getEnrollments()) {
			enrollmentIds.add(e.getId());
		}
		
		List<StudentReportTestResponses> studentTestsScore = testService.getPredictiveTestsScoreByStudentIdExternalTestIds(studentReport.getStudentId(),
				rawScaleExternalTestIds, enrollmentIds, testsStatusIds);
		if(CollectionUtils.isEmpty(studentTestsScore)){
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "Responses not found for student: " + studentReport.getStudentId() 
				+ " AssessmentProgramId - " + studentReport.getAssessmentProgramId()
				+ " ContentAreaId - " + studentReport.getContentAreaId()
				+ " GradeId - " + studentReport.getGradeId()
				+ " SchoolYear - " + studentReport.getCurrentSchoolYear()
				+ " ExternalTestIds = " + rawScaleExternalTestIds;
				reportProcessReason.setReason(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}
		
		List<StudentsResponses> stg1CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg1OtherstudentsResponses = new ArrayList<StudentsResponses>();
		
		for(StudentReportTestResponses studentReportTestResponse : studentTestsScore){
			if(StageEnum.STAGE1.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
				List<StudentsResponses> studentResponses =  studentReportTestResponse.getStudentResponses();
				Long completedStudentTestId = null;
				for(StudentsResponses studentResponse: studentResponses){
					if(testsCompletedStatusId.equals(studentResponse.getStudentsTestStatus()) ||
							testsInprogressStatusId.equals(studentResponse.getStudentsTestStatus()) ||
							testStatusInprogressTimedoutId.equals(studentResponse.getStudentsTestStatus())){
						if(completedStudentTestId == null){
							completedStudentTestId = studentResponse.getStudentsTestsId();
						}
						if(completedStudentTestId.equals(studentResponse.getStudentsTestsId())){
							stg1CompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						stg1OtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(stg1CompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(stg1CompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(stg1OtherstudentsResponses);
				}

			}
		}
		
		studentReport.setStudentTestsScore(studentTestsScore);
		
		logger.debug("Completed BatchPredictiveStudentReportGetTests process....for Student - " + studentReport.getStudentId());
		
		return studentReport;
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


	public Long getTestsCompletedStatusId() {
		return testsCompletedStatusId;
	}


	public void setTestsCompletedStatusId(Long testsCompletedStatusId) {
		this.testsCompletedStatusId = testsCompletedStatusId;
	}


	public Long getTestsInprogressStatusId() {
		return testsInprogressStatusId;
	}


	public void setTestsInprogressStatusId(Long testsInprogressStatusId) {
		this.testsInprogressStatusId = testsInprogressStatusId;
	}


	public Long getTestStatusUnusedId() {
		return testStatusUnusedId;
	}


	public void setTestStatusUnusedId(Long testStatusUnusedId) {
		this.testStatusUnusedId = testStatusUnusedId;
	}


	public Long getTestStatusPendingId() {
		return testStatusPendingId;
	}


	public void setTestStatusPendingId(Long testStatusPendingId) {
		this.testStatusPendingId = testStatusPendingId;
	}


	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}


	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}


	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}


	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}


	public List<Long> getRawScaleExternalTestIds() {
		return rawScaleExternalTestIds;
	}


	public void setRawScaleExternalTestIds(List<Long> rawScaleExternalTestIds) {
		this.rawScaleExternalTestIds = rawScaleExternalTestIds;
	}


	public Boolean getIsTestingWindowComplete() {
		return isTestingWindowComplete;
	}


	public void setIsTestingWindowComplete(Boolean isTestingWindowComplete) {
		this.isTestingWindowComplete = isTestingWindowComplete;
	}


	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}


	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

}
