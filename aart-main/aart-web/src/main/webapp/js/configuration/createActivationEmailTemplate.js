	     	
jQuery(function(){
	
	
        var config = {
        		
        		allowedContent : {
        			    $1: {
        			        // Use the ability to specify elements as an object.
        			        elements: CKEDITOR.dtd,
        			        attributes: true,
        			        styles: true,
        			        classes: true
        			    }
        			},
        		
        		extraAllowedContent : 'div[onselectstart,contenteditable,id,class,style,disabled]',        		
	            toolbar:
	            [  
					{ name: 'basicstyles', items: [ 'Bold', 'Italic' , 'Underline', 'Strike'] },
					{ name: 'styles', items: [ 'Styles' ] },
					{ name: 'paragraph', groups: [ 'list'], items: [ 'NumberedList', 'BulletedList' ] },
					{ name: 'clipboard', groups: [ 'undo' ], items: [ 'Undo', 'Redo' ] },
					{ items: [ 'ActivationLinkBtn' ] }
					
	            ]
        };    
              
        jQuery('#emailBody').ckeditor(config);
        
        CKEDITOR.on('instanceReady', function() {
            $(".cke_button__activationlinkbtn_icon").text("Click here to insert activation link");
        });
              
 });

$(function(){
	
	$(function() {
	    $("#createEmailActivationForm").submit(function() { 
	    	return false; 
	    });
	});
	
	$("#statesAlreadyHaving").val('false');
	$("#statelists").html('');	
	$("#hiddenparameters").html('');
	
	var templateId = $("#id").val();	
	if(templateId=='' ||templateId==null || templateId==undefined){
		loadDefaultTemplate();
		
	}	  
	
	if($("#assessmentProgramId").val()=='') {
		$(".checkedAllStates, .unCheckedAllStates").attr("disabled",true); 
	}	
	
	$('textarea#emailBody' ).ckeditor();
	   
	$("#assessmentProgramId").select2({
			placeholder: 'Select',
			multiple: false
	});
		
	$("#statesSelect").select2({
			placeholder: 'Select',
			multiple: true
	});
		
	$("#emailTemplateLists").select2({
			placeholder: 'Select',
			multiple: false		
	});

	$("#emailTemplateClose").click(function(){
	//  $("#confirmDialogStatesMove").dialog('close');
		$("#editActivationEmailDiv").dialog('close'); 
		$(".rightNoButton").trigger('click');
	});
	
	$('input[name=allStates]:radio').change(function () {
		
		if (this.value == 'true') {
        	$('#statesSelect').prop('disabled', true);
        	var templateId = $("#id").val();	
        	if(templateId=='' ||templateId==null || templateId==undefined){
        		$('#statesSelect').val("0").trigger('change.select2');
        	}        	
        	$("#statesSelect").removeClass("required");
        }
        else if (this.value == 'false') {
        	$('#statesSelect').prop('disabled', false); 
        	$("#statesSelect").addClass("required");
        }
    });
	
	
	$('input[name=defaultTemplate]:radio').change(function () {
        if (this.value == 'true') {
        	$('#emailTemplateLists').prop('disabled', true);
        	$('#emailTemplateLists').val("0").trigger('change.select2');
        	$("#emailTemplateLists").removeClass("required");
        	loadDefaultTemplate();   
        }
        else if (this.value == 'false') {
        	$('#emailTemplateLists').prop('disabled', false);
        	$("#emailSubject").val('');
			$("#emailBody").val('');	
			$(".checkedEpLogo").prop("checked",false); 
			$(".unCheckedEpLogo").prop("checked",false);
			$("#emailTemplateLists").addClass("required");
			loadTemplate();
			$("#emailTemplateLists").val([]).trigger('change.select2');
			console.log($('#emailTemplateLists').select2("val") == null);
        }
    });
	
	function loadTemplate(){		
		$.ajax({
			url: 'getTemplateLists.htm',
			data: {					
			},
			dataType: 'json',
			type: "POST",
			async: false,
			success: function(response){				
				var emailTemplates = response.emailTemplateLists;
				var emailTemplateLists = $('select#emailTemplateLists');
				var newOptions='';
				newOptions += '<option value="">Select</option>';	
				if (emailTemplates !== undefined && emailTemplates !== null && emailTemplates.length > 0){
					$.each(emailTemplates, function(i, clremailTemplates) {
						if(emailTemplates[i].id !=''){
							newOptions += '<option role="option" value="' + emailTemplates[i].id + '">' +
							emailTemplates[i].templateName + '</option>';
						}
					});
					$("#emailTemplateLists").html(newOptions);	
					if (emailTemplates.length == 1) {
						emailTemplateLists.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						emailTemplateLists.trigger('change');
					}
					$('#emailTemplateLists').trigger('change.select2');
				}				
	       }
		});		
	}
	
	$('#emailTemplateLists').change(function(){
		
		if($(this).val() !== ''){
			$.ajax({
				url: 'getOldTemplateLists.htm',
				data: {
					templateId :  $(this).val()
				},
				dataType: 'json',
				type: "POST",
				async: false,
				success: function(response){
					var emailActivation = response.emailActivation;
					$("#emailSubject").val(emailActivation.emailSubject);
					$("#emailBody").val(emailActivation.emailBody);
					var includeEpLogo = emailActivation.includeEpLogo;
					if(includeEpLogo){
						$(".checkedEpLogo").prop("checked",true); 
						$(".unCheckedEpLogo").prop("checked",false);
					}
					else{
						$(".unCheckedEpLogo").prop("checked",true); 
						$(".checkedEpLogo").prop("checked",false); 
					}					
		       }
			});
			
		}
		else{
			$("#emailSubject").val('');
			$("#emailBody").val('');	
			$(".checkedEpLogo").prop("checked",false); 
			$(".unCheckedEpLogo").prop("checked",false); 
		}
		
	});
	
	
	
	$('#assessmentProgramId').change(function(){
		var templateId = 0;
		if($(this).val()==''){
			$(".checkedAllStates, .unCheckedAllStates").attr("disabled",true);
			$('#statesSelect').val("0").trigger('change.select2');
			$('#statesSelect').prop('disabled', true);
		}else{
			$(".checkedAllStates, .unCheckedAllStates").attr("disabled",false);
			$(".checkedAllStates").prop("checked",true);
			$('#statesSelect').val("0").trigger('change.select2');
			$('#statesSelect').prop('disabled', true);
		}
		
		if($(this).val()=='' && $("input[name=allStates]").prop("checked")==true){
			$('#statesSelect').prop('disabled', true);
		}
		else if($(this).val()!='' && $("input[name=allStates]").prop("checked")==false){
			$('#statesSelect').prop('disabled', false);
		}
		 
		if($(this).val() !== ''){		
			$.ajax({
				url: 'getOrganizationByAssessmentProgramIdOnly.htm',
				data: {
					assessmentProgramId: $(this).val(),
					templateId : templateId
				},
				dataType: 'json',
				type: "POST",
				async: false,
				success: function(response){
					var orgAssessProgs = response.orgAssessProgs;
					var stateSelect = $('select#statesSelect');
					var newOptions='';
					if (orgAssessProgs !== undefined && orgAssessProgs !== null && orgAssessProgs.length > 0){
						$.each(orgAssessProgs, function(i, clrAssessmentProgram) {
							if(orgAssessProgs[i].organization.id !=''){
								newOptions += '<option role="option" value="' + orgAssessProgs[i].organization.id + '">' +
								orgAssessProgs[i].organization.organizationName + '</option>';
							}
						});
						$("#statesSelect").html(newOptions);	
						if (orgAssessProgs.length == 1) {
							stateSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
							stateSelect.trigger('change');
						}
						$('#statesSelect').trigger('change.select2');
					}
		       }
			});
		}
		
	});
	
	
	 $("#editEmailTemplateSave").unbind('dblclick');	   
	 $("#editEmailTemplateSave").dblclick(function(e){
		  e.stopPropagation();
		  e.preventDefault();
		  return false;
	 });
	 
	 
	$("#editEmailTemplateSave").click(function(){
		var templateId = $(this).val();
		var emailBody = CKEDITOR.instances['emailBody'].getData();
		if(emailBody.indexOf("Activation Link will be displayed here")>0){
			editEmailTemplateSaveHandler(templateId);
		}else{
			if($('#createEmailActivationForm').valid()) {
				$('#createEmailTemplateContent #messageActivationEmailTemplate').html('<span class="error_message ui-state-error">Activation link is must need to insert, Failed to Email Template.</span>').show();
			}	
		}		
	});
	
	if($("input[name=allStates]").prop("checked")==true){
		$('#statesSelect').prop('disabled', true);
	}
	else{
		$('#statesSelect').prop('disabled', false);
	}
		
	var isDefault = $("#isDefault").val();    
    if(isDefault!=null && isDefault!=undefined && isDefault == "false"){ 
    	
    	var assessmentProgramId = $("#assessmentProgramId").val();
        var templateId = $("#id").val();
        
    	$.ajax({
    		url: 'getOrganizationByAssessmentProgramIdOnly.htm',
    		data: {
    			assessmentProgramId: assessmentProgramId,
    			templateId : templateId
    		},
    		dataType: 'json',
    		type: "POST",
    		async: false,
    		success: function(response){
    			var orgAssessProgs = response.orgAssessProgs;
    			var statesList = response.statesList;
    			var stateSelect = $('select#statesSelect');
    			var newOptions='';
    			if (orgAssessProgs !== undefined && orgAssessProgs !== null && orgAssessProgs.length > 0){
    				$.each(orgAssessProgs, function(i, clrAssessmentProgram) {
    					if(orgAssessProgs[i].organization.id !=''){
    						newOptions += '<option role="option" value="' + orgAssessProgs[i].organization.id + '">' +
    						orgAssessProgs[i].organization.organizationName + '</option>';
    					}
    				});
    				$("#statesSelect").html(newOptions);	
    				if (orgAssessProgs.length == 1) {
    					stateSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
    					stateSelect.trigger('change');
    				}
    				$('#statesSelect').trigger('change.select2');				
    				
    				if(statesList!=null && statesList!=undefined){
    						$("#statesSelect option").each(function(){
    							var val = $(this).val();
    							$.each(statesList, function(i, states) {
    								if(statesList[i].stateId == val){
    									$("#statesSelect option[value="+val+"]").attr("selected","selected");
    								}
    							});			
    						});					
    				}	
    				
    				$('#statesSelect').trigger('change.select2');
    			}
           }
    	});	
    }
    
    $('#createEmailActivationForm').validate({
		ignore: ""
	});
    
    $('#emailTemplateLists').prop('disabled', true);
    
    var bodyOfEmailText = CKEDITOR.instances['emailBody'].getData();
    $('#emailBodyHidden').val(bodyOfEmailText); 
    
 });

$("#EditEmailTemplateReset").click(function(){
	var bodyOfEmailText = $('#emailBodyHidden').val();
	CKEDITOR.instances['emailBody'].setData(bodyOfEmailText);
});

function editEmailTemplateSaveHandler(templateId){
	
	$("#editEmailTemplateSave").addClass('ui-state-disabled');
	if($('#createEmailActivationForm').valid()) {		
				
	  if(CKEDITOR.instances.emailBody.document.getBody().getText().length>1000){
		    $('#createEmailTemplateContent #messageActivationEmailTemplate').html('<span class="error_message ui-state-error">Please enter no more than 1000 characters in Body Of Email Content.</span>').show();
			$(this).unbind('click');
			$("#editEmailTemplateSave").removeClass('ui-state-disabled');
	  }else{
		  $('#createEmailTemplateContent #messageActivationEmailTemplate').html('');
			$('#createEmailTemplateContent #messageActivationEmailTemplate').fadeIn();	
			
			var isDefault = $("#isDefault").val();
			var formData = $('#createEmailActivationForm').serializeArray();		
			
			var templateSucessmsg="Created Custom";
			var templateErrormsg = "Create Custom";
			if(templateId!=null && templateId!=undefined && templateId!==''){
				templateSucessmsg ="Edited Custom";
				templateErrormsg="Edit Custom";
			}		
			if(isDefault=="true") templateSucessmsg="Edited Default";
			
			$.ajax({
					url: 'saveEmailTemplate.htm',				
					type: "POST",
					data:formData,			
					success: function(response) { 
						if(response.result == "success") {
							
							 $("#editActivationEmailDiv").load('createEmailTemplate.htm', {isDefault:isDefault,templateId : templateId}, function(){
								 $('#createEmailTemplateContent #messageActivationEmailTemplate').html('<span class="info_message ui-state-highlight">Successfully '+templateSucessmsg+' Email Template.</span>').show();
							 }).dialog('open');
							 							 
							if(isDefault=="false" && templateId == ''){						
								$('#createEmailActivationForm')[0].reset();
								$("#editEmailTemplateSave").removeClass('ui-state-disabled');
								$('#statesSelect').prop('disabled', true);
								$('#emailTemplateLists').prop('disabled', true);
								loadDefaultTemplate();
						    }
							
							if(templateId!='' && isDefault=="false"){
								var templateName = $("#templateName").val();
							    if(templateName!=null && templateName!=undefined && templateName!=''){
							    	templateName=templateName.trim();
							    	$("#templateName").val(templateName);
							    }	
							}
							
						    var subject = $("#emailSubject").val();
						    if(subject!=null && subject!=undefined && subject!=''){
						    		subject=subject.trim();
						    		$("#emailSubject").val(subject);
						    }						    	
						   
						}else if(response.result == "failed") {
							$('#createEmailTemplateContent #messageActivationEmailTemplate').html('<span class="error_message ui-state-error">Failed to '+templateErrormsg+' Email Template.</span>').show();
							$(this).unbind('click');
						} 
					    else if(response.result == "AlreadyTemplateName") {
							$('#createEmailTemplateContent #messageActivationEmailTemplate').html('<span class="error_message ui-state-error">This Template Name already having, Failed to '+templateErrormsg+' Email Template.</span>').show();
							$(this).unbind('click');			    	
					    }		
					    else if(response.result == "statesAlreadyHaving") {
					    	var emailTemplateStates = response.stateList;
					    	confirmMoveStates(emailTemplateStates,templateId);				    
					    }	
						
						$("#editEmailTemplateSave").removeClass('ui-state-disabled');	   	    			    		
					},
					error: function() {
						$('#createEmailTemplateContent #messageActivationEmailTemplate').html('<span class="error_message ui-state-error">Failed to '+templateErrormsg+' Email Template.</span>').show();
						$(this).unbind('click');
			    		$("#editEmailTemplateSave").removeClass('ui-state-disabled');
			    		
			    	 }  		
				 
					});
	  }
			
		
		
	} else {
		$('#createEmailTemplateContent #messageActivationEmailTemplate').html('<span class="error_message ui-state-error">Correct validation errors.</span>').show();
		$(this).unbind('click');
		$("#editEmailTemplateSave").removeClass('ui-state-disabled');
	}
}

function loadDefaultTemplate(){
	$.ajax({
		url: 'getDefaultTemplate.htm',
		data: {					
		},
		dataType: 'json',
		type: "POST",
		async: false,
		success: function(response){
			var emailActivation = response.emailActivation;
			$("#emailSubject").val(emailActivation.emailSubject);
			$("#emailBody").val(emailActivation.emailBody);
			var includeEpLogo = emailActivation.includeEpLogo;
			if(includeEpLogo){
				$(".checkedEpLogo").prop("checked",true); 
				$(".unCheckedEpLogo").prop("checked",false);
			}
			else{
				$(".unCheckedEpLogo").prop("checked",true); 
				$(".checkedEpLogo").prop("checked",false); 
			}					
       }
	});
}

function confirmMoveStates(emailTemplateStates,templateId){
	var stateNames = "";
	var hiddenparameters = "";
    if(emailTemplateStates!=null && emailTemplateStates!=undefined && emailTemplateStates.length>0){
		for(var i =0;i<emailTemplateStates.length;i++){			    	    
			if(stateNames=="") {
				stateNames=emailTemplateStates[i].activationEmailTemplate.states+" &#8212; "+emailTemplateStates[i].activationEmailTemplate.templateName;  
			}
			else {
				stateNames=stateNames+" </br>"+emailTemplateStates[i].activationEmailTemplate.states+" &#8212; "+emailTemplateStates[i].activationEmailTemplate.templateName;  
			}
			
			hiddenparameters=hiddenparameters+'<input type="hidden" id="statesAlreadyTemplateIds" name="statesAlreadyTemplateIds"  value='+emailTemplateStates[i].templateId+"_"+emailTemplateStates[i].stateIds+' />';
		}		
	}    
   
	$("#confirmDialogStatesMove").dialog({
		width: 520,
		
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		},
		      buttons : {
		      	  
		     "Yes" :  {
		    	 class: 'rightMoveButton',
		    	 text:'Move State(s)',
		    	 click: function(evt){	
		    			$("#statesAlreadyHaving").val("true");
		    			$("#hiddenparameters").html(hiddenparameters);
		    		    editEmailTemplateSaveHandler(templateId);	
		    		    $(this).dialog('close');
		    	 },
		     },
		     "No" :{
		    	 class: 'rightNoButton',
		    	 text:'Cancel',
		    	 click:function(){
		    		 $(this).dialog('close');
		    	 },
		      }
		     }			
		 }).html('<div style="margin-left:27px;line-height: 20px;" id ="statelists" >The following state(s) are in use on another template.<br><br>'+ 
				  '<div style="margin-left: 50px;width: 300px;">' +stateNames+'</div><br>'+
		 		  'Do you wish to move the state(s) to the new template?</div></div>');
		return ;
		
}

