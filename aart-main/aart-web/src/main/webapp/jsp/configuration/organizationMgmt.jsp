<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="form action-bar">
	<div class="form-fields">
		<label for="actions" class="isrequired form-label">Select Action<font size="5" color="red">*</font>:</label>			
		<select id="actions" class="bcg_select" name="actions">
			<option value="">Select</option>
			<security:authorize access="hasRole('PERM_ORG_VIEW')">
		 		<option value="viewOrg">View Organization</option>
		 	</security:authorize>
		 	<security:authorize access="hasRole('PERM_ORG_UPLOAD')">
				<option value="uploadOrg">Upload Organization</option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_ORG_CREATE')">
				<option value="createOrg">Create Organization</option>
			</security:authorize>
		</select>
	</div>
</div>

<div class="tabTable">
<span id="message"></span>

	<div class="row">

		<div id ="viewOrgDiv" class="hidden">
			<jsp:include page="viewOrganizations.jsp"></jsp:include>
		</div>
		
		<div id ="uploadOrgDiv" class="hidden">
			<jsp:include page="organizationUpload.jsp"></jsp:include>
		</div>
		
		<div id ="createOrgDiv" class="hidden">
			<jsp:include page="createOrganization.jsp"></jsp:include>
		</div>
	
	
	</div>
</div>
   
<script type="text/javascript"
	src="<c:url value='/js/configuration/viewOrganizations.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/uploadOrganizations.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/createOrganization.js'/>"> </script>
    <script type="text/javascript"
    	src="<c:url value='/js/configuration/organizationMgmt.js'/>"> </script>
  
