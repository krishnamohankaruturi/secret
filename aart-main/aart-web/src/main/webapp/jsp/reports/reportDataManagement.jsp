<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

 <div class="breadcrumb">
	<h1><label id ="breadCrumMessage"><fmt:message key="label.reportdatamanagement.uploadreportdata"/> </label></h1>
	<h2><label id="breadCrumMessageTag"><fmt:message key="label.reportdatamanagement.uploadreportdata.caption"/></label></h2>	 
 </div> <!-- /breadcrumbs -->
 
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 	
			
		<div  id="reportsDataManagementTabs" class="panel_full">
			<ul class="tabs">
				<security:authorize access="hasRole('SUMMATIVE_REPORTS_UPLOAD')">
			  		<li><a href="#tabs_uploadReportData"> <fmt:message key="label.reports.uploadreportdata"/> </a></li>
			  	</security:authorize>
			  <li><a href="#tabs_downloads">  <fmt:message key="label.reports.download"/>  </a></li>		  
			  <li><a href="#tabs_availableTemplates">  <fmt:message key="label.reports.availabletemplates"/>  </a></li>
			  <li><a href="#tabs_rosters">  <fmt:message key="label.reports.rosters"/>  </a></li>
			  <li><a href="#tabs_organization">  <fmt:message key="label.reports.organization"/>  </a></li>
			  <security:authorize access="hasRole('PERM_BATCH_REGISTER')">
			  	<li><a href="#tabs_batch">  <fmt:message key="label.reports.batch"/>  </a></li>
			  </security:authorize>
			</ul>
		   
			<div id="tabs_uploadReportData">
				<jsp:include page="uploadReportData.jsp" />
			</div>
			
			<div id="tabs_downloads">
				<jsp:include page="other.jsp" />
			</div>
			
			<div id="tabs_availableTemplates">
				<jsp:include page="other.jsp" />
			</div>
			
			<div id="tabs_rosters">
				
			</div>
			
			<div id="tabs_organization">
				<jsp:include page="organizationMgmt.jsp" />
			</div>

			<div id="tabs_batch">
				<jsp:include page="batchRegistration.jsp" />
			</div>
			
	  </div>	

<script>
	  	//Setting up the tabs.
		$(function() {
			
			$('#reportsDataManagementTabs').tabs({
				beforeActivate: function(event, ui) {
						var selected = $("#reportsDataManagementTabs").tabs("option", "active");
						var indexStart = 0;
						//if the user does not have above permission, the indexes are off by one
						<security:authorize access="!hasRole('SUMMATIVE_REPORTS_UPLOAD')">
							indexStart--;
						</security:authorize>
						
		        		if(ui.index == indexStart) {
							$('#breadCrumMessage').text("Report Data Management");
							$('#breadCrumMessageTag').text("Manage Report Data.");
						} else if(ui.index == indexStart+1) {
						} else if(ui.index == indexStart+2) {
						} else if(ui.index == indexStart+3) {
						} else if(ui.index == indexStart+4) {
							$('#breadCrumMessage').text("Configuration: Organization - Create Organization");
							$('#breadCrumMessageTag').text("");
						} else if(ui.index == indexStart+5) {
							$('#breadCrumMessage').text("Configuration: Batch Registration");
							$('#breadCrumMessageTag').text("");
						} 

		        		$('#reportsDataManagementTabs li a').eq(selected).removeClass('active');
		        		$('#reportsDataManagementTabs li a').eq(ui.index).addClass('active');
				}
			});
	    });
</script>
