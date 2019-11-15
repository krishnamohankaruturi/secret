var scoringuploadCategoryCode = 'SCORING_RECORD_TYPE';
var assessmentProgId = null;
function scoringUploadScoresInit(){	
	
	assessmentProgId = $('#userDefaultAssessmentProgram').val();
	$('#uploadScoresDistrict,#uploadScoresContentAreas,#uploadScoresGrades,#uploadScoresStage').select2({
		placeholder:'Select',
		multiple: false,
		allowClear : true
	});	
	
	$('#uploadScoresSchool,#uploadScoresTestSessions').select2({
		placeholder:'Select',
		multiple: true		
	});	
	
	$('#uploadScoreFileDataInput').val('');
	//disableButtons("uploadScoreFileDataInput");
	disableButtons("uploadScoreButton");
	disableButtons("uploadScoresNextBtn");
	disableButtons("uploadScoresDownloadBtn");
	$("#downloadIconImage,#downloadFileName").hide();
	disableHref();
	scoringUploadScoresOnload();
	loadScoringUploadData();
	filteringOrganization($('#uploadScoresDistrict'));
	filteringOrganization($('#uploadScoresSchool'));
	uploadScoresReset();
	$('#uploadScoreFileDataInput').val('');	
	$('#uploadScoresIncludeItem').attr('checked', false);
	
	var userDefaultAssessmentProgram = $("#userDefaultAssessmentProgram option:selected").text();
	if(userDefaultAssessmentProgram!=null && userDefaultAssessmentProgram!=undefined && userDefaultAssessmentProgram!=""){
		if(userDefaultAssessmentProgram=='CPASS'){
			$('#uploadScoresGradesLabel').text("PATHWAY: ").append("<span class='lbl-required'>*</span>");
			$('#uploadScoresGrades').attr('title','Pathway');
		}
		else{		
			$('#uploadScoresGradesLabel').text("GRADE: ").append("<span class='lbl-required'>*</span>");
			$('#uploadScoresGrades').attr('title','Grade');		
		}				
	}	
	//if user is school Level
	if($("#userCurrentOrganizationType").val() == 'SCH'){
		uploadScoresSubjectLoad();
		refreshSelectBtn("uploadScoresContentAreas");
		refreshSelectBtn("uploadScoresGrades");
		refreshSelectBtn("uploadScoresStage");
		refreshSelectBtn("uploadScoresTestSessions");
		uploadScorerNextBtnDisable();
	}
	$("#lblTinyTextAssmentPrg").hide().text('');
	$(".clearTinyText").text('');
}

function uploadScoresReset(){
	refreshSelectBtn("uploadScoresSchool");
	refreshSelectBtn("uploadScoresContentAreas");
	refreshSelectBtn("uploadScoresGrades");
	refreshSelectBtn("uploadScoresStage");
	refreshSelectBtn("uploadScoresTestSessions");
}

$('#uploadScoresNextBtn').on('click', function (e){
	disableHref();
	disableButtons("uploadScoresDownloadBtn");
	disableButtons("uploadScoresNextBtn");
	$("#downloadIconImage,#downloadFileName").hide();
	
	$("#downloadFileName,#scoringCompletedError,#multipleTestsError").empty();
	$.ajax({
		url: 'getStudentsToBeScored.htm',
        data: {
        	districtId : $("#uploadScoresDistrict").val(),
        	schoolIds : $('#uploadScoresSchool').val(),
        	contentAreaId:$("#uploadScoresContentAreas").val(),
        	gradeId :$("#uploadScoresGrades").val(),
        	stageId : $("#uploadScoresStage").val(),
        	testSessionIds : $('#uploadScoresTestSessions').val(),
        	includeItem : $('#uploadScoresIncludeItem').is(":checked")
        	},
        	dataType: 'json',
        type: "POST"
	}).done(function (data) { 
		$("#fileProcessingtext").hide();
    	enableButtons("uploadScoresNextBtn");
    	if(data['downloadScoreFileName'] != "" && data['downloadScoreFileName'] != null && data['downloadScoreFileName'] != undefined){
    		disableHref();
    		$("#downloadIconImage,#downloadFileName").show();
        	$("#downloadFileName").append(data.downloadScoreFileName);
        	$("#downloadFileNameHref").attr("href","getScoringAssignmentScoreFile.htm?fileName="+data['downloadScoreFileName']);
        	enableButtons("uploadScoresDownloadBtn");	        	
    	}else if(data['errorFound_scoringcompleted'] != "" && data['errorFound_scoringcompleted'] != null && data['errorFound_scoringcompleted'] != undefined){
    		disableHref();
    		$("#scoringErrorMessages").show();
    		$("#scoringCompletedError").append(data.errorFound_scoringcompleted).show();
    	}else if(data['errorFound_multipletests'] != "" && data['errorFound_multipletests'] != null && data['errorFound_multipletests'] != undefined){
    		disableHref();
    		$("#scoringErrorMessages").show();
    		$("#multipleTestsError").append(data.errorFound_multipletests).show();
    	}else if(data['errorFound_multiplecounttests'] == 'multiple_test'){
    		disableHref();
    		$("#scoringErrorMessages").show();        		
    		var multipleTestSessionName = [];
    		$('#uploadScoresTestSessions :selected').each(function(i, selected){ 
    			multipleTestSessionName[i] = $(selected).text();         		  
    		});
    		disableHref();
    		$("#multipleTestsError").append("Test Session "+multipleTestSessionName+" does not use the same items to score with all other selected tests. Please re-select and submit again.").show();
    	}
    	setTimeout(function() { 
			$("#scoringErrorMessages").hide();
		    }, 5000);
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	}).always(function(jqXHROrData, textStatus, jqXHROrErrorThrown){
		// Handle the beforeSend event
    	$("#fileProcessingtext").show();
	});
});

function scoringUploadScoresOnload(){
	//District onLoad
	uploadScoresDistrictLoad();

	// District OnChange
	$('#uploadScoresDistrict').on("change",function() {
		uploadScorerDistrictChangeEvent();
		refreshSelectBtn("uploadScoresSchool");
		refreshSelectBtn("uploadScoresContentAreas");
		refreshSelectBtn("uploadScoresGrades");
		refreshSelectBtn("uploadScoresStage");
		refreshSelectBtn("uploadScoresTestSessions");
		uploadScorerNextBtnDisable();
		disableHref();
	});
	
	
	//School OnChange
	$('#uploadScoresSchool').on("change",function() {
		uploadScoresSubjectLoad();
		refreshSelectBtn("uploadScoresContentAreas");
		refreshSelectBtn("uploadScoresGrades");
		refreshSelectBtn("uploadScoresStage");
		refreshSelectBtn("uploadScoresTestSessions");
		uploadScorerNextBtnDisable();
		disableHref();
	});
	
	// Content Area OnChange	
	$('#uploadScoresContentAreas').on("change",function() {
		uploadScorerSubjectChangeEvent();
		
		refreshSelectBtn("uploadScoresGrades");
		refreshSelectBtn("uploadScoresStage");
		refreshSelectBtn("uploadScoresTestSessions");
		
		uploadScorerNextBtnDisable();
		disableHref();
	});	

	// Grade OnChange	
	$('#uploadScoresGrades').on("change",function() {		
		refreshSelectBtn("uploadScoresStage");
		refreshSelectBtn("uploadScoresTestSessions");		
		uploadScoresStageLoad();
		uploadScoresTestSessionLoad();
		uploadScorerNextBtnDisable();
		disableHref();
	});	
	
	// Stage onChange
	$('#uploadScoresStage').on("change",function() {
		refreshSelectBtn("uploadScoresTestSessions");
		
		uploadScoresTestSessionLoad();
		uploadScorerNextBtnDisable();
		disableHref();
	});	
	
	// TestSessions onChange
	$('#uploadScoresTestSessions').on("change",function(){
		uploadScorerNextBtnDisable();
		disableHref();
	});
	
	
	// Load Grid
	$('#uploadScoreButton').off("click").on("click",function() {	
		if($('#uploadScoreButton').attr("disabled") == "disabled") {
			event.preventDefault();
		} else {
			
			uploadScoring();
			
		}
	}); 

	$('#uploadScoingForm').validate({
		ignore: "",
		rules: {
			uploadScoreFileDataInput: {
	      		required: true,
	      		extension: "csv"
	    	}
		},
		errorPlacement : function(error, element) {
			setTimeout(function() { 
				$('#errorplaceScoringdiv').html('');
			 $('#errorplaceScoringdiv').append(error);
			 
    		}, 500);
	        
	    }
	});

uploadScorerGrid();

$('input[id=uploadScoreFileData]').on("change",function() {	
	$('#uploadScoreFileDataInput').val($('#uploadScoreFileData')[0].files[0].name);
	if($('#uploadScoreFileDataInput').val()==''){		
		disableButtons("uploadScoreButton");
	}
	else{		
		enableButtons("uploadScoreButton");
	}
});

}


function uploadScorerNextBtnDisable(){	
	   if($("#uploadScoresDistrict").val() != "" &&  $("#uploadScoresSchool").val() != "" &&  $("#uploadScoresContentAreas").val() != ""
			&& $("#uploadScoresGrades").val() != "" && $("#uploadScoresTestSessions").val() != "" && $("#uploadScoresTestSessions").val() != null ){
			enableButtons("uploadScoresNextBtn");			
		} else{
			disableButtons("uploadScoresNextBtn");
		}
	
}

function refreshSelectBtn(id){
	$("#"+id).find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$("#"+id).val("0").trigger('change.select2');	
}

function uploadScoresDistrictLoad(){
	// onLoad District
	var districtOrgSelect = $('#uploadScoresDistrict');
	$.ajax({
		url: 'getOrgsBasedOnUserContext.htm',
		dataType: 'json',
		data: {
			orgId : $("#uploadScorerUserCurrentOrgId").val(),
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
				uploadScorerDistrictChangeEvent();
			} 
		} 
		$('#uploadScoresDistrict').trigger('change.select2');
		$('#uploadScoresSchool').val("0").trigger('change.select2');
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}

function disableHref(){
	$("#downloadFileNameHref").attr('href','#');	
}


function uploadScoresSubjectLoad(){
//onLoad Subject
	var schoolId = [];
	var assessmentProgId = $('#userDefaultAssessmentProgram').val();
	
    if($("#userCurrentOrganizationType").val() == 'SCH' && schoolId.length == 0){
    	schoolId[0] = $("#uploadScorerUserCurrentOrgId").val();
    }else{
    	schoolId = $('#uploadScoresSchool').val();
    }
    
	if(schoolId != 0 && schoolId != null){
		$.ajax({
			 url: 'getUploadScoreSubject.htm',
		     data: {
		    	assessmentProgramId: assessmentProgId,
		    	schoolId: schoolId
		    	},
		    dataType: 'json',
		    type: "POST"
		})
		.done(function (contentAreas) { 
			$('#uploadScoresContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
			$.each(contentAreas, function(i, contentArea) {
				$('#uploadScoresContentAreas').append($('<option></option>').attr("value", contentArea.id).text(contentArea.name));
			});
			
			if (contentAreas.length == 1) {
				$("#uploadScoresContentAreas option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#uploadScoresContentAreas").trigger('change');
			}
			$('#uploadScoresContentAreas').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
    }
}

function uploadScoresTestSessionLoad(){	
	//testSession onLoad
	$('#uploadScoresTestSessions').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#uploadScoresTestSessions').trigger('change.select2');	
	var assessmentProgId = $('#userDefaultAssessmentProgram').val();
	var subjectId = $('#uploadScoresContentAreas').val();
	var gradeId = $('#uploadScoresGrades').val();
	var stageId = $('#uploadScoresStage').val();
	var schoolId = [];
	schoolId = $('#uploadScoresSchool').val();
	
	$.ajax({
		url: 'getUploadScoresTestSessions.htm',
	    data: {
	    	assessmentProgramId: assessmentProgId,
	    	subjectId: subjectId,
	    	gradeId: gradeId,
	    	stageId: stageId,
	    	schoolIds: schoolId	    	
	    	},
	    dataType: 'json',
	    type: "POST"
	}).done(function (testSessionUploads) { 
		$('#uploadScoresTestSessions').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
		$.each(testSessionUploads, function(i, testSessionUpload) {
			$('#uploadScoresTestSessions').append($('<option></option>').attr("value", testSessionUpload.id).text(testSessionUpload.name));
		});
		if (testSessionUploads.length == 1) {
			$("#uploadScoresTestSessions").find('option:first').attr('selected', 'selected');
			$('#uploadScoresTestSessions').trigger('change.select2');
			uploadScorerNextBtnDisable();
		}
		$('#uploadScoresTestSessions').trigger('change.select2');
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown); 
	});
}

// Load Grid
$('#uploadScoreButton').off("click").on("click",function() {
	
	if($('#uploadScoreButton').attr("disabled") == "disabled") {
		
	} else {
		uploadScoring();
	}
}); 



function uploadScoresStageLoad(){	
	//stage onLoad
	$('#uploadScoresStage').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#uploadScoresStage').trigger('change.select2');	
	
	var schoolId = [];
	schoolId = $('#uploadScoresSchool').val();
	var subjectId = $('#uploadScoresContentAreas').val();	
	var gradeId = $('#uploadScoresGrades').val();
	$.ajax({
		url: 'getUploadScoresStage.htm',
	    data: {
	    	schoolId: schoolId,
	    	subjectId: subjectId,
	    	gradeId: gradeId
	    	},
	    dataType: 'json',
	    type: "POST",
	}).done(function (stageScores) { 
		$('#uploadScoresStage').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
		$.each(stageScores, function(i, stageScore) {
			$('#uploadScoresStage').append($('<option></option>').attr("value", stageScore.id).text(stageScore.name));
		});
		
		if (stageScores.length == 1) {
			$("#uploadScoresStage option").removeAttr('selected').next('option').attr('selected', 'selected');
			$("#uploadScoresStage").trigger('change');
		}
		$('#uploadScoresStage').trigger('change.select2');
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});

	
$('#uploadScoingForm').validate({
	ignore: "",
	rules: {
		uploadScoreFileDataInput: {
      		required: true,
      		extension: "csv"
    	}
	},
	errorPlacement : function(error, element) {
		setTimeout(function() { 
			$('#errorplaceScoringdiv').html('');
			$('#errorplaceScoringdiv').append(error);
			
		}, 500);
        
    }
});

uploadScorerGrid();
$('input[id=uploadScoreFileData]').on("change",function() {
	$('#uploadScoreFileDataInput').val($('#uploadScoreFileData')[0].files[0].name);
	if($('#uploadScoreFileDataInput').val()==''){		
		disableButtons("uploadScoreButton");
	}
	else{		
		enableButtons("uploadScoreButton");
	}
});
}

function uploadScoring(){
	 
	var fd = new FormData();
	var date = new Date();
	var milliSec =date.getMilliseconds();
	var filedata = $('#uploadScoreFileData');
	var filelist = filedata[0].files;
	var file = filelist[0];
	if($('#uploadScoingForm').valid()) {		
		disableButtons("uploadScoreButton");
		fd.append('assessmentProgramId',assessmentProgId);
		fd.append('categoryCode',scoringuploadCategoryCode);
		fd.append('reportUpload',"false");
		fd.append('date', date.getTime());
		fd.append('milliSec',milliSec);
		fd.append('uploadFile',file);
		$.ajax({
			url: 'uploadFileData.htm',
			data: fd,
			dataType: 'json',
			processData: false,
			contentType: false,
			cache: false,
			type: 'POST'
		}).done(function (data) { 
			loadScoringUploadData();
			if(data.showWarning) {
				enableButtons("uploadScoreButton");
	    	} else if(data.errorFound) {
	    		
				enableButtons("uploadScoreButton");	    		
            } 
	    	else if(data.nopermit){
	    		enableButtons("uploadScoreButton");		    		
	    	}
	    	else {
	    		monitorScoringFile(data.uploadId);
	    	}
			$('#uploadScoreFileDataInput').val('');
			
			var fileInput = $('#uploadScoreFileData');
			fileInput.replaceWith(fileInput.val('').clone(true));
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
	} 
}

function loadScoringUploadData(){
	  $.ajax({
		  url: "getabUploadProgressStatus.htm?&categoryCode="+scoringuploadCategoryCode,
	       type: "POST",
	       success:function(data) {
	            $('#uploadScoresDataGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	          
	       }
	    });

	}
function disableButtons(id){	
	$("#"+id).prop("disabled",true);
	$("#"+id).addClass('ui-state-disabled');
}

function enableButtons(id){
	$("#"+id).prop("disabled",false);
	$("#"+id).removeClass('ui-state-disabled');
}

function monitorScoringFile(uploadFileRecordId){
	$.ajax({
		url: 'monitorUploadFileStatus.htm',
        type: 'GET',
        cache: false,
        data: {uploadFileRecordId: uploadFileRecordId}
	}).done(function (data) { 
		if(data.uploadFileStatus === 'FAILED' || data.errorFound){
    		$('uploadScoreFileDataInput').val();
    		  loadScoringUploadData();
    		  enableButtons(uploadScoreButton);
      	} else if(data.uploadFileStatus === 'COMPLETED'){
      		$('uploadScoreFileDataInput').val();
      		  loadScoringUploadData();
      		enableButtons(uploadScoreButton);
    		
		} else {
			if(data.uploadFileStatus === "IN_PROGRESS"){
				$('#uploadScoresDataGridTableId').jqGrid("setCell", uploadFileRecordId, "status", data.status.charAt(0).toUpperCase() + data.status.toLowerCase().slice(1));
			}
			window.setTimeout(function(){monitorScoringFile(uploadFileRecordId);}, 9000);
		}  
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown); 
	});
}

function uploadScorerGrid(){ 

	  var uploadUsergrid = $('#uploadScoresDataGridTableId');
	  $("#uploadScoresDiv div").show();
	  var colModel = [
	                  {label: 'id', name: 'id', hidden: true, hidedlg: true, sorttype: 'int'},
	                  {label: 'Date', name: 'date', index: 'date', width:'120px', hidedlg: true, formatoptions: {newformat: 'm/d/Y'},
	                   formatter: function(cellValue, options, rowObject, action){
	                    return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                   }
	                  },
	                  {label: 'Time', name: 'time', index: 'time',width:'120px',  hidedlg: true, formatoptions: {newformat: 'h:i:s A'},
	                      formatter: function(cellValue, options, rowObject, action){
	                       return $.fn.fmatter.call(this, 'date', new Date(cellValue), $.extend({}, $.jgrid.formatter.date, options), rowObject, action);
	                      }
	                     },
	                  {label: 'Status', name: 'status', index: 'status',width:'450px',  hidedlg: true,  formatter: scoresUploadSatusFormatter},
	                  {label: 'File', name: 'filePath', index: 'filePath', hidedlg: true,width:'120px',  formatter:extractScoresUploadErrorLinkFormatter},
	                  {label: 'Organization Name', name: 'organizationName', index: 'organizationName', hidedlg: true,hidden:true},
	                  {label: 'Failed Count', name: 'failedCount', index: 'failedCount', hidedlg: true,hidden:true},
	                  {label: 'statusCheck', name: 'statusCheck', index: 'statusCheck', hidedlg: true,hidden:true}
	                 ];

	 $(uploadUsergrid).jqGrid({
	  datatype: 'local',
	  width: '740px',
	  height: 'auto',
	  filterstatesave: true,
	  pagestatesave: true,
	  colModel: colModel,
	  filterToolbar: false,
	  rowNum: 10,
	  rowList: [5, 10, 20, 30, 40, 60, 90],
	  columnChooser: false, 
	     multiselect: false,
	  footerrow : true,
	  pager: '#uploadScoresDataGridPager',
	  sortname: 'id',
	  sortorder: 'DESC',
	  altclass: 'altrow',
	  altRows: true,
	  jsonReader: {
	         page: function (obj) {
	             return obj.page !== undefined ? obj.page : "0";
	         },
	         repeatitems:false,
	      root: function(obj) { 
	       return obj.rows;
	      } 
	     }
	  });
}

function uploadScorerSubjectChangeEvent(){
	$('#uploadScoresGrades').trigger('change.select2');
	var contentAreaId = $('#uploadScoresContentAreas').val();
	var schoolId = [];
	
		 if($("#userCurrentOrganizationType").val() == 'SCH' && schoolId.length == 0){
		    	schoolId[0] = $("#uploadScorerUserCurrentOrgId").val();
		    }else{
		    	schoolId = $('#uploadScoresSchool').val();
		    }	
		
		
	if (contentAreaId != 0) {
		$.ajax({
			url: 'getUploadScoresGradeBySubjectId.htm',
	        data: {
	        	subjectId: contentAreaId,
	        	schoolId : schoolId},
	        dataType: 'json',
	        type: "GET"
		}).done(function (grades) { 
			$('#uploadScoresGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();				        	
			$.each(grades, function(i, grade) {
				$('#uploadScoresGrades').append($('<option></option>').attr("value", grade.id).text(grade.name));
			});
								
			if (grades.length == 1) {
				$("#uploadScoresGrades option").removeAttr('selected').next('option').attr('selected', 'selected');
				$("#uploadScoresGrades").trigger('change');
			}				
			$('#uploadScoresGrades').trigger('change.select2');
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown);
		});
	}
}



function uploadScorerDistrictChangeEvent(){	
	var districtOrgId = $('#uploadScoresDistrict').val();
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
			$('#uploadScoresSchool').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$.each(schoolOrgs, function(i, schoolOrg) {
				$('#uploadScoresSchool').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
			});		
			if (schoolOrgs.length == 1) {
				$("#uploadScoresSchool").find('option:first').attr('selected', 'selected');
				$('#uploadScoresSchool').trigger('change.select2');
			}
			$('#uploadScoresSchool').trigger('change.select2');
			filteringOrganization($('#uploadScoresSchool'));
		}).fail(function (jqXHR, textStatus, errorThrown) { 
			console.log(errorThrown); 
		});
	}	
}
function scoresUploadSatusFormatter(cellValue, options, rowObject){
	if( cellValue == "COMPLETED"){
		cellValue = 'Completed: Records Created/Updated: ' + rowObject.successCount + " Rejected: "+rowObject.failedCount + " Alerts: "+rowObject.alertCount;
	}
	return cellValue;
} 

function extractScoresUploadErrorLinkFormatter(cellValue, options, rowObject){
	var status = rowObject.statusCheck;
	if ((status == "COMPLETED" && rowObject.failedCount > 0 || rowObject.alertCount > 0) || (status == "FAILED" ) ) {
		var dataOrganizationName=rowObject.organizationName;
		var currDate = new Date();
		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
		var file = rowObject.fileName.split(".",1);
		var fileName = file +"_UserUpload_Errors_"+dateStringForFile+".csv";
		return '<a href="getUploadErrorFile.htm?uploadedId='+rowObject.id + '&fileName='+fileName+'"><img src="images/icon-csv-green.png" title="Click to download extract."/> </a>';
	}
	else
		return '<a> </a>';
}
