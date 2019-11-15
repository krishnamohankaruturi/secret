/**
 * 
 */

$(function(){
	
	$(function() {
	    $("#manageOrgForm").submit(function() { 
	    	return false; 
	    });
	});
	
	$('#dlmtestModelTd').hide();
	
	$('#manageOrgTestModels').select2({
		placeholder:'Select',
		multiple: false,
		selectedList: 1,
		header: ''
	});
	

	$('#manageOrgAssessmentProgram').trigger('change.select2').select2({multiple: true});
	
	$("#manageOrgForm").validate({
		ignore: '',
		submitHandler: function(){
			
		},
		rules: {
			manageOrganizationName: {required: true, maxlength: 100},		
			manageDisplayIdentifier: {required: true, maxlength: 100},		
			manageOrgAssessmentProgram: {required: true},	
			manageReportYear: {required: true}
		}
	});
	
	$('#manageOrgTestModels').trigger('change.select2');
		if($("#manageOrgtypeCode").val() != "ST" && $("#manageOrgtypeCode").val() != "SCH"){
			$("#testTimeSpan").hide();
			$("#testDaysDiv1").hide();
			$("#testDaysDiv2").hide();
		}
		showTestModel();
});

$.validator.addMethod("manageOrgReportYearValidation", function(value, element) {
    return (parseInt(value) > 2014);
}, "Report year should be greater than 2014");

$.validator.addMethod('manageOrgReportYearNumeric', function (value) {
       return /^[0-9]+$/.test(value);
}, '4 digits year is required');


$("#manageOrgAssessmentProgram").on("change",function() {
	showTestModel();
});

$("#manageOrgReset").on("click",function(){	
   $("#manageOrgForm")[0].reset();
   $("#errorMessage").html('');
   showTestModel();
});

$('#manageOrgSave').on("click",function(){
	 var testBeginTime =  $('#manageTestBeginTime').val();
	 var testEndTime = $('#manageTestEndTime').val();
	 if(testBeginTime != null && testBeginTime != ''){
		 if(dateValidator(testBeginTime) == false){
			 $('#errorMessage').html('Please enter valid test begin time format - HH:MM AM/PM');
			 return false;
		 }
	 }
	 if(testEndTime != null && testBeginTime != ''){
		 if(dateValidator(testEndTime) == false){
			 $('#errorMessage').html('Please enter valid test end time format - HH:MM AM/PM');
			 return false;
		 }
	 }
	 if($("#manageOrgForm").valid()){
		// var data = $(':input[value!=""]',  $("#manageOrgForm")).serializeArray();
		 $.ajax({
				url: 'saveManageOrganization.htm',
				type: 'POST',
				data: {
					id: $('#manageOrgId').val(),
					organizationName: $('#manageOrganizationName').val(),
					displayIdentifier: $('#manageDisplayIdentifier').val(),
					manageOrgAssessmentProgram: $('#manageOrgAssessmentProgram').val(),
					manageOrgTestModels: $('#manageOrgTestModels').val(),
					reportYear: $('#manageReportYear').val(),
					typeCode: $('#manageOrgtypeCode').val(),
					currentSchoolYear: $('#manageSchoolYear').val(),
					testBeginTime: $('#manageTestBeginTime').val(),
		 			testEndTime: $('#manageTestEndTime').val(),
		 			testDays: $("input.testDays:checked").val()
				}
		 }).done(function(data){
					if(data.success){						
						 $('#manageOrganizationDiv').dialog('close');
							$('#manageOrganizationSuccessMessage').show();
							setTimeout(function() {
								$('#manageOrganizationSuccessMessage').hide();
							}, 3000);
					}
		});
	 }else{
		 
	 }
});

function dateValidator(testingTimes){
	var inputTime = testingTimes.split(" ");
	var timeDigit = inputTime[0].split(":");
	if(inputTime.length != 2 || timeDigit.length != 2){
		return false;
	}
	else if(inputTime[1].trim() != 'AM' && inputTime[1].trim() != 'PM'){
		return false;
	}
	else if(timeDigit[0] > 12 || timeDigit[0] < 1 || timeDigit[1] > 59){
		return false;
	}
	else{
		return true;
	}
}

function showTestModel(){
	var manageCheckTestingModel=false;
	
	 $("#manageOrgAssessmentProgram option:selected").each(function(){
         if($(this).attr('data-abbName')=='DLM') 
        	 manageCheckTestingModel=true;        
    }); 
	
	  if(manageCheckTestingModel){
		     $("#dlmtestModelTd").show();  
		     $('#manageOrgTestModels').addClass('required');
	  }else{
		     $('#manageOrgTestModels').val('').trigger('change.select2');
		     $("#dlmtestModelTd").hide();
		     $('#manageOrgTestModels').removeClass('required');
      }
}