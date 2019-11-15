/* 
 * localstorage.js
 * 
 * Class that manages local storage. 
 * Local storage warns the user at 5MB is Firefox, then "should" allow them to allow for more.
 * 
 */ 

var localStore = new function() {
	
	var supported = false;
	try {
		supported = 'localStorage' in window && window['localStorage'] !== null;
	} catch (e) {
		supported = false;
		console.log("localStorage not supported in this browser.")
	}
	
	// Function: is_supported 
	// Does this browser support the use of local storage.
	this.is_supported = function() {
		return supported;
	}

	// Method: get
	// Param: key - The string of a type of log event to create.
	// Log a event of type to the local storage with the epoch in the key.
	this.get = function(key) {
		if (! supported) {
			return;
		}
		
		if (localStorage.getItem(key + ".type") == "object") {
			return localStorage.getItem(key) && jQuery.parseJSON(localStorage.getItem(key));
		}

		return localStorage.getItem(key);
	}
	
	this.getChange = function(key) {
		if (! supported) {
			return -1;
		}
		return localStorage.getItem(key + ".lastChange");
	}

	// Method: set
	// Param: key - The string of a type of log event to create.
	// Param: value - The object to save.
	// Save a value to the local storage with the key. JSONify the object if needed.
	this.set = function(key, value) {
		if (! supported) {
			return;
		}
		try {
			var epoch = new Date().getTime();
			if (typeof value == 'object') {
				localStorage.setItem(key, jQuery.toJSON(value));
			} else {
				localStorage.setItem(key, value);
			}
			localStorage.setItem(key + ".lastChange", epoch);
			localStorage.setItem(key + ".type", typeof value);
		} catch (e) {
			console.log("Out of space in local storage.");
		}
	}

	// Method: remove
	// Param: key - The name of the item to remove
	// Removes the item from localstorage and all it's helper keys.
	this.remove = function(key) {
		if (supported) {
			localStorage.removeItem(key + ".lastChange");
			localStorage.removeItem(key + ".type");
			localStorage.removeItem(key);
		}
	}
};