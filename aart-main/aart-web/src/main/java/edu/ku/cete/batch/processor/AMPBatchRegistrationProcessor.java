 package edu.ku.cete.batch.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

public class AMPBatchRegistrationProcessor extends BatchRegistrationProcessor {
	
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
		itemAttributeList.add("onscreenKeyboard");
		itemAttributeList.add("Spoken");
		itemAttributeList.add("Braille");
		itemAttributeList.add("Signing"); //US16830
		List<StudentProfileItemAttributeDTO> pnpAttribuites = studentProfileService.getStudentProfileItemAttribute(enrollment.getStudentId(), itemAttributeList);
		itemAttributeList.clear();
		itemAttributeList.add("supportsTwoSwitch");
		itemAttributeList.add("SpokenSourcePreference");
		itemAttributeList.add("UserSpokenPreference");
		itemAttributeList.add("preferenceSubject");
		itemAttributeList.add("paperAndPencil");//Alternate Form - Paper and Pencil
		itemAttributeList.add("largePrintBooklet");//Alternate Form - Large print booklet
		itemAttributeList.add("SigningType"); //US16830
		pnpAttribuites.addAll(studentProfileService.getStudentProfileItemContainer(enrollment.getStudentId(), itemAttributeList));
		Map<String, String> pnpAttributeMap = new HashMap<String, String>();
		for(StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites){
			if(pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0)
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), "false");
			else
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), pnpAttribute.getSelectedValue());

		}
		
		//if PNP settings found for Braille or Large Print or Paper/Pencil, skip this student as test will be taken via Questar.
		if((pnpAttributeMap.get("Braille") != null && pnpAttributeMap.get("Braille").equalsIgnoreCase("true")) ||
		   (pnpAttributeMap.get("paperAndPencil") != null && pnpAttributeMap.get("paperAndPencil").equalsIgnoreCase("true")) ||
		   (pnpAttributeMap.get("largePrintBooklet") != null && pnpAttributeMap.get("largePrintBooklet").equalsIgnoreCase("true"))){
			throw new SkipBatchException("Found PNP setting for Braille or Large Print or Paper/Pencil. Skipping this student. ");
		}
			
		return pnpConditionReadAloudSwitch(pnpAttributeMap);
	}
	
	private Set<String> pnpConditionReadAloudSwitch(Map<String, String> pnpAttributeMap){
		logger.debug("--> pnpConditionReadAloudSwitch" );
		Set<String> accessibilityFlag = new HashSet<String>();
		if(!pnpAttributeMap.isEmpty()){
			boolean spokenFlag = pnpAttributeMap.get("Spoken") != null && pnpAttributeMap.get("Spoken").equalsIgnoreCase("true");
			boolean userSpokenPreferenceFlag = pnpAttributeMap.get("UserSpokenPreference") != null && pnpAttributeMap.get("UserSpokenPreference").equalsIgnoreCase("textandgraphics");
			boolean preferenceSubjectMathSciAndEla = pnpAttributeMap.get("preferenceSubject") != null && pnpAttributeMap.get("preferenceSubject").equalsIgnoreCase("math_science_and_ELA");
			boolean preferenceSubjectMathSciOnly = pnpAttributeMap.get("preferenceSubject") != null && pnpAttributeMap.get("preferenceSubject").equalsIgnoreCase("math_and_science");
			boolean onscreenKeyboard = pnpAttributeMap.get("onscreenKeyboard") != null && pnpAttributeMap.get("onscreenKeyboard").equalsIgnoreCase("true");
			boolean supportsTwoSwitch = pnpAttributeMap.get("supportsTwoSwitch") != null && pnpAttributeMap.get("supportsTwoSwitch").equalsIgnoreCase("true");
			
			//US16830
			boolean signingFlag = pnpAttributeMap.get("Signing") != null && pnpAttributeMap.get("Signing").equalsIgnoreCase("true");
			boolean signingType = pnpAttributeMap.get("SigningType") != null && pnpAttributeMap.get("SigningType").equalsIgnoreCase("asl");
			
			if(contentArea.getAbbreviatedName().equalsIgnoreCase("ELA")){
				if(spokenFlag && userSpokenPreferenceFlag && preferenceSubjectMathSciAndEla){	
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP valid" );	
					accessibilityFlag.add("read_aloud");
				}else{
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP not valid" );	
				}
			}
			
			if(contentArea.getAbbreviatedName().equalsIgnoreCase("M") || contentArea.getAbbreviatedName().equalsIgnoreCase("Sci")){
				if(spokenFlag && userSpokenPreferenceFlag && (preferenceSubjectMathSciAndEla || preferenceSubjectMathSciOnly)){	
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP valid" );	
					accessibilityFlag.add("read_aloud");
				}else{
					logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP not valid" );	
				}
			}
			
			if(onscreenKeyboard || supportsTwoSwitch){
				accessibilityFlag.add("switch");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP Switch valid" );	
			}else{
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " PNP Switch not valid" );	
			}
			
			//US16830
			if(signingFlag && signingType) {
				accessibilityFlag.add("signed");
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " ASL valid" );	
			}else{
				logger.debug("getBatchRegistrationId() - " + getBatchRegistrationId() + " Subject - " + contentArea.getAbbreviatedName() + " ASL not valid" );	
			}
		}
		
		logger.debug("<-- pnpConditionReadAloudSwitch" );
		return  accessibilityFlag;
	}	 
}
