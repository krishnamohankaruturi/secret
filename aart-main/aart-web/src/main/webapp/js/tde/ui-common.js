$(function() {
	window.onerror = function(desc, page, line, chr) {
		var jsObj = {
			error : desc,
			pageurl : page,
			lineno : line,
			charcater : chr
		};
		var jsonError=JSON.stringify(jsObj);
		logger.error(jsonError);
		/*$.ajax({
			url : contextPath + '/JSON/jserror/base.htm',
			type : 'GET',
			data : {
				"json" : jsonError
			},
			success : function(data) {
				//Do Nothing
			}
		});*/
		return true;
	};

	$(document).ajaxError(function(event, jqXHR, ajaxSettings, thrownError) {
		console.debug(thrownError);
		var ajaxErrorObj = {
			settings : ajaxSettings,
			message : thrownError
		};
		var jsonError=JSON.stringify(ajaxErrorObj);
		logger.error(jsonError);
		
		if(jqXHR.readyState == 0 && jqXHR.status == 0 && jqXHR.responseText == '' && thrownError == '') {
			test.showAlertBox('network-connectivity');
		}
		/*$.ajax({
			url : contextPath + '/JSON/jserror/ajax.htm',
			type : 'GET',
			data : {
				"json" : jsonError
			},
			success : function(data) {
				//Do Nothing
			}
		});*/

	});

	$('#show-toolbox').toggle(function() {
		$('#tools-c').fadeTo("slow", 1);
	}, function() {

	});

	$('#j_username').focus();

	$('#confirmClose').click(function() {
		sessionStorage.clear();
		window.close();
	});

	$('#confirmCancel').click(function() {
		$('.overlay').fadeOut("slow");
	});

	$('input').click(function() {
		$(this).focus();
	});

	

	$('#tde-content-answers li').click(function() {

		$('#tde-content-answers li').removeClass('selected');

		$(this).addClass('selected');

	});

	// Welcome animation

	$('.welcome-c ul li').hover(function() {
		$(this).addClass('active');
		$('.hovUp', this).stop().animate({
			top : '0px'
		}, 200);
	}, function() {
		$('.hovUp', this).stop().animate({
			top : '5px'
		}, 200);
		$(this).removeClass('active');
	});

});
// END READY

function closeBrowser() {
	var windowWidth = document.documentElement.clientWidth, windowHeight = document.documentElement.clientHeight, popupHeight = $(window).height() * 0.4;
	popupWidth = $('.overlay-content').width();

	$('.overlay-content').css({
		"position" : "absolute",
		"top" : windowHeight / 2 - popupHeight / 2,
		"left" : windowWidth / 2 - popupWidth / 2
	});

	$('.overlay').fadeIn("slow");
}

function signOut() {
	if (!tde.testparam.uiblock) {
		tde.testparam.uiblock = true;
	} else {
		tde.testparam.uiblock = false;
	}
	if(tde.testparam.uiblock) {
		sessionStorage.clear();
		window.location.replace(contextPath + "/j_spring_security_logout");
	}
}

function serverstore_responses(minimum, repeat, force) {
	/*
	 * if (!force && !ajaxActive) { setTimeout(function()
	 * {serverstore_responses(minimum, repeat, force)}, serverSaveCycle);
	 * return; // Do not allow ajax call. }
	 */

	if(tde.testparam.testTypeName == 'Practice'){
		return;
	}
	
	if (minimum == null) {
		minimum = 1;
	}

	if (repeat == null) {
		repeat = false;
	}
	// Store Responses.
	if (responses.answer.length >= minimum) {
		var query = new Array(), sent = new Array();

		for (var i in responses.answer) {
			if(!responses.answer[i].serverSent) {
				sent["id_" + responses.answer[i].task] = i;
	
				var entry = '{';
				// If this id for this is known put it in here so we can do an
				// update and not an add.
				if (responses.responseIds[responses.answer[i].question] != null) {
					entry += '"newFlag": false, ';
				} else {
					entry += '"newFlag": true, ';
				}
				var responseText = null;
				if (responses.answer[i].response != null) {
					responseText = JSON.stringify(responses.answer[i].response);
				}
				var foilText = null;
				if (responses.answer[i].foil != null) {
					foilText = responses.answer[i].foil;
					if(tde.testparam.jO.testFormatCode == 'ADP')
						testObj.studentResponseTaskVariantFoilId[ responses.answer[i].task] = responses.answer[i].foil;
				}
	
				entry += '"studentId": ' + tde.config.student.id + ',"testId":' + currentStudentTest.testId + ',"testSectionId": ' + currentStudentTestSection.testSectionId + ',"studentTestId": ' + currentStudentTest.id + ',"studentTestSectionId":' + currentStudentTestSection.id + ',"taskId":' + responses.answer[i].task + ',"foil":' + foilText + ',"response":' + responseText + ', "score" :'+ responses.answer[i].score +'}';
	
				query.push(entry);
				responses.answer[i].serverSent = true; // keep track of responses already sent to server
			}
		}

		if(query.length > 0)
		$.ajax({
			url : contextPath + '/JSON/studentresponse/saveList.htm',
			type : 'POST',
			dataType : 'json',
			data : {list:'['+query.join(',')+']'},
			success : function(data) {
				var toRemove = new Array();

				// Clear out the saved responses. (but only if it hasn't changed
				// since we saved it.)
				for (var item in data) {
					// need this...logger.info("<security:authentication
					// property="principal.username"/> test save taskId: " +
					// item);
					// taskId => responseId
					if(item != 'LCS_ID'){
					toRemove.push(sent["id_" + item]);
					responses.responseIds[tasks["id_" + item]] = data[item];
					}
					else{
						//do nothing.
						//console.log(data[item]);
					}
					// Save responseId if not already known.

					// Clean up the responses, remove items from the save list
					// if they have not changed.
				}

				for (var remove in toRemove.sort().reverse()) {
					responses.answer.splice(toRemove[remove], 1);
					// Removes from answers.
				}
				
				for(var i in responses.answer) {
					responses.answer[i].serverSent = false;
				}

				responses.changed = new Date().getTime();
				// Save the change to responses.
			}
		});
	}
}