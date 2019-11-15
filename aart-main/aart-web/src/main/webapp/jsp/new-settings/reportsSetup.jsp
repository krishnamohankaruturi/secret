<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style>
 
.select2{
width:226px !important;
}
div#uploadResultsFileDataInput .input-append .input-file{
width :226 !important;
height:33px !important;
margin-top:-3px !important;
}
div#uploadResultsFileData .input-append .add-onResult{
margin-top:-3px !important;
width :28px !important;
margin-left:-26px !important;
height:33px !important;
}
</style>
<link rel="stylesheet" href="<c:url value='/css/theme/configuration.css'/>" type="text/css" />

		<div id ="reportSetUpActions">
	<ul id="reportsetupDiv" class="nav nav-tabs sub-nav">
			<security:authorize access="hasRole('VIEW_REPORT_CONTROL_ACCESS')">
		 	<li class="nav-item get-reportsetup">	
			<%-- <a href="#tabs_reportsetup_reportaccess" id="reportSetupReportAccess"> <fmt:message key="label.reports.reportsetup.reportaccess"/> </a>	 --%>		
		<a class="nav-link" href="#tabs_reportsetup_reportaccess" data-toggle="tab" role="tab"><fmt:message key="label.reports.reportsetup.reportaccess"/></a>
		</li>
		</security:authorize>
		<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')"> 
	  			<li class="nav-item get-reportsetup">
	  		<%-- <a href="#tabs_reportsetup_uploads" id="reportSetupUploads">  <fmt:message key="label.reports.reportsetup.upload"/>  </a> --%>
	  	<a class="nav-link" href="#tabs_reportsetup_uploads" data-toggle="tab" role="tab"><fmt:message key="label.reports.reportsetup.upload"/></a>
	  	</li>
	  	</security:authorize>	
	  	<security:authorize access="hasRole('VIEW_UPLOAD_RESULTS')"> 
	  			<li class="nav-item get-reportsetup">
	  		<%-- <a href="#tabs_reportsetup_uploadResults" id="reportSetupUploadResults">  <fmt:message key="label.reports.reportsetup.uploadResults"/>  </a> --%>
	  	<a class="nav-link" href="#tabs_reportsetup_uploadResults" data-toggle="tab" role="tab"><fmt:message key="label.reports.reportsetup.uploadResults"/></a>
	  	</li>
	  	</security:authorize>
	  	<security:authorize access="hasAnyRole('CREATE_GRF_INFO','EDIT_GRF_INFO')"> 
	  			<li class="nav-item get-reportsetup">
	  		<%-- <a href="#tabs_reportsetup_manageGRF" id="reportSetupManageGRF">  <fmt:message key="label.reports.reportsetup.manageGRF"/>  </a> --%>
	  	<a class="nav-link" href="#tabs_reportsetup_manageGRF" data-toggle="tab" role="tab"><fmt:message key="label.reports.reportsetup.manageGRF"/></a>
	  	</li>
	  	</security:authorize>		  	
	</ul>
	
	<div id="content" class="tab-content">
	<security:authorize access="hasRole('VIEW_REPORT_CONTROL_ACCESS')"> 
	<div id="tabs_reportsetup_reportaccess" class="tab-pane" role="tabpanel">
		 <jsp:include page="../configuration/reportAccess.jsp" /> 
	</div>
	</security:authorize>
	<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')"> 
	<div id="tabs_reportsetup_uploads" class='hidden' class="tab-pane" role="tabpanel">
		<jsp:include page="../configuration/uploadReportData.jsp" />
	</div>
	</security:authorize>
	
	<!-- Upload Results tab -->
	<security:authorize access="hasRole('VIEW_UPLOAD_RESULTS')">
	<div id="tabs_reportsetup_uploadResults" class='hidden' class="tab-pane" role="tabpanel">
		<jsp:include page="../configuration/uploadResults.jsp" />
	</div>
	</security:authorize>
	
	<security:authorize access="hasAnyRole('CREATE_GRF_INFO','EDIT_GRF_INFO')"> 
	<div id="tabs_reportsetup_manageGRF" class='hidden' class="tab-pane" role="tabpanel">					    				
		<jsp:include page="../configuration/manageGRF.jsp" />
	</div>
	</security:authorize>
	</div>
</div>


<script type="text/javascript">
var summativeGridRefreshInterval;
</script>
<script type="text/javascript" src="<c:url value='/js/configuration/reportsSetup.js'/>"> </script>


