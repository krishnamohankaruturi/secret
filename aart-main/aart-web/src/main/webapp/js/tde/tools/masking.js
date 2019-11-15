var maskingAccommodation = (function() {

	function init(profile) {
		
		var keyValues = profile['masking'];
		//colourTint
		
		if (keyValues) {
			var attrNamesArray = _.pluck(keyValues, 'attrName');
			var attrValuesArray = _.pluck(keyValues, 'attrValue');
			var makingObject = _.object(attrNamesArray, attrValuesArray);
			
			if (makingObject.assignedsupport && makingObject.assignedsupport === 'true') {
			
				if (makingObject.maskingtype === 'custommask') {
					
					$('#tools-c ul').append('<li id="maskstool" class="tool-masks">' + tool_names.mask.name + '<span title="' + tool_names.mask.name + '"></span></li>');
					$('#tool-arena').append(new EJS({
						url : 'js/views/tool/mask.ejs'
					}).render({}));

					$('#maskstool').on('click', function(e) {
						//$(document).trigger('tracker.tool', ['CustomMask']);
						if ($('.mask').size() <= 10) {
							$('#mask').clone(true).attr('id', 'clonedMask').prependTo('#masks').show().resizable().draggable({
								containment : "window"
							}).find('a.tool-close').bind('click touchend', function() {
								$(this).parent().parent().remove();
							});
						} else {
							test.showAlertBox('maskWarn');
						}
					});

					$('#maskWarnConfirm').click(function() {
						$('#maskWarn').fadeOut("slow");
					});
				}else if (makingObject.maskingtype === 'answermask') {
					//Setting the flag so that it will be used through out the test.
					//$(document).trigger('tracker.tool', ['AnswerMask']);
					task.setMaskResponses(true);
				}
			}
			if (makingObject.activatebydefault && makingObject.activatebydefault === 'true') {
				$('#maskstool').trigger('click');
			}
		}
	}

	return {
		init : init
	};

})();
