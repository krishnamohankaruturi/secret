<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<link rel="stylesheet" href="<c:url value='/css/reportAccess.css'/>" type="text/css" />
<style type="text/css">

</style>
<security:authorize access="hasRole('VIEW_REPORT_CONTROL_ACCESS')">
<span class="info_message ui-state-highlight successMessage hidden" id="editSuccessMessage" >Saved Successfully</span>

<div id="reportAccessTabContainer" class="form">
	
	<div class="form-fields">
		<label for="reportAccessAssessmentProgram" class="field-label required">Assessment Program:<span class="lbl-required">*</span></label>
		<select name="reportAccessAssessmentProgram" title="Assessment Program" class="bcg_select" id="reportAccessAssessmentProgram">
			<option value="0">Select</option>
		</select>
	</div>
	<div class="form-fields">
		<label class="isrequired field-label">State:<span class="lbl-required">*</span></label>
			<select id="reportAccessState" title="State" name="reportAccessState" class="bcg_select">
		</select>
		<input type="checkbox" class="selectAll_state" id="reportAccessState_selectAll_state" title="Select All" style="height :13px !important; margin-left: 5px;"/> Select All
	</div>
	
	<button class="btn_blue" id="reportAccessSearch">Search</button>		
	
</div>

<div id ="reportAccessSetUpActions">
	<ul id="reportAccessSetupDiv" class="nav nav-tabs sub-nav">
		 <li class="nav-item get-reportsetup">			
		<a class="nav-link active" id="reportaccess_reports" href="#tabs_reportaccess_reports" data-toggle="tab" role="tab">Reports</a>
		</li>
		 <li class="nav-item get-reportsetup">			
		<a class="nav-link active" id="reportaccess_general" href="#tabs_reportaccess_general" data-toggle="tab" role="tab">General Reports</a>
		</li>
	  	<li class="nav-item get-reportsetup">
	  	<a class="nav-link" id="reportaccess_instruct_embedded" href="tabs_reportaccess_instruct_embedded"  data-toggle="tab" role="tab">Instructionally Embedded</a>
	  	</li>	
	  	<li class="nav-item get-reportsetup">
	  	<a class="nav-link" id="reportaccess_year_end" href="tabs_reportaccess_year_end" data-toggle="tab" role="tab">End-of-Year</a>
	  	</li>	
	  	<li class="nav-item get-reportsetup">
	  	<a class="nav-link" id="reportaccess_student_archive" href="tabs_reportaccess_student_archive"  data-toggle="tab" role="tab">Student Report Archive</a>
	  	</li>	
	  	<li class="nav-item get-reportsetup">
	  	<a class="nav-link" id="reportaccess_extracts" href="tabs_reportaccess_extracts" data-toggle="tab" role="tab">Extracts</a>
	  	</li>	
	  	 	
	</ul>
	
	<div id="tabContent" class="tab-content"> 
	<div id="tabs_reportaccess_reports" class="tab-pane" role="tabpanel"> 
	</div>
	<div id="tabs_reportaccess_general" class="tab-pane" role="tabpanel"> 
	</div>
	<div id="tabs_reportaccess_instruct_embedded" class='hidden' class="tab-pane" role="tabpanel">
	</div>
	<div id="tabs_reportaccess_year_end" class="tab-pane" role="tabpanel"> 
	</div>
	<div id="tabs_reportaccess_student_archive" class="tab-pane" role="tabpanel"> 
	</div>
	<div id="tabs_reportaccess_extracts" class="tab-pane" role="tabpanel"> 
	</div>
	
	</div>
</div>


<div id="reportAccessGridSelectOneToModify">Please select any one to modify.</div>
<div class="full_main"  style="width: 86.128% !important;margin-left:18px;">
	<div id="reportAccessGridCell" >
		<div id="reportAccessTestSession" hidden="hidden"></div>
	 	<div id="reportAccessTestSessionGridContainer" class="kite-table" style="margin-left: -25px;">
	 		<table class="responsive" id="reportAccessGridTableId"></table>
			<div id="reportAccessGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>

<div id ="editReportAccessWindow" class="hidden">
		<jsp:include page="editReportAccessWindow.jsp"></jsp:include>
</div>

<%-- <script type="text/javascript" src="<c:url value='/js/configuration/reportAccess.js'/>"> </script> --%>	
</security:authorize>
<script>
var reportAccessListObj = [];
var assessmentProgId='';
var stateId='';
var assessmentProgReportSearch = '';
var stateReportSearch = '';
var assessmentProgramsReportAcc = '';
var colNamesObj = [];
var colModelObj = [];
var row = [];
var reportAccessGeneral = 'GEN';
var reportAccessInsEmb = 'IE';
var reportAccessYearEnd = 'YE';
var reportAccessStudentArch = 'SA';
var reportAccessDataExtracts = 'EX';


$(function($){
	loadAssessmentProgramsReportAccess();	
	reportAccessInit();
	$('#reportAccessAssessmentProgram').select2({
		placeholder:'Select',
		multiple: false
	});
	
	$('#reportAccessState').select2({
		placeholder:'Select',
		multiple: true
	});
	
	//reportAccessGridAuto();
	$('#reportAccessGridCell').hide();
	$('#reportAccessGridSelectOneToModify').hide();
	$('#reportAccessSetupDiv').hide();
	//filteringOrganization($('#reportAccessState'));
	$("#reportAccessSearch").prop("disabled",true);
	$('#reportAccessSearch').addClass('ui-state-disabled');
});

function clearReportPage(){
	$('#reportAccessAssessmentProgram').val('');
	$('#reportAccessAssessmentProgram').trigger('change');
	$('#reportAccessAssessmentProgram').trigger('change.select2');	
	populateSelectReportAccess($('#reportAccessAssessmentProgram'), assessmentProgramsReportAcc, 'id', 'programName');
	$('#reportAccessGridSelectOneToModify').hide();
	$('#reportAccessState').val('');
	$('#reportAccessState').trigger('change');
	$('#reportAccessGridCell').hide();
	$("#reportAccessSearch").prop("disabled",true);
	$('#reportAccessSearch').addClass('ui-state-disabled');
}

function loadAssessmentProgramsReportAccess(){
	var select = $('#reportAccessAssessmentProgram');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	$.ajax({
		url: 'getCurrentUserAssessmentPrograms.htm',
		dataType: 'json',
		type: "POST"		
	}).done(function(assessmentPrograms){
		assessmentProgramsReportAcc = assessmentPrograms;
		populateSelectReportAccess(select, assessmentPrograms, 'id', 'programName');
		
	});
}
function populateSelectReportAccess($select, data, idProp, textProp){
	 $('#reportAccessAssessmentProgram').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
 	if (data !== undefined && data !== null && data.length > 0){
		for (var x = 0; x < data.length; x++){
		 		$select.append($('<option></option>').val(data[x][idProp]).text(data[x][textProp]));
		}
		
		if (data.length == 1) {
		 $select.find('option:first').prop('selected',false).next('option').attr('selected', 'selected');
		 $select.trigger('change');
		}
		  
		$select.val('').trigger('change.select2');
		return true;
	}
	return false;
}

function reportAccessInit(){
	//$('#reportAccessGridTableId').hide();
	 $('#reportAccessAssessmentProgram').on("change",function(){	
		 $('#reportAccessSetupDiv').hide();
		 $('#reportAccessTestSessionGridContainer').hide();
		 $('#reportAccessState').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		disabledReportSearchBtn();
		 var assessmentProgramId = $('#reportAccessAssessmentProgram').val();
		 if(assessmentProgramId == '' || assessmentProgramId == null){
			 return false;
		 }
    	  $.ajax({
    	        url: 'getMultipleStatesForUser.htm',
    	        data: {assessmentProgramId:assessmentProgramId },
    	        dataType: 'json',
    	        type: "GET"   	        
    		}).done(function(states) {
	        	states.forEach(function(entry) {
  	        	   $('#reportAccessState').append($('<option></option>').attr("value",entry.multipleStateId).text(entry.stateName));
 	        	});
 	        	$('#reportAccessState').trigger('change.select2');	
 	        });

    		$('#reportAccessState_selectAll_state').prop('checked', false);
      });
	 
	 $('#reportAccessSearch').off("click").on("click",function(event) { 
		 $('#reportAccessSetupDiv').show();
		 
		 if($('#reportAccessAssessmentProgram').find('option:selected').text() != 'Dynamic Learning Maps') {
			 $('#reportaccess_reports').show();
			 $('#reportaccess_general').hide();
			 $('#reportaccess_instruct_embedded').hide();
			 $('#reportaccess_year_end').hide();			 
			 $('#reportAccessTestSessionGridContainer').show();
			 $('#reportAccessSetUpActions>ul>li .active').removeClass('active');
			 $('#reportaccess_reports').addClass('active');
			 loadGrid(reportAccessGeneral);
		 } else {
			 $('#reportaccess_reports').hide();
			 $('#reportaccess_general').show();
			 $('#reportaccess_instruct_embedded').show();
			 $('#reportaccess_year_end').show();			 
			 $('#reportAccessTestSessionGridContainer').show();
			 $('#reportAccessSetUpActions>ul>li .active').removeClass('active');
			 $('#reportaccess_general').addClass('active');
			 loadGrid(reportAccessGeneral);
		 }

	 });	 

	 $('#reportaccess_reports').off("click").on("click",function(event) {
		 loadGrid(reportAccessGeneral);
	
	 }); 

	 $('#reportaccess_general').off("click").on("click",function(event) {
		 loadGrid(reportAccessGeneral);
	
	 });
	 
	 $('#reportaccess_instruct_embedded').off("click").on("click",function(event) {
		 loadGrid(reportAccessInsEmb);
	
	 });
	 
	 $('#reportaccess_year_end').off("click").on("click",function(event) {
		 loadGrid(reportAccessYearEnd);
	
	 });
	 
	 $('#reportaccess_student_archive').off("click").on("click",function(event) {
		 loadGrid(reportAccessStudentArch);
	
	 });
	 $('#reportaccess_extracts').off("click").on("click",function(event) {	
		 loadGrid(reportAccessDataExtracts);
	
	 });
	 

}

$(document).on("click", ".selectAll_state", function(ev){	
	var checkboxId= this.id;  
	checkboxId = checkboxId.replace("_selectAll_state","");	
	if($(this).is(':checked')){
	   $("#"+checkboxId).find('option').prop("selected",true);
        $("#"+checkboxId).trigger('change');
	   }else{
	      $("#"+checkboxId).find('option').prop("selected",false);
        $("#"+checkboxId).trigger('change');

	   }
	   
});

function loadGrid(reportCategoryTypeCode) {
  	var $gridAuto = $("#reportAccessGridTableId");	  	
	 $("#reportAccessGridTableId").jqGrid('clearGridData');
	 $("#reportAccessGridTableId").jqGrid("GridUnload");
    
	 var reportAccessAssessmentProgramId = $('#reportAccessAssessmentProgram').val();
	 var reportAccessStateIds = $('#reportAccessState').select2("val");
	 
	  assessmentProgReportSearch = $('#reportAccessAssessmentProgram').find('option:selected').text();
	  stateReportSearch = $('#reportAccessState').find('option:selected').text();
	  assessmentProgId = $('#reportAccessAssessmentProgram').find('option:selected').val(); 			 
	  stateId = $('#reportAccessState').val();					 
	  
	 if(reportAccessAssessmentProgramId != '' && reportAccessStateIds != ''){
		 
		 var functionsMapping = {
	    	"checkRolesHaveAccess": function (cellValue, opts, rowObject) {
	         	var checkBox ='';
	         	var stateRolePermArr = [];
	         	var reportPermForState = [];
	         	var orgArray = [];
	         	var reportPermForAllState = [];
	         	var orgName = [];
	         	var info = '';
	    		if(cellValue != undefined) {
	    			var i = 0;
	    			$("#reportAccessState option:selected").each(function () {	    				
	    				   var $this = $(this);
	    				   if ($this.length) {
	    				    orgArray.splice(i, 0, $this.text());
	    				    i++;
	    				   }
	    				});

	    			
		    		var orgNames = rowObject.orgName;
		    		orgName = orgNames.split(',');
		    		var stateArray = orgName;
 		    	 	
		    		stateRolePermArr = cellValue.split(',');
		    		
		    		if(orgArray.length>1) {
		    			var reportPermForAllState = [];
		    			var statusOrg = 'These states do not match either permissions or access: ';
		    			var permStatus = '';	
		    			
	 	    			    var noPermState = [];
			    			for(var i = 0; orgArray.length < i; i++) {
			    				for(var j = 0; stateArray.length < j; j++) {
			    					if(orgArray[i] == stateArray[j]) {
			    						
			    					} else {
			    						noPermState.push(orgArray[i]);
			    					}
			    				}
			    			}
			    			
			    			orgArray = noPermState; 
		    			
		    			for (var i = 0; i < orgArray.length; ++i) {
		    				permStatus = permStatus +'&#013; '+ orgArray[i]+' : '+'No Permission';
		    			}
		    			
		    			for(var i=0; stateRolePermArr.length > i; i++){
		    				str_perm = stateRolePermArr[i].split(':');		    				
		    				reportPermForAllState.splice(i, 0, parseInt(str_perm[1]));
		    				
		    				if(parseInt(str_perm[1]) === 1) {
		    					permStatus =  permStatus+ '&#013; '+ str_perm[0]+' : '+'No Access';
		    				}
							if(parseInt(str_perm[1]) === 0) {
		    					permStatus = permStatus +'&#013; '+ str_perm[0]+' : '+'No Permission';		    					
		    				}
		    			}
		    			
		    			statusOrg = statusOrg +permStatus + '&#013; &#013;'+ 'Please manage seperately.';
		    			
		    			var checkAllStateHAsSamePerm = false;
		    			for(var k = 0; reportPermForAllState.length > k; k++) {
		    				if(parseInt(reportPermForAllState[0]) === parseInt(reportPermForAllState[k])) {
		    					checkAllStateHAsSamePerm = true;
		    				} else {
		    					checkAllStateHAsSamePerm = false;
		    					break;
		    				}
		    			}		    			
		    			
	    				if(checkAllStateHAsSamePerm) {	
	    					if(reportPermForAllState[0] === 2) {
	    						checkBox = '<label class="switch"><input type="checkbox" checked="true" value='+str_perm[2]+' /><span class="slider round"  title="Access On"></span></label>';
	    					} else if (reportPermForAllState[0] === 1) {
	    						checkBox = '<label class="switch unChecked"><input type="checkbox" value='+str_perm[2]+' /><span class="slider round"  title="Access Off"></span></label>';
	    					} else {
	    						checkBox = '<label class="switch"><input type="checkbox" disabled/><span class="slider round" title="No Access" ></span></label>';
	    					}
	    					if(reportAccessStateIds.length> orgName.length) {
	     		    	 		checkBox = '<label class="switch"><input type="checkbox" disabled/><span class="slider round"></span></label>'+
	     		    	 		'<img src="images/Question Symbol.png" alt="Question" class= "questionMark" title="'+statusOrg+'">';
	    		    	 	} 
	    					
	    				} else {	    					
	    					checkBox = '<label class="switch"><input type="checkbox" disabled/><span class="slider round"></span></label>'+
	    					'<img src="images/Question Symbol.png" alt="Question" class= "questionMark" title="'+statusOrg+'">';
	    				}

		    		} else {
   		    	 		reportPermForState = stateRolePermArr[0].split(':');
   		    			if(parseInt(reportPermForState[1]) === parseInt("2")) {
   			    			checkBox = '<label class="switch"><input type="checkbox" checked="true" value='+reportPermForState[2]+' /><span class="slider round"  title="Access On"></span></label>';
   			    		} else if (parseInt(reportPermForState[1]) === parseInt("1")) {
   			    			checkBox = '<label class="switch unChecked"><input type="checkbox" value='+reportPermForState[2]+' /><span class="slider round"  title="Access Off"></span></label>';
   			    			
   			    		} else {
   					    	checkBox = '<label class="switch"><input type="checkbox" disabled/><span class="slider round" title="No Access"></span></label>';    					    	
   			    		}
		    		}
		    		
	    		}

		        return checkBox;
		    }
		} 	 
       	$.ajax({
        	url: 'getReportAccessData.htm?q=1', 
   	        data:  {
					   filters:"",
					   reportAccessAssessmentProgramId:reportAccessAssessmentProgramId,
					   reportAccessStateIds : reportAccessStateIds,
					   reportType:reportCategoryTypeCode,
					   page:	1,
					   rows:	10000,
					   sidx:	"reportName,subject",
					   sord:	"asc"
					   
					   },
   	        dataType: 'json',
   	        type: "POST" 		    	    	             
   		}).done(function(response) {	    	        	
   	        	colNamesObj = response.colNames;
   	        	colModelObj = response.colModel;	    	        	
   	      		 row = response.rows;        	
   	         	for (i=0; i<colModelObj.length; i++) {
   	                cm = colModelObj[i];
   	                if (cm.hasOwnProperty("formatter") && functionsMapping.hasOwnProperty(cm.formatter)) {
   	                    cm.formatter = functionsMapping[cm.formatter];
   	                }
   	           }
                      reportAccessGridAuto(colNamesObj,colModelObj,row);
   	          });
		  
		 $('#reportAccessGridCell').show();
	 }
}

$('#reportAccessState').on("change",function(){
	disabledReportSearchBtn();
});

function disabledReportSearchBtn(){
	var assessmentProgramId = $('#reportAccessAssessmentProgram').val();
	var reportAccessStateIds = $('#reportAccessState').select2("val");		
	
	if(reportAccessStateIds != null && reportAccessStateIds !='' ){
		var count = 0;
		$('#reportAccessState option').each(function() {
		    if(!$(this).is(':selected')){  
		    	count = count+1;
		    }
	    }); 
	    if(count > 0) {
	    	$('#reportAccessState_selectAll_state').prop('checked', false);
	    } else {
	    	$('#reportAccessState_selectAll_state').prop('checked', true);	    	
	    }
	} else {
    	$('#reportAccessState_selectAll_state').prop('checked', false);
	}
	if(assessmentProgramId != '' && reportAccessStateIds != null && assessmentProgramId != 0 && reportAccessStateIds !='' ){
		$("#reportAccessSearch").prop("disabled",false);
		$('#reportAccessSearch').removeClass('ui-state-disabled');	
	} else {
		$("#reportAccessSearch").prop("disabled",true);
		$('#reportAccessSearch').addClass('ui-state-disabled');
		$('#reportAccessSetupDiv').hide();
		$('#reportAccessTestSessionGridContainer').hide();
	}
	
}


function reportAccessGridAuto(colNamesObj,colModelObj,row){
	var subjectHiddenFlag = false;
   	if($('#reportaccess_extracts').hasClass('active')) {
   		subjectHiddenFlag = true;
   	}
	var $gridAuto = $("#reportAccessGridTableId");
	//Unload the grid before each request.
	var gridWidthForVO = $('#reportAccessGridTableId').parent().width();		
	if(gridWidthForVO < 1045) {
		gridWidthForVO = 1045;				
	}

  	var cellWidthForVO = gridWidthForVO/10; 
 	
 	var cmforViewScorerGrid = [
 		{  name : 'id', index : 'id',label: 'Report Acsess Id', key:true, sortable : false, hidden : true, hidedlg : true, frozen: true},
 		{  name : 'reportName', index : 'reportName',label: 'Report',  sortable : false,  search : false, hidden : false, hidedlg : true, frozen: true, width: 150},
 		{ name : 'subject', index : 'subject',  label: 'Subject', sortable : false, search : false, hidden : subjectHiddenFlag, hidedlg : true, frozen: true, width: 150},
 		{  name : 'permission', index : 'permission',label: 'Permission', sortable : false, search : false, hidden: true, hidedlg: true},
 		{  name : 'authorityId', index : 'authorityId',label: 'authorityId', hidden : true, hidedlg : true}
 	];
	 	
	 var columnNames = [ 'id', 'Report','Subject', 'Permission','authorityId'];
	 
 	 	if(colNamesObj!=null && colNamesObj!=undefined && colNamesObj!=null && colNamesObj!=undefined){
			if(colNamesObj.length>0){
				for(var i=0;i<colNamesObj.length;i++){
					columnNames.push(colNamesObj[i]);
				}	
			}		  			
		}
		if(colModelObj!=null && colModelObj!=undefined && colModelObj!=null && colModelObj!=undefined){
			if(colModelObj.length>0){
				for(var i=0;i<colModelObj.length;i++){
					cmforViewScorerGrid.push(colModelObj[i]);
				}
			}
		}  
	 
	 
		//JQGRID
	  	$("#reportAccessGridTableId").jqGrid({
			mtype: "POST",
			datatype : "local",
			data: row,
			width: gridWidthForVO,
			colNames : columnNames,
		  	colModel :cmforViewScorerGrid, 	
			rowNum : 10000,
			multiselectWidth: 40,
			rowList : [],
			pgbuttons: false,
		    pgtext: null,
		    viewrecords: false,
			pager : '#reportAccessGridPager',
			sortname : 'reportName,subject',
	        sortorder: 'asc',
	        emptyrecords : "No records to display",
	        height:'auto',
	        refresh: false,
			loadonce: false,
			viewable: false,
			shrinkToFit: false,
			onInitGrid: function () {
				if(!row.length>0) {
					 $("<div class='alert-info-grid'>No Record(s) Found</div>").insertAfter($(this).parent());
				}
		       
		    },

		    beforeSelectRow: function (rowid, e) {
		    	var check = false;
		         return check; 
		    },
		    beforeRequest: function() {	        
		      
		    },
		    gridComplete: function() {	            		            	
		    	//Need to store the radio button value so that we can set it back,
		    	//because JQGrid does not support automatic selection in memory.
	        	
		    },
		    loadComplete:function(){        
		        var tableid=$(this).attr('id'); 
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	           
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');
	                    
	                   if ( $(value).is('select')) {
	                	   $(value).removeAttr("role");
	                	   $(value).css({"width": "100%"});
				                   };
	                    });
	    	
	    }
	});

	$("#reportAccessGridTableId").jqGrid('setFrozenColumns');
	 $gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]); 

   	 
   	$('#reportAccessGridPager_left').append('<div><label class="switch"><input type="checkbox" checked="true" title="Access On"/><span class="slider round"></span></label> <p> = Report Access turned on</p></div>');
   	$('#reportAccessGridPager_center').append('<div><label class="switch unChecked"><input type="checkbox"title="Access Off" /><span class="slider round"></span></label> <p> = Report Access turned off</p></div>');
   	$('#reportAccessGridPager_right').append('<div><label class="switch"><input type="checkbox"title="No Access" disabled/><span class="slider round"></span></label> <p> = Report Permission Not Given</p></div>');

   	<security:authorize access="!hasRole('EDIT_REPORT_CONTROL_ACCESS')">
   		$('.round').click(false);   		
 	</security:authorize>
 	
   	<security:authorize access="hasRole('EDIT_REPORT_CONTROL_ACCESS')">
   	
   	$('td').find(':checkbox').on("click",function(){

   		var roleCode = '';
   		var activeFlag;
   		var reportAccessId = [];
   		reportAssessmentPgmIds = $(this).closest('tr').attr('id');
   		reportAccessId = reportAssessmentPgmIds.split(',');   		
   		var customFile = $(this).closest('tr').find('td:nth-child(2)').attr('title');

   		if($(this).prop("checked") == true) { 
   			$(this).parents('label').removeClass('unChecked');
   			$(this).siblings('span').prop('title', 'Access On');
   			var checkedValue = $(this).val();
   			roleCode = checkedValue;
   			activeFlag = true;
   		}
		if($(this).prop("checked") == false) {
   			$(this).parents('label').addClass('unChecked');
   			$(this).siblings('span').prop('title', 'Access Off');
	   		var checkedValue = $(this).val();
   			roleCode = checkedValue;
   			activeFlag = false;
   		}
				
		var URL ='editReportAccessDataForMultipleState.htm';
		var paramData ={
				reportAssessmentProgramId : reportAccessId, 
				groupCode : roleCode, 
				activeFlag : activeFlag
				};
		
		if($('#reportaccess_extracts').hasClass('active')) {			
			URL = 'editExtractAccessDataForMultipleState.htm';	
			paramData = {
					extractAssessmentProgramId : reportAccessId, 
					groupCode : roleCode, 
					activeFlag : activeFlag
			};
		} 
		
		$.ajax({
	        url: URL,
	        data: paramData,
	        dataType: 'json',
	        type: "POST"      
		}).done(function(states) {	       		
       		if(customFile == 'State Specific Files') {
       			stateSpecificFileHasPerm();
       		}
        	$("#editSuccessMessage").show();
        setTimeout(function(){
        	$("#editSuccessMessage").hide();
        }, 3000);
        });
		
   	});
   	</security:authorize>
}

function stateSpecificFileHasPerm() {
	var organizationId = $('#userDefaultOrganization').val();
	var groupId = $('#userDefaultGroup').val();
	var userAssessmentProgramId = $('#userDefaultAssessmentProgram').val();
	var data = {};
	data.currentOrganizationId = organizationId;
	data.currentGroupId = groupId;	
	data.currentAssessmentProgramId = userAssessmentProgramId;	
	
	$.ajax({
		url: 'getStateSpecificFileHasPerm.htm',
		dataType: 'json',
		data: data,
		type: "POST"		
	}).done(function(result) {			
		if(result) {
			$('#stateSpecificFilesId').show();
		} else {
			$('#stateSpecificFilesId').hide();				
		}
	});

}

</script>