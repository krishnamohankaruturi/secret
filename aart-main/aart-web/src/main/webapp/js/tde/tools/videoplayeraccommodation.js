var vplayerAccommodation = (function() {

	function init(profile) {
		var keyValues = profile['signing'];
		
		var attrNamesArray = _.pluck(keyValues, 'attrName');
		var attrValuesArray = _.pluck(keyValues, 'attrValue');

		// Flat to this object..
		var videoPlay = _.object(attrNamesArray, attrValuesArray);

		if (videoPlay.assignedsupport && videoPlay.assignedsupport==='true' && testObj.uiname == 'genTest') {

			if (videoPlay.signingtype === 'asl') {
				$('#tool-arena').append(new EJS({
					url : 'js/views/tool/videoplayer.ejs'
				}).render({}));
				//$(document).trigger('tracker.tool', [ 'asl' ]);
				videoplayer.openPlayer();
			}
			if (videoPlay.activatebydefault === 'false') {

				// $('#contrasttool, #screenContrast .tool-hdr
				// a').trigger('click');
				videoplayer.close();
			}
		}
	}
	return {
		init:init
	};

})();
