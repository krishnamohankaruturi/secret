<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/apierrorsdashboard.css'/>" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/external/select2/4.0.1/select2.min.js'/>"></script>


<script type="text/javascript" src="<c:url value='/js/apierrorsdashboard.js?v=1'/>"></script>
<br>
<div  class="apierrormsgs" >
	<div id="api_orgsfilter">
		<form id="searchApiErrorsbyOrgForm" class ="form">
			<fieldset class="fieldset-auto" style="padding-top:0px;padding-bottom:0px">

					<div class="form-fields" id="api_districtFilter">
						<label for="api_districtOrgsFilter" class="field-label"></span>DISTRICT:</label>
						<select id="api_districtOrgsFilter" title="District" class="bcg_select required" name="api_districtOrgsFilter">
							<option value="">Select</option>
						</select>
						<!-- <label id="districtErrorDis" style="display:none;font-size:0.8em;" class="error"></label> -->
					</div>
				
				<div class="form-fields" id="api_schoolFilter">
					<label for="api_schoolOrgsFilter" class="field-label">SCHOOL:</label>
					<select id="api_schoolOrgsFilter" title="School" class="bcg_select required" name="api_schoolOrgsFilter">
						<option value="">Select</option>
					</select>
				</div>
				
				<div class="form-fields" id="api_orphanedFilter">
					<input type="checkbox" id="viewOrphanedRecords" title="ViewOrphanedRecords"><fmt:message key="label.dashboard.apierrors.vieworphanedrecords" />
				</div>
				
				<div class="form-fields" id="api_searchButton">
				    <button class="btn_api_msgs" id="api_searchByOrgBtn">Search</button>
				</div>
				
			</fieldset>
		</form>
	</div>			
	
	<div class ="table_wrap">
	<div class="kite-table">		
		<table id="apiErrorTable"  class="responsive" role='presentation'></table>
		<div id="pApiErrorTable" class="responsive"></div>
	</div>
	<div id="viewApiRecordsPopup" class="_bcg config hidden" style="padding-left: 20px;">
		<div id="viewApiRecordsContainer"  class="kite-table">
 			<table class="responsive" id="viewApiRecordsGrid" role='presentation'></table>
			<div id="viewApiRecordsGridPager"></div>
		</div>
	</div>
	
</div>
	
</div>