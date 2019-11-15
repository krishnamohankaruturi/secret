<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 	
<security:authorize access="hasRole('PERM_REPORT_VIEW')">
		<div  id="reportsTabs" class="reports panel_full">		
			<ul class="tabs">
			  <li><a href="#tabs_perfReports"> 116<fmt:message key="label.reports.performance"/> </a></li>
			  <li><a href="#tabs_otherReports">  <fmt:message key="label.reports.other"/>  </a></li>		  
			</ul>
		   
			<div id="tabs_perfReports">
				<jsp:include page="performance.jsp" />
			</div>
			
			<div id="tabs_otherReports">
				<jsp:include page="other.jsp" />
			</div>
	  </div>
</security:authorize>

<security:authorize access="!hasRole('PERM_REPORT_VIEW')">
	<fmt:message key="error.permissionDenied"/>
</security:authorize>	
<script>
  	//Setting up the tabs.
	$(function() {
		
		$('#reportsTabs').tabs({
		});

    }); 

</script>