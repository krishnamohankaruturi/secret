<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/jsp/include.jsp" %>

	<div id ="testRecordsActions">
		<ul id="testRecordsNav" class="nav nav-tabs sub-nav">
		
			<security:authorize access="hasRole('PERM_TESTRECORD_VIEW')">
				<li class="nav-item get-testRecords">
					<a class="nav-link" href="#viewTestRecords" data-toggle="tab" role="tab">View Test Record</a>
				</li>
			</security:authorize>
		    
		    <security:authorize access="hasRole('PERM_TESTRECORD_CREATE')">
				<li class="nav-item get-testRecords">
					<a class="nav-link" href="#createTestRecords" data-toggle="tab" role="tab">Create Test Record</a>
				</li>
			</security:authorize>	
				
		    <security:authorize access="hasRole('PERM_TESTRECORD_CLEAR')">
				<li class="nav-item get-testRecords">
					<a class="nav-link" href="#clearTestRecords" data-toggle="tab" role="tab">Clear Test Record</a>
				</li>
			</security:authorize>
			
			<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
				<li class="nav-item get-testRecords">
					<a class="nav-link" href="#uploadTEC" data-toggle="tab" role="tab"><fmt:message key="label.config.students.uploadtec"/></a>
				</li>
			</security:authorize>
			
		</ul>
	</div>

	<div id="content" class="tab-content">
		
		<security:authorize access="hasRole('PERM_TESTRECORD_VIEW')">
			<div id="viewTestRecords" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/viewTestRecord.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/viewTestRecord.js'/>"> </script>
			</div>
		</security:authorize>		
		<security:authorize access="hasRole('PERM_TESTRECORD_CREATE')">
			<div id="createTestRecords" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/createTestRecord.jsp"></jsp:include> 
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_TESTRECORD_CLEAR')">
			<div id="clearTestRecords" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/clearTestRecord.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/testRecordsClear.js'/>"> </script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
			<div id="uploadTEC" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/tecUpload.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/tecUpload.js'/>"> </script>
			</div>
		</security:authorize>
		
	</div>

	<script type="text/javascript" src="<c:url value='/js/configuration/testRecords.js'/>"> </script>