<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div style="width:98%;margin-left:1%;margin-bottom:0px;padding-bottom:0px;">
	
<div id="reportsSetupTabs" class="panel_full" style="padding-top: 10px;">
	<ul class="tabs" style="padding-top:0px;">
		<security:authorize access="hasRole('VIEW_REPORT_CONTROL_ACCESS')">
			<li><a href="#tabs_reportsetup_reportaccess" id="reportSetupReportAccess"> <fmt:message key="label.reports.reportsetup.reportaccess"/> </a></li>
		</security:authorize>
		<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')"> 
	  		<li><a href="#tabs_reportsetup_uploads" id="reportSetupUploads">  <fmt:message key="label.reports.reportsetup.upload"/>  </a></li>
	  	</security:authorize>	
	  	<security:authorize access="hasRole('VIEW_UPLOAD_RESULTS')"> 
	  		<li><a href="#tabs_reportsetup_uploadResults" id="reportSetupUploadResults">  <fmt:message key="label.reports.reportsetup.uploadResults"/>  </a></li>
	  	</security:authorize>
	  	<security:authorize access="hasAnyRole('CREATE_GRF_INFO','EDIT_GRF_INFO')"> 
	  		<li><a href="#tabs_reportsetup_manageGRF" id="reportSetupManageGRF">  <fmt:message key="label.reports.reportsetup.manageGRF"/>  </a></li>
	  	</security:authorize>		  	
	</ul>
	
	
	<security:authorize access="hasRole('VIEW_REPORT_CONTROL_ACCESS')"> 
	<div id="tabs_reportsetup_reportaccess">
		 <jsp:include page="reportAccess.jsp" /> 
	</div>
	</security:authorize>
	<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')"> 
	<div id="tabs_reportsetup_uploads" class='hidden'>
		<jsp:include page="uploadReportData.jsp" />
	</div>
	</security:authorize>
	
	<!-- Upload Results tab -->
	<security:authorize access="hasRole('VIEW_UPLOAD_RESULTS')">
	<div id="tabs_reportsetup_uploadResults" class='hidden'>
		<jsp:include page="uploadResults.jsp" />
	</div>
	</security:authorize>
	
	<security:authorize access="hasAnyRole('CREATE_GRF_INFO','EDIT_GRF_INFO')"> 
	<div id="tabs_reportsetup_manageGRF" class='hidden'>
		<jsp:include page="manageGRF.jsp" />
	</div>
	</security:authorize>
	
</div>
<p>&nbsp;</p>
</div>
<script type="text/javascript">

</script>
<script type="text/javascript" src="<c:url value='/js/configuration/reportsSetup.js'/>"> </script>
