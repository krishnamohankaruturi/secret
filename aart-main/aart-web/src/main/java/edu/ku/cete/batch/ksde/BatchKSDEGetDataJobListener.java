package edu.ku.cete.batch.ksde;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class BatchKSDEGetDataJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchKSDEGetDataJobListener.class);
	
	@Override
	public void beforeJob(JobExecution jobExecution) { 
		logger.debug("--> beforeJob");

		logger.debug("<-- beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) { 
		logger.debug("--> afterJob");
 		 
	 	logger.debug("<-- afterJob");
	}

	
}
