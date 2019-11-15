var previewAccessProfileFlag = $('#previewAccessProfileFlag');
$('#accessProfileTabs').tabs({
	beforeActivate: 
			function(event, ui) {
		if(ui.newTab.index() == 0) {
			$('.containerDiv').hide();
			$('.errorMessage').hide();
			$('.attributeDiv').remove();
			loadProfileSummary();
		}
	}
});

if(previewAccessProfileFlag){
	$('.controlset').css('width','20%');
	loadProfileSummary();
	$.ajaxSetup({ cache: false });
	
$.ajax({
	url: 'loadStudentProfileItemAttributeData.htm',
	data: {
		studentId: $('#studentIdPNP').val(),
	},			
	dataType: 'json',
	type: "GET"
}).done(function(response) {	
	for(var i = 0; i < response.length;i++) {
		$('input, select').each(function() {
			var $this = $(this);
			var $thisClass = $this.attr('class');
			var $thisId = $this.data('id');
			var attributeText = (response[i].attributeContainerName+response[i].attributeName).toLowerCase();
			if($thisClass !== null && $thisClass !== undefined && $thisClass !== "undefined" && $thisClass.toLowerCase().indexOf(attributeText) == 0) {
				if($thisId == undefined || $thisId == "undefined" || $thisId == "") {
					$this.data('id', response[i].id);
					loadDisplayEnhancements($this, response[i]);
					hideOrDisableDisplayEnhancements(response[i]);
					loadLanguageBraille($this,response[i]);
					hideOrDisableLanguageBraille(response[i]);
					loadAudioEnvironment($this, response[i]);
					hideOrDisableAudioEnvironment(response[i]);
					loadSystemIndependent($this, response[i]);
					hideOrDisableSystemIndependent(response[i]);
					//setCss();
				}
			}
		});
	}
});
} else {
	$('.controlset').css('width','20%');
	//$("#accessProfileTabs").find('li:eq(' + 1 + ')').addClass('ui-state-hidden');
	$('#accessProfileTabs .t-item:eq(1)').hide();
	loadProfileSummary();
	
	$.ajaxSetup({ cache: false });
}