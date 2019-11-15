//Global variable
var validValue = true;
function loadAudioEnvironment(current, responseData) {
	//alert(JSON.stringify(responseData));
	//alert(responseData.selectedValue);
	var checkboxValue = false;

	if(responseData.selectedValue.toLowerCase() == "true") 
		checkboxValue = true;
	else if(responseData.selectedValue.toLowerCase() == "false") 
		checkboxValue = false;
	
	var currentClass = $(current).attr('class').toLowerCase();

	if(currentClass.indexOf(("auditoryBackgroundAssignedSupport").toLowerCase()) == 0) {
		$('.auditoryBackgroundAssignedSupport').prop('checked',checkboxValue);			
		if(checkboxValue) {
			$('.auditoryBackgroundActivateByDefault').attr("disabled" , true);
		}
		
	} else if(currentClass.indexOf(("auditoryBackgroundActivateByDefault").toLowerCase()) == 0) {
		$('.auditoryBackgroundActivateByDefault').prop('checked',checkboxValue);
		
	}  else if(currentClass.indexOf(("breaksAssignedSupport").toLowerCase()) == 0) {
		$('.breaksAssignedSupport').prop('checked',checkboxValue);
		
	} else if(currentClass.indexOf(("additionalTestingTimeAssignedSupport").toLowerCase()) == 0) {
		$('.additionalTestingTimeAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.additionalTestingTimeActivateByDefault').attr("disabled" , true);
			$('.additionalTestingTimeTimeMultiplierSelection').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("additionalTestingTimeActivateByDefault").toLowerCase()) == 0) {
		$('.additionalTestingTimeActivateByDefault').prop('checked',checkboxValue);
		
	} else if(currentClass.indexOf(("additionalTestingTimeTimeMultiplierSelection").toLowerCase()) == 0) {
		$('input[name=timeAssignedSupport]').each(function() {
			if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
				$(this).prop("checked", true);
			} else if($.isNumeric(responseData.selectedValue)){
				$('.additionalTestingTimeTimeMultiplier').val(responseData.selectedValue);
				$(this).prop("checked", true);
				
				$('.additionalTestingTimeTimeMultiplier').attr("disabled" , false);
			}
		});
	} else if(currentClass.indexOf(("spokenAssignedSupport").toLowerCase()) == 0) {
		$('.spokenAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			if(($('#studentAssessmentProgramCode').val().indexOf('AMP') !== -1) 
				|| ($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1)){
				$('.spokenActivateByDefault').attr("disabled" , true);
			} else {
				$('.spokenActivateByDefault').attr("disabled" , true);
			}
			$('.spokenAudio').not('.disable_human,.disable_textonly,.disable_graphicsonly,.disable_nonvisual,.disable_spokenReadAtStartPreference').attr("disabled" , false);
		} else {
			$('.spokenAudio').each(function() {
				$(this).attr("disabled" , true);
			});
		}
		
	} else if(currentClass.indexOf(("spokenActivateByDefault").toLowerCase()) == 0) {
		$('.spokenActivateByDefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("spokenSpokenSourcePreference").toLowerCase()) == 0) {
		$('input[name=voiceSource]').each(function() {
			if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
				$(this).prop("checked", true);
				//$('.spokenSpokenSourcePreference').attr("disabled" , false);
				$('.syntheticSpokenSource').prop("checked", true);
				$('.syntheticSpokenSource').attr("disabled" , false);
				$('.humanSpokenSource').addClass('ui-state-disabled');
				$('.humanSpokenSource').attr("disabled" , "disabled");
			}
		});			
	} else if(currentClass.indexOf(("spokenReadAtStartPreference").toLowerCase()) == 0) {
		$('input[name=readAtStart]').each(function() {
			if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
				$(this).prop("checked", true);
				
				$('.spokenReadAtStartPreference').attr("disabled" , false);
			}
		});
	} else if(currentClass.indexOf(("spokenUserSpokenPreference").toLowerCase()) == 0) {
		$('input[name=spokenPreference]').each(function() {
			if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
				$(this).prop("checked", true);
				//$('.spokenUserSpokenPreference').attr("disabled" , false);
			} 
		});						
	} else if(currentClass.indexOf(("spokenDirectionsOnly").toLowerCase()) == 0) {
		$('input[name=audioDirections]').each(function() {
			if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
				$(this).prop("checked", true);
				if(($('#studentAssessmentProgramCode').val().indexOf('AMP') !== -1) 
					|| ($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1)){
					$('.spokenDirectionsOnly').attr("disabled" , true);
				} else {
					$('.spokenDirectionsOnly').attr("disabled" , false);
				}
				
			} 
		});
	} else if(currentClass.indexOf(("spokenPreferenceSubject").toLowerCase()) == 0) {
		if (responseData.selectedValue.length > 0){
			$('input.spokenPreferenceSubject').each(function(){
				if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
					$(this).prop("checked", true);
				}
			});
		}
	} else if(currentClass.indexOf(("onscreenKeyboardAssignedSupport").toLowerCase()) == 0) {
		$('.onscreenKeyboardAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.onscreenKeyboardActivateByDefault').attr("disabled" , true);
			$('.onscreenKeyboardAutomaticScanInitialDelaySelection').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("onscreenKeyboardActivateByDefault").toLowerCase()) == 0) {
		$('.onscreenKeyboardActivateByDefault').prop('checked',checkboxValue);
			
	} else if(currentClass.indexOf(("onscreenKeyboardScanSpeed").toLowerCase()) == 0) {
		if(responseData.selectedValue.length > 0) {
			$('.onscreenKeyboardScanSpeed').val(responseData.selectedValue);
			$('.onscreenKeyboardScanSpeed').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("onscreenKeyboardAutomaticScanInitialDelaySelection").toLowerCase()) == 0) {
		$('input[name=timeAutomaticScan]').each(function() {
			if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
				$(this).prop("checked", true);
			} else if($.isNumeric(responseData.selectedValue) &&
					$(this).val() == "valueInSeconds"){
				$('.onscreenKeyboardAutomaticScanInitialDelay').val(responseData.selectedValue);
				$(this).prop("checked", true);
				$('.onscreenKeyboardAutomaticScanInitialDelay').attr("disabled" , false);
			}
		});
	} else if(currentClass.indexOf(("onscreenKeyboardAutomaticScanRepeat").toLowerCase()) == 0) {
		$('input[name=automaticScanRepeat]').each(function() {
			if($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {					
				$(this).prop("checked", true);
				$('.onscreenKeyboardAutomaticScanRepeat').attr("disabled" , false);
			} 
		});
	}
}

$('#valueInSeconds').on("click",function(){
	$('.onscreenKeyboardAutomaticScanInitialDelay').val("5");
});

$('.auditoryBackgroundAssignedSupport').on("click",function() {
	
	if($('.auditoryBackgroundAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.auditoryBackgroundActivateByDefault').attr("disabled" , true);
			$('.auditoryBackgroundActivateByDefault').prop('checked',true);
		}else{
			if($('#auditoryBackgroundActivateByDefault').hasClass('ui-state-disabled')){
				$('.auditoryBackgroundActivateByDefault').prop('disabled',true);
			}else{
				$('.auditoryBackgroundActivateByDefault').attr("disabled" , false);
			}
			$('.auditoryBackgroundActivateByDefault').prop('checked',false);
		}
		
	} else {
		$('.auditoryBackgroundActivateByDefault').prop('checked',false);
		$('.auditoryBackgroundActivateByDefault').attr("disabled" , true);
	}
});

$('#specifytimemultiplier').on("click",function(){
	$('.additionalTestingTimeTimeMultiplier').val("1.5");
});
$('.additionalTestingTimeAssignedSupport').on("click",function() {
	if($('.additionalTestingTimeAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.additionalTestingTimeActivateByDefault').attr("disabled" , true);
			$('.additionalTestingTimeActivateByDefault').prop('checked',true);	
		}else {
			$('.additionalTestingTimeActivateByDefault').attr("checked" , false);
			if($('#additionalTestingTimeActivateByDefault').hasClass('ui-state-disabled')){
				$('.additionalTestingTimeActivateByDefault').prop('disabled',true);
			}else{
				$('.additionalTestingTimeActivateByDefault').prop('disabled',false);
			}
				
		}
		if($('#additionalTestingTimeTimeMultiplierSelection').hasClass('ui-state-disabled')){
			$('.additionalTestingTimeTimeMultiplierSelection').attr("disabled" , true);
		}else{
			$('.additionalTestingTimeTimeMultiplierSelection').attr("disabled" , false);
		}
		$('input[name=timeAssignedSupport]').each(function() {
			if($(this).val() == "specifytimemultiplier") { 
				$(this).prop("checked", true);
			}
		});
		
		$('.additionalTestingTimeTimeMultiplier').val("1.5");
		$('.additionalTestingTimeTimeMultiplier').attr("disabled" , false);
		
	} else {
		$('.additionalTestingTimeActivateByDefault').prop('checked',false);
		$('input[name=timeAssignedSupport]').each(function() {
			 $(this).prop("checked", false);
		});
		$('.additionalTestingTimeTimeMultiplier').val("");
		
		$('.additionalTestingTimeActivateByDefault').attr("disabled" , true);
		$('.additionalTestingTimeTimeMultiplierSelection').attr("disabled" , true);
	}
});

$('.additionalTestingTimeTimeMultiplierSelection').on("click",function() {
	if($('.additionalTestingTimeTimeMultiplierSelection:checked').val() != "unlimited") {
		$('.additionalTestingTimeTimeMultiplier').attr("disabled" , false);
	} else {
		$('.additionalTestingTimeTimeMultiplier').val("");
		
		$('.additionalTestingTimeTimeMultiplier').attr("disabled" , true);
	}
});


$('.spokenAssignedSupport').on("click",function() {
	if($('.spokenAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.spokenActivateByDefault').prop('checked', true);
			$('.spokenActivateByDefault').attr("disabled" , true);
		}else{
			if($('#spokenActivateByDefault').hasClass('ui-state-disabled')){
				$('.spokenActivateByDefault').attr("disabled" , true);
			}else{
				$('.spokenActivateByDefault').attr("disabled" , false);
			}
			$('.spokenActivateByDefault').prop('checked',false);
		}
		if ($('.spokenActivateByDefault').attr("disabled") == "disabled"){
			$('.spokenAudio').not('.disable_human,.disable_textonly,.disable_graphicsonly,.disable_nonvisual,.disable_spokenReadAtStartPreference').attr("disabled" , false);
			$('input.spokenAudio[type="radio"]').each(function() {
				var amp = $('#studentAssessmentProgramCode').val().indexOf('AMP') !== -1; 
				var dlm = $('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1;
				
				var val = $(this).val();
				var name = $(this).attr('name');
				var check = false;
				if ($.inArray(val, ['synthetic', 'false', 'textandgraphics']) > -1){
					// AMP and DLM don't want these autopopulated, apparently
					if ((amp || dlm) && ($.inArray(name, ['readAtStart', 'audioDirections']) > -1)){
						check = false;
					} else {
						check = true;
					}
				} else {
					check = false;
				}
				$(this).prop('checked', check);
			});
		}else{
			$('.spokenActivateByDefault').attr("disabled" , false);			
			$('.spokenAudio').not('.disable_human,.disable_textonly,.disable_graphicsonly,.disable_nonvisual,.disable_spokenReadAtStartPreference').attr("disabled" , false);
			$('input.spokenAudio[type="radio"]').each(function() {
				if ($.inArray($(this).val(), ['synthetic', 'false', 'textandgraphics']) > -1){
					$(this).prop("checked", true);
				}
			});
		}
	} else {
		$('.spokenActivateByDefault').prop('checked',false);
		$('input.spokenAudio[type="radio"]').each(function() {
			 $(this).prop("checked", false);
		});
		
		$('.spokenActivateByDefault').attr("disabled" , true);
		$('.spokenAudio').not('.disable_human,.disable_textonly,.disable_graphicsonly,.disable_nonvisual,.disable_spokenReadAtStartPreference').attr("disabled" , true);
	}
});


$('.onscreenKeyboardAssignedSupport').on("click",function() {
	
	if($('.onscreenKeyboardAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.onscreenKeyboardActivateByDefault').attr("disabled" , true);
			$('.onscreenKeyboardActivateByDefault').prop('checked',true);	
		}else{
			if($('#onscreenKeyboardActivateByDefault').hasClass('ui-state-disabled')){
				$('.onscreenKeyboardActivateByDefault').attr("disabled" , true);
			}else{
				$('.onscreenKeyboardActivateByDefault').attr("disabled" , false);
			}
			$('.onscreenKeyboardActivateByDefault').prop('checked',false);
		}
		$('.breaks').attr("disabled" , false);
		 $('input.breaks[type="radio"]').each(function() {
			if($(this).val() == "valueInSeconds" || $(this).val() == "infinity")
				$(this).prop("checked", true);
		});
		
		 $('.onscreenKeyboardAutomaticScanInitialDelay').val("5");
		$('.onscreenKeyboardScanSpeed').val("2");
		
	} else {
		$('.onscreenKeyboardActivateByDefault').prop('checked',false);
		$('input.breaks[type="radio"]').each(function() {
			 $(this).prop("checked", false);
		});
		$('.onscreenKeyboardAutomaticScanInitialDelay').val("");
		$('.onscreenKeyboardScanSpeed').val("");
		
		
		$('.onscreenKeyboardActivateByDefault').attr("disabled" , true);
		$('.breaks').attr("disabled" , true);
		$('.onscreenKeyboardAutomaticScanInitialDelay').attr("disabled" , true);
		$('.onscreenKeyboardScanSpeed').attr("disabled" , true);
	}
});

$('.onscreenKeyboardAutomaticScanInitialDelaySelection').on("click",function() {
	if($('.onscreenKeyboardAutomaticScanInitialDelaySelection:checked').val() != "manual") {
		$('.onscreenKeyboardAutomaticScanInitialDelay').attr("disabled" , false);
	} else {
		$('.onscreenKeyboardAutomaticScanInitialDelay').val("");
		
		$('.onscreenKeyboardAutomaticScanInitialDelay').attr("disabled" , true);
	}
});


function contructAudioEnvRequestData(hashtable) {
	var selectedValue = ""; 
	
	hashtable[$('.auditoryBackgroundAssignedSupport').data('id')] = $('.auditoryBackgroundAssignedSupport').is(':checked');
	hashtable[$('.auditoryBackgroundActivateByDefault').data('id')] = $('.auditoryBackgroundActivateByDefault').is(':checked');

	hashtable[$('.breaksAssignedSupport').data('id')] = $('.breaksAssignedSupport').is(':checked');

	hashtable[$('.additionalTestingTimeAssignedSupport').data('id')] = $('.additionalTestingTimeAssignedSupport').is(':checked');
	hashtable[$('.additionalTestingTimeActivateByDefault').data('id')] = $('.additionalTestingTimeActivateByDefault').is(':checked');

	if($('.additionalTestingTimeTimeMultiplierSelection:checked').val() != undefined) {
		selectedValue = $('.additionalTestingTimeTimeMultiplierSelection:checked').val();
	}
	if(selectedValue == "unlimited") {
		selectedValue = "unlimited";
	} else if(selectedValue == "specifytimemultiplier") {
		selectedValue = $('.additionalTestingTimeTimeMultiplier').val();
		if(!$.isNumeric(selectedValue) || selectedValue <= 1) {
			$('.additionalTestingTimeTimeMultiplier').val("");
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight});
			$('.showTimeMultiplierError').show();
    		setTimeout("aart.clearMessages()", 5000);
			return false;
		}
	}
	if($('.additionalTestingTimeTimeMultiplier').is(':visible')){
	hashtable[$('.additionalTestingTimeTimeMultiplier').data('id')] = selectedValue;
	}
	
	hashtable[$('.spokenAssignedSupport').data('id')] = $('.spokenAssignedSupport').is(':checked');
	hashtable[$('.spokenActivateByDefault').data('id')] = $('.spokenActivateByDefault').is(':checked');

	selectedValue = "";
	if($('.spokenSpokenSourcePreference:checked').val() != undefined) {
		selectedValue = $('.spokenSpokenSourcePreference:checked').val();
	}
	if($('.spokenSpokenSourcePreference').is(':visible')){
	hashtable[$('.spokenSpokenSourcePreference').data('id')] = selectedValue;
	}
	selectedValue = "";
	if($('.spokenReadAtStartPreference:checked').val() != undefined) {
		selectedValue = $('.spokenReadAtStartPreference:checked').val();
	}
	if($('.spokenReadAtStartPreference').is(':visible')){
	hashtable[$('.spokenReadAtStartPreference').data('id')] = selectedValue;
	}
	selectedValue = "";
	if($('.spokenUserSpokenPreference:checked').val() != undefined) {
		selectedValue = $('.spokenUserSpokenPreference:checked').val();
	}
	if($('.spokenUserSpokenPreference').is(':visible')){
	hashtable[$('.spokenUserSpokenPreference').data('id')] = selectedValue;
	}
	selectedValue = "";
	if($('.spokenDirectionsOnly:checked').val() != undefined) {
		selectedValue = $('.spokenDirectionsOnly:checked').val();
	}
	var dlm = $('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1;
	if(!dlm) {
		if($('.spokenDirectionsOnly').is(':visible')){
		hashtable[$('.spokenDirectionsOnly').data('id')] = selectedValue;
		}
	}
	
	selectedValue = "";
	if (hashtable[$('.spokenAssignedSupport').data('id')] && // ONLY validate if Spoken is checked
			!$('#spokenSubject').hasClass('hidden_spokenSubject')){ // we have to manually check if they're available
		if ($('.spokenPreferenceSubject:checked').val() != undefined){
			selectedValue = $('.spokenPreferenceSubject:checked').val();
			if($('.spokenPreferenceSubject').is(':visible')){
			hashtable[$('.spokenPreferenceSubject:checked').data('id')] = selectedValue;
			}
		} else {
			if(selectedValue === "") {
				$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight});
				$('.showSpokenSubjectError').show();
        		setTimeout("aart.clearMessages()", 5000);
				return false;
			}
		}
	} else {
		if($('.spokenPreferenceSubject').is(':visible')){
		hashtable[$('.spokenPreferenceSubject').eq(0).data('id')] = selectedValue;
		}
	}

	hashtable[$('.onscreenKeyboardAssignedSupport').data('id')] = $('.onscreenKeyboardAssignedSupport').is(':checked');
	hashtable[$('.onscreenKeyboardActivateByDefault').data('id')] = $('.onscreenKeyboardActivateByDefault').is(':checked');
	
	selectedValue = "";
	if($('.onscreenKeyboardAssignedSupport').is(':checked')) {
		selectedValue = $('.onscreenKeyboardScanSpeed').val();
		if(!$.isNumeric(selectedValue) || selectedValue < 1) {
			 $('.onscreenKeyboardScanSpeed').val("");
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight});
			$('.showScanSpeedError').show();
    		setTimeout("aart.clearMessages()", 5000);
			return false;
		}
	}
	if($('.onscreenKeyboardScanSpeed').is(':visible')){
	hashtable[$('.onscreenKeyboardScanSpeed').data('id')] = selectedValue;
	}
	
	selectedValue = "";
	if($('.onscreenKeyboardAutomaticScanInitialDelaySelection:checked').val() != undefined) {
		selectedValue = $('.onscreenKeyboardAutomaticScanInitialDelaySelection:checked').val();
	}
	if(selectedValue == "manual") {
		selectedValue = "manual";
	} else if(selectedValue == "valueInSeconds") {
		selectedValue = $('.onscreenKeyboardAutomaticScanInitialDelay').val();
		if(!$.isNumeric(selectedValue) || selectedValue < 0 ||
				parseInt(selectedValue,10) < parseInt($('.onscreenKeyboardScanSpeed').val(),10)) {
			$('.onscreenKeyboardAutomaticScanInitialDelay').val("");
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight});
			$('.showAutomaticScanInitialDelayError').show();
    		setTimeout("aart.clearMessages()", 5000);
			return false;
		}
	}
	if($('.onscreenKeyboardAutomaticScanInitialDelay').is(':visible')){
	hashtable[$('.onscreenKeyboardAutomaticScanInitialDelay').data('id')] = selectedValue;
	}

	selectedValue = "";
	if($('.onscreenKeyboardAutomaticScanRepeat:checked').val() != undefined) {
		selectedValue = $('.onscreenKeyboardAutomaticScanRepeat:checked').val();
	}
	if($('.onscreenKeyboardAutomaticScanRepeat').is(':visible')){
	hashtable[$('.onscreenKeyboardAutomaticScanRepeat').data('id')] = selectedValue;
	}
	
	hashtable['studentId'] = $('#studentIdPNP').val();
	
	// all validations are completed and valid here.
	return true;
}

function hideOrDisableAudioEnvironment(responseData){
	if (responseData.attributeContainerName =='AuditoryBackground'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#auditoryBackgroundAssignedSupport :input').attr( "disabled", "disabled");
			$('#auditoryBackgroundAssignedSupport').addClass('ui-state-disabled');
			$('#auditoryBackgroundAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#auditoryBackgroundAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='AuditoryBackground'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#auditoryBackgroundActivateByDefault :input').attr( "disabled", "disabled");
			$('#auditoryBackgroundActivateByDefault').addClass('ui-state-disabled');
			$('#auditoryBackgroundActivateByDefault').hide();
		} else if(responseData.viewOption == '' || responseData.viewOption == 'enable'){
			$('#auditoryBackgroundActivateByDefault :input').attr( "disabled", true);
		} else if (responseData.viewOption == 'hide'){
			$('#auditoryBackgroundActivateByDefault').hide();
		}
	}	
	if (responseData.attributeContainerName =='breaks'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#breaksAssignedSupport :input').attr( "disabled", "disabled");
			$('#breaksAssignedSupport').addClass('ui-state-disabled');
			$('#breaksAssignedSupport').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#breaksAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='AdditionalTestingTime'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#additionalTestingTimeAssignedSupport :input').attr( "disabled", "disabled");
			$('#additionalTestingTimeAssignedSupport').addClass('ui-state-disabled');
			$('#additionalTestingTimeAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#additionalTestingTimeAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='AdditionalTestingTime'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#additionalTestingTimeActivateByDefault :input').attr( "disabled", "disabled");
			$('#additionalTestingTimeActivateByDefault').addClass('ui-state-disabled');
			$('#additionalTestingTimeActivateByDefault').hide();
		} else if(responseData.viewOption == '' || responseData.viewOption == 'enable'){
			$('#additionalTestingTimeActivateByDefault :input').attr( "disabled", true);
		} else if (responseData.viewOption == 'hide'){
			$('#additionalTestingTimeActivateByDefault').hide();
		}
	}	
	if (responseData.attributeContainerName =='AdditionalTestingTime'
		&& responseData.attributeName == 'TimeMultiplier'){
		if (responseData.viewOption == 'disable'){
			$('#additionalTestingTimeTimeMultiplierSelection :input').attr( "disabled", "disabled");
			$('#additionalTestingTimeTimeMultiplierSelection').addClass('ui-state-disabled');
			$('#additionalTestingTimeTimeMultiplierSelection').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#additionalTestingTimeTimeMultiplierSelection').hide();
		}
	}
	if (responseData.attributeContainerName =='Spoken'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#spokenAssignedSupport :input').attr( "disabled", "disabled");
			$('#spokenAssignedSupport').addClass('ui-state-disabled');
			$('#spokenAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#spokenAssignedSupport').hide();
		}
	}	
	
	if (responseData.attributeContainerName =='Spoken'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#spokenActivateByDefault :input').attr( "disabled", "disabled");
			$('#spokenActivateByDefault').addClass('ui-state-disabled');
			$('#spokenActivateByDefault').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#spokenActivateByDefault').hide();
		}
	}	
	if (responseData.attributeContainerName =='Spoken'
		&& responseData.attributeName == 'ReadAtStartPreference'){
		if (responseData.viewOption == 'disable'){
			$('#spokenReadAtStartPreference :input').attr( "disabled", "disabled");
			$('#spokenReadAtStartPreference').addClass('ui-state-disabled');
			$('#spokenReadAtStartPreference :input').addClass('disable_spokenReadAtStartPreference');
			$('#spokenReadAtStartPreference').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#spokenReadAtStartPreference').hide();
		}
	}
	if (responseData.attributeContainerName == 'Spoken'
		&& responseData.attributeName == 'directionsOnly'){
		if (responseData.viewOption == 'disable'){
			$('#spokenDirectionsOnly :input').attr('disabled', 'disabled').addClass('disable_spokenDirectionsOnly');
			$('#spokenDirectionsOnly').addClass('ui-state-disabled');
			$('#spokenDirectionsOnly').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#spokenDirectionsOnly').hide();
		} 
	}
	if (responseData.attributeContainerName == 'Spoken'
		&& responseData.attributeName == 'preferenceSubject'){
		if (responseData.viewOption == 'disable'){
			$('#spokenSubject :input').attr('disabled', 'disabled').addClass('disable_spokenPreferenceSubject');
			$('#spokenSubject').addClass('ui-state-disabled');
			$('#spokenSubject').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#spokenSubject').addClass('hidden_spokenSubject').hide(); // need the class for checking later
		}
	}
	
	//these are special cases for human spoken preference, 
	//text only user spoken preference, and graphics only user spoken preference
	if (responseData.attributeContainerName =='Spoken'
		&& responseData.attributeName == 'SpokenSourcePreference'
			&& responseData.viewOption != null){
		splitAndDisableOrHideAudio(responseData);
	}	
	if (responseData.attributeContainerName =='Spoken'
		&& responseData.attributeName == 'UserSpokenPreference'
			&& responseData.viewOption != null){
		splitAndDisableOrHideAudio(responseData);
	}
	
	if (responseData.attributeContainerName =='onscreenKeyboard'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#onscreenKeyboardAssignedSupport :input').attr( "disabled", "disabled");
			$('#onscreenKeyboardAssignedSupport').addClass('ui-state-disabled');
			$('#onscreenKeyboardAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#onscreenKeyboardAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='onscreenKeyboard'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#onscreenKeyboardActivateByDefault :input').attr( "disabled", "disabled");
			$('#onscreenKeyboardActivateByDefault').addClass('ui-state-disabled');
			$('#onscreenKeyboardActivateByDefault').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#onscreenKeyboardActivateByDefault').hide();
		}
	}
	if (responseData.attributeContainerName =='onscreenKeyboard'
		&& responseData.attributeName == 'scanSpeed'){
		if (responseData.viewOption == 'disable'){
			$('#onscreenKeyboardScanSpeedDiv :input').attr( "disabled", "disabled");
			$('#onscreenKeyboardScanSpeedDiv').addClass('ui-state-disabled');
			$('#onscreenKeyboardScanSpeedDiv').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#onscreenKeyboardScanSpeedDiv').hide();
		}
	}
	if (responseData.attributeContainerName =='onscreenKeyboard'
		&& responseData.attributeName == 'automaticScanInitialDelay'){
		if (responseData.viewOption == 'disable'){
			$('#onscreenKeyboardAutomaticScanInitialDelay :input').attr( "disabled", "disabled");
			$('#onscreenKeyboardAutomaticScanInitialDelay').addClass('ui-state-disabled');
			$('#onscreenKeyboardAutomaticScanInitialDelay').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#onscreenKeyboardAutomaticScanInitialDelay').hide();
		}
	}
	if (responseData.attributeContainerName =='onscreenKeyboard'
		&& responseData.attributeName == 'automaticScanRepeat'){
		if (responseData.viewOption == 'disable'){
			$('#onscreenKeyboardAutomaticScanRepeat :input').attr( "disabled", "disabled");
			$('#onscreenKeyboardAutomaticScanRepeat').addClass('ui-state-disabled');
			$('#onscreenKeyboardAutomaticScanRepeat').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#onscreenKeyboardAutomaticScanRepeat').hide();
		}
	}
}

function splitAndDisableOrHideAudio(responseData){
	//split the options by comma
	var splitOptions = responseData.viewOption.split(',');
	for (var i=0; i < splitOptions.length; i++){
		var optionAndValue = splitOptions[i];
		//split by underscore to get the hide or disable and the input id related
		var splitOptionAndValue = optionAndValue.split('_');
		//hide or disable
		var option = splitOptionAndValue[0];
		//input id
		var value = splitOptionAndValue[1];
		//currently the business rules for this tab are to only hide or show
		//but coded it to handle both - except the header needs to be modified for 
		//disabling
		if (option =='disable'){
			$('#'+value+' :input').attr( "disabled", "disabled");
			$('#'+value).addClass('ui-state-disabled');
			$('#'+value).hide();
		} else if (option == 'hide'){
			$('#'+value).hide();
		}
		$('#'+value+' :input').addClass(optionAndValue);
	}
}