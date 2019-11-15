$(function () {
        //$("#dialog_view_pass").dialog({ autoOpen: false });

		$(".restricted").on("click",function () {
            	$("#dialog_view_pass").dialog({ minHeight: 220, minWidth: 500 });
                $("#dialog_view_pass").dialog('open');
                return false;
            }
        ); 
        	var ctrr=0;
        	$(".collapser").each(function( index ) {
        		if(ctrr != 0){
        			var element = $(this).closest('div').find(".collapsiblerow");
        			$(this).closest('div').find(".collapsiblerow").hide();	
        			$(this).closest('div').find("span").text('+');
        		}
        		ctrr++;		
        	});
        	$(".collapser").on("click",function(){
        		var element = $(this).closest('div').find(".collapsiblerow");
        		if($(element).is(":visible")){
        			$(this).closest('div').find(".collapsiblerow").hide();			
        			$(this).closest('div').find("span").text('+');	
        		} else {
        			$(this).closest('div').find(".collapsiblerow").show();			
        			$(this).closest('div').find("span").text('-');
        		}		
        	  });
        	
        	$('#reason_exit').select2({
        		placeholder:'Select',
        		multiple: false
        	});
          populateExitReason($('#reason_exit'), exitCodes, 'code', 'description');
        	$('#unRollStudentNextConfirm').attr('disabled','disabled').addClass('ui-state-disabled');
        	
        	$('#reason_exit').on("change",function (event){
        		if($('#reason_exit').val() != 0){
        			if($('#unRollDate').val() !=='null' && $('#unRollDate').val() !==''){
        				$('#unRollStudentNextConfirm').removeAttr('disabled').removeClass('ui-state-disabled');
        			}
        		}else{
    				$('#unRollStudentNextConfirm').attr('disabled','disabled').addClass('ui-state-disabled');
    			}
        		
        	});
        	$('#unRollDate').on("change",function (event){       		
        		if($('#unRollDate').val() !=='null' && $('#unRollDate').val() !==''){ 
        			if(new Date($('#unRollDate').val()) < new Date()){
        				$('.futureUnrollDate').hide();
	        			if(new Date($('#unRollDate').val()).getTime() < new Date($('#schoolEntryDate_exit').val()).getTime()){
	        				$('#unRollDate').val('');
	        				$('#unRollStudentNextConfirm').attr('disabled','disabled').addClass('ui-state-disabled');
	        				$('#invalidDateMessage_exit').show();
	        			}else{
	        				 $('#invalidDateMessage_exit').hide();
	        			}
        			}else{
        				$('#unRollDate').val('');
        				$('#unRollStudentNextConfirm').attr('disabled','disabled').addClass('ui-state-disabled');
       				 	$('#invalidDateMessage_exit').hide();
        				$('.futureUnrollDate').show();
        			}
        			if($('#reason_exit').val() != 0 && $('#unRollDate').val() !==''){
        		            $('#unRollStudentNextConfirm').removeAttr('disabled').removeClass('ui-state-disabled');
        			}
        		}else{
					$('#unRollStudentNextConfirm').attr('disabled','disabled').addClass('ui-state-disabled');
				}
        		
        	});
        	
        	$("#unRollDate").datepicker({
    		    dateFormat: "mm/dd/yy"
    		}).datepicker("setDate", new Date());        	
        	
        
        	
         	var width = $("#unRollStudentInput :nth-child(2) div.bcg_select span").width();
        	var height = $("#unRollStudentInput :nth-child(2) div.bcg_select span").height();
        	$("#unRollStudentInput").css({'float':'left','width':'45%','padding-bottom':'10px'});
        	$('#unRollStudentInput p').css({'color':'#a7a9ac','padding-bottom':'0px'});
        	$("#unRollStudentInput input").css({'margin':'-10px','margin-left':'0px','width':width,'height':height});
        	$("#unRollStudentInput :nth-child(2) div.bcg_select").css({'margin':'-10px','margin-left':'0px'});
        		
        	$("#unRollStudentNextConfirm").on('click',function(e) {
        		var ExitDate = $('#unRollDate').val();
        		var ExitReasonvalue = $('#reason_exit').val();
        		
        		var selectedOrgsForExit = $('#selectedOrgs_exit').val();        		
        		var currentSchoolYears = $('#currentSchoolYears_exit').val();
        		var attendanceSchoolDisplayIdentifiers = $('#attendanceSchoolDisplayIdentifiers_exit').val();
        		var stateStudentIdentifier = $('#stateStudentIdentifier_exit').val();
        		e.preventDefault();
        		$("#Confirmdialog").dialog({
        		  buttons : {
        			"Yes" : function() {
                         $.ajax({
                             url:"UnEnrollSelectedStudent.htm",
                             data: {
                            	 'stateStudentIdentifier':stateStudentIdentifier,
                            	 'exitReason':ExitReasonvalue,
                            	 'exitDate':ExitDate,
                            	 'currentSchoolYear':currentSchoolYears,
								 'selectedOrg':selectedOrgsForExit,                            	 
                            	 'attendanceSchoolDisplayIdentifiers':attendanceSchoolDisplayIdentifiers},
                             datatype:"json",
                             type: "POST"
                         }).done(function(){
                        	 $('#UnRollMessage').html("Successfully Removed!");
                        	 $('#unRollviewStudentDetailsDiv').dialog('close');
                        	 $('#unRollDate').datepicker("disable");
                         }).fail(function() {
                        	 $('#UnRollMessage').html("Error Occurred in Removing Student!");
                        	 $('#unRollviewStudentDetailsDiv').dialog('close');
                        	 $('#unRollDate').datepicker("disable");
                         });
        			  $(this).dialog("close");
        			  $('#UnrollviewStudentsContinue').attr('disabled','disabled').addClass('ui-state-disabled');
        			  $('#UnrollviewStudentsContinues').attr('disabled','disabled').addClass('ui-state-disabled');
        			},
        			"No" : function(){
        			  $(this).dialog("close");
        			  $('#UnrollviewStudentsContinue').attr('disabled','disabled').addClass('ui-state-disabled');
        			  $('#UnrollviewStudentsContinues').attr('disabled','disabled').addClass('ui-state-disabled');
        			  $('#unRollviewStudentDetailsDiv').dialog('close');
        			}
        		  }
        		});
        		$("#Confirmdialog").dialog("open");
        	}); 
        	$(".btn_close").on('click',function(e) {
        		//$('#unRollDate').datepicker("disable");
        	});	
        	
            $('#dialog_view_pass').hide();
    });

function populateExitReason($select, data, idProp, textProp){
	 $('#reason_exit').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	if (data !== undefined && data !== null && data.length > 0){
		for (var x = 0; x < data.length; x++){
		 		$select.append($('<option></option>').val(data[x][idProp]).text(data[x][idProp] +' - ' + data[x][textProp]));
		}
		
		if (data.length == 1) {
		 $select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
		 $select.trigger('change');
		}
		  
		$select.val('').trigger('change.select2');
		return true;
	}
	return false;
}
