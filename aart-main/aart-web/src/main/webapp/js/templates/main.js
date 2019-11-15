if(typeof String.prototype.trim !== 'function') {
	  String.prototype.trim = function() {
	    return this.replace(/^\s+|\s+$/g, ''); 
	  };
}

if (!Array.prototype.map)
{
  Array.prototype.map = function(fun , thisArg )
  {
    "use strict";

    if (this === void 0 || this === null)
      throw new TypeError();

    var t = Object(this);
    var len = t.length >>> 0;
    if (typeof fun !== "function")
      throw new TypeError();

    var res = new Array(len);
    var thisArg = arguments.length >= 2 ? arguments[1] : void 0;
    for (var i = 0; i < len; i++)
    {
      // NOTE: Absolute correctness would demand Object.defineProperty
      //       be used.  But this method is fairly new, and failure is
      //       possible only if Object.prototype or Array.prototype
      //       has a property |i| (very unlikely), so use a less-correct
      //       but more portable alternative.
      if (i in t)
        res[i] = fun.call(thisArg, t[i], i, t);
    }

    return res;
  };
}

$(function () {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
   	    xhr.setRequestHeader(header, token);
	});
  
   	$.ajaxSetup({
        // Disable caching of AJAX responses
        cache: false
    });
});

jQuery.ajaxPrefilter("json",function (options, originalOptions, jqXHR) {
 	NProgress.start();
    options.success = function (response) {
		NProgress.done();
    	var that = this, args = arguments;
        if(null == response || typeof response == 'undefined') {
           $("#ajaxError").html("Unknown error Occurred while processing your request.").show();
           setTimeout('$("#ajaxError").hide()',7000);
        }
        else if (jQuery.isFunction(originalOptions.success)) {
        	originalOptions.success.apply(that, args);
        }

    }
	options.error = function (response) {
		NProgress.done();
		var that = this, args = arguments;
    	if(null == response || typeof response == 'undefined') {
    		$("#ajaxError").html("Unknown error Occurred while processing your request.").show();
    		setTimeout('$("#ajaxError").hide()',7000);
        }
        else  {
        	 var errorObj = JSON.parse(response.responseText);	
        	 if(errorObj != null){
        		 $("#ajaxError").html(errorObj.errorMessage).show();
            	 setTimeout('$("#ajaxError").hide()',7000);
        	 }
        } 
        originalOptions.success.apply(that, args);
	}
	 $('.select2-hidden-accessible').removeAttr("aria-hidden");
	});

// Use the browser's built-in functionality to quickly and safely escape the
// string
function escapeHtml(str) {
	str = (str == null) ? '' : str;
    var div = document.createElement('div');
    div.appendChild(document.createTextNode(str));
    return div.innerHTML;
};
 
// UNSAFE with unsafe strings; only use on previously-escaped ones!
function unescapeHtml(escapedStr) {
	escapedStr = (escapedStr == null) ? '' : escapedStr;
    var div = document.createElement('div');
    div.innerHTML = escapedStr;
    var child = div.childNodes[0];
    return child ? child.nodeValue : '';
};

//Sort drop down values in alphabetical order
function sortDropdownOptions(options){
	options.detach().sort(function(option1,option2) {              
	    var value1 = $(option1).text();
	    var value2 = $(option2).text(); 
	    if(value1 == 'Select'){
	    	return -1;
	    }
	    return (value1 > value2)?1:((value1 < value2)?-1:0);            
	});
	
	return options;
}