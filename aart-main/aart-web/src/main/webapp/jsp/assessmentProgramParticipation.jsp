<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<security:authorize access="hasRole('PERM_ASSESSPROGPARTIC_VIEW')">
	<div id="container_login">
       <div id="header">
       		<img src="../images/logo.png" alt="<fmt:message key="label.common.KITE" />" />
       </div>
       <div class="viewportPortable" >
       		<fmt:message key="label.page.assessmentprogramparticipation" />
	       <div class="homeContent">
	        <form:form method="post" action="assessmentProgramParticipation.htm">
	            <fieldset>            	
	                <p>
	                    <input type="submit" value="<fmt:message key='Download.button'/>" />
	                </p>	 
	            </fieldset>
	        </form:form>	        
	        </div>
       </div>
	</div>
	<script src="js/external/jquery-1.7.2.min.js"></script>
	<script src="js/logger.localstorage.min.js"> </script>
	<script src="js/localstorage.min.js"> </script>
</security:authorize>
<security:authorize access="!hasRole('PERM_ASSESSPROGPARTIC_VIEW')">
    <span class="error"><fmt:message key="error.permissionDenied"/></span>
</security:authorize>