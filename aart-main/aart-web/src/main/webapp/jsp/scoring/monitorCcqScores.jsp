<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style type="text/css">
.mastery-wrapper {
	width: 800px;
	margin:0 auto;
}
.mastery-table input[type="radio"]{	
	display: table-cell;
}
.mastery-table p {
	text-align: left;
}
.mastery-table td,.mastery-table th {
 	padding:4px; 
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
.mastery-table .no-border-ccq {
	 border-width: 0px medium 1px 0; 
}
.assignScorerpanel_head {
 color: #5f7e1a;
}

.score_tick{
	color : green
}
#MonitorCCQScorerPopup{
    background-color: white;
    border-color: gray;
    border-style: solid;
    border-width: 1px;
    display: none; 
    left: 38%;
    padding: 10px 0 10px 10px;
    position: absolute;
    top: 40%;
    width: 425px;
    z-index: 1000;
}
#MonitorCCQScorerPopupClose{
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
</style>
 <script type="text/javascript" >
		var userAssessProg = '${user.currentAssessmentProgramName}';
 </script>
<div id="monitorCcqScoresMainContainer">
<input id="monitorScorerUserCurrentOrgId" type="hidden" value="${user.currentOrganizationId}" />
<!-- Monitor CCQ Score Test page container starting div -->

 <div id="monitorCCQScoreTestSearchContainer" style="display:block;"> 
   <div>&nbsp;</div><div>&nbsp;</div>
	<div id="monitorCcqScoresFilterContainer" style="margin-bottom:60px">
			<div style="margin-bottom:20px">Select test</div>
				<div style="margin-left:28px;" class="assignScorerpanel_head"> Select criteria, then click Search : </div>
				<form id="searchMonitorCcqScoresFilterForm" name="searchMonitorCcqScoresFilterForm" class="form">				
						
					    <div class="form-fields">
							<label for="monitorCcqScoresAssessmentPrograms" class="field-label">ASSESSMENT PROGRAM:<span class="lbl-required">*</span></label>			
							<select id="monitorCcqScoresAssessmentPrograms" class="bcg_select required" name="monitorCcqScoresAssessmentPrograms">
								<option value="">Select</option>
							</select>
						</div>
														
						<div class="form-fields">
							<label for="monitorCcqScoresContentAreas" class="field-label">SUBJECT:</label>			
							<select id="monitorCcqScoresContentAreas" class="bcg_select" name="monitorCcqScoresContentAreas">
								<option value="">Select</option>
							</select>
						</div>
						<div class="form-fields">
							<label for="monitorCcqScoresGrades" class="field-label" >${user.currentAssessmentProgramName == 'CPASS' ? 'PATHWAY':'GRADE' }:</label>			
							<select id="monitorCcqScoresGrades" class="bcg_select" name="monitorCcqScoresGrades">
								<option value="">Select</option>
							</select>
						</div>
																								
						<div class="form-fields" ${user.accessLevel < 50 ? '':'style="display:none"' }>
								<label for="monitorCcqScoresDistrict" class="field-label">DISTRICT:</label>			
								<select id="monitorCcqScoresDistrict" class="bcg_select" name="monitorCcqScoresDistrict">
									<option value="">Select</option>
								</select>
						</div>

						<div class="form-fields" ${user.accessLevel < 70 ? '':'style="display:none"' }>
								<label for="monitorCcqScoresSchool" class="field-label">SCHOOL:</label>			
								<select id="monitorCcqScoresSchool" class="bcg_select" name="monitorCcqScoresSchool">
									<option value="">Select</option>
								</select>
						</div>
						
				</form>
						<button class="btn_blue" style="float: right;margin-right: 33px;" id="monitorCcqScoresSearchBtnTest">Search</button>
	</div>
	<div>&nbsp;</div><div>&nbsp;</div>
	<div id="monitorCcqScoresGridContainer" style="margin-bottom:30px;">
			<div style="margin-left:20px;">				
				<label id="mcqsScorlblNextTest" style="margin:0px;" class="assignScorerpanel_head">Select a Test, then click Next:  
				</label>
				<div>&nbsp;</div>
			</div>
			<div style="width: 86.128% !important;margin-left:18px;">
				<div id="monitorCcqScoresGridCell" >
				 	<div id="monitorCcqScoresDivGridContainer" class="kite-table">
				 		<table class="responsive" id="monitorCcqScoresGridTableId"></table>
						<div id="monitorCcqScoresGridPager" style="width: auto;"></div>
				 	</div>
				</div>
			</div>	
			<button class="btn_blue" style="float: right;margin-right: 33px;" id="monitorCcqScoresNextBtn"> Next</button>		
	</div>
	</div>	
	
<!--Monitor Score Test page container ending div -->

	<div id="monitorCcqScoresRemainderTableContainer" class="mastery-wrapper" style="margin-bottom:30px;">
	 <div>&nbsp;</div><div>&nbsp;</div>					
		<div style="font-weight: bold; margin: 3px 0 0 4px;">
				<a class="monitorCcqScoresStudentTestName" href="javascript:callMonitorCCQScoreTestSearch();" style="color:#0e76bc; text-decoration: underline;"></a>
				<span  style="color:#0e76bc;"> ></span>
				Scoring Status
			</div>
			<div style="font-weight: bold; margin: 10px 0 0 4px;" id="scoreTestStudentTestSessionBack" ><a  style="color:#0e76bc;" href="javascript:callMonitorCCQScoreTestSearch();">< back </a></div>
			
			<div style="margin: 8px 0 0 58px;">
				<span  style="color:#0e76bc;">ASSESSMENT PROGRAM: </span><span id="lblMonitorCcqScoresRemainderAssessmentPrg"> </span>				
			</div>
			<div style="margin: 8px 0 0 58px;">
				<span id="scoreTestStudentSubjectParent" style="color:#0e76bc;">SUBJECT&nbsp;: </span> <span id="lblMonitorCcqScoresRemainderSubject" ></span> 
			</div>
			<div style="margin: 8px 0 0 58px;">
				<span  style="color:#0e76bc;">TEST&nbsp;: </span><span id="lblMonitorCcqScoresRemainderTest"></span> 
			</div>		
		
<!-- 		<div style="margin-left:20px;margin-top: 15px;">
			SUBJECT: <span id="lblMonitorCcqScoresRemainderSubject" style="color:#0e76bc;"> </span>
			<span id="lblMonitorCcqScoresRemainderGradeP">PATHWAY: <span id="lblMonitorCcqScoresRemainderGrade" style="color:#0e76bc;"></span></span>
			CCQ: <span id="lblMonitorCcqScoresRemainderTest" style="color:#0e76bc;"></span> 
		</div> -->
		<div style="margin-left:20px; margin-top: 15px;">
			Test with check mark(√) has been scored.
		</div>
		<div>&nbsp;</div>
		<div style="margin-left:28px;" class="assignScorerpanel_head"> 
		For each test not scored (if any), select to send an email reminder to the scorer or exclude it from average, then click Next: 
		</div>
		<div>&nbsp;</div>
		<div style="width: 86.128% !important;margin-left:18px;">
			<div id="monitorCcqScoresRemainderGridCell" >
			 	<div id="monitorCcqScoresRemainderDivGridContainer" class="kite-table">
			 		<table class="responsive" id="monitorCcqScoresRemainderGridTableId"></table>
					<div id="monitorCcqScoresRemainderGridPager" style="width: auto;"></div>
			 	</div>
			</div>
		</div>		
		<div style="margin: 60px 0px 0px; line-height: 32px;" >
		<button class="btn_blue" style="float: right;margin-right: 33px;margin-top: -48px;" id="monitorCcqScoreRemainderNextBtn">Next</button>
		</div>
	</div>
	<div>&nbsp;</div><div>&nbsp;</div>
  <div id="successMonitorScoresRemainderMessage" style="display:none;height:165px;">
  <div style="margin: 0px; margin-top: 15px; margin-left: 20px">
   <span id="lblMcqRemainderSubjectP">SUBJECT: <span id="lblMcqRemainderSubject" style="color:#0e76bc;"></span> </span>
   <span id="lblMcqRemainderGradeP">${user.currentAssessmentProgramName == 'CPASS' ? 'PATHWAY':'GRADE' }: <span id="lblMcqRemainderGrade" style="color:#0e76bc;"></span></span>
   ${user.currentAssessmentProgramName == 'CPASS' ? 'CCQ':'Test' }: <span id="lblMcqRemainderTest" style="color:#0e76bc;"></span> 
  </div>
  <div id="divMcqRemainderErrMsg" class="warning" style="margin: 0px; text-align:left;  margin-left:15px;margin-top:25px;color: red;display:none">
	Email reminder(s) successfully sent.		
</div>
<div style="margin-left:28px;" class="assignScorerpanel_head"> 
	To monitor another test click Continue; otherwise click Done.
	<button class="btn_blue" id="btnMcqsContinue">Continue</button>
			<button class="btn_blue" id="btnMcqsDone">Done</button> 
</div>
 </div>
   
</div>

 <div id="MonitorCCQScorerPopup">
   <div style="text-decoration: underline;"> Scorer 	<div id="MonitorCCQScorerPopupClose" > X </div> </div><br/>
   		<div id="MonitorCCQScorerPopupPopupMessage" >
    	 <p>Last Name: <span id="monitorScorerLastName" style="color: #0e76bc;"> </span> </p>
    	 <p>First Name: <span id="monitorScorerFirstName" style="color: #0e76bc;"> </span> </p>
    	 <p>MI: <span id="monitorScorerMiddleInitial" style="margin-left:40px;color: #0e76bc;"> </span> </p>
    	 <p>Email: <span id="monitorScorerEmail" style="margin-left:40px;color: #0e76bc;"> </span> </p>
   	</div>
</div>

 <div id="confirmMonitorCCQScorerPopupNextBtn" title="">
      <p><span class="warning" style="color: red;">Warning!</span>  Please select at least one. </p>
   </div>