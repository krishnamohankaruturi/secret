var clickedURL = ''; 
$(function(){
	clickedURL=$('#viewTestRecordNav li.nav-item:first a').attr('href');
	$('li.get-sub-viewrecord').on('click',function(e){		 
    	 clickedURL = $(this).find("a").attr('href');   	
    	 viewTestRecordInitMethod();
        e.preventDefault(); // same to return false; 
    }); 
	
});
var objectId = 0;

function viewTestRecordInitMethod(){
	$('#studentViewTestRecord').hide();
	$('#viewTestRecordContainer').hide();
	$('#viewTestStudentRecordScreen').hide();
	$('#buttonBackOrDoneFromViewTestScreen').hide();
	$("#searchFilterViewTestRecordContainer").show();
	$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
	resetViewTestRecord();
	$("#stateOrgsViewTestRecord").trigger('change.select2');
	$("#assessmentProgramsViewTestRecord").trigger('change.select2');
	populateOrgsViewTestRecordData();	
    $('#stateOrgsViewTestRecord, #assessmentProgramsViewTestRecord').select2({
			placeholder:'Select',	
			multiple: false,
			allowClear :true
	});
	
	$('#districtOrgsViewTestRecord, #schoolOrgsViewTestRecord, #viewSubjectSelectTestRecord, #viewTestTypeSelectTestRecord').select2({
		placeholder:'Select',	
		multiple: false,
		allowClear :true
	});
	
	viewTestRecordGridWindow();
	viewStudentTestRecordGridWindow();
	searchButtonViewTestRecord();
	
	$('#viewTestRecordGridTopNextBtn,#viewTestRecordGridBottomNextBtn').off('click').on('click',function() {
		  $('#viewTestRecordGridNextScreen').hide();
		  $('#viewTestStudentRecordScreen').show();
		  $('#buttonBackOrDoneFromViewTestScreen').show();
		  var objects = $('#viewTestRecordStudentsByOrgTableId').jqGrid ('getRowData', objectId);
          var Name = objects.legalFirstName+" "+objects.legalMiddleName+" "+objects.legalLastName;
          var stateStudentIdentifier = objects.stateStudentIdentifier;
          $('#selectviewTestStudentVal').text(Name);
          $('#selectviewTestStateStudentIdVal').text(stateStudentIdentifier);   
          
          var orgChildrenIds = 0;
			if( $('#schoolOrgsViewTestRecord').val() == null || $('#schoolOrgsViewTestRecord').val() == ''){
				
			  orgChildrenIds = $('#districtOrgsViewTestRecord').val();
			}else{
				
			  orgChildrenIds = $('#schoolOrgsViewTestRecord').val();
			}   
			var assessmentProgramId = $('#assessmentProgramsViewTestRecord').val();
      	$("#viewStudentTestRecordStudentsByOrgTableId").jqGrid('clearGridData');
          var $gridAuto = $("#viewStudentTestRecordStudentsByOrgTableId");
		    $gridAuto.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getViewTestRecordsByStudentsGridData.htm', 
			search: false, 
			postData: {
				"filters": "",
				 "orgChildrenIds": orgChildrenIds,
		        "studentId" :objectId,
		        "assessmentProgramId":assessmentProgramId
			}
			}).trigger("reloadGrid",[{page:1}]);
          
	});
	

	
	$('#backFromViewTestStudentRecordScreen').off('click').on('click',function() {
		var chooseTestRecordViewType = clickedURL;
		if(chooseTestRecordViewType == "#ViewStudentByTestRecord"){
			$('#viewTestRecordGridNextScreen').hide();
			$('#buttonBackOrDoneFromViewTestScreen').hide();
			$('#searchFilterViewTestRecordContainer').show();
		}else if(chooseTestRecordViewType =="#ViewTestRecordByStudent"){
			$('#viewTestStudentRecordScreen').hide();
			$('#buttonBackOrDoneFromViewTestScreen').hide();
			$("#viewStudentTestRecordStudentsByOrgTableId").jqGrid('clearGridData');					
			$('#viewTestRecordGridNextScreen').show();
			$('#selectviewTestStudentVal').val("");
	        $('#selectviewTestStateStudentIdVal').val("");
		} 
	});
	
	filteringOrganization($('#stateOrgsViewTestRecord'));

	filteringOrganization($('#districtOrgsViewTestRecord'));

	filteringOrganization($('#schoolOrgsViewTestRecord'));
	setValue(clickedURL);
	
	
}

$('#doneFromViewTestStudentRecordScreen').off('click').on('click',function() { 
	$('#viewTestRecordDiv').hide();
	$('#viewTestStudentRecordScreen').hide();
	$('#buttonBackOrDoneFromViewTestScreen').hide();
	$("#viewStudentTestRecordStudentsByOrgTableId").jqGrid('clearGridData');
	$('#selectviewTestStudentVal').val("");
    $('#selectviewTestStateStudentIdVal').val(""); 
    setValue(clickedURL);
});


	function setValue(chooseTestRecordViewType){
if(chooseTestRecordViewType == "#ViewStudentByTestRecord"){
	resetViewTestRecord();
	populateViewTestRecordAssessmentProgram();
	$("#stateOrgsViewTestRecord").trigger('change');
	$("#districtOrgsViewTestRecord").trigger('change');
	$('#studentViewTestRecord').hide();
	$('#viewTestStudentRecordScreen').hide();
	$('#viewTestRecordGridNextScreen').hide();
	$('#viewTestRecordByStudentHeader').hide();
	$('#buttonBackOrDoneFromViewTestScreen').hide();
	$('#viewTestRecordContainer').show();
	$('#viewStudentByTestRecordHeader').show();
	$("#searchFilterViewTestRecordContainer").show();
	$('#viewSubjectAndTestTypeSelectTestRecord').show();
}
else if(chooseTestRecordViewType =="#ViewTestRecordByStudent"){
	resetViewTestRecord();
	populateViewTestRecordAssessmentProgram();
	$("#stateOrgsViewTestRecord").trigger('change.select2');
	$("#districtOrgsViewTestRecord").trigger('change.select2');
	$('#studentViewTestRecord').hide();
	$('#viewTestStudentRecordScreen').hide();
	$('#viewTestRecordGridNextScreen').hide();
	$('#viewStudentByTestRecordHeader').hide();
	$('#buttonBackOrDoneFromViewTestScreen').hide();
	$('#viewTestRecordContainer').show();
	$('#viewTestRecordByStudentHeader').show();
	$("#searchFilterViewTestRecordContainer").show();
	$('#viewSubjectAndTestTypeSelectTestRecord').hide();
}
else{
	resetViewTestRecord();
	$('#studentViewTestRecord').hide();
	$('#viewTestRecordContainer').hide();
	$('#viewTestStudentRecordScreen').hide();
	$('#viewTestRecordGridNextScreen').hide();
	$('#viewTestRecordByStudentHeader').hide();
	$('#viewStudentByTestRecordHeader').hide();
	$('#buttonBackOrDoneFromViewTestScreen').hide();
	$('#viewSubjectAndTestTypeSelectTestRecord').hide();
}
}

function resetViewTestRecord(){
	$("#districtOrgsViewTestRecord").val("").trigger('change.select2');
	$("#schoolOrgsViewTestRecord").val("").trigger('change.select2');
	$('#assessmentProgramsViewTestRecord').val("").trigger('change.select2');
	$("#viewSubjectSelectTestRecord").val("").trigger('change.select2');
	$("#viewTestTypeSelectTestRecord").val("").trigger('change.select2');
	$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
	aart.clearMessages();
}

function populateOrgsViewTestRecordData(){
	var stateOrgSelect = $('#stateOrgsViewTestRecord');clrStateOptionText='';
	
	$('#stateOrgsViewTestRecord').html("");
	$('#stateOrgsViewTestRecord').val("").trigger('change.select2');

	$('#districtOrgsViewTestRecord').html("");
	$('#districtOrgsViewTestRecord').val("").trigger('change.select2');
	
	$('#schoolOrgsViewTestRecord').html("");
	$('#schoolOrgsViewTestRecord').val("").trigger('change.select2');
		
	$.ajax({
        url: 'getStatesOrgsForUser.htm',
        data: { },
        dataType: 'json',
        type: "GET"
	}).done(function(states) {
    	if (states !== undefined && states !== null && states.length > 0) {
			$.each(states, function(i, stateOrg) {
				clrStateOptionText = states[i].organizationName;
				stateOrgSelect.append($('<option></option>').val(stateOrg.id).html(clrStateOptionText));
			});
			if (states.length == 1) {
				stateOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				$("#stateOrgsViewTestRecord").trigger('change');
			}
		}
    	$('#schoolOrgsViewTestRecord, #districtOrgsViewTestRecord').val('').trigger('change.select2');
    	
      });
	
}

$('#stateOrgsViewTestRecord').on("change",function(event) {
	
	var districtOrgSelect = $('#districtOrgsViewTestRecord');clrDistrictOptionText ='';
	$('#districtOrgsViewTestRecord').html("");
	$('#districtOrgsViewTestRecord').val("").trigger('change.select2');
	
	$('#schoolOrgsViewTestRecord').html("");
	$('#schoolOrgsViewTestRecord').val("").trigger('change.select2');
	var stateOrgId = $('#stateOrgsViewTestRecord').val();
	if(stateOrgId == null || stateOrgId == "" || stateOrgId == undefined)
		{
			//populateOrgsViewTestRecordData();
			$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			$("#districtOrgsViewTestRecord").val("").trigger('change.select2');
			$("#schoolOrgsViewTestRecord").val("").trigger('change.select2');
			$('#assessmentProgramsViewTestRecord').trigger('change.select2');
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
		if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
			$.each(districtOrgs, function(i, districtOrg) {
				clrDistrictOptionText = districtOrgs[i].organizationName;
				districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(clrDistrictOptionText));
			});
			if (districtOrgs.length == 1) {
				districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				$("#districtOrgsViewTestRecord").trigger('change.select2');
			}
		} 
		$('#schoolOrgsViewTestRecord, #districtOrgsViewTestRecord').val('').trigger('change.select2');
	});
});

$('#districtOrgsViewTestRecord').on("change",function(event) {
	
	$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
	
	$('#schoolOrgsViewTestRecord').html("");
	$('#schoolOrgsViewTestRecord').val("").trigger('change.select2');
		var districtOrgId = $('#districtOrgsViewTestRecord').val();
		var assessmentId = $('#assessmentProgramsViewTestRecord').val();
		
		if (districtOrgId!=null && districtOrgId != 0) {
			$.ajax({
				 url: 'getOrgsBasedOnUserContext.htm',
			     data: {
			        	orgId : districtOrgId,
			        	orgType:'SCH',
			        	orgLevel: 70	
			        	},				
				type: "GET",
			    dataType: 'json'
			}).done(function(schoolOrgs) {
	        	schoolSelect=$('#schoolOrgsViewTestRecord');   
	        	viewSchoolOptionText='';
				if (schoolOrgs !== undefined && schoolOrgs !== null && schoolOrgs.length > 0) {
					$.each(schoolOrgs, function(i, schoolOrg) {
						viewSchoolOptionText = schoolOrgs[i].organizationName;
						schoolSelect.append($('<option></option>').val(schoolOrg.id).html(viewSchoolOptionText));
					});
					if (schoolOrgs.length == 1) {
						schoolSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
						$("#schoolOrgsViewTestRecord").trigger('change.select2');
					}
				} 
				$('#schoolOrgsViewTestRecord').val('').trigger('change.select2');
				enableDisableViewSearchButton();
	        });
		}
});	

$('#assessmentProgramsViewTestRecord').on("change",function(event) {
	
	var assessmentId = $('#assessmentProgramsViewTestRecord').val();
	var districtId = $('#districtOrgsViewTestRecord').val();

	if(assessmentId != "" && assessmentId != " " && assessmentId != "undefined" && districtId != "" && districtId != " " && districtId != "undefined" ){
		$('#searchBtnViewTestRecord').removeAttr('disabled').removeClass('ui-state-disabled');
	}
	else{
		$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
	}
	enableDisableViewSearchButton();
});

function populateViewTestRecordAssessmentProgram(){ 
	
	var viewAssessmentTestRecordSelect = $('#assessmentProgramsViewTestRecord'), clrOptionText='';
	viewAssessmentTestRecordSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	$('#assessmentProgramsViewTestRecord').val("").trigger('change.select2');

	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(clrAssessmentPrograms) {				
		if (clrAssessmentPrograms !== undefined && clrAssessmentPrograms !== null && clrAssessmentPrograms.length > 0) {
			$.each(clrAssessmentPrograms, function(i, clrAssessmentProgram) {
				clrOptionText = clrAssessmentPrograms[i].programName;
				if(clrAssessmentProgram.assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
					viewAssessmentTestRecordSelect.append($('<option selected=\''+'selected'+'\'></option>').val(clrAssessmentProgram.id).html(clrOptionText));
				} else {
					viewAssessmentTestRecordSelect.append($('<option></option>').val(clrAssessmentProgram.id).html(clrOptionText));
				}
			});
			
		} 
		
		$('#assessmentProgramsViewTestRecord').trigger('change');
	
	});
	

}



$("#assessmentProgramsViewTestRecord").off('click').on('change',function() {
	
	var selectSubject = $('#viewSubjectSelectTestRecord'), selectSubjectoptionText='';
	var assessmentProgramId = $('#assessmentProgramsViewTestRecord').val();
	$('#viewTestTypeSelectTestRecord').html("");
	$('#viewTestTypeSelectTestRecord').val("").trigger('change.select2');
	
	selectSubject.html("");
	selectSubject.append($('<option></option>').val("").html("Select"));
	selectSubject.val('').trigger('change.select2');
	
	if(assessmentProgramId ==  null || assessmentProgramId == undefined || assessmentProgramId == "")
		{
			enableDisableViewSearchButton();
			return false;
		}
	
	$.ajax({
			url: 'getContentAreasByAssessmentProgramForTestRecord.htm',
		dataType: 'json',
		type: "POST",
		data:{"assessmentProgramId":assessmentProgramId}
	}).done(function(subjects) {				
		if (subjects !== undefined && subjects !== null && subjects.length > 0) {
			$.each(subjects, function(i, subject) {
				subabbrname = subject.abbreviatedName;
					selectSubjectoptionText = subjects[i].name;
					selectSubject.append($('<option></option>').val(subject.abbreviatedName).html(selectSubjectoptionText));
			});
			
			if (subjects.length == 1) {
				selectSubject.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				$("#viewSubjectSelectTestRecord").trigger('change.select2');
			}
		} 
		$('#viewSubjectSelectTestRecord,#viewTestTypeSelectTestRecord').val('').trigger('change.select2');
	});	
	enableDisableViewSearchButton();
});	

$("#viewSubjectSelectTestRecord").on("change",function() {

	$('#viewTestTypeSelectTestRecord').html("");selectTestTypeoptionText='';
	$('#viewTestTypeSelectTestRecord').append($('<option></option>').val("").html("Select"));
	var subjectCode = $('#viewSubjectSelectTestRecord').val();
	var assessmentProgramId = $('#assessmentProgramsViewTestRecord').val();
	if(subjectCode == null || subjectCode == undefined || subjectCode == ""){
	
		$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
		$("#viewTestTypeSelectTestRecord").val("").val('').trigger('change.select2');
		return false;
	}
	
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
				$("#viewTestTypeSelectTestRecord").append($('<option></option>').val(testtype.testTypeCode).html(selectTestTypeoptionText));
			});
			
			if (testTypes.length == 1) {
				$("#viewTestTypeSelectTestRecord").find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				
			}
		}
		$("#viewTestTypeSelectTestRecord").val('').trigger('change.select2');
	});
		
});

$("#viewTestTypeSelectTestRecord").on("change",function() {
	enableDisableViewSearchButton();
});
function enableDisableViewSearchButton(){

	var stateSelect = $("#stateOrgsViewTestRecord").val();
	var districtSelect = $("#districtOrgsViewTestRecord").val();
	var assessmentSelect =$("#assessmentProgramsViewTestRecord").val();
	var subjectSelect =$("#viewSubjectSelectTestRecord").val();
	var testTypeSelect = $("#viewTestTypeSelectTestRecord").val();
	
	if(clickedURL == "#ViewStudentByTestRecord"){
		if(stateSelect == undefined || stateSelect == ""){
			$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			return false;
		}else if(districtSelect == undefined || districtSelect == "" ){
				$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
				return false;
		}else if(assessmentSelect == undefined || assessmentSelect == "" ){
					$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
					return false;
		}else if(subjectSelect == undefined || subjectSelect == ""){
			$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			return false;
		}else if(testTypeSelect == undefined || testTypeSelect == "" ){
			$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			return false;
		}else {
			$('#searchBtnViewTestRecord').removeAttr('disabled').removeClass('ui-state-disabled');
		}
	}
	else if(clickedURL =="#ViewTestRecordByStudent"){
		if(stateSelect == undefined || stateSelect == ""){
			$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			return false;
		}else if(districtSelect == undefined || districtSelect == "" ){
				$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
				return false;
		}else if(assessmentSelect == undefined || assessmentSelect == "" ){
					$('#searchBtnViewTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
					return false;
		}else {
			$('#searchBtnViewTestRecord').removeAttr('disabled').removeClass('ui-state-disabled');
		}
	}	
}

function searchButtonViewTestRecord(){
	$('#searchBtnViewTestRecord').off('click').on('click',function(e){
		e.preventDefault();
		var chooseTestRecordViewType = clickedURL;
		if(chooseTestRecordViewType == "#ViewStudentByTestRecord"){
			var subjectTestRecord = $("#viewSubjectSelectTestRecord").val();
			var testTypeRecord= $("#viewTestTypeSelectTestRecord").val();
			if(testTypeRecord==undefined || testTypeRecord =="" || subjectTestRecord == undefined || subjectTestRecord == "" ){
				return false;
			}
			$("#viewSelectSubjectVal").text($("#viewSubjectSelectTestRecord option:selected" ).text());
			$("#viewSelectTestTypeVal").text($("#viewTestTypeSelectTestRecord option:selected" ).text());
			$("#viewTestRecordStudentsByOrgTableId").jqGrid('clearGridData');
			$("#searchFilterViewTestRecordContainer").hide();
			$("#studentViewTestRecord").show();
			var assessmentProgramId = $('#assessmentProgramsViewTestRecord').val();
			var orgChildrenIds = 0;
			if(assessmentProgramId ==undefined || assessmentProgramId ==""){
				return false;
			}
			$("#selectSubjectVal").text($("#viewSubjectSelectTestRecord option:selected" ).text());
			$("#selectTestTypeVal").text($("#viewTestTypeSelectTestRecord option:selected" ).text());
			if( $('#schoolOrgsViewTestRecord').val() == null || $('#schoolOrgsViewTestRecord').val() == ''){
			  orgChildrenIds = $('#districtOrgsViewTestRecord').val();
			}else{
			  orgChildrenIds = $('#schoolOrgsViewTestRecord').val();
			}
			
		    var $gridAuto = $("#viewTestRecordStudentsByOrgTableId");
		    $gridAuto.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getClearTestRecordStudentsGridData.htm', 
			search: false, 
			postData: {
				"filters": "",
		         "assessmentProgramId": assessmentProgramId,
		         "orgChildrenIds": orgChildrenIds,
		         "testTypeCode" :testTypeRecord,
		         "subjectCode" :subjectTestRecord
			}
			}).trigger("reloadGrid",[{page:1}]);
		    $('#viewTestRecordGridTopNextBtn').hide();
		    $('#viewTestRecordGridBottomNextBtn').hide();
		    $('#studentNameStateStuIdLabel').hide();
		    $('#testRecordSubjectTestTypeHeader').show();
		    $('#buttonBackOrDoneFromViewTestScreen').show();
		    $('#viewTestRecordGridNextScreen').show(); 
		 	$('#viewTestRecordGridTopNextBtn,#viewTestRecordGridBottomNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
		}else if(chooseTestRecordViewType =="#ViewTestRecordByStudent"){
			$("#viewTestRecordStudentsByOrgTableId").jqGrid('clearGridData');	
			$('#buttonBackOrDoneFromViewTestScreen').hide();
			$("#searchFilterViewTestRecordContainer").hide();
			$('#testRecordSubjectTestTypeHeader').hide();
			$('#viewTestRecordGridTopNextBtn').show();
		    $('#viewTestRecordGridBottomNextBtn').show();
			$('#studentNameStateStuIdLabel').show();
			$("#studentViewTestRecord").show();
			var assessmentProgramId = $('#assessmentProgramsViewTestRecord').val();
			var orgChildrenIds = 0;
			if( $('#schoolOrgsViewTestRecord').val() == null || $('#schoolOrgsViewTestRecord').val() == ''){
			  orgChildrenIds = $('#districtOrgsViewTestRecord').val();
			}else{
			  orgChildrenIds = $('#schoolOrgsViewTestRecord').val();
			}
		    var $gridAuto = $("#viewTestRecordStudentsByOrgTableId");
		    $gridAuto.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getCreateTestRecordStudentsGridData.htm', 
			search: false, 
			postData: { "filters": "",
				         "assessmentProgramId": assessmentProgramId,
				         "orgChildrenIds": orgChildrenIds}
			}).trigger("reloadGrid",[{page:1}]);
		    $('#viewTestRecordGridNextScreen').show(); 
		 	$('#viewTestRecordGridTopNextBtn,#viewTestRecordGridBottomNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
		}
	});

}

function viewTestRecordGridWindow(){
	
	var grid_width = $('.kite-table').width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1015;				
	}
    var cell_width = grid_width/5;
	
    selectedStudentArr = [];
    var $gridAuto = $("#viewTestRecordStudentsByOrgTableId");

    var cmforVuewStudentsCTR = [
		{ name : 'legalFirstName', index : 'legalFirstName', label : 'legalFirstNameViewTestRecords', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'legalMiddleName', index : 'legalMiddleName', width : cell_width,sortable : false, hidden : false,search : false, hidedlg : true},
		{ name : 'legalLastName', index : 'legalLastName', label : 'legalLastNameViewTestRecords',  width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', label : 'stateStudentIdentifierViewTestRecords', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
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
		pager : '#pViewTestRecordStudentsByOrgTableId',
		// F-820 Grids default sort order
		sortname : 'legalLastName,legalFirstName,legalMiddleName',
		multiselect: false,
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
	      $('#viewTestRecordGridTopNextBtn,#viewTestRecordGridBottomNextBtn').removeAttr('disabled').removeClass('ui-state-disabled');
	      objectId = rowid;
       	},
       	loadComplete: function(){
       	  $('#viewTestRecordGridTopNextBtn,#viewTestRecordGridBottomNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
       	 var tableid=$(this).attr('id');  
       	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');         
         $.each(objs, function(index, value) {         
          var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                $(value).attr('title',$(nm).text()+' filter');                          
                });
      	}
	});

	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
}

function viewStudentTestRecordGridWindow(){
	
	var grid_width = $('.kite-table').width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 1007;				
	}
    var cell_width = grid_width/3;
	
    selectedStudentArr = [];
    var $gridAuto = $("#viewStudentTestRecordStudentsByOrgTableId");

    var cmforVuewStudentsRecordCTR = [
		{ name : 'subjectName', index : 'subjectName', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'testTypeName', index : 'testTypeName', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'currentSchoolYears', index : 'currentSchoolYears', width : cell_width,sortable : false, hidden : false,search : false, hidedlg : true},
	];

    //JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: grid_width,
		colNames : [
					'Subject', 'Test Type', 'School Year'
					],
		colModel :cmforVuewStudentsRecordCTR,	
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		height : 'auto',
		pager : '#pViewStudentTestRecordStudentsByOrgTableId',
		sortname : 'subjectName',
		multiselect: false,
		pagestatesave:true,
		filterstatesave:false,
		columnChooser:false,
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
	    loadComplete: function(){
	       	   	 var tableid=$(this).attr('id');  
	        	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');         
	          $.each(objs, function(index, value) {         
	           var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                 $(value).attr('title',$(nm).text()+' filter');
	                 $(value).css({"width": "100%"});
	                 });
	       	}
	    
	});
	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
}