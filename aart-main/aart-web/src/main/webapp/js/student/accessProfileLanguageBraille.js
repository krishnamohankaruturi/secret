function validateBrailleOptions(){
	if($('#divLanguageBraille').is(':visible')){
	var floor= $.trim($('.brailleNumberOfBrailleCells').val());
	if(!($('.brailleNumberOfBrailleCells').prop('disabled'))){
		if(($('.brailleNumberOfBrailleCells').val() != ' ') && ($('.brailleNumberOfBrailleCells').val() != '')){
			if(!($.isNumeric(floor) )){
				$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
				$(".errorBrailleCells").show();
				$('.brailleNumberOfBrailleCells').val(" ");
				setTimeout("aart.clearMessages()", 5000);
				return false;
			}else if (floor < 1){
				$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
				$(".errorBrailleCells").show();
				$('.brailleNumberOfBrailleCells').val(" ");
				setTimeout("aart.clearMessages()", 5000);
				return false;
			}
			
			if(parseInt($('.brailleNumberOfBrailleCells').val()) <= 0){
				$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
				$(".errorBrailleCells").show();
				$('.brailleNumberOfBrailleCells').val(" ");
				setTimeout("aart.clearMessages()", 5000);
				return false;
			}
		}else{
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
			$(".errorBrailleCells").show();
			$('.brailleNumberOfBrailleCells').val(" ");
			setTimeout("aart.clearMessages()", 5000);
			return false;
		}
	}
	var floor1 = $('.brailleBrailleDotPressure').val();
	if(!($('.brailleBrailleDotPressure').prop('disabled'))){
		if(($('.brailleBrailleDotPressure').val() != ' ') && ($('.brailleBrailleDotPressure').val() != '')){
		if(!($.isNumeric(floor1))){
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
			$(".errorDotPressure").show();
			$('.brailleBrailleDotPressure').val(" ");
			setTimeout("aart.clearMessages()", 5000);
			return false;
		}else if (floor1 <= 0 || floor1 >= 1){
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
			$(".errorDotPressure").show();
			$('.brailleBrailleDotPressure').val(" ");
			setTimeout("aart.clearMessages()", 5000);
			return false;
		}
		if(parseInt($('.brailleBrailleDotPressure').val()) > 0){
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
			$(".errorDotPressure").show();
			$('.brailleBrailleDotPressure').val(" ");
			setTimeout("aart.clearMessages()", 5000);
			return false;
		}
		}else{
			$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
			$(".errorDotPressure").show();
			$('.brailleBrailleDotPressure').val(" ");
			setTimeout("aart.clearMessages()", 5000);
			return false;
		}
	}
	
	var brailleFileTypeSelected = false;
	$('#brailleFileType :input').each(function(){
		if($(this).is(':checked') === true){
			brailleFileTypeSelected = true;
		}else if(!$('#brailleFileType').is(':visible')){
			brailleFileTypeSelected = true;
		}
	});
	if(!brailleFileTypeSelected && $('.brailleAssignedSupport').is(':checked')){
		$('#accessProfileDiv').animate({scrollTop: $('#accessProfileDiv')[0].scrollHeight}, 'slow');
		$(".errorBrailleFileType").show();
		setTimeout("aart.clearMessages()", 5000);
		return false;
	}
	}
	return true;
}

function contructLanguageBrailleRequestData(hashtable) {
	var brailleMarkValues=[];	
	var brailleMarkValue="";
	var valid = validateBrailleOptions();

	if (!valid)
		return valid;
	hashtable[$('.itemTranslationDisplayAssignedSupport').data('id')] = $('.itemTranslationDisplayAssignedSupport').is(':checked');
	hashtable[$('.itemTranslationDisplayActivateBydefault').data('id')] = $('.itemTranslationDisplayActivateBydefault').is(':checked');
	
	if($('.itemTranslationDisplaylanguage').is(':visible')){
		hashtable[$('.itemTranslationDisplaylanguage').data('id')] = $('.itemTranslationDisplaylanguage').val();
	}
	
	hashtable[$('.keywordTranslationDisplayAssignedSupport').data('id')] = $('.keywordTranslationDisplayAssignedSupport').is(':checked');
	hashtable[$('.keywordTranslationDisplayActivateBydefault').data('id')] = $('.keywordTranslationDisplayActivateBydefault').is(':checked');

	if($('.keywordTranslationDisplayLanguage').is(':visible')){
		hashtable[$('.keywordTranslationDisplayLanguage').data('id')] = $('.keywordTranslationDisplayLanguage').val();
	}
	
	hashtable[$('.signingAssignedSupport').data('id')] = $('.signingAssignedSupport').is(':checked');
	hashtable[$('.signingActivateBydefault').data('id')] = $('.signingActivateBydefault').is(':checked');
	
	if($('.signingSigningType').is(':visible')){
	hashtable[$('.signingSigningType').data('id')] = $('.signingSigningType').val();
	}
	
	hashtable[$('.tactileAssignedSupport').data('id')] = $('.tactileAssignedSupport').is(':checked');
	hashtable[$('.tactileActivateBydefault').data('id')] = $('.tactileActivateBydefault').is(':checked');
	
	if($('.tactileTactileFile').is(':visible')){
	hashtable[$('.tactileTactileFile').data('id')] = $('.tactileTactileFile').val();
	}
	hashtable[$('.brailleAssignedSupport').data('id')] = $('.brailleAssignedSupport').is(':checked');
	hashtable[$('.brailleActivateBydefault').data('id')] = $('.brailleActivateBydefault').is(':checked');
	hashtable[$('.brailleEbaeFileType').data('id')] = $('.brailleEbaeFileType').is(':checked');
	hashtable[$('.brailleUebFileType').data('id')] = $('.brailleUebFileType').is(':checked');
	
	if($('.brailleUsage').is(':visible')){
	  hashtable[$('.brailleUsage').data('id')] = $('.brailleUsage').val();
	}
	if($('.brailleBrailleGrade:checked').is(':visible')){
		hashtable[$('.brailleBrailleGrade').data('id')] = $('.brailleBrailleGrade:checked').val();
	}
	
	 $('.MarkType:checked').each(function(el) {
	    	 brailleMarkValues.push($(this).val());
	    });
	    
	if($('.brailleBrailleMark').is(':visible')){
		hashtable[$('.brailleBrailleMark').data('id')] = brailleMarkValue.concat(brailleMarkValues.join(","));
	}
	if($('.brailleBrailleStatusCell').is(':visible')){
		hashtable[$('.brailleBrailleStatusCell').data('id')] = $('.brailleBrailleStatusCell:checked').val();
	}
	if($('.brailleBrailleDotPressure').is(':visible')){
		hashtable[$('.brailleBrailleDotPressure').data('id')] = $('.brailleBrailleDotPressure').val();
	}
	if($('.brailleNumberOfBrailleCells').is(':visible')){
		hashtable[$('.brailleNumberOfBrailleCells').data('id')] = $('.brailleNumberOfBrailleCells').val();
	}
	if($('.brailleNumberOfBrailleDots').is(':visible')){
		hashtable[$('.brailleNumberOfBrailleDots').data('id')] = $('.brailleNumberOfBrailleDots').val();
	}
	
	hashtable['studentId'] = $('#studentIdPNP').val();
	
	// validation done above. If control reached here, data should be valid.
	return true;
}

$('.itemTranslationDisplayAssignedSupport').on("click",function() {
	if($('.itemTranslationDisplayAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.itemTranslationDisplayActivateBydefault').attr("disabled" , true);
			$('.itemTranslationDisplayActivateBydefault').prop('checked',true);
		}else{
			if($('#itemTranslationDisplayActivateBydefault').hasClass('ui-state-disabled')){
				$('.itemTranslationDisplayActivateBydefault').attr("disabled" , true);	
			}else{
				$('.itemTranslationDisplayActivateBydefault').attr("disabled" , false);	
			}
			$('.itemTranslationDisplayActivateBydefault').prop('checked',false);
		}
		if($('#itemTranslationDisplaylanguage').hasClass('ui-state-disabled')){
			$('.itemTranslationDisplaylanguage').attr("disabled" , true);	
		}else{
			$('.itemTranslationDisplaylanguage').attr("disabled" , false);	
		}
	} else {
		$('.itemTranslationDisplayActivateBydefault').prop('checked',false);
		$('.itemTranslationDisplaylanguage option').removeAttr('selected');
		
		$('.itemTranslationDisplayActivateBydefault').attr("disabled" , true);
		$('.itemTranslationDisplaylanguage').attr("disabled" , true);
	}
});

$('.keywordTranslationDisplayAssignedSupport').on("click",function() {
if($('.keywordTranslationDisplayAssignedSupport').is(':checked')) {
	if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
		$('.keywordTranslationDisplayActivateBydefault').attr("disabled" , true);
		$('.keywordTranslationDisplayActivateBydefault').prop('checked',true);
	}else{
		if($('#keywordTranslationDisplayActivateBydefault').hasClass('ui-state-disabled')){
			$('.keywordTranslationDisplayActivateBydefault').attr("disabled" , true);
		}else{
			$('.keywordTranslationDisplayActivateBydefault').attr("disabled" , false);
		}
		$('.keywordTranslationDisplayActivateBydefault').prop('checked',false);
	}
	if($('#keywordTranslationDisplayLanguage').hasClass('ui-state-disabled')){
		$('.keywordTranslationDisplayLanguage').attr("disabled" , true);	
	}else{
		$('.keywordTranslationDisplayLanguage').attr("disabled" , false);	
	}
} else {
	$('.keywordTranslationDisplayActivateBydefault').prop('checked',false);
	$('.keywordTranslationDisplayLanguage option').removeAttr('selected');
	
	$('.keywordTranslationDisplayActivateBydefault').attr("disabled" , true);
	$('.keywordTranslationDisplayLanguage').attr("disabled" , true);
}
});
  
$('.signingAssignedSupport').on("click",function() {
if($('.signingAssignedSupport').is(':checked')) {
	if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
		$('.signingActivateBydefault').attr("disabled" , true);
		$('.signingActivateBydefault').prop('checked',true);
	}else{
		if($('#signingActivateBydefault').hasClass('ui-state-disabled')){
			$('.signingActivateBydefault').attr("disabled" , true);	
		}else{
			$('.signingActivateBydefault').attr("disabled" , false);	
		}
		$('.signingActivateBydefault').prop('checked',false);
	}
	if($('#signingSigningType').hasClass('ui-state-disabled')){
		$('.signingSigningType').attr("disabled" , true);	
	}else{
		$('.signingSigningType').attr("disabled" , false);	
	}
} else {
	$('.signingActivateBydefault').prop('checked',false);
	$('.signingSigningType option').removeAttr('selected');
	
	$('.signingActivateBydefault').attr("disabled" , true);
	$('.signingSigningType').attr("disabled" , true);
}
});

$('.tactileAssignedSupport').on("click",function() {
if($('.tactileAssignedSupport').is(':checked')) {
	if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
		$('.tactileActivateBydefault').attr("disabled" , true);
		$('.tactileActivateBydefault').prop('checked',true);
	}else{
		if($('#tactileActivateBydefault').hasClass('ui-state-disabled')){
			$('.tactileActivateBydefault').attr("disabled" , true);	
		}else{
			$('.tactileActivateBydefault').attr("disabled" , false);	
		}
		$('.tactileActivateBydefault').prop('checked',false);
	}
	if($('#tactileTactileFile').hasClass('ui-state-disabled')){
		$('.tactileTactileFile').attr("disabled" , true);	
	}else{
		$('.tactileTactileFile').attr("disabled" , false);	
	}
} else {
	$('.tactileActivateBydefault').prop('checked',false);
	$('.tactileTactileFile option').removeAttr('selected');
	
	$('.tactileActivateBydefault').attr("disabled" , true);
	$('.tactileTactileFile').attr("disabled" , true);
}
});

$('.brailleAssignedSupport').on("click",function() {
	var disabledClass = 'ui-state-disabled';
	if($('.brailleAssignedSupport').is(':checked')) {
		if($('#studentAssessmentProgramCode').val().indexOf('DLM') !== -1){
			$('.brailleActivateBydefault').attr("disabled" , true);
			$('.brailleActivateBydefault').prop('checked',true);
		}else{
			$('.brailleActivateBydefault').attr("disabled" , false);
			$('.brailleActivateBydefault').prop('checked',false);
		}
		if($('#brailleAssignedSupport :input').data('state-setting') == 'NONE'){
			$('#brailleFileType :input.brailleEbaeFileType').attr( "disabled", "disabled");
			$('#brailleFileType :input.brailleUebFileType').attr( "disabled", "disabled");
			$('#brailleFileType').addClass(disabledClass);
			$('input[class=brailleEbaeFileType]').each(function() {
				$(this).prop("checked", false);
			}); 
			$('input[class=brailleUebFileType]').each(function() {
				$(this).prop("checked", false);
			});
		} else if($('#brailleAssignedSupport :input').data('state-setting') == 'BOTH'){
			$('.brailleEbaeFileType').attr("disabled" , false);
			$('.brailleUebFileType').attr("disabled" , false);
			$('#brailleFileType').removeClass(disabledClass);
		} else if($('#brailleAssignedSupport :input').data('state-setting') == 'EBAE'){
			$('.brailleEbaeFileType').attr("disabled" , false);
			$('#brailleFileType :input.brailleUebFileType').attr( "disabled", "disabled");
			$('#brailleFileType').removeClass(disabledClass);
			$('input[class=brailleEbaeFileType]').each(function() {
				$(this).prop("checked", true);
			}); 
		}else if($('#brailleAssignedSupport :input').data('state-setting') == 'UEB'){
			$('.brailleUebFileType').attr("disabled" , false);
			$('#brailleFileType :input.brailleEbaeFileType').attr( "disabled", "disabled");
			$('#brailleFileType').removeClass(disabledClass);
			$('input[class=brailleUebFileType]').each(function() {
				$(this).prop("checked", true);
			});
		}

		if ($('#brailleUsage[class ~= "'+disabledClass+'"]').length == 0){
			$('.brailleUsage').attr("disabled" , false);
		}
		if ($('#brailleBrailleGrade[class ~= "'+disabledClass+'"]').length == 0){
			$('.brailleBrailleGrade').attr("disabled" , false);
		}
		if ($('#brailleBrailleMark[class ~= "'+disabledClass+'"]').length == 0){
			$('.brailleBrailleMark').attr("disabled" , false);
		}
		if ($('.brailleAssignedSupport[class ~= "'+disabledClass+'"]').length == 0){
			if ($('#MarkType[class ~= "'+disabledClass+'"]').length == 0){
			$('.MarkType').attr("disabled" , false);
			}
		}
		else{
			$('.MarkType').attr("disabled" , false);
		}
		if ($('#brailleBrailleStatusCell[class ~= "'+disabledClass+'"]').length == 0){
			$('.brailleBrailleStatusCell').attr("disabled" , false);
		}
		if ($('#brailleBrailleDotPressure[class ~= "'+disabledClass+'"]').length == 0){
			$('.brailleBrailleDotPressure').attr("disabled" , false);
		}
		if ($('#brailleNumberOfBrailleCells[class ~= "'+disabledClass+'"]').length == 0){
			$('.brailleNumberOfBrailleCells').attr("disabled" , false);
		}
		if ($('#brailleNumberOfBrailleDots[class ~= "'+disabledClass+'"]').length == 0){
			$('.brailleNumberOfBrailleDots').attr("disabled" , false);
		}
		if(($('.brailleBrailleDotPressure').val() == ' ') || ($('.brailleBrailleDotPressure').val() == '')){
			$('.brailleBrailleDotPressure').val("0.5");
		}
		if(($('.brailleNumberOfBrailleCells').val() == ' ') || ($('.brailleNumberOfBrailleCells').val() == '')){
			$('.brailleNumberOfBrailleCells').val("80");
		}
		 $('input[name=brailleBrailleStatusCell]').each(function() {
			if(($(this).val() == "off")){
			 $(this).prop("checked", true);
			}
		});
		$('input[name=brailleBrailleGrade]').each(function() {
			if(($(this).val() == "uncontracted")){
				 $(this).prop("checked", true);
			}
		}); 
	} else {
		$('.brailleActivateBydefault').prop('checked',false);
		$('input[class=brailleEbaeFileType]').each(function() {
			 $(this).prop("checked", false);
		});
		$('input[class=brailleUebFileType]').each(function() {
			 $(this).prop("checked", false);
		});
		$('.brailleUsage option').removeAttr('selected');
		$('.brailleBrailleDotPressure').val(' ');
		$('.brailleNumberOfBrailleCells').val(' ');
		$('input[name=brailleBrailleStatusCell]').each(function() {
			 $(this).prop("checked", false);
		});
		$('input[name=brailleBrailleGrade]').each(function() {
			 $(this).prop("checked", false);
		});
		$('input[name=MarkType]').each(function() {
			 $(this).prop("checked", false);
		});
		$('.brailleActivateBydefault').attr("disabled" , true);
		$('.brailleUsage').attr("disabled" , true);
		
		$('.brailleEbaeFileType').attr("disabled" , true);
		$('.brailleUebFileType').attr("disabled" , true);
		$('#brailleFileType').addClass(disabledClass);
		
		$('.brailleBrailleGrade').attr("disabled" , true);
		$('.MarkType').attr("disabled" , true);
		$('.brailleBrailleStatusCell').attr("disabled" , true);
		$('.brailleBrailleMark').attr("disabled" , true);
		$('.brailleBrailleDotPressure').attr("disabled" , true);
		$('.brailleNumberOfBrailleCells').attr("disabled" , true);
		$('.brailleNumberOfBrailleDots').attr("disabled" , true);
	}
	});

function loadLanguageBraille(current, responseData) {
	var disabledClass = 'ui-state-disabled';
	var checkboxValue = false;

	if(responseData.selectedValue.toLowerCase() == "true") 
		checkboxValue = true;
	else if(responseData.selectedValue.toLowerCase() == "false") 
		checkboxValue = false;
	
	var currentClass = ($(current).attr('class').toLowerCase());
	if(currentClass.indexOf(("itemTranslationDisplayAssignedSupport").toLowerCase()) == 0) {
		$('.itemTranslationDisplayAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.itemTranslationDisplayActivateBydefault').attr("disabled" , true);
			$('.itemTranslationDisplaylanguage').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("itemTranslationDisplayActivateBydefault").toLowerCase()) == 0) {
		$('.itemTranslationDisplayActivateBydefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("itemTranslationDisplaylanguage").toLowerCase()) == 0) {
		$(".itemTranslationDisplaylanguage").find("option").each(function(){
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).attr('selected','selected');
			}
	    });
	} else if(currentClass.indexOf(("keywordTranslationDisplayAssignedSupport").toLowerCase()) == 0) {
		$('.keywordTranslationDisplayAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.keywordTranslationDisplayActivateBydefault').attr("disabled" , true);
			$('.keywordTranslationDisplayLanguage').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("keywordTranslationDisplayActivateBydefault").toLowerCase()) == 0) {
		$('.keywordTranslationDisplayActivateBydefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("keywordTranslationDisplayLanguage").toLowerCase()) == 0) {
		$(".keywordTranslationDisplayLanguage").find("option").each(function(){
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).attr('selected','selected');
			}
	    });
	} else if(currentClass.indexOf(("signingAssignedSupport").toLowerCase()) == 0) {
		$('.signingAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.signingActivateBydefault').attr("disabled" , true);
			$('.signingSigningType').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("signingActivateBydefault").toLowerCase()) == 0) {
		$('.signingActivateBydefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("signingSigningType").toLowerCase()) == 0) {
		$(".signingSigningType").find("option").each(function(){
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).attr('selected','selected');
			}
	    });
	}else if(currentClass.indexOf(("tactileAssignedSupport").toLowerCase()) == 0) {
		$('.tactileAssignedSupport').prop('checked',checkboxValue);
		if(checkboxValue) {
			$('.tactileActivateBydefault').attr("disabled" , true);
			$('.tactileTactileFile').attr("disabled" , false);
		}
	} else if(currentClass.indexOf(("tactileActivateBydefault").toLowerCase()) == 0) {
		$('.tactileActivateBydefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("tactileTactileFile").toLowerCase()) == 0) {
		$(".tactileTactileFile").find("option").each(function(){
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).attr('selected','selected');
			}
	    });
	}else if(currentClass.indexOf(("brailleAssignedSupport").toLowerCase()) == 0) {
		$('.brailleAssignedSupport').prop('checked',checkboxValue);
		$('#brailleAssignedSupport :input').data('state-setting', responseData.brailleFileType);
		if($('#brailleAssignedSupport :input').data('state-setting') == 'NONE'){
			$('#brailleFileType :input.brailleEbaeFileType').attr( "disabled", true);
			$('#brailleFileType :input.brailleUebFileType').attr( "disabled", true);
			$('#brailleFileType').addClass(disabledClass);
		} else if($('#brailleAssignedSupport :input').data('state-setting') == 'BOTH'){
			$('#brailleFileType :input.brailleEbaeFileType').attr("disabled" , false);
			$('#brailleFileType :input.brailleUebFileType').attr("disabled" , false);
			$('#brailleFileType').removeClass(disabledClass);
		} else if($('#brailleAssignedSupport :input').data('state-setting') == 'EBAE'){
			$('#brailleFileType :input.brailleEbaeFileType').attr("disabled" , false);
			$('#brailleFileType :input.brailleUebFileType').attr( "disabled", "disabled");
			$('#brailleFileType').removeClass(disabledClass);
		}else if($('#brailleAssignedSupport :input').data('state-setting') == 'UEB'){
			$('#brailleFileType :input.brailleUebFileType').attr("disabled" , false);
			$('#brailleFileType :input.brailleEbaeFileType').attr( "disabled", "disabled");
			$('#brailleFileType').removeClass(disabledClass);
		}
		
		if(checkboxValue) {
			$('.brailleActivateBydefault').attr("disabled" , true);
			$('.brailleUsage').attr("disabled" , false);
			$('.brailleBrailleGrade').attr("disabled" , false);
			$('.brailleBrailleMark').attr("disabled" , false);
			$('.MarkType').attr("disabled" , false);
			$('.brailleBrailleStatusCell').attr("disabled" , false);
			$('.brailleBrailleDotPressure').attr("disabled" , false);
			$('.brailleNumberOfBrailleCells').attr("disabled" , false);
			$('.brailleNumberOfBrailleDots').attr("disabled" , false);
		} else {
			$('#brailleFileType :input.brailleEbaeFileType').attr( "disabled", true);
			$('#brailleFileType :input.brailleUebFileType').attr( "disabled", true);
		}
	} else if(currentClass.indexOf(("brailleActivateBydefault").toLowerCase()) == 0) {
		$('.brailleActivateBydefault').prop('checked',checkboxValue);
	} else if(currentClass.indexOf(("brailleUsage").toLowerCase()) == 0) {
		$(".brailleUsage").find("option").each(function(){
			if ($(this).val().toLowerCase() == responseData.selectedValue) {
				$(this).attr('selected','selected');
			}
	    });
	}else if(currentClass.indexOf(("brailleBrailleGrade").toLowerCase()) == 0) {
		$('input[name=brailleBrailleGrade]').each(function() {
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).prop("checked", true);
			}
		});
	} else if(currentClass.indexOf(("brailleEbaeFileType").toLowerCase()) == 0) {
		$('input[class=brailleEbaeFileType]').each(function() {
			if($('.brailleAssignedSupport').is(':checked')){
				$('#brailleFileType :input.brailleEbaeFileType').prop("checked", (responseData.selectedValue.toLowerCase() === 'true'));
			} else {
				$('#brailleFileType :input.brailleEbaeFileType').prop("checked", false);
			}
		});
	}  else if(currentClass.indexOf(("brailleUebFileType").toLowerCase()) == 0) {
		if($('.brailleAssignedSupport').is(':checked')){
			$('#brailleFileType :input.brailleUebFileType').prop("checked", (responseData.selectedValue.toLowerCase() === 'true'));
		} else {
			$('#brailleFileType :input.brailleUebFileType').prop("checked", false);
		}
	} else if(currentClass.indexOf(("brailleBrailleStatusCell").toLowerCase()) == 0) {
		$('input[name=brailleBrailleStatusCell]').each(function() {
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).prop("checked", true);
			}
		});
	}else if(currentClass.indexOf(("brailleBrailleDotPressure").toLowerCase()) == 0) {
		$('.brailleBrailleDotPressure').val(responseData.selectedValue);
	}else if(currentClass.indexOf(("brailleNumberOfBrailleCells").toLowerCase()) == 0) {
		$('.brailleNumberOfBrailleCells').val(responseData.selectedValue);
	}else if(currentClass.indexOf(("brailleNumberOfBrailleDots").toLowerCase()) == 0) {
		$(".brailleNumberOfBrailleDots").find("option").each(function(){
			if ($(this).val().toLowerCase() == responseData.selectedValue.toLowerCase()) {
				$(this).attr('selected','selected');
			}
		});
	}else if(currentClass.indexOf(("brailleBrailleMark").toLowerCase()) == 0) {		
		var str_array = (responseData.selectedValue).split(',');
		for(var i = 0; i < str_array.length; i++)
		{
			// Trim the excess whitespace.
			str_array[i] = str_array[i].replace(/^\s*/, "").replace(/\s*$/, "");

			$('input[name=MarkType]').each(function() {
				if ($(this).val() == str_array[i]) {
					$(this).prop("checked", true);
				}
			});
		}
	}
}

function hideOrDisableLanguageBraille(responseData){
	var disabledClass = 'ui-state-disabled';
	if (responseData.attributeContainerName =='itemTranslationDisplay'
			&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#itemTranslationDisplayAssignedSupport :input').attr( "disabled", "disabled");
			$('#itemTranslationDisplayAssignedSupport').addClass(disabledClass);
			$('#itemTranslationDisplayAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#itemTranslationDisplayAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='itemTranslationDisplay'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#itemTranslationDisplayActivateBydefault :input').attr( "disabled", "disabled");
			$('#itemTranslationDisplayActivateBydefault').addClass(disabledClass);
			$('#itemTranslationDisplayActivateBydefault').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#itemTranslationDisplayActivateBydefault').hide();
		}
	}		
	if (responseData.attributeContainerName =='itemTranslationDisplay'
		&& responseData.attributeName == 'Language'){
		if (responseData.viewOption == 'disable'){
			$('#itemTranslationDisplayLanguage :input').attr( "disabled", "disabled");
			$('#itemTranslationDisplayLanguage').addClass(disabledClass);
			$('#itemTranslationDisplayLanguage').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#itemTranslationDisplaylanguage').hide();
		}
	}	
	if (responseData.attributeContainerName =='keywordTranslationDisplay'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#keywordTranslationDisplayAssignedSupport :input').attr( "disabled", "disabled");
			$('#keywordTranslationDisplayAssignedSupport').addClass(disabledClass);
			$('#keywordTranslationDisplayAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#keywordTranslationDisplayAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='keywordTranslationDisplay'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#keywordTranslationDisplayActivateByDefault :input').attr( "disabled", "disabled");
			$('#keywordTranslationDisplayActivateByDefault').addClass(disabledClass);
			$('#keywordTranslationDisplayActivateByDefault').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#keywordTranslationDisplayActivateByDefault').hide();
		}
	}		
	if (responseData.attributeContainerName =='keywordTranslationDisplay'
		&& responseData.attributeName == 'Language'){
		if (responseData.viewOption == 'disable'){
			$('#keywordTranslationDisplayLanguage :input').attr( "disabled", "disabled");
			$('#keywordTranslationDisplayLanguage').addClass(disabledClass);
			$('#keywordTranslationDisplayLanguage').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#keywordTranslationDisplayLanguage').hide();
		}
	}	
	if (responseData.attributeContainerName =='Signing'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#signingAssignedSupport :input').attr( "disabled", "disabled");
			$('#signingAssignedSupport').addClass(disabledClass);
			$('#signingAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#signingAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='Signing'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#signingActivateByDefault :input').attr( "disabled", "disabled");
			$('#signingActivateByDefault').addClass(disabledClass);
			$('#signingActivateByDefault').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#signingActivateByDefault').hide();
		}
	}		
	if (responseData.attributeContainerName =='Signing'
		&& responseData.attributeName == 'SigningType'){
		if (responseData.viewOption == 'disable'){
			$('#signingSigningType :input').attr( "disabled", "disabled");
			$('#signingSigningType').addClass(disabledClass);
			$('#signingSigningType').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#signingSigningType').hide();
		}
	}
	if (responseData.attributeContainerName =='Tactile'
		&& responseData.attributeName == 'assignedSupport'){
		if (responseData.viewOption == 'disable'){
			$('#tactileAssignedSupport :input').attr( "disabled", "disabled");
			$('#tactileAssignedSupport').addClass(disabledClass);
			$('#tactileAssignedSupport').parent().hide();
		} else if (responseData.viewOption == 'hide'){
			$('#tactileAssignedSupport').hide();
		}
	}
	if (responseData.attributeContainerName =='Tactile'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#tactileActivateByDefault :input').attr( "disabled", "disabled");
			$('#tactileActivateByDefault').addClass(disabledClass);
			$('#tactileActivateByDefault').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#tactileActivateByDefault').hide();
		}
	}		
	if (responseData.attributeContainerName =='Tactile'
		&& responseData.attributeName == 'tactileFile'){
		if (responseData.viewOption == 'disable'){
			$('#tactileTactileFile :input').attr( "disabled", "disabled");
			$('#tactileTactileFile').addClass(disabledClass);
			$('#tactileTactileFile').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#tactileTactileFile').hide();
		}
	}
	if (responseData.attributeContainerName =='Braille'
        && responseData.attributeName == 'assignedSupport'){
		$('#brailleAssignedSupport :input').data('state-setting', responseData.brailleFileType);
        if (responseData.viewOption == 'disable'){
            $('#brailleAssignedSupport :input').attr( "disabled", "disabled");
            $('#brailleAssignedSupport').addClass('ui-state-disabled');
            $('#brailleAssignedSupport').parents('li').hide();
        } else if (responseData.viewOption == 'hide'){
            $('#brailleAssignedSupport').hide();
        }
    }
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'activateByDefault'){
		if (responseData.viewOption == 'disable'){
			$('#brailleActivateByDefault :input').attr( "disabled", "disabled");
			$('#brailleActivateByDefault').addClass(disabledClass);
			$('#brailleActivateByDefault').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleActivateByDefault').hide();
		}
	}		
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'usage'){
		if (responseData.viewOption == 'disable'){
			$('#brailleUsage :input').attr( "disabled", "disabled");
			$('#brailleUsage').addClass(disabledClass);
			$('#brailleUsage').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleUsage').hide();
		}
	}
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'brailleStatusCell'){
		if (responseData.viewOption == 'disable'){
			$('#brailleBrailleStatusCell :input').attr( "disabled", "disabled");
			$('#brailleBrailleStatusCell').addClass(disabledClass);
			$('#brailleBrailleStatusCell').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleBrailleStatusCell').hide();
		}
	}
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'brailleDotPressure'){
		if (responseData.viewOption == 'disable'){
			$('#brailleBrailleDotPressure :input').attr( "disabled", "disabled");
			$('#brailleBrailleDotPressure').addClass(disabledClass);
			$('#brailleBrailleDotPressure').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleBrailleDotPressure').hide();
		}
	}		
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'numberOfBrailleCells'){
		if (responseData.viewOption == 'disable'){
			$('#brailleNumberOfBrailleCells :input').attr( "disabled", "disabled");
			$('#brailleNumberOfBrailleCells').addClass(disabledClass);
			$('#brailleNumberOfBrailleCells').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleNumberOfBrailleCells').hide();
		}
	}
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'numberOfBrailleDots'){
		if (responseData.viewOption == 'disable'){
			$('#brailleNumberOfBrailleDots :input').attr( "disabled", "disabled");
			$('#brailleNumberOfBrailleDots').addClass(disabledClass);
			$('#brailleNumberOfBrailleDots').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleNumberOfBrailleDots').hide();
		}
	}
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'ebaeFileType'){
		if (responseData.viewOption == 'disable'){
			$('#brailleFileType :input.brailleEbaeFileType').attr( "disabled", "disabled");
			$('#brailleFileType').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleFileType').hide();
		}
	}
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'uebFileType'){
		if (responseData.viewOption == 'disable'){
			$('#brailleFileType :input.brailleUebFileType').attr( "disabled", "disabled");
			$('#brailleFileType').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleFileType').hide();
		}
	}
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'brailleGrade'){
		if (responseData.viewOption == 'disable'){
			$('#brailleBrailleGrade :input').attr( "disabled", "disabled");
			$('#brailleBrailleGrade').addClass(disabledClass);
			$('#brailleBrailleGrade').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleBrailleGrade').hide();
		}
	}		
	if (responseData.attributeContainerName =='Braille'
		&& responseData.attributeName == 'brailleMark'){
		if (responseData.viewOption == 'disable'){
			$('#brailleBrailleMark :input').attr( "disabled", "disabled");
			$('#brailleBrailleMark').addClass(disabledClass);
			$('#brailleBrailleMark').hide();
		} else if (responseData.viewOption == 'hide'){
			$('#brailleBrailleMark').hide();
		}
	}	
}