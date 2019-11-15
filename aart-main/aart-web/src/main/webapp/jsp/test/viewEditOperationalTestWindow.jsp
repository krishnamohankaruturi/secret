<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="/jsp/include.jsp"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

#gbox_SelectCollectionId,
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
    padding-left: 27%;
}


#AddMoveInoperationalTestcollection {
    display: inline-block;
    margin-top: 20%;
    width: 120px;
}

#AddMoveInoperationalTestcollection > div {
    background: #000 none repeat scroll 0 0;
    color: #fff;
    display: block;
    margin: 5px 0;
    padding: 20px 0;
    text-align: center;
    width: 100%;
}

#AddMoveInoperationalTestcollection > div:hover {
	cursor: pointer;
	background: #767676 none repeat scroll 0 0;
	color :#fff;
}

.button-group-new button {
    background: #0e76bc none repeat scroll 0 0;
    border-radius: 7px;
    color: #fff;
    display: inline-block;
    font-size: 13px;
    font-weight: bold;
    padding: 12px 20px;
    text-align: center;
    text-decoration: none;
    text-transform: uppercase;
    margin: 10px;
    border: none;
}

.button-group-new button:hover {
	background: #86d8ff none repeat scroll 0 0;
    color: #fff;
}
    
.right.txt-edit {
    margin-top: -68px;
    cursor: pointer
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

#OperationalTestWindowEditMode{
	color: blue !important;
    width: 100% !important;
    font-family:"Courier New", Courier;
}	
</style>

<div id="OperationalTestWindowViewSetup">
<h5>Test Collection ID [xxxx] has an existing window with overlapping</h5>
<h3 id="OperationalTestWindowEditMode">Operational Test Window Edit Mode</h3>
<div class="section-1">
	<h2  class="txt_header">Window :</h2>
	<span id="TestCollectionEditwindow" class="right txt-edit"><a>Edit</a></span>
	
	<div id="OpTestWindowSetupMakereadableDiv"></div>
	<div id="OpTestWindowSetupContentDiv">
	<div id="window">
		<div class="form-left-side">
			<div id="assessmentProgramTestWindowDiv" class="form-fields">
				<label for="assessmentProgramTestWindow" class="isrequired field-label">Assessment Program:<span class="lbl-required">*</span></label> 
				<select id="assessmentProgramTestWindow" name="assessmentProgramTestWindow" class="bcg_select">
					</select>
			</div>
			
				<label  class="isrequired field-label">WINDOW NAME</label>
				 <input type="text" id="windowName" name="windowName" value="${testwindow[0].windowName}" class="input-large" maxlength="60" />
			
		</div>
		<div  class="form-right-side">
			<div class="status-cont">
				<label class="isrequired field-label">STATUS : </label> ${testwindow[0].saveStatus}
			</div>
			<div class="status-cont">
			<label class="isrequired field-label"> SUSPEND : </label>
			<c:choose>
				<c:when test="${testwindow[0].suspendWindow}">
					<input id="windowsuspend" type="checkbox" name="suspend" checked="true" />  
				</c:when>
				<c:otherwise>
					<input id="windowsuspend" type="checkbox" name="suspend"  checked="false"/>  
				</c:otherwise>
			</c:choose>
			</div> 
		</div>
		<div class="clear"></div>
	</div>



<div class="section-2">
	<h2 class="txt_header">ADMIN OPTIONS :</h2>
	
			<div  class="form-left-side">
			
			<label  class="field-label isrequired">MANAGED BY</label>
						<select id="managedbyCodeSelect" data-codeselectvalue='${testwindow[0].managedBy}' class="bcg_select required">
							<option value="">Select</option>
							<option value="Manual">Manual</option>
							<option value="System">System</option>
						</select> 
			
			
			<label  class="field-label isrequired">RANDOMIZATION</label>
						<select id="randomizedCodeSelect" data-codeselectvalue='${testwindow[0].randomized}' class="bcg_select required">
							<option value="">Select</option>
			 			<option value="Login">Login</option>
				 		<option value="Enrollment">Enrollment</option>
						</select> 
			
			</div>
			<div  class="form-right-side">
				<div class="ticket-cont">
					<label  class="field-label isrequired">TICKETING :</label>
					<c:choose>
						<c:when test="${testwindow[0].ticketingFlag}">
							<input type="radio" id="ticketingon" name="ticketing" value="true" checked><span class="value"> On </span>
							<input type="radio" id="ticketingoff" name="ticketing" value="false"><span class="value">  Off </span>	
						</c:when>
						<c:otherwise>
							<input type="radio" id="ticketingon" name="ticketing" value="true" ><span class="value"> On </span>
							<input type="radio" id="ticketingoff" name="ticketing" value="false" checked><span class="value">  Off </span>	
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="ticket-cont">
					<label  class="field-label isrequired">TEST EXIT :</label>
					<c:choose>
						<c:when test="${testwindow[0].requiredToCompleteTest}">
							<input type="radio" id="testExitId1" name="testExit" value="true" checked><span class="value"> Complete Test </span>
							<input type="radio" id="testExitId2" name="testExit" value="false"><span class="value">  Not Required To Complete Test </span>
						</c:when>
						<c:otherwise>
							<input type="radio" id="testExitId1" name="testExit" value="true" ><span class="value"> Complete Test </span>
							<input type="radio" id="testExitId2" name="testExit" value="false" checked><span class="value">  Not Required To Complete Test </span>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="ticket-cont">
					<label  class="field-label isrequired">GRACE PERIOD :</label>
				<c:choose>
					<c:when test="${testwindow[0].gracePeroid}">
						<input type="radio" id="graceperoidon" name="graceperoid" value="true" ><span class="value">  On </span>
						<input type="radio" id="graceperoidoff" name="graceperoid" value="false" checked><span class="value">  Off </span>
					</c:when>
					<c:otherwise>
						<input type="radio" id="graceperoidon" name="graceperoid" value="true" ><span class="value">  On </span>
						<input type="radio" id="graceperoidoff" name="graceperoid" value="false" checked><span class="value">  Off </span>	
					</c:otherwise>
				</c:choose>
				<input id="gracetimeInMin" type="number" value=${testwindow[0].graceTimeInMin} min="0" max="90" step="30" />
				</div>
			</div>
			<div class="clear"></div>
		
</div>

<div class="section-3"> 
	<h2 class="txt_header">DATES :</h2>

	<div class="form-left-side">
		<div class="date-cont">
			<span id="startDateSpanForTestWindow"> <label>START DATE :</label>
		  		<input id="testwindowstartDate" name="dobDate" data-codeselectvalue='${testwindow[0].effectiveDate}' class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/> 
			</span>
		</div>
		<div class="date-cont">
			<span id="startTimeSpanForTestWindow"> <label> START TIME :</label>
				<input type="text" id="testwindowstartTime" class="input-large" /> 
				<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label></div>
			</span>
		</div>
	</div>
	<div class="form-right-side">
		<div class="date-cont">
		<span id="endDateSpanForTestWindow"> <label>END DATE :</label>
			<input type="text" id="testwindowendDate" class="input-large" data-codeselectvalue='${testwindow[0].expiryDate}'  placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/> 
		</span>
		</div>
		<div class="date-cont">
		<span id="endTimeSpanForTestWindow"> <label>END TIME :</label>
			<input type="text" id="testwindowendTime" class="input-large"   />
			<div class="error-msg"><label>please enter time in hh:mm:ss AM/PM format</label> </div>
		</span>
		</div>
	</div>
	<div class="clear"></div>
</div>
	
<div class="section-4">
	<h2 class="txt_header">SELECT TEST COLLECTIONS :</h2>
	
	<div class="table-data-section">
			<div id="SelctingoperationalTestcollection">
				<div id="ViewingWindowColectionscontantDiv">
					<table id="ViewingWindowColections">
							<tr>
								<th>Id</th>
								<th>Test Collection Name</th>
								<th>Program Name</th>
							</tr>
							<c:forEach items="${testwindowcollection}" var="element"> 
							<tr>
								<td>${element.testCollectionId}</td>
								<td>${element.name}</td>
								<td>${element.programName}</td>
							</tr>
							</c:forEach>
					</table>
				</div>
				<div id="EditableWindowColectionsGrids">
					<table id="SelectCollectionId"></table>
					<div id="page1"></div>
				
				
					<div id=AddMoveInoperationalTestcollection>
						<div id="AddInoperationalTestcollection">Add In &gt;&gt;</div>
						<div id="MoveOutoperationalTestcollection">&lt;&lt; Move Out</div>
					</div>
				
				
					<table id="SelectedCollectionId"></table>
					<div id="page2"></div>
				</div>
				<div class="clear"></div>
		
		</div>
	</div>
</div>
</div>

<div id="button-group-new-OperationalTestWindow" class="button-group-new">
	<button type="button" id="OperationalTestWindowSetupsave">Save</button>
	<button type="button" id="OperationalTestWindowSetupcancel">Cancel</button>
</div>
</div>

