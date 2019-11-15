<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<security:authorize access="hasAnyRole(
	'VIEW_GENERAL_STUDENT_REPORT',
	'VIEW_GENERAL_STUDENT_WRITING',
	'VIEW_GNRL_STUDENT_RPT_BUNDLED',
	'VIEW_GENERAL_SCHOOL_REPORT',
	'VIEW_GENERAL_DISTRICT_REPORT')">
	
	<jsp:include page="reports-common.jsp"/>
	<link rel="stylesheet" href="<c:url value='/css/theme/studentreport.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/css/report_content.css'/>" type="text/css" />
	
	<ul id="generalAssessmentNav" class="nav nav-tabs sub-nav reports-nav">
		<security:authorize access="hasRole('VIEW_GENERAL_STUDENT_REPORT')">
			<c:set var="reportCode" value="GEN_ST" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<li class="nav-item">
						<a id="${reportCode}" class="nav-link" href="#student-individual" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="general_student" data-inactive-org-req="true">
							<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GENERAL_STUDENT_WRITING')">
			<c:set var="reportCode" value="GEN_ST_WRITING" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<li class="nav-item">
						<a id="${reportCode}" class="nav-link" href="#student-writing" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="general_student_writing" data-inactive-org-req="true">
							<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GNRL_STUDENT_RPT_BUNDLED')">
			<c:set var="reportCode" value="GEN_ST_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<li class="nav-item">
						<a id="${reportCode}" class="nav-link" href="#student-bundled" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="general_student_all" data-inactive-org-req="true">
							<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GENERAL_SCHOOL_REPORT')">
			<c:set var="reportCode" value="GEN_SS" />
				<c:forEach items="${reportCategories}" var="reportCategory">
					<c:if test="${reportCategory.categoryCode == reportCode}">
						<li class="nav-item">
							<a id="${reportCode}" class="nav-link" href="#general-school" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="general_school_summary" data-inactive-org-req="true">
								<c:out value="${reportCategory.categoryName}"/>
							</a>
						</li>
					</c:if>
				</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GENERAL_DISTRICT_REPORT')">
			<c:set var="reportCode" value="GEN_DS" />
				<c:forEach items="${reportCategories}" var="reportCategory">
					<c:if test="${reportCategory.categoryCode == reportCode}">
						<li class="nav-item">
							<a id="${reportCode}" class="nav-link" href="#general-district" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="general_district_summary" data-inactive-org-req="true">
								<c:out value="${reportCategory.categoryName}"/>
							</a>
						</li>
					</c:if>
				</c:forEach>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content report-tab-content">
		<security:authorize access="hasRole('VIEW_GENERAL_STUDENT_REPORT')">
			<c:set var="reportCode" value="GEN_ST" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-individual" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GENERAL_STUDENT_WRITING')">
			<c:set var="reportCode" value="GEN_ST_WRITING" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-writing" class="tab-pane">
					</div>
					<script type="text/javascript" src="<c:url value='/js/controllers/writingReport.js'/>"></script>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GNRL_STUDENT_RPT_BUNDLED')">
			<c:set var="reportCode" value="GEN_ST_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-bundled" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GENERAL_SCHOOL_REPORT')">
			<c:set var="reportCode" value="GEN_SS" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="general-school" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_GENERAL_DISTRICT_REPORT')">
			<c:set var="reportCode" value="GEN_DS" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="general-district" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
	</div>

	<script type="text/javascript" src="<c:url value='/js/reports-ui/general-assessment/generalAssessment.js'/>"></script>	
</security:authorize>
