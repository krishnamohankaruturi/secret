<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div id="rostersTabContent" style="clear:both;">	
	<div class="form action-bar">
		<div class="form-fields">
			<label for="rosterSelect" class="isrequired form-label">Select Action<font size="5" color="red">*</font>:</label>			
			<select id="rosterSelect" class="bcg_select" name="rosterSelect">
				<option value="" selected="selected">Select</option>
				<security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL')">
			  	<option value="view">View Roster</option>
			  	</security:authorize>
			  	<security:authorize access="hasRole('PERM_ROSTERRECORD_UPLOAD')">
			  			<option value="upload">Upload Roster</option>
			  	</security:authorize>
			  	<security:authorize access="hasRole('PERM_ROSTERRECORD_CREATE')">
						<option value="create">Create Roster</option>
			  	</security:authorize>
			</select>
			<div id="rosterMessage" class="config error placement" hidden="hidden"></div>
		</div>
	</div>

	<div class="tabTable">
		<div id ="viewRoster" hidden="hidden">
			<jsp:include page="viewRosters.jsp"></jsp:include> 
		</div>
		
		<div id = "uploadRoster" hidden="hidden"> 
			<jsp:include page="uploadRosters.jsp"></jsp:include>
		</div>
		
		<div id = "createRoster" hidden="hidden"> 
			<jsp:include page="createRosters.jsp"></jsp:include>
		</div>
	</div>
</div>

<script type="text/javascript"
	src="<c:url value='/js/configuration/viewRosters.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/uploadRosters.js'/>"> </script>
<script type="text/javascript"
	src="<c:url value='/js/configuration/createRosters.js'/>"> </script>
	
	
<script type="text/javascript">
	var isDLMUser = false; 
	$(function(){
		var userAP = '${user.currentAssessmentProgramName}';
		if(userAP == 'DLM')
			isDLMUser = true;
	});

</script>