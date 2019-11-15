var assessmentProgramForEditOperational = null;
var subjectEditNumOfTestForOTWindowAssignedBefore = true;
var copyExistingWindow = false;
function methodToBeLoadedWithPopup(id,assessmentProgramName,assessmentProgramId,copyWindow){
	$('#editDailyAccessCodeTestwindowstartTime').val("");
	$('#editDailyAccessCodeTestwindowendTime').val("");
	$('#editTicketingoftheday').hide();
	$("#editOperationalTestWindowSetupsave").hide();
	$("#editOperationalTestWindowSetupcancel").hide();
	$('#editAssessmentProgramsAddNewTestWindowSelectText').val(assessmentProgramName);
	$('#editAssessmentProgramsAddNewTestWindowSelect').val(assessmentProgramId);
	$('#editAssessmentProgramsAddNewTestWindowSelect').hide();
	assessmentProgramForEditOperational =  assessmentProgramId;
    fillEditWindowValues(id);
    $('#actualRandomizationValue').val($("#editrandomizedCodeSelect").val());
    $('#actualManagedByCode').val($("#editManagedbyCodeSelect").val());    
    loadAdminOptionsData(id);
	loadEditOperationalTestCollectionWindow(id);
	loadExistingTestWindowCollections(id);
	$("#changeViewMode").hide();
	$("#changeEditMode").hide();
	copyExistingWindow = copyWindow;
	$("#editTestWindowStartDate").datepicker({
	   dateFormat: "mm/dd/yy"
	}).datepicker();
	editTestWindowStateSeleted(id);		
	$("#editTestWindowEndDate").datepicker({
	    dateFormat: "mm/dd/yy"
	}).datepicker();
	hideEditErrorMessages();
	editPopulateTestEnrollmentMethod(id,assessmentProgramId);
	editScoringWindowMethod(id,assessmentProgramId);	
	$("#editTestwindowScoringStartDate").datepicker({
	    dateFormat: "mm/dd/yy"
	}).datepicker();
	$("#editTestWindowScoringEndDate").datepicker({
	    dateFormat: "mm/dd/yy"
	}).datepicker();
	$('#editScoringWindowMethod').select2({
		placeholder:'Select',
		multiple: false
	});
		
/*		$('.editTicketing').on('change',function(){
	    	  var editTicketingFlag = $(this).val();
	    	 // $('#editTicketingDaySelect').val("0").trigger('change.select2');
	    	  if(editTicketingFlag == 'true'){
	    	    $('#editTicketingoftheday').show();
	    	  }else{
	    	    $('#editTicketingoftheday').hide();
	    	  }
	    });*/
		
		 $('.editticketing').on("change",function(){
		        var ticketingFlag = $(this).val();
		      //  $('#dailyAccessCodeTimeFrameDiv').val("0").trigger('change.select2');
		        if(ticketingFlag == 'DAILY_ACCESS_CODE'){
		          $('#editDailyAccessCodeTimeFrameDiv').show();
		        }else{
		          $('#editDailyAccessCodeTimeFrameDiv').hide();
		          $('#editDailyAccessCodeTestwindowstartTime').val("");
		      	  $('#editDailyAccessCodeTestwindowendTime').val("");
		        }
		     });
	    
	    $('.editTestEnrollment').on('change',function(){
	    	var editTestEnrollmentFlag = $(this).val();
	        $('#editTestEnrollmentMethod').val("0").trigger('change.select2');
	        $('#editTestEnrollmentMethod').trigger('change');
	    	if(editTestEnrollmentFlag == 'true'){
	    		subjectEditNumOfTestForOTWindowAssignedBefore = false;
	    		$('.editTestEnrollmentDiv').show();
	    	}else{
	    		$('.editTestEnrollmentDiv').hide();
	    		$('#selectUpdateNumbeOfTestBySubjectGrid').hide();
	    	}
	   	});
	    
	    $('.editScoringWindow').on('change',function(){	    	
	    	var editScoringWindowFlag = $(this).val();
	    	$('#editScoringWindowMethod').val("0").trigger('change.select2');
	    	if(editScoringWindowFlag == 'true'){
	    		$('.editScoringWindowMethoddiv').show();
	    		$('#editScoringAvailableTimeFrameDiv').show();
	    		$('#editTestwindowScoringStartDate').val('');
	    		$('#editTestWindowScoringEndDate').val('');
	    		$('#editTestWindowScoringStartTime').val('');
	    		$('#editTestWindowScoringEndTime').val('');
	    		
	    	}else{
	    		$('.editScoringWindowMethoddiv').hide();
	    		$('#editScoringAvailableTimeFrameDiv').hide();
	    	}
	    });
	    
	    $('.editIAPWindow').on('change',function(){	    	
	    	var flag = $(this).val();
	    	if (flag == 'true'){
	    		$('#iapWindow_edit').show();
	    	} else {
	    		$('#iapWindow_edit').hide();
	    	}
	    });
	    
	    $('#selectUpdateNumbeOfTestBySubjectGrid').hide();
	    
	    $('#editTestEnrollmentMethod').on("change",function(){
	    	editOtwNoOfTestForSubjects();
	    });
	    

}

function editTestWindowStateSeleted(id){
	$('#editStatebyTestWindowSelect').html('');
	$('#editStatebyTestWindowSelect').trigger('change.select2');
	$.ajax({
        url: 'getStatesBesedOnAssessmentProgram.htm',
        data: {assessmentProgramId:assessmentProgramForEditOperational},
        dataType: 'json',
        type: "GET"
	}).done(function(states) {		        	
    	$('#editStatebyTestWindowSelect').trigger('change.select2');
    	if(!copyExistingWindow){
			$.ajax({
		        url: 'getOperationalTestWindowSelectedStatesForUser.htm',
		        data: {operationalTestWindowId:id },
		        dataType: 'json',
		        type: "GET"
			}).done(function(instates) {
	        	for (var key in instates) {		    			     
	        		   $('#editStatebyTestWindowSelect').append($('<option></option>').attr("value",key).text(instates[key]));		    			        		
	        	 }
	        	$('#editStatebyTestWindowSelect option').prop('selected', true);
	        	$('#editStatebyTestWindowSelect').trigger('change.select2');
	        	
	        	
	        	for (var key in states) {
	        		if (!$('#editStatebyTestWindowSelect').find('option[value='+key+']').length > 0) {
	        	         $('#editStatebyTestWindowSelect').append($('<option></option>').attr("value",key).text(states[key]));
	        		}
	        	 } 
	        	 $('#editStatebyTestWindowSelect').trigger('change.select2');
			 });
		 } else {
			 for (var key in states) {
	        		if (!$('#editStatebyTestWindowSelect').find('option[value='+key+']').length > 0) {
	        	         $('#editStatebyTestWindowSelect').append($('<option></option>').attr("value",key).text(states[key]));
	        		}
	        	 } 
	        	 $('#editStatebyTestWindowSelect').trigger('change.select2');		    			 
		 }
	});
	$('#editStatebyTestWindowSelect').trigger('change.select2');
}

function editScoringWindowMethod(id,assessmentProgramId){
	$('#editScoringWindowMethod').find('option').filter(function(){return $(this).val().length > 0;}).remove().end();	
	 $.ajax({
	     url: 'getScoringAssignmentMethod.htm',
	     data: {assessmentProgramId:assessmentProgramId },
	     dataType: 'json',
	     type: "GET"
	 }).done(function(editScoringWindowMethod) {
   	  	if(editScoringWindowMethod != null ){
   		  var obj = editScoringWindowMethod;
   		  var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);	
   		  for (var key in obj) {
   			  $('#editScoringWindowMethod').append($('<option></option>').attr("value",key).text(obj[key]));  
   			  if(obj[key] == rowData.ScoringWindowName){
   				  $('#editScoringWindowMethod').val(key).prop("selected", true);
   			  }
   		  }
   		  $('#editScoringWindowMethod').trigger('change.select2');
   	  	}   
	});
		$('#editScoringWindowMethod').trigger('change.select2');
}


function editPopulateTestEnrollmentMethod(id,assessmentProgramId){
	$('#editTestEnrollmentMethod').find('option').filter(function(){return $(this).val().length > 0;}).remove().end();	
	$.ajax({
		url: 'getTestEnrollmentMethod.htm',
		data: {assessmentProgramId:assessmentProgramId },
		dataType: 'json',
		type: "GET"
	}).done(function(editTestEnrollmentMethod) {
		if(editTestEnrollmentMethod != null ){
			var obj = editTestEnrollmentMethod;
			var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);	
			for (var key in obj) {
				$('#editTestEnrollmentMethod').append($('<option></option>').attr("value",key).text(obj[key]));
			  	if(obj[key] == rowData.TestEnrollmentMethod){
			  		$('#editTestEnrollmentMethod').val(key).prop("selected", true);
			  	}
			}
			$('#editTestEnrollmentMethod').trigger('change.select2');
			editOtwNoOfTestForSubjects();
		} 
	});
	$('#editTestEnrollmentMethod').trigger('change.select2');
}

function editOtwNoOfTestForSubjects(){

	  var selectedTestEnrollmentMethod = $('#editTestEnrollmentMethod').find('option:selected').html();
	  
	  $('#selectUpdateNumbeOfTestBySubjectContainer').data("selectedTestEnrollmentMethod", selectedTestEnrollmentMethod);
	  if( (selectedTestEnrollmentMethod == 'Student Tracker Field Test') || (selectedTestEnrollmentMethod == 'Multi-assign') ){
		  $("#selectUpdateNumbeOfTestBySubject").jqGrid("GridUnload");
		  var windowId=$("#hdnWindowId").val();
		  var mydata1 = []; 
		  $.ajax({
				url: 'getContentAreasWithNumberOfTestForOTWindow.htm',
				data:{"id":windowId},
				type: 'POST',
				dataType: 'json'
		  }).done(function(data) {		            			            
			    	if(data=='' || data == null){
			    		var assessmentProgramId = $("#editAssessmentProgramsAddNewTestWindowSelect").val();			    		
			    		$.ajax({
		    				url: 'getContentAreasByAssessmentProgramForTestRecord.htm',
		    				data:{"assessmentProgramId":assessmentProgramId},
		    				type: 'POST',
		    				dataType: 'json'
			    		}).done(function(datax) {		            			             	
	    			    	$.each(datax,function(index,value){
	    			    		mydata1[index] = {
	    			    				id:datax[index].id,
	    			    				name :datax[index].name
	        					    };
	    			    	});
	    			    	loadNumberOfTestBySubjectforEditOTW(mydata1);
			    		});
			    		subjectEditNumOfTestForOTWindowAssignedBefore = false;
			    	}else{
			    		$.each(data,function(index,value){
				    		mydata1[index] = {
				    				id :data[index].contentareaid,
				    				name :data[index].name,
				    				numberofTest :data[index].numberoftests
	  					    };
				    	});
			    		
			    		loadNumberOfTestBySubjectforEditOTW(mydata1);
			    		subjectEditNumOfTestForOTWindowAssignedBefore = true;
			    	} 
		  }); 
		  $('#selectUpdateNumbeOfTestBySubjectGrid').show();
	  }else{
		  $('#selectUpdateNumbeOfTestBySubjectGrid').hide();
	  }

}

function loadNumberOfTestBySubjectforEditOTW(mydata1){
	
	$("#selectUpdateNumbeOfTestBySubject").scb({
    	datatype: "local",
	    data: mydata1,
     	colNames:['Id' ,'Name', 'Number Of Tests'],
     	colModel :[
     	      {name: 'id', index: 'id', hidden:true, sortable : false, search: false},
	 		  {name: 'name', index: 'name', hidden:false, sortable : true, search: false},
     	      {name:'numberofTest',index: 'numberofTest', hidden:false, formatter: numberEditOfTestsBySubjectFormatter, sortable : false, search: false}
		],
     	pager : '#selectUpdateNumbeOfTestBySubjectPager',
     	loadonce: false,
     	viewrecords: true,
     	multiselect:false,
		search: false,
     	scroll:true,
     	width:'170px',
     	loadComplete: function(){
     		var selectUpdateNumbeOfTestBySubjectGridinput = '' ;
     		$('#selectUpdateNumbeOfTestBySubjectGrid table tr').find("input[id^=inp_UpdateNumberOfTests_Subject_]").on("change",function(){
     		  selectUpdateNumbeOfTestBySubjectGridinput = $(this).val();
     		  if(!(/^[0-9]+$/.test(selectUpdateNumbeOfTestBySubjectGridinput)) || selectUpdateNumbeOfTestBySubjectGridinput > 99 || selectUpdateNumbeOfTestBySubjectGridinput < 0 || selectUpdateNumbeOfTestBySubjectGridinput == '' ){
     			$('#editOperationalTestWindowErrorMessages, #errorEditNumberOfTestWholeNumber').show();
     			scrollPageTopEdit('errorEditNumberOfTestWholeNumber');
     			$(this).val('0');
     		  }else{
     			$('#editOperationalTestWindowErrorMessages, #errorEditNumberOfTestWholeNumber').hide();
     		  }
     		});
     	}
	});

	$('#gview_selectUpdateNumbeOfTestBySubject .ui-search-toolbar, #selectUpdateNumbeOfTestBySubjectPager').css('display','none');
	
}

function editTestWindowInitMethod(id){
	$('#editrandomizedCodeSelect').on("change",function(){
		clearTestCollectionWindow();
  	});
	
    $('#editManagedbyCodeSelect').on("change",function(){
    	if($("#editManagedbyCodeSelect").val() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST'){
    		$('#editrandomizedCodeSelect').val('');
        	$('#editrandomizedCodeSelect').trigger('change.select2');
        }
		clearTestCollectionWindow();
  	});
    function clearTestCollectionWindow(){
	    	if($('#editManagedbyCodeSelect').val()=='MANUAL_DEFINED_ENROLLMENT_TO_TEST'){
				$('.group2').hide();
			 } else {
				$('.group2').show();
			 }
		$('#editSelectTestGridTableId').jqGrid('clearGridData');
		$('#editRightSelectTestGridTableId').jqGrid('clearGridData');
    }
	$('#editOperationalTestWindowViewSetup :input').attr('readonly','readonly');
	var tcGridWidth = 408;
	var tcGridHeight = 310;
	var tcCellWidth = tcGridWidth/3;
	var $gridAuto = $("#editSelectTestGridTableId");
	$gridAuto.scb({
	 	datatype: "local",
		rowNum: 100000,
 	 	rows: 100000,
	 	colNames:['Internal ID','External ID','Test Collection Name'],
	 	colModel:[
	 	      {name: 'id', index: 'id',width:tcCellWidth, search:true,sortable : true,key:true,hidedlg: false,hidden: true},
	 	      {name: 'testCollectionId', index: 'testCollectionId',width:tcCellWidth,search:true,sortable : true,hidedlg: true},
	 	      {name: 'name', index:'name', width:tcCellWidth, search:true,sortable : true,hidedlg: true}             
         ],
        width: tcGridWidth,
        height: tcGridHeight,
        viewrecords: true,
 	    scroll:false,
 	    multiselect:true,
 	    pager : '#peditSelectTestGridTableId',
 	    shrinkToFit: false,
        loadonce : true,
	    sortname : 'name',
	    ignoreCase: true,
	    rowList: [],        // disable page size dropdown
	    pgbuttons: false,     // disable page control like next, back button
	    pgtext: null,         // disable pager text like 'Page 0 of 10'
	    viewrecords: false,
	    loadComplete: function () {
	  		gridName = this.id;
	  		editReSizeTestWindowGridBasedOnContent(gridName);
	  		 var ids = $(this).jqGrid('getDataIDs');     
	  		var tableid=$(this).attr('id');    
	  		for(var i=0;i<ids.length;i++)
            {         
                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'name')+ ' Check Box');
                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
            }
	  		var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	  		$('#cb_'+tableid).attr('title','Available Collection Grid All Check Box');
	  		$('#cb_'+tableid).removeAttr('aria-checked');
	  		$.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
	  }
 	});
     
     $("#changeEditMode").on("click",function(){
	   var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);	
	   $('#otw_id').text(rowData.otwId);
	   $("#editOperationalTestWindowSetupsave").show();
	   $("#editOperationalTestWindowSetupcancel").show(); 
	   $("#changeEditMode").hide();
	   $("#changeViewMode").show();
	   $('#editTestCollectionEditwindow').hide();
	   $('#opt_windowIdDiv').show();
	   copyExistingWindow = false;
	   $('#editOperationalTestWindowViewMode').hide();
	   $('#editOperationalTestWindowEditMode').show();
	   $('#editOperationalTestWindowCopyMode').hide();
	   $('#editOperationalTestWindowViewSetup :input').attr('readonly',false);
	   //$('#editAssessmentProgramsAddNewTestWindowSelectText').attr('readonly',true);
	   $('#editAssessmentProgramsAddNewTestWindowSelectText').prop('disabled',true);
	   $('#editOperationalTestWindowViewTitleMode').html($('#editOperationalTestWindowViewTitleMode').html().replace('View', 'Edit'));
       $('#OpTestWindowSetupMakereadableDiv').css({'position':'absolute','height':0,'width':0,"margin-top":"0px","z-index":999});
	});
   
     $("#changeCopyMode").on("click",function(){
    	 copyExistingOperationalTestWindow();
    	 $('#editOperationalTestWindowViewTitleMode').html($('#editOperationalTestWindowViewTitleMode').html().replace('View', 'Copy from '+  $('#hdnWindowName').val()));
     });
     
   $("#editRightSelectTestGridTableId").scb({
		datatype: "local",
		rowNum: 100000,
 	 	rows: 100000,
	 	colNames:['Internal ID','External ID','Test Collection Name'],
	   	colModel:[
	   	    {name: 'id', index: 'id',width:tcCellWidth, search:true,sortable : true,key:true,hidedlg: false,hidden: true},
	   		{name:'testCollectionId', index:'testCollectionId', width:tcCellWidth, sortable: true, search:true,hidedlg: true},
	   		{ name:'name', index:'name', width:tcCellWidth, sortable: true, search:true,hidedlg: true}
	   	],
        width: tcGridWidth,
        height: tcGridHeight,
	   	viewrecords: true,
 	   	scroll:false,
 	   	multiselect:true,
 	    ignoreCase: true,
 	    shrinkToFit: false,
 	    pager : '#peditRightSelectTestGridTableId',
 	    rowList: [],        // disable page size dropdown
	    pgbuttons: false,     // disable page control like next, back button
	    pgtext: null,         // disable pager text like 'Page 0 of 10'
	    viewrecords: false,
	    loadComplete: function () {
	  		gridName = this.id;
	  		editReSizeTestWindowGridBasedOnContent(gridName);
	  		 var ids = $(this).jqGrid('getDataIDs');  
	  		var tableid=$(this).attr('id');    
	  		for(var i=0;i<ids.length;i++)
            {         
                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'name') + ' Check Box');
                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
            }
	  		var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	  		$('#cb_'+tableid).attr('title','Assigned Collection Grid All Check Box');
	  		$('#cb_'+tableid).removeAttr('aria-checked');
	  		$.each(objs, function(index, value) {         
              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                    $(value).attr('title',$(nm).text()+' filter');                          
                    });
	  }
	});

   $("#editRightSelectTestGridTableId").jqGrid('filterToolbar');

   
   $("#editAddInoperationalTestcollection").off("click").on('click',function(){
		
	   var allRightSelectedRowsInGrid = $("#editRightSelectTestGridTableId").jqGrid('getDataIDs');
	   var rowIds = $("#editSelectTestGridTableId").jqGrid('getGridParam','selarrrow');
		
		var rowIds1 = rowIds;
		
		if (rowIds != null && rowIds.length > 0) {
			
			for ( var i = 0;  i < rowIds.length; i++) {
				var count =0;
				for(var j = 0; j < allRightSelectedRowsInGrid.length; j++ ){
				     if(allRightSelectedRowsInGrid[j] == rowIds[i]){
					    count++;
				     }
				}
				if(count == 0){
					var rowObj = $("#editSelectTestGridTableId").jqGrid('getRowData',
							rowIds[i]);
					$("#editRightSelectTestGridTableId").jqGrid("addRowData", rowObj.id, rowObj, "last");
				}
			}	
			
			while(rowIds1.length > 0){
				$("#editSelectTestGridTableId").delRowData(rowIds1[rowIds1.length-1]);
			}
			
		}
		$("#editSelectTestGridTableId").trigger('reloadGrid');
		$("#editRightSelectTestGridTableId").trigger('reloadGrid');
		$('#cb_editSelectTestGridTableId').prop('checked',false);
	});
	
	$("#editMoveOutoperationalTestcollection").off("click").on('click',function(){
		
		 var allLeftSelectRowsInGrid = $('#editSelectTestGridTableId').jqGrid('getDataIDs');
		 var rowIds = $("#editRightSelectTestGridTableId").jqGrid('getGridParam','selarrrow');
			
         var rowIds1 = rowIds;
								
				if (rowIds != null && rowIds.length > 0) {
					
					for ( var i = 0;  i < rowIds.length; i++) {
						var count =0;
						for(var j = 0; j < allLeftSelectRowsInGrid.length; j++ ){
						     if(allLeftSelectRowsInGrid[j] == rowIds[i]){
							    count++;
						     }
						}
						if(count == 0){
							var rowObj = $("#editRightSelectTestGridTableId").jqGrid('getRowData',
									rowIds[i]);
							$("#editSelectTestGridTableId").jqGrid("addRowData", rowObj.id, rowObj, "last");
						}
					}	
				
				while(rowIds1.length > 0){
					$("#editRightSelectTestGridTableId").delRowData(rowIds1[rowIds1.length-1]);
				}
			}
			 //$("#editRightSelectTestGridTableId").trigger('reloadGrid');
			 $("#editSelectTestGridTableId").trigger('reloadGrid');
			 $("#editRightSelectTestGridTableId").trigger('reloadGrid');
				$('#cb_editRightSelectTestGridTableId').prop('checked',false);
	});
   
   
	$("#editOperationalTestWindowSetupcancel").off("click").on('click',function(){
		$("#editOperationalTestWindowSetupcancel").prop("disabled",true);
		//var id = $("#hdnWindowId").val();
		fillEditWindowValues(id);
	    loadAdminOptionsData(id);
	    $('#editStatebyTestWindowSelect').val("0").trigger('change.select2');
		loadEditOperationalTestCollectionWindow(id);
		loadExistingTestWindowCollections(id);
		editPopulateTestEnrollmentMethod(id,assessmentProgramForEditOperational);
		editScoringWindowMethod(id,assessmentProgramForEditOperational);	
		$("#editTestWindowStartDate").datepicker({
			   dateFormat: "mm/dd/yy"
			}).datepicker();
					
			$("#editTestWindowEndDate").datepicker({
			    dateFormat: "mm/dd/yy"
			}).datepicker();
			$("#editTestwindowScoringStartDate").datepicker({
			    dateFormat: "mm/dd/yy"
			}).datepicker();
			$("#editTestWindowScoringEndDate").datepicker({
			    dateFormat: "mm/dd/yy"
			}).datepicker();
			hideEditErrorMessages();
			if(copyExistingWindow){
				$('#status_otw').text('BEGIN');
				$("#editWindowName").val('');
				$("#editTestWindowStartDate").val('');
				$("#editTestWindowEndDate").val('');
				$("#editTestWindowStartTime").val('');
				$("#editTestWindowEndTime").val('');
				$("#editDailyAccessCodeTestwindowstartTime").val('');
				$("#editDailyAccessCodeTestwindowendTime").val('');
				
				$("#editTestwindowScoringStartDate").val('');
				$("#editTestWindowScoringEndDate").val('');
				$("#editTestWindowScoringStartTime").val('');
				$("#editTestWindowScoringEndTime").val('');
			}else{
				editTestWindowStateSeleted(id);
			}
		$("#editOperationalTestWindowSetupcancel").prop("disabled",false);	
	});

	$("#editSearchTestCollectionBtn").off('click').on('click',function(){

		if( $("#editManagedbyCodeSelect").val() == ''){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editManagedByRequiredErrorMessage").show();
			scrollPageTopEdit('editManagedByRequiredErrorMessage');
			return false;
		}
		
		if($("#editManagedbyCodeSelect").val() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST' && $("#editrandomizedCodeSelect").val() == ''){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editRandomizationRequiredErrorMessage").show();
			scrollPageTopEdit('editRandomizationRequiredErrorMessage');
			return false;
		}
		if($("#editManagedbyCodeSelect").val() == 'MANUAL_DEFINED_ENROLLMENT_TO_TEST'){
			$("#editrandomizedCodeSelect").val('');
		}		
		loadEditOperationalTestCollectionWindow(id);		
		
		if($('#actualRandomizationValue').val() == $("#editrandomizedCodeSelect").val() &&  $("#editManagedbyCodeSelect").val() ==  $('#actualManagedByCode').val()) {
			 loadExistingTestWindowCollections(id);
		 }else{
			 $('#editRightSelectTestGridTableId').jqGrid('clearGridData');
		 }
	});
	
}

function editReSizeTestWindowGridBasedOnContent(gridName){
	// Test - resize cols based on content
		
     $('.editParent').append('<span id="editWidthTest" class="editWidthTest"/>');

     //$('#gbox_' + gridName + ' .ui-jqgrid-htable,#' + gridName).css('width', '100%');
     $('#' + gridName).parent().css('width', 'inherit');

     var columnNames = $("#" + gridName).jqGrid('getGridParam', 'colModel');
     var thisWidth;

     // Loop through Cols
     for (var itm = 0, itmCount = columnNames.length; itm < itmCount; itm++) {

         var curObj = $('[aria-describedby=' + gridName + '_' + columnNames[itm].name + ']');
         
         var thisCell = $('#' + gridName + '_' + columnNames[itm].name + ' div');
         $('#editWidthTest').html(thisCell.text()).css({
                 'font-family': thisCell.css('font-family'),
                 'font-size': thisCell.css('font-size'),
                 'font-weight': thisCell.css('font-weight')
             });
         var maxWidth = Width = $('#editWidthTest').width() + 24;                                        

         // Loop through Rows
         //removed the width check for column 0 as it now seems to have some very long content (over 900 pixels)
         //causing the columns to be extremely wide
         for (var itm2 = 1; itm2 < curObj.length; itm2++) {
             var thisCell = $(curObj[itm2]);

             $('#editWidthTest').html(thisCell.html()).css({
                 'font-family': thisCell.css('font-family'),
                 'font-size': thisCell.css('font-size'),
                 'font-weight': thisCell.css('font-weight')
             });

             thisWidth = $('#editWidthTest').width();
             if (thisWidth > maxWidth) maxWidth = thisWidth;                        
         }                    

         $('#' + gridName + ' .jqgfirstrow td:eq(' + itm + '), #' + gridName + '_' + columnNames[itm].name).width(maxWidth).css('min-width', maxWidth);

     }

     $('.editWidthTest').remove();

}

function copyExistingOperationalTestWindow(){
	   	   copyExistingWindow = true; 
		   $('#editOperationalTestWindowCopyMode').text('- Copy from '+  $('#hdnWindowName').val());
		   $("#editOperationalTestWindowSetupsave").show();
		   $("#editOperationalTestWindowSetupcancel").show();
		   $('#opt_windowIdDiv').hide();
		   $("#changeEditMode").hide();
		   //$("#changeViewMode").show();
		   $('#editOperationalTestWindowViewMode').hide();
		   $('#editOperationalTestWindowEditMode').hide();
		   $('#editOperationalTestWindowViewSetup :input').attr('readonly',false);
		   $('#editAssessmentProgramsAddNewTestWindowSelectText').prop('disabled',true);
		 $('#OpTestWindowSetupMakereadableDiv').css({'position':'absolute','height':0,'width':0,"margin-top":"0px","z-index":999});
		 $('#editTestCollectionEditwindow').hide();
		 $('#editOperationalTestWindowCopyMode').show();
		 $('#status_otw').text('BEGIN');
		 $('#editStatebyTestWindowSelect').val('').trigger('change.select2');
		 $('#editWindowName').val('');
		 $('#editTestWindowStartDate').val('');
		 $('#editTestWindowEndDate').val('');
		 $('#editTestWindowStartTime').val('');
		 $('#editTestWindowEndTime').val('');
		 $("#editTestwindowScoringStartDate").val('');
			$("#editTestWindowScoringEndDate").val('');
			$("#editTestWindowScoringStartTime").val('');
			$("#editTestWindowScoringEndTime").val('');
	     subjectEditNumOfTestForOTWindowAssignedBefore = false;
}
function fillEditWindowValues(id){
	
	var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);
	
	var managedVal = '';
	if(rowData.managedBy == 'System Defined Enrollment to Test'){
		
		managedVal = 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST';
	} else if(rowData.managedBy == 'Manual Enrollment to Test'){
		
		managedVal = 'MANUAL_DEFINED_ENROLLMENT_TO_TEST';
	}
	
	// managed value
	//$('#editManagedbyCodeSelect').trigger('change.select2');
	$('#editManagedbyCodeSelect').val(managedVal).prop('selected', true);
	$('#editManagedbyCodeSelect').trigger('change.select2');
		
	//randomization
	$('#editrandomizedCodeSelect').val(rowData.randomization).prop("selected", true);
	$('#editrandomizedCodeSelect').trigger('change.select2');
	if(rowData.TestEnrollment != 'On'){
		$('.editTestEnrollmentDiv').hide();
		$("#editTestEnrollemntOff").prop('checked',true);
	}
	else{
		$("#editTestEnrollmentOn").prop('checked',true);
		$('.editTestEnrollmentDiv').show();
	}
	
	if(rowData.ScoringWindowFlag != 'On'){
		$('.editScoringWindowMethoddiv').hide();
		$('#editScoringWindowOff').prop('checked',true);
		$('#editScoringAvailableTimeFrameDiv').hide();
	}else {
		$('#editScoringWindowOn').prop('checked',true);
		$('.editScoringWindowMethoddiv').show();
		$('#editScoringAvailableTimeFrameDiv').show();
	}
	
	if (rowData.instructionPlannerWindow != 'On'){
		$('#instructionalPlannerWindowOff_edit').prop('checked', true);
		var iapWindow = $('#iapWindow_edit');
		if (iapWindow.length > 0){
			iapWindow.hide();
			var nameField = $('#instructionalPlannerDisplayName_edit', iapWindow);
			nameField.val('');
		}
	} else {
		$('#instructionalPlannerWindowOn_edit').prop('checked', true);
		var iapWindow = $('#iapWindow_edit');
		if (iapWindow.length > 0){
			iapWindow.show();
			var nameField = $('#instructionalPlannerDisplayName_edit', iapWindow);
			nameField.val(rowData.instructionPlannerDisplayName);
		}
	}
	
	$("#editWindowName").val(rowData.windowName);
	$("#editTestWindowStartDate").val(rowData.beginDate);
	$("#editTestWindowEndDate").val(rowData.endDate);
	$("#editTestWindowStartTime").val(rowData.beginTestTime);
	$("#editTestWindowEndTime").val(rowData.endTestTime);
	
	//scoring
	if(rowData.ScoringWindowFlag == 'On'){
	$("#editTestwindowScoringStartDate").val(rowData.scoringWindowStartDate);
	$("#editTestWindowScoringEndDate").val(rowData.scoringWindowEndDate);
	$("#editTestWindowScoringStartTime").val(rowData.scoringWindowStartTime);
	$("#editTestWindowScoringEndTime").val(rowData.scoringWindowEndTime);
	} else { 
		$("#editTestwindowScoringStartDate").val('');
		$("#editTestWindowScoringEndDate").val('');
		$("#editTestWindowScoringStartTime").val('');
		$("#editTestWindowScoringEndTime").val('');		
	}
	
	
	
	if(rowData.suspendWindow == 'true'){
		 $("#editWindowsuspend").prop('checked', true);
	}else{
		$("#editWindowsuspend").prop('checked', false);
	}
   
	if((new Date()).setHours(0, 0, 0, 0) == (new Date(rowData.endDate)).setHours(0, 0, 0, 0)){
		var currentDate = new Date();
		var endTestTime24hr= convert_to_24h(rowData.endTestTime);
		var beginTestTime24hr= convert_to_24h(rowData.beginTestTime);
		var currentTime = currentDate.getHours()+":"+currentDate.getMinutes()+":"+currentDate.getSeconds();
		if(currentTime > endTestTime24hr){
		    $("#status_otw").html('COMPLETED');
		}
		else if(currentTime < beginTestTime24hr){
			$("#status_otw").html('BEGIN');
		}
		else if(currentTime >= beginTestTime24hr && currentTime <= endTestTime24hr){
			$("#status_otw").html('IN PROGRESS');
		}
	}
	else if((new Date()).getTime()>(new Date(rowData.endDate)).getTime()){
			$("#status_otw").html('COMPLETED');
	}
	
	else if(new Date().getTime()<(new Date(rowData.beginDate)).getTime()){
		$("#status_otw").html('BEGIN');
    }
	
	else if(new Date().getTime() <= (new Date(rowData.endDate)).getTime() && new Date().getTime() >= (new Date(rowData.beginDate)).getTime()){
		$("#status_otw").html('IN PROGRESS');
    }
	$("#hdnWindowId").val(rowData.testOperationalWindowId);

	
}

function hideEditErrorMessages(){
	$('#editWindowInvalidDACTimeErrorMessage').hide();
	$("#editOperationalTestWindowErrorMessages").hide();
	$("#editWindowNameRequiredErrorMessage").hide();
	$('#errorEditNumberOfTestWholeNumber').hide();
	$("#editWindowTicketingRequiredErrorMessage").hide();
	$("#editWindowTestEnrollmentMethodRequiredErrorMessage").hide();
	$("#editManagedByRequiredErrorMessage").hide();
	$("#editRandomizationRequiredErrorMessage").hide();
	$("#editBeginDateErrorMessage").hide();
	$("#editEndDateErrorMessage").hide();
	$("#editWindowNameCommonRequiredErrorMessage").hide();
	$("#editTestCollectionRequiredErrorMessage").hide();
	$("#editSuccessMessage").hide();
	$("#copySuccessMessage").hide();
	$("#editWindowInvalidDateErrorMessage").hide();
	$("#editSelectTestGridTableId")[0].clearToolbar();
	$("#editRightSelectTestGridTableId")[0].clearToolbar();
}


function loadEditOperationalTestCollectionWindow(id){

	hideEditErrorMessages();
	
	var rowData = $('#viewOperationalWindowTableId').jqGrid('getRowData',id);	
	$("#hdnWindowId").val(rowData.otwId);
	var windowd=$("#hdnWindowId").val();

	var assessmentProgramId = '0';
	var randomizationType = "null";
	var categoryCode = "null";
	var windowId = '';
	if($('#editManagedbyCodeSelect').val()=='MANUAL_DEFINED_ENROLLMENT_TO_TEST'){
		$('.group2').hide();
	} else {
		$('.group2').show();
	}
	if( $("#editManagedbyCodeSelect").val() != '')
	{
		categoryCode = $("#editManagedbyCodeSelect").val();
	}else{
		$('#editSelectTestGridTableId').jqGrid('clearGridData');
		$('#editRightSelectTestGridTableId').jqGrid('clearGridData');
		return false;    	 
	}
	
	if( $("#editAssessmentProgramsAddNewTestWindowSelect").val() != '' )
	{	
		assessmentProgramId = $("#editAssessmentProgramsAddNewTestWindowSelect").val();
	}/*else{
		$('#editSelectTestGridTableId').jqGrid('clearGridData');
		$('#editRightSelectTestGridTableId').jqGrid('clearGridData');
		return false;    	 
	}*/
	

	if($("#editrandomizedCodeSelect").val() != '' )  {
		randomizationType = $("#editrandomizedCodeSelect").val();
	}else if($("#editManagedbyCodeSelect").val() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST'){
		$('#editSelectTestGridTableId').jqGrid('clearGridData');
		$('#editRightSelectTestGridTableId').jqGrid('clearGridData');
		return false;    	 
	}
		
	
	if( $("#hdnWindowId").val() != '')
	windowId = $("#hdnWindowId").val();
	
	var selectTestgrid = $('#editSelectTestGridTableId');
	jQuery("#editSelectTestGridTableId").jqGrid("clearGridData");
		
	//$('#editSearchTestCollectionBtn').off('click').on('click',function(){
		/*if( $("#editManagedbyCodeSelect").val() == ''){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editManagedByRequiredErrorMessage").show();
			scrollPageTopEdit();
			return false;
		}
		
		if($("#editManagedbyCodeSelect").val() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST' && $("#editrandomizedCodeSelect").val() == ''){
			$("#editRandomizationRequiredErrorMessage").show();
			scrollPageTopEdit();
			return false;
		}*/
		
	
	if($('#editManagedbyCodeSelect').val()=='MANUAL_DEFINED_ENROLLMENT_TO_TEST'){
		randomizationType = "null";
	
	 }
		selectTestgrid.jqGrid('setGridParam',{
			 	type: 'POST',
				datatype:"json", 
				search: false,
				url : 'newOperationalTestWindowAPTCDTOList.htm',
				postData : {
					filters: null,
					assessmentProgramId:assessmentProgramId,
					randomizationType:randomizationType,
					categoryCode:categoryCode,
					windowId :windowId
				},
		}).trigger("reloadGrid"); 	
		function scrollPageTopEdit(id){
			$("#editOperationalTestWindow").animate({ scrollTop: 0}, 1000);
			setTimeout(function(){
				$('#'+id).hide();
			}, 5000);
		}
	//});

}


function loadExistingTestWindowCollections(windowId){
	
	jQuery("#editRightSelectTestGridTableId").jqGrid("clearGridData");

 	
	$.ajax({
		url: 'getExistingWindowTestCollections.htm',
		data: {
			windowId :windowId
		},
		type: 'POST',
		dataType: 'json'
	}).done(function(data) {		            			            
	  	
			$('#editRightSelectTestGridTableId').jqGrid('setGridParam', {data: data}).trigger('reloadGrid');
	    
	    
	}).fail(function() {		        	  		        	  		        	 
//		  alert("error");	            	
	});
	
	
	$("#editSelectTestGridTableId tbody").append($("#editRightSelectTestGridTableId tr").has().detach());
	
}




$('#editOperationalTestWindowSetupsave').off("click").on('click',function(){
	hideEditErrorMessages();
	
	var subjectEditNumOfTestForOTWindowAssignedFull = false;
    var subject = []; var numOfTests = [];
	var selectUpdateNumbeOfTestBySubjectContainer = $('#selectUpdateNumbeOfTestBySubjectContainer').data("selectedTestEnrollmentMethod");
	if( (selectUpdateNumbeOfTestBySubjectContainer == 'Student Tracker Field Test') || (selectUpdateNumbeOfTestBySubjectContainer == 'Multi-assign') ){			
		var numOfTestsIds ='';
		$.each($('#selectUpdateNumbeOfTestBySubject').jqGrid('getDataIDs'), function(index, value){
		   numOfTestsIds = '#inp_UpdateNumberOfTests_Subject_'+value; 
		   subject[index] = $('#selectUpdateNumbeOfTestBySubject').jqGrid ('getCell', value , 'id');
		});
		
		var increment = 0;
		$.each($('#selectUpdateNumbeOfTestBySubjectGrid table tr td') ,function(index, value){
		  var valuesr = $(this).find('input[id^=inp_UpdateNumberOfTests_Subject_]').val();
		  if(valuesr !== undefined){
		      numOfTests[increment] = valuesr;
		      increment++;
		  }		
		});
		subjectEditNumOfTestForOTWindowAssignedFull = true;
	}
	
	var fullDate =new Date();
	
	var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
	var hours = fullDate.getHours();
	var minutes = fullDate.getMinutes();
	var seconds = fullDate.getSeconds();
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  seconds = seconds < 10 ? '0'+seconds : seconds;
 	
	var currentDateTime = twoDigitMonth+ "/" +fullDate.getDate()+ "/" + fullDate.getFullYear()+" "+hours + ':' + minutes + ':'+seconds;

	

	
	var windowName = $('#editWindowName').val();
	
	var windowsuspend = $('#editWindowsuspend').is(':checked');
	var managedbyCodeSelect = $('#editManagedbyCodeSelect option:selected').val();
	var randomizedCodeSelect = $('#editrandomizedCodeSelect option:selected').val();
	var ticketingflag = $('#editTicketingOn').is(':checked');
	var testExit = $('#editTestExitId1').is(':checked');
	var assessmentProgramId = $('#editAssessmentProgramsAddNewTestWindowSelect').val();
	var graceperoid = $('#editGraceperoidOn').is(':checked');
	var testEnrollmentFlag = $('#editTestEnrollmentOn').is(':checked');
	var scoringWindowFlag = $('#editScoringWindowOn').is(':checked');
	
	//var editTicketOfTheDay = $('#editTicketingDaySelect').val(); 
	//var	editTicketOfTheDaySelected = (editTicketOfTheDay != null ? editTicketOfTheDay.toString() : "");
	var editTestEnrollmentMethod = $("#editTestEnrollmentMethod").val();
	var	editTestEnrollmentSelected = (editTestEnrollmentMethod != null ? editTestEnrollmentMethod.toString() : "");
	
	var gracetimeInMin = $('#editGracetimeInMin').val();
	var postSelecteddataId = "";
	
	var scoringWindowMehtod = $('#editScoringWindowMethod').val();
	var scoringWindowMethodSelected = (scoringWindowMehtod != null ? scoringWindowMehtod.toString() : "");
	
	var SelectgridId = $('#editSelectTestGridTableId');
	var valueselected = $("#editRightSelectTestGridTableId").jqGrid('getDataIDs');
	
	var begindate=$('#editTestWindowStartDate').val();
	var endDate = $('#editTestWindowEndDate').val();
	var beginTime = $('#editTestWindowStartTime').val();
	var endTime = $('#editTestWindowEndTime').val();
	if(beginTime == ''){
		beginTime="12:00:00 AM";
	}
	if(endTime == ''){
		endTime="11:59:00 PM";
	}
	var beginTIme24Format = convert_to_24h(beginTime);
	var endTime24HourFormat = convert_to_24h(endTime);
	if( beginTIme24Format == "" || endTime24HourFormat == "" ){
		$("#editOperationalTestWindowErrorMessages").show();
		$("#editWindowInvalidDateErrorMessage").show();
		scrollPageTopEdit('editWindowInvalidDateErrorMessage');
		return false;
	}


	
	var effectivestartDateTime = begindate+" "+beginTime;
	var expirationendDateTime =endDate+" "+endTime;
	
	//scoringwindow method start and end date time
	var scoringWindowStartDate = $('#editTestwindowScoringStartDate').val();
	var scoringWindowEndDate = 	$('#editTestWindowScoringEndDate').val();
	var scoringWindowStartTime = $('#editTestWindowScoringStartTime').val();
	var scoringWindowEndTime = $('#editTestWindowScoringEndTime').val();
	if(scoringWindowStartTime == '' || scoringWindowStartTime == 'Not Available'){
		scoringWindowStartTime = "12:00:00 AM";
	}
	if(scoringWindowEndTime == '' || scoringWindowEndTime == 'Not Available'){
		scoringWindowEndTime = "11:59:00 PM";
	}
	
	var scoringStartTime24Format = convert_to_24h(scoringWindowStartTime);
	var scoringEndTime24Format = convert_to_24h(scoringWindowEndTime);
	if(scoringWindowFlag){
	if( scoringStartTime24Format == "" || scoringEndTime24Format == "" ){
		$("#editOperationalTestWindowErrorMessages").show();
		$("#editWindowInvalidDateErrorMessage").show();
		scrollPageTopEdit('editWindowInvalidDateErrorMessage');
		return false;
	}
	}
	var scoringWindowStartDateTime = '';
	var scoringWindowEndDateTime = '';
	if(scoringWindowFlag){
	 scoringWindowStartDateTime = scoringWindowStartDate+" "+scoringWindowStartTime;
	 scoringWindowEndDateTime = scoringWindowEndDate+" "+scoringWindowEndTime;
	}
	

	if(windowName.trim().length == 0){
		$("#editOperationalTestWindowErrorMessages").show();
		$("#editWindowNameRequiredErrorMessage").show();
		scrollPageTopEdit('editWindowNameRequiredErrorMessage');
		return false;
	}

	if(testEnrollmentFlag){
		if(editTestEnrollmentMethod.trim().length == 0){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editWindowTestEnrollmentMethodRequiredErrorMessage").show();
			scrollPageTopEdit('editWindowTestEnrollmentMethodRequiredErrorMessage');
			return false;
		}
	}
	if(scoringWindowFlag){
		if(scoringWindowMehtod.trim().length == 0){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editWindowScoringAssignmentMethodRequiredErrorMessage").show();
			scrollPageTopEdit('editWindowScoringAssignmentMethodRequiredErrorMessage');
			return false;
		}
	}
	//managedbyCodeSelect is a select option and if we don't select anything it will return undefined.. So including the check when nothing selected
	if( managedbyCodeSelect == undefined || managedbyCodeSelect.trim().length == 0){
		$("#editOperationalTestWindowErrorMessages").show();
		$("#editManagedByRequiredErrorMessage").show();
		scrollPageTopEdit('editManagedByRequiredErrorMessage');
		return false;
	}
	
	//randomizedCodeSelect is a select option and if we don't select anything it will return undefined.. So including the check when nothing selected
	if(managedbyCodeSelect!="MANUAL_DEFINED_ENROLLMENT_TO_TEST"){
		if(randomizedCodeSelect== undefined || (randomizedCodeSelect.trim().length == 0 && managedbyCodeSelect.trim() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST')){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editRandomizationRequiredErrorMessage").show();
			scrollPageTopEdit('editRandomizationRequiredErrorMessage');
			return false;
		}
	}
	$.each(valueselected, function (index, value) {
		  Selecteddata = $('#editRightSelectTestGridTableId').jqGrid('getCell', value, 'id');
		  postSelecteddataId = Selecteddata+","+postSelecteddataId;
		});
	var windowId=$("#hdnWindowId").val();
	if(copyExistingWindow){
		windowId = '';	
	}
	// Validate for invalid date if any
	
	if(postSelecteddataId ==''){
		$("#editOperationalTestWindowErrorMessages").show();
		$("#editTestCollectionRequiredErrorMessage").show();
		scrollPageTopEdit('editTestCollectionRequiredErrorMessage');
		return false;
	}
	
	var dialyAccessCodeflag = $('#editDailyAccessCode').is(':checked');
	var dacStartTime =  $('#editDailyAccessCodeTestwindowstartTime').val();
	var dacEndTime =  $('#editDailyAccessCodeTestwindowendTime').val();
	var dacStartDateTime = begindate+" "+dacStartTime;
	var dacEndDateTime = endDate+" "+dacEndTime;
	
	if(dialyAccessCodeflag){
		
		if( dacStartTime.trim().length == 0 || dacEndTime.trim().length == 0 ){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editWindowInvalidDACTimeErrorMessage").show();
			scrollPageTopEdit('editWindowInvalidDACTimeErrorMessage');
			return false;
		}
		var dacStartTime24Format = convert_to_24h(dacStartTime);
		var dacEndTime24HourFormat = convert_to_24h(dacEndTime);
		if( dacStartTime24Format == "" || dacEndTime24HourFormat == "" ){
			$("#editOperationalTestWindowErrorMessages").show();
			$("#editWindowInvalidDACTimeErrorMessage").show();
			scrollPageTopEdit('editWindowInvalidDACTimeErrorMessage');
			return false;
		}
	}
	
	var instructionalPlannerWindow = $('input[name="instructionalPlannerWindow"][data-process-type="edit"]:checked').val();
	if (typeof (instructionalPlannerWindow) === 'undefined' || instructionalPlannerWindow === ''){
		instructionalPlannerWindow = null;
	}
	
	var instructionalPlannerWindowDisplayName = null;
	if (instructionalPlannerWindow == 'true'){
		instructionalPlannerWindowDisplayName = $('#instructionalPlannerDisplayName_edit').val();
	}

	var multipleStateIdArr = $("#editStatebyTestWindowSelect").val();
	var	multipleStateId = (multipleStateIdArr != null ? multipleStateIdArr.toString() : "");
	
	$("#editOperationalTestWindowSetupsave").prop("disabled",true);
	 $.ajax({
          url: 'addOperationalTestWindowContent.htm',
          dataType: 'text',
          type: "POST",
          data: {
        	testCollectionId: postSelecteddataId,
          	windowName: windowName,
          	ticketingFlag: ticketingflag,
          	//ticketOfTheDaySelected: editTicketOfTheDaySelected,
          	requiredToCompleteTest: testExit,
          	effectiveDatetime: effectivestartDateTime,
          	expirationDatetime: expirationendDateTime,
          	suspendWindow: windowsuspend,
          	managedBy: managedbyCodeSelect,
          	randomized: randomizedCodeSelect,
          	gracePeroid: graceperoid,
          	graceTimeInMin: gracetimeInMin,
          	windowId:windowId,
          	multipleStateId:multipleStateId,
          	testEnrollmentFlag:testEnrollmentFlag,
          	testEnrollmentId:editTestEnrollmentSelected,
          	scoringWindowFlag:scoringWindowFlag,
          	scoringWindowId:scoringWindowMethodSelected,
          	scoringWindowStartDateTime:scoringWindowStartDateTime,
          	scoringWindowEndDateTime:scoringWindowEndDateTime,
          	assessmentProgramId:assessmentProgramId,
        	subjectName: subject,
          	numOfTests: numOfTests,
          	subjectNumOfTestForOTWindowAssignedBefore: subjectEditNumOfTestForOTWindowAssignedBefore,
          	dacFlag:dialyAccessCodeflag,
          	dacStartTime:dacStartTime,
          	dacEndTime:dacEndTime,
          	dacStartDateTime : dacStartDateTime,
          	dacEndDateTime : dacEndDateTime,
          	instructionalPlannerWindow: instructionalPlannerWindow,
          	instructionalPlannerDisplayName: instructionalPlannerWindowDisplayName
          }
     }).done(function(data) {
        	if((subjectEditNumOfTestForOTWindowAssignedFull == true) && (subjectEditNumOfTestForOTWindowAssignedBefore == false)){
        		subjectEditNumOfTestForOTWindowAssignedBefore = true;
        	}
        	$("#editOperationalTestWindowErrorMessages").show();
        	
        	if(data == '1')
        	{
        		if(!copyExistingWindow){
        			$("#editSuccessMessage").show();        		
        			scrollPageTopEdit('editSuccessMessage');
        		}else{
        			$('#copySuccessMessage').show();
        			scrollPageTopEdit('copySuccessMessage');
        		}
        	}else{
        		$('#editWindowNameCommonRequiredErrorMessage').show().html(data);
        		scrollPageTopEdit('editWindowNameCommonRequiredErrorMessage');
        	}
        	$("#viewOperationalWindowTableId").trigger('reloadGrid');
        	
        	$("#editOperationalTestWindowSetupsave").prop("disabled",false);
     		//$("#viewOperationalWindowTableId").trigger('reloadGrid');
        	$('#editStatebyTestWindowSelect').trigger('change.select2');
     }).fail(function(jqXHR,textStatus,errorThrown) {
        	$("#editOperationalTestWindowSetupsave").prop("disabled",false);
        	$("#editOperationalTestWindowErrorMessages").show();
         	$('#editOperationalTestWindowErrorMessages').html(errorThrown);
         	scrollPageTopEdit('editOperationalTestWindowErrorMessages');
	 });
		
  });

function scrollPageTopEdit(id){
	$("#editOperationalTestWindow").animate({ scrollTop: 0}, 1000);
	setTimeout(function(){
		$('#'+id).hide();
	}, 5000);
}


function numberEditOfTestsBySubjectFormatter(cellvalue, options, rowObject){
	var originalCellValue = cellvalue;
	if(cellvalue = undefined || cellvalue == null/* || copyExistingWindow*/){
		cellvalue = 0;
	}else{
		cellvalue = originalCellValue;
	}
	 return "<input style='width:50px;' type='text' min='0' max ='99' value='"+cellvalue+"' id='inp_UpdateNumberOfTests_Subject_"+options.rowId+"' />";
}

function makeTwoDigit(digit){
	return digit > 9 ? "" + digit: "0" + digit;
}

function loadAdminOptionsData(id){
	$.ajax({
		url: 'getAdminOptionData.htm',
		data: {
			windowId :id
		},
		type: 'POST'
	}).done(function(data) {
		$('#editDailyAccessCodeTimeFrameDiv').hide();
		if(data.ticketingFlag != null && data.ticketingFlag){
			$("#editTicketingOn").prop('checked',true);
		}else{
			$("#editTicketingOff").prop('checked',true);
			$("#editTicketingoftheday").hide();
		}
        if(data.gracePeriod != null && data.gracePeriod){            	
        	$("#editGraceperoidOn").prop('checked',true);
        	$("#editGracetimeInMin").val(data.graceTime);
		}else{
			$("#editGraceperoidOff").prop('checked',true);
			$("#editGracetimeInMin").val('');
		}
        if(data.testExit != null && data.testExit){
        	$("#editTestExitId2").prop('checked',true);
		}else{
			$("#editTestExitId1").prop('checked',true);
		}
       
        if(data.dacFlag != null && data.dacFlag){                    
        	$("#editDailyAccessCode").prop('checked',true);
        	$("#editDailyAccessCodeTestwindowstartTime").val(data.dacStartTime);
        	$("#editDailyAccessCodeTestwindowendTime").val(data.dacEndTime);
        	$('#editDailyAccessCodeTimeFrameDiv').show();
		}           
	}).fail(function() {		        	  		        	  		        	 
//		alert("error");	            	
	});
}

$(function(){
	$.ajax({
        url: 'getServerTime.htm',           
        dataType: 'json',
        type: "GET"
	}).done(function(data) {
    	if(data != null) {
    		//$("#timestmp").text(new Date(data));            		
    		$("#clockEditOprWind").clock({"timestamp":new Date(data)});
    	}             
	});
});


