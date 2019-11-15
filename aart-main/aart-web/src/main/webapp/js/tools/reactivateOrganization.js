/**
 * 
 */


$(function() {

		$('#stateReactivateOrg, #districtsReactivateOrg, #schoolsReactivateOrg, #districtReactivateOrg,#typesOfOrganization').select2({
			placeholder : 'Select',
			multiple : false
		});
		
		$('#districtReactivateOrg').val('').select2().trigger('change.select2');
		$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
		$('#reactivateDistrictContainer').hide();
		$('#reactivateSchoolContainer').hide();
		$('#reactivateDistrictsContainer').hide();
		
		populateStates('reactivate');
		
		$('#stateReactivateOrg').on("change",function(){
			populateDistrictsForReactivation($(this).val());
		});
		$('#stateReactivateOrg').on("change",function(){
			populateDistricts($(this).val(),'reactivate');
		});
		getOrganizationTypes('reactivate');
		$('#districtsReactivateOrg').on("change",function(){
			populateSchoolsForReactivation($(this).val());
		});
		
		$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
		$('#schoolsReactivateOrg').val('').select2().trigger('change.select2');
		$('#districtReactivateOrg').val('').select2().trigger('change.select2');
		$('#typesOfOrganization').val('').select2().trigger('change.select2');
		filteringOrganization($('#stateReactivateOrg'));
		filteringOrganization($('#districtsReactivateOrg'));
		filteringOrganization($('#schoolsReactivateOrg'));
		filteringOrganization($('#districtReactivateOrg'));
		filteringOrganization($('#typesOfOrganization'));
		
		$('#reactivateDistrictsContainer').hide();
		$('#reactivateDistrictContainer').hide();
		$('#reactivateSchoolContainer').hide();
		
		$('#reactivateOrgSubmit').attr('disabled',true);
		$('#reactivateOrgSubmit').addClass('btn_disabled');
		
		//Reactivate button click event
		$('#reactivateOrgSubmit').on("click",function(ev) {
			
			var orgFilterSelected = '';
			if($('#typesOfOrganization').val() ==='5'){
				orgFilterSelected = 'districtReactivateOrg';
			} else if($('#typesOfOrganization').val() ==='7'){
				orgFilterSelected = 'schoolsReactivateOrg';
			}
			var organizationName = $('#'+orgFilterSelected + '  option:selected').text();
			$('#reactivateOrgSubmitDilg').html('Are you sure to reactivate organization <b>'+ organizationName +'</b>');
			
			$('#reactivateOrgSubmitDilg').dialog({
				resizable : false,
				modal : true,
				width : 500,
				height : 200,
				buttons : {
					"OK" : function(ev) {
						var currentOrgType=$('#typesOfOrganization').val();
						var  deactivateOrgURL='';
						var data = {};
						
						 if(currentOrgType ==='7'){
						 	reactivateOrgURL='enableSchool.htm';
						 	data.schoolId=$('#schoolsReactivateOrg').val();
						 	
						 }else if(currentOrgType ==='5'){
						 reactivateOrgURL='enableDistrict.htm';
						 data.districtId=$('#districtReactivateOrg').val();
						 }
						 data.organizationName = organizationName;
							$.ajax({
		                        url : reactivateOrgURL,
		                        data : data,
		                        dataType : 'json',
		                        type : "POST"
		                    }).done(function(response) {
		                        	$('#reactivateOrgSubmitDilg').dialog("close");
		                        	if(response.status === 'success') {
		        						$('#org_mgmt_error').hide();
		        						$('#org_mgmt_success').html(response.successMessage);
		        						$('#org_mgmt_success').show();
		        					} else if(response.errorFound === 'true'){
		        						$('#org_mgmt_error').html(response.errorMessage);
		        						$('#org_mgmt_error').show();
		        					}
		                        	$('#stateReactivateOrg').trigger('change');
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
		
		$('#typesOfOrganization').on("change",function(event) {
			var type = $('#typesOfOrganization').val();
				$('#schoolsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
			if($(this).val() ==='5'){
				$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtReactivateOrg').val('').select2().trigger('change.select2');
				$('#reactivateSchoolContainer').hide();
				$('#reactivateDistrictContainer').show();
				$('#reactivateDistrictsContainer').hide();
				$('#org_mgmt_success').html('');
				$('#reactivateOrgSummary').html('');
				$('#org_mgmt_error').html('');
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
			} else if($(this).val() ==='7'){
				$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtReactivateOrg').val('').select2().trigger('change.select2');
				$('#org_mgmt_success').html('');
				$('#reactivateOrgSummary').html('');
				$('#org_mgmt_error').html('');
				$('#reactivateDistrictsContainer').show();
				$('#reactivateDistrictContainer').hide();
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
			} else if(type===0||type==='undefined'||type===''){
				$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtReactivateOrg').val('').select2().trigger('change.select2');
				$('#org_mgmt_error').html('');
				$('#reactivateSchoolContainer').hide('');
				$('#reactivateDistrictContainer').hide('');
				$('#reactivateDistrictsContainer').hide('');
				$('#reactivateOrgSummary').html('');
				$('#org_mgmt_success').html('');
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
			}
			else {
				$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtReactivateOrg').val('').select2().trigger('change.select2');
				$('#org_mgmt_success').html('');
				$('#reactivateOrgSummary').html('');
				$('#reactivateDistrictsContainer').hide();
				$('#reactivateDistrictContainer').hide();
				$('#reactivateSchoolContainer').hide();
				$('#org_mgmt_error').html('Deactivation at this organization level is not implemented.');
				$('#org_mgmt_error').show();
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
			}
		});
		$('#stateReactivateOrg').on("change",function(event) {
			$('#typesOfOrganization').val('').select2().trigger('change.select2');
			$('#reactivateDistrictContainer').hide();
			$('#reactivateDistrictsContainer').hide();
			$('#reactivateSchoolContainer').hide();
			$('#reactivateOrgSubmit').attr('disabled',true);
			$('#reactivateOrgSubmit').addClass('btn_disabled');
			$('#reactivateOrgSummary').html('');
		});
		$('#districtsReactivateOrg').on("change",function(event) {
			var type = $('#districtsReactivateOrg').val();
			if(type == null ||type == 'undefined' || type == ''){
				$('#schoolsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtReactivateOrg').val('').select2().trigger('change.select2');
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
				$('#reactivateOrgSummary').html('');
				$('#reactivateSchoolContainer').hide();
			}else{
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
				$('#reactivateOrgSummary').html('');
				$('#reactivateSchoolContainer').show();
				$('#reactivateDistrictContainer').hide();
			}
			});
		$('#districtReactivateOrg').on("change",function(event) {
			var type = $('#districtReactivateOrg').val();
			if(type == null ||type == 'undefined' || type == ''){
				$('#districtsReactivateOrg').val('').select2().trigger('change.select2');
				$('#districtReactivateOrg').val('').select2().trigger('change.select2');
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
				$('#reactivateOrgSummary').html('');
			}else {
				getSummaryForDeactivateOrgManagement($('#typesOfOrganization').val(), $(this).val(), $(this).find('option:selected').text(), 'reactivateOrgSummary');
				$('#reactivateSchoolContainer').hide();
				$('#reactivateDistrictContainer').show();
				$('#reactivateDistrictsContainer').hide();
				$('#reactivateOrgSubmit').attr('disabled',false);
				$('#reactivateOrgSubmit').removeClass('btn_disabled');
				$('#reactivateOrgSummary').html('');
			}
		});
		$('#schoolsReactivateOrg').on("change",function(event) {
			var type = $('#schoolsReactivateOrg').val();
			if(type == null ||type == 'undefined' || type == ''){
				$('#reactivateOrgSubmit').attr('disabled',true);
				$('#reactivateOrgSubmit').addClass('btn_disabled');
				$('#reactivateOrgSummary').html('');
			}else {
				getSummaryForDeactivateOrgManagement($('#typesOfOrganization').val(), $(this).val(), $(this).find('option:selected').text(), 'reactivateOrgSummary');
				$('#reactivateSchoolContainer').show();
				$('#reactivateDistrictContainer').hide();
				$('#reactivateOrgSubmit').attr('disabled',false);
				$('#reactivateOrgSubmit').removeClass('btn_disabled');
				$('#reactivateOrgSummary').html('');
			}
		});
		
	});