<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>
.info-msg-content {
	margin: 20px 0;
	float: left;
    line-height: 30px;
    height:450px;
    margin-right: 5%;
    text-align: center;
    width: 100%;
    display:none;
}
#assignScorerStudentGridTableId .ui-jqgrid tr .ui-row-ltr td {
    border-right: 1px solid white;
    padding: 0 2px 2px 0;
    text-align: left !important;
}
.assignScorerpanel_head {
 color: #5f7e1a;
}
.assignScorerpanel_head_studentscore {
 color: #0E76BC;
}


#view_scorer_test_grid {
  position:relative;
    overflow:auto;
    
    }
   .ui-jqgrid .ui-jqgrid-hbox {
    padding-right: 0px;
}   
.select2-container{
width:250px !important;
}
.hideWarningFields {
		display:none
	}
	.with-sidebar-content{
border-left:0px solid #d7d7d7 !important;
} 

</style>


<div class="assignScorerpanel_head" style="margin-left:28px;margin-top: 20px;"> Select criteria, then click search. </div>
<div id="assignScorerviewScorerTestSessionFilterContainterDiv"> </div>
<div id="assignScorerviewScorerTestSessionFilterContainter">
<div id="viewScorerTestSessionFilterContainter">
			<form id="searchTestSessionFilterForm" name="searchTestSessionFilterForm" class="form">
				<input id="assignScorersUserAccesslevel" type="hidden" value="${user.accessLevel}" />
					<div class="btn-bar" style="margin-top:10px;margin-left:3px;">
						<div id="searchFilterErrors" class="error form-fields"></div>
						<div id="searchFilterMessage" style="padding:20px" class="hidden"></div> 
					</div>
				    <div class="form-fields" ${user.accessLevel < 50 ? '':'style="display:none"' }>
						<label for="assignScorerDistrict" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
						<select id="assignScorerDistrict" title="District" class="bcg_select required" name="assignScorerDistrict">
							<option value="">Select</option>
						</select>
					</div>
					
					<div class="form-fields" ${user.accessLevel < 70 ? '':'style="display:none"' }>
						<label for="assignScorerSchool" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
						<select id="assignScorerSchool" title="School" class="bcg_select required" name="assignScorerSchool">
							<option value="">Select</option>
						</select>
					</div>	
					<div class="form-fields">				
						<label for="assignScorerContentAreas" class="field-label">Subject:<span class="lbl-required">*</span></label>			
						<select id="assignScorerContentAreas" title="Subject" class="bcg_select required" name="assignScorerContentAreas">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields">
				<c:choose>
					<c:when test="${user.currentAssessmentProgramName == 'CPASS'}">
						<label for="assignScorerGrades" class="field-label">Pathway:<span
							class="lbl-required">*</span></label>
						<select id="assignScorerGrades" title="Pathway"
							class="bcg_select required" name="assignScorerGrades">
							<option value="">Select</option>
						</select>
					</c:when>
					<c:otherwise>
						<label for="assignScorerGrades" class="field-label">Grade:<span
							class="lbl-required">*</span></label>
						<select id="assignScorerGrades" title="Grade"
							class="bcg_select required" name="assignScorerGrades">
							<option value="">Select</option>
						</select>
					</c:otherwise>
				</c:choose>
			</div>					
					<div class="form-fields">
						<label for="assignScoresStage" class="field-label">Stage:</label>			
						<select id="assignScoresStage" title="Stage" class="bcg_select required" name="assignScoresStage">
							<option value="">Select</option>
						</select>
					</div>
					
					<div class="form-fields">
						<label for="assignScorerTestPrograms" class="field-label">Test:<span class="lbl-required">*</span></label>			
						<select id="assignScorerTestPrograms" title="Test" class="bcg_select required" name="assignScorerTestPrograms">
							<option value="">Select</option>
						</select>
					</div>
					
					
			</form>
			 <button class="btn_blue" style="float:right;margin-right:144px" id="assignScorerSearchScorersBtn">Search</button> 
	
<!-- Assign Scoring grid -->	

<div id="assignStudentGridScroll"></div>
	<!-- Next Tab  -->
	
	<div  style="display : inline;margin-top:-37px; float: left;">
	
	
	<!-- Test Scoring grid/a -->	
	 <div id="assignStudentScores_gridContainter" style="margin-top: 10%;">
	<div style="margin-left:28px;" class="assignScorerpanel_head_studentscore" > Select Students  </div><br>
	<div style="margin-left:28px;">Pick the students to be assigned and click the Add button. You can also remove before saving.</div>
	<div class="full_main" style="width: 86.128% !important;margin-left:18px;margin-top:15px;">
		<div id="viewOrgStudentGridCell" >
			<div id="viewOrgStudent" hidden="hidden"></div>
		 	<div id=viewOrgGridContainer class="kite-table">
		 		<table class="responsive" id="viewStudentGridTableId"></table>
				<div id="viewStudentGridPager" style="width: auto;"></div>
		 	</div>
		</div>
	</div>
	<div>
	
	<div style="float: right;margin-right: 80px;">
			<button class="btn_blue" id="assignStudentRemoveScorersBtn">Remove</button>
	</div>
	<div style="float: right;margin-right: -147px;">
			<button class="btn_blue" id="assignStudentAddScorersBtn">Add</button>
	</div>
	
	</div>	
	</div>	 
	
			
	</div>
	
	<div id="assignScorerGridScroll"></div>
	<!-- second grid -->
	
	 <div  style="display : inline;margin-top:25px;">	
	<div id="assignScorerScores_gridContainter" style="margin-top: 3%;float: left;">
	<div style="margin-left:28px;" class="assignScorerpanel_head_studentscore" > Select scorers  </div><br>
	<div style="margin-left:28px;">Pick the scorer to be assigned and click the Add button. You can also remove before saving.</div>
		<!-- <div style="margin-left:28px;" class="assignScorerpanel_head" > Select scorers then click Add:  </div> -->
	<div class="full_main" style="width: 86.128% !important;margin-left:18px;margin-top:15px;">
		<div id="viewOrgScorerGridCell" >
			<div id="viewOrgScorer" hidden="hidden"></div>
		 	<div id=viewOrgScorerGridContainer class="kite-table">
		 		<table class="responsive" id="viewScorerGridTableId"></table>
				<div id="viewScorerGridPager" style="width: auto;"></div>
		 	</div>
		</div>
	</div>
	<div>
	
	
	<div style="float: right;margin-right: 80px;">
			<button class="btn_blue" id="assignScorerRemoveScorersBtn">Remove</button>
	</div>
	<div style="float: right;margin-right: -147px;">
			<button class="btn_blue" id="assignScorerAddScorersBtn">Add</button>
	</div>
	</div>	
	</div>		
	
			
	</div> 
	
	
	</div>
	<br/>	<br/><br/>	<br/>				
	<div style="margin-top:1%;" id="scorer_student_grid">
		<div style="margin-left: 24px;margin-right: 137px;" class="assignScorerpanel_head_studentscore">Your Selections</div>		
				<br/>
		<div style="margin:-4px 66px 0 25px;">Selected Students <div style="float:right;margin-right:310px;margin-bottom:2px"> Selected Scorers </div></div>
		<div class="full_main" style="width: 45.128% !important;margin-top:2px;margin-right: 37px;margin-left:12px">
		<div id="viewStudentOrgGridCell" >
			<div id="viewstudentOrg" hidden="hidden"></div>
		 	<div id=viewOrgStudentGridContainer class="kite-table" >
		 		<table class="responsive" id="assignScorerStudentGridTableId"></table>
		 	</div>
		</div>
		</div>	
	<div class="full_main" style="width: 45.128% !important;margin-right:54px;margin-left:-53px;">
		<div id="viewScorerOrgGridCell" >
			<div id="viewscorerOrg" hidden="hidden"></div>
		 	<div id=viewOrgScoreGridContainer class="kite-table" style="margin-left:55px !important;">
		 		<table class="responsive" id="selectScorerGridTableId" ></table>
		 		<div id="selectScorerGridPager" style="width: auto;"></div>				
		 	</div>
		</div>
	</div>	
	
	</div>
	
	
	
	<div class="info-msg-content" id="info-msg-content" style="margin: 0px;margin-top:45px;">		
		  <div id="divAssignScorerErrMsgemptysave" class="warning" style="margin: 0px; text-align:left;  margin-left:15px;margin-top:25px;color: red;display:none"> 
		 </div>
		 <div style="margin: 0px; text-align:left;  margin-left:34px;">
		     <div class="assignScorerpanel_head" style="text-align:left">
		     Enter a descriptive assignment name.
		     </div>
		 	<input type="text" id="txtAssignScorerCcqTestName" name="txtAssignScorerCcqTestName" placeholder="Enter a test name"  class="input-large" maxlength="190" title="Enter a descriptive assignment name	"/>		
		 	<button class="btn_blue" id="info-msg-cancel" class='ui-state-disabled'  style="float:right;margin-right: 191px;margin-top:-10px">Cancel</button>
		 	<button class="btn_blue" id="info-msg-nextbtn" class='ui-state-disabled' disabled="disabled" style="float:right;margin-right: -134px;margin-top:-10px">Save</button>
			<!-- <button class="btn_blue" id="info-msg-cancelbtn">Cancel</button>	 -->
		</div>
		<div id="divAssignScorerErrMsg" class="warning" style="margin: 0px; text-align:left;  margin-left:15px;margin-top:25px;color: red;display:none">
			
		</div>		
		<br/><br/>
	</div> 
	
	<div id="info-msg-save" class="info-msg-save" style="float:left;display:none;margin-top:50px;height:450px;width:90%">
	   <%-- <div style="float : left;margin-left : 35px;width:100%"> 
	   SUBJECT: <span id="msgSaveSubjectName" style="color:#0e76bc;"></span> 
	    ${user.currentAssessmentProgramName == 'CPASS' ? 'PATHWAY':'GRADE' }: <span id="msgSaveGradeName" style="color:#0e76bc;"></span>  </div> --%>
	   <br/><br/>
		<div style=" margin: 40px 0 0 35px;"> ${user.currentAssessmentProgramName == 'CPASS' ? 'CCQ':'' }  <span id="assignScorerMsgSaveTestName" style="color:#0e76bc;"></span> has been successfully associated with selected students and scorers on <span id="msgSaveCurrentDate" style="color:#0e76bc;"></span>.</div>
		<div style=" margin: 35px 0 0 40px;" class="assignScorerpanel_head"> To assign scorers to other test sessions, click Continue; otherwise click Done:
		<div style="float:right;"><button class="btn_blue" id="assignScorerContinue">Continue</button>
			<button class="btn_blue" id="assignScorerDone">Done</button>
		</div>
		</div>
	</div>
	<br/>
	<br/>
	<br/>
	<br/>
	</div>	
	<input id="assignScorerUserCurrentOrgId" type="hidden" value="${user.currentOrganizationId}" />
	<input id="assignScorerUserCurrentassessmentprgId" type="hidden" value="${user.currentAssessmentProgramId}" />
	
	<div id="confirmDialogSelectClearAssignScorer" title="" class="hideWarningFields">
      <p><span class="warning " style="color: red;">Warning!</span>  All selected information will be lost. </p>
      <div><p>Proceed to cancel?</p></div>
   </div>
   <div id="confirmDialogAssignScorerDeleteStudent" title="" class="hideWarningFields">
      <p><span class="warning" style="color: red;">Warning!</span>  Delete student from the list? </p>
   </div>
   <div id="confirmDialogAssignScorerDeleteScorer" title="" class="hideWarningFields">
      <p><span class="warning" style="color: red;">Warning!</span>  Delete scorer from the list? </p>
   </div>
    <div id="confirmDialogAssignScorerCancelScorer" title="" class="hideWarningFields">
      <p><span class="warning" style="color: red;">Warning!</span>  All information will be lost. </p>
      <div style="margin:0px;"> Proceed to cancel?</div>
   </div>
   <div id="confirmDialogAssignScorerRefreshScorer" title="" class="hideWarningFields">
      <p><span class="warning" style="color: red;">Warning!</span>  Please deselect scorers or click Add before changing the criteria. </p>
   </div>
   <div id="confirmDialogTestSessionSelectionChange" title="" class="hideWarningFields">
      <p><span class="warning" style="color: red;">Warning!</span>  Selected information will be lost. </p>
      <div><p>Proceed to Yes?</p></div>
   </div>
   <div id="warningDialogStudentDuplicateTestSession" title="" class="hideWarningFields">
      <p><span class="warning" style="color: red;">Warning!</span>  Students already mapped with scorer! </p>
     
   </div>
   