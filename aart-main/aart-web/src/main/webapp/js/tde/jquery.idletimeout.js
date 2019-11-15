/*
 * jQuery Idle Timeout 1.2
 * Copyright (c) 2011 Eric Hynds
 *
 * http://www.erichynds.com/jquery/a-new-and-improved-jquery-idle-timeout-plugin/
 *
 * Depends:
 *  - jQuery 1.4.2+
 *  - jQuery Idle Timer (by Paul Irish, http://paulirish.com/2009/jquery-idletimer-plugin/)
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 */

(function($, win) {

	var idleTimeout = {
		init : function(element, resume, options) {
			var self = this, elem;

			this.warning = elem = $(element);
			this.resume = $(resume);
			this.options = options;
			this.countdownOpen = false;
			this.failedRequests = options.failedRequests;
			//this._startTimer();
			this.title = document.title;

			// expose obj to data cache so peeps can call internal methods
			$.data(elem[0], 'idletimeout', this);

			// start the idle timer
			$.idleTimer(options.idleAfter * 1000);

			// once the user becomes idle
			$(document).bind(
					"idle.idleTimer",
					function() {

						// if the user is idle and a countdown isn't already
						// running
						if ($.data(document, 'idleTimer') === 'idle'
								&& !self.countdownOpen) {
							//self._stopTimer();
							self.countdownOpen = true;
							self._idle();
						}
					});

			// bind continue link
			this.resume.bind("click", function(e) {
				e.preventDefault();

				win.clearInterval(self.countdown); // stop the countdown
				self.countdownOpen = false; // stop countdown
				//self._startTimer(); // start up the timer again
				//self._keepAlive(false); // ping server
				options.onResume.call(self.warning); // call the resume
				// callback
			});
		},

		_idle : function() {
			var self = this, options = this.options, warning = this.warning[0], counter = options.warningLength;

			// fire the onIdle function
			options.onIdle.call(warning);

			// set inital value in the countdown placeholder
			options.onCountdown.call(warning, counter);

			// create a timer that runs every second
			this.countdown = win.setInterval(function() {
				if (--counter === 0) {
					window.clearInterval(self.countdown);
					options.onTimeout.call(warning);
				} else {
					options.onCountdown.call(warning, counter);
					/*document.title = options.titleMessage
							.replace('%s', counter)
							+ self.title;*/
				}
			}, 1000);
		},

		_startTimer : function() {
			var self = this;

			this.timer = win.setTimeout(function() {
				self._keepAlive();
			}, this.options.pollingInterval * 1000);
		},

		_stopTimer : function() {
			// reset the failed requests counter
			this.failedRequests = this.options.failedRequests;
			win.clearTimeout(this.timer);
		},

		_keepAlive : function(recurse) {
			var self = this, options = this.options;

			// Reset the title to what it was.
			document.title = self.title;

			// assume a startTimer/keepAlive loop unless told otherwise
			if (typeof recurse === "undefined") {
				recurse = true;
			}

			// if too many requests failed, abort
			if (!this.failedRequests) {
				this._stopTimer();
				options.onAbort.call(this.warning[0]);
				return;
			}

			$.ajax({
				timeout : options.AJAXTimeout,
				url : options.keepAliveURL,
				error : function(err) {
					console.debug(err);
					self.failedRequests--;
				},
				success : function(response) {
					if ($.trim(response) !== options.serverResponseEquals) {
						self.failedRequests--;
					}
				},
				complete : function() {
					if (recurse) {
						self._startTimer();
					}
				}
			});
		}
	};

	// expose
	$.idleTimeout = function(element, resume, options) {
		

		idleTimeout.init(element, resume, $.extend($.idleTimeout.options,
				options));
		return this;
	};

	// options
	$.idleTimeout.options = {
		// number of seconds after user is idle to show the warning
		warningLength : 30,

		// url to call to keep the session alive while the user is active
		keepAliveURL : "",

		// the response from keepAliveURL must equal this text:
		serverResponseEquals : "OK",

		// user is considered idle after this many seconds. 10 minutes default
		idleAfter : 600,

		// a polling request will be sent to the server every X seconds
		pollingInterval : 60,

		// number of failed polling requests until we abort this script
		failedRequests : 5,

		// the $.ajax timeout in MILLISECONDS!
		AJAXTimeout : 250,

		// %s will be replaced by the counter value
		titleMessage : 'Warning: %s seconds until log out | ',

		/*
		 * Callbacks "this" refers to the element found by the first selector
		 * passed to $.idleTimeout.
		 */
		// callback to fire when the session times out
		onTimeout : logoutBasedOnTimer,

		// fires when the user becomes idle
		onIdle : alertSessionTimeout,

		// fires during each second of warningLength
		onCountdown : watchCountdown,

		// fires when the user resumes the session
		onResume : reactivateSession,

		// callback to fire when the script is aborted due to too many failed
		// requests
		onAbort : $.noop
	};

})(jQuery, window);

function watchCountdown(periods) {
	if(periods>60){
		var data=parseInt(periods)-60;
		$('#timerSelector').text(
			'01 mins and ' + data + ' seconds');
	}else{
		$('#timerSelector').text(
				'00  mins and ' + periods + ' seconds');
	}
}
function logoutBasedOnTimer(ev) {
	$.ajax({
		url : contextPath + "/j_spring_security_logout",
		success : function(data) {
			$('.timeroverlay').fadeOut("slow");
			window.location.href = contextPath + "/logIn.htm";
		},
		error : function(event, jqXHR) {
			if(jqXHR == 'error' && event.readyState == 0 && event.status == 0) {
				$('.timeroverlay').fadeOut("slow");
				window.location.href = contextPath + "/logIn.htm";
			}
		}
	});
}
function reactivateSession(sessionfrom) {
	$.ajax({
		url : contextPath + '/JSON/student/reactivatesession2.htm',
		type : 'POST',
		success : function(data) {

			//if (sessionfrom !== 'sessiontime') {
				//$('#timerSelector').countdown('destroy');
				$('#timeroverlay').fadeOut("slow");
				//self.countdownOpen = false;
				//if (!isNaN(data)) {
					//window.clearTimeout($.idleTimer.tId);
					//setupSessionTime(data * 1000);
				//} else {
					//console.debug('Not able to get the data');
				//}
			//}

		},
		error: function(event, jqXHR) {
			if(jqXHR == 'error' && event.readyState == 0 && event.status == 0) {
				$('.timeroverlay').fadeOut("slow");
				window.location.href = contextPath + "/logIn.htm";
			}
		}
	});
}
function alertSessionTimeout() {
	var elem = "timeroverlay";
	var windowWidth = document.documentElement.clientWidth, windowHeight = $(window).height(), popupHeight = $(
			window).height() * 0.4;
	popupWidth = $('#' + elem + ' .overlay-content').width();
	if($(window).scrollTop() > 0) {
		windowHeight = $(window).height() + $(window).scrollTop();
	} 
	//$('#timerSelector').countdown('destroy');
	/*$('#timerSelector').countdown({
		until : new Date(new Date().getTime() + 90000),
		onTick : watchCountdown,
		onExpiry : logoutBasedOnTimer
	});
*/
	$('#' + elem + ' .overlay-content').css({
		"position" : "absolute",
		"top" : windowHeight / 2 - popupHeight / 2,
		"left" : windowWidth / 2 - popupWidth / 2
	});

	$('#' + elem + ' .overlay-content').show();
	$('#' + elem + ' .overlay').show();
	$('#' + elem).show();
	var D = document;
	$('#' + elem)
			.height(
					Math.max(Math.max(D.body.scrollHeight,
							D.documentElement.scrollHeight), Math
							.max(D.body.offsetHeight,
									D.documentElement.offsetHeight), Math
							.max(D.body.clientHeight,
									D.documentElement.clientHeight)) - 8);

	// $('#timeroverlay').fadeIn("slow");
	/*
	 * var popupHeight = screen.availHeight * 0.4, popupWidth = $(
	 * '.overlay-content').width();
	 * 
	 * var left = Number((screen.availWidth / 2) - (popupWidth / 2)); var tops =
	 * Number((screen.availHeight / 2) - (popupHeight / 2));
	 * 
	 * $('.overlay-content').css({ "position" : "absolute", "top" : tops, "left" :
	 * left });
	 * 
	 * $('#timerSelector').countdown('destroy'); $('#timerSelector').countdown({
	 * until : new Date(new Date().getTime() + 90000), onTick : watchCountdown,
	 * onExpiry : logoutBasedOnTimer }); $('#timeroverlay').fadeIn("slow");
	 * //$('#timeroverlay').draggable({ containment: "window" });
	 */

}