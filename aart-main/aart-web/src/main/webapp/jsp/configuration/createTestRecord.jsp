<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/include.jsp" %>
<style>
#createTestRecordStudentHeader,
#createStudentNameStateStuIdLabel,
#selectTestRecordHeaderCTR,
#selectTestRecordFooterCTR {
	color:#81a64c;
	
}
</style>

<div >
<div id="messages" class="messages">
 </div>
 <div class="noBorder">
		<div id="searchFilterCreateTestRecordContainer">
			<form id="searchFilterCreateTestRecordForm" name="searchFilterCreateTestRecordForm" class="form">
				
				
					<div class="btn-bar">
						<div id="searchFilterCreateTestRecordErrors" class="Usermessages"></div>
						<!-- <div id="searchFilterCreateTestRecordMessage" style="padding:20px" class="hidden"></div> -->
						<span class="info_message ui-state-highlight successMessage hidden" id="createRecordSuccessMessage" ><fmt:message key="label.config.testRecord.create.success"/></span>
						<span class="error_message ui-state-error selectAllLabels hidden validate" id="createRecordFailMessage"><fmt:message key="error.config.testRecord.create.fail"/></span>
					</div>
  
					<div style="margin-top:15px;margin-bottom:20px">
						<label id="createTestRecordStudentHeader" class="field-label" style="font-size:20px" >Select Organization and Assessment Program, then click Search:</label>			
					</div>
					
				    <div class="form-fields">
						<label for="stateOrgsCreateTestRecord" for="stateOrgsCreateTestRecord" class="field-label">STATE:<span class="lbl-required">*</span></label>			
						<select id="stateOrgsCreateTestRecord" class="bcg_select required" title="State" name="stateOrgsCreateTestRecord">
							<option value="">Select</option>
						</select>
					</div>
				
					    <div class="form-fields">
							<label for="districtOrgsCreateTestRecord" for="districtOrgsCreateTestRecord" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
							<select id="districtOrgsCreateTestRecord" title="District" class="bcg_select required" name="districtOrgsCreateTestRecord">
								<option value="">Select</option>
							</select>
						</div>
					
					<div class="form-fields">
						<label for="schoolOrgsCreateTestRecord" for="schoolOrgsCreateTestRecord" class="field-label">SCHOOL:</label>			
						<select id="schoolOrgsCreateTestRecord" title="School" class="bcg_select" name="schoolOrgsCreateTestRecord">
							<option value="">Select</option>
						</select>
					</div>					
					<div class="form-fields">
						<label for="assessmentProgramsCreateTestRecord" for="assessmentProgramsCreateTestRecord" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
						<select id="assessmentProgramsCreateTestRecord" title="Assessment Program" class="bcg_select required" name="assessmentProgramsCreateTestRecord">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields" >
				            <button class="btn_blue" id="searchBtnCreateTestRecord">Search</button> 			
				    </div>
								
			</form>
		</div>
		<div id="studentCreateTestRecord">
			
			<form id="studentCreateTestRecordform">

				<div class="btn-bar">
						<label id="createStudentNameStateStuIdLabel" style="font-size:18px;  margin-top: 20px; float:left" class="field-label">Select Student(s), then click Next:</label>
						<button class="btn_blue" id="createTestRecordGridTopNextBtn" style="margin-left:582px">Next</button>  	
				</div>
					
				<div class ="table_wrap">
					<div class="kite-table" style="margin-left: 1px;" >
						<table id="createTestRecordStudentsByOrgTableId"  class="responsive"></table>
						<div id="pcreateTestRecordStudentsByOrgTableId" class="responsive"></div>
					</div>
				</div>
				
				<div style="margin-left:672px;" class="form-fields">
						<div class="btn-bar" style="float:right;margin-right: 86px;">
							<button class="btn_blue" id="createTestRecordGridBottomNextBtn">Next</button> </div>				 
				</div>
			</form>
		</div>
		<div id="selectTestRecordDiv">
			<form id="selectTestRecordForm" name="selectTestRecordForm" class="form">
				
				<div><label for="" class="field-label" id="selectTestRecordHeaderCTR">Select Test Record:</label>
				</div>
					
				<div class="form-fields">
							<label for="subjectSelectTestRecord" for="subjectSelectTestRecord" class="field-label">Subject:<span class="lbl-required">*</span></label>			
							<select id="subjectSelectTestRecord" title="Subject" class="bcg_select required" name="subjectSelectTestRecord">
								<option value="">Select</option>
							</select>
						</div>
					
					<div class="form-fields">
						<label for="testTypeSelectTestRecord" for="testTypeSelectTestRecord" class="field-label">Test Type:<span class="lbl-required">*</span></label>			
						<select id="testTypeSelectTestRecord" title="Test Type" class="bcg_select" name="testTypeSelectTestRecord">
							<option value="">Select</option>
						</select>
					</div>	
					<div>
					<label for="" class="field-label" id = "selectTestRecordFooterCTR">Register selected student to selected test record:</label>
				            <button class="btn_blue" id="yesBtnSelectTestRecord">Yes</button> 
				            <button class="btn_blue" id="cancelBtnSelectTestRecord">Cancel</button> 			
				    </div>
						
			</form>
			<div id="confirmDialogSelectTestRecord" title="Create Test Record">
  				<p><span class="warning" style="color: red;">Warning!</span> All selected information will be lost. </p>
  				<div><p>Proceed to cancel?</p></div>
			</div>
		</div>
		
	
<br />	
</div>
</div>









