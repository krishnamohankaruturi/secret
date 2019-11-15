package edu.ku.cete.batch.support;

import java.util.List;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class SimultaneousJobAspect implements MethodInterceptor,
		InitializingBean {

	private static final int JOB_INDEX = 0;

	// list of job names that SHOULD NOT run simultaneously
	private List<String> jobNames;

	private JobExplorer jobExplorer;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// get the job names
		Object[] arguments = invocation.getArguments();
		// get the 'job' argument (argument 0)
		Job job = (Job) arguments[JOB_INDEX];
		// get the name
		for (String jobName : jobNames) {
			if (jobName.equalsIgnoreCase(job.getName())) {
				// check if there's one running
				Set<JobExecution> jobExecutions = jobExplorer
						.findRunningJobExecutions(jobName);
				if (jobExecutions != null && !jobExecutions.isEmpty()) {
					// have a match --> throw a job exception
					throw new JobExecutionAlreadyRunningException(
							jobName
									+ " is already running and can't be run simultaneously");
				}// end if
			}// end if
		}// end for
			// continue
		return invocation.proceed();
	}

	public void setJobNames(List<String> jobNames) {
		this.jobNames = jobNames;
	}

	public void setJobExplorer(JobExplorer jobExplorer) {
		this.jobExplorer = jobExplorer;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(jobExplorer);
	}
}