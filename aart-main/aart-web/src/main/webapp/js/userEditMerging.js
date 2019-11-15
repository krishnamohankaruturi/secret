/**
 * Manoj Kumar O : Added for US_16244(provide UI TO merge Users) 
 * Contains all javascript needful for Merge user functionality.
 */

$(function() {
		/**
		 * Initialize organization filter
		 */
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



	
	$("#editMergingUserR_div").on("click",'#btn_editMergingUserR',function(){
		
//		$('#btn_editMergingUser_Next').removeAttr('disabled').removeClass('ui-state-disabled');
		$('#btn_editMergingUserR').addClass('ui-state-disabled');
		$('#btn_editMergingUserL').removeClass('ui-state-disabled');
		//Enable save button as one User is selected
		$('#btn_editMergingUser_Next').removeAttr('disabled').removeClass('ui-state-disabled');
		
	});
	$("#editMergingUserL_div").on("click",'#btn_editMergingUserL',function(){

		$('#btn_editMergingUserL').addClass('ui-state-disabled');
		$('#btn_editMergingUserR').removeClass('ui-state-disabled');
		//Enable save button as one User is selected
		$('#btn_editMergingUser_Next').removeAttr('disabled').removeClass('ui-state-disabled');
		
	});
	
	$('[id^=default_roleL]').on('click', function (e){
		callDefaultRoleClick(e, 'm');
	});


	$('[id^=default_roleR]').on('click', function (e){
		callDefaultRoleClick(e, 'm');
	});

	/**
	 * Clicks to save user data.
	 */

	$('#btn_editRecordMergingUserToKeep').off('click').on('click', function (e) {
	
	//	$('#moveRosterConfirmationBox').show();
		callSaveUsertoKeepClickSave(e, 'm');

     });
	
		
	
	jQuery("#savePurgingUserRoster").on("click",function(){
		savePurgingUserRoster('m');
	});

	jQuery("#deletePurgingUserRoster").on("click",function(){
	
		jQuery("#moveRosterConfirmationBox").hide();
		jQuery("#purgeUserTd").show();
	});

	jQuery("#btn_purgeUser").on("click",function(){
		deletePurgingUser('m');
		
	});

	
	jQuery("#btn_editMergingUser_Next").on("click",function(){
		
	//	$('#btn_editRecordMergingUserToKeep').addClass('ui-state-disabled');
	//	$("#btn_editRecordMergingUserToKeep").prop("disabled",true);
	//	$('#btn_editRecordMergingUserToKeep').addClass('ui-state-disabled');
	
		$('#purgeUserTd').hide();
		$('#moveRosterConfirmationBox').hide();
		var  keptUser_id;
		var purgeUser_id;
		if($('#btn_editMergingUserL').hasClass('ui-state-disabled'))
		{
			//firstEducatorIdentifier=$("#eeducatorIdentifierR").val();
			keptUser_id = $("#hdneuserIdL").val();  
			purgeUser_id = $("#hdneuserIdR").val();
		}else{
			//firstEducatorIdentifier=$("#eeducatorIdentifierL").val();
			keptUser_id = $("#hdneuserIdR").val();
			purgeUser_id = $("#hdneuserIdL").val();

		}
		openMergeUserToKeepPopup(keptUser_id,purgeUser_id);
	});	
	
	
	function openMergeUserToKeepPopup(keptUser_id,purgeUser_id){

		 	aart.clearMessages();
		 	$('#mInvalidOrganizationRoleMessage').hide();
			
			for(var i=0; i < selectedUserArr.length; i++){
			   var ret = selectedUserArr[i];
				if(i==0){
				if(ret.id == keptUser_id){	
					$('#meducatorIdentifierUTK').val(ret.uniqueCommonIdentifier);
					$('#mfirstNameUTK').val(ret.firstName);
					$('#mlastNameUTK').val(ret.surName);
					$('#memailAddressUTK').val(ret.email);
					$('#muserIdUTK').val(ret.id);
				}else {				
					$('#meducatorIdentifierUTP').val(ret.uniqueCommonIdentifier);
					$('#mfirstNameUTP').val(ret.firstName);
					$('#mlastNameUTP').val(ret.surName);
					$('#memailAddressUTP').val(ret.email);
					$('#muserIdUTP').val(ret.id);
				}
			//var usertoPurge = $('#mergeUserGridTableId').jqGrid('getRowData',purgeUser_id);
							
				}
				if(i==1){
					if(ret.id == purgeUser_id){							
						$('#meducatorIdentifierUTP').val(ret.uniqueCommonIdentifier);
						$('#mfirstNameUTP').val(ret.firstName);
						$('#mlastNameUTP').val(ret.surName);
						$('#memailAddressUTP').val(ret.email);
						$('#muserIdUTP').val(ret.id);					
						
					}else {					
						$('#meducatorIdentifierUTK').val(ret.uniqueCommonIdentifier);
						$('#mfirstNameUTK').val(ret.firstName);
						$('#mlastNameUTK').val(ret.surName);
						$('#memailAddressUTK').val(ret.email);
						$('#muserIdUTK').val(ret.id);						
					}
				//var usertoPurge = $('#mergeUserGridTableId').jqGrid('getRowData',purgeUser_id);
								
					}
				
				
			}
	
			$.when(getFirstMergingUserRoleAjax(keptUser_id),getSecondMergingUserRoleAjax(purgeUser_id)).then(function(keptData,purgeData) {
		
				displayUserToKeepUserRoles(keptData);
				displayUserToPurgeUserRoles(purgeData);
			});

	
		$('#editMergingUsersToKeep').dialog({
			autoOpen: false,
			modal: true,
			resizable: false,
			width: 1000,
			height: 700,
			title: "Edit the User to keep then click ",
				create: function(event, ui) { 
		    	var widget = $(this).dialog("widget");
			},	
			close: function(ev, ui) {
				openMergeUserPopup();
				$('#mergeUserOrganization').val('');
				$('#mergeUserAssessmentProgram').val('');
				$('#mergeUserGroup').val('');
				$('#mergeRoleRegionContainer').hide();
				$('#mergeRoleDistrictContainer').hide();
				$('#mergeRoleSchoolContainer').hide();
				$('#meducatorIdentifierUTK').attr('disabled', false);
    			$('#mfirstNameUTK').attr('disabled', false);
    			$('#mlastNameUTK').attr('disabled', false);
    			$('#memailAddressUTK').attr('disabled', false);
			    $(this).dialog('destroy');
		 	}
		}).dialog('open');
	
		//$("#btn_editRecordMergingUserToKeep").prop("disabled",false);
		//$('#btn_editRecordMergingUserToKeep').removeClass('ui-state-disabled');
		//$('#btn_editRecordMergingUserToKeep').removeClass('ui-state-disabled');
		
	}

	function purgeUserRolesdisablingInputs(){
		$("#eorgTableUTP td[id^=eorgscontainerUTP_td_] div[id^=eaddUserFilterUTP_]").before('<div class="overlapdisableedefaultOrg"></div>');

		$("#eorgTableUTP td[id^=eorgscontainerUTP_td_] input").attr('disabled','disabled').addClass('ui-state-disabled');
		
		$("[id$='UTP']input[type='text']").attr('disabled','true');

		$("[id^=erolescontainerUTP_td_] input[type=checkbox]").attr('disabled','disabled').addClass('ui-state-disabled');
		$("[id^=erolescontainerUTP_td_] input[type=radio]").attr('disabled','disabled').addClass('ui-state-disabled');
		
		$("table[id^=eexisting_rolesUTP_table_] td[aria-describedby$=_allowedToAssign]").html("false");
		$("table[id^=eexisting_rolesUTP_table_] td[aria-describedby$=_allowedToAssign]").attr("title","false");
		
		$("[id^=ebtn_removeOrgUTP_]").attr('disabled','disabled').addClass('ui-state-disabled');

		var widthdefaultOrg = $('td[id^=eorgscontainerUTP_td_1]').width();
		var heightdefaultOrg = $('td[id^=eorgscontainerUTP_td_1]').height();

		$('.overlapdisableedefaultOrg').css({
		   "position": "absolute",
		   "width": widthdefaultOrg,
		   "height": heightdefaultOrg,
		   "margin-top":"0px",
		   "z-index":999,
		});

		$('[id^=ebtn_addOrgUTP_]').attr('disabled','disabled').addClass('ui-state-disabled');
	}
	
	function displayUserToPurgeUserRoles(purgeData)
	{
			if(purgeData == null){
	     		alert('Failed');
	     		return;
	     	} else {
	     		
	     		var user = purgeData[0].user;
		     		if(user != null && user != undefined){
		     			
		    			// Disable save button till all org data is loaded.
		     			$('#btn_editRecordMergingUserToKeep').attr('disabled','disabled').addClass('ui-state-disabled');
		     			
		     			/**
		     			 * Remove existing organization rows if any except the first organization row
		     			 */
		     			var rows = $('[id^=eorgRowUTP]');
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
		    	//			$(".orgrow").remove();
		    				if(firstOrgHierarchy != undefined && firstOrgHierarchy != null){
		    					
		    					var orgHtml = '<table class="_bcg orgrow" id="eorgRowUTP" data-rowindex="1">' + 
		    	                	'</table>';
		    					var shouldShowEditUser=false;
		    					var id = user.id;
		    					//var selectRowLength = $('#mergeUserGridTableId').jqGrid ('getGridParam','selarrrow').length;
		    					if(selectedUserArr.length == 2){
		    					if (id!= null && id!=undefined)	{
		    						$(".full_main #message").hide();
		    						$.ajax({
		    					         url: 'getAllUserDetails.htm',
		    					         data: '&userId=' + id,
		    					         dataType: "json",
		    					         type: 'POST',
		    					         error: function(jqXHR, textStatus, errorThrown){
		    					        	 
		    					         },
		    					         success: function(data){
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
		    			        						var rowData = {id: 'KeepGrid2'+(idx+1), state: 0, district: 0, school: 0, assessmentProgram: 0, role: 0, region: 0, Default: false };
		    			        						
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
		    					        			initializeAllowedRolesForUser('m');
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
		    					        			renderUserRolesGrid('mergeUserToKeepGrid2', 'm');
		    					        			$('#mergeUserToKeepGrid2').jqGrid('setGridWidth', '450');
		    					        			mergeUserRolesGridData = [];
		    					        			
		    					        			$('#mergeUserToKeepGrid2').setColProp('state', {edittype:"select", formatter:"select", editoptions: { value: statesAllowedForUserAdd,dataEvents:stateDataEvents}});
		    					        			$('#mergeUserToKeepGrid2').setColProp('assessmentProgram', {edittype:"select", formatter:"select", editoptions: { value: apsAllowedForUserAdd}});
		    					        			$('#mergeUserToKeepGrid2').setColProp('role', {edittype:"select", formatter:"select", editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents}});
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
		    					        			$('#mergeUserToKeepGrid2').setColProp('region', {edittype:"select", formatter:"select", editoptions: { value: allowedRegionsForUserAdd,dataEvents:regionDataEvents}});
		    					        			$('#mergeUserToKeepGrid2').setColProp('district', {edittype:"select", formatter:"select", editoptions: { value: allowedDistrictsForUserAdd,dataEvents:districtDataEvents}});
		    					        			$('#mergeUserToKeepGrid2').setColProp('school', {edittype:"select", formatter:"select", editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents}});
		    					        			$('#mergeUserToKeepGrid2').trigger('reloadGrid');
		    					         		}
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
			setTimeout(function(){
				purgeUserRolesdisablingInputs();
			},1000);
	}
 
	function displayUserToKeepUserRoles(keptData)
	{
				
			if(keptData == null){
	     		alert('Failed');
	     		return;
	     	} else {
	     		
        		var user = keptData[0].user;
	     
	     		if(user != null && user != undefined){
	     			
	    			// Disable save button till all org data is loaded.
	     			$('#btn_editRecordMergingUserToKeep').attr('disabled','disabled').addClass('ui-state-disabled');
	     			
	     			/**
	     			 * Remove existing organization rows if any except the first organization row
	     			 */
	     			var rows = $('[id^=morgRowUTK]');
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
	    		//		$(".orgrow").remove();
	    				if(firstOrgHierarchy != undefined && firstOrgHierarchy != null){
	    					
	    					var orgHtml = '<table class="_bcg orgrow" id="morgRowUTK" data-rowindex="1">' + 
	    	                	'</table>';
	    				
	    					
	    					
	    					var shouldShowEditUser=false;
	    					var id = user.id;
	    					//var selectRowLength = $('#mergeUserGridTableId').jqGrid ('getGridParam','selarrrow').length;
	    					if(selectedUserArr.length == 2){
	    					if (id!= null && id!=undefined)	{
	    						$(".full_main #message").hide();
	    						$.ajax({
	    					         url: 'getAllUserDetails.htm',
	    					         data: '&userId=' + id,
	    					         dataType: "json",
	    					         type: 'POST',
	    					         error: function(jqXHR, textStatus, errorThrown){
	    					        	 
	    					         },
	    					         success: function(data){
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
	    					        			
	    					        			if(data.doesUserHaveHighRoles){
		    					        			$('#meducatorIdentifierUTK').attr('disabled', 'disabled');
		    					    	        	$('#mfirstNameUTK').attr('disabled', 'disabled');
		    					    	        	$('#mlastNameUTK').attr('disabled', 'disabled');
		    					    	        	$('#memailAddressUTK').attr('disabled', 'disabled');
		    					         		}
	    					        			/**
	    					        			 * Create user's roles data.
	    					        			 */
	    					        			var orglist = user.organizations;
	    					        			var rowId = 1;
	    					        			
	    					        			// Iterate over all roles and push the data to 
	    					        			$(data.userRoles).each(function(idx, userRole){
	    					        				// initialize here.
	    			        						var rowData = {id: 'KeepGrid1'+(idx+1), state: 0, district: 0, school: 0, assessmentProgram: 0, role: 0, region: 0, Default: false };
	    			        						
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
	    					        			initializeAllowedRolesForUser('m');
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
	    					        			renderUserRolesGrid('mergeUserToKeepGrid1', 'm');
	    					        			$('#mergeUserToKeepGrid1').jqGrid('setGridWidth', '450');
	    					        			mergeUserRolesGridData = [];
	    					        			
	    					        			$('#mergeUserToKeepGrid1').setColProp('state', {edittype:"select", formatter:"select", editoptions: { value: statesAllowedForUserAdd,dataEvents:stateDataEvents}});
	    					        			$('#mergeUserToKeepGrid1').setColProp('assessmentProgram', {edittype:"select", formatter:"select", editoptions: { value: apsAllowedForUserAdd}});
	    					        			$('#mergeUserToKeepGrid1').setColProp('role', {edittype:"select", formatter:"select", editoptions: { value: rolesAllowedForUserAdd,dataEvents:roleDataEvents}});
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
	    					        			$('#mergeUserToKeepGrid1').setColProp('region', {edittype:"select", formatter:"select", editoptions: { value: allowedRegionsForUserAdd,dataEvents:regionDataEvents}});
	    					        			$('#mergeUserToKeepGrid1').setColProp('district', {edittype:"select", formatter:"select", editoptions: { value: allowedDistrictsForUserAdd,dataEvents:districtDataEvents}});
	    					        			$('#mergeUserToKeepGrid1').setColProp('school', {edittype:"select", formatter:"select", editoptions: { value: allowedSchoolsForUserAdd,dataEvents:schoolDataEvents}});
	    					        			$('#mergeUserToKeepGrid1').trigger('reloadGrid');
	    					         		}
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
	}
	
	
	function callSaveUsertoKeepClickSave(e, screen){
		debugger;
		inValidTeacherRole = "";
		aart.clearMessages();
		$('#'+screen+'mUserARTSmessages').hide();
		$('#'+screen+'InvalidOrganizationRoleMessage').html('');
		$('#'+screen+'InvalidOrganizationRoleMessage').hide();
		var proceed = true;
		var inValidOrgRole ="";
		var users = [];
		var currentUser = {};
		var purgeUser = {};
		currentUser.educatorIdentifier = $('#'+screen+'educatorIdentifierUTK').val().trim();
		currentUser.firstName = $('#'+screen+'firstNameUTK').val();
		currentUser.lastName = $('#'+screen+'lastNameUTK').val();
		currentUser.email = $('#'+screen+'emailAddressUTK').val();
		currentUser.id = $('#'+screen+'userIdUTK').val();
		purgeUser.id = $('#'+screen+'userIdUTP').val();
		currentUser.action="MERGE";
		var teacherMap = [];
		var organizations = [];
		var validateUserRoles = [];
		var userGridData = [];
		var validateUserRolesArray=[];
		var sourceUserGridData = [];
		 document.getElementById("left_security").value="left_security";
		 document.getElementById("right_security").value="right_security";
		 document.getElementById("left_security").value="left_security";
		 document.getElementById("right_security").value="right_security";
		 ($('.myEmail:checked').val()=='rightemail')?currentUser.emailId=purgeUser.id:null;
		 ($('.myEducator:checked').val()=='righteducator')?currentUser.educatorId=purgeUser.id:null;
		userGridData  = $("#mergeUserToKeepGrid1").jqGrid('getGridParam','data');
		sourceUserGridData = $("#mergeUserToKeepGrid2").jqGrid('getGridParam','data');
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
			roles.push(row.role);

			var userRole = {orgId: Number(orgId), assessmentProgram: Number(row.assessmentProgram), role: Number(row.role)};
			if(userRole != null && jQuery.inArray( JSON.stringify(userRole), validateUserRoles ) >= 0){
				proceed = false;
				return false;
			}
			validateUserRoles.push(JSON.stringify(userRole));

			var teacherRoleId = groupsNameMap['Teacher'];
			var teacherPNPRoleId = groupsNameMap['Teacher: PNP Read Only'];
			var orgid_apid =Number(orgId)+"_"+Number(row.assessmentProgram);
			if((Number(row.role) ===  Number(teacherRoleId)) || (Number(row.role) ===  Number(teacherPNPRoleId))){
				var valExists = $.grep(teacherMap, function(element, index) {
					return element.key === orgid_apid;
				});
				
				if(valExists.length <=0){
					teacherMap.push({key:orgid_apid, value: [row.role]});
				} else {
					$.each(teacherMap, function(index, element){
						if(element.key === orgid_apid){
							if($.inArray(row.role, element.value) === -1){
								element.value.push(row.role);
							} else {
								// do nothing
							}
						} else {
							// do nothing
						}
					});
				}
				var teacherRoleCheck = false;
				$.each(teacherMap, function(index, element){
						if(element.value && element.value.length >= 2){
							teacherRoleCheck = true;
						} else {
							// do nothing
						}
				});
				if(teacherRoleCheck != null && teacherRoleCheck){
					proceed = false;
					inValidTeacherRole = inValidTeacherRole  + " Both Teacher and Teacher: PNP Read Only Role is not allowed. <br>" ;
					return false;
				}
			}
			organizationDetails.rolesIds = roles;
			
			organizations.push(organizationDetails);
		});
		
		// merge source user data to destination user's data.
		$(sourceUserGridData).each(function( i, row ) {
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
				purgeUser.defaultOrgId =  orgId;
				purgeUser.defaultAssessmentProgramId = row.assessmentProgram;
			}
			organizationDetails.assessmentProgramId = row.assessmentProgram;
			roles.push(row.role);

			var userRole = {orgId: Number(orgId), assessmentProgram: Number(row.assessmentProgram), role: Number(row.role)};
			orgid_apid =Number(orgId)+"_"+Number(row.assessmentProgram);
			if(userRole != null && jQuery.inArray( JSON.stringify(userRole), validateUserRoles ) >= 0){
				//proceed = false;
			}
				validateUserRoles.push(JSON.stringify(userRole));

				var teacherRoleId = groupsNameMap['Teacher'];
				var teacherPNPRoleId = groupsNameMap['Teacher: PNP Read Only'];
				if((Number(row.role) ===  Number(teacherRoleId)) || (Number(row.role) ===  Number(teacherPNPRoleId))){
					var teacherRoleCheck = {orgId: Number(orgId), assessmentProgram: Number(row.assessmentProgram), roleId: Number(row.role)};
					if($.inArray(JSON.stringify(teacherRoleCheck),validateUserRolesArray)== -1){
						validateUserRolesArray.push(JSON.stringify(teacherRoleCheck));
					}
					var valExists = $.grep(teacherMap, function(element, index) {
						return element.key === orgid_apid;
					});
					
					if(valExists.length <=0){
						teacherMap.push({key:orgid_apid, value: [row.role]});
					} else {
						$.each(teacherMap, function(index, element){
							if(element.key === orgid_apid){
								if($.inArray(row.role, element.value) === -1){
									element.value.push(row.role);
								} else {
									// do nothing
								}
							} else {
								// do nothing
							}
						});
					}
					var teacherRoleCheck = false;
					$.each(teacherMap, function(index, element){
							if(element.value && element.value.length >= 2){
								teacherRoleCheck = true;
							} else {
								// do nothing
							}
					});
					if(teacherRoleCheck != null && teacherRoleCheck){
						proceed = false;
						inValidTeacherRole = inValidTeacherRole  + " Both Teacher and Teacher: PNP Read Only Role is not allowed. <br>" ;
						return false;
					}
				}
				organizationDetails.rolesIds = roles;
				organizations.push(organizationDetails);
		});
		
		currentUser.organizations = organizations;
		users.push(currentUser);
		if(!validateMergeUser(currentUser, purgeUser, screen)){
			$('#'+screen+'mUserARTSmessages').show();
			$('#'+screen+'mUserrequiredMessage').show();
			return false;
		}

		if(proceed){
			currentUser.organizations = organizations;
			users.push(currentUser);
		
			if(validateMergeUser(currentUser, purgeUser, screen)){
			
				var usersParameter = users.length > 0 ? $.toJSON(users) : '';
				var overwriteflag = false;
				if(screen == 'm'){
					overwriteflag = true;					
				}
				$.ajax({
			         url: 'saveUser.htm',
			         data: '&users=' + encodeURIComponent(usersParameter)+'&overwrite='+overwriteflag+'&userId='+currentUser.id,
			         dataType: "json",
			         type: 'POST',
			         error: function(jqXHR, textStatus, errorThrown){
			        	 
			         },
			         success: function(data){
			        	 $('#'+screen+'mduplicateUserError').hide();
			        	 $('#'+screen+'mInvalidOrgRoleMessage').hide();
			        	 $('#'+screen+'midOrgUserError').hide();
			        	 $('#'+screen+'InvalidOrganizationRoleMessage').hide();
			         	if(data == null){
			         		$('#'+screen+'mUserARTSmessages').show();
		        			$('#'+screen+'mUserfailMessage').show();
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
				         					$('#'+screen+'mduplicateUserError').html(msg.error_duplicate_user_emailaddr).show();	
					         			}else{
					         				$('#'+screen+'mduplicateUserError').hide();
					         			}
				         				if(user.errorCode.indexOf('USER_ORG_EXISTS_') >=0){
				         					
				         					var message=msg.error_user_create_eduorgexists;
				         					message = message.replace('_ORG_', user.errorCode.substring(user.errorCode.indexOf('USER_ORG_EXISTS_') + 16));
				         					$('#'+screen+'orgidOrgUserError').html(message).show();
						        			$('#'+screen+'mUserARTSmessages').show();
					         			} 
			         				}else{
				         				$('#'+screen+'mUserARTSmessages').show();
					        			$('#'+screen+'mUserfailMessage').show();
				         			}
			         			} else {
			         				if(screen == 'm'){
			         					
			         					showMoveRosterConfirmationBox();
			         					//$('#moveRosterConfirmationBox').show();
			         				//	$('#editUsers').dialog('close');

         				         //		$("#viewUserGridTableId").trigger("reloadGrid");	
			         				} else{
			         					$("#usersSelect").val("");
				         				$("#usersSelect").trigger('change.select2');
			         				}			         				
			         				$('#'+screen+'mUserARTSmessages').show();
				        			$('#'+screen+'mUsersuccessMessage').show();
				        			//setTimeout("aart.clearMessages()", 10000);
				        			setTimeout(function(){ $('#UserARTSmessages').hide(); },10000);
			         			}
			         		} else{
			         			$('#'+screen+'mUserARTSmessages').show();
			        			$('#'+screen+'mUserfailMessage').show();
			         		}
			         	}
			         	
			         }
			     });
			} else{
				$('#'+screen+'mUserARTSmessages').show();
				$('#'+screen+'mUserrequiredMessage').show();
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

	
 function showMoveRosterConfirmationBox(){
	 var puserId = $('#muserIdUTP').val();
	 $.ajax({	 
		  url: 'checkRosterforUser.htm?',
         data: '&puserId='+puserId,
         dataType: "json",
         type: 'POST',
		success: function(data) {	
				if(data !=null && data){
					$('#moveRosterConfirmationBox').show();
				}
				else
					jQuery("#purgeUserTd").show();
			}		        			
		});
	 
 }
 function savePurgingUserRoster(screen){
	 
		aart.clearMessages();
		$('#'+screen+'mUserARTSmessages').hide();
		var proceed = true;
		var inValidOrgRole ="";
		var users = [];
		var currentUser = {};
		currentUser.educatorIdentifier = $('#'+screen+'educatorIdentifierUTP').val();
		currentUser.firstName = $('#'+screen+'firstNameUTP').val();
		currentUser.lastName = $('#'+screen+'lastNameUTP').val();
		currentUser.email = $('#'+screen+'emailAddressUTP').val();
		currentUser.id = $('#'+screen+'userIdUTP').val();
		var rosterMoveuserId = $('#'+screen+'userIdUTK').val();
		users.push(currentUser);

		var usersParameter = users.length > 0 ? $.toJSON(users) : '';
		var overwriteflag = false;
		if(screen == 'm'){
			overwriteflag = true;					
		}
		
		$.ajax({
	         url: 'moveRoster.htm',
	         data: '&users=' + encodeURIComponent(usersParameter)+'&overwrite='+overwriteflag+'&userId='+currentUser.id+'&rosterMoveuserId='+rosterMoveuserId,
	         dataType: "json",
	         type: 'POST',
	         error: function(jqXHR, textStatus, errorThrown){
	        	 
	         },
	         success: function(data){
	        	 $('#'+screen+'mduplicateUserError').hide();
	        	 $('#'+screen+'mInvalidOrgRoleMessage').hide();
	        	 $('#'+screen+'midOrgUserError').hide();
	         	if(data == null){
	         	$('#'+screen+'mUserARTSmessages').show();
       			$('#'+screen+'mUserfailMessage').show();
	         	} else {
	         		var user = data.user;
	         	
	         		if(user!=null && user != undefined){
	         			
	         			if(user ==  0)
	         				{
	         				$('#'+screen+'mUserARTSmessages').show();
		        			$('#'+screen+'mUsersuccessMessage').show();
		        			jQuery("#moveRosterConfirmationBox").hide();
	                		jQuery("#purgeUserTd").show();
		        		
	         			}else{
	         				if(screen == 'm'){
	       //  					$('#editUsers').dialog('close');
			//	         		$("#viewUserGridTableId").trigger("reloadGrid");	
	         				} else{
	         					$("#usersSelect").val("");
		         				$("#usersSelect").trigger('change.select2');
	         				}			         				
	         				$('#'+screen+'mUserARTSmessages').show();
		        			$('#'+screen+'mUserRosterMovesuccessMessage').show();
		        			jQuery("#moveRosterConfirmationBox").hide();
	                		jQuery("#purgeUserTd").show();
		        		}
	         		}
	         		else if(data.roleErrorMessage.trim().length > 0)
     				{
      		
	         			$('#'+screen+'mUserARTSmessages').show();
	         			$('#'+screen+'mInvalidRole').html(data.roleErrorMessage);
                		$('#'+screen+'mInvalidRole').show();
                		jQuery("#moveRosterConfirmationBox").hide();
                		jQuery("#purgeUserTd").show();
	         			
     				}else{
	         			
	         			$('#'+screen+'mUserARTSmessages').show();
	        			$('#'+screen+'mUserfailMessage').show();
	         		}
	         	}
	         	
	         }
	     });
		
 }
	
 function deletePurgingUser(screen){
		
	 purgeUserId = $('#'+screen+'userIdUTP').val();
	 $('#'+screen+'mUserARTSmessages').hide();
		$.ajax({
	         url: 'purgeUser.htm',
	         data: '&purgeUserId='+purgeUserId,
	         dataType: "json",
	         type: 'POST',
	         error: function(jqXHR, textStatus, errorThrown){
	         },
	         success: function(data){
	        	 selectedUserArr = [];
	        	 if(screen == 'm'){
	        		$('#editMergingUsersToKeep').dialog('close');
  					$('#editMergingUsers').dialog('close');
		         	$("#mergeUserGridTableId").trigger("reloadGrid");
		         	$('#mergeUserNextButton').attr('disabled','disabled').addClass('ui-state-disabled');
  				} else{
  					$("#usersSelect").val("");
      				$("#usersSelect").trigger('change.select2');
  				}
	        	$('#UserARTSmessages').show();
     			$('#'+screen+'UserMergesuccessMessage').show();
     			$('#mergeUserNextButton').attr('disabled','disabled').addClass('ui-state-disabled');
	         }
	     });
	 
	 
	}
		
	
 function validateMergeUser(currentUser, purgeUser, screen) {
		var validData = true;
		var message = msg.label_required_fields;
		//var educator = firstEducatorIdentifier;
		
		/* DE6479 - educator id is optional
		 * if(isEmpty(currentUser.educatorIdentifier)){
			validData = false;
			message = message + msg.validation_educator_identifier;
		}*/
		
		/*if(educator != null &&  !isEmpty(educator) && screen != '' && isEmpty(currentUser.educatorIdentifier.trim()) ){
			validData = false;
			message = message + msg.validation_educator_identifier;
		}*/
		
		if(isEmpty(currentUser.firstName)){
			validData = false;
			message = message + msg.validation_first_name;
		}
		
		if(isEmpty(currentUser.lastName)){
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
	
		if(isEmpty(currentUser.defaultOrgId)&&isEmpty(purgeUser.defaultOrgId)){
			validData = false;
			message = message + msg.validation_default_org;
		}
		/*if(isEmpty(currentUser.assessmentProgramsIds)){
			validData = false;
			message = message + msg.validation_atleast_one_assessmentprogram;
		}*/
		
		var organizations = currentUser.organizations;
		if(isEmptyArr(organizations)){
			validData = false;
			/*message = message + msg.validation_default_org;*/
		} else if(!isEmptyArr(organizations)){
			$(organizations).each(function( i, organizationDetails ) {
				var roles = organizationDetails.rolesIds;
				if(isEmptyArr(roles)){
					validData = false;
					message = message + msg.validation_atleast_one_role;
				}
				
				/*if(isEmpty(organizationDetails.defaultRoleId)){
					validData = false;
					message = message + msg.validation_default_role;
				}*/
				
				if(organizationDetails.organizationId == null || organizationDetails.organizationId == 0){
					validData = false;
					message = message + msg.validation_all_org + " in " + (i+1) + " row.Select correct organization type for the role.";
				}
				
				if(organizationDetails.assessmentProgramId == null || organizationDetails.assessmentProgramId == 0){
					validData = false;
					message = message +"<br/>- Role and assessmentprogram mismatch" + " in " + (i+1) + " row. Select correct assessmentprogram type for the role.";
				}
			});
		}
		
		if(!validData){
			$('#'+screen+'mUserrequiredMessage').html(message);
		}
		return validData;
	}

 
 /* 
  * Add organizations for user to keep
 */
 function callAddMergUserOrgClickEdit(e, screen,LorR){
		var target = e.target;
		var idd = target.id;
		
		var closestRow = $('[id^='+idd+']').closest('table');
		//var rownum = parseInt(idd.substring(idd.lastIndexOf('_')+1));
		
		//var existingRows = $('[id^='+screen+'orgRow]');
		var rownum = $('#'+screen+'orgTable'+LorR).data('rowindex');
		
		
		$('#'+screen+'orgTable'+LorR).data('rowindex', (rownum+1));
		//if(existingRows != undefined && existingRows != null){
			//rownum = existingRows.length;
		//}
		
		//closestRow.find('#'+screen+'existing_roles_table_1').jqGrid('GridUnload');
		
     var v = closestRow.clone();
     
     //Rename ids per the new row.
     v.attr('id', screen+'orgRowUTK_'+(rownum+1));
     v.data('rowindex', rownum+1);
     v.find('[id^='+screen+'btn_addOrg'+ LorR +']').attr("id", screen+'btn_addOrg'+LorR+'_'+(rownum+1));
     v.find('[id^='+screen+'btn_removeOrg'+LorR+']').attr("id", screen+'btn_removeOrg'+LorR+'_'+(rownum+1));
     v.find('[id^='+screen+'orgscontainer'+LorR+'_td]').attr("id", screen+'orgscontainer'+LorR+'_td_'+(rownum+1));
     v.find('[id^='+screen+'rolescontainer'+LorR+'_td]').attr("id", screen+'rolescontainer'+LorR+'_td_'+(rownum+1));
     //v.find('[id^='+screen+'selectedOrgId]').attr("id", screen+'selectedOrgId_'+(rownum+1));
     
     //Clear organization container
     v.find('#'+screen+'orgscontainer'+LorR+'_td_'+(rownum+1)).empty();        
     $('<div id="'+screen+'addUserFilter'+LorR+'_'+(rownum+1)+'"></table>').appendTo(v.find('#'+screen+'orgscontainer'+LorR+'_td_'+(rownum+1)));
     $('<br/><br/><input type="radio" id="'+screen+'default_org" name="'+screen+'defaultOrg"/> Default Organization <font size="5" color="red">*</font>').appendTo(v.find('#'+screen+'orgscontainer'+LorR+'_td_'+(rownum+1)));
     
     
     //Clear roles container
     v.find('#'+screen+'rolescontainer'+LorR+'_td_'+(rownum+1)).empty();
     $('<div class="form-fields"><label class="field-label">Roles:<span class="error">*</span></label><table id="'+screen+'existing_roles_table'+LorR+'_'+(rownum+1)+'"></table></div>').appendTo(v.find('#'+screen+'rolescontainer'+LorR+'_td_'+(rownum+1)));
     

     //Append the newly created row.
     v.appendTo('#'+screen+'orgTable'+LorR);

     $('[id^='+screen+'btn_addOrg'+LorR+']').off('click').on('click', function (e){
     	callAddMergUserOrgClickEdit(e, screen,LorR);
		});
     $('[id^='+screen+'btn_removeOrg'+LorR+']').off('click').on('click', function (e){
			callRemoveOrgClick(e, screen);
		});
     
     if((rownum+1) > 1){
     	$('[id^='+screen+'btn_removeOrg'+LorR+']').show();
     }
     //Build organization filter for the new row.
     //$('#'+screen+'addUserFilter_'+(rownum+1)+'').orgFilter();
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
 	
     $('#'+screen+'addUserFilter'+LorR+'_'+(rownum+1)+'').orgFilter({
 		'containerClass': '',
 		 getAllUserOrgs:true,
 		requiredLevels: [20]
 	});
 	
     $('#'+screen+'addUserFilter'+LorR+'_'+(rownum+1)+'').validate({ignore: ""});
     

     getRoles(screen+'existing_roles_table'+LorR+'_'+(rownum+1), null, null, null, screen);
	}

 
});
 
 
 
