var sensitivityTags;
var url;
var dataParams;
var itiTestSessionHistoryId;
var itiEditMode;

$("#setupITISave").click(function() {
	var gridParam = window.sessionStorage.getItem('selectedStudentITI');  
	var studentInfo = $.parseJSON(gridParam);
	var sensitivityTags =[];
	if ($('#sensitivityTags').select2("val") != null) {
		sensitivityTags =$('#sensitivityTags').select2( "val")
				.map(function(value) {
					return value;
				});
	}
	$.ajax({
		url: 'assignDLMStudentsToTest.htm',					
		dataType: 'json',
		data:{
			testCollectionId : testcollectionId,
			testCollectionName : testcollectionName,
			studentId : studentInfo.studentId,
			stateStudentIdentifier : studentInfo.stateStudentIdentifier,
			studentEnrlRosterId : studentInfo.studentEnrlRosterId,
			rosterId:studentInfo.rosterId,
			linkageLevel:linkageLevel,
			eElement:eElement,
			levelDesc:levelShortDesc, 
			source:"iti",
			action:"save",
			sensitivityTags:sensitivityTags,
			essentialelementid:linkageLevelid,
			conceptualArea:conceptualArea,
			claim:claim
		},
		type: "POST",
		beforeSend: function() {
			$("#iticonfirmloadingmessage").show();
			$("#confirmationActions").hide();
		},
		complete: function() {
			$("#iticonfirmloadingmessage").hide();
			$("#confirmationActions").show();
		},
		success: function(response) {               
			$('#instructionalPlanConfirmError').text("");
			if(response != null){
				if(response.valid){
					var dialog = $('#saveOrDoneITIAssignmentPopUp').dialog({
						resizable: false,
						height: 250,
						width: 350,
						modal: true,
						autoOpen: true,
						title: 'Plan Saved.',
						create: function(event, ui){
							var widget = $(this).dialog("widget");
						},
						buttons: {
							NO: function(){
								$("#assignmentActions").hide();
								$(this).dialog("close");
								window.location.href = 'viewinstructionalSupport.htm';
							},
							
							YES: function() {
								$('#setupInstructionalToolsSupportTabs li:eq(1) a').tab('show');
								$("#EEDropdown").prop('selectedIndex',0);
								loadStudentInfoMessage();
								$(this).dialog("close");
							}								
						},
						open: function(){
							$("#setupITISave").attr('disabled', 'disabled');
							$("#setupITIConfirm").attr('disabled', 'disabled');
							$("#setupITICancel").attr('disabled', 'disabled');
							$(".setupITIBackButton").attr('disabled', 'disabled');
						},
						close: function(){
							$("#setupITISave").removeAttr('disabled');
							$("#setupITIConfirm").removeAttr('disabled');
							$("#setupITICancel").removeAttr('disabled', 'disabled');
							$(".setupITIBackButton").removeAttr('disabled', 'disabled');
						}
					});
					dialog.dialog("open");
				}else{
					if(response.duplicateKey){
						$('#instructionalPlanConfirmError').text("A test session already exists for this selected plan that the student has not  yet completed. "+
														 "The test must be completed, before a new session can be created.");
					}
				}
			}
		}
	});		
});


$('#confirmITIPlan').dialog({
	resizable: false,
	height: 250,
	width: 500,
	modal: true,
	autoOpen:false,			
	title: "Confirm",
	buttons: {
	    No: function() {
	    	 $(this).dialog("close");
	    },
	    Yes: function() {
    		 $(this).dialog("close");
			 $('#instructionalPlanConfirmError').text("");
    		setupITINextButtonConfirmation();
	    }
	}
	
});

$('#cancelITIPlan').dialog({
	resizable: false,
	height: 250,
	width: 500,
	modal: true,
	autoOpen:false,			
	title: "Cancel",
	buttons: {
	    No: function() {
	    	 $(this).dialog("close");
	    },
	    Yes: function() {
	    	var newItiEditModeVal = window.sessionStorage.getItem('itiEditMode');
	    	if(newItiEditModeVal == 'false'){
	    		$.ajax({
	    			url: 'cancelItiInstructionPlan.htm',					
	    			dataType: 'json',
	    			data : {
	    				itiTestSessionHistoryId:itiTestSessionHistoryId
	    			},
	    			type: "POST",
	    			success: function(response) {
	    				$('#cancelITIPlan').dialog("close");
	    	  			window.location.href = 'viewinstructionalSupport.htm';
	    			}
	    		});		
	    	}else{
	    		$(this).dialog("close");
	  			window.location.href = 'viewinstructionalSupport.htm';
	    	}
	    	
	    }
	}
	
});
function setupITINextButtonConfirmation(){
	var gridParam = window.sessionStorage.getItem('selectedStudentITI');  
	var studentInfo = $.parseJSON(gridParam);
	if ($('#sensitivityTags').select2("val") != null) {
		sensitivityTags = $('#sensitivityTags').select2("val")
				.map(function(value) {
					return value;
				});
	}
	var newItiEditModeVal = window.sessionStorage.getItem('itiEditMode');
	if(newItiEditModeVal == 'true'){
		url = 'assignDLMStudentsToTest.htm';
		dataParams = {
				testCollectionId : testcollectionId,
				testCollectionName : testcollectionName,
				studentId : studentInfo.studentId,
				stateStudentIdentifier : studentInfo.stateStudentIdentifier,
				studentEnrlRosterId : studentInfo.studentEnrlRosterId,
				source:"iti",
				rosterId:studentInfo.rosterId,
				linkageLevel:linkageLevel,
				eElement:eElement,
				levelDesc:levelShortDesc,
				action:"confirm",
				sensitivityTags:sensitivityTags,
				essentialelementid:linkageLevelid,
				conceptualArea:conceptualArea,
				claim:claim
			};
	}else{
		url = 'confirmDLMStudentsToTest.htm';
		dataParams = {
				itiTestSessionHistoryId:itiTestSessionHistoryId
			};
	}
	$.ajax({
		url: url,					
		dataType: 'json',
		data:dataParams,
		type: "POST",
		beforeSend: function() {
			$("#confirmationActions").hide();
		},
		complete: function() {
			$("#confirmationActions").show();
		},
		success: function(response) {
			$('#instructionalPlanConfirmError').text("");
			if(response != null){
				if(response.valid){
					testcollectionId = response.id;
					var htmlstring = response.name;
					$(".instructionalPlan").text(htmlstring);
					$("#setupITIDone").show();
					$("#setupITIConfirm").hide();
					$("#setupITICancel").hide();
					$("#setupITISave").hide();
					$("#confirmationActions .setupITIBackButton").hide();
					enableConfirmationTab();
					if(response.resource.length > 0){
						$("#resourcepdf").prop("href", response.resource);						
						$("#instrcutionalPlanResourceDiv").show();
					}
		 			$('#breadCrumMessageITI').text("Add New Instructional Plan: Assignment Confirmed");
					$('#breadCrumMessageTagITI').text("Download or print the testlet information page; you may access the TIP later through the View Instructional Plan History interface.");
				}else{
					if(response.duplicateKey){
						$('#instructionalPlanConfirmError').text("A test session already exists for this selected plan that the student has not  yet completed. "+
														 "The test must be completed, before a new session can be created.");
					}
					if(response.nocontent){
						$('#instructionalPlanConfirmError').text("Assessment content is not available at this time based on the selected Themes for this Essential Element and Level. "+ 
								" Please either check back in the future or select a different level for this Essential Element.");
					}
				}
			}
		}
	});		
}

$("#setupITICancel").click(function() {
	$('#cancelITIPlan').html("Are you sure you want to cancel this Instructional Plan?");
	$('#cancelITIPlan').dialog("open");
});

$("#setupITIConfirm").click(function() {
	$('#confirmITIPlan').html("Are you sure you want to confirm this Instructional Plan? This action cannot be undone. <br/> When you choose Yes, a test will be assigned.");
	$('#confirmITIPlan').dialog("open");
});

$("#setupITIDone").click(function() {
	var dialog = $('#saveOrDoneITIAssignmentPopUp').dialog({
		resizable: false,
		height: 250,
		width: 350,
		modal: true,
		autoOpen: true,
		title: 'Assignment confirmed.',
		create: function(event, ui){
			var widget = $(this).dialog("widget");
		},
		buttons: {
			NO: function(){
				$("#assignmentActions").hide();
				$(this).dialog("close");
				window.location.href = 'viewinstructionalSupport.htm';
			},
			
			YES: function() {
				enableAllTabsExceptFirstTab();
				$('#setupInstructionalToolsSupportTabs li:eq(1) a').tab('show');
				$("#confirmationActions .setupITIBackButton").show();
				$("#EEDropdown").prop('selectedIndex',0);
				if(itiEditMode == 'false') {
					disableStudentRosterTab();
					window.sessionStorage.setItem('sameStudentProcess', true);
				} else {
					enableStudentRosterTab();
				}        		
        		window.sessionStorage.setItem('itiEditMode', true);
				loadStudentInfoMessage();
				$(this).dialog("close");
			}								
		},
		open: function(){
			$("#setupITISave").attr('disabled', 'disabled');
			$("#setupITIConfirm").attr('disabled', 'disabled');
			$("#setupITICancel").attr('disabled', 'disabled');
			$(".setupITIBackButton").attr('disabled', 'disabled');
		},
		close: function(){
			$("#setupITISave").removeAttr('disabled');
			$("#setupITIConfirm").removeAttr('disabled');
			$("#setupITICancel").removeAttr('disabled', 'disabled');
			$(".setupITIBackButton").removeAttr('disabled', 'disabled');
		}
	});
	dialog.dialog("open");	
});

$(function() {
	$("#setupITIDone").hide();
	$("#setupITIConfirm").show();
	$("#setupITICancel").show();
	$("#instrcutionalPlanResourceDiv").hide();
	itiEditMode = window.sessionStorage.getItem('itiEditMode');
	testcollectionName = window.sessionStorage.getItem('testCollectionName');
	itiTestSessionHistoryId = window.sessionStorage.getItem('itiTestSessionHistoryId');
	$(".instructionalPlan").html(testcollectionName);
	$('#breadCrumMessageITI').text("Add New Instructional Plan: Confirm Assignment");
	$('#breadCrumMessageTagITI').text("Choose Cancel plan to cancel this plan. Choose Confirm Assignment to assign a test to the student.");
	if(itiEditMode == 'false'){
		$("#setupITISave").hide();
		$(".setupITIBackButton").hide();
		enableConfirmationTab();
	}
});