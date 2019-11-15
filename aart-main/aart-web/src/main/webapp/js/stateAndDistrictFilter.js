
$(function() {
	$.ajax({
        url: 'getStatesForUser.htm',
        data: { },
        dataType: 'json',
        type: "GET",
        success: function(states) {
        	if (states != null) {
				$.each(states, function(i, state) {
					$('#stateId').append($('<option></option>').attr("value", state.id).text(state.organizationName));
				});
				
				if (states.length == 1) {
					$("#stateId option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#stateId").trigger('change');
				} else {
					var lastid = $('#state').attr('data-lastid');
					if(lastid != "") {
						$("#stateId").val(lastid);
						$("#stateId").trigger('change');
					}					
				}
        	} else {
        		$('#state').hide();
        		$('#stateId').append($('<option></option>').attr("value", -1).text("No state."));
        		$('#stateId').val(-1);
        		$.ajax({
        	        url: 'getDistrictsForUser.htm',
        	        data: { },
        	        dataType: 'json',
        	        type: "GET",
        	        success: function(districts) {
        	        	if (districts != null) {
        					$.each(districts, function(i, district) {
        						$('#districtId').append($('<option></option>').attr("value", district.id).text(district.displayIdentifier));
        					});
        					if (districts.length == 1) {
        						$("#districtId option").removeAttr('selected').next('option').attr('selected', 'selected');
        						$("#districtId").trigger('change');
        					} else { 
        						var lastid = $('#district').attr('data-lastid');
        						if(lastid != "") {
        							$("#districtId").val(lastid);
        							$("#districtId").trigger('change');
        						}
        					}
        					
        	        	} else {
        	        		$('#district').hide();
        	        		$('#districtId').append($('<option></option>').attr("value", -1).text("No district."));
        	        		$('#districtId').val(-1);
        	        		$.ajax({
        	        	        url: 'getSchoolsForUser.htm',
        	        	        data: { },
        	        	        dataType: 'json',
        	        	        type: "GET",
        	        	        success: function(schools) {
        	        	        	if (schools != null) {
        	        					$.each(schools, function(i, school) {
        	        						$('#schoolId').append($('<option></option>').attr("value", school.id).text(school.displayIdentifier));
        	        					});
        	        					
        	        					if (schools.length == 1) {
        	        						$("#schoolId option").removeAttr('selected').next('option').attr('selected', 'selected');
        	        						$("#schoolId").trigger('change');
        	        					} else {
        	        						var lastid = $('#school').attr('data-lastid');
            	        					if(lastid != "") {
            	        						$("#schoolId").val(lastid);
            	        						$("#schoolId").trigger('change');
            	        					}
        	        					} 
        	        					//$("#schoolId").val();
        	        	        	}
        	        	        }
        	        		});
        	        	}
        	        }
        		});
        	}
        }
	});
});


$('#stateId').change(function() {
	var stateId = $('#stateId').val();
	$('#districtId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#schoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	if (stateId != 0) {
		$.ajax({
	        url: 'getDistrictsInState.htm',
	        data: {
	        		stateId: stateId
	        	},
	        dataType: 'json',
	        type: "GET",
	        success: function(districts) {
				$.each(districts, function(i, district) {
					$('#districtId').append($('<option></option>').attr("value", district.id).text(district.displayIdentifier));
				});
				
				if (districts.length == 1) {
					$("#districtId option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#districtId").trigger('change');
				} else {
					var lastid = $('#district').attr('data-lastid');
					if(lastid != "") {
						$("#districtId").val(lastid);
						$("#districtId").trigger('change');
					}
				}
	        }
		});
		
		$('#district').show();
	}
});


$('#districtId').change(function() {
	var districtId = $('#districtId').val();
	$('#schoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	if (districtId != 0) {
		$.ajax({
	        url: 'getSchoolsInDistrict.htm',
	        data: {
	        		districtId: districtId               
	        	},
	        dataType: 'json',
	        type: "GET",
	        success: function(schools) {
				$.each(schools, function(i, school) {
					$('#schoolId').append($('<option></option>').attr("value", school.id).text(school.displayIdentifier));
				});
				if (schools.length == 1) {
					$("#schoolId option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#schoolId").trigger('change');
				} else {
					var lastid = $('#school').attr('data-lastid');
					if(lastid != "") {
						$("#schoolId").val(lastid);
						$("#schoolId").trigger('change');
					}
				}
	        }
		});
		
		$('#school').show();
	}
});
