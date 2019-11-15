window.onload = function() {
	if (!localStorage.firstload) {
        localStorage.setItem("firstload", "true");
        window.location.reload();
    }
	
	if(!checkBrowserValidity()){
		showInvalidBrowserMessage();
		hideResetPassword();
	} 
}

var oldPassword = "";
$(function(){
	oldPassword = $('#password').val();
	$('#password').val("");
	
	/*$('#resetPassword').click(function(){
		$(this).attr('disabled', 'disabled').prop('disabled', true).hide();
	});*/
	
	$('#resetPasswordFormId').submit(function(){
		if($('#allowReset').val() === 'N')
			return false;
		
		var password = $('#password').val();
		var confirmPassword = $('#confirmPassword').val();
		var isValid = isValidPassword(password, confirmPassword);
		
		/*if (!isValid){
			$('#resetPassword').removeAttr('disabled').prop('disabled', false).show();
		}*/
		
		return isValid;
	});
/*  Added for US-14985 */
	$('#resetPasswordFormId').validate({
		rules : {
			password : {
				required : true,
				pwcheckchars :true,
				minlength:8,
				maxlength:32
				
			},
			confirmPassword : {
				required : true,
				equalTo : '#password'
			}
		},
		errorClass: "error",
		messages : {
			password: {
	            required: "Please enter new password",
	        },
	        confirmPassword: {
	            required: "Please re-enter password",
	            equalTo: "New and confirm passwords don't match"
	        },
		}

	});
/*	Added for defect DE10060 */
	$.validator.addMethod("pwcheckchars", function (value) {
		
		   return ( /[^a-zA-Z0-9]/.test(value) && /[a-z]/.test(value) && /\d/.test(value) && /[A-Z]/.test(value));
		     }, "The password must be 8 - 32 characters long and at<br />least  1 special character, 1 upper case letter, 1 lower <br />case letter and 1 number.");	

});

function isValidPassword(password, confirmPassword) {
	if (isValidString(password)){
		if (password === oldPassword){
			$('#newPassword').show();
			$('#password').val("");
			$('#confirmPassword').val("");
			return false;
		} else if (password === confirmPassword) {
			return true;
		}  else {
			$('#passwordRequired').show();
			return false;
		}
	} else {
		$('#passwordRequired').show();
		return false;
	}
}

function isValidString(str) {
	return (str !== undefined && str !== null && typeof str === "string" && str !== "");
}

function checkBrowserValidity(){
	var nVer = navigator.appVersion;
	var nAgt = navigator.userAgent;
	var browserName  = navigator.appName;
	var fullVersion  = ''+parseFloat(navigator.appVersion); 
	var majorVersion = parseInt(navigator.appVersion,10);
	var nameOffset,verOffset,ix;

	// In Opera 15+, the true version is after "OPR/" 
	if ((verOffset=nAgt.indexOf("OPR/"))!=-1) {
	 browserName = "Opera";
	 fullVersion = nAgt.substring(verOffset+4);
	}
	// In older Opera, the true version is after "Opera" or after "Version"
	else if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
	 browserName = "Opera";
	 fullVersion = nAgt.substring(verOffset+6);
	 if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	   fullVersion = nAgt.substring(verOffset+8);
	}
	// In MSIE, the true version is after "MSIE" in userAgent
	else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
		 browserName = "Microsoft Internet Explorer";
		 fullVersion = nAgt.substring(verOffset+5);
	}
	else if ((verOffset=nAgt.indexOf("Trident"))!=-1) {
		 browserName = "Microsoft Internet Explorer";
		 fullVersion = nAgt.substring(verOffset+8);
	}
	// In Chrome, the true version is after "Chrome" 
	else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
	 browserName = "Chrome";
	 fullVersion = nAgt.substring(verOffset+7);
	}
	// In Safari, the true version is after "Safari" or after "Version" 
	else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
	 browserName = "Safari";
	 fullVersion = nAgt.substring(verOffset+7);
	 if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	   fullVersion = nAgt.substring(verOffset+8);
	}
	// In Firefox, the true version is after "Firefox" 
	else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
	 browserName = "Firefox";
	 fullVersion = nAgt.substring(verOffset+8);
	}
	// In most other browsers, "name/version" is at the end of userAgent 
	else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < 
	          (verOffset=nAgt.lastIndexOf('/')) ) 
	{
	 browserName = nAgt.substring(nameOffset,verOffset);
	 fullVersion = nAgt.substring(verOffset+1);
	 if (browserName.toLowerCase()==browserName.toUpperCase()) {
	  browserName = navigator.appName;
	 }
	}
	
	// trim the fullVersion string at semicolon/space if present
	if ((ix=fullVersion.indexOf(";"))!=-1)
	   fullVersion=fullVersion.substring(0,ix);
	if ((ix=fullVersion.indexOf(" "))!=-1)
	   fullVersion=fullVersion.substring(0,ix);
		
	majorVersion = parseInt(''+fullVersion,10);
	
	if (isNaN(majorVersion)) {
	 fullVersion  = ''+parseFloat(navigator.appVersion); 
	 majorVersion = parseInt(navigator.appVersion,10);
	 
	}
	
	if ((verOffset=nAgt.indexOf("Firefox"))!=-1)
	{
		//Minimum Acceptable version of Firefox is 24
		if(majorVersion<24)
			return false;
		else
			return true;
	}
	else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) 
	{
		//Minimum Acceptable version of Internet Explorer is 8
		if(majorVersion<8)
			return false;
		else
			return true;
	}
	else if(verOffset=nAgt.indexOf("Trident")!=-1)
	{
		if(!(window.ActiveXObject) && "ActiveXObject" in window)
			return true;
		else
			return false;
	}
	else if ((verOffset=nAgt.indexOf("Chrome"))!=-1)
	{
		//Minimum Acceptable version of Chrome is 35
		if(majorVersion<35)
			return false;
		else
			return true;
	}
	else if ((verOffset=nAgt.indexOf("Safari"))!=-1)
	{
		if ((ix=fullVersion.indexOf("."))!=-1)
		{
			var versionDecimals = fullVersion.split(".");
			//Minimum Acceptable version of Safari is 5.0.5
			if(versionDecimals[0]>5){
				return true;
			}
			else if(versionDecimals[0]==5)
			{
				if(versionDecimals[1]>0)
				{
					return true;
				}
				else if(versionDecimals[1]==0)
				{
					if(versionDetails.length==3)
					{
						if(versionDecimals[2]>=5)
							return true;
					}
					else
						return false;
				}
			}
			else 
				return false;
		
		}
	}
}

function hideResetPassword(){
	$('#resetPasswordForm').remove();
}

function showInvalidBrowserMessage(){
	$('#resetInvalidBrowser').css('display','');
}