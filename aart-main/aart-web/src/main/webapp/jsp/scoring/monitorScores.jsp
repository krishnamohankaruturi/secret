<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/jsp/include.jsp"%>
</style>

<div id="monitorScoresSearch">
	<form id="searchMonitorScoresFilterForm" name="searchMonitorScoresFilterForm" class="form">
	<input id="monitorScoresUserAccesslevel" type="hidden" value="${user.accessLevel}" />
			<div class="form-fields" ${user.accessLevel < 50 ? '':'style="display:none"' }>
				<label for="monitorScoresDistrict" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
				<select id="monitorScoresDistrict" title="District" class="bcg_select" name="monitorScoresDistrict">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields" ${user.accessLevel < 70 ? '':'style="display:none"' }>
				<label for="monitorScoresSchool" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
				<select id="monitorScoresSchool" title="School" class="bcg_select" name="monitorScoresSchool">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields">
				<label for="monitorScoresContentAreas" class="field-label">SUBJECT:<span class="lbl-required">*</span></label>			
				<select id="monitorScoresContentAreas" title="Subject" class="bcg_select" name="monitorScoresContentAreas">
					<option value="">Select</option>
				</select>
			</div>
			<div class="form-fields">
			<c:choose>
				<c:when test="${user.currentAssessmentProgramName == 'CPASS'}">
					<label for="monitorScoresGrades" class="field-label">Pathway:<span
						class="lbl-required">*</span></label>
					<select id="monitorScoresGrades" class="bcg_select" title="Pathway"
						name="monitorScoresGrades">
						<option value="">Select</option>
					</select>
				</c:when>
				<c:otherwise>
					<label for="monitorScoresGrades" class="field-label">Grade:<span
						class="lbl-required">*</span></label>
					<select id="monitorScoresGrades" class="bcg_select" title="Grade"
						name="monitorScoresGrades">
						<option value="">Select</option>
					</select>
				</c:otherwise>
			</c:choose>
		</div>
			<div class="form-fields">
				<label for="monitorScoresStage" class="field-label" >STAGE:<span class="lbl-required">*</span></label>			
				<select id="monitorScoresStage" title="Stage" class="bcg_select" name="monitorScoresStage">
					<option value="">Select</option>
				</select>
			</div>	
			
		</form>	
		<div>
		<button class="btn_blue" style="float: right;margin-right: 43px;" id="monitorScoresSearchBtn">Search</button>
		</div>
		<a id='downloadCSV' ></a>
		<input id="monitorScorerUserCurrentOrgId" type="hidden" value="${user.currentOrganizationId}" />
		<input id="monitorScorerUserAssessmentProgramId" type="hidden" value="${user.currentAssessmentProgramId}" />
		<input id="monitorScorerUserAssessLevel" type="hidden" value="${user.accessLevel}" />
			<div>
		<a class="monitorScoresDownload" id="monitorScoresDownloadBtn1" style="float: right;margin-right: 2px;margin-bottom:26px; margin-top: 46px;"><img style="float: right;
		height: 24px;margin-right: -49px; margin-top: 2px;"class="downloadImg" id="monitorScoresDownloadBtn2" src="images/download.png" title="Monitor Score Download"></a>
		</div>
			 <div class="full_main monitorScoresGgrid" style="width: 92.128% !important;margin-left:14px;">
					<div id="viewOrgTestcessionGridCell" >
					<div id="viewTestOrg" hidden="hidden"></div>
		 			<div id=viewOrgMonitorGridContainer class="kite-table" style="border:0px solid rgb(216, 216, 216);">
		 			<table class="responsive" id="arts_monitorScoreTestSession"></table>
				<div id="arts_monitorTestSessionPager" style="width: auto;"></div>
		 	</div>
		</div>
	</div> 
		
		 				
	</div>
<script type="text/javascript" src="<c:url value='/js/external/select2/4.0.1/select2.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/external/papaparse.js'/>"></script>
