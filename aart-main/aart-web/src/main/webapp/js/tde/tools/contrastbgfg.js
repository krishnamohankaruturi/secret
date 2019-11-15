var colorContrastAccommodation = (function() {

	function init(profile) {
		var fgKeyValues = profile['foregroundcolour'];
		var bgKeyValues = profile['backgroundcolour'];

		var colorClassMap = {
			'fff' : ['contrast_white_green','contrast_white_red','contrast_white'],
			'000' : ['contrast_black_yellow','contrast_black_gray','contrast_black_white'],
			'ffffff' : ['contrast_white_green','contrast_white_red','contrast_white'],
			'000000' : ['contrast_black_yellow','contrast_black_gray','contrast_black_white'],
			'3b9e24' : 'contrast_white_green',
			'c62424' : 'contrast_white_red',
			'efee79' : 'contrast_black_yellow',
			'999' : 'contrast_black_gray',
			'999999' : 'contrast_black_gray',
			'272727' : 'contrast_white'
		};

		if (bgKeyValues && fgKeyValues) {
			var bgAttrNamesArray = _.pluck(bgKeyValues, 'attrName');
			var bgAttrValuesArray = _.pluck(bgKeyValues, 'attrValue');

			var fgAttrNamesArray = _.pluck(fgKeyValues, 'attrName');
			var fgAttrValuesArray = _.pluck(fgKeyValues, 'attrValue');

			//Flat to this object..
			var fgScreenContrast = _.object(fgAttrNamesArray, fgAttrValuesArray);
			var bgScreenContrast = _.object(bgAttrNamesArray, bgAttrValuesArray);

			if ((fgScreenContrast.assignedsupport && fgScreenContrast.assignedsupport === 'true') && (bgScreenContrast.assignedsupport && bgScreenContrast.assignedsupport === 'true')) {
				
				if(!$('#colorcontrasttool').is('*')) {
					$('#tools-c ul').append('<li id="colorcontrasttool" class="tool-colorcontrast">' + tool_names.contrast.name_colorcontrast + '<span title="' + tool_names.contrast.name_colorcontrast + '"></span></li>');
					$('#tool-arena').append(new EJS({
						url : 'js/views/tool/screenContrast.ejs'
					}).render());
	
					$("#screenContrast").draggable({ 
											containment: "window",
											start : function() {
												tool.getOnTop($('#screenContrast'));
											}
					});
					$('#colorcontrasttool, #screenContrast .tool-hdr a').on('click', function() {
						
						$('#screenContrast').toggle();
						$("#colorcontrasttool").toggleClass("selected");
						//$(document).trigger('tracker.tool', ['color_contrast']);
						tool.positionTool($('#screenContrast'));
						if ($('#screenContrast').is(':visible')) {
							tool.getOnTop($('#screenContrast'));
						}
					});
					
					$('#screenContrast').on('click', function() {
						tool.getOnTop($('#screenContrast'));
					});
	
					$("#screenContrast .drawer-content a span").click(function() {
						$('body').removeClassRegEx(/^contrast_/);
						$('body').removeClassRegEx(/^overlay_/);
	
						if ($(this).hasClass('contrastWhite')) {
							tde.testparam.colorcontrast = null;
							if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
	            				$('#tde-content * :not(.well .line)').css('background','');
	            				$('#tde-content * :not(well)').css('color','#000');
							}
						}
						if ($(this).hasClass('contrastBlackWhite')) {
							$('body').addClass('contrast_black_white');
							tde.testparam.colorcontrast = 'contrast_black_white';
							if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
	            				$('#tde-content * :not(.well .line)').css('background','#000');
	            				$('#tde-content * :not(well)').css('color','#fff');
							}
						}
						if ($(this).hasClass('contrastBlackGray')) {
							$('body').addClass('contrast_black_gray');
							tde.testparam.colorcontrast = 'contrast_black_gray';
							if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
	            				$('#tde-content * :not(.well .line)').css('background','#000');
	            				$('#tde-content * :not(well)').css('color','#999999');
							}
						}
						if ($(this).hasClass('contrastBlackYellow')) {
							$('body').addClass('contrast_black_yellow');
							tde.testparam.colorcontrast = 'contrast_black_yellow';
							if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
	            				$('#tde-content * :not(.well .line)').css('background','#000');
	            				$('#tde-content * :not(well)').css('color','#efee79');
							}
						}
						if ($(this).hasClass('contrastWhiteGreen')) {
							$('body').addClass('contrast_white_green');
							tde.testparam.colorcontrast = 'contrast_white_green';
							if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
	            				$('#tde-content * :not(.well .line)').css('background','');
	            				$('#tde-content * :not(well)').css('color','#3b9e24');
							}
						}
						if ($(this).hasClass('contrastWhiteRed')) {
							$('body').addClass('contrast_white_red');
							tde.testparam.colorcontrast = 'contrast_white_red';
							if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
	            				$('#tde-content * :not(.well .line)').css('background','');
	            				$('#tde-content * :not(well)').css('color','#c62424');
							}
						}
					});
				}

				if ((fgScreenContrast.activatebydefault && fgScreenContrast.activatebydefault === 'true') && (bgScreenContrast.activatebydefault && bgScreenContrast.activatebydefault === 'true')) {
					//Do the Logic Here..
					fgScreenContrast.colour = fgScreenContrast.colour.replace('#','');
					bgScreenContrast.colour = bgScreenContrast.colour.replace('#','');
					var fgClassName = colorClassMap[fgScreenContrast.colour];
					var bgClassName = colorClassMap[bgScreenContrast.colour];
					if (fgClassName && bgClassName && (bgClassName.indexOf(fgClassName)!=-1)) {
						$('body').addClass(fgClassName);
						tde.testparam.colorcontrast = fgClassName;
						if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
            				$('#tde-content *').css('background', '#'+bgScreenContrast.colour);
            				$('#tde-content * :not(well)').css('color','#'+fgScreenContrast.colour);
						}
					}
					//$(document).trigger('tracker.tool', ['color_contrast']);
				}

			}
		}
	}

	return {
		init : init
	};

})();
