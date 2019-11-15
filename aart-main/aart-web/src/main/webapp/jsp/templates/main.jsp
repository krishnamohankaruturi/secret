<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<!doctype html>

<html lang='en'>
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta http-equiv="cache-control" content="max-age=0" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
		<meta http-equiv="pragma" content="no-cache" />
		<meta name="robots" content="noindex, nofollow" />
		<noscript><meta http-equiv="refresh" content="0; url=${pageContext.request.contextPath}/nojs.htm" /></noscript>
		<security:csrfMetaTags />
		<title><fmt:message key="label.common.title"/></title>

		<link rel="stylesheet" href="<c:url value='/css/external/jquery-ui-1.12.1.custom.min.css'/>" type="text/css" />
		<link rel="stylesheet" href="<c:url value='/css/jquery.timepicker.css'/>" type="text/css" />	    
		<link rel="stylesheet" href="<c:url value='/css/theme/recordBrowser.css'/>" type="text/css" />
		<link rel="stylesheet" href="<c:url value='/css/external/select2.css'/>" type="text/css" />
	 
		<link rel="stylesheet" href="<c:url value='/css/custom.css'/>" type="text/css" />		
		<link rel="stylesheet" href="<c:url value='/css/external/jstree.css'/>" type="text/css" />
		<link rel="stylesheet" href="<c:url value='/css/screen.css'/>" type="text/css" />
		<link rel="stylesheet" href="<c:url value='/css/bcg-jqGrid.css'/>" type="text/css" />	 
		<link rel="stylesheet" href="<c:url value='/css/external/nprogress.css'/>" type="text/css" />
		<link rel="stylesheet" href="<c:url value='/css/firstContactSettings.css?v=20160504'/>" type="text/css" />
		<link rel="stylesheet" href="<c:url value='/css/jqgrid-custom-columnchooser-fix.css'/>" type="text/css"/>
		<link rel="stylesheet" href="<c:url value='/css/configuration.css'/>" type="text/css" />

		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="<c:url value='/css/external/bootstrap-4.min.css'/>" type="text/css">
		
		<link rel="stylesheet" href="<c:url value='/css/ep-custom-nav.css'/>" type="text/css">
		<link rel="stylesheet" href="<c:url value='/css/external/open-iconic-bootstrap.min.css'/>" type="text/css">
		
		<link rel="stylesheet" href="<c:url value='/css/epfacelift.css'/>" type="text/css" />
		
		<script type="text/javascript" src="<c:url value='/js/external/jquery-3.3.1.js'/>"> </script>
		<!-- Popper JS -->
		<script type="text/javascript" src="<c:url value='/js/external/popper.min.js'/>"> </script>
		
		<!-- Latest compiled JavaScript -->
		<script type="text/javascript" src="<c:url value='/js/external/bootstrap-4.min.js'/>"> </script>

		<script type="text/javascript" src="<c:url value='/js/external/i18n/grid.locale-en.js'/>"> </script>				
		<script type="text/javascript" src="<c:url value='/js/external/jquery.jqgrid.min-4.15.5.js'/>"></script>
	 	<script type="text/javascript" src="<c:url value='/js/external/jquery-ui-1.12.1.custom.min.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/d3.v4.min.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/select2.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/jquery.timepicker.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/jquery.jstree.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/ui.scrollabletab.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/aart.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/ATSUtil.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/ui.multiselect.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/recordbrowser.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/jquery.validate.js'/>"> </script>  
		<script type="text/javascript" src="<c:url value='/js/external/additional-methods.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/saveRecordBrowserState.js'/>"> </script> 
		<script type="text/javascript" src="<c:url value='/js/custom.orgFilter.js'/>"> </script>   
		<script type="text/javascript" src="<c:url value='/js/external/jquery.idletimeout.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/jquery.idletimer.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/nprogress.js'/>"> </script>
		
		<script type="text/javascript" src="<c:url value='/js/external/jquery.bcgmultiselect.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/modernizr-2.6.2.min.js'/>"></script> 
		
		<script type="text/javascript" src="<c:url value='/js/external/jquery.iframe-transport.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/external/jquery.fileupload.js'/>"></script>
		<!-- for safari4 -->
		<script type="text/javascript" src="<c:url value='/js/external/json3.min.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/ckeditor/ckeditor.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/ckeditor/adapters/jquery.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/external/underscore.js'/>"> </script>
		<script type="text/javascript" src="<c:url value='/js/external/jquery.fileDownload.js'/>"></script>
		<script type="text/javascript" src="<c:url value='js/backwardcompatibilityfixes.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/firstContactResponseView.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/configuration/pnpOptionSettings.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/userHome.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/configuration/rosters.js'/>"></script>
		
		<!--[if (gte IE 6)&(lte IE 8)]>
			<script type="text/javascript" src="<c:url value='js/selectivizr.js'/>"></script>
		<![endif]-->	 
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/templates/main.js"></script>
		
		<script type="text/javascript">
			<%--
			changes to default jqGrid options:
				ignoreCase - "true" sets sorting and searches on local data (or data retrieved once) to be case-insensitive
			--%>
			jQuery.extend(jQuery.jgrid.defaults, {
				ignoreCase: true
			});
			$(function() {
			$(document).find("table").each(function() {
					$(this).attr('role', 'presentation')
				});
			});
		</script>
	</head>
	
	<body class="body_bcg">
		<noscript>
			<style type="text/css">
				.js-content {display:none;}
			</style>
			<div class="_bcg noscriptmsg">
			To access Kite Educator Portal it is necessary to enable JavaScript. Here are the <a href="http://www.enable-javascript.com" target="_blank"> instructions how to enable JavaScript in your web browser.</a>
			</div>
		</noscript>
		<input id="externalLink" value='<%=session.getAttribute("externalLink")%>' type="hidden">
		<input type="hidden" id="currentAssessmentProgram" value="${user.currentAssessmentProgramName}"/> 
		<div id="ep-main-site" class="_bcg js-content">
				<tiles:insertAttribute name="header"/>
				<tiles:insertAttribute name="navigation"/>
				<div class="content wrap_bcg">
					 <div style="position:fixed; top:0%; left:40%"><span class='error hidden' id="ajaxError"></span></div>
					 <tiles:insertAttribute name="content"/>
				</div>
				<tiles:insertAttribute name="footer"/>
		</div>
		
		<div id="confirmCloseUnsavedDialog" class="hidden" title="Warning - Unsaved Changes">
			<input id="defaultConfirmText" value="<fmt:message key='label.confirm.dialog.default'/>" type="hidden"/>
			<p id="confirmCloseText"></p>
		</div>
		
		<%@ include file="/jsp/templates/google-analytics.jsp" %>
	</body>
</html>