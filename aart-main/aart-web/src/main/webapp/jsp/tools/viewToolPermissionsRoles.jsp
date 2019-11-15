<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 

<div>
	<ul id="permissionRoles" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('PERMISSIONS_AND_ROLES_EXTRACT')">
			<li class="nav-item">
				<a class="nav-link" href="#permissionrole" data-toggle="tab" role="tab" onclick="permissionClick(true)"><fmt:message key="tools.submenu.PermissionsAndRolesExtractSubTab" /></a>
			</li>
		</security:authorize>
		<c:if test="${user.groupCode == 'GSAD'}">
			<li class="nav-item">
				<a class="nav-link" href="#permissionupload" data-toggle="tab" role="tab" onclick="permissionClick(false)"><fmt:message key="tools.submenu.UploadPermissionsSubTab" /></a>
			</li>
		</c:if>
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('PERMISSIONS_AND_ROLES_EXTRACT')">
			<div id="permissionrole" class="tab-pane" role="tabpanel">
				<jsp:include page="permissionsAndRolesExtract.jsp"/>
			</div>
		</security:authorize>
	</div>
		<div id="contentUpload" class="tab-content">
		<c:if test="${user.groupCode == 'GSAD'}">
			<div id="permissionupload" class="tab-pane" role="tabpanel">
				<jsp:include page="uploadPermissions.jsp"/>
			</div>
		</c:if>
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
	$('#permissionRoles li.nav-item:first a').tab('show');
	$("#permissionsOrgFilterForm").show();
});


function permissionClick(flag){
	if(flag){
		$("#uploadPermissionForm").hide();
		$("#permissionsOrgFilterForm").show();
		
	}else{
		$("#permissionsOrgFilterForm").hide();
		$("#uploadPermissionForm").show();
	}
}



</script>
