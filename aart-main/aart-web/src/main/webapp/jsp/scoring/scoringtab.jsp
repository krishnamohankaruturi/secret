<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">
<script type="text/javascript"
	src="<c:url value='/js/scoring/assignScorers.js'/>"> </script>
</security:authorize>	
<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">	
<script type="text/javascript"
	src="<c:url value='/js/scoring/scorersTest.js'/>"> </script>
</security:authorize>
<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">	
<script type="text/javascript"
	src="<c:url value='/js/scoring/monitorScores.js'/>"> </script>
</security:authorize>
<security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')"> 
<script type="text/javascript"
	src="<c:url value='/js/scoring/monitorHarmToSelf.js'/>"> </script>
</security:authorize> 
<security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">	
<script type="text/javascript"
	src="<c:url value='/js/scoring/uploadScores.js'/>"> </script>	
</security:authorize>	
<style>
	.hideScoringTabs {
		display:none
	}
</style>

<div id="scoringTabs" class="panel_full">
	<aside id="scoring_sidebar">
	<div id="scoring_menu">
	   <ul style="text-align: center;" >
	     <li class="active"><a href="#" class="manageScoringMenu"><fmt:message key="label.scoring.mainmenu.managescoring"/></a>
  			<ul>
  			<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">		  
				<li class="assignScorersSubmenu"><a href="#tabs_assignscorers"><fmt:message key="label.scoring.submenu.assignscorers"/></a> </li>			
			</security:authorize>
			<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
				<li class="monitorscorersSubmenu"><a href="#tabs_monitorccqscores"><fmt:message key="label.scoring.submenu.monitorscores"/></a> </li>
			</security:authorize>
			 <security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')">
				<li class="monitorHarmToSelfSubmenu"><a href="#tabs_monitorharmtoself"><fmt:message key="label.scoring.submenu.monitorharmtoself"/></a> </li>			
			</security:authorize>
			</ul>
	    </li>
	    <li class="active" ><a href="#" class="ccqScoringMenu"><fmt:message key="label.scoring.mainmenu.ccqscoring"/></a>
		  <ul>	<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">		
				<li class="scoretestSubmenu"><a href="#tabs_scorerstest"><fmt:message key="label.scoring.submenu.scoretests"/></a> </li>
				</security:authorize>
				<security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
				<li class="uploadScoresSubmenu"><a href="#tabs_uploadscores"><fmt:message key="label.scoring.submenu.uploadscorers"/></a> </li>			
				<%-- <li><a href="#"><fmt:message key="label.scoring.submenu.viewprintscores"/></a> </li> --%>
				</security:authorize>
		  </ul>
	    </li>
      </ul>
	</div>
	
	<!-- Tiny Text Added for Scoring tab -->
	<div class="tiny-text-side-bar">
		<div id="lblTinyTextAssmentPrg" class="tinyTextDiv" ></div>
		<div id="lblTinyTextDistrict" class="tinyTextDiv" ></div>
		<div id="lblTinyTextSchool" class="tinyTextDiv" ></div>
		<div id="lblTinyTextSubject" class="tinyTextDiv clearTinyText" ></div>
		<div id="lblTinyTextGrade" class="tinyTextDiv clearTinyText" ></div>
		<div id="lblTinyTextStage" class="tinyTextDiv clearTinyText" ></div>
		<div id="lblTinyTextTestName" class="tinyTextDiv clearTinyText" ></div>
		<div id="lblTinyTextStudentName" class="tinyTextDiv clearTinyText" ></div>
		<div id="lblTinyTextStateStudentId" class="tinyTextDiv clearTinyText" ></div>
		<div id="lblTinyTextItemQuestion" class="tinyTextDiv clearTinyText" ></div>
	</div>
	
	</aside>
    <div class="with-sidebar-content" style="height:525px;" >
    	<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">
			<div id="tabs_assignscorers" class="hideScoringTabs" >
				<jsp:include page="assignScorers.jsp" />
			</div>
		</security:authorize>	
		<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">
		<div id="tabs_scorerstest" class="hideScoringTabs" >
			<jsp:include page="scorersTest.jsp" />
		</div>
		</security:authorize>
		<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
		<div id="tabs_monitorccqscores" class="hideScoringTabs" >
		
			<jsp:include page="monitorScores.jsp" />
			
		</div>
		</security:authorize>
		
		 <security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')">
		<div id="tabs_monitorharmtoself" class="hideScoringTabs" >
			<jsp:include page="monitorHarmToSelf.jsp" />
		</div>
		 </security:authorize> 
		<!-- Upload Scores  -->
		<security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
		<div id="tabs_uploadscores" class="hideScoringTabs">
			<jsp:include page="uploadScores.jsp" />
		</div>
		</security:authorize>
    </div>
</div>	
<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">
	<input type="hidden" id="hdnScoringEnableAssignScorer" value="Y"/>
</security:authorize>
<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">
	<input type="hidden" id="hdnScoringEnableScorerTest" value="Y"/>
</security:authorize>
<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
	<input type="hidden" id="hdnScoringEnableMonitorScores" value="Y"/>
</security:authorize>
 <security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')"> 
	<input type="hidden" id="hdnScoringEnableMonitorSelfToHarm" value="Y"/>
</security:authorize>
<security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
	<input type="hidden" id="hdnScoringEnableUploadScores" value="Y"/>
</security:authorize>
	<!-- <div id="viewAssignScorerTestSession" class="kite-table">
	 		<table class="responsive" id="viewAssignScorerTestSessionTableId"></table>
			<div id="viewAssignScorerTestSessionPager" style="width: auto;"></div>
	 </div> -->
		
<script type="text/javascript">
//JavaScript Document
function scoringMenu() {
	"use strict";
    var winWidth = $(window).width();
    var left = "0";
    var padLeft = "185px";
    var className = "menuHide";
	
    if (winWidth < 801) {
        left = "-185px";
        padLeft = "0";
        className = "menuShow";
    }
	
    $("#sidebar").stop().animate({"left": left}, 300);
    $("#container").stop().animate({"padding-left": padLeft}, 300);
  // $("#menuToggle").removeClass().addClass(className);
   
	scoringScoreTestDisableOtherTab();
	
}

function scoringScoreTestDisableOtherTab(){
	$('.ccqScoringMenu').hide();
	$('.manageScoringMenu').hide();
	
	if($('#hdnScoringEnableAssignScorer').length ==1 || $('#hdnScoringEnableMonitorScores').length==1 ||  $('#hdnScoringEnableMonitorSelfToHarm').length==1){
		$('.manageScoringMenu').show();
	}
	if($('#hdnScoringEnableScorerTest').length ==1 || $('#hdnScoringEnableUploadScores').length==1){
		$('.ccqScoringMenu').show();
	}
		
	$('#scoring_menu li ul li a').filter(function(indx){
	}).hover(function(){
	    $(this).css("background","#e8e8e8");
	  },function(){
	    $(this).css("background","#e8e8e8");
	  }).css("background","#e8e8e8").removeAttr("href");
}


$(function() {
	"use strict";
	var assignScorerTab = false;
	var ccqTestTab = false;
	var monitorScoresTab = false;
	
	$('#scoring_menu li').find('>ul').stop(true, true).addClass("show");
	$('#scoring_menu ul ul li').on("click",function() {
		$('#scoring_menu ul ul li').removeClass("current");
		$(this).addClass("current");
		$(".clearTinyText").text('');
	});
	
	$(document).on( "click","#menuToggle", function(e) {
		e.preventDefault();
		var left="-185px";
		var padLeft="0";
		
		if($(this).hasClass("menuShow")) {
			 left="0px";
		     padLeft="185px";
		}
		
		$("#sidebar").stop().animate({"left":left}, 300);
		$("#container").stop().animate({"padding-left": padLeft }, 300,
			function(){
				 $("#menuToggle").toggleClass("menuShow menuHide");
		    });
	});	
	/* $('#scoring_menu li').click(function(e) {
		 var exist = $(this).children("a").attr('href');
		if( exist == undefined)
			return;
		 if(!$(this).hasClass("current")){
		   	$(this).toggleClass("current").find('>ul').stop(true, true).toggleClass("show")
		    	.end().siblings().find('ul').removeClass("show").closest('li'); 
		    $(this).siblings("li").removeClass("current");
		    $(this).find("li").removeClass("current"); 
		 }
		  e.stopPropagation();
		  e.preventDefault();
	}); */

	$('#scoring_menu li ul li a').on("click",function(e) {
		var hrf = $(this).attr('href');
		if( hrf == undefined){
			return;
		}
		$('#scoring_menu li ul li a').each(function(){
			var id = $(this).attr('href');
			if( id != undefined && id != "#")
				$(id).hide();
		});
		var cId = $(this).attr('href');
		if( cId != "#"){
			if( cId == "#tabs_assignscorers" )
				{
				<security:authorize access="hasRole('PERM_SCORING_ASSIGNSCORER')">
				   	scoringAssignScorersInit();
					$(cId).show();
				</security:authorize>	
				}
			else if(cId == "#tabs_scorerstest" )
			    {
				<security:authorize access="hasAnyRole('PERM_SCORE_CCQ_TESTS','PERM_SCORE_ALL_TEST')">
					scoringScorersTestInit();
					$(cId).show();
				</security:authorize>	
				}
			else if(cId == "#tabs_monitorccqscores" )
		    {
			<security:authorize access="hasRole('PERM_SCORING_MONITORSCORES')">
				scoringMonitorCcqScoresInit();
				$(cId).show();
			</security:authorize>	
			}
			else if(cId == "#tabs_monitorharmtoself" )
		    {
				<security:authorize access="hasRole('PERM_MONITOR_HARM_TO_SELF')">
				scoringMonitorHarmToSelfInit();
				$(cId).show();
				</security:authorize>
			} 
			else if(cId == "#tabs_uploadscores")
			 {
				<security:authorize access="hasRole('PERM_SCORE_UPLOADSCORER') and hasRole('PERM_SCORE_ALL_TEST') and hasRole('PERM_EDIT_SCORES')">
				scoringUploadScoresInit();
				$(cId).show();
				</security:authorize>	
			 }
			
		}	
		e.preventDefault();
		
	});
     $('#scoring_menu li').each(function(i, val) {
			var children = $(val).find('ul li');
			
			if(children.size() > 0) {
				$(val).prepend('<span></span>');  
			}
     }); 
		
    scoringMenu();
	//containerHeight();
	
	$(window).resize(function() {
		scoringMenu();
		//containerHeight();
	});	
	
});


//Created function for tiny text changes

function districtEventTinyTextChanges(selectedDistrict){	
	if(selectedDistrict!='' && selectedDistrict!='Select' && selectedDistrict!=null && selectedDistrict!='undefined'){
		$("#lblTinyTextDistrict").show().text(selectedDistrict);		
	}else{
		$("#lblTinyTextDistrict").hide().text('');	
	}
	$("#lblTinyTextSchool").hide().text('');
	$("#lblTinyTextSubject").hide().text('');
	$("#lblTinyTextGrade").hide().text('');
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function schoolEventTinyTextChanges(selectedSchool){	
	if(selectedSchool!='' && selectedSchool!='Select' && selectedSchool!=null && selectedSchool!='undefined'){
		$("#lblTinyTextSchool").show().text(selectedSchool);
	}	
	else{
		$("#lblTinyTextSchool").hide().text('');	
	}

	$("#lblTinyTextSubject").hide().text('');
	$("#lblTinyTextGrade").hide().text('');
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');	
}

function subjectEventTinyTextChanges(selectedSubject){	
	if(selectedSubject!='' && selectedSubject!='Select' && selectedSubject!=null && selectedSubject!='undefined'){
		$("#lblTinyTextSubject").show().text(selectedSubject);		
	}else{
		$("#lblTinyTextSubject").hide().text('');
	}
	$("#lblTinyTextGrade").hide().text('');
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function gradeEventTinyTextChanges(selectedGrade){	
	if(selectedGrade!='' && selectedGrade!='Select' && selectedGrade!=null && selectedGrade!='undefined'){
		$("#lblTinyTextGrade").show().text(selectedGrade);
	}else{
		$("#lblTinyTextGrade").hide().text('');
	}
	$("#lblTinyTextStage").hide().text('');
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function stageEventTinyTextChanges(selectedStage){	
	if(selectedStage!='' && selectedStage!='Select' && selectedStage!=null && selectedStage!='undefined'){
		$("#lblTinyTextStage").show().text(selectedStage);
	}else{
		$("#lblTinyTextStage").hide().text('');
	}
	$("#lblTinyTextTestName").hide().text('');
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function testEventTinyTextChanges(selectedTestName){	
	if(selectedTestName!='' && selectedTestName!='Select' && selectedTestName!=null && selectedTestName!='undefined'){
		$("#lblTinyTextTestName").show().text(selectedTestName);
	}else{
		$("#lblTinyTextTestName").hide().text('');
	}
	$("#lblTinyTextStudentName").hide().text('');
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function studentNameEventTinyTextChanges(selectedStudentName){	
	if(selectedStudentName!='' && selectedStudentName!='Select' && selectedStudentName!=null && selectedStudentName!='undefined'){
		$("#lblTinyTextStudentName").show().text(selectedStudentName);
	}else{
		$("#lblTinyTextStudentName").hide().text('');
	}
	$("#lblTinyTextStateStudentId").hide().text('');
	$("#lblTinyTextItemQuestion").hide().text('');
}

function stateStudentIdEventTinyTextChanges(selectedStateStudentId){	
	if(selectedStateStudentId!='' && selectedStateStudentId!='Select' && selectedStateStudentId!=null && selectedStateStudentId!='undefined'){
		$("#lblTinyTextStateStudentId").show().text("State Student ID: "+selectedStateStudentId);
	}else{
		$("#lblTinyTextStateStudentId").hide().text('');
	}
	$("#lblTinyTextItemQuestion").hide().text('');
}

function itemQuestionEventTinyTextChanges(itemQuestion){	
	if(itemQuestion!='' && itemQuestion!='Select' && itemQuestion!=null && itemQuestion!='undefined'){
		$("#lblTinyTextItemQuestion").show().text("Item: Question "+itemQuestion);
	}else{
		$("#lblTinyTextItemQuestion").hide().text('');
	}
}

function initTinytextChanges(userAccessLevel){
	$('#lblTinyTextAssmentPrg').show().text($('select#userDefaultAssessmentProgram option:selected').html());
	if(userAccessLevel==50) {
		districtEventTinyTextChanges($('select#userDefaultOrganization option:selected').html()); 
	}
	else if(userAccessLevel==70) {
		districtEventTinyTextChanges(''); 
		schoolEventTinyTextChanges($('select#userDefaultOrganization option:selected').html());
	}
	else{
		$('#lblTinyTextDistrict').addClass('clearTinyText');
		$('#lblTinyTextSchool').addClass('clearTinyText');
		$(".clearTinyText").text('');
	}
}

</script>		