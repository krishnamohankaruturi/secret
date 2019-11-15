<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userMove.js'/>"> </script>
<style>
.ui-widget-overlay{
opacity:-0.6 !important;
}
#moveUsers .full_side{
	margin-right: 30px;
}

</style>
<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="moveUserOrgFilterForm">
		<div id="moveUserOrgFilter"></div>
	</form>
    <security:authorize access="hasAnyRole('PERM_USER_MOVE')">
	 	<a class="panel_btn" href="#" id="moveUserButton">Search</a>
	</security:authorize>
</div>
<div class="full_main">
	<span style="margin-left:274px" id="moveUserSpan"><label>Select users needed to move, then click</label><button class="panel_btn"  id="moveUserNextButton" style="margin-left:20px; margin-top:10px; margin-bottom:10px;"><fmt:message key="label.config.user.create.next"/></button></span>
	<div id="moveUserGridCell">
		<div id="moveUser" hidden="hidden" class="hidden"></div>
	 	<div id="moveUserGridContainer" class="kite-table">
	 		<table class="responsive" id="moveUserGridTableId"></table>
			<div id="moveUserGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>
<div id="moveUserOrganizationInfo">
	<jsp:include page="moveUserOrganizationInfo.jsp"></jsp:include>
</div>

<script>
	var move_User_Select_Option_Loadonce = false;
</script>