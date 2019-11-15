package edu.ku.cete.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.PnpStateSettings;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ProfileAttributeContainerService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

/**
 * @author vittaly
 *
 */

@Controller
public class AccessProfileController {

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(AccessProfileController.class);
	
	/**
     * studentProfileService.
     */
    @Autowired
	private StudentProfileService studentProfileService;
    
    @Autowired
	private OrganizationService organizationService;
    
    @Autowired
	private ProfileAttributeContainerService profileAttributeContainerService;
    
    /**
 	 * @param studentId
 	 * @return
 	 */
 	@RequestMapping(value = "viewAccessProfile.htm", method = RequestMethod.GET)
     public final ModelAndView viewAccessProfile(final @RequestParam Long studentId,
       		final @RequestParam String stateStudentIdentifier,final @RequestParam String studentInfo, 
       		final @RequestParam String previewAccessProfile, final @RequestParam String assessmentProgramCode) {
 		LOGGER.trace("Entering viewAccessProfile method");
 		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
 		List<StudentProfileItemAttributeDTO> studentProfileItemAttributeValues = loadStudentProfileItemAttributeDataSection(studentId);
 		String fgcolor = "";
 		String bgcolor = "";
 		String attributeName="assignedSupport";
 		String viewOption="enable";
 		boolean previewAccessProfileFlag = true;
 		boolean previewAccessProfileDisplayEnhancementsFlag = false;
 		boolean previewAccessProfileLanguageAndBrailleFlag = false;
 		boolean previewAccessProfileAudioAndEnvironmentSetupFlag = false;
 		boolean previewAccessProfileOtherSupportsFlag = false;
 		for(StudentProfileItemAttributeDTO studentProfileItemAttributeValue : studentProfileItemAttributeValues){
 			if((studentProfileItemAttributeValue.getAttributeContainerName()).equals("ForegroundColour")){
 				if((studentProfileItemAttributeValue.getAttributeName()).equals("colour")){
 					LOGGER.debug("ContainerName()  :"+studentProfileItemAttributeValue.getAttributeName()+" getSelectedValue() "+studentProfileItemAttributeValue.getSelectedValue());
 					fgcolor=studentProfileItemAttributeValue.getSelectedValue();
 				}
 			}
 			if((studentProfileItemAttributeValue.getAttributeContainerName()).equals("BackgroundColour")){
 				if((studentProfileItemAttributeValue.getAttributeName()).equals("colour")){
 					 bgcolor=studentProfileItemAttributeValue.getSelectedValue();
 				}
 			}
 			}
 		List<StudentProfileItemAttributeDTO> studentProfileItemAttributeEnableDTO = studentProfileService.selectStudentAttributesAndContainersByAttributeName(studentId, user,attributeName,viewOption);
 		for(StudentProfileItemAttributeDTO studentProfileItemAttribute:studentProfileItemAttributeEnableDTO)
 		{
 			if(studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("Magnification") ||
 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("ColourOverlay") ||
 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("InvertColourChoice") ||
 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("Masking") ||
 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("BackgroundColour")){
 				previewAccessProfileDisplayEnhancementsFlag=true;
 			}
 			
 			if(studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("itemTranslationDisplay") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("Signing") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("Braille") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("keywordTranslationDisplay") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("Tactile")){
 				previewAccessProfileLanguageAndBrailleFlag=true;
 	 		}
 			
 			if(studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("AuditoryBackground") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("Spoken") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("onscreenKeyboard") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("breaks") ||
 	 			   studentProfileItemAttribute.getAttributeContainerName().trim().equalsIgnoreCase("AdditionalTestingTime")){
 					previewAccessProfileAudioAndEnvironmentSetupFlag=true;
 	 			}
 		}
 		attributeName=null;
 		viewOption="show";
 		List<StudentProfileItemAttributeDTO> studentProfileItemAttributeShowDTO = studentProfileService.selectStudentAttributesAndContainersByAttributeName(studentId, user,attributeName,viewOption);
  		if(!studentProfileItemAttributeShowDTO.isEmpty()) {
  			previewAccessProfileOtherSupportsFlag=true;
  		}
 		
 		if(previewAccessProfile.equals("preview")){
 			previewAccessProfileFlag = false;
 		}
     	ModelAndView mav = new ModelAndView("accessProfile");
     	mav.addObject("studentInfo",studentInfo);
     	mav.addObject("fgcolor",fgcolor);
     	mav.addObject("bgcolor",bgcolor);
     	mav.addObject("studentId",studentId);
     	mav.addObject("previewAccessProfileFlag",previewAccessProfileFlag);
     	mav.addObject("assessmentProgramCode", assessmentProgramCode);
     	mav.addObject("studentProfileItemAttributeValues",studentProfileItemAttributeValues);
     	mav.addObject("previewAccessProfileDisplayEnhancementsFlag",previewAccessProfileDisplayEnhancementsFlag);
     	mav.addObject("previewAccessProfileLanguageAndBrailleFlag",previewAccessProfileLanguageAndBrailleFlag);
     	mav.addObject("previewAccessProfileAudioAndEnvironmentSetupFlag",previewAccessProfileAudioAndEnvironmentSetupFlag);
     	mav.addObject("previewAccessProfileOtherSupportsFlag",previewAccessProfileOtherSupportsFlag);
     	LOGGER.trace("Leaving viewAccessProfile method");
         return mav;
     }

	/**
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "loadStudentProfileItemAttributeData.htm", method = RequestMethod.GET)
    public @ResponseBody List<StudentProfileItemAttributeDTO> loadStudentProfileItemAttributeData(final @RequestParam Long studentId) {
		LOGGER.trace("Entering loadStudentProfileItemAttributeData method");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		List<StudentProfileItemAttributeDTO> studentProfileItemAttributeDTO = studentProfileService.selectStudentAttributesAndContainers(studentId, user);
		
		String brailleFileType = profileAttributeContainerService
				.getPnpStateSettingsByState(user.getCurrentAssessmentProgramName(), getUserStateId());
		
		
		
		for (StudentProfileItemAttributeDTO entry : studentProfileItemAttributeDTO){
			entry.setBrailleFileType(brailleFileType);
		}
		
    	LOGGER.trace("Leaving loadStudentProfileItemAttributeData method");
        return studentProfileItemAttributeDTO;
    }
	
	public Long getUserStateId() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long organizationId = userDetails.getUser().getCurrentOrganizationId();
        List<Organization> userOrgHierarchy = new ArrayList<Organization>();
        userOrgHierarchy = organizationService.getAllParents(organizationId);
        userOrgHierarchy.add(organizationService.get(organizationId));
        Long stateId = null;
        for(Organization org : userOrgHierarchy){
        	if(org.getOrganizationType().getTypeCode().equals("ST")){
        		stateId = org.getId();
        	}
        }
		return stateId;
	}
    
	/**
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "loadStudentProfileItemAttributeDataSection.htm", method = RequestMethod.GET)
    public @ResponseBody List<StudentProfileItemAttributeDTO> loadStudentProfileItemAttributeDataSection(final @RequestParam Long studentId) {
		LOGGER.trace("Entering loadStudentProfileItemAttributeData method");
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		List<StudentProfileItemAttributeDTO> studentProfileItemAttributeDTO = studentProfileService.selectStudentAttributesAndContainersSection(studentId, user);
		
    	LOGGER.trace("Leaving loadStudentProfileItemAttributeData method");
        return studentProfileItemAttributeDTO;
    }
	/**
	 * @param requestHashtable
	 * @return
	 */
	@RequestMapping(value = "saveStudentProfileItemAttributeData.htm", method = RequestMethod.POST)
    public @ResponseBody Boolean saveStudentProfileItemAttributeData(
    		@RequestParam Map<String, String> studentProfileItemAttributeData) {
		LOGGER.trace("Entering saveStudentProfileItemAttributeData method");
		Boolean success = false;
		
		LOGGER.debug("requestHashtable - " + studentProfileItemAttributeData);
		//Get the studentID from map and remove that key, so that we can iterate the map elements and 
		 // do DB inserts/updates directly.
		Long studentId = Long.parseLong(studentProfileItemAttributeData.get("studentId"));
		studentProfileItemAttributeData.remove("studentId");

		try{
			success = studentProfileService.saveStudentProfileItemAttributes(studentProfileItemAttributeData, studentId);
		} catch(Exception exception){
			LOGGER.error("Exception occured while saving pnp settings for student " + studentId + " : ", exception);
		}
    	LOGGER.trace("Leaving saveStudentProfileItemAttributeData method");
        return success;
    }
	
	
}
