var monitorCcqScorerSelectedTestId = null;
var const_scorer_tick = "√";
function scoringMonitorCcqScoresInit(){
	resetMonitorCcqScores(); 
	monitorCcqScoresTestInit();
	$("#MonitorCCQScorerPopup").hide();
	$("#divMcqRemainderErrMsg").hide();
	$("#confirmMonitorCCQScorerPopupNextBtn").dialog({
	    autoOpen: false,
	    modal: true
	});
}

function resetMonitorCcqScores(){
	$('#monitorCcqScoresAssessmentPrograms').val('');
	$('#monitorCcqScoresAssessmentPrograms').trigger('change');
	if($("#monitorCcqScoresGridTableId")[0].grid && $("#monitorCcqScoresGridTableId")[0]['clearToolbar']){
		$("#monitorCcqScoresGridTableId")[0].clearToolbar();
	}
	$('#monitorCcqScoresContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	
	$("#monitorCcqScoresContentAreas").val("").trigger('change.select2');
	$("#monitorCcqScoresGrades").val("").trigger('change.select2');
	$("#monitorCcqScoresDistrict").val("").trigger('change.select2');
	$('#monitorCcqScoresSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresSchool').trigger('change.select2');
	monitorCcqScoresAssessmentProgram("monitorCcqScoresAssessmentPrograms");
	monitorCcqScoresTestHide();
}

function monitorCcqScoresTestHide(){
	$('#monitorCcqScoresGridContainer').hide();
	$('#monitorCCQScoreTestSearchContainer').show();
	disableMonitorCcqScoresTestSearch();
	monitorCcqScoresRemainderHide();
}
function monitorCcqScoresRemainderHide(){
	$('#monitorCcqScoresRemainderTableContainer').hide();
	$("#monitorCcqScoresNextBtn").prop("disabled",true);
	$('#monitorCcqScoresNextBtn').addClass('ui-state-disabled');
	
	monitorScoresRemainderMessageHide();
}
function monitorScoresRemainderMessageHide(){
	$('#successMonitorScoresRemainderMessage').hide();
}
function disableMonitorCcqScoresTestSearch(){
	$("#monitorCcqScoresSearchBtnTest").prop("disabled",true);
	$('#monitorCcqScoresSearchBtnTest').addClass('ui-state-disabled');
	$("#monitorCcqScoresNextBtn").prop("disabled",true);
	$('#monitorCcqScoresNextBtn').addClass('ui-state-disabled');
}

function monitorCcqScoresTestInit(){
	monitorCcqScorerSelectedTestId = null;
	monitorCcqScoresTestGrid();
	//scorerStudentsAppearsForScoreTestGrid();
	
	/*$('#scorerTestStudentScoredNextBtn').off('click').on('click',function(){
		if( ! testScoreAllStudentInAllTestSessionScored ){
			scorerTestStudentScrollBarMove=true;
			$('#scorerStudentsAppearsForScoreTestGrid').show();
			showgridintestscoringtab();
			
			
			$('#scoreTestAssessmentPrg').empty().append($('select#monitorCcqScoresAssessmentPrograms option:selected').html());
			
			if($('select#monitorCcqScoresContentAreas option:selected').html() != 'Select'){
				$('#scoreTestStudentSubject').empty().append($('select#monitorCcqScoresContentAreas option:selected').html());
				$('#scoreTestStudentSubjectParent').show();
				
			} else {
				$('#scoreTestStudentSubjectParent').hide();
			}
			
			if($('select#monitorCcqScoresGrades option:selected').html() != 'Select'){
				$('#scoreTestStudentGrade').empty().append($('select#monitorCcqScoresGrades option:selected').html());
				$('#scoreTestStudentGradeParent').show();
			} else {
				$('#scoreTestStudentGradeParent').hide();
			}
			var testSessionName = $('#scorerTestStudentScoredGridTableId').jqGrid ('getCell', scoreTestScoringAssignmentId, 'testsessionName');
			$('#scoreTestStudentTestSession').empty().append(testSessionName);
			
		}
		else{
			scoringScorersTestInit();
		}
	});*/
	
	$('#monitorCcqScoresAssessmentPrograms,#monitorCcqScoresContentAreas,#monitorCcqScoresGrades, #monitorCcqScoresDistrict, #monitorCcqScoresSchool').select2({
		placeholder:'Select',
		multiple: false
	}); 
	
	// District Value	 
	$('#monitorCcqScoresDistrict').on("change",function() {
		monitorCcqScoreDistrictChangeEvent();
	});	
	
	monitorCcqScoreLoad();
	
	filteringOrganization($('#monitorCcqScoresDistrict'));
	filteringOrganization($('#monitorCcqScoresSchool'));
	
	// Assessment Programs change value		
	$('#monitorCcqScoresAssessmentPrograms').on("change",function() {			
	
		clearMonitorCcqScoresTestFilters();
		var assessmentProgramId = $('#monitorCcqScoresAssessmentPrograms').val();
		$('#monitorCcqScoresContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#monitorCcqScoresContentAreas').val("0").trigger('change.select2');	
		
		if (assessmentProgramId != 0) {			
			$.ajax({
		        url: 'getContentAreasByAssessmentProgram.htm',
		        data: {
		        	assessmentProgramId: assessmentProgramId
		        	},
		        dataType: 'json',
		        type: "POST",
		        success: function(contentAreas) {
		        					        	
					$.each(contentAreas, function(i, contentArea) {
						$('#monitorCcqScoresContentAreas').append($('<option></option>').attr("value", contentArea.id).text(contentArea.name));
					});
					
					if (contentAreas.length == 1) {
						$("#monitorCcqScoresContentAreas option").removeAttr('selected').next('option').attr('selected', 'selected');
						$("#monitorCcqScoresContentAreas").trigger('change');
					}
					$('#monitorCcqScoresContentAreas').trigger('change.select2');
		        }
			});					
		}else {
			$('#monitorCcqScoresGridContainer').hide();
			
		}
		monitorCcqScoresTestSearchButtonActive();
	});

//monitorCcqScoresContentAreas
$('#monitorCcqScoresContentAreas').on("change",function() {			
	
	$('#monitorCcqScoresGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresGrades').trigger('change.select2');
	var contentAreaId = $('#monitorCcqScoresContentAreas').val();
	if (contentAreaId != 0) {
		$.ajax({
	        url: 'getScoreTestGradeCourseByContentAreaId.htm',
	        data: {
	        	contentAreaId: contentAreaId
	        	},
	        dataType: 'json',
	        type: "GET",
	        success: function(grades) {
	        	$('#monitorCcqScoresGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
				$.each(grades, function(i, grade) {
					$('#monitorCcqScoresGrades').append($('<option></option>').attr("value", grade.id).text(grade.name));
				});
				
				if (grades.length == 1) {
					$("#monitorCcqScoresGrades option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#monitorCcqScoresGrades").trigger('change');
				}				
				$('#monitorCcqScoresGrades').trigger('change.select2');
	        }
		});				
	}
});	

// monitorScorer Continue
$('#btnMcqsContinue').off("click").on("click",function(event){
	resetMonitorCcqScores();
});
// monitorScorer Done
$('#btnMcqsDone').off("click").on("click",function(event){
	$("#tabs_monitorccqscores").hide();

	//window.location.reload();
	/*refreshAssignScorer = true;*/
	$('a[href="#tabs_monitorccqscores"]').parent("li").removeClass("current");
});

$('#monitorCcqScoresSearchBtnTest').off("click").on("click",function(event) {
	
	if($("#monitorCcqScoresGridTableId")[0].grid && $("#monitorCcqScoresGridTableId")[0]['clearToolbar']){
		$("#monitorCcqScoresGridTableId")[0].clearToolbar();
	}
	
	if($("#monitorCcqScoresAssessmentPrograms").val() != ""){
			$('#monitorCcqScoresGridContainer').show();
			monitorCcqScoresTestGridLoad();
			monitorCcqScoresRemainderHide();
			monitorCcqScoreMoveScrollBar("monitorCcqScoresGridContainer",0);
			// hide and show grid
			$('#successMonitorScoresRemainderMessage').hide();
			$("#divMcqRemainderErrMsg").hide();
			$('#monitorCcqScoresRemainderTableContainer').hide();
	}		
});	

$('#monitorCcqScoresNextBtn').off('click').on('click',function(e){
	var subject = $('#monitorCcqScoresGridTableId').jqGrid ('getCell', monitorCcqScorerSelectedTestId, 'subject');
	var CCQTest = $('#monitorCcqScoresGridTableId').jqGrid ('getCell', monitorCcqScorerSelectedTestId, 'ccqTestName');
	var testsessionName = $('#monitorCcqScoresGridTableId').jqGrid ('getCell', monitorCcqScorerSelectedTestId, 'testsessionName');
	
	$('#lblMonitorCcqScoresRemainderAssessmentPrg').empty().append($('select#monitorCcqScoresAssessmentPrograms option:selected').html());
	$("#lblMonitorCcqScoresRemainderSubject").empty().text(subject);
/*	$("#lblMonitorCcqScoresRemainderGrade").empty().text(pathway);*/
	$("#lblMonitorCcqScoresRemainderTest").empty().text(testsessionName);	
	$(".monitorCcqScoresStudentTestName").empty().text(CCQTest);	
	
	
	monitorCcqScoresDetailsGrid();
	$('#successMonitorScoresRemainderMessage').hide();
	$("#divMcqRemainderErrMsg").hide();
	/*$("#monitorCcqScoreRemainderNextBtn").prop("disabled",false);
	$('#monitorCcqScoreRemainderNextBtn').removeClass('ui-state-disabled');*/
	$("#monitorCCQScoreTestSearchContainer").hide();
	$("successMonitorScoresRemainderMessage").hide();
	$("monitorCcqScoresRemainderTableContainer").show();

});

$('#monitorCcqScoreRemainderNextBtn').off('click').on('click',function(e){
	var subject = $('#monitorCcqScoresGridTableId').jqGrid ('getCell', monitorCcqScorerSelectedTestId, 'subject');
	var pathway = $('#monitorCcqScoresGridTableId').jqGrid ('getCell', monitorCcqScorerSelectedTestId, 'grade');
	var CCQTest = $('#monitorCcqScoresGridTableId').jqGrid ('getCell', monitorCcqScorerSelectedTestId, 'ccqTestName');
	var monitorassessmentProgram = $('select#monitorCcqScoresAssessmentPrograms option:selected').val();
	
	var monitorCCQstatus = false;
	$("#lblMcqRemainderSubject").empty().text(subject);
	$("#lblMcqRemainderGrade").empty().text(pathway);
	$("#lblMcqRemainderTest").empty().text(CCQTest);
	
	var scorerStudentData = [];
	$('.scorerRemaiderSelect option:selected').each(function(){		
		if($(this).val() == "R" || $(this).val() == "E"){
			monitorCCQstatus = true;
			var scorerIdStr = $(this).parent().parent("td").attr("aria-describedby");
			 
			var scorerId = scorerIdStr.substring((scorerIdStr.lastIndexOf("_")+1));
			var scorerIdArr = scorerId.split("-");
			var studentId = $(this).parent().parent().parent("tr").attr("id");
			var scoringStudentId = $('#monitorCcqScoresRemainderGridTableId').jqGrid ('getCell', studentId, 'scorerstudentId'); 
			
			scorerStudentData.push('{"scorerId":'+scorerIdArr[0]+',"scoringAssignmentScorerId":'+scorerIdArr[1]+',"scoringAssignmentStudentId":'+scoringStudentId+',"studentId":'+studentId+',"type":"'+$(this).val()+'"}');
		}		
	});
	
//	if(scorerStudentData!= null && scorerStudentData[0] != undefined && scorerStudentData[0] != null && scorerStudentData[0].length > 0 ){
		if(monitorCCQstatus){
			monitorScoresSubmitToRemainder(scorerStudentData,subject,pathway,CCQTest,monitorassessmentProgram);
			
		}	
		$('#successMonitorScoresRemainderMessage').show();	
		monitorCcqScoreMoveScrollBar("successMonitorScoresRemainderMessage",0);
		/*$("#monitorCcqScoreRemainderNextBtn").prop("disabled",true);
		$('#monitorCcqScoreRemainderNextBtn').addClass('ui-state-disabled');*/
		
	/*} else {
		$("#confirmMonitorCCQScorerPopupNextBtn").dialog({
		      buttons : {
		    	  "Ok" : function() {
		    		  $(this).dialog("close");
		    	   }
		     }
	  	});
		$("#confirmMonitorCCQScorerPopupNextBtn").dialog("open");
		$('#successMonitorScoresRemainderMessage').hide();
		$("#divMcqRemainderErrMsg").hide();
	}*/
	
});

$('#monitorCCQScoreTestSearchContainer').show();	
}


function monitorCcqScoreLoad(){
	var districtOrgSelect = $('#monitorCcqScoresDistrict');
	$.ajax({
		url: 'getOrgsBasedOnUserContext.htm',
		dataType: 'json',
		data: {
			orgId : $("#monitorScorerUserCurrentOrgId").val(), //${user.currentOrganizationId},
	    	orgType:'DT',
	    	orgLevel:50
	    	},				
		type: "GET",
		success: function(districtOrgs) {	
			districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			districtOrgSelect.trigger('change.select2');
			if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
				$.each(districtOrgs, function(i, districtOrg) {
					optionText = districtOrgs[i].organizationName;
					districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
				});					
		   if (districtOrgs.length == 1) {
				districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				//$("#assignScorerdistrict").trigger('change');
				monitorCcqScoreDistrictChangeEvent();
			} 
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
			}
			$('#monitorCcqScoresrSchool, #monitorCcqScoresDistrict').trigger('change.select2');
		}
	});

	
}



function monitorScoresSubmitToRemainder(postData,subject,pathway,CCQTestName,monitorassessmentProgram){
	var scorerStudent="";
	var multipleStudent = postData;
	if(postData.length == 1){
		scorerStudent = postData[0];
		multipleStudent = [""];
	} 
	
	$.ajax({
		url: 'monitorScorerSendRemainder.htm',
		dataType: 'json',
		type: "POST",
		data:{ monitorScorerStudentDetails : multipleStudent,
			   subject : subject,
			   pathway : pathway,
			   CCQTestName : CCQTestName,
			   monitorScorerStudent : scorerStudent,
			   monitorassessmentProgram : monitorassessmentProgram
			},
		success: function(response) {
			if(response.mailSend){
				$("#divMcqRemainderErrMsg").show();
			}else {
				$("#divMcqRemainderErrMsg").hide();
			}
		},
		error:function(response){
			
		}
	});
}

function monitorCcqScoresTestSearchButtonActive(){
	if(  $("#monitorCcqScoresAssessmentPrograms").val() != "" ){
		$("#monitorCcqScoresSearchBtnTest").prop("disabled",false);
		$('#monitorCcqScoresSearchBtnTest').removeClass('ui-state-disabled');
	}
	else{
		$("#monitorCcqScoresSearchBtnTest").prop("disabled",true);
		$('#monitorCcqScoresSearchBtnTest').addClass('ui-state-disabled');
	}	
}

function clearMonitorCcqScoresTestFilters(){
	$('#monitorCcqScoresContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresDistrict').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresGrades, #monitorCcqScoresContentAreas, #monitorCcqScoresDistrict, #monitorCcqScoresSchool').trigger('change.select2');
	monitorCcqScoreLoad();
}



function monitorCcqScoresTestGridLoad(){
	
	var assessmentPrgId = $("#monitorCcqScoresAssessmentPrograms").val();
	var subjectId = $("#monitorCcqScoresContentAreas").val();
	var gradeId = $("#monitorCcqScoresGrades").val();  
	var districtId = $("#monitorCcqScoresDistrict").val(); 
	var schoolId = $("#monitorCcqScoresSchool").val();
	
	scoreTestScoringAssignmentId = null;
	scoreTestScoringAssignmentScorerId = null;
	scoreTestStudentId = null;
	
	
	var $gridAuto = $("#monitorCcqScoresGridTableId");
	$gridAuto.jqGrid('clearGridData');
	
	$gridAuto.jqGrid('setGridParam',{
		datatype:"json", 
		/*url : 'getAssignScorerTestSessionView.htm?q=1',*/
		url : 'getMonitorScoresScoringDetails.htm',
		search: false, 
		postData: {
				   "filters":"",
				   assessmentPrgId:assessmentPrgId,
				   subjectId:subjectId,
				   gradeId:gradeId,
				   districtId:districtId,
				   schoolId:schoolId
				   }
	}) .trigger("reloadGrid",[{page:1}]);
}


function callMonitorCCQScoreTestSearch(){
	$("#monitorCCQScoreTestSearchContainer").show();
	$("#monitorCcqScoresRemainderTableContainer").hide();
	$("#successMonitorScoresRemainderMessage").hide();
	monitorCcqScoresTestGridLoad();
}

function monitorCcqScoresAssessmentProgram(testScorerAssessmentPrograms){		
	// Assessment Program
	var apSelect = $('#'+testScorerAssessmentPrograms), optionText='';
	//apSelect.find('option').filter(function(){return $(this).val() > 0 ;}).remove().end();	
	apSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	apSelect.val("0").trigger('change.select2');	
	
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST",
		success: function(assessmentPrograms) {				
			if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
				$.each(assessmentPrograms, function(i, assessmentProgram) {
					optionText = assessmentPrograms[i].programName;
					if(assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
						apSelect.append($('<option selected=\''+'selected'+'\'></option>').val(assessmentProgram.id).html(optionText));
					} else {
						apSelect.append($('<option></option>').val(assessmentProgram.id).html(optionText));
					}
				});
				apSelect.trigger('change');				
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('#searchFilterErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
			}
			apSelect.trigger('change.select2');
		}
	});
}

function monitorCcqScoresTestGrid(){

	var gradeHeaderGridText="Grade";
	if(userAssessProg!=null && userAssessProg!=undefined && userAssessProg!=""){
		if(userAssessProg=='CPASS') {
			gradeHeaderGridText = "Pathway";  
		}
	}
		
	var $gridAuto = $("#monitorCcqScoresGridTableId");
	//Unload the grid before each request.
	//$gridAuto.jqGrid('GridUnload');
	var gridWidthForVO = $('#monitorCcqScoresGridTableId').parent().width();		
	if(gridWidthForVO < 700) {
		gridWidthForVO = 700;				
	}
	var cellWidthForVO = gridWidthForVO/3;
	
	var cmforTestScorerGrid = [
	    { label: 'scoringAssignmentId',key:true, width:0,name : 'id', index : 'id', hidden : true,hidedlg : true},		
		{ label: '', name : 'testSessionSelect', index : 'testSessionSelect',width:40,search : false,sortable:false, hidden : false,formatter:monitorCcqScoreTestRadioButtonFormatter,  hidedlg : true},
		{ label: 'testsessionName', name : 'testsessionName', index : 'testsessionName', hidden : true, hidedlg : true},
        { label: 'CCQ Test', name : 'ccqTestName', index : 'ccqTestName', width : cellWidthForVO, search : true,sorttype : 'text', hidden : false, hidedlg : true,formatter: escapeHtml},
        { label: 'Date Created', name : 'createdDate', index : 'createdDate', width : 100, sorttype : 'date', search : false, hidden : false, hidedlg : true, formatter: function (cellval, opts) {
	           if(cellval){
			        	var date = new Date(cellval);
   			         opts = $.extend({}, $.jgrid.formatter.date, opts);
   			         return $.fmatter.util.DateFormat("", date, 'm/d/Y', opts);
			           }else{
			        	   return '';
			           }
			     	}},
		{ label: '# of Students', name : 'studentCount', index : 'studentCount', sorttype : 'int', width : 125, search : false, hidden: false, hidedlg: true},
		{ label: '# of Students Scored', name : 'studentsscoredcount', index : 'studentsscoredcount', sorttype : 'int', width : 195, search : false, hidden: false, hidedlg: true},
		{ label: '# of Scorers', name : 'scorercount', index : 'scorercount',sorttype : 'text', width : 100, search : false, hidden : true, hidedlg : false},
		{ label: 'Testing Program', name : 'testingProgramName', index : 'testingProgramName',sorttype : 'text', width : cellWidthForVO, search : true, hidden : true, hidedlg : false},
		{ label: 'Subject', name : 'subject', index : 'subject',sorttype : 'text', width : cellWidthForVO, search : true, hidden : true, hidedlg : false},
		{ label: 'Grade', name : 'grade', index : 'grade',sorttype : 'text', width : cellWidthForVO, search : true, hidden : true, hidedlg : false},
		{ label: 'District', name : 'districtName', index : 'districtName', width : cellWidthForVO, search : true, hidden : true, hidedlg : false},
		{ label: 'School', name : 'schoolName', index : 'schoolName', width : cellWidthForVO, search : true, hidden : true, hidedlg : false
		}
	];
	//JQGRID
  	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVO,
		/*height:"200px",*/
		colNames : [ 'scoringAssignmentId','','testsessionName','Test','Date Created', '# of Students', '# of Students Scored' , '# of Scorers' ,'Testing Program','Subject',gradeHeaderGridText,'District Name', 'School'
		           ],
	  	colModel :cmforTestScorerGrid,
		rowNum : 10,
		multiselect:false,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#monitorCcqScoresGridPager',
		sortname : 'id',
        sortorder: 'asc',
        refresh: false,
		columnChooser : true,
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
	    	var radio = $(e.target).closest('tr').find('input[type="radio"]');
        	radio.attr('checked', 'checked');
        	
        	$("#monitorCcqScoresNextBtn").prop("disabled",false);
	    	$('#monitorCcqScoresNextBtn').removeClass('ui-state-disabled');
    		return true;
	    },
	    onSelectRow: function(rowid, status, e){
	    	monitorCcqScorerSelectedTestId = rowid;
	    	$("#monitorCcqScoresNextBtn").prop("disabled",false);
	    	$('#monitorCcqScoresNextBtn').removeClass('ui-state-disabled');

	    }, 
	    onSelectAll: function(rowids, status, e){
	    	
	    	
	    },
	    beforeRequest: function() {
	    	/*if(!$('#searchTestSessionFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
	    		return false;
	    	} */
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
	    //	monitorCcqScorerSelectedTestId = null;
	    	$("#monitorCcqScoresNextBtn").prop("disabled",false);
	    	$('#monitorCcqScoresNextBtn').removeClass('ui-state-disabled');
	    		    	
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');		              
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	        var assessmentPrgId =  $('#monitorCcqScoresAssessmentPrograms').val();
			var subjectId =  $("#monitorCcqScoresContentAreas").val();
			var gradeId =  $("#monitorCcqScoresGrades").val();
			var districtId = $("#monitorCcqScoresDistrict").val(); 
			var schoolId = $("#monitorCcqScoresSchool").val();
			$(this).setGridParam({postData: {assessmentPrgId :assessmentPrgId,
				subjectId:subjectId,gradeId:gradeId, districtId:districtId,schoolId:schoolId}});
	    },
	    gridComplete: function() {	            		            	
	    	//Need to store the radio button value so that we can set it back,
	    	//because JQGrid does not support automatic selection in memory.
	    }, loadComplete:function(){
	    	
	    	$("#monitorCcqScoresNextBtn").prop("disabled",true);
     		$("#monitorCcqScoresNextBtn").addClass('ui-state-disabled');
	     	 
	    }	
	});
   	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
   	
   $("#refresh_monitorCcqScoresGridTableId").hide();
//   $("#cb_"+$gridAuto[0].id).hide();
}

function monitorCcqScoreTestRadioButtonFormatter(cellvalue, options, rowObject){
	 var htmlString = "";
	 htmlString = '<input type="radio" id="mccqScoreTest_' + options.rowId + '" name="monitorCcqScoresSelectedTest" value=""/>';
	    return htmlString;
	}
	
$('[name=monitorCcqScoresSelectedTest]').on('click', function (e){
	  var rid = $(this).attr('id');
	  rid = rid.replace("mccqScoreTest_","");
	  $('#monitorCcqScoresGridTableId').jqGrid('setSelection', rid);
});


function monitorCcqScoreMoveScrollBar(pos,size){
	var childId = $('#'+pos).offset();
	var parentId = $('#tabs_monitorccqscores').offset();	
	var len =	childId.top - parentId.top;
	$(".with-sidebar-content").animate({
        scrollTop:  len + size
   });
}

function monitorCcqScoresDetailsGrid(){
	var $gridAuto = $("#monitorCcqScoresRemainderGridTableId");
	//Unload the grid before each request.
	$gridAuto.jqGrid('GridDestroy');
	$('#monitorCcqScoresRemainderDivGridContainer').append('<table class="responsive" id="monitorCcqScoresRemainderGridTableId"></table>'+
					'<div id="monitorCcqScoresRemainderGridPager" style="width: auto;"></div>');
	
	$gridAuto = $("#monitorCcqScoresRemainderGridTableId");
	var gridWidthForVO = $('#monitorCcqScoresRemainderGridTableId').parent().width();		
	if(gridWidthForVO < 700) {
		gridWidthForVO = 700;				
	}
	var cellWidthForVO = gridWidthForVO/5;
	
	
	var cmforTestScorerGrid = [
	                           { label: 'studentId',key:true, width:0,name : 'studentId', index : 'studentId', hidden : true,hidedlg : true, sortable : false},
	                           { label: 'scorerstudentId',key:true, width:0,name : 'scorerstudentId', index : 'scorerstudentId', hidden : true,hidedlg : true, sortable : false},
	                           { label: 'lastName', name : 'lastName', index : 'lastName', width : cellWidthForVO, search : false,sorttype : 'text', hidden : false, hidedlg : true, sortable : false},
	                           { label: 'firstName', name : 'firstName', index : 'firstName', width : cellWidthForVO, search : false,sorttype : 'text', hidden : false, hidedlg : true, sortable : false}
	                   	];
	var colName = ['Student Id','scorerstudentId','Last Name','First Name'];
	
	$.ajax({
        url: 'getMonitorCcqScoresTestDetails.htm',
        data: {
        	scoringAssignmentId: monitorCcqScorerSelectedTestId
        	},
        dataType: 'json',
        type: "POST",
        success: function(response) {        	
        	var headers = response.rows[0];         	
        	for(var i = 4; i < headers.length - 4; i++){        		
        		var scorerIdStr = headers[i].split("-");         		
        		var scorerIdwithAssignmentIdHeader = scorerIdStr[0]+"-"+scorerIdStr[1]; 
        		var scorerNameHeader = scorerIdStr[2]+" "+scorerIdStr[3];    
        		cmforTestScorerGrid.push({ label: 'cScorer_'+scorerIdwithAssignmentIdHeader, width:165,name : 'cScorer_'+scorerIdwithAssignmentIdHeader, index : 'cScorer_'+scorerIdwithAssignmentIdHeader, search:false, hidden : false,hidedlg : true,sortable:false,
        			 cellattr: function(rowId, tv, rawObject, cm, rdata) {
                         if (tv ==  const_scorer_tick) { return ' class="score_tick"'; }
                    },
        			formatter:monitorCcqScoreRemainderFormatter });
        		
        		colName.push("<a href='#' id="+scorerIdStr[0]+" class='scorerHeader' style='text-decoration:underline;color: #fff;'>"+scorerNameHeader+"</a>");
        	}        	
        	cmforTestScorerGrid.push({ label: 'districtName', name : 'district', index : 'district', width : 200, search : false, sorttype : 'text', hidden : false, hidedlg : false, sortable : false});
        	cmforTestScorerGrid.push({ label: 'schoolName', name : 'school', index : 'school', width : 200, search : false,sorttype : 'text', hidden : false, hidedlg : false, sortable : false});
        	cmforTestScorerGrid.push({ label: 'testingProgram', name : 'testingProgram', index : 'testingProgram', width : 200, search : false,sorttype : 'text', hidden : false, hidedlg : false, sortable : false});
        	cmforTestScorerGrid.push({ label: 'stateStudentIdentifier', name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', width : 200, search : false,sorttype : 'text', hidden : false, hidedlg : false, sortable : false});
        	
        	colName.push("District");
        	colName.push("School");
        	colName.push("Testing Program");
        	colName.push("State Student Id");
        	var rows = response.rows ;
	
			//JQGRID
		  	$gridAuto.scb({
				mtype: "POST",
				datatype : "local",
				width: gridWidthForVO,
				/*height:"200px",*/
				data:rows.splice(1),
				colNames : colName,
			  	colModel :cmforTestScorerGrid,
				rowNum : 10,
				multiselect:false,
				rowList : [ 5,10, 20, 30, 40, 60, 90 ],
				pager : '#monitorCcqScoresRemainderGridPager',
				sortname : 'id',
		        sortorder: 'asc',
		        refresh: false,
				columnChooser : true,
				loadonce: true,
				viewable: false,
				/*localReader: {
			        page: function (obj) {
			            return obj.page !== undefined ? obj.page : "0";
			        }
			    },*/
			    localReader: {
                    repeatitems: true,
                    cell: "",
                    id: 0
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
			   
			    onSelectRow: function(rowid, status, e){
			    	
			    }, 
			    onSelectAll: function(rowids, status, e){
			    	
			    	
			    },
			    beforeRequest: function() {
			    	
			    },
			    gridComplete: function() {	            		            	
			    	$('.scorerHeader').off('click').on('click',function(){
			    		
			    		var scorerId = $(this).attr('id');
			    		
			    		$.ajax({
			    	        url: 'getMonitorCCQScoresDetails.htm',
			    	        data: {
			    	        	scorerId: scorerId
			    	        },
			    	        dataType: 'json',
			    	        type: "POST",
			    	        success: function(result) {			    	        	
			    	        	$("#monitorScorerLastName").empty().text(result.lastName);	
			    	        	$("#monitorScorerFirstName").empty().text(result.firstName);
			    	        	if(result.middleName != ''){
			    	        	$("#monitorScorerMiddleInitial").empty().text(' ');
			    	        	}else {
			    	        	$("#monitorScorerMiddleInitial").empty().text(result.middleName);
			    	        	}
			    	        	$("#monitorScorerEmail").empty().text(result.email);	
			    	        	$("#MonitorCCQScorerPopup").show();			    	        	
			    	        }
			    		});
			    		
			    		$("#MonitorCCQScorerPopupClose").on('click',function(){
			    			$("#MonitorCCQScorerPopup").hide();
			    		});
			    		
			    	});
			    	
			    
			    	//Need to store the radio button value so that we can set it back,
			    	//because JQGrid does not support automatic selection in memory.
			    	
			    }	
			});
		  	
		   	$gridAuto.jqGrid('setGridParam',{
				postData: { "filters": ""}
			}).trigger("reloadGrid",[{page:1}]);
			$gridAuto[0].toggleToolbar();
		   $("#refresh_monitorCcqScoresRemainderGridTableId").hide();
		//   $("#cb_"+$gridAuto[0].id).hide();
		   $('#monitorCcqScoresRemainderTableContainer').show();
		   monitorCcqScoreMoveScrollBar("monitorCcqScoresRemainderTableContainer",0);
        }
	});
	
}

function monitorCcqScoreRemainderFormatter(cellvalue, options, rowObject){
	if(cellvalue == 0)
		return '<select class="scorerRemaiderSelect"><option value="">Select Action</option><option value="R">Send reminder to scorer</option><option value="E">Exclude from average</option></select>';
	return const_scorer_tick;
}

function monitorCcqScoreDistrictChangeEvent(){
	//assignScoringTestSessionGridAreaHide();
	
	$('#monitorCcqScoresSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#monitorCcqScoresSchool').trigger('change.select2');
	var districtOrgId = $('#monitorCcqScoresDistrict').val();
	if (districtOrgId != null && districtOrgId != '' && districtOrgId != 0) {
		$.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
	        data: {
	        	orgId : districtOrgId,
	        	orgType:'SCH',
	        	orgLevel:70
	        	},
	        dataType: 'json',
	        type: "GET",
	        success: function(schoolOrgs) {
				$.each(schoolOrgs, function(i, schoolOrg) {
					$('#monitorCcqScoresSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
				});		
				if (schoolOrgs.length == 1) {
					$("#monitorCcqScoresSchool option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#monitorCcqScoresSchool").trigger('change');
				}
				$('#monitorCcqScoresSchool').trigger('change.select2');
	        }
		});
	}
}

