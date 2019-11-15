package edu.ku.cete.batch.audit;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.ScheduledJobLauncher;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.service.ActivationEmailTemplateService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.student.FirstContactService;

/**
 * Sudhansu.b : US17687 :For User audit Batch
 */

public class AuditLogScheduler {

	private final static Log logger = LogFactory.getLog(ScheduledJobLauncher.class);

	@Autowired
	private UserService userService;

	@Autowired
	private OrganizationService orgService;

	@Autowired
	private GroupsService grpService;

	@Autowired
	StudentService studentService;

	@Autowired
	private RosterService rosterService;

	@Autowired
	private FirstContactService firstContactService;
	
	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;

	@Autowired
	private ActivationEmailTemplateService activationEmailTemplateService;

	String isScheduleOn;

	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	public void run() throws Exception {
		logger.debug("--> run - Job: audit log scheduler triggered " + isScheduleOn);
		if ("ON".equalsIgnoreCase(isScheduleOn)) {
			logger.debug("--> run - Job: audit log scheduler triggered ");
			List<Long> auditEntries = userService.getUnProcessedAuditLoggedUser();

			for (Long domainAuditHistoryId : auditEntries) {
				DomainAuditHistory domainAuditHistory = domainAuditHistoryDao.getById(domainAuditHistoryId);
				if ("USER".equalsIgnoreCase(domainAuditHistory.getObjectType())) {
					userService.addToUserAuditTrailHistory(domainAuditHistory);
				} else if ("ORGANIZATION".equalsIgnoreCase(domainAuditHistory.getObjectType())) {
					orgService.addToOrganizationAuditTrailHistory(domainAuditHistory);
				} else if ("GROUPS".equalsIgnoreCase(domainAuditHistory.getObjectType())) {
					grpService.addToGroupsAuditTrailHistory(domainAuditHistory);
				} else if ("ROSTER".equalsIgnoreCase(domainAuditHistory.getObjectType())) {
					rosterService.addToRosterAuditTrailHistory(domainAuditHistory);
				} else if ("STUDENT".equalsIgnoreCase(domainAuditHistory.getObjectType())) {
					studentService.addToGroupsAuditTrailHistory(domainAuditHistory);
				} else if ("SURVEY".equalsIgnoreCase(domainAuditHistory.getObjectType())) {
					firstContactService.addToGroupAuditTrailHistory(domainAuditHistory);
				} else if ("ACTIVITIONEMAIL".equalsIgnoreCase(domainAuditHistory.getObjectType())) {
					activationEmailTemplateService.addToactivationEmailTemplateAuditTrailHistory(domainAuditHistory);
				}else {
					userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(), "COMPLETED");
				}
			}
			logger.debug("--> run - Job: audit log scheduler completed. ");
		} else {
			logger.debug("--> run - Job: Switch on audit log scheduler.");
		}

	}

}
