<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<div >
<div id="messagesTestManagementTab" class="messages">
    <br/> <span id="test_session_created" class="info_message ui-state-highlight hidden"><fmt:message key='label.testsession.created' /></span> 
    <br/> <span id="test_session_edited" class="info_message ui-state-highlight hidden"><fmt:message key='label.testsession.update.success' /></span> 
 </div>
 <div>
    <br/> <span id="select_testsession_error" class="info_message ui-state-error hidden"><fmt:message key='error.select.testsessions.tickets' /></span>
 </div>
 
<div id ="testManagement" class="noBorder">
	<input id="detailViewCaption" value ="View Test Session Detail" type="text" class="hidden" />	
	<input id="testSessionSuccessFlag" value ="${validFlag}" type="text" class="hidden" />
	<input id="saveGrid" value ="${saveGrid}" type="text" class="hidden" />		
		
	<!-- In below input component attribute "class="hidden" used to fix the IE8 browser issue. -->	
	<input id="loggedInUserId" value ="${user.id}" type="text" class="hidden" />	
	<!--  <input id="detailViewCaptionStudentDetails" value ="View Student Detail" type="text" class="hidden" />  -->
	<div>
		<div class="full_side" style="margin-bottom:0px;">
			<%-- <c:if test="${!user.teacher}">
				<a class="tmfilterheading" href="javascript:void(0)"><h1 class="panel_head sub"><span class="search_btn">&nbsp;</span>Filter</h1></a>
			</c:if> --%>
		</div>
	</div>
		<div id="searchFilterContainerTop">
			<form id="searchFilterForm" name="searchFilterForm" class="form">
				<div class="btn-bar">
					<div id="searchFilterErrors" class="error"></div>
					<div id="searchFilterMessage" style="padding:20px" class="hidden"></div> 
				</div>
				<c:if test="${!user.teacher}">
					<c:if test="${user.accessLevel < 50 }">
					    <div class="form-fields">
							<label for="districtOrgs" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
							<select id="districtOrgs" title="District" class="bcg_select required" name="districtOrgs">
								<option value="">Select</option>
							</select>
						</div>
					</c:if>					
					<c:if test="${user.accessLevel < 60 }">
					<div class="form-fields">
						<label for="schoolOrgs" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
						<select id="schoolOrgs" title="School" class="bcg_select required" name="schoolOrgs">
							<option value="">Select</option>
						</select>
					</div>
					</c:if>
					<div class="form-fields">
						<label for="testingPrograms" class="field-label">Testing Program:<span class="lbl-required">*</span></label>			
						<select id="testingPrograms" title="Testing Program" class="bcg_select required" name="testingPrograms">
							<option value="">Select</option>
						</select>
					</div>			
					<div class="form-fields">
						<label for="contentAreas" class="field-label">Subject:</label>			
						<select id="contentAreas" title="Subject" class="bcg_select" name="contentAreas">
							<option value="">Select</option>
						</select>
					</div>
					<div class="form-fields">
						<label for="grades" class="field-label">Grade:</label>			
						<select id="grades" title="Grade" class="bcg_select" name="grades">
							<option value="">Select</option>
						</select>
					</div>
				</c:if>
				<div class ="table_wrap">
				<div style="text-align:right;">
					<input type="checkbox" id="showCompleted" name="showCompleted" value="Yes" title="Include completed">Include completed
					<c:if test="${!user.teacher}">
						<input type="checkbox" id="showExpired" name="showExpired" value="Yes" title="Include expired">Include expired
					</c:if>
					<button class="btn_blue" id="searchBtn">Search</button>
<%-- 					<button class="btn_blue" data-toggle="tooltip" title="<fmt:message key='label.testmanagement.viewToolTip'/>" id="tmViewTSTicketsTopButton">View Tickets</button> --%>
					<span class="d-inline-block" data-toggle="tooltip" title="<fmt:message key='label.testmanagement.viewToolTip'/>">
					  <button class="btn_blue" type="button" id="tmViewTSTicketsTopButton">View Tickets</button>
					</span>
				</div>	
				</div>				
			</form>
		</div>
	<div id="noTSReport" class="none" style="width:100%"></div>	
	
	 <!-- /top_info -->
<div class ="table_wrap">
	<div class="kite-table">
		<table id="testSessionsTableId"  class="responsive"></table>
		<div id="ptestSessionsTableId" class="responsive"></div>
	</div>
</div>	
<br />	
</div>
</div>
<script>

var isTeacher = false;
<c:if test="${user.teacher}">
	isTeacher = true;
</c:if>
$(function() {
	intiTestManagement();
	//resetTestManagmentTab();
	$("#tmViewTSTicketsTopButton").prop("disabled",true);
	$("#tmViewTSTicketsTopButton").addClass('ui-state-disabled');
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

function intiTestManagement(){
	
<security:authorize  access="hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL')" >                		
	<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')" >
		//Need to set this in the session so that loggedInUserId would be used as a key to store the orgs.
		//window.sessionStorage.setItem("loggedInUserId", $('#loggedInUserId').val() );
		//Capture the mouse position for Loading message display.
		//$(document).mousemove(function(e) {
		//	mousePosition = e.pageY;				
		//});
		var $grid= $('#testSessionsTableId');
		var grid_width = $grid.parent().width();
		if(grid_width == 100 || grid_width == 0) {
			grid_width = 1029;				
		}
		var cell_width =180;
		var gridParam;

		var cm=[ 
				{ name : 'testSessionRosterId', index : 'testSessionRosterId',label:'testSessionRosterIdViewTestManagment', width : cell_width,
						editable : true, editrules:{edithidden:true}, viewable: false,
						editoptions:{readonly:true,size:10},
						sorttype : 'int', search : true, hidden: true, hidedlg: true },

					{ name : 'testSessionId', index : 'testSessionId',label:'testSessionIdViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: false,
							editoptions:{readonly:true,size:10},
							sorttype : 'int', search : true, hidden: true, hidedlg: true },
							
			        { name: 'actions',label:'actionsViewTestManagment', width: 65, sortable: false, hidedlg: true, search: false, 
						formatter: function(cellValue, options, rowObject){
							//var id = rowObject[0];
							return '<div id="testSessionsGridActionDiv_'+rowObject[0]+'" data-testsessionid="'+ rowObject[1]+'" align="center">' +
								'<div id="deleteTestSession_'+rowObject[0]+'" class="jqactions"></div>' +
							'</div>';
						}
						},
						
					{ name : 'testSessionName', index : 'testSessionName',label:'testSessionNameViewTestManagment', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true,size:10},
								sortable : true, search : true, hidden : false, hidedlg : true,
								formatter: editTestSessionLinkFormatter, unformat: editTestSessionLinkUnFormatter},

					{ name : 'printTicket', index : 'printTicket',label:'printTicketViewTestManagment', width : cell_width-80,
										editable : true, editrules:{edithidden:true}, viewable: true,
										editoptions:{readonly:true,size:10}, sortable : false, search : false, hidden : false, hidedlg : true	   						
						,formatter: printTicketLinkFormatter, unformat: printTicketLinkUnFormatter },
					{ name : 'testInformation', index : 'testInformation',label:'testInformationViewTestManagment', width : cell_width, editable : true, 
      						editrules:{edithidden:true}, sortable : false, search : false, hidden : false,viewable: true, 
							editoptions:{readonly:true,size:10}, formatter: testInfoLinkFormatter, unformat: testInfoLinkUnFormatter },
					{ name : 'testCollectionId', index : 'testCollectionId',label:'testCollectionIdViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: false,
							editoptions:{readonly:true,size:10},
							sortable : true, search : true, hidden : true, hidedlg: true },
							
					{ name : 'assessmentName', index : 'assessmentName',label:'assessmentNameViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: true,
							editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },
						
					{ name : 'testLetProgress', index : 'testLetProgress',label:'testLetProgressViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: true,
							editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },
					
					{ name : 'tcGradeCourseName', index : 'tcGradeCourseName',label:'tcGradeCourseNameViewTestManagment',
							width : cell_width, editable : true,
							editrules:{edithidden:true}, viewable: true,
							editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },

				{ name : 'tcContentAreaName', index : 'tcContentAreaName',label:'tcContentAreaNameViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },

					{ name : 'programName', index : 'programName',label:'programNameViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: true,
							editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

					{ name : 'assessmentProgramName', index : 'assessmentProgramName',label:'assessmentProgramNameViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: true,
							editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

					{ name : 'rosterId', index : 'rosterId',label:'rosterIdViewTestManagment', width : cell_width, editable : true,
							editrules:{edithidden:true}, viewable: false, editoptions:{readonly:true,size:10},
							summaryType : 'count', summaryTpl : '({0}) total',
							sortable : true, search : true,  hidden : true, hidedlg: true }, 
					
					{ name : 'courseSectionName', index : 'courseSectionName',label:'courseSectionNameViewTestManagment',
							width : cell_width, editable : true, editrules:{edithidden:true},
							viewable: true, editoptions:{readonly:true,size:10}, sortable : true,
							search : true, hidden : false, hidedlg : true },										

					{ name : 'attendanceSchoolIdentifier', index : 'attendanceSchoolIdentifier',label:'attendanceSchoolIdentifierViewTestManagment',
							width : cell_width, search : true,
							label: 'attendanceSchoolIdentifier', hidden : false },										
					
					{ name : 'attendanceSchoolName', index : 'attendanceSchoolName',label:'attendanceSchoolNameViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: true,
							editoptions:{readonly:true}, sortable : true, search : true, hidden : false,
                    	formatter: escapeHtml },

					{ name : 'educatorIdentifier', index : 'educatorIdentifier',label:'educatorIdentifierViewTestManagment',
							width : cell_width, editable : true, editrules:{edithidden:true},
							viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true,
                    	formatter: escapeHtml },
					
					{ name : 'educatorFirstName', index : 'educatorFirstName',label:'educatorFirstNameViewTestManagment', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: true,
							editoptions:{readonly:true,size:10}, search : true, hidden : true,
                    	formatter: escapeHtml },
					
					{ name : 'educatorLastName', index : 'educatorLastName',label:'educatorLastNameViewTestManagment',
							width : cell_width, editable : true, editrules:{edithidden:true},
							viewable: true, editoptions:{readonly:true, size:10}, search : true, hidden : true,
                    	formatter: escapeHtml },
							
		   			{ name : 'highStakesFlag', index : 'highStakesFlag',label:'highStakesFlagViewTestManagment', width : cell_width,
	  						editable : true, editrules:{edithidden:true}, viewable: false,
	  						editoptions:{readonly:true,size:10},
	  						sorttype : 'int', search : true, hidden: true, hidedlg: true },
	  						
		   			{ name : 'expiredFlag', index : 'expiredFlag',label:'expiredFlagViewTestManagment', width : cell_width,
	  						editable : true, editrules:{edithidden:true}, viewable: false,
	  						editoptions:{readonly:true,size:10},
	  						sorttype : 'int', search : true, hidden: true, hidedlg: true },
	  				{ name : 'testSessionSource', index : 'testSessionSource',label:'testSessionSourceViewTestManagment', width : cell_width,
	  	  						viewable: false, sorttype : 'int', search : false, hidden: true, hidedlg: true },
	  	  			
	  	  			<security:authorize access="hasRole('QUALITY_CONTROL_COMPLETE')">
							{ name : 'randomizationType', index : 'randomizationType',label:'randomizationTypeViewTestManagment',
								width : cell_width, editable : true, editrules:{edithidden:true},
								viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },
    				</security:authorize>
	  	  			<security:authorize access="!hasRole('QUALITY_CONTROL_COMPLETE')">
   						{ name : 'randomizationType', index : 'randomizationType',label:'randomizationTypeViewTestManagment',
   							width : cell_width, editable : true, editrules:{edithidden:true},
   							viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },
        			</security:authorize>
				];			    
		
		//JQGRID   
		//getRosterStudentsByTeacher1
		$grid.scb({
			//url : 'getTestSessionsByUser.htm?q=1',
			mtype: "POST",
			datatype : "local",				
			width: grid_width,
			filterstatesave:true,
			pagestatesave:true,
			colNames : [
	   					'Test Session Roster Id', 
	   					'Test Session Id',
	   					'Actions',
	   					'Test Session Name', 
	   					'Tickets',
	   					$('#currentAssessmentProgram').val()=="PLTW" ? 'Test Materials' : 'Test Information',
	   					'Test Collection Id',
	   					'Assessment Name', 
	   					'Test Progress',
	   					'Grade', 
	   					'Subject',
	   					'Testing Program',
	   					'Assessment Program',
	   					'Roster Id',
	   					'Roster',
	   					'School ID',
	   					'School Name', 
	   					'Educator Id',
	   					'Educator First Name',
	   					'Educator Last Name',
	   					'Highstake Flag',
	   					'Expired Flag',
	   					'Testsession Source',
	   					'Randomization Type'
	   		           ],
	   		colModel : cm,
	   		rowNum : 10,//30,
			rowList : [ 5,10, 20, 30, 40, 60, 90 ],
			pager : '#ptestSessionsTableId', 
	        sortname: 'testSessionName',
	       	sortorder: 'asc',
	       	/**
	       	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15032 : Test Management landing page - misc UI changes plus "view ticket"
	       	 * Refered from test co-ordination page.
	       	 * Needed a new column for multiple checkbox selection for view tickets functionality.
	       	 */
	       	columnChooser: false, 
	       	multiselect: true,
			footerrow : true,
			userDataOnFooter : true,
			beforeRequest: function() {
				
			
	    	  	//var saveGrid = $('#saveGrid').val(); 
				// if(($('#testSessionSuccessFlag').val() == 'editSession') && (flag == 1) && (saveGrid == 'save')) {
				if(!isTeacher && isNotBack && $(this).getGridParam('datatype') == 'json'){
					isNotBack = true;
					if(!$('#searchFilterForm').valid()) {return false;}
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
		    	//saveGridParameters($("#testSessionsTableId"));
		    	
		    	if($(this).getGridParam('datatype') == 'json') {
		   
		    		/**
		    		 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15032 : Test Management landing page - misc UI changes plus "view ticket"
		    		 * Refered from test co-ordination page.
		    		 * set all the selected check boxes to false.
		    		 */
		    		if(selectedTestMgtSessionRosterIds) {
		        		$.each(selectedTestMgtSessionRosterIds, function (index, value) {
		        			$("#testSessionsTableId").setSelection(value, false);
		        		});
		        	}
		    		
				    var recs = parseInt($("#testSessionsTableId").getGridParam("records"));				    
					if (isNaN(recs) || recs == 0) {
					     //$("#gbox_testSessionsTableId").hide();
					    // $("#noTSReport").html('No student records found.');
					     
					     //Set min height of 1px on no records found
					     //$('.jqgfirstrow').css("height", "1px");
					 } else {
					     $("#gbox_testSessionsTableId").show();
					     $("#noTSReport").html('');
					 }
					
					$('div.jqactions', $grid).on("mouseover",function(){
						
				
						$(this).addClass('ui-state-hover');
					}).on("mouseout",function(){
						$(this).removeClass('ui-state-hover');
					});
					
					var hasDeletePermission = false;
					var hasHighStakesPermission = false;
					<security:authorize access="hasRole('PERM_TESTSESSION_DELETE')" >
						hasDeletePermission = true;
					</security:authorize>
					<security:authorize access="hasRole('HIGH_STAKES')" >
						hasHighStakesPermission = true;
					</security:authorize>
				
					var rows = $grid.jqGrid('getRowData');
					for (var x = 0; x < rows.length; x++){
						
						var testSessionRosterId = rows[x].testSessionRosterId;
						var uiicon = 'ui-icon-alert';
						var title = 'Not Authorized to Deactivate Test Session';
						
						if((rows[x].highStakesFlag == "N" && hasDeletePermission) || 
								(rows[x].highStakesFlag == "Y" && hasDeletePermission && hasHighStakesPermission)){
							uiicon = 'ui-icon-trash';//scissors, closethick
							title = 'Delete Test Session';
						}  
						
						var deleteTestSessionSpan = $('<span>').addClass('ui-icon ' + uiicon).attr('title', title);
						
						if((rows[x].highStakesFlag == "N" && hasDeletePermission) || 
								(rows[x].highStakesFlag == "Y" && hasDeletePermission && hasHighStakesPermission)) {

							deleteTestSessionSpan = $('<span onkeypress=deleteTestSessionSpanclickfunction(event) onclick=deleteTestSessionSpanclickfunction(event) tabindex="0">').addClass('ui-icon ' + uiicon).attr('title', title);
						} else {
							
							deleteTestSessionSpan.addClass('ui-state-disabled');
						}
							
						$('#deleteTestSession_' + testSessionRosterId).append(deleteTestSessionSpan);
						
			    		if(rows[x].expiredFlag == "Y") {
					    	$("#"+testSessionRosterId).css("color","gray");
					    	$('a', "#"+testSessionRosterId).css("color","gray");
			    	    }
					}
					//Display the setuptestsession success message after settingup the testsession and editing the test session
					if($('#testSessionSuccessFlag').val() == 'setupTestSessionStudentDetails') {
						$("#messagesTestManagementTab").show();
						$('#test_session_edited').show();
						setTimeout("aart.clearMessages();$('#test_session_edited').text('');$('#test_session_edited').removeClass('info_message');$('#test_session_edited').removeClass('ui-state-highlight');", 3000);
					} else if($('#testSessionSuccessFlag').val() == 'setupTestSessionSessionInformation') {
						$("#messagesTestManagementTab").show();
						$('#test_session_created').show();
						setTimeout("aart.clearMessages();$('#test_session_created').text('');$('#test_session_created').removeClass('info_message');$('#test_session_created').removeClass('ui-state-highlight');", 3000);
						setTimeout(function(){ $("#messagesTestManagementTab").hide(); },3000);
					}
		    	}
		    	 var ids = $(this).jqGrid('getDataIDs');         
		         var tableid=$(this).attr('id');      
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title',$(this).getCell(ids[i], 'tcGradeCourseName')+' '+$($(this).getCell(ids[i], 'testSessionName')).text()+ ' Check Box');
		                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
		            }
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		             $('#cb_'+tableid).attr('title','Test Managment Grid Select All Check Box');
		             $('#cb_'+tableid).removeAttr('aria-checked');
		             $.each(objs, function(index, value) {         
		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                    $(value).attr('title',$(nm).text()+' filter');                          
		                    });
		             $('td[id^="view_"]').on("click",function(){
			       		  if($(".EditTable.ViewTable tbody").find('th').length==0){
			     			$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
			       		  }
			       		  });		    
		    },
		    /**
		     * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15032 : Test Management landing page - misc UI changes plus "view ticket"
		     * Refered from test co-ordination page.
		     * On select row and on select all functionality causes the respetive arrays to be reset..
		     */
		    onSelectRow: function(id, status) {
	    
        		//Add/remove items to/from selectedTestMgtSessions array based on checkbox selection/deselection. 
       		  		  
        		if(status) {
        			        		
					//User checks the test session.
					var gridRow = $('#testSessionsTableId').jqGrid('getRowData',id);
					selectedTestMgtSessions.push(gridRow['testSessionId']);
					selectedTestMgtSessionRosterIds.push(id);
					selectedTSAPMgtValues.push(gridRow['testSessionId'] + "-" + gridRow['assessmentProgramName']);
				} else {
					//User unchecks the test session.
					
					var gridRow = $('#testSessionsTableId').jqGrid('getRowData',id);

					selectedTestMgtSessions = $.grep(selectedTestMgtSessions, function(value) {
						return value != gridRow['testSessionId'];
					});
					
					selectedTestMgtSessionRosterIds = $.grep(selectedTestMgtSessionRosterIds, function(value) {
						return value != id;
					});
					
					selectedTSAPMgtValues = $.grep(selectedTSAPMgtValues, function(value) {
						return value != gridRow['testSessionId'] + "-" + gridRow['assessmentProgramName'];
					});
				}

        		//Check SelectAllCheckbox header on rowNum change.
        		var recordCount = $("#testSessionsTableId").getGridParam('reccount');
        		var checkboxChecckedCount = 0;
        		$("input[type='checkbox']").each(function() {
        			if(this.name != "" && 
        					this.name.indexOf("jqg_testSessionsTableId_") != -1 && 
        					this.checked) {
        				checkboxChecckedCount++;
        			}
	    	    });
	        	if(recordCount == checkboxChecckedCount) {
	        		$("#cb_testSessionsTableId").attr("checked", "checked");
	        	}
	        	if(selectedTestMgtSessions.length>0){
					$("#tmViewTSTicketsTopButton").prop("disabled",false);
					$('#tmViewTSTicketsTopButton').removeClass('ui-state-disabled');
				}else{
					$("#tmViewTSTicketsTopButton").prop("disabled",true);
					$("#tmViewTSTicketsTopButton").addClass('ui-state-disabled');
				}
			},
			onSelectAll: function(id, status) {
				if(status) {
					//User checks the test session.
					for(var i=0; i<id.length; i++) {
						var gridRow = $('#testSessionsTableId').jqGrid('getRowData',id[i]);
						selectedTestMgtSessions.push(gridRow['testSessionId']);
						selectedTestMgtSessionRosterIds.push(id[i]);
						selectedTSAPMgtValues.push(gridRow['testSessionId'] + "-" + gridRow['assessmentProgramName']);
					}
				} else {
					//User unchecks the test session.
					for(var i=0; i<id.length; i++) {
						var gridRow = $('#testSessionsTableId').jqGrid('getRowData',id[i]);
						
						selectedTestMgtSessions = $.grep(selectedTestMgtSessions, function(value) {
							return value != gridRow['testSessionId'];
						});
						
						selectedTestMgtSessionRosterIds = $.grep(selectedTestMgtSessionRosterIds, function(value) {
							return value != id[i];
						});
						
						selectedTSAPMgtValues = $.grep(selectedTSAPMgtValues, function(value) {
							return value != gridRow['testSessionId'] + "-" + gridRow['assessmentProgramName'];
						});
						
					}
				}
				if(selectedTestMgtSessions.length>0){
					$("#tmViewTSTicketsTopButton").prop("disabled",false);
					$('#tmViewTSTicketsTopButton').removeClass('ui-state-disabled');
				}else{
					$("#tmViewTSTicketsTopButton").prop("disabled",true);
					$("#tmViewTSTicketsTopButton").addClass('ui-state-disabled');
				}
			}
		});		

		$grid.jqGrid('navGrid', '#ptestSessionsTableId', {edit: false, add: false, del: false});

				//Clear the previous error messages
				setTimeout("aart.clearMessages()", 0);
			</security:authorize>
		</security:authorize>
		if(!isTeacher) {
			
			/**
			 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15032 : Test Management landing page - misc UI changes plus "view ticket"
			 * filter button is not needed.
			 */
	    	$('#grades, #testingPrograms, #contentAreas, #districtOrgs, #schoolOrgs, #managedbyCodeSelect,#randomizedCodeSelect,#editManagedbyCodeSelect,#editrandomizedCodeSelect').select2({
	    		placeholder:'Select',
	    		multiple: false,
	    		allowClear : true
	    	});
	    	
	    	filteringOrganization($("#districtOrgs"));
	    	filteringOrganization($("#schoolOrgs"));
	    	
	    		//initilizeAssessmentProgram();
	    		populateSearchFiltersData();
	    	
	    	
	    	
	    	$('#searchFilterForm').validate({
	    		ignore: "",
	    		rules: {
	    			testingPrograms: {required: true},
	    			<c:if test = "${user.accessLevel < 50}" >
	    				districtOrgs: {required: true},
	    			</c:if>
	    			schoolOrgs: {required: true}
	    		}
	    	});

			$('#searchBtn').on("click",function(e) {
				
				e.preventDefault();
				$('#searchFilterErrors').html("");
				$('#searchFilterMessage').html("").hide();
				$("#searchFilterForm").trigger("validate");
				if($('#searchFilterForm').valid()){
					setSearchFilterValuesToSession();
					loadTestMgmtTestSessions(true);
				}
				
			});
		}
		if(isTeacher){
			 $('#searchBtn').on("click",function(e) {
			 e.preventDefault();
			 setSearchFilterValuesToSession();
			 loadTestMgmtTestSessions(true);
		 	});
		 }
		
		$('#tmViewTSTicketsTopButton').on("click",function() {
			if (selectedTestMgtSessions !== undefined &&  selectedTestMgtSessions.length > 0) {
				window.location.href = 'getPDFTickets.htm?'+ 
						'&isAutoRegistered=false' +
						'&selectedTestSessions='+ selectedTestMgtSessions; 
			} else {
				$('#select_testsession_error').show();
				setTimeout("aart.clearMessages()", 3000);
			}
				
		}
		
		
		);
	
}

function resetTestManagmentTab(){
	$("#testSessionsTableId")[0].clearToolbar();
	$('.error').hide();
	if(!isTeacher){
		$('#searchFilterForm')[0].reset();
	}
	$("#testSessionsTableId").jqGrid("clearGridData", true);				
}

function reloadTMData(){
	if(($('#testingPrograms').val()!='' 
			&& $('#schoolOrgs').val()!='' && $('#districtOrgs').val()!='') || 
		(getFromSessionStorage("tmAssessmentProgramId")!=null && 
				getFromSessionStorage("tmTestingProgramId")!=null && 
				getFromSessionStorage("tmDistrictOrgId")!=null && 
				getFromSessionStorage("tmSchoolOrgId")!=null))
	{
		$('.error').hide();
		setTimeout(function(){ 
			$("#searchBtn").trigger("click");
		},2000);
		$("#testSessionsTableId").closest(".ui-jqgrid").find('.loading').hide();
	
	}
	
}

</script>