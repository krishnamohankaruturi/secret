<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 	
    <security:authorize access="!hasRole('PERM_BATCH_REPORT')"> 
        <div class="messages">
			<span class="error_message ui-state-error permissionDeniedMessage" id="batchReportPermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
		</div>
    </security:authorize>
    <security:authorize access="hasRole('PERM_BATCH_REPORT')"> 
        <div id="errorReportDetailsDailog" class="_bcg">
        	<div id="errorReportDetailsGridContainer" class="kite-table">
		 		<table class="responsive kite-table" role='presentation' id="errorReportDetailsGridTableId"><tr><td></td></tr></table>
				<div id="errorReportDetailsGridPager" style="width: auto;"></div>
		 	</div>
        </div>
        <div id="batchReportContainer" class="form">
			<div class="btn-bar">
				<div id="batchReportErrors" class="error"></div>
				<div id="batchReportingMessage" style="padding:20px" class="hidden"></div> 
			</div>
			<form id="batchReportForm" name="batchReportForm" class="form">
			
		    <div class="form-fields">
				<label for="assessmentReportPrograms" class="field-label isrequired">Assessment Program: <span class="lbl-required">*</span></label>			
				<select id="assessmentReportPrograms" title="Assessment Program" class="bcg_select required" name="assessmentReportPrograms">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields">
				<label for="reportTestingPrograms" class="field-label">Testing Program: <span class="lbl-required">*</span></label>			
				<select id="reportTestingPrograms" title="Testing Program" class="bcg_select required" name="reportTestingPrograms">
					<option value="">Select</option>
				</select>
				<span class="error" id="testingProgramError" style="display: none;position: relative;
				top: 30px;right: 253px;font-size: 0.8em;">This field is required.</span>
			</div><br/>
			<div class="form-fields" id="reportingSubjectID">
				<label for="coursesReport" class="field-label">Subject: <span class="lbl-required">*</span></label>			
				<select id="coursesReport" title="Subject" class="bcg_select required" name="coursesReport">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields" id="reportingGradeID">
				<label for="gradesReport" class="field-label">Grade: <span class="lbl-required">*</span></label>			
				<select id="gradesReport" title="Grade" class="bcg_select required" name="gradesReport">
					<option value="">Select</option>
				</select>
			</div>			
			<br/>
			
			<div class="form-fields" style="width: auto !important;height: auto !important; margin-bottom:20px;" >
				<label for="process" class="field-label">Process <span class="lbl-required">*</span></label><br/>				
				<input type="checkbox" id="processReport" name="processReport" value="true" class="processCheck" title="<fmt:message key="label.batch.reporting.process1"/>"/> <fmt:message key="label.batch.reporting.process1"/> <br/>
				<input type="checkbox" id="processByStudentId" name="processByStudentId" value="true" class="processCheck2" title="<fmt:message key="label.batch.reporting.processbystudent"/>"/> <fmt:message key="label.batch.reporting.processbystudent"/>	<br/>
					&nbsp;&nbsp;&nbsp;
					<input type="radio" value="1" name="generateForSpecificStudentOrAllInDistrict" class="processCheck3" title="<fmt:message key="label.batch.reporting.process.specific.isr"/>"/><fmt:message key="label.batch.reporting.process.specific.isr"/> &nbsp;&nbsp;
					<input type="radio" value="2" name="generateForSpecificStudentOrAllInDistrict" class="processCheck3" title="<fmt:message key="label.batch.reporting.process.specific.district"/>"/><fmt:message key="label.batch.reporting.process.specific.district"/> <br/>
				<input type="checkbox" id="generateStudentReport" name="generateStudentReport" value="true" class="processCheck" title="<fmt:message key="label.batch.reporting.process2"/>"/> <fmt:message key="label.batch.reporting.process2"/><br/>				
				<input type="checkbox" id="generateSchoolAndDistrictReport" name="generateSchoolAndDistrictReport" value="true"  class="processCheck" title="<fmt:message key="label.batch.reporting.process3"/>"/> <fmt:message key="label.batch.reporting.process3"/> <br/>
				<input type="checkbox" id="generateSchoolFilesOfStudentReport" name="generateSchoolFilesOfStudentReport" value="true"  class="processCheck" title="<fmt:message key="label.batch.reporting.process4"/>"/> <fmt:message key="label.batch.reporting.process4"/>  <br/>
				<input type="checkbox" id="generateDistrictFilesOfStudentReport" name="generateDistrictFilesOfStudentReport" value="true"  class="processCheck" title="<fmt:message key="label.batch.reporting.process5"/>"/> <fmt:message key="label.batch.reporting.process5"/> <br/>
				
				<input type="checkbox" id="generateStudentSummaryBundledBySchool" name="generateStudentSummaryBundledBySchool" value="true"  class="processCheck" title="<fmt:message key="label.batch.reporting.student.summary.bundled.school"/>"/> <fmt:message key="label.batch.reporting.student.summary.bundled.school"/> <br/>
				<input type="checkbox" id="generateStudentSummaryBundledByDistrict" name="generateStudentSummaryBundledByDistrict" value="true"  class="processCheck" title="<fmt:message key="label.batch.reporting.student.summary.bundled.district"/>"/> <fmt:message key="label.batch.reporting.student.summary.bundled.district"/> <br/>
				<input type="checkbox" id="generateSchoolSummaryBundled" name="generateSchoolSummaryBundled" value="true"  class="processCheck" title="<fmt:message key="label.batch.reporting.school.summary.bundled"/>"/> <fmt:message key="label.batch.reporting.school.summary.bundled"/>
				<span class="error" id="processSubmitError" style="display: none;position: relative;top: 20px;right: 447px;
    			font-size: 0.8em;">Please select a process to submit.</span>
			</div>
			
			<div class="form-fields" id="process" class="btn-bar" style="width: auto !important;height: auto !important;" >
				<a class="panel_btn btn_right" href="#" id="processBtn">Start process</a>
				<label id="reportProcessProgress"></label>			
				<input type="hidden" id="batchrptstudid" name="batchrptstudid" class="field-label" />
				<input type="hidden" id="studentCalc" name="studentCalc" class="field-label" />
				<input type="hidden" id="checkPS" name="checkPS" value="false" class="field-label" />
		     </div>
	        
	        <div id="ReportConfirmationDiv"></div>
	 	 </form>
		</div>    
		<div id="batchReportHistoryContainer" class="form">   
		<form id="batchReportHistoryForm" name="batchReportHistoryForm" class="form">
			
	        <div id="reportingHistorydetails" class="btn-bar">
	          <label class="field-label">History</label>
			  <div id="reportFromDateDiv" class="form-fields" style="width:18%">
          		<label for="reportFromDate" class="field-label">From:<span class="lbl-required">*</span></label>
           		<input id="reportFromDate" name="reportFromDate" class="input-large" placeholder="dd/mm/yyyy" title="dd/mm/yyyy" style="width:100%"/>
			  </div>
			  <div id="reporToDateDiv" class="form-fields" style="width:18%">
          		<label for="reportToDate" class="field-label">To:<span class="lbl-required">*</span></label>
           		<input id="reportToDate" name="reportToDate" class="input-large" placeholder="dd/mm/yyyy" title="dd/mm/yyyy" style="width:100%"/>
			  </div>
			  <div id="getReportHistory" class="form-fields"  style="width:10%">
			  	<a class="panel_btn btn_right" href="#" id="reportHistoryBtn">Display</a>
	          </div>
	         </div>
	         
	        <div id="batchReportHistoryDateErrors" class="error hidden"></div>
	        <div class="table_wrap">
				<div id="batchReportHistoryGridContainer" class="kite-table">
					<table id="batchReportHistoryGridTableId" class="responsive"></table>
					<div id="pbatchReportHistoryGridTableId" class="responsive"></div>
				</div>
			</div>
			</form>
	 	 </div> 	
	        
		<script type="text/javascript" src="<c:url value='/js/configuration/batchReportingReady.js'/>"> </script>	

	 </security:authorize>