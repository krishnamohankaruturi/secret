

function activeStudentInit(studentId){

	var me = this;
//	var id= $('#studentId').val();

	var id=studentId;
	var apSelect = $('#activateStudentAssessmentProgramSelectStudent'), optionText='';
	$('.messages').html('').hide();
	$('#activateStudentAssessmentProgramSelectStudent').trigger('change.select2');
	
	$.ajax({
		url: 'getCurrentUserAssessmentPrograms.htm',
		dataType: 'JSON',
		type: "POST",
		success: function(data) {	
		
			if (data !== undefined && data !== null && data.length > 0) {
				$('#activateStudentAssessmentProgramSelectStudent').html('');
				
				for (var i = 0, length = data.length; i < length; i++) {
					optionText = data[i].programName;
					if(data[i].id == $('#hiddenCurrentAssessmentProgramId').val()){
						apSelect.append($('<option selected=\''+'selected'+'\'></option>').val(data[i].id).html(optionText));
					} else {
						//apSelect.append($('<option></option>').val(data[i].id).html(optionText).prop("disabled", true));
					}
				}					
				$('#activateStudentAssessmentProgramSelectStudent').select2({placeholder:'Select', multiple: true});
				if (data.length != 1) {
					apSelect.prop('disabled', false);
				}
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('.messages').html(me.options.newreport_no_assessmentprogram).show();
			}
			
		}
	
	
	});
	
	jQuery.validator.setDefaults({
			submitHandler: function() {		
			},
			errorPlacement: function(error, element) {
				if(element.hasClass('required') || element.prop('type') == 'file') {
					error.insertAfter(element.next());
				}
				else {
		    		error.insertAfter(element);
				}
		    }
	}); 
	
	var form = $('#activeStudentForm'); 
	
	$("#activateStudentStateEntryDate, #activateStudentDistrictEntryDate, #activateStudentEsolProgramEntryDate,#activateStudentUsaEntryDate", form).datepicker({
		dateFormat: 'mm/dd/yy',
		changeMonth: false,
		changeYear: false
	});
	
	$('#activateStudentDobDate, #activateStudentSchoolEntryDate', form).datepicker({
		dateFormat: 'mm/dd/yy',
		changeMonth: false,
		changeYear: false,
		maxDate : new Date()
	});
	
	$('#activeStudentForm .bcg_select').select2({
 			placeholder:'Select', 
 			multiple: false,
 			allowClear : true
 	});
 	 		
	$("#activateStudentConfirmation").dialog({
		autoOpen : false,
		modal : true
	});
		
/*	$('#activateStudentFirstLanguage').change(function() {
	 		if($('#activateStudentFirstLanguage').val() == "0") {
	 			$('#activateStudentEsolParticipationCodeSelect').val("0").trigger('change.select2');
	 			$('#activateStudentEsolParticipationCodeDiv').hide();
	 			$('#activateStudentEsolProgramEntryDateDiv').hide();
	 			$('#activateStudentUsaEntryDateDiv').hide();
	 		}else{
	 			$('#activateStudentEsolParticipationCodeSelect').val('').trigger('change.select2');
	 			$('#activateStudentEsolParticipationCodeDiv').show();
	 			$('#activateStudentEsolProgramEntryDateDiv').show();
	 			$('#activateStudentUsaEntryDateDiv').show();
	 		}
	 	});*/
		
		$('#activateStudentEsolParticipationCodeSelect').on("change",function() {
	 		if($('#activateStudentEsolParticipationCodeSelect').val() == "0" || $('#activateStudentEsolParticipationCodeSelect').val() == "5") {
	 			$('#activateStudentEsolProgramEntryDateDiv').hide();
	 			$('#activateStudentUsaEntryDateDiv').hide();
	 		}else{
	 			$('#activateStudentEsolProgramEntryDateDiv').show();
	 			$('#activateStudentUsaEntryDateDiv').show();
	 		}
	 	}); 	 
		
	$('#activateStudentsReset').on("click",function() {
			resetActivateStudentSelection();
	});
		
    $(document).ready(function(){
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
	});
    
    $.validator.addMethod("dateUS",
			    function(value, element) {
			        return value.match(/^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/[0-9]{4}$/);
			    });
	
	$('#activeStudentForm').validate({
			ignore: "",
			rules: {
				legalFirstName: {required: true},
				legalLastName: {required: true},				
				localStudentIdentifier: {maxlength: 20},
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
				stateEntryDateEnrollment: {dateUS: {
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
				schoolEntryDateEnrollment: {required: true, dateUS: {
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
				hispanicEthnicity: {required: true},
				assessmentProgramsStudent: {required: true}, 			
				esolParticipationCode: {required: true},				
				comprehensiveRace:  {required: true},				
				gender:  {required: true},				
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
			}, validateLessThanThousandYear :true}
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
	
	
	$("#saveEnrollment").on("click",function(event) {
		if($('#activeStudentForm').valid()){ 
			event.preventDefault();
			activateStudent();
			$('#activeStudentDiv').dialog("close");
		}else{
			$('#messageActivateStudent').html('<span class="error_message ui-state-error">Fill in all required fields.</span>').show();		
			$('html, body').animate({
				scrollTop: $("#messageActivateStudent").offset().top
			}, 0);
    		setTimeout(function(){ $("#messageActivateStudent").hide(); },3000);
		}
	});

		
	function activateStudent(){
		var enrollId = $('#activeEnrollmentId').val();
		var orgDistrictId =  $("#activeStudentDistrict").val();
		var orgSchoolId = $("#activeStudentSchool").val();
		var gradeId = $("#activateStudentCurrentGradeSelect").val();
		var studentId = $("#activateStudentId").val();
		
		var formData = $('#activeStudentForm').serializeArray();
		formData.push({name: 'enrollId', value: enrollId});
		formData.push({name: 'orgDistrictId', value: orgDistrictId});
		formData.push({name: 'orgSchoolId', value: orgSchoolId});
		formData.push({name: 'gradeId', value: gradeId});
		formData.push({name: 'studentId', value: studentId});
		
		var activateStudentAccountabilitySchoolSelectStudent = $('#activateStudentAccountabilitySchoolSelectStudent').find(":selected").attr('code');
		var activateStudentAccountabilityDistrictSelectStudent = $('#activateStudentAccountabilityDistrictSelectStudent').find(":selected").attr('code');
		
		if(activateStudentAccountabilitySchoolSelectStudent==undefined) activateStudentAccountabilitySchoolSelectStudent = '';
		if(activateStudentAccountabilityDistrictSelectStudent==undefined) activateStudentAccountabilityDistrictSelectStudent = '';
		
		formData.push({name: 'accountabilitySchoolStudentIdentifier', value: activateStudentAccountabilitySchoolSelectStudent});
		formData.push({name: 'accountabilityDistrictStudentIdentifier', value: activateStudentAccountabilityDistrictSelectStudent});
		
		if(!isEmpty(orgDistrictId) && !isEmpty(orgSchoolId) && !isEmpty(gradeId)){
		$.ajax({
 	 			url: 'reActivateStudent.htm',					
 	 			dataType: 'json',
 	 			data: formData, 	 			
 	 			type: "POST",
 	 			success: function(response) {
 	 				if(response.response == 'Success'){ 	 					
 	 					activateStudentConfirmation();	 	 					
 	 				}	 	 				
 	 			}
 	 	});	
		}
	}
	
	
	function resetActivateStudentSelection() {
		$('#activeStudentDiv').dialog('close');	 		
		$("#findStudentStateId").val('');
 	}
	
}
