<!DOCTYPE html>
<%@ include file="/jsp/include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr" %>
<%@ page session="false"%>
<html lang='en'>
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
  <meta name="viewport" content="width=1024px, user-scalable = yes" />
  <meta name="apple-mobile-web-app-capable" content="yes">
  <title><fmt:message key="label.common.title" /></title>
  <meta name="robots" content="noindex, nofollow"/>

  <meta name="viewport" content="width=768px, initial-scale=1.0, minimum-scale=1.0" />
  <!--<jwr:style src="/css/global.css" />-->
  <link rel="stylesheet" type="text/css" href="/css/new-theme/screen.css">
  
  
</head>
<body id="signinview">
	
		<div class="sw clearfix">	
				<div class="signin-c">
					<h2><fmt:message key="label.logIn.heading" /></h2>
					
					<form method="post" autocomplete="off" action="<c:url value="/j_spring_security_check" />">
						<c:if test="${not empty param.error}">
                    		<div class="signin-error">
								<img src="/images/error-icon.png" alt="Error!" />
								<p><fmt:message key="BindAuthenticator.badCredentials" /></p>
							</div>
                		</c:if>
						<div class="signin">
							<label for="username"><fmt:message key="label.logIn.username" /></label>
							<input type="text" name="j_username" id="j_username" autocapitalize="off"/>
						</div><!-- /input -->
						
						<div class="signin">
							<label for="password"><fmt:message key="label.logIn.password" /></label>
							<input type="password" name="j_password" id="j_password" />
						</div><!-- /input -->

						<div class="signinactions">
							<button id="goTo-welcome"><fmt:message key="label.logIn.signin" /><img src="/images/arrow-icon.png" alt="" /></button>
						</div>
						
					</form>
					
				</div><!-- /signin c -->
				
				<footer>
					<div class="signin-copyright">
						<p>
							<c:set var="year" value="<%= new java.util.Date().getYear()%>" />
							<c:set var="fmtYear">${year + 1900}</c:set>
							<fmt:message key="label.common.copyright">
								<fmt:param value="${fmtYear}" />
							</fmt:message>
						</p>
					</div>
						
						<c:if test="${fn:contains(header['User-Agent'],'10.0.5')}">
							<ul class="signin-close">
								<li><a href="#" onClick="javascript: window.close();"><fmt:message key="label.common.closebrowser"/></a></li>
							</ul>
						</c:if>
					
				</footer>
		</div>

 <jwr:script src="/js/global.js" />
 

</body>
</html>
  