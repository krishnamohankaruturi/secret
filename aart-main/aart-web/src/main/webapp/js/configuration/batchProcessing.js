$(function(){
	$('#batchTabs').tabs({
		beforeActivate: function(event, ui) {
			if(ui.newPanel[0].id == 'tabs_registration') {
				$('#tabs_reporting').hide();
				$('#tabs_ksdewebService').hide();
				$('#tabs_registration').show();
			}else if(ui.newPanel[0].id == 'tabs_reporting'){
				$('#tabs_registration').hide();
				$('#tabs_ksdewebService').hide();
				$('#tabs_reporting').show();
			}else if(ui.newPanel[0].id== 'tabs_ksdewebService'){
				$('#tabs_registration').hide();
				$('#tabs_reporting').hide();
				$('#tabs_ksdewebService').show();
			}
		}
	});
});