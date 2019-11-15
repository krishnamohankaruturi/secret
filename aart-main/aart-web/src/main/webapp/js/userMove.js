/**
 * Sudhansu.b : Added for US16821(provide UI TO move Users) 
 * Contains all javascript needful for Move user functionality.
 */
 var selectedorgId = null;
 var selectedDistrictName = '';
 var selectedSchoolName = '';
 var selectedSchoolId = null;
 var selectedDistrictId = null;
 var selectedUserArrForMove = [];

function move_Init_User_tab() {
	$('#moveUserOrgFilter').orgFilter({
		'containerClass': '',
		requiredLevels: [20]
	});
	
	if(globalUserLevel <= 50){
		$('#moveUserOrgFilter').orgFilter('option','requiredLevels',[50]);
	}
	
	$('#moveUserOrgFilterForm').validate({ignore: ""});
	
	filteringOrganizationSet($('#moveUserOrgFilterForm'));
	
//} 
//$(function() {	   	
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
		
		buildMoveOrgGrid();
		buildMoveUsersOrganizationGrid();
	
	$('#moveUserNextButton').off('click').on('click',function(e){
		e.preventDefault();
		
	    openMoveUserPopup();
	    
	});
	
	$('#moveUsersOrganizationNext').off('click').on('click',function(e){
		
		var userIds = [];
		var newOrganizationId = null;
		var oldOrganizationId = null;
		
		for(var i=0;i<selectedUserArrForMove.length;i++){
			userIds.push(selectedUserArrForMove[i].id);
		    }
		
		
		if(userIds == null || userIds.length <=0){
			$("#selectAtleastOneUserToMove").show();
			setTimeout(function() {
				$("#selectAtleastOneUserToMove").hide();
			}, 2000);
			return false;
		}
		
		if(selectedSchoolId == '' || selectedSchoolId == null){
			newOrganizationId = $('#moveUsersOrganizationNewDistrict').val();
			oldOrganizationId = selectedDistrictId;
		}else{
			newOrganizationId = $('#moveUsersOrganizationNewSchool').val();
			oldOrganizationId = selectedSchoolId;
		}
		
		if(newOrganizationId == '' || newOrganizationId == null){
			$("#selectOrganizationToMove").show();
			setTimeout(function() {
				$("#selectOrganizationToMove").hide();
			}, 2000);
			return false;
		}
		

				 $.ajax({
			url : 'moveUser.htm',
			dataType : 'json',
			type : "POST",
			data : {
				"userIds" : userIds,
				"newOrganizationId" : newOrganizationId,
				"oldOrganizationId" : oldOrganizationId,
			}
		}).done(function(data) {
			if (data.success) {
				$('#moveUserOrganizationInfo').dialog('close');
				$("#UserARTSmessages").show();
				if (data.updatedAll === "false") {
					$('#moveUserPartial').show();
				} else {
					$('#moveUserSuccessful').show();
				}
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			} else if (data.orgExist) {
				$("#selectedOrganizationExist").show();
				setTimeout(function() {
					$("#selectedOrganizationExist").hide();
				}, 2000);
			} else if (data.errorFound) {
				$('#moveUserOrganizationInfo').dialog('close');
				$("#UserARTSmessages").show();
				$('#moveUserInternalError').show();
				setTimeout("aart.clearMessages()", 3000);
				setTimeout(function() {
					$("#UserARTSmessages").hide();
				}, 3000);
			}

		});
	});
	
	$('#moveUsersOrganizationNewDistrict, #moveUsersOrganizationNewSchool').select2({
		placeholder:'Select',
		multiple: false
	});
	$('#moveUserButton').on("click",function(event) {
		event.preventDefault();
		moveEnableDisableBtn(0);
		selectedUserArrForMove = [];
		if($('#moveUserOrgFilterForm').valid()) {
			var $gridAuto = $("#moveUserGridTableId");
			$gridAuto[0].clearToolbar();
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getUsersByOrganization.htm?q=1',
				search: false, 
				postData: { "filters": "",
					         "requestFor":"move"}
			}).trigger("reloadGrid",[{page:1}]);
		}
	});
	
	function buildMoveOrgGrid() {
		
		var $gridAuto = $("#moveUserGridTableId");
		
		//Unload the grid before each request.
		$("#moveUserGridTableId").jqGrid('clearGridData');
		$("#moveUserGridTableId").jqGrid("GridUnload");
		
		var gridWidthForVO = $gridAuto.parent().width();		
		if(gridWidthForVO < 760) {
			gridWidthForVO = 760;				
		}
		var cellWidthForVO = gridWidthForVO/5;
		
		

		var cmforViewUserGrid = [
			{ name : 'id',label:'moveUser_id', index : 'id', width : cellWidthForVO, search : false, hidden: true, key: true, hidedlg: false},			
			/*{ name : 'statusCode', index : 'statusCode', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},*/
			
			/*Changed during  US16247 */
			{ name : 'statusCode',label:'moveUser_statusCode', index : 'statusCode', width : cellWidthForVO, search : true , stype : 'select', searchoptions: { value : ':All;Active:Active;Inactive:Inactive;Pending:Pending', sopt:['eq'] }, hidden : false, hidedlg : false},
			{ name : 'uniqueCommonIdentifier',label:'moveUser_uniqueCommonIdentifier', index : 'uniqueCommonIdentifier', width : cellWidthForVO, search : true, hidden: false, hidedlg: false,
				formatter: escapeHtml
			},
			{ name : 'firstName',label:'moveUser_firstName', index : 'firstName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'surName',label:'moveUser_surName', index : 'surName', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},				
			{ name : 'email',label:'moveUser_email', index : 'email', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'programNames',label:'moveUser_programNames', index : 'programNames', width : cellWidthForVO, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
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
			pager : '#moveUserGridPager',
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
			      var rData = $('#moveUserGridTableId').jqGrid('getRowData',rowid);
			      var userObj =	{  
		   		  		  id : rData.id,statusCode :rData.statusCode,uniqueCommonIdentifier : rData.uniqueCommonIdentifier ,
		   		  		  firstName : rData.firstName,surName : rData.surName,email : rData.email, programNames : rData.programNames,
		   		  		  };


			    	
	         	   if(status){
	         		 var found = false; 
	         		 
	         		 $.each(selectedUserArrForMove, function(i){
	       			    if(selectedUserArrForMove[i].id === rData.id) {
	       			    	found = true;
	       			    }
	       			 }); 
	         		 if( ! found )
	         			 selectedUserArrForMove.push(userObj);
	         		   
	         		  var grid = $(this);
	         		  var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox", grid);
	         		   //if selected row and checkbox is disabled, do not let it get checked.
	         		   if(cbsdis.is(":disabled")){
	         			  cbsdis.prop('checked', false);         			   
	         		   }
	         	   } else{
	         		   $.each(selectedUserArrForMove, function(i){
	         			    if(selectedUserArrForMove[i].id === rData.id) {
	         			    	selectedUserArrForMove.splice(i,1);
	         			        return false;
	         			    }
	         			});
	         		  
	         		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
	         		   //do nothing.
	         	   }  
	         	  moveEnableDisableBtn(selectedUserArrForMove);
	         	},
			    onSelectAll: function(aRowids,status) {
			    	var grid = $(this);
			        if (status) {
			            // uncheck "protected" rows
			            var cbs = $("tr.jqgrow > td > input.cbox:disabled", $(this)[0]);
			            cbs.removeAttr("checked");
			            var allRowsIds = grid.jqGrid('getDataIDs');
			            for(var i=0;i<allRowsIds.length;i++){
			            	//console.log(allRowsIds[i], $("#jqg_viewUserGridTableId_"+allRowsIds[i]).is(":disabled"));
					        if($("#jqg_moveUserGridTableId_"+allRowsIds[i],grid).is(":disabled")){	
					        	grid.jqGrid('setSelection', allRowsIds[i], false);
					        }
					        var result = $.grep(selectedUserArrForMove, function(userO){ return userO.id == allRowsIds[i]; });
					        if(result.length == 0){
					        	 var rData = $('#moveUserGridTableId').jqGrid('getRowData',allRowsIds[i]);
						    	
					        	 var userObj =	{  
					   		  		  id : rData.id,statusCode :rData.statusCode,uniqueCommonIdentifier : rData.uniqueCommonIdentifier ,
					   		  		  firstName : rData.firstName,surName : rData.surName,email : rData.email, programNames : rData.programNames,
					   		  		  };


					        	
					        	selectedUserArrForMove.push(userObj);
					        }	
					    }	
			        }
			        else{
			        	var allRowsIds = grid.jqGrid('getDataIDs');
			            for(var i=0;i<allRowsIds.length;i++){
			            	var result = $.grep(selectedUserArrForMove, function(userO){ return userO.id == allRowsIds[i]; });
			            	if( result.length == 1 ){
			            		var indx = selectedUserArrForMove.indexOf(result[0]);
			            		selectedUserArrForMove.splice(indx,1);
			            	}	
			            }
			        }
			        moveEnableDisableBtn(selectedUserArrForMove);
			    },
			    beforeRequest: function() {
			    	if(!$('#moveUserOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
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
			        
			        if($('#moveUserOrgFilter').orgFilter('value') != null)  {
			        	var orgs = new Array();
			        	orgs.push($('#moveUserOrgFilter').orgFilter('value'));
			        	$(this).setGridParam({postData: {
			        		'orgChildrenIds': function() {return orgs;}}
			        	});
			        	selectedDistrictId = $('#moveUserOrgFilter_district').val();
			        	selectedorgId = $('#moveUserOrgFilter_state').val();
			        	selectedSchoolId = $('#moveUserOrgFilter_school').val();
			        	selectedDistrictName = $('#moveUserOrgFilter_district option:selected').text();
			        	selectedSchoolName = $('#moveUserOrgFilter_school option:selected').text();
			        } else if($(this).getGridParam('datatype') == 'json') {
			        		return false;
			        }
			        $("#moveUserGridTableId").jqGrid('clearGridData');
			    },
		    //Commented during US16245 : to enable check box  for all
		    loadComplete: function (data) {
		    	$("#moveUserGridTableId").trigger('reloadGrid');
		    	for(var i=0;i<selectedUserArrForMove.length;i++){
			        $("#moveUserGridTableId").jqGrid('setSelection', selectedUserArrForMove[i].id, true);
			    }
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
            },
		});
		
		$gridAuto.jqGrid('setGridParam',{
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
		
		// Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
			
		
	}	
}
//});

function openMoveUserPopup(){
	    $('#moveUsersOrganizationNewSchoolDiv').show();
		$('#moveUserOrganizationOldSchoolDiv').show();
	   populateDistrict();
	   $("#moveUserOrganizationInfoGridTableId").trigger('destroy');
	   populateSelectedUsersForMove();
	   //$("#moveUserOrganizationInfoGridTableId").trigger('reloadGrid');
	   $('#moveUserOrganizationOldDistrict').html(selectedDistrictName);
	   $('#moveUserOrganizationOldSchool').html(selectedSchoolName);
	   
		$('#moveUserOrganizationInfo').dialog({
			autoOpen: false,
			modal: true,
			resizable: false,
			width: 1000,
			height: 700,
			title: "Select the Organization to transfer to",
			create: function(event, ui) { 
		    	var widget = $(this).dialog("widget");
			},	
			close: function(ev, ui) {
				 $("#moveUserOrganizationInfoGridTableId").jqGrid('clearGridData');
				if($("#moveUserGridTableId")[0].grid && $("#moveUserGridTableId")[0]['clearToolbar']){
				   $("#moveUserGridTableId")[0].clearToolbar();
			     } 
				$("#moveUserGridTableId").jqGrid('setGridParam',{
					postData: { "filters": ""}
				}).trigger("reloadGrid",[{page:1}]);
				selectedUserArrForMove = [];
				$("#moveUserNextButton").prop("disabled",true);
				$('#moveUserNextButton').addClass('ui-state-disabled');	
		 	}
		}).dialog('open');
}

function populateDistrict(){
	if(selectedSchoolId == '' || selectedSchoolId == null){
		$('#moveUsersOrganizationNewSchoolDiv').hide();
		$('#moveUserOrganizationOldSchoolDiv').hide();
	}
	var tsDistrictOrgselect = $('#moveUsersOrganizationNewDistrict');
	$('#moveUsersOrganizationNewSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#moveUsersOrganizationNewDistrict').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	
	$.ajax({
		url: 'getChildOrgsWithParentForFilter.htm',
		dataType: 'json',
		data: {
			orgId : selectedorgId,
        	orgType:'DT'
        	},				
		type: "POST"
	}).done(function(tsDistrictOrgs) {				
		if (tsDistrictOrgs !== undefined && tsDistrictOrgs !== null && tsDistrictOrgs.length > 0) {
			$.each(tsDistrictOrgs, function(i, tsDistrictOrg) {
				tsOptionText = tsDistrictOrgs[i].organizationName;
				tsDistrictOrgselect.append($('<option></option>').val(tsDistrictOrg.id).html(tsOptionText));
			});
			
			if (tsDistrictOrgs.length == 1) {
				tsDistrictOrgselect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				if(selectedSchoolId != '' && selectedSchoolId != null){
				   $("#moveUsersOrganizationNewDistrict").trigger('change');
				}
			}
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#tsSearchFilterErrors').html("No District Organizations Found for the current user").show();
		}
		$('#moveUsersOrganizationNewSchool, #moveUsersOrganizationNewDistrict').val("").trigger('change.select2');
	});
	
	$('#moveUsersOrganizationNewDistrict').on("change",function() {  
		if(selectedSchoolId != '' && selectedSchoolId != null){
		$('#moveUsersOrganizationNewSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#moveUsersOrganizationNewSchool').trigger('change.select2');
		var districtOrgId = $('#moveUsersOrganizationNewDistrict').val();
		if (districtOrgId != 0) {
			$.ajax({
		        url: 'getChildOrgsWithParentForFilter.htm',
		        data: {
		        	orgId : districtOrgId,
		        	orgType:'SCH'
		        	},
		        dataType: 'json',
		        type: "POST"
			}).done(function(tsSchoolOrgs) {
	        	
				$.each(tsSchoolOrgs, function(i, schoolOrg) {
					$('#moveUsersOrganizationNewSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				});
				
				if (tsSchoolOrgs.length == 1) {
					$("#moveUsersOrganizationNewSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
				}							
				$('#moveUsersOrganizationNewSchool').trigger('change.select2');
	        });
		}
		}
	});
	
	filteringOrganization($('#moveUsersOrganizationNewDistrict'));
	filteringOrganization($('#moveUsersOrganizationNewSchool'));
	
}


function buildMoveUsersOrganizationGrid(){
		var $gridAuto = $("#moveUserOrganizationInfoGridTableId");
		
		//Unload the grid before each request.
		$("#moveUserOrganizationInfoGridTableId").jqGrid('clearGridData');
		$("#moveUserOrganizationInfoGridTableId").jqGrid("GridUnload");
		
		var gridWidth = 530;
		var cellWidth = gridWidth/6;
		
		
		

		var cmforMoveUserOrganizationGrid = [
			{ name : 'id', index : 'id',label:'move_user_id', width : cellWidth, search : false, hidden: true, key: true, hidedlg: false},			
			/*{ name : 'statusCode', index : 'statusCode', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},*/
			
			/*Changed during  US16247 */
			{ name : 'statusCode', index : 'statusCode',label:'move_user_statusCode', width : cellWidth, search : true , stype : 'select', searchoptions: { value : ':All;Active:Active;Inactive:Inactive;Pending:Pending', sopt:['eq'] }, hidden : false, hidedlg : false},
			{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier',label:'move_user_uniqueCommonIdentifier', width : cellWidth, search : true, hidden: false, hidedlg: false,
				formatter: escapeHtml
			},
			{ name : 'firstName', index : 'firstName',label:'move_user_firstName', width : cellWidth, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'surName', index : 'surName',label:'move_user_surName', width : cellWidth, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},				
			{ name : 'email', index : 'email',label:'move_user_email', width : cellWidth, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
			{ name : 'programNames', index : 'programNames',label:'move_user_programNames', width : cellWidth, search : true, hidden : false, hidedlg : false,
				formatter: escapeHtml
			},
		];
		
		$gridAuto.scb({
			datatype : "local",
			width: gridWidth,
		  	colModel :cmforMoveUserOrganizationGrid,
		  	colNames: ["User Id",       // Changed During US16242
			           "Status",
					   "Educator Identifier",
					   "First Name",
					   "Last Name",					   
					   "Email",
					   "Assessment Programs"],
			rowNum : 10,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			pager : '#moveUserOrganizationInfoGridPager',
			sortname: 'firstName',
			sortorder: 'desc',
			loadonce: false,
			viewrecords : true,
			multiselect: true,
			beforeRequest: function() {
			},
			 onSelectRow: function(rowid, status, e){
			      var rData = $('#moveUserOrganizationInfoGridTableId').jqGrid('getRowData',rowid);
			      var userObj =	{  
		   		  		  id : rData.id,statusCode :rData.statusCode,uniqueCommonIdentifier : rData.uniqueCommonIdentifier ,
		   		  		  firstName : rData.firstName,surName : rData.surName,email : rData.email, programNames : rData.programNames,
		   		  		  };


			    	
	         	   if(status){
	         		 var found = false; 
	         		 
	         		 $.each(selectedUserArrForMove, function(i){
	       			    if(selectedUserArrForMove[i].id === rData.id) {
	       			    	found = true;
	       			    }
	       			 }); 
	         		 if( ! found )
	         			 selectedUserArrForMove.push(userObj);
	         		   
	         		  var grid = $(this);
	         		  var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox", grid);
	         		   //if selected row and checkbox is disabled, do not let it get checked.
	         		   if(cbsdis.is(":disabled")){
	         			  cbsdis.prop('checked', false);         			   
	         		   }
	         	   } else{
	         		   $.each(selectedUserArrForMove, function(i){
	         			    if(selectedUserArrForMove[i].id === rData.id) {
	         			    	selectedUserArrForMove.splice(i,1);
	         			        return false;
	         			    }
	         			});
	         		  
	         		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
	         		   //do nothing.
	         	   }  
	         	},
			    onSelectAll: function(aRowids,status) {
			    	var grid = $(this);
			        if (status) {
			            // uncheck "protected" rows
			            var cbs = $("tr.jqgrow > td > input.cbox:disabled", $(this)[0]);
			            cbs.removeAttr("checked");
			            var allRowsIds = grid.jqGrid('getDataIDs');
			            for(var i=0;i<allRowsIds.length;i++){
			            	//console.log(allRowsIds[i], $("#jqg_viewUserGridTableId_"+allRowsIds[i]).is(":disabled"));
					        if($("#jqg_moveUserOrganizationInfoGridTableId_"+allRowsIds[i],grid).is(":disabled")){	
					        	grid.jqGrid('setSelection', allRowsIds[i], false);
					        }
					        var result = $.grep(selectedUserArrForMove, function(userO){ return userO.id == allRowsIds[i]; });
					        if(result.length == 0){
					        	 var rData = $('#moveUserOrganizationInfoGridTableId').jqGrid('getRowData',allRowsIds[i]);
						    	
					        	 var userObj =	{  
					   		  		  id : rData.id,statusCode :rData.statusCode,uniqueCommonIdentifier : rData.uniqueCommonIdentifier ,
					   		  		  firstName : rData.firstName,surName : rData.surName,email : rData.email, programNames : rData.programNames,
					   		  		  };


					        	
					        	selectedUserArrForMove.push(userObj);
					        }	
					    }	
			        }
			        else{
			        	var allRowsIds = grid.jqGrid('getDataIDs');
			            for(var i=0;i<allRowsIds.length;i++){
			            	var result = $.grep(selectedUserArrForMove, function(userO){ return userO.id == allRowsIds[i]; });
			            	if( result.length == 1 ){
			            		var indx = selectedUserArrForMove.indexOf(result[0]);
			            		selectedUserArrForMove.splice(indx,1);
			            	}	
			            }
			        }
			    },
			loadComplete: function() {
				$("#moveUserOrganizationInfoGridTableId").trigger('reloadGrid');
				 for(var i=0;i<selectedUserArrForMove.length;i++){
				        $("#moveUserOrganizationInfoGridTableId").jqGrid('setSelection', selectedUserArrForMove[i].id, true);
				 }
				 $('#view_moveUserOrganizationInfoGridTableId').off('mousedown').on('mousedown',function(){
					  $('#alertmod').css('z-index','2000');
				});
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
			}
		});			
		$("#moveUserOrganizationInfoGridPager_center").css("float","right");
		$("#moveUserOrganizationInfoGridPager_center").css("margin-right","-30px");
		$("#moveUserOrganizationInfoGridPager_center").css("margin-top","10px");
	}	



function populateSelectedUsersForMove(){
	  $("#moveUserOrganizationInfoGridTableId").jqGrid('clearGridData');
	  $("#moveUserGridTableId").jqGrid('clearGridData');
	  $("#moveUserOrganizationInfoGridTableId").jqGrid('setGridParam',{
			datatype:"json", 
			datatype : "local",
			search: false, 
			data:selectedUserArrForMove
		}) .trigger("reloadGrid",[{page:1}]);
	}
	


function moveEnableDisableBtn(selectedRowId){
	
	if(selectedRowId.length >= 1){	
		$("#moveUserNextButton").prop("disabled",false);
		$('#moveUserNextButton').removeClass('ui-state-disabled');		
	}else{
		$("#moveUserNextButton").prop("disabled",true);
		$('#moveUserNextButton').addClass('ui-state-disabled');	
	} 
	

}

