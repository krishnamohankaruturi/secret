package edu.ku.cete.batch.scoringassignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.ku.cete.batch.BatchRegistrationJobListener;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.report.domain.BatchRegisteredScoringAssignments;

/* sudhansu.b
 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
 */	
public class BatchReportProcessJobListenerForScoring implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchRegistrationJobListener.class);
	
    @Autowired
    private BatchRegistrationService brService;
    
    @Autowired
    private StudentsTestsService studentsTestsService;
    
    private Instant startTime;
    
    private int errorFailedCount = 0;
    
    private int studentSuccessCount = 0;
    
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
		startTime = new Instant();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        int schoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
        JobParameters params = jobExecution.getJobParameters();
		BatchRegistration brRecord = new BatchRegistration();
		brRecord.setSubmissionDate(new Date());
		brRecord.setStatus("IN-PROGRESS");
		brRecord.setSuccessCount(0);
		brRecord.setFailedCount(0);
		brRecord.setBatchTypeCode(SourceTypeEnum.AUTOSCORING.getCode());
		if(null != params.getLong("assessmentProgramId") && params.getLong("assessmentProgramId") != 0) {
			brRecord.setAssessmentProgram(params.getLong("assessmentProgramId"));
		}
		brRecord.setCreatedDate(new Date());
		brRecord.setModifiedDate(new Date());
		brRecord.setCreatedUser(user.getId());
		brService.insertSelectiveBatchRegistration(brRecord);
		jobExecution.getExecutionContext().put("user", user);
		jobExecution.getExecutionContext().put("schoolYear", schoolYear);
		jobExecution.getExecutionContext().put("batchRegistrationRecord", brRecord);
		jobExecution.getExecutionContext().put("batchRegistrationId", brRecord.getId());
		jobExecution.getExecutionContext().put("jobMessages",new CopyOnWriteArrayList<BatchRegistrationReason>());
		
		logger.debug("<-- beforeJob");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());
			
		int successCount = 0, failedCount = 0, writeSkipCount = 0;
		BatchRegistration brRecord = (BatchRegistration) jobExecution.getExecutionContext().get("batchRegistrationRecord");
		
		String dupulicateTestName = (String)jobExecution.getExecutionContext().get("dupulicateTestName");
		
		List<Long> testSessionIds = (List<Long>) jobExecution.getExecutionContext().get("testSessionIds");
		Set<Long> successStudents = (Set<Long>) jobExecution.getExecutionContext().get("successStudents");
		Set<Long> failedStudents = (Set<Long>) jobExecution.getExecutionContext().get("failedStudents");
		Set<Long> mappedStudents = (Set<Long>) jobExecution.getExecutionContext().get("mappedStudents");
		
		List<StudentsTests> studentTestsList = new ArrayList<StudentsTests>();
		
		if(testSessionIds.size() >0){
		 studentTestsList = studentsTestsService.getStudentsWithRostersWithEnrollmentAutoScoring(testSessionIds);
		}
		
		errorFailedCount = 0;
		studentSuccessCount = successStudents.size();
		
		for (StudentsTests StudentsTest : studentTestsList) {
			if(StudentsTest.getEnrollmentId()==null){
				writeReason("No Enrollment found for student",StudentsTest.getTestSessionId(),StudentsTest.getStudentId(),brRecord.getId(),jobExecution);
				errorFailedCount++;
			}
			else if(StudentsTest.getEnrollmentId()!=null && StudentsTest.getEnrollmentRosterId()==null){
				writeReason("Enrollment not mapped for student",StudentsTest.getTestSessionId(),StudentsTest.getStudentId(),brRecord.getId(),jobExecution);
				errorFailedCount++;
			}
			else if(StudentsTest.getEnrollmentId()!=null && StudentsTest.getEnrollmentRosterId()!=null && StudentsTest.getRosterId()==null){
				writeReason("No Roster found for student",StudentsTest.getTestSessionId(),StudentsTest.getStudentId(),brRecord.getId(),jobExecution);
				errorFailedCount++;
			}
		}
		
		if(dupulicateTestName != null){
		  for (Long id : failedStudents) {
			  writeReason("Same Assignment Name Exists : Due to same rostername Problem ",null,id,brRecord.getId(),jobExecution);
			  errorFailedCount++;
		   }
		}
		
		for (Long studentid : mappedStudents) {
			 writeReason("Student already having a " +
			 		"" +
			 		"" +
			 		"scoring assginmet with the proctor",null,studentid,brRecord.getId(),jobExecution);
			 errorFailedCount++;
		}
			
		
		
		if(null != brRecord) {
			Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
			for(StepExecution sExecution: sExecutions) {
				if(sExecution.getStepName().startsWith("partitioned")) {
					writeSkipCount = sExecution.getWriteSkipCount();
					successCount = studentSuccessCount;
					//failedCount = sExecution.getProcessSkipCount() + writeSkipCount;
					break;
				}
			}
			
			brRecord.setSuccessCount(successCount);
			brRecord.setFailedCount(failedCount + errorFailedCount);
			brRecord.setStatus(jobExecution.getStatus().name());
			brRecord.setModifiedDate(new Date());
			brService.updateBatchRegistrationSelective(brRecord);
						
			List<BatchRegistrationReason> jobMessages = (List<BatchRegistrationReason>) jobExecution.getExecutionContext().get("jobMessages");
			if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
				BatchRegistrationReason reason = new BatchRegistrationReason();
				reason.setBatchRegistrationId(brRecord.getId());
				for(Throwable t: jobExecution.getAllFailureExceptions()) {
					reason.setReason(t.getMessage());
					jobMessages.add(reason);
				}
			}
			
			for(BatchRegistrationReason r: jobMessages) {
				r.setBatchRegistrationId(brRecord.getId());
			}
			
			brService.insertReasons(jobMessages);
		}
		
		//insert scoringAssessments created in batchregisteredscoringassignment table
		
		List<BatchRegisteredScoringAssignments> batchRegisteredScoringAssignments = (List<BatchRegisteredScoringAssignments>) jobExecution.getExecutionContext().get("batchScoringAssignments");
		if(batchRegisteredScoringAssignments!=null && !batchRegisteredScoringAssignments.isEmpty() && batchRegisteredScoringAssignments.size()>0){
			brService.insertBatchRegisteredScoringAssignments(batchRegisteredScoringAssignments);		
		}		
		
		jobExecution.getExecutionContext().remove("jobMessages");
		logger.debug("***** successCount: "+successCount);
		logger.debug("***** failed/skippedCount: "+failedCount);
		logger.debug("***** writeSkipCount: "+writeSkipCount);
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", duration:" + duration);
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", duration after reasons write: " + getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}


	@SuppressWarnings("unchecked")
	private void writeReason(String msg,Long testSessionId,Long studentId,Long batchRegistrationId,JobExecution jobExecution) {
		logger.debug(msg);		
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setTestSessionId(testSessionId);
		brReason.setStudentId(studentId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) jobExecution.getExecutionContext().get("jobMessages")).add(brReason);
	}
	
	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
	
	public Instant getStartTime() {
		return startTime;
	}
	
	public Instant setStartTime(Instant startTime) {
		return this.startTime = startTime;
	}

	public int getErrorFailedCount() {
		return errorFailedCount;
	}

	public void setErrorFailedCount(int errorFailedCount) {
		this.errorFailedCount = errorFailedCount;
	}

	
}

