<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/forgotPassword.js"></script>

<div class="panel_center">
    <h1 class="panel_center_hdr">Forgot Password?</h1>
    <p class="forgot">Enter your username and we will send you an email with the instructions for changing your password.</p>

    <div class="signinerror" style="display:none;">
        <span id="emailsuccess" class="ui-state-highlight info_message hidden"><fmt:message key='info.reset.password'/></span>
		<span id="emailfailed" class="error_message hidden"><fmt:message key='error.reset.password'/></span>
		<span id="emailrequired" class="error_message hidden"><fmt:message key='error.username.required'/></span>
		<span id="userNotFound" class="error_message hidden"><fmt:message key='user.not.found'/></span>
		<span id="userInactive" class="error_message hidden"><fmt:message key='user.not.active'/></span>
    </div><!-- /error -->
    
    <div class="field">
        <label class="field_label" for="username"><fmt:message key='label.logIn.username'/></label>
        <input class="field_text" id="username" type="text" name="username"/>
    </div><!-- /input -->
    
    <div class="actions">
        <button class="actions_submit"  id="submitResetRequest"><fmt:message key='label.common.submit'/> &raquo;</button>
        <a class="forgot_password" href="${pageContext.request.contextPath}/logIn.htm"><fmt:message key='back.to.login'/></a>
    </div>

</div><!-- /signin_form -->