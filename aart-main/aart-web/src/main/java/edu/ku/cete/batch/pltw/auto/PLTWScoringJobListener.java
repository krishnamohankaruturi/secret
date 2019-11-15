package edu.ku.cete.batch.pltw.auto;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
import org.springframework.security.core.context.SecurityContextHolder;

import com.ctc.wstx.util.StringUtil;

import edu.ku.cete.batch.BatchRegistrationJobListener;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.ScoringAPIReprocessService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.util.SourceTypeEnum;

public class PLTWScoringJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchRegistrationJobListener.class);
	
    @Autowired
    private BatchRegistrationService brService;
    
    @Autowired
    private AssessmentProgramService apService;
    
    @Autowired
    private AppConfigurationService appConfigService;
    
    @Autowired
	private ScoringAPIReprocessService scoringAPIReprocessService;
    
    private Instant startTime;
    
    private int errorFailedCount = 0;
    
    private int studentSuccessCount = 0;
    
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
		startTime = new Instant();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        int schoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
        
        JobParameters params = jobExecution.getJobParameters();
		BatchRegistration brRecord = new BatchRegistration();
		brRecord.setSubmissionDate(new Date());
		brRecord.setStatus("IN-PROGRESS");
		brRecord.setSuccessCount(0);
		brRecord.setFailedCount(0);
		SourceTypeEnum type = SourceTypeEnum.PLTWSCORINGNIGHTLY;
		if ("true".equalsIgnoreCase(params.getString("interimFlag"))) {
			type = SourceTypeEnum.PLTWSCORINGDAYTIME;
		}
		brRecord.setBatchTypeCode(type.getCode());
		if(null != params.getLong("assessmentProgramId") && params.getLong("assessmentProgramId") != 0) {
			brRecord.setAssessmentProgram(params.getLong("assessmentProgramId"));
			AssessmentProgram ap = apService.findByAssessmentProgramId(brRecord.getAssessmentProgram());
			jobExecution.getExecutionContext().put("assessmentProgram", ap);
		} else {
			AssessmentProgram ap = apService.findByAbbreviatedName(params.getString("assessmentProgramCode"));
			brRecord.setAssessmentProgram(ap.getId());
			jobExecution.getExecutionContext().put("assessmentProgram", ap);
		}
		brRecord.setCreatedDate(new Date());
		brRecord.setModifiedDate(new Date());
		brRecord.setCreatedUser(user.getId());
		
		jobExecution.getExecutionContext().put("user", user);
		jobExecution.getExecutionContext().put("schoolYear", schoolYear);
		jobExecution.getExecutionContext().put("batchRegistrationRecord", brRecord);
		jobExecution.getExecutionContext().put("batchRegistrationId", brRecord.getId());
		jobExecution.getExecutionContext().put("jobMessages",new CopyOnWriteArrayList<BatchRegistrationReason>());
		jobExecution.getExecutionContext().put("reprocessIds",new CopyOnWriteArrayList<Long>());
		
		Date lastRuntime = brService.getLatestSubmissionDateWithBatchTypeCode(type.getCode());
		jobExecution.getExecutionContext().put("jobLastSubmissionDate", lastRuntime);
		
		List<AppConfiguration> config = appConfigService.selectByAttributeTypeAndAssessmentProgramId("scoring-api-url", brRecord.getAssessmentProgram());
		if (CollectionUtils.isNotEmpty(config) && StringUtils.isNotBlank(config.get(0).getAttributeValue())) {
			jobExecution.getExecutionContext().put("scoringAPIURL", config.get(0).getAttributeValue());
		}
		
		config = appConfigService.selectByAttributeTypeAndAssessmentProgramId("scoring-api-key", brRecord.getAssessmentProgram());
		if (CollectionUtils.isNotEmpty(config) && StringUtils.isNotBlank(config.get(0).getAttributeValue())) {
			jobExecution.getExecutionContext().put("scoringAPIKey", config.get(0).getAttributeValue());
		}
		
		config = appConfigService.selectByAttributeTypeAndAssessmentProgramId("scoring-api-page-size", brRecord.getAssessmentProgram());
		if (CollectionUtils.isNotEmpty(config)) {
			String value = StringUtils.isBlank(config.get(0).getAttributeValue()) ? "200" : config.get(0).getAttributeValue();
			int pageSize = Integer.parseInt(value);
			jobExecution.getExecutionContext().put("scoringAPIPageSize", pageSize);
		}
		
		config = appConfigService.selectByAttributeTypeAndAssessmentProgramId("scoring-api-validate-enrollments", brRecord.getAssessmentProgram());
		if (CollectionUtils.isNotEmpty(config)) {
			jobExecution.getExecutionContext().put("validateEnrollments", "true".equalsIgnoreCase(config.get(0).getAttributeValue()));
		}
		
		brService.insertSelectiveBatchRegistration(brRecord);
		
		logger.debug("<-- beforeJob");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());
			
		int successCount = 0, failedCount = 0, writeSkipCount = 0;
		BatchRegistration brRecord = (BatchRegistration) jobExecution.getExecutionContext().get("batchRegistrationRecord");
		
		if(null != brRecord) {
			Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
			for(StepExecution sExecution: sExecutions) {
				writeSkipCount += sExecution.getWriteSkipCount();
				successCount += sExecution.getReadCount();
				failedCount += sExecution.getProcessSkipCount() + sExecution.getWriteSkipCount();
			}
			
			brRecord.setSuccessCount(successCount);
			brRecord.setFailedCount(failedCount + errorFailedCount);
			brRecord.setStatus(jobExecution.getStatus().name());
			brRecord.setModifiedDate(new Date());
			brService.updateBatchRegistrationSelective(brRecord);
						
			List<BatchRegistrationReason> jobMessages = (List<BatchRegistrationReason>) jobExecution.getExecutionContext().get("jobMessages");
			if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
				BatchRegistrationReason reason = new BatchRegistrationReason();
				reason.setBatchRegistrationId(brRecord.getId());
				for(Throwable t: jobExecution.getAllFailureExceptions()) {
					reason.setReason(t.getMessage());
					jobMessages.add(reason);
				}
			}
			
			for(BatchRegistrationReason r: jobMessages) {
				r.setBatchRegistrationId(brRecord.getId());
			}
			
			brService.insertReasons(jobMessages);
		}
		
		List<Long> reprocessIds = (List<Long>) jobExecution.getExecutionContext().get("reprocessIds");
		if (CollectionUtils.isNotEmpty(reprocessIds)) {
			final int listSize = reprocessIds.size();
			final int pageSize = 300; // update this many at a time, because doing a transaction for every one would be silly.
			final int pages = (listSize / pageSize) + (listSize % pageSize != 0 ? 1 : 0);
			for (int x = 0; x < pages; x++) {
				int lower = pageSize * x;
				int upper = pageSize * (x + 1);
				if (upper > listSize) {
					upper = listSize;
				}
				scoringAPIReprocessService.updateDateToNow(reprocessIds.subList(lower, upper));
			}
		}
		
		jobExecution.getExecutionContext().remove("jobMessages");
		jobExecution.getExecutionContext().remove("reprocessIds");
		logger.debug("***** successCount: "+successCount);
		logger.debug("***** failed/skippedCount: "+failedCount);
		logger.debug("***** writeSkipCount: "+writeSkipCount);
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", duration:" + duration);
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", duration after reasons write: " + getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}
	
	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
	
	public Instant getStartTime() {
		return startTime;
	}
	
	public Instant setStartTime(Instant startTime) {
		return this.startTime = startTime;
	}

	public int getErrorFailedCount() {
		return errorFailedCount;
	}

	public void setErrorFailedCount(int errorFailedCount) {
		this.errorFailedCount = errorFailedCount;
	}

	
}

