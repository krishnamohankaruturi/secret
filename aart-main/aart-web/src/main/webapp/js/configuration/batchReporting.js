function batchReportingInit(){
	$('#batchReportForm')[0].reset();
	$("label.error").html('');
	jQuery.validator.setDefaults({
			submitHandler: function() {		
			},
			errorPlacement: function(error, element) {
				if(element.hasClass('required') || element.attr('type') == 'file') {
					error.insertAfter(element.next());
				}
				else {
		    		error.insertAfter(element);
				}
		    }
	});
	$('#progressReport').html("");
	$('#reportProcessProgress').html("");
	$('#batchReportErrors').html("");
	$('#batchReportingMessage').html("");
	$('.hidden', $('#batchReportContainer')).hide();
	$('#gradesReport, #coursesReport, #assessmentReportPrograms, #reportTestingPrograms').select2({
		placeholder:'Select',
		multiple: false
	});
	
	populateReportAssessmentPrograms();
	$('#errorReportDetailsDailog').dialog({
		resizable: false,
		height: 700,
		width: 1200,
		modal: true,
		autoOpen:false,
		buttons: {
		    Close: function() {
		    	 $(this).dialog('close');
		    }			    
		}
	});
	
	
	$('#processBtn').on("click",function(){
		$('#batchReportErrors').html("");
		$('#batchReportingMessage').html("").hide();
		var batchReportMessage = "";
		var validationFlag = false;
		var assessmentProgramId = $('#assessmentReportPrograms').val();
		var subjectId = $('#coursesReport').val();
		var gradeId = $('#gradesReport').val();
		
		
		$('#testingProgramError').hide();
		$('#processSubmitError').hide();
		if(assessmentProgramId == 3){
			if(subjectId == null || subjectId == "" || subjectId == 0){
				$("#coursesReport").val(null);
			}
			if (gradeId == null || gradeId == "" || gradeId == 0){
				$("#gradesReport").val(null);
			}
			
			var testingProgram = $('#reportTestingPrograms').val();
			var processReport = $('input[name="processReport"]:checked').val();
			var processByStudentId = $('input[name="processByStudentId"]:checked').val();
			var generateStudentReport = $('input[name="generateStudentReport"]:checked').val();
			var generateSchoolAndDistrictReport = $('input[name="generateSchoolAndDistrictReport"]:checked').val();
			var generateSchoolFilesOfStudentReport = $('input[name="generateSchoolFilesOfStudentReport"]:checked').val();
			var generateDistrictFilesOfStudentReport = $('input[name="generateDistrictFilesOfStudentReport"]:checked').val();
			var generateStudentSummaryBundledBySchool = $('input[name="generateStudentSummaryBundledBySchool"]:checked').val();
			var generateStudentSummaryBundledByDistrict = $('input[name="generateStudentSummaryBundledByDistrict"]:checked').val();
			var generateSchoolSummaryBundled = $('input[name="generateSchoolSummaryBundled"]:checked').val();
			
			if(testingProgram == null || testingProgram == ""){
				$('#testingProgramError').show();
				validationFlag = false;
			}if((processReport == null || processReport == undefined) && (processByStudentId == null || processByStudentId == undefined) 
					&& (generateStudentReport == null || generateStudentReport == undefined) 
					&& (generateSchoolAndDistrictReport == null || generateSchoolAndDistrictReport == undefined)
					&& (generateSchoolFilesOfStudentReport == null || generateSchoolFilesOfStudentReport == undefined) 
					&& (generateDistrictFilesOfStudentReport == null || generateDistrictFilesOfStudentReport == undefined)
					&& (generateStudentSummaryBundledBySchool == null || generateStudentSummaryBundledBySchool == undefined) 
					&& (generateStudentSummaryBundledByDistrict == null || generateStudentSummaryBundledByDistrict == undefined)
					&& (generateSchoolSummaryBundled == null || generateSchoolSummaryBundled == undefined)){
				$('#processSubmitError').show();
				validationFlag = false;
			}else{
				validationFlag = true;
			}
		}else{
			validationFlag = $('#batchReportForm').valid();
		}
		
		var processVal =$('#process').is(':checked');
		var processReport= false;
		var generateStudentReport = false;
		var generateSchoolAndDistrictReport = false;
		
		if (validationFlag) {
			var processes = '';
			$('#processBtn').hide();
			$('#reportProcessProgress').html("SUBMITTED");
			$.ajax({
				url: 'processReportData.htm',
				data: $("#batchReportForm").serialize(),
				type: 'POST'
			})
			.done( function(results){
				if (results != null){
					$('#reportProcessProgress').html(results.status);
		        	$('#reportProcessProgress').data("reportProcessId", results.reportProcessId);
				}
			$('#reportHistoryBtn').click();
			monitorBatchReportProcessStatus();
			})
		} 
		
	});
	
	$("#reportFromDate, #reportToDate").datepicker();
	$('#reportFromDate').datepicker("setDate", "+0");
	$('#reportToDate').datepicker("setDate", "+0");
	
	$('#reportHistoryBtn').off('click').on('click', function(event) {
		$('.loading').show();
		var reportFromDate = $('#reportFromDate').val();
		var reportToDate = $('#reportToDate').val();
		
		jQuery.validator.addMethod(
    		    "greaterThanOrEqualTo",
    		    function(value, element, params) {
    		        var target = $(params).val();
    		        var isValueNumeric = !isNaN(parseFloat(value)) && isFinite(value);
    		        var isTargetNumeric = !isNaN(parseFloat(target)) && isFinite(target);
    		        if (isValueNumeric && isTargetNumeric) {
    		            return Number(value) >= Number(target);
    		        }

    		        if (!/Invalid|NaN/.test(new Date(value))) {
    		            return new Date(value) >= new Date(target);
    		        }

    		        return false;
    		    },
    		    '');
		
		jQuery.validator.addMethod(
    		    "isDate",
    		    function(value, element) {
    		    	if(value=='')
    		    		return false;
    		    	
    		    	var regexDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    		    	var dateMatch = value.match(regexDatePattern); 
    		    	
    		    	if (dateMatch == null)
    		    		return false;
    		    	
    		    	var dtMonth = dateMatch[1];
    		    	var dtDay= dateMatch[3];
   		    	    var dtYear = dateMatch[5];
   		    	    
   		    	    if (dtMonth < 1 || dtMonth > 12) 
	   		          return false;
	   		        else if (dtDay < 1 || dtDay> 31) 
	   		          return false;
	   		        else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31) 
	   		          return false;
	   		        else if (dtMonth == 2) 
	   		        {
	   		          var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
	   		          if (dtDay> 29 || (dtDay ==29 && !isleap)) 
	   		                  return false;
	   		        }
   		    	    return true;
    	        },
    		    'Please enter a valid date');
		
		$('#batchReportHistoryForm').validate({
    		ignore: '',
    		rules: {
    			reportFromDate: 
    			{
    				required: true,
    				isDate: true
    			},
    			reportToDate: 
    			{
    				required: true,
    				greaterThanOrEqualTo: '#reportFromDate',
    				isDate: true
    			}
    		},
    		messages: {
    			reportFromDate: 'Please enter a valid date.',
    			reportToDate: 'Please enter a valid date.'
    		}
    	});
		
		if ($('#batchReportHistoryForm').valid()) 
		{
		
			var reportFromDateFmt = $('#reportFromDate').datepicker('getDate');
			var reportFromStartDate = new Date(reportFromDateFmt.getFullYear()
	                ,reportFromDateFmt.getMonth()
	                ,reportFromDateFmt.getDate()
	                ,0,0,0); 
			
	        var reportToDateFmt = $('#reportToDate').datepicker('getDate');
			var reportToEndDate = new Date(reportToDateFmt.getFullYear()
	                ,reportToDateFmt.getMonth()
	                ,reportToDateFmt.getDate()
	                ,23,59,59);
			$("#batchReportHistoryDateErrors").hide();
			var $batchReportGrid = $("#batchReportHistoryGridTableId");
			
			$batchReportGrid.jqGrid('clearGridData');
			var sidx = $batchReportGrid.jqGrid('getGridParam','sortname');
			var sord = $batchReportGrid.jqGrid('getGridParam','sortorder');
			var page = $batchReportGrid.jqGrid('getGridParam','page');
			var rows = $batchReportGrid.jqGrid('getGridParam','rowNum');
			
			$.ajax({
		        url: 'getReportProcessHistory.htm',
		        data : 
		        {
					reportFromDate: reportFromStartDate,
					reportToDate: reportToEndDate,
					sidx: sidx,
					sord: sord,
					page: page,
					rows: rows
				},
				type: 'POST'
		        
			}).done(function (reportHistoryData) {
	        	 $batchReportGrid.setGridParam({'data': reportHistoryData}).trigger('reloadGrid');
	        	 $('.loading').hide();
	        })
		}
		
	});	
	
	var gridWidthForReport = $('#batchReportHistoryGridContainer').width();		
	
	if(gridWidthForReport < 1050) {
		gridWidthForReport = 1050;				
	}
	
	$('#batchReportHistoryGridTableId').scb({
        datatype: "local",
        data: [],
        loadonce: true,
		filterToolbar: true,
		viewable: false,
        colNames: [
                   'Id',
				   'Submission',	                       
				   'Ended',
                   'Assessment Program',
                   'Testing Program',
                   'Report Cycle',
                   'Subject',
                   'Grade',
                   'Process',
                   'Status',
                   'Number Errors',
                   'Error File',
                   'Record Counts',
                   'Count',
                   'State'
                  ],
        colModel: [
				   {name: 'id', index: 'id', hidden:true},
   				   {name: 'submissionDate', search : true, index: 'submissionDate', search: false, width: 160, hidedlg: true,  
					   formatter: function(cellValue, options, rowObject){
							if(cellValue != 'Not Available' && cellValue != '') { 
								var date = new Date(cellValue);
	           					var ampm = date.getHours() >= 12 ? 'PM' : 'AM';
	           					var hours = date.getHours() % 12;
	           					if (hours == 0){
	           						hours = 12;
	           					}
	           					var timeStr = (hours > 9 ? hours : '0' + hours.toString()) + ':' +
	           							(date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes().toString()) + ':' +
	           							(date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds().toString());
	           					timeStr += ' ' + ampm;
	           					return ($.datepicker.formatDate('mm/dd/yy', date)) + ' ' + timeStr;
	           				} else { return cellValue; }
						}
				   },
				   {name: 'modifiedDate', search : true, index: 'modifiedDate', search: false, width: 160, hidedlg: true,
					   formatter: function(cellValue, options, rowObject){
						   var status = rowObject.status.toUpperCase();
						   
						   if(status != 'IN PROGRESS' && status != 'STARTED')
						   {
							 if(cellValue != 'Not Available' && cellValue != '') { 
								if(cellValue == null)
								{
									return "";
								}
								var date = new Date(cellValue);
	           					var ampm = date.getHours() >= 12 ? 'PM' : 'AM';
	           					var hours = date.getHours() % 12;
	           					if (hours == 0){
	           						hours = 12;
	           					}
	           					var timeStr = (hours > 9 ? hours : '0' + hours.toString()) + ':' +
	           							(date.getMinutes() > 9 ? date.getMinutes() : '0' + date.getMinutes().toString()) + ':' +
	           							(date.getSeconds() > 9 ? date.getSeconds() : '0' + date.getSeconds().toString());
	           					timeStr += ' ' + ampm;
	           					return ($.datepicker.formatDate('mm/dd/yy', date)) + ' ' + timeStr;
	           				} else { return cellValue; }
						  }
						  return "";
					   }
				   },
				   {name: 'assessmentProgramName', index: 'assessmentProgramName', search: false, hidedlg: true, width: 150},
				   {name: 'testingProgramName', index: 'testingProgramName', search: false, hidedlg: true, width: 100},
				   {name: 'reportCycle', index: 'reportCycle', search: false, hidedlg: true, width: 90},
				   {name: 'subjectName', index: 'subjectName', search: false, hidedlg: true, width: 130},
				   {name: 'gradeName', index: 'gradeName', search: false, hidedlg: true, width: 90},
				   {name: 'process', index: 'process', search: false, hidedlg: true, 
					   formatter: function(cellValue, options, rowObject){
	        				if(cellValue.indexOf(':') > -1){
	        					var processes = cellValue.split(":");
	        					var pString =""
	        					for(i = 0; i < processes.length; i++){
	        						if(processes[i].trim()!='')
	        							pString += processes[i]+'<br/>'; 
	        					}
	        					return pString;
	        				}
	        				else
	        					return cellValue;
	        			}
				   },
				   {name: 'status', index: 'status',search : false, hidedlg: true, width: 90},
				   {name: 'failedCount', index: 'failedCount', sortable: false, hidedlg: true, search: false, width: 80},
				   {name: 'errorInfo', index: 'errorInfo', sortable: false, hidedlg: true, search: false,
					   formatter: function(cellValue, options, rowObject){
						    var status = rowObject.status.toUpperCase();
						    var failedCount = rowObject.failedCount;
					    	
						    if(rowObject.resultJson != '0' && status != 'IN PROGRESS' && status != 'STARTED')
						    {
						    	var currDate = new Date();
					    		var twoDigitMonth = ((currDate.getMonth().length+1) === 1)? (currDate.getMonth()+1) : '0' + (currDate.getMonth()+1);
					    		var dateStringForFile = twoDigitMonth+""+currDate.getDate().toString()+""+currDate.getFullYear().toString()
					    		+"_"+currDate.getHours().toString()+""+currDate.getMinutes().toString()+""+currDate.getSeconds().toString();
					    		
					    		var fileName = rowObject.assessmentProgramName+"_"+rowObject.subjectName.replace(/ /g,"_")+"_Report_Errors_"+dateStringForFile+".csv";
								
					    		return "<a href='getReportsErrorFile.htm?reportId="+rowObject.id+"&gradeName="+rowObject.gradeName+"&fileName="+fileName+"' title='Show error file'><img src='images/icon-csv.png' title='Click to download.'/></a>";
					    	
						    }
	        				return "";
	        			}
				   },
				   {name: 'successCount', index: 'successCount', sortable: false, search: false, hidedlg: true, hidden: true, width: 50},
				   {name: 'count', index: 'count', sortable: false, search: false, hidedlg: true, width: 150},
				   {name: 'state', index: 'state', search: false,sortable: false, hidedlg: true, width: 150}
                   ],
        height : 'auto',
        //shrinkToFit: true,
        filterToolbar: false,
        pager : '#pbatchReportHistoryGridTableId',
        rowNum : 10,
        rowList : [5, 10, 20, 30, 60, 90],
        sortname : 'submissionDate',
        sortorder : 'desc',
        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
        altRows: true,
        altclass: 'altrow',		        
        hoverrows : false,
        multiselect : false,
        caption: '',
        beforeSelectRow: function(rowid, e) {	            
        	 
        },
        gridComplete: function() {	            	
        	 
        },
        width:gridWidthForReport-20
    }); 
		 
};

function monitorBatchReportProcessStatus(){
	var reportProcessId = $('#reportProcessProgress').data("reportProcessId");
    $.ajax({
        url: 'monitorBatchReportProcess.htm',
        data: {"reportProcessId": reportProcessId},
        type: "GET"
       
	}) .done(function(data) {
		if(data.status === 'COMPLETED' || !(data.status === 'STARTING' || data.status === 'STARTED' || data.status === 'In Progress')){		
        	$('#reportProcessProgress').html(data.status);
        	$('#reportProcessProgress').removeData("jobId");
        	$('#reportHistoryBtn').click();
        	$('#processBtn').show();
		} else {
			window.setTimeout("monitorBatchReportProcessStatus()", 10000);
		}  
    })
}

function resetBatchReporting(){
	$('#assessmentReportPrograms').val('').select2();
	$('#gradesReport').val('').select2();
	$('#coursesReport').val('').select2();
	$('#reportTestingPrograms').val('').select2();
	
	$('#gradesReport, #coursesReport, #reportTestingPrograms').find('option').filter(function(){
		return $(this).val() != '';
	}).remove().end();
	$('.hidden', $('#batchReportContainer')).hide();
}
function populateReportAssessmentPrograms() {
	var me = this;
	var apSelect = $('#assessmentReportPrograms'), optionText='';
	$('.messages').html('').hide();
	apSelect.find('option').filter(function(){
		return $(this).val() != '';
	}).remove().end();
	
	$.ajax({
		url: 'getAssessmentProgramsForBatchReporting.htm',
		dataType: 'json',
		type: "GET"
	}).done(function(assessmentReportPrograms) {				
		if (assessmentReportPrograms !== undefined && assessmentReportPrograms !== null && assessmentReportPrograms.length > 0) {
			$.each(assessmentReportPrograms, function(i, assessmentProgram) {
				$('#assessmentReportPrograms').append($('<option></option>').prop("value", assessmentProgram.id).text(assessmentProgram.programName));
			});
			
			if (assessmentReportPrograms.length == 1) {
				$("#assessmentReportPrograms option").removeAttr('selected').next('option').prop('selected', true);
				$("#assessmentReportPrograms").trigger('change');
			}
			$('#assessmentReportPrograms, #gradesReport, #coursesReport, #reportTestingPrograms').trigger('change.select2');
		}else {
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#batchReportErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
		}
	})
	var assessmentProgramId = 0;
	
	$('#assessmentReportPrograms').on("change",function() {
		$('#reportProcessProgress').html('');
 	    $('#batchReportErrors').html("");
		$('#batchReportingMessage').html("").hide();
		
		$('#coursesReport').find('option').filter(function(){return $(this).val() != '';}).remove().end();
		$('#gradesReport').find('option').filter(function(){return $(this).val() != '';}).remove().end();
		$('#reportTestingPrograms').find('option').filter(function(){return $(this).val() != '';}).remove().end();
		
		assessmentProgramId = $('#assessmentReportPrograms').val();
		
		if(assessmentProgramId == 3){
			$('#reportingSubjectID').hide();
			$('#reportingGradeID').hide();
		}else{
			$('#reportingSubjectID').show();
			$('#reportingGradeID').show();
		}
		
		if(assessmentProgramId=="")
		{
			$('#gradesReport, #coursesReport, #reportTestingPrograms').select2({
				placeholder:'Select',
				multiple: false
			});
		}
		else if (assessmentProgramId != 0) {
			$('.hidden', $('#batchReportContainer')).show();
			
			//populate testing programs			
			$.ajax({
				url: 'getTestingProgramsForReportingByAssessmentProgram.htm',
				data: {assessmentProgramId: assessmentProgramId},
				dataType: 'json',
				type: 'POST'
			}).done( function(testPrograms){
				if (testPrograms !== undefined && testPrograms !== null && testPrograms.length > 0) {
					$.each(testPrograms, function(i, testProgram) {//alert(testProgram.programName + ' ID:'+testProgram.id);
					$('#reportTestingPrograms').append($('<option></option>').prop("value", testProgram.id).text(testProgram.programName));
					});
				}
				
				$("#reportTestingPrograms").trigger('change.select2');
			})
			
			$.ajax({
		        url: 'getCoursesBasedOnAssessmentProgram.htm',
		        data: {
		        	assessmentProgramId: assessmentProgramId
		        	},
		        dataType: 'json',
		        type: "GET"
			}).done(function(gradeCourses) {
	        	if(gradeCourses.length>1)
	        		$('#coursesReport').append($('<option></option>').prop("value", -1).text('All'));
				$.each(gradeCourses, function(i, gradeCourse) {
					$('#coursesReport').append($('<option></option>').prop("value", gradeCourse.id).text(gradeCourse.name));
				});
				
				if (gradeCourses.length == 1) {
					$("#coursesReport option").removeAttr('selected').next('option').prop('selected', 'selected');
					$("#coursesReport").trigger('change');
				}
				$('#gradesReport, #coursesReport').trigger('change.select2');
	        })		
			
		}
	});
	

	$('#coursesReport').on("change",function() {
		$('#reportProcessProgress').html('');
		$('#batchReportErrors').html("");
		$('#batchReportingMessage').html("").hide();
		$('#gradesReport').find('option').filter(function(){return $(this).val() != '';}).remove().end();
		
		if($('#assessmentReportPrograms').val()!='')
			assessmentProgramId = $('#assessmentReportPrograms').val();
		var contentAreaId = $('#coursesReport').val();
		if(contentAreaId=="")
		{
			$('#gradesReport').select2({
				placeholder:'Select',
				multiple: false
			});
		}
		else if (contentAreaId != 0) {
			$.ajax({
		        url: 'getGradesBasedOnAssessmentProgramAndCourses.htm',
		        data: {
		        	assessmentProgramId : assessmentProgramId,
		        	contentAreaId: contentAreaId
		        	},	        	
		        dataType: 'json',
		        type: "GET"
		        
			}).done(function(grades) {
	        	if(grades.length>=1)
	        		$('#gradesReport').append($('<option></option>').prop("value", -1).text('All'));
	        	if(contentAreaId!=-1){
					$.each(grades, function(i, grade) {
						$('#gradesReport').append($('<option></option>').prop("value", grade.id).text(grade.name));
					});
	        	}
				
				
				if (grades.length == 1) {
					$("#gradesReport option").removeAttr('selected').next('option').prop('selected', 'selected');
					$("#gradesReport").trigger('change');
				}
				$("#gradesReport").trigger('change.select2');
	        })
		}
	});		

}


