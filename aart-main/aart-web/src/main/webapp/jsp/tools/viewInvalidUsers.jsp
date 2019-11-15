<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript" src="<c:url value='/js/userCommon.js'/>">
</script>
<script type="text/javascript" src="<c:url value='/js/userEdit.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.json-2.4.min.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/inactiveUserView.js'/>"> </script>


<script type="text/javascript">
	var msg = {
		label_user_create_success : '<fmt:message key="label.config.user.create.success"/>',
		label_user_create_failed : '<fmt:message key="error.config.user.create.fail"/>',
		label_required_fields : '<fmt:message key="error.config.correct.validation"/>',
		label_email_success : '<fmt:message key="label.config.user.view.email.success"/>',
		label_user_modify_success : '<fmt:message key="label.config.user.modify.success"/>',
		label_sameuser_modify_sucess : '<fmt:message key="sucess.myprofile.changeDefaultOrgAndRole"/>',
		label_user_information : '<fmt:message key="label.config.user.create.userinformation"/>',
		label_educator_identifier : '<fmt:message key="label.config.user.create.educatoridentifier"/>',
		label_first_name : '<fmt:message key="label.config.user.create.firstname"/>',
		label_email : '<fmt:message key="label.config.user.create.email"/>',
		label_last_name : '<fmt:message key="label.config.user.create.lastname"/>',
		label_select_org : '<fmt:message key="label.config.user.create.selectorg"/>',
		label_assign_roles : '<fmt:message key="label.config.user.create.assignroles"/>',
		label_default_org : '<fmt:message key="label.config.user.create.defaultorg"/>',
		label_add_org : '<fmt:message key="label.config.user.create.addorg"/>',
		label_remove_org : '<fmt:message key="label.config.user.create.removeorg"/>',
		label_save : '<fmt:message key="label.config.user.create.save"/>',

		validation_select_user_for_email : '<fmt:message key="validation.config.user.create.selectuserforemail"/>',
		validation_educator_identifier : '<fmt:message key="validation.config.user.create.educatoridentifier"/>',
		validation_first_name : '<fmt:message key="validation.config.user.create.firstname"/>',
		validation_last_name : '<fmt:message key="validation.config.user.create.lastname"/>',
		validation_email : '<fmt:message key="validation.config.user.create.email"/>',
		validation_valid_email : '<fmt:message key="validation.config.user.create.validemail"/>',
		validation_default_org : '<fmt:message key="validation.config.user.create.defaultorganization"/>',
		validation_all_org : '<fmt:message key="validation.config.user.create.allorg"/>',
		validation_atleast_one_role : '<fmt:message key="validation.config.user.create.atleastonerole"/>',
		validation_default_role : '<fmt:message key="validation.config.user.create.defaultrole"/>',
		validation_atleast_one_assessmentprogram : '<fmt:message key="validation.config.user.create.atleastoneassessmentprogram"/>',
		validation_noAssessmentProgram : '<fmt:message key="error.noAssessmentProgram"/>',

		error_permission_denied : '<fmt:message key="error.permissionDenied"/>',
		error_duplicate_org : '<fmt:message key="error.config.user.create.duporg"/>',
		error_duplicate_user : '<fmt:message key="error.config.user.create.dupuser"/>',
		error_email_error : '<fmt:message key="error.config.user.view.email.error"/>',
		error_email_noselect : '<fmt:message key="validation.config.user.create.selectuserforemail"/>',
		error_user_modify_failed : '<fmt:message key="error.config.user.modify.fail"/>',
		error_user_create_eduorgexists : '<fmt:message key="error.config.user.create.identifierorgexists"/>',
		error_duplicate_user_emailaddr : '<fmt:message key="error.config.user.dupuser.emailaddress"/>',
		error_educator_identifier_not_null : '<fmt:message key="error.config.user.dupuser.educatoridentifier"/>',
		error_dtc_singleuser_restricted : '<fmt:message key="error.config.dtc.singleuser.restricted"/>',
		error_btc_singleuser_restricted : '<fmt:message key="error.config.btc.singleuser.restricted"/>'
	};
	var hasUserModifyPermission = true;
	var isMergeScreen = false;
</script>
<style>
		
.header {
	    float: left;
	    margin-right: 100px;
	    width:100%;
}

.leftmargin {
	margin-left:20px;
}

.rightMargin {
	float:right;
	margin-right:20px;
}


</style>
<div class="tabTable">
	<h1 id="inactiveAccountMgmtHeader">Inactive Acccount Management</h1>
	<div id="UserARTSmessages" class="userMessages">
		<span class="error_message ui-state-error permissionDeniedMessage hidden" id="UserpermissionDeniedMessage"><fmt:message
				key="error.permissionDenied" /></span> <span class="info_message ui-state-highlight successMessage hidden"
			id="UsersuccessMessage"><fmt:message key="label.config.user.create.success" /></span> <span
			class="error_message ui-state-error selectAllLabels hidden validate" id="UserfailMessage"><fmt:message
				key="error.config.user.create.fail" /></span> <span class="info_message ui-state-highlight successMessage hidden"
			id="vInvalidUsersuccessMessage"><fmt:message key="label.config.user.modify.success" /></span> <span
			class="info_message ui-state-highlight successMessage hidden" id="samevInvalidUsersuccessMessage"><fmt:message
				key="sucess.myprofile.changeDefaultOrgAndRole" /></span> <span
			class="info_message ui-state-highlight successMessage hidden" id="eUserMergesuccessMessage"><fmt:message
				key="label.config.user.merge.success" /></span> <span class="error_message ui-state-error selectAllLabels hidden validate"
			id="vInvalidUserModifyfailMessage"><fmt:message key="error.config.user.modify.fail" /></span> <span
			class="error_message ui-state-error selectAllLabels hidden validate" id="UserrequiredMessage"><fmt:message
				key="error.config.correct.validation" /></span> <span class="error_message ui-state-error selectAllLabels hidden validate"
			id="duplicateorganization"><fmt:message key="error.config.user.create.duporg" /></span> <span
			class="error_message ui-state-error selectAllLabels hidden duplicate" id="UserduplicateMessage"><fmt:message
				key="error.config.user.create.dupuser" /></span> <span class="info_message ui-state-highlight emails_sent hidden"
			id="emails_sent_id"><fmt:message key="label.config.user.view.email.success" /></span> <span
			class="error_message ui-state-error emails_not_sent hidden validate" id="emails_not_sent_id"><fmt:message
				key="error.config.user.view.email.error" /></span> <span class="info_message ui-state-highlight account_activated hidden"
			id="account_activated_id"><fmt:message key="label.config.user.view.account.activate.success" /></span> <span
			class="error_message ui-state-error account_not_activated hidden validate" id="account_not_activated_id"><fmt:message
				key="error.config.user.view.account.activate.error" /></span> <span
			class="info_message ui-state-highlight account_inactivated hidden" id="account_inactivated_id"><fmt:message
				key="label.config.user.view.account.inactivate.success" /></span> <span
			class="error_message ui-state-error account_not_inactivated hidden validate" id="account_not_inactivated_id"><fmt:message
				key="error.config.user.view.account.inactivate.error" /></span> <span
			class="error_message ui-state-error select_at_least_one hidden validate" id="select_at_least_one_id"><fmt:message
				key="validation.config.user.create.selectuserforemail" /></span> <span
			class="error_message ui-state-error account_unAuthorized hidden validate" id="account_unAuthorized_id"><fmt:message
				key="validation.config.user.create.account_unAuthorized" /></span> <span
			class="error_message ui-state-error pending_user_selected hidden validate" id="pending_user_selected_id"><fmt:message
				key="validation.config.user.create.selectpendingusers" /></span> <span
			class="error_message ui-state-error inactive_user_selected hidden validate" id="inactive_user_selected_id"><fmt:message
				key="validation.config.user.create.inactiveuserselected" /></span> <span
			class="error_message ui-state-error active_user_selected hidden validate" id="active_user_selected_id"><fmt:message
				key="validation.config.user.create.activeuserselected" /></span> <span
			class="error_message ui-state-error invalidFormat hidden validate" id="UserInvalidFormatMessage"></span> <span
			class="error_message ui-state-error selectAllLabels hidden validate" id="UserEduOrgExistsMessage"><fmt:message
				key="error.config.user.create.identifierorgexists" /></span> <span
			class="error_message ui-state-error selectAllLabels hidden validate" id="vInvalidUserEduOrgExistsMessage"><fmt:message
				key="error.config.user.create.identifierorgexists" /></span> <span
			class="info_message ui-state-highlight moveUserSuccessful hidden" id="moveUserSuccessful"><fmt:message
				key="label.config.user.move.success" /></span> <span
			class="error_message ui-state-error moveUserInternalError hidden validate" id="moveUserInternalError"><fmt:message
				key="error.config.user.move.error" /></span>
	</div>
</div>
<div>
	<label class="hidden error" id="messageViewInvalidUSers"></label>
	<div id="viewInactiveUserGridCell">
		<div id="viewInactiveUser" hidden="hidden" class="hidden"></div>
		<div id="viewInactiveUserGridContainer" class="kite-table">
			<table class="responsive" id="viewInactiveUserGridTableId"></table>
			<div id="viewInactiveUserGridPager" style="width: auto;"></div>
		</div>
	</div>
</div>
<div class="row">
	<div id="editUsers" class="hidden">
		<div class="_bcg">
			<div id="editusers-contain" class="form wrap_bcg" style="margin-bottom: 5%;">
				<table id="editcontainertable" style="width:98%;">
		            <tr>
		                <td style="text-align:right;">
		                	<security:authorize access="hasRole('PERM_USER_MODIFY')">
		                		<button id="btn_editTop_Admin" class="btn_blue" style="float:right;margin-right: 5%;margin-bottom: 0;">
		                			<fmt:message key="label.config.user.create.save"/>
		                		</button>
		                	</security:authorize>	
		                </td>
		            </tr>
		            <tr>
		            	<td>
							<div id="eUserARTSmessages" class="userMessages">
								<span class="error_message ui-state-error permissionDeniedMessage hidden" id="eUserpermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
						        <span class="info_message ui-state-highlight successMessage hidden" id="evInvalidUsersuccessMessage" ><fmt:message key="label.config.user.create.success"/></span>
						        <span class="error_message ui-state-error selectAllLabels hidden validate" id="evInvalidUserCreatefailMessage"><fmt:message key="error.config.user.create.fail"/></span>
						        <span class="info_message ui-state-highlight successMessage hidden" id="evInvalidUserModifysuccessMessage" ><fmt:message key="label.config.user.modify.success"/></span>
						        <span class="info_message ui-state-highlight successMessage hidden" id="esamevInvalidUsersuccessMessage" ><fmt:message key="sucess.myprofile.changeDefaultOrgAndRole"/></span>
						        <span class="error_message ui-state-error selectAllLabels hidden validate" id="evInvalidUserModifyfailMessage"><fmt:message key="error.config.user.modify.fail"/></span>
						        <span class="error_message ui-state-error selectAllLabels hidden validate" id="eUserrequiredMessage"><fmt:message key="error.config.required.fields"/></span>
						        <span class="error_message ui-state-error selectAllLabels hidden validate" id="eduplicateorganization"><fmt:message key="error.config.user.create.duporg"/></span>
								<span class="error_message ui-state-error selectAllLabels hidden duplicate" id="eUserduplicateMessage" ><fmt:message key="error.config.user.create.dupuser"/></span>
								<span class="info_message ui-state-highlight emails_sent hidden" id="eemails_sent_id" ><fmt:message key="label.config.user.view.email.success"/></span>
								<span class="error_message ui-state-error emails_not_sent hidden validate" id="eemails_not_sent_id"><fmt:message key="error.config.user.view.email.error"/></span>
								<span class="error_message ui-state-error select_at_least_one hidden validate" id="eselect_at_least_one_id"><fmt:message key="validation.config.user.create.selectuserforemail"/></span>
								<span class="error_message ui-state-error invalidFormat hidden validate" id="eUserInvalidFormatMessage"></span>
								<span class="error_message ui-state-error selectAllLabels hidden validate" id="evInvalidUserEduOrgExistsMessage"><fmt:message key="error.config.user.create.identifierorgexists"/></span>
								<span class="error_message ui-state-error selectAllLabels hidden validate" id="eInvalidOrgRoleMessage"></span>
								
							</div>            	
		            	</td>
		            </tr>
		            <tr>
		                <td>
		                	<div >
				                <span class="error" id="eduplicateUserError"></span><br>
				                <span class="error" id="eidOrgUserError"></span>
				                <span class="error" id="eInvalidOrganizationRoleMessage"></span><br>
		               	
		                		<h1 class="panel_head sub"><fmt:message key="label.config.user.create.userinformation"/></h1>
		         				<div class="form-fields">
		        					<label class="field-label"><fmt:message key="label.config.user.create.firstname"/><span class="lbl-required">*</span></label>
		        					<input type="text" id="efirstName" class="input-large" maxlength = "79" title="First Name"/>
		         				</div>
		         				<div class="form-fields">
		         					<label class="field-label"><fmt:message key="label.config.user.create.lastname"/><span class="lbl-required">*</span></label>
		         					<input type="text" id="elastName" class="input-large" maxlength = "79" title="Last Name"/>
		         				</div>
		         				<div class="form-fields">
		        					<label class="field-label"><fmt:message key="label.config.user.create.email"/><span class="lbl-required">*</span></label>
		        					<input type="text" id="eemailAddress" class="input-large" maxlength = "254" title="Email"/>
		         				</div>
		         				<div class="form-fields">
		       						<label class="field-label"><fmt:message key="label.config.user.create.educatoridentifier"/></label>
		       						<input type="text" id="eeducatorIdentifier" class="input-large" maxlength = "254" title="Educator Identifier"/>
		        				</div>
		         			</div>             
		                </td>
		           	</tr>           	
		           	<security:authorize access="hasRole('PERM_USER_MODIFY')">
			            <tr><td><h1 class="panel_head sub">Select Organization & Assign Roles</h1></td></tr>
			           	<tr>
			           		<td>
								<div class="form-fields" style="width: 450px;">
									<span><label for="editUserOrganization" class="field-label" 
											style="display: inline-block;text-align: right;width: 190px;">State:<span class="lbl-required">*</span></label>
										<select id="editUserOrganization" class="switchRole" style="clear: both;display: inline-block;">
											<option value="" >Select</option>
										</select>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td>	
								<div class="form-fields" style="width: 450px;">
									<span><label for="editUserAssessmentProgram" class="field-label"
										style="display: inline-block;text-align: right;width: 190px;"><fmt:message key="label.home.assessmentprogram.select"/>:<span class="lbl-required">*</span></label>
									<select id="editUserAssessmentProgram" class="switchRole" style="clear: both;display: inline-block;">
										<option value="" >Select</option>
									</select>
									</span>
								</div>
							</td>
						</tr>
						<tr>
							<td>
							    <div class="form-fields" style="width: 450px;">
							    	<span> <label for="editUserGroup" class="field-label" 
							    		style="display: inline-block;text-align: right;width: 190px;"><fmt:message key="label.home.role.select"/>:<span class="lbl-required">*</span></label>
										<select id="editUserGroup" class="switchRole js-role-map" style="clear: both;display: inline-block;">
											<option value="">Select</option>
							 			</select>
						 			</span>
						 		</div>
			           		</td>
			           	</tr>
			           	<tr>
							<td>
							    <div id="editRoleRegionContainer" class="form-fields" style="width: 450px; display:none;">
							    	<span> <label for="editUserRegion" class="field-label" 
							    		style="display: inline-block;text-align: right;width: 190px;">Region:<span class="lbl-required">*</span></label>
										<select id="editUserRegion" class="switchRole" style="clear: both;display: inline-block;">
											<option value="">Select</option>
							 			</select>
						 			</span>
						 		</div>
			           		</td>
			           	</tr>
			           	<tr>
							<td>
							    <div id="editRoleDistrictContainer" class="form-fields" style="width: 450px; display:none;">
							    	<span> <label for="editUserDistrict" class="field-label" 
							    		style="display: inline-block;text-align: right;width: 190px;">District:<span class="lbl-required">*</span></label>
										<select id="editUserDistrict" class="switchRole" style="clear: both;display: inline-block;">
											<option value="">Select</option>
							 			</select>
						 			</span>
						 		</div>
			           		</td>
			           	</tr>
			           	<tr>
							<td>
							    <div id="editRoleSchoolContainer" class="form-fields" style="width: 450px; display:none;">
							    	<span> <label for="editUserSchool" class="field-label" 
							    		style="display: inline-block;text-align: right;width: 190px;">School:<span class="lbl-required">*</span></label>
										<select id="editUserSchool" class="switchRole" style="clear: both;display: inline-block;">
											<option value="">Select</option>
							 			</select>
						 			</span>
						 		</div>
			           		</td>
			           	</tr>
			           	 <tr>
			                <td style="text-align:left;padding-left: 20px;">
			                	<button id="btn_editUserToGrid" disabled='disabled' class="btn_disabled" >Add</button>
			                </td>
			            </tr>
		            </security:authorize>
		            <tr>
		            	<td>
		            		<div id="editUserRolesGridContainer" class="kite-table">
			            		<table id="editUserRolesGrid" class="responsive"></table>
			            		<div id="editUserRolesGridPager" style="width: auto;"></div>
		            		</div>
		            	</td>
		            </tr>
		              <tr>
		                <td style="text-align:right;">
		                	<security:authorize access="hasRole('PERM_USER_MODIFY')">
		                		<button id="btn_editBottom_Admin" class="btn_blue" style="float:right;margin-right: 5%;margin-bottom: 0;">
		                			<fmt:message key="label.config.user.create.save"/>
		                		</button>
		                	</security:authorize>	
		                </td>
		            </tr>
			    </table>
			</div>
		</div>
		<div id="closeEditDialog"  style="display:none;">You have changes that have not yet been saved. Are you sure you want to exit?</div>
	</div>
</div>
<input type="hidden"  id="userId"/>
<!-- <a class="panel_btn" href="#" id="viewInvalidUserButton">Search</a> -->
<button class="btn_blue" id="viewInvalidUserButton">Search</button>

<script>
var edit_User_Select_Option_Loadonce = false;
var editUserSelectedForEdit = -999;
//Need to remove these.
var rolesAllowedForUserEdit = '';
var apsAllowedForUserEdit = '';
var statesAllowedForUserEdit = '';
var rolesAllowedForUserAdd = '';
var apsAllowedForUserAdd = '';
var userAllowedRoles='';
var statesAllowedForUserAdd = '';
var allowedRegionsForUserAdd = '';
var allowedDistrictsForUserAdd = '';
var allowedSchoolsForUserAdd = '';
var groupMap = {};
var groupRoleMap = {};
var groupsNameMap = {};
var selectedRoleOrgTypeId = 0;
	$(function() {
		initInactiveUserTab();
		
		
		initGroupsOrgTypeMap();
		
		$('#editUserOrganization, #editUserAssessmentProgram, #editUserGroup, #editUserRegion, #editUserDistrict, #editUserSchool').select2({
    		placeholder:'Select',
    		multiple: false,
    		allowClear : true
    	});
		
		$('#editUserOrganization').change(function(){
			$('#editRoleRegionContainer').hide();
			$('#editRoleDistrictContainer').hide();
			$('#editRoleSchoolContainer').hide();
			//getUserOrgAssessmentProgram($(this).val(), 'editUserAssessmentProgram');
			populateChildOrganizationsControls($(this).val(), 'RG', 30, 'editUserRegion', 'editUserDistrict');
			//getAssessmentProgramIdByOrganizationOnly -- ORG ONLY
			if($(this).val() !== '' && $(this).val() != null){
				var currentOrgType=$('#userCurrentOrganizationType').val();
				var orgAssessmentProgramURL='';
				if(currentOrgType==='CONS'){
					orgAssessmentProgramURL='getAssessmentProgramIdByOrganizationOnly.htm';
				}else{
					orgAssessmentProgramURL='getCurrentUserAndOrgAssessmentPrograms.htm';
				}
				$.ajax({
					url: orgAssessmentProgramURL,
					data: {
						organizationId: $(this).val()
					},
					dataType: 'json',
					type: "POST",
					async: false,
					success: function(assessmentProgramsByOrg){
						var clrOptionText='';
						var roleSelect = $('select#editUserAssessmentProgram')
						var newOptions='<option value="">Select</option>';
						if (assessmentProgramsByOrg !== undefined && assessmentProgramsByOrg !== null && assessmentProgramsByOrg.length > 0){
							$.each(assessmentProgramsByOrg, function(i, clrAssessmentProgram) {
								if(assessmentProgramsByOrg[i].id !=''){
									newOptions += '<option role="option" value="' + assessmentProgramsByOrg[i].id + '">' +
									assessmentProgramsByOrg[i].abbreviatedname + '</option>';
								}
							});
							roleSelect.html(newOptions);
							if (assessmentProgramsByOrg.length == 1) {
								roleSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
								roleSelect.trigger('change');
							}
						}
			       }
				});
			}else
				{
				$('#editUserAssessmentProgram').val("").trigger('change.select2');
				$('#editUserAssessmentProgram').find('option').remove();
				$('#editUserDistrict').find('option').remove();
				$('#editUserSchool').find('option').remove();
				}
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		$('#editUserAssessmentProgram').change(function(){
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		$('#editUserRegion').change(function(){
			populateChildOrganizationsControls($(this).val(), 'DT', 50, 'editUserDistrict', 'editUserDistrict');
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		$('#editUserDistrict').change(function(){
			selectedRoleOrgTypeId = groupMap[Number($('#editUserGroup').val())];
			if(selectedRoleOrgTypeId===7){
				populateChildOrganizationsControls($(this).val(), 'SCH', 70, 'editUserSchool', 'editUserDistrict');
			}
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		$('#editUserSchool').change(function(){
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		getStatesOrgsForUser('editUserOrganization', false);
		getAllowedRolesForUser('editUserGroup', 'e');
		$('#editUserGroup').change(function(){
			selectedRoleOrgTypeId = groupMap[$(this).val()];
			if(selectedRoleOrgTypeId === 2){
				$('#editRoleDistrictContainer').hide();
				$('#editRoleSchoolContainer').hide();
				$('#editRoleRegionContainer').hide();
				//To Set the Values
				$('#addUserRegion').html('<option value="0">Select</option>')
				$('#editUserRegion').val(0);
			}else if(selectedRoleOrgTypeId === 3){
				$('#editRoleDistrictContainer').hide();
				$('#editRoleSchoolContainer').hide();
				$('#editRoleRegionContainer').show();
			} else if(selectedRoleOrgTypeId === 5){
				$('#editRoleDistrictContainer').show();
				$('#editRoleSchoolContainer').hide();
				$('#editRoleRegionContainer').hide();
			} else if(selectedRoleOrgTypeId === 7){
				$('#editRoleDistrictContainer').show();
				$('#editRoleSchoolContainer').show();
				$('#editRoleRegionContainer').hide();
			} else {
				$('#editRoleDistrictContainer').hide();
				$('#editRoleSchoolContainer').hide();
				$('#editRoleRegionContainer').hide();
			}
			if($('#editRoleRegionContainer').find('option').length > 1){
				$('#editRoleRegionContainer').show();
			}
			$('#editUserDistrict').val('').trigger('change.select2');
			$('#editUserSchool').val('').trigger('change.select2');
			$('#editUserSchool').find('option').remove();
			$('#editUserRegion').val('').trigger('change.select2');
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion', selectedRoleOrgTypeId);
		});
		renderUserRolesGrid('editUserRolesGrid', 'e');
		
		initAddUserEvent('btn_editUserToGrid', 'editUserRolesGrid', 'e');
	});
</script>