var ttsAccommodation = (function() {
	
	var ttsPlayList;
	var ttsPassagePlayList;
	var readaloudtype;
	
	function init(profile) {
		
		var keyValues = profile['spoken'];
		
		if (keyValues) {
			var attrNamesArray = _.pluck(keyValues, 'attrName');
			var attrValuesArray = _.pluck(keyValues, 'attrValue');
			
			//Flat to this object..
			var ttsObj = _.object(attrNamesArray, attrValuesArray);
			
			if (ttsObj.assignedsupport && ttsObj.assignedsupport==='true' && ttsObj.directionsonly && ttsObj.directionsonly === 'false') {
				if (testObj.uiname == 'altTest') {
					/*$('.nr-buttons .cdiv').append('<a class="btn red-btn tde-controls-ttsPlay tabable play" tabindex="102" href="#"><img src="/images/signout-icon.png">Play</a>'+
												  '<div id="ttsAudio" class="jp-jplayer" style="display:none;"></div>');*/
					$('.nr-buttons .cdiv').append('<div class="cdiv-inner tde-controls-ttsPlay tabable play"><a class="" tabindex="102" href="#"><img src="/images/tts-read.png"></a></div>');
					$('.nr-buttons .cdiv').append('<div id="ttsAudio" class="jp-jplayer"></div>');
				} else if(testObj.uiname == 'genTest') {
					$('#tools-c ul').append('<li id="ttstool" class="tool-tts">'+tool_names.readAloud.name+'<span title="'+tool_names.readAloud.name+'"></span></li>');
					
					//$('#tool-arena').append(new EJS({url : 'js/views/tool/tts.ejs'}).render({}));
					$('#tool-arena').append($('#textToSpeech'));
					$('#tool-arena').append($('#ttsPassageAudio'));
					
					$('#textToSpeech').draggable({
						containment : '#tde-content',
						start : function() {
							tool.getOnTop($('#textToSpeech'));
						}
					});
					
					$('#ttstool, #textToSpeech .tool-close').on('click', function() {
						if($('#textToSpeech').is(':visible')){
							tagHighlighter.unhighlight();
							$('#textToSpeech .ttsPlay').removeClass('pause').addClass('play');
							ttsPlayList.pause();
							ttsPlayList.setPaused(false);
							ttsPlayList.select(0);
							$('#ttstool').removeClass('selected');
							$('#textToSpeech').hide();
						} else {
							$('#textToSpeech').show();
							$('#ttstool').addClass('selected');
							tool.positionTool($('#textToSpeech'));
							tool.getOnTop($('#textToSpeech'));
						}
					});
					
					$('#textToSpeech .player-next').on('click', function() {
						tagHighlighter.unhighlight();
						$('#textToSpeech .ttsPlay').removeClass('play').addClass('pause');
						ttsPlayList.setPaused(false);
						ttsPlayList.next();
					});
					$('#textToSpeech .player-prev').on('click', function() {
						ttsPlayList.select(ttsPlayList.current);
						tagHighlighter.unhighlight();
						if(ttsPlayList.current == 0) {
							$('#textToSpeech .ttsPlay').removeClass('pause').addClass('play');
						} else {
							$('#textToSpeech .ttsPlay').removeClass('play').addClass('pause');
						}
						
						ttsPlayList.setPaused(false);
						ttsPlayList.previous();
					});
					
					
					
					if ( Modernizr.touch ) {
						$('#tools-c').jScrollPane();
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
					
				}
					
					ttsPlayList = new jPlayerPlaylist(
						{
							jPlayer: '#ttsAudio',
							cssSelectorAncestor : '#ttsAudio_container'
						},
						[],
						{
							supplied: "oga, mp3",
							loop: false
						});
					
					ttsPassagePlayList = new jPlayerPlaylist(
							{
								jPlayer: '#ttsPassageAudio',
								cssSelectorAncestor : '#ttsPassageAudio_container'
							},
							[],
							{
								supplied: "oga, mp3",
								loop: false
							});
					tde.config.ttsrefresh = true;
				//}
				readaloudtype = ttsObj.userspokenpreference.replace(" ","").toLowerCase();
			}
			
		}
	}
	
	function refresh() {
		
		ttsPlayList.pause();
		ttsPlayList.remove();
		ttsPlayList.setPaused(false);
		
		ttsPassagePlayList.pause();
		ttsPassagePlayList.remove();
		ttsPassagePlayList.setPaused(false);
		
		$('.tde-controls-ttsPlay, #ttstool, #tts-passage').show();
		$('.tde-controls-ttsPlay, #textToSpeech .ttsPlay, #tts-passage .ttsPassagePlay').off('click');
		$('.tde-controls-ttsPlay img').attr('src', '/images/tts-read.png');
		$('.tde-controls-ttsPlay, #textToSpeech .ttsPlay, #tts-passage .ttsPassagePlay').removeClass('pause').addClass('play');
		
		var currentSection = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]];
		
		var testletPage = false;
		var testletStory = task.getTestletStory();
		if(testletStory.is) {
			if(testletStory.onTestlet) {
				testletPage = true;
				var testlet = currentSection.testlets[currentSection['testletId_'+tasks[tde.testparam.currentQuestion].testletId]];
				
				var groupCounts = 0;
				var orderedKeys = _.sortBy(_.keys(testlet.groups).map(function(groupNo){
				    													return parseInt(groupNo);
																  }), function(num) {
															return num;
											});
				
				var lens = new Array();
				for(var i=0; i<orderedKeys.length; i++) {
					lens.push(testlet.groups[orderedKeys[i]].totalLength);
				}
				
				for(var i=0; i<lens.length; i++) {
					groupCounts += lens[i];
					if(testletStory.currentScreen < groupCounts) {
						groupCounts -= lens[i];
						break;
					}
				}
				
				var pageCounts = _.pluck(testlet.groups[testletStory.groupNumber], 'pageCount');
				var checkPage = 0;
				var stim = 0;
				for(var i=0; i<pageCounts.length; i++) {
					checkPage += pageCounts[i];
					if((testletStory.currentScreen - groupCounts) < checkPage) {
						stim = i;
						checkPage -= pageCounts[i];
						break;
					}
				}
				var pageNum = testletStory.currentScreen - groupCounts - checkPage;
				
				var testletContentGroups = _.filter(_.sortBy(_.filter(testlet.groups[parseInt(testletStory.groupNumber)][stim].contentGroups, function(contentGroup) {
														return contentGroup.readAloudAccommodation != null && contentGroup.readAloudAccommodation.readAloudType.replace(" ","").toLowerCase() == readaloudtype;
													}), function(contentGroup) {
												return contentGroup.readAloudAccommodation.defaultOrder;
											}), function(contentGroup){
													if(contentGroup.htmlElementId != null && contentGroup.htmlElementId != '.passage') {
														var elementId = contentGroup.htmlElementId;
														if(elementId.substring(1,0) == '#') {
													       elementId = elementId.substring(1);
														}
														var re = new RegExp('id="'+elementId);
														//if(re.exec($('.passage').html())) {
														if(re.exec(testlet.groups[parseInt(testletStory.groupNumber)][stim].pages[pageNum])) {
															return contentGroup;
														}
													} else if(contentGroup.htmlElementId == null || contentGroup.htmlElementId == '.passage') {
														
														if(contentGroup.compositeMediaId != null) {
															var re = new RegExp('mediavariantid="'+contentGroup.compositeMediaId);
															if(re.exec(testlet.groups[parseInt(testletStory.groupNumber)][stim].pages[pageNum])) {
																return contentGroup;
															}
														} else if(contentGroup.charIndexEnd != null && contentGroup.charIndexStart != null) {
															var startTextLength = 0;
															var endTextLength = 0;
															
															for(var i=0; i<=pageNum; i++) {
																//get lentgth b4 this page...check start and end charindex range to be within that page
																endTextLength += $('<div>'+testlet.groups[parseInt(testletStory.groupNumber)][stim].pages[i]+'</div>').text().length;
															}
															startTextLength = endTextLength - $('<div>'+testlet.groups[parseInt(testletStory.groupNumber)][stim].pages[pageNum]+'</div>').text().length;
															
															if(contentGroup.charIndexEnd <= endTextLength && contentGroup.charIndexStart >= startTextLength) {
																return contentGroup;
															}
														}
														
													}
											}); 
				
				//console.log($('.passage').text());
				
				for(var cg in testletContentGroups) {
					if(testletContentGroups[cg].readAloudAccommodation != null) {
						var floc = testletContentGroups[cg].readAloudAccommodation.accessibilityFile.fileLocation;
						testletContentGroups[cg]['grouptype'] = 'passage';
						if(testletContentGroups[cg].htmlElementId == null && testletContentGroups[cg].compositeMediaId == null) {
							testletContentGroups[cg].htmlElementId = '.passage';
						}else if(testletContentGroups[cg].htmlElementId != null) {
							if(testletContentGroups[cg].htmlElementId.substring(1,0) != '#' && testletContentGroups[cg].htmlElementId.substring(1,0) != '.' ) {
								testletContentGroups[cg].htmlElementId = '#'+testletContentGroups[cg].htmlElementId;
							}
						}
						ttsPlayList.add({
							title: "text to speech",
							mp3: tde.config.mediaUrl + floc.replace('.ogg','.mp3'),
							oga: tde.config.mediaUrl + floc,
							contentGroup : testletContentGroups[cg]
						});
					}
				}
			}
		}
		
		if((!testletPage /*&& testletStory.is) || (!testletPage && !tasks[tde.testparam.currentQuestion].testlet*/)) {
			var taskContentGroups = _.map(_.filter(tasks[tde.testparam.currentQuestion].contentGroups, function(contentGroup) {
												return contentGroup.readAloudAccommodation != null && contentGroup.readAloudAccommodation.readAloudType.replace(" ","").toLowerCase() == readaloudtype;
										  }), function(contentGroup) {
												if((contentGroup.htmlElementId == null || /.q-question:eq/.test(contentGroup.htmlElementId)) && contentGroup.compositeMediaId == null && contentGroup.stimulusVariantId == null) {
													
													if(testletStory.is || !tasks[tde.testparam.currentQuestion].testlet) {
														contentGroup.htmlElementId = '.taskstem';
													} else {
														if($('#view_one').hasClass('activePassage')) {
															contentGroup.htmlElementId = '.q-question:eq(0)';
														} else {
															contentGroup.htmlElementId = '.q-question:eq('+tde.testparam.taskgroups[task.getCurrentGroup()].indexOf(''+tde.testparam.currentQuestion)+')';
														}
													}
												} else if(contentGroup.htmlElementId != null) {
													if(contentGroup.htmlElementId.substring(1,0) != '#' && contentGroup.htmlElementId.substring(1,0) != '.' ) {
														contentGroup.htmlElementId = '#'+contentGroup.htmlElementId;
													}
												}
												contentGroup['grouptype'] = 'task';
												return contentGroup;
									});
			
			var foilContentGroups = _.pluck(tasks[tde.testparam.currentQuestion].foils, 'contentGroups');
			
			for(var fcg in foilContentGroups) {
				for(var cg in foilContentGroups[fcg]) {
					if(foilContentGroups[fcg][cg].readAloudAccommodation != null) {
						
						if((foilContentGroups[fcg][cg].htmlElementId == null || /.foil:eq/.test(foilContentGroups[fcg][cg].htmlElementId)) && foilContentGroups[fcg][cg].compositeMediaId == null && foilContentGroups[fcg][cg].stimulusVariantId == null) {
							if(testletStory.is || !tasks[tde.testparam.currentQuestion].testlet || $('#view_one').hasClass('activePassage')) {
								foilContentGroups[fcg][cg].htmlElementId = '.foil:eq('+fcg+')';
							} else {
								var foilCount = 0;
								var taskGroup = tde.testparam.taskgroups[task.getCurrentGroup()];
								var position = taskGroup.indexOf(''+tde.testparam.currentQuestion);
								for(var i=0; i< position; i++) {
									foilCount += tasks[parseInt(taskGroup[i])].foils.length;
								}
								foilCount += parseInt(fcg);
								foilContentGroups[fcg][cg].htmlElementId = '.foil:eq('+foilCount+')';
							}
							
						} else if(foilContentGroups[fcg][cg].htmlElementId != null) {
							if(foilContentGroups[fcg][cg].htmlElementId.substring(1,0) != '#' && foilContentGroups[fcg][cg].htmlElementId.substring(1,0) != '.' ) {
								foilContentGroups[fcg][cg].htmlElementId = '#'+foilContentGroups[fcg][cg].htmlElementId;
							}	
						}
						foilContentGroups[fcg][cg]['grouptype'] = 'foil:eq('+fcg+')';
					}
				}
			}
			
			foilContentGroups = _.filter(_.reduceRight(foilContentGroups, function(a,b) {
											return a.concat(b);
										}), function(contentGroup) {
											return contentGroup.readAloudAccommodation != null && contentGroup.readAloudAccommodation.readAloudType.replace(" ","").toLowerCase() == readaloudtype;
								});
			
			var contentGroups = _.sortBy(taskContentGroups.concat(foilContentGroups), function(contentGroup) {
									return contentGroup.readAloudAccommodation.defaultOrder;
								});
			
			var firstFoil = true;
			for(var cg in contentGroups) {
				if(contentGroups[cg].readAloudAccommodation != null) {
					//Add delay between the foils -- (altUi === first foil = 2s and rest = 1.5s) , (genUi === first foil = 0.8s and rest = 0.4s)
					if(firstFoil && /foil/.test(contentGroups[cg].grouptype)) {
						var delay = (testObj.uiname == 'genTest' ? 400 : 2000);
						firstFoil = false;
						contentGroups[cg].readAloudAccommodation['delay'] = delay;//2000;
					} else if(!firstFoil && /foil/.test(contentGroups[cg].grouptype)) {
						var delay = (testObj.uiname == 'genTest' ? 400 : 1500);
						contentGroups[cg].readAloudAccommodation['delay'] = delay;//1500;
					}
					
					var floc = contentGroups[cg].readAloudAccommodation.accessibilityFile.fileLocation;
					ttsPlayList.add({
						title: "text to speech",
						mp3: tde.config.mediaUrl + floc.replace('.ogg','.mp3'),
						oga: tde.config.mediaUrl + floc,
						contentGroup : contentGroups[cg]
					});
				}
			}
			
			if(tasks[tde.testparam.currentQuestion].testlet && ($('.passage').is(':visible') && !$('.test-questions').is(':visible'))) {
				ttsPlayList.remove();
			}
		}
		
		if(!testletPage && tasks[tde.testparam.currentQuestion].testlet && $('.passage').is(':visible')) {
			var stimulusContentGroups = _.pluck(_.sortBy(currentSection.testlets[currentSection['testletId_'+tasks[tde.testparam.currentQuestion].testletId]].stimuli, function(stim) {
				return stim.sortOrder;
			}),'contentGroups');
			var ttsContentGroups = new Array();
			$.each(stimulusContentGroups, function() {

					var testletContentGroups = _.map(_.sortBy(_.filter(this, function(contentGroup) {
						return contentGroup.readAloudAccommodation != null && contentGroup.readAloudAccommodation.readAloudType.replace(" ","").toLowerCase() == readaloudtype;
					}), function(contentGroup) {
						return contentGroup.readAloudAccommodation.defaultOrder;
					}), function(contentGroup) {
					if(contentGroup.htmlElementId == null && contentGroup.compositeMediaId == null) {
						contentGroup.htmlElementId = '.passage';
					} else if(contentGroup.htmlElementId != null) {
						if(contentGroup.htmlElementId.substring(1,0) != '#' && contentGroup.htmlElementId.substring(1,0) != '.' ) {
							contentGroup.htmlElementId = '#'+contentGroup.htmlElementId;
						}
					}
						contentGroup['grouptype'] = 'passage';
						return contentGroup;
					});

				ttsContentGroups.push(testletContentGroups);
			});
			ttsContentGroups = _.flatten(ttsContentGroups);
			
			//Add all contentgroups to the playlist
			for(var cg in ttsContentGroups) {
				//if(ttsContentGroups[cg].readAloudAccommodation != null) {
					var floc = ttsContentGroups[cg].readAloudAccommodation.accessibilityFile.fileLocation;
					ttsPassagePlayList.add({
						title: "text to speech - passage",
						mp3: tde.config.mediaUrl + floc.replace('.ogg','.mp3'),
						oga: tde.config.mediaUrl + floc,
						contentGroup : ttsContentGroups[cg]
					});
				//}
			}
		}
		
		if(_.isEmpty(ttsPassagePlayList.playlist)) {
			$('#tts-passage').hide();
		}
		
		if(_.isEmpty(ttsPlayList.playlist)) {
			$('.tde-controls-ttsPlay').hide();
			$('#textToSpeech').hide();
			$('#ttstool').hide();
		}
		
		$('.tde-controls-ttsPlay, #textToSpeech .ttsPlay, #tts-passage .ttsPassagePlay').on('click', function() {
			if($(this).hasClass('play')) {
				console.log($(this));
				tabScan.stopActionAudio();
				tabScan.stop();
				$(this).removeClass('play').addClass('pause');
				//$(this).text('pause');
				$('.tde-controls-ttsPlay img').attr('src', '/images/tts-pause.png');
				if(!$(this).hasClass('ttsPassagePlay')) {
					ttsPlayList.play();
					ttsPlayList.setPaused(false);
				} else {
					ttsPassagePlayList.play();
					ttsPassagePlayList.setPaused(false);
				}
			} else if($(this).hasClass('pause')) {
				$(this).removeClass('pause').addClass('play');
				if(!$(this).hasClass('ttsPassagePlay')) {
					ttsPlayList.pause();
					ttsPlayList.setPaused(true);
				} else {
					ttsPassagePlayList.pause();
					ttsPassagePlayList.setPaused(true);
				}
				$('.tde-controls-ttsPlay img').attr('src', '/images/tts-read.png');
			}
			
		});
		
		$('#tts-passage .player-next').on('click', function() {
			tagHighlighter.unhighlight();
			$('#tts-passage .ttsPassagePlay').removeClass('play').addClass('pause');
			ttsPassagePlayList.setPaused(false);
			ttsPassagePlayList.next();
		});
		
		$('#tts-passage .player-prev').on('click', function() {
			ttsPassagePlayList.select(ttsPassagePlayList.current);
			tagHighlighter.unhighlight();
			if(ttsPassagePlayList.current == 0) {
				$('#tts-passage .ttsPassagePlay').removeClass('pause').addClass('play');
			} else {
				$('#tts-passage .ttsPassagePlay').removeClass('play').addClass('pause');
			}
			
			ttsPassagePlayList.setPaused(false);
			ttsPassagePlayList.previous();
		});
		
	}
	
	function pause() {
		if(ttsPlayList) {
			$('#textToSpeech .ttsPlay').removeClass('pause').addClass('play');
			ttsPlayList.pause();
			ttsPlayList.setPaused(true);
		}
		if(ttsPassagePlayList) {
			$('#tts-passage .ttsPassagePlay').removeClass('pause').addClass('play');
			ttsPassagePlayList.pause();
			ttsPassagePlayList.setPaused(true);
		}
	}
	
	function openScrollPassageQuestion(htmlElementId) {
		if((/.q-question:eq\(/.test(htmlElementId) || ($(htmlElementId).parents('.q-question') || $(htmlElementId).hasClass('q-question'))) && task.getTestletQuestionView()) {
			var elem = $(htmlElementId);
			elem = (elem.hasClass('q-question')) ? elem : elem.parents('.q-question');
			if(!elem.siblings('.foils').children('ul').is(':visible')) {
				elem.siblings('.foils').children('ul').show();
			}
			var div= $('.test-questions .all-qWrap');
			if ( Modernizr.touch && div.data('jsp')) {
				div.data('jsp').scrollToY(elem.parent().offset().top - (div.offset().top - div.scrollTop())-3);
			} else {
				if(div.prop('scrollHeight') > div.height()) {
					div.scrollTop((elem.parent().offset().top - (div.offset().top - div.scrollTop())-3));				
				}
			}
		}
	}
	
	return {
		init : init,
		refresh : refresh,
		pause : pause,
		openScrollPassageQuestion : openScrollPassageQuestion
	};

	
})();