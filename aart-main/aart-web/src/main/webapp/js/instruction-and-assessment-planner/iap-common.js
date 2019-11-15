function accessProfileDetails(studentId, stateStudentIdentifier, assessmentProgramCode, title) {
	var assmtPrgmCode = '';
	if(assessmentProgramCode !== undefined && assessmentProgramCode !== null){
		assmtPrgmCode = assessmentProgramCode;
	}
	var viewAccessProfileUrl =
		'viewAccessProfile.htm?&studentId='+studentId+
		'&assessmentProgramCode='+encodeURIComponent(assmtPrgmCode.toString())+
		'&stateStudentIdentifier='+encodeURIComponent(stateStudentIdentifier.toString())+
		'&studentInfo=&previewAccessProfile=noPreview';
	$('#accessProfileDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1087,
		height: 700,			
		title: escapeHtml(title), // global variables...I know.
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
		},
		beforeClose: function() {
			var confirmText = 'You have not saved the PNP settings. Select Go Back to return to the PNP settings or select Exit to continue without saving.';
			var canExit = ATSUtil.dialogPromptToSave(this, confirmText);
			if (canExit){
				$(this).html('');
			}
			return canExit;
		}
	}).load(viewAccessProfileUrl, ATSUtil.dialogLockThisExitAfterChange).dialog('open');
}

function viewFirstContactDetails(studentId,title) {
	$('#firstContactViewDiv').dialog({
		autoOpen: false,
		resizable : false,
		modal: true,
		width: 1110,
		height: 700,			
		title: escapeHtml(title),
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
		},
		beforeClose: function() {
			saveFirstContactResponsesOnClose();
		    $(this).html('');
		    //removing the student information from local storage
		    window.localStorage.removeItem(studentId);	    
		    let pathArray = location.href.split('?')[0].split('/');
		    if (/^instruction\-planner\.htm/.test(pathArray[pathArray.length - 1])){
		    	setIAPSearchFilterValuesToSession();
		    	setInSessionStorage("reloadIAPHomePage", true);	
		    }
		    location.reload();
		}
	}).load('firstContactResponseView.htm?&studentId='+studentId).dialog('open');
}

function callMethodPassed(functionToCall, studentID, studentFirstName, studentMiddleName, studentLastName
		, stateID, gender , dateOfBirth, assessmentProgramCode, title){
	var studentInfo = {
			id: studentID,
			studentFirstName: studentFirstName,
			studentMiddleName: studentMiddleName,
			studentLastName: studentLastName,
			stateStudentIdentifier: stateID,
			gender: gender == '1' ? "Male" : (gender == '0' ? "Female" : "-"),
			dateOfBirth: dateOfBirth
		};
	//adding student information to local storage as it is getting used in PNP profile page
	window.localStorage.setItem(studentInfo.id, JSON.stringify(studentInfo));
	if(functionToCall == 'accessProfileDetails' ){
		accessProfileDetails(studentID, stateID , assessmentProgramCode, title);
	}else if(functionToCall== 'openViewStudentPopup'){
		openViewStudentPopup(studentID, stateID, false, title); 
	}
}

function reloadPageWithScrollingInfo(){
	window.sessionStorage.setItem('iap-scrollLeft', $(window).scrollLeft());
	window.sessionStorage.setItem('iap-scrollTop', $(window).scrollTop());
	location.reload(true);
}

function beginInstruction(eeId, linkageLevelName){
	$.ajax({
		url: 'createPlan.htm',
		method: 'POST',
		dataType: 'json',
		data: {
			studentId: $('#studentId').val(),
			enrollmentsRostersId: $('#enrollmentsRostersId').val(),
			rosterId: $('#rosterId').val(),
			otwId: $('#otwId').val(),
			contentAreaId: $('#contentAreaId').val(),
			gradeLevel: $('#gradeLevel').val(),
			essentialElementId: eeId,
			linkageLevel: linkageLevelName,
		}
	}).done(function(response){
		if (response.success == true){
			reloadPageWithScrollingInfo();
		} else {
			window.location.href = 'generalError.htm';			
		}
	}).fail(function(){
		window.location.href = 'generalError.htm';
	});
}

function finishInstruction(existingITIId, assignTestlet){
	$.ajax({
		url: 'finishPlan.htm',
		method: 'POST',
		dataType: 'json',
		data: {
			studentId: $('#studentId').val(),
			enrollmentsRostersId: $('#enrollmentsRostersId').val(),
			rosterId: $('#rosterId').val(),
			otwId: $('#otwId').val(),
			contentAreaId: $('#contentAreaId').val(),
			gradeLevel: $('#gradeLevel').val(),
			itiId: existingITIId,
			assignTestlet: assignTestlet
		}
	}).done(function(response){
		if (response.success == true){
			reloadPageWithScrollingInfo();
		} else {
			window.location.href = 'generalError.htm';			
		}
	}).fail(function(){
		window.location.href = 'generalError.htm';
	});
}

function cancelTestlet(itiId){
	$.ajax({
		url: 'cancelTestlet.htm',
		method: 'POST',
		dataType: 'json',
		data: {
			studentId: $('#studentId').val(),
			enrollmentsRostersId: $('#enrollmentsRostersId').val(),
			rosterId: $('#rosterId').val(),
			otwId: $('#otwId').val(),
			contentAreaId: $('#contentAreaId').val(),
			gradeLevel: $('#gradeLevel').val(),
			itiId: itiId
		}
	}).done(function(response){
		if (response.success == true){
			reloadPageWithScrollingInfo();
		} else {
			window.location.href = 'generalError.htm';			
		}
	}).fail(function(){
		window.location.href = 'generalError.htm';
	});
}

function applySCCode(itiId, studentsTestsId, scId){
	$.ajax({
		url: 'applySCCodeToPlan.htm',
		method: 'POST',
		dataType: 'json',
		data: {
			studentId: $('#studentId').val(),
			enrollmentsRostersId: $('#enrollmentsRostersId').val(),
			rosterId: $('#rosterId').val(),
			otwId: $('#otwId').val(),
			contentAreaId: $('#contentAreaId').val(),
			gradeLevel: $('#gradeLevel').val(),
			itiId: itiId,
			studentsTestsId: studentsTestsId,
			scId: scId
		}
	}).done(function(response){
		if (response.success == true){
			reloadPageWithScrollingInfo();
		} else {
			window.location.href = 'generalError.htm';			
		}
	}).fail(function(){
		window.location.href = 'generalError.htm';
	});
}

function approveSCCode(sscId, statusId){
	$.ajax({
		url: 'approveSCCodeForPlan.htm',
		method: 'POST',
		dataType: 'json',
		data: {
			studentId: $('#studentId').val(),
			enrollmentsRostersId: $('#enrollmentsRostersId').val(),
			rosterId: $('#rosterId').val(),
			otwId: $('#otwId').val(),
			contentAreaId: $('#contentAreaId').val(),
			gradeLevel: $('#gradeLevel').val(),
			sscId: sscId,
			statusId: statusId
		}
	}).done(function(response){
		if (response.success == true){
			//reloadPageWithScrollingInfo();
		} else {
			console.error(response.message);
			window.location.href = 'generalError.htm';
		}
	}).fail(function(){
		window.location.href = 'generalError.htm';
	});
}
var localRef_viewFirstContactDetails = viewFirstContactDetails;
var localRef_accessProfileDetails = accessProfileDetails;

function openViewStudentPopup(studentId, stateStudentIdentifier, editLink, title) {
	var dialogTitle = "View Student Record - " + title;
	var action = 'view';
	var selectedOrg = null;
	$('#viewStudentDetailsDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1087,
		height: 700,			
		title: dialogTitle,
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
		},
		beforeClose: function() {
		    $(this).html('');
		}

	}).load('viewStudentDetails.htm',{"studentId":studentId,
									  "editLink":editLink,
									  "action":action,
									  "selectedOrg[]":selectedOrg}).dialog('open');

}

$(function(){
	
});
