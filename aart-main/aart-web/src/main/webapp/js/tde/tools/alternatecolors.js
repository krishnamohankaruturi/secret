var alternateColorsAccommodation = (function() {

	function init(profile) {
		var keyValues = profile['colouroverlay'];
		//colourTint

		var colorClassMap = {
			'bef9c3' : 'overlay_light_green',
			'f2c5c5' : 'overlay_light_pink',
			'c5c5c5' : 'overlay_light_gray',
			'f5f2a4' : 'overlay_light_yellow',
			'87cffd' : 'overlay_light_blue',
			'fff' : 'overlay_white',
			'ffffff' : 'overlay_white'
		};

		if (keyValues) {
			var attrNamesArray = _.pluck(keyValues, 'attrName');
			var attrValuesArray = _.pluck(keyValues, 'attrValue');
			//var colorOverlay=_.zip(attrNamesArray,attrValuesArray).map(function(v){this[v[0]]=v[1];}, some = {});

			var colorOverlay = _.object(attrNamesArray, attrValuesArray);
			
			if (colorOverlay.assignedsupport && colorOverlay.assignedsupport === 'true') {

				$('#tools-c ul').append('<li id="overlaytool" class="tool-overlay">' + tool_names.colorOverlay.name + '<span title="' + tool_names.colorOverlay.name + '"></span></li>');

				$('#tool-arena').append(new EJS({
					url : 'js/views/tool/colorOverlay.ejs'
				}).render());
				$("#screenOverlay").draggable({
					containment : "window",
					start : function() {
						tool.getOnTop($('#screenOverlay'));
					}
				});
				$('#overlaytool, #screenOverlay .tool-hdr a').on('click', function(ev) {
					//$(document).trigger('tracker.tool', ['alternate_colors']);
					$('#screenOverlay').toggle();
					$("#overlaytool").toggleClass("selected");
					tool.positionTool($('#screenOverlay'));
					if ($('#screenOverlay').is(':visible')) {
						tool.getOnTop($('#screenOverlay'));
					}
				});
				
				$('#screenOverlay').on('click', function() {
					tool.getOnTop($('#screenOverlay'));
				});

				$("#screenOverlay .drawer-content a span").on('click', function(ev) {
					$('body').removeClassRegEx(/^overlay_/);
					$('body').removeClassRegEx(/^contrast_/);

					// Overlay
					if ($(this).hasClass('overlayWhite')) {
						$('body').addClass('overlay_white');
						if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
            				$('#tde-content *').css('background','');
						}
					}
					if ($(this).hasClass('overlayLightBlue')) {
						$('body').addClass('overlay_light_blue');
						if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
            				$('#tde-content *').css('background','#87cffd');
						}
					}
					if ($(this).hasClass('overlayLightYellow')) {
						$('body').addClass('overlay_light_yellow');
						if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
            				$('#tde-content *').css('background','#f5f2a4');
						}
					}
					if ($(this).hasClass('overlayLightGray')) {
						$('body').addClass('overlay_light_gray');
						if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
            				$('#tde-content *').css('background','#c5c5c5');
						}
					}
					if ($(this).hasClass('ovleryaLightPink')) {
						$('body').addClass('overlay_light_pink');
						if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
            				$('#tde-content *').css('background','#f2c5c5');
						}
					}
					if ($(this).hasClass('overlayLightGreen')) {
						$('body').addClass('overlay_light_green');
						if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
            				$('#tde-content *').css('background','#bef9c3');
						}
					}

				});

				if (colorOverlay.activatebydefault && colorOverlay.activatebydefault === 'true') {

					//$('#overlaytool, #screenOverlay .tool-hdr a').trigger('click');
					//Write Logic to map the colors to classes.
					//colourTint
					colorOverlay.colour = colorOverlay.colour.replace('#','');
					$('body').addClass(colorClassMap[colorOverlay.colour]);
					
					if(testObj.uiname=='altTest') {
						$('body').css({background: '#'+colorOverlay.colour});
						
					}
					
					if(tasks[tde.testparam.currentQuestion].taskType == 'ITP') {
        				$('#tde-content *').css('background','#'+colorOverlay.colour);
					}
				}
			}

		}
	}

	return {
		init : init
	};

})();
