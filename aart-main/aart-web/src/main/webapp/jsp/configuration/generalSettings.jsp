<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" />

<script type="text/javascript" src="<c:url value='/js/configuration/generalSettings.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/configuration/orgAcademicYears.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/configuration/pnpOptionSettings.js'/>"> </script>


<div style="width:98%;margin-left:1%;margin-bottom:0px;padding-bottom:0px;">
<div id="generalTabs" class="panel_full" style="padding-top: 10px;">
	<ul class="tabs" style="padding-top:0px;">
		<security:authorize access="hasRole('PERM_ANNUAL_RESET')">
			<li><a href="#tabs_general_orgAcademicYears" id="organizationAcademicYears"> <fmt:message key="label.settings.general.orgAcademicYears"/> </a></li>
			<li><a href="#tabs_annual_fcs_reset" id="annualFCSReset"> <fmt:message key="label.general.tabs_annual_fcs_reset"/> </a></li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_PNP_OPTIONS')">
			<li><a href="#tabs_general_pnpOptions" id="pnpOptions"> <fmt:message key="label.general.pnpOptions"/> </a></li>
		</security:authorize>
	
	</ul>
	<security:authorize access="hasRole('PERM_ANNUAL_RESET')"> 
		<div id="tabs_general_orgAcademicYears" style="margin-right: 25px;" class="hidden">
			<jsp:include page="orgAcademicYear.jsp" />
		</div>
		<div id="tabs_annual_fcs_reset" style="margin-right: 25px;" class="hidden">
			<jsp:include page="annualFCSReset.jsp" />
		</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_PNP_OPTIONS')"> 
		<div id="tabs_general_pnpOptions" style="margin-right: 25px;" class="hidden">
			<jsp:include page="pnpOptionSettings.jsp" />
		</div>
	</security:authorize>
</div>
<p>&nbsp;</p>
</div>
