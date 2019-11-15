var orgHierarchies = {};
var childOrg = 20;
$(function() {
	
	$.ajax({
        url: 'getServerTime.htm',           
        dataType: 'json',
        type: "GET"
	}).done(function(data) {
    	if(data != null) {
    		$("#clockNewOprWind").clock({"timestamp":new Date(data)});
    	}            
	});

	$("#newOperationalTestWindowErrorMessages").hide();
	initializedates();
	newOperationalTestWindowGridInitMethod();
	     
		$('#testEnrollmentMethod').select2({
			placeholder:'Select',
			multiple: false
		});
		$('#scoringWindowMethod').select2({
			placeholder:'Select',
			multiple: false
		});
		$('#ticketingDaySelect').select2({
			placeholder:'Select',
			multiple: false
		});
   	
		 $('#assessmentProgramsAddNewTestWindowSelect').on("change",function(){
			 $('#orgFilterStateIdTestWindowSelect').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			 var assessmentProgramId = $('#assessmentProgramsAddNewTestWindowSelect').val();
			 if(assessmentProgramId == '' || assessmentProgramId == null){
				 return false;
			 }
	    	 $.ajax({
    	        url: 'getStatesBesedOnAssessmentProgram.htm',
    	        data: {assessmentProgramId:assessmentProgramId },
    	        dataType: 'json',
    	        type: "GET"
	    	 }).done(function(states) {
	        	var obj = states;
	        	for (var key in obj) {
	        	   $('#orgFilterStateIdTestWindowSelect').append($('<option></option>').attr("value",key).text(obj[key]));
	        	 } 
	        	$('#orgFilterStateIdTestWindowSelect').trigger('change.select2');	
	        	filteringOrganization($("#orgFilterStateIdTestWindowSelect"));
	    	 });
	    	 testEnrollmentMethodDataPopulation(assessmentProgramId);
	    	 scoringAssignmentMethodDataPopulation(assessmentProgramId);
	    	 $('#testEnrollmentMethod').trigger('change');
	    	 $('#scoringWindowMethod').trigger('change');	    	  
	      });
	
	$('input[name="instructionalPlannerWindow"][data-process-type="new"]').on('change', function(){
		var val = $(this).val();
		var iapWindow = $('#iapWindow_new');
		if (val === 'false'){
			if (iapWindow.length > 0){
				$('#instructionalPlannerDisplayName_new', iapWindow).val('');
				iapWindow.hide();
			}
		} else if (val === 'true') {
			if (iapWindow.length > 0){
				$('#instructionalPlannerDisplayName_new', iapWindow).val('');
				iapWindow.show();
			}
		}
	});
	$('#instructionalPlannerWindowOff_new').prop('checked', true).attr('checked', 'checked').trigger('change');
});

function scoringAssignmentMethodDataPopulation(assessmentProgramId){
	$('#scoringWindowMethod').find('option').filter(function(){return $(this).val().length > 0;}).remove().end();	
	 $.ajax({
       url: 'getScoringAssignmentMethod.htm',
       data: {assessmentProgramId:assessmentProgramId },
       dataType: 'json',
       type: "GET"
	 }).done(function(scoringWindowMethod) {
       	if(scoringWindowMethod != null ){
       		var obj = scoringWindowMethod;
       		for (var key in obj) {
       			$('#scoringWindowMethod').append($('<option></option>').attr("value",key).text(obj[key]));
       		}
       		$('#scoringWindowMethod').trigger('change.select2');	
        }
	});	
}

function testEnrollmentMethodDataPopulation(assessmentProgramId){
	$('#testEnrollmentMethod').find('option').filter(function(){return $(this).val().length > 0;}).remove().end();	
		 $.ajax({
	        url: 'getTestEnrollmentMethod.htm',
	        data: {assessmentProgramId:assessmentProgramId },
	        dataType: 'json',
	        type: "GET"
		 }).done(function(testEnrollmentMethod) {
        	if(testEnrollmentMethod != null ){
        		var obj = testEnrollmentMethod;
        		for (var key in obj) {
        			$('#testEnrollmentMethod').append($('<option></option>').attr("value",key).text(obj[key]));
        		}
        		$('#testEnrollmentMethod').trigger('change.select2');	
        	}
		});
}

function initializedates(){		
	$("#testwindowstartDate").datepicker({
	      dateFormat: "mm/dd/yy"
	  }).datepicker("setDate", new Date());
		
		$("#testwindowendDate").datepicker({
		    dateFormat: "mm/dd/yy"
		}).datepicker("setDate", new Date());
		
		$("#testwindowScoringStartDate").datepicker({
		    dateFormat: "mm/dd/yy"
		}).datepicker("setDate", new Date());
		
		$("#testWindowScoringEndDate").datepicker({
		    dateFormat: "mm/dd/yy"
		}).datepicker("setDate", new Date());		
	}

function newOperationalTestWindowGridInitMethod(){
	var tcGridWidth = 408;
	var tcGridHeight = 310;
	var tcCellWidth = tcGridWidth/3;
	var $gridAuto = $("#selectTestGridTableId");
	$gridAuto.scb({
	 	datatype: "local",
		rowNum: 100000,
		rows: 100000,
	 	colNames:['Internal ID','External ID','Test Collection Name'],
		colModel:[
 	      {name: 'id', index: 'id',width:tcCellWidth, search:true,sortable : true,key:true,hidedlg: false,hidden: true},
	 	  {name: 'testCollectionId', index: 'testCollectionId',width:tcCellWidth,search:true,sortable : true,hidedlg: true},
          {name: 'name',index:'name', width:tcCellWidth, search:true,sortable : true,hidedlg: true}             
         ],
         width: tcGridWidth,
         height: tcGridHeight,
         loadonce : true,
         viewrecords: true,
          scroll:false,
          pager : '#pselectTestGridTableId',
          multiselect:true,
          shrinkToFit: false,
          sortname : 'name',
          rowList: [],        // disable page size dropdown
          pgbuttons: false,     // disable page control like next, back button
          pgtext: null,         // disable pager text like 'Page 0 of 10'
          viewrecords: false,
          ignoreCase: true,
          loadComplete: function () {
 	        	gridName = this.id;
 	        	reSizeTestWindowGridBasedOnContent(gridName);
	 	  		 var ids = $(this).jqGrid('getDataIDs');  
		 	  		var tableid=$(this).attr('id');    
		 	  		for(var i=0;i<ids.length;i++)
		             {         
		                 $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'name') + ' Check Box');
		                 $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
		             }
		 	  		var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		 	  		$('#cb_'+tableid).attr('title','Availble Collection Grid All Check Box');
		 	  		$('#cb_'+tableid).removeAttr('aria-checked');
		 	  		$.each(objs, function(index, value) {         
		               var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                     $(value).attr('title',$(nm).text()+' filter');                          
		                     });
 	       }
	});

$("#SelectedCollectionId").scb({
	datatype: "local",
	rowNum: 100000,
 	rows: 100000,
 	colNames:['Internal ID','External ID','Test Collection Name'],
  	colModel:[
   	    {name: 'id', index: 'id',width:tcCellWidth, search:true,sortable : true,key:true,hidedlg: false,hidden: true},      
   		{name:'testCollectionId', index:'testCollectionId', width:tcCellWidth, sortable: true, hidedlg: true, searchoptions:{sopt: ['cn']}, search:true},
   		{name:'name', index:'name', width:tcCellWidth, sortable: true, search:true, hidedlg: true, searchoptions:{sopt: ['cn']},fixed: true}
   	],
    width: tcGridWidth,
    height: tcGridHeight,
    loadonce : true,
    viewrecords: true,
    scroll:false,
    pager : '#pSelectedCollectionId',
    multiselect:true,
    ignoreCase: true,
    shrinkToFit:false,
    rowList: [],        // disable page size dropdown
    pgbuttons: false,     // disable page control like next, back button
    pgtext: null,         // disable pager text like 'Page 0 of 10'
    viewrecords: false,
    loadComplete: function () {
	  		gridName = this.id;
	  		reSizeTestWindowGridBasedOnContent(gridName);
	  		 var ids = $(this).jqGrid('getDataIDs');     
		  		var tableid=$(this).attr('id');    
		  		for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'name')+ ' Check Box');
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

$("#SelectedCollectionId").jqGrid('filterToolbar');

function reSizeTestWindowGridBasedOnContent(gridName){
	// Test - resize cols based on content
		
     $('.parent').append('<span class= "widthTest"/>');

     $('#gbox_' + gridName + ' .ui-jqgrid-htable,#' + gridName).css('width', '100%');
     $('#' + gridName).parent().css('width', 'inherit');

     var columnNames = $("#" + gridName).jqGrid('getGridParam', 'colModel');
     var thisWidth;

     // Loop through Cols
     for (var itm = 0, itmCount = columnNames.length; itm < itmCount; itm++) {

         var curObj = $('[aria-describedby=' + gridName + '_' + columnNames[itm].name + ']');
         
         var thisCell = $('#' + gridName + '_' + columnNames[itm].name + ' div');
         $('.widthTest').html(thisCell.text()).css({
                 'font-family': thisCell.css('font-family'),
                 'font-size': thisCell.css('font-size'),
                 'font-weight': thisCell.css('font-weight')
             });
         var maxWidth = Width = $('.widthTest').width() + 24;                                        

         // Loop through Rows
         //removed the width check for column 0 as it now seems to have some very long content (over 900 pixels)
         //causing the columns to be extremely wide
         for (var itm2 = 1; itm2 < curObj.length; itm2++) {
             var thisCell = $(curObj[itm2]);

             $('.widthTest').html(thisCell.html()).css({
                 'font-family': thisCell.css('font-family'),
                 'font-size': thisCell.css('font-size'),
                 'font-weight': thisCell.css('font-weight')
             });

             thisWidth = $('.widthTest').width();
             if (thisWidth > maxWidth) maxWidth = thisWidth;                        
         }                    

         $('#' + gridName + ' .jqgfirstrow td:eq(' + itm + '), #' + gridName + '_' + columnNames[itm].name).width(maxWidth).css('min-width', maxWidth);

     }

     $('.widthTest').remove();

}
}
function newOperationalTestWindowInitMethod(){
	

	$(window).on("load", function() {
	    $('form').get(0).reset(); //clear form data on page load
	});
	loadNewOperationalTestCollectionWindow();
		
	$("#AddInoperationalTestcollection").off("click").on('click',function(){
			
			var rowIds = $("#selectTestGridTableId").jqGrid('getGridParam','selarrrow');
			
			var rowIds1 = rowIds;
			
			if (rowIds != null && rowIds.length > 0) {
				
				for ( var i = 0;  i < rowIds.length; i++) {
					var rowObj = $("#selectTestGridTableId").jqGrid('getRowData',
							rowIds[i]);					
					$("#SelectedCollectionId").jqGrid("addRowData", rowObj.id, rowObj, "last");
				}	
				
				while(rowIds1.length > 0){
					$("#selectTestGridTableId").delRowData(rowIds1[rowIds1.length-1]);
				}
				
			}
			$("#SelectedCollectionId").trigger('reloadGrid');
			$("#selectTestGridTableId").trigger('reloadGrid');
			$("#cb_selectTestGridTableId").prop('checked',false);
		});
		
		$("#MoveOutoperationalTestcollection").off("click").on('click',function(){
		
            var rowIds = $("#SelectedCollectionId").jqGrid('getGridParam','selarrrow');
			
            var rowIds1 = rowIds;
            
			if (rowIds != null && rowIds.length > 0) {
				for ( var i = 0, length = rowIds.length; i < length; i++) {
					var rowObj = $("#SelectedCollectionId").jqGrid('getRowData',
							rowIds[i]);
					$("#selectTestGridTableId").jqGrid("addRowData", rowObj.id, rowObj);
				}
				while(rowIds1.length > 0){
					$("#SelectedCollectionId").delRowData(rowIds1[rowIds1.length-1]);
				}
			}
			$("#SelectedCollectionId").trigger('reloadGrid');
			$("#selectTestGridTableId").trigger('reloadGrid');
			$("#cb_SelectedCollectionId").prop('checked',false);
		});

    
      
    $('#assessmentProgramsAddNewTestWindowSelect').on("change",function(){
    	  loadNewOperationalTestCollectionWindow();
  	});
    $('#orgFilterStateIdTestWindowSelect').on("change",function(){
  	  loadNewOperationalTestCollectionWindow();
	});
    $('#randomizedCodeSelect').on("change",function(){
    	  loadNewOperationalTestCollectionWindow();
  	});
      
    $('#managedbyCodeSelect').on("change",function(){
    	  loadNewOperationalTestCollectionWindow();
  	});
    
    $('.ticketing').on("change",function(){
        var ticketingFlag = $(this).val();
      //  $('#dailyAccessCodeTimeFrameDiv').val("0").trigger('change.select2');
        if(ticketingFlag == 'DAILY_ACCESS_CODE'){
          $('#dailyAccessCodeTestwindowstartTime').val('');
          $('#dailyAccessCodeTestwindowendTime').val('');
          $('#dailyAccessCodeTimeFrameDiv').show();
        }else{
          $('#dailyAccessCodeTimeFrameDiv').hide();
        }
     });
    
    
    $('.testenrollment').on('change',function(){
    	var testEnrollmentFlag = $(this).val();
        $('#testEnrollmentMethod').val("0").trigger('change.select2');
    	if(testEnrollmentFlag == 'true'){
    		$('.testenrollmentdiv').show();
    	}else{
    		$('.testenrollmentdiv').hide();
    		$('#selectNumbeOfTestBySubjectGrid').hide();
    	}
   	});
    $('.scoringWindow').on('change',function(){
    	var scoringWindowFlag = $(this).val();
    	$('#scoringWindowMethod').val("0").trigger('change.select2');
    	if(scoringWindowFlag == 'true'){
    		$('.scoringWindowMethoddiv').show();
    		$('#scoringAvailableTimeFrameDiv').show();
    		$('#testwindowScoringStartDate').val('');
    		$('#testWindowScoringEndDate').val('');
    		$('#testWindowScoringStartTime').val('');
    		$('#testWindowScoringEndTime').val('');
    		
    	}else{
    		$('.scoringWindowMethoddiv').hide();
    		$('#scoringAvailableTimeFrameDiv').hide();
    	}
    });
    $('#selectNumbeOfTestBySubjectGrid').hide();
    
    $('#testEnrollmentMethod').on("change",function(){
    	  var assessmentProgramId = $('#assessmentProgramsAddNewTestWindowSelect').val();
    	  var selectedTestEnrollmentMethod = $(this).find('option:selected').html();
    	  $('#selectNumbeOfTestBySubjectContainer').data("selectedTestEnrollmentMethod", selectedTestEnrollmentMethod);
    	  if( (selectedTestEnrollmentMethod == 'Student Tracker Field Test') || (selectedTestEnrollmentMethod == 'Multi-assign') ){
    		  $("#selectNumbeOfTestBySubject").jqGrid("GridUnload");
    		  $.ajax({
    				url: 'getContentAreasByAssessmentProgramForTestRecord.htm',
    				data:{"assessmentProgramId":assessmentProgramId},
    				type: 'POST',
    				dataType: 'json'
    		  }).done(function(data) {		            			            
			    	var mydata1 = [];    	
			    	$.each(data,function(index,value){
			    		mydata1[index] = {
			    				id:data[index].id,
			    				name :data[index].name
    					    };
			    	});
			    	
			    	$("#selectNumbeOfTestBySubject").scb({
			    	    	datatype: "local",
			    		    data: mydata1,
		    	         	colNames:['Id' ,'Name', 'Number Of Tests'],
		    	         	colModel :[
		    	         	      {name: 'id', index: 'id', hidden:true, sortable : false, search: false},
    	    			 		  {name: 'name', index: 'name', hidden:false, sortable : true, search: false},
		    	         	      {name:'numberofTest',index: 'numberofTest', hidden:false, formatter: numberOfTestsBySubjectFormatter, sortable : false, search: false}
		    	    		],
		    	         	pager : '#selectNumbeOfTestBySubjectPager',
		    	         	loadonce: false,
		    	         	viewrecords: true,
		    	         	multiselect:false,
		    				search: false,
		    	         	scroll:true,
		    	         	width:'170px',
		    	         	loadComplete: function() {
		    	         		var selectNumbeOfTestBySubjectGridinput = '' ;
		    		    		$('#selectNumbeOfTestBySubjectGrid table tr').find("input[id^=inp_CreateNumberOfTests_Subject_]").on("change",function(){
		    		    		  selectNumbeOfTestBySubjectGridinput = $(this).val();
		    		    		  if(!(/^[0-9]+$/.test(selectNumbeOfTestBySubjectGridinput)) || selectNumbeOfTestBySubjectGridinput > 99 || selectNumbeOfTestBySubjectGridinput < 0 || selectNumbeOfTestBySubjectGridinput == '' ){
		    						$('#newOperationalTestWindowErrorMessages, #errorNumberOfTestWholeNumber').show();
		    						scrollCreatePageTop('errorNumberOfTestWholeNumber');
		    						$(this).val('0');
		    		    		  }else{
		    		    			$('#newOperationalTestWindowErrorMessages, #errorNumberOfTestWholeNumber').hide();
		    		    		  }
		    		    		});
		    	         	}
			    
			    	});
			    	
			    	$('#gview_selectNumbeOfTestBySubject .ui-search-toolbar, #selectNumbeOfTestBySubjectPager').css('display','none'); 
    		  }); 
    		$('#selectNumbeOfTestBySubjectGrid').show();
    		
    	  }else{
    		  $('#selectNumbeOfTestBySubjectGrid').hide();
    	  }
    });
    
    $('#OperationalTestWindowSetupsave').off('click').on('click',function(){
    	
	hideErrorMessages();
	
    var subject = []; var numOfTests = [];
	var selectNumbeOfTestBySubjectContainer = $('#selectNumbeOfTestBySubjectContainer').data("selectedTestEnrollmentMethod");
	if( (selectNumbeOfTestBySubjectContainer == 'Student Tracker Field Test') || (selectNumbeOfTestBySubjectContainer == 'Multi-assign') ){			
		var numOfTestsIds ='';
		$.each($('#selectNumbeOfTestBySubject').jqGrid('getDataIDs'), function(index, value){
		   numOfTestsIds = '#inp_CreateNumberOfTests_Subject_'+value; 
		   subject[index] = $('#selectNumbeOfTestBySubject').jqGrid ('getCell', value , 'id');
		   numOfTests[index] = $('#selectNumbeOfTestBySubjectGrid table tr').find('input[id$='+value+']').val();			   
		});
		
	}
	
	var fullDate =new Date();
	
	var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
	var hours = fullDate.getHours();
	var minutes = fullDate.getMinutes();
	var seconds = fullDate.getSeconds();
	  minutes = minutes < 10 ? '0'+minutes : minutes;
	  seconds = seconds < 10 ? '0'+seconds : seconds;
 	
	var currentDateTime = twoDigitMonth+ "/" +fullDate.getDate()+ "/" + fullDate.getFullYear()+" "+hours + ':' + minutes + ':'+seconds;

	

	var assessmentProgramSelectStudent = $('#assessmentProgramSelectStudent').val();
	var windowName = $('#windowName').val();
	
	var windowsuspend = $('#windowsuspend').is(':checked');
	var managedbyCodeSelect = $('#managedbyCodeSelect option:selected').val();
	var randomizedCodeSelect = $('#randomizedCodeSelect option:selected').val();
	var ticketingflag = $('#ticketingon').is(':checked');
	var testEnrollmentFlag = $('#testenrollmenton').is(':checked');
	var scoringWindowFlag = $('#scoringWindowOn').is(':checked');
	var assessmentProgramId = $('#assessmentProgramsAddNewTestWindowSelect').val();
	var testExit = $('#testExitId1').is(':checked');
	
	//var ticketOfTheDay = $('#ticketingDaySelect').val(); 
	//var	ticketOfTheDaySelected = "";// (ticketOfTheDay != null ? ticketOfTheDay.toString() : "");
	var testEnrollmentMethod = $("#testEnrollmentMethod").val();
	var	testEnrollmentSelected = (testEnrollmentMethod != null ? testEnrollmentMethod.toString() : "");
	
	var scoringWindowMehtod = $('#scoringWindowMethod').val();
	var scoringWindowMethodSelected = (scoringWindowMehtod != null ? scoringWindowMehtod.toString() : "");
	
	var graceperoid = $('#graceperoidon').is(':checked');
	
	
	var gracetimeInMin = $('#gracetimeInMin').val();
	var postSelecteddataId = "";
	var SelectgridId = $('#SelectedCollectionId');
	var valueselected = $("#SelectedCollectionId").jqGrid('getDataIDs');
	
	var begindate=$('#testwindowstartDate').val();
	var endDate = $('#testwindowendDate').val();
	var beginTime = $('#testwindowstartTime').val();
	var endTime = $('#testwindowendTime').val();
	if(beginTime == ''){
		beginTime="12:00:00 AM";
	}
	if(endTime == ''){
		endTime="11:59:00 PM";
	}
	var beginTIme24Format = convert_to_24h(beginTime);
	var endTime24HourFormat = convert_to_24h(endTime);
	if( beginTIme24Format == "" || endTime24HourFormat == "" ){
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newWindowInvalidDateErrorMessage").show();
		scrollCreatePageTop('newWindowInvalidDateErrorMessage');
		return false;
	}

	
	var effectivestartDateTime = begindate+" "+beginTime;
	var expirationendDateTime =endDate+" "+endTime;
	
	//scoringwindow method start and end date time
	var scoringWindowStartDate = $('#testwindowScoringStartDate').val();
	var scoringWindowEndDate = 	$('#testWindowScoringEndDate').val();
	var scoringWindowStartTime = $('#testWindowScoringStartTime').val();
	var scoringWindowEndTime = $('#testWindowScoringEndTime').val();
	
	if(scoringWindowStartTime == ''){
		scoringWindowStartTime = "12:00:00 AM";
	}
	if(scoringWindowEndTime == ''){
		scoringWindowEndTime = "11:59:00 PM";
	}
	
	var scoringStartTime24Format = convert_to_24h(scoringWindowStartTime);
	var scoringEndTime24Format = convert_to_24h(scoringWindowEndTime);
	if(scoringWindowFlag){
	if( scoringStartTime24Format == "" || scoringEndTime24Format == "" ){
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newWindowInvalidDateErrorMessage").show();
		scrollCreatePageTop('newWindowInvalidDateErrorMessage');
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
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newWindowNameRequiredErrorMessage").show();
		scrollCreatePageTop('newWindowNameRequiredErrorMessage');
		return false;
	}

	if(testEnrollmentFlag){
		if(testEnrollmentMethod.trim().length == 0){
			$("#newOperationalTestWindowErrorMessages").show();
			$("#newWindowTestEnrollmentMethodRequiredErrorMessage").show();
			scrollCreatePageTop('newWindowTestEnrollmentMethodRequiredErrorMessage');
			return false;
		}
	}
	
	if(scoringWindowFlag){
		if(scoringWindowMehtod.trim().length == 0){
			$("#newOperationalTestWindowErrorMessages").show();
			$("#newWindowScoringAssignmentMethodRequiredErrorMessage").show();
			scrollCreatePageTop('newWindowScoringAssignmentMethodRequiredErrorMessage');
			return false;
		}
	}
	
	
	//managedbyCodeSelect is a select option and if we don't select anything it will return undefined.. So including the check when nothing selected
	if( managedbyCodeSelect == undefined || managedbyCodeSelect.trim().length == 0){
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newManagedByRequiredErrorMessage").show();
		scrollCreatePageTop('newManagedByRequiredErrorMessage');
		return false;
	}
	
	//randomizedCodeSelect is a select option and if we don't select anything it will return undefined.. So including the check when nothing selected
	if(randomizedCodeSelect== undefined || (randomizedCodeSelect.trim().length == 0 && managedbyCodeSelect.trim() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST')){
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newRandomizationErrorMessage").show();
		scrollCreatePageTop('newRandomizationErrorMessage');
		return false;
	}
	

	
	$.each(valueselected, function (index, value) {
		  Selecteddata = $('#SelectedCollectionId').jqGrid('getCell', value, 'id');
		  postSelecteddataId = Selecteddata+","+postSelecteddataId;
		});
	var windowId="";
	// Validate for invalid date if any
	if(postSelecteddataId ==''){
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newTestCollectionRequiredErrorMessage").show();
		scrollCreatePageTop('newTestCollectionRequiredErrorMessage');
		return false;
	}
	
	var dialyAccessCodeflag = $('#dailyAccessCode').is(':checked');
	var dacStartTime =  $('#dailyAccessCodeTestwindowstartTime').val();
	var dacEndTime =  $('#dailyAccessCodeTestwindowendTime').val();
	if(dacStartTime == ''){
		dacStartTime="12:00:00 AM";
	}
	if(dacEndTime == ''){
		dacEndTime="11:59:00 PM";
	}
	var beginDacTIme24Format = convert_to_24h(dacStartTime);
	var endDacTime24HourFormat = convert_to_24h(dacEndTime);

	var dacStartDateTime = begindate+" "+dacStartTime;
	var dacEndDateTime = endDate+" "+dacEndTime;
	
	if(dialyAccessCodeflag){
		
		if( dacStartTime.trim().length == 0 || dacEndTime.trim().length == 0 ){
			$("#newOperationalTestWindowErrorMessages").show();
			$("#newWindowInvalidDACTimeErrorMessage").show();
			scrollCreatePageTop('newWindowInvalidDACTimeErrorMessage');
			return false;
		}
		var dacStartTime24Format = convert_to_24h(dacStartTime);
		var dacEndTime24HourFormat = convert_to_24h(dacEndTime);
		if( dacStartTime24Format == "" || dacEndTime24HourFormat == "" ){
			$("#newOperationalTestWindowErrorMessages").show();
			$("#newWindowInvalidDACTimeErrorMessage").show();
			scrollCreatePageTop('newWindowInvalidDACTimeErrorMessage');
			return false;
		}
	}
	
	var instructionalPlannerWindow = $('input[name="instructionalPlannerWindow"][data-process-type="new"]:checked').val();
	if (typeof (instructionalPlannerWindow) === 'undefined' || instructionalPlannerWindow === ''){
		instructionalPlannerWindow = null;
	}
	
	var instructionalPlannerWindowDisplayName = null;
	if (instructionalPlannerWindow == 'true'){
		instructionalPlannerWindowDisplayName = $('#instructionalPlannerDisplayName_new').val();
	}
	
	var multipleStateIdArr = $("#orgFilterStateIdTestWindowSelect").val();
	var	multiStateId = (multipleStateIdArr != null ? multipleStateIdArr.toString() : "");
	
	$("#OperationalTestWindowSetupsave").prop("disabled",true);
	$.ajax({
          url: 'addOperationalTestWindowContent.htm',
          dataType: 'text',
          type: "POST",
          data: {
        	testCollectionId: postSelecteddataId,
          	windowName: windowName,
          	ticketingFlag: ticketingflag,
          	//ticketOfTheDaySelected: ticketOfTheDaySelected,
          	requiredToCompleteTest: testExit,
          	effectiveDatetime: effectivestartDateTime,
          	expirationDatetime: expirationendDateTime,
          	suspendWindow: windowsuspend,
          	managedBy: managedbyCodeSelect,
          	randomized: randomizedCodeSelect,
          	gracePeroid: graceperoid,
          	graceTimeInMin: gracetimeInMin,
          	windowId:windowId,
          	multipleStateId:multiStateId,
          	testEnrollmentFlag:testEnrollmentFlag,
          	testEnrollmentId:testEnrollmentSelected,
          	scoringWindowFlag:scoringWindowFlag,
          	scoringWindowId:scoringWindowMethodSelected,
          	scoringWindowStartDateTime:scoringWindowStartDateTime,
          	scoringWindowEndDateTime:scoringWindowEndDateTime,
          	assessmentProgramId:assessmentProgramId,
          	subjectName: subject,
          	numOfTests: numOfTests,
          	subjectNumOfTestForOTWindowAssignedBefore: false,
          	dacFlag:dialyAccessCodeflag,
          	dacStartTime:dacStartTime,
          	dacEndTime:dacEndTime,
          	dacStartDateTime : dacStartDateTime,
          	dacEndDateTime : dacEndDateTime
          }
	}).done(function(data) {
    	$("#newOperationalTestWindowErrorMessages").show();
    	if(data == '1'){  
    		clearFormOnReset();
    		$("#newOperationalTestWindowErrorMessages").show();
    		$("#newSuccessMessage").show();
    		scrollCreatePageTop('newSuccessMessage');
    		setTimeout(function() { 
    			$("#newOperationalTestWindowErrorMessages").hide();
    		    $("#newSuccessMessage").hide(); 
    		    }, 5000);
    		$("#OperationalTestWindowSetupsave").prop("disabled",false);
    	}else{
    		$('#newWindowNameCommonRequiredErrorMessage').show().html(data);
    		$("#OperationalTestWindowSetupsave").prop("disabled",false);
    	}
    	$("#viewOperationalWindowTableId").trigger('reloadGrid');
    	scrollCreatePageTop('newWindowNameCommonRequiredErrorMessage');
    }).fail(function(jqXHR,textStatus,errorThrown) {
    	$("#OperationalTestWindowSetupsave").prop("disabled",false);
  	   	$("#newOperationalTestWindowErrorMessages").show();
   		$('#newOperationalTestWindowErrorMessages').html(errorThrown);
   		scrollCreatePageTop('newOperationalTestWindowErrorMessages');
	});
  });

    function scrollCreatePageTop(id){
    	$("#newOperationalTestWindow").animate({ scrollTop: 0}, 1000);
    	setTimeout(function(){
    		$('#'+id).hide();
    	}, 10000);
    }

$("#OperationalTestWindowSetupcancel").on("click",function(e){
	clearFormOnReset();
});

$('#newSearchTestCollectionBtn').on('click',function(){
	
	var $gridAuto = $("#selectTestGridTableId");
	$('#SelectedCollectionId').jqGrid('clearGridData');
	$('#selectTestGridTableId').jqGrid('clearGridData');
	var assessmentProgramId = '0';
	var randomizationType = "null";
	var categoryCode = "null";
	var windowId = 0;
	if( $("#assessmentProgramsAddNewTestWindowSelect").val() == '' )
	{
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newAssessProgramErrorMessage").show();
		scrollCreatePageTop('newAssessProgramErrorMessage');
		return false;    	 
	}else{
		assessmentProgramId = $("#assessmentProgramsAddNewTestWindowSelect").val();
	}
	
	
	if( $("#managedbyCodeSelect").val() == ''){
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newManagedByRequiredErrorMessage").show();
		scrollCreatePageTop('newManagedByRequiredErrorMessage');
		return false;
	}else{
		categoryCode = $("#managedbyCodeSelect").val();
	}
	
	if($("#managedbyCodeSelect").val() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST' && $("#randomizedCodeSelect").val() == ''){
		$("#newOperationalTestWindowErrorMessages").show();
		$("#newRandomizationErrorMessage").show();
		scrollCreatePageTop('newRandomizationErrorMessage');
		return false;
	}else if($("#managedbyCodeSelect").val() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST' && $("#randomizedCodeSelect").val() != ''){
		randomizationType = $("#randomizedCodeSelect").val();
	}
	
	 $gridAuto.jqGrid('setGridParam',{
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
});

function scrollCreatePageTop(id){
	$("#newOperationalTestWindow").animate({ scrollTop: 0}, 1000);
	setTimeout(function(){
		$('#'+id).hide();
	}, 5000);
}

}; 

function loadNewOperationalTestCollectionWindow(){
	hideErrorMessages();
	var $gridAuto = $("#selectTestGridTableId");
	//Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
	
	if($('#managedbyCodeSelect').val()=='MANUAL_DEFINED_ENROLLMENT_TO_TEST')
	{
		$('.group2').hide();
	} else {
		$('.group2').show();
	}
	$('#SelectedCollectionId').jqGrid('clearGridData');
	$('#selectTestGridTableId').jqGrid('clearGridData');
	var assessmentProgramId = '0';
	var organizationTypeId = '0';
	var randomizationType = "null";
	var categoryCode = "null";
	var windowId = 0;
	
	if( $("#assessmentProgramsAddNewTestWindowSelect").val() != '' )
	{	
		assessmentProgramId = $("#assessmentProgramsAddNewTestWindowSelect").val();
	}else{
		$gridAuto.jqGrid('clearGridData');
		$('#SelectedCollectionId').jqGrid('clearGridData');
		return false;    	 
	}
	if( $("#orgFilterStateIdTestWindowSelect").val() != '' )
	{	
		organizationTypeId = $("#orgFilterStateIdTestWindowSelect").val();
	}else{
		$gridAuto.jqGrid('clearGridData');
		$('#SelectedCollectionId').jqGrid('clearGridData');
		return false;    	 
	}
	if( $("#managedbyCodeSelect").val() != ''){
		categoryCode = $("#managedbyCodeSelect").val();
	}else{
		$gridAuto.jqGrid('clearGridData');
		$('#SelectedCollectionId').jqGrid('clearGridData');
		return false;    	 
	}
	if( $("#managedbyCodeSelect").val() == 'SYSTEM_DEFINED_ENROLLMENT_TO_TEST' && $("#randomizedCodeSelect").val() == ''){
		$gridAuto.jqGrid('clearGridData');
		$('#SelectedCollectionId').jqGrid('clearGridData');
		return false;	 
	}else if($("#managedbyCodeSelect").val() == 'MANUAL_DEFINED_ENROLLMENT_TO_TEST'){
		//randomizationType = '';
	}else{
		randomizationType = $("#randomizedCodeSelect").val();
	}
}


      function populateNewTestWindowAssessmentProgram(){
			$('#assessmentProgramsAddNewTestWindowSelect').select2({
    			placeholder:'Select',
    			multiple: false
    		});
    		
    		var vowAPSelect = $('#assessmentProgramsAddNewTestWindowSelect'), vowOptionText='';
    		vowAPSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
    		
    		$.ajax({
    			url: 'getAssessmentProgramsByUserSelected.htm',
    			dataType: 'json',
    			type: "POST"
    		}).done(function(vowAssessmentPrograms) {				
				if (vowAssessmentPrograms !== undefined && vowAssessmentPrograms !== null && vowAssessmentPrograms.length > 0) {
					$.each(vowAssessmentPrograms, function(i, vowAssessmentProgram) {
						vowOptionText = vowAssessmentPrograms[i].programName;
						if(vowAssessmentProgram.id==$('#hiddenCurrentAssessmentProgramId').val()){
							vowAPSelect.append($('<option selected=\''+'selected'+'\'></option>').val(vowAssessmentProgram.id).html(vowOptionText));
						}else{
							vowAPSelect.append($('<option></option>').val(vowAssessmentProgram.id).html(vowOptionText));
						}
					});
					vowAPSelect.trigger('change');
				} 
				vowAPSelect.select2();
				vowAPSelect.trigger('change.select2');
    		});
    		$('#orgFilterStateIdTestWindowSelect').select2();
    	}
     
      
      function findNextOrg(currentOrg) {
    		var stateId = $('#orgFilterStateIdTestWindowSelect').val();
    		if(stateId == -1)
    			stateId = 0;

    		for(var i=10;i<80;i=i+10) {
    			if($.inArray(parseInt(currentOrg)+i, orgHierarchies[stateId]) >= 0) {
    				childOrg = parseInt(currentOrg)+i;
    				return;
    			}
    		}
    		childOrg = 80;
    	}
  
      function convert_to_24h(time_str) {
    	  time_str = time_str.toLowerCase();
    	 if( time_str.indexOf("am") < 0 &&  time_str.indexOf("pm") < 0 )
    	  {    		 
    		 return "";
    	  }	 
  	    // Convert a string like 10:05:23 PM to 24h format, returns like [22,5,23]
  	    var time = time_str.match(/(\d{1,2}):(\d{1,2}):(\d{1,2}) (\w)/);
  	     if(time == null)
  	      {
  	    	 return "";
  	      }
  	    
  	    var hours = Number(time[1]);
  	    var minutes = Number(time[2]);
  	    var seconds = Number(time[3]);
  	    var meridian = time[4].toLowerCase();
			var time24HourFormat;
  	    if (meridian == 'p' && hours < 12) {
  	      hours = hours + 12;
  	    }
  	    else if (meridian == 'a' && hours == 12) {
  	      hours = hours - 12;
  	    }
  	    hours=hours < 10 ? '0'+hours : hours;
  	    minutes = minutes < 10 ? '0'+minutes : minutes;
  		seconds = seconds < 10 ? '0'+seconds : seconds;
  	   
  	    time24HourFormat = hours+':'+minutes+':'+seconds;
  	    
  	    return time24HourFormat;
  	  };	
 
     
 function hideErrorMessages(){
	 	$('#newWindowInvalidDACTimeErrorMessage').hide();
		$("#newOperationalTestWindowErrorMessages").hide();
		$("#newWindowNameRequiredErrorMessage").hide();
		$("#newWindowTicketingRequiredErrorMessage").hide();
		$("#newWindowTestEnrollmentMethodRequiredErrorMessage").hide();
		$("#newWindowScoringAssignmentMethodRequiredErrorMessage").hide();
		$("#newManagedByRequiredErrorMessage").hide();
		$("#newRandomizationErrorMessage").hide();
		$("#newBeginDateErrorMessage").hide();
		$("#newEndDateErrorMessage").hide();
		$("#newWindowNameCommonRequiredErrorMessage").hide();
		$("#newTestCollectionRequiredErrorMessage").hide();
		$("#newWindowInvalidDateErrorMessage").hide();
		$("#newAssessProgramErrorMessage").hide();
		$("#SelectedCollectionId")[0].clearToolbar();
		$("#selectTestGridTableId")[0].clearToolbar();
		
 }
 
 function numberOfTestsBySubjectFormatter(cellvalue, options, rowObject){
	 return "<input style='width:50px;' type='text' min='0' max ='99' value='0' id='inp_CreateNumberOfTests_Subject_"+options.rowId+"' />";
 }
 
 function clearFormOnReset(){
	 hideErrorMessages();
	 $('#dailyAccessCodeTimeFrameDiv').hide();
	 $('#selectNumbeOfTestBySubjectGrid').hide();
	 $("#windowName").val("");
	 $("#gracetimeInMin").val("");
	 $("#testwindowstartTime").val("");
	 $("#testwindowendTime").val("");
	 $("#testwindowstartDate").val("");
	 $("#testwindowendDate").val("");
	 $("#testwindowScoringStartDate").val("");
	 $("#testWindowScoringEndDate").val("");
	 //initializedates();
	 $('#assessmentProgramsAddNewTestWindowSelect').val("0").trigger('change.select2');
	 $('#orgFilterStateIdTestWindowSelect').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
	// $('#ticketingDaySelect').find('option').filter(function(){return $(this).val().length > 0;}).remove().end();
	 $('#testEnrollmentMethod').find('option').filter(function(){return $(this).val().length > 0;}).remove().end();
	 $('#orgFilterStateIdTestWindowSelect').val("0").trigger('change.select2');
	 $('#ticketingDaySelect').val("0").trigger('change.select2');
	 $('#testEnrollmentMethod').val("0").trigger('change.select2');
	 $('#managedbyCodeSelect').val("0").trigger('change.select2');
	 $('#randomizedCodeSelect').val("0").trigger('change.select2');
	 $('#scoringWindowMethod').val("0").trigger('change.select2');
	 $('#scoringAvailableTimeFrameDiv').hide();
	 $('.scoringWindowMethoddiv').hide();
	 
	 $('input#ticketingoff').prop('checked', true);
	 $('input#testExitId2').prop('checked', true);
	 $('input#graceperoidoff').prop('checked', true);
	 $('input#testenrollemntoff').prop('checked', true);
	 $('input#scoringWindowOff').prop('checked', true);
	 $('input#windowsuspend').prop('checked',false);
	 $('input#testenrollmenton').prop('checked',false);
	 $("#selectTestGridTableId").jqGrid('clearGridData');
	 $('#SelectedCollectionId').jqGrid('clearGridData');
	 $("#ticketingoftheday").hide();
	 $(".testenrollmentdiv").hide();
	/* $('#selectTestGridTableId').orgFilter('reset');
	 $("#selectTestGridTableId").jqGrid('clearGridData');
	 $("#selectTestGridTableId")[0].clearToolbar();
	 $('#SelectedCollectionId').clearGridData(); */
 }
 
 /* Sets time in clock label and calls itself every second */	
	(function($){
		$.clock={version:"2.0.1",locale:{}};
		t=[];
		$.fn.clock = function(d){
			var c={		
				en:{weekdays:["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],
				months:["January","February","March","April","May","June","July","August","September","October","November","December"]}};
			return this.each(function(){$.extend(c,$.clock.locale);d=d||{};d.timestamp=d.timestamp||"z";y=new Date().getTime();d.sysdiff=0;if(d.timestamp!="z"){d.sysdiff=d.timestamp-y}d.langSet=d.langSet||"en";d.format=d.format||((d.langSet!="en")?"24":"12");d.calendar=d.calendar||"true";if(!$(this).hasClass("jqclock")){$(this).addClass("jqclock");}var e=function(g){if(g<10){g="0"+g}return g;},f=function(j,n){var r=$(j).attr("id");if(n=="destroy"){clearTimeout(t[r]);}else{m=new Date(new Date().getTime()+n.sysdiff);var p=m.getHours(),l=m.getMinutes(),v=m.getSeconds(),u=m.getDay(),i=m.getDate(),k=m.getMonth(),q=m.getFullYear(),o="",z="",w=n.langSet;if(n.format=="12"){o=" AM";if(p>11){o=" PM"}if(p>12){p=p-12}if(p===0){p=12}}p=e(p);l=e(l);v=e(v);if(n.calendar!="false"){z=((w=="en")?"<span class='clockdate'>"+c[w].weekdays[u]+", "+c[w].months[k]+" "+i+", "+q+"</span>":"<span class='clockdate'>"+c[w].weekdays[u]+", "+i+" "+c[w].months[k]+" "+q+"</span>");}$(j).html(z+"<span class='clocktime'>"+p+":"+l+":"+v+o+"</span>");t[r]=setTimeout(function(){f($(j),n)},1000);}};f($(this),d);});};return this;
	})(jQuery);
      
