package edu.ku.cete.service.impl;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.bytecode.opencsv.CSVReader;
import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.OperationalTestWindowStudent;
import edu.ku.cete.domain.OperationalTestWindowStudentRecord;
import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.enrollment.TestRecord;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.PersonalNeedsProfileRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.upload.FirstContactRecord;
import edu.ku.cete.domain.upload.UploadedOrganization;
import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.domain.validation.FieldSpecificationExample;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.OperationalTestWindowStudentMapper;
import edu.ku.cete.model.UploadFileRecordMapper;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UploadFileService;
import edu.ku.cete.service.UploadService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.student.FirstContactService;
import edu.ku.cete.util.AartColumnMappingStrategy;
import edu.ku.cete.util.AartCsvToBean;
import edu.ku.cete.util.AartCsvVerticalParser;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StringUtil;
import edu.ku.cete.util.UploadSpecification;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UploadFileServiceImpl implements UploadFileService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UploadFileServiceImpl.class);

    @Autowired
    private UploadSpecification uploadSpecification;
    
	@Autowired
	private CategoryService categoryService;
	
    @Autowired
    private OrganizationService organizationService;    
    
    @Autowired
    private PermissionUtil permissionUtil;
    
    @Autowired
    private AssessmentProgramDao assessmentProgramDao; 
    
    @Autowired
	private FirstContactService firstContactService;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RestrictedResourceConfiguration restrictedResourceConfiguration;
    
    @Autowired
    private ResourceRestrictionService resourceRestrictionService;
    
    @Autowired
    private RosterService rosterService;
    
    @Autowired
    private FieldSpecificationDao fieldSpecificationDao;
    
    @Autowired
    private UploadService uploadService; 
    
    @Autowired
	private StudentService studentService;
    
    @Autowired
    private CategoryTypeDao categoryTypeDao;
    
    @Autowired
    private CategoryDao categoryDao;
    
    @Autowired
    private UploadFileRecordMapper uploadFileRecordDao;
    
    @Autowired
    private OperationalTestWindowStudentMapper operationalTestWindowStudentMapper;
    
	private Map<Long, Category> recordTypeIdMap = new HashMap<Long, Category>();
	
	private Category userRecordType;
	
	private Category pdTrainingUploadType;
	
	private Category enrollmentRecordType;
	
	private Category scrsRecordType;
	
	private Category orgRecordType;
	
	private Category testRecordType;
	
	private Category firstContactRecordType;
	
	private Category tecRecordType;
	
	private Category tecXMLRecordType;
	
	private Category personalNeedsProfileRecordRecordType;
	
	private Category includeExcludeRecordType;
	
	@Value("${educatorid.warning.message}")
	private String educatorIdWarningMessage;
	
	@Value("${studentid.warning.message}")
	private String studentIdWarningMessage;
	
	@Value("${user.organization.DLM}")
    private String dlmOrgName;
	
	@Value("${default.file.encoding.charset}")
	private Boolean defaultFileCharset;
	
	@Autowired
    private MessageSource messageSource;
	
    @Autowired
    public final void setMetaData() {
        setUploadSpecification();
        setRecordTypes();
    }
    
    private void setUploadSpecification() {
        StringUtil.setUploadSpecification(uploadSpecification);
        NumericUtil.setUploadSpecification(uploadSpecification);
    }
    
    private void setRecordTypes() {
        //get category type.
        CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        categoryTypeExample.createCriteria().andTypeCodeEqualTo(uploadSpecification.getCsvRecordTypeCode());
        //This should not be empty.Failure to deploy is the expected behavior.
        List<CategoryType> categoryTypes = categoryTypeDao.selectByExample(categoryTypeExample);
        if (CollectionUtils.isEmpty(categoryTypes)) {
            LOGGER.debug("No category Types");
            return;
        }
        CategoryExample categoryExample = new CategoryExample();
        //there is only one category type for record type.
        categoryExample.createCriteria().andCategoryTypeIdEqualTo(categoryTypes.get(0).getId());
        recordTypeIdMap = new HashMap<Long, Category>();
        List<Category> recordTypes = categoryDao.selectByExample(categoryExample);
        if (CollectionUtils.isEmpty(recordTypes)) {
            LOGGER.debug("No categories of record type");
            return;
        }
        for (Category recordType:recordTypes) {
            recordTypeIdMap.put(recordType.getId(), recordType);
        }                               
        this.scrsRecordType = getRecordType(uploadSpecification.getScrsRecordType());
        this.enrollmentRecordType = getRecordType(uploadSpecification.getEnrollmentRecordType());
        this.testRecordType = getRecordType(uploadSpecification.getTestRecordType());
        this.userRecordType = getRecordType(uploadSpecification.getUserRecordType());
        this.pdTrainingUploadType = getRecordType(uploadSpecification.getPdTrainingUploadType());
        this.orgRecordType =  getRecordType(uploadSpecification.getOrgRecordType());
        this.personalNeedsProfileRecordRecordType =  getRecordType(
        		uploadSpecification.getPersonalNeedsProfileRecordType());
        this.firstContactRecordType =  getRecordType(uploadSpecification.getFirstContactRecordType());
        this.tecRecordType = getRecordType(uploadSpecification.getTecRecordType());
        this.tecXMLRecordType = getRecordType(uploadSpecification.getTecXMLRecordType());
        this.includeExcludeRecordType = getRecordType(uploadSpecification.getIncludeExcludeRecordType());
    }
    
    private Category getRecordType(String recordTypeCode) {
        for (Category recordType:recordTypeIdMap.values()) {
            if (recordType.getCategoryCode().equalsIgnoreCase(recordTypeCode)) {
                return recordType;
            }
        }
        return null;
    }
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Map<String,Object> create(UploadFile uploadFile, Errors errors, Long uploadFileRecordId) throws IOException, ServiceException {
    	Map<String,Object> uploadMV = new HashMap<String,Object>();
        Category selectedType = recordTypeIdMap.get(uploadFile.getSelectedRecordTypeId());
        UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();       
        long organizationId = 0;
        if (errors.hasErrors()) {
            uploadMV.put("recordTypes", recordTypeIdMap.values());
            if (uploadFile.getRosterUpload() == 1
            		|| selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getEnrollmentRecordType())
            		 || selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getUserRecordType())){
            	uploadMV.put("stateId", uploadFile.getStateId());
            	uploadMV.put("districtId", uploadFile.getDistrictId());
            	uploadMV.put("schoolId", uploadFile.getSchoolId());
            }
        } else if(uploadFile.getContinueOnWarning() == null && 
        		validateIdentifiers(uploadFile,uploadMV)) {
        	if (uploadFile.getRosterUpload() == 1 
        			|| selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getEnrollmentRecordType())
        			|| selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getUserRecordType())) {
            	uploadMV.put("stateId", uploadFile.getStateId());
            	uploadMV.put("districtId", uploadFile.getDistrictId());
            	uploadMV.put("schoolId", uploadFile.getSchoolId());
            }
        	uploadMV.put("uploadFile", uploadFile);
        	uploadMV.put("recordTypes", recordTypeIdMap.values());
        	uploadMV.put("showWarning", true);
        } else {
        	           
            Organization currentContext = organizationService.get(user.getUser().getCurrentOrganizationId());
            ContractingOrganizationTree
            contractingOrganizationTree = organizationService.getTree(currentContext);

            List<GrantedAuthority> authorities = (List<GrantedAuthority>) user.getAuthorities();
            uploadMV.put("showWarning", null);
            uploadMV.put("uploadFile", uploadFile);

            uploadMV.put("uploadCompleted", true);
            uploadMV.put("recordTypes", recordTypeIdMap.values());
            
            if(selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getIncludeExcludeRecordType())){
            	// US17330 - upload include/exclude students for operational test window.
            	uploadMV.put("OperationalWindowId", uploadFile.getOperationalWindowId());
        		uploadMV = uploadIncludeExcludeStudents(uploadFile, uploadMV, errors, contractingOrganizationTree);
        		
            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getEnrollmentRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_ENRL_UPLOAD")) {
            	Organization org = null;

            	if (uploadFile.getSchoolId() > 0){
            		org = organizationService.get(uploadFile.getSchoolId());
            	} else if (uploadFile.getBuildingId() > 0){
            		org = organizationService.get(uploadFile.getBuildingId());
            	} else if (uploadFile.getDistrictId() > 0){
            		org = organizationService.get(uploadFile.getDistrictId());
            	} else if (uploadFile.getAreaId() > 0){
            		org = organizationService.get(uploadFile.getAreaId());
            	} else if (uploadFile.getRegionId() > 0){
            		org = organizationService.get(uploadFile.getRegionId());
            	} else if (uploadFile.getStateId() > 0){
            		org = organizationService.get(uploadFile.getStateId());
            	}
            	if (org == null){
            		errors.reject("Filter Error","Invalid district, building or school organization id.");
            		uploadMV.put("uploadErrors", errors.getAllErrors());
            	} else {
            		contractingOrganizationTree = organizationService.getTree(org);
            		uploadMV = uploadEnrollment(uploadFile, uploadMV, errors, contractingOrganizationTree, org.getId());
            	}

            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getScrsRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_ROSTERRECORD_UPLOAD")) {
            	Organization org = null;
            	Long stateId = uploadFile.getStateId();
            	Long districtId = uploadFile.getDistrictId();
            	Long schoolId = uploadFile.getSchoolId();
            	if (schoolId != null && schoolId > 0){
            		org = organizationService.get(schoolId);
            		organizationId = schoolId;
            	}else if (districtId != null && districtId > 0){
            		org = organizationService.get(districtId);
            		organizationId = districtId;
            	} else if (stateId != null && stateId > 0){
            		org = organizationService.get(stateId);
            		organizationId = stateId;
            	}
            	
            	if (org == null){
            		errors.reject("Filter Error","Invalid state, district and/or school organization id.");
            		uploadMV.put("uploadErrors", errors.getAllErrors());
                	uploadMV.put("stateId", uploadFile.getStateId());
                	uploadMV.put("districtId", uploadFile.getDistrictId());
                	uploadMV.put("schoolId", uploadFile.getSchoolId());
            	} else {
            		//DE5842 removed this call so the whole contract tree is passed back
            		//we need to have the whole tree available to be able to find
            		//the district and attendance school to look up the educator
            		//contractingOrganizationTree = organizationService.getTree(org);
                	uploadMV.put("schoolName", org.getOrganizationName());
            		uploadMV = uploadRoster(uploadFile, uploadMV, errors,
                		contractingOrganizationTree,organizationId);
            		uploadMV.put("stateId", uploadFile.getStateId());
                	uploadMV.put("districtId", uploadFile.getDistrictId());
                	uploadMV.put("schoolId", uploadFile.getSchoolId());
            	}
            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getUserRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_USER_UPLOAD")) {

                uploadUsersFromFile(uploadFile, errors, uploadMV, currentContext,
                		contractingOrganizationTree, user);

        		uploadMV.put("stateId", uploadFile.getStateId());
            	uploadMV.put("districtId", uploadFile.getDistrictId());
            	uploadMV.put("schoolId", uploadFile.getSchoolId());
            }else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getPdTrainingUploadType())
                    && permissionUtil.hasPermission(authorities, "PD_TRAINING_RESULTS_UPLOADER")) {

                uploadPDTrainingResultsFromFile(uploadFile, errors, uploadMV, currentContext,
                		contractingOrganizationTree, user);
                
            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getOrgRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_ORG_UPLOAD")) {

                uploadOrganizationsFromFile(uploadFile, errors, uploadMV, currentContext,
                		contractingOrganizationTree);
                organizationService.clearTreeCache(currentContext);

            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getPersonalNeedsProfileRecordType())
                    && permissionUtil.hasPermission(authorities, "PERSONAL_NEEDS_PROFILE_UPLOAD")) {

                uploadMV = uploadPersonalNeedsProfile(uploadFile, uploadMV, errors,
                		contractingOrganizationTree);

            } else if (selectedType.getCategoryCode(
            		).equalsIgnoreCase(uploadSpecification.getTestRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_TEST_UPLOAD")) {
            	//TODO add condition here to include record type.
                uploadMV = uploadTest(uploadFile, uploadMV, errors, contractingOrganizationTree);
            } else if (selectedType.getCategoryCode(
            		).equalsIgnoreCase(uploadSpecification.getFirstContactRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_FIRST_CONTACT_UPLOAD")) {

                uploadMV = uploadFirstContact(uploadFile, uploadMV, errors,
                		contractingOrganizationTree);

            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getTecRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_ENRL_UPLOAD")) {
            	Organization org = null;

            	if (uploadFile.getSchoolId() > 0){
            		org = organizationService.get(uploadFile.getSchoolId());
            	} else if (uploadFile.getBuildingId() > 0){
            		org = organizationService.get(uploadFile.getBuildingId());
            	} else if (uploadFile.getDistrictId() > 0){
            		org = organizationService.get(uploadFile.getDistrictId());
            	} else if (uploadFile.getAreaId() > 0){
            		org = organizationService.get(uploadFile.getAreaId());
            	} else if (uploadFile.getRegionId() > 0){
            		org = organizationService.get(uploadFile.getRegionId());
            	} else if (uploadFile.getStateId() > 0){
            		org = organizationService.get(uploadFile.getStateId());
            	}
            	if (org == null){
            		errors.reject("Filter Error","Invalid district, building or school organization id.");
            		uploadMV.put("uploadErrors", errors.getAllErrors());
            	} else {
            		contractingOrganizationTree = organizationService.getTree(org);
            		uploadMV = uploadTEC(uploadFile, uploadMV, errors, contractingOrganizationTree);
            	}

            }
        }
        
		uploadMV.remove("uploadFile");
		uploadMV.remove("recordTypes");
		uploadMV.put("uploadFileStatus", "COMPLETED");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
		String modelJson = mapper.writeValueAsString(uploadMV);
        
        Long completedId = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
        uploadMV.put("uploadEnrollmentFileStatus", "COMPLETED");
        UploadFileRecord uploadFileRecord = uploadFileRecordDao.selectByPrimaryKey(uploadFileRecordId);
        uploadFileRecord.setStatusId(completedId);
        uploadFileRecord.setModifiedDate(new Date());
        uploadFileRecord.setJsonData(modelJson);
        uploadFileRecordDao.updateByPrimaryKeySelective(uploadFileRecord);

        return uploadMV;
    }
    
    private Boolean validateIdentifiers(UploadFile uploadFile, Map<String,Object> uploadMV) throws IOException {
    	Boolean showWarning = false;
    	
    	CSVReader csvReader = null;
    	String fileCharset = null;
		String[] nextLine;
		String[] columns;

		Long recordType = uploadFile.getSelectedRecordTypeId();
		Integer ssidColumnIndex = -1, lsidColumnIndex = -1, eidColumnIndex = -1;
		
    	try {
    		InputStream inputStream = new FileInputStream(uploadFile.getFile());
        	fileCharset = detectCharset(inputStream);
			csvReader = new CSVReader(new InputStreamReader(inputStream,fileCharset),'\t'); 
		
			if(this.userRecordType.getId() == recordType || this.pdTrainingUploadType.getId() == recordType) {
				eidColumnIndex = 2;				
			} else if(this.scrsRecordType.getId() == recordType) {
				lsidColumnIndex = 3;
				ssidColumnIndex = 4;
				eidColumnIndex = 10;				
			} if(this.enrollmentRecordType.getId() == recordType) {
				lsidColumnIndex = 9;
				ssidColumnIndex = 10;
			}
			
			while ((nextLine = csvReader.readNext()) != null &&
					!showWarning) {
				columns = nextLine[0].split(",");
				if( lsidColumnIndex > -1 && nextLine != null && columns.length > lsidColumnIndex && 
						NumberUtils.isNumber(columns[lsidColumnIndex]) && columns[lsidColumnIndex].length() == 9) {
					showWarning = true;
					uploadMV.put("warningMessage", studentIdWarningMessage);
				}
				
				if( ssidColumnIndex > -1 && nextLine != null && columns.length > ssidColumnIndex && 
						NumberUtils.isNumber(columns[ssidColumnIndex]) &&  columns[ssidColumnIndex].length() == 9) {
					showWarning = true;
					uploadMV.put("warningMessage", studentIdWarningMessage);
				}
				
				if( eidColumnIndex > -1 && nextLine != null && columns.length > eidColumnIndex && 
						NumberUtils.isNumber(columns[eidColumnIndex]) &&  columns[eidColumnIndex].length() == 9) {
					showWarning = true;
					uploadMV.put("warningMessage", educatorIdWarningMessage);
				}								
			}
			
		} catch (IOException e) {
			 LOGGER.error("Error occured while processing file",  e);
		} finally {
			csvReader.close();
		}
		
    	return showWarning;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private Map<String,Object> uploadEnrollment(UploadFile uploadFile, Map<String,Object> uploadMV,
            Errors errors, ContractingOrganizationTree contractingOrganizationTree, Long orgId)
            throws IOException, ServiceException {
        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        List<EnrollmentRecord> enrollmentRecords = new ArrayList<EnrollmentRecord>();
        LOGGER.debug("UploadSpecification" + uploadSpecification);

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restriction restriction = getEnrollmentRestriction(userDetails);

        if (restriction != null) {
        	InputStream inputStream = new FileInputStream(uploadFile.getFile());
        	String fileCharset = detectCharset(inputStream);
        	CSVReader reader = new CSVReader(new InputStreamReader(inputStream, fileCharset)); 
        	
            AartColumnMappingStrategy<EnrollmentRecord>
            columnReadingStrategy = new AartColumnMappingStrategy<EnrollmentRecord>();
            columnReadingStrategy.setType(EnrollmentRecord.class);
            columnReadingStrategy.setColumnMapping(uploadSpecification.getEnrollmentColumnAttributeMap());
            AartCsvToBean<EnrollmentRecord> enrollmentBeanParser = new AartCsvToBean<EnrollmentRecord>();
            enrollmentBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(enrollmentRecordType));
            try {
                enrollmentRecords = enrollmentBeanParser.aartParse(columnReadingStrategy, reader);
            } catch (AartParseException e) {
                InValidDetail invalidDetail = InValidDetail.getInstance(
                        ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                        ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
                uploadMV.put("inValidDetail", invalidDetail);
                //the reason for failure cannot be captured.
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            } catch (InvocationTargetException e) {
                throw new IOException(e);
            } catch (InstantiationException e) {
                throw new IOException(e);
            } catch (IntrospectionException e) {
                throw new IOException(e);
            } finally {
            	inputStream.close();
            }
        } else {
            InValidDetail invalidDetail = InValidDetail.getInstance(FieldName.UPLOAD + ParsingConstants.BLANK,
                    ParsingConstants.BLANK, true,
                    InvalidTypes.NOT_ALLOWED);
            uploadMV.put("inValidDetail", invalidDetail);
        }
        List<ValidateableRecord> inValidEnrollments = new ArrayList<ValidateableRecord>();
        List<EnrollmentRecord> createdEnrollments = new ArrayList<EnrollmentRecord>();
        //TODO Consider removing the contractingOrganizationTree itself.
        List<? extends StudentRecord> studentRecords= studentService.verifyStateStudentIdentifiersExist(
        		contractingOrganizationTree.getContractingOrganizationTreeIds(),
        		contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
        		contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(),
        		enrollmentRecords);

        if (CollectionUtils.isNotEmpty(studentRecords)) {
        	AssessmentProgram ap = assessmentProgramDao.findByProgramName(dlmOrgName);
            for (StudentRecord studentRecord : studentRecords) {
            	EnrollmentRecord enrollmentRecord = (EnrollmentRecord) (studentRecord);
                if (!studentRecord.isDoReject()) {
                    //this is the only line where i need to access enrollment record.in other cases just validateable record is
                    //enough.
                    enrollmentRecord.getEnrollment().setRestrictionId(restriction.getId());
                    if (enrollmentRecord.getDlmStatus()){
                    	enrollmentRecord.setAssessmentProgramId(ap.getId());
                    }
                    enrollmentRecord.getEnrollment().setSourceType(SourceTypeEnum.UPLOAD.getCode());
                    enrollmentRecord.getStudent().setStateId(userDetails.getUser().getContractingOrgId());
                    enrollmentRecord = enrollmentService.cascadeAddOrUpdate(enrollmentRecord,
                    		contractingOrganizationTree, orgId, (int)(long)userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
                    if (!enrollmentRecord.isDoReject()) {
                        createdEnrollments.add(enrollmentRecord);
                        if (enrollmentRecord.isCreated()) {
                            recordsCreatedCount++;
                        } else {
                            recordsUpdatedCount++;
                        }
                    } else {
                        recordsRejectedCount++;
                    }
                } else {
                    recordsRejectedCount++;
                }
                if (enrollmentRecord.isInValid()) {
                    inValidEnrollments.add(enrollmentRecord);
                }
            }

            if (CollectionUtils.isNotEmpty(inValidEnrollments)) 
            {   
            	if(inValidEnrollments.size() > 100 && recordsRejectedCount > 100)
            		uploadMV.put("inValidRecords", new ArrayList<ValidateableRecord>(inValidEnrollments.subList(0, 100)));            
            	else 
            		uploadMV.put("inValidRecords", inValidEnrollments);
            }
        }

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.put("uploadErrors", errors.getAllErrors());
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());
        
        //uploadMV.addObject("createdEnrollments", createdEnrollments);
        uploadMV.put("recordsSkippedCount", 0);
        uploadMV.put("recordsCreatedCount", recordsCreatedCount);
        uploadMV.put("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.put("recordsRejectedCount", recordsRejectedCount);
        uploadMV.put("totalRecordCount", enrollmentRecords.size());

        return uploadMV;
    }
    
    private String detectCharset(InputStream fis) throws IOException {
	    return java.nio.charset.Charset.defaultCharset().name();
    }
    
    private Restriction getEnrollmentRestriction(UserDetailImpl userDetails) {
        //Find the restriction for what the user is trying to do on this page.
        Restriction restriction = resourceRestrictionService.getResourceRestriction(
                userDetails.getUser().getOrganization().getId(),
                permissionUtil.getAuthorityId(
                        userDetails.getUser().getAuthoritiesList(),
                        RestrictedResourceConfiguration.getUploadEnrollmentPermissionCode()),
                        null,
                restrictedResourceConfiguration.getEnrollmentResourceCategory().getId());
        return restriction;
    }
    
    private Map<String, FieldSpecification> getFieldSpecificationMap(Category recordType) {
        Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();
        FieldSpecificationExample fieldSpecificationExample = new FieldSpecificationExample();
        if (recordType == null) {
            return fieldSpecificationMap;
        }
        fieldSpecificationExample.createCriteria().andRecordTypeIdEqualTo(recordType.getId());
        List<FieldSpecification> fieldSpecifications = fieldSpecificationDao.selectByExample(fieldSpecificationExample);
        if (CollectionUtils.isNotEmpty(fieldSpecifications)) {
            for (FieldSpecification fieldSpecification : fieldSpecifications) {
                //LOGGER.debug("FieldSpecification.toString() :" + fieldSpecification.toString());
            	//For PNP, need to support all cases, so convert fieldnames to lowercase.
            	if(recordType.getCategoryCode().equalsIgnoreCase(personalNeedsProfileRecordRecordType.getCategoryCode())) {
            		fieldSpecificationMap.put(fieldSpecification.getFieldName().toLowerCase(), fieldSpecification);
            	} else {
            		fieldSpecificationMap.put(fieldSpecification.getFieldName(), fieldSpecification);
            		
            	}
            }
        }
        //LOGGER.debug(fieldSpecificationMap);
        LOGGER.debug(fieldSpecificationMap.toString());
        return fieldSpecificationMap;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private Map<String,Object> uploadRoster(UploadFile uploadFile, Map<String,Object> uploadMV,
            Errors errors, ContractingOrganizationTree contractingOrganizationTree,long organizationId)
            throws IOException, ServiceException {
        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        int recordsRemovedCount = 0;
        List<RosterRecord> rosterRecords = new ArrayList<RosterRecord>();
        Set<Long> rosterIdsCreated = new HashSet<Long>();
        
        LOGGER.debug("UploadSpecification" + uploadSpecification);

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //int loggedUserCurrSchYear = rosterService.getCurrentSchoolYear(userDetails.getUser().getCurrentOrganizationId());        
        int loggedUserCurrSchYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        
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
        //If no restriction is found why even try parsing the file, because they cannot be saved.
        if (restriction != null) {  
        	InputStream inputStream = new FileInputStream(uploadFile.getFile());
        	String fileCharset = detectCharset(inputStream);
        	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset)); 
        	
            AartColumnMappingStrategy<RosterRecord> columnReadingStrategy = new AartColumnMappingStrategy<RosterRecord>();
            columnReadingStrategy.setType(RosterRecord.class);
            columnReadingStrategy.setColumnMapping(uploadSpecification.getScrsColumnAttributeMap());

            AartCsvToBean<RosterRecord> scrsBeanParser = new AartCsvToBean<RosterRecord>();
            scrsBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(scrsRecordType));

            try {
                rosterRecords = scrsBeanParser.aartParse(columnReadingStrategy, reader);
            } catch (AartParseException e) {
                InValidDetail invalidDetail = InValidDetail.getInstance(
                        ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                        ParsingConstants.BLANK, true,
                        InvalidTypes.IN_CORRECT);
                uploadMV.put("inValidDetail", invalidDetail);
                //the reason for failure cannot be captured.
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            } catch (InvocationTargetException e) {
                throw new IOException(e);
            } catch (InstantiationException e) {
                throw new IOException(e);
            } catch (IntrospectionException e) {
                throw new IOException(e);
            } finally {
            	inputStream.close();
            }
        } else {
            //The user is not allowed to upload rosters.
            InValidDetail invalidDetail = InValidDetail.getInstance(
            		FieldName.UPLOAD + ParsingConstants.BLANK,
                    ParsingConstants.BLANK, true,
                    InvalidTypes.NOT_ALLOWED);
            uploadMV.put("inValidDetail", invalidDetail);
        }

        List<ValidateableRecord> inValidScrsRecords = new ArrayList<ValidateableRecord>();
        
        List<? extends StudentRecord> studentRecords = studentService.verifyStateStudentIdentifiersExist(
        		contractingOrganizationTree.getContractingOrganizationTreeIds(),
        		contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
        		contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(),
        		rosterRecords);
        
        if (CollectionUtils.isNotEmpty(studentRecords)) {
            //TODO - call a service layer method passing the scrsRecords.
            //Passing the invalidScrsRecords list and the scrsRescords
        	AssessmentProgram ap = assessmentProgramDao.findByProgramName(dlmOrgName);
        	
            for (StudentRecord studentRecord : studentRecords) {
            	RosterRecord rosterRecord = (RosterRecord) (studentRecord);
            	rosterRecord.setRosterRecordType(scrsRecordType.getCategoryCode());
            	rosterRecord.setSourceType(SourceTypeEnum.UPLOAD.getCode());
            	rosterService.validateSchoolIdentifiers(rosterRecord, organizationId, loggedUserCurrSchYear);
                if (!rosterRecord.isDoReject()) {
                    rosterRecord.getRoster().setRestriction(restriction);
                    rosterRecord.getRoster().setRestrictionId(restriction.getId());
                    if (rosterRecord.getDlmStatus()){
                    	rosterRecord.setAssessmentProgramId(ap.getId());
                    }                    
                    //this is the only line where i need to access roster record.
                    //In other cases just validateable record is
                    //enough.
                    rosterRecord.getStudent().setStateId(userDetails.getUser().getContractingOrgId());                    
                    rosterRecord = rosterService.cascadeAddOrUpdate(rosterRecord, contractingOrganizationTree);
                    if (!rosterRecord.isDoReject()) {
                    	if(rosterRecord.isRemoved()) {
                    		recordsRemovedCount++;
                    	} else if (rosterRecord.isCreated()) {
                            recordsCreatedCount++;
                        } else {
                            recordsUpdatedCount++;
                        }
                    } else {
                        recordsRejectedCount++;
                    }
                } else {
                    recordsRejectedCount++;
                }
                if (rosterRecord.isInValid()) {
                    inValidScrsRecords.add(rosterRecord);
                }
                if (rosterRecord.getSaveStatus()==RecordSaveStatus.SCRS_RECORD_UPLOAD_COMPLETE) {
                	
                	rosterIdsCreated.add(rosterRecord.getRoster().getId());
                }
            }
            if (CollectionUtils.isNotEmpty(inValidScrsRecords)) 
            {   
            	if(inValidScrsRecords.size() > 100 && recordsRejectedCount > 100)
            		uploadMV.put("inValidRecords", new ArrayList<ValidateableRecord>(inValidScrsRecords.subList(0, 100)));            		            	
            	else 
            		uploadMV.put("inValidRecords", inValidScrsRecords);
            }
        }
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.put("uploadErrors", errors.getAllErrors());
            return uploadMV;
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());

       /* if(rosterIdsCreated.isEmpty()) {
        	 uploadMV.addObject("rostersCreated",null);
        } else {
        	 ArrayList<Long> list = new ArrayList<Long>();
        	 list.addAll(rosterIdsCreated);
        	uploadMV.addObject("rostersCreated",rosterService.getRosterDtoInRosterIds(list));
        }*/
        uploadMV.put("recordsSkippedCount", 0);
        uploadMV.put("recordsCreatedCount", recordsCreatedCount);
        uploadMV.put("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.put("recordsRejectedCount", recordsRejectedCount);
        uploadMV.put("recordsRemovedCount", recordsRemovedCount);
        uploadMV.put("totalRecordCount", rosterRecords.size());
        return uploadMV;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void uploadUsersFromFile(UploadFile uploadFile, Errors result, Map<String,Object> mav, Organization currentContext,
    		ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser) {
        LOGGER.debug("Entering the uploadUserFromFile method.");

        List<UploadedUser> uploadedUsers = new ArrayList<UploadedUser>();
        List<UploadedUser> invalidUsers = new ArrayList<UploadedUser>();
        List<UploadedUser> successUsers = new ArrayList<UploadedUser>();

        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        int totalRecordCount = 0;
        
        try {
            if (uploadFile.getFile() != null) {
            	InputStream inputStream = new FileInputStream(uploadFile.getFile());
            	String fileCharset = detectCharset(inputStream);
            	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset));            	
                AartColumnMappingStrategy<UploadedUser> columnReadingStrategy = new AartColumnMappingStrategy<UploadedUser>();
                columnReadingStrategy.setType(UploadedUser.class);
                columnReadingStrategy.setColumnMapping(uploadSpecification.getUserColumnMap());
                
                AartCsvToBean<UploadedUser> aartCsvToBeanParser = new AartCsvToBean<UploadedUser>();
                aartCsvToBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(userRecordType));
                uploadedUsers = aartCsvToBeanParser.aartParse(columnReadingStrategy, reader);
                LOGGER.debug("uploadedUsers "+uploadedUsers);
                LOGGER.debug("Received {} users to upload to the system.", uploadedUsers.size());

                Long parentOrgId = uploadFile.getSelectedOrgId();
                
                //This is clean.
                int createdUsers = userService.checkUploadedUsers(uploadedUsers, invalidUsers, currentContext,
                		contractingOrganizationTree, currentUser, successUsers, parentOrgId);
                
                
                /*mav.addObject("successUsers", successUsers);*/
                recordsCreatedCount = createdUsers;
                recordsRejectedCount = invalidUsers.size();
                totalRecordCount = uploadedUsers.size();
                
                if (CollectionUtils.isNotEmpty(invalidUsers)) 
                {   
                	if(invalidUsers.size() > 100 && recordsRejectedCount > 100)
                		mav.put("inValidRecords",new  ArrayList<UploadedUser>(invalidUsers.subList(0, 100)));
                	else  
                		mav.put("inValidRecords", invalidUsers);
                }
                
                LOGGER.debug("Rejected {} users from the uploaded file.", invalidUsers.size());
                inputStream.close();
            } else {
                LOGGER.debug("File uploaded is empty or doesn't exist. Returning error message back to user.");
                mav.put("invalidUploadedFile", true);
            }
        } catch (IOException e) {
            LOGGER.error("Caught IOException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_READ_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (AartParseException e) {
            LOGGER.error("Caught AartParseException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                    ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
            mav.put("inValidDetail", invalidDetail);
        } catch (IllegalAccessException e) {
            LOGGER.error("Caught IllegalAccessException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.ACCESS_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (InvocationTargetException e) {
            LOGGER.error("Caught InvocationTargetException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (InstantiationException e) {
            LOGGER.error("Caught InstantiationException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (IntrospectionException e) {
            LOGGER.error("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (ServiceException e) {
            LOGGER.error("Caught ServiceException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } 
        if (CollectionUtils.isNotEmpty(invalidUsers)) 
        {   
        	if(invalidUsers.size() > 100 && recordsRejectedCount > 100)    	
        		mav.put("inValidRecords", new ArrayList<UploadedUser>(invalidUsers.subList(0, 100)));    
        	else
        		mav.put("inValidRecords", invalidUsers);
        }
        mav.put("recordsSkippedCount", 0);
        mav.put("recordsCreatedCount", recordsCreatedCount);
        mav.put("recordsUpdatedCount", recordsUpdatedCount);
        mav.put("recordsRejectedCount", recordsRejectedCount);
        mav.put("totalRecordCount", totalRecordCount);

        LOGGER.trace("Leaving the uploadUserFromFile method.");
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void uploadPDTrainingResultsFromFile(UploadFile uploadFile, Errors result, Map<String,Object> mav, Organization currentContext,
    		ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser) {
        LOGGER.debug("Entering the uploadPDTrainingResultsFromFile method.");

        List<User> uploadedUsers = new ArrayList<User>();
        List<User> invalidUsers = new ArrayList<User>();
        List<User> successUsers = new ArrayList<User>();

        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        int totalRecordCount = 0;
        
        try {
            if (uploadFile.getFile() != null) {
            	InputStream inputStream = new FileInputStream(uploadFile.getFile());
            	String fileCharset = detectCharset(inputStream);
            	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset));            	
                AartColumnMappingStrategy<User> columnReadingStrategy = new AartColumnMappingStrategy<User>();
                columnReadingStrategy.setType(User.class);
                LOGGER.debug("uploadSpecification.getPdTrainingResultsMap() "+uploadSpecification.getPdTrainingResultsMap());
                columnReadingStrategy.setColumnMapping(uploadSpecification.getPdTrainingResultsMap());
                
                AartCsvToBean<User> aartCsvToBeanParser = new AartCsvToBean<User>();
                aartCsvToBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(pdTrainingUploadType));
                uploadedUsers = aartCsvToBeanParser.aartParse(columnReadingStrategy, reader);
                
                LOGGER.debug("Received {} users to upload to the system.", uploadedUsers.size());
                LOGGER.error("Received {} users to upload to the system.", uploadedUsers.size());

                Long parentOrgId = uploadFile.getSelectedOrgId();
                
                //This is clean.
                int uploadedPDTrainingResultsCount = userService.checkUploadedPDTrainingResults(uploadedUsers, invalidUsers, currentContext,
                		contractingOrganizationTree, currentUser, successUsers, parentOrgId);
                
                /*mav.addObject("successUsers", successUsers);*/
                recordsCreatedCount = uploadedPDTrainingResultsCount;
                recordsRejectedCount = invalidUsers.size();
                totalRecordCount = uploadedUsers.size();
                
                if (CollectionUtils.isNotEmpty(invalidUsers)) 
                {   
                	if(invalidUsers.size() > 100 && recordsRejectedCount > 100)
                		mav.put("inValidRecords",new  ArrayList<User>(invalidUsers.subList(0, 100)));
                	else  
                		mav.put("inValidRecords", invalidUsers);
                }
                
                LOGGER.debug("Rejected {} users from the uploaded file.", invalidUsers.size());
                inputStream.close();
            } else {
                LOGGER.debug("File uploaded is empty or doesn't exist. Returning error message back to user.");
                mav.put("invalidUploadedFile", true);
            }
        } catch (IOException e) {
            LOGGER.error("Caught IOException in uploadPDTrainingResultsFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_READ_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (AartParseException e) {
            LOGGER.error("Caught AartParseException in uploadPDTrainingResultsFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                    ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
            mav.put("inValidDetail", invalidDetail);
        } catch (IllegalAccessException e) {
            LOGGER.error("Caught IllegalAccessException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.ACCESS_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (InvocationTargetException e) {
            LOGGER.error("Caught InvocationTargetException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (InstantiationException e) {
            LOGGER.error("Caught InstantiationException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (IntrospectionException e) {
            LOGGER.error("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } catch (ServiceException e) {
            LOGGER.error("Caught ServiceException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.put("inValidDetail", invalidDetail);
        } 
        if (CollectionUtils.isNotEmpty(invalidUsers)) 
        {   
        	if(invalidUsers.size() > 100 && recordsRejectedCount > 100)    	
        		mav.put("inValidRecords", new ArrayList<User>(invalidUsers.subList(0, 100)));    
        	else
        		mav.put("inValidRecords", invalidUsers);
        }
        mav.put("recordsSkippedCount", 0);
        mav.put("recordsCreatedCount", recordsCreatedCount);
        mav.put("recordsUpdatedCount", recordsCreatedCount);
        mav.put("recordsRejectedCount", recordsRejectedCount);
        mav.put("totalRecordCount", totalRecordCount);

        LOGGER.trace("Leaving the uploadPDTrainingResultsFromFile method.");
    }
    
    @SuppressWarnings("unchecked")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public void uploadOrganizationsFromFile(UploadFile uploadFile, Errors result, Map<String,Object> mav,
    		Organization currentUserOrg, ContractingOrganizationTree contractingOrganizationTree) {

    	List<UploadedOrganization> invalidOrgs = new ArrayList<UploadedOrganization>();    
    	mav.put("recordsSkippedCount", 0);
        mav.put("recordsCreatedCount", 0);
        mav.put("recordRejectedCount", 0);
        mav.put("totalRecordsProcessed", 0);

        try {
            if (uploadFile.getFile() != null) {
                            
                LOGGER.debug("orgRecordType :" + orgRecordType.getId());
                Map<String, FieldSpecification> fieldSpecificationMap = getFieldSpecificationMap(orgRecordType);    
                         
                Map<String, Object> resultObj = uploadService.bulkUploadOrganization(uploadFile,
                		fieldSpecificationMap, currentUserOrg, contractingOrganizationTree, invalidOrgs, 
                		detectCharset(new FileInputStream(uploadFile.getFile())), defaultFileCharset);       

                List<Organization> createdOrgsList = (List<Organization>)resultObj.get("createdOrgs");
                for(Organization createdOrg:createdOrgsList) {
                	organizationService.clearTreeCache(createdOrg);
                }
               /* mav.addObject("createdOrgs", resultObj.get("createdOrgs"));*/
                mav.put("recordsCreatedCount", resultObj.get("recordsCreatedCount"));
                mav.put("recordsRejectedCount", resultObj.get("recordsRejectedCount"));
                mav.put("recordsUpdatedCount", resultObj.get("recordsUpdatedCount"));
                mav.put("totalRecordCount", resultObj.get("totalRecordCount"));
                if (CollectionUtils.isNotEmpty(invalidOrgs)) 
                {   
                	if(invalidOrgs.size() > 100 && (Integer) resultObj.get("recordsRejectedCount") > 100)
                		mav.put("inValidRecords", new ArrayList<UploadedOrganization>(invalidOrgs.subList(0, 100)));                		                 	
                	else 
                		mav.put("inValidRecords", invalidOrgs);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Caught IOException in uploadOranizationsFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.put("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (AartParseException e) {
            LOGGER.error("Caught AartParseException in uploadOrganizationsFromFile. StackTrace: {}" + e.getStackTrace());
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                    ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
            mav.put("inValidDetail", invalidDetail);
        } catch (IllegalAccessException e) {
            LOGGER.error("Caught IllegalAccessException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.put("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (InvocationTargetException e) {
            LOGGER.error("Caught InvocationTargetException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.put("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (InstantiationException e) {
            LOGGER.error("Caught InstantiationException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.put("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (IntrospectionException e) {
            LOGGER.error("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.put("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        }
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private Map<String,Object> uploadPersonalNeedsProfile(
    		UploadFile uploadFile,
    		Map<String,Object> uploadMV, Errors errors,
			ContractingOrganizationTree contractingOrganizationTree) throws IOException {
        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        List<PersonalNeedsProfileRecord> personalNeedsProfileRecords = new ArrayList<PersonalNeedsProfileRecord>();
        LOGGER.debug("UploadSpecification" + uploadSpecification);

        InputStream inputStream = new FileInputStream(uploadFile.getFile());
    	String fileCharset = detectCharset(inputStream);
    	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset)); 
        
        AartColumnMappingStrategy<PersonalNeedsProfileRecord> columnReadingStrategy = new AartColumnMappingStrategy<PersonalNeedsProfileRecord>();
        columnReadingStrategy.setType(PersonalNeedsProfileRecord.class);
        columnReadingStrategy.setColumnMapping(uploadSpecification.getPersonalNeedsProfileColumnAttributeMap());

        AartCsvVerticalParser<PersonalNeedsProfileRecord> personalNeedsProfileBeanParser = new AartCsvVerticalParser<PersonalNeedsProfileRecord>();
        personalNeedsProfileBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(personalNeedsProfileRecordRecordType));

        try {
        	personalNeedsProfileRecords = personalNeedsProfileBeanParser.aartParse(columnReadingStrategy, reader);
        } catch (AartParseException e) {
        	InValidDetail invalidDetail = InValidDetail.getInstance(
        			ParsingConstants.BLANK + FieldName.FILE_FORMAT,
        			ParsingConstants.BLANK, true,
        			InvalidTypes.IN_CORRECT);
        	uploadMV.put("inValidDetail", invalidDetail);
        	//the reason for failure cannot be captured.
        } catch (IllegalAccessException e) {
        	throw new IOException(e);
        } catch (InvocationTargetException e) {
        	throw new IOException(e);
        } catch (InstantiationException e) {
        	throw new IOException(e);
        } catch (IntrospectionException e) {
        	throw new IOException(e);
        }

        List<ValidateableRecord> inValidPersonalNeedsProfileRecords = new ArrayList<ValidateableRecord>();

        if (CollectionUtils.isNotEmpty(personalNeedsProfileRecords)) {
        	personalNeedsProfileRecords = uploadService.cascadeAddOrUpdate(personalNeedsProfileRecords,
        			contractingOrganizationTree.getUserOrganizationTree());
        }
        for(PersonalNeedsProfileRecord personalNeedsProfileRecord:personalNeedsProfileRecords) {
        	if(personalNeedsProfileRecord.isDoReject()) {
        		inValidPersonalNeedsProfileRecords.add(personalNeedsProfileRecord);
        	}
        	if(personalNeedsProfileRecord.isDoReject()) {
        		recordsRejectedCount ++;
        	} else if(personalNeedsProfileRecord.isCreated()) {
        		recordsCreatedCount ++;
        	} else {
        		recordsUpdatedCount ++;
        	}
        }
        if (errors.hasErrors()) {
        	for (ObjectError error : errors.getAllErrors()) {
        		LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
        	}
        	uploadMV.put("uploadErrors", errors.getAllErrors());
        	return uploadMV;
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());

        uploadMV.put("recordsSkippedCount", 0);
        uploadMV.put("recordsCreatedCount", recordsCreatedCount);
        uploadMV.put("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.put("inValidRecords", inValidPersonalNeedsProfileRecords);
        uploadMV.put("recordsRejectedCount", recordsRejectedCount);
        uploadMV.put("totalRecordCount", personalNeedsProfileRecords.size());

        return uploadMV;
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private Map<String,Object> uploadTest(UploadFile uploadFile, Map<String,Object> uploadMV, Errors errors,
    		ContractingOrganizationTree contractingOrganizationTree)
            throws IOException, ServiceException {
        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        List<TestRecord> testRecords = new ArrayList<TestRecord>();
        LOGGER.debug("UploadSpecification" + uploadSpecification);

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restriction restriction = getEnrollmentRestriction(userDetails);

        if (restriction != null) {
        	InputStream inputStream = new FileInputStream(uploadFile.getFile());
        	String fileCharset = detectCharset(inputStream);
        	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset)); 
        	
            AartColumnMappingStrategy<TestRecord> columnReadingStrategy = new AartColumnMappingStrategy<TestRecord>();
            columnReadingStrategy.setType(TestRecord.class);
            columnReadingStrategy.setColumnMapping(uploadSpecification.getTestColumnAttributeMap());
            AartCsvToBean<TestRecord> scrsBeanParser = new AartCsvToBean<TestRecord>();
            scrsBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(testRecordType));
            try {
                testRecords = scrsBeanParser.aartParse(columnReadingStrategy, reader);
            } catch (AartParseException e) {
                InValidDetail invalidDetail = InValidDetail.getInstance(
                        ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                        ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
                uploadMV.put("inValidDetail", invalidDetail);
                //the reason for failure cannot be captured.
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            } catch (InvocationTargetException e) {
                throw new IOException(e);
            } catch (InstantiationException e) {
                throw new IOException(e);
            } catch (IntrospectionException e) {
                throw new IOException(e);
            }
        } else {
            InValidDetail invalidDetail = InValidDetail.getInstance(FieldName.UPLOAD + ParsingConstants.BLANK,
                    ParsingConstants.BLANK, true,
                    InvalidTypes.NOT_ALLOWED);
            uploadMV.put("inValidDetail", invalidDetail);
        }
        List<ValidateableRecord> inValidTestRecords = new ArrayList<ValidateableRecord>();
        List<? extends StudentRecord> studentRecords= studentService.verifyStateStudentIdentifiersExist(
        		contractingOrganizationTree.getContractingOrganizationTreeIds(),
        		contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
        		contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(),
        		testRecords);
        
        if (CollectionUtils.isNotEmpty(studentRecords)) {
            for (StudentRecord studentRecord : studentRecords) {
            	TestRecord testRecord = (TestRecord) (studentRecord);            	
                if (!testRecord.isDoReject()) {
                    testRecord.getEnrollment().setRestrictionId(restriction.getId());
                    if (testRecord.getDlmStatus()){
                    	AssessmentProgram ap = assessmentProgramDao.findByProgramName(dlmOrgName);
                    	testRecord.setAssessmentProgramId(ap.getId());
                    }
                    testRecord = enrollmentService.cascadeAddOrUpdate(testRecord, contractingOrganizationTree);
                    if (!testRecord.isDoReject()) {
                        if (testRecord.isCreated()) {
                            recordsCreatedCount++;
                        } else {
                            recordsUpdatedCount++;
                        }
                    } else {
                        recordsRejectedCount++;
                    }
                } else {
                    recordsRejectedCount++;
                }
                if (testRecord.isInValid()) {
                    inValidTestRecords.add(testRecord);
                }
            }
            if (CollectionUtils.isNotEmpty(inValidTestRecords)) {
                uploadMV.put("inValidRecords", inValidTestRecords);
            }
        }
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.put("uploadErrors", errors.getAllErrors());
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());
        uploadMV.put("recordsSkippedCount", 0);
        uploadMV.put("recordsCreatedCount", recordsCreatedCount);
        uploadMV.put("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.put("recordsRejectedCount", recordsRejectedCount);
        uploadMV.put("totalRecordCount", testRecords.size());

        return uploadMV;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private Map<String,Object> uploadFirstContact(UploadFile uploadFile,
			Map<String,Object> uploadMV, Errors errors,
			ContractingOrganizationTree contractingOrganizationTree) throws IOException {
        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        int recordsSkippedCount = 0;
        List<FirstContactRecord> firstContactRecords = new ArrayList<FirstContactRecord>();
        LOGGER.debug("UploadSpecification" + uploadSpecification);
        
        //UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //If no restriction is found why even try parsing the file, because they cannot be saved.
        InputStream inputStream = new FileInputStream(uploadFile.getFile());
    	String fileCharset = detectCharset(inputStream);
    	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset)); 
        
        AartColumnMappingStrategy<FirstContactRecord> columnReadingStrategy = new AartColumnMappingStrategy<FirstContactRecord>();
        columnReadingStrategy.setType(FirstContactRecord.class);
        columnReadingStrategy.setColumnMapping(uploadSpecification.getFirstContactColumnAttributeMap());

        AartCsvToBean<FirstContactRecord> firstContactBeanParser = new AartCsvToBean<FirstContactRecord>();
        firstContactBeanParser.setColumnAttributeMap(uploadSpecification.getFirstContactColumnAttributeMap()) ;
        firstContactBeanParser.setFieldSpecificationMap(
        		this.getFieldSpecificationMap(firstContactRecordType));

        try {
        	firstContactRecords = firstContactBeanParser.aartParse(columnReadingStrategy, reader);
        } catch (AartParseException e) {
        	InValidDetail invalidDetail = InValidDetail.getInstance(
        			ParsingConstants.BLANK + FieldName.FILE_FORMAT,
        			ParsingConstants.BLANK, true,
        			InvalidTypes.IN_CORRECT);
        	uploadMV.put("inValidDetail", invalidDetail);
        	//the reason for failure cannot be captured.
        } catch (IllegalAccessException e) {
        	throw new IOException(e);
        } catch (InvocationTargetException e) {
        	throw new IOException(e);
        } catch (InstantiationException e) {
        	throw new IOException(e);
        } catch (IntrospectionException e) {
        	throw new IOException(e);
        }


        List<ValidateableRecord> inValidFirstContactRecords = new ArrayList<ValidateableRecord>();

        List<? extends StudentRecord> studentRecords = studentService.verifyStudentsExist(
        		contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
        		firstContactRecords);
        
        
        //TODO for the time being this relies on same JVM execution for the marking on roster records.
        
        if (CollectionUtils.isNotEmpty(studentRecords)) {
            //call a service layer method passing the scrsRecords.Passing the invalidScrsRecords list and the scrsRescords
            for (StudentRecord studentRecord : studentRecords) {
            	FirstContactRecord firstContactRecord = (FirstContactRecord) (studentRecord);
                if (!firstContactRecord.isDoReject()) {
                    //this is the only line where i need to access roster record.
                    //In other cases just validateable record is
                    //enough.

                    firstContactRecord = firstContactService.cascadeAddOrUpdate(firstContactRecord);                	
                	
                	if (!firstContactRecord.isDoReject()) {
                        if (firstContactRecord.isCreated()) {
                            recordsCreatedCount++;
                        } else if (!firstContactRecord.isExistingRecord()){
                            recordsUpdatedCount++;
                        }else {
                        	recordsSkippedCount++;
                        }
                    } else {
                        recordsRejectedCount++;
                    }
                } else {
                    recordsRejectedCount++;
                }
                if (firstContactRecord.isInValid()) {
                	inValidFirstContactRecords.add(firstContactRecord);
                }
            }
            if (CollectionUtils.isNotEmpty(inValidFirstContactRecords)) {
                uploadMV.put("inValidRecords", inValidFirstContactRecords);
            }
        }
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.put("uploadErrors", errors.getAllErrors());
            return uploadMV;
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());

        uploadMV.put("recordsSkippedCount", recordsSkippedCount);
        uploadMV.put("recordsCreatedCount", recordsCreatedCount);
        uploadMV.put("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.put("recordsRejectedCount", recordsRejectedCount);
        uploadMV.put("totalRecordCount", firstContactRecords.size());

        return uploadMV;
    }
	
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private Map<String,Object> uploadTEC(UploadFile uploadFile, Map<String,Object> uploadMV,
            Errors errors, ContractingOrganizationTree contractingOrganizationTree)
            throws IOException, ServiceException {
        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        List<TecRecord> tecRecords = new ArrayList<TecRecord>();
        LOGGER.debug("UploadSpecification" + uploadSpecification);

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Restriction restriction = getTecRestriction(userDetails);

        if (restriction != null) {
        	InputStream inputStream = new FileInputStream(uploadFile.getFile());
        	String fileCharset = detectCharset(inputStream);
        	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset)); 
        	
            AartColumnMappingStrategy<TecRecord> columnReadingStrategy = new AartColumnMappingStrategy<TecRecord>();
            columnReadingStrategy.setType(TecRecord.class);
            columnReadingStrategy.setColumnMapping(uploadSpecification.getTecColumnAttributeMap());
            AartCsvToBean<TecRecord> tecBeanParser = new AartCsvToBean<TecRecord>();
            tecBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(tecRecordType));
            try {
                tecRecords = tecBeanParser.aartParse(columnReadingStrategy, reader);
            } catch (AartParseException e) {
                InValidDetail invalidDetail = InValidDetail.getInstance(
                        ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                        ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
                uploadMV.put("inValidDetail", invalidDetail);
                //the reason for failure cannot be captured.
            } catch (IllegalAccessException e) {
                throw new IOException(e);
            } catch (InvocationTargetException e) {
                throw new IOException(e);
            } catch (InstantiationException e) {
                throw new IOException(e);
            } catch (IntrospectionException e) {
                throw new IOException(e);
            } finally {
            	inputStream.close();
            }
        } else {
            InValidDetail invalidDetail = InValidDetail.getInstance(FieldName.UPLOAD + ParsingConstants.BLANK,
                    ParsingConstants.BLANK, true,
                    InvalidTypes.NOT_ALLOWED);
            uploadMV.put("inValidDetail", invalidDetail);
        }
        checkTecRecordDependencies(tecRecords);
        List<ValidateableRecord> inValidTecs = new ArrayList<ValidateableRecord>();
        List<TecRecord> createdTecs = new ArrayList<TecRecord>();
        List<? extends StudentRecord> studentRecords= studentService.verifyStateStudentIdentifiersExist(
        		contractingOrganizationTree.getContractingOrganizationTreeIds(),
        		contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
        		contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(),
        		tecRecords);

        if (CollectionUtils.isNotEmpty(studentRecords)) {
            for (StudentRecord studentRecord : studentRecords) {
            	TecRecord tecRecord = (TecRecord) (studentRecord);
                if (!studentRecord.isDoReject()) {
                		tecRecord.getEnrollment().setRestrictionId(restriction.getId());
                		tecRecord = enrollmentService.cascadeAddOrUpdate(tecRecord, contractingOrganizationTree, userDetails.getUser());
                    if (!tecRecord.isDoReject()) {
                        createdTecs.add(tecRecord);
                        if (tecRecord.isCreated()) {
                            recordsCreatedCount++;
                        } else {
                            recordsUpdatedCount++;
                        }
                    } else {
                        recordsRejectedCount++;
                    }
                } else {
                    recordsRejectedCount++;
                }
                if (tecRecord.isInValid()) {
                    inValidTecs.add(tecRecord);
                }
            }
            if (CollectionUtils.isNotEmpty(inValidTecs)) 
            {   
            	if(inValidTecs.size() > 100 && recordsRejectedCount > 100)
            		uploadMV.put("inValidRecords", new ArrayList<ValidateableRecord>(inValidTecs.subList(0, 100)));            		            
            	else 
            		uploadMV.put("inValidRecords", inValidTecs);
            }
        }

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
                }
            uploadMV.put("uploadErrors", errors.getAllErrors());
            }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());
        
        /*uploadMV.addObject("createdTecs", createdTecs);*/
        uploadMV.put("recordsSkippedCount", 0);
        uploadMV.put("recordsCreatedCount", recordsCreatedCount);
        uploadMV.put("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.put("recordsRejectedCount", recordsRejectedCount);
        uploadMV.put("totalRecordCount", tecRecords.size());

        return uploadMV;
    }
    
    public void checkTecRecordDependencies(List<TecRecord> tecRecords) {

    	for (TecRecord tecRecord : tecRecords){
    		String recordType = tecRecord.getRecordType();
    		if (recordType ==null || recordType.isEmpty())
    			continue;
    		else if (recordType.equalsIgnoreCase("Exit")){
    			
    			if (tecRecord.getExitReason() == 0 ){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.EXIT_REASON, "", true, InvalidTypes.EMPTY, " Exit reason is blank and is required when the record type is set to Exit.");
    			}
    			
    			if (tecRecord.getExitDate() == null ){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.EXIT_DATE, "", true, InvalidTypes.EMPTY, " Exit Date is blank and is required when the record type is set to Exit.");
    			}
    			
    			List<InValidDetail> inValidDetails = tecRecord.getInValidDetails();
    			
    			for (int i=0; i < inValidDetails.size(); i++){

					InValidDetail InValidDetail = (InValidDetail) inValidDetails.get(i);					
					if(InValidDetail.getFieldName().equalsIgnoreCase("Test Type")) {
						if(inValidDetails.size() == 1 && tecRecord.isDoReject()) {
							tecRecord.setDoRejectOverride(false);
						}
						inValidDetails.remove(i);
						i = i - 1;
					}
				}
    			
    		}else if (recordType.equalsIgnoreCase("Test")){
    			if (tecRecord.getTestType() == null || tecRecord.getTestType().equals("")){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.TEST_TYPE, "", true, InvalidTypes.EMPTY, " Test type is blank and is required when the record type is set to Test.");
    			}
    			if (tecRecord.getSubject() == null || tecRecord.getSubject().equals("")){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.SUBJECT, "", true, InvalidTypes.EMPTY, " Subject is blank and is required when the record type is set to Test.");
    			}
    		}else if (recordType.equalsIgnoreCase("Clear")){
    			if (tecRecord.getTestType() == null || tecRecord.getTestType().equals("")){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.TEST_TYPE, "", true, InvalidTypes.EMPTY, " Test type is blank and is required when the record type is set to Clear.");
    			}
    			if (tecRecord.getSubject() == null || tecRecord.getSubject().equals("")){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.SUBJECT, "", true, InvalidTypes.EMPTY, " Subject is blank and is required when the record type is set to Clear.");
    			}
    		}
    	}
		
	}
    
    private Restriction getTecRestriction(UserDetailImpl userDetails){
    	//TEC follows the same permissions as upload enrollment so using the same restrictions
    	return getEnrollmentRestriction(userDetails);
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Map<String, FieldSpecification> getFieldSpecificationRecordMap(String uploadTypeCode, String recordType) {
    	Category uploadCategory = categoryService.selectByCategoryCodeAndType(uploadTypeCode, recordType);
        Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();
        if(uploadCategory != null) {
            List<FieldSpecification> fieldSpecifications = fieldSpecificationDao.selectByFieldCodeAndTypeCode(uploadCategory.getId());
            if (CollectionUtils.isNotEmpty(fieldSpecifications)) {
                for (FieldSpecification fieldSpecification : fieldSpecifications) {
                	if(fieldSpecification.getMappedName() != null){
                    	fieldSpecificationMap.put(fieldSpecification.getMappedName().toLowerCase(), fieldSpecification);
                	}else{
                		throw new RuntimeException("Mapped name is missing for field specification - " + fieldSpecification.getFieldName() + ", id - " + fieldSpecification.getId());
                	}
                }
            }
        }
        
        return fieldSpecificationMap;
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Map<String, FieldSpecification> getFieldSpecificationRecordMapForKids(String uploadTypeCode, String recordType) {
    	Category uploadCategory = categoryService.selectByCategoryCodeAndType(uploadTypeCode, recordType);
        Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();
        if(uploadCategory != null) {
            List<FieldSpecification> fieldSpecifications = fieldSpecificationDao.selectByFieldCodeAndTypeCodeForKids(uploadCategory.getId());
            if (CollectionUtils.isNotEmpty(fieldSpecifications)) {
                for (FieldSpecification fieldSpecification : fieldSpecifications) {
                	fieldSpecificationMap.put(fieldSpecification.getMappedName(), fieldSpecification);
                }
            }
        }
        
        return fieldSpecificationMap;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private Map<String,Object> uploadIncludeExcludeStudents(UploadFile uploadFile, Map<String,Object> uploadMV,
            Errors errors, ContractingOrganizationTree contractingOrganizationTree)
            throws IOException, ServiceException {
    	// Initialize the counts
        int recordsCreatedCount = 0;
        int recordsUpdatedCount = 0;
        int recordsRejectedCount = 0;
        
        List<OperationalTestWindowStudentRecord> operationalTestWindowStudentRecords = new ArrayList<OperationalTestWindowStudentRecord>();
        LOGGER.debug("UploadSpecification" + uploadSpecification);

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        InputStream inputStream = new FileInputStream(uploadFile.getFile());
    	String fileCharset = detectCharset(inputStream);
    	CSVReader reader = new CSVReader(new InputStreamReader(inputStream, fileCharset)); 
    	
    	// Set the Response type from parser
        AartColumnMappingStrategy<OperationalTestWindowStudentRecord>
        columnReadingStrategy = new AartColumnMappingStrategy<OperationalTestWindowStudentRecord>();
        columnReadingStrategy.setType(OperationalTestWindowStudentRecord.class);
        
        // Prepare parser with field specification from databse
        columnReadingStrategy.setColumnMapping(uploadSpecification.getIncludeExcludeStudentColumnAttributeMap());
        AartCsvToBean<OperationalTestWindowStudentRecord> operationalTestWindowStudentParser = new AartCsvToBean<OperationalTestWindowStudentRecord>();
        operationalTestWindowStudentParser.setFieldSpecificationMap(this.getFieldSpecificationMap(includeExcludeRecordType));
        try {
        	// Parse the uploaded file based on the specification provided
            operationalTestWindowStudentRecords = operationalTestWindowStudentParser.aartParse(columnReadingStrategy, reader);
            
        } catch (AartParseException e) {
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                    ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
            uploadMV.put("inValidDetail", invalidDetail);
            //the reason for failure cannot be captured.
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        } catch (InvocationTargetException e) {
            throw new IOException(e);
        } catch (InstantiationException e) {
            throw new IOException(e);
        } catch (IntrospectionException e) {
            throw new IOException(e);
        } finally {
        	inputStream.close();
        }
        
        // Parsing completed and input available for include/exclude, prepare List of state student identifiers
        List<String> studentStateIds = new ArrayList<String>();
        for(OperationalTestWindowStudentRecord operationalTestWindowStudentRecord: operationalTestWindowStudentRecords){
        	// Initilizae student to false and then check condition and activate if passed checks.
        	operationalTestWindowStudentRecord.getOperationalTestWindowStudent().setActiveFlag(false);
        	if(StringUtils.isNotBlank(operationalTestWindowStudentRecord.getStateStudentIdentifier())){
        		studentStateIds.add(operationalTestWindowStudentRecord.getStateStudentIdentifier());
        	}
        }
        
        if(!studentStateIds.isEmpty()){
        	// Find the student's id by state student identifiers.
        	for(OperationalTestWindowStudentRecord operationalTestWindowStudentRecord: operationalTestWindowStudentRecords){
        		// process records which are passed by parser and not rejected
        		if (!operationalTestWindowStudentRecord.isDoReject()) {
        			OperationalTestWindowStudent operationalTestWindowStudent = operationalTestWindowStudentRecord.getOperationalTestWindowStudent();
                	if(StringUtils.isNotBlank(operationalTestWindowStudentRecord.getStateStudentIdentifier())){
                		String schoolIdentifier = operationalTestWindowStudentRecord.getAttendanceSchoolProgramIdentifier();
                		List<Organization> schools = organizationService.getByDisplayIdentifier(schoolIdentifier, userDetails.getUser());
                		if(schools != null && !schools.isEmpty()){
                			Organization school = schools.get(0);
                			StudentExample example = new StudentExample();
                			String stateStudentIdentifier = operationalTestWindowStudentRecord.getStateStudentIdentifier();
                            example.createCriteria().andStatestudentidentifierEqualTo(stateStudentIdentifier)
                            .andAttendanceSchoolIdEqualTo(school.getId());
                            List<Student> students = studentService.getByCriteria(example);
                            if(students != null &&  !students.isEmpty()){
                            	Student student = students.get(0);
                        		if(operationalTestWindowStudentRecord.getStateStudentIdentifier().equalsIgnoreCase(student.getStateStudentIdentifier())){
                        			
                        			// Initialize operational test window student information available
                        			operationalTestWindowStudent.setStudentId(student.getId());
                        			operationalTestWindowStudent.setExclude(operationalTestWindowStudentRecord.getExclude().equals("E"));
                        			operationalTestWindowStudent.setOperationalTestWindowId(uploadFile.getOperationalWindowId());
                        			operationalTestWindowStudent.setCreatedDate(new Date());
                        			
                        			// Fetch remaining information from roster and validate.
                        			String attendanceSchoolIdentifier =  operationalTestWindowStudentRecord.getAttendanceSchoolProgramIdentifier();
                        			String subjectAbbreviatedName = operationalTestWindowStudentRecord.getSubject();
                        			String courseAbbreviatedName = operationalTestWindowStudentRecord.getCourse();
                        			
                        			// Validate students by cross checking with roster with subject & course.
                        			Roster studentRoster = rosterService.getRostersByStudentEnrollInformation(student.getId(), attendanceSchoolIdentifier, subjectAbbreviatedName,
                    					courseAbbreviatedName, currentSchoolYear);
                        			if(studentRoster != null) {
                        				operationalTestWindowStudent.setContentAreaId(studentRoster.getStateSubjectAreaId());
                            			operationalTestWindowStudent.setCourseId(studentRoster.getStateCoursesId());
                            			// Mark this student as valid if found in roster enrollment
                            			operationalTestWindowStudent.setActiveFlag(true);
                        			} else {
                        				// Mark this student as invalid if not found in roster enrollment
                        				operationalTestWindowStudent.setActiveFlag(false);
                        				String subject = StringUtils.isNotBlank(operationalTestWindowStudentRecord.getSubject()) ? operationalTestWindowStudentRecord.getSubject() : "";
                        				String course = StringUtils.isNotBlank(operationalTestWindowStudentRecord.getCourse()) ? operationalTestWindowStudentRecord.getCourse() : "";
                        				InValidDetail inValidDetail = new InValidDetail("Roster enrollment", subject + "-" + course, true);
                        				operationalTestWindowStudentRecord.addInvalidDetail(inValidDetail);
                        				operationalTestWindowStudentRecord.setDoReject(true);
                        			}
                        		}
                            } else {
                            	operationalTestWindowStudent.setActiveFlag(false);
                    			InValidDetail inValidDetail = new InValidDetail("student", operationalTestWindowStudentRecord.getStateStudentIdentifier(), true);
                    			inValidDetail.setReason(" does not exists.");
                				operationalTestWindowStudentRecord.addInvalidDetail(inValidDetail);
                				operationalTestWindowStudentRecord.setDoReject(true);
                            }
                		} else {
                			operationalTestWindowStudent.setActiveFlag(false);
                			String schoolId = operationalTestWindowStudentRecord.getAttendanceSchoolProgramIdentifier();
                			InValidDetail inValidDetail = new InValidDetail("attendanceSchoolProgramIdentifier", schoolId, true);
                			inValidDetail.setReason(" does not exists or the user does not have permission to upload.");
            				operationalTestWindowStudentRecord.addInvalidDetail(inValidDetail);
            				operationalTestWindowStudentRecord.setDoReject(true);
                		}
                	} else {
                		// Mark this student as invalid if state student identifier is blank.
        				operationalTestWindowStudent.setActiveFlag(false);
        				// invalidDetails should be added by now
                	}
        		}
            }
        }
        
        // For the validated students get existing entries from operationaltestwindowstudent table.
        List<ValidateableRecord> inValidIncludeExcludeStudents = new ArrayList<ValidateableRecord>();
        List<OperationalTestWindowStudentRecord> createdIncludeExcludeStudents = new ArrayList<OperationalTestWindowStudentRecord>();
        
		if (CollectionUtils.isNotEmpty(operationalTestWindowStudentRecords)) {
			for (OperationalTestWindowStudentRecord operationalTestWindowStudentRecord : operationalTestWindowStudentRecords) {
				// if not rejected by validation then process
				if (!operationalTestWindowStudentRecord.isDoReject()) {
					// Only process if validated by roster enrollment check.
					if (operationalTestWindowStudentRecord.getOperationalTestWindowStudent().getActiveFlag()) {
						
						OperationalTestWindowStudent operationalTestWindowStudent = operationalTestWindowStudentRecord.getOperationalTestWindowStudent();
						OperationalTestWindowStudent existingOperationalTestWindowStudent = 
							operationalTestWindowStudentMapper.findByOperationalTestWindowStudent(operationalTestWindowStudent);
						
						if(existingOperationalTestWindowStudent != null) {
							// Update record if exists
							operationalTestWindowStudentMapper.updateSelective(operationalTestWindowStudent);
							operationalTestWindowStudentRecord.setCreated(false);
							recordsUpdatedCount++;
						} else {
							// Create if not exists
							operationalTestWindowStudentMapper.insert(operationalTestWindowStudent);
							operationalTestWindowStudentRecord.setCreated(true);
							recordsCreatedCount++;
						}
						createdIncludeExcludeStudents.add(operationalTestWindowStudentRecord);
						
					} else {
						// Add to invalid records
						recordsRejectedCount++;
						operationalTestWindowStudentRecord.setInValid(true);
						inValidIncludeExcludeStudents.add(operationalTestWindowStudentRecord);
					}
				} else {
					// Add to invalid records
					recordsRejectedCount++;
					operationalTestWindowStudentRecord.setInValid(true);
					inValidIncludeExcludeStudents.add(operationalTestWindowStudentRecord);
				}
			}

            if (CollectionUtils.isNotEmpty(inValidIncludeExcludeStudents)) 
            {   
            	if(inValidIncludeExcludeStudents.size() > 100 && recordsRejectedCount > 100)
            		uploadMV.put("inValidRecords", new ArrayList<ValidateableRecord>(inValidIncludeExcludeStudents.subList(0, 100)));            
            	else 
            		uploadMV.put("inValidRecords", inValidIncludeExcludeStudents);
            }
        }
		uploadMV.put("createdIncludeExcludeStudents", createdIncludeExcludeStudents);

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.put("uploadErrors", errors.getAllErrors());
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());
        
        uploadMV.put("recordsSkippedCount", 0);
        uploadMV.put("recordsCreatedCount", recordsCreatedCount);
        uploadMV.put("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.put("recordsRejectedCount", recordsRejectedCount);
        uploadMV.put("totalRecordCount", operationalTestWindowStudentRecords.size());

        return uploadMV;
    }
    
    @Override
    public List<FieldSpecification> selectPNPColumnsBySettingsForAssessmentProgramAndOrganization(
    		Long assessmentProgramId, Long organizationId) {
    	return fieldSpecificationDao.selectPNPColumnsBySettingsForAssessmentProgramAndOrganization(assessmentProgramId, organizationId);
    }
}

