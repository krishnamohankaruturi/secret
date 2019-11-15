<!DOCTYPE html>
<%@ include file="/jsp/include.jsp"%>
<%@ page session="false" %>
<%@ page isErrorPage="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<fmt:setBundle basename="messages" /> 
<html lang='en'>
<head>
<meta charset="UTF-8" />
<title><fmt:message key="label.errorpage.title"/></title>
<meta name="robots" content="noindex, nofollow"/>
    <meta name="viewport" content="width=768px, initial-scale=1.0, minimum-scale=1.0" />
    <link rel="stylesheet" href="/css/new-theme/app-altUI.css" type="text/css" /> 
</head>

<body>
	<!-- overlay -->
<div class="load-overlay"></div>

<div> 
    
    <div class="alt-header">
        <h1><fmt:message key="label.errorpage.header"/></h1>
    
        <a class="alt-ui-logo"><img src="/images/kite-alt-logo.png" alt="KITE" /></a><!-- /logo -->

        <br class="clear" />

    </div><!-- /intro header -->

    <section>
    
        <div id="test-description">
            <h1><font color="red"><fmt:message key="${errMsg}"/></font></h1><br/>
            <h2>${errorMessage}</h2><br/>
          <!--   <center><img src="/images/robot.jpg"></center> -->

        </div><!-- /test-desc -->   

    </section><!-- /tabset3 -->

    <div class="alt-footer">
        
        <a id="" class="btn blue-btn" href="/"><fmt:message key="label.errorpage.homeBtn"/></a>

    </div><!-- /intro header -->

</div><!-- /wrap -->

</body>
</html>
