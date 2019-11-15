//Global variable
var backgroundColourVal = "";
var foregroundColourVal = "";

$(function() {    
	//use this method to add new colors to pallete
	//$.fn.colorPicker.addColors(['000', '000', 'fff', 'fff']);
	$('#color3').colorPicker({pickerDefault: "", colors: ["fff", "87cffd", "f5f2a4", "c5c5c5", "f2c5c5", "bef9c3"], transparency: true});
});

function hideOrDisableDisplayEnhancements(responseData){
	if (responseData.attributeContainerName === 'Magnification'){
		if (responseData.attributeName === 'assignedSupport'){
			if (responseData.viewOption === 'disable'){
				$('#magnificationAssignedSupport :input').attr('disabled', 'disabled');
				$('#magnificationAssignedSupport').addClass('ui-state-disabled');
				$('#magnificationAssignedSupport').parent().hide();
			} else if (responseData.viewOption === 'hide'){
				$('#magnificationAssignedSupport').hide();
			}
		} else if (responseData.attributeName === 'activateByDefault'){
			if (responseData.viewOption == 'disable'){
				$('#magnificationActivateByDefault :input').attr('disabled', 'disabled');
				$('#magnificationActivateByDefault').addClass('ui-state-disabled');
				$('#magnificationActivateByDefault').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#magnificationActivateByDefault').hide();
			}
		} else if (responseData.attributeName === 'magnification'){
			if (responseData.viewOption === 'disable'){
				$('#magnificationMagnification :input').attr('disabled', 'disabled');
				$('#magnificationMagnification').addClass('ui-state-disabled');
				$('#magnificationMagnification').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#magnificationMagnification').hide();
			}
		}
	} else if (responseData.attributeContainerName === 'Masking'){
		if (responseData.attributeName === 'assignedSupport'){
			if (responseData.viewOption === 'disable'){
				$('#maskingAssignedSupport :input').attr('disabled', 'disabled');
				$('#maskingAssignedSupport').addClass('ui-state-disabled');
				$('#maskingAssignedSupport').parent().hide();
			} else if (responseData.viewOption === 'hide'){
				$('#maskingAssignedSupport').hide();
			}
		} else if (responseData.attributeName === 'activateByDefault'){
			if (responseData.viewOption == 'disable'){
				$('#maskingActivateByDefault :input').attr('disabled', 'disabled');
				$('#maskingActivateByDefault').addClass('ui-state-disabled');
				$('#maskingActivateByDefault').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#maskingActivateByDefault').hide();
			}
		} else if (responseData.attributeName === 'MaskingType'){
			if (responseData.viewOption === 'disable'){
				$('#maskingMaskingType :input').attr('disabled', 'disabled');
				$('#maskingMaskingType').addClass('ui-state-disabled');
				$('#maskingMaskingType').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#maskingMaskingType').hide();
			}
		}
	} else if (responseData.attributeContainerName === 'ColourOverlay'){
		if (responseData.attributeName === 'assignedSupport'){
			if (responseData.viewOption === 'disable'){
				$('#colourOverlayAssignedSupport :input').attr('disabled', 'disabled');
				$('#colourOverlayAssignedSupport').addClass('ui-state-disabled');
				$('#colourOverlayAssignedSupport').parent().hide();
			} else if (responseData.viewOption === 'hide'){
				$('#colourOverlayAssignedSupport').hide();
			}	
		}else if (responseData.attributeName === 'activateByDefault'){
			if (responseData.viewOption === 'disable'){
				$('#colourOverlayActivateByDefault :input').attr('disabled', 'disabled');
				$('#colourOverlayActivateByDefault').addClass('ui-state-disabled');
				$('#colourOverlayActivateByDefault').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#colourOverlayActivateByDefault').hide();
			}	
		}else if (responseData.attributeName === 'colour'){
			if (responseData.viewOption === 'disable'){
				$('#colourOverlayColour :input').attr('disabled', 'disabled');
				$('#colourOverlayColour').addClass('ui-state-disabled');
				$('#colourOverlayColour').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#colourOverlayColour').hide();
			}	
		}
	}  
	else if (responseData.attributeContainerName === 'BackgroundColour'){
		if (responseData.attributeName === 'assignedSupport'){
			if (responseData.viewOption === 'disable'){
				$('#backgroundColourAssignedSupport :input').attr('disabled', 'disabled');
				$('#backgroundColourAssignedSupport').addClass('ui-state-disabled');
				$('#backgroundColourAssignedSupport').parent().hide();
			} else if (responseData.viewOption === 'hide'){
				$('#backgroundColourAssignedSupport').hide();
			}
		}else if (responseData.attributeName === 'activateByDefault'){
			if (responseData.viewOption === 'disable'){
				$('#backgroundColourActivateByDefault :input').attr('disabled', 'disabled');
				$('#backgroundColourActivateByDefault').addClass('ui-state-disabled');
				$('#backgroundColourActivateByDefault').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#backgroundColourActivateByDefault').hide();
			}
		}else if (responseData.attributeName === 'colour'){
			if (responseData.viewOption === 'disable'){
				$('#contrastColor :input').attr('disabled', 'disabled');
				$('#contrastColor').addClass('ui-state-disabled');
				$('#contrastColor').hide();
			} else if (responseData.viewOption === 'hide'){
				$('#contrastColor').hide();
			}
		}
	}  
	else if (responseData.attributeContainerName === 'InvertColourChoice'){
		if (responseData.attributeName === 'assignedSupport'){
			if (responseData.viewOption === 'disable'){
				$('#invertColourChoiceAssignedSupport :input').attr('disabled', 'disabled');
				$('#invertColourChoiceAssignedSupport').addClass('ui-state-disabled');
				$('#invertColourChoiceAssignedSupport').parent().hide();
			} else if (responseData.viewOption === 'hide'){
				$('#invertColourChoiceAssignedSupport').hide();
			}
		}else if (responseData.attributeName === 'activateByDefault'){
			if (responseData.viewOption === 'disable'){
				$('#invertColourChoiceActivateByDefault :input').attr('disabled', 'disabled');
				$('#invertColourChoiceActivateByDefault').addClass('ui-state-disabled');
				$('#invertColourChoiceActivateByDefault').parent().hide();
			} else if (responseData.viewOption === 'hide'){
				$('#invertColourChoiceActivateByDefault').hide();
			}
		}
	}  
}


function loadDisplayEnhancements(current, responseData) {
	var checkboxValue = false;

	if(responseData.selectedValue.toLowerCase() == "true") 
		checkboxValue = true;
	else if(responseData.selectedValue.toLowerCase() == "false") 
		checkboxValue = false;
	
	var currentClass = $(current).attr('class').toLowerCase();
	
	if(currentClass.indexOf(("magnificationAssignedSupport").toLowerCase()) == 0) {
		$('.magnificationAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.magnificationActivateByDefault').attr("disabled" , true);
			$('.magnificationMagnification').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("magnificationActivateByDefault").toLowerCase()) == 0) {
		$('.magnificationActivateByDefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("magnificationMagnification").toLowerCase()) == 0) {
		$(".magnificationMagnification").find("option").each(function(){
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).attr('selected','selected');
			}
	    });
	} else if(currentClass.indexOf(("maskingAssignedSupport").toLowerCase()) == 0) {
		$('.maskingAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.maskingActivateByDefault').attr("disabled" , true);
			$('.maskingMaskingType').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("maskingActivateByDefault").toLowerCase()) == 0) {
		$('.maskingActivateByDefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("maskingMaskingType").toLowerCase()) == 0) {
		$('input[name=maskingMaskingType]').each(function() {
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				 $(this).prop("checked", true);
			}
		});
	} else if(currentClass.indexOf(("colourOverlayAssignedSupport").toLowerCase()) == 0) {
		$('.colourOverlayAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.colourOverlayActivateByDefault').attr("disabled" , true);
			$('.color3').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("colourOverlayActivateByDefault").toLowerCase()) == 0) {
		$('.colourOverlayActivateByDefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("ColourOverlaycolour").toLowerCase()) == 0) {
		$('.ColourOverlaycolour').val(responseData.selectedValue);
		$('.colorPicker-picker').css('background-color',responseData.selectedValue);
	} else if(currentClass.indexOf(("backgroundColourAssignedSupport").toLowerCase()) == 0) {
		$('.backgroundColourAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.backgroundColourActivateByDefault').attr("disabled" , true);				
		}
	} else if(currentClass.indexOf(("backgroundColourActivateByDefault").toLowerCase()) == 0) {
		$('.backgroundColourActivateByDefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("backgroundColourColour").toLowerCase()) == 0) {
		$('.backgroundColourColour').val(responseData.selectedValue);
		backgroundColourVal = responseData.selectedValue;
	} else if(currentClass.indexOf(("foregroundColourColour").toLowerCase()) == 0) {
		$('.foregroundColourColour').val(responseData.selectedValue);
		foregroundColourVal = responseData.selectedValue;
	} else if(currentClass.indexOf(("invertColourChoiceAssignedSupport").toLowerCase()) == 0) {
		$('.invertColourChoiceAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.invertColourChoiceActivateByDefault').attr("disabled" , true);
		}
	} else if(currentClass.indexOf(("invertColourChoiceActivateByDefault").toLowerCase()) == 0) {
		$('.invertColourChoiceActivateByDefault').prop('checked',checkboxValue);
	}
	
	var selectedbgcolor = "";
	var selectedcolor = "";
	
	if(backgroundColourVal != "" && foregroundColourVal != "") {
		$('.contrastColorDiv').each(function() {
			
			selectedbgcolor = $(this).css('background-color');
			selectedcolor = $(this).css('color');
			
			if(selectedbgcolor.indexOf (("rgb")) == 0)
				selectedbgcolor = getHexa(selectedbgcolor);
			if(selectedcolor.indexOf(("rgb")) == 0)
				selectedcolor = getHexa(selectedcolor);								

			if(selectedbgcolor == backgroundColourVal &&
					selectedcolor == foregroundColourVal) {
				$(this).css('border-color','red');
			}
		});
		backgroundColourVal = "";
		foregroundColourVal = "";
	}
	
}


$('.magnificationAssignedSupport').on("click",function() {
	
	if($('.magnificationAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.magnificationActivateByDefault').attr("disabled" , true);
			$('.magnificationActivateByDefault').prop('checked',true);
		}
		else{
			if($('#magnificationActivateByDefault').hasClass('ui-state-disabled')){
				$('.magnificationActivateByDefault').attr("disabled" , true);	
			}else{
				$('.magnificationActivateByDefault').attr("disabled" , false);	
			}
			$('.magnificationActivateByDefault').prop('checked',false);
		}
		if($('#magnificationMagnification').hasClass('ui-state-disabled')){
			$('.magnificationMagnification').attr("disabled" , true);	
		}else{
			$('.magnificationMagnification').attr("disabled" , false);	
		}
	} else {
		$('.magnificationActivateByDefault').prop('checked',false);
		$('.magnificationMagnification option').removeAttr('selected');
		
		$('.magnificationActivateByDefault').attr("disabled" , true);
		$('.magnificationMagnification').attr("disabled" , true);
	}
});

$('.maskingAssignedSupport').on("click",function() {
	
	if($('.maskingAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.maskingActivateByDefault').attr("disabled" , true);
			$('.maskingActivateByDefault').prop('checked',true);
		} else{
			if($('#maskingActivateByDefault').hasClass('ui-state-disabled')){
				$('.maskingActivateByDefault').attr("disabled" , false);	
			}else{
				$('.maskingActivateByDefault').attr("disabled" , false);	
			}
			$('.maskingActivateByDefault').prop('checked',false);
		}
		if($('#maskingMaskingType').hasClass('ui-state-disabled')){
			$('.maskingMaskingType').attr("disabled" , true);	
		}else{
			$('.maskingMaskingType').attr("disabled" , false);	
		}
		$('input[name=maskingMaskingType]').each(function() {
			if($(this).val().toLowerCase() == "answermask")
				$(this).prop("checked", true);
		});
	} else {
		$('.maskingActivateByDefault').prop('checked',false);
		$('input[name=maskingMaskingType]').each(function() {
			 $(this).prop("checked", false);
		});
		
		$('.maskingActivateByDefault').attr("disabled" , true);
		$('.maskingMaskingType').attr("disabled" , true);
	}
});

$('.colourOverlayAssignedSupport').on("click",function() {
	
	if($('.colourOverlayAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.colourOverlayActivateByDefault').attr("disabled" , true);
			$('.colourOverlayActivateByDefault').prop('checked',true);
		} else {
			if($('#colourOverlayActivateByDefault').hasClass('ui-state-disabled')){
				$('.colourOverlayActivateByDefault').attr("disabled" , true);	
			}else{
				$('.colourOverlayActivateByDefault').attr("disabled" , false);	
			}
			$('.colourOverlayActivateByDefault').prop('checked',false);
		}
		$('.color3').attr("disabled" , false);
		$('.ColourOverlaycolour').val('#ffffff');
		$('.colorPicker-picker').css('background-color','#ffffff');
		
	} else {
		$('.colourOverlayActivateByDefault').prop('checked',false);
		$('.magnificationMagnification option').removeAttr('selected');
		$('.ColourOverlaycolour').val('');
		$('.colorPicker-picker').css('background-color','');
		
		$('.colourOverlayActivateByDefault').attr("disabled" , true);
		$('.color3').attr("disabled" , true);
	}
});

$('.backgroundColourAssignedSupport').on("click",function() {

	var selectedbgcolor = "";
	var selectedcolor = "";
	
	if($('.backgroundColourAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.backgroundColourActivateByDefault').attr("disabled" , true);
			$('.backgroundColourActivateByDefault').prop('checked',true);
		} else {
			if($('#backgroundColourActivateByDefault').hasClass('ui-state-disabled')){
				$('.backgroundColourActivateByDefault').attr("disabled" , true);	
			}else{
				$('.backgroundColourActivateByDefault').attr("disabled" , false);	
			}
			$('.backgroundColourActivateByDefault').prop('checked',false);
		}
		$('.backgroundColourColour').val('#000000');
		$('.foregroundColourColour').val('#999999');
		
		$('.contrastColorDiv').each(function() {
			
			selectedbgcolor = $(this).css('background-color');
			selectedcolor = $(this).css('color');
			
			if(selectedbgcolor.indexOf (("rgb")) == 0)
				selectedbgcolor = getHexa(selectedbgcolor);
			if(selectedcolor.indexOf(("rgb")) == 0)
				selectedcolor = getHexa(selectedcolor);
			
			if(selectedbgcolor == "#ffffff" &&
					selectedcolor == "#000000") {
				$(this).css('border-color','red');
			}
		});
		
	} else {
		$('.backgroundColourActivateByDefault').prop('checked',false);
		$('.backgroundColourColour').val('');
		$('.foregroundColourColour').val('');
		
		$('.contrastColorDiv').each(function() {
			$(this).css('border-color','');
		});

		$('.backgroundColourActivateByDefault').attr("disabled" , true);
	}
});

$('.contrastColorDiv').on("click",function() {
	if($('.backgroundColourAssignedSupport').is(':checked')) {			
		$('.contrastColorDiv').each(function() {
			$(this).css('border-color','');
		});
		$(this).css('border-color','red');
		
		var selectedbgcolor = $(this).css('background-color');
		var selectedcolor = $(this).css('color');
		
		if(selectedbgcolor.indexOf (("rgb")) == 0)
			selectedbgcolor = getHexa(selectedbgcolor);
		if(selectedcolor.indexOf(("rgb")) == 0)
			selectedcolor = getHexa(selectedcolor);
		
		$('.backgroundColourColour').val(selectedbgcolor);
		$('.foregroundColourColour').val(selectedcolor);
	}
});

$('.invertColourChoiceAssignedSupport').on("click",function() {
	
	if($('.invertColourChoiceAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.invertColourChoiceActivateByDefault').attr("disabled" , true);
			$('.invertColourChoiceActivateByDefault').prop('checked',true);			
		} else {
			if($('#invertColourChoiceActivateByDefault').hasClass('ui-state-disabled')){
				$('.invertColourChoiceActivateByDefault').attr("disabled" , true);	
			}else{
				$('.invertColourChoiceActivateByDefault').attr("disabled" , false);	
			}
			$('.invertColourChoiceActivateByDefault').prop('checked',false);		
		}
		$('.ColourOverlaycolour').attr("disabled" , false);
	} else {
		$('.invertColourChoiceActivateByDefault').prop('checked',false);			

		$('.invertColourChoiceActivateByDefault').attr("disabled" , true);			
	}
});



function getHexa(colorval) {
    var parts = colorval.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
    var color = '';
    
    delete(parts[0]);
    for (var i = 1; i <= 3; ++i) {
        parts[i] = parseInt(parts[i]).toString(16);
        if (parts[i].length == 1) parts[i] = '0' + parts[i];
    }
    color = '#' + parts.join('');
    
    return color;
}


$('.save').on("click",function(event) {
	var buttons = $('#accessProfileDiv .save').attr('disabled', 'disabled').addClass('ui-state-disabled');
	// Initialize table and pass to other functions and update it. (return value should be status of validation)
	var hashtable = {};
	validValue = contructDisplayEnhancementsRequestData(hashtable);
	// using AND operation on validValue so other page's validations won't override.
	validValue = validValue & contructLanguageBrailleRequestData(hashtable);
	validValue = validValue & contructAudioEnvRequestData(hashtable);
	validValue = validValue & contructSystemIndependentRequestData(hashtable);
	var studentProfileItemAttributeData = hashtable;
	if (validValue){
		$.ajax({
			url: 'saveStudentProfileItemAttributeData.htm',
			//data: "studentProfileItemAttributeData=" + JSON.stringify(studentProfileItemAttributeData) +  "&studentId=" + studentId,
			data: studentProfileItemAttributeData,
			dataType: 'json',
			type: "POST",
	    	complete: function(jqxhr, textStatus){
	    		buttons.removeAttr('disabled').removeClass('ui-state-disabled');
	    	}
		}).done(function(response) {
			if(response) {					
        		$(".successMessage").show();
        		setTimeout("aart.clearMessages()", 5000);
        		ATSUtil.dialogUnlockExitChangesSaved($('#accessProfileDiv'));
			}
    	});
	} else {
		buttons.removeAttr('disabled').removeClass('ui-state-disabled');
	}
 });


function contructDisplayEnhancementsRequestData(hashtable) {			
	
	var maskingMaskingTypeValue = "";
	
	hashtable[$('.magnificationAssignedSupport', $('#displayEnhancementsContent')).data('id')] = $('.magnificationAssignedSupport', $('#displayEnhancementsContent')).is(':checked');
	hashtable[$('.magnificationActivateByDefault', $('#displayEnhancementsContent')).data('id')] = $('.magnificationActivateByDefault', $('#displayEnhancementsContent')).is(':checked');
	
	if($('.magnificationMagnification').is(':visible')){
	hashtable[$('.magnificationMagnification', $('#displayEnhancementsContent')).data('id')] = $('.magnificationMagnification', $('#displayEnhancementsContent')).val();
	}
	
	hashtable[$('.maskingAssignedSupport', $('#displayEnhancementsContent')).data('id')] = $('.maskingAssignedSupport', $('#displayEnhancementsContent')).is(':checked');
	hashtable[$('.maskingActivateByDefault', $('#displayEnhancementsContent')).data('id')] = $('.maskingActivateByDefault', $('#displayEnhancementsContent')).is(':checked');
	if($('.maskingMaskingType:checked', $('#displayEnhancementsContent')).val() != undefined) {
		maskingMaskingTypeValue = $('.maskingMaskingType:checked', $('#displayEnhancementsContent')).val();
	}
	if($('.maskingMaskingType').is(':visible')){
	hashtable[$('.maskingMaskingType', $('#displayEnhancementsContent')).data('id')] = maskingMaskingTypeValue;
	}
	hashtable[$('.colourOverlayAssignedSupport', $('#displayEnhancementsContent')).data('id')] = $('.colourOverlayAssignedSupport', $('#displayEnhancementsContent')).is(':checked');
	hashtable[$('.colourOverlayActivateByDefault', $('#displayEnhancementsContent')).data('id')] = $('.colourOverlayActivateByDefault', $('#displayEnhancementsContent')).is(':checked');
	if($('.ColourOverlaycolour').is(':visible')){
	hashtable[$('.ColourOverlaycolour', $('#displayEnhancementsContent')).data('id')] = $('.ColourOverlaycolour', $('#displayEnhancementsContent')).val();
	}
	hashtable[$('.backgroundColourAssignedSupport', $('#displayEnhancementsContent')).data('id')] = $('.backgroundColourAssignedSupport', $('#displayEnhancementsContent')).is(':checked');
	hashtable[$('.backgroundColourActivateByDefault', $('#displayEnhancementsContent')).data('id')] = $('.backgroundColourActivateByDefault', $('#displayEnhancementsContent')).is(':checked');
	if($('.backgroundColourColour').is(':visible')){
	hashtable[$('.backgroundColourColour', $('#displayEnhancementsContent')).data('id')] = $('.backgroundColourColour', $('#displayEnhancementsContent')).val();
	}
	hashtable[$('.foregroundColourAssignedSupport', $('#displayEnhancementsContent')).data('id')] = $('.backgroundColourAssignedSupport', $('#displayEnhancementsContent')).is(':checked');
	hashtable[$('.foregroundColourActivateByDefault', $('#displayEnhancementsContent')).data('id')] = $('.backgroundColourActivateByDefault', $('#displayEnhancementsContent')).is(':checked');
	if($('.foregroundColourColour').is(':visible')){
	hashtable[$('.foregroundColourColour', $('#displayEnhancementsContent')).data('id')] = $('.foregroundColourColour', $('#displayEnhancementsContent')).val();
	}
	hashtable[$('.invertColourChoiceAssignedSupport', $('#displayEnhancementsContent')).data('id')] = $('.invertColourChoiceAssignedSupport', $('#displayEnhancementsContent')).is(':checked');
	hashtable[$('.invertColourChoiceActivateByDefault', $('#displayEnhancementsContent')).data('id')] = $('.invertColourChoiceActivateByDefault', $('#displayEnhancementsContent')).is(':checked');
	
	hashtable['studentId'] = $('#studentIdPNP').val();
	
	// no validation returning true here.
	return true;
}