<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<input id="columnChooserGrid4" value="true" type="hidden" class="hidden" />
<c:if test="${!TMTabAccessFlag}">
	<div class="panel_full noBorder">
		<span id="test_mgmt_no_access" class="info_message">
			${TMTabAccessFailedReason} </span>
	</div>
</c:if>

<c:if test="${TMTabAccessFlag}">
<div>
	<ul id="viewTestManagementID" class="nav nav-tabs sub-nav">
		<li class="nav-item">
			<a class="nav-link" href="#tabs_testManagement" data-toggle="tab" role="tab"><fmt:message key="label.testmanagement.viewTestSession" /></a>
		</li>
		<security:authorize access="hasRole('PERM_TESTSESSION_CREATE')">
			<li class="nav-item">
				<a class="nav-link" href="setupTestSession.htm"  role="tab"><fmt:message key="label.testmanagement.addnewtest" /></a>
			</li>
		</security:authorize>
		<security:authorize access="hasRole('PERM_QC_TESTSESSION_CREATE')">
			<li class="nav-item">
				<a class="nav-link" href="setupTestSession.htm?qcTest=QC"  role="tab"><fmt:message key="label.testmanagement.createQcTestSessions" /></a>
			</li>
		</security:authorize>
	</ul>
	
	<div id="content" class="tab-content">
		<div id="tabs_testManagement" class="tab-pane" role="tabpanel">
			<jsp:include page="testManagementTab.jsp"/>
		</div>
	</div>
</div>
<div id="deactivate-testsession-confirm" style="display: none;">
		<div id="deactivateTestSessionMessage"></div>
</div>
<script>
$(function(){
	<%-- navigate to the first available tab --%>
	$('#viewTestManagementID li.nav-item:first a').tab('show');
});
</script>

<script type="text/javascript">

//Global variable to capture the mouse position for 
//"Loading...." message display.
var flag = 1;
var isTeacher = false;
var isNotBack = true;
<c:if test="${user.teacher}">
	isTeacher = true;
</c:if>

/**
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15032 : Test Management landing page - misc UI changes plus "view ticket"
 * Refered from test co-ordination page.
 * Added below arrays to contain the data required for the view tickets funtionality in test session management screen.
 */

 
var selectedTestMgtSessions = [];
var selectedTSAPMgtValues = [];
var selectedTestMgtSessionRosterIds = [];
var selected_PERM_OTW_VIEW = false;
$(function() {
	var firstLoadTestManagementTab = true;
	var firstLoadInstructionalSupportTab = true;
	var firstLoadViewAutoRegistrationTestSessions = true;
	var firstLoadViewOperationalTestWindow = true;
	var firstProjectedTesting =  true;
	<c:if test="${param.showPage != 'TM'}">
		resetTestManagmentTab();
		clearTMSearchFilterValuesFromSession();
	</c:if>
	$("#messages").hide();
		$('#setupTestSessionManagementNavigationTabs').tabs({
				activate: function(event, ui) {
					
					if(!firstLoadTestManagementTab)		
					{
						resetTestManagmentTab();
						clearTMSearchFilterValuesFromSession();
					}
					
					if(!firstLoadViewAutoRegistrationTestSessions)		
						resetTestCoordination();
					
					if(ui.newPanel[0].id == 'tabs_testManagement') {
						var path='testManagementTab';
						if(firstLoadTestManagementTab){
							$('#tabs_testManagement').load("viewTestSessions.htm?path="+path, function() {
								resetTestManagmentTab();
								<c:if test="${param.showPage != 'TM'}">
									clearTMSearchFilterValuesFromSession();
								</c:if>
								$('#breadCrumMessage').text("Test Management");
								$('#breadCrumMessageTag').text("Setup, manage and monitor all assessments and sessions.");				
								//window.localStorage.removeItem("ARTSgridParamsData");
								var loaded = $('#tabs_testManagement').prop('loaded') != undefined ? $('#tabs_testManagement').prop('loaded') : false;
							});
							firstLoadTestManagementTab = false;
						}							
					} else if(ui.newPanel[0].id == 'tabs_instructionalSupport') {
						if(firstLoadInstructionalSupportTab){
						var path='instructionalSupportTab';
						$('#tabs_instructionalSupport').load("viewTestSessions.htm?path="+path, function() {
							$('#breadCrumMessage').text("Instructional Tools");
							$('#breadCrumMessageTag').text("Plan instruction and assessment, confirm test assignment, and view history of instructional plans.");
							initITIStudentRostersIS();
							resetInstructionalToolsTab();
							});
								firstLoadInstructionalSupportTab = false;
						}else{
							resetInstructionalToolsTab();
						}
					} else if(ui.newPanel[0].id == 'tabs_ticketing') {						
						<c:if test="${param.showPage == 'TC'}">
							if(!firstLoadViewAutoRegistrationTestSessions)
								firstLoadViewAutoRegistrationTestSessions = true;
							else 
								firstLoadViewAutoRegistrationTestSessions = false;
						</c:if>
					
						if(firstLoadViewAutoRegistrationTestSessions){
							var path='testCoordination';
							$('#tabs_ticketing').load("viewTestSessions.htm?path="+path, function() {
								$('#breadCrumMessage').text("Test Coordination");
								$('#breadCrumMessageTag').text("");
								window.localStorage.removeItem("ARTSgridParamsData");							
								testCoordinationInit();
								resetTestCoordination();
							});								
							
						}else{
							var path='testCoordination';
							$('#tabs_ticketing').load("viewTestSessions.htm?path="+path, function() {
								$('#breadCrumMessage').text("Test Coordination");
								$('#breadCrumMessageTag').text("");
								window.localStorage.removeItem("ARTSgridParamsData");
								if(getFromSessionStorage("tsAssessmentProgramId")!=null && 
										getFromSessionStorage("tsTestingProgramId")!=null && 
										getFromSessionStorage("tsDistrictOrgId")!=null && 
										getFromSessionStorage("tsSchoolOrgId")!=null)
									reLoadARTSData();
								
							});				
							
						}								
					}  else if(ui.newPanel[0].id == 'tabs_viewOperationalTestWindow') {
						
						if(firstLoadViewOperationalTestWindow){
						var path='viewOperationalTestWindow';
						$('#tabs_viewOperationalTestWindow').load("viewTestSessions.htm?path="+path, function() {
							resetViewOperationalTestWindow();
						});
							firstLoadViewOperationalTestWindow = false;
						}else{
							resetViewOperationalTestWindow();
						}
					}else if(ui.newPanel[0].id == 'tabs_viewProjectedTesting') {						
						if(firstProjectedTesting){
							var path='viewProjectedTesting';
							$('#tabs_viewProjectedTesting').load("viewTestSessions.htm?path="+path, function() {
								
							});
							firstProjectedTesting = false;
							}else{
								
								$("#projectedTestingGraph").show();
								$("#uploadProjectedTestingScr").hide();
								$("#viewMyCalendarPage").hide();
							}
					}
				}
			});
			
			
			<c:if test="${param.showPage == 'TM'}">
				$("#setupTestSessionManagementNavigationTabs").tabs("option", "active", "#tabs_testManagement");
				if(!isTeacher){
					var path='testManagementTab';
					$('#tabs_testManagement').load("viewTestSessions.htm?path="+path, function() {
						$('#breadCrumMessage').text("Test Management");
						$('#breadCrumMessageTag').text("Setup, manage and monitor all assessments and sessions.");				
						reloadTMData();
					});
				}
			</c:if>
		});

		function loadTestMgmtTestSessions(backButtonFlag) {
			var assessmentProgramId = "";
			var testingProgramId = "";
			var contentAreaId = "";
			var gradeCourseId = "";
			var schoolOrgId = "";
			var showExpiredFlag = "";
			var completedFlag="";
			
			/**
			 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15032 : Test Management landing page - misc UI changes plus "view ticket"
			 * Refered from test co-ordination page.
			 * Refresh the test session and assessment programs maps on each load.
			 */
			selectedTestMgtSessions = [];
			selectedTSAPMgtValues = [];
			selectedTestMgtSessionRosterIds = [];
			
			var criteriaValid = isTeacher; // teacher doesn't have search criteria, so it's automatically valid
			
			if(!isTeacher) {
				assessmentProgramId = getFromSessionStorage("tmAssessmentProgramId");
				testingProgramId = getFromSessionStorage("tmTestingProgramId");
				contentAreaId = getFromSessionStorage("tmContentAreaId");
				gradeCourseId = getFromSessionStorage("tmGradeCourseId");
				schoolOrgId = getFromSessionStorage("tmSchoolOrgId");
				showExpiredFlag = getFromSessionStorage("tmShowExpiredFlag");
				completedFlag = getFromSessionStorage("tmCompletedFlag");
				
				criteriaValid = assessmentProgramId != null && assessmentProgramId != '' &&
					testingProgramId != null && testingProgramId != '' &&
					schoolOrgId != null && schoolOrgId != '' &&
					contentAreaId != null && gradeCourseId != null;
			}
			else {
			 	completedFlag = getFromSessionStorage("tmCompletedFlag");
				}
			if(criteriaValid){
				var $grid = $("#testSessionsTableId");
				//Clear the previous error messages
				setTimeout("aart.clearMessages()", 0);
				
				if(!backButtonFlag) {
					var pdata = $grid.getGridParam("postData");
					if(typeof pdata  == 'undefined' || typeof pdata.filters == 'undefined') {
						pdata = {
							filters: ""
						};
					}
					
					pdata.assessmentProgramId =assessmentProgramId;
					pdata.testingProgramId = testingProgramId;
					pdata.contentAreaId = contentAreaId;
					pdata.gradeCourseId = gradeCourseId;
					pdata.schoolOrgId = schoolOrgId;
					pdata.showExpired = showExpiredFlag;
					pdata.includeCompletedTestSession=completedFlag;
					$grid.jqGrid('setGridParam',{
						datatype:"json", 
						url : 'getTestSessionsByUser.htm?q=1', 
						search: false,
						postData: pdata
					}).trigger("reloadGrid");
				} else {
					$grid.jqGrid('setGridParam',{
						datatype:"json", 
						url : 'getTestSessionsByUser.htm?q=1', 
						search: false, 
						postData :  {
							filters: "",
							assessmentProgramId : assessmentProgramId,
							testingProgramId : testingProgramId,
							contentAreaId : contentAreaId,
							gradeCourseId : gradeCourseId,
							schoolOrgId : schoolOrgId,
							showExpired : showExpiredFlag,
							includeCompletedTestSession: completedFlag
						}
					}).trigger("reloadGrid",[{page:1}]);	
				}
			}
		}
		
		function checkTestSessionDeletable(testSessionId) {
			$.ajax({
	            url: 'checkTestSessionDeletable.htm',
	            data: {
	            	testSessionId : testSessionId
	            },
	            type: "POST",
	            success : function(response) {
	            	
	    			if(response.result == 'Deletable') {	    					    			
						$("#deactivate-testsession-confirm").dialog({
							title: 'Remove Test Session',
							resizable: false,
							height: 175,
							width: 375,
							modal: true,
							autoOpen:false,
							buttons: {
							    Continue: function() {
							    	deleteTestSession(testSessionId);
							        $(this).dialog("close");
							    },
							    Cancel: function() {
							        $(this).dialog("close");
							    }
							}
						});
						
						$("#deactivateTestSessionMessage", $("#deactivate-testsession-confirm")).html('<fmt:message key="label.testmanagement.deactivatetestsession.confirm"/> </label>');
						$("#deactivate-testsession-confirm").dialog('open');
						
	    			}  else {
						alert('Unable to Delete Test Session as Students Tests are In Progress or Completed status.');
					}	    			
	            },
	            error : function(response) {
	            	alert('Not able to delete selected test session');
	            }
	        });	
		}
		
		function deleteTestSession(testSessionId) {
			$.ajax({
	            url: 'deactivateTestSession.htm',
	            data: {
	            	testSessionId : testSessionId
	            },
	            type: "POST",
	            success : function(response) {
	    			// reload the grid
	    			if(response.result == 'deactivated') {
	    				$("#viewmodtestSessionsTableId #cData").trigger("click");	
	    				$('#testSessionsTableId').jqGrid('setGridParam',{
							datatype:"json", 
							url : 'getTestSessionsByUser.htm?q=1', 
							search: false
						}).trigger("reloadGrid",[{page:1}]);
	    			}
	            },
	            error : function(response) {
	            	alert('Not able to deactivate selected test session');
	            }
	        });			
		}

		//Custom formatter for lastname link. 
		function printTicketLinkFormatter(cellvalue, options, rowObject) {
			var htmlString = '<div title="You do not have permission to view tickets."><img class="ui-state-disabled" alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png"></div>';
			<security:authorize access="hasRole('PERM_TESTTICKET_VIEW')" >
			htmlString = '<a class="pdfLink" href="getPDFTickets.htm?'+
					'assessmentProgramName=' + rowObject[12] + 
					'&testSessionId='+ rowObject[1] + 
					'&testCollectionId='+ rowObject[6] + 
					'">' + '<img alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png">' + '</a>';
			</security:authorize>
		    return htmlString;
		}

		//Custom unformatter for lastname link.
		function printTicketLinkUnFormatter(cellvalue, options, rowObject) {
		    return rowObject[1];
		}
		function testInfoLinkFormatter(cellvalue, options, rowObject) {
			var mediaPath = "${applicationScope['nfs.url']}";
			
			var lval = '';
			if(cellvalue==null || typeof cellvalue  == 'undefined' || cellvalue.length==0 ){
				lval = '';
			} else {
				var carr = cellvalue.split(',');
				$.each(carr, function(index, item) {
					var iarr = item.split('`--|-!');
					if(iarr[2] == 'pdf')
						lval = lval + '<a  target="_blank" style="display:inline-block;" href="' + mediaPath + iarr[0] + '">' + '<img alt="'+ iarr[1] +'" style="border:0px solid;" src="images/pdf.png">' + '</a>';
					else if(iarr[2] == 'UCB' || iarr[2] == 'UEB' || iarr[2] == 'EBAE')
						lval = lval + '<a  target="_blank" style="display:inline-block;" href="' + mediaPath + iarr[0] + '">' + '<img alt="'+ iarr[1] +'" style="border:0px solid;" src="images/test/braille_25_25.png">' + '</a>';
				
				});			
			} 
			return lval;	    
		}


		//Custom unformatter for lastname link.
		function testInfoLinkUnFormatter(cellvalue, options, rowObject) {
		    return;
		}

		//Custom formatter for edit test session link. 
		function editTestSessionLinkFormatter(cellvalue, options, rowObject) {
			var htmlString = "N.A";
			htmlString = '<a class="link" href="setupTestSession.htm?'+
					'testSessionId='+ rowObject[1] + '&testSessionName='+ escapeHtml(rowObject[3]) + '&highStakesFlag='+ rowObject[20] +
					'&testCollectionId='+ rowObject[6] + '&source='+ rowObject[22] + '&rosterId='+ rowObject[13] + '&testingProgram='+$("#testingPrograms option:selected").text() +'">' + escapeHtml(cellvalue) + '</a>';
		    return htmlString;	
		}

		//Custom unformatter for lastname link.
		function editTestSessionLinkUnFormatter(cellvalue, options, rowObject) {
		     return rowObject[3];
		}
		function saveGridParameters(grid) {  
			var gridInfo = new Object();
		    gridInfo.url = grid.jqGrid('getGridParam', 'url');
		    gridInfo.sortname = grid.jqGrid('getGridParam', 'sortname');
		    gridInfo.sortorder = grid.jqGrid('getGridParam', 'sortorder');
		    gridInfo.selrow = grid.jqGrid('getGridParam', 'selrow');
		    gridInfo.page = grid.jqGrid('getGridParam', 'page');
		    gridInfo.rowNum = grid.jqGrid('getGridParam', 'rowNum');
		    gridInfo.postData = grid.jqGrid('getGridParam', 'postData');
		    gridInfo.search = grid.jqGrid('getGridParam', 'search');

		    $('#gridParams').val(JSON.stringify(gridInfo));
		    window.localStorage.setItem("gridParamsData", JSON.stringify(gridInfo));
		}
		
		//execute this on back button click.
		function loadARTSGrid() {
			$('#testCoordinationBackDiv').addClass("hidden");
			$('#autoRegisteredTestSessionsDiv').show();
			$('#autoRegisteredTSStudentsDiv').hide();
			$('#breadCrumMessage').text("Test Coordination");
			$('#breadCrumMessageTag').text("");
		}
		
		function initilizeAssessmentProgram() {
			$('#testingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#contentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
			$('#grades, #testingPrograms, #contentAreas').trigger('change.select2');
			var assessmentProgramId = $('#hiddenCurrentAssessmentProgramId').val();
			$('#grades, #contentAreas').prop('disabled', true);
			if (assessmentProgramId != 0) {
				$.ajax({
			        url: 'getDynamicTestingPrograms.htm',
			        data: {
			        	assessmentProgramId: assessmentProgramId
			        	},
			        dataType: 'json',
			        type: "POST",
			        success: function(testingPrograms) {
			        	
			        	$('#testingPrograms').html("");		
		        		$('#testingPrograms').append($('<option></option>').val('').html('Select'));
						$.each(testingPrograms, function(i, testingProgram) {
							$('#testingPrograms').append($('<option></option>').attr("value", testingProgram.id).text(testingProgram.programName));
						});
						
						var filterSelectedValue = getFromSessionStorage("tmTestingProgramId");				
						if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
							$("#testingPrograms").val(filterSelectedValue);
							$("#testingPrograms").trigger('change');
						} else if (testingPrograms.length == 1) {
							$("#testingPrograms option").removeAttr('selected').next('option').attr('selected', 'selected');
							$("#testingPrograms").trigger('change');
						}
						$('#testingPrograms').trigger('change.select2');
			        }
				});
				$('#testingPrograms').on("change",function() {
					$('#contentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					$('#contentAreas').trigger('change.select2');
					$('#contentAreas').prop('disabled', false);
					$('#grades').prop('disabled', true);

					$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					$('#grades').trigger('change.select2');
					var testingProgramId = $('#testingPrograms').val();
					$.ajax({
				        url: 'getContentAreasByAssessmentProgramandTestProgramId.htm',
				        data: {
				        	assessmentProgramId: assessmentProgramId,
				        	testingProgramId : testingProgramId
				        	},
				        dataType: 'json',
				        type: "POST",
				        success: function(contentAreas) {
				        	$('#contentAreas').html("");	
				        	$('#grades').html("");
			        		$('#contentAreas').append($('<option></option>').val('').html('Select'));
			        		$('#grades').append($('<option></option>').val('').html('Select'));			        	

							$.each(contentAreas, function(i, contentArea) {
								$('#contentAreas').append($('<option></option>').attr("value", contentArea.id).text(contentArea.name));
							});
							
							var filterSelectedValue = getFromSessionStorage("tmContentAreaId");				
							if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
								$("#contentAreas").val(filterSelectedValue);
								$("#contentAreas").trigger('change');
							}
							/*
							if (contentAreas.length == 1) {
								$("#contentAreas option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#contentAreas").trigger('change');
							}*/
							$('#contentAreas').trigger('change.select2');
				        }
					});
				});
			}
			$('#grades, #testingPrograms, #contentAreas').trigger('change.select2');
		}
		
		function populateSearchFiltersData() {
			var $grid = $("#testSessionsTableId");
			var pdata = $grid.getGridParam("postData");
			
			var optionText='';
			$('.messages').html('').hide();
			
			initilizeAssessmentProgram();
			
			<c:if test="${user.accessLevel < 50 }">
				var districtOrgSelect = $('#districtOrgs');
				$.ajax({
					url: 'getChildOrgsWithParentForFilter.htm',
					dataType: 'json',
					data: {
						orgId : ${user.currentOrganizationId},
			        	orgType:'DT'
			        	},				
					type: "POST",
					success: function(districtOrgs) {				
						if (districtOrgs !== undefined && districtOrgs !== null && districtOrgs.length > 0) {
							$.each(districtOrgs, function(i, districtOrg) {
								optionText = districtOrgs[i].organizationName;
								districtOrgSelect.append($('<option></option>').val(districtOrg.id).html(optionText));
							});
							
							var filterSelectedValue = getFromSessionStorage("tmDistrictOrgId");				
							if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
								districtOrgSelect.val(filterSelectedValue);
		    					$("#districtOrgs").trigger('change');
							} else if (districtOrgs.length == 1) {
								districtOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
		    					$("#districtOrgs").trigger('change');
		    				}
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
						}
						$('#schoolOrgs, #districtOrgs').trigger('change.select2');
					}
				});
				
				$('#districtOrgs').on("change",function() {
				
					$('#schoolOrgs').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					$('#schoolOrgs').trigger('change.select2');
					var districtOrgId = $('#districtOrgs').val();
					if (districtOrgId != 0) {
						$.ajax({
					        url: 'getChildOrgsWithParentForFilter.htm',
					        data: {
					        	orgId : districtOrgId,
					        	orgType:'SCH'
					        	},
					        dataType: 'json',
					        type: "POST",
					        success: function(schoolOrgs) {
					        					        	
								$.each(schoolOrgs, function(i, schoolOrg) {
									$('#schoolOrgs').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
								});
								
								var filterSelectedValue = getFromSessionStorage("tmSchoolOrgId");				
								if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
									$("#schoolOrgs").val(filterSelectedValue);
			    					$("#schoolOrgs").trigger('change');
								} else if (schoolOrgs.length == 1) {
									$("#schoolOrgs option").removeAttr('selected').next('option').attr('selected', 'selected');
									$("#schoolOrgs").trigger('change');
								}							
								$('#schoolOrgs').trigger('change.select2');
					        }
						});
					}
				});	
			</c:if>
			
			<c:if test="${user.accessLevel >= 50 && !user.teacher}">				
				var schoolOrgSelect = $('#schoolOrgs');
				$.ajax({
					url: 'getChildOrgsWithParentForFilter.htm',
					dataType: 'json',
					data: {
						orgId : ${user.currentOrganizationId},
			        	orgType:'SCH'
			        	},
					type: "POST",
					success: function(schoolOrgs) {				
						if (schoolOrgs !== undefined && schoolOrgs !== null && schoolOrgs.length > 0) {
							$.each(schoolOrgs, function(i, schoolOrg) {
								optionText = schoolOrgs[i].organizationName;
								schoolOrgSelect.append($('<option></option>').val(schoolOrg.id).html(optionText));
							});
							
							var filterSelectedValue = getFromSessionStorage("tmSchoolOrgId");				
							if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
								schoolOrgSelect.val(filterSelectedValue);
								schoolOrgSelect.trigger('change');
							} else if (schoolOrgs.length == 1) {
								schoolOrgSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
								schoolOrgSelect.trigger('change');
		    				}
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrors').html("No School Organizations Found for the current user").show();
						}
						$('#schoolOrgs').trigger('change.select2');
					}
				});
			</c:if>
			
			
			$('#contentAreas').on("change",function() {
							
				$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
				$('#grades').trigger('change.select2');

				$('#grades').prop('disabled', true);

				var assessmentProgramId = $('#hiddenCurrentAssessmentProgramId').val();
				var testingProgramId = $('#testingPrograms').val();
				var contentAreaId = $('#contentAreas').val();
				if (contentAreaId != 0) {
					$.ajax({
				        url: 'getGradeCourseByContentAreaIdForTestManagement.htm',
				        data: {
				        	assessmentProgramId:assessmentProgramId,
				        	testingProgramId:testingProgramId,
				        	contentAreaId: contentAreaId
				        	},
				        dataType: 'json',
				        type: "GET",
				        success: function(grades) {

				        	$('#grades').html("");		
				        	$('#grades').append($('<option></option>').val('').html('Select'));				        	

							$.each(grades, function(i, grade) {
								$('#grades').append($('<option></option>').attr("value", grade.id).text(grade.name));
							});
							$('#grades').prop('disabled', false);

							var filterSelectedValue = getFromSessionStorage("tmGradeCourseId");				
							if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
								$("#grades").val(filterSelectedValue);
								$("#grades").trigger('change');
								
							}							
							/*
							if (grades.length == 1) {
								$("#grades option").removeAttr('selected').next('option').attr('selected', 'selected');
								$("#grades").trigger('change');
							}*/					
							$('#grades').trigger('change.select2');
				        }
					});				
				}
			});

		}
		

		
		function clearTMSearchFilterValuesFromSession() {
			
			removeInSessionStorage("tmAssessmentProgramId");
			removeInSessionStorage("tmTestingProgramId");
			removeInSessionStorage("tmContentAreaId");
			removeInSessionStorage("tmGradeCourseId");
			removeInSessionStorage("tmDistrictOrgId");
			removeInSessionStorage("tmSchoolOrgId");
			removeInSessionStorage("tmShowExpiredFlag");
			removeInSessionStorage("tmCompletedFlag");
		}
		
		function setSearchFilterValuesToSession() {
			
			setInSessionStorage("tmAssessmentProgramId", $('#hiddenCurrentAssessmentProgramId').val());
			setInSessionStorage("tmTestingProgramId", $('#testingPrograms').val());
			setInSessionStorage("tmContentAreaId", $('#contentAreas').val());
			setInSessionStorage("tmGradeCourseId", $('#grades').val());
			setInSessionStorage("tmDistrictOrgId", $('#districtOrgs').val());
			setInSessionStorage("tmSchoolOrgId", $('#schoolOrgs').val());
			<c:if test="${user.accessLevel > 50 }">
				setInSessionStorage("tmSchoolOrgId", $('#hiddenCurrentOrganizationId').val());
			</c:if>
			setInSessionStorage("tmShowExpiredFlag", $('#showExpired').prop('checked'));
			setInSessionStorage("tmCompletedFlag", $('#showCompleted').prop('checked'));
		}		
		
		function setInSessionStorage(storageItemName, storageItemValue) {
			window.sessionStorage.setItem(storageItemName, storageItemValue);			
		}

		function removeInSessionStorage(storageItemName) {
		    window.sessionStorage.removeItem(storageItemName);
		}
		
		function getFromSessionStorage(storageItemName) {
			var itemValue = window.sessionStorage.getItem(storageItemName);
			if(typeof itemValue != 'undefined' && itemValue != null) {
				return itemValue;
			}
			
			return null;
		}
		
		/**
		 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15032 : Test Management landing page - misc UI changes plus "view ticket"
		 * Refered from test co-ordination page.
		 * When user clicks on the view tickets button in test session management page, he/she gets a pdf file with all the information per selection.
		 */
		
		function deleteTestSessionSpanclickfunction(e){
			e.stopPropagation();
			var id = $(e.currentTarget.parentElement.parentElement).data('testsessionid');
			checkTestSessionDeletable(id);																		
		}
		
	</script>
</c:if>