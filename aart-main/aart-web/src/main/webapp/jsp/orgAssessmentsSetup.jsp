<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<security:authorize access="hasRole('PERM_MANAGEORGASSESSPROG_VIEW')">
<style>
.column {
    float: left;
    overflow-y: auto;
    height: 400px;
    width: 425px;
    margin-bottom: 20px;
}

.column-left {
    margin-right: 150px;
}

#orgAssessmentProgramForm {
    margin-bottom: 20px;
}

.tablecell {
    display: table;
}

.header-div {
    margin: 5px;
}

.header-div span {
    font-weight: bold;
    color: #144745;
    font-style: italic;
    text-decoration: underline;
}
</style>
<div>
    <div class="header-div">
        <span>Add Org-Assessment Program</span>
    </div>
    <form:form method="post" action="addOrgAssessmentPrograms.htm">
	    <div class="column-left column">
	        <c:forEach items="${organizations }" var="org">
	            <span class="tablecell">
	                <input type="checkbox" name="organizations" class="orgCheckbox" value="${org.id }"/>
	                <label>${org.organizationName }</label>
	            </span>
	        </c:forEach>
	    </div>
	    <div class="column">
	        <c:forEach items="${assessmentPrograms }" var="ap">
	            <span class="tablecell">
	                <input type="checkbox" class="apCheckBox" value="${ap.id }" name="assessmentPrograms"/>
	                <label>${ap.programName }</label>
	            </span>
	        </c:forEach>
	    </div>

	    <input type="submit" />
	    </form:form>
</div>
<div>
    <div class="header-div">
        <span>Delete Org-Assessment Program</span>
    </div>
    <form:form method="post" action="deleteOrgAssessmentProgram.htm">
        <select name="orgAssessmentProgramId" >
            <option value="-1">Select</option>
            <c:forEach items="${orgAssessmentPrograms }" var="orgAssessmentProgram">
                <option value="${orgAssessmentProgram.id }">${orgAssessmentProgram.organization.organizationName } - ${orgAssessmentProgram.assessmentProgram.programName }</option>
            </c:forEach>
        </select>
        <input type="submit" />
    </form:form>
</div>
</security:authorize>
<security:authorize access="!hasRole('PERM_MANAGEORGASSESSPROG_VIEW')">
    <span class="error"><fmt:message key="error.permissionDenied"/></span>
</security:authorize>