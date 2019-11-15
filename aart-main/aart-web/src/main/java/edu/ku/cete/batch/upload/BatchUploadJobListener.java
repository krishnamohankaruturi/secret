package edu.ku.cete.batch.upload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import edu.ku.cete.batch.reportprocess.BatchReportProcessStarter;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.PermissionUploadFile;
import edu.ku.cete.domain.ScoringUploadFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.PermissionUploadFileService;
import edu.ku.cete.service.ScoringUploadFileService;
import edu.ku.cete.service.UploadFileService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.OrganizationGrfCalculationService;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
import edu.ku.cete.service.report.UploadIncidentFileWriterProcessService;
import edu.ku.cete.service.report.UploadScCodeFileWriterProcessService;
import edu.ku.cete.util.SourceTypeEnum;

public class BatchUploadJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchUploadJobListener.class);
	
	@Autowired
	private BatchUploadService batchUploadService;
 
    private Instant startTime;
    
    @Autowired
	private UploadFileService uploadFileService;
   
    /**
     * Upload data files like user, organization, roster etc 
     */
    @Autowired
    private OrganizationService organizationService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private GroupsService groupsService;
    
    @Autowired
    private OrganizationTypeService orgTypeService;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private ScoringUploadFileService scoringUploadFileService;
    
    @Autowired
    private UploadGrfFileWriterProcessService uploadGrfFileWriterProcessService;
    
    @Autowired    
    private UploadScCodeFileWriterProcessService uploadScCodeFileWriterProcessService;
    
    @Autowired 
    private UploadIncidentFileWriterProcessService uploadIncidentFileWriterProcessService;
    
    @Autowired
    private OrganizationGrfCalculationService orgGrfCalculationService;
    
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	BatchReportProcessStarter batchReportProcessStarter;
        
	@Value("${userupload.userRecordType}")
	private String userUploadRecordType;
    
	@Value("${orgupload.recordType}")
	private String orgUploadRecordType;
	
	@Value("${enrollmentRecordType}")
	private String enrollmentRecordType;
	
	@Value("${scrsRecordType}")
	private String scrsRecordType;
	
	@Value("${xmlEnrollmentRecordType}")
	private String xmlEnrollmentRecordType;
	
	@Value("${xmlUnEnrollmentRecordType}")
	private String xmlUnEnrollmentRecordType;
	
	@Value("${xmlLeaRecordType}")
	private String xmlLeaRecordType;
	
	@Value("${xmlSchoolRecordType}")
	private String xmlSchoolRecordType;
	
	@Value("${xmlDeleteLeaRecordType}")
	private String xmlDeleteLeaRecordType;
	
	@Value("${xmlDeleteSchoolRecordType}")
	private String xmlDeleteSchoolRecordType;

	@Value("${xmlRosterRecordType}")
	private String xmlRosterRecordType;
	
	@Value("${scoringRecordType}")
	private String scoringRecordType;
	
	@Value("${uploadIncidentFileType}")
	private String uploadIncidentFileType;
	
	@Value("${uploadKansasScCodeFileType}")
	private String uploadKansasScCodeFileType;
	
	@Value("${uploadCommonScCodeFileType}")
	private String uploadCommonScCodeFileType;
	
	@Value("${uploadCommonGrfFileType}")
	private String uploadCommonGrfFileType;
	
	@Value("${uploadIowaGrfFileType}")
	private String uploadIowaGrfFileType;
	
	@Value("${uploadNewYorkGrfFileType}")
	private String uploadNewYorkGrfFileType;
	
	@Value("${GRF.original.upload}")
	private String OriginalGrfUpload;
	
	@Value("${GRF.update.upload}")
	private String UpdatedGrfUpload;
	
	@Value("${uploadDelawareGrfFileType}")
	private String uploadDelawareGrfFileType;
	
	@Value("${uploadDCGrfFileType}")
	private String uploadDCGrfFileType;
	
	@Value("${uploadArkansasGrfFileType}")
	private String uploadArkansasGrfFileType;

	@Value("${permissionRecordType}")
	private String permissionRecordType;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	PermissionUploadFileService permissionUploadFileService;
		
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
		startTime = new Instant();
		//UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User user = userDetails.getUser();
        JobParameters params = jobExecution.getJobParameters();
		BatchUpload ubRecord = new BatchUpload();
		ubRecord.setStatus("IN-PROGRESS");
		ubRecord.setSuccessCount(0);
		ubRecord.setFailedCount(0);
		ubRecord.setModifiedDate(new Date());
		ubRecord.setId(params.getLong("batchUploadId"));
		batchUploadService.updateByPrimaryKeySelectiveBatchUpload(ubRecord);
		ubRecord = batchUploadService.selectByPrimaryKeyBatchUpload(params.getLong("batchUploadId"));
 		//jobExecution.getExecutionContext().put("user", user);
		jobExecution.getExecutionContext().put("batchUploadRecord", ubRecord);
		jobExecution.getExecutionContext().put("batchUploadId", ubRecord.getId());
		jobExecution.getExecutionContext().put("grfUploadType", ubRecord.getGrfProcessType());
		Map<String, FieldSpecification> feildSpecMap = new HashMap<String, FieldSpecification>();
		Map<String, String> mappedFieldNames = new HashMap<String, String>();
		String uploadTypeCode = params.getString("uploadTypeCode");
		String uploadRecordType = params.getString("recordType");
		Long stateId = params.getLong("stateId");
		Long reportYear= params.getLong("reportYear");
		Long uploadedUserId= params.getLong("uploadedUserId");
		jobExecution.getExecutionContext().put("uploadTypeCode", uploadTypeCode);
		jobExecution.getExecutionContext().put("gradeChangeCount", 0);//Added  for F671 GRF grade change  scenario for triggering the email
		try {
			
			feildSpecMap = uploadFileService.getFieldSpecificationRecordMap(uploadTypeCode, uploadRecordType);
			//Added for f430-US19339 for scoring upload
            if(scoringRecordType.equals(uploadTypeCode)){
            	if(ubRecord.getDocumentId() != null){
            	//Call DB with document id, from there we will get all the dynamic column information and test id 
            		//then set those into appropriate place
            		
            		ScoringUploadFile scoringFile = scoringUploadFileService.getUplodFileDetails(ubRecord.getDocumentId());
            
            		if(scoringFile != null){
            			jobExecution.getExecutionContext().put("testId", scoringFile.getTestId());
    	            	jobExecution.getExecutionContext().put("documentId", scoringFile.getId());
    	            	jobExecution.getExecutionContext().put("assessmentProgramId", scoringFile.getAssessmentProgramId());
    	            	jobExecution.getExecutionContext().put("userId", scoringFile.getCreatedUser());
    	            	
    	            	List<FieldSpecification> specifications = scoringFile.getFieldSpecifications();
    	            			
    	            	for (FieldSpecification fieldSpecification : specifications) {
    	            		feildSpecMap.put(fieldSpecification.getMappedName().toString().toLowerCase(),fieldSpecification);
						}		
            		}	            	
            	}
            }
            //Added for F885 permissions File Upload
            if(permissionRecordType.equals(uploadTypeCode)){
            	PermissionUploadFile permissionFile = permissionUploadFileService.getUplodFileDetails(ubRecord.getId());
            	if(permissionFile != null){
	            	List<FieldSpecification> specifications = permissionFile.getFieldSpecifications();
	            	List<String> dynamicRoleNameList=new ArrayList<String>();
	             			
	            	for (FieldSpecification fieldSpecification : specifications) {
	            		dynamicRoleNameList.add(fieldSpecification.getMappedName().toString().toLowerCase());
	            		feildSpecMap.put(fieldSpecification.getMappedName().toString().toLowerCase(),fieldSpecification);
					}
	            	jobExecution.getExecutionContext().put("dynamicRoleNameList", dynamicRoleNameList);
        		}
            }
            
            
			for(String mappedName : feildSpecMap.keySet()){
				mappedFieldNames.put(feildSpecMap.get(mappedName).getFieldName(), mappedName);
			}
			/**
			 * Prasanth :  US16352 : To upload data file (User,Organization,Enrollment etc)    
			 */
			if("FILE_DATA".equals( params.getString("UPLOAD_TYPE")) || "XML_FILE_DATA".equals( params.getString("UPLOAD_TYPE"))){
				logger.debug("FILE DATA Upload ..");
				/**
				 * DE14034: QA-Not able to exit the student by uploading TEC file with new DLM exit code
				 * Developer: Navya Kooram @ EP KU Team
				 * This method is called to populate the User Object. 
				 * Instead of calling getByUsername() which was assigning the default assessment program and it's permissions to the uploadUser who initiated the batch process,
				 * 	this method will populate the User object based on the userId, the assessmentprogramId, organizationId and the RoleId (groupId - StateSystemAdmin etc)
				 */
				User user = userService.getUserDetailsByIdOrgAssessmentProgram(params.getLong("uploadedUserId"), ubRecord.getAssessmentProgramId(), params.getLong("uploadedUserOrgId"), params.getLong("uploadedUserGroupId"));
				
				List<Organization> userOrganizations = user.getOrganizations();
				
				Long userOrgId = params.getLong("uploadedUserOrgId") ;
				user.setCurrentOrganizationId(userOrgId);
				for(Organization organization : userOrganizations){
					if(userOrgId != null && organization.getId() != null 
							&& organization.getId().longValue() == userOrgId.longValue()){
						user.setCurrentOrganizationType(organization.getTypeCode());
						user.setDefaultOrganizationId(organization.getId());
						break;
					}
				}
				Long userGroupId = params.getLong("uploadedUserGroupId");
				user.setCurrentGroupsId(userGroupId);
				user.setDefaultUserGroupsId(userGroupId);
				
				UserDetailImpl userDetail = new UserDetailImpl(user); 
				Long orgId = userOrgId;
				user.setCurrentOrganizationId(userOrgId);
				user.setCurrentGroupsId(userGroupId);
				if( uploadTypeCode.equals(enrollmentRecordType) || uploadTypeCode.equals(scrsRecordType ) ||
					uploadTypeCode.equals(xmlEnrollmentRecordType) || uploadTypeCode.equals(xmlRosterRecordType)
					|| uploadTypeCode.equals(xmlUnEnrollmentRecordType)){
					orgId = params.getLong("selectedOrgId"); 
				}
				
				Organization contractingOrg = organizationService.getContractingOrganization(userOrgId);
				if(contractingOrg != null) {
					userDetail.getUser().setContractingOrgDisplayIdentifier(contractingOrg.getDisplayIdentifier());
					userDetail.getUser().setContractingOrgId(contractingOrg.getId());	
					userDetail.getUser().setContractingOrganization(contractingOrg);
				}
				
				Organization currentContext = organizationService.get(orgId);
		        ContractingOrganizationTree contractingOrganizationTree = organizationService.getTree(currentContext);
		        
		        ExecutionContext ec = jobExecution.getExecutionContext();
		        if(uploadTypeCode.equals(userUploadRecordType)){
		        List<Groups> groups =  groupsService.getAllGroups();
		        /**
		         * F468 roles depreciated 
		         */
		        Iterator<Groups> iterateroles = groups.iterator();
		        while (iterateroles.hasNext()) {
		            Groups activeGroup = iterateroles.next();
		            if (activeGroup.isIsdepreciated()) {
		            	iterateroles.remove();
		            }
		        }
		        	
	        	/**
		         * US16533 : To check the user allowed roles
		         */
	        	List<Groups> allowedGroups =  groupsService.getExceptionalGroupsBelowLevel(userGroupId);
	        	
	        	/**
		         * F468 roles depreciated 
		         */
	        	Iterator<Groups> iterateAllowedRoles = allowedGroups.iterator();
		        while (iterateAllowedRoles.hasNext()) {
		            Groups activeAllowedGroup = iterateAllowedRoles.next();
		            if (activeAllowedGroup.isIsdepreciated()) {
		            	iterateAllowedRoles.remove(); 
		            }
		        }
	        	
	        	ec.put("groupsList", groups);
	        	ec.put("allowedRoles", allowedGroups);
		        }
		        else if(uploadTypeCode.equals(orgUploadRecordType) || uploadTypeCode.equals(xmlLeaRecordType) ||
		        		uploadTypeCode.equals(xmlSchoolRecordType) || uploadTypeCode.equals(xmlDeleteLeaRecordType) ||
		        		uploadTypeCode.equals(xmlDeleteSchoolRecordType)){
		        	logger.debug(" Uploading organization .. ");
		        	
		        	List<OrganizationType> orderedOrgTypesByLevel = orgTypeService.getOrgHierarchyByOrg(stateId);
		        	
		        	Collections.sort(orderedOrgTypesByLevel, OrganizationType.OrgTypeLevelComparator);
		        	Map<String, OrganizationType> orgTypesMap = new HashMap<String, OrganizationType>(); 
	              
		        	for (OrganizationType orgType : orderedOrgTypesByLevel) {
		        		orgTypesMap.put(orgType.getTypeCode().toUpperCase(), orgType); 
		        	}
		        	Collection<Organization> currentUserChildOrgs = contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationTree();
		        	//Maintain the current list of children for the context user 
		            Map<String, Organization> validVerifiedParentOrgIdentifier = new HashMap< String, Organization>();
		            validVerifiedParentOrgIdentifier.put(currentContext.getDisplayIdentifier(), currentContext);
		            for (Organization currentUserChildOrg : currentUserChildOrgs) {
		                validVerifiedParentOrgIdentifier.put(currentUserChildOrg.getDisplayIdentifier(), currentUserChildOrg);
		            }
		            Set<String> organizationDisplayIdentifiers = contractingOrganizationTree.getOrganizationDisplayIdentifiers();
		            //building uniqueness is set by the state
		           // Long stateId = uploadFile.getStateId();
		            Organization state = organizationService.get(stateId);
		            OrganizationType buildingUniquenessType = null;
		            if (state.getBuildingUniqueness() != null){
		            	buildingUniquenessType = orgTypeService.get(state.getBuildingUniqueness());
		            }
		        	ec.put("orderedOrgTypesByLevel", orderedOrgTypesByLevel);
		        	ec.put("validVerifiedParentOrgIdentifier", validVerifiedParentOrgIdentifier);
		        	ec.put("orgTypesMap", orgTypesMap);
		        	ec.put("buildingUniquenessType", buildingUniquenessType);
		        	ec.put("organizationDisplayIdentifiers", organizationDisplayIdentifiers);
		        }
		        else if( uploadTypeCode.equals(enrollmentRecordType) || uploadTypeCode.equals(scrsRecordType ) ||
		        	uploadTypeCode.equals(xmlEnrollmentRecordType) || uploadTypeCode.equals(xmlRosterRecordType)
		        	|| uploadTypeCode.equals(xmlUnEnrollmentRecordType)){
		        	
		        	Restriction restriction =null;
		        	//if(uploadTypeCode.equals(scrsRecordType ))
		        	//	restriction = rosterService.getRosterRestriction(userDetail);
		        	//else	
		        	//	restriction = enrollmentService.getEnrollmentRestriction(userDetail);
		        	
		        	AssessmentProgram assessmentProgram  = assessmentProgramService.findByAbbreviatedName("DLM");

		        	ec.put("restriction", restriction);
		        	ec.put("assessmentProgram", assessmentProgram);
		        }
		        ec.put("currentContext", currentContext);
		        ec.put("currentUser", userDetail);
		        ec.put("contractingOrganizationTree", contractingOrganizationTree);
		        
		        PreAuthenticatedAuthenticationToken token = new PreAuthenticatedAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());
		        SecurityContextHolder.getContext().setAuthentication(token);
		        
			}
			/**
			  * uday
			  * F458
			  * updating the previous records to inactive for uploaded result file	
			  */
			 if(StringUtils.equalsIgnoreCase(uploadTypeCode,uploadCommonGrfFileType)
					 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadIowaGrfFileType)
					 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadNewYorkGrfFileType) 
		          	 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadDelawareGrfFileType)
		          	 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadDCGrfFileType) 
		          	 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadArkansasGrfFileType)){
				 
				 String grfUploadType = ubRecord.getGrfProcessType();				 
				 if(OriginalGrfUpload.equalsIgnoreCase(grfUploadType)){
					 //Need to hard delete from grf table instead of soft delete for the uploaded state and year
					 uploadGrfFileWriterProcessService.clearRecordsOnOriginalGRFUpload(stateId, reportYear, ubRecord.getAssessmentProgramId());
					 
					 //Need to delete calculated GRF data for State and District summary Reports
					 orgGrfCalculationService.deleteOrganizationGrfCalculation(stateId,reportYear,ubRecord.getAssessmentProgramId());
						
					 
					 //Need to delete All DLM organization reports for the uploaded state and year
					 batchReportProcessService.deleteAllOrganizationReportsOnGRFUpload(stateId, reportYear, ubRecord.getAssessmentProgramId());
					 
					//Need to delete All DLM Student reports for the uploaded state and year
					 batchReportProcessService.deleteAllStudentReportsOnGRFUpload(stateId, reportYear, ubRecord.getAssessmentProgramId());
				 }	 						 
			}
			 
		  if(StringUtils.equalsIgnoreCase(uploadTypeCode,uploadIncidentFileType)){
			  uploadIncidentFileWriterProcessService.updateIncidentFileRecordsByStateAndReportYear(stateId, reportYear,uploadedUserId,null,false);
		  }
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		jobExecution.getExecutionContext().put("feildSpecRecords", feildSpecMap);
		jobExecution.getExecutionContext().put("jobMessages",new CopyOnWriteArrayList<BatchUploadReason>());
		jobExecution.getExecutionContext().put("mappedFieldNames", mappedFieldNames);
		//added during US16966 - To add alert message to upload
		jobExecution.getExecutionContext().put("alertCount", 0);
		jobExecution.getExecutionContext().put("alertMessages",new CopyOnWriteArrayList<BatchUploadReason>());
		logger.debug("<-- beforeJob");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());
		boolean isUploadResultSuccess = true;
		int successCount = 0, failedCount = 0;
		BatchUpload buRecord = (BatchUpload) jobExecution.getExecutionContext().get("batchUploadRecord");
		JobParameters params = jobExecution.getJobParameters();
		String uploadTypeCode = params.getString("uploadTypeCode");
		List<BatchUploadReason> jobMessages = (List<BatchUploadReason>) jobExecution.getExecutionContext().get("jobMessages");
		List<BatchUploadReason> alertMessages = (List<BatchUploadReason>) jobExecution.getExecutionContext().get("alertMessages");
		Integer gradeChangeCount = (Integer) jobExecution.getExecutionContext().get("gradeChangeCount");
		
		if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
			isUploadResultSuccess = false;
			if((StringUtils.equalsIgnoreCase(uploadTypeCode,uploadCommonGrfFileType)
					||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadIowaGrfFileType)
					||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadNewYorkGrfFileType)
		          	||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadDelawareGrfFileType)
		          	||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadDCGrfFileType) 
		          	||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadArkansasGrfFileType))){
				 uploadGrfFileWriterProcessService.deleteFailedBatchGrfFileRecords(params.getLong("batchUploadId"));
			}else if(StringUtils.equalsIgnoreCase(uploadTypeCode,uploadKansasScCodeFileType)||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadCommonScCodeFileType)){
				 uploadScCodeFileWriterProcessService.deleteBatchFailedScCodeFileRecords(params.getLong("batchUploadId"));
			}else if(StringUtils.equalsIgnoreCase(uploadTypeCode,uploadIncidentFileType)){
				 uploadIncidentFileWriterProcessService.deleteFailedBatchIncidentFileRecords(params.getLong("batchUploadId"));
			}
			BatchUploadReason buReason = new BatchUploadReason();
			buReason.setBatchUploadId(buRecord.getId());
			for(Throwable t: jobExecution.getAllFailureExceptions()) {
 					buReason.setReason(t.getMessage());
					jobMessages.add(buReason);
			}
		}else{
			 /**
			  * US16352
			  * For organization upload: if uploaded successfully then need to clear the organization cache for the parent organization	
			  */
			 if(uploadTypeCode.equals(orgUploadRecordType) || uploadTypeCode.equals(xmlLeaRecordType) ||
		        		uploadTypeCode.equals(xmlSchoolRecordType) || uploadTypeCode.equals(xmlDeleteLeaRecordType) ||
		        		uploadTypeCode.equals(xmlDeleteSchoolRecordType)){
				 Organization currentContext = (Organization)jobExecution.getExecutionContext().get("currentContext");
				 organizationService.clearTreeCache(currentContext);
			 }
			 /**
			  * uday
			  * F458
			  * For General Research File  upload: if uploaded successfully then need trigger new batch for calculation and report generation	
			  */
			 if((StringUtils.equalsIgnoreCase(uploadTypeCode,uploadCommonGrfFileType)
					 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadIowaGrfFileType)
					 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadNewYorkGrfFileType)
		          	 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadDCGrfFileType)
		          	 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadDelawareGrfFileType) 
		          	 ||StringUtils.equalsIgnoreCase(uploadTypeCode,uploadArkansasGrfFileType))){
				 String grfUploadType = (String)jobExecution.getExecutionContext().get("grfUploadType");
				 if(CollectionUtils.isEmpty(jobMessages)){
					 uploadGrfFileWriterProcessService.updateGrfFileRecordsByStateAndReportYear(params.getLong("stateId"), params.getLong("reportYear"),params.getLong("uploadedUserId"), params.getLong("assessmentProgramId"), params.getLong("batchUploadId"),true);
					 if(gradeChangeCount > 0){
						//Send email to ATS management
						 emailService.sendEmailForGRFGradeChange(((Organization)jobExecution.getExecutionContext().get("currentContext")).getOrganizationName());
					 }		 
					 
					//Added for F671
					 
					 //Insert entry in GRF audit table
					 GrfStateApproveAudit audit = new GrfStateApproveAudit();
					 audit.setActiveFlag(true);
					 audit.setStateId(params.getLong("stateId"));
					 audit.setSchoolYear(params.getLong("reportYear"));
					 audit.setAuditColumnProperties();
					 audit.setOperation(grfUploadType);
					 audit.setUpdatedUserId(params.getLong("uploadedUserId"));
					 audit.setSource(SourceTypeEnum.UPLOAD.getCode());
						 
					 uploadGrfFileWriterProcessService.setGRFAuditInfo(audit);
					 
					 
				}else{
					isUploadResultSuccess = false;
					
					//If Edit GRF upload then reset back the recent record as true
					if(UpdatedGrfUpload.trim().equalsIgnoreCase(grfUploadType.trim()))
					  uploadGrfFileWriterProcessService.setRecentFlag(params.getLong("batchUploadId"), params.getLong("reportYear"));
					
					//Need to delete all the inserted entry because some record got failed
					uploadGrfFileWriterProcessService.deleteFailedBatchGrfFileRecords(params.getLong("batchUploadId"));
				}
			 }
			 else if(StringUtils.equalsIgnoreCase(uploadTypeCode,uploadIncidentFileType)){
				 if(CollectionUtils.isEmpty(jobMessages)){
					 uploadIncidentFileWriterProcessService.updateIncidentFileRecordsByStateAndReportYear(params.getLong("stateId"), params.getLong("reportYear"),params.getLong("uploadedUserId"), params.getLong("batchUploadId"),true);
				 }else{
					 isUploadResultSuccess = false;
					 //Need to delete all the inserted entry because some record got failed
					 uploadIncidentFileWriterProcessService.deleteFailedBatchIncidentFileRecords(params.getLong("batchUploadId"));
				 }
			 }
		}
		//changed during US16966 - to add alert message in upload
		for(BatchUploadReason reason: jobMessages) {
			reason.setBatchUploadId(buRecord.getId());
			reason.setErrorType("reject");
		}
		if(CollectionUtils.isNotEmpty(jobMessages)){
			batchUploadService.insertBatchUploadReasons(jobMessages);
		}
		//added during US16966 - To add alert message to upload
		for(BatchUploadReason reason: alertMessages) {
			reason.setBatchUploadId(buRecord.getId());
			reason.setErrorType("alert");
		}
		if(CollectionUtils.isNotEmpty(alertMessages)){
			batchUploadService.insertBatchUploadReasons(alertMessages);
		}
		
		/**
		 * DE10197: Even though upload status is completed still writing to reason : so not able to fetch reasons sometimes  
		 */
		if(buRecord != null) {
 			Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
			for(StepExecution sExecution: sExecutions) {
					successCount = sExecution.getWriteCount();
					failedCount = sExecution.getProcessSkipCount();
					break;
			}
			
			buRecord.setSuccessCount(isUploadResultSuccess ? successCount : 0);
			buRecord.setFailedCount(failedCount);
			//added during US16966 - To add alert message to upload
			buRecord.setAlertCount((Integer)jobExecution.getExecutionContext().get("alertCount"));
			
			buRecord.setStatus(jobExecution.getStatus().name());
			buRecord.setModifiedDate(new Date());
			batchUploadService.updateByPrimaryKeySelectiveBatchUpload(buRecord);
		}
		
		jobExecution.getExecutionContext().remove("jobMessages");
		logger.debug("***** Batch Upload successCount: "+successCount);
		logger.debug("***** Batch Upload failed/skippedCount: "+failedCount);
		logger.debug("Batch Upload Finish job: "+jobExecution.getExecutionContext().get("batchUploadId") +", duration:" + duration);
		logger.debug("Batch Upload Finish job: "+jobExecution.getExecutionContext().get("batchUploadId") +", duration after reasons write: " 
					+ getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
	
}
