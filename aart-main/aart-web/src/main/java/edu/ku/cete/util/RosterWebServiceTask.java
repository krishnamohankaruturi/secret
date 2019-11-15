package edu.ku.cete.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.xstream.XStream;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.ksde.kids.client.AuthenticationSoapHeader;
import edu.ku.cete.ksde.kids.client.KIDSWebServiceSoap;
import edu.ku.cete.ksde.rosters.result.RosterByDateInputParameter;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.report.domain.KSDEXMLAudit;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.KSDEXMLAuditService;
import edu.ku.cete.service.enrollment.RosterService;

//TODO move the operations / methods to AARTWebServiceTask and only the specific ones in the respective.
public class RosterWebServiceTask extends TimerTask {
	/**
	 * logger.
	 */
	private static final Log LOGGER = LogFactory
			.getLog(RosterWebServiceTask.class);

	/**
	 * {@link ScheduledWebServiceInputConfiguration}.
	 */
	private ScheduledWebServiceInputConfiguration scheduledWebServiceInputConfiguration;
	/**
	 * webservice url from table.
	 */
	private Category webServiceUrl;
	/**
	 * Field specification dao.
	 */
	private FieldSpecificationDao fieldSpecificationDao;
	/**
	 * Roster service.
	 */
	private RosterService rosterService;
	/**
	 * category service.
	 */
	private CategoryService categoryService;
	/**
	 * categoryDao.
	 */
	//private CategoryDao categoryDao;
	/**
	 * roster.
	 */
	private Category rosterRecordType;
	/**
	 * web service client.
	 */
	private KIDSWebServiceSoap client;

	private KSDEXMLAuditService xmlService;
	/**
	 * kansas assessment codes.
	 */
	private Map<String, Category> kansasAssessmentInputNames = new HashMap<String, Category>();
	/**
	 * for un- marshalling.
	 */
	private XStream xStream;
	/**
	 * emailService.
	 */
	private EmailService emailService;
	/**
	 * The scheduler that scheduled this task.
	 */
	private RosterWebServiceScheduler rosterWebServiceScheduler;

	/**
	 * webServiceRecordTypeCode.
	 */
	private String webServiceRecordTypeCode;
	/**
	 * message source.
	 */
	private MessageSource messageSource;
	/**
	 * uploadSpecification.
	 */
	private UploadSpecification uploadSpecification;

	/**
	 * temporary files for storing emails.
	 */
	private String emailPath;

	/**
	 * maxRejectedRecords.
	 */
	private int maxRejectedRecords;

	/**
	 * @return the maxRejectedRecords
	 */
	public int getMaxRejectedRecords() {
		return maxRejectedRecords;
	}

	/**
	 * @param maxRejectedRecords
	 *            the maxRejectedRecords to set
	 */
	public void setMaxRejectedRecords(int maxRejectedRecords) {
		this.maxRejectedRecords = maxRejectedRecords;
	}

	/**
	 * @return the scheduledWebServiceInputConfiguration
	 */
	public ScheduledWebServiceInputConfiguration getScheduledWebServiceInputConfiguration() {
		return scheduledWebServiceInputConfiguration;
	}

	/**
	 * @param scheduledWebServiceInputConfiguration
	 *            the scheduledWebServiceInputConfiguration to set
	 */
	public void setScheduledWebServiceInputConfiguration(
			ScheduledWebServiceInputConfiguration scheduledWebServiceInputConfiguration) {
		this.scheduledWebServiceInputConfiguration = scheduledWebServiceInputConfiguration;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @return the uploadSpecification
	 */
	public UploadSpecification getUploadSpecification() {
		return uploadSpecification;
	}

	/**
	 * @param uploadSpecification
	 *            the uploadSpecification to set
	 */
	public void setUploadSpecification(UploadSpecification uploadSpecification) {
		this.uploadSpecification = uploadSpecification;
	}

	/**
	 * @return the emailPath
	 */
	public String getEmailPath() {
		return emailPath;
	}

	/**
	 * @param emailPath
	 *            the emailPath to set
	 */
	public void setEmailPath(String emailPath) {
		this.emailPath = emailPath;
	}

	/**
	 * @return the webServiceUrl.
	 */
	public final Category getWebServiceUrl() {
		return webServiceUrl;
	}

	/**
	 * @param webServUrl
	 *            the webServiceUrl to set
	 */
	public final void setWebServiceUrl(Category webServUrl) {
		this.webServiceUrl = webServUrl;
	}

	/**
	 * @return the fieldSpecificationDao
	 */
	public final FieldSpecificationDao getFieldSpecificationDao() {
		return fieldSpecificationDao;
	}

	/**
	 * @param fieldSpecDao
	 *            the fieldSpecificationDao to set
	 */
	public final void setFieldSpecificationDao(
			FieldSpecificationDao fieldSpecDao) {
		this.fieldSpecificationDao = fieldSpecDao;
	}

	/**
	 * @return the rosterRecordType
	 */
	public final Category getRosterRecordType() {
		return rosterRecordType;
	}

	/**
	 * @param rosterRecType
	 *            the rosterRecordType to set
	 */
	public final void setRosterRecordType(Category rosterRecType) {
		this.rosterRecordType = rosterRecType;
	}

	/**
	 * @return the client
	 */
	public final KIDSWebServiceSoap getClient() {
		return client;
	}

	/**
	 * @param wsClient
	 *            the client to set
	 */
	public final void setClient(KIDSWebServiceSoap wsClient) {
		this.client = wsClient;
	}

	/**
	 * @return the enrollmentService
	 */
	public final RosterService getRosterService() {
		return rosterService;
	}

	/**
	 * @param enrollmentService
	 *            the enrollmentService to set
	 */
	public final void setRosterService(RosterService rosterService) {
		this.rosterService = rosterService;
	}

	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService() {
		return categoryService;
	}

	/**
	 * @param categoryService
	 *            the categoryService to set
	 */
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/**
	 * @return the xStream
	 */
	public final XStream getxStream() {
		return xStream;
	}

	/**
	 * @param xStreamParser
	 *            the xStream to set
	 */
	public final void setxStream(XStream xStreamParser) {
		this.xStream = xStreamParser;
	}

	/**
	 * @return the emailService
	 */
	public final EmailService getEmailService() {
		return emailService;
	}

	/**
	 * @param emailServ
	 *            the emailService to set
	 */
	public final void setEmailService(EmailService emailServ) {
		this.emailService = emailServ;
	}

	/**
	 * @return the webServiceRecordTypeCode
	 */
	public String getWebServiceRecordTypeCode() {
		return webServiceRecordTypeCode;
	}

	/**
	 * @param webServiceRecordTypeCode
	 *            the webServiceRecordTypeCode to set
	 */
	public void setWebServiceRecordTypeCode(String webServiceRecordTypeCode) {
		this.webServiceRecordTypeCode = webServiceRecordTypeCode;
	}

	/**
	 * @param rosterWebServScheduler
	 *            {@link RosterWebServiceScheduler}
	 */
	public final void setRosterWebServiceScheduler(
			RosterWebServiceScheduler rosterWebServScheduler) {
		this.rosterWebServiceScheduler = rosterWebServScheduler;
	}

	/**
	 * @return the kansasAssessmentInputNames
	 */
	public Map<String, Category> getKansasAssessmentInputNames() {
		return kansasAssessmentInputNames;
	}

	/**
	 * @param kansasAssessmentInputNames
	 *            the kansasAssessmentInputNames to set
	 */
	public void setKansasAssessmentInputNames(
			Map<String, Category> kansasAssessmentInputNames) {
		this.kansasAssessmentInputNames = kansasAssessmentInputNames;
	}

	/**
	 * @return the timer
	 */
	public final RosterWebServiceScheduler getRosterWebServiceScheduler() {
		return rosterWebServiceScheduler;
	}

	public KSDEXMLAuditService getXmlService() {
		return xmlService;
	}

	public void setXmlService(KSDEXMLAuditService xmlService) {
		this.xmlService = xmlService;
	}

	/**
	 * test.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void invokeWebService() {
		RecordSaveStatus webServiceStatus = RecordSaveStatus.BEGIN_WEBSERVICE_UPLOAD;
		Date startingTimeStamp = new Date();
		Map<String, Object> messages = WebServiceHelper
				.getInitializedMessages();

		UserDetailImpl userDetailImpl = null;

		try {
			userDetailImpl = rosterWebServiceScheduler.getInitializedUser();
			// Create a token and set the security context
	        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetailImpl, userDetailImpl.getPassword(), userDetailImpl.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(token);
	        
			Restriction restriction = rosterWebServiceScheduler
					.getRosterRestriction(userDetailImpl);

			webServiceStatus = RecordSaveStatus.CHECKED_AUTHORITIES;
			if (restriction == null) {
				throw new AartParseException(
						"No Permission to upload enrollment ");
			}
			webServiceStatus = RecordSaveStatus.VERIFIED_AUTHORITIES;
			messages.put("fileOrigination", webServiceUrl.getCategoryName());

			// Get the input parameters in a map.
			Map<String, Category> rostersByDateInputParameterMap = WebServiceHelper
					.getRosterByDateInputParameterMap(
							scheduledWebServiceInputConfiguration,
							webServiceRecordTypeCode, categoryService,
							fieldSpecificationDao);
			// get the input parameter in the object.
			RosterByDateInputParameter rostersByDateInputParameter = WebServiceHelper
					.getRosterByDateInputParameter(
							scheduledWebServiceInputConfiguration,
							rostersByDateInputParameterMap, categoryService,
							fieldSpecificationDao);
			messages.put("recordPullStartTime",
					rostersByDateInputParameter.getStrRosterFromDate());
			messages.put("recordPullEndTime",
					rostersByDateInputParameter.getStrRosterToDate());
			if (rostersByDateInputParameter.getRosterToDate()
					.before(new Date())) {
				// Get the frequency values in a map.
				Map<String, Category> rosterByDateFrequencyParameterMap = WebServiceHelper
						.getRosterByDateFrequencyMap(uploadSpecification,
								uploadSpecification
										.getKansasWebServiceConfigTypeCode(),
								categoryService, fieldSpecificationDao);

				Holder<String> strFromDateHolder = new Holder<String>(
						rostersByDateInputParameter.getStrRosterFromDate());
				Holder<Boolean> bolRequestComplete = new Holder<Boolean>(false);
				Holder<String> getRostersByDateResult = new Holder<String>("");

				AuthenticationSoapHeader authenticationSoapHeader = new AuthenticationSoapHeader();
				authenticationSoapHeader
						.setUsername(scheduledWebServiceInputConfiguration
								.getWsConnectionUsername());
				authenticationSoapHeader
						.setPassword(scheduledWebServiceInputConfiguration
								.getWsConnectionPassword());

				client.getSTCOByDate(strFromDateHolder,
						rostersByDateInputParameter.getStrRosterToDate(),
						rostersByDateInputParameter.getStrCurrentSchoolYear(),
						bolRequestComplete, authenticationSoapHeader,
						getRostersByDateResult);
				webServiceStatus = RecordSaveStatus.WEB_SERVICE_CALL_SUCCESSFUL;

				messages.put("recordPullEndTime",
						rostersByDateInputParameter.getStrRosterToDate());

				String result = getRostersByDateResult.value;

				saveXML(result,
						rostersByDateInputParameter.getRosterFromDate(),
						rostersByDateInputParameter.getRosterToDate());

				rostersByDateInputParameter.changeInputForNextRun(
						rosterByDateFrequencyParameterMap, uploadSpecification,
						strFromDateHolder.value, bolRequestComplete.value);

				Category categoryEndTime = rostersByDateInputParameterMap
						.get(scheduledWebServiceInputConfiguration
								.getRosterWebServiceEndTime());
				categoryEndTime.setCurrentContextUserId(userDetailImpl
						.getUser().getId());
				categoryEndTime.setAuditColumnProperties();
				rostersByDateInputParameterMap.put(
						scheduledWebServiceInputConfiguration
								.getRosterWebServiceEndTime(), categoryEndTime);

				Category categoryStartTime = rostersByDateInputParameterMap
						.get(scheduledWebServiceInputConfiguration
								.getRosterWebServiceStartTime());
				categoryStartTime.setCurrentContextUserId(userDetailImpl
						.getUser().getId());
				categoryStartTime.setAuditColumnProperties();
				rostersByDateInputParameterMap.put(
						scheduledWebServiceInputConfiguration
								.getRosterWebServiceStartTime(),
						categoryStartTime);

				WebServiceHelper.updateRostersByDateInputParameter(
						scheduledWebServiceInputConfiguration,
						rostersByDateInputParameterMap, categoryService,
						rostersByDateInputParameter);
			} else {
				LOGGER.debug("Skipped End time "
						+ rostersByDateInputParameter.getStrRosterToDate()
						+ "is not passed the current time " + new Date());
				
				messages.put("Skipped", "End time "
						+ rostersByDateInputParameter.getStrRosterToDate()
						+ "is not passed the current time " + new Date());
			}
			messages.put("nextRunStartTime",
					rosterWebServiceScheduler.getScheduleInterval());
			messages.put(
					"nextRunStartTimeStamp",
					new Date((new Date()).getTime()
							+ rosterWebServiceScheduler.getScheduleInterval())
							.toString());
			webServiceStatus = RecordSaveStatus.ROSTER_NEXT_UPLOAD_SET;
		} catch (Exception e) {
			LOGGER.error("Failed after " + webServiceStatus, e);
			messages.put("nextRunStartTime",
					rosterWebServiceScheduler.getRetryTime());
			messages.put(
					"nextRunStartTimeStamp",
					new Date((new Date()).getTime()
							+ rosterWebServiceScheduler.getRetryTime())
							.toString());
			if (webServiceStatus.equals(RecordSaveStatus.ROSTER_DATA_RECEIVED)) {
				// print the result to help operations.
				LOGGER.error("Potentially invalid data");
			} else {
				// reinitialize all the webservice resources.
				rosterWebServiceScheduler.getWebServiceTimer().cancel();
				LOGGER.debug("Cancelled the current schedule");
				rosterWebServiceScheduler.reSchedule();
				LOGGER.debug("Re initialized all web service resources");
			}
		} finally {
			// send the email irrespective of whether it fails or succeeds.
			messages.put("timeTaken", (new Date()).getTime()
					- startingTimeStamp.getTime());
			messages.put("endingTimeStamp", (new Date()).toString());
			messages.put("webServiceType", "roster");
			try {
				messages.put("fileReferenceMessage", messageSource.getMessage(
						"fileReferenceMessage", null, null));
				String uniqueFolder = userDetailImpl.getUserId()
						+ ParsingConstants.INNER_DELIM + System.nanoTime()
						+ ParsingConstants.BLANK;

				//emailService.sendScheduledWebServiceUploadMsg(userDetailImpl,
				//		webServiceStatus, messages, null); //Removed based on ops request
				// delete the folder created for unique purposes.
				UploadHelper.removeDir(emailPath + java.io.File.separator
						+ uniqueFolder);
			} catch (Exception e) {
				LOGGER.error("unknown exception in sending email For User"
						+ userDetailImpl, e);
			}
		}
	}

	private void saveXML(String result, Date fromDate, Date toDate) {
		KSDEXMLAudit xmlRecord = new KSDEXMLAudit();
		xmlRecord.setXml(result);
		xmlRecord.setType("STCO");
		xmlRecord.setCreateDate(new Date());
		xmlRecord.setProcessedCode("NOTPROCESSED");
		xmlRecord.setFromDate(fromDate);
		xmlRecord.setToDate(toDate);
		xmlService.insert(xmlRecord);
	}

	/**
	 * clean all the timer resources.
	 */
	public final void cleanResources() {
		xStream = null;
		client = null;
	}

	/**
	 * the thread method invoking the web service.
	 */
	public final void run() {
//		LOGGER.debug("Starting at time " + new Date());
//		try {
//			if (rosterWebServiceScheduler.resetScheduleInterval()
//					|| rosterWebServiceScheduler.resetRetryTime()) {
//				LOGGER.warn("The schedule has changed.");
//				// cancel the current scheduled timer.
//				rosterWebServiceScheduler.getWebServiceTimer().cancel();
//				rosterWebServiceScheduler.changeSchedule();
//			} else {
//				LOGGER.debug("The schedule has not changed.");
//			}
//			// Proceed further, only when the schedule is turned on.
//			if (uploadSpecification.getKansasScheduledWebServiceSwitch() != null
//					&& CommonConstants.KANSAS_SCHEDULED_WEB_SERVICE_SWITCH_ON
//							.equalsIgnoreCase(uploadSpecification
//									.getKansasScheduledWebServiceSwitch())) {
//				LOGGER.debug("The schedule is turned ON.");
//				invokeWebService();
//			} else {
//				LOGGER.debug("The schedule is turned OFF.");
//			}
//		} catch (Exception e) {
//			LOGGER.error("Run failed", e);
//		}
	}

}
