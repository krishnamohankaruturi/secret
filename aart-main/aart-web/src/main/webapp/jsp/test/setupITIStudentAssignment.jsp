<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<div>	
	<div>
		<div style='float: left;'>
			<input id="setupITIStudentAssignmentBackButton" class="panel_btn backButton setupITIBackButton" type="button" value="Back">
	 	</div>
	</div>
		<div class="divFontForITI studentInfo" id="studentDetails"></div><br><br>
	<div  id="instrcutionalPlanStudentAssignmentInfoDiv" class="divFontForITI" style="padding:20px;">
		<span id="insPlanLabel"><fmt:message key="label.iti.assignmentInstructionalplan"/></span> 
		<span style="padding:20px; color: #0E76BC; font-weight: bold" class="instructionalPlan"> </span>
	</div>
	
	<div class="divFontForITI" id="instrcutionalPlanEE" style="color: #0E76BC; padding:20px;">
		<span style="font-weight: bold"><fmt:message key="label.iti.assignmentInstructionalplanEE"/></span> 
		<span id="instructionalPlanContentCodeName"> </span>
		<br/><br/><br/>
		<span id="instructionalPlanLeveDesc"> </span>
		<span id="instructionalPlanLevel" class="hidden"> </span>
	</div>
	<br/> 
	<div id="instrcutionsPDF" class="hidden" style="padding:20px;"> 
			<span  id="instrcutionsPDFLabel"><fmt:message key="label.iti.instrcutionsPDF"/></span>
			<a id="instrcutionsPDFLink" href=""><img src='images/pdf.png' style='border:0px solid;' /> </a>
	</div>
	<br/>
	<div id="instrcutionalPlanSensitivity" class="hidden" style="padding:20px;"> 
			<span  id="sensitivityTagInstruction"><fmt:message key="label.iti.sensitivityTagInstruction"/><br/></span>
			<br/>
			<div>
				<label for="sensitivityTags" class="field-label">Theme: </label>			
				<select id="sensitivityTags" class="bcg_select" name="sensitivityTags">
				</select>
			</div>
	</div>
	<div id="instrcutionalPlanStudentAssignmentErrorDiv" style="padding:20px;"> 
			<span  id="instructionalPlanError" class="error"> </span>
	</div>
	<div id="saveITIAssignments" style="display: none">			
		<label>Would you like to create another plan for this student in this subject?
		</label>&nbsp;
		<br/>				
	</div>
	<div>
		<div>
			<span id="itiloadingmessage" class="hidden" style="padding:25px;">Saving plan...<img src="<c:url value='/images/ajax-loader.gif'/>"/></span>
		</div> 
		<div id="assignmentActions">
			<div style='float: right;' >
				<input id="setupITINextButtonSave" class="panel_btn nextButton hidden" type="button" value="Save Plan">
	 			<input id="setupITINextButtonConfirmation" class="panel_btn nextButton setupITINextButton hidden" type="button" value="Continue">
	 		</div>
	 		<br/><br/><br/><br/>
			<div id="instrcutionalPlanInfo" style="padding:20px;"><fmt:message key="label.iti.assignmentInstructions"/></div>
		</div>
	</div>
	<br/><br/>
</div>
<script type="text/javascript"
	src="<c:url value='/js/test/setupITIStudentAssignment.js'/>"> </script>

