$(function() {
	permissionsAndRolesExtract_Initmethod();
});

function permissionsAndRolesExtract_Initmethod() {

	$('#PermissionStateDropDown, #PermissionAPDropDown').select2({
		placeholder : 'Select',
		multiple : true
	});

	filteringOrganization($('#PermissionStateDropDown'));
	filteringOrganization($('#PermissionAPDropDown'));
	$('#PermissionStateDropDown').html("");
	$
			.ajax({
				url : 'getPermittedStateIds.htm',
				type : "GET"
			}).done(function(data) {
				$('#PermissionStateDropDown').val('');
				if (data !== undefined && data !== null && data.length > 0) {
					$('#PermissionStateDropDown').html("");
					$.each(data, function(i, data) {
						var opt = $('<option></option>').val(data.id).html(
								data.organizationName);
						$('#PermissionStateDropDown').append(opt);
					});
					$('#PermissionStateDropDown').trigger('change.select2');
					$('#PermissionStateDropDown').val('').trigger(
							'change.select2');
				}
			});

	$('#PermissionStateDropDown').on("change",
			function(event) {
				if ($('#PermissionStateDropDown').select2("val") != null) {
					var stateIds = $('#PermissionStateDropDown').select2("val")
							.map(function(selectedState) {
								return selectedState;
							});
				}
				var getData = new Object();
				$('#PermissionAPDropDown').html("");
				getData['stateIds[]'] = stateIds;
				if (stateIds != '' && stateIds != null && stateIds.length > 0) {
					$.ajax({
						url : 'getPermittedAPsBySelectedStateIds.htm',
						data : getData,
						type : "GET",
						success : function(data) {
							if (data !== undefined && data !== null
									&& data.length > 0) {
								$('#PermissionAPDropDown').html("");
								$.each(data, function(i, data) {
									$('#PermissionAPDropDown').append(
											$('<option></option>').val(data.id)
													.html(data.programName));
								});
							}
							$('#PermissionAPDropDown')
									.trigger('change.select2');
							$('#PermissionAPDropDown').val('').trigger(
									'change.select2');
						}
					});
				} else {
					$('#PermissionAPDropDown').html('');
					$('#PermissionAPDropDown').trigger('change.select2');
				}				
				$('#PermissionAPDropDown_selectAll').attr("checked", false);
			});

	$("#permissionsDownloadLInk")
			.on("click",
					function(event) {
						var stateIds = $('#PermissionStateDropDown').select2("val");
						var assessmentprogramIds = $('#PermissionAPDropDown').select2("val");
						if (stateIds == null || stateIds == undefined || stateIds.length < 1) {
							alert('Please select at least one State');
							event.preventDefault();
						}else if (assessmentprogramIds==null || assessmentprogramIds == undefined || assessmentprogramIds.length < 1) {
							alert('Please select at least one Assessment Program');
							event.preventDefault();
						}
						else {
							var downloadUrl = "generatePermissionsAndRolesExtract.htm?stateIds="
									+ encodeURIComponent(stateIds)
									+ "&assessmentprogramIds="
									+ encodeURIComponent(assessmentprogramIds);
							$("#permissionsDownloadLInk").attr("href", downloadUrl);
						}  
					});
	
	 $(document).on("click", ".Role_Permission_State_AP_selectAll", function(ev){	
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
}