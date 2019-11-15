/**
 * Manoj Kumar O : Added for US_16244(provide UI TO merge Users) 
 * Contains all javascript needful for Merge user functionality.
 */
var selectedUserArr = []	;

function merge_Init_User_tab(){
	$('#mergeUserOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [20]
	});
	
	$('#mergeUserOrgFilterForm').validate({ignore: ""});
	
	filteringOrganizationSet($('#mergeUserOrgFilterForm'));
	
	selectedUserArr = [];
		
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
	
	buildMergeOrgGrid();
		
	$('#mergeUserButton').on("click",function(event) {
		var b = $('#mergeUserOrgFilterForm').valid();
		if(b) {
			//	buildMergeOrgGrid();
		}
		
		event.preventDefault();
		enableDisableBtn(0);
		if($('#mergeUserOrgFilterForm').valid()) {
			selectedUserArr = [];
			var $gridAuto = $("#mergeUserGridTableId");
			$gridAuto[0].clearToolbar();
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getUsersByOrganization.htm?q=1',
				search: false, 
				postData: { "filters": "",
					        "requestFor":"merge"}
			}).trigger("reloadGrid",[{page:1}]);
		}
	});
}
	
function buildMergeOrgGrid() {
	
	var $gridAuto = $("#mergeUserGridTableId");
	
	//Unload the grid before each request.
	$("#mergeUserGridTableId").jqGrid('clearGridData');
	$("#mergeUserGridTableId").jqGrid("GridUnload");
	
	selectedUserArr = [];
	
	var gridWidthForVO = $gridAuto.parent().width();		
	if(gridWidthForVO < 760) {
		gridWidthForVO = 760;				
	}
	var cellWidthForVO = gridWidthForVO/5;

	var cmforViewUserGrid = [
		{ name : 'id', index : 'id',label:'mergeUserId', width : cellWidthForVO, search : false, hidden: true, key: true, hidedlg: false},			
		{ name : 'statusCode', index : 'statusCode',label:'mergeUserStatusCode', width : cellWidthForVO, search : true , stype : 'select', searchoptions: { value : ':All;Active:Active;Inactive:Inactive;Pending:Pending', sopt:['eq'] }, hidden : false, hidedlg : false},
		{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier',label:'mergeUserUniqueCommonIdentifier', width : cellWidthForVO, search : true, hidden: false, hidedlg: false,
			formatter: escapeHtml
		},
		{ name : 'firstName', index : 'firstName',label:'mergeUserFirstName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
			formatter: escapeHtml
		},
		{ name : 'surName', index : 'surName',label:'mergeUserSurName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
			formatter: escapeHtml
		},				
		{ name : 'email', index : 'email',label:'mergeUserEmail', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
			formatter: escapeHtml
		},
		{ name : 'assessmentPrograms', index : 'assessmentPrograms',label:'mergeUserAssessmentPrograms', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
			formatter: escapeHtml
		}
	];
	

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
				   "Assessment Programs"],
	  	colModel :cmforViewUserGrid,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#mergeUserGridPager',
		viewrecords : true,
		multiselect: true,
		page: 1,
		// F-820 Grids default sort order
	    sortname: 'surName,firstName',
	    sortorder: 'asc',
		loadonce: false,
		viewable: false,
	    beforeSelectRow: function(rowid, e) {
    	return true;
	    },
	    onSelectRow: function(rowid, status, e){
	      var rData = $('#mergeUserGridTableId').jqGrid('getRowData',rowid);
	      //firstEducatorIdentifier = rData.uniqueCommonIdentifier;
	      var userObj =	{ uniqueCommonIdentifier : rData.uniqueCommonIdentifier , firstName : rData.firstName,
	    		  		  surName : rData.surName, email : rData.email,
	    		  		  id : rData.id };
	    	
     	   if(status){
     		 var found = false; 
     		 
     		 $.each(selectedUserArr, function(i){
   			    if(selectedUserArr[i].id === rData.id) {
   			    	found = true;
   			    }
   			 }); 
     		 if( ! found )
     			 selectedUserArr.push(userObj);
     		   
     		  var grid = $(this);
     		  var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox", grid);
     		   //if selected row and checkbox is disabled, do not let it get checked.
     		   if(cbsdis.is(":disabled")){
     			  cbsdis.prop('checked', false);         			   
     		   }
     	   } else{
     		   $.each(selectedUserArr, function(i){
     			    if(selectedUserArr[i].id === rData.id) {
     			    	selectedUserArr.splice(i,1);
     			        return false;
     			    }
     			});
     		  
     		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
     		   //do nothing.
     	   }  
     	  enableDisableBtn(selectedUserArr);
     	},
	    onSelectAll: function(aRowids,status) {
	    	var grid = $(this);
	        if (status) {
	            // uncheck "protected" rows
	            var cbs = $("tr.jqgrow > td > input.cbox:disabled", $(this)[0]);
	            cbs.removeAttr("checked");
	            var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
			        if($("#jqg_mergeUserGridTableId_"+allRowsIds[i],grid).is(":disabled")){	
			        	grid.jqGrid('setSelection', allRowsIds[i], false);
			        }
			        var result = $.grep(selectedUserArr, function(userO){ return userO.id == allRowsIds[i]; });
			        if(result.length == 0){
			        	 var rData = $('#mergeUserGridTableId').jqGrid('getRowData',allRowsIds[i]);
				    	
					     var userObj =	{ uniqueCommonIdentifier : rData.uniqueCommonIdentifier , firstName : rData.firstName,
					    		  		  surName : rData.surName, email : rData.email,
					    		  		  id : rData.id };
			        	
			        	selectedUserArr.push(userObj);
			        }	
			    }	
	        }
	        else{
	        	var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
	            	var result = $.grep(selectedUserArr, function(userO){ return userO.id == allRowsIds[i]; });
	            	if( result.length == 1 ){
	            		var indx = selectedUserArr.indexOf(result[0]);
	            		selectedUserArr.splice(indx,1);
	            	}	
	            }
	        }
	        enableDisableBtn(selectedUserArr);
	    },
	    beforeRequest: function() {
	    	if(!$('#mergeUserOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
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
	        
	        if($('#mergeUserOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#mergeUserOrgFilter').orgFilter('value'));
	        	$(this).setGridParam({postData: {
	        		'orgChildrenIds': function() {return orgs;}}
	        	});
	        } else if($(this).getGridParam('datatype') == 'json') {
	        		return false;
	        }
	        $("#mergeUserGridTableId").jqGrid('clearGridData');
	    },  
	    //Commented during US16245 : to enable check box  for all
	    loadComplete: function (data) {
	    	 var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
            for(var i=0;i<ids.length;i++)
            {         
                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
            }

            $('#cb_'+tableid).removeAttr('aria-checked');
	    	$("#mergeUserGridTableId").trigger('reloadGrid');
	    	for(var i=0;i<selectedUserArr.length;i++){
		        $("#mergeUserGridTableId").jqGrid('setSelection', selectedUserArr[i].id, true);
		    }
        },
        gridComplete: function (data) {
	    	 var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'surName')+ ' Check Box');
	            }
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
	
	setTimeout("aart.clearMessages()", 0);
}
		
	
jQuery("#mergeUserNextButton").on("click",function(){
	

	 openMergeUserPopup();

});

/**
 * This method is called when user clicks on the edit button. This method is responsible for displaying the edit user overlay
 * with all the information prepopulated as per existing data for the user e.g. user details, organization details, role details etc.
 */
function openMergeUserPopup() { 
	isMergeScreen=true;
	$("#btn_editMergingUser").hide();
	$('#mduplicateUserError').hide();
	$('#mInvalidOrgRoleMessage').hide();
	$('#midOrgUserError').hide();
	aart.clearMessages();
	$( "#editMergingUsers" ).ajaxStop(function() {
		  $("#btn_editMergingUser").show();
	});
	if( selectedUserArr.length != 2 )
	{
		return false;
	}	
	for(var i=0; i < selectedUserArr.length; i++){
		var ret = selectedUserArr[i];
		if(i == 0) {
		$('#meducatorIdentifierL').val(ret.uniqueCommonIdentifier);
		$('#mfirstNameL').val(ret.firstName);
		$('#mlastNameL').val(ret.surName);
		$('#memailAddressL').val(ret.email);
		$('#hdneuserIdL').val(ret.id);
		}
		if( i == 1 ) {
			$('#meducatorIdentifierR').val(ret.uniqueCommonIdentifier);
			$('#mfirstNameR').val(ret.firstName);
			$('#mlastNameR').val(ret.surName);
			$('#memailAddressR').val(ret.email);
			$('#hdneuserIdR').val(ret.id);
		}
	}

	$.when(getFirstMergingUserRoleAjax(selectedUserArr[0].id),getSecondMergingUserRoleAjax(selectedUserArr[1].id)).then(function(data_0,data_1) {
		displayFirstUserRoles(data_0);
		displaySecondUserRoles(data_1);
	
		//$('#btn_editMergingUserL').addClass('ui-state-disabled');
		//$('#btn_editMergingUserR').addClass('ui-state-disabled');
		$('#btn_editMergingUser_Next').attr('disabled','disabled').addClass('ui-state-disabled');
	});

	$('#editMergingUsers').dialog({
		autoOpen: false,
		modal: true,
		resizable: false,
		width: 1000,
		height: 700,
		title: "Select the User to keep",
		create: function(event, ui) { 
	    	var widget = $(this).dialog("widget");
		},	
		close: function(ev, ui) {	
			//$("#mergeUserGridTableId").jqGrid('resetSelection');
	 	}
	}).dialog('open');
}

function displaySecondUserRoles(data){

var side="right";
	if(data == null){
 		alert('Failed');
 		return;
 	} else {
 		
 		var user = data[0].user;
 
 		if(user != null && user != undefined){
			// Disable save button till all org data is loaded.
 			$('#btn_editRecordMergingUserToKeep').attr('disabled','disabled').addClass('ui-state-disabled');
 			
 			/**
 			 * Remove existing organization rows if any except the first organization row
 			 */
 			var rows = $('[id^=eorgRowR]');
 			$(rows).each(function( index, element ) {
				if(index > 0){	        						
					$(element).remove();		        						
				}
			});
 			
 			/**
			 * Create organization rows.
			 */
			var orglist = user.organizations;
			if(orglist != undefined && orglist != null && orglist.length > 0){
				
				/**
				 * Create first organization row.
				 */
				
				var firstOrg = orglist[0];
				var firstOrgHierarchy = firstOrg.hierarchy;
				if(firstOrgHierarchy != undefined && firstOrgHierarchy != null){
					
					var orgHtml = '<table class="_bcg orgrow" id="eorgRowR" data-rowindex="1">' + '</table>';
					
					var shouldShowEditUser=false;
					var id = selectedUserArr[1].id;
					if(selectedUserArr.length == 2){
						if (id!= null && id!=undefined)	{
							$(".full_main #message").hide();
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
						         	//	alert(JSON.stringify(data));
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
						         			var ret = $('#mergeUserGridTableId').jqGrid('getRowData',id);
						         			$('#eeducatorIdentifier').val(ret.uniqueCommonIdentifier);
						        			$('#efirstName').val(ret.firstName);
						        			$('#elastName').val(ret.surName);
						        			$('#eemailAddress').val(ret.email);
						        			$('#userId').val(ret.id);
						        			
						        			/**
						        			 * Create user's roles data.
						        			 */
						        			var orglist = user.organizations;
						        			var rowId = 1;
						        			
						        			// Iterate over all roles and push the data to 
						        			$(data.userRoles).each(function(idx, userRole){
						        				// initialize here.
				        						var rowData = {id: 'Grid2'+(idx+1), state: 0, district: 0, school: 0, assessmentProgram: 0, role: 0, region: 0, Default: false };
				        						
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
												mergeUserRolesGridData.push(rowData);
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
						        			
						        			renderUserRolesGrid('mergeUserGrid2', 'm');
						        			$('#mergeUserGrid2').jqGrid('setGridWidth', '450');
						        			mergeUserRolesGridData = [];
						        			
						        			$('#mergeUserGrid2').setColProp('state', {edittype:"select", formatter:"select", editoptions: { value: statesAllowedForUserAdd,dataEvents:stateDataEvents}
						    				, sorttype: function (cell, obj) {
						    					return  (_.findWhere(allowedStates,{key:cell})) ? _.findWhere(allowedStates,{key:cell}).value : '';
						    			    }});
						        			$('#mergeUserGrid2').setColProp('assessmentProgram', {edittype:"select", formatter:"select", editoptions: { value: apsAllowedForUserAdd}
						    				, sorttype: function (cell, obj) {
						    					return  (_.findWhere(allowedAps,{key:cell})) ? _.findWhere(allowedAps,{key:cell}).value : '';
						    			    }});
						        			$('#mergeUserGrid2').setColProp('role', {edittype:"select", formatter:"select", editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents}
						    				, sorttype: function (cell, obj) {
						    					return  (_.findWhere(allowedRoles,{key:cell})) ? _.findWhere(allowedRoles,{key:cell}).value : '';
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
						        			$('#mergeUserGrid2').setColProp('region', {edittype:"select", formatter:"select", editoptions: { value: allowedRegionsForUserAdd,dataEvents:regionDataEvents}
						        			, sorttype: function (cell, obj) {
						    					return  (_.findWhere(allowedRegions,{key:cell})) ? _.findWhere(allowedRegions,{key:cell}).value : '';
						    			    }});
						        			$('#mergeUserGrid2').setColProp('district', {edittype:"select", formatter:"select", editoptions: { value: allowedDistrictsForUserAdd,dataEvents:districtDataEvents}
						        			, sorttype: function (cell, obj) {
						    					return  (_.findWhere(allowedDistricts,{key:cell})) ? _.findWhere(allowedDistricts,{key:cell}).value : '';
						    			    }});
						        			$('#mergeUserGrid2').setColProp('school', {edittype:"select", formatter:"select", editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents}
						        			, sorttype: function (cell, obj) {
						    					return  (_.findWhere(allowedSchools,{key:cell})) ? _.findWhere(allowedSchools,{key:cell}).value : '';
						    			    }});
						        			$('#mergeUserGrid2').trigger('reloadGrid');
						         		}
						         	}
						         });
						}
					}
				}
			}
			//Enable save button as all org data is loaded.
			$('#btn_editRecordMergingUserToKeep').removeAttr('disabled').removeClass('ui-state-disabled');
 		}
 	}
	var row = new Array();
	
	var flag=false;
	
$(data[0].user.groupsList).each(function(i,e)
		{
	row.push(data[0].user.groupsList[i].groupName);
		});

$(row).each(function(i,e)
		{
	if(row[i]=="Teacher")
		{
		flag=true;
		}
		});
$('#mergeUserRoosterGrid2').jqGrid("GridUnload"); 
$('#mergeUserRoosterGrid2').jqGrid('clearGridData');
	if(flag)
	{
		
		thisIsRoosterGrid('mergeUserRoosterGrid2','viewRosterGridPager2');
		if($('#mergeUserOrgFilterForm').valid() && $('#mergeUserOrgFilter').orgFilter('value') != null) {
			var $gridAuto = $("#mergeUserRoosterGrid2");
			var orgs = new Array();
	    	orgs.push($('#mergeUserOrgFilter').orgFilter('value'));
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'findRostersforTeacherIdInCurrentYearForMergeUser.htm?q=1',
				search: false, 
				postData: { "filters": "",id},
			}).trigger("reloadGrid",[{page:1}]);
		}
	}
	securityAgreement(id,side);
	trainingComplete(id,side);
/*	 document.getElementById("left_security").value="left_security";
	 document.getElementById("right_security").value="right_security";
	 x=document.getElementById("left_security").value;
	alert(x);*/
    setTimeout(function(){
    //	$("#popup-new :input:not([id^=btn_editMergingUser])").attr("disabled", true);
    	
    	$("#popup-new table[id^=eexisting_rolesL_table_] td[aria-describedby$=_allowedToAssign]").html("false");
		$("#popup-new table[id^=eexisting_rolesL_table_] td[aria-describedby$=_allowedToAssign]").attr("title","false");
		
		$("#popup-new table[id^=eexisting_rolesR_table_] td[aria-describedby$=_allowedToAssign]").html("false");
		$("#popup-new table[id^=eexisting_rolesR_table_] td[aria-describedby$=_allowedToAssign]").attr("title","false");
    	
    	var widthdefaultOrg = $('#eaddUserFilterL_1').parent("td").width();
		var heightdefaultOrg = $('#eaddUserFilterL_1').parent("td").height();

		$('.overlapdisableedefaultOrg').css({
		            "position": "absolute",
		            "width": widthdefaultOrg,
		            "height": heightdefaultOrg,
		            "margin-top":"0px",
		            "z-index":999,
		});
    },500);
    
}
	
function displayFirstUserRoles(data){

	var side="left";
	if(data == null){
 		alert('Failed');
 		return;
 	} else {
 		var user = data[0].user;
 		if(user != null && user != undefined){
			// Disable save button till all org data is loaded.
 			$('#btn_editRecordMergingUserToKeep').attr('disabled','disabled').addClass('ui-state-disabled');
 			
 			/**
 			 * Remove existing organization rows if any except the first organization row
 			 */
 			var rows = $('[id^=eorgRowL]');
 			$(rows).each(function( index, element ) {
				if(index > 0){	        						
					$(element).remove();		        						
				}
			});
 	
			/**
		 	* Create organization rows.
		 	*/
			var orglist = user.organizations;
			if(orglist != undefined && orglist != null && orglist.length > 0){
			
			/**
			 * Create first organization row.
			 */
			
			var firstOrg = orglist[0];
			var firstOrgHierarchy = firstOrg.hierarchy;
			$(".orgrow").remove();
			if(firstOrgHierarchy != undefined && firstOrgHierarchy != null){
				var orgHtml = '<table class="_bcg orgrow" role="presentation" id="eorgRowL" data-rowindex="1">' + '</table>';
				
					
				var shouldShowEditUser=false;
				var id = selectedUserArr[0].id;
				//var selectRowLength = $('#mergeUserGridTableId').jqGrid ('getGridParam','selarrrow').length;
				if(selectedUserArr.length == 2){
					if (id!= null && id!=undefined)	{
						$(".full_main #message").hide();
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
					         			var ret = $('#mergeUserGridTableId').jqGrid('getRowData',id);
					         			$('#eeducatorIdentifier').val(ret.uniqueCommonIdentifier);
					        			$('#efirstName').val(ret.firstName);
					        			$('#elastName').val(ret.surName);
					        			$('#eemailAddress').val(ret.email);
					        			$('#userId').val(ret.id);
					        			
					        			/**
					        			 * Create user's roles data.
					        			 */
					        			var orglist = user.organizations;
					        			var rowId = 1;
					        			
					        			// Iterate over all roles and push the data to 
					        			$(data.userRoles).each(function(idx, userRole){
					        				// initialize here.
			        						var rowData = {id: 'Grid1'+(idx+1), state: 0, district: 0, school: 0, assessmentProgram: 0, role: 0, region: 0, Default: false };
			        						
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
											mergeUserRolesGridData.push(rowData);
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
					        			renderUserRolesGrid('mergeUserGrid1', 'm');
					        			$('#mergeUserGrid1').jqGrid('setGridWidth', '450');
					        			mergeUserRolesGridData = [];
					        			
					        			$('#mergeUserGrid1').setColProp('state', {edittype:"select", formatter:"select", editoptions: { value: statesAllowedForUserAdd,dataEvents:stateDataEvents}
					        			, sorttype: function (cell, obj) {
					    					return  (_.findWhere(allowedStates,{key:cell})) ? _.findWhere(allowedStates,{key:cell}).value : '';
					    			    }});
					        			$('#mergeUserGrid1').setColProp('assessmentProgram', {edittype:"select", formatter:"select", editoptions: { value: apsAllowedForUserAdd}
					        			, sorttype: function (cell, obj) {
					    					return  (_.findWhere(allowedAps,{key:cell})) ? _.findWhere(allowedAps,{key:cell}).value : '';
					    			    }});
					        			$('#mergeUserGrid1').setColProp('role', {edittype:"select", formatter:"select", editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents}
					        			, sorttype: function (cell, obj) {
					    					return  (_.findWhere(allowedRoles,{key:cell})) ? _.findWhere(allowedRoles,{key:cell}).value : '';
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
					        			$('#mergeUserGrid1').setColProp('region', {edittype:"select", formatter:"select", editoptions: { value: allowedRegionsForUserAdd,dataEvents:regionDataEvents}
					        			, sorttype: function (cell, obj) {
					    					return  (_.findWhere(allowedRegions,{key:cell})) ? _.findWhere(allowedRegions,{key:cell}).value : '';
					    			    }});
					        			$('#mergeUserGrid1').setColProp('district', {edittype:"select", formatter:"select", editoptions: { value: allowedDistrictsForUserAdd,dataEvents:districtDataEvents}
					        			, sorttype: function (cell, obj) {
					    					return  (_.findWhere(allowedDistricts,{key:cell})) ? _.findWhere(allowedDistricts,{key:cell}).value : '';
					    			    }});
					        			$('#mergeUserGrid1').setColProp('school', {edittype:"select", formatter:"select", editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents}
					        			, sorttype: function (cell, obj) {
					    					return  (_.findWhere(allowedSchools,{key:cell})) ? _.findWhere(allowedSchools,{key:cell}).value : '';
					    			    }});
					        			$('#mergeUserGrid1').trigger('reloadGrid');
					         		}
					         	}
					         });
						}
					}
				}
			}
			//Enable save button as all org data is loaded.
			$('#btn_editRecordMergingUserToKeep').removeAttr('disabled').removeClass('ui-state-disabled');
 		}
 	}	
	
	
	var row = new Array();
	
	var flag=false;
	
$(data[0].user.groupsList).each(function(i,e)
		{
	row.push(data[0].user.groupsList[i].groupName);
		});

$(row).each(function(i,e)
		{
	if(row[i]=="Teacher")
		{
		flag=true;
		}
		});
$('#mergeUserRoosterGrid').jqGrid("GridUnload");
$('#mergeUserRoosterGrid').jqGrid('clearGridData');
	if(flag)
	{
		
		thisIsRoosterGrid('mergeUserRoosterGrid','viewRosterGridPager1');
		if($('#mergeUserOrgFilterForm').valid() && $('#mergeUserOrgFilter').orgFilter('value') != null) {
			var $gridAuto = $("#mergeUserRoosterGrid");
			var orgs = new Array();
	    	orgs.push($('#mergeUserOrgFilter').orgFilter('value'));
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'findRostersforTeacherIdInCurrentYearForMergeUser.htm?q=1',
				search: false, 
				postData: { "filters": "",id},
			}).trigger("reloadGrid",[{page:1}]);
		}

	}
	securityAgreement(id,side);
	trainingComplete(id,side);
}
	
function getFirstMergingUserRoleAjax(userId){
	return $.ajax({
		url: 'getAllMergingUserDetails.htm',
		data: '&userId=' + userId,
		dataType: "json",
		type: 'POST',
	});
}	
	
function getSecondMergingUserRoleAjax(userId){
	return $.ajax({
		url: 'getAllMergingUserDetails.htm',
		data: '&userId=' + userId,
		dataType: "json",
		type: 'POST',
	});
}
	
function enableDisableBtn(selectedRowId){
	if(selectedRowId.length == 2){
		$("#mergeUserNextButton").prop("disabled",false);
		$('#mergeUserNextButton').removeClass('ui-state-disabled');
	}else{
		$("#mergeUserNextButton").prop("disabled",true);
		$('#mergeUserNextButton').addClass('ui-state-disabled');	
	} 
}

function thisIsRoosterGrid(GridId,PagerId) {

var gridPager= '#'+PagerId;
	var $gridAuto = $('#'+GridId);
	//Unload the grid before each request.
	$gridAuto.jqGrid('clearGridData');
	$gridAuto.jqGrid("GridUnload");
	var gridWidthForVR = $gridAuto.parent().width();		
	if(gridWidthForVR < 740) {
		gridWidthForVR = 740;				
	}
	var cellWidthForVR = gridWidthForVR/5;
	var showHideCourse = false;
	if($('#currentAssessmentProgram').val()=="PLTW"){
		showHideCourse = true;
	}
	var cmforViewRosters = [	         		       
	                        { name : 'id', index : 'id',label:'viewrosterID', width : cellWidthForVR, search : false, hidden: true, hidedlg: false },
	        				{ name : 'name', index : 'name',label:'rosterName', width : cellWidthForVR, search : true, hidden: false, hidedlg: false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'edUniqueCommonId', index : 'edUniqueCommonId', width : cellWidthForVR, search : true, hidden : false, hidedlg : false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'firstName', index : 'firstName', label : 'view_roster_educator_firstname', width : cellWidthForVR, search : true, hidden : false, hidedlg : false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'lastName', index : 'lastName', label : 'view_roster_educator_lastname', width : cellWidthForVR, search : true, hidden : false, hidedlg : false,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'contentArea', index : 'contentArea', width : cellWidthForVR, search : true, hidden : false, hidedlg : false},
	        				{ name : 'stateCoursesName', index : 'stateCoursesName', width : cellWidthForVR, search : true, hidden : showHideCourse, hidedlg : showHideCourse},
	        				{ name : 'edId', index : 'edId', width : 0, search : true, hidden : true, hidedlg : true,
	                        	formatter: escapeHtml
	                        },
	        				{ name : 'contentAreaId', index : 'contentAreaId', width : 0, search : true, hidden : true, hidedlg : true},
	        				{ name : 'edStatus', index : 'edStatus', width : 0, search : false, hidden : false, hidedlg : true,sort: false},
	        				{ name : 'stateCoursesId', index : 'stateCoursesId', width : 0, search : true, hidden : true, hidedlg : true},
	        				{ name : 'currentSchoolYear', index : 'currentSchoolYear', width : 0, search : false, hidden : false, hidedlg : true},
	        				{ name : 'schoolName', index : 'schoolName', width : 0, search : true, hidden : false, hidedlg : true,
	                        	formatter: escapeHtml
	                        },
	                        // US16275 Add field
	                        { name : 'schoolId', index : 'schoolId', width : cellWidthForVR, search : false, hidden: true, hidedlg: false},
	        			];
	
	var cnforViewRosters = [
						"Roster Id",    // US16275 Rename the field
						"Roster Name",
						"Educator Identifier",
						"First Name",
						"Last Name",
						$('#currentAssessmentProgram').val()=="PLTW" ? 'Course' : 'Subject',
						"Course",
						'edId',
						'contentAreaId',
						'Educator Status',
						"Course Id",
						"School Year",
						"School Name",
						"School Id"    // US16275 Add Field
					];	

	//JQGRID
	$gridAuto.scb({
		//url : 'getRostersToViewForMergeUser.htm?q=1',
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVR,
	  	colModel :cmforViewRosters,
	  	colNames: cnforViewRosters,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : gridPager,
		sortname : 'name',
		loadonce: false,
		emptyrecords:"No Roster available for this User"
	});
}
function securityAgreement(id,side)
{

var prt="";
$.ajax({
    url: 'getSecurityAgreement.htm',
    data: '&id='+ id,
    async: false,  
    dataType: "json",
    type: 'POST',
}).done(function(data)
		{
	debugger;
	if(data != undefined && data != null){
          prt=data;
          if(side=="left")
        	  {
        	  if(!jQuery.isEmptyObject(prt)){
        	  (prt.isagreed) ? $("#agreedL").html("Agreed on") : $("#agreedL").html("Do Not Agree On") 
        	  $("#expireidL").html(prt.expiredate)
        	  $("#signerL").html(prt.signername)
        	  $("#byL").show();}
        	  else
        		  {$("#agreedL").text("");
        		  $("#expireidL").text("");
            	  $("#signerL").text("");
            	  $("#byL").hide();
        		  }
        		  }
          else
        	  {
        	  if(!jQuery.isEmptyObject(prt)){
        	  (prt.isagreed) ? $("#agreedR").html("Agreed on") : $("#agreedR").html("Do Not Agree On") 
        	  $("#expireidR").html(prt.expiredate);
        	  $("#signerR").html(prt.signername);
        	  $("#byR").show();}
        	  else{$("#agreedR").text("");
        		  $("#expireidR").text("");
            	  $("#signerR").text("");
            	  $("#byR").hide();
        	  }
        		  
	}
	}
});
}
function trainingComplete(id,side)
{
var frt="";
	$.ajax({
		url:'getTrainingCompletion.htm',
		data: '&id='+id,
		async: false,
		dataType:"json",
		type:'POST',
	    }).done(function(data)
			{
			frt=data;
			if(side=="left"){
				if(!jQuery.isEmptyObject(frt)){
				frt.isagreed=="t" ?  $("#trainingL").html("Yes") :  $("#trainingL").html("NO")
				$("#dateL").html(frt.agreedate);
				$("#onL").show();
			}else{
					$("#trainingL").text("");
				    $("#dateL").text("");
				    $("#onL").hide();
			}
			}else
				{
				if(!jQuery.isEmptyObject(frt)){
				frt.isagreed=="t" ?  $("#trainingR").html("Yes") :  $("#trainingR").html("NO")
						$("#dateR").html(frt.agreedate);
				$("#onR").show();
				}else
				{
				$("#trainingR").text("");
				$("#dateR").text("");
				 $("#onR").hide();
				}
				}});
}










