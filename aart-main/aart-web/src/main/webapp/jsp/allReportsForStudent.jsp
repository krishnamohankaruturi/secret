<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>


<div id="allReportsStudentsByNameGridContainer" class="kite-table hidden" style="margin-left:25px;width: auto;">
	<table class="responsive" id="allReportsStudentsByNameGridTableId"></table>
	<div id="allReportsStudentsByNameGridPager" style="width: auto;"></div>
</div>
<div id="studentInfo" class="hidden all-reports-student-info">
	<label id="studentNameAndId"></label>
</div>
<div id="kapAllReportsForSSIDGrid" class="kite-table hidden" style="margin-left:25px;margin-bottom:20px;width: auto;">
	<div class="all-reports-assessmentprogram-header">
		<label id="KAPAssessementsLabel">Kansas Assessment Program</label>
	</div>
	<table class="responsive" id="kapAllReportsForGridTableId"></table>
	<br>
</div>
<div id="dlmAllReportsForSSIDGrid" class="kite-table hidden" style="margin-left:25px;margin-bottom:20px;width: auto;">
	<div class="all-reports-assessmentprogram-header">
		<label id="DLMAssessementsLabel" >Dynamic Learning Maps Assessments</label>
	</div>
	<table class="responsive" id="dlmAllReportsForGridTableId"></table>
	<br>
</div>
<div id="cpassAllReportsForSSIDGrid" class="kite-table hidden" style="margin-left:25px;width: auto;">
	<div class="all-reports-assessmentprogram-header">
		<label id="CPASSAssessementsLabel">Career Pathways Assessments</label>
	</div>
	<table class="responsive" id="cpassAllReportsForGridTableId"></table>
</div>
<script type="text/javascript" src="<c:url value='/js/allReportsForStudent.js'/>"></script>