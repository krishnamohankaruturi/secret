<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="viewStudentsOrgFilterForm">
		<div id="viewStudentsOrgFilter"></div>
	</form>
    <security:authorize access="hasAnyRole('PERM_STUDENTRECORD_VIEW')">
	 	<a class="panel_btn" href="#" id="viewStudentsButton">Search</a>
	</security:authorize>
</div>
<div class="full_main">
	<div id="viewStudentsGridCell">
		<div id="viewStudentsGrid" hidden="hidden"></div>
	 	<div id=viewStudentsGridContainer class="kite-table">
	 		<table class="responsive" id="viewStudentsByOrgTable"></table>
			<div id="pviewStudentsByOrgTable" style="width: auto;"></div>
	 	</div>
	</div>
	<div id="firstContactViewDiv" ></div>
	<div id="accessProfileDiv" ></div>
	<div id="viewStudentDetailsDiv" ></div>
	<div id="editStudentDiv" ></div>
</div>
	
<script type="text/javascript">
	var viewStudentLoadOnce = false;
	<security:authorize access="hasRole('PERM_STUDENTRECORD_CREATE')">
		var createStudentPermission = true;
	</security:authorize>
	<security:authorize access="hasRole('PERM_STUDENTRECORD_MODIFY')">
		var editStudentPermission = true;
	</security:authorize>
	<security:authorize access="hasRole('VIEW_STUDENT_PNP')">
	  var viewStudentPNPpermission = true;
    </security:authorize>
    
	var viewFirstContactSurvey = false;
    <security:authorize access="hasRole('VIEW_FIRST_CONTACT_SURVEY')">
   		viewFirstContactSurvey = true; 
    </security:authorize>
	
	var editFirstContactSurvey = false;
    <security:authorize access="hasRole('EDIT_FIRST_CONTACT_SURVEY')">
   		editFirstContactSurvey = true; 
    </security:authorize>
</script>