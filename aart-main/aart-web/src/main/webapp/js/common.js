$(function() {
// SIGN IN
	
	$('#username').focus();
	
	$('input').click(function () {	
		$(this).focus();	
	});
	
	$('#show_error').click(function () {
		
		$('.signin-error').show();
		$('#username').focus();
		return false;
		
	});

});