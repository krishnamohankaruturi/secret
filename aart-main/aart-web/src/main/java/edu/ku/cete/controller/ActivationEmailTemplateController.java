package edu.ku.cete.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ku.cete.domain.JQGridJSONModel;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.common.ActivationEmailTemplate;
import edu.ku.cete.domain.common.ActivationEmailTemplateState;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.model.ActivationEmailTemplateDao;
import edu.ku.cete.service.ActivationEmailTemplateService;
import edu.ku.cete.service.ActivationEmailTemplateStateService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.json.ActivationEmailTemplateJsonConverter;

@Controller
public class ActivationEmailTemplateController {

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(ActivationEmailTemplateController.class);

	private static final String EMAIL_TEMPLATE_SETUP_JSP = "createActivationEmailTemplate";

	/**
	 * organizationService
	 */
	@Autowired
	private ActivationEmailTemplateService activationEmailTemplateService;
	
	@Autowired
	private ActivationEmailTemplateStateService activationEmailTemplateStateService;
	
	@Autowired
	private AssessmentProgramService assessProgService;
		
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private OrgAssessmentProgramService orgAssessmentProgramService;
    
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
	@RequestMapping(value = "getEmailActivationToView.htm", method = RequestMethod.POST)
	public final @ResponseBody
	JQGridJSONModel getEmailActivationToView(
			@RequestParam(value = "orgChildrenIds[]", required = false) String[] orgChildrenIds,
			@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters
			) throws JsonProcessingException, IOException {

		JQGridJSONModel jqGridJSONModel = null;
		Integer currentPage = null;
		Integer limitCount = null;
		List<ActivationEmailTemplate> emailActivation;
		Integer totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		Map<String, Object> criteria = new HashMap<String, Object>();
		//criteria.put("organizationId", NumericUtil.parse(orgChildrenIds[0], -1));
		populateCriteria(criteria, filters);
		
		emailActivation = activationEmailTemplateService.getAllChildrenToView(
				criteria, sortByColumn, sortType, (currentPage - 1) * limitCount, limitCount);

		totalCount = activationEmailTemplateService.countAllChildrenToView(criteria);

		jqGridJSONModel = ActivationEmailTemplateJsonConverter.convertToEmailActivationJson(
				emailActivation, totalCount, currentPage, limitCount);

		return jqGridJSONModel;
	}

	private void populateCriteria(Map<String, Object> criteria, String filters)	throws JsonProcessingException, IOException {
		if (null != filters && !filters.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(filters);
			final JsonNode results = rootNode.get("rules");			
			for (final JsonNode element : results) {				
					criteria.put(element.get("field").asText(), element.get("data").asText());
			}
		}
	}
	
	 @RequestMapping(value = "createEmailTemplate.htm")
	 public final ModelAndView createEmailTemplate( @RequestParam(value="templateId",required=false) Long templateId,
		       @RequestParam(value="isDefault", defaultValue="false") boolean isDefault ) {
		 	LOGGER.trace("Entering the createEmailTemplate method");
	    	ModelAndView mav = new ModelAndView(EMAIL_TEMPLATE_SETUP_JSP);	       
	    	ActivationEmailTemplate emailActivation = new ActivationEmailTemplate();
	    	Map<Long, String> assessmentProgramMap = new LinkedHashMap<Long, String>();
	    
	    	emailActivation.setIsDefault(isDefault);
	    	if(isDefault){
	    		emailActivation = activationEmailTemplateService.getDefaultEmailTemplate();	    		
	    	}else{
	    		
	    		if(templateId!=null && templateId.longValue()!=0){
	    			emailActivation = activationEmailTemplateService.getEmailActivationDetailsByTemplateId(templateId);
	    		}
	    		
		    	List<AssessmentProgram> assessProgLists = assessProgService.getAllActive();	
		    	
			    for (AssessmentProgram assProg: assessProgLists) {
					assessmentProgramMap.put(assProg.getId(), assProg.getProgramName());
			    }		    	
		    		    	
		    	mav.addObject("assessProgLists",assessmentProgramMap);		    	
	    	}
	    	mav.addObject("ActivationEmailTemplate",emailActivation); 
	    	LOGGER.trace("Leaving the createEmailTemplate method");
	    	return mav;
	    }
	 
	 @RequestMapping(value = "saveEmailTemplate.htm")
	 public final  @ResponseBody Map<String,Object> saveEmailTemplate( 
			 @RequestParam(value="id",required=false) Long templateId,
			 @RequestParam(value="templateName",required=false) String templateName,
			 @RequestParam(value="assessmentProgramId",required=false) Long assessmentProgramId,			 
		     @RequestParam(value="allStates",defaultValue="false") boolean allStates, 
		     @RequestParam(value="states", required=false) Long[] states,
		     @RequestParam(value="isDefault", defaultValue="false") boolean isDefault,
		     @RequestParam(value="emailSubject", required=false) String emailSubject, 
		     @RequestParam(value="emailBody", required=false) String emailBody, 
		     @RequestParam(value="includeEpLogo", defaultValue="false") boolean includeEpLogo, 
		     @RequestParam(value="statesAlreadyHaving", defaultValue="false" ,required=false) boolean statesAlreadyHaving,
			 HttpServletRequest request) {	
		    
		     String statesAlreadyTemplateIds[] = request.getParameterValues("statesAlreadyTemplateIds");
		     
		 	LOGGER.trace("Entering the saveEmailTemplate method");
		 
		    Map<String,Object> returnMap = new HashMap<String, Object>();
		    String success = "failed";		 
	    	       
	    	ActivationEmailTemplate emailActivation = new ActivationEmailTemplate();	    
	    	emailActivation.setId(templateId);
	    	templateName=(!templateName.equals("") && templateName!=null)?templateName.trim():templateName;
	    	emailActivation.setTemplateName(templateName);
	    	emailActivation.setAssessmentProgramId(assessmentProgramId);
	    	emailActivation.setAllStates(allStates);
	    	emailSubject=(!emailSubject.equals("") && emailSubject!=null)?emailSubject.trim():emailSubject;
	    	emailActivation.setEmailSubject(emailSubject);
	    	emailActivation.setEmailBody(emailBody);
	    	emailActivation.setIncludeEpLogo(includeEpLogo);	    	
	    	emailActivation.setIsDefault(isDefault);	    	
	    	
	    	if(allStates){	    		
	    		List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService.findStatesByAssessmentProgramId(assessmentProgramId);
	    		if(orgAssessProgs.size()>0){
	    			int y=0;
		    		states = new Long[orgAssessProgs.size()];
		    		for(OrgAssessmentProgram orgasessment : orgAssessProgs){
		    			states[y]=orgasessment.getOrganization().getId();    			
		    			y++;
		    		}
	    		}	    	
	    	}
	    	
	    	if(!isDefault){	
	    		boolean templateNameHaving = false;
	    		if(templateId!=null){
	    			if(activationEmailTemplateService.getTemplateByTemplateNameandTemplateId(emailActivation)!=null){
	    				templateNameHaving= true;
	    			}
	    		}else{
	    			if(activationEmailTemplateService.getTemplateByTemplateName(emailActivation)!=null){
	    				templateNameHaving= true;
	    			}
	    		}	    		
	    		
	    		if(templateNameHaving){    			
    				success="AlreadyTemplateName";
    			}else{ 
    				
    				List<ActivationEmailTemplateState> stateList = activationEmailTemplateStateService.getStatesByAssessmentProgramId(assessmentProgramId,states,templateId);
    				if(stateList!=null && stateList.size()>0 ){ 
    					if(!statesAlreadyHaving){
    						returnMap.put("stateList", stateList);    					
        					success = "statesAlreadyHaving";
    					}
    					else{
    						try{
    							if(statesAlreadyTemplateIds.length>0){
    								activationEmailTemplateService.updateOldTemplateStatesActiveFalse(statesAlreadyTemplateIds);
    							}    							
    							
    							if(activationEmailTemplateService.createCustomEmailTemplate(templateId,emailActivation,states)){
    	        		    		success = "success";
    	        		        }
    						}
    						catch(Exception e){
    							LOGGER.error("Exception ooccured while adding Email Template: " + e.getMessage(), e);
    						}
    						
    					}    					
    					
    				}
    				else{
    					if(activationEmailTemplateService.createCustomEmailTemplate(templateId,emailActivation,states)){
        		    		success = "success";
        		        }
    			   }
    				   				
    			}	    		
	    	}else{
	    		try{	    				    			
	    			emailActivation.setAssessmentProgramId(new Long(0));
	    			activationEmailTemplateService.updateDefaultEmailTemplate(emailActivation);		    		
		    		success = "success";	    			    			
	    		}catch(Exception e){
	    			LOGGER.error("Exception ooccured while adding Email Template: " + e.getMessage(), e);
	    		}
	    		
	    	}
	    	returnMap.put("result", success);
	    
	     	LOGGER.trace("Leaving the saveEmailTemplate method");
	     	
	    	return returnMap;
	    }
	 	 
	 @RequestMapping(value = "getOldTemplateLists.htm", method = RequestMethod.POST)
		public final @ResponseBody Map<String,Object> getOldTemplateLists(@RequestParam("templateId") Long templateId) {
		 	LOGGER.trace("Entering the getOldTemplateLists method");	
		 	Map<String,Object> returnMap = new HashMap<String, Object>();				
			if(templateId!=null && templateId.longValue()!=0){
				ActivationEmailTemplate emailActivation =  activationEmailTemplateService.getEmailActivationDetailsByTemplateId(templateId);
				returnMap.put("emailActivation", emailActivation);
			}
		 	LOGGER.trace("Leaving the getOldTemplateLists method");
			return returnMap;
		}
	 
	 
	 @RequestMapping(value = "	 getDefaultTemplate.htm", method = RequestMethod.POST)
		public final @ResponseBody Map<String,Object> getDefaultTemplate() {
		 LOGGER.trace("Entering the getDefaultTemplate method");
			Map<String,Object> returnMap = new HashMap<String, Object>();		
			try{
				ActivationEmailTemplate emailActivation =  activationEmailTemplateService.getDefaultEmailTemplate();
				returnMap.put("emailActivation", emailActivation);				
			}
			catch(Exception e){
				LOGGER.error("Exception ooccured while getDefaultTemplate : " + e.getMessage(), e);
			}
			LOGGER.trace("Leaving the getDefaultTemplate method");
			return returnMap;
		}
	 
	 @RequestMapping(value = "getTemplateLists.htm", method = RequestMethod.POST)
	 public final @ResponseBody Map<String,Object> getTemplateLists() {
		 LOGGER.trace("Entering the getTemplateLists method");
			Map<String,Object> returnMap = new HashMap<String, Object>();
			List<ActivationEmailTemplate> emailTemplateLists = activationEmailTemplateService.getAllActive();
			returnMap.put("emailTemplateLists", emailTemplateLists);
			LOGGER.trace("Leaving the getTemplateLists method");
			return returnMap;
		}
	 
	 
	 @RequestMapping(value = "getOrganizationByAssessmentProgramIdOnly.htm", method = RequestMethod.POST)
		public final @ResponseBody Map<String,Object> getOrganizationByAssessmentProgramId(@RequestParam("assessmentProgramId") Long assessmentProgramId,
				@RequestParam("templateId") Long templateId) {
		 	LOGGER.trace("Entering the getOrganizationByAssessmentProgramId method");
			Map<String,Object> returnMap = new HashMap<String, Object>();
			if(assessmentProgramId!=null && assessmentProgramId.longValue()!=0){
				List<OrgAssessmentProgram> orgAssessProgs = orgAssessmentProgramService.findStatesByAssessmentProgramId(assessmentProgramId);
				returnMap.put("orgAssessProgs", orgAssessProgs);
			}	
			if(assessmentProgramId!=null && assessmentProgramId.longValue()!=0 && templateId!=null && templateId.longValue()!=0){
				List<ActivationEmailTemplateState> statesList = activationEmailTemplateStateService.getActiveStatesByTemplateId(templateId);
				returnMap.put("statesList", statesList);
			}	
			LOGGER.trace("Leaving the getOrganizationByAssessmentProgramId method");
			return returnMap;
		}
}
