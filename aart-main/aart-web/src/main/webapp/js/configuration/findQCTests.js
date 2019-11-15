var seletedRows;
var assessmentProgramValues;
var testingProgramValues;
var assessmentValues;
var gradeCourseValues;
var contentAreaValues;	
var myDefaultSearch = 'cn';
var selectedTestids=[];
function findQCTestsInit(){
	if(!gfindQCTestsLoadOnce){
		gfindQCTestsLoadOnce = true;
		getSelectFilterValues();
		$("#dialog-confirm").dialog({
			resizable: false,
			height: 200,
			width: 500,
			modal: true,
			autoOpen:false,
			title:'QC Completed',
			buttons: {
			    Continue: function() {
			    	 $(this).dialog("close");
			    	 updateQCComplete(selectedTestids);
			    },
			    Cancel: function() {
			    	 $(this).dialog("close");
			    }
			}
		});	 
		$('.markqccomplete').on('click', function(event) {
			selectedTestids = [];
			seletedRows = $('#testsQCTable').jqGrid('getGridParam', 'allselectedrows').map(String);
			if( seletedRows.length > 0){
				$("#dialog-confirm").html('Are you sure you want to mark the selected test(s) as QC Complete?');
				$("#dialog-confirm").dialog('open');
				for(var i =0; i < seletedRows.length;i++ ){
					var selectedRowData = seletedRows[i].split("-");
					selectedTestids.push(selectedRowData[0]);
				}
			}else{
				alert('Please Select atleast one');
	 		}
		});
		
		$("#RemoveDialog-confirm").dialog({
			resizable: false,
			height: 200,
			width: 500,
			modal: true,
			autoOpen:false,
			title:'QC Completed',
			buttons: {
			    Continue: function() {
			    	 $(this).dialog("close");
			    	 removeQCComplete(selectedTestids);
			    },
			    Cancel: function() {
			    	 $(this).dialog("close");
			    }
			}
		});
		
			$('.removeQCComplete').on('click', function(event) {
				selectedTestids = [];
				seletedRows = $('#testsQCTable').jqGrid('getGridParam', 'allselectedrows').map(String);
				if( seletedRows.length > 0){
					$("#RemoveDialog-confirm").html('Are you sure you want to remove the selected test(s) as QC Complete?');
					$("#RemoveDialog-confirm").dialog('open');
					for(var i =0; i < seletedRows.length;i++ ){
						var selectedRowData = seletedRows[i].split("-");
						selectedTestids.push(selectedRowData[0]);
					}
				}else{
					alert('Please Select atleast one');
		 		}
			});
		
	}
	initQCGrid();
};

function initQCGrid() {
	var $gridAuto = $("#testsQCTable");
	$("#testsQCTable").jqGrid('clearGridData');
	$("#testsQCTable").jqGrid("GridUnload");
	var gridWidthForVO = $('#testsQCTable').parent().width();		
	if(gridWidthForVO < 1035) {
		gridWidthForVO = 1035;				
	}
	if($gridAuto[0].grid && $gridAuto[0]['clearToolbar']){
		$gridAuto[0].clearToolbar();
	}
	var colModel =  [		
							{name: 'externalId', label: 'CB Test ID', index: 'externalId', hidden:false, search: true, sortable:true},
	       					{name: 'collectionName', label: 'Test Collection Name', index: 'collectionName', search: true,formatter: tcNameFormatter, unformat: tcNameUnFormatter, align: 'center',sortable:true},
	    	   				{name: 'testName', label: 'Test Name', index: 'name', formatter: linkFmatter, search: true, unformat: linkUnFmt, align: 'center',sortable:true},
	    	   				{name: 'testId', label: 'EP Test ID', index: 'testId', hidden:true, search: true, sortable:true},
	    	   				{name: 'testInternalName', label: 'Test Internal Name', index: 'testInternalName',search: true, align: 'center',sortable:true},
	                        {name: 'contentAreaName', label: $('#currentAssessmentProgram').val()=="PLTW" ? 'Course' : 'Subject', index: 'contentAreaName',search: true, stype : 'select', searchoptions: { value : contentAreaValues, sopt:['eq'] }, align: 'center',sortable:true},
	                        {name: 'gradeCourseName', label: 'Grade', index: 'gradeCourseName',search: true, stype : 'select', searchoptions: { value : gradeCourseValues, sopt:['eq'], style: "width:100%;"}, align: 'center',sortable:true},
	    	   				{name: 'assessmentProgramName', label: 'Assessment Program', search : true, stype : 'select', searchoptions: { value : assessmentProgramValues, sopt:['eq'], style: "width:100%;"}, index: 'assessmentProgramName',sortable:true},	                       
	    	                {name: 'testingProgramName', label: 'Testing Program', search: true, stype : 'select', searchoptions: { value : testingProgramValues, sopt:['eq']}, index: 'testingProgramName', align: 'center',sortable:true},
	                        {name: 'assessmentName', label: 'Assessment', search: true, stype : 'select', searchoptions: { value : assessmentValues, sopt:['eq'] }, index: 'assessmentName', align: 'center',sortable:true},
	    	                {name: 'qcComplete', label: 'QC Complete', stype:'select', formatter: qcFormatter, searchoptions:{value:":All;true:Yes;false:No", style: "width:100%;"},index: 'qcComplete',search: true,align: 'center',sortable:true}
	    	               ]        
	$("#testsQCTable").scb({
		url : "findQCTest.htm",
		mtype: "POST",
		datatype : "json",
		width: gridWidthForVO,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
        colModel: colModel,     
		pager : '#testsQCPager',
		multiselect: true,
		localReader: {
	        page: function (obj) {
	            return obj.page !== undefined ? obj.page : "0";
	        }
	    },
		jsonReader: {
        	repeatitems:false,
        	page: function (obj) {
 	            return obj.page !== undefined ? obj.page : "0";
 	        },
	    	root: function(obj) { 
	    		return obj.rows;
	    	} 
	    },
	    beforeRequest: function() {
	    	var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');

	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	    },
		loadComplete: function(data) {
			 for (var i = 0; i < data.rows.length; i++) {
                 var rowData = data.rows[i];
                 if (rowData.qcComplete) {
 	            	 if($("#jqg_testsQCTable_" + data.rows[i].id).prop("checked")){
 	            		 $("#testsQCTable").jqGrid('setSelection',data.rows[i].id, true);

 	            	 }
                 }
             }
			 $(this).jqGrid('filterToolbar', {stringResult: true, searchOnEnter: true, defaultSearch: myDefaultSearch});
			
			 var ids = $(this).jqGrid('getDataIDs');         
	         var tableid=$(this).attr('id'); 
	                
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title',$(this).getCell(ids[i], 'collectionName') +' '+$($(this).getCell(ids[i], 'testName')).text()+' (Ep Test Id: '+$(this).getCell(ids[i], 'testId')+ ') Check Box');
	                $('#jqg_'+tableid+'_'+ids[i]).removeAttr("aria-checked");
	            }
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	             $('#cb_'+tableid).attr('title','Qc tests Grid All Check Box');
	             $('#cb_'+tableid).removeAttr("aria-checked");
	             $.each(objs, function(index, value) {         
	              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
	                    $(value).attr('title',$(nm).text()+' filter');
	                    
	                   if ( $(value).is('select')) {
	                	   $(value).removeAttr("role");
	                	   $(value).css({"width": "100%"});
	                	   
	                    	$(value).select2({
	              	   		  placeholder:'Select',
	            	   		  multiple: false,
	            	      		  allowClear : true
	            	   		 });
	                    };
	                    });
	             $('td[id^="view_"]').click(function(){
					  	$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
					}); 
	        	 $('.select2-hidden-accessible').removeAttr("aria-hidden");
		},
		onSelectRow: function(rowid, status,e){
			enableAndDisableQc();
			
	     	},
		onSelectAll: function(aRowids,status) {
	        if (status) {
 	            $(this).find("tr.jqgrow:has(td > input.cbox:disabled)").map(function() {
	            	$("#testsQCTable").jqGrid('setSelection',this.id, true);
	            });
 	            $(this)[0].p.selarrrow = $(this).find("tr.jqgrow:has(td > input.cbox:checked)").map(function() { 
 	            	return this.id; 
	            }).get(); 
 	            
 	            $("#cb_testsQCTable").attr("checked", true);
	        }
	        
	        enableAndDisableQc();
	    }
	});	
};

function tcNameFormatter(cellvalue, options, rowObject) {
	//show both manual and system test collection names
	//for the qc control page
	return rowObject.collectionName;
};

function tcNameUnFormatter(cellvalue, options, rowObject) {
    return;
};

function qcFormatter(cellvalue, options, rowObject) {
	if(cellvalue){
        return "Yes";
	} else
		return "No"; 
};

function linkFmatter(cellvalue, options, rowObject) {
	var htmlString = "";
	if(rowObject.canPreview) {
		htmlString = "<a href='javascript:openTestDialog("+rowObject.testCollectionId+ "," +rowObject.testId+");'>" + rowObject.name + "</a>";
	} else {
		htmlString = rowObject.name;
	}	
	if(rowObject) {
		htmlString = "<a href='javascript:openTestDialog("+rowObject.testCollectionId+ "," +rowObject.testId+");'>" + rowObject.name + "</a>";
	} else {
		htmlString = rowObject.name;
	}
    return htmlString;
};

function linkUnFmt(cellvalue, options, rowObject) {
    return;
};

function openTestDialog(testCollectionId, testId) {		
	$('#previewQCTestDiv').dialog({
		autoOpen: false,
		modal: true,			
		width: 1080,
		height: 655,
		title:'',			
		close: function(ev, ui) {				
			$('#setupTestSessionTabs li:eq(0) a').tab('show');
		    $("#testsQCTable").jqGrid('setSelection',testId);
		    $("#testsQCTable").resetSelection();
		    $(this).html('');
		}
	}).load('previewTest.htm?&selectedTestCollectionId='+testCollectionId + '&selectedTestId='+testId).dialog('open');	
}


function updateQCComplete(testIds) {
	$.ajax({
		url: 'updateQCComplete.htm',
		data: {
			qcComplete: true,
			testIds: testIds.join(',')
		},
		dataType: 'json',
		cache: false,
		type: "POST"
	}).done(function (data) { 
		$("#testsQCTable").trigger("reloadGrid"); 
		$("#removeQCCompleteButton").prop("disabled",false);
		$('#removeQCCompleteButton').removeClass('ui-state-disabled');    
		$("#markQcCompleteButton").prop("disabled",false);
		$('#markQcCompleteButton').removeClass('ui-state-disabled');
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}

function removeQCComplete(testIds) {
	$.ajax({
		url: 'removeQCComplete.htm',
		data: {
			qcComplete: false,
			testIds: testIds.join(',')
		},
		dataType: 'json',
		cache: true,
		type: "POST"
	}).done(function (data) { 
		$("#testsQCTable").trigger("reloadGrid");
		$("#removeQCCompleteButton").prop("disabled",false);
		$('#removeQCCompleteButton').removeClass('ui-state-disabled');    
		$("#markQcCompleteButton").prop("disabled",false);
		$('#markQcCompleteButton').removeClass('ui-state-disabled');
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}
function enableAndDisableQc(){
	var qcCompleteArray = [];
    var qcValues = false;
    var qcValueForYes = false;
    var qcValueForNo = false;
      var selectedrows = $("#testsQCTable").jqGrid('getGridParam', 'selarrrow');
      if (selectedrows.length) {
          for (var i = 0; i < selectedrows.length; i++) {
              var selecteddetails = $("#testsQCTable").jqGrid('getRowData', selectedrows[i]);
              if(selecteddetails.qcComplete === "Yes"){
              	qcValueForYes = true;
              }
              else if(selecteddetails.qcComplete === "No"){
              	qcValueForNo = true;
              }
              var qcCompleteObj = { qcComplete: selecteddetails.qcComplete };
              qcCompleteArray.push(qcCompleteObj);
          }
      }
      for (var i = 0; i < qcCompleteArray.length - 1; i++) {
          if (qcCompleteArray[i].qcComplete != qcCompleteArray[i + 1].qcComplete) {
          	qcValues = true;
          }
          else if((qcCompleteArray[i].qcComplete === "Yes") && (qcCompleteArray[i].qcComplete == qcCompleteArray[i + 1].qcComplete)){
          	qcValueForYes = true;
          }
          else if((qcCompleteArray[i].qcComplete === "No") && (qcCompleteArray[i].qcComplete == qcCompleteArray[i + 1].qcComplete)){
          	qcValueForNo = true;
          }
      }
      
    	if(qcValues){
			$("#removeQCCompleteButton").prop("disabled",true);
			$('#removeQCCompleteButton').addClass('ui-state-disabled');    
			$("#markQcCompleteButton").prop("disabled",true);
			$('#markQcCompleteButton').addClass('ui-state-disabled');  
    	}else if(qcValueForYes){
			$("#markQcCompleteButton").prop("disabled",true);
			$('#markQcCompleteButton').addClass('ui-state-disabled');
			$("#removeQCCompleteButton").prop("disabled",false);
			$('#removeQCCompleteButton').removeClass('ui-state-disabled'); 
    	}else if(qcValueForNo){
			$("#removeQCCompleteButton").prop("disabled",true);
			$('#removeQCCompleteButton').addClass('ui-state-disabled');
			$("#markQcCompleteButton").prop("disabled",false);
			$('#markQcCompleteButton').removeClass('ui-state-disabled');
    	}else{
			$("#removeQCCompleteButton").prop("disabled",false);
			$('#removeQCCompleteButton').removeClass('ui-state-disabled');    
			$("#markQcCompleteButton").prop("disabled",false);
			$('#markQcCompleteButton').removeClass('ui-state-disabled');
    	}
	
}
function getSelectFilterValues() {
	return $.ajax({
		  url: 'getSelectAssessmentsDropdownData.htm',            
	      dataType: 'json',
	      type: "GET",
	      async: false
	}).done(function (data) { 
		assessmentProgramValues = ":All";
   	 	for (var i=0; i<data.assessmentPrograms.length; i++) {
   	 		assessmentProgramValues += ";" + data.assessmentPrograms[i].programName 
   	 			+ ":" + data.assessmentPrograms[i].programName;
   	 	}
   	 	
   	 	testingProgramValues = ":All";
	 	for (var i=0; i<data.testingPrograms.length; i++) {
	 		testingProgramValues += ";" + data.testingPrograms[i].programName 
	 			+ ":" + data.testingPrograms[i].programName;
	 	}
	 	
	 	assessmentValues = ":All";
   	 	for (var i=0; i<data.assessments.length; i++) {
   	 			assessmentValues += ";" + data.assessments[i].assessmentName
   	 			+ ":" + data.assessments[i].assessmentName;
   	 	}
   	 	
   	 	gradeCourseValues = ":All";
	 	for (var i=0; i<data.gradeCourses.length; i++) {
	 		gradeCourseValues += ";" + data.gradeCourses[i].name 
	 			+ ":" + data.gradeCourses[i].name;
	 	}
	 	
	 	contentAreaValues = ":All";
   	 	for (var i=0; i<data.contentAreas.length; i++) {
   	 		contentAreaValues += ";" + data.contentAreas[i].name 
   	 			+ ":" + data.contentAreas[i].name;
   	 	}
	}).fail(function (jqXHR, textStatus, errorThrown) { 
		console.log(errorThrown);
	});
}

