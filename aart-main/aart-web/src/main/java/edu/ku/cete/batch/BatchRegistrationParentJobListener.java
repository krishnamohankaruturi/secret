package edu.ku.cete.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.service.BatchRegistrationService;

@Component("batchRegParentJobListener")
public class BatchRegistrationParentJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchRegistrationParentJobListener.class);
	
    @Autowired
    private BatchRegistrationService brService;
    
    private Instant startTime;
    
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
        JobParameters params = jobExecution.getJobParameters();
		jobExecution.getExecutionContext().put("batchUiJobId",  params.getLong("batchUiJobId"));
		logger.debug("<-- beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		Long batchUiJobId = (Long) jobExecution.getExecutionContext().get("batchUiJobId");
		if(batchUiJobId != null){
			BatchRegistration brMainJobRecord = new BatchRegistration();
			brMainJobRecord.setStatus(jobExecution.getStatus().name());
			brMainJobRecord.setId(batchUiJobId);
			brService.updateBatchRegistrationSelective(brMainJobRecord);
		}
		logger.debug("<-- afterJob");
	}

	public Instant getStartTime() {
		return startTime;
	}
	
	public Instant setStartTime(Instant startTime) {
		return this.startTime = startTime;
	}	
}
