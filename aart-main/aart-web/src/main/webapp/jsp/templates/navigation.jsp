<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="wrap_bcg">
	<ul class="nav nav-pills main-nav" id="epNav">
		<li class="nav-item">
			<a class="nav-link home" href="<c:url value='/userHome.htm'/>" role="tab">
				<span class="oi oi-home"></span>
				<span class="sr-only">Home</span>
			</a>
		</li>
		<security:authorize access="hasAnyRole(
			'PERM_ROLE_VIEW',
			'SUMMATIVE_REPORTS_UPLOAD',
			'PERM_BATCH_REGISTER',
			'PERM_ORG_VIEW',
			'PERM_STUDENTRECORD_VIEW',
			'PERM_ROSTERRECORD_VIEW',
			'PERM_ROSTERRECORD_VIEWALL',
			'PERM_USER_VIEW',
			'QUALITY_CONTROL_COMPLETE') ">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab"><fmt:message key="label.nav.configuration"/></a>
				<div class="dropdown-menu">
					<security:authorize access="hasAnyRole('PERM_BATCH_REGISTER', 'PERM_BATCH_REPORT', 'UPLOAD_WEBSERVICE')">
						<a class="dropdown-item" href="<c:url value='/batchProcesses.htm'/>" role="tab"><fmt:message key="label.reports.batch"/></a>
					</security:authorize>
					<security:authorize access="hasRole('EP_MESSAGE_CREATOR')">
						<a class="dropdown-item" href="<c:url value='/createMessages.htm'/>" role="tab"><fmt:message key="label.nav.createmessage"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole('EDIT_FIRST_CONTACT_SETTINGS')">
						<a class="dropdown-item" href="<c:url value='/firstContactSettings.htm'/>" role="tab"><fmt:message key="label.firstcontact.survey"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole('PERM_ANNUAL_RESET','PERM_PNP_OPTIONS')">
						<a class="dropdown-item" href="generalConfig.htm" role="tab"><fmt:message key="label.settings.general"/></a>
					</security:authorize>
					<security:authorize access="hasRole('CHANGE_ITI_CONFIG')">
						<a class="dropdown-item" href="<c:url value='/configureITI.htm'/>" role="tab"><fmt:message key="label.reports.ITI"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole('PERM_ORG_VIEW','PERM_ORG_UPLOAD','PERM_ORG_CREATE')">
						<a class="dropdown-item" href="<c:url value='/organization.htm'/>" role="tab"><fmt:message key="label.reports.organization"/></a>
					</security:authorize>
					<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
						<a class="dropdown-item" href="<c:url value='/findQCTests.htm'/>" role="tab"><fmt:message key="label.nav.qualitycontrol"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole('SUMMATIVE_REPORTS_UPLOAD','VIEW_REPORT_CONTROL_ACCESS','VIEW_UPLOAD_RESULTS','CREATE_GRF_INFO','EDIT_GRF_INFO')">
						<a class="dropdown-item" href="<c:url value='/reportSetup.htm'/>"  role="tab"><fmt:message key="label.reports.uploadreportdata"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole('PERM_ROLE_VIEW', 'PERM_ROLE_MODIFY', 'PERM_ROLE_CREATE', 'PERM_ROLE_DELETE', 'PERM_ROLE_SEARCH')">
						<a class="dropdown-item" href="<c:url value='/roles.htm'/>"  role="tab"><fmt:message key="label.reports.roles"/></a> 
					</security:authorize>
					<security:authorize access="hasAnyRole('PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_VIEWALL','PERM_ROSTERRECORD_UPLOAD', 'PERM_ROSTERRECORD_CREATE')">
						<a class="dropdown-item" href="<c:url value='/rosters.htm'/>" role="tab"><fmt:message key="label.reports.rosters"/></a>
					</security:authorize>
					<security:authorize	access="hasAnyRole('PERM_STUDENTRECORD_VIEW','PERM_ENRL_UPLOAD','PERM_STUDENTRECORD_CREATE')">
						<a class="dropdown-item" href="<c:url value='/students.htm'/>" role="tab"><fmt:message key="label.reports.students"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole('PERM_TESTRECORD_CREATE','PERM_TESTRECORD_CLEAR','PERM_TESTRECORD_VIEW')">
						<a class="dropdown-item" href="<c:url value='/testRecords.htm'/>" role="tab"><fmt:message key="label.reports.testRecords"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole('PERM_USER_VIEW','PERM_USER_UPLOAD','PERM_USER_CREATE')">
						<a class="dropdown-item" href="<c:url value='/users.htm'/>" role="tab"><fmt:message key="label.reports.users"/></a>
					</security:authorize>
				</div>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_TESTSESSION_VIEW')">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab"><fmt:message key="label.nav.testmanagement"/></a>
				<div class="dropdown-menu">
				<security:authorize access="hasRole('HIGH_STAKES_TICKETING')"> 
					<a class="dropdown-item" href="<c:url value='/viewTestCoordination.htm'/>" role="tab"><fmt:message key="label.testmanagement.ticketing"/></a>
				</security:authorize>
				<a class="dropdown-item" href="<c:url value='/viewTestSessions.htm'/>" role="tab"><fmt:message key="label.testmanagement.testmanagement"/></a>
				<%--
				<c:if test="${user.itiStatus == true}">
					<a class="dropdown-item" href="<c:url value='/viewinstructionalSupport.htm'/>" role="tab"><fmt:message key="label.instructionalsupport"/></a>
				</c:if>
				--%>
				<c:if test="${user.IAPNavigationStatus == true}">
					<a class="dropdown-item" href="<c:url value='/instruction-planner.htm'/>" role="tab"><fmt:message key="label.instructionplanner"/></a>
				</c:if>
				<security:authorize access="hasRole('PERM_OTW_VIEW')"> 
					<a class="dropdown-item" href="<c:url value='/viewOperationalTestWindow.htm'/>" role="tab"><fmt:message key="label.testmanagement.opetationalTest"/></a>
				</security:authorize>
 				<security:authorize access="hasAnyRole('VIEW_SUMMARY_PROJECTED_TESTING','VIEW_DETAILED_PROJECTED_TESTING','EDIT_DETAILED_PROJECTED_TESTING')"> 
					<a class="dropdown-item" href="<c:url value='/viewProjectedTesting.htm'/>" role="tab"><fmt:message key="label.testmanagement.projectedtesting"/></a> 
 				</security:authorize> 
				</div>
			</li>


		</security:authorize>
		<security:authorize access="hasRole('PERM_SCORING_VIEW') AND hasAnyRole('PERM_SCORING_ASSIGNSCORER','PERM_SCORE_CCQ_TESTS','PERM_SCORING_MONITORSCORES','PERM_SCORE_UPLOADSCORER','PERM_MONITOR_HARM_TO_SELF')">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab"><fmt:message key="label.nav.scoring"/></a>
			<div class="dropdown-menu">				
  			<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">		  
  			<a class="dropdown-item" href="<c:url value='/manageScore.htm'/>" role="tab"><fmt:message key="label.scoring.mainmenu.managescoring"/></a>
  		 	</security:authorize>
			<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
				<a class="dropdown-item" href="<c:url value='/myScore.htm'/>" role="tab"><fmt:message key="label.scoring.mainmenu.ccqscoring"/></a>				
			</security:authorize>			
				</div>
			</li>
		</security:authorize>
		<security:authorize access="hasAnyRole('PERM_INTERIM_ACCESS')">
			<li class="nav-item">
				<a class="nav-link" href="<c:url value='/interim.htm'/>" role="tab"><fmt:message key="label.nav.interim"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasAnyRole('PERM_REPORT_VIEW')">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab"><fmt:message key="label.nav.reports"/></a>
				<div class="dropdown-menu">
					<security:authorize access="hasAnyRole(
							'PD_TRAINING_EXPORT_FILE_CREATOR',
							'DATA_EXTRACTS_CURRENT_ENROLLMENT',
							'DATA_EXTRACTS_PNP',
							'DATA_EXTRACTS_ROSTER',
							'DATA_EXTRACTS_USERS',
							'DATA_EXTRACTS_QUESTAR_PREID',
							'DATA_EXTRACTS_TEC_RECORDS',
							'DATA_EXTRACTS_DLM_TEST_ADMIN',
							'DATA_EXTRACTS_PNP_SUMMARY',
							'DATA_EXTRACTS_TEST_ADMIN',
							'DATA_EXTRACTS_TEST_TICKETS',
							'DATA_EXTRACTS_TESTFORM_ASIGNMENT',
							'DATA_EXTRACTS_TESTFORM_MEDIA',
							'DATA_EXTRACTS_SPL_CIRCM_CODE_REP',
							'DATA_EXTRACTS_STU_UNAME_PASSWORD',
							'DATA_EXTRACTS_KSDE_TEST_TASC',
							'DATA_EXTRACTS_SECURITY_AGREEMENT',
							'DATA_EXTRACTS_KELPA2_AGREEMENT',
							'DATA_EXTRACTS_KAP_STUDENT_SCORES',
							'DATA_EXTRACTS_DLM_BP_COVERAGE',
							'DATA_EXTRACTS_MONITOR_SCORING',
							'DATA_EXTRACTS_DLM_Gen_Research',
							'DATA_EXTRACTS_DLM_SPEC_CIRCUM',
							'DATA_EXTRACTS_DLM_INCIDENT',
							'DATA_EXTRACTS_ORGANIZATIONS',
							'DATA_EXTRACTS_FCS_REPORT',
							'DATA_EXTRACTS_TESTING_READINESS',
							'DATA_EXTRACTS_DLM_EXIT_STUDENT',
							'DATA_EXTRACTS_PNP_ABRIDGED',
							'DATA_EXTRACTS_PLTW_TESTING_READ')">
	  					<a class="dropdown-item" href="<c:url value='/dataExtracts.htm'/>" role="tab"><fmt:message key="label.nav.reports.data.extracts"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole(
							'VIEW_GENERAL_STUDENT_REPORT',
	  						'VIEW_GNRL_STUDENT_RPT_BUNDLED',
	  						'VIEW_GENERAL_SCHOOL_REPORT',
	  						'VIEW_GENERAL_DISTRICT_REPORT')">
						<a class="dropdown-item" href="<c:url value='/reports-general-assessment.htm'/>" role="tab"><fmt:message key="label.nav.reports.general.assessment"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole(
							'VIEW_ALT_MONITORING_SUMMARY',
							'VIEW_ALTERNATE_STUDENT_REPORT',
	  						'VIEW_ALTERNATE_ROSTER_REPORT',
	  						'VIEW_ALT_STD_SUMMARY_BUNDLED_REP',
	  						'VIEW_ALT_CLASSROOM_REPORT',
	  						'VIEW_ALT_SCHOOL_REPORT',
	  						'VIEW_ALT_SCH_SUMMARY_BUNDLED_REP',
	  						'VIEW_ALT_YEAREND_STD_IND_REP',
	  						'VIEW_ALT_STUDENT_SUMMARY_REP',
	  						'VIEW_ALT_YEAREND_STD_BUNDLED_REP',
	  						'VIEW_ALT_BLUEPRINT_COVERAGE',
	  						'VIEW_ALT_YEAREND_DISTRICT_REPORT',
	  						'VIEW_ALT_YEAREND_STATE_REPORT',
	  						'VIEW_DLM_STUDENT_DCPS')">
						<a class="dropdown-item" href="<c:url value='/reports-alternate-assessment.htm'/>" role="tab"><fmt:message key="label.nav.reports.alternate.assessment"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole(
							'VIEW_CPASS_ASMNT_STUDENT_IND_REP',
	  						'VIEW_CPASS_ASMNT_STUDENT_BUN_REP',
	  						'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP')">
						<a class="dropdown-item" href="<c:url value='/reports-career-pathways.htm'/>" role="tab"><fmt:message key="label.nav.reports.cpass"/></a>
					</security:authorize>
					<security:authorize access="hasAnyRole(
							'VIEW_KELPA_ASMNT_STUDENT_IND_REP',
							'VIEW_KELPA_ASMNT_STUDENT_BUN_REP')">
						<a class="dropdown-item" href="<c:url value='/reports-english-language-learners.htm'/>" role="tab"><fmt:message key="label.nav.reports.kelpa"/></a>
					</security:authorize>
					<!-- for state specific files -->
						<security:authorize access="hasAnyRole(
							'MANAGE_STATE_SPECIFIC_FILES',
							'VIEW_STATE_SPECIFIC_FILES')">
						<a class="dropdown-item" id = stateSpecificFilesId href="<c:url value='/reports-customfiles.htm'/>" role="tab"><fmt:message key="label.nav.reports.customfiles"/></a>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALL_STUDENT_REPORTS')">
						<a class="dropdown-item" href="<c:url value='/reports-all-reports-for-student.htm'/>" role="tab"><fmt:message key="label.nav.reports.allreports"/></a>
					</security:authorize>
				</div>
			</li>
		</security:authorize>
		<security:authorize access="hasAnyRole('VIEW_DASHBOARD_MENU')">
			<li class="nav-item">
				<a class="nav-link" href="<c:url value='/dashboard.htm'/>" role="tab"><fmt:message key="label.nav.dashboard"/></a>
			</li>
		</security:authorize>

		<security:authorize access="hasRole('VIEW_TOOLS_MENU')">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab"><fmt:message key="label.nav.tools"/></a>
				<div class="dropdown-menu">
<!-- 					Help Management -->
					<security:authorize access="hasRole('TOOLS_CREATE_HELP_CONTENT')"> 
						<a class="dropdown-item" href="<c:url value='/helpContent.htm'/>" role="tab"><fmt:message key="tools.helpManagement.helpContent.label"/></a>
					</security:authorize>
<!-- 					User Management -->
					<security:authorize access="hasRole('VIEW_USER_MANAGEMENT')">
						<a class="dropdown-item" href="<c:url value='/viewToolUserManagement.htm'/>" role="tab"><fmt:message key="label.toolmainmenu.UserManagement"/></a>
					</security:authorize>
<!-- 					Organization Data -->
					<security:authorize access="hasRole('TOOLS_VIEW_ORGANIZATION_DATA')"> 
						<a class="dropdown-item" href="<c:url value='/toolsOrganizationManagement.htm'/>" role="tab"><fmt:message key="tools.org.main.label"/></a>
					</security:authorize>
<!-- 					Test Reset -->
					<security:authorize access="hasRole('VIEW_TEST_RESET')">
						<a class="dropdown-item" href="<c:url value='/toolsTestReset.htm'/>" role="tab"><fmt:message key="tools.main.testreset.label"/></a>
					</security:authorize>
<!-- 					Miscellaneous -->
					<security:authorize access="hasRole('VIEW_MISCELLANEOUS')">
						<a class="dropdown-item" href="<c:url value='/toolsMiscellaneous.htm'/>" role="tab"><fmt:message key="tools.org.miscellaneous.label"/></a>
					</security:authorize>

<!-- 					Student Information -->
					<security:authorize access="hasRole('VIEW_STUDENT_INFORMATION')">
					<a class="dropdown-item" href="<c:url value='/studentInformationMgmt.htm'/>" role="tab"><fmt:message key="tools.mainmenu.StudentInformation"/></a>
					</security:authorize>

<!-- 					Permissions and Roles -->
					<c:choose>
						<c:when test="${user.groupCode == 'GSAD'}">
							<a class="dropdown-item"
								href="<c:url value='/viewToolPermissionsRoles.htm'/>" role="tab"><fmt:message
									key="tools.mainmenu.PermissionsAndRoles" /></a>
						</c:when>
						<c:otherwise>
							<security:authorize
								access="hasRole('PERMISSIONS_AND_ROLES_EXTRACT')">
								<a class="dropdown-item"
									href="<c:url value='/viewToolPermissionsRoles.htm'/>"
									role="tab"><fmt:message
										key="tools.mainmenu.PermissionsAndRoles" /></a>
							</security:authorize>
						</c:otherwise>
					</c:choose>
				</div>
			</li>
		</security:authorize>


		<li class="nav-item">
			<a class="nav-link" href="<c:url value='/helpTab.htm'/>" role="tab"><fmt:message key="label.nav.help"/></a>
		</li>
	</ul>
	
	<!-- dialog window markup -->
	<div id="session_timeout_overlay" class="hidden">
		<fmt:message key='sessionTimeOut.msg'/>
	</div>
</div>

<fmt:message key='sessionTimeOut.header' var='sessionTimeOutHeader' />

<input id="navigationRequestContextPathForCurrentPage" type="hidden" value="${pageContext.request.contextPath}">
<input id="timeOutSessionMaxInactiveIntervalOfPage" type="hidden" value="${pageContext.session.maxInactiveInterval-75}">
<input id="sessionTimeOutHeaderNavigationPage" type="hidden" value="${sessionTimeOutHeader}">

<script type="text/javascript" src="<c:url value='/js/templates/navigation.js' />"></script>

<%--  <security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">	  --%>
<%--     <script type="text/javascript" src="<c:url value='/js/tools/activationEmailTemplateView.js'/>"> </script> --%>
<%--  </security:authorize> --%>
<script>
var globalUserLevel = ${user.accessLevel};
<%--var globalUserITIStatus = ${user.itiStatus};--%>
var hasPermCustomFile = ${hasPermCustomFile};
if (hasPermCustomFile) {
	$('#stateSpecificFilesId').show();
} else {
	$('#stateSpecificFilesId').hide();			
}
</script>
