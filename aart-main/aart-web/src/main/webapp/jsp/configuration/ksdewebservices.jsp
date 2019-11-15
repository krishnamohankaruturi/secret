<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>"
	type="text/css" />
<security:authorize access="!hasRole('UPLOAD_WEBSERVICE')">
	<div class="messages">
		<span class="error_message ui-state-error permissionDeniedMessage" id="ksdePermissionDeniedMessage">
		<fmt:message key="error.permissionDenied" /></span>
	</div>
</security:authorize>

<security:authorize access="hasRole('UPLOAD_WEBSERVICE')">
	<div id="webServiceContainer" class="form">
		<div class="form-fields">
			<label for="extractState" class="field-label isrequired">STATE: <span
				class="lbl-required">*</span>
			</label> <select id="extractState" title="State" class="bcg_select required" name="extractState">
				<option value="0">Select</option>
			</select>
		</div>
		<div class="form-fields">
			<label for="extractAssessmentProgram" class="field-label required">ASSESSMENT PROGRAM:<span class="lbl-required">*</span></label>
			<select name="extractAssessmentProgram" title="Assessment Program" class="bcg_select required" id="extractAssessmentProgram">
				<option value="0">Select</option>
			</select>
		</div>
		<div class="form-fields">
			<label for="extractSubject" class="field-label required">SUBJECT:<span class="lbl-required">*</span></label>
			<select name="extractSubject"  title="Subject" class="bcg_select required" id="extractSubject">
				<option value="0">Select</option>
			</select>
		</div>
		<div>
			<div class="inline_block">
				<input type="submit" id="webServiceUpload" value="<fmt:message key='upload.button'/>" />
			</div>
			<div class="inline_block">
				<input id="createExtract" type="button" value="<fmt:message key='extract.button'/>" />
			</div>
		</div>
		
	</div>
	<div id="extractStatusMsg"></div>
	<div id="statusMsg">
	<div id="uploadStatus"></div>
	<div id="rosterUploadStatus"></div>
	</div>
	
	<div id="extractsSection" class="kite-table">
		<table class="responsive" id="ksdeTable"></table>
		<div id="extractsPager"></div>
	</div>
	<!-- div id="ampExtractsSection" class="kite-table">
		<table class="responsive" id="ampTable"></table>
	</div-->
	<script type="text/javascript">
	$(function() {
		$("#extractStatusMsg").html('');
		ksdeExtract();
		loadExtractStates();
		disableCreateExtractBtn();
	});
	
	function disableCreateExtractBtn(){
		var extractAssessmentId = $('#extractAssessmentProgram').val();
		var extractStateId = $('#extractState').val();
		var extractSubjectId =  $('#extractSubject').val();
		if(extractAssessmentId != null && extractStateId != null && extractAssessmentId != 0 && extractStateId != 0 
				&& extractAssessmentId != '' && extractStateId != '' && extractSubjectId != null && extractSubjectId!= 0 && extractSubjectId!= ''){
			$("#createExtract").prop("disabled",false);
			$('#createExtract').removeClass('btn_disabled');
			$('#createExtract').addClass("btn_blue");
		} else {
			$("#createExtract").prop("disabled",true);
			$('#createExtract').removeClass('btn_blue');
			$('#createExtract').addClass('btn_disabled');
		}
	}
	
	function loadExtractStates() {
		var me = this;
		var apSelect = $('#extractState'), optionText='';
		$('.messages').html('').hide();
		apSelect.find('option').filter(function(){
			return $(this).val() != '';
		}).remove().end();
		
		$.ajax({
			url: 'getAllExtractStates.htm',
			dataType: 'json',
			type: "GET"
		}).done (function(states) {				
			if (states !== undefined && states !== null && states.length > 0) {
				
				if(states.length>1)
	        		$('#extractState').append($('<option></option>').prop("value", '').text('All'));
				$.each(states, function(i, state) {
					$('#extractState').append($('<option></option>').prop("value", state.id).text(state.organizationName));
				});
				$("#extractState option").first('option').prop('selected', true);
				if (states.length == 1) {
					$("#extractState option").removeAttr('selected').next('option').prop('selected', true);
					$("#extractState").trigger('change');
				}
				$('#extractState, #extractAssessmentProgram, #extractSubject').select2().trigger('change.select2');
			}
		})
	}
	
	  $('#extractState').on("change",function() {
		 $('#extractAssessmentProgram').find('option').filter(function(){return $(this).val() != '';}).remove().end();
		 $('#extractSubject').find('option').filter(function(){return $(this).val() != '';}).remove().end();
		 disableCreateExtractBtn();
		 $("#extractStatusMsg").html('');
		  var stateId = $("#extractState").val();
		  if(stateId=="")
			{
				$('#extractAssessmentProgram, #extractSubject').select2({
					placeholder:'Select',
					multiple: false
				});
			}
		  else if (stateId != undefined && stateId != null && stateId != 0) {
				$.ajax({
			        url: 'getExtractAssessmentProgramsByOrganization.htm',
			        data: {
			        	organizationId: stateId
			        	},
			        dataType: 'json',
			        type: "GET"  
				}).done(function(assessmentPrograms) {
		        	if(assessmentPrograms.length>1)
		        		$('#extractAssessmentProgram').append($('<option></option>').prop("value", -1).text('All'));
					$.each(assessmentPrograms, function(i, assessmentProgram) {
						$('#extractAssessmentProgram').append($('<option></option>').prop("value", assessmentProgram.id).text(assessmentProgram.programName));
					});
					
					if (assessmentPrograms.length == 1) {
						$("#extractAssessmentProgram option").removeAttr('selected').next('option').prop('selected', true);
						$("#extractAssessmentProgram").trigger('change');
					}
					$('#extractAssessmentProgram, #extractSubject').trigger('change.select2');
		        })
			}
	  }); 
		
	  $('#extractAssessmentProgram').on("change",function() {
		  $("#extractStatusMsg").html('');
			$('#extractSubject').find('option').filter(function(){return $(this).val() != '';}).remove().end();
			disableCreateExtractBtn();
			var assessmentProgramId = $('#extractAssessmentProgram').val();
			var stateId = $("#extractState").val();
			var contentAreaId = $("#extractSubject").val();
			if(assessmentProgramId=="")
			{
				$('#extractAssessmentProgram').select2({
					placeholder:'Select',
					multiple: false
				});
			}
			else if (assessmentProgramId != null && assessmentProgramId !=undefined && assessmentProgramId != 0) {
				//Hard coding for KAP- could be removed in future. This is a temperory fix to support existing KSDE extracts
				if(getSelectedText('extractState') == 'Kansas' && getSelectedText('extractAssessmentProgram') == 'KAP'){
					$('#extractSubject').append($('<option></option>').prop("value", "-1").text("All"));
					$('#extractSubject').append($('<option></option>').prop("value", "SELAA,D74").text("English Language Arts and Mathematics"));
					$('#extractSubject').append($('<option></option>').prop("value", "SHISGOVA").text("Social Studies"));
					$('#extractSubject').append($('<option></option>').prop("value", "SSCIA").text("Science"));
				}else{
					$.ajax({
				        url: 'getExtractCoursesBasedOnAssessmentProgram.htm',
				        data: {
				        	assessmentProgramId : assessmentProgramId
				        	},	        	
				        dataType: 'json',
				        type: "GET"
					}) .done(function(contentAreas) {
			        	
			        	if(contentAreas.length>1)
			        		$('#extractSubject').append($('<option></option>').prop("value", -1).text('All'));
			        	if(contentAreaId!=-1){
							$.each(contentAreas, function(i, contentArea) {
								$('#extractSubject').append($('<option></option>').prop("value", contentArea.abbreviatedName).text(contentArea.name));
							});
			        	}
						if (contentAreas.length == 1) {
							$("#extractSubject option").removeAttr('selected').next('option').prop('selected', 'selected');
							$("#extractSubject").trigger('change');
						}
						$("#extractSubject").trigger('change.select2');
			        })
				}
			}
		});		
	  
	  $('#extractSubject').on("change",function() {
		  $("#extractStatusMsg").html('');
		  disableCreateExtractBtn();
	  });
	  
	var upload_summary_msg ="<fmt:message key='upload.webservice.summary' />";
	var upload_success_msg ="<fmt:message key='upload.successful' />";
	var roster_upload_success_msg = "<fmt:message key='roster.upload.successful' />";
	var upload_failed_msg ="<fmt:message key='upload.failed' />";
	var upload_unauthorize_msg = "<fmt:message key='upload.notAuthorized' />";
	var extract_error_msg = "<font color ='red'><fmt:message key='extract.unsupport.error'/></font>";
	$("#createExtract").on("click",function() {
		$("#uploadStatus").html('');
		$("#rosterUploadStatus").html('');
		$("#extractStatusMsg").html('');
		if( getSelectedText('extractAssessmentProgram') == 'KELPA2' || (getSelectedText('extractState') == 'Kansas' && getSelectedText('extractAssessmentProgram') == 'KAP')){
			var $modal = $(this);
			$("#createExtract").prop('disabled',true);
			var data = {};
			data.subject = $("#extractSubject").val();
				$.ajax({
					url: 'generateksdeextract.htm',
				    data: data,
				    dataType:'json',
				    type: 'POST'
				   					
			}).done(function(extractId){
		    	$("#ksdeTable").trigger('reloadGrid');
		    	monitorExtractGenerationStatus(extractId);
		    }).fail( function(jqXHR, textStatus, errorThrown) {
				$("#extractStatusMsg").html(extract_error_msg);
			}).always( function() {
				$("#createExtract").prop('disabled',false);
			})
		} else {
			$("#extractStatusMsg").html(extract_error_msg);
		}
	});
		
	
	$("#webServiceUpload").on("click",function(e){
			$("#webServiceUpload").prop('disabled',true);
			$("#uploadStatus").html('');
			$("#rosterUploadStatus").html('');
			$("#extractStatusMsg").html('');
			$.ajax({
				url: 'invokewebservice.htm',
			    processData: false,
			    contentType: false,
			    type: 'POST'
				
			}).done(function(data){
		    	showServiceStatus(data);
		    }).fail( function(jqXHR, textStatus, errorThrown) {
				$("#uploadStatus").html(upload_failed_msg);
			}).always( function() {
				$("#webServiceUpload").prop('disabled',false);
			})
		});
		
		var formatMessage = function(msg,arr) {
			$.each(arr,function(index,val){
				msg = msg.replace('{'+index+'}',val);
			});
			return msg;
		};
		
		function showServiceStatus(dataObj) {
			if(dataObj.uploadCompleted) {
				var statusMsg,rostersStatusMsg;
				if(dataObj.uploadSuccessful) {
					var uploadArr =[dataObj.recordsCreatedCount,dataObj.recordsUpdatedCount,
					                dataObj.recordsRejectedCount,dataObj.recordsCount];
					statusMsg = upload_success_msg;
				} else {
					statusMsg = upload_failed_msg;
				}
				/* if (!dataObj.uploadAuthorized) {
					statusMsg = upload_unauthorize_msg;
				} */
				
				/*if (dataObj.rosterUploadSuccessful) {
					var uploadArr =[dataObj.rosterRecordsCreatedCount,dataObj.rosterRecordsUpdatedCount,
					                dataObj.rosterRecordsRejectedCount,dataObj.rosterRecordsCount];
					rostersStatusMsg = roster_upload_success_msg;
				} else {
					rostersStatusMsg = upload_failed_msg;
				} */
				/* if (!dataObj.uploadAuthorized) {
					rostersStatusMsg = upload_unauthorize_msg;
				} */
				
				$("#uploadStatus").html(statusMsg);
				$("#rosterUploadStatus").html(rostersStatusMsg);
			}
		}
	function ksdeExtract() {
		$('#ksdeTable').jqGrid({
			url: 'getKSDEExtracts.htm',
			mtype: 'POST',
			datatype: 'json',
			postData: {
				filters: "",
			},
			colNames: [
				'id',
				'extractTypeId',
				'Extract',
				'Description',
				'Requested',
				'fileName',
				'File',
				'dummy1',
				'dummy2',
				'dummy3',
				'dummy4',
				'dummy5',
				'dummy6',
				'dummy7',
				'dummy8',
				'State',
				'Assessment Program'
			],
			colModel: [
				{name: 'id', index: 'id', align: 'center', hidden: true,key:true},
				{name: 'extractTypeId', index: 'extractTypeId', align: 'center', hidden: true,key:true},
				{name: 'extractName', index: 'extractName', align: 'center', sortable: false, width: 200,key:true},
				{name: 'description', index: 'description', align: 'left', sortable: false, width: 300,key:true},
				{name: 'requested', index: 'requested', align: 'center', sortable: false,  width: 180,
					formatter: function(cellValue, options, rowObject){
						if(cellValue != 'Not Available' && cellValue != '') { 
							var date = new Date(Number(cellValue));
           					var ampm = date.getHours() >= 12 ? 'PM' : 'AM';
           					var hours = date.getHours() % 12;
           					if (hours == 0){
           						hours = 12;
           					}
           					var timeStr = (hours > 9 ? hours : '0' + hours.toString()) + ':' +
           							(date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes().toString()) + ':' +
           							(date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds().toString());
           					timeStr += ' ' + ampm;
           					return ($.datepicker.formatDate('mm/dd/yy', date)) + ' ' + timeStr;
           				} else { return cellValue; }
					},key:true
				},
				{name: 'fileName', index: 'fileName', align: 'center', hidden: true,key:true},
				{name: 'status', index: 'status', align: 'center', sortable: false , width: 100, formatter: extractLinkFormatter,key:true},
				<%--
				Reason for dummy columns is that the original way that ModuleReport was built was NOT for this grid,
				but for the original data extracts grid.
				So the empty row generator includes a lot of cells that are not necessary on this grid,
				so to get at the data at the end (state and assessment program), we have to pad with dummy space.
				--%>
				{name: 'dummy1', index: 'dummy1', align: 'center', sortable: false, hidden: true,key:true},
				{name: 'dummy2', index: 'dummy2', align: 'center', sortable: false,  hidden: true,key:true},
				{name: 'dummy3', index: 'dummy3', align: 'center', sortable: false,  hidden: true,key:true},
				{name: 'dummy4', index: 'dummy4', align: 'center', sortable: false,  hidden: true,key:true},
				{name: 'dummy5', index: 'dummy5', align: 'center', sortable: false,  hidden: true,key:true},
				{name: 'dummy6', index: 'dummy6', align: 'center', sortable: false,  hidden: true,key:true},
				{name: 'dummy7', index: 'dummy7', align: 'center', sortable: false,  hidden: true,key:true},
				{name: 'dummy8', index: 'dummy8', align: 'center', sortable: false,  hidden: true,key:true},
				{name: 'state', index: 'state', align: 'center', sortable: false, width: 120,key:true},
				{name: 'assessmentprogram', index: 'assessmentprogram', align: 'center', sortable: false, width: 150,key:true}
			],
			height: 'auto',
			shrinkToFit: true,
			rowNum: 100,
			pgbuttons : false,
			viewrecords : false,
			pgtext : "",
			pginput : false,
			//rowList: null,
			//pager: '#extractsPager',
			viewrecords: true,
			sortorder: 'asc',
			sortname: 'extractName',
			altclass: 'altrow',
			emptyrecords: "<fmt:message key='label.common.norecordsfound'/>",
			altRows: true,
			hoverrows: true,
			multiselect: false,
			toppager: false,
			beforeSelectRow: function(){
				return false;
			}
		});
		
		function extractLinkFormatter(cellValue, options, rowObject){
			var status = rowObject[6].toUpperCase();
			//alert(rowObject[5]);
			if (status == "COMPLETED") {
				return '<a href="getDataExtractCsv.htm?extractName=' +rowObject[5] + '&extractType='+rowObject[1] + '&extractId='+rowObject[0] +'"><img src="images/icon-csv.png" title="Click to download extract."/></a>';
				
			}
			return cellValue;
		}
		
		}
	
	function monitorExtractGenerationStatus(extractId){
		$.post(
			'monitorExtractGenerationStatus.htm',
			{extractId: extractId},
			function(responseText){
				if (responseText === 'In Progress'){
					$("#createExtract").prop('disabled',true);
					setTimeout(function(){monitorExtractGenerationStatus(extractId);}, 90000);
				} else if (responseText === 'Completed' || responseText === 'Failed'){
					$("#createExtract").prop('disabled',false);
					$('#ksdeTable').jqGrid("GridUnload");
					ksdeExtract();
					$('#ksdeTable').trigger('reloadGrid');
				} else {
					$("#createExtract").prop('disabled',false);
				}
			},
			"html"
		);
	}
	function getSelectedText(elementId) {
	    var elt = document.getElementById(elementId);

	    if (elt.selectedIndex == -1)
	        return null;

	    return elt.options[elt.selectedIndex].text;
	}

	</script>
</security:authorize>