$('#sensitivityTags').select2({
	placeholder:'Select',
	multiple: true
});
var testcollectionId;
var testcollectionName;
var noTestPlanMsg = "Assessment content is not available at this time for this Essential Element and Level. Please either check back in the future or select a different level or another Essential Element.";
var duplicateTestPlan = "Instructional plan for selected essential element and level already exists for this student. Plan is ";
$("#setupITINextButtonSave").click(function() {
	var gridParam = window.sessionStorage.getItem('selectedStudentITI');  
	var studentInfo = $.parseJSON(gridParam);
	
	var sensitivityTags =[];
	if ($('#sensitivityTags').select2("val") != null) {
		sensitivityTags = $('#sensitivityTags').select2("val")
				.map(function(value) {
					return value;
				});
	}
	if(testcollectionId){
		$.ajax({
			url: 'assignDLMStudentsToTest.htm',					
			dataType: 'json',
			data:{
				testCollectionId : testcollectionId,
				testCollectionName : testcollectionName,
				studentId : studentInfo.studentId,
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
				$("#itiloadingmessage").show();
				$("#assignmentActions").hide();
			},
			complete: function() {
				$("#itiloadingmessage").hide();
				$("#assignmentActions").show();
			},
			success: function(response) {
				$('#instructionalPlanError').text("");
				if(response != null){
					if(response.valid){
						var dialog = $('#saveITIAssignments').dialog({
							resizable: false,
							height: 250,
							width: 350,
							modal: true,
							autoOpen: true,
							title: 'Plan Saved.',
							create: function(event, ui){
								var widget = $(this).dialog("widget");;
							},
							buttons: {
								NO: function(){
									$("#assignmentActions").hide();
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
								$("#setupITINextButtonSave").attr('disabled', 'disabled');
								$("#setupITINextButtonConfirmation").attr('disabled', 'disabled');
								$(".setupITIBackButton").attr('disabled', 'disabled');
							},
							close: function(){
								$("#setupITINextButtonSave").removeAttr('disabled');
								$("#setupITINextButtonConfirmation").removeAttr('disabled');
								$(".setupITIBackButton").removeAttr('disabled', 'disabled');
							}
						});
						//dialog.dialog("widget").find("button").css({"padding":"0 0"} );
						dialog.dialog("open");
					}else{
						if(response.duplicateKey){
							$('#instructionalPlanError').text("A test session already exists for this selected plan that the student has not  yet completed. "+
															 "The test must be completed, before a new session can be created.");
						}
					}
				}
			}
		});
	}
		
});

function getInstructionalPlan(){	
	gridParam = window.sessionStorage.getItem("selectedStudentITI");  
	studentInfo = $.parseJSON(gridParam);
	$('#instructionalPlanContentCodeName').text(eElement);
	$('#instructionalPlanLeveDesc').text(levelLongDesc);
	var eECode = $('#EEDropdown option:selected').text().substring(1);
	$.ajax({
		url: 'getRandomTestCollectionForLinkageLevel.htm',					
		dataType: 'json',
		data:{
			mappedLinkageLevel : linkageLevel,
			actualLinkageLevel : actualLinkageLevel,
			contentAreaId: studentInfo.stateSubjectAreaId,
			contentAreaAbbrName: studentInfo.subjectAreaCode,
			eElement : eECode.substring(0,eECode.indexOf('  ')),
			gradeCourseCode: studentInfo.gradeCourseCode,
			studentId: studentInfo.studentId,
			rosterId:studentInfo.rosterId,
			essentialelementid:linkageLevelid
		},
		type: "GET",
		success: function(response) {
			$("#instructionalPlanError").html("");
			$(".instructionalPlan").html("");
			if(response.testcollection != null){	
				testcollectionId = response.testcollection.id;
				testcollectionName = response.testcollection.name;
				var htmlstring = response.testcollection.name;
				$(".instructionalPlan").text(htmlstring);
				$("#insPlanLabel").show();
				$("#assignmentActions").show();
				$("#setupITIConfirm").show();
				$("#setupITICancel").show();
				if(response.sensitivitytags != null){
					$('#instrcutionalPlanSensitivity').show();
					$('#sensitivityTags').empty();
					$.each(response.sensitivitytags, function(i, sensitivitytag) {
						$('#sensitivityTags').append("<option value='" + sensitivitytag.id + "'>" + sensitivitytag.name + "</option>");
					});
					$('#sensitivityTags').trigger('change.select2');
					$('#sensitivityTags').val('').trigger("change.select2");
					
				} else {
					$('#sensitivityTags').empty();
					$('#instrcutionalPlanSensitivity').hide();
				}
				if(response.instructionsPDF != null){
					$('#instrcutionsPDF').show();
					$("#instrcutionsPDFLink").prop("href", "getItiInstructionsPdf.htm?instructionsloc=" +  response.instructionsPDF);
				}else{
					$('#instrcutionsPDF').hide();
				}
			}else{
				$("#insPlanLabel").hide();
				if(response.result == "nocollection"){	
					$("#instructionalPlanError").text(noTestPlanMsg);
				}else if(response.result == "notests"){
					$("#instructionalPlanError").text('All content available for selected essential element and level has already been assigned to this student.');
 				}
				else if(response.result == "duplicate"){
					$("#instructionalPlanError").text('Instructional plan (' + response.plan + ') for selected essential element and level already exists for this student.');
 				}
				$("#assignmentActions").hide();
				$('#instrcutionsPDF').hide();
				$('#instrcutionalPlanSensitivity').hide();
			}
		}
	});	
}
function validateInstructionalPlan(){
	if($("#instructionalPlanError").html().length != 0){
		return true;
	}
	return false;
}