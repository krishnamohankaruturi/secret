<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userEdit.js'/>"> </script>
<!-- Added due to US16245 : for permission purpose -->
<script type="text/javascript">
  <security:authorize access="hasRole('PERM_USER_ACTIVATE')">
	var activateUserPermission = true;
  </security:authorize>
  <security:authorize access="hasRole('PERM_USER_INACTIVATE')">
	var inactivateUserPermission = true;
  </security:authorize>
  var view_User_Select_Option_Loadonce = false;
</script>

<script type="text/javascript" src="<c:url value='/js/userView.js'/>"> </script>
<style>
/* ._bcg .kite-table table tr.altrow {
    background: none repeat scroll 0 0 #e5e5e5 !important;
} */

#viewUsers .full_side{
	margin-right: 30px;
}

#viewUsers .full_main{
	margin-top: 2.5%;
}

</style>
<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="viewUserOrgFilterForm">
		<div id="viewUserOrgFilter"></div>
	</form>
    <security:authorize access="hasAnyRole('PERM_USER_VIEW')">
    	<input type="checkbox" id="showInactiveUsers" name="showInactiveUsers" title="Include Inactive Users" style = "margin: 19px 5px 0px 3px;">Include Inactive Users
	 	<a class="panel_btn" href="#" id="viewUserButton" title="Search">Search</a>
	</security:authorize>
</div>
<div class="full_main">
<label class="hidden error" id="viewUsermessage"></label>
	<div id="viewUserGridCell">
		<div id="viewUser" hidden="hidden" class="hidden"></div>
	 	<div id="viewUserGridContainer" class="kite-table">
	 	    <div class ="message"></div>
	 		<table class="responsive" id="viewUserGridTableId"></table>
			<div id="viewUserGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>

	