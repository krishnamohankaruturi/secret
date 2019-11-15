package edu.ku.cete.batch.support;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;

public class BatchRepositoryCleanupJob {

	final static Log logger = LogFactory.getLog(BatchRepositoryCleanupJob.class);
	
	private MapJobRepositoryFactoryBean repositoryFactory;
	
	public void setRepositoryFactory(MapJobRepositoryFactoryBean repositoryFactory) {
		this.repositoryFactory = repositoryFactory;
	}
	
	public void run() {
		logger.debug("--> run");
		int runningJobsCount = 0;
		
		List<String> jobNames = repositoryFactory.getJobInstanceDao().getJobNames();

		logger.debug("all job names: "+jobNames);
		for(String jobName: jobNames) {
			runningJobsCount = repositoryFactory.getJobExecutionDao().findRunningJobExecutions(jobName).size();
			if(runningJobsCount > 0) {
				break;
			}
		}
		logger.debug("runningJobsCount: "+runningJobsCount);
		//clear all
		if(runningJobsCount == 0) {			
			repositoryFactory.clear();
		}
		logger.debug("<-- run");
	}
}
