<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/external/jquery-ui-1.8.19.custom.min.css" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/css/theme/recordBrowser.css" rel="stylesheet">        
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/screen.css" type="text/css" />
<div class="panel_center" id="resetInvalidBrowser" style='display:none;font-size:14px;font-family:sans-serif;' >

	<fmt:message key="error.invalidBrowser.line1"/>
	<fmt:message key="error.invalidBrowser.line2"/>
	<fmt:message key="error.invalidBrowser.line3"/>
	
</div>
<div class="panel_center" id="resetPasswordForm">
<c:choose>
	<c:when test='${linkUsed}'>
		<div style="border-bottom: 1px solid #f3f3f3; "><h1 class="panel_center_hdr"><fmt:message key='reset.used'/></h1></div>
		<div>
		 	<!-- Reset Link Used -->
		    <div class="resetexpiredinstructions"><fmt:message key="reset.used.msg.text"/></div>
		    <br/><div>
		    	<a href="${pageContext.request.contextPath}/logIn.htm" class="returnloginlink"><fmt:message key="label.login.link"/></a>
		    </div>
	    </div>
	</c:when>
	<c:when test='${linkExpired}'>
		<div style="border-bottom: 1px solid #f3f3f3; "><h1 class="panel_center_hdr"><fmt:message key='reset.expired'/></h1></div>
		<div>
		 	<!-- Reset Link expired -->
		    <div class="resetexpiredinstructions"><fmt:message key="reset.expired.msg.text"/></div>
		    <br/><div>
		    	<a href="${pageContext.request.contextPath}/logIn.htm" class="returnloginlink"><fmt:message key="label.login.link"/></a>
		    </div>
	    </div>
	</c:when>
	<c:when test='${parameterError}'>
		<div style="border-bottom: 1px solid #f3f3f3; ">
			<h1 class="panel_center_hdr">
				<c:choose>
					<c:when test='${passwordExpired}'>
						<fmt:message key='label.expired.password'/>
					</c:when>
					<c:otherwise>
						<fmt:message key='reset.password'/>
					</c:otherwise>
				</c:choose>
			</h1>
		</div>
	 	<div>
		 	<!-- Generic Error -->
		    <div class="ui-state-error"><fmt:message key="error.generic"/></div>
		    <br/><div>
		    	<a href="${pageContext.request.contextPath}/logIn.htm" class="returnloginlink"><fmt:message key="label.login.link"/></a>
		    </div>
		</div>
	</c:when> 
	<c:when test='${updateSuccess}'>
		<div style="border-bottom: 1px solid #f3f3f3; ">
			<h1 class="panel_center_hdr">
				<c:choose>
					<c:when test='${passwordExpired}'>
						<fmt:message key='label.expired.password'/>
					</c:when>
					<c:otherwise>
						<fmt:message key='reset.password'/>
					</c:otherwise>
				</c:choose>
			</h1>
		</div>
	 	<div>	 	
		    <div class="ui-state-highlight"><fmt:message key='label.reset.password.success'/></div>
		    <br/><div>
		    	<a href="${pageContext.request.contextPath}/logIn.htm" class="returnloginlink"><fmt:message key="label.login.link"/></a>
		    </div>
		</div>
	</c:when>
	<c:otherwise>
		<c:choose>
			<c:when test='${passwordExpired}'>
			<div style="border-bottom: 1px solid #f3f3f3; "><h1 class="panel_center_hdr"><fmt:message key='label.expired.password'/></h1></div>
	        </c:when>	        
	        <c:otherwise>
	        <div style="border-bottom: 1px solid #f3f3f3; "><h1 class="panel_center_hdr"><fmt:message key='reset.password'/></h1></div>       
	        </c:otherwise>
        </c:choose>
        <c:if test='${duplicatePasswordError}'>
        	<div class="ui-state-error"><fmt:message key='duplicate.password'/></div>
        </c:if>
<!--  Added for US-14985 -->
        <c:if test='${updateError}'>
        	<%-- <div class="ui-state-error"><fmt:message key='usedPasswordError.password'/></div> --%>
         <div class="ui-state-error">${updateErrorMsg}</div>
        </c:if>
        <br/><div class="instructions"><fmt:message key='reset.instruction.text'/></div>
		<div>
			<span id="passwordRequired" class="ui-state-error error_messages hidden"><fmt:message key='password.required'/></span>
			<span id="newPassword" class="ui-state-error error_messages hidden"><fmt:message key='password.must.be.new'/></span>
		</div>
<!--  Added for US-14985 --> 
		<div id="cpError" class="passwordErrors"></div>
       		<form action="changePassword.htm" method="POST" id="resetPasswordFormId">
       		<security:csrfInput />
        <div class="container form ca-form"> 
        	<div class="form-fields">
    			<div></div>
        		<label class="field-label" for="username"><fmt:message key='common.username'/>:</label>
                <input id="username" name="username" value="${username}" type="text" readonly="readonly"/>
    		</div><!-- /input -->
    
    		<div class="form-fields">
    			<div></div>
        		<label class="field-label" for="password"><fmt:message key='common.password'/>:</label>
        		<input type="password" name="password" id="password" placeholder="Enter Your New Password"/>
    		</div><!-- /input -->
    
    		<div class="form-fields">
    			<div></div>
        		<label class="field-label" for="confirmPassword"><fmt:message key='password.confirm'/>:</label>
	            <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm Password"/>
    		</div><!-- /input -->
			<div class="btn-bar">
	        	<input class ="btn_blue" type="submit" id="resetPassword" value="<fmt:message key='label.common.submit'/>"/>
	        </div>
	        
	        <input type="hidden" id="allowReset" name="allowReset" value='${allowReset}'/>  
	        <input type="hidden" id="authToken" name="authToken" value='${authToken}'/>
	        <input type="hidden" id="passwordExpired" name="passwordExpired" value='${passwordExpired}'/>
	        </div>
        </form>
	</c:otherwise>
</c:choose>
</div>
<!--  Added for US-14985 -->
<script src="${pageContext.request.contextPath}/js/localstorage.min.js"> </script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/passfield.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/resetPassword.js"></script>