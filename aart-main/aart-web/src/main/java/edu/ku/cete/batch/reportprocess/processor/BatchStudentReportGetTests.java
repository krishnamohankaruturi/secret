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
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.StageEnum;

public class BatchStudentReportGetTests implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;
    private Organization contractingOrganization;
    private Long testsCompletedStatusId;
    private Long testsInprogressStatusId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long testStatusInprogressTimedoutId;
	private String assessmentProgramCode;
    private List<Long> rawScaleExternalTestIds;
    private Long specialCircumstanceStatusSavedId;
    private Long specialCircumstanceStatusApprovedId; 
    private Long scoringStatusCompletedId;
    private String skipStudentCalcForUncompletedStages;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
	@Autowired
	private TestService testService;
	
    final static Log logger = LogFactory.getLog(BatchStudentReportGetTests.class);

	@SuppressWarnings("unchecked")
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside getTests process....for Student - " + studentReport.getStudentId());
		List<Long> testsStatusIds = new ArrayList<Long>();
		List<Long> specialCircumstanceStatusIds = new ArrayList<Long>();
		testsStatusIds.add(testsCompletedStatusId);
		if(assessmentProgramCode.equalsIgnoreCase("KAP")){
			testsStatusIds.add(testsInprogressStatusId);
			testsStatusIds.add(testStatusInprogressTimedoutId);
			testsStatusIds.add(testStatusPendingId);
			testsStatusIds.add(testStatusUnusedId);
		}
		
		specialCircumstanceStatusIds.add(specialCircumstanceStatusSavedId);
		specialCircumstanceStatusIds.add(specialCircumstanceStatusApprovedId);
		
		studentReport.setEnrollments(enrollmentService.findReportEnrollments(studentReport.getStudentId(), contractingOrganization.getId(),
				studentReport.getSchoolYear(), rawScaleExternalTestIds, testsStatusIds));
		
		List<Long> enrollmentIds = new ArrayList<Long>();
		for (Enrollment e : studentReport.getEnrollments()) {
			enrollmentIds.add(e.getId());
		}
		
		List<StudentReportTestResponses> studentTestsScore = testService.getTestsScoreByStudentIdExternalTestIds(studentReport.getStudentId(),
				rawScaleExternalTestIds, enrollmentIds, testsStatusIds, specialCircumstanceStatusIds, contractingOrganization.getId(), scoringStatusCompletedId);
		if(CollectionUtils.isEmpty(studentTestsScore)){
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "No Completed/InProgress tests/responses found for student: "+studentReport.getStudentId() ;
				reportProcessReason.setReason(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}
			
		List<StudentsResponses> stg1CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg1OtherstudentsResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg2CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg2OtherstudentsResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg3CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg3OtherstudentsResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg4CompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> stg4OtherstudentsResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> performanceCompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> performanceOtherstudentsResponses = new ArrayList<StudentsResponses>();
		
		//For F788 - during spring testing session is in progress, need not do score calculations until student completes all the stages.
		if(skipStudentCalcForUncompletedStages.equalsIgnoreCase("true") && assessmentProgramCode.equalsIgnoreCase(CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE))
		{
			for(StudentReportTestResponses studentReportTestResponse : studentTestsScore) {
					if(!studentReportTestResponse.getStudentsTestStatus().equals(testsCompletedStatusId)){
						throw new SkipBatchException("Stage "+studentReportTestResponse.getStageCode()+
								" test is not completed Skipping Calculation. Skipping student id -" + studentReport.getStudentId());
					}				
			}
		}
		
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
			if(StageEnum.STAGE2.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							stg2CompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						stg2OtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(stg2CompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(stg2CompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(stg2OtherstudentsResponses);
				}

			}
			
			if(StageEnum.STAGE3.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							stg3CompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						stg3OtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(stg3CompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(stg3CompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(stg3OtherstudentsResponses);
				}

			}
			
			if(StageEnum.STAGE4.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							stg4CompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						stg4OtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(stg4CompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(stg4CompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(stg4OtherstudentsResponses);
				}

			}
			
			if(StageEnum.PERFORMANCE.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							performanceCompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						performanceOtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(performanceCompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(performanceCompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(performanceOtherstudentsResponses);
				}

			}
			
		}
			
		studentReport.setStudentTestsScore(studentTestsScore);
		
		logger.debug("Completed getTests process....for Student - " + studentReport.getStudentId());
		
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
		
		public List<Long> getRawScaleExternalTestIds() {
			return rawScaleExternalTestIds;
		}

		public void setRawScaleExternalTestIds(List<Long> rawScaleExternalTestIds) {
			this.rawScaleExternalTestIds = rawScaleExternalTestIds;
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


		public String getAssessmentProgramCode() {
			return assessmentProgramCode;
		}

		public void setAssessmentProgramCode(String assessmentProgramCode) {
			this.assessmentProgramCode = assessmentProgramCode;
		}


		public Long getSpecialCircumstanceStatusSavedId() {
			return specialCircumstanceStatusSavedId;
		}


		public void setSpecialCircumstanceStatusSavedId(Long specialCircumstanceStatusSavedId) {
			this.specialCircumstanceStatusSavedId = specialCircumstanceStatusSavedId;
		}


		public Long getSpecialCircumstanceStatusApprovedId() {
			return specialCircumstanceStatusApprovedId;
		}


		public void setSpecialCircumstanceStatusApprovedId(Long specialCircumstanceStatusApprovedId) {
			this.specialCircumstanceStatusApprovedId = specialCircumstanceStatusApprovedId;
		}


		public Long getTestStatusInprogressTimedoutId() {
			return testStatusInprogressTimedoutId;
		}


		public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
			this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
		}


		public Long getScoringStatusCompletedId() {
			return scoringStatusCompletedId;
		}


		public void setScoringStatusCompletedId(Long scoringStatusCompletedId) {
			this.scoringStatusCompletedId = scoringStatusCompletedId;
		}


		public String getSkipStudentCalcForUncompletedStages() {
			return skipStudentCalcForUncompletedStages;
		}


		public void setSkipStudentCalcForUncompletedStages(String skipStudentCalcForUncompletedStages) {
			this.skipStudentCalcForUncompletedStages = skipStudentCalcForUncompletedStages;
		}
		
		
}
