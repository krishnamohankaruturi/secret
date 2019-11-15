/**
 * 06/18/2018 Kiran Reddy Taduru
 * Separated script from JSP as part of face-lift for moveSchools.jsp
 */

$(function() {
		$('#sourceStateForMoving, #sourceDistrictForMoving,#sourceSchoolForMoving, #destinationdistrictForMoving').select2({
			placeholder : 'Select',
			multiple : false
		});
		
		$('#sourceStateForMoving').val('').select2().trigger('change.select2');
		$('#sourceDistrictForMoving').val('').select2().trigger('change.select2');
		$('#sourceSchoolForMoving').val('').select2().trigger('change.select2');
		$('#moveDestinationDistrictContainer').hide();
		$('#moveSchoolSubmit').attr('disabled',true);
		$('#moveSchoolSubmit').addClass('btn_disabled');
		
		populateStatesForMove();
		
		$('#sourceStateForMoving').on("change",function(){
			populateDistrictsForMove($(this).val());
		});
		$('#sourceDistrictForMoving').on("change",function(){
			populateSchoolsForMove($(this).val());
		});
		
		$('#sourceDistrictForMoving').val('').select2().trigger('change.select2');
		$('#sourceSchoolForMoving').val('').select2().trigger('change.select2');
		$('#destinationdistrictForMoving').val('').select2().trigger('change.select2');
		filteringOrganization($('#sourceStateForMoving'));
		filteringOrganization($('#sourceDistrictForMoving'));
		filteringOrganization($('#sourceSchoolForMoving'));
		filteringOrganization($('#destinationdistrictForMoving'));
		$('#moveDestinationDistrictContainer').hide();
		$('#moveSchoolSubmit').attr('disabled',true);
		$('#moveSchoolSubmit').addClass('btn_disabled');
		
		//Move school button click event
		$('#moveSchoolSubmit').on("click",function(ev) {
			
			var sourceSchoolName = $('#sourceSchoolForMoving option:selected').text();
			var destinationDistrictName = $('#destinationdistrictForMoving option:selected').text();
			$('#moveSchoolSubmitDilg').html('Are you sure to move school <b>'+ sourceSchoolName + '</b> to district <b>'+ destinationDistrictName+'</b>');
			
			$('#moveSchoolSubmitDilg').dialog({
				resizable : false,
				modal : true,
				width : 500,
				height : 200,
				buttons : {
					"OK" : function(ev) {
						$.ajax({
	                        url : 'moveSchool.htm',
	                        data : {
	                        	sourceSchool : $('#sourceSchoolForMoving').val(),
	                        	destinationDistrict : $('#destinationdistrictForMoving').val(),
	                        	sourceSchoolName : sourceSchoolName,
	                        	destinationDistrictName : destinationDistrictName,
	                        },
	                        dataType : 'json',
	                        type : "POST"
	                    }).done(function(response) {
                        	$('#moveSchoolSubmitDilg').dialog("close");
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
		
		$('#sourceStateForMoving').on("change",function(event) {
			$('#sourceSchoolForMoving').val('').select2().trigger('change.select2');
			$('#moveDestinationDistrictContainer').hide();
			$('#moveSchoolSummary').html('');
			$('#moveSchoolSubmit').attr('disabled',true);
			$('#moveSchoolSubmit').addClass('btn_disabled');
		});
		
		$('#sourceDistrictForMoving').on("change",function(event) {
			var type = $('#sourceDistrictForMoving').val();
			$('#destinationdistrictForMoving').val('').select2().trigger('change.select2');
			$('#moveDestinationDistrictContainer').hide();
			if(type===0||type===undefined||type===''){
			$('#sourceSchoolForMoving').val('').select2().trigger('change.select2');
			$('#moveDestinationDistrictContainer').hide();
			$('#org_mgmt_success').html('');
			$('#moveSchoolSummary').html('');
			$('#org_mgmt_error').html('');
			$('#moveSchoolSubmit').attr('disabled',true);
			$('#moveSchoolSubmit').addClass('btn_disabled');
			
		}
		else {
			$('#sourceSchoolForMoving').val('').select2().trigger('change.select2');
			$('#moveSchoolSubmit').attr('disabled',true);
			$('#moveSchoolSubmit').addClass('btn_disabled');
			$('#org_mgmt_success').html('');
			$('#moveSchoolSummary').html('');
			$('#org_mgmt_error').html('');
		}	
		});
		
		$('#sourceSchoolForMoving').on("change",function(event) {
			var type = $('#sourceSchoolForMoving').val();
			$('#destinationdistrictForMoving').val('').select2().trigger('change.select2');
			if(type===0||type===undefined||type===''){
			$('#moveDestinationDistrictContainer').hide();
			$('#org_mgmt_success').html('');
			$('#moveSchoolSummary').html('');
			$('#org_mgmt_error').html('');
			$('#moveSchoolSubmit').attr('disabled',true);
			$('#moveSchoolSubmit').addClass('btn_disabled');
			
		}
		else {
			getSummaryForOrgManagement(7, $(this).val(), $(this).find('option:selected').text(), 'moveSchoolSummary');
			$('#moveDestinationDistrictContainer').show();
			$('#org_mgmt_success').html('');
			$('#moveSchoolSummary').html('');
			$('#org_mgmt_error').html('');
		}	
		});
		$('#destinationdistrictForMoving').on("change",function(event) {
			var type = $('#destinationdistrictForMoving').val();
			if(type===0||type===undefined||type===''){
			$('#org_mgmt_success').html('');
			$('#org_mgmt_error').html(''); 
			$('#moveSchoolSubmit').attr('disabled',true);
			$('#moveSchoolSubmit').addClass('btn_disabled');
		}
		else {
			$('#moveSchoolSubmit').attr('disabled',false);
			$('#moveSchoolSubmit').removeClass('btn_disabled');
			$('#org_mgmt_success').html('');
			$('#org_mgmt_error').html('');
		}	
		});
	});