$(function() {
	
	// TOOLBOX
	//$('#show-toolbox').toggle(
	/*$('body').delegate('#show-toolbox','toggle',
	  function () {
		$(this).addClass('active');
	    $('#toolbox-c').stop().animate({left:'0px'}, 666);
		$('#tde-content').stop().animate({left:'275px'}, 666);
		$('header, .tde-controls-c').stop().animate({left:'245px'}, 666);		
	  }, 
	  function () {
	    $('#toolbox-c').stop().animate({left:'-160px'}, 666);
		$('header').stop().animate({left:'85px'}, 666);
		$('#tde-content, .tde-controls-c').stop().animate({left:'105px'}, 666);
		$(this).removeClass('active');
	  }
	);*/
	$('body').delegate('#show-toolbox','click',function(){
		if($(this).hasClass('active')) {
			
			if(magnificationAccommodation.getMagnificationLevel() == 0 || magnificationAccommodation.getMagnificationLevel() == 'NaN') {
				toolboxleft = '-160px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 1) {
				toolboxleft = '-207px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 2) {
				toolboxleft = '-240px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 3) {
				toolboxleft = '-285px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 4) {
				toolboxleft = '-320px';
			}
			
			
			
			$('#toolbox-c').stop().animate({left: toolboxleft}, 50);
			$('header').stop().animate({left:'85px'}, 50);
			$('#tde-content').stop().animate({left:'105px'}, 50);
			$('.tde-controls-c').stop().animate({left:'95px'}, 50);
			$(this).removeClass('active');
		} else {
			
			if(magnificationAccommodation.getMagnificationLevel() == 0 || magnificationAccommodation.getMagnificationLevel() == 'NaN') {
				hdrleft = '240px';
				tdecontentleft = '255px';
				footerleft = '250px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 1) {
				hdrleft = '293px';
				tdecontentleft = '310px';
				footerleft = '293px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 2) {
				hdrleft = '328px';
				tdecontentleft = '345px';
				footerleft = '340px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 3) {
				hdrleft = '370px';
				tdecontentleft = '380px';
				footerleft = '375px';
			} else if (magnificationAccommodation.getMagnificationLevel() == 4) {
				hdrleft = '406px';
				tdecontentleft = '423px';
				footerleft = '415px';
			}			
			$(this).addClass('active');
		    $('#toolbox-c').stop().animate({left:'0px'}, 50);
			$('#tde-content').stop().animate({left: tdecontentleft}, 50);
			$('header').stop().animate({left: hdrleft}, 50);
			$('.tde-controls-c').stop().animate({left: footerleft}, 50);
		}
	});
	
	$('#tools-c ul li').toggle(function(){
			
		$(this).addClass('selected');
		var toolid = $(this).attr('id');
		$('.' + toolid).show();
		$('#select-tools').fadeTo("fast",1);	
		
	}, function() {
		
		var toolid = $(this).attr('id');
		$('.' + toolid).hide();
		$(this).removeClass('selected');
    	
    });
	
	$('#tools-active ul li a').click(function(){
		
		var toolclass = $(this).parent().attr('class');
		$('#' + toolclass).removeClass('selected');
		$(this).parent().hide();
		console.log(toolclass);
    	
    });

	// TB SB
		$('#tools-c').lionbars({
				autohide: false	
		});
	
	
	// Q ANSWERS
	
	$('.mark-question').hover(
	  function () {
		$(this).next().stop().fadeTo("fast",1);	
	},function () {
	   	$(this).next().stop().fadeTo("fast",0);
	});
 
});

// PASSAGE/QUESTION CONTROL

//$('#show-passage-only').click(function(){
$('body').delegate('#show-passage-only', 'click', function(){
	$('.span2-nav li a').removeClass();
	$('.test-passage').show();
	$('.test-passage').removeClass('span2 span2-right span_pvertical_stacked').addClass('span1');
	$('.test-questions').hide();
	$(this).addClass('active');
	$('.test-questions').css({"height":$('#tde-content').height() - 80});
	task.setUserPrefTestlet('questionOnly', 'passage_only');
	task.setUserPrefTestlet('currentGroup', task.getCurrentGroup());
	
	if($('#textToSpeech').is(':visible')) {
		$('#textToSpeech .tool-close').click();
		ttsAccommodation.refresh();
	} else if(tde.config.ttsrefresh) {
		ttsAccommodation.refresh();
	}
	
	if ( Modernizr.touch && $('.test-passage').data('jsp')) {
		$('.test-passage').data('jsp').reinitialise();
	}
});

//$('#show-questions-only').click(function(){
$('body').delegate('#show-questions-only', 'click', function(){
	$('.span2-nav li a').removeClass();
	$('.test-questions').show();
	$('.test-questions').removeClass('span2-right');
	$('.test-questions').removeClass('span_pvertical_stacked');
	$('.test-questions').removeClass('span2').addClass('span1');
	$('.test-passage').hide();
	$(this).addClass('active');
	$('.test-questions').css({"height":$('#tde-content').height() - 80});
	task.setUserPrefTestlet('questionOnly', 'questions_only');
	task.setUserPrefTestlet('currentGroup', task.getCurrentGroup());
	
	/*var div= $('.all-qWrap');
	if(div.prop('scrollHeight') > div.height()) {
		div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())));
	}*/
	//add scroll logic here - scroll to question in the test-questions div
	var div= $('.test-questions .all-qWrap');
	if ( Modernizr.touch && div.data('jsp')) {
		$('.all-qWrap').data('jsp').reinitialise();
		div.data('jsp').scrollToY($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3);
	}
	else {
		if(div.prop('scrollHeight') > div.height()) {
			div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3));				
		}
	}
	if($('#textToSpeech').is(':visible')) {
		$('#textToSpeech .tool-close').click();
		ttsAccommodation.refresh();
	} else if(tde.config.ttsrefresh) {
		ttsAccommodation.refresh();
	}
});

//$('#show-passage-question').click(function(){
$('body').delegate('#show-passage-question', 'click', function(){
	$('.span2-nav li a').removeClass();
	$('.test-passage, .test-questions').removeClass('span1').addClass('span2');
	var testlet = testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].testlets[testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]]['testletId_'+tasks[tde.testparam.currentQuestion].testletId]];
	//if(tasks[tde.testparam.currentQuestion].taskLayout == 'passage_right'){
	if (testlet.layoutCode == 'passage_right') {
		$('.test-passage, .test-questions').addClass('span2-right');
	//} else if(tasks[tde.testparam.currentQuestion].taskLayout == 'passage_vertical_stacked') {
	} else if (testlet.layoutCode == 'passage_vertical_stacked') {
		$('.test-passage, .test-questions').addClass('span_pvertical_stacked');
		$('.test-passage, .test-questions').removeClass('span2').addClass('span1');
	}
	$('.test-passage').show();
	$('.test-questions').show();
	$(this).addClass('active');
	
	var height = $('#tde-content').height() - 80;
	if(testlet.layoutCode!='passage_vertical_stacked') {
		$('.test-passage').css({"height":height});
		$('.test-questions').css({"height":height});
	} else {
		$('.test-passage').css({"height":height*0.4});
		$('.test-questions').css({"height":height*0.4});
	}
	
	task.setUserPrefTestlet('questionOnly', 'passage_questions');
	
	/*var div= $('.all-qWrap');
	if(div.prop('scrollHeight') > div.height()) {
		div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())));
	}*/
	//add scroll logic here - scroll to question in the test-questions div
	var div= $('.test-questions .all-qWrap');
	if ( Modernizr.touch ) {
		if($('.test-passage').data('jsp')) {
			$('.test-passage').data('jsp').reinitialise();
		}
		if(div.data('jsp')) {
			$('.all-qWrap').data('jsp').reinitialise();
			div.data('jsp').scrollToY($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3);
		}
	}
	else {
		if(div.prop('scrollHeight') > div.height()) {
			div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())-3));				
		}
	}
	
	if($('#textToSpeech').is(':visible')) {
		$('#textToSpeech .tool-close').click();
		ttsAccommodation.refresh();
	} else if(tde.config.ttsrefresh) {
		ttsAccommodation.refresh();
	}
});

(function($)
{
	$.fn.removeClassRegEx = function(regex)
    {
        var classes = $(this).attr('class');

        if(!classes || !regex) return false;

        var classArray = [];
        classes = classes.split(' ');

        for(var i=0, len=classes.length; i<len; i++) if(!classes[i].match(regex)) classArray.push(classes[i]);

        $(this).attr('class', classArray.join(' '));
    };
})(jQuery);

(function($)
{
	$.fn.hasClassRegEx = function(regex)
    {
        var classes = $(this).attr('class');

        if(!classes || !regex) return false;

        classes = classes.split(' ');

        for(var i=0, len=classes.length; i<len; i++) if(classes[i].match(regex)) return true;
        
        return false;

    };
})(jQuery);

