
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userCommon.js'/>"> </script>
<style>
.input-large {
  width: 232px !important;
}
.form .form-fields input {
  height: 34px !important;
}

.bcg_select
{
  width: 250px;
}
._bcg .full_side {
    margin-right:7.5%;
}

input,
textarea,
select,
.uneditable-input {
height:28px;
}
._bcg .tab-pane .panel_btn {
	background: #0e76bc url("../images/btn-bg.png") no-repeat center center;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	-ms-border-radius: 4px;
	-o-border-radius: 4px;
	border-radius: 4px;
	border: 0;
	display: inline-block;
	padding: 10px 20px;
	color: white;
	text-decoration: none;
	font-size: 1em;
	font-weight: 300;
	line-height: 20px;
	-webkit-transition: all .3s ease-in-out;
	-moz-transition: all .3s ease-in-out;
	-o-transition: all .3s ease-in-out;
	transition: all .3s ease-in-out;
	margin-top: 24px;
}

</style>

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

<div id ="userActions">
	<ul id="userNav" class="nav nav-tabs sub-nav">
			<security:authorize access="hasRole('PERM_USER_VIEW')">
		 	<li class="nav-item get-users">	
		 		<a class="nav-link" href="#viewUsers" data-toggle="tab" role="tab"><fmt:message key="label.config.user.view.action"/></a>
		 		</li>
		 	</security:authorize>
		 	
		 	<security:authorize access="hasRole('PERM_USER_CREATE')">
	        <li class="nav-item get-users">	
	            <a class="nav-link" href="#addusers" data-toggle="tab" role="tab"><fmt:message key="label.config.user.create.action"/></a> 
	        </li>
	        </security:authorize>
	        
	        <security:authorize access="hasRole('PERM_USER_UPLOAD')">
				<li class="nav-item get-users">	
				<a class="nav-link" href="#uploadUsers" data-toggle="tab" role="tab"><fmt:message key="label.config.user.upload.action"/></a>
			</li>
			
			<security:authorize access="hasRole('PERM_USER_CLAIM')">
	            <li class="nav-item get-users">	
	            <a class="nav-link" href="#findUsers" data-toggle="tab" role="tab">
				<fmt:message key="label.config.user.find.action"/> </a>
			</li>
			</security:authorize>
			
			</security:authorize>
			
			<security:authorize access="hasRole('PERM_USER_MERGE')">
			<li class="nav-item get-users">	
				<a class="nav-link" href="#mergeUsers" data-toggle="tab" role="tab"><fmt:message key="label.config.user.merge.action"/></a>
			</li>
			</security:authorize>
			
			<security:authorize access="hasRole('PERM_USER_MOVE')">
			<li class="nav-item get-users">	
				<a class="nav-link" href="#moveUsers" data-toggle="tab" role="tab"><fmt:message key="label.config.user.move.action"/></a>
			</li>
			</security:authorize>
			
				<security:authorize access="hasRole('PD_TRAINING_RESULTS_UPLOADER')">
				<li class="nav-item get-users">	
				<a class="nav-link" href="#uploadPDTrainingResults" data-toggle="tab" role="tab"><fmt:message key="label.config.user.uploadPDResults.action"/></a>
			</li>
			</security:authorize>
				
	<security:authorize access="hasRole('PERM_USER_SPECIAL')">
				<li class="nav-item get-users">	
				<a class="nav-link" href="#specialUsers" data-toggle="tab" role="tab"><fmt:message key="label.config.user.special.action"/></a>
			</li>
			</security:authorize>	
		 	
	</ul>
		

<!-- <div class="tabTable"> -->
	<div id="content" class="tab-content">
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
	
	<security:authorize access="hasRole('PERM_USER_CREATE')">		
			<div id ="addUsers" class="tab-pane" role="tabpanel">			
			<jsp:include page="../configuration/addUsers.jsp"></jsp:include>
		<script type="text/javascript" src="<c:url value='/js/userAdd.js'/>"></script>
		</div>		
		</security:authorize>
		
		<security:authorize access="hasRole('PERM_USER_CLAIM')">
			<div id ="findUsers" class="tab-pane" role="tabpanel">					
			<jsp:include page="../configuration/findUsers.jsp"></jsp:include>
		<script type="text/javascript" src="<c:url value='/js/userFind.js'/>"></script>
		</div>		
		</security:authorize>
		
		<security:authorize access="hasRole('PERM_USER_MERGE')">
			<div id ="mergeUsers" class="tab-pane" role="tabpanel">
			<jsp:include page="../configuration/mergeUsers.jsp"></jsp:include>
			<script type="text/javascript" src="<c:url value='/js/userMerge.js'/>"> </script>
		</div>
			 </security:authorize>
			 
				<security:authorize access="hasRole('PERM_USER_SPECIAL')">
			<div id ="specialUsers" class="tab-pane" role="tabpanel">
			<jsp:include page="../configuration/specialUsers.jsp"></jsp:include>
			<script type="text/javascript" src="<c:url value='/js/userSpecial.js'/>"> </script>
		</div>
			</security:authorize> 
			
			<security:authorize access="hasRole('PERM_USER_MOVE')">
			<div id ="moveUsers" class="tab-pane" role="tabpanel">
			<jsp:include page="../configuration/moveUsers.jsp"></jsp:include>
			<script type="text/javascript" src="<c:url value='/js/userMove.js'/>"> </script>
		</div>
		
			</security:authorize>
				<security:authorize access="hasRole('PD_TRAINING_RESULTS_UPLOADER')">
				<div id ="uploadPDTrainingResults" class="tab-pane" role="tabpanel">
			<jsp:include page="../configuration/uploadPDTrainingResults.jsp"></jsp:include>
		</div>
			</security:authorize>	
			<security:authorize access="hasRole('PERM_USER_UPLOAD')">
				<div id ="uploadUsers" class="tab-pane" role="tabpanel">
			<jsp:include page="../configuration/uploadUsers.jsp"></jsp:include>
		<script type="text/javascript" src="<c:url value='/js/userUpload.js'/>"> </script>
		</div>
			</security:authorize>	
			<security:authorize access="hasRole('PERM_USER_VIEW')">
		 	<div id ="viewUsers" class="tab-pane" role="tabpanel">
			<jsp:include page="../configuration/viewUsers.jsp"></jsp:include>
			<script type="text/javascript" src="<c:url value='/js/userView.js'/>"> </script>
		</div>
		 	</security:authorize>
		 	<div id ="editUsers" class="hidden">		 	
			<jsp:include page="../configuration/editUsers.jsp"></jsp:include>
			<script type="text/javascript" src="<c:url value='/js/userEdit.js'/>"> </script>		
		</div>
		<div id ="editMergingUsers"  class="hidden">
			<jsp:include page="../configuration/editMergingUsers.jsp"></jsp:include>
 		<%-- <script type="text/javascript" src="<c:url value='/js/userEditMerging.js'/>"> </script>  --%>
		</div>
		
			<div id ="editMergingUsersToKeep"  class="hidden">
			<jsp:include page="../configuration/editMergingUsersToKeep.jsp"></jsp:include>
 		<%-- <script type="text/javascript" src="<c:url value='/js/userEditMerging.js'/>"> </script>  --%>
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
			type : "GET"
		}).done(function(data) {
			
		});
		setTimeout(function(){ 
			$('#usersSelect').find('option[value=view]').attr('selected','selected').trigger("change.select2");
		},1000);
	}
});
</script>
 <script type="text/javascript" src="<c:url value='/js/configuration/userMgmt.js'/>"> </script>

 