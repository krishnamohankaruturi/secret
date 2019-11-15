<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userEdit.js'/>"> </script>
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
<script type="text/javascript" src="<c:url value='/js/external/jquery.json-2.4.min.js'/>"> </script>
<input id="doesUserHaveHighRolesEditUser" type="hidden" value="${doesUserHaveHighRoles}" />
<div class="_bcg">
	<div id="editusers-contain" class="form wrap_bcg" style="margin-bottom: 5%;">
		<table id="editcontainertable" style="width:98%;">
           <%--  <tr>
                <td style="text-align:right;">
                	<security:authorize access="hasRole('PERM_USER_MODIFY')">
                		<button id="btn_editTop" class="btn_blue" style="float:right;margin-right: 5%;margin-bottom: 0;">
                			<fmt:message key="label.config.user.create.save"/>
                		</button>
                	</security:authorize>	
                	<security:authorize access="hasRole('PERM_USER_CLAIM')">
                		<button id="btn_editTop_Find" class="btn_blue" style="float:right;margin-right: 5%;margin-bottom: 0;">
                			<fmt:message key="label.config.user.create.save"/>
                		</button>
                	</security:authorize>
                </td>
            </tr> --%>
            <tr>
            	<td>
					<div id="eUserARTSmessages" class="userMessages">
						<span class="error_message ui-state-error permissionDeniedMessage hidden" id="eUserpermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
				        <span class="info_message ui-state-highlight successMessage hidden" id="eUserCreatesuccessMessage" ><fmt:message key="label.config.user.create.success"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="eUserCreatefailMessage"><fmt:message key="error.config.user.create.fail"/></span>
				        <span class="info_message ui-state-highlight successMessage hidden" id="eUserModifysuccessMessage" ><fmt:message key="label.config.user.modify.success"/></span>
				        <span class="info_message ui-state-highlight successMessage hidden" id="esameUsersuccessMessage" ><fmt:message key="sucess.myprofile.changeDefaultOrgAndRole"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="eUserModifyfailMessage"><fmt:message key="error.config.user.modify.fail"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="eUserrequiredMessage"><fmt:message key="error.config.required.fields"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="eduplicateorganization"><fmt:message key="error.config.user.create.duporg"/></span>
						<span class="error_message ui-state-error selectAllLabels hidden duplicate" id="eUserduplicateMessage" ><fmt:message key="error.config.user.create.dupuser"/></span>
						<span class="info_message ui-state-highlight emails_sent hidden" id="eemails_sent_id" ><fmt:message key="label.config.user.view.email.success"/></span>
						<span class="error_message ui-state-error emails_not_sent hidden validate" id="eemails_not_sent_id"><fmt:message key="error.config.user.view.email.error"/></span>
						<span class="error_message ui-state-error select_at_least_one hidden validate" id="eselect_at_least_one_id"><fmt:message key="validation.config.user.create.selectuserforemail"/></span>
						<span class="error_message ui-state-error invalidFormat hidden validate" id="eUserInvalidFormatMessage"></span>
						<span class="error_message ui-state-error selectAllLabels hidden validate" id="eUserEduOrgExistsMessage"><fmt:message key="error.config.user.create.identifierorgexists"/></span>
						<span class="error_message ui-state-error selectAllLabels hidden validate" id="eInvalidOrgRoleMessage"></span>
						
					</div>            	
            	</td>
            </tr>
            <tr>
                <td>
                	<div>
		                <div style="color:red;" id="eduplicateUserError"></div>
		                <div style="color:red;" id="eidOrgUserError"></div>
		                <div style="color:red;" id="eInvalidOrganizationRoleMessage"></div>
						<security:authorize access="hasRole('PERM_USER_MODIFY')">
							<button id="btn_editTop" class="btn_blue" style="float: right; margin-right: 5%;">
								<fmt:message key="label.config.user.create.save" />
							</button>
						</security:authorize>
						<security:authorize access="hasRole('PERM_USER_CLAIM')">
							<button id="btn_editTop_Find" class="btn_blue" style="float: right; margin-right: 5%;;">
								<fmt:message key="label.config.user.create.save" />
							</button>
						</security:authorize>
						<h1 class="panel_head sub"><fmt:message key="label.config.user.create.userinformation"/></h1>
         				<div class="form-fields">
        					<label class="field-label" for="efirstName"><fmt:message key="label.config.user.create.firstname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="efirstName" class="input-large" maxlength = "79"/>
         				</div>
         				<div class="form-fields">
         					<label class="field-label" for="elastName"><fmt:message key="label.config.user.create.lastname"/><span class="lbl-required">*</span></label>
         					<input type="text" id="elastName" class="input-large" maxlength = "79"/>
         				</div>
         				<div class="form-fields">
        					<label class="field-label" for="eemailAddress"><fmt:message key="label.config.user.create.email"/><span class="lbl-required">*</span></label>
        					<input type="text" id="eemailAddress" class="input-large" maxlength = "254"/>
         				</div>
         				<div class="form-fields">
       						<label class="field-label" for="eeducatorIdentifier" id="eeducatorIdentifierLabel"><fmt:message key="label.config.user.create.educatoridentifier"/></label>
       						<input type="text" id="eeducatorIdentifier" class="input-large" maxlength = "254"/>
        				</div>
         			</div>             
                </td>
           	</tr>           	
           	<security:authorize access="hasRole('PERM_USER_MODIFY')">
	            <tr><td><span class="panel_head sub">Select Organization & Assign Roles</span></td></tr>
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
                		<button id="btn_editBottom" class="btn_blue" style="float:right;margin-right: 5%;margin-bottom: 0;">
                			<fmt:message key="label.config.user.create.save"/>
                		</button>
                	</security:authorize>
                	<security:authorize access="hasRole('PERM_USER_CLAIM')">
                		<button id="btn_editBottom_Find" class="btn_blue" style="float:right;margin-right: 5%;margin-bottom: 0;">
                			<fmt:message key="label.config.user.create.save"/>
                		</button>
                	</security:authorize>	
                </td>
            </tr>
	    </table>
	</div>
</div>
<div id="closeEditDialog"  style="display:none;">You have changes that have not yet been saved. Are you sure you want to exit?</div>
<div id="edutifierfierNotFoundTeacherPopup" class="hidden">
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
	$(function(){
		initGroupsOrgTypeMap();
		$('#editUserOrganization').on("change",function(){
			$('#editRoleRegionContainer').hide();
			//getUserOrgAssessmentProgram($(this).val(), 'editUserAssessmentProgram');
			populateChildOrganizationsControls($(this).val(), 'RG', 30, 'editUserRegion', 'editUserDistrict');
			//getAssessmentProgramIdByOrganizationOnly -- ORG ONLY
			if($(this).val() !== ''&& $(this).val() != null){
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
					async: false
				}).done(function(assessmentProgramsByOrg){
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
		$('#editUserAssessmentProgram').on("change",function(){
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		$('#editUserRegion').on("change",function(){
			populateChildOrganizationsControls($(this).val(), 'DT', 50, 'editUserDistrict', 'editUserDistrict');
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		$('#editUserDistrict').on("change",function(){
			selectedRoleOrgTypeId = groupMap[Number($('#editUserGroup').val())];
			if(selectedRoleOrgTypeId===7){
				populateChildOrganizationsControls($(this).val(), 'SCH', 70, 'editUserSchool', 'editUserDistrict');
			}
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		$('#editUserSchool').on("change",function(){
			enableDisableButton('btn_editUserToGrid', 'editUserOrganization', 'editUserAssessmentProgram', 'editUserGroup',
				'editUserDistrict', 'editUserSchool', 'editUserRegion');
		});
		getStatesOrgsForUser('editUserOrganization', false);
		getAllowedRolesForUser('editUserGroup', 'e');
		$('#editUserGroup').on("change",function(){
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
			
			if($('#editUserGroup option:selected').text() == 'Teacher'){
		 		$('span[id^="eeducatorIdentifierAsterisk"]').remove();
		 		$("#eeducatorIdentifierLabel").append('<span id="eeducatorIdentifierAsterisk" class="lbl-required">*</span>');
		 	}else{
		 			if($('#editUserGroup option:selected').text() != 'Teacher')
		 				$('span[id^="eeducatorIdentifierAsterisk"]').remove();
		 				
			var teacherId = groupsNameMap['Teacher'];
			var eeducatorIdentifierAsterisk = true;
			 var editGridData = $("#editUserRolesGrid").jqGrid("getGridParam", "data"),
				editGridRoleId = $.map(editGridData, function (item) { return item.role; }); 
				for (var i = 0; i < editGridRoleId.length; ++i) {
					if (editGridRoleId[i] == teacherId) {
						$('span[id^="eeducatorIdentifierAsterisk"]').remove();
						$("#eeducatorIdentifierLabel")
								.append(
										'<span id="eeducatorIdentifierAsterisk" class="lbl-required">*</span>');
						eeducatorIdentifierAsterisk = false;
					} else if (editGridRoleId[i] != teacherId
							&& eeducatorIdentifierAsterisk == true) {
						$('span[id^="eeducatorIdentifierAsterisk"]').remove();
					}
				}
		 		}
		});
		renderUserRolesGrid('editUserRolesGrid', 'e');
		
		initAddUserEvent('btn_editUserToGrid', 'editUserRolesGrid', 'e');
	});
</script>