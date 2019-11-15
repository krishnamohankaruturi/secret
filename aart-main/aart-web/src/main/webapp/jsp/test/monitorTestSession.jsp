<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
	
	#assessmentProgramDiv .ui-widget, .testSearch .ui-widget {
	    font-size: 1.0em;
	}
	
	#title {
	    font-weight: bold;
	    color: #14407C;
	    font-size: 1.2em;
	}
	
	.selectable, .clearSelection {
	    border-bottom: 1px solid #000;
	}
	
	.selectable:hover, .selected:hover, .clearSelection:hover {
	    cursor: pointer;
	    background-color: #DFEFFC;
	}
	
	.selected {
	    border-bottom: 1px solid #000;
	    background-color: #FDFD9C;
	}
	
	.ui-menu {
	    min-height: 37px;
	    max-height: 200px;
	    overflow-y: scroll;
	}
	
	#no_results_msg {
	    font-size: 1.5em;
	    color: #2E6E9E;
	}
	
	.ui-accordion-content {
	    height: 400px;
	}
	
	#accordionParent {	
	    overflow-y: auto;
	}
	
	#accordionParent {
	    overflow-y: auto;
	    margin-bottom: 5px;
	}
	
	#accordionParent .ui-accordion.ui-accordion-content {
	    max-height: 900px;
	}
	
	#setup_test_session {
	    margin-top: 15px;
	    margin-bottom: 5px;    
	}
	
	.clearDiv {
	    clear: both;
	}
	
	.cpass {
	    display: none;
	}
	#cpassDiv {
	    margin: 10px;
	}
	
	#cpassDiv div {
	    margin: 5px;
	    float:left;
	}
	
	#cpass_select {
	    width: 250px;
	}
	
	.messages {
	    margin-bottom: 5px;
	}
	.header {
	    float: left;
	    margin-right: 100px;
	    width:100%;
	}
	
	.underline {
	    text-decoration: underline;
	}
	
	.col_wrapper {
		width: 800px;
	
	}
	
	.left-col {
		float: left; 
	  	text-align: right; 
	  	width: 500px;
	}
	
	.right-col {
	  	float: right;  
	  	text-align: left; 
	  	width: 300px;
	}
	
	.link:hover, .cancelTestSession:hover {
	    cursor: pointer;
	}
	
	.tableth
	{
		border:1px solid;
		align:center;
		border-radius:3px;
		background:   #DFEFFC;
		text-align: center;
	}
	
	.tabletd
	{
		border:1px solid;
		align:center;
		border-radius:3px;
		text-align: center;		
	}
	
	.tableroweven {
	 	background-color: #EBEAEB;
	}
	
	/* .accordion {
		width: 100%;
		border: 1px solid #ccc;
		border-bottom: none;
		font-family: Arial, Helvetica, sans-serif;
		font-size: 14px;
	} */
	
	/* .accordionheader {
		background: #DFEFF0;
		border-radius: 5px;
		cursor: pointer;
		padding: 8px 4px;
		font-size: 14px; 
	} */
	
	.testsessionlabel {
		text-decoration: none;
	}
	
	/* .testsessionbody {
		border-top: none;
		padding: 2% 4%;
		padding-bottom: 6%;
		display: none;
		height: 230px;
		width: 900px;
		overflow: scroll;		
	} */
	
	
</style>

<div class="pageContent">
    <div>
        <div class="messages">
        	<span class="error_message ui-state-error hidden" id="sys_error"><fmt:message key="error.generic"/></span>
        	<span class="error_message ui-state-error hidden" id="noAssessmentProgram"><fmt:message key="error.noAssessmentProgram"/></span>
        	<span class="error_message ui-state-error hidden" id="noTestingProgram"><fmt:message key="error.noTestingProgram"/></span>
        	<span class="error_message ui-state-error hidden" id="noAssessment"><fmt:message key="error.noAssessment"/></span>
            <span id="end_test_session_success" class="info_message ui-state-highlight hidden"><fmt:message key='label.endtestsession.success'/></span>
            <span id="end_test_session_error" class="error_message ui-state-error hidden"><fmt:message key='label.endtestsession.error'/></span>            
            <span id="no_endtestsession_params" class="error_message ui-state-error hidden"><fmt:message key='error.endtestsession.no.params'/></span>
            <span id="reactivation_success" class="info_message ui-state-highlight hidden"><fmt:message key='label.monitortestsession.reactivation.success'/></span>            
            <span id="reactivation_error" class="error_message ui-state-error hidden"><fmt:message key='label.monitortestsession.reactivation.error'/></span>
            <span id="no_reactivate_params" class="error_message ui-state-error hidden"><fmt:message key='error.monitortestsession.sectionstatus.no.params'/></span>
            <span id="noRosters" class="error_message ui-state-error hidden"><fmt:message key='error.testsession.norosters'/></span>                        
            <span id="notestsessions" class="error_message ui-state-error hidden"><fmt:message key='error.testsession.notestforroster'/></span>           
            <span id="notestresults" class="error_message ui-state-error hidden"><fmt:message key='error.testsession.notestresultsfortestsession'/></span>
            <security:authorize access="!hasRole('PERM_TEST_SEARCH')"><span class="ui-state-error"><fmt:message key='error.common.permissiondenied.test.search'/></span></security:authorize>
            <security:authorize access="!hasRole('PERM_ROSTERRECORD_VIEW')"><span class="ui-state-error"><fmt:message key='error.common.permissiondenied.roster.view'/></span></security:authorize>
            <security:authorize access="!hasRole('PERM_TESTSESSION_VIEW')"><span class="ui-state-error"><fmt:message key='error.common.permissiondenied.testsession.view'/></span></security:authorize>
            <security:authorize access="!hasRole('PERM_TESTSESSION_MONITOR')"><span class="ui-state-error"><fmt:message key='error.common.permissiondenied.testsession.monitor'/></span></security:authorize>
        </div>
        <div class="header">
           <label id="title"><fmt:message key='label.tests.monitortestsession'/></label>
        </div>
        <br/>        
		
		<div id="wrapper" class="col_wrapper">
			<div id="left-col" class="left-col">
	 			<label for="assessmentProgramSelect"><fmt:message key="label.tests.search.assessmentprogram"/></label>  
		               <br/>
		      	<label for="testingProgramSelect" class="hidden testingProgramBlock"><fmt:message key="label.tests.search.testingprogram"/></label> 
		               <br/>
		     	<label for="assessmentSelect" class="hidden assessmentBlock"><fmt:message key="label.tests.search.assessment"/></label>	
			</div>
	 
			<div id="right-col" class="right-col">
				<c:choose>
					<c:when test="${fn:length(assessmentPrograms) > 0 }">
						<select id="assessmentProgramSelect">
						    <option value=""><fmt:message key="label.common.select"/></option>
							<c:forEach items="${assessmentPrograms }" var="assessmentProgram" varStatus="index">
								<option value="${assessmentProgram.id }">${assessmentProgram.assessmentProgram.programName }</option>
							</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<span class="ui-state-error"><fmt:message key="error.tests.noassessmentprogramsfororg"/></span>
					</c:otherwise>
				</c:choose>
				<br/>
				<select id="testingProgramSelect" class="hidden testingProgramBlock">
				         <option value="-1"><fmt:message key="label.common.select"/></option>
				</select>
				<br/> 
				<select id="assessmentSelect" class="hidden assessmentBlock">
				         <option value="-1"><fmt:message key="label.common.select"/></option>
				</select> 
			</div>	 
		</div>
    </div>
  	
  	<br/> <br/> <br/> <br/>
    <hr>

    <div>
        <div id="accordionParent" class="testSearch cpass hidden">
            <div id="accordion">                
            </div>           
        </div>                      
    </div>        
    <div id="loading_overlay">
           <fmt:message key="label.common.loading"/>
           <br/>
           <img src="<c:url value='/images/ajax-loader-big.gif'/>"/>
    </div> 
    <div id="question_overlay">
           <label id="question"> </label>           
    </div>
</div>

<script>

	$(function() {
		
		$('#assessmentProgramSelect option').removeAttr('selected');
		
	    $('#loading_overlay').dialog({
	        height: 200,
	        width: 200,
	        modal: true,
	        autoOpen: false
	    });
	    
	    $('#question_overlay').dialog({
	        height: 500,
	        width: 600,
	        modal: true,
	        autoOpen: false
	    });
	
	    $('#session_name_overlay').dialog({
	    	modal: true,
	    	autoOpen: false,
	    	height: 320,
	    	width: 450
	    });
	    
	    $('.clearSelection').click(function() {
	    	$(this).siblings('.selected').removeClass('selected').addClass('selectable');
	    });           
	    	    
	    aart.clearMessages = function() {
			var msg = $('.success_message, .error_message, .info_message');
		    
		    msg.each(function(){
		        if ($(this).css("display") !== "none") {
		            $(this).fadeOut(500);
		            $(this).hide();
		        }
		    });
		};
				
		closeQuestionDialog = function() {
			$('#question_overlay').dialog("close");
		};
	   
	    <security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW')">
	    aart.buildRosterAccordion("#accordion", function() {
	    	<security:authorize access="hasRole('PERM_TESTSESSION_VIEW')">	    	
	        var h3 = $(this).next("div").find("h3");              
	        if (h3.length == 0) {
	        	
	        	var assessmentProgramId = $('#assessmentProgramSelect').val();
	        	var testingProgramId = $('#testingProgramSelect').val();
	        	var assessmentId = $('#assessmentSelect').val();
				var accordion = $(this).next("div");  
				accordion.attr("id", 'testSessionAccordion');				
				//show loading symbol. 
	            $('#loading_overlay').dialog("open");
	            if (assessmentProgramId !== null && !isNaN(assessmentProgramId) && Number(assessmentProgramId) > 0) {
		            var rosterId = $(this).children(".rosterId").val();		            		            
		            if (rosterId != null && !isNaN(rosterId) && Number(rosterId) > 0) {
		            	$.ajax({
		        			url: 'getStudentsTestsForRoster.htm',
		        			data: {
		        				rosterId: rosterId,
		        				assessmentProgramId: assessmentProgramId,
		        				testingProgramId: testingProgramId,
		        				assessmentId: assessmentId
		        			},
		        			dataType: 'json',
		        			type: "POST",
		        			success: function(data) {
		        				$('#loading_overlay').dialog("close");
		        			    if (data.studentsTests != null && data.studentsTests !== undefined && data.studentsTests.length > 0) {
		        					if (accordion.length > 0) {
		        						var accordionName;	        					
		        						var tests = [];		        	                	
		        	                	var found = false;
		        	                	for (var i = 0, length = data.studentsTests.length; i < length; i++) {
		        	                		found = false;
		        	                		for (var j = 0, tLen = tests.length; j < tLen && !found; j++) {
		        	                			if (tests[j].testSession.id === data.studentsTests[i].testSession.id && data.studentsTests[i].testCollectionId !== undefined
		        	                					&& data.studentsTests[i].testCollectionId !== null
		        	                					&& tests[j].testCollectionId === data.studentsTests[i].testCollectionId) {
		        	                				found = true;
		        	                			}
		        	                		}

		        	                		if (!found) {
		        	                			var temp = {
		        	                				testCollectionId: data.studentsTests[i].testCollectionId,
		        	                				testSession: data.studentsTests[i].testSession,
		        	                				testCollection: data.studentsTests[i].testCollection
		        	                			};
		        	                			tests.push(temp);
		        	                		}
		        	                	}	        	                		        	                	
		        	                	
		        						for (var i = 0, length = tests.length; i < length; i++) {
		        							
		        							if (tests[i].testSession.name !== null) {
		        								accordionName = tests[i].testSession.name;
		        							}	        							
		        							if(accordionName != null || accordionName != "") {
		        								 var divId = "testResults" + rosterId + tests[i].testSession.id;
		        								accordion.append("<h3 class='ui-widget-header ui-helper-reset ui-corner-top accordionheader'  id='testSessionHeader' ><a href='#' class='testsessionlabel' style='color: #045FB4;'>" + 
		        									accordionName + "</a><input class='testSessionId' type='hidden' value='" + tests[i].testSession.id +"' rosterId = '" + rosterId + 
		        									"' testCollectionSystemFlag = '" + tests[i].testCollection.systemFlag + "'/></h3><div id='" + divId + "' class='ui-widget-content testsessionbody'></div>");		        								
		        							}
		        							accordionName = "";
		        						}
		        						
		        						$('#loading_overlay').dialog("close");	        							        					
		        					}
		        				} else {
		        					$('body, html').animate({scrollTop:0}, 'slow');
		        					$('#loading_overlay').dialog("close");
		        					$('#notestsessions').show();
		        					setTimeout("aart.clearMessages()", 3000);
		        				}        				
		        				$('#loading_overlay').dialog("close");
		        			}
		        		});	
		            }
	        	}
	        }
	        </security:authorize>
	    }, "#noRosters", null);
	    </security:authorize>    
	});
	
	function testingProgramDropDown(testingPrograms) {
		var optString = '<option value="-1"><fmt:message key="label.common.select"/></option>';
		if (testingPrograms !== null && testingPrograms !== undefined && testingPrograms.length > 0) {
	        for (var i = 0; i < testingPrograms.length; i++) {
	        	optString += '<option value="' + testingPrograms[i].id + '" >' + testingPrograms[i].programName  + '</option>';
	        }
	    } else {
	    	$('#testingProgramSelect').html("");	    	
	    	//show error message
	    	$('#noTestingProgram').show();
	    }
		$('#testingProgramSelect').html(optString);
	}
	
	function createTestingProgramsDropdown(assessmentProgramId) {
		if (assessmentProgramId !== undefined && assessmentProgramId !== null) {
			return $.ajax({
				url: 'getTestingPrograms.htm',
				data: {
					assessmentProgramId: assessmentProgramId					
				},
				dataType: 'json',
				type: "POST",
				success: function(data){
		        	 testingProgramDropDown(data);  
		       }
			});
		}  
	}
	
	function assessmentDropDown(assessments) {
		var optString = '<option value="-1"><fmt:message key="label.common.select"/></option>';
		if (assessments !== null && assessments !== undefined && assessments.length > 0) {	
	        for (var i = 0; i < assessments.length; i++) {
	        	optString += '<option value="' + assessments[i].id + '" >' + assessments[i].assessmentName  + '</option>';
	        }
	    } else {
	    	$('#assessmentSelect').html("");	    	
	    	//show error message
	    	$('#noAssessment').show();
	    }
		$('#assessmentSelect').html(optString);
	}
	
	function createAssessmentsDropdown(assessmentProgramId, testingProgramId) {
		if (assessmentProgramId !== undefined && assessmentProgramId !== null && testingProgramId !== undefined && testingProgramId !== null) {
			return $.ajax({
				url: 'getAssessments.htm',
				data: {
					assessmentProgramId: assessmentProgramId,
					testingProgramId: testingProgramId
				},
				dataType: 'json',
				type: "POST",
				success: function(data){
					assessmentDropDown(data);  
		       }
			});
		} 
	}
	
	//Adding multiple select / deselect students functionality
	$('.selectall').on('change', function(e) {		
		$(e.target).closest('table.testResultsTable').find('.students').attr('checked', this.checked);					
	});
	
	$('.students').on('change', function(e) {	
		$(e.target).closest('table.testResultsTable').find('.students').length == 
			$(e.target).closest('table.testResultsTable').find('.students:checked').length ? $(e.target).closest('table.testResultsTable').find('.selectall').attr('checked', 'checked') : '';
		$(e.target).closest('table.testResultsTable').find('.students').length != 
			$(e.target).closest('table.testResultsTable').find('.students:checked').length ? $(e.target).closest('table.testResultsTable').find('.selectall').removeAttr('checked') : '';
	});
	
	$('#assessmentProgramSelect').change(function(){ 
		//Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
		
		if(Number($(this).val()) != 0) {			
			$('.testingProgramBlock').removeClass("hidden");
			$('.testingProgramBlock').show();
			$('.assessmentBlock').hide();
			var testingPrograms = createTestingProgramsDropdown(Number($(this).val())); 
			$('.testSearch').hide();
		    $('.cpass').hide();
		    $('#testSessionAccordion').html("");
		} else {
			$('#accordionParent div table').remove();				
			$('.testSearch').hide();
		    $('.cpass').hide();
		    $('.testingProgramBlock').hide();
		    $('.assessmentBlock').hide();
		}
	});
	
	$('#testingProgramSelect').change(function() {
		//Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
		
		if(Number($(this).val()) != -1) {
			var testingPrograms = createAssessmentsDropdown(Number($('#assessmentProgramSelect').val()), Number($(this).val())); 
			$('.assessmentBlock').removeClass("hidden");
			$('.assessmentBlock').show();
			$('.testSearch').hide();
		    $('.cpass').hide();
		    $('#testSessionAccordion').html("");
		} else {
			$('#accordionParent div table').remove();				
			$('.testSearch').hide();
		    $('.cpass').hide();
		    $('.assessmentBlock').hide();
		}
	});
	
	var assessmentPrograms = [];
	<c:forEach items="${assessmentPrograms}" var="assessmentProgram" varStatus="index">
	    assessmentPrograms[${index.index}] = {};
	    assessmentPrograms[${index.index}].id = ${assessmentProgram.id};
	    assessmentPrograms[${index.index}].programName = '${assessmentProgram.programName}';
	</c:forEach>
	
	$('#assessmentSelect').change(function(){
		//Clear the previous error messages
		aart.clearMessages();
		
		// check that the user actually selected a valid assessment program
		// get the testing programs for the selected assessment program
		// set the testing program select with the retrieved data.	
		var assessmentId = Number($(this).val());
		if(assessmentId != -1) {
		    $('.testSearch').hide();
		    $('.cpass').hide();
		    
		    $("#accordion").accordion({
				active: false,
				collapisble: true
			});
		    $("#testSessionAccordion").accordion({
				active: false,
				collapisble: true
			});
		    
		    $('#accordion div').hide(); 
		    $('.testSearch').show();
		    $('#testSessionAccordion').html("");
	    } else { 		
			$('.testSearch').hide();
		    $('.cpass').hide();	    
		}
	});
	
	$(document).on('click', '#testSessionHeader', function() {
		var rosterId = $(this).children(".testSessionId").attr("rosterId");
        var testSessionId = $(this).children(".testSessionId").val();
        var testCollectionSystemFlag = $(this).children(".testSessionId").attr("testCollectionSystemFlag");
        var contentDiv = $("#testResults" + rosterId + testSessionId);
        
        //Call the below method on each testsession link click.
        if(!$("#testResults" + rosterId + testSessionId).is(':visible')) {
			getStudentsTestSessionData(contentDiv, rosterId, testSessionId, testCollectionSystemFlag);
        }
		
		$("#testResults" + rosterId + testSessionId).slideToggle();
		$(contentDiv).css("overflow","scroll");
		
	});
	
	function getStudentsTestSessionData(contentDiv, rosterId, testSessionId, testCollectionSystemFlag) {		
		<security:authorize access="hasRole('PERM_TESTSESSION_MONITOR')">    						        
	    var table = contentDiv.find("table");

	    if (table.length == 0) {	    	
	        if (rosterId != null && !isNaN(rosterId) && Number(rosterId) > 0) {
	        	 //show loading symbol. 
	        	$('#loading_overlay').dialog("open");	        	 	        	 	        	 	        	 
	        	 //If systemFlag is set to true, then make a call to show the 	        	 
	        	 // StudentsPerformanceReport otherwise show the StudentsStatus.	        	 
	        	 if(testCollectionSystemFlag === "false") {
	        		 urlValue = 'getTestSessionReport.htm';
	        	 } else {
	        		 urlValue = 'getStudentsTestSessionStatus.htm';
	        	 }	        	 
	            $.ajax({	                
	                url: urlValue,
	                data: {
	                    roster: rosterId,
	                    testSessionId: testSessionId
	                },
	                dataType: 'json',
	                type: "POST",
	                success: function(result) {	                	
	                	//If systemFlag is set to true, then show the  	        	 
	   	        	 	// StudentsPerformanceReport otherwise show the StudentsStatus.
	                    if(testCollectionSystemFlag === "false") {
	                    	buildTestResultsTable(result, contentDiv, rosterId,testSessionId);
	               	 	} else {
	               	 		if(result.students != null && result.students != undefined && result.students.length > 0) {
	               	 			buildStudentsTestSessionStatusTable(result, contentDiv, rosterId, testSessionId);      
	               	 		} else {
		               	 		$('body, html').animate({scrollTop:0}, 'slow');
		            	        $('#notestresults').show();
		            	        setTimeout("aart.clearMessages()", 3000);
	               	 		}
	               	 	}	    
	                    $('#loading_overlay').dialog("close");
	                }
	            });
	            $('#loading_overlay').dialog("close");
	        }
	        $('#loading_overlay').dialog("close");
	    }
	    </security:authorize>		
	}
	
	//Build the table for performace monitoring
	function buildTestResultsTable(studentTestSessions, contentDiv, rosterId, testSessionId) {   
		var newTable = $("<table style='border-collapse:collapse' width='100%' class='testResultsTable'></table>");
	    var htmlString = "";
		var splitString = "";
		
		if(studentTestSessions != null && studentTestSessions.length > 0) {
			for (var listIndex = 0; listIndex < studentTestSessions.length; listIndex++) {
				htmlString += "<tr>";		
				splitString = studentTestSessions[listIndex].split(";");
				for (var splitIndex = 0; splitIndex < splitString.length; splitIndex++) {			  		  
					if(listIndex == 0) { 												
						if(splitIndex == 0) {
							htmlString += "<th class='tableth'> <div style='width:10px;'> <input type='checkbox' class='selectall' /> </div> </th>" +
								"<th class='studentTable, tableth'> <div style='text-align:center; width:100px;'>" + splitString[splitIndex] + " </div> </th>";							
						} else if(splitIndex == 1) {
							htmlString += "<th class='studentTable , tableth'> <div style='text-align:center; width:100px;'>" + splitString[splitIndex] + " </div> </th>";
						} else if(splitIndex == splitString.length -2 || splitIndex == splitString.length - 1) {					
							htmlString +=  "<th class='studentTable , link , tableth' > <div style='width:80px;'>" + splitString[splitIndex] + " </div> </th>";					
						} else {
							htmlString +=  "<th class='studentTable , link , tableth' onclick='getQuestion(" + splitString[splitIndex].split(",")[0] + "," + + splitString[splitIndex].split(",")[1] + ")' > <div style='width:50px;'>" + splitString[splitIndex].split(",")[1]  + " </div> </th>";
						}
					} else {				
						if(splitIndex == 0 && listIndex < (studentTestSessions.length - 1)) {
							htmlString +=  "<td class='tabletd'> <div style='width: 10px;'> <input type='checkbox'  class='students' rosterId='" + rosterId +  "' testSessionId='" +  testSessionId + "' studentId='" + splitString[splitIndex] + "' />  </div> </td>";
						} else if((splitIndex == 1 || splitIndex == 2) && listIndex < (studentTestSessions.length - 1)) {
							htmlString += "<td class='tabletd'> <div style='text-align:center; width:100px;'>" + splitString[splitIndex] + "</div></td>";
						} else if((splitIndex == splitString.length -2 || splitIndex == splitString.length - 1) && listIndex < (studentTestSessions.length - 1)) {
							htmlString += "<td class='tabletd'> <div style='text-align:center; width:80px;'>" + splitString[splitIndex] + "</div></td>";
						} else if(listIndex == studentTestSessions.length - 1) {
							htmlString += "<td style='background-color: #FFFFFF; font-weight: bold;'> <div style='text-align:center; width:50px;'>" + splitString[splitIndex] + " </div> </td>";
						} else {					
							htmlString += "<td class='tabletd'> <div style='text-align:center; width:50px;'>" + splitString[splitIndex] + "</div></td>";
						}
					}
				}
				htmlString += "<td></td> </tr>" ;
			}
					
			newTable.append(htmlString);
			newTable.css("border", "solid black 1px");
			newTable.find('.studentTable').css("border-bottom", "solid black 1px");			
			contentDiv.append("<br>");
			contentDiv.append(newTable);
			contentDiv.append("<br>");			
			$('table.testResultsTable').find("tr:even").addClass('tableroweven');
			newTable = $("<table style='float: right; border-collapse:collapse;'> </table>");
			htmlString = ("<tr> <td> </td> <td> </td>" + 
					   		"<td>&nbsp;&nbsp;&nbsp;<button id='monitortest_endsession_button' type='button' onclick='endStudentSession(" + rosterId + "," + testSessionId + ")'>End Test Session</button></td> </tr> ");
			newTable.append(htmlString);
			contentDiv.append(newTable);
						
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
	        $('#notestresults').show();
	        setTimeout("aart.clearMessages()", 3000);
		}
	}
	
	function endStudentSession(rosterId, testSessionId ) {	
		var studentId = 1;
		var studentIds = [];
		var i =0;
		
		$("input[type='checkbox']:checked").each(function() {
			if(testSessionId == $(this).attr("testSessionId")) {
				studentId = $(this).attr("studentId");
				if (studentId !== undefined && studentId !== null) {
					studentIds[i] = studentId;				
				} else {
					studentIds[i] = 0;
				}				
		        i++;       
			}
	    });
			
		if(studentIds == undefined || studentIds == null || studentIds.length <= 0) {
			$('body, html').animate({scrollTop:0}, 'slow');
	    	$('#session_name_overlay').dialog("close");
	        $('#no_endtestsession_params').show();	
	        setTimeout("aart.clearMessages()", 3000);
		} else {
			$.ajax({	
				url: "closeStudentsTests.htm",
				data: {				
					testSessionId: testSessionId,
					studentIds: studentIds
				},
				datatype: 'json',
				type: "POST",
				success: function(data){
					$('body, html').animate({scrollTop:0}, 'slow');
					// print a success message
					$('#session_name_overlay').dialog("close");
					$('#end_test_session_success').show();
					setTimeout("aart.clearMessages()", 3000);
				}, 
				error: function(data) {
					$('body, html').animate({scrollTop:0}, 'slow');
	            	$('#session_name_overlay').dialog("close");
	                $('#end_test_session_error').show();
	                setTimeout("aart.clearMessages()", 3000);
				}
			}); 
		}
	}
	
	function getQuestion(questionid,questionNum) {	
		 $.ajax({	
			url: "getQuestionForTest.htm",
			data: {			
				questionid: questionid			
			},
			datatype: 'json',
			type: "POST",
			success: function(data) {
				$('#question_overlay').html("Question " + questionNum + ": " + data);
				$('#question_overlay').dialog("open");
				setTimeout("closeQuestionDialog()", 10000);				
			}
		}); 		
	}
	
	//Build the table for status monitoring
	function buildStudentsTestSessionStatusTable(response, contentDiv, rosterId, testSessionId) {
		//The gridComplete and onPaging events are so that a user can select students across multiple pages						
	    var tableId = "table" + rosterId + testSessionId;
	    var pagerId = "pager" + rosterId + testSessionId;
	    var colNames = new Array();
	    var colModel = new Array();     

		//Populate static columnnames.
	    colNames.push("");
	    colNames.push("Name");
	    colNames.push("Overall Status");
	    	   	    
	    //Populate static columnmodel.
		colModel.push({name:'studentsTestId',index:'studentsTestId', width:8, align: 'center', formatter: function (cellvalue, options, rowObject) {
			return checkboxFormatter(cellvalue, options, rowObject, testSessionId); } });
		colModel.push({name:'studentName', index:'studentName', width:30, align: 'center'});
		colModel.push({name:'overallStatus', index:'overallStatus', width:30, align: 'center'});
		
		//Populate dynamic columnnames and columnmodel.
		if(typeof response.sectionStatusColumnNames != 'undefined' && response.sectionStatusColumnNames !=null) {
			for(var i=0; i < response.sectionStatusColumnNames.length; i++) {
				colNames.push(response.sectionStatusColumnNames[i]);
				colModel.push({name:response.sectionStatusColumnNames[i], index:response.sectionStatusColumnNames[i], width:30, align: 'center'});
			}
		}
	    contentDiv.append("<table id='" + tableId + "'></table><div id='" + pagerId +"'></div>");
	    $("#" + tableId).jqGrid({
	        datatype: "local",
	        data: response.students,
	        colNames: colNames,        
	        colModel: colModel,   
	        viewsortcols : [ false, 'vertical', true ],
	        height : 'auto',
	        width: contentDiv.width(),
	        shrinkToFit: true,
	        rowNum : 10,
	        rowList : [ 10, 20, 30 ],
	        sortname : '',
	        sortorder : 'asc',
	        pager: pagerId,
	        viewrecords : true,
	        emptyrecords : "<fmt:message key='label.common.norecordsfound'/>",
	        altRows : true,
	        hoverrows : true,
	        multiselect : false,        
	        gridComplete: function() {
	        	var currentPage = $(this).getGridParam("page").toString();        	        	        	 
	        	//retirieve any previously stored rows for this page and re-select them
	        	var retrieveSelectedRows = $(this).data(currentPage);
	        	if (retrieveSelectedRows) {
	        		$.each(retrieveSelectedRows, function (index, value){
	        			$("#" + tableId).setSelection(value, false);
	        		});
	        	}
	        },                
	        onPaging: function() {
	            var pagerId = this.p.pager.substr(1); // get pager id like "pager" 
	            var pageValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val(); 
	            var saveSelectedRows = $(this).getGridParam('selarrrow'); 
	            //Store any selected rows 
	            $(this).data(pageValue.toString(), saveSelectedRows);
	        }
	    });
					
	    //Add reactivate button at the end of each testsession studentstatus's grid.
    	var newTable = $("<table id='button_" + tableId + "' style='float: right; border-collapse:collapse;'> </table>");
    	var htmlString = ("<tr><td><br><button id='monitortest_reactivateTicket_refresh_button' type='button' onclick='refreshStudentsSectionStatusData(" + rosterId + "," + testSessionId  + ")' >Refresh</button></td>" +
        		"<td><br></td><td><br><button id='monitortest_reactivateTicket_button" + testSessionId + "' type='button' onclick='reactivateStudentsSection(" + rosterId + "," + testSessionId + ")' disabled='disabled' >Reactivate</button></td> </tr>");
		newTable.append(htmlString);
		contentDiv.append(newTable);  	
	}
	
	//Formatter for the jqgrid checkbox
	function checkboxFormatter(cellvalue, options, rowObject, testSessionId) {		
		var htmlString = "";
  		if(rowObject.inProgressTimedOut) {
			htmlString = " <input type='checkbox' class='studentsTestIdCheckBox' id = 'studentsTestIdCheckBox" + rowObject.studentsTestId + "' studentsTestId='" + rowObject.studentsTestId + "' testSessionId='" + testSessionId + "' />";
  		} else {
  			htmlString = " <input type='checkbox' class='studentsTestIdCheckBox' disabled='true' studentsTestId='" + rowObject.studentsTestId + "' testSessionId='" + testSessionId + "' />";
  		}	    
	    return htmlString;
	}		
	
	//Enable/disable the reactivate button based on checkbox selection for that testsession
	$(".studentsTestIdCheckBox").on('change', function(e) {
		var testSessionId = $(this).attr("testSessionId");
		var studentsTestId = $(this).attr("studentsTestId");
		var buttonId = "monitortest_reactivateTicket_button" + testSessionId;
		var checkboxId = "studentsTestIdCheckBox" + studentsTestId;		
		//alert('testSessionId-'+testSessionId);
		//alert('buttonId-'+buttonId);
		//alert('checkboxId-'+checkboxId);
		var enableReactivation = false;
		$("input[type='checkbox']:checked").each(function() {			
			if(!enableReactivation && testSessionId == $(this).attr("testSessionId")) {
				enableReactivation = true;
			}
	    });

		if(enableReactivation) {
			//alert('checkboxId-'+checkboxId+'is checked');
			$('#' + buttonId).attr("disabled" , false);
		} else {
			//alert('checkboxId-'+checkboxId+'is not checked');
			$('#' + buttonId).attr("disabled" , true);
		}
		
	});
	
	//Refresh the data by sending the request again.
	function refreshStudentsSectionStatusData(rosterId, testSessionId) {
		$("#testResults" + rosterId + testSessionId).html("");		
		getStudentsTestSessionData($("#testResults" + rosterId + testSessionId), rosterId, testSessionId, "true");
	}
	
	//Reactivate the students with status "In progress Timed out".  
	function reactivateStudentsSection(rosterId, testSessionId) {
		var reactivateButtonId = "monitortest_reactivateTicket_button" + testSessionId;
		var checkboxId = "studentsTestIdCheckBox" + testSessionId;
		var studentsTestId;
		var studentsTestIds = [];
		var i = 0;

		if (confirm("Are you sure you want to reactivate the ticket for the session/section timed out?")) {
			$('#loading_overlay').dialog("open");
			$("input[type='checkbox']:checked").each(function() {			
				if(testSessionId == $(this).attr("testSessionId")) {				
					studentsTestId = $(this).attr("studentsTestId");
					if (studentsTestId !== undefined && studentsTestId !== null) {
						studentsTestIds[i] = studentsTestId;
					} else {
						studentsTestIds[i] = 0;
					}
			        i++;
				}
		    });

			if(studentsTestIds == undefined || studentsTestIds == null || studentsTestIds.length <= 0) {
				$('body, html').animate({scrollTop:0}, 'slow');
		    	$('#session_name_overlay').dialog("close");
		        $('#no_reactivate_params').show();	
		        setTimeout("aart.clearMessages()", 3000);
			} else {
				$.ajax({	
					url: "reactivateStudentsSection1.htm",
					data: {										
						studentsTestIds: studentsTestIds,
						testSessionId: testSessionId
					},
					datatype: 'json',
					type: "POST",
					success: function(data){
						//Refresh jqgrid data by sending the request again.
						refreshStudentsSectionStatusData(rosterId, testSessionId);	
						
						$('body, html').animate({scrollTop:0}, 'slow');
						$('#session_name_overlay').dialog("close");
						if(data == true) {
							// print success message						
							$('#reactivation_success').show();
						} else {
							$('#reactivation_error').show();
						}
						setTimeout("aart.clearMessages()", 3000);
					}, 
					error: function(data) {
						// print error message
						$('body, html').animate({scrollTop:0}, 'slow');
		            	$('#session_name_overlay').dialog("close");
		                $('#reactivation_error').show();
		                setTimeout("aart.clearMessages()", 3000);
					}
				}); 
			}
		}
		//Clear checkbox selection and disable the button
		$('#' + checkboxId).removeAttr('checked'); 
		$('#' + reactivateButtonId).attr("disabled" , true);
		$('#loading_overlay').dialog("close");
	}
	
</script>
