<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<link rel="stylesheet" href="<c:url value='/css/external/jquery-ui-1.8.19.custom.min.css'/>" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/css/theme/recordBrowser.css" rel="stylesheet">        
<link rel="stylesheet" href="<c:url value='/css/custom.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/css/screen.css'/>" type="text/css" />
<link type="text/css" href="${pageContext.request.contextPath}/css/bcg-jqGrid.css" rel="stylesheet">
<script type="text/javascript" src="<c:url value='/js/external/jquery-ui-1.8.19.custom.min.js'/>"> </script>
<script type="text/javascript" src="<c:url value='/js/external/jquery.validate.js'/>"> </script>

<div class="panel_center">
<c:choose>
	<c:when test="${not empty Error }">${Error}</c:when>
	<c:otherwise>
	    <div style="border-bottom: 1px solid #f3f3f3; "><h1 class="panel_center_hdr"><fmt:message key='common.newpassword'/></h1></div>
	   	<div class="instructions"></div>
		<div id="msges" class="error"></div>
	    <form id="createAccountForm" class="form ca-form">
		    <input type="hidden" name="auth" value="${param.auth}"> 
		    <div class="form-fields">
		    	<div></div>
		        <label class="field-label" for="password"><fmt:message key='common.password'/>:<span class="error">*</span></label>
		        <input  type="password" id="password" placeholder="Password" name="password" maxlength="50"/>
		    </div><!-- /input -->
	    
	    	<div class="form-fields">
	    		<div></div>
	        	<label class="field-label" for="cfrmPassword"><fmt:message key='common.confirmpassword'/>:<span class="error">*</span></label>
		        <input type="password" id="cfrmPassword" placeholder="Confirm Password" name="cfrmPassword" maxlength="50"/>
	    	</div><!-- /input -->                   
		    
		    <div class="btn-bar">
		         <button class ="btn_blue" id="newPassword"><fmt:message key='common.submit'/></button>
		    </div>
		</form>
	</c:otherwise>
</c:choose>

<div id="dialog-confirm" style="display:none;">
Successfully Created Account.
</div>
</div><!-- /signin_form -->

<script type="text/javascript">
	jQuery.validator.setDefaults({
		submitHandler: function() {		
		},
		//errorPlacement: function(error, element) {
	    	//error.appendTo(element.parent().find('span.error'));
	    //}
	});

	$(function() {	
		var container = $('div.instructions');
		// validate the form when it is submitted
		$("#createAccountForm").validate({
			rules:{			
	            cfrmPassword:{ required:true,equalTo: "#password" },
	            password:{required:true,  minlength: 6}
			},
			messages: { cfrmPassword :{equalTo:"Password should match with the field above"} },
			errorContainer: container
		});
	
		$("#newPassword").click(function(){
			$('#msges').html('');
			if ($("#createAccountForm").valid()) {
				var request=$.ajax({
					url: "newPassword.htm",
					data: $("#createAccountForm").serialize(),
					dataType: 'json',
					type: 'POST',
					success: function(data) {
						if(data.ERROR!=null){
							$('#msges').html('Failed to activate your account.');						
						}else{
							$('#msges').html('');
							$("#dialog-confirm").dialog('open');						
						}
					}
				});				 
				request.fail(function(jqXHR, textStatus) {
			    	alert( "Request failed: " + textStatus );
				});
		 	}		
		});
	});
	
	$("#dialog-confirm").dialog({
		resizable: false,
		height: 150,
		width: 300,
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
</script>