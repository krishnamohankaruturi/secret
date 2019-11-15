 
    function addStudentsInit(){
    	
    	var me = this;
		var apSelect = $('#assessmentProgramSelectStudent'), optionText='';
		$('.messages').html('').hide();
		apSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$.ajax({
			url: 'getCurrentUserAssessmentPrograms.htm',
			dataType: 'JSON',
			type: "POST"
		}).done(function(data) {	
			
			if (data !== undefined && data !== null && data.length > 0) {
				$('#assessmentProgramSelectStudent').html('');
				
				for (var i = 0, length = data.length; i < length; i++) {
					optionText = data[i].programName;
					if(data[i].id == $('#hiddenCurrentAssessmentProgramId').val()){
						apSelect.append($('<option selected=\''+'selected'+'\'></option>').val(data[i].id).html(optionText));
					} else {
						//apSelect.append($('<option></option>').val(data[i].id).html(optionText).prop("disabled", true));
					}
				}					
				$('#assessmentProgramSelectStudent').select2({placeholder:'Select', multiple: true});
				if (data.length != 1) {
					apSelect.prop('disabled', false);
				}
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('.messages').html(me.options.newreport_no_assessmentprogram).show();
			}
			
		});
    	
 		gAddStudentsLoadOnce = true;
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
 		
 		$("#stateEntryDate,#schoolEntryDate,#districtEntryDate,#dobDate,#esolProgramEntryDate,#usaEntryDate").datepicker();
 		
 		$('#addStudentsFormDiv .bcg_select').select2({
 			placeholder:'Select', 
 			multiple: false,
 			allowClear : true
 		});
 	 	
 		$.validator.addMethod("dateUS",
 			    function(value, element) {
 			        return value.match(/^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/[0-9]{4}$/);
 			    });
 		
 		$('#addStudentsForm').validate({
 			ignore: "",
 			rules: {
 				legalFirstName: {required: true},
 				legalLastName: {required: true},
 				stateSchoolId: {required: true,maxlength: 50},
 				localSchoolId: {maxlength: 20},
 				dobDate: {required: true,  dateUS: {
					depends: function (element) {
						if(!$(element).is(":hidden")){
							return true;	
						}
						else 
						{
							return false;	
						}
					}
			}, validateLessThanThousandYear :true},
 				stateEntryDate: { dateUS: {
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
 				schoolEntryDate: {required: true,  dateUS: {
					depends: function (element) {
						if(!$(element).is(":hidden")){
							return true;	
						}
						else 
						{
							return false;	
						}
					}
			}, validateLessThanThousandYear :true},
 				districtEntryDate: {dateUS: {
					depends: function (element) {
						if(!$(element).is(":hidden") && $(element).val()!=''){
							return true;	
						}
						else 
						{
							return false;	
						}
					}
			},  validateLessThanThousandYear :true},
 				currentSchoolYearSelect: {required: true},
 				currentGradeSelect: {required: true},
 				hispanicEthnicitySelect: {required: true},
 				assessmentProgramsStudent: {required: true}, 			
 				esolParticipationCodeSelect: {required: true},
 				comprehensiveRaceSelect:  {required: true},
 				gender:  {required: true},
 				comprehensiveRace: {required: true},
 				hispanicEthnicity: {required: true},
 				primaryDisability: {required: true},
 				assessmentProgramsStudent: {required: true},
 				esolParticipationCode: {required: true},
 				addStudentDistrict: {required: true},
 				addStudentSchool: {required: true},
 				currentGrade: {required: true},
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
			},  validateLessThanThousandYear :true},
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
			},  validateLessThanThousandYear :true}
 			},
 			messages: {
 			    legalFirstName : {
					required : "This field is required.",
				},					
				legalLastName : {
					required : "This field is required.",
				},	 
 				dobDate: {
 					required : "This field is required.", 					
 					dateUS : "Please enter valid Date of Birth",
 					validateLessThanThousandYear : "Date should be greater than year 1000"
 				},
 				stateEntryDate: { 					
 					dateUS : "Please enter valid State Entry Date",
 					validateLessThanThousandYear : "Date should be greater than year 1000"
 				},
 				schoolEntryDate: {
 					required : "This field is required.",
 					dateUS : "Please enter valid School Entry Date",
 					validateLessThanThousandYear : "Date should be greater than year 1000"
 				},
 				districtEntryDate: {  					
 					dateUS : "Please enter valid District Entry Date",
 					validateLessThanThousandYear : "Date should be greater than year 1000"
 				},
 				esolProgramEntryDate: { 					
 					dateUS : "Please enter valid ESOL Program Entry Date",
 					validateLessThanThousandYear : "Date should be greater than year 1000"
 				},
 				usaEntryDate: { 					
 					dateUS : "Please enter valid USA Entry Date",
 					validateLessThanThousandYear : "Date should be greater than year 1000"
 				},
 				gender : {
					required : "This field is required.",
				},
				comprehensiveRace : {
					required : "This field is required.",
				},
				hispanicEthnicity : {
					required : "This field is required.",
				},
				primaryDisability : {
					required : "This field is required.",
				},
				assessmentProgramsStudent : {
					required : "This field is required.",
				},
				esolParticipationCode : {
					required : "This field is required.",
				},
				addStudentDistrict : {
					required : "This field is required.",
				},
				addStudentSchool : {
					required : "This field is required.",
				},
				currentGrade : {
					required : "This field is required.",
				}
		     }
 		});
 		$('#dobDate').on("change",function(){
 			    $(this).valid();
 		});
 		$('#genderSelect').on("change",function(){
 			    $(this).valid();
 		});
 		$('#comprehensiveRaceSelect').on("change",function(){
			    $(this).valid();
		});
 		$('#hispanicEthnicitySelect').on("change",function(){
			    $(this).valid();
		});
 		$('#primaryDisabilitySelect').on("change",function(){
			    $(this).valid();
		});
 		$('#currentGradeSelect').on("change",function(){
 				$(this).valid();
		});
 		$('#schoolEntryDate').on("change",function(){
 				$(this).valid();
 		});
 		
 		$('#addStudentDistrict').on("change",function(){
 			$(this).valid();
 			var orgDistrictIdentifier = $('#addStudentDistrict').find(":selected").attr('code');
 			if(orgDistrictIdentifier != undefined && orgDistrictIdentifier != ""){
 				$('#residenceDistrictId').val(orgDistrictIdentifier);
 				addStudentSchoolSelection();	
 			}
 			else 
 			{
 				$('#residenceDistrictId').val('');
 				$('#attendanceSchoolId').val('');
 				$('#addStudentSchool').html("");
 	 			$('#addStudentSchool').append($('<option></option>').val("").html("Select")).trigger('change.select2');
 	 			
 			}
 		});
 		
 		$('#addStudentSchool').on("change",function(){
 			$(this).valid();
 			var orgSchoolIdentifier = $('#addStudentSchool').find(":selected").attr('code');
 			if(orgSchoolIdentifier != undefined && orgSchoolIdentifier != ""){
 				$('#attendanceSchoolId').val(orgSchoolIdentifier);
 			}
 			else 
 				$('#attendanceSchoolId').val('');
 		});
 		
 		
 		$('#createStudentsReset').on("click",function() {
 			resetAddStudentSelection();
 			$('#stateStudentId').val('');
 			$('#addStudentsPage').hide(); 	
 			$("#stateStudentId").attr("disabled", false);
 			$("#stateStudentIdentifierAdd").removeClass('ui-state-disabled');
 		});
 		
$("#createStudentsSave").on("click",function(){
	if($('#addStudentsForm').valid()){  
		$("#residenceDistrictId").removeAttr('disabled');
		$("#residenceDistrictId").val($('#addStudentDistrict').find(":selected").attr('code'));
		var formData = $('#addStudentsForm').serializeArray();
		$.each(formData , function(i, field) {
			formData[i].value = $.trim(field.value);
		});
		$("#residenceDistrictId").attr('disabled', 'disabled');
		formData.push({name: 'orgId', value: $('#addStudentSchool').val()});
		formData.push({name: 'accountabilitySchoolIdentifier', value: $('#accountabilitySchoolSelectStudent').find(":selected").attr('code')});
		formData.push({name: 'accountabilityDistrictIdentifier', value: $('#accountabilityDistrictSelectStudent').find(":selected").attr('code')});
		$.ajax({
			url: 'isStudentDemographicValueExists.htm',					
			dataType: 'json',
		    type: "POST",
			data:formData
		}).done(function(data){
			if(data.demographicMatches == true){
				$("#addStudentDemographicWarningMessagePopup").dialog({
					resizable: false,
					width: 540,
					modal: true,
					autoOpen: true,
					title: 'Warning',
					dialogClass: 'addStudentDialog',
					create: function(event, ui){				
					},
	 				buttons: {		
	 					"Continue" : function(){
	 						$(this).dialog('close');
	 						afterDemographicValidation(formData);
	 					},									
		 		        "Cancel" : function(){			
		 					$(this).dialog('close');
		 				}
	 				},
				});
			}else {
				afterDemographicValidation(formData);
			}
		});
	}
});	

function afterDemographicValidation(formData){	
	$.ajax({
		url: 'addStudents.htm',					
		dataType: 'json',
        type: "POST",
		data:formData
	}).done(function(data){
		if(data.success){
 			$("#stateStudentId").attr("disabled", false);
 			$("#stateStudentIdentifierAdd").removeClass('ui-state-disabled');
			$('#addStudentsPage').hide();
			$("#stateStudentId").val('');
			$(".addStudentMessages").show();
			$('#successMessage').show();
			resetAddStudentSelection();
		}else if(data.rejected){
			if(data.rejectReason != null){
				$('.addStudentMessages').html('<span class="error_message ui-state-error selectAllLabels">'+data.rejectReason+'</span>').show();
			}else
				$('#failMessage').show();
			$(".addStudentMessages").show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function(){ $("#orgARTSmessages").hide(); },3000);
		}else if(data.duplicateStateStudentIdFound){
			$(".addStudentMessages").show();
			$('#duplicateMessage').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function(){ $("#orgARTSmessages").hide(); },3000);
		}else if(data.nopermit){
			$(".addStudentMessages").show();
			$('#ksPermissionDeniedMessage').show();
		}
	}).fail(function() {
		$(".addStudentMessages").show();
		$('#failMessage').show();
		setTimeout("aart.clearMessages()", 3000);
		setTimeout(function(){ $("#orgARTSmessages").hide(); },3000);
	});
}
 				
 		/*	}
 			});*/
 		
 	/*	var esolRulesToChange = {
 				esolProgramEntryDate: {required: true, dateUS: true},
 				usaEntryDate: {required: true, dateUS: true}
 	 	};*/
 		
 		/*$('#firstLanguageSelect').change(function() {
 	 		if($('#firstLanguageSelect').val() == "0") {
 	 			//$('#esolParticipationCodeSelect').val("0").trigger("change.select2");
 	 			//$('#esolParticipationCodeDiv').hide();
 	 			//$('#esolProgramEntryDateDiv').hide();
 	 			//$('#usaEntryDateDiv').hide();
 	 			//removeRules(esolRulesToChange);
 	 		}else{
 	 			$('#esolParticipationCodeSelect').val('').trigger('change.select2');
 	 			$('#esolParticipationCodeDiv').show();
 	 			$('#esolProgramEntryDateDiv').show();
 	 			$('#usaEntryDateDiv').show();
 	 			//addRules(esolRulesToChange);
 	 		}
 	 	});*/
 		
 		$('#esolParticipationCodeSelect').on("change",function() {
 			$(this).valid();
 	 		if($('#esolParticipationCodeSelect').val() == "0" || $('#esolParticipationCodeSelect').val() == "5") {
 	 			$('#esolProgramEntryDateDiv').hide();
 	 			$('#usaEntryDateDiv').hide();
 	 			//removeRules(esolRulesToChange);
 	 		}else{
 	 			$('#esolProgramEntryDateDiv').show();
 	 			$('#usaEntryDateDiv').show();
 	 			//addRules(esolRulesToChange); 	 			
 	 		}
 	 	}); 	 
 		
 		$.ajax({
 	 	 			url: 'getAddStudentsFormData.htm',					
 	 	 			dataType: 'json',
 	 	 			data:{
 	 	 			},
 	 	 			type: "POST"
 	 	 	}).done(function(response) {
	 				if(response.primaryDisabilityCode != null && response.primaryDisabilityCode.length > 0){
 	 					 $.each(response.primaryDisabilityCode, function (index, value) {
 	 						 $('#primaryDisabilitySelect').append(new Option(value.categoryCode+" - "+ value.categoryName, value.categoryCode));	
 	 					 });
 	 					 $('#primaryDisabilitySelect').trigger('change.select2');
 	 				}  
 	 				if(response.firstLanguage != null && response.firstLanguage.length > 0){
 						 $.each(response.firstLanguage, function (index, value) {
 							 $('#firstLanguageSelect').append(new Option(value.categoryCode+" - "+ value.categoryName, value.categoryCode));	
 						 });
 						 $('#firstLanguageSelect').trigger('change.select2');
 					}
 	 				if(response.comprehensiveRace != null && response.comprehensiveRace.length > 0){
 						 $.each(response.comprehensiveRace, function (index, value) {
 							 $('#comprehensiveRaceSelect').append(new Option(value.categoryCode+" - "+ value.categoryName, value.categoryCode));	
 						 });
 						 $('#comprehensiveRaceSelect').trigger('change.select2');
 					}
 	 				if(response.currentGrade != null && response.currentGrade.length > 0){
						 $.each(response.currentGrade, function (index, value) {
							 //if(value.gradeLevel)
							 $('#currentGradeSelect').append(new Option(value.name, value.abbreviatedName ));	
						 })
						 $('#currentGradeSelect').trigger('change.select2');
					} 	 	 				
 	 				if(response.esolParticipationcode != null && response.esolParticipationcode.length > 0){
						 $.each(response.esolParticipationcode, function (index, value) {
							 $('#esolParticipationCodeSelect').append(new Option(value.attributeValue+" - "+ value.attributeName, value.attributeValue ));	
						 })
						 $('#esolParticipationCodeSelect').trigger('change.select2');
					}
 	 				if(response.generation != null && response.generation.length > 0){
						 $.each(response.generation, function (index, value) {
							 $('#generation').append(new Option(value.attributeValue, value.attributeValue ));//For Different sorting order we have modified attribute name so in this drop down values and option is same.  	
						 })
						 $('#generation').trigger('change.select2');
					}
 	 				if(response.gender != null && response.gender.length > 0){
						 $.each(response.gender, function (index, value) {
							 $('#genderSelect').append(new Option(value.attributeName, value.attributeValue ));	
						 })
						 $('#genderSelect').trigger('change.select2');
					}
 	 				if(response.hispanicEthinicity != null && response.hispanicEthinicity.length > 0){
						 $.each(response.hispanicEthinicity, function (index, value) {
							 $('#hispanicEthnicitySelect').append(new Option(value.attributeName, value.attributeValue ));	
						 })
						 $('#hispanicEthnicitySelect').trigger('change.select2');
					}
 	 				if(response.giftedStudent != null && response.giftedStudent.length > 0){
						 $.each(response.giftedStudent, function (index, value) {
							 $('#giftedStudentSelect').append(new Option(value.attributeName, value.attributeValue ));	
						 })
						 $('#giftedStudentSelect').trigger('change.select2');
					}
 	 			});	
 		
	}	
	
/*	
	function addRules(rulesObj){
	    for (var item in rulesObj){
	       $('#'+item).rules('add',rulesObj[item]);  
	    }
	}
	function removeRules(rulesObj){
	    for (var item in rulesObj){
	       $('#'+item).rules('remove');  
	    }
	}
*/	
	
	
    $(document).ready(function(){
    	    	
		$.validator.addMethod("validateLessThanThousandYear", function (value, element) {						
			 var now = new Date('12/31/0999');
			 var myDate = new Date(value);
			 return this.optional(element) || myDate > now;
		});
    	
    	$("editStudentForm").submit(function(){
    		var assessmentPrograms = new Array();
    			assessmentPrograms = $("#assessmentProgramSelectStudent").val();
    			
    			if(assessmentPrograms == null || assessmentPrograms.length < 1) {
        			validData = false;
        		}
        });
     		
     	});
    
		
		//Accountability Distirct change function	
		$('#accountabilityDistrictSelectStudent').on("change",function() {	
			accountabilitySchoolSelection();	
		}); 
		 
		 
		function accountabilitySchoolSelection(){
			$('#accountabilitySchoolSelectStudent').html("");
			$('#accountabilitySchoolSelectStudent').append($('<option></option>').val("").html("Select")).trigger('change.select2');
				var districtOrgId = $('#accountabilityDistrictSelectStudent').val();
				if(!isEmpty(districtOrgId)){
				$.ajax({
					url: 'getAccountabilitySchools.htm',
					dataType: 'json',
					data: { 
						orgId : districtOrgId	
					},
					async:false,
					type: "GET"
				}).done(function(schoolOrgs) {
					$.each(schoolOrgs, function(i, schoolOrg) {
						$('#accountabilitySchoolSelectStudent').append($('<option></option>').attr("value", schoolOrg.id).attr("code",schoolOrg.displayIdentifier).text(schoolOrg.displayIdentifier+" - "+schoolOrg.organizationName));	
					});
					$('#accountabilitySchoolSelectStudent').trigger('change.select2');
				});
			}
				
		}
	   
	function getContractionOrg(){

			$.ajax({
				url: 'getContractOrgDetails.htm',					
				dataType: 'json',
				data:{
					
				},
				type: "GET"
			}).done(function(response) {
				if(response.organization.currentSchoolYear != null){
					var year = response.organization.currentSchoolYear;
					$('#currentSchoolYear').text(year);	
					loadDistrictandSchoolDropdowns(response.organization.id);
				}
			});	
			
			
			
	}

 	function loadDistrictandSchoolDropdowns(stateId){
 		
 	 	var addStudentDistrictOrgSelect = $('#addStudentDistrict');
 	 	
 	 	$('#addStudentSchool').html("");
		$('#addStudentSchool').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		$('#accountabilitySchoolSelectStudent').html("");
		$('#accountabilitySchoolSelectStudent').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			
	 		if(globalUserLevel == 20)
		    {
				$.ajax({
					url: 'getChildOrgsWithParentForFilter.htm',
					dataType: 'json',
					data: {
						orgId : stateId,
				    	orgType:'DT'
				    	},				
					type: "POST"
				}).done(function(districtOrgs) {	
					addStudentDistrictOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					addStudentDistrictOrgSelect.val(null).trigger('change.select2');
					if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
						$.each(districtOrgs, function(i, districtOrg) {
							optionText = districtOrgs[i].displayIdentifier+" - "+districtOrgs[i].organizationName;
							addStudentDistrictOrgSelect.append($('<option></option>').val(districtOrg.id).attr("code",districtOrg.displayIdentifier).html(optionText));	
						});
				   if (districtOrgs.length == 1) {
					  addStudentDistrictOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					  addStudentSchoolSelection();	
					} 
					}
					$('#addStudentSchool, #addStudentDistrict').trigger('change.select2');
				});
			}
	
		if(globalUserLevel == 50 || globalUserLevel == 70 ){
			 $.ajax({
					url: 'getOrgsBasedOnUserContext.htm',
					data: {
						orgId : stateId,
			        	orgType:'DT',
			        	orgLevel: 50	
			    	},
					dataType: 'json',
					type: "GET",
					async: false
			 }).done(function(districtOrgs){
					
					addStudentDistrictOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					addStudentDistrictOrgSelect.trigger('change.select2');
					
					if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
						$.each(districtOrgs, function(i, districtOrg) {
							optionText = districtOrgs[i].displayIdentifier+" - "+districtOrgs[i].organizationName; 						
							addStudentDistrictOrgSelect.append($('<option></option>').val(districtOrg.id).attr("code",districtOrg.displayIdentifier).html(optionText));	
						});
				   if (districtOrgs.length == 1) {
					  addStudentDistrictOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					  addStudentSchoolSelection();	
					  } 
					} 
					$('#addStudentSchool, #addStudentDistrict').trigger('change.select2');
					
					
				});
		}
		
		
		
 		var districtOrgSelect = $('#accountabilityDistrictSelectStudent'); 		 
 		 	 		
 		$.ajax({
 				url: 'getAccountabilityDistricts.htm',
 				dataType: 'json',
 				data: { 				
 			    	orgType:'DT'
 			    	},				
 				type: "POST"
 			}).done(function(districtOrgs) {	
 					districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
 					districtOrgSelect.trigger('change.select2');
 					if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
 						$.each(districtOrgs, function(i, districtOrg) {
 							optionText = districtOrgs[i].displayIdentifier+" - "+districtOrgs[i].organizationName;
 							districtOrgSelect.append($('<option></option>').val(districtOrg.id).attr("code",districtOrg.displayIdentifier).html(optionText));
 						});	
 					} 
 					$('#accountabilitySchoolSelectStudent, #accountabilityDistrictSelectStudent').trigger('change.select2');
 				});
 		
 	}
  		
 		function addStudentSchoolSelection(){
 			$('#addStudentSchool').html("");
 			$('#addStudentSchool').append($('<option></option>').val("").html("Select")).trigger('change.select2');
 			var districtOrgId = $('#addStudentDistrict').val();
 				if(!isEmpty(districtOrgId)){
 				$.ajax({
 					url: 'getOrgsBasedOnUserContext.htm',
 					dataType: 'json',
 					data: {
 						orgId : districtOrgId,
 			        	orgType:'SCH',
 			        	orgLevel: 70	
 					},
 					async:false,
 					type: "GET"
 				}).done(function(schoolOrgs) {
 						$.each(schoolOrgs, function(i, schoolOrg) { 							
 							$('#addStudentSchool').append($('<option></option>').attr("value", schoolOrg.id).attr("code",schoolOrg.displayIdentifier).text(schoolOrg.displayIdentifier+" - "+schoolOrg.organizationName));	
 						});		
 						if (schoolOrgs.length == 1) {
 							$("#addStudentSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
 							$("#addStudentSchool").trigger('change');
 						}
 						$('#addStudentSchool').trigger('change.select2');
 					});}
 				
 		}
 		
 		function addStudentDistrictChange(){
 			addStudentSchoolSelection();	
 		}
 	
 		function resetAddStudentSelection() {
 	 	   	//don't reset the whole form
 	 		//reset values that will be changed for the next student entered
 	 		//just refresh the select boxes instead of making round trips to
 	 		//the server for data
 	 		$('#legalFirstName').val("");
 	 		$('#legalMiddleName').val("");
 	 		$('#legalLastName').val("");
 	 		$('#generation').val('').trigger('change.select2');
 	 		$('#dobDate').val("");
 	 		$('#genderSelect').val('').trigger('change.select2');
 	 		$('#currentSchoolYearSelect').val('').trigger('change.select2');
 	 		$('#currentGradeSelect').val('').trigger('change.select2');
 	 		$('#aypSchoolId').val("");
 	 		$('#comprehensiveRaceSelect').val('').trigger('change.select2');
 	 		$('#hispanicEthnicitySelect').val('').trigger('change.select2');
 	 		$('#firstLanguageSelect').val('').trigger('change.select2'); 	
 	 		$('#localSchoolId').val("");
 	 		$('#giftedStudentSelect').val('').trigger('change.select2');
 	 		$('#stateEntryDate').val("");
 	 		$('#schoolEntryDate').val("");
 	 		$('#districtEntryDate').val("");
 	 		$('#primaryDisabilitySelect').val('').trigger('change.select2');
 	 		$('#esolParticipationCodeSelect').val('').trigger('change.select2');
 	 		$('#esolProgramEntryDate').val("");
 	 		$('#usaEntryDate').val(""); 		
 	 		$('#addStudentSchool').val('').trigger('change.select2');
 	 		$('#addStudentDistrict').val('').trigger('change.select2');
 	 		$('#residenceDistrictId').val("");
 	 		$('#attendanceSchoolId').val("");
 	 		$('#accountabilityDistrictSelectStudent').val('').trigger('change.select2');
 	 		$('#accountabilitySchoolSelectStudent').val('').trigger('change.select2');
 	 	}
	