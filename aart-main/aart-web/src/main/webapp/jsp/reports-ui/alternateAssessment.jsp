<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.select2-container #studentRoster{
width : 375px !important;
}

.displaySelectedMenuSpace{
	margin-top: 20px;
    margin-bottom: -20px;
}
</style>
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
	
	<jsp:include page="reports-common.jsp"/>
	
	<ul id="alternateAssessmentNav" class="nav nav-tabs sub-nav reports-nav">
		<%--
			General Reports
		--%>
		<security:authorize access="hasAnyRole('VIEW_ALT_MONITORING_SUMMARY')">
			<li class="nav-item dropdown get-report">
				<a id = "ALT_MONITORING_SUMMARY_GEN" class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">General Reports</a>
				<div class="dropdown-menu" id="generalReportDropDown">
					<security:authorize access="hasRole('VIEW_ALT_MONITORING_SUMMARY')">
						<c:set var="reportCode" value="ALT_MONITORING_SUMMARY" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">						
								<a id="${reportCode}" class="dropdown-item" href="#monitoring-summary" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_monitoring_summary" data-inactive-org-req="false">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
							
						</c:if>
						</c:forEach>
					</security:authorize>
				</div>
			</li>
		</security:authorize>
		
		<%--
			Instructionally Embedded
		--%>
		<security:authorize access="hasAnyRole(
			'VIEW_ALT_BLUEPRINT_COVERAGE',
			'VIEW_ALTERNATE_STUDENT_REPORT',
			'VIEW_ALTERNATE_ROSTER_REPORT')">
			<li class="nav-item dropdown get-report">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">Instructionally Embedded</a>
				<div class="dropdown-menu" id="instructionallyEmbeddedReportDropDown">
					<security:authorize access="hasRole('VIEW_ALT_BLUEPRINT_COVERAGE')">
						<c:set var="reportCode" value="ALT_BLUEPRINT_COVERAGE" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#blueprint-coverage" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_blueprint_coverage" data-inactive-org-req="false">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								
							</c:if>
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALTERNATE_STUDENT_REPORT')">
						<c:set var="reportCode" value="ALT_ST" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">									
								<a id="${reportCode}" class="dropdown-item" href="#student-progress" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student" data-inactive-org-req="false">									<c:out value="${reportCategory.categoryName}"/>
								</a>
								<script type="text/javascript" src="<c:url value='/js/controllers/studentReport.js'/>"> </script>
								
							</c:if>
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALTERNATE_ROSTER_REPORT')">
						<c:set var="reportCode" value="ALT_CR" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#roster-report" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_roster" data-inactive-org-req="false">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								
							</c:if>
						</c:forEach>
					</security:authorize>
				</div>
			</li>
		</security:authorize>
		
		
		<%--
			Year End
		--%>
		<security:authorize access="hasAnyRole(
			'VIEW_ALT_YEAREND_STD_IND_REP',
			'VIEW_ALT_YEAREND_STD_BUNDLED_REP',
			'VIEW_DLM_STUDENT_DCPS',
			'VIEW_ALT_STUDENT_SUMMARY_REP',
			'VIEW_ALT_STD_SUMMARY_BUNDLED_REP',
			'VIEW_ALT_YEAREND_STATE_REPORT',
			'VIEW_ALT_YEAREND_DISTRICT_REPORT',
			'VIEW_ALT_SCHOOL_REPORT',
			'VIEW_ALT_SCH_SUMMARY_BUNDLED_REP',
			'VIEW_ALT_CLASSROOM_REPORT')">
			<li class="nav-item dropdown get-report">
				<a class="nav-link dropdown-toggle" href="#" data-toggle="dropdown" role="tab">End-of-Year</a>
				<div class="dropdown-menu" id="endToYearReportDropdown">
					<security:authorize access="hasRole('VIEW_ALT_YEAREND_STD_IND_REP')">
						<c:set var="reportCode" value="ALT_ST_IND" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
								
								<a id="${reportCode}" class="dropdown-item" href="#student-individual" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student_individual<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								</c:if>
						</c:forEach>
							
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_YEAREND_STD_BUNDLED_REP')">
						<c:set var="reportCode" value="ALT_ST_ALL" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#student-bundled" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student_all" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								
							</c:if>
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_DLM_STUDENT_DCPS')">
						<c:set var="reportCode" value="ALT_ST_DCPS" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
								
								<a id="${reportCode}" class="dropdown-item" href="#student-dcps" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student_dcps" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								
							</c:if>
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_STUDENT_SUMMARY_REP')">
						<c:set var="reportCode" value="ALT_STD_SUMMARY" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#student-summary" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student_summary<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								</c:if>
						</c:forEach>
							
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_STD_SUMMARY_BUNDLED_REP')">
						<c:set var="reportCode" value="ALT_ST_SUM_ALL" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									<c:forEach items="${reports}" var="reports">
      					<c:if test="${reports.reportCode == reportCode}">
								<a id="${reportCode}" class="dropdown-item" href="#student-summary-bundled" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student_summary_all" data-inactive-org-req="true" >
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								</c:if>
						</c:forEach>
							</c:if>
							
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_YEAREND_STATE_REPORT')">
						<c:set var="reportCode" value="ALT_SS" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#yearend-state" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_yearend_state_summary" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
							
							</c:if>
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_YEAREND_DISTRICT_REPORT')">
						<c:set var="reportCode" value="ALT_DS" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#yearend-district" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_yearend_district_summary" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								
							</c:if>
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_SCHOOL_REPORT')">
						<c:set var="reportCode" value="ALT_SCHOOL" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#school-report" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_school_summary" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								
							</c:if>
						</c:forEach>
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_SCH_SUMMARY_BUNDLED_REP')">
						<c:set var="reportCode" value="ALT_SCH_SUM_ALL" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#school-summary" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_school_summary_all" data-inactive-org-req="true">
       
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								</c:if>
						</c:forEach>
							
					</security:authorize>
					<security:authorize access="hasRole('VIEW_ALT_CLASSROOM_REPORT')">
						<c:set var="reportCode" value="ALT_CLASS_ROOM" />
						<c:forEach items="${reportCategories}" var="reportCategory">
							<c:if test="${reportCategory.categoryCode == reportCode}">
									
								<a id="${reportCode}" class="dropdown-item" href="#classroom-report" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_classroom" data-inactive-org-req="true">
									<c:out value="${reportCategory.categoryName}"/>
								</a>
								
							</c:if>
						</c:forEach>
					</security:authorize>
				</div>
			</li>
		</security:authorize>
	</ul>
	<div class="displaySelectedMenuSpace">
		<span id="displaySelectedMenu" class="panel_head"></span>
	</div>
	<div id="content" class="tab-content report-tab-content">
		<%--
			General Reports
		--%>
		<security:authorize access="hasRole('VIEW_ALT_MONITORING_SUMMARY')">
			<c:set var="reportCode" value="ALT_MONITORING_SUMMARY" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="monitoring-summary" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		
		<%--
			Instructionally Embedded
		--%>
		<security:authorize access="hasRole('VIEW_ALT_BLUEPRINT_COVERAGE')">
			<c:set var="reportCode" value="ALT_BLUEPRINT_COVERAGE" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="blueprint-coverage" class="tab-pane">
					</div>					
				 	<script type="text/javascript" src="<c:url value='/js/external/fixed_table_rc.js'/>"></script>
					<link rel="stylesheet" type="text/css" href="<c:url value='/css/external/fixed_table_rc.css'/>">
					<script type="text/javascript" src="<c:url value='/js/controllers/blueprintCoverageReport.js'/>"></script>
					<link rel="stylesheet" href="<c:url value='/css/theme/blueprintcoveragereport.css'/>" type="text/css" />
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALTERNATE_STUDENT_REPORT')">
			<c:set var="reportCode" value="ALT_ST" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-progress" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALTERNATE_ROSTER_REPORT')">
			<c:set var="reportCode" value="ALT_CR" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="roster-report" class="tab-pane">
					</div>
					<script type="text/javascript" src="<c:url value='/js/controllers/rosterReportOnline.js'/>"></script>
					<link rel="stylesheet" href="<c:url value='/css/theme/rosterreport.css'/>" type="text/css" />
				</c:if>
			</c:forEach>
		</security:authorize>
		
		<%--
			Year End
		--%>
		<security:authorize access="hasRole('VIEW_ALT_YEAREND_STD_IND_REP')">
			<c:set var="reportCode" value="ALT_ST_IND" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-individual" class="tab-pane">
					</div>
					<script type="text/javascript" src="<c:url value='/js/controllers/studentReport.js'/>"></script>	
					<link rel="stylesheet" href="<c:url value='/css/theme/studentreport.css'/>" type="text/css" />			
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_YEAREND_STD_BUNDLED_REP')">
			<c:set var="reportCode" value="ALT_ST_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-bundled" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_DLM_STUDENT_DCPS')">
			<c:set var="reportCode" value="ALT_ST_DCPS" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-dcps" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_STUDENT_SUMMARY_REP')">
			<c:set var="reportCode" value="ALT_STD_SUMMARY" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-summary" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_STD_SUMMARY_BUNDLED_REP')">
			<c:set var="reportCode" value="ALT_ST_SUM_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-summary-bundled" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_YEAREND_STATE_REPORT')">
			<c:set var="reportCode" value="ALT_SS" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="yearend-state" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_YEAREND_DISTRICT_REPORT')">
			<c:set var="reportCode" value="ALT_DS" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="yearend-district" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_SCHOOL_REPORT')">
			<c:set var="reportCode" value="ALT_SCHOOL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="school-report" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_SCH_SUMMARY_BUNDLED_REP')">
			<c:set var="reportCode" value="ALT_SCH_SUM_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="school-summary" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_ALT_CLASSROOM_REPORT')">
			<c:set var="reportCode" value="ALT_CLASS_ROOM" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="classroom-report" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
	</div>
	
	<input id="isIEModelStateNewReports" type="hidden" value=${isIEModelState}>
	
	<script type="text/javascript" src="<c:url value='/js/reports-ui/alternate-assessment/alternateAssessment.js'/>"></script>
</security:authorize>
