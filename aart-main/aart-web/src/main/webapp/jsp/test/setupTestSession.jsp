<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/jsp/include.jsp" %>

<style>

li.nav-item.ui-tabs-tab {
     background:#ffffff;
}
.disabled {
  color: currentColor;
  cursor: not-allowed;
  opacity: 0.5;
  text-decoration: none;
  pointer-events: none;
  display: inline-block;
}
</style>
 
<div>
		<div>
			<a href="#" onClick="editSession()"> &lsaquo;back </a>
		</div>
	<div class="breadcrumb">
		<div>
			<h1><label id ="breadCrumMessage"><fmt:message key="label.add.new.testsession" /></label></h1>
			<h2><label id="breadCrumMessageTag"> <fmt:message key="label.select.test" /></label></h2>
		</div>		
	</div>
</div>
		
<div  id="setupTestSessionTabs" style="padding-bottom: 15px; visibility: hidden;">
	<ul id="addTestSessionNav" class="nav nav-tabs sub-nav">
		<li class="nav-item get-val" id="assessmentDetailsID" >
			<a class="nav-link" href="#tabs_assessmentDetails" data-toggle="tab" role="tab"><fmt:message key="label.testsession.assessmentdetails" /></a>
		</li>
		<li class="nav-item " id="studentsID">
			<a class="nav-link get-val" href="#tabs_students" data-toggle="tab" role="tab"><fmt:message key="label.testsession.students" /></a>
		</li>
		<li class="nav-item" id="sessionInformationID">
			<a class="nav-link get-val" href="#tabs_sessionInformation" data-toggle="tab" role="tab"><fmt:message key="label.testsession.sessioninformation" /></a>
		</li>
		<li class="nav-item" id="monitorID">
			<a class="nav-link get-val" href="#tabs_Monitor" data-toggle="tab" role="tab"><fmt:message key="label.testsession.monitor" /></a>
		</li>
		<c:if test="${rubricFlag}">
			<li class="nav-item" id="scoresID">
				<a class="nav-link get-val" href="#tabs_Scores" data-toggle="tab" role="tab"><fmt:message key="label.testsession.scores" /></a>
			</li>
		</c:if>
	</ul>

	<div id="content" class="tab-content">
		<div id="tabs_assessmentDetails" class="tab-pane hidden" role="tabpanel">
			<jsp:include page="setupTestSessionAssessment.jsp"/>
		</div>
		<div id="tabs_students" class="tab-pane hidden" role="tabpanel">
			<jsp:include page="setupTestSessionStudentDetails.jsp"/>
		</div>
		<div id="tabs_sessionInformation" class="tab-pane hidden" role="tabpanel">
			<jsp:include page="setupTestSessionSessionInformation.jsp"/>
		</div>
		<div id="tabs_Monitor" class="tab-pane hidden" role="tabpanel">
			<jsp:include page="viewTestSessionMonitor.jsp"/>
		</div>
		<c:if test="${rubricFlag}">
			<div id="tabs_Scores" class="tab-pane hidden" role="tabpanel">
				<jsp:include page="setupTestSessionScoresDetails.jsp"/>
			</div>
		</c:if>
	</div>
</div>

<script>
$(function(){
	//navigate to the first available tab
	$('#setupTestSessionTabs li.nav-item:first a').tab('show');
});
</script>	
	
	
<input id="gridInfo" value="${gridInfo}" type="text" class="hidden" />
<input id="testSessionName12" value ="${testSessionName}" type="text" class="hidden" />	

<script>
var isHaveCreateQcTestPermisson = false;

<c:if test="${hasQCPermission == true}">
	isHaveCreateQcTestPermisson=true;
</c:if>

var setupTestSessionMessages = {
	'label.testsession.created': "<fmt:message key='label.testsession.created'/>",
	'error.testsession.duplicatename': "<fmt:message key='error.testsession.duplicatename'/>",
	'label.testsession.request.status': "<fmt:message key='label.testsession.request.status'/>"
};

 var isTeacher = false;
 var byPassCheck =false;
 <c:if test="${user.teacher}">
	isTeacher = true;
 </c:if>
  /**
  * Biyatpragyan Mohanty (bmohanty_sta@ku.edu) : DE4134: Next buttons are disabled if a test is previewed
  * This is a peculiar error, when next button is clicked after overlay opened and closed, the same does not work
  * Listened the next button click, invoked the document.getready manually, then it started going to next page
  * But started giving trouble selecting the active tab. Checked for threee scrnarios
  * - Click Add new test and browse through tabs back and forth
  * - Select radio buttons and click on next to go through other pages
  * - Select radio buttons, go through all the tabs, click on any tab back and forth
  * - Select radio buttons, open the preview Nodes and, then go through next and do the same steps as above.
  */
  	//Setting up the tabs.
	$(function() {

		$('a[data-toggle="tab"]').on('shown.bs.tab', function(event) {
			var isValid=true;
			
			var lastTab =$(event.relatedTarget).text();
			var curTab =$(event.target).text();
			if(byPassCheck==false){
				isValid=validateTabNavigation(curTab);
			}else{
				byPassCheck=false;
			}
			if(isValid){
				updatePageContent(curTab);
			}  else {
				 event.preventDefault();
				 event.stopImmediatePropagation();
				 switch(lastTab)
			 		{
			 			case 'Assessment Details':
			 				$('.nav-tabs a[href="#tabs_assessmentDetails"]').tab('show');
				 		  	break;	
			 			case 'Students':
			 				$('.nav-tabs a[href="#tabs_students"]').tab('show');
			 				break;
			 			default:	
			 		}
				}	
			return isValid;
		});
		
		$('.nextButton').on('click',function() {
			var curTabTest = $('.nav-tabs .active').text();
			var isValid=true;
			switch(curTabTest)
	 		{
	 			case 'Assessment Details':
	 				if(validateTabNavigation('Students')){
	 					$('.nav-tabs a[href="#tabs_students"]').tab('show');
	 				}
		 		  	break;	
	 			case 'Students':
	 				if(validateTabNavigation('Session Information')){
	 					$('.nav-tabs a[href="#tabs_sessionInformation"]').tab('show');
	 				}
	 				break;
	 			default:	
	 		}
	    });
		
		var defaultSelected = $('.nav-tabs .active').text();
		if(defaultSelected == 'Students' && $('#breadCrumMessage').text() != 'Add new test session: Register Students'){
			populateStudentsSearchFiltersData();
  			loadStudentsData();
  			byPassCheck=true;
  			$('.nav-tabs a[href="#tabs_students"]').tab('show');
		}else if(defaultSelected == 'Assessment Details' && $('#breadCrumMessage').text() == 'Add new test session: Select Assessment'){
			clearSearchFilterValuesFromSession();
			populateSearchFiltersData();
			loadTestAndTestCollectionsData();
			selectionLoader();
		}else
			selectionLoader();
		
		$('#setupTestSessionTabs').css('visibility','visible');
		//$('#setupTestSessionTabs li').removeClass('ui-tabs-tab');
		
	}); // end of document ready
	
	
	//Deb -> Verifies the Tab text and loads the tab accordingly
	function updatePageContent(pageIndexText){
		switch(pageIndexText)
 		{
 			case 'Assessment Details':
 				$('#breadCrumMessage').text("Add new test session: Select Assessment");
				$('#breadCrumMessageTag').text("Select Test and then hit next to register students to the test");
				/**
				* Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15083 : Test Management Add Test Session page - misc UI changes
				* Added below codoe to load filters and empty grid when user clicks on the add new test session : select assessment tab.
				*/
				populateSearchFiltersData();
				loadTestAndTestCollectionsData();
	 		  	break;	
 			case 'Students':
 				$('#breadCrumMessage').text("Add new test session: Register Students");
 				$('#breadCrumMessageTag').text("Choose the students to register to the selected test below. Click NEXT to continue");
 	  			//Load students recordbrowser data only on Students tab selection.
 	  			//This method definition would be in setupTestSessionStudentDetails.jsp.
 	  			//populateStudentsSearchFiltersData();
 	  			loadStudentsData();
	 		  	break;
 			case 'Session Information':
 				$('#breadCrumMessage').text("Add new test session: Enter Name");
 				$('#breadCrumMessageTag').text("");
 				if(selectedTestId!=null && selectedTestId.length>0 && selectedTestId.length==1) 
 					$(".multipleTestText").hide();
  				else if(isHaveCreateQcTestPermisson && selectedTestId.length>1) 
  					$(".multipleTestText").show();
	 		  	break;
	 		case 'Scores':
	 			$('#breadCrumMessage').text("Scoring: "+$("#testSessionName12").val());
    			$('#breadCrumMessageTag').text("");
    			loadScoreTestSessionData();
	 		  	break;
	 		case 'Monitor':
	 			$('#breadCrumMessage').text("Monitor Test Session: "+$("#testSessionName12").val());
    			$('#breadCrumMessageTag').text("");
    			loadMonitorTestSessionData();
				break;
	 		default:
 		}
	}
	
	function validateTabNavigation(pageIndexText){
		var isValid=true;
		if(byPassCheck==false){
			switch(pageIndexText)
	 		{
	 			case 'Assessment Details':
		 		  	break;	
	 			case 'Students':
	 				if(!skipValidation &&((!isHaveCreateQcTestPermisson && !selectedTestId.length > 0 && (selectedRadio == null || selectedRadio == undefined)) ||  
		    				  (isHaveCreateQcTestPermisson && (selectedCheckbox == null || selectedCheckbox == undefined || selectedCheckbox == '')))) {
		    				isValid = false;
		    				$('body, html').animate({scrollTop:0}, 'slow');
		    				$(".selectAssessmentsError").show();
		    				setTimeout("aart.clearMessages()", 3000);		  	    				
		    			}
		 		  	break;
	 			case 'Session Information':
	 				if(!skipValidation && !selectedTestId.length > 0 && ((!isHaveCreateQcTestPermisson && (selectedRadio == null || selectedRadio == undefined)) 
	    					|| (isHaveCreateQcTestPermisson && (selectedCheckbox == null || selectedCheckbox == undefined || selectedCheckbox == '')))) {
	    				isValid = false;
	    				$('body, html').animate({scrollTop:0}, 'slow');
	    				$(".selectAssessmentsError").show();
	    				setTimeout("aart.clearMessages()", 3000);		  	    				
	    			}else if(!skipValidation && selectedStudents.length <= 0) {
	    				isValid = false;
	    				$('body, html').animate({scrollTop:0}, 'slow');
	    				$(".selectStudentsError").show();
	    				setTimeout("aart.clearMessages()", 3000);
	    			}
		 		  	break;
		 		case 'Scores':
		 		  	break;
		 		case 'Monitor':
					break;
		 		default:
	 		}
		}else{
			byPassCheck=false;
		}		
		return isValid;
	}
	
	function disableTab(tabID){
		$(tabID).addClass('disabled');
		$(tabID).find('a').removeAttr('data-toggle');
	}	
	function enableTab(tabID){
		$(tabID).removeClass('disabled');
		$(tabID).find('a').attr("data-toggle", "tab");
	}
	function selectionLoader(){
		var testSessionId = $("#testSessionId").val();
		
		//Enable Assessment Details tab only when setting-up the test session for first time
		//When editing the existing test session, disable all other tabs except students tab.
		if(testSessionId == null || testSessionId == "") {
			if($('#breadCrumMessage').text() == 'Add new test session: Select Assessment') {
				skipValidation = false;
				$('#setupTestSessionTabs li a').eq(0).addClass('active');
				var tabIdStr = "#tabs_Monitor";
				$(tabIdStr).remove();
				//$('#setupTestSessionTabs').tabs('refresh');
				var hrefStr = "a[href='" + tabIdStr + "']";
				$( hrefStr ).closest("li").remove();
				//$('#setupTestSessionTabs').tabs('remove', 3);
			}			
		} else {
			skipValidation = true;
			var str = window.location.search;
			//Changed the contains function to indexof to fix defect DE5389: Error found in TC100550: US13076_009_TestSession_ReturnPreviousScreen_CrossBrowser
			if((str.indexOf("enableRubric=true") >= 0)) {
				//If returning from rubric overlay from scoring tab
				//$('#setupTestSessionTabs').tabs('option', 'active', 4).tabs('option', 'enable', 4);
				enableTab("#monitorID");
				byPassCheck=true;
				$('.nav-tabs a[href="#tabs_Monitor"]').tab('show');
			} else {
				//$('#setupTestSessionTabs').tabs('option', 'active', 1).tabs('option', 'enable', 1);
				enableTab("#studentsID");
				byPassCheck=true;
				$('.nav-tabs a[href="#tabs_students"]').tab('show');
			}
			//$('#setupTestSessionTabs').tabs('option', 'disabled', 0);
			//$('#setupTestSessionTabs').tabs('option', 'disabled', 2);
			disableTab("#assessmentDetailsID");
			disableTab("#sessionInformationID");
			<security:authorize access="!hasRole('PERM_TESTSESSION_MONITOR')">
			var tabIdStr = "#tabs_Monitor";
			$(tabIdStr).remove();
			//$('#setupTestSessionTabs').tabs('refresh');
			var hrefStr = "a[href='" + tabIdStr + "']";
			$( hrefStr ).closest("li").remove();
			//$('#setupTestSessionTabs').tabs('remove', 3);
			</security:authorize>
		}
	}
			
	function clearSearchFilterValuesFromSession() {
		
		removeInSessionStorage("stsaAssessmentProgramId");
		removeInSessionStorage("stsaTestingProgramId");
	}
	
	function setSearchFilterValuesToSession() {
		
		setInSessionStorage("stsaAssessmentProgramId", $('#stsaAssessmentPrograms').val());
		setInSessionStorage("stsaTestingProgramId", $('#stsaTestingPrograms').val());
	}		
	
	function setInSessionStorage(storageItemName, storageItemValue) {
		window.sessionStorage.setItem(storageItemName, storageItemValue);			
	}

	function removeInSessionStorage(storageItemName) {
	    window.sessionStorage.removeItem(storageItemName);
	}
	
	function getFromSessionStorage(storageItemName) {
		var itemValue = window.sessionStorage.getItem(storageItemName);
		if(typeof itemValue != 'undefined' && itemValue != null) {
			return itemValue;
		}
		
		return null;
	}

</script>
