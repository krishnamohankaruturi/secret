/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

import edu.ku.cete.domain.ReportProcess;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 14, 2017 4:24:49 PM
 */
public class PredictiveReportProcessJobListener implements JobExecutionListener {

	final static Log logger = LogFactory.getLog(PredictiveReportProcessJobListener.class);
	
	private Instant startTime;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob Predictive report process");
		startTime = new Instant();
		JobParameters params = jobExecution.getJobParameters();
		jobExecution.getExecutionContext().put("batchReportProcessId", params.getLong("reportProcessId"));
		ReportProcess reportProcessRecord = new ReportProcess();
		reportProcessRecord.setStatus(jobExecution.getStatus().name());
		reportProcessRecord.setId(params.getLong("reportProcessId"));
		reportProcessRecord.setModifiedDate(new Date());
		batchReportProcessService.updateByPrimaryKeySelectiveBatchReportProcess(reportProcessRecord);
		logger.debug("<-- beforeJob Prodictive report process");
	}


	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob Predictive report process");
		Long reportProcessId = (Long) jobExecution.getExecutionContext().get("batchReportProcessId");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());		
		
		int successCount = 0, failedCount = 0;		
		
		Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
		String stepName = StringUtils.EMPTY;
		for(StepExecution sExecution: sExecutions) {
				successCount = sExecution.getWriteCount();
				failedCount = sExecution.getProcessSkipCount();
				stepName = sExecution.getStepName();
				break;
		}
		
		logger.info("Predictive Batch Report Process Finish job: " + stepName
				   + ", Job ID: " + reportProcessId 
				   +", duration before reasons write: " + duration);
		
		ReportProcess reportProcessRecord = new ReportProcess();
		reportProcessRecord.setStatus(jobExecution.getStatus().name());
		reportProcessRecord.setId(reportProcessId);
		reportProcessRecord.setFailedCount(failedCount);
		reportProcessRecord.setSuccessCount(successCount);
		reportProcessRecord.setModifiedDate(new Date());
		batchReportProcessService.updateByPrimaryKeySelectiveBatchReportProcess(reportProcessRecord);
		
		 
		List<ReportProcessReason> errorMessages = new ArrayList<ReportProcessReason>();
		if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
			ReportProcessReason buProcessReason = new ReportProcessReason();
			buProcessReason.setReportProcessId(reportProcessId);
			for(Throwable t: jobExecution.getAllFailureExceptions()) {
				buProcessReason.setReason(t.getMessage());
				buProcessReason.setReportProcessId(reportProcessId);
				errorMessages.add(buProcessReason);
			}
		}
		
		if(CollectionUtils.isNotEmpty(errorMessages)){
			batchReportProcessService.insertSelectiveReportProcessReasons(errorMessages);
		}
		
		logger.info("Predictive Batch Report Process Finish job: " + stepName 
				    + ", Job ID: " + reportProcessId 
				    +", duration after reasons write: " 
					+ getDuration(new Interval(startTime, new Instant()).toPeriod()));
		
		logger.debug("<-- afterJob Predictive report process");
	}
	
	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}

}
