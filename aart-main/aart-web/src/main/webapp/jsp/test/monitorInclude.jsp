<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/screen.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/css/bcg-jqGrid.css" />

<script type="text/javascript" src="${pageContext.request.contextPath}/js/test/monitor.js"></script>

<script type="text/javascript">
	<security:authorize access="hasAnyRole('REACTIVATE_STUDENT_TESTSESSION')">
		var hasReactivatePermission = true;
	</security:authorize>
	<security:authorize access="!hasAnyRole('REACTIVATE_STUDENT_TESTSESSION')">
		var hasReactivatePermission = false;
	</security:authorize>
	<security:authorize access="hasAnyRole('END_STUDENT_TESTSESSION')">
		var hasEndPermission = true;
	</security:authorize>
	<security:authorize access="!hasAnyRole('END_STUDENT_TESTSESSION')">
		var hasEndPermission = false;
	</security:authorize>
	var testSessionId = '${testSessionId}';
	var rosterId = '${rosterId}';
	var testingProgramName = '${testingProgramName}';
	var assessmentProgramName = '${user.currentAssessmentProgramName}';
 </script>