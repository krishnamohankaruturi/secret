package edu.ku.cete.batch;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import edu.ku.cete.domain.TestEnrollmentMethod;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.UserService;

public class ScheduledJobLauncher {

	private final static Log logger = LogFactory.getLog(ScheduledJobLauncher.class);
	
	@Autowired
	@Qualifier("scheduledJobLauncher")
	private JobLauncher jobLauncher;

    @Value("${wsAdminUserName}")
    private String wsAdminUserName;
    
	@Autowired
	private UserService userService;
	
	@Autowired
    private AssessmentProgramService assessmentProgramService;
	
	@Autowired
    private TestingProgramService testingProgramService;	
	@Autowired
	private BatchRegistrationService brService;
	
	Job job;
	String assesmentProgramCode;
	String isScheduleOn;
	String testingProgramAbbr;
	String enrollmentMethod;
	String interimFlag;
	public Job getJob() {
		return job;
	}

	public void setJob(Job bJob) {
		this.job = bJob;
	}

	public String getAssesmentProgramCode() {
		return assesmentProgramCode;
	}

	public void setAssesmentProgramCode(String assesmentProgramCode) {
		this.assesmentProgramCode = assesmentProgramCode;
	}
    
	public String getTestingProgramAbbr() {
		return testingProgramAbbr;
	}

	public void setTestingProgramAbbr(String testingProgramAbbr) {
		this.testingProgramAbbr = testingProgramAbbr;
	}
	
	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public String getInterimFlag() {
		return interimFlag;
	}

	public void setInterimFlag(String interimFlag) {
		this.interimFlag = interimFlag;
	}

	public void run() {
		if(isScheduleOn.equalsIgnoreCase("ON")) {
			logger.debug("--> run - Job: " + job.getName());
			try {
				logger.debug("initializing TechUser");
				initializedTechUser();
				AssessmentProgram assessmentProgram = assessmentProgramService.findByAbbreviatedName(assesmentProgramCode);
				if(assessmentProgram != null){
					JobParametersBuilder builder = new JobParametersBuilder();
					builder.addString("assessmentProgramCode", assessmentProgram.getAbbreviatedname());
					builder.addLong("assessmentProgramId", assessmentProgram.getId());
					if(testingProgramAbbr != null){
						List<TestingProgram> testingProgram = testingProgramService.getByAssessmentProgIdAndTestingProgAbbr(assessmentProgram.getId(), testingProgramAbbr);
						builder.addLong("testingProgramId", testingProgram.get(0).getId());
					}else{
						builder.addLong("testingProgramId", null);
					}
					if(enrollmentMethod != null){
						builder.addString("enrollmentMethod", enrollmentMethod);

						TestEnrollmentMethod testEnrollmentMethod = brService.getTestEnrollmentMethodByCode(assessmentProgram.getId(), enrollmentMethod);
						if(testEnrollmentMethod != null) {
							builder.addLong("enrollmentMethodId", testEnrollmentMethod.getId());
						}
					}else{
						builder.addString("enrollmentMethod", null);
						builder.addLong("enrollmentMethodId", null);
					}
					if(interimFlag != null){
						builder.addString("interimFlag", interimFlag);
					}else{
						builder.addString("interimFlag", null);
					}
					builder.addLong("assessmentId", null);
					builder.addLong("testTypeId", null);
					builder.addLong("contentAreaId", null);
					builder.addLong("gradeCourseId", null);
					builder.addDate("date", new Date());
					builder.addLong("uiBatchJobId", null);

					logger.debug("Launching job");

					JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
					logger.debug("Exit Status : " + execution.getStatus());
				}
			} catch (Exception e) {
				logger.error("ERROR", e);
			}
			logger.debug("<-- run - Job: " + job.getName());
		}
	}
	
	private final void initializedTechUser() {
		logger.debug("initializing TechUser: "+wsAdminUserName);
		User user = userService.getByUserName(wsAdminUserName);
		UserDetailImpl userDetailImpl = new UserDetailImpl(user);
		PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetailImpl, userDetailImpl.getPassword(), userDetailImpl.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        logger.debug("initialized TechUser: "+wsAdminUserName);
	}
}
