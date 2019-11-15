var shouldShowEditUser=false;
var userRolesModefied=false;
var hasUserModifyPermission = true;
var isMergeScreen = false;
var inValidTeacherRole = "";

function find_Init_User_tab(){
	
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required') || element.attr('type') == 'file') {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});
		
	buildFindUserGrid();
	
	
	$('#findUserButton').on("click",function(event) {
			event.preventDefault();
			var fname= $('#fName').val();
			var lname= $('#lName').val();
			var edId = $('#educatorId').val();
			if(fname && lname ||edId) {
			var $gridAuto = $("#findUserGridTableId");
			$gridAuto.setGridParam({ postData: ""});
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getUsersForClaim.htm?q=1',
				search: false, 
				postData: { "filters": "",
					        "requestFor":"find",
					        "userFirstName":fname,
					        "userLastName":lname,
					        "educatorId":edId,
					        "showInactiveUsers":true}
			}).trigger("reloadGrid",[{page:1}]);
			}else{
				if(!fname||!lname){
					$('#errorMsgDailog').dialog({
						resizable : false,
						modal : true,
						width : 300,
						height : 200,
						create : function(event, ui) {
					
						},
						open : function(ev, ui) {
						},
						buttons : {
							OK : function() {
								$(this).dialog("close");
							},
							Cancel : function() {
								$(this).dialog("close");
							}
						}
					});
				}
			}
	});
	
	function getFindUserStatusFilterOptions(){
		return ':All;Active:Active;Inactive:Inactive;Pending:Pending';
	}
	
	function buildFindUserGrid(){
		var $gridAuto = $("#findUserGridTableId");
		
		//Unload the grid before each request.
		$("#findUserGridTableId").jqGrid('clearGridData');
		$("#findUserGridTableId").jqGrid("GridUnload");
		
		var gridWidthForVO = 1047;
		var cellWidthForVO = gridWidthForVO/5;
		
		var userSearchOptions = getFindUserStatusFilterOptions();
		

		var ViewFindUsersGrid = [
			{ name : 'id', index : 'id',label:'findUserId', width : cellWidthForVO, search : false, hidden: true, key: true, hidedlg: false},			
			{ name : 'statusCode', index : 'statusCode',label:'findUserStatusCode', width : cellWidthForVO, search : true , stype : 'select', searchoptions: { value : userSearchOptions, sopt:['eq'] }, hidden : false, hidedlg : false},
			{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier',label:'findUser', width : cellWidthForVO, search : true, hidden: false, hidedlg: false,
				formatter: escapeHtml
			},
			{ name : 'firstName', index : 'firstName',label:'findUserFirstName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'surName', index : 'surName',label:'findUserSurName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},				
			{ name : 'email', index : 'email',label:'findUserEmail', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'programNames', index : 'programNames',label:'findUserProgramNames', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'userRoles', index : 'userRoles',label:'findUserUserRoles', width : cellWidthForVO, search : true, hidden : true, hidedlg : false,
				formatter: fontStyleFormat
			},
			{ name : 'activeFlag', index : 'activeFlag',label:'findUserActiveFlag', width : cellWidthForVO, search : false, hidden : true, hidedlg : false,
				formatter:getActiveFlagStatus
			}
		];
		
		function fontStyleFormat(cellvalue, options, rowObject) {
			 var cellHtml = "<div style='line-height: 20px;' originalValue='" + cellvalue + "'>" + cellvalue + "</div>";
			 return cellHtml;
		}
		
		//JQGRID
		$gridAuto.scb({
			mtype: "POST",
			datatype : "local",
			width: gridWidthForVO,
			colNames: ["User Id",
			           "Status",
					   "Educator Identifier",
					   "First Name",
					   "Last Name",					   
					   "Email",
					   "Assessment Programs",
					   "Roles","Active Flag"],
		  	colModel :ViewFindUsersGrid,
			rowNum : 10,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			pager : '#findUserGridPager',
			viewrecords : true,
			multiselect: true,
			page: 1,
			//F820 -Grids default Sort order
		    sortname: 'surName,firstName',
		    sortorder: 'asc',
			loadonce: false,
			viewable: false,
		    beforeSelectRow: function(rowid, e) {
		    	return true;
		    },
		    onSelectRow: function(rowid, status, e){
         	   if(status){
         		  var grid = $(this);
         		  var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox", grid);
         		   //if selected row and checkbox is disabled, do not let it get checked.
         		   if(cbsdis.is(":disabled")){
         			  cbsdis.prop('checked', false);         			   
         		   }
         	   } else{
         		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
         		   //do nothing.
         	   }               	      
         	},
		    onSelectAll: function(aRowids,status) {
		        if (status) {
		        	var grid = $(this);
		            // uncheck "protected" rows
		            var cbs = $("tr.jqgrow > td > input.cbox:disabled", $(this)[0]);
		            cbs.removeAttr("checked");
		            var allRowsIds = grid.jqGrid('getDataIDs');
		            for(var i=0;i<allRowsIds.length;i++){
				        if($("#jqg_findUserGridTableId_"+allRowsIds[i],grid).is(":disabled")){	
				        	grid.jqGrid('setSelection', allRowsIds[i], false);
				        }
				    }	
		        }
		    },
		    beforeRequest: function() {
		    	
		    	//Set the page param to lastpage before sending the request when 
				//the user entered current page number is greater than lastpage number.
				var currentPage = $(this).getGridParam('page');
		        var lastPage = $(this).getGridParam('lastpage');
		              
		        if (lastPage!= 0 && currentPage > lastPage) {
		        	$(this).setGridParam('page', lastPage);
		        	$(this).setGridParam({postData: {page : lastPage}});
		        }
		        
		        $("#findUserGridTableId").jqGrid('clearGridData');
		    },  
		    loadComplete: function (data) {

		    	 var ids = $(this).jqGrid('getDataIDs');         
		         var tableid=$(this).attr('id');      
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'surName')+ ' Check Box');
		                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
		            }
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		             $('#cb_'+tableid).removeAttr('aria-checked');
		             $('#cb_'+tableid).attr('title','Claim User Grid All Check Box');
		             $.each(objs, function(index, value) {         
		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                    $(value).attr('title',$(nm).text()+' filter');
		                    if ( $(value).is('select')) {
			                	   $(value).removeAttr("role");
			                	   $(value).css({"width": "100%"});
			                	   
			                    	$(value).select2({
			              	   		  placeholder:'Select',
			            	   		  multiple: false,
			            	      		  allowClear : true
			            	   		 });
			                    };
		                    });
	                 
           
		    	
		    },
            gridComplete: function (data) {},
		});
		
		$gridAuto.jqGrid('setGridParam',{
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
		
		$gridAuto.jqGrid('navGrid','#findUserGridPager', {edit: false, add: false, del: false, search: false, refresh: false})
        .navButtonAdd('#findUserGridPager', {
        	caption: "",
        	title: "Claim User",
        	buttonicon: "ui-icon-pencil",
        	onClickButton: function() {
        		openFindUserPopup();
        	}
        });
		
		// Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
	}
}

function openFindUserPopup(){
	var userData=[];
	var currentGroupId=$('#hiddenCurrentGroupsId').val();
	var orgTypeCode=groupMap[currentGroupId];
	userData = $("#findUserGridTableId").jqGrid('getGridParam','data');
	var rowData={};
	$.each(userData,function(i,obj){
		if(obj.id==rowid){
			rowData= userData[i];
		} 
	});
	
	var id = $('#findUserGridTableId').jqGrid ('getGridParam','selrow');
	var selectRowLength = $('#findUserGridTableId').jqGrid ('getGridParam','selarrrow').length;
	if(selectRowLength == 1){
		var userDetails = $('#findUserGridTableId').jqGrid('getRowData',id);
		
		if(userDetails.activeFlag=="true"&&userDetails.statusCode!="Inactive"){
			activeUserPopup(userDetails);
		}else if(userDetails.activeFlag=="false" ||userDetails.statusCode=="Inactive"){
			inactiveUserMessagePopUp();
		}
	}else {
		$(".full_main #messageFindUSers").html('<span class="error_message ui-state-highlight">Please select only one user to modify.</span>').show();
	}
}

function activeUserPopup(userDetails) {
	var districtNames;

	$.ajax({
		url : 'getUserOrganizationsForUserClaim.htm',
		data :{
			userId: userDetails.id},
		dataType : 'json',
		type : "POST"
	}).done(function(data) {
		districtNames = data.districts;
		renderUiPopup(userDetails,districtNames);
	});
	

}

function renderUiPopup(userDetails,districtNames){
	$('#activeUsers').dialog({
		resizable : false,
		modal : true,
		width : 500,
		height : 200,
		create : function(event, ui) {
		var eFirstName = $('#educatorFName').html(' '+userDetails.firstName);
		var eLastName = $('#educatorLName').html(' '+userDetails.surName);
			$('#districtNames').html(' '+districtNames);
			$(".ui-dialog").removeAttr("role");
		},
		open : function(ev, ui) {
			var eFirstName = $('#educatorFName').html(' '+userDetails.firstName);
			var eLastName = $('#educatorLName').html(' '+userDetails.surName);
				$('#districtNames').html(' '+districtNames);
		},
		buttons : {
			"Send Email" : function() {
				sendEmail(userDetails.id);
				$(this).dialog("close");
			},
			Cancel : function() {
				$(this).dialog("close");
				$('#findUserGridTableId').trigger('reloadGrid');
			}
		}
	});
}


function sendEmail(userId){
	$.ajax({
		url : 'sendEmailToDTCUsers.htm',
		data :{
			userId: userId},
		dataType : 'json',
		type : "POST"
	}).done(function(data) {
		if(data.success){
			$('#sentEmailSucessMsg').text(data.success);
			$('#sentEmailSucessMsg').show();
			setTimeout("$('#sentEmailSucessMsg').hide()", 5000);
		}else{
			$('#sentEmailErrorMsg').text(data.error);
			$('#sentEmailErrorMsg').show();
			setTimeout("$('#sentEmailErrorMsg').hide()", 5000);
		}
	});
}

function inactiveUserMessagePopUp(){
	
	$('#inActiveUsers').dialog({
		resizable : false,
		modal : true,
		width : 400,
		height : 200,
		create : function(event, ui) {
		
		},
		open : function(ev, ui) {
			
		},
		buttons : {
			"OK" : function() {
				$(this).dialog("close");
				inActiveUserPopup();
			},
			Cancel : function() {
				$(this).dialog("close");
				$('#findUserGridTableId').trigger('reloadGrid');
			}
		}
	});
}
function inActiveUserPopup(){
	userRolesModefied = false;
	$("#btn_editUser").hide();
	 	$('#eduplicateUserError').hide();
		$('#eInvalidOrganizationRoleMessage').hide();
		$('#eidOrgUserError').hide();
		
		// reset data array here for re-population on opening popup.
		editUserRolesGridData = [];
		
		aart.clearMessages();
		$( "#editUsers" ).ajaxStop(function() {
	$("#btn_editUser").show();
		  	
	});
		
	var id = $('#findUserGridTableId').jqGrid ('getGridParam','selrow');
	if (id)	{
		$(".full_main #messageFindUSers").hide();
		$.ajax({
	         url: 'getAllInactiveUserDetailsForAdmin.htm',
	         data: '&userId=' + id,
	         dataType: "json",
	         type: 'POST'
	     }).done(function(data){
	         	if(data == null){
	         		alert('Failed');
	         		return;
	         	} else {
	         		var user = data.user;
	         		if(user.id==$('#currentUserIdFromHeader').val()){
	         			shouldShowEditUser=false;
	         		}else{
	         			shouldShowEditUser=true;
	         		}
	         		if(user != null && user != undefined){
	         			
	         			/**
	         			 * Get the data from view user grid like user name, email etc and populate in the edit overlay.
	         			 */
	         			var userId = $('#findUserGridTableId').jqGrid ('getGridParam','selrow');
	         			$('#userId').val(userId);
	         			var ret = $('#findUserGridTableId').jqGrid('getRowData',userId);

	         			$('#eeducatorIdentifier').val(ret.uniqueCommonIdentifier);
	        			$('#efirstName').val(ret.firstName);
	        			$('#elastName').val(ret.surName);
	        			$('#eemailAddress').val(ret.email);
	        			$('#userId').val(ret.id);
	        			
	        			var orglist = user.organizations;
	        			var rowId = 1;
	        			
	        			// Iterate over all roles and push the data to 
	        			$(data.userRoles).each(function(idx, userRole){
	        				// initialize here.
    						var rowData = {id: rowId, state: 0, district: 0, school: 0, assessmentProgram: 0, role: 0, region: 0, Default: false };
    						
							rowData.assessmentProgram = userRole.assessmentProgramId;
							rowData.Default = userRole.isDefault;
							
							var allowedApsExist=_.findWhere(allowedAps,{key:rowData.assessmentProgram});
		        			if(!allowedApsExist) {
		        				allowedAps.push({key:rowData.assessmentProgram, value: userRole.abbreviatedName});
		        			}
							
							if(orglist != undefined && orglist != null && orglist.length > 0){
		        				$(orglist).each(function( index, element ) {
		        					if(element.id === userRole.organizationId){
		        						// original organization value will be populated here
		        						if(element.organizationType.typeCode === 'ST'){
		        							rowData.state = element.id;
		        							var alreadyStateExist=_.findWhere(allowedStates,{key:rowData.state});
		        		        			if(!alreadyStateExist){
		        		        				allowedStates.push({key: rowData.state, value: element.organizationName});
		        		        			}
		        						} else if(element.organizationType.typeCode === 'RG'){
		        							rowData.region = element.id;
		        							var allowedRegionsExist=_.findWhere(allowedRegions,{key:rowData.region});
		        		        			if(!allowedRegionsExist){
		        		        				allowedRegions.push({key:rowData.region, value: element.organizationName});
		        		        			}
		        						} else if(element.organizationType.typeCode === 'DT'){
		        							rowData.district = element.id;
		        							var allowedDistrictsExist=_.findWhere(allowedDistricts,{key:rowData.district});
		        		        			if(!allowedDistrictsExist){
		        		        				allowedDistricts.push({key:rowData.district, value:element.organizationName });
		        		        			}
		        						} else if(element.organizationType.typeCode === 'SCH'){
		        							rowData.school = element.id;
		        							var allowedSchoolsExist=_.findWhere(allowedSchools,{key:rowData.school});
		        		        			if(!allowedSchoolsExist){
		        		        				allowedSchools.push({key:rowData.school, value:element.organizationName });
		        		        			}
		        						}
		        						// hierarchy organization values will be populated here
		        						$(element.hierarchy).each(function(idx, hierarchy){
		        							if(hierarchy.organizationType.typeCode === 'ST'){
		        								rowData.state = hierarchy.id;
		        								var alreadyStateExist=_.findWhere(allowedStates,{key:rowData.state});
			        		        			if(!alreadyStateExist){
			        		        				allowedStates.push({key: rowData.state, value: hierarchy.organizationName});
			        		        			}
			        						} else if(hierarchy.organizationType.typeCode === 'RG'){
			        							rowData.region = hierarchy.id;
			        							var allowedRegionsExist=_.findWhere(allowedRegions,{key:rowData.region});
			        		        			if(!allowedRegionsExist){
			        		        				allowedRegions.push({key:rowData.region, value: hierarchy.organizationName});
			        		        			}
			        						} else if(hierarchy.organizationType.typeCode === 'DT'){
			        							rowData.district = hierarchy.id;
			        							var allowedDistrictsExist=_.findWhere(allowedDistricts,{key:rowData.district});
			        		        			if(!allowedDistrictsExist){
			        		        				allowedDistricts.push({key:rowData.district, value:hierarchy.organizationName });
			        		        			}
			        						} else if(hierarchy.organizationType.typeCode === 'SCH'){
			        							rowData.school = hierarchy.id;
			        							var allowedSchoolsExist=_.findWhere(allowedSchools,{key:rowData.school});
			        		        			if(!allowedSchoolsExist){
			        		        				allowedSchools.push({key:rowData.school, value:hierarchy.organizationName });
			        		        			}
			        						}
		        						});
		        					}
		        				});
		        			}

							rowData.role = userRole.groupId;
							var allowedRolesExist=_.findWhere(allowedRoles,{key:rowData.role});
		        			if(!allowedRolesExist){
		        				allowedRoles.push({key:rowData.role, value: userRole.groupName});
		        			}
							rowId++;
							editUserRolesGridData.push(rowData);
						});
	        			statesAllowedForUserAdd='';
	        			apsAllowedForUserAdd='';
	        			//rolesAllowedForUserAdd='';
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
	        			
	        			renderUserRolesGrid('editUserRolesGrid', 'e');
	        			
	        			$('#editUserRolesGrid').setColProp('state', {edittype:"select", formatter:"select", editoptions: { value: statesAllowedForUserAdd,dataEvents:stateDataEvents}
	        			, sorttype: function (cell, obj) {
	        				return (_.findWhere(allowedStates,{key:cell})) ? _.findWhere(allowedStates,{key:cell}).value : '';
	        		    }});
	        			$('#editUserRolesGrid').setColProp('assessmentProgram', {edittype:"select", formatter:"select", editoptions: { value: apsAllowedForUserAdd}
	        			, sorttype: function (cell, obj) {
	        				return (_.findWhere(allowedAps,{key:cell})) ? _.findWhere(allowedAps,{key:cell}).value : '';
	        		    }});
	        			$('#editUserRolesGrid').setColProp('role', {edittype:"select", formatter:"select", editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents}
	        			, sorttype: function (cell, obj) {
	        				return (_.findWhere(allowedRoles,{key:cell})) ? _.findWhere(allowedRoles,{key:cell}).value : '';
	        		    }});
	        			//We need to add three more to the same.
	        			if(allowedRegionsForUserAdd==""){
	        				allowedRegionsForUserAdd=defaultValues;
	        			}
	        			if(allowedDistrictsForUserAdd==""){
	        				allowedDistrictsForUserAdd=defaultValues;
	        			}
	        			if(allowedSchoolsForUserAdd==""){
	        				allowedSchoolsForUserAdd=defaultValues;
	        			}
	        			$('#editUserRolesGrid').setColProp('region', {edittype:"select", formatter:"select", editoptions: { value: allowedRegionsForUserAdd,dataEvents:regionDataEvents}
	        			, sorttype: function (cell, obj) {
	        				return (_.findWhere(allowedRegions,{key:cell})) ? _.findWhere(allowedRegions,{key:cell}).value : '';
	        		    }});
	        			$('#editUserRolesGrid').setColProp('district', {edittype:"select", formatter:"select", editoptions: { value: allowedDistrictsForUserAdd,dataEvents:districtDataEvents}
	        			, sorttype: function (cell, obj) {
	        				return (_.findWhere(allowedDistricts,{key:cell})) ? _.findWhere(allowedDistricts,{key:cell}).value : '';
	        		    }});
	        			$('#editUserRolesGrid').setColProp('school', {edittype:"select", formatter:"select", editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents}
	        			, sorttype: function (cell, obj) {
	        				return (_.findWhere(allowedSchools,{key:cell})) ? _.findWhere(allowedSchools,{key:cell}).value : '';
	        		    }});
	        			$('#editUserRolesGrid').trigger('reloadGrid');
	         		}
	         	}
	         });
		$('#editUsers').dialog({
			autoOpen: false,
			modal: true,
			//resizable: false,
			width: 'auto',
			height: 640,
			title: "Modify User",
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			    $(".ui-dialog-titlebar-close span", widget).prop('id', 'editUserDialogClose');
			
			},
			beforeclose: function(event, ui) 
		    {
				if(userRolesModefied===true){
					 var parentWidget = $(this).dialog("widget");
					$( '#closeEditDialog' ).dialog({
				        resizable: false,
				        modal: true,
				        width: 500,
				        height: 200,
			    	    buttons: {
		        	  "OK": function() {
		        		  userRolesModefied = false;
		        		  $('#editUserDialogClose').click();
		        		  $(this).dialog('close');
		        		  return true;
			          },
			          Cancel: function() {
			        	  $(this).dialog( "close" );
			        	  return false;
			          }
			        }
			    });
			    return false;
		    } else {
		    	return true;
		    }
		    },
			close: function(ev, ui) {
				$('#editUserOrganization').val('');
				$('#editUserAssessmentProgram').val('');
				$('#editUserGroup').val('');
				$('#editRoleRegionContainer').hide();
				$('#findUserGridTableId').trigger('reloadGrid');
				$('#editRoleDistrictContainer').hide();
				$('#editRoleSchoolContainer').hide();
			}
		}).dialog('open');
	} else {			
		var v ;
	}
	if(!hasUserModifyPermission){
		$('#editusers-contain').find('input').attr('disabled','disabled');
		$('#editusers-contain').find('select').prop('disabled','disabled');
		$('#editusers-contain').find('select').css('background-color','#dadada');
	}
}

function getActiveFlagStatus(cellValue, options, rowObject){
	
	return rowObject[8];
}

function callSaveClaimUserClick(e, screen, isFindUser){
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
		if(orgTypeId == 2){
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
			if(screen == 'e'){
				overwriteflag = true;					
			}
			
			$('#btn_addUser').prop("disabled",true);
			$('#btn_addUser').addClass('ui-state-disabled');
			
			$.ajax({
		         url: 'claimUser.htm',
		         data: '&users=' + encodeURIComponent(usersParameter)+'&overwrite='+overwriteflag+'&userId='+currentUser.id+'&isFindUser='+isFindUser,
		         dataType: "json",
		         type: 'POST'
		     }).done(function(data){
	        	 $('#'+screen+'duplicateUserError').hide();
	        	 $('#'+screen+'InvalidOrgRoleMessage').hide();
	        	 $('#'+screen+'idOrgUserError').hide();
				userRolesModefied = false;
	         	if(data == null){
	         		$('#'+screen+'UserARTSmessages').show();
        			$('#'+screen+'UserfailMessage').show();
	         	} else {
	         		var user = data.user;
	         		if(user!=null && user != undefined){
	         			if(user.errorCode != undefined && user.errorCode.trim().length > 0){
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
	         						$('#'+screen+'UsersuccessMessage').html(msg.label_sameuser_modify_sucess).show();
	         					}
	         					else{
	         						$('#'+screen+'UsersuccessMessage').html(msg.label_user_modify_success).show();
	         					}
 				         		$("#viewUserGridTableId").trigger("reloadGrid");	
	         				} else{
	         					$("#usersSelect").val("");
		         				$("#usersSelect").trigger('change.select2');
		         				$('#'+screen+'UsersuccessMessage').show();
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
	         }).fail(function(jqXHR, textStatus, errorThrown){
		        	$('#btn_addUser').prop("disabled",false);
		 			$('#btn_addUser').removeClass('ui-state-disabled');
		         });
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