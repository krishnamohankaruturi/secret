$(function(){

	$('input[id=orgFileData]').change(function() {
			$('#orgFileDataInput').val($('#orgFileData')[0].files[0].name);
		});
		
		$('#Organizations_TemplatedownloadquickHelp').on('mouseover',function(){
			$(".QuickHelpHint").show();
		});
		$('#Organizations_TemplatedownloadquickHelp').on('mouseout',function(){
			$(".QuickHelpHint").hide();
		});
		$('.QuickHelpHint').on('mouseover',function(){
			$(".QuickHelpHint").show();
		});
		$('.QuickHelpHint').on('mouseout',function(){
			$(".QuickHelpHint").hide();
		});
		$('#Organizations_Templatelink_Popup').on('click keypress',function(){
			if(event.type=='keypress'){
				   if(event.which !=13){
				    return false;
				   }
				  }
			$("#Organizations_TemplatedownloadquickHelpPopup").hide();
		});
		
});		