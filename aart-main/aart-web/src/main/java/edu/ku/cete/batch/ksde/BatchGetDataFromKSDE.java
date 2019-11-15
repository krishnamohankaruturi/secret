package edu.ku.cete.batch.ksde;

import java.util.Date;
import java.util.Map;

import javax.xml.ws.Holder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

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
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.RosterWebServiceTask;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.WebServiceHelper;

public class BatchGetDataFromKSDE implements Tasklet {

	private static final Log LOGGER = LogFactory.getLog(RosterWebServiceTask.class);

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

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		String runTimeStamp = new Date().toString();
		// KSDE Process
		LOGGER.info("Started executing BatchGetDataFromKSDE (" + runTimeStamp + ")");
		Category webServiceUrl = categoryService.selectByCategoryCodeAndType(kansasWebServiceUrlCode,
				kansasWebServiceConfigTypeCode);
		KIDSWebServiceSoap client = WebServiceHelper.getWebServiceClient(webServiceUrl);
		Map<String, Category> webServiceRecordCategories = categoryService
				.selectCodeMapByCategoryType(webServiceRecordTypeCode);

		Category ksdeWebServiceFromTime = webServiceRecordCategories.get(kansasScheduledWebServiceStartTime);
		String ksdeFromTime = ksdeWebServiceFromTime.getCategoryName();
		LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ") From Time - " + ksdeFromTime);

		Category ksdeWebServiceToTime = webServiceRecordCategories.get(kansasScheduledWebServiceEndTime);
		String ksdeToTime = ksdeWebServiceToTime.getCategoryName();
		LOGGER.info("BatchGetDataFromKSDE (" + runTimeStamp + ") To Time - " + ksdeToTime);

		Date ksdeOldFromTimeDate = DateUtil.parseAndFail(ksdeFromTime, kansasDateFormat);
		Date ksdeOldToTimeDate = DateUtil.parseAndFail(ksdeToTime, kansasDateFormat);
		Date ksdeFromTimeDate = DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(ksdeFromTime, "US/Central",  "MM/dd/yyyy hh:mm:ss a");
		Date ksdeToTimeDate = DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(ksdeToTime, "US/Central",  "MM/dd/yyyy hh:mm:ss a");
				
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
				throw new AartParseException("No Permission to upload enrollment ");
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
			rostersByDateInputParameter.setRosterFromDate(ksdeOldFromTimeDate);
			rostersByDateInputParameter.setRosterToDate(ksdeOldToTimeDate);
			Map<String, Category> rosterByDateFrequencyParameterMap = WebServiceHelper.getRosterByDateFrequencyMap(
					uploadSpecification, uploadSpecification.getKansasWebServiceConfigTypeCode(), categoryService, null);
			// Change input for next run
			rostersByDateInputParameter.changeInputForNextRun(rosterByDateFrequencyParameterMap, uploadSpecification,
					strFromDateHolder.value, bolRequestComplete.value);
			ksdeWebServiceFromTime.setCategoryName(rostersByDateInputParameter.getStrRosterFromDate());
			categoryService.updateByPrimaryKey(ksdeWebServiceFromTime);
			ksdeWebServiceToTime.setCategoryName(rostersByDateInputParameter.getStrRosterToDate());
			categoryService.updateByPrimaryKey(ksdeWebServiceToTime);
		} else {
			LOGGER.debug("Skipped KIDS Web Service Call as End time " + ksdeToTime + "is not passed the current time "
					+ new Date().toString());
		}
		LOGGER.info("Completed executing BatchGetDataFromKSDE (" + runTimeStamp + ")");
		return RepeatStatus.FINISHED;
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
		if(result.contains("<KIDS_Record>")){
			xmlRecord.setProcessedCode("NOTPROCESSED");
		}else{
			xmlRecord.setProcessedCode("COMPLETED");
			xmlRecord.setProcessedDate(new Date());
			xmlRecord.setSuccessCount(0);
			xmlRecord.setFailedCount(0);
		}
		xmlRecord.setFromDate(fromDate);
		xmlRecord.setToDate(toDate);
		xmlService.insert(xmlRecord);
		return xmlRecord;
	}
}
