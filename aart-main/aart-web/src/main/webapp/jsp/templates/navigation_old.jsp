<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="wrap_bcg">
	<div class="nav">
		<ul>
			<c:choose>
				<c:when test="${current eq 'userHome'}">
					<li class="current">
						<a href="<c:url value='/userHome.htm'/>"><fmt:message key="label.userHome.home"/></a>
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<a href="<c:url value='/userHome.htm'/>"><fmt:message key="label.userHome.home"/></a>
					</li>
				</c:otherwise>
			</c:choose>
			
			<security:authorize access="hasRole('PERM_ROLE_VIEW') OR hasRole('SUMMATIVE_REPORTS_UPLOAD') OR hasRole('PERM_BATCH_REGISTER') OR 
			hasRole('PERM_ORG_VIEW') OR hasRole('PERM_STUDENTRECORD_VIEW') OR hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL') OR 
				hasRole('PERM_USER_VIEW') OR hasRole('QUALITY_CONTROL_COMPLETE') ">
			<li><a href="<c:url value='/configuration.htm'/>"><fmt:message key="label.nav.configuration"/></a></li>
			</security:authorize>
		
			<security:authorize access="hasRole('PERM_TESTSESSION_VIEW')">
			<li><a href="<c:url value='/viewTestSessions.htm'/>"><fmt:message key="label.nav.testmanagement"/></a></li>
			</security:authorize>

			<security:authorize access="hasRole('PERM_SCORING_VIEW') AND hasAnyRole('PERM_SCORING_ASSIGNSCORER','PERM_SCORE_CCQ_TESTS','PERM_SCORING_MONITORSCORES','PERM_SCORE_UPLOADSCORER','PERM_MONITOR_HARM_TO_SELF')"> 
				<li><a href="<c:url value='/viewScoringTab.htm'/>"><fmt:message	key="label.nav.scoring" /></a></li>
			</security:authorize>
			
			<security:authorize access="hasAnyRole('PERM_INTERIM_ACCESS')">
				<c:if test="${user.currentAssessmentProgramName == 'KAP'}">
					<li><a href="<c:url value='/interim.htm'/>"><fmt:message
								key="label.nav.interim" /></a></li>
				</c:if>
			</security:authorize>

			<security:authorize access="hasAnyRole('PERM_REPORT_VIEW','PD_TRAINING_EXPORT_FILE_CREATOR', 'PERM_DLM_TRAINING_STATUS_EXTRACT')">
				<li ><a href="<c:url value='/reports.htm'/>"><fmt:message key="label.nav.reports"/></a></li>
			</security:authorize>
			
			<security:authorize access="hasAnyRole('VIEW_DASHBOARD_MENU')">
				<li> <a href="<c:url value='/dashboard.htm'/>"><fmt:message key="label.nav.dashboard"/></a>
			</security:authorize>
			 <security:authorize access="hasRole('VIEW_TOOLS_MENU')"> 
				<%-- <li><a href="<c:url value='/viewToolsTab.htm'/>"><fmt:message	key="label.nav.tools" /></a></li> --%>
				<li><a href="<c:url value='/viewToolsTab.htm'/>"><fmt:message	key="label.nav.tools" /></a></li>
   			 </security:authorize> 
			
				<li ><a href="<c:url value='/helpTab.htm'/>"><fmt:message key="label.nav.help"/></a></li>
									
			<!-- <li><a href="<c:url value='/comingSoon.htm'/>"><fmt:message key="label.nav.testbuilder"/></a></li> -->
			<%-- <security:authorize access="hasRole('CETE_SYS_ADMIN') OR hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL') OR
					hasRole('PERM_STUDENTRECORD_VIEW') OR hasRole('PERM_TESTSESSION_VIEW') OR hasRole('QUALITY_CONTROL_COMPLETE')">
				<li  class="last"><a href="<c:url value='/siteMap.htm'/>"><fmt:message key="label.nav.sitemap"/></a></li>
			</security:authorize> --%>
		</ul>
	</div><!-- /nav -->
	
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