
var test = new function() {
	var gracePeriodInterval;
	
	this.beginTest = function(testrequest) {
		
		responses.lastAnswer = new Date().getTime();
		$('body').removeClass('intro');
		tde.testparam.currentQuestion = currentStudentTestSection.lastNavQNum;
		tool.setUpTools();
		
		$('.hdr').sliderkit({
			auto:false,
			circular:false,
			shownavitems:5,
			scroll:3,
			scrollspeed: 500,
			scrolleasing: "linear",
			mousewheel:false,
			start:1
		});
				
		if(testObj.uiname == 'altTest') {
			tde.testparam.showTick = false;
			$(document).off(".tdeBlock"); //DE7567
		}
		
		/*if(tde.testparam.testTypeName == 'Practice'){
			refreshPageDisable = true;
		}*/
		
		task.showTask(currentStudentTestSection.lastNavQNum);
		ttsAccommodation.init(JSON.parse(sessionStorage.getItem('profile')));
		
		if(testObj.uiname == 'genTest') {
			test.navigateHeader();
		}
		
		if(currentStudentTest.gracePeriod > 0 && (!tde.testparam.jO.testFormatCode || tde.testparam.jO.testFormatCode != 'ADP')){
			gracePeriodInterval = setTimeout(function(){
				test.updateTestStatus("inprogresstimedout");
			}, currentStudentTest.gracePeriod);
		}
		
		tabScan.bindBodyTabEnterKey();
		
		//if(testObj.uiname == 'altTest') {
			//ttsAccommodation.init(JSON.parse(sessionStorage.getItem('profile')));
			
			if(tde.config.ttsrefresh) {
				if(!Modernizr.touch) {
					ttsAccommodation.refresh();
				} else {
					setTimeout(function(){
						ttsAccommodation.refresh();
					},2000);
				}
			}
		//}
		
		//capture browser/os version from which test is taken
		tracker.trackUserAgent();
	};
	
	this.localstore_responses = function() {
		var updated = parseInt(localStore.getChange("responses." + currentStudentTest.id));
		
		if (!updated || responses.changed > updated) {
			//localStore.set("responses." + currentStudentTest.id, responses);
		}
		
		//setTimeout("test.localstore_responses()", localSaveCycle); // Reset the timmer.
	};
	
	this.showAlertBox = function(elem){
		var windowWidth = document.documentElement.clientWidth,
			windowHeight = document.documentElement.clientHeight,
		popupHeight = $(window).height()*0.4;
		popupWidth = $('#'+elem+' .overlay-content').width(); 
		
		$('#'+elem+' .overlay-content').css({"position": "absolute",  
						  "top": windowHeight/2-popupHeight/2,
						  "left": windowWidth/2-popupWidth/2  });  
	
		$('#'+elem+' .overlay-content').show();
		$('#'+elem+' .overlay').show();
		$('#'+elem).show();
		if(!/ipad/ig.test(userAgent) && elem!='ticketConfirmation') {
			$('#'+elem+' .overlay-content').draggable({ containment: "window" });
		}
		var D = document;
		$('#' + elem).height(Math.max(Math.max(D.body.scrollHeight, D.documentElement.scrollHeight), Math.max(D.body.offsetHeight, D.documentElement.offsetHeight), Math.max(D.body.clientHeight, D.documentElement.clientHeight))-8);
		$('#' + elem).width(Math.max(Math.max(D.body.scrollWidth, D.documentElement.scrollWidth), Math.max(D.body.offsetWidth, D.documentElement.offsetWidth), Math.max(D.body.clientWidth, D.documentElement.clientWidth))-8);
	};
	
	/**
	 * function: canUseTool(name)
	 * param: string name of tool
	 * return: boolean
	 * Check if a tool can be used.
	 */
	 this.canUseTool = function(name) {
		 // This simply falls through from student -> test -> task (may need to check more later.)
		 //return studentAccommodations[name] || testAccommodations[name] || tasks[tde.testparam.currentQuestion].accommodations[name]
		//return testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].tools[name];
		//////return testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].tools.where()
		
		//return testObj.testSections[testSectionIndex].tools[name];
		 
		var ret = false;
		/*$(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].tools).each(function(){
			if(this.codeName == name) {
				ret = true;
			}
		});*/
		var currentTestSection = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]];
		
		if(currentTestSection.toolUsageCode == 'SECTION_TOOLS') {
			var testSectionTools = _.pluck(currentTestSection.tools, 'codeName');
			if(_.contains(testSectionTools, name)) {
				ret = true;
			}
		} else if(currentTestSection.toolUsageCode == 'TASK_TOOLS') {
			var taskTools = _.pluck(_.filter(_.flatten(_.pluck(currentTestSection.tasks, 'tools')), function(tool) {
															if(tool != undefined){
																return tool;
															}
							}), 'codeName');
			if(_.contains(taskTools, name)) {
				ret = true;
			}
		}
		return ret;
	 };
	 
	 this.canUseAccommodation = function(name) {
		 var profile = JSON.parse(sessionStorage.getItem('profile'));
		 
		 if(profile) {
			 return profile[name];
		 } else {
			 return false;
		 }
	 };
	
	this.questionCountAnswered = function(){
		var count=0;
		for(var c in responses.values) {
			if(responses.values[c] != null){
				count++;
			}
		}
		return count;	
	};
	
	this.defaultCursor = function() {
		$('.w').removeClass('eraserCursor');
		$('#tde-content').removeClass('highlighterCursor');
		$('.w').removeClass('strikeThroughCursor');
	};
	
	this.addPointerCursor = function() {
		$('.foils ul li').addClass('pointerCursor');
	};
	
	this.removePointerCursor = function() {
		$('.foils ul li').removeClass('pointerCursor');
	};
	
	this.showReview = function(saveResponse) {
		
		//push studentSection score
		//storeSectionScore();
		
		//Load Review End Template
		var html = new EJS({
			url : 'js/views/reviewend.ejs'
		}).render({
			currentTest : testObj
		});
		$('.w').append(html);
		
		$('body').addClass('intro');
		$('header').hide();
		$('#toolbox-c').hide();
		$('#tde-content:not(.magnified-content)').hide();
		$('.tde-controls-c').hide();
		
		$('.alt-header').hide();
		$('.alt-footer').hide();
		
		tabScan.reset();
		tool.hideAllUITools();
		tool.deactivateCursorTools();
		//tabScan.stopActionAudio(); -- stop audio added to tabScan.reset()
		ttsAccommodation.pause();
		//$('#reviewEnd').show();
		setUpReview();
		tde.config.ajaxActive = false;
		if(saveResponse)
			serverstore_responses(0, false, true); // Force a save of data.
	};
	
	function setUpReview() {
		//var lastPart, currentPart;
		$('#reviewQuestions').html("");
		//postIt.hideNotes();
		
		if(testObj.uiname != 'altTest') 
			$('#reviewQuestions').append('<p><b>Your Progress</b></p>');
		$('#reviewQuestions').append('<ul class="review-q"></ul>');
		for (var tsk in tasks) {
			if (!isNaN(parseInt(tsk))) {
				currentPart = task.findPart(tsk);
				
				/*if (currentPart != lastPart) {
					$('#reviewQuestions').append('<h4>' + taskGroupNames[currentPart] + '</h4>');
					$('#reviewQuestions').append('<ul class="review-q"></ul>');
					lastPart = currentPart;
				}*/
				var content = '<li>'
							+ '<a href="#" id="goreview_' + tsk + '">' + (parseInt(tsk) + 1 + tde.testparam.softbreakCount);
				
				if (responses.values[tsk] != null && responses.values[tsk] != undefined && responses.values[tsk] != '[]' && responses.values[tsk] != '') {
					if(jQuery.inArray(parseInt(tsk), tde.testparam.reviews) >= 0) {
						content += '<span class="answered-marked"></span></a></li>';
					} else {
						content += '<span class="answered"></span></a></li>';
					}
				} else {
					if( jQuery.inArray(parseInt(tsk), tde.testparam.reviews) >= 0 ) {
						content += '<span class="not-answered-marked"></span></a></li>';
					} else {
						content += '<span class="not-answered"></span></a></li>';
					}
				}
				
				$('.review-q:last').append(content);
				
				var linearNavigation = _.find(testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].rules, function(rule){
												return rule.code.toLowerCase() == 'linear' && rule.navigation == true;
										});
				
				if ( !testObj.fixedLength && !linearNavigation ) {
					$('#goreview_' + tsk).click(function() {
						$('#reviewEnd').remove();
						var qNum = parseInt($(this).attr('id').replace("goreview_", ""));
						if(tde.testparam.qctoans == test.questionCountAnswered()){
							if(task.nextQuestionAnswered(qNum-1) != qNum){
								test.showAlertBox('navigationAlert');
								return;
							}
						}
						$('#tde-content:not(.magnified-content)').show();
	
						if(testObj.uiname == 'genTest') {
							$('body').removeClass('intro');
							$('header').show();
							$('#toolbox-c').show();
							$('.tde-controls-c').show();
							test.navigateHeader();
						} else {
							$('.alt-header').show();
							$('.alt-footer').show();
							$('.alt-footer #pauseButton').removeClass('resume').addClass('pause');
							$('.alt-footer #pauseButton').text('pause');
							task.setTestletStoryParams(qNum);
						}
						tool.showAllUITools();
						task.showTask(qNum);
					});
				} else {
					$('#goreview_' + tsk).attr('disabled', true).css("cursor", "default");
				}
				
			}
		}
	}
	
	this.navigateHeader = function() {
		var length = Math.ceil(($(window).width() - $('#toolbox-c').width() - $('.test-nav-prev').width() - $('.test-nav-next').width()) / 51);
		var start = (parseInt($('ul.test-nav').css('left').match('\\d+')) / 51) || 0;
		var end = start + length;
		setTimeout(function() {
			if(tde.testparam.currentQuestion > end) {
				$('.hdr').data('sliderkit').navNext();
				var newEnd = (parseInt($('ul.test-nav').css('left').match('\\d+')) / 51) + length;
				if(tde.testparam.currentQuestion > newEnd) {
					test.navigateHeader();
				}
			} else if(tde.testparam.currentQuestion < start) {
				$('.hdr').data('sliderkit').navPrev();
				var newStart = parseInt($('ul.test-nav').css('left').match('\\d+')) / 51;
				if(tde.testparam.currentQuestion < newStart) {
					test.navigateHeader();
				}
			}
		}, 50);
	};
	
	this.finishSection = function() {
		$.ajax({
			url : contextPath + '/JSON/studentTestSection/finish.htm',
			type : 'GET',
			data : {studentTestSectionId : currentStudentTestSection.id},
			success : function(data) {
				window.location.replace(contextPath + "/testHome.htm?type=all");
			},
			error : function (jqXHR) {
				// Should we do more something here?
				logger.info( tde.testparam.username + " finish test section failure");
				logger.info( tde.testparam.username + ": "+ jqXHR.responseText);
			}
		});
	};
	
	this.updateTestStatus = function(status) {
		$.ajax({
			url : contextPath + '/JSON/studentTest/updateTestStatus.htm',
			type : 'GET',
			data : {studentTestSectionId : currentStudentTestSection.id,
					studentTestId : currentStudentTest.id,
					testSectionId : currentStudentTestSection.testSectionId,
					testSessionId : currentStudentTest.testSessionId,
					status : status,
					userName : tde.config.student.userName},
			success : function(data) {
			},
			error : function (jqXHR) {
				// Should we do more something here?
				logger.info( tde.testparam.username + " testing status update failure");
				logger.info( tde.testparam.username + ": "+ jqXHR.responseText);
			}
		});
	};
	
	this.serverstore_responsehistory = function() {
		var query = new Array();
		var path = JSON.stringify(JSON.stringify(responses.history));
		//console.log(path);
		//query.push('{"studentTestSectionId":' + currentStudentTestSection.id + ',"path":' + JSON.stringify(responses.history) +'}');
		query.push('{"path": '+path+',"studentId": ' + tde.config.student.id + ',"studentTestSectionId":' + currentStudentTestSection.id + ',"taskId":' + null + ',"foil":' + null + ',"response":' + null + ',"timeSpent":' + null + ', "respondSeconds":' + null + ', "studentTestId":' + currentStudentTest.id + '}');
		
		//console.log('['+query.join(',')+']');
		$.ajax({
			url : contextPath + '/JSON/studentresponsehistory/saveList.htm',
			type : 'POST',
			data : {//studentTestSectionId : currentStudentTestSection.id,
					//list : JSON.stringify(responses.history)},
					list : '['+query.join(',')+']'},
			success : function(data) {
			},
			error : function (jqXHR) {
				// Should we do more something here?
				logger.info( tde.testparam.username + " test section history update failure");
				logger.info( tde.testparam.username + ": "+ jqXHR.responseText);
			}
		});
		
		
	};
};