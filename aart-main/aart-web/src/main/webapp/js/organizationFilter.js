
var orgHierarchies = {};
var childOrg = 20;

$(function() {
		
	$.ajax({
        url: 'getStatesOrgsForUser.htm',
        data: { },
        dataType: 'json',
        type: "GET",
        success: function(states) {
        	if (states != null) {
				$.each(states, function(i, state) {
					$('#orgFilterStateId').append($('<option></option>').attr("value", state.id).text(state.organizationName));
					if(state.organizationHierarchies != null) {
						orgHierarchies[state.id] = state.organizationHierarchies.map(function (x) { 
						    return parseInt(x, 10); 
						});
					}
				});
				
				if (states.length == 1) {
					$("#orgFilterStateId option").removeAttr('selected').next('option').attr('selected', 'selected');
					findNextOrg(childOrg);
					$("#orgFilterStateId").trigger('change');
				} else {
					var lastid = $('#orgFilterStateDiv').attr('data-lastid');
					if(lastid != "") {
						$("#orgFilterStateId").val(lastid);
						findNextOrg(childOrg);
						$("#orgFilterStateId").trigger('change');
					}					
				}
        	} else {
        		$('#orgFilterStateDiv').hide();
        		$('#orgFilterStateId').append($('<option></option>').attr("value", -1).text("No state."));
        		$('#orgFilterStateId').val(-1);
        		$.ajax({
        	        url: 'getChildOrgsForUser.htm',
        	        data: { },
        	        dataType: 'json',
        	        type: "GET",
        	        success: function(orgs) {
        	        	
        	        	var filter;
        	        	var filterOption;        	        	        	        
        	        	childOrg = orgs[0].organizationType.typeLevel;        	        	
        	        	hideOrgsFilters(childOrg);
        	        	
        	        	if(childOrg == 30) {
        	        		filter = '#orgFilterRegionId';
        	        		filterOption = "#orgFilterRegionId option";
        	        	} if(childOrg == 40) {
        	        		filter = '#orgFilterAreaId';
        	        		filterOption = "#orgFilterAreaId option";
        	        	} else if(childOrg == 50) {
        	        		filter = '#orgFilterDistrictId';
        	        		filterOption = "#orgFilterDistrictId option";
        	        	} else if(childOrg == 60) {
        	        		filter = '#orgFilterBuildingId';
        	        		filterOption = "#orgFilterBuildingId option";
        	        	} else if(childOrg == 70) {
        	        		filter = '#orgFilterSchoolId';
        	        		filterOption = "#orgFilterSchoolId option";
        	        	}
        	        	
        	        	if (orgs != null) {
        					$.each(orgs, function(i, org) {
        						$(filter).append($('<option></option>').attr("value", org.id).text(org.organizationName));
        						if(org.organizationHierarchies != null) {
        							orgHierarchies[0] = org.organizationHierarchies.map(function (x) { 
        							    return parseInt(x, 10); 
        							});
        						}
        					});
        					
        					showRequiredOrgsFilters();
        					
        					if (orgs.length == 1) {
        						$(filterOption).removeAttr('selected').next('option').attr('selected', 'selected');
        						//if(childOrg != 80) {
        							$(filter).trigger('change');
        						//}
        					}
        	        	}
        	        }
        		});
        	}
        }
	});
});


$('#orgFilterStateId, #orgFilterRegionId, #orgFilterAreaId, #orgFilterDistrictId, #orgFilterBuildingId').change(function(event) {	
	var orgId;
	
	if(event.target.id == 'orgFilterSchoolId') {
		orgId = $('#orgFilterSchoolId').val();
	} else if(event.target.id == 'orgFilterBuildingId') {
		orgId = $('#orgFilterBuildingId').val();		
		findNextOrg(60);
	} else if(event.target.id == 'orgFilterDistrictId') {
		orgId = $('#orgFilterDistrictId').val();
		findNextOrg(50);
	} else if(event.target.id == 'orgFilterAreaId') {
		orgId = $('#orgFilterAreaId').val();
		findNextOrg(40);
	} else if(event.target.id == 'orgFilterRegionId') {
		orgId = $('#orgFilterRegionId').val();
		findNextOrg(30);		
	} else if(event.target.id == 'orgFilterStateId') {
		orgId = $('#orgFilterStateId').val();
		showAllOrgsFilters();
		clearPreviousSelection(childOrg);
		
		if(orgId != 0) {
			findNextOrg(20);
			showRequiredOrgsFilters();
		} else {
			childOrg = 30;
		}
	}


	var url;
	var dataValues;
	var filter;
	var filterOption;
	
	if(childOrg == '30') {
		url = 'getChildOrgsForFilter.htm';
		filter = '#orgFilterRegionId';
		filterOption = "#orgFilterRegionId option";
		dataValues =  { orgId: orgId, orgType: 'RG' };
	} else if(childOrg == '40') {
		url = 'getChildOrgsForFilter.htm';
		filter = '#orgFilterAreaId';
		filterOption = "#orgFilterAreaId option";
		dataValues =  { orgId: orgId, orgType: 'AR' };
	} else if(childOrg == '50') {
		url = 'getChildOrgsForFilter.htm';
		dataValues =  { orgId: orgId, orgType: 'DT' };
		filter = '#orgFilterDistrictId';
		filterOption = "#orgFilterDistrictId option";
	} else if(childOrg == '60') {
		url = 'getChildOrgsForFilter.htm';
		filter = '#orgFilterBuildingId';
		filterOption = "#orgFilterBuildingId option";
		dataValues =  { orgId: orgId, orgType: 'BLDG' };
	} else if(childOrg == '70') {
		url = 'getChildOrgsForFilter.htm';
		dataValues =  { orgId: orgId, orgType: 'SCH' };
		filter = '#orgFilterSchoolId';
		filterOption = "#orgFilterSchoolId option";
	}
	
	if (orgId != 0) {
		$.ajax({
	        url: url,
	        data: dataValues,
	        dataType: 'json',
	        type: "GET",
	        success: function(orgs) {
	        	
	        	clearPreviousSelection(childOrg);
	        	
	        	$.each(orgs, function(i, org) {
					$(filter).append($('<option></option>').attr("value", org.id).text(org.organizationName));
				});
				
	        	findNextOrg(childOrg);
	        	
				if (orgs.length == 1) {
					$(filterOption).removeAttr('selected').next('option').attr('selected', 'selected');
					//if(childOrg != 80){
						$(filter).trigger('change');
					//}
				}
	        }
		});
	} else {
		clearPreviousSelection(childOrg);
	}
});



function findNextOrg(currentOrg) {
	var stateId = $('#orgFilterStateId').val();
	if(stateId == -1)
		stateId = 0;

	for(var i=10;i<80;i=i+10) {
		if($.inArray(parseInt(currentOrg)+i, orgHierarchies[stateId]) >= 0) {
			childOrg = parseInt(currentOrg)+i;
			return;
		}
	}
	childOrg = 80;
}


function clearPreviousSelection(childOrg) {
	if(childOrg == 20) {
		$('#orgFilterStateId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterRegionId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterAreaId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterDistrictId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterBuildingId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterSchoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	} else if(childOrg == 30) {
		$('#orgFilterRegionId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterAreaId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterDistrictId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterBuildingId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterSchoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	} if(childOrg == 40) {
		$('#orgFilterAreaId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterDistrictId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterBuildingId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterSchoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	} else if(childOrg == 50) {
		$('#orgFilterDistrictId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterBuildingId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterSchoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	} else if(childOrg == 60) {
		$('#orgFilterBuildingId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#orgFilterSchoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	} else if(childOrg == 70) {
		$('#orgFilterSchoolId').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	}
}


function showRequiredOrgsFilters() {
	var stateId = $('#orgFilterStateId').val();
	if(stateId == -1)
		stateId = 0;
	
	for(var i=10;i<80;i=i+10) {
		if($.inArray(i, orgHierarchies[stateId]) < 0) {
			if(i == 30) {
				$('.orgFilterRegionDiv').hide();
			} else if(i == 40) {
				$('.orgFilterAreaDiv').hide();
			} else if(i == 50) {
				$('.orgFilterDistrictDiv').hide();
			} else if(i == 60) {
				$('.orgFilterBuildingDiv').hide();
			} else if(i == 70) {
				$('.orgFilterSchoolDiv').hide();
			}
		}
	}
}


function showAllOrgsFilters() {
	$('.orgFilterRegionDiv').show();
	$('.orgFilterAreaDiv').show();
	$('.orgFilterDistrictDiv').show();
	$('.orgFilterBuildingDiv').show();
	$('.orgFilterSchoolDiv').show();
}


function hideOrgsFilters(currentOrg) {
	if(currentOrg == 30) {
		$('.orgFilterStateDiv').hide();
	} else if(currentOrg == 40) {
		$('.orgFilterStateDiv').hide();
		$('.orgFilterRegionDiv').hide();
	} else if(currentOrg == 50) {
		$('.orgFilterStateDiv').hide();
		$('.orgFilterRegionDiv').hide();
		$('.orgFilterAreaDiv').hide();
	} else if(currentOrg == 60) {
		$('.orgFilterStateDiv').hide();
		$('.orgFilterRegionDiv').hide();
		$('.orgFilterAreaDiv').hide();
		$('.orgFilterDistrictDiv').hide();
	} else if(currentOrg == 70) {
		$('.orgFilterStateDiv').hide();
		$('.orgFilterRegionDiv').hide();
		$('.orgFilterAreaDiv').hide();
		$('.orgFilterDistrictDiv').hide();
		$('.orgFilterBuildingDiv').hide();
	}
}
