<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<div>

	<div id="sessionConfirmation">	   
		<div class="messages">
			<div class="error_message ui-state-error hidden" id="sys_error"><fmt:message key="error.generic" /></div> 		
			<div class="error_message ui-state-error hidden" id="testsession_student_required"><fmt:message key='error.testsession.select.student' /></div>
			<div class="error_message ui-state-error hidden" id="testsession_testortestcollection_required"><fmt:message key='error.testsession.select.testortestcollection' /></div>						
			<div class="error_message ui-state-error hidden" id="duplicate_session_name"><fmt:message key='error.testsession.duplicatename' /></div>
			<div id="test_session_created" class="info_message ui-state-highlight hidden"><fmt:message key='label.testsession.created' /></div>
			<div id="test_session_request_status" class="info_message ui-state-highlight hidden"><fmt:message key='label.testsession.request.status' /></div> 
					
					
			<security:authorize access="!hasRole('PERM_TESTSESSION_CREATE')">
				<div class="ui-state-error"><fmt:message key='error.common.permissiondenied.testsession.create' /></div>
			</security:authorize>		
		</div>	
		<div class="top_info">
			<span class ="panel_subhead">
			<br>
	        	Please enter a name for the test session. The name should be 3-20 characters long.
			</span>
			<security:authorize access="hasRole('PERM_QC_TESTSESSION_CREATE')">	
			 	<span class="multipleTestText" style="width: 50%;margin: 30px 0 20px 10px;color: #0e76bc;font-size: 1em;display: block;" >
	        		Note: If more than one test form was selected, the Test Internal Name 
					will be appended to the test session name entered below.
				</span>
			</security:authorize>
			<br>
			<input class="testname" placeholder="Test Name" style="margin-left: 10px;margin-top: 10px;margin-bottom: 11px;" id="testSessionName" maxlength="20" title="Test Name"/>
			<security:authorize access="hasRole('PERM_QC_TESTSESSION_CREATE')">	
				<span class="multipleTestText" style="font-size: 16px;color: grey;">_InternalTestName</span>	
			</security:authorize>	
			<br>
			<br>
			<div class="error_message ui-state-error hidden" id="session_name_required_only_characters">Junk Characters are not allowed</div>
			<div class="error_message ui-state-error hidden" id="session_name_required"><fmt:message key='error.testsession.name.required' /></div>
			<security:authorize access="hasRole('PERM_TESTSESSION_CREATE')">
				<input type="button" id="setupTestSession" value="<fmt:message key='label.common.save'/>" class="btn_blue" />			
			</security:authorize>
			<br/>
		</div>
	</div>
	<br />
</div>

<script>
	var setupTestSessionMessageTimeout = null;
	
	/* $(function() {
		//$('#setupTestSession').removeClass('panel_btn');
		//$('#setupTestSession').attr('disabled','disabled');
	});
 */
</script>
<script type="text/javascript"
	src="<c:url value='/js/test/setupTestSessionSessionInformation.js'/>"> </script>

