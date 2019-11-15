package edu.ku.cete.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.dataextract.service.DataReportDetailService;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.OrganizationHierarchy;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.util.CommonConstants;


@Controller
public class OrganizationFilterController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private OrganizationService orgService;
    
    @Autowired
    private GroupsDao groupsDao;
	       
    /**
	  * OrganizationTypeService
	  */
    @Autowired
	private OrganizationTypeService organizationTypeService;
    
    @Autowired
	private AssessmentProgramService assessmentProgramService;
    
   
    
    @Autowired
    private DataReportDetailService dataReportDetailsService;

    @RequestMapping(value = "getStatesForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getStatesForUser() {
        logger.trace("Entering the getStatesForUser method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        Long orgId = user.getOrganization().getId();
        List<Organization> states = null;
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)){
        	states = orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)){
        	//Hack for DE5725 - Org structure for DLM and ARMM are different
        	if (user.getOrganization().getDisplayIdentifier().equals("DLM") || user.getOrganization().getDisplayIdentifier().equals("ARMM")){
        		states =orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_REGION_CODE);
        	}else{
        		states = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, userId);
        	}
        }  
        //Hack for DE5725 - Org structure for DLM and ARMM are different
        else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_REGION_CODE)){
        	states = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_REGION_CODE, userId);
        }
        logger.trace("Leaving the getStatesForUser method.");
        return states;
    }
    
    @RequestMapping(value = "/getAllStates.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getAllStates() {
        logger.trace("Entering the getAllStates method.");
       
       // List<Organization> states = orgService.getByTypeId(CommonConstants.ORGANIZATION_STATE_CODE);
        List<Organization> states = orgService.getByTypeId(CommonConstants.ORGANIZATION_STATE_CODE, "name");
        populateHierarchy(states);
        logger.trace("Leaving the getAllStates method.");
        return states;
    }
    
    @RequestMapping(value = "getDistrictsForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getDistrictsForUser() {
        logger.trace("Entering the getDistrictsForUser method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        List<Organization> districts = null;
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)){
        	districts = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_DISTRICT_CODE, userId);
        }
        logger.trace("Leaving the getDistrictsForUser method.");
        return districts;
    }
    
    @RequestMapping(value = "getSchoolsForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getSchoolsForUser() {
        logger.trace("Entering the getSchoolsForUser method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        List<Organization> schools = null;
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)){
        	schools = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_SCHOOL_CODE, userId);
        }
        logger.trace("Leaving the getSchoolsForUser method.");
        return schools;
    }
    
    @RequestMapping(value = "getDistrictsInState.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getDistrictsInState(Long stateId,
        	@RequestParam(value = "isInactiveOrgReq", required = false) Boolean isInactiveOrgReq) {
        logger.trace("Entering the getDistrictsInState method.");
        List<Organization> districts = null;
        if(isInactiveOrgReq != null && isInactiveOrgReq){
        	districts = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(stateId, CommonConstants.ORGANIZATION_DISTRICT_CODE);   
        }else{
        	districts = orgService.getAllChildrenByOrgTypeCode(stateId, CommonConstants.ORGANIZATION_DISTRICT_CODE);   
        }
        
        Collections.sort(districts, OrgComparator);
        
        logger.trace("Leaving the getDistrictsInState method.");
        return districts;
    }
    
    @RequestMapping(value = "getSchoolsInDistrict.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getSchoolsInDistrict(Long districtId,
        	@RequestParam(value = "isInactiveOrgReq", required = false) Boolean isInactiveOrgReq) {
        logger.trace("Entering the getSchoolsInDistrict method.");
        List<Organization> districts = null;
        if(isInactiveOrgReq != null && isInactiveOrgReq){
        	districts = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(districtId, CommonConstants.ORGANIZATION_SCHOOL_CODE);        	
        }else{
        	districts = orgService.getAllChildrenByOrgTypeCode(districtId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
        }       
        
        Collections.sort(districts, OrgComparator);
        
        logger.trace("Leaving the getSchoolsInDistrict method.");
        return districts;
    }
    
    static Comparator<Organization> OrgComparator = new Comparator<Organization>(){
    	public int compare(Organization org1, Organization org2){
    		return org1.getOrganizationName().compareToIgnoreCase(org2.getOrganizationName());
    	}
    };
    
    
    @RequestMapping(value = "getSchoolsInDistrictforKapScore.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getSchoolsInDistrictforKapScore(Long districtId) {
        logger.trace("Entering the getSchoolsInDistrict method.");

        List<Organization> districts = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(districtId, CommonConstants.ORGANIZATION_SCHOOL_CODE);      
        Collections.sort(districts, OrgComparator);
        
        logger.trace("Leaving the getSchoolsInDistrict method.");
        return districts;
    }
    /**
     * @return
     */
    @RequestMapping(value = "getStatesByUserForReports.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getStatesByUserForReports() {
        logger.trace("Entering the getStatesByUserForReports method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        Long orgId = user.getOrganization().getId();
        List <Organization> organizations = null;
        List<Organization> states = null;
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)) {
        	states = orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)) {
        	//Hack for DE5725 - Org structure for DLM and ARMM are different
        	if (user.getOrganization().getDisplayIdentifier().equals("DLM") || 
        			user.getOrganization().getDisplayIdentifier().equals("ARMM")) {
        		states =orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_REGION_CODE);
        	} else {
        		states = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, userId);
        	}
        } else {
        	organizations = orgService.getAllParents(user.getOrganizationId());
        	states = new ArrayList<Organization>();
        	for(Organization org: organizations) {
    			if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
    				states.add(org);
    			}
    		}
        }

        logger.trace("Leaving the getStatesByUserForReports method.");
        return states;
    }
    
    /**
     * @return
     */
    @RequestMapping(value = "getStatesByAssesmentPrograms.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getStatesByAssesmentPrograms(
    		@RequestParam(value="editCall",required=false)Boolean editOption,
    		@RequestParam(value="assesmentProgramList[]") List<Long> assesmentProgramList) {
        logger.trace("Entering the getStatesByUserForReports method.");
        
        List<Organization> states = null;

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		Groups group = groupsDao.getGroup(userDetails.getUser().getCurrentGroupsId());
		Boolean isEditCall=Boolean.FALSE;
		   if(editOption==null)
	        {
	        	editOption=Boolean.FALSE;
	        }
	        isEditCall= editOption && (group.getGroupCode().equalsIgnoreCase("SSAD") || group.getGroupCode().equalsIgnoreCase("PGAD")
	        		||user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)
	        		
	        		);
	        if(isEditCall)
	        {
	        	states=orgService.getStatesByAssesmentPrograms(assesmentProgramList,CommonConstants.ORGANIZATION_STATE_CODE);
	        }

        logger.trace("Leaving the getStatesByUserForReports method.");
        return states;
    }
    
    /**
     * @return
     */

    
    @RequestMapping(value = "getDistrictsByUserForReports.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Organization> getDistrictsByUserForReports(
    		@RequestParam(value = "stateId", required = false) Long stateId,
    		@RequestParam(value = "isInactiveOrgReq", required = false) Boolean isInactiveOrgReq) {
        logger.trace("Entering the getDistrictsByUserForReports method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        if (stateId == null) {
	        List<Organization> states = getStatesOrgsForUser(false,false);
	    	if (CollectionUtils.isNotEmpty(states)) {
	    		Organization state = states.get(0);
	    		if (state != null) {
	    			stateId = state.getId();
	    		}
	    	}
        }
        List<Organization> organizations = null;
        List<Organization> districts = null;
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)) {
        	if(isInactiveOrgReq != null && !isInactiveOrgReq)
        		districts = orgService.getAllChildrenByOrgTypeCode(stateId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
        	if(isInactiveOrgReq != null && isInactiveOrgReq)
        	    districts = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(stateId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)) {
        	if(isInactiveOrgReq != null && !isInactiveOrgReq)
        	    districts = orgService.getAllChildrenByOrgTypeCode(stateId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
        	if(isInactiveOrgReq != null && isInactiveOrgReq)
        	    districts = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(stateId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
        	districts = Arrays.asList(orgService.get(user.getCurrentOrganizationId()));
        	//districts = orgService.getByTypeAndUserIdInParent(CommonConstants.ORGANIZATION_DISTRICT_CODE, userId, stateId);
        } else {
        	organizations = orgService.getAllParents(user.getOrganizationId());
        	districts = new ArrayList<Organization>();
        	for(Organization org: organizations) {
    			if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_5) {
    				districts.add(org);
    			}
    		}
        }
        Collections.sort(districts, OrgComparator);
        
        logger.trace("Leaving the getDistrictsByUserForReports method.");
        return districts;
    }
    
    
    
    
    @RequestMapping(value = "getStatesByUserForAlternateReports.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getStatesByUserForAlternateReports(
    		@RequestParam(value = "isInactiveOrgReq", required = false) Boolean isInactiveOrgReq) {
        logger.trace("Entering the getStatesByUserForAlternateReports method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long orgId = user.getOrganization().getId();
        List <Organization> organizations = null;
        List<Organization> states = null;
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)) {
        	if(isInactiveOrgReq != null && !isInactiveOrgReq)
        		states = orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
        	if(isInactiveOrgReq != null && isInactiveOrgReq)
        	    states = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)) {
        	states = Arrays.asList(orgService.get(user.getCurrentOrganizationId()));
        } else {
        	organizations = orgService.getAllParents(user.getOrganizationId());
        	states = new ArrayList<Organization>();
        	for(Organization org: organizations) {
    			if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2) {
    				states.add(org);
    			}
    		}
        }

        logger.trace("Leaving the getStatesByUserForAlternateReports method.");
        return states;
    }
    
    

    
    /**
     * @return
     */
    
	
	//For Report Year
    @RequestMapping(value = "getReportyearByUserForReports.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Long> getReportyearByUserForReports(
    		@RequestParam(value = "reportType", required = false) String reportType,	
    		@RequestParam(value = "reportCode", required = false) String reportCode,
    		@RequestParam(value = "assessmentCode",required = false)String  assessmentCode,
    		@RequestParam(value = "assessmentProgId" ,required = false)String assessmentProgIds    		
    		) {
    		logger.trace("Entering the getReportyearByUserForReports method.");  
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        User user = userDetails.getUser();
	        Long assessmentProgId = Long.valueOf(assessmentProgIds);
	        Long currentYear = null;
	        List<Long> reportYear = new ArrayList<Long>();
	        	if(assessmentCode.equals("CPASS")){  	        		
	        		currentYear = orgService.getReportYear(user.getContractingOrganization().getId(), assessmentProgId);
	        		reportYear.add(currentYear);
	        	}else{  
        			currentYear= new Long(user.getContractingOrganization().getReportYear());
        			reportYear.add(currentYear);	        			 
	        	}	       
	        	//dataWareReportyear = dataReportDetailsService.getCurrentReportYear(user.getContractingOrganization().getId(), assessmentProgId,reportType,reportCode,user.getContractingOrganization().getReportYear());
	        	
	        	AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgId);
	        	for(long i = assessmentProgram.getBeginReportYear()!=null?assessmentProgram.getBeginReportYear():2015L; i < currentYear ;i++){	            	
	        		reportYear.add(i);
	            }
	         Collections.sort(reportYear, Collections.reverseOrder());
         return reportYear;
    }
    
    @RequestMapping(value = "getSchoolsByUserForReports.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Organization> getSchoolsByUserForReports(
    		@RequestParam("districtIds[]") Long[] districtIds,
    		@RequestParam(value = "isInactiveOrgReq", required = false) Boolean isInactiveOrgReq) {
        logger.trace("Entering the getSchoolsByUserForReports method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        List<Organization> schools = new ArrayList<Organization>();
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE) ||
        		user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE) ||
        		user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
        	for (Long id : districtIds) {
        		if(isInactiveOrgReq != null && !isInactiveOrgReq)
        		   schools.addAll(orgService.getAllChildrenByOrgTypeCode(id, CommonConstants.ORGANIZATION_SCHOOL_CODE));
        		if(isInactiveOrgReq != null && isInactiveOrgReq)
        		   schools.addAll(orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(id, CommonConstants.ORGANIZATION_SCHOOL_CODE));
        	}
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)) {
        	schools.add(user.getOrganization());
        } else {
        	for (Long id : districtIds) {
        		schools.addAll(orgService.getByTypeAndUserIdInParent(CommonConstants.ORGANIZATION_SCHOOL_CODE, userId, id));
        	}
        }
        Collections.sort(schools, OrgComparator);
        
        logger.trace("Leaving the getSchoolsByUserForReports method.");
        return schools;
    }
    
    

    
    /**
     * @return
     */
    @RequestMapping(value = "getStatesOrgsForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getStatesOrgsForUser(
    		@RequestParam(value="getAllUserOrgs", required = false) boolean getAllUserOrgs,
    		@RequestParam(value="editOption", required = false) Boolean editOption) {
        logger.trace("Entering the getStatesOrgsForUser method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        Long orgId = user.getOrganization().getId();
		Groups group = groupsDao.getGroup(userDetails.getUser().getCurrentGroupsId());
		Boolean isEditCall=Boolean.FALSE;
		   if(editOption==null)
	        {
	        	editOption=Boolean.FALSE;
	        }
	        isEditCall= editOption && (group.getGroupCode().equalsIgnoreCase("SSAD"));
	     
        List<Organization> states = null;
        logger.debug("logged in users orgid: "+orgId+" org type: "+user.getOrganization().getOrganizationType().getTypeCode());
        if(isEditCall)
        {
        	states=orgService.getAllChildrenByTypeForEditSSA(CommonConstants.ORGANIZATION_STATE_CODE);
        }
        else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_CONSORTIA_CODE)){
        	states = orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_STATE_CODE)){
        	//Hack for DE5725 - Org structure for DLM and ARMM are different
        	if (user.getOrganization().getDisplayIdentifier().equals("DLM") || user.getOrganization().getDisplayIdentifier().equals("ARMM")){
        		states =orgService.getAllChildrenByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_REGION_CODE);
        	}else{
        		if(getAllUserOrgs)
        		    states = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, userId);
        		else
        			states = orgService.getLoggedInUserOrganizationHierarchy(orgId, CommonConstants.ORGANIZATION_STATE_CODE);

        	}
        }  else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE) || 
        		user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_SCHOOL_CODE) || 
        		user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_BUILDING_CODE)){
        	states = orgService.getAllParentsByOrgTypeCode(orgId, CommonConstants.ORGANIZATION_STATE_CODE);
        } 
        
        populateHierarchy(states);

        logger.trace("Leaving the getStatesOrgsForUser method.");
        return states;
    }

	private void populateHierarchy(List<Organization> states) {
		Map<Long,String> orgTypeCodeMap = new HashMap<Long,String>();
        List<String> orgHierarchiesList = new ArrayList<String>();
        Map<Long,List<String>> orgHierarchiesMap = new HashMap<Long,List<String>>();
        
        List<OrganizationHierarchy> organizationHierarchies = orgService.getAllOrganizationHierarchies();
        List<OrganizationType> organizationTypes = organizationTypeService.getAll();
        for(OrganizationType organizationType: organizationTypes) {
        	orgTypeCodeMap.put(organizationType.getOrganizationTypeId(), String.valueOf(organizationType.getTypeLevel()));
		}
        
        for(OrganizationHierarchy organizationHierarchy : organizationHierarchies) {
    		if(orgHierarchiesMap.get(organizationHierarchy.getOrganizationId()) != null) {
    			orgHierarchiesList = orgHierarchiesMap.get(organizationHierarchy.getOrganizationId());        			
    		} else {
    			orgHierarchiesList = new ArrayList<String>();
    		}
    		orgHierarchiesList.add(orgTypeCodeMap.get(organizationHierarchy.getOrganizationTypeId()));
			orgHierarchiesMap.put(organizationHierarchy.getOrganizationId(),orgHierarchiesList);
        }
        
        if(states != null && states.size() > 0) {
	        for(Organization organization : states) {
	        	organization.setOrganizationHierarchies(orgHierarchiesMap.get(organization.getId()));
	        }
        }
	}
    
    
    /**
     * @return
     */
    @RequestMapping(value = "getChildOrgsForUser.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getChildOrgsForUser() {
        logger.trace("Entering the getChildOrgsForUser method.");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        Long userId = user.getId();
        List<Organization> organizations = null;
        if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_REGION_CODE)){
        	organizations = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_REGION_CODE, userId);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_AREA_CODE)){
        	organizations = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_AREA_CODE, userId);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_DISTRICT_CODE)){
        	organizations = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_DISTRICT_CODE, userId);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_BUILDING_CODE)){
        	organizations = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_BUILDING_CODE, userId);
        } else if (user.getOrganization().getOrganizationType().getTypeCode().equals(CommonConstants.ORGANIZATION_SCHOOL_CODE)){
        	organizations = orgService.getByTypeAndUserId(CommonConstants.ORGANIZATION_SCHOOL_CODE, userId);
        } 
        	
        
        Map<Long,String> orgTypeCodeMap = new HashMap<Long,String>();
        
        List<Organization> parentStateOrg = orgService.getAllParentsByOrgTypeCode(
        		organizations.get(0).getId(), CommonConstants.ORGANIZATION_STATE_CODE);

        List<OrganizationType> organizationTypes = organizationTypeService.getAll();
        for(OrganizationType organizationType: organizationTypes) {
        	orgTypeCodeMap.put(organizationType.getOrganizationTypeId(), String.valueOf(organizationType.getTypeLevel()));
		}
        
        if(parentStateOrg != null) {
        	List<OrganizationHierarchy> organizationHierarchies = orgService.getOrgHierarchiesById(parentStateOrg.get(0).getId());
        	List<String> orgHierarchiesList = new ArrayList<String>();
        	for(OrganizationHierarchy orgHierarchy: organizationHierarchies) {
        		orgHierarchiesList.add(String.valueOf(orgTypeCodeMap.get(orgHierarchy.getOrganizationTypeId())));
        	}
        	for(Organization org : organizations) {
        		org.setOrganizationHierarchies(orgHierarchiesList);
        	}
        }
        
        logger.trace("Leaving the getChildOrgsForUser method.");
        return organizations;
    }
    
    /**
     * @param orgId
     * @param orgType
     * @return
     */
    @RequestMapping(value = "getChildOrgsForFilter.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getChildOrgsForFilter(Long orgId, String orgType) {
        logger.trace("Entering the getChildOrgsForFilter method.");

        List<Organization> districts = orgService.getAllChildrenByOrgTypeCode(orgId, orgType);      
        Collections.sort(districts, OrgComparator);
        
        logger.trace("Leaving the getChildOrgsForFilter method.");
        return districts;
    }
        
    @RequestMapping(value = "getChildOrgsWithParentForFilter.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Organization> getAllChildrenWithParentByOrgTypeCode(Long orgId, String orgType) {
        logger.trace("Entering the getAllChildrenWithParentByOrgTypeCode method.");

        List<Organization> districts = orgService.getAllChildrenWithParentByOrgTypeCode(orgId, orgType);      
        Collections.sort(districts, OrgComparator);
        
        logger.trace("Leaving the getAllChildrenWithParentByOrgTypeCode method.");
        return districts;
    }    
    
    /**
     * @param orgId
     * @param orgType
     * @return
     */
    @RequestMapping(value = "getOrgsBasedOnUserContext.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getOrgsBasedOnUserContext(Long orgId, String orgType, int orgLevel){
    	
    	List<Organization> organizations = null;
    
	     if(SecurityContextHolder.getContext().getAuthentication() != null
					&& SecurityContextHolder.getContext().getAuthentication()
					.isAuthenticated()){
	    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    User user = userDetails.getUser();
		    Long loggedInUserOrgId = user.getOrganization().getId();
		    int loggedInUsersOrgLevel = user.getOrganization().getOrganizationType().getTypeLevel();
	    	    
		    if (loggedInUsersOrgLevel >= orgLevel){
		        organizations = orgService.getLoggedInUserOrganizationHierarchy(loggedInUserOrgId, orgType);    
		    	
		    } else {
		    	organizations = orgService.getAllChildrenByOrgTypeCode(orgId, orgType);    
		    }
	     } else {
		    	organizations = orgService.getAllChildrenByOrgTypeCode(orgId, orgType);    
		 }
    
	     
	Collections.sort(organizations, OrgComparator);
    logger.trace("Leaving the getOrgsBasedOnUserContext method.");
    return organizations;
    }
    
    @RequestMapping(value = "getDeactivateOrgsBasedOnUserContext.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getDeactivateOrgsBasedOnUserContext(Long orgId, String orgType, int orgLevel){
    	
    	List<Organization> organizations = null;
    
	     if(SecurityContextHolder.getContext().getAuthentication() != null
					&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
	    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    User user = userDetails.getUser();
		    Long loggedInUserOrgId = user.getOrganization().getId();
		    int loggedInUsersOrgLevel = user.getOrganization().getOrganizationType().getTypeLevel();
	    	    
		    if (loggedInUsersOrgLevel >= orgLevel){
		        organizations = orgService.getLoggedInUserDeactivateOrganizationHierarchy(loggedInUserOrgId, orgType);    
		    	
		    } else {
		    	organizations = orgService.getDeactivateChildrenByOrgTypeCode(orgId, orgType);    
		    }
	     } else {
		    	organizations = orgService.getDeactivateChildrenByOrgTypeCode(orgId, orgType);    
		 }
	     
	Collections.sort(organizations, OrgComparator);
    logger.trace("Leaving the getOrgsBasedOnUserContext method.");
    return organizations;
    }
    
    @RequestMapping(value = "getOrgsForExtractFilters.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getOrgsForExtractFilters(
    		@RequestParam(value = "isInactiveOrgReq", required = false) Boolean isInactiveOrgReq) {
    	logger.trace("Entering the getOrgsForExtractFilters method.");
    	List<Organization> orgs = null;
    	
    	if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		Long loggedInUsersOrgId = user.getOrganization().getId();
    		//int loggedInUsersOrgLevel = user.getOrganization().getOrganizationType().getTypeLevel();
    		int userAccessLevel = user.getAccessLevel();
    		
    		if (userAccessLevel < 20) { // user is consortia level, so we'll retrieve states
    			orgs = getStatesOrgsForUser(false,false);
    		} else if (userAccessLevel < 50) { // user is above districts
    			if(isInactiveOrgReq != null && isInactiveOrgReq){
    			  orgs = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(loggedInUsersOrgId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
    			}else{
    				orgs = getChildOrgsForFilter(loggedInUsersOrgId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
    		    }   			
    			
    		} else if (userAccessLevel < 70) { // user is above schools
    			if(isInactiveOrgReq != null && isInactiveOrgReq){
    			  orgs = orgService.getAllActiveAndInactiveChildrenByOrgTypeCode(loggedInUsersOrgId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
    			}else{
    			  orgs = getChildOrgsForFilter(loggedInUsersOrgId, CommonConstants.ORGANIZATION_SCHOOL_CODE);	
    			}
    		} else { // school level, so there are no filters needed (this request generally won't be made in this case, but this is just in case) 
    			orgs = new ArrayList<Organization>();
    		}
    	}
    	
    	Collections.sort(orgs, OrgComparator);
    	logger.trace("Leaving the getOrgsForExtractFilters method.");
    	return orgs;
    }
    
    
    @RequestMapping(value = "getOrgsForExtractFiltersPDStatus.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getOrgsForExtractFiltersPDStatus() {
    	logger.trace("Entering the getOrgsForExtractFiltersPDStatus method.");
    	List<Organization> orgs = null;
    	
    	if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		Long loggedInUsersOrgId = user.getOrganization().getId();
    		//int loggedInUsersOrgLevel = user.getOrganization().getOrganizationType().getTypeLevel();
    		
    		String role = user.getGroupCode(); //it will give current group code    		
    		
    		Long assessmentProgramId = assessmentProgramService.findByAbbreviatedName("DLM").getId();
    		
    		if(user.getAccessLevel() <= 20 && ("PDAD").equalsIgnoreCase(role)){
    			orgs = orgService.getOrganizationByassessmentProgramId(assessmentProgramId);
    		}else if(user.getAccessLevel() <= 20){
    		    orgs = getChildOrgsForFilter(loggedInUsersOrgId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
    		}else if(user.getAccessLevel() <= 50){
    			orgs = getChildOrgsForFilter(loggedInUsersOrgId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
    		}
    	}    	
    	Collections.sort(orgs, OrgComparator);
    	logger.trace("Leaving the getOrgsForExtractFiltersPDStatus method.");
    	return orgs;
    }
    
    /*@RequestMapping(value = "getDistrictsByUserForReports.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getDistrictsByUserForNewReports() {
    	List<Organization> orgs = null;
    	if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
    		
	    	List<Organization> states = getStatesOrgsForUser(false);
	    	if (states != null && !states.isEmpty()) {
	    		Organization state = states.get(0);
	    		orgs = getDistrictsByUserForReports(state.getId());
	    	}
    	}
    	Collections.sort(orgs, OrgComparator);
    	return orgs;
    }*/
    
    @RequestMapping(value = "getSchoolsInDistrictForPDStatus.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Organization> getSchoolsInDistrictForPDStatus(@RequestParam("districtId[]") Long[] districtId) {
        logger.trace("Entering the getSchoolsInDistrict method.");
        List<Organization> districts = new ArrayList<Organization>();
        for(int i=0;i<districtId.length;i++){
        	districts.addAll(orgService.getAllChildrenByOrgTypeCode(districtId[i], CommonConstants.ORGANIZATION_SCHOOL_CODE));      
	        Collections.sort(districts, OrgComparator);
        }
        logger.trace("Leaving the getSchoolsInDistrict method.");
        return districts;
    }
    
    @RequestMapping(value = "getOrgsForExtractFiltersMultiSelect.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getOrgsForExtractFiltersMultiSelect() {
    	logger.trace("--> getOrgsForExtractFiltersMultiSelect method.");
    	List<Organization> orgs = null;
    	
    	if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		Long loggedInUsersOrgId = user.getOrganization().getId();
    		//int loggedInUsersOrgLevel = user.getOrganization().getOrganizationType().getTypeLevel();
    		int userAccessLevel = user.getAccessLevel();
    		
    		if (userAccessLevel < 20) { // user is consortia level, so we'll retrieve states
    			orgs = getStatesOrgsForUser(false,false);
    		} else if (userAccessLevel < 50) { // user is above districts
    			orgs = getChildOrgsForFilter(loggedInUsersOrgId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
    		} else if (userAccessLevel < 70) { // user is above schools
    			orgs = getChildOrgsForFilter(loggedInUsersOrgId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
    		} else { // school level, so there are no filters needed (this request generally won't be made in this case, but this is just in case) 
    			orgs = new ArrayList<Organization>();
    		}
    	}
    	
    	Collections.sort(orgs, OrgComparator);
    	logger.trace("<-- getOrgsForExtractFiltersMultiSelect method.");
    	return orgs;
    }
    
    @RequestMapping(value = "getDistrictsForStates.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getDistrictsForStates(@RequestParam("stateIds[]") Long[] stateIds) {
        logger.trace("Entering the getDistrictsForStates method.");
        List<Organization> districts = new ArrayList<Organization>();
        
        for(Long stateId : stateIds){
        	districts.addAll(orgService.getAllChildrenByOrgTypeCode(stateId, CommonConstants.ORGANIZATION_DISTRICT_CODE));  
        }
            
        Collections.sort(districts, OrgComparator);
        logger.trace("Leaving the getDistrictsForStates method.");
        return districts;
    }
    
    @RequestMapping(value = "getSchoolsForDistricts.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Organization> getSchoolsForDistricts(@RequestParam("districtIds[]") Long[] districtIds) {
        logger.trace("Entering the getSchoolsForDistricts method.");
        List<Organization> schools = new ArrayList<Organization>();

        for(Long districtId : districtIds){
        	schools.addAll(orgService.getAllChildrenByOrgTypeCode(districtId, CommonConstants.ORGANIZATION_SCHOOL_CODE));
        }
        
        Collections.sort(schools, OrgComparator);
        logger.trace("Leaving the getSchoolsForDistricts method.");
        return schools;
    }
    
    @RequestMapping(value = "checkForOrganizationChildExistsForUser.htm", method = RequestMethod.POST)
    public final @ResponseBody String checkForOrganizationChildExists (@RequestParam("selectedDistrictId") Long selectedDistrictId,
    		@RequestParam("selectedSchoolId") Long selectedSchoolId,
    		@RequestParam("currentRowOrgType") Long selectedOrgType,
    		@RequestParam("orgTypeCode") Long userOrgType) {
    	
    	try{
    		logger.info("--> checkForOrganizationChildExists.htm ");
            Map<String, String> model = new HashMap<String, String>();
            ObjectMapper mapper = getObjectMapper();
            
    		logger.trace("Entering the checkForOrganizationChildExists method.");
            UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            User currentUser = userDetails.getUser();
            List<Long> orgIds = currentUser.getAllChilAndSelfOrgIds();
        	Boolean checkOrgExists=orgService.isOrgChild(orgIds,selectedDistrictId, selectedSchoolId,userOrgType,selectedOrgType);
        	
        	 model.put("status", "success");
        	 model.put("checkOrgExists", checkOrgExists.toString());

             String modelJson = mapper.writeValueAsString(model);
             logger.debug("<-- checkForOrganizationChildExists.htm");
             return modelJson;

    	}catch (Exception e) {
            Map<String, String> model = new HashMap<String, String>();
    		 model.put("error", e.getMessage());
            logger.error("Exception occurred while getting child organizations: ", e);
            return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
        }
        
    }
    
    private ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
        return mapper;
    }
    
}
