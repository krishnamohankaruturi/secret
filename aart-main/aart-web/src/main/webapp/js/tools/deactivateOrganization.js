/**
 * 
 */

$(function() {

		$('#stateDeactivateOrg, #districtsDeactivateOrg, #schoolsDeactivateOrg, #districtDeactivateOrg,#typeOfOrganization').select2({
			placeholder : 'Select',
			multiple : false
		});
		
		$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
		$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
		$('#deactivateDistrictContainer').hide();
		$('#deactivateSchoolContainer').hide();
		$('#deactivateDistrictsContainer').hide();
		populateStates('deactivate');
		$('#stateDeactivateOrg').on("change",function(){
			populateDistrictsForDeactivation($(this).val());
		});
		$('#stateDeactivateOrg').on("change",function(){
			populateDistricts($(this).val(),'deactivate');
		});
		getOrganizationTypes('deactivate');
		$('#districtsDeactivateOrg').on("change",function(){
			populateSchoolsForDeactivation($(this).val());
		});
		
		$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
		$('#schoolsDeactivateOrg').val('').select2().trigger('change.select2');
		$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
		$('#typeOfOrganization').val('').select2().trigger('change.select2');
		filteringOrganization($('#stateDeactivateOrg'));
		filteringOrganization($('#districtsDeactivateOrg'));
		filteringOrganization($('#schoolsDeactivateOrg'));
		filteringOrganization($('#districtDeactivateOrg'));
		filteringOrganization($('#typeOfOrganization'));
		
		$('#deactivateDistrictsContainer').hide();
		$('#deactivateDistrictContainer').hide();
		$('#deactivateSchoolContainer').hide();
		
		$('#deactivateOrgSubmit').attr('disabled',true);
		$('#deactivateOrgSubmit').addClass('btn_disabled');
		
		//Deactivate button click
		$('#deactivateOrgSubmit').on("click",function(ev) {			
			
			var orgFilterSelected = '';
			if($('#typeOfOrganization').val() ==='5'){
				orgFilterSelected = 'districtDeactivateOrg';
			} else if($('#typeOfOrganization').val() ==='7'){
				orgFilterSelected = 'schoolsDeactivateOrg';
			}
			var organizationName = $('#'+orgFilterSelected + '  option:selected').text();
			$('#deactivateOrgSubmitDilg').html('Are you sure to deactivate organization <b>'+ organizationName +'</b>');
			
			$('#deactivateOrgSubmitDilg').dialog({
				resizable : false,
				modal : true,
				width : 500,
				height : 200,
				buttons : {
					"OK" : function(ev) {
						
						var currentOrgType=$('#typeOfOrganization').val();
						var  deactivateOrgURL='';
						var data = {};
						
						 if(currentOrgType ==='7'){
						 	deactivateOrgURL='disableSchool.htm';
						 	data.schoolId=$('#schoolsDeactivateOrg').val();
						 	
						 }else if(currentOrgType ==='5'){
						 deactivateOrgURL='disableDistrict.htm';
						 data.districtId=$('#districtDeactivateOrg').val();
						 }
						 data.organizationName = organizationName;
							$.ajax({
		                        url : deactivateOrgURL,
		                        data : data,
		                        dataType : 'json',
		                        type : "POST"
		                    }).done(function(response) {
	                        	$('#deactivateOrgSubmitDilg').dialog("close");
	                        	if(response.status === 'success') {
	        						$('#org_mgmt_error').hide();
	        						$('#org_mgmt_success').html(response.successMessage);
	        						$('#org_mgmt_success').show();
	        					} else if(response.errorFound === 'true'){
	        						$('#org_mgmt_error').html(response.errorMessage);
	        						$('#org_mgmt_error').show();
	        					}
	                        	$('#stateDeactivateOrg').trigger('change');
	                        });
						return true;
					},
					Cancel : function(ev) {
						$(this).dialog("close");
						return false;
					}
				}
			});
		});
		
		$('#typeOfOrganization').on("change",function(event) {
			var type = $('#typeOfOrganization').val();
				$('#schoolsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
			if($(this).val() ==='5'){
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#deactivateSchoolContainer').hide();
				$('#deactivateDistrictContainer').show();
				$('#deactivateDistrictsContainer').hide();
				$('#org_mgmt_success').html('');
				$('#deactivateOrgSummary').html('');
				$('#org_mgmt_error').html('');
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
			} else if($(this).val() ==='7'){
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#org_mgmt_success').html('');
				$('#deactivateOrgSummary').html('');
				$('#org_mgmt_error').html('');
				$('#deactivateDistrictsContainer').show();
				$('#deactivateDistrictContainer').hide();
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
			} else if(type===0||type==='undefined'||type===''){
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#org_mgmt_error').html('');
				$('#deactivateSchoolContainer').hide('');
				$('#deactivateDistrictContainer').hide('');
				$('#deactivateDistrictsContainer').hide('');
				$('#deactivateOrgSummary').html('');
				$('#org_mgmt_success').html('');
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
			}
			else {
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#org_mgmt_success').html('');
				$('#deactivateOrgSummary').html('');
				$('#deactivateDistrictsContainer').hide();
				$('#deactivateDistrictContainer').hide();
				$('#deactivateSchoolContainer').hide();
				$('#org_mgmt_error').html('Deactivation at this organization level is not implemented.');
				$('#org_mgmt_error').show();
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
			}
		});
		$('#stateDeactivateOrg').on("change",function(event) {
			$('#typeOfOrganization').val('').select2().trigger('change.select2');
			$('#deactivateDistrictContainer').hide();
			$('#deactivateDistrictsContainer').hide();
			$('#deactivateSchoolContainer').hide();
			$('#deactivateOrgSubmit').attr('disabled',true);
			$('#deactivateOrgSubmit').addClass('btn_disabled');
			$('#deactivateOrgSummary').html('');
		});
		$('#districtsDeactivateOrg').on("change",function(event) {
			var type = $('#districtsDeactivateOrg').val();
			if(type == null ||type == 'undefined' || type == ''){
				$('#schoolsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
				$('#deactivateOrgSummary').html('');
				$('#deactivateSchoolContainer').hide();
			}else{
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
				$('#deactivateOrgSummary').html('');
				$('#deactivateSchoolContainer').show();
				$('#deactivateDistrictContainer').hide();
			}
			});
		$('#districtDeactivateOrg').on("change",function(event) {
			var type = $('#districtDeactivateOrg').val();
			if(type == null ||type == 'undefined' || type == ''){
				$('#districtsDeactivateOrg').val('').select2().trigger('change.select2');
				$('#districtDeactivateOrg').val('').select2().trigger('change.select2');
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
				$('#deactivateOrgSummary').html('');
			}else {
				getSummaryForOrgManagement($('#typeOfOrganization').val(), $(this).val(), $(this).find('option:selected').text(), 'deactivateOrgSummary');
				$('#deactivateSchoolContainer').hide();
				$('#deactivateDistrictContainer').show();
				$('#deactivateDistrictsContainer').hide();
				$('#deactivateOrgSubmit').attr('disabled',false);
				$('#deactivateOrgSubmit').removeClass('btn_disabled');
				$('#deactivateOrgSummary').html('');
			}
		});
		$('#schoolsDeactivateOrg').on("change",function(event) {
			var type = $('#schoolsDeactivateOrg').val();
			if(type == null ||type == 'undefined' || type == ''){
				$('#deactivateOrgSubmit').attr('disabled',true);
				$('#deactivateOrgSubmit').addClass('btn_disabled');
				$('#deactivateOrgSummary').html('');
			}else {
				getSummaryForOrgManagement($('#typeOfOrganization').val(), $(this).val(), $(this).find('option:selected').text(), 'deactivateOrgSummary');
				$('#deactivateSchoolContainer').show();
				$('#deactivateDistrictContainer').hide();
				$('#deactivateOrgSubmit').attr('disabled',false);
				$('#deactivateOrgSubmit').removeClass('btn_disabled');
				$('#deactivateOrgSummary').html('');
			}
		});
		
	});