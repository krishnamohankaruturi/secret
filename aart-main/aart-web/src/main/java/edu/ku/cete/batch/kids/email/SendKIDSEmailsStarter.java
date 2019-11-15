package edu.ku.cete.batch.kids.email;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Component;

import edu.ku.cete.batch.BaseBatchProcessStarter;

@Component
public class SendKIDSEmailsStarter extends BaseBatchProcessStarter {

    private final static Log logger = LogFactory.getLog(SendKIDSEmailsStarter.class);
	
    @Resource
   	private Job sendKIDSEmailsJob;
    
	private String isScheduleOn;

	public Long run()
			throws Exception {
 	 	logger.debug("--> startSendKIDSEmails -- "+isScheduleOn);
		Long jobId = null;
		if(isScheduleOn.equalsIgnoreCase("ON")) {
	 		JobParametersBuilder builder = new JobParametersBuilder();
		 	builder.addDate("run date", new Date());
			jobId = startJob(sendKIDSEmailsJob, builder.toJobParameters()); 
		}
		logger.debug("<-- startSendKIDSEmails");
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

	public Job getSendKIDSEmailsJob() {
		return sendKIDSEmailsJob;
	}

	public void setSendKIDSEmailsJob(Job sendKIDSEmailsJob) {
		this.sendKIDSEmailsJob = sendKIDSEmailsJob;
	}
}
