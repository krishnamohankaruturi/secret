<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style type="text/css">
.ui-icon.ui-icon-search-new{
	background: url("./images/OTWviewicon.png") no-repeat !important;
	width:22px !important;
	height:15px;
	background-size:100% !important;
	background-position:bottom;
	margin-top:2px !important;
}
.ui-icon.ui-icon-copy-new{
	background: url("./images/OTWcopyicon.png") no-repeat !important;
	width:16px !important;
	height:16px;
	background-size:100% !important;
	margin-top:0px !important;
}
.ui-icon.ui-icon-include-exclude{
	background: url("./images/in_exIcon.png") no-repeat !important;
	width:16px !important;
	height:16px !important;
	background-size:100% !important;
	background-position:bottom;
	margin-top:2px !important;
}
.ui-dialog-title {
	font-size: 1.1em; 
	color: #94b54d;
	text-align: left;
}

/* .ui-dialog .ui-dialog-title {
	height : 40px !important;
} Deb: Keeping for now Not sure the effect of it.*/
#gbox_viewOperationalWindowTableId{
margin-top: 58px;
}
span[id^="ui-id-"] {
	height : 60px !important;
}
label {
    margin-bottom: 0px;
}
</style>
<div>
	<ul id="viewTestWindowID" class="nav nav-tabs sub-nav">
		<li class="nav-item">
			<a class="nav-link" href="#tabs_testWindow" data-toggle="tab" role="tab"><fmt:message key="label.testmanagement.opetationalTest" /></a>
		</li>
	</ul>
		<div id="content" class="tab-content">
		<div id="tabs_testWindow" class="tab-pane" role="tabpanel">
			<input id="columnChooserGrid3" value ="true" type="hidden" class="hidden" />	
				<div class="btn-bar">
					<div id="searchFilterErrorsVOF" class="error"></div>
					<div id="searchFilterMessageVOF" style="padding:20px" class="hidden"></div> 
				</div>
				
			 	<div class="form-fields">
			 	
					<label for="assessmentProgramsVOF" class="field-label" style="color: #0E76BC;font-size: 0.9em; margin: 15px 12px 5px;text-transform: uppercase;">Assessment Program:<span class="lbl-required">*</span></label>			
					<br><select id="assessmentProgramsVOF" class="bcg_select required" name="assessmentProgramsVOF" >
						<option value="">Select</option>
					</select>
								
				<div style ="float: right;">
					<button class="panel_btn"  id="searchOperTestBtnVOF">Search</button>
					<button class="panel_btn"  id="newAddTestWindowDlg">New Window</button>
				</div>
				</div>
				
				<div class ="table_wrap">
					<div class="kite-table">
						<table id="viewOperationalWindowTableId"  class="responsive"></table>
						<div id="pviewOperationalWindowTableId" class="responsive"></div>
					</div>
				</div>
				<div id ="newOperationalTestWindow" class="hidden">
					<jsp:include page="newOperationalTestWindow.jsp"></jsp:include>
				</div>
				<div id ="editOperationalTestWindow" class="hidden">
					<!-- <div id="editOperationalTestWindowViewMode" Style="margin-left: 376px;color: blue">View window</div>
					<div id="editOperationalTestWindowEditMode" hidden="hidden" class="hidden" Style="margin-left: 376px;color: blue">Edit window</div> -->
					<!-- <div id="editOperationalTestWindowCopyMode" hidden="hidden" class="hidden" Style="margin-left: 376px;color: blue"></div> -->
					<jsp:include page="editOperationalTestWindow.jsp"></jsp:include>
					<input type="text" id="hdnWindowName" class="hidden" value=""/>
				</div>
				<div id="includeExcludeDialog" style="display: none" class="form-fields">
					<label for="includeExcludeFileData" class="form-label" >File:<span class="lbl-required" >*</span></label><br/>
					<input id="includeExcludeFileData" name="includeExcludeFileData" type="file" class="hideFileUploader" />
		                 
					<div class="input-append">
		          	  	<input type="text" name="includeExcludeFileDataInput" id="includeExcludeFileDataInput" title="File" class="input-file" readOnly="readOnly"/> 
					  	<a class="add-on" tabindex="0" title="CSV" onkeypress="uploadOtwCsv(event);" onclick="uploadOtwCsv(event);"><img alt="CSV" src="images/icon-csv.png"/> </a>
		 				<p>Please use the current version of the <a id="ENR_Templatelink" href="getTemplate.htm?templateName=IncludeExcludeUpload_Template.CSV">Upload Template.</a>
		 				<input type="button" id="uploadIncludeExclude" value="Upload"/></p>
		 			</div>
					<div class="full_main">
						<div id="resultsuploadUserGridCell">
							<div id="resultsuserUploadReport" class="hidden"></div> <br/>
							<div id="resultsIncludeExcludeStudentUploadResults" class="hidden"></div>
							<div id="resultsuploadUser" class="hidden"></div>
					 		<div id="resultsuploadUserGridContainer" class="kite-table">
					 			<table class="responsive kite-table" id="resultsuploadUserGridTableId"></table>
								<div id="resultsuploadUserGridPager" style="width: auto;"></div>
					 		</div>
						</div>
					</div>	
				</div>
		</div>
	</div>
</div>

<script>
$(function(){
	<%-- navigate to the first available tab --%>
	$('#viewTestWindowID li.nav-item:first a').tab('show');
});
</script>
<!-- <script type="text/javascript" src="/AART/js/viewEditOperationalTestWindow.js"> </script> -->
<script type="text/javascript" src="/AART/js/operationalTestWindow.js"> </script>
<script type="text/javascript" src="/AART/js/newOperationalTestWindow.js"> </script>
<script type="text/javascript" src="/AART/js/editOperationalTestWindow.js"> </script>	 	 
<script type="text/javascript">
var uploadReport = '';
var assessmentProgramIdBeforeSelected = '';
var assessmentProgramNameBeforeSelected = '';
$(function(){
	
	$("#searchFilterMessageVOF").hide();
	$('#managedbyCodeSelect,#randomizedCodeSelect,#editManagedbyCodeSelect,#editrandomizedCodeSelect,#assessmentProgramsVOF').select2({
		placeholder:'Select',
		multiple: false,
		selectedList: 1,
		header: ''
	});
	
	
	$('#viewOperationalFilterForm').validate({
			ignore: "",
			rules: {
				assessmentProgramsVOF: {required: false}
		    },
		    messages:{
		        assessmentProgramsVOF: ""
		    }
	});
	$("#searchOperTestBtnVOF").prop("disabled",true);
	$('#searchOperTestBtnVOF').addClass('ui-state-disabled');
	
	$("#searchOperTestBtnVOF").off("click").on("click",function(){
	   
		$grid = $("#viewOperationalWindowTableId");
		var myStopReload = function () {
	        $grid.off("jqGridToolbarBeforeClear", myStopReload);
	        return "stop"; // stop reload
	    };
	    $grid.on("jqGridToolbarBeforeClear", myStopReload);
	    if ($grid[0].ftoolbar) {
	    	$grid[0].clearToolbar();
	    }
//	 if($('#assessmentProgramsVOF').valid()){
	   	var assessmentProgramId = $("#assessmentProgramsVOF").val();
		loadOperationalTestWindowGrid(assessmentProgramId);
		assessmentProgramIdBeforeSelected = $("#assessmentProgramsVOF").val();
		assessmentProgramNameBeforeSelected = $('#assessmentProgramsVOF').find('option:selected').text();
//	  }
	
	});
	
	
	$('#assessmentProgramsVOF').on("change",function(){
	  if($("#assessmentProgramsVOF").val() != '')
		  {
		  	$("#searchOperTestBtnVOF").prop("disabled",false);
			$('#searchOperTestBtnVOF').removeClass('ui-state-disabled');
		  }else{
		  	$("#searchOperTestBtnVOF").prop("disabled",true);
			$('#searchOperTestBtnVOF').addClass('ui-state-disabled');
			$("#viewOperationalWindowTableId").jqGrid("clearGridData", true);
			loadOperationalTestWindowGrid('');
		  }
		if($("#assessmentProgramsVOF option:selected").text()=='Dynamic Learning Maps'){
			$('.ui-icon-include-exclude').show();  
		} else {
			$('.ui-icon-include-exclude').hide();
		}
	});
	
	<security:authorize access="hasRole('PERM_OTW_VIEW')" >	
		viewOperationalInitMethod();
	</security:authorize>
	
	resetViewOperationalTestWindow();
	
	$('td[id^="view_"]').on("click",function(){
		$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
	});
});

function uploadOtwCsv(event){
		if(event.type=='keypress'){
			if(event.which !=13){
				return false;
			}
		}
		$('input[id=includeExcludeFileData]').trigger('click');
}

function resetViewOperationalTestWindow(){
	$('.ui-icon-include-exclude').hide();
	if($('#viewOperationalFilterForm')[0]!=null)
		$('#viewOperationalFilterForm')[0].reset();
	$("#viewOperationalWindowTableId").jqGrid("clearGridData", true);
	$("#searchOperTestBtnVOF").prop("disabled",true);
	$('#searchOperTestBtnVOF').addClass('ui-state-disabled');
	loadOperationalTestWindowGrid('');
 
}

function viewOperationalInitMethod(){
	populateTestWindowAssessmentProgram();
	viewOperationalTestWindowGrid();
	populateNewTestWindowAssessmentProgram();
//	populateEditTestWindowAssessmentProgram(0);
	$("#newAddTestWindowDlg").on("click",function(){
		if($('#viewmodviewOperationalWindowTableId').hasClass('ui-jqdialog')){
			$('#viewmodviewOperationalWindowTableId').remove();
		}
		openAddNewTestWindowPopUp();
	
	});
	
};

 function openAddNewTestWindowPopUp(){

	 newOperationalTestWindowInitMethod();
	$('#newOperationalTestWindow').dialog({
		autoOpen: false,
		modal: true,
		resizable: false,
		width: 1000,
		height: 700,
		title: "Operational Test Window Setup",
		create: function(event, ui) { 
    		var widget = $(this).dialog("widget");
		},
		close: function(ev, ui) {	
			hideErrorMessages();
			clearFormOnReset();
 		}
	}).dialog('open');
} 
 
function populateTestWindowAssessmentProgram(){
	
	var vowAPSelect = $('#assessmentProgramsVOF'), vowOptionText='';
	$('.messages').html('').hide();
	vowAPSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST" 
	}).done(function(vowAssessmentPrograms) {				
		if (vowAssessmentPrograms !== undefined && vowAssessmentPrograms !== null && vowAssessmentPrograms.length > 0) {
			$.each(vowAssessmentPrograms, function(i, vowAssessmentProgram) {
				vowOptionText = vowAssessmentPrograms[i].programName;
				if(vowAssessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
					vowAPSelect.append($('<option selected=\''+'selected'+'\'></option>').val(vowAssessmentProgram.id).html(vowOptionText));
				} else {
					vowAPSelect.append($('<option></option>').val(vowAssessmentProgram.id).html(vowOptionText));
				}
			});
			
			vowAPSelect.trigger('change');
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#tsSearchFilterErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
		}
		vowAPSelect.trigger('change.select2');
		
	});

}


function viewOperationalTestWindowGrid(){
	
	var grid_width = $('.kite-table').width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 980;				
	}
    var cell_width = grid_width/4;
	
	
var $gridAuto = $("#viewOperationalWindowTableId");


var cmforOperationalTestWindow = [
		
		{ name : 'otwId', index : 'otwId',label:'otwIdtestwindows', width : cell_width - 100, sortable : false, search : true, hidden : false, hidedlg : false, jsonmap: "cell.0" },
		{ name : 'windowName', index : 'windowName',label:'windowNameTestWindows', width : cell_width, hidden : false,search : true, hidedlg : true, jsonmap: "cell.2"},
		{ name : 'status', index : 'status',label:'statusTestWindows', width : cell_width - 120,sortable : true, search : false , hidedlg : false, jsonmap: "cell.25"},
		{ name : 'beginDate', index : 'beginDate',label:'beginDateTestwindoes', width : cell_width - 100,sortable : true, search : false , hidedlg : true, jsonmap: "cell.5"},
		{ name : 'endDate', index : 'endDate',label:'endDateTestWindows', width : cell_width - 100,	sortable : true, search : false , hidedlg : true, jsonmap: "cell.6"},		   						
		{ name : 'suspended', index : 'suspended',label:'suspendedTestWindows', width : cell_width - 100, sortable : false, search : false, hidden : false, hidedlg : false, jsonmap: 'cell.100',formatter: suspendedFormatter },
		{ name : 'managedBy', index : 'managedBy',label:'managedByTestWindows', width : cell_width,search : true, hidden : false, hidedlg : true, jsonmap: "cell.3" },
		{ name : 'randomization', index : 'randomization', width : cell_width,hidden: false, hidedlg : true, jsonmap: "cell.4"},
		{ name : 'createdDate', index : 'createdDate', width : cell_width - 80,search : false, sortable : true, jsonmap: "cell.7"},
	    { name : 'modifiedDate', index : 'modifiedDate', width : cell_width - 80,search : false, sortable : true, jsonmap: "cell.8"},
	    { name : 'createdBy', index : 'createdBy', width : cell_width, search : false, hidden: false , jsonmap: "cell.9"},
	    { name : 'modifiedBy', index : 'modifiedBy', width : cell_width, sortable : true, search : false, hidden : false, jsonmap: "cell.10" },
	    { name : 'testOperationalWindowId', index : 'testOperationalWindowId', width : cell_width, search : true, hidden: true, hidedlg : true, jsonmap: "cell.11"},
	    { name : 'beginTestTime', index : 'beginTestTime', width : cell_width - 80,sortable : false, search : false ,hidden : true, hidedlg : true, jsonmap: "cell.12"},
		{ name : 'endTestTime', index : 'endTestTime', width : cell_width - 80,	sortable : false, search : false ,hidden : true, hidedlg : true, jsonmap: "cell.13"},
		{ name : 'categoryCode', index : 'categoryCode', width : cell_width - 80,	sortable : false, search : false ,hidden : true, hidedlg : true, jsonmap: "cell.14"},
		{ name : 'state', index : 'state', width : cell_width - 80,sortable : true, search : true ,hidden : false, hidedlg : false, jsonmap: "cell.15"},
		{ name : 'TestEnrollmentMethod', index : 'TestEnrollmentMethod', width : cell_width - 80,sortable : true, search : false ,hidden : false, hidedlg : false, jsonmap: "cell.16"},
		{ name : 'TestEnrollment', index : 'TestEnrollment', width : cell_width - 80,sortable : false, search : false ,hidden : false, hidedlg : false, jsonmap: "cell.17"},
	 	/* { name : 'Ticketing', index : 'Ticketing', width : cell_width - 80,sortable : true, search : false ,hidden : false, hidedlg : false},
		{ name : 'TicketingOfTheDay', index : 'Ticketingoftheday', width : cell_width - 80,sortable : true, search : false ,hidden : true, hidedlg : true}, 
		 */{ name : 'dailyAccessCode', index : 'dailyAccessCode', width : cell_width - 80,sortable : true, search : false ,hidden : true, hidedlg : false, jsonmap: "cell.18"},
		 { name : 'ScoringWindowFlag', index : 'ScoringWindowFlag', width : cell_width - 80, sortable : false, search : false ,hidden : false, hidedlg : false, jsonmap: "cell.19"},
		 { name : 'ScoringWindowName', index : 'ScoringWindowName', width : cell_width - 80, search : false ,hidden : false, hidedlg : false, jsonmap: "cell.20"},		 
		 { name : 'scoringWindowStartDate', index : 'scoringWindowStartDate', width : cell_width, search : true, hidden: true, hidedlg : true, jsonmap: "cell.21"},
		 { name : 'scoringWindowEndDate', index : 'scoringWindowEndDate', width : cell_width, search : true,hidden:true, hidedlg : true, jsonmap: "cell.22"},	
		 { name : 'scoringWindowStartTime', index : 'scoringWindowStartTime', width : cell_width, search : true, hidden : true, hidedlg : true, jsonmap: "cell.23"},
		 { name : 'scoringWindowEndTime', index : 'scoringWindowEndTime', width : cell_width, search : false ,hidden : true, hidedlg : true, jsonmap: "cell.24"},
		 { name : 'suspendWindow', index : 'suspendWindow', width : cell_width, search : false ,hidden : true, hidedlg : true, jsonmap: "cell.1" },
		 { name : 'instructionPlannerWindow', index : 'instructionPlannerWindow', width : cell_width, search : false, jsonmap: "cell.26" },
		 { name : 'instructionPlannerDisplayName', index : 'instructionPlannerDisplayName', width : cell_width, search : false, jsonmap: "cell.27" }
	];

$("#viewOperationalWindowTableId").jqGrid('clearGridData');
$("#viewOperationalWindowTableId").jqGrid("GridUnload");

function suspendedFormatter(cellvalue, options, rowObject) {
    return cellvalue !=undefined ? cellvalue : '&nbsp';
}

//JQGRID
$gridAuto.scb({
	mtype: "POST",
	datatype : "local",
	width: grid_width,
	colNames : [
				'Test Window Id', 'Window Name', 'Status', 'Begin Date','End Date', 'Suspended', 'Managed By', 
				'Randomization', 'Created Date', 'Modified Date', 'Created By', 'Modified By', '','Begin Time','End Time'
				,'category Code','State','Test-Enrollment Method',' Auto-Enrollment','Authentication-Method','Scoring','Scoring Method'
				,'','','','','SuspendWindow', 'Instructional And Assessment Planner', 'Instructional And Assessment Planner Window Name'],
		colModel :cmforOperationalTestWindow,	
	rowNum : 10,
	rowList : [ 5,10, 20, 30, 40, 60, 90 ],
	height : 'auto',
	pager : '#pviewOperationalWindowTableId',
	sortname : 'windowName',
	onSortCol: function (index, iCol, sortorder) {    
	    if(index === "suspended"){
	        index = "suspendWindow";
	        return "stop";              
	    }
	},
	multiselect: false,
	pagestatesave:false,
	filterstatesave:false,
	columnChooser:true, 
	jsonReader: { repeatitems: false},
	beforeProcessing: function (data){
		var items = data.rows, n = items.length, i, item;

		for (i = 0; i < n; i++) {
	    	item = items[i].cell;
			
			if(item[25] == "Active"){
				if(item[1] == "false"){
					<security:authorize access="hasRole('PERM_OTW_EDIT')">
						item[100] = '<div style="display: inline-block;">NO &nbsp;<img style="float:right;" tabindex="0" src="images/btn-pause.png" alt="||" OnKeyPress="toggleSuspend('+i+');" onclick="toggleSuspend('+i+')"></div>';
					</security:authorize>
					<security:authorize access="!hasRole('PERM_OTW_EDIT')">
						item[100] = '<div style="display: inline-block;">NO </div>';
					</security:authorize>
				}else{
					<security:authorize access="hasRole('PERM_OTW_EDIT')">
					item[100] = '<div style="display: inline-block;">YES &nbsp;<img style="float:right;" tabindex="0" src="images/btn-play.png" alt="|>" OnKeyPress="toggleSuspend('+i+');" onclick="toggleSuspend('+i+')"></div>';
				</security:authorize>
				<security:authorize access="!hasRole('PERM_OTW_EDIT')">
					item[100] = '<div style="display: inline-block;">YES </div>';
				</security:authorize>
				}
	    	}
		}
	
	},
	loadComplete: function() {	
    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
    	 var ids = $(this).jqGrid('getDataIDs');         
         var tableid=$(this).attr('id');      
            
            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
           
             $.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
    }	
	
});
<security:authorize access="hasRole('PERM_OTW_VIEW')">
$gridAuto.jqGrid('navGrid', '#pviewOperationalWindowTableId', {edit: false, add: false, del: false, search: true, refresh: false})
.jqGrid('navButtonAdd','#pviewOperationalWindowTableId', {
	caption: "",
	title: "View Test Window",
	buttonicon: "ui-icon-search-new",
	onClickButton: function() {
		openViewTestWindowPopUp();

	}
});
</security:authorize>

<security:authorize access="hasRole('PERM_OTW_EDIT')">
$gridAuto.jqGrid('navGrid', '#pviewOperationalWindowTableId', {edit: false, add: false, del: false, search: false, refresh: false})
.jqGrid('navButtonAdd','#pviewOperationalWindowTableId', {
	caption: "",
	title: "Edit Test Window",
	buttonicon: "ui-icon-pencil",
	onClickButton: function() {
		openEditTestWindowPopUp();
	}
});
$gridAuto.jqGrid('navGrid', '#pviewOperationalWindowTableId', {edit: false, add: false, del: false, search: true, refresh: false})
.jqGrid('navButtonAdd','#pviewOperationalWindowTableId', {
	caption: "",
	title: "Copy Test Window",
	buttonicon: "ui-icon-copy-new",
	onClickButton: function() {
		openCopyTestWindowPopUp();
	}
});
$gridAuto.jqGrid('navGrid', '#pviewOperationalWindowTableId', {edit: false, add: false, del: false, search: false, refresh: false})
.jqGrid('navButtonAdd','#pviewOperationalWindowTableId', {
	title: "Include/Exclude Students",
	caption: "",
 	id: "includeExcludeIcon",
 	buttonicon: "ui-icon-include-exclude",
	onClickButton: function() {
		openIncludeExcludeStudentsPopUp();
	} 
});
</security:authorize>
//   $('div:contains("View Test Session Detail"):last').html('<div></div');
	loadOperationalTestWindowGrid('');
	$('#editStatebyTestWindowSelect').select2();
	$('#editTicketingDaySelect').select2({
		placeholder:'Select',
		multiple: false
	});
	$('#editTestEnrollmentMethod').select2({
		placeholder:'Select',
		multiple: false
	});
}

function loadOperationalTestWindowGrid(testWindowAssessmentProgramId){
	
	if($('#viewmodviewOperationalWindowTableId').hasClass('ui-jqdialog')){
		$('#viewmodviewOperationalWindowTableId').remove();
	}
	
	var $gridAuto = $("#viewOperationalWindowTableId");

	var assessmentProgramId = $("#assessmentProgramsVOF").val();

//Clear the previous error messages
	setTimeout("aart.clearMessages()", 0);
	
	 $gridAuto.jqGrid('setGridParam',{
		datatype:"json", 
		search: false,
		url : 'getTestWindowsByAssessmentPrograms.htm',
		postData : {
			filters: null,
			assessmentProgramId : assessmentProgramId
		},
	}).trigger("reloadGrid",[{page:1}]); 
 
}

function toggleSuspend(rowId){		
	if(event.type=='keypress'){
		if(event.which !=13){
			return false;
		}
	}
$('input[id=userFileData]').trigger('click');

rowId=$('#viewOperationalWindowTableId').jqGrid('getGridParam','selrow');
var otwData = $('#viewOperationalWindowTableId').getRowData(rowId);

var id = otwData.otwId;
var suspendWindow = !(otwData.suspendWindow == 'true')

var data = {id:id,suspendWindow:suspendWindow};
$.ajax({
	url: 'setOperationalTestWindowSuspendState.htm',  
	data: data,        
    dataType: 'text',
    type: "POST" 
	}).done(function(response) {
	    $("#viewOperationalWindowTableId").trigger("reloadGrid");
   	});

}


function openEditTestWindowPopUp(){
	
	$('#editOperationalTestWindowViewMode').hide();
	$('#editOperationalTestWindowCopyMode').hide();
	$('#editOperationalTestWindowEditMode').show();
	$('#editTestCollectionEditwindow').hide();
	 $('#opt_windowIdDiv').show();
	var id = null;
	id = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow');
	//var selectRowLength = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow').length;
	editTestWindowInitMethod(id);
	if(id != null){
		if (id)	{
			
			$('#editOperationalTestWindow').dialog({
				autoOpen: false,
				modal: true,
				resizable: false,
				width: 1000,
				height: 700,
				title: "Operational Test Window Setup - Edit",
				create: function(event, ui) { 
		    		var widget = $(this).dialog("widget");
				},	
				close: function(ev, ui) {	
					hideErrorMessages();
					clearFormOnReset();
		 		}
		 		
			}).dialog('open');
			var assessmentProgramName = assessmentProgramNameBeforeSelected;
			var assessmentProgramId = assessmentProgramIdBeforeSelected;
			var copyWindow=false;
				methodToBeLoadedWithPopup(id,assessmentProgramName,assessmentProgramId,copyWindow);
			}
		   var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);	
		   $("#hdnWindowId").val(rowData.otwId);
		   $('#otw_id').text(rowData.otwId);
		   $("#editOperationalTestWindowSetupsave").show();
		   $("#editOperationalTestWindowSetupcancel").show();
		   $("#changeEditMode").hide();
		   $("#changeViewMode").hide();
		   $('#editOperationalTestWindowViewSetup :input').attr('readonly',false);
		   //$('#editAssessmentProgramsAddNewTestWindowSelectText').attr('readonly',true);
		   $('#editAssessmentProgramsAddNewTestWindowSelectText').prop('disabled',true);
		   $('#OpTestWindowSetupMakereadableDiv').css({'position':'absolute','height':0,'width':0,"margin-top":"0px","z-index":999});
		   $('#overlappingSelctingoperationalTestcollection').css({'position':'absolute','height':0,'z-index':1});
	} else
		alert("Please select an Operational Test Window");
}
function openViewTestWindowPopUp(){
	
	$('#editOperationalTestWindowViewMode').show();
	$('#editOperationalTestWindowEditMode').hide();
	$('#editTestCollectionEditwindow').hide();
	$('#editOperationalTestWindowCopyMode').hide();
	 $('#opt_windowIdDiv').show();
	var id = null;
	id = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow');
	//var selectRowLength = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow').length;
	editTestWindowInitMethod(id);
	if(id != null){
		if (id)	{
			
			$('#editOperationalTestWindow').dialog({
				autoOpen: false,
				modal: true,
				resizable: false,
				width: 1000,
				height: 700,
				title: "Operational Test Window Setup - View",
				create: function(event, ui) { 
		    		var widget = $(this).dialog("widget");
				},	
				close: function(ev, ui) {				
		 		}	
		 		
			}).dialog('open');
			var assessmentProgramName = assessmentProgramNameBeforeSelected;
			var assessmentProgramId = assessmentProgramIdBeforeSelected;
			var copyWindow=false;
				methodToBeLoadedWithPopup(id,assessmentProgramName,assessmentProgramId,copyWindow);
			}
		   var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);	
		   $("#hdnWindowId").val(rowData.otwId);
		   $('#hdnWindowName').val(rowData.windowName);
		   $('#otw_id').text(rowData.otwId);
		   $("#editOperationalTestWindowSetupsave").hide();
		   $("#editOperationalTestWindowSetupcancel").hide(); 
		   $("#changeEditMode").show();
		   $("#changeViewMode").hide();
		   $('#editOperationalTestWindowViewSetup :input').attr('readonly',true);
		   $('#editAssessmentProgramsAddNewTestWindowSelectText').prop('disabled',true);
		   Width = $('#OpTestWindowSetupContentDiv').width();
	       Height = $('#OpTestWindowSetupContentDiv').height();
	       gridheight = $('#gbox_editSelectTestGridTableId').outerHeight();
		   $('#OpTestWindowSetupMakereadableDiv').css({'position':'absolute','height':Height,'width':Width,"margin-top":"0px","z-index":999});		   
		   $('#overlappingSelctingoperationalTestcollection').css({'position':'absolute','height':gridheight,'width':Width,'z-index':1});
	} else
		alert("Please select an Operational Test Window");
}
function openCopyTestWindowPopUp(){
	   
	  
	$('#editOperationalTestWindowViewMode').hide();
	$('#editOperationalTestWindowCopyMode').show();
	$('#editOperationalTestWindowEditMode').hide();
	$('#editTestCollectionEditwindow').hide();
	 $('#opt_windowIdDiv').hide();
	var id = null;
	id = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow');
	//var selectRowLength = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow').length;
	editTestWindowInitMethod(id);
	if(id != null){
		if (id)	{
			
			$('#editOperationalTestWindow').dialog({
				autoOpen: false,
				modal: true,
				resizable: false,
				width: 1000,
				height: 700,
				title: "Operational Test Window Setup",
				create: function(event, ui) { 
		    		var widget = $(this).dialog("widget");
				},	
				close: function(ev, ui) {				
		 		}	
		 		
			}).dialog('open');
			var assessmentProgramName = assessmentProgramNameBeforeSelected;
			var assessmentProgramId = assessmentProgramIdBeforeSelected;
			var copyWindow = true;
				methodToBeLoadedWithPopup(id,assessmentProgramName,assessmentProgramId,copyWindow);
			}
			var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);	
		   $('#hdnWindowName').val(rowData.windowName);
		   $("#editOperationalTestWindowSetupsave").show();
		   $("#editOperationalTestWindowSetupcancel").show();
		   $("#changeEditMode").hide();
		   $("#changeViewMode").hide();
		   $('#OpTestWindowSetupMakereadableDiv').css({'position':'absolute','height':0,'width':0,"margin-top":"0px","z-index":999});
		   $('#overlappingSelctingoperationalTestcollection').css({'position':'absolute','height':0,'z-index':1});
		   copyExistingOperationalTestWindow();
		   $('#editStatebyTestWindowSelect').val('').trigger('change.select2');
	
	} else
		alert("Please select an Operational Test Window");
   
}
function openIncludeExcludeStudentsPopUp(){
	// Initialize while opening.
	$('#resultsuserUploadReport').html('').hide();
	$('#UserInvalidFormatMessage').html('').hide();
	$("#UserARTSmessages,#UserInvalidFormatMessage").html('').hide();
	$('#resultsIncludeExcludeStudentUploadResults').html('').hide();
	$('#includeExcludeFileDataInput').val('');
	$('#includeExcludeFileData').val('');
	uploadReport = '';
    var id = $('#viewOperationalWindowTableId').jqGrid ('getGridParam','selrow');
    if(id != null){
        $("#includeExcludeDialog").dialog({
            modal: true,
            autoOpen: false,
           	title: " ",
            width: 'auto',
            height: 'auto',
            create: function(event, ui) { 
    		var widget = $(this).dialog("widget");
			},	
			close: function(ev, ui) {				
	 		}
        });
        $('#includeExcludeDialog').dialog('open');
        $('#includeExcludeDialog').dialog({autoOpen: false}).dialog('widget').find('.ui-dialog-title').html('Select the CSV file (list of students to be included<br> and/or excluded), and click upload:');
     }else{
         alert("Please select an Operational Test Window");
     }
}
</script>
