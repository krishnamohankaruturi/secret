<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style type="text/css">
.mastery-wrapper {
	width: "auto";
	margin:0 auto;
}
#expandedRubricId {
color:#0e76bc;
text-decoration: underline;
}
#expandedRubricId:hover {
cursor: pointer;
text-decoration: none;
color:#0e76bc;
}
.mastery-table input[type="radio"]{	
	display: table-cell;
}
.mastery-table p {
	text-align: left;
	font-size:12px;
}
.mastery-table td,.mastery-table th {
 	padding: 0 3px; 
 	font-size:12px;
 }

.mastery-table tr.score td:nth-child(odd) { 
    background: #eee;
}
.mastery-table tr.score td:nth-child(even) {
    background: #fff;
}
.mastery-table tr td:nth-child(odd) { 
    background: #fff;
}
.mastery-table tr td:nth-child(even) {
    background: #eee;
}
.bg-white {	
	background-color: #fff !important;
}

#scoringTestCriteriaTable th {
 	text-align: left;
    vertical-align: top;
  }
.scoretestInnertd{
    text-align: center;
    vertical-align: top;
}

.scoreTestScoringTotal{
	text-align: center;
	vertical-align: center !important;
}

#scoringTestCriteriaTable td p{
    text-align: center;
 }


.colorbar {
	background-color: #fff !important;
	width: 80%;
	height: 15px;
	padding: 0 !important;
}

.colorbar img{
	min-width: 100%;
}

.mastery-table th {
	border:1px solid #ddd;
	border-right: none;
}

.mastery-table td {
	border:1px solid #ddd;
}
	
.mastery-table .no-border {
	border: none;
}
.assignScorerpanel_head {
 color: #5f7e1a;
}

.studentQuestionheader img{
  float: left;
  display: inline-block;
  vertical-align: middle;
  width: 30px;
}

.studentQuestionheader {
     padding-right: 4px;
	 font-weight:bold;
	 padding-bottom: 4px;
     padding-top: 4px;
	 cursor:pointer;
	 width:396px;
	 background-color:#EDEDED;
	 margin-top: 2px;
	 display: inline-block;
	 word-break: break-all;
     word-wrap: break-word;       /* Internet Explorer 5.5+ */
	 }
.studentQuestiondescription{
	display:none;
	border:1px solid #ada4a4;
	overflow: hidden;
	margin-bottom: 6px;
}

.studentQuestionheader-tilte{
	margin-top: 7px;
	float: left;
	display: inline-block;
    vertical-align: middle;
    width: calc(100% - 30px);
}

.questionPrompt{
	width:48%;
	float: left;
	padding: 5px;
	border: 1px solid #ada4a4;
	overflow: auto;
}

.studentResponse{
	padding: 5px;
	float:right;
	width: 47%;
	overflow: auto;
}
.questionPromptTitle{
	text-align:left;
	color: #82a53d;
	margin-top:10px;
	margin-bottom:10px;
}
.studentResponseTilte{
	text-align: center;
	color: #82a53d;
	margin-bottom: 20px;
}
.studentResponseContent{
	margin: 40px;
}
</style>

<link href="css/external/audioPlayer/audioPlayer.css" rel="stylesheet">

<div id="scorerTestsMainContainer" >

<!-- Score Test page container starting div -->

 <div id="scorerTestSearchContainer" style="display:block;">
  <div>&nbsp;</div><div>&nbsp;</div>  
	<div id="scorerTestTestSessionFilterContainer" style="margin-bottom:60px;width:100%;">
			<!-- <div style="margin-bottom:20px">Select test</div> -->
				<div id="scorerTestSelectAssignmentHeader" style="margin-left:18px;" class="assignScorerpanel_head">Select Assignment</div>
				<form id="searchScorerTestSessionFilterForm" name="searchScorerTestSessionFilterForm" class="form">				
						<input id="scoreTestsUserCurrentOrgId" type="hidden" value="${user.currentOrganizationId}" />
						<input id="scoreTestsUserAccesslevel" type="hidden" value="${user.accessLevel}" />
						
					<security:authorize access="hasRole('PERM_SCORE_ALL_TEST')">
				    <div id="scorerTestDistrictDiv" class="form-fields" ${user.accessLevel < 50 ? '':'style="display:none"' }>
						<label for="scoreTestsDistrict" class="field-label">DISTRICT <span class="lbl-required">*</span></label>			
						<select id="scoreTestsDistrict" title="District" class="bcg_select" name="scoreTestsDistrict">
							<option value="">Select</option>
						</select>
					</div>
					</security:authorize>
					<security:authorize access="hasRole('PERM_SCORE_ALL_TEST')">
					<div id="scorerTestSchoolDiv"class="form-fields" ${user.accessLevel < 70 ? '':'style="display:none"' }>
						<label for="scoreTestsSchool" class="field-label">SCHOOL <span class="lbl-required">*</span></label>			
						<select id="scoreTestsSchool" title="School" class="bcg_select" name="scoreTestsSchool">
							<option value="">Select</option>
						</select>
					</div>		
					</security:authorize>								
						<div class="form-fields" id="scorerTestContentAreasDiv">
							<label for="scorerTestContentAreas" class="field-label">Subject <span class="lbl-required">*</span></label>			
							<select id="scorerTestContentAreas" title="Subject" class="bcg_select" name="scorerTestContentAreas">
								<option value="">Select</option>
							</select>
						</div>
						<div class="form-fields" id="scorerTestGradesDiv">
							<label for="scorerTestGrades" id="scorerTestGradeLabel" class="field-label"><!-- PATHWAY <span class="lbl-required">*</span> --></label>				
							<select id="scorerTestGrades" class="bcg_select" name="scorerTestGrades">
								<option value="">Select</option>
							</select>
						</div>
				</form>
						<button class="btn_blue" style="float: right;margin-right: 70px;" id="scorerTestSearchBtnTestSessions">Search</button>
	</div>
	<div id="scorerTestStudentScoredGridContainer" style="margin-bottom:30px;">
			<div style="margin-left:20px;">				
				<label id="tstScorlblAllCompltd" style="display:none;margin:0px;" class="assignScorerpanel_head">All Test Sessions and Students have been scored. Click Next to close the window:
				</label>
				<label id="tstScorlblNextTestSession" style="margin:0px;" class="assignScorerpanel_head">Select Assignment To Score
				</label>
				<div>&nbsp;</div>
			</div>
			<div style="width: 86.128% !important;margin-left:18px;">
				<div id="scorerTestStudentScoredGridCell" >
					<div id="scorerTestStudentScoredTestSession" hidden="hidden"></div>
				 	<div id=scorerTestStudentGridContainer class="kite-table" >
				 		<table class="responsive" id="scorerTestStudentGridTableId" ></table>
						<div id="scorerTestStudentScoredGridPager" style="width: auto;"></div>
				 	</div>
				</div>
			</div>			
	</div>
</div>	
	
<!-- Score Test page container ending div -->

<!-- Student Grid page container starting div -->

	<div id="scorerStudentsAppearsScoreTestGridContainer" style="margin-bottom:30px;">		
	   <div>&nbsp;</div><div>&nbsp;</div>	
			<div style="line-height: 25px;font-weight: bold; margin: 3px 0 0 4px;">
				<a class="scoreTestStudentCcqTestName" href="javascript:callTestsessionSearch();" style="color:#0e76bc; text-decoration: underline;"></a>
				<span  style="color:#0e76bc;"> ></span>
				Select Student
			</div>
			<div style="font-weight: bold; margin: 10px 0 0 4px;" id="scoreTestStudentTestSessionBack" ><a  style="color:#0e76bc;text-decoration:underline;" href="javascript:callTestsessionSearch();">< back </a></div>
			<!-- <div style="font-weight: bold; margin: 10px 0 0 2px;" ><span  style="color:#82a53d;">Select student</span></div> -->
			
<!-- 			<div style="width:70%;">
			<div style="margin: 8px 0 0 58px;">
				<span  style="color:#0e76bc;">ASSESSMENT PROGRAM: </span><span id="scoreTestAssessmentPrg"> </span>				
			</div>
			<div style="margin: 8px 0 0 58px;">
				<span id="scoreTestStudentSubjectParent" style="color:#0e76bc;">SUBJECT&nbsp;: </span> <span id="scoreTestStudentSubject" ></span> 
			</div>
			<div style="margin: 8px 0 0 58px;">
				<span  style="color:#0e76bc;">TEST&nbsp;: </span><span id="scoreTestStudentTestSession"></span> 
			</div>
			</div> -->
			<div style="float: right;line-height: 25px;width:31%;" >			
				<div style="text-align: center;font-style: bold !important;width: 150px;height: 25px;line-height: 25px;margin-right:30px;float: left;border: 2px solid #000;">
					<b>Test Materials</b></div>
			<div id="mediapath" style="display:none;">${applicationScope['nfs.url']}</div>		
			<div id="scorerTestResources" style='float: left;padding : 1px;'>		
				<!-- <div style="text-align: center;margin-top: 25px;">
					<img src="images/pdf.png" style="height: 25px;width: 25px;" title="Name of File"/>
					<a style="color:#0e76bc; text-decoration: underline;">Name of File</a>
				</div>
				<div style="text-align: center;">
					<img src="images/icon-csv-green.png" style="" title="Name of File"/>
					<a style="color:#0e76bc; text-decoration: underline;">Name of File</a>
				</div>
				<div style="text-align: center;">
					<img src="images/icon-csv-green.png" style="" title="Name of File"/>
					<a style="color:#0e76bc; text-decoration: underline;">Name of File</a>
				</div> -->
			</div>
			</div>
			<div>&nbsp;</div>
			<div style="margin:0px;float:left;">
			<div style="margin-bottom: 15px;margin-left:2%;" class="assignScorerpanel_head">Select a student and item you want to score.</div>
			<div style="width: 86.128% !important;margin-left:18px;">
				<div id="scorerStudentsAppearsForScoreTestGridCell" >
					<div id="scorerStudentsAppearsForScoreTestSession" hidden="hidden"></div>
				 	<div id=scorerStudentsAppearsForScoreTestGridContainer class="kite-table">
				 		<table class="responsive" id="scorerStudentsAppearsForScoreTestGridTableId"></table>
						<div id="scorerStudentsAppearsForScoreTestGridPager" style="width: auto;"></div>
				 	</div>
				</div>
			</div>
			
			</div>			
						
			<div style="float: left;margin-bottom: 3%;margin-top: 3%;margin-left: 2%;width: 89%;font-size: 12px;" >
				<div class="scoringlegend" style="width: 22%;" ><div style="float: left;"><img src="images/icons/No_answer.png" class="scoringLegendIcon"  width="60%" height="12%"  title="No answer recorded"> </div><div style="padding-top: 5px;">No answer recorded</div></div>
				<div class="scoringlegend" style="width: 20%;" ><div style="float: left;"><img src="images/icons/Ready_to_score.png" class="scoringLegendIcon"  width="60%" height="12%"  title="Ready To Score"> </div><div style="padding-top: 5px;">Ready To Score</div></div>
				<div class="scoringlegend" style="width: 13%;" ><div style="float: left;"><img src="images/icons/Scored.png" class="scoringLegendIcon" width="60%" height="12%" title="Scored"> </div><div style="padding-top: 5px;">Scored</div></div>
				<div class="scoringlegend" style="width: 22%;" ><img src="images/icons/Edit_score.png" class="scoringLegendIcon" width="10%" height="12%;"  style="margin-bottom: 4px;" title="Edit Score">&nbsp;&nbsp;&nbsp;Edit Score</div>
			</div>
	</div>
	
	<!-- Student Grid page container ending div -->
	
	
	<!-- Submit Score page container starting div -->
	
	<div id="scoringTestCriteriaTableContainer" class="mastery-wrapper" style="margin-bottom:30px;">
	  <div>&nbsp;</div><div>&nbsp;</div>	
		<div style="font-weight: bold; margin: 3px 0 0 -8px;line-height: 25px;">
		<a class="scoreTestStudentCcqTestName" href="javascript:callTestsessionFromRubricSearch();" style="color:#0e76bc; text-decoration: underline;"></a>
		<span  style="color:#0e76bc;"> ></span>
		<a id="scoreTestStudentCcqStudentName" href="javascript:callTestSessionStudentClear();" style="color:#0e76bc; text-decoration: underline;"></a>
		<span  style="color:#0e76bc;"> ></span>	
		<a href="javascript:callTestSessionStudentClear();" style="color:#0e76bc; text-decoration: underline;">Question <span id="scoreTestStudentCcqQuestions"></span></a>
		<span  style="color:#0e76bc;"> ></span>		
		Score student performance
		</div>
		
		<div style="font-weight: bold; margin: 10px 0 0 -8px;" id="scoreTestStudentTestselectionBack" ><a  style="color:#0e76bc;text-decoration:underline;" href="javascript:callTestSessionStudentClear();">< back </a></div>
		<!-- 			<div style="font-weight: bold; margin: 10px 0 0 -9px;" ><span  style="color:#82a53d;">Score student performance</span></div>
		<div style="margin: 8px 0 0 58px;">
				<span  style="color:#0e76bc;">ASSESSMENT PROGRAM: </span><span id="lblScoreTestCriteriaAssmentPrg"> </span>				
		</div>
		<div style="margin: 8px 0 0 58px;">
				<span  style="color:#0e76bc;">SUBJECT&nbsp;: </span> <span id="lblScoreTestCriteriaSubject" ></span> 
		</div>
		<div style="margin: 8px 0 0 58px;">
				<span  style="color:#0e76bc;">TEST&nbsp;: </span><span id="lblScoreTestCriteriaTestsession"></span> 
		</div>
		<div style="margin: 8px 0 0 58px;">
			<span  style="color:#0e76bc;">STUDENT NAME:</span> <span id="lblScoreTestCriteriaStudntName" > </span>
		</div>
		<div style="margin: 8px 0 0 58px;">
			<span  style="color:#0e76bc;">STATE STUDENT ID: </span><span id="lblScoreTestCriteriaStudentId" > </span>
		</div>
		<div style="margin: 8px 0 0 58px;">
			<span  style="color:#0e76bc;">ITEM NUMBER: </span>Question <span id="lblScoreTestCriteriaItemNumber" > </span>
		</div> -->



	<!-- Score Test Prompt Question Response container starting div -->
			<div id="stimulusviewid" style="display:none;background-color: white;flot:right;text-align: right;font-weight: bold; padding-bottom: 10px;
						    		    margin-bottom: -7%; margin-top: 6%;text-decoration: underline;margin-left: 50%; width: 50%; cursor: pointer;height: 21px;" ><a href="#" id="stimulusviewlinkida" ><span id="stimulusviewlinkid"  >View Stimulus</span></a>
		</div>	
	  <div id="scoringTestQuestionResponseContainerData" >
		 	
	
	 </div>
	 
	     <div style="text-align: right;margin-top:40px;margin-bottom:10px;"><span tabindex = "0" id="expandedRubricId" style="display:none;" onclick="javascript:viewExpandedRubricDialog();" OnKeyPress="javascript:viewExpandedRubricDialog();" >View Expanded Rubric</span></div>
	  	 
		<div id="rubricContentDiv" >			
				
		</div>
		
		<table id="scoringTestCriteriaTable" border="0" cellspacing="0"  cellpadding="0" class="mastery-table">
			   <th style="text-align:center;vertical-align: middle;" rowspan="2" width="70" class="border-ccq firstColumn" style="padding:5px">Scoring Criteria</th>
		</table> 
	
		<div style="margin: 80px 0px 0px; line-height: 45px;" >
				
		<div style="float: right;margin: 20px 0px -30px 0px;line-height: 32px;"  id="nonScoreReasonDiv" >

    		<span>
    		<input type="hidden" id="taskVariantIdScoringReason" >
    		<input type="hidden" id="selectedRowStudentId" >  			
    		<label style="float: left;margin-top: -85px;margin-left: -140px;padding-right: 6px;">Non Score Reason</label></span>
    	
    		<span style="float: right;margin-top: -85px;margin-left: 10px;">
				<select id="nonScoreReasons" title="Non Score Reason" class="bcg_select" name="nonScoreReasons">
					<option value="">Select</option>
				</select>
			</span>
    		
  		</div>
		
<!-- 		<div style="margin: 12px 0 0 0;">
			<span style="color:#0e76bc; font-weight: bold;"><a href="#" id="ScoreTestStudentResponse" ><img src="images/icon_rubric_notepad.png" style="width:20px;" title="Scoring Required"/><span  style="margin-botom:2px;color:#0e76bc;" id="promptText" >Prompt and Student Response</span></a></span>
		</div> -->	
		
		<!-- <div class="assignScorerpanel_head" style="margin-left:0px;font-weight:bold;color:black;">Submit scores</div> -->
		<div style=" margin: 0 0 0 0px;" class="assignScorerpanel_head">When done, click submit score to save the score</div>
		<button class="btn_blue" style="float: right;margin-right: 0px;margin-top: -48px;" id="scorerTestSubmitScoreBtn">Submit Score</button>
		</div>
	</div>
		
	<!-- Submit Score page container ending div -->
		
 	<div id="scoringTestCriteriaTableContainerData" class="mastery-wrapper" style="margin-bottom:30px;">
	   <div style="margin: 20px 0px 0px; line-height: 32px;display:none;" id="continueScoringQuitScoringBtns" >
		
		<!-- <div style="float: left;margin: 27px 0px 0px;line-height: 32px;">
    		<span><label style="float: left;margin-left: 360px;margin-top: -85px;">Non Score Reason</label></span>
    	
    		<span style="float: right;margin-left: 25px;margin-top: -85px;">
				<select id="nonScoreReasons" class="bcg_select" name="nonScoreReasons">
					<option value="">Select</option>
				</select>
			</span>
    		
  		</div> -->
		
		<div style="float: right;margin: 27px 0px 0px;line-height: 32px;" >
			<button class="btn_blue" style="float: left;margin-right: 5px;margin-top: -48px;" id="continueScoringStudentSubmitScoreBtn">Next Student</button>
			<button class="btn_blue" style="float: right;margin-top: -48px;" id="continueScoringItemSubmitScoreBtn">Next Item</button>
		</div>
			<div style="float: right;margin: 85px -207px 0px;line-height: 32px;" >
				<button class="btn_blue" style="float: right;margin-top: -48px;" id="quitScoringStudentSubmitScoreBtn">Quit Scoring</button>
			</div>		
	</div> 	
	
	<div>&nbsp;</div><div>&nbsp;</div>
  <div id="successScoringTestSavedMessageScores" style="display:none;">
  <div><b>Score(s) successfully submitted for:</b></div>
  <div style="margin: 0px; margin-top: 25px; margin-left: 20px">
   STUDENT NAME: <span id="lblSuccessScoreTestCriteriaStudntName" style="color:#0e76bc;"> </span>
   STATE STUDENT ID: <span id="lblSuccessScoreTestCriteriaStudentId" style="color:#0e76bc;"> </span>
  </div> 
  <div style="margin: 0px; margin-top: 15px; margin-left: 20px">
   ASSESSMENT PROGRAM: <span id="lblSuccessScoreTestCriteriaAssmentPrg" style="color:#0e76bc;"> </span>
   <span id="lblSuccessScoreTestCriteriaSubjectP">SUBJECT: <span id="lblSuccessScoreTestCriteriaSubject" style="color:#0e76bc;"></span> </span>
<!--    <span id="lblSuccessScoreTestCriteriaGradeP">GRADE :<span id="lblSuccessScoreTestCriteriaGrade" style="color:#0e76bc;"></span></span> -->
   TEST: <span id="lblSuccessScoreTestCriteriaTestsession" style="color:#0e76bc;"></span> 
  </div>
 </div>
 <div id="successScoringTestSavedAllMessageScores" style="display:none;height:450px;">
  <div style="margin-top: 25px;">All Test and Students have been scored. Click Next to close the window:
  <button id="btnCloseScorerTestTestSession" button="button" class="btn_blue">Next</button>
  </div>
 </div>	 
 <!--  <div id="closeScorerTestTestSessionContainerConfirmation" style="margin-top: 25px;display:none;height:450px;">
   <div><b>All Student in this Test have been Scored.</b></div>
   <div>
    Score another Test?
    <button id="btnScoreTestNextTestSessionYes" type="button" class="btn_blue">Yes</button>
    <button id="btnScoreTestNextTestSessionNo" type="button" class="btn_blue">No</button>
   </div>
   
  </div>
 <div id="scorerTestNextStudetContainerConfirmation" style="margin-top: 25px;display:none;height:450px;">
   <div>
    Score another student in same Test?
    <button id="btnScorerTestNextStudetYes" type="button" class="btn_blue">Yes</button>
    <button id="btnScorerTestNextStudetNo" type="button" class="btn_blue">No</button>
   </div>
   
  </div> -->
 
<!--  	<div id="testScoreConfirmDialogSubmit" title="">
      <p><span class="warning" style="color: red;">Warning!</span>  Once submitted, scores cannot be changed. </p>
      <div style="margin:0px;"> Do you want to Proceed ?</div>
   </div> -->	
   
   <div id="confirmDialogSelectClearScorerTest" title="">
      <p><span class="warning" style="color: red;">Warning!</span>  All selected information will be lost. </p>
      <div><p>Proceed to cancel?</p></div>
   </div>

   
</div>

</div>
    <div id="previewMvDialog" title="" style="maxHeight:300px;overflow-y :auto;" >
     </div>
<script type="text/javascript">

var isEditScore = false;
<security:authorize access="hasRole('PERM_EDIT_SCORES')">	
	isEditScore=true;
</security:authorize>

	var scoreTest = false;
	var scoreAllTest = false;
	<security:authorize access="hasRole('PERM_SCORE_CCQ_TESTS')">
		var scoreTest = true;
	</security:authorize>
	<security:authorize access="hasRole('PERM_SCORE_ALL_TEST')">
	var scoreAllTest = true;
</security:authorize>
	
</script>