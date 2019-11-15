<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 

<div>
	<ul id="miscMgmt" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('CUSTOM_REPORTS')">
			<li class="nav-item">
				<a class="nav-link" href="#miscMgmt-custReports" data-toggle="tab" role="tab"><fmt:message key="tools.org.customreports.label"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('AUDIT_HISTORY')"> 
			<li class="nav-item">
				<a class="nav-link" href="#miscMgmt-auditHist" data-toggle="tab" role="tab"><fmt:message key="tools.org.audithistory.label"/></a>
			</li>
		</security:authorize>		
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('CUSTOM_REPORTS')">
			<div id="miscMgmt-custReports" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/CustomReports.jsp"/>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('AUDIT_HISTORY')"> 
			<div id="miscMgmt-auditHist" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/AuditHistory.jsp"/>
			</div>
		</security:authorize>		
	</div>	
	
</div>

<script type="text/javascript" src="<c:url value='/js/toolstab.js?v=1'/>"></script>
<!-- <link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" /> -->

<script type="text/javascript">
    jQuery.browser = {};
    (function () {
        jQuery.browser.msie = false;
        jQuery.browser.version = 0;
        if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
            jQuery.browser.msie = true;
            jQuery.browser.version = RegExp.$1;
        }
    })();
</script>

<script>
$(function(){
	<%-- navigate to the first available tab --%>
	$('#miscMgmt li.nav-item:first a').tab('show');
});
</script>

 