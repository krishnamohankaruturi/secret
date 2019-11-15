<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<link rel="stylesheet" href="<c:url value='/css/external/multidatepicker/multidatepicker.css'/>" type="text/css" />
<script type="text/javascript" src="/AART/js/external/multidatepicker/multidatepicker.js"> </script>

<div>
	<ul id="viewProjectedTestingID" class="nav nav-tabs sub-nav">
		<li class="nav-item">
			<a class="nav-link" href="#tabs_ticketing" data-toggle="tab" role="tab"><fmt:message key="label.testmanagement.projectedtesting" /></a>
		</li>
		<security:authorize access="hasRole('EDIT_DETAILED_PROJECTED_TESTING')" >
			<li class="nav-item">
				<a class="nav-link" href="#tabs_ticketing" onClick="javascript:loadUploadProjectedTestingScr(); return false;" data-toggle="tab" role="tab">Upload</a>
			</li>
		</security:authorize>	
		<security:authorize access="hasAnyRole('VIEW_DETAILED_PROJECTED_TESTING','EDIT_DETAILED_PROJECTED_TESTING')" >	
			<li class="nav-item">
				<a class="nav-link" href="#tabs_ticketing" onClick="javascript:viewMyCalendarProjTesting(); return false;" data-toggle="tab" role="tab">View My Calendar</a>
			</li>
		</security:authorize>	
	</ul>
	
	<div id="content" class="tab-content">
		<div id="tabs_ticketing" class="tab-pane" role="tabpanel">

<div id = "projectedTestingMain">
	<input id="projectedTestingUserCurrentOrgId" type="hidden" value="${user.currentOrganizationId}" />
	<input id="projectedTestingUserCurrentAccessLevel" type="hidden" value="${user.accessLevel}" />
	<input id="projectedTestingUserAssessmentProgId" type="hidden" value="${user.currentAssessmentProgramId}" />
	<div id="projectedTestingGraph">

		<div id="viewProjectBtnScreen">
			<security:authorize access="hasRole('VIEW_DISTRICT_PROJECTION_SUMMARY')" >
				<div class="projectTestTab">
					<a href="#" onclick="location.href='getDistrictSummaryReportCsv.htm'">District Summary CSV</a>
				</div>
			</security:authorize>	
		</div>
		<div style="text-align: center;">
			<span style="font-weight: bold;">System Wide Projections for Testing and Scoring</span>
		</div>
		<div id="viewTestingChart">
			<div id="verticalSVGLabel"  style="float: left"></div>
			<div id="verticalSVG" style="float: left"></div>
			<div id="horizontalSVG" style="overflow-x: scroll"></div>
		</div>
		<div id="assessmentPrgrmsLegendIcons" style="text-align: center; margin-top:-20px;">
		</div>
		<div id="projectedScoringNote">Note:Scoring not applicable to all Assessment Programs</div>
	</div>
	<div id="uploadProjectedTestingScr" class="hidden">
		<div class="full_side" >
			<form id="projectedTestingUploadForm">
		    	<div class="form-fields">
					<label for="projectedTestingFileData" class="testing_form-label">File:<span class="lbl-required">*</span></label>
					<input id="projectedTestingFileData" name="projectedTestingFileData" type="file" class="hideFileUploader">
					<div class="input-append">
					    <input name="projectedTestingFileDataInput" title="File" id="projectedTestingFileDataInput" class="input-file" readOnly="readOnly"/>
					    <a tabindex=0 class="add-on" title="CSV" onclick="uploadProjectedTestingFile(event);" 
					    	OnKeyPress ="uploadProjectedTestingFile(event);">
					    <img src="${pageContext.request.contextPath}/images/icon-csv.png" alt ="CSV icon"/></a>
					    <span tabindex=0 title="Quick help for downloading the CSV template" id="projTest_Templatedownloadquick">
					    	<img  src="${pageContext.request.contextPath}/images/quickHelp.png" alt="Quick help for downloading the CSV template"/>
					    </span>
						<br>
						<div id="errorplaceTestingdiv">
						</div>
						<span id="loadingmessageUE" class="hidden" style="padding:25px;">
							<fmt:message key="label.common.loading"/>...<img src="<c:url value='/images/ajax-loader.gif'/>"/>
						</span>
						<span><a class="panel_btn" href="#" id="uploadProjectedTesting"><fmt:message key="upload.button"/></a></span>
						<div class="QuickHelpProjectedTestingHint" >Please use the current version of the 
							<a id="projTest_Templatelink" href="getTemplate.htm?templateName=Projected_Testing_Upload_Template.CSV">Projected Testing Upload Template.</a>
						</div>
					</div>
				</div>
				<div id="projTest_TemplatedownloadquickHelpPopup" >
				   	<div id="projTest_TemplatedownloadquickHelpPopupClose" > X </div>
				   		<div id="projTest_TemplatedownloadquickHelpPopupMessage" >
				    	 <p>Please use the current version of the 
				    	 	<a id="projTest_TemplatelinkPopup" href="getTemplate.htm?templateName=Projected_Testing_Upload_Template.CSV">Projected Testing Upload Template.</a>
				    	 </p>
				   	</div>
				</div>	
			</form>
		</div>
		<div class="full_main">
			<div id="projTestGridCell">
				<div id="projTestUploadReport" hidden="hidden"></div> <br/>
					<div id="projTestUploadReportDetails" >
				</div>
			 	<div id="projTestGridContainer" class="kite-table">
			 		<table class="responsive" id="enrollmentGrid"></table>
					<div id="projTestGridPager" style="width: auto;"></div>
			 	</div>
			</div>
			 <div id="uploadProjectedTestingGridCell" >
				<div id="uploadProjectedTestingDiv" hidden="hidden"></div>
			 	<div id=uploadProjectedTestingGridContainer class="kite-table">
			 		<table class="responsive" id="uploadProjectedTestingGridTableId"></table>
					<div id="uploadProjectedTestingGridPager" style="width: auto;"></div>
			 	</div>
			</div>
        </div>	
	</div>
	<div id="viewMyCalendarPage" style="display:none;margin-left:23px;">
		<div style="margin:0px;margin-top:10px;">
			<span style="color: #94b54d;font-size: 1.1em;text-align: left;">My Projected Testing Calendar</span>
			<span style="color: #94b54d;font-size: 1.1em;float: right; margin-right: 190px;">Assessment Program: ${user.currentAssessmentProgramName}</span>
		</div>
		<div style="margin-top:10px;"></div>
		<!-- View Calendar Grid -->
		<div class="full_main"  style="width: 100% !important; margin-left: -10px;">
			<div id="viewMyCalendarSessionGridCell" >
			<div id="viewMyCalendarSession" hidden="hidden"></div>
			 	<div id="viewMyCalendarSessionGridContainer" class="kite-table">
			 		<table class="responsive" id="viewMyCalendarSessionGridTableId"></table>
					<div id="viewMyCalendarSessionGridPager" style="width: auto;"></div>
			 	</div>
			</div>
		</div>
		<security:authorize access="hasRole('EDIT_DETAILED_PROJECTED_TESTING')" >	
		<div style="margin:0px;">
		   <form class="form" id="addNewProjTesting" name="addNewProjTesting">
		   <div id="errorplaceTestingdivId">
				
				</div>
			<div id="addNewRowLabel">Add New Row</div>
			<div style="margin-top:10px;margin-left:-25px;">
				<div class="form-fields required" style="display:inline-block;">
					<label for="projectedTestingDistrict" class="addNewRowProjType">District <span class="lbl-required">*</span></label>
					<select id="projectedTestingDistrict" title="District" class="bcg_select required" name="projectedTestingDistrict">
						<option value="">District</option>
					</select>
				</div>
				<div class="form-fields required" style="display:inline-block;">
					<label for="projectedTestingSchool" class="addNewRowProjType">School <span class="lbl-required">*</span></label>
					<select id="projectedTestingSchool" title="School" class="bcg_select required" name="projectedTestingSchool">
						<option value="">School</option>
					</select>
				</div>
				<div class="form-fields required" style="display:inline-block;">
					<label for="projectedTestingGrade" class="addNewRowProjType">Grade <span class="lbl-required">*</span></label>
					<select id="projectedTestingGrade" title="Grade" class="bcg_select required" name="projectedTestingGrade">
						  <option value="">Grade</option>
					</select>
				</div>
				<div class="form-fields required" style="display:inline-block;">
					<label for="projectedTestingType" class="addNewRowProjType">Projection Type <span class="lbl-required">*</span></label>
					<select id="projectedTestingType" title="Projection Type" class="bcg_select required" name="projectedTestingType">
						<option value="">Projection Type</option>
						<security:authorize access="hasRole('VIEW_PROJECTED_SCORING')" >
					    <option value="S">Scoring</option>
					    </security:authorize>
						<option value="T">Testing</option>
					</select>
				</div>
				
				<div id="projectedTestingMonthDiv" class="form-fields" style="display:inline-block;">
					<label for="projectedTestingMonth" class="addNewRowProjType">Month <span class="lbl-required">*</span></label>
		            <select id="projectedTestingMonth" title="Month" name="projectedTestingMonth" class="bcg_select"></select>
	           	</div>
	           	<div class="addNewMonthDatePickerdiv addNewMonthDatePicker" id="addNewMonthDatePickerdiv" style="margin: 0px 0px -6px 17px;"></div>
	       </div> 
	       </form>
		</div>
		</security:authorize>
	</div>
</div>



		</div>	
	</div>
</div>
<script>
$(function(){
	<%-- navigate to the first available tab --%>
	$('#viewProjectedTestingID li.nav-item:first a').tab('show');
});
$(function() {
	$('a[data-toggle="tab"]').on('shown.bs.tab', function(event) {
		var lastTab =$(event.relatedTarget).text();
		var curTab =$(event.target).text();
		switch(lastTab)
 		{
			case 'Upload':
				$('#uploadProjectedTestingScr').hide();
				break;
			case 'View My Calendar':
				$('#viewMyCalendarPage').hide();
				break;
			default:
			break;
 		}
		if(curTab=='Projected Testing'){
			window.location="viewProjectedTesting.htm";
/* 			$('#projectedTestingGraph').show();
			$('#projectedTestingType').val("0").trigger('change.select2');
			$(".gridAddNewMultiDatepicker #divLabelwidth").html('');
			$(".gridAddNewMultiDatepicker #gradeLabelwidth").html('');
			$(".gridAddNewMultiDatepicker #projectionTypeLabelwidth").html(''); */
		}
	});
});
</script>

<script type="text/javascript">
<security:authorize access="hasRole('EDIT_DETAILED_PROJECTED_TESTING')" >
var editProjectedTesting = true;
</security:authorize>
<security:authorize access="!hasRole('EDIT_DETAILED_PROJECTED_TESTING')" >
var editProjectedTesting = false;
</security:authorize>
</script>
<script type="text/javascript" src="/AART/js/projectedTesting.js"> </script>	
	