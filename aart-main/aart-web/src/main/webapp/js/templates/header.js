var currentUserIdFromHeader = $('#currentUserIdFromHeader').val();
window.sessionStorage.setItem("currentUserId", currentUserIdFromHeader );

function populateDefaultList($select, responseData, displayProperty, idProperty, triggerNext){
	if (responseData !== undefined && responseData !== null && responseData.length > 0){
		for (var i = 0; i < responseData.length; i++){
			if (responseData[i].isDefault){
				$select.append($('<option>', {value: responseData[i][idProperty], text: responseData[i][displayProperty]}).attr('selected', 'selected'));
			} else {
				$select.append($('<option>', {value: responseData[i][idProperty], text: responseData[i][displayProperty]}));
			}
		}
		if(triggerNext){
			$select.trigger('change');
		}
	} else {
		// nothing returned, no action required
	}
}

function changeDefaultOrganization(groupId, dependentElementId){
	$.ajax({
		url: 'templates/changeDefaultOrganization.htm',
		data: {
			groupId : groupId
		},
		dataType: 'json',
		type: 'POST',
		success: function(data){
			if (data !== undefined && data !== null && data.length > 0) {
				$select = $('#'+dependentElementId);
				$select.find('option').remove();
				populateDefaultList($select, data, 'organizationName', 'id', true);
			} else {
				
			}
		}
	});
}

function changeDefaultAssessmentProgram(organizationId, dependentElementId){
	$.ajax({
		url: 'templates/changeDefaultAssessmentProgram.htm',
		data: {
			organizationId : organizationId
		},
		dataType: 'json',
		type: 'POST',
		success: function(data) {
			if (data !== undefined && data !== null && data.length > 0) {
				$select = $('#'+dependentElementId);
				$select.find('option').remove();
				populateDefaultList($select, data, 'abbreviatedname', 'id', true);
			} else {
				
			}
		}
	});
}