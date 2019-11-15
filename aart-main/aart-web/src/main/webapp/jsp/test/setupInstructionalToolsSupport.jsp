<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>
li.nav-item.ui-tabs-tab {
     background:#ffffff;
}

.disabled {
  color: currentColor;
  cursor: not-allowed;
  opacity: 0.5;
  text-decoration: none;
  pointer-events: none;
  display: inline-block;
}
</style>	
<div>	
	<div>
		<a href="#" class="itiback"> &lsaquo;back </a>
	</div>
	<div class="breadcrumb">
		<div>
			<h1><label id ="breadCrumMessageITI"><fmt:message key="label.iti.setupITISelectStudent" /></label></h1>
			<h2><label id="breadCrumMessageTagITI"> <fmt:message key="label.iti.selectstudents" /></label></h2>
		</div>		
	</div>
	
	
	<div  id="setupInstructionalToolsSupportTabs" style='padding-bottom:15px'>
		<ul class="nav nav-tabs sub-nav" id="instructionalToolsSupportTabs">
			<li class="nav-item" id="studentRostersID">
				<a class="nav-link" href="#tabs_studentRosters" data-toggle="tab" role="tab"><fmt:message key="label.iti.studentroster" /></a>
			</li>
			<li class="nav-item" id="selectContentID">
				<a class="nav-link" href="#tabs_selectContent" data-toggle="tab" role="tab"><fmt:message key="label.iti.selectcontent" /></a>
			</li>
			<li class="nav-item" id="assignmentID">
				<a class="nav-link" href="#tabs_assignment" data-toggle="tab" role="tab"><fmt:message key="label.iti.assignment" /></a>
			</li>
			<li class="nav-item" id="confirmationID">
				<a class="nav-link" href="#tabs_confirmation" data-toggle="tab" role="tab"><fmt:message key="label.iti.confirmation" /></a>
			</li>	
		</ul>
		<div id="content" class="tab-content">
			<div class='hidden' id="tabs_studentRosters">
				<jsp:include page="setupITIStudentRosters.jsp" />
			</div>
			<div class='hidden' id="tabs_selectContent">
				<jsp:include page="setupITIStudentContent.jsp" />
			</div>
			<div class='hidden' id="tabs_assignment">
				<jsp:include page="setupITIStudentAssignment.jsp" />
			</div>
			<div class='hidden' id="tabs_confirmation">
				<jsp:include page="setupITIStudentConfirmation.jsp" />
			</div>
		</div>
		<div id="confirmDialog"></div>
	</div>
</div>
<script>
$(function(){
	<%-- navigate to the first available tab --%>
	$('#instructionalToolsSupportTabs li.nav-item:first a').tab('show');
});
</script>
<script type="text/javascript"
	src="<c:url value='/js/test/setupInstructionalToolsSupport.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/test/setupITIStudentConfirmation.js'/>"> </script>

