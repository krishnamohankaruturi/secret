package edu.ku.cete.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrganizationAnnualResetsService;
import edu.ku.cete.service.student.FirstContactService;

@Controller
public class FirstContactSettingsController {
	// Per US17690
	private Logger LOGGER = LoggerFactory.getLogger(FirstContactSettingsController.class);

	@Autowired
	private FirstContactService firstContactService;	
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
    private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private OrganizationAnnualResetsService organizationAnnualResetsService;
	
	/**
	 * 
	 */
	@RequestMapping(value = "saveFirstContactSettings.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Boolean> saveFirstContactSettings(
			@RequestParam(value = "firstContactSettings", required = true) String firstContactSettings)
					throws IOException {
		LOGGER.trace("Entering the saveFirstContactSettings method.");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		boolean result = true;
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<FirstContactSettings> fcsSettings = null;
			fcsSettings = mapper.readValue(firstContactSettings, new TypeReference<List<FirstContactSettings>>() {
			});
			firstContactService.toggleSurveyOnCategoryChange(fcsSettings);
			result = true;
		} catch (Exception e) {
			LOGGER.error("Exception while updating First Contact Survey Settings.", e);
			result = false;
		} finally {
			map.put("valid", result);
		}
		LOGGER.trace("Leaving the saveFirstContactSettings method.");
		return map;
	}
	
	
	@RequestMapping(value = "resetFCStatus.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Boolean> resetFCStatus(@RequestParam("dlmStateSelect") Long[] organizationIds) {
		LOGGER.trace("Entering the resetFCStatus method.");
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		boolean result = true;
		try {
			
			Long inProgressStatusId = categoryDao.getCategoryId("IN_PROGRESS", "SURVEY_STATUS");
			Long completeStatusId = categoryDao.getCategoryId("COMPLETE", "SURVEY_STATUS");
			Long readyToSubmitId = categoryDao.getCategoryId("READY_TO_SUBMIT", "SURVEY_STATUS");
		      
			for(Long orgId : organizationIds){
				
				firstContactService.resetSurveyStatus(orgId, inProgressStatusId, completeStatusId, readyToSubmitId);
			}			
			result = true;
		} catch (Exception e) {
			LOGGER.error("Exception occured while resetting FC survey status." + e.getMessage(), e);			
			result = false;
		}finally {
			map.put("valid", result);
		}
		LOGGER.trace("Leaving the resetFCStatus method.");
		return map;
	}
	
	
	@RequestMapping(value = "getDLMStates.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Organization> getDLMStates() {
		LOGGER.trace("Entering the getDLMStates() method for FCS Reset");
		List<Organization> dlmStates = null;
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAbbreviatedName("DLM");
		if(assessmentProgram!= null) {
			 dlmStates = organizationAnnualResetsService.getStatesBasedOnAssmntProgramIdFCSResetFlag(assessmentProgram.getId()); 	
		}
		 
        
        LOGGER.trace("Leaving the getDLMStates() method for FCS Reset");
        return dlmStates;        
    }
	
	
}
