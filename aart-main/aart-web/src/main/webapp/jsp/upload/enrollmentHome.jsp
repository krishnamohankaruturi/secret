<!DOCTYPE html>
<%@ include file="/jsp/include.jsp" %>
<html lang='en'>
<head>
<meta charset="UTF-8" />

<title><fmt:message key="label.common.title"/></title>
<link rel="stylesheet" href="/css/theme.concatenated.min.css" type="text/css" />

</head>
  
  <body class="white_black scale_1x">
    
    <div id="container_login">
       <div id="header">
       		<img src="images/logo.png" alt="<fmt:message key="label.common.KITE" />" />
       </div>
       <div class="viewportPortable" >
	       <div class="homeContent">
	       		This is the enrollment home page. 
	           <a class="button2" href="/searchEnrollment.htm"><img src="images/buttonBG.png" alt="buttonBG" width="20" height="20" />
	           <fmt:message key="label.enrollment.search"/></a> 
	           <a class="button2" href="/upload.htm"><img src="images/buttonBG.png" alt="buttonBG" width="20" height="20" />
	           <fmt:message key="label.enrollment.upload"/></a>
	       </div>
       </div>
	</div>
  
  <script src="js/external/jquery-1.7.2.min.js"></script>
  <script src="js/logger.localstorage.min.js"> </script>
  <script src="js/localstorage.min.js"> </script>
</body>
</html>