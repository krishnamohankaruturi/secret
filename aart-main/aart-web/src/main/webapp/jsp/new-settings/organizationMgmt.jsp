<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<link rel="stylesheet"
	href="<c:url value='/css/theme/configuration.css'/>" type="text/css" />
<div id ="organizationActions">
	<ul id="orgNav" class="nav nav-tabs sub-nav">			
		 	<security:authorize access="hasRole('PERM_ORG_VIEW')">
		 	<li class="nav-item get-orgs">	
		 		<a class="nav-link" href="#viewOrgDev" data-toggle="tab" role="tab">View Organization</a>
		 		</li>
		 	</security:authorize>
		 	<security:authorize access="hasRole('PERM_ORG_CREATE')">
		 	<li class="nav-item get-orgs">	
		 		<a class="nav-link" href="#createOrgDev" data-toggle="tab" role="tab">Create Organization</a>
		 		</li>
		 	</security:authorize>
		 	<security:authorize access="hasRole('PERM_ORG_UPLOAD')">
		 	<li class="nav-item get-orgs">	
		 		<a class="nav-link" href="#uploadOrgDev" data-toggle="tab" role="tab">Upload Organization</a>
		 		</li>
		 	</security:authorize>
		 	
		 	</ul>
		 	<div id="content" class="tab-content">
<!-- <div class="tabTable" style="margin-top: 13px;"> -->
<span id="message"></span>
	<security:authorize access="hasRole('PERM_ORG_UPLOAD')">
			<div id ="uploadOrgDev" class="tab-pane" role="tabpanel">			
			<jsp:include page="../configuration/organizationUpload.jsp"></jsp:include>
		<script type="text/javascript"
	src="<c:url value='/js/configuration/uploadOrganizations.js'/>"> </script>
		</div>		
		</security:authorize>
	<security:authorize access="hasRole('PERM_ORG_CREATE')">
			<div id ="createOrgDev" class="tab-pane" role="tabpanel">			
			<jsp:include page="../configuration/createOrganization.jsp"></jsp:include>
		<script type="text/javascript"
	src="<c:url value='/js/configuration/createOrganization.js'/>"> </script>
		</div>		
		</security:authorize>
	<security:authorize access="hasRole('PERM_ORG_VIEW')">	
			<div id ="viewOrgDev" class="tab-pane" role="tabpanel">			
			<jsp:include page="../configuration/viewOrganizations.jsp"></jsp:include>
		<script type="text/javascript"
	src="<c:url value='/js/configuration/viewOrganizations.js'/>"> </script>
		</div>		
		</security:authorize>
			
		
</div>
</div>
		 	<!-- </div> -->
		 	
    <script type="text/javascript"
    	src="<c:url value='/js/configuration/organizationMgmt.js'/>"> </script>