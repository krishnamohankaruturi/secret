/**
 * 
 */
package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thoughtworks.xstream.XStream;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.ksde.kids.client.AuthenticationSoapHeader;
import edu.ku.cete.ksde.kids.client.KIDSWebServiceSoap;
import edu.ku.cete.ksde.kids.result.KidConverter;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.ksde.kids.result.KidsByDateInputParameter;
import edu.ku.cete.ksde.kids.result.KidsData;
import edu.ku.cete.ksde.kids.result.KidsDataConverter;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.report.domain.KSDEXMLAudit;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.KSDEXMLAuditService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;

/**
 * @author m802r921
 * Left as a seperate task so than can be invoked by a
 * stand alone program or a web application.
 * TODO:- Why are all dependencies wiped clean for every run.
 * Trued controller context and component context.
 */
public class WebServiceTask extends TimerTask {
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(WebServiceTask.class);
    
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
	 * enrollment service.
	 */
	private EnrollmentService enrollmentService;
	/**
	 * category service.
	 */
	private CategoryService categoryService;
	/**
	 * kid.
	 */
	private Category kidRecordType;
	/**
	 * web service client.
	 */
	private KIDSWebServiceSoap client;
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
	 * TODO remove this reference. Keep the task isolated from scheduler.
	 */
	private WebServiceScheduler webServiceScheduler;

	/**
	 * webServiceRecordTypeCode.
	 */
	private String webServiceRecordTypeCode;
	
	/**
	 * webServiceRecordTypeCode.
	 */
	private String kansasWebServiceConfigTypeCode;
	
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

	private OrganizationService organizationService;

	private StudentService studentService;
	
	private KSDEXMLAuditService xmlService;
	
	/**
	 * @return the maxRejectedRecords
	 */
	public int getMaxRejectedRecords() {
		return maxRejectedRecords;
	}
	/**
	 * @param maxRejectedRecords the maxRejectedRecords to set
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
	 * @param scheduledWebServiceInputConfiguration the scheduledWebServiceInputConfiguration to set
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
	 * @param messageSource the messageSource to set
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
	 * @param uploadSpecification the uploadSpecification to set
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
	 * @param emailPath the emailPath to set
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
	 * @param webServUrl the webServiceUrl to set
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
	 * @param fieldSpecDao the fieldSpecificationDao to set
	 */
	public final void setFieldSpecificationDao(
			FieldSpecificationDao fieldSpecDao) {
		this.fieldSpecificationDao = fieldSpecDao;
	}
	/**
	 * @return the kidRecordType
	 */
	public final Category getKidRecordType() {
		return kidRecordType;
	}

	/**
	 * @param kidRecType the kidRecordType to set
	 */
	public final void setKidRecordType(Category kidRecType) {
		this.kidRecordType = kidRecType;
	}
	/**
	 * @return the client
	 */
	public final KIDSWebServiceSoap getClient() {
		return client;
	}
	/**
	 * @param wsClient the client to set
	 */
	public final void setClient(KIDSWebServiceSoap wsClient) {
		this.client = wsClient;
	}
	/**
	 * @return the enrollmentService
	 */
	public final EnrollmentService getEnrollmentService() {
		return enrollmentService;
	}
	/**
	 * @param enrollmentService the enrollmentService to set
	 */
	public final void setEnrollmentService(EnrollmentService enrollmentService) {
		this.enrollmentService = enrollmentService;
	}
	/**
	 * @return the categoryService
	 */
	public CategoryService getCategoryService() {
		return categoryService;
	}
	/**
	 * @param categoryService the categoryService to set
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
	 * @param xStreamParser the xStream to set
	 */
	public final void setxStream(XStream xStreamParser) {
		this.xStream = xStreamParser;
	}
	/**
	 * @return the organizationService
	 */
	public final OrganizationService getOrganizationService() {
		return organizationService;
	}
	/**
	 * @param organizationService the organizationService to set
	 */
	public final void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	/**
	 * @return the webServiceRecordTypeCode
	 */
	public String getWebServiceRecordTypeCode() {
		return webServiceRecordTypeCode;
	}	
	/**
	 * @param webServiceRecordTypeCode the webServiceRecordTypeCode to set
	 */
	public void setWebServiceRecordTypeCode(String webServiceRecordTypeCode) {
		this.webServiceRecordTypeCode = webServiceRecordTypeCode;
	}
	/**
	 * @return the KansasWebServiceScheduleFrequencyCode
	 */
	public String getKansasWebServiceConfigTypeCode() {
		return kansasWebServiceConfigTypeCode;
	}
	/**
	 * @param KansasWebServiceConfigTypeCode the KansasWebServiceConfigTypeCode to set
	 */
	public void setKansasWebServiceConfigTypeCode(String kansasWebServiceConfigTypeCode) {
		this.kansasWebServiceConfigTypeCode = kansasWebServiceConfigTypeCode;
	}
	/**
	 * @param webServScheduler {@link WebServiceScheduler}
	 */
	public final void setWebServiceScheduler(WebServiceScheduler webServScheduler) {
		this.webServiceScheduler = webServScheduler;
	}
	/**
	 * @return the kansasAssessmentInputNames
	 */
	public Map<String, Category> getKansasAssessmentInputNames() {
		return kansasAssessmentInputNames;
	}
	/**
	 * @param kansasAssessmentInputNames the kansasAssessmentInputNames to set
	 */
	public void setKansasAssessmentInputNames(
			Map<String, Category> kansasAssessmentInputNames) {
		this.kansasAssessmentInputNames = kansasAssessmentInputNames;
	}
	/**
	 * @return the timer
	 */
	public final WebServiceScheduler getWebServiceScheduler() {
		return webServiceScheduler;
	}
	/**
	 * @return the emailService
	 */
	public final EmailService getEmailService() {
		return emailService;
	}
	/**
	 * @param emailServ the emailService to set
	 */
	public final void setEmailService(EmailService emailServ) {
		this.emailService = emailServ;
	}
	/**
	 * @return the studentService
	 */
	public StudentService getStudentService() {
		return studentService;
	}
	/**
	 * @param studentService the studentService to set
	 */
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	/**
	 * test.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void invokeWebService() {
		RecordSaveStatus webServiceStatus = RecordSaveStatus.BEGIN_WEBSERVICE_UPLOAD;
		Date startingTimeStamp = new Date();
        Map<String, Object> messages = WebServiceHelper.getInitializedMessages();
		
		KidConverter kidConverter = new KidConverter();
		KidsDataConverter kidsDataConverter = new KidsDataConverter();
		
		Integer recordsCreatedCount = 0 ;
		Integer recordsUpdatedCount = 0 ;
		Integer recordsRejectedCount = 0 ;
		String result = null;
		UserDetailImpl userDetailImpl = null;
		List<ValidateableRecord> rejectedRecords = new ArrayList<ValidateableRecord>();
		KSDEXMLAudit xmlAudit = null;
		try {
			userDetailImpl = webServiceScheduler.getInitializedUser();
			// Create a token and set the security context
	        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetailImpl, userDetailImpl.getPassword(), userDetailImpl.getAuthorities());
	        SecurityContextHolder.getContext().setAuthentication(token);
            ContractingOrganizationTree contractingOrganizationTree = organizationService.getTree(
            		userDetailImpl.getUser().getOrganization());			
			Restriction restriction = webServiceScheduler.getEnrollmentRestriction(userDetailImpl);
			webServiceStatus = RecordSaveStatus.CHECKED_AUTHORITIES;
			if (restriction == null) {
				throw new AartParseException("No Permission to upload enrollment ");
			}
			webServiceStatus = RecordSaveStatus.VERIFIED_AUTHORITIES;
	        messages.put("fileOrigination",webServiceUrl.getCategoryName());
			Map<String, FieldSpecification> kidFieldSpecMap = WebServiceHelper.getFieldSpecificationMap(kidRecordType, fieldSpecificationDao);
			kidConverter.setFieldSpecificationMap(kidFieldSpecMap);
			kidConverter.setKansasAssessmentInputNames(kansasAssessmentInputNames);
			xStream.registerConverter(kidConverter);
			xStream.registerConverter(kidsDataConverter);
			webServiceStatus = RecordSaveStatus.UNMARSHALLER_INITIALIZED;

			//Get the input parameters in a map.
			Map<String, Category> kidsByDateInputParameterMap = WebServiceHelper.getKidsByDateInputParameterMap(
					scheduledWebServiceInputConfiguration,
					webServiceRecordTypeCode,
					categoryService, fieldSpecificationDao);
			//get the input parameter in the object.
			KidsByDateInputParameter kidsByDateInputParameter = WebServiceHelper.getKidsByDateInputParameter(
					scheduledWebServiceInputConfiguration,
					kidsByDateInputParameterMap,
					categoryService, fieldSpecificationDao);
	        messages.put("recordPullStartTime", kidsByDateInputParameter.getStrFrom());
	        messages.put("recordPullEndTime", kidsByDateInputParameter.getStrTo());
	        if (kidsByDateInputParameter.getStrToDate().before(new Date())) {
		        //Get the frequency values in a map.
				Map<String, Category> kidsByDateFrequencyParameterMap
				= WebServiceHelper.getKidsByDateFrequencyMap(
						uploadSpecification,
						kansasWebServiceConfigTypeCode,
						categoryService, fieldSpecificationDao);
				
	
				Holder<String> strFromDateHolder = new Holder<String>(kidsByDateInputParameter.getStrFrom());
				Holder<Boolean> bolRequestComplete = new Holder<Boolean>(false);
				//Holder<String> getSCRSByDateResult = new Holder<String>("");
				Holder<String> getKidsByDateResult = new Holder<String>("");
				AuthenticationSoapHeader authenticationSoapHeader
				= new AuthenticationSoapHeader();
				authenticationSoapHeader.setUsername(
						scheduledWebServiceInputConfiguration.getWsConnectionUsername());
				authenticationSoapHeader.setPassword(
						scheduledWebServiceInputConfiguration.getWsConnectionPassword());
				int countOfKidsByDate
				= client.countKidsByDate(kidsByDateInputParameter.getStrFrom(),
						kidsByDateInputParameter.getStrTo(), kidsByDateInputParameter.getStrCurrentSchoolYear(), authenticationSoapHeader);
	
				LOGGER.debug("Count Kids by date is successsful " + countOfKidsByDate);
				LOGGER.debug("Before making call with startdate: " + strFromDateHolder + "  and enddate: " + 
						kidsByDateInputParameter.getStrTo() + " for school year: " + kidsByDateInputParameter.getStrCurrentSchoolYear());
				
				client.getKidsByDate(strFromDateHolder, kidsByDateInputParameter.getStrTo(),
						kidsByDateInputParameter.getStrCurrentSchoolYear(),
						bolRequestComplete, authenticationSoapHeader,
						getKidsByDateResult);
				
				LOGGER.debug("After making call with startdate: " + strFromDateHolder + "  and enddate: " + 
						kidsByDateInputParameter.getStrTo() + " for school year: " + kidsByDateInputParameter.getStrCurrentSchoolYear());
				webServiceStatus = RecordSaveStatus.WEB_SERVICE_CALL_SUCCESSFUL;
	
				//kidsByDateInputParameter.setStrToDate(strFromDateHolder.value);
		        messages.put("recordPullEndTime",
		        		kidsByDateInputParameter.getStrTo());
	
				result = getKidsByDateResult.value;
				xmlAudit = saveXML(result, kidsByDateInputParameter.getStrFromDate(), kidsByDateInputParameter.getStrToDate());
				
				KidsData kidsData = (KidsData) xStream.fromXML(result);
				WebServiceHelper.validateESOLFields(kidsData);
				webServiceStatus = RecordSaveStatus.KIDS_DATA_RECEIVED;
				List<? extends StudentRecord> studentRecords = studentService.verifyStateStudentIdentifiersExist(
		        		contractingOrganizationTree.getContractingOrganizationTreeIds(),
		        		contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
		        		contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(),
		        		kidsData.getKids());
	
	            for (StudentRecord studentRecord : studentRecords) {
	            	KidRecord kidRecord = (KidRecord) (studentRecord);
					if (kidRecord.isDoReject()) {
						recordsRejectedCount ++;
						rejectedRecords.add(kidRecord);
					} else {
						kidRecord.getEnrollment().setRestrictionId(restriction.getId());
						kidRecord.getEnrollment().setSourceType(SourceTypeEnum.TESTWEBSERVICE.getCode());
						kidRecord.setCurrentContextUserId(userDetailImpl.getUser().getId());
						userDetailImpl.getUser().setCurrentContextUserId(userDetailImpl.getUser().getId());
						kidRecord = enrollmentService.cascadeAddOrUpdate(kidRecord,
								contractingOrganizationTree, userDetailImpl.getUser(), false);
						if (kidRecord.isDoReject()) {
							recordsRejectedCount ++;
							rejectedRecords.add(kidRecord);
						}
						else if (kidRecord.isCreated()) {
							recordsCreatedCount ++;
						}
						else {
							recordsUpdatedCount ++;
						}
					}
				}
				webServiceStatus = RecordSaveStatus.KIDS_DATA_SAVED;
				if (rejectedRecords != null && CollectionUtils.isNotEmpty(rejectedRecords)
						&& rejectedRecords.size() > maxRejectedRecords) {
					LOGGER.debug("More Rejected Records " + rejectedRecords.size());
				}
				
				//kidsByDateInputParameter.changeInputForNextRun(kidsByDateFrequencyParameterMap, uploadSpecification);
				kidsByDateInputParameter.changeInputForNextRun(kidsByDateFrequencyParameterMap, uploadSpecification, strFromDateHolder.value, bolRequestComplete.value);
				
				Category categoryEndTime = kidsByDateInputParameterMap.get(
						scheduledWebServiceInputConfiguration.getKansasWebServiceEndTime());
				categoryEndTime.setCurrentContextUserId(userDetailImpl.getUser().getId());
				categoryEndTime.setAuditColumnProperties();
				kidsByDateInputParameterMap.put(scheduledWebServiceInputConfiguration.getKansasWebServiceEndTime(), categoryEndTime);
				
				Category categoryStartTime = kidsByDateInputParameterMap.get(
						scheduledWebServiceInputConfiguration.getKansasWebServiceStartTime());
				categoryStartTime.setCurrentContextUserId(userDetailImpl.getUser().getId());
				categoryStartTime.setAuditColumnProperties();
				kidsByDateInputParameterMap.put(scheduledWebServiceInputConfiguration.getKansasWebServiceStartTime(), categoryStartTime);
				
				WebServiceHelper.updateKidsByDateInputParameter(
						scheduledWebServiceInputConfiguration, kidsByDateInputParameterMap,
						categoryService, kidsByDateInputParameter);
	        } else {
	        	//skip if end time is not reached
	        	LOGGER.debug("Skipped End time " + kidsByDateInputParameter.getStrToDate()
						+ "is not passed the current time " + new Date());
				
				messages.put("Skipped", "End time " + kidsByDateInputParameter.getStrToDate()
						+ "is not passed the current time " + new Date());
	        }
	        messages.put("recordsCreatedCount", recordsCreatedCount);
	        messages.put("recordsUpdatedCount", recordsUpdatedCount);
	        messages.put("recordsRejectedCount", recordsRejectedCount);
			messages.put("nextRunStartTime", webServiceScheduler.getScheduleInterval());
			messages.put("nextRunStartTimeStamp",
					new Date((new Date()).getTime() + webServiceScheduler.getScheduleInterval()).toString());
			webServiceStatus = RecordSaveStatus.KIDS_NEXT_UPLOAD_SET;
		} catch (Exception e) {
			LOGGER.error("Failed after " + webServiceStatus, e);
			messages.put("nextRunStartTime", webServiceScheduler.getRetryTime());
			messages.put("nextRunStartTimeStamp",
					new Date((new Date()).getTime() + webServiceScheduler.getRetryTime()).toString());
			if (webServiceStatus.equals(RecordSaveStatus.KIDS_DATA_RECEIVED)) {
				//print the result to help operations.
				LOGGER.error("Potentially invalid data");
			} else {
				//reinitialize all the webservice resources.
				webServiceScheduler.getWebServiceTimer().cancel();
				LOGGER.debug("Cancelled the current schedule");
				webServiceScheduler.reSchedule();
				LOGGER.debug("Re initialized all web service resources");
			}
		} finally {
			//send the email irrespective of whether it fails or succeeds.
			//TODO These messages to enum 
	        messages.put("timeTaken", (new Date()).getTime() - startingTimeStamp.getTime());
	        messages.put("endingTimeStamp", (new Date()).toString());
	        messages.put("webServiceType", "kid");
	        try {
		        messages.put("fileReferenceMessage", messageSource.getMessage("fileReferenceMessage", null, null));
		        String rejectedReasons = UploadHelper.writeRejectedRecords(rejectedRecords,
		        		uploadSpecification.getErrorMessageFileHeaders(),
		        		messageSource);
		        if(xmlAudit != null) {
		        	xmlAudit.setErrors(rejectedReasons);
		        	xmlAudit.setProcessedDate(new Date());
					xmlService.updateByPrimaryKeySelective(xmlAudit);
		        }
			} catch (Exception e) {
				LOGGER.error("unknown exception in sending email For User" + userDetailImpl,
						e);
			}
		}
	}
	
	private KSDEXMLAudit saveXML(String result, Date fromDate, Date toDate) {	
		KSDEXMLAudit xmlRecord = new KSDEXMLAudit();
		xmlRecord.setXml(result);
		xmlRecord.setType("KIDS");
		xmlRecord.setCreateDate(new Date());
		xmlRecord.setProcessedCode("COMPLETED");
		xmlRecord.setFromDate(fromDate);
		xmlRecord.setToDate(toDate);
		xmlService.insert(xmlRecord);
		return xmlRecord;
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
		LOGGER.debug(
				"Starting at time " + new Date());
		try {
			if (
					webServiceScheduler.resetScheduleInterval() || 
					webServiceScheduler.resetRetryTime()
					) {
				LOGGER.warn("The schedule has changed.");
				//cancel the current scheduled timer.
				webServiceScheduler.getWebServiceTimer().cancel();
				webServiceScheduler.changeSchedule();
			} else {
				LOGGER.debug("The schedule has not changed.");
			}
			//Proceed further, only when the schedule is turned on.
			if(uploadSpecification.getKansasScheduledWebServiceSwitch() != null  &&
					CommonConstants.KANSAS_SCHEDULED_WEB_SERVICE_SWITCH_ON.equalsIgnoreCase(
							uploadSpecification.getKansasScheduledWebServiceSwitch())) {	
				LOGGER.debug("The schedule is turned ON.");
				invokeWebService();
			} else {
				LOGGER.debug("The schedule is turned OFF.");
			}
		} catch (Exception e) {
			LOGGER.error("Run failed", e);
		}
	}
	public KSDEXMLAuditService getXmlService() {
		return xmlService;
	}
	public void setXmlService(KSDEXMLAuditService xmlService) {
		this.xmlService = xmlService;
	}

}
