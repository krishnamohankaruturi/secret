/* 
 * striker.js
 * 
 * Tool to "strike" items from the answer list.
 * 
 */
var striker = new function() {
	var mode;
	
	this.isMode = function(newMode) {
		return mode == newMode;
	}
	
	this.activate = function(selector, newMode) {
		test.removePointerCursor();
		$('.w').addClass('strikeThroughCursor');
		mode = newMode;
		// Turn off the previous mode.
		$(selector).die(); 
		
		$(selector).on("click", function() {
			if ($(this).parents('li').find('input').attr('checked') || $(this).parents('li').find('input')[0].attributes['checked']) {
				var currentTime = new Date().getTime();
				var timeSpent = currentTime - responses.lastAnswer;
			
				$(this).parents('li').find("input").attr("checked", false);
				$(this).parents('li').find('label').removeClass('paginateAnswered');
				
				responses.lastAnswer = responses.changed = currentTime;
				
				if ($(this).parents('li').find('input').is(':radio')) {
					
					var updateAnsFlag = false;
					for (ans in responses.answer) {
						//updating the response.answer if the task is already in this list
			        	if(responses.answer[ans].task == tasks[tde.testparam.currentQuestion].id) {
			        		//splice from array not done because its cost efficient to update the object than to remove and add another.
			        		responses.answer[ans].foil = null;
			        		responses.answer[ans].response = null;
			        		responses.answer[ans].score = null;
			        		updateAnsFlag = true;
			        		break;
			        	}
					}
					if(!updateAnsFlag)
						responses.answer.push({question: tde.testparam.currentQuestion, task: tasks[tde.testparam.currentQuestion].id, foil: null, response: null, score: null}); 
					
					responses.history.push({task: tasks[tde.testparam.currentQuestion].id, foil: null, response: null, time: timeSpent, respondtime: new Date().toString(), respondseconds: currentTime});
					
					responses.values[tde.testparam.currentQuestion] = null;
					
					$('#goto_'+tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
					$('#goto_'+tde.testparam.currentQuestion).parent().removeClass('q-complete');
					if($('#goto_'+ tde.testparam.currentQuestion).parent().hasClass('q-incomplete')) {
						$('#goto_' + tde.testparam.currentQuestion).parent().addClass('q-incomplete-active');
					} else {
						$('#goto_' + tde.testparam.currentQuestion).parent().addClass('q-active');
					}
					
				} else {
					var tmpAnswer = task.getTmpAnswer();
					var position = jQuery.inArray(tasks[tde.testparam.currentQuestion].foils[$(this).parents('li').find('input:checkbox').attr('value')].id, tmpAnswer);
					tmpAnswer.splice(position, 1);
					if(tmpAnswer && tmpAnswer.length===0){
						$('#goto_'+tde.testparam.currentQuestion).parent().removeClass('q-complete-active');
						$('#goto_'+tde.testparam.currentQuestion).parent().removeClass('q-complete');
						$('#goto_'+tde.testparam.currentQuestion).parent().addClass('q-active');
						$('.tde-controls-clear').trigger('click');
					} else {
						task.iniTmpAnswer(tmpAnswer);
						task.storeTmpAnswerInResponses(false);
					}
				}
			}
			
			if (!/MathML/.test($(this).html())) {
				if (!$(this).hasClass(mode)) {
					$(this).addClass(mode);
					var kids = $(this).children();
					var mediaKids = $(this).children(".mediacontent");
					kids.addClass("strikethroughText");
					mediaKids.addClass("strikethroughMediaContent");
				} else {
					$(this).removeClass(mode);
					var kids = $(this).children();
					var mediaKids = $(this).children(".mediacontent");
					kids.removeClass("strikethroughText");
					mediaKids.removeClass("strikethroughMediaContent");
				}
			} else if (/MathML/.test($(this).html())) {
				if (!$(this).hasClass('strikedByImg')) {
					var strikeDiv = $('<div/>').addClass('strikeDiv'); //relative inline-block
					var imgStrike = null;

					$.each($(this).contents(), function(i,e){ 
						if(e.nodeType == Node.TEXT_NODE){
							var spanTag = $('<span/>');
							spanTag.append(e);
							$(strikeDiv).append(spanTag); 
						} else {
							$(strikeDiv).append(e); 
						}
					});
					$(this).append(strikeDiv);

					var divFoil = $(this);
					var lineWidth = 0;
					var lineOffsetLeft = 0;
					var lineOffsetTop = 0;
					var offsetTopAdjustment = 0;
					if($(strikeDiv).find('math')){
						offsetTopAdjustment = 10;
					}
					$.each($(strikeDiv).contents(), function(i,e){
						if(!$(e).is("br") && 0 == lineWidth){ 
							lineOffsetLeft =  $(e)[0].offsetLeft;
							lineOffsetTop =  $(e)[0].offsetTop; 
						}
						//DE7355
						if($(e).is("p")){
							imgStrike = $('<img/>').attr('src', 'images/strike.png').addClass('strikeImg');
							if(imgStrike){
								$(imgStrike).width($(e).width()+15);
								$(imgStrike).css({left: lineOffsetLeft, top:lineOffsetTop+offsetTopAdjustment});
								$(this).append(imgStrike);								
							}
							lineWidth = 0;
							imgStrike = null;
							$(this).addClass('strikedByImg');
							//DE7355-END
						} else if($(e).is("br")){
							if(imgStrike){
								$(imgStrike).width(lineWidth+15);
								$(imgStrike).css({left: lineOffsetLeft, top:lineOffsetTop+offsetTopAdjustment});
								divFoil.append(imgStrike);
							}
							lineWidth = 0; 
							imgStrike = null;
						}else {
							if(null == imgStrike){
								imgStrike = $('<img/>').attr('src', 'images/strike.png').addClass('strikeImg');
							} 
							lineWidth = lineWidth + $(e).width(); 
						}
					});

					$(imgStrike).width(lineWidth+15);
					$(imgStrike).css({left: lineOffsetLeft, top:lineOffsetTop+offsetTopAdjustment});
					$(this).append(imgStrike);					
					$(this).addClass('strikedByImg');
				} else {
					var ch = $(this).children(':first')[0].childNodes;
					$(this).children(':first').remove();
					$(this).children('.strikeImg').remove();
					for(var i=0; i< ch.length ; i++) {
						$(this).append(ch[0]);
						i--;
					}
					$(this).removeClass('strikedByImg');
				}
			}
			magnifyingGlass.resetMagnifyingGlass();
		});
	};
	
	this.deactivate = function(selector) {
		$(selector).die();
		mode = "";
		test.defaultCursor();
		test.addPointerCursor();
	};
	
	this.removeStrike = function(foil) {
		if (!/MathML/.test(foil.html())) {
			if(foil.hasClass("strikethroughText")) 
				foil.removeClass("strikethroughText");
			if ( foil.children().length > 0 ) {
				foil.find('*').each(function(index, item){
					if ( $(item).hasClass("strikethroughMediaContent")) {
						$(item).removeClass("strikethroughMediaContent");
					}
					if ( $(item).hasClass("strikethroughText")) {
						$(item).removeClass("strikethroughText");
					}
				});
			} 
		} else if(/MathML/.test(foil.html())) {
			//DE7355
			if ($(foil.context).contents().length > 1) {
				$.each($(foil.context).contents(), function(i,e){
					$(e).children('.strikeImg').remove();
					$(e).removeClass('strikedByImg');
					 
				});
			//DE7355-END
			}else {
				if (foil.hasClass('strikedByImg')) {				
					var ch = foil.children(':first').get(0);
					$(ch).replaceWith(ch.childNodes);
					foil.children('.strikeImg').remove();
					foil.removeClass('strikedByImg');
					
				}
			}
		}
	};
	
	
};