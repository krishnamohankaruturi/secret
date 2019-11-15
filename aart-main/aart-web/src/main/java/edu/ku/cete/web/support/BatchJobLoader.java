package edu.ku.cete.web.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.MethodInvokingRunnable;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.BatchJobSchedule;
import edu.ku.cete.service.BatchRegistrationService;

@Component
public class BatchJobLoader implements ApplicationListener<ContextRefreshedEvent> {

	private final static Log LOGGER = LogFactory.getLog(BatchJobLoader.class);
	
	@Autowired
	private BatchRegistrationService batchRegistrationService;
	
    @Value("${server.name}")
    private String server;
    
    private Map<Long, Map<BatchJobSchedule,ScheduledFuture<?>>> scheduledJobs= null;
    
    public void onApplicationEvent(ContextRefreshedEvent event) {
    	LOGGER.info("--> onApplicationEvent");
    	ApplicationContext appContext = event.getApplicationContext();
    	if(appContext.getParent() == null) {
	    	try {
	    		if (scheduledJobs == null) {
	    			scheduledJobs = new HashMap<Long, Map<BatchJobSchedule,ScheduledFuture<?>>>();
	    		}
	    		//initJobs(appContext);
	    	} catch (Exception e) {
	    		LOGGER.error("Initialization of scheduled job failed.");
	    		LOGGER.error("BatchJobLoader: ", e);
			}
    	}
    	LOGGER.info("<-- onApplicationEvent");
    }

	public void initJobs(ApplicationContext appContext) throws Exception {
		List<BatchJobSchedule> jobSchedules = batchRegistrationService.getBatchJobSchedules(server);
		if(CollectionUtils.isNotEmpty(jobSchedules)) {
			TaskScheduler scheduler = appContext.getBean(TaskScheduler.class);
			
			for(BatchJobSchedule jobSchedule: jobSchedules) {
				LOGGER.debug("scheduledJobs.containsKey(jobSchedule.getId()) :: "+scheduledJobs.containsKey(jobSchedule.getId()));
				//set schedule on/off
				PropertyUtils.setSimpleProperty(appContext.getBean(jobSchedule.getJobRefName()), "isScheduleOn", jobSchedule.getScheduled() ? "ON":"OFF");
				if(jobSchedule.getScheduled().booleanValue() && scheduledJobs.get(jobSchedule.getId()) == null) {
					LOGGER.debug("Previously no job exists. Scheduling new one.");
					scheduleJob(appContext, scheduler, jobSchedule);
				} else if(jobSchedule.getScheduled().booleanValue() && scheduledJobs.get(jobSchedule.getId()) != null) {
					LOGGER.debug("Previousjob exists. checking for schedule time.");
					for(BatchJobSchedule prevSchedule: scheduledJobs.get(jobSchedule.getId()).keySet()) {
						if(prevSchedule.getId().equals(jobSchedule.getId()) &&
								!prevSchedule.getCronExpression().equals(jobSchedule.getCronExpression())) {
							scheduledJobs.get(jobSchedule.getId()).get(prevSchedule).cancel(false);
							scheduledJobs.remove(jobSchedule.getId());
							scheduleJob(appContext, scheduler, jobSchedule);
							break;
						}
					}
				} else if(scheduledJobs.get(jobSchedule.getId()) != null) {
					LOGGER.debug("Previousjob exists. cancelling the job.");
					for(BatchJobSchedule prevSchedule: scheduledJobs.get(jobSchedule.getId()).keySet()) {
						if(prevSchedule.getId().equals(jobSchedule.getId())) {
							scheduledJobs.get(jobSchedule.getId()).get(prevSchedule).cancel(false);
							scheduledJobs.remove(jobSchedule.getId());
							break;
						}
					}
				}
			}
		} else {
			LOGGER.info("No jobs found to schedule on server: "+server);
		}
		
		//logScheduledJobs();
	}

	private void scheduleJob(ApplicationContext appContext,
			TaskScheduler scheduler, BatchJobSchedule jobSchedule)
			throws ClassNotFoundException, NoSuchMethodException {
		MethodInvokingRunnable jobDetail = new MethodInvokingRunnable();
		jobDetail.setTargetObject(appContext.getBean(jobSchedule.getJobRefName()));
		jobDetail.setTargetMethod(jobSchedule.getInitMethod());
		jobDetail.afterPropertiesSet();
		
		TimeZone tz = TimeZone.getTimeZone("US/Central");
		CronTrigger cronTrigger = new CronTrigger(jobSchedule.getCronExpression(), tz);
		Date nextExecutionTime = cronTrigger.nextExecutionTime(new TriggerContext() {
			@Override public Date lastScheduledExecutionTime() {return null;}
			@Override public Date lastCompletionTime() {return null;}
			@Override public Date lastActualExecutionTime() {return null;}
		});
		ScheduledFuture<?> jobFuture = scheduler.schedule(jobDetail, cronTrigger);
		Map<BatchJobSchedule,ScheduledFuture<?>> scheduledJob = new HashMap<BatchJobSchedule,ScheduledFuture<?>>();
		scheduledJob.put(jobSchedule, jobFuture);
		scheduledJobs.put(jobSchedule.getId(), scheduledJob);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a z");
		sdf.setTimeZone(tz);
		LOGGER.info("Scheduled " + jobSchedule.getJobName() + " at cron \"" + jobSchedule.getCronExpression() + "\" on " + jobSchedule.getAllowedServer() +
				" -- next execution scheduled for " + sdf.format(nextExecutionTime));
	}
	
	private void logScheduledJobs() {
		String jobs = "";
		for (Long scheduleId : scheduledJobs.keySet()) {
			for (BatchJobSchedule job : scheduledJobs.get(scheduleId).keySet()) {
				if (jobs.length() > 0) jobs += ", ";
				jobs += "{" + job.getJobName() + " at " + job.getCronExpression() + " on " + job.getAllowedServer() + "}";
			}
		}
		LOGGER.info("Jobs scheduled: " + jobs);
	}
}
