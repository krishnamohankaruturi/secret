<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.select2-container{
width:250px !important;
}
#register{
margin:7px 20px 0 15px !important;
}
 .ui-widget-header{
font-weight:normal !important;
}
.ui-dialog .ui-dialog-buttonpane
{
border-width:0 0 0 0 !important
}
.ui-widget-overlay{
opacity:0 !important;
}
.ui-widget-header{
border:0px solid #ddd !important;
background:none !important;
}
</style>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 	
    <security:authorize access="!hasRole('PERM_BATCH_REGISTER')"> 
        <div  class="messages">
			<span class="error_message ui-state-error permissionDeniedMessage" id="batchregistrationPermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
		</div>
    </security:authorize>
    <security:authorize access="hasRole('PERM_BATCH_REGISTER')"> 
        <div id="errorDetailsDailog" class="_bcg">
        	<div id="errorDetailsGridContainer" class="kite-table">
		 		<table class="responsive kite-table" id="errorDetailsGridTableId"><tr><td></td></tr></table>
				<div id="errorDetailsGridPager" style="width: auto;"></div>
		 	</div>
        </div>
        <div id="confirmDLMMultiAssign"></div>
		<div id="batchRegContainer"  class="form">
			<div class="btn-bar">
				<div id="batchRegErrors" class="error"></div>
				<div id="batchReportMessage" style="padding:20px" class="hidden"></div> 
			</div>
		    <div class="form-fields">
				<label for="assessmentPrograms" class="field-label isrequired">Assessment Program:<span class="lbl-required">*</span></label>			
				<select id="assessmentPrograms" title="Assessment Program" class="bcg_select required" name="assessmentPrograms">
					<option value="">Select</option>
				</select>
			</div>
			<div style="float:right;">
				<button class="btn_blue" id="ampImportBtn">AMP/Questar Import XML</button>
				<button class="btn_blue" id="ampProcessBtn">AMP/Questar Process</button>
			</div>
			<form id="batchRegForm" name="batchRegForm" class="form">
				<div class="form-fields hidden">
					<label for="testingPrograms" class="field-label isrequired">Testing Program:<span class="lbl-required">*</span></label>			
					<select id="testingPrograms" title="Testing Program" class="bcg_select required" name="testingPrograms">
						<option value="">Select</option>
					</select>
				</div>
		        <div class="form-fields hidden">
					<label for="assessments" class="field-label">Assessment:</label>			
					<select id="assessments" title="Assessment" class="bcg_select required" name="assessments">
						<option value="">Select</option>
					</select>
				</div>
				<div class="form-fields hidden">
					<label for="testTypes" class="field-label">Test Type:</label>			
					<select id="testTypes" title="Test Type" class="bcg_select required" name="testTypes">
						<option value="">Select</option>
					</select>
				</div>		
				<div class="form-fields hidden">
					<label for="courses" class="field-label" id="labelCourse">Course:</label>			
					<select id="courses" title="Course" class="bcg_select required" name="courses">
						<option value="">Select</option>
					</select>
				</div>
				<div class="form-fields hidden">
					<label for="grades" class="field-label" id="labelGrade">Grade:</label>			
					<select id="grades" title="Grade" class="bcg_select required" name="grades">
						<option value="">Select</option>
					</select>
				</div>
				<div class="form-fields hidden bProcess" >
					<label for="testEnrollmentMethod" class="field-label isrequired">Auto-enrollment method:<span class="lbl-required">*</span></label>			
					<select id="testEnrollmentMethod" title="Auto-enrollment method" class="bcg_select required" name="testEnrollmentMethod">
						<option value="">Select</option>
					</select>
				</div>
				<div class="form-fields hidden testWindowDiv">
					<label for="testWindow" class="field-label isrequired">Test Window:<span class="lbl-required">*</span></label>			
					<select id="testWindow" title="Test Window" class="bcg_select required" name="testWindow">
						<option value="">Select</option>
					</select>
					<label id="testWindowDates" class="label-grey"></label>
				</div>
				<div class="form-fields hidden fixed" >
					<label for="dlmFixedGrades" class="field-label isrequired">Grade:<span class="lbl-required">*</span></label>			
					<select id="dlmFixedGrades" title="Grade" class="bcg_select required" name="dlmFixedGrades">
						<option value="">Select</option>
					</select>
				</div>
		        <div id="register" class="btn-bar">
	               <button class="btn_blue" id="registerBtn">Register</button> 
	    		    <label id="progress"></label>			
		        </div>
		        <div id="history" class="btn-bar">
	    		            <br/>
			            <a href="#" id="showHistory">Show Submission History</a>
			            <br/>
		        </div>
		        <div id="historydetails" class="btn-bar hidden">
				  <div id="fromDateDiv" class="form-fields">
	          		<label for="fromDate" class="field-label">From:<span class="lbl-required">*</span></label>
	           		<input id="fromDate" name="fromDate" class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/>
				  </div>
				  <div id="toDateDiv" class="form-fields">
	          		<label for="toDate" class="field-label">To:<span class="lbl-required">*</span></label>
	           		<input id="toDate" name="toDate" class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/>
				  </div>
				   <div id="getHistory" class="form-fields">
				   		<label  class="field-label"></label>
			            <button class ="btn_blue" id="historyBtn">Display</button>
		        </div>
		        <div id="historyDateErrors" class="error hidden"></div>
				  <div id="historyGridContainer" class="kite-table">
			 		<table class="responsive kite-table" id="historyGridTableId"><tr><td></td></tr></table>
					<div id="historyGridPager" style="width: auto;"></div>
			 	  </div>
		        </div>
	        </form>
	    </div>
	    
	    <div class='hidden'>
	    	<div id="otwDialog" class="form">
	    		<label id="otwDialogErrors" class="error"></label><br>
	    		<label for="otwId" class="field-label isrequired">Operational Test Window:<span class="lbl-required">*</span></label>			
				<select id="otwId" class="bcg_select required" name="otwId"><option value="0">Select</option></select>
	    	</div>
	    </div>
		
    </security:authorize>