var preview = new function() {
	
	var testletStory = {is: false, currentScreen : 0, onTestlet : true, groupNumber : null, totalLength : null, testlet : null};
	var userPrefTestlet = {viewAll : true, questionOnly: null, currentGroup: -1};
	var currentQuestion = 0;
	var viewAll = null;
	
	var messages = null, tasks = new Array(), testletflag = false, contextPath = null, uiType = 'genTest', tasktype = null, showtick = true;
	
	this.init = function(obj) {
		messages = obj.messages;
		tasks = obj.tasks;
		testletflag = obj.testletflag;
		contextPath = obj.contextPath;
		uiType = obj.uiType;
		showtick = obj.showtick;
	};
	
	this.showTask = function(questionNumber) {
		
		if (questionNumber >= tasks.length || questionNumber < 0) {
			return;
		}
		
		$('#tde-content').html("");
		//var questionNumber = 0;
		var responses={};
		responses.values = [];
		var reviews=[];
		//var viewAll=true;
		//var showtick=true;
		var tmpAnswer=[];
		
		var testlet = testletStory.testlet;
		if(testletflag == 'true') {
			
			if(testlet.layoutCode == 'paginated') {
				
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
					$('#tde-content').append(new EJS({url: contextPath +'/js/views/storyboard-base.ejs'}).render({ contextStimulus: testlet.groups[testletStory.groupNumber][stim].pages[testletStory.currentScreen - groupCounts - checkPage] }));
					
				} else {
					testletStory.onTestlet = false;
					var testletGroup = testlet.groups[testletStory.groupNumber];
					//currentQuestion = parseInt(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].tasks['id_'+  (testletGroup.taskIds[(testletStory.currentScreen - groupCounts - (testletGroup.totalLength - testletGroup.taskCount)  )])]);
					var taskId = (testletGroup.taskIds[(testletStory.currentScreen - groupCounts - (testletGroup.totalLength - testletGroup.taskCount)  )]);
					for(tsk in tasks) {
						if (tasks[tsk].id == taskId) {
							currentQuestion = tsk;
							break;
						}
					}
					//currentQuestion = tasks[];//parseInt(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].tasks['id_'+  (testletGroup.taskIds[(testletStory.currentScreen - groupCounts - (testletGroup.totalLength - testletGroup.taskCount)  )])]);
					questionNumber = currentQuestion;
					tasktype = tasks[questionNumber].taskType;
				}
			}
		}
		
		if(testletflag=='false' || (testletflag == 'true' && testletStory.onTestlet == false)){
			var task= tasks[currentQuestion];//taskParam[0],
			tasktype = task.taskType; 
			currentQuestion=questionNumber;
			EJS.config({cache: false});
			
			if (tasktype == 'ITP') {
				$.getScript(task['question'].replace('index.html',
						'js/itemScript.js'), function() {
					loadCss(task['question'].replace('index.html',
							'css/itemStyles.css'));
					$('#tde-content').load(
							task['question'],
							function(responseText, statusText, xhr) {
								if (statusText == "success") {
									$().dndPageScroll();
									$('#tde-content').addClass('fourteenpoint');
									operationalJS.initItem();
								} else if (statusText == "error") {
									removeCss();
								}
							});
				});
			} else if (tasktype == 'MC-K' || tasktype == 'T-F' || tasktype == 'MC-S' || tasktype == 'MC-MS') {
				// The layout of this quesiton.  Default to 0 if we don't know.
				var layout = task.taskLayout;//taskLayouts[tasks[questionNumber].taskLayoutId || 0].codeName;
				
				//if(tasktype != 'MC-MS'){
					if(layout == 'rating_scale' || layout == 'horizontal') {
						$('#tde-content').append(new EJS({url: contextPath+'/js/views/mc-rating.ejs'}).render({task: task, qNum: questionNumber, layout: layout, answers: tmpAnswer, response: responses, markedReview: reviews, uiType: uiType, path: contextPath, showtick: showtick, spokenPref: null}));
					} else {
						if(tasktype != 'MC-MS'){
							$('#tde-content').append(new EJS({url: contextPath+'/js/views/question.ejs'}).render({task: task, qNum: questionNumber, layout: layout, answers: tmpAnswer, response: responses, path: contextPath, markedReview: reviews, uiType: uiType, showtick: showtick, spokenPref: null}));
						} else {
							$('#tde-content').append(new EJS({url: contextPath+'/js/views/question.ejs'}).render({task: task, qNum: questionNumber, layout: layout, answers: tmpAnswer, response: responses, path: contextPath, markedReview: reviews, uiType: uiType, showtick: showtick, spokenPref: null}));
						}
					}
				//} else {
				//	$('#tde-content').append(new EJS({url: contextPath+'/js/views/question.ejs'}).render({task: task, qNum: questionNumber, layout: layout, answers: tmpAnswer, response: responses, path: contextPath, markedReview: reviews, uiType: uiType, showtick: showtick}));
				//}
				
				
			} else if(tasktype == 'CR') {
				$('#tde-content').append(new EJS({url: contextPath+'/js/views/task-cr.ejs'}).render({task: task, qNum: questionNumber, response : null, uiType: uiType, markedReview: reviews, path: contextPath}));
			} else if(tasktype == 'ER') {
				$('#tde-content').append(new EJS({url: contextPath+ '/js/views/er-question.ejs'}).render({task: task, qNum: questionNumber, answers: null, response: responses, path: contextPath, markedReview: reviews, uiType: uiType, showtick: showtick}));
				
				if (uiType == 'altTest') {
					CKEDITOR.replace($('.ckeditor')[0], {
						contentsCss : contextPath+ '/js/external/ckeditor/alternateUI.css',      });
				} else {
					$('.ckeditor').ckeditor();
				}
				
			}
		} else if(testletflag == 'true' && !testletStory.is) {
			
			//var tasks=taskParam;
			var taskgroups=[];
			var taskNos=[];
			$.each(tasks,function(i,val){
				taskNos.push(i);
			});
			taskgroups.push(taskNos);
			var group = 0;//task.findPart(questionNumber);
			
			
			if(viewAll == null) {
				if(testlet.questionViewCode == 'all_at_once') {
					viewAll = true;
				} else {
					viewAll = false;
				}
			}
			
							
			$('#tde-content').append(new EJS({url: contextPath+'/js/views/mc-passage.ejs'}).render({tasks: tasks, qNum: questionNumber, viewAll: viewAll, message: messages, group: group, taskgroup:taskgroups, contextStimulus: testlet.stimuli, markedReview: reviews, response: responses, testlet: testlet, path: contextPath, showtick: showtick, spokenPref: null}));
			
			
			var height = $('#tde-content').height() - 80;
			if(testlet.layoutCode!='passage_vertical_stacked') {
				$('.test-passage').css({"height":height});
				$('.test-questions').css({"height":height});
			} else {
				$('.test-passage').css({"height":height*0.4});
				$('.test-questions').css({"height":height*0.4});
			}
			
			//$('.test-passage').css({"height":height});
			//$('.test-questions').css({"height":height});
			//$('#tde-content').find('.q').find('ul').show();
			
			
			if(!testlet.locked) {
				$('#view_one').click(function() {
					viewAll = false;
					preview.showTask(currentQuestion);
					$('#view_all').removeClass('activePassage');
					$('#view_one').addClass('activePassage');
				});
				
				$('#view_all').click(function() {
					viewAll = true;
					preview.showTask(currentQuestion);
					$('#view_one').removeClass('activePassage');
					$('#view_all').addClass('activePassage');
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
			
			$('.test-passage').mCustomScrollbar({theme:'dark', advanced:{updateOnContentResize: true}});
			$('.test-passage').prepend($('#guideHandle'));
			$('.test-passage').append($('#guideTop'));
			$('.all-qWrap').mCustomScrollbar({theme:'dark', advanced:{updateOnContentResize: true}});
			
			//add scroll logic here - scroll to question in the test-questions div
			var div= $('.test-questions .all-qWrap');
			if(div.prop('scrollHeight') > div.height()) {
				//div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())));
				div.mCustomScrollbar("scrollTo", ($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())));
			}
			
			if(testletStory.testlet.stimulusNeeded == true) {
				$('.tde-controls-c ul.span2-nav').show();
			} else {
				$('.tde-controls-c ul.span2-nav').hide();
			}
		}
	};
	
	
	this.showNextPage = function(){
		if(tasks[currentQuestion].testlet == true && testletStory.is == true) {
			
			if(testletStory.currentScreen < (testletStory.totalLength-1) ) {
				if (tasktype == 'ITP') {
					removeCss();
				}
				testletStory.currentScreen++;
				preview.showTask(currentQuestion);
			} else {
				//task.showTask(currentQuestion + 1);
				//testletStory = {is: false, currentScreen : 0, onTestlet : true, groupNumber : null, totalLength : null};
			}
		}
		else {
			preview.showTask(currentQuestion + 1);
		}
	};
	
	this.showPrevPage = function() {
		if(tasks[currentQuestion].testlet == true && testletStory.is == true) {
			if(testletStory.currentScreen < testletStory.totalLength && testletStory.currentScreen > 0) {
				if (tasktype == 'ITP') {
					removeCss();
				}
				testletStory.currentScreen--;
				preview.showTask(0);
			} else {
				//preview.showTask(currentQuestion - 1);
				//testletStory = {is: false, currentScreen : 0, onTestlet : true, groupNumber : null, totalLength : null};
			}
			
		} else {
			preview.showTask(currentQuestion - 1);
		}
	};
	
	this.setupTestlet = function(testletObj) {
		//if(!_.isEmpty(testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].testlets)) {
			//var testlets = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].testlets;
			var testlets = new Array();
			testlets[0] = testletObj;
			
			var testletCount = 0;
			//var testletGroups = {};
			$.each(testlets, function(index, testlet) {
				var testletGroups = {};
				if (this.layoutCode == 'paginated') {
					var stimulusCount=0;
					$.each(this.stimuli, function() {
						if (/page-break-after\s*:\s*always/i.test(this.text)) {
							var text = this.text;
							text = text.replace(/page-break-after\s*:\s*always\s*.*?div>/ig,'"></div>page-break-for-tde');//replace(/page-break-after\s*:\s*always\s*.*?>/ig,'"></div>page-break-for-tde<div>');
							var story = text.split(/page-break-for-tde/);

							testlets[testletCount].stimuli[stimulusCount]['pages'] = story;
							testlets[testletCount].stimuli[stimulusCount]['pageCount'] = story.length;
						} else {
							testlets[testletCount].stimuli[stimulusCount]['pages'] = new Array();
							testlets[testletCount].stimuli[stimulusCount]['pages'][0] = this.text;
							testlets[testletCount].stimuli[stimulusCount]['pageCount']= 1;
						}
						stimulusCount++;
					});
					
					var stimuliGroups  = _.groupBy(this.stimuli, function(stimulus) {
						return stimulus.groupNumber;
					});

					$.each(stimuliGroups, function(groupNumber, stimuliGroup) {
						var sortedStimuliGroup = _.sortBy(stimuliGroup, function(stimulus) {
							return stimulus.sortOrder;
						});
						testletGroups[groupNumber] = sortedStimuliGroup; 
						
						var testletGroupTasks = _.sortBy(_.filter(tasks, function(tsk) {
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
					
					var taskGroups  = _.groupBy(tasks, function(task) {
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
					
					testlets[testletCount]['groups'] = testletGroups;
				}
				
				testletCount++;
			});
		//}
			testletStory.testlet = testletObj;
	};
	
	this.setUserPrefTestlet = function(property, value) {
		userPrefTestlet[property] = value;
	};
	
};