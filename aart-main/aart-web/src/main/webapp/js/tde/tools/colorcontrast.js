var reverseContrastAccommodation=(function() {

	function init(profile) {
		var keyValues = profile['invertcolourchoice'];

		if (keyValues) {
			var attrNamesArray = _.pluck(keyValues, 'attrName');
			var attrValuesArray = _.pluck(keyValues, 'attrValue');
			
			//Flat to this object..
			var screenContrast = _.object(attrNamesArray, attrValuesArray);
			

			if (screenContrast.assignedsupport && screenContrast.assignedsupport==='true') {
				
				if(!$('#contrasttool').is('*')) {
					$('#tools-c ul').append('<li id="contrasttool" class="tool-contrast">' + tool_names.contrast.name + '<span title="' + tool_names.contrast.name + '"></span></li>');
					$('#tool-arena').append(new EJS({
						url : 'js/views/tool/screenContrast.ejs'
					}).render());
	
					//$("#screenContrast").draggable({ containment: "window" });
					$('#contrasttool').on('click', function() {
						//$(document).trigger('tracker.tool', ['reverse_contrast']);
						//$('#screenContrast').toggle();
						$("#contrasttool").toggleClass("selected");
						if($('body').hasClass('contrast_black_white')){
							$('body').removeClass('contrast_black_white');
							$("#contrasttool").removeClass("selected");
							tde.testparam.colorcontrast = null;
							
							//if(tasks[tde.testparam.currentQuestion].tasktype == 'ITP') {
								if(testObj.uiname=='altTest') {
									$('body').css('background','');
								}
								$('#tde-content * :not(.well .line)').css('background','');
								$('#tde-content *').css('color','');
								$('#tde-content').css('background','');
							//}
						}else{
							$('body').addClass('contrast_black_white');
							tde.testparam.colorcontrast = 'contrast_black_white';
							if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
								if(testObj.uiname=='altTest') {
									$('body').css('background','#000');
								}
								$('#tde-content').css('background','#000');
	            				$('#tde-content *').css('color','#fff');
	            				$('#tde-content * :not(.well .line)').css('background','#000');
							}
						}
						
						tool.positionTool($('#screenContrast'));
						if ($('#screenContrast').is(':visible')) {
							tool.getOnTop($('#screenContrast'));
						}
					});
	
					$('#screenContrast').on('click', function() {
						tool.getOnTop($('#screenContrast'));
					});
				}

				if (screenContrast.activatebydefault && screenContrast.activatebydefault==='true') {
					$('body').addClass('contrast_black_white');
					tde.testparam.colorcontrast = 'contrast_black_white';
					$("#contrasttool").toggleClass("selected");
				}

			}
		}
	}
	return {
		init:init
	};

})();
