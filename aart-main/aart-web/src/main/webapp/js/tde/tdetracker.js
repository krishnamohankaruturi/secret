var tracker = (function() {
	var appKey = '';
	/*
	 * JSON Format -
	 * {"typecode":"studentid","typename":"student","goaltype":"tool","attrs":[{"testid",""},{"testsectionid":""},{"taskid":""},{"studentid":""},{"toolname":""}]}
	 */

	function trackGoal(inobj) {
		$.ajax({
			url : contextPath + '/JSON/tracker.htm',
			type : 'POST',
			data : {
				"appKey" : appKey,
				"json" : JSON.stringify(inobj)
			},
			success : function(data) {
				//Do Nothing
			}
		});
	}
	function trackTool(ev, toolname) {
		if(tde.testparam.testTypeName == 'Practice'){
			return;
		}
		// tasks
		// currentStudentTestSection
		// currentQuestion
		setTimeout(function() {
			var trackToolObj = {};
			trackToolObj.typecode = currentStudentTestSection.studentTestId;
			trackToolObj.typename = "StudentTest";
			trackToolObj.goaltype = "Tool";
			trackToolObj.attrs = [];
			trackToolObj.attrs.push({
				testsectionid : currentStudentTestSection.testSectionId
			});
			trackToolObj.attrs.push({
				position : tde.testparam.currentQuestion
			});
			trackToolObj.attrs.push({
				taskid : tasks[tde.testparam.currentQuestion].id
			});
			trackToolObj.attrs.push({
				toolname : toolname
			});
			trackGoal(trackToolObj);
		}, 1000);

	}
	function trackQuestionShow(ev, toolname) {
		if(tde.testparam.testTypeName == 'Practice'){
			return;
		}
		// tasks
		// currentStudentTestSection
		// currentQuestion
		setTimeout(function() {
			var trackToolObj = {};
			trackToolObj.typecode = currentStudentTestSection.studentTestId;
			trackToolObj.typename = "StudentTest";
			trackToolObj.goaltype = "QuestionDisplay";
			trackToolObj.attrs = [];
			trackToolObj.attrs.push({
				testsectionid : currentStudentTestSection.testSectionId
			});
			trackToolObj.attrs.push({
				position : tde.testparam.currentQuestion
			});
			trackToolObj.attrs.push({
				taskid : tasks[tde.testparam.currentQuestion].id
			});
			
			trackGoal(trackToolObj);
		}, 1000);

	}
	
	function trackUserAgent() {
		if(tde.testparam.testTypeName == 'Practice'){
			return;
		}
		var trackToolObj = {};
		trackToolObj.typecode = currentStudentTestSection.id;
		trackToolObj.typename = "StudentTestSection";
		trackToolObj.goaltype = "Os-Browser";
		trackToolObj.attrs = [];
		trackToolObj.attrs.push({
			useragent : userAgent//this info is available at navigator.userAgent also
		});
		trackGoal(trackToolObj);
	}

	function init(key) {
		appKey = key;
		$(document).bind('tracker.tool', trackTool);
		$(document).bind('tracker.question', trackQuestionShow);
	}

	return {
		init : init,
		trackGoal : trackGoal,
		trackUserAgent : trackUserAgent
	};

})();