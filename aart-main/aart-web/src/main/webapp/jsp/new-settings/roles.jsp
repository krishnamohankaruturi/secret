<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<security:authorize access="!hasAnyRole('PERM_ROLE_VIEW', 'PERM_ROLE_MODIFY', 'PERM_ROLE_CREATE', 'PERM_ROLE_DELETE', 'PERM_ROLE_SEARCH')">
<span class="error_message ui-state-error permissionDeniedMessage" id="rolesPermissionDeniedMessage" ><fmt:message key="error.permissionDenied"/></span>
</security:authorize>
<security:authorize access="hasAnyRole('PERM_ROLE_VIEW', 'PERM_ROLE_MODIFY', 'PERM_ROLE_CREATE', 'PERM_ROLE_DELETE', 'PERM_ROLE_SEARCH')">
<div id="rolesTabContent" >

	<ul id="roleNav" class="nav nav-tabs sub-nav">
			<security:authorize access="hasRole('PERM_ROLE_MODIFY')">
	        <li class="nav-item get-roles">	
	            <a class="nav-link" href="#editRoles" data-toggle="tab" role="tab"><fmt:message key='label.config.roles.select.edit'/></a> 
	        </li>
	        </security:authorize>
			<security:authorize access="hasRole('PERM_ROLE_VIEW')">
		 	<li class="nav-item get-roles">	
		 		<a class="nav-link" href="#viewRoles" data-toggle="tab" role="tab"><fmt:message key='label.config.roles.select.view'/></a>
		 		</li>
		 	</security:authorize>
	 </ul>
	       
	 <div id="tab">
		<div class="full_side">	
	    	<h1 class="panel_head sub">Select State and Assessment Program</h1>
			<span class="panel_subhead">Specify State and Assessment Program and click Search.</span>
			<form id="viewRolesOrgFilterForm" class="hidden">
				<div id="viewRolesOrgFilter">
					<div class="form-fields">
						<label for="stateOrgsViewRole" class="field-label">STATE:<span class="lbl-required">*</span></label>			
						<select id="stateOrgsViewRole" title="State" class="bcg_select required " name="stateOrgsViewRole">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields">
						<label for="assessmentProgramsOrgsViewRole" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
						<select id="assessmentProgramsOrgsViewRole" class="bcg_select required" name="assessmentProgramsOrgsViewRole">
							<option value="">Select</option>
						</select>
					</div>
		        </div>
			</form>
			 <form id="editRolesOrgFilterForm" class="hidden">
				<div id="editRolesOrgFilter">
					<div class="form-fields">
						<label for="assessmentProgramsOrgsEditRole" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
						<select id="assessmentProgramsOrgsEditRole" title="Assessment Program" class="bcg_select required" name="assessmentProgramsOrgsEditRole">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields">
						<label for="stateOrgsEditRole" class="field-label">STATE:<span class="lbl-required">*</span></label>			
						<select id="stateOrgsEditRole" title="State" class="bcg_select required " name="stateOrgsEditRole">
							<option value="">Select</option>
						</select>
					</div>
					<div>
					<input type="checkbox" class="Role_State_selectAll" id="stateOrgsEditRole_selectAll" title="Select_All" />Select All
					</div>
		        </div>
			</form>
			<a class="panel_btn" href="#" id="searchRolesButton">Search</a>
		</div>
	</div>
	<div class="full_main" style="padding: 25px 32px !important;" >
		<div class="tabTable">
			<div id="viewRoles">
		    	<div class="kite-table">
					<table class="responsive" id="viewRolesConfigTableId"></table>
					<div id="viewRolesConfigTableIdPager"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="managePermissionsDiv" class="hidden">
		<span class="dialog-title"></span>
		 <div class="_bcg">
			<div class="config wrap_bcg">
				<div id="groupIdentifier"><input id="groupId" type="hidden" /></div>
				<div id="rolesPermissionTabs">
					<security:authorize access="hasRole('PERM_ROLE_MODIFY')">
						<a class="btn_blue" id="saveRolesBtnTop" href="javascript:void(0)">Save</a>
					</security:authorize>
				</div>
			</div>
		</div>
	</div>
	
	<div class="hidden">
		<div id="successDialog">
			<p><span id="success" ><fmt:message key='label.config.roles.success'/></span></p>
		</div>
	</div>
	
	<div class="hidden">
		<div id="warningDialog">
			<p><span id="warningForClose" ><fmt:message key='label.config.roles.coloseWarning'/></span></p>
		</div>
	</div>
	
</div>
</security:authorize>

<script type="text/javascript">
//var gSaveRoster = false;
var gViewRolesLoadOnce = false;
var viewRolesReadOnly = true;
var gEditRolesLoadOnce = false;
$(function(){	
	$('#roleNav li.nav-item:first a').tab('show');	
	
	 $('li.get-roles').on('click',function(e){		 
	    	var clickedURL = $(this).find("a").attr('href');     
	    	rolesInitMethod(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });   	
	 var usersItemMenu = $('#roleNav li.nav-item:first a');
	  if(usersItemMenu.length>0){
	   var clickedURL = usersItemMenu.attr('href');     
	   rolesInitMethod(clickedURL.substring(1, clickedURL.length));
	  }	    
});
</script>
	<script type="text/javascript"src="<c:url value='/js/configuration/roles.js'/>">
</script>