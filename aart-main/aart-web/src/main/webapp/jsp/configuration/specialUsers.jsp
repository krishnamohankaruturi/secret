<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userSpecial.js'/>"> </script>
<style>
.ui-widget-overlay{
opacity:-0.6 !important;
}
#specialUsers .full_side{
	margin-right: 30px;
}

</style>
<div class="full_side">	
	<h1 class="panel_head sub">Select sepecial Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="specialUserOrgFilterForm">
		<div id="specialUserOrgFilter"></div>
	</form>
    <security:authorize access="hasAnyRole('PERM_USER_SPECIAL')">
	 	<a class="panel_btn" href="#" id="specialUserButton">Search</a>
	</security:authorize>
</div>
<div class="full_main">
	<div id="specialUserGridCell">
		<div id="specialUser" hidden="hidden" class="hidden"></div>
	 	<div id="specialUserGridContainer" class="kite-table">
	 		<span style="margin-left:250px" id="specialUserSpan" class='hidden'> <label>Select only TWO users special, then click</label><button class="panel_btn"  id="specialUserNextButton" style="margin-left:20px"><fmt:message key="label.config.user.create.next"/></button></span>
	 		<table class="responsive" id="specialUserGridTableId"></table>
			<div id="specialUserGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>

<script>
	var special_User_Select_Option_Loadonce = false;
</script>