<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/tools/viewInternalUsers.js'/>"> </script>


<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on search</span>
    <form id="viewInternalUserOrgFilterForm">
		<div id="viewInternalUserOrgFilter"></div>
	</form>
	 	<a class="panel_btn" href="#" id="viewInternalUserButton">Search</a>
</div>
<div class="full_main">
	<label class="hidden error" id="messageViewInternalUsers"></label>
	<div id="viewInternalUserGridCell">
		<div id="viewInternalUser" hidden="hidden" class="hidden"></div>
	 	<div id="viewInternalUserGridContainer" class="kite-table">
	 		<table class="responsive" id="viewInternalUserGridTableId"></table>
			<div id="viewInternalUserGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>
<div id="viewinternalUsers" style="display: none;">
	<div id="editUserDetailsSucess" style="text-align: center; color: green"></div>
	<div id="editUserDetailsError" style="text-align: center; color: red"></div>
	<div id="internalUserDetails" style="float: left;">
		<table>
			<tr>
				<td><label>UserID:</label></td>
				<td><span id="internalUserId"></span></td>
			</tr>
			<tr>
				<td><label>FirstName:</label></td>
				<td><span id="firstName"></span></td>
			</tr>
			<tr>
				<td><label>LastName:</label></td>
				<td><span id="lastName"></span></td>
			</tr>
			<tr>
				<td><label>Email:</label></td>
				<td><span id="email"></span></td>
			</tr>
			<tr>
				<td><input type="checkbox" id="internalUser" name="internalUser" title="Internal User" >
				<label>Internal User</label> 
				</td>
			</tr>
		</table>
	</div>
</div>

<script type="text/javascript">
view_EditInternalUsers_tab();
</script>