var isUsersLimitedToOnePerRole = false;
var editOption = false;
var allowRequest=false;
var changedPermissions =[];
function resetRoles() {
	$("#rolesActionSelect").select2({placeholder:'Select'});
	var options = sortDropdownOptions($("#rolesActionSelect option"));
	options.appendTo("#rolesActionSelect");	

	$('#rolesActionSelect').val("").trigger('change.select2');
	$("#viewRoles").hide();
	$("#tab").hide();
}
function savePermissions(){
	// collect all inputs that have selected
	changedPermissions =[];
	var groupId = $('#groupId').val();
	if (editOption) {
		var organizationId = $('#stateOrgsEditRole').val();
		var assessmentProgramId = $('#assessmentProgramsOrgsEditRole').val();
	} else {
		var organizationId = $('#stateOrgsViewRole').val();
		var assessmentProgramId = $('#assessmentProgramsOrgsViewRole').val();
	}

	var authorities = $('input:checkbox:checked.authCheckbox').map(function() {
		return this.value;
	}).get();
	// call save method
	$.ajax({
		url : 'saveRolePermissions.htm',
		data : {
			groupId : groupId,
			organizationId : organizationId,
			assessmentProgramId : assessmentProgramId,
			authorities : function() {
				return authorities;
			}
		},
		dataType : 'json',
		type : 'POST'		
	}).done(function(status) {
		// display message the permisssions were saved
		if (status) {
			$('#successDialog').dialog({
				modal : true,
				title : 'Confirmation',
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}	
				}
			});
		}
	});
}
function rolesInitMethod(action) {

	/*$("#rolesActionSelect").change(function() {*/
		//var action = $(this).val();
		$("#tab").hide();		
		if (action == "viewRoles") {
			$("#viewRolesOrgFilterForm").removeClass("hidden");
			editOption = false;
			$('viewRolesConfigTableId').jqGrid('clearGridData');
			$("#tab").show();
			$("#viewRolesOrgFilterForm").show();
			$("#editRolesOrgFilterForm").hide();
			$("#viewRolesConfigTableId").hide();
			populateOrgsViewRoles();
			populateViewRolesAssessmentProgram();
			if (!gViewRolesLoadOnce) {
				$('#viewRolesOrgFilterForm').validate().resetForm();
				loadRolesGrid();
				getAllRoles();
			}
			viewRolesReadOnly = true;
			$("#viewRoles").hide();
			$("#viewRoles").show();
		} else if (action == "editRoles") {
			$("#editRolesOrgFilterForm").removeClass("hidden");
			editOption = true;
			$('viewRolesConfigTableId').jqGrid('clearGridData');
			$("#viewRolesConfigTableId").hide();
			$("#tab").show();
			$("#viewRolesOrgFilterForm").hide();
			$("#editRolesOrgFilterForm").show();
			populateViewRolesAssessmentProgram();
			multiselect();
			if (!gEditRolesLoadOnce) {
				$('#viewRolesOrgFilterForm').validate().resetForm();
				loadRolesGrid();
				getAllRoles();
			}

			viewRolesReadOnly = false;
			$("#viewRoles").hide();

		} else if (action == "createRoles") {
			editOption = false;
			$("#viewRoles").hide();
			$("#viewRolesOrgFilterForm").hide();
			$("#editRolesOrgFilterForm").hide();

		} else {
			editOption = false;
			$("#viewRoles").hide();
		}
		multiselect();
	/*}).trigger("change");*/

	$("#assessmentProgramsOrgsEditRole").on("change",function() {
		var assesmentProgramId = $('#assessmentProgramsOrgsEditRole').val();
		$('#stateOrgsEditRole').html("");
		if (editOption == true && assesmentProgramId != null) {
			var getData = new Object();
			getData['assesmentProgramList[]'] = assesmentProgramId;
			getData['editCall'] = editOption;
			var clrStateOptionText = '';
			var stateOrgSelect = $('#stateOrgsEditRole');
			$.ajax({
				url : 'getStatesByAssesmentPrograms.htm',
				data : getData,
				dataType : 'json',
				type : "GET"		
			}).done(function(states) {
				if (states !== undefined && states !== null && states.length > 0) {
					$.each(states, function(i, stateOrg) {
						clrStateOptionText = states[i].organizationName;
						stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(clrStateOptionText));
					});
					if (states.length == 1) {
						stateOrgSelect.find('option:first').prop('selected',false).next('option').attr('selected', 'selected');
					}
					filteringOrganization($('#stateOrgsEditRole'));

					}

					$('#stateOrgsEditRole').val('').trigger('change.select2');

			});
		}
		$('#stateOrgsEditRole_selectAll').attr("checked", false);
				
	});

	$("#stateOrgsViewRole").on("change",function() {
		var organizationId = $('#stateOrgsViewRole').val();
		if ((organizationId != null && organizationId != "") || (editOption == true && organizationId != null)) {
			var postData = new Object();
			if (editOption) {
				postData['organizationIdList'] = organizationId;
				postData['isEdit'] = editOption;
			} else {
				postData['organizationId'] = organizationId;
			}
			$.ajax({
				url : 'getAssessmentProgramIdByOrganizationRoles.htm',
				data : postData,
				dataType : 'json',
				type : "POST"				
			}).done(function(assessmentProgramsByOrg) {
				var clrOptionText = '';
				$('#assessmentProgramsOrgsViewRole').html("");
				if (!editOption)
					$('#assessmentProgramsOrgsViewRole').append($('<option></option>').val("").html("Select"));

				$.each(assessmentProgramsByOrg, function(i, clrAssessmentProgram) {
					clrOptionText = assessmentProgramsByOrg[i].abbreviatedname;
					$('#assessmentProgramsOrgsViewRole').append($('<option></option>').val(clrAssessmentProgram.id).html(clrOptionText));
				});
				if (assessmentProgramsByOrg.length == 1) {
					$('#assessmentProgramsOrgsViewRole').find('option:first').prop('selected',false).next('option').attr('selected', 'selected');
					$('#assessmentProgramsOrgsViewRole').trigger('change');
				}
				$('#assessmentProgramsOrgsViewRole').trigger('change.select2');
			});
		}
	});
	function multiselect() {
		if (editOption) {
			$('#stateOrgsEditRole, #assessmentProgramsOrgsEditRole').select2({
				placeholder : 'Select',
				multiple : true
			});

		} else {
			$('#stateOrgsViewRole, #assessmentProgramsOrgsViewRole').select2({
				placeholder : 'Select',
				multiple : false
			});
		}
	}

	$('#viewRolesOrgFilterForm').validate({
		ignore : ""
	});

	$('#searchRolesButton').on("click",function(event) {
		if (editOption) {
			if ($('#stateOrgsEditRole').val() != '' && $('#assessmentProgramsOrgsEditRole').val() != '') {
				$("#viewRolesConfigTableId").show();
				$("#viewRoles").show();
				getAllRoles();
				allowRequest=true;
			} else if ($('#stateOrgsEditRole').val() == '') {
				alert("Please select one or more states")
			} else if ($('#assessmentProgramsOrgsEditRole').val() == '') {
				alert("Please select one or more assesment programs")
			}
		} else {
			if ($('#viewRolesOrgFilterForm').valid()) {
				$("#viewRolesConfigTableId").show();
				getAllRoles();
			}
		}

	});
	filteringOrganization($('#stateOrgsViewRole'));
	filteringOrganization($('#assessmentProgramsOrgsViewRole'));
	filteringOrganization($('#stateOrgsEditRole'));
	filteringOrganization($('#assessmentProgramsOrgsEditRole'));
	
	 $(document).on("click", ".Role_State_selectAll", function(ev){	
			var checkboxId= this.id;  
			checkboxId = checkboxId.replace("_selectAll","");
			
			
			if($('#'+checkboxId+'> option').length==0){
				$(this).attr("checked", false);
				$(this).trigger('change');
			}
			if($(this).is(':checked')){
				
			   $("#"+checkboxId).find('option').prop("selected",true);
		        $("#"+checkboxId).trigger('change');
				
			   }else{
			      $("#"+checkboxId).find('option').prop("selected",false);
		        $("#"+checkboxId).trigger('change');

			   }
			   
		});
	 $(document).on("click", ".authCheckbox", function(ev){	
		 var indexOfpermission=$.inArray($(this).val(), changedPermissions);
		 if(indexOfpermission > -1){			
			 changedPermissions.splice(indexOfpermission, 1);
			}else{
			changedPermissions.push($(this).val());
			}
	 });

}
function loadRolesGrid() {
	var $gridAuto = $('#viewRolesConfigTableId');
	// Unload the grid before each request.

	var gridWidthForRoles = $gridAuto.parent().width() - 60;
	if (gridWidthForRoles < 775) {
		gridWidthForRoles = 775;
	}

	var cmforViewRoles = [ {
		name : 'id',
		index : 'id',
		width : 0,
		search : false,
		hidden : true,
		hidedlg : true
	}, {
		name : 'roleName',
		index : 'roleName',
		width : gridWidthForRoles - 5,
		search : false,
		hidden : false,
		hidedlg : true,
		sort : true,
		sortType : 'Text'
	} 
	];

	allowRequest = true;
	// JQGRID
	$gridAuto.scb({
		mtype : "POST",
		datatype : "local",
		width : gridWidthForRoles,
		colNames : [ "Id", "Role Name" 
		],
		colModel : cmforViewRoles,
		rowNum : 10,
		sortname : 'roleName',
		sortorder : 'asc',
		caption : "",
		loadonce : true,
		filterToolbar : false,
		columnChooser : false,
		viewable : false,
		onSelectRow : function() {
			if (allowRequest) {
				allowRequest = false;
				var $this = $(this);
				var selRowId = $this.jqGrid('getGridParam', 'selrow');
				var groupId = $this.jqGrid('getCell', selRowId, 'id');
				if (editOption) {
					var organizationId = $('#stateOrgsEditRole').val();
					var assessmentProgramId = $('#assessmentProgramsOrgsEditRole').val();

				} else {
					var organizationId = $('#stateOrgsViewRole').val();
					var assessmentProgramId = $('#assessmentProgramsOrgsViewRole').val();
				}
				var groupName = $this.jqGrid('getCell', selRowId, 'roleName');
				if (editOption) {
					var getData = new Object();
					getData['organizationId[]'] = organizationId;
					getData['assessmentProgramId[]'] = assessmentProgramId;
					getData['groupId'] = groupId;
					$.ajax({
						url : 'checkPermissionsConflict.htm',
						data : getData,
						dataType : 'json',
						type : 'GET'		
					}).done(function(dto) {

						if (dto['conflict'] == 'true') {
							if (confirm('The selected Assessment Program(s) and State(s) do not share all of the same permissions for ' + groupName + '. If you continue, only the permissions that match will display in the UI. If you save any changes, those changes will override the current settings for all of the selected Assessment Program(s) and State(s).')) {
								loadPermissions(groupId, organizationId, assessmentProgramId, groupName, editOption);
							} else {
								allowRequest = true;
							}
						} else {
							loadPermissions(groupId, organizationId, assessmentProgramId, groupName, editOption);
						}
					});
				} else {
					loadPermissions(groupId, organizationId, assessmentProgramId, groupName, editOption);
				}
			}
		}

	});
	function loadPermissions(groupId, organizationId, assessmentProgramId, groupName, editOption) {
		var permissionsData = new Object();
		if (editOption) {
			permissionsData['groupId'] = groupId;
			permissionsData['organizationId[]'] = organizationId;
			permissionsData['assessmentProgramId[]'] = assessmentProgramId;
			permissionsData['forEdit'] = true;
		} else {
			permissionsData['groupId'] = groupId;
			permissionsData['organizationId'] = organizationId;
			permissionsData['assessmentProgramId'] = assessmentProgramId;
		}
		if(organizationId !=null && assessmentProgramId !=null){
		$.ajax({
			url : 'getAllRolePermissions.htm',
			data : permissionsData,
			dataType : 'json',
			type : 'GET',		
		}).done(function(data) {
			// load the ejs file with required data.
			$('#rolesPermissionTabs').html(new EJS({
				url : '/AART/js/views/authorities.ejs'
			}).render({
				data : data, viewRolesReadOnly : viewRolesReadOnly
			}));
			openPermissionsOverlay(groupName);
			allowRequest = true;
			$(".restricted").attr("disabled", true);
		}).fail(function(dto) {
			allowRequest = true;
		});
	}
	}
	// Clear the previous error messages
	setTimeout("aart.clearMessages()", 0);
}

function getAllRoles() {
	var $gridAuto = $("#viewRolesConfigTableId");
	$gridAuto.jqGrid('setGridParam', {
		datatype : "json",
		url : 'getRolesForUser.htm',
		search : false,
		postData : {
			"filters" : ""
		}
	}).trigger("reloadGrid", [ {
		page : 1
	} ]);
}

// open the div in dialog
function openPermissionsOverlay(groupName) {
	

	if(editOption){
	var selectedStates='';
	var lnth=$('#stateOrgsEditRole').find(":selected").length;
	$('#stateOrgsEditRole').find(":selected").each(function (index,val) {
			if(index==lnth-1){
			selectedStates += $(this).text();
			}else{
				selectedStates +=$(this).text() + ",";
			}
	});
	

	
	var selectedAssessmentPrograms='';
	lnth=$('#assessmentProgramsOrgsEditRole').find(":selected").length;
	$('#assessmentProgramsOrgsEditRole').find(":selected").each(function (index,val) {
			if(index==lnth-1){
				selectedAssessmentPrograms += $(this).text();
			}else{
				selectedAssessmentPrograms +=$(this).text() + ",";
			}
	});
	
	} else {
		var selectedStates='';
		var lnth=$('#stateOrgsViewRole').find(":selected").length;
		$('#stateOrgsViewRole').find(":selected").each(function (index,val) {
				if(index==lnth-1){
				selectedStates += $(this).text();
				}else{
					selectedStates +=$(this).text() + ",";
				}
		});		
		
		var selectedAssessmentPrograms='';
		lnth=$('#assessmentProgramsOrgsViewRole').find(":selected").length;
		$('#assessmentProgramsOrgsViewRole').find(":selected").each(function (index,val) {
				if(index==lnth-1){
					selectedAssessmentPrograms += $(this).text();
				}else{
					selectedAssessmentPrograms +=$(this).text() + ",";
				}
		});
	}

	
	$('#managePermissionsDiv').dialog({
		autoOpen : true,
		modal : true,
		width : 1087,
		height : 700,
		dialogClass : 'rolesSubMenutabs',
		title : 'Manage Permissions - ' + groupName,
		create : function(event, ui) {
			var widget = $(this).dialog("widget");
		},
		open : function(ev, ui) {
			$(".dialog-title").html('');
			$(this).find("span.dialog-title").append("<div class='title' style='width: 90%;'>" 
					+"<b><font color='#5f7e1a'>Assessment Program(s):</font></b>"+selectedAssessmentPrograms+ "<br>"+"<b><font color='#5f7e1a'>State(s):</font></b>"+selectedStates+"</div>");
		},
	    beforeClose: function(ev, ui) {

			if(changedPermissions.length> 0){
				showPermissionsChangedWarning();
				return false;
			}
        },
		close : function(ev, ui) {
		}
	});
}

$("#rolesPermissionTabs").on("keypress",function(e)    {  
	 rolesTabKevent(e); 
	     }
	 );

	function rolesTabKevent(e){ 
	    if (e.keyCode == 39 || e.keyCode == 40) {   
	     var currentIndex=$("#rolesPermissionTabs ul li a.active.show").parent().index();
	     $("#rolesPermissionTabs ul li a").removeClass("active show");
	     
	     if(currentIndex!=$('#rolesPermissionTabsContent li').length-1)
	      $("#rolesPermissionTabs ul li a").eq(currentIndex+1).addClass("active show");
	     else
	      $("#rolesPermissionTabs ul li a").eq(0).addClass("active show");         
	    }
	    if (e.keyCode == 37 || e.keyCode == 38) {      
	     var currentIndex=$("#rolesPermissionTabs ul li a.active.show").parent().index();
	     $("#rolesPermissionTabs ul li a").removeClass("active show");
	     $("#rolesPermissionTabs ul li a").eq(currentIndex-1).addClass("active show");

	    }
	}

function populateOrgsViewRoles() {
	if (!editOption) {

		var stateOrgSelect = $('#stateOrgsViewRole');
		clrStateOptionText = '';
		$('#stateOrgsViewRole').html("");
		$.ajax({
			url : 'getStatesOrgsForUser.htm',
			data : {},
			dataType : 'json',
			type : "GET"			
		}).done(function(states) {
			if (states !== undefined && states !== null && states.length > 0) {
				$.each(states, function(i, stateOrg) {
					clrStateOptionText = states[i].organizationName;
					stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(clrStateOptionText));
				});
				if (states.length == 1) {
					stateOrgSelect.find('option:first').prop('selected',false).next('option').attr('selected', 'selected');
					$("#stateOrgsViewRole").trigger('change');
				}
			}

				$('#stateOrgsViewRole').trigger('change.select2');

		});
	}
}
function populateViewRolesAssessmentProgram() {
	if (editOption) {
		var clrOptionText = '';
		$('#assessmentProgramsOrgsEditRole').html("");

		$.ajax({
			url : 'getOrgAssessmentPrograms.htm',
			data : {
				editRoles : true
			},
			dataType : 'json',
			type : "POST"
		}).done(function(assessmentProgramsOrgsEditRole) {
			if (assessmentProgramsOrgsEditRole !== undefined && assessmentProgramsOrgsEditRole !== null && assessmentProgramsOrgsEditRole.length > 0) {
				$.each(assessmentProgramsOrgsEditRole, function(i, clrAssessmentProgram) {
					clrOptionText = assessmentProgramsOrgsEditRole[i].abbreviatedname;
					$('#assessmentProgramsOrgsEditRole').append($('<option></option>').val(clrAssessmentProgram.id).html(clrOptionText));
				});

					if (assessmentProgramsOrgsEditRole.length == 1) {
						$("#assessmentProgramsOrgsEditRole").trigger('change');
					}
				}
				$('#stateOrgsEditRole').html('');
				$('#assessmentProgramsOrgsEditRole').val('').trigger('change.select2');
				filteringOrganization($('#assessmentProgramsOrgsEditRole'));

		});
	} else {
		var clrOptionText = '';
		$('#assessmentProgramsOrgsViewRole').html("");
		$('#assessmentProgramsOrgsViewRole').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		$.ajax({
			url : 'getCurrentUserAndOrgAssessmentPrograms.htm',
			dataType : 'json',
			type : "POST"			
		}).done(function(assessmentProgramsOrgsViewRole) {
			if (assessmentProgramsOrgsViewRole !== undefined && assessmentProgramsOrgsViewRole !== null && assessmentProgramsOrgsViewRole.length > 0) {
				$.each(assessmentProgramsOrgsViewRole, function(i, clrAssessmentProgram) {
					clrOptionText = assessmentProgramsOrgsViewRole[i].abbreviatedname;
					if (clrAssessmentProgram.assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()) {
						$('#assessmentProgramsOrgsViewRole').append($('<option selected=\'' + 'selected' + '\'></option>').val(clrAssessmentProgram.id).html(clrOptionText));
					} else {
						$('#assessmentProgramsOrgsViewRole').append($('<option></option>').val(clrAssessmentProgram.id).html(clrOptionText));
					}
				});

					if (assessmentProgramsOrgsViewRole.length == 1) {
						$("#assessmentProgramsOrgsViewRole").trigger('change');
					}
				}
				$('#assessmentProgramsOrgsViewRole').trigger('change.select2');

		});
	}
}

function limitUser(groupCode, enableFlag) {
	var organizationIds = $('#stateOrgsEditRole').val();
	var assessmentProgramIds = $('#assessmentProgramsOrgsEditRole').val();
	$.ajax({
		url : 'limitUsers.htm',
		data : {
			groupCode : groupCode,
			enableFlag : enableFlag,
			organizationIds : organizationIds,
			assessmentProgramIds : assessmentProgramIds
		},
		dataType : 'json',
		type : "POST"		
	}).done(function(data) {
		$("#limitRolesProcessing").hide();
		if (data) {
			var element = $('.slider-button-limitusers');
			if (enableFlag === 'OFF') {
				isUsersLimitedToOnePerRole = false;
				element.addClass('on').html('OFF');
			} else {
				isUsersLimitedToOnePerRole = true;
				element.removeClass('on').html('ON');
				element.removeClass('slider-color');
			}
		}
	});
}

function showPermissionsChangedWarning(){
	$('#warningDialog').dialog({
		modal : true,
		title : 'Warning',
		width : 543,
		buttons : {
			Continue : function() {
				changedPermissions=[];
				$(this).dialog("close");
				$('#managePermissionsDiv').dialog("close");
			},	
			Save : function(){
				savePermissions();
				$(this).dialog("close");
			}
		}
	});
}