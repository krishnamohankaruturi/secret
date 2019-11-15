$(document).ready(function(){
	$("#resetSchoolYearBtn").on("click",function() {	
		resetAnnualSchoolYear();
	});
	
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required')) {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});
});
function initAnnualFCSReset(){		
	$('#resetFCSForm').validate({
			ignore: "",
			rules: {				
				dlmStateSelect: {required: true}
			}
	});
	
	refreshFCStates();
	
	$("#resetFCStatusBtn").on("click",function(e){
		if($('#resetFCSForm').valid()){ 
			$("#resetFCStatusBtn").prop("disabled",true);
			$('#resetFCStatusBtn').removeClass('btn_blue');
			$('#resetFCStatusBtn').addClass('btn_disabled');
			var formData = $('#resetFCSForm').serializeArray();
			
			$.ajax({
				url: 'resetFCStatus.htm',
			    dataType: 'json',
			    type: 'POST',
			    data:formData			
			}).done(function(response){
		    	if (response.valid) {
		    		$('#updatefcs-success').show();
			    	$("#resetFCStatusBtn").prop("disabled",false);
					$('#resetFCStatusBtn').removeClass('btn_disabled');
					$('#resetFCStatusBtn').addClass("btn_blue");
					refreshFCStates();
		    	}
		    	
		    	setTimeout("aart.clearMessages()", 3000);
		    	
		    }).fail(function(jqXHR, textStatus, errorThrown) {
				$('#updatefcs-error').show();
				setTimeout("aart.clearMessages()", 3000);
			});
		}
	});
	
	annualResetStates();

	$('#resetSchoolYearFilter').orgFilter('option','requiredLevels',[70]);
	$('#resetFCSFilter').orgFilter('option','requiredLevels',[70]);
}

function refreshFCStates(){
	var me = this;	
	$('.messages').html('').hide();	
	$('#dlmStateSelect').val('').trigger('change.select2');
	$("#resetFCStatusBtn").prop("disabled",false);
	$('#resetFCStatusBtn').removeClass('btn_disabled');
	$('#resetFCStatusBtn').addClass("btn_blue");
	$.ajax({
		url: 'getDLMStates.htm',
		dataType: 'JSON',
		type: "POST"
		
	}).done(function(data) {	
		
		if (data !== undefined && data !== null && data.length > 0) {
			$('#dlmStateSelect').html('');
			
			for (var i = 0, length = data.length; i < length; i++) {
				optionText = data[i].organizationName;
				$('#dlmStateSelect').append($('<option></option>').val(data[i].id).html(optionText));					
			}					
			$('#dlmStateSelect').select2({placeholder:'Select', multiple: true});
			if (data.length != 1) {
				$('#dlmStateSelect').prop('disabled', false);
			}
		} else {
			 $('#dlmStateSelect').select2({placeholder:'Select', multiple: true});
			$("#resetFCStatusBtn").prop("disabled",true);
			$('#resetFCStatusBtn').removeClass('btn_blue');
			$('#resetFCStatusBtn').addClass('btn_disabled');	
		}
		
	});
}


function annualResetStates(){
	var schoolYearOptionText='';
	$("#resetSchoolYearBtn").prop("disabled",false);
	$('#resetSchoolYearBtn').removeClass('btn_disabled');
	$('#resetSchoolYearBtn').addClass("btn_blue");
	$('#schoolYearStateSelect').prop('disabled', false);
	$('#schoolYearStateSelect').val('').trigger('change.select2');
    $('#schoolYearStateSelect').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
    
	$.ajax({
		url: 'getAnnualResetStates.htm',
		dataType: 'JSON',
		type: "GET"
	}).done(function(data) {
		   if (data !== undefined && data !== null && data.length > 0) {
			    $('#schoolYearStateSelect').html('');
				for (var i = 0, length = data.length; i < length; i++) {
					schoolYearOptionText = data[i].organizationName;
					$('#schoolYearStateSelect').append($('<option></option>').val(data[i].organizationId).html(schoolYearOptionText));					
				}		
				$('#schoolYearStateSelect').select2({placeholder:'Select', multiple: true});
				
				if (data.length != 1) {
					$('#schoolYearStateSelect').prop('disabled', false);
				}
		   } 
		   else {
			    $('#schoolYearStateSelect').select2({placeholder:'Select', multiple: true});
				$("#resetSchoolYearBtn").prop("disabled",true);
				$('#resetSchoolYearBtn').removeClass('btn_blue');
				$('#resetSchoolYearBtn').addClass('btn_disabled');
			}

		});		
}

function resetAnnualSchoolYear(){
	$('#resetSchoolYearForm').validate({
		ignore: "",
		rules: {				
			schoolYearStateSelect: {required: true}
		}
	});
	
	if($('#resetSchoolYearForm').valid()){ 
		
		$("#resetSchoolYearBtn").prop("disabled",true);
		$('#resetSchoolYearBtn').removeClass('btn_blue');
		$('#resetSchoolYearBtn').addClass('btn_disabled');
		
		var formData=[];
	    $("#schoolYearStateSelect option:selected").each(function(){
	    	formData.push($(this).val());
	    });
		
	    $.ajax({
			url: 'resetAnnualSchoolYear.htm',
			dataType: 'text',
		    type: 'POST',
		    data: {
				data: JSON.stringify(formData)
			}			
		}).done(function(response) {
			var responseObject = JSON.parse(response);
			
			if( responseObject.resetComplete){
				$("#resetSchoolYearBtn").prop("disabled",false);
				$('#resetSchoolYearBtn').removeClass('btn_disabled');
				$('#resetSchoolYearBtn').addClass("btn_blue");
				
				$('#schoolYearDatesError').html('');
				$('#schoolYearDatesError').hide();
				$('#schoolYearDatesSuccess').html("School Year successfully reset for the selected states.");
				$('#schoolYearDatesSuccess').show();
				annualResetStates();
				$('html, body').animate({
					scrollTop: $("#schoolYearDatesSuccess").offset().top
				}, 1500);
				setTimeout(function(){ $("#schoolYearDatesSuccess").hide(); },3000);
			}
			else{
				$('#schoolYearDatesError').html('School Year was not reset for the selected states.');
				$('#schoolYearDatesError').show();
				$('html, body').animate({
					scrollTop: $("#schoolYearDatesError").offset().top
				}, 1500);
				setTimeout(function(){ $("#schoolYearDatesError").hide(); },3000);
			}
		
	    }).fail(function(jqXHR, textStatus, errorThrown) {
			$('#schoolYearDatesError').html('School Year was not reset for the selected states. Error Thrown.');
			$('#schoolYearDatesError').show();
			$('html, body').animate({
				scrollTop: $("#schoolYearDatesError").offset().top
			}, 1500);
			setTimeout(function(){ $("#schoolYearDatesError").hide(); },3000);	
		});
		
	}
	
}

