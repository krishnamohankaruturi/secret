<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 

<div>
	<ul id="userManagement" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">
			<li class="nav-item">
				<a class="nav-link" href="#activation-email" data-toggle="tab" role="tab"><fmt:message key="label.toolsubmenu.ActivationEmail"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('EDIT_INACTIVE_ACCOUNTS')"> 
			<li class="nav-item">
				<a class="nav-link" href="#inactive-account" data-toggle="tab" role="tab"><fmt:message key="label.toolsubmenu.InactiveAccounts"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('EDIT_INTERNAL_USERS')">
			<li class="nav-item">
				<a class="nav-link" href="#internal-user" data-toggle="tab" role="tab"><fmt:message key="label.toolsubmenu.InternalUsers"/></a>
			</li>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">
			<div id="activation-email" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/ActivationEmailTemplateView.jsp"/>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('EDIT_INACTIVE_ACCOUNTS')"> 
			<div id="inactive-account" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/viewInvalidUsers.jsp"/>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('EDIT_INTERNAL_USERS')">
			<div id="internal-user" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/viewInternalUsers.jsp"/>
			</div>
		</security:authorize>
	</div>
</div>

<script type="text/javascript" src="<c:url value='/js/toolstab.js?v=1'/>"></script>
<link rel="stylesheet" href="<c:url value='/css/external/font-awesome.min.css'/>" type="text/css" />

<script type="text/javascript">
    jQuery.browser = {};
    (function () {
        jQuery.browser.msie = false;
        jQuery.browser.version = 0;
        if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
            jQuery.browser.msie = true;
            jQuery.browser.version = RegExp.$1;
        }
    })();
</script>

<script>
$(function(){
	<%-- navigate to the first available tab --%>
	$('#userManagement li.nav-item:first a').tab('show');
});
</script>

 <security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">	 
    <script type="text/javascript" src="<c:url value='/js/tools/activationEmailTemplateView.js'/>"> </script>
 </security:authorize>