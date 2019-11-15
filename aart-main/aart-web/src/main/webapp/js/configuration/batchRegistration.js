function batchRegistrationInit(){
	$('#batchRegForm')[0].reset();
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
	$('#progress').html("");
	$('#batchRegErrors').html("");
	$('#batchReportMessage').html("");
	$('.hidden', $('#batchRegContainer')).hide();
	$('#grades, #courses, #testTypes, #assessments, #testingPrograms, #assessmentPrograms, #testEnrollmentMethod, #testWindow').select2({
		placeholder:'Select',
		multiple: false,
		allowClear : true
	});
	
	$('#confirmDLMMultiAssign').dialog({
		resizable: false,
		height: 250,
		width: 500,
		modal: true,
		autoOpen:false,			
		title: "Confirm",
		buttons: {
		    No: function() {
		    	 $(this).dialog("close");
		    },
		    Yes: function() {
	    		 $(this).dialog("close");
	    		 registerProcess();
		    }
		}
		
	});
	
	$('#errorDetailsDailog').dialog({
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
	
	$('#batchRegForm').validate({
		ignore: "",
		rules: {
			testingPrograms: {required: {
				depends: function(element){
		            return ($( "#assessmentPrograms option:selected" ).data('pcode') != 'DLM' && $( "#assessmentPrograms option:selected" ).data('pcode') != 'PLTW' && $( "#assessmentPrograms option:selected" ).data('pcode') != 'I-SMART');
		        }
			}},
			dlmFixedGrades:{required: {
				depends: function(element) {
					return ($( "#assessmentPrograms option:selected" ).data('pcode') == 'DLM' && $( "#testEnrollmentMethod option:selected" ).data('mcode') == 'FXD');
				}
			}},
			testEnrollmentMethod: {required: true},
			assessments: {required: false},
			testTypes: {required: false},
			courses: {required: false},
			grades: {required: false},
			testWindow: {required: true}
		}
	});
	
	$("#fromDate, #toDate").datepicker();
	$('#fromDate').datepicker("setDate", "-7");
	$('#toDate').datepicker("setDate", "+0");
	$('#showHistory').off('click').on("click",function(event) {
	    event.preventDefault();
	    $("#gbox_historyGridTableId").show();
	    $(this).text($(this).text() == 'Show Submission History' ? 'Hide Submission History' : 'Show Submission History'); 
		$("#historydetails").toggle();
	});
	$('#historyBtn').off('click').on('click', function(event) {
		$("#historyDateErrors").hide();
			$('#historyGridTableId').jqGrid('clearGridData');
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		var fromMatches = /^(\d{2})[-\/](\d{2})[-\/](\d{4})$/.exec(fromDate);
		var toMatches = /^(\d{2})[-\/](\d{2})[-\/](\d{4})$/.exec(toDate);
		if(fromDate == null || toDate == null){
			$("#historyDateErrors").html('Please select from and to date.').show(); 
			return false;
		}
		if(fromMatches == null || toMatches == null){
			$("#historyDateErrors").html('Invalid from/to date. Please select valid date.').show(); 
			return false;
		}

        var fromDateFmt = $('#fromDate').datepicker('getDate');
		var fromStartDate = new Date(fromDateFmt.getFullYear()
                ,fromDateFmt.getMonth()
                ,fromDateFmt.getDate()
                ,0,0,0); 
		
        var toDateFmt = $('#toDate').datepicker('getDate');
		var toEndDate = new Date(toDateFmt.getFullYear()
                ,toDateFmt.getMonth()
                ,toDateFmt.getDate()
                ,23,59,59);
		
        if (fromStartDate.getTime() > toEndDate.getTime())  {
        	$("#historyDateErrors").html('Invalid date range. From date must be less than to date.').show(); 
			return false;
		}
        
		$.ajax({
	        url: 'getBatchRegistrationHistory.htm',
	        data: {
	        	fromDate: fromStartDate,
	        	toDate : toEndDate
	        	},
	        dataType: 'json',
	        type: "GET"
	       
		}).done(function(historyData) {	        	
	    		$('#historyGridTableId').setGridParam({'data': historyData}).trigger('reloadGrid');
	        })
			
	});
	
	$('#registerBtn').off('click').on('click', function() {
		$('#batchRegErrors').html("");
		$('#batchReportMessage').html("").hide();
		var batchReportMessage = "";
		var pcode = $( "#assessmentPrograms option:selected" ).data('pcode');
		var mcode = $( "#testEnrollmentMethod option:selected" ).data('mcode');
		var validationFlag = false;
		var assessmentProgramId = $('#assessmentPrograms').val();
		if(assessmentProgramId.length == 0){
    		$('#batchRegErrors').html("Please select Assessment Program.").show();
		}else{
			validationFlag = $('#batchRegForm').valid();
			if (validationFlag) {
				if(pcode == 'DLM' && mcode == 'MLTASGN'){
					$('#confirmDLMMultiAssign').html("You are submitting the batch processing for students to receive " +
							"the following quantities of assessments: <br/><br/>" + dlmMultiAssignConfig + 
							"<br/><br/>Do you want to proceed?");
					$('#confirmDLMMultiAssign').dialog("open");
				}else{
					registerProcess();
				}
			} 
		}
	});
	
	var gridWidthForVO = $('#batchRegContainer').width();		
	if(gridWidthForVO < 1050) {
		gridWidthForVO = 1050;				
	}
	$('#historyGridTableId').scb({
        datatype: "local",
        data: [],
        loadonce: true,
		//filterToolbar: false,
		viewable: false,
        colNames: [
                   'Id',
				   'Submission Date',	                       
				   'Status',
                   'Submission Report',					   
                   'Assessment Program',
                   'Testing Program',
                   'Assessment',
                   'Test Type',
                   'Course/Subject',
                   'Grade/Pathway',
                   'Test Window',
                   'Enrollment Method'
                  ],
        colModel: [
				   {name: 'id', index: 'id', hidden:true},
   				   {name: 'submissionDate', search : true, index: 'submissionDate', hidedlg: true, 
					   formatter: batchRegistrationDateFormatter},
					{name: 'status', index: 'status', hidedlg: true, search : true},
                   {name: 'report', index: 'report', hidedlg: true, search : false, 
   					formatter:function(cellValue, options, cell){
   					 	if(cell.failedCount > 0){
   					    	return "<a href='javascript:errorDetails(" + cell.id + ")' class='errorDetails'>Success:"  + cell.successCount + ", Failed:" + cell.failedCount + "</a>";
   					    }else{
   					    	return "Success:" + cell.successCount + ", Failed: 0";
   					    }
          		 	}
                   },
                   {name: 'assessmentProgramName', index: 'assessmentProgramName', hidedlg: true},
   				   {name: 'testingProgramName', index: 'testingProgramName', hidedlg: true},	                       
                   {name: 'assessmentName', index: 'assessmentName', hidedlg: true},
                   {name: 'testTypeName', index: 'testTypeName', label: 'testTypeNameBatchRegistration', hidedlg: true},
                   {name: 'contentAreaName', index: 'contentAreaName', hidedlg: true},
                   {name: 'gradeName', index: 'grade', hidedlg: true},
                   {name: 'testWindowName', index: 'testWindowName', hidedlg: true},
                   {name: 'enrollmentMethodName', index: 'enrollmentMethodName', hidedlg: true}
                   ],
        height : 'auto',
        shrinkToFit: true,
        pager : '#historyGridPager',
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : '',
        sortorder : 'asc',
        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
        altRows: true,
        altclass: 'altrow',		        
        hoverrows : false,
        multiselect : false,
        caption: '',
        beforeSelectRow: function(rowid, e) {	            
        	 
        },
        loadComplete: function(){
      	   	 var tableid=$(this).attr('id');  
       	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');         
         $.each(objs, function(index, value) {         
          var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                $(value).attr('title',$(nm).text()+' filter');
                $(value).css({"width": "100%"});
                });
      	},
        gridComplete: function() {     	
        $("#gbox_historyGridTableId").show();
        },
        width:gridWidthForVO-20
    }); 
	 $('#historyGridTableId').jqGrid('filterToolbar',
             { stringResult: true, searchOnEnter: false, defaultSearch: "cn" });
	
	$('#errorDetailsGridTableId').scb({
        datatype: "local",
        data: [],
        loadonce: true,
		filterToolbar: false,
		viewable: false,
        colNames: [
				   'Student ID',	                       
				   'First Name',
                   'Last Name',					   
                   'Reason',
                  ],
        colModel: [
				   {name: 'studentId', height:20, width:100},
   				   {name: 'firstName', width:100},
                   {name: 'lastName', width:100},
   				   {name: 'reason', width:700},	                       
                   ],
        height : 'auto',
        width : 1100,
        shrinkToFit: true,
        pager : '#errorDetailsGridPager',
        rowNum : 10,
        rowList : [ 10, 20, 30 ],
        sortname : '',
        sortorder : 'asc',
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
    }); 	 
};


function batchRegistrationDateFormatter(cellValue, options, rowObject){
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

function registerProcess(){
	var assessmentProgramId = $('#assessmentPrograms').val();
	var testingProgramId = $('#testingPrograms').val();
	var assessmentId = $('#assessments').val();
	var testTypeId = $('#testTypes').val();
	var subjectAreaId = $('#courses').val();
	var gradeCourseId = $('#grades').val();
	var dlmFixedGradeAbbrName = null;
	var testEnrollmentMethod = $('#testEnrollmentMethod').val();
	var testEnrollmentMetodCode = $( "#testEnrollmentMethod option:selected" ).data('mcode');
	if(testEnrollmentMetodCode == 'FXD') {
		dlmFixedGradeAbbrName = $('#dlmFixedGrades').val();
	}
	var testWindowId = $('#testWindow').val();
	$('#registerBtn').hide();
	$('#progress').html("Batch Registration Process - IN-PROCESS.<br/>");
	$.ajax({
        url: 'checkMathAndElaLimits.htm',
        data: {
        	},
        dataType: 'json',
        type: "GET"
        
	}).done(function(results) {
    	if(results){
    		//valid
			$.ajax({
		        url: 'checkPoolTypes.htm',
		        data: {
		        	},
		        dataType: 'json',
		        type: "GET"
			}).done(function(list) {
	        	if(list.length == 0){
	        		//valid
	    			$.ajax({
	    		        url: 'processBatchRegistration.htm',
	    		        data: {
	    		        	assessmentProgramId: assessmentProgramId,
	    		        	testTypeId : testTypeId,
	    		        	subjectAreaId : subjectAreaId,
	    		        	assessmentId : assessmentId,
	    		        	testingProgramId : testingProgramId,
	    		        	gradeCourseId : gradeCourseId,
	    		        	dlmFixedGradeAbbrName : dlmFixedGradeAbbrName,
	    		        	testEnrollmentMethodId:testEnrollmentMethod,
	    		        	testWindowId: testWindowId
	    		        	},
	    		        dataType: 'json',
	    		        type: "POST"
	    			}).done(function(results) {
    		        	 if (results == null){
	    		        		batchReportMessage += "No enrollment records were found for the selected criteria.";
	    		        	}else{
	    		        		batchReportMessage += "Batch records successful:" + results.success +
	    						"  failed:" + results.failed +" ";
	    		        	} 
	    		        	$('#progress').html(results.status);
	    		        	$('#progress').data("jobId", results.jobId);
	    		        	window.setTimeout("monitorBatchRegStatus()", 10000);
	    		        })
	        		
	        	}else if (list.length == 19){
	        		//not valid
	        		$('#progress').html("Batch Registration Process - ERROR.<br/>");
	        		$('#batchRegErrors').html("Please enter the States Solution parameters.").show();
	        		$('#registerBtn').show();
	        	}else if (list.length < 19){
	        		//not valid
	        		$('#progress').html("Batch Registration Process - ERROR.<br/>");
	        		//append list of states
	        		var message = "Please enter the States Solution parameters for state(s): ";
	        		var joinedList = list.join(', ');
	        		$('#batchRegErrors').html(message+joinedList).show();
	        		$('#registerBtn').show();
	        	}
	        })
    		
    	}else{
    		//not valid
    		$('#progress').html("Batch Registration Process - ERROR.<br/>");
    		$('#batchRegErrors').html("Please enter Math and ELA test session parameters.").show();
    		$('#registerBtn').show();
    	}
    })
}

function monitorBatchRegStatus(){
	var jobId = $('#progress').data("jobId");
    $.ajax({
        url: 'monitorBatchRegistration.htm',
        data: {"jobId": jobId},
        type: "GET"
	}) .done( function(data) {
		if(data.status === 'COMPLETED' || !(data.status === 'STARTING' || data.status === 'STARTED' || data.status === 'IN-PROGRESS')){		
        	$('#progress').html(data.status);
        	$('#progress').removeData("jobId");
        	$('#registerBtn').show();
		} else {
			window.setTimeout("monitorBatchRegStatus()", 10000);
		}  
    })
}

function errorDetails(id){
		$('#errorDetailsGridTableId').jqGrid('clearGridData');
	$.ajax({
		url: 'getBatchRegistrationErrorDetails.htm',
		data: {
	      	batchid: id
		},
		dataType: 'json',
		type: "GET"
		
	}).done( function(errorDetailsData) {				
	     $('#errorDetailsGridTableId').setGridParam({'data': errorDetailsData}).trigger('reloadGrid');
		 $('#errorDetailsDailog').dialog('open');
	})
}

function resetBatchRegistration(){
	$('#assessmentPrograms').val('').select2();
	$('#grades').val('').select2();
	$('#courses').val('').select2();
	$('#testTypes').val('').select2();
	$('#assessments').val('').select2();
	$('#testingPrograms').val('').select2();
	$('#testWindow').val('').select2();
	$('#testEnrollmentMethod').val('').select2();
	$('#dlmFixedGrades').val('').select2();
	$("#testWindowDates").html("");
	$('#progress').html("");
	$('#batchRegErrors').html("");
	$('#batchReportMessage').html("");
	
	$('#grades, #courses, #testTypes, #assessments, #testingPrograms, #testEnrollmentMethod,#dlmFixedGrades, #testWindow').find('option').filter(function(){
		return $(this).val() > 0;
	}).remove().end();
	$('.hidden', $('#batchRegContainer')).hide();
}

function populateAssessmentPrograms() {
	var me = this;
	var apSelect = $('#assessmentPrograms'), optionText='';
	$('.messages').html('').hide();
	$(".fixed, .dlmFixedGrades").hide();
	apSelect.find('option').filter(function(){
		return $(this).val() > 0;
	}).remove().end();
	
	$.ajax({
		url: 'getAssessmentProgramsForAutoRegistration.htm',
		dataType: 'json',
		type: "GET"
		
	}).done(function(assessmentPrograms) {				
		if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
			$.each(assessmentPrograms, function(i, assessmentProgram) {
				optionText = assessmentPrograms[i].programName;
				apSelect.append($('<option data-pcode="'+assessmentPrograms[i].abbreviatedname+'"></option>').val(assessmentProgram.id).html(optionText));
			});
			if (assessmentPrograms.length == 1) {
				apSelect.find('option:first').removeAttr('selected').next('option').prop('selected', true);
				apSelect.prop('disabled', true);
				$("#assessmentPrograms").trigger('change');
			}
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#batchRegErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
		}
		$('#grades, #courses, #testTypes, #assessments, #testingPrograms, #assessmentPrograms, #testEnrollmentMethod, #testWindow').trigger('change.select2');
	})
	
	$('#assessmentPrograms').on("change",function() {
    	$('#progress').html('');
 	   	$('#batchRegForm')[0].reset();
 		$("label.error").html('');
		$('.hidden', $('#batchRegContainer')).hide();
		$(".fixed, .dlmFixedGrades").hide();
		if($('#showHistory').text() == 'Hide Submission History') 
			$('#showHistory').text('Show Submission History').show(); 
		
		$('#batchReportMessage').html("").hide();
		$('#testingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#assessments').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#testTypes').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#testWindow').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#testEnrollmentMethod').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		
		
		$("#testWindowDates").html("");
		var assessmentProgramId = $('#assessmentPrograms').val();
		var pcode = $( "#assessmentPrograms option:selected" ).data('pcode');
		if (assessmentProgramId != 0 && pcode != 'DLM' && pcode != 'PLTW' && pcode != 'I-SMART') {
			if (pcode == 'CPASS'){
				$('#labelCourse').text('Subject:');
				$('#labelGrade').text('Pathway:');
			} else {
				$('#labelCourse').text('Course:');
				$('#labelGrade').text('Grade:');
			}
			$('.hidden', $('#batchRegContainer')).show();
			$(".fixed, .dlmFixedGrades").hide();
			if($('#showHistory').text() == 'Show Submission History') 
				$('#showHistory').text('Hide Submission History').show(); 
			
			//$(".bProcess").hide();
			$.ajax({
		        url: 'getTestingProgramsForAutoRegistration.htm',
		        data: {
		        	assessmentProgramId: assessmentProgramId
		        	},
		        dataType: 'json',
		        type: "GET"
			}).done(function(testingPrograms) {
				$.each(testingPrograms, function(i, testingProgram) {
					$('#testingPrograms').append($('<option></option>').prop("value", testingProgram.id).text(testingProgram.programName));
				});
				
				if (testingPrograms.length == 1) {
					$("#testingPrograms option").removeAttr('selected').next('option').prop('selected', true);
					$("#testingPrograms").trigger('change');
				}
				$('#grades, #courses, #testTypes, #assessments, #testingPrograms, #testEnrollmentMethod, #testWindow').trigger('change.select2');
	        })
		}else if(assessmentProgramId != 0 && (pcode == 'DLM' || pcode == 'PLTW' || pcode == 'I-SMART')) {
			$(".bProcess, .testWindowDiv").show();
		} 

		if(pcode == 'CPASS') {
			$('#labelCourse', $('#batchRegContainer')).text('Subject:');
			$('#labelGrade', $('#batchRegContainer')).text('Pathway:');
		} else {
			$('#labelCourse', $('#batchRegContainer')).text("Course:");
			$('#labelGrade', $('#batchRegContainer')).text("Grade:");
		}
		
		$.ajax({
	        url: 'getTestEnrollmentMethods.htm',
	        data: { assessmentProgramId: assessmentProgramId },
	        dataType: 'json',
	        type: "GET"
		}).done(function(windowMethods) {
			$.each(windowMethods, function(i, windowMethod) {
				$('#testEnrollmentMethod').append($('<option data-mcode="'+windowMethod.methodCode+'"></option>').val(windowMethod.id).html(windowMethod.methodName));
			});
			
			if (windowMethods.length == 1) {
				$("#testEnrollmentMethod option").removeAttr('selected').next('option').prop('selected', true);
				$("#testEnrollmentMethod").trigger('change');
			}
			
			$('#testEnrollmentMethod, #testWindow').trigger('change.select2');
			$(".fixed, .dlmFixedGrades").hide();
        })
	});

	$('#testingPrograms').on("change",function() {
		$('#batchReportMessage').html("").hide();
		$('#assessments').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#testTypes').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		var testingProgramId = $('#testingPrograms').val();
		var assessmentProgramId = $('#assessmentPrograms').val();
		if (testingProgramId != 0  && assessmentProgramId != 0) {
			$.ajax({
		        url: 'getAssessmentsForAutoRegistration.htm',
		        data: {
		        	testingProgramId: testingProgramId,
		        	assessmentProgramId: assessmentProgramId
		        	},
		        dataType: 'json',
		        type: "GET"
			}).done(function(assessments) {
				$.each(assessments, function(i, assessment) {
					$('#assessments').append($('<option></option>').prop("value", assessment.id).text(assessment.assessmentName));
				});
				
				if (assessments.length == 1) {
					$("#assessments option").removeAttr('selected').next('option').prop('selected', true);
					$("#assessments").trigger('change');
				}
				$('#grades, #courses, #testTypes, #assessments').trigger('change.select2');
	        })
		}
	});

	$('#assessments').on("change",function() {
		$('#batchReportMessage').html("").hide();
		$('#testTypes').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		var assessmentId = $('#assessments').val();
		if (assessmentId != 0) {
			$.ajax({
		        url: 'getTestTypeByAssessmentId.htm',
		        data: {
		        	assessmentId: assessmentId
		        	},
		        dataType: 'json',
		        type: "GET"
		        
			}).done( function(testTypes) {
				$.each(testTypes, function(i, testType) {
					$('#testTypes').append($('<option></option>').prop("value", testType.id).text(testType.testTypeName));
				});
				
				if (testTypes.length == 1) {
					$("#testTypes option").removeAttr('selected').next('option').prop('selected', true);
					$("#testTypes").trigger('change');
				}
				$('#grades, #courses, #testTypes').trigger('change.select2');
	        })
		}
	});
	
	$('#testTypes').on("change",function() {
		$('#batchReportMessage').html("").hide();
		$('#courses').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		var testTypeId = $('#testTypes').val();
		var assessmentId = $('#assessments').val();
		if (testTypeId != 0) {
			$.ajax({
		        url: 'getContentAreaByTestType.htm',
		        data: {
		        	testTypeId: testTypeId,
		        	assessmentId: assessmentId
		        	},
		        dataType: 'json',
		        type: "GET"
			}).done (function(contentAreas) {
				$.each(contentAreas, function(i, contentArea) {
					$('#courses').append($('<option></option>').prop("value", contentArea.id).text(contentArea.name));
				});
				
				if (contentAreas.length == 1) {
					$("#courses option").removeAttr('selected').next('option').prop('selected', true);
					$("#courses").trigger('change');
				}
				$('#grades, #courses').trigger('change.select2');
	        })
		}
	});			

	$('#courses').on("change",function() {
		$('#batchReportMessage').html("").hide();
		$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		var subjectAreaId = $('#courses').val();
		var testTypeId = $('#testTypes').val();
		var assessmentId = $('#assessments').val();
		if (subjectAreaId != 0) {
			$.ajax({
		        url: 'getGradeCoursesByTestTypeAndContentArea.htm',
		        data: {
		        	contentAreaId: subjectAreaId,
		        	testTypeId : testTypeId,
		        	assessmentId : assessmentId
		        	},	        	
		        dataType: 'json',
		        type: "GET"
		        
			}).done(function(gradeCourses) {
				$.each(gradeCourses, function(i, gradeCourse) {
					$('#grades').append($('<option></option>').prop("value", gradeCourse.id).text(gradeCourse.name));
				});
				
				if (gradeCourses.length == 1) {
					$("#grades option").removeAttr('selected').next('option').prop('selected', true);
					$("#grades").trigger('change');
				}
				$("#grades").trigger('change.select2');
	        })
		}
	});	
	
	$('#testEnrollmentMethod').on("change",function() {
		$("#testWindowDates").html('');
		$(".fixed, .dlmFixedGrades").hide();
		$('#testWindow').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		var testEnrollmentMetodCode = $( "#testEnrollmentMethod option:selected" ).data('mcode');
		if(testEnrollmentMetodCode == 'FXD') {
			$().show();			
			$(".fixed, .dlmFixedGrades").show();
			$.ajax({
				url: 'getGradesByAssessmentProgramId.htm',
				data: {assessmentProgramId: $('#assessmentPrograms').val()},
				dataType: 'json',
				type: 'GET'
			}).done(function(grades) {
				$('#dlmFixedGrades').empty();
				$.each(grades, function(i, grade) {
					$('#dlmFixedGrades').append($('<option></option>').prop("value", grade.abbreviatedName).text(grade.name));
				});	
				if (grades.length == 1) {
					$("#dlmFixedGrades option").removeAttr('selected').next('option').prop('selected', true);
					$("#dlmFixedGrades").trigger('change');
				}
				$("#dlmFixedGrades").trigger('change.select2');
			})
		} else {			
			$('#dlmFixedGrades').select2({
				placeholder:'Select',
				multiple: false,			
			});
			$(".fixed, .dlmFixedGrades").hide();
		}		
		$.ajax({
	        url: 'getTestWindows.htm',
	        data: { autoEnrollmentMethodId: $('#testEnrollmentMethod').val(),
	        		assessmentProgramId: $('#assessmentPrograms').val() },
	        dataType: 'json',
	        type: "GET"
		}).done(function(testWindows) {
			$.each(testWindows, function(i, testWindow) {
				$('#testWindow').append($('<option data-effectivedate="'+testWindow.effectiveDate+'" data-expirydate="'+testWindow.expiryDate+'"></option>').val(testWindow.id).html(testWindow.windowName));
			});
			
			if (testWindows.length == 1) {
				$("#testWindow option").removeAttr('selected').next('option').prop('selected', true);
				$("#testWindow").trigger('change');
			}
			
			$('#testWindow').trigger('change.select2');
        })
	});
	
	$('#testWindow').on("change",function() {
		$("#testWindowDates").html('');
		$("label.error").html('');
		
		var ftime = $( "#testWindow option:selected" ).data('effectivedate');
		var fromDate = new Date(ftime);
		
		var exptime = $( "#testWindow option:selected" ).data('expirydate');

		if(typeof exptime != 'undefined' && exptime != null && exptime != '') {
			var expDate = new Date(exptime);
			$("#testWindowDates").html($.datepicker.formatDate( "m/d/yy",new Date(fromDate)) +' - '+ $.datepicker.formatDate( "m/d/yy",new Date(expDate)));
		} else if(typeof ftime != 'undefined' && ftime != null && ftime != '') {
			$("#testWindowDates").html($.datepicker.formatDate( "m/d/yy",new Date(fromDate)));
		}
	});
}

