package edu.ku.cete.batch.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.web.support.BatchJobLoader;

public class BatchOnFlagCheckJob {

	private final static Log LOGGER = LogFactory.getLog(BatchOnFlagCheckJob.class);
	
	@Autowired 
	private ApplicationContext appContext;
	
	@Autowired
	private BatchRegistrationService batchRegistrationService;
	
	@Autowired
	private BatchJobLoader batchJobLoader;
	
    @Value("${server.name}")
    private String server;
	
	public void run() {
		LOGGER.debug("--> run");
		//set schedule on/off
		try {
			batchJobLoader.initJobs(appContext);
		} catch (Exception e) {
			LOGGER.warn("Initialization of scheduled job failed.");
    		LOGGER.warn("BatchOnFlagCheckJob: ", e);
		}
		LOGGER.debug("<-- run");
	}
}
