<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <link href="css/message.css" rel="stylesheet">
    <%@ include file="/jsp/include.jsp"%> 
<script type="text/javascript" src="<c:url value='/js/configuration/auditHistory.js'/>"> </script>
<div id="toolsauditHistoryFilter">
<form id="toolsauditHistoryForm" name="toolsauditHistoryFilterForm" class="form">

  <table>
			<tr>
				<td>
					<div class="form-fields" id="auditHistoryContainer" style=" margin-bottom: -7%; margin-left: 11%;">
						<label for="auditHistory" class="field-label required" style="margin-left: -15%;" >Audit Tables: <span class="lbl-required">*</span></label>
						 <select id="auditHistory" class="bcg_select required" name="auditHistory" style="width: 256px;">
							<option  value="0" >Select</option>
						</select>
					</div>
				</td>
			</tr>
		    
  </table>			

</form>
</div>
<div class="full_main">
	<div id="viewAuditHistoryGridCell">
		<!--AMP Changes-->
		<!-- <div id="auditHistory" hidden="hidden"></div> -->
	 	<div id=auditHistoryGridContainer class="kite-table">
	 		<table class="responsive" id="auditHistoryByOrgTable"></table>
			<div id="pauditHistoryByOrgTable" style="width: auto;"></div>
	 	</div>
	</div>
</div>

<div class="calendarAuditHistory" style="display: none; margin-left: 2%;">

	<span style="font-size: 15px; color: #434343;">Start Date</span> <input readonly="readonly"
		type="text" name="startDate" id="startDate" title="Start Date"
		class="calendarAuditHistory-dropdown"
		style="background-image: url(images/date-icons.png); background-repeat: no-repeat; background-position: right center;" />
	<span style="margin-left: 8%; font-size: 15px; color: #434343; padding-right: 10px">Start
		Time </span> <input type="text" name="startTime" id="startTime" title="Start Time"
		class="calendarAuditHistory-dropdown"
		style="background-image: url(images/time-icons.png); background-repeat: no-repeat; background-position: right center;" />
	<br> <br> <span style="font-size: 15px; color: #434343; margin-left: -1px;">End
		Date </span> <input type="text" name="endDate" id="endDate" title="End Date" readonly="readonly"
		class="calendarAuditHistory-dropdown"
		style="margin-left:4%; background-image: url(images/date-icons.png); background-repeat: no-repeat; background-position: right center;" />
	<span style="margin-left: 8%; font-size: 15px; color: #434343;">End Time</span> <input
		type="text" name="endTime" id="endTime" title="End Time"
		class="calendarAuditHistory-dropdown"
		style="background-image: url(images/time-icons.png); background-repeat: no-repeat; background-position: right center;" />	
</div>
<hr style="margin-top: 3%;margin-bottom: 2%; width: 100%; height: 2px;" class="extraMidFilterSection organizationIdclass">
<div class="extraMidFilterSection" style="display: none; margin-left: 2%;">
<div style="color: hsl(204, 86%, 40%);font-size: 15px;margin-left: -2%; margin-top: -1%;" class="extraMidFilterSection organizationIdclass">Enter an Organization ID or use the other filters to make selection.</div>
<div style="color: hsl(204, 86%, 40%);font-size: 15px;margin-left: -2%; margin-top: 0.6%;" class="extraMidFilterSection organizationIdclass">NOTE: If no filters are selected, the report may take some time to generate.</div>
<div style="color: hsl(204, 86%, 40%);font-size: 15px;margin-left: -2%; margin-top: 4%;" class="userAccountClass">Enter EITHER an Educator ID or email address.</div>
<div style="color: hsl(204, 86%, 40%);font-size: 15px;margin-left: -2%; margin-top: 2.5%;" class="rosterClass">Enter one Educator ID or use the other filters to make selection.</div>
<div style="color: hsl(204, 86%, 40%);font-size: 15px;margin-left: -2%; margin-top: 0.6%;" class="rosterClass">NOTE: If no filters are selected, the report may take some time to generate.</div>

<div id="mainMidFilter" style="margin-top: 3%;">
<span for="organizationId" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: -0.5%;margin-right: 3%;" class="midFilter organizationIdclass" id="organizationIdSpan">ORG ID #: </span>
<input type="text"  id="organizationId" style="height: 25px;width:20%;margin-right: 3%;" class="midFilter organizationIdclass" name="organizationId" value="" title="organizationId"/> 

<span for="stateStudentId" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: -0.5%;margin-right: 2%;" class="midFilter stateStudentIdclass" id="stateStudentIdSpan">STATE STUDENT ID:<span class="lbl-required">*</span> </span>
<input type="text"  id="stateStudentId" style="height: 25px;width:17%;margin-right: 1%;" class="midFilter stateStudentIdclass" name="stateStudentId" value="" title="stateStudentId"/> 

<span for="educatorId" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: 1.5%;margin-right: 2%;" class="midFilter userAccountClass" id="educatorIdSpan">EDUCATOR ID: </span>
<input type="text"  id="educatorId" style="height: 25px;width:17%;margin-right: 1%;" class="midFilter userAccountClass" name="educatorId" value="" title="educatorId"/> 
<div style="color: hsl(0, 0%, 26%);font-size: 15px;margin: -1% 2% -1% 11.5%;" class="midFilter userAccountClass">OR</div>
<span for="emailAddressId" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: -0.5%;margin-right: 2%;" class="midFilter userAccountClass" id="emailAddressSpan">EMAIL ADDRESS: </span>
<input type="text"  id="emailAddressId" style="height: 25px;width:17%;margin-right: 1%;" class="midFilter userAccountClass" name="emailAddressId" value="" title="emailAddressId"/> 
<span for="rosterEducatorId" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: 1.5%;margin-right: 2%;" class="midFilter rosterClass" id="rosterEducatorIdSpan">EDUCATOR ID: </span>
<input type="text"  id="rosterEducatorId" style="height: 25px;width:17%;margin-right: 1%;" class="midFilter rosterClass" name="rosterEducatorId" value="" title="rosterEducatorId"/> 

	<span> <a class="panel_btn viewAuditHistoryDetailsButton" href="#"
			id="viewAuditHistoryDetailsTwo"  style="margin-left: 1% ;margin-top:1%" >Generate Report</a> 
				<a class="linkAuditHistoryDetails"
			href="downloadAuditHistory.htm" id="linkAuditHistoryDetailsTwo"
			style="margin-left: 5px; margin-top: -37px;">Download Report</a>
	</span>

	
</div><br/>
<div class="form-fields rosterClass" style="display: none; margin-top:-1.5%;" id="contentAreaDiv">
			<span for="contentArea" class="rosterClass" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: -0.3%;margin-right: 2%;" id="contentAreaSpan">CONTENT AREA:</span>
				<select name="contentArea"  class="bcg_select" style="margin-top: 1%;" id="contentArea">
					<option value="">Select</option>
				</select>
</div>
</div>
<hr style="margin-top: 3%;margin-bottom: 2%; width: 100%; height: 2px;" class="extraFilterSection">
<div class="extraFilterSection" style="display: none; margin-left: 2%;">
	<div Style="font-size:15px;margin-left: -2%;margin-bottom: 2%;" class="extraFilterSection">FILTER BY:</div>
	<div style="color: hsl(204, 86%, 40%);font-size: 15px;margin-left: -2%; margin-top: -1%;" class="extraFilterSection">Select one or more filters, as needed.</div>
	<div class="form-fields"style="display: none;margin-left: 10px;" id="auditAssessmentProgramDiv">
		<span for="auditAssessmentProgram" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-top: 1%; margin-left: 0.3%;margin-right: 0.3%;">ASSESSMENT PROGRAM:</span>
			<select name="auditAssessmentProgram" class="bcg_select" style="margin-top: 1%;" id="auditAssessmentProgram">
				<option value="">Select</option>
				</select>
	</div>
	<br/>	
	<div class="form-fields" style="display: none; " id="auditStateDiv">
			<span for="auditState" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: 1%;margin-right: 0.3%;" id="auditStateSpan">STATE:</span>
				<select name="auditState"  class="bcg_select" style="margin-top: 1%;" id="auditState">
					<option value="">Select</option>
				</select>
	</div>
	
	<br/>
	<div class="form-fields" style="display: none;" id="auditRoleDiv">
			<span for="auditRole" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: 14.5%;margin-right: 1%;" id="auditRoleSpan">ROLE:</span>
				<select name="auditRole"  class="bcg_select" style="margin-top: 1%;width: 350px" id="auditRole">
					<option value="">Select</option>
				</select>
	</div>
	
	<div class="form-fields" style="display: none; " id="auditDistrictDiv">
			<span for="auditDistrict" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: 1%;margin-right: 1%;" id="auditDistrictSpan">DISTRICT:</span>
				<select name="auditDistrict"  class="bcg_select" style="margin-top: 1%;" id="auditDistrict">
					<option value="">Select</option>
				</select>
	</div>
	<br/>
	<div class="form-fields" style="display: none;" id="auditSchoolDiv">
			<span for="auditSchool" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left: 1%;margin-right: 1%;" id="auditSchoolSpan">SCHOOL:</span>
				<select name="auditSchool"  class="bcg_select" style="margin-top: 1%;" id="auditSchool">
					<option value="">Select</option>
				</select>
				<div class="rosterClass"  style="display: none;"></div><br/>
				<span for="contentAreaBottom" class="rosterClass" style="color: hsl(0, 0%, 26%);font-size: 15px;margin-left:-0.5%;margin-right: 1%;" id="contentAreaBottomSpan">CONTENT AREA:</span>
				<select name="contentAreaBottom"  class="bcg_select rosterClass" style="margin-top: 1%;" id="contentAreaBottom">
					<option value="">Select</option>
				</select><br/>
				<span>
					<a class="panel_btn viewAuditHistoryDetailsButton" href="#" id="viewAuditHistoryDetailsOne"
				style="margin-left: 2.3%;" >Generate Report</a>
				<a class="linkAuditHistoryDetails" href="downloadAuditHistory.htm"
				id="linkAuditHistoryDetailsOne"
				style="margin-left: 5px; margin-top: -37px;">Download Report</a></span>
	</div>
</div>
<br/>
<div id="viewAuditHistoryDetailsButtomDiv" style="display: none;">
	<a class="panel_btn viewAuditHistoryDetailsButton" href="#" id="viewAuditHistoryDetails"
		style="margin-left: 24.5%;">Generate Report</a>
	<a class="linkAuditHistoryDetails" href="downloadAuditHistory.htm"
		id="linkAuditHistoryDetails"
		style="margin-left: 5px; margin-top: -37px;">Download Report</a>
</div>
<div id="overSizeWarningMsg"  style="margin-left: 10%;"></div>
<script>
	$(function() {
		getAuditTables();
		viewAuditHistoryInit();
		
	});
</script>