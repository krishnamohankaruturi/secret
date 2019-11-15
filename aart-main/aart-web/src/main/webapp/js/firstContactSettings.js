$(function(){
	initFistContactSettings();
});

function initFistContactSettings() {
	$("#savefirstContactSettings-top, #savefirstContactSettings-bottom").on("click",function(e) {;
		var fcsSettings = [];
		var subjectsSelected =true;
		$('#firstContactSettingsTable input[type=radio]:checked').each(function(){
			var orgId = $(this).prop('name');
			var scienceFlag=$('#scienceFlag'+orgId).is(':checked');
			var elaFlag=$('#elaFlag'+orgId).is(':checked');
			var mathFlag=$('#mathFlag'+orgId).is(':checked');
			fcsSettings.push({organizationId : $(this).prop('name'), categoryId : $(this).val(), scienceFlag:scienceFlag, mathFlag:mathFlag, elaFlag:elaFlag });
		});
		for(var i=0;i < fcsSettings.length;i++){
			if(fcsSettings[i].scienceFlag == false && fcsSettings[i].elaFlag == false && fcsSettings[i].mathFlag == false){
				subjectsSelected = false;
				$('tr#'+ fcsSettings[i].organizationId).find('.top-newsavefcssettings-error, .top-newsavefcssettings-error').show();
				setTimeout("aart.clearMessages()", 60000);
			}else{
				$('tr#'+ fcsSettings[i].organizationId).find('.top-newsavefcssettings-error, .top-newsavefcssettings-error').hide();
			}
		}
		if(subjectsSelected != false){
			$.ajax({
				url : 'saveFirstContactSettings.htm',
				data : {"firstContactSettings": JSON.stringify(fcsSettings)},
				dataType : 'json',
				type : 'POST'
			}).done(function(response) {
				if (response.valid) {
					var path='firstContactSettings';
					$('#top-savefcssettings-success, #bottom-savefcssettings-success').show();
			    } else {	   		                    
			    	 $('#top-savefcssettings-error, #top-savefcssettings-error').show();
			    }
				$('.top-newsavefcssettings-error, .top-newsavefcssettings-error').hide();
			 	setTimeout("aart.clearMessages()", 60000);
			}).fail( function(jqXHR, textStatus, errorThrown) {
					 $('#top-savefcssettings-error, #top-savefcssettings-error').show();
					setTimeout("aart.clearMessages()", 60000);
				})
		}
	});
}
