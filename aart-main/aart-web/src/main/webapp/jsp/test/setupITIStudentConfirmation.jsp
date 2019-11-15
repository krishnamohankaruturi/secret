<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
	
<div>	
	
	
    <input id="columnChooserGrid" value ="false" type="hidden" class="hidden" />	
	<div>
			<span id="iticonfirmloadingmessage" class="hidden" style="padding:25px;">Saving plan...<img src="<c:url value='/images/ajax-loader.gif'/>"/></span>
	</div> 
	<div id="confirmationActions">
		<div style='float: left;'>
			<input id="setupITIStudentConformationBackButton" class="panel_btn backButton setupITIBackButton" type="button" value="Back">
	 	</div>
		<div style='float: right;' >
			<input id="setupITICancel" class="panel_btn nextButton setupITICancel" type="button" value="Cancel Plan">
		</div>
		<div style='float: right;' >
			<input id="setupITIConfirm" class="panel_btn nextButton setupITIConfirm" type="button" value="Confirm Assignment">
		</div>
		<div style='float: right;' >
			<input id="setupITISave" class="panel_btn nextButton setupITISave" type="button" value="Save Plan">
		</div>
	</div><br><br><br><br>
	<div class="divFontForITI studentInfo" id="studentDetailsStudentConformation"></div><br/><br/>
	<div id="instrcutionalPlanInfoDiv" class="divFontForITI" style="padding:20px; font-weight: bold"><fmt:message key="label.iti.assignmentInstructionalplan"/>
			<span style="padding:20px; color: #0E76BC; font-weight: bold" class="instructionalPlan"> </span>
	</div>
	<div id="saveOrDoneITIAssignmentPopUp" style="display: none">
		<label>Would you like to create another plan for this student in this subject?
		</label>&nbsp;
		<br/>
	</div>
	<div id="instrcutionalPlanResourceDiv" class="hidden" style="padding:20px; font-weight: bold">
			<fmt:message key="label.iti.resourcePrintInstruction"/><br/>
			<fmt:message key="label.iti.resourceInstruction"/>
			<a id="resourcepdf" href="" download="" target="_blank"><img src='images/pdf.png' style='border:0px solid;' /> </a>
	</div>
	<div id="instrcutionalPlanErrorDiv" style="padding:20px;"> 
			<span  id="instructionalPlanConfirmError" class="error"> </span>
	</div>
	<br>
 	<div style='float: right;'>
			<input id="setupITIDone" class="panel_btn" type="button" value="Done">
	</div>
</div>
<div id="confirmITIPlan"></div>
<div id="cancelITIPlan"></div>


