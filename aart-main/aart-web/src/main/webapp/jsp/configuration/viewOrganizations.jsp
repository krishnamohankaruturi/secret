<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
#manageOrganization .manageIcon{
background-image: url('images/EP-manageIcon.png');
width: 26px;
height: 23px;
background-size: 24px;
margin-left: 3px;
}
.ui-widget-overlay{
opacity: -0.1 !important;
}
.ui-dialog-buttons{
left:531px !important;
top:323px !important;
}
.organizationDialogPosition{
top:170px !important;
}
</style>
 <div>
	<span class="error_message ui-state-error permissionDeniedMessage hidden" id="manageOrganizationFailedMessage" >Manage Organization Failed</span>
    <span class="info_message ui-state-highlight successMessage hidden" id="manageOrganizationSuccessMessage" >Organization change request received. Changes will be reflected shortly</span>
</div>	
<div  class="full_side">
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="viewOrgFilterForm">
		<div id="viewOrgOrgFilter"></div>
	</form>
    <security:authorize access="hasAnyRole('PERM_ORG_VIEW')">
	 	<a class="panel_btn" href="#" id="viewOrgButton">Search</a>
	 	<div id="progressbarViewOrg"></div>
	 </security:authorize>
   </div>
  <div style="color:red;display:none;" id="manageOrgErrorMsg">Please select a contracting organization to manage</div>
  <div style="color:red;display:none;" id="editOrgErrorMsg"> Please select a non-contracting organization to edit</div> 
<div class="full_main">
	<div id="viewOrgGridCell" >
		<div id="viewOrg" hidden="hidden"></div>
	 	<div id=viewOrganizationGridContainer class="kite-table">
	 		<table class="responsive" id="viewOrgGridTableId"></table>
			<div id="viewOrgGridPager" style="width: auto;"></div>
	 	</div>
	</div>
	<div id="editOrganizationDiv" ></div>
	<div id="viewOrganizationDiv" ></div>
	<div id="manageOrganizationDiv"></div>
</div>

<script type="text/javascript" >
	var gViewOrganizationLoadOnce = false;
		var editPermission = false;
		var managePermission = false;
	$(function() {
		<security:authorize access="hasRole('PERM_ORG_MODIFY')">
		   editPermission = true;		
		</security:authorize>
		
		<security:authorize access="hasRole('PERM_ORG_MANAGE')">
		   managePermission = true;		
		</security:authorize>
	});

	   
</script>

