<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>  

<html lang='en'>
<body>
<!-- CSS goes in the document HEAD or added to your external stylesheet -->
<style>
	.tr1 {
	background-color:gainsboro;
}
.tr2 {
	background-color:white;
}

#OperationalTestWindowViewSetup h4 {
    color: green;
    font-size: 22px;
    margin: 20px 0 10px;
    padding: 0;
}

#OperationalTestWindowViewSetup h5 {
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
  	width:180px;
  	margin-left:15px;
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

#gbox_selectTestGridTableId,
#AddMoveInoperationalTestcollection,
#gbox_SelectedCollectionId {
	float: left;
}

#AddMoveInoperationalTestcollection {
	width: 120px; 
	display: inline-block;
}

.button-group-new {
    clear: both;
    margin: 40px 0 20px;
    padding-left: 38%;
}


#AddMoveInoperationalTestcollection {
    display: inline-block;
    margin-top: 15%;
    width: 120px;
}

#AddMoveInoperationalTestcollection > div {
    background: #000 none repeat scroll 0 0;
    color: #fff;
    display: block;
    margin: 5px 8px;
    padding: 10px 0;
    text-align: center;
    width: 88%;
}

#AddMoveInoperationalTestcollection > div:hover {
	cursor: pointer;
	background: #767676 none repeat scroll 0 0;
	color :#fff;
}

.button-group-new button {
     background: url("./images/btn-bg.png") no-repeat scroll center center #0e76bc; 
  border: 0 none;
  border-radius: 4px;
  color: white;
  display: inline-block;
  font-size: 1em;
  font-weight: 300;
  line-height: 20px;
  margin: 30px 10px 10px;
  padding: 10px 20px;
  text-decoration: none;
  transition: all 0.3s ease-in-out 0s;
}

.button-group-new button:hover {
	/*background: #86d8ff none repeat scroll 0 0;
    color: #fff;*/
      background: none repeat scroll 0 0 #0e76bc;
}
    
.right.txt-edit {
    margin-top: -48px;
}

.ticket-cont .value {
    display: inline-block;
    padding: 0 5px;
    width: 153px;
}

.ticket-cont > input#gracetimeInMin {
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
	width:100%;
	margin-bottom:10px;
}

.selectRightCol {
	float: right;
	text-align: left;
	width: 280px;
	margin-right: 30px;
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

.form-full-side .group3 .search_btn {
   background: url("./images/btn-bg.png") no-repeat scroll center center #0e76bc; 
  border: 0 none;
  border-radius: 4px;
  color: white;
  display: inline-block;
  font-size: 1em;
  font-weight: 300;
  line-height: 20px;
  margin: 30px 10px 10px;
  padding: 10px 20px;
  text-decoration: none;
  transition: all 0.3s ease-in-out 0s;
}

.form-full-side .group3 .search_btn:hover {
  background: none repeat scroll 0 0 #0e76bc;
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
 #SelctingoperationalTestcollection .ui-state-default.ui-jqgrid-hdiv{
	/* width:405px !important; */
	overflow: auto;
 }
#SelctingoperationalTestcollection .ui-jqgrid .ui-jqgrid-hbox{
	padding-right:0px;
}
#ticketingdayon{
	margin-left: -30px;
}
#ticketingdayoff{
	margin-left: -70px;
}
#selectNumbeOfTestBySubjectContainer{
	float:right; 
	margin-right:100px;
}
#selectTestGridTableId .ui-jqgrid tr.jqgrow td{
   overflow:auto !important;
}
#TestCollectionEditwindow {
    color: #0254eb !important;
}
</style>
<input id ="columnChooserGrid" value ="true" type="hidden" class="hidden" />	
<div id="OperationalTestWindowViewSetup">
<div class="selectDiv">
<div class="selectLeftCol">
<!-- <h5 style="display:none">Test Collection ID </h5><h5 style="display:none"> has an existing window with overlapping</h5> -->
<br />
<div id="newOperationalTestWindowErrorMessages" class="userMessages">
		<span class="error_message ui-state-error selectAllLabels hidden" id="newWindowNameCommonRequiredErrorMessage"></span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newWindowNameRequiredErrorMessage">Enter window name.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newWindowTicketingRequiredErrorMessage">Select Ticketing Method.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newWindowTestEnrollmentMethodRequiredErrorMessage">Select Test Enrollment Method.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newWindowScoringAssignmentMethodRequiredErrorMessage">Select Scoring Assignment Method.</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newWindowInvalidDateErrorMessage">Please enter a valid start-end time value.</span>
        <span class="error_message ui-state-error selectAllLabels hidden" id="newManagedByRequiredErrorMessage">Select Managed By.</span>
        <span class="error_message ui-state-error selectAllLabels hidden" id="newBeginDateErrorMessage">Begin date/time should  be greater than current date/time.</span>
        <span class="error_message ui-state-error selectAllLabels hidden" id="newEndDateErrorMessage">End date/time should not be less than current date/time.</span>
		<span class="info_message ui-state-highlight successMessage hidden" id="newSuccessMessage" >Operational Test Window Created Successfully</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newTestCollectionRequiredErrorMessage" >Please Select Test Collection</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newRandomizationErrorMessage" >Select Randomization</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newAssessProgramErrorMessage" >Select Assessment Program</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="errorNumberOfTestWholeNumber" >Please enter only a whole number in the number of tests field (Within 0 - 99)</span>
		<span class="error_message ui-state-error selectAllLabels hidden" id="newWindowInvalidDACTimeErrorMessage">Please enter Daily Access Codes valid start-end time value.</span>
</div>
</div>
</div>
<div class="section-1">
	<h2  class="txt_header">Window :</h2>
	<span id="TestCollectionEditwindow" class="right txt-edit"><a>Create Mode</a></span>
	
	<div id="window">
		<div class="form-left-side">
			<div id="assessmentProgramsAddNewTestWindowDiv" class="form-fields">
			
				<label for="assessmentProgramsAddNewTestWindowSelect">Assessment Program:<span class="lbl-required">*</span></label> 
				<select id="assessmentProgramsAddNewTestWindowSelect" title="Assessment Program" name="assessmentProgramsAddNewTestWindowSelect" class="bcg_select">
					<option value="">Select</option>

				</select>
			</div>
			
			<div class='orgFilterStateTestWindowDiv'>		
			<label for="orgFilterStateIdTestWindowSelect" class="isrequired field-label">State<span class="lbl-required"></span></label>
				<select id='orgFilterStateIdTestWindowSelect' title="State" name="orgFilterStateIdTestWindowSelect" class="bcg_select" multiple="multiple">
				</select>
				<br>
			</div>
			
				<label  for="windowName" class="isrequired field-label">WINDOW NAME<span class="lbl-required">*</span></label>
				 <input type="text" id="windowName" name="windowName"  class="input-large" maxlength="60" />
			
		</div>
		<div  class="form-right-side" style="width: 20%;">
			<div class="status-cont" style="margin-top: 25px;">
				<label class="isrequired field-label">STATUS : </label> BEGIN
			</div>
			<div class="status-cont" style="margin-top:10px;">
				<label for="windowsuspend" class="isrequired field-label"> SUSPEND : </label>
				<input id="windowsuspend" type="checkbox" name="suspend"  /> 
			</div> 
		</div>
		
		<div class="selectRightCol">
			<br>
			<label class="rightMargin"><fmt:message
					key="label.operTestWindow.currentDaetime" /></label><br> <label
				class="rightMargin" style="margin-right: 50px"><fmt:message
					key="label.operTestWindow.timeZone" /></label><br> <label id="clockNewOprWind"></label>
			

		</div>
		<div class="clear"></div>
	</div>
</div>

<div class="section-2">
	<h2 class="txt_header">ADMIN OPTIONS :</h2>
			<div  class="form-right-side full-width">
				<div class="ticket-cont">
     				<label  class="field-label isrequired">Authentication Method :</label>
     				<div class="optionaligninsectiontwo"><input type="radio" id="ticketingoff"  class="ticketing" name="ticketing" value="false" checked title="None"><span class="value"> None </span></div>
     				<div><input type="radio" id="ticketingon" title="Tickets"  class="ticketing" name="ticketing" value="true"><span class="value"> Tickets </span></div>
     				<div><input type="radio" id="dailyAccessCode"  class="ticketing" name="ticketing" title="Daily Access Codes" value="DAILY_ACCESS_CODE" ><span class="value"> Daily Access Codes </span></div>
	
				<div id="ticketingoftheday" class="form-fields" style="margin-left: 0px; width: 200px; display:none;">
							<label for="ticketingDaySelect" class="field-label" style="width: 205px">TICKETING METHOD :<span class="lbl-required">*</span></label>
							<select id="ticketingDaySelect" title="Ticketing Method" name="ticketingDaySelect" class="bcg_select">
								<option value="">Select</option>
					 			<option value="TICKET_OF_THE_DAY">TICKET OF THE DAY</option>
						 		<option value="TICKETED_AT_SECTION">TICKETED AT SECTION</option>
							</select> 
				</div>  
				</div>
				
				<div class="ticket-cont">
					<label  class="field-label isrequired">TEST EXIT :</label>
					<div class="optionaligninsectiontwo"><input type="radio" id="testExitId1" name="testExit" value="true" title="Complete Test" ><span class="value"> Complete Test </span></div>
					<div><input type="radio" id="testExitId2" name="testExit" value="false" checked title="Not Required To Complete Test"><span class="value width1"> Not Required To Complete Test </span></div>
				</div>
				
				<div class="ticket-cont">
					<label  class="field-label isrequired">GRACE PERIOD :</label>
					<div class="optionaligninsectiontwo"><input type="radio" id="graceperoidon" name="graceperoid" value="true" title="On" ><span class="value" > On </span></div>
					<div><input type="radio" id="graceperoidoff" name="graceperoid" value="false" checked title="Off"><span class="value"> Off </span>
					<input id="gracetimeInMin" type="number" value="mins" min="0" max="120" step="30" title="In Minutes" /><span class="value"> In Minutes </span></div>
				</div>
				<div class="ticket-cont">
					<label  class="field-label isrequired">AUTO-ENROLLMENT:</label>
					<div class="optionaligninsectiontwo"><input type="radio" id="testenrollmenton"  class="testenrollment" value="true" name="testenrollment" title="On"><span class="value"> On </span></div>
					<div><input type="radio" id="testenrollemntoff"  class="testenrollment" value="false" name="testenrollment" title="Off" checked><span class="value"> Off </span></div>
				
					<div class="testenrollmentdiv" style="margin-left: 0px; width: 200px; display:none;">
						<label for="testEnrollmentMethod" class="field-label"  style="width: 220px">TEST ENROLLMENT METHOD :<span class="lbl-required">*</span></label>
						<select id="testEnrollmentMethod" title="Test enrollment method" name="testEnrollmentMethod" class="bcg_select">
							<option value="">Select</option>
						</select>
					</div>
				
				</div>
				
				<%--
				<c:if test="${user.currentAssessmentProgramName == 'DLM'}">
				--%>
					<div class="ticket-cont">
						<div class="instructionAndAssessmentPlannerWindowYesNo">
							<label class="field-label isrequired">INSTRUCTION AND ASSESSMENT PLANNER:</label>
							<div class="optionaligninsectiontwo">
								<input type="radio" id="instructionalPlannerWindowOn_new" class="newIAPWindow" value="true" name="instructionalPlannerWindow" data-process-type="new" title="Instruction And Assessment Planner On">
									<span class="value">On</span>
							</div>
							<div>
								<input type="radio" id="instructionalPlannerWindowOff_new" class="newIAPWindow" value="false" name="instructionalPlannerWindow" data-process-type="new" title="Instruction And Assessment Planner Off" checked>
									<span class="value">Off</span>
							</div>
						</div>
						<div id="iapWindow_new" style="display:none;">
							<label class="field-label isrequired">WINDOW DISPLAY NAME:</label>
							<input type="text" id="instructionalPlannerDisplayName_new" name="instructionalPlannerDisplayName" placeholder="Display Name"/>
						</div>
					</div>
				<%--
				</c:if>
				--%>
				<div class="ticket-cont">
					<label  class="field-label isrequired">SCORING WINDOW:</label>
					<div class="optionaligninsectiontwo">
						<input type="radio" id="scoringWindowOn"  class="scoringWindow" value="true" title="On" name="scoringWindow">
						<span class="value"> On </span>
					</div>
					<div><input type="radio" id="scoringWindowOff" title="Off" class="scoringWindow" value="false" name="scoringWindow" checked>
						<span class="value"> Off </span>
					</div>				
					<div class="scoringWindowMethoddiv" style="margin-left: 0px; width: 200px; display:none;">
						<label for="scoringWindowMethod"  class="field-label" style="width: 220px">SCORING ASSIGNMENT METHOD:<span class="lbl-required">*</span></label>
						<select id="scoringWindowMethod" title="Scoring assignment method" name="scoringWindowMethod" class="bcg_select">
							<option value="">Select</option>
						</select>
					</div>				
				</div>
				
				
				<div id='selectNumbeOfTestBySubjectContainer'>
					<div id='selectNumbeOfTestBySubjectGrid'>
						<table id="selectNumbeOfTestBySubject"></table>
						<div id="selectNumbeOfTestBySubjectPager"></div>
					</div>
				</div>
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
			<span id="startDateSpanForNewTestWindow">
			<label for="testwindowstartDate"  class="field-label isrequired">START DATE:<span class="lbl-required">*</span></label>
			<input id="testwindowstartDate" class="input-large"  name="testwindowstartDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/>
			</span>
		</div>
		<div class="date-cont">
			<span id="startTimeSpanForNewTestWindow"> <label for="testwindowstartTime"> START TIME :</label>
				<input type="text" id="testwindowstartTime" class="input-large" /> 
				<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label></div>
			</span>
		</div>
	</div>
	<div class="form-right-side move-left">
		<div class="date-cont">
		<span id="endDateSpanForNewTestWindow"> <label for="testwindowendDate">END DATE :<span class="lbl-required">*</span></label>
			<input type="text" id="testwindowendDate" class="input-large"  placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/> 
		</span>
		</div>
		<div class="date-cont">
		<span id="endTimeSpanForNewTestWindow"> <label for="testwindowendTime">END TIME :</label>
			<input type="text" id="testwindowendTime" class="input-large"   />
			<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label> </div>
		</span>
		</div>
	</div>
	</div>
	
	<div id ="dailyAccessCodeTimeFrameDiv" class="hidden" >
  <div>	
  <label>Local Timeframe Daily Access Codes Usable by Students:</label>
  </div>
 <div class="form-left-side">
   
  <div class="date-cont">
   <span id="dailyAccessCodeStartTimeSpan"> <label for="dailyAccessCodeTestwindowstartTime"> START TIME :<span class="lbl-required">*</span></label>
    <input type="text" id="dailyAccessCodeTestwindowstartTime" class="input-large" /> 
    <div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label></div>
   </span>
  </div>
 </div>
 <div class="form-right-side move-left">
  
  <div class="date-cont">
  <span id="dailyAccessCodeEndTimeSpan"> <label for="dailyAccessCodeTestwindowendTime">END TIME :<span class="lbl-required">*</span></label>
   <input type="text" id="dailyAccessCodeTestwindowendTime" class="input-large"   />
   <div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label> </div>
  </span>
  </div>
 </div>
 </div>
	<div class="clear"></div>
	



<div id="scoringAvailableTimeFrameDiv" style="display:none;">
	<div>	
  		<label>Local Timeframe Scoring Available</label>
  	</div>
	<div class="form-left-side">
		<div class="date-cont">
			<span id="startDateSpanTestWindowScoring">
				<label for="testwindowScoringStartDate" class="field-label isrequired">START DATE:<span class="lbl-required">*</span></label>
				<input id="testwindowScoringStartDate" class="input-large"  name="testwindowScoringStartDate" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/>
			</span>
		</div>
		<div class="date-cont">
			<span id="startTimeSpanTestWindowScoring" > <label for="testWindowScoringStartTime"> START TIME :</label>
				<input type="text" id="testWindowScoringStartTime" class="input-large" /> 
				<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label></div>
			</span>
		</div>
	</div>
	<div class="form-right-side move-left">
		<div class="date-cont">
		<span id="endDateSpanTestWindowScoring"> <label for="testWindowScoringEndDate">END DATE :<span class="lbl-required">*</span></label>
			<input type="text" id="testWindowScoringEndDate" class="input-large"  placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/> 
		</span>
		</div>
		<div class="date-cont">
		<span id="endTimeSpanTestWindowScoring"> <label for="testWindowScoringEndTime">END TIME :</label>
			<input type="text" id="testWindowScoringEndTime" class="input-large"   />
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
				<label for="managedbyCodeSelect" class="field-label isrequired">MANAGED BY<span class="lbl-required">*</span></label>
				
				<div class="form-fields">
							<select id="managedbyCodeSelect" title="Managed by" class="bcg_select required">
								<option value="">Select</option>
					 			<option value="MANUAL_DEFINED_ENROLLMENT_TO_TEST">Manual</option>
						 		<option value="SYSTEM_DEFINED_ENROLLMENT_TO_TEST">System</option>
							</select> 
				</div>
			</div>
			<div class="group2">
				<label  for="randomizedCodeSelect" class="field-label isrequired">RANDOMIZATION<span class="lbl-required">*</span></label>
							<select id="randomizedCodeSelect" title="Randomization" class="bcg_select required">
								<option value="">Select</option>
					 			<option value="login">At Login</option>
						 		<option value="enrollment">Enrollment</option>
							</select> 
							
			</div>
			<div class="group3 search">
					<button type="button" class="search_btn" id="newSearchTestCollectionBtn" >Search</button>
			</div>
			<div class="clear"></div>
	</div>
	<div class="table-data-section">
		<div class="table-title1"><label  class="field-label isrequired">Available Collections:<span class="lbl-required">*</span></label></div>
		<div class="table-title2"><label  class="field-label isrequired">Assigned Collections:<span class="lbl-required">*</span></label></div>
		<div class="clear"></div>
		<div id="SelctingoperationalTestcollection">
			<div id="selectTestGridContainer" class="parent">
				<table id="selectTestGridTableId"></table>
				<div id="pselectTestGridTableId" class="responsive"></div>
			</div>
				<div id=AddMoveInoperationalTestcollection>
					<div id="AddInoperationalTestcollection">Add In &gt;&gt;</div>
					<div id="MoveOutoperationalTestcollection">&lt;&lt; Move Out</div>
				</div>
			
			<div id="selectedCollectionContainer" class="parent">
				<table id="SelectedCollectionId"></table>
				<div id="pSelectedCollectionId" class="responsive"></div>
			</div>
		</div>
	</div>
</div>
<div class="button-group-new">
	<button type="button" id="OperationalTestWindowSetupsave">Save</button>
	<button type="button" id="OperationalTestWindowSetupcancel">Reset</button>
</div>
</div>

<script type="text/javascript">


</script>
</body>
</html>
