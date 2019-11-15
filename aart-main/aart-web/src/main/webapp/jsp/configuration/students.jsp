<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="form action-bar">
	<div class="form-fields">
		<label for="studentActions" class="isrequired form-label"><fmt:message key="label.config.select.action"/><font size="5" color="red">*</font>:</label>			
		<select id="studentActions" class="bcg_select" name="studentActions" title="Student Actions" >
			<option value=""><fmt:message key="label.common.select"/></option>
			<security:authorize access="hasRole('PERM_STUDENTRECORD_CREATE')">
					<option value="addStudents">Add Student</option>
			</security:authorize>
			<security:authorize access="hasAnyRole('PERM_EXIT_STUDENT', 'PERM_EXIT_ALT_STUDENT')">
					<option value="exitStudents">Exit Student</option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
					<option value="uploadEnrollment"><fmt:message key="label.config.students.uploadenrollment"/></option>
					<option value="uploadTEC"><fmt:message key="label.config.students.uploadtec"/></option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_FIRST_CONTACT_UPLOAD')">
			</security:authorize>
			<security:authorize access="hasRole('PERSONAL_NEEDS_PROFILE_UPLOAD')">
			</security:authorize>
			<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
				<option value="viewStudents">View Students</option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_TRANSFER_STUDENT')">
					<option value="transferStudents">Transfer Students</option>
			</security:authorize>
			
			 <security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
	           <security:authorize access="hasRole('PERM_STUDENTRECORD_SEARCH')">
			<option value="findStudents">Find Student</option>
			</security:authorize>
			</security:authorize>					
		</select>
	</div>
</div>
<div class="tabTable">
	<div class="studentMessages studentARTSmessages">
		<span class="error_message ui-state-error permissionDeniedMessage hidden" id="studentPermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
		<span class="error_message ui-state-error selectAllLabels hidden validate requiredMessage" id="studentRequiredMessage"><fmt:message key="error.config.required.fields"/></span>
	</div>
	<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
	<div id ="viewStudents" hidden="hidden" class="hidden">
		 <jsp:include page="viewStudents.jsp"></jsp:include>
	</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_STUDENTRECORD_CREATE')">
	<div id ="addStudents" hidden="hidden" class="hidden">
		  <jsp:include page="studentSearch.jsp"></jsp:include>  
	</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
	<div id ="uploadEnrollmentDiv" hidden="hidden" class="hidden">
		 <jsp:include page="uploadEnrollment.jsp"></jsp:include> 
	</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
	<div id ="uploadTecDiv" hidden="hidden" class="hidden">
		 <jsp:include page="uploadTEC.jsp"></jsp:include> 
	</div>
	</security:authorize>	
	<security:authorize access="hasRole('PERM_FIRST_CONTACT_UPLOAD')">
	<div id ="uploadFirstContactDiv" hidden="hidden" class="hidden">
		<%--<jsp:include page="uploadFirstContact.jsp"></jsp:include>--%>
	</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_EXIT_STUDENT')">
	<div id ="exitStudentsDiv" hidden="hidden" class="hidden">
		<jsp:include page="studentExit.jsp"></jsp:include>
	</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_TRANSFER_STUDENT')">
	<div id ="transferStudentsDiv" hidden="hidden" class="hidden">
		 <jsp:include page="studentTransfer.jsp"></jsp:include>
	</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW') ">
	<security:authorize access="  hasRole('PERM_STUDENTRECORD_SEARCH')">
	<div id ="findStudentsDiv" hidden="hidden" class="hidden">
		 <jsp:include page="findStudent.jsp"></jsp:include>
	</div>
	</security:authorize>
	</security:authorize>
	
</div>

<script type="text/javascript"
	src="<c:url value='/js/configuration/studentSearch.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/addStudent.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/students.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/studentExit.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/viewStudents.js'/>"> </script>
	<script type="text/javascript"
	src="<c:url value='/js/configuration/uploadEnrollment.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/uploadTEC.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/findStudent.js'/>"> </script>
	