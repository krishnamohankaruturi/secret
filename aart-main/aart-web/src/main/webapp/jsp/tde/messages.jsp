<script>
var testObj=new Object(),
currentStudentTestSection=new Object(),
currentStudentTest=new Object()
messages = {"testPage" : {"dirrectionsFor": "<fmt:message key='label.test.directionsfor'/>",
                			  "goBack": "<fmt:message key='label.test.goback'/>",
                			  "begin": "<fmt:message key='label.test.begin'/>",
                			  "next": "<fmt:message key='label.common.next'/>",
                			  "back": "<fmt:message key='label.common.back'/>",
                			  "saveasdraft": "<fmt:message key='label.common.saveasdraft'/>",
                			  "clear" : "<fmt:message key='label.common.clear'/>",
                			  "reviewAndEnd" : "<fmt:message key='label.test.reviewEnd'/>",
                			  "passageOnly" : "<fmt:message key='label.test.passageOnly'/>",
                			  "questionsOnly" : "<fmt:message key='label.test.questionsOnly'/>",
                			  "passageAndquestions" : "<fmt:message key='label.test.passageAndquestions'/>",
                			  "reviewFor": "<fmt:message key='label.test.reviewfor'/>",
                			  "reviewAnswered": "<fmt:message key='label.test.reviewanswered'/>",
                			  "reviewNotAnswered": "<fmt:message key='label.test.reviewnotanswered'/>",
                			  "answeredReview": "<fmt:message key='label.test.answeredreview'/>",
                			  "notAnsweredReview": "<fmt:message key='label.test.notansweredreview'/>",
                			  "reviewEnd": "<fmt:message key='msg.test.reviewend'/>",
                			  "reviewStep1": "<fmt:message key='msg.test.reviewstep1'/>",
                			  "reviewStep2": "<fmt:message key='msg.test.reviewstep2'/>",
                			  "reviewWarn1": "<fmt:message key='msg.test.reviewWarn1'/>",
                			  "reviewWarn2": "<fmt:message key='msg.test.reviewWarn2'/>",
                			  "backToTest": "<fmt:message key='label.test.backtotest'/>",
                			  "endTest": "<fmt:message key='label.test.endtest'/>",
                			  "confirmTestEnd": "<fmt:message key='label.test.confirmtestend'/>",
                			  "yes": "<fmt:message key='label.common.yes'/>",
                			  "no": "<fmt:message key='label.common.no'/>",
                			  "invalidTicketMsg": "<fmt:message key='label.common.invalidticketmsg'/>",
                			  "welcomeTo": "<fmt:message key='label.common.welcometo'/>",
                			  "ticket": "<fmt:message key='label.common.ticket'/>",
                			  "ticketNumber": "<fmt:message key='label.common.ticketnumber'/>",
                			  "letsGo": "<fmt:message key='label.common.letsgo'/>",
                			  "viewOne": "<fmt:message key='label.test.viewone'/>",
                			  "viewAll": "<fmt:message key='label.test.viewall'/>",
                			  "replay" : "<fmt:message key='label.tool.common.replay'/>",
                			  "play":"<fmt:message key='label.tool.common.play'/>",
                			  "pause" : "<fmt:message key='label.tool.common.pause'/>",
                			  "resume": "<fmt:message key='label.tool.common.resume'/>",
                			  "end" : "<fmt:message key='label.tool.common.end'/>",
                			  "mute":"<fmt:message key='label.tool.common.unmute'/>",
 		  					  "unmute":"<fmt:message key='label.tool.common.unmute'/>",
 		  					  "close" : "<fmt:message key='label.tool.mask_close'/>",
 		  					  "savedraftmessage" : "<fmt:message key='label.test.savedraftmessage'/>",
 		  					  "exitwithoutsave" : "<fmt:message key='label.test.exitwithoutsave'/>",
 		  					  "feedBackHeaderMessage" : "<fmt:message key='msg.test.feedback.header'/>",
 		  					  "feedBackPercentage" : "<fmt:message key='msg.test.feedback.percentage'/>",
 		  					  "feedBackScore" : "<fmt:message key='msg.test.feedback.score'/>",
 		  					  "feedBackOutof" : "<fmt:message key='msg.test.feedback.outof'/>",
 		  					  "feedBackPoints" : "<fmt:message key='msg.test.feedback.points'/>"
                			 },
            "testHome" : { "takeaTest" : "<fmt:message key='label.common.takeaTest'/>",
            			   "practice" : "<fmt:message key='label.common.practice'/>",
            			   "signout" : "<fmt:message key='label.common.signout' />",
            			   "closebrowser" : "<fmt:message key='label.common.closebrowser' />",
            			   "takeTest" : "<fmt:message key='label.testHome.takeTest' />",
            			   "practiceTest" : "<fmt:message key='label.testHome.takePracticeTest' />",
            			   "noTestMessage1" : "<fmt:message key='label.testHome.noTestMessage1' />",
            			   "noTestMessage2" : "<fmt:message key='label.testHome.noTestMessage2' />",
            			   "browserCloseAlert" : "<fmt:message key='label.studentHome.browserCloseAlert' />",
            			   "genericError" : "<fmt:message key='label.errorpage.message' />"
            			   
            }
                			 
	},
	
tool_names = {"pointer":"<fmt:message key='label.tool.pointer'/>",
	  	  "calculator":"<fmt:message key='label.tool.calculator'/>",
	  	  "graphingCalculator":"<fmt:message key='label.tool.graphingCalculator'/>",
		  "mask": {"name":"<fmt:message key='label.tool.mask'/>",
			  	   "warning":"<fmt:message key='label.tool.mask_warning'/>"},
		  "auditoryCalming":{"name":"<fmt:message key='label.tool.auditory_calming'/>",
		  					 "nowplaying":"<fmt:message key='label.tool.auditory_calming.nowplaying'/>",
		  					 "change":"<fmt:message key='label.tool.auditory_calming.change'/>",
		  					 "volume":"<fmt:message key='label.tool.auditory_calming.volume'/>",
		  					 "update_required":"<fmt:message key='label.tool.auditory_calming.update_required'/>",
		  					 "update_message":"<fmt:message key='label.tool.auditory_calming.update_message'/>"},
		  "colorOverlay":{"name":"<fmt:message key='label.tool.colorOverlay'/>",
			  			  "header":"<fmt:message key='label.tool.colorOverlay_header'/>",
			  			  "white":"<fmt:message key='label.tool.colorOverlay_white'/>",
			  			  "blue":"<fmt:message key='label.tool.colorOverlay_light_blue'/>",
			  			  "yellow":"<fmt:message key='label.tool.colorOverlay_light_yellow'/>",
			  			  "gray":"<fmt:message key='label.tool.colorOverlay_light_gray'/>",
			  			  "pink":"<fmt:message key='label.tool.colorOverlay_light_pink'/>",
			  			  "green":"<fmt:message key='label.tool.colorOverlay_light_green'/>"},
		  "contrast":{"name":"<fmt:message key='label.tool.contrast'/>",
			  		  "name_colorcontrast":"<fmt:message key='label.tool.colorcontrast'/>",
			          "header":"<fmt:message key='label.tool.contrast_header'/>",
			  		  "white_black":"<fmt:message key='label.tool.contrast_white_black'/>",
			  		  "black_white":"<fmt:message key='label.tool.contrast_black_white'/>",
			  		  "black_gray":"<fmt:message key='label.tool.contrast_black_gray'/>",
			  		  "black_yellow":"<fmt:message key='label.tool.contrast_black_yellow'/>",
			  		  "white_green":"<fmt:message key='label.tool.contrast_white_green'/>",
			  		  "white_red":"<fmt:message key='label.tool.contrast_white_red'/>"},
		  "tags":{"name":"<fmt:message key='label.tool.tags'/>",
			  	  "main_idea":"<fmt:message key='label.tool.tags_main_idea'/>",
			  	  "supporting_detail":"<fmt:message key='label.tool.tags_supporting_detail'/>",
			  	  "keywords":"<fmt:message key='label.tool.tags_keywords'/>",
			  	  "evidence":"<fmt:message key='label.tool.tags_evidence'/>",
			  	  "readagain":"<fmt:message key='label.tool.tags_readagain'/>",
			  	  "important":"<fmt:message key='label.tool.tags_important'/>",
			  	  "clear":"<fmt:message key='label.tool.tags_clear'/>",
			  	  "header":"<fmt:message key='label.tool.tags_header'/>"},
		  "magnification":{"name":"<fmt:message key='label.tool.magnification'/>",
			               "header":"<fmt:message key='label.tool.magnification_header'/>"},
		  "search":{"name":"<fmt:message key='label.tool.search'/>",
			        "label":"<fmt:message key='label.searchbox.searchlabel'/>",
			        "go":"<fmt:message key='label.searchbox.go'/>",
			        "clear":"<fmt:message key='label.common.clear'/>"},
		  "reference":"<fmt:message key='label.tool.reference'/>",
		  "periodicTable":{ "name":"<fmt:message key='label.tool.periodicTable'/>",
			  			    "ptname":"<fmt:message key='label.pt.name'/>", 
							"symbol":"<fmt:message key='label.pt.symbol'/>", 
							"atomicnumber":"<fmt:message key='label.pt.atomicnumber'/>", 
							"atomicmass":"<fmt:message key='label.pt.atomicmass'/>", 
							"groupnumber":"<fmt:message key='label.pt.groupnumber'/>", 
							"period":"<fmt:message key='label.pt.period'/>", 
							"block":"<fmt:message key='label.pt.block'/>"
		  },
		  "textToSpeech":{"name":"<fmt:message key='label.tool.textToSpeech'/>"},
		  "highlighter":"<fmt:message key='label.tool.highlighter'/>",
		  "postItNotes":{"name":"<fmt:message key='label.tool.postitnotes'/>",
			             "ok": "<fmt:message key='label.common.ok'/>",
			             "yes": "<fmt:message key='label.common.yes'/>",
			             "no": "<fmt:message key='label.common.no'/>",
			             "warning": "<fmt:message key='label.tool.postit_warning'/>",
			             "delete": "<fmt:message key='label.tool.postit_confirmdelete'/>"},
		  "striker":"<fmt:message key='label.tool.striker'/>",
		  "eraser":"<fmt:message key='label.tool.eraser'/>",
		  "guideLine":"<fmt:message key='label.tool.guideLine'/>",
		  "ruler":"<fmt:message key='label.tool.ruler'/>",
		  "magnifyingGlass":{"name":"<fmt:message key='label.tool.magnifyingGlass'/>",
			  				 "control":"<fmt:message key='label.tool.magnifyingGlass_Control'/>"},
		  "videoplayer" : {"stop" : "<fmt:message key='label.tool.videoplayer.stop'/>",
			               "maxvolume" : "<fmt:message key='label.tool.videoplayer.max_volume'/>",
			               "repeat": "<fmt:message key='label.tool.videoplayer.repeat'/>",
			               "repeatoff": "<fmt:message key='label.tool.videoplayer.repeat_off'/>",
			               "minimize": "<fmt:message key='label.tool.videoplayer.minimize'/>",
			               "name" : "<fmt:message key='label.tool.videoplayer.videoplayer'/>"},
		  "talkingCalculator":"<fmt:message key='label.tool.talking_calculator'/>",
		  "rubric":"<fmt:message key='label.tool.rubric'/>",
		  "sessiontimer":{
			  "expirywarning":'<fmt:message key="label.sessionmessages.expirywarning" />',
			  "selecttext":'<fmt:message key="label.sessionmessages.selecttext" />',
			  "expirywarning":'<fmt:message key="label.sessionmessages.expirywarning" />',
			  "timeremaining":'<fmt:message key="label.sessionmessages.timeremaining" />',
			  "extendsession":'<fmt:message key="label.sessionmessages.extendsession" />',
			  "studentLogout":'<fmt:message key="label.studentHome.logout"/>'
		  },
		  "readAloud":{
			  "name" : '<fmt:message key="label.tool.readaloud" />'
		  },
		  "library":{"name":'<fmt:message key="label.tool.library" />',
			  		 "viewdoc": '<fmt:message key="label.tool.library.viewdoc" />'}
},

tasktype = '',//Based on position in tasks array
tasks = null,// = localStore.get("tasks." + studentTestId), // Get the tasks from localstore. // commented because secure browser not able to remove dom cache
responses=null,
secureBrowser = false;
//viewAll = null;
if (responses == null) {
	// lastAnswer: is the last time an answer was recorded.
	// changed: A change was made and this structure needs to be saved to the localstore
	// values: Answers used by the frontend
	// responseIds: studentRepsonse Ids by question.
	// history: History of answer selections (unsaved to server) {task: val, foil: val, time: val}
	// answer: Tasks (unsaved) {task: val, foil: val}
	responses = { lastAnswer: new Date().getTime(), changed: new Date().getTime(), responseIds: new Array(), 
		    values: new Array(), answer: new Array(), history: new Array(), scores : new Array() };
}

if(/10.0.5|24./.test(userAgent)) {
	secureBrowser = true;
}

var tde = {
			config : {
						uiblock : false,
						ajaxActive : true,
						localSaveCycle : 5000, // Time in miliseconds to save the responses to localstorage.
						//serverSaveCycle = 5000 // Time in miliseconds to save the responses to server.
						myscroll : null,
						policies : null, // organization policies
						ttsrefresh : false,
						mediaUrl : "${applicationScope['nfs.url']}", // url of the media server
						tool_selectors : {	"highlighter": "#tde-content",
											"striker": "#tde-content li .foil",
											"eraser": "#tde-content:not(.magnified-content)",
											"guideline": ".test-passage",
											"ruler": "#rulerDiv",
											"postIt": ".w",
											"magnifyingglass": ".w #tde-content"
						},
						student : { "id": "${user.id}", "userName": "${user.displayName}"/* , "firstName": "${student.firstName}", "surName": "${student.surName}" */ },
						lcsId : null,
						lcsMediaUrl : null
			},
			
			//test-param object
			testparam : {
							currentQuestion : -1, //Based on position in tasks array.
							currentScreen : 0,
							showTick : true,
							exitandsave : true,
							tabbedIndex : null,
							scanInterval : null,
							qctoans : -1,//${test.testSections[testSectionIndex].questioncounttoanswer}
							//testSectionIndex = null,
							//lastNavQNum : null
							//studentAccommodations = new Array(),
							//testAccommodations = new Array(),
							//magnifyingtools = "",
							username : null,
							jO : null,
							testTypeName : null,
							taskgroups : new Array(),
							//taskGroupNames : new Array(),
							reviews : new Array("P"), // Needs placeholder so splice works right.
							taskTags : new Array(),
							postItNotes : new Array(),
							pluckedTestSections : '',
							colorcontrast : null,
							softbreakCount : 0
			}
};
//var test = {};
//var messages = {};

</script>