package edu.ku.cete.util;

import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.KSDEXMLAuditService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;

@Component
public class RosterWebServiceScheduler  implements MessageSourceAware{
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(RosterWebServiceScheduler.class);
	/**
	 * set the metadata.
	 */
	@Autowired
	private CategoryService categoryService;
	/**
	 * categoryDao.
	 */
	@Autowired
	private CategoryDao categoryDao;
    /**
     * schedule interval.
     */
    private long scheduleInterval;
    /**
     * time to wait till tomcat starts.
     */
    private long tomcatStartTime;
    /**
     * time to wait for schedule frequency change to take effect.
     */
    private long retryTime;
    /**
     * wsAdminUserName.
     */
    @Value("${wsAdminUserName}")
    private String wsAdminUserName;
    
    /**
     * scheduledWebServiceInputConfiguration.
     */
    @Autowired
    private ScheduledWebServiceInputConfiguration scheduledWebServiceInputConfiguration;
    /**
	 * the specification that has field limits, allowable values etc.
	 */
	@Autowired
	private UploadSpecification uploadSpecification;
	/**
	 * Field specification dao.
	 */
	@Autowired
	private FieldSpecificationDao fieldSpecificationDao;
	/**
	 * Enrollment service.
	 */
	@Autowired
	private RosterService rosterService;
	/**
	 * for sending email.
	 */
	@Autowired
	private EmailService emailService;
	/**
	 * userService.
	 */
	@Autowired
	private UserService userService;
	/**
	 * OrganizationService service.
	 */
	@Autowired
	private OrganizationService organizationService;
    /**
     * resourceRestrictionService.
     */
    @Autowired
    private ResourceRestrictionService resourceRestrictionService;
    /**
     * permissionUtil.
     */
    @Autowired
    private PermissionUtil permissionUtil;
    /**
     * restrictedResourceConfiguration.
     */
    @Autowired
    private RestrictedResourceConfiguration restrictedResourceConfiguration;
    
    @Autowired
    private KSDEXMLAuditService xmlService;
    
	/**
	 *webService timer.
	 */
	private Timer webServiceTimer = new Timer();
	/**
	 * RosterwebServiceTask.
	 */
	private RosterWebServiceTask rosterWebServiceTask;
	/**
	 * email path.
	 */
    @Value("${email.path}")
	private String emailPath;
	/**
	 * message bundle
	 */
	private MessageSource messageSource;
    /**
     * maxRejectedRecords.
     */
    @Value("${email.maxRejectedRecords}")
	private int maxRejectedRecords;

	/**
	 * TODO Move this to helper.
	 * initialize should also re-initialize.
	 */
	/**
	 * 
	 */
	public final void initializeWebServiceTask() {
		rosterWebServiceTask = new RosterWebServiceTask();
		//manually inject dependencies to timer task as annotated
		// dependencies are destroyed after every run.
		rosterWebServiceTask.setScheduledWebServiceInputConfiguration(
				scheduledWebServiceInputConfiguration);
		//may need to happen each time.
		Category webServiceUrl =   getWebServiceUrl();
		rosterWebServiceTask.setWebServiceUrl(webServiceUrl);
		rosterWebServiceTask.setClient(WebServiceHelper.getWebServiceClient(
				webServiceUrl));
		//If this is some thing we are changing often.
		rosterWebServiceTask.setKansasAssessmentInputNames(
				WebServiceHelper.getKansasAssessments(categoryService,
						uploadSpecification.getKansasAssessmentTags()));
		//need to happen only once
		rosterWebServiceTask.setRosterRecordType(getRecordType());
		rosterWebServiceTask.setFieldSpecificationDao(
				fieldSpecificationDao);
		rosterWebServiceTask.setxStream(WebServiceHelper.getUnMarshaller());
		rosterWebServiceTask.setRosterService(rosterService);
		rosterWebServiceTask.setCategoryService(categoryService);
		rosterWebServiceTask.setEmailService(emailService);
		rosterWebServiceTask.setWebServiceRecordTypeCode(
				uploadSpecification.getWebServiceRecordTypeCode());
		rosterWebServiceTask.setEmailPath(emailPath);
		rosterWebServiceTask.setUploadSpecification(uploadSpecification);
		rosterWebServiceTask.setMessageSource(messageSource);
		rosterWebServiceTask.setMaxRejectedRecords(maxRejectedRecords);
		
		rosterWebServiceTask.setXmlService(xmlService);
	}   
    
    /**
     * TODO
     * @param userDetails {@link UserDetailImpl}
     * @return {@link Restriction}
     */
    public Restriction getRosterRestriction(UserDetailImpl userDetails) {
        //Find the restriction for what the user is trying to do on this page.
    	//TODO Look at Roster Service Impl
        //Find the restriction for what the user is trying to do on this page.
        Restriction restriction = resourceRestrictionService.getResourceRestriction(
                userDetails.getUser().getOrganization().getId(),
                permissionUtil.getAuthorityId(
                        userDetails.getUser().getAuthoritiesList(),
                        RestrictedResourceConfiguration.getUploadRosterPermissionCode()),
                permissionUtil.getAuthorityId(
                        userDetails.getUser().getAuthoritiesList(),
                        RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
                restrictedResourceConfiguration.getRosterResourceCategory().getId());
        return restriction;
    } 
    
	/**
	 * @return the scheduledWebServiceInputConfiguration
	 */
	public ScheduledWebServiceInputConfiguration
	getScheduledWebServiceInputConfiguration() {
		return scheduledWebServiceInputConfiguration;
	}
	/**
	 * @param scheduledWebServiceInputConfiguration the scheduledWebServiceInputConfiguration to set
	 */
	public void setScheduledWebServiceInputConfiguration(
			ScheduledWebServiceInputConfiguration scheduledWebServiceInputConfiguration) {
		this.scheduledWebServiceInputConfiguration = scheduledWebServiceInputConfiguration;
	}
	/**
	 * @return the webServiceTimer
	 */
	public final Timer getWebServiceTimer() {
		return webServiceTimer;
	}
	/**
	 * @param webServTimer the webServiceTimer to set
	 */
	public final void setWebServiceTimer(Timer webServTimer) {
		this.webServiceTimer = webServTimer;
	}
	/**
	 * startWebServiceTimer.
	 */
	@PostConstruct
	public final void startWebServiceTimer() {
		resetScheduleInterval();
		schedule();
		setTomcatStartTime();
		resetRetryTime();
		LOGGER.debug("Timer scheduled to run every "
				+ scheduleInterval + "milli-seconds");
	}
	/**
	 * initializes the user that is set to invoke the scheduled web service.
	 * @return {@link UserDetailImpl}
	 */
	public final UserDetailImpl getInitializedUser() {
		User user = userService.getByUserName(wsAdminUserName);
		return new UserDetailImpl(user);
	}
	/**
	 * initializes the user that is set to invoke the scheduled web service.
	 * @return {@link UserDetailImpl}
	 */
	public final ContractingOrganizationTree getOrganizationTree(UserDetailImpl user) {     
		user.getUser().setCurrentContextUserId(user.getUserId());
        ContractingOrganizationTree
          contractingOrganizationTree = organizationService.getTree(user.getUser().getOrganization());
        return contractingOrganizationTree; 
	}
	/**
	 * schedule the timer task.
	 */
	public final void schedule() {
		initializeWebServiceTask();
		rosterWebServiceTask.setRosterWebServiceScheduler(this);
		this.webServiceTimer = new Timer();
		//wait for tomcat to start up.
		webServiceTimer.schedule(rosterWebServiceTask, tomcatStartTime,
				scheduleInterval);
	}
	/**
	 * Called by the timer itself to reschedule up on failure.
	 */
	public final void reSchedule() {
		initializeWebServiceTask();
		rosterWebServiceTask.setRosterWebServiceScheduler(this);
		this.webServiceTimer = new Timer();
		//wait before retrying after failure.
		webServiceTimer.schedule(rosterWebServiceTask, retryTime,
				scheduleInterval);
	}
	/**
	 * Change schedule of the timer task almost immediately.
	 */
	public final void changeSchedule() {
		initializeWebServiceTask();
		rosterWebServiceTask.setRosterWebServiceScheduler(this);
		this.webServiceTimer = new Timer();
		//Change the schedule immediately.
		webServiceTimer.schedule(rosterWebServiceTask, scheduleInterval, scheduleInterval);
	}
	/**
	 * checks if the given schedule is the same as the one in the database.If not updates the current value and returns true.
	 * if it is the same retains the current value of scheduled frequency and returns false.
	 * @return {@link Boolean}
	 */
	public final boolean resetScheduleInterval() {
		Category kansasWebServiceScheduleFrequency = null;
		boolean isScheduleReset = false;
		try {
			kansasWebServiceScheduleFrequency = categoryService
					.selectByCategoryCodeAndType(uploadSpecification
							.getKansasWebServiceScheduleFrequencyCode(),
							uploadSpecification
									.getKansasWebServiceConfigTypeCode());
			isScheduleReset
			= !(NumericUtil.compare(scheduleInterval, kansasWebServiceScheduleFrequency.getCategoryName(),
					Long.MAX_VALUE));
			if (isScheduleReset) {
				this.scheduleInterval = NumericUtil.parse(kansasWebServiceScheduleFrequency.getCategoryName(),
						this.scheduleInterval, Long.MAX_VALUE);
			}
		} catch (Exception e) {
			LOGGER.error("Scheduled frequency parameter is invalid", e);
			isScheduleReset
			= !NumericUtil.compare(scheduleInterval, Long.MAX_VALUE);
			if (isScheduleReset) {
				this.scheduleInterval = Long.MAX_VALUE;
			}
		}
		return isScheduleReset;
	}
	/**
	 * checks if the given schedule is the same as the one in the database.If not updates the current value and returns true.
	 * if it is the same retains the current value of scheduled frequency and returns false.
	 */
	public final void setTomcatStartTime() {
		Category tomcatStartTimeCategory = null;
		try {
			tomcatStartTimeCategory = categoryService
					.selectByCategoryCodeAndType(uploadSpecification
							.getTomcatStartTimeCode(),
							uploadSpecification
									.getKansasWebServiceConfigTypeCode());
			this.tomcatStartTime = NumericUtil.parse(tomcatStartTimeCategory.getCategoryName(),
						this.tomcatStartTime, Long.MAX_VALUE);
		} catch (Exception e) {
			LOGGER.error("Tomcat Start Time  parameter is invalid", e);
			this.tomcatStartTime = Long.MAX_VALUE;
		}
	}
	/**
	 * checks if the given schedule is the same as the one in the database.If not updates the current value and returns true.
	 * if it is the same retains the current value of scheduled frequency and returns false.
	 */
	/**
	 * @return {@link Boolean}
	 */
	public final boolean resetRetryTime() {
		Category retryTimeCategory = null;
		boolean isRetryReset = false;
		try {
			retryTimeCategory = categoryService
					.selectByCategoryCodeAndType(uploadSpecification
							.getRetryTimeCode(),
							uploadSpecification
									.getKansasWebServiceConfigTypeCode());
			isRetryReset
			= !(NumericUtil.compare(retryTime, retryTimeCategory.getCategoryName(),
					Long.MAX_VALUE));
			if (isRetryReset) {
				this.retryTime = NumericUtil.parse(retryTimeCategory.getCategoryName(),
						this.retryTime, Long.MAX_VALUE);
			}
		} catch (Exception e) {
			LOGGER.error("Retry Time  parameter is invalid", e);
			isRetryReset
			= !NumericUtil.compare(retryTime, Long.MAX_VALUE);
			if (isRetryReset) {
				this.retryTime = Long.MAX_VALUE;
			}
		}
		return isRetryReset;
	}
	/**
	 * @return the scheduleInterval
	 */
	public final long getScheduleInterval() {
		return scheduleInterval;
	}
	/**
	 * @param scheduleInterval the scheduleInterval to set
	 */
	public final void setScheduleInterval(long scheduleInterval) {
		this.scheduleInterval = scheduleInterval;
	}
	/**
	 * @return the tomcatStartTime
	 */
	public final long getTomcatStartTime() {
		return tomcatStartTime;
	}
	/**
	 * @param tomcatStartTime the tomcatStartTime to set
	 */
	public final void setTomcatStartTime(long tomcatStartTime) {
		this.tomcatStartTime = tomcatStartTime;
	}
	/**
	 * @return the retryTime
	 */
	public final long getRetryTime() {
		return retryTime;
	}
	/**
	 * @param retTime the retryTime to set
	 */
	public final void setRetryTime(long retTime) {
		this.retryTime = retTime;
	}
	/**
	 * Sets what record type is scrs and what is enrollment and more.
	 * @return {@link Category}
	 */
	private Category getWebServiceUrl() {
		//currently there is only kansas.For more urls remove the get(o) and add drop down.
		return categoryService.selectByCategoryCodeAndType(
				uploadSpecification.getKansasWebServiceUrlCode(),
				uploadSpecification.getKansasWebServiceConfigTypeCode());
	}
	/**
	 * Sets what record type is scrs and what is enrollment and more.
	 * @return {@link Category}
	 */
	private Category getRecordType() {
		return categoryService.selectByCategoryCodeAndType(uploadSpecification.getRosterRecordType(),
				uploadSpecification.getWebServiceRecordTypeCode());
	}

	/**
	 * Called when the context is destroyed i.e. on application shutdown or un deployment.
	 * @throws Exception Exception
	 */
	@PreDestroy
	public final void cleanUp() throws Exception {
		LOGGER.warn("Cleaning up all resources ");
		try {
			rosterWebServiceTask.cleanResources();
			webServiceTimer.cancel();
		} catch (Exception e) {
			LOGGER.error("Cleaning all scheduler resources failed", e);
		}
	}
	@Override
	public void setMessageSource(MessageSource arg0) {
		this.messageSource = arg0;
	}
}
