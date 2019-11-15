var transferSelectedStudentArr = [];
var confirmSelectedStudentArr =[];
var existingSchoolIds = [];
var existingStudentFlaglength=0;
var checkingExistingStudentsAllFlagged = false;
$(document).ready(function(e){
	$("#selectQuickHelpHintForDistrictPopupClose").on('click',function(){
		$("#selectQuickHelpHintForDistrictPopup").hide();
	});
	$("#selectQuickHelpHintForAccountabilityDistrictPopupClose").on('click',function(){
		$("#selectQuickHelpHintForAccountabilityDistrictPopup").hide();
	});
	$("#selectDistrictStudent_TemplatedownloadquickHelp").on('click',function(){
		$("#selectQuickHelpHintForDistrictPopup").show();
	});
	$("#selectAccountabilityDistrictStudent_TemplatedownloadquickHelp").on('click',function(){
		$("#selectQuickHelpHintForAccountabilityDistrictPopup").show();
	});
	$('#selectStudent_TemplatedownloadquickHelp').on('mouseover',function(){
		$("#selectQuickHelpHint").show();
	});
	$('#selectStudent_TemplatedownloadquickHelp').on('mouseout',function(){
		$("#selectQuickHelpHint").hide();
	});
	
	$('#selectDistrictStudent_TemplatedownloadquickHelp').on('mouseover',function(){
		$("#selectQuickHelpHintForDistrict").show();
	});
	$('#selectDistrictStudent_TemplatedownloadquickHelp').on('mouseout',function(){
		$("#selectQuickHelpHintForDistrict").hide();
	});
	$('#selectAccountabilityDistrictStudent_TemplatedownloadquickHelp').on('mouseover',function(){
		$("#selectQuickHelpHintForAccountabilityDistrict").show();
	});
	$('#selectAccountabilityDistrictStudent_TemplatedownloadquickHelp').on('mouseout',function(){
		$("#selectQuickHelpHintForAccountabilityDistrict").hide();
	});
	
	$("#confirmDialogDeleteTransferStudent").dialog({
	    autoOpen: false,
	    modal: true
	});
	
	$("#confirmDialogCancelTransferStudent").dialog({
	    autoOpen: false,
	    modal: true
	});
	
});
	


function transferStudentInitmethod(){
	
	transferStudentLoadOnce = true;
	$('#transferStudentsOrgFilter').orgFilter({
		containerClass: '',
		requiredLevels: [20,30,40,50]
	});
	if(globalUserLevel <= 70){
		$('#transferStudentsOrgFilter').orgFilter('option','requiredLevels',[70]);
	}
	$('#transferStudentsOrgFilterForm').validate({ignore: ""});
	
	
	buildTransferStudentsByOrgGrid();
//	buildDestinationDistrictGrid();
	buildRemoveDestinationStudentGrid();
	
	
	
	$('#transferStudentsSearchButton').on("click",function(event) {
		
		if($('#transferStudentsOrgFilterForm').valid()) {
			
			buildTransferStudentsByOrgGrid();
			$("#viewConfirmationGridTable").jqGrid('clearGridData');
			
			var assessmentProgramId = 0;
			transferSelectedStudentArr = [];
			var $gridAuto = $("#transferStudentsByOrgTable");
			$gridAuto[0].clearToolbar();
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getCreateTestRecordStudentsGridData.htm?q=1',
				search: false, 
				postData: { "filters": ""
							,"assessmentProgramId": assessmentProgramId
					}
				}).trigger("reloadGrid",[{page:1}]);
			
		}
		var opts = $('#transferStudentsOrgFilter_school')[0].options;
		existingSchoolIds = $.map(opts, function(elem) {
		    return (elem.value);
		});
		existingSchoolIds = existingSchoolIds.slice(1);
		
	});

	$('#districtOrgsSelectDestination, #accountabilityDistrictOrgsSelectDestination,#tansStudDestGridHeaderDestinationLocalId').select2({
		  placeholder:'Select',
		  multiple: false,
   		  allowClear : true
		 });
	
	$('#districtOrgsSelectDestination').on("change",function(e){
		var destDistrictId = $(this).val();
			populateGridHeaderDestinationSchool("tansStudDestGridHeaderAttntSchool", destDistrictId,true);
	});
	
	$('#accountabilityDistrictOrgsSelectDestination').on("change",function(e){
		var accountabilityDistrict = $(this).val();
			var existingDistId = $('#transferStudentsOrgFilter_district').val();
			populateGridHeaderDestinationSchool("tansStudDestGridHeaderAYPSchool", accountabilityDistrict, false,existingDistId);
		
	});
	
	$("#transferStudentsGridNext").off("click").on("click",function(){
		if($('#transferStudentsOrgFilterForm').valid()) {
			$("#transferStudentSelectViewGrid").hide();
			$("#transferDestinationConfirmationDiv").hide();
			$("#selectDestinationDistrictViewDiv").show();
			populateDestinationDistrict();
			populateDestinationAccountabilityDistrict();
			buildDestinationDistrictGrid();
			$("#selectQuickHelpHintForDistrictPopup").hide();
			$("#selectQuickHelpHintForAccountabilityDistrictPopup").hide();
			populateSelectedStudentsForTransfer();
		}
	});
	
	filteringOrganizationSet($('#transferStudentsOrgFilterForm'));
	
}


$("#transferStudentsDestinationGridNext").off("click").on("click",function(){
	$("#transferStudentsConfirmGridNext").prop("disabled",false);
	$('#transferStudentsConfirmGridNext').removeClass('ui-state-disabled');
	checkingExistingStudentsAllFlagged = true;
	populateDestinationSelectedStudentForTransfer();
});


$("#transferStudentsConfirmGridNext").off("click").on("click",function(){
	if(checkingExistingStudentsAllFlagged == true){
		//resetStudents();
		studentsInitNew('transferStudents');
	}else{
		buildConfirmationViewGrid();
		populateConfirmationViewGridData();
		$("removeNotTransferredStudentFromDestinationTable").jqGrid('clearGridData');
	}
});


$("#transferStudentsNo").off('click').on('click',function(e){
	
	$("#confirmDialogCancelTransferStudent").dialog({
	      buttons : {
	     "Yes" : function() {
	    	 //resetStudents();
	    	 studentsInitNew('transferStudents');
	    	 $(this).dialog("close");
	     },
	     "No" : function(){
	       $(this).dialog("close");
	      }
	     }
	 });
	$("#confirmDialogCancelTransferStudent").dialog("open");
	
});

$("#transferStudentsYes").off('click').on('click',function(e){
	var accountabilityDistrictId ='';
	if($('#accountabilityDistrictOrgsSelectDestination').val()!=null && $('#accountabilityDistrictOrgsSelectDestination').val()!=undefined){
		accountabilityDistrictId=$('#accountabilityDistrictOrgsSelectDestination').val();
	}
	var targatedAccountabilityDistrictIdentifier =$('#accountabilityDistrictOrgsSelectDestination').find(":selected").attr('displayidentifier');
	if(targatedAccountabilityDistrictIdentifier == undefined ){
		targatedAccountabilityDistrictIdentifier='';
		}
	var buttonsToToggle = $('#transferStudentsYes, #transferStudentsNo');
	buttonsToToggle.prop('disabled', true).attr('disabled', 'disabled');
	e.preventDefault();
	transferSelectedStudentArr = [];
	var targetDistrictIdentifier=  null;
	var grid = $("#viewConfirmationGridTable");
	var transferStudent = "";
	var allRowsIds = grid.jqGrid('getDataIDs');
	
	if($('#transferStudentsOrgFilter_district').val() != $('#districtOrgsSelectDestination').val()){
		targetDistrictIdentifier = $('#districtOrgsSelectDestination').val();
	}
	
    for(var i=0;i<allRowsIds.length;i++){
        var result = $.grep(transferSelectedStudentArr, function(studentO){ return studentO.id == allRowsIds[i]; });
              
        if(result.length == 0){
        	 	var rData = $('#viewConfirmationGridTable').jqGrid('getRowData',allRowsIds[i]);
        	 	var targetAttendanceSchoolId=null;;
        	 	
        	 	if(rData.attendanceSchoolNamesID.trim().length >  0){
        	 		targetAttendanceSchoolId = rData.attendanceSchoolNamesID;
        	 	}
	        	var targetAypSchoolId = null;
	        	var targetLocalId = "";
	        	var exitWithdrawalType = null;
	        	var oldAttendanceSchoolId = $('#transferStudentsOrgFilter_school').val();
        	 	if(rData.aypSchoolNamesID.trim().length >  0 && rData.aypSchoolNamesID !='R'){
        	 		targetAypSchoolId = rData.aypSchoolNamesID;
        	 	}
        	 	if(rData.localStudentIdentifiers.trim().length >  0){
        	 		targetLocalId = rData.localStudentIdentifiers;
        	 	}
        	 	if(rData.exitReasonCode.trim().length >  0){
        	 		exitWithdrawalType = rData.exitReasonCode;
        	 	}
				
        	 	if(rData.existingStudentFlag.trim() == 0 ){        		 
	        		transferSelectedStudentArr.push('{"stateStudentIdentifier": "' + rData.stateStudentIdentifier +
	        										'","targetAttendanceSchoolId" :' + targetAttendanceSchoolId +
	        										',"targetAypSchoolId" :' + targetAypSchoolId +
	        										',"targetLocalId" : "' + targetLocalId +
	        										'","exitWithdrawalType" :'+ exitWithdrawalType +
	        										',"oldAttendanceSchoolId" :'+ oldAttendanceSchoolId +
	        										',"targetDistrictIdentifier" :' + targetDistrictIdentifier +
	        										',"studentId" :' + allRowsIds[i] 
	        							+'}');
        	 }	
        }
    }
	
    if(transferSelectedStudentArr.length == 0){
    	$("#trasferStudentRecordsValidation").show();
		setTimeout("aart.clearMessages()", 5000);
		setTimeout(function(){ $('#trasferStudentRecordsValidation').hide(); },5000);
		buttonsToToggle.prop('disabled', false).removeAttr('disabled');
		return false;
    }
    
    if(transferSelectedStudentArr.length == 1){
    	transferStudent = transferSelectedStudentArr[0];
		transferSelectedStudentArr = [""];
	} 
    
		$.ajax({
		url: 'transferStudents.htm',
		dataType: 'json',
		type: "POST",
		data:{
				transferSelectedStudentArr : transferSelectedStudentArr,
				transferStudent : transferStudent,
				accountabilityDistrictId : accountabilityDistrictId,
				accountabilityDistrictIdentifier : targatedAccountabilityDistrictIdentifier
		}
	}).done(function(data) {
		$("#transferStudentSelectViewGrid").show();
		$("#selectDestinationDistrictViewDiv").hide();
		$("#removeNotTransferredStudentFromDestinationViewDiv").hide();
		$("#transferDestinationConfirmationDiv").hide();
		$("#searchFilterTransferStudentsErrors").show();
		transferSelectedStudentArr = [];
		confirmSelectedStudentArr =[];
		existingSchoolIds = [];
		
		if($("#transferStudentsByOrgTable")[0].grid && $("#transferStudentsByOrgTable")[0]['clearToolbar']){
			   $("#transferStudentsByOrgTable")[0].clearToolbar();
		     } 
			$("#transferStudentsByOrgTable").jqGrid('setGridParam',{
				postData: { "filters": ""}
			}).trigger("reloadGrid",[{page:1}]);
			$("#transferStudentsGridNext").prop("disabled",true).addClass('ui-state-disabled');
	
		if(data.response == "success")
			{
			$("#transferStudentSuccessMessage").show();
			setTimeout("aart.clearMessages()", 5000);
			setTimeout(function(){ $('#transferStudentSuccessMessage').hide(); },5000);
			}
		else if(data.response == "failed"){
			$("#transferStudentFailMessage").show();
			setTimeout("aart.clearMessages()", 5000);
			setTimeout(function(){ $('#transferStudentFailMessage').hide(); },5000);
			
		}else{
			$("#transferNoStudentFailMessage").show();
			setTimeout("aart.clearMessages()", 5000);
			setTimeout(function(){ $('#transferNoStudentFailMessage').hide(); },5000);
		}
		
		buttonsToToggle.prop('disabled', false).removeAttr('disabled');
	});
	
});


function populateDestinationDistrict(){
	var districtOrgSelect = $('#districtOrgsSelectDestination');clrDistrictOptionText ='';
	districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#districtOrgsSelectDestination').html("");
	$('#districtOrgsSelectDestination').append($('<option></option>').val("").html("Select")).trigger('change.select2');

	var stateOrgId = $('#transferStudentsOrgFilter_state').val();
if(stateOrgId == "" || stateOrgId =="undefined")
	{
		$("#districtOrgsSelectDestination").val("").trigger('change.select2');
		return false;
	}
	$.ajax({
		url: 'getOrgsBasedOnUserContext.htm',
		dataType: 'json',
		data: {
			orgId : stateOrgId,
	    	orgType:'DT',
	    	orgLevel: 50	
	    	},				
		type: "GET",
		
		}).done(function(districtOrgs) {
			var isSameDistrict = false;
			if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
				$.each(districtOrgs, function(i, districtOrg) {
					clrDistrictOptionText = districtOrgs[i].organizationName;
					districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(clrDistrictOptionText));
					
					if($("#transferStudentsOrgFilter_district").val() == districtOrgs[i].id){
						isSameDistrict = true;
						$("#districtOrgsSelectDestination").val(districtOrg.id).attr('selected','true');
						$("#districtOrgsSelectDestination").trigger('change');
					}	
					
				});
				if (districtOrgs.length == 1 && !isSameDistrict) {
					districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$("#districtOrgsSelectDestination").trigger('change');
				}
			}
			
			$(' #districtOrgsSelectDestination').trigger('change.select2');
	    });

}

function populateDestinationAccountabilityDistrict(){
	var accountabilityDistrictOrgSelect = $('#accountabilityDistrictOrgsSelectDestination');clrDistrictOptionText ='';
	accountabilityDistrictOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#accountabilityDistrictOrgsSelectDestination').html("");
	$('#accountabilityDistrictOrgsSelectDestination').append($('<option></option>').val("").html("Select")).trigger('change.select2');

	var stateOrgId = $('#transferStudentsOrgFilter_state').val();
if(stateOrgId == "" || stateOrgId =="undefined")
	{
		$("#accountabilityDistrictOrgsSelectDestination").val("").trigger('change.select2');
		return false;
	}
	var stateOrgIds = [stateOrgId];
	$.ajax({
		url: 'getDistrictsForStates.htm',
		dataType: 'json',
		data: {
			stateIds: stateOrgIds
	    	},				
		type: "GET"
		}).done(function(districtOrgs) {
			if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
				$.each(districtOrgs, function(i, districtOrg) {
					clrDistrictOptionText = districtOrgs[i].organizationName;
					accountabilityDistrictOrgSelect.append($('<option></option>').val(districtOrg.id).html(clrDistrictOptionText).attr('displayIdentifier',districtOrg.displayIdentifier));					
				});
				if (districtOrgs.length == 1) {
					accountabilityDistrictOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$("#accountabilityDistrictOrgsSelectDestination").trigger('change');
				}
			}
			
			$('#accountabilityDistrictOrgsSelectDestination').val("").trigger('change.select2');
	    });

}

function buildDestinationDistrictGrid(){
	
	var $gridAuto = $("#destinationDistrictByOrgTable");
//	var gridWidthForVS = $gridAuto.parent().width();		
//	if(gridWidthForVS < 500) {
		var	gridWidthForVS = 1040;				
//	}
	var cellWidthForVS = gridWidthForVS/4;
	
	colModelDestinationStudent = [ {name:'stateStudentIdentifier',label:'stateStudentIdentifierstudentTransfer', width:100, sortable:false, search : false, hidden: false,hidedlg:true},
		                        {name:'legalLastName',label:'legalLastNamestudentTransfer', width:150, sortable:false, search : false, hidden: false,hidedlg:true},
		                        {name:'legalFirstName',label:'legalFirstNamestudentTransfer', width:150,sortable:false, search : false, hidden: false,hidedlg:true},
							    {name:'legalMiddleName',label:'legalMiddleNamestudentTransfer', width:150,sortable:false, search : false, hidden: true,hidedlg:true},
								{name:'destinationAttandanceSchool',label:'destinationAttandanceSchoolstudentTransfer', width:200,sortable:false, search : false, hidden: false,hidedlg:true,formatter : destinationDistrictAttandanceSchoolFormatter},
								{name:'aypSchoolNames',label:'aypSchoolNamesstudentTransfer', width:200,sortable:false,search : false, hidden: false},
								{name:'destinationAYPSchool',label:'destinationAYPSchoolstudentTransfer', width:200,sortable:false, search : false, hidden: false,hidedlg:true,formatter : transferStudDestinationAYPSchoolFormatter},
								{name:'localStudentIdentifiers',label:'localStudentIdentifiersstudentTransfer', width:150,sortable:false, search : false, hidden: false,hidedlg:true},
								{name:'destinationLocalId',label:'destinationLocalIdstudentTransfer', width:200,sortable:false, search : false, hidden: false,hidedlg:true,formatter : transferStudDestinanationLocalIdFormatter},
								{name:'exitReason',label:'exitReasonstudentTransfer', width:200,sortable:false, search : false, hidden: false,hidedlg:true,formatter : transferStudExitReasonFormatter},
								{name:'schoolId',label:'schoolIdstudentTransfer', width:200,sortable:false, search : false, hidden: true,hidedlg:true}
							];
	
	$gridAuto.scb({
		datatype : "local",
		width: gridWidthForVS,
	  	colModel :colModelDestinationStudent,
	  	colNames : [ 'State ID', 'Last Name', 'First Name', 'Middle Name','Destination Attendance School','Existing Accountability School','Destination Accountability School',
		             'Local Id','Destination Local Id','Exit Reason','schoolId'
		           ],
		rowNum : 10000,
		rowList : [ 10, 20, 30, 40, 60, 90 ],
		sortname: 'legalLastName,legalFirstName,legalMiddleName',
		sortorder: 'asc',
		loadonce: true,
		multiselect: false,
	    beforeRequest: function() {
	    	
	    },localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
	    	root: function(obj) { 
	    		return obj.rows;
	    	}
	    },
	    onSelectRow: function(rowid, status, e){
      	},
       onSelectAll: function(aRowids,status) {
	    },
	    loadComplete:function(allRowsIds,status){	    	
	    	transferStudDestinationGridComplete($gridAuto);
	    	        
	         var tableid=$(this).attr('id');	         
	         var objs= $( '#gbox_'+tableid).find('[id^=destinationDistrictByOrgTable_]');	        
	         $.each(objs, function(index, value) {   	        	 
	         var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));      
	         $(value).attr('title',$(nm).text()+' filter');                          
	                    });
	    	
	         $("select[id^=tsDestDist_],select[id^=destDist_]").select2({
	   		  placeholder:'Select',
	   		  multiple: false,
	      		  allowClear : true
	   		 });
	         $('#tansStudDestGridHeaderDestinationLocalId').select2({
	   		  placeholder:'Select',
	   		  multiple: false,
	      		  allowClear : true
	   		 });
	    },
	    gridComplete:function(allRowsIds,status){	    
	    	//transferStudDestinationGridComplete($gridAuto);
	    }
	});
	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
	$("#destinationDistrictByOrgTablebuttonColumnChooser").hide();
	$("#view_destinationDistrictByOrgTable").hide();
	$("#transferStudentsByOrgTable").jqGrid('clearGridData');
}


function destinationDistrictAttandanceSchoolFormatter(cellvalue, options, rowObject){
	
	
	var cmbHtml	= '<select id="destDist_attndncSchool_'+options.rowId+'"  style="width: 180px" title="Destination Attendance School" name="destDist_attndncSchool_'+options.rowId+'">'+
		  '<option value="">Select</option></select>';
	
	return cmbHtml; 
	
}

function populateSelectedStudentsForTransfer(){
	  
	  if(transferSelectedStudentArr.length < 13)
		  {
		  
		  $("#destinationDistrictByOrgTable").jqGrid('setGridHeight', 'auto');
		  }else
			  $("#destinationDistrictByOrgTable").jqGrid('setGridHeight', '447');
	  
	  $("#destinationDistrictByOrgTable").jqGrid('setGridParam',{
			datatype:"json", 
			datatype : "local",
			search: false, 
			data:transferSelectedStudentArr
		}) .trigger("reloadGrid",[{page:1}]);
	}

function buildTransferStudentsByOrgGrid(){

	var $gridAuto = $("#transferStudentsByOrgTable");
	//Unload the grid before each request.
	//$gridAuto.jqGrid('GridUnload');
	
	var gridWidthForVS = $gridAuto.parent().width();		
	if(gridWidthForVS < 750) {
		gridWidthForVS = 750;				
	}
	var cellWidthForVS = gridWidthForVS/5;
	colModelTransferStudent = [ {name:'stateStudentIdentifier',label:'stateStudentIdentifiertransferStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
	                        {name:'legalLastName',label:'legalLastNametransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
	                        {name:'legalFirstName',label:'legalFirstNametransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
						    {name:'legalMiddleName',label:'legalMiddleNametransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
								formatter: escapeHtml},
						    {name:'attendanceSchoolNames',label:'attendanceSchoolNamestransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            
                            {name:'aypSchoolNames',label:'aypSchoolNamestransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'accountabilityDistrictName',label:'accountabilityDistrictNameTransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'gradeCourseName',label:'gradeCourseNametransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'dateOfBirthStr',label:'dateOfBirthtransferStudent', width:cellWidthForVS, sorttype : 'date', search : true, hidden: true},
                            {name:'generationCode',label:'generationCodetransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
                            	formatter: escapeHtml},
                            {name:'genderString',label:'genderStringtransferStudent', width:cellWidthForVS, search : true, hidden: true, stype : 'select', searchoptions: { value : ':All;1:Male;0:Female', sopt:['eq'] }},
                            {name:'firstLanguage',label:'firstLanguagetransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'comprehensiveRace',label:'comprehensiveRacetransferStudent', width:cellWidthForVS, sorttype : 'text', search : true,hidden: true},
                            {name:'localStudentIdentifiers',label:'localStudentIdentifierstransferStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
                            {name:'currentSchoolYears',label:'currentSchoolYearstransferStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
                            {name:'attendanceSchoolDisplayIdentifiers',label:'attendanceSchoolDisplayIdentifierstransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'residenceDistrictIdentifiers',label:'residenceDistrictIdentifierstransferStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
                            {name:'primaryDisabilityCode',label:'primaryDisabilityCodetransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'rosterIds',label:'rosterIdstransferStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: true},
                            {name:'gradeCourseId',label:'gradeCourseIdtransferStudent', width:cellWidthForVS, sortable : 'false', search : false, hidedlg : true, hidden: true},
                            {name:'programName',label:'programNametransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'accessProfileStatus',label:'accessProfileStatustransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;NO SETTINGS:NO SETTINGS;CUSTOM:CUSTOM', sopt:['eq'] }, hidden: false},
                            {name:'status',label:'statustransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;Not Applicable:Not Applicable;Not Started:Not Started; Completed:Completed;Ready to Submit:Ready to Submit;In Progress:In Progress', sopt:['eq'] }, hidden: true},
                            {name:'hispanicEthnicity',label:'hispanicEthnicitytransferStudent', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
                            {name:'giftedStudent',label:'giftedStudenttransferStudent', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
                            {name:'esolParticipationCode',label:'esolParticipationCodetransferStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'schoolId',label:'schoolIdtransferStudent', width:cellWidthForVS, sortable : 'false', search : false, hidedlg : true, hidden: true},
                          ];
	
	
	
	//JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVS,
		colNames : [ 'State ID', 'Last Name', 'First Name', 'Middle Name','School Name','Accountability School Name','Accountability District Name','Grade ', 'Date of Birth', 'Generation', 'Gender', 'First Language',
		             'Comprehensive Race','Local Id', 'Current School Year', 'School Id','District Id',
		             'Disability', 'Roster Id','Grade Id', 'Assessment Program', 'PNP Profile', 'First Contact',
		             'Hispanic Ethnicity', 'Gifted Student', 'ESOL Participation','Attendance School ID'
		           ],
	
	    colModel :colModelTransferStudent,           
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#ptransferStudentsByOrgTable',
		// F-820 Grids default sort order
		sortname : 'legalLastName,legalFirstName,legalMiddleName',
        sortorder: 'asc',
		loadonce: false,
		viewable: false,
		multiselect:true,
	    beforeRequest: function() {
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');

	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	        
	        if($('#transferStudentsOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#transferStudentsOrgFilter').orgFilter('value'));
	        	selectedOrg = orgs;
	        	$(this).setGridParam({postData: {'orgChildrenIds': function() {
						return orgs;
					}}});
	        } else if($(this).getGridParam('datatype') == 'json') {
        		return false;
	        }
	        $('#transferStudentsGridNext').attr('disabled','disabled').addClass('ui-state-disabled');
	    },localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
	    	root: function(obj) { 
	    		return obj.rows;
	    	}
	    },
	    onSelectRow: function(rowid, status, e){
	    	 var rData = $('#transferStudentsByOrgTable').jqGrid('getRowData',rowid);
	    	
	    	
	    	 var studentObj = { stateStudentIdentifier : rData.stateStudentIdentifier ,
	    			 			legalLastName  : rData.legalLastName , 
	    			 			legalFirstName : rData.legalFirstName,
	    			 			aypSchoolNames : rData.aypSchoolNames,
	    			 			localStudentIdentifiers : rData.localStudentIdentifiers,
	    			 			schoolId		:rData.schoolId,
	    			 			id : rowid
  		  		    };
	    	
	    	if(status){
	    		var found = false; 
	    		$.each(transferSelectedStudentArr, function(i){
      			    if(transferSelectedStudentArr[i].id === rowid) {
      			    	found = true;
      			    }
      			 }); 
	    		 if( ! found )
	    			 transferSelectedStudentArr.push(studentObj);
        		  var grid = $(this);
        		  var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox", grid);
        		   //if selected row and checkbox is disabled, do not let it get checked.
        		   if(cbsdis.is(":disabled")){
        			  cbsdis.prop('checked', false);         			   
        		   }
        	   } else{
        		   $.each(transferSelectedStudentArr, function(i){
        			    if(transferSelectedStudentArr[i].id === rowid) {
        			    	transferSelectedStudentArr.splice(i,1);
        			        return false;
        			    }
        			});
       		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
        		   //do nothing.
        	   }
	    	sortingArrayData(transferSelectedStudentArr);
	    	enableDisableTransferNextBtn(transferSelectedStudentArr);
      	},
       onSelectAll: function(aRowids,status) {
    	   transferSelectedStudentArr = [];
	    	var grid = $(this);
	        if (status) {
	            // uncheck "protected" rows
	            var cbs = $("tr.jqgrow > td > input.cbox:disabled", $(this)[0]);
	            cbs.removeAttr("checked");
	            var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
			        if($("#jqg_transferStudentsByOrgTable_"+allRowsIds[i],grid).is(":disabled")){	
			        	grid.jqGrid('setSelection', allRowsIds[i], false);
			        }
			        var result = $.grep(transferSelectedStudentArr, function(studentO){ return studentO.id == allRowsIds[i]; });
			        if(result.length == 0){
			        	 var rData = $('#transferStudentsByOrgTable').jqGrid('getRowData',allRowsIds[i]);
				    	
			        	 var studentObj = { stateStudentIdentifier : rData.stateStudentIdentifier ,
		    			 			legalLastName  : rData.legalLastName , 
		    			 			legalFirstName : rData.legalFirstName,
		    			 			aypSchoolNames : rData.aypSchoolNames,
		    			 			localStudentIdentifiers : rData.localStudentIdentifiers,
		    			 			schoolId		:rData.schoolId,
		    			 			id : allRowsIds[i]
	  		  		    };
			        	
			        	transferSelectedStudentArr.push(studentObj);
			        }	
			    }	
	        }
	        else{
	        	var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
	            	var result = $.grep(transferSelectedStudentArr, function(studentO){ return studentO.id == allRowsIds[i]; });
	            	if( result.length == 1 ){
	            		var indx = transferSelectedStudentArr.indexOf(result[0]);
	            		transferSelectedStudentArr.splice(indx,1);
	            	}	
	            }
	        }
	        sortingArrayData(transferSelectedStudentArr);
	        enableDisableTransferNextBtn(transferSelectedStudentArr);
	    },
	   loadComplete:function(data){
	    	var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
	            }
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	            $('#cb_'+tableid).removeAttr('aria-checked');
	             $('#cb_'+tableid).attr('title','Transfer Student Grid All Check Box');
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');
	                    if ( $(value).is('select')) {
		                	   $(value).removeAttr("role");
		                	   $(value).css({"width": "100%"});
		                    	$(value).select2({
		              	   		  placeholder:'Select',
		            	   		  multiple: false,
		            	      		  allowClear : true
		            	   		 });
		                    };
	                    });
	    }
	});
	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);

}


function buildRemoveDestinationStudentGrid(){
	
	
	var $gridAuto = $("#removeNotTransferredStudentFromDestinationTable");
	//Unload the grid before each request.
//	$gridAuto.jqGrid('GridUnload');
	var gridWidthForVO = 980;		
	if(gridWidthForVO < 350) {
		gridWidthForVO = 980;				
	}
	var cellWidthForVS = gridWidthForVO/5;
	var cmforRemoveDestinationStudentGrid = [ 
	                                         	{label: ' ', name: 'existingStudentFlag', index: 'existingStudentFlag', hidedlg: false,width: 60,sortable:false,search:false, formatter:existingStudentFlagFormatter},
					                            {name:'stateStudentIdentifier', width:cellWidthForVS, sortable:false, search : false, hidden: false},
					 	                        {name:'legalLastName', width:cellWidthForVS, sorttype : false, search : false, hidden: false,
													formatter: escapeHtml},
											    {name:'legalFirstName', width:cellWidthForVS, sortable:false, search : false, hidden: false,
													formatter: escapeHtml},
												{name:'attendanceSchoolNamesID', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},	
												{name:'attendanceSchoolNames', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
												{name:'aypSchoolNamesID', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
						                        {name:'aypSchoolNames', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},	
						                        {name:'localStudentIdentifiers', width:150,sortable:false, search : false, hidden: false,hidedlg:true},
						                        {name:'exitReasonCode', width:150,sortable:false, search : false, hidden: false,hidedlg:true},
											    {label: ' ', name: 'delete', index: 'delete', hidedlg: true,width: 60,sortable:false,search:false, formatter:deleteNotToBeMovedStudentFormatter},
						                        {name:'currentSchoolId', width:150,sortable:false, search : false, hidden: true,hidedlg:true}
						                        ];
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
	//	height : '200px',
		colNames : ['<img src="images/orange-flag.png" title="These Studnets are already enrolled in destination school"/>',
		            'State Id','Last Name', 'First Name','attendanceSchoolNamesID','Attendance School','aypSchoolNamesID','Accountability School','Local Id','Exit Reason Code',
		            '<img src="images/delete_icon.gif" title="Remove Students"/>',''],
	  	colModel :cmforRemoveDestinationStudentGrid,
		sortname : 'legalLastName,legalFirstName',
		sortorder: 'asc',
		viewrecords: true,
		rowNum : 10000,
		rowList: [2000],        // disable page size dropdown
	    pgbuttons: false,     // disable page control like next, back button
	    pgtext: null,  
	    loadonce: true,
		refresh: false,
		viewable: true,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
  		jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
	    	root: function(obj) { 
	    		return obj.rows;
	    	} 
	    },
	    beforeRequest: function() {
	    	
	    },
	    loadComplete: function(){
	    	$("#destinationDistrictByOrgTable").jqGrid('clearGridData');
	    }
	});
   	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
   	$gridAuto[0].toggleToolbar();
}


function populateDestinationSelectedStudentForTransfer(){
	transferSelectedStudentArr=[];
	var grid = $("#destinationDistrictByOrgTable");
	var allRowsIds = grid.jqGrid('getDataIDs');
	
    for(var i=0;i<allRowsIds.length;i++){
    	var aypScoolName = '';
          	 var rData = $('#destinationDistrictByOrgTable').jqGrid('getRowData',allRowsIds[i]);
        	 var destAttndncSchool='destDist_attndncSchool_'+allRowsIds[i];
        	 
        	 if($("#"+destAttndncSchool).val() == ''){
        			$("#trasferStudentAttendaceSchoolError").show();
    				setTimeout("aart.clearMessages()", 5000);
        			setTimeout(function(){ $('#trasferStudentAttendaceSchoolError').hide(); },5000);
        			return false;
        	 }
        	 var destAYPSchool = 'tsDestDist_aypSchool_'+allRowsIds[i];
        	var destSelectedaypSchool = $("#"+destAYPSchool+" option:selected").val();
        	 if(destSelectedaypSchool.trim().length > 0  ){
        		 aypScoolName = $("#"+destAYPSchool+" option:selected").text();
        	 }
        	 var destLocalId = 'tsDestDist_LocalId_'+allRowsIds[i];
        	 var exitReasonCode = 'tsDestDist_exitReason_'+allRowsIds[i];
        	 if( $("#"+exitReasonCode).val() == ''){
        			$("#trasferStudentExitReasonError").show();
    				setTimeout("aart.clearMessages()", 5000);
        			setTimeout(function(){ $('#trasferStudentExitReasonError').hide(); },5000);
        			return false;
        	 }
        	 if($("#districtOrgsSelectDestination").val() == '' ){
           			$("#trasferStudentDistrictValidation").show();
        			setTimeout("aart.clearMessages()", 5000);
        			setTimeout(function(){ $('#trasferStudentDistrictValidation').hide(); },5000);
        			return false;
        	}
        	 var studentObj =	{ stateStudentIdentifier : rData.stateStudentIdentifier ,
			 			legalLastName  : rData.legalLastName , 
			 			legalFirstName : rData.legalFirstName,
			 			attendanceSchoolNamesID :$("#"+destAttndncSchool).val(),
			 			attendanceSchoolNames : $("#"+destAttndncSchool+" option:selected").text(),
			 			aypSchoolNamesID : $("#"+destAYPSchool).val(),
			 			aypSchoolNames : aypScoolName,
			 			localStudentIdentifiers :$("#"+destLocalId).val(),
			 			exitReasonCode : $("#"+exitReasonCode).val(),
			 			currentSchoolId:rData.schoolId,
			 			id : allRowsIds[i]
		    };
        	
        	transferSelectedStudentArr.push(studentObj);
    }
    if(transferSelectedStudentArr.length < 13)
	  {
	  
	  $("#removeNotTransferredStudentFromDestinationTable").jqGrid('setGridHeight', 'auto');
	  }else
		  $("#removeNotTransferredStudentFromDestinationTable").jqGrid('setGridHeight', '447');

    
    $("#removeNotTransferredStudentFromDestinationTable").jqGrid('clearGridData');
    
	$("#removeNotTransferredStudentFromDestinationTable").jqGrid('setGridParam',{
			datatype:"json", 
			datatype : "local",
			search: false, 
			data:transferSelectedStudentArr
		}) .trigger("reloadGrid",[{page:1}]);
	  
		
		$("#transferStudentSelectViewGrid").hide();
		$("#selectDestinationDistrictViewDiv").hide();
		$("#transferDestinationConfirmationDiv").hide();
		$("#removeNotTransferredStudentFromDestinationViewDiv").show();

}


function populateConfirmationViewGridData(){
	confirmSelectedStudentArr =[];
	
	var grid = $("#removeNotTransferredStudentFromDestinationTable");
	var allRowsIds = grid.jqGrid('getDataIDs');
    for(var i=0;i<allRowsIds.length;i++){
        if($("#jqg_removeNotTransferredStudentFromDestinationTable_"+allRowsIds[i],grid).is(":disabled")){	
        	grid.jqGrid('setSelection', allRowsIds[i], false);
        }
        var result = $.grep(confirmSelectedStudentArr, function(studentO){ return studentO.id == allRowsIds[i]; });
        var date = new Date();
        var districtEntryDate ="";
        if($('#transferStudentsOrgFilter_district').val() != $('#districtOrgsSelectDestination').val())
        	{
        		districtEntryDate = date;
        	}
        
        if(result.length == 0){
        	 	var rData = $('#removeNotTransferredStudentFromDestinationTable').jqGrid('getRowData',allRowsIds[i]);
        	 	
        	 	if(rData.existingStudentFlag.trim().length == 0 ){        		 
	        	var studentObj =	{ stateStudentIdentifier : rData.stateStudentIdentifier ,
				 			legalLastName  : rData.legalLastName , 
				 			legalFirstName : rData.legalFirstName,
				 			attendanceSchoolNamesID :rData.attendanceSchoolNamesID,
				 			attendanceSchoolNames : rData.attendanceSchoolNames,
				 			aypSchoolNamesID : rData.aypSchoolNamesID,
				 			aypSchoolNames : rData.aypSchoolNames,
				 			districtEntryDate :districtEntryDate,
				 			schoolEntryDate : date,
				 			localStudentIdentifiers: rData.localStudentIdentifiers,
				 			exitReasonCode :rData.exitReasonCode,
				 			exitDate :date,
				 			existingStudentFlag :rData.existingStudentFlag.trim().length, 
				 			id : allRowsIds[i]
			    };
        	 confirmSelectedStudentArr.push(studentObj);
        	 }	
        }
    } 
    if(confirmSelectedStudentArr.length < 13)
	  {
	  $("#viewConfirmationGridTable").jqGrid('setGridHeight', 'auto');
	  }else
		  $("#viewConfirmationGridTable").jqGrid('setGridHeight', '447');

    $("#viewConfirmationGridTable").jqGrid('clearGridData');
	  $("#viewConfirmationGridTable").jqGrid('setGridParam',{
			datatype:"json", 
			datatype : "local",
			search: false, 
			data:confirmSelectedStudentArr
		}).jqGrid('sortGrid', 'legalLastName', true, 'asc').jqGrid('sortGrid', 'legalFirstName', true, 'asc').trigger("reloadGrid",[{page:1}]);
    
	  $("#transferStudentSelectViewGrid").hide();
		$("#selectDestinationDistrictViewDiv").hide();
		$("#removeNotTransferredStudentFromDestinationViewDiv").hide();
		$("#transferDestinationConfirmationDiv").show();
		$("#removeNotTransferredStudentFromDestinationTable").jqGrid('clearGridData');
}

function sortingArrayData(sortingData){
	sortingData.sort(function (a, b) {
        var aSize = a.legalLastName.toLowerCase();
        var bSize = b.legalLastName.toLowerCase();
        var aLow = a.legalFirstName.toLowerCase();
        var bLow = b.legalFirstName.toLowerCase();
        if(aSize == bSize)
        {
            return (aLow < bLow) ? -1 : (aLow > bLow) ? 1 : 0;
        }
        else
        {
            return (aSize < bSize) ? -1 : 1;
        }
    }); 	
}

function buildConfirmationViewGrid(){
	
	var $gridAuto = $("#viewConfirmationGridTable");
	//Unload the grid before each request.
//	$gridAuto.jqGrid('GridUnload');
	var gridWidthForVO = 980;		
	if(gridWidthForVO < 350) {
		gridWidthForVO = 980;				
	}
	var cellWidthForVS = gridWidthForVO/5;
	var cmforConfirmationViewGrid = [ 
					                            {name:'stateStudentIdentifier', width:cellWidthForVS, sortable:false, search : false, hidden: false},
					 	                        {name:'legalLastName', width:cellWidthForVS, sorttype : false, search : false, hidden: false,
													formatter: escapeHtml},
											    {name:'legalFirstName', width:cellWidthForVS, sortable:false, search : false, hidden: false,
													formatter: escapeHtml},
												{name:'attendanceSchoolNamesID', width:cellWidthForVS, sortable:false, search : false, hidden: true},	
												{name:'attendanceSchoolNames', width:cellWidthForVS, sortable:false, search : false, hidden: false},
												{name:'aypSchoolNamesID', width:cellWidthForVS, sortable:false, search : false, hidden: true},
						                        {name:'aypSchoolNames', width:cellWidthForVS, sortable:false, search : false, hidden: false},	
						                        {name:'localStudentIdentifiers', width:150,sortable:false, search : false, hidden: false},
						                        {name:'districtEntryDate', width:cellWidthForVS, sortable:false, search : false, hidden: false,formatter: 'date', formatoptions: { srcformat: 'U/1000', newformat:'m/d/Y'}},	
												{name:'schoolEntryDate', width:cellWidthForVS, sortable:false, search : false, hidden: false,formatter: 'date', formatoptions: { srcformat: 'U/1000', newformat:'m/d/Y'}},
												{name:'exitReasonCode', width:cellWidthForVS, sortable:false, search : false, hidden: false},	
						                        {name:'exitDate', width:150,sortable:false, search : false, hidden: false,formatter: 'date', formatoptions: { srcformat: 'U/1000', newformat:'m/d/Y'}},
												{name:'existingStudentFlag', sortable:false, search : false, hidden: true},
						                        ];
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
	//	height : '200px',
		colNames : ['State Id','Last Name', 'First Name','attendanceSchoolNamesID','Attendance School','aypSchoolNamesID','Accountability School','Local Id',
		            'District Entry Date','School Entry Date','Exit Reason Code','Exit Date','Existing Student Flag'],
	  	colModel :cmforConfirmationViewGrid,
		viewrecords: true,
		rowNum : 10000,
		rowList: [2000],        // disable page size dropdown
	    pgbuttons: false,     // disable page control like next, back button
	    pgtext: null,  
	    loadonce: true,
		refresh: false,
		viewable: true,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
  		jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
	    	root: function(obj) { 
	    		return obj.rows;
	    	} 
	    },
	    beforeRequest: function() {
	    	
	    },
	  
	});
  	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
   	$gridAuto[0].toggleToolbar();
   	
  	
  	
}




function transferStudDestinationGridComplete($gridAuto){

	var colModel = $gridAuto.jqGrid("getGridParam","colModel");
	var attdSchoolIndx = getTransferStudJqGridColumnIndex(colModel,"destinationAttandanceSchool");
	bindTransferStudComboBoxToSearchHeader(attdSchoolIndx, "tansStudDestGridHeaderAttntSchool");
	
	attdSchoolIndx = getTransferStudJqGridColumnIndex(colModel,"destinationAYPSchool");
	bindTransferStudComboBoxToSearchHeader(attdSchoolIndx, "tansStudDestGridHeaderAYPSchool");
	
	attdSchoolIndx = getTransferStudJqGridColumnIndex(colModel,"destinationLocalId");
	bindTransferStudComboBoxToSearchHeader(attdSchoolIndx, "tansStudDestGridHeaderDestinationLocalId");
	
	attdSchoolIndx = getTransferStudJqGridColumnIndex(colModel,"exitReason");
	bindTransferStudComboBoxToSearchHeader(attdSchoolIndx, "tansStudDestGridHeaderExistReason");
	
	
	var allRowsIds = $gridAuto.jqGrid('getDataIDs');
	var exitReasons = 	getTransferStudExitReasons();
	
	for(var i=0;i<allRowsIds.length;i++){
		var cmbHtml	= '<select id="tsDestDist_exitReason_'+allRowsIds[i]+'" name="tsDestDist_exitReason_'+allRowsIds[i]+'"><option value="">Select</option>';
	    	for(var j = 0;j<exitReasons.length;j++){
	    		cmbHtml +=  '<option value="'+exitReasons[j].value+'">'+exitReasons[j].text+'</option>';
	    	}
		cmbHtml += '</select>';
		$("#tsDestDist_exitReason_"+allRowsIds[i]).html(cmbHtml);
	}
	
	
	$('#tansStudDestGridHeaderAttntSchool, #tansStudDestGridHeaderAYPSchool').select2({
	    placeholder:'Select for all',
	    multiple: false,
	    allowClear : true
	    
	   }).find('option').filter(function(){return $(this).val() > 0;}).remove().end()
	   .trigger('change.select2');
	 
	 $('#tansStudDestGridHeaderExistReason').select2({
	   placeholder:'Select for all',
	   multiple: false,
	   allowClear : true
	  });	
	
	$('#tansStudDestGridHeaderAttntSchool, #tansStudDestGridHeaderAYPSchool, #tansStudDestGridHeaderDestinationLocalId, #tansStudDestGridHeaderExistReason').css("width",'180px');
	$('#tansStudDestGridHeaderAttntSchool, #tansStudDestGridHeaderAYPSchool, #tansStudDestGridHeaderDestinationLocalId, #tansStudDestGridHeaderExistReason').next('div').children('span').css("width",'180px');
	
	$('#tansStudDestGridHeaderAttntSchool').on("change",function(e){
		$("select[id^=destDist_attndncSchool_]").val($(this).val()).trigger('change.select2').change();
		
	});
	
	$('#tansStudDestGridHeaderAYPSchool').on("change",function(e){
		$("select[id^=tsDestDist_aypSchool]").val($(this).val());
		if($(this).find('option:selected').text() != 'Keep existing Accountability' && $(this).val() != 'E' ){
			$("select[id^=tsDestDist_aypSchool]").trigger('change.select2').change();
		}else if($(this).find('option:selected').text() == 'Remove existing Accountability' && $(this).val() == 'R' ){
			$.each( $('#destinationDistrictByOrgTable tbody tr'),function(index, value){				 
				    $("#destinationDistrictByOrgTable tbody tr").filter(function(){				    	
				    		$(this).find('select[id^=tsDestDist_aypSchool_]').val().trigger('change.select2').change();
					    	return true;				    	 
				    });				  
			});
		}else{		
			$.each( $('#destinationDistrictByOrgTable tbody tr'),function(index, value){
				  var indexValue = jQuery(this).attr('id');
				  if(indexValue != undefined){
				    var ExistingAYPSchool = jQuery('#destinationDistrictByOrgTable').jqGrid ('getCell', indexValue, 'aypSchoolNames');
				    var ExistingAYPSchoolVal = $('#tansStudDestGridHeaderAYPSchool option').filter(function () { return $(this).html() == ExistingAYPSchool; }).val();
				    $("#destinationDistrictByOrgTable tbody tr").filter(function(){
				    	if($(this).attr('id') == indexValue){
				    		$(this).find('select[id^=tsDestDist_aypSchool_]').val(ExistingAYPSchoolVal).trigger('change.select2').change();;
					    	return true;
				    	} 
				    });
				  }
			});
		}		
	});

	$('#tansStudDestGridHeaderDestinationLocalId').on("change",function(e){
		if( $(this).val() == "KEEP_EXISTING"){
			$.each( $('#destinationDistrictByOrgTable tbody tr'),function(index,value){
				  var indexValue = jQuery(this).attr('id');
				  if(indexValue != undefined){
				    var destinationLocalIdVal = jQuery('#destinationDistrictByOrgTable').jqGrid ('getCell', indexValue, 'localStudentIdentifiers');
				    $("#destinationDistrictByOrgTable tbody tr").filter(function(){
				    	if($(this).attr('id') == indexValue){
				    		$(this).find('input[id^=tsDestDist_LocalId_]').val(destinationLocalIdVal).trigger('change.select2').change();;
				    		return true;
				    	} 
				    });
				  }
			});
		}else{
			$('#destinationDistrictByOrgTable tbody tr input[id^=tsDestDist_LocalId_]').val('').trigger('change.select2').change();;
		}
	});
	
	$('#tansStudDestGridHeaderExistReason').on("change",function(e){
		$("select[id^=tsDestDist_exitReason]").val($(this).val()).trigger('change.select2').change();
	});
	$('#districtOrgsSelectDestination').trigger('change');
}

function bindTransferStudComboBoxToSearchHeader(index, elementId){
	var colHeader = $("table[aria-labelledby=gbox_destinationDistrictByOrgTable] thead tr:eq(1) th:eq(" + index + ")");
	var optionHtml = "<select id='"+ elementId +"' title='Destination Local Id' style='width:185px;' class='bcg_select' name='" + elementId + "'> <option value=''>Select for all</option> ";
	if( elementId == "tansStudDestGridHeaderDestinationLocalId" ){
		optionHtml += '<option value="KEEP_EXISTING">Keep existing Local Ids</option><option value="REMOVE_EXISTING">Remove existing Local Ids</option>';
	}
	else if( elementId == "tansStudDestGridHeaderExistReason" ){
		var exitReasons = 	getTransferStudExitReasons();
		for(var i = 0;i<exitReasons.length;i++){
			optionHtml +=  '<option value="'+exitReasons[i].value+'">'+exitReasons[i].text+'</option>';
		}
	}
	optionHtml += '</select>';
	$(colHeader).html(optionHtml);
}
function getTransferStudExitReasons(){
	var exitReasons = [{value:"1",text:" 01 Transfer to Public School, Same District"},
	                   {value:"2",text:" 02 Transfer to Public School, Different District, Same State"},
	                   {value:"4",text:" 04 Transfer to an Accredited Private School"},
	                   {value:"5",text:" 05 Transfer to a Non-accredited Private School"}
	                  ] ;
	return exitReasons; 
}
function getTransferStudJqGridColumnIndex(colModel, columnName){
	for(var i=0;i<colModel.length;i++){
		if(colModel[i].name == columnName){
			return i;
		}
	}
}

function enableDisableTransferNextBtn(transferSelectedStudentArr){
	if(transferSelectedStudentArr.length >= 1){	
		$("#transferStudentsGridNext").prop("disabled",false);
		$('#transferStudentsGridNext').removeClass('ui-state-disabled');		
	}else{
		$("#transferStudentsGridNext").prop("disabled",true);
		$('#transferStudentsGridNext').addClass('ui-state-disabled');	
	} 
}

function existingStudentFlagFormatter(cellValue, options, rowObject){
	
	//var studentCurrentRowIdObject = $('#destinationDistrictByOrgTable').jqGrid('getRowData',rowObject.id);
	
	var studentCurrentSchoolIds = rowObject.currentSchoolId;
	var destinationSchoolId = rowObject.attendanceSchoolNamesID;
	if(studentCurrentSchoolIds.indexOf(destinationSchoolId) > -1){
		return '<a href="javascript:;"><img src="images/orange-flag.png" title="These Studnets are already enrolled in destination school"/> </a>';
	}else{
		checkingExistingStudentsAllFlagged = false; 
		return '';
	}

}
function deleteNotToBeMovedStudentFormatter (cellValue, options, rowObject){
	
	
	//var studentCurrentRowIdObject = $('#destinationDistrictByOrgTable').jqGrid('getRowData',rowObject.id);
	var studentCurrentSchoolIds = rowObject.currentSchoolId;
	var destinationSchoolId =rowObject.attendanceSchoolNamesID;
	if(studentCurrentSchoolIds.indexOf(destinationSchoolId) > -1){
		return '';
	}else{
		return '<a href="javascript:;" onclick="deleteTransferStudentFromList(' +options.rowId + ');"><img src="images/delete_icon.gif" title="Remove Students"/> </a>';
	}
	 
}

function deleteTransferStudentFromList(rowId){
	$("#confirmDialogDeleteTransferStudent").dialog({
	      buttons : {
	     "Yes" : function() {
	    	 deleteTransferStudentJqGridRowData("removeNotTransferredStudentFromDestinationTable",rowId);
	    	 $(this).dialog("close");
	     },
	     "No" : function(){
	       $(this).dialog("close");
	      }
	     }
	 });
	$("#confirmDialogDeleteTransferStudent").dialog("open");
}


/*function dateFormatter(cellval, opts, rowObject){
	if(cellval){
   	var date = new Date(cellval);
         opts = $.extend({}, $.jgrid.formatter.date, opts);
         return new Date("", date, 'm/d/Y', opts);
      }else{
   	   return '';
     }
}*/

function transferStudDestinationAYPSchoolFormatter(cellvalue, options, rowObject){
	var cmbHtml	= '<select id="tsDestDist_aypSchool_'+options.rowId+'" style="width: 180px" title="Destination Ayp School" name="tsDestDist_aypSchool_'+options.rowId+'">'+
		  '<option value="">Select</option></select>';
	
	return cmbHtml; 
}

function deleteTransferStudentJqGridRowData(gId,rowId){
	$("#"+gId).jqGrid('delRowData',rowId);
	var studentIds = $("#removeNotTransferredStudentFromDestinationTable").jqGrid ('getDataIDs');
	if( studentIds.length == 0 ){
		$("#transferStudentsConfirmGridNext").prop("disabled",true);
		$('#transferStudentsConfirmGridNext').addClass('ui-state-disabled');
		
	}
}

function transferStudDestinanationLocalIdFormatter(cellvalue, options, rowObject){

	var cmbHtml	= '<input type="text" style="width: 180px" title="Destination Local Id" id="tsDestDist_LocalId_'+options.rowId+'" name="tsDestDist_LocalId_'+options.rowId+'">';
	return cmbHtml; 
}
function transferStudExitReasonFormatter(cellvalue, options, rowObject){
	var cmbHtml	= '<select id="tsDestDist_exitReason_'+options.rowId+'" style="width: 180px" title="Exit Reason" name="tsDestDist_exitReason_'+options.rowId+'"><option value="">Select</option>';
	cmbHtml += '</select>';
	return cmbHtml; 
}


function populateGridHeaderDestinationSchool(elementId, districtId, isAttendanceSchool, existingDistrictId){
	var headerAttdSchool = $('#'+elementId),clrSchoolOptionText ='';
	headerAttdSchool.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#'+elementId).html("");
	if( isAttendanceSchool ){
		$('#'+elementId).append($('<option></option>').val("").html("Select for all")).trigger('change.select2');
	}	
	else if( existingDistrictId == districtId){
		$('#'+elementId).append("<option value='' selected='selected' >Select for all</option> <option value='E'>Keep existing Accountability</option><option value='R'>Remove existing Accountability</option>");
	}
	else if( existingDistrictId != districtId){
		$('#'+elementId).append("<option value='' selected='selected' >Select for all</option>");
	}
	
	
	
	if(districtId == "" || districtId =="undefined" || districtId == undefined)
	{
		
		$("#"+elementId).val("").trigger('change.select2');
		$('#'+elementId).css("width",'180px');
		$('#'+elementId).next('div').children('span').css("width",'180px');
		if(elementId == "tansStudDestGridHeaderAttntSchool"){
		$("select[id^=destDist_attndncSchool]").html("<option value='' selected='selected' >Select</option>");
		}else{
		$("select[id^=tsDestDist_aypSchool]").html("<option value='' selected='selected' >Select</option>");
		}
		return false;
	}
	$.ajax({
	url: 'getOrgsBasedOnUserContext.htm',
	dataType: 'json',
	data: {
		orgId : districtId,
    	orgType:'SCH',
    	orgLevel: 70	
    	},				
	type: "GET"
	}).done(function(schoolOrgs) {				
		if (schoolOrgs !== undefined && schoolOrgs !== null && schoolOrgs.length > 0) {
			$.each(schoolOrgs, function(i, schoolOrg) {
				clrSchoolOptionText = schoolOrgs[i].organizationName;
				headerAttdSchool.append($('<option></option>').val(schoolOrg.id).html(clrSchoolOptionText));
			});
			if (schoolOrgs.length == 1) {
				headerAttdSchool.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				$("#"+elementId).trigger('change');
			}
		} 
		$('#'+elementId).trigger('change.select2');
		$('#'+elementId).css("width",'180px');
		$('#'+elementId).next('div').children('span').css("width",'180px');
		
		var schoolOptionsHtml = $('#'+elementId).html();
		
		if(isAttendanceSchool){
			$("select[id^=destDist_attndncSchool]").html(schoolOptionsHtml.replace("Select for all","Select"));
		}
		else {
			
			schoolOptionsHtml = schoolOptionsHtml.replace("Select for all","Select");
			$("select[id^=tsDestDist_aypSchool]").html(schoolOptionsHtml);
		//	$("select[id^=tsDestDist_aypSchool] option[value='']").remove();
			$("select[id^=tsDestDist_aypSchool] option[value='E']").remove();
		    $("select[id^=tsDestDist_aypSchool] option[value='R']").remove();
		//	$("select[id^=tsDestDist_aypSchool]").prepend(schoolOptionsHtml);
			
		}
    });

}

