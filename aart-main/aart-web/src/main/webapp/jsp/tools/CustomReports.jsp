<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<script type="text/javascript" src="<c:url value='/js/configuration/customReports.js'/>"> </script>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on search</span>
    <form id="viewStudentsOrgFilterFormCustomReports">
	  <div id="viewStudentsOrgFilterCustomReports"></div>
	</form>
	<a class="panel_btn" href="#" id="viewStudentsButtonCustomReports">Search</a>
</div>
<div class="full_main">
	<div id="viewStudentsGridCell">
		<div id ="customReportsViewStudents" hidden="hidden"></div>
	 	<div id=viewStudentsGridContainer class="kite-table">
	 		<table class="responsive" id="viewStudentsByOrgTableCustomReports"></table>
			<div id="pviewStudentsByOrgTableCustomReports" style="width: auto;"></div>
	 	</div>
	 	<div id="studentReportDownloadLink" style="margin-top:2%;display:none"><a id="js-linkfordownloadreports" style="color:#0254eb">Generate Link For Custom Extract</a></div>
	</div>
	<div id="viewStudentDetailsDivCustomReports" >
	 <div class ="table_wrap"> 
	<div class="kite-table"> 
		<table id="testSessionsTableIdCustomReports"  class="responsive"></table>
		<div id="ptestSessionsTableIdCustomReports" class="responsive"></div>
	</div> 
 </div> 
	</div>
</div>
<script>
viewStudentsInitForCustomReports();
</script>
