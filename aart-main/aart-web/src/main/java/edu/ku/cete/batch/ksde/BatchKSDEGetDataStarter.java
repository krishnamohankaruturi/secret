package edu.ku.cete.batch.ksde;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Component;

import edu.ku.cete.batch.BaseBatchProcessStarter;

@Component
public class BatchKSDEGetDataStarter extends BaseBatchProcessStarter {

	private final static Log logger = LogFactory.getLog(BatchKSDEGetDataStarter.class);

	@Resource
	private Job batchGetDataFromKSDEJob;

	private String isScheduleOn;

	public Long run() throws Exception {
		logger.debug("--> startBatchGetDataFromKSDE");
		Long jobId = null;
		if (isScheduleOn.equalsIgnoreCase("ON")) {
			JobParametersBuilder builder = new JobParametersBuilder();

			// need unique job parameter to rerun the completed job
			builder.addDate("run date", new Date());

			// All
			jobId = startJob(batchGetDataFromKSDEJob, builder.toJobParameters());
		}
		logger.debug("<-- startBatchGetDataFromKSDE");
		return jobId;
	}

	@Override
	public String getUploadType() {
		return null;
	}

	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	public Job getBatchGetDataFromKSDEJob() {
		return batchGetDataFromKSDEJob;
	}

	public void setBatchGetDataFromKSDEJob(Job batchGetDataFromKSDEJob) {
		this.batchGetDataFromKSDEJob = batchGetDataFromKSDEJob;
	}
}
