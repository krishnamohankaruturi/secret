$('#pnpOptionsControlDiv').find('.onoffswitch').on('click',function(ev) {
	
	ev.preventDefault();
	ev.stopPropagation();
	
	var categoryId = $(this).find('input').data('categoryid');
	var assessmentProgram = $(this).find('input').data('ap');
	var pianacId = $(this).find('input').data('pianacid');
	
	if ($(this).find('input:checkbox').prop("checked")) {
		$(this).find('input:checkbox').prop('checked',false);
		if(($(this).find('input').data('tabtype')  != undefined || $(this).find('input').data('tabtype')  != null) 
			&& $(this).find('input').data('tabtype') === 'othertab'){
			savePnpOptions('hide', categoryId, assessmentProgram, pianacId);
		} else {
			savePnpOptions('disable', categoryId, assessmentProgram, pianacId);
		}
	} else {		
		$(this).find('input:checkbox').prop('checked', true);
		if(($(this).find('input').data('tabtype')  != undefined || $(this).find('input').data('tabtype')  != null) 
				&& $(this).find('input').data('tabtype') === 'othertab'){
			savePnpOptions('show', categoryId, assessmentProgram, pianacId);
		} else {
			savePnpOptions('enable', categoryId, assessmentProgram, pianacId);
		}
	}
});

function savePnpOptions(viewOption, categoryId, assessmentProgram, pianacId) {
	$.ajax({
		url : 'savePNPoptions.htm',
		data : {
			viewOption : viewOption,
			categoryId : categoryId,
			assessmentProgram : assessmentProgram,
			pianacId : pianacId
		},
		dataType : 'json',
		type : 'POST'	
	}).done(function(response) {
		$('#pnpOptionsNotificationMsg').html('Option changes saved successfully.');
		$('#pnpOptionsNotificationMsg').addClass('ui-state-highlight');
		setTimeout("$('#pnpOptionsNotificationMsg').html('');$('#pnpOptionsNotificationMsg').removeClass('ui-state-highlight')", 5000);	
	});
}

function saveStatePnpOptionsOverride(viewOption, stateId, assessmentProgram, pianacId) {
	$.ajax({
		url : 'saveStatePnpOptionsOverride.htm',
		data : {
			viewOption : viewOption,
			stateId : stateId,
			assessmentProgram : assessmentProgram,
			pianacId : pianacId
		},
		dataType : 'json',
		type : 'POST'
	}).done(function(response) {
		$('#pnpStateNotificationsMsg').html('Option changes saved successfully.');
		$('#pnpStateNotificationsMsg').addClass('ui-state-highlight');
		setTimeout("$('#pnpStateNotificationsMsg').html('');$('#pnpStateNotificationsMsg').removeClass('ui-state-highlight')", 5000);	
	});
}