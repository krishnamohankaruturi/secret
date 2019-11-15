package edu.ku.cete.batch;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.controller.ReportExtractProcessor;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DataReportTypeEnum;


@Component
public class ReportExtractProcessStarter {

	private final static Log logger = LogFactory.getLog(ReportExtractProcessStarter.class);

	@Autowired(required = true)
	private DataReportService dataReportService;

	@Autowired(required = true)
	private UserService userService;

	@Autowired(required = true)
	private ReportExtractProcessor reportExtractProcessor;
	
	@Autowired(required = true)
	private CategoryDao categoryDao;

	private String isScheduleOn;

	private Long inProgressStatusId;
	
	private Long inQueueStatusId;
	
	private Long failedStatusId;
	
	/**
	 *  Existing process which uses the stored procedure and runs an extract at a time based on schedule.
	 */
	public Long startReportExtractProcess() throws Exception {
		logger.debug("--> startReportExtractProcess isScheduleOn " + isScheduleOn + " " + this.hashCode());
		Long jobId = null;

		if (isScheduleOn.equalsIgnoreCase("ON")) {
			initializeIds();
			
			ModuleReport moduleReport = dataReportService.getQueuedReport(inQueueStatusId, inProgressStatusId);
			queueReportProcess(moduleReport);
		}
		logger.debug("<-- startReportExtractProcess");
		return jobId;
	}
	
	/**
	 *  Process which takes unique type of active extract requests and schedules one by one.
	 */
	public Long startAllReportExtractProcess() throws Exception {
		logger.debug("--> startAllReportExtractProcess isScheduleOn " + isScheduleOn + " " + this.hashCode());
		Long jobId = null;
		if (isScheduleOn.equalsIgnoreCase("ON")) {
			initializeIds();
			List<ModuleReport> moduleReports = dataReportService.getQueuedReports(inQueueStatusId, inProgressStatusId, null);
			
			for(ModuleReport moduleReport : moduleReports){
				queueReportProcess(moduleReport);
			}
		}
		logger.debug("<-- startAllReportExtractProcess");
		return jobId;
	}
	
	/**
	 *  Process which uses the takes unique type of active extract requests and schedules simultaneously
	 */
	public Long startAsyncReportExtractProcess() throws Exception {
		logger.debug("--> startAsyncReportExtractProcess isScheduleOn " + isScheduleOn + " " + this.hashCode());
		Long jobId = null;
		if (isScheduleOn.equalsIgnoreCase("ON")) {
			initializeIds();
			List<ModuleReport> moduleReports = dataReportService.getQueuedReports(inQueueStatusId, inProgressStatusId, null);
			
			for(ModuleReport moduleReport : moduleReports){
				asyncQueueReportProcess(moduleReport);
			}
		}
		logger.debug("<-- startAsyncReportExtractProcess");
		return jobId;
	}

	/**
	 *  Process which takes unique type of active extract requests and schedules one by one.
	 */
	public Long startSchoolReportExtractProcess() throws Exception {
		logger.debug("--> startSchoolReportExtractProcess isScheduleOn " + isScheduleOn + " " + this.hashCode());
		Long jobId = null;
		if (isScheduleOn.equalsIgnoreCase("ON")) {
			
			initializeIds();
			List<ModuleReport> moduleReports = dataReportService.getQueuedReports(inQueueStatusId, inProgressStatusId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
			
			for(ModuleReport moduleReport : moduleReports){
				queueReportProcess(moduleReport);
			}
		}
		logger.debug("<-- startSchoolReportExtractProcess");
		return jobId;
	}
	
	/**
	 *  Process which takes unique type of active extract requests and schedules one by one.
	 */
	public Long startDistrictReportExtractProcess() throws Exception {
		logger.debug("--> startDistrictReportExtractProcess isScheduleOn " + isScheduleOn + " " + this.hashCode());
		Long jobId = null;
		if (isScheduleOn.equalsIgnoreCase("ON")) {
			
			initializeIds();
			List<ModuleReport> moduleReports = dataReportService.getQueuedReports(inQueueStatusId, inProgressStatusId,CommonConstants.ORGANIZATION_DISTRICT_CODE);
			
			for(ModuleReport moduleReport : moduleReports){
				queueReportProcess(moduleReport);
			}
		}
		logger.debug("<-- startDistrictReportExtractProcess");
		return jobId;
	}
	
	public Long startStateReportExtractProcess() throws Exception {
		logger.debug("--> startStateReportExtractProcess isScheduleOn " + isScheduleOn + " " + this.hashCode());
		Long jobId = null;
		if (isScheduleOn.equalsIgnoreCase("ON")) {
			
			initializeIds();
			List<ModuleReport> moduleReports = dataReportService.getQueuedReports(inQueueStatusId, inProgressStatusId, CommonConstants.ORGANIZATION_STATE_CODE);
			
			for(ModuleReport moduleReport : moduleReports){
				queueReportProcess(moduleReport);
			}
		}
		logger.debug("<-- startStateReportExtractProcess");
		return jobId;
	}

	private void initializeIds() {
		// Run only once
		if(null == inProgressStatusId){
			inProgressStatusId = categoryDao.getCategoryId("IN_PROGRESS", "PD_REPORT_STATUS");
		}
		// Run only once
		if(null == inQueueStatusId){
			inQueueStatusId = categoryDao.getCategoryId("IN_QUEUE", "PD_REPORT_STATUS");
		}
		// Run only once
		if(null == failedStatusId){
			failedStatusId = categoryDao.getCategoryId("FAILED", "PD_REPORT_STATUS");
		}
	}
	
	@Async
	private void asyncQueueReportProcess(ModuleReport moduleReport)
			throws IOException, JsonParseException, JsonMappingException {
		queueReportProcess(moduleReport);
	}
	
	@SuppressWarnings("unchecked")
	private void queueReportProcess(ModuleReport moduleReport)
			throws IOException, JsonParseException, JsonMappingException {
		if (null != moduleReport) {
			logger.info("Starting : "+ moduleReport.getId());
			 dataReportService.moveReportToInProgress(inQueueStatusId, inProgressStatusId,moduleReport.getId());
			
			if (StringUtils.isNotBlank(moduleReport.getJsonData())) {
				User user = userService.get(Long.valueOf(moduleReport.getModifiedUser()));
				user = userService.getByUserName(user.getUserName());
				final Long orgId = moduleReport.getOrganizationId();
				
				final Long moduleReportId = moduleReport.getId();
				ObjectMapper objectMapper = new ObjectMapper();
				Map<String, Object> params = objectMapper.readValue(moduleReport.getJsonData(), Map.class);
				
				final Map<String, Object> parameters = new HashMap<String, Object>(params);
				final UserDetailImpl userDetails = getUserDetails(user);
				user.setCurrentOrganizationId(Long.valueOf(parameters.get("currentOrganizationId").toString()));
				if(moduleReport.getGroupId() != null) {
					user.setCurrentGroupsId(moduleReport.getGroupId());
				}
				reportExtractProcessor.startExtract(userDetails, moduleReportId, DataReportTypeEnum.getById(moduleReport.getReportTypeId()), orgId,
						parameters);
			} else if (moduleReport.getReportTypeId() == 18) {
				User user = userService.get(Long.valueOf(moduleReport.getModifiedUser()));
				user = userService.getByUserName(user.getUserName());
				final Long orgId = moduleReport.getOrganizationId();
				
				final Long moduleReportId = moduleReport.getId();
				final UserDetailImpl userDetails = getUserDetails(user);
				if(moduleReport.getGroupId() != null) {
					user.setCurrentGroupsId(moduleReport.getGroupId());
				}
				reportExtractProcessor.startExtract(userDetails, moduleReportId, DataReportTypeEnum.getById(moduleReport.getReportTypeId()), orgId,
						null);
			} else {
				logger.warn("JSON data is missing for module report: "+moduleReport.getId());
				// move to failed state as json data is missing
				dataReportService.updateModuleReportStatus(moduleReport, failedStatusId);
			}
		}
	}

	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	private UserDetailImpl getUserDetails(User user) {
		UserDetailImpl userDetails = new UserDetailImpl(user);
		PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);
		return userDetails;
	}
}
