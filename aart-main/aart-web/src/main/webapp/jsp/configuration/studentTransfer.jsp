<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
 #selectStudentGridHeader{
	 color:#81a64c;
 }
 
 #selectQuickHelpHint {
    background-color: white;
    border-color: gray;
    border-style: solid;
    border-width: 1px;
    display: none;
    position: absolute;
    z-index: 1000;
    font-size: 14px;
    padding: 0 10px;
}

 #selectQuickHelpHintForDistrict,#selectQuickHelpHintForAccountabilityDistrict {
 	margin-left: 370px;
    background-color: white;
    border-color: gray;
    border-style: solid;
    border-width: 1px;
    display: none;
    position: absolute;
    z-index: 1000;
    font-size: 14px;
    padding: 0 10px;
}

/*  #transferDestinationConfirmationDiv .transferDistrictStudentConfirmationList,  */
#selectDestinationDistrictViewDiv .selectOptionsDistrictTransferStudent{
 	margin-top:-10px; 
}

#transferDestinationConfirmationDiv .transferDistrictStudentConfirmationList {
    margin-left: -25px;
}

#transferDestinationConfirmationDiv .transferDistrictStudentConfirmationList li{
 	background-color: transparent !important;
 	list-style-type: disc;
 	color: #434343 !important;
}

#selectDestinationDistrictViewDiv .selectOptionsDistrictTransferStudent li{
	 background-color: transparent !important;
	 list-style-type: disc;
	 color: #81a64c;
} 

#selectQuickHelpHintPopup{
	background-color: white;
    border-color: gray;
    border-style: solid;
    border-width: 1px;
    display: none;
    left: 30%;
    padding: 10px 0 10px 10px;
    position: absolute;
    top: 38%;
    width: 525px;
    z-index: 1000;
}
#selectQuickHelpHintPopupClose, #selectQuickHelpHintForDistrictPopupClose, #selectQuickHelpHintForAccountabilityDistrictPopupClose{
  background-color: #0e76bc;
    border-radius: 4px;
    color: white;
    cursor: pointer;
    float: right;
    font-size: 16px;
    padding: 4px;
    position: relative;
    right: 5px;
    top: -1.5px;
 }
 #selectQuickHelpHintPopupMessage{
 font-family: sans-serif;
    font-size: 0.8em;
    line-height: 1.5em;
    margin-left: 7px;
    padding-right: 7px;   
    }
#selectQuickHelpHintForDistrictPopup, #selectQuickHelpHintForAccountabilityDistrictPopup{
	background-color: white;
    border-color: gray;
    border-style: solid;
    border-width: 1px;
    display: none;
    left: 38%;
    padding: 10px 0 10px 10px;
    position: absolute;
    top: 44%;
    width: 525px;
    z-index: 1000;
}
 #selectQuickHelpHintForDistrictPopupMessage, #selectQuickHelpHintForAccountabilityDistrictPopupMessage{
 font-family: sans-serif;
    font-size: 0.8em;
    line-height: 1.5em;
    margin-left: 7px;
    padding-right: 7px;   
    }
    
#gview_destinationDistrictByOrgTable th .select2-container--default{
width:180px !important;
margin-left: 5px;
} 
</style>
<div id="transferStudentSelectViewGrid">
	
	<div class="btn-bar" style="margin-bottom:10px">
			<div id="searchFilterTransferStudentsErrors" class="studentMessages"></div>
			<span class="info_message ui-state-highlight hidden successMessage " id="transferStudentSuccessMessage" ><fmt:message key="label.config.transferStudent.create.success"/></span>
			<span class="error_message ui-state-error selectAllLabels hidden validate" id="transferStudentFailMessage"><fmt:message key="error.config.transferStudent.create.fail"/></span>
			<span class="error_message ui-state-error selectAllLabels hidden validate" id="transferNoStudentFailMessage"><fmt:message key="error.config.transferStudent.create.noStudent"/></span>
	</div>
	
<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="transferStudentsOrgFilterForm">
		<div id="transferStudentsOrgFilter"></div>
	</form>
	<a class="panel_btn" href="#" id="transferStudentsSearchButton">Search</a>
</div>

<div class="full_main" >

	<div><span style="font-size: 17px;" id="selectStudentGridHeader">Select student(s) to Transfer From, then click Next:</span></div>
	<div style="margin :0px 0px 10px 0px">
		<span style="font-size: 17px;">Note: Select students transferring to the same District</span>
		<span id="selectStudent_TemplatedownloadquickHelp"><img src="${pageContext.request.contextPath}/images/quickHelp.png" style="width:auto" title="Quick Help" alt="Quick Help"></span>
	</div>
	<div id="selectQuickHelpHint"><span><p>The destination district (not the schools) must be the same for all selected students.</p></span></div>		
	
	<div id="transferStudentsGridCell">
	 	<div id=transferStudentsGridContainer class="kite-table">
	 		<table class="responsive" id="transferStudentsByOrgTable"></table>
			<div id="ptransferStudentsByOrgTable" style="width: auto;"></div>
	 	</div>
	</div>
	<div align="right" style="margin-right:-70px">
		<input type="button" id="transferStudentsGridNext" value="Next"/>
	</div>
</div>
</div>

<div id="selectDestinationDistrictViewDiv" class="form hidden">
	
		<div style="font-size: 17px; display:inline; color:#81a64c"><span>Select destination District, if different than existing District:</span>
			<div class="form-fields">
		    	<label for="districtOrgsSelectDestination" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>
		    		<select id="districtOrgsSelectDestination" class="bcg_select required" name="districtOrgsSelectDestination">
		     			<option value="">Select</option>
			   		</select>
			</div>
			<span id="selectDistrictStudent_TemplatedownloadquickHelp" style='margin-left:-25px'><img src="${pageContext.request.contextPath}/images/quickHelp.png" title="Quick Help" alt="Quick Help" style="width:auto"></span>
		</div>
		<div id="selectQuickHelpHintForDistrict"><span><p>The destination district (not the schools) must be the same for all selected students</p></span></div>
		<div id="selectQuickHelpHintForDistrictPopup" >
		   	<div id="selectQuickHelpHintForDistrictPopupClose" > X </div>
		   		<div id="selectQuickHelpHintForDistrictPopupMessage" >
		   		<p>The destination district (not the schools) must be the same for all selected students.</p>
		   	</div>
		</div>
		<ol class='selectOptionsDistrictTransferStudent'>
			   <li>Select destination Attendance School for all students, or for each individual student.</li>
	    </ol>
	    
	    <div style="font-size: 17px; display:inline; color:#81a64c; width: 100%;"><span style="width: 46%; float: left; margin-top: 2%;">Select a new destination Accountability District, if different than existing Accountability District:</span>
			<div class="form-fields" style="margin-top: -1%;margin-left: 0.8%;">
		    	<label for="accountabilityDistrictOrgsSelectDestination" class="field-label">ACCOUNTABILITY DISTRICT:</label>
		    		<select id="accountabilityDistrictOrgsSelectDestination" class="bcg_select required" name="accountabilityDistrictOrgsSelectDestination">
		     			<option value="">Select</option>
			   		</select>
			</div>
			<span id="selectAccountabilityDistrictStudent_TemplatedownloadquickHelp" style='margin-left:-25px'><img src="${pageContext.request.contextPath}/images/quickHelp.png" title="Quick Help" alt="Quick Help" style="width:auto"></span>
		</div>
	    <div id="selectQuickHelpHintForAccountabilityDistrict"><span><p>The Accountability destination district (not the schools) must be the same for all selected students</p></span></div>
		<div id="selectQuickHelpHintForAccountabilityDistrictPopup" >
		   	<div id="selectQuickHelpHintForAccountabilityDistrictPopupClose" > X </div>
		   		<div id="selectQuickHelpHintForAccountabilityDistrictPopupMessage" >
		   		<p>The Accountability destination district (not the schools) must be the same for all selected students.</p>
		   	</div>
		</div>	    
	    <ol class='selectOptionsDistrictTransferStudent'>
			   <li style="float: left;">Select option to keep or remove existing Accountability School, or select an Accountability School for all students or for each individual student.</li>
			   <li>Select option to keep or remove Local Ids for all students, or enter/edit individual student's Local Ids.</li>
			   <li>Select applicable Exit Reason for all Students or for each individual student.</li>
		</ol>
		<div style="margin-bottom:5px">
		    <span class="error_message ui-state-error trasferStudentDistrictValidation hidden validate" id="trasferStudentDistrictValidation">Please Select Destination District</span>
		  	<span class="error_message ui-state-error trasferStudentAttendaceSchoolError hidden validate" id="trasferStudentAttendaceSchoolError">Please Select Destination Attendance School To Proceed</span>
		  	<span class="error_message ui-state-error trasferStudentExitReasonError hidden validate" id="trasferStudentExitReasonError">Please Select Exit Reason To Proceed</span>
		</div>
		<div id="destinationDistrictGridCell" >
		 	<div id=destinationDistrictGridContainer class="kite-table">
		 		<table class="responsive" id="destinationDistrictByOrgTable"></table>
				<div id="pdestinationDistrictByOrgTable" style="width: auto;"></div>
		 	</div>
		</div>
		<div align="right" style="margin-right:8px">
			<input type="button" id="transferStudentsDestinationGridNext" value="Next"/>
		</div>
	
</div>
<div id="removeNotTransferredStudentFromDestinationViewDiv" class="form hidden"  >
	<div style="font-size: 17px; display:inline; color:#81a64c"><span>Remove any students not to be transferred to the listed destination school, then click Next:</span></div>
	<div><p><span class="warning" style="color:#e30707;">Warning!</span> Students already enrolled in the destination school, denoted by <img src="${pageContext.request.contextPath}/images/orange-flag.png" alt="Orange flag" width="30px" height="30px">, <u>will NOT be transferred</u>.</p></div>
	<div id="removeNotTransferredStudentFromDestinationGridCell" >
		 	<div id=removeNotTransferredStudentFromDestinationGridContainer class="kite-table">
		 		<table class="responsive" id="removeNotTransferredStudentFromDestinationTable"></table>
				<div id="pRemoveNotTransferredStudentFromDestinationTable" style="width: auto;"></div>
		 	</div>
	</div>
	<div align="right" style="margin-right:8px">
		<input type="button" id="transferStudentsConfirmGridNext" value="Next"/>
	</div>
</div>

<div id="transferDestinationConfirmationDiv" class="form hidden" >

		<div style="font-size: 17px; display:inline; color:#81a64c"><span>Please review before concluding the transfer.</span>
		</div>
		<div style="margin-left:30px; padding-right:10px;">
			<p>Listed students will be exited from the leaving school(s) and enrolled in the selected destination school(s). Students' general
				information, demographic and profile, as well as test records for the current year will be
				transferred to the destination school(s). 
			</p>
			<p style='margin-bottom:-10px'>For Each Student, the following table displays:</p>
			<ol class='transferDistrictStudentConfirmationList' style="margin-top: 1%;">
			   <li>The destination District, School, Accountability School and Local Id</li>
			   <li>The destination District and School Entry Dates</li>
			   <li>The Exit Reason Code and Date</li>
			</ol>
		</div>
		<div >
			<span style="display:inline; color:#81a64c">Click Yes to transfer students, or No to cancel: </span>
			<span style="margin-left: 460px"><input type="button" id="transferStudentsYes" value="Yes"/></span>
			<span align="right"><input type="button" id="transferStudentsNo" value="No"/></span>
		</div>
		<span class="error_message ui-state-error trasferStudentRecordsValidation hidden validate" id="trasferStudentRecordsValidation">Please Select At least One Student to Transfer</span>
		<div id="viewConfirmationGridCell" >
		 	<div id=viewConfirmationGridContainer class="kite-table">
		 		<table class="responsive" id="viewConfirmationGridTable"></table>
				<div id="pViewConfirmationGridTable" style="width: auto;"></div>
		 	</div>
		</div>

</div>


<div id="confirmDialogDeleteTransferStudent" title="">
      <p><span class="warning" style="color:#e30707;">Warning!</span>  Are you sure you want to remove student from the transfer list? </p>
</div>
<div id="confirmDialogCancelTransferStudent" title="">
      <p><span class="warning" style="color:#e30707;">Warning!</span>  Selected/entered information will not be saved and students will not be transferred</p>
      <p>Cancel transfer?</p>
  </div>  
  
<script type="text/javascript" 
	src="<c:url value='/js/configuration/transferStudents.js'/>"> </script>
<script type="text/javascript">
var transferStudentLoadOnce = false;
</script>