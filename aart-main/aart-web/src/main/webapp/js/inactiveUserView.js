var shouldShowEditUser=false;
var userRolesModefied=false;
var hasUserModifyPermission = true;
var isMergeScreen = false;
function initInactiveUserTab() {
	
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
		
	buildInactiveUserGrid();
	
	$('#viewInvalidUserButton').on("click",function(event) {
		event.preventDefault();
		var $gridAuto = $("#viewInactiveUserGridTableId");
		$gridAuto[0].clearToolbar();
		$gridAuto.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getInactiveUsersForAdmin.htm?q=1',
			search: false, 
			postData: { "filters": "",
				        "requestFor":"view",
				        "showInactiveUsers":true}
		}).trigger("reloadGrid",[{page:1}]);
	});
	
	function getInactiveUserStatusFilterOptions(){
		return ':All;Active:Active;Inactive:Inactive;Pending:Pending';
	}
	
	function buildInactiveUserGrid() {
		var $gridAuto = $("#viewInactiveUserGridTableId");
		
		//Unload the grid before each request.
		$("#viewInactiveUserGridTableId").jqGrid('clearGridData');
		$("#viewInactiveUserGridTableId").jqGrid("GridUnload");
		
		var gridWidthForVO = 1050;
		console.log(gridWidthForVO);
		var cellWidthForVO = gridWidthForVO/5;
		
		var userSearchOptions = getInactiveUserStatusFilterOptions();
		

		var cmforViewUserGrid = [
			{ name : 'id', index : 'id', width : cellWidthForVO, search : false, hidden: true, key: true, hidedlg: false},			
			{ name : 'statusCode', index : 'statusCode', width : cellWidthForVO, search : true , stype : 'select', searchoptions: { value : userSearchOptions, sopt:['eq'] }, hidden : false, hidedlg : false},
			{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier', width : cellWidthForVO, search : true, hidden: false, hidedlg: false,
				formatter: escapeHtml
			},
			{ name : 'firstName', index : 'firstName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'surName', index : 'surName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},				
			{ name : 'email', index : 'email', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'programNames', index : 'programNames', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'userRoles', index : 'userRoles', width : cellWidthForVO, search : true, hidden : true, hidedlg : false,
				formatter: fontStyleFormat
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
					   "Roles"],
		  	colModel :cmforViewUserGrid,
			rowNum : 10,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			pager : '#viewInactiveUserGridPager',
			viewrecords : true,
			multiselect: true,
			page: 1,
		    sortname: 'surName, firstName, middleName',
		    sortorder: 'desc',
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
				        if($("#jqg_viewInactiveUserGridTableId_"+allRowsIds[i],grid).is(":disabled")){	
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
		        
		        $("#viewInactiveUserGridTableId").jqGrid('clearGridData');
		    },  
		    loadComplete: function (data) {
		    	 var ids = $(this).jqGrid('getDataIDs');         
		         var tableid=$(this).attr('id');      
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'surName')+ ' Check Box');
		            }
		            $('input').removeAttr("aria-checked");
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		             $('#cb_'+tableid).attr('title','User Grid All Check Box');
			             $.each(objs, function(index, value) {         
			              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
			                    $(value).attr('title',$(nm).text()+' filter');                          
			                    });
            },
		});
		
		$gridAuto.jqGrid('setGridParam',{
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
		
		$gridAuto.jqGrid('navGrid', '#viewInactiveUserGridPager', {edit: false, add: false, del: false, search: false, refresh: false})
        .jqGrid('navButtonAdd','#viewInactiveUserGridPager', {
        	caption: "",
        	title: "Modify User",
        	buttonicon: "ui-icon-pencil",
        	onClickButton: function() {
        		openEditInactiveUserPopup();
        	}
        });
		
		// Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
	}	
	
	/**
	 * This method is called when user clicks on the edit button. This method is responsible for displaying the edit user overlay
	 * with all the information prepopulated as per existing data for the user e.g. user details, organization details, role details etc.
	 */
	function openEditInactiveUserPopup() {
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
		var id = $('#viewInactiveUserGridTableId').jqGrid ('getGridParam','selrow');
		var selectRowLength = $('#viewInactiveUserGridTableId').jqGrid ('getGridParam','selarrrow').length;
		if(selectRowLength == 1){
		if (id)	{
			$(".full_main #message").hide();
			$.ajax({
		         url: 'getAllInactiveUserDetailsForAdmin.htm',
		         data: '&userId=' + id,
		         dataType: "json",
		         type: 'POST',
		         error: function(jqXHR, textStatus, errorThrown){
		        	 console.log(errorThrown);
		         },
		         success: function(data){
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
		         			var userId = $('#viewInactiveUserGridTableId').jqGrid ('getGridParam','selrow');
		         			$('#userId').val(userId);
		         			var ret = $('#viewInactiveUserGridTableId').jqGrid('getRowData',userId);

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
					$('#editUserOrganization').val("").trigger('change.select2');
					$('#editUserGroup').val("").trigger('change.select2');
					$('#editUserAssessmentProgram').val("").trigger('change.select2');
					$('#editUserAssessmentProgram').find('option').remove();
					$('#editUserDistrict').find('option').remove();
					$('#editUserSchool').find('option').remove();
					$('#editRoleRegionContainer').hide();
					$('#editRoleDistrictContainer').hide();
					$('#editRoleSchoolContainer').hide();
				}
			}).dialog('open');
		} else {			
			var v ;
		}
		} else {
			$(".full_main #messageViewInvalidUSers").html('<span class="error_message ui-state-highlight">Please select only one user to modify.</span>').show();
		}
		if(!hasUserModifyPermission){
			$('#editusers-contain').find('input').attr('disabled','disabled');
			$('#editusers-contain').find('select').prop('disabled','disabled');
			$('#editusers-contain').find('select').css('background-color','#dadada');
		}
	}
	
	function resendInactiveUserActivationEmail() {
		var rowIds = $("#viewInactiveUserGridTableId").jqGrid('getGridParam',
				'selarrrow');
		var flag=true;
		var params = {};
		params.ids = [];

		if (rowIds != null && rowIds.length > 0) {
			for ( var i = 0, length = rowIds.length; i < length; i++) {
				// get the row
				var rowObj = $("#viewInactiveUserGridTableId").jqGrid('getRowData',
						rowIds[i]);
				// get the id
				params.ids[i] = rowObj.id;
				
				if (rowObj.statusCode!="Pending") {
					flag=false;
					break;
				}
				
			}
			
			if(flag){
			$('#processing_request').show();

			$.ajax({
				url : 'resendUserActivationEmails.htm',
				data : params,
				type : 'POST',
				dataType : 'json' 
			}).done(function(data) {
				$('#processing_request').hide();

				// $('html, body').animate({scrollTop: mousePosition - 550},
				// 'slow');
				$("#UserARTSmessages").show();
				$('#emails_sent_id').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);

				$("#viewInactiveUserGridTableId").jqGrid('resetSelection');
			})
			.fail(function() {
				// $('html, body').animate({scrollTop: mousePosition - 550},
				// 'slow');
				$('#processing_request').hide();
				$("#UserARTSmessages").show();
				$('#emails_not_sent_id').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			});
			
		}else{
			$("#UserARTSmessages").show();
			$('#pending_user_selected_id').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function() {
				$("#UserARTSmessages").hide();
			}, 3000);
		}
		} else {
			// $('html, body').animate({scrollTop: mousePosition - 550},
			// 'slow');
			$("#UserARTSmessages").show();
			$('#select_at_least_one_id').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function() {
				$("#UserARTSmessages").hide();
			}, 3000);
		}
	}

	/**
	 * US16245 : Added for Active button
	 * Activate the selected Users Manually.
	 */
	function activateInactiveUserManually() {
		var rowIds = $("#viewInactiveUserGridTableId").jqGrid('getGridParam',
				'selarrrow');
		var flag=true;
		var params = {};
		params.ids = [];

		if (rowIds != null && rowIds.length > 0) {
			for ( var i = 0, length = rowIds.length; i < length; i++) {
				// get the row
				var rowObj = $("#viewInactiveUserGridTableId").jqGrid('getRowData',rowIds[i]);
				// get the id
				params.ids[i] = rowObj.id;
				if (rowObj.statusCode !== "Inactive") {
					flag=false;
					break;
				}
			}
			if(flag){
			$('#processing_request').show();

			$.ajax({
				url : 'chageUserStatusManually.htm?status=active',
				data : params,
				type : 'POST',
				dataType : 'json'
			}).done(function(data) {
				$('#processing_request').hide();
				// $('html, body').animate({scrollTop: mousePosition - 550},
				// 'slow');
				$("#UserARTSmessages").show();
				if (data.permit) {
					// print success message
					$('#account_activated_id').show();
					$("#viewInactiveUserGridTableId").trigger("reloadGrid");
				}else if(data.unAuthorized){
					$('#account_unAuthorized_id').show();
					$("#viewInactiveUserGridTableId").trigger("reloadGrid");						
				}else{
					// print error message
					$('#account_not_activated_id').show();
				}
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);

				$("#viewInactiveUserGridTableId").jqGrid('resetSelection');
			})
			.fail(function() {
				$('#processing_request').hide();
				$("#UserARTSmessages").show();
				$('#account_not_activated_id').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			});
			}else{
				$("#UserARTSmessages").show();
				$('#active_user_selected_id').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			}
		}else {
			$("#UserARTSmessages").show();
			$('#select_at_least_one_id').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function() {
				$("#UserARTSmessages").hide();
			}, 3000);
		}
	}

	/**
	 * US16245 : Added for Inactive button
	 * Inactivate the selected Users Manually.
	 */
	function inactivateInactiveUserManually() {
		var rowIds = $("#viewInactiveUserGridTableId").jqGrid('getGridParam',
				'selarrrow');
		var flag=true;
		var params = {};
		params.ids = [];
	
		if (rowIds != null && rowIds.length > 0) {
			for ( var i = 0, length = rowIds.length; i < length; i++) {
				// get the row
				var rowObj = $("#viewInactiveUserGridTableId").jqGrid('getRowData',
						rowIds[i]);
				// get the id
				params.ids[i] = rowObj.id;
				if (rowObj.statusCode == "Inactive") {
					flag=false;
					break;
				}
				
			}
			if(flag){

			$('#processing_request').show();

			$.ajax({
				url : 'chageUserStatusManually.htm?status=inactive',
				data : params,
				type : 'POST',
				dataType : 'json'
			}).done(function(data) {
				$('#processing_request').hide();

				$("#UserARTSmessages").show();
				if (data.permit) {
					// print success message
					$('#account_inactivated_id').show();
					$("#viewInactiveUserGridTableId").trigger("reloadGrid");
				} else {
					// print error message
					$('#account_not_inactivated_id').show();
				}
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);

				$("#viewInactiveUserGridTableId").jqGrid('resetSelection');
			})
			.fail(function() {
				$('#processing_request').hide();
				$("#UserARTSmessages").show();
				$('account_not_inactivated_id').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			});
			
			}else{
				$("#UserARTSmessages").show();
				$('#inactive_user_selected_id').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			}
		} else {
			$("#UserARTSmessages").show();
			$('#select_at_least_one_id').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function() {
				$("#UserARTSmessages").hide();
			}, 3000);
		}
	}
}

