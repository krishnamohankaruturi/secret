var inValidTeacherRole = "";
var editUserRolesGridData = [];

/**
 * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
 * Common code to be used for add and edit user screens.
 */
// Get assessment programs based on the selected organizationId(state)
var currentMode = '',allowedStates=[],allowedAps=[],allowedRoles=[],allowedRegions=[],allowedDistricts=[],allowedSchools=[];
var defaultValues = "0:";
$.jgrid.extend({
    destroyFilterToolbar: function () {
        "use strict";
        return this.each(function () {
            if (!this.ftoolbar) {
                return;
            }
            this.triggerToolbar = null;
            this.clearToolbar = null;
            this.toggleToolbar = null;
            this.ftoolbar = false;
            $(this.grid.hDiv).find("table thead tr.ui-search-toolbar").remove();
        });
    }
});



function populateChildOrganizationsControls(organizationId, orgType, orgTypeLevel, childElementId, districtElementId){
	organizationId = organizationId == null?'0':organizationId;
	if(organizationId != '' && orgType != '' && orgTypeLevel !=''){
		$.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
			dataType: 'json',
			data: {
				orgId : organizationId,
	        	orgType:orgType,
	        	orgLevel: orgTypeLevel	
			},
			async:false,
			type: "GET"
		}).done(function(childOrgs) {
			var childOrgSelect= $('#'+childElementId);
			
			childOrgSelect.html('');
			if (childOrgs !== undefined && childOrgs !== null && childOrgs.length > 0) {
				childOrgSelect.append($('<option></option>').val(0).html('Select'));
				$.each(childOrgs, function(i, districtOrg) {
					optionText = childOrgs[i].organizationName;
					childOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
				});
				if (childOrgs.length == 1) {
					childOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					childOrgSelect.trigger('change');
				}
				if(orgType === 'RG' && childOrgs.length > 0){
					childOrgSelect.parent().parent().show();
				}
			} else {
				if(orgType === 'RG'){
					populateChildOrganizationsControls(organizationId, 'DT', 50, districtElementId);
				}
			}
			//debugger;
			if(orgType === 'RG' && childOrgs.length <= 1){
				if(childElementId.indexOf('_')!=-1){
					childOrgSelect.parent().parent().hide();
				}
            }
		});
	}
}
function populateChildOrganizationsControlsForGrid(organizationId, orgType, orgTypeLevel, childElementId, districtElementId){
	organizationId = organizationId == null?'0':organizationId;
	if(organizationId != '' && orgType != '' && orgTypeLevel !=''){
		$.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
			dataType: 'json',
			data: {
				orgId : organizationId,
	        	orgType:orgType,
	        	orgLevel: orgTypeLevel	
			},
			async:false,
			type: "GET"
		}).done(function(childOrgs) {
			var childOrgSelect='';
			if(currentMode===''){
				childOrgSelect= $('#addUserRolesGrid #'+childElementId);
			}else if(currentMode==='e'){
				childOrgSelect = $('#editUserRolesGrid #'+childElementId);
			}else{
				childOrgSelect = $('#mergeUserToKeepGrid1 #'+childElementId);
			}
			
			childOrgSelect.html('');
			if (childOrgs !== undefined && childOrgs !== null && childOrgs.length > 0) {
				childOrgSelect.append($('<option></option>').val(0).html('Select'));
				$.each(childOrgs, function(i, districtOrg) {
					optionText = childOrgs[i].organizationName;
					childOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
				});
				if (childOrgs.length == 1) {
					childOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					childOrgSelect.trigger('change');
				}
			} else {
			
				childOrgSelect.html('');
				if (childOrgs !== undefined && childOrgs !== null && childOrgs.length > 0) {
					childOrgSelect.append($('<option></option>').val(0).html('Select'));
					$.each(childOrgs, function(i, districtOrg) {
						optionText = childOrgs[i].organizationName;
						childOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
					});
					if (childOrgs.length == 1) {
						childOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						childOrgSelect.trigger('change');
					}
				} else {
					if(orgType === 'RG'){
						childOrgSelect.html($('<option></option>').val(0).html(''));
						populateChildOrganizationsControlsForGrid(organizationId, 'DT', 50, districtElementId);
					}
				}
			}
		});
	}
}

function getStatesOrgsForUser(elementId, singleOptionSelectAllowed){
	var stateOrgSelect = $('#'+elementId);
	$.ajax({
        url: 'getStatesOrgsForUser.htm',
        data: { },
        dataType: 'json',
        type: "GET" 
	}).done(function(states) {
    	stateOrgSelect.empty();
    	if (states !== undefined && states !== null && states.length > 0) {
			$.each(states, function(i, stateOrg) {
				var clrStateOptionText = states[i].organizationName;
				stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(clrStateOptionText));
			});
				if (states.length == 1) {
					stateOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$('#'+elementId).trigger('change');
				}else{
					stateOrgSelect.val("").trigger('change.select2')
				}
		} 
      });
}
function getUserOrgAssessmentProgram(organizationId, dependentElementId){
	if(organizationId !== undefined && organizationId !==''){
		$.ajax({
			url: 'templates/getUserOrgAssessmentProgram.htm',
			data: {
				organizationId : organizationId
			},
			dataType: 'json',
			type: 'POST'
		}).done(function(data) {
			if (data !== undefined && data !== null && data.length > 0) {
				$select = $('#'+dependentElementId);
				$select.find('option').remove();
				$select.append($('<option>', {value: '', text: 'Select'}).attr('selected', 'selected'));
				populateDefaultList($select, data, 'abbreviatedname', 'id', true);
			} else {
				
			}
		});
	}
}


function initializeAllowedRolesForUser(screen){
	if(screen === ''){
		rolesAllowedForUserAdd = '';
		userAllowedRoles='';
		allowedRoles.sort(function(a, b) {
		    var at = a.value, bt = b.value;
		    return (at > bt)?1:((at < bt)?-1:0);
		});
		
		$.each(allowedRoles, function( index, object ) {
			userAllowedRoles = userAllowedRoles + Number(object.key) + ":" + object.value +  ";";
			});
		if(userAllowedRoles.length > 0){
			userAllowedRoles = userAllowedRoles.substring(0, userAllowedRoles.length-1);
		}
		// Initialize the values for Roles Dropdown
		$("#addUserGroup option").each(function()
		{
			if($(this).val()!=''){
				rolesAllowedForUserAdd = rolesAllowedForUserAdd + Number($(this).val()) + ":" + $(this).text() +  ";";
			}
		});
		if(rolesAllowedForUserAdd.length > 0){
			rolesAllowedForUserAdd = rolesAllowedForUserAdd.substring(0, rolesAllowedForUserAdd.length-1);
		}
	} else if(screen === 'e'){
		rolesAllowedForUserEdit = '';
		userAllowedRoles='';
		$.each(allowedRoles, function( index, object ) {
			userAllowedRoles = userAllowedRoles + Number(object.key) + ":" + object.value +  ";";
			});
		if(userAllowedRoles.length > 0){
			userAllowedRoles = userAllowedRoles.substring(0, userAllowedRoles.length-1);
		}
		// Initialize the values for Roles Dropdown
		$("#editUserGroup option").each(function()
		{
			if($(this).val()!=''){
				rolesAllowedForUserEdit = rolesAllowedForUserEdit + Number($(this).val()) + ":" + $(this).text() +  ";";
			}
		});
		if(rolesAllowedForUserEdit.length > 0){
			rolesAllowedForUserEdit = rolesAllowedForUserEdit.substring(0, rolesAllowedForUserEdit.length-1);
		}
	} else if(screen === 'm'){
		rolesAllowedForUserMerge = '';
		// Initialize the values for Roles Dropdown
		$("#mergeUserGroup option").each(function()
		{
			//if(!$(this).prop('disabled')){
				if($(this).val()!=''){
					rolesAllowedForUserMerge = rolesAllowedForUserMerge + Number($(this).val()) + ":" + $(this).text() +  ";";
				}
			//}
		});
		if(rolesAllowedForUserMerge.length > 0){
			rolesAllowedForUserMerge = rolesAllowedForUserMerge.substring(0, rolesAllowedForUserMerge.length-1);
		}
	}
}

function initGroupsOrgTypeMap(){
	$.ajax({
		url: 'getGroupsOrgTypeMap.htm',
		type: 'GET',
		dataType: 'json',
		async : false 
	}).done(function(data) {
			if(data != undefined && data != null){
				groupMap = data.groupsOrgMap;
				groupRoleMap=data.groupsRoleTypeMap;
				groupsNameMap=data.groupsNameMap;
				groupsHierarchyMap=data.groupsHierarchyMap;
				groupsCodeMap=data.groupsCodeMap;
			}
		});
}
function getAllowedRolesForUser(dropDownElementId, screen){
	$.ajax({
		url: 'getAllRoles.htm',
		data: {
		},
		type: 'POST',
		dataType: 'json'
	}).done(function(data) {
		// Clear all options in Roles
		var roleSelect = $('#'+dropDownElementId);
		roleSelect.find('option').filter(function() {
	        return $(this).val() > 0
	    }).remove().end();
		
		var roles = [];
		for (var i = 0, length = data['allGroups'].length; i < length; i++) {
			optionText = data['allGroups'][i].groupName;
			var organizationTypeId = data['allGroups'][i].organizationTypeId;
			var groupId = data['allGroups'][i].groupId;
			if(data['allGroups'][i].allowedToAssign){
				
				if(data['allGroups'][i].isdepreciated){
					roleSelect.append($('<option class="hideOptionAddUserRole" style="display:none"></option>').data('organizationTypeId', organizationTypeId).val(groupId).html(optionText));
				}
				else{
					roleSelect.append($('<option></option>').data('organizationTypeId', organizationTypeId).val(groupId).html(optionText));
				}
			} else {
				
				if(data['allGroups'][i].isdepreciated){
					roleSelect.append($('<option class="hideOptionAddUserRole" style="display:none"></option>').data('organizationTypeId', organizationTypeId).val(groupId).html(optionText));
				}
				else{
					roleSelect.append($('<option class="hideOptionAddUserRole" style="display:none"></option>').data('organizationTypeId', organizationTypeId).val(groupId).html(optionText));
				}
			}
			
		}
		$(".hideOptionAddUserRole").wrap("<span>");
		initializeAllowedRolesForUser(screen);
	});
}



var stateDataEvents=[{ type: 'change',fn: function(e) {var row = $(e.target).closest('tr.jqgrow');executeEditFunction( row.attr('id'),'fromStateChange')}}];
var roleDataEvents=[{ type: 'change',fn: function(e) {var row = $(e.target).closest('tr.jqgrow');loadChildOrgsBasedOnRole( row.attr('id'),$(this).val())}}];
var districtDataEvents=[{ type: 'change',fn: function(e) {var row = $(e.target).closest('tr.jqgrow'); loadChildOrgs(row.attr('id'),'SCH',70,$(this).val())}}];
var regionDataEvents=[{ type: 'change',fn: function(e) {var row = $(e.target).closest('tr.jqgrow'); loadChildOrgs(row.attr('id'),'DT',50,$(this).val())}}];
var schoolDataEvents=[{ type: 'change',fn: function(e) {var row = $(e.target).closest('tr.jqgrow'); loadSchoolOptions(row.attr('id'),$(this).val(),$(this).find('option:selected').text())}}];
//This is just to update the Edit Values For School.
var loadSchoolOptions=function(rowid,schoolId,schoolName){
	var rolesGridId;
	if(currentMode === ''){
		rolesGridId = 'addUserRolesGrid';
	} else if(currentMode==='e'){
		rolesGridId = 'editUserRolesGrid';
	} else if(currentMode==='m'){
		rolesGridId = 'mergeUserToKeepGrid1';
	}
	userGridData = $("#"+rolesGridId).jqGrid('getGridParam','data');
	var rowData={};
	$.each(userGridData,function(i,obj){
		if(obj.id==rowid){
			rowData= userGridData[i];
		}
	});
	rowData.school=schoolId;
	
	allowedSchools.push({key: schoolId, value: schoolName});
	allowedSchoolsForUserAdd='';
	$(allowedSchools).each(function(i, obj){
		allowedSchoolsForUserAdd = allowedSchoolsForUserAdd + obj.key +":"+obj.value+";";
	});
	if(allowedSchoolsForUserAdd.length > 0){
		allowedSchoolsForUserAdd = allowedSchoolsForUserAdd.substring(0, allowedSchoolsForUserAdd.length-1);
	}
	$('#'+rolesGridId).setColProp('school', {edittype:"select", formatter:"select", editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents}
	, sorttype: function (cell, obj) {
		return (_.findWhere(allowedSchools,{key:cell})) ? _.findWhere(allowedSchools,{key:cell}).value : '';
    }});
}
var loadChildOrgsBasedOnRole=function(rowid,id){
	
	var orgTypeId = groupMap[id];
	var rolesGridId;
	if(currentMode === ''){
		rolesGridId = 'addUserRolesGrid';
	} else if(currentMode === 'e'){
		rolesGridId = 'editUserRolesGrid';
	} else if(currentMode === 'm'){
		rolesGridId = 'mergeUserToKeepGrid1';
	}
	var currentAddOrEditGrid=$("#"+rolesGridId);
	userGridData = $("#"+rolesGridId).jqGrid('getGridParam','data');
	var rowData={};
	$.each(userGridData,function(i,obj){
		if(obj.id==rowid){
			rowData= userGridData[i];
		}
	}); 
	var teacherId = groupsNameMap['Teacher'];
	var educatorIdentifierAsterisk = true;
	var eeducatorIdentifierAsterisk = true;
	if (currentMode === '') {
		var addgridRoleId=$("#"+rolesGridId).jqGrid('getCol','role');
		for (var i = 0; i < addgridRoleId.length; ++i) {
			if (addgridRoleId[i] == teacherId || id == teacherId) {
				$('span[id^="educatorIdentifierAsterisk"]').remove();
				$("#educatorIdentifierLabel")
						.append(
								'<span id="educatorIdentifierAsterisk" class="lbl-required">*</span>');
				educatorIdentifierAsterisk = false;
			} else if (addgridRoleId[i] != teacherId
					&& educatorIdentifierAsterisk == true || id != teacherId
					&& educatorIdentifierAsterisk == true) {
				$('span[id^="educatorIdentifierAsterisk"]').remove();
			}
		}
	} else if (currentMode === 'e') {
		var editgridRoleId = $("#"+rolesGridId).jqGrid('getCol','role');
		for (var i = 0; i < editgridRoleId.length; ++i) {
			if (editgridRoleId[i] == teacherId || id == teacherId) {
				$('span[id^="eeducatorIdentifierAsterisk"]').remove();
				$("#eeducatorIdentifierLabel")
						.append(
								'<span id="eeducatorIdentifierAsterisk" class="lbl-required">*</span>');
				eeducatorIdentifierAsterisk = false;
			} else if (editgridRoleId[i] != teacherId
					&& eeducatorIdentifierAsterisk == true || id != teacherId
					&& eeducatorIdentifierAsterisk == true) {
				$('span[id^="eeducatorIdentifierAsterisk"]').remove();
			}
		}
	}
	rowData.role=id;
	//orgTypeId= 5 (Distrcit) 7(School) 3 (Region) 2 (State)
	if(orgTypeId===2){
		currentAddOrEditGrid.find("select#"+rowid+"_district").prop('disabled','disabled').css('background-color','#e5e5e5');
		currentAddOrEditGrid.find("select#"+rowid+"_district").val('').html("<option value='0'></option>");
		currentAddOrEditGrid.find("select#"+rowid+"_region").prop('disabled','disabled').css('background-color','#e5e5e5');
		currentAddOrEditGrid.find("select#"+rowid+"_region").val('').html("<option value='0'></option>");
		currentAddOrEditGrid.find("select#"+rowid+"_school").prop('disabled','disabled').css('background-color','#e5e5e5');
		currentAddOrEditGrid.find("select#"+rowid+"_school").val('').html("<option value='0'></option>");
	}
	
	if(orgTypeId===5){
		currentAddOrEditGrid.find("select#"+rowid+"_district").removeAttr('disabled').css('background-color','');
		currentAddOrEditGrid.find("select#"+rowid+"_school").val('').html("<option value='0'></option>");
		currentAddOrEditGrid.find("select#"+rowid+"_school").prop('disabled','disabled').css('background-color','#e5e5e5');
		//currentAddOrEditGrid.find("select#"+rowid+"_state").val()
		loadChildOrgs(rowid,'DT',50,rowData.state);
		currentAddOrEditGrid.find("select#"+rowid+"_district").val(rowData.district);
		//Load the Districts
	}
	
	if(orgTypeId===7){
		currentAddOrEditGrid.find("select#"+rowid+"_district").removeAttr('disabled').css('background-color','');
		currentAddOrEditGrid.find("select#"+rowid+"_school").removeAttr('disabled').css('background-color','');
		//Load the Schools
		//currentAddOrEditGrid.find("select#"+rowid+"_district").val()
		loadChildOrgs(rowid,'SCH',70,rowData.district);
		currentAddOrEditGrid.find("select#"+rowid+"_school").val(rowData.school);
	}
	//Just for Loading the Regions.
	if(orgTypeId>=5){
		//Loaded the Regions and if they have any.
		populateChildOrganizationsControlsForGrid(rowData.state, 'RG', 30, rowid+'_region', rowid+'_'+'district');
		//populateChildOrganizationsControlsForGrid(id,type,typeLevel,rowid+'_'+childName,rowid+'_'+'district');
	}
	
}
var loadChildOrgs=function(rowid,type,typeLevel,id){
	//organizationId, orgType, orgTypeLevel, childElementId, districtElementId
	
	var childName='school';
	if(type==='DT'){
		childName='district';
	}
	var rolesGridId;
	if(currentMode === ''){
		rolesGridId = 'addUserRolesGrid';
	} else if(currentMode === 'e'){
		rolesGridId = 'editUserRolesGrid';
	} else if(currentMode === 'm'){
		rolesGridId = 'mergeUserToKeepGrid1';
	}
	userGridData = $("#"+rolesGridId).jqGrid('getGridParam','data');
	var rowData={};
	$.each(userGridData,function(i,obj){
		if(obj.id==rowid){
			rowData= userGridData[i];
		}
	});
	if(type==='DT'){
		rowData.region=id;
	}else{
		rowData.district=id;
	}
	var orgTypeId = groupMap[rowData.role];
	//Do not load Districts if the user role is State Level
	//Do not load School if the user role is Dsitrict Level
	var loadChilds=false;
	if(childName=='school' && orgTypeId==7){
		loadChilds=true;
	}
	if(childName=='district' && orgTypeId>=5){
		loadChilds=true;
	}
	if(loadChilds){
		populateChildOrganizationsControlsForGrid(id,type,typeLevel,rowid+'_'+childName,rowid+'_'+'district');
	}
	
	
}
var executeSaveFunction = function() {
	var teacherId = groupsNameMap['Teacher'];
	var educatorIdentifierAsterisk = true;
	var eeducatorIdentifierAsterisk = true;
	if (currentMode === '') {
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
	} else if (currentMode === 'e') {
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
}
var executeRestoreFunction=function(){
	var teacherId = groupsNameMap['Teacher'];
	var educatorIdentifierAsterisk = true;
	var eeducatorIdentifierAsterisk = true;
	if (currentMode === '') {
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
	} else if (currentMode === 'e') {
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
}
var executeEditFunction=function(rowid,fromChange){
	var rolesGridId='';
	if(currentMode === ''){
		rolesGridId = 'addUserRolesGrid';
	} else if(currentMode === 'e'){
		rolesGridId = 'editUserRolesGrid';
	} else if(currentMode === 'm'){
		rolesGridId = 'mergeUserToKeepGrid1';
	}
	var currentAddOrEditGrid=$("#"+rolesGridId);
	userGridData = $("#"+rolesGridId).jqGrid('getGridParam','data');
	var rowData={};
	$.each(userGridData,function(i,obj){
		if(obj.id==rowid){
			rowData= userGridData[i];
		} 
	});
	rowData.state=currentAddOrEditGrid.find('select#'+rowid+"_state").val();
	//For Handling who can Edit What Role in the User.
	var currentOrganizationId=$('#hiddenCurrentOrganizationId').val();
	var selectedDistrictId=rowData.district;
	var selectedSchoolId=rowData.school;
	var currentGroupId=$('#hiddenCurrentGroupsId').val();
	var orgTypeCode=groupMap[currentGroupId];
	var currentRowOrgType=groupMap[rowData.role];
	var orgRoleTypeCode=groupRoleMap[currentGroupId];
	var groupCode=groupsCodeMap[currentGroupId];
	var currentRowRoleType=groupRoleMap[rowData.role];
	var checkOrgExists = false;
	var roleMap={};
	$('.js-role-map option').each(function(i,val){
		roleMap[$(val).attr('value')]=$(val).text();
	})
	var exceptionRule=false;
	if(orgRoleTypeCode==currentRowRoleType){
		if(groupsHierarchyMap[rowData.role] > groupsHierarchyMap[currentGroupId]){
			exceptionRule=true;
		}
	}
	$.ajax({
		url : 'checkForOrganizationChildExistsForUser.htm',
		data : {
			selectedDistrictId : selectedDistrictId,
			selectedSchoolId : selectedSchoolId,
			currentRowOrgType : currentRowOrgType,
			orgTypeCode : orgTypeCode
		},
		dataType : 'json',
		type : "POST"
	}).done(function(data) {
		if(data.checkOrgExists === 'true'){
			checkOrgExists = true;
		} else {
			exceptionRule=false;
			//orgTypeCode>=currentRowOrgType
			setTimeout(function(){
				jQuery.fn.fmatter.rowactions.call(this,'cancel',event);
				currentAddOrEditGrid.find('.jqgrow[id="'+rowid+'"]').removeClass('ui-state-highlight');
			}, 500);
			//alert('You do not have permission to edit this role.');
			return false;			
			}
	});
	if(!exceptionRule){
		if(groupCode == 'BTC' && (selectedSchoolId != Number(currentOrganizationId))){
			exceptionRule=false;
			currentAddOrEditGrid.jqGrid('restoreRow',rowid);
			setTimeout(function(){
				jQuery.fn.fmatter.rowactions(rowid,rolesGridId,'cancel',0)
				currentAddOrEditGrid.find('.jqgrow[id="'+rowid+'"]').removeClass('ui-state-highlight');
			}, 500);
			$('#delmodaddUserRolesGrid').hide();
            $('#delmodeditUserRolesGrid').hide();
            $('#delmodmergeUserToKeepGrid1').hide();
            alert('You do not have permission to edit this role.');
            $('.ui-widget-overlay').css('z-index', '0'); 
			return false;
		} else if(groupCode == 'BTC' && (selectedSchoolId == Number(currentOrganizationId))){

			//For Positioning this Works - And Let's See What Happens.
			$form.closest('div.ui-jqdialog').position({
		        my: "center",
		        of: currentAddOrEditGrid.closest('div.ui-jqgrid')
		    });
			
		} else if((orgRoleTypeCode>=currentRowRoleType)){
			exceptionRule=false;
			//orgTypeCode>=currentRowOrgType
			currentAddOrEditGrid.jqGrid('restoreRow',rowid);
			setTimeout(function(){
				jQuery.fn.fmatter.rowactions.call(this,'cancel',event);
				currentAddOrEditGrid.find('.jqgrow[id="'+rowid+'"]').removeClass('ui-state-highlight');
			}, 500);
			alert('You do not have permission to edit this role.');
			return false;
		}
	}
	// set this here so that above validation failure will not cause an issue.
	userRolesModefied = true;

	currentAddOrEditGrid.find("select").css('width', '120px');
	//Get all the States Call
	if(fromChange!=undefined){
		getStatesOrgsForUser(rowid+'_state', true);
	}
	var stateId = rowData.state;
	
	populateChildOrganizationsControlsForGrid(stateId, 'RG', 30, rowid+'_region', rowid+'_'+'district');
	currentAddOrEditGrid.find('select#'+rowid+'_region').val(rowData.region);
	if(currentAddOrEditGrid.find('select#'+rowid+'_region option').length > 1){
		currentAddOrEditGrid.find('select#'+rowid+'_region option').each(function(){
			allowedRegions.push({key:$(this).attr('value'),value:$(this).text()});
	    });
	}
	var allowedRegionsForUserAdd = '';
	//Only Push the Data if thee is some information otherwise Just make It Empty.
	$(allowedRegions).each(function(i, obj){
		allowedRegionsForUserAdd = allowedRegionsForUserAdd + obj.key +":"+obj.value+";";
	});
	if(allowedRegionsForUserAdd.length > 0){
		allowedRegionsForUserAdd = allowedRegionsForUserAdd.substring(0, allowedRegionsForUserAdd.length-1);
	}
	if(allowedRegions.length==0){
		allowedRegionsForUserAdd=defaultValues;
	}
	$('#'+rolesGridId).setColProp('region', 
			{edittype:"select", formatter:"select", editoptions: { value: allowedRegionsForUserAdd,dataEvents: regionDataEvents}});
		
	// Populate assessment Programs
	//getAssessmentProgramIdByOrganization getCurrentUserAndOrgAssessmentPrograms
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
			organizationId: stateId
		},
		dataType: 'json',
		type: "POST",
		async: false
	}).done(function(assessmentProgramsByOrg){
		var clrOptionText='';
		var roleSelect = currentAddOrEditGrid.find('select#'+rowid+'_assessmentProgram')
		var newOptions='<option value="">Select</option>';
		if (assessmentProgramsByOrg !== undefined && assessmentProgramsByOrg !== null && assessmentProgramsByOrg.length > 0){
			var apsAllowedForUserAdd = '';
			$.each(assessmentProgramsByOrg, function(i, clrAssessmentProgram) {
				if(assessmentProgramsByOrg[i].id !=''){
					newOptions += '<option role="option" value="' + assessmentProgramsByOrg[i].id + '">' +
					assessmentProgramsByOrg[i].abbreviatedname + '</option>';
					apsAllowedForUserAdd = apsAllowedForUserAdd + Number(assessmentProgramsByOrg[i].id) + ":" + assessmentProgramsByOrg[i].abbreviatedname +  ";";
				}
			});
			if(apsAllowedForUserAdd.length > 0){
				apsAllowedForUserAdd = apsAllowedForUserAdd.substring(0, apsAllowedForUserAdd.length-1);
			}
			currentAddOrEditGrid.find('select#'+rowid+"_assessmentProgram").html(newOptions);
			$('#'+rolesGridId).setColProp('assessmentProgram', 
				{edittype:"select", formatter:"select", editoptions: { value: apsAllowedForUserAdd}});
			currentAddOrEditGrid.find('select#'+rowid+"_assessmentProgram").val(rowData.assessmentProgram);
		}
   });
	
	$.ajax({
		url: 'getAllRoles.htm',
		data: {
		},
		type: 'POST',
		dataType: 'json',
		async:false
	}).done(function(data) {
		// Clear all options in Roles
		var roleSelect = currentAddOrEditGrid.find('select#'+rowid+'_role');
		roleSelect.find('option').filter(function() {
	        return $(this).val() > 0
	    }).remove().end();
		var roles = [];
		var depreciatedGroupId;
		var depreciatedOptionText = "";
		for (var i = 0, length = data['allGroups'].length; i < length; i++) {
			
			optionText = data['allGroups'][i].groupName;
			var organizationTypeId = data['allGroups'][i].organizationTypeId;
			var groupId = data['allGroups'][i].groupId;
			var isdepreciated = data['allGroups'][i].isdepreciated;
	
			if(data['allGroups'][i].allowedToAssign){
				if(!data['allGroups'][i].isdepreciated){
			
				roleSelect.append($('<option></option>').data('organizationTypeId', organizationTypeId).val(groupId).html(optionText));
				}
				
				if(data['allGroups'][i].isdepreciated){
					
					if(groupId == rowData.role){
						depreciatedGroupId = groupId;
						depreciatedOptionText = optionText;
					}
				}
				
			} else {
				if(!data['allGroups'][i].isdepreciated){ 
					roleSelect.append($('<option style="display:none"></option>').data('organizationTypeId', organizationTypeId).val(groupId).html(optionText));
				}
				
			}
		}
		if(depreciatedGroupId !=null && depreciatedGroupId !='undefined' && depreciatedOptionText !=null && depreciatedOptionText !=''){
			roleSelect.append($('<option></option>').data('organizationTypeId', organizationTypeId).val(depreciatedGroupId).html(depreciatedOptionText));
			
			$('#'+rowid+'_role').append($('#' +rowid+'_role option').remove().sort(function(a, b) {
			    var at = $(a).text(), bt = $(b).text();
			    return (at > bt)?1:((at < bt)?-1:0);
			}));
			
			jQuery('#' +rowid+'_role option').filter(function(){
				    return $.trim($(this).text()) ==  'Select'
			}).remove();
			 
			roleSelect.prepend($('<option></option>').data('organizationTypeId', organizationTypeId).val(1).html("Select"));
		}
		roleSelect.val(rowData.role); 
		
	});
	var orgTypeId = groupMap[rowData.role];
	// Populate Districts 
	//This is to make sure we are not populating the values.
	if(orgTypeId>=5){
		$.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
			data: {
				orgId : stateId,
	        	orgType:'DT',
	        	orgLevel: 50	
	    	},
			dataType: 'json',
			type: "GET",
			async: false
		}).done(function(districtsByState){
			var clrOptionText='';
			var newOptions='<option value="">Select</option>';
			if (districtsByState !== undefined && districtsByState !== null && districtsByState.length > 0){
				var districtValues = '';
				$.each(districtsByState, function(i, clrAssessmentProgram) {
					if(districtsByState[i].id !=''){
						newOptions += '<option role="option" value="' + districtsByState[i].id + '">' +
						districtsByState[i].organizationName + '</option>';
						districtValues = districtValues + Number(districtsByState[i].id) + ":" + districtsByState[i].organizationName +  ";";
					}
				});
				if(districtValues.length > 0){
					districtValues = districtValues.substring(0, districtValues.length-1);
				}
				currentAddOrEditGrid.find('select#'+rowid+"_district").html(newOptions);
				currentAddOrEditGrid.find('select#'+rowid+"_district").val(rowData.district);
				$('#'+rolesGridId).setColProp('district', 
					{edittype:"select", formatter:"select",  editoptions: { value: districtValues,dataEvents:districtDataEvents}});
			}
       });
	}	
		//May be we should get Schools as well if the orgTypeId is 7. - Let's check.
		if(orgTypeId===2){
			currentAddOrEditGrid.find("select#"+rowid+"_district").prop('disabled','disabled').css('background-color','#e5e5e5');
			currentAddOrEditGrid.find("select#"+rowid+"_region").prop('disabled','disabled').css('background-color','#e5e5e5');
			currentAddOrEditGrid.find("select#"+rowid+"_school").prop('disabled','disabled').css('background-color','#e5e5e5');
		}
		if(orgTypeId===5){
			currentAddOrEditGrid.find("select#"+rowid+"_school").html('<option value="0"></option>');
			currentAddOrEditGrid.find("select#"+rowid+"_school").val('0');
			currentAddOrEditGrid.find("select#"+rowid+"_school").prop('disabled','disabled').css('background-color','#e5e5e5');
		}
		if(orgTypeId===7){
			populateChildOrganizationsControlsForGrid(rowData.district, 'SCH', 70, rowid+'_school', rowid+'_'+'district');
			currentAddOrEditGrid.find("select#"+rowid+"_school").val(rowData.school);
			currentAddOrEditGrid.find("select#"+rowid+"_school").removeAttr('disabled').css('background-color','');
		}
		if(currentAddOrEditGrid.find("select#"+rowid+"_region").children('option').length<=1){
			currentAddOrEditGrid.find("select#"+rowid+"_region").html('<option value="0"></option>');
	        currentAddOrEditGrid.find("select#"+rowid+"_region").val('0');
	        currentAddOrEditGrid.find("select#"+rowid+"_region").prop('disabled','disabled').css('background-color','#e5e5e5');
		}
}

function getSafeValue(value){
	if(value === ''){
		value = ':Select';
	} else if(value.length > 0){
		value = ':Select;'+value;
	}
	return value;
}

function renderUserRolesGrid(rolesGridId, screen){
	$('#'+rolesGridId).jqGrid("GridUnload");
	currentMode = screen;
	//initializeAllowedStatesForUser(screen);
	
	var dataType = "local";
	var gridData = [];//addUserRolesGridData;
	if(screen === 'e'){
		//dataType = "jsonstring";
		gridData = editUserRolesGridData;
	}else if(screen === 'm'){
		gridData = mergeUserRolesGridData;
	}
	var rolesGrid=$('#'+rolesGridId);
	myDelOptions = {
			
        // because I use "local" data I don't want to send the changes to the server
        // so I use "processing:true" setting and delete the row manually in onclickSubmit
		afterShowForm: function ($form) {
			$('#delmodaddUserRolesGrid').show();
			$('#delmodeditUserRolesGrid').show();
			$('#delmodmergeUserToKeepGrid1').show();
			//This is to get the ID of the Row from the Grid.
			var rowid = $form.find('tr#DelData').find('td:first').text();
			var currentAddOrEditGrid=$("#"+rolesGridId);
			userGridData = $("#"+rolesGridId).jqGrid('getGridParam','data');
			var rowData={};
			$.each(userGridData,function(i,obj){
				if(obj.id==rowid){
					rowData= userGridData[i];
				} 
			});
			var currentOrganizationId=$('#hiddenCurrentOrganizationId').val();
			var selectedDistrictId=rowData.district;
			var selectedSchoolId=rowData.school;
			var currentGroupId=$('#hiddenCurrentGroupsId').val();
			var orgTypeCode=groupMap[currentGroupId];
			var currentRowOrgType=groupMap[rowData.role];
			var orgRoleTypeCode=groupRoleMap[currentGroupId];
			var groupCode=groupsCodeMap[currentGroupId];
			var currentRowRoleType=groupRoleMap[rowData.role];
			var checkOrgExists = false;
			//orgTypeCode>=currentRowOrgType
			var roleMap={};
			$('.js-role-map option').each(function(i,val){
				roleMap[$(val).attr('value')]=$(val).text();
			})
			var exceptionRule=false;

			if(orgRoleTypeCode==currentRowRoleType){
				if(groupsHierarchyMap[rowData.role] > groupsHierarchyMap[currentGroupId]){
					exceptionRule=true;
				}
			}
			$.ajax({
				url : 'checkForOrganizationChildExistsForUser.htm',
				data : {
					selectedDistrictId : selectedDistrictId,
					selectedSchoolId : selectedSchoolId,
					currentRowOrgType : currentRowOrgType,
					orgTypeCode : orgTypeCode
				},
				dataType : 'json',
				type : "POST" 
			}).done(function(data) {
				if(data.checkOrgExists === 'true'){
					checkOrgExists = true;
				} else {
					exceptionRule = false;
					currentAddOrEditGrid.jqGrid('restoreRow', rowid);
					setTimeout(function() {
						//jQuery.fn.fmatter.rowactions(rowid, rolesGridId, 'cancel', 0)
						currentAddOrEditGrid.find('.jqgrow[id="' + rowid + '"]').removeClass('ui-state-highlight');
					}, 500);
					$('.ui-widget-overlay').css('z-index', '0');
					return false;
				}
			});
			
			if(!exceptionRule){
				if(groupCode == 'BTC' && (selectedSchoolId != Number(currentOrganizationId))){
						exceptionRule=false;
						currentAddOrEditGrid.jqGrid('restoreRow',rowid);
						setTimeout(function(){
							//jQuery.fn.fmatter.rowactions(rowid,rolesGridId,'cancel',0)
							currentAddOrEditGrid.find('.jqgrow[id="'+rowid+'"]').removeClass('ui-state-highlight');
						}, 500);
						$('#delmodaddUserRolesGrid').hide();
			            $('#delmodeditUserRolesGrid').hide();
			            $('#delmodmergeUserToKeepGrid1').hide();
			            alert('You do not have permission to delete this role.');
			            $('.ui-widget-overlay').css('z-index', '0'); 
			            //$('#'+rolesGridId).jqGrid('setGridParam', {}).trigger('reloadGrid');
						return false;
					} else if(groupCode == 'BTC' && (selectedSchoolId == Number(currentOrganizationId))){
			
						//For Positioning this Works - And Let's See What Happens.
						$form.closest('div.ui-jqdialog').position({
					        my: "center",
					        of: currentAddOrEditGrid.closest('div.ui-jqgrid')
					    });
						
					} else if((orgRoleTypeCode>=currentRowRoleType)){
					exceptionRule=false;
					currentAddOrEditGrid.jqGrid('restoreRow',rowid);
					setTimeout(function(){
						jQuery.fn.fmatter.rowactions(rowid,rolesGridId,'cancel',0)
						currentAddOrEditGrid.find('.jqgrow[id="'+rowid+'"]').removeClass('ui-state-highlight');
					}, 500);
					$('#delmodaddUserRolesGrid').hide();
		            $('#delmodeditUserRolesGrid').hide();
		            $('#delmodmergeUserToKeepGrid1').hide();
		            alert('You do not have permission to delete this role.');
		            $('.ui-widget-overlay').css('z-index', '0'); 
					return false;
				}else{
					//For Positioning this Works - And Let's See What Happens.
					$form.closest('div.ui-jqdialog').position({
				        my: "center",
				        of: currentAddOrEditGrid.closest('div.ui-jqgrid')
				    });
					return false;
				}
				
			}else{
				//For Positioning this Works - And Let's See What Happens.
				$form.closest('div.ui-jqdialog').position({
			        my: "center",
			        of: currentAddOrEditGrid.closest('div.ui-jqgrid')
			    });
			}
		},
		onclickSubmit: function(options,rowid) {
			options.processing=true;
			var gridId = rolesGrid[0].id,
            gridP = rolesGrid[0].p,
            newPage = gridP.page;
            // we can use onclickSubmit function as "onclick" on "Delete" button
            // delete row
			$('#'+rolesGridId).jqGrid('delRowData',rowid);
            gridData = jQuery.grep(gridData, function(value) {
                return value.id != rowid;
            });
            if (gridP.lastpage > 1) {// on the multipage grid reload the grid
                if (gridP.reccount === 0 && newPage === gridP.lastpage) {
                    // if after deliting there are no rows on the current page
                    // which is the last page of the grid
                    newPage -= 1; // go to the previous page
                }
                // reload grid to make the row from the next page visable.
                $('#'+rolesGridId).trigger("reloadGrid", [{ page: newPage}]);
            }
            var teacherId = groupsNameMap['Teacher'];
        	if(screen === ''){
        	    var addGridData = $('#'+rolesGridId).jqGrid("getGridParam", "data"),
    			addGridRoleId = $.map(addGridData, function (item) { return item.role; });
        		var educatorIdentifierAsterisk=true;
            	if(addGridRoleId=="" || addGridRoleId ==null)
            		{
            		$('span[id^="educatorIdentifierAsterisk"]').remove();
            		}
            	else{
            	     for (var i = 0; i < addGridRoleId.length; ++i) {
            	    	if( addGridRoleId[i] == teacherId){
            					$('span[id^="educatorIdentifierAsterisk"]').remove();
            					$("#educatorIdentifierLabel").append('<span id="educatorIdentifierAsterisk" class="lbl-required">*</span>');
            					educatorIdentifierAsterisk= false;
            	    	}
            	    	else if(addGridRoleId[i] != teacherId && educatorIdentifierAsterisk==true){
            	    		$('span[id^="educatorIdentifierAsterisk"]').remove();
            	    		}
            	     }
            	}
        	}else if(screen === 'e'){
        		var editGridData = $('#'+rolesGridId).jqGrid("getGridParam", "data"),
    			editGridRoleId = $.map(editGridData, function (item) { return item.role; });
        		var eeducatorIdentifierAsterisk=true;
            	if(editGridRoleId=="" || editGridRoleId ==null)
            		{
            		$('span[id^="eeducatorIdentifierAsterisk"]').remove();
            		}
            	else{
            	     for (var i = 0; i < editGridRoleId.length; ++i) {
            	    	if( editGridRoleId[i] == teacherId){
            					$('span[id^="eeducatorIdentifierAsterisk"]').remove();
            					$("#eeducatorIdentifierLabel").append('<span id="eeducatorIdentifierAsterisk" class="lbl-required">*</span>');
            					eeducatorIdentifierAsterisk= false;
            	    	}
            	    	else if(editGridRoleId[i] != teacherId && eeducatorIdentifierAsterisk==true){
            	    		$('span[id^="eeducatorIdentifierAsterisk"]').remove();
            	    		}
            	     }
            	}
        	}
            $('#delmodaddUserRolesGrid').hide();
            $('#delmodeditUserRolesGrid').hide();
			$('#delmodmergeUserToKeepGrid1').hide();
			$('#'+rolesGridId).jqGrid('setGridParam', {}).trigger('reloadGrid');
           return true;
        },
        processing:true
    };
	var pagerId = '#'+rolesGridId+'Pager';
	statesAllowedForUserAdd = getSafeValue(statesAllowedForUserAdd);
	apsAllowedForUserAdd = getSafeValue(apsAllowedForUserAdd);
	rolesAllowedForUserAdd = getSafeValue(rolesAllowedForUserAdd);
	userAllowedRoles=getSafeValue(userAllowedRoles);
	allowedRegionsForUserAdd = getSafeValue(allowedRegionsForUserAdd);
	allowedDistrictsForUserAdd = getSafeValue(allowedDistrictsForUserAdd);
	allowedSchoolsForUserAdd = getSafeValue(allowedSchoolsForUserAdd);
	
	$('#'+rolesGridId).jqGrid({ 
		datatype: dataType, 
		data: gridData,
		editurl: 'clientArray',
		colNames:['Edit','Id','State', 'Assessment Program', 'Role', 'District', 'School', 'Default'], 
		rowNum : 10,
		rowList : [ 10, 20, 30, 40, 60, 90 ],
		width: 1046,
		page: 1,
		pager : pagerId,
		viewrecords : true,
		multiselect: true,
		altclass: 'altrow',
		altRows:true,
		height : 'auto',
		colModel:[ 
	          {
	        	  label: "Edit Actions",
	        	  name: "actions",
	            width: 100,
	            formatter: "actions",
	            sortable:false,
	            search:false,
	            hidden: (!hasUserModifyPermission || (isMergeScreen && rolesGridId != 'mergeUserToKeepGrid1')),
	            formatoptions: {
	                keys: true,
	                onEdit: executeEditFunction,
	                afterSave: executeSaveFunction,
	                afterRestore: executeRestoreFunction,
	                editOptions: {},
	                addOptions: {},
	                delOptions: myDelOptions
	            }
           	},
			{name:'id',index:'id', label:rolesGridId+'Id',width:90, editable: false,sortable:false,search:false, hidden: true}, 
	       	{name:'state',index:'state',label:rolesGridId+'State', width:125,editable: true,edittype:"select",sortable:true 
				, stype:'select', searchoptions: {value: statesAllowedForUserAdd}}, 
	       	{name:'assessmentProgram',index:'assessmentProgram',label:rolesGridId+'AssessmentProgram', width:125, editable: true,edittype:"select",sortable:true
				, stype:'select', searchoptions: {value: apsAllowedForUserAdd }}, 
			{name:'role',index:'role',label:rolesGridId+'Role', width:125, editable: true,edittype:"select",sortable:true 
				, stype:'select', searchoptions: {value: userAllowedRoles }},
			/*{name:'region',index:'region',label:rolesGridId+'Region', width:125, sortable:true,editable: true,edittype:"select"
				, stype:'select', searchoptions: {value: allowedRegionsForUserAdd }},*/
			{name:'district',index:'district',label:rolesGridId+'District', width:125, sortable:true,editable: true, edittype:"select"
	    	   	, stype:'select', searchoptions: {value: allowedDistrictsForUserAdd }},
		   	{name:'school',index:'school',label:rolesGridId+'School', width:125, sortable:true,editable: true,edittype:"select"
	    	   	, stype:'select', searchoptions: {value: allowedSchoolsForUserAdd }},
		   	{name:'Default',index:'Default',label:rolesGridId+'Default', width:100, sortable:false,editable: false, search:false,
		   		formatter: function(cellValue, option) {
		   			var disableFlag = (!hasUserModifyPermission || (isMergeScreen && rolesGridId != 'mergeUserToKeepGrid1')) ? 'disabled="disabled"': '';
	        	   if(cellValue){
	        		   return '<input type="radio" '+ disableFlag +' checked="checked"  name="DefaultRole_'+option.gid+'"  />';
	        	   } else {
	        		   return '<input type="radio" '+ disableFlag +'name="DefaultRole_'+option.gid+'" />';
	        	   }
		   		}
	   		}],
	loadComplete: function (data) {
   	 var ids = $(this).jqGrid('getDataIDs');         
        var tableid=$(this).attr('id');      
           for(var i=0;i<ids.length;i++)
           {         
               $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'role') +' '+$(this).getCell(ids[i], 'assessmentProgram')+ ' Check Box');
           }
           var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
           
           $.each(objs, function(index, value) {
        	   if($(value).attr('title')==undefined){
        		   
             var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                   $(value).attr('title',$(nm).text()+' filter');
        	   }
        	   if ( $(value).is('select')) {
        		   if ( !$(value).is( ".select2-hidden-accessible" ) ) {
            	   $(value).removeAttr("role");
            	   $(value).css({"width": "100%"});
            	   
                	$(value).select2({
          	   		  placeholder:'Select',
        	   		  multiple: false,
        	      		  allowClear : true
        	   		 });
        		   }
                };
                   });
           var radioButtons= $( '#gbox_'+tableid).find('[name^=DefaultRole_'+rolesGridId+']');
           $.each(radioButtons, function(index, value) {
        	   $(value).attr('title',rolesGridId+' row '+(index+1)+' Default Role Radio button');
           });
           
         
   },

	});
	$('#'+rolesGridId).jqGrid('navGrid' , pagerId, {edit: true, add: true, del: true, search: true, refresh: true});
	$('#'+rolesGridId).jqGrid('filterToolbar', { stringResult: false, searchOnEnter: false});
}

function initAddUserEvent(buttonElementId, rolesGridId, screen){
	$('#'+buttonElementId).on("click",function(){
		var maxRowId = $('#'+rolesGridId).find('.jqgrow').length;
		
		var defaultRole = false;
		if(maxRowId === 0){
			maxRowId=-1;
			defaultRole = true;
		}else{
			maxRowId=parseInt($('#'+rolesGridId).find('.jqgrow')[maxRowId-1].id);
		}
		if(screen ===''){
			var State = Number($('#addUserOrganization').val());
			var assessmentProgram = Number($('#addUserAssessmentProgram').val());
			var userGroup = Number($('#addUserGroup').val());			
			var userGroupname = $('#addUserGroup option:selected').text();
			if(userGroupname == 'Teacher'){
				$('span[id^="educatorIdentifierAsterisk"]').remove();
				$("#educatorIdentifierLabel").append('<span id="educatorIdentifierAsterisk" class="lbl-required">*</span>');
			}			
			var orgTypeId = groupMap[userGroup];
			//orgType ID 2 is State Not 1.

			var roleDistrict = Number($('#addUserDistrict').val());

			var roleSchool = Number($('#addUserSchool').val());

			var roleRegion = Number($('#addUserRegion').val());
			//}
			
			// Push the values to in memory array
			//addUserRolesGridData.push({id: maxRowId, state: State, assessmentProgram: assessmentProgram,
				//role: userGroup, district: roleDistrict, school: roleSchool, region: roleRegion, Default: defaultRole});
			//initializeAllowedStatesForUser(screen);
			//Push into EditOptions for Display..
			// allowedStates=[],allowedAps=[],allowedRoles=[],allowedRegions=[],allowedDistricts=[],allowedSchools=[];
			
			var alreadyStateExist=_.findWhere(allowedStates,{key:State});
			if(!alreadyStateExist){
				allowedStates.push({key: State, value: $('#addUserOrganization option:selected').text()});
			}
			var allowedApsExist=_.findWhere(allowedAps,{key:assessmentProgram});
			if(!allowedApsExist){
				allowedAps.push({key:assessmentProgram, value: $('#addUserAssessmentProgram option:selected').text()});
			}
			var allowedRolesExist=_.findWhere(allowedRoles,{key:userGroup});
			if(!allowedRolesExist){
				allowedRoles.push({key:userGroup, value: $('#addUserGroup option:selected').text()});
			}
			var allowedRegionsExist=_.findWhere(allowedRegions,{key:roleRegion});
			if(!allowedRegionsExist){
				allowedRegions.push({key:roleRegion, value: $('#addUserRegion option:selected').text()});
			}
			var allowedDistrictsExist=_.findWhere(allowedDistricts,{key:roleDistrict});
			if(!allowedDistrictsExist){
				allowedDistricts.push({key:roleDistrict, value: $('#addUserDistrict option:selected').text()});
			}
			var allowedSchoolsExist=_.findWhere(allowedSchools,{key:roleSchool});
			if(!allowedSchoolsExist){
				allowedSchools.push({key: roleSchool, value: $('#addUserSchool option:selected').text()});
			}
			statesAllowedForUserAdd='';
			apsAllowedForUserAdd='';
			rolesAllowedForUserAdd='';
			userAllowedRoles='';
			allowedRegionsForUserAdd='';
			allowedDistrictsForUserAdd='';
			allowedSchoolsForUserAdd='';
			$(allowedStates).each(function(i, obj){
				statesAllowedForUserAdd = statesAllowedForUserAdd + obj.key +":"+obj.value+";";
			});
			if(statesAllowedForUserAdd.length > 0){
				statesAllowedForUserAdd = statesAllowedForUserAdd.substring(0, statesAllowedForUserAdd.length-1);
			}
			$(allowedAps).each(function(i, obj){
				apsAllowedForUserAdd = apsAllowedForUserAdd + obj.key +":"+obj.value+";";
			});
			if(apsAllowedForUserAdd.length > 0){
				apsAllowedForUserAdd = apsAllowedForUserAdd.substring(0, apsAllowedForUserAdd.length-1);
			}
			initializeAllowedRolesForUser('');
			$(allowedRegions).each(function(i, obj){
				allowedRegionsForUserAdd = allowedRegionsForUserAdd + obj.key +":"+obj.value+";";
			});
			if(allowedRegionsForUserAdd.length > 0){
				allowedRegionsForUserAdd = allowedRegionsForUserAdd.substring(0, allowedRegionsForUserAdd.length-1);
			}
			$(allowedDistricts).each(function(i, obj){
				allowedDistrictsForUserAdd = allowedDistrictsForUserAdd + obj.key +":"+obj.value+";";
			});
			if(allowedDistrictsForUserAdd.length > 0){
				allowedDistrictsForUserAdd = allowedDistrictsForUserAdd.substring(0, allowedDistrictsForUserAdd.length-1);
			}
			$(allowedSchools).each(function(i, obj){
				allowedSchoolsForUserAdd = allowedSchoolsForUserAdd + obj.key +":"+obj.value+";";
			});
			if(allowedSchoolsForUserAdd.length > 0){
				allowedSchoolsForUserAdd = allowedSchoolsForUserAdd.substring(0, allowedSchoolsForUserAdd.length-1);
			}
			statesAllowedForUserAdd = getSafeValue(statesAllowedForUserAdd);
			apsAllowedForUserAdd = getSafeValue(apsAllowedForUserAdd);
			rolesAllowedForUserAdd = getSafeValue(rolesAllowedForUserAdd);
			userAllowedRoles=getSafeValue(userAllowedRoles);
			allowedRegionsForUserAdd = getSafeValue(allowedRegionsForUserAdd);
			allowedDistrictsForUserAdd = getSafeValue(allowedDistrictsForUserAdd);
			allowedSchoolsForUserAdd = getSafeValue(allowedSchoolsForUserAdd);
			
			$('#'+rolesGridId).jqGrid("destroyFilterToolbar");
			var cm = $('#'+rolesGridId).jqGrid('getGridParam','colModel');
			$('#'+rolesGridId).setColProp('state', {edittype:"select", formatter:"select", stype:"select",
				editoptions: { value: statesAllowedForUserAdd,dataEvents:stateDataEvents}, 
				searchoptions: {value: statesAllowedForUserAdd}
				, sorttype: function (cell, obj) {
					return  (_.findWhere(allowedStates,{key:cell})) ? _.findWhere(allowedStates,{key:cell}).value : '';
			    }});
			var stateFilterId =$(_.findWhere(cm, {name: 'state'})).attr('label');
			populateFilterValuesUserCommon(statesAllowedForUserAdd,stateFilterId);		
			
			$('#'+rolesGridId).setColProp('assessmentProgram', {edittype:"select", formatter:"select",stype:"select",
				editoptions: { value: apsAllowedForUserAdd},
				searchoptions: {value: apsAllowedForUserAdd}
				, sorttype: function (cell, obj) {
					return (_.findWhere(allowedAps,{key:cell})) ? _.findWhere(allowedAps,{key:cell}).value : '';
			    }});
			var apFilterId =$(_.findWhere(cm, {name: 'assessmentProgram'})).attr('label');
			populateFilterValuesUserCommon(apsAllowedForUserAdd,apFilterId);
			
			$('#'+rolesGridId).setColProp('role', {edittype:"select", formatter:"select",stype:"select",
				editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents},
				searchoptions: {value: userAllowedRoles }
				, sorttype: function (cell, obj) {
					return  (_.findWhere(allowedRoles,{key:cell})) ? _.findWhere(allowedRoles,{key:cell}).value : '';
			    }});
			var roleFilterId =$(_.findWhere(cm, {name: 'role'})).attr('label');
			populateFilterValuesUserCommon(userAllowedRoles,roleFilterId);
			
			//We need to add three more to the same.
			$('#'+rolesGridId).setColProp('region', {edittype:"select", formatter:"select",stype:"select",
				editoptions: { value: allowedRegionsForUserAdd,dataEvents:regionDataEvents},
				searchoptions: {value: allowedRegionsForUserAdd }
				, sorttype: function (cell, obj) {
					return  (_.findWhere(allowedRegions,{key:cell})) ? _.findWhere(allowedRegions,{key:cell}).value : '';
			    }});
			var regionFilterId =$(_.findWhere(cm, {name: 'region'})).attr('label');
			populateFilterValuesUserCommon(allowedRegionsForUserAdd,regionFilterId);
			
			$('#'+rolesGridId).setColProp('district', {edittype:"select", formatter:"select", stype:"select",
				editoptions: { value: allowedDistrictsForUserAdd,dataEvents:districtDataEvents},
				searchoptions: {value: allowedDistrictsForUserAdd }
				, sorttype: function (cell, obj) {
					return  (_.findWhere(allowedDistricts,{key:cell})) ? _.findWhere(allowedDistricts,{key:cell}).value : '';
			    }});
			var districtFilterId =$(_.findWhere(cm, {name: 'district'})).attr('label');
			populateFilterValuesUserCommon(allowedDistrictsForUserAdd,districtFilterId);
			
			$('#'+rolesGridId).setColProp('school', {edittype:"select", formatter:"select", stype:"select",
				editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents},
				searchoptions: {value: allowedSchoolsForUserAdd }
				, sorttype: function (cell, obj) {
					return  (_.findWhere(allowedSchools,{key:cell})) ? _.findWhere(allowedSchools,{key:cell}).value : '';
			    }});
			var schoolFilterId =$(_.findWhere(cm, {name: 'school'})).attr('label');
			populateFilterValuesUserCommon(allowedSchoolsForUserAdd,schoolFilterId);
			
			$('#'+rolesGridId).jqGrid('addRowData', maxRowId+1, {id: maxRowId+1, state: State, assessmentProgram: assessmentProgram, 
				role: userGroup, district: roleDistrict, school: roleSchool, region: roleRegion, Default: defaultRole});
			
			// Clear all drop downs and disable Add button
			$('#addUserOrganization').val('').change();
			$('#addUserAssessmentProgram').val('').change();
			$('#addUserGroup').val('').change();
			$('#addRoleRegionContainer').hide();
			
			$('#'+rolesGridId).jqGrid('filterToolbar', { stringResult: false, searchOnEnter: false});
			
		} else {
			if(screen === 'e'){
			var State = Number($('#editUserOrganization').val());
			var assessmentProgram = Number($('#editUserAssessmentProgram').val());
			var userGroup = Number($('#editUserGroup').val());
			var userGroupname = $('#editUserGroup option:selected').text();
			if(userGroupname == 'Teacher'){
				$('span[id^="eeducatorIdentifierAsterisk"]').remove();
				$("#eeducatorIdentifierLabel").append('<span id="eeducatorIdentifierAsterisk" class="lbl-required">*</span>');
			}			
			var roleDistrict = Number($('#editUserDistrict').val());
			var roleSchool = Number($('#editUserSchool').val());
			var roleRegion = Number($('#editUserRegion').val());
			
			
			// Push the values to in memory array
			//editUserRolesGridData.push({id: maxRowId, state: State, assessmentProgram: assessmentProgram, 
				//role: userGroup, district: roleDistrict, school: roleSchool, region: roleRegion, Default: defaultRole});
			
			allowedStates.push({key: State, value: $('#editUserOrganization option:selected').text()});
			allowedAps.push({key:assessmentProgram, value: $('#editUserAssessmentProgram option:selected').text()});
			allowedRoles.push({key:userGroup, value: $('#editUserGroup option:selected').text()});
			allowedRegions.push({key:roleRegion, value: $('#editUserRegion option:selected').text()});
			allowedDistricts.push({key:roleDistrict, value: $('#editUserDistrict option:selected').text()});
			allowedSchools.push({key: roleSchool, value: $('#editUserSchool option:selected').text()});
			var allowedRolesExist=_.findWhere(allowedRoles,{key:userGroup});
			if(!allowedRolesExist){
				allowedRoles.push({key:userGroup, value: $('#addUserGroup option:selected').text()});
			}
			initializeAllowedRolesForUser('e');
			userAllowedRoles=getSafeValue(userAllowedRoles);
			$('#'+rolesGridId).setColProp('role', {edittype:"select", formatter:"select",stype:"select",
				editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents},
				searchoptions: {value: userAllowedRoles }
				, sorttype: function (cell, obj) {
					return  (_.findWhere(allowedRoles,{key:cell})) ? _.findWhere(allowedRoles,{key:cell}).value : '';
			    }});
			}else if(screen ==='m'){
				var State = Number($('#mergeUserOrganization').val());
				var assessmentProgram = Number($('#mergeUserAssessmentProgram').val());
				var userGroup = Number($('#mergeUserGroup').val());
				var roleDistrict = Number($('#mergeUserDistrict').val());
				var roleSchool = Number($('#mergeUserSchool').val());
				var roleRegion = Number($('#mergeUserRegion').val());
				
				
				
				allowedStates.push({key: State, value: $('#mergeUserOrganization option:selected').text()});
				allowedAps.push({key:assessmentProgram, value: $('#mergeUserAssessmentProgram option:selected').text()});
				allowedRoles.push({key:userGroup, value: $('#mergeUserGroup option:selected').text()});
				allowedRegions.push({key:roleRegion, value: $('#mergeUserRegion option:selected').text()});
				allowedDistricts.push({key:roleDistrict, value: $('#mergeUserDistrict option:selected').text()});
				allowedSchools.push({key: roleSchool, value: $('#mergeUserSchool option:selected').text()});
			}
			statesAllowedForUserAdd='';
			apsAllowedForUserAdd='';
			allowedRegionsForUserAdd='';
			allowedDistrictsForUserAdd='';
			allowedSchoolsForUserAdd='';
			
			$(allowedStates).each(function(i, obj){
				statesAllowedForUserAdd = statesAllowedForUserAdd + obj.key +":"+obj.value+";";
			});
			if(statesAllowedForUserAdd.length > 0){
				statesAllowedForUserAdd = statesAllowedForUserAdd.substring(0, statesAllowedForUserAdd.length-1);
			}
			$(allowedAps).each(function(i, obj){
				apsAllowedForUserAdd = apsAllowedForUserAdd + obj.key +":"+obj.value+";";
			});
			if(apsAllowedForUserAdd.length > 0){
				apsAllowedForUserAdd = apsAllowedForUserAdd.substring(0, apsAllowedForUserAdd.length-1);
			}
			initializeAllowedRolesForUser(screen);
			$(allowedRegions).each(function(i, obj){
				allowedRegionsForUserAdd = allowedRegionsForUserAdd + obj.key +":"+obj.value+";";
			});
			if(allowedRegionsForUserAdd.length > 0){
				allowedRegionsForUserAdd = allowedRegionsForUserAdd.substring(0, allowedRegionsForUserAdd.length-1);
			}
			$(allowedDistricts).each(function(i, obj){
				allowedDistrictsForUserAdd = allowedDistrictsForUserAdd + obj.key +":"+obj.value+";";
			});
			if(allowedDistrictsForUserAdd.length > 0){
				allowedDistrictsForUserAdd = allowedDistrictsForUserAdd.substring(0, allowedDistrictsForUserAdd.length-1);
			}
			$(allowedSchools).each(function(i, obj){
				allowedSchoolsForUserAdd = allowedSchoolsForUserAdd + obj.key +":"+obj.value+";";
			});
			if(allowedSchoolsForUserAdd.length > 0){
				allowedSchoolsForUserAdd = allowedSchoolsForUserAdd.substring(0, allowedSchoolsForUserAdd.length-1);
			}
			
			$('#'+rolesGridId).setColProp('state', {edittype:"select", formatter:"select", editoptions: { value: statesAllowedForUserAdd,dataEvents:stateDataEvents}
			, sorttype: function (cell, obj) {
				return (_.findWhere(allowedStates,{key:cell})) ? _.findWhere(allowedStates,{key:cell}).value : '';
		    }});
			$('#'+rolesGridId).setColProp('assessmentProgram', {edittype:"select", formatter:"select", editoptions: { value: apsAllowedForUserAdd}
			, sorttype: function (cell, obj) {
				return (_.findWhere(allowedAps,{key:cell})) ? _.findWhere(allowedAps,{key:cell}).value : '';
		    }});
			$('#'+rolesGridId).setColProp('role', {edittype:"select", formatter:"select", editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents}
			, sorttype: function (cell, obj) {
				return (_.findWhere(allowedRoles,{key:cell})) ? _.findWhere(allowedRoles,{key:cell}).value : '';
		    }});
			//We need to add three more to the same.
			$('#'+rolesGridId).setColProp('region', {edittype:"select", formatter:"select", editoptions: { value: allowedRegionsForUserAdd,dataEvents:regionDataEvents}
			, sorttype: function (cell, obj) {
				return (_.findWhere(allowedRegions,{key:cell})) ? _.findWhere(allowedRegions,{key:cell}).value : '';
		    }});
			$('#'+rolesGridId).setColProp('district', {edittype:"select", formatter:"select", editoptions: { value: allowedDistrictsForUserAdd,dataEvents:districtDataEvents}
			, sorttype: function (cell, obj) {
				return (_.findWhere(allowedDistricts,{key:cell})) ? _.findWhere(allowedDistricts,{key:cell}).value : '';
		    }});
			$('#'+rolesGridId).setColProp('school', {edittype:"select", formatter:"select", editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents}
			, sorttype: function (cell, obj) {
				return (_.findWhere(allowedSchools,{key:cell})) ? _.findWhere(allowedSchools,{key:cell}).value : '';
		    }});
			
			$('#'+rolesGridId).jqGrid('addRowData', maxRowId+1, {id: maxRowId+1, state: State, assessmentProgram: assessmentProgram, 
				role: userGroup, district: roleDistrict, school: roleSchool, region: roleRegion, Default: defaultRole});
			
			// Clear all drop downs and disable Add button
			if(screen === 'e'){
				$('#editUserOrganization').val('').change();
				$('#editUserAssessmentProgram').val('').change();
				$('#editUserGroup').val('').change();
				$('#editRoleRegionContainer').hide();
			}else if(screen === 'm'){
				$('#mergeUserOrganization').val('').change();
				$('#mergeUserAssessmentProgram').val('').change();
				$('#mergeUserGroup').val('').change();
				$('#mergeRoleRegionContainer').hide();
			}
			userRolesModefied = true;
			
		}
		

	   	 var ids = $("#"+rolesGridId).jqGrid('getDataIDs');         
	        var tableid=rolesGridId;      
	           for(var i=0;i<ids.length;i++)
	           {         
	               $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'role') +' '+$(this).getCell(ids[i], 'assessmentProgram')+ ' Check Box');
	           }
	           var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	          
	           $.each(objs, function(index, value) {       	  
	        		   
	             var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                   $(value).attr('title',$(nm).text()+' filter');
	        	   	                   });
	           var radioButtons= $( '#gbox_'+tableid).find('[name^=DefaultRole_'+tableid+']');
	           $.each(radioButtons, function(index, value) {
	        	   $(value).attr('title',tableid+' row '+(index+1)+' Default Role Radio button');
	           });
		$('#'+rolesGridId).jqGrid('setGridParam', {}).trigger('reloadGrid');
	});
	
}

function enableButton(buttonElementId){
	$('#'+buttonElementId).removeAttr('disabled');
	$('#'+buttonElementId).addClass("btn_blue");
	$('#'+buttonElementId).removeClass("btn_disabled");
}

function populateFilterValuesUserCommon(filterValues,filterId){	
var filterValuesArray=filterValues.split(';');
$('#gs_'+filterId).find('option').remove();
filterValuesArray.forEach(function(v) {
		  v = v.split(':');
		  $('#gs_'+filterId).append($('<option></option>').val(v[0]).html(v[1]));
		});	
}

function disableButton(buttonElementId){
	$('#'+buttonElementId).attr('disabled', 'disabled');
	$('#'+buttonElementId).removeClass("btn_blue");
	$('#'+buttonElementId).addClass("btn_disabled");
}
$(document).on('input[name="DefaultRole_addUserRolesGrid"],input[name="DefaultRole_editUserRolesGrid"],input[name="DefaultRole_mergeUserToKeepGrid1"]','click',function(ev){
	var row=$(ev.target).closest('tr.jqgrow');
	var rowid=row.attr('id');
	
	var userGridData = [];
	if(screen === ''){
		userGridData = $("#addUserRolesGrid").jqGrid('getGridParam','data');
	} else if(screen === 'e'){
		userGridData = $("#editUserRolesGrid").jqGrid('getGridParam','data');
	} else if(screen === 'm'){
		userGridData = $("#mergeUserToKeepGrid1").jqGrid('getGridParam','data');
	}
	var rowData={};
	$.each(userGridData,function(i,obj){
		if(obj.id==rowid){
			rowData= userGridData[i];
		}else{
			userGridData[i].Default=false;
		}
	});
	rowData.Default=$(this).prop('checked');
	
});
function enableDisableButton(buttonElementId, stateElementId, apElementId, roleElementId, 
	districtElementId, schoolElementId, regionElementId){
	var stateSelected = $('#'+stateElementId).val();
	var apSelected = $('#'+apElementId).val();
	var roleSelected = $('#'+roleElementId).val();
	var districtSelected = $('#'+districtElementId).val();
	var schoolSelected = $('#'+schoolElementId).val();
	var regionSelected = $('#'+regionElementId).val();
	if(selectedRoleOrgTypeId === 7 && stateSelected !== undefined && apSelected !== undefined && roleSelected !== undefined &&
		districtSelected !== undefined && schoolSelected !== undefined &&
		stateSelected !== '' && apSelected !== '' && roleSelected !== '' && (districtSelected !== '' && districtSelected != 0 && districtSelected !=null) && (schoolSelected !== '' && schoolSelected != 0 && schoolSelected != null)){
		enableButton(buttonElementId);
	} else if(selectedRoleOrgTypeId === 5 && stateSelected !== undefined && apSelected !== undefined && roleSelected !== undefined &&
		districtSelected !== undefined &&
		stateSelected !== '' && apSelected !== '' && roleSelected !== '' && (districtSelected !== '' && districtSelected != 0 && districtSelected !=null)){
		enableButton(buttonElementId);
	} else if(selectedRoleOrgTypeId === 3 && stateSelected !== undefined && apSelected !== undefined && roleSelected !== undefined &&
		stateSelected !== '' && apSelected !== '' && roleSelected !== '' && (regionSelected !== '' && regionSelected != '' && regionSelected!=null)){
		enableButton(buttonElementId);
	}  else if(selectedRoleOrgTypeId === 2 && stateSelected !== undefined && apSelected !== undefined && roleSelected !== undefined &&
		stateSelected !== '' && apSelected !== '' && roleSelected !== ''){
		enableButton(buttonElementId);
	} else if(selectedRoleOrgTypeId === 0 && stateSelected !== undefined && apSelected !== undefined && roleSelected !== undefined &&
		stateSelected !== '' && apSelected !== '' && roleSelected !== ''){
		enableButton(buttonElementId);
	} else {
		disableButton(buttonElementId);
	}
}

function isEmpty(data){
	if(data == undefined || data == null || data.length == 0 ){
		return true;
	}
}

function isEmptyArr(data){
	if(data == undefined || data == null || data.length == 0 ){
		return true;
	}
}

/**
 * Email Validaation
 */
function validateEmail(email) { 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-\_0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
} 

/**
 * Validation method to be fired before a user data is saved.
 */
function validateUser(currentUser, screen) {
	var teacherId = groupsNameMap['Teacher'];
	var validData = true;
	var message = msg.label_required_fields;

	if(isEmpty(currentUser.firstName.trim())){
		validData = false;
		message = message + msg.validation_first_name;
	}
	
	if(isEmpty(currentUser.lastName.trim())){
		validData = false;
		message = message + msg.validation_last_name;
	}
	
	if(isEmpty(currentUser.email)){
		validData = false;
		message = message + msg.validation_email;
	} else if(!isEmpty(currentUser.email)){
		if(!validateEmail(currentUser.email)){
			validData = false;
			message = message + msg.validation_valid_email;
		}
	}
	
	var organizations = currentUser.organizations;
	if(isEmptyArr(organizations)){
		validData = false;
		/*message = message + msg.validation_default_org;*/
	} else if(!isEmptyArr(organizations)){
		var educatorIdentifierErrorMsg=true;
		$(organizations).each(function( i, organizationDetails ) {
			var roles = organizationDetails.rolesIds;
			if(roles==teacherId){				
				if(isEmpty(currentUser.educatorIdentifier.trim())){
					validData = false;
					if(educatorIdentifierErrorMsg==true){
					message = message + msg.validation_educator_identifier;
					educatorIdentifierErrorMsg = false;
					}
				}
			}
			if(isEmptyArr(roles)){
				validData = false;
				message = message + msg.validation_atleast_one_role;
			}
			
			if((organizationDetails.organizationId == null && organizationDetails.organizationId !=-999 ) || (organizationDetails.organizationId == 0 && organizationDetails.organizationId !=-999)){
				validData = false;
				message = message + msg.validation_all_org + " in " + (i+1) + " row. Select correct organization type for the role.";
			}
			
			if(organizationDetails.assessmentProgramId == null || organizationDetails.assessmentProgramId == 0){
				validData = false;
				message = message +"<br/>- Role and assessmentprogram mismatch" + " in " + (i+1) + " row. Select correct assessmentprogram type for the role.";
			}
		});
	}
	
	if(isEmpty(currentUser.defaultOrgId)){
		validData = false;
		/*message = message + msg.validation_default_org;*/
	}
	//added during US16351-for dopdown validation
	if(isEmpty(currentUser.defaultAssessmentProgramId)){
		validData = false;
		/*message = message +  msg.validation_atleast_one_assessmentprogram;*/
	}
	
	if(isEmpty(currentUser.defaultAssessmentProgramId)){
		validData = false;
		message = message +  "<br/>- One role must be selected as default.";
	}
	
	if(!validData){
		$('#'+screen+'UserrequiredMessage').html(message);
	}
	return validData;
}

/**
 * This method is called to save user details it will be called either during creation or update process.
 * In case of updation there will not be any confirmation for duplicate user rather will be auto updated.
 * In case of creation, if duplicate user exists, upon user confirmation the data will be modified.
 * @param e
 * @param screen
 */
function callSaveUserClick(e, screen, isAdminOperation){
	debugger;
	aart.clearMessages();
	$('#'+screen+'duplicateUserError').html('');
	$('#'+screen+'duplicateUserError').hide();
	$('#'+screen+'idOrgUserError').html('');
	$('#'+screen+'idOrgUserError').hide();
	$('#'+screen+'UserARTSmessages').hide();
	$('#'+screen+'InvalidOrgRoleMessage').html('');
	$('#'+screen+'InvalidOrganizationRoleMessage').html('');
	var proceed = true;
	var inValidOrgRole ="";
	var users = [];
	var currentUser = {};
	currentUser.educatorIdentifier = $('#'+screen+'educatorIdentifier').val().trim();
	currentUser.firstName = $('#'+screen+'firstName').val();
	currentUser.lastName = $('#'+screen+'lastName').val();
	currentUser.email = $('#'+screen+'emailAddress').val();
	currentUser.id = $('#userId').val();
	if(screen != ''){
		currentUser.action = "UPDATE";
	}else{
		currentUser.action = "INSERT";
	}
	
	var organizations = [];
	var validateUserRoles = [];
	var userGridData = [];
	var validateUserRolesArray=[];
	if(screen === ''){
		userGridData = $("#addUserRolesGrid").jqGrid('getGridParam','data');
	} else {
		userGridData = $("#editUserRolesGrid").jqGrid('getGridParam','data');
	}
	$(userGridData).each(function( i, row ) {
		var roles = [];
		var orgId;
		var organizationDetails = {};
		var orgTypeId = groupMap[row.role];
		if(orgTypeId == 1){
			orgId = row.state;
		}
		else if(orgTypeId == 2){
			orgId = row.state;
		} else if(orgTypeId == 3){
			orgId = row.region;
		} else if(orgTypeId == 5){
			orgId = row.district;
		} else if(orgTypeId == 7){
			orgId = row.school;
		}
		
		organizationDetails.organizationId = orgId;
		if(row.Default){
			organizationDetails.defaultRoleId = row.role;
			currentUser.defaultOrgId =  orgId;
			currentUser.defaultAssessmentProgramId = row.assessmentProgram;
		}
		organizationDetails.assessmentProgramId = row.assessmentProgram;
		organizationDetails.state=row.state;
		roles.push(row.role);

		var userRole = {orgId: Number(orgId), assessmentProgram: Number(row.assessmentProgram), role: Number(row.role)};
		if(userRole != null && jQuery.inArray( JSON.stringify(userRole), validateUserRoles ) >= 0){
			proceed = false;
			return false;
		}
		validateUserRoles.push(JSON.stringify(userRole));
		
		var teacherRoleId = groupsNameMap['Teacher'];
		var teacherPNPRoleId = groupsNameMap['Teacher: PNP Read Only'];
		if((Number(row.role) ===  Number(teacherRoleId)) || (Number(row.role) ===  Number(teacherPNPRoleId)))
		{
			var teacherRoleCheck = {orgId: Number(orgId), assessmentProgram: Number(row.assessmentProgram)};
			if(teacherRoleCheck != null && jQuery.inArray( JSON.stringify(teacherRoleCheck), validateUserRolesArray ) >= 0)
			{
				proceed = false;
				inValidTeacherRole = inValidTeacherRole  + " Both Teacher and Teacher: PNP Read Only Role is not allowed. <br>" ;
				return false;
			}
			validateUserRolesArray.push(JSON.stringify(teacherRoleCheck));
		}
		organizationDetails.rolesIds = roles;
		
		organizations.push(organizationDetails);
	});
	currentUser.organizations = organizations;
	users.push(currentUser);
	if(!validateUser(currentUser, screen)){
		$('#'+screen+'UserARTSmessages').show();
		$('#'+screen+'UserrequiredMessage').show();
		return false;
	}
	
	if(proceed){
		
		if(validateUser(currentUser, screen)){
			var usersParameter = users.length > 0 ? $.toJSON(users) : '';
			var overwriteflag = false;
			var errorCodeExists = false;
			if(screen == 'e'){
				overwriteflag = true;					
			}
			
			$('#btn_addUser').prop("disabled",true);
			$('#btn_addUser').addClass('ui-state-disabled');
			
			$.ajax({
		         url: 'saveUser.htm',
		         data: '&users=' + encodeURIComponent(usersParameter)+'&overwrite='+overwriteflag+'&userId='+currentUser.userId+'&isAdminOperation='+isAdminOperation,
		         dataType: "json",
		         type: 'POST',
		         beforeSend: function() { 
		             $('#btn_topSaveUser, #btn_bottomSaveUser').prop('disabled', true); // disable button
		           }
		     }).done(function(data){
	        	 $('#'+screen+'duplicateUserError').hide();
	        	 $('#'+screen+'InvalidOrgRoleMessage').hide();
	        	 $('#'+screen+'idOrgUserError').hide();
				userRolesModefied = false;
				isErrorCodeExists = false;
	         	if(data == null){
	         		$('#'+screen+'UserARTSmessages').show();
        			$('#'+screen+'UserfailMessage').show();
	         	} else {
	         		var user = data.user;
	         		if(user!=null && user != undefined){
	         			if(user.errorCode != undefined && user.errorCode.trim().length > 0){
	         				isErrorCodeExists = true;
	         				if(user.errorCode.indexOf('ONE_DTC_USER_EXISTS') >= 0){
	         					if(user.errorCode.indexOf('ONE_DTC_USER_EXISTS') >=0){
		         					$('#'+screen+'duplicateUserError').html(msg.error_dtc_singleuser_restricted).show();	
			         			}else{
			         				$('#'+screen+'duplicateUserError').hide();
			         			}
	         				} else if(user.errorCode.indexOf('ONE_BTC_USER_EXISTS') >= 0){
	         					if(user.errorCode.indexOf('ONE_BTC_USER_EXISTS') >=0){
		         					$('#'+screen+'duplicateUserError').html(msg.error_btc_singleuser_restricted).show();	
			         			}else{
			         				$('#'+screen+'duplicateUserError').hide();
			         			}
	         				} else if(user.errorCode.indexOf('USER_EXISTS') >=0 
	         					|| user.errorCode.indexOf('USER_ORG_EXISTS_') >=0){
	         					
		         				if(user.errorCode.indexOf('USER_EXISTS') >=0){
		         					$('#'+screen+'duplicateUserError').html(msg.error_duplicate_user_emailaddr).show();					        							         					
			         			}
		         				if(user.errorCode.indexOf('USER_ORG_EXISTS_') >=0){
		         					
		         					var message=msg.error_user_create_eduorgexists;
		         					message = message.replace('_ORG_', user.errorCode.substring(user.errorCode.indexOf('USER_ORG_EXISTS_') + 16));
				        			$('#'+screen+'idOrgUserError').html(message).show();
				        			isErrorCodeExists = true;
			         			}
		         				
	         				}else if(user.errorCode.indexOf('EDUCATOR_IDENTIFIER_NOT_NULL') >=0){
		         					$('#'+screen+'duplicateUserError').html(msg.error_educator_identifier_not_null).show();				         				
	         				}else{			         				
		         				$('#'+screen+'UserARTSmessages').show();
			        			$('#'+screen+'UserfailMessage').show();
		         			}
	         			} else {
	         				if(screen == 'e'){
	         					$('#editUsers').dialog('close');
	         					if(currentUser.id==$('#currentUserIdFromHeader').val()){
	         						$('#UsersuccessMessage').html(msg.label_sameuser_modify_sucess).show();
	         					}
	         					else{
	         						$('#UsersuccessMessage').html(msg.label_user_modify_success).show();
	         					}
 				         		$("#viewUserGridTableId").trigger("reloadGrid");	
	         				} else{
	         					$('#UsersuccessMessage').show();
	         					$("#usersSelect").val("");
		         				$("#usersSelect").trigger('change.select2');
		         						         				}			         				
	         				$('#'+screen+'UserARTSmessages').show();
	         			}
	         		} else{
	         			$('#'+screen+'UserARTSmessages').show();
	        			$('#'+screen+'UserfailMessage').show();
	         		}
	         	}
	         	
	         	$('#btn_addUser').prop("disabled",false);
				$('#btn_addUser').removeClass('ui-state-disabled');
				
				if(!isErrorCodeExists){
						$('#firstName').val('');
						$('#lastName').val('');
						$('#emailAddress').val('');
						$('#educatorIdentifier').val('');
						
						//clear all drop down values too
						$('#addUserOrganization').val('').change();
						$('#addUserAssessmentProgram').val('').change();
						$('#addUserGroup').val('').change();
						
						$("#addUserRolesGrid").jqGrid("clearGridData");
					}
				
				$('#btn_topSaveUser, #btn_bottomSaveUser').prop('disabled', false);
	         }).fail(function(jqXHR, textStatus, errorThrown){
		        	$('#btn_addUser').prop("disabled",false);
		 			$('#btn_addUser').removeClass('ui-state-disabled');
		         } );
		} else{
			$('#'+screen+'UserARTSmessages').show();
			$('#'+screen+'UserrequiredMessage').show();
		}	
	} else{
		if(!proceed){
			if(inValidTeacherRole !== ''){
				$('#'+screen+'InvalidOrganizationRoleMessage').html(inValidTeacherRole).show();
			} else {
				$('#'+screen+'InvalidOrganizationRoleMessage').html('Do not choose the same role and organization twice.').show();
			}
		}
	}
	
}