/**
 * 06/18/2018 Kiran Reddy Taduru
 * Separated script from JSP as part of face-lift for mergeSchools.jsp
 */

$(function() {

		$('#sourceStateForMerging, #sourceschoolForMerging, #destinationschoolForMerging, #sourceDistrictForMerging, #destinationDistrictForMerging').select2({
			placeholder : 'Select',
			multiple : false
		});
		
		$('#sourceStateForMerging').val('').select2().trigger('change.select2');
		$('#sourceDistrictForMerging').val('').select2().trigger('change.select2');
		$('#sourceschoolForMerging').val('').select2().trigger('change.select2');
		$('#mergeSchoolSubmit').attr('disabled',true);
		$('#mergeSchoolSubmit').addClass('btn_disabled');
		$('#mergeDestinationDistrictContainer').hide();
		$('#mergeDestinationSchoolContainer').hide();
		var type = $('#sourceDistrictForMerging').val();
		if(type===0||type===undefined||type===''){
			$('#sourceschoolForMerging option').remove();
		}
		populateStatesForMerge();
		$('#sourceStateForMerging').on("change",function(){
			 populateDistrictsForMerge($(this).val());
		});
		$('#sourceDistrictForMerging').on("change",function(){
			 populateSchoolsForMerge($(this).val(),'source');
		});
		$('#destinationDistrictForMerging').on("change",function(){
			 populateSchoolsForMerge($(this).val(),'destination');
		});
		
		
		$('#sourceschoolForMerging').val('').select2().trigger('change.select2');
		$('#destinationschoolForMerging').val('').select2().trigger('change.select2');
		filteringOrganization($('#sourceStateForMerging'));
		filteringOrganization($('#sourceDistrictForMerging'));
		filteringOrganization($('#destinationDistrictForMerging'));
		filteringOrganization($('#sourceschoolForMerging'));
		filteringOrganization($('#destinationschoolForMerging'));
		$('#mergeDestinationDistrictContainer').hide();
		$('#mergeDestinationSchoolContainer').hide();
		$('#mergeSchoolSubmit').attr('disabled',true);
		$('#mergeSchoolSubmit').addClass('btn_disabled');
		
		//Merge school button click event
		$('#mergeSchoolSubmit').on("click",function(ev) {
			
			var sourceSchoolName = $('#sourceschoolForMerging option:selected').text();
			var destinationSchoolName = $('#destinationschoolForMerging option:selected').text();
			$('#mergeSchoolSubmitdilg').html('Are you sure to merge school <b>'+ sourceSchoolName + '</b> with school <b>'+ destinationSchoolName+'</b>');
			var sent = false;
			$('#mergeSchoolSubmitdilg').dialog({
				resizable : false,
				modal : true,
				width : 500,
				height : 200,
				buttons : {
					"OK" : function(ev) {
						 if (sent) return;
		                    sent = true;
						$.ajax({
	                        url : 'mergeSchool.htm',
	                        data : {
	                            sourceSchool : $('#sourceschoolForMerging').val(),
	                            destinationSchool : $('#destinationschoolForMerging').val(),
	                            sourceSchoolName : sourceSchoolName,
	                            destinationSchoolName : destinationSchoolName
	                        },
	                        dataType : 'json',
	                        type : "POST"
	                    }).done(function(response) {
                        	$('#mergeSchoolSubmitdilg').dialog("close");
                        	if(response.status === 'success') {
        						$('#org_mgmt_error').hide();
        						$('#org_mgmt_success').html(response.successMessage);
        						$('#org_mgmt_success').show();
        					} else if(response.errorFound === 'true'){
        						$('#org_mgmt_error').html(response.errorMessage);
        						$('#org_mgmt_error').show();
        					}
                        });
						
						return true;
					},
					Cancel : function(ev) {
						$(this).dialog("close");
						return false;
					}
				}
			}).dialog('open');
		});
		$('#sourceStateForMerging').on("change",function(event) {
			var type = $('#sourceStateForMerging').val();
			if(type===0||type===undefined||type===''){
				$('#sourceDistrictForMerging option').remove();
				$('#sourceDistrictForMerging').val('').select2().trigger('change.select2');
				$('#sourceschoolForMerging option').remove();
				$('#sourceschoolForMerging').val('').select2().trigger('change.select2');
				$('#mergeSchoolSubmit').attr('disabled',true);
				$('#mergeSchoolSubmit').addClass('btn_disabled');
			}
			$('#sourceschoolForMerging').val('').select2().trigger('change.select2');
			$('#destinationschoolForMerging').val('').select2().trigger('change.select2');
			$('#mergeDestinationDistrictContainer').hide();
			$('#mergeDestinationSchoolContainer').hide();
			$('#mergeSchoolsSummary').html('');
			$('#mergeSchoolSubmit').attr('disabled',true);
			$('#mergeSchoolSubmit').addClass('btn_disabled');
		});
		$('#sourceDistrictForMerging').on("change",function(event) {
			$('#destinationDistrictForMerging').val('').select2().trigger('change.select2');
			$('#destinationschoolForMerging').val('').select2().trigger('change.select2');
			$('#mergeDestinationDistrictContainer').hide();
			$('#mergeDestinationSchoolContainer').hide();
			var type = $('#sourceDistrictForMerging').val();
			if(type===0||type===undefined||type===''){
			$('#sourceschoolForMerging option').remove();
			$('#sourceschoolForMerging').val('').select2().trigger('change.select2');
			$('#mergeDestinationDistrictContainer').hide();
			$('#mergeDestinationSchoolContainer').hide();
			$('#org_mgmt_success').html('');
			$('#mergeSchoolsSummary').html('');
			$('#org_mgmt_error').html('');
			$('#mergeSchoolSubmit').attr('disabled',true);
			$('#mergeSchoolSubmit').addClass('btn_disabled');
			
		}	
			else {
				$('#destinationDistrictForMerging').val('').select2().trigger('change.select2');
				$('#mergeSchoolSubmit').attr('disabled',true);
				$('#mergeSchoolSubmit').addClass('btn_disabled');
				$('#mergeSchoolsSummary').html('');
				$('#org_mgmt_success').html('');
				$('#org_mgmt_error').html('');
			}	
		});
		$('#sourceschoolForMerging').on("change",function(event) {
			var type = $('#sourceschoolForMerging').val();
			$('#destinationDistrictForMerging').val('').select2().trigger('change.select2');
			$('#destinationschoolForMerging').val('').select2().trigger('change.select2');
			if(type===0||type===undefined||type===''){
			$('#mergeDestinationDistrictContainer').hide();
			$('#mergeDestinationSchoolContainer').hide();
			$('#org_mgmt_success').html('');
			$('#mergeSchoolsSummary').html('');
			$('#org_mgmt_error').html('');
			$('#mergeSchoolSubmit').attr('disabled',true);
			$('#mergeSchoolSubmit').addClass('btn_disabled');
			
		}	
			else {
				getSummaryForOrgManagement(7, $(this).val(), $(this).find('option:selected').text(), 'mergeSchoolsSummary');
				$('#mergeDestinationDistrictContainer').show();
				var type = $('#destinationDistrictForMerging').val();
				if(type===0||type===undefined||type===''){
					$('#destinationschoolForMerging option').remove();
					$('#destinationschoolForMerging').val('').select2().trigger('change.select2');
					$('#mergeSchoolSubmit').attr('disabled',true);
					$('#mergeSchoolSubmit').addClass('btn_disabled');
				}
				var type = $('#destinationschoolForMerging').val();
				if(type===0||type===undefined||type===''){
					$('#mergeSchoolSubmit').attr('disabled',true);
					$('#mergeSchoolSubmit').addClass('btn_disabled');
				}
				$('#mergeDestinationSchoolContainer').show();
				$('#mergeSchoolsSummary').html('');
				$('#org_mgmt_success').html('');
				$('#org_mgmt_error').html('');
			}	
		});
		
		$('#destinationDistrictForMerging').on("change",function(event) {
			var type = $('#destinationDistrictForMerging').val();
			if(type===0||type===undefined||type===''){
				$('#destinationschoolForMerging option').remove();
				$('#destinationschoolForMerging').val('').select2().trigger('change.select2');
				$('#org_mgmt_success').html('');
				$('#org_mgmt_error').html(''); 
				$('#mergeSchoolSubmit').attr('disabled',true);
				$('#mergeSchoolSubmit').addClass('btn_disabled');
			}
			else {
				$('#mergeSchoolSubmit').attr('disabled',true);
				$('#mergeSchoolSubmit').addClass('btn_disabled');
				$('#org_mgmt_success').html('');
				$('#org_mgmt_error').html('');
			}	
		});
		
		$('#destinationschoolForMerging').on("change",function(event) {
			var type = $('#destinationschoolForMerging').val();
			if(type===0||type===undefined||type===''){
			$('#org_mgmt_success').html('');
			$('#org_mgmt_error').html(''); 
			$('#mergeSchoolSubmit').attr('disabled',true);
			$('#mergeSchoolSubmit').addClass('btn_disabled');
		}
		else {
			$('#mergeSchoolSubmit').attr('disabled',false);
			$('#mergeSchoolSubmit').removeClass('btn_disabled');
			$('#org_mgmt_success').html('');
			$('#org_mgmt_error').html('');
		}	
		});
	});