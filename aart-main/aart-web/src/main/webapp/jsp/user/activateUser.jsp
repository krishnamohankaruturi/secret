<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<spring:url value="/activateUser.htm" var="activateUserUrl"/>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/external/jquery-ui-1.8.19.custom.min.css" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/css/theme/recordBrowser.css" rel="stylesheet">        
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/screen.css" type="text/css" />

<div class="panel_center" id="activateInvalidBrowser" style='display:none;font-size:14px;font-family:sans-serif;' >

	<fmt:message key="error.invalidBrowser.line1"/>
	<fmt:message key="error.invalidBrowser.line2"/>
	<fmt:message key="error.invalidBrowser.line3"/>
	
</div>

<div class="panel_center" id="activateForm">                  
	<c:choose>
		<c:when test='${(activeUser == false) && (inactiveUser == false)}'>
		    <c:choose>
		       <c:when test='${activationExpired == false }'>
					<form:form id="activateUserForm" cssClass="form ca-form"
						modelAttribute="userGroup" action="${activateUserUrl}" method="POST">
	                   	<div style="border-bottom: 1px solid #f3f3f3; "><h1 class="panel_head">Activate User</h1></div>
	                    <c:if test="${not empty invalidPasswords}">
						<div class="signinerror">
						       <span class="error_message"><fmt:message key="error.user.activation.password.matching"/></span>
						</div>
						</c:if>
						<div class="form-fields">
							<div></div>
					        <label class="field-label" for="First_Name">First Name:</label>
					        <form:input path="user.firstName" id="First_Name"/>
					    </div><!-- /input -->
						<div class="form-fields">
							<div></div>
					        <label class="field-label" for="Last_Name">Last Name:</label>
					        <form:input path="user.surName" id="Last_Name"/>
					    </div><!-- /input -->
					    <div class="form-fields">
						    <div></div>
						    <label class="field-label" for="password_field"><fmt:message key="label.logIn.password" /></label>
						     <input id="password_field" type="password" name="password_field" />
						</div><!-- /input -->
					    <div class="form-fields">
						    <div></div>
						    <label class="field-label" for="confirm_password" style="margin-top:30px">Confirm Password:</label>
						    <input id="confirm_password" type="password" name="confirmPassword" />
						</div><!-- /input -->
						
						<form:hidden path="id"/>
						<form:hidden path="userId"/>
						<form:hidden path="groupId"/>
						<form:hidden path="activationNo"/>
						<form:hidden path="user.id"/>
						<div class="btn-bar">
							<input type="submit" class="btn_blue" value="Activate" />
						</div>
					</form:form>
				</c:when>
				<c:otherwise>
				    <!-- activation has expired  -->
				    <span class="error"><fmt:message key="error.activation.expired"/></span>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test='${inactiveUser == true}'>
			<div class="error"><fmt:message key="error.activation.userinactive"/></div>
		</c:when>
		<c:otherwise>
		    <!-- User is already activated, don't allow someone else to change the users password -->
		    <div class="error"><fmt:message key="error.activation.useractive"/></div>
		    <br/>
		    <div><a class="forgot_password" href="${pageContext.request.contextPath}/logIn.htm"><fmt:message key='back.to.login'/></a>
		    </div>
		</c:otherwise>
	</c:choose>
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/jquery-1.7.2.min.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/localstorage.min.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/passfield.min.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/user/activateUser.js"></script>