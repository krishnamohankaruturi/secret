package edu.ku.cete.batch;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BaseBatchProcessStarter {
	private final static Log logger = LogFactory.getLog(BaseBatchProcessStarter.class);
	
	public abstract String getUploadType();
	
	@Resource
	@Qualifier("webJobLauncher")
	private JobLauncher jobLauncher;
	  
	@Resource
	private JobExplorer jobExplorer;
	
	@Autowired
	private JobRepository jobRepository;
	
	protected Long startJob(Job job, JobParameters params) throws Exception {
		JobExecution jobExecution = null;
		try {
			jobLauncher.run(job, params);
			jobExecution = jobRepository.getLastJobExecution(job.getName(), params);
			if(jobExecution != null){
				logger.debug(jobExecution.toString());
				return jobExecution.getId();
			}
			return null;
		} catch (JobExecutionAlreadyRunningException e) {
			logger.warn("JobExecutionAlreadyRunningException:", e);
			throw e;
		} catch (JobRestartException e) {
			logger.error("JobRestartException:",e);
			throw e;
		} catch (JobInstanceAlreadyCompleteException e) {
			logger.error("JobInstanceAlreadyCompleteException:",e);
			throw e;
		} catch (JobParametersInvalidException e) {
			logger.error("JobParametersInvalidException:",e);
			throw e;
		}
	}	
	
	public String getJobStatus(Long jobId) {
		JobExecution jobExecution = jobExplorer.getJobExecution(jobId);
		if(jobExecution == null) {
			return BatchStatus.UNKNOWN.name();
		}
		return jobExecution.getStatus().name();
	}
}
