var refreshAssignScorer = true;
var refreshAssignStudent = true;
var selectedScoresList = [];
var selectedStudentList =[];
var assignScorerSelectedTestSessionId = [];
var assignScorerDisableForm = false;
var assignScorerTestSessionScrollBarMove = false;
$(function() {
	$("#confirmDialogSelectClearAssignScorer").dialog({
	    autoOpen: false,
	    modal: true
	});
	$("#confirmDialogAssignScorerDeleteStudent").dialog({
	    autoOpen: false,
	    modal: true
	});
	$("#confirmDialogAssignScorerDeleteScorer").dialog({
	    autoOpen: false,
	    modal: true
	});
	$("#confirmDialogAssignScorerCancelScorer").dialog({
	    autoOpen: false,
	    modal: true
	});
	$("#confirmDialogAssignScorerRefreshScorer").dialog({
	    autoOpen: false,
	    modal: true
	});
	$("#confirmDialogTestSessionSelectionChange").dialog({
	    autoOpen: false,
	    modal: true
	});
	$("#warningDialogStudentDuplicateTestSession").dialog({
	    autoOpen: false,
	    modal: true
	});		
	showGridStudent();
	showAssignScorer();

});


function scoringAssignScorersInit(){	
	if( ! refreshAssignScorer ){		
		$("#confirmDialogSelectClearAssignScorer").dialog({			
		      buttons : {
		     "Yes" : function() {		    	 
		    	 refreshAssignScorer = true;
		    	 scoringAssignScorerRefresh();
		    	 $(this).dialog("close");
		     },
		     "No" : function(){
		       $(this).dialog("close");
		      }
		     }
		 });
		$("#confirmDialogSelectClearAssignScorer").dialog("open");
		$("#divAssignScorerErrMsgemptysave").html().hide();
		return ;
	} 
	else{
		refreshAssignStudent = true;		
		refreshAssignScorer = true;
		scoringAssignScorerRefresh();
		showAssignScorer();
		showGridStudent();
	}
	
	//Tiny Text changes
	initTinytextChanges($("#assignScorersUserAccesslevel").val());
	$('#assignScorerScoringAssessmentPrograms,#assignScorerScoringDistrict,#assignScorerScoringSchool,#assignScorerGrades, #assignScorerTestPrograms, #assignScorerContentAreas, #assignScorerDistrict, #assignScorerSchool,#assignScoresStage,#assignScorerTestPrograms').select2({
		placeholder:'Select',
		multiple: false,
		allowClear:true
});
}
function scoringAssignScorerRefresh(){	
	$('#searchFilterErrors').html("").hide();
	assignScorerSelectedTestSessionId = [];
	$('#assignScorerStudentGridTableId').trigger('change.select2');	 
	 assignScorerViewTestSession(false);
		if($("#viewStudentGridTableId")[0].grid && $("#viewStudentGridTableId")[0]['clearToolbar']){
		$("#viewStudentGridTableId")[0].clearToolbar();
	}
	if($("#viewScorerGridTableId")[0].grid && $("#viewScorerGridTableId")[0]['clearToolbar']){
		$("#viewScorerGridTableId")[0].clearToolbar();
	}
	assginScoringTestsessionInit();
	assignScoringScorersControlEventsInit();
	assignScoringStudentScorerInit();
	assignScoringSaveControlInit();	
	showGridStudent();
	showAssignScorer();
	filteringOrganization($('#assignScorerDistrict'));
	filteringOrganization($('#assignScorerSchool'));	
}
function assginScoringTestsessionInit(){
	assignScorerSelectedTestSessionId = [];
	// next button disabled
	assignScoringTestSessionGridAreaHide();	
	clearAssignScorerTestsessionFilters(false);
	// District Value	 	
	$('#assignScorerDistrict').on("change",function() {
		
		//Added for tiny text changes
		districtEventTinyTextChanges($(this).find('option:selected').text());	
		if( ! assignScoringValidateTestSessionChange(this) )
			return false;
		$('#searchFilterErrors').html("").hide();		
		assignScorerDistrictChangeEvent();		
		refreshSelectBtn("assignScorerSchool");
		refreshSelectBtn("assignScorerContentAreas");
		refreshSelectBtn("assignScorerGrades");
		refreshSelectBtn("assignScoresStage");
		refreshSelectBtn("assignScorerTestPrograms");	
	});
	var districtOrgSelect = $('#assignScorerDistrict');	
	$.ajax({
		url: 'getOrgsBasedOnUserContext.htm',
		dataType: 'json',
		data: {
			orgId : $("#assignScorerUserCurrentOrgId").val(),
	    	orgType:'DT',
	    	orgLevel:50
	    	},				
		type: "GET"
	}).done(function (districtOrgs) {
		districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		districtOrgSelect.trigger('change.select2');
		if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
			$.each(districtOrgs, function(i, districtOrg) {
				optionText = districtOrgs[i].organizationName;
				
				districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
			});						
		   if (districtOrgs.length == 1) {			   
				districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				assignScorerDistrictChangeEvent();
			}
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
		}
		$('#assignScorerSchool, #assignScorerDistrict').trigger('change.select2');
		assignScoringTestSessionGridAreaHide();
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown); 
	});

	
	//subject value
	$('#assignScorerSchool').on("change",function() {	
		//Added for tiny text changes
		schoolEventTinyTextChanges($(this).find('option:selected').text());	
		
		$('#assignScorerContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#assignScorerContentAreas').trigger('change.select2');	
		refreshSelectBtn("assignScorerContentAreas");
		refreshSelectBtn("assignScorerGrades");
		refreshSelectBtn("assignScoresStage");
		refreshSelectBtn("assignScorerTestPrograms");	
		var schoolAreaId = $('#assignScorerSchool').val();
		$('#searchFilterErrors').html("").hide();
		if (schoolAreaId != 0) {
			var subjectSelect = $('#assignScorerContentAreas');	
			$.ajax({
				url: 'getContentAreasByScorerAssessmentProgram.htm',
				dataType: 'json',
				data: {
					assessmentProgramId : $("#assignScorerUserCurrentassessmentprgId").val(),
					schoolAreaId :schoolAreaId
			    	},				
				type: "POST"
			}).done(function (subjectvalue) {
				subjectSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				subjectSelect.trigger('change.select2');
				if (subjectvalue !== undefined && subjectvalue !== null && subjectvalue.length > 0) {				
					$.each(subjectvalue, function(i, subjectdata) {
						optionText = subjectvalue[i].name;
						subjectSelect.append($('<option></option>').val(subjectdata.id).html(optionText));
					});					
				   if (subjectvalue.length == 1) {			 
					   subjectSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					   $("#assignScorerContentAreas").trigger('change');
					} 
				} else {
					$('body, html').animate({scrollTop:0}, 'slow');
					$('#searchFilterErrors').html("No subject Found for the current user").show();
				}
				$('#assignScorerContentAreas').trigger('change.select2');
			}).fail(function (jqXHR, textStatus, errorThrown) { 
				console.log(errorThrown); 
			});
		}
		assignScoringTestSessionGridAreaHide();
	});
	
	$('#assignScorerContentAreas').on("change",function() {
		
		//Added for tiny text changes
	     subjectEventTinyTextChanges($(this).find('option:selected').text());	
		
		$('#assignScorerGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#assignScorerGrades').trigger('change.select2');
		
		refreshSelectBtn("assignScorerGrades");
		refreshSelectBtn("assignScoresStage");
		refreshSelectBtn("assignScorerTestPrograms");
		var contentAreaId = $('#assignScorerContentAreas').val();	
		$('#searchFilterErrors').html("").hide();
		if (contentAreaId != 0) {
			var gradeSelect = $('#assignScorerGrades');
			var schoolAreaId = $('#assignScorerSchool').val();
			$.ajax({
				url: 'getGradeCourseByContentAreaIdForAssignScorers.htm',
		        data: {
		        	assessmentProgramId : $("#assignScorerUserCurrentassessmentprgId").val(),
		        	schoolAreaId :schoolAreaId,
		        	contentAreaId: contentAreaId
		        	
		        	},
		        dataType: 'json',
		        type: "GET"
			}).done(function (grades) { 
				gradeSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	        	gradeSelect.trigger('change.select2');
				if (grades !== undefined && grades !== null && grades.length > 0) {				
					$.each(grades, function(i, grade) {							
						optionText = grades[i].name;							
						gradeSelect.append($('<option></option>').val(grade.id).html(optionText));						
					});					
				   if (grades.length == 1) {			 
					   gradeSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					   $("#assignScorerGrades").trigger('change');
					} 
				} else {
					$('body, html').animate({scrollTop:0}, 'slow');
					$('#searchFilterErrors').html("No Grade Found for the current user").show();
				}
				$('#assignScorerGrades,#assignScorerContentAreas').trigger('change.select2');
			}).fail(function (jqXHR, textStatus, errorThrown) { 
				console.log(errorThrown); 
			});
		}
		assignScoringTestSessionGridAreaHide();
	});	
		$('#assignScorerGrades').on("change",function() {	
			assignscoreTestchangeEvent();	
			//Added for tiny text changes
			gradeEventTinyTextChanges($(this).find('option:selected').text());				
					$('#assignScoresStage').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					//$('#assignScoresStage').trigger('change.select2');
					$('#assignScoresStage').val('').trigger('change.select2');
					refreshSelectBtn("assignScoresStage");
					refreshSelectBtn("assignScorerTestPrograms");			
					var schoolId = $('#assignScorerSchool').val();
					var  gradeId= $('#assignScorerGrades').val();
				    var contentAreaId = $('#assignScorerContentAreas').val();				
				    $('#searchFilterErrors').html("").hide();
				    if (gradeId != 0) {					
			    	$.ajax({
			    		url: 'getStageByGradeIdForAssignScorers.htm',
				        data: {
				            assessmentProgramId : $("#assignScorerUserCurrentassessmentprgId").val(),
				        	schoolId: schoolId,
				        	contentAreaId: contentAreaId,
					    	gradeId: gradeId
				        	},
				        dataType: 'json',
				        type: "GET"
			    	}).done(function (stages) { 
			    		$('#assignScoresStage').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			        	$('#assignScoresStage').trigger('change.select2');	     	
						$.each(stages, function(i, stage) {
							$('#assignScoresStage').append($('<option></option>').attr("value", stage.id).text(stage.name));
						});												
						if (stages.length == 1) {
							$("#assignScoresStage option").removeAttr('selected').next('option').attr('selected', 'selected');
							$("#assignScoresStage").trigger('change');
						}
						$('#assignScoresStage').trigger('change.select2');
			    	}).fail(function (jqXHR, textStatus, errorThrown) { 
			    		console.log(errorThrown); 
			    	});
				}
				
			});
		
			
			$('#assignScoresStage').on("change",function() {	
				//Added for tiny text changes
				stageEventTinyTextChanges($(this).find('option:selected').text());	
				
			var schoolId = $('#assignScorerSchool').val();
			var  gradeId= $('#assignScorerGrades').val();
		    var subjectId = $('#assignScorerContentAreas').val();				
		    var stageId= $('#assignScoresStage').val();
		    $('#searchFilterErrors').html("").hide(); 	    	    	
		    $.ajax({
		    	url: 'getAssignScorerTest.htm',
		        data: {		    	
			    	assessmentProgramId : $("#assignScorerUserCurrentassessmentprgId").val(),
		        	schoolId: schoolId,
			    	subjectId: subjectId,
			    	gradeId: gradeId,
			    	stageId:stageId
		        	},
		        dataType: 'json',
		        type: "POST"
		    }).done(function (tests) { 
		    	$('#assignScorerTestPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	    		$('#assignScorerTestPrograms').trigger('change.select2');
	    		$('#assignScorerTestPrograms').val('');     	
				$.each(tests, function(i, test) {
					if($("#assignScorerTestPrograms option[value="+test.id+"]").text() == undefined
							|| $("#assignScorerTestPrograms option[value="+test.id+"]").text() == '')
					$('#assignScorerTestPrograms').append($('<option></option>').attr("value", test.id).text(test.name));
				});									
				if (tests.length == 1) {
					$("#assignScorerTestPrograms option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#assignScorerTestPrograms").trigger('change');
				}				
				$('#assignScorerTestPrograms').trigger('change.select2');
		    }).fail(function (jqXHR, textStatus, errorThrown) { 
		    	console.log(errorThrown); 
		    });
		    assignScoringTestSessionGridAreaHide();
		});
		
	$("#assignScorerTestPrograms").on("change",function(){
		testEventTinyTextChanges($(this).find('option:selected').text());	
		if( ! assignScoringValidateTestSessionChange(this) )
			return false;			
		if( $("#assignScorerTestPrograms").val() != '' ){			
			$("#assignScorerSearchScorersBtn").prop("disabled",false);
			$('#assignScorerSearchScorersBtn').removeClass('ui-state-disabled');
		}
		else{
			assignScoringTestSessionGridAreaHide();
		}
	});	
	$('#viewTestSessionNextBtn').off("click").on("click",function(event) {		
		if(  assignScorerScoingTabShown() ){
			$("#confirmDialogTestSessionSelectionChange").dialog({
  		      buttons : {
  		    	  "Yes" : function() {
  		    		  assignScoringScorersControlEventsInit();
  		    		  assignScorerViewAddScorerSection();
  		    		  assignScorerMoveScrollBar("tabs_assignscorers",0);
  		    		  $(this).dialog("close");
  		    		  
  		    	   },
  		    	   "No": function(){
  		    		   $(this).dialog("close");  
  		    	   }
  		     }
  	  		});
			$("#confirmDialogTestSessionSelectionChange").dialog("open");
	    	return false;
		}		
		assignScorerViewAddScorerSection();
		assignScorerMoveScrollBar("assignScorerScores_gridContainter",0);
		assignScorerMoveScrollBar("assignStudentScores_gridContainter",0);				
	});
}

function assignscoreTestchangeEvent(){
		var assessmentProgramId = $("#assignScorerUserCurrentassessmentprgId").val();
		var schoolId = $('#assignScorerSchool').val();
		var  gradeId= $('#assignScorerGrades').val();
	    var subjectId = $('#assignScorerContentAreas').val();				
	    
	    if (gradeId != 0) {					
    	$.ajax({
    		url: 'getAssignScorerTest.htm',
 	        data: {	        	
 	            assessmentProgramId :assessmentProgramId,
 	        	schoolId: schoolId,
 		    	subjectId: subjectId,
 		    	gradeId: gradeId,
 	        	},
 	        dataType: 'json',
 	        type: "POST"
    	}).done(function (tests) { 
    		$('#assignScorerTestPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
    		$('#assignScorerTestPrograms').trigger('change.select2');
    		$('#assignScorerTestPrograms').val('');
			$.each(tests, function(i, test) {
			if($("#assignScorerTestPrograms option[value="+test.id+"]").text() == undefined
					|| $("#assignScorerTestPrograms option[value="+test.id+"]").text() == '')	
				$('#assignScorerTestPrograms').append($('<option></option>').attr("value", test.id).text(test.name));
			});	
			if (tests.length == 1) {
				$("#assignScorerTestPrograms option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#assignScorerTestPrograms").trigger('change');
			}	 else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('#searchFilterErrors').html("No TestSession Found for the current user").show();
			}			
			$('#assignScorerTestPrograms').trigger('change.select2');
    	}).fail(function (jqXHR, textStatus, errorThrown) { 
    		console.log(errorThrown); 
    	});
	}
	assignScoringTestSessionGridAreaHide();
}
function assignScorerDistrictChangeEvent(){
	$('#assignScorerSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assignScorerSchool').trigger('change.select2');
	var districtOrgId = $('#assignScorerDistrict').val();
	
	if (districtOrgId != 0) {
		$.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
	        data: {
	        	orgId : districtOrgId,
	        	orgType:'SCH',
	        	orgLevel:70
	        	},
	        dataType: 'json',
	        type: "GET"
		}).done(function (schoolOrgs) { 
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#assignScorerSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});		
			if (schoolOrgs.length == 1) {
				$("#assignScorerSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#assignScorerSchool").trigger('change');
			}
			$('#assignScorerSchool').trigger('change.select2');
			assignScoringTestSessionGridAreaHide();
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
	}
}
function assignScorerViewAddScorerSection(){
	assignScoringScorersAreaHide();
	$('#assignScorerScores_gridContainter').show();
	$('#assignStudentScores_gridContainter').show();
	var  testsessionName = assignScorerGetTestSessionName(); 
	$("#testSessionName").empty();
	$("#testSessionName").append(testsessionName);
	
}
function assignScoringTestSessionGridAreaHide(){
	if($("#assignScorerContentAreas").val() != null  &&   $("#assignScoresStage").val() != null &&   $("#assignScorerTestPrograms").val() != null && $("#assignScorerTestPrograms").val() != ""){
	$("#assignScorerSearchScorersBtn").prop("disabled",false);
		$('#assignScorerSearchScorersBtn').removeClass('ui-state-disabled');
	}
	else{
		$("#assignScorerSearchScorersBtn").prop("disabled",true);
		$('#assignScorerSearchScorersBtn').addClass('ui-state-disabled');
	}
	$('#view_scorer_test_grid').hide();
	assignScorerSelectedTestSessionId = [];
}
function assignScoringScorersAreaHide(){
	selectedScoresList = [];
	$('#assignScorerScores_gridContainter').hide();
	$('#assignStudentScores_gridContainter').hide();
	$('#assignStudentScores_gridContainter').hide();
	$('#assignScorerScores_gridContainter').hide();
	enableOrDisableScorerAddNextBtn();
	enableOrDisableScorerAddBtn();
	enableOrDisableStudentAddBtn();
	assignScoringStudentScoresAreaHide();
}
function assignScoringStudentScoresAreaHide(){
	$('#scorer_student_grid').hide();
	assignScoringSaveAreaHide();
}

function assignScoringValidateTestSessionChange(combo){
	if(assignScorerScoingTabShown()){
		$("#confirmDialogTestSessionSelectionChange").dialog({
		      buttons : {
		    	  "Yes" : function() {
		    		  var selId = $(combo).attr('selectedval');
		    		  $(combo).val(selId).prop('selected', true);
		    		  $(combo).trigger('change.select2');
		    		  assignScoringTestSessionGridAreaHide();
		    		  assignScoringScorersAreaHide();
		    		  $(combo).trigger("change");
		    		  $(this).dialog("close");
		    	   },
		    	   "No": function(){
		    		   $(this).dialog("close");  
		    	   }
		     }
	  	});
		$("#confirmDialogTestSessionSelectionChange").dialog("open");
		$(combo).attr('selectedval',$(combo).val() );
		return false;
		
	}
	else{
		$(combo).attr('current',$(combo).val() );
		assignScoringTestSessionGridAreaHide();
		return true;
	}
}

function assignScoringScorersControlEventsInit(){
	assignScoringScorersAreaHide();
	$('#assignScoresStage').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assignScoresStage').trigger('change.select2');
			
	$('#assignScorerScoringDistrict').on("change",function() {
		if( assignscoringCheckSelectedScorersAdded() )
		{ 	$(this).attr('current',$(this).val() );			
			$("#assignScorerSchool").trigger('change');
			
		}else{
			var selId = $(this).attr('current');
			$("#assignScorerScoringDistrict").val(selId).prop('selected', true);
			$('#assignScorerScoringDistrict').trigger('change.select2');
			return false;
			
		}	
	});	
	$('#assignScorerScoringSchool').on("change",function(e) {
		if( assignscoringCheckSelectedScorersAdded() )
		{ 
			$(this).attr('current',$(this).val() );
		}
		else{
			var selId = $(this).attr('current');
			$("#assignScorerScoringSchool").val(selId).prop('selected', true);
			$('#assignScorerScoringSchool').trigger('change.select2');
			return false;
		}
	});
	
	$('#assignScorerSearchScorersBtn').off("click").on("click",function(event) {		
		$('#searchFilterErrors').html("").hide();
		$('#assignStudentScores_gridContainter').show();				
			enableOrDisableScorerAddBtn();
			enableOrDisableStudentAddBtn();		
			var testSessionId=$('#assignScorerTestPrograms').val();
			var subjectId = $('#assignScorerContentAreas').val();
			var gradeId = $('#assignScorerGrades').val();
				 $.ajax({
				    datatype:"json", 
					url : 'getAssignScorerTestsessionStudents.htm?q=1', 
					search: false,
					type: "POST",
					data: {	   "filters":"",
							   "testSessionId":testSessionId,
							   "subjectId":subjectId,
							   "gradeId":gradeId
							   },
				   success :function(response){
					   $("#viewStudentGridTableId").jqGrid("GridUnload");
					   viewStudentGrid(response.rows);
				   }
			});
			 
			assignScorerMoveScrollBar("assignStudentScores_gridContainter",0);
			
			//for scoring grid   
			$('#assignScorerScores_gridContainter').show();					
			enableOrDisableScorerAddBtn();
			    var stateId =$("#assignScorerUserCurrentOrgId").val();
			    var assessmentPrgId =  $("#assignScorerUserCurrentassessmentprgId").val();
		        var districtId =  $("#assignScorerDistrict").val();		
				var schoolId =  $("#assignScorerSchool").val();			
				
				 $.ajax({
					    datatype:"json", 
						url : 'getAssignScorerScorersView.htm?q=1', 
						search: false,
						type: "POST",
						data: {	   "filters":"",
								   stateId :stateId,
								   assessmentPrgId: assessmentPrgId,							   
								   districtId :districtId,
								   schoolId :schoolId									  
								   },
					   success :function(response){
						   $("#viewScorerGridTableId").jqGrid("GridUnload");
						   viewAssignScorerGrid(response.rows);
					   }
				});				
				$('#scorer_student_grid').show();
				$("#assignScorerStudentGridTableId").jqGrid('clearGridData');
				$("#selectScorerGridTableId").jqGrid('clearGridData');
				$(".info-msg-content").show();				
				$('#assignScorerStudentGridTableId').trigger('change.select2');				
				$("#divAssignScorerErrMsg").html("").hide();
				$("#txtAssignScorerCcqTestName").val("");
				$("#divAssignScorerErrMsgemptysave").html("").hide();
				selectedStudentList =[];
				selectedScoresList =[];
				$("#info-msg-nextbtn").prop("disabled",true);
				$('#info-msg-nextbtn').addClass('ui-state-disabled');
				$("#info-msg-cancel").prop("disabled",true);
				$('#info-msg-cancel').addClass('ui-state-disabled');
	
	});		
	//Selected check box added in anothe Grid
$('#assignStudentAddScorersBtn').off("click").on("click",function(event) {	
		var gridAuto = $("#viewStudentGridTableId");		
		var belowStudentList = $("#assignScorerStudentGridTableId").jqGrid("getDataIDs");
		for(var i=0;i<selectedStudentList.length;i++ ){
			if(jQuery.inArray(selectedStudentList[i].studentTestId.toString(), belowStudentList) == -1){
				$("#assignScorerStudentGridTableId").addRowData( selectedStudentList[i].studentTestId,selectedStudentList[i], 'last');
			}		
		}
		gridAuto.jqGrid('resetSelection'); 
		 selectedStudentList =[];
		 enableOrDisableStudentAddBtn();
});
//Remove Selected Check box
	$('#assignStudentRemoveScorersBtn').off("click").on("click",function(event) {
		var gridAuto = $("#viewStudentGridTableId");
		var belowStudentList = $("#assignScorerStudentGridTableId").jqGrid("getDataIDs");
		for (var i = belowStudentList.length -1; i >= 0; i--){			
			var result = $.grep(selectedStudentList,function(studentO){				
				return studentO.studentTestId.toString() == belowStudentList[i].toString();
				});			
			if(result.length){
				$('#assignScorerStudentGridTableId').jqGrid('delRowData',belowStudentList[i]);
				}		}	
		 	gridAuto.jqGrid('resetSelection');
		 	selectedStudentList =[];
		 	enableOrDisableStudentAddBtn();
	});	
$('#assignScorerRemoveScorersBtn').off("click").on("click",function(event) {
	var gridAuto = $("#viewScorerGridTableId");		
	var belowScorerList = $("#selectScorerGridTableId").jqGrid("getDataIDs");
	for (var i =belowScorerList.length -1;i>= 0;i--){		
		var result = $.grep(selectedScoresList,function(scorerO){
			return scorerO.userId.toString() == belowScorerList[i].toString(); });				
				if(result.length){
					$('#selectScorerGridTableId').jqGrid('delRowData',belowScorerList[i]);
				}		
		}		
		gridAuto.jqGrid('resetSelection');
		selectedScoresList = [];	
		enableOrDisableScorerAddBtn();
});
$('#assignScorerAddScorersBtn').off("click").on("click",function(event) {
	var belowScorerList = $("#selectScorerGridTableId").jqGrid("getDataIDs");	
	for(var i=0;i<selectedScoresList.length;i++ ){			
		if(jQuery.inArray(selectedScoresList[i].userId.toString(), belowScorerList) == -1){				
			$("#selectScorerGridTableId").addRowData(selectedScoresList[i].userId,selectedScoresList[i], 'last');	
			
		}	
	}
	$("#viewScorerGridTableId").jqGrid('resetSelection');
    selectedScoresList = [];
    enableOrDisableScorerAddBtn();
    $('#selectScorerGridTableId').jqGrid('setGridParam', {sortname: 'lastFirstName', sortorder: 'asc'}).trigger('reloadGrid');
	$('#selectScorerGridTableId').jqGrid('setGridHeight', '218' ); 
    
});
//	testScorerAssessmentProgram("assignScorerScoringAssessmentPrograms"); 
	showAssignScorerDistrict();
}
function assignScorerGetSelectedTestSessionId(){
	var testSessionId = null;
	if(assignScorerSelectedTestSessionId != null && assignScorerSelectedTestSessionId.length == 1 && assignScorerSelectedTestSessionId[0] != null){
		testSessionId = assignScorerSelectedTestSessionId[0];
	}
	return testSessionId;
}
function assignScoringStudentScorerInit(){
	assignScoringStudentScoresAreaHide();
	$('#scorerStudentNextBtn').off("click").on("click", function(event) {
		$("#divAssignScorerErrMsg").html("").hide();		
		$("#txtAssignScorerCcqTestName").val("");
		$(".info-msg-content").show();
		assignScorerMoveScrollBar("info-msg-content",0);		
	});
}
function assignScoringSaveAreaHide(){	
	$('.info-msg-save').hide();
	$('.info-msg-content').hide();
}
function assignScoringSaveControlInit(){
	assignScoringSaveAreaHide();
	$("#divAssignScorerErrMsg").hide();
	$('#txtAssignScorerCcqTestName').val("");
	$("#info-msg-cancel").prop("disabled",true);
	$('#info-msg-cancel').addClass('ui-state-disabled');
	$("#info-msg-nextbtn").prop("disabled",true);
	$('#info-msg-nextbtn').addClass('ui-state-disabled');
	$('#txtAssignScorerCcqTestName').off('keyup').on('keyup', function(event){
		var testName =  $(this).val();
		if(testName.trim().length > 0 ){
			$("#info-msg-nextbtn").prop("disabled",false);
			$('#info-msg-nextbtn').removeClass('ui-state-disabled');
			$("#info-msg-cancel").prop("disabled",false);
			$('#info-msg-cancel').removeClass('ui-state-disabled');
		}
		else{
			$("#info-msg-nextbtn").prop("disabled",true);
			$('#info-msg-nextbtn').addClass('ui-state-disabled');
			$("#info-msg-cancel").prop("disabled",true);
			$('#info-msg-cancel').addClass('ui-state-disabled');
		}
		$("#divAssignScorerErrMsg").hide();
	});
	
	$('#info-msg-cancel').on("click",function(){		
		scoringAssignScorersInit();			
	});
	
	$('#info-msg-nextbtn').off("click").on("click",function(event) {			
		saveScoringAssignments();	
	});
	
	$('#info-msg-cancelbtn').off("click").on("click",function(event){
		$("#confirmDialogAssignScorerCancelScorer").dialog({
		      buttons : {
		     "Yes" : function() {
		    	 scoringAssignScorerRefresh();
		    	 $(this).dialog("close");
		     },
		     "No" : function(){
		       $(this).dialog("close");
		      }
		     }
		 });
		$("#confirmDialogAssignScorerCancelScorer").dialog("open");		
	});
	
	
	$('#assignScorerDone').off("click").on("click",function(event){		
		location.reload();
	});
	
	$('#assignScorerContinue').off("click").on("click",function(event){
		scoringAssignScorersInit();
	});
	
}
function saveScoringAssignments(){
	$("#info-msg-nextbtn").prop("disabled",true);
	$("#divAssignScorerErrMsg").hide();
	var testSessionId = $('#assignScorerTestPrograms').val();
	var studentTestIds = $("#assignScorerStudentGridTableId").jqGrid ('getDataIDs');	
	var scorersIds = $("#selectScorerGridTableId").jqGrid('getDataIDs');
	var testName = $('#txtAssignScorerCcqTestName').val();
	
	testName = testName.trim();	
	if( studentTestIds.length == 0  ){	
		$("#divAssignScorerErrMsgemptysave").html("Warning! please select student grid value").show();
		$("#divAssignScorerErrMsgemptysave").delay(5000).fadeOut();
		$("#info-msg-nextbtn").prop("disabled",false);
		return false;
	}
	if( scorersIds.length == 0){		
		$("#divAssignScorerErrMsgemptysave").html("Warning! please select scorer grid value").show();
		$("#divAssignScorerErrMsgemptysave").delay(5000).fadeOut();
		$("#info-msg-nextbtn").prop("disabled",false);
		return false;
	}
	if( testName.length == 0 ){
		$("#divAssignScorerErrMsgemptysave").html("Warning! please enter testname").show();
		$("#divAssignScorerErrMsgemptysave").delay(5000).fadeOut();
		$("#info-msg-nextbtn").prop("disabled",false);
		return false;
	}	
	var studentGrid = $('#assignScorerStudentGridTableId');
	var studentIdTestIds = [];	
	for(var i=0; i < studentTestIds.length; i++ ){
		var enrollementId=studentGrid.jqGrid ('getCell', studentTestIds[i], 'enrollmentId');
		var studentId = studentGrid.jqGrid ('getCell', studentTestIds[i], 'studentId');
		var testId =studentGrid.jqGrid ('getCell', studentTestIds[i], 'testId');
		studentIdTestIds[i] = studentId + "#" + enrollementId+ "#" + testId+ "#" +studentTestIds[i];		
	}
	$.ajax({
		url: 'createScoringAssignments.htm',
        data: {
        	testSessionId: testSessionId,
        	studentIdTestIds: studentIdTestIds,
        	scorerIds:scorersIds,
        	testName:testName
        },
        dataType: 'json',
        type: "POST"
	}).done(function (response) { 
		$("#divAssignScorerErrMsg").hide();
    	if( response.result == "success"){
    		assignScorerViewTestSession(true);
    		$("#info-msg-save").show();	
			$("#assignScorerMsgSaveTestName").empty().text(testName);
			var d = new Date();
			var strDate =  (d.getMonth()+1) + "/" + d.getDate() + "/" + d.getFullYear() ;
			$("#msgSaveCurrentDate").empty().append(strDate);  
			$(".info-msg-content").hide();
			refreshAssignScorer = true;
			refreshAssignStudent = true;
    	}else if( response.result == "Duplicate"){
    		$("#divAssignScorerErrMsg").html("Warning! Test name already exists. Please use a different name.").show();
    		$("#info-msg-nextbtn").prop("disabled",true);
    		$('#info-msg-nextbtn').addClass('ui-state-disabled');
    		$("#info-msg-cancel").prop("disabled",true);
    		$('#info-msg-cancel').addClass('ui-state-disabled');
    	}else if(response.result.startsWith("This scorer has already been")){
    		var newstr = response.result.replace(/\n/g, "<br/>");
    		$("#divAssignScorerErrMsg").html("Warning! :"+newstr+"<br/> Please select different scorer.").show();
    		$("#info-msg-nextbtn").prop("disabled",true);
    		$('#info-msg-nextbtn').addClass('ui-state-disabled');
    		$("#info-msg-cancel").prop("disabled",true);
    		$('#info-msg-cancel').addClass('ui-state-disabled');
    	}else{
    		$("#divAssignScorerErrMsg").text(response.result).show();
    	}
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}
function assignScoringScoresAssmentPrgChangeEvnt(){
	$('#assignScorerScoringTestingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assignScorerScoringTestingPrograms').trigger('change.select2');
	var assessmentProgramId = $('#assignScorerScoringAssessmentPrograms').val();
	if (assessmentProgramId != 0) {
		$.ajax({
			url: 'getTestingPrograms.htm',
	        data: {
	        	assessmentProgramId: assessmentProgramId
	        	},
	        dataType: 'json',
	        type: "POST"
		}).done(function (testingPrograms) { 
			$.each(testingPrograms, function(i, testingProgram) {
				$('#assignScorerScoringTestingPrograms').append($('<option></option>').attr("value", testingProgram.id).text(testingProgram.programName));
			});						
			if (testingPrograms.length == 1) {
				$("#assignScorerScoringTestingPrograms option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#assignScorerScoringTestingPrograms").trigger('change');
			} 
			$('#assignScorerScoringTestingPrograms').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
	}

	$('#assignStudentScores_gridContainter').hide();
	$('#assignScorerScores_gridContainter').hide();
		
}
function enableOrDisableScorerAddNextBtn(){
	if( selectedScoresList != null && selectedScoresList.length > 0 && selectedScoresList[0] != null){
		$('#btnAssignScorerScoresNext').prop("disabled",false);
		$('#btnAssignScorerScoresNext').removeClass('ui-state-disabled');
		$('#btnAssignScorerScoresNext1').prop("disabled",false);
		$('#btnAssignScorerScoresNext1').removeClass('ui-state-disabled');
	}else{
		$('#btnAssignScorerScoresNext').prop("disabled",true);
		$('#btnAssignScorerScoresNext').addClass('ui-state-disabled');
		$('#btnAssignScorerScoresNext1').prop("disabled",true);
		$('#btnAssignScorerScoresNext1').addClass('ui-state-disabled');
	}
}

function disableScorerAddNextBtn(){
	$('#btnAssignScorerScoresNext').prop("disabled",true);
	$('#btnAssignScorerScoresNext').addClass('ui-state-disabled');
	$('#btnAssignScorerScoresNext1').prop("disabled",true);
	$('#btnAssignScorerScoresNext1').addClass('ui-state-disabled');
}
function disableScorerNextBtn(){
	$('#scorerStudentNextBtn').prop("disabled",true);
	$('#scorerStudentNextBtn').addClass('ui-state-disabled');
}
function enableScorerNextBtn(){
	$('#scorerStudentNextBtn').prop("disabled",false);
	$('#scorerStudentNextBtn').removeClass('ui-state-disabled');
}

function enableOrDisableScorerAddBtn(){
	if( selectedScoresList != null && selectedScoresList.length > 0 && selectedScoresList[0] != null){	
		$('#assignScorerAddScorersBtn').prop("disabled",false);
		$('#assignScorerAddScorersBtn').removeClass('ui-state-disabled');
		$('#assignScorerRemoveScorersBtn').prop("disabled",false);
		$('#assignScorerRemoveScorersBtn').removeClass('ui-state-disabled');
	}else{
		$('#assignScorerAddScorersBtn').prop("disabled",true);
		$('#assignScorerAddScorersBtn').addClass('ui-state-disabled');
		$('#assignScorerRemoveScorersBtn').prop("disabled",true);
		$('#assignScorerRemoveScorersBtn').addClass('ui-state-disabled');
	}
}


function  enableOrDisableStudentAddBtn(){
	if( selectedStudentList != null && selectedStudentList.length > 0 && selectedStudentList[0] != null){		
		$('#assignStudentAddScorersBtn').prop("disabled",false);
		$('#assignStudentAddScorersBtn').removeClass('ui-state-disabled');		
		$('#assignStudentRemoveScorersBtn').prop("disabled",false);
		$('#assignStudentRemoveScorersBtn').removeClass('ui-state-disabled');
	}else{
		$('#assignStudentAddScorersBtn').prop("disabled",true);
		$('#assignStudentAddScorersBtn').addClass('ui-state-disabled');		
		$('#assignStudentRemoveScorersBtn').prop("disabled",true);
		$('#assignStudentRemoveScorersBtn').addClass('ui-state-disabled');
	}
}
function assignstudentCheckSelectedScorersAdded(){
	if( selectedStudentList != null && selectedStudentList.length > 0 && selectedStudentList[0] != null ){
		if( selectedStudentList != null && selectedStudentList.length > 0 && selectedStudentList[0] != null ){
			for(var i=0;i<selectedStudentList.length;i++){
		        if( ! assignStudentSelectedScorerExistList(selectedStudentList[i].studentId)){
		        	assignStudentScorerDeselectMsg();
		        	return false;
		        }
			}
		}else{
			assignStudentScorerDeselectMsg();
			return false;
		}	
	}
	return true;

}
function assignStudentSelectedScorerExistList(scorerId){
	var result = $.grep(selectedStudentList, function(scorerO){ return scorerO.userId == scorerId; });
	return result.length == 0 ? false: true;
}

function assignStudentScorerDeselectMsg(){
	$("#confirmDialogAssignScorerRefreshScorer").dialog({
	      buttons : {
	    	  "Ok" : function() {
	    		  $(this).dialog("close");
	    	   }
	     }
  	});
	$("#confirmDialogAssignScorerRefreshScorer").dialog("open");
}

function assignscoringCheckSelectedScorersAdded(){
	if( selectedScoresList != null && selectedScoresList.length > 0 && selectedScoresList[0] != null ){
		if( selectedScoresList != null && selectedScoresList.length > 0 && selectedScoresList[0] != null ){
			for(var i=0;i<selectedScoresList.length;i++){
		        if( ! assignScorerSelectedScorerExistList(selectedScoresList[i].userId)){
		        	assignScoringScorerDeselectMsg();
		        	return false;
		        }
			}
		}else{
			assignScoringScorerDeselectMsg();
			return false;
		}	
	}
	return true;
}
function assignScorerSelectedScorerExistList(scorerId){
	var result = $.grep(selectedScoresList, function(scorerO){ return scorerO.userId == scorerId; });
	return result.length == 0 ? false: true;
}
function assignScoringScorerDeselectMsg(){
	$("#confirmDialogAssignScorerRefreshScorer").dialog({
	      buttons : {
	    	  "Ok" : function() {
	    		  $(this).dialog("close");
	    	   }
	     }
  	});
	$("#confirmDialogAssignScorerRefreshScorer").dialog("open");
}
function clearAssignScorerTestsessionFilters(clearFromAssessprgChange){
	$('#assignScoresStage').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assignScorerTestPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();	
	$('#assignScorerTestingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assignScorerContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assignScorerGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assignScorerGrades, #assignScorerTestingPrograms, #assignScorerContentAreas , #assignScorerTestPrograms,#assignScoresStage').trigger('change.select2');
	if(! clearFromAssessprgChange){
		$('#assignScorerSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#assignScorerSchool').trigger('change.select2');
	}	
	
}
function viewStudentGrid(rows){
	var grade="Grade";
	var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
	if(userDefaultAssessmentProgram!=null && userDefaultAssessmentProgram!=undefined && userDefaultAssessmentProgram!=""){
		if(userDefaultAssessmentProgram=='CPASS') {
			grade="Pathway";
		}
	}
	 var oldFrom = $.jgrid.from,  lastSelected;
	 $.jgrid.from = function (source, initalQuery) {
	    var result = oldFrom.call(this, source, initalQuery),
	    old_select = result.select;
	    result.select = function (f) {
	        lastSelected = old_select.call(this, f);
	        return lastSelected;
	    };
	    return result;
	 };
	   
	var $gridAuto = $("#viewStudentGridTableId");
	$("#viewStudentGridTableId").jqGrid('clearGridData');
	$("#viewStudentGridTableId").jqGrid("GridUnload");
		gridWidthForVO = 980;				
	var cellWidthForVO = gridWidthForVO/3;	
	var cmforViewStudentGrid = [
		{ label: 'studentId', name : 'studentId', index : 'studentId', hidden : true, hidedlg : true},	
		{ label: 'studentEnrollmentId', name : 'enrollmentId', index : 'enrollmentId', hidden : true, hidedlg : true},	
		{ label: 'studentTestId',key : true, name : 'studentTestId', index : 'studentTestId', hidden : true, hidedlg : true},	
		{ label: 'studentFirstName', name : 'firstName', index : 'firstName', width : cellWidthForVO, search : true, hidden: false, hidedlg: true},
		{ label: 'studentLastName', name : 'lastName', index : 'lastName', width : cellWidthForVO, search : true, hidden: false, hidedlg: true},			
        	{ label: 'stateStudentIdentifier', name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', width :'100px',  search : true, hidden : false, hidedlg : true},	        
		{ label: 'studentDistrict', name : 'districtName', index : 'districtName', width : cellWidthForVO, search : false, hidden : false, hidedlg : false},
		{ label: 'studentSchool', name : 'schoolName', index : 'schoolName', width : cellWidthForVO, search : false, hidden : false, hidedlg : false},
		{ label: 'studentGrade', name : 'abbreviatedName', index : 'abbreviatedName', width : cellWidthForVO, search : false, hidden : false, hidedlg : false},
		{ label: 'studentRoster', name : 'roster', index : 'roster', width : cellWidthForVO, search : true, hidden : false, hidedlg : false},
		{ label: 'TestId', name : 'testId', index : 'testId', width : cellWidthForVO, search : true, hidden : true, hidedlg : true}

	];	
	//JQGRID
	$("#viewStudentGridTableId").scb({
		mtype: "POST",
		datatype : "local",
		data : rows,
		width: gridWidthForVO,
		height:"auto",
		colNames : [ 'studentId','enrollmentId','studentTestId','First Name','Last Name','State Student Identifier','District', 'School', grade,'Roster','TestId'],
	  	colModel :cmforViewStudentGrid,
		rowNum : 10,
		rowList : [ 10, 20, 30, 40, 60, 90 ],
		search: false,
	    sortable:false,
	    sortname: 'lastName, firstName',
	    sortorder: 'asc',
	    viewrecords : true,
        viewable : false,
        page :1,
        pager : '#viewStudentGridPager', 
		multiselect:true,
		columnChooser : false,
		loadonce: true,
		refresh: false,
	    onSelectRow: function(rowid, status, e){
	    	if(e != undefined){
	    	  var rData = $('#viewStudentGridTableId').jqGrid('getRowData',rowid);	    	  
		      var selScorerObj =	{ lastFirstMiddleName :rData.firstName+ " " +  rData.lastName   ,
		    		  studentId : rData.studentId ,enrollmentId :rData.enrollmentId , studentTestId:rData.studentTestId,stateStudentIdentifier:rData.studentTestId,districtName:rData.districtName,schoolName:rData.schoolName,abbreviatedName:rData.abbreviatedName,testId:rData.testId
		    		  				  };		           
         	   if(status){
         		 var found = false; 
         		for(var i=0;i<selectedStudentList.length;i++ ){ 
         			if(selectedStudentList[i].studentTestId == rowid) {
       			    	found = true;
       			    }	
       			 } 
         		 if( ! found )
         			selectedStudentList.push(selScorerObj);
         		 
         	   } else{
         		   for(var i=0;i<selectedStudentList.length;i++ ){
         			    if(selectedStudentList[i].studentTestId == rowid) {
         			    	selectedStudentList.splice(i,1);
         			        break;
         			    }
         			}
         	   }
         	  enableOrDisableStudentAddBtn();
	    	}else{
	    		$gridAuto.jqGrid('setSelection', rowid, false);
	    	}
	    }, 
	    onSelectAll: function(rowids, status, e){	   
			var filteredData = $("#viewStudentGridTableId").jqGrid('getGridParam', 'lastSelected'), i, n, ids = [];
		    if (filteredData) {
		        for (i = 0, n = filteredData.length; i < n; i++) {
		            ids.push(filteredData[i].studentTestId);
		        }		        
		    }	    	
		    selectedStudentList = [];
	        if (status) {
	        		        		for(var i=0;i<filteredData.length;i++){

	 	            	var result = $.grep(selectedStudentList, function(studentO){ 
	 			        return studentO.studentTestId == filteredData[i].studentTestId; });
	 			        if(result.length == 0){
	 			        var selStudentObj =	{ lastFirstMiddleName : filteredData[i].firstName  + " " + filteredData[i].lastName ,
	 			        			  studentId : filteredData[i].studentId ,enrollmentId :filteredData[i].enrollmentId , studentTestId:filteredData[i].studentTestId,
	 			        			  stateStudentIdentifier:filteredData[i].studentTestId,districtName:filteredData[i].districtName,schoolName:filteredData[i].schoolName,
	 			        			  abbreviatedName:filteredData[i].abbreviatedName,testId:filteredData[i].testId
	 			        			  };
	 			        	  selectedStudentList.push(selStudentObj);
	 			        }
	 			    }
	        	}	        
	        else{
	        	
	            for(var i=0;i<filteredData.length;i++){
	            	var result = $.grep(selectedStudentList, function(studentO){ 
	            		return studentO.studentTestId == filteredData[i].studentTestId; });
	            	if( result.length == 1 ){
	            		var indx = selectedStudentList.indexOf(result[0]);
	            		selectedStudentList.splice(indx,1);
	            	}	
	            }
	        }
	       
	        enableOrDisableStudentAddBtn();
	    },
	    
	     loadComplete:function(data){   
	    	 this.p.lastSelected = lastSelected;
	        	for (var i = 0; i < selectedStudentList.length; i++) {
	        		$gridAuto.jqGrid('setSelection', selectedStudentList[i].studentTestId, false);
			}
	    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
	    	 var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'lastName')+ ' Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
	            }
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	             $('#cb_'+tableid).attr('title','Student Grid Select All Check Box');
	             $('#cb_'+tableid).removeAttr('aria-checked');
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');                          
	                    });
	    }
	});
   	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
    $("#refresh_viewStudentGridTableId").hide();
    $('#viewStudentGridTableId').jqGrid('navGrid', '#viewStudentGridPager', {edit: false, add: false, del: false, refresh:false, search:false});
}



	function assignScorerTestSessionRadioButtonFormatter(cellvalue, options, rowObject){
		var htmlString = "";
		htmlString = '<input type="radio" id="testSess_' + options.rowId + '" name="assignScorerselectedTestsession" value=""/>';				
	    return htmlString;
	}
	$('[name=assignScorerselectedTestsession]').on('click', function (e){
		var target = e.target;
		var rid = $(target).closest('input').prop('id');
		rid = rid.replace("testSess_","");
		$('#viewAssignScorerGridTableId').jqGrid('setSelection', rid);
	});
	
	function assignScorerScoingTabShown(){
		if( ( $('#assignScorerScoringDistrict').val() != "" ||
				 $('#assignScorerScoringSchool').val() != "" || 
				 $('#assignScorerScoringAssessmentPrograms').val() != "" ||
				 $('#assignScorerScoringTestingPrograms').val() != "" ) &&
				$('#assignScorerScores_gridContainter').is(":visible") && $('#assignStudentScores_gridContainter').is(":visible") ) 
			return true;	
		return false;
	}
	function scorerTestsessiondateFormatter(cellval, opts, row) {
		
        if(cellval != 'Not Available'){
     	var date = new Date(cellval);
	         opts = $.extend({}, $.jgrid.formatter.date, opts);
	         return $.fmatter.util.DateFormat("", date, 'm/d/Y', opts);
        }else{
     	   return cellval;
        }
  	};
 
	function viewAssignScorerGrid(rows){
		
		
		
		var oldFrom = $.jgrid.from,  lastSelected;

		 $.jgrid.from = function (source, initalQuery) {
		    var result = oldFrom.call(this, source, initalQuery),
		    old_select = result.select;
		    result.select = function (f) {
		        lastSelected = old_select.call(this, f);
		        return lastSelected;
		    };
		    return result;
		 };
		 
		var $gridAuto = $("#viewScorerGridTableId");
		$("#viewScorerGridTableId").jqGrid('clearGridData');
		$("#viewScorerGridTableId").jqGrid("GridUnload");
			gridWidthForVO = 980;				
		var cellWidthForVO = gridWidthForVO/3;
		
		var cmforViewScorerGrid = [
		    {label: 'id', key : true ,name : 'scorerid', index : 'scorerid', hidden : true, hidedlg : true},	
		    { label: 'scorerFirstName', name : 'scorerFirstName', index : 'scorerFirstName', width : cellWidthForVO, search : true, hidden: false, hidedlg: true},
			{ label: 'scorerLastName', name : 'scorerLastName', index : 'scorerLastName', width : cellWidthForVO, search : true, hidden: false, hidedlg: true},			
			{ label: 'scorerDistrict', name : 'districtName', index : 'districtName', width : cellWidthForVO, search : false, hidden : false, hidedlg : true},
			{ label: 'scorerSchool', name : 'schoolName', index : 'schoolName', width : cellWidthForVO, search : false, hidden : false, hidedlg : true},
			{ label: 'MI', name : 'MI', index : 'MI', width :'100px',  search : false, hidden : true, hidedlg : true},			
			{ label: 'Email', name : 'email', index : 'email', width : cellWidthForVO, search : false, hidden: true, hidedlg: true},			
			{ label: 'Testing Program', name : 'testingProgramName', index : 'testingProgramName', width : cellWidthForVO, search : true, hidden : true, hidedlg : true},
			{ label: 'group id', name : 'groupId', index : 'groupId', hidden : true, hidedlg : true},
			{ label: 'Role', name : 'groupCode', index : 'groupCode', search : true,hidden : false, hidedlg : true}
		];
	
		//JQGRID
		$("#viewScorerGridTableId").scb({
			mtype: "POST",
			datatype : "local",
			data : rows,
			width: gridWidthForVO,
			height:"auto",
			colNames : [ 'id', 'First Name','Last Name','District', 'School','MI','Email','Testing Program','group id','Role'],
		  	colModel :cmforViewScorerGrid,
			rowNum : 10,
			rowList : [ 10, 20, 30, 40, 60, 90 ],
		    search: false,
		    sortable:false,
		    viewrecords : true,
	        viewable : false,
	        page:1,
			pager : '#viewScorerGridPager', 
			sortname: 'scorerLastName, scorerFirstName, MI',
			sortorder: 'asc',
			multiselect:true,
			columnChooser : false,
			loadonce: true,
			refresh: false,
		    onSelectRow: function(rowid, status, e){
		    	
		    	if(e != undefined){
			   	 disableScorerAddNextBtn();
		    	  var rData = $('#viewScorerGridTableId').jqGrid('getRowData',rowid);
		    	  var selScorerObj =	{ lastFirstMiddleName :rData.scorerFirstName+" " +rData.scorerLastName  ,
		  				  userId : rData.scorerid, lastFirstName :rData.scorerLastName+" " +rData.scorerFirstName
		  				  };
	         	   if(status){
	         		 var found = false;
	         		for(var i=0;i<selectedScoresList.length;i++ ){
	       			    if(selectedScoresList[i].userId == rowid) {
	       			    	found = true;
	       			    }	
	       			 }
	         		 if(!found ){
	         			selectedScoresList.push(selScorerObj);
	         			
	         		 }
	         		 
	         	   } else{
	         		   for(var i=0;i<selectedScoresList.length;i++ ){
	         			    if(selectedScoresList[i].userId == rowid) {
	         			    	selectedScoresList.splice(i,1);
	         			        break;
	         			    }
	         			}
	         	   }
	         	  enableOrDisableScorerAddBtn();
		    	}else{
		    		$gridAuto.jqGrid('setSelection', rowid, false);
		    	}
		    	
		    	
		    }, 
		    onSelectAll: function(rowids, status, e){
		    	disableScorerAddNextBtn();			    	
		    	var filteredData = $('#viewScorerGridTableId').jqGrid('getGridParam', 'lastSelected'), i, n, ids = [];		    	
			    if (filteredData) {
			        for (i = 0, n = filteredData.length; i < n; i++) {
			            ids.push(filteredData[i].scorerid);
			        }			        
			    }	    	
			    selectedScoresList =[];
		        if (status) {
		        	for(var i=0; i<filteredData.length ;i++){		        		
		        		var result = $.grep(selectedScoresList, function(scorerO){ 
		        			return scorerO.userId == filteredData[i].scorerid; });
		        		if(result == 0){
		        			
			        		var selScorerObj =	{ lastFirstMiddleName : filteredData[i].scorerFirstName   + " " + filteredData[i].scorerLastName ,
				        			  userId : filteredData[i].scorerid , groupCode : filteredData[i].groupCode, lastFirstName : filteredData[i].scorerLastName   + " " + filteredData[i].scorerFirstName 
				        			  };
				        	  selectedScoresList.push(selScorerObj);
		        	    }  
		        	}		        	
		        }
		        else{
		        	for(var i=0; i<filteredData.length ;i++){
		        		var result = $.grep(selectedScoresList, function(scorerO){ return scorerO.userId == filteredData[i].scorerid; });
		            	if( result.length == 1 ){
		            		var indx = selectedScoresList.indexOf(result[0]);
		            		selectedScoresList.splice(indx,1);
		            	}	
		            }
		        }
		        
		        enableOrDisableScorerAddBtn();
		    },
		    loadComplete:function(data){
		    	 this.p.lastSelected = lastSelected;
		    	for (var i = 0; i < selectedScoresList.length; i++) {
		    		$gridAuto.jqGrid('setSelection', selectedScoresList[i].userId, false);
	            }
		    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
		    	
		    	var ids = $(this).jqGrid('getDataIDs');         
		         var tableid=$(this).attr('id');      
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'scorerFirstName') +' '+$(this).getCell(ids[i], 'scorerLastName')+ ' Check Box');
		                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
		            }
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		             $('#cb_'+tableid).attr('title','Scorer Grid Select All Check Box');
		             $('#cb_'+tableid).removeAttr('aria-checked');
		             $.each(objs, function(index, value) {         
		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                    $(value).attr('title',$(nm).text()+' filter');                          
		                    });
		    }
		});
	   	$gridAuto.jqGrid('setGridParam',{
			postData: { "filters": ""}
		}).trigger("reloadGrid",[{page:1}]);
	    $("#refresh_viewScorerGridTableId").hide();
	    $('#viewScorerGridTableId').jqGrid('navGrid', '#viewScorerGridPager', {edit: false, add: false, del: false, refresh:false, search:false});
	}
	
	 function showGridStudent(){
			var $gridAuto = $("#assignScorerStudentGridTableId");			
			$("#assignScorerStudentGridTableId").jqGrid('clearGridData');
			$("#assignScorerStudentGridTableId").jqGrid("GridUnload");	
			var gridWidthForVO = 350;		
			if(gridWidthForVO <= 350) {
				gridWidthForVO = 485;				
			}
			var cmforViewScorerGrid = [
			    { label: 'id', name : 'studentId', index : 'studentId', search:false, width :0, hidden: true, hidedlg: false},
			    { label: 'studentTestId', name : 'studentTestId',index : 'studentTestId',key:true, search:false, width :0, hidden: true, hidedlg: false},
			    { label: 'enrollmentId', name : 'enrollmentId',index : 'enrollmentId', search:false, width :0, hidden: true, hidedlg: false},
			    { label: 'testId', name : 'testId',index : 'testId', search:false, width :0, hidden: true, hidedlg: false},
			    { label: 'First Name, Last Name', align:"left", name : 'lastFirstMiddleName', index : 'lastFirstMiddleName', 
			    	search:false, width : 398, hidden: false, hidedlg: true 
		        },
		        {label: ' ', name: 'delete', index: 'delete', hidedlg: true,width: 100,sortable:false,search:false, formatter:deleteAssignStudentFormatter}
			];
			//JQGRID
		  	$gridAuto.scb({
				mtype: "POST",
				datatype : "local",
				width: gridWidthForVO,
				height : '200px',			
				colNames : ['id','studentTestId','enrollmentId','testId', 'First Name, Last Name ',' '],
			  	colModel :cmforViewScorerGrid,
				sortname : 'lastFirstMiddleName',
				viewrecords: true,
				rowNum:100,
				rowList: [100],        // disable page size dropdown
			    pgbuttons: false,     // disable page control like next, back button
			    pgtext: null,  
			    loadonce: false,
				refresh: false,
				viewable: false,
				localReader: {
			        page: function (obj) {
			            return obj.page !== undefined ? obj.page : "0";
			        }
			    },
		  		jsonReader: {
			        page: function (obj) {
			            return obj.page !== undefined ? obj.page : "0";
			        },
			        repeatitems:false,
			    	root: function(obj) { 
			    		return obj.rows;
			    	} 
			    },
			    beforeRequest: function() {
			    	
			    },beforeSelectRow: function (rowid, e) {
			    	if(assignScorerDisableForm){
			    		return false;
			    	}else {
			    		return true;
			    	}
			    },
			    loadComplete:function(data){
			    	
			    	if(data.total > 0){
			    		enableScorerNextBtn();
			    	} else {
			    		disableScorerNextBtn();
			    	}
			    	
			    }
			});
		  	
		   	$gridAuto.jqGrid('setGridParam',{
				postData: { "filters": ""}
			}).trigger("reloadGrid",[{page:1}]);
		   	$gridAuto[0].toggleToolbar();
	   
		    
		
	 }
function showAssignScorer(){
		// Assign Scorer Grid
		var $gridAuto = $("#selectScorerGridTableId");
		//Unload the grid before each request.
		$("#selectScorerGridTableId").jqGrid('clearGridData');
		$("#selectScorerGridTableId").jqGrid("GridUnload");
		var gridWidthForVO = 350;		
		if(gridWidthForVO <= 350) {
			gridWidthForVO = 485;				
		}
		var cmforViewScorerGrid = [
		    { label: 'id', name : 'userId', index : 'userId',key:true, search:false, width :0, hidden: true, hidedlg: true},
			{ label: 'Last Name, First Name', name : 'lastFirstName', index : 'lastFirstName', sortable:true, width :0, search:false, hidden: true, hidedlg: false},
		    { label: 'First Name, Last Name ',align:"left", name : 'lastFirstMiddleName', index : 'lastFirstMiddleName', sortable:true,search:false, width : 398, hidden: false, hidedlg: true},
			{ label: ' ', name: 'delete', index: 'delete', hidedlg: true, width:0, hidden: false, search:false, sortable:false,
	        	formatter:deleteAssignScorerFormatter
	        }
	        
		];
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
		height : '200px',	
		colNames : ['id', ' ', 'First Name,Last Name', ' '],
	  	colModel :cmforViewScorerGrid,		
	  	viewrecords: false,
	  	rowNum:100,
		rowList: [100],        // disable page size dropdown
	    pgbuttons: false,     // disable page control like next, back button
	    pgtext: null,
		sortname : 'lastFirstName',
		sortorder: "asc",
		loadonce: false,
		refresh: false,
		viewable: false,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
	    beforeRequest: function() {
	    },beforeSelectRow: function (rowid, e) {
	    	if(assignScorerDisableForm){
	    		return false;
	    	}else {
	    		return true;
	    	}
	    },loadComplete: function () {
	    	
	    }
	});
   	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
   	$gridAuto[0].toggleToolbar();
}
	function deleteAssignScorerFormatter(cellValue, options, rowObject){
		return '<a href="javascript:;" onclick="assignScorerDeleteScorerFromList(' +options.rowId+');"><img src="images/delete_icon.gif" title="Delete"/> </a>'; 
	}
	function assignScorerDeleteScorerFromList(rowId){
		if(assignScorerDisableForm){
    		return false;
    	}	
		$("#confirmDialogAssignScorerDeleteScorer").dialog({
		      buttons : {
		     "Yes" : function() {
		    	 deleteAssignScorerJqGridRowData("selectScorerGridTableId",rowId);
		    	 for(var i = 0; i< selectedScoresList.length; i++){
			 			var result = $.grep(selectedScoresList,function(scorerO){		 				
			 				return scorerO.userId == rowId; });				 			
			 					if(result.length){
			 						var indx = selectedScoresList.indexOf(result[0]);					
			 						selectedScoresList.splice(indx,1);
			 						}
			 					}	
		    	 $(this).dialog("close");
		    	 $(".info-msg-content").show();
		     },
		     "No" : function(){
		       $(this).dialog("close");
		      }
		     }
		 });
		$("#confirmDialogAssignScorerDeleteScorer").dialog("open");
	}
function deleteAssignScorerJqGridRowData(gId,rowId){
		
		$("#"+gId).jqGrid('delRowData',rowId);
		var scorersIds = $("#selectScorerGridTableId").jqGrid('getDataIDs');
		
		if( scorersIds.length == 0 ){
			$("#scorerStudentNextBtn").prop("disabled",true);
			$('#scorerStudentNextBtn').addClass('ui-state-disabled');
			$("#btnAssignScorerScoresNext").prop("disabled",true);
			$('#btnAssignScorerScoresNext').addClass('ui-state-disabled');
			$("#btnAssignScorerScoresNext1").prop("disabled",true);
			$('#btnAssignScorerScoresNext1').addClass('ui-state-disabled');
			assignScoringSaveAreaHide();
		}
	}
	
	function deleteAssignStudentFormatter (cellValue, options, rowObject){
		
		return '<a href="javascript:;" onclick="assignScorerDeleteStudentFromList(' +options.rowId + ');"><img src="images/delete_icon.gif" title="Delete"/> </a>'; 
	}
	
	function assignScorerDeleteStudentFromList(rowId){
		
		if(assignScorerDisableForm){
    		return false;
    	}		
		$("#confirmDialogAssignScorerDeleteStudent").dialog({
		      buttons : {
		     "Yes" : function() {
		    	 deleteAssignStudentJqGridRowData("assignScorerStudentGridTableId",rowId);
		    	 for(var i = 0; i< selectedStudentList.length; i++){
		 			var result = $.grep(selectedStudentList,function(studentO){
		 				
		 				return studentO.id == rowId; });	
		 			
		 					if(result.length){
		 						var indx = selectedStudentList.indexOf(result[0]);					
		 						selectedStudentList.splice(indx,1);
		 						}
		 					}	
		    	 $(this).dialog("close");
		    	 $(".info-msg-content").show();
		     },
		     "No" : function(){
		       $(this).dialog("close");
		      }
		     }
		 });
		$("#confirmDialogAssignScorerDeleteStudent").dialog("open");
	}
function deleteAssignStudentJqGridRowData(gId,rowId){
		$("#"+gId).jqGrid('delRowData',rowId);
		var studentIds = $("#assignScorerStudentGridTableId").jqGrid ('getDataIDs');
		if( studentIds.length == 0){
			$("#scorerStudentNextBtn").prop("disabled",true);
			$('#scorerStudentNextBtn').addClass('ui-state-disabled');
			$("#btnAssignScorerScoresNext").prop("disabled",true);
			$('#btnAssignScorerScoresNext').addClass('ui-state-disabled');
			$("#btnAssignScorerScoresNext1").prop("disabled",true);
			$('#btnAssignScorerScoresNext1').addClass('ui-state-disabled');
			assignScoringSaveAreaHide();
		}
	
	}
	 function showAssignScorerDistrict(){
		 $('#assignScorerScoringSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		 $('#assignScorerScoringDistrict').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		 $('#assignScorerScoringSchool,#assignScorerScoringDistrict').trigger('change.select2');
		// District Value
			var districtOrgSelect = $('#assignScorerScoringDistrict');
			$.ajax({
				url: 'getOrgsBasedOnUserContext.htm',
				dataType: 'json',
				data: {
					orgId : $("#assignScorerUserCurrentOrgId").val(), //${user.currentOrganizationId},
			    	orgType:'DT',
			    	orgLevel:50
			    	}
			}).done(function (districtOrgs) { 
				if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
					$.each(districtOrgs, function(i, districtOrg) {
						optionText = districtOrgs[i].organizationName;
						districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
					});
					
					if (districtOrgs.length == 1) {
						districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
    					$("#assignScorerSchool").trigger('change');
    				} 
				} else {
					$('body, html').animate({scrollTop:0}, 'slow');
					$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
				}
				$('#assignScorerScoringSchool, #assignScorerScoringDistrict').trigger('change.select2'); 
			}).fail(function (jqXHR, textStatus, errorThrown) { 
				onsole.log(errorThrown);
			});

	 }
	 
	 function assignScorerViewTestSession(disabledValue){
		 $('#assignScorerSearchScorersBtn,#viewTestSessionNextBtn,#assignScorerAddScorersBtn,#assignScorerRemoveScorersBtn,#assignStudentAddScorersBtn,#assignStudentRemoveScorersBtn,#btnAssignScorerScoresNext,#btnAssignScorerScoresNext1,#scorerStudentNextBtn').prop("disabled",disabledValue);
		 if( disabledValue ){
			 $("#assignScorerScoringDistrict,#assignScorerScoringSchool,#assignScorerScoringAssessmentPrograms,#assignScorerScoringTestingPrograms,#assignScorerGrades,#assignScorerAssessmentPrograms, #assignScorerTestPrograms, #assignScorerContentAreas, #assignScorerDistrict, #assignScorerSchool , #assignScoresStage").prop("disabled",disabledValue);
			 $('#assignScorerSearchScorersBtn,#viewTestSessionNextBtn,#assignScorerAddScorersBtn,#assignScorerRemoveScorersBtn,#assignStudentAddScorersBtn,#assignStudentRemoveScorersBtn,#btnAssignScorerScoresNext,#btnAssignScorerScoresNext1,#scorerStudentNextBtn').addClass('ui-state-disabled');
		 }else{
			 $("#assignScorerScoringDistrict,#assignScorerScoringSchool,#assignScorerScoringAssessmentPrograms,#assignScorerScoringTestingPrograms,#assignScorerGrades,#assignScorerAssessmentPrograms, #assignScorerTestPrograms, #assignScorerContentAreas, #assignScorerDistrict, #assignScorerSchool ,#assignScoresStage").prop("disabled",disabledValue);
			 $("#assignScorerScoringDistrict,#assignScorerScoringSchool,#assignScorerScoringAssessmentPrograms,#assignScorerScoringTestingPrograms,#assignScorerGrades,#assignScorerAssessmentPrograms, #assignScorerTestPrograms, #assignScorerContentAreas, #assignScorerDistrict, #assignScorerSchool , #assignScoresStage").removeAttr("aria-disabled");
			 $("#assignScorerDistrict").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerScoringDistrict").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerScoringSchool").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerScoringAssessmentPrograms").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerScoringTestingPrograms").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerGrades").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScoresStage").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerAssessmentPrograms").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerTestPrograms").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerContentAreas").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerDistrict").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerSchool").next('div').children().removeClass("ui-state-disabled");
			 $("#assignScorerDistrict_filter").parent('div').removeClass("ui-state-disabled");
			 $("#assignScorerDistrict_filter").prop("disabled",disabledValue);			 
			 $("#assignScorerSchool_filter").parent('div').removeClass("ui-state-disabled");
			 $("#assignScorerSchool_filter").prop("disabled",disabledValue);			 
			 $("#assignScorerScoringDistrict_filter").parent('div').removeClass("ui-state-disabled");
			 $("#assignScorerScoringDistrict_filter").prop("disabled",disabledValue);			 
			 $("#assignScorerScoringSchool_filter").parent('div').removeClass("ui-state-disabled");
			 $("#assignScorerScoringSchool_filter").prop("disabled",disabledValue);
		 }
		 
		 $("#assignScorerScoringDistrict,#assignScorerScoringSchool,#assignScorerScoringAssessmentPrograms,#assignScorerTestPrograms,#assignScorerGrades,#assignScorerAssessmentPrograms, #assignScorerTestPrograms, #assignScorerContentAreas, #assignScorerDistrict, #assignScorerSchool , #assignScoresStage").select2();
	
		 $("#viewStudentGridTableId").jqGrid("clearGridData", true).trigger("reloadGrid");
		 $("#viewScorerGridTableId").jqGrid("clearGridData", true).trigger("reloadGrid");
		 $("#assignScorerStudentGridTableId").jqGrid("clearGridData", true).trigger("reloadGrid");
		 $("#selectScorerGridTableId").jqGrid("clearGridData", true).trigger("reloadGrid");
		 $('#viewOrgStudentGridContainer,#viewOrgScorerGridContainer').find(':input')
	        .each(function() {	        	
	            $(this).prop('disabled',disabledValue);
	      });				 
		 assignScorerDisableForm =  disabledValue;		
		 var colM = jQuery('#viewStudentGridTableId').jqGrid('getGridParam','colModel');	 
		 if(colM != undefined)			
		 for(var i=0;i<colM.length;i++){
			 jQuery('#viewStudentGridTableId').setColProp( colM[i].name, {sortable: !disabledValue});
		 }	
		 var colMM = jQuery('#viewScorerGridTableId').jqGrid('getGridParam','colModel');	 
		 if(colMM != undefined)			
		 for(var i=0;i<colMM.length;i++){
			 jQuery('#viewScorerGridTableId').setColProp( colMM[i].name, {sortable: !disabledValue});
		 }	
		
	  }
	 
	 function refreshSelectBtn(id){
			$("#"+id).find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$("#"+id).val('').trigger('change.select2');
		}

		function assignScorerMoveScrollBar(pos,size){
			
		var len =	$('#'+pos).offset().top - $('#tabs_assignscorers').offset().top;
			$(".with-sidebar-content").animate({
		        scrollTop:  len+size
		   });
		}
		
	 