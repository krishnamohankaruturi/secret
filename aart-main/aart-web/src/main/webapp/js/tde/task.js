
var task = new function() {
	var viewAll = null;
	var userPrefTestlet = {viewAll : true, questionOnly: null, currentGroup: -1};
	var tmpAnswer = new Array();
	var maskResponseBool=false;
	var canPlay = null;
	
	this.setMaskResponses=function(val){
		maskResponseBool=val;
	};
	
	this.getTestletQuestionView = function() {
		return viewAll;
	};
	
	//var scanProperties = null;
	
	//var scanIntervalFunctionID;
	/*var scanCount = 0;
	var visibleAnswerCount = 0;
	var scanRunning = false;
	var highlightIndex = -1;
	var audioPlaying = false;
	var audioAlreadyPlayed = false;
	var resetScan = false;*/
	var testletDivScroll = null;
	
	var testletStory = {is: false, currentScreen : 0, onTestlet : true, groupNumber : null, totalLength : null};
	var testFormatCode;
	var heightAdjustmentInterval = null;
	
	this.showTask = function(questionNumber) {
		
		if (testObj.uiname == 'altTest'){
			$('.alt-content').css("height","");
			if(null != heightAdjustmentInterval)
				clearInterval(heightAdjustmentInterval);
		}
		
		$('.tabable').blur();
		tabScan.reset();
		//Important to apply the 14 pt font for innovative items.
		$('#tde-content').removeClass('fourteenpoint');
		//if disabled class on tts then remove it for other item
		$('#ttstool, #textToSpeech a:not("a.ind-tool-close")').removeClass('disabletts');
		
		var magContent = null;
		if(magnifyingGlass.getMode() != "") {
			$('.magnifying_glass_c').width($('.magnifying_glass_c').width());
			$('.magnifying_glass_c').height($('.magnifying_glass_c').height());
			magContent = $('.magnifying_glass_c');
			$('.magnifying_glass_c').remove();
		}
		
		if (questionNumber < 0) {
			questionNumber = 0;
		}
		//$(document).trigger('tracker.question', ['question']);
		
		if (questionNumber >= tasks.length) {
			questionNumber = tasks.length - 1;
			// This is the end of the test. Send to the review page.
			logger.info( tde.testparam.username + " test review");
			testletStory = {is: false, currentScreen : 0, onTestlet : true, groupNumber : null, totalLength : null};
			test.showReview(false);
			/*if(task.scanIntervalFunctionID){
				clearInterval(task.scanIntervalFunctionID);
			}*/
			//zz need this..resetMagnifyingGlass();
			return;
		}
		
		// Set active on the main question bar.
		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-active');
		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-incomplete-active');
		if( $('#goto_' + questionNumber).parent().hasClass('q-incomplete')) {
			$('#goto_' + questionNumber).parent().addClass('q-incomplete-active');
		} else if( $('#goto_' + questionNumber).parent().hasClass('q-complete') ) {
			$('#goto_' + questionNumber).parent().addClass('q-complete-active');
		} else {
			$('#goto_' + questionNumber).parent().addClass('q-active');
		}
		
		if (task.findPart(tde.testparam.currentQuestion) == task.findPart(questionNumber && parseInt(tde.testparam.currentQuestion) != parseInt(questionNumber)) ) {
			if ($("#foils_" + tde.testparam.currentQuestion).length > 0) {
				// We are moving between quesitons in a passage.	
				$("#foils_" + tde.testparam.currentQuestion).hide();
				$("#foils_" + questionNumber).show();

				// Set active on the main question bar.
				$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-active');
				$('#goto_' + questionNumber).parent().addClass('q-active');
				
				tde.testparam.currentQuestion = questionNumber;
// 				return;
				// We would want to leave here so we don't regenerate the html and loose highlighting, but one/all view makes this not work at present.
			}
		}
		
		//$('#passageQuestion_foot').css('visibility', 'hidden');
		$('#tde-content').html("");
		
		var currentSection = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]];

		tool.hideTool('rubric');
		
		if(tasks[questionNumber].rubricNeeded)
			tool.showTool('rubric'); 
			
		
		var questionLabel = 'Question';
		if(tde.config.policies !=null) {
			for (policy in tde.config.policies) {
				if(tde.config.policies[policy].name == 'question'){
					questionLabel = tde.config.policies[policy].data;
					break;
				}
			}
		}
		
		tde.testparam.currentQuestion = questionNumber;
		tasktype = tasks[questionNumber].taskType;//taskTypes[tasks[questionNumber].taskTypeId].codeName;
		tde.testparam.tabbedIndex = null;
		task.clearTmpAnswer();
		
		if(userPrefTestlet.currentGroup != task.getCurrentGroup()) {
			task.setUserPrefTestlet('questionOnly', null);
			task.setUserPrefTestlet('currentGroup', -1);
			$('#show-passage-question').addClass('active');
			$('#show-questions-only').removeClass('active');
			$('#show-passage-only').removeClass('active');
		}
		
		var spokenPrefObj = _.findWhere($.parseJSON(sessionStorage.getItem('profile'))['spoken'],{attrName: 'userspokenpreference'});
		var spokenPref = null;
		if(spokenPrefObj) {
			spokenPref = spokenPrefObj['attrValue'];
		}
		
		var testlet = null;
		if(tasks[questionNumber].testlet) {
			testlet = _.find(currentSection.testlets, function(testlet){
				if(testlet.id == tasks[questionNumber].testletId) {
					return testlet;
				}
			});
			
			if(/*testlet.stimulusNeeded &&*/ testlet.layoutCode == 'paginated') {
				testletStory.is = true;
				var groupCounts = 0;
				var orderedKeys = _.sortBy(_.keys(testlet.groups).map(function(groupNo){
				    return parseInt(groupNo);
				}), function(num) {
					return num;
				});
				if (!testletStory.groupNumber) {
					testletStory.groupNumber = orderedKeys[0];
					testletStory.totalLength = _.reduce(_.pluck(testlet.groups, 'totalLength'), function(memo, num){ return memo + num; }, 0);
				} else {
					var lens = new Array();
					for(var i=0; i<orderedKeys.length; i++) {
						lens.push(testlet.groups[orderedKeys[i]].totalLength);
					}
					
					
					for(var i=0; i<lens.length; i++) {
						groupCounts += lens[i];
						if(testletStory.currentScreen < groupCounts) {
							testletStory.groupNumber = orderedKeys[i];//_.keys(testlet.groups).sort()[i];//_.keys(testlet.groups)[i];
							groupCounts -= lens[i];
							break;
						}
					}
				}
				
				if ((testletStory.currentScreen - groupCounts) < (testlet.groups[testletStory.groupNumber].totalLength - testlet.groups[testletStory.groupNumber].taskCount)) {
					testletStory.onTestlet = true;
					
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
					$('#tde-content').append(new EJS({url: 'js/views/storyboard-base.ejs'}).render({ contextStimulus: testlet.groups[testletStory.groupNumber][stim].pages[testletStory.currentScreen - groupCounts - checkPage] }));
					
				} else {
					testletStory.onTestlet = false;
					var testletGroup = testlet.groups[testletStory.groupNumber];
					tde.testparam.currentQuestion = parseInt(currentSection.tasks['id_'+  (testletGroup.taskIds[(testletStory.currentScreen - groupCounts - (testletGroup.totalLength - testletGroup.taskCount)  )])]);
					questionNumber = tde.testparam.currentQuestion;
					tasktype = tasks[questionNumber].taskType;
				}
			}
		} else {
			viewAll = null;
		}
		
		//This if block is for LCS to use nginx to load media files
		var teQuestion = tde.config.mediaUrl+tasks[questionNumber].question;
		if(tde.config.lcsId && tde.config.lcsId.length > 0){
			var index = (teQuestion.indexOf('media') !=  -1) ? teQuestion.indexOf('media'): teQuestion.indexOf('nfs');//teQuestion.indexOf('media') || teQuestion.indexOf('nfs');
			var relativeQuestionUrl = teQuestion.substring(index);
			teQuestion = tde.config.lcsMediaUrl + relativeQuestionUrl;			
		}
		
		if ((tasktype == 'ITP' && !tasks[questionNumber].testlet) || (tasktype == 'ITP' && tasks[questionNumber].testlet && testletStory.is && !testletStory.onTestlet)) {
			$('#ttstool, #textToSpeech a:not("a.ind-tool-close")').addClass('disabletts');
			
			//US13313 - TDE Changes for Handling New API's in TE Items
			var qtiOperationalJS = qtiCustomInteractionContext.getTEIScript(questionNumber);
			if( qtiOperationalJS != null){
				task.loadCss(teQuestion+'/css/itemStyles.css');
				qtiCustomInteractionContext.updateTEI(questionNumber, responses.values[tde.testparam.currentQuestion]);
				$('#tde-content').append(qtiCustomInteractionContext.getTEIHtmlNode(questionNumber));
				$().dndPageScroll();
				//This has been added separately, because the checkbox images messing up the styles if we apply for all types like this.
				if(testObj.uiname == 'genTest') {
					$('#tde-content').addClass('fourteenpoint');
				} 
				task.applyColorsItp();

				/**
				 * Biyatpragyan Mohanty(bmohanty_sta@ku.edu) : US12332 : Task - Technology Enhanced - Mark for Review
				 * Issue : TE items were not having review link, so user could not mark them for review.
				 * Dynamically prepending the marker link to the loaded URL data from the server package.
				 */
				if (testObj.uiname != 'altTest') {
					$('#tde-content').prepend(new EJS({url: 'js/views/temarker.ejs'}).render({qNum: questionNumber, markedReview: tde.testparam.reviews}));
				}	
				$('#ttstool, #textToSpeech a:not("a.ind-tool-close")').removeClass('disabletts');
			} else {
				//$.getScript(teQuestion.replace('index.html','js/itemScript.js'), function() {
					tde.config.uiblock = true;
					$.ajax({
				         type: "GET",
				         url: teQuestion+'/js/itemScript.js',
				         dataType: "script",
				         cache: true
					}).done(function() {
						if(testObj.uiname == 'genTest') {
							$.blockUI({ message: '<h1> Loading...</h1>',
							    overlayCSS: { backgroundColor: '#C8C8C8' }
							});
						}
						task.loadCss(teQuestion+'/css/itemStyles.css');
						$('#tde-content').load(teQuestion+'/index.html', function(responseText, statusText, xhr){
			                if(statusText == "success") {
		                		$().dndPageScroll();
		                		//This has been added separately, because the checkbox images messing up the styles if we apply for all types like this.
		                		if(testObj.uiname == 'genTest') {
		                			$('#tde-content').addClass('fourteenpoint');
		                		}
		                		//Check the Values for ITP
		                		if(responses.values[tde.testparam.currentQuestion]){
		                			operationalJS.initItem(tasks[tde.testparam.currentQuestion].id,JSON.parse(responses.values[tde.testparam.currentQuestion]));
		                		}else{
		                			operationalJS.initItem(tasks[tde.testparam.currentQuestion].id);
		                		}
		                		task.applyColorsItp();
		            			
		            			/**
		            			 * Biyatpragyan Mohanty(bmohanty_sta@ku.edu) : US12332 : Task - Technology Enhanced - Mark for Review
		            			 * Issue : TE items were not having review link, so user could not mark them for review.
		            			 * Dynamically prepending the marker link to the loaded URL data from the server package.
		            			 */
		            			if (testObj.uiname != 'altTest') {
		            				$('#tde-content').prepend(new EJS({url: 'js/views/temarker.ejs'}).render({qNum: questionNumber, markedReview: tde.testparam.reviews}));
		            			}	
		            			
		            			if(/ipad/ig.test(userAgent)) {
		            				setBodyHeight();
		            			}
		            			
		            			$('#ttstool, #textToSpeech a:not("a.ind-tool-close")').removeClass('disabletts');
						}
		                else if(statusText == "error") {
		                        console.log("An error occurred: " + xhr.status + " - " + xhr.statusText);
		                        task.removeCss();
		                }
			            $.unblockUI();
		        	});
				});
			}
		} else if (((tasktype == 'MC-K' || tasktype == 'MC-S' || tasktype == 'MC-MS' || tasktype == 'T-F' || tasktype == 'ER') && (tasks[questionNumber].testlet == false || tasks[questionNumber].testlet == null)) || ((tasktype == 'MC-K' || tasktype == 'MC-S' || tasktype == 'MC-MS' || tasktype == 'T-F' || tasktype == 'ER') && tasks[questionNumber].testlet && testletStory.is && !testletStory.onTestlet)) {
			// The layout of this quesiton.  Default to 0 if we don't know.
			var layout = tasks[questionNumber].taskLayout;//taskLayouts[tasks[questionNumber].taskLayoutId || 0].codeName;
			
			if(tasktype == 'ER') {
				$('#tde-content').append(new EJS({url: 'js/views/er-question.ejs'}).render({task: tasks[questionNumber], qNum: questionNumber, answers: null, response: responses, path: contextPath, markedReview: tde.testparam.reviews, uiType: testObj.uiname, showtick: tde.testparam.showTick}));

				if (testObj.uiname == 'altTest') {
					if(testObj.contentArea == 'M') {
						CKEDITOR.replace( $('.ckeditor')[0], {
						        contentsCss : '/js/external/ckeditor/alternateUI.css',
						        toolbar: 'math'
						});
					}
					else {
						CKEDITOR.replace( $('.ckeditor')[0], {
					        contentsCss : '/js/external/ckeditor/alternateUI.css',
					        toolbar: 'nonmath'
					    });
					}
				} else if($('.ckeditor').length > 0){
					if(testObj.contentArea == 'M') {
						CKEDITOR.replace($('.ckeditor')[0], {toolbar: 'math' });
					} else {
						CKEDITOR.replace($('.ckeditor')[0], {toolbar: 'nonmath' });
					}
				}
				
				var editor = CKEDITOR.instances["answer_"+questionNumber+"_0"];
				if (responses && responses.values && responses.values[questionNumber]!=null) { 
					editor.setData(responses.values[questionNumber]);
				}
				editor.on( 'change', function(e) {
					ckeditorBlur(questionNumber, false);
					
				});
				
				setTimeout(function(){
					$('.cke_wysiwyg_frame').contents().each(function(index, item){
						var itemName = $(item).get(0).title.split(',')[1].trim();
						var editor = CKEDITOR.instances[itemName];
						if ( editor ) {
							$(item).keypress(function(e){
								ckeditorKeyPress(e);
							});
							setTimeout(function(){
								editor.on('contentDom', function() {
									var editable = editor.editable();
									editable.attachListener(editor.document, 'keypress', function(e) {
										ckeditorKeyPress(e);
									});
								});
							},750);
						}
					});
				},500);
			} else if(tasktype != 'MC-MS'){
				if(layout == 'rating_scale' || layout == 'horizontal') {
					$('#tde-content').append(new EJS({url: 'js/views/mc-rating.ejs'}).render({task: tasks[questionNumber], qNum: questionNumber, answers: null, response: responses, path: contextPath, markedReview: tde.testparam.reviews, uiType: testObj.uiname, showtick: tde.testparam.showTick, spokenPref: spokenPref}));
				} else {
					$('#tde-content').append(new EJS({url: 'js/views/question.ejs'}).render({task: tasks[questionNumber], qNum: questionNumber, layout: layout, answers: null, response: responses, path: contextPath, markedReview: tde.testparam.reviews, uiType: testObj.uiname, showtick: tde.testparam.showTick, spokenPref: spokenPref}));
				}
			} else {
				try {
					populateTmpAnswer(questionNumber);
				} catch(e) {}
				if(layout == 'rating_scale' || layout == 'horizontal') {
					$('#tde-content').append(new EJS({url: 'js/views/mc-rating.ejs'}).render({task: tasks[questionNumber], qNum: questionNumber, answers: tmpAnswer, response: responses, path: contextPath, markedReview: tde.testparam.reviews, uiType: testObj.uiname, showtick: tde.testparam.showTick, spokenPref: spokenPref}));
				} else {
					$('#tde-content').append(new EJS({url: 'js/views/question.ejs'}).render({task: tasks[questionNumber], qNum: questionNumber, layout: layout, answers: tmpAnswer, response: responses, path: contextPath, markedReview: tde.testparam.reviews, uiType: testObj.uiname, showtick: tde.testparam.showTick, spokenPref: spokenPref}));
				}
			}
			if ( Modernizr.touch ) {
				$('.scroller').jScrollPane();
				$('.scroller img').load(function() { 
					setTimeout(function () {
						$('.scroller').data('jsp').reinitialise(); 
					}, 100); 
				});
			}
			
		} else if((tasktype == 'CR' && (tasks[questionNumber].testlet == false || tasks[questionNumber].testlet == null)) || (tasktype == 'CR' && tasks[questionNumber].testlet && testletStory.is && !testletStory.onTestlet)) {
			$('#tde-content').append(new EJS({url: 'js/views/task-cr.ejs'}).render({task: tasks[questionNumber], qNum: questionNumber, response : responses.values[tde.testparam.currentQuestion], uiType: testObj.uiname, markedReview: tde.testparam.reviews, path: contextPath}));
			$('input.response').blur(function() {
				// seperate the responses with ~~~
				var answer = '';
				$('input.response').each(function() {
					answer = answer + $(this).val() + '~~~';
				});
				if(answer.replace(/~~~/g, '') == ''){
					//answer = '';
					task.clear();
				} else {
					task.recordAnswer(answer, false);
				}
			});
			
			$('input.numeric').keydown(function(evt) {
				// blocks all keys except numeric/decimal/arrows/backspace/tab/shift/minus -- 48-57/37-40/190/8/9/16/189,109
				var theEvent = evt || window.event;
				var key = theEvent.keyCode || theEvent.which;
				if(evt.shiftKey) { // allow only shift+tab
					if(key == 9) {
						return true;
					} else {
						return false;
					}
				}
				if((key>=48 && key<=57) || (key>=37 && key<=40) || key==190 || key == 110 /*|| key == 46*/ || key == 8 || key == 9 || key == 16 || (key == 109 || key == 189 || key == 173)) {
					return true;
				} else {
					return false;
				}
			});
		} else if(tasks[questionNumber].testlet && !testletStory.is) {
			var group = task.findPart(questionNumber);
			
			if(viewAll == null || userPrefTestlet.currentGroup != group) {
				userPrefTestlet.currentGroup = parseInt(group);
				if(testlet.questionViewCode == 'all_at_once') {
					viewAll = true;
				} else {
					viewAll = false;
				}
			}
			
			$('#tde-content').append(new EJS({url: 'js/views/mc-passage.ejs'}).render({tasks: tasks, qNum: questionNumber, viewAll: viewAll, message: messages, group: group, taskgroup:tde.testparam.taskgroups, contextStimulus: testlet.stimuli, markedReview: tde.testparam.reviews, response: responses, testlet : testlet, path: contextPath, showtick: tde.testparam.showTick, spokenPref: spokenPref}));
			
			$('.q-question, .q-hdr .show-q-questions').not(':has(audio), :has(video)').click(function(e) {
				makeQuestionActive($(this).parents('div.q').data('qnum'));
				
				if(tasktype == 'MC-MS') {
					populateTmpAnswer($(this).parents('div.q').data('qnum'));
				}
				$('.show-q-questions-active').removeClass('show-q-questions-active');
				$(this).closest('div.q').find('.show-q-questions').addClass('show-q-questions-active');
				
				if($(this).closest('div.q').find('.foils p').length > 0) {
					if($(this).closest('div.q').find('.foils p').is(':visible')) {
						$(this).closest('div.q').find('.foils p').hide();
					} else {
						$(this).closest('div.q').find('.foils p').show();
					}
				}
				
				if($(this).closest('div.q').find('.foils ul').length > 0) {
					if($(this).closest('div.q').find('.foils ul').is(':visible')) {
						$(this).closest('div.q').find('.foils ul').hide();
					} else {
						$(this).closest('div.q').find('.foils ul').show();
					}
				}
					
				if ( Modernizr.touch ) {
					$('.all-qWrap').data('jsp').reinitialise();
				}
				
				e.preventDefault();

				//add scroll logic here - scroll to question in the test-questions div
				var div= $('.test-questions .all-qWrap');
				if ( Modernizr.touch )
					div.data('jsp').scrollToY($(this).closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3);
				else {
					if(div.prop('scrollHeight') > div.height()) {
						div.scrollTop(($(this).closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3));				
					}
				} 
			});
			
			$('.mark-question').click(function(e) {
				makeQuestionActive($(this).parents('div.q').data('qnum'));
				
				if(tasktype == 'MC-MS') {
					populateTmpAnswer($(this).parents('div.q').data('qnum'));
				}
				e.preventDefault();
				
				//add scroll logic here - scroll to question in the test-questions div
				var div= $('.test-questions .all-qWrap');
				if ( Modernizr.touch )
					div.data('jsp').scrollToY($(this).closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3);
				else {
					if(div.prop('scrollHeight') > div.height()) {
						div.scrollTop(($(this).closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3));				
					}
				} 
			});
			
			//if(testlet.layoutCode!='passage_vertical_stacked' && userPrefTestlet.questionOnly != 'passage_questions'){
				var height = $('#tde-content').height() - 80;
				if(testlet.layoutCode!='passage_vertical_stacked') {
					$('.test-passage').css({"height":height});
					$('.test-questions').css({"height":height});
				} else {
					$('.test-passage').css({"height":height*0.4});
					$('.test-questions').css({"height":height*0.4});
				}
			//}
			
			// Add tag image
			for (var taskTag in tde.testparam.taskTags) {
				if (tde.testparam.taskTags[taskTag].group != null && tde.testparam.taskTags[taskTag].group == group) {
				    tags.replaceTag(".test-passage", tde.testparam.taskTags[taskTag]);
				}
			}
			
			if(!testlet.locked) {
				$('#view_one').click(function() {
					viewAll = false;
					task.showTask(tde.testparam.currentQuestion);
					$('#view_all').removeClass('activePassage');
					$('#view_one').addClass('activePassage');
					
					if($('#textToSpeech').is(':visible')) {
						$('#textToSpeech .tool-close').click();
						ttsAccommodation.refresh();
					} else if(tde.config.ttsrefresh) {
						ttsAccommodation.refresh();
					}
				});
				
				$('#view_all').click(function() {
					viewAll = true;
					task.showTask(tde.testparam.currentQuestion);
					$('#view_one').removeClass('activePassage');
					$('#view_all').addClass('activePassage');
					
					if($('#textToSpeech').is(':visible')) {
						$('#textToSpeech .tool-close').click();
						ttsAccommodation.refresh();
					} else if(tde.config.ttsrefresh) {
						ttsAccommodation.refresh();
					}
				});
			} else {
				$('div.linksLine').hide();
			}
			
			if(userPrefTestlet.questionOnly == 'questions_only') {
				$('.span2-nav li a').removeClass();
				$('.test-questions').show();
				$('.test-questions').removeClass('span2 span2-right span_pvertical_stacked').addClass('span1');
				$('.test-passage').hide();
				$('#show-questions-only').addClass('active');
				$('.test-questions').css({"height":$('#tde-content').height() - 80});
			} else if(userPrefTestlet.questionOnly == 'passage_only') {
				$('#show-passage-only').click();
			} else if(userPrefTestlet.questionOnly == 'passage_questions') {
				$('#show-passage-question').click();
			}else {
				if(testlet.displayViewCode == 'questions_only') {
					$('#show-questions-only').click();
				} else if(testlet.displayViewCode == 'passage_only') {
					$('#show-passage-only').click();
				}
			}
						
			if ( Modernizr.touch ) {
				
				testletDivScroll = $('.test-passage').jScrollPane();
				$('.all-qWrap').jScrollPane();
				
				$('.test-passage img').load(function() { 
					setTimeout(function () {
						$('.test-passage').data('jsp').reinitialise(); 
					}, 100); 
				});

				$('.all-qWrap img').load(function() { 
					setTimeout(function () { 
						$('.all-qWrap').data('jsp').reinitialise(); 
					}, 100); 
				});
				
				if(guideLine.getTimerScroll > 0){
					guideLine.setTimerScroll(0);
				}
			}
			
			$('.test-passage').prepend($('#guideHandle'));
			$('.test-passage').append($('#guideTop'));
			
			//add scroll logic here - scroll to question in the test-questions div
			var div= $('.test-questions .all-qWrap');
			if ( Modernizr.touch )
				div.data('jsp').scrollToY($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3);
			else {
				if(div.prop('scrollHeight') > div.height()) {
					div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3));				
				}
			}
			
			if (testObj.uiname == 'altTest') {
				if(testObj.contentArea == 'M') {
					CKEDITOR.replace( $('.ckeditor')[0], {
					        contentsCss : '/js/external/ckeditor/alternateUI.css',
					        toolbar: 'math'
					});
				}
				else {
					CKEDITOR.replace( $('.ckeditor')[0], {
				        contentsCss : '/js/external/ckeditor/alternateUI.css',
				        toolbar: 'nonmath'
				    });
				}
			} else if($('.ckeditor').length > 0){
				if(testObj.contentArea == 'M') {
					CKEDITOR.replace($('.ckeditor')[0], {toolbar: 'math' });
				} else {
					CKEDITOR.replace($('.ckeditor')[0], {toolbar: 'nonmath' });
				}
			}
			
			setTimeout(function(){
				$('.cke_wysiwyg_frame').contents().each(function(index, item){
					var itemName = $(item).get(0).title.split(',')[1].trim();
					var editor = CKEDITOR.instances[itemName];
					if ( editor ) {
						var queNum = parseInt(itemName.replace("answer_", ""));
						if (responses && responses.values && responses.values[queNum]!=null) { 
							editor.setData(responses.values[queNum]);
						} 
						
						// Reattaches events if setData is called because setData recreates the document
						setTimeout(function(){
							editor.on('contentDom', function() {
								var editable = editor.editable();
								
								editable.attachListener(editor.document, 'click', function() {
									makeQuestionActive($('#cke_' + $(this).get(0)['$'].title.split(',')[1].trim()).parents('div.q').data('qnum'));
								});
								editable.attachListener(editor.document, 'blur', function() {
									ckeditorBlur(questionNumber, true);
								});
								editable.attachListener(editor.document, 'keypress', function(e) {
									ckeditorKeyPress(e);
								});
							});
						},500);
		
						$(item).click(function() {
							makeQuestionActive($('#cke_' + $(this).get(0).title.split(',')[1].trim()).parents('div.q').data('qnum'));
						});
						
						$(item).blur(function() {
							ckeditorBlur(questionNumber, true);
						}); 
						
						$(item).keypress(function(e){
							ckeditorKeyPress(e);
						});
					}
				});
			}, 500);

		}

		/**
		 * Biyatpragyan Mohanty(bmohanty_sta@ku.edu) : US12332 : Task - Technology Enhanced - Mark for Review
		 * Issue : TE items were not having review link, so user could not mark them for review.
		 * Change below line, since I am adding the div dynamically .click not going to work. So user on/off click.
		 */
		//$('.mark-question').click(function(){
		$('.w').off('click', '.mark-question').on('click', '.mark-question',function(){
			var qnum = $(this).parents('div.q').data('qnum') || tde.testparam.currentQuestion;
			var position = jQuery.inArray(parseInt(qnum), tde.testparam.reviews);
			if (position < 0) {
				tde.testparam.reviews.push(parseInt(qnum));
				//makeQuestionActive(qnum);
				$('#goto_' + qnum).parent().removeClass('q-active');
				$('#goto_' + qnum).parent().removeClass('q-complete-active');
				$('#goto_' + qnum).parent().addClass('q-incomplete-active');
				$('#goto_' + qnum).parent().addClass('q-incomplete');
				$(this).addClass('mark-question-active');
			} else {
				tde.testparam.reviews.splice(position, position);
				$('#goto_' + qnum).parent().removeClass('q-incomplete');
				$('#goto_' + qnum).parent().removeClass('q-incomplete-active');
				if($('#goto_'+ qnum).parent().hasClass('q-complete')) {
					$('#goto_' + qnum).parent().addClass('q-complete-active');
				} else {
					$('#goto_' + qnum).parent().addClass('q-active');
				}
				$(this).removeClass('mark-question-active');
			}
			
			if(tasktype == 'MC-K' && tasks[tde.testparam.currentQuestion].testlet == true) {
				$('.show-q-questions-active').removeClass('show-q-questions-active');
				$(this).parents('ul').find('.show-q-questions').addClass('show-q-questions-active');
				$(this).closest('div.q').find('.foils ul').show();
			}
		});
		
		$('#tde-content div.foils ul li div.foil, #tde-content div.foils ul li div.tick').not(':has(audio), :has(video)').click(function(){
			if (striker.isMode("strikethroughText") || highLighter.isMode("highlight") || Eraser.isMode("highlight")) {
				return;
			}
			if(viewAll){
				makeQuestionActive($(this).parents('div.q').data('qnum'));
			}
			var foil = $(this).parent();
			
			//if(tasktype != 'MC-MS') {
			if(foil.children().find('input').is(':radio') || foil.children('input').is(':radio')) {
				$(this).parents('ul').children().find('input:radio').each(function() {
					$(this).attr('checked', false);
					$(this).next().removeClass('paginateAnswered');
					if(!tde.testparam.showTick) {
						$(this).parent().siblings('div.foil').removeClass('mark_answered');
						$(this).siblings('div.foil').removeClass('mark_answered');
					}
				});
				foil.find('input:radio').attr('checked',true);
				task.recordAnswer(foil.find('input:radio').get(0), false);
			} else {
				//console.log(task.getTmpAnswer());
				populateTmpAnswer($(this).parents('div.q').data('qnum') || tde.testparam.currentQuestion);
				//console.log("after populate");
				//console.log(tmpAnswer.length);
				
				if(foil.find('input:checkbox').attr('checked') == 'checked') {
					foil.find('input:checkbox').attr('checked', false);
					foil.find('label').removeClass('paginateAnswered');
					if(!tde.testparam.showTick) {
						foil.find('.foil').removeClass('mark_answered');
					}
					var position = jQuery.inArray(tasks[tde.testparam.currentQuestion].foils[foil.find('input:checkbox').attr('value')].id, tmpAnswer);
					tmpAnswer.splice(position, 1);
					task.storeTmpAnswerInResponses(false);
					if(tmpAnswer && tmpAnswer.length===0){
						$('#goto_'+tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
						$('#goto_'+tde.testparam.currentQuestion).parent().removeClass('q-complete');
						$('#goto_'+tde.testparam.currentQuestion).parent().addClass('q-active');
						//$('.tde-controls-clear').trigger('click');
						task.clear();
					}
					return false;
				} else {
					if((tmpAnswer.length < tasks[tde.testparam.currentQuestion].maxChoices) || (tasks[tde.testparam.currentQuestion].maxChoices < 1)) {
						//console.log("gonna select");
						//console.log(tmpAnswer);
						foil.find('input:checkbox').attr('checked', true);
						tmpAnswer.push(tasks[tde.testparam.currentQuestion].foils[foil.find('input:checkbox').attr('value')].id);
						//console.log("after select");
						//console.log(tmpAnswer.length);
					} else {
						$('body .w').append(new EJS({
							url : 'js/views/navigation-overlay.ejs'
						}).render({constraint : 'max', minChoices : null}));
						test.showAlertBox('navigation-overlay');
						return;
					}
				} 
				//if(viewAll){ 
					task.storeTmpAnswerInResponses(false);
					task.clearTmpAnswer();
					populateTmpAnswer($(this).parents('div.q').data('qnum') || tde.testparam.currentQuestion);
				//}
			}
			//if(testObj.uiname == 'genTest') {
			if(tasktype == 'MC-K' || tasktype == 'MC-S' || tasktype == 'MC-MS' || tasktype == 'T-F') {
				
					foil.find('label').addClass('paginateAnswered');
					if(!tde.testparam.showTick) {
						foil.find('.foil').addClass('mark_answered');
					}
					$('#goto_'+tde.testparam.currentQuestion).parent().removeClass('q-active');
					$('#goto_'+tde.testparam.currentQuestion).parent().addClass('q-complete-active');
					$('#goto_'+tde.testparam.currentQuestion).parent().addClass('q-complete');
				
			}
			
			striker.removeStrike(foil.children('div.foil'));
			if( (task.scanProperties && task.scanProperties.assignedsupport == 'true' && task.scanRunning) ||  $('.mark_highlight').length > 0) {
				tabScan.reset();
			}
			$(this).blur();
		});
		
		if(tasks[tde.testparam.currentQuestion].testlet == true &&  currentSection.testlets[currentSection['testletId_'+tasks[tde.testparam.currentQuestion].testletId]].stimulusNeeded == true) {
			$('.tde-controls-c ul.span2-nav').show();
		} else {
			$('.tde-controls-c ul.span2-nav').hide();
		}
		
		if (!striker.isMode("strikethroughText") && !highLighter.isMode("highlight") && !Eraser.isMode("highlight")) {
			test.addPointerCursor();
		}
		
		if(testObj.uiname == 'altTest') {
			/*if(tasktype == 'tset_stim_br' || tasktype == 'tset_stim_cb' || tasktype == 'tset_quad' || tasktype == 'tset_triad') {
				$('.nr-buttons').hide();
				$('.alt-buttons').show();
			} else {*/
				$('.alt-buttons').hide();
				$('.nr-buttons').show();
			//}
			/*if(test.canUseTool('audio_test')) {
				playAudio();
			}*/
		}
		
		postIt.hideNotes();
		for (var i=0;i<tde.testparam.postItNotes.length;i++) {
			var currentGroup = task.getCurrentGroup();
			if(tde.testparam.postItNotes[i].group != null && tde.testparam.postItNotes[i].group == currentGroup) {
				postIt.displayNote(tde.testparam.postItNotes[i].id);
			}
		}
		if(maskResponseBool && !responses.values[tde.testparam.currentQuestion]){
			maskResponses();
		}
		
		if(magnifyingGlass.getMode() != "") {
			$('#tools-arena').append(magContent);
			magnifyingGlass.resetMagnifyingGlass();
		}
		if(tde.config.ttsrefresh) {
			ttsAccommodation.refresh();
		}
		
		// Set the intial timer if we have one, if not set the wait for click
		//  1.  Is this a scanned test
		//  2.  If auto start set auto start timer otherwise set event listener for key start.
		if ( task.scanProperties && task.scanProperties.assignedsupport == 'true' ) {
			if ( task.scanProperties.automaticscaninitialdelay != "manual" ) {
				task.scanRunning = true;
				task.startScanTimeoutID = setTimeout( "tabScan.start()", task.scanProperties.automaticscaninitialdelay*1000 );
			}
		}  
		
		var adjustedAltUIHeight = 0;
		if(testObj.uiname == 'altTest') {
			if (! Modernizr.touch ) {
				$('.alt-content').css("overflow-y", "auto");
				
				
			}
			heightAdjustmentInterval = setInterval(function(){
				if(adjustedAltUIHeight == 0 && ($('.alt-footer').has('.tde-controls-save:visible').length > 0 || $('.alt-footer').has('.tde-controls-ttsPlay:visible').length > 0))
					adjustedAltUIHeight = 220; 
				else if(adjustedAltUIHeight == 0)
					adjustedAltUIHeight = 200;
				
				if ($('.alt-content').children().length > 0 && !($(document).height() > $(window).height()) && $('.alt-content').height() <  ($(window).height() - adjustedAltUIHeight)){
					$('.alt-content').css("height", ($(window).height() - adjustedAltUIHeight) + "px");
				}
			}, 100);
		}
		
		var noOfImages = $("#tde-content img:not(#tts-passage img, #guideHandle)").length;
		var noLoaded = 0;
		if(noOfImages > 0 && testObj.uiname == 'genTest') {
			$.blockUI({ message: '<h1> Loading...</h1>',
			    overlayCSS: { backgroundColor: '#C8C8C8' }
			});
		}
		
		$('#tde-content img').on('load', function(){
			noLoaded++;
			if(noOfImages == noLoaded) {
				if(testObj.uiname == 'altTest') {
					heightAdjustmentInterval = setInterval(function(){
						if(adjustedAltUIHeight == 0 && ($('.alt-footer').has('.tde-controls-save:visible').length > 0 || $('.alt-footer').has('.tde-controls-ttsPlay:visible').length > 0))
							adjustedAltUIHeight = 220; 
						else if(adjustedAltUIHeight == 0)
							adjustedAltUIHeight = 200;
						
						if ($('.alt-content').children().length > 0 && !($(document).height() > $(window).height()) && $('.alt-content').height() <  ($(window).height() - adjustedAltUIHeight)){
							$('.alt-content').css("height", ($(window).height() - adjustedAltUIHeight) + "px");
						}
					}, 100);
					$.unblockUI();
				} else {
					$.unblockUI();
				}
				
		    }
		});
		
		if(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].toolUsageCode == 'TASK_TOOLS') {
			tool.setUpTaskTools();
		}
		
		if(/ipad/ig.test(userAgent) && tasktype != 'ITP') {
			setBodyHeight();
		}

		$('.q-hdr').addClass('unselectable').find("*").addClass('unselectable');
		
		if (this.canPlay == null){
			var tmp = document.createElement('video');
			if (typeof (tmp.canPlayType) === 'function'){
				this.canPlay = {
					mp4: tmp.canPlayType('video/mp4'),
					ogv: tmp.canPlayType('video/ogg')
				};
			} else {
				// IE8 and lower are the only browsers that don't support HTML5 media
				this.canPlay = null;
			}
		}
		
		var me = this;
		$('video').each(function() {
				//$(this).get(0).setAttribute("preload", "auto");
				var v = $(this).get(0);
				
				// see if we can play the video
				if (me.canPlay != null){
					var src = v.src;
					var ext = src.substring(src.lastIndexOf('.') + 1);
					if (ext === 'ogv' && me.canPlay[ext] === ''){
						ext = '.' + ext;
						var lastIndex = src.lastIndexOf(ext, src.length - ext.length);
						if (lastIndex !== -1){
							var fileName = src.substring(0, lastIndex);
							src = fileName + '.mp4';
						}
						v.src = src;
					}
				}
				
				v.addEventListener("click", function(event){
					if($(v).prop('prefetch-done') != 'Y') {
						event.preventDefault();
						me.loadVideo(v);
						return false;
					}
				}, false);
			}
		);
	};
	
	this.loadVideo = function(video) {
		var xhr = new XMLHttpRequest();
		var src = video.src;
		
		xhr.open("GET", src, true);
		xhr.responseType = "blob";
		
		$.blockUI({
			message : '<h1 id="videoloading"> Loading... 0%</h1>',
			overlayCSS : {
				backgroundColor : '#C8C8C8'
			}
		});
		
		xhr.addEventListener("load", function() {
			if (xhr.status === 200) {
				var URL = window.URL || window.webkitURL;
				var blob_url = URL.createObjectURL(xhr.response);
				$(video).prop("org-src", $(video).attr("src")).prop('prefetch-done', 'Y');

				video.src = blob_url;

				video.autoplay = true;
			} else {
				// handle error
				video.autoplay = true;
			}

			$.unblockUI();
		}, false);

		var prev_pc = 0;
		xhr.addEventListener("progress", function(event) {
			if (event.lengthComputable) {
				var pc = Math.round((event.loaded / event.total) * 100);
				if (pc != prev_pc) {
					prev_pc = pc;
					$('#videoloading').html(' Loading... ' + pc + '%');
				}
			}
		});

		xhr.send();
	};
	this.storeAndGo = function(forward) {
		// For Fixed Lenght Adaptive tests we do not go forward until they have
		// chosen and answer.
		/*
		 * if ( ( testObj.linear || testObj.fixedLength ) &&
		 * !responses.values[tde.testparam.currentQuestion] ) { return; }
		 */
		
		var store = false;
		if(task.getTestletStory().is) {
			if(!task.getTestletStory().onTestlet) {
				store = true;
			}
		} else if(tasktype == 'ER' && tasks[tde.testparam.currentQuestion].testlet){ 
			// if blur event not handle the save
			if(CKEDITOR.instances['answer_'+tde.testparam.currentQuestion+'_0'].getData() != responses.values[tde.testparam.currentQuestion]) {
				//response not saved -- save now
				ckeditorBlur(tde.testparam.currentQuestion, true);
			}
		} else {
			store = true;
		}
		
		var removecss = false;
		if(store) {
			
			if (tasktype == 'ITP') {
				removecss = true;
				task.recordAnswer(operationalJS.scoreItem(), true);
				//US13313 - TDE Changes for Handling New API's in TE Items
				var qtiOperationalJS = qtiCustomInteractionContext.getTEIScript(tde.testparam.currentQuestion);
				if( qtiOperationalJS != null){
					$('#tde-content-tei').append(qtiCustomInteractionContext.getTEIHtmlNode(tde.testparam.currentQuestion));
				}
			} else if (tasktype == 'MC-MS') {
				if(tmpAnswer.length < tasks[tde.testparam.currentQuestion].minChoices && tasks[tde.testparam.currentQuestion].minChoices > 0) {
					$('body .w').append(new EJS({
						url : 'js/views/navigation-overlay.ejs'
					}).render({constraint : 'min', minChoices : tasks[tde.testparam.currentQuestion].minChoices}));
					test.showAlertBox('navigation-overlay');
					return;
				}
				task.storeTmpAnswerInResponses(true);
			} else if ( tasktype == 'ER' ) {
				task.recordAnswer(CKEDITOR.instances['answer_'+tde.testparam.currentQuestion+'_0'], true);
			} else {
				serverstore_responses(1, true, false);
			}
		}
		
		if((store && !task.canNavigate()) || (store && testObj.fixedLength && !task.canNavigate('adaptive'))) {
			return;
		}
		
		if(removecss) {
			task.removeCss();
		}
		if ( forward ) {
			task.showNextPage();
		} else {
			task.showPrevPage();
		}
		
		if (testObj.uiname == 'genTest') {
			test.navigateHeader();
		}
		
	};
	
	this.storeTmpAnswerInResponses = function(saveResponse) {
		var JSON_obj = {
				"operationalItem": {
					"answer": tmpAnswer
				}
			};
		task.recordAnswer(JSON.stringify(JSON_obj), saveResponse);
	};
	
	this.clearTmpAnswer = function() {
		tmpAnswer = [];
	};
	
	this.getTmpAnswer = function() {
		return tmpAnswer;
	};
	
	this.iniTmpAnswer = function(arr) {
		tmpAnswer = arr;
	};
	
	this.getTestletStory = function() {
		return testletStory;
	};
	
	this.getTestletDivScroll = function() {
		return testletDivScroll;
	};
	
	this.setupTasks = function() {
		var profile = JSON.parse(sessionStorage.getItem('profile'));
		var oskValues = profile['onscreenkeyboard'];
		if (oskValues) {
			var attrNamesArray = _.pluck(oskValues, 'attrName');
			var attrValuesArray = _.pluck(oskValues, 'attrValue');
			//Flat to this object..
			task.scanProperties = _.object(attrNamesArray, attrValuesArray);
		}
		
			var passage = null,
				current = -1;
			var isTaskShuffled = false;
			var shuffledTasks = new Array();
			var studentTaskMap = {};
			var currentSection = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]];
			var maxSectionScore = 0;
			
			if (currentSection.taskDeliveryRuleCode == 'REVERSE_ORDER') {
				tasks = tasks.reverse();
			} else if (currentSection.taskDeliveryRuleCode == 'RANDOM_ORDER' && (!currentStudentTestSection.studentsTestSectionsTasks || _.isEmpty(currentStudentTestSection.studentsTestSectionsTasks))) {
				tasks = _.shuffle(tasks);
				isTaskShuffled = true;
			}
			
			if(currentStudentTestSection.studentsTestSectionsTasks && !_.isEmpty(currentStudentTestSection.studentsTestSectionsTasks)) {
				// get task order from currentStudentTestSection
				for(var i=0; i<currentStudentTestSection.studentsTestSectionsTasks.length; i++) {
					var studentTask = currentStudentTestSection.studentsTestSectionsTasks[i];
					studentTaskMap[studentTask.taskId] = {};
					studentTaskMap[studentTask.taskId]['sortOrder'] = studentTask.sortOrder;
					if(studentTask.studentsTestSectionsTasksFoils != null 
							&& studentTask.studentsTestSectionsTasksFoils.length > 0 && studentTask.studentsTestSectionsTasksFoils[0].foilId != null) {
						studentTaskMap[studentTask.taskId]['foils'] = {};
						for(var j=0; j<studentTask.studentsTestSectionsTasksFoils.length; j++) {
							studentTaskMap[studentTask.taskId]['foils'][studentTask.studentsTestSectionsTasksFoils[j].foilId] = studentTask.studentsTestSectionsTasksFoils[j].sortOrder;
						}
					} else {
						studentTaskMap[studentTask.taskId]['foils'] = null;
					}
				}
				delete currentStudentTestSection.studentsTestSectionsTasks;
			}
			
			testFormatCode = tde.testparam.jO.testFormatCode;
			
			//US13313 - TDE Changes for Handling New API's in TE Items
			var teiCallBackCounter = 0;
			
			// Set up the select question list.
			for (tsk in tasks) {
				var isFoilsShuffled = false;
				var stims = new Array(),
					accommodations = new Array();
				
				tasks["id_" + tasks[tsk].id] = tsk; // Key for easy use.
				
				for (accommodation in tasks[tsk].accommodations) {
					accommodations[tasks[tsk].accommodations[accommodation].codeName] = tasks[tsk].accommodations[accommodation];
				}
				
				//US13313 - TDE Changes for Handling New API's in TE Items -- Uncomment the commented code inside the if condition to enable this functionality ---
				if (tasks[tsk].question != undefined && tasks[tsk].taskType == 'ITP') {
					//teiCallBackCounter++;
				}
				
				tasks[tsk].accommodations = accommodations; // Replace accommodations with associative array.
				
				/*for (stimuli in tasks[tsk].stimuli) {
					stims.push(tasks[tsk].stimuli[stimuli].id);
				}*/
				if (tasks[tsk].testlet == true) {
					stims.push(tasks[tsk].testletId);
				}
				
				var stims_key = stims.join(',') || 'mc';
				
				if (stims_key == passage) {
					tde.testparam.taskgroups[current].push(tsk); 
					tasks[tsk].grouping = passage;
				}
				else {
					passage = stims_key;
					current++;
					
					tde.testparam.taskgroups[current] = new Array();
					tde.testparam.taskgroups[current].push(tsk); 
					tasks[tsk].grouping = stims_key;
				}
				
				// check if first time in test and shuffle=true for foils
				if (tasks[tsk].shuffled && studentTaskMap[tasks[tsk].id] == null) {
					tasks[tsk].foils = _.shuffle(tasks[tsk].foils);
					isFoilsShuffled = true;
				}
				
				tasks[tsk].foilmap = new Array();
				
				for (foil in tasks[tsk].foils) {
					tasks[tsk].foilmap["map_" + tasks[tsk].foils[foil].id] = foil;
				}
				
				//dont include header if testObj.uiname=alt
				if(testObj.uiname != 'altTest') {
					var linearNavigation = _.find(testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].rules, function(rule){
													return rule.code.toLowerCase() == 'linear' && rule.navigation == true;
											});
					
					$('.test-nav').append('<li><a href="#" id="goto_' + tsk + '">' + (parseInt(tsk) + 1 + tde.testparam.softbreakCount)  + '</a></li>');
					if ( !testObj.fixedLength && !linearNavigation ) {
						$('#goto_' + tsk).click(function(){
							var num = parseInt($(this).attr('id').replace("goto_", ""));
							if(tde.testparam.qctoans == test.questionCountAnswered()){
								if(task.nextQuestionAnswered(num-1) == num) {
									task.showTask(num);
								} else {
									test.showAlertBox('navigationAlert');
								}
							} else {
								if(tasktype == 'ITP') {
									task.removeCss();
									task.recordAnswer(operationalJS.scoreItem(), true);
								} else if(tasktype == 'MC-MS') {
									if(tmpAnswer.length < tasks[tde.testparam.currentQuestion].minChoices && tasks[tde.testparam.currentQuestion].minChoices > 0) {
										$('body .w').append(new EJS({
											url : 'js/views/navigation-overlay.ejs'
										}).render({constraint : 'min', minChoices : tasks[tde.testparam.currentQuestion].minChoices}));
										test.showAlertBox('navigation-overlay');
										return;
									}
									task.storeTmpAnswerInResponses(true);
								} else if ( tasktype == 'ER' ) {
									task.recordAnswer(CKEDITOR.instances['answer_'+tde.testparam.currentQuestion+'_0'], true);
								} else {
									serverstore_responses(1, true, false);
								}
								if(!task.canNavigate()) {
									return;
								}
								task.showTask(num);
							}
						});
					} else {
						$('#goto_' + tsk).attr('disabled', true).css("cursor", "default");
					}
					
					if (responses.values[tsk] != null && responses.values[tsk] != undefined && responses.values[tsk] != '[]') {
						$('#goto_' + tsk).parent().addClass('q-complete');
					}
				}
				
				// populate Students shuffledTasks to store the order in db
				if (isTaskShuffled || isFoilsShuffled) {
					/*sortOrder -- of task
					taskId*/
					var taskEntry = '{"taskId" :'+ tasks[tsk].id + ', "sortOrder" :'+ tsk;
					
					if (isFoilsShuffled) {
						/*studentTasksFoils -- each foil
						--------------------
						foilId
						sortOrder -- of the foil */

						var foils = tasks[tsk].foils;
						var shuffledFoils = new Array();
						for (var i=0; i<foils.length; i++) {
							var foilEntry =  '{"foilId":' + foils[i].id + ', "sortOrder":' + i +'}';
							shuffledFoils.push(foilEntry);
						}
						taskEntry = taskEntry + ', "studentsTestSectionsTasksFoils":' + '['+shuffledFoils.join(',')+']';
					} else {
						taskEntry = taskEntry + ', "studentsTestSectionsTasksFoils":'+ null;
					}
					
					taskEntry = taskEntry + '}';
					shuffledTasks.push(taskEntry);
				};
				
				if(studentTaskMap[tasks[tsk].id] != null) {
					tasks[tsk].sortOrder = studentTaskMap[tasks[tsk].id].sortOrder;
					if(studentTaskMap[tasks[tsk].id].foils != null) {
						for(var foil in tasks[tsk].foils) {
							tasks[tsk].foils[foil].sortOrder = studentTaskMap[tasks[tsk].id].foils[tasks[tsk].foils[foil].id];
						}
						tasks[tsk].foils = _.sortBy(tasks[tsk].foils, function(foil) {
							return foil.sortOrder;
						});
					}
				}
				
				//Get max score of each task -- add it to the currentStudentTest object
				maxSectionScore += maxScore(tasks[tsk]);
				
			}
			
			//Push section scores info into studentTestSecton object
			//currentStudentTest.scores = [{testSectionId:xx, score:yy, maxScore:zz},{},{}]
			var testScores = $.parseJSON(currentStudentTest.scores) || new Array();
			
			if(!_.findWhere(testScores, {testSectionId : currentStudentTestSection.testSectionId})) {
				testScores.push({testSectionId: currentStudentTestSection.testSectionId, score: 0, maxScore: maxSectionScore});
			}
			currentStudentTest.scores = testScores;
			
			// Store shuffled tasks/foils in db
			if (!_.isEmpty(shuffledTasks)) {
				// store them
				$.ajax({
					url : contextPath + '/JSON/studentTestSection/storeTaskFoilOrder.htm',
					type : "POST",
					data :{ studentTestSectionId : currentStudentTestSection.id,
							testSectionId : currentStudentTestSection.testSectionId,
							testSessionId : currentStudentTest.testSessionId,
							studentsTasks : '['+shuffledTasks.join(',')+']'
					},
					success : function(data) {},
					error : function (jqXHR) {
						logger.info(tde.testparam.username+ ": not able to store task/foil order");
						logger.info(tde.testparam.username + ": " + jqXHR.responseText);
					}
				});
			}
			
			// when student comes in second time -- show him his previous order
			if(!_.isEmpty(studentTaskMap)) {
				tasks = _.sortBy(tasks, function(task) {
					return task.sortOrder;
				});
				
				$.each(tasks, function(index) {
					tasks["id_" + this.id] = index;
				});
				
			}

			if(!_.isEmpty(currentSection.testlets)) {
				var testlets = currentSection.testlets;
				var testletCount = 0;
				$.each(testlets, function(index, testlet) {
					var testletGroups = {};
					if (this.layoutCode == 'paginated') {
						var stimulusCount=0;
						$.each(this.stimuli, function() {
							if (/page-break-after\s*:\s*always/i.test(this.text)) {
								//var story = this.text.split(/page-break-after\s*:\s*always/ig);
								var text = this.text;
								text = text.replace(/page-break-after\s*:\s*always\s*.*?div>/ig,'"></div>page-break-for-tde');//replace(/page-break-after\s*:\s*always\s*.*?>/ig,'"></div>page-break-for-tde<div>');
								var story = text.split(/page-break-for-tde/);
								//for(var i=0; i < story.length; i++) {
									/*if(story[i].indexOf('<') < story[i].indexOf('>')) {
										story[i] = story[i].substring(0, story[i].lastIndexOf('<')-1);
									} else {
										story[i] = story[i].substring(story[i].indexOf('<'));
									}
									story[i] = story[i].substring(0, story[i].lastIndexOf('>')+1);*/
									
								//}
								currentSection.testlets[testletCount].stimuli[stimulusCount]['pages'] = story;
								currentSection.testlets[testletCount].stimuli[stimulusCount]['pageCount'] = story.length;
							} else {
								currentSection.testlets[testletCount].stimuli[stimulusCount]['pages'] = new Array();
								currentSection.testlets[testletCount].stimuli[stimulusCount]['pages'][0] = this.text;
								currentSection.testlets[testletCount].stimuli[stimulusCount]['pageCount']= 1;
							}
							stimulusCount++;
						});
						
						var stimuliGroups  = _.groupBy(this.stimuli, function(stimulus) {
							return stimulus.groupNumber;
						});
						 
						var testletTasks = _.filter(tasks, function(tsk) {
							return tsk.testletId == parseInt(testlet.id);
						});
						
						$.each(stimuliGroups, function(groupNumber, stimuliGroup) {
							var sortedStimuliGroup = _.sortBy(stimuliGroup, function(stimulus) {
								return stimulus.sortOrder;
							});
							testletGroups[groupNumber] = sortedStimuliGroup; 
							
							var testletGroupTasks = _.sortBy(_.filter(testletTasks, function(tsk) {
									return tsk.groupNumber == parseInt(groupNumber);
								}), function(tsk) {
									return tsk.sortOrder;
							});
							var taskCount = _.size(testletGroupTasks);
							var screenCount = _.reduce(_.pluck(this, 'pageCount'), function(memo, num){ return memo + num; }, 0);
							testletGroups[groupNumber]['taskCount'] = taskCount;
							testletGroups[groupNumber]['totalLength'] =  taskCount + screenCount;
							testletGroups[groupNumber]['taskIds'] = _.pluck(testletGroupTasks, 'id');
						});
						
						var taskGroups  = _.groupBy(testletTasks, function(task) {
							return task.groupNumber;
						});
						
						$.each(taskGroups, function(groupNumber, taskGroup) { 
							var taskFound = _.filter(testlet.stimuli, function(stimuliItem) {
								return stimuliItem.groupNumber == groupNumber;
							});
							
							if(!taskFound || taskFound.length == 0){
								testletGroups[groupNumber] = []; 
								testletGroups[groupNumber].push({groupNumber: groupNumber, pageCount : 0, pages : []});
								var taskCount = _.size(taskGroup);
								var screenCount = 0;
								testletGroups[groupNumber]['taskCount'] = taskCount;
								testletGroups[groupNumber]['totalLength'] =  taskCount + screenCount;
								testletGroups[groupNumber]['taskIds'] = _.pluck(_.sortBy(taskGroup, function(task) {
																					task.sortOrder;
																		}), 'id');
							}
						});
						currentSection.testlets[testletCount]['groups'] = testletGroups;
					}
					
					testletCount++;
				});
			}
			
			//console.log(testletGroups);
			
			//showTask(0); // Show the first question.
			//showTask(lastNavQNum);
			
			//setInterval("task.serverstore_lastNavigatedQuestion()",30000);
			setTimeout("test.localstore_responses()", tde.config.localSaveCycle); // Reset the timmer.
			
			if(tde.testparam.jO.testTypeName != 'Practice' && currentStudentTestSection.checkResponses == true) {
				// Get all the responses.
				$.ajax({
					url : contextPath + '/JSON/studentresponse/findByTestSection.htm',
					dataType : 'json',
					data : ({studentsTestId: currentStudentTest.id, testSectionId : currentStudentTestSection.id}),
					success : function(data) {
						var pending = new Array();
						for (var item in responses.answer) {
							pending.push(responses.answer[item].task);
						}
						// If the response from localstore does has something in response.task the value has not been saved to the database.
						for (var entry in data) {
							var taskId = data[entry].taskId;
							// Only update entries that are not pending upload.
							if (jQuery.inArray(taskId, pending) < 0 ) {
								if(data[entry].foil != null) {
									responses.values[tasks["id_" + taskId]] = tasks[tasks["id_" + taskId]].foilmap["map_" + data[entry].foil];
								} else {
									responses.values[tasks["id_" + taskId]] = data[entry].response;
								}
							}
							responses.responseIds[tasks["id_"+taskId]] = data[entry].studentTestSectionId + '_' + data[entry].taskId;
							if(data[entry].score != null) {
								responses.scores[tasks["id_"+taskId]] = data[entry].score;
							}
							
							if(data[entry].foil != null || (data[entry].response != null && data[entry].response != '[]')) {
								$('#goto_' + tasks["id_" + taskId]).parent().addClass('q-complete');
							}
						}
						//US13313 - TDE Changes for Handling New API's in TE Items
						if(teiCallBackCounter > 0){
							$('body').append('<div id="tde-content-tei" style="display:none;"> </div>');
							$.each(tasks, function(questionNumber, item) {
								if (tasks[questionNumber].question != undefined && tasks[questionNumber].taskType == 'ITP') {
									//This if block is for LCS to use nginx to load media files
									var teQuestion = tde.config.mediaUrl+tasks[questionNumber].question;
									if(tde.config.lcsId && tde.config.lcsId.length > 0){
										var index = (teQuestion.indexOf('media') !=  -1) ? teQuestion.indexOf('media'): teQuestion.indexOf('nfs');//teQuestion.indexOf('media') || teQuestion.indexOf('nfs');
										var relativeQuestionUrl = teQuestion.substring(index);
										//var relativeQuestionUrl = teQuestion.substring(teQuestion.indexOf('media'));
										teQuestion = tde.config.lcsMediaUrl + relativeQuestionUrl;
										
									}
									$.get(teQuestion+'/js/itemScript.js', function(itemScript) { 
										$.get(teQuestion+'/index.html', function(itemHtml, statusText, xhr){
											if(statusText == "success") { 
												teiCallBackCounter--;
												$('#tde-content-tei').append(itemHtml);
												if(itemScript.getTypeIdentifier && itemScript.getTypeIdentifier()){
													qtiCustomInteractionContext.register(itemScript);
													qtiCustomInteractionContext.setTEIId(questionNumber, $(itemHtml).attr('id'));
												} /*else {// for mock testing
													qtiCustomInteractionContext.register(itemQti);
													qtiCustomInteractionContext.setTEIId(questionNumber, $(itemHtml).attr('id'));
												}  */
												if(teiCallBackCounter == 0){
													qtiCustomInteractionContext.initialize();
													test.beginTest();
												}
											}
											else if(statusText == "error") {
												console.log("An error occurred: " + xhr.status + " - " + xhr.statusText); 
											}
										},"html");
									});
								} 
							});
						} else { 
							test.beginTest();
						} 
					},
					error : function (jqXHR) {
						// TODO We should do something witht he errors here.
						logger.info(tde.testparam.username+ ": not able to retrieve responses");
						logger.info(tde.testparam.username + ": " + jqXHR.responseText);
					}
				});
			} else {
				//US13313 - TDE Changes for Handling New API's in TE Items
				if(teiCallBackCounter > 0){
					$('body').append('<div id="tde-content-tei" style="display:none;"> </div>');
					$.each(tasks, function(questionNumber, item) {
						if (tasks[questionNumber].question != undefined && tasks[questionNumber].taskType == 'ITP') {
							//This if block is for LCS to use nginx to load media files
							var teQuestion = tde.config.mediaUrl+tasks[questionNumber].question;
							if(tde.config.lcsId && tde.config.lcsId.length > 0){
								var index = (teQuestion.indexOf('media') !=  -1) ? teQuestion.indexOf('media'): teQuestion.indexOf('nfs');//teQuestion.indexOf('media') || teQuestion.indexOf('nfs');
								var relativeQuestionUrl = teQuestion.substring(index);
								//var relativeQuestionUrl = teQuestion.substring(teQuestion.indexOf('media'));
								teQuestion = tde.config.lcsMediaUrl + relativeQuestionUrl;
								
							}
							$.get(teQuestion+'/js/itemScript.js', function(itemScript) { 
								$.get(teQuestion+'/index.html', function(itemHtml, statusText, xhr){
									if(statusText == "success") { 
										teiCallBackCounter--;
										$('#tde-content-tei').append(itemHtml);
										if(itemScript.getTypeIdentifier && itemScript.getTypeIdentifier()){
											qtiCustomInteractionContext.register(itemScript);
											qtiCustomInteractionContext.setTEIId(questionNumber, $(itemHtml).attr('id'));
										} /*else {// for mock testing
											qtiCustomInteractionContext.register(itemQti);
											qtiCustomInteractionContext.setTEIId(questionNumber, $(itemHtml).attr('id'));
										}  */
										if(teiCallBackCounter == 0){
											qtiCustomInteractionContext.initialize();
											test.beginTest();
										}
									}
									else if(statusText == "error") {
										console.log("An error occurred: " + xhr.status + " - " + xhr.statusText); 
									}
								},"html");
							});
						} 
					});
				} else { 
					test.beginTest();
				}
			}
	};
	
	this.findPart = function(questionNumber) {
		for (group in tde.testparam.taskgroups) {
			for (tsk in tde.testparam.taskgroups[group]) {
				if (tde.testparam.taskgroups[group][tsk] == questionNumber) {
					return group;
				}
			}
		}
		return 0; // So we go to the firstion question if we are lost.
	};
	
	this.recordAnswer = function(element, saveResponse) {
		var question,
		selection,
		currentTime = new Date().getTime(),
		timeSpent = currentTime - responses.lastAnswer,
		update = false;
		var score;
		
		if ( tasktype == 'ER' ) {
			question = parseInt($(element).attr('name').replace("answer_", ""));
			selection = 0;
			var responseText = CKEDITOR.instances[$(element).attr('name')].getData();
			
			var isEmptyStringAdd = false;
			if( jQuery.inArray(question, responses.responseIds) < 0 && (!responseText || responseText == null) && (responses.values[question] == null || responses.values[question] == '')) {
				isEmptyStringAdd = true;
			}
			
			if(!isEmptyStringAdd){
	        	responses.answer.push({question: question, task: tasks[question].id, foil: null, response: responseText, score: null});	        	 
				$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete').addClass('q-complete');
		        responses.history.push({task: tasks[question].id, foil: null, response: responseText, time: timeSpent, respondtime: new Date().toString(), respondseconds: currentTime});
		        
		        responses.values[question] = responseText;
		        responses.lastAnswer = responses.changed = currentTime; // Must be the last thing we do (or we might miss something in save)
	        }
		} else if(tasktype != 'ITP' && tasktype != 'MC-MS' && tasktype != 'CR') {
			question = parseInt($(element).attr('name').replace("answer_", ""));
			selection = $(element).attr('value');
			score = Number(itemScore.fetch(question, selection));
			for (ans in responses.answer) {
				//updating the response.answer if the task is already in this list
	        	if(responses.answer[ans].task == tasks[question].id) {
	        		//splice from array not done because its cost efficient to update the object than to remove and add another.
	        		responses.answer[ans].foil = tasks[question].foils[selection].id;
	        		responses.answer[ans].score = score;
	        		update = true;
	        		break;
	        	}
			}
			if(!update){
	        	responses.answer.push({question: question, task: tasks[question].id, foil: tasks[question].foils[selection].id, response: null, score: score, serverSent : false});
	        }
	        responses.history.push({task: tasks[question].id, foil: tasks[question].foils[selection].id, response: null, time: timeSpent, respondtime: new Date().toString(), respondseconds: currentTime});
	        
	        responses.values[question] = selection;
	        responses.lastAnswer = responses.changed = currentTime; // Must be the last thing we do (or we might miss something in save)
	        responses.scores[question] = score;
		} else if(tasktype == 'ITP' || tasktype == 'MC-MS') {
			var jElem = $.evalJSON(element)['operationalItem']['answer'];
			var responseText = JSON.stringify(jElem);
			responseText = (responseText != '[]') ? responseText : null;
			var answered = isItemAnswered(jElem, tasktype);
			question = tde.testparam.currentQuestion;
			if (answered || (!answered && responses.values[question])) {
				score = Number(itemScore.fetch(question, jElem));
				for (ans in responses.answer) {
					if(responses. answer[ans].task == tasks[question].id) {
						responses.answer[ans].response = responseText;//JSON.stringify(jElem['operationalItem']['answer']);
						responses.answer[ans].score = score;
						update = true;
						break;
					}
				}
				
				if(!update) {
					responses.answer.push({question: question, task: tasks[question].id, foil: null, response: responseText, score: score, serverSent : false});
				}
				responses.history.push({task: tasks[question].id, foil: null, response: responseText, time: timeSpent, respondtime: new Date().toString(), respondseconds: currentTime});
				responses.values[question] = responseText;//JSON.stringify(jElem['operationalItem']['answer']);
				responses.lastAnswer = responses.changed = currentTime;
				responses.scores[question] = score;
				
				if((tasktype == 'ITP') && answered) {
					$('#goto_' + tde.testparam.currentQuestion).parent().addClass('q-complete');
				} else if((tasktype == 'ITP') && !answered) {
					$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete');
				}
			}
		} else if(tasktype == 'CR') {
			var responseText = element;
			if((responseText != '') || (responseText == '' && responses.values[tde.testparam.currentQuestion] != undefined)) {
				question = tde.testparam.currentQuestion;
				score = Number(itemScore.fetch(question, responseText));
				for (ans in responses.answer) {
					if(responses. answer[ans].task == tasks[question].id) {
						responses.answer[ans].response = responseText;
						responses.answer[ans].score = score;
						update = true;
						break;
					}
				}
				if(!update) {
					responses.answer.push({question: question, task: tasks[question].id, foil: null, response: responseText, score: score, serverSent : false});
				}
				responses.history.push({task: tasks[question].id, foil: null, response: responseText, time: timeSpent, respondtime: new Date().toString(), respondseconds: currentTime});
				responses.values[question] = responseText;
				responses.lastAnswer = responses.changed = currentTime;
				responses.scores[question] = score;
				if(responseText != '') {
					$('#goto_' + tde.testparam.currentQuestion).parent().addClass('q-complete');
				} else {
					$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete');
				}
			}
		}
		if(saveResponse)
			serverstore_responses(1, true, false);
				
	};
	
	this.showNextPage = function(){
		if(tasktype == 'tset_stim_br' || tasktype == 'tset_stim_cb' || tasktype == 'tset_triad' || tasktype == 'tset_quad') {
			tde.config.stim_flags.nextPageInterval = 0;
			tde.config.stim_flags.pause = '';
			if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].completion) {
				tde.testparam.currentScreen = tde.config.taskSetFlow[tasktype].ini;
			} else {
				tde.testparam.currentScreen++;
			}
			if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].ini) {
				task.showTask(tde.testparam.currentQuestion + 1);
			} else {
				task.showTask(tde.testparam.currentQuestion);		
			}
			
			if(tasktype == 'tset_quad') {
				$('.displayOutline #backb').show();
			}
		} /*else if(tasks[tde.testparam.currentQuestion].testlet == true && testletStory.is == true) { 
			if(testletStory.currentScreen < testletStory.story.length) {
				testletStory.currentScreen++;
				task.showTask(tde.testparam.currentQuestion);
			} else if(testletStory.currentScreen > (testletStory.story.length-1) && testletStory.currentScreen < (testletStory.story.length + tde.testparam.taskgroups[task.getCurrentGroup()].length - 1)) {
				testletStory.currentScreen++;
				task.showTask(tde.testparam.currentQuestion + 1);
			} else {
				task.showTask(tde.testparam.currentQuestion + 1);
				testletStory = {is: false, currentScreen : 0, onTestlet : true, story : null};
			}
		}  */
		else if(tasks[tde.testparam.currentQuestion].testlet == true && testletStory.is == true) {
			
			if(testletStory.currentScreen < (testletStory.totalLength-1) ) {
				testletStory.currentScreen++;
				task.showTask(tde.testparam.currentQuestion);
			} else {
				testletStory = {is: false, currentScreen : 0, onTestlet : true, groupNumber : null, totalLength : null};
				task.showTask(tde.testparam.currentQuestion + 1);
			}
		}
		else {
			if(tde.testparam.qctoans == test.questionCountAnswered() && ((tde.testparam.currentQuestion + 1) < tasks.length)){
				if(task.nextQuestionAnswered(tde.testparam.currentQuestion) != tde.testparam.currentQuestion) {
					task.showTask(task.nextQuestionAnswered(tde.testparam.currentQuestion));	
				} else{
					test.showAlertBox('navigationAlert');
				}
			} else {
				task.showTask(tde.testparam.currentQuestion + 1);
			}
		}
	};
	
	this.showPrevPage = function(){
		if(tasktype == 'tset_quad') {
			if(tde.testparam.currentScreen == 1) {
				tde.testparam.currentScreen = 0;
				task.showTask(tde.testparam.currentQuestion - 1);
			} else {
				if(tde.testparam.currentScreen > 1) {
					tde.testparam.currentScreen --;
				}
				task.showTask(tde.testparam.currentQuestion);
			}
		} else if(tasks[tde.testparam.currentQuestion].testlet == true && testletStory.is == true) {
			if(testletStory.currentScreen < testletStory.totalLength && testletStory.currentScreen > 0) {
				testletStory.currentScreen--;
				task.showTask(tde.testparam.currentQuestion);
			} else {
				testletStory = {is: false, currentScreen : 0, onTestlet : true, groupNumber : null, totalLength : null};
				task.showTask(tde.testparam.currentQuestion - 1);
			}
			
		} else {
			if(tde.testparam.qctoans == test.questionCountAnswered()){  
				if(prevQuestionAnswered(tde.testparam.currentQuestion) != tde.testparam.currentQuestion){
					task.showTask(prevQuestionAnswered(tde.testparam.currentQuestion));
				} else {
					test.showAlertBox('navigationAlert');
				}
			} else{
				task.showTask(tde.testparam.currentQuestion - 1);
			}
		}
	};
	
	/**
	 *  play audio associated with the task step
	 */	
	/*function playAudio() {
		if(tasktype != 'tset_stim_br' && tasktype != 'tset_stim_cb'){
			setAndPlay();
		} else if(tasktype == 'tset_stim_br') {
			if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].cue) {
				setAndPlay();
			} else if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].question) {
				task.highlightAndPlay('TDE/audio/task/'+$(tasks[tde.testparam.currentQuestion].question).find('.audio').data('audio'), $('.stem'), $('#testAudio'), 'reading_highlight');
			} else if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].stimuli) {
				stimStart();
			}
		} else if(tasktype == 'tset_stim_cb') {
			if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].stimuli) {
				stimStart();
			} else if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].question) {
				task.highlightAndPlay('TDE/audio/task/'+$(tasks[tde.testparam.currentQuestion].question).find('.audio').data('audio'), $('.stem'), $('#testAudio'), 'reading_highlight');
			}
		}
	}
	
	function setAndPlay() {
		var fileToPlay = tde.config.mediaUrl + "TDE/audio/task/";
		var screenName = getScreenName(tasktype, tde.testparam.currentScreen);
		if(screenName != 'stimuli') {
			fileToPlay = fileToPlay + $(tasks[tde.testparam.currentQuestion][screenName]).find('span.audio').data('audio');
		} else {
			fileToPlay = tde.config.mediaUrl + "TDE/audio/stimulus/" + $(tasks[tde.testparam.currentQuestion]['contextStimulus'].text).find('span.audio').data('audio'); //tasks[tde.testparam.currentQuestion].stimuli[0].id;
		}
		$("#testAudio").jPlayer("setMedia", {
		 	mp3: fileToPlay + '.mp3',
		 	oga: fileToPlay + '.ogg'
		});
		$('#testAudio').jPlayer("play");
	}*/
	
	/**
	 * starts reading the stimulus..
	 */
	/*function stimStart() {
		var stimSentences = $('.stimuli .audio');
		if(stimSentences.length > 0) {
			tde.config.stim_flags.audioCount = 0;
			task.highlightAndPlay('TDE/audio/stimulus/'+$(stimSentences[0]).data('audio'), $(stimSentences[0]), $('#testAudio'), 'reading_highlight');
		} else if($('video').length) {
			$('video').get(0).play();
		} else {
			if($('#tde-content').is(':visible')){
				tde.config.stim_flags.nextPageInterval = setTimeout("task.showNextPage()", 3000);
			}
		}
	}
	
	*//**
	 * highlight a node and plays the audio file.
	 *//*
	this.highlightAndPlay = function(audio, node, player, cls) {
		player.jPlayer("setMedia", {
		 	mp3: tde.config.mediaUrl + audio + '.mp3',
		 	oga: tde.config.mediaUrl + audio + '.ogg'
		});
		player.jPlayer("play");
		node.addClass(cls);
	};*/
	
	/**
	 * replay plays all the steps on the question page
	 * of tset_stim_br and tset_stim_cb
	 */
	/*this.replayAll = function() {
		var markedRead = $('.mark_read');
		$(markedRead).each(function(){
			$(this).removeClass('mark_read');
		});
		$('.reading_highlight').removeClass('reading_highlight');
		tde.config.stim_flags.toRead = 'stimuli';
		tde.config.stim_flags.secondPass = true;
		if(tde.config.stim_flags.interval){
			clearInterval(tde.config.stim_flags.interval);
		}
		var stimSentences = $('.stimuli .audio');
		if(stimSentences.length > 0) {
			tde.config.stim_flags.audioCount = 0;
			task.highlightAndPlay('TDE/audio/stimulus/'+$(stimSentences[0]).data('audio'), $(stimSentences[0]), $('#testAudio'), 'reading_highlight');
		} else if($('video').length) {
			$('video').get(0).load();
			$('video').get(0).play();
		} else {
			tde.config.stim_flags.toRead='question';
			task.doNextSteps();
		}
	};*/
	
	/**
	 * it is called if stimulus is a media file and somethings needs to be done 
	 * after the media ends.
	 */
	/*this.onStimMediaEnd = function(){
		if(tasktype == 'tset_stim_br') {
			if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].stimuli){
				tde.config.stim_flags.nextPageInterval = setTimeout("task.showNextPage()", 3000);
			} else if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].question) {
				tde.config.stim_flags.toRead = 'question';
				task.doNextSteps();
			}
		}
	};*/
	
	/**
	 * Follows the task flow after the audio stops playing
	 * Called from jplayer library..'ended' event listner
	 */
	/*this.doNextSteps = function() {
		if ( task.scanProperties && task.scanProperties.assignedsupport ) {
			task.audioPlaybackComplete = true;
		} else if(tasktype == 'tset_stim_br' || tasktype == 'tset_stim_cb') {
			if((tasktype == 'tset_stim_br') && tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].cue) {
				tde.config.stim_flags.nextPageInterval = setTimeout("task.showNextPage()", 3000);
			} //else 
			if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].stimuli) {
				var nodeRead = $('.reading_highlight');
				$(nodeRead).removeClass('reading_highlight');
				$(nodeRead).addClass('mark_read');
				var stimSentences = $('.stimuli .audio');
				if(stimSentences.length != $('.mark_read').length) {
					tde.config.stim_flags.audioCount++;
					task.highlightAndPlay('TDE/audio/stimulus/'+$(stimSentences[tde.config.stim_flags.audioCount]).data('audio'), $(stimSentences[tde.config.stim_flags.audioCount]), $('#testAudio'), 'reading_highlight');
				} else{
					tde.config.stim_flags.nextPageInterval = setTimeout("task.showNextPage()", 3000);
				}
			} else if(tde.testparam.currentScreen == tde.config.taskSetFlow[tasktype].question) {
				var nodeRead = $('.reading_highlight');
				$(nodeRead).removeClass('reading_highlight');
				if(tde.config.stim_flags.toRead =='options'){
					//var answers = $('li.hidden');
					var foils = $('.foil');
					var playBackTime;
					//if(answers.length == foils.length) {
					if($('.mark_read').length > 0) {
						playBackTime = 2000;
					} else {
						playBackTime = 3000;
					}
					if(foils.length != $('.mark_read').length) {
						tde.config.stim_flags.interval = setTimeout(function(){	
							$('.foils').show();
							
							$(foils[tde.config.stim_flags.foil]).addClass('mark_read');
							var foilaudio;
							if(typeof $(tasks[tde.testparam.currentQuestion].foils[$(foils[tde.config.stim_flags.foil]).siblings('input').val()].text).find('span.audio').data('audio') === 'undefined') {
								foilaudio =  $(tasks[tde.testparam.currentQuestion].foils[$(foils[tde.config.stim_flags.foil]).siblings('input').val()].text).data('audio');
							} else {
								foilaudio =  $(tasks[tde.testparam.currentQuestion].foils[$(foils[tde.config.stim_flags.foil]).siblings('input').val()].text).find('span.audio').data('audio');
							}
							task.highlightAndPlay('TDE/audio/task/'+ foilaudio, $(foils[tde.config.stim_flags.foil]), $('#testAudio'), 'reading_highlight');
							tde.config.stim_flags.foil++;
					}, playBackTime);
					} else{
						if(tde.config.stim_flags.secondPass == false) {
							tde.config.stim_flags.toRead = 'question';
							task.doNextSteps();
						} else {
							tde.config.stim_flags.toRead = 'stimuli';
						}
					}
				} else if(tde.config.stim_flags.toRead == 'question') {
					tde.config.stim_flags.interval = setTimeout(function(){
						task.highlightAndPlay('TDE/audio/task/'+$(tasks[tde.testparam.currentQuestion].question).find('span.audio').data('audio'), $('.stem'), $('#testAudio'), 'reading_highlight');
						tde.config.stim_flags.foil = 0;
						if(tde.config.stim_flags.secondPass == false) { tde.config.stim_flags.toRead = 'stimuli';}
						else {tde.config.stim_flags.toRead = 'options';}
					},3000);
				} else if(tde.config.stim_flags.secondPass == false && tde.config.stim_flags.toRead == 'stimuli'){
					$('#replayButton').show();
				} else if(tde.config.stim_flags.secondPass == true && tde.config.stim_flags.toRead == 'stimuli') {
					$(nodeRead).addClass('mark_read');
					var stimSentences = $('.stimuli .audio');
					if(stimSentences.length != $('.mark_read').length) {
						tde.config.stim_flags.audioCount++;
						task.highlightAndPlay('TDE/audio/stimulus/'+$(stimSentences[tde.config.stim_flags.audioCount]).data('audio'), $(stimSentences[tde.config.stim_flags.audioCount]), $('#testAudio'), 'reading_highlight');
					} else{
						tde.config.stim_flags.toRead = 'question';
						var markedRead = $('.mark_read');
						$(markedRead).each(function(){
							$(this).removeClass('mark_read');
						});
						task.doNextSteps();
					}
				}
			}
		}
	};*/
	
	/**
	 * Load the given css file dynamically.
	 * @param file
	 */
	this.loadCss = function(file) {
		$('<link rel="stylesheet" type="text/css" href="'+file+'" >')
		   .appendTo("head");
	};

	/**
	 * Remove the css file added for innovative items.
	 */
	this.removeCss = function(url) {
		if(url){
			$('link').each(function(){
			if($(this).attr('href').match(url)) {
					$(this).remove();
			}
			});
		}else{
			$('link').each(function(){
				if($(this).attr('href').match('css/itemStyles.css')) {
					$(this).remove();
				}
			});
		}
	};
	
	this.serverstore_lastNavigatedQuestion = function() {
		if(tde.testparam.testTypeName == 'Practice'){
			return;
		} else if(currentStudentTestSection.lastNavQNum == tde.testparam.currentQuestion || tde.testparam.currentQuestion == -1){
			return;
		} else{
			$.ajax({
				url : contextPath + '/JSON/studentTestSection/updateLastNavQNum.htm',
				type : 'GET',
				data : {lastNavQNum : tde.testparam.currentQuestion, 
					studentTestSectionId : currentStudentTestSection.id,
					userName : tde.config.student.userName},
				success : function(data) {
					currentStudentTestSection.lastNavQNum = tde.testparam.currentQuestion;
				},
				error : function (jqXHR) {
					// Should we do more something here?
					logger.info( tde.testparam.username + " test save last question navigation failure");
					logger.info( tde.testparam.username + ": "+ jqXHR.responseText);
				}
			});
		}
	};
	
	/*function span_wrap (node, className) {
		if (node.children().length) {
			node.children().each(function () {
				span_wrap($(this), className);
			});
		} else {
			if (node.text() == '') {
				return;
			}
			
			var list = node.text().split(' ');
			node.html('');
			for (var i = 0, len = list.length; i < len; i++) {
				node.append('<span class="' + className + '">' + list[i] + '</span> ');
			}
		}
	}*/
	
	this.nextQuestionAnswered = function (questionNum) {
		//for(var r in responses.values){
		for(var i=questionNum+1; i<responses.values.length; i++) {
			if(responses.values[i] != null){
				questionNum=i;
				break;
			}
		}
		return questionNum;
	};

	function prevQuestionAnswered(questionNum) {
		for(var i=questionNum-1; i>=0; i--){
			if(responses.values[i] != null){
				questionNum=i;
				break;
			}
		}
		return questionNum;
	}
	
	/**
	 * Gets the screen name to show in taskSet items
	 * @return {screenName}
	 */
	/*function getScreenName(ttype, screen) {
		var taskflow = tde.config.taskSetFlow[ttype];
		var ret;
		for(var key in taskflow) {
			if(screen == taskflow[key]) {
				ret = key;
				break;
			}
		}
		return ret;
	}*/
	
	/*this.scanFocus = function() {
		var tabables = $('.foil:visible');
		var index;// = getNextTabElem(tabables);
		
		index = getNextTabElem(tabables);
		
		if(index!=-1){
			$(tabables[tde.testparam.tabbedIndex]).blur();
			$(tabables[index]).focus();
		}
		
		tde.testparam.tabbedIndex = index;
	}
	
	*//**
	 * This function is called if step scan accommodation is set.
	 *//*
	this.tabFocus = function(key) {
		var tabables = $('.tabable:not(.magnified-content .tabable)');
		var index;// = getNextTabElem(tabables);
		
		if(key == 'tab') {
			index = getNextTabElem(tabables);
		} else {
			index = getPrevTabElem(tabables);
		}
		
		if(index!=-1){
			$(tabables[tde.testparam.tabbedIndex]).blur();
			$(tabables[index]).focus();
		}
		tde.testparam.tabbedIndex = index;
	};*/

	/**
	 * Gets the next element to be tabbed to from the list of tabable elements.
	 * @returns {Number}
	 */
	/*function getNextTabElem(tabElems) {
		var nextElemIndex = -1;
		var smallestIndex = 0;
		if(tde.testparam.tabbedIndex== null){
			nextElemindex=0;
		}
		for(var i=0; i<tabElems.length; i++) {
			if($(tabElems[i]).attr('tabindex') < $(tabElems[smallestIndex]).attr('tabindex')) {
				if($(tabElems[i]).is(':visible')){
					smallestIndex = i;
				}
			}
			if(nextElemIndex == -1) {
				if($(tabElems[i]).attr('tabindex') > $(tabElems[tde.testparam.tabbedIndex]).attr('tabindex')) {
					if($(tabElems[i]).is(':visible')){
						nextElemIndex = i;
					}
				}
			} else {
				if(($(tabElems[i]).attr('tabindex') < $(tabElems[nextElemIndex]).attr('tabindex')) && i!= tde.testparam.tabbedIndex) {
					if($(tabElems[i]).is(':visible')){
						nextElemIndex = i;
					}
				}
			}
		}
		if(nextElemIndex == -1){
			if($(tabElems[smallestIndex]).is(':visible')) {
				nextElemIndex = smallestIndex;
			}
			for(var i=0; i<tabElems.length; i++) {
				if($(tabElems[i]).attr('tabindex') < $(tabElems[smallestIndex]).attr('tabindex')) {
					if($(tabElems[i]).is(':visible')){
						smallestIndex = i;
					}
				}
			}
			nextElemIndex = smallestIndex;
		}
		return nextElemIndex;
	};
	
	function getPrevTabElem(tabElems) {
		//var tabElems = $('.tabable');
		var prevElemIndex = -1;
		var smallestIndex = 0;
		if(tde.testparam.tabbedIndex== null){
			prevElemindex=0;
		}
		for(var i=0; i<tabElems.length; i++) {
			if($(tabElems[i]).attr('tabindex') < $(tabElems[smallestIndex]).attr('tabIndex')) {
				if($(tabElems[i]).is(':visible')){
					smallestIndex = i;
				}
			}
			if(prevElemIndex == -1) {
				if($(tabElems[i]).attr('tabindex') < $(tabElems[tde.testparam.tabbedIndex]).attr('tabindex')) {
					if($(tabElems[i]).is(':visible')){
						prevElemIndex = i;
					}
				}
			} else {
				if(($(tabElems[i]).attr('tabindex') > $(tabElems[prevElemIndex]).attr('tabindex')) && i!= tde.testparam.tabbedIndex && ($(tabElems[i]).attr('tabindex') < $(tabElems[tde.testparam.tabbedIndex]).attr('tabindex'))) {
					if($(tabElems[i]).is(':visible')){
						prevElemIndex = i;
					}
				}
			}
		}
		if(prevElemIndex == -1){
			for(var i=0; i<tabElems.length; i++) {
				if($(tabElems[i]).attr('tabindex') > $(tabElems[smallestIndex]).attr('tabIndex')) {
					if($(tabElems[i]).is(':visible')){
						smallestIndex = i;
					}
				}
			}
			prevElemIndex = smallestIndex;
		}
		return prevElemIndex;
	};*/
	
	function maskResponses() {
		var responses = $('div.foil');
		$(responses).each(function() {
			var resChildren = this.childNodes;
			var maskDiv = $('<div/>')
							.addClass('relativePosition maskDiv');
			
			var imgCloser = $('<img/>')
							.attr('src', 'images/closebox.png')
							.click(function(){
								var ch = this.parentNode.childNodes;
								var length = ch.length;
								for(var i=0; i<length; i++) {
									if($(ch[0]).hasClass('maskResponse')) {
										$(ch[0]).remove();
									} else {
										$(ch[0]).parent().parent().append($(ch[0]));
									}
								}
								$(this).parent().children('div.maskDiv').remove();
								$(this).remove();
								return false;
							})
							.addClass('maskResponseCloser');
			
			var imgMask = $('<img/>')
							.attr('src', 'images/black.png')
							.addClass('maskResponse');
			
			$(maskDiv).append(imgCloser);
			$(maskDiv).append(imgMask);
			var length = resChildren.length;
			for(var i=0; i<length; i++) {
				$(maskDiv).append(resChildren[0]);
			}
			$(this).append(maskDiv);
		});
	};
	
	/*
	  * Adds a class to the focused element - for Tab selection of Ans 
	  * @param e - element
	  */
	/*this.focusOnElement = function(e){
		var foilid;
		if(tasktype == 'tset_stim_br' || tasktype == 'tset_stim_cb') {
			$(e).parents('.foil').addClass("reading_highlight");
		} else {
			foilaudio = $(tasks[tde.testparam.currentQuestion].foils[$(e).parents('li').children('input').val()].text).find('span.audio').data('audio');
			$('#testAudio').jPlayer("stop");
			task.highlightAndPlay('TDE/audio/task/'+foilaudio, $(e).parents('.foil'), $('#foilAudio'), 'focussedAnswer');
		}
	};
	 Remove class from element when not focused 
	this.blurElement = function(e){
		if(tasktype == 'tset_stim_br' || tasktype == 'tset_stim_cb') {
			$(e).parents('.foil').removeClass("reading_highlight");
		} else {
			$(e).parents('.tabui').removeClass("focussedAnswer");
		}
	};*/
	/* Mark Answer on hitting return key - for return key selection of Ans */
	/*function markAnswer(elem, event){
		if(tasktype == 'tset_quad' || tasktype == 'tset_triad'){
			$(elem).parents('.foils').find('input:checked').parents('.tabui').removeClass("markedAnswer");
			$(elem).parents('.foils').find('input:checked').attr('checked', false);
			$(elem).parents('.tabui').addClass("markedAnswer");
			$(elem).parents('.foil').siblings('input').attr("checked", !$(elem).parents('.foil').siblings('input').attr("checked"));
		} else {
			$(elem).parents('.foils').find('input:checked').siblings('.foil').removeClass('mark_highlight');
			$(elem).parents('.foils').find('input:checked').attr('checked', false);
			$(elem).parents('.foil').siblings('input').attr("checked", !$(elem).parents('.foil').siblings('input').attr("checked"));
			Custom.clear();
			$(elem).parents('.foil').addClass('mark_highlight');
			if(tde.config.stim_flags.interval){
	    		clearInterval(tde.config.stim_flags.interval);
	    	}
			$('#testAudio').jPlayer('stop');
			tde.config.stim_flags.nextPageInterval = setTimeout("task.showNextPage()", 3000);
		}
		task.recordAnswer($(elem).parents('.foil').siblings('input'), false);
		if(tasktype == "tset_triad") {
	    	task.showNextPage();
	    }
		event.stopPropagation();
		
		if ( scanIntervalFunctionID != null ) {
			clearInterval(scanIntervalFunctionID);
			scanIntervalFunctionID = null;
		}
	}*/
	
	this.getCurrentGroup = function() {
		var currentGroup;
		for (var i=0;i<tde.testparam.taskgroups.length;i++) {
			if (tde.testparam.taskgroups[i].indexOf(tde.testparam.currentQuestion+'') != -1)
				currentGroup = i;
		}
		return currentGroup;
	};
	
	function makeQuestionActive(qNum) {
		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-active');
		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-incomplete-active');
		
		if( $('#goto_' + qNum).parent().hasClass('q-incomplete')) {
			$('#goto_' + qNum).parent().addClass('q-incomplete-active');
		} else if( $('#goto_' + qNum).parent().hasClass('q-complete') ) {
			$('#goto_' + qNum).parent().addClass('q-complete-active');
		} else {
			$('#goto_' + qNum).parent().addClass('q-active');
		}
		
		tde.testparam.currentQuestion = qNum;
		tasktype = tasks[qNum].taskType;
	}
	
	this.setUserPrefTestlet = function(property, value) {
		userPrefTestlet[property] = value;
	};
	
	this.clear = function() {
		var currentTime = new Date().getTime();
		var timeSpent = currentTime - responses.lastAnswer;

		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
		$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete');
		if ($('#goto_' + tde.testparam.currentQuestion).parent().hasClass('q-incomplete')) {
			$('#goto_' + tde.testparam.currentQuestion).parent().addClass('q-incomplete-active');
		} else {
			$('#goto_' + tde.testparam.currentQuestion).parent().addClass('q-active');
		}
		if (tasktype != 'ITP' && tasktype != 'ER') {
			var foils;
			if (tasks[tde.testparam.currentQuestion].testlet == true) {
				foils = $('#tde-content').find("[data-qnum='" + tde.testparam.currentQuestion + "']").find('input');
			} else {
				foils = $('#tde-content input');
			}
			$(foils).each(function() {
				$(this).attr('checked', false);
				$(this).next().removeClass('paginateAnswered');
				task.clearTmpAnswer();
			});
			$('input.response').val('');
		} else if(tasktype == 'ER') {
			CKEDITOR.instances["answer_"+tde.testparam.currentQuestion+"_0"].setData("");
			$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete');
			$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
		}else {
			operationalJS.resetItem();
			// handleReset();
			$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete');
			$('#goto_' + tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
		}
		var answerFound = _.findWhere(responses.answer, {question : tde.testparam.currentQuestion, task : tasks[tde.testparam.currentQuestion].id});
		if ( answerFound != undefined && null != answerFound && null != responses.responseIds[answerFound.question]){//clear the response for existing saved response after modify the response
			answerFound.foil = null;
			answerFound.response = null;
			answerFound.score = null;
		}
		else if ( answerFound != undefined && null != answerFound && null == responses.responseIds[answerFound.question]){//remove the response for newly added
			for (var i in responses.answer) {
				if(responses.answer[i].task == answerFound.task){
					responses.answer.splice(i, 1);
				}
			}
		} else { //clear the reponse for existing saved response
			responses.answer.push({
				question : tde.testparam.currentQuestion,
				task : tasks[tde.testparam.currentQuestion].id,
				foil : null,
				response : null,
				score : null
			});
		}
		responses.history.push({
			task : tasks[tde.testparam.currentQuestion].id,
			foil : null,
			response : null,
			time : timeSpent,
			respondtime : new Date().toString(),
			respondseconds : currentTime
		});

		responses.values[tde.testparam.currentQuestion] = null;
		responses.lastAnswer = responses.changed = currentTime;
		responses.scores[tde.testparam.currentQuestion] = null;
		// Must be the last thing we do (or we might miss something in save)
		serverstore_responses(1, true, false);
	},
	
	this.applyColorsItp = function() {
		if($('body').hasClass('contrast_black_white')) {
			if(testObj.uiname=='altTest') {
				$('body').css('background','#000');
			}
			$('#tde-content').css('background','#000');
			$('#tde-content *').css('color','#fff');
			$('#tde-content * :not(.well .line)').css('background','#000');
		} else if($('body').hasClass('contrast_black_yellow')) {
			$('#tde-content * :not(.well .line)').css('background','#000');
			$('#tde-content * :not(well)').css('color','#efee79');
		} else if($('body').hasClass('contrast_black_gray')) {
			$('#tde-content * :not(.well .line)').css('background','#000');
			$('#tde-content * :not(well)').css('color','#999999');
		} else if($('body').hasClass('contrast_white_green')) {
			$('#tde-content * :not(.well .line)').css('background','#fff');
			$('#tde-content * :not(well)').css('color','#3b9e24');
		} else if($('body').hasClass('contrast_white_red')) {
			$('#tde-content * :not(.well .line)').css('background','#fff');
			$('#tde-content * :not(well)').css('color','#c62424');
		}
		
		if($('body').hasClass('overlay_light_blue')) {
			$('#tde-content * :not(.well .line)').css('background','#87cffd');
		} else if($('body').hasClass('overlay_light_yellow')) {
			$('#tde-content * :not(.well .line)').css('background','#f5f2a4');
		} else if($('body').hasClass('overlay_light_gray')) {
			$('#tde-content * :not(.well .line)').css('background','#c5c5c5');
		} else if($('body').hasClass('overlay_light_pink')) {
			$('#tde-content * :not(.well .line)').css('background','#f2c5c5');
		} else if($('body').hasClass('overlay_light_green')) {
			$('#tde-content * :not(.well .line)').css('background','#bef9c3');
		}
	};
	
	function isItemAnswered(response, taskType) {
		var answered = false;
		if(taskType == 'ITP') {
			try {
				if(response[0].options) {
					var length = _.reduce(_.pluck(response, 'options'), function(memo, opt){ return memo + opt.length; }, 0);
					if(length > 0) {
						answered = true;
					}
					
				} else {
					if(response.length > 0) {
						answered = true;
					}
				}
			} catch(e) {}
		} else if(taskType == 'MC-MS' || taskType == 'ER') {
			if(response && response.length > 0) {
				answered = true;
			}
		} else if((taskType == 'MC-K'|| taskType == 'MC-S' || taskType == 'T-F')) {
			if(response != null && response != undefined) {
				answered = true;
			}
		} else if(taskType == 'CR') {
			if(response.replace('~~~','') != '') {
				answered = true;
			}
		}
		return answered;
	}
	
	this.canNavigate = function(mode) {
		//testObj.testSections[i].rules[r].code == "LINEAR" && testObj.testSections[i].rules[r].navigation
		var linearNavigation = _.find(testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].rules, function(rule){
									return rule.code.toLowerCase() == 'linear' && rule.navigation == true;
							});
		//if(testObj.testSections[testObj['sectionId_' + currentStudentTestSection.testSectionId]].navigationMode == 'Linear') {
		if(linearNavigation || mode == 'adaptive') {
			var answered = false;
			var currentTask = tasks[tde.testparam.currentQuestion];
			
			if(currentTask.taskType == 'ITP' || currentTask.taskType == 'MC-MS') {
				if(responses.values[tde.testparam.currentQuestion] != null) {
					var jElem = $.evalJSON(responses.values[tde.testparam.currentQuestion]);
					answered = isItemAnswered(jElem, currentTask.taskType);
				}
			} else {
				answered = isItemAnswered(responses.values[tde.testparam.currentQuestion], currentTask.taskType);
			}
			
			if(!answered) {
				$('body .w').append(new EJS({
					url : 'js/views/navigation-overlay.ejs'
				}).render({constraint : 'navigation', minChoices : null}));
				test.showAlertBox('navigation-overlay');
			}
			return answered;
		} else {
			return true;
		}
	};
	
	function maxScore(task) {
		var score = 0;
		if(task.scoringNeeded) {
			if(task.taskType == 'ITP' && task.scoringData != null) {
				if(task.scoringNeeded && task.scoringData) {
					var data = $.parseJSON(task.scoringData);
					score = data.correctResponses[0].score;
				}
			} else if(task.taskType == 'MC-MS' && task.scoringData != null) {
				var data = $.parseJSON(task.scoringData);
				score = data.correctResponse.score;
			} else if(task.taskType == 'MC-S') {
				score = (_.max(task.foils, function(foil){ return foil.responseScore; })).responseScore;
			} else if(task.taskType == 'MC-K' || task.taskType == 'T-F') {
				var foil = _.findWhere(task.foils, {correctResponse : true});
				if(foil) {
					score = foil.responseScore;
				}
			} else if(task.taskType == 'CR' && task.scoringData != null) {
				var data = $.parseJSON(task.scoringData);
				
				if(_.isArray(data)) {
					score = _.reduce(_.pluck(_.pluck($.parseJSON(task.scoringData), 'correctResponse'),'score'), function(memo, num){ return memo + num; }, 0);
				} else {
					score = _.reduce(_.pluck(_.pick($.parseJSON(task.scoringData), 'correctResponse'),'score'), function(memo, num){ return memo + num; }, 0);
				}
			}
		}
		return score;
	}
	
	function setBodyHeight() {
		setTimeout(function(){
			if($('body').height() != $(window).height()) {
				$('body').height($(window).height());
			}
			if(document.body.scrollHeight > $('body').height()) {
				$('body').height(document.body.scrollHeight);
			}
		},500);
	}
	
	function ckeditorKeyPress(e) {
		if((e.keyCode == 9 || e.which == 9) || (e.data && e.data.getKey() == 9)) {
			if(e.cancelable) {
				e.preventDefault();
			}
			setTimeout(function(){
				if(!e.shiftKey) {
					tabScan.tab();
				} else {
					tabScan.shiftTab();
				}
			},50);
		}
	}
	
	function ckeditorBlur(questionNumber, record) {
		if(record) {
			task.recordAnswer(CKEDITOR.instances['answer_'+tde.testparam.currentQuestion+'_0'], true);
		}
		
		if( jQuery.inArray(questionNumber, tde.testparam.reviews) >= 0 ) {
			return;
		}
		
		var checkstring =  CKEDITOR.instances['answer_'+tde.testparam.currentQuestion+'_0'].getData();
		if ( !($('#goto_' + questionNumber).parent().hasClass('q-incomplete') ) ) {$('#goto_' + questionNumber).parent().removeClass('q-incomplete');}
		if ( !($('#goto_' + questionNumber).parent().hasClass('q-incomplete-active') ) ) {$('#goto_' + questionNumber).parent().removeClass('q-incomplete-active');}
		
		
		if(checkstring && checkstring != "" ) {
			if ( !($('#goto_' + questionNumber).parent().hasClass('q-complete') ) ) { $('#goto_' + questionNumber).parent().addClass('q-complete'); }
			$('#goto_' + questionNumber).parent().addClass('q-complete-active');
		} else {
			if ( $('#goto_' + questionNumber).parent().hasClass('q-complete') ) { $('#goto_' + questionNumber).parent().removeClass('q-complete'); }
			$('#goto_' + questionNumber).parent().addClass('q-active');
		}
	}
	
	function populateTmpAnswer(questionNumber) {
		tmpAnswer = [];
		var ans = responses.values[questionNumber];
		if(ans!= null && ans!=undefined && ans!='[]') {
			//tmpAnswer = responses.values[questionNumber].replace(/\[|\]|\s/g,'').split(',');
			tmpAnswer = ans.replace(/\[|\]|\s/g,'').split(',');
			tmpAnswer.forEach(function(item, i) {
				tmpAnswer[i] = parseInt(tmpAnswer[i]);
			});
		}
	}
	
	this.setTestletStoryParams = function(questionNumber) {
		if(tasks[questionNumber].testlet) {
			
			var testlet = _.find(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].testlets, function(testlet){
				if(testlet.id == tasks[questionNumber].testletId) {
					return testlet;
				}
			});
			
			if(testlet.layoutCode == 'paginated') {
				testletStory.is = true;
				testletStory.onTestlet = false;
				testletStory.totalLength = _.reduce(_.pluck(testlet.groups, 'totalLength'), function(memo, num){ return memo + num; }, 0);
				
				var screenPosition = 0;
				var groupKeys = _.sortBy(_.keys(testlet.groups).map(function(groupNo){
				    						return parseInt(groupNo);
										}), function(num) {
											return num;
									});
				
				// set groupNumber and currentScreen - for 'questionNumber'
				for(var i=0; i<groupKeys.length; i++) {
					screenPosition += testlet.groups[groupKeys[i]].totalLength;
					if(_.contains(testlet.groups[groupKeys[i]].taskIds, tasks[questionNumber].id)) {
						testletStory.groupNumber = groupKeys[i];
						screenPosition -= testlet.groups[groupKeys[i]].taskIds.length;
						screenPosition += (testlet.groups[groupKeys[i]].taskIds.indexOf(tasks[questionNumber].id));
						break;
					}
				}
			}
			
		}
	};
};