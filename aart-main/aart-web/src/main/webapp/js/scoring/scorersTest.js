var scoreTestScoringAssignmentId = null;
var scoreTestScoringAssignmentScorerId = null;
var scoreTestStudentId = null;
var scoreTestSelectedTestSessionId = [];
var testSessionNameCCQ = null;
var testSessionIdVal = null;
var numberOfDynamiccolumn = null;

var testScoreAllStudentInAllTestSessionScored = false;
var scorerTestStudentScrollBarMove = false;

function scoringScorersTestInit(){
	testScoreAllStudentInAllTestSessionScored = false;
	resetScorerScoreTests(); 
	testScorerTestSessionInit();
	
	$("#confirmDialogSelectClearScorerTest").dialog({
	    autoOpen: false,
	    modal: true
	});
	
	$('#scoreTestsDistrict, #scoreTestsSchool, #scorerTestContentAreas, #scorerTestGrades, #nonScoreReasons').select2({
			placeholder:'Select', 
			multiple: false,
			allowClear : true
	});
	
}

function resetScorerScoreTests(){
	if($("#scorerTestStudentGridTableId")[0].grid && $("#scorerTestStudentGridTableId")[0]['clearToolbar']){
		$("#scorerTestStudentGridTableId")[0].clearToolbar();
	}
	
	$('#scoreTestsSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#scoreTestsSchool').trigger('change.select2');
	
	$('#scorerTestContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#scorerTestGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	
	$("#scorerTestContentAreas").val("").trigger('change.select2');
	$("#scorerTestGrades").val("").trigger('change.select2');
	testScoreTestSessionHide();
}

function testScoreTestSessionHide(){
	$('#scorerTestStudentScoredGridContainer').hide();
	$("#scoringTestQuestionResponseContainerData").hide();
	disableTestScoreTestSessionSearch();
	testScoreStudentHide();
}
function testScoreStudentHide(){
	$('#scorerStudentsAppearsScoreTestGridContainer').hide();
	testScoreTestCriteriaHide();
}
function testScoreTestCriteriaHide(){
	$('#scoringTestCriteriaTableContainer').hide();
	$("#continueScoringQuitScoringBtns").hide();
	$("#scorerTestSubmitScoreBtn").prop("disabled",true);
	$('#scorerTestSubmitScoreBtn').addClass('ui-state-disabled');
	testScoreTestSavedSuccessMessageHide();
	testScoreAllTestsessionCompletedConfirmationHide();
	testScoreNextTestSessionConfirmationHide();
	testScoreNextStudentConfirmationHide();
}
function disableTestScoreTestSessionSearch(){
	$("#scorerTestSearchBtnTestSessions").prop("disabled",true);
	$('#scorerTestSearchBtnTestSessions').addClass('ui-state-disabled');
}
function scoreTestCheckAllTestSessionStudentScored(){
	var assessmentPrgId = $('#userDefaultAssessmentProgram').val();
	var subjectId = $("#scorerTestContentAreas").val();
	var gradeId = $("#scorerTestGrades").val();  	
	$.ajax({
		url: 'checkAllTestSessionStudentsScoredForScorer.htm',
        dataType: 'json',
        data:{
        	assessmentPrgId:assessmentPrgId,
			subjectId:subjectId,
			gradeId:gradeId
        },
        type: "GET"
	}).done(function (result) { 
		if( result.status == "COMPLETED"){
    		testScoreAllStudentInAllTestSessionScored = true;
    		$("#tstScorlblNextTestSession").hide();
    		$("#tstScorlblAllCompltd").show();
    	}
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}


function testScorerTestSessionInit(){
	
	//Tiny Text changes	
	initTinytextChanges($("#scoreTestsUserAccesslevel").val());
	
	scorerTestStudentScoredGrid();
	
	$('#scorerTestAssessmentPrograms,#scorerTestContentAreas,#scorerTestGrades,#nonScoreReasons,#scoreTestsDistrict,#scoreTestsSchool').select2({
		placeholder:'Select',
		multiple: false
	});
	
	filteringOrganization($('#scoreTestsDistrict'));
	filteringOrganization($('#scoreTestsSchool'));
	 
//district and school
	$('#scoreTestsDistrict').on("change",function() {
		
		//Added for tiny text changes
		districtEventTinyTextChanges($(this).find('option:selected').text());	
		
		$('#scoreTestsSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#scorerTestContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#scorerTestGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		
		$('#scoreTestsSchool').trigger('change.select2');
		$('#scorerTestContentAreas').trigger('change.select2');
		$('#scorerTestGrades').trigger('change.select2');
		
		$("#scorerTestSearchBtnTestSessions").prop("disabled",true);
		$('#scorerTestSearchBtnTestSessions').addClass('ui-state-disabled');
		
		scoreTestsDistrictChangeEvent();
	});	
	if(scoreAllTest==true){
		var districtOrgSelect = $('#scoreTestsDistrict');
		$.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
			dataType: 'json',
			data: {
				orgId : $("#scoreTestsUserCurrentOrgId").val(), //${user.currentOrganizationId},
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
					scoreTestsDistrictChangeEvent();
				} 
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
			}
			$('#scoreTestsSchool, #scoreTestsDistrict').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
	}
//Subject
	$('#scoreTestsSchool').on("change",function() {

		//Added for tiny text changes
		schoolEventTinyTextChanges($(this).find('option:selected').text());
		var assessmentProgramId = $('#userDefaultAssessmentProgram').val();
		var schoolId = $('#scoreTestsSchool').val(); 
		
		$('#scorerTestContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#scorerTestGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		
		$('#scorerTestContentAreas').trigger('change.select2');
		$('#scorerTestGrades').trigger('change.select2');
		
		$("#scorerTestSearchBtnTestSessions").prop("disabled",true);
		$('#scorerTestSearchBtnTestSessions').addClass('ui-state-disabled');
		
		if(schoolId!=0){
			if(assessmentProgramId !== "" && assessmentProgramId !== undefined ){
				$.ajax({
					url: 'getScoreTestContentAreasByAssessmentProgram.htm',
			        data: {
			        	assessmentProgramId : assessmentProgramId,
			        	schoolId : schoolId
			        	},
			        dataType: 'json',
			        type: "POST"
				}).done(function (contentAreas) { 
					$('#scorerTestContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
					$.each(contentAreas, function(i, contentArea) {
						$('#scorerTestContentAreas').append($('<option></option>').attr("value", contentArea.id).text(contentArea.name));
					});
					
					if (contentAreas.length == 1) {
						$("#scorerTestContentAreas option").removeAttr('selected').next('option').attr('selected', 'selected');
						$("#scorerTestContentAreas").trigger('change');
					}
					$('#scorerTestContentAreas').trigger('change.select2');
				}).fail(function (jqXHR, textStatus, errorThrown) {
					console.log(errorThrown);
				});

			} else {
				testScoreTestSessionHide();
			}
		}
	});
	
	function scoreTestsDistrictChangeEvent(){
		var districtOrgId = $('#scoreTestsDistrict').val();
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
					$('#scoreTestsSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				});		
				if (schoolOrgs.length == 1) {
					$("#scoreTestsSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#scoreTestsSchool").trigger('change');
				}
				$('#scoreTestsSchool').trigger('change.select2');
			}).fail(function (jqXHR, textStatus, errorThrown) { 
				console.log(errorThrown);
			});
		}
	}

$('#nonScoreReasons').on("change",function() {
	var nonScoreReasonValue = $('#nonScoreReasons').val();
	var totalRubricCategory = $('#rubrictabs li').length;
	var scoredRubric = $('input:radio[name^=rc_]:checked');
	
	if(nonScoreReasonValue != null && nonScoreReasonValue.length > 0){
		if(scoredRubric.length == 0 || totalRubricCategory == scoredRubric.length){
			$("#scorerTestSubmitScoreBtn").prop("disabled",false);
			$('#scorerTestSubmitScoreBtn').removeClass('ui-state-disabled');	
		}		
	}else{
		if(totalRubricCategory == scoredRubric.length){
			$("#scorerTestSubmitScoreBtn").prop("disabled",false);
			$('#scorerTestSubmitScoreBtn').removeClass('ui-state-disabled');
		}else{
			$("#scorerTestSubmitScoreBtn").prop("disabled",true);
			$('#scorerTestSubmitScoreBtn').addClass('ui-state-disabled');
		}		
	}
	
});


//scorerTestContentAreas
$('#scorerTestContentAreas').on("change",function() {			
	//Added for tiny text changes
	subjectEventTinyTextChanges($(this).find('option:selected').text());
	$('#scorerTestGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#scorerTestGrades').trigger('change.select2');
	
	$("#scorerTestSearchBtnTestSessions").prop("disabled",true);
	$('#scorerTestSearchBtnTestSessions').addClass('ui-state-disabled');
	
	var assessmentProgramId = $('#userDefaultAssessmentProgram').val();
	var schoolId = $('#scoreTestsSchool').val(); 
	var contentAreaId = $('#scorerTestContentAreas').val();
	if (contentAreaId != 0) {
		$.ajax({
			url: 'getScoreTestGradeCourseByContentAreaId.htm',
	        data: {
	        	contentAreaId : contentAreaId,
	        	assessmentProgramId : assessmentProgramId,
	        	schoolId : schoolId
	        	},
	        dataType: 'json',
	        type: "GET"
		}).done(function (grades) {
			$('#scorerTestGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
			$.each(grades, function(i, grade) {
				$('#scorerTestGrades').append($('<option></option>').attr("value", grade.id).text(grade.name));
			});
			
			if (grades.length == 1) {
				$("#scorerTestGrades option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#scorerTestGrades").trigger('change');
			}				
			$('#scorerTestGrades').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
	}
});	

//scorerTestGrades
$('#scorerTestGrades').on("change",function() {	
	
	//Added for tiny text changes
	gradeEventTinyTextChanges($(this).find('option:selected').text());
	
	$("#scorerTestSearchBtnTestSessions").prop("disabled",true);
	$('#scorerTestSearchBtnTestSessions').addClass('ui-state-disabled');
	
	
	if(($('#scoreTestsDistrict').val()!='' && $('#scoreTestsSchool').val()!='' 
		&& $('#scorerTestContentAreas').val()!='' && $('#scorerTestGrades').val()!='')){
			$("#scorerTestSearchBtnTestSessions").prop("disabled",false);
			$('#scorerTestSearchBtnTestSessions').removeClass('ui-state-disabled');
	}
});	


if(scoreTest==true && scoreAllTest==false){
	if($("#scorerTestStudentGridTableId")[0].grid && $("#scorerTestStudentGridTableId")[0]['clearToolbar']){
		$("#scorerTestStudentGridTableId")[0].clearToolbar();
	}
	
	$('#scorerTestStudentScoredGridContainer').show();
	scoreTestLoadTestSessions();
	scorerTestMoveScrollBar("scorerTestStudentGridContainer",-115);
	$('#scorerStudentsAppearsScoreTestGridContainer').hide();
	$('#scoringTestCriteriaTableContainer').hide();	
	$('#scoringTestCriteriaTableContainerData').hide();

	$('#scorerTestSelectAssignmentHeader').hide();
	$('#scorerTestContentAreasDiv').hide();	
	$('#scorerTestGradesDiv').hide();
	$('#scorerTestDistrictDiv').hide();
	$('#scorerTestSchoolDiv').hide();
	$('#scorerTestSearchBtnTestSessions').hide();
}
$('#scorerTestSearchBtnTestSessions').off("click").on("click",function(event) {
	$('#scorerTestStudentScoredGridContainer').show();
	scoreTestLoadTestSessions();
	scorerTestMoveScrollBar("scorerTestStudentGridContainer",-115);
	$('#scorerStudentsAppearsScoreTestGridContainer').hide();
	$('#scoringTestCriteriaTableContainer').hide();	
	$('#scoringTestCriteriaTableContainerData').hide();	
});

	$('#scorerTestSubmitScoreBtn').off('click').on('click',function(){
		saveScoringScoreTest();	    	 
   	 	//$(this).dialog("close");
   	 	$("#scoringTestCriteriaTableContainerData").show();		         
        $("#scorerTestSubmitScoreBtn").addClass("ui-state-disabled");
        $("#scorerTestSubmitScoreBtn").prop("disabled",true);
        $('#nonScoreReasons').prop('disabled', true);
        $(".scoreRadio").attr("disabled","disabled");
		
	});
	

	$('#btnScoreTestNextTestSessionNo').off('click').on('click',function(){
		scoringScorersTestInit();
	});
	$('#btnScorerTestNextStudetYes').off('click').on('click',function(){
		soreTestMoveToNextStudent();
	});
	$('#btnScorerTestNextStudetNo').off('click').on('click',function(){
		scoringScorersTestInit();
	});
	$('#btnCloseScorerTestTestSession').off('click').on('click',function(){
		scoringScorersTestInit();
	});

	var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
	if(userDefaultAssessmentProgram!=null && userDefaultAssessmentProgram!=undefined && userDefaultAssessmentProgram!=""){
		if(userDefaultAssessmentProgram=='CPASS'){
			$('#scorerTestGradeLabel').text("PATHWAY ").append("<span class='lbl-required'>*</span>");
			$('#tstScorlblNextTestSession').text("Select CCQ Assignment To Score");
			$('#scorerTestGrades').attr('title','Pathway');
		}
		else{		
			$('#scorerTestGradeLabel').text("GRADE ").append("<span class='lbl-required'>*</span>");
			$('#scorerTestGrades').attr('title','Grade');
		}				
	}	
	
	$('#continueScoringItemSubmitScoreBtn').off('click').on('click',function(){	
		var sum=0;
		var studentsTestsId = $(this).attr("studentsTestsId");
		var tasksvariants = document.getElementsByClassName("rowIndex_"+studentsTestsId);
   		if(tasksvariants.length>0){
   		    for (var i = 0; i < tasksvariants.length; i++) {
   		    	sum++;	
   		  	}
   		 }
   		if(sum==0){
			callTestSessionStudentClear();
		}else{
			var colId = $(this).attr("colid");			  
			colId=colId.replace("colPosition_"+studentsTestsId+"_","");
		    var status = $(".colPosition_"+studentsTestsId+"_"+colId).attr("status");
		    if(status=='Scored'){
		    	colId=parseInt(colId)+1;
		    }
		    if(document.getElementsByClassName("colPosition_"+studentsTestsId+"_"+colId).length>0){
		    	document.getElementsByClassName("colPosition_"+studentsTestsId+"_"+colId)[0].click();
		    }
		    else{
		    	if(document.getElementsByClassName("colPosition_"+studentsTestsId+"_1").length>0){
		    		document.getElementsByClassName("colPosition_"+studentsTestsId+"_1")[0].click();
		    	}    	
		    }	
		}
		
		$('#nonScoreReasons').prop('disabled', false);
	    $('#scoringTestCriteriaTableContainerData').hide();
	    $('#scoringTestQuestionResponseContainerData').hide();
	});
	
	$('#continueScoringStudentSubmitScoreBtn').off('click').on('click',function(){
			var sum=0;
			var taskVariantIds = $(this).attr("taskvariantids");
			var tasksvariants = document.getElementsByClassName("rowIndexVariant_"+taskVariantIds);
	   		if(tasksvariants.length>0){
	   		    for (var i = 0; i < tasksvariants.length; i++) {
	   		    	sum++;	
	   		  	}
	   		 }
			
			if(sum==0){
				callTestSessionStudentClear();
			}else{
				var rowId = $(this).attr("rowid");			  
				rowId=rowId.replace("rowPosition_"+taskVariantIds+"_","");
				var status = $(".rowPosition_"+taskVariantIds+"_"+rowId).attr("status");
				if(status=='Scored'){
					rowId=parseInt(rowId)+1;
				}
			    if(document.getElementsByClassName("rowPosition_"+taskVariantIds+"_"+rowId).length>0){
			    	document.getElementsByClassName("rowPosition_"+taskVariantIds+"_"+rowId)[0].click();
			    }
			    else{
			    	if(document.getElementsByClassName("rowPosition_"+taskVariantIds+"_1").length>0){
			    		document.getElementsByClassName("rowPosition_"+taskVariantIds+"_1")[0].click();
			    	}    	
			    }		    
			}
					
			$('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid('setSelection', $(this).attr("studentid"));				
			$('#nonScoreReasons').prop('disabled', false);
		    $('#scoringTestCriteriaTableContainerData').hide();
		    $('#scoringTestQuestionResponseContainerData').hide();
	});
	
	$('#quitScoringStudentSubmitScoreBtn').off('click').on('click',function(){		
		$("#scoringTestCriteriaTableContainerData").hide();
		$("#scorerStudentsAppearsScoreTestGridContainer").hide();
		$("#scorerTestStudentScoredGridContainer").hide();
		$("#scoringTestCriteriaTableContainer").hide();	
		$("#scoringTestQuestionResponseContainerData").hide();
		$('#scorerTestSearchContainer').show();	
		testEventTinyTextChanges('');
	});
	
    $('#nonScoreReasons').prop('disabled', false);
    $('#scorerTestSearchContainer').show();	
    $('#stimulusviewlinkid').off("click").on("click",function(event) {
    	
    	var taskVariantId =$(this).attr('taskVariantId');
    	var testletId=$(this).attr('value');
 	   previewStimulus(testletId,taskVariantId);
 });


}

function scoreTestLoadTestSessions(){	
	var assessmentPrgId = $('#userDefaultAssessmentProgram').val();
	var subjectId = $("#scorerTestContentAreas").val();
	var gradeId = $("#scorerTestGrades").val();
	var schoolId = $("#scoreTestsSchool").val();
	
	scoreTestScoringAssignmentId = null;
	scoreTestScoringAssignmentScorerId = null;
	scoreTestStudentId = null;
	studentTestId = null;
	
	var $gridAuto = $("#scorerTestStudentGridTableId");
	$gridAuto.jqGrid('clearGridData');
	
	var myStopReload = function () {
		$gridAuto.off("jqGridToolbarBeforeClear", myStopReload);
        return "stop"; // stop reload
    };
    $gridAuto.on("jqGridToolbarBeforeClear", myStopReload);
    if ($gridAuto[0].ftoolbar) {
    	$gridAuto[0].clearToolbar();
    }
	
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json", 
		url : 'getTestScorerAssignedTestSessions.htm',
		search: false, 
		postData: {
				   "filters":"",
				   assessmentProgramId : assessmentPrgId,
				   subjectId : subjectId,
				   gradeId : gradeId,
				   schoolId: schoolId
				   },
		loadBeforeSend: function(request) {
			// For some reason, this request doesn't get the CSRF information
			// As a quick band-aid that we needed to get out, this worked
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			request.setRequestHeader(header, token);
		},
	}) .trigger("reloadGrid",[{page:1}]);
}

function showgridintestscoringtab(scoringAssignmentId,testsessionId){
	
	//var colmodel=null;
	//var colname=null;
	
	$("#scorerStudentsAppearsForScoreTestGridTableId").closest(".ui-jqgrid").find('.loading').show();
	$("#lui_scorerStudentsAppearsForScoreTestGridTableId").show();

	if(testSessionIdVal !=null){
		var assessmentPrgId = $("#userDefaultAssessmentProgram").val();
		$.ajax({
			url: 'getscorerStudentsAppearsForScoreTest.htm',
			dataType: 'json',
			type: 'POST',
			data: {
				scoringAssignmentId :scoringAssignmentId,	
				assessmentProgramId :assessmentPrgId
			}
		}).done(function (respopnse) { 
			numberOfDynamiccolumn = respopnse.numberOfDynamicColumn;
			var colModels=respopnse.ColModel;
			var alinkFormatter=linkFormatter;
			if(colModels!=null && colModels!=undefined){
				if(colModels.length>0){
					for(var i=0;i<colModels.length;i++){
						var map = colModels[i];
						map["formatter"] = alinkFormatter;
					}
				}
			}		

			jQuery("#scorerStudentsAppearsForScoreTestGridTableId").jqGrid("clearGridData");
			$('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid('clearGridData');
			$('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid("GridUnload");
			scorerStudentsAppearsForScoreTestGrid(colModels,respopnse.ColNames, respopnse);
			$("#refresh_scorerStudentsAppearsForScoreTestGridTableId").hide();
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown); 
		});
		
	var mediaPath = $('#mediapath').text();
	$.ajax({
		url: 'getScorerStudentResources.htm',
		dataType: 'json',
		type: 'POST',
		data: {
			testSessionId :testsessionId
		}
	}).done(function (resources) { 
		if(resources !== undefined && resources !== null && resources.length > 0){	
			$('#scorerTestResources').empty();
			$.each(resources, function(i, resource) {
				var fileName = resource.fileName;
				var fileType = resource.fileType;
				if(fileType == 'pdf'){
				  $('#scorerTestResources').append('<div style="margin:0px;"><img src="images/pdf.png" style="float:left;height: 25px;width: 25px;" title="'+fileName+'"/><a download="'+fileName+'" href="'+mediaPath+resource.fileLocation+'" style="color:#0e76bc; text-decoration: underline;margin-left:5px;">'+fileName+'</a></div>');
				}else if(fileType == 'csv' || fileType == 'xls'){
					$('#scorerTestResources').append('<div style="margin:0px;"><img src="images/icon-csv-green.png" style="float:left;height: 25px;width: 25px;" title="'+fileName+'"/><a download="'+fileName+'" href="'+mediaPath+resource.fileLocation+'" style="color:#0e76bc; text-decoration: underline;margin-left:5px;" >'+fileName+'</a></div>');	
				} else {
					$('#scorerTestResources').append('<div style="margin:0px;"><a download="'+fileName+'" href="'+mediaPath+resource.fileLocation+'" style="color:#0e76bc; text-decoration: underline;margin-left:5px;">'+fileName+'</a></div>');
				}
				
				
			});				
		}else{
		    $('#scorerTestResources').empty();
		}			
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});

	}
}

function scorerTestStudentScoredGrid(){
	
	var ccqTest="Assignment";
	var grade="Grade";
	var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
	if(userDefaultAssessmentProgram!=null && userDefaultAssessmentProgram!=undefined && userDefaultAssessmentProgram!=""){
		if(userDefaultAssessmentProgram=='CPASS') {
			ccqTest="CCQ Assignment";
			grade="Pathway";
		}
	}
	
	var $gridAuto = $("#scorerTestStudentGridTableId");
	//Unload the grid before each request.
	//$gridAuto.jqGrid('GridUnload');
	var gridWidthForVO = $('#scorerTestStudentGridTableId').parent().width();		
	if(gridWidthForVO < 780) {
		gridWidthForVO = 780;				
	}
	var cellWidthForVO = gridWidthForVO/3;
	
	var cmforTestScorerGrid = [
	    { label: 'scoringAssignmentId',key:true, width:0,name : 'scoringAssignmentId', index : 'scoringAssignmentId', hidden : true,hidedlg : true},
		{ label: 'testsessionId', name : 'id', index : 'id', hidden : true, hidedlg : true},
		{ label: 'scorerId', name : 'scorerId', index : 'scorerId', hidden : true, hidedlg : true},
		{ label: 'testsessionName', name : 'testsessionName', index : 'testsessionName', hidden : true, hidedlg : true},
		{ label: '', name : 'testSessionSelect', index : 'testSessionSelect',width:40,search : false,sortable:false, hidden : true,formatter:scorerTestsSessionRadioButtonFormatter,  hidedlg : true},
		{ label: 'CCQ Test', name : 'ccqTestName', index : 'ccqTestName', width : cellWidthForVO, search : true,sorttype : 'text', hidden : false, hidedlg : true,formatter: escapeHtml},
		{ label: 'Subject', name : 'subject', index : 'subject',sorttype : 'text', width : cellWidthForVO, search : false, hidden : false, hidedlg : true},
		{ label: 'Grade', name : 'grade', index : 'grade',sorttype : 'text', width : cellWidthForVO, search : true, hidden : false, hidedlg : true},
        { label: 'Dates Created', name : 'createdDate', index : 'createdDate', width : 205, sorttype : 'date', search : false, hidden : true, hidedlg : true, formatter: function (cellval, opts) {
	           if(cellval){
			        	var date = new Date(cellval);
   			         opts = $.extend({}, $.jgrid.formatter.date, opts);
   			         return $.fmatter.util.DateFormat("", date, 'm/d/Y', opts);
			           }else{
			        	   return '';
			           }
			     	}},
		{ label: '# of Students', name : 'studentCount', index : 'studentCount', sorttype : 'int', width : 225, search : false, hidden: false, hidedlg: true},
		{ label: '# of Students Scored', name : 'studentsscoredcount', index : 'studentsscoredcount', sorttype : 'int', width : 105, search : false, hidden: false, hidedlg: true},
		{ label: '# of Scorers', name : 'scorercount', index : 'scorercount',sorttype : 'text', search : false, hidden : true, hidedlg : true},
		{ label: 'Testing Program', name : 'testingProgramName', index : 'testingProgramName',sorttype : 'text', hidden : true, hidedlg : true}
		
	];
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO+210,
		/*height:"200px",*/
		colNames : ['scoringAssignmentId','TestsessionId','scorerId','testsessionName','',ccqTest,'Subject', grade,'Date Created', '# of Students', '# of Students Scored' , '# of Scorers' ,'Testing Program'
		           ],
	  	colModel :cmforTestScorerGrid,
		rowNum : 10,
		multiselect:false,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#scorerTestStudentScoredGridPager',
		sortname : 'ccqTestName',
        sortorder: 'asc',
        refresh: false,
		columnChooser : false,
		loadonce: false,
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
	    beforeSelectRow: function (rowid, e) {
	    	
	    	if( $(e.target).closest('tr').find('input[type="radio"]').attr('disabled') == 'disabled')
	    		return false;
	        //$("#scorerTestStudentGridTableId").jqGrid('setSelection', rowid, true);
	        $(e.target).closest('tr').find('input[type="radio"]').attr('checked', 'checked');
	        return true;
	    	
	    	if(scorerTestCheckStudentScorerHide() && scoreTestSelectedTestSessionId[0] != undefined && scoreTestSelectedTestSessionId[0] != null && rowid != scoreTestSelectedTestSessionId[0] ){
	    		$("#confirmDialogSelectClearScorerTest").dialog({
		  		      buttons : {
		  		    	  "Yes" : function() {
		  		    		  $("#scorerTestStudentGridTableId").jqGrid('setSelection', rowid, true);
		  		    		  var radio = $(e.target).closest('tr').find('input[type="radio"]');
		  		              radio.attr('checked', 'checked');
		  		    		 // assignScoringScorersControlEventsInit();
		  		            scorerTestControlEventHide();
		  		    		  $(this).dialog("close");
		  		    	   },
		  		    	   "No": function(){
		  		    		   $(this).dialog("close");  
		  		    	   }
		  		     }
		  	  	});
		  		$("#confirmDialogSelectClearScorerTest").dialog("open");
		  		
		  		e.preventDefault();
		  		return false;
	    	}
	    	else{
	    		//var radio = $(e.target).closest('tr').find('input[type="radio"]');
            //	radio.attr('checked', 'checked');
	    		return true;	    		
	    		}	
	    	
	    	
	    },
	    onSelectRow: function(rowid, status, e){
	    	if( scoreTestSelectedTestSessionId.indexOf(rowid) < 0 ){
	    		scoreTestSelectedTestSessionId = [];
	    		scoreTestSelectedTestSessionId.push(rowid);
    		}
	    	
	    	scoreTestScoringAssignmentId =  $('#scorerTestStudentGridTableId').jqGrid ('getCell', rowid, 'scoringAssignmentId');
	    	scoreTestScoringAssignmentScorerId = $('#scorerTestStudentGridTableId').jqGrid ('getCell', rowid, 'scorerId');
	    	scoreTestStudentId= $('#scorerTestStudentGridTableId').jqGrid ('getCell', rowid, 'scorerId');
	    	
	    	if(!testScoreAllStudentInAllTestSessionScored){
	    		
				scorerTestStudentScrollBarMove=true;
				$('#scorerStudentsAppearsForScoreTestGrid').show();
				var testSessionId = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'id');
				var scoringAssignmentId = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'scoringAssignmentId');
				testSessionIdVal=testSessionId;
				
				$('#scorerStudentsAppearsScoreTestGridContainer').show();
				
				showgridintestscoringtab(scoringAssignmentId,testSessionId);
				
				
				$('#scoreTestAssessmentPrg').empty().append($('select#scorerTestAssessmentPrograms option:selected').html());
				
				
				var subject = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'subject');
				var grade = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'grade');
				$('#scoreTestStudentSubject').empty().append(subject);
				$('#scoreTestStudentSubjectParent').show();
				var ccqTestName = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'ccqTestName');
				var testSessionName = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'testsessionName');
	    		
	    		
				/*var $gridAuto = $("#scorerStudentsAppearsForScoreTestGridTableId");
	    		var studentName = $gridAuto.jqGrid ('getCell', studentId, 'lastName');    		  
	    		studentName += " " + $gridAuto.jqGrid ('getCell', studentId, 'firstName');
	  		    		
	  		    $('#lblScoreTestCriteriaStudntName').empty().append(studentName);*/
				
				//Added for tiny text changes
				
				subjectEventTinyTextChanges($("#scorerTestContentAreas option:selected").text());
				gradeEventTinyTextChanges($("#scorerTestGrades option:selected").text());
				testEventTinyTextChanges(testSessionName);
				
				$('#scoreTestStudentTestSession').empty().append(testSessionName);
				$('.scoreTestStudentCcqTestName').empty().append(ccqTestName);
				//$('#scoreTestStudentCcqStudentName').empty().append();
				//testSessionNameCCQ=ccqTestName;
				//loadNonScoreingReasons();
			}
			else{
				scoringScorersTestInit();
			}
			$('#scoringTestCriteriaTableContainer').hide();
			$('#scoringTestCriteriaTableContainerData').hide();
			scorerTestMoveScrollBar("scorerStudentsAppearsScoreTestGridContainer",5);	
			$('#scorerTestSearchContainer').hide();
	    	
	    }, 
	    onSelectAll: function(rowids, status, e){
	    	
	    	if( status ){
	    		for(var i = 0; i< rowids.length; i++ ){
		    		if( scoreTestSelectedTestSessionId.indexOf(rowids[i]) < 0 )
		    			scoreTestSelectedTestSessionId.push(rowids[i]);
	    		}
	    	}
	    	else{
	    		for(var i = 0; i< rowids.length; i++ ){
		    		if( scoreTestSelectedTestSessionId.indexOf(rowids[i]) >= 0 ){
		    			scoreTestSelectedTestSessionId.splice(scoreTestSelectedTestSessionId.indexOf(rowids[i]), 1 );
		    		}
	    		}
	    	}
	    },
	    beforeRequest: function() {
	    	/*if(!$('#searchTestSessionFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
	    		return false;
	    	} */
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
	    	
	    	//$("#scorerTestStudentScoredNextBtn").prop("disabled",true);
	    	//$('#scorerTestStudentScoredNextBtn').addClass('ui-state-disabled');
	    	
	    	
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');		              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	        var assessmentPrgId =  $('#userDefaultAssessmentProgram').val();
			var subjectId =  $("#scorerTestContentAreas").val();
			var gradeId =  $("#scorerTestGrades").val();
			$(this).setGridParam({postData: {assessmentPrgId :assessmentPrgId,
				subjectId:subjectId,gradeId:gradeId}});
	    },
	    gridComplete: function() {	            		            	
	    	//Need to store the radio button value so that we can set it back,
	    	//because JQGrid does not support automatic selection in memory.
	    	var tableid=$(this).attr('id');       
	        var objs= $( '#gbox_'+tableid).find('[id^=gs_]');;
	        $.each(objs, function(index, value) {
	          var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));
	               $(value).attr('title',$(nm).text()+' filter');
	                        
	               });
	    }	
	});
   	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
   	
   $("#refresh_scorerTestStudentGridTableId").hide();
//   $("#cb_"+$gridAuto[0].id).hide();
}

function scorerStudentsAppearsForScoreTestGrid(colmodel,colname, respopnse){
	var size=3;
	if(colmodel=='') size=2;
	var $gridAuto = $("#scorerStudentsAppearsForScoreTestGridTableId");
	//Unload the grid before each request.
	///$gridAuto.jqGrid('GridUnload');
	var gridWidthForVO = $('#scorerStudentsAppearsForScoreTestGridTableId').parent().width();		
	if(gridWidthForVO < 700) {
		gridWidthForVO = 700;				
	}
	
	var cellWidthForVO = gridWidthForVO/size;

	
	var cmforTestScorerGrid = [        
	    { label: 'id', name : 'id', index : 'id', hidden: true, hidedlg : true, id:'id'},	    
	    { label: 'studentsTestsId', name : 'studentsTestsId', key :true, index : 'studentsTestsId', hidden: true, hidedlg : true},
	    { label: 'scoringStudentId', name : 'scoringStudentId', index : 'scoringStudentId', hidden: true, hidedlg : true},
	    { label: 'TestingProgram', name : 'testingProgramName', index : 'testingProgramName', hidden : true, hidedlg : true},
	    //{ label: '', name : 'testSessionSelect', index : 'testSessionSelect',width:40,search : false,sortable:false, hidden : true,formatter:scorerTestsStudentsAppearsRadioButtonFormatter,  hidedlg : true},
	    { label: 'Last Name', name : 'lastName', index : 'lastName', sorttype : 'int', width : cellWidthForVO, search : false, sortable:false, hidden: true, hidedlg: true},
	   // { label: 'MI', name : 'mi', index : 'mi', width :'100px',  search : false, sortable:false, hidden : true, hidedlg : true},
        { label: 'First Name', name : 'firstName', index : 'firstName', width : cellWidthForVO, search : false, sortable:false, hidden: true, hidedlg: true},
		{ label: 'State Student Id', name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', sorttype : 'text', width : cellWidthForVO, search : false, sortable:false, hidden : true, hidedlg : true},
	    //{ label: 'Grade', name : 'grade', index : 'grade', sorttype : 'text', width : cellWidthForVO, search : false, sortable:false, hidden : true, hidedlg : true},
		{ label: 'testsId', name : 'testsId', index : 'testsId', hidden: true, hidedlg : true},
		//{ label: 'District', name : 'districtName', index : 'districtName', sorttype : 'text', width : cellWidthForVO, search : false, sortable:false, hidden: true, hidedlg : true},
		//{ label: 'School', name : 'schoolName', index : 'schoolName', sorttype : 'text', width : cellWidthForVO, search : false, sortable:false, hidden: true, hidedlg : true},
		//{ label: 'Test Name', name : 'testName', index : 'testName', sorttype : 'text', width : cellWidthForVO, search : false, sortable:false, hidden: true, hidedlg : true},
		{ label: 'Student', name : 'studentNameIdentifier', index : 'studentNameIdentifier', sorttype : 'text', width : cellWidthForVO, search : true, sortable:true, hidden : false, hidedlg : true},
		{ label: 'Scoring Status', name : 'status', index : 'status', sorttype : 'text', width : cellWidthForVO, search : true, sortable:true, hidden : false, hidedlg : true}
	];
	
	var colNames=['id','','','TestingProgram', 'Last Name', 'First Name', 'State Student Id', 'testsId', 'Student', 'Scoring Status'];
	
	if(colmodel!=null && colmodel!=undefined && colname!=null && colname!=undefined){
		if(colmodel.length>0){
			for(var i=0;i<colmodel.length;i++){
				cmforTestScorerGrid.push(colmodel[i]);
			}
		}
		
		if(colname.length>0){
			for(var i=0;i<colname.length;i++){
				colNames.push(colname[i]);
			}
		}
	}
		
	$gridAuto.scb({	
		mtype: "POST",
		datatype: "local",
		data:respopnse.rows,   
		colNames:colNames,
		colModel: cmforTestScorerGrid,  
		height : 'auto',
		width: gridWidthForVO+100,
		shrinkToFit: false,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		page: 1,
		refresh: false,
		loadonce:false,
		gridview: true,
		sortname: 'id',
		sortorder: 'asc',
		columnChooser : false,
		pager: '#scorerStudentsAppearsForScoreTestGridPager',
		viewrecords : true,
		viewable : false,
		emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
		multiselect : false,
	    beforeSelectRow: function (rowid, e) {
	    	var iCol = $.jgrid.getCellIndex(e.target);
	    	
	    	if( $(e.target).closest('tr').find('a').attr('iCol',iCol))	    	
	    	return true;
	    	
	    	if( $(e.target).closest('tr').find('input[type="radio"]').attr('disabled') == 'disabled')
	    		return false;
	    	 //$("#scorerStudentsAppearsForScoreTestGridTableId").jqGrid('setSelection', rowid, true);
	    	 
		     $(e.target).closest('tr').find('input[type="radio"]').attr('checked', 'checked');
		     return true;
	    },
	    onSelectRow: function(rowid, status, e){
	    	
		    	studentTestId =  $('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid('getRowData',rowid).id;
		    	scoreTestStudentId =  $('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid('getRowData',rowid).studentsTestsId;
	    	
	    }, 
	    onSelectAll: function(rowids, status, e){
	    },
	    gridComplete: function() {
			$("#scorerStudentsAppearsForScoreTestGridTableId").closest(".ui-jqgrid").find('.loading').hide();
			$("#lui_scorerStudentsAppearsForScoreTestGridTableId").hide();
			$("#refresh_scorerStudentsAppearsForScoreTestGridTableId").hide();
			$('#scoringTestCriteriaTableContainer').hide();
	    },
	    loadComplete: function() {
	    	
	    	
	    	if(scorerTestStudentScrollBarMove){
	    		//scorerTestMoveScrollBar("scorerStudentsAppearsScoreTestGridContainer",200);
	    		scorerTestStudentScrollBarMove = false;
	    	}
	    	//scorerTestMoveScrollBar("scorerStudentsAppearsScoreTestGridContainer",5);
	    	this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
	    	//Need to store the radio button value so that we can set it back,
	    	//because JQGrid does not support automatic selection in memory.
	    	
	    	var studentsTestsIds = $('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid('getDataIDs');	    	
	    	if(studentsTestsIds.length>0){
	    		for(var i=0;i<studentsTestsIds.length;i++){	   	    	
	   	    	 $(".rowIndex_"+studentsTestsIds[i]).each(function(colCount) {	   	    		
		    		  var taskvariantid = $(this).attr("taskvariantid");	
		    		  var tasksvariants = document.getElementsByClassName("rowIndexVariant_"+taskvariantid);
		    		  if(tasksvariants.length>0){
		    		    for (var i = 0; i < tasksvariants.length; i++) {
		    		    	var count = parseInt(i)+1;
		    		        tasksvariants[i].setAttribute("rowId", "rowPosition_"+taskvariantid+"_"+count);
		    		        tasksvariants[i].classList.add("rowPosition_"+taskvariantid+"_"+count);
		    		  	}
		    		  }			 
		    	 });
	   	    	 
	   	    	}
	    	}	
	    	if(studentsTestsIds.length>0){
	    		var scoredItemCount = 0;
	    		for(var i=0;i<studentsTestsIds.length;i++){
	    			scoredItemCount = 0;
	    			$(".rowScoredLabel_"+studentsTestsIds[i]).each(function(colCount) {
	    				scoredItemCount++; 
	    			 });	    			
	    			if(scoredItemCount == 0){
	    				$('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid('setCell', studentsTestsIds[i], 'status', 'Not Scored');
	    			}
	    			
	   	    	 $(".rowIndex_"+studentsTestsIds[i]).each(function(colCount) {
	   	    	 colCount = parseInt(colCount)+1;	
	    		 $(this).attr("colId", "colPosition_"+studentsTestsIds[i]+"_"+colCount);
	    		 $(this).addClass("colPosition_"+studentsTestsIds[i]+"_"+colCount);
	   	    	 });	   	    	 
	   	    	}
	    	}	
	    	var tableid=$(this).attr('id');       
	        var objs= $( '#gbox_'+tableid).find('[id^=gs_]');;
	        $.each(objs, function(index, value) {
	          var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));
	               $(value).attr('title',$(nm).text()+' filter');
	                        
	               });
		}   
	});
	if(colmodel.length<4){
	width = $gridAuto.jqGrid('getGridParam','width');
	$gridAuto.jqGrid('setGridWidth', width, true);	
	}
	$gridAuto.jqGrid('filterToolbar', { stringResult: true, searchOnEnter: false, defaultSearch: "cn" });
	$gridAuto.jqGrid('navGrid', '#scorerStudentsAppearsForScoreTestGridPager', {edit: false, add: false, del: false, search: false, refresh: false});
 }


function linkFormatter( cellvalue, options, rowObject ){
	var colName = options.colModel.name;
	if(cellvalue!=null && cellvalue!='' && cellvalue!=undefined){
		var variantValue = cellvalue.split('_');
		var variantStatus =  variantValue[1];
		var testletId=variantValue[2];
		var havingStumulus=variantValue[3];
		var studentName = rowObject.firstName;  
		studentName += " " + rowObject.lastName;
		if(variantStatus=='ReadyToScore'){		
				return '<a href="#" class="rowIndexVariant_'+variantValue[0]+' rowIndex_'+rowObject.studentsTestsId+' row_uniq_'+rowObject.studentsTestsId+'_'+variantValue[0]+'" colName="'+colName+'" studentstestsid ="'+rowObject.studentsTestsId+'" status="'+variantStatus+'" testsId="'+rowObject.testsId+'" taskVariantId = "'+variantValue[0]+'" onclick="scoreTestLoadScore('+'\'' +variantValue[0]+ '\''+','+rowObject.testsId+','+rowObject.id+','+rowObject.studentsTestsId+','+'\'' +colName+'\'' +','+'\'' +testletId+ '\'' +','+'\''+havingStumulus+'\');" ><span class="rowIndexScored_'+rowObject.studentsTestsId+'" data-id="'+variantValue[0]+'" ><img title="'+studentName+'" src="images/icons/Ready_to_score.png" height="20px" /></span></a>';
			}
			else if(variantStatus=='Scored'){
				if(isEditScore) return '<span class="rowIndexScored_'+rowObject.studentsTestsId+' rowScoredLabel_'+rowObject.studentsTestsId+'" data-id="'+variantValue[0]+'" ><img title="'+studentName+'" src="images/icons/Scored.png" height="20px" style="display: inline-block;cursor:default;" >&nbsp;&nbsp;<a href="#" class="rowIndexVariant_'+variantValue[0]+' rowIndex_'+rowObject.studentsTestsId+' row_uniq_'+rowObject.studentsTestsId+'_'+variantValue[0]+'" colName="'+colName+'" studentstestsid ="'+rowObject.studentsTestsId+'" status="'+variantStatus+'" testsId="'+rowObject.testsId+'" taskVariantId = "'+variantValue[0]+'" onclick="scoreTestLoadScore('+'\'' +variantValue[0]+ '\''+','+rowObject.testsId+','+rowObject.id+','+rowObject.studentsTestsId+','+'\'' +colName+'\'' +','+'\'' +testletId+ '\'' +','+'\''+havingStumulus+'\');" ><span class="rowIndexScored_'+rowObject.studentsTestsId+'" data-id="'+variantValue[0]+'" ><img title="'+studentName+'" src="images/icons/Edit_score.png" width="20px" height="20px" style="display: inline-block;" /></a></span>';				
				else return '<span class="rowIndexScored_'+rowObject.studentsTestsId+' rowScoredLabel_'+rowObject.studentsTestsId+'" data-id="'+variantValue[0]+'" ><img  title="'+studentName+'" src="images/icons/Scored.png" height="20px" ></span>';
			}
			else if(variantStatus=='NoResponse'){
				return '<a href="#" class="rowIndexVariant_'+variantValue[0]+' rowIndex_'+rowObject.studentsTestsId+' row_uniq_'+rowObject.studentsTestsId+'_'+variantValue[0]+'" colName="'+colName+'" studentstestsid ="'+rowObject.studentsTestsId+'" status="'+variantStatus+'" testsId="'+rowObject.testsId+'" taskVariantId = "'+variantValue[0]+'" onclick="scoreTestLoadScore('+'\'' +variantValue[0]+ '\''+','+rowObject.testsId+','+rowObject.id+','+rowObject.studentsTestsId+','+'\'' +colName+'\''+','+'\'' +testletId+ '\'' +','+'\''+havingStumulus+'\');" ><span class="rowIndexScored_'+rowObject.studentsTestsId+'" data-id="'+variantValue[0]+'" ><img  title="'+studentName+'" src="images/icons/No_answer.png" style="padding-top: 1px;display: inline-block;"  height="20px" title="Scoring Required"/></span></br></a>';
			}
			else if(variantStatus=='N/A')
			{
				return "N/A";
			}
	    }				
}
    
function scoreTestLoadScore(taskVariantIds,testsId,studentId,studentsTestsId,colName,testletId,havingStumulus){

	$("#scoringTestCriteriaTable").hide();
	var taskVariants = taskVariantIds.split("-");
	taskVariantId =taskVariants[0]; 
	scoreTestStudentId = studentId;		
	var rowId = document.getElementsByClassName("row_uniq_"+studentsTestsId+"_"+taskVariantIds)[0].getAttribute("rowid");
	var colId = document.getElementsByClassName("row_uniq_"+studentsTestsId+"_"+taskVariantIds)[0].getAttribute("colid");
   	if(testletId!=='un'){
   		$("#stimulusviewid").show();
   		$("#stimulusviewlinkid").attr("value",testletId);
   		$("#stimulusviewlinkid").attr("taskVariantId",'');
   	}else if(havingStumulus=='True'){
   			$("#stimulusviewid").show();
   	   		$("#stimulusviewlinkid").attr("taskVariantId",taskVariantId);
   	   	$("#stimulusviewlinkid").attr("value",'');
   		}
   	else
   		{
   		$("#stimulusviewid").hide();
   		}
	$("#continueScoringStudentSubmitScoreBtn").attr("taskvariantid",taskVariantId);
	$("#continueScoringStudentSubmitScoreBtn").attr("studentsTestsId",studentsTestsId);
	$("#continueScoringStudentSubmitScoreBtn").attr("rowid",rowId);
	$("#continueScoringStudentSubmitScoreBtn").attr("taskVariantIds",taskVariantIds);
	
	$("#continueScoringItemSubmitScoreBtn").attr("taskvariantid",taskVariantId);
	$("#continueScoringItemSubmitScoreBtn").attr("studentsTestsId",studentsTestsId);
	$("#continueScoringItemSubmitScoreBtn").attr("colid",colId);	
	
	$("#scorerTestSubmitScoreBtn").prop("disabled",true);
	$('#scorerTestSubmitScoreBtn').addClass('ui-state-disabled');
	$('#nonScoreReasons').prop('disabled', false);
	$("#continueScoringQuitScoringBtns").hide();
	$("#scoringTestCriteriaTableContainerData").hide();
		
	var  testSessionId= $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'id');
	$.ajax({
		url: 'getStudentTestScoringCriteria.htm',
        data: {
        	testSessionId: testSessionId,
        	studentId:studentId,
        	testsId:testsId,
        	variantValue:taskVariantId
        	},
        dataType: 'json',
        type: "GET"
	}).done(function (scoringCriterias) { 
		$('#scoringTestCriteriaTableContainer').show();
		$('#lblScoreTestCriteriaSubjectP').show();
		
		var $gridAuto = $("#scorerStudentsAppearsForScoreTestGridTableId");
		var studentName = $gridAuto.jqGrid ('getCell', studentsTestsId, 'lastName');  
		var studentNameScorer = $gridAuto.jqGrid ('getCell', studentsTestsId, 'lastName');
		studentNameScorer += " " + $gridAuto.jqGrid ('getCell', studentsTestsId, 'firstName');
		    studentName += ", " + $gridAuto.jqGrid ('getCell', studentsTestsId, 'firstName');  		  
		    
		    
		$('#scoreTestStudentCcqStudentName').empty().append(studentNameScorer);
		$('#scoreTestStudentCcqQuestions').empty().append(colName);
		
		scorerTestMoveScrollBar("scoringTestCriteriaTableContainer",5);
	   	scoreTestPopulateScoreCriteria(scoringCriterias);
	    loadNonScoreingReasons(taskVariantIds,studentsTestsId);
	      	    
	    if(scoringCriterias.length>0){
	     $('#nonScoreReasonDiv').show();
	     $("#scorerTestSubmitScoreBtn").show();    
	     }
	    else{
	     $('#rubricContentDiv').html('');
	     $('#nonScoreReasonDiv').hide();
	     $("#scorerTestSubmitScoreBtn").hide();
	    }
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});

	
	$('#scorerStudentsAppearsScoreTestGridContainer').hide();	
	$('#scoringTestCriteriaTableContainer').show();	
	
	
	//Added for tiny text changes
	var $gridAuto = $("#scorerStudentsAppearsForScoreTestGridTableId");
	var studentName = $gridAuto.jqGrid ('getCell', studentsTestsId, 'lastName');  
    studentName += ", " + $gridAuto.jqGrid ('getCell', studentsTestsId, 'firstName'); 
	var stateStudentIdentifier = $gridAuto.jqGrid ('getCell', studentsTestsId, 'stateStudentIdentifier');
	studentNameEventTinyTextChanges(studentName);
	stateStudentIdEventTinyTextChanges(stateStudentIdentifier);
	itemQuestionEventTinyTextChanges(colName);
	
	
	loadStudentQuestionResponse(taskVariantIds,studentId,studentsTestsId);
}

function activeRubricTab(evt, rubricTabName) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("rubrictabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("rubrictablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(rubricTabName).style.display = "block";
    evt.currentTarget.className += " active";
}

function scoreTestPopulateScoreCriteria(scoringCriterias){
	
	if(scoringCriterias.length == 0){
		$('#scoringTestCriteriaTable tr').slice(1).remove();
		return ;
	}
	var noOfColumns = 0 ;
	var tableWidth = 130;
	$.each(scoringCriterias, function(i) {
			if(scoringCriterias[0].rubricCatergoryId == scoringCriterias[i].rubricCatergoryId){
				noOfColumns++;
				tableWidth = tableWidth+200;
			}		
			
	});
	
	if(noOfColumns<4)  tableWidth=800;
	
	//var tableWidth = noOfColumns*250;
	//console.log("tableWidth  "+tableWidth);
	var prevScoringCriteria = null;
	var isHeader = true;
	var headerHtml ='<tr class="score">';
	var contetnHtml ='<tr class="catgeoryScore" name="'+ scoringCriterias[0].rubricCatergoryId+'"  value="' + scoringCriterias[0].rubricCatergoryId + '" id="' + scoringCriterias[0].rubricCatergoryId + '_scoringTestCriteriaTable">';
	var isRowHeader = true;
	$.each(scoringCriterias, function(i, scoringCriteria) {
			
			if(prevScoringCriteria == null || scoringCriteria.rubricCatergoryId == prevScoringCriteria.rubricCatergoryId  ){
				if( isHeader ){
					if(scoringCriteria.rubricType =='Holistic'){
						headerHtml += scoreTestScoringColHeaderHtmlHolistic(scoringCriteria.score,scoringCriteria.rubricCatergoryId);
					}else{
					headerHtml += scoreTestScoringColHeaderHtml(scoringCriteria.score);
					}
				}
				isRowHeader = false;
			}
			else{
				if(isHeader){
					headerHtml += scoreTestScoringColHeaderHtml("Score");
					headerHtml += '</tr>';
					isHeader = false;
				}
				
				contetnHtml += scoreTestScoringCriteriaRowTotalHtml(prevScoringCriteria.rubricCatergoryId);
				if(scoringCriteria.lastRow){
					contetnHtml += '</tr><tr id="'+scoringCriteria.rubricCatergoryId+'">';
				 }else{
					 contetnHtml += '</tr><tr class="catgeoryScore" name="'+scoringCriteria.rubricCatergoryId+'" value="'+scoringCriteria.rubricCatergoryId+'" id="'+scoringCriteria.rubricCatergoryId+'_scoringTestCriteriaTable">';
				 }
				
				isRowHeader = true;
			}
			if( isRowHeader || prevScoringCriteria == null ){
				 if(scoringCriteria.lastRow){
					 contetnHtml +=  '<td hidden style="width:70">' + scoringCriteria.rubricCatName + '</th>';
					 //contetnHtml +=  '<th class = "firstColumn" hidden style="width:70">' + scoringCriteria.rubricCatName + '</th>';
				 }else{
				contetnHtml += scoreTestScoringRowHeaderHtml(  scoringCriteria.rubricCatName);
				 }
			}
			 if(scoringCriteria.lastRow){
				 contetnHtml += scoreTesteScoringCriteriaRuricInfoHtmlLastRow (scoringCriteria);
			 }
			 else if(scoringCriteria.rubricType == 'Holistic'){
				contetnHtml += scoreTesteScoringCriteriaRuricInfoHtmlHolistic(scoringCriteria);
			}
			else{
				contetnHtml += scoreTesteScoringCriteriaRuricInfoHtml(scoringCriteria);
			}
			
			if(scoringCriterias.length == (i+1) ){
				
					 contetnHtml += '<td class="scoreTestScoringTotalLast" hidden style="width:60" ><div id="rowsco_Last" style="margin-top:12px;text-align:center;"> </div></td>';
				
				contetnHtml += '</tr>';
			}
			prevScoringCriteria = scoringCriteria;
	});
	if(isHeader){
		headerHtml += scoreTestScoringColHeaderHtml("Score");
		headerHtml += '</tr>';
	}	
	$('#scoringTestCriteriaTable tr').slice(1).remove();
	$('#scoringTestCriteriaTable').append(headerHtml).append(contetnHtml);
	 $('#scoringTestCriteriaTable').attr('width', tableWidth);
	
	var ulContenthtml='<ul id="rubrictabs">';
	var contetnHtml ='';	
	var isRowHeader = true;
	var isHolistic=false;
	$.each(scoringCriterias, function(i, scoringCriteria) {	
		isHolistic=scoringCriteria.rubricType == 'Holistic'? true:false;
		if(!scoringCriteria.lastRow){		
			var firstrow = "";		
			if(i==0) firstrow = "active";	
			
			if(prevScoringCriteria == null || scoringCriteria.rubricCatergoryId == prevScoringCriteria.rubricCatergoryId  ){
				isRowHeader = false;				
				if(i==0){
					contetnHtml += '<div id="rubricContent_'+scoringCriteria.rubricCatergoryId+'" class="rubrictabcontent '+firstrow+'" ><table class="rubricContentTable" role="presentation" ><tr class="catgeoryScore" name="'+scoringCriteria.rubricCatergoryId+'" value="'+scoringCriteria.rubricCatergoryId+'" id="'+scoringCriteria.rubricCatergoryId+'_rubricContentTable">';
					contetnHtml+='<td style="text-align:center;width:50px;vertical-align: text-top;background-color: #EEEEEE;">'+
					'<p style="margin-top:0px;margin-bottom:-15px;vertical-align: text-top;background: #CCCCCC;height:30px;">Scoring Criteria</p></td>';
				}
			}
			else{	
				if(i>0) contetnHtml +='<td style="background-color: #EEEEEE;vertical-align: text-top;border-left: 1px solid #000;"><p style="margin-top:0px;background: #CCCCCC;height:30px;">Total Score</p><span style="display:none;" class="rowScore rowScore_rub" name="rowsco_'+prevScoringCriteria.rubricCatergoryId+'" id="rowsco_'+prevScoringCriteria.rubricCatergoryId+'"  > </span><div style="text-align: center;height:max-content;" class ="rowsco_Last" ></div></td>';
				contetnHtml += '</tr></table>';
				contetnHtml += '</div><div id="rubricContent_'+scoringCriteria.rubricCatergoryId+'" class="rubrictabcontent '+firstrow+'" ><table class="rubricContentTable" role="presentation"  ><tr class="catgeoryScore" name="'+scoringCriteria.rubricCatergoryId+'" value="'+scoringCriteria.rubricCatergoryId+'" id="'+scoringCriteria.rubricCatergoryId+'_rubricContentTable">';
				isRowHeader = true;
			}	
			
			if( (isRowHeader || prevScoringCriteria == null) && scoringCriteria.rubricCatergoryId>0 ){
				contetnHtml+='<td style="text-align:center;width:50px;vertical-align: text-top;background-color: #EEEEEE;">'+
					'<p style="margin-top:0px;margin-bottom:-15px;vertical-align: text-top;background: #CCCCCC;height:30px;">Scoring Criteria</p></td>';
			}
			
			if(prevScoringCriteria==null){
				contetnHtml+='<td style="text-align:center;width:50px;vertical-align: text-top;background-color: #EEEEEE;">'+
				'<p style="margin-top:0px;margin-bottom:-15px;vertical-align: text-top;background: #CCCCCC;height:30px;">Scoring Criteria</p></td>';
			}
			
			contetnHtml += scoreTestDivScoringCriteriaRuricInfoHtml(scoringCriteria);
			
			if(scoringCriterias.length == (i+1) ){			
					contetnHtml += '<td style="background-color: #EEEEEE;vertical-align: text-top;border-left: 1px solid #000;"><p style="margin-top:0px;background: #CCCCCC;height:30px;">Total Score</p><span style="display:none;" class="rowScore rowScore_rub" name="rowsco_'+prevScoringCriteria.rubricCatergoryId+'" id="rowsco_'+prevScoringCriteria.rubricCatergoryId+'"  > </span><div style="text-align: center;height:max-content;" class ="rowsco_Last" ></div></td></tr></table></div>';
					contetnHtml +="</tr></table></div>";
			}				
			
			if((isRowHeader || prevScoringCriteria == null) && scoringCriteria.rubricCatergoryId>0){
				ulContenthtml += scoreTestScoringRowHeaderLiHtml(scoringCriteria, firstrow);
			}
			else if(i==0){
				ulContenthtml += scoreTestScoringRowHeaderLiHtml(scoringCriteria, firstrow);
			}
						
			prevScoringCriteria = scoringCriteria;
		}	
		
	});	
	if(!isHolistic){
	$('#expandedRubricId').show();
	}
	ulContenthtml+= "</ul>";	
	$('#rubricContentDiv').html('').append(ulContenthtml).append(contetnHtml);
	$("#rubrictabs li").css("max-width", 100/$(".rubrictablinks").length+'%');
	
	
	$('.cellScoreClick').off('click').on('click',function(){
		var id = this.id;
		id = id.replace('cellScoreClick_','');
		var checked=$("#scoreRadio_"+id).prop('checked');
		if(!checked){
			$("#scoreRadio_"+id).attr('checked', 'checked').trigger('click');
		}		
    });	
	
	$('input:radio[name^=rc_]').off('click').on('click',function(){
		var name = $(this).attr('name');
		name = name.replace('rc_','');
		var checked=$(this).prop('checked');
		var doublechecked=false;
		var thisRowValue=$('#rowsco_'+ name).val();
		var totalScore=$('.rowsco_Last').val();
		if( $('#rowsco_'+ name).html()!=''){
		var latestTotalScore=Number(totalScore) - Number(thisRowValue); 
		$('.rowsco_Last').val(latestTotalScore);
		$('.rowsco_Last').html(latestTotalScore);
		}
		
		if($('#rowsco_'+ name).val()==$(this).val() && checked && $('#rowsco_'+ name).html()!=''){
			$(this).prop('checked', false);
			$('#rowsco_'+ name).val($(this).val());
			$('#rowsco_'+ name).html('');
			doublechecked=true;
		}
		if(!doublechecked){
			$(this).prop('checked', true);
			$('#rowsco_'+ name).val($(this).val());
			$('#rowsco_'+ name).html($(this).val());
			totalScore=$('.rowsco_Last').val();
		var thisValue=$(this).val();
		 latestTotalScore=Number(thisValue) + Number(totalScore);
		$('.rowsco_Last').val(latestTotalScore);
		$('.rowsco_Last').html(latestTotalScore);
		}
		var totalRubricCategory = $('#rubrictabs li').length; // $('input:radio[name^=rc_]');
		var scoredRubric = $('input:radio[name^=rc_]:checked');
		var nonScoreReasonValue = $('#nonScoreReasons').val();
		
		if( totalRubricCategory == scoredRubric.length ){
			$("#scorerTestSubmitScoreBtn").prop("disabled",false);
			$('#scorerTestSubmitScoreBtn').removeClass('ui-state-disabled');			
		}
		else{
			if(nonScoreReasonValue != null && nonScoreReasonValue.length > 0){
				if(totalRubricCategory != scoredRubric.length){
					$("#scorerTestSubmitScoreBtn").prop("disabled",true);
					$('#scorerTestSubmitScoreBtn').addClass('ui-state-disabled');
				}else{alert("..else");
					$("#scorerTestSubmitScoreBtn").prop("disabled",false);
					$('#scorerTestSubmitScoreBtn').removeClass('ui-state-disabled');	
				}			
			}
			else{
				$("#scorerTestSubmitScoreBtn").prop("disabled",true);
				$('#scorerTestSubmitScoreBtn').addClass('ui-state-disabled');
			}			
		}
		var totalScore = $('.rowsco_Last').val();
		var nonScoreReason = [ "BL", "IS", "OT", "RNE" ];
		
		if(totalScore!=null && totalScore!=undefined && totalScore!='' && totalScore>0){
			$("#nonScoreReasons").select2('destroy');
			$.each(nonScoreReason, function(i) {
			    $('#nonScoreReasons option[code=' + nonScoreReason[i] + ']').prop('disabled', true);
			});
			$("#nonScoreReasons").select2();
			if($("#nonScoreReasons option:selected").attr("code")!='HSO') $("#nonScoreReasons").val("").trigger('change.select2');
		}else if(totalScore!=null && totalScore!=undefined && totalScore!='' && totalScore==0){
			$("#nonScoreReasons").select2('destroy');
			$.each(nonScoreReason, function(i) {
			    $('#nonScoreReasons option[code=' + nonScoreReason[i] + ']').prop('disabled', false);
			});
			$("#nonScoreReasons").select2();
			$("#nonScoreReasons").val("").trigger('change.select2');
		}
		
		
	});
	
	$('input:radio[name^=rc_h]').off('click').on('click',function(){
		var name = $(this).attr('name');
		name = name.replace('rc_h','');
		var checked=$(this).prop('checked');
		var doublechecked=false;
		var thisRowValue=$('#rowsco_'+ name).val();
		var totalScore=$('.rowsco_Last').val();
		if( $('#rowsco_'+ name).html()!=''){
		var latestTotalScore=Number(totalScore) - Number(thisRowValue); 
		$('.rowsco_Last').val(latestTotalScore);
		$('.rowsco_Last').html(latestTotalScore);
		}
		
		if($('#rowsco_'+ name).val()==$(this).val() && checked && $('#rowsco_'+ name).html()!=''){
			$(this).prop('checked', false);
			$('#rowsco_'+ name).val(0);
			$('#rowsco_'+ name).html('');
			doublechecked=true;
		}
		if(!doublechecked){
			$(this).prop('checked', true);
			$('#rowsco_'+ name).val($(this).val());
			$('#rowsco_'+ name).html($(this).val());
			totalScore=$('.rowsco_Last').val();
		var thisValue=$(this).val();
		 latestTotalScore=Number(thisValue) + Number(totalScore);
		$('.rowsco_Last').val(latestTotalScore);
		$('.rowsco_Last').html(latestTotalScore);
		}
		
		var scoredRubric = $('input:radio[name^=rc_h]:checked');
		var rubricCatgeries = [];		
		var i=0;
		$('.catgeoryScore').each(function() {
			rubricCatgeries[i++] = $(this).attr("name");
		});
		if(!doublechecked){
		applyScoreForHolstic(rubricCatgeries,$(this).val(),$(this).prop('checked'));
		}else{
			applyScoreForHolstic(rubricCatgeries,0,$(this).prop('checked'));
		}
		if(  scoredRubric.length>0 ){
			$("#scorerTestSubmitScoreBtn").prop("disabled",false);
			$('#scorerTestSubmitScoreBtn').removeClass('ui-state-disabled');
		}
		else{
			$("#scorerTestSubmitScoreBtn").prop("disabled",true);
			$('#scorerTestSubmitScoreBtn').addClass('ui-state-disabled');
		}
	});
	
}

function scoreTestScoringRowHeaderLiHtml(scoringCriteria,firstrow){	
	return '<li><a tabindex = "0" class="rubrictablinks '+firstrow+'" title="'+scoringCriteria.rubricCatName+'" onclick="activeRubricTab(event, '+'\'' +'rubricContent_'+scoringCriteria.rubricCatergoryId+ '\')" OnKeyPress="activeRubricTab(event, '+'\'' +'rubricContent_'+scoringCriteria.rubricCatergoryId+ '\')"  >' + scoringCriteria.rubricCatName + '</a></li>';
}

function applyScoreForHolstic(rubricCatgeries,val,isShown){
	var totalcount=0;
	$.each(rubricCatgeries, function(i, rubricCatgerie) {
		$('#rowsco_'+rubricCatgerie).val(val);
		$('#rowsco_'+rubricCatgerie).html(val);
		totalcount=Number(totalcount)+ Number(val);
		if(!isShown && totalcount==0){
			$('#rowsco_'+rubricCatgerie).html('');	
		}
	});
	$('.rowsco_Last').val(totalcount);
	$('.rowsco_Last').html(totalcount);
	if(!isShown && totalcount==0){
		$('.rowsco_Last').html('');	
	}
	
}
function scoreTestScoringColHeaderHtml(score){
	 
	 if(score == "Score"){
	  return '<td class = "lastColumn" hidden style="padding:10px;width:60"><div  align="center">' + score + '</div></td>'; //width="15%"
	 }else{
	  return '<td style="padding:10px;width:200" ><div  align="center">' + score + '</div></td>'; //width="15%"
	 }
	}
	function scoreTestScoringColHeaderHtmlHolistic(score,rubricCatergoryId){ //width="15%"
	 return '<td style="padding:10px;width:200" class="scoretestInnertd"><div  align="center">' + score + '</div></td>';
	}

	function scoreTestDivScoringCriteriaRuricInfoHtml(scoringCriteria){
	 var score = scoringCriteria.score;
	 var rubricScoreWeight = scoringCriteria.rubricScoreWeight;
	 score=parseFloat(score)*parseFloat(rubricScoreWeight);
	 return '<td style="width:200;background-color: #EEEEEE;cursor: pointer;" class="scoretestInnertd rubricContentTd cellScoreClick" id="cellScoreClick_'+ scoringCriteria.rubricCatergoryId +'_'+score+'" > '+
	     '<p style="margin-top:0px;margin-bottom:-15px;line-height: 33px;background: #CCCCCC;height:30px;">'+ scoringCriteria.score +'&nbsp;&nbsp;<input type="radio" name="rc_'+ scoringCriteria.rubricCatergoryId +'" value="'+ score +'" id="scoreRadio_'+ scoringCriteria.rubricCatergoryId +'_'+score+'" class="scoreRadio" title="'+scoringCriteria.score+'" /></p>'+
	     '<div style="height:max-content;" ><p style="text-align:left;margin-top: 25px;">' + scoringCriteria.rubricInfoDesc + '</p></div>'+
	     '</td>';
	}

	function scoreTesteScoringCriteriaRuricInfoHtml(scoringCriteria){
	 var score = scoringCriteria.score;
	 var rubricScoreWeight = scoringCriteria.rubricScoreWeight;
	 score=parseFloat(score)*parseFloat(rubricScoreWeight);
	 return '<td style="width:200;vertical-align: middle;" class="scoretestInnertd rubricContentTd"> '+
	     '<p style="margin-top:2px;margin-bottom:-15px;background: #eee;"></p>'+
	     '<p style="text-align:left;">' + scoringCriteria.rubricInfoDesc + '</p>'+
	     '</td>';
	}

	function scoreTesteScoringCriteriaRuricInfoHtmlHolistic(scoringCriteria){
	 return '<td style="width:200" class="scoretestInnertd rubricContentTd"> '+
	     '<p style="text-align:left;padding:0px 3px;font-size:12px;">' + scoringCriteria.rubricInfoDesc + '</p>'+
	     '</td>';
	}
	function scoreTesteScoringCriteriaRuricInfoHtmlLastRow(scoringCriteria){
	 return '<td hidden> '+
	     '<p style="text-align:left">' + scoringCriteria.rubricInfoDesc + '</p>'+
	     '</td>';
	}
	function scoreTestScoringRowHeaderHtml(criteria){
	 return '<th class = "firstColumn" style="padding:10px; width:70;vertical-align: middle;">' + criteria + '</th>';
	}
	function scoreTestScoringCriteriaRowTotalHtml(catId){
	 return '<td class="scoreTestScoringTotal" hidden style="width:60"><div class="rowScore" name="rowsco_'+catId+'" id="rowsco_view_'+catId+'"  > </div></td>'; //style="width:100px !mpotant;"  
	}
function saveScoringScoreTest(){
	
	var rubricCategoryIdsScores = [];
	var i=0;
/*	$('input:radio[name^=rc_]:checked').each(function() {
		rubricCategoryIdsScores[i++] = $(this).attr("name").replace("rc_","")+"#"+$(this).val();
	});*/
	$('.rowScore_rub').each(function() {
		rubricCategoryIdsScores[i++] = $(this).attr("name").replace("rowsco_","")+"#"+$(this).text();
	});
	var selectedRowStudentId =$("#selectedRowStudentId").val();
	var scoringAssignmentStudentId = $('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid ('getCell', selectedRowStudentId, 'scoringStudentId');
	var scoringStatus = $('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid ('getCell',selectedRowStudentId, 'status');
	var testId = $('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid ('getCell',selectedRowStudentId, 'testsId');
	var scoringAssignmentId = scoreTestScoringAssignmentId;
	
	var scorerId = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'scorerId');
	
	var noOfStudents = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'studentCount');
	var noOfStudentsScored = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'studentsscoredcount');
	
	var nonScoringReason =  $('#nonScoreReasons').val();
	var taskVariantIds =$("#taskVariantIdScoringReason").val();
	var taskVariants = taskVariantIds.split("-");
	var isScored = true;
	var m=0;
	$('.rowIndexScored_'+selectedRowStudentId).each(function() {		
		var htmlcont = $(this).text();
		var taskVarId = $(this).attr("data-id");
		if(htmlcont!=null && htmlcont!=undefined && htmlcont!='' && taskVariantIds!=taskVarId){
			htmlcont=htmlcont.trim();
			if(htmlcont!="Scored"){
				isScored = false;
		 }
		}	
		m++;
    });	
	if(m==0) isScored=false;
	$.ajax({
		url: 'createScoringScoreTest.htm',
        data: {
        	scoringAssignmentId: scoringAssignmentId,
        	scoringAssignmentStudentId: scoringAssignmentStudentId,
        	scoringAssignmentScorerId:scorerId,
        	rubricCategoryIdsScores:rubricCategoryIdsScores,
        	nonScoringReason:nonScoringReason,
        	taskVariantId:taskVariants,
        	isScored:isScored,
        	scoringStatus:scoringStatus,
        	studentsTestId:selectedRowStudentId,
        	testId:testId
        },
        dataType: 'json',
        type: "POST"
	}).done(function (result) { 
		if( result.result == "success"){
    		testScoreTestSavedSuccessMessage(selectedRowStudentId,scoringAssignmentStudentId);
    		if( result.alltestsession == "COMPLETED"){
    			if(noOfStudents == noOfStudentsScored){
    				testScoreAllTestsessionCompletedConfirmation();
    			}
    			
    		}else if( result.currenttestsession == "COMPLETED"){
    			testScoreNextTestSessionConfirmationShow();
    		}
    		else {
    			testScoreNextStudentConfirmationShow();
    		}
    		
    		var col = $("#continueScoringItemSubmitScoreBtn").attr("colid");
    	    var row = $("#continueScoringStudentSubmitScoreBtn").attr("rowid");
    		var studentstestsid = $("#continueScoringItemSubmitScoreBtn").attr("studentstestsid");
    		var taskvariantids = $("#continueScoringStudentSubmitScoreBtn").attr("taskvariantids");
    		$("."+col).attr("status","Scored");
    		$("."+col).removeClass("rowIndex_"+studentstestsid);
    		$("."+row).attr("status","Scored");
    		$("."+row).removeClass("rowIndexVariant_"+taskvariantids);
   		
    		$("#continueScoringQuitScoringBtns").show();
    	}
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});

}

function scorerTestsSessionRadioButtonFormatter(cellvalue, options, rowObject){
	 var htmlString = "";
	 var studentScored = rowObject.studentsscoredcount;
	 var studentCount = rowObject.studentCount;
	 var disabled = '' ;
	 var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
	 if(userDefaultAssessmentProgram=='KELPA2'){
	   htmlString = '<input type="radio"  id="scoTstSes_' + options.rowId + '" name="scorerTestsselectedTestsession" value=""/>';
	  }else{
	   htmlString = '<input type="radio" '+ disabled + ' id="scoTstSes_' + options.rowId + '" name="scorerTestsselectedTestsession" value=""/>';
	  }
	    return htmlString;
	}
	
$('[name=scorerTestsselectedTestsession]').on('click', function (e){
	  var rid = $(this).attr('id');
	  rid = rid.replace("scoTstSes_","");
	  $('#scorerTestStudentGridTableId').jqGrid('setSelection', rid);
});

function scorerTestsStudentsAppearsRadioButtonFormatter(cellvalue, options, rowObject){
	var htmlString = "";
	var studentScored = rowObject.status.toUpperCase() == "COMPLETED" ? ' disabled="disabled" title="Student already scored." ' :'' ;
	htmlString = '<input type="hidden" ' + studentScored + ' id="std_' + options.rowId + '" name="scorerTestsStudentsAppearsTestsession" value=""/>';
    return htmlString;
}

$('[name=scorerTestsStudentsAppearsTestsession]').on('click', function (e){
	var rid = $(this).attr('id');
	  rid = rid.replace("std_","");
		$('#scorerStudentsAppearsForScoreTestGridTableId').jqGrid('setSelection', rid);
});

function scoreTestAllTestSessionStudentsScored(){
	testScoreAllStudentInAllTestSessionScored = true;
	
}
function soreTestMoveToNextTestSession(){
	testScoreStudentHide();
	scoreTestLoadTestSessions();
	scorerTestMoveScrollBar(270);
}
function soreTestMoveToNextStudent(){
	testScoreTestCriteriaHide();	
	showgridintestscoringtab();
	scorerTestStudentScrollBarMove=true;
	
}
function scorerTestCheckStudentScorerHide(){
	if($('#scorerStudentsAppearsScoreTestGridContainer').is(":visible"))
		return true;
	
	return false;
}

function scorerTestControlEventHide(){
	$('#scorerStudentsAppearsScoreTestGridContainer').hide();
	$('#scoringTestCriteriaTableContainer').hide();
} 

function testScoreTestSavedSuccessMessage(selectedRowStudentId,scoringAssignmentStudentId){
	
	 var $gridAuto = $("#scorerStudentsAppearsForScoreTestGridTableId");
	 var studentName = $gridAuto.jqGrid ('getCell', selectedRowStudentId, 'lastName'); 
	 studentName += ", " + $gridAuto.jqGrid ('getCell', selectedRowStudentId, 'firstName');
	 var stateStudentIdentifier = $gridAuto.jqGrid ('getCell', selectedRowStudentId, 'stateStudentIdentifier');
	 var subject = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'subject');
	 var testSessionName = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'testsessionName');
	 
	 $('#successScoringTestSavedMessageScores').show();
	 $('#lblSuccessScoreTestCriteriaStudntName').text(studentName);
	 $('#lblSuccessScoreTestCriteriaStudentId').text(stateStudentIdentifier);
	 $('#lblSuccessScoreTestCriteriaAssmentPrg').text($('select#userDefaultAssessmentProgram option:selected').html());	 
	 $('#lblSuccessScoreTestCriteriaSubjectP').show();
	 $('#lblSuccessScoreTestCriteriaSubject').text(subject);
	// $('#lblSuccessScoreTestCriteriaGrade').text($('#lblScoreTestCriteriaGrade').text());
	 $('#lblSuccessScoreTestCriteriaTestsession').text(testSessionName);
}

function testScoreAllTestsessionCompletedConfirmation(){
	 $('#successScoringTestSavedAllMessageScores').show();
}
function testScoreNextTestSessionConfirmationShow(){ 
	$('#closeScorerTestTestSessionContainerConfirmation').show();
}
function testScoreNextStudentConfirmationShow(){
	$('#scorerTestNextStudetContainerConfirmation').show();
}

function testScoreTestSavedSuccessMessageHide(){
	 $('#successScoringTestSavedMessageScores').hide();
}

function testScoreAllTestsessionCompletedConfirmationHide(){
	 $('#successScoringTestSavedAllMessageScores').hide();
}
function testScoreNextTestSessionConfirmationHide(){ 
	$('#closeScorerTestTestSessionContainerConfirmation').hide();
}
function testScoreNextStudentConfirmationHide(){
	$('#scorerTestNextStudetContainerConfirmation').hide();
}

function scorerTestMoveScrollBar(pos,size){
	var childId = $('#'+pos).offset();
	var parentId = $('#tabs_scorerstest').offset();	
	var len =	childId.top - parentId.top;
	$(".with-sidebar-content").animate({
        scrollTop:  len + size
   });
}
function callTestsessionSearch(){
	//var myfilter = { groupOp: "AND", rules: [] };
	//myfilter.rules.push({ field: "ccqTestName", op: "eq", data: testSessionNameCCQ });
	var assessmentPrgId = $('#userDefaultAssessmentProgram').val();
	var subjectId = $("#scorerTestContentAreas").val();
	var gradeId = $("#scorerTestGrades").val();
	var schoolId = $("#scoreTestsSchool").val();
	var $gridAuto = $("#scorerTestStudentGridTableId");
	//callTestSessionStudentClear();
	$gridAuto.jqGrid('clearGridData');
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json",		
		/*url : 'getAssignScorerTestSessionView.htm?q=1',*/
		url : 'getTestScorerAssignedTestSessions.htm',
		search: false, 
		postData: {
				   "filters":"",//JSON.stringify(myfilter),
				   assessmentPrgramId:assessmentPrgId,
				   subjectId:subjectId,				   
				   gradeId:gradeId,
				   schoolId:schoolId
				   },
		loadBeforeSend: function(request) {
			// For some reason, this request doesn't get the CSRF information
			// As a quick band-aid that we needed to get out, this worked
			// this is a secondary location that might actually work without this hack, but I don't trust it
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			request.setRequestHeader(header, token);
		},
	}) .trigger("reloadGrid",[{page:1}]);	
	 $("#scorerStudentsAppearsScoreTestGridContainer").hide();
	 $("#scoringTestQuestionResponseContainerData").hide();
	 $('#scorerTestSearchContainer').show();
	 testEventTinyTextChanges('');
}
function callTestsessionFromRubricSearch(){	
	$('#lblScoreTestCriteriaSubjectP').hide();
	$('#scoringTestCriteriaTableContainer').hide();
	$('#scoringTestCriteriaTableContainerData').hide();
	$("#scoringTestQuestionResponseContainerData").hide();
	callTestsessionSearch();
}
function callTestSessionStudentClear(){	
		
	var testSessionId = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'id');
	var scoringAssignmentId = $('#scorerTestStudentGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'scoringAssignmentId');
	showgridintestscoringtab(scoringAssignmentId,testSessionId);

	$('#scoreTestStudentCcqStudentName').empty();
	$('#scoreTestStudentCcqQuestions').empty();	
	$('#scoringTestCriteriaTableContainer').hide();
	$('#scorerStudentsAppearsScoreTestGridContainer').show();
	$('#scoringTestCriteriaTableContainerData').hide();	
	$("#scoringTestQuestionResponseContainerData").hide();

	$("#lblTinyTextTestName").show();
	studentNameEventTinyTextChanges('');
	stateStudentIdEventTinyTextChanges('');
	itemQuestionEventTinyTextChanges('');	
}


function loadNonScoreingReasons(taskVariantIds,studentsTestsId){
	
	$("#taskVariantIdScoringReason").val(taskVariantIds);
	$("#selectedRowStudentId").val(studentsTestsId);
	var nsrSelect = $('#nonScoreReasons'), optionText='';
	nsrSelect.find('option').filter(function(){return $(this).val() > 0 ;}).remove().end();
	
	$.ajax({
		url: 'getnonScoreReasons.htm',
		dataType: 'json',
		type: "POST"
	}).done(function (data) { 
		if (data !== undefined && data !== null && data.length > 0) {
			$.each(data, function(i) {
				optionText = data[i].categoryName;
					nsrSelect.append($('<option></option>').attr("code",data[i].categoryCode).val(data[i].id).html(optionText));
			});
			nsrSelect.trigger('change');				
		} 
		nsrSelect.trigger('change.select2'); 
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}

function loadStudentQuestionResponse(taskVariantId,studentId,studentsTestsId){
	var taskVariantIds = taskVariantId.split("-");
	$("#scoringTestQuestionResponseContainerData").html('');
	$("#promptText").text("Prompt and Student Response");
	$.ajax({
		url: 'getPromptAndStudentResponse.htm',
		data: {
			taskVariantId: taskVariantIds,
			studentId: studentId,
			studentsTestsId: studentsTestsId
        },
		dataType: 'json',
		type: "POST"
	}).done(function (promptAndStudentResponse) { 
		var studentResponseHaving = false;
		var questionPromptCount = 0;
		if (promptAndStudentResponse !== undefined && promptAndStudentResponse !== null && promptAndStudentResponse.length > 0) {
			$.each(promptAndStudentResponse, function(i) {
			  var rowCount =i+1;
			  var taskStem = promptAndStudentResponse[i].taskStem;
			  var taskName = promptAndStudentResponse[i].taskName;
			  var studentResponse = promptAndStudentResponse[i].studentResponse;
			  var foilResponse = promptAndStudentResponse[i].foilResponse;			   
			  if(taskStem!=null && taskStem!=undefined && taskStem!='') {
				  taskStem = taskStem.replace('.ogg', '.mp3');
				  taskStem = taskStem.replace('.ogv', '.mp4');
			  }
			  var content ='<div style="width:100%"> <div id="studentQuestionheader'+rowCount+'" class="studentQuestionheader">'+
					    		   '<img src="./images/bullet_toggle_plus.png" />'+
					    		   '<span class="studentQuestionheader-tilte">'+taskName+'</span>'+
					    		   '</div>'+
								   '<div id="studentQuestiondescription'+rowCount+'" class="studentQuestiondescription">'+
								   '<div class="questionPrompt" id="questionPrompt" >'+							   
								   '<div class="questionPromptTitle">Question Prompt</div>'+
								   '<div class="questionPromptContent" >'+taskStem+'</div>';
			
					  				if(foilResponse.length>0){				  					
										 $.each(foilResponse, function(j) {
											 var foilText = foilResponse[j].foilText;
											 if(foilText!=null && foilText!=undefined && foilText!='') {
												 foilText = foilText.replace('.ogg', '.mp3');
												 foilText = foilText.replace('.ogv', '.mp4');
											 }
											 content=content+foilText;
										 });
					  				}				  				
									
					  			    content=content+'</div>'+
									'<div class="studentResponse" >'+
									'<div class="studentResponseTilte">Student Response</div>'+								
									'<div class="studentResponseContent" id="studentResponseContent_'+rowCount+'" >'+							
					  			    '<div style="text-align: center;display:none;" id ="audioErrorContent_'+rowCount+'" >Audio file is not available</div>';
					  			    if(studentResponse==null || studentResponse==undefined || studentResponse==''){
					  			    	content=content+'<div style="text-align: center;">Student Response Not Captured Through The System</div>';
					  			    }
					  			    else{
					  			    	studentResponseHaving=true;
					  			    	if(studentResponse!=null && studentResponse!=undefined && studentResponse!='' && (studentResponse.indexOf('.mp3')>0 || studentResponse.indexOf('.ogg')>0)){
					  			    		 
						  				  	   $.ajax({
						  				  	    url :'s3Credentials.htm',
						  				  	    type: 'GET',	
						  				  	    data: {
						  				  	    	filename : studentResponse
						  				  	       },
						  				  	    success : function(responseData){
												 //use presigned url returned by eprestservices as audio src
												 var audioUrl = responseData;
							  			    		 var audioContent='<div id="audioContainer_'+rowCount+'"  class="audioContainer" style="display:none;" >'+
													 '<div id="audio-player">'+
													 '<div class="range"  >'+
													 '<input type="range" step="any" id="seekbar_'+rowCount+'" class="seekbar" onchange="ChangeTheTime(this)" value="0"></input>'+
													 '</div>'+	
													 '<div class="duration"  >'+
													 '<span id="duration_'+rowCount+'" class="durationtext" >0:00</span> </div>'+
													 '</div>'+
													 '<br>'+
													 '<div id="buttons" class="buttons" > <span>'+
													 '<button id="prev_'+rowCount+'" class="prev" ></button>'+
													 '<button id="play_'+rowCount+'" class="play" ></button>'+
													 '<button id="pause_'+rowCount+'" class="pause" ></button>'+
												     '<button id="stop_'+rowCount+'" class="stop" ></button>'+
													 '<button id="next_'+rowCount+'" class="next" ></button>'+
													 '</span> </div>'+
													 '<div class="clearfix"></div>'+
													 '<div id="tracker" class="tracker">'+
													 '<div class="clearfix"></div>'+
													 '<ul id="playlist_'+rowCount+'" class="hidden playlist" style="display:none;" >'+
													 '<li song='+audioUrl+' audio="" id="playlistli_'+rowCount+'">'+studentResponse+'</li>'+
													 '</ul>'+
													 '</div>'+
													 '</div>';
						  			    		  
						  			    		$("#studentResponseContent_"+rowCount).append(audioContent);
						  			    		audioInit(rowCount);
						  			    		 
						  				  	},
									  		error : function(data){
									  		   	// console.log("..Error.."+data);			
									  		}
									  	   });
					  				  	   
					  			    	}else{
					  			    		content=content+studentResponse;
					  			    	}
					  			    }				  			    
					  			    
								    content=content+'</div>'+
									'</div>'+								
								    '</div>'+
								    '</div>';				
					                
					  $("#scoringTestQuestionResponseContainerData").append(content);
					  questionPromptCount++;		
			});
		}
	
		if(!studentResponseHaving && questionPromptCount>1){
			  $("#promptText").text("Prompts");	  
		}					    	
		else if(!studentResponseHaving && questionPromptCount==1){
			  $("#promptText").text("Prompt");	
		} 
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	}).always (function(jqXHROrData, textStatus, jqXHROrErrorThrown) {
		$("#scoringTestQuestionResponseContainerData").css('display','none'); 
		eventShowHiderQuestionResponse();
		
		jQuery("#questionPrompt audio").each(function(){
		    jQuery(this).attr('controlsList','nodownload');
		    jQuery(this).load();
		});
		
		jQuery("#questionPrompt video").each(function(){
		    jQuery(this).attr('controlsList','nodownload');
		    jQuery(this).load();
		});
		
		$("#scoringTestQuestionResponseContainerData ul").each(function(index) {
			$(this).addClass("questionResponseContainerUl");
		});
	});
}

function eventShowHiderQuestionResponse(){	
	
	if($("#scoringTestQuestionResponseContainerData").css('display') == 'none'){
		var mImg = "./images/bullet_toggle_minus.png";
		var pImg = "./images/bullet_toggle_plus.png";			    
		$(".studentQuestiondescription").not("#studentQuestiondescription1").slideUp();
		$("#studentQuestiondescription1").show();
		$("#studentQuestionheader1").children("img").attr("src", mImg).attr("title","Minus Icon");
		$("#studentQuestionheader1").css({"background-color": "silver"});						
		$('#scoringTestQuestionResponseContainerData').show();		
    }else{
    	$('#scoringTestQuestionResponseContainerData').hide();
    }	
	$(".questionPromptContent").find('*').width($(".questionPromptContent").width());
	$(".questionPrompt").height($(".studentQuestiondescription").height());
		
	
	$('.studentQuestionheader').off('click').on('click',function(){		
		var mImg = "./images/bullet_toggle_minus.png";
		var pImg = "./images/bullet_toggle_plus.png";		     
		var currentState = $(this).next(".studentQuestiondescription").css("display");		
		var id =$(this).attr("id");
		id=id.replace('studentQuestionheader','');

		        if(currentState == "block"){
		            $(this).next(".studentQuestiondescription").slideUp();
		            $(this).children("img").attr("src", pImg).attr("title","Plus Icon");
					$(this).css({"background-color": "#EDEDED"});
		        }
		        else{
		            $(this).next(".studentQuestiondescription").slideDown();
		            $(this).children("img").attr("src", mImg).attr("title","Minus Icon");
					$(this).css({"background-color": "silver"});
		        }
	});	
}



function audioInit(rowCount){
	var audio;
	
	//Hide Pause
	$('#pause_'+rowCount).hide();

	initAudio($('#playlist_'+rowCount+' li:first-child'));

	function initAudio(element){
		var song = element.attr('song');
		//Create audio object
		audio = new Audio(song);
		$('#playlistli_'+rowCount).data('audio', audio);
		audio.addEventListener('durationchange', function() {			  
		     var seekbar = $("#seekbar_"+rowCount);
	         seekbar.min = 0;
	         seekbar.max = audio.duration;	    
		}, false);
		
		audio.addEventListener('ended', function() {			
			$('#stop_'+rowCount).trigger("click");
		}, false);
		
		audio.addEventListener('error', function failed(e) {
			$('#audioErrorContent_'+rowCount).show();
			$('#audioContainer_'+rowCount).hide();			
		}, true);

		audio.addEventListener("canplaythrough", function () {
			$('#audioContainer_'+rowCount).show();
		}, false);		
	
	}
}


//Play button
$(document).on('click','.play',function(){
	var row = $(this).attr("id");
	var rowCount=row.replace('play_','');	
	var audio = $('#playlistli_'+rowCount).data('audio');
	audio.play();	
	$('#play_'+rowCount).hide();
	$('#pause_'+rowCount).show();
	showDuration(rowCount,audio);
});

//Pause button
$(document).on('click','.pause',function(){
	var row = $(this).attr("id");
	var rowCount=row.replace('pause_','');	
	var audio = $('#playlistli_'+rowCount).data('audio');
	audio.pause();	
	$('#play_'+rowCount).show();
	$('#pause_'+rowCount).hide();
});

//Stop button
$(document).on('click','.stop',function(){
	var row = $(this).attr("id");
	var rowCount=row.replace('stop_','');
	var audio = $('#playlistli_'+rowCount).data('audio');
	audio.pause();	
	audio.currentTime = 0;
	$('#play_'+rowCount).show();
	$('#pause_'+rowCount).hide();
});

//Next button
$(document).on('click','.next',function(){
	var row = $(this).attr("id");
	var rowCount=row.replace('next_','');
	var audio = $('#playlistli_'+rowCount).data('audio');
	audio.currentTime += 5.0; 
	showDuration(rowCount,audio);
});

//Prev button
$(document).on('click','.prev',function(){
	var row = $(this).attr("id");
	var rowCount=row.replace('prev_','');
	var audio = $('#playlistli_'+rowCount).data('audio');
	audio.currentTime -= 5.0; 
	showDuration(rowCount,audio);
});

 //Time/Duration
 function showDuration(rowCount,audio){
	$(audio).on('timeupdate',function(){
		//Get hours and minutes
		var s = parseInt(audio.currentTime % 60);
		var m = parseInt(audio.currentTime / 60) % 60;
		if(s < 10){
			s = '0'+s;
		}
		$('#duration_'+rowCount).html(m + ':'+ s);
		updateUI(rowCount,audio);
	});
 }
 
   // fires when seekbar is changed
   function ChangeTheTime(event){
	   var value = $(event).val();
	   var idcount = $(event).attr("id");
	   idcount=idcount.replace('seekbar_','');
	   var audio = $('#playlistli_'+idcount).data('audio');
	   audio.currentTime = value; 
	   audio.play();
	   $('#play_'+idcount).hide();
	   $('#pause_'+idcount).show();	   
   }
   
   function updateUI(rowCount,audio){	   
	   var seekbar = document.getElementById('seekbar_'+rowCount);  
	   seekbar.min = audio.startTime;
       seekbar.max = audio.duration;
       seekbar.value = audio.currentTime;        
    }  

	function viewExpandedRubricDialog() {
		    $('#scoringTestCriteriaTable').dialog({
	        autoOpen: false,
	        modal: true,
	        width: "70%",	       	 
	        dialogClass: 'scoringTestCriteriaTable',
	        create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			open :function(){
				$('#scoringTestCriteriaTable').css('width', '100%');
			}
			
	    });
	    
	    $('#scoringTestCriteriaTable').wrap($('<div></div>', {
	    	  id: "scoringTestCriteriaTableDiv",
	    	  class: 'scoringTestCriteriaTableDiv',
	    	  css: { 'max-height': '500px', 'overflow-y': 'auto' }
	    	}));	
	    $('#scoringTestCriteriaTable').dialog('open');
	    
	}
	
	function previewStimulus(clusterTestletId,taskVariantId) {
		var testletIds=clusterTestletId;
		var stimulusTaskVariantId=taskVariantId;
		$.ajax({
			url: 'getStimulusContent.htm',
			data: {
				testletId: testletIds,
				taskVariantId:stimulusTaskVariantId
			},
			dataType: 'json',
			type: "POST"
		}).done(function (StimulusContent) { 
			var contentDiv = $("#previewMvDialog");
			contentDiv.dialog({					 
				width: '98%',	
				dialogClass: 'previewStimulusDialog',
				close: function( event, ui ) {
					contentDiv.find('audio').each(function() {
						this.pause();
					});
					contentDiv.find('video').each(function() {
						this.pause();
					});
					contentDiv.find('audio').trigger('pause');
					contentDiv.find('video').trigger('pause');
				}
			});
			
			$('#previewMvDialog').wrap($('<div></div>', {
		    	  id: "previewMvDialogDiv",
		    	  class: 'previewMvDialogDiv',
		    	  css: { 'max-height': '500px', 'overflow-y': 'auto' }
		    	}));
			
			contentDiv.html('');

			for (var key in StimulusContent) {
				contentDiv.append(StimulusContent[key].stimulusContent);
			}
			$("#previewMvDialog").dialog('open'); 
			$('#previewMvDialog').dialog({ position: 'center' });
			
			contentDiv.find('audio').on('play', function (event) {
				var myAudio = event.currentTarget;
				contentDiv.find('audio').each(function() {
					if(this!=myAudio && this.paused==false){
						this.pause();
					}
				});
				contentDiv.find('video').trigger('pause');
			});

			contentDiv.find('video').on('play', function(event) {
				var myVideo = event.currentTarget;
				contentDiv.find('video').each(function() {
					if(this!=myVideo && this.paused==false){
						this.pause();
					}
				});
				contentDiv.find('audio').trigger('pause');
			}); 
			contentDiv.show();
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
	}
