<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix='security' uri='http://www.springframework.org/security/tags' %>  

<div class="_bcg"> 
	<div class="wrap_bcg"> 
	<div class="">
		<div class="formHeader"><p><fmt:message key='sessionTimeOut.msg'/></p></div> 
		<div> 
			<security:authorize access="hasRole('PERM_TESTSESSION_CREATE')"> 
	           <a class="btn_blue" href="javascript:refreshSession()"><fmt:message key='label.common.submit'/></a>
	       	</security:authorize> 				
		</div>   
	</div> 
	</div>
</div> 

<script>
var lastActivityTime = null;
$(function(){ 
	lastActivityTime  = new Date();
});

window.setInterval(closeSessionTimeoutPage, 1000);

function closeSessionTimeoutPage(){
	var currentTime = new Date();  
    if (currentTime.getTime() - lastActivityTime.getTime() >= 60000) {    
    	returnToLoginPage();
    }
}

function returnToLoginPage(){
	window.localStorage.clear();
	parent.location.href = '<%=request.getContextPath()%>' + '/j_spring_security_logout';  
	self.close();
}

function refreshSession(){ 
	//Refreshing the parent so that child loses its relationship with the parent and then it allows itself to be closed.
	 parent.location.reload(true);
	 self.close();	 
}
 
/*window.onbeforeunload = function(){
	if((window.event.clientX<0) || (window.event.clientY<0)) {
		returnToLoginPage();
	}
};*/

</script>