var sessiontimer = (function() {
	var sessionTimeOut;
	//var promptToExtendSessionTimeoutId;
	function setupSessionTime(sessionTime) {
		 
		//promptToExtendSessionTimeoutId=window.setTimeout(alertSessionTimeout, sessionTime - 100000);

		$.idleTimer(sessionTime - 100000);

		$(document).on("idle.idleTimer", alertSessionTimeout);

		$(document).on("active.idleTimer", function() {
			reactivateSession('sessiontime');
		});

	}

	function alertSessionTimeout() {
		var elem = "timeroverlay";
		var windowWidth = document.documentElement.clientWidth, windowHeight = document.body.clientHeight, popupHeight = $(
				window).height() * 0.4;
		popupWidth = $('#' + elem + ' .overlay-content').width();

		$('#timerSelector').countdown('destroy');
		$('#timerSelector').countdown({
			until : new Date(new Date().getTime() + 90000),
			onTick : watchCountdown,
			onExpiry : logoutBasedOnTimer
		});

		$('#' + elem + ' .overlay-content').css({
			"position" : "absolute",
			"top" : windowHeight / 2 - popupHeight / 2,
			"left" : windowWidth / 2 - popupWidth / 2
		});

		$('#' + elem + ' .overlay-content').show();
		$('#' + elem + ' .overlay').show();
		$('#' + elem).show();
		var D = document;
		$('#' + elem).height(
				Math.max(Math.max(D.body.scrollHeight,
						D.documentElement.scrollHeight), Math.max(
						D.body.offsetHeight, D.documentElement.offsetHeight),
						Math.max(D.body.clientHeight,
								D.documentElement.clientHeight)) - 8);

		// $('#timeroverlay').fadeIn("slow");
		/*
		 * var popupHeight = screen.availHeight * 0.4, popupWidth = $(
		 * '.overlay-content').width();
		 * 
		 * var left = Number((screen.availWidth / 2) - (popupWidth / 2)); var
		 * tops = Number((screen.availHeight / 2) - (popupHeight / 2));
		 * 
		 * $('.overlay-content').css({ "position" : "absolute", "top" : tops,
		 * "left" : left });
		 * 
		 * $('#timerSelector').countdown('destroy');
		 * $('#timerSelector').countdown({ until : new Date(new Date().getTime() +
		 * 90000), onTick : watchCountdown, onExpiry : logoutBasedOnTimer });
		 * $('#timeroverlay').fadeIn("slow"); //$('#timeroverlay').draggable({
		 * containment: "window" });
		 */

	}
	function watchCountdown(periods) {
		$('#timerSelector').text(
				'0' + periods[5] + ' mins and ' + periods[6] + ' seconds');
	}
	function logoutBasedOnTimer(ev) {
		$.ajax({
			url : contextPath + "/j_spring_security_logout",
			success : function(data) {
				$('.timeroverlay').fadeOut("slow");
				window.location.href = contextPath + "/logIn.htm";
			}
		});
	}
	function reactivateSession(sessionfrom) {
		$.idleTimer("destroy");
		$.ajax({
			url : contextPath + '/JSON/student/reactivatesession2.htm',
			type:'POST',
			success : function(data) {

				if (sessionfrom !== 'sessiontime') {
					$('#timerSelector').countdown('destroy');
					$('#timeroverlay').fadeOut("slow");
					if (!isNaN(data)) {
						window.clearTimeout($.idleTimer.tId);
						setupSessionTime(data * 1000);
					} else {
						console.debug('Not able to get the data');
					}
				}

			}
		});
	}

	function init(obj) {
		sessionTimeOut = obj.timeout;
		// Load the Timer EJS
		$('body').append(new EJS({
			url : obj.contextPath + '/js/views/tool/sessiontimer.ejs'
		}).render(obj.sessionuimessages));

		setupSessionTime(sessionTimeOut);

		$('#extendSession').on('click', reactivateSession);
		$('#sessionLogout').on('click', logoutBasedOnTimer);
	}

	return {
		init : init
	};

})();