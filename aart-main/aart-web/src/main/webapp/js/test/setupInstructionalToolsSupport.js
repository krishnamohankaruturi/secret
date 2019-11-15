var byPassCheck =false;
function disableAllTabs(){
	$('#setupInstructionalToolsSupportTabs').find('li').addClass('disabled');
	$('#setupInstructionalToolsSupportTabs li.disabled').find('a').removeAttr('data-toggle');
}
function enableConfirmationTab(){
	$('#setupInstructionalToolsSupportTabs').find('li').addClass('disabled');
	$('#confirmationID').removeClass('disabled');
	$('#setupInstructionalToolsSupportTabs li.disabled').find('a').removeAttr('data-toggle');
	byPassCheck=true;
	$('.nav-tabs a[href="#tabs_confirmation"]').tab('show');
}
function enableAllTabsExceptFirstTab(){
	$('#setupInstructionalToolsSupportTabs').find('li').removeClass('disabled');
	$('#studentRostersID').addClass('disabled');
	$('#setupInstructionalToolsSupportTabs li').find('a').attr("data-toggle", "tab");
}
function enableStudentRosterTab(){
	$('#studentRostersID').removeClass('disabled');
	$('#studentRostersID').find('a').attr("data-toggle", "tab");
}
function disableStudentRosterTab(){
	$('#studentRostersID').addClass('disabled');
	$('#studentRostersID').find('a').find('a').removeAttr('data-toggle');
}
$(function() {
	$('#breadCrumMessageITI').text("Add New Instructional Plan: Select Student");
	$('#breadCrumMessageTagITI').text("Select a student from roster and choose Next.");
	$('.itiback').click(function() {
		if(selectedStudentRadio != null && selectedStudentRadio != undefined){
			$("#confirmDialog").html('You have unsaved changes, do you wish to continue?');
			$("#confirmDialog").dialog('open');
		}else{
			window.location.href = 'viewinstructionalSupport.htm';
		}
		
	});
	$('#confirmDialog').dialog({
		resizable: false,
		height: 200,
		width: 500,
		modal: true,
		autoOpen:false,			
		title: "Warning - Unsaved Changes",
		buttons: {
		    No: function() {
		    	 $(this).dialog("close");
		    },
		    Yes: function() {
		    	 $(this).dialog("close");
		    	 window.location.href = 'viewinstructionalSupport.htm';
		    }
		}
		
	});
	$('#contentForm')[0].reset();
	loadStudentInfoMessage();
	//Need to set this in the session so that loggedInUserId would be used as a key to store the orgs.
	window.sessionStorage.setItem("loggedInUserId", $('#loggedInUserId').val() );
	
	function updatePageContent(pageIndexText){
		switch(pageIndexText)
 		{
 			case 'Student Roster':
 				$('#breadCrumMessageITI').text("Add New Instructional Plan: Select Student");
				$('#breadCrumMessageTagITI').text("Select a student from roster and choose Next.");
	 		  	break;	
 			case 'Select Content':
	 			$('#breadCrumMessageITI').text("Add New Instructional Plan: Select Content");
				$('#breadCrumMessageTagITI').text("Select an essential element and linkage level. Then, choose Next.");
				loadStudentInfoMessage();
	 		  	break;
	 		case 'Assignment':
	 			$('#breadCrumMessageITI').text("Add New Instructional Plan: View Assignment");
				$('#breadCrumMessageTagITI').text("Review the instructional plan you have selected for your student.");
	 		  	break;
	 		case 'Confirmation':
	 			$('#breadCrumMessageITI').text("Add New Instructional Plan: Confirm Assignment");
				$('#breadCrumMessageTagITI').text("Choose Cancel plan to cancel this plan. Choose Confirm Assignment to assign a test to the student.");
				$("#setupITIDone").hide();
				$('#instructionalPlanConfirmError').text("");
				$("#instrcutionalPlanResourceDiv").hide();
				break;
	 		default:
 		}
	}
	
	function activaTab(tab){
	    $('.nav-tabs a[href="#' + tab + '"]').tab('show');
	};
	
	function validateCurrentTabContent(curentIndex){
		var isValidITI=true;
		var sameStudentProcess = window.sessionStorage.getItem('sameStudentProcess'); 		
 		switch(curentIndex)
 		{
	 		case 0:
	 			if(selectedStudentRadio == undefined){
	  				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$("#selectStudentRosterError").show();
	  				setTimeout("aart.clearMessages()", 3000);
	  			}else if (!validateFCSurveyComBand()){
					//validate if firstcontactsurvey completed and com[plexity band calculated
	 				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$("#selectStudentRosterFCSBandError").show();
	  				//setTimeout("aart.clearMessages()", 3000);
	 			}
	 		  	break;	 		
	 		case 1:
	 			if(selectedStudentRadio == undefined && sameStudentProcess == false){
	  				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$("#selectStudentRosterError").show();
	  				setTimeout("aart.clearMessages()", 3000);
	  			}else if(validateContent()){
	  				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$(".selectStudentContentError").show();
	  				setTimeout("aart.clearMessages()", 3000);
	  			}else if(validateLinkageLevel()){
	  				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$(".selectLinkageLevelError").show();
	  				setTimeout("aart.clearMessages()", 3000);
	  			} 
	 		  	break;
	 		case 2:
	 			if(selectedStudentRadio == undefined && sameStudentProcess == false){
	  				isValidITI =false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$("#selectStudentRosterError").show();
	  				setTimeout("aart.clearMessages()", 3000);
	  			}else if(validateContent()){
	  				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$(".selectStudentContentError").show();
	  				setTimeout("aart.clearMessages()", 3000);
	  			}else if(validateLinkageLevel()) {
	  				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  				$(".selectLinkageLevelError").show();
	  				setTimeout("aart.clearMessages()", 3000);
	  			}else if(validateInstructionalPlan()) {
	  				isValidITI = false;
	  				$('body, html').animate({scrollTop:0}, 'slow');
	  			}
	 		  	break;
	 		default:
 		}
		return isValidITI;
	}
	
	function getCurrentTabIndex(curentIndexText){
		var index =-1;
		switch(curentIndexText)
 		{
	 		case 'Student Roster':
	 			index= 0;
	 			break;
	 		case 'Select Content':
	 			index= 1;
	 			break;
	 		case 'Assignment':
	 			index= 2;
	 			break;
	 		case 'Confirmation':
	 			index= 3;
	 			break;
	 		default:
 		}
		return index;
	}
	
	
	$('ul.nav-list > li.disabled > a').click(function () { return false; });
	
	$('a[data-toggle="tab"]').on('shown.bs.tab', function(event) {
		var isValidITI=true;
		var lastTab =$(event.relatedTarget).text();
		var curTab =$(event.target).text();
		var curTabIndex = getCurrentTabIndex(curTab);
		var lastTabIndex = getCurrentTabIndex(lastTab);
		if(byPassCheck==false){
			if(curTabIndex>lastTabIndex){
				var i=lastTabIndex;
				for(;i<curTabIndex;i++){
					if(validateCurrentTabContent(i) == false){
						isValidITI=false;
						byPassCheck=true;
						$('#setupInstructionalToolsSupportTabs li:eq('+i+') a').tab('show');
						break;
					}
				}
			}
		}else{
			byPassCheck=false;
		}
		if(isValidITI){
			updatePageContent(curTab);
		}  else {
			 event.preventDefault();
			 event.stopImmediatePropagation();
		}
		return isValidITI;
	});
	
	//Enabling the correct tab on clicking Next button.
	$('.setupITINextButton').on('click',function() {
		var selected = $('.nav-tabs .active').text();
		if(validateCurrentTabContent(getCurrentTabIndex(selected))){
			var nextIndex = getCurrentTabIndex(selected)+1;
			byPassCheck=true;
			$('#setupInstructionalToolsSupportTabs li:eq('+nextIndex+') a').tab('show');
		}
    });
	
	//Enabling the correct tab on clicking Back button.
	$('.setupITIBackButton').on('click',function() {
		var selected = $('.nav-tabs .active').text();
		byPassCheck=true;
		var nextIndex = getCurrentTabIndex(selected)-1;
		$('#setupInstructionalToolsSupportTabs li:eq('+nextIndex+') a').tab('show');
    });
	
});



