<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<c:set var="isDLM" value="${user.currentAssessmentProgramName == 'DLM'}" />

<style>
	.tr1 {
	background-color:gainsboro;
}
.tr2 {
	background-color:white;
}

#editOperationalTestWindowViewSetupDiv h4 {
    color: green;
    font-size: 22px;
    margin: 20px 0 10px;
    padding: 0;
}

#editOperationalTestWindowViewSetupDiv h5 {
	color :red;
	font-size: 14px;
}
h2.txt_header {
	border-bottom: 1px solid #aaa;
    color: #000;
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 20px;
    padding: 0 0 12px;
    text-transform: uppercase;
}

.form-left-side {
	float: left;
	width: 40%;
	margin:12px 0
}

.form-right-side {
	float: left;
	width: 55%;
	margin:12px 0
}

.clear {
	clear: both;
}

.section-1 .form-left-side label,
.section-2 .form-left-side label {
    color: #0099ff;
    display: block;
    padding-top: 10px;
    text-transform: uppercase;
    width: 100%;
    font-size: 14px;
}

.section-2 .ticket-cont div{
	display:inline-block; 
}

.section-2 .ticket-cont div.optionaligninsectiontwo{
  	width:192px !important;
}

.status-cont {
	padding: 15px 0;
	display: block;
}

.status-cont label {
    color: #0099ff;
    display: inline-block;
    padding: 10px 0;
    text-transform: uppercase;
    width: 120px;
    font-size: 14px;
}

.form-right-side .ticket-cont {
	padding: 15px 0;
	display: block;
	width: 100%;
}

.form-right-side .ticket-cont label{
   color: #0099ff;
    display: inline-block;
    padding: 0;
    text-transform: uppercase;
    width: 120px;
    font-size: 14px;
}

.section-3 .form-right-side .date-cont {
	padding: 0;
	display: block;
	width: 100%;
}

.section-3 .form-left-side .date-cont label,
.section-3 .form-right-side .date-cont label {
	color: #0099ff;
    display: inline-block;
    padding: 7px;
    text-transform: uppercase;
    width: 120px;
    font-size: 14px;
}

.status-cont {
	padding: 15px 0;
	display: block;
}

.status-cont label {
    color: #0099ff;
    display: inline-block;
    padding: 10px 0;
    text-transform: uppercase;
    width: 120px;
    font-size: 14px;
}

#gbox_editSelectTestGridTableId,
#editAddMoveInoperationalTestcollection,
#gbox_editRightSelectTestGridTableId {
	float: left;
}

#editAddMoveInoperationalTestcollection {
	width: 120px; 
	display: inline-block;
}

.button-group-new {
    clear: both;
    margin: 40px 0 20px;
    padding-left: 38%;
}

#editAddMoveInoperationalTestcollection {
    display: inline-block;
    margin-top: 15%;
    width: 120px;
}

#editAddMoveInoperationalTestcollection > div {
    background: #000 none repeat scroll 0 0;
    color: #fff;
    display: block;
    margin: 5px 8px;
    padding: 10px 0;
    text-align: center;
    width: 88%;
}

#editAddMoveInoperationalTestcollection > div:hover {
	cursor: pointer;
	background: #767676 none repeat scroll 0 0;
	color :#fff;
}

.ticket-cont > input#editGracetimeInMin {
    width: 70px;
}

.error-msg label {
	color: blue !important;
    width: 100% !important;
    font-size: 13px;
    font-weight: bold;
    font-family:"Courier New", Courier, monospace;
	}
.selectDiv {
	height: 60px;
	width: 100%;
}

.selectLeftCol {
	float: left;
	text-align: left;
	margin-left: 5px;
}

.selectRightCol {
	float: right;
	text-align: left;
	width: 280px;
	margin-right: 50px;
}

.bodyWrapper {
	width: 100%;
}

.bodyLeftCol {
	float: left;
	text-align: left;
	width: 200px;
	margin-left: 25px;
}

.bodyRightCol {
	float: right;
	text-align: left;
	width: 760px;
}

.note {
	font-family: "Courier";
	color: Blue;
	font-weight: bold;
}

.formSelect {
	overflow-x: scroll;
	overflow: -moz-scrollbars-horizontal;
	height: 180px;
	width: 620px;
	margin-left: 40px;
	border-right: solid 1px black;
}

.jqclock {
	float: right;
	text-align: center;
	border: 1px Black solid;
	background: #66CCFF;
	padding: 10px;
	margin: 5px;
}

.clockdate {
	color: DarkRed;
	margin-bottom: 10px;
	font-size: 16px;
	display: block;
}

.clocktime {
	border: 2px inset White;
	background: Black;
	padding: 5px;
	font-size: 20px;
	font-family: "Courier";
	color: LightGreen;
	margin: 2px;
	display: block;
}

.form-full-side {
	width: 100%;
	float: left;
	margin: 20px 0;
}

.form-full-side .group1, 
.form-full-side .group2,
.form-full-side .group3 {
	float: left;
	width: 33%;
}

.form-full-side .group1 label,
.form-full-side .group2 label  {
	color: #0099ff;
  display: block;
  font-size: 14px;
  padding: 10px 0;
  text-transform: uppercase;
  width: 100%;
}

.form-right-side.move-left {
	padding-left: 45px;
}

.value.width1 {
	width: 235px;
}

.form-right-side.full-width { width: 90% !important;}

.table-title1 {width: 55%; float: left;}
.table-title2 {width: 40%; float: left;}

.table-title1 label, .table-title2 label { 
color: #0099ff;
  display: block;
  font-size: 14px;
  padding: 10px 0;
  text-transform: uppercase;
  width: 100%;
  }
  
#SelctingoperationalTestcollectionForEdit .ui-state-default.ui-jqgrid-hdiv{
	width:405px !important;
	overflow: auto;
 }
#SelctingoperationalTestcollectionForEdit .ui-jqgrid .ui-jqgrid-hbox{
	padding-right:1px;
}
#editticketingdayon{
	margin-left: -30px;
}
#editticketingdayoff{
	margin-left: -70px;
}
#selectUpdateNumbeOfTestBySubjectContainer{
	float:right; 
	margin-right:100px;
}
.right.txt-edit {
    margin-top: -68px;
    cursor: pointer
}
</style>

<form id="editOperationalTestWindowViewSetup">
<div id="editOperationalTestWindowViewSetupDiv">
<div class="selectDiv">
<div class="selectLeftCol">
<br/>
<div id="editOperationalTestWindowErrorMessages" class="userMessages">
		<span class="error_message ui-state-error selectAllLabels hidden" id="editWindowNameCommonRequiredErrorMessage"></span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="editWindowNameRequiredErrorMessage">Enter window name.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="editWindowTicketingRequiredErrorMessage">Select Ticketing Method.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="editWindowTestEnrollmentMethodRequiredErrorMessage">Select Test Enrollment Method.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="editWindowInvalidDateErrorMessage">Please enter a valid start-end time value.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="editWindowScoringAssignmentMethodRequiredErrorMessage">Select Scoring Assignment Method.</span>
        <span class="error_message ui-state-error selectAllLabels hidden" id="editManagedByRequiredErrorMessage">Select Managed By.</span>
        <span class="error_message ui-state-error selectAllLabels hidden" id="editRandomizationRequiredErrorMessage">Select Randomization.</span>
        <span class="error_message ui-state-error selectAllLabels hidden" id="editBeginDateErrorMessage">Begin date/time should  be greater than current date/time.</span>
        <span class="error_message ui-state-error selectAllLabels hidden" id="editEndDateErrorMessage">End date/time should not be less than current date/time.</span>
   		<span class="info_message ui-state-highlight successMessage hidden" id="editSuccessMessage" >Operational Test Window Updated Successfully</span>
   		<span class="info_message ui-state-highlight successMessage hidden" id="copySuccessMessage" >Operational Test Window Copied Successfully</span>
   		<span class="error_message ui-state-error selectAllLabels hidden" id="editTestCollectionRequiredErrorMessage" >Please Select Test Collection</span>
   		<span class="error_message ui-state-error selectAllLabels hidden" id="errorEditNumberOfTestWholeNumber" >Please enter only a whole number in the number of tests field (Within 0 - 99).</span>
   	    <span class="error_message ui-state-error selectAllLabels hidden" id="editWindowInvalidDACTimeErrorMessage">Please enter Daily Access Codes valid start-end time value.</span>
</div>
</div>
</div>
<div>
	<h2 class="txt_header">Window :</h2>
	<security:authorize access="hasRole('PERM_OTW_EDIT')">
		<span id="editTestCollectionEditwindow" class="right txt-edit">
		<div id="changeEditMode" class="" style="background-color:#0e76bc; width:70px; height: 20px; padding: 12px 20px; text-align:center; margin-left:-120px; float: left;"><span class='panel_btn' style="color: white; text-decoration: none;">Edit</span></div>
		<div id="changeCopyMode" class="" style="background-color:#0e76bc; width:70px; height: 20px; padding: 12px 20px; text-align: center"><span class='panel_btn' style="color: white; text-decoration: none;">Copy</span></div>
		</span>
	</security:authorize>
</div>
<div id="OpTestWindowSetupMakereadableDiv"></div>
<div id="OpTestWindowSetupContentDiv">
<div class="section-1">
	<div id="editwindow">
		<div class="form-left-side">
			<div id="editAssessmentProgramsAddNewTestWindowDiv" class="form-fields">
				<label for="assessmentProgramsAddNewTestWindowLabel" >Assessment Program:<span class="lbl-required">*</span></label> 
				<!-- <select id="editAssessmentProgramsAddNewTestWindowSelect" name="editAssessmentProgramsAddNewTestWindowSelect" class="bcg_select required">
					<option value="">Select</option>
				</select> -->
				<input type="text" id="editAssessmentProgramsAddNewTestWindowSelect" name="editAssessmentProgramsAddNewTestWindowSelect"  class="input-large" maxlength="60" />
				<input type="text" id="editAssessmentProgramsAddNewTestWindowSelectText" name="editAssessmentProgramsAddNewTestWindowSelectText"  class="input-large" maxlength="60" title='editAssessmentProgramsAddNewTestWindowSelectText'/>
			</div>
			
			<div class="group1">
				<label for="editStatebyTestWindowSelect" class="field-label isrequired">State<span class="lbl-required"></span></label>
				
				<div class="form-fields">
					<select id="editStatebyTestWindowSelect" title="State" class="bcg_select required" multiple="multiple">
					</select> 
				</div>
			</div>
				<label  class="isrequired field-label">WINDOW NAME<span class="lbl-required">*</span></label>
				 <input type="text" id="editWindowName" name="editWindowName"  class="input-large" maxlength="60" title='Edit Window Name'/>
			
		</div>
		<div  class="form-right-side" style="width: 25%;">
			<div class="status-cont" style="margin-top: 20px;">
				<label class="isrequired field-label">STATUS : </label> 
				<span id="status_otw"></span>
			</div>
			<div class="status-cont" style="margin-top:15px;">
				<label for="editWindowsuspend" class="isrequired field-label"> SUSPEND : </label>
				<input id="editWindowsuspend" type="checkbox" name="editWindowSuspend"  /> 
			</div>
			<div id="opt_windowIdDiv" class="status-cont" style="margin-top:15px;">
				<label class="isrequired field-label"> WINDOW ID : </label>
				<span id="otw_id"></span> 
			</div>  
		</div>
		
		
		<div class="selectRightCol">
			<br>
			<label class="rightMargin">Current System Datetime</label>
			<br> <label	class="rightMargin" style="margin-right: 50px">CST (Kansas City)</label>
			<br> <label id="clockEditOprWind"></label>
			<br>

		</div>
		<div class="clear"></div>
	</div>
</div>
<div class="section-2">
	<h2 class="txt_header">ADMIN OPTIONS :</h2>
			<div  class="form-right-side full-width">
				<div class="ticket-cont">
     				<label  class="field-label isrequired">Authentication Method :</label>
     				<div class="optionaligninsectiontwo"><input type="radio" id="editTicketingOff"  class="editticketing" name="editticketing" value="false" checked title='None'><span class="value"> None </span></div>
     				<div><input type="radio" id="editTicketingOn"  class="editticketing" name="editticketing" value="true" title='Tickets'><span class="value" > Tickets </span></div>
     				<div><input type="radio" id="editDailyAccessCode"  class="editticketing" name="editticketing" value="DAILY_ACCESS_CODE" title='Daily Access Codes'><span class="value" > Daily Access Codes </span></div>
				<div id="editTicketingoftheday" class="form-fields" style="margin-left: 0px; width: 200px;display: none;">
							<label  class="field-label isrequired" style="width: 205px">TICKETING MEHTOD :<span class="lbl-required">*</span></label>
							<select id="editTicketingDaySelect" name="editTicketingDaySelect" class="bcg_select">
								<option value="">Select</option>
					 			<option value="TICKET_OF_THE_DAY">TICKET OF THE DAY</option>
						 		<option value="TICKETED_AT_SECTION">TICKETED AT SECTION</option>
							</select> 
				</div>  
				</div>
				
				<div class="ticket-cont">
					<label  class="field-label isrequired">TEST EXIT :</label>
					<div class="optionaligninsectiontwo"><input type="radio" id="editTestExitId1" name="editTestExitId" value="true" checked title="Complete Test"><span class="value"> Complete Test </span></div>
					<div><input type="radio" id="editTestExitId2" name="editTestExitId" value="false" title="Not Required To Complete Test"><span class="value width1"> Not Required To Complete Test </span></div>					
				</div>
				
				<div class="ticket-cont">
					<label  class="field-label isrequired">GRACE PERIOD :</label>
					<div class="optionaligninsectiontwo"><input type="radio" id="editGraceperoidOn" name="editGraceperoid" value="true" title="GRACE PERIOD  On"><span class="value" > On </span></div>
					<div><input type="radio" id="editGraceperoidOff" name="editGraceperoid" value="false" checked title="GRACE PERIOD  Off"><span class="value"> Off </span>
					<input id="editGracetimeInMin" type="number" value="mins" min="0" max="120" step="30" title="GRACE PERIOD  In Minutes" /><span class="value"> In Minutes </span></div>
				</div>
				<div class="ticket-cont">
					<label  class="field-label isrequired">AUTO-ENROLLMENT:</label>
					<div class="optionaligninsectiontwo"><input type="radio" id="editTestEnrollmentOn"  class="editTestEnrollment" name="editTestEnrollment" value="true" title="AUTO-ENROLLMENT ON"><span class="value"> On </span></div>
					<div><input type="radio" id="editTestEnrollemntOff" class="editTestEnrollment" name="editTestEnrollment" value="false" checked title="AUTO-ENROLLMENT Off"><span class="value"> Off </span></div>
					<div class="editTestEnrollmentDiv" style="margin-left: 0px; width: 200px; display:none;">
						<label  class="field-label isrequired" style="width: 220px">TEST ENROLLMENT METHOD :<span class="lbl-required">*</span></label>
						<select id="editTestEnrollmentMethod" name="editTestEnrollmentMethod" class="bcg_select">
							<option value="">Select</option>
						</select>
					</div>
				</div>
				<c:if test="${user.currentAssessmentProgramName == 'DLM'}">
					<div class="ticket-cont">
						<div class="instructionAndAssessmentPlannerWindowYesNo">
							<label class="field-label isrequired">INSTRUCTION AND ASSESSMENT PLANNER:</label>
							<div class="optionaligninsectiontwo">
								<input type="radio" id="instructionalPlannerWindowOn_edit" class="editIAPWindow" value="true" name="instructionalPlannerWindow" data-process-type="edit" title="Instruction And Assessment Planner On">
								<span class="value">On</span>
							</div>
							<div>
								<input type="radio" id="instructionalPlannerWindowOff_edit" class="editIAPWindow" value="false" name="instructionalPlannerWindow" data-process-type="edit" title="Instruction And Assessment Planner Off" checked>
								<span class="value">Off</span>
							</div>
						</div>
						<div id="iapWindow_edit" style="display:none;">
							<label class="field-label isrequired">WINDOW DISPLAY NAME:</label>
							<input type="text" id="instructionalPlannerDisplayName_edit" name="instructionalPlannerDisplayName" placeholder="Display Name"/>
						</div>
					</div>
				</c:if>
				<div class="ticket-cont">
					<label  class="field-label isrequired">SCORING WINDOW:</label>
					<div class="optionaligninsectiontwo">
						<input type="radio" id="editScoringWindowOn"  class="editScoringWindow" value="true" name="editScoringWindow" title="SCORING WINDOW On">
						<span class="value"> On </span>
					</div>
					<div><input type="radio" id="editScoringWindowOff"  class="editScoringWindow" value="false" name="editScoringWindow" checked title="SCORING WINDOW Off">
						<span class="value"> Off </span>
					</div>				
					<div class="editScoringWindowMethoddiv" style="margin-left: 0px; width: 200px; display:none;">
						<label  class="field-label" style="width: 220px">SCORING ASSIGNMENT METHOD:<span class="lbl-required">*</span></label>
						<select id="editScoringWindowMethod" name="editScoringWindowMethod" class="bcg_select">
							<option value="">Select</option>
							<!-- <option value="manual">Manual</option>
		 					<option value="auto">Auto Assignment By Roster</option> -->
						</select>
					</div>
				</div>
				
				
				<div id='selectUpdateNumbeOfTestBySubjectContainer'>
					<div id='selectUpdateNumbeOfTestBySubjectGrid'>
						<table id="selectUpdateNumbeOfTestBySubject"></table>
						<div id="selectUpdateNumbeOfTestBySubjectPager"></div>
					</div>
				</div> 
				
				<!-- <div id='selectNumbeOfTestBySubjectContainer'>
					<div id='selectNumbeOfTestBySubjectGrid'>
						<table id="selectNumbeOfTestBySubject"></table>
						<div id="selectNumbeOfTestBySubjectPager"></div>
					</div>
				</div> -->
			</div>
			<div class="clear"></div>
</div>
<div class="section-3"> 
	<h2 class="txt_header">DATES :</h2>
<div>
<div>	
  <label>Local Timeframe Testing Available</label>
</div>
	<div class="form-left-side">
		<div class="date-cont">
			 <span id="editStartDateSpan">
			<label for="startDateLabel" class="field-label isrequired">START DATE:<span class="lbl-required">*</span></label>
			<input id="editTestWindowStartDate" class="input-large"  name="editTestWindowStartDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/>
			</span>
		</div>
		<div class="date-cont">
			<span id="editStartTimeSpan"> <label> START TIME :</label>
				<input type="text" id="editTestWindowStartTime" class="input-large" title='Edit Test Window Start Time'/> 
				<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label></div>
			</span>
		</div>
	</div>
	<div class="form-right-side move-left">
		<div class="date-cont">
		<span id="editEndDateSpan"> <label>END DATE :<span class="lbl-required">*</span></label>
			<input type="text" id="editTestWindowEndDate" class="input-large"  placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/> 
		</span>
		</div>
		<div class="date-cont">
		<span id="editEndTimeSpan"> <label>END TIME :</label>
			<input type="text" id="editTestWindowEndTime" class="input-large" title='Edit Test Window End Time'/>
			<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label> </div>
		</span>
		</div>
	</div>
	</div>
	<div id ="editDailyAccessCodeTimeFrameDiv" class="hidden" >
  <div>	
  <label>Local Timeframe Daily Access Codes Usable by Students:</label>
  </div>
 <div class="form-left-side">
   
  <div class="date-cont">
   <span id="editDailyAccessCodeStartTimeSpan"> <label> START TIME :<span class="lbl-required">*</span></label>
    <input type="text" id="editDailyAccessCodeTestwindowstartTime" class="input-large" title="Start Time" /> 
    <div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label></div>
   </span>
  </div>
 </div>
 <div class="form-right-side move-left">
  
  <div class="date-cont">
  <span id="editDailyAccessCodeEndTimeSpan"> <label>END TIME :<span class="lbl-required">*</span></label>
   <input type="text" id="editDailyAccessCodeTestwindowendTime" class="input-large" title="End Time"   />
   <div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label> </div>
  </span>
  </div>
 </div>
 </div>
 
<div id="editScoringAvailableTimeFrameDiv" style="display:none;">
	<div>	
  		<label>Local Timeframe Scoring Available</label>
  	</div>
	<div class="form-left-side">
		<div class="date-cont">
			<span id="startDateSpanForEditTestwindowScoring">
				<label for="startDateLabel" class="field-label isrequired">START DATE:<span class="lbl-required">*</span></label>
				<input id="editTestwindowScoringStartDate" class="input-large"  name="editTestwindowScoringStartDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/>
			</span>
		</div>
		<div class="date-cont">
			<span id="startTimeSpanForEditTestwindowScoring"> <label> START TIME :</label>
				<input type="text" id="editTestWindowScoringStartTime" class="input-large" /> 
				<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label></div>
			</span>
		</div>
	</div>
	<div class="form-right-side move-left">
		<div class="date-cont">
		<span id="endDateSpanEditTestwindowScoring"> <label>END DATE :<span class="lbl-required">*</span></label>
			<input type="text" id="editTestWindowScoringEndDate" class="input-large"  placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/> 
		</span>
		</div>
		<div class="date-cont">
		<span id="endTimeSpanEditTestwindowScoring"> <label>END TIME :</label>
			<input type="text" id="editTestWindowScoringEndTime" class="input-large"   />
			<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label> </div>
		</span>
		</div>
	</div>
</div>
 
 
	<div class="clear"></div>
</div>
<div class="section-4">
	<h2 class="txt_header">SELECT TEST COLLECTIONS :</h2>
	
	<div  class="form-full-side">
			<div class="group1">
				<label  class="field-label isrequired">MANAGED BY<span class="lbl-required">*</span></label>
				
				<div class="form-fields">
							<select id="editManagedbyCodeSelect" class="bcg_select required">
								<option value="">Select</option>
					 			<option value="MANUAL_DEFINED_ENROLLMENT_TO_TEST">Manual</option>
						 		<option value="SYSTEM_DEFINED_ENROLLMENT_TO_TEST">System</option>
							</select> 
				</div>
			</div>
			<div class="group2">
				<label  class="field-label isrequired">RANDOMIZATION<span class="lbl-required">*</span></label>
							<select id="editrandomizedCodeSelect" class="bcg_select required">
								<option value="">Select</option>
					 			<option value="login">At Login</option>
						 		<option value="enrollment">Enrollment</option>
							</select> 
							<input type="hidden" id="actualRandomizationValue" />
							<input type="hidden" id="actualManagedByCode" />
			</div>
			<div class="group3 search">
					<button type="button" class="search_btn" id="editSearchTestCollectionBtn">Search</button>
			</div>
			<div class="clear"></div>
	</div>
	<div class="table-data-section">
		<div class="table-title1"><label  class="field-label isrequired">Available Collections:<span class="lbl-required">*</span></label></div>
		<div class="table-title2"><label  class="field-label isrequired">Assigned Collections:<span class="lbl-required">*</span></label></div>
		<div class="clear"></div>
		<div id='overlappingSelctingoperationalTestcollection'></div>
		<div id="SelctingoperationalTestcollectionForEdit">
			<div id="editSelectTestGridContainer" class="editParent">
				<table id="editSelectTestGridTableId"></table>
				<!-- <div id="editSelectTestGridPager"></div> -->
				<div id="peditSelectTestGridTableId" class="responsive"></div>
			</div>
				<div id=editAddMoveInoperationalTestcollection>
					<div id="editAddInoperationalTestcollection">Add In &gt;&gt;</div>
					<div id="editMoveOutoperationalTestcollection">&lt;&lt; Move Out</div>
				</div>
			
			<div id="editRightSelectTestContainer" class="editParent">
				<table id="editRightSelectTestGridTableId"></table>
				<!-- <div id="editRightSelectTestGridPager"></div> -->
				<div id="peditRightSelectTestGridTableId" class="responsive"></div>
			</div>	
			
		</div>
	</div>
</div>
</div>
<div class="button-group-new">
	<button type="button" id="editOperationalTestWindowSetupsave">Save</button>
	<button type="button" id="editOperationalTestWindowSetupcancel">Reset</button>
</div>
<input type="text" id="hdnWindowId" class="hidden" value=""/>
</div>
</form>
<%--
</body>
</html>
--%>

<script>
	var isDLM = ${user.currentAssessmentProgramName == 'DLM'};
</script>