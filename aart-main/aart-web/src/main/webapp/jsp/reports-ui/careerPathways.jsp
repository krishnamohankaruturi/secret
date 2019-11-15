<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %><security:authorize access="hasAnyRole(
	'VIEW_CPASS_ASMNT_STUDENT_IND_REP',
	'VIEW_CPASS_ASMNT_STUDENT_BUN_REP',
	'VIEW_CPASS_ASMNT_SCHOOL_DTL_REP')">
		<ul id="careerPathwaysAssessmentNav" class="nav nav-tabs sub-nav reports-nav">		
		<security:authorize access="hasRole('VIEW_CPASS_ASMNT_STUDENT_IND_REP')">
			<c:set var="reportCode" value="CPASS_GEN_ST" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">					 
					<li class="nav-item get-report">
						<a id="${reportCode}" class="nav-link " href="#student-individual" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student_individual<c:if test="${user.isTeacher}">_teacher</c:if>" data-inactive-org-req="true"
						 >	<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>
					
				</c:if>
				</c:forEach>				
		</security:authorize>
		<security:authorize access="hasRole('VIEW_CPASS_ASMNT_STUDENT_BUN_REP')">
			<c:set var="reportCode" value="CPASS_GEN_ST_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					
					<li class="nav-item get-report">
						<a id="${reportCode}" class="nav-link" href="#student-bundled" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="alternate_student_all" data-inactive-org-req="true">
							<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>
					 </c:if>
					</c:forEach>				
		</security:authorize>
		<security:authorize access="hasRole('VIEW_CPASS_ASMNT_SCHOOL_DTL_REP')">
			<c:set var="reportCode" value="CPASS_GEN_SD" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">				
					<li class="nav-item get-report">
						<a id="${reportCode}" class="nav-link" href="#school-detail" data-toggle="tab" role="tab" data-report-code="<c:out value="${reportCode}"/>" data-report-type="general_school_detail" data-inactive-org-req="true">
							<c:out value="${reportCategory.categoryName}"/>
						</a>
					</li>						
						</c:if>
			</c:forEach>				
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content report-tab-content">
		<security:authorize access="hasRole('VIEW_CPASS_ASMNT_STUDENT_IND_REP')">
			<c:set var="reportCode" value="CPASS_GEN_ST" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-individual" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_CPASS_ASMNT_STUDENT_BUN_REP')">
			<c:set var="reportCode" value="CPASS_GEN_ST_ALL" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="student-bundled" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
		<security:authorize access="hasRole('VIEW_CPASS_ASMNT_SCHOOL_DTL_REP')">
			<c:set var="reportCode" value="CPASS_GEN_SD" />
			<c:forEach items="${reportCategories}" var="reportCategory">
				<c:if test="${reportCategory.categoryCode == reportCode}">
					<div id="school-detail" class="tab-pane">
					</div>
				</c:if>
			</c:forEach>
		</security:authorize>
	</div>
	
	<jsp:include page="reports-common.jsp"/>
	<script type="text/javascript" src="<c:url value='/js/reports-ui/cpass-assessment/careerPathwaysAssessment.js'/>"></script>	
</security:authorize>