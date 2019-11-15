<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<link rel="stylesheet" href="<c:url value='/css/dashboardtab.css'/>" type="text/css" />
<script type="text/javascript" src="<c:url value='/js/dashboardtab.js?v=1'/>"></script>
<script type="text/javascript" src="<c:url value='/js/external/papaparse.js'/>"></script>
<style>
#testingSummaryTable tr.altrow,#scoringSummaryTable tr.altrow {
background : none;
}
#testingSummaryTable tr.ui-state-hover,  #scoringSummaryTable tr.ui-state-hover{
background : none;
}
</style>

<input type="hidden" id="accessLevel" value="${user.accessLevel}" />
<input type="hidden" id="userOrg" value="${user.currentOrganizationId}" />
<input type="hidden" id="orgType" value="${user.currentOrganizationType}" />
<input type="hidden" id="stateId" value="${user.contractingOrgId}" />
<input type="hidden" id="userOrgName" value="${user.orgName}" />
<input type="hidden" id="userAPName" value="${user.currentAssessmentProgramName}" />
<input type="hidden" id="currentSchoolYear" value="${currentSchoolYear}"/>
<input type="hidden" id="today" value="${today}"/>
<input type="hidden" id="priorDay" value="${priorDay}"/>
<input type="hidden" id="asOfString" value="${asOfString}"/>
<input type="hidden" id="selectedStateId"  />
<input type="hidden" id="selectedDistrictId"  />
<input type="hidden" id="selectedSchoolId"  />
<div>
	<ul id="dashboard" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('VIEW_TESTING_SUMMARY_DASHBOARD')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_testing_summary" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.testingsummary" /></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_TESTING_SCORING_DASHBOARD')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_scoring_summary" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.scoringsummary" /></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_REACTIVATIONS_DASHBOARD')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_reactivations" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.reactivations" /></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_TESTING_OUTHOURS_DASHBOARD')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_outside_hours" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.outsidehours" /></a>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('VIEW_SHORT_DURATION_TEST')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_short_duration_test" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.shortdurationtest" /></a>
			</li>
		</security:authorize>
			
		<security:authorize access="hasRole('VIEW_TEST_ASSIGNMENT_ERRORS')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_test_assignment_errors" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.testassignmenterrors" /></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_KIDS_ERROR_MESSAGES')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_kids_errors" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.kiteerrors" /></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_API_ERRORS')">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_api_errors" data-toggle="tab" role="tab"><fmt:message key="label.dashboard.apierrors" /></a>
			</li>
		</security:authorize>
		
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('VIEW_TESTING_SUMMARY_DASHBOARD')">
			<div id="tabs_testing_summary" class="tab-pane" role="tabpanel">
				<div  class="testingSummary">
					<%@ include file="/jsp/testingsummarydashboard.jsp" %>
				</div>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_TESTING_SCORING_DASHBOARD')">
			<div id="tabs_scoring_summary" class="tab-pane" role="tabpanel">
				<%@ include file="/jsp/scoringsummarydashboard.jsp" %>	
			</div>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_REACTIVATIONS_DASHBOARD')">
			<div id="tabs_reactivations" class="tab-pane" role="tabpanel">
				<%@ include file="/jsp/reactivationsdashboard.jsp" %>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_TESTING_OUTHOURS_DASHBOARD')">
			<div id="tabs_outside_hours" class="tab-pane" role="tabpanel">
				<%@ include file="/jsp/outsidehoursdashboard.jsp" %>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_SHORT_DURATION_TEST')">
			<div id="tabs_short_duration_test" class="tab-pane" role="tabpanel">
				<%@ include file="/jsp/shortdurationtesting.jsp" %>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_TEST_ASSIGNMENT_ERRORS')">
			<div id="tabs_test_assignment_errors" class="tab-pane" role="tabpanel">
				<%@ include file="/jsp/testassignmenterrordashboard.jsp" %>
			</div>
		</security:authorize>
		
		<security:authorize access="hasRole('VIEW_KIDS_ERROR_MESSAGES')">
			<div id="tabs_kids_errors" class="tab-pane" role="tabpanel">
				<%@ include file="/jsp/kidserrorsdashboard.jsp" %>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_API_ERRORS')">
			<div id="tabs_api_errors" class="tab-pane" role="tabpanel">
				<%@ include file="/jsp/apierrorsdashboard.jsp" %>
			</div>
		</security:authorize>	
					
	</div>
</div>