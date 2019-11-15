 <!-- Changes for F845 Test Assignment Errors Dashboard -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/testassignmenterrordashboard.css'/>" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/external/select2/4.0.1/select2.min.js'/>"></script>

<script type="text/javascript" src="<c:url value='/js/testassignmenterrorsdashboard.js?v=1'/>"></script>
<br>
<div  class="testassignmenterrors" >
	<div id="testassignment_orgsfilter">
		<form id="searchErrorsbyOrgForm" class ="form">
			<fieldset class="fieldset-auto" style="padding-top:0px;padding-bottom:0px">

					<div class="form-fields" id="testassignment_districtFilter">
						<label for="testassignment_districtOrgsFilter" class="field-label"><span class="lbl-required">*</span>DISTRICT:</label>
						<select id="testassignment_districtOrgsFilter" title="District" class="bcg_select required" name="testassignment_districtOrgsFilter">
							<option value="">Select</option>
						</select>
						<label id="districtErrorDis" style="display:none;font-size:0.8em;" class="error"></label>
					</div>
				
				   		<div class="form-fields" id="testassignment_schoolFilter">
							<label for="testassignment_schoolOrgsFilter" class="field-label">SCHOOL:</label>
							<select id="testassignment_schoolOrgsFilter" title="School" class="bcg_select required" name="testassignment_schoolOrgsFilter">
								<option value="">Select</option>
							</select>
				  		</div>
				  
				  			<div class="form-fields">
				  				<button class="btn_test_assginment_error_msgs" id="testassignment_searchByOrgBtn">Search</button>
				  			</div>
				  
				  
				  
			</fieldset>
		</form>
	</div>
</div>			
	
	<div class ="table_wrap">
	<div class="kite-table">		
		<table id="testAssignmentErrorTable"  class="responsive" role='presentation'></table>
		<div id="pTestAssignmentErrorTable" class="responsive"></div>
	</div>
	<div id="viewTestAssignmentRecordsPopup" class="_bcg config hidden" style="padding-left: 20px;">
		<div id="viewTestAssignmentRecordsContainer"  class="kite-table">
 			<table class="responsive" id="viewTestAssignmentRecordsGrid" role='presentation'></table>
			<div id="viewTestAssignmentRecordsGridPager"></div>
		</div>
	</div>
	
    </div>
	
