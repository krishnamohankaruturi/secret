<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/jsp/include.jsp" %>

<div class="wrap_bcg">

	<div class="footer"> 
	<security:authorize access="hasAnyRole('CETE_SYS_ADMIN','PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_VIEWALL','PERM_STUDENTRECORD_VIEW',
		'PERM_TESTSESSION_VIEW','QUALITY_CONTROL_COMPLETE')">
		<div id="feedbackDiv"></div>
		<div class="conform_feedback-div" style="position: fixed; top: 100px; left: 400px; display: none;text-align: center;">
			<input type="hidden" id="cancelmessageId" />
			<p>Thank you!  Your feedback has been successfully submitted</p>
			<button type="button" id="thanksforfeedback" class="btn"
				style="margin-top: 10px; height: 35px;">Ok</button>
		</div>
	</security:authorize>
	
		<c:set var="now" value="<%=new java.util.Date()%>" />  
		<fmt:formatDate pattern="yyyy" value="${now}" var="cyear"/>
	    <p class="copyright">
	    <security:authorize access="hasAnyRole('CETE_SYS_ADMIN','PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_VIEWALL','PERM_STUDENTRECORD_VIEW',
	    	'PERM_TESTSESSION_VIEW','QUALITY_CONTROL_COMPLETE')">
	   		<a class="feedbacklink" style="display:none"><fmt:message key="label.nav.feedback.link"/></a>
	    </security:authorize>
		<c:if test="${imageFolderName eq 'PLTW'}"> 
			<fmt:message key="label.logIn.copyright" ><fmt:param value="${cyear}"/></fmt:message><img src="images/${imageFolderName}/kite_footer_logo_2018.png" alt="Kite" style="vertical-align: middle;border-style: none;"></p>
		</c:if>
		<c:if test="${imageFolderName eq 'Kite'}"> 
			<fmt:message key="label.logIn.copyright" ><fmt:param value="${cyear}"/></fmt:message></p>
		</c:if>
		
	    
	
	
    <div class="sitemap"><security:authorize access="hasAnyRole('CETE_SYS_ADMIN','PERM_ROSTERRECORD_VIEW','PERM_ROSTERRECORD_VIEWALL',
    	'PERM_STUDENTRECORD_VIEW','PERM_TESTSESSION_VIEW','QUALITY_CONTROL_COMPLETE')">
			<!-- 	<li  class="last"> --><a href="<c:url value='/siteMap.htm'/>"><fmt:message key="label.nav.sitemap"/></a><!-- </li> -->
			</security:authorize>
	</div>
    
	</div> <!-- /footer -->
</div>	
<script src="${pageContext.request.contextPath}/js/external/ejs.js"></script>
<script type="text/javascript">
$(function(){
	 var currentAssessmentProgramName = '${user.currentAssessmentProgramName}';
	if((document.location.pathname == "/AART/interim.htm") && (currentAssessmentProgramName != 'PLTW')){
		$('.feedbacklink').show();
	}else{
		$('.feedbacklink').hide();
	}
	$('.feedbacklink').on('click',function(ev){
    	$('#feedbackDiv').html(new EJS({
			url : '/AART/js/views/epfeedback.ejs'
		}).render({
			
		}));
    var feedbackDialog = $('#feedbackDiv').dialog({
			autoOpen: false,
			modal: true,
			resizable: true,
			width: $(window).width()-480,
	        height: $(window).height()-80,
			title: "Educator Portal Feedback",
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
			},
			close: function(ev, ui) {				
			    $(this).html('');
			},
			open: function(ev, ui) {
			$('#feedback_cancel').off('click').on('click',function(ev){
					ev.preventDefault();
		    		feedbackDialog.dialog('close');
		    	});
			}
		}).dialog('open');
	});
    function validateEmail(email) { 
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-\_0-9]+\.)+[a-zA-Z]{2,}))$/;
    	return re.test(email);
    }
})
</script>
