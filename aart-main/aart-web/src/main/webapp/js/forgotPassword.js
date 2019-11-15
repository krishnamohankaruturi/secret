$(function() {
	$('.signinerror').hide();
	$('.info_message').hide();
	$('.error_message').hide();
	$('#submitResetRequest').click(function(){
		var $submitButton = $(this);
		$('.signinerror').hide();
		$('.info_message').hide();
		$('.error_message').hide();
		var username = $('#username').val();
		if (isValidString(username)) {
			$submitButton.attr('disabled', 'disabled').prop('disabled', true).css('opacity', '0.5');
			$.ajax({
				url: "sendResetEmail.htm",
				data: {
					username: username
				},
				dataType: 'json',
				type: 'POST',
				success: function(data) {
					if (data.success) {
						//The reset email was sent
						$('.error_message').hide();
				
						$('.signinerror').show();
						$('#emailsuccess').show();
					} else {
						$('.info_message').hide();
					
						 $('.signinerror').show();
						//Something happened on the server and the email could not be sent.
						if (data.serviceError) {
							$('#emailfailed').show();
						} else if (data.parameterError){
							$('#userNotFound').show();
						} else {
							$("#userInactive").show();
						}
					}
				},
				complete: function(){
					$submitButton.removeAttr('disabled').prop('disabled', false).css('opacity', '');
				}
			});
		} else {
			//$('.signinerror').css('display','');
			$('.signinerror').show();
			//diplay a message saying that the email is a required field.
			$('#emailrequired').show();
		}
	});
});

function isValidString(username) {
	return (username !== undefined && username !== null && typeof username === "string" && username !== "");
}