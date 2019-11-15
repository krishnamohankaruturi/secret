
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="/jsp/include.jsp" %> 
<style>
.h3, h3{
font-size : 1.20em !important;
}
.h1, .h2, .h3, .h4, .h5, .h6, h1, h2, h3, h4, h5, h6{
font-weight:bold !important;
}
h1, h2, h3, h4, h5, h6{
margin-top:19px !important;
}
.txtFields, .text{
height:35px !important;
}

.field-label {
	color: #0e76bc;
}
.approverCheckBoxError{
    margin-top: 15px;
    margin-bottom: 10px;
    display:none;
}
.btncursor{
	cursor: pointer;
}

.approveConfirmDialog{
 margin-top: 5px;
 float: right;
 text-align: left;
 width: 85%;

}
.reminderList{
	background-color: initial !important;
	color:orange !important;
	margin-bottom: 20px;	
}
.reminderLabel{
	color:#484848 !important;
	line-height: 22px;
	margin-bottom: 20px;
}
.listDev{
margin-top: 40px;
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
#approveCheckboxError{
	color:#f01;
}

.input-append, .input-file {
    margin-top: 0px !important;

</style>
<div style="margin-left: 20px; float: left;width: 100%;">
	<div>
		<div style="float: left;">
			<label class="field-label">STATE:</label> <label
				style="margin-right: 30px;">${user.contractingOrganization.organizationName}</label>
		</div>
		<div style="float: left;">
			<input type="hidden" id="manageGrfReportYear" value=${user.contractingOrganization.reportYear}></input>
			<label class="field-label" >REPORT YEAR: </label><label>${user.contractingOrganization.reportYear}</label>
		</div>
		<c:if test="${user.contractingOrganization.reportYear != user.contractingOrganization.currentSchoolYear}">
		 	<c:if test="${user.groupCode != 'SAAD'}">
				<div style="float: left;">
					<a	href="javascript:setReportYearToSchoolYear();" style="margin-left: 16px;">Set report year to school year</a>
				</div>
			</c:if>
		</c:if>
	</div>
	
	<div id="commonGrfError" class="error commonGrfError" style="display: none;"></div>
	<div id="uploadOriginalGRFError" class="error uploadOriginalGRFError" style="display: none;">You must select file and try to upload.</div>

	<form id="manageOriginalGrfForm" class="form">	
 	<security:authorize access="hasRole('CREATE_GRF_INFO')"> 
 	<c:if test="${user.groupCode != 'SAAD'}">
		<div style="float: left;color:#0a507e; width:100%;" class="approvehide">
			<label style="float: left; padding-top: 24px;">Upload original GRF</label>
			
			<div style="float: left; margin-left: 6%;" >
				<div style="float: left; padding-top: 12px;" class="form-fields" >
					<input id="manageGRFOriginalFileData" name="manageGRFOriginalFileData" type="file"
						class="hideFileUploader">
					<div class="input-append">
						<input type="text" name="manageGRFOriginalFileDataInput"
							id="manageGRFOriginalFileDataInput"
							onkeypress="uploadOrginalGrfFile(event);"
							title="Upload original GRF"
							onclick="uploadOrginalGrfFile(event);"
							class="input-file" style="height: 29px;" readOnly="readOnly" /> <a
							class="add-onResult"
							tabindex="0"
							style="margin-left: -6px !important;"
							title="Upload orginal grf file"
							onkeypress="uploadOrginalGrfFile(event);"
							onclick="uploadOrginalGrfFile(event);">
						</a>
					</div>
				</div>
				<div style="float: left;margin-left: 13px;margin-top: 8px;">
					<button type="button" style="width: 130%;" class="btn_blue btncursor" id="uploadManageGRFOriginal" >Upload</button>
				</div>
			</div>
		</div>
		</c:if>	
	</security:authorize>
	</form> 
	<br/>

	<div id="uploadGRFError" class="error uploadGRFError" style="display: none;">You must select file and try to upload.</div>
	<form id="manageGrfForm" class="form">
		<div style="float: left;color:#0a507e;width: 100%;" class = "approvehide" >
			<label style="float: left; padding-top: 24px;" id="editGrf">Upload updated GRF</label>
			<div style="float: left; margin-left: 5%;">
				<div style="float: left; padding-top: 12px;"  class="form-fields" >
					<input id="manageGRFFileData" name="manageGRFFileData" type="file"
						class="hideFileUploader">
					<div class="input-append">
						<input type="text" name="manageGRFFileDataInput"
							id="manageGRFFileDataInput"
							onkeypress="uploadUpdatedGrfFile(event);"
							onclick="uploadUpdatedGrfFile(event);"
							title="Upload updated GRF"
							class="input-file" style="height: 29px; margin-left: 5px;" readOnly="readOnly" /> <a
							tabindex="0"
							style="margin-left: -6px !important;"
							title="upload updated GRF file"
							class="add-onResult"
							onkeypress="uploadUpdatedGrfFile(event);"
							onclick="uploadUpdatedGrfFile(event);">
						</a>
					</div>
				</div>
			</div>
			<div style="float: left;margin-left: 15px;margin-top: 8px;">
				<button type="button" style="width: 130%;" class="btn_blue btncursor" id="uploadManageGRF" >Upload</button>
			</div>
		</div>
	</form>

	<div id="grfExtractRequestResponseMessage" style="font-weight:bold;"></div>
	<security:authorize access="hasRole('CREATE_GRF_INFO')">
	<c:if test="${user.groupCode != 'SAAD'}">
		<div style="float: left; width :100%;color:#0a507e;padding-top: 2%;">
		   <input id="manageGrfScCodeExtract" name="manageGrfScCodeExtract" value="true" title="Create Special Circumstances Code extract" class="processCheck" type="checkbox"><label for="manageGrfScCodeExtract">Create Special Circumstances Code extract</label>
		 <br/>
		   <input id="manageGrfExitStudentExtract" name="manageGrfExitStudentExtract" value="true" title="Create Exited Student extract" class="processCheck" type="checkbox"><label for="manageGrfExitStudentExtract">Create Exited Student extract</label>
		</div>
	
		<button class="panel_btn"  id="manageGrfExtract">Submit</button>

	</c:if>

	</security:authorize>
	
    <div id="uploadGRFConfirmationPopup" style="display:none; height: auto;">
			<div><span id="uploadGRFConfirmationMessage" style="line-height: 20px;" ></span></div>
	</div>
	<div id="ConfirmationDivUploadGRF">
	</div>
	<div id="uploadGRFErrorMessage" style="color:#0a507e;">
	</div>
	<div id = "recentInfo" style="float: left; line-height : 83px;color:#0a507e;">
		<label id ="recentInfoMessage"></label>
	</div>
	
	<div style="float: left; color: #0a507e; padding-right: 20px; margin-right: 20px;"
		>
	<div class="approvehide" style="float: left; color: #0a507e; padding-right: 20px; margin-right: 20px;">
		<div style="margin-left: -20px; margin-right:-20px;background-color: #f0f0f0; padding-top: 2%; padding-bottom: 2%; padding-left: 2%;">
			<img style="margin-bottom: 1%;" alt="Remainder" src="images/Reminder.png"></img>
			&nbsp;&nbsp;&nbsp;<label style="font-size: 32px; color: black;font-family: arial;font-weight: bold;">Important
				Reminders</label>
		</div>
		
		<div style="float: left; width: 50%;" class="listDev">
		<ul style="list-style-image: url('images/yellow-bullet.png');">
			<li class="reminderList reminderLabel">
			Download and save the original version of the General Research File for your records.</label></li>
			 	

			<li class="reminderList reminderLabel">
			Invalidating
				a student record will cause the student's assessment results
				to be excluded from aggregated reports. An invalidated student
				will NOT receive an individual score report.</li>
				
			<li	class="reminderList reminderLabel">
			Changing 
				the student's current grade will cause the student's assessment
			 	results to be removed and replaced with 9s in the GRF.</li>
				
			<li class="reminderList reminderLabel">
			Changes 
				to the State Student ID must be applied to EACH subject line item for that student.</li>
		</ul>
		</div>
		<div style="float: right; width: 50%;" class="listDev">
		<ul  style="list-style-image: url('images/yellow-bullet.png');">	
		<li	class="reminderList reminderLabel">
			Changes to the fields below will not be saved<br>
					a. Unique_Row_Identifier<br>
					b. State<br>
					c. Subject<br>
					d. Final_Band<br>
					e. TBD_Growth<br>
					f. Performance_Level<br>
					g. EE columns (EE_1 - EE_26)<br></li>
		<li class="reminderList reminderLabel">
			Select the approve button ONLY if you are sure you have uploaded your 
					  state's final General Research File. Approval cannot be reversed.</li>
				
		</ul>
		</div>
		<div style="text-align: center;width: 100%;float: left;" class="approvehide">
			<div id="approveCheckboxError" class="error approverCheckBoxError">You
				must check that you approve the General Research File for your
				state.</div>
			<input type="checkbox" name="approveGRF" id="approveGRF" title="I approve the General Research File for my state." value="true">
			I approve the General Research File for my state.
			<button style="width: 10%;"
				class="btn_blue grfStateProcessesApproveButton btncursor"
				id="grfStateProcessesApproveButton" onclick="approveBtn()">Approve</button>
		</div>
	</div>
</div>
<hr style="margin-bottom: 3%;"class="extraFilterSection">
<div style="float: left;"></div>
<br />

<label style="float: left; color: #0e76bc; margin-left: 0px;">End-of-Year Report & Extract Status</label>
<br />
<br />
<div class="full_main" style="float: left;">
	<div class="table_wrap">
		<div id="manageGRFReportDataTableIdContainer" class="kite-table"
			style="width: 1050px; margin-left: -16px;">
			<table id="manageGRFReportDataTableId" class="responsive"></table>
			<div id="pmanageGRFReportDataTableId" class="responsive"></div>
		</div>
	</div>
	<div id="editGRFStudentDiv"></div>
</div>

<script type="text/javascript">
$(function(){
	buildViewGRFByOrgGrid();
	$('#manageOriginalGrfForm').validate({
		ignore: '',
		rules: {			
			manageGRFOriginalFileData: {
				required: true,
				extension: 'xlsx,CSV,xls'
			}
		},
		messages: {			
			manageGRFOriginalFileData: 'A CSV/EXCEL file is required.'
		}
	});
	
	$('#manageGrfForm').validate({
		ignore: '',
		rules: {			
			manageGRFFileData: {
				required: true,
				extension: 'xlsx,CSV,xls'
			}
		},
		messages: {			
			manageGRFFileData: 'A CSV/EXCEL file is required.'			
		}
	});
	
	$('#uploadManageGRFOriginal').on("click",function(){
		setTimeout(function(){ $('.form-fields .error').hide('');  },3000);
		if($('#manageOriginalGrfForm').valid()){
			if($('#manageGRFOriginalFileData').val()==''){
				$('#uploadOriginalGRFError').show();
				setTimeout(function(){ $("#uploadOriginalGRFError").hide(); },3000);
			}else{
				uploadGRFData('Original GRF');	
			}			
		}
	});
	
	$('#uploadManageGRF').on("click",function(){
		setTimeout(function(){ $('.form-fields .error').hide('');  },3000);
		if($('#manageGrfForm').valid()){
			if($('#manageGRFFileData').val()==''){
				$('#uploadGRFError').show();
				setTimeout(function(){ $("#uploadGRFError").hide(); },3000);
			}else{
				uploadGRFData('Updated GRF');	
			}		
		}
	});
	
	
	$('#manageGrfExtract').on("click",function(){
			var manageGrfScCodeExtract = $('#manageGrfScCodeExtract').prop('checked');
			var manageGrfExitStudentExtract = $('#manageGrfExitStudentExtract').prop('checked');
				
				if(!manageGrfScCodeExtract && !manageGrfExitStudentExtract){
					$('#grfExtractRequestResponseMessage').css('color', 'red').html('Please select atleast one extract type');
					setTimeout(function(){ $('#grfExtractRequestResponseMessage').html(''); },3000);
				}else{
					if(manageGrfScCodeExtract && !manageGrfExitStudentExtract){
						var fd = { 'manageGrfScCodeExtract' : $('#manageGrfScCodeExtract').prop('checked'), 'manageGrfExitStudentExtract' : false};
						startSpecialReport('DLM Special Circumstance File:Available with score report delivery.', fd);
						$("#manageGrfScCodeExtract").prop("checked", false);
					}
				
					if(!manageGrfScCodeExtract && manageGrfExitStudentExtract){
						var fd = { 'manageGrfExitStudentExtract' : $('#manageGrfExitStudentExtract').prop('checked'), 'manageGrfScCodeExtract' : false};
						startExitReport('DLM Exited Students:Available with score report delivery.', fd);
						$("#manageGrfExitStudentExtract").prop("checked", false);
					}
				
					if(manageGrfScCodeExtract && manageGrfExitStudentExtract){
						var fd = { 'manageGrfScCodeExtract' : $('#manageGrfScCodeExtract').prop('checked'), 'manageGrfExitStudentExtract' : false};
						var fd1 = { 'manageGrfExitStudentExtract' : $('#manageGrfExitStudentExtract').prop('checked'), 'manageGrfScCodeExtract' : false};
						startSpecialReport('DLM Special Circumstance File:Available with score report delivery.', fd);
						startExitReport('DLM Exited Students:Available with score report delivery.', fd1);
						$("#manageGrfScCodeExtract").prop("checked", false);
						$("#manageGrfExitStudentExtract").prop("checked", false);
					}
					$('#manageGrfExtract').prop("disabled",true);
					$('#manageGrfExtract').addClass('ui-state-disabled');	
				}
		});
});


function showWarningPopup(message){
	$('#ConfirmationDivUploadGRF').html(message).dialog('open');
	$('#manageGrfExtract').prop("disabled",false);
	$('#manageGrfExtract').removeClass('ui-state-disabled');
}

function startSpecialReport(uploadType, fd){
	var data = {				
			assessmentProgramId: $('#userDefaultAssessmentProgram').val(),
			fileTypeId:uploadType,
			stateId: $('#userDefaultOrganization').val(),
			categoryCode:"",
			grfUploadType:uploadType,
			reportYear:$('#manageGrfReportYear').val()
		};
		
		$.ajax({
			url: 'checkForInProgressUpload.htm',
			data: data,
			dataType: 'json',
			type: 'POST'
		}).done(function(count){
			if (count == 0){
				checkManageGRFExtract(fd);
			} else {
				showWarningPopup("<fmt:message key='label.managegrf.specialcirumstane.alreadyupload.warning'/>");
			}
		});
}

function startExitReport(uploadType, fd){
	var data = {				
			assessmentProgramId: $('#userDefaultAssessmentProgram').val(),
			fileTypeId: uploadType,
			stateId: $('#userDefaultOrganization').val(),
			categoryCode:"",
			grfUploadType:uploadType,
			reportYear:$('#manageGrfReportYear').val()
		};
		
		$.ajax({
			url: 'checkForInProgressUpload.htm',
			data: data,
			dataType: 'json',
			type: 'POST'
		}).done(function(count){
			if (count == 0){
				checkManageGRFExtract(fd);
			} else {
				showWarningPopup("<fmt:message key='label.managegrf.exitstudent.alreadyupload.warning'/>");
			}
		});
}

function checkManageGRFExtract(fd){
		$.ajax({
			url: 'manageGrfExtract.htm',
			data: fd,
			dataType: 'json',
			type: 'POST'			
		}).done(function(data){
			loadGRFGridTable(true);
			$('#grfExtractRequestResponseMessage').css('color', 'green').html('Successfully requested selected extracts');
			setTimeout(function(){ $('#grfExtractRequestResponseMessage').html(''); },3000);
			$('#manageGrfExtract').prop("disabled",false);
			$('#manageGrfExtract').removeClass('ui-state-disabled');
		});
}

$("#ConfirmationDivUploadGRF").dialog({
    autoOpen: false,
    modal: true
});

$('input[id=manageGRFFileData]').on("change",function() {
	$('#manageGRFFileDataInput').val($('#manageGRFFileData')[0].files[0].name);
});

$('input[id=manageGRFOriginalFileData]').on("change",function() {
	$('#manageGRFOriginalFileDataInput').val($('#manageGRFOriginalFileData')[0].files[0].name);
});
	
	clearInterval(summativeGridRefreshInterval);
	  $("#grfStateProcessesApproveButton").prop("disabled",true);
	  $('#grfStateProcessesApproveButton').addClass('ui-state-disabled');
	  buildViewGRFByOrgGrid();

 	$("#grfStateProcessesApproveButton").prop("disabled",true);
	$('#grfStateProcessesApproveButton').addClass('ui-state-disabled');
	
	function loadRecentAction(){
		$.ajax({
			url: 'getGRFRecentStatus.htm',
			data: {stateId:$('#userDefaultOrganization').val(),
				   assessmentProgramId:$('#userDefaultAssessmentProgram').val(),
				   reportYear:$('#manageGrfReportYear').val()},
			dataType: 'json',
			type: 'POST'			
		}).done(function(data){
			if(data.length > 0 && data[0] != null){
				  if(data[0].operation == 'APPROVE'){
					$(".approvehide").hide();
				    $("#recentInfoMessage").text("GRF approved: "+data[0].updatedDateStr+" by "+data[0].userName);
				  }else if(data[0].operation == 'Original GRF'){
				    $("#recentInfoMessage").text("Original GRF uploaded: "+data[0].updatedDateStr+" by "+data[0].userName);
				    $("#grfStateProcessesApproveButton").prop("disabled",false);
					$('#grfStateProcessesApproveButton').removeClass('ui-state-disabled');
				  }else if(data[0].operation == 'Updated GRF'){
				    $("#recentInfoMessage").text("GRF last updated: "+data[0].updatedDateStr+" by "+data[0].userName);
				    $("#grfStateProcessesApproveButton").prop("disabled",false);
					$('#grfStateProcessesApproveButton').removeClass('ui-state-disabled');
				  }
				}
		});
	}
	
	function approveBtn(){
		if($('#approveGRF').is(':checked')){
			$("#approveCheckboxError").hide();
			
			$('<div style="line-height: 20px;text-align:center;" ></div>').html("<div style='float: left;width: 15%;'><img alt='Remainder' src='images/Remainder.png'></div><div class='approveConfirmDialog' ><fmt:message key='label.managegrf.stategrfapprove.confirm'/></div>").dialog({
			      height: 200,
			      width: 'auto',
			      modal: true,
			      closeOnEscape: false,
			      buttons: {
			        "CONTINUE": function() {
			        	$(this).dialog("close");
			        	//call Aggregate Report Generation method
			        		
			        	var reportYear = $('#manageGrfReportYear').val();
			        	
			        	var data = {				
						assessmentProgramId: $('#userDefaultAssessmentProgram').val(),
						stateId: $('#userDefaultOrganization').val(),
						reportYear:reportYear
						};
			        	
			        	$.ajax({
						url: 'checkForInProgressGrfUploadOrReport.htm',
						data: data,
						dataType: 'json',
						type: 'POST'
						}).done(function(count){
							if (count == 0){							 	
					        	var data = {				
									assessmentProgramId: $('#userDefaultAssessmentProgram').val(),					
									stateId: $('#userDefaultOrganization').val(),					
									reportYear:$('#manageGrfReportYear').val()
								};	     	
					         	
					        	approveGrfRecords(data);
								
							} else {
								$('#ConfirmationDivUploadGRF')
								.html("<fmt:message key='label.managegrf.alreadyuploadorreport.warning'/>")
								.dialog('open');								
							}
						  });		        	
			       
			        },
			      	"CANCEL": function() {		      		
			          	$(this).dialog( "close" );
			       	}
			      }
				});	
			
		}else{
			$("#approveCheckboxError").show();
			setTimeout(function(){ $("#approveCheckboxError").hide(); },3000);
		}
	}
	

	function approveGrfRecords(data){
		$.ajax({
			url: 'approveGrfRecords.htm',
			data: data,
			dataType: 'json',
			type: "POST",
			async: false,						
		}).done(function(data){
			loadGRFGridTable(true);	
		}).fail(function(jqXHR, textStatus, errorThrown) {
			
		});
	}
	
	function setReportYearToSchoolYear(){		
		
		$('<div style="line-height: 20px;text-align:center;" ></div>').html("<div style='float: left;width: 15%;'><img alt='Remainder' src='images/Remainder.png'></div><div class='approveConfirmDialog' ><fmt:message key='label.managegrf.updatereportyear.confirm'/></div>").dialog({
		      height: 200,
		      width: 585,
		      modal: true,
		      closeOnEscape: false,
		      buttons: {
		        "CONTINUE": function() {
		        	$(this).dialog("close");
		        		        	
		        	var data = {						
						stateId: $('#userDefaultOrganization').val()
					};
		        	
		        	$.ajax({
					url: 'setReportYearToSchoolYear.htm',
					data: data,
					dataType: 'json',
					type: 'POST'					
				    }).done(function(data){
						if(data.success){
							$("#userDefaultAssessmentProgram").trigger('change');	
						}else{
							$("#commonGrfError").show().html("Report year not updated");
							setTimeout(function(){ $("#commonGrfError").hide(); },3000);
						}					
					 });    	       
		        },
		      	"CANCEL": function() {		      		
		          	$(this).dialog( "close" );
		       	}
		      }
			});			
	}
	
	function searchButtonClick(){	
		var uniqueRowIdentifier = $("#manageGRFUniqueRowIdentifier").val();
		var stateStudentIdentifier = $("#manageGRFStateStudentID").val();
		var subjectId = $("#manageGRFSubject").val();
		if(uniqueRowIdentifier =='' && stateStudentIdentifier == '' && subjectId == ''){
			$('#searchStudentGrfMand').show();			
			$("#notValidRowId").hide();
			$("#studentIdErr").hide();		
			$("#editGRFStudentContent").hide();
		}else if(uniqueRowIdentifier =='' && (stateStudentIdentifier == '' || subjectId == '')){
			$('#searchStudentGrfMand').show();			
			$("#notValidRowId").hide();
			$("#studentIdErr").hide();			
			$("#editGRFStudentContent").hide();
		}
		else{
			$("#notValidRowId").hide();
			$("#studentIdErr").hide();
			$('#searchStudentGrfMand').hide();
			$('#editGRFStudentInfo').hide();
			$('#saveGRFStudentInformation').hide();
			callEditStudentGRF(uniqueRowIdentifier, stateStudentIdentifier, subjectId);
		}	
	}
	
	
	function uploadGRFData(uploadType){
		var reportYear = $('#manageGrfReportYear').val();
		var filedata = '';
		if(uploadType == 'Original GRF'){
			filedata = $('#manageGRFOriginalFileData');
			$('#uploadGRFConfirmationMessage').empty().append("<fmt:message key='label.managegrf.uploadoriginalgrf.confirm'/> The current report year is <b>"+reportYear+"</b>. Do you wish to continue?");
		}else{
			filedata = $('#manageGRFFileData');
			$('#uploadGRFConfirmationMessage').empty().append("<fmt:message key='label.managegrf.uploadupdatedgrf.confirm'/>");
		}
		
		var filelist = filedata[0].files;
		var file = filelist[0];
		
		var dialog = $('#uploadGRFConfirmationPopup').dialog({
			resizable: false,
			width: 585,
			modal: true,
			autoOpen: true,
			title: '',
			create: function(event, ui){				
				var widget = $(this).dialog("widget");
				$("#uploadManageGRFOriginal").prop("disabled",false);
				$('#uploadManageGRFOriginal').removeClass('ui-state-disabled');
				$("#uploadManageGRF").prop("disabled",false);
				$('#uploadManageGRF').removeClass('ui-state-disabled');
			
			},
		buttons: {		
			Yes: function(){
					var data = {				
						assessmentProgramId: $('#userDefaultAssessmentProgram').val(),
						fileTypeId: "UPLOAD_GRF_FILE_TYPE",
						stateId: $('#userDefaultOrganization').val(),
						categoryCode:"UPLOAD_GRF_FILE_TYPE",
						grfUploadType:uploadType,
						reportYear:reportYear
					};
					
					$.ajax({
						url: 'checkForInProgressUpload.htm',
						data: data,
						dataType: 'json',
						type: 'POST'						
					}).done(function(count){
						if (count == 0){
							upoadGRFData(data, file);
						} else {
							$("#uploadManageGRFOriginal").prop("disabled",false);
							$('#uploadManageGRFOriginal').removeClass('ui-state-disabled');
							$("#uploadManageGRF").prop("disabled",false);
							$('#uploadManageGRF').removeClass('ui-state-disabled');
							$('#ConfirmationDivUploadGRF')
							.html("<fmt:message key='label.managegrf.alreadyupload.warning'/>")
							.dialog('open');
							
						}
					});
								
				$("#uploadManageGRFOriginal").prop("disabled",true);
				$('#uploadManageGRFOriginal').addClass('ui-state-disabled');
				$("#uploadManageGRF").prop("disabled",true);
				$('#uploadManageGRF').addClass('ui-state-disabled');
				// close 
				$(this).dialog('close');				
		},
        No : function(){			
			$(this).dialog('close');
			$("#uploadManageGRFOriginal").prop("disabled",false);
			$('#uploadManageGRFOriginal').removeClass('ui-state-disabled');
			$("#uploadManageGRF").prop("disabled",false);
			$('#uploadManageGRF').removeClass('ui-state-disabled');
		}
		},
	});
}
	
	function upoadGRFData(params, file){
		var fd = new FormData();
		fd.append('uploadFile', file);	
		fd.append('assessmentProgramId', params.assessmentProgramId);
		fd.append('categoryCode',params.categoryCode);
		fd.append('stateId', params.stateId);
		fd.append('grfUploadType', params.grfUploadType);
		fd.append('reportYear', params.reportYear);
		 
		$.ajax({
			url: 'uploadFileData.htm',
			data: fd,
			dataType: 'json',
			processData: false,
			contentType: false,
			type: 'POST'			
		}).done(function(data){
			if (data.errorMessage != null){
				$('#uploadGRFErrorMessage')
				.html(data.errorMessage)
				.dialog('open');
			}
			loadGRFGridTable(true);
			$("#uploadManageGRFOriginal").prop("disabled",false);
			$('#uploadManageGRFOriginal').removeClass('ui-state-disabled');
			$("#uploadManageGRF").prop("disabled",false);
			$('#uploadManageGRF').removeClass('ui-state-disabled');
			if(params.grfUploadType == 'Original GRF'){
				$('#manageGRFOriginalFileDataInput').val('');
				$('#manageGRFOriginalFileData').val('');
			}else{
				$('#manageGRFFileDataInput').val('');
				$('#manageGRFFileData').val('');
			}
		});
	}
	
	function editGRFStateProcesses() {
		var assessmentProgramId = $('#userDefaultAssessmentProgram').val();
		var stateId = $('#userDefaultOrganization').val();
		var reportYear = $('#manageGrfReportYear').val();
		
    	var data = {				
				assessmentProgramId: assessmentProgramId,					
				stateId: stateId,					
				reportYear: reportYear
			};	  
    	
		$.ajax({
			url: 'checkForInProgressGrfUploadOrReport.htm',
			data: data,
			dataType: 'json',
			type: 'POST'
			}).done(function(count){
				if (count == 0){							 	
		        	
		        	$('#editGRFStudentDiv').dialog(
		    				{
		    					autoOpen : false,
		    					modal : true,
		    					width : 1140,
		    					height : 650,					
		    					create : function(event, ui) {
		    						var widget = $(this).dialog("widget");
		    					},
		    					
		    					open: function(event, ui) {
		    						
		    				    },
		    					beforeClose : function() {
		    					}
		    				}).load('editStudentGrf.htm', {
		    			           assessmentProgramId:assessmentProgramId,
		    			           stateId:stateId,
		    			           reportYear:reportYear
		    		        }).dialog('open');
					
				} else {
					$('#ConfirmationDivUploadGRF')
					.html("<fmt:message key='label.managegrf.alreadyuploadorreport.warning'/>")
					.dialog('open');								
				}
			  });
	}	
	

	function callEditStudentGRF(uniqueRowIdentifier, stateStudentIdentifier, subjectId) {
		var assessmentProgramId = $('#userDefaultAssessmentProgram').val();
		var stateId = $('#userDefaultOrganization').val();
		var reportYear = $('#manageGrfReportYear').val();
		setTimeout(function(){
		$('#editGRFStudentDiv').dialog(
				{
					autoOpen : false,
					modal : true,
					width : 1140,
					height : 650,
					/* title: escapeHtml(dialogTitle), */
					create : function(event, ui) {
						var widget = $(this).dialog("widget");
					},
					
					open: function(event, ui) {
						
				    },
					beforeClose : function() {
					}
				}).load('editStudentGrfInformation.htm', {
			           assessmentProgramId:assessmentProgramId,
			           stateId:stateId,
			           reportYear:reportYear,
			           uniqueRowIdentifier : uniqueRowIdentifier,
			           stateStudentIdentifier:stateStudentIdentifier.trim(),
			           subjectId:subjectId
		        }, function(){
		        	
		        	if(($("#uploadGrfId").val()==null || $("#uploadGrfId").val()=='') && $("#manageGRFUniqueRowIdentifier").val()==''){
		        		$("#studentIdErr").show();
		        		$("#notValidRowId").hide();	
		        		$('#searchStudentGrfMand').hide();
		        		$("#notValidRowIdStateStudentId").hide();
		        	}
		        	else if (($("#uploadGrfId").val()==null || $("#uploadGrfId").val()=='') && $("#manageGRFUniqueRowIdentifier").val()!='' && $("#manageGRFStateStudentID").val()==''){
		        		$("#notValidRowId").show();
		        		$("#studentIdErr").hide();
		        		$('#searchStudentGrfMand').hide();
		        		$("#notValidRowIdStateStudentId").hide();
		        	}
		        	else if (($("#uploadGrfId").val()==null || $("#uploadGrfId").val()=='') && $("#manageGRFUniqueRowIdentifier").val()!='' && $("#manageGRFStateStudentID").val()!=''){
		        		$("#notValidRowIdStateStudentId").show();
		        		$("#notValidRowId").hide();
		        		$("#studentIdErr").hide();
		        		$('#searchStudentGrfMand').hide();		
		        	}
		        	else{		        		
		        		$("#editGRFStudentContent").show();	
		        	}	        	
		        	
		        }).dialog('open');
		
		},200)	
	}
	
	function resetManageGrfUploads(){ 
		$('#manageGRFOriginalFileDataInput').val('');
		$('#manageGRFOriginalFileData').val('');
		$('#manageGRFFileDataInput').val('');
		$('#manageGRFFileData').val('');
	}
	
	function loadGRFGridTable(resetPageNumber) {		
		var $grid = $('#manageGRFReportDataTableId');
		var pData = $grid.getGridParam('postData');
		loadRecentAction();
		if (!pData.filters) {
			pData.filters = '';
		}
		var opts = [];
		if (resetPageNumber) {
			opts = [ {
				page : 1
			} ];
		}
		$grid.jqGrid('setGridParam', {
			datatype : 'json',
			url : 'getGRFProcessStatus.htm',
			search : false,
			postData : pData
		}).trigger('reloadGrid', opts);
	}
	
	function buildViewGRFByOrgGrid() {
		
		var colModel = [
				{
					label : 'id',
					name : 'id',
					hidden : true,
					hidedlg : true
				}, {
					label : 'Action',
					name : 'grfProcessType',
					index : 'grfprocesstype',
					width : 200,
					hidedlg : true
				}, {
					label : 'Submitted',
					name : 'submissionDate',
					index : 'submissiondate',
					hidedlg : true,
					formatoptions : {
						newformat : 'm/d/Y h:i:s A'
					},
					formatter : function(cellValue, options, rowObject, action) {
						return $.fn.fmatter.call(this, 'date', new Date(
								cellValue), $.extend({},
								$.jgrid.formatter.date, options), rowObject,
								action);
					}
				}, {
					label : 'File Name',
					name : 'fileName',
					index : 'filename',
					hidedlg : true
				}, {
					label : 'Status',
					name : 'status',
					index : 'status',
					width : 200,
					hidedlg : true
				}, {
					label : 'Rejected',
					name : 'failedCount',
					index : 'failedcount',
					hidedlg : true,
					width : 110
				}, {
					label : 'Created',
					name : 'successCount',
					index : 'successcount',
					hidedlg : true,
					width : 100
				}, {
					label : 'User',
					name : 'createdUserDisplayName',
					index : 'createduserdisplayname',
					hidedlg : true
				}, {
					label : 'Report Year',
					name : 'reportYear',
					index : 'reportyear',
					width : 70,
					hidedlg : true
				}, {
					label : 'Error File',
					name: 'filePath', 
					index: 'filePath', 
					hidedlg: true,
					width:'100px',  
					formatter:extractGRFErrorMessageFormatter
				}
		];
		$('#manageGRFReportDataTableId').scb({
			mtype : 'POST',
			datatype : 'local',
			width : $('#manageGRFReportDataTableIdContainer').width(),
			height : 'auto',
			filterstatesave : true,
			pagestatesave : true,
			colModel : colModel,
			filterToolbar : false,
			rowNum : 5,
			rowList : [ 5, 10, 20, 30, 40, 60, 90 ],
			columnChooser : false,
			multiselect : false,
			footerrow : true,
			pager : '#pmanageGRFReportDataTableId',
			sortname : 'submissiondate',
			sortorder : 'DESC',
			jsonReader : {
				page : function(obj) {
					return obj.page !== undefined ? obj.page : "0";
				},
				repeatitems : false,
				root : function(obj) {
					return obj.rows;
				}
			}
		});

		loadGRFGridTable(true);
	}
	
	//Code to set the position of the AlertMod 
	 //warning message (Please, select a row)
	 var grfViewModal = $.jgrid.viewModal;
	 $.extend($.jgrid,{
	     viewModal: function (selector,o){ 
	         if(selector == '#alertmod'){
	             var of = jQuery(o.gbox).offset();
	             var w = jQuery(o.gbox).width();
	             var h = jQuery(o.gbox).height();
	             var w1 = $(selector).width();
	             //var h1 = $(selector).height();
	             $(selector).css({
	                 'top':"1336.55px",
	                 'left':of.left+((w-w1)/2)
	             });
	         }
	         grfViewModal.call(this, selector, o);
	     }
	 }); 
	 
	function extractGRFErrorMessageFormatter(cellValue, options, rowObject){
		var status = rowObject.status;
		if ((status == "COMPLETED" && rowObject.failedCount > 0) || (status == "FAILED" ) ) {
			var currDate = new Date();
			var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
			var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
			+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
			var file = rowObject.fileName.split(".",1);
			var fileName = file +"_GRF_Errors_"+dateStringForFile+".csv";
				return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
		}
		else
			return '<a> </a>';
	}
	function uploadOrginalGrfFile(event){
		 if(event.type=='keypress'){
			  if(event.which !=13){
			   return false;
			  }
			 }
		 $('input[id=manageGRFOriginalFileData]').click();
	}
	
	function uploadUpdatedGrfFile(event){
		 if(event.type=='keypress'){
			  if(event.which !=13){
			   return false;
			  }
			 }
		 $('input[id=manageGRFFileData]').click();
	}


</script>