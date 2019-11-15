$(function($){
	batchRegistrationInit();
	populateAssessmentPrograms();
	$('#otwDialog').dialog({
		resizable: false,
		height: 200,
		width: 550,
		modal: true,
		autoOpen: false,
		title: 'Select Window',
		buttons: {
			Cancel: function(){
				$(this).dialog('close');
		    },
		    Ok: function(){
		    	var otwId = $('#otwId').val();
		    	if (otwId > 0){
			    	$.ajax({
			    		url: 'questar/ampProcess.htm',
				        data: {operationalTestWindowId: otwId},
				        dataType: 'text',
				        type: 'POST',
				        success: function(data){
				        	alert(data);
				        }
			    	});
					$(this).dialog('close');
		    	} else {
		    		$("#otwDialogErrors").html('Please select Operational Test Window.');
		    	}
			}
		}
	});
	
	// these both go to QuestarController
    $('#ampImportBtn').off('click').on('click', function(){
		$.ajax({
	        url: 'questar/ampImport.htm',
	        data: {},
	        dataType: 'text',
	        type: 'POST',
	        success: function(data){
	        	alert(data);
	        }
		});
	});
	
	$('#ampProcessBtn').on('click', function(){
		$("#otwDialogErrors").html('');
		var select = $('#otwId');
		$('option', select).slice(1).remove();
		$.ajax({
	        url: 'questar/getAMPOperationalTestWindows.htm',
	        data: {},
	        dataType: 'json',
	        type: 'POST',
	        success: function(data){
	        	if (data != null && data.length > 0){
	        		var monthNames = [
						'January', 'February', 'March',
						'April', 'May', 'June', 'July',
						'August', 'September', 'October',
						'November', 'December'
	        		];
	        		for (var x = 0; x < data.length; x++){
	        			var text = '';
	        			var opTestWindow = data[x];
	        			var eff = new Date(opTestWindow.effectiveDate);
	        			var exp = new Date(opTestWindow.expiryDate);
	        			var effStr = monthNames[eff.getMonth()] + ' ' + eff.getDate() + ', ' + eff.getFullYear();
	        			var expStr = monthNames[exp.getMonth()] + ' ' + exp.getDate() + ', ' + exp.getFullYear();
	        			text += opTestWindow.windowName + ' (' + effStr + ' - ' + expStr + ')';
	        			select.append($('<option>', { value: opTestWindow.id, text: text }));
	        		}
	        		$('#otwDialog').dialog('open');
	        	}
	        }
		});
	});
});