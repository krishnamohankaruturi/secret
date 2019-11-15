<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userAdd.js'/>"> </script>
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
.gridActionImage{
	width:48px;
	height:48px;
	margin-right:10px;
}
</style>


<script type="text/javascript" src="<c:url value='/js/external/jquery.json-2.4.min.js'/>"> </script>
<div class="form">
	<div id="users-contain" style="margin-bottom: 5%;">
		<table id="containertable" style="width:98%;" role='presentation'>
            <tr>
                <td>
                	<div>
		                <div id="duplicateUserError" style="color:red;"> </div>
		                <div id="InvalidOrganizationRoleMessage" style="color:red;"></div>
		                <div id="idOrgUserError" style="color:red;"></div>  
		                <button id="btn_topSaveUser" class="btn_blue" style="float:right;" ><fmt:message key="label.config.user.create.save"/></button>              	
                		<h1 class="panel_head sub"><fmt:message key="label.config.user.create.userinformation"/></h1>
         				<div class="form-fields">
        					<label class="field-label" for="firstName"><fmt:message key="label.config.user.create.firstname"/><span class="lbl-required">*</span></label>
        					<input type="text" id="firstName" class="input-large" maxlength = "79"/>
        					<input type="hidden" id="userId" value=""/>
         				</div>
         				<div class="form-fields">
         					<label class="field-label" for="lastName"><fmt:message key="label.config.user.create.lastname"/><span class="lbl-required">*</span></label>
         					<input type="text" id="lastName" class="input-large" maxlength = "79"/>
         				</div>
         				<div class="form-fields">
        					<label class="field-label" for="emailAddress"><fmt:message key="label.config.user.create.email"/><span class="lbl-required">*</span></label>
        					<input type="text" id="emailAddress" class="input-large" maxlength = "254"/>
         				</div>
         				<div class="form-fields" >
       						<label class="field-label" for="educatorIdentifier" id="educatorIdentifierLabel"><fmt:message key="label.config.user.create.educatoridentifier"/></label>
       						<input type="text" id="educatorIdentifier" class="input-large" maxlength = "254"/>
        				</div>        				
         			</div>             
                </td>
           	</tr>
           	<tr><td><Span class="panel_head sub">Select Organization & Assign Roles</Span></td></tr>
           	<tr>
           		<td>
					<div class="form-fields" style="width: 450px;">
						<span><label for="addUserOrganization" class="field-label" 
								style="display: inline-block;text-align: right;width: 190px;">State:<span class="lbl-required">*</span></label>
							<select id="addUserOrganization" class="switchRole" style="clear: both;display: inline-block;">
								<option value="" >Select</option>
							</select>
						</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>	
					<div class="form-fields" style="width: 450px;">
						<span><label for="addUserAssessmentProgram" class="field-label"
							style="display: inline-block;text-align: right;width: 190px;"><fmt:message key="label.home.assessmentprogram.select"/>:<span class="lbl-required">*</span></label>
						<select id="addUserAssessmentProgram" class="switchRole" style="clear: both;display: inline-block;">
							<option value="" >Select</option>
						</select>
						</span>
					</div>
				</td>
			</tr>
			<tr>
				<td>
				    <div class="form-fields" style="width: 450px;">
				    	<span> <label for="addUserGroup" class="field-label" 
				    		style="display: inline-block;text-align: right;width: 190px;" ><fmt:message key="label.home.role.select"/>:<span class="lbl-required">*</span></label>
							<select id="addUserGroup" class="switchRole js-role-map" style="clear: both;display: inline-block;">
								<option value="">Select</option>
				 			</select>
			 			</span>
			 		</div>
           		</td>
           	</tr>
           	<tr>
				<td>
				    <div id="addRoleRegionContainer" class="form-fields" style="width: 450px; display:none;">
				    	<span> <label for="addUserRegion" class="field-label" 
				    		style="display: inline-block;text-align: right;width: 190px;" >Region:<span class="lbl-required">*</span></label>
							<select id="addUserRegion" class="switchRole" style="clear: both;display: inline-block;">
								<option value="">Select</option>
				 			</select>
			 			</span>
			 		</div>
           		</td>
           	</tr>
           	<tr>
				<td>
				    <div id="addRoleDistrictContainer" class="form-fields" style="width: 450px; display:none;">
				    	<span> <label for="addUserDistrict" class="field-label" 
				    		style="display: inline-block;text-align: right;width: 190px;">District:<span class="lbl-required">*</span></label>
							<select id="addUserDistrict" class="switchRole" style="clear: both;display: inline-block;">
								<option value="">Select</option>
				 			</select>
			 			</span>
			 		</div>
           		</td>
           	</tr>
           	<tr>
				<td>
				    <div id="addRoleSchoolContainer" class="form-fields" style="width: 450px; display:none;">
				    	<span> <label for="addUserSchool" class="field-label" 
				    		style="display: inline-block;text-align: right;width: 190px;">School:<span class="lbl-required">*</span></label>
							<select id="addUserSchool" class="switchRole" style="clear: both;display: inline-block;">
								<option value="">Select</option>
				 			</select>
			 			</span>
			 		</div>
           		</td>
           	</tr>
           	 <tr>
                <td style="text-align:left;padding-left: 20px;">
                	<button id="btn_addUserToGrid" disabled='disabled' title="ADD Button" class="btn_disabled" >Add</button>
                </td>
            </tr>
            <tr>
            	<td>
            		<div id="addUserRolesGridContainer" class="kite-table">
	            		<table class="responsive" id="addUserRolesGrid" role='presentation'></table>
	            		<div id="addUserRolesGridPager" style="width: auto;"></div>
            		</div>
            	</td>
            </tr>
            <tr>
                <td style="text-align:right; padding-top: 10px;">
                	<button id="btn_bottomSaveUser" class="btn_blue" ><fmt:message key="label.config.user.create.save"/></button>
                </td>
            </tr>
	    </table>
	</div>
</div>
<script>
	var add_User_Select_Option_Loadonce = false;
	var addUserSelectedForEdit = -999;
	var addUserRolesGridData = [];
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
		$('#addUserOrganization').on("change",function(){
			//getUserOrgAssessmentProgram($(this).val(), 'addUserAssessmentProgram');
			if($(this).val() !== ''&& $(this).val() != null){
				populateChildOrganizationsControls($(this).val(), 'RG', 30, 'addUserRegion', 'addUserDistrict');
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
					var roleSelect = $('select#addUserAssessmentProgram')
					var newOptions='<option value="">Select</option>';
					if (assessmentProgramsByOrg !== undefined && assessmentProgramsByOrg !== null && assessmentProgramsByOrg.length > 0){
						$.each(assessmentProgramsByOrg, function(i, clrAssessmentProgram) {
							if(assessmentProgramsByOrg[i].id !=''){
								newOptions += '<option role="option" value="' + assessmentProgramsByOrg[i].id + '">' +
								assessmentProgramsByOrg[i].abbreviatedname + '</option>';
							}
						});
						$("#addUserAssessmentProgram").html(newOptions);
						if (assessmentProgramsByOrg.length == 1) {
							roleSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
							roleSelect.trigger('change');
						}
					}
		       });
			}else
				{
				$('#addUserAssessmentProgram').val("").trigger('change.select2');
				$('#addUserAssessmentProgram').find('option').remove();
				$('#addUserDistrict').find('option').remove();
				$('#addUserSchool').find('option').remove();
				}
			
			enableDisableButton('btn_addUserToGrid', 'addUserOrganization', 'addUserAssessmentProgram', 'addUserGroup',
				'addUserDistrict', 'addUserSchool', 'addUserRegion');
		});
		$('#addUserAssessmentProgram').on("change",function(){
			enableDisableButton('btn_addUserToGrid', 'addUserOrganization', 'addUserAssessmentProgram', 'addUserGroup',
				'addUserDistrict', 'addUserSchool', 'addUserRegion');
		});
		$('#addUserRegion').on("change",function(){
			populateChildOrganizationsControls($(this).val(), 'DT', 50, 'addUserDistrict', 'addUserDistrict');
			enableDisableButton('btn_addUserToGrid', 'addUserOrganization', 'addUserAssessmentProgram', 'addUserGroup',
				'addUserDistrict', 'addUserSchool', 'addUserRegion');
		});
		$('#addUserDistrict').on("change",function(){
			selectedRoleOrgTypeId = groupMap[Number($('#addUserGroup').val())];
			if(selectedRoleOrgTypeId===7){
				populateChildOrganizationsControls($(this).val(), 'SCH', 70, 'addUserSchool', 'addUserDistrict');
			}
			enableDisableButton('btn_addUserToGrid', 'addUserOrganization', 'addUserAssessmentProgram', 'addUserGroup',
				'addUserDistrict', 'addUserSchool', 'addUserRegion');
		});
		$('#addUserSchool').on("change",function(){
			enableDisableButton('btn_addUserToGrid', 'addUserOrganization', 'addUserAssessmentProgram', 'addUserGroup',
				'addUserDistrict', 'addUserSchool', 'addUserRegion');
		});
		getStatesOrgsForUser('addUserOrganization', false);
		getAllowedRolesForUser('addUserGroup', '');
		
		$('#addUserGroup').on("change",function(){
			selectedRoleOrgTypeId = groupMap[$(this).val()];
			if(selectedRoleOrgTypeId === 2){
				$('#addRoleDistrictContainer').hide();
				$('#addRoleSchoolContainer').hide();
				$('#addRoleRegionContainer').hide();
				//To Set the Values
				$('#addUserRegion').html('<option value="0">Select</option>')
				$('#addUserRegion').val(0);
			}else if(selectedRoleOrgTypeId === 3){
				$('#addRoleDistrictContainer').hide();
				$('#addRoleSchoolContainer').hide();
				$('#addRoleRegionContainer').show();
			} else if(selectedRoleOrgTypeId === 5){
				$('#addRoleDistrictContainer').show();
				$('#addRoleSchoolContainer').hide();
				$('#addRoleRegionContainer').hide();
			} else if(selectedRoleOrgTypeId === 7){
				$('#addRoleDistrictContainer').show();
				$('#addRoleSchoolContainer').show();
				$('#addRoleRegionContainer').hide();
			} else {
				$('#addRoleDistrictContainer').hide();
				$('#addRoleSchoolContainer').hide();
				$('#addRoleRegionContainer').hide();
			}
			if($('#addRoleRegionContainer').find('option').length > 1){
				$('#addRoleRegionContainer').show();
			}
			$('#addUserDistrict').val('').trigger('change.select2');
			$('#addUserSchool').val('').trigger('change.select2');
			$('#addUserSchool').find('option').remove();
			$('#addUserRegion').val('').trigger('change.select2');
			enableDisableButton('btn_addUserToGrid', 'addUserOrganization', 'addUserAssessmentProgram', 'addUserGroup',
				'addUserDistrict', 'addUserSchool', 'addUserRegion',selectedRoleOrgTypeId);		 	
		 	
			if($('#addUserGroup option:selected').text() == 'Teacher'){
		 		$('span[id^="educatorIdentifierAsterisk"]').remove();
		 		$("#educatorIdentifierLabel").append('<span id="educatorIdentifierAsterisk" class="lbl-required">*</span>');
		 	}else{
		 			if($('#addUserGroup option:selected').text() != 'Teacher')
		 				$('span[id^="educatorIdentifierAsterisk"]').remove();
		 				
			var teacherId = groupsNameMap['Teacher'];
			var educatorIdentifierAsterisk = true;
			 var addGridData = $("#addUserRolesGrid").jqGrid("getGridParam", "data"),
				addGridRoleId = $.map(addGridData, function (item) { return item.role; }); 
				for (var i = 0; i < addGridRoleId.length; ++i) {
					if (addGridRoleId[i] == teacherId) {
						$('span[id^="educatorIdentifierAsterisk"]').remove();
						$("#educatorIdentifierLabel")
								.append(
										'<span id="educatorIdentifierAsterisk" class="lbl-required">*</span>');
						educatorIdentifierAsterisk = false;
					} else if (addGridRoleId[i] != teacherId
							&& educatorIdentifierAsterisk == true) {
						$('span[id^="educatorIdentifierAsterisk"]').remove();
					}
				}
		 		}
		});
		
		
		renderUserRolesGrid('addUserRolesGrid', '');
		
		initAddUserEvent('btn_addUserToGrid', 'addUserRolesGrid', '');
	});
</script>