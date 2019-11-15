<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<script type="text/javascript" src="<c:url value='/js/userMerge.js'/>"> </script>
<style>
#mergeUsers .full_side{
	margin-right: 30px;
}
</style>
<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="mergeUserOrgFilterForm">
		<div id="mergeUserOrgFilter"></div>
	</form>
    <security:authorize access="hasAnyRole('PERM_USER_MERGE')">
	 	<a class="panel_btn" href="#" id="mergeUserButton">Search</a>
	</security:authorize>
</div>
<div class="full_main">
	<div id="mergeUserGridCell">
		<div id="mergeUser" hidden="hidden" class="hidden"></div>
	 	<div id="mergeUserGridContainer" class="kite-table">
	 		<span style="margin-left:250px" id="mergeUserSpan" class='hidden'> <label>Select only TWO users to merge, then click</label><button class="panel_btn"  id="mergeUserNextButton" style="margin-left:20px"><fmt:message key="label.config.user.create.next"/></button></span>
	 		<table class="responsive" id="mergeUserGridTableId"></table>
			<div id="mergeUserGridPager" style="width: auto;"></div>
	 	</div>
	</div>
</div>

<script>
	var merge_User_Select_Option_Loadonce = false;
</script>