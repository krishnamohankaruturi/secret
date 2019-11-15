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