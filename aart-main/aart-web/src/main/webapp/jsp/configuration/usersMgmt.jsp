
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userCommon.js'/>"> </script>

<script type="text/javascript"> 
var msg = {
		  label_user_create_success: '<fmt:message key="label.config.user.create.success"/>',
		  label_user_create_failed: '<fmt:message key="error.config.user.create.fail"/>',
		  label_required_fields: '<fmt:message key="error.config.correct.validation"/>',
		  label_email_success: '<fmt:message key="label.config.user.view.email.success"/>',
		  label_user_modify_success: '<fmt:message key="label.config.user.modify.success"/>',
		  label_sameuser_modify_sucess:'<fmt:message key="sucess.myprofile.changeDefaultOrgAndRole"/>',
		  label_user_information: '<fmt:message key="label.config.user.create.userinformation"/>',
		  label_educator_identifier: '<fmt:message key="label.config.user.create.educatoridentifier"/>',
		  label_first_name: '<fmt:message key="label.config.user.create.firstname"/>',
		  label_email: '<fmt:message key="label.config.user.create.email"/>',
		  label_last_name: '<fmt:message key="label.config.user.create.lastname"/>',
		  label_select_org: '<fmt:message key="label.config.user.create.selectorg"/>',
		  label_assign_roles: '<fmt:message key="label.config.user.create.assignroles"/>',
		  label_default_org: '<fmt:message key="label.config.user.create.defaultorg"/>',
		  label_add_org: '<fmt:message key="label.config.user.create.addorg"/>',
		  label_remove_org: '<fmt:message key="label.config.user.create.removeorg"/>',
		  label_save: '<fmt:message key="label.config.user.create.save"/>',
		  		  
		  validation_select_user_for_email: '<fmt:message key="validation.config.user.create.selectuserforemail"/>',
		  validation_educator_identifier: '<fmt:message key="validation.config.user.create.educatoridentifier"/>',
		  validation_first_name: '<fmt:message key="validation.config.user.create.firstname"/>',
		  validation_last_name: '<fmt:message key="validation.config.user.create.lastname"/>',
		  validation_email: '<fmt:message key="validation.config.user.create.email"/>',		  
		  validation_valid_email: '<fmt:message key="validation.config.user.create.validemail"/>',
		  validation_default_org: '<fmt:message key="validation.config.user.create.defaultorganization"/>',
		  validation_all_org: '<fmt:message key="validation.config.user.create.allorg"/>',
		  validation_atleast_one_role: '<fmt:message key="validation.config.user.create.atleastonerole"/>',
		  validation_default_role: '<fmt:message key="validation.config.user.create.defaultrole"/>',
		  validation_atleast_one_assessmentprogram:'<fmt:message key="validation.config.user.create.atleastoneassessmentprogram"/>',
		  validation_noAssessmentProgram:'<fmt:message key="error.noAssessmentProgram"/>',
		  
		  error_permission_denied: '<fmt:message key="error.permissionDenied"/>',
		  error_duplicate_org: '<fmt:message key="error.config.user.create.duporg"/>',
		  error_duplicate_user: '<fmt:message key="error.config.user.create.dupuser"/>',
		  error_email_error: '<fmt:message key="error.config.user.view.email.error"/>',
		  error_email_noselect: '<fmt:message key="validation.config.user.create.selectuserforemail"/>',
		  error_user_modify_failed: '<fmt:message key="error.config.user.modify.fail"/>',
		  error_user_create_eduorgexists: '<fmt:message key="error.config.user.create.identifierorgexists"/>',
		  error_duplicate_user_emailaddr: '<fmt:message key="error.config.user.dupuser.emailaddress"/>',
		  error_educator_identifier_not_null : '<fmt:message key="error.config.user.dupuser.educatoridentifier"/>',
		  error_dtc_singleuser_restricted : '<fmt:message key="error.config.dtc.singleuser.restricted"/>',
		  error_btc_singleuser_restricted : '<fmt:message key="error.config.btc.singleuser.restricted"/>'
		 };
	var hasUserModifyPermission = false;
	var isMergeScreen = false;
	var mergeUserRolesGridData = [];
	<security:authorize access="hasRole('PERM_USER_MODIFY')">
		hasUserModifyPermission = true;
	</security:authorize>
</script>

<div class="form action-bar">
	<div class="form-fields">
		<label for="usersSelect" class="isrequired form-label"><fmt:message key="label.config.select.action"/><font size="5" color="red">*</font>:</label>			
		<select id="usersSelect" class="bcg_select" name="actions">
		    <option value=""><fmt:message key="label.config.select.action.default"/></option>
			<security:authorize access="hasRole('PERM_USER_CREATE')">
				<option value="add"><fmt:message key="label.config.user.create.action"/></option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_USER_CLAIM')">
				<option value="find"><fmt:message key="label.config.user.find.action"/></option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_USER_MERGE')">
				<option value="merge"><fmt:message key="label.config.user.merge.action"/></option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_USER_MOVE')">
				<option value="move"><fmt:message key="label.config.user.move.action"/></option>
			</security:authorize>
			<security:authorize access="hasRole('PERM_USER_VIEW')">
		 		<option value="view"><fmt:message key="label.config.user.view.action"/></option>
		 	</security:authorize>
		 	<security:authorize access="hasRole('PD_TRAINING_RESULTS_UPLOADER')">
				<option value="uploadPDResults"><fmt:message key="label.config.user.uploadPDResults.action"/></option>
			</security:authorize>		
		 	<security:authorize access="hasRole('PERM_USER_UPLOAD')">
				<option value="upload"><fmt:message key="label.config.user.upload.action"/></option>
			</security:authorize>	
				<security:authorize access="hasRole('PERM_USER_SPECIAL')">
				<option value="special"><fmt:message key="label.config.user.special.action"/></option>
			</security:authorize>	
		</select>
		<span style="margin-left:225px" id="mergeUserSpan" class='hidden'> <label>Select only TWO users to merge, then click</label><button class="panel_btn"  id="mergeUserNextButton" ><fmt:message key="label.config.user.create.next"/></button></span>
	</div>
	
</div>

<div class="tabTable">
	<div id="UserARTSmessages" class="userMessages">
		<span class="error_message ui-state-error permissionDeniedMessage hidden" id="UserpermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
        <span class="info_message ui-state-highlight successMessage hidden" id="UsersuccessMessage" ><fmt:message key="label.config.user.create.success"/></span>
        <span class="error_message ui-state-error selectAllLabels hidden validate" id="UserfailMessage"><fmt:message key="error.config.user.create.fail"/></span>
        <span class="info_message ui-state-highlight successMessage hidden" id="eUserMgmtModifysuccessMessage" ><fmt:message key="label.config.user.modify.success"/></span>
        <span class="info_message ui-state-highlight successMessage hidden" id="esameUserMgmtsuccessMessage" ><fmt:message key="sucess.myprofile.changeDefaultOrgAndRole"/></span>
        <span class="info_message ui-state-highlight successMessage hidden" id="eUserMergesuccessMessage" ><fmt:message key="label.config.user.merge.success"/></span>
        <span class="error_message ui-state-error selectAllLabels hidden validate" id="eUserMgmtModifyfailMessage"><fmt:message key="error.config.user.modify.fail"/></span>
        <span class="error_message ui-state-error selectAllLabels hidden validate" id="UserrequiredMessage"><fmt:message key="error.config.correct.validation"/></span>
        <span class="error_message ui-state-error selectAllLabels hidden validate" id="duplicateorganization"><fmt:message key="error.config.user.create.duporg"/></span>
		<span class="error_message ui-state-error selectAllLabels hidden duplicate" id="UserduplicateMessage" ><fmt:message key="error.config.user.create.dupuser"/></span>
		<span class="info_message ui-state-highlight emails_sent hidden" id="emails_sent_id" ><fmt:message key="label.config.user.view.email.success"/></span>
		<span class="error_message ui-state-error emails_not_sent hidden validate" id="emails_not_sent_id"><fmt:message key="error.config.user.view.email.error"/></span>
		<span class="info_message ui-state-highlight account_activated hidden" id="account_activated_id" ><fmt:message key="label.config.user.view.account.activate.success"/></span>
		<span class="error_message ui-state-error account_not_activated hidden validate" id="account_not_activated_id"><fmt:message key="error.config.user.view.account.activate.error"/></span>
		<span class="info_message ui-state-highlight account_inactivated hidden" id="account_inactivated_id" ><fmt:message key="label.config.user.view.account.inactivate.success"/></span>
		<span class="error_message ui-state-error account_not_inactivated hidden validate" id="account_not_inactivated_id"><fmt:message key="error.config.user.view.account.inactivate.error"/></span>
		<span class="error_message ui-state-error select_at_least_one hidden validate" id="select_at_least_one_id"><fmt:message key="validation.config.user.create.selectuserforemail"/></span>		
		<span class="error_message ui-state-error account_unAuthorized hidden validate" id="account_unAuthorized_id"><fmt:message key="validation.config.user.create.account_unAuthorized"/></span>		
		<span class="error_message ui-state-error pending_user_selected hidden validate" id="pending_user_selected_id"><fmt:message key="validation.config.user.create.selectpendingusers"/></span>
		<span class="error_message ui-state-error inactive_user_selected hidden validate" id="inactive_user_selected_id"><fmt:message key="validation.config.user.create.inactiveuserselected"/></span>
		<span class="error_message ui-state-error active_user_selected hidden validate" id="active_user_selected_id"><fmt:message key="validation.config.user.create.activeuserselected"/></span>
		<span class="error_message ui-state-error invalidFormat hidden validate" id="UserInvalidFormatMessage"></span>
		<span class="error_message ui-state-error selectAllLabels hidden validate" id="UserEduOrgExistsMessage"><fmt:message key="error.config.user.create.identifierorgexists"/></span>
		<span class="error_message ui-state-error selectAllLabels hidden validate" id="eUserMgmtEduOrgExistsMessage"><fmt:message key="error.config.user.create.identifierorgexists"/></span>
		<span class="info_message ui-state-highlight moveUserSuccessful hidden" id="moveUserSuccessful" ><fmt:message key="label.config.user.move.success"/></span>
		<span class="info_message ui-state-highlight moveUserPartial hidden" id="moveUserPartial" ><fmt:message key="label.config.user.move.partial"/></span>
		<span class="error_message ui-state-error moveUserInternalError hidden validate" id="moveUserInternalError"><fmt:message key="error.config.user.move.error"/></span>
	</div>
	<div class="row">
		<div id ="viewUsers" hidden="hidden" class="hidden">
			<jsp:include page="viewUsers.jsp"></jsp:include>
		</div>
		
		<div id ="uploadUsers" hidden="hidden" class="hidden">
			<jsp:include page="uploadUsers.jsp"></jsp:include>
		</div>
		
		<div id ="uploadPDTrainingResults" hidden="hidden" class="hidden">
			<jsp:include page="uploadPDTrainingResults.jsp"></jsp:include>
		</div>
		
		<div id ="addUsers" hidden="hidden" class="hidden">
			<jsp:include page="addUsers.jsp"></jsp:include>
		</div>
		
		<div id ="moveUsers" hidden="hidden" class="hidden">
			<jsp:include page="moveUsers.jsp"></jsp:include>
		</div>
		
		<div id ="editUsers" hidden="hidden" class="hidden">
			<jsp:include page="editUsers.jsp"></jsp:include>
		</div>
		<div id ="mergeUsers" hidden="hidden" class="hidden">
			<jsp:include page="mergeUsers.jsp"></jsp:include>
		</div>
		<div id ="editMergingUsers" hidden="hidden" class="hidden">
			<jsp:include page="editMergingUsers.jsp"></jsp:include>
		</div>
		<div id ="editMergingUsersToKeep" hidden="hidden" class="hidden">
			<jsp:include page="editMergingUsersToKeep.jsp"></jsp:include>
		</div>
		
		<div id ="findUsers" hidden="hidden" class="hidden">
			<jsp:include page="findUsers.jsp"></jsp:include>
		</div>
		<div id="specialUsers" hidden="hidden" class="hidden">
			<jsp:include page="specialUsers.jsp"></jsp:include>
			</div>
	</div>	
</div>

<script type="text/javascript">
var lConfiguserview = '<fmt:message key="label.config.user.view"/>';
var lConfigUserMerge = '<fmt:message key="label.config.user.merge"/>';
var lConfigUserupload = '<fmt:message key="label.config.user.upload"/>';
var lConfigUserUploadPDResults = '<fmt:message key="label.config.user.uploadPDResults"/>';
var lConfigUserCreate = '<fmt:message key="label.config.user.create"/>';
var lConfigUserFind = '<fmt:message key="label.config.user.find"/>';
$(function() {
	if($('#externalLink').val()==='true'){
		$.ajax({
			url : 'removeExternalLink.htm',
			data :{
				},
			dataType : 'json',
			type : "GET",
			success : function(data) {
				
			}
		});
		setTimeout(function(){ 
			$('#usersSelect').find('option[value=view]').attr('selected','selected').trigger("change.select2");
		},1000);
	}
});
</script>
 <script type="text/javascript" src="<c:url value='/js/configuration/userMgmt.js'/>"> </script>