package edu.ku.cete.batch.dlm.st;

import java.util.Collection;
import java.util.Date;
import java.util.List;
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
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.util.SourceTypeEnum;

@Component("studentTrackerJobListener")
public class StudentTrackerJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(StudentTrackerJobListener.class);
	
    @Autowired
    private BatchRegistrationService brService;
    
    private Instant startTime;
    
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
		startTime = new Instant();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        JobParameters params = jobExecution.getJobParameters();
		BatchRegistration brRecord = new BatchRegistration();
		brRecord.setSubmissionDate(new Date());
		brRecord.setStatus("IN-PROGRESS");
		brRecord.setSuccessCount(0);
		brRecord.setFailedCount(0);
		brRecord.setBatchTypeCode(SourceTypeEnum.TRACKER.name());
		brRecord.setCreatedDate(new Date());
		brRecord.setModifiedDate(new Date());
		brRecord.setCreatedUser(user.getId());
		if(null != params.getLong("operationalTestWindowId") && params.getLong("operationalTestWindowId") != 0) {
			brRecord.setOperationalTestWindowId(params.getLong("operationalTestWindowId"));
		}
		brService.insertSelectiveBatchRegistration(brRecord);
		jobExecution.getExecutionContext().put("user", user);
		jobExecution.getExecutionContext().put("batchRegistrationRecord", brRecord);
		jobExecution.getExecutionContext().put("batchRegistrationId", brRecord.getId());
		jobExecution.getExecutionContext().put("reasons",new CopyOnWriteArrayList<BatchRegistrationReason>());
		
		logger.debug("<-- beforeJob");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", duration:" + getDuration(new Interval(startTime, new Instant()).toPeriod()));

		int successCount = 0, failedCount = 0;
		BatchRegistration brRecord = (BatchRegistration) jobExecution.getExecutionContext().get("batchRegistrationRecord");
		List<BatchRegistrationReason> jobMessages = (List<BatchRegistrationReason>) jobExecution.getExecutionContext().get("reasons");
		
		if(null != brRecord) {
			Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
			for(StepExecution sExecution: sExecutions) {
				if(sExecution.getStepName().startsWith("partitioned")) {
					successCount = sExecution.getWriteCount();
					failedCount = sExecution.getProcessSkipCount();
					if(sExecution.getReadCount() == 0) {
						BatchRegistrationReason reason = new BatchRegistrationReason();
						reason.setBatchRegistrationId(brRecord.getId());
						reason.setReason("No UNTRACKED students to process.");
						jobMessages.add(reason);
					}
					break;
				}
			}
			
			brRecord.setSuccessCount(successCount);
			brRecord.setFailedCount(failedCount);
			brRecord.setStatus(jobExecution.getStatus().name());
			brRecord.setModifiedDate(new Date());
			brService.updateBatchRegistrationSelective(brRecord);
			
			if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
				BatchRegistrationReason reason = new BatchRegistrationReason();
				reason.setBatchRegistrationId(brRecord.getId());
				for(Throwable t: jobExecution.getAllFailureExceptions()) {
					reason.setReason(t.getMessage());
					jobMessages.add(reason);
				}
			}
			for(BatchRegistrationReason reason: jobMessages){
				reason.setBatchRegistrationId(brRecord.getId());
			}
			brService.insertReasons(jobMessages);
		}
		jobExecution.getExecutionContext().remove("reasons");
		logger.debug("***** successCount: "+successCount);
		logger.debug("***** failed/skippedCount: "+failedCount);
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", durationafter reasons write: " + getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
}
