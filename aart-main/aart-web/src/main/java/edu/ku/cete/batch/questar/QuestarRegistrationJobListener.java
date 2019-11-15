package edu.ku.cete.batch.questar;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.ku.cete.batch.BatchRegistrationJobListener;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.QuestarRegistrationReason;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.QuestarService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.SourceTypeEnum;

public class QuestarRegistrationJobListener extends BatchRegistrationJobListener {
	final static Log logger = LogFactory.getLog(QuestarRegistrationJobListener.class);
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private AssessmentProgramService apService;
	
	@Autowired
	private TestingProgramService tpService;
	
	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
    private BatchRegistrationService brService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuestarService questarService;
	
	@Value("${questar.batch.registration.assessmentprogram}")
	String assessmentProgramCode;
	
	@Value("${questar.batch.registration.organization}")
	String orgIdentifier;
	
	@Value("${questar.batch.registration.testingprogram}")
	String testingProgramCode;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {		
		super.setStartTime(new Instant());

		JobParameters params = jobExecution.getJobParameters();
		jobExecution.getExecutionContext().put("operationalTestWindowId", params.getLong("operationalTestWindowId"));
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        //int schoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
        int schoolYear = (int) user.getContractingOrganization().getReportYear();
		BatchRegistration brRecord = new BatchRegistration();
		brRecord.setSubmissionDate(new Date());
		brRecord.setStatus("IN-PROGRESS");
		brRecord.setSuccessCount(0);
		brRecord.setFailedCount(0);
		brRecord.setBatchTypeCode(SourceTypeEnum.QUESTARPROCESS.getCode());

		AssessmentProgram ap = apService.findByAbbreviatedName(assessmentProgramCode);
		Organization org = orgService.getByDisplayIdentifier(orgIdentifier).get(0);
		
		// if there are ever multiple assessmentprograms/testingprograms that this is run for, a partitioner would do well here
		// as it stands now, having this run on only one testing program and one assessment program guarantees only one assessment record
		TestingProgram testingProgram = tpService.getByAssessmentProgIdAndTestingProgAbbr(ap.getId(), testingProgramCode).get(0);
		Assessment assessment = getAssessments(ap.getId(), testingProgram.getId()).get(0);
		
		if(null != ap) {
			brRecord.setAssessmentProgram(ap.getId());
		}
		if(null != testingProgram) {
			brRecord.setTestingProgram(testingProgram.getId());
		}
		if(null != assessment) {
			brRecord.setAssessment(assessment.getId());
		}
		
		brRecord.setCreatedDate(new Date());
		brRecord.setModifiedDate(new Date());
		brRecord.setCreatedUser(user.getId());
		brService.insertSelectiveBatchRegistration(brRecord);
				
		user = userService.getByUserName("questaruser");
		
		jobExecution.getExecutionContext().put("user", user);
		jobExecution.getExecutionContext().put("assessmentProgram", ap);
		jobExecution.getExecutionContext().put("org", org);
		jobExecution.getExecutionContext().put("testingProgram", testingProgram);
		jobExecution.getExecutionContext().put("assessment", assessment);
		jobExecution.getExecutionContext().put("schoolYear", schoolYear);
		jobExecution.getExecutionContext().put("batchRegistrationRecord", brRecord);
		jobExecution.getExecutionContext().put("batchRegistrationId", brRecord.getId());
		jobExecution.getExecutionContext().put("jobMessages",new CopyOnWriteArrayList<QuestarRegistrationReason>());		
		
		questarService.updateStagingRecordsToBeProcessed();
		
		logger.debug("<-- beforeJob");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void afterJob(JobExecution jobExecution) {
		questarService.updateStagingRecordsToProcessed();
		
		String duration = getDuration(new Interval(getStartTime(), new Instant()).toPeriod());
		
		
		int successCount = 0, failedCount = 0;
		BatchRegistration brRecord = (BatchRegistration) jobExecution.getExecutionContext().get("batchRegistrationRecord");
		
		if(null != brRecord) {
			Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
			for(StepExecution sExecution: sExecutions) {
				if(sExecution.getStepName().startsWith("questar")) {
					successCount = sExecution.getWriteCount();
					failedCount = sExecution.getProcessSkipCount();
					break;
				}
			}
			
			brRecord.setSuccessCount(successCount);
			brRecord.setFailedCount(failedCount);
			brRecord.setStatus(jobExecution.getStatus().name());
			brRecord.setModifiedDate(new Date());
			brService.updateBatchRegistrationSelective(brRecord);
			
			List<QuestarRegistrationReason> jobMessages = (List<QuestarRegistrationReason>) jobExecution.getExecutionContext().get("jobMessages");
			if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
				QuestarRegistrationReason reason = new QuestarRegistrationReason();
				reason.setBatchRegistrationId(brRecord.getId());
				for(Throwable t: jobExecution.getAllFailureExceptions()) {
					reason.setReason(t.getMessage());
					jobMessages.add(reason);
				}
			}
			
			for(QuestarRegistrationReason r: jobMessages) {
				r.setBatchRegistrationId(brRecord.getId());
			}
			
			brService.insertQuestarReasons(jobMessages);
		}
		
		jobExecution.getExecutionContext().remove("jobMessages");
		logger.debug("***** successCount: "+successCount);
		logger.debug("***** failed/skippedCount: "+failedCount);
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", duration:" + duration);
		logger.debug("Finish job: "+jobExecution.getExecutionContext().get("batchRegistrationId") 
				+", duration after reasons write: " + getDuration(new Interval(getStartTime(), new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}
	
	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
	
	private List<Assessment> getAssessments(Long assessmentProgramId, Long testingProgramId) {
		return assessmentService.getAssessmentsForAutoRegistration(testingProgramId, assessmentProgramId);
	}	
}