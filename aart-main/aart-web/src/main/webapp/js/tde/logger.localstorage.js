/* 
 * logger.localstorage.js
 * 
 * Class that logs to local storage. 
 * Local storage warns the user at 5MB is Firefox, then "should" allow them to allow for more.
 * 
 */ 

var logger = new function() {

	var levels = {
		"TRACE" : 0,
		"DEBUG" : 1,
		"INFO" : 2,
		"WARN" : 3,
		"ERROR" : 4,
		"FATAL" : 5,
	};
	
	var levelnames = {
			"0": "TRACE",
			"1": "DEBUG",
			"2": "INFO",
			"3": "WARN",
			"4": "ERROR",
			"5": "FATAL"
	};
	
	// Current log level.
	var loglevel = levels.TRACE;

	// Private method: log
	// Param: type - The string of a type of log event to create.
	// Param: event - The string of an event to log.
	// Log a event of type to the local storage with the epoch in the key.
	var log = function(type, event) {
		var epoch = new Date().getTime();
		try {
			localStorage.setItem("log." + type + "." + epoch, event);
		} catch (e) {
			console.log("Out of space in local storage.");
		}
	}

	// Function: is_supported 
	// Does this browser support the use of local storage.
	this.is_supported = function() {
		try {
			return 'localStorage' in window && window['localStorage'] !== null;
		} catch (e) {
			return false;
		}
	}

	// Public method: trace
	// Params: event
	// Create a trace message in the local storage.
	this.trace = function(event) {
		if (loglevel <= levels.TRACE) {
			log(levels.TRACE, event);
		}
	}

	// Public method: debug
	// Params: event
	// Create a debug message in the local storage.
	this.debug = function(event) {
		if (loglevel <= levels.DEBUG) {
			log(levels.DEBUG, event);
		}
	}

	// Public method: info
	// Params: event
	// Create a info message in the local storage.
	this.info = function(event) {
		if (loglevel <= levels.INFO) {
			log(levels.INFO, event);
		}
	}

	// Public method: warn
	// Params: event
	// Create a warn message in the local storage.
	this.warn = function(event) {
		if (loglevel <= levels.WARN) {
			log(levels.WARN, event);
		}
	}

	// Public method: error
	// Params: event
	// Create a error message in the local storage.
	this.error = function(event) {
		if (loglevel <= levels.ERROR) {
			log(levels.ERROR, event);
		}
	}

	// Public method: fatal
	// Params: event
	// Create a fatal message in the local storage.
	this.fatal = function(event) {
		if (loglevel <= levels.FATAL) {
			log(levels.FATAL, event);
		}
	}
	
	this.levelname = function(number) {
		return levelnames[number]
	}
};