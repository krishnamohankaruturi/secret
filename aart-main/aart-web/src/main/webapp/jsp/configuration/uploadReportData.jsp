<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>
.input-append .add-on{
height:36px !important;
margin-top: 0px !important;
}
.ui-widget-header{
border:none !important;
background:none !important;
}
#summativeReportFileDataInput{
width:202px !important;
}
.ui-widget-overlay{
opacity:0 !important;
}

</style>
<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')">

<div id="summativeReportUploadTabContainer">
	<div>
		<div id="ARTSmessages">
			<div id="uploadFilterErrors" class="error"></div>
		</div>
		
		<div>
			<form id="summativeReportFileUploadForm" class="form">
			<input type="text" id="summativeAssessmentProgramCode" class="hidden" value="${user.currentAssessmentProgramName}" />
				<div class="form-fields">
					<label for="summativeReportFileType" class="field-label required">File Type:<span class="lbl-required">*</span></label>
					<select name="summativeReportFileType" title="File Type" class="bcg_select" id="summativeReportFileType">
						<option value="0">Select</option>
					</select>
				</div>
				<div class="form-fields">
					<label for="summativeReportAssessmentProgram" class="field-label required">Assessment Program:<span class="lbl-required">*</span></label>
					<select name="summativeReportAssessmentProgram" title="Assessment Program" class="bcg_select" id="summativeReportAssessmentProgram">
						<option value="0">Select</option>
					</select>
				</div>
				<div class="form-fields">
					<label for="summativeReportTestingProgram" class="field-label required">Testing Program:<span class="lbl-required">*</span></label>
					<select name="summativeReportTestingProgram" title="Testing Program" class="bcg_select" id="summativeReportTestingProgram">
						<option value="0">Select</option>
					</select>
				</div>
				<div class="form-fields" id="divTestingCycle">
					<label for="summativeReportTestingCycle" class="field-label required">Report Cycle:<span class="lbl-required">*</span></label>
					<select name="summativeReportTestingCycle" title="Report Cycle" class="bcg_select" id="summativeReportTestingCycle">
						<option value="0">Select</option>
					</select>					
				</div><br/>
				<div class="form-fields">
					<label for="summativeReportSubject" class="field-label required">Subject:<span class="lbl-required">*</span></label>
					<select name="summativeReportSubject" title="Subject" class="bcg_select" id="summativeReportSubject">
						<option value="0">Select</option>
					</select>
				</div>
				<div class="form-fields">
					<label for="summativeReportFileData" class="field-label required">File:<span class="lbl-required">*</span></label>
					<input id="summativeReportFileData" name="summativeReportFileData" type="file" class="hideFileUploader">
					<div class="input-append">
						<input type="text" name="summativeReportFileDataInput" title="Upload metedata file" id="summativeReportFileDataInput" class="input-file" readOnly="readOnly"/>
						<a class="add-on" tabindex="0" onkeypress="uploadMetaData(event);" onclick="uploadMetaData(event);" title="Upload metedata"><img alt="CSV" src="${pageContext.request.contextPath}/images/icon-csv.png"/></a>
					</div>
				</div>
				<div class="form-fields"> 
					<div class="input-append">
						<a class="panel_btn btn_right" href="#" id="uploadSummativeReportFile"><fmt:message key='upload.button'/></a>
					</div>
				</div>
				<br/><br/>
				
			</form>
		</div>
		<br/><br/>
		
		<div class="table_wrap">
			<div id="uploadSummativeReportDataTableIdContainer" class="kite-table" style="width: 1035px;">
				<table id="uploadSummativeReportDataTableId" class="responsive"></table>
				<div id="puploadSummativeReportDataTableId" class="responsive"></div>
			</div>
		</div>
	</div>
	<div id="uploadReportReasonsDetails">
		<div id="invalidGridContainer" class="kite-table"><table class="responsive" id="invalidUploadrGrid"></table></div>
	</div>
	<div id="reportUploadErrorMessage">
	</div>
	<div id="ConfirmationDiv"></div>
</div>

<script type="text/javascript">

$(function(){
    var invalidGridData=[];
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
	
	$('#divTestingCycle').hide();
	
	$('#uploadReportReasonsDetails').dialog({
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
	
	$('#reportUploadErrorMessage').dialog({
		resizable: false,
		height: 200,
		width: 400,
		modal: true,
		autoOpen:false,
		title:'ERROR',
		buttons: {
			Ok: function() {
				$(this).dialog('close');
		    },	    
		}
	});
	
	$('#summativeReportUploadTabContainer select.bcg_select').select2({
		placeholder: 'Select',
		multiple: false
	});
	$('#summativeReportUploadTabContainer .ui-multiselect').css('width', '160px');
	
	$('#summativeReportAssessmentProgram').on("change",function(){
		var assessmentProgramId = $(this).val();
		if (assessmentProgramId != 0){
			loadTestingPrograms(assessmentProgramId);			
			loadSubjects(assessmentProgramId);
		}
	});
	
	$('#summativeReportFileType').on("change",function(){
		$('#summativeReportTestingProgram').val(0);
		$('#summativeReportTestingProgram').trigger('change.select2');
	});
	
	$('#summativeReportTestingProgram').on("change",function(){
		var testingProgramId = $(this).val();
		if (testingProgramId != 0){
			loadTestingCycles($('#summativeReportAssessmentProgram').val(), testingProgramId);
		}else{
			$("#summativeReportTestingCycle").find('option').filter(function(){return $(this).val() > 0}).remove().end();
			$("#summativeReportTestingCycle").trigger('change.select2');
		} 
		if($('#summativeReportTestingProgram').find('option:selected').text() === 'Summative'){
			$("#summativeReportTestingCycle").rules("add", {
			    required: false,
			    messages: {
			    	required: ''
			    }
			});
		}else if($('#summativeReportTestingProgram').find('option:selected').text() === 'Interim'){
			$("#summativeReportTestingCycle").rules("add", {
			    required: true,
			    messages: {
			    	required: 'This field is required.'
			    }
			    
			});
		}
	});
	
	$('input[id=summativeReportFileData]').on("change",function() {
		$('#summativeReportFileDataInput').val($('#summativeReportFileData')[0].files[0].name);
	});
	
	$('#uploadSummativeReportFile').on("click",function(){
		var grid = $('#uploadSummativeReportDataTableId');
		var ids = grid.getDataIDs();
		var found = false;
		var reportCycleId;
		if($('#summativeReportTestingCycle') != null){
			reportCycleId = $('#summativeReportTestingCycle').val();
		}
		
		if($('#summativeReportFileType option:selected').attr('data-code') == 'ASSESSMENT_TOPICS' ||($('#summativeReportTestingProgram').find('option:selected').text() === 'Summative' && $('#summativeAssessmentProgramCode').val()!=='CPASS')){
			$("#summativeReportTestingCycle").rules("add", {
			    required: false,
			    notZero: false,
			    messages: {
			    	required: '',
			    	notZero: ''
			    }
			});
		}else if($('#summativeReportTestingProgram').find('option:selected').text() === 'Interim' ||($('#summativeReportTestingProgram').find('option:selected').text() === 'Summative' && $('#summativeAssessmentProgramCode').val()==='CPASS')){
			if($('#summativeReportTestingCycle').val() > 0){
				$("#summativeReportTestingCycle").rules("add", {
				    required: false,
				    notZero: false,
				    messages: {
				    	required: '',
				    	notZero: ''
				    }
				    
				});
			}else{
				$("#summativeReportTestingCycle").rules("add", {
				    required: true,
				    notZero : true,
				    messages: {
				    	required: 'This field is required.',
				    	notZero: 'This field is required.'
				    }
				    
				});
			}
			
		}
		if($('#summativeReportFileUploadForm').valid()){
			var data = {
				fileTypeId: $('#summativeReportFileType').val(),
				assessmentProgramId: $('#summativeReportAssessmentProgram').val(),
				contentAreaId: $('#summativeReportSubject').val(),
				testingProgramId: $('#summativeReportTestingProgram').val(),
				testingCycleId: reportCycleId
			};
			checkForDuplicates(data);
		}
	});
	
	var colModel = [
		{label: 'id', name: 'id', hidden: true, hidedlg: true},
		{label: 'Uploaded', name: 'submissionDate', index: 'submissiondate', hidedlg: true, formatoptions: {newformat: 'm/d/Y h:i:s A'},
			formatter: function(cellValue, options, rowObject, action){
				return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
			}
		},
		{label: 'Assessment Program', name: 'assessmentProgramName', index: 'assessmentprogramname', hidedlg: true},
		{label: 'Subject', name: 'contentAreaName', index: 'contentareaname', hidedlg: true},
		{label: 'File Type', name: 'uploadType', index: 'uploadtype', hidedlg: true},
		{label: 'File Name', name: 'fileName', index: 'filename', hidedlg: true},
		{label: 'Status', name: 'status', index: 'status', width: 90, hidedlg: true},
		{label: 'Rejected', name: 'failedCount', index: 'failedcount', hidedlg: true, formatter: rejectedFormatter, width: 80},
		{label: 'Created', name: 'successCount', index: 'successcount', hidedlg: true, width: 70},
		{label: 'User', name: 'createdUserDisplayName', index: 'createduserdisplayname', hidedlg: true},
		{label: 'School Year', name: 'schoolYear', index: 'schoolyear', width: 70, hidedlg: true},
		{label: 'Testing Program', name: 'testingProgramName', index: 'testingprogramname', hidedlg: true},
		{label: 'Report Cycle', name: 'reportCycle', index: 'reportcycle', width: 70, hidedlg: true},
	];
	
	$('#uploadSummativeReportDataTableId').scb({
		mtype: 'POST',
		datatype: 'local',
		width: $('#uploadSummativeReportDataTableIdContainer').width(),
		height: 'auto',
		filterstatesave: true,
		pagestatesave: true,
		colModel: colModel,
		filterToolbar: false,
		rowNum: 10,
		rowList: [5, 10, 20, 30, 40, 60, 90],
		columnChooser: false, 
       	multiselect: false,
		footerrow : true,
		pager: '#puploadSummativeReportDataTableId',
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
	
	$('#summativeReportFileUploadForm').validate({
		ignore: '',
		rules: {
			summativeReportFileType: 'required notZero',
			summativeReportAssessmentProgram: 'required notZero',
			summativeReportSubject: 'required notZero',
			summativeReportTestingProgram: 'required notZero',
			summativeReportTestingCycle: 'required',
			summativeReportFileData: {
				required: true,
				extension: 'csv'
			}
		},
		messages: {
			summativeReportFileType: 'This field is required.',
			summativeReportAssessmentProgram: 'This field is required.',
			summativeReportTestingProgram: 'This field is required',
			summativeReportSubject: 'This field is required.',
			summativeReportFileData: 'A CSV file is required.',
			summativeReportTestingCycle: 'This field is required'
		}
	});
	
	$('#ConfirmationDiv').dialog({
		resizable: false,
		height: 200,
		width: 400,
		modal: true,
		autoOpen:false,
		title:' ',
		buttons: {
			Ok: function() {
				var data = {
					fileTypeId: $('#summativeReportFileType').val(),
					assessmentProgramId: $('#summativeReportAssessmentProgram').val(),
					contentAreaId: $('#summativeReportSubject').val(),
					testingProgramId: $('#summativeReportTestingProgram').val(),
				};
				upload(data);
				$(this).dialog('close');
			},
			Cancel: function() {
				 $(this).dialog('close');
			}
		}
	});
	loadUploadFileTypes();
	loadAssessmentPrograms();
});

function rejectedFormatter(cellValue, options, rowObject){
	if(cellValue == null){
		return "";
	}else{
		if(rowObject.reasons.length > 0){
			return "<a href='javascript:showInvalidDetails(" + rowObject.id + ")' title='Show invalid details'>"+cellValue+"</a>";
		}
		return cellValue;
	}
}

function populateSelect($select, data, idProp, textProp){
	if (data !== undefined && data !== null && data.length > 0){
		for (var x = 0; x < data.length; x++){
			$select.append($('<option></option>').val(data[x][idProp]).text(data[x][textProp]));
		}
		$select.trigger('change.select2');
		return true;
	}
	return false;
}

function uploadMetaData(event){
	 if(event.type=='keypress'){
		  if(event.which !=13){
		   return false;
		  }
		 }
	$('input[id=summativeReportFileData]').click();
}


function loadUploadFileTypes(){
	var select = $('#summativeReportFileType');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	return $.ajax({
		url: 'getUploadFileTypes.htm',
		data: {},
		dataType: 'json',
		type: 'GET'	
	}).done(function(fileTypes){
		for (var x = 0; x < fileTypes.length; x++){
			select.append($('<option data-code="'+fileTypes[x].categoryCode+'" value="'+fileTypes[x].id+'" >'+fileTypes[x].categoryName+'</option>'));
		}
		select.trigger('change.select2');			
});
}

function loadAssessmentPrograms(){
	var select = $('#summativeReportAssessmentProgram');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	// goes to ManageTestSessionController  getAssessmentProgramsOfUserOrganization.htm
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"		
	}).done(function(assessmentPrograms){
		var populated = populateSelect(select, assessmentPrograms, 'id', 'programName');
		if (!populated){
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#uploadFilterErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
			
			// global from configuration
			clearInterval(summativeGridRefreshInterval);
			summativeGridRefreshInterval = 'no_APs';
		}
	});
}

function loadSubjects(assessmentProgramId){
	var select = $('#summativeReportSubject');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	if (assessmentProgramId){
		// goes to SetupTestSessionController
		return $.ajax({
			url: 'getContentAreasByAssessmentProgram.htm',
			data: {assessmentProgramId: assessmentProgramId},
			dataType: 'json',
			type: 'POST'
		}).done(function(subjects){
			populateSelect(select, subjects, 'id', 'name');
		});
	}
}

function loadTestingPrograms(assessmentProgramId){
	var select = $('#summativeReportTestingProgram');
	select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	if (assessmentProgramId){
		return $.ajax({
			url: 'getTestingProgramsForReportingByAssessmentProgram.htm',
			data: {assessmentProgramId: assessmentProgramId},
			dataType: 'json',
			type: 'POST'
		}).done(function(testingprograms){
			populateSelect(select, testingprograms, 'id', 'programName');
		});
	}
}

function loadTestingCycles(assessmentProgramId, testingProgramId){	
	
	if (assessmentProgramId){
	
		return $.ajax({
			url: 'getTestingCyclesForReportingByAssessmentProgram.htm',
			data: {assessmentProgramId: assessmentProgramId,
				testingProgramId: testingProgramId},
			dataType: 'json',
			type: 'POST'
		}).done(function(testingcycles){
			
			if (testingcycles !== undefined && testingcycles !== null && testingcycles.length > 0) {
				$('#divTestingCycle').show();
				$("#summativeReportTestingCycle").addClass("required");
				var select = $('#summativeReportTestingCycle');
				select.find('option').filter(function(){return $(this).val() > 0}).remove().end();
				populateSelect(select, testingcycles, 'id', 'testingCycleName');
				select.trigger('change.select2');
			}else{
				$("#summativeReportTestingCycle").removeClass("required");
				$('#divTestingCycle').hide();
			}
			
			if($('#summativeReportFileType option:selected').attr('data-code') == 'ASSESSMENT_TOPICS'){
				$("#summativeReportTestingCycle").removeClass("required");
				$('#divTestingCycle').hide();
			}
			
		});
	}
}

function loadUploadGrid(resetPageNumber){
	var $grid = $('#uploadSummativeReportDataTableId');
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
		url: 'getSummativeReportUploads.htm',
		search: false,
		postData: pData
	}).trigger('reloadGrid', opts);
}

function checkForDuplicates(data){
	$.ajax({
		url: 'checkDuplicateUpload.htm',
		data: data,
		dataType: 'json',
		type: 'POST'		
	}).done(function(count){
		if (count == 0){
			upload(data);
		} else {
			var confirmationMessage = '';
			if($('#summativeReportTestingProgram').find('option:selected').text() === 'Summative'){
				confirmationMessage = 'The previous data associated with this assessment program, testing program, subject and file type will be deleted. Proceed?';
			}else{
				confirmationMessage = 'The previous data associated with this assessment program, testing program, report cycle, subject and file type will be deleted. Proceed?';
			}
			$('#ConfirmationDiv')
			.html(confirmationMessage)
			.dialog('open');
		}
	});
}

function upload(params){
	var fd = new FormData();
	var filedata = $('#summativeReportFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];
	fd.append('uploadFile', file);
	fd.append('fileTypeId', params.fileTypeId);
	fd.append('assessmentProgramId', params.assessmentProgramId);
	fd.append('contentAreaId', params.contentAreaId);
	fd.append('testingProgramId', params.testingProgramId);
	var reportCycleId;
	if($('#summativeReportTestingCycle') != null){
		reportCycleId = $('#summativeReportTestingCycle').val();
	}
	fd.append('reportingCycleId', reportCycleId);
	$.ajax({
		url: 'uploadFileData.htm',
		data: fd,
		dataType: 'json',
		processData: false,
		contentType: false,
		type: 'POST'	
	}).done(function(data){
		if (data.errorMessage != null){
			$('#reportUploadErrorMessage')
			.html(data.errorMessage)
			.dialog('open');
		}
		loadUploadGrid(false);
	});
}

function resetUploadReport(){
	var form = $('#summativeReportFileUploadForm');
	form[0].reset();
	form.find('select.bcg_select').each(function(){
		$(this).val(0).change().trigger('change.select2');
	});
	form.validate().resetForm();
}

function showInvalidDetails(uploadId) {
	$("#invalidUploadrGrid").jqGrid("GridUnload");
	$("#invalidUploadrGrid").jqGrid({
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
	   		groupDataSorted : true,
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
	$('#uploadReportReasonsDetails').dialog('open');	
}
</script>

</security:authorize>