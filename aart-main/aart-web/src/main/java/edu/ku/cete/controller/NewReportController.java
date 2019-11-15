package edu.ku.cete.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.dataextract.service.DataReportDetailService;
import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.professionaldevelopment.DataDetailDto;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.ReportAssessmentProgram;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.domain.report.roster.RosterReport;
import edu.ku.cete.domain.report.student.DLMStudentReport;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.TestingCycleMapper;
import edu.ku.cete.report.DLMBlueprintCoverageReportGenerator;
import edu.ku.cete.report.DLMMonitoringSummaryDistrictSchoolData;
import edu.ku.cete.report.DLMStudentReportGenerator;
import edu.ku.cete.report.DLMTestAdministrationMonitoringSummaryGenerator;
import edu.ku.cete.report.RosterReportGenerator;
import edu.ku.cete.report.StudentReportGenerator;
import edu.ku.cete.report.domain.StateSpecificFile;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.StudentSpecialCircumstanceService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.ReportService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DataReportTypeEnum;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.json.DataReportJsonConverter;
import edu.ku.cete.util.json.StudentReportDTOJsonConverter;
import edu.ku.cete.web.AssessmentProgramDto;
import edu.ku.cete.web.DLMBlueprintCoverageReportDTO;
import edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.SchoolAndDistrictReportDTO;
import edu.ku.cete.web.StudentReportDTO;

@Controller
public class NewReportController extends BaseController{
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NewReportController.class);
	
	@Autowired
	private DownloadController downloadController;
	
	@Autowired
	private AwsS3Service s3;
	
	/**
	 * orgAssessProgService
	 */
	@Autowired
    private OrgAssessmentProgramService orgAssessProgService;
	
	/**
	 * testingProgramService
	 */
	@Autowired
    TestingProgramService testingProgramService;

    /**
     * contentAreaService
     */
    @Autowired
    private ContentAreaService contentAreaService;
    
    /**
     * gradeCourseService
     */
    @Autowired
    private GradeCourseService gradeCourseService;
    
    @Autowired
    private RosterService rosterService;
    
    @Autowired
   	private EnrollmentService enrollmentService;
    
    /**
     * restrictedResourceConfiguration.
     */
    @Autowired
    private RestrictedResourceConfiguration restrictedResourceConfiguration;

    /**
     * categoryService
     */
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private PermissionUtil permissionUtil;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;

    /**
     * resourceRestrictionService.
     */
    @Autowired
    private ResourceRestrictionService resourceRestrictionService;
    
    @Autowired
    private DataReportDetailService dataReportDetailsService;
    
	/**
	 * reportingReportCategories
	 */
	@Value("${report.testingprograms}")
	private String reportingTestingPrograms;
	
	/**
	 * reportingReportCategories
	 */
	@Value("${report.categorytype.code}")
	private String reportCategoryTypeCode;
	
	@Value("${report.assessmentprograms}")
	private String reportingAssessmentPrograms;
	
	@Autowired
	private DataReportService dataReportService;

	@Autowired
	private AppConfigurationService appConfigurationService;	
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;

	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private OrganizationTypeService orgTypeService;
	
	@Autowired
	private StudentReportService studentReportService;
	
	@Autowired
	private StudentSpecialCircumstanceService studentSpecialCircumstanceService;
	
	@Autowired
	private UserService userService;
	
	@Value("${user.organization.DLM}")
	private String USER_ORG_DLM;
	
	@Autowired
	private StudentReportGenerator studentReportGenerator;
	
    @Autowired
	private RosterReportGenerator rosterGenerator;
	
    @Autowired
	private DLMStudentReportGenerator dlmStudentGenerator;
    
    @Autowired
    private DLMTestAdministrationMonitoringSummaryGenerator dlmMonitoringSummaryGenerator;
    
    @Autowired
	private DLMBlueprintCoverageReportGenerator dlmBlueprintGenerator;
	
    @Autowired
	private DataExtractService dataExtractService;
    
    @Autowired
    private GroupsService groupsService;
    
    @Autowired
    private OrganizationBundleReportService bundleReportService;
    @Autowired
    private BatchReportProcessService batchReportProcessService;
    
    @Autowired
	private ReportService reportService;
    
    @Value("${external.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED;
    
    @Value("${external.import.reportType.studentSummary}")
	private String REPORT_TYPE_STUDENT_SUMMARY;
    
    @Value("${external.import.reportType.school}")
	private String REPORT_TYPE_SCHOOL; 
    
    @Value("${external.districtLevel.school.bundled.reportType}")
   	private String REPORT_TYPE_SCH_SUMMARY_BUNDLED; 
    
    @Value("${classroomReportsImportReportType}")
	private String REPORT_TYPE_CLASSROOM;
    
    @Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;

    @Value("${cpass.student.individual.report.type.code}")
	private String dbCPASSStudentReportsImportReportType;
    
	@Autowired
	private TestingCycleMapper testingCycleMapper;
    
	/**
	 * Render method for the reporting home page.
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value = "reports.htm")
	public final ModelAndView reports() {
		ModelAndView mav = new ModelAndView("new_reports");
		return mav;
	}
	
	@RequestMapping(value = "dataExtracts.htm")
	public final ModelAndView dataExtracts() {
		ModelAndView mav = new ModelAndView("dataExtracts");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
        Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();	
        Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), assessmentProgramId);
		if(reportYear == null)
			reportYear = (long) user.getContractingOrganization().getReportYear();	
		mav.addObject("schoolYear", schoolYear);
		mav.addObject("reportYear", reportYear);
		return mav;
	}
	
	@RequestMapping(value = "reports-general-assessment.htm")
	public final ModelAndView generalAssessments() {
		return getReportModelAndView("reports-ui/generalAssessment");
	}
	
	@RequestMapping(value = "reports-alternate-assessment.htm")
	public final ModelAndView alternateAssessments() {
		return getReportModelAndView("reports-ui/alternateAssessment");
	}
	
	@RequestMapping(value = "reports-career-pathways.htm")
	public final ModelAndView careerPathways() {
		return getReportModelAndView("reports-ui/careerPathways");
	}
	
	@RequestMapping(value = "reports-english-language-learners.htm")
	public final ModelAndView englishLanguageLearners() {
		return getReportModelAndView("reports-ui/englishLanguageLearners");
	}
	
	@RequestMapping(value = "reports-all-reports-for-student.htm")
	public final ModelAndView allReportsForStudent() {
		return getReportModelAndView("reports-ui/allReportsForStudent");
	}
	
	private final ModelAndView getReportModelAndView(String path) {
		ModelAndView mav = new ModelAndView(path);
		mav.addObject("reports", getReportsReadyToView());
		mav.addObject("reportCategories", getReportCategories());
		mav.addObject("isIEModelState", getIsIEModelState());
		return mav;
	}
	
	private final List<Category> getReportCategories() {
		return reportService.getReports();
	}
	
	private final List<ReportAssessmentProgram> getReportsReadyToView() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		return reportService.getReadyToViewFlagsForReports(
				user.getCurrentGroupsId(),
				userDetails.getUser().getContractingOrgId(),
				user.getCurrentOrganizationType());
	}
	
	private final Boolean getIsIEModelState() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		return organizationService.isIEModelState(userDetails.getUser().getContractingOrganization());
	}
	
	/**
	 * @return
	 */
	@RequestMapping(value = "getReportAssessmentPrograms.htm", method = RequestMethod.POST)
    public final @ResponseBody List<AssessmentProgramDto> getAssessmentPrograms() {
		LOGGER.trace("Entering the getAssessmentPrograms() method");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 		User user = userDetails.getUser();
        Organization current = user.getOrganization(); 
        //TODO check contract organization tree to get APs
        List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
        List<String> validAssessmentPrograms = Arrays.asList(reportingAssessmentPrograms.split("\\s*,\\s*"));

        if (current != null) {
        	if(current.getOrganizationType().getTypeCode().equalsIgnoreCase("CONS")) {
        		List<AssessmentProgram> aps = assessmentProgramService.getAllActive();
        		 for (AssessmentProgram ap : aps) {
     					AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
     					assessmentProgramDto.setAssessmentProgram(ap);
     					assessmentPrograms.add(assessmentProgramDto);
                 }
        	} else {
                List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(current.getId());
                for (OrgAssessmentProgram oap : orgAssessProgs) {
                	if (validAssessmentPrograms.contains(oap.getAssessmentProgram().getProgramName())){
    					AssessmentProgramDto assessmentProgramDto = new AssessmentProgramDto();
    					assessmentProgramDto.setAssessmentProgram(oap.getAssessmentProgram());
    					assessmentPrograms.add(assessmentProgramDto);
                	}
                }
        	}
        }
        
        LOGGER.trace("Leaving the getAssessmentPrograms() method");
        return assessmentPrograms;        
    }
	
		
	/**
	 * @param assessmentProgramId
	 * @return
	 */
	@RequestMapping(value = "getReportCategories.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Category> getReportCategories(
    		@RequestParam("assessmentProgramId") Long assessmentProgramId) {
		LOGGER.trace("Entering the getReportCategories() method");

        List<Category> reportCategories = categoryService.selectByCategoryType(reportCategoryTypeCode);
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean studentPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"PERM_REPORT_PERF_STUDENT_VIEW");
        boolean schoolPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"PERM_REPORT_PERF_SCHOOL_VIEW");
//        boolean districtPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"PERM_REPORT_PERF_DISTRICT_VIEW");
//        boolean statePermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"PERM_REPORT_PERF_STATE_VIEW");
        boolean rosterPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_REPORT_PERF_ROSTER_VIEW");
        boolean parentReportPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_REPORT_PERF_PARENT_VIEW");

        
        List<Category> permissionAllowedReportCategories = new ArrayList<Category>();
        for (Category rc : reportCategories){
        	if ("STUDENT".equals(rc.getCategoryCode()) && studentPermission ){
        		permissionAllowedReportCategories.add(rc);
        	}else if ("SCHOOL".equals(rc.getCategoryCode()) && schoolPermission ){
        		permissionAllowedReportCategories.add(rc);
        	/*}else if ("DISTRICT".equals(rc.getCategoryCode()) && districtPermission ){
        		permissionAllowedReportCategories.add(rc);
        	}else if ("STATE".equals(rc.getCategoryCode()) && statePermission ){
        		permissionAllowedReportCategories.add(rc);*/
        	}else if ("ROSTER".equals(rc.getCategoryCode()) && rosterPermission ){
        		permissionAllowedReportCategories.add(rc);
        	}else if ("PARENT_REPORT".equals(rc.getCategoryCode()) && parentReportPermission ){
        		permissionAllowedReportCategories.add(rc);
        	}
        }
        
        LOGGER.trace("Leaving the getReportCategories() method");
        return permissionAllowedReportCategories;        
    }
	
	
	/**
	 * @param assessmentProgramId
	 * @return
	 */
	@RequestMapping(value = "getTestingProgramsForReporting.htm", method = RequestMethod.GET)
	 private final  @ResponseBody List<TestingProgram> getTestingProgramsForReporting(
			 @RequestParam("assessmentProgramId") Long assessmentProgramId) {
		LOGGER.trace("Entering the getTestingProgramsForReporting() method");

        List<TestingProgram> testingPrograms = new ArrayList<TestingProgram>();
        List<TestingProgram> newtestingPrograms = new ArrayList<TestingProgram>();
        List<String> supportedTestingPrograms = Arrays.asList(reportingTestingPrograms.split("\\s*,\\s*"));
        
        testingPrograms = testingProgramService.getByAssessmentProgramId(assessmentProgramId);
        for( TestingProgram testingProgram : testingPrograms) {
        	if(supportedTestingPrograms.contains(testingProgram.getProgramAbbr())) {
        		newtestingPrograms.add(testingProgram);
        	}
        }
        LOGGER.trace("Leaving the getTestingProgramsForReporting() method");
        return newtestingPrograms;
   }
	
	
	 /**
	 * @return
	 */
	@RequestMapping(value = "getContentAreas.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentArea> getContentAreas(@RequestParam("assessmentProgramId") Long assessmentProgramId, @RequestParam("testingProgramId") Long testingProgramId) {
		LOGGER.trace("Entering the getContentAreas() method");

		List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgramAndTestingProgram(assessmentProgramId, testingProgramId);
		LOGGER.trace("Leaving the getContentAreas() method");
		return contentAreas;
	 }

    static Comparator<GradeCourse> gradeCourseComparator = new Comparator<GradeCourse>(){
	   	public int compare(GradeCourse gc1, GradeCourse gc2){
	   		return gc1.getName().compareToIgnoreCase(gc2.getName());
	   	}
	};
		   
	/**
	 * @return
	 */
	@RequestMapping(value = "getGradeCoursesByContentArea.htm", method = RequestMethod.GET)
	public final @ResponseBody List<GradeCourse> getGradeCoursesByContentArea(
			@RequestParam("contentAreaId") Long contentAreaId) {
		LOGGER.trace("Entering the getGradeCoursesByContentArea() method");
		 
		List<GradeCourse> gradeCourses = gradeCourseService.selectGradeCourseByContentAreaId(contentAreaId);	           
		Collections.sort(gradeCourses, gradeCourseComparator);		

		LOGGER.trace("Leaving the getGradeCoursesByContentArea() method");
		return gradeCourses;
	 }
	
	static Comparator<ModuleReport> reportComparator = new Comparator<ModuleReport>(){
		public int compare(ModuleReport r1, ModuleReport r2){
			return r1.getReportType().compareTo( r2.getReportType());
    	}
	};
	static Comparator<DataReportTypeEnum> reportTypeComparator = new Comparator<DataReportTypeEnum>(){
		public int compare(DataReportTypeEnum r1, DataReportTypeEnum r2){
			return r1.getName().compareTo( r2.getName());    	}
		
	};
	
	@RequestMapping(value = "getDataExtracts.htm", method = RequestMethod.POST)
	public final @ResponseBody JQGridJSONModel getDataExtracts(@RequestParam("rows") String limitCountStr,
	  		@RequestParam("page") String page,
	  		@RequestParam("sidx") String sortByColumn,
	  		@RequestParam("sord") String sortType,
	  		@RequestParam("filters") String filters) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
		Long organizationId = userDetails.getUser().getContractingOrgId();
		ArrayList<Short> typeIds = new ArrayList<Short>();
		ArrayList<DataReportTypeEnum> types = new ArrayList<DataReportTypeEnum>();
		String assessmentProgram= StringUtils.EMPTY;
		if(user != null) {
			List<AssessmentProgram> contractingOrgAssessmentPrograms = assessmentProgramService.getAllAssessmentProgramByUserId(user.getId());
			if(contractingOrgAssessmentPrograms.size() == 1 && contractingOrgAssessmentPrograms.get(0).getAbbreviatedname() != null) {
				assessmentProgram = contractingOrgAssessmentPrograms.get(0).getAbbreviatedname();
			}
		}
		// provide SC code extract only if there are any restricted codes for the user state.
		boolean isRestrictedCodesExists = false;
		
		Long restrictedCodesCount = studentSpecialCircumstanceService.getCountOfRestrictedCodesByState(user.getCurrentOrganizationId());
		if(restrictedCodesCount != null && restrictedCodesCount.longValue() > 0 ){
			isRestrictedCodesExists = true;
		}

		List<Long> groupAccessCodes = reportService.getDlmExtractAccessId(user.getCurrentGroupsId(), user.getCurrentOrganizationId(), user.getCurrentAssessmentProgramId());
		List<String> dlmExtractAuthorityCode = appConfigurationService.getByAttributeType(CommonConstants.EXTRACTS_ACCESS);
		
		for (DataReportTypeEnum rule : DataReportTypeEnum.values()) {
			if (rule.getAppCode().equals("DE")) {
				if (permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_" + rule.getPermissionCode())) {
					//Add permission check for TASC extracts
					if(rule.getPermissionCode().equals("KSDE_TEST_TASC"))
					{
						if(dataReportService.checkKAPExtract(user.getCurrentOrganizationId())){
							typeIds.add(rule.getId());
							types.add(rule);
						}
					}
					else { 		
						Long reportTypeId = (long)rule.getId();	
						
						if(dlmExtractAuthorityCode.contains("DATA_EXTRACTS_" + rule.getPermissionCode())) {
							if(groupAccessCodes.contains(reportTypeId)) {
								typeIds.add(rule.getId());
								types.add(rule);								
							}
						} else {
							typeIds.add(rule.getId());
							types.add(rule);
						}
					}
				} else if (permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_STU_UNAME_PASSWORD")
					&& rule.equals(DataReportTypeEnum.STUDENT_USERNAME_PASSWORD ) && !(assessmentProgram.equals("DLM"))) {
					typeIds.add(rule.getId());
					types.add(rule);                                           
				}else if (permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_SPL_CIRCM_CODE_REP")
					&& rule.equals(DataReportTypeEnum.RESTRICTED_SPECIAL_CIRCUMSTANCE_CODE ) && isRestrictedCodesExists) {
					typeIds.add(rule.getId());
					types.add(rule);
				}else if (permissionUtil.hasPermission(user.getAuthorities(), "PERM_DLM_TRAINING_STATUS_EXTRACT")
						&& rule.equals(DataReportTypeEnum.DLM_PD_TRAINING_STATUS )) {
						typeIds.add(rule.getId());
						types.add(rule);
				}else if (permissionUtil.hasPermission(user.getAuthorities(), "PD_TRAINING_EXPORT_FILE_CREATOR") 
						&& rule.equals(DataReportTypeEnum.DLM_PD_TRAINING_LIST)){					
					typeIds.add(rule.getId());
					types.add(rule);
				}else if (permissionUtil.hasPermission(user.getAuthorities(), "KELPA2_AGREEMENT") 
						&& rule.equals(DataReportTypeEnum.K_ELPA_Test_Administration)){					
					typeIds.add(rule.getId());
					types.add(rule);
				} else if(permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_MONITOR_ASSIGNMENT") 
						&& rule.equals(DataReportTypeEnum.MONITOR_SCORING_ASSIGNMENT)){
					typeIds.add(rule.getId());
					types.add(rule);
				}else if (permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_KAP_STUDENT_SCORES") 
						&& (rule.equals(DataReportTypeEnum.KAP_STUDENT_SCORES) || rule.equals(DataReportTypeEnum.KAP_STUDENT_SCORES_SPECIFIED_STUDENT) || rule.equals(DataReportTypeEnum.KAP_STUDENT_SCORES_TESTED_STUDENTS)) ){					
					typeIds.add(rule.getId());
					types.add(rule);
					
				}else if(permissionUtil.hasPermission(user.getAuthorities(),  "DATA_EXTRACTS_KELPA2_STU_SCORES") 
							&& (rule.equals(DataReportTypeEnum.K_ELPA_CURRENT_STUDENT_SCORES))){
						typeIds.add(rule.getId());
						types.add(rule); // The above code check in for Added below code for F675 KELPA2 Student Score Extract
					
				} else if (permissionUtil.hasPermission(user.getAuthorities(), "DLM_BP_COVERAGE")
						&& rule.equals(DataReportTypeEnum.DLM_BLUEPRINT_COVERAGE)){
					typeIds.add(rule.getId());
					types.add(rule);
				}
				// F841 - PLTW Extract Reports
				 else if (permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_TEST_ADMIN_PLTW")
							&& rule.equals(DataReportTypeEnum.TEST_ADMIN_PLTW_AMP)){
						typeIds.add(rule.getId());
						types.add(rule);
				}else if (permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_PLTW_TESTING_READ")
						&& rule.equals(DataReportTypeEnum.PLTW_TESTING_READINESS)){
					// 54 PLTW Testing Readiness Extract
					typeIds.add(rule.getId());
					types.add(rule);
				}
				 else if (permissionUtil.hasPermission(user.getAuthorities(), "DATA_EXTRACTS_DLM_EXIT_STUDENTS")
							&& rule.equals(DataReportTypeEnum.DLM_EXIT_STUDENT)){
						typeIds.add(rule.getId());
						types.add(rule);
					}
			}
		}
		
		int totalCount = types.size() > 0 ? dataReportService.countReportsByType(user, typeIds) : 0;		
		//added to get data dictionary 
		ArrayList<Short> DataDictionarytypes = new ArrayList<Short>();
		for (DataReportTypeEnum Type:types){
			DataDictionarytypes.add(Type.getId());
			Type.setDataDetailFileLocation(null);
			Type.setDataDetailFileName(null);
			Type.setDataDetailActiveFlag(false);
			Type.setSpecialDataDetailFileLocation(null);
			Type.setSpecialDataDetailFileName(null);
			Type.setSpecialDataDetailActiveFlag(false);
			
		}
		List<DataDetailDto> datadetails=dataReportService.getReportsByTypeWithDataDictionaryDetail(user, DataDictionarytypes,assessmentProgramId,organizationId);
		
		for (DataReportTypeEnum type : types) {
			for (DataDetailDto dataDetail : datadetails) {
				if (type.getId().equals(dataDetail.getExtractTypeId().shortValue())) {
					String fileName = dataDetail.getDataDetailFileName();
					String fileLocation = dataDetail.getDataDetailFileLocation();
					String path = FileUtil.buildFilePath(REPORT_PATH, fileLocation + File.separator + fileName);
					// instead of checking the file on the mounted drive check s3
					if (!s3.doesObjectExist(path)) {
						type.setDataDetailActiveFlag(false);
					} else {
						type.setDataDetailFileLocation(fileLocation);
						type.setDataDetailFileName(fileName);
						type.setDataDetailActiveFlag(true);

					}
					// check for eecrosswalk file availability
					if (dataDetail.isSpecialDataDetailActiveFlag()) {
						String specialFileName = dataDetail.getSpecialDataDetailFileName();
						String specialFileLocation = dataDetail.getSpecialDataDetailFileLocation();
						String specialPath = FileUtil.buildFilePath(REPORT_PATH,
								specialFileLocation + File.separator + specialFileName);
						// instead of checking the file on the mounted drive check s3
						if (!s3.doesObjectExist(specialPath)) {
							type.setSpecialDataDetailActiveFlag(false);
						} else {
							type.setSpecialDataDetailFileLocation(specialFileLocation);
							type.setSpecialDataDetailFileName(specialFileName);
							type.setSpecialDataDetailActiveFlag(true);
						}
					}
				}
			}

		}

		int currentPage = NumericUtil.parse(page, 1);
	    int limitCount = NumericUtil.parse(limitCountStr, 5);
	    int totalPages = NumericUtil.getPageCount(totalCount, limitCount);
		JQGridJSONModel jq;
		long alternateId = -1L;
		ArrayList<Short> exitOrScTypeIds = new ArrayList<Short>();
		if(typeIds.contains(DataReportTypeEnum.DLM_EXIT_STUDENT.getId())) {
			typeIds.remove(DataReportTypeEnum.DLM_EXIT_STUDENT.getId());
			exitOrScTypeIds.add(DataReportTypeEnum.DLM_EXIT_STUDENT.getId());
		}
		
		if(typeIds.contains(DataReportTypeEnum.DLM_SPECIAL_CIRCUMSTANCES_FILE.getId())) {
			typeIds.remove(DataReportTypeEnum.DLM_SPECIAL_CIRCUMSTANCES_FILE.getId());
			exitOrScTypeIds.add(DataReportTypeEnum.DLM_SPECIAL_CIRCUMSTANCES_FILE.getId());
		}
		
		if (totalCount > 0){
			List<ModuleReport> exitOrSCReports = null;
			if(!exitOrScTypeIds.isEmpty() && exitOrScTypeIds.size() > 0) {
				 exitOrSCReports = dataReportService.getReportsByTypeForExitOrSC(user, exitOrScTypeIds);
			}
			List<ModuleReport> reports = dataReportService.getReportsByType(user, typeIds);
				if(!exitOrScTypeIds.isEmpty() && exitOrScTypeIds.size() > 0) {
				reports.addAll(exitOrSCReports);
			}
			// populate any type not found from DB records
			Set<Short> typesFound = new HashSet<Short>();
			for (ModuleReport report : reports) {
				//check for file availability
				typesFound.add(report.getReportTypeId());
				for (DataReportTypeEnum type : types) {
					if(type.getId().equals(report.getReportTypeId())){
						report.setDataDetailActiveFlag(type.isDataDetailActiveFlag());
						report.setDataDetailFileLocation(type.getDataDetailFileLocation());
						report.setDataDetailFileName(type.getDataDetailFileName());
						report.setSpecialDataDetailActiveFlag(type.isSpecialDataDetailActiveFlag());
						report.setSpecialDataDetailFileLocation(type.getSpecialDataDetailFileLocation());
						report.setSpecialDataDetailFileName(type.getSpecialDataDetailFileName());
					}
				}
				
			}
			for (DataReportTypeEnum type : types) {
				if (!typesFound.contains(type.getId())) {
					ModuleReport rep = new ModuleReport();
					rep.setId(alternateId);
					rep.setReportTypeId(type.getId());
					rep.setReportType(type.getName());
					rep.setDataDetailActiveFlag(type.isDataDetailActiveFlag());
					rep.setDataDetailFileLocation(type.getDataDetailFileLocation());
					rep.setDataDetailFileName(type.getDataDetailFileName());
					rep.setSpecialDataDetailActiveFlag(type.isSpecialDataDetailActiveFlag());
					rep.setSpecialDataDetailFileLocation(type.getSpecialDataDetailFileLocation());
					rep.setSpecialDataDetailFileName(type.getSpecialDataDetailFileName());
					reports.add(rep);
					totalCount++;
					alternateId=alternateId-1L;
				}
			}
			Collections.sort(reports, reportComparator);
			jq = DataReportJsonConverter.convertToJQGrid(reports, totalCount, currentPage, totalPages);
		} else {
			Collections.sort(types, reportTypeComparator);
			jq = DataReportJsonConverter.generateEmptyJQGridForTypes(types, totalCount, currentPage, totalPages);
		}
		
		return jq;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "validateExtract.htm", method = RequestMethod.POST)
	public final @ResponseBody long validateExtract(@RequestParam("extractId") Long extractId,
			@RequestParam("extractTypeId") Short extractTypeId,
			final HttpServletRequest request) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		Long narrowestOrgId = null;
		Long narrowestOrgTypeId = null;
		
		if (narrowestOrgId == null) { // need to use the user's org ID
			Organization org = organizationService.get(user.getOrganizationId());
			OrganizationType ot = orgTypeService.get(org.getOrganizationTypeId());
			narrowestOrgId = org.getId();
			narrowestOrgTypeId = ot.getOrganizationTypeId();
		}
		
		DataReportTypeEnum type = DataReportTypeEnum.getById(extractTypeId);
		LOGGER.debug("getting report of type "+type.getName()+" for organization "+narrowestOrgId);
		
		Map<String, Object> params = new HashMap<String, Object>();
		if (type.equals(DataReportTypeEnum.KSDE_TEST_TASC_RECORDS) && request.getParameter("studentStateId") != null) {
			String strExtractStudentStateId = request.getParameter("studentStateId");
			Boolean validateStudentStateID = dataReportService.validateKSDETestAndTascStudentStateID(userDetails, strExtractStudentStateId);
			if(validateStudentStateID==false)
			{
				return -1;
			}
		}
		
		return 0;
	}
	
	@RequestMapping(value = "generateExtract.htm", method = RequestMethod.POST)
	public final @ResponseBody long generateExtract(@RequestParam("extractId") Long extractId,
			@RequestParam("extractTypeId") Short extractTypeId,
			@RequestParam(value="assessmentProgramIds[]", required = false) String [] assessmentProgramIds,
			@RequestParam(value="orgainzationIdsForMultiSelect[]", required = false) String [] orgIds,
			@RequestParam(value="includeInternalUsers", required = false) boolean includeInternalUsers,
			@RequestParam(value="includeInactiveOrganizations", required = false) boolean includeInactiveOrganizations,
			@RequestParam(value="contentAreaIds[]", required = false) Long[] contentAreaIds,
			@RequestParam(value="gradeIds[]", required = false) Long[] gradeIds,
			@RequestParam(value="kelpaGradeIds[]", required = false) String[] kelpaGradeIds,
			@RequestParam(value="schoolIds[]", required = false) Long[] schoolIds,
			@RequestParam(value="schoolYears[]", required = false) Long[] schoolYears,
			final HttpServletRequest request) throws IOException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		 Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), user.getCurrentAssessmentProgramId());
				if(reportYear == null)
					reportYear = (long) user.getContractingOrganization().getReportYear();
		String strStateId = request.getParameter("stateId");
		String strDistrictId = request.getParameter("districtId");
		String strSchoolId = request.getParameter("schoolId");
		Long gradeId = StringUtils.isNotBlank(request.getParameter("gradeId"))? Long.parseLong(request.getParameter("gradeId")) :null;
		Long subjectId = StringUtils.isNotBlank(request.getParameter("subjectId"))? Long.parseLong(request.getParameter("subjectId")) :null;
		String assessmentSubjectIds = request.getParameter("assessmentSubjectIds");
		boolean csvDownload = Boolean.parseBoolean(request.getParameter("csvDownload"));
		boolean pdfDownload = Boolean.parseBoolean(request.getParameter("pdfDownload"));  
		
		//Long rosterId = StringUtils.isNotBlank(request.getParameter("rosterId"))? Long.parseLong(request.getParameter("rosterId")) :null;
		Long narrowestOrgId = null;
		Long narrowestOrgTypeId = null;
		List<Long> assessmentPrograms=new ArrayList<Long>();
		if(assessmentProgramIds != null){
			for(String ap:assessmentProgramIds){
				if(ap!=null){
					assessmentPrograms.add(Long.valueOf(ap));
				}
			}
		}
		if (strStateId != null && !strStateId.isEmpty()) {
			narrowestOrgId = Long.parseLong(strStateId);
			OrganizationType ot = orgTypeService.getByTypeCode("ST");
			narrowestOrgTypeId = ot.getOrganizationTypeId();
		}
		if (strDistrictId != null && !strDistrictId.isEmpty()) {
			narrowestOrgId = Long.parseLong(strDistrictId);
			OrganizationType ot = orgTypeService.getByTypeCode("DT");
			narrowestOrgTypeId = ot.getOrganizationTypeId();
		}
		if (strSchoolId != null && !strSchoolId.isEmpty()) {
			narrowestOrgId = Long.parseLong(strSchoolId);
			OrganizationType ot = orgTypeService.getByTypeCode("SCH");
			narrowestOrgTypeId = ot.getOrganizationTypeId();
		}
		
		if (narrowestOrgId == null) { // need to use the user's org ID
			Organization org = organizationService.get(user.getOrganizationId());
			OrganizationType ot = orgTypeService.get(org.getOrganizationTypeId());
			narrowestOrgId = org.getId();
			narrowestOrgTypeId = ot.getOrganizationTypeId();
		}
		
		DataReportTypeEnum type = DataReportTypeEnum.getById(extractTypeId);
		LOGGER.debug("getting report of type "+type.getName()+" for organization "+narrowestOrgId);
		
		String iniToDateStr="", iniFromDateStr="", eoyToDateStr="", eoyFromDateStr="";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clientLocale", request.getLocale());
		params.put("currentOrganizationId", user.getCurrentOrganizationId());
		params.put("assessmentProgramIds", assessmentPrograms);
		params.put("includeInternalUsers", includeInternalUsers);
		params.put("includeInactiveOrganizations", includeInactiveOrganizations);
		params.put("assessmentProgramCode", getAssessmentProgram(user));		
		if (type.equals(DataReportTypeEnum.PNP_SUMMARY) && request.getParameter("extractSummaryLevel") != null) {
			String strExtractSummaryLevel = request.getParameter("extractSummaryLevel");
			OrganizationType summaryLevel = orgTypeService.getByTypeLevel(Integer.parseInt(strExtractSummaryLevel));
			params.put("summaryLevelTypeCode", summaryLevel.getTypeCode());
			params.put("summaryLevelOrgTypeId", summaryLevel.getOrganizationTypeId());
		}
		
		else if (type.equals(DataReportTypeEnum.KSDE_TEST_TASC_RECORDS) && request.getParameter("studentStateId") != null) {
			String strExtractStudentStateId = request.getParameter("studentStateId");
			params.put("studentStateID", strExtractStudentStateId);
		}
		/*
	     * Added during US16344 : for Extracting reports on TEst Form assign to TestCollection for quality check
	     */
		else if(type.equals(DataReportTypeEnum.TEST_FORM_ASSIGNMENT) && request.getParameter("qcCompleteStatus") != null){
			String qcCompleteStatus = request.getParameter("qcCompleteStatus");
			String beginDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			params.put("qcCompleteStatus", qcCompleteStatus);
			params.put("beginDate", beginDate);
			params.put("toDate", toDate);
			
		}/*
	     * Added during US16343 : for Extracting reports on TEst Form assign to TestCollection for quality check
	     */
		else if(type.equals(DataReportTypeEnum.TEST_FORM_MEDIA) && request.getParameter("qcCompleteStatus") != null){
			String qcCompleteStatus = request.getParameter("qcCompleteStatus");
			String beginDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String media = request.getParameter("media");
			params.put("media", media);
			params.put("qcCompleteStatus", qcCompleteStatus);
			params.put("fromDate", beginDate);
			params.put("toDate", toDate);
			
		} else if(DataReportTypeEnum.STUDENT_USERNAME_PASSWORD.equals(type)){
			params.put("grade",request.getParameter("gradeAbbreviatedName") );
			params.put("assessmentSubjectIds", assessmentSubjectIds);
			params.put("csvDownload", csvDownload);
			params.put("pdfDownload", pdfDownload);
			params.put("userRole", user.getCurrentGroupsId());
		}/*
	     * Added during US18828 : for Extracting reports on DLM PD Status
	     */
		else if(DataReportTypeEnum.DLM_PD_TRAINING_STATUS.equals(type) 
				|| DataReportTypeEnum.DLM_PD_TRAINING_LIST.equals(type)
				|| DataReportTypeEnum.SECURITY_AGREEMENT_COMPLETION.equals(type)
				|| DataReportTypeEnum.TESTING_READINESS.equals(type)
				|| DataReportTypeEnum.PLTW_TESTING_READINESS.equals(type)) {
			params.put("roles", user.getGroupCode());
			if(orgIds == null && !"PDAD".equalsIgnoreCase(user.getGroupCode())){
				params.put("orgIds",new Object[] {user.getOrganizationId()});
			}else{
			  params.put("orgIds", orgIds);
			}
		} else if(DataReportTypeEnum.KAP_STUDENT_SCORES.equals(type) || DataReportTypeEnum.KAP_STUDENT_SCORES_TESTED_STUDENTS.equals(type)){
			params.put("contentAreaIds", contentAreaIds);
			params.put("gradeIds", gradeIds);
			params.put("schoolYears", schoolYears);								
			params.put("currentReportYear", reportYear);
			params.put("assessmentProgramCode", user.getCurrentAssessmentProgramName());	
			params.put("assessmentProgramId", userDetails.getUser().getCurrentAssessmentProgramId());	
			params.put("districtId", strDistrictId);
			params.put("schoolIds", schoolIds);
		}else if(DataReportTypeEnum.KAP_STUDENT_SCORES_SPECIFIED_STUDENT.equals(type)) {
			params.put("currentReportYear", reportYear);
			params.put("assessmentProgramCode", user.getCurrentAssessmentProgramName());
			params.put("assessmentProgramId", userDetails.getUser().getCurrentAssessmentProgramId());
			String studentStateId = request.getParameter("studentStateId");
			params.put("studentStateId", studentStateId);
		}
		else if(DataReportTypeEnum.DLM_BLUEPRINT_COVERAGE.equals(type)){
			String strExtractSummaryLevel = request.getParameter("extractSummaryLevel");
			OrganizationType summaryLevel = orgTypeService.getByTypeLevel(Integer.parseInt(strExtractSummaryLevel));
			params.put("summaryLevelTypeCode", summaryLevel.getTypeCode());
			params.put("summaryLevelOrgTypeId", summaryLevel.getOrganizationTypeId());
			params.put("groupByTeacher", request.getParameter("groupByTeacher"));			
			params.put("subjectId", subjectId);
			params.put("gradeId", gradeId);
		}else if(DataReportTypeEnum.DLM_GENERAL_RESEARCH_FILE.equals(type)){
			params.put("stateId", request.getParameter("stateId"));
			params.put("districtId", request.getParameter("districtId"));
			params.put("schoolId", request.getParameter("schoolId"));
			params.put("subject", request.getParameter("subject"));
			params.put("grade", request.getParameter("grade"));
		}else if(DataReportTypeEnum.ORGANIZATIONS.equals(type) && request.getParameter("organizationextractSummaryLevel") != null){
			String strExtractSummaryLevel = request.getParameter("organizationextractSummaryLevel");
			OrganizationType summaryLevel = orgTypeService.getByTypeLevel(Integer.parseInt(strExtractSummaryLevel));
			params.put("summaryLevelTypeCode", summaryLevel.getTypeCode());
			params.put("summaryLevelOrgTypeId", summaryLevel.getOrganizationTypeId());			
		}else if(DataReportTypeEnum.ACCESSIBILITY_PROFILE_ABRIDGED.equals(type)){
			params.put("includeAllStudents", "true".equalsIgnoreCase(request.getParameter("includeAllStudents")));
			params.put("fileType", request.getParameter("fileType"));
		}else if(DataReportTypeEnum.TEST_ADMIN_PLTW_AMP.equals(type)){
			params.put("assessmentProgramCode", user.getCurrentAssessmentProgramName());
		}else if(DataReportTypeEnum.K_ELPA_CURRENT_STUDENT_SCORES.equals(type)){
			params.put("contentAreaIds", contentAreaIds);
			params.put("gradeIds", kelpaGradeIds);
			params.put("schoolYears", schoolYears);								
			params.put("currentReportYear", reportYear);
			params.put("assessmentProgramCode", user.getCurrentAssessmentProgramName());	
			params.put("assessmentProgramId", user.getCurrentAssessmentProgramId());	
			params.put("districtId", strDistrictId);
			params.put("schoolIds", schoolIds);
		}

		else if(DataReportTypeEnum.DLM_EXIT_STUDENT.equals(type)){
			params.put("stateId", request.getParameter("stateId"));
			params.put("districtId", request.getParameter("districtId"));
			params.put("schoolId", request.getParameter("schoolId"));
			params.put("subject", request.getParameter("subject"));
			params.put("grade", request.getParameter("grade"));
		}
		
		iniToDateStr = request.getParameter("itiToDate");
		iniFromDateStr = request.getParameter("itiFromDate");
		eoyToDateStr = request.getParameter("eoyToDate");
		eoyFromDateStr = request.getParameter("eoyFromDate");
			
		params.put("iniToDateStr", iniToDateStr);
		params.put("iniFromDateStr", iniFromDateStr);
		params.put("eoyToDateStr", eoyToDateStr);
		params.put("eoyFromDateStr", eoyFromDateStr);
		
		Long newExtractId = (long) 0;
		Long recentExtractId = (long) 0;	
		
		if(null!= user.getContractingOrganization()){
			params.put("currentSchoolYear", user.getContractingOrganization().getCurrentSchoolYear());
			params.put("contractingOrgName", user.getContractingOrganization().getOrganizationName());
		}
		if(extractId <= 0) {
			recentExtractId = getRecentReportExtractId(user, type.getId());
		}
		if(recentExtractId == 0 || extractId == recentExtractId) {
			newExtractId = dataReportService.generateNewExtract(user, type, extractId, narrowestOrgId, narrowestOrgTypeId, params);
		} else {
			newExtractId = recentExtractId;
		}
		return newExtractId;
	}	

	private Long getRecentReportExtractId(User user, Short typeId) {
		ModuleReport moduleReport = dataReportService.getMostRecentReportByTypeId(user, typeId);
		Long recentExtractId = (long) 0;
		if(moduleReport != null) {
			recentExtractId = moduleReport.getId();
		}
		return recentExtractId;
	}

	private String getAssessmentProgram(User user) {
		if(user != null && user.getContractingOrganization() != null) {
			List<AssessmentProgram> contractingOrgAssessmentPrograms = assessmentProgramService.findByOrganizationId(user.getContractingOrgId());
			for(AssessmentProgram assessmentProgram : contractingOrgAssessmentPrograms) {
				if(StringUtils.isNotEmpty(assessmentProgram.getAbbreviatedname())) {
					if(assessmentProgram.getAbbreviatedname().equalsIgnoreCase("AMP")) {
						return "AMP";
					} else if(assessmentProgram.getAbbreviatedname().equalsIgnoreCase("KAP")) {
						return "KAP";
					} else if(assessmentProgram.getAbbreviatedname().equalsIgnoreCase("DLM")) {
						return "DLM";
					}
				}
			}
		}
		return StringUtils.EMPTY;
	}
	
	@RequestMapping(value = "monitorExtractGenerationStatus.htm", method = RequestMethod.POST)
	public final @ResponseBody String monitorExtractGenerationStatus(
			@RequestParam("extractId") final Long moduleReportId) throws Exception {

		ModuleReport moduleReport = dataReportService.getModuleReportById(moduleReportId);
		
		if(moduleReport != null) {
			return moduleReport.getStatusName();
		}
		
		return "";
	}
	
	@RequestMapping(value = "getTemplate.htm")
	public final void getTemplate(@RequestParam("templateName") String templateName,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			String filePath = "uploadtemplates" + File.separator + templateName;
			String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
			this.downloadReport( "Template not found.", path, request, response);
		}
	}
	
	@RequestMapping(value = "getDataExtractCsv.htm")
	public final void getAdminReportCsv(@RequestParam("extractName") String extractName,
			@RequestParam("extractType") String extractType, @RequestParam("extractId") String extractId,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			ModuleReport moduleReport = dataReportService.getModuleReportById(Long.valueOf(extractId));
			String path = null;
			if(extractName.equals(CommonConstants.FILE_PDF_EXTENSION)) {
				path= moduleReport.getPdfFileName();
			} else {
				path= moduleReport.getFileName();
			} 
			this.downloadReport("Extract not found.", path, request, response);
		}
	}

	@RequestMapping(value = "getDataDetail.htm")
	public final void getDataDetail(@RequestParam("dataDetailFileName") String fileName,
			@RequestParam("dataDetailFileLocation") String fileLocation,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			String path = FileUtil.buildFilePath(REPORT_PATH, fileLocation+File.separator+ fileName);
			this.downloadReport("Data detail not found.", path, request, response);
		}
	}
	
	@RequestMapping(value = "getSpecialDataDetail.htm")
	public final void getDataDetailOther(@RequestParam("specialDataDetailFileName") String fileName,
			@RequestParam("specialDataDetailFileLocation") String fileLocation,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			
			String path = FileUtil.buildFilePath(REPORT_PATH, fileLocation+File.separator+ fileName);
			this.downloadReport("Special data detail not found.", path, request, response);
		}
	}
	@RequestMapping(value = "getContentAreasForReporting.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentArea> getContentAreasForReporting(String reportType, Long districtId,
			Long schoolId,String assessmentProg,String assessmentProgCode, String reportCode,@RequestParam(value = "reportYear", required = false) Long reportYear) {
		List<ContentArea> contentAreas = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();				
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProg));				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				Organization contractingOrg = organizationService.getContractingOrganization(schoolId);
				if(reportYear != null){ //F680 Previous Report Year Newly Added
					params.put("schoolYear",reportYear);
					}else{
				params.put("schoolYear", getSchoolYearForReport(contractingOrg, reportType));
				Long cuntReportYear = organizationService.getReportYear(contractingOrg.getId(), apIds.get(0));
				if(cuntReportYear != null)
					params.put("schoolYear",cuntReportYear);
					}
				params.put("userCurrentRoleId",user.getCurrentGroupsId());
				params.put("assessmentProgramIds", apIds);
				params.put("assessmentProgCode", assessmentProgCode);
				params.put("reportCode", reportCode);
				Long currentReportYear  = null;
				Long assessmentProgId = Long.valueOf(assessmentProg);
				if(assessmentProgCode.equals("CPASS")){
					currentReportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgId);
				}else{
					currentReportYear = new Long(user.getContractingOrganization().getReportYear());
				}			   
				if(reportYear == null || currentReportYear.longValue() ==  reportYear.longValue()){
				    contentAreas = contentAreaService.getContentAreasWhereReportsHaveProcessed(params);
				}else{
					contentAreas = dataReportDetailsService.getContentAreasWhereReportsHaveProcessed(params);
				}			
			}
			else
				return null;
		}
		return contentAreas;
	}	

	@RequestMapping(value = "getGradesForReporting.htm", method = RequestMethod.GET)
	public final @ResponseBody List<GradeCourse> getGradesForReporting(String reportType, @RequestParam(required=false, value="subjectIds[]") Long[] subjectIds,
			Long districtId, Long schoolId,String assessmentProg, String reportCode,@RequestParam(value = "reportYear", required = false) Long reportYear,String assessmentProgCode) {
		List<GradeCourse> grades = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {				
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProg));				
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Organization contractingOrg = userDetails.getUser().getContractingOrganization();				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				if(reportCode.equals("ALT_STD_SUMMARY"))
					reportCode = REPORT_TYPE_STUDENT_SUMMARY;
				params.put("reportTypeCode", reportCode);			
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("assessmentProgramIds", apIds);
				params.put("contentAreaIds", (subjectIds == null) ? null : Arrays.asList(subjectIds));
				if(reportYear != null){ //F680 Previous Report Year Newly Added
				params.put("schoolYear",reportYear);
				}else{
				params.put("schoolYear", getSchoolYearForReport(contractingOrg, reportType));
				Long cuntReportYear = organizationService.getReportYear(contractingOrg.getId(), apIds.get(0));
				if(cuntReportYear != null)
					params.put("schoolYear",cuntReportYear);
				}							
				Long assessmentProgId = Long.valueOf(assessmentProg);
				Long currentReportYear  = null;
							if(assessmentProgCode.equals("CPASS") ){
								currentReportYear = orgService.getReportYear(contractingOrg.getId(), assessmentProgId);
							}else{
								currentReportYear = new Long(contractingOrg.getReportYear());
							}			   
					if(reportYear == null ||  currentReportYear.longValue() ==  reportYear.longValue()){
					grades = gradeCourseService.getGradesWhereReportsHaveProcessed(params);
				    }
				    else{
					grades = dataReportDetailsService.getGradesWhereReportsHaveProcessed(params);
				}
			}
		}
		return grades;
	}
		// getBundled Organization
	 @RequestMapping(value = "getBundledReportOrg.htm", method= RequestMethod.GET)
	    public final @ResponseBody List<Organization> getBundledReportOrg(Long districtId,Long assessmentProgId, String assessmentProgCode, String reportType, String reportCode){
	    	List<Organization> organizations = null;
	    	if (SecurityContextHolder.getContext().getAuthentication() != null &&
	    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {	    		
	    		if (isValidReportType(reportType)) {	    			
	    			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					Organization contractingOrg = userDetails.getUser().getContractingOrganization();	    			
	    			Map<String, Object> params = new HashMap<String, Object>();
					params.put("districtId", districtId);
					params.put("assessmentProgId", assessmentProgId);
					params.put("assessmentProgCode", assessmentProgCode);
					params.put("schoolYear", getSchoolYearForReport(contractingOrg, reportType));
					Long reportYear = organizationService.getReportYear(contractingOrg.getId(), assessmentProgId);
					if(reportYear != null)
						params.put("schoolYear",reportYear);
					if(assessmentProgCode.equals("DLM")) {
						if("alternate_student_summary_all".equalsIgnoreCase(reportType)){
							params.put("reportType", REPORT_TYPE_STUDENT_SUMMARY);
						}else{
							params.put("reportType", dbDLMStudentReportsImportReportType);
						}
						
					}else if(assessmentProgCode.equals("CPASS")){
						params.put("reportType", dbCPASSStudentReportsImportReportType);
					}
					organizations = organizationService.getBundledReportOrg(params);	    			
	    		} 	    		
	    	}	
	    	
	    	return organizations;
	    }
	
	//getContentAreaForBundledReport
	 @RequestMapping(value = "getContentAreaForBundledReport.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentArea> getContentAreaForBundledReport(String reportType,@RequestParam(required=false, value="schoolIds[]")  Long[] schoolIds, 
			String assessmentProgId, String assessmentProgCode){
		 List<ContentArea> contentArea = null;
		 
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
		 if (SecurityContextHolder.getContext().getAuthentication() != null &&
	    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {	
			 if (isValidReportType(reportType)) {
				 
				/* List<Long> schoolId = new ArrayList<Long>();
				 schoolId.add(Long.valueOf(schoolIds));*/
					
				Long assessmentProgramId = Long.valueOf(assessmentProgId);
				Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), Long.parseLong(assessmentProgId));
				contentArea = contentAreaService.getContentAreaForBundledReport(schoolIds,assessmentProgramId,assessmentProgCode, reportYear !=null ?reportYear.intValue(): user.getContractingOrganization().getReportYear());				 
			 }			 
			 
		 } 
		 
		 return contentArea;
	 }
	 
	 @RequestMapping(value="getGradesForBundledReporting.htm", method=RequestMethod.GET)
	 public final @ResponseBody List<GradeCourse> getGradesForBundledReporting(String reportType, 
				Long districtId, @RequestParam(required=false, value="schoolIds[]") Long[] schoolIds,
				@RequestParam(required=false, value="subjectIds[]") Long[] subjectIds, String assessmentProgIds, String assessmentProgCode, String reportCode){
		 List<GradeCourse> gradeCourse = null;
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
		 if (SecurityContextHolder.getContext().getAuthentication() != null &&
	    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {	
			 if (isValidReportType(reportType)) {
				Long assessmentProgId = Long.valueOf(assessmentProgIds);
				if(assessmentProgCode.equals("DLM")) reportCode = dbDLMStudentReportsImportReportType;
				else if(assessmentProgCode.equals("CPASS"))  reportCode = dbCPASSStudentReportsImportReportType;
				
				Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), assessmentProgId);
				gradeCourse = gradeCourseService.getGradesForBundledReporting(districtId, schoolIds, subjectIds, assessmentProgId,
						  assessmentProgCode, reportYear !=null ?reportYear.intValue(): user.getContractingOrganization().getReportYear(),reportCode);				 
			 }
			 
		 } 
		 return gradeCourse;
	 }
	 
	 @RequestMapping(value="getOrgsForGRFFilters.htm", method=RequestMethod.POST)
	 public final @ResponseBody List<Organization> getOrgsForGRFFilters(@RequestParam(required=false, value="stateId") Long stateId,
			                                                            @RequestParam(required =false, value="districtId") Long districtId,
			                                                            @RequestParam(required =false, value="orgType") String orgType,
			                                                            @RequestParam(required=false, value="isInactiveOrgReq") Boolean isInactiveOrgReq){		 
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		 User user = userDetails.getUser();
		 stateId = userDetails.getUser().getContractingOrgId();
		 
		 /*if(user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)){
			 districtLists.add(user.getOrganization());
		 }else if(user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE) || 
				 user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)){
			 
			 List<Organization> districts = dataExtractService.getOrganizationByTypeForGRF(stateId,districtId,orgType,user.getContractingOrganization().getReportYear());
			 districtLists.addAll(districts);
		 }*/		 
		 return dataExtractService.getOrganizationByTypeForGRF(stateId,districtId,orgType,user.getContractingOrganization().getReportYear());
	 }
	
	 @RequestMapping(value="getSchoolsInDistrictForGRF.htm", method = RequestMethod.GET)
	 public final @ResponseBody List<UploadGrfFile> getSchoolsInDistrictForGRF(
			 Long districtId, Long stateId){
		 
		 return dataReportService.getSchoolsInDistrictForGRF(districtId,stateId);
	 }
	 
	 @RequestMapping(value="getContentAreasforGRF.htm", method=RequestMethod.GET)
	 public final @ResponseBody List<ContentArea> getContentAreasforGRF(Long stateId,Long districtId, 
			 Long schoolId){
		 
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		 User user = userDetails.getUser();
		 stateId = userDetails.getUser().getContractingOrgId();
		 if(districtId == null) districtId = user.getOrganization().getRelatedOrganizationId();
		 return dataExtractService.getContentAreasforGRF(stateId,districtId,schoolId, user.getContractingOrganization().getReportYear());
	 }
	 
	 @RequestMapping(value="getGradeCourseByGRF.htm", method=RequestMethod.GET)
	 public final @ResponseBody List<GradeCourse> getGradeCourseByGRF(Long stateId,Long districtId, 
			 Long schoolId, Long contentAreaId){
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		 User user = userDetails.getUser();
		 stateId = userDetails.getUser().getContractingOrgId();
		 
		 return dataExtractService.getGradeCourseByGRF(stateId,districtId,schoolId,contentAreaId,user.getContractingOrganization().getReportYear());
	 }
	 
	@RequestMapping(value = "countStudentReports.htm", method = RequestMethod.GET)
	public final @ResponseBody Long countStudentReports(String reportType, Long districtId,
			Long schoolId, Long subjectId, Long gradeId,
			String assessmentProgram,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		Long count = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();
				
				//List<AssessmentProgram> aps = assessmentProgramService.findByOrganizationId(user.getContractingOrgId());
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				/*for (int x = 0; x < aps.size(); x++) {
					apIds.add(aps.get(x).getId());
				}*/
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				params.put("reportType", reportType);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("contentAreaId", subjectId);
				params.put("gradeId", gradeId);
				params.put("onlyRostered", user.isTeacher() || user.isProctor());
				params.put("userId", user.getId());
				params.put("reportYear", reportYear);
				if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
					count = studentReportService.countStudentReports(params);	
				}else{
					count = dataReportDetailsService.countStudentReports(params);
				}
				
			}
			else
				return 0l;
		}
		return count;
	}
	
	@RequestMapping(value = "getStudentReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<StudentReportDTO> getStudentReports(String reportType, Long districtId,
			Long schoolId, Long subjectId, Long gradeId, Long page, Long perPage, String assessmentProgram,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		List<StudentReportDTO> reports = new ArrayList<StudentReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				/*List<AssessmentProgram> aps = assessmentProgramService.findByOrganizationId(user.getContractingOrgId());
				
				for (int x = 0; x < aps.size(); x++) {
					apIds.add(aps.get(x).getId());
				}*/
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("assessmentProgramIds", apIds);
					params.put("reportType", reportType);
					params.put("districtId", districtId);
					params.put("schoolId", schoolId);
					params.put("contentAreaId", subjectId);
					params.put("gradeId", gradeId);
					params.put("onlyRostered", user.isTeacher() || user.isProctor());
					params.put("userId", user.getId());
					params.put("reportYear", reportYear);
					params.put("offset", (page - 1) * perPage);
					params.put("limit", perPage);
					if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
						reports = studentReportService.getStudentReports(params);	
					}						
				else{
					reports = dataReportDetailsService.getStudentReports(params);	
				}
				
				
			}
			else
				return null;
		}
		return reports;
	}
	
	@RequestMapping(value = "getStudentsWithWritingResponses.htm", method = RequestMethod.GET)
	public final @ResponseBody List<StudentReportDTO> getStudentsWithWritingResponses(@RequestParam("districtId") Long districtId,
			@RequestParam("schoolId") Long schoolId, @RequestParam("gradeId") Long gradeId, @RequestParam("reportType") String reportType) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<StudentReportDTO> students = new ArrayList<StudentReportDTO>();
		if (auth != null && auth.isAuthenticated()) {
			if (isValidReportType(reportType)) {
				User user = ((UserDetailImpl) auth.getPrincipal()).getUser();
				Long apId = user.getCurrentAssessmentProgramId();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", Arrays.asList(apId));
				params.put("reportType", reportType);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("gradeId", gradeId);
				params.put("isTeacher", user.isTeacher() || user.isProctor());
				params.put("userId", user.getId());
				params.put("testSessionSource", SourceTypeEnum.BATCHAUTO.getCode());
				AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(apId);
				if (ap.getAbbreviatedname() != null) {
					String apName = ap.getAbbreviatedname();
					if (apName.equals("KAP")) {
						params.put("contentAreaAbbr", "SS");
					} else if (apName.equals("KELPA2")) {
						params.put("contentAreaAbbr", "ELP");
					}
				}
				//Organization contractingOrg = orgService.getContractingOrganization(schoolId);
				//params.put("reportYear", contractingOrg.getReportYear());
				params.put("schoolYear", getSchoolYearForReport(user.getContractingOrganization(), reportType));
				
				students = dataExtractService.getWritingStudents(params);
			}
		}
		return students;
	}
	
	@RequestMapping(value = "getWritingResponsesForStudents.htm", method = RequestMethod.POST)
	public final @ResponseBody List<StudentReportDTO> getWritingResponsesForStudents(@RequestParam("ids[]") Long[] studentIds,
			@RequestParam("districtId") Long districtId, @RequestParam("schoolId") Long schoolId, @RequestParam("gradeId") Long gradeId,
			@RequestParam("includeScoredResponses") Boolean includeScored) {
		List<StudentReportDTO> studentReports = new ArrayList<StudentReportDTO>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) auth.getPrincipal();
			if (permissionUtil.hasPermission(userDetails.getAuthorities(), "VIEW_GENERAL_STUDENT_WRITING")) {
				User user = ((UserDetailImpl) auth.getPrincipal()).getUser();
				Long apId = user.getCurrentAssessmentProgramId();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", Arrays.asList(apId));
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("gradeId", gradeId);
				params.put("schoolYear", getSchoolYearForReport(user.getContractingOrganization(), "general_student_writing"));
				params.put("studentIds", studentIds);
				params.put("includeScored", Boolean.TRUE.equals(includeScored));
				params.put("isTeacher", user.isTeacher() || user.isProctor());
				params.put("userId", user.getId());
				params.put("testSessionSource", SourceTypeEnum.BATCHAUTO.getCode());
				AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(apId);
				if (ap.getAbbreviatedname() != null) {
					String apName = ap.getAbbreviatedname();
					if (apName.equals("KAP")) {
						params.put("contentAreaAbbr", "SS");
					} else if (apName.equals("KELPA2")) {
						params.put("contentAreaAbbr", "ELP");
					}
				}
				studentReports = dataExtractService.getWritingResponseForReports(params);
			}
		}
		
		return studentReports;
	}
	@RequestMapping(value = "getWritingResponsesPDF.htm", method = RequestMethod.GET)
	public final void getWritingResponsesPDF(HttpServletResponse response,
			@RequestParam("ids[]") Long[] studentIds, @RequestParam("districtId") Long districtId,
			@RequestParam("schoolId") Long schoolId, @RequestParam("gradeId") Long gradeId,
			@RequestParam("includeScoredResponses") Boolean includeScored) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		List<StudentReportDTO> studentReports = new ArrayList<StudentReportDTO>();
		if (auth != null && auth.isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) auth.getPrincipal();
			if (permissionUtil.hasPermission(userDetails.getAuthorities(), "VIEW_GENERAL_STUDENT_WRITING")) {
				User user = ((UserDetailImpl) auth.getPrincipal()).getUser();
				Long apId = user.getCurrentAssessmentProgramId();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", Arrays.asList(apId));
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("gradeId", gradeId);
				params.put("schoolYear", getSchoolYearForReport(user.getContractingOrganization(), "general_student_writing"));
				params.put("studentIds", studentIds);
				params.put("includeScored", Boolean.TRUE.equals(includeScored));
				params.put("isTeacher", user.isTeacher() || user.isProctor());
				params.put("userId", user.getId());
				params.put("testSessionSource", SourceTypeEnum.BATCHAUTO.getCode());
				AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(apId);
				if (ap.getAbbreviatedname() != null) {
					String apName = ap.getAbbreviatedname();
					if (apName.equals("KAP")) {
						params.put("contentAreaAbbr", "SS");
					} else if (apName.equals("KELPA2")) {
						params.put("contentAreaAbbr", "ELP");
					}
				}
				studentReports = dataExtractService.getWritingResponseForReports(params);
			}
			response.setContentType(MimeConstants.MIME_PDF);
			response.setHeader("Content-Disposition","attachment; filename=\"Student Writing Essays.pdf\"");
			
			File pdfFile = studentReportGenerator.generateWritingReport(studentReports);
			
			if (pdfFile != null) {
				try {
					IOUtils.copy(new FileInputStream(pdfFile), response.getOutputStream());
					response.flushBuffer();
					FileUtils.deleteQuietly(pdfFile);
				} catch (IOException ioe) {
					LOGGER.error("Error writing PDF file to output stream:", ioe);
				}
			}
		}
	}
	
	@RequestMapping(value = "getExternalStudentReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ExternalStudentReportDTO> getExternalStudentReports(String reportType, Long districtId,
			Long schoolId, Long subjectId, Long gradeId, Long page, Long perPage, String assessmentProgram,String assessmentProgCode, String reportCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		List<ExternalStudentReportDTO> reports = new ArrayList<ExternalStudentReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				/*List<AssessmentProgram> aps = assessmentProgramService.findByOrganizationId(user.getContractingOrgId());
				
				for (int x = 0; x < aps.size(); x++) {
					apIds.add(aps.get(x).getId());
				}*/
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}				
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				params.put("assessmentProgCode", assessmentProgCode);
				params.put("reportType", reportType);
				if(assessmentProgCode.equals("DLM")) params.put("reportTypeCode", dbDLMStudentReportsImportReportType);
				else if(assessmentProgCode.equals("CPASS"))  params.put("reportTypeCode", dbCPASSStudentReportsImportReportType);				
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("contentAreaId", subjectId);
				params.put("gradeId", gradeId);
				params.put("onlyRostered", user.isTeacher() || user.isProctor());
				params.put("userId", user.getId());
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("reportYear", reportYear);				
				Long assessmentProgId = Long.valueOf(assessmentProgram);
				Long currentReportYear  = null;
							if(assessmentProgCode.equals("CPASS") ){
								currentReportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgId);
							}else{
								currentReportYear = new Long(user.getContractingOrganization().getReportYear());
							}			   
							if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){
				reports = studentReportService.getExternalStudentReports(params);
				}else{
					reports = dataReportDetailsService.getExternalStudentReports(params);
				}
			}
			else
				return null;
		}
		return reports;
	}
	
	@RequestMapping(value = "countExternalStudentReports.htm", method = RequestMethod.GET)
	public final @ResponseBody Long countExternalStudentReports(String reportType, Long districtId,
			Long schoolId, Long subjectId, Long gradeId,
			String assessmentProgram,String assessmentProgCode, String reportCode , @RequestParam(value = "reportYear", required = false) Long reportYear ) {
		Long count = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();				
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				params.put("assessmentProgCode", assessmentProgCode);
				params.put("reportType", reportType);
				if(assessmentProgCode.equals("DLM")) params.put("reportTypeCode", dbDLMStudentReportsImportReportType);
				else if(assessmentProgCode.equals("CPASS"))  params.put("reportTypeCode", dbCPASSStudentReportsImportReportType);				
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("contentAreaId", subjectId);
				params.put("gradeId", gradeId);
				params.put("onlyRostered", user.isTeacher() || user.isProctor());
				params.put("userId", user.getId());
				params.put("reportYear", reportYear);
				Long assessmentProgId = Long.valueOf(assessmentProgram);
				Long currentReportYear  = null;
							if(assessmentProgCode.equals("CPASS") ){
								currentReportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgId);
							}else{
								currentReportYear = new Long(user.getContractingOrganization().getReportYear());
							}			   
							if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){
								count = studentReportService.countExternalStudentReports(params);}
							else{
								count = dataReportDetailsService.countExternalStudentReports(params); 	
							}
			}
			else
				return 0l;
		}
		return count;
	}
	
	@RequestMapping(value = "getExternalStudentReportsForTeacherRoster.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ExternalStudentReportDTO> getExternalStudentReportsForTeacherRoster(String reportType, Long rosterId,
			 Long page, Long perPage, String assessmentProgram,String assessmentProgCode, String reportCode, Long reportYear, Long teacherRosterId) {
		List<ExternalStudentReportDTO> reports = new ArrayList<ExternalStudentReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				int currentReportYear = userDetails.getUser().getContractingOrganization().getReportYear();
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				params.put("rosterId", teacherRosterId);
				if(assessmentProgCode.equals("DLM")) params.put("reportTypeCode", dbDLMStudentReportsImportReportType);
				else if(assessmentProgCode.equals("CPASS"))  params.put("reportTypeCode", dbCPASSStudentReportsImportReportType);				
				params.put("reportYear", reportYear);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				
				reports = studentReportService.getExternalStudentReportsForTeacherRoster(params);
			}
			else
				return null;
		}
		return reports;
	}
	
	@RequestMapping(value = "countExternalStudentReportsForTeacherRoster.htm", method = RequestMethod.GET)
	public final @ResponseBody Long countExternalStudentReportsForTeacherRoster(String reportType, Long rosterId,
			String assessmentProgram,String assessmentProgCode,String reportCode) {
		Long count = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				int reportYear = userDetails.getUser().getContractingOrganization().getReportYear();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				params.put("reportYear", reportYear);				
				if(assessmentProgCode.equals("DLM")) params.put("reportTypeCode", dbDLMStudentReportsImportReportType);
				else if(assessmentProgCode.equals("CPASS"))  params.put("reportTypeCode", dbCPASSStudentReportsImportReportType);				
				params.put("rosterId", rosterId);				
				count = studentReportService.countExternalStudentReportsForTeacherRoster(params);
			}
			else
				return 0l;
		}
		return count;
	}	
	
	@RequestMapping(value = "countSchoolDetailReports.htm", method = RequestMethod.GET)
	public final @ResponseBody Long countSchoolDetailReports(String reportType, Long districtId, Long schoolId,
			Long assessmentProgram,String reportCode,@RequestParam(value = "reportYear", required = false) Long reportYear,String assessmentProgCode) {
		Long count = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		        User user = userDetails.getUser();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("assessmentProgram", assessmentProgram);
				params.put("reportYear", reportYear);
				params.put("reportCode", reportCode); 
				params.put("currentroleId",user.getCurrentGroupsId());
				params.put("stateId", user.getContractingOrgId());				
				Long currentReportYear  = null;
							if(assessmentProgCode.equals("CPASS") ){
								currentReportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgram);
							}else{
								currentReportYear = new Long(user.getContractingOrganization().getReportYear());
							}			   
							if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){
				count = studentReportService.countSchoolDetailReports(params);}
				else{
					count = dataReportDetailsService.countSchoolDetailReports(params);
				}
			}
			else
				return 0l;
		}
		return count;
	}
	
	@RequestMapping(value = "getSchoolDetailReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<SchoolAndDistrictReportDTO> getSchoolDetailReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,String assessmentProgCode,Long assessmentProgram,String reportCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear)
			{
		List<SchoolAndDistrictReportDTO> reports = new ArrayList<SchoolAndDistrictReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			      User user = userDetails.getUser();
			      Map<String, Object> params = null;
			     // Long userCurrentRoleId = user.getCurrentGroupsId();	
			      
			    	  params = new HashMap<String, Object>();
			    	  AssessmentProgram assessProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgram);						
					  params.put("reportType", reportType);
					  params.put("reportCode", reportCode); 
					  params.put("districtId", districtId);
					  params.put("schoolId", schoolId);
					  params.put("currentroleId",user.getCurrentGroupsId());
					  params.put("stateId", user.getContractingOrgId());
					  params.put("assessmentProgramId", assessmentProgram);
					  params.put("assessmentProgramCode", assessProgram.getAbbreviatedname());
					  params.put("offset", (page - 1) * perPage);
					  params.put("limit", perPage);
					  params.put("reportYear", reportYear);						
						Long currentReportYear  = null;
									if(assessmentProgCode.equals("CPASS") ){
										currentReportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgram);
									}else{
										currentReportYear = new Long(user.getContractingOrganization().getReportYear());
									}			   
									if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){
						reports = studentReportService.getSchoolDetailReports(params); 
			          }else{
			    	  reports = dataReportDetailsService.getSchoolDetailReports(params); 
			          }
					  }
						else
				return null;
		}
		return reports;
	}
	
	@RequestMapping(value = "getSchoolSummaryReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<SchoolAndDistrictReportDTO> getSchoolSummaryReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage, String reportCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear,Long assessmentProgram) {
		List<SchoolAndDistrictReportDTO> reports = new ArrayList<SchoolAndDistrictReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			        User user = userDetails.getUser();
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}

				List<Long> contentAreaIds = null;
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("reportTypeCode", reportCode);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("reportYear", reportYear);
				params.put("assessmentProgramid", assessmentProgram);
				params.put("userCurrentRoleId",user.getCurrentGroupsId());	
				params.put("schoolYear",reportYear);	
				
				if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
				    contentAreaIds = contentAreaService.getContentAreasIdForDistrictSummaryReportsHaveProcessed(params);
				    params.put("contentAreaIds", contentAreaIds);
					reports = studentReportService.getSchoolSummaryReports(params);
				}else{
					reports = dataReportDetailsService.getSchoolSummaryReports(params);
				}
				
			}
			else
				return null;
		}
		return reports;
	}
	
	@RequestMapping(value = "countDistrictDetailReports.htm", method = RequestMethod.GET)
	public final @ResponseBody Long countDistrictDetailReports(String reportType, Long districtId) {
		Long count = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("districtId", districtId);
				
				count = studentReportService.countDistrictDetailReports(params);
			}
			else
				return 0l;
		}
		return count;
	}
	
	@RequestMapping(value = "getDistrictDetailReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<SchoolAndDistrictReportDTO> getDistrictDetailReports(String reportType, Long districtId,
			Long page, Long perPage) {
		List<SchoolAndDistrictReportDTO> reports = new ArrayList<SchoolAndDistrictReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("districtId", districtId);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				
				reports = studentReportService.getDistrictDetailReports(params);
			}
			else
				return null;
		}
		return reports;
	}
	
	@RequestMapping(value = "getAllStudentsReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationReportDetails> getAllStudentsReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,Long assessmentProgram,String reportCode, String assessmentProgCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		List<OrganizationReportDetails> reports = new ArrayList<OrganizationReportDetails>();		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			        User user = userDetails.getUser();				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("reportTypeCode", reportCode);
				params.put("districtId", districtId);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("assessmentId", assessmentProgram);
				params.put("assessmentProgCode", assessmentProgCode);
				params.put("reportYear", reportYear );
				Long currentReportYear  = null;
							if(assessmentProgCode.equals("CPASS") ){
								currentReportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgram);
							}else{
								currentReportYear = new Long(user.getContractingOrganization().getReportYear());
							}			   
							if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){	
					List<Long> schoolIds = new ArrayList<Long>();			
					AssessmentProgram assessment = assessmentProgramService.findByAssessmentProgramId(assessmentProgram);							
					if(districtId!=null && schoolId!=null)
					{
						//For all schools in a district
						if(schoolId == -1){
							//Get all the schools in the district for which file paths exist
							if("CPASS".equalsIgnoreCase(assessment.getAbbreviatedname()) || "DLM".equalsIgnoreCase(assessment.getAbbreviatedname())){
								String individualReportType="";
								if("CPASS".equalsIgnoreCase(assessment.getAbbreviatedname()))
										individualReportType=dbCPASSStudentReportsImportReportType;
								else if("DLM".equalsIgnoreCase(assessment.getAbbreviatedname()))
										individualReportType=dbDLMStudentReportsImportReportType;							
							   schoolIds = studentReportService.getSchoolIdsForDistrictDLMandCpass(districtId,individualReportType,reportYear);
							}else{
								schoolIds = studentReportService.getSchoolIdsForDistrict(districtId,reportYear);
							}
						}else if(schoolId == 0){  // To get district bundled reports
							schoolIds.add(districtId);
						}else
							//If a single school is selected
							schoolIds.add(schoolId);
					}
					//For each school retrieve and save the file path and size
					params.put("schoolIds", schoolIds);				
					if(schoolIds.size()>0)
						reports = studentReportService.getAllStudentsReports(params);
					else 
						return null;	
				}else{
					List<Long> schoolIds = new ArrayList<Long>();			
					AssessmentProgram assessment = assessmentProgramService.findByAssessmentProgramId(assessmentProgram);							
					if(districtId!=null && schoolId!=null)
					{
						//For all schools in a district
						if(schoolId == -1){
							//Get all the schools in the district for which file paths exist
							if("CPASS".equalsIgnoreCase(assessment.getAbbreviatedname()) || "DLM".equalsIgnoreCase(assessment.getAbbreviatedname())){
								String individualReportType="";
								if("CPASS".equalsIgnoreCase(assessment.getAbbreviatedname()))
										individualReportType=dbCPASSStudentReportsImportReportType;
								else if("DLM".equalsIgnoreCase(assessment.getAbbreviatedname()))
										individualReportType=dbDLMStudentReportsImportReportType;							
								
							   schoolIds = dataReportDetailsService.getSchoolIdsForDistrictDLMandCpass(districtId,individualReportType,reportYear);
							}else{
								schoolIds = dataReportDetailsService.getSchoolIdsForDistrict(districtId,reportYear);
							}
						}else if(schoolId == 0){  // To get district bundled reports
							schoolIds.add(districtId);
						}else
							//If a single school is selected
							schoolIds.add(schoolId);
					}
					//For each school retrieve and save the file path and size
					params.put("schoolIds", schoolIds);				
					if(schoolIds.size()>0)
						reports = dataReportDetailsService.getAllStudentsReports(params);
					else 
						return null;	
				}			
			}
			else
				return null;
		}
		return reports;
	}
	
	private boolean isValidReportType(String reportType) {
		List<String> supportedTypes = Arrays.asList(new String[] {
				"general_student", "general_student_all","general_school_detail", "general_school_summary", "general_district_detail", "general_district_summary", "general_student_writing",
				"alternate_student", "alternate_roster", "alternate_student_individual", "alternate_student_individual_teacher", "alternate_student_all", "alternate_blueprint_coverage","kelpa_elp_student_score",
				"alternate_yearend_district_summary","alternate_yearend_state_summary", "alternate_student_summary_all", "alternate_school_summary_all", "alternate_student_summary","alternate_student_summary_teacher","alternate_school_summary","alternate_classroom",
				"alternate_student_dcps","kelpa_student_individual","kelpa_student_all"
		});
		return supportedTypes.contains(reportType);
	}
	
	private Long getSchoolYearForReport(Organization org, String reportType) {
		boolean needsCurrentSchoolYear = Arrays.asList(
			"alternate_blueprint_coverage", "general_student_writing"
		).contains(reportType);
		return needsCurrentSchoolYear ? org.getCurrentSchoolYear() : new Long(org.getReportYear());
	}
	
	@RequestMapping(value = "getStudentReportFile.htm")
	public final void getStudentReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id , 
			@RequestParam(value="reportyear",required=false) Long reportyear)
			throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			StudentReport report = null;
			if(userDetails.getUser().getContractingOrganization().getReportYear() == reportyear){
				 report = studentReportService.getByPrimaryKeyAndUserOrg(id, userDetails.getUser().getOrganizationId());	
			}else{
				report = dataReportDetailsService.getByPrimaryKeyAndUserOrg(id, userDetails.getUser().getOrganizationId());	
			}
			
			if (report != null) {
				String path = FileUtil.buildFilePath(REPORT_PATH, report.getFilePath());
				this.downloadReport("Student report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
	}
	
	@RequestMapping(value = "getExternalStudentReportFile.htm")
	public final void getExternalStudentReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id,
			@RequestParam(value="rosterid",required=false) Long rosterId,@RequestParam(value="reportyear",required=false) Long reportYear,String assessmentProgCode,String assessmentid)
			throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Externalstudentreports report = null;
			if (rosterId != null){
				Roster roster = rosterService.selectByPrimaryKey(rosterId);
				report = studentReportService.getExternalReportFileDetailsByPrimaryKey(id, roster.getAttendanceSchoolId());
			} else {				
				Long assessmentProgId = Long.valueOf(assessmentid);
				Long currentReportYear  = null;
							if(assessmentProgCode.equals("CPASS") ){
								currentReportYear = orgService.getReportYear(userDetails.getUser().getContractingOrganization().getId(), assessmentProgId);
							}else{
								currentReportYear = new Long(userDetails.getUser().getContractingOrganization().getReportYear());
							}							
							if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){
						report = studentReportService.getExternalReportFileDetailsByPrimaryKey(id, userDetails.getUser().getOrganizationId());	
					}else{
						report = dataReportDetailsService.getExternalReportFileDetailsByPrimaryKey(id, userDetails.getUser().getOrganizationId());
					}			
				
				
				
			}
			
			
			
			if (report != null) {
				String path = FileUtil.buildFilePath(REPORT_PATH, report.getFilePath());
				this.downloadReport("External Student report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
	}
	
	@RequestMapping(value = "getSchoolDetailReportFile.htm")
	public final void getSchoolDetailReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id,
			@RequestParam(value="reportyear",required=false) Long reportYear,String assessmentProgCode,Long assessmentid) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SchoolAndDistrictReportDTO report = null;
			Long assessmentProgId = Long.valueOf(assessmentid);
			Long currentReportYear  = null;
						if(assessmentProgCode.equals("CPASS") ){
							currentReportYear = orgService.getReportYear(userDetails.getUser().getContractingOrganization().getId(), assessmentProgId);
						}else{
							currentReportYear = new Long(userDetails.getUser().getContractingOrganization().getReportYear());
						}			   
						if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){
				report = studentReportService.getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(
						id, userDetails.getUser().getOrganizationId());
			}else{
				report = dataReportDetailsService.getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(
						id, userDetails.getUser().getOrganizationId());
			}
			
			
			if (report != null) {
				String filePath = report.getDetailedReportPath();
				String fileName = report.getOrganizationId()+"_School_detail_KITE_"+report.getSchoolYear()+"_"+report.getSubjectName()+"_"+report.getGradeName()+".pdf";
				fileName = fileName.replaceAll(" ", "_");
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("School detail report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
	}
	
	@RequestMapping(value = "getSchoolSummaryReportFile.htm")
	public final void getSchoolSummaryReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id,
			 @RequestParam(value="reportyear",required=false) Long reportyear) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SchoolAndDistrictReportDTO report = null;
			if(userDetails.getUser().getContractingOrganization().getReportYear() == reportyear ){
				 report = studentReportService.getSchoolSummaryReportFile(id, userDetails.getUser().getOrganizationId());	
			}else{
				 report = dataReportDetailsService.getSchoolSummaryReportFile(id, userDetails.getUser().getOrganizationId());
			}
			
			if (report != null) {
				String filePath = report.getDetailedReportPath();
				String fileName = report.getAttSchDisplayIdentifier()+"_School_summary_KITE_"+report.getSchoolYear()+"_"+report.getSubjectName()+".pdf";
				fileName = fileName.replaceAll(" ", "_");
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("School summary report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
	}
	
	@RequestMapping(value = "getDistrictDetailReportFile.htm", method = RequestMethod.GET)
	public final void getDistrictDetailReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id) throws IOException {
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			SchoolAndDistrictReportDTO report = studentReportService.getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(
					id, userDetails.getUser().getOrganizationId());
			if (report != null) {
				String filePath = report.getDetailedReportPath();
				String fileName = report.getOrganizationId()+"_District_detail_KITE_"+report.getSchoolYear()+"_"+report.getSubjectName()+"_"+report.getGradeName()+".pdf";
				fileName = fileName.replaceAll(" ", "_");
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("District detail report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
		
	}
	
	
	@RequestMapping(value = "getAllStudentsReportFile.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> getAllStudentsReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id,
			@RequestParam(value="reportYear",required=false) Long reportYear,@RequestParam(value="assessmentProgCode",required=false) String assessmentProgCode,@RequestParam(value="assessmentProgramId",required=false) String assessmentProgramId) throws IOException {
		String s3downloadurl = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User user = userDetails.getUser();
	        OrganizationReportDetails report = null;
	        Long assessmentProgId = null;
	        if(assessmentProgramId != null)
	    	assessmentProgId = Long.valueOf(assessmentProgramId);
	    	Long currentReportYear  = null;
	    				if(assessmentProgCode.equals("CPASS") ){
	    					currentReportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgId);
	    				}else{
	    					currentReportYear = new Long(user.getContractingOrganization().getReportYear());
	    				}			   
	    				if(reportYear != null && currentReportYear.longValue() ==  reportYear.longValue()){
	        	 report = studentReportService.getSchoolAndDistrictReport(id);	
			}else{
				 report = dataReportDetailsService.getSchoolAndDistrictReport(id);	
			}			
			if (report != null) {
				String filePath = report.getDetailedReportPath();
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				if (s3.doesObjectExist(path)){
					// downloadController.download(request, response, path);
					// use presigned url to download reports instead of using the above implementation 
					// because that way the load will be on users network
					s3downloadurl =  s3.generatePresignedUrlforStudentReport(path).toString();
				} else {
					response.sendError(404, "Student report not found.");
					LOGGER.error("Unable to download report: Student report not found.");
				}
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
		return Collections.singletonMap("downloadurl", s3downloadurl);

	}
	
	@RequestMapping(value = "getCommissionersLetter.htm")
	public final void getCommissionersLetter(final HttpServletRequest request, final HttpServletResponse response, Long id) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			String filePath = "/reports/2015_CommissionersLetter.pdf";
			String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
			this.downloadReport("Commissioner letter not found.", path, request, response);
		}
	}
	
	
	@RequestMapping(value = "getDistrictSummaryReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<SchoolAndDistrictReportDTO> getDistrictSummaryReport(String reportType, Long districtId,
			Long page, Long perPage ,String reportCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear,Long assessmentProgram) {
		List<SchoolAndDistrictReportDTO> reports = new ArrayList<SchoolAndDistrictReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				List<Long> contentAreaIds = null;
				
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		        User user = userDetails.getUser();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("reportTypeCode", reportCode);
				params.put("districtId", districtId);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("reportYear", reportYear);
				params.put("assessmentProgramid", assessmentProgram); 
				params.put("userCurrentRoleId",user.getCurrentGroupsId());
				params.put("schoolYear",reportYear);		
			    
				if(user.getContractingOrganization().getReportYear() == reportYear.longValue() ){	
				    contentAreaIds = contentAreaService.getContentAreasIdForDistrictSummaryReportsHaveProcessed(params);
				    params.put("contentAreaIds", contentAreaIds);
					reports = studentReportService.getDistrictSummaryReport(params);
				}else{
					reports = dataReportDetailsService.getDistrictSummaryReport(params);
				}
				
			}
			else
				return null;
		}
		return reports;
	}
	
	@RequestMapping(value = "getDistrictSummaryReportFile.htm", method = RequestMethod.GET)
	public final void getDistrictSummaryReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id,
			@RequestParam(value="reportyear",required=false) Long reportyear) throws IOException {
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SchoolAndDistrictReportDTO report = null;
			if(userDetails.getUser().getContractingOrganization().getReportYear() == reportyear ){
				 report = studentReportService.getDistrictSummaryReportByPrimaryKeyAndUserOrgId(
							id, userDetails.getUser().getOrganizationId());
			}else{
				 report = dataReportDetailsService.getDistrictSummaryReportByPrimaryKeyAndUserOrgId(
							id, userDetails.getUser().getOrganizationId());
			}
			
			if (report != null) {
				String filePath = report.getDetailedReportPath();
				String fileName = report.getAttSchDisplayIdentifier()+"_District_summary_KITE_"+report.getSchoolYear()+"_"+report.getSubjectName() +".pdf";
				fileName = fileName.replaceAll(" ", "_");
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("District summary report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); 
			}
		}
		
	}
	
	@RequestMapping(value = "getDlmDistrictSummaryReportFile.htm", method = RequestMethod.GET)
	public final void getDlmDistrictSummaryReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id, 
			String downloadType,@RequestParam(value = "reportyear", required = false) Long reportyear) throws IOException {
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			SchoolAndDistrictReportDTO report = null;
			if(userDetails.getUser().getContractingOrganization().getReportYear() == reportyear.longValue()){
			 report = studentReportService.getDlmSummaryReportByPrimaryKeyAndUserOrgId(
					id, userDetails.getUser().getOrganizationId(),"ALT_DS");
			}else{
				 report = dataReportDetailsService.getDlmSummaryReportByPrimaryKeyAndUserOrgId(
							id, userDetails.getUser().getOrganizationId(),"ALT_DS");	
			}
			if (report != null) {
				String filePath = report.getDetailedReportPath();				
				String fileName = report.getAttSchDisplayIdentifier()+"_District_Aggregate_KITE_"+report.getSchoolYear()+"_"+report.getOrganizationname() +".pdf";
				
				if(downloadType.equals("csv")){
					filePath = report.getSummaryReportCsvPath();
					fileName = report.getAttSchDisplayIdentifier()+"_District_Aggregate_KITE_"+report.getSchoolYear()+"_"+report.getOrganizationname() +".csv";
				}			
				
				fileName = fileName.replaceAll(" ", "_");
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("DLM district summary report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); 
			}
		}
		
	}
	
	@RequestMapping(value = "getDlmStateSummaryReportFile.htm", method = RequestMethod.GET)
	public final void getDlmStatetSummaryReportFile(final HttpServletRequest request, final HttpServletResponse response,
			Long id, String downloadType, @RequestParam(value="reportyear",required=false) Long reportyear) throws IOException {
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			SchoolAndDistrictReportDTO report = null;
			if(userDetails.getUser().getContractingOrganization().getReportYear() == reportyear ){
				report = studentReportService.getDlmSummaryReportByPrimaryKeyAndUserOrgId(
						id, userDetails.getUser().getOrganizationId(),"ALT_SS");
			}else{
				report = dataReportDetailsService.getDlmSummaryReportByPrimaryKeyAndUserOrgId(
						id, userDetails.getUser().getOrganizationId(),"ALT_SS");
			}
			if (report != null) {
				String filePath = report.getDetailedReportPath();				
				String fileName = report.getAttSchDisplayIdentifier()+"_State_Aggregate_KITE_"+report.getSchoolYear()+"_"+report.getOrganizationname() +".pdf";
				if(downloadType.equals("csv")){
					filePath = report.getSummaryReportCsvPath();
					fileName = report.getAttSchDisplayIdentifier()+"_State_Aggregate_KITE_"+report.getSchoolYear()+"_"+report.getOrganizationname() +".csv";
				}
				fileName = fileName.replaceAll(" ", "_");
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("DLM state summary report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); 
			}
		}
		
	}
	
	@RequestMapping(value = "getDLMRosterReport.htm")
	@ResponseBody
	public final Map<String, Object> getDLMRosterReport(final HttpServletResponse response, final HttpServletRequest request) throws Exception {
		LOGGER.trace("Entering the getDLMRosterReport() method");
		Map<String, Object> parentReportMap = new HashMap<String, Object>();
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();
				
				List<AssessmentProgram> aps = assessmentProgramService.findByOrganizationId(user.getContractingOrgId());
				List<Long> apIds = new ArrayList<Long>(aps.size());
				for (int x = 0; x < aps.size(); x++) {
					apIds.add(aps.get(x).getId());
				}
				
				Long schoolId = Long.valueOf(request.getParameter("schoolId"));
				Long rosterId = Long.valueOf(request.getParameter("rosterId"));
				Long subjectId = Long.valueOf(request.getParameter("subjectId"));
				String[] studentIds = request.getParameterValues("students[]");
				String schoolName = request.getParameter("schoolName");
				String subjectName = request.getParameter("subjectName");
				String[] studentNames = request.getParameterValues("studentNames[]");
				String rosterName = request.getParameter("rosterName");
				String resourcePath = request.getRequestURL().toString().replace(request.getServletPath(), "");
				RosterReport rosterReport = rosterGenerator.generateRosterReport(apIds, schoolId, 
						rosterId, studentIds, schoolName, subjectName, studentNames
						, rosterName, resourcePath, subjectId);
				/**
				 * Pass final data to UI.
				 */
				parentReportMap.put("rosterReportData", rosterReport);
				
			LOGGER.trace("Leaving the getDLMRosterReport() method.");
		}
		return parentReportMap;
	}

	@RequestMapping(value = "getDLMRosterReportFile.htm")
	public final void getDLMRosterReportFile(@RequestParam("schoolId") Long schoolId,
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("rosterName") String rosterName,
			@RequestParam("studentIds") String studentIdsParam,
			@RequestParam("schoolName") String schoolName,
			@RequestParam("contentAreaName") String contentAreaName,
			@RequestParam("studentNames") String studentNamesParam,
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("contentAreaId") Long contentAreaId,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
	
		
		String[] studentNames = studentNamesParam.split("--");
		String[] studentIds = studentIdsParam.split("--");
		List<Long> apIds = new ArrayList<Long>();
		apIds.add(assessmentProgramId);
		String resourcePath = request.getRequestURL().toString().replace(request.getServletPath(), "");
		RosterReport rosterReport = rosterGenerator.generateRosterReport(apIds, schoolId, rosterId
				, studentIds, schoolName, contentAreaName, studentNames
				, rosterName, resourcePath, contentAreaId);
		if (rosterReport != null) {
			OutputStream out = response.getOutputStream();
	 		response.setContentType("application/pdf");
	 		String fileName = "RosterReport_"+schoolName+"_"+ contentAreaName +"_"+rosterName + ".pdf";
	 		fileName = fileName.replaceAll(" ", "");
			
			response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
			
			try {
				rosterGenerator.setupRosterReportPDFGeneration(rosterReport, out);
	 		} 
			catch(NullPointerException ne){
				LOGGER.error("NullPointerException in getDLMRosterReportFile() method.", ne);
			}
			finally {
	 			if (out != null) {
	 				out.close();
	 			}
	 		}
		}
	}
	
	/**
     * This method finds rosters that the currently logged in user is authorized to view.
     * @param request
     * @return List<Roster>
     */
    @RequestMapping(value = "getRostersForReporting.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Roster> getRostersForReporting(String reportType, Long subjectId,
			Long districtId, Long schoolId) {
    	
    	List<Roster> rosters = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();
				
				List<AssessmentProgram> aps = assessmentProgramService.findByOrganizationId(user.getContractingOrgId());
				List<Long> apIds = new ArrayList<Long>(aps.size());
				for (int x = 0; x < aps.size(); x++) {
					apIds.add(aps.get(x).getId());
				}
				
				//This will not have full impact un till more than one pool is defined.
		        Restriction restriction = resourceRestrictionService.getResourceRestriction(
		                userDetails.getUser().getOrganization().getId(), permissionUtil.getAuthorityId(userDetails.getUser()
		                        .getAuthoritiesList(), RestrictedResourceConfiguration.getSearchRosterPermissionCode()), permissionUtil
		                        .getAuthorityId(userDetails.getUser().getAuthoritiesList(),
		                         RestrictedResourceConfiguration.getViewAllRostersPermissionCode()), restrictedResourceConfiguration
		                        .getRosterResourceCategory().getId());
		        
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("assessmentProgramIds", apIds);
				params.put("contentAreaId", subjectId);
				params.put("userId", userDetails.getUserId());
				params.put("currentSchoolYear", userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
			
				if(restriction!=null)
					params.put("restrictionId", restriction.getId());
				
				
				rosters = rosterService.getRostersForReports(params);
				
			}
			else
				return null;
		}
		return rosters;
    }
    
    @RequestMapping(value = "getRosterForTeacherReports.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Roster> getRostersForTeacherReports(String reportType, Long reportYear) {
    	List<Roster> rosters = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				Long userId = userDetails.getUser().getId();
				Long apId = userDetails.getUser().getCurrentAssessmentProgramId();
				Long groupId = userDetails.getUser().getCurrentGroupsId();
				Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
				rosters = rosterService.getRostersForTeacherReports(userId, reportYear, apId, groupId, userDetails.getUser().getCurrentOrganizationId());	
				
			}
			else
				return null;
		}
		return rosters;
    }
    
    @RequestMapping(value = "getDLMStudentsForRosterReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Student> getDLMStudentsForRosterReports(String reportType, Long districtId,
			Long schoolId, Long subjectId, Long rosterId, Long page, Long perPage) {
		LOGGER.trace("Entering the getDLMStudentsForRosterReports method.");
		List<Student> students = new ArrayList<Student>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				
				Roster roster = rosterService.selectByPrimaryKey(rosterId);
				long orgId = roster.getAttendanceSchoolId();
				
				UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Long userOrgId = userDetails.getUser().getOrganization().getId();
				
				List<AssessmentProgram> aps = assessmentProgramService.findByOrganizationId(userOrgId);
				List<Long> assessmentProgramIds = new ArrayList<Long>(aps.size());
				for (int x = 0; x < aps.size(); x++) {
					assessmentProgramIds.add(aps.get(x).getId());
				}
				for(Long assessmentProgramId : assessmentProgramIds) 
        		{
        			AssessmentProgram assessmentProgram = assessmentProgramService
 						.findByAssessmentProgramId(assessmentProgramId);
        			if (assessmentProgram.getProgramName().equalsIgnoreCase(USER_ORG_DLM)) {
				
						List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
						Long authorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewAllRostersPermissionCode());
						Long viewRostersAuthorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewRostersPermissionCode());
						if((childrenOrgIds.contains(orgId) || (orgId == userOrgId)) && (authorityId != null || viewRostersAuthorityId != null)){
							students = enrollmentService.getStudentsByRosterId(rosterId);			
						}else{
							return null;
						}
        			}
        		}
			}
			else
				return null;
		}
		LOGGER.trace("Leaving the getDLMStudentsForRosterReports method.");
		return students;
	}
    
    
    @RequestMapping(value = "getDLMStudentsForStudentReports.htm", method = RequestMethod.GET)
    public final @ResponseBody  List<StudentRoster> getDLMStudentsForStudentReports(String reportType, Long districtId, Long schoolId, Long subjectId){
    	LOGGER.trace("Entering the getDLMStudentsForStudentReports for getting results");
    	
        
        Map<String,String> studentRosterCriteriaMap = new HashMap<String, String>();
        studentRosterCriteriaMap.put("dlmOnly", "yes");
        
        String sortByColumn =   "legallastname,legalfirstname";
        String sortType = "asc";
        
        UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userOrgId = userDetails.getUser().getOrganization().getId();
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        boolean hasViewAllPermission = permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getViewAllRostersPermissionCode());
        
		List<AssessmentProgram> aps = assessmentProgramService.findByOrganizationId(userOrgId);
		List<Long> assessmentProgramIds = new ArrayList<Long>(aps.size());
		for (int x = 0; x < aps.size(); x++) {
			assessmentProgramIds.add(aps.get(x).getId());
		}
		List<StudentRoster> studentRosters = new ArrayList<StudentRoster>();
		for(Long assessmentProgramId : assessmentProgramIds) 
		{
			AssessmentProgram assessmentProgram = assessmentProgramService
					.findByAssessmentProgramId(assessmentProgramId);
			if (assessmentProgram.getProgramName().equalsIgnoreCase(USER_ORG_DLM)) {
		
        
				studentRosters = enrollmentService.getEnrollmentsForDLMStudents(
					userDetails, hasViewAllPermission,
					sortByColumn, sortType, null,null, studentRosterCriteriaMap, null, schoolId, subjectId, currentSchoolYear); 
			}
		}
         LOGGER.trace("Leaving the getDLMStudentsForStudentReports page.");         
         return studentRosters;
    }   
	
    
    @RequestMapping(value = "getDLMStudentReport.htm")
	@ResponseBody
	public final Map<String, Object> getDLMStudentReport(
			@RequestParam(value="studentId", required = true) Long studentId,
			@RequestParam(value="districtName", required = true) String districtName,
			@RequestParam(value="districtId", required = true) String districtId,
			@RequestParam(value="studentName", required = true) String studentName,
			@RequestParam(value="schoolId", required = true) Long schoolId,
			@RequestParam(value="schoolName", required = true) String schoolName,
			@RequestParam(value="gradeName", required = true) String gradeName,
			@RequestParam(value="subjectName", required = true) String subjectName,
			@RequestParam(value="subjectId", required = true) Long subjectId,
			@RequestParam(value="testCycleID", required = false) Long testCycleID,
			final HttpServletResponse response, final HttpServletRequest request) throws Exception {
		LOGGER.trace("Entering the getDLMStudentReport() method");
		Map<String, Object> parentReportMap = new HashMap<String, Object>();
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean studentPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"VIEW_ALTERNATE_STUDENT_REPORT");
        
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				
				DLMStudentReport studentReport = new DLMStudentReport();
				List<Long> allChilAndSelfOrgIds = userDetails.getUser().getAllChilAndSelfOrgIds();
	        	if(studentPermission && schoolId!=null && allChilAndSelfOrgIds.contains(schoolId)){
	      	 			  	
    		    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
				String studentName_StateStudID[] = studentName.split("\\(ID:");
				studentReport.setStudentFirstName(studentName_StateStudID[0]);
				studentReport.setContentAreaName(subjectName);
				studentReport.setDistrictName(districtName);
				studentReport.setReportDate(dateFormat.format(new Date()));
				studentReport.setSchoolName(schoolName);
				studentReport.setStateName(userDetails.getUser().getContractingOrganization().getOrganizationName());
				studentReport.setGradeName(gradeName);
				studentReport.setStateStudentIdentifier(studentName_StateStudID[1].substring(0, studentName_StateStudID[1].lastIndexOf(")")));
				studentReport.setStudentId(studentId);
				studentReport.setSchoolId(schoolId);
				studentReport.setSubjectId(subjectId);
				
				studentReport.setClaimsConceptualData(dlmStudentGenerator.generateStudentReportData(studentId, subjectId,testCycleID));
				studentReport.setDlmLogo(dlmStudentGenerator.getDLMStudentReportIconPath("dlmlogo"));
				studentReport.setTargetLogo(dlmStudentGenerator.getDLMStudentReportIconPath("targeticon"));
				
	        }	
		    /**
		     * Pass final data to UI.
  		     */
			parentReportMap.put("studentReportData", studentReport);
			parentReportMap.put("testCycleID", testCycleID);
				
			LOGGER.trace("Leaving the getDLMStudentReport() method.");
		}
		return parentReportMap;
	}
    
    @RequestMapping(value = "getDLMStudentReportPDF.htm")
	public final void getDLMStudentReportPDF(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier,
			@RequestParam("studentId") String studentId,
			@RequestParam("studentName") String studentName,
			@RequestParam("schoolName") String schoolName,
			@RequestParam("districtName") String districtName,
			@RequestParam("stateName") String stateName,
			@RequestParam("gradeName") String gradeName,
			@RequestParam("subjectId") String subjectId,
			@RequestParam("subjectName") String subjectName,
			@RequestParam("schoolId") Long schoolId,
			@RequestParam(value="testCycleID", required=false) Long testCycleID,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean studentPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"VIEW_ALTERNATE_STUDENT_REPORT");
        if(schoolId != null){
	     List<Long> allChilAndSelfOrgIds = userDetails.getUser().getAllChilAndSelfOrgIds();
       	 if(studentPermission && allChilAndSelfOrgIds.contains(schoolId)){
       		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
     		DLMStudentReport studentReport = new DLMStudentReport();
     		String[] studentNameString = studentName.split(" ");
     		studentReport.setStudentFirstName(studentNameString[0]);
     		studentReport.setStudentLastName(studentNameString[1]);
     		studentReport.setContentAreaName(subjectName);
     		studentReport.setDistrictName(districtName);
     		studentReport.setReportDate(dateFormat.format(new Date()));
     		studentReport.setSchoolName(schoolName);
     		studentReport.setStateName(userDetails.getUser().getContractingOrganization().getOrganizationName());
     		studentReport.setGradeName(gradeName);
			
     		studentReport.setStateStudentIdentifier(stateStudentIdentifier);
       		int schoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
       		studentReport.setSchoolYear(schoolYear);
       		studentReport.setClaimsConceptualData(dlmStudentGenerator.generateStudentReportData(Long.parseLong(studentId), Long.parseLong(subjectId),testCycleID));
     		studentReport.setDlmLogo(dlmStudentGenerator.getDLMStudentReportIconPath("dlmlogo"));
			studentReport.setTargetLogo(dlmStudentGenerator.getDLMStudentReportIconPath("targeticon"));
			
     		OutputStream out = response.getOutputStream();
     		response.setContentType("application/pdf");
     		String fileName = "DLMStudentReport_"+schoolName+"_"+ subjectName +"_"+studentName + ".pdf";
	 		fileName = fileName.replaceAll(" ", "");
			
     		response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
     		try {
         	    dlmStudentGenerator.setupStudentReportPDFGeneration(studentReport, out);
     		} catch (Exception e) {
     			LOGGER.error("Caught {} in getStudentReportPDF() method.", e);
     		}finally {
     			if (out != null) {
     				out.close();
     			}
     		}
         }
        }
	}
    
    @RequestMapping(value = "selectOrgGradeCourses.htm", method = RequestMethod.POST)
	public final @ResponseBody List<GradeCourse> selectAllGradeCourses(@RequestParam("schoolId")Long schoolId) throws Exception {
		return gradeCourseService.selectOrgGradeCourses(schoolId);
	}
    
    @RequestMapping(value = "getSubjectsByGrade.htm", method = RequestMethod.POST)
    public final @ResponseBody List<ContentArea> getSubjectsByGrade(@RequestParam("gradeId")Long gradeId) throws Exception {
    	return contentAreaService.getSubjectsByGrade(gradeId);
    }
    
    @RequestMapping(value = "getRostersBySubject.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Roster> getRostersBySubject(@RequestParam("subjectId")Long subjectId,
    	@RequestParam("schoolId")Long schoolId) throws Exception {
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Long rosterCurrentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		User user = userDetails.getUser();
		boolean isTeacher = user.isTeacher() || user.isProctor();
		
		return rosterService.getRostersBySubject(subjectId,schoolId, rosterCurrentSchoolYear, isTeacher, user.getId());
    }
    
	@RequestMapping(value = "getAlternateMonitoringSummary.htm", method = RequestMethod.GET)
	public final @ResponseBody HashMap<String, Object> getAlternateMonitoringSummary(@RequestParam("summaryLevel") String summaryLevel,
			@RequestParam(value = "districtId", required = false) Long[] districtIds, @RequestParam(value = "schoolId", required = false) Long[] schoolIds) {
		
		HashMap<String, Object> resultMap = new HashMap<>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Long> districtIdsList = districtIds == null ? null : Arrays.asList(districtIds);
			List<Long> schoolIdsList = schoolIds == null ? null : Arrays.asList(schoolIds);
			
			resultMap = dataReportService.getDlmTestAdminMonitoringSummary(summaryLevel, userDetails.getUser().getContractingOrganization(),
					districtIdsList, schoolIdsList, userDetails.getUser());
		}
		
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> prepareDataDistrictAndSchoolPDF(HashMap<String, Object> theResultMap){
		
		Boolean addToList = false;
		Long districtID =(long)-1;
		DLMMonitoringSummaryDistrictSchoolData dataDLMMonitoringSummaryRecord = null;
		List<DLMTestAdminMonitoringSummaryDTO> dlmTestResordsList = (List<DLMTestAdminMonitoringSummaryDTO>)theResultMap.get("resultData");
		
		List<DLMMonitoringSummaryDistrictSchoolData> processedList = new ArrayList<DLMMonitoringSummaryDistrictSchoolData>();
		List<DLMTestAdminMonitoringSummaryDTO> listOfRecordForDistrict = null;
		
		
		for(DLMTestAdminMonitoringSummaryDTO curRecord : dlmTestResordsList) {
			if(districtID.equals((long)-1) || !districtID.equals(curRecord.getDistrictId())) {
				//add the list to the data record
				if(addToList == true) {
					dataDLMMonitoringSummaryRecord.setTestAdminSummaryDtos(listOfRecordForDistrict);
					processedList.add(dataDLMMonitoringSummaryRecord);
					addToList = false;
				}
				
				//Create new list and data record
				listOfRecordForDistrict = new ArrayList<DLMTestAdminMonitoringSummaryDTO>();
				dataDLMMonitoringSummaryRecord= new DLMMonitoringSummaryDistrictSchoolData(curRecord.getDistrictName());
				addToList = true;
				districtID = curRecord.getDistrictId();
				if( StringUtils.isEmpty(curRecord.getSubject())) {
					dataDLMMonitoringSummaryRecord.setNoRecordFound(true);
				}else {
					dataDLMMonitoringSummaryRecord.setNoRecordFound(false);
				}
			}
			listOfRecordForDistrict.add(curRecord);
			
		}
		//if there is anything pending to add
		if(addToList == true) {
			dataDLMMonitoringSummaryRecord.setTestAdminSummaryDtos(listOfRecordForDistrict);
			processedList.add(dataDLMMonitoringSummaryRecord);
		}
		theResultMap.put("resultData", processedList);
		return theResultMap;
	}
	
	@RequestMapping(value = "getAlternateMonitoringSummaryPDF.htm", method = RequestMethod.GET)
	public final void getAlternateMonitoringSummaryPDF(HttpServletResponse response, @RequestParam("summaryLevel") String summaryLevel,
			@RequestParam(value = "districtId", required = false) Long[] districtIds,
			@RequestParam(value = "schoolId", required = false) Long[] schoolIds) throws Exception {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Long> districtIdsList = districtIds == null ? null : Arrays.asList(districtIds);
		List<Long> schoolIdsList = schoolIds == null ? null : Arrays.asList(schoolIds);
		
		boolean permission = permissionUtil.hasPermission(userDetails.getAuthorities(), "VIEW_ALT_MONITORING_SUMMARY");
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && permission) {
			HashMap<String, Object> resultMap = new HashMap<>();		
			resultMap = dataReportService.getDlmTestAdminMonitoringSummary(summaryLevel, userDetails.getUser().getContractingOrganization(),
					districtIdsList, schoolIdsList, userDetails.getUser());
			if (!"state".equals(summaryLevel)) {
				//only needed in case of district or school summary
				prepareDataDistrictAndSchoolPDF(resultMap);
			}
			Organization state = userDetails.getUser().getContractingOrganization();
			String fileName = "TestAdminMonitorSummary_" + state.getDisplayIdentifier() + "_" + StringUtils.capitalize(summaryLevel) + "Level.pdf";
			
			response.setContentType(MimeConstants.MIME_PDF);
			response.setHeader("Content-Disposition","attachment; filename=\"" + fileName + "\"");
			
			File pdfFile = dlmMonitoringSummaryGenerator.generateMonitoringSummary(summaryLevel, resultMap);
			
			if (pdfFile != null) {
				try {
					IOUtils.copy(new FileInputStream(pdfFile), response.getOutputStream());
					response.flushBuffer();
					FileUtils.deleteQuietly(pdfFile);
				} catch (IOException ioe) {
					LOGGER.error("Error writing PDF file to output stream:", ioe);
				}
			}
		}
	}
	
	@RequestMapping(value = "getSubjectsForStudentScoreExtract.htm", method = RequestMethod.POST)
    public final @ResponseBody List<ContentArea> getSubjectsForStudentScoreExtract(@RequestParam(value="orgId[]" ,required = false) Long orgId[], @RequestParam("orgType")String orgType,@RequestParam(value="extractTypeId",required = false) Long extractTypeId) throws Exception {		
    	List<ContentArea> subjects = null;
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();	
        Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
		  Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), assessmentProgramId);
			boolean isDistict =false;
			boolean isSchool =false;
		  if(reportYear == null)
				reportYear = (long) user.getContractingOrganization().getReportYear();
			if(orgType.equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
				isDistict =true;
			}else if(orgType.equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				isDistict =false;
				isSchool =true;
			}	
    	//For Current Enrollment
    	if(extractTypeId == CommonConstants.KAP_EXTRACT_TYPE_ID_38 && assessmentProgramId != null) {		
    		subjects = dataExtractService.getSubjectsForStudentScoreExtract(schoolYear,assessmentProgramId,isDistict,isSchool,Arrays.asList(orgId));        					
		}//For Tested Student
    	else if(extractTypeId ==CommonConstants.KAP_EXTRACT_TYPE_ID_52 && assessmentProgramId != null) {
			subjects = dataExtractService.getSubjectsForTestedStudentScoreExtract(schoolYear,assessmentProgramId,isDistict,isSchool, Arrays.asList(orgId)); 
		}
    	 
		List<ContentArea> subjectAfterExclusion = new ArrayList<ContentArea>();
		if(subjects != null && subjects.size() > 0){
			for(ContentArea ca : subjects){
				subjectAfterExclusion.add(ca);
			}
		}		
		return subjectAfterExclusion;
    }
	
	@RequestMapping(value = "getSubjectsForITIBPSummaryExtract.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ContentArea> getSubjectsForItiBPSummaryExtract() throws Exception{
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();    	
		User user = userDetails.getUser();
		long schoolYear = (long) user.getContractingOrganization().getCurrentSchoolYear();
		boolean isTeacher = user.isTeacher() || user.isProctor();
		return contentAreaService.getItiBluePrintSubjects(schoolYear, isTeacher, user.getId());
	}
	
	@RequestMapping(value = "getGradesForITIBPSummaryExtract.htm", method = RequestMethod.POST)
	public final @ResponseBody List<GradeCourse> getGradesForItiBpSummaryExtract(@RequestParam(value="contentAreaId")Long contentAreaId, 
			@RequestParam("narrowOrgId")Long narrowOrgId) throws Exception{
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		long schoolYear = (long) user.getContractingOrganization().getCurrentSchoolYear();
		boolean isTeacher = user.isTeacher() || user.isProctor();
		return gradeCourseService.getGradesForItiBPCoverageExtract(contentAreaId, narrowOrgId, schoolYear, isTeacher, user.getId());
	}
	
	@RequestMapping(value = "getSchYrBySubjectForStudentScoreExtract.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Long> getStudentReportSchoolYearsBySubject(@RequestParam(value="contentAreaId[]", required = false) Long [] contentAreaId) throws Exception {    	
    	List<Long> schoolYears = null;
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
        AssessmentProgram assessmentPgm = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
    	if(contentAreaId != null && contentAreaId.length > 0){
    		schoolYears = dataExtractService.getStudentReportSchoolYearsBySubject(Arrays.asList(contentAreaId),CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE);
    	} else if(contentAreaId == null && StringUtils.equals(assessmentPgm.getAbbreviatedname(), CommonConstants.KELPA2_STUDENT_REPORT_ASSESSMENTCODE)) {
    		schoolYears = dataExtractService.getStudentReportSchoolYearsBySubjectForKelpa(CommonConstants.KELPA2_STUDENT_REPORT_ASSESSMENTCODE);
    	}
    	return schoolYears;
    }
	
	@RequestMapping(value = "getGradesForStudentScoreExtract.htm", method = RequestMethod.GET)
	public final @ResponseBody List<GradeCourse> getGradesForStudentScoreExtract(@RequestParam(value="orgId[]" ,required = false) Long orgId[], @RequestParam("orgType")String orgType,@RequestParam(value="extractTypeId",required = false) Long extractTypeId){					
		List<GradeCourse> gradeCourses = null;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        int schoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();	
        Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
		Long organizationId = userDetails.getUser().getContractingOrgId();
		   Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), assessmentProgramId);
			if(reportYear == null)
				reportYear = (long) user.getContractingOrganization().getReportYear();
			boolean isDistict = false;
			boolean isSchool = false;
			if(orgType.equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
				isDistict =true;
			}else if(orgType.equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				isDistict =false;
				isSchool =true;
			}		
			if(extractTypeId == CommonConstants.KAP_EXTRACT_TYPE_ID_38 && orgId != null && orgId.length > 0) {		
				gradeCourses = dataExtractService.getGradesByOrgIdForStudentScoreExtract(isDistict,isSchool, Arrays.asList(orgId), schoolYear,CommonConstants.DATA_EXTRACT_REPORT_DISPLAYIDENTIFIER,assessmentProgramId); 
			}else if(extractTypeId ==CommonConstants.KAP_EXTRACT_TYPE_ID_52 && orgId != null && orgId.length > 0) {
				gradeCourses = dataExtractService.getGradesByOrgIdForStudentScoreTestedExtract(isDistict,isSchool, Arrays.asList(orgId),assessmentProgramId,organizationId,CommonConstants.DATA_EXTRACT_REPORT_DISPLAYIDENTIFIER,reportYear);
			}else if (extractTypeId == DataReportTypeEnum.K_ELPA_CURRENT_STUDENT_SCORES.getId().longValue() && orgId != null && orgId.length > 0) { 
				gradeCourses = dataExtractService.getGradesByOrgIdForKelpaStudentScoreExtract(isDistict,isSchool, Arrays.asList(orgId), schoolYear,CommonConstants.DATA_EXTRACT_REPORT_DISPLAYIDENTIFIER,assessmentProgramId);
			}else {
				gradeCourses = null;
			}
        return gradeCourses;
	}
	
	@RequestMapping(value = "getAlternateBlueprintCoverageTeachers.htm", method = RequestMethod.GET)
	public final @ResponseBody List<User> getAlternateBlueprintCoverageTeachers(@RequestParam("reportType") String reportType, @RequestParam("districtId") Long districtId,
			@RequestParam("schoolId") Long schoolId, @RequestParam("subjectId") Long[] subjectIds, @RequestParam("gradeId") Long[] gradeIds) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean permission = permissionUtil.hasPermission(userDetails.getAuthorities(), "VIEW_ALT_BLUEPRINT_COVERAGE");
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && permission) {
			User user = userDetails.getUser();
			if (user.isTeacher() || user.isProctor()) {
				return Arrays.asList(user);
			} else {
				Long schoolYear = getSchoolYearForReport(userDetails.getUser().getContractingOrganization(), reportType);
				return userService.getTeachersWithRosteredDLMKidsInSchoolSubjectsAndGrades(schoolId, Arrays.asList(subjectIds), Arrays.asList(gradeIds), schoolYear);
			}
		}
		return new ArrayList<User>();
	}
	
	@RequestMapping(value = "getAlternateBlueprintCoverage.htm", method = RequestMethod.POST)
	public final @ResponseBody List<DLMBlueprintCoverageReportDTO> getAlternateBlueprintCoverage(@RequestParam("reportType") String reportType, @RequestParam("districtId") Long districtId,
			@RequestParam("schoolId") Long schoolId, @RequestParam("edIds[]") Long[] educatorIds, @RequestParam("groupByTeacher") Boolean groupByTeacher,
			@RequestParam(value = "subjectId", required = false) Long subjectId, @RequestParam(value = "gradeId", required = false) Long gradeId,
			@RequestParam(value = "subjectId[]", required = false) Long[] subjectIds, @RequestParam(value = "gradeId[]", required = false) Long[] gradeIds,
			@RequestParam(value="testCycleID", required=false) Long testCycleID) {
		
		// reasoning for the duplicate subject/grade parameters is because of some quirk in the javascript/ajax engine.
		// if the value is singular, then it omits the brackets from the parameter name.
		// if the value is an array, then it includes the brackets.
		
		// bad request somehow
		if ((subjectId == null && subjectIds == null) || (gradeId == null && gradeIds == null)) {
			return new ArrayList<DLMBlueprintCoverageReportDTO>();
		}
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean permission = permissionUtil.hasPermission(userDetails.getAuthorities(), "VIEW_ALT_BLUEPRINT_COVERAGE");
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && permission && isValidReportType(reportType)) {
			List<Long> subjectIdsList = new ArrayList<Long>();
			if (subjectId != null) {
				subjectIdsList = Arrays.asList(subjectId);
			} else {
				subjectIdsList = Arrays.asList(subjectIds);
			}
			
			List<Long> gradeIdsList = new ArrayList<Long>();
			if (gradeId != null) {
				gradeIdsList = Arrays.asList(gradeId);
			} else {
				gradeIdsList = Arrays.asList(gradeIds);
			}
			
			User user = userDetails.getUser();
			Long schoolYear = getSchoolYearForReport(userDetails.getUser().getContractingOrganization(), reportType);
			List<Long> teacherIds = Arrays.asList(educatorIds);
			if (user.isTeacher() || user.isProctor()) {
				teacherIds = Arrays.asList(user.getId());
				groupByTeacher = Boolean.TRUE;
			}
			return dataReportService.getBlueprintCoverageReport(schoolId, teacherIds, subjectIdsList, gradeIdsList,	schoolYear, groupByTeacher,testCycleID);
		}
		return new ArrayList<DLMBlueprintCoverageReportDTO>();
	}
	
	@RequestMapping(value = "getAlternateBlueprintCoveragePDF.htm", method = RequestMethod.GET)
	public final void getAlternateBlueprintCoveragePDF(@RequestParam("reportType") String reportType, @RequestParam("districtId") Long districtId,
			@RequestParam("schoolId") Long schoolId, @RequestParam("edIds[]") Long[] educatorIds, @RequestParam("groupByTeacher") Boolean groupByTeacher,
			@RequestParam(value = "subjectId", required = false) Long subjectId, @RequestParam(value = "gradeId", required = false) Long gradeId,
			@RequestParam(value = "subjectId[]", required = false) Long[] subjectIds, @RequestParam(value = "gradeId[]", required = false) Long[] gradeIds,
			@RequestParam(value="testCycleID",required=false) Long testCycleID, HttpServletResponse response) throws Exception {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		boolean permission = permissionUtil.hasPermission(userDetails.getAuthorities(), "VIEW_ALT_BLUEPRINT_COVERAGE");
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated() && permission && isValidReportType(reportType)) {
			List<DLMBlueprintCoverageReportDTO> dtos = getAlternateBlueprintCoverage(reportType, districtId, schoolId, educatorIds, groupByTeacher,
					subjectId, gradeId, subjectIds, gradeIds,testCycleID);
			
			response.setContentType(MimeConstants.MIME_PDF);
			response.setHeader("Content-Disposition","attachment; filename=\"Blueprint Coverage Report.pdf\"");
			
			Organization school = organizationService.get(schoolId);
			File pdfFile = dlmBlueprintGenerator.generateBlueprintCoverage(school, dtos, Boolean.TRUE.equals(groupByTeacher));
			
			if (pdfFile != null) {
				try {
					IOUtils.copy(new FileInputStream(pdfFile), response.getOutputStream());
					response.flushBuffer();
					FileUtils.deleteQuietly(pdfFile);
				} catch (IOException ioe) {
					LOGGER.error("Error writing PDF file to output stream:", ioe);
				}
			}
		}
	}
	
	@RequestMapping(value = "validateStudentIdForKapScoreDE.htm", method = RequestMethod.POST)
	public final @ResponseBody long validateStudentIdForKapScoreDE(final HttpServletRequest request, @RequestParam("extractId") Long extractId,
			@RequestParam("extractTypeId") Short extractTypeId,
			@RequestParam(value="schoolYears[]", required = false) Long [] schoolYears,
			@RequestParam(value="contentAreaIds[]", required = false) Long [] contentAreaIds, @RequestParam(value="gradeIds[]", required = false) Long[] gradeIds) {
		
		int returnResult = 0;
		List<Long> subjects = null;
		List<Long> schYrs = null;
		List<Long> grades = null;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		Organization currUserOrg = dataExtractService.get(user.getCurrentOrganizationId()); 
		String currentUserLevel = null;
		Long orgId = null;
		if(currUserOrg != null){
			orgId = currUserOrg.getId();
			if(currUserOrg.getOrganizationType().getTypeCode().equals("ST")){
				currentUserLevel = "ST";
			}else if(currUserOrg.getOrganizationType().getTypeCode().equals("DT")){
				currentUserLevel = "DT";				
			}else if(currUserOrg.getOrganizationType().getTypeCode().equals("SCH")){
				currentUserLevel = "SCH";
			}			
		}		
		
		String stateStudentIdentifier = request.getParameter("studentStateId");
		if(contentAreaIds != null && contentAreaIds.length >0){
			subjects = Arrays.asList(contentAreaIds);
		}
		
		if(schoolYears != null && schoolYears.length >0){
			schYrs = Arrays.asList(schoolYears);
		}
		if(gradeIds != null && gradeIds.length >0){
			grades = Arrays.asList(gradeIds);
		}
		
		List<StudentReport> kapStudentReports = null;
		if("ST".equalsIgnoreCase(currentUserLevel)){
			kapStudentReports = dataExtractService.selectStudentReportsByStudentIdSubjSchYrGrade(stateStudentIdentifier, subjects, schYrs, grades);
			if(CollectionUtils.isNotEmpty(kapStudentReports) && kapStudentReports.size() >0){
				returnResult = 0;
			}else{
				returnResult = -1;
			}
		}else{
			int totalReports = dataExtractService.getStudentReportCountByStudentIdSubject(stateStudentIdentifier, subjects);
			int currentEnrollmentCount = dataExtractService.getEnrollmentCountBySsidOrgId(stateStudentIdentifier, orgId, user.getContractingOrganization().getCurrentSchoolYear().intValue(), currentUserLevel);
			int reportsInUserOrg = dataExtractService.getStudentReportCountByStudentIdSubjectUserOrg(stateStudentIdentifier, subjects, orgId, currentUserLevel);
			
			if(totalReports > 0){

				if(reportsInUserOrg <= 0){
					if(currUserOrg.getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)&& totalReports>0) {
						returnResult = 0;
					}else {
					returnResult = -1;
					}
					
				}else{
					
					returnResult = 0;
				}
								
			}else{
				returnResult = -1;
			}			
		}		
		
		return returnResult;
	}
	
	@RequestMapping(value = "searchByStudentLastName.htm", method = RequestMethod.GET)
	public final @ResponseBody JQGridJSONModel searchByStudentLastName(@RequestParam("studentLastName") String studentLastName, 
			@RequestParam("rows") String limitCountStr,	@RequestParam("page") String page) {
		JQGridJSONModel model = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Organization currUserOrg = organizationService.get(user.getCurrentOrganizationId()); 
			long reportYear = user.getContractingOrganization().getReportYear();
			Long currentGroupId = user.getCurrentGroupsId();
			Groups groups = groupsService.getGroup(currentGroupId);
			int currentPage = NumericUtil.parse(page, 1);
		    int limitCount = NumericUtil.parse(limitCountStr, 5);
		    int offset = (currentPage-1) * limitCount;
			int totalCount = 0;
	
			List<StudentReportDTO> students =  
						studentReportService.searchByStudentLastName(studentLastName, user.getId(), currUserOrg.getOrganizationType().getTypeCode(), 
								currUserOrg.getId(), limitCount, offset, user.getCurrentGroupsId(), user.getContractingOrganization().getCurrentSchoolYear(), reportYear, groups);
			if (students.size() > 0) {
				totalCount = students.get(0).getTotalRecords();
			}
			if (totalCount > 0){
				model = StudentReportDTOJsonConverter.convertForLastNameSearch(students, totalCount, currentPage, limitCount);
			} else {
				model = StudentReportDTOJsonConverter.createEmptyModelForLastNameSearch();
			}
		}
		return model;
	}
	
	@RequestMapping(value = "searchByStateStudentIdForKAP.htm", method = RequestMethod.GET)
	public final @ResponseBody JQGridJSONModel searchByStateStudentIdForKAP(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier) {
		JQGridJSONModel kapModel = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Organization currUserOrg = organizationService.get(user.getCurrentOrganizationId()); 
			long reportYear = user.getContractingOrganization().getReportYear();
			List<StudentReportDTO> kapReports =  studentReportService.searchByStateStudentIdForKAP(stateStudentIdentifier, user.getId(), currUserOrg.getOrganizationType().getTypeCode(), 
					currUserOrg.getId(), user.getCurrentGroupsId(), user.getContractingOrganization().getCurrentSchoolYear(), reportYear);
			 kapModel = StudentReportDTOJsonConverter.convertForKAPStateIdSearch(kapReports);
		}
		return kapModel;
	}
	
	@RequestMapping(value = "searchByStateStudentIdForDLM.htm", method = RequestMethod.GET)
	public final @ResponseBody JQGridJSONModel searchByStateStudentIdForDLM(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier) {
		JQGridJSONModel dlmModel = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Organization currUserOrg = organizationService.get(user.getCurrentOrganizationId()); 
			long reportYear = user.getContractingOrganization().getReportYear();
			Long currentGroupId = user.getCurrentGroupsId();
			Groups groups = groupsService.getGroup(currentGroupId);
			List<StudentReportDTO> dlmReports =  studentReportService.searchByStateStudentIdForDLM(stateStudentIdentifier, user.getId(), currUserOrg.getOrganizationType().getTypeCode(), 
					currUserOrg.getId(), user.getCurrentGroupsId(), user.getContractingOrganization().getCurrentSchoolYear(), reportYear, groups);
			dlmModel = StudentReportDTOJsonConverter.convertForDLMOrCPASSStateIdSearch(dlmReports);
		}
		return dlmModel;
	}
	
	@RequestMapping(value = "searchByStateStudentIdForCPASS.htm", method = RequestMethod.GET)
	public final @ResponseBody JQGridJSONModel searchByStateStudentIdForCPASS(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier) {
		JQGridJSONModel cpassModel = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Organization currUserOrg = organizationService.get(user.getCurrentOrganizationId()); 
			long reportYear = user.getContractingOrganization().getReportYear();
			Long currentGroupId = user.getCurrentGroupsId();
			Groups groups = groupsService.getGroup(currentGroupId);
			List<StudentReportDTO> cpassReports =  studentReportService.searchByStateStudentIdForCPASS(stateStudentIdentifier, user.getId(), currUserOrg.getOrganizationType().getTypeCode(), 
					currUserOrg.getId(), user.getCurrentGroupsId(), user.getContractingOrganization().getCurrentSchoolYear(), reportYear, groups);
			cpassModel = StudentReportDTOJsonConverter.convertForDLMOrCPASSStateIdSearch(cpassReports);
		}
		return cpassModel;
	}
	
	@RequestMapping(value = "getStudentInfoForAllStudentsReports.htm", method = RequestMethod.GET)
	public final @ResponseBody StudentReportDTO getStudentInfoForAllStudentsReports(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier) {
		StudentReportDTO dto = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Organization currUserOrg = organizationService.get(user.getCurrentOrganizationId());
			dto = studentReportService.getStudentInfoForAllStudentsReports(stateStudentIdentifier, user.getId(), currUserOrg.getOrganizationType().getTypeCode(), 
					currUserOrg.getId(),user.getContractingOrganization().getCurrentSchoolYear(),user.getCurrentGroupsId());
		}
		return dto;
	}
	
	@RequestMapping(value = "countReportsForStudent.htm", method = RequestMethod.GET)
	public final @ResponseBody Integer countReportsForStudent(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier) {
		int count = 0;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Organization currUserOrg = organizationService.get(user.getCurrentOrganizationId()); 
			long reportYear = user.getContractingOrganization().getReportYear();
			Long currentGroupId = user.getCurrentGroupsId();
			Groups groups = groupsService.getGroup(currentGroupId);
			count = studentReportService.countReportsForStudent(stateStudentIdentifier, user.getId(), currUserOrg.getOrganizationType().getTypeCode(), 
					currUserOrg.getId(), user.getCurrentGroupsId(), user.getContractingOrganization().getCurrentSchoolYear(), reportYear, groups);
		}
		return count;
	}
	
	@RequestMapping(value = "doesSSIDExist.htm", method = RequestMethod.GET)
	public final @ResponseBody Boolean doesSSIDExist(@RequestParam("stateStudentIdentifier") String stateStudentIdentifier) {
		Boolean exists = false;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long stateId = user.getContractingOrganization().getId();
			Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
			exists = studentReportService.doesSSIDExist(stateStudentIdentifier, stateId, currentSchoolYear);
		}
		return exists;
	}	
	
	@RequestMapping(value = "getStudentReportFileForAllStudentReports.htm")
	public final void getStudentReportFileForAllStudentReports(final HttpServletRequest request, final HttpServletResponse response, Long id, String ssid)
			throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long orgId = user.getCurrentOrganizationId();
			Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
			Organization currUserOrg = organizationService.get(orgId); 
			String userOrgLevel = currUserOrg.getOrganizationType().getTypeCode();
			StudentReport report = studentReportService.getByPrimaryKeyForAllStudentReports(id, orgId, userOrgLevel, currentSchoolYear, ssid);
			if (report != null) {
				String path = FileUtil.buildFilePath(REPORT_PATH, report.getFilePath());
				this.downloadReport("All Student report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
	}
	
	@RequestMapping(value = "getExternalStudentReportFileForAllStudentReports.htm")
	public final void getExternalStudentReportFileForAllStudentReports(final HttpServletRequest request, final HttpServletResponse response, Long id, String ssid)
			throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long orgId = user.getCurrentOrganizationId();
			Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
			Organization currUserOrg = organizationService.get(orgId); 
			String userOrgLevel = currUserOrg.getOrganizationType().getTypeCode();
			Externalstudentreports report = studentReportService.getExternalReportByPrimaryKeyForAllStudentReports(id, orgId, userOrgLevel, currentSchoolYear, ssid);
			if (report != null) {
				String path = FileUtil.buildFilePath(REPORT_PATH, report.getFilePath());
				this.downloadReport("External All Student report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm"); // they tried to change the id
			}
		}
	}	
	
	 @RequestMapping(value = "getELPAStudnetsScoreFileReports.htm", method = RequestMethod.GET)
		public final @ResponseBody List<SchoolAndDistrictReportDTO> getELPAStudentsScoreReportFile(String reportType, Long districtId,
				Long page, Long perPage,Long assessmentProgram) {
			List<SchoolAndDistrictReportDTO> reports = new ArrayList<SchoolAndDistrictReportDTO>();
			if (SecurityContextHolder.getContext().getAuthentication() != null &&
	    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				if (isValidReportType(reportType)) {
					if (page == null || page < 1) {
						page = 1L;
					}
					if (perPage == null || perPage < 1) {
						perPage = 15L; // default to 15
					}
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("reportType", reportType);
					params.put("districtId", districtId);
					params.put("offset", (page - 1) * perPage);
					params.put("limit", perPage);
					params.put("assessmentProgramId", assessmentProgram);
					reports = studentReportService.getELPAStudnetsScoreFileReport(params);
				}
				else
					return null;
			}
			return reports;
		}
	 
	@RequestMapping(value = "requestForDynamicBundle.htm", method = RequestMethod.POST)
	public final @ResponseBody String requestForDynamicBundle(
				                 @RequestParam(value="organizationId")Long organizationId,
				                 @RequestParam(value="assessmentProgramId")Long assessmentProgramId,
				                 @RequestParam(value="schoolIds[]", required = false) Long [] schoolIds,
				                 @RequestParam(value="subjectIds[]") Long [] subjectIds,
				                 @RequestParam(value="gradeIds[]") Long [] gradeIds,
				                 @RequestParam(value="schoolNames[]", required = false) String [] schoolNames,
				                 @RequestParam(value="subjectNames[]") String [] subjectNames,
				                 @RequestParam(value="gradeNames[]") String [] gradeNames,
				                 @RequestParam(value="sort1") String sort1,
				                 @RequestParam(value="sort2") String sort2,
				                 @RequestParam(value="sort3") String sort3,
				                 @RequestParam(value="byOrganization") boolean byOrganization, 
				                 @RequestParam(value="separateFile") boolean separateFile,
				                 @RequestParam(value="bySchool") boolean bySchool,
				                 @RequestParam(value="separateFileForSchool") boolean separateFileForSchool,
				                 @RequestParam(value="reportCode") String reportCode
				                 ) {		 
		 
		    UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
		    Category inQueueStatus = categoryService.selectByCategoryCodeAndType("IN_QUEUE", "PD_REPORT_STATUS");
		    Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), assessmentProgramId);
		    if(bySchool){//request from district page for each school
		 		//if(separateFileForSchool){
		 			for (Long schoolId : schoolIds) {
						OrganizationBundleReport organizationBundleReport = new OrganizationBundleReport();
						
						organizationBundleReport.setOrganizationId(schoolId);
						organizationBundleReport.setStatus(inQueueStatus.getId());
						organizationBundleReport.setAssessmentProgramId(assessmentProgramId);
						organizationBundleReport.setSchoolYear(reportYear != null?reportYear :new Long(user.getContractingOrganization().getReportYear()));
						organizationBundleReport.setActiveFlag(true);
						organizationBundleReport.setAuditColumnProperties();
						organizationBundleReport.setSeparateFile(separateFileForSchool);
						organizationBundleReport.setSubjects(StringUtils.join(subjectIds, ","));
						organizationBundleReport.setGrades(StringUtils.join(gradeIds, ","));
						organizationBundleReport.setSubjectNames(StringUtils.join(subjectNames, ", "));
						organizationBundleReport.setGradeNames(StringUtils.join(gradeNames, ", "));
						organizationBundleReport.setSort1("school".equals(sort1)?sort2:sort1);//If different schoolFiles then sort1 cannot be school name
						organizationBundleReport.setSort2("0".equals(sort2)?null:sort2);
						organizationBundleReport.setSort3("0".equals(sort3) ?null:sort3);	
						organizationBundleReport.setReportType(reportCode);
						bundleReportService.insert(organizationBundleReport);						
				  }
		 	 //}
		    } 
		 	if(byOrganization){
		 		OrganizationBundleReport organizationBundleReport = new OrganizationBundleReport();
				
				organizationBundleReport.setOrganizationId(organizationId);
				organizationBundleReport.setStatus(inQueueStatus.getId());
				organizationBundleReport.setAssessmentProgramId(assessmentProgramId);
				organizationBundleReport.setSchoolYear(reportYear != null?reportYear :new Long(user.getContractingOrganization().getReportYear()));
				organizationBundleReport.setActiveFlag(true);
				organizationBundleReport.setAuditColumnProperties();
				organizationBundleReport.setSeparateFile(separateFile );
				organizationBundleReport.setSchoolIds(StringUtils.join(schoolIds, ","));
				organizationBundleReport.setSubjects(StringUtils.join(subjectIds, ","));
				organizationBundleReport.setGrades(StringUtils.join(gradeIds, ","));
				organizationBundleReport.setSchoolNames(StringUtils.join(schoolNames, ", "));
				organizationBundleReport.setSubjectNames(StringUtils.join(subjectNames, ", "));
				organizationBundleReport.setGradeNames(StringUtils.join(gradeNames, ", "));
				organizationBundleReport.setSort1(sort1);
				organizationBundleReport.setSort2("0".equals(sort2)?null:sort2);
				organizationBundleReport.setSort3("0".equals(sort3) ?null:sort3);
				organizationBundleReport.setReportType(reportCode);
				bundleReportService.insert(organizationBundleReport);
		 	}		 	
		 return "{\"success\" : \"true\"}";
	 }
	@RequestMapping(value = "getDynmaicBundleRequestForOrganization.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationBundleReport> getDynmaicBundleRequestForOrganization(
			@RequestParam("organizationId") Long organizationId,
			@RequestParam(value="assessmentProgramId")Long assessmentProgramId,
			@RequestParam(value="reportCode")String reportCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear  
			)  {
		 
		  UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			if(reportYear == null)
				reportYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgramId);
		/* List<OrganizationBundleReport> bundleReports = bundleReportService.selectByOrganizationAndAssessment(organizationId, assessmentProgramId, reportYear != null?reportYear :new Long(user.getContractingOrganization().getReportYear()), reportCode);*/
			 List<OrganizationBundleReport> bundleReports = bundleReportService.selectByOrganizationAndAssessment(organizationId, assessmentProgramId, reportYear, reportCode);
		 List<String> sortString = new ArrayList<String>();
		 for (OrganizationBundleReport organizationBundleReport : bundleReports) {
			 if(organizationBundleReport.getSort1() != null && !organizationBundleReport.getSort1().equalsIgnoreCase(organizationBundleReport.getSort2()))sortString.add(organizationBundleReport.getSort1());
			 if(organizationBundleReport.getSort2() != null)sortString.add(organizationBundleReport.getSort2());
			 if(organizationBundleReport.getSort3() != null)sortString.add(organizationBundleReport.getSort3());			
	
			organizationBundleReport.setSortString(StringUtils.join(sortString,", "));
			
			sortString.clear();
		}
		 return bundleReports;
	}
	
	@RequestMapping(value = "getEstimatedFileSizeForSelectedFilters.htm", method = RequestMethod.GET)
	public final @ResponseBody String getEstimatedFileSizeForSelectedFilters(
			@RequestParam("organizationId") Long organizationId,
			@RequestParam(value="assessmentProgramId")Long assessmentProgramId,
			@RequestParam(value="schoolIds[]", required = false) Long [] schoolIds,
            @RequestParam(value="subjectIds[]") Long [] subjectIds,
            @RequestParam(value="gradeIds[]") Long [] gradeIds,
            @RequestParam (value="separateFile")Boolean separateFile,
			@RequestParam(value="reportCode")String reportCode
			)  {
		 
		  UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
			
			Long totalSize = 0l;
			if(!separateFile){
				int noOfReports = 0;
				for (Long subjectId : subjectIds) {				
					Category cat = categoryService.selectByCategoryCodeAndType(subjectId.toString(), "REPORTSIZE");
					if(cat != null){
						noOfReports = batchReportProcessService.getCountOfStudentReports(Arrays.asList(subjectId), (schoolIds != null && schoolIds.length > 0) ? Arrays.asList(schoolIds): Arrays.asList(organizationId), Arrays.asList(gradeIds), assessmentProgramId,
								assessmentProgram.getAbbreviatedname(), user.getContractingOrganization().getCurrentSchoolYear(), null,null,null);
						totalSize += (Long.parseLong(cat.getCategoryName()) * noOfReports);
					}
				}
			}
			Category category= categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			int count = bundleReportService.getRequestByStatus(organizationId, assessmentProgramId, user.getContractingOrganization().getCurrentSchoolYear(), category.getId(), reportCode);
		
		 return "{\"totalSize\" :" +totalSize+ ",\"inProgress\":"+(count > 0 ? "true" : "false")+"}";
	}
	
	@RequestMapping(value = "getEstimatedSummaryFileSizeForSelectedFilters.htm", method = RequestMethod.GET)
	public final @ResponseBody String getEstimatedSummaryFileSizeForSelectedFilters(
			@RequestParam("organizationId") Long organizationId,
			@RequestParam(value="assessmentProgramId")Long assessmentProgramId,
			@RequestParam(value="schoolIds[]", required = false) Long [] schoolIds,
            @RequestParam(value="gradeIds[]") Long [] gradeIds,
            @RequestParam (value="separateFile")Boolean separateFile
			)  {
		 
		  UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
			
			Long totalSize = 0l;
			if(!separateFile){
				int noOfReports = 0;			
					Category cat = categoryService.selectByCategoryCodeAndType("ALT_STD_SUM_PDF_SIZE", "REPORTSIZE");
					if(cat != null){
						noOfReports = batchReportProcessService.getCountOfStudentSummaryReports((schoolIds != null && schoolIds.length > 0) ? Arrays.asList(schoolIds): Arrays.asList(organizationId), Arrays.asList(gradeIds), assessmentProgramId,
								assessmentProgram.getAbbreviatedname(), user.getContractingOrganization().getCurrentSchoolYear(), null, null, null, REPORT_TYPE_STUDENT_SUMMARY);
						totalSize += (Long.parseLong(cat.getCategoryName()) * noOfReports);
					}

			}
			Category category= categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			int count = bundleReportService.getRequestByStatus(organizationId, assessmentProgramId, user.getContractingOrganization().getCurrentSchoolYear(), category.getId(), "STUDENT_SUMMARY_BUNDLED");
		
		 return "{\"totalSize\" :" +totalSize+ ",\"inProgress\":"+(count > 0 ? "true" : "false")+"}";
	}
	
	@RequestMapping(value = "isFilePresent.htm", method = RequestMethod.GET)
	public final @ResponseBody boolean isFilePresent(
			@RequestParam("id") Long id,
			@RequestParam("organizationId") Long organizationId,
			@RequestParam(value="assessmentProgramId")Long assessmentProgramId,
			@RequestParam(value="reportCode")String reportCode
			)  {
		 
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
			
		Category category= categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
		OrganizationReportDetails report = studentReportService.getSchoolAndDistrictReport(id);
		  		
		if(report == null){			
			int inprogressCount = bundleReportService.getRequestByStatus(organizationId, assessmentProgramId, user.getContractingOrganization().getCurrentSchoolYear(), category.getId(), reportCode);
			if(inprogressCount > 0){ 
				return false;
			}else{
				return false;//This logic may change later
			}
		}else{
			return true;
		}
	}
	
	@RequestMapping(value = "getAlternateYearEndDistrictSummary.htm", method = RequestMethod.GET)
	public final @ResponseBody List<SchoolAndDistrictReportDTO> getAlternateYearEndDistrictSummary(String reportType, Long districtId,
			Long page, Long perPage, Long assessmentProgram,String reportCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		SchoolAndDistrictReportDTO reports = new SchoolAndDistrictReportDTO();
		List<SchoolAndDistrictReportDTO> distreport= new ArrayList<SchoolAndDistrictReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		        User user = userDetails.getUser();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("reportTypeCode", reportCode);
				params.put("districtId", districtId);
				params.put("assessmentProgramId", assessmentProgram);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("reportYear", reportYear);
				if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
					reports = studentReportService.getDlmDistrictSummaryReport(params);
				}else{
					reports = dataReportDetailsService.getDlmDistrictSummaryReport(params);
				}
				if(reports != null)
				distreport.add(reports);
			}
			else
				return null;
		}
		
		
		return distreport;
	}
	
	@RequestMapping(value = "getAlternateYearEndStateSummary.htm", method = RequestMethod.GET)
	public final @ResponseBody List<SchoolAndDistrictReportDTO> getAlternateYearEndStateSummary(String reportType, Long stateId,
			Long page, Long perPage, Long assessmentProgram,String reportCode,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		SchoolAndDistrictReportDTO reports = new SchoolAndDistrictReportDTO();
		List<SchoolAndDistrictReportDTO> statreport= new ArrayList<SchoolAndDistrictReportDTO>();	;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			        User user = userDetails.getUser();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("reportType", reportType);
				params.put("reportTypeCode", reportCode);
				params.put("stateId", stateId);
				params.put("assessmentProgramId", assessmentProgram);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("reportYear", reportYear);
				if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
				reports = studentReportService.getDlmStateSummaryReport(params);
				}else{
					reports = dataReportDetailsService.getDlmStateSummaryReport(params);
				}
				if(reports !=null)
				statreport.add(reports); 
			}
			else
				return null;
		}
		return statreport;
	}
	
	@RequestMapping(value = "getAllStudentSummaryBundledReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationReportDetails> getAllStudentSummaryBundledReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,Long assessmentProgram,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		List<OrganizationReportDetails> reports = new ArrayList<OrganizationReportDetails>();
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}

				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				        User user = userDetails.getUser();
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				params.put("districtId", districtId);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("assessmentProgramId", assessmentProgram);
				params.put("reportType", REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
				params.put("reportYear" , reportYear);
				List<Long> schoolIds = new ArrayList<Long>();				
				if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
				if(districtId!=null && schoolId!=null){
					//For all schools in a district
					if(schoolId == -1){
						//Get all the schools in the district for which file paths exist						
						schoolIds = studentReportService.getSchoolIdsInDistrictOfSummaryBundledReports(params);						
						
					}else if(schoolId == 0){  // To get district bundled reports
						schoolIds.add(districtId);
					}else{
						//If a single school is selected
						schoolIds.add(schoolId);
					}
						
				}
				
				//For each school retrieve and save the file path and size
				params.put("schoolIds", schoolIds);
				
				if(schoolIds.size() > 0){
					reports = studentReportService.getAllStudentSummaryBundledReports(params);
				}else{ 
					return null;
				}
				}else{
							/*====*/

					if(districtId!=null && schoolId!=null){						
						if(schoolId == -1){													
							schoolIds = dataReportDetailsService.getSchoolIdsInDistrictOfSummaryBundledReports(params);						
						}else if(schoolId == 0){  
							schoolIds.add(districtId);
						}else{							
							schoolIds.add(schoolId);
						}							
					}				
					params.put("schoolIds", schoolIds);					
					if(schoolIds.size() > 0){
						reports = dataReportDetailsService.getAllStudentSummaryBundledReports(params);
					}else{ 
						return null;
						}
					
				}
			}else{
				return null;
			}
		}
		return reports;
	}
	
	@RequestMapping(value = "getStudentSummaryBundledReport.htm", method = RequestMethod.GET)
	public final void getStudentSummaryBundledReportFile(final HttpServletRequest request, final HttpServletResponse response, 
			Long id,@RequestParam(value="reportyear",required=false) Long reportyear) throws IOException {
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User user = userDetails.getUser();
	        OrganizationReportDetails report = null;
	        if(user.getContractingOrganization().getReportYear() == reportyear ){
	        	report = studentReportService.getSchoolAndDistrictReport(id);	
	        }else{
	        	report = dataReportDetailsService.getSchoolAndDistrictReport(id);
	        }
	        
			if (report != null) {
				String filePath = report.getDetailedReportPath();
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("Student summary bundled report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm");
			}
		}
		
	}
	
	@RequestMapping(value = "getAllSchoolSummaryBundledReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationReportDetails> getAllSchoolSummaryBundledReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,Long assessmentProgram,@RequestParam(value="reportYear",required=false) Long reportYear) {
		List<OrganizationReportDetails> reports = new ArrayList<OrganizationReportDetails>();
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				if(districtId!=null){
					// To get district bundled reports
					params.put("districtId", districtId);
					params.put("offset", (page - 1) * perPage);
					params.put("limit", perPage);
					params.put("assessmentProgramId", assessmentProgram);
					params.put("reportYear", reportYear);
					params.put("reportType", REPORT_TYPE_SCH_SUMMARY_BUNDLED);
					if(reportYear !=null && user.getContractingOrganization().getReportYear() == reportYear ){
						reports = studentReportService.getAllSchoolSummaryBundledReportByDistrictId(params);	
					}else{
						reports = dataReportDetailsService.getAllSchoolSummaryBundledReportByDistrictId(params);	
					}
					
					
				}else{
					return null;
				}
				
			}else{
				return null;
			}
		}
		return reports;
	}
	
	@RequestMapping(value = "getSchoolSummaryBundledReport.htm", method = RequestMethod.GET)
	public final void getSchoolSummaryBundledReportFile(final HttpServletRequest request, final HttpServletResponse response, 
			Long id,@RequestParam(value="reportyear",required=false) Long reportyear) throws IOException {
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			OrganizationReportDetails report = null;
			if(userDetails.getUser().getContractingOrganization().getReportYear() == reportyear ){
				report = studentReportService.getSchoolAndDistrictReport(id);	
			}else{
				report = dataReportDetailsService.getSchoolAndDistrictReport(id);
			}
			if (report != null) {
				String filePath = report.getDetailedReportPath();
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("School summary bundled report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm");
			}
		}
		
	}
	
	@RequestMapping(value = "getExternalStudentSummaryReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ExternalStudentReportDTO> getExternalStudentSummaryReports(String reportType, Long districtId,
			Long schoolId, Long subjectId, Long gradeId, Long page, Long perPage, String assessmentProgram,@RequestParam(value="reportYear",required=false) Long reportyear,String reportCode) {
		
		List<ExternalStudentReportDTO> reports = new ArrayList<ExternalStudentReportDTO>();
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
								
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		        User user = userDetails.getUser();	
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				//params.put("reportType", REPORT_TYPE_STUDENT_SUMMARY);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("gradeId", gradeId);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				params.put("reportyear",reportyear);
				if(reportCode.equals("ALT_STD_SUMMARY"))
				reportCode = REPORT_TYPE_STUDENT_SUMMARY;
				params.put("reportCode", reportCode);				
					if(reportyear == null || user.getContractingOrganization().getReportYear() == reportyear.longValue()){
						reports = studentReportService.getExternalStudentSummaryReports(params);
					}else{
						reports = dataReportDetailsService.getExternalStudentSummaryReports(params);
					}	
				
				
				
				
			}else{
				return null;
			}
				
		}
		
		return reports;
	}
	
	@RequestMapping(value = "countExternalStudentSummaryReports.htm", method = RequestMethod.GET)
	public final @ResponseBody Long countOfStudentSummaryReports(String reportType, Long districtId, Long schoolId, Long subjectId, 
			Long gradeId, String assessmentProgram,@RequestParam(value="reportYear",required=false) Long reportyear,String reportCode) {
		
		Long count = null;
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();	
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {				
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				//params.put("reportType", REPORT_TYPE_STUDENT_SUMMARY);
				params.put("districtId", districtId);
				params.put("schoolId", schoolId);
				params.put("gradeId", gradeId);
				if(reportCode.equals("ALT_STD_SUMMARY"))
				reportCode = REPORT_TYPE_STUDENT_SUMMARY;
				params.put("reportCode", reportCode);
				params.put("reportyear", reportyear);
				
					if(reportyear == null || user.getContractingOrganization().getReportYear() == reportyear.longValue()){
						count = studentReportService.countOfStudentSummaryReports(params);
					}else{
						count = dataReportDetailsService.countOfStudentSummaryReports(params);
					}	
				
				
				
			}else{
				return 0l;
			}
				
		}
		
		return count;
	}
	
	@RequestMapping(value = "getStudentSummaryReportsForTeacherRoster.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ExternalStudentReportDTO> getStudentSummaryReportsForTeacherRoster(String reportType, Long rosterId,
			 Long page, Long perPage, String assessmentProgram) {
		List<ExternalStudentReportDTO> reports = new ArrayList<ExternalStudentReportDTO>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				int reportYear = userDetails.getUser().getContractingOrganization().getReportYear();
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				params.put("rosterId", rosterId);
				params.put("reportType", REPORT_TYPE_STUDENT_SUMMARY);
				params.put("reportYear", reportYear);
				params.put("offset", (page - 1) * perPage);
				params.put("limit", perPage);
				
				reports = studentReportService.getStudentSummaryReportsByRosterId(params);
			}else{
				return null;
			}
				
		}
		return reports;
	}
	
	@RequestMapping(value = "countOfStudentSummaryReportsForTeacherRoster.htm", method = RequestMethod.GET)
	public final @ResponseBody Long countOfStudentSummaryReportsForTeacherRoster(String reportType, Long rosterId,
			String assessmentProgram) {
		Long count = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				List<Long> apIds = new ArrayList<Long>();
				apIds.add(Long.valueOf(assessmentProgram));
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				int reportYear = userDetails.getUser().getContractingOrganization().getReportYear();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("assessmentProgramIds", apIds);
				params.put("reportType", REPORT_TYPE_STUDENT_SUMMARY);
				params.put("reportYear", reportYear);
				params.put("rosterId", rosterId);
				
				count = studentReportService.countOfStudentSummaryReportsByRosterId(params);
			}else{
				return 0l;
			}
				
		}
		return count;
	}
	
	@RequestMapping(value = "getStudentSummaryBundledReportOrg.htm", method= RequestMethod.GET)
    public final @ResponseBody List<Organization> getStudentSummaryBundledReportOrg(Long districtId,Long assessmentProgId, String assessmentProgCode, String reportType){
    	List<Organization> organizations = null;
    	if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {	    		
    		if (isValidReportType(reportType)) {	    			
    			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Organization contractingOrg = userDetails.getUser().getContractingOrganization();	    			
    			Map<String, Object> params = new HashMap<String, Object>();
				params.put("districtId", districtId);
				params.put("assessmentProgId", assessmentProgId);
				params.put("assessmentProgCode", assessmentProgCode);
				params.put("schoolYear", getSchoolYearForReport(contractingOrg, reportType));
				params.put("reportType", REPORT_TYPE_SCHOOL);
				organizations = organizationService.getBundledReportOrg(params);	    			
    		} 	    		
    	}	
    	
    	return organizations;
    }
	
	@RequestMapping(value="getGradesForDynamicStudentSummaryBundledReport.htm", method=RequestMethod.GET)
	 public final @ResponseBody List<GradeCourse> getGradesForDynamicStudentSummaryBundledReport(String reportType, 
				Long districtId, @RequestParam(required=false, value="schoolIds[]") Long[] schoolIds, String assessmentProgIds, String assessmentProgCode){
		 List<GradeCourse> gradeCourse = null;
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
		 if (SecurityContextHolder.getContext().getAuthentication() != null &&
	    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {	
			 if (isValidReportType(reportType)) {
				Long assessmentProgId = Long.valueOf(assessmentProgIds);
				 gradeCourse = gradeCourseService.getGradesForDynamicStudentSummaryBundledReport(districtId, schoolIds, assessmentProgId,
						  assessmentProgCode,user.getContractingOrganization().getReportYear());				 
			 }
			 
		 } 
		 return gradeCourse;
	 }
	
	@RequestMapping(value = "requestForDynamicStudentSummaryBundle.htm", method = RequestMethod.POST)
	public final @ResponseBody String requestForDynamicStudentSummaryBundle(
				                 @RequestParam(value="organizationId")Long organizationId,
				                 @RequestParam(value="assessmentProgramId")Long assessmentProgramId,
				                 @RequestParam(value="schoolIds[]", required = false) Long [] schoolIds,
				                 @RequestParam(value="gradeIds[]") Long [] gradeIds,
				                 @RequestParam(value="schoolNames[]", required = false) String [] schoolNames,
				                 @RequestParam(value="gradeNames[]") String [] gradeNames,
				                 @RequestParam(value="sort1") String sort1,
				                 @RequestParam(value="sort2") String sort2,
				                 @RequestParam(value="sort3") String sort3,
				                 @RequestParam(value="byOrganization") boolean byOrganization, 
				                 @RequestParam(value="separateFile") boolean separateFile,
				                 @RequestParam(value="bySchool") boolean bySchool,
				                 @RequestParam(value="separateFileForSchool") boolean separateFileForSchool
				                 ) {		 
		 
		    UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
		    Category inQueueStatus = categoryService.selectByCategoryCodeAndType("IN_QUEUE", "PD_REPORT_STATUS");
		    
		    Long reportYear = organizationService.getReportYear(user.getContractingOrganization().getId(), assessmentProgramId);
		    
		    if(bySchool){//request from district page for each school
		 		
	 			for (Long schoolId : schoolIds) {
					OrganizationBundleReport organizationBundleReport = new OrganizationBundleReport();
					
					organizationBundleReport.setOrganizationId(schoolId);
					organizationBundleReport.setStatus(inQueueStatus.getId());
					organizationBundleReport.setAssessmentProgramId(assessmentProgramId);
					organizationBundleReport.setSchoolYear(reportYear != null?reportYear :new Long(user.getContractingOrganization().getReportYear()));
					organizationBundleReport.setActiveFlag(true);
					organizationBundleReport.setAuditColumnProperties();
					organizationBundleReport.setSeparateFile(separateFileForSchool);
					organizationBundleReport.setSubjects(null);
					organizationBundleReport.setGrades(StringUtils.join(gradeIds, ","));
					organizationBundleReport.setSubjectNames(null);
					organizationBundleReport.setGradeNames(StringUtils.join(gradeNames, ", "));
					organizationBundleReport.setSort1("school".equals(sort1)?sort2:sort1);//If different schoolFiles then sort1 cannot be school name
					organizationBundleReport.setSort2("0".equals(sort2)?null:sort2);
					organizationBundleReport.setSort3("0".equals(sort3) ?null:sort3);
					organizationBundleReport.setReportType(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
					bundleReportService.insert(organizationBundleReport);						
			  }
		    } 
		 	if(byOrganization){
		 		OrganizationBundleReport organizationBundleReport = new OrganizationBundleReport();
				
				organizationBundleReport.setOrganizationId(organizationId);
				organizationBundleReport.setStatus(inQueueStatus.getId());
				organizationBundleReport.setAssessmentProgramId(assessmentProgramId);
				organizationBundleReport.setSchoolYear(reportYear!=null?reportYear :new Long(user.getContractingOrganization().getReportYear()));
				organizationBundleReport.setActiveFlag(true);
				organizationBundleReport.setAuditColumnProperties();
				organizationBundleReport.setSeparateFile(separateFile );
				organizationBundleReport.setSchoolIds(StringUtils.join(schoolIds, ","));
				organizationBundleReport.setSubjects(null);
				organizationBundleReport.setGrades(StringUtils.join(gradeIds, ","));
				organizationBundleReport.setSchoolNames(StringUtils.join(schoolNames, ", "));
				organizationBundleReport.setSubjectNames(null);
				organizationBundleReport.setGradeNames(StringUtils.join(gradeNames, ", "));
				organizationBundleReport.setSort1(sort1);
				organizationBundleReport.setSort2("0".equals(sort2)?null:sort2);
				organizationBundleReport.setSort3("0".equals(sort3) ?null:sort3);
				organizationBundleReport.setReportType(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
				
				bundleReportService.insert(organizationBundleReport);
		 	}		 	
		 return "{\"success\" : \"true\"}";
	 }
	
	@RequestMapping(value = "getAllSchoolSummaryReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationReportDetails> getAllSchoolSummaryReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,Long assessmentProgram,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		List<OrganizationReportDetails> reports = new ArrayList<OrganizationReportDetails>();
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			        User user = userDetails.getUser();
					
				if(schoolId!=null){
					// To get district bundled reports
					params.put("organizationId", schoolId);
					params.put("offset", (page - 1) * perPage);
					params.put("limit", perPage);
					params.put("assessmentProgramId", assessmentProgram);
					params.put("reportType", REPORT_TYPE_SCHOOL);
					params.put("reportYear",reportYear);
					if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
					reports = studentReportService.getAllSchoolSummaryReports(params);
					}else{
						reports = dataReportDetailsService.getAllSchoolSummaryReports(params);
					}
					
				}else{
					return null;
				}
				
			}else{
				return null;
			}
		}
		return reports;
	}
	
	@RequestMapping(value = "getSchoolSummaryPdfReport.htm", method = RequestMethod.GET)
	public final void getSchoolSummaryPdfReportFile(final HttpServletRequest request, final HttpServletResponse response, Long id, 
			String downloadType, @RequestParam(value="reportyear",required=false) Long reportyear) throws IOException {
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User user = userDetails.getUser();
			OrganizationReportDetails report = null;
			
			if(user.getContractingOrganization().getReportYear() == reportyear ){
				report = studentReportService.getSchoolAndDistrictReport(id);
			}else{
				report = dataReportDetailsService.getSchoolAndDistrictReport(id);
			}
			if (report != null) {
				String filePath = StringUtils.EMPTY;
				if(downloadType.equals("pdf")){
					filePath = report.getDetailedReportPath();
				}else if(downloadType.equals("csv")){
					filePath = report.getCsvDetailedReportPath();
				}
				 
				String path = FileUtil.buildFilePath(REPORT_PATH, filePath);
				this.downloadReport("School summary pdf report not found.", path, request, response);
			} else {
				response.sendRedirect("permissionDenied.htm");
			}
		}
		
	}
	
	@RequestMapping(value = "getAllClassroomReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationReportDetails> getAllClassroomReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,Long assessmentProgram, Long dlmTeacherId,
			@RequestParam(value = "reportYear", required = false) Long reportYear) {
		List<OrganizationReportDetails> reports = new ArrayList<OrganizationReportDetails>();
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}

				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				        User user = userDetails.getUser();
				Map<String, Object> params = new HashMap<String, Object>();
				
				if(schoolId!=null){
					params.put("organizationId", schoolId);
					params.put("offset", (page - 1) * perPage);
					params.put("limit", perPage);
					params.put("assessmentProgramId", assessmentProgram);
					params.put("teacherId", dlmTeacherId);
					params.put("reportType", REPORT_TYPE_CLASSROOM);
					params.put("reportYear" , reportYear);
					if(user.getContractingOrganization().getReportYear() == reportYear.longValue()){
					reports = studentReportService.getAllSchoolSummaryReports(params);
					}else{
						reports = dataReportDetailsService.getAllSchoolSummaryReports(params);
					}
				}else{
					return null;
				}
				
			}else{
				return null;
			}
		}
		return reports;
	}
	
	@RequestMapping(value = "getTeachersForClassroomReports.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationReportDetails> getTeachersForClassroomReports(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,Long assessmentProgram) {
		List<OrganizationReportDetails> reports = new ArrayList<OrganizationReportDetails>();
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				if(schoolId!=null){
					// To get district bundled reports
					params.put("organizationId", schoolId);
					params.put("offset", (page - 1) * perPage);
					params.put("limit", perPage);
					params.put("assessmentProgramId", assessmentProgram);
					params.put("reportType", REPORT_TYPE_CLASSROOM);
					
					reports = studentReportService.getAllTeacherNamesForClassroomReports(params);
					
				}else{
					return null;
				}
				
			}else{
				return null;
			}
		}
		return reports;
	}
	
	@RequestMapping(value = "getTeachersForClassroomReportsByReportYear.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OrganizationReportDetails> getTeachersForClassroomReportsByReportYear(String reportType, Long districtId,
			Long schoolId, Long page, Long perPage,Long assessmentProgram,
			@RequestParam(value = "reportYear", required = false) Long selectYear,
			@RequestParam(value = "selectreportType", required = false) String selectreportType) {
		List<OrganizationReportDetails> reports = new ArrayList<OrganizationReportDetails>();
		
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			if (isValidReportType(reportType)) {
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = userDetails.getUser();
				
				
				if (page == null || page < 1) {
					page = 1L;
				}
				if (perPage == null || perPage < 1) {
					perPage = 15L; // default to 15
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				
				if(schoolId!=null){
					// To get district bundled reports
					params.put("organizationId", schoolId);
					params.put("offset", (page - 1) * perPage);
					params.put("limit", perPage);
					params.put("assessmentProgramId", assessmentProgram);
					params.put("reportType", REPORT_TYPE_CLASSROOM);
					params.put("selectreportType", reportType);
					params.put("reportYear", selectYear);
					if(user.getContractingOrganization().getReportYear() ==  selectYear.longValue()){
					reports = studentReportService.getAllTeacherNamesForClassroomReports(params);
					} 
					else{
						reports = dataReportDetailsService.getAllTeacherNamesForClassroomReports(params);
					}
					
				}else{
					return null;
				}
				
				
			}else{
				return null;
			}
		}
		return reports;
	}
	
	@RequestMapping(value = "downloadStateSpecificFile.htm")
	public final void getStateSpecificFile(@RequestParam("stateSpecificFileId") String stateSpecificFileId,
			final HttpServletResponse response, final HttpServletRequest request) throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				StateSpecificFile stateSpecificFiles = dataReportService.getStateSpecificFileById(Long.valueOf(stateSpecificFileId));
	        	String path= stateSpecificFiles.getFileLocation();
	        	this.downloadReport("State specific file not found.", path, request, response);
		}
	}
    
    @RequestMapping(value = "getSubjectsByGradeAndAssessment.htm", method = RequestMethod.POST)
    public final @ResponseBody List<ContentArea> getSubjectsByGradeAndAssessment(@RequestParam("gradeId") String abbreviatedName, 
    		@RequestParam(value="assessmentProgramIds[]") List<Long> assessmentProgramIds) throws Exception {
    	return contentAreaService.getSubjectsByGradeAndAssessment(abbreviatedName, assessmentProgramIds);
    }
    
    @RequestMapping(value = "reports-customfiles.htm")
   	public final ModelAndView reportCustomFiles() {
   		return getReportCustomFilesModelAndView("reports-ui/stateSpecificFiles");
   	}

    private final ModelAndView getReportCustomFilesModelAndView(String path) {
       	Category maxSize = categoryService.selectByCategoryCodeAndType("STATE_SPECIFIC_FILE_MAX_SIZE",
   				"STATE_SPECIFIC_FILE_RULES");

   		Category stateSpecificFileAllowedExtensions = categoryService
   				.selectByCategoryCodeAndType("STATE_SPECIFIC_FILE_ALLOWED_TYPES", "STATE_SPECIFIC_FILE_RULES");

   		ModelAndView mav = new ModelAndView(path);
   		mav.addObject("stateSpecificFileMaxSize", maxSize.getCategoryName());
   		mav.addObject("stateSpecificFileAllowedExtensions", stateSpecificFileAllowedExtensions.getCategoryName());
   		return mav;
   	}
	
    @RequestMapping(value = "getStateSpecificFileHasPerm.htm", method = RequestMethod.POST)
   	public final @ResponseBody Boolean  stateSpecificFileHasPerm(
   			@RequestParam("currentGroupId") Long currentGroupId,
   			@RequestParam("currentOrganizationId") Long currentOrganizationId,
   			@RequestParam("currentAssessmentProgramId") Long currentAssessmentProgramId, 
   			final HttpServletRequest request,	final HttpServletResponse response) {
   				
   				List<Long> groupAccessCodes = reportService.getDlmExtractAccessId(currentGroupId, currentOrganizationId, currentAssessmentProgramId);
   				boolean hasPermCustomFile = false;
   				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
   				User user = userDetails.getUser();
   				if (permissionUtil.hasPermission(user.getAuthorities(), DataReportTypeEnum.DLM_CUSTOM_FILES.getPermissionCode())) {   				
	   				if(groupAccessCodes.contains((long)DataReportTypeEnum.DLM_CUSTOM_FILES.getId())) {
	   					hasPermCustomFile = true;
	   				}
   				}   				
   				HttpSession session = request.getSession();
   				session.setAttribute("hasPermCustomFile", hasPermCustomFile);   				
   				
   		return hasPermCustomFile;
       }

    @RequestMapping(value = "getReportAccessPermission.htm", method = RequestMethod.POST)
   	public final @ResponseBody Boolean  getReportAccessPermission(
   			@RequestParam("currentGroupId") Long currentGroupId,
   			@RequestParam("currentOrganizationId") Long currentOrganizationId,
   			@RequestParam("currentAssessmentProgramId") Long currentAssessmentProgramId) {
    	
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			String categoryCode = null;
			if(user.getCurrentAssessmentProgramName().equals(CommonConstants.KAP_STUDENT_REPORT_ASSESSMENTCODE)) {
   				categoryCode = CommonConstants.GEN_WRITING_RESPONSE;    				
			} else if (user.getCurrentAssessmentProgramName().equals(CommonConstants.ASSESSMENT_PROGRAM_DLM)) {
				categoryCode = CommonConstants.ALT_MONITORING_SUMMARY;    
			}
			String authorityCode = reportService.getReportAccessPermissionForReports(currentGroupId, currentOrganizationId, currentAssessmentProgramId, categoryCode);
			boolean hasPerm = false;
			if (permissionUtil.hasPermission(user.getAuthorities(), authorityCode)) {   				
   					hasPerm = true;
			}
   		return hasPerm;
       }
    
    @RequestMapping(value = "getTestingWindowCycleForReports.htm", method = RequestMethod.POST)
   	public final @ResponseBody List<TestingCycle>  getTestingWindowCycleForReports() {
    	
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			List<TestingCycle> resultWindow = null;
			if (SecurityContextHolder.getContext().getAuthentication() != null &&
	    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
				User user = userDetails.getUser();
				
				Long currentSchoolYear= user.getContractingOrganization().getCurrentSchoolYear();
				Long stateId=user.getContractingOrganization().getId();
				Long currentAssessmentProgramId=user.getCurrentAssessmentProgramId();
				resultWindow = testingCycleMapper.getTestingCyclesByStateIdSchoolYearAssessmentProgram(currentAssessmentProgramId,currentSchoolYear,stateId);
			}
   		return resultWindow;
       }
    
    private final void downloadReport(final String message, final String path, final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		if (s3.doesObjectExist(path)){
			downloadController.download(request, response, path);
		} else {
			response.sendError(404, message);
			LOGGER.error("Unable to download report: "+message);
		}
    }
}