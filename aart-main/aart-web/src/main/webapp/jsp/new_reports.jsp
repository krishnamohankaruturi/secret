<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %> 
<link rel="stylesheet" href="<c:url value='/css/theme/configuration.css'/>" type="text/css" />
<%--<link rel="stylesheet" href="<c:url value='/css/theme/rosterreport.css'/>" type="text/css" />--%>
<link rel="stylesheet" href="<c:url value='/css/report_leftside.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/report_content.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/external/jqpagination.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/external/fixed_table_rc.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/theme/rosterreport.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/theme/studentreport.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/theme/blueprintcoveragereport.css'/>" type="text/css" />

<script type="text/javascript" src="<c:url value='/js/external/jquery-1.8.0.min.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery-ui-1.10.3.custom.min.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.bcgmultiselect.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.multiselect.filter.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/can.custom.min.js'/>"> </script> 
<script type="text/javascript" src="<c:url value='/js/external/jquery.idletimeout.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.idletimer.js'/>"> </script> 
<script type="text/javascript" src="<c:url value='/js/external/jquery.validate.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/i18n/grid.locale-en.min.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.jqGrid.min.js'/>"> </script>

<script type="text/javascript" src="<c:url value='/js/controllers/new_reportController.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/controllers/rosterReportOnline.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/controllers/studentReport.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/controllers/writingReport.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/controllers/blueprintCoverageReport.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.jqpagination.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/external/fixed_table_rc.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/recordbrowser.js'/>"> </script>

<div class="panel_full ui-tabs ui-widget ui-widget-content ui-corner-all" noborder="" style="padding-left:10px; padding-top:10px;">
<security:authorize access="hasRole('VIEW_ALL_STUDENT_REPORTS')">
	<div id="allReportsForStudentSearch">
		<form id="viewAllReportsForStudentForm">
			<img alt="search" src="images/search.png">
			<input id="studentName" type="text" readonly="true" class="input-disabled" placeholder="Student Last Name" title="Student Last Name"/> 
			or 
			<input id="stateStudentIdentifier" type="text" class="" placeholder="Student State ID" title="Student State ID"/> 
			<a class="panel_btn" href="#" id="searchForStudent" style="padding: 5px 10px;font-size: 0.85em;">Search</a>
			<label id="allReportsForStudentSearchError" class="error hidden"></label>
		</form>
	</div>
</security:authorize>
<div id="reportsTabs">
	<div id="sidebar">
		<security:authorize access="hasAnyRole('VIEW_GENERAL_STUDENT_REPORT',
	  								'VIEW_GNRL_STUDENT_RPT_BUNDLED',
	  								'VIEW_GENERAL_SCHOOL_REPORT',
	  								'VIEW_GENERAL_DISTRICT_REPORT')">
			<table id="generalAssessmentsTable" class="reportsTable hidden" role="presentation">			
				<tr><td class="generalAssessmentsHeader allHeaders" style="line-height:2;" >General Assessments</td></tr>
				<security:authorize access="hasRole('VIEW_GENERAL_STUDENT_REPORT')">
					<tr id="GEN_ST_tr" class="hidden"><td id="GEN_ST" class="reportLinks" data-report-type="general_student" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_GENERAL_STUDENT_WRITING')">
					<tr id="GEN_ST_WRITING_tr" class="hidden"><td id="GEN_ST_WRITING" class="reportLinks" data-report-type="general_student_writing" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_GNRL_STUDENT_RPT_BUNDLED')">
					<tr id="GEN_ST_ALL_tr" class="hidden"><td id="GEN_ST_ALL" class="reportLinks" data-report-type="general_student_all" data-inactive-org-req="true"></td></tr>
				</security:authorize>			
				<security:authorize access="hasRole('VIEW_GENERAL_SCHOOL_REPORT')">
 					<tr id="GEN_SS_tr" class="hidden"><td id="GEN_SS" class="reportLinks" data-report-type="general_school_summary" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_GENERAL_DISTRICT_REPORT')">
 					<tr id="GEN_DS_tr" class="hidden"><td id="GEN_DS" class="reportLinks" data-report-type="general_district_summary" data-inactive-org-req="true"></td></tr>
				</security:authorize>
			</table>
		</security:authorize>		
		
		<security:authorize access="hasAnyRole('VIEW_ALT_MONITORING_SUMMARY', 'VIEW_ALTERNATE_STUDENT_REPORT',
	  								'VIEW_ALTERNATE_ROSTER_REPORT','VIEW_ALT_STD_SUMMARY_BUNDLED_REP','VIEW_ALT_CLASSROOM_REPORT','VIEW_ALT_SCHOOL_REPORT','VIEW_ALT_SCH_SUMMARY_BUNDLED_REP','VIEW_ALT_YEAREND_STD_IND_REP','VIEW_ALT_STUDENT_SUMMARY_REP','VIEW_ALT_YEAREND_STD_BUNDLED_REP','VIEW_ALT_BLUEPRINT_COVERAGE','VIEW_ALT_YEAREND_DISTRICT_REPORT','VIEW_ALT_YEAREND_STATE_REPORT','VIEW_DLM_STUDENT_DCPS')">
			<table id="alternateAssessmentsTable" class="reportsTable hidden" role="presentation">			
				<tr><td class="alternateAssessmentsHeader allHeaders" style="line-height:2;" >Alternate Assessments</td></tr>
				<security:authorize access="hasAnyRole('VIEW_ALT_MONITORING_SUMMARY')">
					<tr id="ALT_GENERAL_REPORTS_tr" class="hidden">
						<td class="subtablereporttd">
		  					<div style="width:94%;" class="subtablereport">General Reports</div>
		  				</td>
		  			</tr>
		  		</security:authorize>
		  		
		  		<security:authorize access="hasAnyRole('VIEW_ALT_MONITORING_SUMMARY')">
					<tr id="ALT_MONITORING_SUMMARY_tr" class="hidden"><td id="ALT_MONITORING_SUMMARY" class="reportLinks" data-report-type="alternate_monitoring_summary" data-inactive-org-req="false"></td></tr>
		  		</security:authorize>
		  		
				<security:authorize access="hasAnyRole('VIEW_ALT_BLUEPRINT_COVERAGE', 'VIEW_ALTERNATE_STUDENT_REPORT',
	  								'VIEW_ALTERNATE_ROSTER_REPORT')">
	  			<tr id="ALT_ST_CR_tr" style="margin:0px;" class="hidden" colspan="2">
	  				<td class="subtablereporttd" >
	  					<div style="width:94%;" class="subtablereport">Instructionally Embedded</div>
	  				</td>
	  			</tr>
	  			<security:authorize access="hasRole('VIEW_ALT_BLUEPRINT_COVERAGE')">
					<tr id="ALT_BLUEPRINT_COVERAGE_tr" class="hidden"><td id="ALT_BLUEPRINT_COVERAGE" class="reportLinks" data-report-type="alternate_blueprint_coverage" data-inactive-org-req="false"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALTERNATE_STUDENT_REPORT')">
					<tr id="ALT_ST_tr"  class="hidden"><td id="ALT_ST" class="reportLinks" data-report-type="alternate_student" data-inactive-org-req="false"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALTERNATE_ROSTER_REPORT')">
					<tr id="ALT_CR_tr"  class="hidden"><td id="ALT_CR" class="reportLinks" data-report-type="alternate_roster" data-inactive-org-req="false"></td></tr>
				</security:authorize>
				</security:authorize>
				<security:authorize access="hasAnyRole('VIEW_ALT_YEAREND_STD_IND_REP',
								'VIEW_ALT_YEAREND_STD_BUNDLED_REP','VIEW_ALT_STUDENT_SUMMARY_REP','VIEW_ALT_YEAREND_DISTRICT_REPORT','VIEW_ALT_STD_SUMMARY_BUNDLED_REP','VIEW_ALT_CLASSROOM_REPORT','VIEW_ALT_SCHOOL_REPORT','VIEW_ALT_SCH_SUMMARY_BUNDLED_REP',
								'VIEW_ALT_YEAREND_STATE_REPORT','VIEW_DLM_STUDENT_DCPS')">
	  			<tr id="ALT_IND_ALL_tr" class="hidden">
		  			<td class="subtablereporttd">
		  				<div style="width:36%;" class="subtablereport">Year End</div>
		  			</td>
	  			</tr>	  											
				<security:authorize access="hasRole('VIEW_ALT_YEAREND_STD_IND_REP')">
					<tr id="ALT_ST_IND_tr"  class="hidden"><td id="ALT_ST_IND" class="reportLinks" data-report-type="alternate_student_individual<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALT_YEAREND_STD_BUNDLED_REP')">
					<tr id="ALT_ST_ALL_tr"  class="hidden"><td id="ALT_ST_ALL" class="reportLinks" data-report-type="alternate_student_all" data-inactive-org-req="true"></td></tr>
				</security:authorize>				
				<security:authorize access="hasRole('VIEW_DLM_STUDENT_DCPS')">					
					<tr id="ALT_ST_DCPS_tr"  class="hidden"><td id="ALT_ST_DCPS" class="reportLinks" data-report-type="alternate_student_dcps" data-inactive-org-req="true"></td></tr>
				</security:authorize>	
				<security:authorize access="hasRole('VIEW_ALT_STUDENT_SUMMARY_REP')">
					<tr id="ALT_STD_SUMMARY_tr"  class="hidden"><td id="ALT_STD_SUMMARY" class="reportLinks" data-report-type="alternate_student_summary<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALT_STD_SUMMARY_BUNDLED_REP')">
					<tr id="ALT_ST_SUM_ALL_tr"  class="hidden"><td id="ALT_ST_SUM_ALL" class="reportLinks" data-report-type="alternate_student_summary_all" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALT_YEAREND_STATE_REPORT')">
 					<tr id="ALT_SS_tr" class="hidden"><td id="ALT_SS" class="reportLinks" data-report-type="alternate_yearend_state_summary" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALT_YEAREND_DISTRICT_REPORT')">
 					<tr id="ALT_DS_tr" class="hidden"><td id="ALT_DS" class="reportLinks" data-report-type="alternate_yearend_district_summary" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALT_SCHOOL_REPORT')">
					<tr id="ALT_SCHOOL_tr"  class="hidden"><td id="ALT_SCHOOL" class="reportLinks" data-report-type="alternate_school_summary" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_ALT_SCH_SUMMARY_BUNDLED_REP')">
					<tr id="ALT_SCH_SUM_ALL_tr"  class="hidden"><td id="ALT_SCH_SUM_ALL" class="reportLinks" data-report-type="alternate_school_summary_all" data-inactive-org-req="true"></td></tr>
				</security:authorize>				
				<security:authorize access="hasRole('VIEW_ALT_CLASSROOM_REPORT')">
					<tr id="ALT_CLASS_ROOM_tr"  class="hidden"><td id="ALT_CLASS_ROOM" class="reportLinks" data-report-type="alternate_classroom" data-inactive-org-req="true"></td></tr>
				</security:authorize>				
				</security:authorize>
				
			</table>
		</security:authorize>
		
		
		
		
		
		<security:authorize access="hasAnyRole('VIEW_CPASS_ASMNT_STUDENT_IND_REP',
	  								'VIEW_CPASS_ASMNT_STUDENT_BUN_REP',
	  								'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP')">
				<table id="cpassAssessmentsTable" class="reportsTable hidden" role="presentation">
				
				<tr><td class="cpassAssessmentsHeader allHeaders" style="line-height:1;" >Career Pathways Assessments</td></tr>
				<security:authorize access="hasRole('VIEW_CPASS_ASMNT_STUDENT_IND_REP')">
					<tr id="CPASS_GEN_ST_tr" class="hidden"><td id="CPASS_GEN_ST" class="reportLinks" data-report-type="alternate_student_individual<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_CPASS_ASMNT_STUDENT_BUN_REP')">
					<tr id="CPASS_GEN_ST_ALL_tr" class="hidden"><td id="CPASS_GEN_ST_ALL" class="reportLinks" data-report-type="alternate_student_all" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<security:authorize access="hasRole('VIEW_CPASS_ASMNT_SCHOOL_DTL_REP')">
					<tr id="CPASS_GEN_SD_tr" class="hidden"><td id="CPASS_GEN_SD" class="reportLinks" data-report-type="general_school_detail" data-inactive-org-req="true"></td></tr>
				</security:authorize>
			</table>
		</security:authorize> 
		
		<security:authorize access="hasAnyRole('VIEW_KELPA_ASMNT_STUDENT_IND_REP','VIEW_KELPA_ASMNT_STUDENT_BUN_REP')">	  								
				<table id="elpaAssesmentTable" class="reportsTable hidden">							
				<tr><td class="elpaAssesmentHeader allHeaders" style="line-height:1;" >English Language Learner Assessments</td></tr>		
			 <security:authorize access="hasRole('VIEW_KELPA_ASMNT_STUDENT_IND_REP')">
					<tr id="KELPA_ST_tr" class="hidden"><td id="KELPA_ST" class="reportLinks" data-report-type="kelpa_student_individual<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true"></td></tr>
				</security:authorize>
					<security:authorize access="hasRole('VIEW_KELPA_ASMNT_STUDENT_BUN_REP')">
					<tr id="KELPA_ST_ALL_tr" class="hidden"><td id="KELPA_ST_ALL" class="reportLinks" data-report-type="kelpa_student_all" data-inactive-org-req="true"></td></tr>
				</security:authorize>
				<%--<security:authorize access="hasRole('VIEW_CPASS_ASMNT_SCHOOL_DTL_REP')">
					<tr id="CPASS_GEN_SD_tr" class="hidden"><td id="CPASS_GEN_SD" class="reportLinks" data-report-type="general_school_detail" data-inactive-org-req="true"></td></tr>
				</security:authorize> --%>
			</table>
		</security:authorize>
		
		<div>&nbsp;</div>
		
		
	</div>
	<div id="tabsHome">
		
		<div id="tabs_default" class="reportsDisplayTable hidden">
			<jsp:include page="reportsHome.jsp" />
		</div>
		
		<security:authorize access="hasAnyRole('VIEW_GENERAL_STUDENT_REPORT',
	  								'VIEW_GNRL_STUDENT_RPT_BUNDLED',
	  								'VIEW_GENERAL_SCHOOL_REPORT',
	  								'VIEW_GENERAL_DISTRICT_REPORT',
	  								'VIEW_ALTERNATE_STUDENT_REPORT',
	  								'VIEW_ALTERNATE_ROSTER_REPORT',
	  								'VIEW_ALT_YEAREND_STD_IND_REP',
	  								'VIEW_ALT_YEAREND_STD_BUNDLED_REP',
	  								'VIEW_DLM_STUDENT_DCPS',
	  								'VIEW_CPASS_ASMNT_STUDENT_IND_REP',
	  								'VIEW_CPASS_ASMNT_STUDENT_BUN_REP',
	  								'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP',
	  								'PERM_STUDENTRECORD_VIEW',
	  								'VIEW_ALT_STD_SUMMARY_BUNDLED_REP',
	  								'VIEW_ALT_SCHOOL_REPORT',
	  								'VIEW_ALT_CLASSROOM_REPORT',
	  								'VIEW_ALT_STUDENT_SUMMARY_REP',
	  								'VIEW_ALT_SCH_SUMMARY_BUNDLED_REP')">
			<div id="tabs_reportContent" class="reportsDisplayTable hidden">
				<div id="reportPane" class="reportsEmptyBlockBottom" <%--style= "overflow-y:scroll;"--%>></div>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALL_STUDENT_REPORTS')">
			<div id="tabs_allReportsForStudent" class="reportsDisplayTable hidden">
				<jsp:include page="allReportsForStudent.jsp" />
			</div>
		</security:authorize>
	</div>
</div>

</div>

<c:if test="${user.contractingOrgDisplayIdentifier == 'KS'}">
	<input id="isNewReportsKSUser" type="hidden" value="true">
</c:if>
<c:if test="${user.contractingOrgDisplayIdentifier != 'KS'}">
	<input id="isNewReportsKSUser" type="hidden" value="false">
</c:if>

<c:if test="${user.contractingOrgDisplayIdentifier == 'AK'}">
	<input id="isNewReportsAKUser" type="hidden" value="true">
</c:if>
<c:if test="${user.contractingOrgDisplayIdentifier != 'AK'}">
	<input id="isNewReportsAKUser" type="hidden" value="false">
</c:if>

<c:if test="${user.isTeacher || user.isProctor}">
	<input id="isNewReportsTeacher" type="hidden" value="true">
</c:if>

<c:if test="${not user.isTeacher and not user.isProctor}">
	<input id="isNewReportsTeacher" type="hidden" value="false">
</c:if>

<input id="orgLevel" type="hidden" value="${user.accessLevel}">

<security:authorize access="hasRole('DATA_EXTRACTS_DLM_DS_SUMMARY')">
	<input id="isDlmDistrictDataExtract" type="hidden" value="true">
</security:authorize>
<security:authorize access="hasRole('LOCK_AMP_REPORTS')">
	<input id="isNewReportsBlockedAMP" type="hidden" value="true">
</security:authorize>
<security:authorize access="!hasRole('LOCK_AMP_REPORTS')">
	<input id="isNewReportsBlockedAMP" type="hidden" value="false">
</security:authorize>
<security:authorize access="hasRole('DYNA_BUNDLE_GENERAL_ASSESSMENT')">
	<input id="isDynamicBundleGeneralassessment" type="hidden" value="true">
</security:authorize>
<security:authorize access="hasRole('DYNA_BUNDLE_ALTERNATE_ASSESSMENT')">
	<input id="isDynamicBundleAlternateassessment" type="hidden" value="true">
</security:authorize>
<security:authorize access="hasRole('CREATE_KELPA_STUDENT_BUN_REP')">
	<input id="isDynamicBundleKelpaassessment" type="hidden" value="true">
</security:authorize>
<security:authorize access="hasRole('DYN_BUND_CARPATH_ASSESSMENT')">
	<input id="isDynamicBundleCarrerPatheassessment" type="hidden" value="true">
</security:authorize>
<security:authorize access="hasRole('CREATE_ALT_STD_SUM_DYN_BNDL_REP')">		
	<input id="isCreateStudentSummaryBundledReport" type="hidden" value="true">
</security:authorize>
<security:authorize access="!hasRole('CREATE_ALT_STD_SUM_DYN_BNDL_REP')">		
	<input id="isCreateStudentSummaryBundledReport" type="hidden" value="false">
</security:authorize>
<security:authorize access="hasRole('CREATE_AGGREGATE_CLASSROOM_CSV')">		
	<input id="isAlternateAggregateClassroomCsv" type="hidden" value="true">
</security:authorize>
<security:authorize access="hasRole('CREATE_AGGREGATE_SCHOOL_CSV')">		
	<input id="isAlternateAggregateSchoolCsv" type="hidden" value="true">
</security:authorize>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/new_reports.js"></script>