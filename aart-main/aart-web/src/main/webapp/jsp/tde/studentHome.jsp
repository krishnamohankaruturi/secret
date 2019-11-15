<!doctype html>
<%@ page session="true"%>
<%@ include file="/jsp/tde/include.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jawr.net/tags" prefix="jwr"%>
<%@ page import="java.util.Date" %>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
<title><fmt:message key="label.common.title" /></title>
<meta name="robots" content="noindex, nofollow" />

<meta name="viewport"
	content="width=768px, initial-scale=1.0, minimum-scale=1.0, height=device-height" />

<!--<jwr:style src="/css/global.css" />-->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/tde/new-theme/screen.css">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/tde/new-theme/app.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/tde/new-theme/app-native.css">
<link rel="stylesheet" type="text/css" 
	href="${pageContext.request.contextPath}/css/tde/new-theme/tool.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/tde/external/jquery-ui-1.8.16.custom.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/tde/external/jplayer.blue.monday.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/tde/external/jquery.jscrollpane.css" rel="stylesheet"
	media="all" />
</head>

<body>
	<div id="wrapper">
	<div id="scroller">
	<div class="w">
		<%--
		<header class="red-header">
			<h1 id="logo">
				<a href="${pageContext.request.contextPath}/studentHome.htm"><img
					src="/images/kite-logo.png" alt="kite" /></a>
			</h1>

			<ul class="global-nav">
				<!-- <li><a href="#"><img src="images/help-icon.png" alt="" /> Find Help</a></li> -->
				<!-- <li><a href="#"><img src="images/signout-icon.png" alt="" /> Sign Out</a></li> -->
				
				<c:if test="${fn:contains(header['User-Agent'],'10.0.5')}">
					<li><a id="closeBrowser" href="#"><img
							src="/images/close-icon.png" alt="Close" /> <fmt:message
								key="label.common.closebrowser" /></a></li>
				</c:if>

			</ul>

		</header>
		 
		<c:set var="contains" value="false" />
		<c:forEach var="policy" items="${tde.config.policies}">
			<c:if test="${policy.name eq 'welcomemessage'}">
				<h1 class="page-header">
					<c:if test="${cleanStudentTestId != null}">
						<div>
							<fmt:message key="label.studentHome.testsaved" />
						</div>
					</c:if>
					${policy.data}
				</h1>
				<c:set var="contains" value="true" />
			</c:if>
		</c:forEach>
		<c:if test="${contains==false}">
			<h1 class="page-header">
				<c:if test="${cleanStudentTestId != null}">
					<div>
						<fmt:message key="label.studentHome.testsaved" />
					</div>
				</c:if>
				<fmt:message key="label.studentHome.heading">
					<fmt:param value="${student.firstName}" />
				</fmt:message>
			</h1>
		</c:if>


		<div class="welcome-c clearfix">

			<ul>
				<security:authorize access="hasRole('ROLE_ADMIN')">
					<a href="/management.htm">
						<li><img class="pencil-icon hovUp"
							src="/images/pencil-icon.png"
							alt="<fmt:message key="label.studentHome.administration" />" /><br />
							<span class="practice-btn btn"><fmt:message
									key="label.studentHome.administration" /><img
								src="/images/arrow-icon.png" alt="Go" /></span></li>
					</a>
				</security:authorize>
				<a class="studentsTests" href="#studentsTests">
					<li><img class="pencil-icon hovUp"
						src="/images/pencil-icon.png"
						alt="<fmt:message key="label.common.takeaTest" />" /><br /> <span
						class="taketest-btn btn"><fmt:message
								key="label.common.takeaTest" /><img
							src="/images/arrow-icon.png" alt="Go" /></span></li>
				</a>

				<a class="practiceTests" href="#practiceTests">
					<li class="last"><img class="watch-icon hovUp"
						src="/images/whistle-icon.png"
						alt="<fmt:message key="label.common.practice" />" /><br /> <span
						class="practice-btn btn"><fmt:message
								key="label.common.practice" /><img src="/images/arrow-icon.png"
							alt="Go" /></span></li>
				</a>
			</ul>

		</div>
		<!-- /select contain -->
		--%>
		<footer>
			<div class="copyright">
				<p>
					<c:set var="year" value="<%=new java.util.Date().getYear()%>" />
					<c:set var="fmtYear">${year + 1900}</c:set>
					<fmt:message key="label.common.copyright">
						<fmt:param value="${fmtYear}" />
					</fmt:message>
				</p>
				<c:if test="${lcs == true}">
					<p>Connected through LCS</p>
				</c:if>
			</div>
		</footer>
		
	</div>
	<!-- /wrap -->
	</div>
	</div>
	
	<jwr:script src="/bundles/global.js" />
	<script src="${pageContext.request.contextPath}/js/external/underscore.js"></script>
	  
	<script src="${pageContext.request.contextPath}/js/external/jquery.idletimer.js"></script>
	<script src="${pageContext.request.contextPath}/js/tde/jquery.idletimeout.js"></script>
	
	<jwr:script src="/bundles/testbundle.js" />
	<jwr:script src="/bundles/testbundleext.js" />
	
	<div id="network-connectivity"  class="popupOverlay">
		<div class="overlay-content overlayShowing">
			<div>
				<h3 style="text-align:center;">No Internet Connectivity.</h3>
	   			
	   			<div>
	   				<p>No internet connectivity. Please check your network connection and try again.</p>
				</div>
				<div class="activation">
					<button type="button" id="network-popup-close" class="btn-rev">Close</button>
				</div>
			</div>
    	</div>
	</div>
	
	<c:if test="${fn:containsIgnoreCase(header['User-Agent'],'ipad')}">
		<jwr:script src="/bundles/mobilebundleext.js" />
		<div id="rotateIpadPopup"  class="popupOverlay">
			<div class="overlay-content overlayShowing">
				<div>
					<h3 style="text-align:center;">Please rotate your tablet.</h3>
		   			
		   			<div>
		   				<p>This test is best viewed in landscape mode.  Please rotate your tablet to continue.</p>
					</div>
				</div>
	    	</div>
		</div>
	</c:if>
	
	<script>
		
	 	var profile = new Array();
	 
	 	var sessionTimeoutPeriod=${pageContext.session.maxInactiveInterval * 1000};
	 	//for session timeout
	 	var isAjaxCallMade = false;
	 	var profileAttributes = null;
	 	var contextPath ='${pageContext.request.contextPath}';
	 	$('.w').css('opacity', 0);
	 	/* var refreshPageDisable = true;//false;
	 	function disableF5(e) {
			if (refreshPageDisable
					&& ((e.which || e.keyCode) == 116 || (e.ctrlKey && (e.which || e.keyCode) == 82)))
				e.preventDefault();
		}; */
		$(document)
				.ready(
						function() {
							$(document).on("ajaxStart.tdeBlock", function () {
								if(tde.config.uiblock) {
									$.blockUI({ 
										message: '<h1> Loading...</h1>',
										overlayCSS: { backgroundColor: '#C8C8C8' }
									});
								}
							}).on("ajaxStop.tdeBlock", function () {
								if(tde.config.uiblock){
									$.unblockUI();
									tde.config.uiblock = false;
								}
								isAjaxCallMade = true;
							});
							
							$(document).ajaxError(function( event, jqxhr, settings, exception  ) {
								error.execute(event, jqxhr, settings, exception);
							});
							
							//US14986
							if (typeof _gaq !== "undefined" && _gaq !== null) {
								$(document).ajaxSend(function(event, xhr, settings){
								    _gaq.push(['_trackPageview', settings.url]);
								});
							} 
							//US14986-end
							
							var object={};
							object.contextPath=contextPath;
							object.sessionuimessages=tool_names.sessiontimer;
							object.timeout=sessionTimeoutPeriod;
							$('body').append(new EJS({
								url : contextPath + '/js/views/tool/sessiontimer.ejs'
							}).render(object.sessionuimessages));
							//console.debug(object.timeout);
							//sessiontimer.init(object);
							$.idleTimeout($('#timeroverlay'),$('#extendSession'),{
								warningLength:90,
								idleAfter:(sessionTimeoutPeriod-100000)/1000
							});
							$('#sessionLogout').on('click', logoutBasedOnTimer);
							
							$("a").click(function() {
								logger.info("<security:authentication property="principal"/> studentHome clicked: "+ $(this)[0].href);
							});

							var cleanStudentTestId = "${cleanStudentTestId}";

							if (cleanStudentTestId) {
								// Clear out the localstorage of the test.
								localStore.remove("tasks." + cleanStudentTestId);

								// Clear out the responses.
								localStore.remove("responses." + cleanStudentTestId);
								logger.info("<security:authentication property="principal"/> completed test: "+ cleanStudentTestId);
							} else {
								logger.info("<security:authentication property="principal"/> logged in successfully.");
							}

							sessionStorage.setItem("profile", "{}");
							/* if(profileAttributes == null){
								profileAttributes = {};//${profileAttributes};
								if (profileAttributes) {
									var groupedObj=_.groupBy(profileAttributes, function(obj){
										return obj.attrContainer;
									});
									sessionStorage.setItem("profile", JSON.stringify(groupedObj));
								};
							} */
							tracker.init(1);
							
							var tdeControl = new tdeController( 'body .w', {} );
							var testControl = new testController( 'body .w', {} );
							testControl.setUpTest({'testId': ${testId}, 'id': ${studentTestId}, 'testTypeName':'NoPractice','testFormatCode':"NADP"});
							//$(document).on("keydown", disableF5);
							
							$('#network-popup-close').on('click', function () {
								$('#network-connectivity').hide();
							});
							
							if("${lcs}" == "true") {
								//make a call to get lcs parameters
								$.ajax({
									url : contextPath + '/getLcsParam.htm',
									type : "GET",
									success : function(data) {
										tde.config.lcsId = data.lcsId;
										tde.config.lcsMediaUrl = data.lcsMediaUrl;
										
										// change mediaUrl to point to lcs now
										var mediaUrlArray = tde.config.mediaUrl.split('/');
										var mediaContext = mediaUrlArray[mediaUrlArray.length-2];
										
										tde.config.mediaUrl = tde.config.lcsMediaUrl + mediaContext + '/';
									}
								});
							}
							
							if(sessionStorage.getItem('bandwidth') && sessionStorage.getItem('bandwidth') < 384) {
								$('.copyright').append('<p>Your internet bandwidth is lower than 384Kbps. You may experience technical difficulties.</p>');
							}
						});
		
		//DE7465 - Prevents backspace except in the case of textareas and text inputs to prevent user navigation.
	    $(document).on("keypress",function (e) {
	        var preventKeyPress;
	        if (e.keyCode == 8) {
	            var d = e.srcElement || e.target;
	            switch (d.tagName.toUpperCase()) {
	                case 'TEXTAREA':
	                    preventKeyPress = d.readOnly || d.disabled;
	                    break;
	                case 'INPUT':
	                    preventKeyPress = d.readOnly || d.disabled ||
	                        (d.attributes["type"] && $.inArray(d.attributes["type"].value.toLowerCase(), ["radio", "checkbox", "submit", "button"]) >= 0);
	                    break;
	                case 'DIV':
	                    preventKeyPress = d.readOnly || d.disabled || !(d.attributes["contentEditable"] && d.attributes["contentEditable"].value == "true");
	                    break;
	                default:
	                    preventKeyPress = true;
	                    break;
	            }
	        }
	        else
	            preventKeyPress = false;

	        if (preventKeyPress)
	            e.preventDefault();
	    });
	  //DE7465 - END
		
		
		$(window).on("load", function() {
			$('.w').css('opacity', 1);
			setTimeout(function () {
				if ( Modernizr.touch ) {
					tde.config.myscroll = new iScroll('wrapper');
				}
			}, 100);
		});
		
		if(/ipad/ig.test(navigator.userAgent)){
			
			$(function() {
			    $.stayInWebApp();
			});
			
			$(window).bind('orientationchange', function(event) {
				if ( window.orientation == 0  || window.orientation == 180) {
					$('#rotateIpadPopup').hide();
					test.showAlertBox('rotateIpadPopup');
				} else {
					$('#rotateIpadPopup').hide();
				}
				
				$(window).scrollLeft(0);
				 
				/* $('#ticketConfirmation, #savedraft, #postItWarn, #confirmNoteDelete, #confirmEnd, #errorid').each(function() {
				  if($(this).is(':visible')) {
					  $(this).hide();
					  test.showAlertBox($(this).attr('id'));  
				  }
			  	}); */
			});
			
			if ( window.orientation == 0  || window.orientation == 180 ) {
				test.showAlertBox('rotateIpadPopup');
			}
		};
	</script>

</body>
</html>