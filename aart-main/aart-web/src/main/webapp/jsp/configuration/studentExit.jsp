<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>

<div class="full_side">	
	<h1 class="panel_head sub">Select Organization</h1>
    <span class="panel_subhead">Specify organization level and click on Search.</span>
    <form id="UnrollviewStudentsOrgFilterForm">
		<div id="UnrollviewStudentsOrgFilter"></div>
	</form>
    <security:authorize access="hasAnyRole('PERM_STUDENTRECORD_VIEW')">
	 	<a class="panel_btn" href="#" id="UnrollviewStudentsButton">Search</a>
	</security:authorize>
</div>
<div class="full_main">
<p id="UnRollMessage"></p>
<span style="font-size: 17px;margin: 0 0 0 330px;">Select a student and click:</span>
<input type="button" id="UnrollviewStudentsContinue" value="Continue"/>
	<div id="UnrollviewStudentsGridCell">
		<div id="UnrollviewStudents" hidden="hidden"></div>
	 	<div id=UnrollviewStudentsGridContainer class="kite-table">
	 		<table class="responsive" id="UnrollviewStudentsByOrgTable"></table>
			<div id="previewViewStudentsByOrgTable" style="width: auto;"></div>
	 	</div>
	</div>
<input type="button" id="UnrollviewStudentsContinues" value="Continue"/>
	
	<div id="unRollviewStudentDetailsDiv" >
	<div></div>
	</div>
	
	<div id="Confirmdialog" title="Exit Student">
  	<p><span class="warning" style="color: red;">Warning!</span> Student will be unenrolled and removed from rosters. The student’s test sessions will become available once the student is transferred and rostered again at the same grade level. </p>
  	<div><p>Do you want to proceed?</p></div>
	</div>
</div>
	
<script type="text/javascript">

	var StudentExitLoadOnce = false;

	<security:authorize access="hasRole('PERM_STUDENTRECORD_CREATE')">
		var createStudentPermission = true;
	</security:authorize>

</script>