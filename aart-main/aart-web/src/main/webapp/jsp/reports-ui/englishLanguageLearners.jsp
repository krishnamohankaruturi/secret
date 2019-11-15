<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<security:authorize access="hasAnyRole('VIEW_KELPA_ASMNT_STUDENT_IND_REP','VIEW_KELPA_ASMNT_STUDENT_BUN_REP')">

	<ul id="englishLanguageLearnersAssessmentNav" class="nav nav-tabs sub-nav reports-nav">
		<security:authorize access="hasRole('VIEW_KELPA_ASMNT_STUDENT_IND_REP')">
			<c:set var="reportCode" value="KELPA_ST" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<li class="nav-item">
						<a id="${reportCode}" class="nav-link" href="#student-individual" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="kelpa_student_individual<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true">
							<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_KELPA_ASMNT_STUDENT_BUN_REP')">
			<c:set var="reportCode" value="KELPA_ST_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<li class="nav-item">
						<a id="${reportCode}" class="nav-link" href="#student-bundled" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="kelpa_student_all" data-inactive-org-req="true">
							<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content report-tab-content">
		<security:authorize access="hasRole('VIEW_KELPA_ASMNT_STUDENT_IND_REP')">
			<c:set var="reportCode" value="KELPA_ST" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-individual" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_KELPA_ASMNT_STUDENT_BUN_REP')">
			<c:set var="reportCode" value="KELPA_ST_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-bundled" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
	</div>
	
	<jsp:include page="reports-common.jsp"/>
	<script type="text/javascript" src="<c:url value='/js/reports-ui/kelpa-assessment/englishLanguageLearnerAssessment.js'/>"></script>
</security:authorize>
