var districtArray;
var schoolsArray;
var organizationTypeArray;
function toolsMenu() {
	"use strict";
	var winWidth = $(window).width();
	var left = "0";
	var padLeft = "185px";
	var className = "menuHide";

	if (winWidth < 801) {
		left = "-185px";
		padLeft = "0";
		className = "menuShow";
	}

	$("#sidebar").stop().animate({
		"left" : left
	}, 300);
	$("#container").stop().animate({
		"padding-left" : padLeft
	}, 300);
	$("#menuToggle").removeClass().addClass(className);

	toolsTabDisableOtherTab();

}

function toolsTabDisableOtherTab() {

	$('#scoring_menu li ul li a').filter(function(indx) {
		var enable = false;

		enable = ($(this).attr('href') === "#tabs_org_merge" && $('#hdnMergeSchool').length > 0 ? true : false);

		if (!enable)
			enable = $(this).attr('href') === "#tabs_org_move" && $('#hdnMoveSchool').length > 0 ? true : false;

		if (!enable)
			enable = $(this).attr('href') === "#tabs_org_deactivate" && $('#hdnDeactivateOrganization').length > 0 ? true : false;

		if (!enable)
			enable = $(this).attr('href') === "#tabs_org_reactivate" && $('#hdnReactivateOrganization').length > 0 ? true : false;

		return !enable;
	}).hover(function() {
		$(this).css("background", "#e8e8e8");
	}, function() {
		$(this).css("background", "#e8e8e8");
	}).css("background", "#e8e8e8").removeAttr("href");
}

function populateStatesForMove(){
	var stateOrgSelect = $('#sourceStateForMoving');clrStateOptionText='';
	
	$('#sourceStateForMoving').html("");
	$('#sourceStateForMoving').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		
	$.ajax({
        url: 'getStatesOrgsForUser.htm',
        data: { },
        dataType: 'json',
        type: "GET"
	}).done(function(states) {
    	if (states !== undefined && states !== null && states.length > 0) {
			$.each(states, function(i, stateOrg) {
				clrStateOptionText = states[i].organizationName;
				stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(clrStateOptionText));
			});
			if (states.length == 1) {
				stateOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				$("#sourceStateForMoving").trigger('change');
			}
		} 
	
    	$('#sourceStateForMoving').trigger('change.select2');
    	
      });
}

function populateDistrictsForMove(stateId){
	$('#sourceDistrictForMoving').val('').find('option:not(:first)').remove();
	$('#destinationdistrictForMoving').val('').find('option:not(:first)').remove();
	if(stateId !=null && stateId !=undefined && stateId !=''){
		$.ajax({
			url : 'getOrgsBasedOnUserContext.htm',
			data : {
				orgId 	: stateId,
				orgType	:'DT',
	        	orgLevel:50
			},
			dataType : 'json',
			type : "GET"
		}).done(function(schoolOrgs) {
			$('#sourceDistrictForMoving').html("");
			$('#sourceDistrictForMoving').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$('#destinationdistrictForMoving').html("");
			$('#destinationdistrictForMoving').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#sourceDistrictForMoving').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				$('#destinationdistrictForMoving').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});	
			if (schoolOrgs.length == 1) {
				$("#sourceDistrictForMoving option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#destinationdistrictForMoving option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#sourceDistrictForMoving").trigger('change');
				$("#destinationdistrictForMoving").trigger('change');
			}
			$('#sourceDistrictForMoving').trigger('change.select2');
			$('#destinationdistrictForMoving').trigger('change.select2');
        });
  }
}
function populateSchoolsForMove(districtId) {
	
	var moveSchoolNameSelect = $('#sourceSchoolForMoving');
	moveSchoolNameSelect.val('').find('option:not(:first)').remove();
	
		if(districtId !=null && districtId !=undefined && districtId !=''){
			$.ajax({
				url : 'getOrgsBasedOnUserContext.htm',
				data : {
					orgId : districtId,
					orgType:'SCH',
		        	orgLevel:70
				},
				dataType : 'json',
				type : "GET"
			}).done(function(schoolOrgs) {
				$('#sourceSchoolForMoving').html("");
				$('#sourceSchoolForMoving').append($('<option></option>').val("").html("Select")).trigger('change.select2');
				$.each(schoolOrgs, function(i, schoolOrg) {
					$('#sourceSchoolForMoving').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				});	
				if (schoolOrgs.length == 1) {
						$("#sourceSchoolForMoving option").removeAttr('selected').next('option').attr('selected', 'selected');
						$("#sourceSchoolForMoving").trigger('change');
				}
					$('#sourceSchoolForMoving').trigger('change.select2');
	        });
	}
}

function populateStatesForMerge(){
	var stateOrgSelect = $('#sourceStateForMerging');clrStateOptionText='';
	
	$('#sourceStateForMerging').html("");
	$('#sourceStateForMerging').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		$.ajax({
	        url: 'getStatesOrgsForUser.htm',
	        data : {},
	        dataType: 'json',
	        type: "GET" 
		}).done(function(states) {
        	if (states !== undefined && states !== null && states.length > 0) {
				$.each(states, function(i, stateOrg) {
					clrStateOptionText = states[i].organizationName;
					stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(clrStateOptionText));
				});
				if (states.length == 1) {
					stateOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$("#sourceStateForMerging").trigger('change');
				}
			} 
		
        	$('#sourceStateForMerging').trigger('change.select2');
        	
          });
 }

function populateDistrictsForMerge(stateId) {
	$('#sourceDistrictForMerging').val('').find('option:not(:first)').remove();
	$('#destinationDistrictForMerging').val('').find('option:not(:first)').remove();
	if(stateId !=null && stateId !=undefined && stateId !=''){
		$.ajax({
			url : 'getOrgsBasedOnUserContext.htm',
			data : {
				orgId : stateId,
				orgType:'DT',
	        	orgLevel:50
			},
			dataType : 'json',
			type : "GET" 
		}).done(function(schoolOrgs) {
			$('#sourceDistrictForMerging').html("");
			$('#sourceDistrictForMerging').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$('#destinationDistrictForMerging').html("");
			$('#destinationDistrictForMerging').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#sourceDistrictForMerging').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				$('#destinationDistrictForMerging').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});	
			if (schoolOrgs.length == 1) {
				$("#sourceDistrictForMerging option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#destinationDistrictForMerging option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#sourceDistrictForMerging").trigger('change');
				$("#sourceDistrictForMerging").trigger('change');
			}
			$('#sourceDistrictForMerging').trigger('change.select2');
			$('#destinationDistrictForMerging').trigger('change.select2');
        });
  }
}

function populateSchoolsForMerge(districtId,eventSource) {
	var mergeSourceSchool = $('#sourceschoolForMerging'), mergeDestinationSchool = $('#destinationschoolForMerging');
	if(eventSource==='source'){
		mergeSourceSchool.val('').find('option:not(:first)').remove();
	}else if(eventSource==='destination'){
		mergeDestinationSchool.val('').find('option:not(:first)').remove();
	}
	if(districtId !=null && districtId !=undefined && districtId !=''){
		$.ajax({
			url : 'getOrgsBasedOnUserContext.htm',
			data : {
				orgId : districtId,
				orgType:'SCH',
	        	orgLevel:70
			},
			dataType : 'json',
			type : "GET"
		}).done(function(schoolOrgs) {
			if(eventSource==='source'){
				$('#sourceschoolForMerging').html("");
				$('#sourceschoolForMerging').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			}else if(eventSource==='destination'){
				$('#destinationschoolForMerging').html("");
				$('#destinationschoolForMerging').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			}
			$.each(schoolOrgs, function(i, schoolOrg) {
				if(eventSource==='source'){
					$('#sourceschoolForMerging').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				}else if(eventSource==='destination'){
					$('#destinationschoolForMerging').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				}
			});	
			if (schoolOrgs.length == 1) {
				if(eventSource==='source'){
					$("#sourceschoolForMerging option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#sourceschoolForMerging").trigger('change');
				}else if(eventSource==='destination'){
					$("#destinationschoolForMerging option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#destinationschoolForMerging").trigger('change');
				}
			}
			if(eventSource==='source'){
				$('#sourceschoolForMerging').trigger('change.select2');
			}else if(eventSource==='destination'){
				$('#destinationschoolForMerging').trigger('change.select2');
			}
        });
	}
}

function populateSchoolsForDeactivation(districtId) {
	$('#schoolsDeactivateOrg').val('').find('option:not(:first)').remove();
	
		if(districtId !=null && districtId !=undefined && districtId !=''){
			$.ajax({
				url : 'getOrgsBasedOnUserContext.htm',
				data : {
					orgId : districtId,
					orgType:'SCH',
		        	orgLevel:70
				},
				dataType : 'json',
				type : "GET"
			}).done(function(schoolOrgs) {
				$('#schoolsDeactivateOrg').html("");
				$('#schoolsDeactivateOrg').append($('<option></option>').val("").html("Select")).trigger('change.select2');
				$.each(schoolOrgs, function(i, schoolOrg) {
					$('#schoolsDeactivateOrg').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				});	
				if (schoolOrgs.length == 1) {
						$("#schoolsDeactivateOrg option").removeAttr('selected').next('option').attr('selected', 'selected');
						$("#schoolsDeactivateOrg").trigger('change');
				}
					$('#schoolsDeactivateOrg').trigger('change.select2');
	        });
	}
}

function populateDistrictsForDeactivation(stateId) {
	$('#districtDeactivateOrg').val('').find('option:not(:first)').remove();
	if(stateId !=null && stateId !=undefined && stateId !=''){
		$.ajax({
			url : 'getOrgsBasedOnUserContext.htm',
			data : {
				orgId : stateId,
				orgType:'DT',
	        	orgLevel:50
			},
			dataType : 'json',
			type : "GET"
		}).done(function(schoolOrgs) {
			$('#districtDeactivateOrg').html("");
			$('#districtDeactivateOrg').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#districtDeactivateOrg').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});	
			if (schoolOrgs.length == 1) {
				$("#districtDeactivateOrg option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#districtDeactivateOrg").trigger('change');
			}
			$('#districtDeactivateOrg').trigger('change.select2');
        });
  }
}

function populateDistricts(stateId,sourceElement) {
	
	var districtOrgSelect = '';
	if(sourceElement == 'deactivate'){
		districtOrgSelect=$('#districtsDeactivateOrg');
	}else if(sourceElement == 'reactivate'){
		districtOrgSelect=$('#districtsReactivateOrg');
	}
	districtOrgSelect.val('').find('option:not(:first)').remove();
	if(stateId !=null && stateId !=undefined && stateId !=''){
		$.ajax({
			url : 'getOrgsBasedOnUserContext.htm',
			data : {
				orgId : stateId,
				orgType:'DT',
	        	orgLevel:50
			},
			dataType : 'json',
			type : "GET"
		}).done(function(schoolOrgs) {
			districtOrgSelect.html("");
			districtOrgSelect.append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$.each(schoolOrgs, function(i, schoolOrg) {
				districtOrgSelect.append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});	
			if (schoolOrgs.length == 1) {
				districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				districtOrgSelect.trigger('change');
			}
			districtOrgSelect.trigger('change.select2');
        });
  }
}

function populateStates(eventSource){

	var stateOrgSelect = '',clrStateOptionText='';
    
    if(eventSource == 'deactivate'){
    	stateOrgSelect=$('#stateDeactivateOrg');
    }else if(eventSource == 'reactivate'){
    	stateOrgSelect=$('#stateReactivateOrg');
    }
	
    stateOrgSelect.html("");
	stateOrgSelect.append($('<option></option>').val("").html("Select")).trigger('change.select2');
		$.ajax({
	        url: 'getStatesOrgsForUser.htm',
	        data : {},
	        dataType: 'json',
	        type: "GET"
		}).done(function(states) {
	        	if (states !== undefined && states !== null && states.length > 0) {
					$.each(states, function(i, stateOrg) {
						clrStateOptionText = states[i].organizationName;
						stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(clrStateOptionText));
					});
					if (states.length == 1) {
						stateOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						stateOrgSelect.trigger('change');
					}
				} 
			
	        	stateOrgSelect.trigger('change.select2');
	        	
	          });
}

function populateSchoolsForReactivation(districtId) {
	$('#schoolsReactivateOrg').val('').find('option:not(:first)').remove();
	
		if(districtId !=null && districtId !=undefined && districtId !=''){
			$.ajax({
				url : 'getDeactivateOrgsBasedOnUserContext.htm',
				data : {
					orgId : districtId,
					orgType:'SCH',
		        	orgLevel:70
				},
				dataType : 'json',
				type : "GET"
			}).done(function(schoolOrgs) {
					$('#schoolsReactivateOrg').html("");
					$('#schoolsReactivateOrg').append($('<option></option>').val("").html("Select")).trigger('change.select2');
					$.each(schoolOrgs, function(i, schoolOrg) {
						$('#schoolsReactivateOrg').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
					});	
					if (schoolOrgs.length == 1) {
							$("#schoolsReactivateOrg option").removeAttr('selected').next('option').attr('selected', 'selected');
							$("#schoolsReactivateOrg").trigger('change');
					}
						$('#schoolsReactivateOrg').trigger('change.select2');
		        });
	}
}

function populateDistrictsForReactivation(stateId) {
	$('#districtReactivateOrg').val('').find('option:not(:first)').remove();
	if(stateId !=null && stateId !=undefined && stateId !=''){
		$.ajax({
			url : 'getDeactivateOrgsBasedOnUserContext.htm',
			data : {
				orgId : stateId,
				orgType:'DT',
	        	orgLevel:50
			},
			dataType : 'json',
			type : "GET"
		}).done(function(schoolOrgs) {
			$('#districtReactivateOrg').html("");
			$('#districtReactivateOrg').append($('<option></option>').val("").html("Select")).trigger('change.select2');
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#districtReactivateOrg').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});	
			if (schoolOrgs.length == 1) {
				$("#districtReactivateOrg option").removeAttr('selected').next('option').attr('selected', 'selected');
				//$("#districtReactivateOrg").trigger('change');
			}
			$('#districtReactivateOrg').trigger('change.select2');
        });
  }
}

function getOrganizationTypes(eventSource) {
	
	var orgTypeSelect = '';
	if(eventSource =='deactivate'){
		orgTypeSelect = $('#typeOfOrganization');
	}else if(eventSource =='reactivate'){
		orgTypeSelect = $('#typesOfOrganization');
	}
	$.ajax({
		url : 'getOrganizationTypes.htm',
		data : {},
		dataType : 'json',
		type : "GET"
	}).done(function(organizationTypes) {
		organizationTypeArray = organizationTypes;
		orgTypeSelect.find('option').filter(function() {
			return $(this).val() > 0;
		}).remove().end();
		orgTypeSelect.trigger('change.select2');
		if (organizationTypeArray !== undefined && organizationTypeArray !== null && organizationTypeArray.length > 0) {
			$.each(organizationTypeArray, function(i) {
				optionText = organizationTypeArray[i].typeName;
				if(organizationTypeArray[i].organizationTypeId =='5' || organizationTypeArray[i].organizationTypeId =='7'){
					orgTypeSelect.append($('<option></option>').val(organizationTypeArray[i].organizationTypeId).html(optionText));
				}
			});
		}
		orgTypeSelect.trigger('change.select2');
	});
}

function getSummaryForOrgManagement(organizationTypeId, organizationId, organizationName, summaryDisplayId){
	$('#' + summaryDisplayId).hide();
	$('#' + summaryDisplayId).html('');
	$.ajax({
		url: 'getSummaryForOrgManagement.htm', 
		dataType: 'json',
		data: {
				organizationId : organizationId,
				organizationTypeId : organizationTypeId,
				organizationName : organizationName
	        	},				
		type: "POST"
	}).done(function(response) {
		$('#' + summaryDisplayId).html('');
		$('#' + summaryDisplayId).append($('<div style="color: #82a53d;margin-left: 28px;margin-top: 20px;">'+response.successMessage+'</div>'));
		$('#' + summaryDisplayId).append($('<div style="margin-left: 28px;margin-top: 20px;">Students : ' + response.studentCount+'</div>'));
		$('#' + summaryDisplayId).append($('<div style="margin-left: 28px;margin-top: 20px;">Rosters : ' + response.rosterCount+'</div>'));
		$('#' + summaryDisplayId).append($('<div style="margin-left: 28px;margin-top: 20px;">Users : ' + response.userCount+'</div>'));
		$('#' + summaryDisplayId).show();
	});
}

function getSummaryForDeactivateOrgManagement(organizationTypeId, organizationId, organizationName, summaryDisplayId){
	$('#' + summaryDisplayId).hide();
	$('#' + summaryDisplayId).html('');
	$.ajax({
		url: 'getSummaryForDeactivateOrgManagement.htm', 
		dataType: 'json',
		data: {
				organizationId : organizationId,
				organizationTypeId : organizationTypeId,
				organizationName : organizationName
	        	},				
		type: "POST"
	}).done(function(response) {
		$('#' + summaryDisplayId).html('');
		$('#' + summaryDisplayId).append($('<div style="color: #82a53d;margin-left: 28px;margin-top: 20px;">'+response.successMessage+'</div>'));
		$('#' + summaryDisplayId).append($('<div style="margin-left: 28px;margin-top: 20px;">Students : ' + response.studentCount+'</div>'));
		$('#' + summaryDisplayId).append($('<div style="margin-left: 28px;margin-top: 20px;">Rosters : ' + response.rosterCount+'</div>'));
		$('#' + summaryDisplayId).append($('<div style="margin-left: 28px;margin-top: 20px;">Users : ' + response.userCount+'</div>'));
		$('#' + summaryDisplayId).show();
	});
}