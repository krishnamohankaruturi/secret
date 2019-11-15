var videoplayer = new function() {

	this.openPlayer = function() {
		var origPosition = {width:0,height:0,left:0,top:0};
		var saveDivPosition = function(element) {
			origPosition.width = element.width();
			origPosition.height = element.height();
			origPosition.left = element.offset().left;
			origPosition.top = element.offset().top;
		};
		var restoreDiv = function() {
			$('#videoPanel').animate({
					left:origPosition.left,
					top:origPosition.top
				}, 700);
			$('#videoPanel').animate({
					height:origPosition.height,
					width:origPosition.width
				}, 500);
		};
		var sizeMode = "";
		var myVideolist = new jPlayerPlaylist({
				jPlayer : "#jquery_jplayer_video",
				cssSelectorAncestor: "#jp_container_2",
			},[
			  {
				  m4v: '',//"video/Big_Buck_Bunny_Trailer_480x270_h264aac.m4v",
		          ogv: '',//"video/Big_Buck_Bunny_Trailer_480x270.ogv",
		          poster: ''//"video/Big_Buck_Bunny_Trailer_480x270.png"
	       }
			  ],{		
				swfPath: "js/external/jquery.jplayer/Jplayer.swf",
				supplied: "m4v, ogv",
				wmode: "window",
				preload: "auto"
			});
		myVideolist.select(0);
		//myVideolist.play();
		
		this.play = function() {
			myVideolist.play();
		};
		
		this.pause = function() {
			myVideolist.pause();
		};
	  
		$("#videoPanel").resizable({
			minHeight: 350,
			minWidth: 500,
			stop: function(event,ui) {
				if (sizeMode != 'minimized') {
					console.log(ui);
						var newWidth = ui.size.width;
						var newHeight = ui.size.height - $("#videoPanel div.jp-interface").height() - 50;
						$("#jquery_jplayer_video").jPlayer("option", "size", {width: newWidth,height: newHeight});
						$("#jquery_jplayer_video.jp-jplayer").css("background-color", "#FFFFFF");
						$("#videoPanel .jp-interface").css("width", newWidth);
						$("#videoPanel .jp-video-270p").css("width", newWidth);
						if(/ipad/ig.test(userAgent)){
							if(!$(this).data('drag')){
								$(this).data('drag', true);
								$('#videoPanel-minimize').bind('touchend', minimize);
								$('#videoPanel-close').bind('touchend', close);
							}
						}
				}
			}
		});
		$("#videoPanel").draggable({containment : "window",
									helper: "original",
									start : function() {
										tool.getOnTop($('#videoPanel'));
									},
									 stop: function(){
										 if(/ipad/ig.test(userAgent)){
											if(!$(this).data('drag')){
												$(this).data('drag', true);
												$('#videoPanel-minimize').bind('touchend', minimize);
												$('#videoPanel-close').bind('touchend', function(){videoplayer.close();});
											}
										 }
									 }
		});
		
		$('#videoPanel').click(function() {
			tool.getOnTop($('#videoPanel'));
		});
		
		$("#videoPanel .jp-video").css("border", "none");
		
		$('#videoPanel-close').on('click', function(){videoplayer.close();});
		$('#videoPanel-minimize').on('click', function(ev) {ev.preventDefault(); minimize();});
		
		function minimize() {
			if (sizeMode == "") {
				saveDivPosition($('#videoPanel'));
				var newLeft = $('.tde-controls-c').css('left');//$(document).width() - 200;
				var newTop = $('#tde-content').height() - $('.tde-controls-c').height() + 70;//$(document).height() - 30;
				$('#videoPanel').css({});
				$('#videoPanel').animate({width: 200, height: 30},500);
				$('#videoPanel').animate({left:newLeft,top:newTop},700);
				myVideolist.pause();
				sizeMode = "minimized";
			}
			else {
				restoreDiv();
				myVideolist.play();
				sizeMode = "";
			}
		}
		
		 this.close = function(){
			$('#tools-c ul').append('<li id="videotool" class="tool-masks" style="display: none;"> '+ tool_names.videoplayer.name +' <span title="'+tool_names.videoplayer.name +'"></span></li>');
			var origWidth = $('#videoPanel').width();
			var origHeight = $('#videoPanel').height();
			var origLeft = $('#videoPanel').offset().left;
			var origTop = $('#videoPanel').offset().top;
			
			var newLeft = $('#tde-content').css('left');//$('#videotool').css('left');
			var newTop = 55 + 45*$('#tools-c ul').children().length;//$('#videotool').css('top');
			myVideolist.pause();
			$('#videoPanel').animate({width: 200, height: 30},500);
			$('#videoPanel').animate({left: newLeft,top: newTop},1000, function(){
				$('#videoPanel').css({height: 0, width: 0});
				$('#videotool').show();
				if ( Modernizr.touch ) {
					$('#tools-c').data('jsp').reinitialise();
					if($('#tools-c').has('.jspVerticalBar').length > 0){
						$('#tools-c ul li').css({ "margin-right": "4px" });
					} else {
						$('#tools-c ul li').css({ "margin-right": "26px" });
					}
				} else {
					if($('#tools-c')[0].scrollHeight > $('#tools-c')[0].clientHeight){
						$('#tools-c ul li').css({ "margin-right": "10px" });
					} else {
						$('#tools-c ul li').css({ "margin-right": "26px" });
					}
				}
			});
			$('#videotool').on('click',function(){
				$('#videoPanel').css({height: 30, width: 200});
				$('#videoPanel').animate({
					left:origLeft,
					top:origTop
				}, 700);
				$('#videoPanel').animate({
					height:origHeight,
					width:origWidth
				}, 500);
				$(this).remove();
				if ( Modernizr.touch ) {
					$('#tools-c').data('jsp').reinitialise();
					if($('#tools-c').has('.jspVerticalBar').length > 0){
						$('#tools-c ul li').css({ "margin-right": "4px" });
					} else {
						$('#tools-c ul li').css({ "margin-right": "26px" });
					}
				}
			});
		};
	};
};