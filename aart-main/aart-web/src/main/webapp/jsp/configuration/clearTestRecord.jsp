<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/include.jsp" %>


<style>
#clearTestRecordHeader {
	color:#81a64c;
	font-size:20px
}

</style>

<div >
<div id="messagesClearTest" class="messages">
 <%--    <br/> <span id="test_session_created" class="info_message ui-state-highlight hidden"><fmt:message key='label.testsession.created' /></span> 
    <br/> <span id="test_session_edited" class="info_message ui-state-highlight hidden"><fmt:message key='label.testsession.update.success' /></span>  --%>
 </div>
 <div class="noBorder">
		<div id="searchFilterClearTestRecordContainer">
			<form id="searchFilterClearTestRecordForm" name="searchFilterClearTestRecordForm" class="form">
				
				
					<div class="btn-bar">
						<div id="searchFilterClearTestRecordErrors" class="Usermessages"></div>
						<span class="info_message ui-state-highlight successMessage hidden" id="clearRecordSuccessMessage" ><fmt:message key="label.config.testRecord.clear.success"/></span>
						<span class="error_message ui-state-error selectAllLabels hidden validate" id="clearRecordFailMessage"><fmt:message key="error.config.testRecord.clear.fail"/></span>
						 
					</div>
					<div style="margin-top:15px;margin-bottom:20px">
						<label class="field-label" id= "clearTestRecordHeader">Select Organization, Assessment Program and Test Record, then click Search:</span></label>			
					</div>
					
					<div>
						<label for="" class="field-label" style="font-size:18px">Organization:</label>			
					</div>
				    <div class="form-fields">
						<label for="stateOrgsClearTestRecord" for="stateOrgsClearTestRecord" class="field-label">STATE:<span class="lbl-required">*</span></label>			
						<select id="stateOrgsClearTestRecord" title="State" class="bcg_select required" name="stateOrgsClearTestRecord">
							<option value="">Select</option>
						</select>
					</div>
				
					    <div class="form-fields">
							<label for="districtOrgsClearTestRecord" for="districtOrgsClearTestRecord" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
							<select id="districtOrgsClearTestRecord" title="District" class="bcg_select required" name="districtOrgsClearTestRecord">
								<option value="">Select</option>
							</select>
						</div>
					
					<div class="form-fields">
						<label for="schoolOrgsClearTestRecord" for="schoolOrgsClearTestRecord" class="field-label">SCHOOL:</label>			
						<select id="schoolOrgsClearTestRecord" title="School" class="bcg_select" name="schoolOrgsClearTestRecord">
							<option value="">Select</option>
						</select>
					</div>					
					<div class="form-fields">
						<label for="assessmentProgramsClearTestRecord" for="assessmentProgramsClearTestRecord" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
						<select id="assessmentProgramsClearTestRecord" title="Assessment Program" class="bcg_select required" name="assessmentProgramsClearTestRecord">
							<option value="">Select</option>
						</select>
					</div>
					
					<div style="margin-top:20px">
						<label for="" class="field-label" style="font-size:18px;">Test Record:</label>		
					</div>
					
					<div class="form-fields">
							<label for="clearSubjectSelectTestRecord" for="clearSubjectSelectTestRecord" class="field-label">Subject:<span class="lbl-required">*</span></label>			
							<select id="clearSubjectSelectTestRecord" title="Subject" class="bcg_select required" name="clearSubjectSelectTestRecord">
								<option value="">Select</option>
							</select>
						</div>
					
					<div class="form-fields">
						<label for="clearTestTypeSelectTestRecord" for="clearTestTypeSelectTestRecord" class="field-label">Test Type:<span class="lbl-required">*</span></label>			
						<select id="clearTestTypeSelectTestRecord" title="Test Type" class="bcg_select required" name="clearTestTypeSelectTestRecord">
							<option value="">Select</option>
						</select>
					</div>
					
					<div class="form-fields" >
				            <button class="btn_blue" id="searchBtnClearTestRecord">Search</button> 			
				    </div>
								
			</form>
		</div>
		<div id="studentClearTestRecord">
			
			<form id="studentClearTestRecordform">

				<div style="margin-top:20px;margin-bottom:20px">
						<label for="" class="field-label" style="font-size:20px; color:#81a64c;">Test Record:</label>		
				</div>
				<div>
			        <span>	
		        		<label class="field-label" style="font-size:18px; margin-left:20px">SUBJECT:</label>
		        		<label id="selectSubjectVal" style="color:#0e76bc"></label>	
		        			
		        	</span>
		        	<span style="margin-top: 20px">
			        	<label class="field-label" style="font-size:18px;margin-left:20px">TEST TYPE:</label>
			        	<label id="selectTestTypeVal" style="color:#0e76bc"></label>		
		        	</span>
		        </div>				

				<div id="selectStudentGridTopDiv">
							 <div class="btn-bar">
							 <label style="font-size:20px; margin-left:20px;color:#81a64c;" class="field-label" for="">Select Student(s) to remove from the test record, then click Next:</label>
							 	<!-- <a class="panel_btn" href="#" id="ClearTestRecordGridTopNextBtn">Next</a> --> 
								<button id="clearTestRecordGridTopNextBtn" class="btn_blue" style="margin-left:255px">Next</button>  	
							</div>		 
				</div>
					
				<div class ="table_wrap" id="selectStudentGridDiv">
					<div class="kite-table" style="margin-left: 1px;">
						<table id="clearTestRecordStudentsByOrgTableId"  class="responsive"></table>
						<div id="pclearTestRecordStudentsByOrgTableId" class="responsive"></div>
					</div>
				</div>
				
				<div style="margin-left:672px;" class="form-fields" id="selectStudentGridBottomDiv" >
						<div class="btn-bar" style="float:right;margin-right: 86px;">
						
							<button class="btn_blue" id="clearTestRecordGridBottomNextBtn">Next</button> </div>				 
				</div>
				
				<div id="removeSelectStudentRecordButtons" style="margin-top:40px">
					<label for="" class="field-label" style="color:#81a64c;">Remove selected student(s) from the test record:</label>
					        <button class="btn_blue" id="yesBtnClearTestRecord">Yes</button> 
				            <button class="btn_blue" id="cancelBtnClearTestRecord">Cancel</button> 			
				 </div>
				 <div id="confirmDialogClearTestRecord" title="Clear Test Record">
  				<p><span class="warning" style="color: red;">Warning!</span> All captured information will be lost. </p>
  				<div><p>Proceed to cancel?</p></div>
			</div>
				
			</form>
		</div>
	
	<br/>	
</div>
</div>









