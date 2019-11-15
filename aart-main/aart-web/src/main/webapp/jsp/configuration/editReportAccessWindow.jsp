<%@ include file="/jsp/include.jsp" %>

<style type="text/css">

#editReportAssessScreens{
	margin:10px;
}
#editReportAssessScreens .editReportAssessScreenInfo{
	margin-top: 50px;
	clear: both;
	width: 900px;
}
#editReportAssessScreens .editReportAssessScreenSubmit{
	width:100%;
	float:left;
}
.reportColor{
	color:black;
	font-weight: normal;
}
#editReportAssessScreens .btn_blue{
	background-color: #0e76bc;
    border: 0 none;
    border-radius: 4px;
    color: #ffffff;
    height: 45px;
    margin-right: 6px;
    width: 75px;
    font-size: 1em;
    font-weight: 300;
    cursor: pointer;
}
#editReportAssessScreens .btn_blue:HOVER{
	background: #238bd1;
}
#editReportAssessScreens .field-label{
	color: #0E76BC;
    display: block;
    margin: 15px 0 5px;
    text-transform: none;
    font-weight: bold;
}
#editReportAssessHiddenFileds{
	display:none;
}
</style>
<security:authorize access="hasRole('EDIT_REPORT_CONTROL_ACCESS')">

<div id="editReportAssessHiddenFileds">
	<input id="editReportAssessScreenSubmitID" type="hidden"/>
</div>
<div id="editReportAssessScreens">
	<div class="editReportAssessScreenSubmit"> <button id="editReportAssessScreenSubmitCancel" class="btn_blue right" type="button">Cancel</button> <button id="editReportAssessScreenSubmitSave" class="btn_blue right" type="button">Save</button> </div>
	<div class="editReportAssessScreenInfo">
		<div style="width:100% padding:0px; margin:0px;">
			<div style="margin: -35px 0 0; width: 49%;"><label class="field-label">Assessment Program: <span class="reportColor" id="reportAssessmentProgramName"></span> </label></div>
			<div style="width: 47%; margin-top:-35px;" class="right"><label class="field-label">State: <span class="reportColor" id="reportStateName"></span></label></div>
		</div>
		<div><label class="field-label">Report: <span class="reportColor" id="reportName"></span> </label></div>
		<div><label class="field-label">Subject: <span class="reportColor" id="subject"></span></label></div>
	</div>  
	
	<div><label class="field-label">Roles which have the report permission: <span class="reportColor" id="rolesName"></span> </label></div>
	<div style="margin-bottom:25px; margin-top:35px;">Select which roles should have access to this report now for the selected state and subject</div>
	<div id="editReportAssessCheckBoxes" style = "margin-left: 50px;"></div> 
</div>

<script>
$(function() {
	$("#editSuccessMessage").hide();
	$('#editReportAssessScreenSubmitSave').off('click').on('click',function(){
		var reportAssessmentProgramId = $('#editReportAssessScreenSubmitID').val();
		var rolesId = [];
		$('#editReportAssessCheckBoxes input:checked').each(function(index,value){
			rolesId[index] =  $(this).val();
		});
		
		 $.ajax({
		        url: 'editReportAccessData.htm',
		        data: {reportAssessmentProgramId:reportAssessmentProgramId,rolesId:rolesId },
		        dataType: 'json',
		        type: "POST",
		        success: function(states) {
		        	$("#editSuccessMessage").show();
		        	$("#editReportAccessWindow").dialog("close");
		        	//$("#reportAccessGridTableId").trigger("reloadGrid");
		        	$('#reportAccessSearch').trigger('click');
		        setTimeout(function(){
		        	$("#editSuccessMessage").hide();
		        }, 3000);
		        }
		              
			});
	});
	
	$("#editReportAssessScreenSubmitCancel").on('click',function(){
		$("#editReportAccessWindow").dialog("close");
	});

});
</script>
</security:authorize>