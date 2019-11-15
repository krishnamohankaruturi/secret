
$(function(){	
	
	$(function() {
	    $("#editOrganizationForm").submit(function() { 
	    	return false; 
	    });
	});
	
	$("#confirmDialogEditOrg").dialog({
	    autoOpen: false,
	    modal: true
	});

	 $("#EditOrgReset").on("click",function(){
		 var orgName = $("input[type=hidden]#orgName").val();
		 $("input[type=text]#organizationName").val(orgName);
		 $("#editOrganizationForm")[0].reset();
		 $("#errorMessage").html('');
	 });	 
	 
	 $("#editOrgSave").off('dblclick');	   
	 $("#editOrgSave").dblclick(function(e){
		  e.stopPropagation();
		  e.preventDefault();
		  return false;
	 });	
	 
	 $("#editOrgSave").on("click",function(){
		 var orgName = $("#editOrganizationForm #organizationName").val();
		 if(orgName!=null && orgName!=undefined) orgName=orgName.trim();
		 if(orgName!=null && orgName!=''){
			 var testBeginTime =  $('#editTestBeginTime').val();
			 var testEndTime = $('#editTestEndTime').val();
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
			 confirmEditOrg();		 
		 }else{
			 $("#editOrganizationForm").valid();			
		 }
	 });	

});

function confirmEditOrg(){
		$("#confirmDialogEditOrg").dialog({
		width: 'auto',
		create: function(event, ui){
		   
		 },
		open: function() {
			 $('#continueButton').hide();
		},
		   buttons : {
		     "Continues" :  {
		    	 id: 'continueButton',
		    	 text:'Continues',
		    	 click: function(evt){
		    		 editOrgSaveHandler(evt);
		    		 $(this).dialog("close");
		    		  	 },
		     },
		     "Continue" :{
		    	 class: 'continueButton',
		    	 text:'Continue',
		    	 click:function(evt){
		    		 editOrgSaveHandler(evt);
		    		 $(this).dialog("close");
		    	   	},
		      },
		     "Cancel" :{
		    	 class: 'cancelButton',
		    	 text:'Cancel',
		    	 click:function(){
		    		 $(this).dialog("close");
		    		  	},
		      }
		     },
		     close: function(event, ui)
		        {
		    	 console.log(this);
		    	 $(this).dialog('destroy').remove();
		        }
		 });
	$("#confirmDialogEditOrg").dialog("open");
	return ;
	
}

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

function editOrgSaveHandler(e){
	
	$("#editOrgSave").addClass('ui-state-disabled');
	if($('#editOrganizationForm').valid()) {
		
		$('#message').html('');
		$('#message').fadeIn();
		var orgName = $("#editOrganizationForm #organizationName").val();
		if(orgName != null && orgName!= undefined && orgName!='') orgName=orgName.trim();
		var orgId = $("#organizationIdForEdit").val();
		var orgTypeCode = $("#orgTypeCode").val();
		var testBeginTime = $('#editTestBeginTime').val();
		var testEndTime = $('#editTestEndTime').val();
		var testDays = $("input.editTestDays:checked").val();
		if(testBeginTime == undefined || testBeginTime == ''){
			testBeginTime = null;
		}
		if(testEndTime == undefined || testEndTime == ''){
			testEndTime = null;
		}
		if(testDays == undefined || testDays == ''){
			testDays = null;
		}
		
		$.ajax({
			url: 'editSaveOrganization.htm',				
			type: "POST",
			data:{
				orgName : orgName,
				orgId : orgId,
				orgTypeCode : orgTypeCode,
				testBeginTime : testBeginTime,
				testEndTime : testEndTime,
				testDays : testDays
			}
			}).done(function(response) {
				if(response.result == "success") {
					resetOrgSelection("all");
					$("#actions").val("viewOrg");
					$('#message').html('<span class="info_message ui-state-highlight">Successfully edited organization.</span>').show();
					$('#editOrganization').trigger("newOrg");
				} else if(response.result == "failed") {
					$('#message').html('<span class="error_message ui-state-error">Failed to edit Organization.</span>').show();
					$(this).off('click');
				} 
	    		$("#editOrgSave").removeClass('ui-state-disabled');
	    		$('#message').delay(5000).fadeOut('slow');
	    		$("#editOrganizationDiv").dialog('close');
	    		
	    		var $gridAuto = $("#viewOrgGridTableId");
	 			$gridAuto.jqGrid('setGridParam',{
	 				datatype:"json", 
	 				loadonce: false ,
	 				url: 'getOrgsToView.htm?q=1', 
	 				search: true, 
	 			}).trigger("reloadGrid");
	 			
			}).fail(function() {
				$('#message').html('<span class="error_message ui-state-error">Failed to edit Organization.</span>').show();
				$(this).off('click');
	    		$("#editOrgSave").removeClass('ui-state-disabled');
	    		$("#editOrganizationDiv").dialog('close');
	    	   });
		
	} else {
		$('#message').html('<span class="error_message ui-state-error">Correct validation errors.</span>').show();
		$(this).off('click');
		$("#editOrgSave").removeClass('ui-state-disabled');
	};
};

