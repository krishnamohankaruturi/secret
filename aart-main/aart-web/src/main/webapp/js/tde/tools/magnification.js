var magnificationAccommodation = (function() {
	var magnificationAmount=0;
	function scaleBody(scale) {
		if(testObj.uiname == 'genTest') {
			var tdeContent = $('#tde-content:not(.magnified-content)');
			
			tdeContent.removeAttr('style');
			tdeContent.css("-moz-transform", "scale(1." + scale + ")");
			tdeContent.css("-moz-transform-origin", "0 0");
			tdeContent.css("transform", "scale(1." + scale + ")");
			tdeContent.css("transform-origin", "0 0");
			tdeContent.css("-webkit-transform", "scale(1." + scale + ")");
			tdeContent.css("-webkit-transform-origin", "0 0");
	
			$('.tde-controls-c').removeAttr('style');
			$('.tde-controls-c').css("-moz-transform", "scale(1." + scale + ")");
			$('.tde-controls-c').css("-webkit-transform", "scale(1." + scale + ")");
			$('.tde-controls-c').css("-moz-transform-origin", "0 0");
			$('.tde-controls-c').css("-webkit-transform-origin", "0 0");
			$('.tde-controls-c').css("transform-origin", "0 0");
	
			$('body').removeClassRegEx(/^scale_/);
			if (scale != 0) {
				$('body').addClass('scale_' + scale + 'x');
			}
			
			/*--------------- adjustment for height --------------------*/
			var myHeight = 0;
			if( typeof( window.innerWidth ) == 'number' ) {
			    //Non-IE
			    myHeight = window.innerHeight;
			} else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
			    //IE 6+ in 'standards compliant mode'
			    myHeight = document.documentElement.clientHeight;
			} else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
			    //IE 4 compatible
			    myHeight = document.body.clientHeight;
			}
			
			$('#tools-c').css('height', (myHeight * 0.9) );
		} else {
			//$('body').css({'font-size':((80+(scale*10))+(scale/2)*10)+'%'});
			//$('body').css('font-size', parseFloat($('body').css('font-size')) + (scale*2));
			$('body').css("-moz-transform", "scale(1." + scale + ")");
			$('body').css("-moz-transform-origin", "0 0");
			$('body').css("transform", "scale(1." + scale + ")");
			$('body').css("transform-origin", "0 0");
			$('body').css("-webkit-transform", "scale(1." + scale + ")");
			$('body').css("-webkit-transform-origin", "0 0");
			$('body').removeClassRegEx(/^alt-scale_/);
			$('body').addClass('alt-scale_' + scale + 'x');
		}

	}
	
	function getMagLevel() {
		return magnificationAmount;
	}

	function init(profile) {
		var magnificationObj = null;
		
		var keyValues = profile['magnification'];
		if (keyValues) {
			var attrNamesArray = _.pluck(keyValues, 'attrName');
			var attrValuesArray = _.pluck(keyValues, 'attrValue');
			//Flat to this object..
			magnificationObj = _.object(attrNamesArray, attrValuesArray);
		}
		
		if (test.canUseTool('WSM') || ( magnificationObj && magnificationObj.assignedsupport && magnificationObj.assignedsupport === 'true') ) {
				$('#tools-c ul').append('<li id="magnifytool" class="tool-magnify">' + 
						tool_names.magnification.name + '<span title="' + tool_names.magnification.name + '"></span></li>');
				$('#tool-arena').append(new EJS({
					url : 'js/views/tool/magnification.ejs'
				}).render());

				$('#magnify').draggable({
					containment : "window",
					start : function() {
						tool.getOnTop($('#magnify'));
					}
				});

				$('#magnifytool').on('click', function() {
					//$(document).trigger('tracker.tool', ['magnification']);
					$('#magnify').toggle();
					$(this).toggleClass('selected');
					$('#mag-slider a').focus();
					tool.positionTool($('#magnify'));
					if ($('#magnify').is(':visible')) {
						tool.getOnTop($('#magnify'));
					}
				});

				$('#magnify .tool-hdr a.tool-close').on('click', function() {
					$('#magnify').hide();
					$('#magnifytool').removeClass('selected');
				});

				$("#mag-slider").slider({
					step : 25,
					value : magnificationAmount
				});
				$('#mag-slider').on('slide', function(event, ui) {

					var scale = (ui.value / 25) * 2;
					magnificationAmount = (ui.value / 25);
					scaleBody(scale);
				});

				$('#magnify').on('click', function() {
					tool.getOnTop($('#magnify'));
					$('#mag-slider a').focus();
				});
				
				if (magnificationObj && magnificationObj.activatebydefault && magnificationObj.activatebydefault === 'true') {
					magnificationAmount = parseInt(magnificationObj.magnification) - 1;
					var scale = magnificationAmount * 2;
					scaleBody(scale);
				}
		}
	}

	return {
		init : init,
		getMagnificationLevel : getMagLevel
	};

})();
