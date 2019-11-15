package edu.ku.cete.controller;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import java.util.Collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.ListIterator;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import org.owasp.esapi.StringUtilities;

import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.codehaus.stax2.ri.typed.ValueEncoderFactory;
import org.mozilla.universalchardet.UniversalDetector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.batch.InterimPredictiveReportProcessStarter;
import edu.ku.cete.batch.ksde.BatchKSDEDataProcessStarter;
import edu.ku.cete.batch.ksde.BatchKSDEGetDataStarter;
import edu.ku.cete.batch.reportprocess.BatchReportProcessStarter;
import edu.ku.cete.domain.GrfStateApproveAudit;

import edu.ku.cete.domain.PermissionUploadFile;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.ReportProcess;
import edu.ku.cete.domain.common.AppConfiguration;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;

import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.report.ReportAssessmentProgram;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;

import edu.ku.cete.domain.validation.FieldName;

import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadInfo;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.report.domain.StateSpecificFile;

import edu.ku.cete.service.DataReportService;

import edu.ku.cete.service.AuthoritiesService;

import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;

import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;

import edu.ku.cete.service.PermissionUploadFileService;


import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.UploadFileService;
import edu.ku.cete.service.exception.NoAccessToResourceException;
import edu.ku.cete.service.report.AssessmentTopicService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.CombinedLevelMapService;
import edu.ku.cete.service.report.ExcludedItemsService;
import edu.ku.cete.service.report.ExternalOrganizationResultsService;
import edu.ku.cete.service.report.ExternalReportImportService;
import edu.ku.cete.service.report.ExternalStudentReportResultsProcessService;
import edu.ku.cete.service.report.GrfReportWriterService;
import edu.ku.cete.service.report.LevelDescriptionService;
import edu.ku.cete.service.report.OrganizationPrctByAssessmentTopicService;
import edu.ku.cete.service.report.QuestionInformationService;
import edu.ku.cete.service.report.RawToScaleScoresService;
import edu.ku.cete.service.report.ReportService;
import edu.ku.cete.service.report.StudentPrctByAssessmentTopicService;
import edu.ku.cete.service.report.SubScoresMissingStagesService;
import edu.ku.cete.service.report.SubscoreFrameworkService;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;
import edu.ku.cete.service.report.SubscoresDescriptionService;
import edu.ku.cete.service.report.TestCutScoresService;
import edu.ku.cete.util.CommonConstants;

import edu.ku.cete.util.DataReportTypeEnum;

import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.json.BatchReportHistoryJson;
import edu.ku.cete.web.AssessmentProgramDto;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;
import edu.ku.cete.web.ReportAccessDTO;

@Controller
public class ReportDataManagementController extends BaseController {
	
	@Autowired
	BatchReportProcessStarter batchReportProcessStarter;
	
	@Autowired
	BatchKSDEDataProcessStarter batchKSDEDataProcessStarter;
	
	@Autowired
	BatchKSDEGetDataStarter batchKSDEGetDataStarter;
	
    private ObjectMapper mapper = new ObjectMapper();
	/**
	 * Logger for class.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ReportDataManagementController.class);
	
	@Autowired
	private ReportService reportService;
	
	/**
	 * organizationService
	 */
	@Autowired
	private OrganizationService organizationService;
	 
	/**
	 * OrganizationTypeService
	 */
	 @Autowired
	 private OrganizationTypeService organizationTypeService;
	
	/**
	 * orgAssessProgService
	 */
	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;
	
	/**
	 * categoryService
	 */
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	/**
	 * reportingReportCategories
	 */
	@Value("${report.upload.categorytype.code}")
	private String reportUploadCategoryTypeCode;
	
    /**
	 * Prasanth :  US16352 : To upload data file     
	 */
	@Value("${csvRecordTypeCode}")
	private String	csvRecordTypeCode; 
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	/**
	 * restrictedResourceConfiguration
	 */
	@Autowired
	private RestrictedResourceConfiguration restrictedResourceConfiguration;
	
	/**
	 * resourceRestrictionService
	 */
	@Autowired
	private ResourceRestrictionService resourceRestrictionService;
	
	/**
	 * permissionUtil
	 */
	@Autowired
	private PermissionUtil permissionUtil;
	 
	/**
	 * fileCharset
	 */
	@Value("${default.file.encoding.charset}")
	private Boolean defaultFileCharset;
	
	@Autowired
	private ExcludedItemsService excludedItemsService;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	@Autowired
	private TestCutScoresService testCutScoresService;
	
	@Autowired
	private SubscoreRawToScaleScoresService subscoreRawToScaleScoresService;
	 
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
	@Autowired
	private CombinedLevelMapService combinedLevelMapService;
	
	@Autowired
	private SubscoresDescriptionService subscoresDescriptionService;
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private SubscoreFrameworkService subscoreFrameworkService;
	
	@Autowired
	private SubScoresMissingStagesService subScoresMissingStagesService;
	
	@Autowired
	private ExternalReportImportService externalReportImportService;
	
	@Autowired
	ExternalStudentReportResultsProcessService externalStudentReportResultsProcessService;
	
	@Autowired
	private ExternalOrganizationResultsService uploadOrganizationReportDetailWriterProcessService;
	
	@Autowired
	private StudentPrctByAssessmentTopicService studentPrctByAssessmentTopicService;
	
	@Autowired
	private OrganizationPrctByAssessmentTopicService organizationPrctByAssessmentTopicService;
	
	@Autowired
	private GrfReportWriterService grfReportWriterService;
	
	@Autowired

	private OrganizationTypeService orgTypeService;
	
	@Autowired
	private DataReportService dataReportService;	

	@Autowired
	private GroupsService groupsService;

	@Autowired
	private AppConfigurationService appConfigurationService;
	

	@Value("${combinedLevelMapRecordType}")
	private String combinedLevelMapRecordType;
	
	@Value("${excludedItemsRecordType}")
	private String excludedItemsRecordType;
	
	@Value("${rawToScaleScoresRecordType}")
	private String rawToScaleScoresRecordType;
	
	@Value("${testCutScoresRecordType}")
	private String testCutScoresRecordTypeCode;
	
	@Value("${levelDescriptionsRecordType}")
	private String levelDescriptionsRecordType;
	
	@Value("${miscellaneousReportTextRecordType}")
	private String miscellaneousReportTextRecordType;
	
	@Value("${subscoreDescriptionReportUsageRecordType}")
	private String subscoreDescriptionReportUsageRecordType;
	
	@Value("${subscoreFrameworkMappingRecordType}")
	private String subscoreFrameworkMappingRecordType;
	
	@Value("${subscoreRawToScaleScoreRecordType}")
	private String subscoreRawToScaleScoreRecordType;
	
	@Value("${subScoreDefaultStageIdRecordType}")
	private String subScoreDefaultStageIdRecordType;
	
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
	
	@Value("${studentReportsImportRecordType}")
	private String studentReportsImportRecordType;
	
	@Value("${studentSummaryReportsImportRecordType}")
	private String studentSummaryReportsImportRecordType;
	
	@Value("${schoolReportsImportRecordType}")
	private String schoolReportsImportRecordType;
	
	@Value("${classroomReportsImportRecordType}")
	private String classroomReportsImportRecordType;
	
	@Value("${schoolCsvReportsImportRecordType}")
	private String schoolCsvReportsImportRecordType;
	
	@Value("${classroomCsvReportsImportRecordType}")
	private String classroomCsvReportsImportRecordType;
	
	@Value("${schoolReportsImportReportType}")
	private String  schoolReportsImportReportType;
	
	@Value("${schoolCsvReportsImportReportType}")
	private String  schoolCsvReportsImportReportType;
	
	@Value("${classroomReportsImportReportType}")
	private String  classroomReportsImportReportType;
	
	@Value("${classroomCsvReportsImportReportType}")
	private String  classroomCsvReportsImportReportType;
	
	@Value("${studentReportsImportReportType}")
	private String  studentReportsImportReportType;
	
	@Value("${studentSummaryReportsImportReportType}")
	private String  studentSummaryReportsImportReportType;
	
	@Value("${external.schoolLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL;
    
    @Value("${external.districtLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL;
    
    @Value("${external.districtLevel.school.bundled.reportType}")
	private String REPORT_TYPE_SCHOOL_SUMMARY_BUNDLED_DT_LVL;
    
    @Value("${external.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED;
        
	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
	
	@Value("${general.school.summary.report.type.code}")
	private String generalSchoolSummaryReportTypeCode;
	
	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
	
	@Value("${kelpa.student.bundled.report.type.code}")
	private String kelpaStudentBundledReportTypeCode;
	
	@Value("${general.district.summary.report.type.code}")
	private String generalDistrictSummaryReportTypeCode;
		
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;
	
	@Value("${questionInformationRecordType}")
	private String questionInformationRecordType;
	
	@Value("${testingProgramName.Interim}")
	private String interimTestingProgram;
	
	@Value("${testingProgramName.Summative}")
	private String summativeTestingProgram;
	
	@Value("${category.categoryCode.currentInterimReportCycle}")
	private String currentReportCycleCategory;
		
	@Value("${categoryType.typeCode.reportCycle}")
	private String reportCycleCategoryType;
	
	@Value("${cpassTestCutScoreRecordType}")
	private String cpassTestCutScoreRecordType;	
	
	@Value("${cpassLevelDiscriptionRecordType}")
	private String cpassLevelDiscriptionRecordType;
	
	@Value("${uploadStudentScoreCaluculations}")
	private String uploadStudentScoreCaluculations;
	
	@Value("${uploadStudentPCTByAssessmentTopic}")
	private String uploadStudentPrctByAssessmentTopic;
	
	@Value("${uploadOrganizationPCTByAssessmentTopic}")
	private String uploadOrganizationPrctByAssessmentTopic;
	
	@Value("${uploadOrganizationScoreCaluculations}")
	private String uploadOrganizationScoreCaluculations;
	
	@Value("${category.categoryCode.currentCPASSReportCycle}")
	private String currentCPASSReportCycleCategory;
	
	@Value("${uploadAssessmentTopicCode}")
	private String uploadAssessmentTopicCode;
	
	@Value("${studentDcpsReportsImportRecordType}")
	private String studentDcpsReportsImportRecordType;
	
	@Value("${studentDCPSReportsImportReportType}")
	private String studentDCPSReportsImportReportType;
	
	@Value("${uploadStudentPercentCorrectForAssessmentTopic}")
	private String uploadStudentPercentCorrectForAssessmentTopic;
	
	@Value("${uploadOrganizationPercentCorrectForAssessmentTopic}")
	private String uploadOrganizationPercentCorrectForAssessmentTopic;
	
	@Value("${uploadDCGrfFileType}")
	private String uploadDCGrfFileType;

	@Value("${uploadDelawareGrfFileType}")
	private String uploadDelawareGrfFileType;
	
	@Value("${uploadArkansasGrfFileType}")
	private String uploadArkansasGrfFileType;


	@Value("${permissionRecordType}")
	private String permissionRecordType;


	
	@Autowired
	private TestingProgramService testingProgramService;
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private QuestionInformationService questionInformationService;
	
	@Autowired
	private InterimPredictiveReportProcessStarter interimPredictiveReportProcessStarter;
	
	@Autowired
	private AssessmentTopicService assessmentTopicService;
	
  
    @Autowired
    private PermissionUploadFileService permissionUploadFileService;
	

    @Value("${cpass.student.individual.report.type.code}")
	private String externalStudentReportType;
    
    @Value("${cpass.school.detail.report.type.code}")
	private String cpassSchoolDetailsReportType;
    
    @Value("${cpass.organization.score.report.type.code}")
	private String cpassOrganizationScoreReportReportType;
    
    @Value("${GRF.extract.Exit.Student}")
 	private String GRFExtractExitStudent;
    
    @Value("${GRF.extract.SCCode}")
 	private String GRFExtractSCCode;
   

    @Value("${GRF.update.upload}") 
    private String GRFUpdatedUpload;
    		   
	@Value("${GRF.original.upload}")
	private String GRFOriginalUpload;
			   
	@Value("${GRF.district.report.process.string}")
	private String GRFStateAndDistrictaggregate;
			    
	@Value("${GRF.school.report.process.string}")
	private String GRFSchoolAggregate;
			    
    @Value("${GRF.classroom.report.process.string}")
    private String GRFClassAggregate;
    		    
    @Autowired
    private AwsS3Service s3;
    

    private String headerJsonStr;

	static Comparator<AssessmentProgram> assmntPgmComparator = new Comparator<AssessmentProgram>(){
	   	public int compare(AssessmentProgram ap1, AssessmentProgram ap2){
	   		return ap1.getProgramName().compareToIgnoreCase(ap2.getProgramName());
	   	}
	};
	
	static Comparator<Category> alphaCategoryComparator = new Comparator<Category>(){
		public int compare(Category c1, Category c2){
	   		return c1.getCategoryName().compareToIgnoreCase(c2.getCategoryName());
	   	}
	};
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	/**
	 * @return
	 */
	@RequestMapping(value = "reportDataManagement.htm")
	public final ModelAndView reports() {
		ModelAndView mav = new ModelAndView("reports/reportDataManagement");
	  
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		 List<OrganizationType> organizationTypes = organizationTypeService.getAll();
		 List<Organization> organizationsStates = new ArrayList<Organization>(); 
		 List<Organization> organizationsConsortia = new ArrayList<Organization>(); 
		 List<Organization> organizations = organizationService.getAll();
		 
		 for(Organization organizationsState : organizations){
			 /*if(organizationsState.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
			 organizationsStates.add(organizationsState);
		 }*/
			 if(organizationsState.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_1) {
				 organizationsConsortia.add(organizationsState);
			 }
		 }
		 
		Long userId = user.getId();
		Long orgId = user.getOrganization().getId();
		List<Organization> states = null;
		if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)){
			states = organizationService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
		} else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)){
			//Hack for DE5725 - Org structure for DLM and ARMM are different
		if (user.getOrganization().getDisplayIdentifier().equals("DLM") || user.getOrganization().getDisplayIdentifier().equals("ARMM")){
				states =organizationService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_REGION_CODE);
			}else{
				states = organizationService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, userId);
			}
		}  
		//Hack for DE5725 - Org structure for DLM and ARMM are different
		else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_REGION_CODE)){
			states = organizationService.getByTypeAndUserId(CommonConstants.ORGANIZATION_REGION_CODE, userId);
		}
		
		for(Organization organizationsState : states){
			 if(organizationsState.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
				 organizationsStates.add(organizationsState);
			 }
		}
			
		 mav.addObject("assessmentPrograms", getAssessmentPrograms(user));
		 mav.addObject("organizationsStates", organizationsStates);
		 mav.addObject("organizationsConsortia", organizationsConsortia);
		 mav.addObject("organizationTypes",organizationTypes);
		 return mav;
	}
		
   /**
	* @param user
	*            {@link User}
	* @return List<AssessmentProgram>
	*/
	 private List<AssessmentProgramDto> getAssessmentPrograms(final User user) {
		 //List<Organization> orgs = userService.getOrganizations(user.getId());
		 // TODO change this from the current organization to a unique list of the current organization and its parents assessment
		 // programs.
		 Organization current = user.getOrganization();
		 // List<AssessmentPrograBatchReportProcessStarterm> assessmentPrograms = new ArrayList<AssessmentProgram>();
		 List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
		 if (current != null) {
			 List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(current.getId());
			 for (OrgAssessmentProgram oap : orgAssessProgs) {
				 if (!assessmentPrograms.contains(oap.getAssessmentProgram())) {
					 AssessmentProgramDto temp = new AssessmentProgramDto();
					 temp.setAssessmentProgram(oap.getAssessmentProgram());
					 assessmentPrograms.add(temp);
				 }
			 }
		 }
		 return assessmentPrograms;
	 }
	
	 
		 
	/**
	 * @return
	 */
	@RequestMapping(value = "getUploadFileTypes.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Category> getUploadFileTypes() {
	    LOGGER.trace("Entering the getUploadFileTypes method.");
	    
		List<Category> fileTypes = null;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		fileTypes = categoryService.selectByCategoryType(reportUploadCategoryTypeCode);
		Category toRemove = null;
		List<Category> duplicateFiletypes=new ArrayList();
		for(Category fileType : fileTypes)
		{
			/** Added by Uday: Feature :F97: DLM reports  
			 *Added to keep the same file type name once and to handle  different column set for upload 
			 */
			if((fileType.getCategoryCode().equalsIgnoreCase(levelDescriptionsRecordType)||fileType.getCategoryCode().equalsIgnoreCase(testCutScoresRecordTypeCode)) && user.getCurrentAssessmentProgramName().equalsIgnoreCase("CPASS")){
				duplicateFiletypes.add(fileType);
			}else if((fileType.getCategoryCode().equalsIgnoreCase(cpassTestCutScoreRecordType)||fileType.getCategoryCode().equalsIgnoreCase(cpassLevelDiscriptionRecordType)) && !user.getCurrentAssessmentProgramName().equalsIgnoreCase("CPASS")){
				duplicateFiletypes.add(fileType);
			}
			
			if(!fileType.getActiveFlag())
				toRemove = fileType;
		}
		if(toRemove!=null)
			fileTypes.remove(toRemove);
		/** Added by Uday: Feature :F97: DLM reports  
		 *Added to keep the same file type name once and to handle  different column set for upload 
		 */
			fileTypes.removeAll(duplicateFiletypes);
		
		Collections.sort(fileTypes, alphaCategoryComparator);
		
		LOGGER.trace("Leaving the getUploadFileTypes method.");
	    return fileTypes;
	}
	
	 /**
	 * TODO move it to a EncodingDetector
	 * Detects encoding of the given file by reading from the input stream.
	 * @param fis
	 * @return
	 * @throws IOException
	 */
	public String detectCharset(InputStream fis) throws IOException {
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
		return charset;
	}
	
	@RequestMapping(value = "getSummativeReportUploads.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getSummativeReportUploads(
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String orderByColumn,
			@RequestParam("sord") String order) {
		Map<String, Object> reportUpload = new HashMap<String, Object>();
		int currentPage = NumericUtil.parse(page, 1);
		int limitCount = NumericUtil.parse(limitCountStr, 5);
		int offset = (currentPage - 1) * limitCount;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		List<AssessmentProgram> assessmentPrograms = assessmentProgramService.findByOrganizationId(userDetails.getUser().getCurrentOrganizationId());
		if (assessmentPrograms.size() > 0) {
			List<Long> assessmentProgramIds = new ArrayList<Long>();
			for (AssessmentProgram ap : assessmentPrograms) {
				assessmentProgramIds.add(ap.getId());
			}
			List<Long> fileTypeIds = new ArrayList<Long>();
			List<Category> fileTypes = categoryService.selectByCategoryType("REPORT_UPLOAD_FILE_TYPE");
			for(Category fileTypeid : fileTypes){
				fileTypeIds.add(fileTypeid.getId());
			}
			
			List<BatchUpload> uploads = batchUploadService.selectByAssessmentProgramIdsAndFiltersBatchUpload(assessmentProgramIds,
					orderByColumn, order, limitCount, offset,fileTypeIds);
			for(BatchUpload upload : uploads){
				upload.setReasons(batchUploadService.findBatchUploadReasonsForId(upload.getId()));
			}
			
			int totalCount = batchUploadService.getUploadCountByAssessmentProgramIdsAndFilters(assessmentProgramIds,fileTypeIds);
			
			reportUpload.put("rows", uploads);
			reportUpload.put("total", NumericUtil.getPageCount(totalCount, limitCount));
			reportUpload.put("page", currentPage);
			reportUpload.put("records", totalCount);
			return reportUpload;
		} else {
			reportUpload.put("rows", new ArrayList<BatchUpload>());
			reportUpload.put("total", 0);
			reportUpload.put("page", 0);
			reportUpload.put("records", 0);
			return reportUpload;
		}
	}
	
	@RequestMapping(value = "getUploadInvalidReasons.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getUploadInvalidReasons(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> reportUploadReasons = new HashMap<String, Object>();

		if(request.getParameter("uploadId") != null){
			Long uploadId = Long.valueOf(request.getParameter("uploadId"));
			reportUploadReasons.put("rows", batchUploadService.findBatchUploadReasonsForId(uploadId)) ;
		}
		return reportUploadReasons;
	}
	
	@RequestMapping(value = "checkDuplicateUpload.htm", method = RequestMethod.POST)
	public final @ResponseBody int checkDuplicateUpload(@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("fileTypeId") Long fileTypeId, @RequestParam("contentAreaId") Long contentAreaId, 
			@RequestParam("testingProgramId") Long testingProgramId,
			@RequestParam("testingCycleId") Long testingCycleId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		int schoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		String reportCycle = null;
		TestingProgram testingProgram = testingProgramService.getByTestingProgramId(testingProgramId);
		if(testingProgram != null && interimTestingProgram.equalsIgnoreCase(testingProgram.getProgramName())){
			TestingCycle testingCycle = batchReportProcessService.getTestingCycleByTestingProgramId(testingCycleId);
			if(testingCycle != null){
				reportCycle = testingCycle.getTestingCycleName();
			}
		}
		return batchUploadService.selectDuplicateCountBatchUpload(assessmentProgramId, contentAreaId, fileTypeId, schoolYear, testingProgramId, reportCycle);
	}
	

	
	@RequestMapping(value = "uploadFileData.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> uploadFileData(
			MultipartHttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		LOGGER.trace("Entering the uploadFileData method.");
		/* Added for US16548*/
		request.getParameter("reportCycleId");
		Date now = new Date();
		
		Map<String, Object> reponse = new HashMap<String, Object>();

		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
		
		long fileTypeId = 0l;
		if (request.getParameter("fileTypeId") != null) {
			fileTypeId = Long.parseLong(request.getParameter("fileTypeId"));
		}
	    /**
		 * Prasanth :  US16352 : To upload data file
		 * assessmentProgramId can not be null in db     
		 */
		Long assessmentProgramId = (long) 0 ;
		if (request.getParameter("assessmentProgramId") != null) {
			assessmentProgramId = Long.parseLong(request.getParameter("assessmentProgramId"));
		} else if (user.getCurrentAssessmentProgramId() != null) {
			assessmentProgramId = user.getCurrentAssessmentProgramId();
		}
		Long contentAreaId = (long) 0 ;
		if (request.getParameter("reportingCycleId") != null && StringUtils.isNotBlank(request.getParameter("contentAreaId"))) {
			contentAreaId = Long.parseLong(request.getParameter("contentAreaId"));
		}
		
		/**
		 * Prasanth :  US16352 : To upload data file
		 * get the upload category code and find typeId      
		 */
		String categoryCode = request.getParameter("categoryCode");
		
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		//Added for F671
		if(uploadCommonGrfFileType.equals(categoryCode)){
			if(!"DLM".equalsIgnoreCase(assessmentProgram.getAbbreviatedname())){
				reponse.put("errorMessage", "GRF upload is not allowed for "+assessmentProgram.getAbbreviatedname());
				return reponse;

			}else{
        if("NY".equalsIgnoreCase(user.getContractingOrganization().getDisplayIdentifier())) {
					categoryCode = uploadNewYorkGrfFileType;				
				}
				
				if("IA".equalsIgnoreCase(user.getContractingOrganization().getDisplayIdentifier())) {
					categoryCode = uploadIowaGrfFileType;					
				}
				
				if("DE".equalsIgnoreCase(user.getContractingOrganization().getDisplayIdentifier())) {
					categoryCode = uploadDelawareGrfFileType;					
				}	
				
				if("DC".equalsIgnoreCase(user.getContractingOrganization().getDisplayIdentifier())) {
					categoryCode = uploadDCGrfFileType;					
				}

				if("AR".equalsIgnoreCase(user.getContractingOrganization().getDisplayIdentifier())) {
					categoryCode = uploadArkansasGrfFileType;					
				}

		   }
		}		
		// TODO Fix this.
		List<String> categoryTypes = Arrays.asList("CSV_RECORD_TYPE", "XML_RECORD_TYPE");
		String csvOrXmlfileType = null;
		if ( categoryCode != null) {
			//fileTypeId = batchUploadService.getUploadFileTypeId(categoryCode);
			List<Category> fileTypes = categoryService.selectByCategoryType(categoryTypes);
			for (Category fileType : fileTypes) {
				if (categoryCode.equals(fileType.getCategoryCode())) {
					fileTypeId = fileType.getId();
					csvOrXmlfileType = fileType.getCategoryCode();
					break;
				}
			}
		}
		
		Long stateId = null;
		if (request.getParameter("stateId") != null) {
			stateId = Long.parseLong(request.getParameter("stateId"));
		}else if("PROJ_TEST_RECORD_TYPE".equalsIgnoreCase(categoryCode) || "SCORING_RECORD_TYPE".equalsIgnoreCase(categoryCode) || "PERMISSION_RECORD_TYPE".equalsIgnoreCase(categoryCode)){
			stateId = userDetails.getUser().getContractingOrgId();
		}
		Long districtId = null;
		String districtIdStr = request.getParameter("districtId");
		if (districtIdStr != null && districtIdStr.trim().length() > 0 ) {
			districtId = Long.parseLong(districtIdStr);
		}
		Long schoolId = null;
		String schoolIdStr = request.getParameter("schoolId") ;
		if (schoolIdStr != null && schoolIdStr.trim().length() > 0) {
			schoolId = Long.parseLong(schoolIdStr);
		}
		Long selectedOrgId = null;
		if (request.getParameter("selectedOrgId") != null) {
			selectedOrgId = Long.parseLong(request.getParameter("selectedOrgId"));
		}		
		String reportUploadType = request.getParameter("reportUpload");
		Boolean reportUpload = null;
		if(reportUploadType!=null)
			reportUpload  = Boolean.parseBoolean(reportUploadType);
/*********/		
		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		TestingProgram testingProgram = null; 
		
		BatchUpload upload = new BatchUpload();
		upload.setUploadTypeId(fileTypeId);
		upload.setAssessmentProgramId(assessmentProgramId);
		upload.setContentAreaId(contentAreaId);
		upload.setSubmissionDate(now);
		upload.setCreatedUser(user.getId());
		upload.setStatus("Pending");
		upload.setSchoolYear(currentSchoolYear);
		upload.setActiveFlag(true);
		if(request.getParameter("testingProgramId") != null && StringUtils.isNotBlank(request.getParameter("testingProgramId"))){
			testingProgram = testingProgramService.getByTestingProgramId(Long.parseLong(request.getParameter("testingProgramId")));
			upload.setTestingProgramId(testingProgram.getId());
			upload.setTestingProgramName(testingProgram.getProgramName());
		}
		
		
		/**
		 * Prasanth :  US16352 : To upload data file
		 *       
		 */
		upload.setStateId(stateId);
		upload.setDistrictId(districtId);
		upload.setSchoolId(schoolId);
		upload.setSelectedOrgId(selectedOrgId);
		upload.setUploadedUserOrgId(userDetails.getUser().getCurrentOrganizationId());
		upload.setUploadedUserGroupId(userDetails.getUser().getCurrentGroupsId());
		
		upload.setFileName(cmpf.getOriginalFilename());
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		String formattedDate = formatter.format(new Date());
		String folderDir = assessmentProgramId + "_" + contentAreaId + "_";
		/**
		 * Prasanth :  US16352 : To upload data file
		 */
		if( reportUpload != null && ! reportUpload){
			folderDir = stateId + "_";
			if( districtId != null) folderDir = districtId + "_";
			if( schoolId != null) folderDir =  schoolId + "_";
		}
		/**
		 * Uday :  F459 : To upload data file
		 * get the report year to which we are uploading the file       
		 */
		
		Long reportYear =null;
		
		boolean isHeaderValidationSuccess=true;

		if(request.getParameter("reportYear")!=null){
		  reportYear=	Long.valueOf(request.getParameter("reportYear"));
		}
		upload.setReportYear(reportYear);
		// write the file to disk
		String folderPath = REPORT_PATH;
		if (!folderPath.endsWith(File.separator)) {
			folderPath += File.separator;
		}
		folderPath += user.getId()  + File.separator + formattedDate +  File.separator + folderDir;
		/**
		 * Prasanth :  US16352 : To upload data file
		 */
		if( reportUpload != null && ! reportUpload)
			folderPath += "data_uploads" ;
		else
			folderPath += "summative_report_uploads";
		
		String filePath = folderPath + File.separator + cmpf.getOriginalFilename().replace(" ", "_");
		String[] splitFilePath = filePath.split("\\.");
		String extension = "."+splitFilePath[1];
		File newFile = File.createTempFile(splitFilePath[0], extension);
		cmpf.transferTo(newFile);
		upload.setFilePath(filePath);
		
		String uploadReportType = null;
		List<Category> fileTypes = categoryService.selectByCategoryType(reportUploadCategoryTypeCode);
		for (Category fileType : fileTypes) {
			if (fileTypeId == fileType.getId()) {
				uploadReportType = fileType.getCategoryCode();
				break;
			}
		}
		List<String> reportTypeSummary = new ArrayList<String>();
		
		String reportType="";
		if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname()))
			reportType=cpassStudentBundledReportTypeCode;
		else if(assessmentProgram!=null && "DLM".equals(assessmentProgram.getAbbreviatedname()))
			reportType=alternateStudentBundledReportTypeCode;
		else if(assessmentProgram!=null && "KAP".equals(assessmentProgram.getAbbreviatedname()))
			reportType=generalStudentBundledReportTypeCode;		
		
		if(assessmentProgram!=null && "KAP".equals(assessmentProgram.getAbbreviatedname())){
			reportTypeSummary.add(generalSchoolSummaryReportTypeCode);	
			reportTypeSummary.add(generalDistrictSummaryReportTypeCode);	
		}		
		
		if(!"CPASS".equalsIgnoreCase(assessmentProgram.getAbbreviatedname()) && (uploadStudentPrctByAssessmentTopic.equalsIgnoreCase(uploadReportType)				
				||uploadOrganizationPrctByAssessmentTopic.equalsIgnoreCase(uploadReportType)
				||uploadAssessmentTopicCode.equalsIgnoreCase(uploadReportType))){
			reponse.put("errorMessage", "Selected File type is not allowed for "+assessmentProgram.getAbbreviatedname());
			return reponse;
		}
		
		if(uploadReportType != null 
				&& assessmentProgramId != null && assessmentProgramId > 0){ // If upload is report type then only go through this step.						
			
			Long schoolYear = (long) user.getContractingOrganization().getCurrentSchoolYear();
			Organization contrOrg = organizationService.getContractingOrgByAssessmentProgramIdOrgId(assessmentProgramId, user.getContractingOrgId());
			
			if(interimTestingProgram.equalsIgnoreCase(upload.getTestingProgramName()) && contrOrg != null){
				schoolYear = contrOrg.getCurrentSchoolYear();
			}else{
				schoolYear = (long) contrOrg.getReportYear();
				//Changed for F97 - report year changes
				Long newReportYear = organizationService.getReportYear(user.getContractingOrgId(), assessmentProgramId);
				if(newReportYear != null)
					schoolYear = newReportYear;
			}
			
			String errorMessage = null;
			
			if(interimTestingProgram.equalsIgnoreCase(upload.getTestingProgramName()) && 
					!(uploadReportType.equalsIgnoreCase(rawToScaleScoresRecordType) 
							|| uploadReportType.equalsIgnoreCase(testCutScoresRecordTypeCode)
							|| uploadReportType.equalsIgnoreCase(levelDescriptionsRecordType)
							|| uploadReportType.equalsIgnoreCase(questionInformationRecordType)
							|| uploadReportType.equalsIgnoreCase(excludedItemsRecordType))){
				errorMessage = "Testing Program is invalid for the selected file type";
				reponse.put("errorMessage", errorMessage);
				return reponse;
			}
			
			Map<String, FieldSpecification> feildSpecMap = new HashMap<String, FieldSpecification>();
			feildSpecMap = uploadFileService.getFieldSpecificationRecordMap(uploadReportType, reportUploadCategoryTypeCode);
			String [] headNames = getFileHeaders(newFile);
			if(headNames != null){
				for(int i=0;i<headNames.length;i++){
					//trim heading from file
					headNames[i] = headNames[i].trim();
					if(headNames[i].equalsIgnoreCase("comment")){
						continue;
					}
					FieldSpecification fieldName = feildSpecMap.get(headNames[i].toLowerCase());
					if(fieldName == null){
						errorMessage = "Heading validation failed, please upload a correct file for selected options.";
						reponse.put("errorMessage", errorMessage);
						return reponse;
					}
				}
			}
			
			
			
			if(uploadReportType.equalsIgnoreCase(rawToScaleScoresRecordType) 
					|| uploadReportType.equalsIgnoreCase(testCutScoresRecordTypeCode)
					|| uploadReportType.equalsIgnoreCase(levelDescriptionsRecordType)
					|| uploadReportType.equalsIgnoreCase(questionInformationRecordType)
					|| cpassLevelDiscriptionRecordType.equalsIgnoreCase(uploadReportType)
					|| cpassTestCutScoreRecordType.equalsIgnoreCase(uploadReportType)
					|| excludedItemsRecordType.equalsIgnoreCase(uploadReportType)
					){
				
				String testingProgramFromFile = getFieldValueFromFile(newFile, "Testing_Program");
				
				if(StringUtils.isNotBlank(testingProgramFromFile) && testingProgramFromFile.equalsIgnoreCase(upload.getTestingProgramName())){
					//if interim then compare reportcycle from file with currentreportcycle from category table, reject if both are not same
					if(interimTestingProgram.equalsIgnoreCase(upload.getTestingProgramName()) 
								|| (summativeTestingProgram.equalsIgnoreCase(upload.getTestingProgramName())
							&& (cpassLevelDiscriptionRecordType.equalsIgnoreCase(uploadReportType)
								||cpassTestCutScoreRecordType.equalsIgnoreCase(uploadReportType))
								||excludedItemsRecordType.equalsIgnoreCase(uploadReportType))){
						
						if(!summativeTestingProgram.equalsIgnoreCase(upload.getTestingProgramName()) && excludedItemsRecordType.equalsIgnoreCase(uploadReportType)) {
						
							String reportCycleFromFile = getFieldValueFromFile(newFile, "Report_Cycle");
							String reportCycleFromUI = null;
							if(request.getParameter("reportingCycleId") != null && StringUtils.isNotBlank(request.getParameter("reportingCycleId")) && Long.parseLong(request.getParameter("reportingCycleId")) > 0 ){
								TestingCycle testingCycle = batchReportProcessService.getTestingCycleByTestingProgramId(Long.parseLong(request.getParameter("reportingCycleId")));
								if(testingCycle != null){
									reportCycleFromUI = testingCycle.getTestingCycleName();
									Category reportCycleCategory = categoryService.selectByCategoryCodeAndType(cpassLevelDiscriptionRecordType.equalsIgnoreCase(uploadReportType) || cpassTestCutScoreRecordType.equalsIgnoreCase(uploadReportType) ? currentCPASSReportCycleCategory : currentReportCycleCategory,  reportCycleCategoryType);
									if(reportCycleCategory!=null && !reportCycleCategory.getCategoryName().equalsIgnoreCase(reportCycleFromUI)){
										errorMessage = "Selected report cycle (" + reportCycleFromUI + ")" + " is invalid. Expecting current report cycle value as " + reportCycleCategory.getCategoryName();
										reponse.put("errorMessage", errorMessage);
										return reponse;
									}
								}
							}else{
								errorMessage = "Please select report cycle";
								reponse.put("errorMessage", errorMessage);
								return reponse;
							}
							
							if(StringUtils.isNotBlank(reportCycleFromFile) 
									&& reportCycleFromUI.equals(reportCycleFromFile)){
								upload.setReportCycle(reportCycleFromUI);
							}else{
								errorMessage = "Report cycle specified in the file (" + reportCycleFromFile + ")" + " is invalid. Expecting current report cycle value as " + reportCycleFromUI;
								reponse.put("errorMessage", errorMessage);
								return reponse;
							}
						}
						
					}				
					
				}else{
					errorMessage = "Testing program name provided in the file is not matching with the selected value on the page";
					reponse.put("errorMessage", errorMessage);
					return reponse;
				}				
				
			}			
			
			if(summativeTestingProgram.equalsIgnoreCase(upload.getTestingProgramName())){
				if("CPASS".equals(assessmentProgram.getAbbreviatedname())){
					if(uploadAssessmentTopicCode.equalsIgnoreCase(uploadReportType)){
						uploadOrganizationReportDetailWriterProcessService.deleteOrganizationReportDetail(null, assessmentProgramId, contentAreaId, schoolYear, null, null,cpassSchoolDetailsReportType);								
						studentPrctByAssessmentTopicService.deleteStudentPrct(null, contentAreaId, schoolYear, null, null);
						organizationPrctByAssessmentTopicService.deleteOrganizationPrct(null, contentAreaId, schoolYear, null, null);
						assessmentTopicService.deleteAssessmentTopic(contentAreaId, schoolYear);
					}else{
					    externalStudentReportResultsProcessService.deleteExternalStudentReportResults(null,assessmentProgramId, contentAreaId, schoolYear, upload.getTestingProgramId(), upload.getReportCycle());
					    uploadOrganizationReportDetailWriterProcessService.deleteOrganizationReportDetail(null,assessmentProgramId, contentAreaId, schoolYear, upload.getTestingProgramId(), upload.getReportCycle(), cpassOrganizationScoreReportReportType);
					}
				}else{
					deleteCalcProcess(assessmentProgramId, contentAreaId, null, false, schoolYear, reportTypeSummary, reportType);	
				}
			}else if(interimTestingProgram.equalsIgnoreCase(upload.getTestingProgramName())){
				//delete predictive ISR calcs and reports
				deleteInterimPredictiveReports(assessmentProgramId, upload.getReportCycle(), contentAreaId, null, schoolYear, null);
				
				//delete predictive school/district summary calcs and reports
				deletePredictiveSchoolDistrictSummary(assessmentProgramId, upload.getTestingProgramId(), upload.getReportCycle(), schoolYear, contentAreaId, null);
			}
			
			//For Report Uploads Delete all existing data in respective tables.
			if(uploadReportType.equalsIgnoreCase(excludedItemsRecordType)){
				excludedItemsService.deleteExcludedItems(assessmentProgramId, contentAreaId, schoolYear);
			} else if(uploadReportType.equalsIgnoreCase(rawToScaleScoresRecordType)){
				rawToScaleScoresService.deleteRawToScaleScores(assessmentProgramId, contentAreaId, schoolYear, upload.getTestingProgramId(), upload.getReportCycle());
			} else if(uploadReportType.equalsIgnoreCase(testCutScoresRecordTypeCode)||uploadReportType.equalsIgnoreCase(cpassTestCutScoreRecordType)){
				testCutScoresService.deleteTestCutScores(assessmentProgramId, contentAreaId, schoolYear, upload.getTestingProgramId(), upload.getReportCycle());
			} else if(uploadReportType.equalsIgnoreCase(levelDescriptionsRecordType)||uploadReportType.equalsIgnoreCase(cpassLevelDiscriptionRecordType)){
				levelDescriptionService.deleteLevelDescriptions(assessmentProgramId, contentAreaId, schoolYear, upload.getTestingProgramId(), upload.getReportCycle());
			} else if(uploadReportType.equalsIgnoreCase(miscellaneousReportTextRecordType)){
				
			} else if(uploadReportType.equalsIgnoreCase(subscoreDescriptionReportUsageRecordType)){
				subscoresDescriptionService.deleteSubscoresDescriptions(assessmentProgramId, contentAreaId, schoolYear);
			} else if(uploadReportType.equalsIgnoreCase(subscoreFrameworkMappingRecordType)){
				subscoreFrameworkService.deleteSubscoreFrameworks(assessmentProgramId, contentAreaId, schoolYear);
			} else if(uploadReportType.equalsIgnoreCase(subscoreRawToScaleScoreRecordType)){
				subscoreRawToScaleScoresService.deleteSubscoreRawToScaleScores(assessmentProgramId, contentAreaId, schoolYear);
			} else if(uploadReportType.equalsIgnoreCase(combinedLevelMapRecordType)){
				combinedLevelMapService.deleteCombinedLevelMap(assessmentProgramId, contentAreaId, schoolYear);
			} else if(uploadReportType.equalsIgnoreCase(subScoreDefaultStageIdRecordType)) {
				subScoresMissingStagesService.deleteSubScoreDefaultStages(assessmentProgramId, contentAreaId, schoolYear);
			} else if(uploadReportType.equalsIgnoreCase(questionInformationRecordType)) {
				questionInformationService.deleteQuestionInformation(assessmentProgramId, contentAreaId, schoolYear, upload.getTestingProgramId(), upload.getReportCycle());
			}
		} else if (csvOrXmlfileType != null 
				&& assessmentProgramId != null && assessmentProgramId > 0) {
			try {
				String errorMessage = null;
				if (!newFile.getPath().endsWith(".csv") && (csvOrXmlfileType.equalsIgnoreCase(studentReportsImportRecordType) | csvOrXmlfileType.equalsIgnoreCase(studentSummaryReportsImportRecordType) | 
						csvOrXmlfileType.equalsIgnoreCase(schoolReportsImportRecordType) | csvOrXmlfileType.equalsIgnoreCase(classroomReportsImportRecordType) | 
						csvOrXmlfileType.equalsIgnoreCase(schoolCsvReportsImportRecordType) | csvOrXmlfileType.equalsIgnoreCase(classroomCsvReportsImportRecordType)
						|csvOrXmlfileType.equalsIgnoreCase(uploadOrganizationScoreCaluculations)|csvOrXmlfileType.equalsIgnoreCase(uploadStudentScoreCaluculations)|
						csvOrXmlfileType.equalsIgnoreCase(studentDcpsReportsImportRecordType))){
					errorMessage = "The uploaded file must have a file extension of csv for the selected File Type.";
					reponse.put("errorMessage", errorMessage);
					return reponse;
				}
				if (newFile.getPath().endsWith(".csv")){
					String reportTypeFromFile = peekAtReportType(newFile);
	
					boolean exitForNonMatchingReportType = false;
					Organization state = organizationService.getContractingOrganization(stateId);
					
					//Changed for F97 - report year changes
					Long newReportYear = organizationService.getReportYear(user.getContractingOrgId(), assessmentProgramId);
					if(newReportYear != null)
						state.setReportYear(newReportYear.intValue());
					
					if (state != null && reportYear != null &&!(reportYear.intValue() == state.getReportYear())){
						errorMessage = "The selected school year ["+reportYear+"] does not match "+state.getDisplayIdentifier()+" set report year ["+state.getReportYear()+"].";
						reponse.put("errorMessage", errorMessage);
						return reponse;
					}
					if (csvOrXmlfileType.equalsIgnoreCase(studentReportsImportRecordType)) {
						if (reportTypeFromFile.equalsIgnoreCase(studentReportsImportReportType)){
							externalReportImportService.deleteStudentFiles(assessmentProgramId, reportYear, stateId);
							externalReportImportService.deleteStudentRecords(assessmentProgramId, reportYear, stateId, assessmentProgram.getAbbreviatedname());
						}else{
							errorMessage = "The report type in the uploaded file ["+reportTypeFromFile+"] does not match the selected file type [Student Reports Import].";
							exitForNonMatchingReportType = true;
						}
					} else if (csvOrXmlfileType.equalsIgnoreCase(studentSummaryReportsImportRecordType)) {
						if (reportTypeFromFile.equalsIgnoreCase(studentSummaryReportsImportReportType)){
							externalReportImportService.deleteStudentSummaryFiles(assessmentProgramId, reportYear, stateId);
							externalReportImportService.deleteStudentSummaryRecords(assessmentProgramId, reportYear, stateId);
						}else{
							errorMessage = "The report type in the uploaded file ["+reportTypeFromFile+"] does not match the selected file type [Student Summary Reports Import].";
							exitForNonMatchingReportType = true;
						}
					} else if (csvOrXmlfileType.equalsIgnoreCase(schoolReportsImportRecordType)) {
						if (reportTypeFromFile.equalsIgnoreCase(schoolReportsImportReportType)){
							externalReportImportService.deleteSchoolFiles(assessmentProgramId, reportYear, stateId);
							externalReportImportService.deleteSchoolRecords(assessmentProgramId, reportYear, stateId);
						}else{
							errorMessage = "The report type in the uploaded file ["+reportTypeFromFile+"] does not match the selected file type [School Reports Import].";
							exitForNonMatchingReportType = true;
						}
					} else if (csvOrXmlfileType.equalsIgnoreCase(classroomReportsImportRecordType)) {
						if (reportTypeFromFile.equalsIgnoreCase(classroomReportsImportReportType)){
							externalReportImportService.deleteClassroomFiles(assessmentProgramId, reportYear, stateId);
							externalReportImportService.deleteClassroomRecords(assessmentProgramId, reportYear, stateId);
						}else{
							errorMessage = "The report type in the uploaded file ["+reportTypeFromFile+"] does not match the selected file type [Classroom Reports Import].";
							exitForNonMatchingReportType = true;
						}
					} else if (csvOrXmlfileType.equalsIgnoreCase(classroomCsvReportsImportRecordType)) {
						if (reportTypeFromFile.equalsIgnoreCase(classroomCsvReportsImportReportType)){
							externalReportImportService.deleteClassroomCsvFiles(assessmentProgramId, reportYear, stateId);
						}else{
							errorMessage = "The report type in the uploaded file ["+reportTypeFromFile+"] does not match the selected file type [Classroom CSV Reports Import].";
							exitForNonMatchingReportType = true;
						}
					} else if (csvOrXmlfileType.equalsIgnoreCase(schoolCsvReportsImportRecordType)) {
						if (reportTypeFromFile.equalsIgnoreCase(schoolCsvReportsImportReportType)){
							externalReportImportService.deleteSchoolCsvFiles(assessmentProgramId, reportYear, stateId);
						}else{
							errorMessage = "The report type in the uploaded file ["+reportTypeFromFile+"] does not match the selected file type [School CSV Reports Import].";
							exitForNonMatchingReportType = true;
						}
					} else if (csvOrXmlfileType.equalsIgnoreCase(uploadStudentScoreCaluculations)) {//Added for CPASS results upload
		
						//Validate File header and report cycle value
						if(validateFileHeader(csvOrXmlfileType, newFile)){
							errorMessage = validateReportcycle(newFile, request, csvOrXmlfileType, upload);
							if(errorMessage == null){
								externalStudentReportResultsProcessService.deleteExternalStudentReportResults(stateId,assessmentProgramId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle());
						    }else{
								reponse.put("errorMessage", errorMessage);
								return reponse;
						    }
						}else{
							errorMessage = "Heading validation failed, please upload a correct file for selected options.";
							reponse.put("errorMessage", errorMessage);
							return reponse;
						}
						
					} else if (csvOrXmlfileType.equalsIgnoreCase(uploadOrganizationScoreCaluculations)) {//Added for CPASS results upload
						//Validate File header and report cycle value
						if(validateFileHeader(csvOrXmlfileType, newFile)){
							errorMessage = validateReportcycle(newFile, request, csvOrXmlfileType, upload);
							if(errorMessage == null){
								uploadOrganizationReportDetailWriterProcessService.deleteOrganizationReportDetail(stateId, assessmentProgramId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle(), cpassOrganizationScoreReportReportType);
								externalStudentReportResultsProcessService.deleteExternalStudentReportResults(stateId, assessmentProgramId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle());								
						    }else{
								reponse.put("errorMessage", errorMessage);
								return reponse;
						    }
						}else{
							errorMessage = "Heading validation failed, please upload a correct file for selected options.";
							reponse.put("errorMessage", errorMessage);
							return reponse;
						} 
					}else if (csvOrXmlfileType.equalsIgnoreCase(uploadOrganizationPrctByAssessmentTopic)) {//Added for CPASS results upload
						//Validate File header and report cycle value
						if(validateFileHeader(csvOrXmlfileType, newFile)){
							errorMessage = validateReportcycle(newFile, request, csvOrXmlfileType, upload);
							if(errorMessage == null){
								uploadOrganizationReportDetailWriterProcessService.deleteOrganizationReportDetail(stateId, assessmentProgramId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle(),cpassSchoolDetailsReportType);								
								studentPrctByAssessmentTopicService.deleteStudentPrct(stateId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle());
								organizationPrctByAssessmentTopicService.deleteOrganizationPrct(stateId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle());
						    }else{
								reponse.put("errorMessage", errorMessage);
								return reponse;
						    }
						}else{
							errorMessage = "Heading validation failed, please upload a correct file for selected options.";
							reponse.put("errorMessage", errorMessage);
							return reponse;
						} 
					}else if (csvOrXmlfileType.equalsIgnoreCase(uploadStudentPrctByAssessmentTopic)) {//Added for CPASS results upload
						//Validate File header and report cycle value
						if(validateFileHeader(csvOrXmlfileType, newFile)){
							errorMessage = validateReportcycle(newFile, request, csvOrXmlfileType, upload);
							if(errorMessage == null){
								studentPrctByAssessmentTopicService.deleteStudentPrct(stateId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle());
								uploadOrganizationReportDetailWriterProcessService.clearOrganizationReportPDF(stateId, assessmentProgramId, contentAreaId, reportYear, upload.getTestingProgramId(), upload.getReportCycle(),cpassSchoolDetailsReportType, user.getId());
							}else{
								reponse.put("errorMessage", errorMessage);
								return reponse;
						    }
						}else{
							errorMessage = "Heading validation failed, please upload a correct file for selected options.";
							reponse.put("errorMessage", errorMessage);
							return reponse;
						} 
					}else if (csvOrXmlfileType.equalsIgnoreCase(studentDcpsReportsImportRecordType)){
						if (reportTypeFromFile.equalsIgnoreCase(studentDCPSReportsImportReportType)){
							externalReportImportService.deleteStudentDCPSFiles(assessmentProgramId, reportYear, stateId);
							externalReportImportService.deleteStudentDCPSRecords(assessmentProgramId, reportYear, stateId, assessmentProgram.getAbbreviatedname());
						}else{
							errorMessage = "The report type in the uploaded file ["+reportTypeFromFile+"] does not match the selected file type [Student Reports Import].";
							exitForNonMatchingReportType = true;
						}
					
					}
					
					if (exitForNonMatchingReportType){
						reponse.put("errorMessage", errorMessage);
						return reponse;
					}
				}
			}catch(IOException ioe){
				LOGGER.info("Exception deleteing files for: "+ csvOrXmlfileType+ "file type.  "+ ioe.getMessage());
			}
		}
		
		if("PROJ_TEST_RECORD_TYPE".equalsIgnoreCase(categoryCode)){			
			//Above based on assessmentprogram some delete operation happening
			upload.setAssessmentProgramId(userDetails.getUser().getCurrentAssessmentProgramId());
		}
		//Added for f430-US19339 to support scoring upload
		if("SCORING_RECORD_TYPE".equalsIgnoreCase(categoryCode)){
			CSVReader csvReader = null;
			try {
			 csvReader = new CSVReader(new BufferedReader(new FileReader(newFile)));
			 String [] nextLine;
			 int documentIDIndex = -1;
			 int lineNumber = 0;
		     while ((nextLine = csvReader.readNext()) != null) {
		        lineNumber++;
		        if(lineNumber == 1){
		        	for (int i = 0; i < nextLine.length; i++) {
		        		if ("Document ID".equalsIgnoreCase(nextLine[i].trim())) {
		        			documentIDIndex = i;
		        		}
		        	}
		        }else{
		        	if(nextLine[documentIDIndex] != null){
		        		try{
		        		   upload.setDocumentId(Long.parseLong(nextLine[documentIDIndex]));
				           break;
		        		}catch(Exception e){
		        			
		        		}
		        	}
		        }		        
		      }
		     csvReader.close();
			} catch (Exception e) {
				LOGGER.info("For Scoring upload, uploaded document is not correct :"+ e.getMessage());
				csvReader.close();
			}
			
		}
		
		
		/**
		 * Prasanth :  US16352 : To upload data file
		 */
		if( reportUpload == null || reportUpload )
			batchUploadService.updatePreviousToInactiveBatchUpload(assessmentProgramId, contentAreaId, fileTypeId, currentSchoolYear,reportYear,stateId, upload.getTestingProgramId(), upload.getReportCycle());
		
	
		if(assessmentProgram != null){
			upload.setAssessmentProgramName(assessmentProgram.getProgramName());
		}
		
		ContentArea contentArea =  contentAreaService.selectByPrimaryKey(contentAreaId);
		if(contentArea != null){
			upload.setContentAreaName(contentArea.getName());
		}

		Category uploadTypeCategory = categoryService.selectByPrimaryKey(fileTypeId);
		upload.setGrfProcessType(request.getParameter("grfUploadType"));
		upload.setUploadType(uploadTypeCategory.getCategoryCode());
		upload.setCreatedUserDisplayName(user.getDisplayName());
		

		if(permissionRecordType.equalsIgnoreCase(categoryCode)){
			String errorMessage = null;
			if(! validatePermissionsFileHeader(newFile)) {
				errorMessage = "Heading validation failed, please upload a correct file.";
				reponse.put("errorMessage", errorMessage);
				upload.setStatus(CommonConstants.UPLOAD_STATUS_FAILED);
				isHeaderValidationSuccess=false;
							}	
		}
		
		
		batchUploadService.insertBatchUpload(upload);
		
		//Added for F885 to support permissions upload
		if(permissionRecordType.equalsIgnoreCase(categoryCode) && isHeaderValidationSuccess){
			PermissionUploadFile permissionUploadFile=new  PermissionUploadFile();
				permissionUploadFile.setUploadId(upload.getId());
				permissionUploadFile.setHeaderColumn(headerJsonStr);
				permissionUploadFile.setCreatedUser(user.getId());
				permissionUploadFileService.insert(permissionUploadFile);
		}


		LOGGER.trace("Leaving the uploadFileData method.");
		/**
		 * Prasanth :  US16352 : To upload data file
		 */
		if( reportUpload != null && ! reportUpload)
			reponse.put("uploadId", upload.getId());
		
		//move temp file to s3 and delete the temp file
		s3.synchMultipartUpload(filePath, newFile);
		FileUtils.deleteQuietly(newFile);
		return reponse;
	}
	
	
	private String validateReportcycle(File newFile,
			MultipartHttpServletRequest request, String csvOrXmlfileType, BatchUpload upload) {
		try{
			String reportCycleFromFile = getFieldValueFromFile(newFile, "Report_Cycle");
			String reportCycleFromUI = null;
			if(request.getParameter("reportingCycleId") != null && StringUtils.isNotBlank(request.getParameter("reportingCycleId")) && Long.parseLong(request.getParameter("reportingCycleId")) > 0 ){
				TestingCycle testingCycle = batchReportProcessService.getTestingCycleByTestingProgramId(Long.parseLong(request.getParameter("reportingCycleId")));
				if(testingCycle != null){
					reportCycleFromUI = testingCycle.getTestingCycleName();
					Category reportCycleCategory = categoryService.selectByCategoryCodeAndType(currentCPASSReportCycleCategory,  reportCycleCategoryType);
					
					if(reportCycleCategory == null){
						return "Please set current CPASS Report Cycle Before Upload";
					}else if(!reportCycleCategory.getCategoryName().equalsIgnoreCase(reportCycleFromUI)){
						return "Selected report cycle (" + reportCycleFromUI + ")" + " is invalid. Expecting current report cycle value as " + reportCycleCategory.getCategoryName();
					}
				}
			}else{
				return "Please select Report Cycle";
			}
			
			if(StringUtils.isNotBlank(reportCycleFromFile) 
					&& reportCycleFromUI.equals(reportCycleFromFile)){
				upload.setReportCycle(reportCycleFromFile);
			}else{
				return "Report cycle specified in the file (" + reportCycleFromFile + ")" + " is invalid. Expecting current report cycle value as " + reportCycleFromUI;
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
			return "Something wrong in file";
		}
	}

	private boolean validateFileHeader(String csvOrXmlfileType, File newFile){
		Map<String, FieldSpecification> feildSpecMap = new HashMap<String, FieldSpecification>();
		feildSpecMap = uploadFileService.getFieldSpecificationRecordMap(csvOrXmlfileType, csvRecordTypeCode);
		String[] headNames;
		try {
			headNames = getFileHeaders(newFile);
			if(headNames != null){
				for(int i=0;i<headNames.length;i++){
					//trim heading from file
					headNames[i] = headNames[i].trim();
					if(headNames[i].equalsIgnoreCase("comment")){
						continue;
					}
					FieldSpecification fieldName = feildSpecMap.get(headNames[i].toLowerCase());
					if(fieldName == null){
						return false;
					}
				}
			}
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
			return false;
		}		
	}

	@RequestMapping(value = "checkDuplicateReportProcessing.htm", method = RequestMethod.POST)
	public final @ResponseBody int checkDuplicateReportProcessing(@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("subjectId") Long subjectId, @RequestParam("gradeId") Long gradeId, @RequestParam("process") String process) {
		
		return batchReportProcessService.selectDuplicateCountBatchReportProcess(assessmentProgramId, subjectId, gradeId, process);
	}
	
	@RequestMapping(value = "processReportData.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> processReportData(
			@RequestParam("assessmentReportPrograms") Long assessmentProgramId,
			@RequestParam("reportTestingPrograms") Long testingProgramId,
			@RequestParam("coursesReport") Long subjectId,
			@RequestParam("gradesReport") Long gradeId,
			@RequestParam(value = "generateSchoolAndDistrictReport", required = false) Boolean generateSchoolAndDistrictReport,
			@RequestParam(value = "generateStudentReport", required = false) Boolean generateStudentReport,
			@RequestParam(value = "generateSchoolFilesOfStudentReport", required = false) Boolean generateSchoolFilesOfStudentReport,
			@RequestParam(value = "generateDistrictFilesOfStudentReport", required = false) Boolean generateDistrictFilesOfStudentReport,
			@RequestParam(value = "batchrptstudid", required = false) Long batchrptstudid,
			@RequestParam(value = "studentCalc", required = false) Boolean studentCalc,
			@RequestParam(value = "checkPS", required = false) Boolean checkPS,
			@RequestParam(value = "processReport", required = false) Boolean processReport,
			@RequestParam(value = "processByStudentId", required = false) Boolean processByStudentId,
			@RequestParam(value = "generateForSpecificStudentOrAllInDistrict", required = false) String generateSpecificISROption,
			@RequestParam(value = "generateStudentSummaryBundledBySchool", required = false) Boolean generateStudentSummaryBundledBySchool,
			@RequestParam(value = "generateStudentSummaryBundledByDistrict", required = false) Boolean generateStudentSummaryBundledByDistrict,
			@RequestParam(value = "generateSchoolSummaryBundled", required = false) Boolean generateSchoolSummaryBundled)
			throws Exception {
		LOGGER.trace("Entering the processReportData method.");
		Map<String, Object> responseMap = new HashMap<String, Object>();
		ReportProcess report = new ReportProcess();
		if(subjectId == null && gradeId == null) {
			subjectId = (long) -1;
			gradeId = (long) -1;
		}
		
		if(assessmentProgramId != null && subjectId != null && gradeId != null && testingProgramId != null){
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Date now = new Date();
			String testingProgramName = StringUtils.EMPTY;
			
			boolean schoolDistrictReportFlag = (generateSchoolAndDistrictReport != null && generateSchoolAndDistrictReport);
			boolean processReportFlag = (processReport != null && processReport);
			boolean studentReportFlag = (generateStudentReport != null && generateStudentReport);
			boolean schoolFilesOfStudentReport = (generateSchoolFilesOfStudentReport != null && generateSchoolFilesOfStudentReport);
			boolean districtFilesOfStudentReport = (generateDistrictFilesOfStudentReport != null && generateDistrictFilesOfStudentReport);
            boolean onlyMedianCalc =  (studentCalc != null && !studentCalc);
            boolean enablePostScript =  (checkPS != null && checkPS);
            boolean processByStudentIdFlag = (processByStudentId != null && processByStudentId);
            boolean studentSummaryBundledReportBySchool = (generateStudentSummaryBundledBySchool != null && generateStudentSummaryBundledBySchool);
            boolean studentSummaryBundledReportByDistrict= (generateStudentSummaryBundledByDistrict != null && generateStudentSummaryBundledByDistrict);
            boolean schoolSummaryBundledReport = (generateSchoolSummaryBundled != null && generateSchoolSummaryBundled);
            
			String reportTypeCode="";
			List<String> reportTypeSummary = new ArrayList<String>();
			AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
			if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname()))
				reportTypeCode=cpassStudentBundledReportTypeCode;
			else if(assessmentProgram!=null && "DLM".equals(assessmentProgram.getAbbreviatedname()))
				reportTypeCode=alternateStudentBundledReportTypeCode;
			else if(assessmentProgram!=null && "KAP".equals(assessmentProgram.getAbbreviatedname()))
				reportTypeCode=generalStudentBundledReportTypeCode;		
			else if(assessmentProgram!=null && "KELPA2".equals(assessmentProgram.getAbbreviatedname()))
				reportTypeCode=kelpaStudentBundledReportTypeCode;	
			if(assessmentProgram!=null && "KAP".equals(assessmentProgram.getAbbreviatedname())){
				reportTypeSummary.add(generalSchoolSummaryReportTypeCode);	
				reportTypeSummary.add(generalDistrictSummaryReportTypeCode);	
			}
			
						
			report.setAssessmentProgramId(assessmentProgramId);
			report.setSubjectId(subjectId);
			report.setGradeId(gradeId);
			
			TestingProgram testingProgram = testingProgramService.getByTestingProgramId(testingProgramId);
			Category reportCycleCategory = null;
			String reportCycle = StringUtils.EMPTY;
			
			if(testingProgram != null){
				testingProgramName = testingProgram.getProgramName();
				report.setTestingProgramId(testingProgramId);
				report.setTestingProgramName(testingProgram.getProgramName());
				
				//for interim get reportcycle
				if(interimTestingProgram.equalsIgnoreCase(testingProgramName)){
					reportCycleCategory = categoryService.selectByCategoryCodeAndType(currentReportCycleCategory,  reportCycleCategoryType);
					if(reportCycleCategory != null){
						reportCycle = reportCycleCategory.getCategoryName();
						report.setReportCycle(reportCycleCategory.getCategoryName());
					}
				}
			}
						
			//for summative get reportcycle
			if("CPASS".equals(assessmentProgram.getAbbreviatedname()) && summativeTestingProgram.equalsIgnoreCase(testingProgramName)){//Summative
				reportCycleCategory = categoryService.selectByCategoryCodeAndType(currentCPASSReportCycleCategory,  reportCycleCategoryType);
				if(reportCycleCategory != null){
					reportCycle = reportCycleCategory.getCategoryName();
					report.setReportCycle(reportCycleCategory.getCategoryName());
				}				
			}
            
			StringBuffer processString = new StringBuffer();
			String reportType = StringUtils.EMPTY;
			
			if(processReportFlag){
				processString.append("Process data:");
			}
			if(processByStudentIdFlag){
				processString.append("Process for specific student:");
			}
			if(studentReportFlag){
				processString.append("Generate student report:");
			}
			if(schoolDistrictReportFlag){
				processString.append("Generate school and district reports:");
			}
			
			if(generateSpecificISROption != null){
				//1: Generate ISR for specific students loaded into studentreportreprocess table
				//2: Generate ISR for all students in the schools/districts of specific students loaded into studentreportreprocess table 
				if("1".equals(generateSpecificISROption)){
					processString.append("For specified student only:");
				}else if("2".equals(generateSpecificISROption)){
					processString.append("For all students in specified student's district:");
				}
			}
			
			if(schoolFilesOfStudentReport){
				processString.append("Generate school files of student reports:");
			}
			if(districtFilesOfStudentReport){
				processString.append("Generate district files of student reports:");
			}
			
			if(studentSummaryBundledReportBySchool){
				processString.append("Generate student summary(bundled) reports at school level:");
				reportType = REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL;
			}
			if(studentSummaryBundledReportByDistrict){
				processString.append("Generate student summary(bundled) reports at district level:");
				reportType = REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL;
			}
			if(schoolSummaryBundledReport){
				processString.append("Generate school summary(bundled) reports at district level:");
				reportType = REPORT_TYPE_SCHOOL_SUMMARY_BUNDLED_DT_LVL;
			}
			
			report.setProcess(processString.toString());
			report.setSubmissionDate(now);
			report.setCreatedUser(user.getId());
			report.setStatus("In Progress");
			report.setActiveFlag(true);
			batchReportProcessService.insertBatchReport(report);
			
			if(subjectId.equals(new Long(-1))){
				subjectId = null;
			} 
			if(gradeId.equals(new Long(-1))){
				gradeId = null;
			}						
			
			Organization contrOrg = organizationService.getContractingOrgByAssessmentProgramIdOrgId(assessmentProgramId, user.getContractingOrgId());
			Long schoolYear = (long) user.getContractingOrganization().getCurrentSchoolYear();
			
			if(interimTestingProgram.equalsIgnoreCase(testingProgramName) && contrOrg != null){
				schoolYear = contrOrg.getCurrentSchoolYear();
			}else{
				schoolYear = (long) contrOrg.getReportYear();
			}
			
			//Temporary logic start
			 if("CPASS".equals(assessmentProgram.getAbbreviatedname())){
				 schoolYear = organizationService.getReportYear(user.getContractingOrgId(), assessmentProgramId);
			 }
			//Temporary logic end
			 
			if(processByStudentIdFlag){
			
				if(studentReportFlag && generateSpecificISROption != null){
					//update flags for all students to generate isr for specific student or for all students in specified student's district 
					batchReportProcessService.updateStudentReportReprocessByStudentIdByIsrOption(assessmentProgramId, subjectId, gradeId, schoolYear, generateSpecificISROption, testingProgramId);
				}
				List<Long> studentIdList = batchReportProcessService.getStudentsForReportProcessByStudentId(assessmentProgramId, subjectId, gradeId, schoolYear, testingProgramId);
				if(CollectionUtils.isNotEmpty(studentIdList) && studentIdList.size() > 0){
					for(Long studentId: studentIdList){
						
						if(summativeTestingProgram.equalsIgnoreCase(testingProgramName)){//Summative
							batchReportProcessService.deleteReportSubscoresByStudentId(assessmentProgramId, subjectId, gradeId, studentId, schoolYear);
							if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname())){
								deleteExternalStudentReports(assessmentProgramId, subjectId, gradeId, schoolYear, reportCycle, testingProgramId, user.getContractingOrganization().getId(), externalStudentReportType,  user.getId());
							}else{
								deleteStudentReportProcess(assessmentProgramId, subjectId, null, studentId, schoolYear);	
							}							
							batchReportProcessService.deleteSpecificStudentReportTestScores(assessmentProgramId, subjectId, gradeId, studentId, schoolYear);
							batchReportProcessService.deleteTestLevelStudentSubscores(assessmentProgramId, subjectId, gradeId, studentId, schoolYear);
							batchReportProcessService.deleteSpecificStudentReport(assessmentProgramId, subjectId, gradeId, studentId, schoolYear);
							batchReportProcessService.deleteReportsPercentByLevel(assessmentProgramId, subjectId, gradeId, schoolYear);
							batchReportProcessService.deleteReportMedianScore(assessmentProgramId, subjectId, gradeId, schoolYear);
						
						}else if(interimTestingProgram.equalsIgnoreCase(testingProgramName)){//Interim
							deleteInterimPredictiveReports(assessmentProgramId, reportCycle, subjectId, gradeId, schoolYear, studentId);
						}
						
					}
				}
							
			}else{
				if(summativeTestingProgram.equalsIgnoreCase(testingProgramName)){
					if(processReportFlag){
						deleteCalcProcess(assessmentProgramId, subjectId, gradeId, onlyMedianCalc, schoolYear, reportTypeSummary, reportTypeCode);
					}
					if(schoolDistrictReportFlag){
						if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname())){
							batchReportProcessService.deleteExternalSchoolDetailReports(assessmentProgramId, user.getContractingOrganization().getId(), subjectId, gradeId, schoolYear, reportCycle, testingProgramId, cpassSchoolDetailsReportType,  user.getId());
						}else{
						   deleteSchoolDistrictReportProcess(assessmentProgramId, subjectId, gradeId, schoolYear, reportTypeSummary);
						}
					}
					if(studentReportFlag){
						if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname())){
							deleteExternalStudentReports(assessmentProgramId, subjectId, gradeId, schoolYear, reportCycle, testingProgramId, user.getContractingOrganization().getId(), externalStudentReportType,  user.getId());
						}else{
							deleteStudentReportProcess(assessmentProgramId, subjectId, gradeId, null, schoolYear);	
						}						
					}
					if(schoolFilesOfStudentReport){
						//deleteSchoolPdfZipReportProcess(assessmentProgramId, subjectId, gradeId, schoolYear);
						deleteSchoolPdfZipReportProcessForUserState(assessmentProgramId, subjectId, gradeId, schoolYear,user.getContractingOrganization().getId(),reportTypeCode);
					}
					if(districtFilesOfStudentReport){
						deleteDistrictPdfZipReportProcessForUserState(assessmentProgramId, subjectId, gradeId, schoolYear,user.getContractingOrganization().getId(),reportTypeCode);
					}
					if(studentSummaryBundledReportBySchool){
						deleteStudentSummaryBundledReportsAtSchoolLevel(assessmentProgramId, subjectId, gradeId, schoolYear, user.getContractingOrganization().getId(), REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);					
					}
					if(studentSummaryBundledReportByDistrict){
						deleteStudentSummaryBundledReportsAtDistrictLevel(assessmentProgramId, subjectId, gradeId, schoolYear, user.getContractingOrganization().getId(), REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
					}
					if(schoolSummaryBundledReport){
						deleteSchoolSummaryBundledReportsAtDistrictLevel(assessmentProgramId, subjectId, gradeId, schoolYear, user.getContractingOrganization().getId(), REPORT_TYPE_SCHOOL_SUMMARY_BUNDLED_DT_LVL);
					}
				}else if(interimTestingProgram.equalsIgnoreCase(testingProgramName)){//Interim
					//do not delete ISR calculations and reports
					
					//delete summary calcualtions and school/district summary reports
					if(schoolDistrictReportFlag){
						deletePredictiveSchoolDistrictSummary(assessmentProgramId, testingProgramId, reportCycle, schoolYear, subjectId, gradeId);
					}
				}
				
				
			}
			if(onlyMedianCalc){
				processReportFlag = false;
			}
			
			if(summativeTestingProgram.equalsIgnoreCase(testingProgramName)){
				Long jobId = batchReportProcessStarter.startBatchReportProcess(processReportFlag, onlyMedianCalc,  schoolDistrictReportFlag, studentReportFlag, 
						schoolFilesOfStudentReport, districtFilesOfStudentReport, assessmentProgramId, subjectId, gradeId, report.getId(), batchrptstudid, enablePostScript,
						user.getContractingOrganization().getId(), processByStudentIdFlag, generateSpecificISROption, studentSummaryBundledReportBySchool, 
						studentSummaryBundledReportByDistrict, schoolSummaryBundledReport, reportType, schoolYear, testingProgramId, reportCycle, user.getId());
				
			}else if(interimTestingProgram.equalsIgnoreCase(testingProgramName)){//Interim
				Long jobId = interimPredictiveReportProcessStarter.startPredictiveReportBatchProcess(assessmentProgramId, 
						subjectId, gradeId, testingProgramId, reportCycle, schoolYear, processReportFlag, studentReportFlag, schoolDistrictReportFlag, 
						report.getId(), batchrptstudid,	user.getContractingOrganization().getId(), processByStudentIdFlag, generateSpecificISROption, user.getId());
			}
			
		}
 		responseMap.put("status", "INPROCESS");
 		responseMap.put("reportProcessId", report.getId());
		LOGGER.trace("Leaving the processReportData method.");
		return responseMap;
	}
	
	@RequestMapping(value = "getTASCDataFromKSDE.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean getTASCDataFromKSDE()	throws Exception {
		batchKSDEGetDataStarter.setIsScheduleOn("ON");
		Long jobId =  batchKSDEGetDataStarter.run();
		 
		return true;
	}
	
	@RequestMapping(value = "getTASCProcess.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean getTASCProcess()	throws Exception {
		batchKSDEDataProcessStarter.setIsScheduleOn("ON");
		Long jobId =  batchKSDEDataProcessStarter.run();
		 
		return true;
	}
	 
	private void deleteCalcProcess(Long assessmentProgramId, Long subjectId, Long gradeId, Boolean onlyMedianCalc, Long schoolYear, List<String> reportTypeSummary, String reportType){
		if(!onlyMedianCalc){
			batchReportProcessService.deleteReportSubscores(assessmentProgramId, subjectId, gradeId, schoolYear);
		}
		batchReportProcessService.deleteReportsPercentByLevel(assessmentProgramId, subjectId, gradeId, schoolYear);
		batchReportProcessService.deleteReportMedianScore(assessmentProgramId, subjectId, gradeId, schoolYear);
		if(!onlyMedianCalc){
			deleteStudentReportProcess(assessmentProgramId, subjectId, gradeId, null, schoolYear);
		}
		deleteSchoolDistrictReportProcess(assessmentProgramId, subjectId, gradeId, schoolYear, reportTypeSummary);
		//this should be last one or after deleteStudentReportProcess
		if(!onlyMedianCalc){
			deleteSchoolPdfZipReportProcess(assessmentProgramId, subjectId, gradeId, schoolYear, reportType);
			batchReportProcessService.deleteSpecificStudentReportTestScores(assessmentProgramId, subjectId, gradeId, null, schoolYear);
			batchReportProcessService.deleteTestLevelStudentSubscores(assessmentProgramId, subjectId, gradeId, null, schoolYear);
			batchReportProcessService.deleteSpecificStudentReport(assessmentProgramId, subjectId, gradeId, null, schoolYear);
		}
	}
	
	private void deleteStudentReportProcess(Long assessmentProgramId, Long subjectId, Long gradeId, Long studentId, Long schoolYear){
		//deleteAllStudentReportFiles need to run first before report calc
		batchReportProcessService.deleteAllStudentReportFiles(assessmentProgramId, gradeId, subjectId, studentId, schoolYear);
	}
	
	private void deleteSchoolDistrictReportProcess(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, List<String> reportTypeSummary){
		//deleteAllOrganizationReportFiles need to run first before report calc
		batchReportProcessService.deleteAllOrganizationReportFiles(assessmentProgramId, gradeId, subjectId, schoolYear , reportTypeSummary);
		batchReportProcessService.deleteAllOrgsInOrganizationReportDetails(assessmentProgramId, subjectId, gradeId, schoolYear, reportTypeSummary);
	}
	
	private void deleteSchoolPdfZipReportProcess(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, String reportType){
		//deleteAllOrganizationReportFiles need to run first before report calc
		batchReportProcessService.deleteAllSchoolBundleReportFiles(assessmentProgramId, schoolYear, reportType);
		batchReportProcessService.deleteAllSchoolBundleReports(assessmentProgramId, schoolYear, reportType);
	}
	
	private void deleteSchoolPdfZipReportProcessForUserState(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear,Long organizationId ,String reportType){
		//deleteAllOrganizationReportFiles need to run first before report calc
		batchReportProcessService.deleteAllSchoolBundleReportFilesForUserState(assessmentProgramId, schoolYear,organizationId, reportType);
		batchReportProcessService.deleteAllSchoolBundleReportsForUserState(assessmentProgramId, schoolYear,organizationId , reportType);
	}
	private void deleteDistrictPdfZipReportProcessForUserState(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear,Long organizationId,String reportType){
		//deleteAllOrganizationReportFiles need to run first before report calc
		batchReportProcessService.deleteAllDistrictBundleReportFilesForUserState(assessmentProgramId, schoolYear,organizationId, reportType);
		batchReportProcessService.deleteAllDistrictBundleReportsForUserState(assessmentProgramId, schoolYear,organizationId, reportType);
	}
		
	@RequestMapping(value = "getReportProcessHistory.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ReportProcess> getReportProcessHistory(
			@RequestParam("reportFromDate") Date reportFromDate, 
			@RequestParam("reportToDate") Date reportToDate,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String orderByColumn,
			@RequestParam("sord") String order) throws ParseException {
		
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		
		int currentPage = NumericUtil.parse(page, 1);
		int limit = NumericUtil.parse(limitCountStr, 5);
		int offset = (currentPage - 1) * limit;
		LOGGER.debug("Entering getReportProcessHistory");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		List<AssessmentProgram> assessmentPrograms = assessmentProgramService.selectAssessmentProgramsForBatchReporting(userDetails.getUser().getCurrentOrganizationId());
		List<Long> apIds = new ArrayList<Long>();
		for(AssessmentProgram ap : assessmentPrograms)
		{
			apIds.add(ap.getId());
		}
		List<ReportProcess> reportHistory = new ArrayList<ReportProcess>();
		if(apIds.size()>0)
		{
			reportHistory = batchReportProcessService.getBatchReportingHistory(reportFromDate, reportToDate, orderByColumn, order, limit, offset, apIds);
			int totalCount = batchReportProcessService.countBatchReportingHistory(reportFromDate, reportToDate, apIds);
			jqGridJSONModel = BatchReportHistoryJson.convertToBatchReportHistoryJson(reportHistory,totalCount, currentPage, limit);
		}
		LOGGER.debug("Leaving getReportProcessHistory");
		return reportHistory;
	}
		
	@RequestMapping(value = "getReportsErrorFile.htm", method = RequestMethod.GET)
	public final void getReportsErrorFile(@RequestParam("reportId") Long reportId, 
			@RequestParam("fileName") String fileName,
			@RequestParam("gradeName") String gradeName,
			final HttpServletRequest request, 
			final HttpServletResponse response){

		String[] columnHeaders = 
		{
			"Assessment Program", "Subject", "Grade", "Student ID", "TestID 1", "TestID 2", "TestID 3", "TestID 4", "PerformanceTestID", "Error Message"
		};

		List<String[]> errorRecords = new ArrayList<String[]>();
		errorRecords.add(columnHeaders);
		
		String[] fileInfo = fileName.split("_");
		
		List<ReportProcessReason> reasons = batchReportProcessService.findReportProcessReasonsForId(reportId);
		
		for (ReportProcessReason reason : reasons) {
			String testID1 = "", testID2 = "", studentID="", reasonString = "", testID3 = "", testID4 = "", performanceTestID = "";
			if(reason.getTestId1()!=null)
				testID1 = reason.getTestId1()+"";
			
			if(reason.getTestId2()!=null)
				testID2 = reason.getTestId2()+"";
			
			if(reason.getTestId3() != null){
				testID3 = reason.getTestId3()+"";
			}
			
			if(reason.getTestId4() != null){
				testID4 = reason.getTestId4()+"";
			}
			
			if(reason.getPerformanceTestExternalId() != null){
				performanceTestID = reason.getPerformanceTestExternalId()+"";
			}
			
			if(reason.getStudentId()!=null)
				studentID = reason.getStudentId()+"";
			
			if(reason.getReason()!=null)
				reasonString = reason.getReason();
			
			errorRecords.add(new String[]{
				wrapForCSV(fileInfo[0]),
				wrapForCSV(fileInfo[1]),
				wrapForCSV(gradeName),
				wrapForCSV(studentID),
				wrapForCSV(testID1),
				wrapForCSV(testID2),
				wrapForCSV(testID3),
				wrapForCSV(testID4),
				wrapForCSV(performanceTestID),
				wrapForCSV(reasonString)
			});
		}

		CSVWriter csvWriter = null;
		String[] splitFileName = fileName.split("\\.");
		File reportErrorFile = null;	
		
		try {
			reportErrorFile = File.createTempFile(splitFileName[0], "."+splitFileName[1]);	
			csvWriter = new CSVWriter(new FileWriter(reportErrorFile, true), ',');
		   	csvWriter.writeAll(errorRecords);
		} catch (IOException ex) {
			LOGGER.error("IOException Occured:", ex);
		}
		finally {
        	if(csvWriter != null) {
        		try {
					csvWriter.close();
				} catch (IOException e) {
					LOGGER.error("IOException occured:", e);
				}
        	}
        }
 
		InputStream inputStream = null;
    	try {
			response.setContentType("application/force-download");
    		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            inputStream = new FileInputStream(reportErrorFile);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
    	}
		catch (FileNotFoundException e) {
			LOGGER.error("Batch report error file not Found. File - " + fileName);
			LOGGER.error("FileNotFoundException : ",e);
		}
    	catch (IOException e) {
			LOGGER.error("Error occurred while downloading error csv file. File - ." + fileName);
			LOGGER.error("IOException : ",e);
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
					FileUtils.deleteQuietly(reportErrorFile);
				} catch (IOException e) {
					LOGGER.error("ignore IOException closing: ",e);
				}
			}
		}
	}
	
	private String wrapForCSV(String s) {
		if (s != null && !s.isEmpty() && s.indexOf(',') > -1) {
			return s;
		}
		return s;
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
	
	@RequestMapping(value = "monitorBatchReportProcess.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> monitorBatchReportProcess(HttpServletRequest request, @RequestParam("reportProcessId") Long reportProcessId){
		LOGGER.trace("Entering monitorBatchReportProcess");
		Map<String, String> responseResult = new HashMap<String, String>();
		
		if(reportProcessId != null) {
			responseResult.put("status", batchReportProcessStarter.getJobStatus(reportProcessId));
		}
    	LOGGER.trace("Leaving monitorBatchReportProcess");
		return responseResult;
	}
	
	/**
	 * Prasanth :  US16352 : To monitor the status of uploaded data file     
	 */
	@RequestMapping(value = "monitorUploadFileStatus.htm", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> monitorUploadFileStatus(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uploadFileRecordId") Long uploadFileRecordId) throws Exception { 
		
		Map<String, Object> responseResult = new HashMap<String, Object>();
		
		BatchUpload batchUpload = batchUploadService.selectByPrimaryKeyBatchUpload(uploadFileRecordId);
		String status = batchUpload.getStatus();
		
		if (status.equalsIgnoreCase("COMPLETED") || status.equalsIgnoreCase("FAILED")) {
			
			responseResult.put("uploadedDetails", batchUpload);
			responseResult.put("uploadFileStatus", status);
			responseResult.put("uploadCompleted", true);
				
			if(batchUpload.getFailedCount() > 0  || batchUpload.getSuccessCount() == 0 ){
				List<BatchUploadReason> batchUploadReasons = batchUploadService.find100BatchUploadReasonsForId(uploadFileRecordId);
				Long organizationId = batchUpload.getStateId();
				
				if( batchUpload.getSchoolId() != null && batchUpload.getSchoolId() > 0 )
					organizationId = batchUpload.getSchoolId();
				else if( batchUpload.getDistrictId() != null && batchUpload.getDistrictId() > 0)
					organizationId = batchUpload.getDistrictId();
				
				List<Organization> orgs = organizationService.getAllParents(organizationId);
				Organization selectedOrgs = organizationService.get(organizationId);
				orgs.add(selectedOrgs);
				StringBuilder organizationName = new StringBuilder();
				
				/*sorted based on organization level to form file name as State_district_school */
				Collections.sort(orgs, new Comparator<Organization>() {
	                @Override
	                public int compare(Organization o1, Organization o2) {
	                    return o1.getTypeLevel() - o2.getTypeLevel();
	                }
	            });
				int index = 0;
				for(Organization org:orgs)
				{
					if(index++ >  0 ){
						organizationName.append(org.getOrganizationName());
						organizationName.append("_");
					}
				}
				responseResult.put("uploadErrors", batchUploadReasons);
				responseResult.put("organizationName", organizationName.toString());
			}
			
		}
		else{ 
			responseResult.put("uploadFileStatus", "IN_PROGRESS");
			responseResult.put("status",status);
		}
		return responseResult;
	}
	/**
	 * Prasanth :  US16252 : To download the uploaded error details in a csv file     
	 */
	@RequestMapping(value = "getUploadErrorFile.htm", method = RequestMethod.GET)
	public final void getUploadErrorFile(@RequestParam("uploadedId") Long uploadedId, 
			@RequestParam("fileName") String fileName,
			final HttpServletRequest request, 
			final HttpServletResponse response){

		String[] columnHeaders = 
		{
			"Identifier", "Reasons For Not Valid","Reasons For Alert"
		};

		List<String[]> errorRecords = new ArrayList<String[]>();
		errorRecords.add(columnHeaders);
		
		//String fileName = "Userupload_failed_records_reasons.csv"; 
		fileName = fileName.replaceAll(" ", "_");
		
		List<BatchUploadReason> reasons = batchUploadService.findBatchUploadReasonsForId(uploadedId);
		
		//added during US16966 - To add alert message to upload
		for (BatchUploadReason reason : reasons) {
			String reasonString = "";
			
			if(reason.getReason()!=null)
				reasonString = reason.getReason();
			
			String [] msgs = null;
			String errMsg = reasonString; 
			if(reasonString.indexOf("###") > 0 ){
				msgs = reasonString.split("###");
				errMsg = msgs[1]; 
			}
			if(!"alert".equals(reason.getErrorType())){  
				errorRecords.add(new String[]{
					wrapForCSV("Line number : " + reason.getLine() +" Field Name : " + reason.getFieldName()),
					wrapForCSV(errMsg)
				});
			}else{
				errorRecords.add(new String[]{
						wrapForCSV("Line number : " + reason.getLine() +" Field Name : " + reason.getFieldName()),
						wrapForCSV(""),
						wrapForCSV(errMsg)
					});
			}
		}

		CSVWriter csvWriter = null;
		String[] splitFileName = fileName.split("\\.");
		File errorFile = null;	
		
		try {
			errorFile = File.createTempFile(splitFileName[0], "."+splitFileName[1]);	
			csvWriter = new CSVWriter(new FileWriter(errorFile, true), ',');
		   	csvWriter.writeAll(errorRecords);
		} catch (IOException ex) {
			LOGGER.error("IOException Occured:", ex);
		}
		finally {
        	if(csvWriter != null) {
        		try {
					csvWriter.close();
				} catch (IOException e) {
					LOGGER.error("IOException occured:", e);
				}
        	}
        }
 
		InputStream inputStream = null;
    	try {
			response.setContentType("application/force-download");
    		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            inputStream = new FileInputStream(errorFile);
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
    	}
		catch (FileNotFoundException e) {
			LOGGER.error("Batch Data upload error file not Found. File - " + fileName);
			LOGGER.error("FileNotFoundException : ",e);
		}
    	catch (IOException e) {
			LOGGER.error("Error occurred while downloading error csv file. File - ." + fileName);
			LOGGER.error("IOException : ",e);
		} finally {
			if(inputStream != null) {
				try {
					inputStream.close();
					FileUtils.deleteQuietly(errorFile);
				} catch (IOException e) {
					LOGGER.error("ignore IOException closing: ",e);
				}
			}
		}
	}	
	
	@RequestMapping(value = "getAssessmentProgramsOfUser.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssessmentProgram> getAssessmentProgramsOfUser(){
		LOGGER.trace("Entering getAssessmentProgramsOfUser");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		List<AssessmentProgram> userAssessmentPrograms = assessmentProgramService.getAllAssessmentProgramByUserId(userDetails.getUser().getId());
		Collections.sort(userAssessmentPrograms, assmntPgmComparator);
		
		LOGGER.trace("Leaving getAssessmentProgramsOfUser");
		return userAssessmentPrograms;
	}
	
	
	@RequestMapping(value = "getReportNames.htm", method = RequestMethod.POST)
	public final @ResponseBody List<Category> getReportNames(){
		LOGGER.trace("Entering getReportNames");		
		List<Category> reports = reportService.getReports();		
		LOGGER.trace("Leaving getReportNames");
		return reports;
	}
	
	@RequestMapping(value = "getReadyToViewFlagsForReports.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ReportAssessmentProgram> getReadyToViewFlagsForReports(){
		LOGGER.trace("Entering getReadyToViewFlagsForReports");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Long orgId = userDetails.getUser().getContractingOrgId();
		String orgType = user.getCurrentOrganizationType();
		Long userCurrentRoleId = user.getCurrentGroupsId();	
		List<ReportAssessmentProgram> reports = reportService.getReadyToViewFlagsForReports(userCurrentRoleId,orgId, orgType);
		LOGGER.trace("Leaving getReadyToViewFlagsForReports");
		return reports;
	}
	
	/* Added for US16548*/
	@RequestMapping(value = "getabUploadProgressStatus.htm", method = RequestMethod.POST)
	public @ResponseBody List<BatchUploadInfo> getUploadProgressStaus(HttpServletRequest request, HttpServletResponse response,
	  		@RequestParam("categoryCode") String categoryCode) throws Exception {
		
		Map<String, List> responseResultInfo = new HashMap<String, List>();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		List<BatchUploadInfo> batchUploadInfo = batchUploadService.selectByCategoryCodeBatchUpload(user.getId(),categoryCode,user.getCurrentGroupsId(),user.getCurrentOrganizationId());
		responseResultInfo.put("uploadFileInfo", batchUploadInfo);
		return batchUploadInfo;
	}	
	
	
	@RequestMapping(value = "getReportAccessData.htm", method = RequestMethod.POST)
	 public final @ResponseBody Map<String, Object> getReportAccessData(
	    		@RequestParam("reportAccessAssessmentProgramId") Long assessmentPrgId,
	    		@RequestParam(value="reportAccessStateIds[]", required=false)  Long [] stateIds,
	    		@RequestParam("reportType") String reportCategoryType,
	    		@RequestParam("rows") String limitCountStr,
	    		@RequestParam("page") String page,
	    		@RequestParam("sidx") String sortByColumn,
	    		@RequestParam("sord") String sortType,    		
	    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
		Map<String, Object> reportAccessMap = new HashMap<String, Object>();
		List<String> dynamicRoleColumnName = new ArrayList<String>();
		List<String> dynamicRoleColumnCode = new ArrayList<String>();
		List<Object> columnModelLists = new ArrayList<Object>();
		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;

		Map<String, String> testSessionRecordCriteriaMap = constructRecordFilterCriteria(filters);
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		List<Long> stateId = Arrays.asList(stateIds);
		List<ReportAccessDTO> reportAccess = new ArrayList<ReportAccessDTO>();
		List<String> reportCode = new ArrayList<String>();
		List<String> toRemoveNonGeneralReport = new ArrayList<String>();
		if (StringUtils.equals(reportCategoryType, CommonConstants.GENERAL_REPORT_ACCESS)) {
			AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentPrgId);
			if (StringUtils.equals(assessmentProgram.getAbbreviatedname(), CommonConstants.ASSESSMENT_PROGRAM_DLM)) {
				reportCode = appConfigurationService.getByAttributeType(CommonConstants.GENERAL_REPORT_ACCESS_FOR_DLM);
			} else {				
				toRemoveNonGeneralReport = appConfigurationService.getByAttributeType(CommonConstants.GENERAL_REPORT_ACCESS);
			}
		} else if (StringUtils.equals(reportCategoryType, CommonConstants.INSTRUCTIONALlY_EMBEDDED_REPORT_ACCESS)) {
			reportCode = appConfigurationService.getByAttributeType(CommonConstants.INSTRUCTIONALlY_EMBEDDED_REPORT_ACCESS);
			
		} else if (StringUtils.equals(reportCategoryType, CommonConstants.YEAR_END_REPORT_ACCESS)) {
			reportCode = appConfigurationService.getByAttributeType(CommonConstants.YEAR_END_REPORT_ACCESS);
			
		} else if (StringUtils.equals(reportCategoryType, CommonConstants.STUDENT_ARCHIEVE_REPORT_ACCESS)) {
			reportCode = appConfigurationService.getByAttributeType(CommonConstants.STUDENT_ARCHIEVE_REPORT_ACCESS);
			
		} else if (StringUtils.equals(reportCategoryType, CommonConstants.EXTRACTS_ACCESS)) {
			reportAccess = reportService.getExtractAccessData(assessmentPrgId, stateId, testSessionRecordCriteriaMap,
					sortType, sortByColumn);
		}

		if (reportCode.size() > 0 || toRemoveNonGeneralReport.size()>0) {
			reportAccess = reportService.getReportAccessData(assessmentPrgId, stateId, testSessionRecordCriteriaMap,
					sortType, sortByColumn, toRemoveNonGeneralReport, reportCode);
		}
		
		totalCount = reportAccess.size();
		List<Groups> groups = groupsService.getAllGroupsForReport();

		for (Groups group : groups) {
			dynamicRoleColumnName.add(group.getGroupName());
			dynamicRoleColumnCode.add(group.getGroupCode());
		}

		List<Object> totalLists = setGridData(reportAccess, dynamicRoleColumnCode);

		for (Groups column : groups) {
			Map<Object, Object> colmodal = new HashMap<Object, Object>();
			colmodal.put("label", column.getGroupName());
			colmodal.put("name", column.getGroupCode());
			colmodal.put("index", column.getGroupCode());
			colmodal.put("search", false);
			colmodal.put("sortable", false);
			colmodal.put("hidden", false);
			colmodal.put("hidedlg", true);
			colmodal.put("width", 120);
			colmodal.put("formatter", "checkRolesHaveAccess");
			columnModelLists.add(colmodal);
		}

		reportAccessMap.put("colNames", dynamicRoleColumnName);
		reportAccessMap.put("colModel", columnModelLists);
		reportAccessMap.put("rows", totalLists);
		reportAccessMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		reportAccessMap.put("records", totalCount);
		reportAccessMap.put("page", currentPage);

		return reportAccessMap;

	}
	
	private List<Object> setGridData(List<ReportAccessDTO> reportAccess, List<String> dynamicColumnCode) {
		
		List<Object> totalLists = new ArrayList<>();		
		if (reportAccess != null && reportAccess.size() > 0) {
			int index = 1;
			List<Integer> comparedValue = new ArrayList<Integer>();
			for (int primaryRow = 0; primaryRow < reportAccess.size(); primaryRow++) {
				Map<Object, Object> reportAccessMapList = new HashMap<Object, Object>();
				if (!comparedValue.contains(primaryRow)) {
					String accessPermStatus = CommonConstants.EMPTY;
					String reportAssPgmIds = String.valueOf(reportAccess.get(primaryRow).getId());
					String stateIds = String.valueOf(reportAccess.get(primaryRow).getStateId());
					String authorityId = String.valueOf(reportAccess.get(primaryRow).getAuthorityId());
					String reportName = reportAccess.get(primaryRow).getReportName();
					String subjects = reportAccess.get(primaryRow).getSubject() == null ? "" : reportAccess.get(primaryRow).getSubject();
					String orgName = reportAccess.get(primaryRow).getOrganizationName();
					List<String> rolesHaveAccessList = new ArrayList<String>(Arrays.asList(reportAccess.get(primaryRow).getRolesAccess().split(CommonConstants.SEPARATOR)));
					List<String> rolesHavePermList = new ArrayList<String>(Arrays.asList(reportAccess.get(primaryRow).getRolesHavePerm().split(CommonConstants.SEPARATOR)));

					Map<String, String> reportAccessDynamicColumn = new HashMap<String, String>();
					for (String reportColumnName : dynamicColumnCode) {
						accessPermStatus = reportAccess.get(primaryRow).getOrganizationName().concat(CommonConstants.PERM_ACCESS_STATUS_ZERO);
						if (rolesHavePermList.contains(reportColumnName)) {
							accessPermStatus = reportAccess.get(primaryRow).getOrganizationName().concat(CommonConstants.PERM_ACCESS_STATUS_ONE);
							if (rolesHaveAccessList.contains(reportColumnName)) {
								accessPermStatus = reportAccess.get(primaryRow).getOrganizationName().concat(CommonConstants.PERM_ACCESS_STATUS_TWO);
							}
						}
						reportAccessDynamicColumn.put(reportColumnName, accessPermStatus.concat((CommonConstants.COLON).concat(reportColumnName)));
					}

					String rolesHaveAccess = reportAccess.get(primaryRow).getRolesAccess();

					for (int comparingRow = primaryRow + 1; comparingRow < reportAccess.size(); comparingRow++) {

						if (reportAccess.get(primaryRow).getReporttypeId().equals(reportAccess.get(comparingRow).getReporttypeId())
								&& reportAccess.get(primaryRow).getAuthorityId().equals(reportAccess.get(comparingRow).getAuthorityId())) {
							if ((reportAccess.get(primaryRow).getSubjectId() != null	
									&& reportAccess.get(comparingRow).getSubjectId() != null
									&& reportAccess.get(primaryRow).getSubjectId().equals(reportAccess.get(comparingRow).getSubjectId())) 									
									|| (reportAccess.get(primaryRow).getSubjectId() == null	&& reportAccess.get(comparingRow).getSubjectId() == null)) {

								comparedValue.add(comparingRow);
								reportAssPgmIds = reportAssPgmIds.concat((CommonConstants.SEPARATOR).concat(String.valueOf(reportAccess.get(comparingRow).getId()))); 
								stateIds = stateIds.concat((CommonConstants.SEPARATOR).concat(String.valueOf(reportAccess.get(comparingRow).getStateId())));
								orgName = orgName.concat((CommonConstants.SEPARATOR).concat(reportAccess.get(comparingRow).getOrganizationName()));
								List<String> rolesHaveAccessList1 = new ArrayList<String>(Arrays.asList(reportAccess.get(comparingRow).getRolesAccess().split(CommonConstants.SEPARATOR)));
								List<String> rolesHavePermList1 = new ArrayList<String>(Arrays.asList(reportAccess.get(comparingRow).getRolesHavePerm().split(CommonConstants.SEPARATOR)));

								for (String reportColumnName : dynamicColumnCode) {
									accessPermStatus = reportAccess.get(comparingRow).getOrganizationName().concat(CommonConstants.PERM_ACCESS_STATUS_ZERO);
									if (rolesHavePermList1.contains(reportColumnName)) {
										accessPermStatus = reportAccess.get(comparingRow).getOrganizationName().concat(CommonConstants.PERM_ACCESS_STATUS_ONE);
										if (rolesHaveAccessList1.contains(reportColumnName)) {
											accessPermStatus = reportAccess.get(comparingRow).getOrganizationName().concat(CommonConstants.PERM_ACCESS_STATUS_TWO);
										}
									}
									if (reportAccessDynamicColumn.containsKey(reportColumnName)) {
										Object value = reportAccessDynamicColumn.get(reportColumnName);
										reportAccessDynamicColumn.put(reportColumnName,((String) value).concat(CommonConstants.SEPARATOR) + accessPermStatus.concat(CommonConstants.COLON) + reportColumnName);
									}
								}
							}
						}
					}
					reportAccessMapList.put("id", reportAssPgmIds);
					reportAccessMapList.put("stateId", stateIds);
					reportAccessMapList.put("orgName", orgName);
					reportAccessMapList.put("authorityId", authorityId);
					reportAccessMapList.put("reportName", reportName);
					reportAccessMapList.put("subject", subjects);
					reportAccessMapList.put("rolesHaveAccess", rolesHaveAccess);
					reportAccessMapList.put("rowIndex", index);
					reportAccessMapList.putAll(reportAccessDynamicColumn);
					totalLists.add(reportAccessMapList);
					index++;
				}
				
			}
		}
		
		return totalLists;
	}

	private Map<String,String> constructRecordFilterCriteria(String filters) {
    	Map<String,String> recordCriteriaMap = new HashMap<String, String>();
    	if(null != filters && !filters.equals("")) {
	    	RecordBrowserFilter recordBrowserFilter = null;
	    	//Parse the column filter values which the user enters on the UI record browser grid.
	        try {
	        	recordBrowserFilter = mapper.readValue(filters, 
	        			new TypeReference<RecordBrowserFilter>() {});
	        } catch(JsonParseException e) {
	        	LOGGER.error("Couldn't parse json object.", e);
	        } catch (JsonMappingException e) {
	        	LOGGER.error("Unexpected json mapping", e);
	        } catch (SecurityException e) {
	        	LOGGER.error("Unexpected exception with reflection", e);
	        } catch (IllegalArgumentException e) {
	        	LOGGER.error("Unexpected exception with reflection", e);
	        } catch (Exception e) {
	        	LOGGER.error("Unexpected error", e);
	        }
	        
	    	
	    	if(recordBrowserFilter != null) {
		    	for(RecordBrowserFilterRules recordBrowserFilterRules : recordBrowserFilter.getRules()) {
		    		recordCriteriaMap.put(recordBrowserFilterRules.getField(), 
		    				CommonConstants.PERCENTILE_DELIM + recordBrowserFilterRules.getData().trim() + CommonConstants.PERCENTILE_DELIM);	    		
		    	}
	    	}
    	}
    	return recordCriteriaMap;
    }
	
	
	@RequestMapping(value = "editReportAccessData.htm", method = RequestMethod.POST)
	 public final @ResponseBody boolean getEditReportAccessData(
			 @RequestParam("reportAssessmentProgramId") Long editReportAccessId,
			 @RequestParam(value="rolesId[]", required=false)  Long [] rolesId
			 ) throws NoAccessToResourceException {
		
		boolean editReportResult = reportService.getEditReportAccessData(editReportAccessId,rolesId);
		
		return editReportResult;
		
	}


	@RequestMapping(value = "editReportAccessDataForMultipleState.htm", method = RequestMethod.POST)
	 public final @ResponseBody boolean editReportAccessDataForMultipleState(
			 @RequestParam("reportAssessmentProgramId[]") String [] editReportAccessId,
			 @RequestParam(value="groupCode")  String groupCode,
			 @RequestParam(value="activeFlag") Boolean activeFlag
			 ) throws NoAccessToResourceException {
	        
		Groups groups = groupsService.getGroupByCode(groupCode);		 
		boolean editReportResult = reportService.editReportAccessDataForMultipleState(editReportAccessId, groups.getGroupId(), activeFlag);
		return editReportResult;
		
	}
	
	
	
	@RequestMapping(value = "editExtractAccessDataForMultipleState.htm", method = RequestMethod.POST)
	 public final @ResponseBody boolean editExtractAccessDataForMultipleState(
			 @RequestParam("extractAssessmentProgramId[]") String [] editReportAccessId,
			 @RequestParam(value="groupCode")  String groupCode,
			 @RequestParam(value="activeFlag") Boolean activeFlag
			 ) throws NoAccessToResourceException {
	        
		Groups groups = groupsService.getGroupByCode(groupCode);
		boolean editReportResult = reportService.editExtractAccessDataForMultipleState(editReportAccessId, groups.getGroupId(), activeFlag);
		return editReportResult;
		
	}
	
	@RequestMapping(value = "getReportAllRoleNamesSelectedPermission.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<Long,String> getAllGroupsSelectedPermission(
			@RequestParam("groupAuthoirityId")Long groupAuthoirityId,
			@RequestParam("assessmentProgId") Long assessmentProgId,
			@RequestParam("stateId")Long stateId){
		
		return reportService.getAllGroupsSelectedPermission(groupAuthoirityId,assessmentProgId,stateId);
	}
	
	@RequestMapping(value = "getReportRoleNamesSelectedPermission.htm", method = RequestMethod.POST)
	public final @ResponseBody List<Long> getGroupNameSelectedPermission(
			@RequestParam("reportassessmentgroupid") Long reportassessmentgroupid){
	
		return reportService.getGroupNameSelectedPermission(reportassessmentgroupid);
	}
	
	private void deleteStudentSummaryBundledReportsAtSchoolLevel(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear,Long organizationId, String reportType){		
		batchReportProcessService.deleteAllStudentSummaryBundleReportFiles(assessmentProgramId, schoolYear,organizationId, reportType);
		batchReportProcessService.deleteAllStudentSummaryBundleReports(assessmentProgramId, schoolYear,organizationId, reportType);
	}
	
	private void deleteStudentSummaryBundledReportsAtDistrictLevel(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear,Long organizationId, String reportType){		
		batchReportProcessService.deleteAllDistrictBundleReportFilesForStudentSummary(assessmentProgramId, schoolYear,organizationId, reportType);
		batchReportProcessService.deleteAllDistrictBundleReportsForStudentSummary(assessmentProgramId, schoolYear, organizationId, reportType);
	}
	
	private void deleteSchoolSummaryBundledReportsAtDistrictLevel(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear,Long organizationId, String reportType){		
		batchReportProcessService.deleteAllDistrictBundleReportFilesForSchoolSummary(assessmentProgramId, schoolYear,organizationId, reportType);
		batchReportProcessService.deleteAllDistrictBundleReportsForSchoolSummary(assessmentProgramId, schoolYear, organizationId, reportType);
	}
	 @RequestMapping(value="getStateCode.htm", method=RequestMethod.GET)
	 public final @ResponseBody String getStateCode(@RequestParam("stateId")Long stateId){		 
		String stateCode = organizationService.getStateCodeByStateId(stateId);
		 return stateCode;
	 }
	 @RequestMapping(value = "getCurrentSchoolYearForUploadResults.htm", method = RequestMethod.GET)
	   public final @ResponseBody List<Long> getCurrentSchoolYearForUploadResults(@RequestParam("orgId") Long orgId) {
	    UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
	     .getContext().getAuthentication().getPrincipal();
	         
	    Long orgStateId = userDetails.getUser().getOrganizationId();
	     List<Long> reponse =  new ArrayList<Long>();
	     int year = getCurrentSchoolYear(orgId);
	     reponse.add((long)(year-1));
	     reponse.add((long)year);
	   return reponse;
	   }
	   
	   private int getCurrentSchoolYear(Long orgId) {
	      Calendar cal = Calendar.getInstance();
	      Date currentSchoolYear = new Date();
	        Integer year = null;
	        Organization org = organizationService.get(orgId);
	      
	        if(org != null){
	       currentSchoolYear = org.getSchoolEndDate(); 
	       if(currentSchoolYear != null){
	       cal.setTime(currentSchoolYear);
	         year = cal.get(Calendar.YEAR);
	       }
	        }
	        return year;
	     }
	   
	   
		@RequestMapping(value = "getReportResultsUploads.htm", method = RequestMethod.POST)
		public final @ResponseBody Map<String, Object> getReportResultsUploads(
				@RequestParam("rows") String limitCountStr,
				@RequestParam("page") String page,
				@RequestParam("sidx") String orderByColumn,
				@RequestParam("sord") String order) {
			Map<String, Object> reportUpload = new HashMap<String, Object>();
			int currentPage = NumericUtil.parse(page, 1);
			int limitCount = NumericUtil.parse(limitCountStr, 5);
			int offset = (currentPage - 1) * limitCount;
			int totalCount = 0;
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
			
			List<Organization> userOrganizations=userDetails.getUser().getOrganizations();
			List<Long> userStates=new ArrayList<>();
			if(userOrganizations!=null){
			for(Organization organization:userOrganizations){
				if(organization.getTypeCode()!=null && StringUtils.equalsIgnoreCase(organization.getTypeCode(),"ST")){
					userStates.add(organization.getId());
					}
				}
			}
			
			List<BatchUpload> uploads = batchUploadService.selectuploadResultsByAssessmentProgramIdsAndFiltersBatchUpload(assessmentProgramId,
					userStates,orderByColumn, order, limitCount, offset);
			for(BatchUpload upload : uploads){
				if(StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadIncidentFileType)){
					upload.setUploadType("Incident File");
				}else if(StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadKansasScCodeFileType)||StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadCommonScCodeFileType)){
					upload.setUploadType("Special Circumstances");	

				}else if(StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadCommonGrfFileType)
						||StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadIowaGrfFileType)
						||StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadNewYorkGrfFileType)
						||StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadDelawareGrfFileType)
						||StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadDCGrfFileType)
						||StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadArkansasGrfFileType)){

					upload.setUploadType("General Research File");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), classroomReportsImportRecordType)){
					upload.setUploadType("Classroom Reports Import");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), classroomCsvReportsImportRecordType)){
					upload.setUploadType("Classroom CSV Reports Import");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), schoolReportsImportRecordType)){
					upload.setUploadType("School Reports Import");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), schoolCsvReportsImportRecordType)){
					upload.setUploadType("School CSV Reports Import");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), studentReportsImportRecordType)){
					upload.setUploadType("Student Reports Import");
				}else if(StringUtils.equalsIgnoreCase(upload.getUploadType(), studentDcpsReportsImportRecordType)){
					upload.setUploadType("Student DCPS Reports Import");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), studentSummaryReportsImportRecordType)){
					upload.setUploadType("Student Summary Reports Import");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadStudentScoreCaluculations)){
					upload.setUploadType("Student Scale Scores");
				}else if (StringUtils.equalsIgnoreCase(upload.getUploadType(), uploadOrganizationScoreCaluculations)){
					upload.setUploadType("Organization Scores");
				}else if(StringUtils.equalsIgnoreCase(upload.getUploadType(),uploadStudentPercentCorrectForAssessmentTopic)){
					upload.setUploadType("Student Topic Results");
				}else if(StringUtils.equalsIgnoreCase(upload.getUploadType(),uploadOrganizationPercentCorrectForAssessmentTopic)){
					upload.setUploadType("Organization Topic Results");
				}
				upload.setReasons(batchUploadService.findBatchUploadReasonsForId(upload.getId()));
				upload.setStateName(organizationService.getStateNameByStateId(upload.getStateId()));
			}
			if(uploads.size()>0){
			  totalCount = uploads.get(0).getTotalRecords();
			}
			if(orderByColumn.equals("uploadtype") && order.equals("asc")){
			Collections.sort(uploads, new Comparator<BatchUpload>() {
				@Override
				public int compare(BatchUpload o1, BatchUpload o2) {
					// TODO Auto-generated method stub
					return o1.getUploadType().compareTo(o2.getUploadType());
				}
			});
			} else if(orderByColumn.equals("uploadtype") && order.equals("desc")){
			Collections.sort(uploads, new Comparator<BatchUpload>() {
				@Override
				public int compare(BatchUpload o1, BatchUpload o2) {
					// TODO Auto-generated method stub
					return o2.getUploadType().compareTo(o1.getUploadType());
				}
			});
			}
			
			reportUpload.put("rows", uploads);
			reportUpload.put("total", NumericUtil.getPageCount(totalCount, limitCount));
			reportUpload.put("page", currentPage);
			reportUpload.put("records", totalCount);
			return reportUpload;
		}
		
		
		@RequestMapping(value = "checkForInProgressUpload.htm", method = RequestMethod.POST)
		public final @ResponseBody int checkForInProgressUpload(@RequestParam("assessmentProgramId") Long assessmentProgramId,
								                                @RequestParam("fileTypeId") String fileTypeId,
				                                                @RequestParam(value = "reportYear", required = false) Long reportYear,
				                                                @RequestParam("stateId") Long stateId,
				                                                @RequestParam(value = "testingProgramId" , required = false) Long testingProgramId,
				                                                @RequestParam(value = "reportingCycleId", required = false) Long reportingCycleId,
				                                                @RequestParam(value = "contentAreaId", required = false) Long contentAreaId
				) {
			TestingCycle testingCycle=new TestingCycle();
			
			 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 Organization state = userDetails.getUser().getContractingOrganization();
			if(reportingCycleId!=null)
			 testingCycle = batchReportProcessService.getTestingCycleByTestingProgramId(reportingCycleId);
			
			if(reportYear == null){
			    reportYear = organizationService.getReportYear(stateId, assessmentProgramId);
			    if(reportYear == null)
			       reportYear = (long) userDetails.getUser().getContractingOrganization().getReportYear();
			}
			
			if(uploadCommonGrfFileType.equals(fileTypeId)){

				if("NY".equalsIgnoreCase(state.getDisplayIdentifier())) {
					fileTypeId = uploadNewYorkGrfFileType;					
				}
				
				if("IA".equalsIgnoreCase(state.getDisplayIdentifier()))  {
						fileTypeId = uploadIowaGrfFileType;					
				}

				if("DE".equalsIgnoreCase(state.getDisplayIdentifier()))  {
						fileTypeId = uploadDelawareGrfFileType;						
				}
				
				if("DC".equalsIgnoreCase(state.getDisplayIdentifier()))  {
					fileTypeId = uploadDCGrfFileType;						
				}

				if("AR".equalsIgnoreCase(state.getDisplayIdentifier()))  {
						fileTypeId = uploadArkansasGrfFileType;				
				}		
			}
			if(CommonConstants.DLM_SPECIAL_CIRCUMSTANCE.equals(fileTypeId) || CommonConstants.DLM_EXITED_STUDENTS.equals(fileTypeId)){
				return batchUploadService.checkForInProgressInSpecialCircumstanceAndExitedStudents(assessmentProgramId, stateId, reportYear.intValue(), fileTypeId);
			}
			Category category= categoryService.selectByCategoryCodeAndType(fileTypeId, "CSV_RECORD_TYPE");
			return batchUploadService.checkForInProgressUpload(assessmentProgramId, stateId, category.getId(), reportYear.intValue(),contentAreaId,testingProgramId,testingCycle!=null? testingCycle.getTestingCycleName():null);
		}
		
		@RequestMapping(value = "checkForInProgressGrfUploadOrReport.htm", method = RequestMethod.POST)
		public final @ResponseBody int checkForInProgressGrfUploadOrReport(@RequestParam("assessmentProgramId") Long assessmentProgramId,
				                                                @RequestParam(value = "reportYear", required = false) Long reportYear,
				                                                @RequestParam("stateId") Long stateId) {
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(reportYear == null){
			    reportYear = organizationService.getReportYear(stateId, assessmentProgramId);
			    if(reportYear == null)
			       reportYear = (long) userDetails.getUser().getContractingOrganization().getReportYear();
			}		
			
			return batchUploadService.checkForInProgressGrfUploadOrReport(assessmentProgramId, stateId, reportYear.intValue());
		}
		
		@RequestMapping(value = "approveGrfRecords.htm", method = RequestMethod.POST)
		public void approveGrfRecords(
				@RequestParam("assessmentProgramId") Long assessmentProgramId,
				@RequestParam(value = "reportYear", required = false) Long reportYear,
				@RequestParam("stateId") Long stateId
		) {
					
			 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 Long userId = userDetails.getUserId();
			 String userDisplayname = userDetails.getUser().getDisplayName();
			 try{		
				 //Trigger GRF Report batch
				 batchReportProcessStarter.startBatchReportProcessFromGrfUpload(stateId, reportYear, assessmentProgramId, userId, userDisplayname);
				
			 }catch(Exception e){
				 LOGGER.error("Error occured in Alternate Aggregate Report Generation - "+e.getMessage(), e);	
				 
		     }		
		}
		
		@RequestMapping(value = "getStatesOrgsForUploadResults.htm", method = RequestMethod.GET)
	    public final @ResponseBody List<Organization> getMultipleStatesOrgsForUser(
	    		@RequestParam(value="assessmentProgramId")long assessmentProgramId) {	
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User user = userDetails.getUser();
	        Long userId = user.getId();	       
			List<Organization> stateForUploadResults = organizationService.getStateByAssessmentProgramIdForUploadResults(assessmentProgramId,userId);	        
	        return stateForUploadResults;
	    }
		
		private String peekAtReportType(File newFile) throws IOException{
			CSVReader csvReader = null;
			String reportType = null;
			try {
			 csvReader = new CSVReader(new BufferedReader(new FileReader(newFile)));
			 String [] nextLine;
			 int reportTypeIndex = -1;
			 int lineNumber = 0;
		     while ((nextLine = csvReader.readNext()) != null) {
		        lineNumber++;
		        if(lineNumber == 1){
		        	for (int i = 0; i < nextLine.length; i++) {
		        		if ("Report_Type".equalsIgnoreCase(nextLine[i].trim())) {
		        			reportTypeIndex = i;
		        		}
		        	}
		        }else{
		        	if(nextLine[reportTypeIndex] != null){
		        		try{
		        			reportType = nextLine[reportTypeIndex];
				           break;
		        		}catch(Exception e){
		        			
		        		}
		        	}
		        }		        
		      }
		     csvReader.close();
			} catch (Exception e) {
				LOGGER.info("For Scoring upload, uploaded document is not correct :"+ e.getMessage());
				csvReader.close();
			}
			return reportType;
		}
		
	@RequestMapping(value = "getTestingProgramsForReportingByAssessmentProgram.htm", method = RequestMethod.POST)
    public final @ResponseBody List<TestingProgram> getTestingProgramsForReporting(
    		@RequestParam(value="assessmentProgramId")Long assessmentProgramId) {			       
		
		List<TestingProgram> testingPrograms = testingProgramService.getTestingProgramsForReporting(assessmentProgramId);	        
        
		return testingPrograms;
    }
	
	/**
	 * Returns value at first occurrence for a passed in field name from first line in CSV file
	 * @param newFile
	 * @param fieldName
	 * @return
	 * @throws IOException
	 */
	private String getFieldValueFromFile(File newFile, String fieldName) throws IOException{
		CSVReader csvReader = null;
		String reportType = null;
		try {
		 csvReader = new CSVReader(new BufferedReader(new FileReader(newFile)));
		 String [] nextLine;
		 int reportTypeIndex = -1;
		 int lineNumber = 0;
	     while ((nextLine = csvReader.readNext()) != null) {
	        lineNumber++;
	        if(lineNumber == 1){
	        	for (int i = 0; i < nextLine.length; i++) {
	        		if (fieldName.equalsIgnoreCase(nextLine[i].trim())) {
	        			reportTypeIndex = i;
	        		}
	        	}
	        }else{
	        	if(nextLine[reportTypeIndex] != null){
	        		try{
	        			reportType = nextLine[reportTypeIndex];
			           break;
	        		}catch(Exception e){
	        			
	        		}
	        	}
	        }		        
	      }
	     csvReader.close();
		} catch (Exception e) {
			LOGGER.info("For Scoring upload, uploaded document is not correct :"+ e.getMessage());
			csvReader.close();
		}
		return reportType;
	}
	
	/**
	 * Returns all headers from CSV file
	 * @param newFile
	 * @return
	 * @throws IOException
	 */
	private String[] getFileHeaders(File newFile) throws IOException{
		CSVReader csvReader = null;
		
		try {
		 csvReader = new CSVReader(new BufferedReader(new FileReader(newFile)));
		 String [] nextLine;
		 int lineNumber = 0;
	     while ((nextLine = csvReader.readNext()) != null) {
	    	 lineNumber++;
	    	 if(lineNumber == 1){
	    		 csvReader.close();
	    		 return nextLine;
	    	 }
	    	 
	      }
	     
		} catch (Exception e) {
			LOGGER.info("For Scoring upload, uploaded document is not correct :"+ e.getMessage());
			csvReader.close();
		}
		return null;
	}
	
	
	/**
	 * Delete Interim Predictive Report calculations and pdf files
	 * @param assessmentProgramId
	 * @param reportCycle
	 * @param subjectId
	 * @param gradeId
	 * @param schoolYear
	 * @param studentId
	 */
	private void deleteInterimPredictiveReports(Long assessmentProgramId, String reportCycle, Long subjectId, Long gradeId, Long schoolYear, Long studentId){
		
		//delete pdf files		
		batchReportProcessService.deleteAllPredictiveReportFiles(assessmentProgramId, reportCycle, gradeId, subjectId, studentId, schoolYear);		
		
		//delete calculations data
		batchReportProcessService.deleteStudentReportQuestionInfo(assessmentProgramId, reportCycle, subjectId, gradeId, schoolYear, studentId);
		batchReportProcessService.deleteInterimStudentReports(assessmentProgramId, reportCycle, subjectId, gradeId, schoolYear, studentId);
		
	}
	
	@RequestMapping(value = "getTestingCyclesForReportingByAssessmentProgram.htm", method = RequestMethod.POST)
    public final @ResponseBody List<TestingCycle> getTestingCyclesForReportingByAssessmentProgram(
    		@RequestParam(value="assessmentProgramId")Long assessmentProgramId, @RequestParam(value="testingProgramId")Long testingProgramId) {			       
		List<TestingCycle> testingCycles = new ArrayList<TestingCycle>();
		TestingProgram testingProgram = testingProgramService.getByTestingProgramId(testingProgramId);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		if((testingProgram != null && interimTestingProgram.equalsIgnoreCase(testingProgram.getProgramName()))||(user.getCurrentAssessmentProgramName().equalsIgnoreCase("CPASS")&& testingProgram != null && summativeTestingProgram.equalsIgnoreCase(testingProgram.getProgramName()))){			
			Long schoolYear = (long) user.getContractingOrganization().getCurrentSchoolYear();
			
			testingCycles = batchReportProcessService.getTestingCyclesByProgramIdSchoolYear(assessmentProgramId, schoolYear, testingProgramId);
		}		           
        
		return testingCycles;
    }
	
	/**
	 * Delete Interim Predictive School District summary calculations and reports
	 * @param assessmentProgramId
	 * @param testingProgramId
	 * @param reportCycle
	 * @param schoolYear
	 * @param contentAreaId
	 * @param gradeId
	 */
	public void deletePredictiveSchoolDistrictSummary(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long schoolYear, Long contentAreaId, Long gradeId) {
		//TODO delete summary pdf files
		
		//delete summary calculations
		batchReportProcessService.deletePredictiveSchoolDistrictSummaryCalculations(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeId);
	}
	
	private void deleteExternalStudentReports(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, String reportCycle, Long testingProgramId, Long organizationId,String reportType, Long userId){
		//delete external cpass student individual reports and pdf files
		batchReportProcessService.deleteAllExternalStudentReportFiles(assessmentProgramId, gradeId, subjectId, organizationId, schoolYear, reportCycle, testingProgramId, reportType);
		batchReportProcessService.updateAllExternalStudentReportFilePath(assessmentProgramId, subjectId, gradeId, schoolYear, reportCycle, testingProgramId, organizationId, reportType, userId);
	}
	
	   
	@RequestMapping(value = "getGRFProcessStatus.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getGRFProcessStatus(
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String orderByColumn,
			@RequestParam("sord") String order) {
		Map<String, Object> reportUpload = new HashMap<String, Object>();
		int currentPage = NumericUtil.parse(page, 1);
		int limitCount = NumericUtil.parse(limitCountStr, 5);
		int offset = (currentPage - 1) * limitCount;
		int totalCount = 0;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
		
	
		List<String>  grfProcessTypes =  new ArrayList<String>();		
		
		if(userDetails.getUser().getGroupCode().equals("SAAD")) {
			grfProcessTypes.add(GRFUpdatedUpload);	
		}
			
		List<BatchUpload> uploads = batchUploadService.selectGRFProcessStatusBYStateId(assessmentProgramId,
				userDetails.getUser().getContractingOrgId(), userDetails.getUser().getContractingOrganization().getReportYear(), 
				grfProcessTypes, orderByColumn, order, limitCount, offset);


		if(uploads.size()>0){
		  totalCount = uploads.get(0).getTotalRecords();
		}
		
		reportUpload.put("rows", uploads);
		reportUpload.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		reportUpload.put("page", currentPage);
		reportUpload.put("records", totalCount);
		return reportUpload;
	}
	
	
	@RequestMapping(value = "manageGrfExtract.htm", method = RequestMethod.POST)
	public final @ResponseBody Long getReportAccessData(
			@RequestParam("manageGrfScCodeExtract") Boolean manageGrfScCodeExtract,
			@RequestParam("manageGrfExitStudentExtract") Boolean manageGrfExitStudentExtract)

			throws NoAccessToResourceException, JsonProcessingException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();

		Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
		BatchUpload upload = new BatchUpload();
		int reportYear = userDetails.getUser().getContractingOrganization().getReportYear();
		Long userId = userDetails.getUser().getId();
		String grfProcessType = "";
		Long extractId = 0l;
		DataReportTypeEnum type;
		Long stateId = userDetails.getUser().getContractingOrgId();
		String stateCode =organizationDao.getStateCodeByStateId(stateId);
		
		Long  narrowestOrgId;
		Long narrowestOrgTypeId;
		Organization org = organizationService.get(user.getOrganizationId());
		OrganizationType ot = orgTypeService.get(org.getOrganizationTypeId());
		narrowestOrgId = org.getId();
		narrowestOrgTypeId = ot.getOrganizationTypeId();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("currentOrganizationId", user.getCurrentOrganizationId());
		params.put("assessmentProgramId", user.getCurrentAssessmentProgramId());
		params.put("assessmentProgramCode", user.getCurrentAssessmentProgramName());
		params.put("reportYear", reportYear);
		Long recentExtractId = (long) 0;
		
		if (manageGrfScCodeExtract) {
			type = DataReportTypeEnum.DLM_SPECIAL_CIRCUMSTANCES_FILE;
			grfProcessType = GRFExtractSCCode;
			Long batchUploadId = insertGrfBatchUpload(manageGrfScCodeExtract, manageGrfExitStudentExtract, assessmentProgramId, upload, reportYear,
					userId, grfProcessType, stateId,userDetails.getUser());
			grfReportWriterService.generateScCodeExtract(assessmentProgramId, stateId, reportYear, userId,stateCode, batchUploadId);
			params.put("batchuploadid", batchUploadId);
			recentExtractId = getMostRecentGRFExtractReportByTypeId(user, type.getId());
			extractId = dataReportService.generateNewExtract(user,type , recentExtractId, narrowestOrgId, narrowestOrgTypeId, params);
		}
		if (manageGrfExitStudentExtract) {
			type = DataReportTypeEnum.DLM_EXIT_STUDENT;
			grfProcessType = GRFExtractExitStudent;
			Long batchuploadid = insertGrfBatchUpload(manageGrfScCodeExtract, manageGrfExitStudentExtract, assessmentProgramId, upload, reportYear,
					userId, grfProcessType, stateId, userDetails.getUser());
			grfReportWriterService.generateStudentExitDetailsExtract(assessmentProgramId, stateId,
					reportYear, userId, batchuploadid);
			params.put("batchuploadid", batchuploadid);
			recentExtractId = getMostRecentGRFExtractReportByTypeId(user, type.getId());
			extractId = dataReportService.generateNewExtract(user,type, recentExtractId, narrowestOrgId, narrowestOrgTypeId, params);
		}
		return extractId;
	}
	
	private Long getMostRecentGRFExtractReportByTypeId(User user, Short typeId) {
		ModuleReport moduleReport = dataReportService.getMostRecentGRFExtractReportByTypeId(user, typeId);
		Long recentExtractId = (long) 0;
		if(moduleReport != null) {
			recentExtractId = moduleReport.getId();
		}
		return recentExtractId;
	}

	private Long insertGrfBatchUpload(Boolean manageGrfScCodeExtract, Boolean manageGrfExitStudentExtract,
			Long assessmentProgramId, BatchUpload upload, int reportYear, Long userId, String grfProcessType,
			Long stateId, User user) {
		if (manageGrfScCodeExtract || manageGrfExitStudentExtract) {
			Date now = new Date();
			upload.setContentAreaId(99L);
			upload.setStateId(stateId);
			upload.setFileName("N/A");
			upload.setFilePath("");
			upload.setCreatedUserDisplayName(user.getDisplayName());
			upload.setAssessmentProgramId(assessmentProgramId);
			upload.setSubmissionDate(now);
			upload.setCreatedUser(userId);
			upload.setStatus("INPROGRESS");
			upload.setSchoolYear(reportYear);
			upload.setReportYear((long)reportYear);
			upload.setActiveFlag(true);
			upload.setCreatedDate(now);
			upload.setModifiedDate(now);
			upload.setSubmissionDate(now);
			upload.setGrfProcessType(grfProcessType);
			
			batchUploadService.insertBatchUpload(upload);
		}
		return upload.getId();
	}
	
	@RequestMapping(value = "uploadCustomFilesData.htm", method = RequestMethod.POST)
	public final @ResponseBody String uploadCustomFilesData(MultipartHttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
		Long stateId = null;
		if (request.getParameter("stateId") != null) {
			stateId = Long.parseLong(request.getParameter("stateId"));
		}
		Long assessmentProgramId = (long) 0;
		if (request.getParameter("assessmentProgramId") != null) {
			assessmentProgramId = Long.parseLong(request.getParameter("assessmentProgramId"));
		}
		String descriptionName = request.getParameter("descriptionName");
		StateSpecificFile stateSpecificFile = new StateSpecificFile();
		stateSpecificFile.setAuditColumnProperties();
		stateSpecificFile.setFileName(cmpf.getOriginalFilename());
		stateSpecificFile.setFileDescription(descriptionName);
		stateSpecificFile.setAssessmentProgramId(assessmentProgramId);
		stateSpecificFile.setStateId(stateId);
		SimpleDateFormat formatter = new SimpleDateFormat("MMddyy_hhmmss");
		String formattedDate = formatter.format(new Date());
		String folderPath = REPORT_PATH;
		if (!folderPath.endsWith(File.separator)) {
			folderPath += File.separator;
		}
		folderPath += "CustomFiles" + File.separator + user.getId() + File.separator + formattedDate;
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		String filePath = folderPath + File.separator + cmpf.getOriginalFilename().replace(" ", "_");

		s3.synchMultipartUpload(filePath, cmpf.getInputStream(), cmpf.getContentType());
		stateSpecificFile.setFileLocation(filePath);
		int fileUploaded = -1;
		fileUploaded = batchUploadService.saveCustomFileData(stateSpecificFile);
		if (fileUploaded > 0) {
			return "{\"success\":\"File Uploaded Successfully\"}";
		} else {
			return "{\"error\":\"Error occured while uploading file\"}";
		}
	}

	@RequestMapping(value = "getStateSpecificFileData.htm", method = RequestMethod.POST)
	public final @ResponseBody String getStateSpecificFileData(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam("filters") String filters)
			throws NoAccessToResourceException {
		Map<String, Object> customFilesData = new HashMap<String, Object>();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;
		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long stateId = getStateId(user.getCurrentOrganizationId());
		Map<String, String> recordCriteriaMap = constructRecordFilterCriteria(filters);

		List<StateSpecificFile> stateSpecificFilesData = batchUploadService.getStateSpecificFileData(
				user.getCurrentAssessmentProgramId(), stateId, sortByColumn, sortType, (currentPage - 1) * limitCount,
				limitCount, recordCriteriaMap);
		customFilesData.put("stateSpecificFilesData", stateSpecificFilesData);
		totalCount = 0;
		if (!stateSpecificFilesData.isEmpty()) {
			totalCount = stateSpecificFilesData.get(0).getTotalRecords();
		}
		customFilesData.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		customFilesData.put("page", currentPage);
		customFilesData.put("records", totalCount);
		try {
			jsonData = objectMapper.writeValueAsString(customFilesData);
		} catch (JsonProcessingException e) {
			LOGGER.error("Error while creating JSON data", e);
		}
		return jsonData;
	}

	@RequestMapping(value = "removeStateSpecificFile.htm", method = RequestMethod.POST)
	public final @ResponseBody String removeStateSpecificFile(
			@RequestParam(value = "stateSpecificFileId") Long stateSpecificFileId) {
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			batchUploadService.removeStateSpecificFile(stateSpecificFileId, userDetails.getUser().getId());
			model.put("status", "success");
			model.put("successMessage", "File removed Successfully");
			String modelJson = mapper.writeValueAsString(model);
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while removing file ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage())
					+ "\"}";
		}
	}
	
	@RequestMapping(value = "setReportYearToSchoolYear.htm", method = RequestMethod.POST)
	public final @ResponseBody String setReportYearToSchoolYear(@RequestParam("stateId") Long stateId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();		
		try {
		   organizationService.setReportYearToSchoolYear(stateId,userDetails.getUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"success\":\"false\"}";
		}		
		return "{\"success\":\"true\"}";
		
	}
	
	@RequestMapping(value = "getGRFRecentStatus.htm", method = RequestMethod.POST)
	public final @ResponseBody List<GrfStateApproveAudit> getGRFRecentStatus(@RequestParam("stateId") Long stateId,
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("reportYear") Long reportYear) {
		
		List<GrfStateApproveAudit> grfStateApproveAuditList = new ArrayList<GrfStateApproveAudit>();
		GrfStateApproveAudit grfStateApproveAudit = batchReportProcessService.getGRFRecentStatus(stateId, assessmentProgramId, reportYear);
		grfStateApproveAuditList.add(grfStateApproveAudit);
		return grfStateApproveAuditList;		
	}
	
	private boolean validatePermissionsFileHeader(File newFile){
		Map<String, FieldSpecification> feildSpecMap = new HashMap<String, FieldSpecification>();
		feildSpecMap = uploadFileService.getFieldSpecificationRecordMap(permissionRecordType, csvRecordTypeCode);
		String[] headNames;
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.createObjectNode();
		ArrayNode jsonArray = mapper.createArrayNode();
		boolean haveDynamicColumn=false;
		List<String> roles = groupsService.getPermissionExtractGroupNames();
		try {
			headNames = getFileHeaders(newFile);
			for(String headName:headNames){
					//trim heading from file
					headName=headName.trim();
					FieldSpecification fieldName = feildSpecMap.get(headName.toLowerCase());
					if(fieldName == null){
						if(roles.contains(headName)) {
							ObjectNode formDetailsJson = mapper.createObjectNode();
							formDetailsJson.put(CommonConstants.FIELDSPECIFICFICATION_MAPPEDNAME, headName);
							formDetailsJson.put(CommonConstants.FIELDSPECIFICFICATION_FIELDNAME ,headName);
							formDetailsJson.put(CommonConstants.FIELDSPECIFICFICATION_FIELDTYPE, CommonConstants.FIELDSPECIFICFICATION_FIELDTYPE_STRINGTYPE);
							formDetailsJson.put(CommonConstants.FIELDSPECIFICFICATION_REJECTIFINVALID , CommonConstants.STRING_TRUE);
							formDetailsJson.put(CommonConstants.FIELDSPECIFICFICATION_SHOWERROR, CommonConstants.STRING_TRUE);
							formDetailsJson.put(CommonConstants.FIELDSPECIFICFICATION_REJECTIFEMPTY, CommonConstants.STRING_FALSE);
							jsonArray.add(formDetailsJson);							
							haveDynamicColumn=true;
						}else {
						return false;
						}
					}
				}
				((ObjectNode) root).set("headerColumnDetails", jsonArray);
				headerJsonStr = mapper.writeValueAsString(root);
			return haveDynamicColumn;
		} catch (IOException ioe) {
			LOGGER.info("Exception reading header from csv file :"+ permissionRecordType+ "file type. "+ioe.getMessage());		
			
			return false;
		}		
	}

}
