package edu.ku.cete.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.OrganizationAnnualResets;
import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.InterimGroupService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationAnnualResetsService;
import edu.ku.cete.service.AsynchronousProcessService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UploadFileRecordService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.student.FirstContactService;
import edu.ku.cete.util.CategoryUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.json.OrganizationJsonConverter;
import edu.ku.cete.domain.common.OrganizationTreeDetail;

/**
 * @author vkulkarni_sta
 * 
 */

@Controller
public class OrganizationController {

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory
			.getLogger(OrganizationController.class);

	private static final String ORGANIZATION_SETUP_JSP = "organizationview";

	/**
	 * organizationService
	 */
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationAnnualResetsService organizationAnnualResetsService;
	
	/**
	 * OrganizationTypeService
	 */
	@Autowired
	private OrganizationTypeService organizationTypeService;	
	
	@Autowired
	private AssessmentProgramService assessProgService;

	/**
	 * userService
	 */
	@Autowired
	private UserService userService;

	/**
	 * categoryUtil
	 */
	@Autowired
	private CategoryUtil categoryUtil;
	/**
	 * organization Record type.
	 */
	private Category organizationRecordType;

	/**
	 * uploadSpecification
	 */
	@Autowired
	private UploadSpecification uploadSpecification;
	
	@Autowired
	private UploadFileRecordService uploadFileRecordService;
	
	@Autowired
	private CategoryService categoryService;

	@Value("${org.upload.invalid.parent}")
	private String invalidParentOrg;

	@Value("${user.organization.DLM}")
    private String USER_ORG_DLM;
	
	@Value("${student.password.category.code}")
	private String STUDENT_PASSWORD_CATEGORY_CODE;
	
	@Value("${student.password.category.type.code}")
	private String STUDENT_PASSWORD_CATEGORY_TYPE_CODE;
	
	@Value("${student.password.qcStates}")
	private String STUDENT_PASSWORD_QC_STATES;
	
	@Autowired
	private FirstContactService firstContactService;
	
	@Autowired
	private AsynchronousProcessService asynchronousProcessService;
	
	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;
    
    @Autowired
	private InterimGroupService interimGroupService;
    
    @Value("${kap.assessmentProgramAbbreviatedName}")
	private String KAP_ASSESSMENTPROGRAM;
    
	/**
	 * 
	 * @return {@link ModelAndView}
	 * 
	 */
    
	@RequestMapping(value = "organizationView.htm")
	public final ModelAndView organizationView() {
		LOGGER.trace("Entering the organizationView() method");
		ModelAndView mav = new ModelAndView(ORGANIZATION_SETUP_JSP);

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		List<OrganizationType> organizationTypes = organizationTypeService
				.getAll();
		List<Organization> organizationsStates = new ArrayList<Organization>();

		Long userId = user.getId();
		Long orgId = user.getOrganization().getId();
		List<Organization> states = null;
		if (user.getOrganization().getOrganizationType().getTypeCode()
				.equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)) {
			states = organizationService.getAllChildrenByOrgTypeCode(orgId,
					CommonConstants.ORGANIZATION_STATE_CODE);
		} else if (user.getOrganization().getOrganizationType().getTypeCode()
				.equals(CommonConstants.ORGANIZATION_STATE_CODE)) {
			// Hack for DE5725 - Org structure for DLM and ARMM are different
			if (user.getOrganization().getDisplayIdentifier().equals("DLM")
					|| user.getOrganization().getDisplayIdentifier()
							.equals("ARMM")) {
				states = organizationService.getAllChildrenByOrgTypeCode(orgId,
						CommonConstants.ORGANIZATION_REGION_CODE);
			} else {
				states = organizationService.getByTypeAndUserId(
						CommonConstants.ORGANIZATION_STATE_CODE, userId);
			}
		}
		// Hack for DE5725 - Org structure for DLM and ARMM are different
		else if (user.getOrganization().getOrganizationType().getTypeCode()
				.equals(CommonConstants.ORGANIZATION_REGION_CODE)) {
			states = organizationService.getByTypeAndUserId(
					CommonConstants.ORGANIZATION_REGION_CODE, userId);
		}

		for (Organization organizationsState : states) {
			if (organizationsState.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
				organizationsStates.add(organizationsState);
			}
		}

		mav.addObject("assessmentPrograms", getAssessmentPrograms());
		mav.addObject("organizationsStates", organizationsStates);
		mav.addObject("organizationTypes", organizationTypes);

		LOGGER.trace("Leaving the organizationView() method");
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
		 * 
		 */
	@RequestMapping(value = "createOrganization.htm", method = RequestMethod.POST)
	public final @ResponseBody
	String createOrganization(
			@RequestParam("orgName") String orgName,
			@RequestParam("orgDisplayId") String orgDisplayId,
			@RequestParam("orgType") String orgType,
			@RequestParam(value = "assessmentProgramIds[]", required = false) Long[] assessmentProgramIds,
			@RequestParam(value = "organizationStructure[]", required = false) String[] organizationStructure,
			@RequestParam("contractingOrgFlag") boolean contractingOrgFlag,
			@RequestParam("buldingUniqueness") String buldingUniqueness,
			@RequestParam("parentOrg") String parentConsortiaOrg,
			@RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate,
			@RequestParam("state") String state,
			@RequestParam("region") String region,
			@RequestParam("area") String area,
			@RequestParam("district") String district,
			@RequestParam("building") String building,
			@RequestParam("expirePasswords") String expirePasswords,
			@RequestParam("expirationDateType")String expirationDateType,
			@RequestParam("testingModel")Long testingModel,			
			@RequestParam("reportYear") Integer reportYear,
			@RequestParam("testBeginTime") String testBeginTime,
			@RequestParam("testEndTime") String testEndTime,
			@RequestParam("testDays") String testDays) {

		Organization organization = new Organization();
		List<OrganizationType> organizationTypes = new ArrayList<OrganizationType>();
		Map<String, Long> orgTypeCodeMap = new HashMap<String, Long>();
		String success = "failed";
		Long parentOrgId = null;
		
		if(!contractingOrgFlag) assessmentProgramIds=null; 		
		if(orgType.equals("RG")) region=null;
		if(orgType.equals("AR")) area=null;
		if(orgType.equals("DT")) district=null;
		if(orgType.equals("BLDG")) building=null;
			
		OrganizationType stateBuildingUniquenessType = null;
		organizationTypes = organizationTypeService.getAll();
		if(null != state && !"".equals(state)) {
			Long stateId = Long.decode(state);
	        //building uniqueness is set by the state
	        Organization stateOrg = organizationService.get(stateId);
	        
	        if (stateOrg.getBuildingUniqueness() != null){
	        	stateBuildingUniquenessType = organizationTypeService.get(stateOrg.getBuildingUniqueness());
	        }
	        
		} else {
			for (OrganizationType organizationType : organizationTypes) {
				orgTypeCodeMap.put(organizationType.getTypeCode(),
						organizationType.getOrganizationTypeId());
			}
		}
		OrganizationType newOrganizationType = organizationTypeService.getByTypeCode(orgType);
        boolean validOrgTypeForState = false;
        for (OrganizationType organizationType : organizationTypes) {
			orgTypeCodeMap.put(organizationType.getTypeCode(), organizationType.getOrganizationTypeId());
        	if (newOrganizationType.getTypeLevel() >= organizationType.getTypeLevel()) {
        		validOrgTypeForState = true;
        	}
        }
        if (!validOrgTypeForState) {
        	return "{\"result\": \"invalidOrgType\"}";
        }
		parentOrgId = getParentOrgId(state, region, area, district,
				building);
    	Boolean matched = false;
    	Organization currentContext = null;
    	if (stateBuildingUniquenessType != null && (orgTypeCodeMap.get(orgType) > stateBuildingUniquenessType.getTypeLevel())){
    		//dupes are not found within this org but may be found out side this org
    		if (null != organizationService.getByDisplayIdWithContext(orgDisplayId, parentOrgId)){
    			matched = true;
    		}
    	}else{
    		UserDetailImpl user = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		if(user.getUser().getCurrentOrganizationId() != 0) {
    			currentContext = organizationService.get(user.getUser().getCurrentOrganizationId());
    		} else {
    			currentContext = userService.getOrganizationsByUserId(
        				user.getUserId()).get(0);
    		}
    		
    		if(currentContext == null) {
    			return  "{\"result\": \"failed\"}";	
    		}

    		//no dupes at all
            ContractingOrganizationTree
            contractingOrganizationTree = organizationService.getTree(currentContext);
            Set<String> organizationDisplayIdentifiers = contractingOrganizationTree.getOrganizationDisplayIdentifiers();
    		for (String displayIdentifier : organizationDisplayIdentifiers){
    			if (displayIdentifier.equals(orgDisplayId.toUpperCase())){
    				matched = true;
    				break;
    			}
    		}
        }
    	if (matched == false){   
    		success = organizationService.createOrganization(organization, orgDisplayId, orgName, buldingUniqueness, startDate, endDate, 
    				parentConsortiaOrg, parentOrgId, orgTypeCodeMap, orgType, contractingOrgFlag, expirePasswords, expirationDateType, 
    				organizationStructure, assessmentProgramIds,testingModel,reportYear,testBeginTime,testEndTime,testDays);
    		
    		if(orgType.equals("SCH")){
	    		if(null != state && !"".equals(state)) {
	    			Long stateId = Long.decode(state);
	    		 organizationService.updateOrganizationMergeRelation(stateId, orgType, orgDisplayId);
	    		
	    		}
    		}
    		
    		if(success.equals("success") && currentContext!=null) {
    			organizationService.clearTreeCache(currentContext);
    		}
		} else {
			success = "duplicate";
		}
    	
    	return "{\"result\": \""+success+"\"}";
	}

	/**
	 * @param state
	 * @param region
	 * @param area
	 * @param district
	 * @param building
	 * @return
	 */
	private Long getParentOrgId(String state, String region, String area,
			String district, String building) {
		Long parentOrgId = null;

		if (!StringUtils.isEmpty(building)) {
			parentOrgId = Long.valueOf(building);
		} else if (!StringUtils.isEmpty(district)) {
			parentOrgId = Long.valueOf(district);
		} else if (!StringUtils.isEmpty(area)) {
			parentOrgId = Long.valueOf(area);
		} else if (!StringUtils.isEmpty(region)) {
			parentOrgId = Long.valueOf(region);
		} else if (!StringUtils.isEmpty(state)) {
			parentOrgId = Long.valueOf(state);
		}

		return parentOrgId;
	}

	/**
	 * 			 
	 * 
	 * */
	@RequestMapping(value = "getChildrenOfOrganizationType.htm", method = RequestMethod.GET)
	public final @ResponseBody
	List<Organization> getChildrenOfOrganizationType(
			@RequestParam("organizationId") Long organizationId,
			@RequestParam("organizationTypeId") Long organizationTypeId) {

		List<Organization> immediateOrganizationChildren = organizationService
				.getImmediateChildren(organizationId);

		return immediateOrganizationChildren;
	}

	/**
	 * @deprecated
	 */
	@RequestMapping(value = "uploadOrganization.htm", method = RequestMethod.POST)
	public @ResponseBody
	String uploadOrganization(MultipartHttpServletRequest request,
			HttpServletResponse response) throws IOException, ServiceException {
		try {
			Long stateId = Long.parseLong(request.getParameter("stateId"));
		//		Long regionId = Long.parseLong(request.getParameter("regionId"));
		//		Long areaId = Long.parseLong(request.getParameter("areaId"));
		//		Long districtId = Long.parseLong(request.getParameter("districtId"));
		//		Long buildingId = Long.parseLong(request.getParameter("buildingId"));
		//		Long schoolId = Long.parseLong(request.getParameter("schoolId"));
		//Long lowestLevelOrgId = Long.parseLong(request.getParameter("organizationId"));
			Iterator<String> itr = request.getFileNames();
			MultipartFile mpf = request.getFile(itr.next());
			CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
		
			File destFile = new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename()));
			LOGGER.debug("Writing File {} to directory: {}", cmpf.getOriginalFilename(), uploadSpecification.getPath());
			cmpf.transferTo(new File(uploadSpecification.getPath().concat(cmpf.getOriginalFilename())));
		
			this.organizationRecordType = categoryUtil.getCSVUploadRecordTypes(
					uploadSpecification, uploadSpecification.getOrgRecordType());

			UploadFile uploadFile = new UploadFile();
			uploadFile.setSelectedRecordTypeId(this.organizationRecordType.getId());
			// uploadFile.setRosterUpload(1);
			uploadFile.setStateId(stateId);
			//		uploadFile.setDistrictId(districtId);
			//		uploadFile.setSchoolId(schoolId);
			//		if (schoolId != null && schoolId != 0)
			//			lowestLevelOrgId = schoolId;
			//		else if (buildingId != null && buildingId != 0)
			//			lowestLevelOrgId = buildingId;
			//		else if (districtId != null && districtId != 0)
			//			lowestLevelOrgId = districtId;
			//		else if (areaId != null && areaId != 0)
			//			lowestLevelOrgId = areaId;
			//		else if (regionId != null && regionId != 0)
			//			lowestLevelOrgId = regionId;
			//		else if (stateId != null && stateId != 0)
			//			lowestLevelOrgId = stateId;

			uploadFile.setFile(destFile);
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			Category inProgressStatus = categoryService.selectByCategoryCodeAndType("IN_PROGRESS", "PD_REPORT_STATUS");
			UploadFileRecord uploadFileRecord = buildUploadFileRecord(
					user, cmpf.getOriginalFilename(), inProgressStatus.getId());
			uploadFileRecordService.insertUploadFile(uploadFileRecord);
			
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("uploadFileRecordId", uploadFileRecord.getId());
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.getSerializationConfig().withSerializationInclusion(
				JsonInclude.Include.NON_NULL); // no more null-valued
													// properties
			String modelJson = mapper.writeValueAsString(model);
			return modelJson;
		} catch(Exception e) {
			LOGGER.error("Exception ooccured while uploading Organization file: " + e.getMessage());			
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
	
	@RequestMapping(value = "monitorUploadOrganizationFile.htm", method = RequestMethod.GET)
	public @ResponseBody String monitorUploadOrganizationFile(HttpServletRequest request, HttpServletResponse response,
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
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 */
	@RequestMapping(value = "getOrgsToView.htm", method = RequestMethod.POST)
	public final @ResponseBody
	JQGridJSONModel getOrgsToView(
			@RequestParam("orgChildrenIds[]") String[] orgChildrenIds,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters) throws JsonProcessingException, IOException {

		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		List<Organization> organizations;
		Integer totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("organizationId", NumericUtil.parse(orgChildrenIds[0], -1));
		populateCriteria(criteria, filters);
		
		organizations = organizationService.getAllChildrenToView(
				criteria, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount);

		totalCount = organizationService.countAllChildrenToView(criteria);

		jqGridJSONModel = OrganizationJsonConverter.convertToOrganizationJson(
				organizations, totalCount, currentPage, limitCount);

		return jqGridJSONModel;
	}

	private void populateCriteria(Map<String, Object> criteria, String filters)
			throws JsonProcessingException, IOException {

		if (null != filters && !filters.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(filters);

			final JsonNode results = rootNode.get("rules");
			
			for (final JsonNode element : results) {
				if(element.get("field").asText().equals("expirationDateType")
						|| element.get("field").asText().equals("schoolStartDate")
						|| element.get("field").asText().equals("schoolEndDate")){
					if(element.get("data").asText().toUpperCase().startsWith("N")){//Not Available
						criteria.put(element.get("field").asText(), "NA");
					}else{
						criteria.put(element.get("field").asText(), element
								.get("data").asText());
					}
				}else{
					criteria.put(element.get("field").asText(), element
							.get("data").asText());
				}
				
			}
		}
	}
	
	
    @RequestMapping(value = "editOrganization.htm")
    public final ModelAndView editOrganization(@RequestParam Long organizationId) {
    	ModelAndView mav = new ModelAndView("editOrganization");
    	String stateName="";
    	String districtName="";
    	String regionName="";
    	List<Organization> orgs = organizationService.getAllParents(organizationId);
    	
    	for (Organization organization : orgs) {
			if(organization.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_5){
				districtName = organization.getOrganizationName();
			}else if(organization.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_3){
				regionName = organization.getOrganizationName();
			}else if(organization.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2){
				stateName = organization.getOrganizationName();
			}			
		}
    	Organization organization = organizationService.get(organizationId);    	
  
		List<OrgAssessmentProgram> orgAssesprogLists = orgAssessProgService.findByContractingOrganizationId(organizationId);
		mav.addObject("orgAssesprogLists", orgAssesprogLists);		
    	mav.addObject("organization", organization);    
    	mav.addObject("stateName", stateName);    
    	mav.addObject("districtName", districtName);
    	mav.addObject("regionName", regionName);
    	Date testBegTimeVar = organizationService.getTestBeginTime(organizationId);
    	Date testEndTimeVar = organizationService.getTestEndTime(organizationId);
    	SimpleDateFormat timeFormater = new SimpleDateFormat("hh:mm a");

    	try {
			if (testBegTimeVar != null) {
				mav.addObject("testBeginTimeStr", timeFormater.format(testBegTimeVar));
			}
			if (testEndTimeVar != null) {
				mav.addObject("testEndTimeStr", timeFormater.format(testEndTimeVar));
			}
		} catch (Exception ex) {
			LOGGER.error("Caught in Manage organization view. Stacktrace: {}", ex);
		}
    	String testDaysVar = organizationService.getTestDays(organizationId);
    	if (testDaysVar != null) {
    		mav.addObject("testDaysStr", testDaysVar);
    	}
    	return mav;
    }
    
    @RequestMapping(value = "viewOrganization.htm")
    public final ModelAndView viewOrganization(@RequestParam Long organizationId) {
    	ModelAndView mav = new ModelAndView("viewOrganization");
    	Organization organization = organizationService.getViewOrganizationDetailsByOrgId(organizationId);
    	mav.addObject("organization", organization);    
    	return mav;
    }
    

	@RequestMapping(value = "editSaveOrganization.htm", method = RequestMethod.POST)
	public final @ResponseBody
	String editSaveOrganization(
			@RequestParam("orgId") Long orgId,
			@RequestParam("orgName") String orgName,
			@RequestParam("orgTypeCode") String orgTypeCode,
			@RequestParam("testBeginTime") String testBeginTime,
			@RequestParam("testEndTime") String testEndTime,
			@RequestParam("testDays") String testDays) {
		
		Organization organization = new Organization();
		organization.setId(orgId);
		orgName=(!orgName.equals("") && orgName!=null)?orgName.trim():orgName;
		organization.setOrganizationName(orgName);
		organization.setAuditColumnPropertiesForUpdate();
		OrganizationType  organizationType = new OrganizationType();
		organizationType.setTypeCode(orgTypeCode);		
		organization.setOrganizationType(organizationType);
    	organization.setTestDays(testDays);
		try {
			if(testBeginTime != null && !StringUtils.isEmpty(testBeginTime)) {
    			Date testBeginVar=new SimpleDateFormat("hh:mm a").parse(testBeginTime);
    			organization.setTestBeginTime(testBeginVar);
    		}
    		else {
    			organization.setTestBeginTime(null);
    		}
    		if(testEndTime != null && !StringUtils.isEmpty(testEndTime)) {
    			Date testEndVar=new SimpleDateFormat("hh:mm a").parse(testEndTime);
    			organization.setTestEndTime(testEndVar);
    		}
    		else {
    			organization.setTestEndTime(null);
    		}
		} catch (ParseException ex) {
			LOGGER.error("Caught in Manage Organization. Stacktrace: {}", ex);
			ex.printStackTrace();
		}
		
		String success = "failed";	
		try {			
			organizationService.updateOrganization(organization);
			if(orgTypeCode.equals("SCH") || orgTypeCode.equals("ST") || orgTypeCode.equals("DT")){
				organizationService.updateOrgnameInOrgTreeDetail(organization);
			}			
    		
			success = "success";
		} catch (Exception e) {
			return  "{\"result\": \"failed\"}";	
		}    	
    	
    	return "{\"result\": \""+success+"\"}";
	}
    
	@RequestMapping(value = "checkResidenceDistrictIdentifier.htm", method = RequestMethod.POST)
	public final @ResponseBody String checkValidDisplayIdentifier(@RequestParam("residenceDistrictId") String residenceDistrictId, 
			@RequestParam("stateOrgId") long stateOrgId) {
		String valid = "false";
		List<Organization> org = organizationService.getDistrictInState(residenceDistrictId, stateOrgId);        
		if (org.size() > 0) {
			valid = "true";
		} 
		return valid;
	}
    
	 @RequestMapping(value = "getStatesBesedOnAssessmentProgram.htm", method = RequestMethod.GET)
	    public final @ResponseBody Map<Long,String> getMultipleStatesOrgsForUser(
	    		@RequestParam(value="assessmentProgramId")long assessmentProgramId) {	    	
	        Map<Long,String> operationTestWindowStates = organizationService.getStatesBasedOnassessmentProgramId(assessmentProgramId); 
	        return operationTestWindowStates;
	    }
	
	@RequestMapping(value = "updateCachedOrganizationDetails.htm", method = RequestMethod.GET)
	public final @ResponseBody String updateCachedOrganizationDetails() {
		organizationService.updateCachedOrganizationDetails();  
		return "Done";
	}
	
	@RequestMapping(value = "resetEhCacheEntries.htm", method = RequestMethod.GET)
	public final @ResponseBody String resetEhCacheEntries() {
		organizationService.resetEhCacheEntries();
		return "Done";
	}
	
	@RequestMapping(value = "resetAnnualSchoolYear.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Boolean> resetAnnualSchoolYear(HttpServletRequest request, HttpServletResponse response) throws JSONException {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		String jsonData = request.getParameter("data");
		JSONArray jsonArray = new JSONArray(jsonData);
		List<Long> organizationIds = new ArrayList<Long>();
		
	    if (jsonArray != null) { 
	    	int arraySize = jsonArray.length();
	 	   	for (int i=0;i<arraySize;i++){ 
	 	   		organizationIds.add(Long.valueOf(jsonArray.get(i).toString()));
	 	   	} 
    	} 

		try {
			OrganizationAnnualResets orgAnnualResetObject = null;
			
			Category category=categoryService.selectByCategoryCodeAndType(STUDENT_PASSWORD_CATEGORY_CODE,STUDENT_PASSWORD_CATEGORY_TYPE_CODE);
					int passwordLength=Integer.valueOf(category.getCategoryName());
					String[] qcStates=STUDENT_PASSWORD_QC_STATES.split(",");
			for(Long orgId : organizationIds){
				orgAnnualResetObject = new OrganizationAnnualResets();
				orgAnnualResetObject.setOrganizationId(orgId);
				orgAnnualResetObject.setModifiedDate(new Date());
		    	orgAnnualResetObject.setModifiedUser(((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
		    	/*
		    	 * Sudhansu : F61 : manage organization
		    	 * Need to capture snapshot of the organization at annual reset
		    	 */
		    	
		    	organizationService.createOrganizationSnapshot(orgId, ((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
				organizationAnnualResetsService.resetAnnualDatesForOrganization(orgAnnualResetObject);
				organizationAnnualResetsService.resetAnnualDatesStatusForOrganization(orgAnnualResetObject);
				asynchronousProcessService.resetStudentPasswordOnAnnualReset(orgId,passwordLength,qcStates);
				
				//If organization has KAP then remove interim groups
				if(organizationService.checkForOrganizationAssessmentProgram(orgId, KAP_ASSESSMENTPROGRAM)){
					interimGroupService.removeInterimGroups();
					interimGroupService.removeInterimGroupStudent();
				}
				
			}			
			map.put("resetComplete", true);
		} catch (Exception e) {
			LOGGER.error("resetComplete" + e.getMessage());		
			map.put("resetComplete errorThrown", false);
		}
		return map;
	}
	
	@RequestMapping(value = "saveOrganizationAnnualResets.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Boolean> saveOrganizationAnnualResets(HttpServletRequest request, HttpServletResponse response) throws JSONException, JsonParseException, JsonMappingException, IOException, ParseException {
	
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		try{
			String jsonData = request.getParameter("data");
			ObjectMapper mapper = new ObjectMapper();
			Long endDateYear = null;
			JSONArray jsonArray = new JSONArray(jsonData);
			String[] orgObjects = jsonArrayToStringArray(jsonArray);
			boolean isDLMState = false;
			FirstContactSettings firstContactSettings;
			Category coreSetQuestions = categoryService.selectByCategoryCodeAndType("CORE_SET_QUESTIONS", "FIRST CONTACT SETTINGS");
			
			for(int i = 0; i < orgObjects.length; i++){
			    OrganizationAnnualResets orgAnnualResetObject = mapper.readValue(orgObjects[i], OrganizationAnnualResets.class);
			    
			    Calendar calendar = new GregorianCalendar();
			    calendar.setTime(orgAnnualResetObject.getSchoolEndDate());
			    int year = calendar.get(Calendar.YEAR);
			    endDateYear = Long.valueOf(year);
			    OrganizationAnnualResets existingObject = organizationAnnualResetsService.checkIfOrgUpdateRecordExists(orgAnnualResetObject.getOrganizationId());
			    
			    orgAnnualResetObject.setModifiedDate(new Date());
		    	orgAnnualResetObject.setModifiedUser(((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
		    	orgAnnualResetObject.setSchoolYear(endDateYear);
		    	orgAnnualResetObject.setSchoolYearResetComplete(false);
		    	orgAnnualResetObject.setFcsResetComplete(false);
				
		    	if(existingObject !=null){
			    	organizationAnnualResetsService.updateOrganizationAnnualResets(orgAnnualResetObject);
			    	LOGGER.debug("Updated "+orgAnnualResetObject.getOrganizationId()+ " Organization to AnnualReset table for the year: "+endDateYear);
			    }
			    else
			    {
				    orgAnnualResetObject.setCreatedUser(((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
					orgAnnualResetObject.setCreatedDate(new Date());
					orgAnnualResetObject.setActiveFlag(true);
					organizationAnnualResetsService.addOrganizationAnnualResets(orgAnnualResetObject);
					LOGGER.debug("Added "+orgAnnualResetObject.getOrganizationId()+ " Organization to AnnualReset table for the year: "+endDateYear);
			    }
			    
			    isDLMState = false;
			    isDLMState = organizationService.checkForDLM(orgAnnualResetObject.getOrganizationId(), USER_ORG_DLM);
				if(isDLMState){
					if(firstContactService.checkIfOrgSettingsExists(orgAnnualResetObject.getOrganizationId(), endDateYear) == null){
						FirstContactSettings priorYearFCSSettings = new FirstContactSettings();
						//get prior year settings if available and use for new school year
						priorYearFCSSettings = firstContactService.checkIfOrgSettingsExists(orgAnnualResetObject.getOrganizationId(), (endDateYear-1));
						if(priorYearFCSSettings != null){
							firstContactSettings = new FirstContactSettings();
							firstContactSettings.setCategoryId(priorYearFCSSettings.getCategoryId());
							firstContactSettings.setOrganizationId(orgAnnualResetObject.getOrganizationId());
							//Added mathFlag and elaFlag for F607
							firstContactSettings.setMathFlag(priorYearFCSSettings.getMathFlag());
							firstContactSettings.setElaFlag(priorYearFCSSettings.getElaFlag());
							firstContactSettings.setScienceFlag(priorYearFCSSettings.getScienceFlag());
							firstContactSettings.setSchoolYear(endDateYear);
							firstContactSettings.setAuditColumnProperties();
							firstContactService.insertFirstContactSettings(firstContactSettings);
							
							int rowsUpdated = firstContactService.inactivateOtherSchoolYearRecords(endDateYear, orgAnnualResetObject.getOrganizationId());
							LOGGER.debug("Inactivated "+rowsUpdated+ " Organizations firstContactSettings for schoolYear: "+(endDateYear-1) + "- OrganizationId: " + orgAnnualResetObject.getOrganizationId());
							
						}else{//insert entry with default settings core qns and science flag to false
							firstContactSettings = new FirstContactSettings();
							firstContactSettings.setCategoryId(coreSetQuestions.getId());
							firstContactSettings.setOrganizationId(orgAnnualResetObject.getOrganizationId());
							//Added mathFlag and elaFlag for F607
							firstContactSettings.setElaFlag(true);
							firstContactSettings.setMathFlag(true);
							firstContactSettings.setScienceFlag(false);
							firstContactSettings.setSchoolYear(endDateYear);
							firstContactSettings.setAuditColumnProperties();
							firstContactService.insertFirstContactSettings(firstContactSettings);
						}
						
						LOGGER.debug("FirstContactSettings record inserted for OrganizationId: "+ orgAnnualResetObject.getOrganizationId() + " for schoolYear: "+endDateYear);
					}
				}
				
			}			
			
			map.put("resetOrgsSaved", true);
		}
		catch(Exception e){
			LOGGER.debug("resetOrgsSaved errorThrown: "+e.getMessage());
			map.put("resetOrgsSaved", false);
		}
		
		return map;
		
	}
	
	public String[] jsonArrayToStringArray(JSONArray jsonArray) throws JSONException {
	    int arraySize = jsonArray.length();
	    String[] stringArray = new String[arraySize];

	    for(int i=0; i<arraySize; i++) {
	    	JSONObject jsonObject = jsonArray.getJSONObject(i);
	        stringArray[i] = (String) jsonObject.toString();
	    }

	    return stringArray;
	}
	
	 @RequestMapping(value = "/getAnnualResetStates.htm", method = RequestMethod.GET)
	    public final @ResponseBody List<OrganizationAnnualResets> getAnnualResetStates() {
		 	LOGGER.trace("Entering the getAnnualResetStates method.");
	       
		    List<OrganizationAnnualResets> states = organizationAnnualResetsService.getAnnualResetStates();
	        LOGGER.trace("Leaving the getAnnualResetStates method.");
	        return states;                
	    }

	 @RequestMapping(value = "manageOrganization.htm")
	 public final ModelAndView manageOrganization(@RequestParam Long organizationId){		 
		    ModelAndView mav = new ModelAndView("manageOrganization");
		 	
		    Map<Long, String> availableAssessments = new HashMap<Long, String>();
	    	Organization organization = organizationService.getOrganizationDetailsByOrgId(organizationId);
	    	for (AssessmentProgram assessmentProgram : organization.getAssessmentPrograms()) {
				availableAssessments.put(assessmentProgram.getId(), assessmentProgram.getProgramName());
			}
	    	
	    	SimpleDateFormat formater = new SimpleDateFormat("MM/dd/YYYY");
	    	List<AssessmentProgram> allAssessments = assessProgService.getAllActive();
	    	List<Category> testModels = categoryService.selectByCategoryType("DLM_TESTING_MODEL");
	    	mav.addObject("organization", organization);    
	    	mav.addObject("allAssessments", allAssessments);
	    	mav.addObject("testModels", testModels);
	    	mav.addObject("availableAssessments", availableAssessments);
	    	mav.addObject("startDateStr", formater.format(organization.getSchoolStartDate()));
	    	mav.addObject("endDateStr", formater.format(organization.getSchoolEndDate()));
	    	
	    	Date testBegTimeVar = organizationService.getTestBeginTime(organizationId);
	    	Date testEndTimeVar = organizationService.getTestEndTime(organizationId);
	    	SimpleDateFormat timeFormater = new SimpleDateFormat("hh:mm a");

	    	try {
				if (testBegTimeVar != null) {
					mav.addObject("testBeginTimeStr", timeFormater.format(testBegTimeVar));
				}
				if (testEndTimeVar != null) {
					mav.addObject("testEndTimeStr", timeFormater.format(testEndTimeVar));
				}
			} catch (Exception ex) {
				LOGGER.error("Caught in Manage organization view. Stacktrace: {}", ex);
			}
	    	String testDaysVar = organizationService.getTestDays(organizationId);
	    	if (testDaysVar != null) {
	    		mav.addObject("testDaysStr", testDaysVar);
	    	}
	    	return mav;
	    }	 
	 
	    @RequestMapping(value = "saveManageOrganization.htm", method = RequestMethod.POST)
	    public final @ResponseBody String saveManageOrganization(
	    		@RequestParam("id") Long manageOrgId, 
	    		@RequestParam("organizationName") String manageOrgName,
	    		@RequestParam("displayIdentifier") String displayIdentifier,
	    		@RequestParam("typeCode") String typeCode,
	    		@RequestParam("manageOrgAssessmentProgram[]") Long[] manageOrgAssessmentProgram,
	    		@RequestParam(value = "manageOrgTestModels", required =false) Long manageOrgTestModels,
	    		@RequestParam("reportYear") int reportYear,
	    		@RequestParam("currentSchoolYear") Long currentSchoolYear,
				@RequestParam("testBeginTime") String testBeginTime,
				@RequestParam("testEndTime") String testEndTime,
				@RequestParam("testDays") String testDays) 	{ 	
	    	
	    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
	    	
	    	Organization organization = new Organization();
	    	organization.setId(manageOrgId);
	    	organization.setOrganizationName(manageOrgName);
	    	organization.setDisplayIdentifier(displayIdentifier);
	    	organization.setTestingModel(manageOrgTestModels);
	    	organization.setReportYear(reportYear);
	    	organization.setTypeCode(typeCode);
	    	organization.setCurrentSchoolYear(currentSchoolYear);
	    	organization.setAuditColumnPropertiesForUpdate();	    	
	    	organization.setAssessmentProgramIdList(new ArrayList<Long>(Arrays.asList(manageOrgAssessmentProgram)));

	    	try {
	    		if(testBeginTime != null && !StringUtils.isEmpty(testBeginTime)) {
	    			Date testBeginVar=new SimpleDateFormat("hh:mm a").parse(testBeginTime);
	    			organization.setTestBeginTime(testBeginVar);
	    		}
	    		else {
	    			organization.setTestBeginTime(null);
	    		}
	    		if(testEndTime != null && !StringUtils.isEmpty(testEndTime)) {
	    			Date testEndVar=new SimpleDateFormat("hh:mm a").parse(testEndTime);
	    			organization.setTestEndTime(testEndVar);
	    		}
	    		else {
	    			organization.setTestEndTime(null);
	    		}
			} catch (ParseException ex) {
				LOGGER.error("Caught in Manage Organization. Stacktrace: {}", ex);
				ex.printStackTrace();
			} 
	    	
	    	organization.setTestDays(testDays);
	    	
	    	try{
	    		asynchronousProcessService.manageOrganization(organization, user);
	    		return "{\"success\":\"true\"}";
	    	}catch(Exception e){
	    		LOGGER.error("manage Organization Failed :" +e.getMessage());
	    		return "{\"success\":\"false\"}";
	    	}	    	
	    }	 
}
