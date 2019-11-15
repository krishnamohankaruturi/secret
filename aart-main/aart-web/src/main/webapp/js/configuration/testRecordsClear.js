

var orgHierarchies = {};
var selectedStudentArr = []	;
var childOrg = 20;


function clearTestRecordInitMethod(){
	
	$("#clearTestRecordDiv").show();
	$("#searchFilterClearTestRecordContainer").show();
	$('#tecUploadDiv').hide();
	$("#studentClearTestRecord").show();
	$("#studentClearTestRecord").hide();
	$("#createTestRecordDiv").hide();
	$("#searchFilterCreateTestRecordContainer").hide();
	$("#schoolOrgsClearTestRecord").val("").trigger('change.select2');
	$("#districtOrgsClearTestRecord").val("").trigger('change.select2');
	$("#schoolOrgsClearTestRecord").val("").trigger('change.select2');
	$('#assessmentProgramsClearTestRecord').val("").trigger('change.select2');
	$("#clearSubjectSelectTestRecord").val("").trigger('change.select2');
	$("#clearTestTypeSelectTestRecord").val("").trigger('change.select2');
	$("#assessmentProgramsClearTestRecord").trigger('change');
	$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
	$("label.error").hide();
	  $(".error").removeClass("error");
	
	
	
	$('#stateOrgsClearTestRecord, #districtOrgsClearTestRecord,#schoolOrgsClearTestRecord,#assessmentProgramsClearTestRecord,#clearTestTypeSelectTestRecord,#clearSubjectSelectTestRecord').select2({
		placeholder:'Select',
		multiple: false,
		allowClear:true
	});
	
	
	$('#searchFilterClearTestRecordForm').validate({
		ignore: "",
		rules: {
			assessmentProgramsClearTestRecord: {required: true},
			stateOrgsClearTestRecord: {required: true},
			districtOrgsClearTestRecord: {required: true},
			clearTestTypeSelectTestRecord: {required: true},
			clearSubjectSelectTestRecord: {required: true}
		}
	});
	
	viewClearTestRecordWindowGrid();
	populateClearTestRecordAssessmentProgram();
	populateClearTestRecordSearchFiltersData();
	
	
	$("#confirmDialogClearTestRecord").dialog({
		  autoOpen: false,
		  modal: true
	});

	
	
	$('#searchBtnClearTestRecord').off('click').on('click',function(e) {
		e.preventDefault();
		
		if($('#searchFilterClearTestRecordForm').valid()){
		var subjectTestRecord = $("#clearSubjectSelectTestRecord").val();
		var testTypeRecord= $("#clearTestTypeSelectTestRecord").val();
		
		if(testTypeRecord==undefined || testTypeRecord =="" || subjectTestRecord == undefined || subjectTestRecord == "" )
			{

				return false;
			
			}

		$("#clearTestRecordStudentsByOrgTableId").jqGrid('clearGridData');	
		$("#searchFilterClearTestRecordContainer").hide();
		$("#studentClearTestRecord,#selectStudentGridTopDiv,#selectStudentGridDiv,#selectStudentGridBottomDiv").show();
		$("#removeSelectStudentRecordButtons").hide();
		
		var assessmentProgramId = $('#assessmentProgramsClearTestRecord').val();
		var orgChildrenIds = 0;
		if(assessmentProgramId ==undefined || assessmentProgramId =="")
			{
				return false;
			}
	//	viewClearTestRecordWindowGrid();
		$("#selectSubjectVal").text($("#clearSubjectSelectTestRecord option:selected" ).text());
		$("#selectTestTypeVal").text($("#clearTestTypeSelectTestRecord option:selected" ).text());
		if( $('#schoolOrgsClearTestRecord').val() == ''){
			
		  orgChildrenIds = $('#districtOrgsClearTestRecord').val();
		}else{
			
		  orgChildrenIds = $('#schoolOrgsClearTestRecord').val();
		}

	    var $gridAuto = $("#clearTestRecordStudentsByOrgTableId");
	    $gridAuto.jqGrid('setGridParam',{
		datatype:"json", 
		url : 'getClearTestRecordStudentsGridData.htm', 
		search: false, 
		postData: { "filters": "",
			         "assessmentProgramId": assessmentProgramId,
			         "orgChildrenIds": orgChildrenIds,
			         "testTypeCode" :testTypeRecord,
			         "subjectCode" :subjectTestRecord

					}
		}).trigger("reloadGrid",[{page:1}]);
	     
	 	$('#clearTestRecordGridTopNextBtn,#clearTestRecordGridBottomNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
	 
	}
		
	});
	
	
	
	$('#clearTestRecordGridTopNextBtn,#clearTestRecordGridBottomNextBtn').off('click').on('click',function(e) {
		e.preventDefault();
		$("#searchFilterClearTestRecordContainer,#selectStudentGridTopDiv,#selectStudentGridDiv,#selectStudentGridBottomDiv").hide();
		$("#removeSelectStudentRecordButtons").show();
	});
	
	$("#cancelBtnClearTestRecord").off('click').on('click',function(e) {
		
		e.preventDefault();
		$("#confirmDialogClearTestRecord").dialog({
			create: function(event, ui) { 
		    	var widget = $(this).dialog("widget");
		
			},	
		  buttons : {
			"Yes" : function() {
				$(this).dialog("close");
				selectedStudentArr = []	;
				$("#clearTestRecordDiv").show();
				$("#searchFilterClearTestRecordContainer").show();
				$("#studentClearTestRecord").hide();
			
				$("#stateOrgsClearTestRecord").val("").trigger('change.select2');
				$("#districtOrgsClearTestRecord").val("").trigger('change.select2');
				$("#schoolOrgsClearTestRecord").val("").trigger('change.select2');
				$('#assessmentProgramsClearTestRecord').trigger('change.select2');
				$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
				$("label.error").hide();
				  $(".error").removeClass("error");
				clearTestRecordInitMethod();
				
			},
			"No" : function(){
			 
			  $('#confirmDialogClearTestRecord').dialog('close');
			}
		  }
		});
		$("#confirmDialogClearTestRecord").dialog("open");
	});
	
	$("#yesBtnClearTestRecord").off('click').on('click',function(e) {
 		e.preventDefault();
		clearSelectedStudentTestRecord();
		e.preventDefault();
	});
	
	filteringOrganization($('#stateOrgsClearTestRecord'));
	
	filteringOrganization($('#districtOrgsClearTestRecord'));

	filteringOrganization($('#schoolOrgsClearTestRecord'));
	
	 $('td[id^="view_"]').on("click",function(){
		   		$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
		});

}	

function clearSelectedStudentTestRecord(){
	
	var selectedStudnetArrayList = {};
	selectedStudnetArrayList.stateStudentIdentifier =[];
	selectedStudnetArrayList.attendanceSchoolDisplayIdentifiers = [];
	selectedStudnetArrayList.currentSchoolYears =[];
	
	var orgChildrenIds = 0;
	if( $('#schoolOrgsClearTestRecord').val() == ''){
		
	  orgChildrenIds = $('#districtOrgsClearTestRecord').val();
	}else{
		
	  orgChildrenIds = $('#schoolOrgsClearTestRecord').val();
	}
	
	var k=0;
	for(var i=0; i < selectedStudentArr.length; i++){
		
		 selectedStudnetArrayList.stateStudentIdentifier[k] = selectedStudentArr[i].stateStudentIdentifier;
		 selectedStudnetArrayList.attendanceSchoolDisplayIdentifiers[k] = selectedStudentArr[i].attendanceSchoolDisplayIdentifiers;
		 selectedStudnetArrayList.currentSchoolYears[k] = selectedStudentArr[i].currentSchoolYears;
		k++;
	}
	
	var recordType ="Clear";
	var selectedSubjectType = $('#clearSubjectSelectTestRecord').val();
	var selectedTestType = $('#clearTestTypeSelectTestRecord').val();
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
		$("#clearTestRecordDiv").show();
		$("#searchFilterClearTestRecordContainer").show();
		$("#studentClearTestRecord").hide();
//		$("#selectTestRecordDiv").hide();
		$("#stateOrgsClearTestRecord").trigger('change.select2');
		$("#districtOrgsClearTestRecord").val("").trigger('change.select2');
		$("#schoolOrgsClearTestRecord").val("").trigger('change.select2');
		$("#clearSubjectSelectTestRecord").val("").trigger('change.select2');
		$("#clearTestTypeSelectTestRecord").val("").trigger('change.select2');
		$('#assessmentProgramsClearTestRecord').val("").trigger('change.select2');
		
		$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
		$("label.error").hide();
		  $(".error").removeClass("error");
		clearTestRecordInitMethod();
	if(data.result == "success"){	
		$("#clearRecordSuccessMessage").show();
		setTimeout("aart.clearMessages()", 5000);
		setTimeout(function(){ $('#clearRecordSuccessMessage').hide(); },5000);
	}
	else{
		$("#clearRecordFailMessage").show();
		setTimeout("aart.clearMessages()", 5000);
		setTimeout(function(){ $('#clearRecordFailMessage').hide(); },5000);
	}
});
}

function viewClearTestRecordWindowGrid(){
	
	
	var grid_width = $('.kite-table').width();
	if(grid_width == 100 || grid_width == 0) {
		grid_width = 900;				
	}
    var cell_width = grid_width/5;
	
    selectedStudentArr = [];
    var $gridAuto = $("#clearTestRecordStudentsByOrgTableId");

var cmforViewStudentsCTR = [
		
		{ name : 'legalFirstName', index : 'legalFirstName', label: 'legalFirstNameCreateTest', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'legalMiddleName', index : 'legalMiddleName',label: 'legalMiddleNameCreateTest', width : cell_width,sortable : false, hidden : false,search : false, hidedlg : true},
		{ name : 'legalLastName', index : 'legalLastName',label: 'legalLastNameCreateTest', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier',label: 'stateStudentIdentifierCreateTest', width : cell_width, sortable : true, search : true, hidden : false, hidedlg : true },
		{ name: 'currentSchoolYears',index: 'currentSchoolYears',label: 'currentSchoolYearsCreateTest', width:cell_width, sortable : true, search : false, hidden: false,hidedlg : true},
		{ name: 'attendanceSchoolNames',index: 'attendanceSchoolNames',label: 'attendanceSchoolNamesCreateTest', width:cell_width, sortable : false, search : false, hidden: false,hidedlg : false},
		{ name: 'gradeCourseName',index: 'gradeCourseName',label: 'gradeCourseNameCreateTest', width:cell_width, sortable : false, search : false, hidden: false,hidedlg : false},
		{ name: 'attendanceSchoolDisplayIdentifiers',index: 'attendanceSchoolDisplayIdentifiers',label: 'attendanceSchoolDisplayIdentifiersCreateTest', sortable : false, search : false, hidden: true,hidedlg : true}
	];

				
//	$('#clearTestRecordStudentsByOrgTableId').jqGrid('GridDestroy');
//JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: grid_width,
		colNames : [
					'First Name', 'Middle Name', 'Last Name','State Id','School Year','School Name','Grade','attendanceSchoolId'
					],
		colModel :cmforViewStudentsCTR,	
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		height : 'auto',
		pager : '#pclearTestRecordStudentsByOrgTableId',
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
	      	 var rData = $('#clearTestRecordStudentsByOrgTableId').jqGrid('getRowData',rowid);
		    	
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
			
      	  enableDisableClearTestRecordNextBtn(selectedStudentArr);
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
			        if($("#jqg_clearTestRecordStudentsByOrgTableId_"+allRowsIds[i],grid).is(":disabled")){	
			        	grid.jqGrid('setSelection', allRowsIds[i], false);
			        }
			        var result = $.grep(selectedStudentArr, function(studentO){ return studentO.id == allRowsIds[i]; });
			        if(result.length == 0){
			        	 var rData = $('#clearTestRecordStudentsByOrgTableId').jqGrid('getRowData',allRowsIds[i]);
				    	
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
	        enableDisableClearTestRecordNextBtn(selectedStudentArr);
	    },
	    loadComplete:function(data){
	    	var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id');      
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
	            }
	          
	    var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
        $('#cb_'+tableid).removeAttr('aria-checked');
        $('#cb_'+tableid).attr('title','Student Grid All Check Box');
        $.each(objs, function(index, value) {         
         var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
               $(value).attr('title',$(nm).text()+' filter');                          
               });
	    }
	});
	$gridAuto[0].clearToolbar();

}

function populateClearTestRecordSearchFiltersData(){
	
	enableDisableSearchButton();
	var stateOrgSelect = $('#stateOrgsClearTestRecord'); clrStateOptionText ='';
	$('#stateOrgsClearTestRecord').html("");
	$('#stateOrgsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

	$('#schoolOrgsClearTestRecord').html("");
	$('#schoolOrgsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

	
	$('#districtOrgsClearTestRecord').html("");
	$('#districtOrgsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

	$('#schoolOrgsClearTestRecord').html("");
	$('#schoolOrgsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');
	
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
				$("#stateOrgsClearTestRecord").trigger('change');
			}
		} 
	
        	$('#schoolOrgsClearTestRecord, #districtOrgsClearTestRecord,#stateOrgsClearTestRecord').trigger('change.select2');
    });

	$('#stateOrgsClearTestRecord').on("change",function(event) {
			enableDisableSearchButton();
			var districtOrgSelect = $('#districtOrgsClearTestRecord');clrDistrictOptionText ='';
			districtOrgSelect.find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#districtOrgsClearTestRecord').html("");
			$('#districtOrgsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

			$('#schoolOrgsClearTestRecord').html("");
			$('#schoolOrgsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

			var stateOrgId = $('#stateOrgsClearTestRecord').val();
		if(stateOrgId == "" || stateOrgId =="undefined")
			{
				$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
				$("#districtOrgsClearTestRecord").val("").trigger('change.select2');
				$("#schoolOrgsClearTestRecord").val("").trigger('change.select2');
				$('#assessmentProgramsClearTestRecord').trigger('change.select2');
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
						$("#districtOrgsClearTestRecord").trigger('change');
					}
				} 
				$('#schoolOrgsClearTestRecord, #districtOrgsClearTestRecord').trigger('change.select2');
	        });
		
	});
	
	$('#districtOrgsClearTestRecord').on("change",function(event) {
		
			$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
			clrSchoolOptionText='';
			$('#schoolOrgsClearTestRecord').html("");
			$('#schoolOrgsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

			
			var districtOrgId = $('#districtOrgsClearTestRecord').val();
				var schoolSelect = $("#schoolOrgsClearTestRecord");
				
				if (districtOrgId != 0) {
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
						if (schoolOrgs !== undefined && schoolOrgs !== null && schoolOrgs.length > 0) {
							$.each(schoolOrgs, function(i, schoolOrg) {
								clrSchoolOptionText = schoolOrgs[i].organizationName;
								schoolSelect.append($('<option></option>').val(schoolOrg.id).html(clrSchoolOptionText));
							});
							if (schoolOrgs.length == 1) {
								schoolSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
								$("#schoolOrgsClearTestRecord").trigger('change');
							}
						} 
					
						$('#schoolOrgsClearTestRecord').trigger('change.select2');
						enableDisableSearchButton();
			        });
				}
			});	
	
}

function populateClearTestRecordAssessmentProgram(){
	
	var clearTestRecordSelect = $('#assessmentProgramsClearTestRecord'), clrOptionText='';
	$('#assessmentProgramsClearTestRecord').html("");
	$('#assessmentProgramsClearTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');

	
	
	enableDisableSearchButton();
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(clrAssessmentPrograms) {				
		if (clrAssessmentPrograms !== undefined && clrAssessmentPrograms !== null && clrAssessmentPrograms.length > 0) {
			$.each(clrAssessmentPrograms, function(i, clrAssessmentProgram) {
				clrOptionText = clrAssessmentPrograms[i].programName;
				if(clrAssessmentProgram.assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
					clearTestRecordSelect.append($('<option selected=\''+'selected'+'\'></option>').val(clrAssessmentProgram.id).html(clrOptionText));
				} else {
					clearTestRecordSelect.append($('<option></option>').val(clrAssessmentProgram.id).html(clrOptionText));
				}
			});
			clearTestRecordSelect.trigger('change');
		}
		clearTestRecordSelect.trigger('change.select2');
	
	});
	
}


$("#assessmentProgramsClearTestRecord").on("change",function() {	
	enableDisableSearchButton();
	var selectSubject = $('#clearSubjectSelectTestRecord'), selectSubjectoptionText='';
	var assessmentProgramId = $('#assessmentProgramsClearTestRecord').val();
	$('#clearTestTypeSelectTestRecord').html("");
	$('#clearTestTypeSelectTestRecord').append($('<option></option>').val("").html("Select")).trigger('change.select2');
	
	selectSubject.html("");
	selectSubject.append($('<option></option>').val("").html("Select"));
	selectSubject.trigger('change.select2');
	if(assessmentProgramId == undefined || assessmentProgramId =="")
		{
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
				$("#clearSubjectSelectTestRecord").trigger('change');
			}
		} 
		$('#clearSubjectSelectTestRecord,#clearTestTypeSelectTestRecord').trigger('change.select2');
	});	
	
});	

$("#clearSubjectSelectTestRecord").on("change",function() {

	enableDisableSearchButton();
	$('#clearTestTypeSelectTestRecord').html("");selectTestTypeoptionText = '';
	$('#clearTestTypeSelectTestRecord').append($('<option></option>').val("").html("Select"));
	var subjectCode = $('#clearSubjectSelectTestRecord').val();
	var assessmentProgramId = $('#assessmentProgramsClearTestRecord').val();
	if(subjectCode == undefined || subjectCode == ""){
	
		$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');
		$("#clearTestTypeSelectTestRecord").val("").trigger('change.select2');
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
				$("#clearTestTypeSelectTestRecord").append($('<option></option>').val(testtype.testTypeCode).html(selectTestTypeoptionText));
			});
			
			if (testTypes.length == 1) {
				$("#clearTestTypeSelectTestRecord").find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				
			}
		}
		$("#clearTestTypeSelectTestRecord").trigger('change');
		$('#clearTestTypeSelectTestRecord').trigger('change.select2');

		
	});
		
});

$("#clearTestTypeSelectTestRecord").on("change",function() {
	enableDisableSearchButton();
});

function enableDisableClearTestRecordNextBtn(selectedStudentArr){
	if(selectedStudentArr.length > 0){
		$('#clearTestRecordGridTopNextBtn').removeAttr('disabled').removeClass('ui-state-disabled');
		$('#clearTestRecordGridBottomNextBtn').removeAttr('disabled').removeClass('ui-state-disabled');
	} else{
		$('#clearTestRecordGridTopNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
		$('#clearTestRecordGridBottomNextBtn').attr('disabled','disabled').addClass('ui-state-disabled');
	}
}

function enableDisableSearchButton(){

	var stateSelect = $("#stateOrgsClearTestRecord").val();
	var districtSelect = $("#districtOrgsClearTestRecord").val();
	var assessmentSelect =$("#assessmentProgramsClearTestRecord").val();
	var subjectSelect =$("#clearSubjectSelectTestRecord").val();
	var testTypeSelect = $("#clearTestTypeSelectTestRecord").val();

	if(stateSelect == undefined || stateSelect == ""){
		$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
		return false;
	}else if(districtSelect == undefined || districtSelect == "" ){
			$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
			return false;
	}else if(assessmentSelect == undefined || assessmentSelect == "" ){
				$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
				return false;
	}else if(subjectSelect == undefined || subjectSelect == ""){
		$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
		return false;
	}else if(testTypeSelect == undefined || testTypeSelect == "" ){
		$('#searchBtnClearTestRecord').attr('disabled','disabled').addClass('ui-state-disabled');;
		return false;
	}else {
		$('#searchBtnClearTestRecord').removeAttr('disabled').removeClass('ui-state-disabled');
	}
}