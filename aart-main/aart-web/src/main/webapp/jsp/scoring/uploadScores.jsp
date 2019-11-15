<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<style>
	#errorplaceScoringdiv .error{
	color:red !important;
	text-transform:none !important;
	width:250px;
	}
</style>

<div id="uploadScoresContainers" >
	<div id="uploadScoresSearch">
		<div style="margin-bottom:20px;margin-top:32px;">Upload Scores</div>
		<div style="margin-left:18px;color:#769846;margin-bottom: 5px;">Select Test Sessions:</div>
		<div style="margin-left:22px;">Select the tests for the students that will be scored. Students must take the same test to be downloaded and reuploaded together.</div>
		
		<form id="searchUploadScoresFilterForm" name="searchUploadScoresFilterForm" class="form">
			<div class="form-fields" ${user.accessLevel < 50 ? '':'style="display:none"' }>
				<label for="uploadScoresDistrict" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
				<select id="uploadScoresDistrict" title="District" class="bcg_select" name="uploadScoresDistrict">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields" ${user.accessLevel < 70 ? '':'style="display:none"' }>
				<label for="uploadScoresSchool" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
				<select id="uploadScoresSchool" title="School" class="bcg_select" name="uploadScoresSchool" multiple="multiple">
					<!-- <option value="">Select</option> -->
				</select>
			</div>
			<div class="form-fields">
				<label for="uploadScoresContentAreas" class="field-label">SUBJECT:<span class="lbl-required">*</span></label>			
				<select id="uploadScoresContentAreas" title="Subject" class="bcg_select" name="uploadScoresContentAreas">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields">
				<label for="uploadScoresGrades" id="uploadScoresGradesLabel" class="field-label" ></label>			
				<select id="uploadScoresGrades" class="bcg_select" name="uploadScoresGrades">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields">
				<label for="uploadScoresStage" class="field-label" >STAGE:</label>			
				<select id="uploadScoresStage" title="Stage" class="bcg_select" name="uploadScoresStage">
					<option value="">Select</option>
				</select>
			</div>	
			<div class="form-fields" style="margin-top: 1px;" >
				<label for="uploadScoresTestSessions" class="field-label" >TEST SESSIONS:<span class="lbl-required">*</span></label>			
				<select id="uploadScoresTestSessions" title="Test Sessions" class="bcg_select" name="uploadScoresTestSessions" multiple="multiple">
					<!-- <option value="">Select</option> -->
				</select>
			</div>		
		</form>	
		<button class="btn_blue" style="float: right;margin-right: 33px;" id="uploadScoresNextBtn">Next</button>
		<div style="float:right;margin-right:25px;margin-top:12px;">
			<input id="uploadScoresIncludeItem" type="checkbox" />
			<label for="uploadScoresIncludeItem"  >Include Completed Item.</label>
		</div>								
	</div>
	
	<div id="uploadScoresDownloadContainer">
		<div style="margin-bottom: 15px;margin-top: 40px;">
			<div style="color:#769846;margin-left: 18px;">Download File:</div>
			<div style="margin-left: 22px;">Once the file is available, click the download button to save the file to location on your system.</div>
			<div style="margin-left: 25px;" class="userMessages" id="scoringErrorMessages">
				<span class="error_message ui-state-error selectAllLabels hidden" id="scoringCompletedError" ></span>
				<span class="error_message ui-state-error selectAllLabels hidden" id="multipleTestsError" ></span>
			</div>
			<div style="margin-left: 25px;margin-top: 15px;display: none;" id="fileProcessingtext" >
				<span style="font-weight:bold">File is currently processing.</span>				
			</div>
			<div style="margin-bottom: 15px;margin-top: 10px;margin-left: 22px;">
				<a href="#"><img src="images/icon-csv-green.png" title="Click to download extract." id="downloadIconImage"><span style="margin:0px;"><label id ="downloadFileName"></label></span></a> 
				<a id="downloadFileNameHref" class="btn_blue" id="uploadScoresDownloadBtn">Download</a>
			</div>
		</div>
		<form id="uploadScoingForm">
			<div style="margin:0px;">
				<div style="color:#769846;margin-left: 18px;">Upload File:</div>
				<div style="margin-left: 27px;margin-top:5px;">Browse to attach your score assignments file with filled in scores for all students and click the upload button. </div>
			
				<div class="form-fields">			
					<input id="uploadScoreFileData" name="uploadScoreFileData" type="file" class="hideFileUploader">
					<div class="input-append" style="margin-left: 18px;margin-top: 10px;">
					    <a tabindex="0" onclick="$('input[id=uploadScoreFileData]').click();" OnKeyPress = "$('input[id=uploadScoreFileData]').click();" title="Upload Score Href" ></a><input type="text" name="uploadScoreFileDataInput" onclick="$('input[id=uploadScoreFileData]').click();" OnKeyPress = "$('input[id=uploadScoreFileData]').click();" id="uploadScoreFileDataInput"  readOnly="readOnly" title="Upload Score File Input" />
					    				   
					   <span><a class="panel_btn" href="#" id="uploadScoreButton"><fmt:message key="upload.button"/></a></span>			
					</div>
				</div>
				<div id="errorplaceScoringdiv">
				
				</div>
			</div>
		</form>
		<!-- upload grid -->
		<div class="full_main" style="margin-left:57px;">
			<div id="uploadScoresReportGridCell">
					<div id="scoresUploadReport" hidden="hidden" class="hidden"></div> <br/>
					<div id="scoresUploadReportDetails" ></div>
					<div id="uploadScores" hidden="hidden" class="hidden"></div>
				 	<div id="uploadScoresReportGridContainer" class="kite-table">
				 		<table class="responsive kite-table" id="uploadScoresGridTableId"><tr><td></td></tr></table>
						<div id="uploadScoresGridPager" style="width: auto;"></div>
				 	</div>
			</div>			
			<div id="uploadScoresGridCell" >
				<div id="uploadScoresDiv" hidden="hidden"></div>
			 	<div id=uploadScoresGridContainer class="kite-table">
			 		<table class="responsive" id="uploadScoresDataGridTableId"></table>
					<div id="uploadScoresDataGridPager" style="width: auto;"></div>
			 </div>
			</div>	
		</div>		
	</div>

	




<input id="uploadScorerUserCurrentOrgId" type="hidden" value="${user.currentOrganizationId}" />


</div>