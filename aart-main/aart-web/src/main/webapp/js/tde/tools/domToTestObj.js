var domToTestObj = (function() {
	
	
	function cache(parentElement) {
		
		if($(parentElement).parents('.taskstem').length != 0) {
			//console.log("task");
			tasks[tde.testparam.currentQuestion].question = cleanhtml($(parentElement).parents('.taskstem').html());
		} else if($(parentElement).parents('.foil').length != 0) {
			var questionNumber = $(parentElement).parents('.q').data('qnum') || tde.testparam.currentQuestion;
			$(parentElement).parents('.foils').find('.foil').each(function(index){
				if($(this).is($(parentElement).parents('.foil'))) {
					tasks[questionNumber].foils[index].text = cleanhtml($(parentElement).parents('.foil').html());
				}
			});
		} else if($(parentElement).parents('.passage').length != 0) {
			testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].testlets[testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]]['testletId_'+tasks[tde.testparam.currentQuestion].testletId]].stimuli[0].text = cleanhtml($(parentElement).parents('.passage').html());
		} else if($(parentElement).parents('.q-question').length != 0) {
			var questionNumber = $(parentElement).parents('.q-question').parents('.q').data('qnum');
			tasks[questionNumber].question = cleanhtml($(parentElement).parents('.q-question').html());
		} else if($(parentElement).parents('.foils').length != 0) {
			var questionNumber = $(parentElement).parents('.q').data('qnum') || tde.testparam.currentQuestion;
			
			$(parentElement).parents('.foils').find('.foil').each(function(index){
				tasks[questionNumber].foils[index].text = cleanhtml($(this).html());
			});
		} else if($(parentElement).is($('div.q'))) {
			var questionNumber = $(parentElement).data('qnum');
			tasks[questionNumber].question = cleanhtml($(parentElement).find('.q-question').html());
			$(parentElement).find('.foil').each(function(index){
				tasks[questionNumber].foils[index].text = cleanhtml($(this).html());
			});
		} else if($(parentElement).parents('.test-questions').length != 0) {
			
			$(parentElement).parents('.test-questions').find('.q').each(function() {
				var questionNumber = $(this).data('qnum');
				tasks[questionNumber].question = cleanhtml($(this).find('.q-question').html());
				$(this).find('.foil').each(function(index){
					tasks[questionNumber].foils[index].text = cleanhtml($(this).html());
				});
			});
		} else if($(parentElement).parents('#tde-content').length != 0 || $(parentElement).is($('#tde-content'))) {
			if(! tasks[tde.testparam.currentQuestion].testlet) {
				tasks[tde.testparam.currentQuestion].question = cleanhtml($('.taskstem').html());
				$('.foil').each(function(index){
					tasks[tde.testparam.currentQuestion].foils[index].text = cleanhtml($(this).html());
				});
			} else {
				testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].testlets[testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]]['testletId_'+tasks[tde.testparam.currentQuestion].testletId]].stimuli[0].text = cleanhtml($('.passage').html());
				$(parentElement).parents('#tde-content').find('.q').each(function() {
					var questionNumber = $(this).data('qnum');
					tasks[questionNumber].question = cleanhtml($(this).find('.q-question').html());
					$(this).find('.foil').each(function(index){
						tasks[questionNumber].foils[index].text = cleanhtml($(this).html());
					});
				});
			}
		}
	}
	
	function cleanhtml(html) {
		var node = $('<div></div>');
		node.html(html);
		node.removeClass('strikethroughText strikethroughMediaContent');
		
		// remove the strike by image for mathml
		node.find('div.strikeDiv').each(function() {
			$(this).replaceWith(this.childNodes);
		}).end();
		
		node.find('img.strikeImg').each(function() {
			$(this).remove();
		}).end();
		//
		
		node.find('span.taghighlight').each(function(){
			$(this).replaceWith(this.childNodes);
		}).end();
		return node.html();
	}

	return {
		cache : cache
	};
	
})();