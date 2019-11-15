<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<style>
	.hideScoringTabs {
		display:none
	}
	.with-sidebar-content{
border-left:0px solid #d7d7d7 !important;
} 
.select2-container{
width:250px !important;
}
</style>

<script type="text/javascript" src="<c:url value='/js/scoring/scoringCommon.js'/>"> </script>
	
<div id="myscoringTabs" >	
	<ul id="my_score" class="nav nav-tabs sub-nav">
	<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">
			<li class="nav-item get-score">
				<a class="nav-link" href="#tabs_scorerstest" data-toggle="tab" role="tab"><fmt:message key="label.scoring.submenu.scoretests"/></a>
			
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
			<li class="nav-item get-score">
				<a class="nav-link" href="#tabs_uploadscores" data-toggle="tab" role="tab"><fmt:message key="label.scoring.submenu.uploadscorers"/></a>
			
			</li>
		</security:authorize>
		
	</ul>
    <div class="with-sidebar-content" style="height:525px;width:100%;" >    	
	 <security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">
		<div id="tabs_scorerstest" class="tab-pane hideScoringTabs" role="tabpanel">
			<jsp:include page="../scoring/scorersTest.jsp" />
			<script type="text/javascript"
	src="<c:url value='/js/scoring/scorersTest.js'/>"> </script>
		</div>
		</security:authorize> 
		 <security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
		<div id="tabs_uploadscores" class="hideScoringTabs">
			<jsp:include page="../scoring/uploadScores.jsp" />
			<script type="text/javascript"
	src="<c:url value='/js/scoring/uploadScores.js'/>"> </script>
		</div>
		</security:authorize> 
    </div>
</div>	

<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">
	<input type="hidden" id="hdnScoringEnableScorerTest" value="Y"/>
</security:authorize>

 <security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
	<input type="hidden" id="hdnScoringEnableUploadScores" value="Y"/>
</security:authorize>
	
	
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
	
		
<script type="text/javascript">
//JavaScript Document

$('#my_score li.nav-item:first a').tab('show');
$('li.get-score').on('click',function(e){
	var clickedURL = $(this).find("a").attr('href');     
	myScoring(clickedURL.substring(1, clickedURL.length));
    e.preventDefault(); // same to return false; 
});  

var studentsItemMenu = $('#my_score li.nav-item:first a');
if(studentsItemMenu.length>0){
	var clickedURL = studentsItemMenu.attr('href');     
	myScoring(clickedURL.substring(1, clickedURL.length));
}


function myScoring(cId){
	
	 if(cId == "tabs_scorerstest" )
	    {
		<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">
			scoringScorersTestInit();
			$("#"+cId).show();
			$('#tabs_uploadscores').hide();
			$(".tiny-text-side-bar").show();
		</security:authorize>	
		}
	 
	else if(cId == "tabs_uploadscores")
	 {
		<security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
		scoringUploadScoresInit();
		$('#tabs_scorerstest').hide();
		$("#"+cId).show();
		$(".tiny-text-side-bar").hide();
		</security:authorize>	
	 }
}

</script>		