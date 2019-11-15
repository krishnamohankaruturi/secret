<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<security:authorize var="loggedIn" access="isAuthenticated()" />

<c:choose>
    <c:when test="${loggedIn}">
        <span class="error"><fmt:message key="error.permissionDenied"/></span>
    </c:when>
    <c:otherwise>
        <div class="panel_center">
			<span class="error"><fmt:message key="error.permissionDenied"/></span>
		</div>
    </c:otherwise>
</c:choose>