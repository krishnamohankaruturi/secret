
package edu.ku.cete.batch.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

public class KAPBatchRegistrationProcessor extends BatchRegistrationProcessor {
	
	@Autowired
	private StudentProfileService studentProfileService;

	protected List<Test> getTests(Enrollment enrollment, TestCollection testCollection, Set<String> accessibilityFlags) {
		logger.debug("--> getTests" );
		
		List<Test> tests = null;
 		if(CollectionUtils.isEmpty(accessibilityFlags)){
 			tests = testService.findQCTestsByTestCollectionAndStatusAndAccFlags(testCollection.getId(), 
 					testStatusConfiguration.getPublishedTestStatusCategory().getId(), 
 					AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, null, null);
 		}else{
 			tests = testService.findQCTestsByTestCollectionAndStatusAndAccFlags(testCollection.getId(), 
 					testStatusConfiguration.getPublishedTestStatusCategory().getId(), 
 					AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, true, accessibilityFlags);
 		}
		logger.debug("<-- getTests" );
		return tests;
	}

	protected Set<String> getAccessibleFlags(Enrollment enrollment) {
		List<String> itemAttributeList = new ArrayList<String>();
		itemAttributeList.add("keywordTranslationDisplay");
		itemAttributeList.add("onscreenKeyboard"); //Single Switches 
		itemAttributeList.add("Spoken");
		itemAttributeList.add("Braille");
		itemAttributeList.add("Signing");
		itemAttributeList.add("Magnification");
		List<StudentProfileItemAttributeDTO> pnpAttribuites = studentProfileService.getStudentProfileItemAttribute(enrollment.getStudentId(), itemAttributeList);
		itemAttributeList.clear();
		itemAttributeList.add("supportsTwoSwitch");//Two switch system
		itemAttributeList.add("paperAndPencil");//Alternate Form - Paper and Pencil
		itemAttributeList.add("largePrintBooklet");//Alternate Form - Large print booklet
		itemAttributeList.add("SpokenSourcePreference");// Voice Source 
		itemAttributeList.add("UserSpokenPreference");//Spoken Preference 
		itemAttributeList.add("Language");//Language
		itemAttributeList.add("SigningType");
		pnpAttribuites.addAll(studentProfileService.getStudentProfileItemContainer(enrollment.getStudentId(), itemAttributeList));
		Map<String, String> pnpAttributeMap = new HashMap<String, String>();
		for(StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites){
			if(pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0)
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), "false");
			else
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), pnpAttribute.getSelectedValue());

		}
		return pnpConditionCheck(pnpAttributeMap);
	}
	
	private Set<String> pnpConditionCheck(Map<String, String> pnpAttributeMap){
		logger.debug("--> pnpConditionCheck" );
		Set<String> accessibilityFlag = new HashSet<String>();
		if(!pnpAttributeMap.isEmpty()){
			
			boolean language = pnpAttributeMap.get("Language") != null && pnpAttributeMap.get("Language").equalsIgnoreCase("spa");
			boolean keywordTranslationDisplay = pnpAttributeMap.get("keywordTranslationDisplay") != null && pnpAttributeMap.get("keywordTranslationDisplay").equalsIgnoreCase("true");
			boolean spoken = pnpAttributeMap.get("Spoken") != null && pnpAttributeMap.get("Spoken").equalsIgnoreCase("true");
			boolean onscreenKeyboard = pnpAttributeMap.get("onscreenKeyboard") != null && pnpAttributeMap.get("onscreenKeyboard").equalsIgnoreCase("true");
			boolean supportsTwoSwitch = pnpAttributeMap.get("supportsTwoSwitch") != null && pnpAttributeMap.get("supportsTwoSwitch").equalsIgnoreCase("true");
			boolean paperAndPencil = pnpAttributeMap.get("paperAndPencil") != null && pnpAttributeMap.get("paperAndPencil").equalsIgnoreCase("true");
			boolean largePrintBooklet = pnpAttributeMap.get("largePrintBooklet") != null && pnpAttributeMap.get("largePrintBooklet").equalsIgnoreCase("true");
			boolean braille = pnpAttributeMap.get("Braille") != null && pnpAttributeMap.get("Braille").equalsIgnoreCase("true");
			boolean signing = "true".equalsIgnoreCase(pnpAttributeMap.get("Signing"));
			boolean signingIsASL = "asl".equalsIgnoreCase(pnpAttributeMap.get("SigningType"));
			boolean magnification = "true".equalsIgnoreCase(pnpAttributeMap.get("Magnification"));
			boolean userSpokenPreference = pnpAttributeMap.get("UserSpokenPreference") != null && (pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("nonvisual") || 
					pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("textandgraphics"));
			//Students With Spanish Accommodation
			//Students With Spanish and Spoken Accommodations
			//For ONLY the subjects of Math and Science
			if(language && keywordTranslationDisplay){
				if(contentArea.getAbbreviatedName().equalsIgnoreCase("M") || contentArea.getAbbreviatedName().equalsIgnoreCase("Sci")){
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " 'spanish' PNP valid" );	
					accessibilityFlag.add("spanish");
					  
					//Spoken = true
					if(spoken){	
						logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " 'Spoken' PNP valid" );	
						accessibilityFlag.add("read_aloud");
					} 
				}
			}
			
			//Students With Switch Accommodations
			if(onscreenKeyboard || supportsTwoSwitch){
				accessibilityFlag.add("switch");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Switch' valid" );	
				
				//Students With Switch and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Switch', PNP 'Spoken' valid" );	
				}		
			}
			
			//Students With Paper Pencil Accommodations
			if(paperAndPencil){
				accessibilityFlag.add("paper");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'paperAndPencil' valid" );	
				
				//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - paperAndPencil - PNP 'Spoken' valid" );	
				}		
			}
			
			//Students With Large Print Accommodations
			if(largePrintBooklet || magnification){
				accessibilityFlag.add("large_print");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'largePrintBooklet' or 'magnification' valid" );	
				
				//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - largePrintBooklet or magnification - PNP 'Spoken' valid" );	
				}
			}
			
			//Students With Braille Accommodations
			if(braille){
				accessibilityFlag.add("braille");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Braille' valid" );	
				
				//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - Braille - PNP 'Spoken' valid" );	
				}		
			}
			
			//Students With Only Spoken Accommodations
			if(spoken){
				if(userSpokenPreference){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Spoken', UserSpokenPreference - textandgraphics or nonvisual valid" );	
				}
			}
			
			//US16879, US16900
			if (signing && signingIsASL) {
				accessibilityFlag.add("signed");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP 'Signing' valid" );	
				
				/*//Students With Paper Pencil Accommodations and Spoken Accommodations
				if(spoken){
					accessibilityFlag.add("read_aloud");
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP - Signing - PNP 'Spoken' valid" );	
				}*/
			}
		}
		return  accessibilityFlag;
	}
}
