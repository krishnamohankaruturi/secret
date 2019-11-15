<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/jsp/include.jsp" %>

<div id="header_id">
    <security:authorize access="isAuthenticated()">
	    <div id="log_status">
	        <a href="j_spring_security_logout">Logout</a>
	    </div>
    </security:authorize>
</div>