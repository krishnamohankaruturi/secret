var testController = can.Control({
	defaults : {
		//tde : {},
		ticketObj : {},
		ttsPlayList : undefined
	},
}, {
	'init' : function() {
		
	},
	'.checkticket click' : function(el, ev) {
		this.setUpTest(el.data('obj'));
	},
	
	setUpTest : function(jsonObj) {
		// var jsonObj = $(this).data('obj');
		// Get StudentsTest and find which section he is in and find
		// lastNavQuestion.
		// Get the Tickets for the test and find out for this Section Needs Tickets
		// or Not.
		tde.testparam.jO = jsonObj;
		tde.config.uiblock = true;
		//testModel.getStudentTest(jsonObj, this.proxy('setTestObj'));
		testModel.getTest(jsonObj, this.proxy('setTestObj'));
	},
	
	setTestObj : function(testRes) {
		
		var currentTest = testRes;
		var testSections = new Array();
		
		for (var i = 0; k = currentTest.testSections.length, i < k; i++) {
			currentTest['sectionId_' + currentTest.testSections[i].id] = i;
			for (var j = 0; l = currentTest.testSections[i].testlets.length, j < l; j++) {
				currentTest.testSections[i]['testletId_' + currentTest.testSections[i].testlets[j].id] = j;
			}
			testSections.push('{"id":' + currentTest.testSections[i].id + ', "sortOrder":'+currentTest.testSections[i].sortOrder + '}');
		}
		testObj = currentTest;
		if(tde.testparam.jO.testFormatCode == 'ADP'){
			testObj.adaptiveTestPartObj = JSON.parse(testObj.adaptiveTestPartObj);
			testObj.adaptiveTestTaskVariantFoils = JSON.parse(testObj.adaptiveTestTaskVariantFoils);
			testObj.adaptiveTestSectionContainerThetas = JSON.parse(testObj.adaptiveTestSectionContainerThetas); 
			testObj.studentResponseTaskVariantFoilId = JSON.parse(testObj.studentResponseTaskVariantFoilId); 
			if(testObj.studentResponseTaskVariantFoilId.length > 0){
				var adaptiveStudentResponses = testObj.studentResponseTaskVariantFoilId;
				var studentResponseTaskVariantFoilId = [];
				for(var idx in adaptiveStudentResponses){
					//console.log('getNextAdaptiveTestPart studentResponseTaskVariantFoilId :' + studentResponses[idx].taskId +  ',' + studentResponses[idx].foil);
					studentResponseTaskVariantFoilId[adaptiveStudentResponses[idx].taskId] = adaptiveStudentResponses[idx].foil;
				} 
				testObj.studentResponseTaskVariantFoilId = studentResponseTaskVariantFoilId;
			}
		}
		// TODO: This seems a bit weird.  Presumeably there will be an ADP test that is not fixed lenght.  T
		//       This will have to change.
		testObj.fixedLength = tde.testparam.jO.testFormatCode == 'ADP';
		
		tde.testparam.pluckedTestSections = '['+testSections.join(',')+']';
		
		testModel.getStudentTest(tde.testparam.jO, tde.testparam.pluckedTestSections, this.proxy('setStudentTestObj'));
		
		/*
		 * This if block is for LCS to use nginx-lcs instance to load media files
		 * lcsId and lcsMediaUrl attribute in testObj is defined in LCS Node code
		 * DO NOT OVERRIDE/RESET THIS TWO ATTRIBUTES in TDE
		 * 
		 * SureshMuthu
		 
		if(testObj.lcsId && testObj.lcsId.length > 0) {
			var mediaUrlArray = tde.config.mediaUrl.split('/');
			var mediaContext = mediaUrlArray[mediaUrlArray.length-2];
			
			tde.config.mediaUrl = testObj.lcsMediaUrl + mediaContext;			
		}*/		
	},
	
	setStudentTestObj : function(studentTestRes) {
		var studentTest = studentTestRes.studentTest;
		studentTest.lcsId = studentTestRes.lcsId;
		// Check if the test is already started from LCS
		if(!tde.config.lcsId && studentTest.lcsId != tde.config.lcsId) {
			error.show("You cannot change system during the test process for this test. Go back to your previous system or contact Help Desk !");
			return;
		}
		
		
		currentStudentTestSection = studentTest.studentTestSections[0];
		currentStudentTestSection['score'] = 0;
		currentStudentTestSection['checkResponses'] = false;
		currentStudentTest = studentTest;
		currentStudentTest.gracePeriod = tde.testparam.jO.gracePeriod;//jsonObj.gracePeriod;

		tasks = testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].tasks;
		
		var currentSectionNum = testObj['sectionId_' + currentStudentTestSection.testSectionId];
		for(var i = currentSectionNum-1 ; i >= 0; i--) {
			var section = testObj.testSections[i];
			if(!section.sectionbreak) {
				tde.testparam.softbreakCount += section.tasks.length;
			} else {
				break;
			}
		}
		
		tde.config.uiblock = true;
		testModel.getTicket(tde.testparam.jO.id, this.proxy('checkShowTicketPopUp'));
	},
	
	checkShowTicketPopUp : function(ticketsRes) {
		this.options.ticketObj = ticketsRes;
		if (ticketsRes && !jQuery.isEmptyObject(ticketsRes) && ticketsRes.ticketsNeeded) {
			if (ticketsRes.ticketLevel == 'test') {
				this.ticketPopUp(tde.testparam.jO.testId, tde.testparam.jO.testSessionId, tde.testparam.jO.id, ticketsRes.ticketNo, currentStudentTest.testName, null, false);
			} else {
				var control = this;
				$.each(ticketsRes.testSectionTickets, function() {
					if (this.ticketNo != null) {
						if (this.testSectionId == currentStudentTestSection.testSectionId) {
							control.ticketPopUp(tde.testparam.jO.testId, tde.testparam.jO.testSessionId, tde.testparam.jO.id, this.ticketNo, currentStudentTest.testName, testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].name, false);
							
							$('#ticketNumber').focus();
							return;
						}
					}
				});
			};
		} else {
			this.loadDirections('test');
		}
	},
	
	loadDirections: function(set) {
		
		if(currentStudentTestSection.testingStatusId != 1) {
			currentStudentTestSection['checkResponses'] = true;
		}
		if(tde.testparam.testTypeName != 'Practice' && (!tde.testparam.jO.testFormatCode || tde.testparam.jO.testFormatCode != 'ADP'))
			test.updateTestStatus("inprogress");
		
		// load tts.ejs.. if readaloud for genui should be enabled.
		if(testObj.uiname == 'genTest' && !$('#textToSpeech').length) {
			var spokenPrefObj = _.findWhere($.parseJSON(sessionStorage.getItem('profile'))['spoken'],{attrName: 'assignedsupport'});
			var spokenEnable = null;
			if(spokenPrefObj) {
				spokenEnable = spokenPrefObj['attrValue'];
				if(spokenEnable && spokenEnable === 'true') {
					$('body').append(new EJS({url : 'js/views/tool/tts.ejs'}).render({type: 'task'}));
					if(_.contains(_.pluck(tasks, 'testlet'), true)) {
						$('body').append('<div id="ttsPassageAudio" class="jp-jplayer" style="display:none;"></div>');
					}
					
				}
			}
		}
		
		if(/ipad/ig.test(userAgent) && testObj.uiname == 'altTest') {
			$('#rotateIpadPopup .overlay-content').html('');
			$('#rotateIpadPopup .overlay-content').append('<img src="/images/ipad-orientation-arrow.png" />');
		}
		
		tde.testparam.reviews = new Array("P");
		$('body').addClass('intro');
		if($('#wrapper').length < 1) {
			$('body').prepend('<div id="wrapper"><div id="scroller"></div></div>');
			$('#scroller').append($('.w'));
			
			if ( Modernizr.touch ) {
				tde.config.myscroll = new iScroll('wrapper');
			} else {
				tde.config.myscroll = null;
			}
		}
		// Test Object
		// Student Test Object
		var html = new EJS({
			url : 'js/views/directions.ejs'
		}).render({
			currentTest : testObj,
			set : set,
			studentTestSection : currentStudentTestSection,
			fromTestDir : false
		});
		$('.w').first().html(html);
		this.loadDirectionsAudio(set);
		
		if ( tde.config.myscroll != null ) {
			tde.config.myscroll.refresh();
		} else {
			$('#wrapper').scrollTop(0);
		}
		tde.testparam.postItNotes = new Array();
		
		// if test contains ER then load ckeditor
		if(_.contains(_.pluck(tasks, 'taskType'),'ER')) {
			var control = this;
			control.loadScript('/js/external/ckeditor/ckeditor.js', function() {
				//setTimeout(function(){
					control.loadScript('/js/external/ckeditor/adapters/jquery.js');
				//},500);
			});
		}
	},
	
	loadDirectionsAudio: function(set) {
		if(set == 'section' || testObj.directions == null) {
			var spokenPrefObj = _.findWhere($.parseJSON(sessionStorage.getItem('profile'))['spoken'],{attrName: 'userspokenpreference'});
			var spokenPref = null;
			if(spokenPrefObj) {
				spokenPref = spokenPrefObj['attrValue'];
			}
			
			if(spokenPref) {
				var directionsContentGroups = _.map(_.sortBy(_.filter(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].contentGroups, function(contentGroup) {
																	return contentGroup.readAloudAccommodation != null && contentGroup.readAloudAccommodation.readAloudType.replace(" ","").toLowerCase() == spokenPref.replace(" ","").toLowerCase() && contentGroup.instructionCode.toLowerCase() == 'begin';
															}), function(contentGroup) {
																	return contentGroup.readAloudAccommodation.defaultOrder;
													}), function(contentGroup) {
															if(contentGroup.htmlElementId == null && contentGroup.compositeMediaId == null) {
																contentGroup.htmlElementId = '#test-description';
															} else if(contentGroup.htmlElementId != null) {
																if(contentGroup.htmlElementId.substring(1,0) != '#' && contentGroup.htmlElementId.substring(1,0) != '.' ) {
																	contentGroup.htmlElementId = '#'+contentGroup.htmlElementId;
																}
															}
															return contentGroup;
											});
				
				if(!_.isEmpty(directionsContentGroups)){
					$('.direction-tts').append('<div id="directionsAudio" class="cdiv-inner tde-controls-ttsPlay tabable play"><a href="#"><img src="/images/tts-read.png"></a></div><div id="ttsAudio_direction" class="jp-jplayer"></div>');
					this.options.ttsPlayList = new jPlayerPlaylist(
							{
								jPlayer: '#ttsAudio_direction'
							},
							[],
							{
								supplied: "oga, mp3",
								loop: false
							});
					
					for(var cg in directionsContentGroups) {
							var floc = directionsContentGroups[cg].readAloudAccommodation.accessibilityFile.fileLocation;
							this.options.ttsPlayList.add({
								title: "text to speech",
								mp3: tde.config.mediaUrl + floc.replace('.ogg','.mp3'),
								oga: tde.config.mediaUrl + floc,
								contentGroup : directionsContentGroups[cg]
							});
					}
					
				}
			}
			
		}
	},
	
	ticketPopUp : function(testId, testSessionId, studentsTestsId, ticket, testName, sectionName, reload) {
		this.options.ticketObj['reload'] = reload;
		this.options.ticketObj['ticketNo'] = ticket;
		$('.w').append(new EJS({
			url : 'js/views/ticketconfirm.ejs'
		}).render());

		//$('#ticketConfirmation').css("position","fixed").css("top", ($(window).height() / 2) - ($('#ticketConfirmation').outerHeight() / 2));
		
		var windowWidth = document.documentElement.clientWidth,
			windowHeight = document.documentElement.clientHeight,
			popupHeight = $(window).height() * 0.4,
			popupWidth = $('#ticketConfirmation .overlay-content').width();

		$('#ticketConfirmation .overlay-content').css({
			"position" : "absolute",
			"top" : ((windowHeight - popupHeight) / 2),
			"left" : windowWidth / 2 - popupWidth / 2
		});
		
		if (sectionName) {
			$('#ticketConfirmation .activation-form h3').html($('#ticketConfirmation .activation-form h3').html() + ' ' + testName + ' -- ' + sectionName);
		} else {
			$('#ticketConfirmation .activation-form h3').html($('#ticketConfirmation .activation-form h3').html() + ' ' + testName);
		}
		$('#ticketConfirmation .overlay-content').show();
		$('#ticketConfirmation .overlay').show();
		$('#ticketConfirmation').show();
		
		if ( tde.config.myscroll != null ) {
			tde.config.myscroll.destroy();
		}
		
		tde.config.myscroll = null;

		var D = document;
		$('#ticketConfirmation').height(Math.max(Math.max(D.body.scrollHeight, D.documentElement.scrollHeight), Math.max(D.body.offsetHeight, D.documentElement.offsetHeight), Math.max(D.body.clientHeight, D.documentElement.clientHeight)));

		$('#ticketNumber').val('');
		$('#ticketConfirmation .overlay-hdr p').hide();
		
		if(!/ipad/ig.test(userAgent)){
			$('#ticketConfirmation .overlay-content').draggable({
				cancel : "#ticketNumber",
				containment : "window"
			});
		}
	},
	
	'#directionsAudio click' : function(el) {
		if($(el).hasClass('play')) {
			$(el).removeClass('play').addClass('pause');
			//$(this).text('pause');
			$('.tde-controls-ttsPlay img').attr('src', '/images/tts-pause.png');
			this.options.ttsPlayList.play();
			this.options.ttsPlayList.setPaused(false);
		} else if($(el).hasClass('pause')) {
			$(el).removeClass('pause').addClass('play');
			this.options.ttsPlayList.pause();
			this.options.ttsPlayList.setPaused(true);
			//$(this).text('play');
			$('.tde-controls-ttsPlay img').attr('src', '/images/tts-read.png');
		}
		
	},
	
	'#validateTicket click' : function() {
		if (this.options.ticketObj.ticketNo == $('#ticketNumber').val()) {
			// getTest(testId, testSessionId, studentsTestsId);
			this.loadDirections('test');
			
			if ( Modernizr.touch ) {
				tde.config.myscroll = new iScroll('wrapper');
			} else {
				tde.config.myscroll = null;
			}
		} else {
			var tp = parseInt($('#ticketConfirmation .overlay-content').css('top').match('\\d+'));
			$('#ticketConfirmation .overlay-content').animate({
				top : tp - 50
			}, {
				duration : 'slow',
				easing : 'easeOutBounce'
			}).animate({
				top : tp
			}, {
				duration : 'slow',
				easing : 'easeOutBounce'
			});
			$('#ticketConfirmation .overlay-hdr p').show();
			$('#ticketNumber').blur();
		}
	},
	
	'#ticketConfirmation a.destroy click' : function() {
		if(!this.options.ticketObj.reload) {
			$('#ticketConfirmation').remove();
			if ( Modernizr.touch ) {
				tde.config.myscroll = new iScroll('wrapper');
			} else {
				tde.config.myscroll = null;
			}
		} else {
			window.location.replace(contextPath + "/studentHome.htm");
		}
	},
	
	'#ticketNumber keypress' : function(el,e) {
		if (e.which == 13) {
			if (this.options.ticketObj.ticketNo == $('#ticketNumber').val()) {
				// getTest(testId, testSessionId, studentsTestsId);
				this.loadDirections('test');
				
				if ( Modernizr.touch ) {
					tde.config.myscroll = new iScroll('wrapper');
				} else {
					tde.config.myscroll = null;
				}
			} else {
				var tp = parseInt($('#ticketConfirmation .overlay-content').css('top').match('\\d+'));
				$('#ticketConfirmation .overlay-content').animate({
					top : tp - 50
				}, {
					duration : 'slow',
					easing : 'easeOutBounce'
				}).animate({
					top : tp
				}, {
					duration : 'slow',
					easing : 'easeOutBounce'
				});
				$('#ticketConfirmation .overlay-hdr p').show();
				$('#ticketNumber').blur();
			}
		}
	},
	
	'#ticketNumber click' : function() {
		$('#ticketNumber').focus();
	},
	
	'#app-take-test click' : function() {
		// Load Test
		$('.load-overlay').fadeOut('slow');
		$('#wrapper').remove();
		$('body').prepend('<div class="w"></div>');
		new testController( 'body .w', {} );
		if (testObj.uiname == 'genTest') {
			$('body').append(new EJS({
				url : 'js/views/genTest.ejs'
			}).render({linearNavigation : _.find(testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].rules, function(rule){
												return rule.code.toLowerCase() == 'linear' && rule.navigation == true;}), 
						exitFlag : currentStudentTest.exitFlag
					}));
			$('.w').first().before($('#oldTestPage').find('#tool-arena').clone());
			$('body').addClass('general');
		} else if (testObj.uiname == 'altTest') {
			tde.testparam.exitandsave = false;
			$('body').append(new EJS({
				url : 'js/views/altTest.ejs'
			}).render({exitandsave : tde.testparam.exitandsave, exitFlag : currentStudentTest.exitFlag}));
			$('.w').css({
				width : '95%'
			});
			$('body').addClass('alternate');
		}
		$('.w').first().html($('#oldTestPage').find('.w').html());
		$('#oldTestPage').remove();
		
		task.removeCss('screen.css');
		task.setupTasks();
		var footer = $('.footer');
		$('.nav-header').find("*").addClass('unselectable');
		footer.find("*").addClass('unselectable');
		
		//DE7659
		if(testObj.uiname == 'genTest') {
			var ios7 = (device.platform == 'iOS' && parseInt(device.version) >= 7);
		    if (ios7){
		    	footer.css({ "top": footer.position().top, "bottom": "auto"});
		    }
		}
	},
	
	'#gotoSectionDirections click' : function() {
		// Load Test
		var html = new EJS({
			url : 'js/views/directions.ejs'
		}).render({
			currentTest : testObj,
			set : 'section',
			studentTestSection : currentStudentTestSection,
			fromTestDir : true
		});
		$('.w').first().html(html);
		this.loadDirectionsAudio('section');
		
		if ( tde.config.myscroll != null ) {
			tde.config.myscroll.refresh();
		} else {
			$('#wrapper').scrollTop(0);
		}
	},
	
	'#backSectionDirection click' : function() {
		// Load Test
		var html = new EJS({
			url : 'js/views/directions.ejs'
		}).render({
			currentTest : testObj,
			set : 'test',
			studentTestSection : currentStudentTestSection,
			fromTestDir : false
		});
		$('.w').first().html(html);
		if ( tde.config.myscroll != null ) {
			tde.config.myscroll.refresh();
		} else {
			$('#wrapper').scrollTop(0);
		}
	},
	
	'.tde-controls-back click' : function() {
		task.storeAndGo(false);
	},
	
	'.tde-controls-next click' : function() {
		task.storeAndGo(true);
	},
	
	'.tde-controls-clear click' : function() {
		task.clear();
		if(tasktype == 'ITP') {
			task.applyColorsItp();
		}
	},
	
	'.tde-controls-reviewend click' : function() {
		if (tasktype == 'ITP') {
			task.removeCss();
			task.recordAnswer(operationalJS.scoreItem(), false);
		} else if (tasktype == 'MC-MS') {
			if(task.getTmpAnswer().length < tasks[tde.testparam.currentQuestion].minChoices && tasks[tde.testparam.currentQuestion].minChoices > 0) {
				$('body .w').append(new EJS({
					url : 'js/views/navigation-overlay.ejs'
				}).render({constraint : 'min', minChoices : tasks[tde.testparam.currentQuestion].minChoices}));
				test.showAlertBox('navigation-overlay');
				return;
			}
			task.storeTmpAnswerInResponses(false);
		} else if ( tasktype == 'ER' ) {
			task.recordAnswer(CKEDITOR.instances['answer_'+tde.testparam.currentQuestion+'_0'], false);
		}
		if(!task.canNavigate()) {
			return;
		}
		test.showReview(true);
	},
	
	'#app-go-back click' : function() {
		$('body').removeClass('intro');
		$('#reviewEnd').remove();
		$('header').show();
		$('#toolbox-c').show();
		$('#tde-content:not(.magnified-content)').show();
		$('.tde-controls-c').show();

		$('.alt-header').show();
		$('.alt-footer').show();
		tool.showAllUITools();
		if(testObj.uiname == 'genTest') {
			// goto 1st task if genUI
			task.showTask(0);
			test.navigateHeader();
		} else {
			var taskNum = tasks.length - 1;
			//set testletStory params for it to goto task and not the testlet
			task.setTestletStoryParams(taskNum);
			// goto last task if altUI
			task.showTask(taskNum);
		}
	},
	
	'#replayButton click' : function() {
		if ($('#pauseButton').hasClass('resume')) {
			$('#pauseButton').removeClass('resume').addClass('pause');
			$('#pauseButton').text(messages.testPage.pause);
		}
		task.replayAll();
		return false;
	},
	
	'#pauseButton click' : function() {
		if ($(this).hasClass('pause')) {
			$('#testAudio').jPlayer("pause");
			var video = document.getElementsByTagName('video')[0];
			if (video) {
				video.pause();
			}
			if (tde.config.stim_flags.interval) {
				clearInterval(tde.config.stim_flags.interval);
				tde.config.stim_flags.pause = 'interim';
			}
			if (tde.config.stim_flags.nextPageInterval) {
				clearInterval(tde.config.stim_flags.nextPageInterval);
				tde.config.stim_flags.pause = 'next';
			}
			$(this).removeClass('pause').addClass('resume');
			$(this).text(messages.testPage.resume);
		} else {
			$(this).removeClass('resume').addClass('pause');
			$(this).text(messages.testPage.pause);
			if (tde.config.stim_flags.pause == 'next') {
				tde.config.stim_flags.pause = '';
				tde.config.stim_flags.nextPageInterval = setTimeout("task.showNextPage()", 3000);
			} else {
				// if($('#testAudio').data('jPlayer').status.waitForPlay)
				// {
				if (tde.config.stim_flags.pause == 'interim' && !$('#testAudio').data('jPlayer').status.currentTime) {
					tde.config.stim_flags.pause = '';
					task.doNextSteps();
				} else {
					tde.config.stim_flags.pause = '';
					var video = document
					.getElementsByTagName('video')[0];
					if (video) {
						if ((video.currentTime != video.duration) && (video.currentTime != 0)) {
							video.play();
						} else {
							$('#testAudio').jPlayer("play");
						}
					} else {
						$('#testAudio').jPlayer("play");
					}
				}
			}
		}
		return false;

	},
	
	'#endButton click' : function() {
		$('#testAudio').jPlayer('stop');
		var video = document.getElementsByTagName('video')[0];
		if (video) {
			video.pause();
		}
		tde.testparam.currentScreen = 0;
		if (tde.config.stim_flags.interval) {
			clearInterval(tde.config.stim_flags.interval);
		}
		if (tde.config.stim_flags.nextPageInterval) {
			clearInterval(tde.config.stim_flags.nextPageInterval);
		}
		tde.config.stim_flags = {
			toRead : "options",
			interval : 0,
			secondPass : false,
			audioCount : 0,
			foil : 0,
			nextPageinterval : 0,
			pause : ''
		};
		test.showReview(true);
	},
	
	'#endAssessment click' : function() {
		test.showAlertBox('confirmEnd');
	},
	
	'#confirmCancelButton click' : function() {
		$("#confirmEnd .overlay-content").fadeOut("slow");
		$("#confirmEnd .overlay").fadeOut("slow");
		$("#confirmEnd").fadeOut("slow");
	},
	
	//$('body').delegate('#confirmEndButton', 'click', function() {
	'#confirmEndButton click' : function() {
		$("#confirmEnd .overlay-content").fadeOut("slow");
		$("#confirmEnd .overlay").fadeOut("slow");
		$("#confirmEnd").fadeOut("slow");
		tde.config.uiblock = false;
		$.blockUI({ message: '<h1> Processing...</h1>',
		    overlayCSS: { backgroundColor: '#C8C8C8', color: '#fff' }
		});
		var control = this;
		clearInterval(test.gracePeriodinterval);
		
		if(tde.testparam.testTypeName == 'Practice'){
			var tempstudentTestSections = [];
			for (var section in currentStudentTest.studentTestSections){
				if(currentStudentTest.studentTestSections[section].id != currentStudentTestSection.id){
					tempstudentTestSections.push(currentStudentTest.studentTestSections[section]);
				}
			}
			currentStudentTest.studentTestSections = tempstudentTestSections;
			if(currentStudentTest.studentTestSections.length > 0){
				$('#tool-arena').remove();
				responses = {
					lastAnswer : new Date().getTime(),
					changed : new Date().getTime(),
					responseIds : new Array(),
					values : new Array(),
					answer : new Array(),
					history : new Array(),
					scores : new Array()
				};
				tde.testparam.currentQuestion = -1;
				currentStudentTestSection = currentStudentTest.studentTestSections[0];
				currentStudentTestSection['score'] = 0;
				currentStudentTestSection['checkResponses'] = false;
				tasks = testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].tasks;
				$.unblockUI();
				control.loadDirections('section');
			} else {
				window.location.replace(contextPath + "/studentHome.htm");
			}
		} else {
			var toData = null;
			var sectionScoreJson = _.findWhere(currentStudentTest.scores,{testSectionId: currentStudentTestSection.testSectionId});
			sectionScoreJson.score = _.reduce(responses.scores, function(memo, num){ return memo + num; }, 0);
			if(tde.testparam.jO.testFormatCode == 'ADP'){
				toData = {
						studentTestId : currentStudentTest.id,
						studentTestSectionId : currentStudentTestSection.id,
						testSectionId : currentStudentTestSection.testSectionId,
						testSessionId : currentStudentTest.testSessionId,
						testFormatCode : tde.testparam.jO.testFormatCode,
						testTypeName : tde.testparam.testTypeName,
						userName : tde.config.student.userName,
						interimThetaValues : apdaptiveTest.calculateInterimTheta(testObj),
						numberOfCompletedPart: testObj.adaptiveTestPartObj.numberOfCompletedPart,
						numberOfPart: testObj.adaptiveTestPartObj.numberOfPart,
						testScore : JSON.stringify(currentStudentTest.scores),
						sectionScore : JSON.stringify(sectionScoreJson),
						currentSectionBreak : testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].sectionbreak
				};
			} else {
				toData = {
						studentTestId : currentStudentTest.id,
						studentTestSectionId : currentStudentTestSection.id,
						testSectionId : currentStudentTestSection.testSectionId,
						testSessionId : currentStudentTest.testSessionId,
						testFormatCode : tde.testparam.jO.testFormatCode,
						testTypeName : tde.testparam.testTypeName,
						userName : tde.config.student.userName,
						testScore : JSON.stringify(currentStudentTest.scores),
						sectionScore : JSON.stringify(sectionScoreJson),
						currentSectionBreak : testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].sectionbreak
				};
			}

			serverstore_responses(1, false, true);
			test.serverstore_responsehistory();
			$.ajax({
				url : contextPath + '/JSON/studentTest/saveTest.htm',
				type : 'GET',
				dataType : 'json',
				data : ( toData ),
				success : function(data) {
					
					if(data =="") {
						alert("Lost Internet");
					}
					
					if(data.retPage == 'adaptiveTestNextPart'){
						$.unblockUI();
						$('#tool-arena').remove();
						responses = {
							lastAnswer : new Date().getTime(),
							changed : new Date().getTime(),
							responseIds : new Array(),
							values : new Array(),
							answer : new Array(),
							history : new Array(),
							scores : new Array()
						};
						tde.testparam.currentQuestion = -1;
						control.setTestObj(data.adaptiveTestNextPartObj);
					} else if (data.retPage == 'home') {
						//window.location.replace(contextPath + "/studentHome.htm");
						if(testObj.feedbackNeeded) {
							var scores = currentStudentTest.scores;
							var maxTestScore = _.reduce(_.pluck(scores, 'maxScore'), function(n,m) {return n+m;}, 0);
							var studentTestScore = _.reduce(_.pluck(scores, 'score'), function(n,m) {return n+m;}, 0);
							$('#reviewEnd').hide();
							$.unblockUI();
							$('body .w').append(new EJS({
								url : 'js/views/feedback.ejs'
							}).render({testName : testObj.name, feedbackRules : testObj.feedbackRules, outcometype : testObj.feedbackOutcomeType, maxTestScore: maxTestScore, studentTestScore: studentTestScore}));
							return;
						} else {
							window.location.replace(contextPath + "/studentHome.htm");
						}
					} else if (data.retPage == 'test') {

						$('#tool-arena').remove();
						responses = {
							lastAnswer : new Date().getTime(),
							changed : new Date().getTime(),
							responseIds : new Array(),
							values : new Array(),
							answer : new Array(),
							history : new Array(),
							scores : new Array()
						};
						tde.testparam.currentQuestion = -1;

						var jsonObj = tde.testparam.jO;
						$.ajax({
							url : contextPath + '/JSON/studentTest/getById.htm',
							dataType : 'json',
							type : "POST",
							data : {
								pluckedTestSections : tde.testparam.pluckedTestSections,
								studentsTestsId : jsonObj.id,
								testTypeName : jsonObj.testTypeName
							},
							success : function(studentTestRes) {
								// Local Storage -
								// From Now
								// Ticket Popup
								// Get the Ticket
								// Number/Validate
								// Render the
								// Directions Page
								// Render the Test
								// Page
								// We Should Render
								// the Review and
								// End
								/*var currentTest = studentTestRes.test;

								for (var i = 0; k = currentTest.testSections.length, i < k; i++) {
									currentTest['sectionId_' + currentTest.testSections[i].id] = i;
									for (var j = 0; l = currentTest.testSections[i].testlets.length, j < l; j++) {
										currentTest.testSections[i]['testletId_' + currentTest.testSections[i].testlets[j].id] = j;
									}
								}
								testObj = currentTest;*/
								var studentTest = studentTestRes.studentTest;
								studentTest.lcsId = studentTestRes.lcsId;
								
								currentStudentTestSection = studentTest.studentTestSections[0];
								currentStudentTestSection['score'] = 0;
								currentStudentTestSection['checkResponses'] = false;
								currentStudentTest = studentTest;
								//testObj.uiname = testObj.uiname;
								
								tasks = testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].tasks;
								
								var currentSection = testObj['sectionId_' + currentStudentTestSection.testSectionId];
								for(var i = currentSection-1 ; i >= 0; i--) {
									var section = testObj.testSections[i];
									if(!section.sectionbreak) {
										tde.testparam.softbreakCount += section.tasks.length;
									} else {
										break;
									}
								}
								
								// Soft-break no longer require tickets
								/*$.ajax({
									url : contextPath + '/JSON/studentTest/checkTicket.htm',
									dataType : 'json',
									type : "GET",
									data : {
										studentsTestsId : jsonObj.id
									},

									success : function(ticketsRes) {
										$.unblockUI();
										// var
										// ticketsRes=ticketsRes;
										if (ticketsRes && !jQuery.isEmptyObject(ticketsRes) && ticketsRes.ticketsNeeded) {
											if (ticketsRes.ticketLevel == 'test') {
												// ticketPopUp(jsonObj.testId,
												// jsonObj.testSessionId,
												// jsonObj.id,
												// ticketsRes.ticketNo,
												// currentStudentTest.testName);
												control.loadDirections('section');
											} else {
												$.each(ticketsRes.testSectionTickets, function() {
													if (this.ticketNo != null) {
														if (this.testSectionId == currentStudentTestSection.testSectionId) {
															//$('body .w').append(new EJS({
															//	url : 'js/views/ticketconfirm.ejs'
															//}).render());
															control.ticketPopUp(jsonObj.testId, jsonObj.testSessionId, jsonObj.id, this.ticketNo, currentStudentTest.testName, testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].name, true);
															$('#ticketNumber').focus();
															return;
														}
													}
												});
											}
											;
										} else {
											control.loadDirections('section');
										};
									}
								});*/
								$.unblockUI();
								control.loadDirections('section');
							}
						});
						
						
						//testModel.getStudentTest(jsonObj, this.proxy('setTestObj'));
					}
				}
			});
		}
		//}
	},
	
	'.tde-controls-save click' : function() {
		//get ejs
		$('body .w').append(new EJS({
			url : 'js/views/savedraft.ejs'
		}).render({exitandsave : tde.testparam.exitandsave}));
		test.showAlertBox('savedraft');
		tabScan.reset();
	},
	
	'#savedraftyes click' : function() {
		if(tde.testparam.exitandsave) {
			window.location.replace(contextPath + "/studentHome.htm");
		} else {
			$.ajax({
				url : contextPath + '/JSON/studentTestSection/restore.htm',
				type : "GET",
				data :{ studentTestSectionId : currentStudentTestSection.id},
				success : function(data) {
					window.location.replace(contextPath + "/studentHome.htm");
				},
				error : function (jqXHR) {
					logger.info(tde.testparam.username+ ": not able to store task/foil order");
					logger.info(tde.testparam.username + ": " + jqXHR.responseText);
					$('#savedraft').remove();
				}
			});
		}
	},
	
	'#savedraftno click' : function() {
		var savedraftoverlay = $('#savedraft');
		savedraftoverlay.fadeOut('slow');
		setTimeout(function() {
			savedraftoverlay.remove();
		}, 500);
	},
	
	'#navigationRule click' : function() {
		var overlay = $('#navigation-overlay');
		overlay.fadeOut('slow');
		setTimeout(function() {
			overlay.remove();
		}, 500);
	},
	
	loadScript: function(srcURL, callback)  {
		var deferred = new $.Deferred();
		
		deferred.done(function(callback) {
			if(callback) {
				callback();
			}
		});
		
		var e = document.createElement('script');
		e.onload = function () { deferred.resolve(callback); };
		e.src = srcURL;
		document.getElementsByTagName("head")[0].appendChild(e);
		
		return deferred.promise();
	}
});