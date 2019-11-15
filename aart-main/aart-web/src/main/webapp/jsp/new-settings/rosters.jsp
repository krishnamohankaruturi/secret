<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.select2-container{
width:250px !important;
}
</style>
<div>
	<ul id="rosterNav" class="nav nav-tabs sub-nav">
	    <security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL')">
			<li class="nav-item get-roster">
				<a class="nav-link" href="#view-roster" data-toggle="tab" role="tab">View Roster</a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_ROSTERRECORD_CREATE')">
			<li class="nav-item get-roster">
				<a class="nav-link" href="#create-roster" data-toggle="tab" role="tab">Create Roster</a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_ROSTERRECORD_UPLOAD')">
			<li class="nav-item get-roster">
				<a class="nav-link" href="#upload-roster" data-toggle="tab" role="tab">Upload Roster</a>
			</li>
		</security:authorize>		
		
	</ul>
	
	<div id="content" class="tab-content">
		<security:authorize access="hasRole('PERM_ROSTERRECORD_CREATE')">
			<div id="create-roster" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/createRosters.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/createRosters.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_ROSTERRECORD_UPLOAD')">
			<div id="upload-roster" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/uploadRosters.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/uploadRosters.js'/>"></script>
			</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL')">
			<div id="view-roster" class="tab-pane" role="tabpanel">
				<jsp:include page="../configuration/viewRosters.jsp"/>
				<script type="text/javascript" src="<c:url value='/js/configuration/viewRosters.js'/>"></script>
			</div>
		</security:authorize>
		
		
	</div>
</div>

<script type="text/javascript" src="<c:url value='/js/configuration/rosters.js'/>"></script>

<script>
var isDLMUser = false;
var userAP = '${user.currentAssessmentProgramName}';
isDLMUser = '${user.currentAssessmentProgramName}' == 'DLM' || '${user.currentAssessmentProgramName}' == 'I-SMART' || '${user.currentAssessmentProgramName}' == 'I-SMART2';

$(function(){	
	$('#rosterNav li.nav-item:first a').tab('show');	
	 $('li.get-roster').on('click',function(e){
	    	var clickedURL = $(this).find("a").attr('href');     
	    	rostersInitNew(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });
	 
		var rostersItemMenu = $('#rosterNav li.nav-item:first a');
		if(rostersItemMenu.length>0){
			var clickedURL = rostersItemMenu.attr('href');     
			rostersInitNew(clickedURL.substring(1, clickedURL.length));
		}
});
</script>