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
import edu.ku.cete.util.StageEnum;

public class KelpaBatchStudentReportGetTests implements ItemProcessor<StudentReport,Object>
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
		if(assessmentProgramCode.equalsIgnoreCase("KELPA2")){
			testsStatusIds.add(testsInprogressStatusId);
			testsStatusIds.add(testStatusInprogressTimedoutId);
			testsStatusIds.add(testStatusPendingId);
			testsStatusIds.add(testStatusUnusedId);
		}
		
		specialCircumstanceStatusIds.add(specialCircumstanceStatusSavedId);
		specialCircumstanceStatusIds.add(specialCircumstanceStatusApprovedId);
		
		studentReport.setEnrollments(enrollmentService.findReportEnrollments(studentReport.getStudentId(), contractingOrganization.getId(),
				studentReport.getSchoolYear(), rawScaleExternalTestIds, testsStatusIds));
		
		studentReport.setEnrollmentId(studentReport.getEnrollments().get(0).getId());
		studentReport.setAttendanceSchoolId(studentReport.getEnrollments().get(0).getAttendanceSchoolId());
		studentReport.setDistrictId(studentReport.getEnrollments().get(0).getAttendanceSchoolDistrictId());
		studentReport.setStateId(contractingOrganization.getId());
		
		List<Long> enrollmentIds = new ArrayList<Long>();
		for (Enrollment e : studentReport.getEnrollments()) {
			enrollmentIds.add(e.getId());
		}
		
		List<StudentReportTestResponses> studentTestsScore = testService.getKelpaTestsScoreByStudentIdExternalTestIds(studentReport.getStudentId(),
				rawScaleExternalTestIds, enrollmentIds, testsStatusIds, specialCircumstanceStatusIds, contractingOrganization.getId());
		if(CollectionUtils.isEmpty(studentTestsScore)){
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "No Completed/InProgress tests/responses found for student: "+studentReport.getStudentId() ;
				reportProcessReason.setReason(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}
			
		List<StudentsResponses> readingCompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> readingOtherstudentsResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> listeningCompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> listeningOtherstudentsResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> speakingCompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> speakingOtherstudentsResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> writingCompleteAndInProgressStudentResponses = new ArrayList<StudentsResponses>();
		List<StudentsResponses> writingOtherstudentsResponses = new ArrayList<StudentsResponses>();
		
		for(StudentReportTestResponses studentReportTestResponse : studentTestsScore){
			if(StageEnum.READING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							readingCompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						readingOtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(readingCompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(readingCompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(readingOtherstudentsResponses);
				}

			}
			if(StageEnum.LISTENING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							listeningCompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						listeningOtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(listeningCompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(listeningCompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(listeningOtherstudentsResponses);
				}

			}
			
			if(StageEnum.SPEAKING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							speakingCompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						speakingOtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(speakingCompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(speakingCompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(speakingOtherstudentsResponses);
				}

			}
			
			if(StageEnum.WRITING.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
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
							writingCompleteAndInProgressStudentResponses.add(studentResponse);
						}
					}else{
						writingOtherstudentsResponses.add(studentResponse);
					}
				}
				
				if(writingCompleteAndInProgressStudentResponses.size() != 0){
					studentReportTestResponse.setStudentsResponses(writingCompleteAndInProgressStudentResponses);
				}else{
					studentReportTestResponse.setStudentsResponses(writingOtherstudentsResponses);
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
		
}
