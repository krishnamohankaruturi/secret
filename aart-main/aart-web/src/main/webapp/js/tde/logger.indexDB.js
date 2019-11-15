/* 
 * logger.indexDB.js
 * 
 * Class that logs to local storage.
 * 
 */

var loggerDB = new function() {
	// Get the reference to indexDB in this browser.
	var indexedDB = window.indexedDB || window.webkitIndexedDB
			|| window.mozIndexedDB;

	var levels = {
		"TRACE" : 0,
		"DEBUG" : 1,
		"INFO" : 2,
		"WARN" : 3,
		"ERROR" : 4,
		"FATAL" : 5,
	};

	// Current log level.
	var loglevel = levels.TRACE;

	// Set timeout for first log, reset to 0 after the database is open.
	var logtimeout = 100;

	// Reference to the database.
	var database;
	var databaseName = "log"
	var objectStore = "log";

	// Private error function.
	var onerror = function(error) {
		console.log(error);
	}

	// Create the database if it doesn't already exists
	var request = indexedDB.open(databaseName, "This is the log database.");

	// Connect the database.
	request.onsuccess = function(e) {
		var v = "1.0";
		database = e.target.result;
		var db = database;
		// We can only create Object stores in a setVersion transaction;
		if (v != db.version) {
			var setVrequest = db.setVersion(v);

			// onsuccess is the only place we can create Object Stores
			setVrequest.onfailure = onerror;
			setVrequest.onsuccess = function(e) {
				var store = db.createObjectStore(objectStore, {
					keyPath : "timeStamp"
				});

			};
		}
		logready = true;
	};

	request.onfailure = onerror;

	// Private method: log
	// Param: type - The string of a type of log event to create.
	// Param: event - The string of an event to log.
	// Log a event of type to the local storage with the epoch in the key.
	var log = function(type, event) {
		
		if (database) {

			// Set the timeout to 0 once we have a database.
			if (logtimeout > 0) {
				logtimeout = 0;
			}

			var db = database;
			var trans = db.transaction([objectStore], IDBTransaction.READ_WRITE, 0);
			var store = trans.objectStore(objectStore);

			var data = {
				"text" : event,
				"timeStamp" : new Date().getTime()
			};

			var request = store.put(data);

			request.onerror = function(e) {
				console.log("Error logging: ", e);
			};

		} else {
			console.log("Error logging: Database not read")
		}
	}

	// Function: is_supported
	// Does this browser support the use of local storage.
	this.is_supported = function() {
		var indexedDB = window.indexedDB || window.webkitIndexedDB
				|| window.mozIndexedDB || null;

		return (indexedDB != null);
	}

	// Public method: trace
	// Params: event
	// Create a trace message in the local storage.
	this.trace = function(event) {
		if (loglevel <= levels.TRACE) {
			setTimeout(log, logtimeout, levels.TRACE, event);
		}
	}

	// Public method: debug
	// Params: event
	// Create a debug message in the local storage.
	this.debug = function(event) {
		if (loglevel <= levels.DEBUG) {
			setTimeout(log, logtimeout, levels.DEBUG, event);
		}
	}

	// Public method: info
	// Params: event
	// Create a info message in the local storage.
	this.info = function(event) {
		if (loglevel <= levels.INFO) {
			setTimeout(log, logtimeout, levels.INFO, event);
		}
	}

	// Public method: warn
	// Params: event
	// Create a warn message in the local storage.
	this.warn = function(event) {
		if (loglevel <= levels.WARN) {
			setTimeout(log, logtimeout, levels.WARN, event);
		}
	}

	// Public method: error
	// Params: event
	// Create a error message in the local storage.
	this.error = function(event) {
		if (loglevel <= levels.ERROR) {
			setTimeout(log, logtimeout, levels.ERROR, event);
		}
	}

	// Public method: fatal
	// Params: event
	// Create a fatal message in the local storage.
	this.fatal = function(event) {
		if (loglevel <= levels.FATAL) {
			setTimeout(log, logtimeout, levels.FATAL, event);
		}
	}
};