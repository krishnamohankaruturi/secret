<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<script type="text/javascript" src="<c:url value='/js/external/can.custom.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/reports-ui/ReportsConfig.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/reports-ui/reports-global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/reports-ui/bundledReportCommon.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.jqpagination.js'/>"></script>

<link rel="stylesheet" href="<c:url value='/css/external/reports-ui/report-config.css'/>" type="text/css" />

<%-- general variables or hidden inputs that should be available to all reports pages --%>

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


<script>
function setReportAccess(clickedURL){
	<c:forEach items="${reports}" var="report">
 	if('<c:out value="${report.reportCode}"/>' == clickedURL){
 		$("#"+clickedURL).attr( 'readyToView',<c:out value="${report.readyToView}" />);
 	    $("#"+clickedURL).attr( 'access',<c:out value="${report.access}" />);
 	} 
	</c:forEach>
}
</script>