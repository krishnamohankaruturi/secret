var	hideSupportsProvidedOutsideSystem = true;

var otherSupportsContainerSizeMap = {settingBlock:{size: 1, hidden: 0}, 
		alternateFormBlock: {size: 3, hidden: 0}, 
		presentationBlock: {size: 3, hidden: 0}, 
		additionalToolsBlock: {size: 5, hidden: 0},
		responseBlock: {size: 3, hidden: 0}, 
		outsideSystemBlock: {size: 6, hidden: 0} };

function loadSystemIndependent(current, responseData) {
	var checkboxValue = false;

	if(responseData.selectedValue.toLowerCase() == "true") 
		checkboxValue = true;
	else if(responseData.selectedValue.toLowerCase() == "false") 
		checkboxValue = false;
	
	var currentClass = ($(current).prop('class').toLowerCase());
	
	if(currentClass.indexOf(("settingseparateQuiteSetting").toLowerCase()) == 0) {		
		$('.setting').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("presentationsomeotheraccommodation").toLowerCase()) == 0) {		
			$('.presentationsomeotheraccommodation').prop('checked',checkboxValue);
		}else if(currentClass.indexOf(("presentationreadsAssessmentOutLoud").toLowerCase()) == 0) {		
			$('.presentationreadsAssessmentOutLoud').prop('checked',checkboxValue);
		}else if(currentClass.indexOf(("presentationuseTranslationsDictionary").toLowerCase()) == 0) {		
			$('.presentationuseTranslationsDictionary').prop('checked',checkboxValue);
		} else if(currentClass.indexOf(("responsedictated").toLowerCase()) == 0) {		
			$('.responsedictated').prop('checked',checkboxValue);
		}else if(currentClass.indexOf(("responseusedCommunicationDevice").toLowerCase()) == 0) {		
			$('.responseusedCommunicationDevice').prop('checked',checkboxValue);
		}else if(currentClass.indexOf(("responsesignedResponses").toLowerCase()) == 0) {		
			$('.responsesignedResponses').prop('checked',checkboxValue);
		} else if(currentClass.indexOf(("supportsRequiringAdditionalToolsSupportsTwoSwitch").toLowerCase()) == 0) {
			$('.supportsRequiringAdditionalToolsSupportsTwoSwitch').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsRequiringAdditionalToolsSupportsAdminIpad").toLowerCase()) == 0) {
			$('.supportsRequiringAdditionalToolsSupportsAdminIpad').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsRequiringAdditionalToolsSupportsAdaptiveEquip").toLowerCase()) == 0) {
			$('.supportsRequiringAdditionalToolsSupportsAdaptiveEquip').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsRequiringAdditionalToolsSupportsIndividualizedManipulatives").toLowerCase()) == 0) {
			$('.supportsRequiringAdditionalToolsSupportsIndividualizedManipulatives').prop('checked',checkboxValue);			
		}
		
		else if(currentClass.indexOf(("supportsRequiringAdditionalToolsSupportsCalculator").toLowerCase()) == 0) {
            $('.supportsRequiringAdditionalToolsSupportsCalculator').prop('checked',checkboxValue);            
        } 	
	
		else if(currentClass.indexOf(("supportsProvidedOutsideSystemSupportsHumanReadAloud").toLowerCase()) == 0) {
			$('.supportsProvidedOutsideSystemSupportsHumanReadAloud').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsProvidedOutsideSystemSupportsSignInterpretation").toLowerCase()) == 0) {
			$('.supportsProvidedOutsideSystemSupportsSignInterpretation').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsProvidedOutsideSystemSupportsLanguageTranslation").toLowerCase()) == 0) {
			$('.supportsProvidedOutsideSystemSupportsLanguageTranslation').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsProvidedOutsideSystemSupportsTestAdminEnteredResponses").toLowerCase()) == 0) {
			$('.supportsProvidedOutsideSystemSupportsTestAdminEnteredResponses').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsProvidedOutsideSystemSupportsPartnerAssistedScanning").toLowerCase()) == 0) {
			$('.supportsProvidedOutsideSystemSupportsPartnerAssistedScanning').prop('checked',checkboxValue);			
		}
	
	
	
		else if(currentClass.indexOf(("supportsProvidedOutsideSystemSupportsStudentProvidedAccommodations").toLowerCase()) == 0) {
			$('.supportsProvidedOutsideSystemSupportsStudentProvidedAccommodations').prop('checked',checkboxValue);			
		}
		
		
		
		else if(currentClass.indexOf(("supportsProvidedByAlternateFormVisualImpairment").toLowerCase()) == 0) {
			$('.supportsProvidedByAlternateFormVisualImpairment').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsProvidedByAlternateFormLargePrintBooklet").toLowerCase()) == 0) {
			$('.supportsProvidedByAlternateFormLargePrintBooklet').prop('checked',checkboxValue);			
		} else if(currentClass.indexOf(("supportsProvidedByAlternateFormPaperAndPencil").toLowerCase()) == 0) {
			$('.supportsProvidedByAlternateFormPaperAndPencil').prop('checked',checkboxValue);			
		} 
}


function contructSystemIndependentRequestData(hashtable) {				
   hashtable['studentId'] = $('#studentIdPNP').val();
	
	hashtable[$('.settingseparateQuiteSetting').data('id')] = $('.setting').is(':checked');
	
	hashtable[$('.presentationsomeotheraccommodation').data('id')] = $('.presentationsomeotheraccommodation').is(':checked');
	hashtable[$('.presentationreadsAssessmentOutLoud').data('id')] = $('.presentationreadsAssessmentOutLoud').is(':checked');
	hashtable[$('.presentationuseTranslationsDictionary').data('id')] = $('.presentationuseTranslationsDictionary').is(':checked');
	
	hashtable[$('.responsedictated').data('id')] = $('.responsedictated').is(':checked');
	hashtable[$('.responseusedCommunicationDevice').data('id')] = $('.responseusedCommunicationDevice').is(':checked');
	hashtable[$('.responsesignedResponses').data('id')] = $('.responsesignedResponses').is(':checked');
	
	hashtable[$('.supportsRequiringAdditionalToolsSupportsTwoSwitch').data('id')] = $('.supportsRequiringAdditionalToolsSupportsTwoSwitch').is(':checked');
	hashtable[$('.supportsRequiringAdditionalToolsSupportsAdminIpad').data('id')] = $('.supportsRequiringAdditionalToolsSupportsAdminIpad').is(':checked');
	hashtable[$('.supportsRequiringAdditionalToolsSupportsAdaptiveEquip').data('id')] = $('.supportsRequiringAdditionalToolsSupportsAdaptiveEquip').is(':checked');
	hashtable[$('.supportsRequiringAdditionalToolsSupportsIndividualizedManipulatives').data('id')] = $('.supportsRequiringAdditionalToolsSupportsIndividualizedManipulatives').is(':checked');
	hashtable[$('.supportsRequiringAdditionalToolsSupportsCalculator').data('id')] = $('.supportsRequiringAdditionalToolsSupportsCalculator').is(':checked'); 
	
	hashtable[$('.supportsProvidedOutsideSystemSupportsHumanReadAloud').data('id')] = $('.supportsProvidedOutsideSystemSupportsHumanReadAloud').is(':checked');
	hashtable[$('.supportsProvidedOutsideSystemSupportsSignInterpretation').data('id')] = $('.supportsProvidedOutsideSystemSupportsSignInterpretation').is(':checked');
	hashtable[$('.supportsProvidedOutsideSystemSupportsLanguageTranslation').data('id')] = $('.supportsProvidedOutsideSystemSupportsLanguageTranslation').is(':checked');
	hashtable[$('.supportsProvidedOutsideSystemSupportsTestAdminEnteredResponses').data('id')] = $('.supportsProvidedOutsideSystemSupportsTestAdminEnteredResponses').is(':checked');
	hashtable[$('.supportsProvidedOutsideSystemSupportsPartnerAssistedScanning').data('id')] = $('.supportsProvidedOutsideSystemSupportsPartnerAssistedScanning').is(':checked');
	
	hashtable[$('.supportsProvidedOutsideSystemSupportsStudentProvidedAccommodations').data('id')] = $('.supportsProvidedOutsideSystemSupportsStudentProvidedAccommodations').is(':checked');

	
	hashtable[$('.supportsProvidedByAlternateFormVisualImpairment').data('id')] = $('.supportsProvidedByAlternateFormVisualImpairment').is(':checked');
	hashtable[$('.supportsProvidedByAlternateFormLargePrintBooklet').data('id')] = $('.supportsProvidedByAlternateFormLargePrintBooklet').is(':checked');
	hashtable[$('.supportsProvidedByAlternateFormPaperAndPencil').data('id')] = $('.supportsProvidedByAlternateFormPaperAndPencil').is(':checked');
	
	// validations are completed
	return true;
}

function hideOrDisableSystemIndependent(responseData){
	//8/26/2014 Craig - I commented out the code and left the data in the db in case they want to disable again.
	if (responseData.attributeContainerName =='supportsRequiringAdditionalTools'
		&& responseData.attributeName == 'supportsTwoSwitch'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.additionalToolsBlock.hidden = otherSupportsContainerSizeMap.additionalToolsBlock.hidden + 1;
			$('#supportsTwoSwitch').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsRequiringAdditionalTools'
		&& responseData.attributeName == 'supportsAdminIpad'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.additionalToolsBlock.hidden = otherSupportsContainerSizeMap.additionalToolsBlock.hidden + 1;
			$('#supportsAdminIpad').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsRequiringAdditionalTools'
		&& responseData.attributeName == 'supportsAdaptiveEquip'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.additionalToolsBlock.hidden = otherSupportsContainerSizeMap.additionalToolsBlock.hidden + 1;
			$('#supportsAdaptiveEquip').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsRequiringAdditionalTools'
		&& responseData.attributeName == 'supportsIndividualizedManipulatives'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.additionalToolsBlock.hidden = otherSupportsContainerSizeMap.additionalToolsBlock.hidden + 1;
			$('#supportsIndividualizedManipulatives').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsRequiringAdditionalTools'
        && responseData.attributeName == 'supportsCalculator'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
        	otherSupportsContainerSizeMap.additionalToolsBlock.hidden = otherSupportsContainerSizeMap.additionalToolsBlock.hidden + 1;
            $('#supportsCalculator').hide();
        }
        
    } 
	if(otherSupportsContainerSizeMap.additionalToolsBlock.size === otherSupportsContainerSizeMap.additionalToolsBlock.hidden){
		$('#additionalToolsBlock').parent().hide();
	}
	
	if (responseData.attributeContainerName =='supportsProvidedOutsideSystem'
		&& responseData.attributeName == 'supportsHumanReadAloud'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.outsideSystemBlock.hidden = otherSupportsContainerSizeMap.outsideSystemBlock.hidden + 1;
			$('#supportsHumanReadAloud').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsProvidedOutsideSystem'
		&& responseData.attributeName == 'supportsSignInterpretation'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.outsideSystemBlock.hidden = otherSupportsContainerSizeMap.outsideSystemBlock.hidden + 1;
			$('#supportsSignInterpretation').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsProvidedOutsideSystem'
		&& responseData.attributeName == 'supportsLanguageTranslation'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.outsideSystemBlock.hidden = otherSupportsContainerSizeMap.outsideSystemBlock.hidden + 1;
			$('#supportsLanguageTranslation').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsProvidedOutsideSystem'
		&& responseData.attributeName == 'supportsTestAdminEnteredResponses'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.outsideSystemBlock.hidden = otherSupportsContainerSizeMap.outsideSystemBlock.hidden + 1;
			$('#supportsTestAdminEnteredResponses').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsProvidedOutsideSystem'
		&& responseData.attributeName == 'supportsPartnerAssistedScanning'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.outsideSystemBlock.hidden = otherSupportsContainerSizeMap.outsideSystemBlock.hidden + 1;
			$('#supportsPartnerAssistedScanning').hide();
		}
	}
	
	if (responseData.attributeContainerName =='supportsProvidedOutsideSystem'
		&& responseData.attributeName == 'supportsStudentProvidedAccommodations'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.outsideSystemBlock.hidden = otherSupportsContainerSizeMap.outsideSystemBlock.hidden + 1;
			$('#supportsStudentProvidedAccommodations').hide();
		}
	}
	if(otherSupportsContainerSizeMap.outsideSystemBlock.size === otherSupportsContainerSizeMap.outsideSystemBlock.hidden){
		$('#outsideSystemBlock').parent().hide();
	}
	
	if (responseData.attributeContainerName =='supportsProvidedByAlternateForm'
		&& responseData.attributeName == 'visualImpairment'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.alternateFormBlock.hidden = otherSupportsContainerSizeMap.alternateFormBlock.hidden + 1;
			$('#visualImpairment').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsProvidedByAlternateForm'
		&& responseData.attributeName == 'largePrintBooklet'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.alternateFormBlock.hidden = otherSupportsContainerSizeMap.alternateFormBlock.hidden + 1;
			$('#largePrintBooklet').hide();
		}
	}
	if (responseData.attributeContainerName =='supportsProvidedByAlternateForm'
		&& responseData.attributeName == 'paperAndPencil'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.alternateFormBlock.hidden = otherSupportsContainerSizeMap.alternateFormBlock.hidden + 1;
			$('#paperAndPencil').hide();
		}
	}	
	if(otherSupportsContainerSizeMap.alternateFormBlock.size === otherSupportsContainerSizeMap.alternateFormBlock.hidden){
		$('#alternateFormBlock').parent().hide();
	}
	
	if (responseData.attributeContainerName =='setting'
		&& responseData.attributeName == 'separateQuiteSetting'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.settingBlock.hidden = otherSupportsContainerSizeMap.settingBlock.hidden + 1;
			$('#quietSettting').hide();
		}
	}
	if(otherSupportsContainerSizeMap.settingBlock.size === otherSupportsContainerSizeMap.settingBlock.hidden){
		$('#settingBlock').parent().hide();
	}
	
	if (responseData.attributeContainerName =='presentation'
		&& responseData.attributeName == 'someotheraccommodation'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.presentationBlock.hidden = otherSupportsContainerSizeMap.presentationBlock.hidden + 1;
			$('#accommodation').hide();
		}
	}
	if (responseData.attributeContainerName =='presentation'
		&& responseData.attributeName == 'readsAssessmentOutLoud'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.presentationBlock.hidden = otherSupportsContainerSizeMap.presentationBlock.hidden + 1;
			$('#assessment').hide();
		}
	}
	if (responseData.attributeContainerName =='presentation'
		&& responseData.attributeName == 'useTranslationsDictionary'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.presentationBlock.hidden = otherSupportsContainerSizeMap.presentationBlock.hidden + 1;
			$('#translations').hide();
		}
	}
	if(otherSupportsContainerSizeMap.presentationBlock.size === otherSupportsContainerSizeMap.presentationBlock.hidden){
		$('#presentationBlock').parent().hide();
	}
	
	if (responseData.attributeContainerName =='response'
		&& responseData.attributeName == 'dictated'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.responseBlock.hidden = otherSupportsContainerSizeMap.responseBlock.hidden + 1;
			$('#dictated').hide();
		}
	}
	if (responseData.attributeContainerName =='response'
		&& responseData.attributeName == 'usedCommunicationDevice'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.responseBlock.hidden = otherSupportsContainerSizeMap.responseBlock.hidden + 1;
			$('#communication').hide();
		}
	}
	if (responseData.attributeContainerName =='response'
		&& responseData.attributeName == 'signedResponses'){
		if (responseData.viewOption == 'disable' || responseData.viewOption == 'hide'){
			otherSupportsContainerSizeMap.responseBlock.hidden = otherSupportsContainerSizeMap.responseBlock.hidden + 1;
			$('#responses').hide();
		}
	}
	if(otherSupportsContainerSizeMap.responseBlock.size === otherSupportsContainerSizeMap.responseBlock.hidden){
		$('#responseBlock').parent().hide();
	}
}
