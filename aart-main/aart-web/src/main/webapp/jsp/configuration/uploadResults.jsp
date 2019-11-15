<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>
#uploadResultsTabContainer .form .form-fields{
 display: inline-block;
    margin-bottom: 0;
    margin-left: 10px;
    margin-right: 10px;
    margin-top: 0;
    padding-bottom: 12px;
    width: auto;
}

.form .form-fields .field-label {
    text-transform: none !important;
}


.bcg_select .btn{
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-attachment: scroll;
    background-clip: border-box;
    background-color: white;
    background-image: none;
    background-origin: padding-box;
    background-position-x: 0;
    background-position-y: 0;
    background-repeat: repeat;
    background-size: auto auto;
    border-bottom-color: #a7a9ac;
    border-bottom-left-radius: 3px;
    border-bottom-right-radius: 3px;
    border-bottom-style: solid;
    border-bottom-width: 1px;
    border-image-outset: 0 0 0 0;
    border-image-repeat: stretch stretch;
    border-image-slice: 100% 100% 100% 100%;
    border-image-source: none;
    border-image-width: 1 1 1 1;
    border-left-color: #a7a9ac;
    border-left-style: solid;
    border-left-width: 1px;
    border-right-color: #a7a9ac;
    border-right-style: solid;
    border-right-width: 1px;
    border-top-color: #a7a9ac;
    border-top-left-radius: 3px;
    border-top-right-radius: 3px;
    border-top-style: solid;
    border-top-width: 1px;
    color: #a7a9ac;
    cursor: pointer;
    font-size: 0.9em;
    line-height: 18px;
    margin-bottom: 0;
    margin-left: 0;
    margin-right: 0;
    margin-top: 0;
    overflow-x: hidden;
    overflow-y: hidden;
    padding-bottom: 8px;
    padding-left: 8px;
    padding-right: 8px;
    padding-top: 8px;
    position: relative;
    width: 195px !important;
  }

.input-append .add-onResult {
 -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #fbb117;
    border-bottom-color: #a7a9ac;
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 4px;
    border-bottom-style: solid;
    border-bottom-width: 1px;
    border-image-outset: 0 0 0 0;
    border-image-repeat: stretch stretch;
    border-image-slice: 100% 100% 100% 100%;
    border-image-source: none;
    border-image-width: 1 1 1 1;
    border-left-color: currentcolor;
    border-left-style: none;
    border-left-width: 0;
    border-right-color: #a7a9ac;
    border-right-style: solid;
    border-right-width: 1px;
    border-top-color: #a7a9ac;
    border-top-left-radius: 0;
    border-top-right-radius: 4px;
    border-top-style: solid;
    border-top-width: 1px;
    display: inline-block;
    font-weight: normal;
    height: 29px;
    line-height: 29px;
    margin-left: -9px;
    padding-bottom: 0;
    padding-left: 2px;
    padding-right: 9px;
   /*  padding-top: 6px; */
    text-align: center;
    text-shadow: 0 1px 0 #ffffff;
    vertical-align: top;
    width: 22px;
	padding-top: 3px !important;
}

.input-append .add-onResult{
margin-left:-18px !important;
width:34px !important;
height:36px !important;
}

.form .form-fields input{
height :36px !important;
}

.input-append .input-file{
margin-top : 0px !important;
} 

.ui-state-default, .ui-widget-content .ui-state-default
{
border:none !important;
}
.ui-widget-content a{
color:#0254eb !important;
}


</style>
<div id="uploadResultsTabContainer">
	<div>
		<form id="uploadResultsForm" class="form">
		<input type="text" id="fileTypeCateogryCode" class="hidden" />
		<input type="text" id="assessmentProgramCode" class="hidden" value="${user.currentAssessmentProgramName}" />
		
		<div class="form-fields">
			<label for="uplaodResultsAssessmentProgram" class="field-label required">Assessment Program:<span class="lbl-required">*</span></label>
			<select name="uplaodResultsAssessmentProgram" title="Assessment Program" class="bcg_select" id="uplaodResultsAssessmentProgram">
				<option value="0">Select</option>
			</select>
		</div>
		
		<div class="form-fields">
			<label for="uploadResultsState" class="field-label required">State:<span class="lbl-required">*</span></label>
				<select name="uploadResultsState" title="State" class="bcg_select" id="uploadResultsState">
					<option value="0">Select</option>
				</select>
		</div>
		
		<div class="form-fields">
			<label for="uploadResultsFileType" class="field-label required">File Type:<span class="lbl-required">*</span></label>
				<select name="uploadResultsFileType" title="File type" class="bcg_select" id="uploadResultsFileType">
				<option value="0">Select</option>
 				<%-- <option value="CLASSROOM_REPORTS_IMPORT"><fmt:message key='upload.results.classroom'/></option> --%>
				<%-- <option value="CLASSROOM_CSV_REPORTS_IMPORT"><fmt:message key='upload.results.classroom.csv'/></option> --%>
				 <option value="UPLOAD_INCIDENT_FILE_TYPE"><fmt:message key='upload.results.Incident'/></option>
				<option value="UPLOAD_ORGANIZATION_SCORE_CALC"><fmt:message key='upload.results.cpass.organization.calculation'/></option>
				<option value="UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC"><fmt:message key='upload.results.cpass.organization.pct'/></option> 
				<%-- <option value="SCHOOL_REPORTS_IMPORT"><fmt:message key='upload.results.school'/></option> --%>
				<%-- <option value="SCHOOL_CSV_REPORTS_IMPORT"><fmt:message key='upload.results.school.csv'/></option> --%>			
				<option value="STUDENT_DCPS_REPORTS_IMPORT"><fmt:message key='upload.results.student.dcps'/></option>
				<option value="STUDENT_REPORTS_IMPORT"><fmt:message key='upload.results.student'/></option>
				<option value="UPLOAD_STUDENT_SCORE_CALC"><fmt:message key='upload.results.cpass.student.calculation'/></option>
				<option value="STUDENT_SUMMARY_REPORTS_IMPORT"><fmt:message key='upload.results.student.summary'/></option>
				<option value="UPLOAD_STUDENT_PCT_ASMNT_TOPIC"><fmt:message key='upload.results.cpass.studnt.pct'/></option>
				</select>
		</div>	
		<div class="form-fields">
			<label for="uploadResultsSchoolYear" class="field-label required">School Year:<span class="lbl-required">*</span></label>
				<select name="uploadResultsSchoolYear"  title="School Year" class="bcg_select test" style="width: 192px !important;" id="uploadResultsSchoolYear">
					<option value="0">Select</option>					
				</select>
		</div>
			<div class="form-fields" id="divUploadResultsSubject">
				<label for="uploadResultsReportSubject" class="field-label ">Subject:<span
					class="lbl-required">*</span></label> <select title="Subject"
					name="uploadResultsReportSubject" class="bcg_select"
					id="uploadResultsReportSubject">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields" id="divTestingProgram">
				<label for="uploadResultsReportTestingProgram"
					class="field-label">Testing Program:<span
					class="lbl-required">*</span></label> <select title="Testing Program"
					name="uploadResultsReportTestingProgram" class="bcg_select"
					id="uploadResultsReportTestingProgram">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields" id="divUploadResultsTestingCycle">
				<label for="uploadResultsReportTestingCycle"
					class="field-label">Report Cycle:<span
					class="lbl-required">*</span></label> <select title="Report Cycle"
					name="uploadResultsReportTestingCycle" class="bcg_select"
					id="uploadResultsReportTestingCycle">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields" id="doneId">
		<label for="uploadResultsFileData" class="field-label required">File:<span class="lbl-required">*</span></label>
			<input id="uploadResultsFileData" name="uploadResultsFileData" type="file" class="hideFileUploader">
			<div class="input-append">
				<input type="text" name="uploadResultsFileDataInput" title="Upload results fiel" id="uploadResultsFileDataInput" onkeypress="uploadResultsFile(event);" onclick="uploadResultsFile(event);" class="input-file" readOnly="readOnly"/>
				<a class="add-onResult" tabindex="0" title="Upload results" onkeypress="uploadResultsFile(event);" onclick="uploadResultsFile(event);"  ><%-- <img src="${pageContext.request.contextPath}/images/icon-csv.png"/> --%></a>
			</div>
		</div>
		<div class="form-fields" id="doneIds">
			<div class="input-append">
				<button class="panel_btn btn_right" href="#" id="uploadResultsDataFile" style='float:right;'><fmt:message key='upload.button'/></button>
			</div>
		</div>
		<br/><br/>
			
		</form>
	</div>
	
	
	<br/><br/><br/>
	<div style='color: #0e76bc;margin-left: 10px;'>Submission history  </div>	
	<div class="table_wrap">
		<div id="uploadResultsReportDataTableIdContainer" class="kite-table" style="width: 1040px;">
			<table id="uploadResultsReportDataTableId" class="responsive"></table>
			<div id="puploadResultsReportDataTableId" class="responsive"></div>
		</div>
	</div>
	
	<div id="uploadResultsPopup" style="display:none; height: auto;">
			<div><span id="uploadResultsGRFPopup"></span>  </br></br></br><div>Do you wish to continue?</div> </div>
	</div>
	
	<div id="uploadResultsErrorMessage">
	</div>
	
	<div id="ConfirmationDivUploadResults">
	</div>
	
	<div id="uploadReportResultsReasonsDetails">
		<div id="invalidUploadResultsGridContainer" class="kite-table"><table class="responsive" id="invalidUploadResultsGrid"></table></div>
	</div>
		
</div>

<script type="text/javascript">

$(function(){		
	//Build the overlay
	$('#ConfirmationDivUploadResults').dialog({
		resizable: false,
		height: 150,
		width: 400,
		modal: true,
		autoOpen:false,
		title:'&nbsp;',
		buttons: {
			Ok: function() {
				$(this).dialog('close');
				
				$("#uploadResultsDataFile").prop("disabled",false);
				$('#uploadResultsDataFile').removeClass('ui-state-disabled');
		    },	    
		}
	});
	$('#divUploadResultsTestingCycle').hide();
	$('#divTestingProgram').hide();
	$('#divUploadResultsSubject').hide();
	$('#uploadResultsErrorMessage').dialog({
		resizable: false,
		height: 250,
		width: 600,
		modal: true,
		autoOpen:false,
		title:'ERROR',
		buttons: {
			Ok: function() {
				$(this).dialog('close');
		    },	    
		}
	});
	// onLoad bcgselect
	$('#uploadResultsTabContainer select.bcg_select').select2({
		placeholder: 'Select',
		multiple: false
	});
	$('#uploadResultsTabContainer .ui-multiselect').css('width', '160px');
	
	//setup file 
	$('input[id=uploadResultsFileData]').on("change",function() {
		$('#uploadResultsFileDataInput').val($('#uploadResultsFileData')[0].files[0].name);
	});
	
	var forCpass=$('#assessmentProgramCode').val()=='CPASS' ? false:true;
	//grid
	
	var colModel = [
	        		{label: 'id', name: 'id', hidden: true, hidedlg: true},
	        		{label: 'Uploaded', name: 'submissionDate', index: 'submissiondate', hidedlg: true, formatoptions: {newformat: 'm/d/Y h:i:s A'},
	        			formatter: function(cellValue, options, rowObject, action){
	        				return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	        			}
	        		},
	        		{label: 'Assessment Program', name: 'assessmentProgramName', index: 'assessmentprogramname', hidedlg: true},
	        		{label: 'State', name: 'stateName', index: 'state', hidedlg: true},
	        		{label: 'File Type', name: 'uploadType', index: 'uploadtype', hidedlg: true},
	        		{label: 'File Name', name: 'fileName', index: 'filename', hidedlg: true},
	        		{label: 'Status', name: 'status', index: 'status', width: 190, hidedlg: true},
	        		{label: 'Rejected', name: 'failedCount', index: 'failedcount', hidedlg: true, formatter: rejectedFormatter, width: 100},
	        		{label: 'Created', name: 'successCount', index: 'successcount', hidedlg: true, width: 100},
	        		{label: 'User', name: 'createdUserDisplayName', index: 'createduserdisplayname', hidedlg: true},
	        		{label: 'School Year', name: 'reportYear', index: 'reportyear', width: 70, hidedlg: true},
	        		{label: 'Subject', name: 'contentAreaName', index: 'contentAreaName',hidden: forCpass, hidedlg: forCpass, width: 70},
	        		{label: 'Testing Program', name: 'testingProgramName', index: 'testingProgramName',width: 100,hidden:forCpass, hidedlg: forCpass},
	        		{label: 'Report Cycle', name: 'reportCycle', index: 'reportCycle', width: 100, hidedlg: forCpass,hidden: forCpass},
	        	
	        		];
	        	$('#uploadResultsReportDataTableId').scb({
	        		mtype: 'POST',
	        		datatype: 'local',
	        		width: $('#uploadResultsReportDataTableIdContainer').width(),
	        		height: 'auto',
	        		filterstatesave: true,
	        		pagestatesave: true,
	        		colModel: colModel,
	        		filterToolbar: false,
	        		rowNum: 5,
	        		rowList: [5, 10, 20, 30, 40, 60, 90],
	        		columnChooser: false, 
	               	multiselect: false,
	        		footerrow : true,
	        		pager: '#puploadResultsReportDataTableId',
	        		sortname: 'submissiondate',
	        		sortorder: 'DESC',
	        		jsonReader: {
	        	        page: function (obj) {
	        	            return obj.page !== undefined ? obj.page : "0";
	        	        },
	        	        repeatitems:false,
	        	    	root: function(obj) { 
	        	    		return obj.rows;
	        	    	} 
	        	    }
	        	});
		
	// click upload 
	$('#uploadResultsDataFile').on("click",function(){
		var invalidGridData=[];
		jQuery.validator.setDefaults({
    		submitHandler: function() {		
    		},
    		errorPlacement: function(error, element) {
    			if(element.hasClass('required') || element.attr('type') == 'file') {
    				error.insertAfter(element.next());
    			}
    			else {
    	    		error.insertAfter(element);
    			}
    	    }
    	});
		var fileType = $('#uploadResultsFileType').val();
		setTimeout(function(){ $('.form-fields .error').hide('');  },3000);
		if(fileType !='UPLOAD_STUDENT_SCORE_CALC' && fileType !='UPLOAD_ORGANIZATION_SCORE_CALC'){
			 $("#uploadResultsReportSubject").removeClass("required");
			 $("#uploadResultsReportTestingProgram").removeClass("required");
			 $("#uploadResultsReportTestingCycle").removeClass("required");
		}
		if($('#uploadResultsForm').valid()){
			
		 if(fileType == 'UPLOAD_INCIDENT_FILE_TYPE'){
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new Incident file is uploaded, existing Incident files are deleted.');
		}else if (fileType == 'CLASSROOM_REPORTS_IMPORT' ){
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new Classroom file is uploaded, existing Classroom (and Classroom CSV, if applicable) reports are deleted.');
		}else if (fileType == 'CLASSROOM_CSV_REPORTS_IMPORT' ){
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new Classroom CSV file is uploaded, existing Classroom CSV reports are deleted and Classroom reports are not deleted.');
		}else if (fileType == 'SCHOOL_REPORTS_IMPORT' ){
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new School file is uploaded, existing School (and School CSV, if applicable) reports are deleted.');
		}else if (fileType == 'SCHOOL_CSV_REPORTS_IMPORT'){
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new School CSV file is uploaded, existing School CSV reports are deleted and School reports are not deleted.');
		}else if (fileType == 'STUDENT_REPORTS_IMPORT'){
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new Student file is uploaded, existing Student reports are deleted.');
		}else if (fileType == 'STUDENT_SUMMARY_REPORTS_IMPORT') {
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new Student Summary file is uploaded, existing Student Summary reports are deleted.');
		}else if (fileType == 'UPLOAD_ORGANIZATION_SCORE_CALC') {
			$('#uploadResultsGRFPopup').empty().append('Reminder: The previous data associated with this assessment program, state, file type, school year, subject, testing program and report cycle will be deleted. Proceed?.');
		}else if (fileType == 'UPLOAD_STUDENT_SCORE_CALC') {
			$('#uploadResultsGRFPopup').empty().append('Reminder: The previous data associated with this assessment program, state, file type, school year, subject, testing program and report cycle will be deleted. Proceed?.');
		}else if (fileType == 'UPLOAD_STUDENT_PCT_ASMNT_TOPIC') {
			$('#uploadResultsGRFPopup').empty().append('Reminder: The previous data associated with this assessment program, state, file type, school year, subject, testing program and report cycle will be deleted. Proceed?.');
		}else if (fileType == 'UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC') {
			$('#uploadResultsGRFPopup').empty().append('Reminder: The previous data associated with this assessment program, state, file type, school year, subject, testing program and report cycle will be deleted. Proceed?.');
		}else if(fileType == 'STUDENT_DCPS_REPORTS_IMPORT'){
			$('#uploadResultsGRFPopup').empty().append('Reminder: when a new Student DCPS file is uploaded, existing Student DCPS reports are deleted.');
		}
		
		var dialog = $('#uploadResultsPopup').dialog({
			resizable: false,
			width: 585,
			modal: true,
			autoOpen: true,
			title: '',
			create: function(event, ui){
				
				var widget = $(this).dialog("widget");				
				$(".ui-dialog-titlebar-close span", widget).removeClass('ui-button-icon-primary').removeClass('ui-button-text');
				$("#uploadResultsDataFile").prop("disabled",false);
				$('#uploadResultsDataFile').removeClass('ui-state-disabled');
			
			},
		buttons: {
		
			Yes: function(){		
			
		var grid = $('#uploadResultsReportDataTableId');
		var ids = grid.getDataIDs();
		var found = false;	
		setTimeout(function(){ $('.form-fields .error').hide('');  },3000);
		if($('#uploadResultsForm').valid()){
			var testingProgram='';
			var reportCycle='';
			var subjectId='';
			if($('#uploadResultsReportTestingProgram') != null &&  $('#uploadResultsReportTestingProgram').val()!=''){
				 testingProgram = $('#uploadResultsReportTestingProgram').val();
					
				}
			 if($('#uploadResultsReportTestingCycle') != null && $('#uploadResultsReportTestingCycle').val()!=''){
					reportCycle = $('#uploadResultsReportTestingCycle').val();
				}
			 if($('#uploadResultsReportSubject') != null && $('#uploadResultsReportSubject').val() !=''){
				 subjectId = $('#uploadResultsReportSubject').val();
				}
			var data = {				
				assessmentProgramId: $('#uplaodResultsAssessmentProgram').val(),
				fileTypeId: $('#fileTypeCateogryCode').val(),
				stateId: $('#uploadResultsState').val(),
				contentAreaId:0,
				categoryCode: $('#fileTypeCateogryCode').val(),
				reportYear: $('#uploadResultsSchoolYear').val(),
				testingProgramId: testingProgram,
				reportingCycleId: reportCycle,
				contentAreaId:subjectId
			};
			checkForInProgress(data);
		}	
		
		$("#uploadResultsDataFile").prop("disabled",true);
		$('#uploadResultsDataFile').addClass('ui-state-disabled');
		// close 
		$(this).dialog('close');				
				
		},
        No : function(){			
			$(this).dialog('close');
			$("#uploadResultsDataFile").prop("disabled",false);
			$('#uploadResultsDataFile').removeClass('ui-state-disabled');
		}
		},
		});
		
		
		}
	});
	
	 loadUploadGridTable(true);	
	 loadUploadResultsAssessmentPrograms();
	 loadAssessmentProgramOnChange();
	 //loadCurrentSchoolYear();	
$('#uploadResultsReportTestingProgram').on("change",function(){
			var testingProgramId = $(this).val();
			 
			var select = $('#uploadResultsReportTestingCycle');
			select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
			select.trigger('change.select2');			
			
			if($('#uploadResultsFileType').find('option:selected').val() !== 'UPLOAD_ORGANIZATION_SCORE_CALC'
					&& $('#uploadResultsFileType').find('option:selected').val() !== 'UPLOAD_STUDENT_SCORE_CALC'
						&& $('#uploadResultsFileType').find('option:selected').val() !== 'UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC'
							&& $('#uploadResultsFileType').find('option:selected').val() !== 'UPLOAD_STUDENT_PCT_ASMNT_TOPIC'){
				$("#uploadResultsReportTestingCycle").find('option').filter(function(){return $(this).val() > 0}).remove().end();
				$("#uploadResultsReportTestingCycle").trigger('change.select2');
				
			}else {
			
				loadUploadResultsTestingCycles($('#uplaodResultsAssessmentProgram').val(), testingProgramId);
			}			
			
			$('#uploadResultsFileData').val('');
			$('#uploadResultsFileDataInput').val('');
		});
	
});

jQuery.validator.setDefaults({
	submitHandler: function() {		
	},
	errorPlacement: function(error, element) {
		var div = element.parents('.form-fields');
		error.appendTo(div);
	}
});
jQuery.validator.addMethod('notZero', function(value, element){
	return value != 0;
});
$('#uploadResultsForm').validate({
	ignore: '',
	rules: {
		uploadResultsFileType: 'required notZero',
		uplaodResultsAssessmentProgram: 'required notZero',
		uploadResultsState: 'required notZero',
		uploadResultsSchoolYear: 'required notZero',
		uploadResultsFileData: {
			required: true,
			extension: 'xlsx,CSV,xls'
		}
	},
	messages: {
		uploadResultsFileType: 'This field is required.',
		uplaodResultsAssessmentProgram: 'This field is required.',
		uploadResultsState: 'This field is required.',
		uploadResultsSchoolYear: 'This field is required.',
		uploadResultsFileData: 'A CSV/EXCEL file is required.'
	}
});


function loadAssessmentProgramOnChange(){
	
	
 $('#uplaodResultsAssessmentProgram').on("change",function(){
	$('#uploadResultsState').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	 var assessmentProgramId = $('#uplaodResultsAssessmentProgram').val();
	 if(assessmentProgramId == '' || assessmentProgramId == null){
		 return false;
	 }
	 var select = $('#uploadResultsState');
		//getMultipleStatesOrgsForUser.htm		
	  $.ajax({
	        url: 'getStatesOrgsForUploadResults.htm',
	        data: {assessmentProgramId:assessmentProgramId },
	        dataType: 'json',
	        type: "GET" 
		}).done(function(states) {
        	populateSelectProg(select, states, 'id', 'organizationName');
        	$('#uploadResultsState').trigger('change.select2');	
        });
	  checkTestingProgrmaRequired(assessmentProgramId);
      });

}
function checkTestingProgrmaRequired(assessmentProgramId){
	if($('#assessmentProgramCode').val()=='CPASS'
		&& ($('#uploadResultsFileType').find('option:selected').val() === 'UPLOAD_ORGANIZATION_SCORE_CALC'
			|| $('#uploadResultsFileType').find('option:selected').val() === 'UPLOAD_STUDENT_SCORE_CALC'
				|| $('#uploadResultsFileType').find('option:selected').val() === 'UPLOAD_ORGANIZATION_PCT_ASMNT_TOPIC'
					|| $('#uploadResultsFileType').find('option:selected').val() === 'UPLOAD_STUDENT_PCT_ASMNT_TOPIC')){
		loadUploadResultsTestingPrograms(assessmentProgramId);
		 loadUploadResultsSubjects(assessmentProgramId);
		 $("#uploadResultsReportSubject").addClass("required");
		 $('#divUploadResultsSubject').show();
		$("#uploadResultsReportTestingProgram").addClass("required");
		$('#divTestingProgram').show();
		$("#uploadResultsReportTestingCycle").addClass("required");
		$('#divUploadResultsTestingCycle').show();
}else{
	$('#uploadResultsReportSubject').val('').trigger('change.select2');
	$('#uploadResultsReportTestingCycle').val('').trigger('change.select2');	
	$('#uploadResultsReportTestingProgram').val('').trigger('change.select2');
	 $("#uploadResultsReportSubject").removeClass("required");
	 $('#divUploadResultsSubject').hide();
	
	$("#uploadResultsReportTestingProgram").removeClass("required");
	$('#divTestingProgram').hide();
	$("#uploadResultsReportTestingCycle").removeClass("required");
$('#divUploadResultsTestingCycle').hide();
}
}
function resetUploadResults(){
	$('#uplaodResultsAssessmentProgram').val('').trigger('change.select2');
	$('#uploadResultsState').val('').trigger('change.select2');	
	$('#uploadResultsFileType').val('').trigger('change.select2');
	$('#uploadResultsSchoolYear').val('').trigger('change.select2');
	$('#uploadResultsFileDataInput').val('');
	
}

function rejectedFormatter(cellValue, options, rowObject){
	if(cellValue == null){
		return "";
	}else{
		if(rowObject.reasons.length > 0){
			return "<a href='javascript:showInvalidDetailsResults(" + rowObject.id + ")' title='Show invalid details'>"+cellValue+"</a>";
		}
		return cellValue;
	}
}

function loadUploadGridTable(resetPageNumber){
	var $grid = $('#uploadResultsReportDataTableId');
	var pData = $grid.getGridParam('postData');
	if (!pData.filters){
		pData.filters = '';
	}
	var opts = [];
	if (resetPageNumber){
		opts = [{page: 1}];
	}
	 $grid.jqGrid('setGridParam',{
		datatype: 'json',
		url: 'getReportResultsUploads.htm',
		search: false,
		postData: pData
	}).trigger('reloadGrid', opts);
}

function loadCurrentSchoolYear(state){
	
	var select = $('#uploadResultsSchoolYear');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	// goes to ManageTestSessionController
	if(state!=0){
	$.ajax({
		url: 'getCurrentSchoolYearForUploadResults.htm',
		dataType: 'json',
		data: {orgId :state },
		type: "GET"		
	}).done(function(currentYear){			
		var obj = currentYear;
    	for (var key in obj) {
    	   $('#uploadResultsSchoolYear').append($('<option></option>').attr("value",obj[key]).text(obj[key]));
    	 } 
    	$('#uploadResultsSchoolYear').trigger('change.select2');	

	});
	} else {
		$('#uploadResultsSchoolYear').val('').trigger('change.select2');
	}
	
}

$('#uploadResultsState').on("change",function(){
	var state = $('#uploadResultsState').val();
	var fileType = $('#uploadResultsFileType').val();	
	getFileTypeCategory(state,fileType);
	loadCurrentSchoolYear(state);	
	
});

$('#uploadResultsFileType').on("change",function(){
	var state = $('#uploadResultsState').val();
	var fileType = $('#uploadResultsFileType').val();
	 var assessmentProgramId = $('#uplaodResultsAssessmentProgram').val();
	getFileTypeCategory(state,fileType);
	checkTestingProgrmaRequired(assessmentProgramId);
	$('#uploadResultsSchoolYear').val('').trigger('change.select2');
	$('#uploadResultsReportSubject').val('').trigger('change.select2');
	$('#uploadResultsReportTestingProgram').val('').trigger('change.select2');
	$('#uploadResultsReportTestingCycle').val('').trigger('change.select2');
	$('#uploadResultsFileData').val('');
	$('#uploadResultsFileDataInput').val('');
});

$('#uploadResultsSchoolYear').on("change",function(){
	$('#uploadResultsReportSubject').val('').trigger('change.select2');
	$('#uploadResultsReportTestingProgram').val('').trigger('change.select2');
	$('#uploadResultsReportTestingCycle').val('').trigger('change.select2');
	$('#uploadResultsFileData').val('');
	$('#uploadResultsFileDataInput').val('');
});
		
$('#uploadResultsReportSubject').on("change",function(){
	$('#uploadResultsReportTestingProgram').val('').trigger('change.select2');
	$('#uploadResultsReportTestingCycle').val('').trigger('change.select2');
	$('#uploadResultsFileData').val('');
	$('#uploadResultsFileDataInput').val('');
});

$('#uploadResultsReportTestingCycle').on("change",function(){
	$('#uploadResultsFileData').val('');
	$('#uploadResultsFileDataInput').val('');
});


function getFileTypeCategory(state,filetype){
	var stateId=state;
	if(state == 0 || state == ' ' || filetype == ' ' || filetype == 0 ){
		$('#fileTypeCateogryCode').val(filetype);
	return '';
}else {
	if(filetype=='UPLOAD_INCIDENT_FILE_TYPE'){
		$('#fileTypeCateogryCode').val(filetype);
	}else{

		
	$.ajax({		
		url: 'getStateCode.htm',
		dataType: 'text',
		data : {
			stateId :stateId
			},
		type: "GET"
	}).done(function(stateCode){
		if(stateCode == 'KS' && filetype=='UPLOAD_SC_CODE_FILE_TYPE'){
			$('#fileTypeCateogryCode').val('UPLOAD_SC_CODE_KS_FILE_TYPE');				
		}else if(stateCode == 'NY' && filetype=='UPLOAD_GRF_FILE_TYPE'){
			$('#fileTypeCateogryCode').val('UPLOAD_GRF_NY_FILE_TYPE');
		}else if(stateCode == 'IA' && filetype=='UPLOAD_GRF_FILE_TYPE'){
			$('#fileTypeCateogryCode').val('UPLOAD_GRF_IA_FILE_TYPE');
		}else{
			$('#fileTypeCateogryCode').val(filetype);
		}
	});
	}
}
}
function loadUploadResultsAssessmentPrograms(){
	var select = $('#uplaodResultsAssessmentProgram');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	// goes to ManageTestSessionController
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"		
	}).done(function(assessmentPrograms){
		var populated = populateSelectProg(select, assessmentPrograms, 'id', 'programName');
		if (!populated){
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#uploadFilterErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
			
			// global from configuration
			clearInterval(summativeGridRefreshInterval);
			summativeGridRefreshInterval = 'no_APs_result';
		}
	});
}

function populateSelectProg($select, data, idProp, textProp){
	if (data !== undefined && data !== null && data.length > 0){
		for (var x = 0; x < data.length; x++){
			$select.append($('<option></option>').val(data[x][idProp]).text(data[x][textProp]));
		}
		if (data.length == 1) {
			$select.find('option:first').prop('selected',false).next('option').prop('selected', 'selected');
			$select.trigger('change');
			
		}
		
		$select.trigger('change.select2');
		return true;
	}
	return false;
}

function loadUploadResultGrid(resetPageNumber){
	var $grid = $('#uploadResultsReportDataTableId');
	var pData = $grid.getGridParam('postData');
	if (!pData.filters){
		pData.filters = '';
	}
	var opts = [];
	if (resetPageNumber){
		opts = [{page: 1}];
	}
	 $grid.jqGrid('setGridParam',{
		datatype: 'json',
		url: 'getReportResultsUploads.htm',
		search: false,
		postData: pData
	}).trigger('reloadGrid', opts);
}

function checkForInProgress(data){
	
	$.ajax({
		url: 'checkForInProgressUpload.htm',
		data: data,
		dataType: 'json',
		type: 'POST'
	}).done(function(count){
		if (count == 0){
			uploadResults(data);
		} else {
			$('#ConfirmationDivUploadResults')
			.html('Already same file type is on pending/inprogress.')
			.dialog('open');
		}
	});
}

function uploadResults(params){
	var fd = new FormData();
	var filedata = $('#uploadResultsFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];
	fd.append('uploadFile', file);	
	fd.append('assessmentProgramId', params.assessmentProgramId);
	fd.append('contentAreaId', params.contentAreaId);
	 fd.append('categoryCode',params.categoryCode);
	 fd.append('reportYear', params.reportYear);
	 fd.append('stateId', params.stateId);
	 fd.append('testingProgramId',params.testingProgramId);
	 fd.append('reportingCycleId',params.reportingCycleId);
		
	$.ajax({
		url: 'uploadFileData.htm',
		data: fd,
		dataType: 'json',
		processData: false,
		contentType: false,
		type: 'POST'		
	}).done(function(data){
		if (data.errorMessage != null){
			$('#uploadResultsErrorMessage')
			.html(data.errorMessage)
			.dialog('open');
		}
		loadUploadResultGrid(true);
		
		$("#uploadResultsDataFile").prop("disabled",false);
		$('#uploadResultsDataFile').removeClass('ui-state-disabled');
	});
}

$('#uploadReportResultsReasonsDetails').dialog({
	resizable: false,
	height: 700,
	width: 1200,
	modal: true,
	autoOpen:false,
	buttons: {
	    Close: function() {
	    	 $(this).dialog('close');
	    }			    
	}
});


function showInvalidDetailsResults(uploadId) {
	$("#invalidUploadResultsGrid").jqGrid("GridUnload");
	$("#invalidUploadResultsGrid").jqGrid({
		datatype: "json",
		mtype: "POST",
		url:'getUploadInvalidReasons.htm',
		height: 500,
		width: 'auto',
		altclass: 'altrow',
		shrinkToFit: false,
	   	colNames:['Line','Field Name', 'Reasons For Not Valid'],
	   	colModel:[
	   		{name:'line' ,align:"left",width:50, height: 60},
	   		{name:'fieldName',width:300, height: 60},
	   		{name:'reason',align:"left",width:800, height: 60}
	   	],
	   	viewrecords: true,
	    loadonce: true,
	    altRows : true,
	   	hoverrows : true,
	    grouping:true,
	    rowNum:99999, 
	    hidegrid: false,
	   	groupingView : {
	   		groupField : ['line'],
	   		groupDataSorted : [true],
	   		groupColumnShow : [true]
	   	}, 
	   	beforeRequest: function () {
			$(this).setGridParam({postData: {uploadId : uploadId}});

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
	    caption: "Upload Report - Invalid Record Details "
	});
	$('#uploadReportResultsReasonsDetails').dialog('open');	
}
function loadUploadResultsTestingPrograms(assessmentProgramId){
	var select = $('#uploadResultsReportTestingProgram');
	
	
	if (assessmentProgramId){
		return $.ajax({
			url: 'getTestingProgramsForReportingByAssessmentProgram.htm',
			data: {assessmentProgramId: assessmentProgramId},
			dataType: 'json',
			type: 'POST'			
		}).done(function(testingprograms){
			if (testingprograms !== undefined && testingprograms !== null && testingprograms.length > 0) {
				$('#divTestingProgram').show();
			select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
			populateSelectProg(select, testingprograms, 'id', 'programName');}
		});
	}
}

function loadUploadResultsTestingCycles(assessmentProgramId, testingProgramId){	
	
	if (assessmentProgramId){
	
		return $.ajax({
			url: 'getTestingCyclesForReportingByAssessmentProgram.htm',
			data: {assessmentProgramId: assessmentProgramId,
				testingProgramId: testingProgramId},
			dataType: 'json',
			type: 'POST'			
		}).done(function(testingcycles){
			if (testingcycles !== undefined && testingcycles !== null && testingcycles.length > 0) {
				$('#divUploadResultsTestingCycle').show();
				var select = $('#uploadResultsReportTestingCycle');
				select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
				populateSelectProg(select, testingcycles, 'id', 'testingCycleName');
				select.trigger('change.select2');
				$("#uploadResultsReportTestingCycle").addClass("required");
			}else{					
				//$("#uploadResultsReportTestingCycle").removeClass("required");
			}			
		});
	}
	
}
function loadUploadResultsSubjects(assessmentProgramId){
	var select = $('#uploadResultsReportSubject');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	if (assessmentProgramId){
		// goes to SetupTestSessionController
		return $.ajax({
			url: 'getContentAreasByAssessmentProgram.htm',
			data: {assessmentProgramId: assessmentProgramId},
			dataType: 'json',
			type: 'POST'			
		}).done(function(subjects){
			populateSelectProg(select, subjects, 'id', 'name');
		});
	}
}
function uploadResultsFile(event){
	 if(event.type=='keypress'){
		  if(event.which !=13){
		   return false;
		  }
		 }
	 $('input[id=uploadResultsFileData]').click();
}

</script>
