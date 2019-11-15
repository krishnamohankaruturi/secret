var seletedRows;
var assessmentProgramValues;
var testingProgramValues;
var assessmentValues;
var gradeCourseValues;
var contentAreaValues;	
var myDefaultSearch = 'cn';
var selectedTestids=[];



$(function() {
    $( "#saveTopBtn,#saveBottomBtn").on("click", function() {
		saveAcademicDates();
	});
});


function orgAcademicYearInit(){
	initOrgAcademicYearGrid();
};


function initOrgAcademicYearGrid() {	
	var $gridAuto = $("#orgAcademicYearTable");
	$("#orgAcademicYearTable").jqGrid('clearGridData');	
	$("#orgAcademicYearTable").jqGrid("GridUnload");
	var gridWidthForVO =  1048;
	
	var cellWidthForVO = (gridWidthForVO/3);
	
	var colModel =  [
	    {name: 'organizationName', index: 'organizationName',sortable: false, search : false, width : cellWidthForVO, classes:'stateNameColumn', editable: false},
		{name:'schoolStartDate', index:'schoolStartDate', width:cellWidthForVO, sortable: false, search : false, 
	    	editable: true,
	    	formatter: function(cellValue, options, rowObject){
	    		var htmlString = '';
				if(cellValue != 'Not Available' && cellValue != '' && cellValue != undefined) { 
					var date = new Date(cellValue);
					var value =  ($.datepicker.formatDate({
					            dateFormat: 'mm-dd-yy',
					            changeMonth: true,
					            changeYear: true
					        }, date));
					htmlString = "<input id='schoolStartDate_"+options.rowId+"' class='datepickerfield' name='schoolStartDate_"+options.rowId+"' placeholder='mm-dd-yyyy' title='mm-dd-yyyy' style='width:50%; font-size: 14px;text-align:center;'/>"
   				} else { 
   						htmlString = "<input id='schoolStartDate_"+options.rowId+"' class='datepickerfield' name='schoolStartDate_"+options.rowId+"' placeholder='mm-dd-yyyy' title='mm-dd-yyyy' style='width:50%; font-size: 14px;text-align:center;'/>"
   				}
				return htmlString
			}
		
        },
		{name:'schoolEndDate', index:'schoolEndDate', width:cellWidthForVO, sortable:false, search:false, editable: true,
            align: "center", sorttype: "date", 
            formatter: function(cellValue, options, rowObject){
				var htmlString = '';
				if(cellValue != 'Not Available' && cellValue != '' && cellValue != undefined) { 
					var date = new Date(cellValue);
					var value =  ($.datepicker.formatDate({
				            dateFormat: 'mm-dd-yy',
				            changeMonth: true,
				            changeYear: true
				        }, date));
					htmlString = "<input id='schoolEndDate_"+options.rowId+"' class='datepickerfield' name='schoolEndDate_"+options.rowId+"' placeholder='mm-dd-yyyy' title='mm-dd-yyyy' style='width:50%; font-size: 14px;text-align:center;'/>"
   				} else { 
   						htmlString = "<input id='schoolEndDate_"+options.rowId+"' class='datepickerfield' name='schoolEndDate_"+options.rowId+"' placeholder='mm-dd-yyyy' title='mm-dd-yyyy' style='width:50%; font-size: 14px;text-align:center;'/>"
   				}
				return htmlString
			}
		},
		{name: 'displayIdentifier', index: 'displayIdentifier',sortable: false, search : false, hidden: true, hidelg: true},
		{name: 'id', index: 'id',sortable: false, search : false, hidden: true, hidelg: true}
			
	]
	
	$gridAuto.scb({
		datatype: "local",
        colNames: [
                   'State',
                    'First Day of Academic Year',	
                    'Last Day of Academic Year', 
                    'Display Identifier',
                    'Id'
                   ],
        colModel: colModel,
        width:gridWidthForVO,
        loadtext: 'Loading ...',
        hoverrows:false,
        beforeSelectRow:function (rowId, e) {
        	return true;
        },
        onCellSelect: function(rowId, col, content, event) {
        	var cm = $gridAuto.jqGrid("getGridParam", "colModel");
        	if(cm[col].name == 'schoolStartDate')
        	{
            	$("#schoolStartDate_" + rowId).datepicker({
			            dateFormat: 'mm-dd-yy',
			            changeMonth: true,
			            changeYear: true
            	    }).attr('readonly', 'readonly').keyup(function(e) {
            	    if(e.keyCode == 8 || e.keyCode == 46) {
            	        $.datepicker._clearDate(this);
            	    }
            	});
        	}
            else if(cm[col].name == 'schoolEndDate')
        	{
            	$("#schoolEndDate_" + rowId).datepicker({
			            dateFormat: 'mm-dd-yy',
			            changeMonth: true,
			            changeYear: true
			        }).attr('readonly', 'readonly').keyup(function(e) {
            	    if(e.keyCode == 8 || e.keyCode == 46) {
            	        $.datepicker._clearDate(this);
            	    }
            	});
        	}	
        },
        gridComplete: function () {
        	
        	var $grid = jQuery("#orgAcademicYearTable"), rows = $grid[0].rows, cRows = rows.length,
            iRow, rowId, row, cellsOfRow;

        	for (iRow = 0; iRow < cRows; iRow++) {
	            row = rows[iRow];
	            if ($(row).hasClass("jqgrow")) {
	                cellsOfRow = row.cells;
	                for(var iCol = 0; iCol < cellsOfRow.length; iCol++){
	                	 $(cellsOfRow[iCol]).trigger("click");
	                }
	            }
	        }
        },
        hiddengrid: false,
        jsonReader : {
            root: "rows",
            page: "page",
            total: "total",
            records: "records",
            repeatitems: true,
            cell: "cell",
            id: "id"
        }
	});
	$gridAuto[0].toggleToolbar();
	
	$.ajax({
        url: 'getAllStates.htm',
        dataType: 'json',
        type: "GET"     
	}).done(function(results) {
    	if (results != null){
    		$gridAuto.setGridParam({rowNum:results.length, 'data': results}).trigger('reloadGrid');
    	}
    });
}

function saveAcademicDates() {
	var $gridAuto = $("#orgAcademicYearTable");
	var data = $gridAuto.getGridParam('data');
	var formData = [];
	var validDates = true;
	var currentDate = new Date();
	var notCurrentYearDates = false;
	
	for (var i = 0; i < data.length; i++) {
	    var startDate =  $("#schoolStartDate_" + data[i].id).val(); 
	    var endDate = $("#schoolEndDate_" + data[i].id).val();
	 
	   if((startDate != null && startDate !="" && startDate != undefined) 
		    		&& (endDate != null && endDate != "" && endDate != undefined)){
		    var item = {};
	    	 var startDateFormatted = new Date(startDate.split("-")[2]
		                ,startDate.split("-")[0]-1
		                ,startDate.split("-")[1]
		                ,0,0,0);
	    	 var endDateFormatted = new Date(endDate.split("-")[2]
		                ,endDate.split("-")[0]-1
		                ,endDate.split("-")[1]
		                ,0,0,0);
	    	 
	    	 
	    	 if(startDateFormatted.getFullYear() < currentDate.getFullYear() || endDateFormatted.getFullYear() < currentDate.getFullYear()){
	    		$("#saveAcademicDatesError").html('Invalid date range. First day/Last day set to past school year.').show(); 
	         	$('#saveAcademicDatesError').show();
				$('html, body').animate({
					scrollTop: $("#saveAcademicDatesError").offset().top
				}, 1500);
				setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
	    		return;
	    	 }
	    	
	    	 if (startDateFormatted.getTime() > endDateFormatted.getTime())  {
	         	$("#saveAcademicDatesError").html('Invalid date range. First day must be before last day.').show(); 
	         	$('#saveAcademicDatesError').show();
				$('html, body').animate({
					scrollTop: $("#saveAcademicDatesError").offset().top
				}, 1500);
				setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
	    		return;
	 		 }
	    	 
	    	 /*
	    	 if(endDateFormatted.getFullYear()-startDateFormatted.getFullYear()>1){
	    		$("#saveAcademicDatesError").html('Invalid date range. Academic year date range cannot be more than a year apart.').show(); 
	         	$('#saveAcademicDatesError').show();
				$('html, body').animate({
					scrollTop: $("#saveAcademicDatesError").offset().top
				}, 1500);
				setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
	    		return;
	    	 }
	    	 */
	    	 if(endDateFormatted.getFullYear() == startDateFormatted.getFullYear()){
		    		$("#saveAcademicDatesError").html('Invalid date range. Academic year dates cannot be in the same year.').show(); 
		         	$('#saveAcademicDatesError').show();
					$('html, body').animate({
						scrollTop: $("#saveAcademicDatesError").offset().top
					}, 1500);
					setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
		    		return;
		    	 }
	    	 /*
	    	 if(startDateFormatted.getFullYear() > currentDate.getFullYear()){
	    		 notCurrentYearDates = true;
	    		
	    		$("#saveAcademicDatesError").html('Invalid date range. Dates are not set for the current academic year ('+currentDate.getFullYear()+' - '+(currentDate.getFullYear()+1)+').').show(); 
	         	$('#saveAcademicDatesError').show();
				$('html, body').animate({
					scrollTop: $("#saveAcademicDatesError").offset().top
				}, 1500);
				setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
	    		return;
	    	 }
	    	 */
	    	 item ["organizationId"] = data[i].id;
	    	 item ["organizationName"] = data[i].organizationName;
	    	 item ["displayIdentifier"] = data[i].displayIdentifier;
	         item ["schoolStartDate"] = startDateFormatted;
	         item ["schoolEndDate"] = endDateFormatted;
	         
	         formData.push(item);
	   }
	   else
	   {
		   if(((startDate == null || startDate=="" || startDate == undefined) && (endDate != null && endDate != "" && endDate != undefined)) || 
				   ((startDate != null && startDate !="" && startDate != undefined) && (endDate == null || endDate == "" || endDate == undefined)))
		   {
			   $('#saveAcademicDatesError').html('Invalid date range. Both first day and last day must be provided.');
				$('#saveAcademicDatesError').show();
				$('html, body').animate({
					scrollTop: $("#saveAcademicDatesError").offset().top
				}, 1500);
				setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
				return;
		   }
	   }
	    
	}
	if(formData.length>0){
		sendSaveRequest(formData);
	}
	else{
		$('#saveAcademicDatesError').html('Settings not provided.');
		$('#saveAcademicDatesError').show();
		$('html, body').animate({
			scrollTop: $("#saveAcademicDatesError").offset().top
		}, 1500);
		setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
		return;
	}
		
	
	return;
	
}

function sendSaveRequest(formData){
	$.ajax({
		url: 'saveOrganizationAnnualResets.htm',
		data: {
			data: JSON.stringify(formData)
		},
		dataType: 'text',
		type: "POST"
		
	}).done(function(response) {
		
		var responseObject = JSON.parse(response);
		if(responseObject.resetOrgsSaved){
			$('#saveAcademicDatesError').html('');
			$('#saveAcademicDatesError').hide();
			$('#saveAcademicDatesSuccess').html("Settings changes saved successfully.");
			$('#saveAcademicDatesSuccess').show();
			$("#orgAcademicYearTable").trigger("reloadGrid");
			$('html, body').animate({
				scrollTop: $("#saveAcademicDatesSuccess").offset().top
			}, 1500);
			setTimeout(function(){ $("#saveAcademicDatesSuccess").hide(); },5000);
		}
		else
		{
			$('#saveAcademicDatesError').html('Settings changes not saved.');
			$('#saveAcademicDatesError').show();
			$('html, body').animate({
				scrollTop: $("#saveAcademicDatesError").offset().top
			}, 1500);
			setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
		}
		
	}).fail (function(jqXHR, textStatus, errorThrown) {
		$('#saveAcademicDatesError').html('Settings changes not saved. Error Thrown');
		$('#saveAcademicDatesError').show();
		$('html, body').animate({
			scrollTop: $("#saveAcademicDatesError").offset().top
		}, 1500);
		setTimeout(function(){ $("#saveAcademicDatesError").hide(); },5000);
	});
	return;
}
