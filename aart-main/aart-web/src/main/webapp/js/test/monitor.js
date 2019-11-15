	var performanceStudentsTestIds = [];
	var isRefresh = false;

	var summativeAssessmentProgramNames = ['KAP', 'KELPA2', 'CPASS'];
	
	function loadMonitorTestSessionData() {
		if(($("#arts_monitorTestSession").getGridParam('reccount') == undefined || 
				$("#arts_monitorTestSession").getGridParam('reccount') < 1) || isRefresh) {
			
			$('#arts_monitor_reactivate').hide();
			$('#arts_monitor_endTestSession').hide();
			$('#arts_performanceMonitorButtonDiv').show();
			
			var urlValue = 'monitoring.htm';
	
			 $.ajax({
				url: urlValue,
				data: {
					rosterId: rosterId,
					testSessionId: testSessionId
				},
				dataType: 'json',
				type: "POST"
			 }).done(function(data) {
				buildMonitoringGrid(data);
			 }).always(function(){
		        if (!hasReactivatePermission) {
					$('#arts_monitor_reactivate').hide();
				}
				if (hasEndPermission) {
		        	$('#arts_monitor_endTestSession').attr("disabled" , false);
		        	$('#arts_monitor_endTestSession').show();
				} else {
		    		$('#arts_monitor_endTestSession').attr("disabled" , true);
		    		$('#arts_monitor_endTestSession').hide();
				}
				var ids = $('#arts_monitorTestSession').jqGrid('getDataIDs'); 
	            for(var i=0;i<ids.length;i++)
	            {         
	                $('#jqgh_arts_monitorTestSession_'+ids[i]).attr('title', $(this).getCell(ids[i], '')+ ' Check Box');
	            }				            
				var objs= $('#gbox_arts_monitorTestSession').find('[id^=gs_]');
		        $.each(objs, function(index, value) {         
		        	var nm=$('#jqgh_arts_monitorTestSession_'+$(value).attr('name'));				         
		         	var title=$(nm).text();				         
			        if($(nm).html() == null){
			        	 $(value).attr('title',$(value).attr('name')+' filter'); 
			        	  } else {
			        	  $(value).attr('title',$(nm).text()+' filter'); 
			        }				                                       
		        });
			});
 		}
	}

	function buildMonitoringGrid(response) {

	    var colModel = new Array(); 
	    var groupingHeaders = new Array();
	    
	  	//create the grid.	        
        var grid_width = $('.kite-table').width();
	  	var column_width = "";
		if(grid_width == 100 || grid_width == 0){
			grid_width = 960;				
		}		
		var colNames = ['','Name','Status','# Unanswered Items'];
		
	    //Populate static columnmodel.
		colModel.push({name:'studentsTestId',index:'studentsTestId', label:" ", title:"Students Test Id",  width:30, align: 'center', formatter: performcheckboxFormatter, search : false, sortable : false });		
		colModel.push({name:'studentName', index:'studentName',  label:"Name", title:"Student Name", width:200, align: 'center', sortable : true});
		colModel.push({name:'overallStatus', index:'overallStatus',  label:"Status", title:"Status", width:90, align: 'center', sortable : true, stype:'select', searchoptions:{sopt:['eq'], value:':;Complete:Complete;In Progress:In Progress;In Progress Timed Out:In Progress Timed Out;Unused:Unused'} });
		colModel.push({name:'unansweredCount', index:'unansweredCount', title:"Unanswered Count", label:"# Unanswered Items",width:130, align: 'center', formatter: unansweredFormatter, sortable : true, sorttype : 'int' });
		
		//Populate dynamic columnnames and columnmodel.
		if(typeof response.sectionStatusColumnNames != 'undefined' && response.sectionStatusColumnNames !=null) {
			for(var i=0; i < response.sectionStatusColumnNames.length; i++) {	
				colModel.push({name:response.sectionStatusColumnNames[i], index:response.sectionStatusColumnNames[i], title :response.sectionStatusColumnNames[i],  label:response.sectionTaskColumnLabels[i]+"_"+response.sectionStatusColumnNames[i], width:25, align: 'center', formatter: responseCellFormatter, sortable : false});
				colNames.push(response.sectionTaskColumnLabels[i]);
			}
		}
		
		if(typeof response.groupingHeaders != 'undefined' && response.groupingHeaders !=null) {
			for(var i=0; i < response.groupingHeaders.length; i++) {
				var headerInfo = response.groupingHeaders[i].split('|');
				groupingHeaders.push({ titleText: headerInfo[0], startColumnName: headerInfo[1], numberOfColumns: headerInfo[2] });
			}
		}
	   
		$('#arts_monitorTestSession').jqGrid({
	        datatype: "local",
	        data: response.students,       
	        colModel: colModel,
	        colNames: colNames,
	        postData: {},
	        height : 'auto',
	        width: grid_width,
	        shrinkToFit: false,
	        rowNum : 10,
	        rowList : [ 10, 20, 30 ],
	        page: 1,
	    	search: false,
	    	sortable:false,
	        sortname: 'studentName',
	        sortorder: 'desc',
	        pager: '#arts_monitorTestSessionPager',
	        viewrecords : true,
	        viewable : false,
	        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
	        altRows : true,
	        altclass: 'altrow',
	        hoverrows : true,
	        multiselect : false,
	        gridComplete: function() {
	        	var $gridAuto = $('#arts_monitorTestSession');
		    	var ids = $gridAuto.jqGrid('getDataIDs');
		    	
		        for (var i=0;i<ids.length;i++) {
		            var id=ids[i];
		            var rowData = $gridAuto.jqGrid('getRowData',id);
		          
		            $('#'+id,$gridAuto[0]).attr('title', rowData.studentName + ' (' +
		                                            rowData.Category + ', ' +
		                                            rowData.Subcategory + ')');
		        } 
	        	var recs = parseInt($("#arts_monitorTestSession").getGridParam("records"));				    
				if (isNaN(recs) || recs == 0) {
				     //Set min height of 1px on no records found
				     $('.jqgfirstrow').css("height", "1px");
				 }
			
	        },
	        loadComplete: function () {
	            var $footerRow = $(this.grid.sDiv).find("tr.footrow"),
	            	$newFooterRow = $(this.grid.sDiv).find("tr.footrow");
	            if ($newFooterRow.length === 1) {
		            $newFooterRow = $footerRow.clone();		            
		            $newFooterRow.insertAfter($footerRow);
	            }
	            
	          	//retirieve any previously stored rows for this page and re-select them	        		        	
	        	if (performanceStudentsTestIds) {
	        		$.each(performanceStudentsTestIds, function (index, value) {
	        			$("#"+value).prop('checked', true);
	        		});
	        	} 	
	        },
	        beforeSelectRow: function(id, e) {
	        	var enableReactivation = false;
	    		
	    		//Get the studentsTestIds array based on checkbox selection.
	    		if(hasReactivatePermission) {
		        	$('input:checkbox.performMonitorCheckbox').each(function() {
		        		
		    			if(this.checked) {
		    				enableReactivation = true;
		    			}	    			
		    	    });
	    		}	        	
	        	
	        	//enable/disable button based on checkbox selection.
	        	if(enableReactivation) {	        		
	        		$('#arts_monitor_reactivate').attr("disabled" , false);
	        		$('#arts_monitor_reactivate').show();
	        	} else {
	        		$('#arts_monitor_reactivate').attr("disabled" , true);
	        		$('#arts_monitor_reactivate').hide();
	        	}
	        	
	        	//Get the studentsTestIds array based on checkbox selection/de-selection.
	    	    if(e.target.checked) {
	    	    	performanceStudentsTestIds.push($(e.target).attr("studentsTestId"));
	    	    } else {
	    	    	performanceStudentsTestIds = $.grep(performanceStudentsTestIds,function(n){ return(n != $(e.target).attr("studentsTestId")) });
	    	    }
	    	    //Highlights row selection
	    	    return true;
	        }

	    });
	
		$('#arts_monitorTestSession').jqGrid('setGroupHeaders', {
			  useColSpanStyle: true, 
			  groupHeaders: groupingHeaders
			});
		$("#arts_monitorTestSession").jqGrid('filterToolbar', { stringResult: true, searchOnEnter: false, defaultSearch: "cn" });
	    $('#arts_monitorTestSession').jqGrid('navGrid', '#arts_monitorTestSessionPager', {edit: false, add: false, del: false, refresh:false, search:false});
	    $('#arts_monitorTestSession').triggerHandler("jqGridAfterGridComplete");
	    $('#arts_monitorTestSessionbuttonColumnChooser').hide();
	    $('#refresh_arts_monitorTestSession').hide();
	    $('#search_arts_monitorTestSession').hide();
	}
	
	function responseCellFormatter(cellvalue, options, rowObject) {
		if ((typeof rowObject.studentTasks != 'undefined' && rowObject.studentTasks !=null)){
			if(cellvalue=='T') {
				return '<img src="images/icons/monitor-answered.png" title="'+rowObject.studentName+' - Answered"/>';
			} else if(cellvalue=='F') {
				return '<img src="images/icons/monitor-unanswered.png" title="'+rowObject.studentName+' - Unanswered"/>';
			} else if(cellvalue=='NA') {
				return '<span title="'+rowObject.studentName+' - Not Available">**</span>';				
			} else if(cellvalue==null || cellvalue=='-' || cellvalue.indexOf('/')>=0|| cellvalue.indexOf('%')>=0) {
				return '-';
			} else {
				return '-';
			}
		}else{
			return '-';
		}
	}

	function performcheckboxFormatter(cellvalue, options, rowObject) {		
		var htmlString = "";  		
		htmlString = " <input type='checkbox' class='performMonitorCheckbox' teststatus='" + rowObject.overallStatus + "'inProgressTimedOut='" + rowObject.inProgressTimedOut + "'id = '" + rowObject.studentsTestId + "' studentsTestId='" + rowObject.studentsTestId + "' testSessionId='" + testSessionId + "' title ='" + rowObject.studentName +"' />";
  		    
	    return htmlString;
	}
	
	function unansweredFormatter(cellvalue, options, rowObject){
		if (rowObject.overallStatus !== 'Complete' & rowObject.overallStatus !== 'In Progress Timed Out'){
			return 'N/A';
		} else {
			return cellvalue;
		}
	}

	function reactivateTestSections(confirmReactivate){
		var isAdaptiveTest = $('#testSessionType').val() === 'ADAPTIVE';
		if(performanceStudentsTestIds.length > 0){
			$.ajaxSetup({ cache: false });
			$.ajax({	
				url: "reactivateStudentsSection.htm",
				data: {										
					studentsTestIds: performanceStudentsTestIds,
					testSessionId: testSessionId,
					confirmReactivate : confirmReactivate
				},
				datatype: 'json',
				type: "POST"
			}).done(function(data) {
				$('body, html').animate({scrollTop:0}, 'slow');
				if(data.status == true) {
					// print success message						
					$('#arts_monitor_reactivation_success').show();
					setTimeout("aart.clearMessages()", 3000);
					performanceStudentsTestIds = [];
					if(confirmReactivate){							
						$('#arts_monitorTestSession').jqGrid('clearGridData');
						$('#arts_monitorTestSession').jqGrid("GridUnload");
						loadMonitorTestSessionData();
						$('#arts_monitorTestSession').trigger("reloadGrid");
					}
				} else {
					if (data.permission == false){
						$('#arts_monitor_reactivation_permission_denied').show();
						setTimeout("aart.clearMessages()", 3000);
					} else if (data.ineligibleStudents.length > 0){
						var dialogTitle = "Test Reactivation Validation Error";
						for(var index=0; index < data.ineligibleStudents.length; index++){
							var ineligibleStudent = data.ineligibleStudents[index];
							$('#nonEligibleStudentList').append($("<div class='studentList'>").html(ineligibleStudent.legalFirstName + " " +ineligibleStudent.legalLastName));
						}
						$( "#reactivateValidationDialog" ).dialog({
					        resizable: false,
					        modal: true,
					        width: 500,
					        title: dialogTitle,
					        buttons: {
					          "OK": function() {
					            $( this ).dialog( "close" );
					            $('#nonEligibleStudentList').text('');
					            if(performanceStudentsTestIds.length === data.ineligibleStudents.length){
					            	// do  nothing
					            } else {
					            	reactivateTestSections(true);
					            }
					          },
					          Cancel: function() {
					            $( this ).dialog( "close" );
					            $('#nonEligibleStudentList').text('');
								isRefresh = true;
					          }
					        }
				      	});
					} else {
						reactivateTestSections(true);
					}
				}
			}).fail(function(data) {
				// print error message
				$('body, html').animate({scrollTop:0}, 'slow');
                $('#arts_monitor_reactivation_error').show();
			});
		} else {
			// do nothing
		}
		setTimeout("aart.clearMessages()", 3000);
	}

	$('#arts_monitor_progressRefresh').on("click",function() {
		isRefresh = true;		
		$('#arts_monitorTestSession').jqGrid('clearGridData');
		$('#arts_monitorTestSession').jqGrid("GridUnload");
		loadMonitorTestSessionData();
		$('#arts_monitorTestSession').trigger("reloadGrid");
	});

	$('#arts_monitor_reactivate').on("click",function() {
		var isAdaptiveTest = $('#testSessionType').val() === 'ADAPTIVE';
		var canReactivate = true;
       	$('#arts_monitorTestSession').find('input:checkbox.performMonitorCheckbox:checked').each(function() {
			if(isAdaptiveTest){
				canReactivate = $.inArray($(this).attr('teststatus'), ['In Progress Timed Out', 'Complete']) > -1;
			} else {
				canReactivate = $.inArray($(this).attr('teststatus'), ['In Progress Timed Out', 'Complete']) > -1;
			}
   	    });

		if(!canReactivate) {
				alert('Only InProgress-TimedOut and Complete Student Test Sessions can be reactivated');
		} else {
			var confirmMessage = "Are you sure you want to reactivate the ticket for the session/section timed out?";
			if(isAdaptiveTest){
				var stageName = $('#currentStageName').val();
				var nextStageName = $('#nextStageName').val();
				confirmMessage = "You are about to reactivate " + stageName +".";
				if(nextStageName){
					confirmMessage = confirmMessage + " Any answers provided by the student(s) for " + nextStageName +" will be deleted if you elect to proceed.";
				}
			} else {
				confirmMessage = "The selected test stage will be reopened for the selected students.";
			}
			$('#reactivateConfirmaionMessage').text(confirmMessage);
			$( "#reactivateConfirmationDialog" ).dialog({
		        resizable: false,
		        modal: true,
		        width: 500,
		        title: "Confirm Test Reactivation",
		        buttons: {
		          "OK": function() {
		            $( this ).dialog( "close" );
		            if(performanceStudentsTestIds == undefined || performanceStudentsTestIds == null || performanceStudentsTestIds.length <= 0) {
						$('body, html').animate({scrollTop:0}, 'slow');
				        $('#arts_monitor_no_reactivate_params').show();	
				        setTimeout("aart.clearMessages()", 3000);
					} else {
						reactivateTestSections(false);
					}
		          },
		          Cancel: function() {
		            $( this ).dialog( "close" );
		            $('nonEligibleStudentList').text('');
		          }
		        }
		    });
		}
	});
	
	$('#arts_monitor_endTestSession').on("click",function() {
		var canComplete = true;
       	$('#arts_monitorTestSession').find('input:checkbox.performMonitorCheckbox').each(function() {
   			if(this.checked) {
				if($(this).attr('teststatus') == 'Complete' || $(this).attr('teststatus') == 'Pending' || $(this).attr('teststatus') == 'Processing LCS Responses') {
					canComplete = false;
				}
   			}
   	    });
       	       	
		if(!canComplete) {
			alert('Only Unused or In Progress Student Test Sessions can be ended');
		} else {
			if(performanceStudentsTestIds == undefined || performanceStudentsTestIds == null || performanceStudentsTestIds.length <= 0) {
				$('body, html').animate({scrollTop:0}, 'slow');		   	
			    $('#arts_monitor_no_endtestsession_params').show();
			    setTimeout("aart.clearMessages()", 3000);
			} else {
				$.ajax({	
					url: "endStudentsTest.htm",
					data: {					
						studentIds: performanceStudentsTestIds,
						testSessionId:  testSessionId
					},
					datatype: 'json',
					type: "POST",
					title:'&nbsp;'
				}).done(function(data){
						performanceStudentsTestIds = [];
						$('body, html').animate({scrollTop:0}, 'slow');
						if (data == true) {
						// print a success message
							$('#arts_monitor_end_test_session_success').show();
							setTimeout("aart.clearMessages()", 3000);
							//isRefresh = true;							
							$('#arts_monitorTestSession').jqGrid('clearGridData');
							$('#arts_monitorTestSession').jqGrid("GridUnload");
							loadMonitorTestSessionData();
							$('#arts_monitorTestSession').trigger("reloadGrid");
						}else{
							$('#arts_monitor_end_test_session_permission_denied').show();
							setTimeout("aart.clearMessages()", 3000);
						}
				}).fail(function(data) {
						$('body, html').animate({scrollTop:0}, 'slow');
		                $('#arts_monitor_end_test_session_error').show();
		                setTimeout("aart.clearMessages()", 3000);
				}); 
			}
		}
	});