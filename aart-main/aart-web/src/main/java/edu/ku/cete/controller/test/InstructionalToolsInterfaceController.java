package edu.ku.cete.controller.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.ItiTestSessionResourceInfo;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.MicroMap;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.GradeCourseExample;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentInformationRecord;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.test.EEConceptualAndClaimDetailsDTO;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ComplexityBandDao;
import edu.ku.cete.model.test.ContentFrameworkDetailDao;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.MicroMapService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.tde.webservice.client.LMWebClient;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.ComplexityBandEnum;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.json.ITIJsonConverter;
import edu.ku.cete.util.json.RecordBrowserJsonUtil;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;


/**
 * Controller class for Instructional Tools Interface that handles requests from 
 * different tabs while setting-up a test session. 
 * @author vkulkarni_sta
 *
 */

@Controller
public class InstructionalToolsInterfaceController {
	
	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(SetupTestSessionController.class);
	
	private static final String INSTRUCTIONAL_TOOL_SUPPORT_JSP = "/test/setupInstructionalToolsSupport";
	
    /**
     * organizationService
     */
    @Autowired
	private OrganizationService organizationService;
    
    /**
     * iti TestSession Service
     */
    @Autowired
	private ItiTestSessionService itiTestSessionService;
    /**
     * ContentFrameworkDetailDao
     */
    @Autowired
	private ContentFrameworkDetailDao contentFrameworkDetailDao;
    
    /**
     * enrollmentService
     */
    @Autowired
    private EnrollmentService enrollmentService; 
    
    @Autowired
	private RosterService rosterService;
    
    @Autowired
	private EnrollmentService enrollemntService;
    
    /**
   	 * recordBrowserJsonUtil
   	 */
       @Autowired
       private StudentsTestsStatusConfiguration studentsTestsStatusConfiguration;

       
	/**
     * permissionUtil.
     */
    @Autowired
    private PermissionUtil permissionUtil;  
    
	/**
	 * recordBrowserJsonUtil
	 */
    @Autowired
    private RecordBrowserJsonUtil recordBrowserJsonUtil;
    
    /**
     * User organization for DLM
     */
    @Value("${user.organization.DLM}")
    private String USER_ORG_DLM;
    /**
     * studentService
     */
    @Autowired
    private StudentService studentService;
    
    /**
     * studentsTestsService
     */
    @Autowired
    private StudentsTestsService studentsTestsService;
    
    /**
     * categoryService
     */
    @Autowired
	private CategoryService categoryService;
    
    
    /**
     * testCollectionService
     */
    @Autowired
	private TestCollectionService testCollectionService;
    /**
     * gradeCourseService
     */
    @Autowired
	private GradeCourseService gradeCourseService;
    
    /**
     * lmWebClient
     */
    @Autowired
    private LMWebClient lmWebClient;
    
    /**
     * microMapService
     */
    @Autowired
    private MicroMapService microMapService;
    
    /**
     * TEST_SESSION_STATUS_UNUSED
     */
    @Value("${testsession.status.unused}")
    private String TEST_SESSION_STATUS_UNUSED;
    
    /**
     * TEST_SESSION_STATUS_UNUSED
     */
    @Value("${testsession.status.pending}")
    private String TEST_SESSION_STATUS_PENDING;
    
    /**
     * TEST_SESSION_STATUS_TYPE
     */
    @Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;
    
    /**
     * User organization for DLM
     */
    @Value("${iti.code.recommendedlevel}")
    private String ITI_RECOMMENDED_LEVEL;
    
    /**
     * Source Type
     */
    @Value("${source.type}")
    private String SOURCE_TYPE;
    
    /**
     * ITI Source
     */
    @Value("${source.iti.code}")
    private String SOURCE_CODE_ITI;
    
	@Value("${nfs.url}")
	private String MEDIA_PATH;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private StudentProfileService studentProfileService;
    /**
     * User organization for DLM
     */
    @Value("${iti.code.itistatus}")
    private String ITI_ACCESS;
    
	@Value("${iti.code.sensitivitytags}")
	private String sensitivitytagsCode;
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Autowired
	private AwsS3Service s3;
    /**	
    *
	* @return {@link ModelAndView}
	*
	*/
	@RequestMapping(value = "instructionalToolsSupport.htm")
	public final ModelAndView instructionalToolsSupportView() {
		LOGGER.trace("Entering the instructionalToolsSupportView() method");
		ModelAndView mav = new ModelAndView(INSTRUCTIONAL_TOOL_SUPPORT_JSP);
    	List<Organization> orgChildrenDLM = new ArrayList<Organization>();
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		boolean checkForDLM = organizationService.checkForDLM(
				user.getOrganizationId(), USER_ORG_DLM);
		
		Boolean ITIRecommendedLevel = getITIconfiguration(ITI_RECOMMENDED_LEVEL);
		Boolean ITIStatus = getITIconfiguration(ITI_ACCESS);
		
		if(checkForDLM){
			orgChildrenDLM = organizationService.getAllChildrenDLM(
				user.getOrganizationId(), USER_ORG_DLM);
		}
		boolean qcPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
		LOGGER.debug("hasQCPermission : "+qcPermission);
		mav.addObject("hasQCPermission",qcPermission);
		
		List<Long> orgChildrenIdsDLM = AARTCollectionUtil.getIds(orgChildrenDLM);
		mav.addObject("orgChildrenIdsDLM", orgChildrenIdsDLM);
    	mav.addObject("ITIRecommendedLevel", ITIRecommendedLevel);
		mav.addObject("ITIStatus", ITIStatus);
		LOGGER.trace("Leaving the instructionalToolsSupportView() method");
		return mav;
	}
	  /**	
    *
	* @return {@link ModelAndView}
	*
	*/
	@RequestMapping(value = "viewStudentInstructionalPlans.htm")
	public final ModelAndView viewStudentInstructionalPlans(final @RequestParam Long studentId,
       		final @RequestParam Long rosterId, String sourcePage) {
		LOGGER.trace("Entering the viewStudentInstructionalPlans() method");
		ModelAndView mav = new ModelAndView("viewStudentInstructionalPlans");
		boolean sourcePageFlag=false;
		Student studentDetails = studentService.findById(studentId);
		if(sourcePage.equals("studentRosterPage"))
			sourcePageFlag = true;
     	mav.addObject("sourcePageFlag",sourcePageFlag);
     	mav.addObject("studentUId",studentDetails.getUsername());
    	mav.addObject("studentPw",studentDetails.getPassword());
    	List<ItiTestSessionHistory> itiTestSessionHistoryInfo = itiTestSessionService.getStudentHistory(studentId, rosterId);
    	mav.addObject("historyInfo", itiTestSessionHistoryInfo);
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();    	  
    	int schoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
    	
    	//instruction pdf
    	List<String> instructionPDFs = getInstructionsPdfs(itiTestSessionHistoryInfo, schoolYear);    	
    	mav.addObject("instructionsPDFs", instructionPDFs);      	
    	//resource pdf
    	List<String> resourceLocs = getResourceLocations(itiTestSessionHistoryInfo);    	 
  	    mav.addObject("resourcePDFs", resourceLocs);  	    
		LOGGER.trace("Leaving the viewStudentInstructionalPlans() method");
		return mav;
	}
	
	private List<String> getResourceLocations(
			List<ItiTestSessionHistory> itiTestSessionHistoryInfo) {
		List<String> resourceLocations = new ArrayList<String>();
		String resourceLoc = StringUtils.EMPTY;
		if(itiTestSessionHistoryInfo != null) {
			for(ItiTestSessionHistory ititTestHistory : itiTestSessionHistoryInfo) {
				if(ititTestHistory.getTestSessionId() != null) {
					List<ItiTestSessionResourceInfo> testResourceInfo = itiTestSessionService.getTestResources(ititTestHistory.getTestSessionId());
					if(testResourceInfo != null) {
						for(ItiTestSessionResourceInfo resourceInfo : testResourceInfo){
							if(resourceInfo.getFileType().equalsIgnoreCase("pdf")){
		         	    		resourceLoc = MEDIA_PATH + resourceInfo.getFileLocation().replaceFirst("/", "");
		         	    		break;
		         	    	}
						}				
					}				
				}
				resourceLocations.add(resourceLoc);
			}
		}
		return resourceLocations;
	}
	 
	private List<String> getInstructionsPdfs(
			List<ItiTestSessionHistory> itiTestSessionHistoryInfo, int schoolYear) {
		List<String> instructionPdfs = new ArrayList<String>();
		if(itiTestSessionHistoryInfo != null) {
			for(ItiTestSessionHistory ititTestHistory : itiTestSessionHistoryInfo) {
				String eElement = StringUtils.EMPTY;
				if(ititTestHistory.getEssentialElement() != null) {
					String essentialElement = ititTestHistory.getEssentialElement();
					eElement = essentialElement.substring(0, essentialElement.lastIndexOf(" - "));
				}
				String instructionsPdfFileName = REPORT_PATH + java.io.File.separator +  "itiinstructions"  + java.io.File.separator + schoolYear 
						+ java.io.File.separator + eElement + "_Instructions.pdf";
				if(s3.doesObjectExist(instructionsPdfFileName)){
					instructionPdfs.add(schoolYear + java.io.File.separator + eElement + "_Instructions");
				} else {
					instructionPdfs.add(StringUtils.EMPTY);
				}
			}
			
		}
		return instructionPdfs;
	}
	
	@RequestMapping(value = "cancelItiTestHistoryAndStudentTest.htm", method = RequestMethod.POST)
	public final @ResponseBody int cancelITIHistoryAndStudentTests(
			@RequestParam("itiTestSessionHistoryId") long itiTestSessionHistoryId,
			@RequestParam("testSessionId") long testSessionId,
			@RequestParam("studentId") long studentId) {
		int updateCount = 0;
		updateCount = itiTestSessionService.cancelITIHistoryAndStudentTests(itiTestSessionHistoryId, testSessionId, studentId);
		return updateCount;
	}
	
	/**
     * This method finds students that the currently logged in user is authorized to view.
     * @param request
     * @return List<Roster>
     */
    @RequestMapping(value = "getStudentInformationRecordsForDLM.htm", method = RequestMethod.POST)
    public final @ResponseBody  Map<String, Object> getStudentInformationRecordsForDLM(
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType, 
    		@RequestParam("filters") String filters,
			@RequestParam("schoolOrgId") Long schoolOrgId) {
    	LOGGER.trace("Entering the getRosterStudentsByTeacher page for getting results");
    	Map<String, Object> studentInfo = new HashMap<String, Object>();
    	Long testSessionId = null;   
    	Integer currentPage = -1;
        Integer limitCount = -1;
        Integer totalCount = -1;        
        //String orderByclause = "";
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();                                          		
        currentPage = Integer.parseInt(page);
        limitCount = Integer.parseInt(limitCountStr);
        Map<String,String> studentRosterCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(filters);
        /*if(sortByColumn.equals("stateStudentIdentifier")){
        	orderByclause = "substring(statestudentidentifier, '^[0-9]+')::bigint "+ sortType +", substring(statestudentidentifier, '[^0-9]*$') "+sortType;
        }else{
        	orderByclause = sortByColumn + ParsingConstants.BLANK_SPACE + sortType;
        }*/
        TimerUtil timerUtil = TimerUtil.getInstance();
        studentRosterCriteriaMap.put("dlmOnly", "yes");
        studentRosterCriteriaMap.put("itiHistory", "yes");
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        List<StudentRoster> studentRosters = enrollmentService.getEnrollmentWithRoster(
				userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				sortByColumn, sortType, (currentPage-1)*limitCount,
				limitCount, studentRosterCriteriaMap, testSessionId, schoolOrgId, null, currentSchoolYear);  
        timerUtil.resetAndLog(LOGGER, "Getting student records for test session took ");
        
        totalCount = enrollmentService.countEnrollmentWithRoster(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				studentRosterCriteriaMap, testSessionId, schoolOrgId, null, currentSchoolYear);
        timerUtil.resetAndLog(LOGGER, "Counting student records for test session took ");
        
        
        studentInfo.put("rows", studentRosters);
         studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
         studentInfo.put("page", currentPage);
         studentInfo.put("records", totalCount);
         LOGGER.trace("Leaving the getRosterStudentsByTeacher page.");         
         return studentInfo;
    }
    
    /**
     * This method finds rosters that the currently logged in user is authorized to view.
     * @param request
     * @return List<Roster>
     */
    @RequestMapping(value = "getAutoCompleteStudentInformationRecords.htm", method = RequestMethod.GET)
    public final @ResponseBody Set<String> getAutoCompleteStudentInformationRecords(    		
    		@RequestParam("fileterAttribute") String fileterAttribute,
			@RequestParam("term") String  term) {
    	LOGGER.trace("Entering the getAutoCompleteSelectRosterStudents page for getting results");
    	
        Set<String> autoCompleteValues = new HashSet<String>();
        //TODO move all 3 steps to a service.
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        User user = userDetails.getUser();
		List<Organization> orgChildren = organizationService.getAllChildren(
				user.getOrganizationId(), true);
		List<Long> orgChildrenIds = AARTCollectionUtil.getIds(orgChildren);
        Map<String,String> studentInformationRecordCriteriaMap = recordBrowserJsonUtil.constructAutoCompleteFilterCriteria(fileterAttribute, term);

		List<StudentInformationRecord> studentInformationRecords = studentService.getStudentInformationRecords(
					null, orgChildrenIds,null,
					userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
					RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
					null,null,
					null, studentInformationRecordCriteriaMap, user.getId()); 
  
        
        LOGGER.trace("Leaving the getRosterStudentsByTeacher page.");
        
        for(StudentInformationRecord studentInformationRecord : studentInformationRecords){
 
        	 if(fileterAttribute.equals("stateStudentIdentifier"))
        		autoCompleteValues.add((studentInformationRecord.getStudent().getStateStudentIdentifier())); 
        }
        
        return autoCompleteValues;
    }
	
	@RequestMapping(value = "getClaimConceptualAreasForSelectedEE.htm", method = RequestMethod.GET)
	private @ResponseBody EEConceptualAndClaimDetailsDTO getClaimConceptualAreasForSelectedEE(@RequestParam("contentCodeId") Long contentCodeId) {
		EEConceptualAndClaimDetailsDTO eeConcepAndClaimDetails = contentFrameworkDetailDao.getClaimConceptualAreasForSelectedEE(contentCodeId);
		return eeConcepAndClaimDetails;
	}
    /**
     * @param orgChildrenIds
     * @param limitCountStr
     * @param page
     * @param sortByColumn
     * @param sortType
     * @param filters
     * @param contentCode
     * @return
     */
    @RequestMapping(value = "getLinkageLevels.htm", method = RequestMethod.POST)
    public final @ResponseBody JQGridJSONModel getLinkageLevels(
    		@RequestParam("orgChildrenIds") String[] orgChildrenIds,
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
     		@RequestParam("contentCode") String contentCode,
    		@RequestParam("contentId") Long contentId,
    		@RequestParam("studentId") Long studentId,
    		@RequestParam("rosterId") Long rosterId,
    		@RequestParam("essentialelementid") Long essentialelementid,
    		@RequestParam("gradeCourseCode") String gradeCourseCode,
    		@RequestParam("contentAreaAbbrName") String contentAreaAbbrName,
    		@RequestParam("contentAreaId") Long contentAreaId,
    		@RequestParam("eElementCode") String eElementCode) {
    	    	
		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		List<MicroMap> micromaps = null;
		Long contentFrameWorkDetailId = null;
		Long complexityBandId = null;
		Map<String, MicroMapResponseDTO> linkageLevels = new LinkedHashMap<String, MicroMapResponseDTO>();
		Map<String, Map<String, String>> actualAndMappingLinkageLevels = getActualAndMappedLikageLevelsByContentArea(contentAreaAbbrName);
		List<MicroMapResponseDTO> microMapResponseDtos = null;
		if(essentialelementid == null){
			String[] defaultLinkageLevels;
			if(StringUtils.equalsIgnoreCase("SCI", contentAreaAbbrName)) {
				defaultLinkageLevels = new String[]{"Initial", "Precursor", "Target"};
			}else {
				defaultLinkageLevels =  new String[]{"Initial Precursor", "Distal Precursor", "Proximal Precursor", "Target", "Successor"};
			}
			for(int count=0; count < defaultLinkageLevels.length; count++){
				MicroMapResponseDTO microMapResponseDTO = new MicroMapResponseDTO();
				microMapResponseDTO.setLinkagelabel(defaultLinkageLevels[count]);
				microMapResponseDTO.setMicromapid((long) count);
				microMapResponseDTO.setActualLinkageLevel(defaultLinkageLevels[count]);
				microMapResponseDTO.setLinkagelevelshortdesc(StringUtils.EMPTY);
				microMapResponseDTO.setLinkagelevellongdesc(StringUtils.EMPTY);
				microMapResponseDTO.setTestavailabile(StringUtils.EMPTY);
				linkageLevels.put(microMapResponseDTO.getLinkagelabel(), microMapResponseDTO);
			}
			currentPage = NumericUtil.parse(page, 1);
			limitCount = StringUtils.equalsIgnoreCase("SCI", contentAreaAbbrName)? NumericUtil.parse(limitCountStr,3):NumericUtil.parse(limitCountStr,5);
		} else {
			microMapResponseDtos = lmWebClient.getLinkageLevelInfo(contentCode);
			Student student = studentService.findById(studentId);
			if(StringUtils.equalsIgnoreCase("ELA", contentAreaAbbrName)){
				complexityBandId = student.getFinalElaBandId();
			}else if(StringUtils.equalsIgnoreCase("M", contentAreaAbbrName)){
				complexityBandId = student.getFinalMathBandId();
			} else if(StringUtils.equalsIgnoreCase("SCI", contentAreaAbbrName)){
				complexityBandId = student.getFinalSciBandId();
			}
			ComplexityBandDao recommendedLevelId = new ComplexityBandDao();
			String recommendedLevelCode = StringUtils.EMPTY;
			String recommendedLevel = StringUtils.EMPTY;
			if(complexityBandId != null){
				recommendedLevelId = testCollectionService.getComplexityBandById(complexityBandId);				
				recommendedLevelCode = recommendedLevelId.getBandCode();
				if(ComplexityBandEnum.getByBandCode(recommendedLevelCode, contentAreaAbbrName) != null) {
					recommendedLevel = ComplexityBandEnum.getByBandCode(recommendedLevelCode, contentAreaAbbrName).getLinkageLevel();
				}
			}
		
			if(microMapResponseDtos != null && microMapResponseDtos.size() > 1 && 
					contentId != null) {
				contentFrameWorkDetailId = Long.valueOf(contentId);
				micromaps = microMapService.addOrUpdate(microMapResponseDtos, contentFrameWorkDetailId);
			}
		
			currentPage = NumericUtil.parse(page, 1);
			limitCount = StringUtils.equalsIgnoreCase("SCI", contentAreaAbbrName)? NumericUtil.parse(limitCountStr,3):NumericUtil.parse(limitCountStr,5);
			String testavailability = StringUtils.EMPTY;
			if(microMapResponseDtos != null) {
				if(sortType != null && sortType.equalsIgnoreCase("asc")) {
					for(int order = microMapResponseDtos.size()-1; order >=0; order--) {						
						testavailability = checkTestAvaialbilityForLinkageLevels(gradeCourseCode, studentId, rosterId, essentialelementid, eElementCode,
								contentAreaId, microMapResponseDtos.get(order), contentAreaAbbrName);
						microMapResponseDtos.get(order).setTestavailabile(testavailability);						
						loadLinkageLevels(microMapResponseDtos.get(order), contentAreaAbbrName, recommendedLevel, linkageLevels, actualAndMappingLinkageLevels);
					}
				} else {
					for(MicroMapResponseDTO microMapResponseDto: microMapResponseDtos) {						
						testavailability = checkTestAvaialbilityForLinkageLevels(gradeCourseCode, studentId, rosterId, essentialelementid,
								eElementCode, contentAreaId, microMapResponseDto, contentAreaAbbrName);
						microMapResponseDto.setTestavailabile(testavailability);
						loadLinkageLevels(microMapResponseDto, contentAreaAbbrName, recommendedLevel, linkageLevels, actualAndMappingLinkageLevels);
					}
				}
			}
		}
		jqGridJSONModel = ITIJsonConverter.convertToLinkageLevelJson(
				linkageLevels,linkageLevels.size(), currentPage, limitCount);

    	return jqGridJSONModel;
    }
   
    private void loadLinkageLevels(MicroMapResponseDTO microMapResponseDto, String contentAreaAbbrName, String recommendedLevel, 
    		Map<String, MicroMapResponseDTO> linkageLevels, Map<String, Map<String, String>> actualAndMappingLinkageLevels) {
    	String mappingLinkageLevelLabel = microMapResponseDto.getLinkagelabel();
    	microMapResponseDto.setActualLinkageLevel(microMapResponseDto.getLinkagelabel());
    	if(actualAndMappingLinkageLevels != null && actualAndMappingLinkageLevels.size() > 0 && 
    			StringUtils.isNotEmpty(actualAndMappingLinkageLevels.get(microMapResponseDto.getLinkagelabel()).get("mappinglinkagelevelname"))) {    		
    		mappingLinkageLevelLabel = actualAndMappingLinkageLevels.get(microMapResponseDto.getLinkagelabel()).get("mappinglinkagelevelname");
    	}		
		if(microMapResponseDto.getLinkagelabel().equals(recommendedLevel)){
			microMapResponseDto.setLinkagelabel(mappingLinkageLevelLabel+":recommended");
		} else {
			microMapResponseDto.setLinkagelabel(mappingLinkageLevelLabel);
		}
		linkageLevels.put(mappingLinkageLevelLabel, microMapResponseDto);
    }
    
    private Map<String, Map<String, String>> getActualAndMappedLikageLevelsByContentArea(String contentAreaAbbrName) { 
		return testCollectionService.getActualAndMappedLikageLevelsByContentArea(contentAreaAbbrName);
	}
	private String checkTestAvaialbilityForLinkageLevels(String gradeCourseCode, Long studentId, Long rosterId, Long essentialelementid,
			String eElement, Long contentAreaId, MicroMapResponseDTO microMapResponseDto, String contentAreaAbbrName) {
    	GradeCourseExample example = new GradeCourseExample();
    	example.createCriteria().andAbbreviatedNameEqualTo(gradeCourseCode);
    	Float linkageLevelLowerBound = null;
    	Float linkageLevelUpperBound = null;
    	Long complexityBandId = null;
    	if(ComplexityBandEnum.getByLinkageLevel(microMapResponseDto.getLinkagelabel(), contentAreaAbbrName) != null) {
    		complexityBandId = ComplexityBandEnum.getByLinkageLevel(microMapResponseDto.getLinkagelabel(), contentAreaAbbrName).getBandId();
    	}
    	if(complexityBandId != null) {
    		ComplexityBandDao complexityBandDTO = testCollectionService.getComplexityBandById(complexityBandId);
    		if(complexityBandDTO != null) {
    			linkageLevelLowerBound = (float) complexityBandDTO.getMinRange();
    			linkageLevelUpperBound = (float) complexityBandDTO.getMaxRange();
    		}
    	}    	
    	List<GradeCourse> gradeCourses = gradeCourseService.selectByExample(example);
		List<TestCollection> testCollections = null;
		String existingPlan = itiTestSessionService.checkDuplicateTestsSessionExists(studentId,microMapResponseDto.getLinkagelabel(),rosterId, essentialelementid);
		if(StringUtils.isEmpty(existingPlan)){
			List<String> itemAttributeList = new ArrayList<String>();
			itemAttributeList.add("Braille");
			itemAttributeList.add("Spoken");
			List<StudentProfileItemAttributeDTO> studentProfileAll = studentProfileService.getStudentProfileItemAttribute(studentId, itemAttributeList);
			StudentProfileItemAttributeDTO studentProfileBraille = null;
			StudentProfileItemAttributeDTO studentProfileSpoken = null;
			for(StudentProfileItemAttributeDTO profile : studentProfileAll){
				if(profile.getAttributeName().equals("Braille")){
					studentProfileBraille = profile;
				}
				if(profile.getAttributeName().equals("Spoken")){
					studentProfileSpoken = profile;
				}
			}
 			List<String> eElementList = new ArrayList<String>();
			eElementList.add(eElement);
			if(studentProfileBraille != null && studentProfileBraille.getSelectedValue() != null && studentProfileBraille.getSelectedValue().equalsIgnoreCase("true")){
		    	testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, "braille");
			}
			if(CollectionUtils.isEmpty(testCollections)){
				itemAttributeList.clear();
				itemAttributeList.add("visualImpairment");
				List<StudentProfileItemAttributeDTO> studentProfileVI = studentProfileService.getStudentProfileItemContainer(studentId, itemAttributeList);
				if(CollectionUtils.isNotEmpty(studentProfileVI) && studentProfileVI.get(0).getSelectedValue() != null && studentProfileVI.get(0).getSelectedValue().equalsIgnoreCase("true")){
					testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, "visual_impairment");
				}			
			}
			if(CollectionUtils.isEmpty(testCollections) && studentProfileSpoken != null && studentProfileSpoken.getSelectedValue() != null && studentProfileSpoken.getSelectedValue().equalsIgnoreCase("true")){
				testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, "read_aloud");
			}
			if(CollectionUtils.isEmpty(testCollections)){
				testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, null);			
			}
	    	if (CollectionUtils.isNotEmpty(testCollections)){
	    		return "Yes";
	    	}else{
	    		return "No";
	    	}
		} else {
			return "No";
		}
    }
    
    /**
     * @param categoryCode
     * @param enableFlag
     * @return
     */
    @RequestMapping(value = "saveITIconfiguration.htm", method = RequestMethod.POST)
    public final @ResponseBody int saveITIconfiguration(
    		@RequestParam("categoryCode") String categoryCode,
    		@RequestParam("enableFlag") String enableFlag
    		) {
    	int check;
    	Boolean status = true;
    	if(enableFlag.equals("ON")){
		check = categoryService.updateByCategoryCode(categoryCode,status);
    	}else{
    		status = false;
		check = categoryService.updateByCategoryCode(categoryCode,status);
    	}
		return check;
    }
    
    /**
     * @param categoryCode
     * @param enableFlag
     * @return
     */
    @RequestMapping(value = "getITIconfiguration.htm", method = RequestMethod.GET)
    public final @ResponseBody Boolean getITIconfiguration(
    		@RequestParam("categoryCode") String categoryCode
    		) {
    	Boolean status = categoryService.getStatusITI(categoryCode);
		return status;
    }
    /**
     * @param categoryCode
     * @param enableFlag
     * @return
     */
    @RequestMapping(value = "getRandomTestCollectionForLinkageLevel.htm", method = RequestMethod.GET)
    public final @ResponseBody Map<String, Object> getRandomTestCollectionForLinkageLevel(
    		@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeCourseCode") String  gradeCourseCode,
    		@RequestParam("eElement") String eElement,
			@RequestParam("mappedLinkageLevel") String  mappedLinkageLevel,
			@RequestParam("actualLinkageLevel") String actualLinkageLevel,
			@RequestParam("studentId") long  studentId,
     		@RequestParam("rosterId") long rosterId,
    		@RequestParam("essentialelementid") long essentialelementid,
    		@RequestParam("contentAreaAbbrName") String contentAreaAbbrName
    		) {
    	Map<String, Object> responseMap = new HashMap<String, Object>();
    	GradeCourseExample example = new GradeCourseExample();
    	example.createCriteria().andAbbreviatedNameEqualTo(gradeCourseCode);
    	Float linkageLevelLowerBound = null;
    	Float linkageLevelUpperBound = null;
    	Long complexityBandId = null;    	    	
    	if(ComplexityBandEnum.getByLinkageLevel(actualLinkageLevel, contentAreaAbbrName) != null) {
    		complexityBandId = ComplexityBandEnum.getByLinkageLevel(actualLinkageLevel, contentAreaAbbrName).getBandId();
    	}
    	if(complexityBandId != null) {
    		ComplexityBandDao complexityBandDTO = testCollectionService.getComplexityBandById(complexityBandId);
    		if(complexityBandDTO != null) {
    			linkageLevelLowerBound = (float) complexityBandDTO.getMinRange();
    			linkageLevelUpperBound = (float) complexityBandDTO.getMaxRange();
    		}
    	}    	
    	List<GradeCourse> gradeCourses = gradeCourseService.selectByExample(example);
		List<TestCollection> testCollections = null;
		String existingPlan = itiTestSessionService.checkDuplicateTestsSessionExists(studentId,mappedLinkageLevel,rosterId, essentialelementid);
		if(StringUtils.isEmpty(existingPlan)){
			List<String> itemAttributeList = new ArrayList<String>();
			itemAttributeList.add("Braille");
			itemAttributeList.add("Spoken");
			List<StudentProfileItemAttributeDTO> studentProfileAll = studentProfileService.getStudentProfileItemAttribute(studentId, itemAttributeList);
			StudentProfileItemAttributeDTO studentProfileBraille = null;
			StudentProfileItemAttributeDTO studentProfileSpoken = null;
			for(StudentProfileItemAttributeDTO profile : studentProfileAll){
				if(profile.getAttributeName().equals("Braille")){
					studentProfileBraille = profile;
				}
				if(profile.getAttributeName().equals("Spoken")){
					studentProfileSpoken = profile;
				}
			}
			List<String> eElementList = new ArrayList<String>();
			eElementList.add(eElement);
			if(studentProfileBraille != null && studentProfileBraille.getSelectedValue() != null && studentProfileBraille.getSelectedValue().equalsIgnoreCase("true")){
		    	testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, "braille");
			}
			if(CollectionUtils.isEmpty(testCollections)){
				itemAttributeList.clear();
				itemAttributeList.add("visualImpairment");
				List<StudentProfileItemAttributeDTO> studentProfileVI = studentProfileService.getStudentProfileItemContainer(studentId, itemAttributeList);
				if(CollectionUtils.isNotEmpty(studentProfileVI) && studentProfileVI.get(0).getSelectedValue() != null && studentProfileVI.get(0).getSelectedValue().equalsIgnoreCase("true")){
					testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, "visual_impairment");
				}			
			}
			if(CollectionUtils.isEmpty(testCollections) && studentProfileSpoken != null && studentProfileSpoken.getSelectedValue() != null && studentProfileSpoken.getSelectedValue().equalsIgnoreCase("true")){
				testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, "read_aloud");
			}
			if(CollectionUtils.isEmpty(testCollections))
				testCollections = testCollectionService.findMatchingTestCollectionsIti(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElementList, null);
			TestCollection testCollection = null;
	    	if (CollectionUtils.isNotEmpty(testCollections)){
	    		testCollection = testCollections.get(0);
	    		ContentArea contentArea = contentAreaService.selectByPrimaryKey(contentAreaId);
	    		if(contentArea.getAbbreviatedName().equalsIgnoreCase("ELA")) {
	        		responseMap.put("sensitivitytags", itiTestSessionService.getSensitivityTags(contentArea.getId(), eElement, gradeCourseCode));
	        	}else{
	        		responseMap.put("sensitivitytags", null);
	        	}

	    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	        	
	        	int schoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
	        	String instructionsPdfFileName = REPORT_PATH + java.io.File.separator +  "itiinstructions"  + java.io.File.separator + schoolYear + java.io.File.separator + eElement + "_Instructions.pdf";
	        	if(s3.doesObjectExist(instructionsPdfFileName)){
	        		responseMap.put("instructionsPDF", schoolYear + java.io.File.separator + eElement + "_Instructions");
	        	}
	    	}else{
	    		Long testCollectionCheck = testCollectionService.itiNoTestCollectionReason(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourses.get(0).getAbbreviatedName(), contentAreaId, studentId, 1, null, eElement, null);
	    		if(testCollectionCheck == null || testCollectionCheck == 0L){
			    	responseMap.put("result", "nocollection");
	    		}else{
			    	responseMap.put("result", "notests");
	    		}
	    	}
	    	responseMap.put("testcollection", testCollection);
		} else {
			responseMap.put("result", "duplicate");
	    	responseMap.put("plan", existingPlan);
		}
    	
		return responseMap;
    }
    
	/**
     * This method would assign tests to students i.e setting up the testsession
     * for students.
     * @param students
     * @param testCollectionId
     * @param testId
     * @param sessionName
     * @param testingProgramId
     * @return
     */
    @RequestMapping(value = "assignDLMStudentsToTest.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> assignDLMStudentsToTest(
    		@RequestParam("studentId") long studentId,
    		@RequestParam("studentEnrlRosterId") long studentEnrlRosterId,
    		@RequestParam("rosterId") long rosterId,
    		@RequestParam("testCollectionName") String testCollectionName,
    		@RequestParam("action") String action,
    		@RequestParam("source") String source,
    		@RequestParam(value = "eElement", required = false) String eElement,
    		@RequestParam(value = "linkageLevel", required = false) String linkageLevel,
    		@RequestParam(value = "levelDesc", required = false) String levelDesc,
    		@RequestParam(value = "sensitivityTags[]", required = false) String[] sensitivityTags,
    		@RequestParam(value = "claim", required = false) String claim,
    		@RequestParam(value = "conceptualArea", required = false) String conceptualArea,
    		@RequestParam(value = "essentialelementid", required = false) long essentialelementid,
    		@RequestParam("testCollectionId") long testCollectionId) {
     	LOGGER.trace("Entering the assignStudentsToTest() method."); 
    	LOGGER.trace("Leaving the assignStudentsToTest() method.");
        return itiTestSessionService.processDLMStudentsToTest(testCollectionName, testCollectionId, 
        		rosterId, studentEnrlRosterId, action, source, linkageLevel, sensitivityTags, 
        		studentId, levelDesc, eElement, claim, conceptualArea, essentialelementid);
    }
    
    /**
     * This method would assign tests to students i.e setting up the testsession
     * for students.
     * @param students
     * @param testCollectionId
     * @param testId
     * @param sessionName
     * @param testingProgramId
     * @return
     */
    @RequestMapping(value = "confirmDLMStudentsToTest.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> confirmDLMStudentsToTest(
    		@RequestParam("itiTestSessionHistoryId") long itiTestSessionHistoryId) {
     	LOGGER.trace("Entering the confirmDLMStudentsToTest() method."); 
     	ItiTestSessionHistory itiTestSessionHistory = itiTestSessionService.selectHistoryTagsByItiSessionHistoryId(itiTestSessionHistoryId);
     	return itiTestSessionService.processDLMStudentsToTest(itiTestSessionHistory.getTestCollectionName(), itiTestSessionHistory.getTestCollectionId(), itiTestSessionHistory.getRosterId(),
     	     	itiTestSessionHistory.getStudentEnrlRosterId(), "confirm", "iti", itiTestSessionHistory.getLinkageLevel(), itiTestSessionHistory.getSensitivityTags().split(","), itiTestSessionHistory.getStudentId(),
     	     	itiTestSessionHistory.getLevelDescription(), itiTestSessionHistory.getEssentialElement(), itiTestSessionHistory.getClaim(), itiTestSessionHistory.getConceptualArea(), itiTestSessionHistory.getEssentialElementId() );
    }
    
    
    /**
     * @param resource location resloc
     * @return pdf file
     */
    @RequestMapping(value = "getItiInstructionsPdf.htm", method = RequestMethod.GET)
    public final void getItiInstructionsPdf(@RequestParam String instructionsloc ,final HttpServletResponse response, final HttpServletRequest request) {
        	String[] fileDetails = instructionsloc.split(java.io.File.separator);
    		String reportPDFName = "itiinstructions" + java.io.File.separator + instructionsloc + ".pdf";
        	String pdfFilePath = REPORT_PATH + java.io.File.separator   + reportPDFName;
			if(s3.doesObjectExist(pdfFilePath)){
				InputStream inputStream = null;
	        	try {
					response.setContentType("application/force-download");
		    		response.addHeader("Content-Disposition", "attachment; filename=" + fileDetails[1] + "_" + fileDetails[0] + ".pdf");
	                inputStream = s3.getObject(pdfFilePath).getObjectContent();
	                IOUtils.copy(inputStream, response.getOutputStream());
	                response.flushBuffer();
				} catch (FileNotFoundException e) {
					LOGGER.error("ITI Instruction PDF file not Found. File - " + reportPDFName);
					LOGGER.error("FileNotFoundException : ",e);
				} catch (IOException e) {
					LOGGER.error("Error occurred while downloading parent report pdf file. File - ." + reportPDFName);
					LOGGER.error("IOException : ",e);
				} finally {
					if(inputStream != null) {
						try {
							inputStream.close();
						} catch (IOException e) {
							LOGGER.error("ignore IOException closing: ",e);
						}
					}
				}
			}else{
				LOGGER.debug("ITI Instruction PDF file not Found. File - " + reportPDFName);
			}
    }
    /**
     * get EE from eligible test collections
     * @param categoryCode
     * @param enableFlag
     * @return
     */
    @RequestMapping(value = "getContentCodeUsedInITIElgibleTestCollections.htm", method = RequestMethod.POST)
    public final @ResponseBody List<ContentFrameworkDetail> getContentCodeUsedInITIElgibleTestCollections(
    		@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeCourseCode") String gradeCourseCode,
			@RequestParam("studentId") Long studentId, 
    		@RequestParam("rosterId") Long rosterId,
    		@RequestParam("contentAreaAbbrName") String contentAreaAbbrName,
    		@RequestParam("statePoolType") String statePoolType) {
    	
    	List<ContentFrameworkDetail> essentialElementList =  testCollectionService.
    			selectContentCodeUsedInITIElgibleTestCollections(gradeCourseCode, contentAreaId, studentId, rosterId, statePoolType, contentAreaAbbrName);
		return essentialElementList;
    }
    
    @RequestMapping(value = "getStudentInformationRecordsForDLMStudentReport.htm", method = RequestMethod.POST)
    public final @ResponseBody  List<StudentRoster> getStudentInformationRecordsForDLMStudentReport(
			@RequestParam("schoolOrgId") Long schoolOrgId, @RequestParam("contentAreaId") Long contentAreaId) {
    	LOGGER.trace("Entering the getStudentInformationRecordsForDLMStudentReport for getting results");
    	Long testSessionId = null;   
        String sortByColumn =   "legallastname,legalfirstname";
        String sortType = "asc";
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();                                          		
        Map<String,String> studentRosterCriteriaMap = recordBrowserJsonUtil.constructRecordBrowserFilterCriteria(null);
        studentRosterCriteriaMap.put("dlmOnly", "yes");
        studentRosterCriteriaMap.put("itiHistory", "yes");
        
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        
        List<StudentRoster> studentRosters = enrollmentService.getEnrollmentWithRoster(
				userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				sortByColumn, sortType, null,null, studentRosterCriteriaMap, testSessionId, schoolOrgId, contentAreaId, currentSchoolYear);  
         LOGGER.trace("Leaving the getRosterStudentsByTeacher page.");         
         return studentRosters;
    }   
    
    /**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get all students per roster id, these will be populated on roster selection.
	 */
    @RequestMapping(value = "getStudentsByRosterId.htm", method = RequestMethod.POST)
    public final @ResponseBody  List<Student> getStudentsByRosterId(
			@RequestParam("rosterId") Long rosterId) {
    	LOGGER.trace("Entering the getStudentsByRosterId for getting results");
    	
    	
        
        Map<String, Object> criteria = new HashMap<String, Object>();
        criteria.put("rosterId", rosterId);
		criteria.put("orderByClause", "legallastname asc, legalfirstname asc");
    	criteria.put("limitCount", null);
		criteria.put("offset", null);
		
		List<Student> students;
		
		Roster roster = rosterService.selectByPrimaryKey(rosterId);
		long orgId = roster.getAttendanceSchoolId();
		UserDetailImpl userDetails  = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		Long authorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewAllRostersPermissionCode());
		Long viewRostersAuthorityId =  permissionUtil.getAuthorityId(userDetails.getUser().getAuthoritiesList(),RestrictedResourceConfiguration.getViewRostersPermissionCode());
		if((childrenOrgIds.contains(orgId) || (orgId == userOrgId)) && (authorityId != null || viewRostersAuthorityId != null)){
			students = enrollemntService.getStudentsByRosterId(rosterId);			
		}else{
			return null;
		}
		
         LOGGER.trace("Leaving the getStudentsByRosterId method.");         
         return students;
    }
    
    @RequestMapping(value = "cancelItiInstructionPlan.htm", method = RequestMethod.POST)
    public final @ResponseBody  boolean cancelItiInstructionPlan(@RequestParam("itiTestSessionHistoryId") long itiTestSessionHistoryId){
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();                                          		
		User user = userDetails.getUser();
		return itiTestSessionService.inactivatePendingITISession(itiTestSessionHistoryId, user.getId()) > 0;
	
	}
}
