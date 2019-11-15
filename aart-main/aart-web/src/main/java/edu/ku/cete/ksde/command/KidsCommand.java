package edu.ku.cete.ksde.command;

import java.util.Date;
import java.util.Map;

import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.ksde.kids.KidsSettings;
import edu.ku.cete.ksde.kids.client.AuthenticationSoapHeader;
import edu.ku.cete.ksde.kids.client.KIDSWebServiceSoap;
import edu.ku.cete.ksde.rosters.result.RosterByDateInputParameter;
import edu.ku.cete.report.domain.KSDEXMLAudit;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.KSDEXMLAuditService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.AartResource;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.WebServiceHelper;

@Component
public class KidsCommand implements WebServiceCommand{	
	
	/**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(KidsCommand.class);
	/**
	 * Field specification dao.
	 */
    @Value("${wsAdminUserName}")
	private String wsAdminUserName;

	@Value("${kansasDateFormat}")
	private String kansasDateFormat;

	@Value("${webServiceRecordTypeCode}")
	private String webServiceRecordTypeCode;

	@Value("${kansasWebServiceUrlCode}")
	private String kansasWebServiceUrlCode;

	@Value("${kansasWebServiceConfigTypeCode}")
	private String kansasWebServiceConfigTypeCode;

	@Value("${kansasScheduledWebServiceStartTime}")
	private String kansasScheduledWebServiceStartTime;

	@Value("${kansasScheduledWebServiceEndTime}")
	private String kansasScheduledWebServiceEndTime;

	@Autowired
	private UploadSpecification uploadSpecification;

	@Autowired
	private KidsSettings kidsSettings;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private KSDEXMLAuditService xmlService;

	@Autowired
	private PermissionUtil permissionUtil;

	@Autowired
	private RestrictedResourceConfiguration restrictedResourceConfiguration;

	@Autowired
	private ResourceRestrictionService resourceRestrictionService;

	@Autowired
	private UserService userService;

    /**
	 * 
	 * 
	 */
    public ModelAndView execute(ModelAndView mv) {
    	String runTimeStamp = new Date().toString();
		// KSDE Process
		LOGGER.info("Started executing BatchGetDataFromKSDE (" + runTimeStamp + ")");
		Category webServiceUrl = categoryService.selectByCategoryCodeAndType(kansasWebServiceUrlCode,
				kansasWebServiceConfigTypeCode);
		
		mv = new ModelAndView(AartResource.WEB_SERVICE_FOLDER
				+ java.io.File.separator + AartResource.IMMEDIATE_UPLOAD);
		mv.addObject("uploadCompleted", true);
		
		KIDSWebServiceSoap client = WebServiceHelper.getWebServiceClient(webServiceUrl);
		Map<String, Category> webServiceRecordCategories = categoryService
				.selectCodeMapByCategoryType(webServiceRecordTypeCode);

		Category ksdeWebServiceFromTime = webServiceRecordCategories.get(kansasScheduledWebServiceStartTime);
		String ksdeFromTime = ksdeWebServiceFromTime.getCategoryName();
		LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ") From Time - " + ksdeFromTime);

		Category ksdeWebServiceToTime = webServiceRecordCategories.get(kansasScheduledWebServiceEndTime);
		String ksdeToTime = ksdeWebServiceToTime.getCategoryName();
		LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ") To Time - " + ksdeToTime);
		try{
			Date ksdeFromTimeDate = DateUtil.parseAndFail(ksdeFromTime, kansasDateFormat);
			Date ksdeToTimeDate = DateUtil.parseAndFail(ksdeToTime, kansasDateFormat);
		
		if (ksdeToTimeDate.before(new Date())) {
			User user = userService.getByUserNameIncludeSuperUser(wsAdminUserName, true);
			String strSchoolYear = Long.toString(user.getContractingOrganization().getCurrentSchoolYear());
			UserDetailImpl userDetailImpl = new UserDetailImpl(user);
			// Create a token and set the security context
			PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetailImpl,
					userDetailImpl.getPassword(), userDetailImpl.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(token);
			Restriction restriction = getRosterRestriction(userDetailImpl);
			if (restriction == null) {
				LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ")  :: restriction is null.");
				//throw new AartParseException("No Permission to upload enrollment ");
			}
			AuthenticationSoapHeader authenticationSoapHeader = new AuthenticationSoapHeader();
			authenticationSoapHeader.setUsername(kidsSettings.getUsername());
			authenticationSoapHeader.setPassword(kidsSettings.getEncryptedPassword());
			LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ")  :: User Authentication completed");

			Holder<String> strFromDateHolder = new Holder<String>(ksdeFromTime);
			Holder<Boolean> bolRequestComplete = new Holder<Boolean>(false);
			Holder<String> getKIDSByDateResult = new Holder<String>("");
			LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ")  ::Calling TASC webservice with Fromdate - "
					+ strFromDateHolder.value + ", toDate - " + ksdeToTime + ", strSchoolYear -" + strSchoolYear);
			client.getKidsByDate(strFromDateHolder, ksdeToTime, strSchoolYear, bolRequestComplete,
					authenticationSoapHeader, getKIDSByDateResult);
			LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ")  ::Webservice call completed");
			saveXML(getKIDSByDateResult.value, ksdeFromTimeDate, ksdeToTimeDate, "KIDS");
			// Frequency data
			RosterByDateInputParameter rostersByDateInputParameter = new RosterByDateInputParameter();
			rostersByDateInputParameter.setRosterFromDate(ksdeFromTimeDate);
			rostersByDateInputParameter.setRosterToDate(ksdeToTimeDate);
			Map<String, Category> rosterByDateFrequencyParameterMap = WebServiceHelper.getRosterByDateFrequencyMap(
					uploadSpecification, uploadSpecification.getKansasWebServiceConfigTypeCode(), categoryService, null);
			// Change input for next run
			rostersByDateInputParameter.changeInputForNextRun(rosterByDateFrequencyParameterMap, uploadSpecification,
					strFromDateHolder.value, bolRequestComplete.value);
			ksdeWebServiceFromTime.setCategoryName(rostersByDateInputParameter.getStrRosterFromDate());
			categoryService.updateByPrimaryKey(ksdeWebServiceFromTime);
			ksdeWebServiceToTime.setCategoryName(rostersByDateInputParameter.getStrRosterToDate());
			categoryService.updateByPrimaryKey(ksdeWebServiceToTime);
			
			mv.addObject("uploadSuccessful", true);
			
		} else {
			LOGGER.debug("Skipped KIDS Web Service Call as End time " + ksdeToTime + "is not passed the current time "
					+ new Date().toString());
		}
		LOGGER.info("Completed executing BatchGetDataFromKSDE (" + runTimeStamp + ")");
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}

	public Restriction getRosterRestriction(UserDetailImpl userDetails) {
		Restriction restriction = resourceRestrictionService.getResourceRestriction(
				userDetails.getUser().getOrganization().getId(),
				permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),
						RestrictedResourceConfiguration.getUploadRosterPermissionCode()),
				permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				restrictedResourceConfiguration.getRosterResourceCategory().getId());
		return restriction;
	}

	private KSDEXMLAudit saveXML(String result, Date fromDate, Date toDate, String type) {
		KSDEXMLAudit xmlRecord = new KSDEXMLAudit();
		xmlRecord.setXml(result);
		xmlRecord.setType(type);
		xmlRecord.setCreateDate(new Date());
		xmlRecord.setProcessedCode("NOTPROCESSED");
		xmlRecord.setFromDate(fromDate);
		xmlRecord.setToDate(toDate);
		xmlService.insert(xmlRecord);
		return xmlRecord;
	}
}
