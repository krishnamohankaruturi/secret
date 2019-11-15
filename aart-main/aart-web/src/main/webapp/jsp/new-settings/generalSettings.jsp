<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" />



<div>
	<ul id="generalNav" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('PERM_ANNUAL_RESET')">
			<li class="nav-item set-academic-year">
				<a class="nav-link get-general" href="#set-academic-year" data-toggle="tab" role="tab"><fmt:message key="label.settings.general.orgAcademicYears"/></a>
			</li>
			<li class="nav-item get-general" >
				<a class="nav-link" href="#annual-reset" data-toggle="tab" role="tab"><fmt:message key="label.general.tabs_annual_fcs_reset"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_PNP_OPTIONS')">
			<li class="nav-item get-general">
				<a class="nav-link" href="#pnp-options" data-toggle="tab" role="tab"><fmt:message key="label.general.pnpOptions"/></a>
			</li>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('PERM_ANNUAL_RESET')">
			<div id="set-academic-year" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/orgAcademicYear.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/generalSettings.js'/>"></script>
			</div>
			<div id="annual-reset" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/annualFCSReset.jsp"/>
			</div>
			<script type="text/javascript" src="<c:url value='/js/configuration/orgAcademicYears.js'/>"></script>
		</security:authorize>
		<security:authorize access="hasRole('PERM_PNP_OPTIONS')">
			<div id="pnp-options" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/pnpOptionSettings.jsp"/>
			</div>
			  <script type="text/javascript" src="<c:url value='/js/configuration/pnpOptionSettings.js'/>"></script> 
		</security:authorize>
	</div>
</div>
<%-- <script type="text/javascript" src="<c:url value='/js/configuration/generalSettings.js'/>"> </script> --%>
