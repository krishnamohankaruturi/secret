var orgHierarchies = {};
var selectedStudentArr = []	;
var childOrg = 20;


$(function(){
	$('#testRecordsNav li.nav-item:first a').tab('show');
	 $('li.get-testRecords').on('click',function(e){
	    	var clickedURL = $(this).find("a").attr('href');     
	    	testRecordInit(clickedURL.substring(1, clickedURL.length));
	        e.preventDefault(); // same to return false; 
	    });  
		
		var testRecordsNavMenu = $('#testRecordsNav li.nav-item:first a');
		if(testRecordsNavMenu.length>0){
			var clickedURL = testRecordsNavMenu.attr('href');     
			testRecordInit(clickedURL.substring(1, clickedURL.length));
		}    
});


function testRecordInit(tabId) {
	// SIGN IN
	$('#testRecordsActions').val("");
			
		if (tabId == "createTestRecords"){
			aart.clearMessages();
			selectedStudentArr = []	;
			$("#viewTestRecordDiv").hide();
			$("#createTestRecordDiv").show();
			$("#tecUploadDiv").hide();
			$("#searchFilterCreateTestRecordContainer").show();
			$("#studentCreateTestRecord").hide();
			$("#selectTestRecordDiv").hide();
			$("#clearTestRecordDiv").hide();
			$("#searchFilterClearTestRecordContainer").hide();
			$("#schoolOrgsCreateTestRecord").val("").trigger('change.select2');
			$("#districtOrgsCreateTestRecord").val("").trigger('change.select2');
			$("#schoolOrgsCreateTestRecord").val("").trigger('change.select2');
			$("#subjectSelectTestRecord").val("").trigger('change.select2');
			$("#testTypeSelectTestRecord").val("").trigger('change.select2');
			$('#assessmentProgramsCreateTestRecord').trigger('change.select2');
			$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			$("label.error").hide();
			  $(".error").removeClass("error");
			  testRecordsGridsReset();
			createTestRecordInitMethod();
		}else if(tabId == "clearTestRecords"){
			testRecordsGridsReset();
			$("#viewTestRecordDiv").hide();
			clearTestRecordInitMethod();
		}else if(tabId == "viewTestRecords"){
			aart.clearMessages();
			$("#createTestRecordDiv").hide();
			$("#clearTestRecordDiv").hide();
			$("#tecUploadDiv").hide();			
			$("#viewTestRecordDiv").show();
			testRecordsGridsReset();
			viewTestRecordInitMethod();
		}else if(tabId == "uploadTEC") {
			if(!gUploadTECLoadOnce){
				uploadTecInit();
			}else{
	   			$('#uploadTecOrgFilter').orgFilter('reset');
	   			if($("#tecGrid")[0].grid && $("#tecGrid")[0]['clearToolbar']){
					$("#tecGrid")[0].clearToolbar();
				}
	   			$('#tecGrid').jqGrid('clearGridData');
			}
			$(".QuickHelpHint").hide();
			$("#TEC_uploadTemplatedownloadquickHelpPopup").hide();
			$('#uploadTecOrgFilterForm')[0].reset();
			$('#breadCrumMessage').text("Configuration: Students - Upload Test, Exit, and Clear");
			$('#tecUploadDiv').hide();
			$("#createTestRecordDiv").hide();
			$("#clearTestRecordDiv").hide();
			$("#viewTestRecordDiv").hide();
			$('#tecUploadReport').html('');
			$("label.error").html('');
	   		$('#tecUploadReportDetails').html('');
			$('#tecUploadDiv').show();			
			$("#testRecordARTSmessages").show();			
			setTimeout("aart.clearMessages()", 5000);
			setTimeout(function(){ $("#testRecordARTSmessages").hide(); },5000);
			testRecordsGridsReset();
			loadUploadTECData();
		} else {
			aart.clearMessages();
			$("#createTestRecordDiv").hide();
			$("#clearTestRecordDiv").hide();
			$("#viewTestRecordDiv").hide();
			$('#tecUploadDiv').hide();
			testRecordsGridsReset();
		}
}

function testRecordsGridsReset(){
	$('#viewTestRecordStudentsByOrgTableId').jqGrid('clearGridData');
	$('#viewStudentTestRecordStudentsByOrgTableId').jqGrid('clearGridData');
	$("#createTestRecordStudentsByOrgTableId").jqGrid('clearGridData');
	$("#clearTestRecordStudentsByOrgTableId").jqGrid('clearGridData');	
}

function resetTestRecord(){
	$("#testRecordsActions").select2();
	var options = sortDropdownOptions($("#testRecordsActions option"));
	options.appendTo("#testRecordsActions");
	
	$('#testRecordsActions').val("").trigger('change.select2');
	$("#createTestRecordDiv").hide();
	$("#clearTestRecordDiv").hide();
	$("#viewTestRecordDiv").hide();
	$("#tecUploadDiv").hide();	
	$("#schoolOrgsCreateTestRecord").val("").trigger('change.select2');
	$("#districtOrgsCreateTestRecord").val("").trigger('change.select2');
	$("#schoolOrgsCreateTestRecord").val("").trigger('change.select2');
	$('#assessmentProgramsCreateTestRecord').trigger('change.select2');
	$("#subjectSelectTestRecord").val("").trigger('change.select2');
	$("#testTypeSelectTestRecord").val("").trigger('change.select2');
	$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
	aart.clearMessages();
}

function createTestRecordInitMethod(){
	
	$('#stateOrgsCreateTestRecord, #districtOrgsCreateTestRecord,#schoolOrgsCreateTestRecord,#assessmentProgramsCreateTestRecord,#subjectSelectTestRecord,#testTypeSelectTestRecord').select2({
		placeholder:'Select',
		multiple: false,
		allowClear : true
	});
	
	$("#confirmDialogSelectTestRecord").dialog({
		  autoOpen: false,
		  modal: true
	});
	
	setInSessionStorage("tmStateOrgId", $('#stateOrgsCreateTestRecord').val());
	setInSessionStorage("tmAssessmentProgramId", $('#assessmentProgramsCreateTestRecord').val());
	setInSessionStorage("tmDistrictOrgId", $('#districtOrgsCreateTestRecord').val());
	setInSessionStorage("tmSchoolOrgId", $('#schoolOrgsCreateTestRecord').val());
	setInSessionStorage("tmsubject", $('#subjectSelectTestRecord').val());
	setInSessionStorage("tmTestType", $('#testTypeSelectTestRecord').val());
	viewCreateTestRecordWindowGrid();
	populateTestRecordAssessmentProgram();
	populateCTRSearchFiltersData();
	
	$('#searchBtnCreateTestRecord').off('click').on('click',function(e){
			e.preventDefault();
			if($('#searchFilterCreateTestRecordForm').valid()){
			$("#createTestRecordStudentsByOrgTableId").jqGrid('clearGridData');	
			$("#searchFilterCreateTestRecordContainer").hide();
			$("#studentCreateTestRecord").show();

			var assessmentProgramId = $('#assessmentProgramsCreateTestRecord').val();
			var orgChildrenIds = 0;
			if( $('#schoolOrgsCreateTestRecord').val() == ''){
				
			  orgChildrenIds = $('#districtOrgsCreateTestRecord').val();
			}else{
				
			  orgChildrenIds = $('#schoolOrgsCreateTestRecord').val();
			}
			
		    var $gridAuto = $("#createTestRecordStudentsByOrgTableId");
		    $gridAuto.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getCreateTestRecordStudentsGridData.htm', 
			search: false, 
			postData: { "filters": "",
				         "assessmentProgramId": assessmentProgramId,
				         "orgChildrenIds": orgChildrenIds}
			}).trigger("reloadGrid",[{page:1}]);
		     
		 	$('#createTestRecordGridTopNextBtn,#createTestRecordGridBottomNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
		}
			
	});

	$('#searchFilterCreateTestRecordForm').validate({
		ignore: "",
		rules: {
			assessmentProgramsCreateTestRecord: {required: true},
			stateOrgsCreateTestRecord: {required: true},
			districtOrgsCreateTestRecord: {required: true}
		}
	});
	
	$('#createTestRecordGridTopNextBtn,#createTestRecordGridBottomNextBtn').off('click').on('click',function(e) {
		e.preventDefault();
		showSelectTestRecordDiv();
		$('#testTypeSelectTestRecord').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#testTypeSelectTestRecord').trigger('change.select2');
		$('#testTypeSelectTestRecord').html("");
		$('#testTypeSelectTestRecord').append($('<option></option>').val("").html("Select"));
		populateSubject();
		//populateTestType();
		
	});
	
	$('#testTypeSelectTestRecord').on("change",function(event) {
			
			var testType = $('#testTypeSelectTestRecord').val();
			var testSubject = $('#subjectSelectTestRecord').val();
			
			if(testType != "" && testType != " " && testType != "undefined" && testSubject != "" && testSubject != " " && testSubject != "undefined" ){
				$('#yesBtnSelectTestRecord,#cancelBtnSelectTestRecord').removeAttr('disabled').removeClass('ui-state-disabled');
			}
			else
				$('#yesBtnSelectTestRecord,#cancelBtnSelectTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
	});

 	$("#cancelBtnSelectTestRecord").off('click').on('click',function(e) {
		
		e.preventDefault();
		$("#confirmDialogSelectTestRecord").dialog({
			create: function(event, ui) { 
		    	var widget = $(this).dialog("widget");
			},	
		  buttons : {
			"Yes" : function() {
				$(this).dialog("close");
				selectedStudentArr = []	;
				$("#createTestRecordDiv").show();
				$("#searchFilterCreateTestRecordContainer").show();
				$("#studentCreateTestRecord").hide();
				$("#selectTestRecordDiv").hide();
				$("#stateOrgsCreateTestRecord").val("").trigger('change.select2');
				$("#districtOrgsCreateTestRecord").val("").trigger('change.select2');
				$("#schoolOrgsCreateTestRecord").val("").trigger('change.select2');
				$('#assessmentProgramsCreateTestRecord').trigger('change.select2');
				$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
				$("label.error").hide();
				  $(".error").removeClass("error");
				createTestRecordInitMethod();
				
			},
			"No" : function(){
			 
			  $('#confirmDialogSelectTestRecord').dialog('close');
			}
		  }
		});
		$("#confirmDialogSelectTestRecord").dialog("open");
	}); 
	
 	$("#yesBtnSelectTestRecord").off('click').on('click',function(e) {
 		e.preventDefault();
		saveSelectedStudentTestRecord();
		e.preventDefault();
	});

 	filteringOrganization($('#stateOrgsCreateTestRecord'));

 	filteringOrganization($('#districtOrgsCreateTestRecord'));

 	filteringOrganization($('#schoolOrgsCreateTestRecord'));
 	
}

function saveSelectedStudentTestRecord(){

	var selectedStudnetArrayList = {};
	selectedStudnetArrayList.stateStudentIdentifier =[];
	selectedStudnetArrayList.attendanceSchoolDisplayIdentifiers = [];
	selectedStudnetArrayList.currentSchoolYears =[];
	
	var orgChildrenIds = 0;
	if( $('#schoolOrgsCreateTestRecord').val() == ''){
		
	  orgChildrenIds = $('#districtOrgsCreateTestRecord').val();
	}else{
		
	  orgChildrenIds = $('#schoolOrgsCreateTestRecord').val();
	}
	var studentIdentifer = [];
	var attendanceIdentifier = [];
	var schoolYear = [];
	var k=0;
	for(var i=0; i < selectedStudentArr.length; i++){
				studentIdentifer = selectedStudentArr[i].stateStudentIdentifier;
				attendanceIdentifier = selectedStudentArr[i].attendanceSchoolDisplayIdentifiers.split(",");
				schoolYear = selectedStudentArr[i].currentSchoolYears.split(",");
				if(attendanceIdentifier.length > 1)
					{
						for(j=0;j < attendanceIdentifier.length; j++ )
							{
								selectedStudnetArrayList.stateStudentIdentifier[k] = selectedStudentArr[i].stateStudentIdentifier;
								selectedStudnetArrayList.attendanceSchoolDisplayIdentifiers[k] = attendanceIdentifier[j];
								selectedStudnetArrayList.currentSchoolYears[k] = schoolYear[j];
								k++;
							}
						
					}
				else{
				 selectedStudnetArrayList.stateStudentIdentifier[k] = selectedStudentArr[i].stateStudentIdentifier;
				 selectedStudnetArrayList.attendanceSchoolDisplayIdentifiers[k] = selectedStudentArr[i].attendanceSchoolDisplayIdentifiers;
				 selectedStudnetArrayList.currentSchoolYears[k] = selectedStudentArr[i].currentSchoolYears;
				k++;
				}
		}
	
	var recordType ="Test";
	var selectedSubjectType = $('#subjectSelectTestRecord').val();
	var selectedTestType = $('#testTypeSelectTestRecord').val();
	$.ajax({
		url: 'createTestRecord.htm',
		dataType: 'json',
		type: "POST",
		data:{
			"testSubject":selectedSubjectType,
			"testType":selectedTestType,
			"studentsList":selectedStudnetArrayList,
			"orgChildrenIds" : orgChildrenIds,
			"recordType" : recordType
			}
	}).done(function(data) {
		aart.clearMessages();
		selectedStudentArr = []	;
		$("#createTestRecordDiv").show();
		$("#searchFilterCreateTestRecordContainer").show();
		$("#searchFilterCreateTestRecordErrors").show();
		$("#studentCreateTestRecord").hide();
		$("#selectTestRecordDiv").hide();
		$("#stateOrgsCreateTestRecord").val("").trigger('change.select2');
		$("#districtOrgsCreateTestRecord").val("").trigger('change.select2');
		$("#schoolOrgsCreateTestRecord").val("").trigger('change.select2');
		$("#subjectSelectTestRecord").val("").trigger('change.select2');
		$("#testTypeSelectTestRecord").val("").trigger('change.select2');
		$('#assessmentProgramsCreateTestRecord').trigger('change.select2');
		
		$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
		$("label.error").hide();
		  $(".error").removeClass("error");
		createTestRecordInitMethod();
	if(data.result == "success"){			
		$("#createRecordSuccessMessage").show();
		$("#viewRecordSuccessMessage").show();				
		setTimeout("aart.clearMessages()", 5000);
		setTimeout(function(){ $('#createRecordSuccessMessage').hide(); },5000); 
		setTimeout(function(){ $('#viewRecordSuccessMessage').hide(); },5000);    	
	}
	else{
		
		$("#viewRecordFailMessage").show();
		$("#createRecordFailMessage").show();
		setTimeout("aart.clearMessages()", 5000);
		setTimeout(function(){ $('#createRecordFailMessage').hide(); },5000);
		setTimeout(function(){ $('#viewRecordFailMessage').hide(); },5000);
	}
	
});
	
}

function showSelectTestRecordDiv(){
	
	$("#selectTestRecordDiv").show();
	$("#studentCreateTestRecord").hide();
	$('#yesBtnSelectTestRecord,#cancelBtnSelectTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');

}

function populateSubject(){
	
	var selectSubject = $('#subjectSelectTestRecord'), selectSubjectoptionText='';
	var assessmentProgramId = $('#assessmentProgramsCreateTestRecord').val();
	
	selectSubject.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	selectSubject.trigger('change.select2');
	selectSubject.html("");
	selectSubject.append($('<option></option>').val("").html("Select"));
	
	$.ajax({
		url: 'getContentAreasByAssessmentProgramForTestRecord.htm',
		dataType: 'json',
		type: "POST",
		data:{"assessmentProgramId":assessmentProgramId}
	}).done(function(subjects) {				
		if (subjects !== undefined && subjects !== null && subjects.length > 0) {
			$.each(subjects, function(i, subject) {
				subabbrname = subject.abbreviatedName
					selectSubjectoptionText = subjects[i].name;
					selectSubject.append($('<option></option>').val(subject.abbreviatedName).html(selectSubjectoptionText));
			});
			if (subjects.length == 1) {
				selectSubject.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				$("#subjectSelectTestRecord").trigger('change');
			}
		} 
		$('#subjectSelectTestRecord,#testTypeSelectTestRecord').trigger('change.select2');
	});	
	
	$("#subjectSelectTestRecord").on("change",function() {
	
		$('#testTypeSelectTestRecord').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#testTypeSelectTestRecord').trigger('change.select2');
		$('#testTypeSelectTestRecord').html("");selectTestTypeoptionText = '';
		$('#testTypeSelectTestRecord').append($('<option></option>').val("").html("Select"));
		var subjectCode = $('#subjectSelectTestRecord').val();
		var assessmentProgramId = $('#assessmentProgramsCreateTestRecord').val();
		if(subjectCode == undefined || subjectCode == ""){
		
			$('#yesBtnSelectTestRecord,#cancelBtnSelectTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			$("#testTypeSelectTestRecord").val("").trigger('change.select2');
			return false;
		}
		
		$('#yesBtnSelectTestRecord,#cancelBtnSelectTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
		
		$.ajax({
			url: 'getTestTypeBySubject.htm',
			dataType: 'json',
			type: "POST",
			data:{
					"assessmentProgramId" :assessmentProgramId,
					"subjectCode":subjectCode
					
				}
		}).done(function(testTypes) {
			if (testTypes !== undefined && testTypes !== null && testTypes.length > 0) {
				$.each(testTypes, function(i, testtype) {
					selectTestTypeoptionText = testTypes[i].testTypeName;
					$("#testTypeSelectTestRecord").append($('<option></option>').val(testtype.testTypeCode).html(selectTestTypeoptionText));
				});
				
				if (testTypes.length == 1) {
					$("#testTypeSelectTestRecord").find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					
				}
			}
			$("#testTypeSelectTestRecord").trigger('change');
			$('#testTypeSelectTestRecord').trigger('change.select2');
		});
		
		if($('#testTypeSelectTestRecord').val() != "" && $('#testTypeSelectTestRecord').val() != " " && $('#testTypeSelectTestRecord').val() != "undefined" && subjectCode != "" && subjectCode != " " && subjectCode != "undefined" ){
			$('#yesBtnSelectTestRecord,#cancelBtnSelectTestRecord').removeAttr('disabled').removeClass('ui-state-disabled');
		}
		else
			$('#yesBtnSelectTestRecord,#cancelBtnSelectTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
		
	});
	
}

function populateSelect($select, data, idProp, textProp){
	if (data !== undefined && data !== null && data.length > 0){
		for (var x = 0; x < data.length; x++){
			$select.append($('<option></option>').val(data[x][idProp]).text(data[x][textProp]));
		}
		$select.trigger('change.select2');
		return true;
	}
	return false;
}

function populateTestRecordAssessmentProgram(){

	var vowAPSelect = $('#assessmentProgramsCreateTestRecord'), vowOptionText='';
//	vowAPSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	$('#assessmentProgramsCreateTestRecord').html("");
	$('#assessmentProgramsCreateTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(vowAssessmentPrograms) {				
		if (vowAssessmentPrograms !== undefined && vowAssessmentPrograms !== null && vowAssessmentPrograms.length > 0) {
			$.each(vowAssessmentPrograms, function(i, vowAssessmentProgram) {
				vowOptionText = vowAssessmentPrograms[i].programName;
				if(vowAssessmentProgram.assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
					vowAPSelect.append($('<option selected=\''+'selected'+'\'></option>').val(vowAssessmentProgram.id).html(vowOptionText));
				} else {
					vowAPSelect.append($('<option></option>').val(vowAssessmentProgram.id).html(vowOptionText));
				}
			});
			$('#assessmentProgramsCreateTestRecord').trigger('change');
		} 
		$('#assessmentProgramsCreateTestRecord').trigger('change.select2');
	});
	
}

function populateCTRSearchFiltersData(){

		var stateOrgSelect = $('#stateOrgsCreateTestRecord');
		stateOrgSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$('#stateOrgsCreateTestRecord').html("");
		$('#stateOrgsCreateTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		$('#districtOrgsCreateTestRecord').html("");
		$('#districtOrgsCreateTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		$('#schoolOrgsCreateTestRecord').html("");
		$('#schoolOrgsCreateTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');
	
			
		$.ajax({
	        url: 'getStatesOrgsForUser.htm',
	        data: { },
	        dataType: 'json',
	        type: "GET"
		}).done(function(states) {
        	if (states !== undefined && states !== null && states.length > 0) {
				$.each(states, function(i, stateOrg) {
					optionText = states[i].organizationName;
					stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(optionText));
				});
				
				var filterSelectedValue = getFromSessionStorage("tmStateOrgId");
				if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null && filterSelectedValue != "") {	
					stateOrgSelect.val(filterSelectedValue);
					$("#stateOrgsCreateTestRecord").trigger('change');
				} else if (states.length == 1) {
					stateOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$("#stateOrgsCreateTestRecord").trigger('change');
				}
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
			}
        	
        	$('#schoolOrgsCreateTestRecord, #districtOrgsCreateTestRecord,#stateOrgsCreateTestRecord').trigger('change.select2');
        });
		
	$('#stateOrgsCreateTestRecord').on("change",function(event) {
		var districtOrgSelect = $('#districtOrgsCreateTestRecord');
	//	districtOrgSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$('#districtOrgsCreateTestRecord').html("");
		$('#districtOrgsCreateTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

		
		$('#schoolOrgsCreateTestRecord').html("");
		$('#schoolOrgsCreateTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

					var stateOrgId = $('#stateOrgsCreateTestRecord').val();
		if(stateOrgId == "" || stateOrgId =="undefined")
			{
				$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
				$("#districtOrgsCreateTestRecord").val("").trigger('change.select2');
				$("#schoolOrgsCreateTestRecord").val("").trigger('change.select2');
				$('#assessmentProgramsCreateTestRecord').trigger('change.select2');
			return false;
			}
	
		$.ajax({
			url: 'getOrgsBasedOnUserContext.htm',
			dataType: 'json',
			data: {
				orgId : stateOrgId,
	        	orgType:'DT',
	        	orgLevel: 50	
	        	},				
			type: "GET"
		}).done(function(districtOrgs) {
			//		districtOrgSelect.empty();
			if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
				$.each(districtOrgs, function(i, districtOrg) {
					optionText = districtOrgs[i].organizationName;
					districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
				});
				var filterSelectedValue = getFromSessionStorage("tmDistrictOrgId");	
				if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null && filterSelectedValue != '') {							
					districtOrgSelect.val(filterSelectedValue);
					$("#districtOrgsCreateTestRecord").trigger('change');
				} else if (districtOrgs.length == 1) {
					districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
					$("#districtOrgsCreateTestRecord").trigger('change');
				}
			} else {
				$('body, html').animate({scrollTop:0}, 'slow');
				$('#searchFilterCreateTestRecordErrors').html("No District Organizations Found for the current user").show();
			}
			$('#schoolOrgsCreateTestRecord, #districtOrgsCreateTestRecord').trigger('change.select2');
		});
		enableDisableCreateTestRecordSearchBtn();
	});
		
	$('#districtOrgsCreateTestRecord').on("change",function(event) {
		schoolSelect=$('#schoolOrgsCreateTestRecord');
		$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
		$('#schoolOrgsCreateTestRecord').html("");
		$('#schoolOrgsCreateTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');
		
		
			var districtOrgId = $('#districtOrgsCreateTestRecord').val();
			createSchoolOptionText='';
			var assessmentId = $('#assessmentProgramsCreateTestRecord').val();			
			if (districtOrgId != 0) {
				$.ajax({
			        url: 'getOrgsBasedOnUserContext.htm',
			        data: {
			        	orgId : districtOrgId,
			        	orgType:'SCH',
			        	orgLevel: 70	
			        	},
			        dataType: 'json',
			        type: "GET"
				}).done(function(schoolOrgs) {				
					if (schoolOrgs !== undefined && schoolOrgs !== null && schoolOrgs.length > 0) {
						$.each(schoolOrgs, function(i, schoolOrg) {
							createSchoolOptionText = schoolOrgs[i].organizationName;
							schoolSelect.append($('<option></option>').val(schoolOrg.id).html(createSchoolOptionText));
						});
						if (schoolOrgs.length == 1) {
							schoolSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
							$("#schoolOrgsCreateTestRecord").trigger('change');
						}
					} 
				
					$('#schoolOrgsCreateTestRecord').trigger('change.select2');
		        });
			}
			enableDisableCreateTestRecordSearchBtn();
		});	

	$('#assessmentProgramsCreateTestRecord').on("change",function(event) {
		
		enableDisableCreateTestRecordSearchBtn();		
	});
}

function viewCreateTestRecordWindowGrid(){
	
	var grid_width = $('.kite-table').width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 900;				
	}
    var cell_width = grid_width/5;
	
    selectedStudentArr = [];
var $gridAuto = $("#createTestRecordStudentsByOrgTableId");

var cmforVuewStudentsCTR = [
		
		{ name : 'legalFirstName', index : 'legalFirstName', label : 'legalFirstNameTestRecords', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'legalMiddleName', index : 'legalMiddleName', width : cell_width,sortable : false, hidden : false,search : false, hidedlg : true},
		{ name : 'legalLastName', index : 'legalLastName', label : 'legalLastNameTestRecords', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', label : 'stateStudentIdentifierTestRecords' , width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name: 'currentSchoolYears',index: 'currentSchoolYears', width:cell_width, sortable : true, search : false, hidden: false,hidedlg : true},
		{ name: 'attendanceSchoolNames',index: 'attendanceSchoolNames', width:cell_width, sortable : false, search : false, hidden: false,hidedlg : false},
		{ name: 'gradeCourseName',index: 'gradeCourseName', width:cell_width, sortable : false, search : false, hidden: false,hidedlg : false},
		{ name: 'attendanceSchoolDisplayIdentifiers',index: 'attendanceSchoolDisplayIdentifiers', width:cell_width, sortable : false, search : false, hidden: true,hidedlg : true}
	];

//	$gridAuto.jqGrid('GridUnload');					

//JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: grid_width,
		colNames : [
					'First Name', 'Middle Name', 'Last Name','State Id','School Year','School Name','Grade','attendanceSchoolId'
					],
		colModel :cmforVuewStudentsCTR,	
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		height : 'auto',
		pager : '#pcreateTestRecordStudentsByOrgTableId',
		// F-820 Grids default sort order
		sortname : 'legalLastName,legalFirstName,legalMiddleName',
		multiselect: true,
		pagestatesave:true,
		filterstatesave:false,
	    beforeRequest: function() {    	
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');
	
	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	        
	    },localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },jsonReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        },
	        repeatitems:false,
	    	root: function(obj) { 
	    		return obj.rows;
	    	} 
	    },
	    onSelectRow: function(rowid, status, e){
	    	 var rData = $('#createTestRecordStudentsByOrgTableId').jqGrid('getRowData',rowid);
	    	
	    	
	    	 var studentObj =	{ stateStudentIdentifier : rData.stateStudentIdentifier ,
	    			 			  attendanceSchoolDisplayIdentifiers : rData.attendanceSchoolDisplayIdentifiers , 
	    			 			 currentSchoolYears : rData.currentSchoolYears,
	    			 			 id : rowid
   		  		    };
	    	
	    	if(status){
	    		var found = false; 
	    		$.each(selectedStudentArr, function(i){
       			    if(selectedStudentArr[i].id === rowid) {
       			    	found = true;
       			    }
       			 }); 
	    		 if( ! found )
	    			 selectedStudentArr.push(studentObj);
         		  var grid = $(this);
         		  var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox", grid);
         		   //if selected row and checkbox is disabled, do not let it get checked.
         		   if(cbsdis.is(":disabled")){
         			  cbsdis.prop('checked', false);         			   
         		   }
         	   } else{
         		   $.each(selectedStudentArr, function(i){
         			    if(selectedStudentArr[i].id === rowid) {
         			    	selectedStudentArr.splice(i,1);
         			        return false;
         			    }
         			});
        		   //while deselecting it checkbox will automatically be unchecked, no need to do anything.
         		   //do nothing.
         	   }
      	  	enableDisableNextBtn(selectedStudentArr);
	    //	enableDisableNextBtn();
       	},
        onSelectAll: function(aRowids,status) {
	    	var grid = $(this);
	        if (status) {
	            // uncheck "protected" rows
	            var cbs = $("tr.jqgrow > td > input.cbox:disabled", $(this)[0]);
	            cbs.removeAttr("checked");
	            var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
	            	//console.log(allRowsIds[i], $("#jqg_viewUserGridTableId_"+allRowsIds[i]).is(":disabled"));
			        if($("#jqg_createTestRecordStudentsByOrgTableId_"+allRowsIds[i],grid).is(":disabled")){	
			        	grid.jqGrid('setSelection', allRowsIds[i], false);
			        }
			        var result = $.grep(selectedStudentArr, function(studentO){ return studentO.id == allRowsIds[i]; });
			        if(result.length == 0){
			        	 var rData = $('#createTestRecordStudentsByOrgTableId').jqGrid('getRowData',allRowsIds[i]);
				    	
			        	 var studentObj =	{ stateStudentIdentifier : rData.stateStudentIdentifier ,
	    			 			  attendanceSchoolDisplayIdentifiers : rData.attendanceSchoolDisplayIdentifiers , 
	    			 			 currentSchoolYears : rData.currentSchoolYears,
	    			 			 id : allRowsIds[i]
  		  		    };
			        	
			        	selectedStudentArr.push(studentObj);
			        }	
			    }	
	        }
	        else{
	        	var allRowsIds = grid.jqGrid('getDataIDs');
	            for(var i=0;i<allRowsIds.length;i++){
	            	var result = $.grep(selectedStudentArr, function(studentO){ return studentO.id == allRowsIds[i]; });
	            	if( result.length == 1 ){
	            		var indx = selectedStudentArr.indexOf(result[0]);
	            		selectedStudentArr.splice(indx,1);
	            	}	
	            }
	        }
	    	enableDisableNextBtn(selectedStudentArr);
	    },
	    loadComplete:function(data){
	    	var ids = $(this).jqGrid('getDataIDs'); 
	    	 var tableid=$(this).attr('id');    
	    	 /*for single check box */
	    	 for(var i=0;i<ids.length;i++)	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
	            }
	    	 $('#cb_'+tableid).attr('title','Select Student Grid All Check Box');//all select Check box title
             $('#cb_'+tableid).removeAttr('aria-checked');
	    	 // for Title of input box
	    	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');	    	
             $.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
               }
	});

	$gridAuto[0].clearToolbar();
	  $('td[id^="view_"]').on("click",function(){
			  
				$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
			}); 
}

function enableDisableNextBtn(selectedStudentArr){
	if(selectedStudentArr.length > 0){
		$('#createTestRecordGridTopNextBtn').removeAttr('disabled').removeClass('ui-state-disabled');
		$('#createTestRecordGridBottomNextBtn').removeAttr('disabled').removeClass('ui-state-disabled');
	} else{
		$('#createTestRecordGridTopNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
		$('#createTestRecordGridBottomNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
	}
}

function enableDisableCreateTestRecordSearchBtn(){
	
	var stateSelect = $("#stateOrgsCreateTestRecord").val();
	var districtSelect = $("#districtOrgsCreateTestRecord").val();
	var assessmentSelect =$("#assessmentProgramsCreateTestRecord").val();

	if(stateSelect == undefined || stateSelect == ""){
		$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
		return false;
	}else if(districtSelect == undefined || districtSelect == "" ){
			$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
			return false;
	}else if(assessmentSelect == undefined || assessmentSelect == "" ){
				$('#searchBtnCreateTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
				return false;
	}else {
		$('#searchBtnCreateTestRecord').removeAttr('disabled').removeClass('ui-state-disabled');
	}

	
}


function clearSearchFilterValuesFromSession() {
	
	removeInSessionStorage("tmAssessmentProgramId");
	removeInSessionStorage("tmStateOrgId");
	removeInSessionStorage("tmDistrictOrgId");
	removeInSessionStorage("tmSchoolOrgId");
	removeInSessionStorage("tmTestType");
	removeInSessionStorage("tmsubject");
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