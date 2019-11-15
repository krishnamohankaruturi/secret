<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<div id="autoRegisteredTestSessionsDiv" class="noBorder panel_full">

	<input id="detailViewCaption" value ="View Test Session Detail" type="text" class="hidden" />
	<input id="saveGrid" value ="${saveGrid}" type="text" class="hidden" />		
		
	<!-- In below input component attribute "class="hidden" used to fix the IE8 browser issue. -->	
	<input id="loggedInUserId" value ="${user.id}" type="text" class="hidden" />	
	<input id="refreshGrid" value ="false" type="hidden" class="hidden" />
    <input id="viewRecordGrid" value ="false" type="hidden" class="hidden" />
    <input id="columnChooserGrid2" value ="false" type="hidden" class="hidden" />	

	<div>
    	<span id="select_testsession_errors" class="info_message ui-state-error hidden"><fmt:message key='error.select.testsessions.tickets' /></span>
    	<span id="extend_test_session_window_errors" class="info_message ui-state-error hidden"><fmt:message key='error.select.testsessions.extendwindow' /></span>
    	<span id="extend_test_session_window_save_success" class="info_message ui-state-highlight successMessage hidden"></span>
    	<span id="extend_test_session_window_save_errors" class="info_message ui-state-error hidden"><fmt:message key='label.select.testsessions.extendwindow.save.error' /></span>
 	</div>
 	        	
	<div id="noAutoRegReport" class="none" style="width:100%"></div>
	<c:if test="${!user.teacher}">
		<div id="tsSearchFilterContainer">
			<form id="tsSearchFilterForm" name="tsSearchFilterForm" class="form">
					<div class="form-fields">
						<span  id="tsSelectCriteriaLabel" style="color: #94b54d;">Select the criteria, then click Search: &nbsp;&nbsp;&nbsp;&nbsp;</span>
						<br/>			
					</div>
	
					<div class="btn-bar">
						<div id="tsSearchFilterErrorsSession" class="error"></div>
						<div id="tsViewAutoRegistrationSearchFilterMessage" style="padding:20px" class="hidden"></div> 
					</div>
					
				    <div class="form-fields">
						<label for="tsAssessmentPrograms" class="field-label">Assessment Program:<span class="lbl-required">*</span></label>			
						<select id="tsAssessmentPrograms" title="Assessment Program" class="bcg_select" name="tsAssessmentPrograms">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields">
						<label for="tsTestingPrograms" class="field-label">Testing Program:<span class="lbl-required">*</span></label>			
						<select id="tsTestingPrograms" title="Testing Program" class="bcg_select" name="tsTestingPrograms">
							<option value="">Select</option>
						</select>
					</div>					
					<c:if test="${user.accessLevel < 50 }">
					    <div class="form-fields">
							<label for="tsDistrictOrgs" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
							<select id="tsDistrictOrgs" title="District" class="bcg_select" name="tsDistrictOrgs">
								<option value="">Select</option>
							</select>
						</div>
					</c:if>
					<div class="form-fields">
						<label for="tsSchoolOrgs" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
						<select id="tsSchoolOrgs" title="School" class="bcg_select" name="tsSchoolOrgs">
							<option value="">Select</option>
						</select>
					</div>					
					<div class="form-fields">
						<label for="tsContentAreas" class="field-label">${user.currentAssessmentProgramName == 'PLTW' ? 'Course:' : 'Subject:'}</label>			
						<select id="tsContentAreas" title="Subject" class="bcg_select" name="tsContentAreas">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields">
						<label for="tsGrades" class="field-label">Grade:</label>			
						<select id="tsGrades" title="Grade" class="bcg_select" name="tsGrades">
							<option value="">Select</option>
						</select>
					</div>
					<div id="tsSearchDiv">
						<div class="form-fields" style="float:left">
							<input type="checkbox" id="tsShowCompleted" name="showCompleted" value="Yes" title="Include completed">Include completed 
							<input type="checkbox" id="tsShowExpired" name="showExpired" value="Yes" title="Include expired">Include expired
							<button class="btn_blue btn_disabled" id="tsSearchBtn">Search</button>
						</div>
				   </div>
			</form>
		</div>
	</c:if>		
	<c:if test="${user.teacher}">
		<c:if test="${user.currentAssessmentProgramName=='PLTW'}">
		<div id="tsSearchDivForTeacher">
			<div class="form-fields" style="float:left">
				<input type="checkbox" id="tsShowCompletedForTeacher" name="showCompleted" value="Yes" title="Include completed">Include completed 
				<input type="checkbox" id="tsShowExpiredForTeacher" name="showExpired" value="Yes" title="Include expired">Include expired
				<button class="btn_blue btn_disabled" id="tsForTeacherSearchBtn">Search</button>
			</div>
	   	</div>
	 	</c:if>	
	</c:if>	
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	<p>&nbsp;</p>
	
	<div id="tsTestSessionsDiv" class="hidden">
		<div class ="table_wrap">
			<div class="kite-table">
				<table id="autoRegisteredTestSessionsTableId"  class="responsive"></table>
				<div id="pautoRegisteredTestSessionsTableId" class="responsive"></div>
			</div>
		</div>
		<c:if test="${!user.teacher}">
			<div>
				<div id="viewTSTicketsDiv" class ="viewTicketsDiv" style="text-align:right;">
					<input type="button" id="viewTSTicketsTopButton" value="<fmt:message key='label.button.viewtickets'/>" class="panel_btn nextButton hidden"/>
					<security:authorize access="hasRole('EXTEND_TEST_SESSION_WINDOW')" >
						<input type="button" id="extendTestSessionWindowButton" value="<fmt:message key='label.button.extendTestSessionWindow'/>" class="panel_btn nextButton"/>
					</security:authorize>
				</div>		
			</div>
		</c:if>
	</div>
	<br />
	
	<div id="extendTestSessionWindowDiv" class="hidden">
		<div class="_bcg">
			<div class="testsession wrap_bcg">
				<div id="extendTSInput" class="form-fields">
					<form id="extendTSInputForm" name="extendTSInputForm" class="form">
						<label for="newTSWEndDate" class="field-label isrequired">New End Date:<span class="lbl-required">*</span></label>
	           			<input id="newTSWEndDate" name="newTSWEndDate" class="input-large" placeholder="mm/dd/yyyy" title="mm/dd/yyyy"/>
	           			<br/>
						<span id="newTSWEndDate_errors1" class="info_message ui-state-error hidden">New End Date is Required</span>
						<span id="newTSWEndDate_errors2" class="info_message ui-state-error hidden">New End Date must be greater than window end date and New End Date must be greater than today.</span>
						<br/>
						<span id="newTSWEndDate_confirm_msg" class="info_message ui-state-default hidden">This will extend the testing window for the selected test sessions. Click Confirm to continue.</span>
	           		</form>
	         	</div>				
			</div>
		</div>
	</div>

</div>

<div id="autoRegisteredTSStudentsDiv">			
</div>

<script type="text/javascript">
$( document ).ready(function() {
	$('.nav-tabs a[href="#tabs_ticketing"]').tab('show');
});
//Global variable to capture the mouse position for 
//"Loading...." message display.
var mousePosition;
var flag = 1;
var autoCompleteUrl = "getARTSAutoCompleteData.htm";
var contentAreaNameValues;
var gradeCourseNameValues;
var selectedTestSessions = [];
var selectedTSAPValues = [];
var selectedTestSessionsWithTSEndDate = [];
var selectedOtwIds = [];

var kapAssessmentProgramId = -1, ampAssessmentProgramId = -1, kelpaAssessmentProgramId = -1, summativeTestingProgramId = -1,pltwAssessmentProgramId=-1,ticketsDisplay = true;

$(function() {
	$("form :input").on("change",function(){
		if($('#tsAssessmentPrograms').val()!='' && $('#tsTestingPrograms').val()!='' && $('#tsDistrictOrgs').val()!='' &&  $('#tsSchoolOrgs').val()!='')
		{
			$("#tsSearchBtn").removeClass("btn_disabled");
			$("#tsSearchBtn").prop('disabled', false);
		}
		else
		{
			$("#tsSearchBtn").addClass("btn_disabled");
			$("#tsTestSessionsDiv").addClass("hidden");
			$("#tsSearchBtn").prop('disabled', true);
		}
	});

	
	
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
	<security:authorize  access="hasRole('PERM_ROSTERRECORD_VIEW')" >
		<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')" >

		var grid_width = $('.kite-table').width();
		
		if(grid_width == 100 || grid_width == 0) {
			grid_width = 1029;				
		}
        var cell_width = grid_width/6;
		
		var $gridAuto = $("#autoRegisteredTestSessionsTableId");
		
		var cmforAutoRegistration = [
				{ name : 'testSessionId', index : 'testSessionId', width : cell_width, search : true, hidden: true, hidedlg: true },

				{ name : 'assessmentProgramName', index : 'assessmentProgramName', width : cell_width, hidden : true, hidedlg : true},
					
				{ name : 'organizationDisplayIdentifier', index : 'organizationDisplayIdentifier', width : cell_width, 
					search : true, hidden : false },
							
				{ name : 'testSessionName', index : 'testSessionName', width : cell_width + 80, formatter: editARTestSessionLinkFormatter, unformat: editARTestSessionLinkUnFormatter,
					sortable : true, search : true, hidden : false, hidedlg : true},
				
				{ name : 'printTicket', index : 'printTicket', width : cell_width - 80, formatter: printARTicketLinkFormatter, unformat: printARTicketLinkUnFormatter,
					sortable : false, search : false, hidden : 
							<c:if test="${!(user.teacher)}">false</c:if>
							<c:if test="${(user.teacher)}">true</c:if>
						, hidedlg : true },
				
				{ name : 'printTestFiles', index : 'printTestFiles', width : cell_width, formatter: printTestFormatter, 
					sortable : false, search : false, hidden : false, hidedlg : true },		   						
				{ name : 'contentAreaName', index : 'contentAreaName', width : cell_width, sortable : true},
			    { name : 'gradeCourseName', index : 'gradeCourseName', width : cell_width, sortable : true},
			    { name : 'expiredFlag', index : 'expiredFlag', width : cell_width, search : true, hidden: true, hidedlg: true }
			    ,{ name : 'effectiveDate', index : 'effectiveDate', width : cell_width, sortable : true, search : false, hidden : false }
			    ,{ name : 'expiryDate', index : 'expiryDate', width : cell_width, sortable : true, search : false, hidden : false }
			    ,{ name : 'otwId', index : 'otwId', width : cell_width, sortable : false, search : false, hidden : true,hidedlg: true }
			    ,{ name : 'testPanelId', index : 'testPanelId', width : cell_width, sortable : false, search : false, hidden : true,hidedlg: true }
			];
		
		
		//Unload the grid before each request.		
		$("#autoRegisteredTestSessionsTableId").jqGrid('clearGridData');
		$("#autoRegisteredTestSessionsTableId").jqGrid("GridUnload");					
			
		//JQGRID
		$gridAuto.scb({
			mtype: "POST",
			datatype : "local",
			width: grid_width,
			colNames : [
	   					'', 'Assessment Program Name', 'School ID', 'Test Session Name', 
	   						'Tickets',
	   					'Test Materials', $('#currentAssessmentProgram').val()=="PLTW" ? 'Course' : 'Subject', 'Grade', 'Expired Flag', 'Window Begin', 'Window End', '', 'Test Panel Id'
	   		           ],
	   		colModel :cmforAutoRegistration,	
			rowNum : 10,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			height : 'auto',
			pager : '#pautoRegisteredTestSessionsTableId',
			sortname : 'testSessionName',
			sortorder: 'asc',
			multiselect: true,
			pagestatesave:true,
			filterstatesave:true,
		    beforeRequest: function() {
				if(!isTeacher && isNotBack && $(this).getGridParam('datatype') == 'json'){
					isNotBack = true;
					if(!$('#tsSearchFilterForm').valid()) {return false;}
				}
				
		    	//Set the page param to lastpage before sending the request when 
				  //the user entered current page number is greater than lastpage number.
				var currentPage = $(this).getGridParam('page');
                var lastPage = $(this).getGridParam('lastpage');
                
                 if (lastPage!= 0 && currentPage > lastPage) {
                	 $(this).setGridParam('page', lastPage);
                	$(this).setGridParam({postData: {page : lastPage}});
                }
		    },  
		    loadComplete: function() {
		    	if($(this).getGridParam('datatype') == 'json') {
			    	//Retrieve any previously stored rows for this page and re-select them.
		        	if(selectedTestSessions) {
		        		$.each(selectedTestSessions, function (index, value) {
		        			$("#autoRegisteredTestSessionsTableId").setSelection(value, false);
		        		});
		        	}
	
				    var recs = parseInt($("#autoRegisteredTestSessionsTableId").getGridParam("records"));				    
					if (isNaN(recs) || recs == 0) {
					     //Set min height of 1px on no records found
					     $('.jqgfirstrow').css("height", "1px");
					 } else {
					     $("#gbox_autoRegisteredTestSessionsTableId").show();
					     $("#noAutoRegReport").html('');
					 }
					
					var rows = $gridAuto.jqGrid('getRowData');
					for (var x = 0; x < rows.length; x++){
						
						var testSessionId = rows[x].testSessionId;
			    		if(rows[x].expiredFlag == "Y") {
					    	$("#"+testSessionId).css("color","gray");
					    	$('a', "#"+testSessionId).css("color","gray");
			    	    }
					}
		    	}
		    	$('td[id^="view_"]').on("click",function(){
		       		  if($(".EditTable.ViewTable tbody").find('th').length==0){
		     			$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
		       		  }
		       		  });
		    },
		    beforeSelectRow: function(rowid, e) {
		        var $link = $('a', e.target);
		        if (e.target.tagName.toUpperCase() === "A" || $link.length > 0) {
		            // link exist in the item which is clicked
		            return false;
		        }
		        return true;
		    },
		    onSelectRow: function(id, status) {
        		//Add/remove items to/from selectedTestSessions array based on checkbox selection/deselection. 
        		if(status) {
					//User checks the test session.
					selectedTestSessions.push(id);
					var gridRow = $('#autoRegisteredTestSessionsTableId').jqGrid('getRowData',id);
					selectedTestSessionsWithTSEndDate.push(id + "-" + gridRow['expiryDate']);
					selectedTSAPValues.push(id + "-" + gridRow['assessmentProgramName']);
					selectedOtwIds.push(Number(gridRow['otwId']));
					
				} else {
					//User unchecks the test session.							
					selectedTestSessions = $.grep(selectedTestSessions, function(value) {
						return value != id;
					});
					
					
					var gridRow = $('#autoRegisteredTestSessionsTableId').jqGrid('getRowData',id);
					selectedTestSessionsWithTSEndDate = $.grep(selectedTestSessionsWithTSEndDate, function(value) {
						return value != id + "-" + gridRow['expiryDate'];
					});
					selectedTSAPValues = $.grep(selectedTSAPValues, function(value) {
						return value != id + "-" + gridRow['assessmentProgramName'];
					});
					selectedOtwIds = $.grep(selectedOtwIds, function(value) {
						return value != Number(gridRow['otwId']);
					});
				}

        		//Check SelectAllCheckbox header on rowNum change.
        		var recordCount = $("#autoRegisteredTestSessionsTableId").getGridParam('reccount');
        		var checkboxChecckedCount = 0;
        		$("input[type='checkbox']").each(function() {
        			if(this.name != "" && 
        					this.name.indexOf("jqg_autoRegisteredTestSessionsTableId_") != -1 && 
        					this.checked) {
        				checkboxChecckedCount++;
        			}
	    	    });
	        	if(recordCount == checkboxChecckedCount) {
	        		$("#cb_autoRegisteredTestSessionsTableId").attr("checked", "checked");
	        	}
			},
			onSelectAll: function(id, status) {
				if(status) {
					//User checks the test session.
					for(var i=0; i<id.length; i++) {
						selectedTestSessions.push(id[i]);
						var gridRow = $('#autoRegisteredTestSessionsTableId').jqGrid('getRowData',id[i]);
						selectedTestSessionsWithTSEndDate.push(id[i] + "-" + gridRow['expiryDate']);
						selectedTSAPValues.push(id[i] + "-" + gridRow['assessmentProgramName']);
						selectedOtwIds.push(Number(gridRow['otwId']));
					}
				} else {
					//User unchecks the test session.
					for(var i=0; i<id.length; i++) {
						selectedTestSessions = $.grep(selectedTestSessions, function(value) {
							return value != id[i];
						});
						
						var gridRow = $('#autoRegisteredTestSessionsTableId').jqGrid('getRowData',id[i]);
						selectedTestSessionsWithTSEndDate = $.grep(selectedTestSessionsWithTSEndDate, function(value) {
							return value != id[i] + "-" + gridRow['expiryDate'];
						});
						selectedTSAPValues = $.grep(selectedTSAPValues, function(value) {
							return value != id[i] + "-" + gridRow['assessmentProgramName'];
						});
						selectedOtwIds = $.grep(selectedOtwIds, function(value) {
							return value != Number(gridRow['otwId']);
						});
						
					}
				}
			}
		});		

		</security:authorize>
	</security:authorize>
	
	if(!isTeacher) {
		
		$('#tsAssessmentPrograms, #tsTestingPrograms, #tsGrades, #tsContentAreas, #tsDistrictOrgs, #tsSchoolOrgs').select2({
    		placeholder:'Select',
    		multiple: false,
    		allowClear: true
    	});
    	
    	filteringOrganization($("#tsDistrictOrgs"));
    	filteringOrganization($("#tsSchoolOrgs"));
    	
    	populateTSSearchFiltersData();
    	
    	$('#tsSearchFilterForm').validate({
    		ignore: "",
    		rules: {
    			tsAssessmentPrograms: {required: true},
    			tsTestingPrograms: {required: true},    			
    			<c:if test="${user.accessLevel < 50 }">
    				tsDistrictOrgs: {required: true},
    			</c:if>
    			tsSchoolOrgs: {required: true}
    		}
    	});
    	
		$('#tsSearchBtn').on("click",function(e) {
			$("#tsTestSessionsDiv").removeClass("hidden");
			e.preventDefault();
			if((($('#tsAssessmentPrograms').val() == kapAssessmentProgramId) || ($('#tsAssessmentPrograms').val() == ampAssessmentProgramId)
					|| ($('#tsAssessmentPrograms').val() == pltwAssessmentProgramId) || ($('#tsAssessmentPrograms').val() == kelpaAssessmentProgramId)) 
					&& ($('#tsTestingPrograms').val() == summativeTestingProgramId))
				ticketsDisplay = false;
			else
				ticketsDisplay = true;
			if(!ticketsDisplay)
			{
				$("#autoRegisteredTestSessionsTableId").jqGrid('hideCol','printTicket');
				$("#viewTSTicketsTopButton").hide();
			}
			else
			{
				$("#autoRegisteredTestSessionsTableId").jqGrid('showCol','printTicket');
				$("#viewTSTicketsTopButton").show();
			}
			
			$('#tsSearchFilterErrorsSession').html("");
			$('#tsViewAutoRegistrationSearchFilterMessage').html("").hide();
			if($('#tsSearchFilterForm').valid()){
				setTSSearchFilterValuesToSession();
				loadAutoRegisteredTestSessions();
			}
			
		});
	}	
	
	if(isTeacher) {
		$("#tsForTeacherSearchBtn").removeClass("btn_disabled");
		$("#tsForTeacherSearchBtn").prop('disabled', false);
		$('#tsForTeacherSearchBtn').on("click",function(e) {
			loadAutoRegisteredTestSessions();
		});
	}
});
	function resetTestCoordinationTab(){
		$('.error').hide();
		$('#tsSearchFilterForm')[0].reset();
		setTimeout(function(){
			$("#autoRegisteredTestSessionsTableId").jqGrid("clearGridData", true);				
		},200);			
	}
	
	function loadAutoRegisteredTestSessions() {
		
		$('#autoRegisteredTSStudentsDiv').hide();
		$('#autoRegisteredTestSessionsDiv').show();
		selectedTestSessions = [];
		selectedTSAPValues = [];
		selectedTestSessionsWithTSEndDate = [];
		selectedOtwIds = [];
		
		<security:authorize  access="hasRole('PERM_ROSTERRECORD_VIEW')" >
			<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')" >

				var tsAssessmentProgramId = "";
				var tsTestingProgramId = "";
				var tsContentAreaId = "";
				var tsGradeCourseId = "";
				var tsSchoolOrgId = "";
				var tsShowExpiredFlag = "";
				var tsShowCompletedFlag="";
				
				if(!isTeacher) {					
					tsAssessmentProgramId = getFromSessionStorage("tsAssessmentProgramId");
					tsTestingProgramId = getFromSessionStorage("tsTestingProgramId");
					tsContentAreaId = getFromSessionStorage("tsContentAreaId");
					tsGradeCourseId = getFromSessionStorage("tsGradeCourseId");
					tsSchoolOrgId = getFromSessionStorage("tsSchoolOrgId");	
					tsShowExpiredFlag = getFromSessionStorage("tsShowExpiredFlag");
					tsShowCompletedFlag = getFromSessionStorage("tsShowCompletedFlag");
				}	
				if(isTeacher) {
 					tsAssessmentProgramId=$('#userDefaultAssessmentProgram option:selected').val();
 					tsShowExpiredFlag = $('#tsShowExpiredForTeacher').is(":checked");
					tsShowCompletedFlag = $('#tsShowCompletedForTeacher').is(":checked");
				}
				var $gridAuto = $("#autoRegisteredTestSessionsTableId");
								
				//Clear the previous error messages
				setTimeout("aart.clearMessages()", 0);
				if(!($('#testSessionSuccessFlag').val() == 'editSession' &&  $('#saveGrid').val() == 'save')) {
					
					$gridAuto.jqGrid('setGridParam',{
						datatype:"json", 
						search: false,
						url : 'getAutoRegistrationTestSessionsByUser.htm?q=1',
						postData : {
							filters: null,
							assessmentProgramId : tsAssessmentProgramId,
							testingProgramId : tsTestingProgramId,							
							contentAreaId : tsContentAreaId,
							gradeCourseId : tsGradeCourseId,
							schoolOrgId : tsSchoolOrgId,
							showExpired : tsShowExpiredFlag,
							includeCompletedTestSession:tsShowCompletedFlag
						},
						gridComplete: function() {
							 var ids = $(this).jqGrid('getDataIDs');         
					         var tableid=$(this).attr('id');      
					            for(var i=0;i<ids.length;i++)
					            {  
					            // to get the anchor tag text value from the column
					            	var columnValue = $('#autoRegisteredTestSessionsTableId').jqGrid("getCell", ids[i], 'testSessionName').replace(/^.+(?:>)(.+(?=<\/a)).+$/, '$1');
					            	$('#jqg_'+tableid+'_'+ids[i]).attr('title', columnValue + ' Check Box');
					            	$('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
					            }
					            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
					             $('#cb_'+tableid).attr('title','View Test Session Grid All Check Box');
					             $('#cb_'+tableid).removeAttr('aria-checked');
					             $.each(objs, function(index, value) {         
					              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
					                    $(value).attr('title',$(nm).text()+' filter');                          
					                    });
						}
					}).trigger("reloadGrid",[{page:1}]);
				} else {
					var pdata = $gridAuto.getGridParam("postData");
					if(typeof pdata  == 'undefined' || typeof pdata.filters == 'undefined') {
						pdata = {
							filters: ""
						};
					}

					pdata.assessmentProgramId = tsAssessmentProgramId;
					pdata.testingProgramId = tsTestingProgramId;
					pdata.contentAreaId = tsContentAreaId;
					pdata.gradeCourseId = tsGradeCourseId;
					pdata.schoolOrgId = tsSchoolOrgId;
					pdata.showExpired = tsShowExpiredFlag;
					pdata.includeCompletedTestSession = tsShowCompletedFlag;
					$gridAuto.jqGrid('setGridParam',{
						datatype:"json", 
						url : 'getAutoRegistrationTestSessionsByUser.htm?q=1', 
						search: false,
						postData: pdata
					}).trigger("reloadGrid");
				}
			</security:authorize>
		</security:authorize>
	}
	
	
	//Get dropdowns data to load filters.
	function getDropdownValues() {
		return $.ajax({
            url: 'getARTSDropdownData.htm',            
            dataType: 'json',
            type: "GET",
            async: false
		}).done(function(data) {
            	contentAreaNameValues = ":All";
           	 	for (i=0; i<data.contentAreaNames.length; i++) {
           	 		contentAreaNameValues += ";" + data.contentAreaNames[i]
           	 			+ ":" + data.contentAreaNames[i];
           	 	}
           	 	
           	 	gradeCourseNameValues = ":All";
        	 	for (i=0; i<data.gradeCourseNames.length; i++) {
        	 		gradeCourseNameValues += ";" + data.gradeCourseNames[i]
        	 			+ ":" + data.gradeCourseNames[i];
        	 	}
        });
	}
	
	
	//Load dropdown data for contentAreaNames.
	var loadContentAreaNameValues = function() {        
        return contentAreaNameValues;
    };
	
    
  	//Load dropdown data for gradeCourseNames.
	var loadGradeCourseNameValues = function() {        
        return gradeCourseNameValues;
    };
    
    
	//Code to set the position of the AlertMod 
		//warning message (Please, select a row)
	var orgViewModal = $.jgrid.viewModal;

	//Custom formatter for pdf link. 
	function printTestFormatter(cellvalue, options, rowObject) {
		var mediaPath = "${applicationScope['nfs.url']}";
		
		var htmlStr = '';
		var displayPNPCountLink = false;
		var data = rowObject[5];
		if (data !== undefined && data !== null && data != 'Not Available' && data != '') {
			var fileNames=data.split(','), htmlStr='';
			if(fileNames.length>0)
				displayPNPCountLink = true;
			
			htmlStr += '<div>';
			for(var i=0;i<fileNames.length;i++){
				var carr = fileNames[i].split('----');
				htmlStr += '<a class="pdfLink" title="'+carr[1]+'" href="'+mediaPath+carr[0]+'" target="_blank">';				
				htmlStr += '<img alt="'+carr[1]+'" style="border:0px solid;" src="images/pdf.png">';								
				htmlStr += '</a>';
			}			
			htmlStr += '</div>';
		} 
		displayPNPCountLink= false;
		if(displayPNPCountLink)
		{
			htmlStr += '<a class="link" href="javascript:void(0)" onclick="showPNPCounts('+rowObject[0]+');">';
			htmlStr += 'Show PNP Counts</a>';
		}
		
	    return htmlStr;
	}
	
	
	function showPNPCounts(testsessionid) {
		if(testsessionid != null && testsessionid != '') {
			$.ajax({
				url: 'getStudentPNPValuesByTestSession.htm',
				dataType: 'json',
				data: {"testSessionId": testsessionid},
				type: "GET",
				success: function(data) {				
					if (typeof data  != 'undefined' && data !== null) {
						var htmlString='';
						
						htmlString += '<div style="padding-left: 0.75em;">';
						htmlString += '<font color="#0E76BC"><b>Paper: </b></font>'+data.paperAndPencil+'<br/><br/>';
						htmlString += '<font color="#0E76BC"><b>Large Print: </b></font>'+data.largePrintBooklet+'<br/><br/>';						
						htmlString += '<font color="#0E76BC"><b>Spanish Print: </b></font>'+data.Language+'<br/><br/>';
						htmlString += '</div>';
						
						$('<div />').html(htmlString).dialog({resizable: false,
							height: 200,
							width: 710,
							modal: true, 
							title: "Number of students with PNP's that require special forms",});
					} else {
						$('<div />').html("No Data.").dialog({modal: true});
					}
				}
			});
			
		}
	}
	
	
	function showPrintTestFiles(testsessionid) {
		if(testsessionid != null && testsessionid != '') {
			$.ajax({
				url: 'getAutoRegisteredPrintFiles.htm',
				dataType: 'json',
				data: {"testSessionId": testsessionid},
				type: "GET",
				success: function(data) {				
					if (data !== undefined && data.files !== null && data.files !== '') {
						var fileNames=data.files.split(','), htmlString='';
						for(var i=0;i<fileNames.length;i++){
							htmlString += '<div><a class="pdfLink" href="printTest.htm?';
							htmlString += 'path=' + encodeURIComponent(fileNames[i]);
							htmlString += '">';
							if(fileNames[i].indexOf('paper.') != -1) {
								htmlString += 'Paper';
							} else if(fileNames[i].indexOf('spanish.') != -1) {
								htmlString += 'Spanish';
							} else if(fileNames[i].indexOf('read_aloud.') != -1) {
								htmlString += 'Read aloud';
							} else if(fileNames[i].indexOf('braille.') != -1) {
								htmlString += 'Braille';
							} else if(fileNames[i].indexOf('large_print.') != -1) {
								htmlString += 'Large print';
							}
							htmlString += '</a></div><br>';
						}
						
						$('<div />').html(htmlString).dialog({modal: true});
					} else {
						$('<div />').html("No static version of test is avaiable to print.").dialog({modal: true});
					}
				}
			});
			
		}
	}
	
	//Custom formatter for pdf link. 
	function printARTicketLinkFormatter(cellvalue, options, rowObject) {
		var htmlString = "N.A";
		htmlString = '<a class="pdfLink" href="getPDFTickets.htm?'+
				'assessmentProgramName=' + rowObject[1] + 
				'&testSessionId='+ rowObject[0] +
				'&isAutoRegistered=true' +
				'">' + '<img alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png">' + '</a>';
	    return htmlString;
	}
	
	
	//Custom unformatter for pdf link.
	function printARTicketLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	
	//Custom formatter for test session name link. 
	function editARTestSessionLinkFormatter(cellvalue, options, rowObject) {	
		var htmlString = '<a class="link" href="setupAutoRegisteredTestSession.htm?'+
		'testSessionId='+ encodeURIComponent(rowObject[0]) + '&assessmentProgramName='+ encodeURIComponent(rowObject[1]) + '&testSessionName='+ encodeURIComponent(rowObject[3]) + '&testPanelId='+ encodeURIComponent(rowObject[12])+ '&ticketsDisplay='+ticketsDisplay+'&testingProgram='+$("#tsTestingPrograms option:selected").text()+'">' + ''+cellvalue + '</a>';	

	    return htmlString;				
	}
	
	
	//Custom unformatter for test session name link.
	function editARTestSessionLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	

	//Execute below method on view tickets button click.
	$('#viewTSTicketsTopButton').on("click",function() {
		aart.clearMessages();
		if (selectedTestSessions !== undefined &&  selectedTestSessions.length > 0) {
			window.location.href = 'getPDFTickets.htm?'+ 
					'&isAutoRegistered=true' +
					'&selectedTestSessions='+ selectedTestSessions;
		} else {
			$('#select_testsession_errors').show();
			setTimeout("aart.clearMessages()", 3000);
		}
			
	});
	
	function populateTSSearchFiltersData() {
		var $grid = $("#autoRegisteredTestSessionsTableId");
		var pdata = $grid.getGridParam("postData");
		
		var tsAPSelect = $('#tsAssessmentPrograms'), tsOptionText='';
		$('.messages').html('').hide();
		tsAPSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
		$.ajax({
			url: 'getAssessmentProgramsByUserSelected.htm',
			dataType: 'json',
			type: "POST",
			success: function(tsAssessmentPrograms) {				
				if (tsAssessmentPrograms !== undefined && tsAssessmentPrograms !== null && tsAssessmentPrograms.length > 0) {
					$.each(tsAssessmentPrograms, function(i, tsAssessmentProgram) {
						tsOptionText = tsAssessmentPrograms[i].programName;
						if(tsAssessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
							tsAPSelect.append($('<option selected=\''+'selected'+'\'></option>').val(tsAssessmentProgram.id).html(tsOptionText));
						} else{
							tsAPSelect.append($('<option></option>').val(tsAssessmentProgram.id).html(tsOptionText));	
						}
						if(tsAssessmentPrograms[i].programName.toUpperCase() === "KAP"){
							kapAssessmentProgramId = tsAssessmentProgram.id;
						}else if(tsAssessmentPrograms[i].programName.toUpperCase() === "ALASKA"){
							ampAssessmentProgramId = tsAssessmentProgram.id;
						}else if(tsAssessmentPrograms[i].programName.toUpperCase() === "KELPA2"){
							kelpaAssessmentProgramId = tsAssessmentProgram.id;
						}
						else if(tsAssessmentPrograms[i].programName.toUpperCase() === "PLTW"){
							pltwAssessmentProgramId = tsAssessmentProgram.id;
						}
					});
					
					tsAPSelect.trigger('change');

					var filterSelectedValue = getFromSessionStorage("tsAssessmentProgramId");				
					if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
						tsAPSelect.val(filterSelectedValue);
						tsAPSelect.trigger('change');
					} else if (tsAssessmentPrograms.length == 1) {
						tsAPSelect.find('option:first').prop('selected',false).next('option').attr('selected', 'selected');
						tsAPSelect.trigger('change');
    				}
				} else {
					$('body, html').animate({scrollTop:0}, 'slow');
					$('#tsSearchFilterErrorsSession').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
				}
				$('#tsGrades, #tsTestingPrograms, #tsContentAreas, #tsAssessmentPrograms').trigger('change.select2');
			}
		});
		
		<c:if test="${user.accessLevel < 50 }">
			var tsDistrictOrgselect = $('#tsDistrictOrgs');
			$.ajax({
				url: 'getChildOrgsWithParentForFilter.htm',
				dataType: 'json',
				data: {
					orgId : ${user.currentOrganizationId},
		        	orgType:'DT'
		        	},				
				type: "POST",
				success: function(tsDistrictOrgs) {				
					if (tsDistrictOrgs !== undefined && tsDistrictOrgs !== null && tsDistrictOrgs.length > 0) {
						$.each(tsDistrictOrgs, function(i, tsDistrictOrg) {
							tsOptionText = tsDistrictOrgs[i].organizationName;
							tsDistrictOrgselect.append($('<option></option>').val(tsDistrictOrg.id).html(tsOptionText));
						});
						
						var filterSelectedValue = getFromSessionStorage("tsDistrictOrgId");				
						if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
							tsDistrictOrgselect.val(filterSelectedValue);
	    					$("#tsDistrictOrgs").trigger('change');
						} else if (tsDistrictOrgs.length == 1) {
							tsDistrictOrgselect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
	    					$("#tsDistrictOrgs").trigger('change');
	    				}
					} else {
						$('body, html').animate({scrollTop:0}, 'slow');
						$('#tsSearchFilterErrorsSession').html("No District Organizations Found for the current user").show();
					}
					$('#tsSchoolOrgs, #tsDistrictOrgs').trigger('change.select2');
				}
			});
			
			$('#tsDistrictOrgs').on("change",function() {
				$('#tsSchoolOrgs').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#tsSchoolOrgs').trigger('change.select2');
				var districtOrgId = $('#tsDistrictOrgs').val();
				if (districtOrgId != 0) {
					$.ajax({
				        url: 'getChildOrgsWithParentForFilter.htm',
				        data: {
				        	orgId : districtOrgId,
				        	orgType:'SCH'
				        	},
				        dataType: 'json',
				        type: "POST",
				        success: function(tsSchoolOrgs) {
				        					        	
							$.each(tsSchoolOrgs, function(i, schoolOrg) {
								$('#tsSchoolOrgs').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
							});
							
							var filterSelectedValue = getFromSessionStorage("tsSchoolOrgId");				
							if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
								$("#tsSchoolOrgs").val(filterSelectedValue);
		    					$("#tsSchoolOrgs").trigger('change');
							} else if (tsSchoolOrgs.length == 1) {
								$("#tsSchoolOrgs option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#tsSchoolOrgs").trigger('change');
							}							
							$('#tsSchoolOrgs').trigger('change.select2');
				        }
					});
				}
			});	
		</c:if>
		
		<c:if test="${user.accessLevel >= 50 && !user.teacher}">				
			var tsSchoolOrgselect = $('#tsSchoolOrgs');
			$.ajax({
				url: 'getChildOrgsWithParentForFilter.htm',
				dataType: 'json',
				data: {
					orgId : ${user.currentOrganizationId},
		        	orgType:'SCH'
		        	},
				type: "POST",
				success: function(tsSchoolOrgs) {				
					if (tsSchoolOrgs !== undefined && tsSchoolOrgs !== null && tsSchoolOrgs.length > 0) {
						$.each(tsSchoolOrgs, function(i, schoolOrg) {
							tsOptionText = tsSchoolOrgs[i].organizationName;
							tsSchoolOrgselect.append($('<option></option>').val(schoolOrg.id).html(tsOptionText));
						});
						
						var filterSelectedValue = getFromSessionStorage("tsSchoolOrgId");				
						if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
							tsSchoolOrgselect.val(filterSelectedValue);
							tsSchoolOrgselect.trigger('change');
						} else if (tsSchoolOrgs.length == 1) {
							tsSchoolOrgselect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
	    					$("#tsSchoolOrgs").trigger('change');
	    				}
					} else {
						$('body, html').animate({scrollTop:0}, 'slow');
						$('#tsSearchFilterErrorsSession').html("No School Organizations Found for the current user").show();
					}
					$('#tsSchoolOrgs').trigger('change.select2');
				}
			});
		</c:if>
		
		$('#tsAssessmentPrograms').on("change",function() {
			
			$('#tsTestingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#tsContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#tsGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#tsGrades, #tsTestingPrograms, #tsContentAreas').trigger('change.select2');
			var tsAssessmentProgramId = $('#tsAssessmentPrograms').val();
			
			if (tsAssessmentProgramId != 0) {
				$.ajax({
			        url: 'getDynamicTestingPrograms.htm',
			        data: {
			        	assessmentProgramId: tsAssessmentProgramId
			        	},
			        dataType: 'json',
			        type: "POST",
			        success: function(tsTestingPrograms) {
						$('#tsGrades, #tsContentAreas').prop('disabled', true);
    	
			        	$('#tsTestingPrograms').html("");		
		        		$('#tsTestingPrograms').append($('<option></option>').val('').html('Select'));
						$.each(tsTestingPrograms, function(i, tsTestingProgram) {
							$('#tsTestingPrograms').append($('<option></option>').attr("value", tsTestingProgram.id).text(tsTestingProgram.programName));
							if(tsTestingProgram.programName.toUpperCase() === "SUMMATIVE")
								summativeTestingProgramId = tsTestingProgram.id;
						});
						
						var filterSelectedValue = getFromSessionStorage("tsTestingProgramId");				
						if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
							$("#tsTestingPrograms").val(filterSelectedValue);
							$("#tsTestingPrograms").trigger('change');
						} else if (tsTestingPrograms.length == 1) {
							$("#tsTestingPrograms option").removeAttr('selected').next('option').attr('selected', 'selected');
							$("#tsTestingPrograms").trigger('change');
						}
						$('#tsTestingPrograms').trigger('change.select2');
			        }
				});
				
				$('#tsTestingPrograms').on("change",function() {
					$('#tsContentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					$('#tsContentAreas').trigger('change.select2');
					$('#tsGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					$('#tsGrades').trigger('change.select2');
					$('#tsContentAreas').prop('disabled', false);
					$('#tsGrades').prop('disabled', true);
					var tsTestingProgramId = $('#tsTestingPrograms').val();
					$.ajax({
				        url: 'getContentAreasByAssessmentProgramandTestProgramId.htm',
				        data: {
				        	assessmentProgramId: tsAssessmentProgramId,
				        	testingProgramId : tsTestingProgramId
				        	},
				        dataType: 'json',
				        type: "POST",
				        success: function(tsContentAreas) {
				        	$('#tsContentAreas').html("");		
				        	$('#tsGrades').html("");		

				        	$('#tsContentAreas').append($('<option></option>').val('').html('Select'));
				        	$('#tsGrades').append($('<option></option>').val('').html('Select'));
							$.each(tsContentAreas, function(i, tsContentArea) {
								$('#tsContentAreas').append($('<option></option>').attr("value", tsContentArea.id).text(tsContentArea.name));
							});
							
							var filterSelectedValue = getFromSessionStorage("tsContentAreaId");				
							if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
								$("#tsContentAreas").val(filterSelectedValue);
								$("#tsContentAreas").trigger('change');
							}						
							$('#tsContentAreas').trigger('change.select2');
				        }
					});
				});	
			}
		});
		
		$('#tsContentAreas').on("change",function() {
			$('#tsGrades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#tsGrades').trigger('change.select2');
			$('#tsGrades').prop('disabled', true);
			var assessmentProgramId = $('#tsAssessmentPrograms').val();
			var testingProgramId = $('#tsTestingPrograms').val();
			var contentAreaId = $('#tsContentAreas').val();
			if (contentAreaId != 0) {
				$.ajax({
			        url: 'getGradeCourseByContentAreaIdForTestCoordination.htm',
			        data: {
			        	assessmentProgramId:assessmentProgramId,
			        	testingProgramId:testingProgramId,
			        	contentAreaId: contentAreaId
			        	},
			        dataType: 'json',
			        type: "GET",
			        success: function(tsGrades) {

			        	$('#tsGrades').html("");		
			        	$('#tsGrades').append($('<option></option>').val('').html('Select'));				        	

						$.each(tsGrades, function(i, tsGrade) {
							$('#tsGrades').append($('<option></option>').attr("value", tsGrade.id).text(tsGrade.name));
						});
						$('#tsGrades').prop('disabled', false);

						var filterSelectedValue = getFromSessionStorage("tsGradeCourseId");				
						if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
							$("#tsGrades").val(filterSelectedValue);
							// if filterSelectedValue does not exist in tsGrades list, set to "", otherwise it gets set to null, with causes a bad request
							if($("#tsGrades").val() == null){
								$("#tsGrades").val("");
							}
							$("#tsGrades").trigger('change');
						}											
						$('#tsGrades').trigger('change.select2');
			        }
				});				
			}
		});			
	}	
	
	function setTSSearchFilterValuesToSession() {
		
		setInSessionStorage("tsAssessmentProgramId", $('#tsAssessmentPrograms').val());
		setInSessionStorage("tsTestingProgramId", $('#tsTestingPrograms').val());
		setInSessionStorage("tsContentAreaId", $('#tsContentAreas').val());
		setInSessionStorage("tsGradeCourseId", $('#tsGrades').val());
		setInSessionStorage("tsDistrictOrgId", $('#tsDistrictOrgs').val());
		setInSessionStorage("tsSchoolOrgId", $('#tsSchoolOrgs').val());
		setInSessionStorage("tsShowExpiredFlag", $('#tsShowExpired').is(':checked'));
		setInSessionStorage("tsShowCompletedFlag", $('#tsShowCompleted').is(':checked'));
	}	

	/**
	* Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15915 : Test coordination extend window for test session
	* Code for operation test session window extension. 
	*/
	$("#newTSWEndDate").datepicker();
	
	/*
	* If at least one test session has been selected, then display overlay to changed expiry date(s).
	*/
	$('#extendTestSessionWindowButton').on("click",function() {
		aart.clearMessages();
		if (selectedTestSessions !== undefined &&  selectedTestSessions.length > 0) {
			openETSOverlay();
		} else {
			$('#extend_test_session_window_errors').show();
			setTimeout("aart.clearMessages()", 3000);
		} 
	});
	
	/**
	* Dialog box to dsiplay intended expiry date to be changed for selected test sessions.
	*/
	function openETSOverlay(){
		var testSessionIdsNeedToUpdate = [];
		$('#extendTestSessionWindowDiv').dialog({
			autoOpen: true,
			modal: true,
			width: 400,
			height: 300,			
			title: 'Extend Window',
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			open: function(ev, ui) {
				$('#newTSWEndDate_confirm_msg').hide();
				$('#newTSWEndDate').val("mm/dd/yyyy");
			    $('.ui-dialog-buttonpane button:contains("Confirm")').button().hide();
			},
			close: function(ev, ui) {
	    		$('.ui-dialog-buttonpane button:contains("Save")').button().show();
			},
			buttons: {
			    Cancel: function() {
			    	 $(this).dialog("close");
			    },
			    Save: function() {				    	
			    	var newEndDateVal = $('#newTSWEndDate').val();
			    	if(typeof newEndDateVal  == 'undefined' || typeof newEndDateVal == 'undefined' || newEndDateVal == null || newEndDateVal == '') {			    		
			    		$('#newTSWEndDate_errors1').show();
						setTimeout("aart.clearMessages()", 3000);
						return false;
			    	} else{ 			    	
				    	var vflag = true;
				    	if (new Date(newEndDateVal) <= new Date()) {
			    			vflag = false;				    		
			    		} else {
				    		$.each(selectedTestSessionsWithTSEndDate, function(i, item) {
				    			var tss = item.split('-')[0];
				    			var enddt = item.split('-')[1];
				    			if(new Date(newEndDateVal) >= new Date(enddt)){
				    				testSessionIdsNeedToUpdate.push(tss);
				    			}
							});
			    		}
				    	
				    	if(vflag && testSessionIdsNeedToUpdate.length > 0){
				    		$('#newTSWEndDate_confirm_msg').show();
				    		$('.ui-dialog-buttonpane button:contains("Save")').button().hide();
				    		$('.ui-dialog-buttonpane button:contains("Confirm")').button().show();
				    	} else {
				    		$('#newTSWEndDate_errors2').show();
							setTimeout("aart.clearMessages()", 3000);
				    	}
			    	}
			    },
			    Confirm: function() {
			    	var newEndDateVal = $('#newTSWEndDate').val();
			    	saveExtendedTestSessionWindowEndDate(testSessionIdsNeedToUpdate, newEndDateVal);
		    		$(this).dialog("close");
			    },
			}
		});
		
		/**
		* AJAX call to save new expiry date for selected test sessions by passing operational window ids
		*/
		function saveExtendedTestSessionWindowEndDate(selectedOtwIdsParam, newTSWEndDateParam){
			$.ajax({
		        url: 'extendTestSessionWindowEndDate.htm',
		        data: {
		        	selectedOtwIds : selectedOtwIdsParam,
		        	newTSWEndDate : newTSWEndDateParam
		        	},
		        dataType: 'json',
		        type: "POST",
		        success: function(data) {
		        					        	
					if(typeof data  != 'undefined' || typeof data.success != 'undefined' 
							&& data != null && data.sucess != null && data.success == true){
						$('#extend_test_session_window_save_success').text('Test session end dates have been updated for '+selectedOtwIdsParam.length+' test sessions.');
						$('#extend_test_session_window_save_success').show();
						setTimeout("aart.clearMessages();loadAutoRegisteredTestSessions();", 3000);						
					} else {
						$('#extend_test_session_window_save_errors').show();
						setTimeout("aart.clearMessages()", 3000);
					}
		        }
			}); 
		}
		
		/**
		* Save test session and refresh grid
		*/
		$('#saveTSWBtn').on("click",function(e) {
			
			e.preventDefault();
			$('#tsSearchFilterErrorsSession').html("");
			$('#tsViewAutoRegistrationSearchFilterMessage').html("").hide();			
			if($('#tsSearchFilterForm').valid()){
				setTSSearchFilterValuesToSession();
				loadAutoRegisteredTestSessions();
			}
		});
	}
</script>
