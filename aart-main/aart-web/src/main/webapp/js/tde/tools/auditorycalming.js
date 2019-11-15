var auditoryCalmingAccommodation = (function() {

	function init(profile) {
		var keyValues = profile['auditorybackground'];
		
		if (keyValues) {
			var attrNamesArray = _.pluck(keyValues, 'attrName');
			var attrValuesArray = _.pluck(keyValues, 'attrValue');
			//Flat to this object..
			var auditoryCalming = _.object(attrNamesArray, attrValuesArray);

			if (auditoryCalming.assignedsupport && auditoryCalming.assignedsupport === 'true') {

				$('#tools-c ul').append('<li id="audiotool" class="tool-calming">' + tool_names.auditoryCalming.name + '<span title="' + tool_names.auditoryCalming.name + '"></span></li>');
				$('#tool-arena').append(new EJS({
					url : 'js/views/tool/auditoryCalming.ejs'
				}).render({}));

				$('#auditoryCalming').draggable({
					containment : "window",
					start : function() {
						tool.getOnTop($('#auditoryCalming'));
					}
				});

				var myPlaylist = new jPlayerPlaylist({
					jPlayer : '#auditoryCalmingPlayer',
					cssSelectorAncestor : '#auditoryCalmingPlayer_container'
				}, [{
					title : "Airport Lounge",
					mp3 : tde.config.mediaUrl + "/TDE/audio/Airport_Lounge.mp3",
					oga : tde.config.mediaUrl + "/TDE/audio/Airport_Lounge.ogg"
				}, {
					title : "Ghostpocalypse",
					mp3 : tde.config.mediaUrl + "/TDE/audio/Ghostpocalypse-8Epilog.mp3",
					oga : tde.config.mediaUrl + "/TDE/audio/Ghostpocalypse-8Epilog.ogg"
				}, {
					title : "Prelude in C",
					mp3 : tde.config.mediaUrl + "/TDE/audio/PreludeinC-BWV_846.mp3",
					oga : tde.config.mediaUrl + "/TDE/audio/PreludeinC-BWV_846.ogg"
				}, {
					title : "Silver Blue Light",
					mp3 : tde.config.mediaUrl + "/TDE/audio/Silver_Blue_Light.mp3",
					oga : tde.config.mediaUrl + "/TDE/audio/Silver_Blue_Light.ogg"
				}, {
					title : "Somewhere Sunny",
					mp3 : tde.config.mediaUrl + "/TDE/audio/Somewhere_Sunny_ver_2.mp3",
					oga : tde.config.mediaUrl + "/TDE/audio/Somewhere_Sunny_ver_2.ogg"
				}, {
					title : "Tea Roots",
					mp3 : tde.config.mediaUrl + "/TDE/audio/Tea_Roots.mp3",
					oga : tde.config.mediaUrl + "/TDE/audio/Tea_Roots.ogg"
				}], {
					swfPath : "js/external/jquery.jplayer/Jplayer.swf",
					supplied : "mp3, oga",
					wmode : "window",
					preload : "auto",
					loop : true
				});

				$('#audiotool').on('click', function() {
					//$(document).trigger('tracker.tool', ['auditory_calming']);
					var auditoryCalming = $('#auditoryCalming');
					auditoryCalming.toggle();
					if (auditoryCalming.css('display') == 'none') {
						myPlaylist.pause();
						$(this).removeClass('selected');
					} else {
						myPlaylist.play();
						$(this).addClass('selected');
						tool.positionTool($('#auditoryCalming'));
					}
				});

				$('#auditoryCalming .tool-hdr a.tool-close').on('click', function() {
					$('#auditoryCalming').hide();
					$('#audiotool').removeClass('selected');
					myPlaylist.pause();
				});

				$('.change-title').toggle(function() {
					var pDiv = $('.jp-playlist ul').height();
					$("div.jp-playlist").animate({
						"height" : pDiv,
						paddingBottom : "10px"
					}, 666);
				}, function() {
					$('div.jp-playlist').stop().animate({
						height : 0,
						paddingBottom : "0"
					}, 666);
				});

				//Code for Auto Active Value.
				if (auditoryCalming.activatebydefault && auditoryCalming.activatebydefault === 'true') {
					$('#audiotool').trigger('click');
				}
				
				$('#auditoryCalming').click(function() {
					tool.getOnTop($('#auditoryCalming'));
				});

			}
		}

	}

	return {
		init : init
	};

})();
