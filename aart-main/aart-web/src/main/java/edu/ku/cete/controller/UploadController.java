/**
 *
 */
package edu.ku.cete.controller;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.com.bytecode.opencsv.CSVReader;
import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.OrganizationTree;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.enrollment.TestRecord;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.PersonalNeedsProfileRecord;
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
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.model.validation.FileUploadValidator;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UploadService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.student.FirstContactService;
import edu.ku.cete.util.AartColumnMappingStrategy;
import edu.ku.cete.util.AartCsvToBean;
import edu.ku.cete.util.AartCsvVerticalParser;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.AartResource;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StringUtil;
import edu.ku.cete.util.UploadSpecification;
/**
 * @author m802r921
 *@deprecated
 */
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

    /**
     * LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    /**
     * the specification that has field limits, allowable values etc.
     */
    @Autowired
    private UploadSpecification uploadSpecification;
    @Autowired
    private RestrictedResourceConfiguration restrictedResourceConfiguration;
    @Autowired
    private ResourceRestrictionService resourceRestrictionService;
    /**
     * back end service for enrollment.
     */
    @Autowired
    private EnrollmentService enrollmentService;
    /**
     * Metadata table.
     */
    @Autowired
    private CategoryDao categoryDao;

    /**
     * meta data for record types.
     */
    private Map<Long, Category> recordTypeIdMap = new HashMap<Long, Category>();
    /**
     * Field specification dao.
     */
    @Autowired
    private FieldSpecificationDao fieldSpecificationDao;
    
    @Autowired
    private AssessmentProgramDao assessmentProgramDao; 

    /**
     * for getting the code for record type.
     */
    @Autowired
    private CategoryTypeDao categoryTypeDao;

    @Autowired
    private RosterService rosterService;

    @Autowired
    private PermissionUtil permissionUtil;

    @Autowired
    private UserService userService;
    @Autowired
    private OrganizationService organizationService;

    /**
     * SCRS Record type.
     */
    private Category scrsRecordType;

    /**
     * Enrollment Record type.
     */
    private Category enrollmentRecordType;

    /**
     * Test record type.
     */
    private Category testRecordType;

    /**
     * User record type.
     */
    private Category userRecordType;
    
    /**
     * User record type.
     */
    private Category pdTrainingResultsRecordType;

    /**
     * PersonalNeedsProfileRecord record type.
     */
    private Category personalNeedsProfileRecordRecordType;

	/**
	 * firstContactRecordType.
	 */
	private Category firstContactRecordType;
	
	/**
	 * tecRecordType
	 */
	private Category tecRecordType;
	
	/**
     * studentService.
     */
    @Autowired
	private StudentService studentService;
    /**
	 * fileCharset
	 */
	@Value("${default.file.encoding.charset}")
	private Boolean defaultFileCharset;
	
    @Value("${user.organization.DLM}")
    private String dlmOrgName;

	/**
	 * firstContactService.
	 */
	@Autowired
	private FirstContactService firstContactService;
	/**
	 * educatorIdWarningMessage.
	 */
	@Value("${educatorid.warning.message}")
	private String educatorIdWarningMessage;
	/**
	 * studentIdWarningMessage.
	 */
	@Value("${studentid.warning.message}")
	private String studentIdWarningMessage;
    /**
     * @return {@link UploadSpecification}
     */
    public final UploadSpecification getUploadSpecification() {
        return uploadSpecification;
    }

    /**
     * @param uploadSpec {@link UploadSpecification}
     */
    public final void setUploadSpecification(UploadSpecification uploadSpec) {
        this.uploadSpecification = uploadSpec;
    }

    /**
     * This will create a reference to ContentArea Model.
     *
     * @return ContentArea {@link ContentArea}
     */
    @ModelAttribute("uploadFile")
    public final UploadFile getCommandObject() {
        return new UploadFile();
    }

    /**
     * set the metadata.
     */
    @PostConstruct
    public final void setMetaData() {
        setUploadSpecification();
        setRecordTypes();
    }

    /**
     * This will bind a validator to gradeCourse.
     *
     * @param binder
     *            {@link WebDataBinder}
     */
    @InitBinder()
    public final void initBinder(final WebDataBinder binder) {
        if (binder.getTarget() instanceof UploadFile) {
            binder.setValidator(new FileUploadValidator());
        }
    }

    /**
     * set fields specification.
     */
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

    /**
     * Sets what record type is scrs and what is enrollment and more.
     */
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
        this.pdTrainingResultsRecordType = getRecordType(uploadSpecification.getPdTrainingUploadType());
        this.orgRecordType =  getRecordType(uploadSpecification.getOrgRecordType());
        this.personalNeedsProfileRecordRecordType =  getRecordType(
        		uploadSpecification.getPersonalNeedsProfileRecordType());
        this.firstContactRecordType =  getRecordType(uploadSpecification.getFirstContactRecordType());
        this.tecRecordType = getRecordType(uploadSpecification.getTecRecordType());
    }

    /**
     * This should not be empty.Failure to deploy is the expected behavior.In this case it will throw null pointer.
     * @param recordTypeCode {@link String}
     * @return {@link Category}
     */
    private Category getRecordType(String recordTypeCode) {
        for (Category recordType:recordTypeIdMap.values()) {
            if (recordType.getCategoryCode().equalsIgnoreCase(recordTypeCode)) {
                return recordType;
            }
        }
        return null;
    }

    /**
     * Set the upload specification on to the utility methods.
     */
    private void setUploadSpecification() {
        StringUtil.setUploadSpecification(uploadSpecification);
        NumericUtil.setUploadSpecification(uploadSpecification);
    }

    /**
     * @param userDetails {@link UserDetailImpl}
     * @return {@link Restriction}
     */
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
    
    /**
     * @param userDetails {@link UserDetailImpl}
     * @return {@link Restriction}
     */
    private Restriction getTecRestriction(UserDetailImpl userDetails){
    	//TEC follows the same permissions as upload enrollment so using the same restrictions
    	return getEnrollmentRestriction(userDetails);
    }
    /**
     * @param model {@link Model}
     * @return {@link String}
     */
    @RequestMapping(method = RequestMethod.GET)
    public final String getUploadForm(Model model) {
        model.addAttribute(new UploadFile());
        model.addAttribute("current", "dataUpload");
        
        List<Category> category = new ArrayList<Category>(recordTypeIdMap.values());
        Collections.sort(category, new Comparator<Category>() {

            public int compare(Category o1, Category o2) {
                return (o1.getCategoryName()).compareTo(o2.getCategoryName());
            }
        });        
        Map<Long, Category> recordTypeIdLinkedHashMap = new LinkedHashMap<Long, Category>();
        for (Category recordType:category) {
        	recordTypeIdLinkedHashMap.put(recordType.getId(), recordType);
        }  
        
        model.addAttribute("recordTypes", recordTypeIdLinkedHashMap.values());
        return AartResource.UPLOAD_FOLDER
                + java.io.File.separator + AartResource.UPLOAD_JSP;
    }

    /**
     * @param uploadFile {@link UploadFile}
     * @param uploadMV {@link ModelAndView}
     * @param errors {@link Errors}
     * @param contractingOrganizationTree {@link ContractingOrganizationTree}
     * @return {@link ModelAndView}
     * @throws IOException IOException
     * @throws ServiceException ServiceException
     */
    private ModelAndView uploadEnrollment(UploadFile uploadFile, ModelAndView uploadMV,
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
                uploadMV.addObject("inValidDetail", invalidDetail);
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
            uploadMV.addObject("inValidDetail", invalidDetail);
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

            if (CollectionUtils.isNotEmpty(inValidEnrollments) && inValidEnrollments.size() > 100) {            	
            	uploadMV.addObject("inValidRecords", new ArrayList<ValidateableRecord>(inValidEnrollments.subList(0, 100)));            
            } else {
            	uploadMV.addObject("inValidRecords", inValidEnrollments);
            }
        }

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.addObject("uploadErrors", errors.getAllErrors());
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());
        
        //uploadMV.addObject("createdEnrollments", createdEnrollments);
        uploadMV.addObject("recordsSkippedCount", 0);
        uploadMV.addObject("recordsCreatedCount", recordsCreatedCount);
        uploadMV.addObject("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.addObject("recordsRejectedCount", recordsRejectedCount);
        uploadMV.addObject("totalRecordCount", enrollmentRecords.size());

        return uploadMV;
    }

	/**
     * TODO Try to make this more generic.there is only one line that is different.
     * @param uploadFile {@link UploadFile}
     * @param uploadMV {@link ModelAndView}
     * @param errors {@link BindingResult}
     * @param contractingOrganizationTree {@link Organization}
     * @return {@link ModelAndView}
     * @throws IOException IOException
     * @throws ServiceException ServiceException
     */
    private ModelAndView uploadTest(UploadFile uploadFile, ModelAndView uploadMV, Errors errors,
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
                uploadMV.addObject("inValidDetail", invalidDetail);
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
            uploadMV.addObject("inValidDetail", invalidDetail);
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
                    testRecord.getEnrollment().setSourceType(SourceTypeEnum.UPLOAD.getCode());
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
                uploadMV.addObject("inValidRecords", inValidTestRecords);
            }
        }
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.addObject("uploadErrors", errors.getAllErrors());
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());
        uploadMV.addObject("recordsSkippedCount", 0);
        uploadMV.addObject("recordsCreatedCount", recordsCreatedCount);
        uploadMV.addObject("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.addObject("recordsRejectedCount", recordsRejectedCount);
        uploadMV.addObject("totalRecordCount", testRecords.size());

        return uploadMV;
    }

    /**
     * TODO Try to make this more generic.there is only one line that is different.
     * @param uploadFile {@link UploadFile}
     * @param uploadMV {@link ModelAndView}
     * @param errors {@link Errors}
     * @param contractingOrganizationTree {@link OrganizationTree}
     * @return {@link ModelAndView}
     * @throws IOException IOException
     * @throws ServiceException ServiceException
     */
    private ModelAndView uploadRoster(UploadFile uploadFile, ModelAndView uploadMV,
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
                uploadMV.addObject("inValidDetail", invalidDetail);
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
            uploadMV.addObject("inValidDetail", invalidDetail);
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
            if (CollectionUtils.isNotEmpty(inValidScrsRecords) && inValidScrsRecords.size() > 100) {            	
            	uploadMV.addObject("inValidRecords", new ArrayList<ValidateableRecord>(inValidScrsRecords.subList(0, 100)));            		            	
            } else {
            	uploadMV.addObject("inValidRecords", inValidScrsRecords);
            }
        }
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.addObject("uploadErrors", errors.getAllErrors());
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
        uploadMV.addObject("recordsSkippedCount", 0);
        uploadMV.addObject("recordsCreatedCount", recordsCreatedCount);
        uploadMV.addObject("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.addObject("recordsRejectedCount", recordsRejectedCount);
        uploadMV.addObject("recordsRemovedCount", recordsRemovedCount);
        uploadMV.addObject("totalRecordCount", rosterRecords.size());
        return uploadMV;
    }

    /**
     * method for uploading personal needs profile
     * @param uploadFile
     * @param uploadMV
     * @param errors
     * @param contractingOrganizationTree
     * @return
     * @throws IOException IOException
     */
    private ModelAndView uploadPersonalNeedsProfile(
    		UploadFile uploadFile,
			ModelAndView uploadMV, Errors errors,
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
        	uploadMV.addObject("inValidDetail", invalidDetail);
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
        	uploadMV.addObject("uploadErrors", errors.getAllErrors());
        	return uploadMV;
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());

        uploadMV.addObject("recordsSkippedCount", 0);
        uploadMV.addObject("recordsCreatedCount", recordsCreatedCount);
        uploadMV.addObject("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.addObject("inValidRecords", inValidPersonalNeedsProfileRecords);
        uploadMV.addObject("recordsRejectedCount", recordsRejectedCount);
        uploadMV.addObject("totalRecordCount", personalNeedsProfileRecords.size());

        return uploadMV;
	}
    /**
     * This method takes the uploaded file, parses it, verifies the users, and reports to the user any problems.
     * @param uploadFile {@link UploadFile}
     * @param result {@link BindingResult}
     * @param mav (@link ModelAndView}
     * @param organizationTree {@link OrganizationTree}
     * @param currentContext {@link Organization}
     * @param currentUser {@link UserDetailImpl}
     */
    public final void uploadUsersFromFile(UploadFile uploadFile, Errors result, ModelAndView mav, Organization currentContext,
    		ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser) {
        LOGGER.trace("Entering the uploadUserFromFile method.");

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
                
                LOGGER.debug("Received {} users to upload to the system.", uploadedUsers.size());

                Long parentOrgId = uploadFile.getSelectedOrgId();
                
                //This is clean.
                int createdUsers = userService.checkUploadedUsers(uploadedUsers, invalidUsers, currentContext,
                		contractingOrganizationTree, currentUser, successUsers, parentOrgId);
                if(CollectionUtils.isNotEmpty(invalidUsers) && invalidUsers.size() > 100) {
                	mav.addObject("inValidRecords",new  ArrayList<UploadedUser>(invalidUsers.subList(0, 100)));
                } else { 
                	mav.addObject("inValidRecords", invalidUsers);
                }
                /*mav.addObject("successUsers", successUsers);*/
                recordsCreatedCount = createdUsers;
                recordsRejectedCount = invalidUsers.size();
                totalRecordCount = uploadedUsers.size();
                
                LOGGER.debug("Rejected {} users from the uploaded file.", invalidUsers.size());
                inputStream.close();
            } else {
                LOGGER.debug("File uploaded is empty or doesn't exist. Returning error message back to user.");
                mav.addObject("invalidUploadedFile", true);
            }
        } catch (IOException e) {
            LOGGER.error("Caught IOException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_READ_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (AartParseException e) {
            LOGGER.error("Caught AartParseException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                    ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (IllegalAccessException e) {
            LOGGER.error("Caught IllegalAccessException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.ACCESS_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (InvocationTargetException e) {
            LOGGER.error("Caught InvocationTargetException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (InstantiationException e) {
            LOGGER.error("Caught InstantiationException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (IntrospectionException e) {
            LOGGER.error("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (ServiceException e) {
            LOGGER.error("Caught ServiceException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } 
        if(CollectionUtils.isNotEmpty(invalidUsers) && invalidUsers.size() > 100) {        	
        	mav.addObject("inValidRecords", new ArrayList<UploadedUser>(invalidUsers.subList(0, 100)));    
        } else {
        	mav.addObject("inValidRecords", invalidUsers);
        }
        mav.addObject("recordsSkippedCount", 0);
        mav.addObject("recordsCreatedCount", recordsCreatedCount);
        mav.addObject("recordsUpdatedCount", recordsUpdatedCount);
        mav.addObject("recordsRejectedCount", recordsRejectedCount);
        mav.addObject("totalRecordCount", totalRecordCount);

        LOGGER.trace("Leaving the uploadUserFromFile method.");
    }
    
    /**
     * This method takes the uploaded file, parses it, verifies the users, and reports to the user any problems.
     * @param uploadFile {@link UploadFile}
     * @param result {@link BindingResult}
     * @param mav (@link ModelAndView}
     * @param organizationTree {@link OrganizationTree}
     * @param currentContext {@link Organization}
     * @param currentUser {@link UserDetailImpl}
     */
    public final void uploadPDResultsFromFile(UploadFile uploadFile, Errors result, ModelAndView mav, Organization currentContext,
    		ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser) {
        LOGGER.debug("Entering the uploadPDResultsFromFile method.");

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
                columnReadingStrategy.setColumnMapping(uploadSpecification.getUserColumnMap());
                
                AartCsvToBean<User> aartCsvToBeanParser = new AartCsvToBean<User>();
                aartCsvToBeanParser.setFieldSpecificationMap(this.getFieldSpecificationMap(pdTrainingResultsRecordType));
                uploadedUsers = aartCsvToBeanParser.aartParse(columnReadingStrategy, reader);
                
                LOGGER.debug("Received {} users to upload to the system.", uploadedUsers.size());

                Long parentOrgId = uploadFile.getSelectedOrgId();
                
                //This is clean.
                int createdUsers = userService.checkUploadedPDTrainingResults(uploadedUsers, invalidUsers, currentContext,
                		contractingOrganizationTree, currentUser, successUsers, parentOrgId);
                if(CollectionUtils.isNotEmpty(invalidUsers) && invalidUsers.size() > 100) {
                	mav.addObject("inValidRecords",new  ArrayList<User>(invalidUsers.subList(0, 100)));
                } else { 
                	mav.addObject("inValidRecords", invalidUsers);
                }
                /*mav.addObject("successUsers", successUsers);*/
                recordsCreatedCount = createdUsers;
                recordsRejectedCount = invalidUsers.size();
                totalRecordCount = uploadedUsers.size();
                
                LOGGER.debug("Rejected {} users from the uploaded file.", invalidUsers.size());
                inputStream.close();
            } else {
                LOGGER.debug("File uploaded is empty or doesn't exist. Returning error message back to user.");
                mav.addObject("invalidUploadedFile", true);
            }
        } catch (IOException e) {
            LOGGER.error("Caught IOException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_READ_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (AartParseException e) {
            LOGGER.error("Caught AartParseException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                    ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (IllegalAccessException e) {
            LOGGER.error("Caught IllegalAccessException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.ACCESS_ISSUE,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (InvocationTargetException e) {
            LOGGER.error("Caught InvocationTargetException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (InstantiationException e) {
            LOGGER.error("Caught InstantiationException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (IntrospectionException e) {
            LOGGER.error("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (ServiceException e) {
            LOGGER.error("Caught ServiceException in uploadUsersFromFile. Stacktrace: {}", e);
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.SYSTEM_ERROR,
                    ParsingConstants.BLANK, true, InvalidTypes.ERROR);
            mav.addObject("inValidDetail", invalidDetail);
        } 
        if(CollectionUtils.isNotEmpty(invalidUsers) && invalidUsers.size() > 100) {        	
        	mav.addObject("inValidRecords", new ArrayList<User>(invalidUsers.subList(0, 100)));    
        } else {
        	mav.addObject("inValidRecords", invalidUsers);
        }
        mav.addObject("recordsSkippedCount", 0);
        mav.addObject("recordsCreatedCount", 0);
        mav.addObject("recordsUpdatedCount", recordsCreatedCount);
        mav.addObject("recordsRejectedCount", recordsRejectedCount);
        mav.addObject("totalRecordCount", totalRecordCount);

        LOGGER.debug("Leaving the uploadPDResultsFromFile method.");
    }
    
    /**
     * TODO rename to upload.
     * @param uploadFile {@link UploadFile}
     * @param errors {@link BindingResult}
     * @return {@link String}
     * @throws IOException IOException
     * @throws ServiceException ServiceException
     */
    @RequestMapping(method = RequestMethod.POST)
    public final ModelAndView create(
        UploadFile uploadFile, Errors errors, HttpServletRequest request) throws IOException, ServiceException {
        ModelAndView uploadMV = new ModelAndView(AartResource.UPLOAD_FOLDER
                + java.io.File.separator + AartResource.UPLOAD_JSP);
        Category selectedType = recordTypeIdMap.get(uploadFile.getSelectedRecordTypeId());
        UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();       
        long organizationId = 0;
        if (errors.hasErrors()) {
            uploadMV.addObject("recordTypes", recordTypeIdMap.values());
            if (uploadFile.getRosterUpload() == 1
            		|| selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getEnrollmentRecordType())
            		 || selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getUserRecordType())){
            	uploadMV.addObject("stateId", uploadFile.getStateId());
            	uploadMV.addObject("districtId", uploadFile.getDistrictId());
            	uploadMV.addObject("schoolId", uploadFile.getSchoolId());
            }
        } else if(uploadFile.getContinueOnWarning() == null && 
        		validateIdentifiers(uploadFile,uploadMV)) {
        	if (uploadFile.getRosterUpload() == 1 
        			|| selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getEnrollmentRecordType())
        			|| selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getUserRecordType())) {
            	uploadMV.addObject("stateId", uploadFile.getStateId());
            	uploadMV.addObject("districtId", uploadFile.getDistrictId());
            	uploadMV.addObject("schoolId", uploadFile.getSchoolId());
            }
        	uploadMV.addObject("uploadFile", uploadFile);
        	uploadMV.addObject("recordTypes", recordTypeIdMap.values());
        	uploadMV.addObject("showWarning", true);
        	
        	return uploadMV;
        } else {
        	           
            Organization currentContext = organizationService.get(user.getUser().getCurrentOrganizationId());
            ContractingOrganizationTree
            contractingOrganizationTree = organizationService.getTree(currentContext);

            List<GrantedAuthority> authorities = (List<GrantedAuthority>) user.getAuthorities();
            uploadMV.addObject("showWarning", null);
            uploadMV.addObject("uploadFile", uploadFile);

            uploadMV.addObject("uploadCompleted", true);
            uploadMV.addObject("recordTypes", recordTypeIdMap.values());
           
            if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getEnrollmentRecordType())
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
            		uploadMV.addObject("uploadErrors", errors.getAllErrors());
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
            		uploadMV.addObject("uploadErrors", errors.getAllErrors());
                	uploadMV.addObject("stateId", uploadFile.getStateId());
                	uploadMV.addObject("districtId", uploadFile.getDistrictId());
                	uploadMV.addObject("schoolId", uploadFile.getSchoolId());
            	} else {
            		//DE5842 removed this call so the whole contract tree is passed back
            		//we need to have the whole tree available to be able to find
            		//the district and attendance school to look up the educator
            		//contractingOrganizationTree = organizationService.getTree(org);
                	uploadMV.addObject("schoolName", org.getOrganizationName());
            		uploadMV = uploadRoster(uploadFile, uploadMV, errors,
                		contractingOrganizationTree,organizationId);
            		uploadMV.addObject("stateId", uploadFile.getStateId());
                	uploadMV.addObject("districtId", uploadFile.getDistrictId());
                	uploadMV.addObject("schoolId", uploadFile.getSchoolId());
            	}
            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getUserRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_USER_UPLOAD")) {

                uploadUsersFromFile(uploadFile, errors, uploadMV, currentContext,
                		contractingOrganizationTree, user);

        		uploadMV.addObject("stateId", uploadFile.getStateId());
            	uploadMV.addObject("districtId", uploadFile.getDistrictId());
            	uploadMV.addObject("schoolId", uploadFile.getSchoolId());
            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getPdTrainingUploadType())
                    && permissionUtil.hasPermission(authorities, "PD_TRAINING_RESULTS_UPLOADER")) {

                uploadPDResultsFromFile(uploadFile, errors, uploadMV, currentContext,
                		contractingOrganizationTree, user);

        		
            } else if (selectedType.getCategoryCode().equalsIgnoreCase(uploadSpecification.getOrgRecordType())
                    && permissionUtil.hasPermission(authorities, "PERM_ORG_UPLOAD")) {

                uploadOrganizationsFromFile(uploadFile, errors, uploadMV, currentContext,
                		contractingOrganizationTree);

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
            		uploadMV.addObject("uploadErrors", errors.getAllErrors());
            	} else {
            		contractingOrganizationTree = organizationService.getTree(org);
            		uploadMV = uploadTEC(uploadFile, uploadMV, errors, contractingOrganizationTree);
            	}

            }
        }

        return uploadMV;
    }
    

    /**
     * @param uploadFile
     * @return
     * @throws IOException
     */
    private Boolean validateIdentifiers(UploadFile uploadFile, ModelAndView uploadMV) throws IOException {
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
		
			if(this.userRecordType.getId() == recordType) {
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
					uploadMV.addObject("warningMessage", studentIdWarningMessage);
				}
				
				if( ssidColumnIndex > -1 && nextLine != null && columns.length > ssidColumnIndex && 
						NumberUtils.isNumber(columns[ssidColumnIndex]) &&  columns[ssidColumnIndex].length() == 9) {
					showWarning = true;
					uploadMV.addObject("warningMessage", studentIdWarningMessage);
				}
				
				if( eidColumnIndex > -1 && nextLine != null && columns.length > eidColumnIndex && 
						NumberUtils.isNumber(columns[eidColumnIndex]) &&  columns[eidColumnIndex].length() == 9) {
					showWarning = true;
					uploadMV.addObject("warningMessage", educatorIdWarningMessage);
				}								
			}
			
		} catch (IOException e) {
			 LOGGER.error("Error occured while processing file",  e);
		} finally {
			csvReader.close();
		}
		
    	return showWarning;
    }
    
	private ModelAndView uploadFirstContact(UploadFile uploadFile,
			ModelAndView uploadMV, Errors errors,
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
        	uploadMV.addObject("inValidDetail", invalidDetail);
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
                uploadMV.addObject("inValidRecords", inValidFirstContactRecords);
            }
        }
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
            }
            uploadMV.addObject("uploadErrors", errors.getAllErrors());
            return uploadMV;
        }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());

        uploadMV.addObject("recordsSkippedCount", recordsSkippedCount);
        uploadMV.addObject("recordsCreatedCount", recordsCreatedCount);
        uploadMV.addObject("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.addObject("recordsRejectedCount", recordsRejectedCount);
        uploadMV.addObject("totalRecordCount", firstContactRecords.size());

        return uploadMV;
    }


	/**
     * Organization record type.
     */
    private Category orgRecordType;


    @Autowired
    private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    @Autowired
    private UploadService uploadService; 

    /**
     * Processes the uploaded organization record file. Checking for consistency and adding organizations.
     * @param uploadFile {@link UploadFile}
     * @param result {@link Errors}
     * @param mav {@link ModelAndView}
     * @param currentUserOrg {@link Organization}
     * @param currentUserChildOrgs {@link Collection<Organization>}
     */
    public final void uploadOrganizationsFromFile(UploadFile uploadFile, Errors result, ModelAndView mav,
    		Organization currentUserOrg, ContractingOrganizationTree contractingOrganizationTree) {

    	List<UploadedOrganization> invalidOrgs = new ArrayList<UploadedOrganization>();    
    	mav.addObject("recordsSkippedCount", 0);
        mav.addObject("recordsCreatedCount", 0);
        mav.addObject("recordRejectedCount", 0);
        mav.addObject("totalRecordsProcessed", 0);

        try {
            if (uploadFile.getFile() != null) {
                            
                LOGGER.debug("orgRecordType :" + orgRecordType.getId());
                Map<String, FieldSpecification> fieldSpecificationMap = getFieldSpecificationMap(orgRecordType);    
                         
                Map<String, Object> resultObj = uploadService.bulkUploadOrganization(uploadFile,
                		fieldSpecificationMap, currentUserOrg, contractingOrganizationTree, invalidOrgs, 
                		detectCharset(new FileInputStream(uploadFile.getFile())), defaultFileCharset);       

               /* mav.addObject("createdOrgs", resultObj.get("createdOrgs"));*/
                mav.addObject("recordsCreatedCount", resultObj.get("recordsCreatedCount"));
                mav.addObject("recordsRejectedCount", resultObj.get("recordsRejectedCount"));
                mav.addObject("recordsUpdatedCount", resultObj.get("recordsUpdatedCount"));
                mav.addObject("totalRecordCount", resultObj.get("totalRecordCount"));
                if(CollectionUtils.isNotEmpty(invalidOrgs) && invalidOrgs.size() > 100) {                	
                	mav.addObject("inValidRecords", new ArrayList<UploadedOrganization>(invalidOrgs.subList(0, 100)));                		                 	
                } else {
                	mav.addObject("inValidRecords", invalidOrgs);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Caught IOException in uploadOranizationsFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (AartParseException e) {
            LOGGER.error("Caught AartParseException in uploadOrganizationsFromFile. StackTrace: {}" + e.getStackTrace());
            InValidDetail invalidDetail = InValidDetail.getInstance(
                    ParsingConstants.BLANK + FieldName.FILE_FORMAT,
                    ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT);
            mav.addObject("inValidDetail", invalidDetail);
        } catch (IllegalAccessException e) {
            LOGGER.error("Caught IllegalAccessException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (InvocationTargetException e) {
            LOGGER.error("Caught InvocationTargetException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (InstantiationException e) {
            LOGGER.error("Caught InstantiationException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (IntrospectionException e) {
            LOGGER.error("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}" +  e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        }
    }
    
    /**
     * @param uploadFile {@link UploadFile}
     * @param uploadMV {@link ModelAndView}
     * @param errors {@link Errors}
     * @param contractingOrganizationTree {@link ContractingOrganizationTree}
     * @return {@link ModelAndView}
     * @throws IOException IOException
     * @throws ServiceException ServiceException
     */
    private ModelAndView uploadTEC(UploadFile uploadFile, ModelAndView uploadMV,
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
                uploadMV.addObject("inValidDetail", invalidDetail);
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
            uploadMV.addObject("inValidDetail", invalidDetail);
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
            if(CollectionUtils.isNotEmpty(inValidTecs) && inValidTecs.size() > 100) {            	
            	uploadMV.addObject("inValidRecords", new ArrayList<ValidateableRecord>(inValidTecs.subList(0, 100)));            		            
            } else {
            	uploadMV.addObject("inValidRecords", inValidTecs);
            }
        }

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOGGER.error("Error: " +  error.getCode() +  " - " + error.getDefaultMessage());
                }
            uploadMV.addObject("uploadErrors", errors.getAllErrors());
            }
        LOGGER.debug("Uploaded: " + uploadFile.getFile().getName());
        
        /*uploadMV.addObject("createdTecs", createdTecs);*/
        uploadMV.addObject("recordsSkippedCount", 0);
        uploadMV.addObject("recordsCreatedCount", recordsCreatedCount);
        uploadMV.addObject("recordsUpdatedCount", recordsUpdatedCount);
        uploadMV.addObject("recordsRejectedCount", recordsRejectedCount);
        uploadMV.addObject("totalRecordCount", tecRecords.size());

        return uploadMV;
    }


    private void checkTecRecordDependencies(List<TecRecord> tecRecords) {

    	for (TecRecord tecRecord : tecRecords){
    		String recordType = tecRecord.getRecordType();
    		if (recordType ==null || recordType.isEmpty())
    			continue;
    		else if (recordType.equalsIgnoreCase("Exit")){
    			
    			if (tecRecord.getExitReason() == 0 ){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.EXIT_REASON, "", true, InvalidTypes.EMPTY, " is blank and is required when the record type is set to Exit.");
    			}
    			
    			if (tecRecord.getExitDate() == null ){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.EXIT_DATE, "", true, InvalidTypes.EMPTY, " is blank and is required when the record type is set to Exit.");
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
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.TEST_TYPE, "", true, InvalidTypes.EMPTY, "  is blank and is required when the record type is set to Test.");
    			}
    			if (tecRecord.getSubject() == null || tecRecord.getSubject().equals("")){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.SUBJECT, "", true, InvalidTypes.EMPTY, "  is blank and is required when the record type is set to Test.");
    			}
    		}else if (recordType.equalsIgnoreCase("Clear")){
    			if (tecRecord.getTestType() == null || tecRecord.getTestType().equals("")){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.TEST_TYPE, "", true, InvalidTypes.EMPTY, "  is blank and is required when the record type is set to Clear.");
    			}
    			if (tecRecord.getSubject() == null || tecRecord.getSubject().equals("")){
    				tecRecord.addInvalidField(ParsingConstants.BLANK+FieldName.SUBJECT, "", true, InvalidTypes.EMPTY, " is blank and is required when the record type is set to Clear.");
    			}
    		}
    	}
		
	}

	/**
     * TODO move it to a EncodingDetector
     * Detects encoding of the given file by reading from the input stream.
     * @param fis
     * @return
     * @throws IOException
     */
    public String detectCharset(InputStream fis) throws IOException {
/*    	if(defaultFileCharset) {
    		return "UTF-8";
    	}
    	
    	byte[] buf = new byte[4096];
		int nread;
	    //java.io.FileInputStream fis = new java.io.FileInputStream(csvFileName);
	    UniversalDetector detector = new UniversalDetector(null);
	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
	      detector.handleData(buf, 0, nread);
	    }
	    detector.dataEnd();
	    String charset = detector.getDetectedCharset();
	    if (charset != null) {
	      LOGGER.debug("Detected encoding = " + charset);
	    } else {
	    	LOGGER.debug("No encoding detected.");
	    }
	    detector.reset();
	    if(charset == null || charset.equals("")) {
	    	charset = "UTF-8";
	    }
	    return charset;*/
	    
	    return java.nio.charset.Charset.defaultCharset().name();
    }
    
}