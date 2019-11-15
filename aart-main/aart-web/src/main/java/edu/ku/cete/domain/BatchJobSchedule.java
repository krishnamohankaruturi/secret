package edu.ku.cete.domain;

public class BatchJobSchedule {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column batchjobschedule.id
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column batchjobschedule.jobname
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	private String jobName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column batchjobschedule.jobrefname
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	private String jobRefName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column batchjobschedule.initmethod
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	private String initMethod;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column batchjobschedule.cronexpression
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	private String cronExpression;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column batchjobschedule.scheduled
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	private Boolean scheduled;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column batchjobschedule.allowedserver
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	private String allowedServer;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column batchjobschedule.id
	 * @return  the value of batchjobschedule.id
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column batchjobschedule.id
	 * @param id  the value for batchjobschedule.id
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column batchjobschedule.jobname
	 * @return  the value of batchjobschedule.jobname
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column batchjobschedule.jobname
	 * @param jobName  the value for batchjobschedule.jobname
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName == null ? null : jobName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column batchjobschedule.jobrefname
	 * @return  the value of batchjobschedule.jobrefname
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public String getJobRefName() {
		return jobRefName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column batchjobschedule.jobrefname
	 * @param jobRefName  the value for batchjobschedule.jobrefname
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public void setJobRefName(String jobRefName) {
		this.jobRefName = jobRefName == null ? null : jobRefName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column batchjobschedule.initmethod
	 * @return  the value of batchjobschedule.initmethod
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public String getInitMethod() {
		return initMethod;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column batchjobschedule.initmethod
	 * @param initMethod  the value for batchjobschedule.initmethod
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public void setInitMethod(String initMethod) {
		this.initMethod = initMethod == null ? null : initMethod.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column batchjobschedule.cronexpression
	 * @return  the value of batchjobschedule.cronexpression
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column batchjobschedule.cronexpression
	 * @param cronExpression  the value for batchjobschedule.cronexpression
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression == null ? null : cronExpression
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column batchjobschedule.scheduled
	 * @return  the value of batchjobschedule.scheduled
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public Boolean getScheduled() {
		return scheduled;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column batchjobschedule.scheduled
	 * @param scheduled  the value for batchjobschedule.scheduled
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public void setScheduled(Boolean scheduled) {
		this.scheduled = scheduled;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column batchjobschedule.allowedserver
	 * @return  the value of batchjobschedule.allowedserver
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public String getAllowedServer() {
		return allowedServer;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column batchjobschedule.allowedserver
	 * @param allowedServer  the value for batchjobschedule.allowedserver
	 * @mbggenerated  Thu Nov 12 21:45:44 CST 2015
	 */
	public void setAllowedServer(String allowedServer) {
		this.allowedServer = allowedServer == null ? null : allowedServer
				.trim();
	}
}