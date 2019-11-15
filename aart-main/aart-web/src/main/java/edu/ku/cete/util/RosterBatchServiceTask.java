package edu.ku.cete.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.thoughtworks.xstream.XStream;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.ksde.kids.client.AuthenticationSoapHeader;
import edu.ku.cete.ksde.rosters.result.RosterByDateInputParameter;
import edu.ku.cete.ksde.rosters.result.RosterConverter;
import edu.ku.cete.ksde.rosters.result.RosterData;
import edu.ku.cete.ksde.rosters.result.RosterDataConverter;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.report.domain.KSDEXMLAudit;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.KSDEXMLAuditService;
import edu.ku.cete.service.enrollment.RosterService;

//TODO move the operations / methods to AARTWebServiceTask and only the specific ones in the respective.
public class RosterBatchServiceTask extends TimerTask {
	/**
	 * logger.
	 */
	private static final Log LOGGER = LogFactory
			.getLog(RosterBatchServiceTask.class);

	/**
	 * {@link ScheduledWebServiceInputConfiguration}.
	 */
	private ScheduledWebServiceInputConfiguration scheduledWebServiceInputConfiguration;

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
	private CategoryDao categoryDao;
	/**
	 * roster.
	 */
	private Category rosterRecordType;
	
	@Autowired
    private AssessmentProgramService assessmentProgramService;

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
	private RosterBatchServiceScheduler rosterWebServiceScheduler;

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

	private KSDEXMLAuditService xmlService;

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
	 * @return the categoryDao
	 */
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}

	/**
	 * @param categoryDao
	 *            the categoryDao to set
	 */
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
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
			RosterBatchServiceScheduler rosterWebServScheduler) {
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
	public final RosterBatchServiceScheduler getRosterWebServiceScheduler() {
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
	public final void invokeWebService() {
		RecordSaveStatus webServiceStatus = RecordSaveStatus.BEGIN_WEBSERVICE_UPLOAD;
		Date startingTimeStamp = new Date();
		Map<String, Object> messages = WebServiceHelper
				.getInitializedMessages();

		//ContractingOrganizationTree contractingOrganizationTree = new ContractingOrganizationTree();
		RosterConverter rosterConverter = new RosterConverter();
		RosterDataConverter rosterDataConverter = new RosterDataConverter();

		Integer recordsCreatedCount = 0;
		Integer recordsUpdatedCount = 0;
		Integer recordsRejectedCount = 0;
		String result = null;
		UserDetailImpl userDetailImpl = null;
		List<ValidateableRecord> rejectedRecords = new ArrayList<ValidateableRecord>();
		KSDEXMLAudit xmlAudit = null;
		try {
			userDetailImpl = rosterWebServiceScheduler.getInitializedUser();
			// Create a token and set the security context
	        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetailImpl, userDetailImpl.getPassword(), userDetailImpl.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(token);
	        
			Restriction restriction = rosterWebServiceScheduler
					.getRosterRestriction(userDetailImpl);

			ContractingOrganizationTree contractingOrganizationTree = rosterWebServiceScheduler
					.getOrganizationTree(userDetailImpl);

			webServiceStatus = RecordSaveStatus.CHECKED_AUTHORITIES;
			if (restriction == null) {
				throw new AartParseException(
						"No Permission to upload enrollment ");
			}
			webServiceStatus = RecordSaveStatus.VERIFIED_AUTHORITIES;
			// messages.put("fileOrigination", webServiceUrl.getCategoryName());
			Map<String, FieldSpecification> rosterFieldSpecMap = WebServiceHelper
					.getFieldSpecificationMap(rosterRecordType,
							fieldSpecificationDao);
			rosterConverter.setFieldSpecificationMap(rosterFieldSpecMap);
			rosterConverter
					.setKansasAssessmentInputNames(kansasAssessmentInputNames);
			xStream.alias("STCO_Data", RosterData.class);
			xStream.alias("STCO_Record", WebServiceRosterRecord.class);
			xStream.registerConverter(rosterConverter);
			xStream.registerConverter(rosterDataConverter);
			webServiceStatus = RecordSaveStatus.UNMARSHALLER_INITIALIZED;

			AuthenticationSoapHeader authenticationSoapHeader = new AuthenticationSoapHeader();
			authenticationSoapHeader
					.setUsername(scheduledWebServiceInputConfiguration
							.getWsConnectionUsername());
			authenticationSoapHeader
					.setPassword(scheduledWebServiceInputConfiguration
							.getWsConnectionPassword());

			if(xmlService.selectInProcessCount() == 0) {
				xmlAudit = xmlService.selectOneNotProcessed("STCO");
	
				webServiceStatus = RecordSaveStatus.WEB_SERVICE_CALL_SUCCESSFUL;
				if (null != xmlAudit) {
					result = xmlAudit.getXml();
					
					messages.put("recordPullStartTime", DateUtil.format(xmlAudit.getFromDate(), RosterByDateInputParameter.KANSAS_DATE_FORMAT));
					messages.put("recordPullEndTime", DateUtil.format(xmlAudit.getToDate(), RosterByDateInputParameter.KANSAS_DATE_FORMAT));
					xmlAudit.setProcessedCode("INPROCESS");
					xmlAudit.setProcessedDate(new Date());
					xmlService.updateByPrimaryKeySelective(xmlAudit);
					
					RosterData rostersData = (RosterData) xStream.fromXML(result);
					webServiceStatus = RecordSaveStatus.ROSTER_DATA_RECEIVED;
					long rostersCount = 1;
					long startTime = 1;
					int totalCount = rostersData.getRosterRecords().size();
					List<WebServiceRosterRecord> rostersData1 = new ArrayList<WebServiceRosterRecord>(rostersData.getRosterRecords());
					LOGGER.debug("number of rosters received : " + totalCount);
					//check if there are any 80 State subject area
					for (WebServiceRosterRecord rosterRecord : rostersData1) {
						if(rosterRecord.getStateSubjectCourseIdentifier() != null
								&& rosterRecord.getStateSubjectCourseIdentifier().startsWith("80")){
							//Read
							WebServiceRosterRecord rosterRecord1 = (WebServiceRosterRecord) deepCopy(rosterRecord);
							rosterRecord1.setStateSubjectCourseIdentifier(rosterRecord1.getStateSubjectCourseIdentifier().replaceFirst("80", "01"));
							rostersData.getRosterRecords().add(rosterRecord1);
							//Math
							rosterRecord1 = (WebServiceRosterRecord) deepCopy(rosterRecord);
							rosterRecord1.setStateSubjectCourseIdentifier(rosterRecord1.getStateSubjectCourseIdentifier().replaceFirst("80", "02"));
							rostersData.getRosterRecords().add(rosterRecord1);
							//Sci
							rosterRecord1 = (WebServiceRosterRecord) deepCopy(rosterRecord);
							rosterRecord1.setStateSubjectCourseIdentifier(rosterRecord1.getStateSubjectCourseIdentifier().replaceFirst("80", "03"));
							rostersData.getRosterRecords().add(rosterRecord1);
							//Hist
							rosterRecord.setStateSubjectCourseIdentifier(rosterRecord.getStateSubjectCourseIdentifier().replaceFirst("80", "04"));
						}
					}
					
					for (WebServiceRosterRecord rosterRecord : rostersData
							.getRosterRecords()) {
						startTime = System.currentTimeMillis();
						LOGGER.debug("started processing roster# " + rostersCount
								+ " of " + totalCount);
						if (rosterRecord.isDoReject()) {
							recordsRejectedCount++;
							rejectedRecords.add(rosterRecord);
						} else {
							if (StringUtils.isNotEmpty(rosterRecord
									.getStateSubjectCourseIdentifier())) {
								rosterRecord.setStateSubjectAreaCode(rosterRecord
										.getStateSubjectCourseIdentifier()
										.substring(0, 2));
								rosterRecord.setStateCourseCode(rosterRecord
										.getStateSubjectCourseIdentifier()
										.substring(2, 5));
							}
							rosterRecord.getEnrollment().setRestrictionId(
									restriction.getId());
							rosterRecord.setSchoolIdentifier(rosterRecord
									.getAttendanceSchoolProgramIdentifier());
							rosterRecord.setCurrentContextUserId(userDetailImpl
									.getUser().getId());
							rosterRecord.getEducator().setCurrentContextUserId(
									userDetailImpl.getUser().getId());
							rosterRecord.getStudent().setCurrentContextUserId(
									userDetailImpl.getUser().getId());
							rosterRecord.getStateCourse().setCurrentContextUserId(
									userDetailImpl.getUser().getId());
							userDetailImpl.getUser().setCurrentContextUserId(
									userDetailImpl.getUser().getId());
							SecurityContextHolder.getContext().setAuthentication(
									new AnonymousAuthenticationToken("AART",
											userDetailImpl, userDetailImpl
													.getAuthorities()));
							rosterRecord.setSourceType(SourceTypeEnum.STCOSWEBSERVICE.getCode());
							AssessmentProgram kapAssessmentProgram = assessmentProgramService.findByAbbreviatedName("KAP");
							rosterRecord.getStudent().setAssessmentProgramId(kapAssessmentProgram.getId());
							rosterRecord.getEducator().setAssessmentProgramId(kapAssessmentProgram.getId());
							rosterRecord = (WebServiceRosterRecord) rosterService
									.cascadeAddOrUpdate(rosterRecord,
											contractingOrganizationTree);
							if (rosterRecord.isDoReject()) {
								recordsRejectedCount++;
								rejectedRecords.add(rosterRecord);
							} else if (rosterRecord.isCreated()) {
								recordsCreatedCount++;
							} else {
								recordsUpdatedCount++;
							}
						}
						LOGGER.debug("Time taken for roster #" + rostersCount + " "
								+ (startTime - System.currentTimeMillis()));
						LOGGER.debug("completed processing roster# " + rostersCount
								+ " of " + totalCount);
						rostersCount++;
					}
					webServiceStatus = RecordSaveStatus.ROSTER_DATA_SAVED;
					if (rejectedRecords != null
							&& CollectionUtils.isNotEmpty(rejectedRecords)
							&& rejectedRecords.size() > maxRejectedRecords) {
						LOGGER.debug("More Rejected Records "
								+ rejectedRecords.size());
					}
	
					messages.put("recordsCreatedCount", recordsCreatedCount);
					messages.put("recordsUpdatedCount", recordsUpdatedCount);
					messages.put("recordsRejectedCount", recordsRejectedCount);
					webServiceStatus = RecordSaveStatus.ROSTER_NEXT_UPLOAD_SET;
					xmlAudit.setProcessedCode("COMPLETED");
					xmlAudit.setProcessedDate(new Date());
					xmlService.updateByPrimaryKeySelective(xmlAudit);
				}
			}
			messages.put("nextRunStartTime",
					rosterWebServiceScheduler.getScheduleInterval());
			messages.put(
					"nextRunStartTimeStamp",
					new Date((new Date()).getTime()
							+ rosterWebServiceScheduler.getScheduleInterval())
							.toString());
		} catch (Exception e) {
			LOGGER.error("Failed after " + webServiceStatus, e);
			messages.put("nextRunStartTime",
					rosterWebServiceScheduler.getRetryTime());
			messages.put(
					"nextRunStartTimeStamp",
					new Date((new Date()).getTime()
							+ rosterWebServiceScheduler.getRetryTime())
							.toString());
			rosterWebServiceScheduler.getWebServiceTimer().cancel();
			LOGGER.debug("Cancelled the current schedule");
			rosterWebServiceScheduler.reSchedule();
			LOGGER.debug("Re initialized all web service resources");
		} finally {
			// send the email irrespective of whether it fails or succeeds.
			messages.put("timeTaken", (new Date()).getTime()
					- startingTimeStamp.getTime());
			messages.put("endingTimeStamp", (new Date()).toString());
			messages.put("webServiceType", "roster");
			try {
				messages.put("fileReferenceMessage", messageSource.getMessage(
						"fileReferenceMessage", null, null));
				String rejectedReasons = UploadHelper.writeRejectedRecords(
						rejectedRecords,
						uploadSpecification.getErrorMessageFileHeaders(), messageSource);
				if (xmlAudit != null){
					xmlAudit.setErrors(rejectedReasons);
					xmlService.updateByPrimaryKeySelective(xmlAudit);
				}
			} catch (Exception e) {
				LOGGER.error("unknown exception in sending email For User"
						+ userDetailImpl, e);
			}
		}
	}
	
	public Object deepCopy(Object oldObj) throws Exception {
      ObjectOutputStream oos = null;
      ObjectInputStream ois = null;
      try {
         ByteArrayOutputStream bos = 
               new ByteArrayOutputStream(); // A
         oos = new ObjectOutputStream(bos); // B
         // serialize and pass the object
         oos.writeObject(oldObj);   // C
         oos.flush();               // D
         ByteArrayInputStream bin = 
               new ByteArrayInputStream(bos.toByteArray()); // E
         ois = new ObjectInputStream(bin);                  // F
         // return the new object
         return ois.readObject(); // G
      }
      catch(Exception e) {
         LOGGER.error("Exception in ObjectCloner = " , e);
         throw(e);
      }
      finally {
         oos.close();
         ois.close();
      }
	}

	/**
	 * clean all the timer resources.
	 */
	public final void cleanResources() {
		xStream = null;
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
