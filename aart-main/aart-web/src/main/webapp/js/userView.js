/**
 * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
 * Contains all javascript needful for view user functionality.
 */
var shouldShowEditUser=false;
var userRolesModefied=false;

function view_Init_User_tab() {
	//var educatorIdentifier = '';
	$('#viewUserOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [20]
	});
	
	$('#viewUserOrgFilterForm').validate({ignore: ""});
	
	filteringOrganizationSet($('#viewUserOrgFilterForm'));
	
	   	
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
		
	buildViewOrgGrid();
	
	$('#viewUserButton').on("click",function(event) {
		event.preventDefault();
		debugger;
		if($('#viewUserOrgFilterForm').valid()) {
			$(".full_main .message").hide();
			var $gridAuto = $("#viewUserGridTableId");
			$gridAuto[0].clearToolbar();
			var showInactiveUsers = $('#showInactiveUsers').prop('checked');
			if(showInactiveUsers == false){
				showInactiveUsers = 'unchecked';
				$('#activateUserButton').addClass('ui-state-disabled');
			}else{
				showInactiveUsers = 'checked';
				$('#activateUserButton').removeClass('ui-state-disabled');
			}
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getUsersByOrganization.htm?q=1',
				search: false, 
				postData: { "filters": "",
					        "requestFor":"view",
					        "showInactiveUsers":showInactiveUsers}
			}).trigger("reloadGrid",[{page:1}]);
		}
	});
	
	$('input[name=showInactiveUsers]').on('change',function() {
		debugger;
		var showInactiveUsers = $(this).prop('checked');
		if(showInactiveUsers == false){
			showInactiveUsers = 'unchecked';
		}

		if(showInactiveUsers == true){
			$('#activateUserButton').removeClass('ui-state-disabled');
			showInactiveUsers = 'checked';
		} else {
			$('#activateUserButton').addClass('ui-state-disabled');
		}
		var userSearchOptions = getUserStatusFilterOptions();
		var $gridAuto = $("#viewUserGridTableId");
		$gridAuto.jqGrid("destroyFilterToolbar");
		var cm = $gridAuto.jqGrid('getGridParam','colModel');
		var statusCodeFilterId='';
		statusCodeFilterId =$(_.findWhere(cm, {name: 'statusCode'})).attr('label');
		populateFilterValuesUserView(userSearchOptions,statusCodeFilterId);	
		$gridAuto.setColProp('uniqueCommonIdentifier', {search : true});
		$gridAuto.setColProp('firstName', {search : true});
		$gridAuto.setColProp('surName', {search : true});
		$gridAuto.setColProp('email', {search : true});
		$gridAuto.setColProp('programNames', {search : true});
		$gridAuto.setColProp('userRoles', {search : true});
		
		var requestData = {filters: '',
		        requestFor:'view',
		        showInactiveUsers:showInactiveUsers};
			
		$gridAuto.jqGrid("filterToolbar", {
            stringResult: true,
            searchOnEnter: true,
            searchOperators: true,
            defaultSearch: "cn"
        });
		
		$gridAuto.jqGrid('setGridParam',{
			datatype : "json", 
			url : 'getUsersByOrganization.htm?q=1',
			search : false, 
			postData : requestData
		});
		
		var rowCount =$gridAuto.jqGrid('getGridParam', 'reccount');
		if(rowCount > 0) {
			buildViewOrgGrid();
			$( "#viewUserButton" ).trigger( "click" );
		} else {
			buildViewOrgGrid();			
		}
	});
	
	function populateFilterValuesUserView(filterValues,filterId){
		$('#gs_'+filterId).find('option').remove();
		var filterValuesArray=filterValues.split(';');
		filterValuesArray.forEach(function(v) {
				  v = v.split(':');
				  $('#gs_'+filterId).append($('<option></option>').val(v[0]).html(v[1]));
				});	
		}
	
	function getUserStatusFilterOptions(){
		var userSearchOptions = ':All;Active:Active;Pending:Pending';
		var showInactiveUsers = $('#showInactiveUsers').prop('checked');
		if(showInactiveUsers == false){
			showInactiveUsers = 'unchecked';
		}
		if(showInactiveUsers == true){
			userSearchOptions = ':All;Active:Active;Inactive:Inactive;Pending:Pending'
		}
		return userSearchOptions;
	}
	
	function buildViewOrgGrid() {
		var $gridAuto = $("#viewUserGridTableId");
		
		//Unload the grid before each request.
		$("#viewUserGridTableId").jqGrid('clearGridData');
		$("#viewUserGridTableId").jqGrid("GridUnload");
		
		var gridWidthForVO = $gridAuto.parent().width();		
		if(gridWidthForVO < 760) {
			gridWidthForVO = 760;				
		}
		var cellWidthForVO = gridWidthForVO/5;
		
		var userSearchOptions = getUserStatusFilterOptions();
		

		var cmforViewUserGrid = [
			{ name : 'id', index : 'id',label:'view_user_id', width : cellWidthForVO, search : false, hidden: true, key: true, hidedlg: false},			
			/*{ name : 'statusCode', index : 'statusCode', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},*/
			
			/*Changed during  US16247 */
			{ name : 'statusCode', index : 'statusCode',label:'view_user_statusCode', width : cellWidthForVO, search : true , stype : 'select', searchoptions: { value : userSearchOptions, sopt:['eq'] }, hidden : false, hidedlg : false},
			{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier',label:'view_user_uniqueCommonIdentifier', width : cellWidthForVO, search : true, hidden: false, hidedlg: false,
				formatter: escapeHtml
			},
			{ name : 'firstName', index : 'firstName',label:'view_user_firstName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'surName', index : 'surName',label:'view_user_surName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},				
			{ name : 'email', index : 'email',label:'view_user_email', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'programNames', index : 'programNames',label:'view_user_programNames', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'userRoles', index : 'userRoles',label:'view_user_userRoles', width : cellWidthForVO, search : true, hidden : true, hidedlg : false,
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
			colNames: ["User Id",       // Changed During US16242
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
			pager : '#viewUserGridPager',
			viewrecords : true,
			multiselect: true,
			page: 1,
			// F-820 Grids default sort order
		    sortname: 'surName,firstName',
		    sortorder: 'asc',
			loadonce: false,
			viewable: false,
		    beforeSelectRow: function(rowid, e) {
		    	/*var grid = $(this);
		    	var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox:disabled", grid[0]);
		        if (cbsdis.length === 0) {
		            return true;    // allow select the row
		        } else {
		            return false;   // not allow select the row
		        }*/
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
				        if($("#jqg_viewUserGridTableId_"+allRowsIds[i],grid).is(":disabled")){	
				        	grid.jqGrid('setSelection', allRowsIds[i], false);
				        }
				    }	
		        }
		    },
		    beforeRequest: function() {
		    	if(!$('#viewUserOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
		    		return false;
		    	}
				
		    	//Set the page param to lastpage before sending the request when 
				//the user entered current page number is greater than lastpage number.
				var currentPage = $(this).getGridParam('page');
		        var lastPage = $(this).getGridParam('lastpage');
		              
		        if (lastPage!= 0 && currentPage > lastPage) {
		        	$(this).setGridParam('page', lastPage);
		        	$(this).setGridParam({postData: {page : lastPage}});
		        }
		        
		        if($('#viewUserOrgFilter').orgFilter('value') != null)  {
		        	var orgs = new Array();
		        	orgs.push($('#viewUserOrgFilter').orgFilter('value'));
		        	$(this).setGridParam({postData: {
		        		'orgChildrenIds': function() {return orgs;}}
		        	});
		        } else if($(this).getGridParam('datatype') == 'json') {
		        		return false;
		        }
		        $("#viewUserGridTableId").jqGrid('clearGridData');
		    },  
		    //Commented during US16245 : to enable check box  for all
		    loadComplete: function (data) {
               /* if (data.rows != undefined && data.rows.length > 0) {	
                	var grid = $(this);
                    for (var i = 0; i < data.rows.length; i++) {
                        if ($.trim(data.rows[i].cell[1]) == 'Active' || $.trim(data.rows[i].cell[1]) == 'New') {
                        	//$("tr#"+data.rows[i].id+".jqgrow > td > input.cbox", grid).attr("disabled", "disabled").prop('checked', false);
                        	$("tr#"+data.rows[i].id+".jqgrow > td > input.cbox", grid).hide().prop('checked', false);
                        }
                    }
                }*/
		    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
		    	 var ids = $(this).jqGrid('getDataIDs');         
		         var tableid=$(this).attr('id');      
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'surName')+ ' Check Box');
		                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
		            }
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		             $('#cb_'+tableid).attr('title','User Grid All Check Box');
		             $('#cb_'+tableid).removeAttr('aria-checked');
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
		             $('.select2-hidden-accessible').removeAttr("aria-hidden");
		              },
		});
		
		$gridAuto.jqGrid('setGridParam',{
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
		
		$gridAuto.jqGrid('navGrid','#viewUserGridPager', {edit: false, add: false, del: false, search: false, refresh: false})
        .navButtonAdd('#viewUserGridPager', {
        	caption: "",
        	title: "Modify User",
        	buttonicon: "ui-icon-pencil",
        	onClickButton: function() {
        		openEditUserPopup();
        	}
        }).navButtonAdd('#viewUserGridPager', {
        	caption: "",
        	title: "Re-send Email",
        	buttonicon: "ui-icon-mail-closed",
        	onClickButton: function() {
        		resendActivationEmail();
        	},
        	position: "last"
        });

		//US16245 : Added for active and Inactive button
		if (typeof (activateUserPermission) !== 'undefined') {
			$gridAuto.jqGrid('navGrid','#viewUserGridPager', {edit : false,add : false,del : false,search : false,refresh : false	})
			.navButtonAdd('#viewUserGridPager', {
				caption : "",
				title : "Activate User",
				id:"activateUserButton",
				buttonicon : "ui-icon-check",				
				onClickButton : function() {
					activateUserManually();
				},
			});
		}
		if (typeof (inactivateUserPermission) !== 'undefined') {
			$gridAuto.jqGrid('navGrid','#viewUserGridPager', {edit : false,add : false,del : false,search : false,refresh : false	})
			.navButtonAdd('#viewUserGridPager', {
				caption : "",
				title : "Deactivate User",
				buttonicon : "ui-icon-close",
				onClickButton : function() {
					inactivateUserManually();
				},
				position : "last"
			});
		}
		if(showInactiveUsers === 'checked'){
			$('#activateUserButton').removeClass('ui-state-disabled');
		} else {
			$('#activateUserButton').addClass('ui-state-disabled');
		}
		
		var navButtions=$('.navButtonAdd');
		
		// Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
			
		
	}	
	
	/**
	 * This method is called when user clicks on the edit button. This method is responsible for displaying the edit user overlay
	 * with all the information prepopulated as per existing data for the user e.g. user details, organization details, role details etc.
	 */
	function openEditUserPopup() {
		userAllowedRoles='';
		allowedRoles=[];
		userRolesModefied = false;
		$("#btn_editUser").hide();
   	 	$('#eduplicateUserError').hide();
 		$('#eInvalidOrganizationRoleMessage').hide();
 		$('#eidOrgUserError').hide();
 		getStatesOrgsForUser('editUserOrganization',true);
 		// reset data array here for re-population on opening popup.
 		editUserRolesGridData = [];
 		statesAllowedForUserAdd ='';
 		allowedStates=[];
 		allowedSchools=[];
 		allowedDistricts=[];
 		allowedRegions=[];
 		allowedAps=[];
 		
 		$('#editUserOrganization').select2({
 				placeholder:'Select', 
 				multiple: false,
 				allowClear : true
 			});
 		$('#editUserOrganization').trigger('change.select2');
 		$('#editUserAssessmentProgram').select2({
 				placeholder:'Select', 
 				multiple: false,
 				allowClear : true
 			})
 		$('#editUserAssessmentProgram').val('').trigger('change.select2');
 		$('#editUserGroup').select2({
 				placeholder:'Select', 
 				multiple: false,
 				allowClear : true
 			});
 		$('#editUserGroup').val('').trigger('change.select2');
 		$('#editUserRegion').select2({
 				placeholder:'Select', 
 				multiple: false,
 				allowClear : true
 			});
 		$('#editUserRegion').val('').trigger('change.select2');
 		$('#editUserDistrict').select2({
 				placeholder:'Select', 
 				multiple: false,
 				allowClear : true
 			});
 		$('#editUserDistrict').val('').trigger('change.select2');
 		$('#editUserSchool').select2({
 				placeholder:'Select', 
 				multiple: false,
 				allowClear : true
 			});
 		$('#editUserSchool').val('').trigger('change.select2');
 		
 		aart.clearMessages();
 		$( "#editUsers" ).ajaxStop(function() {
		$("#btn_editUser").show();
			  	
		});
		var id = $('#viewUserGridTableId').jqGrid ('getGridParam','selrow');
		var selectRowLength = $('#viewUserGridTableId').jqGrid ('getGridParam','selarrrow').length;
		if(selectRowLength == 1){
		if (id)	{
			$(".full_main .message").hide();
			$.ajax({
		         url: 'getAllUserDetails.htm',
		         data: '&userId=' + id,
		         dataType: "json",
		         type: 'POST'
		     }).done(function(data){
		         	if(data == null){
		         		alert('Failed');
		         		return;
		         	} else {
		         		var user = data.user;
		         		//TODO: Need to remove this Logic required for the Current User or Not.
		         		if(user.id==$('#currentUserIdFromHeader').val()){
		         			shouldShowEditUser=false;
		         		}else{
		         			shouldShowEditUser=true;
		         		}
		         		if(user != null && user != undefined){
		         			
		         			/**
		         			 * Get the data from view user grid like user name, email etc and populate in the edit overlay.
		         			 */
		         			var ret = $('#viewUserGridTableId').jqGrid('getRowData',id);
		         			$('#eeducatorIdentifier').val(ret.uniqueCommonIdentifier);
		        			$('#efirstName').val(ret.firstName);
		        			$('#elastName').val(ret.surName);
		        			$('#eemailAddress').val(ret.email);
		        			$('#userId').val(ret.id);
		        			if(data.doesUserHaveHighRoles){
		        				$('#eeducatorIdentifier').attr('disabled', 'disabled');
			        			$('#efirstName').attr('disabled', 'disabled');
			        			$('#elastName').attr('disabled', 'disabled');
			        			$('#eemailAddress').attr('disabled', 'disabled');
		         			}
		        			/**
		        			 * Create user's roles data.
		        			 */
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
			        						if(element.organizationType.typeCode === 'CONS'){
			        							rowData.state = -999;
			        							rowData.school = -999;
			        							rowData.district = -999;
			        						}
			        						else if(element.organizationType.typeCode === 'ST'){
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
		        			var teacherId = groupsNameMap['Teacher'];
		        			var eeducatorIdentifierAsterisk = true;
		        			var gridData = $("#editUserRolesGrid").jqGrid("getGridParam", "data"),
		        			gridRoleId = $.map(gridData, function (item) { return item.role; });
		        			for (var i = 0; i < gridRoleId.length; ++i) {
		        				if (gridRoleId[i] == teacherId) {
		        					$('span[id^="eeducatorIdentifierAsterisk"]').remove();
		        					$("#eeducatorIdentifierLabel")
		        							.append(
		        									'<span id="eeducatorIdentifierAsterisk" class="lbl-required">*</span>');
		        					eeducatorIdentifierAsterisk = false;
		        				} else if (gridRoleId[i] != teacherId
		        						&& eeducatorIdentifierAsterisk == true) {
		        					$('span[id^="eeducatorIdentifierAsterisk"]').remove();
		        				}
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
				    $(".ui-dialog-titlebar-close span", widget).prop('title', 'Edit User Dialog');
				
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
					$('#editRoleDistrictContainer').hide();
					$('#editRoleSchoolContainer').hide();
					$('#eeducatorIdentifier').attr('disabled', false);
					$('#efirstName').attr('disabled', false);
					$('#elastName').attr('disabled', false);
					$('#eemailAddress').attr('disabled', false);
				}
			}).dialog('open');
		} else {			
			var v ;
		}
		} else {
			$(".full_main .message").html('<span class="error_message ui-state-highlight">Please select only one user to modify.</span>').show();
		}
		if(!hasUserModifyPermission){
			$('#editusers-contain').find('input').attr('disabled','disabled');
			$('#editusers-contain').find('select').prop('disabled','disabled');
			$('#editusers-contain').find('select').css('background-color','#dadada');
		}
	}
	
	/**
	 *Change During US16245 to validate only pending users are selected
	 * Resend email to selected pending users.
	 */
	function resendActivationEmail() {
		var rowIds = $("#viewUserGridTableId").jqGrid('getGridParam',
				'selarrrow');
		var flag=true;
		var params = {};
		params.ids = [];

		if (rowIds != null && rowIds.length > 0) {
			for ( var i = 0, length = rowIds.length; i < length; i++) {
				// get the row
				var rowObj = $("#viewUserGridTableId").jqGrid('getRowData',
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

				$("#viewUserGridTableId").jqGrid('resetSelection');
			}).fail(function() {
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
	function activateUserManually() {
		var rowIds = $("#viewUserGridTableId").jqGrid('getGridParam',
				'selarrrow');
		var flag=true;
		var params = {};
		params.ids = [];

		var emptyEducatorUserIds = [];
		
		if (rowIds != null && rowIds.length > 0) {
			for ( var i = 0, length = rowIds.length; i < length; i++) {
				// get the row
				var rowObj = $("#viewUserGridTableId").jqGrid('getRowData',rowIds[i]);
				
				if(rowObj.uniqueCommonIdentifier == '' || rowObj.uniqueCommonIdentifier.length == 0){
					emptyEducatorUserIds.push(rowObj.id);
				}
				
				// get the id
				params.ids[i] = rowObj.id;
				if (rowObj.statusCode !== "Inactive") {
					flag=false;
					break;
				}
			}
			if(flag){
			$('#processing_request').show();

				if(emptyEducatorUserIds.length>0){
					
					$.ajax({
						url : 'getDistinctGroupCodesBasedOnUserIds.htm',
						data : {
							userIds : emptyEducatorUserIds
						},
						type : 'POST',
						dataType : 'json'
					}).done(function(data) {			
						if(data != null && data.userOrganizationsGroups.length>0){
							
							var userOrganizations = data.userOrganizationsGroups;
							var dataHtml = "The selected educator(s), ";
							
							var usersDataHtml="";
							for(var i = 0; i < userOrganizations.length; i++){
								var userID = userOrganizations[i].userId;								
								var rowObj = $("#viewUserGridTableId").jqGrid('getRowData',	userID);					
								if(usersDataHtml=='') usersDataHtml = rowObj.firstName +" "+rowObj.surName;
								else usersDataHtml = usersDataHtml + ", "+ rowObj.firstName +" "+rowObj.surName;
							}
																						
							$('#edutifierfierNotFoundTeacherPopup').html(dataHtml+"<b>"+usersDataHtml+"</b> does not have have an Educator Identifier. Please add an Educator Identifier to the educator before activating.").dialog({
						        resizable: false,
						        modal: true,
						        width: 500,
						        height: 'auto',
					    	    buttons: {
				        	  "OK": function() {	        		  
				        		  $(this).dialog('close');
				        		  return false;
					          },
					          Cancel: function() {
					        	  $(this).dialog( "close" );
					        	  return false;
					          }
					        }
					    });
										
						}
						else{
							doActivateUsermanually(params);
						}			
					}).fail(function(jqXHR, textStatus, errorThrown){
					       console.log(errorThrown);
				    });
					
											
				}else{
					doActivateUsermanually(params);
				}
			
			}else{
				$("#UserARTSmessages").show();
				$('#active_user_selected_id').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			}
		}else {
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

	function doActivateUsermanually(params){
		
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
				$("#viewUserGridTableId").trigger("reloadGrid");
			}else if(data.unAuthorized){
				$('#account_unAuthorized_id').show();
				$("#viewUserGridTableId").trigger("reloadGrid");						
			}else{
				// print error message
				$('#account_not_activated_id').show();
			}
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function() {
				$("#UserARTSmessages").hide();
			}, 3000);

			$("#viewUserGridTableId").jqGrid('resetSelection');
		}).fail(function() {
			// $('html, body').animate({scrollTop: mousePosition - 550},
			// 'slow');
			$('#processing_request').hide();
			$("#UserARTSmessages").show();
			$('#account_not_activated_id').show();
			setTimeout("aart.clearMessages()", 3000);
			setTimeout(function() {
				$("#UserARTSmessages").hide();
			}, 3000);
		});
	
	
	}

	/**
	 * US16245 : Added for Inactive button
	 * Inactivate the selected Users Manually.
	 */
	function inactivateUserManually() {
		var rowIds = $("#viewUserGridTableId").jqGrid('getGridParam',
				'selarrrow');
		var flag=true;
		var params = {};
		params.ids = [];
	
		if (rowIds != null && rowIds.length > 0) {
			for ( var i = 0, length = rowIds.length; i < length; i++) {
				// get the row
				var rowObj = $("#viewUserGridTableId").jqGrid('getRowData',
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

				// $('html, body').animate({scrollTop: mousePosition - 550},
				// 'slow');
				$("#UserARTSmessages").show();
				if (data.permit) {
					// print success message
					$('#account_inactivated_id').show();
					$("#viewUserGridTableId").trigger("reloadGrid");
				} else {
					// print error message
					$('#account_not_inactivated_id').show();
				}
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);

				$("#viewUserGridTableId").jqGrid('resetSelection');
			}).fail(function() {
				// $('html, body').animate({scrollTop: mousePosition - 550},
				// 'slow');
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
}
/*});*/

