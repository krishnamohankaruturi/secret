 $(function(){	
	$('#userNav li.nav-item:first a').tab('show');	
	 $('li.get-users').on('click',function(e){		 
	    	var clickedURL = $(this).find("a").attr('href');     
	    	usersInitNew(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });   	
	 var usersItemMenu = $('#userNav li.nav-item:first a');
	  if(usersItemMenu.length>0){
	   var clickedURL = usersItemMenu.attr('href');     
	   usersInitNew(clickedURL.substring(1, clickedURL.length));
	  }	    
}); 
	function usersInitNew(action){	
	// SIGN IN	
	//$('#usersSelect').val("");	
/*	$("#usersSelect").change(function() {*/
		//var action = $(this).find(':selected').val();
		var rolesAllowedForUserAdd = '';
		var apsAllowedForUserAdd = '';
		var userAllowedRoles='';
		var statesAllowedForUserAdd = '';
		var allowedRegionsForUserAdd = '';
		var allowedDistrictsForUserAdd = '';
		var allowedSchoolsForUserAdd = '';
		debugger;
		if (action == "viewUsers"){
			screen = 'e';
			currentMode = 'e';
			isMergeScreen = false;
			// Clear previous role selections if any
			addUserRolesGridData = [];
			editUserRolesGridData = [];
			userGridsReset();
			$('#editUserRolesGrid').jqGrid('clearGridData', true).trigger('reloadGrid');
			if(!view_User_Select_Option_Loadonce){
				view_Init_User_tab();
				view_User_Select_Option_Loadonce = true;
			}			
			aart.clearMessages();
			$('#breadCrumMessage').text(lConfiguserview);
			//$('#viewUserOrgFilter').orgFilter('reset');
			/*$("#viewUserGridTableId")[0].clearToolbar();*/
			$("#viewUserGridTableId").jqGrid('clearGridData');
			/*if($("#viewUserGridTableId")[0].grid && $("#viewUserGridTableId")[0]['clearToolbar']){
				$("#viewUserGridTableId")[0].clearToolbar();
			} */
			$grid = $("#viewUserGridTableId");
			var myStopReload = function () {
	            $grid.off("jqGridToolbarBeforeClear", myStopReload);
	            return "stop"; // stop reload
	        };
	        $grid.on("jqGridToolbarBeforeClear", myStopReload);
	        if ($grid[0].ftoolbar) {
	        	$grid[0].clearToolbar();
	        }
			//$("#viewUserGridTableId").trigger('destroy');
			//$('#viewUserOrgFilter_district').select2('uncheckAll');
			$('#viewUserOrgFilter_district').val('').trigger('change.select2');
			//$('#viewUserOrgFilter_school').select2('uncheckAll');
			$('#viewUserOrgFilter_school').val('').trigger('change.select2');
			$("label.error").html('');
			$("#viewUsers").show();
			$("#uploadUsers").hide();
			$("#addUsers").hide();
			$("#findUsers").hide();
			$("#mergeUsers").hide();
			$("#moveUsers").hide();
			$("#specialUsers").hide();
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
			$("#btn_editTop").show();
			$("#btn_editBottom").show();
			$("#mergeUserSpan").hide();	
			$("#uploadPDTrainingResults").hide();
			$("#eInvalidOrganizationRoleMessage").hide();
			$("#eInvalidOrganizationRoleMessage").html('');			
		}else if (action=="specialUsers"){
			debugger;
			if(!special_User_Select_Option_Loadonce){
				special_Init_User_tab();
				special_User_Select_Option_Loadonce = true;
			}
			aart.clearMessages();
			$('#breadCrumMessage').text(lConfiguserview);
			//$('#viewUserOrgFilter').orgFilter('reset');
			userGridsReset();
			
			/*if($("#viewUserGridTableId")[0].grid && $("#viewUserGridTableId")[0]['clearToolbar']){
				$("#viewUserGridTableId")[0].clearToolbar();
			} */
			$grid = $("#specialUserGridTableId");
			var myStopReload = function () {
	            $grid.off("jqGridToolbarBeforeClear", myStopReload);
	            return "stop"; // stop reload
	        };
	        $grid.on("jqGridToolbarBeforeClear", myStopReload);
	        if ($grid[0].ftoolbar) {
	        	$grid[0].clearToolbar();
	        }
			//$('#moveUserOrgFilter_district').select2('uncheckAll');
			$('#specialUserOrgFilter_district').val('').trigger('change.select2');
			//$('#moveUserOrgFilter_school').select2('uncheckAll');
			$('#specialUserOrgFilter_district').val('').trigger('change.select2');
			$("label.error").html('');
			$("#viewUsers").hide();
			$("#uploadUsers").hide();
			$("#findUsers").hide();
			$("#addUsers").hide();
			$("#mergeUsers").hide();
			$("#moveUsers").hide();
			$("#specialUsers").show();
			$("#moveUserOrganizationInfo").hide();
			$("#mergeUserSpan").hide();	
			$("#uploadPDTrainingResults").hide();
			$("#specialUserNextButton").prop("disabled",true);
			$('#specialUserNextButton').addClass('ui-state-disabled');	
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
			}
		else if (action == "moveUsers"){
			if(!move_User_Select_Option_Loadonce){
				move_Init_User_tab();
				move_User_Select_Option_Loadonce = true;
			}
			aart.clearMessages();
			$('#breadCrumMessage').text(lConfiguserview);
			//$('#viewUserOrgFilter').orgFilter('reset');
			userGridsReset();
			
			/*if($("#viewUserGridTableId")[0].grid && $("#viewUserGridTableId")[0]['clearToolbar']){
				$("#viewUserGridTableId")[0].clearToolbar();
			} */
			$grid = $("#moveUserGridTableId");
			var myStopReload = function () {
	            $grid.off("jqGridToolbarBeforeClear", myStopReload);
	            return "stop"; // stop reload
	        };
	        $grid.on("jqGridToolbarBeforeClear", myStopReload);
	        if ($grid[0].ftoolbar) {
	        	$grid[0].clearToolbar();
	        }
			//$('#moveUserOrgFilter_district').select2('uncheckAll');
			$('#moveUserOrgFilter_district').val('').trigger('change.select2');
			//$('#moveUserOrgFilter_school').select2('uncheckAll');
			$('#moveUserOrgFilter_district').val('').trigger('change.select2');
			$("label.error").html('');
			$("#viewUsers").hide();
			$("#uploadUsers").hide();
			$("#findUsers").hide();
			$("#addUsers").hide();
			$("#mergeUsers").hide();
			$("#specialUsers").hide();
			$("#moveUsers").show();
			$("#moveUserOrganizationInfo").hide();
			$("#mergeUserSpan").hide();	
			$("#uploadPDTrainingResults").hide();
			$("#moveUserNextButton").prop("disabled",true);
			$('#moveUserNextButton').addClass('ui-state-disabled');	
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
		}else if (action == "mergeUsers"){
			screen = 'm'; 
			currentMode = 'm';
			isMergeScreen = true;
			if(!merge_User_Select_Option_Loadonce){
				merge_Init_User_tab();
				merge_User_Select_Option_Loadonce = true;
			}
			aart.clearMessages();
			mergeUserRolesGridData = [];
			addUserRolesGridData = [];
			editUserRolesGridData = [];
			$('#breadCrumMessage').text(lConfigUserMerge);
			$('#mergeUserOrgFilter').orgFilter('reset');
			userGridsReset();
			/*if($("#mergeUserGridTableId")[0].grid && $("#mergeUserGridTableId")[0]['clearToolbar']){
				$("#mergeUserGridTableId")[0].clearToolbar();
			}*/
			$grid = $("#mergeUserGridTableId");
			var myStopReload = function () {
	            $grid.off("jqGridToolbarBeforeClear", myStopReload);
	            return "stop"; // stop reload
	        };
	        $grid.on("jqGridToolbarBeforeClear", myStopReload);
	        if ($grid[0].ftoolbar) {
	        	$grid[0].clearToolbar();
	        }
			$("label.error").html('');
			$("#mergeUsers").show();
			$("#mergeUserSpan").show();
			$("#viewUsers").hide();
			$("#uploadUsers").hide();
			$("#moveUsers").hide();
			$("#findUsers").hide();
			$("#specialUsers").hide();
			$("#addUsers").hide();
			$("#mergeUserNextButton").prop("disabled",true);
			$('#mergeUserNextButton').addClass('ui-state-disabled');
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
			selectedUserArr = [];
			$("#uploadPDTrainingResults").hide();
		}else if(action == "uploadUsers"){
			if(!upload_User_Select_Option_Loadonce){
				upload_Init_User_Tab();				
				upload_User_Select_Option_Loadonce = true;
			}
			aart.clearMessages();
			$('#breadCrumMessage').text(lConfigUserupload);
			$("#viewUsers").hide();
			$('#uploadUserOrgFilter').orgFilter('reset');
			$('#uploadUserFilterForm')[0].reset();
			$("#uploadUserGridTableId").jqGrid('clearGridData');
			userGridsReset();
			$("#userUploadReport").html('');
			$("label.error").html('');
			$("#uploadUsers").show();
			$("#addUsers").hide();
			$("#findUsers").hide();
			$("#moveUsers").hide();
			$("#mergeUsers").hide();
			$("#specialUsers").hide();
			$("#mergeUserSpan").hide();
			$("#uploadPDTrainingResults").hide();
			$("#User_TemplatedownloadquickHelpPopup").hide();
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
			showUserUploadGrid();
		}else if(action == "uploadPDTrainingResults"){
			aart.clearMessages();
			$('#breadCrumMessage').text(lConfigUserUploadPDResults);
			$("#findUsers").hide();
			$("#viewUsers").hide();
			$("#uploadUsers").hide();
			$("#addUsers").hide();
			$("#moveUsers").hide();
			$("#mergeUsers").hide();
			$("#specialUsers").hide();
			$("#mergeUserSpan").hide();
			$("#uploadPDTrainingResults").show();
			$("#recordsRejectedCount").html("");
			$("#recordsSkippedCount").html("");
	    	$("#uploadFileStatus").html("");
	    	$("#recordsUpdatedCount").html("");
	    	$("#recordsCreatedCount").html("");
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
		}else if(action == "addusers"){
			screen = '';
			currentMode = '';
			isMergeScreen = false;
			// Clear previous role selections if any
			addUserRolesGridData = [];
			editUserRolesGridData = [];
			userGridsReset();
			$('#addUserRolesGrid').jqGrid('clearGridData', true).trigger('reloadGrid');
			var cm = $('#addUserRolesGrid').jqGrid('getGridParam','colModel');
			var stateFilterId =$(_.findWhere(cm, {name: 'state'})).attr('label');
			$('#gs_'+stateFilterId).find('option').remove();
			var apFilterId =$(_.findWhere(cm, {name: 'assessmentProgram'})).attr('label');
			$('#gs_'+apFilterId).find('option').remove();
			var roleFilterId =$(_.findWhere(cm, {name: 'role'})).attr('label');
			$('#gs_'+roleFilterId).find('option').remove();
			var districtFilterId =$(_.findWhere(cm, {name: 'district'})).attr('label');
			$('#gs_'+districtFilterId).find('option').remove();
			var schoolFilterId =$(_.findWhere(cm, {name: 'school'})).attr('label');
			$('#gs_'+schoolFilterId).find('option').remove();
			$('#addUserOrganization').select2({
	 			placeholder:'Select', 
	 			multiple: false,
	 			allowClear : true
	 		});
			$('#addUserOrganization').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			getStatesOrgsForUser('addUserOrganization',true);
			$('#addUserAssessmentProgram').select2({
	 			placeholder:'Select', 
	 			multiple: false,
	 			allowClear : true
	 		})
			$('#addUserAssessmentProgram').val('').trigger('change.select2');
			$('#addUserGroup').select2({
	 			placeholder:'Select', 
	 			multiple: false,
	 			allowClear : true
	 		});
			$('#addUserGroup').val('').trigger('change.select2');
			$('#addUserRegion').select2({
	 			placeholder:'Select', 
	 			multiple: false,
	 			allowClear : true
	 		});
			$('#addUserRegion').val('').trigger('change.select2');
			$('#addUserDistrict').select2({
	 			placeholder:'Select', 
	 			multiple: false,
	 			allowClear : true
	 		});
			$('#addUserDistrict').val('').trigger('change.select2');
			$('#addUserSchool').select2({
	 			placeholder:'Select', 
	 			multiple: false,
	 			allowClear : true
	 		});
			$('#addUserSchool').val('').trigger('change.select2');
			// Clear all error/notification messages.
			aart.clearMessages();
			//Reset all data fields upon revisit.
			$('#addRoleDistrictContainer').hide();
			$('#addRoleSchoolContainer').hide();
			$( "#users-contain input" ).val('');
			$("label.error").html('');
			$('#breadCrumMessage').text(lConfigUserCreate);
			$("#viewUsers").hide();
			$("#moveUsers").hide();
			$("#specialUsers").hide();
			$("#uploadUsers").hide();
			$("#uploadPDTrainingResults").hide();
			$("#addUsers").show();
			$('span[id^="educatorIdentifierAsterisk"]').remove();
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
			$("#findUsers").hide();
			$("#duplicateUserError").hide();
			$("#InvalidOrgRoleMessage").hide();
			$("#idOrgUserError").hide();
			$("#mergeUsers").hide();
			$("#mergeUserSpan").hide();
			$("#InvalidOrganizationRoleMessage").hide();
			$("#InvalidOrganizationRoleMessage").html('');
		}else if(action == "findUsers"){
			if(!find_User_Select_Option_Loadonce){
				find_Init_User_tab();
				find_User_Select_Option_Loadonce = true;
			}
			aart.clearMessages();
			editUserRolesGridData = [];
			userGridsReset();
			$('#fName').val('');
			$('#lName').val('');
			$('#educatorId').val('');
			$("#findUsers").show();
			$("#uploadUsers").hide();
			$("#viewUsers").hide();
			$("#addUsers").hide();
			$("#moveUsers").hide();
			$("#mergeUsers").hide();
			$("#specialUsers").hide();
			$("#mergeUserSpan").hide();
			$("#uploadPDTrainingResults").hide();
			$("#btn_editTop_Find").show();
			$("#btn_editBottom_Find").show();
			$("#btn_editTop").hide();
			$("#btn_editBottom").hide();
		}else{
			aart.clearMessages();
			$("#findUsers").hide();
			$("#uploadUsers").hide();
			$("#viewUsers").hide();
			$("#addUsers").hide();
			$("#moveUsers").hide();
			$("#btn_editTop_Find").hide();
			$("#btn_editBottom_Find").hide();
			$("#mergeUsers").hide();
			$("#mergeUserSpan").hide();
			$("#uploadPDTrainingResults").hide();
			$("#specialUsers").hide();
		}
	 /* }).trigger( "change" ); */
}

function userGridsReset(){
	$('#editUserRolesGrid').jqGrid('clearGridData');
	$("#moveUserGridTableId").jqGrid('clearGridData');
	$("#moveUserOrganizationInfoGridTableId").jqGrid('clearGridData');
	$("#mergeUserGridTableId").jqGrid('clearGridData');
	$('#addUserRolesGrid').jqGrid('clearGridData');
	$('#findUserGridTableId').jqGrid('clearGridData');
	$("#viewUserGridTableId").jqGrid('clearGridData');
}

function resetUser(){
	$("#usersSelect").select2();
	var options = sortDropdownOptions($("#usersSelect option"));
	options.appendTo("#usersSelect");
	
	$('#usersSelect').val("").trigger('change.select2');	
	aart.clearMessages();
	$("#uploadUsers").hide();
	$("#viewUsers").hide();
	$("#addUsers").hide();
	$("#moveUsers").hide();
	$("#mergeUsers").hide();
	$("#mergeUserSpan").hide();
	$("#uploadPDTrainingResults").hide();
}