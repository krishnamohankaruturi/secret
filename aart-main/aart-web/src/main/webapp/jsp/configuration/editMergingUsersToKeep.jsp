<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<%-- <script type="text/javascript" src="<c:url value='/js/userEditMerging.js'/>"> </script> --%>
<%-- <script type="text/javascript" src="<c:url value='/js/userMerge.js'/>"> </script> --%>

<style>
		
.header {
	    float: left;
	    margin-right: 100px;
	    width:100%;
}

.leftmargin {
	margin-left:0px;
}

.rightMargin {
	float:right;
	margin-right:0px;
}
 #btn_editMergingUserToKeep{
  background: #009933 
 }
#btn_editMergingUserToPurge{
  background: #E62B2B
 }
.merge-field-label{
font-size: 20px;
color:#0E76BC
}

 #editMergingUsersToKeep table td {
	vertical-align:bottom;  !important;
} 

</style>


<script type="text/javascript" src="<c:url value='/js/external/jquery.json-2.4.min.js'/>"> </script>

<div class="_bcg">
	<div id="editMergingUsersToKeep-contain" class="form wrap_bcg">
		<table id="editMergingUsersToKeepcontainertable" role='presentation' cellspacing="0" style="width:50%;">
			<tr>
				<td colspan="2" class="vertical_border">
					<div id="editRecordMergingUserToKeep_div"> <button id="btn_editRecordMergingUserToKeep" class="btn_blue" style="float:left;">Save</button></div>
				</td>
				<td colspan="2" id="moveRosterConfirmationBox" style="display:none">
					<div>
						<label class="merge-field-label" >Move Users's Roster, before purging?</label>
					</div>
					<div>
						<span>
							<button id="savePurgingUserRoster" class="btn_blue" style="float:left;">Yes</button>
						</span>
						<span>
							<button id="deletePurgingUserRoster" class="btn_blue" style="float:left;">No</button>
						</span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="vertical_border" colspan="2"/>
				<td id="purgeUserTd" style="display:none" colspan="2">
					<div id="purgeUserdiv">
						<button id="btn_purgeUser" class="btn_blue" style="float:left; margin-left:5%;">Purge</button>
					</div>
				</td>
			</tr>
            
            <tr>
            	<td class="vertical_border" colspan="2">
					<div id="mmUserARTSmessages" class="userMessages">
						<span class="error_message ui-state-error permissionDeniedMessage hidden" id="mmUserpermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="mmUserCreatefailMessage"><fmt:message key="error.config.user.create.fail"/></span>
				        <span class="info_message ui-state-highlight successMessage hidden" id="mmUsersuccessMessage" ><fmt:message key="label.config.user.modify.success"/></span>
				        <span class="info_message ui-state-highlight successMessage hidden" id="msameUsersuccessMessage" ><fmt:message key="sucess.myprofile.changeDefaultOrgAndRole"/></span>
				        <span class="info_message ui-state-highlight successMessage hidden" id="mmUserRosterMovesuccessMessage" ><fmt:message key="label.config.user.merge.moveroster.success"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="mmUserModifyfailMessage"><fmt:message key="error.config.user.modify.fail"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="mmUserrequiredMessage"><fmt:message key="error.config.required.fields"/></span>
				        <span class="error_message ui-state-error selectAllLabels hidden validate" id="mmduplicateorganization"><fmt:message key="error.config.user.create.duporg"/></span>
						<span class="error_message ui-state-error selectAllLabels hidden duplicate" id="mmmUserduplicateMessage" ><fmt:message key="error.config.user.create.dupuser"/></span>
						<span class="info_message ui-state-highlight emails_sent hidden" id="mmemails_sent_id" ><fmt:message key="label.config.user.view.email.success"/></span>
						<span class="error_message ui-state-error emails_not_sent hidden validate" id="mmemails_not_sent_id"><fmt:message key="error.config.user.view.email.error"/></span>
						<span class="error_message ui-state-error select_at_least_one hidden validate" id="mmselect_at_least_one_id"><fmt:message key="validation.config.user.create.selectuserforemail"/></span>
						<span class="error_message ui-state-error select_at_least_one hidden validate" id="mmselect_assessmentprogram"><fmt:message key="validation.config.user.create.assessmentprogramsIds"/></span>
						<span class="error_message ui-state-error invalidFormat hidden validate" id="mmUserInvalidFormatMessage"></span>
						<span class="error_message ui-state-error selectAllLabels hidden validate" id="mmUserEduOrgExistsMessage"><fmt:message key="error.config.user.create.identifierorgexists"/></span>
						<span class="error_message ui-state-error selectAllLabels hidden validate" id="mmInvalidOrgRoleMessage"></span>
						<span class="info_message ui-state-highlight successMessage hidden" id="mmInvalidRole" value=""></span>
						<span class="error_message ui-state-error selectAllLabels hidden duplicate" id="morgidOrgUserError"></span>
					</div>            	
            	</td>
            </tr>
             <tr>
                <td class="vertical_border" colspan="2">
                	<div>
		                <span class="error" id="mmduplicateUserError"></span>
		                <span class="error" id="mInvalidOrganizationRoleMessage"></span>		               
         			</div>             
                </td>
           	</tr>     
            <tr>
                <td style="text-align:right;" colspan ="2" class="vertical_border">
                		<div id="editMergingUserToKeep_div"><button id="btn_editMergingUserToKeep" class="btn_blue" style="float:left;">User to Keep</button></div>
                </td>
                 <td style="text-align:right;" colspan ="2">
                		<div id="editMergingUserToPurge_div"><button id="btn_editMergingUserToPurge" class="btn_blue" style="float:left; margin-left:5%;">User to Purge</button></div>
                </td>
            </tr>
            <tr>
            	<td colspan="2" class="vertical_border">
           			<div>
           				<span class="panel_head sub"><fmt:message key="label.config.user.create.userinformation"/></span>
           			</div>
           		</td>
           		<td colspan="2" style="padding-left: 25px;">
           			<div>
           				<span class="panel_head sub"><fmt:message key="label.config.user.create.userinformation"/></span>
           			</div>
           		</td>
            </tr>
           <tr>
           		<td colspan="1" id="userInfoUTKLefttd">
           			<div class="form-fields" >
        					<label class="field-label" for="mfirstNameUTK" ><fmt:message key="label.config.user.create.firstname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mfirstNameUTK" class="input-medium"/>
         			</div>
         			<div class="form-fields">
         			<input type="radio"  id="emailLeft"  name= "email" value='leftemail' class='myEmail'> 
        					<label class="field-label" for="memailAddressUTK"><fmt:message key="label.config.user.create.email"/><span class="lbl-required">*</span></label>
        					<input type="text" id="memailAddressUTK" class="input-medium"/>
         			</div>
           		</td>
           		<td colspan="1" class="vertical_border" style="width:25%;">
           		 		<div class="form-fields">
        					<label class="field-label" for="mlastNameUTK"><fmt:message key="label.config.user.create.lastname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mlastNameUTK" class="input-medium"/>
         				</div>
         				<div class="form-fields">
         				<input type="radio"  id="educatorLeft"  name= "educator" value='lefteducator' class='myEducator'>
        					<label class="field-label" for="meducatorIdentifierUTK"><fmt:message key="label.config.user.create.educatoridentifier"/></label>
        					<input type="text" id="meducatorIdentifierUTK" class="input-medium"/>
         				</div>
           		</td>
           		<td colspan="1" style="width: 35%;padding-left: 25px;">
           		 	<div class="form-fields">
        					<label class="field-label" for="mfirstNameUTP"><fmt:message key="label.config.user.create.firstname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mfirstNameUTP" class="input-medium"/>
         				</div>
         				<div class="form-fields">
         				<input type="radio"  id="emailRight"  name= "email" value='rightemail' class='myEmail' >
        					<label class="field-label" for="memailAddressUTP"><fmt:message key="label.config.user.create.email"/><span class="lbl-required">*</span></label>
        					<input type="text" id="memailAddressUTP" class="input-medium"/>
         			</div>
           		</td>
           		<td colspan="1">
           			<div class="form-fields">
        					<label class="field-label" for="mlastNameUTP"><fmt:message key="label.config.user.create.lastname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="mlastNameUTP" class="input-medium"/>
         			</div>
         			<div class="form-fields">
         			<input type="radio"  id="educatorRight"  name= "educator" value='righteducator' class='myEducator'> 
        					<label class="field-label" for="meducatorIdentifierUTP"><fmt:message key="label.config.user.create.educatoridentifier"/></label>
        					<input type="text" id="meducatorIdentifierUTP" class="input-medium"/>
         			</div>
           		</td>
           </tr>
           <security:authorize access="hasRole('PERM_USER_MODIFY')">
	            <tr><td colspan="4"><span class="panel_head sub">Select Organization & Assign Roles</span></td></tr>
	           	<tr>
	           		<td colspan="4">
						<div class="form-fields" style="width: 450px;">
							<span><label for="mergeUserOrganization" class="field-label" 
									style="display: inline-block;text-align: left;width: 190px;">State:<span class="lbl-required">*</span></label>
								<select id="mergeUserOrganization" class="switchRole" style="clear: both;display: inline-block;">
									<option value="" >Select</option>
								</select>
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4">	
						<div class="form-fields" style="width: 450px;">
							<span><label for="mergeUserAssessmentProgram" class="field-label"
								style="display: inline-block;text-align: left;width: 190px;"><fmt:message key="label.home.assessmentprogram.select"/>:<span class="lbl-required">*</span></label>
							<select id="mergeUserAssessmentProgram" class="switchRole" style="clear: both;display: inline-block;">
								<option value="" >Select</option>
							</select>
							</span>
						</div>
					</td>
				</tr>
				<tr>
					<td  colspan="4">
					    <div class="form-fields" style="width: 450px;">
					    	<span> <label for="mergeUserGroup" class="field-label" 
					    		style="display: inline-block;text-align: left;width: 190px;"><fmt:message key="label.home.role.select"/>:<span class="lbl-required">*</span></label>
								<select id="mergeUserGroup" class="switchRole js-role-map" style="clear: both;display: inline-block;">
									<option value="">Select</option>
					 			</select>
				 			</span>
				 		</div>
	           		</td>
	           	</tr>
	           	<tr>
					<td  colspan="4">
					    <div id="mergeRoleRegionContainer" class="form-fields" style="width: 400px; display:none;">
					    	<span> <label for="mergeUserRegion" class="field-label" 
					    		style="display: inline-block;text-align: left;width: 190px;">Region:<span class="lbl-required">*</span></label>
								<select id="mergeUserRegion" class="switchRole" style="clear: both;display: inline-block;">
									<option value="">Select</option>
					 			</select>
				 			</span>
				 		</div>
	           		</td>
	           	</tr>
	           	<tr>
					<td colspan="4">
					    <div id="mergeRoleDistrictContainer" class="form-fields" style="width: 400px; display:none;">
					    	<span> <label for="mergeUserDistrict" class="field-label" 
					    		style="display: inline-block;text-align: left;width: 190px;">District:<span class="lbl-required">*</span></label>
								<select id="mergeUserDistrict" class="switchRole" style="clear: both;display: inline-block;">
									<option value="">Select</option>
					 			</select>
				 			</span>
				 		</div>
	           		</td>
	           	</tr>
	           	<tr>
					<td colspan="4">
					    <div id="mergeRoleSchoolContainer" class="form-fields" style="width: 400px; display:none;">
					    	<span> <label for="mergeUserSchool" class="field-label" 
					    		style="display: inline-block;text-align: left;width: 190px;">School:<span class="lbl-required">*</span></label>
								<select id="mergeUserSchool" class="switchRole" style="clear: both;display: inline-block;">
									<option value="">Select</option>
					 			</select>
				 			</span>
				 		</div>
	           		</td>
	           	</tr>
	           	 <tr colspan="4">
	                <td style="text-align:left;padding-left: 20px;">
	                	<button id="btn_mergeUserToGrid" disabled='disabled' class="btn_disabled" >Add</button>
	                </td>
	            </tr>
            </security:authorize>
           
            <tr>
            	<td id="eorgTableUTK" colspan="2" class="vertical_border">
	            	<div id="mergeUserToKeepGridContainer1" class="kite-table">
					     <table id="mergeUserToKeepGrid1" class="responsive"></table>
					     <div id="mergeUserToKeepGrid1Pager" style="width: auto;"></div>
			       </div>
                </td>
                <td id="eorgTableUTP" colspan="2">
	                <div id="mergeUserToKeepGridContainer2" class="kite-table">
					     <table id="mergeUserToKeepGrid2" class="responsive"></table>
					     <div id="mergeUserToKeepGrid2Pager" style="width: auto;"></div>
			       </div>
                </td>
            </tr>  
        
    </table>
	    <input type="hidden" name="muserIdUTK" id="muserIdUTK" value="">
	    <input type="hidden" name="muserIdUTP" id="muserIdUTP" value="">
	</div>
	
</div>

<script>
	var merge_User_Select_Option_Loadonce = false;
	var editUserSelectedForEdit = -999;
	//Need to remove these.
	var rolesAllowedForUserMerge = '';
	
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
		$('#mergeUserOrganization').change(function(){
			$('#mergeRoleRegionContainer').hide();
			$('#mergeRoleDistrictContainer').hide();
			$('#mergeRoleSchoolContainer').hide();
			populateChildOrganizationsControls($(this).val(), 'RG', 30, 'mergeUserRegion', 'mergeUserDistrict');
			//getAssessmentProgramIdByOrganizationOnly -- ORG ONLY
			if($(this).val() !== ''){
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
						var roleSelect = $('select#mergeUserAssessmentProgram')
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
			}
			enableDisableButton('btn_mergeUserToGrid', 'mergeUserOrganization', 'mergeUserAssessmentProgram', 'mergeUserGroup',
				'mergeUserDistrict', 'mergeUserSchool', 'mergeUserRegion');
		});
		$('#mergeUserAssessmentProgram').change(function(){
			enableDisableButton('btn_mergeUserToGrid', 'mergeUserOrganization', 'mergeUserAssessmentProgram', 'mergeUserGroup',
				'mergeUserDistrict', 'mergeUserSchool', 'mergeUserRegion');
		});
		$('#mergeUserRegion').change(function(){
			populateChildOrganizationsControls($(this).val(), 'DT', 50, 'mergeUserDistrict', 'mergeUserDistrict');
			enableDisableButton('btn_mergeUserToGrid', 'mergeUserOrganization', 'mergeUserAssessmentProgram', 'mergeUserGroup',
				'mergeUserDistrict', 'mergeUserSchool', 'mergeUserRegion');
		});
		$('#mergeUserDistrict').change(function(){
			selectedRoleOrgTypeId = groupMap[Number($('#mergeUserGroup').val())];
			if(selectedRoleOrgTypeId===7){
				populateChildOrganizationsControls($(this).val(), 'SCH', 70, 'mergeUserSchool', 'mergeUserDistrict');
			}
			enableDisableButton('btn_mergeUserToGrid', 'mergeUserOrganization', 'mergeUserAssessmentProgram', 'mergeUserGroup',
				'mergeUserDistrict', 'mergeUserSchool', 'mergeUserRegion');
		});
		$('#mergeUserSchool').change(function(){
			enableDisableButton('btn_mergeUserToGrid', 'mergeUserOrganization', 'mergeUserAssessmentProgram', 'mergeUserGroup',
				'mergeUserDistrict', 'mergeUserSchool', 'mergeUserRegion');
		});
		getStatesOrgsForUser('mergeUserOrganization', false);
		getAllowedRolesForUser('mergeUserGroup', 'm');
		$('#mergeUserGroup').change(function(){
			selectedRoleOrgTypeId = groupMap[$(this).val()];
			if(selectedRoleOrgTypeId === 2){
				$('#mergeRoleDistrictContainer').hide();
				$('#mergeRoleSchoolContainer').hide();
				$('#mergeRoleRegionContainer').hide();
				//To Set the Values
				$('#addUserRegion').html('<option value="0">Select</option>')
				$('#mergeUserRegion').val(0);
			}else if(selectedRoleOrgTypeId === 3){
				$('#mergeRoleDistrictContainer').hide();
				$('#mergeRoleSchoolContainer').hide();
				$('#mergeRoleRegionContainer').show();
			} else if(selectedRoleOrgTypeId === 5){
				$('#mergeRoleDistrictContainer').show();
				$('#mergeRoleSchoolContainer').hide();
				$('#mergeRoleRegionContainer').hide();
			} else if(selectedRoleOrgTypeId === 7){
				$('#mergeRoleDistrictContainer').show();
				$('#mergeRoleSchoolContainer').show();
				$('#mergeRoleRegionContainer').hide();
			} else {
				$('#mergeRoleDistrictContainer').hide();
				$('#mergeRoleSchoolContainer').hide();
				$('#mergeRoleRegionContainer').hide();
			}
			if($('#mergeRoleRegionContainer').find('option').length > 1){
				$('#mergeRoleRegionContainer').show();
			}
			$('#mergeUserDistrict').val('');
			$('#mergeUserSchool').val('');
			$('#mergeUserRegion').val('');
			enableDisableButton('btn_mergeUserToGrid', 'mergeUserOrganization', 'mergeUserAssessmentProgram', 'mergeUserGroup',
				'mergeUserDistrict', 'mergeUserSchool', 'mergeUserRegion', selectedRoleOrgTypeId);
		});
		renderUserRolesGrid('mergeUserToKeepGrid1', 'm');
		
		initAddUserEvent('btn_mergeUserToGrid', 'mergeUserToKeepGrid1', 'm');
	});
</script>