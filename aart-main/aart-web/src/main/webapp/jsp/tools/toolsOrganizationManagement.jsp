<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" href="<c:url value='/css/theme/reports.css'/>" type="text/css" /> 

<div>
	<ul id="orgManagement" class="nav nav-tabs sub-nav">
		<security:authorize access="hasRole('TOOLS_MERGE_SCHOOLS')">
			<li class="nav-item">
				<a class="nav-link" href="#orgManagement-merge" data-toggle="tab" role="tab"><fmt:message key="tools.org.merge.label"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_MOVE_A_SCHOOL')"> 
			<li class="nav-item">
				<a class="nav-link" href="#orgManagement-move" data-toggle="tab" role="tab"><fmt:message key="tools.org.move.label"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_DEACTIVATE_ORGANIZATION')">
			<li class="nav-item">
				<a class="nav-link" href="#orgManagement-deactivate" data-toggle="tab" role="tab"><fmt:message key="tools.org.deactivate.label"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_REACTIVATE_ORGANIZATION')">
			<li class="nav-item">
				<a class="nav-link" href="#orgManagement-reactivate" data-toggle="tab" role="tab"><fmt:message key="tools.org.reactivate.label"/></a>
			</li>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('TOOLS_MERGE_SCHOOLS')">
			<div id="orgManagement-merge" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/mergeSchools.jsp"/>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_MOVE_A_SCHOOL')"> 
			<div id="orgManagement-move" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/moveSchools.jsp"/>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_DEACTIVATE_ORGANIZATION')">
			<div id="orgManagement-deactivate" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/deactivateOrganization.jsp"/>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('TOOLS_REACTIVATE_ORGANIZATION')">
			<div id="orgManagement-reactivate" class="tab-pane" role="tabpanel">
				<jsp:include page="../tools/reactivateOrganization.jsp"/>
			</div>
		</security:authorize>
	</div>
	<div id="org_mgmt_success" ></div>
	<div id="org_mgmt_error" ></div>
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
	$('#orgManagement li.nav-item:first a').tab('show');
});
</script>

 <%-- <security:authorize access="hasRole('EDIT_ACTIVATION_EMAIL')">	 
    <script type="text/javascript" src="<c:url value='/js/tools/activationEmailTemplateView.js'/>"> </script>
 </security:authorize> --%>