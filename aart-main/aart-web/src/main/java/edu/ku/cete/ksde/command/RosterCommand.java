/**
 * 
 */

package edu.ku.cete.ksde.command;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.ws.Holder;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.thoughtworks.xstream.XStream;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserRecord;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.ksde.kids.client.AuthenticationSoapHeader;
import edu.ku.cete.ksde.kids.client.KIDSWebServiceSoap;
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
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.KSDEXMLAuditService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.ImmediateWebServiceInputConfiguration;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.UploadHelper;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.WebServiceHelper;

/**
 * 
 * 
 */
@Component
public class RosterCommand implements WebServiceCommand {

    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(RosterCommand.class);
	/**
	 * Field specification dao.
	 */
	@Autowired
	private FieldSpecificationDao fieldSpecificationDao;
	
	@Autowired
	private ImmediateWebServiceInputConfiguration immediateWebServiceInputConfiguration;
    /**
	 * the specification that has field limits, allowable values etc.
	 */
	@Autowired
	private UploadSpecification uploadSpecification;
	/**
	 * set the metadata.
	 */
	@Autowired
	private CategoryService categoryService;
	/**
	 * set the category dao.
	 */
	@Autowired
	private CategoryDao categoryDao;
	/**
	 * enrollment service.
	 */
	@Autowired
	private EnrollmentService enrollmentService;
		
	/**
	 * Roster service.
	 */
	@Autowired
	private UserService userService;
	
	
	/**
	 * Roster service.
	 */
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * Roster service.
	 */
	@Autowired
	private RosterService rosterService;
	
	@Autowired
    private MessageSource messageSource;
	
	/**
	 * roster.
	 */
	private Category rosterRecordType;
	
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
	 * web service url.
	 */
	private Category webServiceUrl;
    /**
     * emailService.
     */
    @Autowired
    private EmailService emailService;
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
    @Value("${email.path}")
    private String emailPath;
    @Value("${email.maxRejectedRecords}")
	private int maxRejectedRecords;
	/**
	 * {@link StudentService}
	 */
    @Autowired
	private StudentService studentService;
    
    @Autowired
    private KSDEXMLAuditService xmlService;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
	/**
	 * initialization.
	 */
	@PostConstruct
	public void setMetaData() {
		setRecordType();
		//setKansasAssessments(); - Check with Mahesh
		setWebServiceUrl();
		client = WebServiceHelper.getWebServiceClient(webServiceUrl);
		xStream = WebServiceHelper.getUnMarshaller();
	}

	/**
	 * Sets what record type is scrs and what is enrollment and more.
	 */
	private void setRecordType() {
		this.rosterRecordType = categoryService.selectByCategoryCodeAndType(uploadSpecification.getRosterRecordType(),
				uploadSpecification.getWebServiceRecordTypeCode());
	}
	/**
	 * Sets what record type is scrs and what is enrollment and more.
	 */
	private void setWebServiceUrl() {
		//currently there is only kansas.For more urls add drop down.
		this.webServiceUrl
		= categoryService.selectByCategoryCodeAndType(
				uploadSpecification.getKansasWebServiceUrlCode(),
				uploadSpecification.getKansasWebServiceConfigTypeCode());
	}	
    
    /**
     * TODO
     * @param userDetails {@link UserDetailImpl}
     * @return {@link Restriction}
     */
    private Restriction getRosterRestriction(UserDetailImpl userDetails) {
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
	 *
	 *
	 */
	public ModelAndView execute(ModelAndView mv) {
		RecordSaveStatus webServiceStatus = RecordSaveStatus.BEGIN_WEBSERVICE_UPLOAD;
		Date startingTimeStamp = new Date();
        Map<String, Object> messages = WebServiceHelper.getInitializedMessages();
        messages.put("startingTimeStamp", startingTimeStamp.toString());
        //UserDetailImpl userDetails = null;
        UserDetailImpl user = null;
        
        mv.addObject("uploadCompleted", true);		
        
		//Instantiate the ScrsConverter.
		RosterConverter rosterConverter = new RosterConverter();
		RosterDataConverter rosterDataConverter = new RosterDataConverter();
				
		Integer recordsCreatedCount = 0 ;
		Integer recordsUpdatedCount = 0 ;
		Integer recordsRejectedCount = 0 ;
		String result = null;
		Restriction restriction = null;
		List<ValidateableRecord> rejectedRecords = new ArrayList<ValidateableRecord>();
		KSDEXMLAudit xmlAudit = null;
		try {
	        //userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	        	       	      
	        	       	       
	        user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	        
            Organization currentContext = user.getUser().getOrganization();//userService.getOrganizations(user.getUserId()).get(0);
	        ContractingOrganizationTree
	          contractingOrganizationTree = organizationService.getTree(currentContext);
	        restriction = getRosterRestriction(user);
			webServiceStatus
			= RecordSaveStatus.CHECKED_AUTHORITIES;
	        if (restriction == null) {
	        	LOGGER.error("the user " + user + " is not allowed to do webservice upload");
	        	throw new AartParseException(user + " Not allowed to do web service upload");
	        }
			webServiceStatus
			= RecordSaveStatus.VERIFIED_AUTHORITIES;
	        messages.put("fileOrigination", webServiceUrl.getCategoryName());
			Map<String, FieldSpecification>
			rosterFieldSpecMap = WebServiceHelper.getFieldSpecificationMap(this.rosterRecordType, fieldSpecificationDao);

			//TODO create a converter for scrs record type.
			rosterConverter.setFieldSpecificationMap(rosterFieldSpecMap);
			//rosterConverter.setKansasAssessmentInputNames(kansasAssessmentInputNames);
			xStream.alias("STCO_Data", RosterData.class);
	        xStream.alias("STCO_Record", WebServiceRosterRecord.class);
	        
			xStream.registerConverter(rosterConverter);
			xStream.registerConverter(rosterDataConverter);
						
			webServiceStatus = RecordSaveStatus.UNMARSHALLER_INITIALIZED;

			//TODO create a converter for Roster record type.
			//Get the input parameters in a map.
			//TODO if passing DAO or service is required then this needs to be in a service.
			Map<String, Category> rosterByDateInputParameterMap
			= WebServiceHelper.getRosterByDateInputParameterMap(
					immediateWebServiceInputConfiguration,
					uploadSpecification.getWebServiceRecordTypeCode(),
					categoryService, fieldSpecificationDao);
			//get the input parameter in the object.
			RosterByDateInputParameter rosterByDateInputParameter
			= WebServiceHelper.getRosterByDateInputParameter(
					immediateWebServiceInputConfiguration,
					rosterByDateInputParameterMap,
					categoryService, fieldSpecificationDao);

			//Get the frequency values in a map.
			Map<String, Category> rosterByDateFrequencyParameterMap
			= WebServiceHelper.getRosterByDateFrequencyMap(
					uploadSpecification,
					uploadSpecification.getKansasWebServiceConfigTypeCode(),
					categoryService, fieldSpecificationDao);
			
			mv.addObject("recordPullStartTime",
					rosterByDateInputParameter.getStrRosterFromDate());
	        mv.addObject("recordPullEndTime",
	        		rosterByDateInputParameter.getStrRosterToDate());
	        messages.put("recordPullStartTime",
	        		rosterByDateInputParameter.getStrRosterFromDate());
	        messages.put("recordPullEndTime",
	        		rosterByDateInputParameter.getStrRosterToDate());
	        
			Holder<String> strFromDateHolder = new Holder<String>(rosterByDateInputParameter.getStrRosterFromDate());
			Holder<Boolean> bolRequestComplete = new Holder<Boolean>(false);
			Holder<String> getRostersByDateResult = new Holder<String>("");
			
			AuthenticationSoapHeader authenticationSoapHeader
			= new AuthenticationSoapHeader();
			authenticationSoapHeader.setUsername(
					immediateWebServiceInputConfiguration.getWsConnectionUsername());
			authenticationSoapHeader.setPassword(
					immediateWebServiceInputConfiguration.getWsConnectionPassword());			
			
			//TODO put client.getSCRSByDate
			/*int countOfRostersByDate
			= client.countKidsByDate(rosterByDateInputParameter.getStrFrom(),  // Need to check with Mahesh whether we can use countKidsByDate() method or not 
					rosterByDateInputParameter.getStrTo(), rosterByDateInputParameter.getStrCurrentSchoolYear(),
					authenticationSoapHeader);*/

			//LOGGER.debug("Count Rosters by date is successsful " + countOfRostersByDate);
			client.getSTCOByDate(strFromDateHolder, rosterByDateInputParameter.getStrRosterToDate(),
					rosterByDateInputParameter.getStrCurrentSchoolYear(),
					bolRequestComplete, authenticationSoapHeader,
					getRostersByDateResult);
			
			webServiceStatus = RecordSaveStatus.WEB_SERVICE_CALL_SUCCESSFUL;
			
			//rosterByDateInputParameter.setStrToDate(strFromDateHolder.value);
	        messages.put("recordPullEndTime",
	        		rosterByDateInputParameter.getStrRosterToDate());

			result = getRostersByDateResult.value;
			
			xmlAudit = saveXML( result, rosterByDateInputParameter.getRosterFromDate(),rosterByDateInputParameter.getRosterToDate());
			RosterData rosterData = (RosterData) xStream.fromXML(result);
			List<WebServiceRosterRecord> rostersData = new ArrayList<WebServiceRosterRecord>(rosterData.getRosterRecords());
			//check if there are any 80 State subject area
			for (WebServiceRosterRecord rosterRecord : rostersData) {
				if(rosterRecord.getStateSubjectCourseIdentifier() != null
						&& rosterRecord.getStateSubjectCourseIdentifier().startsWith("80")){
					//Read
					WebServiceRosterRecord rosterRecord1 = (WebServiceRosterRecord) deepCopy(rosterRecord);
					rosterRecord1.setStateSubjectCourseIdentifier(rosterRecord1.getStateSubjectCourseIdentifier().replaceFirst("80", "01"));
					rosterData.getRosterRecords().add(rosterRecord1);
					//Math
					rosterRecord1 = (WebServiceRosterRecord) deepCopy(rosterRecord);
					rosterRecord1.setStateSubjectCourseIdentifier(rosterRecord1.getStateSubjectCourseIdentifier().replaceFirst("80", "02"));
					rosterData.getRosterRecords().add(rosterRecord1);
					//Sci
					rosterRecord1 = (WebServiceRosterRecord) deepCopy(rosterRecord);
					rosterRecord1.setStateSubjectCourseIdentifier(rosterRecord1.getStateSubjectCourseIdentifier().replaceFirst("80", "03"));
					rosterData.getRosterRecords().add(rosterRecord1);
					//Hist
					rosterRecord.setStateSubjectCourseIdentifier(rosterRecord.getStateSubjectCourseIdentifier().replaceFirst("80", "04"));
				}
			}
			
			webServiceStatus = RecordSaveStatus.ROSTER_DATA_RECEIVED;
	        List<? extends StudentRecord> studentRecords = studentService.verifyStateStudentIdentifiersExist(
	        		contractingOrganizationTree.getContractingOrganizationTreeIds(),
	        		contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
	        		contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(),
	        		rosterData.getRosterRecords());
	        //TODO for the time being this relies on same JVM execution for the marking on roster records.
	        //create a record called UploadedRecord and have all uploaded records extend that.
	        List<? extends UserRecord> userRecords = userService.verifyEducatorIds(rosterData.getRosterRecords(),
	        		contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy()); // TODO need to remove no use of calling

			
            for (StudentRecord studentRecord : studentRecords) {
				WebServiceRosterRecord rosterRecord = (WebServiceRosterRecord) (studentRecord);
				if (rosterRecord.isDoReject()) {
					recordsRejectedCount ++;
					rejectedRecords.add(rosterRecord);
				} else {
					if(StringUtils.isNotEmpty(rosterRecord.getStateSubjectCourseIdentifier())) {
						rosterRecord.setStateSubjectAreaCode(rosterRecord.getStateSubjectCourseIdentifier().substring(0, 2));
						rosterRecord.setStateCourseCode(rosterRecord.getStateSubjectCourseIdentifier().substring(2, 5));						
					}
					rosterRecord.getEnrollment().setRestrictionId(restriction.getId());
					//rosterRecord.getEnrollment().setAypSchoolIdentifier(rosterRecord.getAccountabilityschoolidentifier());					
					rosterRecord.setSchoolIdentifier(rosterRecord.getAttendanceSchoolProgramIdentifier());
					rosterRecord.setCurrentContextUserId(user.getUser().getId());
					rosterRecord.getEducator().setCurrentContextUserId(user.getUser().getId());
					rosterRecord.getStudent().setCurrentContextUserId(user.getUser().getId());
					rosterRecord.getStateCourse().setCurrentContextUserId(user.getUser().getId());					
					user.getUser().setCurrentContextUserId(user.getUser().getId());
					rosterRecord.setSourceType(SourceTypeEnum.STCOSWEBSERVICE.getCode());
					AssessmentProgram kapAssessmentProgram = assessmentProgramService.findByAbbreviatedName("KAP");
					rosterRecord.getStudent().setAssessmentProgramId(kapAssessmentProgram.getId());
					rosterRecord.getEducator().setAssessmentProgramId(kapAssessmentProgram.getId());					
					//Webservice roster record is just the same as Roster record so it is treated the same way.
					rosterRecord = (WebServiceRosterRecord) rosterService.cascadeAddOrUpdate(rosterRecord,
							contractingOrganizationTree);
					if (rosterRecord.isDoReject()) {
						recordsRejectedCount ++;
						rejectedRecords.add(rosterRecord);
					} else if (rosterRecord.isCreated()) {
						recordsCreatedCount ++;
					}  else {
						recordsUpdatedCount ++;
					}
				}
			}
			if (rejectedRecords != null && CollectionUtils.isNotEmpty(rejectedRecords)
					&& rejectedRecords.size() > maxRejectedRecords) {
				LOGGER.debug("More Rejected Records " + rejectedRecords.size());
			}
			
			webServiceStatus = RecordSaveStatus.ROSTER_DATA_SAVED;
			
			//rosterByDateInputParameter.changeInputForNextRun(rosterByDateFrequencyParameterMap, uploadSpecification);
			
			rosterByDateInputParameter.changeInputForNextRun(
					rosterByDateFrequencyParameterMap, uploadSpecification,
					strFromDateHolder.value, bolRequestComplete.value);
			
			//TODO why pass DAO and use helper instead of using say categoryService ?
			//INFO this is done here instead of helper because WS task does not have spring context.
			rosterByDateInputParameterMap.get(
					immediateWebServiceInputConfiguration.getRosterWebServiceEndTime())
					.setAuditColumnProperties();
			rosterByDateInputParameterMap.get(
					immediateWebServiceInputConfiguration.getRosterWebServiceStartTime())
					.setAuditColumnProperties();
			
			WebServiceHelper.updateRostersByDateInputParameter(
					immediateWebServiceInputConfiguration, rosterByDateInputParameterMap,
					categoryService, rosterByDateInputParameter);

	        mv.addObject("rosterRecordsCreatedCount", recordsCreatedCount);
	        mv.addObject("rosterRecordsUpdatedCount", recordsUpdatedCount);
	        mv.addObject("rosterRecordsRejectedCount", recordsRejectedCount);
	        mv.addObject("rosterRecordsCount", rosterData.getRosterRecords().size());

	        messages.put("recordsCreatedCount", recordsCreatedCount);
	        messages.put("recordsUpdatedCount", recordsUpdatedCount);
	        messages.put("recordsRejectedCount", recordsRejectedCount);
	        messages.put("recordsCount", rosterData.getRosterRecords().size());
	        
			mv.addObject("rosterUploadSuccessful", true);
			webServiceStatus = RecordSaveStatus.ROSTER_NEXT_UPLOAD_SET;
		} catch (Exception e) {
			//catching the exception to prevent indefinite reruns in the thread.
			LOGGER.error("Failed after " + webServiceStatus, e);
			mv.addObject("rosterUploadSuccessful", false);
			//reinitialize every thing to account for all possibilities.
			if (webServiceStatus.equals(RecordSaveStatus.ROSTER_DATA_RECEIVED)) {
				LOGGER.error("Potentially invalid data");
			} else {
				setMetaData();
			}
		} finally {
			//send the message irrespective of whether it succeeds or fails.
	        messages.put("timeTaken", (new Date()).getTime() - startingTimeStamp.getTime());
	        messages.put("endingTimeStamp", (new Date()).toString());
	        messages.put("fileReferenceMessage", messageSource.getMessage("fileReferenceMessage", null, null));
	        messages.put("webServiceType", "roster");
	        if (rejectedRecords.size() > 0){
		        try {
			        String rejectedReasons = UploadHelper.writeRejectedRecords(rejectedRecords,
			        		uploadSpecification.getErrorMessageFileHeaders(), messageSource);
					xmlAudit.setErrors(rejectedReasons);
					xmlService.updateByPrimaryKeySelective(xmlAudit);
				} catch (Exception e) {
					LOGGER.error("unknown exception in sending email ", e);
				}
	        }
		}
		
		return mv;
	}
	
	private KSDEXMLAudit saveXML(String result, Date fromDate, Date toDate) {	
		KSDEXMLAudit xmlRecord = new KSDEXMLAudit();
		xmlRecord.setXml(result);
		xmlRecord.setType("STCO");
		xmlRecord.setCreateDate(new Date());
		xmlRecord.setProcessedCode("COMPLETED");
		xmlRecord.setFromDate(fromDate);
		xmlRecord.setToDate(toDate);
		xmlService.insert(xmlRecord);
		return xmlRecord;
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
}
