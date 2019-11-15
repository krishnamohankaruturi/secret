<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<div id ="studentActions">
	<ul id="studentNav" class="nav nav-tabs sub-nav">
	    
	    <security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
			<li class="nav-item get-val">
				<a class="nav-link" href="#viewStudents" data-toggle="tab" role="tab">View Students</a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW') and hasRole('PERM_STUDENTRECORD_SEARCH')">
			<li class="nav-item get-val">
				<a class="nav-link" href="#findStudents" data-toggle="tab" role="tab">Find Student</a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_STUDENTRECORD_CREATE')">
			<li class="nav-item get-val">	
			<!-- <a class="nav-link"  href="javascript:;" onclick="studentsInitNew('addstudents');" data-toggle="tab" role="tab">Add Student</a> -->		
				 <a class="nav-link" href="#addstudents" data-toggle="tab" role="tab" ">Add Student</a> 
			</li>
		</security:authorize>
				<security:authorize access="hasRole('PERM_TRANSFER_STUDENT')">
			<li class="nav-item get-val">
				<a class="nav-link" href="#transferStudents" data-toggle="tab" role="tab">Transfer Students</a>
			</li>
		</security:authorize>
		<security:authorize access="hasAnyRole('PERM_EXIT_STUDENT', 'PERM_EXIT_ALT_STUDENT')">
			<li class="nav-item get-val">
				<a class="nav-link" href="#exitStudents" data-toggle="tab" role="tab">Exit Student</a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
			<li class="nav-item get-val">
				<a class="nav-link" href="#uploadEnrollmentst" data-toggle="tab" role="tab"><fmt:message key="label.config.students.uploadenrollment"/></a>
			</li>
			<li class="nav-item get-val">
				<a class="nav-link" href="#uploadTEC" data-toggle="tab" role="tab"><fmt:message key="label.config.students.uploadtec"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_FIRST_CONTACT_UPLOAD')"></security:authorize>
		<security:authorize access="hasRole('PERSONAL_NEEDS_PROFILE_UPLOAD')">
			<li class="nav-item get-val">
				<a class="nav-link" href="#uploadPNP" data-toggle="tab" role="tab">Upload PNP</a>
			</li>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content">
		<div class="studentMessages studentARTSmessages">
			<span class="error_message ui-state-error permissionDeniedMessage hidden" id="studentPermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
			<%-- <span class="error_message ui-state-error selectAllLabels hidden validate requiredMessage" id="studentRequiredMessage"><fmt:message key="error.config.required.fields"/></span> --%>
		</div>
		<security:authorize access="hasRole('PERM_STUDENTRECORD_CREATE')">
			<div id="addstudents" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/studentSearch.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/studentSearch.js'/>"></script>
				<script type="text/javascript" src="<c:url value='/js/configuration/addStudent.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasAnyRole('PERM_EXIT_STUDENT', 'PERM_EXIT_ALT_STUDENT')">
			<div id="exitStudents" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/studentExit.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/studentExit.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_ENRL_UPLOAD')">
			<div id="uploadEnrollmentst" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/uploadEnrollment.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/uploadEnrollment.js'/>"></script>
			</div>
			<div id="uploadTEC" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/uploadTEC.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/uploadTEC.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_FIRST_CONTACT_UPLOAD')"></security:authorize>
		<security:authorize access="hasRole('PERSONAL_NEEDS_PROFILE_UPLOAD')">
			<div id="uploadPNP" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/uploadPNP.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/uploadPNP.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')">
			<div id="viewStudents" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/viewStudents.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/viewStudents.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_TRANSFER_STUDENT')">
			<div id="transferStudents" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/studentTransfer.jsp"/>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW') AND hasRole('PERM_STUDENTRECORD_SEARCH')">
			<div id="findStudents" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/findStudent.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/findStudent.js'/>"></script>
			</div>
		</security:authorize>
	</div>
</div>

<script type="text/javascript" src="<c:url value='/js/configuration/students.js'/>"></script>
