<!doctype html>
<html lang='en'>
<head>
<meta charset="utf-8">
<title>KITE TDE</title>
<meta name="robots" content="noindex, nofollow" />

<meta name="viewport" content="width=1024px, user-scalable = yes" />
<meta name="apple-mobile-web-app-capable" content="yes">
<link rel="stylesheet" href="../override/app.css" type="text/css" />
<link rel="stylesheet" href="../override/app-native.css" type="text/css" />

<!--  <link rel="stylesheet" href="../css/preview/app.css" type="text/css" />
<link rel="stylesheet" href="../css/preview/app-native.css" type="text/css" />-->

<link rel="stylesheet" href="../css/new-theme/tool.css" type="text/css" />
<link rel="stylesheet"
	href="../css/external/jquery.mCustomScrollbar.css" type="text/css" />
<link rel="stylesheet"
	href="../css/external/jquery-ui-1.8.16.custom.css" type="text/css" />


</head>
<body>



	<div class="w">
	
		<a id="confirm-answer" class="btn" href="#">Submit Answer <img
			src="<%=request.getContextPath()%>/images/arrow-sm-icon.png" alt="Go" /></a>
		<!-- /confirm btn -->
		<div id="paramTask" style="display:none"><%=request.getParameter("task")%></div>
	</div>
	<!-- /wrap -->

	<script src="../js/external/jquery-1.7.2.js"></script>
	<script src="../js/external/jquery.json-2.4.js"></script>
	<script src="../js/external/modernizr-2.6.2.js"></script>
	<script src="../js/s/libs/sb.js"></script>
	<script src="../js/s/libs/jeasing.js"></script>
	<script src="../js/external/jquery-ui-1.8.16.js"></script>
	<script src="../js/s/libs/sliderkit/jquery.sliderkit.1.9.2.js"></script>
	<!-- <script src="../js/s/app.js"></script>-->
	<script src="../js/external/ejs.js"></script>
	<script src="../js/logger.localstorage.js"></script>
	<script src="../js/localstorage.js"></script>
	
	<script src="../js/task.js"></script>
	<script src="../js/test.js"></script>
	<script src="../js/cb-preview.js"></script>

	<script src="../js/external/jquery.ui.touch-punch.js"></script>
	<script src="../js/external/jquery.jplayer/jquery.jplayer.js"></script>
	<script src="../js/external/jquery.jplayer/add-on/jplayer.playlist.js"></script>
	<script src="../js/external/jquery.mCustomScrollbar.js"></script>
	<script src="../js/external/jquery.mousewheel.js"></script>
	<script src="../js/external/underscore.js"></script>
	<script src="../js/jquery.dnd.scroll.js"></script>
	<script src="../js/external/ckeditor/ckeditor.js"></script>
	<script src="../js/external/ckeditor/adapters/jquery.js"></script>
	
	


	<script type="text/javascript">
	 
		$(function() {
			//$('header, #toolbox-c, .gen-content, .tde-controls-c').show();
			var contextPath= '/tdestatic'; <%--'http\://localhost:8080/tde-web'; '/tdestatic';--%>
			<%--var task=JSON.parse('<%=request.getParameter("task")%>'),--%>
			var testletflag='<%=request.getParameter("testlet")%>';
			var taskParam=JSON.parse(unescape('<%=request.getParameter("task")%>'));
			var testlet=JSON.parse(unescape('<%=request.getParameter("testletObj")%>'));
			var uiType=JSON.parse(unescape('<%=request.getParameter("uiType")%>'));
			//alert(taskParam);
   			console.debug(taskParam);
			console.debug(testletflag);
			console.debug(testlet);
			//$('#tde-content').removeClass('fourteenpoint');
			
			
			var messages={};
			//messages.testPage={};
			messages.testPage= {'passageOnly' : 'Passage Only',
								'questionsOnly': 'Questions Only',
								'passageAndquestions': 'Passage And Questions',
								'saveasdraft': 'save',
								'back':'Back',
								'clear':'Clear',
								'next': 'Next',
								'reviewAndEnd': 'Review And End',
								'replay': 'Replay',
								'pause': 'Pause',
								'end': 'End',
								'viewOne': 'View One',
								'viewAll': 'View All'}; 
			
			var testObj = {};
			testObj.exitFlag = false;
			
			//var uiType='genTest';
			//testletflag = 'false';//'true';
			var showtick = true;
			
			if (uiType == 'genTest') {
				$('body').append(new EJS({
					url : contextPath + '/js/views/genTest.ejs'
				}).render({messages : messages, testObj : testObj, linearNavigation : false}));
				$('.w').first().before($('#oldTestPage').find('#tool-arena').clone());
				$('body').addClass('general');
				
				// preview - set image src
				$('.tde-controls-back a img').attr('src', '..'+$('.tde-controls-back a img').attr('src'));
				$('.tde-controls-next a img').attr('src', '..'+$('.tde-controls-next a img').attr('src'));
				
				$('.test-nav-prev img').attr('src', '..'+$('.test-nav-prev img').attr('src'));
				$('.test-nav-next img').attr('src', '..'+$('.test-nav-next img').attr('src'));
			} else if (uiType == 'altTest') {
				//tde.testparam.exitandsave = false;
				showtick = false;
				var exitandsave = false;
				$('body').append(new EJS({
					url : contextPath + '/js/views/altTest.ejs'
				}).render({exitandsave : exitandsave, messages : messages, testObj : testObj}));
				$('.w').css({
					width : '95%'
				});
				$('body').addClass('alternate');
				$('.nr-buttons').show();
			}
			$('.w').first().html($('#oldTestPage').find('.w').html());
			$('#oldTestPage').remove();

			preview.init({"messages":messages, "testletflag": testletflag, "contextPath": contextPath, "uiType": uiType, "tasks": taskParam, "showtick": showtick});
			
			if(testletflag == 'true') {
				preview.setupTestlet(testlet);
			}
			tasktype = taskParam[0].taskType;
			preview.showTask(0);
			
			
			$('.tde-controls-back').on('click' , function() {
				preview.showPrevPage();
			});
			
			$('.tde-controls-next').on('click', function() {
				preview.showNextPage();
			});
			
			$('body').delegate('#show-passage-only', 'click', function(){
				$('.span2-nav li a').removeClass();
				$('.test-passage').show();
				$('.test-passage').removeClass('span2 span2-right span_pvertical_stacked').addClass('span1');
				$('.test-questions').hide();
				$(this).addClass('active');
				$('.test-questions').css({"height":$('#tde-content').height() - 80});
				preview.setUserPrefTestlet('questionOnly', 'passage_only');
				preview.setUserPrefTestlet('currentGroup', 0);
			});

			$('body').delegate('#show-questions-only', 'click', function(){
				$('.span2-nav li a').removeClass();
				$('.test-questions').show();
				$('.test-questions').removeClass('span2-right');
				$('.test-questions').removeClass('span_pvertical_stacked');
				$('.test-questions').removeClass('span2').addClass('span1');
				$('.test-passage').hide();
				$(this).addClass('active');
				$('.test-questions').css({"height":$('#tde-content').height() - 80});
				preview.setUserPrefTestlet('questionOnly', 'questions_only');
				preview.setUserPrefTestlet('currentGroup', 0);
				
				var div= $('.all-qWrap');
				if(div.prop('scrollHeight') > div.height()) {
					div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())));
				}
			});

			$('body').delegate('#show-passage-question', 'click', function(){
				$('.span2-nav li a').removeClass();
				$('.test-passage, .test-questions').removeClass('span1').addClass('span2');
				//var testlet = preview.getTestlet();//testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]].testlets[testObj.testSections[testObj['sectionId_'+currentStudentTestSection.testSectionId]]['testletId_'+tasks[tde.testparam.currentQuestion].testletId]];
				if (testlet.layoutCode == 'passage_right') {
					$('.test-passage, .test-questions').addClass('span2-right');
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
				
				preview.setUserPrefTestlet('questionOnly', 'passage_questions');
				
				var div= $('.all-qWrap');
				if(div.prop('scrollHeight') > div.height()) {
					div.scrollTop(($('.show-q-questions-active').closest('div.q').offset().top - (div.offset().top - div.scrollTop())));
				}
			});
			
		});
		/**
		 * Load the given css file dynamically.
		 * @param file
		 */
		var loadCss = function(file) {
			$('<link rel="stylesheet" type="text/css" href="'+file+'" >')
					.appendTo("head");
		};

		/**
		 * Remove the css file added for innovative items.
		 */
		var removeCss = function(url) {
			if (url) {
				$('link').each(function() {
					if ($(this).attr('href').match(url)) {
						$(this).remove();
					}
				});
			} else {
				$('link').each(function() {
					if ($(this).attr('href').match('css/itemStyles.css')) {
						$(this).remove();
					}
				});
			}
		};
		
	</script>


</body>
</html>