<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 
<style>
#showHistory{
text-decoration:underline !important;}
input, textarea, select, .uneditable-input
{
height:35px !important;
}
</style>

<div>
	<ul id="batchProcessNav" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('PERM_BATCH_REGISTER')">
			<li class="nav-item get-batchs">			 		
				<a class="nav-link" href="#batch_registration" data-toggle="tab" role="tab"><fmt:message key="label.reports.batch.registration"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_BATCH_REPORT')"> 
			<li class="nav-item get-batchs">
				<a class="nav-link" href="#batch_reporting" data-toggle="tab" role="tab"><fmt:message key="label.reports.batch.reporting"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('UPLOAD_WEBSERVICE')">
			<li class="nav-item get-batchs">
				<a class="nav-link" href="#batch_web-services" data-toggle="tab" role="tab"><fmt:message key="label.reports.batch.tabs_ksdewebService"/></a>
			</li>
		</security:authorize>
	</ul>
	
	
		<div id="content" class="tab-content">
		<security:authorize access="hasRole('PERM_BATCH_REGISTER')">
			<div id="batch_registration" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/batchRegistration.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/batchRegistrationReady.js'/>"> </script>	
					<script type="text/javascript" src="<c:url value='/js/configuration/batchRegistration.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_BATCH_REPORT')"> 
			<div id="batch_reporting" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/batchReporting.jsp"/>
					<script type="text/javascript" src="<c:url value='/js/configuration/batchReporting.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('UPLOAD_WEBSERVICE')">
			<div id="batch_web-services" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/ksdewebservices.jsp"/>
			</div>
		</security:authorize>
	</div>
</div>

<script type="text/javascript" src="<c:url value='/js/configuration/batchProcessing.js'/>"></script>



<script>
var lBatchReportingHeading = '<fmt:message key="label.batch.reporting.heading"/>';
var lBatchRegistrationHeading = '<fmt:message key="label.batch.registration.heading"/>';
var lBatchKsdeWebServiceHeading = '<fmt:message key="label.batch.ksdeWebService.heading"/>';
$(document).ready(function(){
	<%-- navigate to the first available tab --%>
	$('#batchProcessNav li.nav-item:first a').tab('show');
});
</script>