<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>
 
 
<div>
	<div class="breadcrumb">
		<p><a href="#" onClick="backToTestCoordination()"> &lsaquo;back </a></p>
		<h1><label id ="breadCrumMessage">Test Coordination : ${testSessionName} - Students</label></h1>
		<h2><label id="breadCrumMessageTag"></label></h2>		
	</div>
		
	<div>
		<ul id="setupAutoRegisteredTestSessionTabs" class="nav nav-tabs sub-nav">
			<li class="nav-item">
				<a class="nav-link" href="#tabs_AutoRegistered_students" onClick="javascript:loadStudentsTab(); return false;" data-toggle="tab" role="tab"><fmt:message key="label.testsession.students" /></a>
			</li>	
			<security:authorize access="hasRole('TEST_COORD_TESTSESSION_MONITOR')" >
			<li class="nav-item">
				<a class="nav-link" href="#tabs_AutoRegistered_Monitor" onClick="javascript:loadMonitorTab(); return false;" data-toggle="tab" role="tab"><fmt:message key="label.testsession.monitor" /></a>
			</li>
			</security:authorize>
			<security:authorize access="hasRole('TEST_COORD_TESTSESSION_SCORING')" >
			<li class="nav-item">
				<a class="nav-link" href="#tabs_AutoRegistered_Scores" onClick="javascript:loadScoresTab(); return false;" data-toggle="tab" role="tab"><fmt:message key="label.testsession.scores" /></a>
			</li>
			</security:authorize>	
		</ul>
		
		<div id="content" class="tab-content">
			<div id="tabs_AutoRegistered_students" class="tab-pane" role="tabpanel">
				<jsp:include page="viewAutoRegisteredTSStudents.jsp" />
			</div>	
			<security:authorize access="hasRole('TEST_COORD_TESTSESSION_MONITOR')" >
			<div id="tabs_AutoRegistered_Monitor" class="tab-pane" role="tabpanel">
				<jsp:include page="viewTestSessionMonitor.jsp" />
			</div>
			</security:authorize>
			<security:authorize access="hasRole('TEST_COORD_TESTSESSION_SCORING')" >
			<div id="tabs_AutoRegistered_Scores" class="tab-pane" role="tabpanel">
				<jsp:include page="viewAutoRegisteredTSScores.jsp" />
			</div>
			</security:authorize>
		</div>
	</div>
</div>

<input id="gridInfo" value="${gridInfo}" type="text" class="hidden" />
<input id="testSessionName1" value ="${fn:escapeXml(testSessionName)}" type="text" class="hidden" />	
<input id="testSessionType" value="${testSessionType}" type="hidden"/>
<input id="currentStageName" value="${currentStageName}" type="hidden" />
<input id="nextStageName" value="${nextStageName}" type="hidden" />
<script type="text/javascript" src="<c:url value='/js/configuration/testCoordination.js'/>"> </script>
<script>
  	//Setting up the tabs.
	$(function() {
		$('.nav-tabs a[href="#tabs_AutoRegistered_students"]').tab('show');	
		loadStudentsTab();
	}); // end of document ready
	
	
	function loadMonitorTab(){
		$('#breadCrumMessage').text("Test Coordination : "+$('#testSessionName1').val()+" - Monitor");
		$('#breadCrumMessageTag').text("");
		gridsReset();
		<security:authorize access="hasRole('TEST_COORD_TESTSESSION_MONITOR')" >
			loadMonitorTestSessionData();
		</security:authorize>
	}
	
	function loadStudentsTab(){
		$('#breadCrumMessage').text("Test Coordination : "+$('#testSessionName1').val()+" - Students");
		$('#breadCrumMessageTag').text("");
		gridsReset();
		loadAutoRegisteredTSStudents();
	}
	
	function loadScoresTab(){
		$('#breadCrumMessage').text("Test Coordination : "+$('#testSessionName1').val()+" - Scores");
		$('#breadCrumMessageTag').text("");
			gridsReset();		
		<security:authorize access="hasRole('TEST_COORD_TESTSESSION_SCORING')" >
			loadScoreTestSessionData();
			</security:authorize>
	}
	function  gridsReset(){
		$('#autoRegisteredTSStudentsTableId').jqGrid('clearGridData');
	}
	
	function backToTestCoordination(){

		window.location.href = "viewTestCoordination.htm?keepData=true";
	}
</script>
