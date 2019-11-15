<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/include.jsp" %>

<script type="text/javascript" src="<c:url value='/js/configuration/viewTestRecord.js'/>"> </script>

<div class="form action-bar">
	<div class="form-fields">
		<label for="actions" class="isrequired form-label"><fmt:message key="label.config.select.action"/><font size="5" color="red">*</font>:</label>			
		<select id="testRecordsActions" class="bcg_select" name="testRecordsActions" title="Select Action">
			<option value=""><fmt:message key="label.common.select"/></option>
			<security:authorize access="hasRole('PERM_TESTRECORD_CREATE')">
					<option value="createTestRecords">Create Test Record</option>
			</security:authorize>
			
			<security:authorize access="hasRole('PERM_TESTRECORD_CLEAR')">
					<option value="clearTestRecords">Clear Test Record</option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
					<option value="uploadTEC"><fmt:message key="label.config.students.uploadtec"/></option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_TESTRECORD_VIEW')">
				<option value="viewTestRecords">View Test Record</option>
			</security:authorize>
			
		</select>
	</div>
</div>

<div class="tabTable">
	<div id="testRecordARTSmessages" class="studentMessages">
		<span class="error_message ui-state-error permissionDeniedMessage hidden" id="testRecordPermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
		<span class="error_message ui-state-error selectAllLabels hidden validate requiredMessage" id="testRecordRequiredMessage"><fmt:message key="error.config.required.fields"/></span>
	</div>
	<security:authorize access="hasRole('PERM_TESTRECORD_CREATE')">
		<div id ="createTestRecordDiv" hidden="hidden" class="hidden">
			 <jsp:include page="createTestRecord.jsp"></jsp:include> 
		</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_TESTRECORD_CLEAR')">
		<div id ="clearTestRecordDiv" hidden="hidden" class="hidden">
			 <jsp:include page="clearTestRecord.jsp"></jsp:include> 
		</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_TESTRECORD_VIEW')">	
	<div id ="viewTestRecordDiv" hidden="hidden" class="hidden">
		<jsp:include page="viewTestRecord.jsp"></jsp:include>
	</div>
	</security:authorize>
	<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
	<div id ="tecUploadDiv" hidden="hidden" class="hidden">
		 <jsp:include page="tecUpload.jsp"></jsp:include> 
	</div>
	</security:authorize>
</div>
<script type="text/javascript" 
	src="<c:url value='/js/configuration/testRecords.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/configuration/testRecordsClear.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/configuration/tecUpload.js'/>"> </script>