var isLoaded = false;
function editStudentInit(studentId){
	var me = this;
//	var id= $('#studentId').val();

	var id=studentId;
	var apSelect = $('#assessmentProgramStudentEdit'), optionText='';
	$('.messages').html('').hide();
	$('#assessmentProgramStudentEdit').trigger('change.select2');	
	$('#giftedStudentEnrollment').next().find("span").removeAttr("aria-disabled");
	$.ajax({
		url: 'getStudentAssessmentPrograms.htm',
		data: {studentId:id}
	}).done(function(data) {	
		$.ajax({
			url: 'getCurrentUserAssessmentPrograms.htm',
			dataType: 'JSON',
			type: "POST"
		}).done(function(value) {
			var currentStudentAPs = [];
			$('#assessmentProgramStudentEdit').html('');
			if (data !== undefined && data !== null && data.length > 0) {
				for (var i = 0, length = data.length; i < length; i++) {
					currentStudentAPs.push(data[i].id);
					var optionText = data[i].abbreviatedname+"-"+data[i].programName;
					if(data[i].id == $('#hiddenCurrentAssessmentProgramId').val()){
						$('#assessmentProgramStudentEdit').append($('<option></option>').val(data[i].id).html(optionText));	
					}
					else{
						$('#assessmentProgramStudentEdit').append($('<option></option>').val(data[i].id).html(optionText).prop("disabled", true));
					}
				}					
				$('#assessmentProgramStudentEdit option').prop('selected', true);
			} 
			if (value !== undefined && value !== null && value.length > 0) {
				
				for (var i = 0, length = value.length; i < length; i++) {
					var optionText = value[i].abbreviatedname+"-"+value[i].programName;
					if(value[i].id == $('#hiddenCurrentAssessmentProgramId').val()){
						if (!$('#assessmentProgramStudentEdit').find('option[value='+value[i].id+']').length > 0) {
							$('#assessmentProgramStudentEdit').append($('<option></option>').val(value[i].id).html(optionText));
						}
					}
					/*else{
						if (!$('#assessmentProgramStudentEdit').find('option[value='+value[i].id+']').length > 0) {
							$('#assessmentProgramStudentEdit').append($('<option></option>').val(value[i].id).html(optionText).prop("disabled", true));
						}
					}*/
				}					
			}
			$('#assessmentProgramStudentEdit').select2({
				placeholder:'Select',
				multiple: true}).val(currentStudentAPs).trigger('change').trigger('change.select2');
		});	
	});	
 }
$(document).ready(function(){
	
	$.ajax({
		url: 'getstatestudentidentifierlength.htm',					
        type: "POST",
		success: function(stateStudentIdentifierLength){
		$('#editStudentStateStudentId').attr("maxlength", stateStudentIdentifierLength);
		}
	});
	
	$.validator.addMethod("validateLessThanThousandYear", function (value, element) {						
		 var now = new Date('12/31/0999');
		 var myDate = new Date(value);
		 return this.optional(element) || myDate > now;
	});	
	
	jQuery.validator.addMethod("validateFutureDate", function(value, element) { 
		 var now = new Date();
		 var myDate = new Date(value);
		 return this.optional(element) || myDate < now;
	});
	
	$('#stateStudentIdentifier').on("change",function() {
			$('#messageEditStudent').hide();
	});
	
 $("editStudentForm").submit(function(){
		var assessmentPrograms = new Array();
			assessmentPrograms = $("#assessmentProgramStudentEdit").val();
			
			if(assessmentPrograms == null || assessmentPrograms.length < 1) {
    			validData = false;
    			//alert("assessmentPrograms");
    		}
    });
	
 var form = $('#editStudentForm'); 
	$("#editStudentGradeChangePopup").dialog({
		resizable: false,
		height: 260,
		width: 500,
		modal: true,
		autoOpen: false,
		create: function(event, ui) { 
	    	var widget = $(this).dialog("widget");
		},	
	  buttons : {
		  
		"Yes" :  {
		    	 	class: 'rightYesButton',
		    	 	text:'Yes',
		    	 	click: function(){
		    	 		//call saveEditedStudent.htm with grade change
		    	 		callSaveEditStudent(form);
		    	 		$('#editStudentGradeChangePopup').dialog('close');
		    	 	}
		},
	     "No" :   {
	    	 		class: 'rightNoButton',
	    	 		text:'No',
	    	 		click:function(){
	    	 			$('#editStudentGradeChangePopup').dialog('close');
	    	 }
		}
	  }
	});
 
	var dlm = '${student.dlmStudentStr}';
	
	if (dlm == 'Yes'){
		$('#assessmentProgram', form).val('true');
	} else {
		$('#assessmentProgram', form).val('false');
	}
	$('select', form).select2({
		placeholder: 'Select',
		multiple: false,
		allowClear : true
	});
	$('#editStudentESOLEntryDate, #editStudentUSAEntryDate, #editStudentStateEntryDate, #editStudentDistrictEntryDate', form).datepicker({
		dateFormat: 'mm/dd/yy',
		changeMonth: false,
		changeYear: false
	});
	
	$('#editStudentDateOfBirth, #editStudentSchoolEntryDate', form).datepicker({
		dateFormat: 'mm/dd/yy',
		changeMonth: false,
		changeYear: false,
		maxDate : new Date()
	});
	
	$('#esolParticipationCode', form).on("change",function(){
		var val = $(this).val();
		if (val == '0' || val == '5'){
			$('#editStudentESOLEntryDateDiv, #editStudentUSAEntryDateDiv').hide();
			$('#editStudentESOLEntryDate, #editStudentUSAEntryDate', form).val('').datepicker('option', {minDate:null, maxDate: null});
		} else {
			$('#editStudentESOLEntryDateDiv, #editStudentUSAEntryDateDiv').show();
		}
	}).on("change",function(){});
	
/*	$('#firstLanguage', form).change(function(event){
		if ($(this).val() == '0'){
			$('#editStudentESOLDiv', form).hide();
			$('#esolParticipationCode', form).val('0').trigger('change.select2').change();
			$('#editStudentESOLEntryDate, #editStudentUSAEntryDate', form).val('').datepicker('option', {minDate:null, maxDate: null});
		}else if(isLoaded){
			$('#esolParticipationCode', form).val('').trigger('change.select2').change();
			$('#editStudentESOLDiv', form).show();
		}
	}).change();*/
	
	$("#editStudentForm #stateStudentIdentifier").on('input',function() {
		$('#messageEditStudent').hide();
	});	
	
	$('#editStudentContent #saveStudent').on("click",function(){
		var disabled = form.find(':disabled').prop("disabled", false);

		var valid = form.valid();
		$("#editStudentContent #messageEditStudent").html('');
		if (!valid){
			return;
		}
		
		var dlmOnly = $("#DLMOnly", form).val();
		var doesStudentHaveTestSessions =$("#doesStudentHaveTestSessions", form).val();
		var sentGradeLevel = $("#unchangedGradeLevel", form).val();
		var selectedGradeLevel = $("#currentGradeLevelEnrollment", form).val();
		if ((dlmOnly === 'true') && (doesStudentHaveTestSessions === 'true') && (sentGradeLevel != selectedGradeLevel)) {
			//open a popup to confirm the call to change grade and invalidate test sessions
			$("#editStudentGradeChangePopup").dialog('open');
		} else {
			callSaveEditStudent(form);
		}
		disabled.attr('disabled',true);
		
	});
	
	$.validator.addMethod("dateUS",
		    function(value, element) {
		        return value.match(/^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/[0-9]{4}$/);
		    });
	
	form.validate({
		ignore: '',
		onkeyup: false,
		submitHandler: function(){
			
		},
		errorPlacement: function(error, element){
			if(element.hasClass('required') || element.attr('type') == 'file'){
				error.insertAfter(element.next());
			} else {
				error.insertAfter(element);
			}
		},
		rules: {
			legalFirstName: {required: true, maxlength: 80},
			legalMiddleName: {maxlength: 80},
			legalLastName: {required: true, maxlength: 80},
			generationCode: {maxlength: 10},
			stateStudentIdentifier: {required: true, maxlength: 50},
			hispanicEthnicity: 'required',
			assessmentProgramStudentEdit: {required: true},
			esolParticipationCode: 'required',
			dateOfBirth:  {required: true, dateUS: {
				depends: function (element) {
					if(!$(element).is(":hidden")){
						return true;	
					}
					else 
					{
						return false;	
					}
				}
		}, validateLessThanThousandYear :true, validateFutureDate:true},
			schoolEntryDate :{required: true,dateUS: {
				depends: function (element) {
					if(!$(element).is(":hidden")){
						return true;	
					}
					else 
					{
						return false;	
					}
				}
		}},
			currentSchoolYear :{required: true},
			currentGrade : {required: true},
			esolProgramEntryDate: {dateUS: {
				depends: function (element) {
					if(!$(element).is(":hidden") && $(element).val()!=''){
						return true;	
					}
					else 
					{
						return false;	
					}
				}
		}, validateLessThanThousandYear :true},
			usaEntryDate: {dateUS: {
				depends: function (element) {
					if(!$(element).is(":hidden") && $(element).val()!=''){
						return true;	
					}
					else 
					{
						return false;	
					}
				}
		}, validateLessThanThousandYear :true},
			localStudentIdentifier: {maxlength: 20},
			schoolEntryDateEnrollment : {required: true, dateUS: true, validateLessThanThousandYear :true, validateFutureDate:true},
			stateEntryDateEnrollment: { dateUS: {
				depends: function (element) {
					if(!$(element).is(":hidden") && $(element).val()!=''){
						return true;	
					}
					else 
					{
						return false;	
					}
				}
		}, validateLessThanThousandYear :true},
			districtEntryDateEnrollment: {dateUS: {
				depends: function (element) {
					if(!$(element).is(":hidden") && $(element).val()!=''){
						return true;	
					}
					else 
					{
						return false;	
					}
				}
		}, validateLessThanThousandYear :true},
		},
 		messages: {
 			
 			    legalFirstName : {
					required : "This field is required.",
				},					
				legalLastName : {
					required : "This field is required.",
				},	  		 					
				dateOfBirth: {
 					required : "This field is required.",
 					dateUS : "Please enter valid Date of Birth",
 					validateLessThanThousandYear : 'Date should be greater than year 1000',
 					validateFutureDate : "Date should be less than current date."
 				},
 				stateEntryDateEnrollment: { 				
 					dateUS : "Please enter valid State Entry Date",
 					validateLessThanThousandYear : 'Date should be greater than year 1000'
 				},
 				schoolEntryDateEnrollment: {
 					required : "This field is required.",
 					dateUS : "Please enter valid School Entry Date",
 					validateLessThanThousandYear : 'Date should be greater than year 1000',
 					validateFutureDate : "Date should be less than current date."
 				},
 				districtEntryDateEnrollment: { 				
 					dateUS : "Please enter valid District Entry Date",
 					validateLessThanThousandYear : 'Date should be greater than year 1000'
 				},
 				esolProgramEntryDate: { 					
 					dateUS : "Please enter valid ESOL Program Entry Date",
 					validateLessThanThousandYear : 'Date should be greater than year 1000'
 				},
 				usaEntryDate: { 					
 					dateUS : "Please enter valid USA Entry Date",
 					validateLessThanThousandYear : 'Date should be greater than year 1000'
 				}
		}
	});
	
	var id= $('#studentIdForEdit').val();
	editStudentInit(id);
	isLoaded = true;
});

function callSaveEditStudent(form) {
	// the not() is there for a reason, see next block
	var data = $('#editStudentForm :input:not([name="assessmentProgramStudentEdit"])')
    .filter(function(index, element) {
        return $(element).val() != '';
    }).serializeArray();
	
	// the assessment program dropdown doesn't play nicely with disabled options,
	// so to keep things consistent, we add them ourselves here
	var selectedAssessmentPrograms = $('#assessmentProgramStudentEdit option:selected').map(function(i,v){return this.value}).get();
	for (var x = 0; x < selectedAssessmentPrograms.length; x++){
		data.push({
			name: 'assessmentProgramStudentEdit',
			value: selectedAssessmentPrograms[x]
		});
	}
	
	$.ajax({
		url: 'isEditStudentDemographicValueExists.htm',					
		dataType: 'json',
        type: "POST",
		data: data
	}).done(function(result){
		if(result.demographicMatches == true){
			$("#editStudentDemographicWarningMessagePopup").dialog({
				resizable: false,
				width: 500,
				modal: true,
				autoOpen: true,
				title: 'Warning',
				dialogClass: 'editStudentDialog',
 				buttons: {		
 					"Continue" : function(){
 						$(this).dialog('close');
 						afterDemographicSaveEditStudent(data, form);
 					},									
	 		        "Cancel" : function(){			
	 					$(this).dialog('close');
	 				}
 				},
			});
		}else{
			afterDemographicSaveEditStudent(data, form);
		}
	});
}

function afterDemographicSaveEditStudent(data, form){
	$.ajax({
		url: 'saveEditedStudent.htm',
		type: 'POST',
		data: data
	}).done(function(data){
		if (data.result == 'success'){
			$("#editStudentContent #messageEditStudent").html('<span class="info_message ui-state-highlight">Student updated successfully</span>').show();
			//on a successful save, change the unchanged grade level to the selected grade level - this allows successive clicks with out a reload to display the popup
			$("#unchangedGradeLevel", form).val($("#currentGradeLevelEnrollment", form).val());
		} else if(data.result == 'duplicate') {
			$("#editStudentContent #messageEditStudent").html('<span class="error_message ui-state-highlight">State Student Identifier already existed for another student.</span>').show();
		} else {
			$("#editStudentContent #messageEditStudent").html('<span class="error_message ui-state-highlight">Error found while updating the data</span>').show();
		}
		$('#editStudentDiv').animate({scrollTop:0}, 'slow');
	});
}
