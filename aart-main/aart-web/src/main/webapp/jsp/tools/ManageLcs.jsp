<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript" src="<c:url value='/js/configuration/manageLcs.js'/>"> </script>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on search</span>
    <form id="viewStudentsOrgFilterFormManageLCS">
	  <div id="viewStudentsOrgFilterManageLCS"></div>
	</form>
	<a class="panel_btn" href="#" id="viewStudentsButton1">Search</a>
</div>

<div class="full_main">
	<div style="text-align: center; margin: 1%;">
		<span id="breaklcsmessage"> </span>
	</div>
  <div class="cssculomn">
  <form id="studentidform">
	  <div class="form-gorup">
	  <label>Student State Identifier:
	  <input type="text" id="state_student_identifier_id">
	  
	  </label>
	  </div>
	  <div class="form-gorup">
	  	<input type="button" id="js-breaklcswithstatestudentidentifier" value="Break LCS">
	  </div>
	</form>
  </div>
  <div class="cssculomn">
  <form id="lcsidform">
	  <div class="form-gorup">
	  <label>LCS Id:
	  <input type="text" id="lcs_id">
	  </label>
	  </div>
	  <div class="form-gorup">
	  	<input type="button" id="js-breaklcswithlcsid" value="Break LCS">
	  </div>
	</form>
  </div>
</div>

<div class="full_main">
	<div id="viewStudentsGridCellManageLCS">
		<div id="viewStudentsManageLCS" hidden="hidden"></div>
	 	<div id=viewStudentsGridContainerManageLCS class="kite-table">
	 		<table class="responsive" id="viewStudentsByOrgTableManageLCS"></table>
			<div id="pviewStudentsByOrgTableManageLCS" style="width: auto;"></div>
	 	</div>
	</div>
 	<div id="viewStudentDetailsDivManageLCS" >
 	<div style="text-align: center;">
		<span id="breaklcsgridmessage" style="border: none;" class="info_message ui-state-error hidden" > </span>
	</div>
	<div class ="table_wrap">
		<div class="kite-table">
			<table id="testSessionsTableIdManageLCS"  class="responsive"></table>
			<div id="ptestSessionsTableIdManageLCS" class="responsive"></div>
		</div>
	</div>
	</div> 
</div>
<script>
viewStudentsInitForStudentLCS();
</script>
