<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript" src="<c:url value='/js/configuration/dlmTestLets.js'/>"> </script>

<%@ include file="/jsp/test/viewTestSessions.jsp"%>
<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on search</span>
    <form id="viewStudentsOrgFilterForm">
	  <div id="viewStudentsOrgFilter"></div>
	</form>
	<a class="panel_btn" href="#" id="viewStudentsButton">Search</a>
</div>
<div class="full_main">
	<div id="viewStudentsGridCell">
		<div id="viewStudentsByOrg" hidden="hidden"></div>
	 	<div id=viewStudentsGridContainer class="kite-table">
	 		<table class="responsive" id="viewStudentsByOrgTable"></table>
			<div id="pviewStudentsByOrgTable" style="width: auto;"></div>
	 	</div>
	</div>
	
	<div id="viewStudentDetailsDiv" >
		<div style="text-align: center;">
			<span id="resettestletmessage" style="border: none;" class="info_message ui-state-error hidden" > </span>
		</div>
	 	<div class ="table_wrap"> 
			<div class="kite-table"> 
				<table id="testSessionsTableId"  class="responsive"></table>
				<div id="ptestSessionsTableId" class="responsive"></div>
			</div> 
	 	</div> 
	</div>
</div>
<script>
viewStudentsInitForDLMTestLet();
</script>
