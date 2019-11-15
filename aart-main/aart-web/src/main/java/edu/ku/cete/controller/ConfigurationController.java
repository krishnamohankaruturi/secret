package edu.ku.cete.controller;
	
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.annotation.PostConstruct;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.batch.upload.BatchUploadProcessStarter;
import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.Row;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.domain.UserAssessmentPrograms;
import edu.ku.cete.domain.UserSecurityAgreement;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.common.PnpAccomodation;
import edu.ku.cete.domain.common.PnpAssessmentKeyComparator;
import edu.ku.cete.domain.common.PnpStateSettings;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.UserDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AuthoritiesService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.GroupAuthoritiesService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ProfileAttributeContainerService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.UploadFileRecordService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.exception.NoAccessToResourceException;
import edu.ku.cete.service.impl.AssessmentProgramServiceImpl;
import edu.ku.cete.service.impl.report.BatchReportProcessServiceImpl;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.student.FirstContactService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.json.GroupsJsonConverter;
import edu.ku.cete.util.json.RosterDTOJsonConverter;
import edu.ku.cete.util.json.StudentEnrollmentsJsonConverter;
import edu.ku.cete.util.json.UsersJsonConverter;
import edu.ku.cete.web.AssessmentProgramDto;
import edu.ku.cete.web.AuthoritiesDTO;
import edu.ku.cete.web.GroupAuthoritiesDto;
import edu.ku.cete.web.RosterDTO;
import edu.ku.cete.web.UserSecurityAgreemntDTO;
import edu.ku.cete.web.authorities.AuthoritiesTab;
	
@Controller
public class ConfigurationController {
	
	/**
	 * Logger for class.
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ConfigurationController.class);
	
	/* EnrollmentService  */
	@Autowired
	private EnrollmentService enrollmentService;
	
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
	@Autowired
	private AssessmentProgramService assessProgService;
	 @Autowired
	  private BatchUploadProcessStarter batchUploadProcessStarter;
	/**
	 * uploadSpecification
	 */
	@Autowired
	private UploadSpecification uploadSpecification;
	
	/**
	 * categoryService
	 */
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * reportingReportCategories
	 */
	@Value("${report.upload.categorytype.code}")
	private String reportUploadCategoryTypeCode;
	
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
	
	@Autowired
	private UploadFileProcessor uploadFileProcessor;
	
	/**
	 * permissionUtil
	 */
	@Autowired
	private PermissionUtil permissionUtil;
	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Autowired
	private AuthoritiesService authService;
	
	@Autowired
	private GroupAuthoritiesService gaService;
	
	@Value("${report.assessmentprograms}")
	private String reportingAssessmentPrograms;
	
	/**
	 * fileCharset
	 */
	@Value("${default.file.encoding.charset}")
	private Boolean defaultFileCharset;
	
	/**
	 * for getting the code for record type.
	 */
	@Autowired
	private CategoryTypeDao categoryTypeDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private EnrollmentService enrollemntService;
	
	
	@Autowired
	private StudentService studentService;
	
	   /**
     * User organization for DLM
     */
    @Value("${iti.code.recommendedlevel}")
    private String ITI_RECOMMENDED_LEVEL;
    
    /**
     * User organization for DLM
     */
    @Value("${iti.code.itistatus}")
    private String ITI_ACCESS;
	
    
    @Value("${org.password.expiration.type}")
    private String ORG_PASS_EXP_TYPE;
	/**
	 * 
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private GradeCourseService gradeCourseService;

	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private UploadFileRecordService uploadFileRecordService;
	
	@Autowired
	private FirstContactService firstContactService;
	
	@Autowired
	private ProfileAttributeContainerService profileAttributeContainerService;

	@Value("${dlm.multiassignment.ela}")
	private Integer elaMultiLimit;
	
	@Value("${dlm.multiassignment.math}")
	private Integer mMultiLimit;
	
	@Value("${dlm.multiassignment.science}")
	private Integer sciMultiLimit;

	@Autowired
	private AppConfigurationService appConfigurationService;
	
    @Autowired
    private OrganizationService orgService;
    

	@Autowired
	private DataExtractService dataExtractService;
	
	@Autowired
    private OrganizationDao organizationDao;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private OperationalTestWindowService otwService;
	
	@Value("${ismart.assessmentProgram.abbreviatedName}")
	private String ISMART_PROGRAM_ABBREVIATEDNAME;
	
	@Value("${ismart2.assessmentProgram.abbreviatedName}")
	private String ISMART_2_PROGRAM_ABBREVIATEDNAME;
	
	@Autowired
  	private UserDao userDao;
  	
	@RequestMapping(value = "configuration.htm")
	public final ModelAndView getConfiguration(Model model,@RequestParam(value = "path", required = false, defaultValue = "configuration/configuration") String path) {	
	    
		ModelAndView mav = new ModelAndView(path);
	        model.addAttribute(new UploadFile());
	        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
	        boolean qcPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
					RestrictedResourceConfiguration.getQualityControlCompletePermission());
			LOGGER.debug("hasQCPermission : "+qcPermission);
			Boolean ITIRecommendedLevel = getITIconfiguration(ITI_RECOMMENDED_LEVEL);
			Boolean ITIStatus = getITIconfiguration(ITI_ACCESS);
	        User user = userDetails.getUser();
			 List<OrganizationType> organizationTypes = organizationTypeService.getAll();
			 List<Organization> organizationsStates = new ArrayList<Organization>(); 
			 List<Organization> organizationsConsortia = organizationService.getByTypeId(CommonConstants.ORGANIZATION_CONSORTIA_CODE);
			 Collections.sort(organizationsConsortia, OrgComparator);
			 
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
			
			if(states != null){
			for(Organization organizationsState : states){
				 if(organizationsState.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
					 organizationsStates.add(organizationsState);
				 }
			}
			}
			// Per US17690
			if(permissionUtil.hasPermission(userDetails.getAuthorities(), "EDIT_FIRST_CONTACT_SETTINGS")){
				List<FirstContactSettings> firstContactSettings = firstContactService.getFirstContactSurveySettings(user.getContractingOrgId());
 				mav.addObject("firstContactSettings", firstContactSettings);
 				Category coreSet = categoryService.selectByCategoryCodeAndType("CORE_SET_QUESTIONS", "FIRST CONTACT SETTINGS");
 				Category allQuestions = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
 				mav.addObject("coreSet", coreSet);
 				mav.addObject("allQuestions", allQuestions);
 				mav.addObject("organizationId",user.getContractingOrgId());
			}
			
			//US19086 & US19087
			
			if(permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_PNP_OPTIONS")){
                
				List<PnpAccomodation> pnpAccomodations = profileAttributeContainerService.getPnpAccomodations();
				Collections.sort(pnpAccomodations);
				Map<Long, Map<Long, Map<String, PnpAccomodation>>> pnpAccomodationsMap = new TreeMap<Long, Map<Long, Map<String, PnpAccomodation>>>();
				for(PnpAccomodation pnpAccomodation : pnpAccomodations){
					
					if(pnpAccomodationsMap.get(pnpAccomodation.getCategoryId()) == null){
						pnpAccomodationsMap.put(pnpAccomodation.getCategoryId(), new TreeMap<Long, Map<String, PnpAccomodation>>());
					} 
					
					Map<Long, Map<String, PnpAccomodation>> pnpMapByCategories = pnpAccomodationsMap.get(pnpAccomodation.getCategoryId());
					
					if(pnpMapByCategories.get(pnpAccomodation.getSortOrder()) == null){
						pnpMapByCategories.put(pnpAccomodation.getSortOrder(), new TreeMap<String, PnpAccomodation>(new PnpAssessmentKeyComparator()));
					}
					
					Map<String, PnpAccomodation> pnpMapByAP = pnpMapByCategories.get(pnpAccomodation.getSortOrder());
					
					if(pnpMapByAP.get(pnpAccomodation.getAssessmentProgram()) == null){
						pnpMapByAP.put(pnpAccomodation.getAssessmentProgram(), pnpAccomodation);
					}
				}
				
				// Add missing programs
				List<String> supportedAps = new ArrayList<String>(Arrays.asList("KAP", "DLM", "CPASS", "KELPA2"));
				Collections.sort(supportedAps, new PnpAssessmentKeyComparator());
				for(Long categoryId: pnpAccomodationsMap.keySet()){
					Map<Long, Map<String, PnpAccomodation>> pnpMapByCategories = pnpAccomodationsMap.get(categoryId);
					for(Long accomodation : pnpMapByCategories.keySet()){
						Map<String, PnpAccomodation> pnpMapByAp = pnpMapByCategories.get(accomodation);
						PnpAccomodation existingPnpAccomodation = null;
						for(PnpAccomodation value : pnpMapByAp.values()) {
							existingPnpAccomodation = value;
						    break;
						}
						for(String ap : supportedAps){
							if(pnpMapByAp.get(ap) == null){
								PnpAccomodation newPnpAccomodation = new PnpAccomodation();
								newPnpAccomodation.setAssessmentProgram(ap);
								newPnpAccomodation.setAccomodation(existingPnpAccomodation.getAccomodation());
								newPnpAccomodation.setCategoryId(categoryId);
								newPnpAccomodation.setViewOption("enable");
								newPnpAccomodation.setPianacid(existingPnpAccomodation.getPianacid());
								pnpMapByAp.put(ap, newPnpAccomodation);
							}
						}
					}
				}
				Map<Long, String> sortedAccomodations = new HashMap<>();
				for(PnpAccomodation pnpAccomodation: pnpAccomodations){
					sortedAccomodations.put(pnpAccomodation.getSortOrder(), pnpAccomodation.getAccomodation());
				}
				mav.addObject("sortedAccomodations", sortedAccomodations);
				mav.addObject("supportedAps", supportedAps);
				mav.addObject("pnpAccomodationsMap", pnpAccomodationsMap);
				
                List<PnpStateSettings> pnpStateSettings = profileAttributeContainerService.getPnpStateSettings();
                Map<String, List<PnpStateSettings>> pnpStateSettingsMap = new HashMap<String, List<PnpStateSettings>>();
                for(PnpStateSettings pnpStateSetting : pnpStateSettings){
                	if(pnpStateSettingsMap.get(pnpStateSetting.getAssessmentProgram()) == null){
                		pnpStateSettingsMap.put(pnpStateSetting.getAssessmentProgram(), new ArrayList<PnpStateSettings>());
                	}
                	pnpStateSettingsMap.get(pnpStateSetting.getAssessmentProgram()).add(pnpStateSetting);
                }
                mav.addObject("pnpStateSettingsMap", pnpStateSettingsMap);
				
				Category displayEnhancements = categoryService.selectByCategoryCodeAndType("DISPLAY_ENHANCEMENTS","PNP_OPTIONS_SETTINGS");
 				Category languageAndBraille = categoryService.selectByCategoryCodeAndType("LANGUAGE_AND_BRAILLE","PNP_OPTIONS_SETTINGS");
 				Category audioAndEnvironmentSupport = categoryService.selectByCategoryCodeAndType("AUDIO_AND_ENVIRONMENT_SUPPORT","PNP_OPTIONS_SETTINGS");
 				Category otherSupports = categoryService.selectByCategoryCodeAndType("OTHER_SUPPORTS","PNP_OPTIONS_SETTINGS");
 				mav.addObject("displayEnhancements", displayEnhancements);
 				mav.addObject("languageAndBraille", languageAndBraille);
 				mav.addObject("audioAndEnvironmentSupport", audioAndEnvironmentSupport );
 				mav.addObject("otherSupports", otherSupports);
			}
			 mav.addObject("expirationDateTypes",categoryService.selectByCategoryType(ORG_PASS_EXP_TYPE));
			 mav.addObject("testingModels",categoryService.selectByCategoryType("DLM_TESTING_MODEL"));
			 mav.addObject("assessmentPrograms", getAssessmentPrograms());
			 mav.addObject("organizationsStates", organizationsStates);
			 mav.addObject("organizationsConsortia", organizationsConsortia);
			 mav.addObject("organizationTypes",organizationTypes);
			 mav.addObject("hasQCPermission",qcPermission);
			 mav.addObject("ITIRecommendedLevel", ITIRecommendedLevel);
			 mav.addObject("ITIStatus", ITIStatus);
			 mav.addObject("dlmmultiassignconfig", getDLMBatchAutoMultiAssignConfigs());
			 return mav;
	}
	
	@RequestMapping(value = "batchProcesses.htm")
	public final ModelAndView getBatchProcessConfiguration() {
		return new ModelAndView("new-settings/batchProcesses");
	}
	
	@RequestMapping(value = "createMessages.htm")
	public final ModelAndView getCreateMessagesConfiguration() {
		return new ModelAndView("configuration/createMessage");
	}
	
	@RequestMapping(value = "testRecords.htm")
	public final ModelAndView getTestRecordsConfiguration() {
		return new ModelAndView("new-settings/testRecordsMgmt");
	}
	
	@RequestMapping(value = "firstContactSettings.htm")
	public final ModelAndView getFirstContactConfiguration() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		ModelAndView mav = new ModelAndView("configuration/firstContactSettings");
		List<FirstContactSettings> firstContactSettings = firstContactService.getFirstContactSurveySettings(user.getContractingOrgId());
		mav.addObject("firstContactSettings", firstContactSettings);
		Category coreSet = categoryService.selectByCategoryCodeAndType("CORE_SET_QUESTIONS", "FIRST CONTACT SETTINGS");
		Category allQuestions = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
		mav.addObject("coreSet", coreSet);
		mav.addObject("allQuestions", allQuestions);
		mav.addObject("organizationId", user.getContractingOrgId());
		return mav;
	}
	
	@RequestMapping(value = "generalConfig.htm")
	public final ModelAndView getGeneralConfiguration() {
		ModelAndView mav = new ModelAndView("new-settings/generalSettings");
		
		List<PnpAccomodation> pnpAccomodations = profileAttributeContainerService.getPnpAccomodations();
		Collections.sort(pnpAccomodations);
		Map<Long, Map<Long, Map<String, PnpAccomodation>>> pnpAccomodationsMap = new TreeMap<Long, Map<Long, Map<String, PnpAccomodation>>>();
		for(PnpAccomodation pnpAccomodation : pnpAccomodations){
			if(pnpAccomodationsMap.get(pnpAccomodation.getCategoryId()) == null){
				pnpAccomodationsMap.put(pnpAccomodation.getCategoryId(), new TreeMap<Long, Map<String, PnpAccomodation>>());
			} 
			
			Map<Long, Map<String, PnpAccomodation>> pnpMapByCategories = pnpAccomodationsMap.get(pnpAccomodation.getCategoryId());
			
			if(pnpMapByCategories.get(pnpAccomodation.getSortOrder()) == null){
				pnpMapByCategories.put(pnpAccomodation.getSortOrder(), new TreeMap<String, PnpAccomodation>(new PnpAssessmentKeyComparator()));
			}
			
			Map<String, PnpAccomodation> pnpMapByAP = pnpMapByCategories.get(pnpAccomodation.getSortOrder());
			
			if(pnpMapByAP.get(pnpAccomodation.getAssessmentProgram()) == null){
				pnpMapByAP.put(pnpAccomodation.getAssessmentProgram(), pnpAccomodation);
			}
		}
		
		// Add missing programs
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Long assessmentId = userDetails.getUser().getCurrentAssessmentProgramId();
		AssessmentProgram assessmentProgram = assessProgService.findByAssessmentProgramId(assessmentId);
		List<String> supportedAps = new ArrayList<>();
		if (CommonConstants.ASSESSMENT_PROGRAM_PLTW.equals(assessmentProgram.getAbbreviatedname())) {
			supportedAps = new ArrayList<String>(Arrays.asList("PLTW"));
		}else{
			supportedAps = new ArrayList<String>(Arrays.asList("KAP", "DLM", "CPASS", "KELPA2", "I-SMART", "I-SMART2"));
		}
		Collections.sort(supportedAps, new PnpAssessmentKeyComparator());
		for(Long categoryId: pnpAccomodationsMap.keySet()){
			Map<Long, Map<String, PnpAccomodation>> pnpMapByCategories = pnpAccomodationsMap.get(categoryId);
			for(Long accomodation : pnpMapByCategories.keySet()){
				Map<String, PnpAccomodation> pnpMapByAp = pnpMapByCategories.get(accomodation);
				PnpAccomodation existingPnpAccomodation = null;
				for(PnpAccomodation value : pnpMapByAp.values()) {
					existingPnpAccomodation = value;
				    break;
				}
				for(String ap : supportedAps){
					if(pnpMapByAp.get(ap) == null){
						PnpAccomodation newPnpAccomodation = new PnpAccomodation();
						newPnpAccomodation.setAssessmentProgram(ap);
						newPnpAccomodation.setAccomodation(existingPnpAccomodation.getAccomodation());
						newPnpAccomodation.setCategoryId(categoryId);
						newPnpAccomodation.setViewOption("enable");
						newPnpAccomodation.setPianacid(existingPnpAccomodation.getPianacid());
						pnpMapByAp.put(ap, newPnpAccomodation);
					}
				}
			}
		}
		Map<Long, String> sortedAccomodations = new HashMap<>();
		for(PnpAccomodation pnpAccomodation: pnpAccomodations){
			sortedAccomodations.put(pnpAccomodation.getSortOrder(), pnpAccomodation.getAccomodation());
		}
		mav.addObject("sortedAccomodations", sortedAccomodations);
		mav.addObject("supportedAps", supportedAps);
		mav.addObject("pnpAccomodationsMap", pnpAccomodationsMap);
		
        List<PnpStateSettings> pnpStateSettings = profileAttributeContainerService.getPnpStateSettings();
        Map<String, List<PnpStateSettings>> pnpStateSettingsMap = new HashMap<String, List<PnpStateSettings>>();
        for(PnpStateSettings pnpStateSetting : pnpStateSettings){
        	if(pnpStateSettingsMap.get(pnpStateSetting.getAssessmentProgram()) == null){
        		pnpStateSettingsMap.put(pnpStateSetting.getAssessmentProgram(), new ArrayList<PnpStateSettings>());
        	}
        	pnpStateSettingsMap.get(pnpStateSetting.getAssessmentProgram()).add(pnpStateSetting);
        }
        mav.addObject("pnpStateSettingsMap", pnpStateSettingsMap);
		
        Category displayEnhancements = categoryService.selectByCategoryCodeAndType("DISPLAY_ENHANCEMENTS","PNP_OPTIONS_SETTINGS");
		Category languageAndBraille = categoryService.selectByCategoryCodeAndType("LANGUAGE_AND_BRAILLE","PNP_OPTIONS_SETTINGS");
		Category audioAndEnvironmentSupport = categoryService.selectByCategoryCodeAndType("AUDIO_AND_ENVIRONMENT_SUPPORT","PNP_OPTIONS_SETTINGS");
		Category otherSupports = categoryService.selectByCategoryCodeAndType("OTHER_SUPPORTS","PNP_OPTIONS_SETTINGS");
		mav.addObject("displayEnhancements", displayEnhancements);
		mav.addObject("languageAndBraille", languageAndBraille);
		mav.addObject("audioAndEnvironmentSupport", audioAndEnvironmentSupport );
		mav.addObject("otherSupports", otherSupports);
		
		return mav;
	}
	
	@RequestMapping(value = "configureITI.htm") 
	public final ModelAndView getInstructionalToolConfiguration() {
		ModelAndView mav = new ModelAndView("new-settings/configureITI"); 
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		boolean qcPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
		LOGGER.debug("hasQCPermission : "+qcPermission);
		Boolean ITIRecommendedLevel = getITIconfiguration(ITI_RECOMMENDED_LEVEL);
		Boolean ITIStatus = getITIconfiguration(ITI_ACCESS);
		mav.addObject("hasQCPermission",qcPermission);
		 mav.addObject("ITIRecommendedLevel", ITIRecommendedLevel);
		 mav.addObject("ITIStatus", ITIStatus);
		return  mav;
	}
	
	@RequestMapping(value = "findQCTests.htm") 
	public final ModelAndView getQCConfiguration() {
		return new ModelAndView("new-settings/findQCTests");
	}
	
	@RequestMapping(value = "roles.htm") 
	public final ModelAndView getRolesConfiguration() {
		return new ModelAndView("new-settings/roles");
	}
	
	@RequestMapping(value = "rosters.htm")
	public final ModelAndView getRostersConfiguration() {
		return new ModelAndView("new-settings/rosters");
	}
	
	@RequestMapping(value = "students.htm")
	public final ModelAndView getStudentsConfiguration() {
		return new ModelAndView("new-settings/students");
	}
	
	@RequestMapping(value = "reportSetup.htm")
	public final ModelAndView getreportSetupsConfiguration() {
		ModelAndView mav = new ModelAndView("new-settings/reportsSetup"); 
		return mav;
	}
	
	@RequestMapping(value = "organization.htm")
	public final ModelAndView getOrganizationsConfiguration() {
		ModelAndView mav = new ModelAndView("new-settings/organizationMgmt"); 
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			List<OrganizationType> organizationTypes = organizationTypeService.getAll();
			List<Organization> organizationsStates = new ArrayList<Organization>(); 
			 List<Organization> organizationsConsortia = organizationService.getByTypeId(CommonConstants.ORGANIZATION_CONSORTIA_CODE);
			 Collections.sort(organizationsConsortia, OrgComparator);
		 User user = userDetails.getUser();
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
			
			if(states != null){
			for(Organization organizationsState : states){
				 if(organizationsState.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
					 organizationsStates.add(organizationsState);
				 }
			}
			}
	
        mav.addObject("organizationTypes",organizationTypes);
        mav.addObject("expirationDateTypes",categoryService.selectByCategoryType(ORG_PASS_EXP_TYPE));
		 mav.addObject("testingModels",categoryService.selectByCategoryType("DLM_TESTING_MODEL"));
		 mav.addObject("assessmentPrograms", getAssessmentPrograms());
		 mav.addObject("organizationsStates", organizationsStates);
		 mav.addObject("organizationsConsortia", organizationsConsortia);
        return mav;
	}
	/**
	 * @param user
	 *            {@link User}
	 * @return List<AssessmentProgram>
	 */
	private List<AssessmentProgram> getAssessmentPrograms() {		
		List<AssessmentProgram> assessmentPrograms = assessProgService.getAllActive();

		return assessmentPrograms;
	}
	 
	/**
	 * A wrapper to convert the uploadController Form based call to support an ajax based call
	 * @deprecated dead code
	 */
	
	@RequestMapping(value = "uploadRoster.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadRoster(MultipartHttpServletRequest request, HttpServletResponse response, 
			@RequestParam(value="continueOnWarning", required=false) Boolean continueOnWarning) throws IOException, ServiceException {                 
		try {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Long stateId = 0L;
			if(null != request.getParameter("stateId") && !"".equals(request.getParameter("stateId"))) {
				stateId = Long.parseLong(request.getParameter("stateId"));
			}
			Long districtId = 0L;
			if(null != request.getParameter("districtId") && !"".equals(request.getParameter("districtId"))) {
				districtId = Long.parseLong(request.getParameter("districtId"));
			}
			Long schoolId = 0L;
			if(null != request.getParameter("schoolId") && !"".equals(request.getParameter("schoolId"))) {
				schoolId = Long.parseLong(request.getParameter("schoolId"));
			}
			Iterator<String> itr =  request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;

			File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));			
			LOGGER.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
			cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
		
			UploadFile uploadFile = new UploadFile();
			uploadFile.setSelectedRecordTypeId(scrsRecordType.getId());
			uploadFile.setRosterUpload(1);
			uploadFile.setStateId(stateId);
			uploadFile.setDistrictId(districtId);
			uploadFile.setSchoolId(schoolId);
			uploadFile.setFile(destFile);
			uploadFile.setContinueOnWarning(continueOnWarning);
		
			BeanPropertyBindingResult errors = new BeanPropertyBindingResult(uploadFile, reportUploadCategoryTypeCode);
			
			Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			UploadFileRecord uploadFileRecord = buildUploadFileRecord(
					user, cmpf.getOriginalFilename(), inProgressStatus.getId());
			uploadFileRecordService.insertUploadFile(uploadFileRecord);
			
			uploadFileProcessor.create(uploadFile, errors, uploadFileRecord.getId());
			//request.getSession().setAttribute("uploadRosterFileProcess", futureMav);
			
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("uploadFileRecordId", uploadFileRecord.getId());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
			String modelJson = mapper.writeValueAsString(model);
			return modelJson;
		} catch(Exception e) {
			LOGGER.error("Exception ooccured while uploading roster file: " + e.getMessage(), e);			
			return "{\"errorFound\":\"true\"}";
		}
	}
	
	private UploadFileRecord buildUploadFileRecord(User user, String fileName, Long statusId) {
		UploadFileRecord uploadFileRecord = new UploadFileRecord();
		uploadFileRecord.setFileName(fileName);
		uploadFileRecord.setCreatedUser(user.getId());
		uploadFileRecord.setCreatedDate(new Date());
		uploadFileRecord.setModifiedUser(user.getId());
		uploadFileRecord.setModifiedDate(new Date());
		uploadFileRecord.setStatusId(statusId);
		return uploadFileRecord;
	}
	
	/**
	 * A wrapper to convert the uploadController Form based call to support an ajax based call
	 * @deprecated dead code
	 */
	
	@RequestMapping(value = "uploadFirstContact.htm", method = RequestMethod.POST)
	public @ResponseBody String uploadFirstContact(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {                 

		Long stateId = Long.parseLong(request.getParameter("stateId"));
		Long districtId = Long.parseLong(request.getParameter("districtId"));
		Long schoolId = Long.parseLong(request.getParameter("schoolId"));
		Iterator<String> itr =  request.getFileNames();
		MultipartFile mpf = request.getFile(itr.next());
		CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;

		File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));
		LOGGER.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
		cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
		
		UploadFile uploadFile = new UploadFile();
		uploadFile.setSelectedRecordTypeId(firstContactRecordType.getId());
		uploadFile.setRosterUpload(0);
		uploadFile.setStateId(stateId);
		uploadFile.setDistrictId(districtId);
		uploadFile.setSchoolId(schoolId);
		uploadFile.setFile(destFile);
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(uploadFile, reportUploadCategoryTypeCode);
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
		UploadFileRecord uploadFileRecord = buildUploadFileRecord(
				user, cmpf.getOriginalFilename(), inProgressStatus.getId());
		uploadFileRecordService.insertUploadFile(uploadFileRecord);

		uploadFileProcessor.create(uploadFile, errors, uploadFileRecord.getId());
		//request.getSession().setAttribute("uploadFirstContactFileProcess", futureMav);
		
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("uploadFileRecordId", uploadFileRecord.getId());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
		String modelJson = mapper.writeValueAsString(model);
		return modelJson;
	}
	
	@RequestMapping(value = "monitorFileUploadConfiguration.htm", method = RequestMethod.GET)
	public @ResponseBody String monitorUploadConfigutarionFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("uploadFileRecordId") Long uploadFileRecordId) throws Exception { 
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		UploadFileRecord uploadFileRecord = uploadFileRecordService.selectByPrimaryKeyAndCreateUserId(
				uploadFileRecordId, user.getId());
		Category status = categoryService.selectByPrimaryKey(uploadFileRecord.getStatusId());
		
		if (status.getCategoryCode().equals("COMPLETED") || status.getCategoryCode().equals("FAILED")) {
			return uploadFileRecord.getJsonData();
		}
		return "{\"uploadFileStatus\": \"IN_PROGRESS\"}";
	}
	
	/**
	 * @param orgChildrenIds
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @param filters
	 * @return
	 * @throws NoAccessToResourceException 
	 * @throws JsonProcessingException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "getRostersToView.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getRostersToView(
			@RequestParam("orgChildrenIds") String[] orgChildrenIds,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,    		
			@RequestParam(value = "filters", required = false) String filters) throws NoAccessToResourceException, JsonProcessingException, IOException   {
		    	
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		Integer currentPage = null;
		Integer limitCount = null;
		List<RosterDTO> rosterDTOs;
		Integer totalCount = -1;
		currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        Map<String, Object> criteria = new HashMap<String, Object>();
        long filterOrgId =  NumericUtil.parse(orgChildrenIds[0], -1);
        criteria.put("organizationId", filterOrgId);

		populateCriteria(criteria, filters);			

		
        //If user is a Teacher only return rosters for that teacher
		UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userDetails.getUser().isTeacher()) {
			criteria.put("arrtUserId", userDetails.getUserId());
		}
		Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		List<Authorities> userAuthorities = userDetails.getUser().getAuthoritiesList();
		Long ViewAllRostersAuthorityId =  permissionUtil.getAuthorityId(userAuthorities,RestrictedResourceConfiguration.getViewAllRostersPermissionCode());
		Long ViewRostersAuthorityId =  permissionUtil.getAuthorityId(userAuthorities,RestrictedResourceConfiguration.getViewRostersPermissionCode());
		if(childrenOrgIds.contains(filterOrgId) && (ViewAllRostersAuthorityId != null || ViewRostersAuthorityId != null)){
			criteria.put("assessmenProgramId", userDetails.getUser().getCurrentAssessmentProgramId());

			criteria.put("currentSchoolYear", (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
			
			rosterDTOs = rosterService.getRostersToViewByOrg(criteria, sortByColumn, sortType, (currentPage-1)*limitCount, limitCount);
			 totalCount = 0;

	        if(!rosterDTOs.isEmpty()) {
	        	totalCount = rosterDTOs.get(0).getRoster().getTotalRecords();
	        }
			jqGridJSONModel = RosterDTOJsonConverter.convertToRosterDTOJson(rosterDTOs,totalCount, currentPage, limitCount);
		}else{
			throw new NoAccessToResourceException("Access to this information is restricted.");
		}
		return jqGridJSONModel;
	}
	
	 private void populateCriteria(Map<String, Object> criteria, String filters)
				throws JsonProcessingException, IOException {

			if (null != filters && !filters.equals("")) {
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(filters);

				final JsonNode results = rootNode.get("rules");
				
				for (final JsonNode element : results) {
					criteria.put(element.get("field").asText(), element
							.get("data").asText());
				}
			}
		}

	/**
	 * @param orgChildrenIds
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @param filters
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 * @throws NoAccessToResourceException 
	 */
	@RequestMapping(value = "getEducatorsForRosters.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getEducatorsForRosters(
			@RequestParam("orgChildrenIds") String[] orgChildrenIds,
			@RequestParam("rosterId") long rosterId,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,    		
			@RequestParam(value = "filters", required = false) String filters) throws JsonProcessingException, IOException, NoAccessToResourceException {
		    	
		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		List<User> users;
		Integer totalCount = -1;

		UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
        long filterOrgId =  NumericUtil.parse(orgChildrenIds[0], -1);

		currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        
        Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("organizationId", filterOrgId);
		
		if(userDetails.getUser().isTeacher()) {
			criteria.put("isTeacher", userDetails.getUser().isTeacher());
			criteria.put("educatorId", userDetails.getUserId());
		}
		//::TODO temporary fix... look for permanent fix
		criteria.put("isSysAdmin", true);
		criteria.put("assessmenProgramId", userDetails.getUser().getCurrentAssessmentProgramId());
		populateCriteria(criteria, filters);			
		Long orgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(orgId);
		Long authorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewAllRostersPermissionCode());
		Long viewRostersAuthorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewRostersPermissionCode());
		if((childrenOrgIds.contains(filterOrgId) || (filterOrgId == orgId)) && (authorityId != null || viewRostersAuthorityId != null)){
			users = userService.getByOrganizationWithRosterAssigned(criteria,
					sortByColumn, sortType, (currentPage-1)*limitCount,
					limitCount, rosterId);
			criteria.put("rosterId", rosterId);
			totalCount = userService.countAllTeacherToView(criteria);
			jqGridJSONModel = UsersJsonConverter.convertToUserJsonRoster(users,totalCount, currentPage, limitCount);
		}else{
			throw new NoAccessToResourceException("Forbidden Access.");
		}
		return jqGridJSONModel;
	}


	/**
	 * Modified : Biyatpragyan Mohanty : bmohanty_sta@ku.edu : DE8974 : When using the Select Students filter on the View/Edit Roster, choice 
	 * does not add to Enrolled Students but replaces existing Enrolled Student
	 * @param orgChildrenIds
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @param filters
	 * @return
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "getStudentsForRosters.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getStudentsForRosters(
			@RequestParam("orgChildrenIds") String[] orgChildrenIds,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "rosterId", required = false) String rosterId,
			@RequestParam(value = "filters", required = false) String filters, 
			@RequestParam(value = "_search", required = false) String _search) throws JsonProcessingException, IOException{
		    	
        
        JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		Long organizationId = null;
		List<Enrollment> enrollments;
		Integer totalCount = -1;
		currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        
		organizationId = Long.parseLong(orgChildrenIds[0]);
		
		List<Organization> orgChildren = organizationService.getAllChildren(
				organizationId, true);
		List<Long> orgChildIds = AARTCollectionUtil.getIds(orgChildren);

		UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		if(sortByColumn.contains("genderStr")) {
			sortByColumn = sortByColumn.replace("genderStr", "gender");
		}
		
        Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("organizationId", NumericUtil.parse(orgChildrenIds[0], -1));
		criteria.put("orgChildrenIds", orgChildIds);
		criteria.put("sortByColumn",sortByColumn);
		criteria.put("sortType",sortType);
		criteria.put("educatorId", userDetails.getUserId());
		criteria.put("isTeacher", userDetails.getUser().isTeacher());
		criteria.put("_search", _search);
		criteria.put("assessmenProgramId", userDetails.getUser().getCurrentAssessmentProgramId());
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		 
		if(rosterId!=null) {
			criteria.put("rosterId", NumericUtil.parse(rosterId, -1));
		}
		populateCriteria(criteria, filters);	
		
    	enrollments = enrollemntService.getStudentsByOrgId(criteria, sortByColumn, sortType, 
     			(currentPage-1)*limitCount, limitCount, currentSchoolYear);
		totalCount = enrollemntService.countStudentsByOrgId(criteria, currentSchoolYear);		
        
		
		jqGridJSONModel = StudentEnrollmentsJsonConverter.convertToStudentEnrollmentsJson(
				enrollments,totalCount, currentPage, limitCount);
		
		return jqGridJSONModel;
	}
	
	
	/**
	 * @param orgChildrenIds
	 * @param limitCountStr
	 * @param page
	 * @param sortByColumn
	 * @param sortType
	 * @param filters
	 * @return
	 * @throws NoAccessToResourceException 
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "getStudentEnrollmentsByRosterId.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getStudentEnrollmentsByRosterId(
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,    		
			@RequestParam(value = "filters", required = false) String filters
			) throws NoAccessToResourceException, JsonProcessingException, IOException {
		    	
		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;		
		List<Enrollment> enrollments;
		Integer totalCount = -1;
		
		if(sortByColumn.length()==0) {
			sortByColumn=null;
			sortType=null;
		}

		currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        
        Map<String, Object> criteria = new HashMap<String, Object>();
		if(rosterId!=null) {
			criteria.put("rosterId", rosterId);
		}
		
    	criteria.put("limitCount", limitCount);
		criteria.put("offset", (currentPage-1)*limitCount);
		if(sortByColumn != null)
			criteria.put("sortByColumn", sortByColumn);
		 if(sortType != null)
			criteria.put("sortType", sortType);
		
		
		if(filters != null) {
			populateCriteria(criteria, filters);
		}
		Roster roster = rosterService.selectByPrimaryKey(rosterId);
		long orgId = roster.getAttendanceSchoolId();
		UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		Long authorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewAllRostersPermissionCode());
		Long viewRostersAuthorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewRostersPermissionCode());
		if((childrenOrgIds.contains(orgId) || (orgId == userOrgId)) && (authorityId != null || viewRostersAuthorityId != null)){
			enrollments = enrollemntService.getStudentEnrollmentsByRosterId(criteria);
			totalCount = enrollemntService.countStudentEnrollmentsByRosterId(criteria);		
			jqGridJSONModel = StudentEnrollmentsJsonConverter.convertToStudentEnrollmentsJson(
					enrollments,totalCount, currentPage, limitCount);
		}else{
			throw new NoAccessToResourceException("Forbidden Access.");
		}
		return jqGridJSONModel;
	}

	   /**
     * @param categoryCode
     * @param enableFlag
     * @return
     */
    public final @ResponseBody Boolean getITIconfiguration(
    		@RequestParam("categoryCode") String categoryCode
    		) {
    	Boolean status = categoryService.getStatusITI(categoryCode);
		return status;
    }
    	
	@RequestMapping(value = "getRolesForUser.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getGroupsForUser(@RequestParam("rows") Integer limitCount,
			@RequestParam("page") Integer page){
		LOGGER.trace("Entering getGroupsForUser");
        UserDetailImpl userDetailImpl = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Groups> groups = new ArrayList<Groups>();
        
        if(userDetailImpl.getUser().isSysAdmin()) {
        	groups = groupsService.getAllGroups();
        } else {
	        LOGGER.debug("orgType : "+userDetailImpl.getUser().getCurrentGroupsId());
	        groups = groupsService.getAllGroupsBelowLevel(userDetailImpl.getUser().getCurrentGroupsId());
        }
        
        LOGGER.trace("Leaving getGroupsForUser");
        return GroupsJsonConverter.convertToGroupsJson(groups, page, limitCount);
	}
	
	@RequestMapping(value = "getITIStates.htm", method = RequestMethod.GET)
	public final @ResponseBody JsonResultSet getITIStates() throws JsonProcessingException{

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long assessmentId = userDetails.getUser().getCurrentAssessmentProgramId();

		// If DLM is not selected return nothing unless Global SysAdm, if GSM set assessmentId to 3 (DLM) to pull all DLM states
		boolean GSA = userDetails.getUser().isSysAdmin();
		AssessmentProgram assessmentprogram=assessProgService.findByAbbreviatedName("DLM");
        Long dlmId = null;
        if (assessmentprogram != null) {
              dlmId = assessmentprogram.getId();
        }
		
		if(assessmentId != null && dlmId != null 
				&& assessmentId.intValue() !=  dlmId.intValue()){
			
			if(GSA){
				assessmentId = dlmId;
			}
			else{
				JsonResultSet empty = new JsonResultSet();
				empty.setRecords(0);
				return empty;
			}
		}
		
		Long currentSchoolYear = null;
		if (userDetails.getUser().getContractingOrganization() != null && userDetails.getUser().getContractingOrganization().getCurrentSchoolYear() != null) {
			currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		}
		
		Map<Long,String> dlmStates = organizationService.getStatesBasedOnassessmentProgramId(assessmentId);
		List<TestingCycle> cycles = batchReportProcessService.getCurrentTestCycleDetails(assessmentId, currentSchoolYear, null, null);
		
		// add any data that may be missing
		for (TestingCycle cycle : cycles) {
			List<Long> stateIdsInCycleWindow = otwService.getStateIdsForOTWId(cycle.getOperationalTestWindowId());
			for (Long key : dlmStates.keySet()) {
				if (stateIdsInCycleWindow.contains(key)) {
					List<OrganizationDetail> orgDetails = organizationService.getOrganizationDetailByOrgId(key, cycle.getId());
					if(CollectionUtils.isEmpty(orgDetails)){
						OrganizationDetail newOrg = new OrganizationDetail();
						newOrg.setOrganizationId(key);
						newOrg.setCreatedDate(new Date());
						newOrg.setModifiedDate(new Date());
						newOrg.setCreatedUser((int)userDetails.getUserId());
						newOrg.setModifiedUser((int)userDetails.getUserId());
						newOrg.setActiveFlag(true);
						newOrg.setTestingCycleId(cycle.getId());
						organizationService.addOrganizationDetails(newOrg);
					}
				}
			}
		}
		
		
		List<OrganizationDetail> orgDetails = organizationService.getOrganizationDetails(null);
		
		for(int i = 0; i < orgDetails.size(); i++){
			OrganizationDetail currentOrg = orgDetails.get(i);
			
			if(currentOrg.getActiveFlag() == null || dlmStates.containsKey(currentOrg.getOrganizationId()) != currentOrg.getActiveFlag()){
				currentOrg.setActiveFlag(dlmStates.containsKey(currentOrg.getOrganizationId()));
				currentOrg.setModifiedDate(new Date());
				currentOrg.setModifiedUser((int)userDetails.getUserId());
				organizationService.updateOrganizationDetails(currentOrg);
			}
			
		}
		
		orgDetails = organizationService.getOrganizationDetails(null);
		
		List<Long> userDLMIds = new ArrayList<Long>();
		List<UserAssessmentPrograms> uaps = userService.getInactiveUserRolesForAdmin(userDetails.getUserId());
		for(UserAssessmentPrograms uap: uaps){
			if(uap.getAbbreviatedName().equals("DLM") && organizationService.get(uap.getOrganizationId()).getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)){
				userDLMIds.add(uap.getOrganizationId());
			}
		}
		
		// Remove mappings that aren't equal to current users selected org if they are not GSA
		if(!GSA){
			List<OrganizationDetail> toRemove = new ArrayList<>();

			// Pass 1 - collect delete candidates
			for (OrganizationDetail od : orgDetails) {
				if(!userDLMIds.contains(od.getOrganizationId())){
					toRemove.add(od);
				}
			}
			// Pass 2 - delete
			for (OrganizationDetail tR : toRemove) {
				orgDetails.remove(tR);
			}
		}
		
		JsonResultSet jsonResultSet =  new JsonResultSet();
		List<Row> rows = new ArrayList<Row>();
	    for(int i = 0; i < orgDetails.size(); i++){
	    	if(orgDetails.get(i).getActiveFlag()){
				Row row = new Row();
				row.setId(orgDetails.get(i).getId());
				List<String> jsonrow = orgDetails.get(i).buildJSONRow();
				row.setCell(jsonrow.toArray(new String[0]));
				rows.add(row);
	    	}
	    }
	    jsonResultSet.setRows(rows);
	    jsonResultSet.setRecords(rows.size());
		return jsonResultSet;
		
	}
	

	@RequestMapping(value = "updateITIWindow.htm", method = RequestMethod.POST)
	public @ResponseBody String updateITIWindow(
			@RequestParam("orgIds") String[] orgIds,
			@RequestParam(value="startDate", required = false) String startDate,
			@RequestParam("endDate") String endDate) throws Exception {   
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String response = "";
		int success = 0;
		for(int i = 0; i <orgIds.length; i++){
			OrganizationDetail org = new OrganizationDetail();
			org.setId(Long.parseLong(orgIds[i]));
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
			Date date;
			if(startDate == null || startDate.equals("")){
				
				List<OrganizationDetail> existingDetails = organizationService.getOrganizationDetails(Long.parseLong(orgIds[i]));
				if(existingDetails.get(0) == null || existingDetails.get(0).getItiStartDate() == null){
					date = new Date();
				}else{
					date = existingDetails.get(0).getItiStartDate();
				}
				org.setItiStartDate(date);
			}else{
				date = format.parse(startDate);
				org.setItiStartDate(date);
			}
			
			date = format.parse(endDate);
			org.setItiEndDate(date);
			org.setModifiedDate(new Date());
			org.setModifiedUser((int)userDetails.getUserId());
			success += organizationService.updateOrganizationDetails(org);
		}
		response = String.valueOf(success);
		return  response;
	}

	@RequestMapping(value = "getAllRolePermissions.htm", method = RequestMethod.GET)
	public final @ResponseBody GroupAuthoritiesDto getAllGroupAuthorities(@RequestParam("groupId") Long groupId,
			@RequestParam(value = "organizationId", required = false) Long organizationId,
			@RequestParam(value = "assessmentProgramId", required = false) Long assessmentProgramId,
			@RequestParam(value = "organizationId[]", required = false) List<Long> organizationIdList,
			@RequestParam(value = "assessmentProgramId[]", required = false) List<Long> assessmentProgramIdList,
			@RequestParam(value = "forEdit", required = false) Boolean forEdit) throws Exception {

		if (organizationId == null && assessmentProgramId == null) {
			if (organizationIdList == null && assessmentProgramIdList == null) {
				throw new Exception("No Assesment Program ID and Organization Passed");
			}
		}

		LOGGER.trace("Entering getAllGroupAuthorities");
		GroupAuthoritiesDto authoritiesContainer = new GroupAuthoritiesDto();
		Groups group = groupsService.getGroup(groupId);
		// assign the group
		authoritiesContainer.setGroup(group);
		// add all assigned authorities
		List<Authorities> assignedAuthorities = new ArrayList<Authorities>();
		List<Long> restrictedAuthorities = new ArrayList<Long>();

		if (forEdit != null && forEdit) {
			assignedAuthorities = authService.getByCombinedStatesAssesmentPrograms(groupId, organizationIdList,
					assessmentProgramIdList);
			restrictedAuthorities = authService.getPermissionsToExcludeMultiple(groupId, organizationIdList,
					assessmentProgramIdList);
		} else {
			assignedAuthorities = authService.getByCombinedStateAssessmentProgram(groupId, organizationId,
					assessmentProgramId);
			restrictedAuthorities = authService.getPermissionsToExclude(groupId, organizationId, assessmentProgramId);
		}
		
		List<String> authTabNames = authService.getTabNames();
 		for(String tabName: authTabNames){
 			authoritiesContainer.getTabAuthoritiesMap().put(tabName, new ArrayList<AuthoritiesDTO>());
 			authoritiesContainer.getAuthoritiesTabs().add(new AuthoritiesTab(tabName));
 		}

		// add all unassigned authorities
		List<Authorities> allAuthorities = authService.getAll();
		for (Authorities auth : allAuthorities) {
			AuthoritiesDTO aDTO = new AuthoritiesDTO();
			aDTO.setAssigned(true);
			aDTO.setAuthority(auth);
			aDTO.setAssigned(assignedAuthorities.contains(auth));
			aDTO.setRestricted(restrictedAuthorities.contains(auth.getAuthoritiesId()));
			// Arrange the authorities into tabs.
			authoritiesContainer.addAuthorityByTabName(auth.getTabName(), aDTO);
		}
		
		Map<String, Long> processedGroupingMap = new LinkedHashMap<String, Long>();
		Long processedGroupingCount = 0L;
		// Iterate over all the tabs
		for (String  tabNameKey : authoritiesContainer.getTabAuthoritiesMap().keySet()) {
			// Get the tab authorities based on tabname
			List<AuthoritiesDTO> tabAuthorityDTOs = authoritiesContainer.getTabAuthoritiesMap().get(tabNameKey);
			long tabAuthoritiesSize = tabAuthorityDTOs.size();
			for(AuthoritiesDTO aDTO: tabAuthorityDTOs){
				Authorities authority = aDTO.getAuthority();
				boolean isProcessed = false;
				// if tab name alone exists treat them as tab level properties.
				if(StringUtils.isNotBlank(authority.getTabName()) && !"--NA--".equals(authority.getTabName())){
					//Find the right container and add it.
					List<List<AuthoritiesDTO>> authorityContainers = authoritiesContainer.getAuthoritiesTab(authority.getTabName()).getAuthorityContainers();
					for(List<AuthoritiesDTO> container : authorityContainers){
						int groupingSize = 0;
						String groupingName = authority.getGroupingName();
						if(StringUtils.isNotBlank(groupingName)){
							groupingSize = authoritiesContainer.getGroupingAuthoritiesMap().get(groupingName).size();
							if(processedGroupingMap.get(groupingName) == null){
								processedGroupingMap.put(groupingName, new Long(0L));
							}
							processedGroupingCount = processedGroupingMap.get(groupingName);
						}
						
						if((container.size() - processedGroupingCount) + groupingSize <= (tabAuthoritiesSize/AuthoritiesTab.CONTAINER_SIZE)){
							if(StringUtils.isNotBlank(groupingName)){
								processedGroupingMap.put(groupingName, processedGroupingMap.get(groupingName) + 1L);
							}
							container.add(aDTO);
							isProcessed = true;
						} else {
							int containerIndex = authorityContainers.indexOf(container);
							authorityContainers.get((containerIndex == authorityContainers.size()-1) ? containerIndex : containerIndex+1).add(aDTO);
							isProcessed = true;
						}
						if(isProcessed){
							break;
						}
					}
				}
			}
		}

		// Per US18825 & US18927
		Groups userGroup = groupsService.getGroup(groupId);
		Boolean isUsersLimitedToOnePerRole = false;
		Boolean isUsersLimitPerRoleVisible = false;
		Boolean allUsersLimitedToOnePerRole = true;
		if (userGroup.getGroupCode().equals("DTC") || userGroup.getGroupCode().equals("BTC")) {
			isUsersLimitPerRoleVisible = true;
			boolean rowExists = false;
			if (forEdit != null && forEdit) {
				for (Long orgId : organizationIdList) {
					for (Long apId : assessmentProgramIdList) {
						rowExists = groupsService.isUsersLimitedToOnePerRoleExists(userGroup.getGroupCode(), orgId,
								apId);
						if (rowExists) {
							isUsersLimitedToOnePerRole = groupsService
									.isUsersLimitedToOnePerRole(userGroup.getGroupCode(), orgId, apId);
							if(!isUsersLimitedToOnePerRole){
								allUsersLimitedToOnePerRole = false;
							}
						} else {
							allUsersLimitedToOnePerRole = false;
						}
					}
				}
				isUsersLimitedToOnePerRole = allUsersLimitedToOnePerRole;
			} else {
				rowExists = groupsService.isUsersLimitedToOnePerRoleExists(userGroup.getGroupCode(), organizationId,
						assessmentProgramId);
				if (rowExists) {
					isUsersLimitedToOnePerRole = groupsService.isUsersLimitedToOnePerRole(userGroup.getGroupCode(),
							organizationId, assessmentProgramId);
				}
			}
		}
		authoritiesContainer.setIsUsersLimitedToOnePerRole(isUsersLimitedToOnePerRole);
		authoritiesContainer.setIsUsersLimitPerRoleVisible(isUsersLimitPerRoleVisible);
		LOGGER.trace("Leaving getAllGroupAuthorities");
		return authoritiesContainer;
	}

	
	@RequestMapping(value = "saveRolePermissions.htm", method = RequestMethod.POST)
	public final @ResponseBody boolean saveGroupAuthorities(@RequestParam("groupId") Long groupId,
			@RequestParam("authorities") String[] authorities,
			@RequestParam(value = "organizationId", required = false) Long organizationId,
			@RequestParam(value = "assessmentProgramId", required = false) Long assessmentProgramId,
			@RequestParam(value = "organizationId[]", required = false) List<Long> organizationIdList,
			@RequestParam(value = "assessmentProgramId[]", required = false) List<Long> assessmentProgramIdList,
			@RequestParam(value = "forEdit", required = false) Boolean forEdit) throws Exception {
		// save permissions for a group.

		if (organizationId == null && assessmentProgramId == null) {
			if (organizationIdList == null && assessmentProgramIdList == null) {
				throw new Exception("No Assesment Program ID and Organization Passed");
			}
		}
		if (organizationIdList == null) {
			organizationIdList = new ArrayList<Long>();
			organizationIdList.add(organizationId);
		}
		if (assessmentProgramIdList == null) {
			assessmentProgramIdList = new ArrayList<Long>();
			assessmentProgramIdList.add(assessmentProgramId);
		}

		LOGGER.debug("Saving permissions for group with id {}", groupId);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Authorities> userAuthorities = userDetails.getUser().getAuthoritiesList();
		Long permRoleModify = permissionUtil.getAuthorityId(userAuthorities,
				RestrictedResourceConfiguration.getPermRoleModify());

		// TODO remove existing ids to only save changed values
		for (Long orgId : organizationIdList) {

			for (Long apId : assessmentProgramIdList) {
				if (permRoleModify != null) {
					List<String> a = Arrays.asList(authorities);
					List<Long> auths = new ArrayList<Long>();
					for (String s : a) {
						auths.add(Long.valueOf(s));
					}
					gaService.saveGroupAuthoritiesList(groupId, orgId, apId, auths);
				}
			}

		}
		return true;
	}
	
	@RequestMapping(value = "addStudents.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> addStudents(@RequestParam("orgId") String orgId,
			@RequestParam("stateSchoolId") String stateSchoolId,
			@RequestParam("localSchoolId") String localSchoolId,
			@RequestParam("schoolEntryDate") String schoolEntryDate,
			@RequestParam("districtEntryDate") String districtEntryDate,
			@RequestParam("legalFirstName") String legalFirstName,
			@RequestParam("legalMiddleName") String legalMiddleName,
			@RequestParam("legalLastName") String legalLastName,
			@RequestParam("generation") String generation,
			@RequestParam("gender") String gender,
			@RequestParam("dobDate") String dobDate,
			@RequestParam("comprehensiveRace") String comprehensiveRace,
			@RequestParam("firstLanguage") String firstLanguage,
			@RequestParam("primaryDisability") String primaryDisability,
			@RequestParam("giftedStudent") String giftedStudent,
			@RequestParam("currentGrade") String currentGrade,
			@RequestParam("accountabilitySchoolStudent") String accountabilitySchoolId,
			@RequestParam("accountabilityDistrictStudent") String accountabilityDistrictId,
			@RequestParam("accountabilitySchoolIdentifier") String accountabilitySchoolIdentifier,
			@RequestParam("accountabilityDistrictIdentifier") String accountabilityDistrictIdentifier,
			@RequestParam("residenceDistrictId") String residenceDistrictId,
			@RequestParam("stateEntryDate") String stateEntryDate,
			//@RequestParam("dlmStatus") String dlmStatus,
			@RequestParam("hispanicEthnicity") String hispanicEthnicity,
			@RequestParam("esolParticipationCode") String esolParticipationCode,
			@RequestParam("esolProgramEntryDate") String esolProgramEntryDate,
			@RequestParam("usaEntryDate") String usaEntryDate,
			//@RequestParam("attendanceSchoolId") String attendanceSchoolId,
			@RequestParam("assessmentProgramsStudent") Long[] assessmentPrograms) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<EnrollmentRecord> enrollmentRecords = new ArrayList<EnrollmentRecord>();
		EnrollmentRecord enrollmentRec = new EnrollmentRecord();
		Organization org = organizationService.get(Long.parseLong(orgId));
		Student studentrecord = new Student();
		studentrecord.setComprehensiveRace(comprehensiveRace);
		if(null != gender && !gender.equals(""))
			studentrecord.setGender(Integer.parseInt(gender));
		studentrecord.setGenerationCode(generation);
		studentrecord.setLegalFirstName(legalFirstName);
		studentrecord.setLegalLastName(legalLastName);
		studentrecord.setLegalMiddleName(legalMiddleName);
		if(firstLanguage.length() != 0)
			studentrecord.setFirstLanguage(firstLanguage);
		studentrecord.setStateStudentIdentifier(stateSchoolId);
		if(StringUtils.isNotBlank(dobDate)) {
			studentrecord.setDateOfBirth(DateUtil.parse(dobDate, "MM/dd/yyyy", null));
		}
		studentrecord.setHispanicEthnicity(hispanicEthnicity);
		studentrecord.setPrimaryDisabilityCode(primaryDisability);
		studentrecord.setEsolParticipationCode(esolParticipationCode);
		if(StringUtils.isNotBlank(esolProgramEntryDate)) {
			studentrecord.setEsolProgramEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(esolProgramEntryDate, "US/Central",  "MM/dd/yyyy")
					.getTime()));
		}
		if(StringUtils.isNotBlank(usaEntryDate)) {
			studentrecord.setUsaEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(usaEntryDate, "US/Central",  "MM/dd/yyyy")
					.getTime()));
		}

		enrollmentRec.setStudent(studentrecord);
		//if(null != currentSchoolYear && !currentSchoolYear.equals(""))
			//enrollmentRec.setCurrentSchoolYear(Integer.parseInt(currentSchoolYear));
		
		UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int currentSchoolYear = user.getUser().getContractingOrganization().getCurrentSchoolYear().intValue();
		enrollmentRec.setCurrentSchoolYear(currentSchoolYear);
		
		enrollmentRec.setLocalStudentIdentifier(localSchoolId);
		if(null != orgId && !orgId.equals(""))
			enrollmentRec.setAttendanceSchoolId(Long.parseLong(orgId));
		enrollmentRec.setAttendanceSchoolProgramIdentifier(org
				.getDisplayIdentifier());
		if(StringUtils.isNotBlank(districtEntryDate)) {
			enrollmentRec.setDistrictEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(districtEntryDate, "US/Central",  "MM/dd/yyyy")
					.getTime()));
		}
		if(StringUtils.isNotBlank(schoolEntryDate)) {
			enrollmentRec.setSchoolEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(schoolEntryDate, "US/Central",  "MM/dd/yyyy")
					.getTime()));
		}
		/*if(null != dlmStatus && !dlmStatus.equals(""))
			enrollmentRec.setDlmStatus(Boolean.parseBoolean(dlmStatus));*/
		
		for (Long assessmentProgram : assessmentPrograms) {
			if(assessmentProgram == 3){
				enrollmentRec.setDlmStatus(true);
			}
		}
		
		if(null != giftedStudent && !giftedStudent.equals(""))
			enrollmentRec.setGiftedStudent(Boolean.parseBoolean(giftedStudent));
		
		try {
			enrollmentRec.setAypSchoolIdentifier(accountabilitySchoolIdentifier);
			if(accountabilitySchoolId!=null && !accountabilitySchoolId.equals("")) enrollmentRec.setAypSchoolId(new Long(accountabilitySchoolId));
			
			if(accountabilityDistrictId!=null && !accountabilityDistrictId.equals("")) enrollmentRec.setAccountabilityDistrictId(new Long(accountabilityDistrictId));
			enrollmentRec.setAccountabilityDistrictIdentifier(accountabilityDistrictIdentifier);
			
			enrollmentRec.setCurrentGradeLevel(currentGrade);
			enrollmentRec.setResidenceDistrictIdentifier(residenceDistrictId);
			if(StringUtils.isNotBlank(stateEntryDate)) {
				enrollmentRec.setStateEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(stateEntryDate, "US/Central",  "MM/dd/yyyy")
						.getTime()));
			}
		} catch (AartParseException e) {
            LOGGER.error(e.getMessage(), e);
		}
		
		enrollmentRecords.add(enrollmentRec);

		//Organization Organization Organization rganization currentContext = userService.getOrganizationsByUserId(user.getUserId()).get(0);
		Organization  currentContext = user.getUser().getOrganization();
		
		ContractingOrganizationTree contractingOrganizationTree = organizationService
				.getTree(currentContext);
		Restriction restriction = getEnrollmentRestriction(user);

		List<? extends StudentRecord> studentRecords = studentService
				.verifyStateStudentIdentifiersExist(
						contractingOrganizationTree
								.getContractingOrganizationTreeIds(),
						contractingOrganizationTree.getUserOrganizationTree()
								.getUserOrganizationIds(),
						contractingOrganizationTree
								.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(),
						enrollmentRecords);

		if (CollectionUtils.isNotEmpty(studentRecords)) {
			for (StudentRecord studentRecord : studentRecords) {
				EnrollmentRecord enrollmentRecord = (EnrollmentRecord) (studentRecord);
				if (!studentRecord.isDoReject()) {
					// this is the only line where i need to access enrollment
					// record.in other cases just validateable record is
					// enough.
					enrollmentRecord.getEnrollment().setRestrictionId(
							restriction.getId());
					/*if (enrollmentRecord.getDlmStatus()) {
						AssessmentProgram ap = assessmentProgramDao
								.findByProgramName("Dynamic Learning Maps");
						enrollmentRecord.setAssessmentProgramId(ap.getId());
					}*/					
					enrollmentRecord.setAssessmentProgramIds(assessmentPrograms);
					enrollmentRecord.getStudent().setStateId(user.getUser().getContractingOrgId());
					enrollmentRecord.getEnrollment().setSourceType(SourceTypeEnum.MANUAL.getCode());
					//Changed during DE12837 -- set source type in student
					enrollmentRecord.getStudent().setSourceType(SourceTypeEnum.MANUAL.getCode());
					boolean duplicateStStudIdPresesnt = studentService.isStateStuIdExits(enrollmentRecord.getStudent().getStateStudentIdentifier(), 
							enrollmentRecord.getStudent().getStateId());
					if(duplicateStStudIdPresesnt) {
						result.put("duplicateStateStudentIdFound", (Boolean) true);
						return result;
					}
					enrollmentRecord = enrollemntService.cascadeAddOrUpdate(
							enrollmentRecord, contractingOrganizationTree, org.getId(), (int)(long)user.getUser().getContractingOrganization().getCurrentSchoolYear());
					if (!enrollmentRecord.isDoReject()) {
						if (enrollmentRecord.isCreated()) {
							result.put("success", (Boolean) true);
							return result;
						} else {
							result.put("success", (Boolean) true);
							return result;
						}
					} else {
						result.put("rejected", (Boolean) true);
					    InValidDetail detail = enrollmentRecord.getInValidDetails().get(0);
						result.put("rejectReason", (String) detail.getActualFieldName()+" "+detail.getReason());
						return result;
					}
					
				} 
				else {
					result.put("rejected", (Boolean) true); 
					InValidDetail detail = enrollmentRecord.getInValidDetails().get(0);
					result.put("rejectReason", (String) detail.getActualFieldName()+" "+detail.getReason());
					return result;
				}

			}
		}
		result.put("success", (Boolean) true);
		return result;
	}

    
	/**
	 * @param rosterId
	 * @param name
	 * @param contentAreaId
	 * @param educatorId
	 * @param addStudentIds
	 * @param delStudentIds
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "updateRoster.htm", method = RequestMethod.POST)
	public final @ResponseBody String updateRoster(
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("schoolId") Long schoolId,
			@RequestParam("schoolYear") Integer currentSchoolYear,
			@RequestParam("name") String name,
			@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("educatorId") Long educatorId,
			@RequestParam("addStudentIds") Long[] addStudentIds,
			@RequestParam("delStudentIds") Long[] delStudentIds,
			@RequestParam("courseId") Long courseId
		) {
			UserDetailImpl userDetailImpl = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentUserAssessmentProgramId = userDetailImpl.getUser().getCurrentAssessmentProgramId();
			String assessmentProgramCode = userDetailImpl.getUser().getCurrentAssessmentProgramName();
			Long modifiedUserId = userDetailImpl.getUserId();
			Long schoolYear = (long) currentSchoolYear;
			Roster roster = new Roster();
			roster.setId(rosterId);
			roster.setCourseSectionName(name);
			roster.setTeacherId(educatorId);
			roster.setModifiedUser(userDetailImpl.getUserId());
			roster.setModifiedDate(new Date());
			roster.setStateSubjectAreaId(contentAreaId);
			roster.setStateCoursesId(courseId == 0L ? null : courseId);
			roster.setAttendanceSchoolId(schoolId);
			roster.setCurrentSchoolYear(currentSchoolYear);
			
			if(contentAreaId != null && contentAreaId.longValue() != 0){
				
				List<StudentRoster> studentsAlreadyOnExistingRosters = new ArrayList<StudentRoster>();
				for(Long enrollmentId : addStudentIds){
					Long courseIdTemp = (courseId == 0L) ? null : courseId;
					List<StudentRoster> studentRosterList = rosterService.checkIfRosterExistsForEnrollmentSubjectCourse(enrollmentId, contentAreaId, courseIdTemp, (long) schoolYear, assessmentProgramCode);
					//Student exists on Roster A
					if(studentRosterList!=null && studentRosterList.size()>0)
						//Under roster A, student has complete, pending, unused, or in progress testlets.Student belongs to DLM only.
						//Student either has saved ITI plan or active testsessions in pending, unused, inprogress, complete status. 
						
						//If more than one already exists, must add all? 
						for(StudentRoster studentRoster : studentRosterList){
								studentsAlreadyOnExistingRosters.add(studentRoster);
						}
				}
				
				if(currentUserAssessmentProgramId.equals(assessProgService.findByAbbreviatedName("DLM").getId())
						|| assessmentProgramCode.equalsIgnoreCase(ISMART_PROGRAM_ABBREVIATEDNAME)
						|| assessmentProgramCode.equalsIgnoreCase(ISMART_2_PROGRAM_ABBREVIATEDNAME)){
					for(Long enrollmentId : addStudentIds){
						List<StudentRoster> studentRostersForExitedStudentsList = rosterService.checkForTestSessionsOnExitedRostersAlso(enrollmentId, roster.getStateSubjectAreaId(), roster.getStateCoursesId(), (long) roster.getCurrentSchoolYear(), assessmentProgramCode);
						studentsAlreadyOnExistingRosters.addAll(studentRostersForExitedStudentsList);
					}
					if((studentsAlreadyOnExistingRosters != null && studentsAlreadyOnExistingRosters.size() > 0) ||
					   (addStudentIds != null && addStudentIds.length > 0) ||
					   (delStudentIds != null && delStudentIds.length > 0) ){
						// The system should remove student from Roster A.
						// The system should automatically remove the student from the old roster and transfer complete 
						// (complete should be displayed at old and new school), pending, unused, or in progress testlets to the new roster.
						// Old roster should be able to see completed testlets in historical information.
						HashMap<String, Boolean> transferStatus = rosterService.automatedRosterTransferUpdatesToEditRoster(roster, addStudentIds, delStudentIds, studentsAlreadyOnExistingRosters, modifiedUserId, assessmentProgramCode);
						
						if(transferStatus.get("duplicate")!=null && transferStatus.get("duplicate")) 
							return "{\"duplicate\":\"true\"}";
					
						if((transferStatus.get("automatedRosterTransferComplete")!=null && transferStatus.get("automatedRosterTransferComplete")) && 
								(transferStatus.get("status")!=null && transferStatus.get("status")) && (transferStatus.get("disabledRoster")!=null && transferStatus.get("disabledRoster"))) {
							return "{\"success\":\"true\", \"disabledRoster\":\"true\"}";
						} else if ((transferStatus.get("automatedRosterTransferComplete")!=null && transferStatus.get("automatedRosterTransferComplete")) && 
								(transferStatus.get("status")!=null && transferStatus.get("status"))) {
							return "{\"success\":\"true\"}";
						}
						
						if(transferStatus.get("status")!=null && transferStatus.get("status"))
							return "{\"success\":\"true\"}";
					}
					else{
						int status = rosterService.updateRosterWithEnrollments(roster, addStudentIds, delStudentIds);
						
						if(status == 1)
							return "{\"success\":\"true\"}";
						else if(status == -1)
							return "{\"duplicate\":\"true\"}";
					}
				} else{
					int status = rosterService.updateRosterWithEnrollments(roster, addStudentIds, delStudentIds);
					
					if(status == 1)
						return "{\"success\":\"true\"}";
					else if(status == -1)
						return "{\"duplicate\":\"true\"}";

				}
			}
				
			
			return "{\"success\":\"false\"}";
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
	    //if no upload permission check for add student permission
	    if(restriction == null) {
	    	restriction = resourceRestrictionService.getResourceRestriction(
		            userDetails.getUser().getOrganization().getId(),
		            permissionUtil.getAuthorityId(
		                    userDetails.getUser().getAuthoritiesList(),
		                    RestrictedResourceConfiguration.getAddEnrollmentPermissionCode()),
		                    null,
		            restrictedResourceConfiguration.getEnrollmentResourceCategory().getId());
	    }
	    return restriction;
	}
	
	/**
	 * @param orgIds
	 * @param name
	 * @param contentAreaId
	 * @param educatorId
	 * @param addStudentIds
	 * @param courseId
	 * @return
	 */
	@RequestMapping(value = "createRoster.htm", method = RequestMethod.POST)
	public final @ResponseBody String createRoster(
			@RequestParam("orgIds") Long[] orgIds,
			@RequestParam("name") String name,
			@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("educatorId") Long educatorId,
			@RequestParam("addStudentIds") Long[] addStudentIds,
			@RequestParam("courseId") Long courseId
		) {
			UserDetailImpl userDetailImpl
	        	= (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long currentUserAssessmentProgramId = userDetailImpl.getUser().getCurrentAssessmentProgramId();
			String assessmentProgramCode = userDetailImpl.getUser().getCurrentAssessmentProgramName();
			//Find the restriction for what the user is trying to do on this page.
	        Restriction restriction = resourceRestrictionService.getResourceRestriction(
	                userDetailImpl.getUser().getOrganization().getId(),
	                permissionUtil.getAuthorityId(
	                        userDetailImpl.getUser().getAuthoritiesList(),
	                        RestrictedResourceConfiguration.getUploadRosterPermissionCode()),
	                permissionUtil.getAuthorityId(
	                        userDetailImpl.getUser().getAuthoritiesList(),
	                        RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
	                restrictedResourceConfiguration.getRosterResourceCategory().getId());
	        
	        Long modifiedUserId = userDetailImpl.getUserId();
			Long schoolYear = userDetailImpl.getUser().getContractingOrganization().getCurrentSchoolYear();
	        
			if(contentAreaId!=null && contentAreaId.longValue()!=0 && addStudentIds!=null && addStudentIds.length >0 && orgIds!=null && orgIds.length>0){
				
				//Adding student to roster B 
				Roster roster = new Roster();
				roster.setAttendanceSchoolId(orgIds[orgIds.length-1]);
				roster.setAypSchoolId(orgIds[orgIds.length-1]);
				roster.setCourseSectionName(name);
				roster.setTeacherId(educatorId);
				roster.setCreatedUser(modifiedUserId);
				roster.setModifiedUser(modifiedUserId);
				if (restriction != null) {
					roster.setRestriction(restriction);
					roster.setRestrictionId(restriction.getId());
				} else {
					LOGGER.info("No restriction was found for user org: "+userDetailImpl.getUser().getOrganization().getId()
							+" with permission code: "+RestrictedResourceConfiguration.getUploadRosterPermissionCode()
							+" and differential permission code: "+RestrictedResourceConfiguration.getViewAllRostersPermissionCode()
							+" and restricted resource type id: "+restrictedResourceConfiguration.getRosterResourceCategory().getId());
				}
				roster.setStateSubjectAreaId(contentAreaId);
				roster.setStateCoursesId(courseId);
				roster.setSourceType(SourceTypeEnum.MANUAL.getCode());
				roster.setCurrentSchoolYear((int) (long) schoolYear);
				
				List<StudentRoster> studentsAlreadyOnExistingRosters = new ArrayList<StudentRoster>();
				for(Long enrollmentId : addStudentIds){
					 
					List<StudentRoster> studentRosterList = rosterService.checkIfRosterExistsForEnrollmentSubjectCourse(enrollmentId, contentAreaId, courseId, schoolYear, assessmentProgramCode);
					//Student exists on Roster A
					if(studentRosterList!=null && studentRosterList.size()>0){
						//Under roster A, student has complete, pending, unused, or in progress testlets.Student belongs to DLM only.
						//Student either has saved ITI plan or active testsessions in pending, unused, inprogress, complete status. 
						for(StudentRoster studentRoster : studentRosterList){
							studentsAlreadyOnExistingRosters.add(studentRoster);
						}
					}
				}
				if(currentUserAssessmentProgramId.equals(assessProgService.findByAbbreviatedName("DLM").getId())
						|| assessmentProgramCode.equalsIgnoreCase(ISMART_PROGRAM_ABBREVIATEDNAME)
						|| assessmentProgramCode.equalsIgnoreCase(ISMART_2_PROGRAM_ABBREVIATEDNAME)){
				
					for(Long enrollmentId : addStudentIds){
						List<StudentRoster> studentRostersForExitedStudentsList = rosterService.checkForTestSessionsOnExitedRostersAlso(enrollmentId, roster.getStateSubjectAreaId(), roster.getStateCoursesId(), (long) roster.getCurrentSchoolYear(), assessmentProgramCode);
						studentsAlreadyOnExistingRosters.addAll(studentRostersForExitedStudentsList);
					}
					if(studentsAlreadyOnExistingRosters!=null && studentsAlreadyOnExistingRosters.size()>0){
						// The system should remove student from Roster A.
						// The system should automatically remove the student from the old roster and transfer complete 
						// (complete should be displayed at old and new school), pending, unused, or in progress testlets to the new roster.
						// Old roster should be able to see completed testlets in historical information. 
						HashMap<String, Boolean> transferStatus = rosterService.automatedRosterTransferUpdatesToCreateRoster(roster, addStudentIds, studentsAlreadyOnExistingRosters, modifiedUserId, assessmentProgramCode);
						
						if(transferStatus.get("duplicate")!=null && transferStatus.get("duplicate")) 
							return "{\"duplicate\":\"true\"}";
					
						if((transferStatus.get("automatedRosterTransferComplete")!=null && transferStatus.get("automatedRosterTransferComplete")) && 
								(transferStatus.get("status")!=null && transferStatus.get("status")))
							return "{\"success\":\"true\"}";
						
						if(transferStatus.get("status")!=null && transferStatus.get("status"))
							return "{\"success\":\"true\"}";
					}
					else{
						int status = rosterService.createRoster(roster, addStudentIds);
					
						if(status == 1)
							return "{\"success\":\"true\"}";
						else if(status == 0)
							return "{\"duplicate\":\"true\"}";
					}	
				} else {
					int status = rosterService.createRoster(roster, addStudentIds);
					
					if(status == 1)
						return "{\"success\":\"true\"}";
					else if(status == 0)
						return "{\"duplicate\":\"true\"}";
				}	
			}
			
			return "{\"success\":\"false\"}";
	}
	
	@RequestMapping(value = "checkForMultipleRostersToCreateRoster.htm", method = RequestMethod.GET)
	public final @ResponseBody List<StudentRoster> checkForMultipleRostersToCreateRoster(
			@RequestParam("orgIds") Long[] orgIds,
			@RequestParam("name") String name,
			@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("educatorId") Long educatorId,
			@RequestParam("addStudentIds") Long[] addStudentIds,
			@RequestParam("courseId") Long courseId
		) {
		
		UserDetailImpl userDetailImpl = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long schoolYear = userDetailImpl.getUser().getContractingOrganization().getCurrentSchoolYear();
		HashMap<Long, StudentRoster> multipleRostersForStudent = new HashMap<Long, StudentRoster>();
		String assessmentProgramCode = userDetailImpl.getUser().getCurrentAssessmentProgramName();
		
		for(Long enrollmentId : addStudentIds){
			//Test the scenario where we are checking for students exited from previous rosters 
			//and are not on any active rosters but still have active testsesions
			List<StudentRoster> studentRosterList = rosterService.checkIfRosterExistsForEnrollmentSubjectCourse(enrollmentId, contentAreaId, courseId, schoolYear, assessmentProgramCode);
			
			if(studentRosterList!=null && studentRosterList.size()>0){
				for(StudentRoster studentRoster : studentRosterList){
					if(!multipleRostersForStudent.containsKey(studentRoster.getStudent().getId())){
						multipleRostersForStudent.put(studentRoster.getStudent().getId(), studentRoster);
					}
				}
			}
		}
		
		List<StudentRoster> studentsAlreadyOnExistingRosters = new ArrayList<StudentRoster>(multipleRostersForStudent.values());
		
		return studentsAlreadyOnExistingRosters;
	}
	
	@RequestMapping(value = "checkForMultipleRostersToEditRoster.htm", method = RequestMethod.GET)
	public final @ResponseBody List<StudentRoster>  checkForMultipleRostersToEditRoster(
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("schoolId") Long schoolId,
			@RequestParam("schoolYear") Integer schoolYearOnRoster,
			@RequestParam("name") String name,
			@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("educatorId") Long educatorId,
			@RequestParam("addStudentIds") Long[] addStudentIds,
			@RequestParam("delStudentIds") Long[] delStudentIds,
			@RequestParam("courseId") Long courseId
		) {
		
			UserDetailImpl userDetailImpl = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String assessmentProgramCode = userDetailImpl.getUser().getCurrentAssessmentProgramName();
			
			Long schoolYear = (long) schoolYearOnRoster;
			courseId = courseId == 0L ? null : courseId;		
			HashMap<Long, StudentRoster> multipleRostersForStudent = new HashMap<Long, StudentRoster>();
			
			for(Long enrollmentId : addStudentIds){
				//Test the scenario where we are checking for students exited from previous rosters 
				//and are not on any active rosters but still have active testsesions
				List<StudentRoster> studentRosterList = rosterService.checkIfRosterExistsForEnrollmentSubjectCourse(enrollmentId, contentAreaId, courseId, schoolYear, assessmentProgramCode);
				
				if(studentRosterList!=null && studentRosterList.size()>0){
					for(StudentRoster studentRoster : studentRosterList){
						if(!multipleRostersForStudent.containsKey(studentRoster.getStudent().getId())){
							multipleRostersForStudent.put(studentRoster.getStudent().getId(), studentRoster);
						}
					}
				}
			}
			
			List<StudentRoster> studentsAlreadyOnExistingRosters = new ArrayList<StudentRoster>(multipleRostersForStudent.values());
			return studentsAlreadyOnExistingRosters;	
	}
	
	@RequestMapping(value = "getRosterDropdownData.htm", method = RequestMethod.GET)
	public @ResponseBody String getRosterDropdownData() throws Exception {
		//List<GradeCourse> gradeCourses = new ArrayList<GradeCourse>();
		List<ContentArea> contentAreas= new ArrayList<ContentArea>();
		  
		//gradeCourses = gradeCourseService.selectAllGradeCourses();		
		contentAreas = contentAreaService.selectAllContentAreas();
			
		Map<String,Object> model = new HashMap<String,Object>();
	    //model.put("gradeCourses", gradeCourses);
	    model.put("contentAreas", contentAreas);
	    
	    ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
		String modelJson = mapper.writeValueAsString(model);
		return modelJson;
	}
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15421 : Roster - refactor Add Roster manually page
	 * Get all courses that are related a content area, This is achieved by getting the gradecourse data
	 * that has course column as true i.e. they are only courses.
	 * @param contentAreaId
	 * @return
	 */
	@RequestMapping(value = "getCoursesByContentArea.htm", method = RequestMethod.GET)
	public @ResponseBody String getCoursesByContentArea(@RequestParam("contentAreaId") Long contentAreaId) throws Exception {
		List<GradeCourse> courses= new ArrayList<GradeCourse>();		  		
		courses = gradeCourseService.getCoursesByContentArea(contentAreaId);			
		Map<String,Object> model = new HashMap<String,Object>();
	    model.put("courses", courses);
	    
	    ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL); // no more null-valued properties
		String modelJson = mapper.writeValueAsString(model);
			return modelJson;
	   		   	   
	}
	
	//BEGIN Copied from UploadController
	/**
     * meta data for record types.
     */
    private Map<Long, Category> recordTypeIdMap = new HashMap<Long, Category>();
	
    private Category scrsRecordType;

	private Category firstContactRecordType;
	
	 /**
     * set the metadata.
     */
    @PostConstruct
    public final void setMetaData() {
        setRecordTypes();
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
        //this.enrollmentRecordType = getRecordType(uploadSpecification.getEnrollmentRecordType());
        //this.testRecordType = getRecordType(uploadSpecification.getTestRecordType());
        //this.userRecordType = getRecordType(uploadSpecification.getUserRecordType());
        //this.orgRecordType =  getRecordType(uploadSpecification.getOrgRecordType());
        //this.personalNeedsProfileRecordRecordType =  getRecordType(
        //		uploadSpecification.getPersonalNeedsProfileRecordType());
        this.firstContactRecordType =  getRecordType(uploadSpecification.getFirstContactRecordType());
    }
  //END Copied from UploadController
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
	   /**
  * @param orgStateId
  * @return
  */
   @RequestMapping(value = "getCurrentSchoolYear.htm", method = RequestMethod.GET)
 public final @ResponseBody Map<String, Object> getCurrentSchoolYear(
 		@RequestParam("orgStateId") String orgStateId
 		) {
	  Map<String, Object> reponse =  new HashMap<String, Object>();
	  int year = getCurrentSchoolYear( Long.parseLong(orgStateId));
	  reponse.put("currentSchoolYear", Integer.toString(year));
	return reponse;
 }
   
   @RequestMapping(value = "getContractOrgDetails.htm", method = RequestMethod.GET)
 public final @ResponseBody Map<String, Object> getContractOrg() {
	  Map<String, Object> reponse =  new HashMap<String, Object>();	  
  	  UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  	  Organization organization = userDetails.getUser().getContractingOrganization();	
	  reponse.put("organization", organization);
	return reponse;
 }
   
   @RequestMapping(value = "getAttendanceSchoolDisplayIdentifier.htm", method = RequestMethod.GET)
   public final @ResponseBody Map<String, Object> getAttendanceSchoolDisplayIdentifier(
   		@RequestParam("orgSchoolId") Long orgSchoolId
   		) {
  	  Map<String, Object> reponse =  new HashMap<String, Object>();
  	  
  	  Organization organization=organizationService.get(orgSchoolId);
  	  
  	  String attendanceSchoolDisplayIdentifier=organization.getDisplayIdentifier();
  	  
  	  
  	  reponse.put("attendanceSchoolDisplayIdentifier",attendanceSchoolDisplayIdentifier);
  	return reponse;
   }
   
   @RequestMapping(value = "getAttendanceDistrictDisplayIdentifier.htm", method = RequestMethod.GET)
   public final @ResponseBody Map<String, Object> getAttendanceDistrictDisplayIdentifier(
   		@RequestParam("orgDistrictId") Long orgDistrictId
   		) {
  	  Map<String, Object> reponse =  new HashMap<String, Object>();
  	  
  	  Organization organization=organizationService.get(orgDistrictId);
  	  String attendanceDistrictDisplayIdentifier=organization.getDisplayIdentifier();
  	  
  	  reponse.put("attendanceDistrictDisplayIdentifier", attendanceDistrictDisplayIdentifier);
  	  
  	return reponse;
   }
   
   /**
    * Get options info for selects in addStudents page.
    * @param orgStateId
    * @return map contains info for different 
    */
   @RequestMapping(value = "getAddStudentsFormData.htm", method = RequestMethod.POST)
   public final @ResponseBody Map<String, Object> getAddStudentsFormData() {
  	  Map<String, Object> reponse =  new HashMap<String, Object>();
  	  
  	  //Primary Disability Code
  	  CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
 	  CategoryExample categoryExample = new CategoryExample();
  	  categoryTypeExample.createCriteria().andTypeCodeEqualTo("PRIMARY_DISABILITY_CODES");
  	  categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
  	  categoryExample.setOrderByClause("categoryname");
  	  List<Category> primaryDisabilityCode = categoryService.selectByCategoryType(categoryExample);
  	  reponse.put("primaryDisabilityCode", primaryDisabilityCode);
  	   	  
  	  //First Language
  	  categoryTypeExample.clear();
  	  categoryExample.clear();
  	  categoryTypeExample.createCriteria().andTypeCodeEqualTo("FIRST_LANGUAGE");
 	  categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
 	  categoryExample.setOrderByClause("categoryname"); 
  	  List<Category> firstLanguage = categoryService.selectByCategoryType(categoryExample);
  	  reponse.put("firstLanguage", firstLanguage);
  	  
  	  //Comprehensive Race
  	  categoryTypeExample.clear();
 	  categoryExample.clear();
  	  categoryTypeExample.createCriteria().andTypeCodeEqualTo("COMPREHENSIVE_RACE");
 	  categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
 	  categoryExample.setOrderByClause("categoryname");
  	  List<Category> comprehensiveRace = categoryService.selectByCategoryType(categoryExample);
  	  reponse.put("comprehensiveRace", comprehensiveRace);
  	  
  	  //current grade
  	  //GradeCourseExample example = new GradeCourseExample();
  	  //example.createCriteria().andGradeLevelBetween(1, 12);
  	  //example.setOrderByClause("gradelevel asc");
 	  List<GradeCourse> currentGrade = gradeCourseService.getAllIndependentGrades();
  	  reponse.put("currentGrade", currentGrade);
  	  
  	  //Esol participationcode
  	  List<AppConfiguration> esolParticipationcodes = appConfigurationService.selectByAttributeType(CommonConstants.ESOL_PARTICIPATION_CODE_ATTRIBUTE_TYPE);
  	  String assessmentProgramESOLParticipationCode = appConfigurationService.getByAttributeCode(CommonConstants.ESOL_PARTICIPATION_CODE_ASSESSMENT_PROGRAM);
  	  List<String> assessmentProgramList = new ArrayList<String>();
		if (StringUtils.isNotBlank(assessmentProgramESOLParticipationCode)
				&& assessmentProgramESOLParticipationCode.contains(",")) {
			String aPrgm[] = assessmentProgramESOLParticipationCode.split("\\,");
			if (aPrgm.length > 0) {
				for (int j = 0; j < aPrgm.length; j++) {
					assessmentProgramList.add(aPrgm[j]);
				}
			}
		} else {
			assessmentProgramList.add(assessmentProgramESOLParticipationCode);
		}
  	  
  	  UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  User user = userDetails.getUser();
	  Organization current = user.getOrganization(); 
    
 	  Iterator<AppConfiguration> iterator = esolParticipationcodes.iterator();
		while (iterator.hasNext()) {
			AppConfiguration next = iterator.next();
			if (assessmentProgramList.contains(user.getCurrentAssessmentProgramName())) {
				if (next.getAttributeValue().equals("4")) {
					iterator.remove();
				}
			} else {
				if (next.getAttributeValue().equals("7")||next.getAttributeValue().equals("8")) {
					iterator.remove();
				}
			}
		}
 	  Collections.sort(esolParticipationcodes, new Comparator<AppConfiguration>() {
 	    public int compare(AppConfiguration ap1, AppConfiguration ap2) {
 	        return ap1.getAttributeName().compareTo(ap2.getAttributeName());
 	    }
 	  });
 	 reponse.put("esolParticipationcode", esolParticipationcodes);
	 
 	 //Generation
  	  List<AppConfiguration> generationCodes = appConfigurationService.selectByAttributeType(CommonConstants.GENERATION_ATTRIBUTE_TYPE);
 	  reponse.put("generation", generationCodes);
  	  
 	  //Gender
  	  List<AppConfiguration> genderCodes = appConfigurationService.selectByAttributeType(CommonConstants.GENDER_ATTRIBUTE_TYPE);
 	  reponse.put("gender", genderCodes);
 	  
 	  //Hispanic Ethinicity
 	// F933-Code is added based on assessment program id drop down value needs to be displayed.
  	  List<AppConfiguration> hispanicEthinicityCodes = appConfigurationService.selectByAttributeTypeAndAssessmentProgramIdForHispanic(CommonConstants.HISPANIC_CODE_ASSESSMENT_PROGRAM,userDetails.getUser().getCurrentAssessmentProgramId());
  	  if(hispanicEthinicityCodes.size()>0){
  		reponse.put("hispanicEthinicity", hispanicEthinicityCodes);
  		  
  	  }
  	  else {
  		 List<AppConfiguration> hispanicEthinicityCodesDefault = appConfigurationService.selectByAttributeTypeAndDefaultAssessment(CommonConstants.HISPANIC_ETHINICITY_ATTRIBUTE_TYPE);
  		 reponse.put("hispanicEthinicity", hispanicEthinicityCodesDefault);
  	  }
 	  
 	  //Gifted Student
  	  List<AppConfiguration> giftedStudentCodes = appConfigurationService.selectByAttributeType(CommonConstants.GIFTED_STUDENT_ATTRIBUTE_TYPE);
 	  reponse.put("giftedStudent", giftedStudentCodes);
  	  
  	  //Assessment Program
  	  
      //TODO check contract organization tree to get APs
      List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
      List<String> validAssessmentPrograms = Arrays.asList(reportingAssessmentPrograms.split("\\s*,\\s*"));

      if (current != null) {
        List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(current.getId());
        for (OrgAssessmentProgram oap : orgAssessProgs) {
        	if (validAssessmentPrograms.contains(oap.getAssessmentProgram().getProgramName())){
				AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
				assessmentProgramDto.setAssessmentProgram(oap.getAssessmentProgram());
				assessmentPrograms.add(assessmentProgramDto);
        	}
         }
      }
      reponse.put("assessmentPrograms", assessmentPrograms);
  	  
  	return reponse;
   }   
   
   private String getDLMBatchAutoMultiAssignConfigs(){
	   StringBuffer configInfo = new StringBuffer();
	   configInfo.append("ELA : " + elaMultiLimit);
	   configInfo.append("<br/>");
	   configInfo.append("M : " + mMultiLimit);
	   configInfo.append("<br/>");
	   configInfo.append("Sci : " + sciMultiLimit);
	   return configInfo.toString();
   }
   
   @RequestMapping(value = "batchUploadProcessStart.htm", method = RequestMethod.POST)
	public final @ResponseBody Integer batchUploadProcessStart() throws Exception {
	   batchUploadProcessStarter.startBatchUploadProcess();
	   return null;
   }

   /*
    * UnEnroll the Selected Student 
    */
	@RequestMapping(value = "UnEnrollSelectedStudent.htm", method = RequestMethod.POST)
	public final @ResponseBody String UnEnrollSelectedStudent(
			@RequestParam("stateStudentIdentifier") String stateStudentIdentifier,
			@RequestParam("exitReason") String exitReason, 
			@RequestParam("exitDate") String date,
			@RequestParam("currentSchoolYear")int currentSchoolYear,
			@RequestParam("selectedOrg")long selectdOrgId,
			@RequestParam("attendanceSchoolDisplayIdentifiers") String attendanceSchoolDisplayIdentifiers 
			) throws ParseException{
		
		UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
       Organization currentContext = organizationService.get(user.getUser().getCurrentOrganizationId());
       ContractingOrganizationTree
       contractingOrganizationTree = organizationService.getTree(currentContext);
       
       //DE17697: Use organizations timezone to be consistent with dates entered from enrollment upload
       String timeZone = organizationDao.getTimeZoneForOrg(selectdOrgId);
       if(StringUtils.isEmpty(timeZone)){
       	timeZone = "US/Central";
       }
              
		Date exitDate = new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(date, timeZone,  "MM/dd/yyyy")
				.getTime());

		enrollmentService.processExit(attendanceSchoolDisplayIdentifiers , contractingOrganizationTree, stateStudentIdentifier, exitReason, exitDate, currentSchoolYear, user.getUser(),selectdOrgId);
		
		return "{\"success\":\"true\"}";
	}
	
	@RequestMapping(value = "getTestTypeBySubject.htm", method = RequestMethod.POST)
	public final @ResponseBody List<TestType> getTestTypeUsingAssessmentProgram(
			@RequestParam("subjectCode") String subjectCode,
			@RequestParam("assessmentProgramId") Long assessmentProgramId
			
			) throws ParseException{
		
		List<TestType> testTypes = new ArrayList<TestType>();
		
		testTypes = testTypeService.getTestTypeBySubjectIdAssessmentProgram(subjectCode,assessmentProgramId); 		
		
		return testTypes;
	}

	 @RequestMapping(value = "removeExternalLink.htm")
	    public final @ResponseBody String removeExternalLink(HttpServletResponse response, HttpServletRequest request)
	            throws IOException {
		 HttpSession session = request.getSession();
		   session.setAttribute("externalLink", false);
		   
		   return "{\"success\":\"true\"}";
	    }
	 
	@RequestMapping(value = "checkPermissionsConflict.htm", method = RequestMethod.GET)
	public final @ResponseBody String checkPermissionsConflict(
			@RequestParam("organizationId[]") List<Long> organizationIdList,
			@RequestParam("assessmentProgramId[]") List<Long> assesmentProgramIdList,
			@RequestParam("groupId") Long groupId) {
		LOGGER.trace("Entering getAuthoritiesByGroupId");
		// List<Authorities> authorities = authService.getByGroupId(groupId);
		Boolean ab = authService.checkPermissionsConflict(organizationIdList, assesmentProgramIdList, groupId);
		LOGGER.trace("Leaving getAuthoritiesByGroupId");
		return "{\"conflict\":\"" + ab.toString() + "\"}";
	}

	static Comparator<Organization> OrgComparator = new Comparator<Organization>() {
		public int compare(Organization org1, Organization org2) {
			return org1.getOrganizationName().compareToIgnoreCase(org2.getOrganizationName());
		}
	};

	private UserSecurityAgreement userSecurityAgreement;

    @RequestMapping(value = "getAppMetaData.htm", method = RequestMethod.GET)
	public final ModelAndView getManifestInfo(HttpServletResponse response, HttpServletRequest request) {
    	ModelAndView mav = new ModelAndView("metaData");
		String buildNumber = "";
		String builtBy = "";
		try {
			InputStream is = request.getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
			if (is != null) {
				Manifest manifest = new Manifest(is);
				Attributes mainAttribs = manifest.getMainAttributes();
				buildNumber = mainAttribs.getValue("Build-Number");
				builtBy = mainAttribs.getValue("Built-By");
			}
		} catch (Exception e1) {
			// Silently ignore wrong manifests on classpath?
		}
		mav.addObject("buildNumber", buildNumber);
		mav.addObject("builtBy",builtBy );
		return mav;
	}
    
 
    @RequestMapping(value = "findStudentIfExist.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> findStudentAlreadyExists(@RequestParam("stateStudentId") String stateStudentId) {
    	
	  	Map<String, Object> response =  new HashMap<String, Object>();
	  	      	
		UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
		
		Long editStudentAuthorityId =  permissionUtil.getAuthorityId(user.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getEditStudentPermissionCode());
		
		Map<String, AppConfiguration> appConfigurationMap =  appConfigurationService.selectIdMapByAttributeType(CommonConstants.APP_CONFIGURATION_ATTR_TYPE_FOR_ADD_STUDENT_WARNING_MESSAGE);
		
		int count = enrollmentService.findEnrollmentBySSIDAndOrgId(stateStudentId, user.getUser().getCurrentAssessmentProgramId(), user.getUser().getContractingOrgId(), user.getUser().getContractingOrganization().getCurrentSchoolYear(), user.getUser().getCurrentOrganizationId());
		//If student is already active in current logged in context
		if(count>0) {
			response.put("status", CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_ACTIVE);	
			response.put("message", appConfigurationMap.get(CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_ACTIVE).getAttributeName());
		}else {
			
			Enrollment enrollment = enrollemntService.findStudentBasedOnStateStudentIdentifier(stateStudentId, user.getUser().getContractingOrgId());
			
			if (enrollment==null) {
				// if we don't find any in the regular database, check the data warehouse for legacy data
				LOGGER.debug("Did not find enrollments for SSID " + stateStudentId + " in regular database, checking data warehouse...");
				enrollment = dataExtractService.findStudentBasedOnStateStudentIdentifier(stateStudentId, user.getUser().getContractingOrgId());
				LOGGER.debug("Found " + (enrollment==null ? "no " : "") + "enrollment records for SSID " + stateStudentId + " in data warehouse");
			}
			
			String status = enrollemntService.findStateStudentIdentifierStatus(enrollment, user, editStudentAuthorityId);
			
			if(appConfigurationMap!=null && !appConfigurationMap.isEmpty()) {
				
				if(StringUtils.equals(status, CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_BUT_NOT_ACTIVE)){
					response.put("message", appConfigurationMap.get(CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_BUT_NOT_ACTIVE).getAttributeName());
				}
				else if(StringUtils.equals(status, CommonConstants.STATE_STUDENT_IDENTIFIER_SHOW_ACTIVATE)){
					response.put("message", appConfigurationMap.get(CommonConstants.STATE_STUDENT_IDENTIFIER_SHOW_ACTIVATE).getAttributeName());
				}
				else if(StringUtils.equals(status, CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ORG)){
					response.put("message", appConfigurationMap.get(CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ORG).getAttributeName());
				}
				else if(StringUtils.equals(status, CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_ACTIVE)){
					response.put("message", appConfigurationMap.get(CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_ACTIVE).getAttributeName());
				}
				else if(StringUtils.equals(status, CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ASSESSMENTPROGRAM)){
					response.put("message", appConfigurationMap.get(CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ASSESSMENTPROGRAM).getAttributeName());
				}
			}else {
				status = CommonConstants.ERROR_ADD_STATE_STUDENT_IDENTIFIER_PAGE;
			}
			
			response.put("enrollment", enrollment);
			response.put("status", status);
		}
		
		return response;
	}
      
    @RequestMapping(value = "getAccountabilityDistricts.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Organization> getAllChildrenWithParentByOrgTypeCode(String orgType) {
    	LOGGER.trace("Entering the getAccountabilitySchoolDistricts method.");

    	UserDetailImpl user = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	    	
    	Long orgId = null;
    	/* 
    	if (user.getUser().getCurrentOrganizationType().equals("ST")) {
    		 orgId = user.getUser().getContractingOrganization().getId();
		 }else if (user.getUser().getCurrentOrganizationType().equals("DT")) {
			 orgId = user.getUser().getCurrentOrganizationId();
		 }
		 else if (user.getUser().getCurrentOrganizationType().equals("SCH")) {				
			 Organization district = orgService.getDistrictBySchoolOrgId(user.getUser().getCurrentOrganizationId());
			 orgId = district.getId();
		 }	
    	*/
    	// Changed to allow accountability district to be any district in state for any user.
    	orgId = user.getUser().getContractingOrganization().getId();
    	
        List<Organization> districts = orgService.getAllChildrenWithParentByOrgTypeCode(orgId, orgType);      
        Collections.sort(districts, OrgComparator);
        
        LOGGER.trace("Leaving the getAccountabilitySchoolDistricts method.");
        return districts;
    }
    
    /**
     * @param orgId
     * @return Organization List
     */
    @RequestMapping(value = "getAccountabilitySchools.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getAccountabilitySchools(Long orgId){
    	
    	List<Organization> organizations = null;

		organizations = orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_SCHOOL_CODE);    

		Collections.sort(organizations, OrgComparator);
	    LOGGER.trace("Leaving the getAccountabilitySchools method.");
	    return organizations;
    }
    
    @RequestMapping(value = "users.htm")
    public final ModelAndView getUsersConfiguration() {
     return new ModelAndView("new-settings/usersMgmt");
    }
      
    
    @RequestMapping(value = "findRostersforTeacherIdInCurrentYearForMergeUser.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel findRostersforTeacherIdInCurrentYearForMergeUser(
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,    		
			@RequestParam(value = "filters", required = false) String filters,final HttpServletRequest request,
			final HttpServletResponse response) throws NoAccessToResourceException, JsonProcessingException, IOException   {
		    	
		JQGridJSONModel jqGridJSONModel = new JQGridJSONModel();
		Integer currentPage = null;
		Integer limitCount = null;
		List<RosterDTO> rosterDTOs;
		Integer totalCount = -1;
		currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        Map<String, Object> criteria = new HashMap<String, Object>();
		populateCriteria(criteria, filters);
		long userId = Long.parseLong(request.getParameter("id"));
		User user = userService.getAllDetails(userId);
		if(user.isTeacher())
		{
			criteria.put("teacherId", userId);
		}
	rosterDTOs = rosterService.findRostersforTeacherIdInCurrentYearForMergeUser(criteria, sortByColumn, sortType, (currentPage-1)*limitCount, limitCount);
			 totalCount = 0;
	        if(!rosterDTOs.isEmpty()) {
	        	totalCount = rosterDTOs.get(0).getRoster().getTotalRecords();
	        }
			jqGridJSONModel = RosterDTOJsonConverter.convertToRosterDTOJson(rosterDTOs,totalCount, currentPage, limitCount);
		
		return jqGridJSONModel;
	}
    

    
    
}
