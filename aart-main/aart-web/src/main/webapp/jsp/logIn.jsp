
<%@ include file="/jsp/include.jsp" %>

<div class="panel_center" id="invalidBrowser" style='display:none;font-size:14px;font-family:sans-serif;' >

	<fmt:message key="error.invalidBrowser.line1"/>
	<fmt:message key="error.invalidBrowser.line2"/>
	<fmt:message key="error.invalidBrowser.line3"/>
	
</div>

<div class="panel_center" id="securityNote">
  <p style='font-size:15px;font-weight:bold'><span style='color:#e30707;'>Notice: </span>
  <fmt:message key="notice.security.msg"/></p>
</div>

<div class="panel_center" id="loginForm">

    <h1 class="panel_center_hdr"><fmt:message key="label.logIn.signin" />&nbsp;<fmt:message key="label.to.ep"/></h1>

    <form method="post" action="<c:url value="/j_spring_security_check" />">
    <security:csrfInput />
    <div class="field">
    <div></div>
        <label class="field_label" for="j_username">
        	<fmt:message key="label.logIn.username" />
   </label>
        <input class="field_text" id="j_username" type="text" name="j_username" autocapitalize="none" autocorrect="off" autocomplete="off" title="username"/>
    </div><!-- /input -->
    
    <div class="field">
    <div></div>
        <label class="field_label" for="j_password">
        	<fmt:message key="label.logIn.password" />
  </label>
        <input class="field_text" id="j_password" type="password" name="j_password" autocapitalize="none" autocorrect="off" autocomplete="off" title="password"/>
    </div><!-- /input -->
   
	<%-- <c:if test="${not empty param.error}">
    <div class="signinerror">
        <fmt:message key="error.login" />
    </div><!-- /error -->
	</c:if>
	<c:if test="${empty param.error}">
    <!-- div class="signinerror">
        &nbsp;
    </div --><!-- /No error -->
	</c:if>           --%>          
	
    <!--Added due to US16245 : for showing different message -->
    <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
      <font color="red">
      <div class="signinerror">
        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
    </div>        
      </font>
    </c:if>
    <c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
    
    <div class="actions">
        <button class="actions_submit">
        	<fmt:message key="label.logIn.signin" /> &raquo;
        </button>
        <a class="forgot_password" href="forgotPassword/forgotPassword.htm"><fmt:message key="label.logIn.forgotpassword" /></a>
    </div>
    <%-- <div class="actions">
        <p>If you wish to create an account, click                       
        <a class="create_account" href="createAccount/createAccount.htm"><fmt:message key="label.logIn.clickHere" /></a>.</p>
    </div> --%>
    </form>

</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/logIn.js"></script>
<!-- /signin_form -->