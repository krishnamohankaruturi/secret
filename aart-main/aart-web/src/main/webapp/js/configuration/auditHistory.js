var criteria;
function getAuditTables(){
	var auditHstoryTableArray=[];
	var auditTable = $('#auditHistory');	
	auditTable.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	$('#auditHistory').select2({
		placeholder : 'Select',
		multiple : false
	});
	$.ajax({
			url : 'auditHistoryTableList.htm',
			data : {},
			dataType : 'json',
			type : "GET"
		}).done(function(response) {			
			populateSelectProg(auditTable, response, 'auditName', 'dispalyName');
        	$('#auditHistory').trigger('change');
			$('.calendarAuditHistory').hide();				
			$('.linkAuditHistoryDetails').hide();			
    	$('#auditHistory').trigger('change.select2');
    	filteringOrganization($('#auditHistory')); 
		});
}

function populateSelectStates($select, data, idProp, textProp){
	
	if (data !== undefined && data !== null && data.length > 0){
		for (var x = 0; x < data.length; x++){
			
			if(data[x][idProp] == $('#userDefaultOrganization').val()){
				
				$select.append($('<option selected=\''+'selected'+'\'></option>').val(data[x][idProp]).html(data[x][textProp]));
			} else {
				
				$select.append($('<option></option>').val(data[x][idProp]).text(data[x][textProp]));
			}
			
		}
		if (data.length == 1) {
			$select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
			$select.trigger('change');
			
		}
		$select.trigger('change');
		$select.trigger('change.select2');
		return true;
	}
	return false;
}



function populateSelectProg($select, data, idProp, textProp){
	if (data !== undefined && data !== null && data.length > 0){
		for (var x = 0; x < data.length; x++){			
			$select.append($('<option></option>').val(data[x][idProp]).text(data[x][textProp]));
		}
		if (data.length == 1) {
			$select.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
			$select.trigger('change');
			
		}
		
		$select.trigger('change.select2');
		return true;
	}
	return false;
}

function viewAuditHistoryInit(){
	
	
	$("#auditHistory").find('option:first').attr('selected', 'selected');
	$("#auditHistory").trigger('change');
	$("#auditHistory").trigger('change.select2');
	$('.extraMidFilterSection').hide();
	$('.extraFilterSection').hide();
	
	$('#auditHistory').change(function(){
		var selectedAuditHistory=$("#auditHistory option:selected").val();
		currentDate : new Date();
		$('.linkAuditHistoryDetails').hide();
		if(selectedAuditHistory!=null && selectedAuditHistory!="" && selectedAuditHistory!='0'){
			$('.panel_btn').show();
			$('.calendarAuditHistory').show();
		$('#startDate').datepicker({
			dateFormat: 'mm-dd-yy',
			maxDate : new Date(),
			defaultDate: new Date(),
			onSelect : function(date) {
				var selectedDate = new Date(date);
				var startDate = new Date(selectedDate.getTime());
				/*$("#endDate").datepicker("option", "minDate", startDate);*/	
			$("#endDate").datepicker("option", "minDate", $("#startDate").datepicker("getDate"));		
				$('#startDate').datepicker("option", "maxDate", new Date());
			    return $(this).trigger('change');
			}
		}).datepicker("setDate", new Date());
	
		$('#endDate').datepicker({
			dateFormat: 'mm-dd-yy',
			maxDate : new Date(),
			defaultDate: new Date(),
			onSelect : function(date) {
				var selectedDate = new Date(date);
				var endDate = new Date(selectedDate.getTime());				
				$("#endDate").datepicker("option", "maxDate", new Date());
			    return $(this).trigger('change');
			}
		}).datepicker("setDate", new Date());
		
		$("#endDate").datepicker("option", "minDate", $("#startDate").datepicker("getDate"));
		$("#startDate").trigger('change');
		$("#endDate").trigger('change');
		
		$("#startTime").removeData('timepicker-list');
		$("#endTime").removeData('timepicker-list');
		var displayMessagetime = $("#startTime").timepicker({
			'timeFormat' : 'h:i A',
			'step' : 15
		}).timepicker("setTime",'12:00am');
		var expireMessagetime = $("#endTime").timepicker({
			'timeFormat' : 'h:i A',
			'step' : 15
			 
		}).timepicker("setTime",'11:59pm');
		$('.extraMidFilterSection').hide();
		$('.extraFilterSection').hide();
		$('#organizationId').val('');
		$('#stateStudentId').val('');
		$('.userAccountClass').hide();
		$('#emailAddressId').val('');
		$('#educatorId').val('');
		$('.rosterClass').hide();
		$('#auditRoleDiv').hide();	
		$('#rosterEducatorId').val('');
		if(selectedAuditHistory=='activationtemplateaudittrailhistory'){
			$('.extraFilterSection').show();			
			$('#auditAssessmentProgramDiv').show();
			
			$('#auditAssessmentProgram').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditStateDiv').show();
			
			$('#auditState').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditRoleDiv').hide();	
			$('#auditDistrictDiv').hide();
			$('#auditSchoolDiv').hide();			
			$('#viewAuditHistoryDetails').css({'margin-left':'24.5%','margin-top':'0%'});
			$('#viewAuditHistoryDetailsButtomDiv').show();
			$('#auditStateSpan').css({'margin-left':'15%'});
			$('#auditStateDiv').css({'margin-top':'-1.5%'});
			auditAssessmentPrograms();
			auditStates();
		}else if(selectedAuditHistory=='organizationmanagementaudit'){
			$('.extraFilterSection').show();
			$('#auditAssessmentProgramDiv').hide();
			$('#auditStateDiv').show();			
			$('#auditDistrictDiv').show();			
			$('#auditSchoolDiv').show();
			
			$('#auditState').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditDistrict').select2({
				placeholder : 'Select',
				multiple : false
			});
			$('#auditSchool').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('.extraMidFilterSection').show();
			$('#auditRoleDiv').hide();	
			$('#viewAuditHistoryDetailsButtomDiv').hide();
			$(".stateStudentIdclass").hide();
			$(".organizationIdclass").show();
			$('#auditDistrictSpan').css({'margin-left': '-1.6%'});
			$('#auditSchoolSpan').css({'margin-left': '-1.1%'});
			$('#auditSchoolDiv').css({'margin-top':'-1.5%'});
			$('#auditStateSpan').css({'margin-left':'1%'});
			$('#auditStateDiv').css({'margin-top':'0%'});
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
			auditStates();
			$("#auditState").trigger('change');
		}
		else if(selectedAuditHistory=='organizationaudittrailhistory'){
			$('.extraFilterSection').show();
			$('#auditAssessmentProgramDiv').hide();
			$('#auditStateDiv').show();
			$('#auditDistrictDiv').show();
			$('#auditSchoolDiv').show();
			
			$('#auditState').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditDistrict').select2({
				placeholder : 'Select',
				multiple : false
			});
			$('#auditSchool').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('.extraMidFilterSection').show();
			$('#viewAuditHistoryDetailsButtomDiv').hide();
			$(".stateStudentIdclass").hide();
			$(".organizationIdclass").show();
			$('#auditDistrictSpan').css({'margin-left': '-1.6%'});
			$('#auditSchoolSpan').css({'margin-left': '-1.1%'});
			$('#auditSchoolDiv').css({'margin-top':'-1.5%'});
			$('#auditStateSpan').css({'margin-left':'1%'});
			$('#auditStateDiv').css({'margin-top':'0%'});
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
			auditStates();
			$("#auditState").trigger('change');
		}else if(selectedAuditHistory=='studentaudittrailhistory'){
			$('#auditAssessmentProgramDiv').hide();
			$('.extraMidFilterSection').show();
			$(".organizationIdclass").hide();
			$(".stateStudentIdclass").show();
			$('#viewAuditHistoryDetailsButtomDiv').hide();
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
		}else if(selectedAuditHistory=='useraudittrailhistory'){
			$('#auditAssessmentProgramDiv').hide();
			$('.extraMidFilterSection').show();
			$(".organizationIdclass").hide();
			$(".stateStudentIdclass").hide();
			$('.userAccountClass').show();
			$('#viewAuditHistoryDetailsButtomDiv').hide();
			$("#viewAuditHistoryDetailsTwo").prop("disabled",false);
		    $('#viewAuditHistoryDetailsTwo').removeClass('ui-state-disabled');
		}else if(selectedAuditHistory=='rosteraudittrailhistory'){
			$('.extraFilterSection').show();
			$('#auditAssessmentProgramDiv').hide();
			$('.extraMidFilterSection').show();
			$(".organizationIdclass").hide();
			$(".stateStudentIdclass").hide();
			$('.userAccountClass').hide();
			$('#auditRoleDiv').hide();	
			 $('.rosterClass').show();
			$('#viewAuditHistoryDetailsButtomDiv').hide();
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
		    $('#auditStateDiv').show();
			$('#auditDistrictDiv').show();
			$('#auditSchoolDiv').show();
			
			$('#auditState').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditDistrict').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditSchool').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#contentArea').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#contentAreaBottom').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			
			$('#auditStateSpan').css({'margin-left': '6.8%'});
			$('#auditDistrictSpan').css({'margin-left': '4%'});
			$('#auditSchoolSpan').css({'margin-left': '4.5%'});
			$('#auditSchoolDiv').css({'margin-top':'-0.8%'});
			$('#auditStateDiv').css({'margin-top':'0%'});
			contentArea();
			auditStates();
		    $("#auditState").trigger('change.select2');
		    
		    
		}else if(selectedAuditHistory=='firstcontactsurveyaudithistory'){
			$('#auditAssessmentProgramDiv').hide();
			$('.extraMidFilterSection').show();
			$(".organizationIdclass").hide();
			$(".stateStudentIdclass").show();
			$('#viewAuditHistoryDetailsButtomDiv').hide();
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
		}
		else if(selectedAuditHistory=='roleaudittrailhistory'){
			$('.extraFilterSection').show();			
			$('#auditAssessmentProgramDiv').show();
			$('#auditStateDiv').show();
			$('#auditRoleDiv').show();
			
			$('#auditState').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditAssessmentProgram').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditRole').select2({
				placeholder : 'Select',
				multiple : false
			});
			
			$('#auditDistrictDiv').hide();
			$('#auditSchoolDiv').hide();			
			$('#viewAuditHistoryDetails').css({'margin-left':'22.5%','margin-top':'0%'});
			$('#viewAuditHistoryDetailsButtomDiv').show();
			$('#auditStateSpan').css({'margin-left':'14.5%'});
			/*$('#auditRoleSpan').css({'margin-left':'14.5%'});*/
			$('#auditStateDiv').css({'margin-top':'-1.5%'});
			/*$('#auditRoleDiv').css({'margin-top':'-1.5%'});*/
			auditAssessmentPrograms();
			auditStates();
			auditRoles();
		}else if(selectedAuditHistory=='studentpnpsaudithistory'){
			$('#auditAssessmentProgramDiv').hide();
			$('.extraMidFilterSection').show();
			$(".organizationIdclass").hide();
			$(".stateStudentIdclass").show();
			$('#viewAuditHistoryDetailsButtomDiv').hide();
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
		}
		else {		
		
			$('#auditAssessmentProgramDiv').hide();
			$('#viewAuditHistoryDetails').css({'margin-left':'13.5%','margin-top':'7%'});
			$('#viewAuditHistoryDetailsButtomDiv').show();
			$('#auditStateDiv').hide();
			$('#auditSchoolDiv').hide();
			$('#auditDistrictDiv').hide();
			$('#auditRoleDiv').hide();
			
		}
	}else{
		$('.extraFilterSection').hide();
		$('.extraMidFilterSection').hide();
		$('.calendarAuditHistory').hide();
		$('#tabs_org_audithistory .panel_btn').hide();
	}
			
	});	
	function checkcriteria() {
		var startDate = $("#startDate").val();
		var startTime = $("#startTime").val();
		var endDate = $("#endDate").val();
		var endTime = $("#endTime").val();
				
		if (startDate == "" || startTime == ""
			|| endDate == "" || endTime == "") {
			criteria = false;
			
			$('.linkAuditHistoryDetails').hide();

		} 
		else {
			criteria = true;
			
			$('.linkAuditHistoryDetails').hide();

		}
	}
	
	$("#startDate").on("change",function (){		
		checkcriteria();
		
		});
	$("#endDate").on("change",function (){		
		checkcriteria();
		});
	$('#startTime').on('change', function() { 
		 var validTime = $(this).val().match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
			if(!validTime){
				$(this).timepicker({
					'timeFormat' : 'h:i A',
					'step' : 15
				}).timepicker("setTime",'12:00am');
			}
	 		
		});
	$('#endTime').on('change', function() {
		 var validTime = $(this).val().match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
			if(!validTime){
				$(this).timepicker({
					'timeFormat' : 'h:i A',
					'step' : 15
					 
				}).timepicker("setTime",'11:59pm');
			}
	 		
		});
	$('.viewAuditHistoryDetailsButton').click(function(event) {
		var idValue=this.id;
		
		viewAuditHistoryLink(idValue);
	});
	
	$("#organizationId").on("change paste keyup mouseleave",function(event){
		var organizationId=$("#organizationId").val();
		if(event.type=='change'){
			$('.linkAuditHistoryDetails').hide();
			}
		if(organizationId!=null && organizationId !=undefined && organizationId !=''){
			$("#viewAuditHistoryDetailsTwo").prop("disabled",false);
		    $('#viewAuditHistoryDetailsTwo').removeClass('ui-state-disabled');
		}else{
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
		    $("#linkAuditHistoryDetailsTwo").hide();
		}
	    if(event.keyCode == 13){
	    	if(organizationId!=null && organizationId !=undefined && organizationId !=''){
	        $("#viewAuditHistoryDetailsTwo").on();
	    	}
	    }
	});
	$("#stateStudentId").on("change paste keyup mouseleave",function(event){
		var stateStudentId=$("#stateStudentId").val();
		
		if(event.type=='change'){
			$('.linkAuditHistoryDetails').hide();
			}
		if(stateStudentId!=null && stateStudentId !=undefined && stateStudentId !=''){
			$("#viewAuditHistoryDetailsTwo").prop("disabled",false);
		    $('#viewAuditHistoryDetailsTwo').removeClass('ui-state-disabled');
		}else{
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
		    $("#linkAuditHistoryDetailsTwo").hide();
		}
	    if(event.keyCode == 13){
	    	if(organizationId!=null && organizationId !=undefined && organizationId !=''){
	        $("#viewAuditHistoryDetailsTwo").on();
	    	}
	    }
	});
	$("#emailAddressId,#educatorId").on("change keyup",function(event){
			if(event.type=='change'){
		$('.linkAuditHistoryDetails').hide();
			}
			 if(event.keyCode == 13){
			    	if(organizationId!=null && organizationId !=undefined && organizationId !=''){
			        $("#viewAuditHistoryDetailsTwo").click();
			    	}
			    }
	});
	$("#viewAuditHistoryDetailsTwo").on("mouseover",function(event){
		var organizationId=$("#organizationId").val();
		var stateStudentId=$("#stateStudentId").val();
		var rosterEducatorId=$("#rosterEducatorId").val();
		var auditHistory=$("#auditHistory option:selected").val();
		if((organizationId!=null && organizationId !=undefined && organizationId !='')||(stateStudentId!=null && stateStudentId !=undefined && stateStudentId !='')
				||(auditHistory=='useraudittrailhistory')||(rosterEducatorId!=null && rosterEducatorId !=undefined && rosterEducatorId !='')){
			$("#viewAuditHistoryDetailsTwo").prop("disabled",false);
		    $('#viewAuditHistoryDetailsTwo').removeClass('ui-state-disabled');
		}else{
			$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
		    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
		    $("#linkAuditHistoryDetailsTwo").hide();
		}
	   	});
	function viewAuditHistoryLink(idValue){
		var auditHistory=$("#auditHistory option:selected").val();
		var stateId='';
		var districtId='';
		var schoolId='';
		var contentArea='';
		var educatorId='';
		var groupId='';
		var  rosterEducatorId=$("#rosterEducatorId").val();
		if($("#auditState option:selected").val()!=undefined && $("#auditState option:selected").val()!=null ){
			stateId=$("#auditState option:selected").val();
		}
		
		if($("#auditDistrict option:selected").val()!=undefined && $("#auditDistrict option:selected").val()!=null ){
			districtId=$("#auditDistrict option:selected").val();
		}
		
		if($("#auditSchool option:selected").val()!=undefined && $("#auditSchool option:selected").val()!=null ){
			schoolId=$("#auditSchool option:selected").val();
		}
		
		if($("#contentAreaBottom option:selected").val()!=undefined && $("#contentAreaBottom option:selected").val()!=null ){
			contentArea=$("#contentAreaBottom option:selected").val();
		}
		if($("#auditRole option:selected").val()!=undefined && $("#auditRole option:selected").val()!=null ){
			groupId=$("#auditRole option:selected").val();
		}
	
		if( auditHistory=='rosteraudittrailhistory' && rosterEducatorId !=undefined 
				&& rosterEducatorId !=null && rosterEducatorId !=''){
			educatorId=$("#rosterEducatorId").val();
		}
		
		var startTime = $("#startTime").val();
		var hours = Number(startTime.match(/^(\d+)/)[1]);
		var minutes = Number(startTime.match(/:(\d+)/)[1]);
		var AMPM = startTime.match(/\s?([AaPp][Mm]?)$/)[1];
		var pm = ['PM', 'pM', 'pm', 'Pm'];
	    var am = ['AM', 'aM', 'am', 'Am'];
	    if (pm.indexOf(AMPM) >= 0 && hours < 12) hours = hours + 12;
	    if (am.indexOf(AMPM) >= 0 && hours == 12) hours = hours - 12;
		var sHours = hours.toString();
		var sMinutes = minutes.toString();
		if(hours<10) sHours = "0" + sHours;
		if(minutes<10) sMinutes = "0" + sMinutes;
		var startTime24=sHours + ":" + sMinutes;
		
		var endTime = $("#endTime").val();
		var endHours = Number(endTime.match(/^(\d+)/)[1]);
		var endMinutes = Number(endTime.match(/:(\d+)/)[1]);
		var endAMPM = endTime.match(/\s?([AaPp][Mm]?)$/)[1];
		var endPm = ['PM', 'pM', 'pm', 'Pm'];
	    var endAm = ['AM', 'aM', 'am', 'Am'];
	    if (endPm.indexOf(endAMPM) >= 0 && endHours < 12) endHours = endHours + 12;
	    if (endAm.indexOf(endAMPM) >= 0 && endHours == 12) endHours = endHours - 12;
		var endHour = endHours.toString();
		var endMinute = endMinutes.toString();
		if(endHours<10) endHour = "0" + endHour;
		if(endMinutes<10) endMinute = "0" + endMinute;
		var endTime24=endHour + ":" + endMinute;
		
            if($('#startDate').val() == $('#endDate').val() && 
            		$('#startDate').val() + ' ' + startTime24 >=$('#endDate').val()+ ' ' + endTime24){
                alert("End time should be greater than start time . Please select proper end time.");    
        }
        else{        	
        	$('.linkAuditHistoryDetails').hide();
        	if(idValue=='viewAuditHistoryDetails'){
             $('#linkAuditHistoryDetails').show();
        	}else if(idValue=='viewAuditHistoryDetailsOne'){
        		
        		if((auditHistory=='organizationaudittrailhistory' || (auditHistory=='rosteraudittrailhistory' && contentArea=='')) && stateId =='' && districtId =='' && schoolId=='' && groupId==''){
        			
        			showWarning();
        		}else{
        		$('#linkAuditHistoryDetailsOne').show();}
        	}else if(idValue=='viewAuditHistoryDetailsTwo'){
        		
        	if(($('#organizationId').val()!=undefined && $('#organizationId').val()!=null && $('#organizationId').val()!='' )||($('#stateStudentId').val()!=undefined && $('#stateStudentId').val()!=null && $('#stateStudentId').val()!='' )
        			||(educatorId !=undefined && educatorId !=null && educatorId !='' )||(auditHistory=='useraudittrailhistory')){
        		$('#linkAuditHistoryDetailsTwo').show();
        	}
        	}
        }
	}
		$('.linkAuditHistoryDetails').on("click",function(event) {
		var auditName='';
		var auditHistory=$("#auditHistory option:selected").val();
		var startDate=$("#startDate").val(); 
		var startTime=$("#startTime").val(); 
		var endDate=$("#endDate").val(); 
		var endTime=$("#endTime").val();		
		var stateId="";
		var linkName=this.id;
		if($("#auditState option:selected").val()!=undefined && $("#auditState option:selected").val()!=null ){
			stateId=$("#auditState option:selected").val();
		}
		var districtId="";
		if($("#auditDistrict option:selected").val()!=undefined && $("#auditDistrict option:selected").val()!=null ){
			districtId=$("#auditDistrict option:selected").val();
		}
		var schoolId="";
		if($("#auditSchool option:selected").val()!=undefined && $("#auditSchool option:selected").val()!=null ){
			schoolId=$("#auditSchool option:selected").val();
		}
		var assessmentProgrmId="";
			if($("#auditAssessmentProgram option:selected").val()!=undefined && $("#auditAssessmentProgram option:selected").val()!=null ){
				assessmentProgrmId=$("#auditAssessmentProgram option:selected").val();
			}
		var organizationId="";
																																			
			if($("#organizationId").val()!=undefined && $("#organizationId").val()!=null && $("#organizationId").val()!='' && linkName == 'linkAuditHistoryDetailsTwo'){
				organizationId=$("#organizationId").val();
			}
			
		var	stateStudentId	="";
			if($("#stateStudentId").val()!=undefined && $("#stateStudentId").val()!=null && $("#stateStudentId").val()!='' && linkName == 'linkAuditHistoryDetailsTwo'){
				stateStudentId=$("#stateStudentId").val();
			}
		var	educatorId	="";
		if($("#educatorId").val()!=undefined && $("#educatorId").val()!=null && $("#educatorId").val()!='' && linkName == 'linkAuditHistoryDetailsTwo'){
			educatorId=$("#educatorId").val();
		}
		var	emailAddress	="";
		if($("#emailAddressId").val()!=undefined && $("#emailAddressId").val()!=null && $("#emailAddressId").val()!='' && linkName == 'linkAuditHistoryDetailsTwo'){
			emailAddress=$("#emailAddressId").val();
		}
		
		var contentArea='';
		if($("#contentAreaBottom option:selected").val()!=undefined && $("#contentAreaBottom option:selected").val()!=null && linkName == 'linkAuditHistoryDetailsOne'){
			contentArea=$("#contentAreaBottom option:selected").val();
		}
		
		if($("#contentArea option:selected").val()!=undefined && $("#contentArea option:selected").val()!=null && linkName == 'linkAuditHistoryDetailsTwo'){
			contentArea=$("#contentArea option:selected").val();
		}
		var groupId="";		
		if($("#auditRole option:selected").val()!=undefined && $("#auditRole option:selected").val()!=null ){
			groupId=$("#auditRole option:selected").val();
			/*alert(groupId);*/
		}
		
		if(auditHistory=='rosteraudittrailhistory' && $("#rosterEducatorId").val()!=undefined && $("#rosterEducatorId").val()!=null && $("#rosterEducatorId").val()!='' && linkName == 'linkAuditHistoryDetailsTwo'){
			educatorId=$("#rosterEducatorId").val();
		}
		
		var downloadUrl="downloadAuditHistory.htm?auditName="
			+auditHistory+"&startDate="+encodeURIComponent(startDate)+"&endDate="+encodeURIComponent(endDate)+"&startTime="+encodeURIComponent(startTime)+"&endTime="+ encodeURIComponent(endTime)
			+"&assessmentProgramId="+encodeURIComponent(assessmentProgrmId)+"&stateId="+encodeURIComponent(stateId)+"&districtId="+encodeURIComponent(districtId)+"&schoolId="+encodeURIComponent(schoolId)
			+"&organizationId="+encodeURIComponent(organizationId)+"&stateStudentId="+encodeURIComponent(stateStudentId)
			+"&educatorId="+encodeURIComponent(educatorId)+"&emailAddress="+encodeURIComponent(emailAddress)+"&contentArea="+encodeURIComponent(contentArea)			
			+"&_=1479127163288"+"&groupId="+groupId;
		
		$(".linkAuditHistoryDetails").attr("href", downloadUrl);
	});	

function showWarning(){
	$('#overSizeWarningMsg').dialog({
		resizable: false,
		height: 150,
		width: 400,
		modal: true,
		autoOpen:false,
		title:'&nbsp;',
		buttons: {
			Ok: function() {
				$('#linkAuditHistoryDetailsOne').show();
				$(this).dialog('close');
		    },
		    Cancel:function() {
				$(this).dialog('close');
				
		    },
		}
	});
	
	$('#overSizeWarningMsg').html('No filter is selected, Report may be large.').dialog('open');
}		
function contentArea(){
	$.ajax({
		url: 'getRosterDropdownData.htm',
		data: { },
		dataType: 'json',
		type: "GET"
	}).done(function(data) {
		$('#contentAreaBottom').html("");
		$('#contentAreaBottom').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		$('#contentArea').html("");
		$('#contentArea').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		for(var i=0;i<data.contentAreas.length;i++) {
	
			$('#contentAreaBottom').append($('<option value=\"'+data.contentAreas[i].id+'\"></option>').html(data.contentAreas[i].name));
			
		}
		
		for(var i=0;i<data.contentAreas.length;i++) {
			$('#contentArea').append($('<option value=\"'+data.contentAreas[i].id+'\"></option>').html(data.contentAreas[i].name));
			
		}
		
	});
	$('#contentAreaBottom').trigger('change.select2');
	$('#contentArea').trigger('change.select2');
	}


function auditRoles(){
	var select = $('#auditRole');	
	$('#auditRole').find('option').filter(function(){return $(this).val() > 0;}).remove().end();	    
		    $.ajax({
	        url: 'getRolesOrgsForaudithistoryByUser.htm',
	        dataType: 'json',
	        type: "GET"
		}).done(function(groups) {        	
        	populateSelectStates(select, groups, 'groupId', 'groupName');	        	
        	$('#auditRole').trigger('change.select2'); 	    	
        	
        });
      }



function auditStates(){
	
				$('#auditState').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				  var select = $('#auditState');				
				  $.ajax({
				        url: 'getStatesOrgsForaudithistoryByUser.htm',
				        dataType: 'json',
				        type: "GET"
					}).done(function(states) {
			        	populateSelectStates(select, states, 'id', 'organizationName');
			        	
			        	
			        	populateSelectStates(select, states, 'id', 'organizationName');
			        	$('#auditState').trigger('change.select2');	
			        });
			      }


$('#auditState').change(function(){
	var selectedAuditHistory=$("#auditHistory option:selected").val();
	var stateId=$("#auditState option:selected").val();
	$('.linkAuditHistoryDetails').hide();
	if(selectedAuditHistory=='activationtemplateaudittrailhistory'){
		return;
	}else{
		auditDistrictsBasedOnState(stateId);
		$("#auditDistrict").trigger('change');
		$('#auditDistrict').trigger('change.select2');
	}
	
});

$('#auditDistrict').change(function(){
	var selectedAuditHistory=$("#auditDistrict option:selected").val();
	var districtId=$("#auditDistrict option:selected").val();
	var stateId=$("#auditState option:selected").val();
	$('.linkAuditHistoryDetails').hide();
	if(selectedAuditHistory=='activationtemplateaudittrailhistory'){
		return;
	}else{
		auditSchoolssBasedOnDistrict(districtId,stateId);
		$("#auditSchool").trigger('change');
		$('#auditSchool').trigger('change.select2');
	}

});
$('#auditSchool').on("change",function(){
	$('.linkAuditHistoryDetails').hide();
});
$('#auditAssessmentProgram').on("change",function(){
	$('.linkAuditHistoryDetails').hide();
});
$('#auditRole').on("change",function(){	
	$('.linkAuditHistoryDetails').hide();
});
function auditAssessmentPrograms(){
				var select = $('#auditAssessmentProgram');
				select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
				
				// goes to ManageTestSessionController
				$.ajax({
					url: 'getAssessmentProgramsByUser.htm',
					dataType: 'json',
					type: "POST"
				}).done(function(assessmentPrograms){
					 populateSelectProg(select, assessmentPrograms, 'id', 'programName');
				});
			}


function auditDistrictsBasedOnState(stateId) {
	$('#auditDistrict').val('').find('option:not(:first)').remove();
		if(stateId !=null && stateId !=undefined && stateId !=''){
		$.ajax({
			url : 'getOrgsBasedOnUserContext.htm',
			data : {
				orgId : stateId,
				orgType:'DT',
	        	orgLevel:50
			},
			dataType : 'json',
			type : "GET"
		}).done(function(districtOrgs) {
			$('#auditDistrict').html("");
			$('#auditDistrict').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$.each(districtOrgs, function(i, districtOrg) {
				$('#auditDistrict').append($('<option></option>').attr("value", districtOrg.id).text(districtOrg.organizationName));
					});	
			if (districtOrgs.length == 1) {
				$("#auditDistrict option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#auditDistrict").trigger('change');
								}
			$('#auditDistrict').trigger('change.select2');
        });
  }
}

function auditSchoolssBasedOnDistrict(districtId) {
	$('#auditSchool').val('').find('option:not(:first)').remove();
		if(districtId !=null && districtId !=undefined && districtId !=''){
		$.ajax({
			url : 'getOrgsBasedOnUserContext.htm',
			data : {
				orgId : districtId,
				orgType:'SCH',
	        	orgLevel:70
			},
			dataType : 'json',
			type : "GET"
		}).done(function(districtOrgs) {
			$('#auditSchool').html("");
			$('#auditSchool').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$.each(districtOrgs, function(i, districtOrg) {
				$('#auditSchool').append($('<option></option>').attr("value", districtOrg.id).text(districtOrg.organizationName));
					});	
			if (districtOrgs.length == 1) {
				$("#auditSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#auditSchool").trigger('change');
								}
			$('#auditSchool').trigger('change.select2');
        });
  }
}

$("#organizationId").on("paste", function(e){
	var pastedData = e.originalEvent.clipboardData.getData('text');
	 // access the clipboard using the api
	var numericReg = /^\d*[0-9](|.\d*[0-9]|,\d*[0-9])?$/;
    
   if( !numericReg.test(pastedData)){
	   $("#viewAuditHistoryDetailsTwo").prop("disabled",true);
	    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
	    $("#linkAuditHistoryDetailsTwo").hide();
	   e.preventDefault();}
   else {
	   $("#viewAuditHistoryDetailsTwo").prop("disabled",false);
	    $('#viewAuditHistoryDetailsTwo').removeClass('ui-state-disabled');
	   
		   }
} );

$("#rosterEducatorId").on("change paste keyup mouseleave",function(event){
	var rosterEducatorId=$("#rosterEducatorId").val();
	if(event.type=='change'){
		$('.linkAuditHistoryDetails').hide();
		}
	if(rosterEducatorId!=null && rosterEducatorId !=undefined && rosterEducatorId !=''){
		$("#viewAuditHistoryDetailsTwo").prop("disabled",false);
	    $('#viewAuditHistoryDetailsTwo').removeClass('ui-state-disabled');
	}else{
		$("#viewAuditHistoryDetailsTwo").prop("disabled",true);
	    $('#viewAuditHistoryDetailsTwo').addClass('ui-state-disabled');
	    $("#linkAuditHistoryDetailsTwo").hide();
	}
    if(event.keyCode == 13){
    	if(organizationId!=null && organizationId !=undefined && organizationId !=''){
        $("#viewAuditHistoryDetailsTwo").click();
    	}
    }
});



$("#organizationId").on('keypress',function (e) {
	
	if(e.keyCode==190 || e.keyCode==110){
    	e.preventDefault();
    	return;
    }
    // Allow: backspace, delete, tab, escape, enter and .
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
         // Allow: Ctrl+A, Command+A
        ((e.keyCode === 65||e.keyCode===67 ||e.keyCode===86) && (e.ctrlKey === true || e.metaKey === true)) || 
         // Allow: home, end, left, right, down, up
        (e.keyCode >= 35 && e.keyCode <= 40)) {
             // let it happen, don't do anything
             return;
    }
    // Ensure that it is a number and stop the keypress
    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
        e.preventDefault();
    }
});		
			
}

