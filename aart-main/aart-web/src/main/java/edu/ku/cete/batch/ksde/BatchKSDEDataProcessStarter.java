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
public class BatchKSDEDataProcessStarter extends BaseBatchProcessStarter {

    private final static Log logger = LogFactory.getLog(BatchKSDEDataProcessStarter.class);
	
    @Resource
   	private Job batchKSDEDataProcessJob;
    
	private String isScheduleOn;

	public Long run()
			throws Exception {
 	 	logger.debug("--> startBatchKSDEDataProcess");
		Long jobId = null;
		if(isScheduleOn.equalsIgnoreCase("ON")) {
	 		JobParametersBuilder builder = new JobParametersBuilder();
		 	builder.addDate("run date", new Date());
			jobId = startJob(batchKSDEDataProcessJob, builder.toJobParameters()); 
		}
		logger.debug("<-- startBatchKSDEDataProcess");
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

	public Job getBatchKSDEDataProcessJob() {
		return batchKSDEDataProcessJob;
	}

	public void setBatchKSDEDataProcessJob(Job batchKSDEDataProcessJob) {
		this.batchKSDEDataProcessJob = batchKSDEDataProcessJob;
	}
}
