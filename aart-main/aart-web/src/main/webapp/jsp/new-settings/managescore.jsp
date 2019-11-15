<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<script type="text/javascript"	src="<c:url value='/js/scoring/scoringCommon.js'/>"> </script>
	
	
<div id="managescoringTabs" class="with-sidebar-content" style="height:590px; width:auto;">	
	<ul id="scoring_manage" class="nav nav-tabs sub-nav">
	<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">	
			<li class="nav-item manage-score">
				<a class="nav-link" href="#tabs_assignscorers" data-toggle="tab" role="tab"><fmt:message key="label.scoring.submenu.assignscorers"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
			<li class="nav-item manage-score">
				<a class="nav-link" href="#tabs_monitorccqscores" data-toggle="tab" role="tab"><fmt:message key="label.scoring.submenu.monitorscores"/></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')">
			<li class="nav-item manage-score">
				<a class="nav-link" href="#tabs_monitorharmtoself" data-toggle="tab" role="tab"><fmt:message key="label.scoring.submenu.monitorharmtoself"/></a>
			</li>
		</security:authorize>
	</ul>
	
    <div id="content" class="tab-content" >
    	<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">
			<div id="tabs_assignscorers" class="hideScoringTabs" role="tabpanel">
				<jsp:include page="../scoring/assignScorers.jsp" />
				<script type="text/javascript" src="<c:url value='/js/scoring/assignScorers.js'/>"> </script>
			</div>
		</security:authorize>		
		<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
		<div id="tabs_monitorccqscores" class="hideScoringTabs" role="tabpanel">		
			<jsp:include page="../scoring/monitorScores.jsp" />	
			<script type="text/javascript" src="<c:url value='/js/scoring/monitorScores.js'/>"> </script>		
		</div>
		</security:authorize>		
		  <security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')">
		<div id="tabs_monitorharmtoself" class="hideScoringTabs" role="tabpanel">
			<jsp:include page="../scoring/monitorHarmToSelf.jsp" />
		<script type="text/javascript"
	src="<c:url value='/js/scoring/monitorHarmToSelf.js'/>"> </script>
		</div>
		 </security:authorize> 
    </div>
</div>	

<!-- Tiny Text Added for Scoring tab -->
	<div class="tiny-text-side-bar">
		<span id="lblTinyTextAssmentPrg" class="tinyTextDiv" ></span>
		<span id="lblTinyTextDistrict" class="tinyTextDiv" ></span>
		<span id="lblTinyTextSchool" class="tinyTextDiv" ></span>
		<span id="lblTinyTextSubject" class="tinyTextDiv clearTinyText" ></span>
		<span id="lblTinyTextGrade" class="tinyTextDiv clearTinyText" ></span>
		<span id="lblTinyTextStage" class="tinyTextDiv clearTinyText" ></span>
		<span id="lblTinyTextTestName" class="tinyTextDiv clearTinyText" ></span>
		<span id="lblTinyTextStudentName" class="tinyTextDiv clearTinyText" ></span>
		<span id="lblTinyTextStateStudentId" class="tinyTextDiv clearTinyText" ></span>
		<span id="lblTinyTextItemQuestion" class="tinyTextDiv clearTinyText" ></span>
	</div>


<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">
	<input type="hidden" id="hdnScoringEnableAssignScorer" value="Y"/>
</security:authorize>

<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
	<input type="hidden" id="hdnScoringEnableMonitorScores" value="Y"/>
</security:authorize>
 <security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')"> 
	<input type="hidden" id="hdnScoringEnableMonitorSelfToHarm" value="Y"/>
</security:authorize>

		
<script type="text/javascript">

$('#scoring_manage li.nav-item:first a').tab('show');
$('li.manage-score').on('click',function(e){
	var clickedURL = $(this).find("a").attr('href');     
	manageScoring(clickedURL.substring(1, clickedURL.length));
    e.preventDefault(); // same to return false; 
});  

var studentsItemMenu = $('#scoring_manage li.nav-item:first a');
if(studentsItemMenu.length>0){
	var clickedURL = studentsItemMenu.attr('href');     
	manageScoring(clickedURL.substring(1, clickedURL.length));
}

function manageScoring(cId){	
	
	if( cId == "tabs_assignscorers" )
		{
		<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">
		   	scoringAssignScorersInit();
			$("#"+cId).show();
			$('#tabs_monitorccqscores').hide();
			$('#tabs_monitorharmtoself').hide();
			$(".tiny-text-side-bar").show();
		</security:authorize>	
		}
	
	else if(cId == "tabs_monitorccqscores" )
    {
	<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
		scoringMonitorCcqScoresInit();
		$("#"+cId).show();
		$('#tabs_assignscorers').hide();
		$('#tabs_monitorharmtoself').hide();
		$(".tiny-text-side-bar").show();
		
	</security:authorize>	
	}
	else if(cId == "tabs_monitorharmtoself" )
    {
		<security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')">
		scoringMonitorHarmToSelfInit();
		$("#"+cId).show();
		$('#tabs_assignscorers').hide();
		$('#tabs_monitorccqscores').hide();
		$(".tiny-text-side-bar").show();
		
		</security:authorize>
	} 	
	
}	
</script>		