<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<spring:url value="/activateUser.htm" var="activateUserUrl"/>

<div class="panel_center">
    <div class="forgot">Account for ${user.firstName}&nbsp;${user.surName} has been successfully activated.</div>
    <br/>
    <div><a class="forgot_password" href="${pageContext.request.contextPath}/logIn.htm"><fmt:message key='back.to.login'/></a>
    </div>
</div><!-- /signin_form -->