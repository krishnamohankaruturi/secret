<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib tagdir='/WEB-INF/tags' prefix='sc'%>
<%@ include file="/jsp/include.jsp" %>
<link rel="stylesheet" href="<c:url value='/css/external/jquery-ui-1.8.19.custom.min.css'/>" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/css/theme/recordBrowser.css" rel="stylesheet">        
<link rel="stylesheet" href="<c:url value='/css/custom.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/screen.css'/>" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/css/bcg-jqGrid.css" rel="stylesheet">
<script type="text/javascript" src="<c:url value='/js/external/jquery-ui-1.8.19.custom.min.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.validate.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/custom.orgFilter.js'/>"> </script>   
<script type="text/javascript" src="<c:url value='/js/external/jquery.bcgmultiselect.js'/>"> </script> 

<div id="container_login" class="panel_2span">
	<form  id="createAccountForm" class="form ca-form">
    	<div style="border-bottom: 1px solid #f3f3f3; "><h1 class="panel_head"><fmt:message key='common.createaccount'/></h1></div>
	    <div class="instructions"></div>
	    <label id="msges" class="error"></label>    
        <div class="form-fields">
            <label for="email" class="field-label isrequired"><fmt:message key='common.enteremailaddress'/>:<span class="error">*</span></label>
            <input type="text" oncopy="return false;" id="email" placeholder="Enter Email" name="email" maxlength="50" size ="31"/>
        </div>
        <div class="form-fields">
            <label for="confirmEmail" class="field-label isrequired"><fmt:message key='common.confirmemailaddress'/>:<span class="error">*</span></label>
            <input type="text" onpaste="return false;" id="confirmEmail" placeholder="Confirm Email" name="confirmEmail" maxlength="50" size ="31"/>
        </div>
        <div class="form-fields">
            <label for="firstName" class="field-label isrequired"><fmt:message key='common.firstname'/>:<span class="error">*</span></label>
            <input type="text" id="firstName" placeholder="First Name" name="firstName" maxlength="50" size ="31"/>
        </div>
        <div class="form-fields">
            <label for="lastName" class="field-label isrequired"><fmt:message key='common.lastname'/>:<span class="error">*</span></label>
            <input type="text" id="surName" placeholder="Last Name" name="surName" maxlength="50" size ="31"/>
        </div>
        
        <div class="form-fields">
            <label for="teacherId" class="field-label"><fmt:message key='common.teacherid'/>:</label>
            <input type="text" id="uniqueCommonIdentifier" placeholder="Teacher Id" name="uniqueCommonIdentifier" maxlength="50" size ="31"/>
        </div>        
    	
		<div id='createUserOrgFilter'></div>

        <div class="form-fields">
        	 <sc:captcha/>
		</div>
       <div class="btn-bar">
	        <button class ="btn_blue" id="createAccount"><fmt:message key='common.createaccount'/></button>
	        <button class ="btn_blue cancel" id="cancelRequest"><fmt:message key='label.common.cancel'/></button>         
       </div>
        
		<div style="border-top: 1px solid #f3f3f3; margin-top:10px;padding-top:10px;">Already have an account? <a href="${pageContext.request.contextPath}/logIn.htm">Sign In</a></div>
    </form>
    
	<div id="dialog-confirm" style="display:none;">
	A verification email has been sent to your email address with a confirmation link. Click on the link to verify your email address.
	</div>
</div>

<script type="text/javascript">

$(function() {
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		errorPlacement: function(error, element) {
			if(element.hasClass('required')) {
				error.insertAfter(element.next());
			}
			else {
	    		error.insertAfter(element);
			}
	    }
	});
	
	$('#createUserOrgFilter').orgFilter({ 
		url: '${pageContext.request.contextPath}/getAllStates.htm', 
		childOrgUrl: '${pageContext.request.contextPath}/getChildOrgsForFilter.htm',
		requiredLevels: [20],
		containerClass: ''
	});	
	
	var container = $('div .instructions');
	// validate the form when it is submitted
	var validator = $("#createAccountForm").validate({
		ignore: "",
		rules:{
			surName:'required', 
			firstName:'required', 
			lastName:'required',
			state:'required',  
			email: {
                required: true,
                email: true
            },
            confirmEmail: { 
            	required:true,
            	equalTo: "#email" 
            }, 
            password: {
            	required:true,  
            	minlength: 6
			}
		},
		messages: { 
			confirmEmail: {
				equalTo:"Email address should match with the field above"
			}
		},
		errorContainer: container
	});

	$("#cancelRequest").click(function() {
		$('#createAccountForm').trigger("reset");
		validator.resetForm();
		$('#createUserOrgFilter').orgFilter('reset');
	});
	$("#createAccount").click(function(){
		$('#msges').html('');
		
		if ($("#createAccountForm").valid()) {
			var params = $("#createAccountForm").serializeArray();
			params.push({
			    name: "organizationId",
			    value: $('#createUserOrgFilter').orgFilter('value')
			});
			$.ajax({
				url: "saveAccount.htm",
				data: params,
				dataType: 'json',
				type: 'POST',
				success: function(data) {
					if(data.msg=='DUPLICATE'){
						$('#msges').html('An account with the same email address already exists. Please login with that email address').show();
					}else if(data.msg=='INVALIDCAPCTHA'){
						//$('#msges').html('Please enter the words exactly as displayed').show();
						$('<label for="recaptcha_response_field" class="error">Please enter the words exactly as in picture.</label>').insertAfter($('#recaptcha_reload_btn'));
					}
					else{
						$('#msges').html('').hide();
						$("#dialog-confirm").dialog('open');
					}
				},
				error: function(jqXHR,textStatus,errorThrown) {
						alert( "Request failed: " + textStatus );		
				}
			});				 
		}		
	});
	
	$("#dialog-confirm").dialog({
		resizable: false,
		height: 150,
		width: 500,
		modal: true,
		autoOpen:false,
		title:'&nbsp;',
		buttons: {	    
		      OK: function() {
		    	 $(this).dialog("close");
		    	 window.location.href = '${pageContext.request.contextPath}/logIn.htm';
		    }
		}
	});
	//to clear form on load
	$("#cancelRequest").trigger("click");
});
</script>