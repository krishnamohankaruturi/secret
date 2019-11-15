<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!doctype html>

<!--[if lt IE 8]>      <html class="no-js ie"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <noscript><meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/nojs.htm" /></noscript>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title><fmt:message key="label.common.title"/></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width,initial-width=1">
        <meta http-equiv="cache-control" content="max-age=0" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="pragma" content="no-cache" />
		<security:csrfMetaTags />
        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
         
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/screen.css" type="text/css" />
       	<script type="text/javascript" src="${pageContext.request.contextPath}/js/external/jquery-1.7.2.min.js"> </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/external/modernizr-2.6.2.min.js"></script> 
        <script src="${pageContext.request.contextPath}/js/common.js"></script>
             
         
		<!--[if (gte IE 6)&(lte IE 8)]>
            <script type="text/javascript" src="<c:url value='js/selectivizr.js'/>"></script>
            <noscript><link rel="stylesheet" href="[fallback css]" /></noscript>
            <script type="text/javascript" >
            if(typeof String.prototype.trim !== 'function') {
			  String.prototype.trim = function() {
			    return this.replace(/^\s+|\s+$/g, ''); 
			  }
			}</script>
        <![endif]-->    
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/templates/mainNoNavigation.js"></script> 
         
        
    </head>
    <body class="body_bc initial">
    	
        <div class="_bcg">

            <div class="hdr">
            	<div style="text-align:center;">
            		<a href="/AART/logIn.htm" style="margin-left:5%;">
                		<img src="${pageContext.request.contextPath}/images/${imageFolderName}/kite_logo_2018.png" alt="Kite" class="logo_img_login"/>
                	</a>
                </div>
            </div><!-- /hdr -->
		<noscript>
		    <style type="text/css">
		        .js-content {display:none;}
		    </style>
		    <div class="wrap_bcg signin noscriptmsg">
		    	<div class="panel_center">
		   	To access Kite Educator Portal it is necessary to enable JavaScript. Here are the <a href="http://www.enable-javascript.com" target="_blank"> instructions how to enable JavaScript in your web browser.</a>
		   	</div></div>
		</noscript>
            <section class="wrap_bcg signin js-content">
				<tiles:insertAttribute name="content"/>
		        
		        <tiles:insertAttribute name="footer"/>
                
            </section><!-- /signin -->

        </div><!-- /signin_bcg -->
        
		<%@ include file="/jsp/templates/google-analytics.jsp" %>
    </body>
</html>