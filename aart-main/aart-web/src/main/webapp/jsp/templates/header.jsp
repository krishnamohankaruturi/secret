<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="hdr">
	<div class="wrap_bcg">
	<a class="logo_left" href="userHome.htm"><img src="images/${imageFolderName}/kite_logo_2018.png" alt="Kite" class="logo_img"/></a>
	<ul class="login_info">
		<li class="current">Logged in as 
			 <span id="currentDisplayName">
				<c:if test="${empty user.displayName}"> 
					<c:out value="${user.firstName}"/>&nbsp;<c:out value="${user.surName}"/>,
				</c:if>
				<c:if test="${not empty user.displayName}"> 
					<c:out value="${user.displayName}"/>,
				</c:if>
			</span>
			 <security:authorize access="isAuthenticated()">
	<span id="log_status"> 
		<a href="j_spring_security_logout" onClick="window.localStorage.clear();"><fmt:message key="label.userHome.sigout" /></a>
		 </span> 
		</security:authorize> 
	</li>
 	<li class="switchRoleContainer">
	    <div>
	    	<span> <label for="userDefaultGroup" class="form-label"><fmt:message key="label.home.role.select"/>:</label>
				<select id="userDefaultGroup" class="switchRole" title="user Default Group">
					<c:forEach items="${user.userGroups}" var="userGroup" varStatus="status">
			 			<c:set var="selectedGroupFlag" scope="page" value=""></c:set>
						<c:if test="${userGroup.groupId eq user.currentGroupsId}">
							<c:set var="selectedGroupFlag" scope="page" value="selected='selected'"></c:set>
						</c:if>
			 			<option ${selectedGroupFlag} value="${userGroup.groupId}"><c:out value="${userGroup.groupName}"/></option>
					</c:forEach>
	 			</select>
 			</span>
 		</div>
 	</li>
	<li class="switchRoleContainer">
		<div>
			<span><label for="userDefaultOrganization" class="form-label"><fmt:message key="label.home.organization.select"/>:</label>
				<select id="userDefaultOrganization" class="switchRole" title="user Default Organization">
					<c:forEach items="${user.userOrganizationsGroups}" var="userOrganizationsGroup" varStatus="status">
						<c:set var="selectedOrganizationFlag" scope="page" value=""></c:set>
						<c:if test="${userOrganizationsGroup.group.groupId eq user.currentGroupsId}">
				 			<c:if test="${userOrganizationsGroup.organization.id eq user.currentOrganizationId}">
								<c:set var="selectedOrganizationFlag" scope="page" value="selected='selected'"></c:set>
				 			</c:if>
		     				<option ${selectedOrganizationFlag} value="${userOrganizationsGroup.organization.id}"><c:out value="${userOrganizationsGroup.organization.organizationName}"/></option>
	     				</c:if>
		     		</c:forEach>
				</select>
			</span>
		</div>		
	</li>
	<li class="switchRoleContainer">
		<div>
			<span><label for="userDefaultAssessmentProgram" class="form-label"><fmt:message key="label.home.assessmentprogram.select"/>:</label>
			<select id="userDefaultAssessmentProgram" class="switchRole" title="User Default Assessment Program">
       			<c:forEach items="${user.userAssessmentPrograms}" var="userAssessmentProgram" varStatus="status">
       				<c:set var="selectedAssessmentProgramFlag" scope="page" value=""></c:set>
       				<c:if test="${userAssessmentProgram.id eq user.currentAssessmentProgramId}">
						<c:set var="selectedAssessmentProgramFlag" scope="page" value="selected='selected'"></c:set>
					</c:if>
					<option ${selectedAssessmentProgramFlag} value="${userAssessmentProgram.id}"><c:out value="${userAssessmentProgram.abbreviatedname}"/></option>
				</c:forEach>
			</select>
			</span>
		</div>		
	</li>
</ul>
	</div><!-- /bcg wrap --> 
	<!-- <div class="wrap_bcg">
		
	</div> -->
</div>
<div class="wrap_bcg">
		
	</div>
<!-- /hdr -->

<!-- The Below commentted code is old header -->		
<!-- 
<div id="header_id">
    <div id="logo_div">
        <img src="<c:url value='/images/logo.png'/>"/>
    </div>
    <security:authorize access="isAuthenticated()">
	    <div id="log_status">
	        <a href="j_spring_security_logout">Logout</a>
	    </div>
    </security:authorize>
</div>
-->
<input id="currentUserIdFromHeader" type="hidden" value="${user.id}"/>
<input id="userCurrentOrganizationType" type="hidden" value="${user.currentOrganizationType}"/>
<input id="hiddenCurrentGroupsId" type="hidden" value="${user.currentGroupsId}" />
<input id="hiddenCurrentOrganizationId" type="hidden" value="${user.currentOrganizationId}" />
<input id="hiddenCurrentAssessmentProgramId" type="hidden" value="${user.currentAssessmentProgramId}" />
<script type="text/javascript" src="<c:url value='/js/templates/header.js' />"></script>
<script>
$(function(){
	$('#userDefaultGroup, #userDefaultOrganization, #userDefaultAssessmentProgram').select2({
		placeholder:'Select',
		multiple: false,
		selectedList: 1,
		header: ''
	});
	
	$('#userDefaultGroup').on("change",function(){
		$('#userDefaultAssessmentProgram').prop('disabled', true);
		$('#userDefaultOrganization').prop('disabled', true);
		changeDefaultOrganization($(this).val(), 'userDefaultOrganization');
	});
	$('#userDefaultOrganization').on("change",function(){
		$('#userDefaultGroup').prop('disabled', true);
		$('#userDefaultAssessmentProgram').prop('disabled', true);
		changeDefaultAssessmentProgram($(this).val(), 'userDefaultAssessmentProgram');
	});
	// Fix for DE14015
	var uniqueOrgs = [];
	$('#userDefaultOrganization').find('option').each(function(i){
		if(uniqueOrgs.indexOf($(this).val()) !== -1){
			$(this).remove();
		}
		uniqueOrgs.push($(this).val());
	});
});
</script>