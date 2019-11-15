package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchReportScoreCalcJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchReportScoreCalcJobListener.class);
	
    private Instant startTime;
    
	@Autowired
	private BatchReportProcessService batchReportProcessService;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
		startTime = new Instant();
		JobParameters params = jobExecution.getJobParameters();
		jobExecution.getExecutionContext().put("batchReportProcessId", params.getLong("reportProcessId"));
		logger.debug("<-- beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		Long reportProcessId = (Long) jobExecution.getExecutionContext().get("batchReportProcessId");
		logger.debug("Batch Report Process Finish job: "+ reportProcessId +", duration after reasons write: " 
					+ getDuration(new Interval(startTime, new Instant()).toPeriod()));
		
		
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
		
		logger.debug("<-- afterJob");
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
	
}
