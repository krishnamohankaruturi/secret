var tabScan = (function() {
	var scanCount = 0;
	
	var parentCycle = true;
	var childCycleCount = 0, maxChildCycleCount = 3;
	
	var selectorIndex = 0, selectedElem = null;
	var childSelectorIndex = 0, tabbedChildElem = null, tabbedChildsParent = null;
	var actionPlayList;// = null;
	var spokenPref = null;
	
	function startScan() {
		task.scanRunning = true;

		if(task.scanIntervalFunctionID) {
			clearInterval(task.scanIntervalFunctionID);
		}
		
		scanStep();
		task.scanIntervalFunctionID = setInterval( scanStep, task.scanProperties.scanspeed*1000 );
	};
	
	function scanStep() {
		if ( scanCount >= task.scanProperties.automaticscanrepeat || (tasktype == 'ITP' && !parentCycle && (childCycleCount > maxChildCycleCount-1) )) {
			stopScan();
			reset();
			return;
		}
		
		tab();
	}	
	
	function stopScan() {
		clearInterval(task.scanIntervalFunctionID);
		clearTimeout(task.startScanTimeoutID);
		$('.mark_highlight').removeClass('mark_highlight');
		$('.markhighlight_te_parent').removeClass('markhighlight_te_parent');
		$('.markhighlight_te_child').removeClass('markhighlight_te_child');
		task.scanRunning  = false;
		task.resetScan = true;
	};
	
	function bindBodyTabEnterKey() {
		var bodyKeyPress = $('body').data('events')['keypress'];
		var tabenter = undefined;
		// keycode = 9 -- tab
		// keycode = 13 -- enter
		if(bodyKeyPress) {
			tabenter = _.findWhere(bodyKeyPress, {selector : null});
		}
		if(tabenter == undefined) {
			$('body').keypress(function(event){
				if ( task.audioPlaying || ($('.overlay-content').is(':visible') && !$('#ticketConfirmation .overlay-content').is(':visible'))) { /* Check to see if audio is playing & also block keys when any popUp is open */
					event.preventDefault();
					return;
				}
				if(!task.scanRunning && ( event.shiftKey && event.keyCode == 9 ) ) {
					event.preventDefault();
					shiftTab();
				} else if(!task.scanRunning && event.keyCode == 9) {
					event.preventDefault();
					tab();
				} else if(event.keyCode == 13) {
					enter(event);
				}
			});
			
			actionPlayList = new jPlayerPlaylist(
					{
						jPlayer: '#actionAudio'
					},
					[],
					{
						supplied: "oga, mp3",
						loop: false
					});
			
			var spokenPrefObj = _.findWhere($.parseJSON(sessionStorage.getItem('profile'))['spoken'],{attrName: 'userspokenpreference'});
			if(spokenPrefObj) {
				spokenPref = spokenPrefObj['attrValue'];
			}
			
			$('#actionAudio').on($.jPlayer.event.ended, function() {
				if(task.scanRunning) {
					/*if(tasks[tde.testparam.currentQuestion].taskType != 'ITP') {
						//task.scanIntervalFunctionID = setInterval( task.scanStep, task.scanProperties.scanspeed*1000 );
						task.scanIntervalFunctionID = setInterval( scanStep, task.scanProperties.scanspeed*1000 );
					} else {
						task.scanIntervalFunctionID = setInterval( scanStep, task.scanProperties.scanspeed*1000 );
					}*/
					task.scanIntervalFunctionID = setInterval( scanStep, task.scanProperties.scanspeed*1000 );
				}
			});
			
			$('#actionAudio').on($.jPlayer.event.playing, function() {
				if(task.scanIntervalFunctionID) {
					clearInterval(task.scanIntervalFunctionID);
				}
			 });
		}
	}
	
	function tab() {
		tabFocus('tab');
		magnifyingGlass.resetMagnifyingGlass();
	}
	
	function shiftTab() {
		tabFocus('shiftTab');
		magnifyingGlass.resetMagnifyingGlass();
	}
	
	function enter(event) {
		if ( task.audioPlaying ) { /* Check to see if audio is playing */ 
			event.preventDefault();
			return;
		} else if ( task.scanProperties && task.scanProperties.assignedsupport == 'true' && !task.scanRunning && !$('.tde-controls-ttsPlay').hasClass('mark_highlight')) {
			event.preventDefault();
			/*if(tasktype == 'ITP'){
				startScan();
			} else {
				//task.startScan();
				startScan();
			}*/
			startScan();
		} else if ( $('.tde-controls-ttsPlay') && $('.tde-controls-ttsPlay').hasClass('mark_highlight') ) {
			 task.audioPlaying = true;
			 event.preventDefault();
			 /*if(tasktype == 'ITP') {
				 stopScan();
			 } else {
				 //task.stopScan();
				 stopScan();
			 }*/
			 stopScan();
			 if($('#ttsAudio').data('events').jPlayer_ended.length == 1) {
				 $('#ttsAudio').on($.jPlayer.event.ended, function() {
					 task.audioPlaying = false;
					 task.audioAlreadyPlayed = true;
				 });
				 $('#ttsAudio').on($.jPlayer.event.playing, function() {
					 task.audioPlaying = true;
					 task.audioAlreadyPlayed = false;
				 });
			 }
			 $('.tde-controls-ttsPlay').trigger("click");
			 
		} else if ( task.scanProperties && task.scanProperties.assignedsupport && task.scanRunning && selectedElem!= null) {
			/*if(tasktype == 'ITP') {
				event.preventDefault();
				selectTabbed();
				//stopScan();
			} else {
				//task.selectHighlighted();
				event.preventDefault();
				selectTabbed();
			}*/
			event.preventDefault();
			selectTabbed();
		} else {
			if(selectedElem != null) {
				event.preventDefault();
				selectTabbed();
			}
		}
		
	}
	
	function selectTabbed() {
		if(selectedElem.hasClass('foil') || selectedElem.hasClassRegEx('tde-controls-')) {
			selectedElem.click();
		} else {
			
			if(parentCycle) {
				selectedElem.click();
				if(!tabbedChildElem) {
					parentCycle = false;
				} else {
					var clickable = true;
					if(!selectedElem.data('events')) {
						clickable = false;
					} else if(!selectedElem.data('events')['click']) {
						clickable = false;
					}
					
					if($(selectedElem).is(tabbedChildsParent) || !clickable) {
						parentCycle = false;
					}
					tabbedChildElem = null;
					tabbedChildsParent = null;
					
					if(task.scanRunning && clickable) {
						stopScan();
						reset();
					}

				}
				childSelectorIndex = 0;
				childCycleCount = 0;
			} else {
				var child = tabbedChildElem;
				tabbedChildElem.click();
				tabbedChildElem.removeClass('markhighlight_te_child');
				parentCycle = true;
				childSelectorIndex = 0;
				childSelected = true;
				tabbedChildsParent = selectedElem;
				selectorIndex = 0;
				if(task.scanRunning) {
					stopScan();
					reset();
					tabbedChildElem = child;
				}
			}
		}
	};
	
	function tabFocus(key) {
		stopActionPlayList();
		var selector = getSelector(key);
		
		if(tasktype == 'ITP' /*&& (task.getTestletStory().is && !task.getTestletStory().onTestlet)*/) {
		if(parentCycle) {
			$('.markhighlight_te_parent').removeClass('markhighlight_te_parent');
			
			//selector[selectorIndex].addClass('mark_highlight');
			selector[selectorIndex].addClass('markhighlight_te_parent');
			selector[selectorIndex].focus();
			selectedElem = selector[selectorIndex];
			selectorIndex++;
			
			if(selectorIndex >= selector.length) {
				selectorIndex = 0;
				if(task.scanRunning) {
					scanCount++;
				}
			}
			
		} else {
			/*$(selector).filter('.mark_highlight').each(function(){
				$(this).removeClass('mark_highlight');
			});*/
			$(selector).filter('.markhighlight_te_child').removeClass('markhighlight_te_child');
			
			if($(selector).eq(childSelectorIndex)) {
				//$(selector).eq(childSelectorIndex).addClass('mark_highlight');
				$(selector).eq(childSelectorIndex).addClass('markhighlight_te_child');
				$(selector).eq(childSelectorIndex).focus();

				// form play list here..filter from the contentGroup based on the id of the element -- if student profile has readAloud
				if($('.tde-controls-ttsPlay').is(':visible') && testObj.uiname != 'genTest') {
					var audios = _.map(_.sortBy(_.filter(tasks[tde.testparam.currentQuestion].contentGroups, function(contentGroup) {
													if(contentGroup.readAloudAccommodation != null && contentGroup.readAloudAccommodation.readAloudType.replace(" ","").toLowerCase() == spokenPref) {
														if(contentGroup.htmlElementId == '#'+$(selector).eq(childSelectorIndex).attr('id')) {
															return contentGroup;
														} else if(contentGroup.htmlElementId == '#'+$(selector).eq(childSelectorIndex).parent().attr('id')) {
															var parenttext = $(selector).eq(childSelectorIndex).parent().text().substring(contentGroup.charIndexStart, contentGroup.charIndexEnd);
															var childtext = new RegExp($(selector).eq(childSelectorIndex).text());
															if(childtext.test(parenttext)) {
																return contentGroup;
															}
														} else if(selector.eq(childSelectorIndex).find(contentGroup.htmlElementId).length > 0) {
    														  return contentGroup;
														}
													}
												}), function(contentGroup) {
													return contentGroup.readAloudAccommodation.defaultOrder;
										}), function(contentGroup) {
											return contentGroup.readAloudAccommodation.accessibilityFile.fileLocation;
								});
					if(audios && audios.length > 0) {
						refreshActionPlayList(audios);
						actionPlayList.play();
					}
				}
				
				tabbedChildElem = $(selector).eq(childSelectorIndex);
				childSelectorIndex++;
			}
			
			if(childSelectorIndex >= $(selector).length) {
				childSelectorIndex = 0;
				childCycleCount++;
			}
		}
		} else {
			//$(selector).filter('.mark_highlight').removeClass('mark_highlight');
			$('.mark_highlight').removeClass('mark_highlight');
			
			var selectorElem = selector[selectorIndex];//$(selector).eq(selectorIndex);
			
			if(selectorElem) {
				selectorElem.addClass('mark_highlight');
				selectorElem.focus();
				
				if(selectorElem.attr('data-audio') && selectorElem.attr('data-audio') != '[]' && testObj.uiname != 'genTest') {
					refreshActionPlayList($.parseJSON(selectorElem.attr('data-audio')));
					actionPlayList.play();
				}
				
				if(selectorElem.is('textarea')) {
					CKEDITOR.instances[selectorElem.attr('id')].focus();
				}
				selectedElem = selectorElem;
				selectorIndex++;
			}
			
			if(selectorIndex >= selector.length) {
				selectorIndex = 0;
			}
		}
		
	}
	
	function getSelector(key) {
		var selector = null;
		//if(key == 'tab') {
			if(tasktype == 'ITP' /*&& (task.getTestletStory().is && !task.getTestletStory().onTestlet)*/) {
				if(parentCycle) {
					selector = new Array();
					if($('.tde-controls-ttsPlay').is(':visible')) {
						selector.push($('.tde-controls-ttsPlay'));
					}
					$('#tde-content .tabable:not(.magnified-content .tabable):visible').filter('.tabable-container').each(function() {
						selector.push($(this));
					});
					if(testObj.uiname == 'genTest') {
						$('.tde-controls .tabable:not(.magnified-content):visible').each(function() {
							selector.push($(this));
						});
					} else {
						selector.push($('.tde-controls-back'));
						selector.push($('.tde-controls-next'));
					}
					//selector = $('.footer .tabable:not(.tde-controls-back):visible').add($('#tde-content .tabable:not(.magnified-content .tabable):visible').filter('.tabable-container'));
					
					if(key == 'shiftTab') {
						selector.reverse();
					}
					
				} else {
					if(childCycleCount < maxChildCycleCount) {
						selector = selectedElem.find('.tabable:not(.magnified-content .tabable):visible');
						
						if(key == 'shiftTab') {
							selector = selector.get().reverse();
						}
						
					} else {
						parentCycle = true;
						selectedElem.find('.markhighlight_te_child').removeClass('markhighlight_te_child');
						tabbedChildElem = null;
						selector = getSelector(key);
					}
				}
			} else {
				selector = new Array();
				if(testObj.uiname == 'genTest') {
					//selector = $('.tabable:not(.magnified-content):visible, textarea.ckeditor'); //$('.tabable:not(.magnified-content .tabable):visible');//$('.foil:visible');
					$('textarea.ckeditor').each(function() {
						if($(this).parent().is(':visible')) {
							selector.push($(this));
						}
					});
					
					$('#tde-content .tabable:not(.magnified-content):visible, .tde-controls .tabable:not(.magnified-content):visible').each(function() {
						selector.push($(this));
					});
					
				} else {
					//selector = $('.tabable:not(.magnified-content):visible, textarea.ckeditor');
					if($('.tde-controls-ttsPlay').is(':visible')) {
						selector.push($('.tde-controls-ttsPlay'));
					}
					$('#tde-content .tabable:not(.magnified-content):visible, textarea.ckeditor').each(function() {
						selector.push($(this));
					});
					selector.push($('.tde-controls-back'));
					selector.push($('.tde-controls-next'));
				}
				
				if(key == 'shiftTab') {
					selector = selector.reverse();
				}
			}
		
		/*} else if(key == 'shiftTab') {
			selector = $('.tabable:not(.magnified-content .tabable):visible');
		}*/
		
		return selector;
	}
	
	function reset() {
		scanProperties = null;
		scanRunning = false;
		audioPlaying = false;
		scanCount = 0;
		
		parentCycle = true, childCycle = false;
		childCycleCount = 0;
		
		selectorIndex = 0, selectedElem = null;
		childSelectorIndex = 0, tabbedChildElem = null, tabbedChildsParent = null;
		stopScan();
		stopActionPlayList();
	}
	
	function refreshActionPlayList(audios) {
		actionPlayList.remove();
		for(var i=0; i<audios.length; i++) {
			actionPlayList.add({
				title: "tab text to speech",
				mp3: tde.config.mediaUrl + audios[i].replace('.ogg','.mp3'),
				oga: tde.config.mediaUrl + audios[i],
			});
		}
		actionPlayList.select(0);
	} 
	
	function stopActionPlayList() {
		if(actionPlayList) {
			actionPlayList.pause();
		}
	}
	
	function playActionPlayList() {
		if(actionPlayList) {
			actionPlayList.play();
		}
	}
	
	
	return {
		bindBodyTabEnterKey : bindBodyTabEnterKey,
		tab : tab,
		shiftTab : shiftTab,
		reset : reset,
		start : startScan,
		stop : stopScan,
		stopActionAudio : stopActionPlayList,
		playActionAudio : playActionPlayList,
		refreshActionAudio : refreshActionPlayList
	};
	
	
	
})();