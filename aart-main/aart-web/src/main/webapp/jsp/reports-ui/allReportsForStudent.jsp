<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<div class="all-reports-for-student-title">
	<h4 id="allReportsStudentsTitle" class="all-reports-for-student-title"><fmt:message key="label.allreports.title"/></label>
</div>
<div class="all-reports-for-student-search-padding">
	<security:authorize access="hasRole('VIEW_ALL_STUDENT_REPORTS')">
		<div id="allReportsForStudentSearch">
			<form id="viewAllReportsForStudentForm">
				<img alt="search" src="images/search.png">
				<input id="studentName" type="text" readonly="true" class="input-disabled" placeholder="Student Last Name" title="Student Last Name"/> 
				or 
				<input id="stateStudentIdentifier" type="text" class="" placeholder="Student State ID" title="Student State ID"/> 
				<a class="panel_btn" href="#" id="searchForStudent" style="padding: 5px 10px;font-size: 0.85em;"><fmt:message key="label.common.search"/></a>
				<label id="allReportsForStudentSearchError" class="error hidden"></label>
			</form>
		</div>
		<div class="reportsDisplayTable">
			<div id="allReportsStudentsByNameGridContainer" class="kite-table hidden">
				<label id="allReportsStudentsByNameGridTableLabel" for="allReportsStudentsByNameGridTableId" class="all-reports-assessmentprogram-header"><fmt:message key="label.allreports.search.results"/></label>
				<table class="responsive" id="allReportsStudentsByNameGridTableId"></table>
				<div id="allReportsStudentsByNameGridPager"></div>
			</div>
			<div id="studentInfo" class="hidden all-reports-student-info">
				<label id="studentNameAndId"></label>
			</div>
			<div id="kapAllReportsForSSIDGrid" class="kite-table hidden">
				<div class="all-reports-assessmentprogram-header">
					<label id="KAPAssessementsLabel"><fmt:message key="label.allreports.kap.results"/></label>
				</div>
				<table class="responsive" id="kapAllReportsForGridTableId"></table>
				<br>
			</div>
			<div id="dlmAllReportsForSSIDGrid" class="kite-table hidden">
				<div class="all-reports-assessmentprogram-header">
					<label id="DLMAssessementsLabel" ><fmt:message key="label.allreports.dlm.results"/></label>
				</div>
				<table class="responsive" id="dlmAllReportsForGridTableId"></table>
				<br>
			</div>
			<div id="cpassAllReportsForSSIDGrid" class="kite-table hidden">
				<div class="all-reports-assessmentprogram-header">
					<label id="CPASSAssessementsLabel"><fmt:message key="label.allreports.cpass.results"/></label>
				</div>
				<table class="responsive" id="cpassAllReportsForGridTableId"></table>
			</div>
		</div>
	</security:authorize>
	<input type="hidden" id="enter-two-chars-error" value="<fmt:message key='label.allreports.two.chars.error'/>" />
	<input type="hidden" id="enter-input-error" value="<fmt:message key='label.allreports.enter.input.error'/>" />
	<input type="hidden" id="report-not-available" value="<fmt:message key='label.allreports.report.not.available'/>" />
	<input type="hidden" id="ssid-does-not-exist" value="<fmt:message key='label.allreports.ssid.not.exist'/>" />
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/allReportsForStudent.js"></script>