<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 

<script type="text/javascript" src="<c:url value='/js/configuration/batchRegistration.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/configuration/batchReporting.js'/>"> </script>

<div style="width:98%;margin-left:1%;margin-bottom:0px;padding-bottom:0px;">
	
<div id="batchTabs" class="panel_full" style="padding-top: 10px;">
	<ul class="tabs" style="padding-top:0px;">
		<security:authorize access="hasRole('PERM_BATCH_REGISTER')">
			<li><a href="#tabs_registration" id="registration"> <fmt:message key="label.reports.batch.registration"/> </a></li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_BATCH_REPORT')"> 
	  		<li><a href="#tabs_reporting" id="reporting">  <fmt:message key="label.reports.batch.reporting"/>  </a></li>
	  	</security:authorize>
	  	<security:authorize access="hasRole('UPLOAD_WEBSERVICE')">
	  		<li><a href="#tabs_ksdewebService" id="ksdeWebService">  <fmt:message key="label.reports.batch.tabs_ksdewebService"/>  </a></li>
	  	</security:authorize>			  	
	</ul>
	
	<div class="head hidden">
		<span class="info_message" id="search_failed">
			<h1>An error occurred.</h1>
		</span>					
	</div>
	
	<security:authorize access="hasRole('PERM_BATCH_REGISTER')"> 
		<div id="tabs_registration">
			<jsp:include page="batchRegistration.jsp" />
		</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_BATCH_REPORT')"> 
	<div id="tabs_reporting" class='hidden'>
		<jsp:include page="batchReporting.jsp" />
	</div>
	</security:authorize>
	<security:authorize access="hasRole('UPLOAD_WEBSERVICE')">
	<div id="tabs_ksdewebService" class='hidden'>
		<jsp:include page="ksdewebservices.jsp" />
	</div>
	</security:authorize>
</div>
<p>&nbsp;</p>
</div>
<script type="text/javascript">
var lBatchReportingHeading = '<fmt:message key="label.batch.reporting.heading"/>';
var lBatchRegistrationHeading = '<fmt:message key="label.batch.registration.heading"/>';
var lBatchKsdeWebServiceHeading = '<fmt:message key="label.batch.ksdeWebService.heading"/>';
</script>
<script type="text/javascript" src="<c:url value='/js/configuration/batchProcessing.js'/>"> </script>