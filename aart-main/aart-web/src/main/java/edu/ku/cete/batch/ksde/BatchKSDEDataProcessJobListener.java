package edu.ku.cete.batch.ksde;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.ksde.kids.result.KSDEData;
import edu.ku.cete.ksde.kids.result.KSDEDataConverter;
import edu.ku.cete.ksde.kids.result.KSDERecord;
import edu.ku.cete.ksde.kids.result.KSDERecordConverter;
import edu.ku.cete.ksde.kids.result.KidConverter;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.ksde.kids.result.KidsDashboardRecord;
import edu.ku.cete.ksde.kids.result.KidsData;
import edu.ku.cete.ksde.kids.result.KidsDataConverter;
import edu.ku.cete.report.domain.KSDEXMLAudit;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.DashboardService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.KSDERecordStagingService;
import edu.ku.cete.service.KSDEXMLAuditService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.UploadFileService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;

public class BatchKSDEDataProcessJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchKSDEDataProcessJobListener.class);
	@Autowired
	private KSDEXMLAuditService xmlService;

	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private ResourceRestrictionService resourceRestrictionService;

	private XStream xStream;

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PermissionUtil permissionUtil;

	@Autowired
	private RestrictedResourceConfiguration restrictedResourceConfiguration;

	private Instant startTime;

	private Map<String, Category> kansasAssessmentInputNames = new HashMap<String, Category>();

	@Value("${wsAdminUserName}")
	private String wsAdminUserName;

	@Value("${kansasAssessmentTags}")
	private String kansasAssessmentTags;
	
	@Autowired
	private KSDERecordStagingService ksdeRecordStagingService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private DashboardService dashboardService;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		try {
			beforeJobHelper(jobExecution);
		} catch (Exception e) {
			try {
				emailService.sendKIDSErrorEmail(null, "beforeJob", e);
			} catch (Exception e1) {
				logger.error("Error trying to send KIDS error email:", e1);
			}
		}
	}
	
	private void beforeJobHelper(JobExecution jobExecution) {
		UserDetailImpl user = null;
		ContractingOrganizationTree contractingOrganizationTree = null;
		Restriction restriction = null;
		logger.info("--> BatchKSDEDataProcessJobListener :: beforeJob");
		startTime = new Instant();

		KSDEXMLAudit ksdeDateNotProcessedKids = xmlService.selectOneNotProcessed("KIDS");
		if (ksdeDateNotProcessedKids != null) {
			user = new UserDetailImpl(userService.getByUserNameIncludeSuperUser(wsAdminUserName, true));
			PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(user, user.getPassword(), user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(token);
			Organization currentContext = user.getUser().getOrganization();
			contractingOrganizationTree = organizationService.getTree(currentContext);
			restriction = getRosterRestriction(user);

			String xmlData = ksdeDateNotProcessedKids.getXml();
			KidConverter kidConverter = new KidConverter();
			KidsDataConverter kidsDataConverter = new KidsDataConverter();

			Map<String, FieldSpecification> fieldSpecificationMap = null;

			try {
				fieldSpecificationMap = uploadFileService.getFieldSpecificationRecordMap("KID_RECORD_TYPE",	"WEB_SERVICE_RECORD_TYPE");
				kidConverter.setFieldSpecificationMap(fieldSpecificationMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			kansasAssessmentInputNames = getKansasAssessments(categoryService, kansasAssessmentTags);
			kidConverter.setKansasAssessmentInputNames(kansasAssessmentInputNames);

			xStream = new XStream(new StaxDriver());
			xStream.registerConverter(kidConverter);
			xStream.registerConverter(kidsDataConverter);

			xStream.alias("KIDS_Data", KidsData.class);
			xStream.alias("KIDS_Record", KidRecord.class);
			KidsData kidsData = (KidsData) xStream.fromXML(xmlData);

			// Start -- get raw data
			KSDERecordConverter ksdeConverter = new KSDERecordConverter();
			KSDEDataConverter ksdeDataConverter = new KSDEDataConverter();
			xStream = new XStream(new StaxDriver());
			ksdeConverter.setFieldSpecificationMap(fieldSpecificationMap);
			
			xStream.registerConverter(ksdeConverter);
			xStream.registerConverter(ksdeDataConverter);

			xStream.alias("KIDS_Data", KSDEData.class);
			xStream.alias("KIDS_Record", KSDERecord.class);
			KSDEData ksdeData = (KSDEData) xStream.fromXML(xmlData);
			// End

			List<KidRecord> kidRecords = kidsData.getKids();
			jobExecution.getExecutionContext().put("rejectedReasons", new CopyOnWriteArrayList<String>());
			jobExecution.getExecutionContext().put("kidRecords", kidRecords);
			jobExecution.getExecutionContext().put("ksdeRawRecords", ksdeData.getKids());
			jobExecution.getExecutionContext().put("xmlAuditRecordId", ksdeDateNotProcessedKids.getId());
			jobExecution.getExecutionContext().put("restrictionId", restriction.getId());
			jobExecution.getExecutionContext().put("contractingOrganizationTree", contractingOrganizationTree);
			jobExecution.getExecutionContext().put("userDetails", user);
			jobExecution.getExecutionContext().put("fieldSpecificationMap", fieldSpecificationMap);

		} else {
			jobExecution.getExecutionContext().put("kidRecords", new ArrayList<KidRecord>());
		}
		logger.info("<-- BatchKSDEDataProcessJobListener :: beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		try {
			afterJobHelper(jobExecution);
		} catch (Exception e) {
			logger.info("afterJob error (should not affect application performance, but logging it anyway):", e);
			/*try {
				emailService.sendKIDSErrorEmail(null, "afterJob", e);
			} catch (Exception e1) {
				logger.error("Error trying to send KIDS error email:", e1);
			}*/
		}
	}
	
	@SuppressWarnings("unchecked")
	private void afterJobHelper(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());
		List<String> rejectedRecords = (List<String>) jobExecution.getExecutionContext().get("rejectedReasons");
		Long xmlAuditRecordId = (Long) jobExecution.getExecutionContext().get("xmlAuditRecordId");
		
		KSDEXMLAudit kidXmlAudit = new KSDEXMLAudit();
		if (jobExecution.getStatus().equals(BatchStatus.FAILED)) {
			for(Throwable t: jobExecution.getAllFailureExceptions()) {
				rejectedRecords.add(t.getMessage());
				logger.error(t.getMessage());
				if(t.getCause() != null && t.getCause().getStackTrace() != null && t.getCause().getStackTrace().length > 0){
					for(StackTraceElement ste : t.getCause().getStackTrace()){
						rejectedRecords.add(ste.toString());
						logger.error(ste.toString());
					}										
				}				
			}
			kidXmlAudit.setProcessedCode("FAILED");
		} else {
			kidXmlAudit.setProcessedCode("COMPLETED");
		}
		
		if (xmlAuditRecordId != null) {
			if(kidXmlAudit.getProcessedCode() != null && !"FAILED".equalsIgnoreCase(kidXmlAudit.getProcessedCode())){
				HashMap<Long,KidRecord> kidRecordsMap = new HashMap<Long,KidRecord>();
				List<KSDERecord> ksdeRecords = (List<KSDERecord>) jobExecution.getExecutionContext().get("ksdeRawRecords");
				List<KidRecord> kidRecords = (List<KidRecord>) jobExecution.getExecutionContext().get("kidRecords");
				List<KidRecord> kidRecordListForDashboard = new ArrayList<KidRecord>();
				
				for(KidRecord kidRecord : kidRecords){
					kidRecordsMap.put(kidRecord.getSeqNo(), kidRecord);
				}
				for (KSDERecord ksdeRec : ksdeRecords) {
					ksdeRec.setXmlAuditId(xmlAuditRecordId);
					if(kidRecordsMap != null && kidRecordsMap.size() > 0 && kidRecordsMap.containsKey(ksdeRec.getSeqNo())){
						KidRecord kidRec = (KidRecord)kidRecordsMap.get(ksdeRec.getSeqNo());
						ksdeRec.setReason(kidRec.getReasons());
						ksdeRec.setEmailSent(kidRec.getEmailSent());
						ksdeRec.setEmailSentTo(kidRec.getEmailSentTo());
						ksdeRec.setTriggerEmail(kidRec.getTriggerEmail());
						ksdeRec.setEmailTemplateIds(kidRec.getEmailTemplateIds());
						if(kidRec.getStatus() != null){
							ksdeRec.setStatus(kidRec.getStatus());
						}else{
							ksdeRec.setStatus(kidXmlAudit.getProcessedCode());
						}
						if (ksdeRec.getRecordType().equalsIgnoreCase("TASC")) {
							kidRec.setStateSubjectAreaCode(ksdeRec.getTascStateSubjectAreaCode());
						}
						kidRec.setStatus(ksdeRec.getStatus());
						kidRecordListForDashboard.add(kidRec);
					}
					if (ksdeRec.getRecordType().equalsIgnoreCase("TEST") || ksdeRec.getRecordType().equalsIgnoreCase("EXIT")) {
						ksdeRecordStagingService.insertKidsRecord(ksdeRec);
					} else if (ksdeRec.getRecordType().equalsIgnoreCase("TASC")) {
						ksdeRecordStagingService.insertTascRecord(ksdeRec);
					}
				}
				
				if(CollectionUtils.isNotEmpty(kidRecordListForDashboard) && kidRecordListForDashboard.size() > 0){
					for(KidRecord kidRecord : kidRecordListForDashboard){
						//process for dashboard here
						List<KidsDashboardRecord> dashboardRecordsToProcess = kidRecord.getKidsDashboardRecords();
						List<String> receivedSubjectCodes = kidRecord.getReceivedSubjectCodes();
						if(CollectionUtils.isNotEmpty(dashboardRecordsToProcess) && dashboardRecordsToProcess.size() > 0){
							
							for(KidsDashboardRecord dashboardRecord: dashboardRecordsToProcess){
								List<KidsDashboardRecord> existingRecords = dashboardService.getDashboardRecords(dashboardRecord.getRecordType(), 
																								dashboardRecord.getStateStudentIdentifier(), 
																								dashboardRecord.getAypSchoolId(), 
																								dashboardRecord.getAttendanceSchoolId(), 
																								dashboardRecord.getSubjectArea());
								
								if(CollectionUtils.isNotEmpty(existingRecords) && existingRecords.size() > 0){
									//update record
									for(KidsDashboardRecord updateExistingRecord : existingRecords){
										dashboardRecord.setId(updateExistingRecord.getId());
										dashboardRecord.setSuccessfullyProcessedDate("COMPLETED".equalsIgnoreCase(kidRecord.getStatus()) ? new Date() : null);
										dashboardService.updateKidDashboardRecord(dashboardRecord);
									}
									
								}else{
									//insert into dashboard table
									dashboardService.insertKidsDashboardRecord(dashboardRecord);
								}
							}
						}
						if(SourceTypeEnum.TASCWEBSERVICE.getCode().equalsIgnoreCase(kidRecord.getRecordType()) ||
								SourceTypeEnum.TESTWEBSERVICE.getCode().equalsIgnoreCase(kidRecord.getRecordType())){
							if(CollectionUtils.isNotEmpty(receivedSubjectCodes)){
								for(String subj : receivedSubjectCodes){
									if(!kidRecord.getErrorSubjectCodes().contains(subj)){
										//update successfullyprocesseddate if record present for this subject and school
										if("COMPLETED".equalsIgnoreCase(kidRecord.getStatus())){
											dashboardService.updateExistingRecordsByTypeSchIdStdId(kidRecord.getRecordType(), 
													kidRecord.getStateStudentIdentifier(), kidRecord.getEnrollment().getAypSchoolId(), 
													kidRecord.getEnrollment().getAttendanceSchoolId(), subj, Long.valueOf(kidRecord.getRecordCommonId()));
										}										
									}
								}
							}
							
						}else if(SourceTypeEnum.EXITWEBSERVICE.getCode().equalsIgnoreCase(kidRecord.getRecordType())){
							if(CollectionUtils.isEmpty(dashboardRecordsToProcess)){
								//update successfullyprocesseddate on existing records with EXIT type if available
								dashboardService.updateExistingRecordsByTypeSchIdStdId(kidRecord.getRecordType(), 
										kidRecord.getStateStudentIdentifier(), kidRecord.getEnrollment().getAypSchoolId(), 
										kidRecord.getEnrollment().getAttendanceSchoolId(), null, Long.valueOf(kidRecord.getRecordCommonId()));
							}
						}
					}
				}
				
				if(CollectionUtils.isNotEmpty(kidRecordListForDashboard)){
					kidRecordListForDashboard.clear();
				}
			}
			
			int successCount = 0, failedCount = 0;
			
			Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
			for (StepExecution sExecution : sExecutions) {
				successCount = sExecution.getWriteCount();
				failedCount = sExecution.getProcessSkipCount();
				break;
			}
			StringWriter stringWriter = new StringWriter();
			for (String rejectedRecord : rejectedRecords) {
				stringWriter.append(rejectedRecord);
				stringWriter.append(System.getProperty("line.separator"));
			}
			
			if (stringWriter.toString().length() > 0) {
				kidXmlAudit.setErrors(stringWriter.toString());
			}
			kidXmlAudit.setId(xmlAuditRecordId);
			kidXmlAudit.setFailedCount(failedCount);
			kidXmlAudit.setSuccessCount(successCount);
			kidXmlAudit.setProcessedDate(new Date());
			
			xmlService.updateByPrimaryKeySelective(kidXmlAudit);
			
			

		}
		logger.debug("<-- afterJob: " + duration);
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}

	private Restriction getRosterRestriction(UserDetailImpl userDetails) {
		// Find the restriction for what the user is trying to do on this page.
		// TODO Look at Roster Service Impl
		// Find the restriction for what the user is trying to do on this page.
		Restriction restriction = resourceRestrictionService.getResourceRestriction(
				userDetails.getUser().getOrganization().getId(),
				permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),
						RestrictedResourceConfiguration.getUploadRosterPermissionCode()),
				permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				restrictedResourceConfiguration.getRosterResourceCategory().getId());
		return restriction;
	}

	public Map<String, Category> getKansasAssessmentInputNames() {
		return kansasAssessmentInputNames;
	}

	public void setKansasAssessmentInputNames(Map<String, Category> kansasAssessmentInputNames) {
		this.kansasAssessmentInputNames = kansasAssessmentInputNames;
	}

	/**
	 * @param categoryService {@link CategoryService}
	 * @param kansasAssessmentTags {@link String}
	 * @return {@link Map}
	 */
	public static final Map<String, Category> getKansasAssessments(CategoryService categoryService,
			String kansasAssessmentTags) {
		Map<String, Category> kansasAssessmentInputNames = new HashMap<String, Category>();
		List<Category> kansasAssessmentCodeCategories = categoryService.selectByCategoryType(kansasAssessmentTags);
		if (CollectionUtils.isEmpty(kansasAssessmentCodeCategories)) {
			logger.debug("No categories of Kansas Assessments");
		} else {
			for (Category kansasAssessmentCode : kansasAssessmentCodeCategories) {
				kansasAssessmentInputNames.put(kansasAssessmentCode.getCategoryName(), kansasAssessmentCode);
			}
		}
		return kansasAssessmentInputNames;
	}	
	
}