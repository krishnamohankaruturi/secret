<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp" %>
<style>
.header {
    font-weight: bold;
    color: #14407C;
    font-size: 1.2em;
    float: left;
    margin-right: 100px;
    width:100%;
}
#accordionParent {
    overflow-y: auto;
    margin-bottom: 5px;
}

#accordionParent .ui-accordion.ui-accordion-content {
/*     max-height: 500px; */
}

#accordionParent div table tr, th, td {
    border-left: 1px solid #a6c9e3;
    border-top: 1px solid #a6c9e3;
    padding: 3px 5px 3px 5px;
    text-align: center;
}
.pdfLink:hover, .cancelTestSession:hover {
    cursor: pointer;
}

.pdfLink, .cancelTestSession {
    margin: 0px 3px;
}

.cancelTestSession img {
    height: 20px;
    width: 20px;
}

col_wrapper {
	width: 800px;

}

.left-col {
	float: left; 
  	text-align: right; 
/*   	width: 500px; */
}

.right-col {
  	float: right;  
  	text-align: left; 
/*   	width: 490px; */
}

</style>
<div class="pageContent panel_full">
    <div>
        <div class="messages">
         	<span class="error_message ui-state-error hidden" id="sys_error"><fmt:message key="error.generic"/></span>
         	<span class="error_message ui-state-error hidden" id="noAssessmentProgram"><fmt:message key="error.noAssessmentProgram"/></span>
        	<span class="error_message ui-state-error hidden" id="noTestingProgram"><fmt:message key="error.noTestingProgram"/></span>
        	<span class="error_message ui-state-error hidden" id="noAssessment"><fmt:message key="error.noAssessment"/></span>
            <span class="error_message ui-state-error hidden" id="no_assess_prog"><fmt:message key='error.testsession.noassessprogselected'/></span>
            <span class="error_message ui-state-error hidden" id="test_update_failed"><fmt:message key='error.testsession.update.failed'/></span>
            <span class="error_message ui-state-error hidden" id="no_stu_for_roster"><fmt:message key='error.testsession.nostuforroster'/></span>
            <span class="error_message ui-state-error hidden relative " id="no_test_for_roster"><fmt:message key='error.testsession.notestforroster'/></span>
            <span class="error_message ui-state-error hidden" id="invalid_update_params"><fmt:message key='error.testsession.noupdateparams'/></span>
            <span class="error_message ui-state-error hidden" id="cancel_session_failed"><fmt:message key='error.testsession.cancel.failed'/></span>
            <span class="info_message ui-state-highlight hidden" id="cancel_success_retrieval_failed"><fmt:message key='testsession.cancel.success.retrieval.failed'/></span>
            <span class="info_message ui-state-highlight hidden" id="test_update_success"><fmt:message key='label.testsession.update.success'/></span>
            <span class="info_message ui-state-highlight hidden" id="cancel_session_success"><fmt:message key='testsession.cancel.success'/></span>
            <span id="noRosters" class="error_message ui-state-error hidden"><fmt:message key='error.testsession.norosters'/></span>
            <c:choose>
                <c:when test='${empty invalidPdfParams }'>
                    <span class="error_message ui-state-error hidden" id="pdf_error"><fmt:message key="error.testsession.pdf.genfailed"/></span>
                </c:when>
                <c:otherwise>
                    <span class="error_message ui-state-error" id="pdf_error"><fmt:message key="error.testsession.pdf.genfailed"/></span>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test='${empty pdfGenerated }'>
                    <span class="error_message ui-state-error hidden" id="pdf_error"><fmt:message key="error.testsession.pdf.noSessions"/></span>
                </c:when>
                <c:otherwise>
                    <span class="error_message ui-state-error" id="pdf_error"><fmt:message key="error.testsession.pdf.noSessions"/></span>
                </c:otherwise>
            </c:choose>
            <security:authorize access="!hasRole('PERM_ROSTERRECORD_VIEW')">
	            <span class="ui-state-error"><fmt:message key='error.common.permissiondenied.roster.view'/></span>
	        </security:authorize>
	        <security:authorize access="!hasRole('PERM_TESTSESSION_VIEW')">
	            <span class="ui-state-error"><fmt:message key='error.common.permissiondenied.testsession.view'/></span>
	        </security:authorize>
        </div>
        <div class="header">
           <label id="title"><fmt:message key='label.tests.managetestsession'/></label>
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
		                   <c:when test="${fn:length(testingPrograms) > 0 }">
		                       <select id="assessmentProgramSelect">
		                           <option value=""><fmt:message key="label.common.select"/></option>
		                           <c:forEach items="${testingPrograms }" var="testingProgram" varStatus="index">
		                               <option value="${testingProgram.id }">${testingProgram.assessmentProgram.programName }</option>
		                           </c:forEach>
		                       </select>
		                   </c:when>
		                   <c:otherwise>
		                       <span class="ui-state-error"><fmt:message key="error.tests.notestingprogramsfororg"/></span>
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
    
    <div class="hidden manageTests">
        <security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW')">
	        <div id="accordionParent" class="manageTests hidden">
	        </div>
        </security:authorize>
        
        <security:authorize access="hasRole('PERM_TESTSESSION_MODIFY')">
            <input type="button" id="submitButton" class="panel_btn hidden manageTests" value="<fmt:message key='label.common.submit'/>" />
        </security:authorize>
    </div>
</div>
<div id="loading_overlay" class="hidden">
   <fmt:message key="label.common.loading"/>
   <br/>
   <img src="<c:url value='/images/ajax-loader-big.gif'/>"/>
</div>
<script>
(function(aart, $, undefined) {
	aart.div;
	aart.sessionCollections;
	
	aart.getDiv = function() {
		return div;
	};
	
	aart.setDiv = function(newDiv) {
		div = newDiv;
	};
	
	aart.getSessionCollections = function() {
		return sessionCollections;
	};
	
	aart.setSessionCollections = function(newSessionCollections) {
		sessionCollections = newSessionCollections;
	};
}(window.aart = window.aart || {}, jQuery));
$(function() {
	
	$('#assessmentProgramSelect option').removeAttr('selected');
	
	setTimeout("aart.clearMessages()", 5000);
	$('#loading_overlay').dialog({
        height: 200,
        width: 200,
        modal: true,
        autoOpen: false
    });
	
	$('#accordionParent').delegate(".cancelTestSession", "click", function() {
		var testSession = $(this).attr("testSession");
		//if (confirm("Are you sure you want to End this test session")){
			cancelTestSession(testSession);
		//}
	});
	
	$("#accordionParent").delegate("", "")

    <security:authorize access="hasRole('PERM_TESTSESSION_MODIFY')">
	$("#submitButton").click(function() {
		// for each student get selected tests
		var activeRoster = $('#accordionParent .ui-state-active:not(".loading")');
        
        var rosterId = activeRoster.children('.rosterId').val();
        
        if (activeRoster !== undefined && activeRoster !== null && rosterId !== undefined && rosterId !== null && !isNaN(Number(rosterId))) {
			var data = {};
			data.rosterId = rosterId;
			data.studentsTests = [];
			activeRoster.next().find('.student').each(function() {
				var studentId = $(this).children(".studentId").val();
				var index = -1;
	            
	            for (var i = 0, length = data.studentsTests.length; i < length; i++) {
	                if (data.studentsTests[i].studentId == studentId) {
	                    index = i;
	                }
	            }
	            
	            if (index === -1) {
	                index = data.studentsTests.length;
	                data.studentsTests[index] = {};
	                data.studentsTests[index].studentId = studentId;
	                data.studentsTests[index].testSessions = [];
	                data.studentsTests[index].testCollections = [];
	            }
	
	            var counter = 0;
	            $(this).siblings().children("input[type='checkbox']:checked").each(function() {
	                data.studentsTests[index].testSessions[counter] = $(this).val();
	                data.studentsTests[index].testCollections[counter] = $(this).attr("testCollection");
	                counter++;
	            });
			});
	
			var assessmentId = $('#assessmentSelect').val();
			if (assessmentId !== null && !isNaN(assessmentId) && Number(assessmentId) > 0) {
				$('#loading_overlay').dialog("open");
				data.length = data.studentsTests.length;
				data.assessmentId = assessmentId;
		        $.ajax({
		            url: 'saveStudentsTests.htm',
		            data: data,
		            dataType: 'json',
		            type: "POST",
		            success: function(data) {
		                //print success message to the user.
		                if (data) {
		                	setTimeout($('#loading_overlay').dialog("close"), 5000);
		                	
		                	$('body, html').animate({scrollTop:0}, 'slow');
		                	$('#test_update_success').show();
		                	setTimeout('aart.clearMessages()', 5000);
		                } else {
		                	$('#loading_overlay').dialog("close");
		                	$('body, html').animate({scrollTop:0}, 'slow');
		                	$('#test_update_failed').show();
		                    setTimeout("aart.clearMessages()", 5000);
		                }
		            },
		            error: function() {
		            	$('#loading_overlay').dialog("close");
		            	$('body, html').animate({scrollTop:0}, 'slow');
		                $('#test_update_failed').show();
		                setTimeout("aart.clearMessages()", 5000);
		            }
		        });
			} else {
				$('#loading_overlay').dialog("close");
				$('body, html').animate({scrollTop:0}, 'slow');
				//Print an error message saying that the assesment program needs to be selected.
				$('#no_assess_prog').show();
				setTimeout("aart.clearMessages()", 3000);
			}
        } else {
        	$('body, html').animate({scrollTop:0}, 'slow');
        	$('#invalid_update_params').show();
        	setTimeout("aart.clearMessages()", 3000);
        }
	}).button();
	</security:authorize>

	<security:authorize access="hasRole('PERM_ROSTERRECORD_VIEW') and hasRole('PERM_TESTSESSION_VIEW')">
	aart.buildRosterAccordion("#accordionParent", function() {
		$('#loading_overlay').dialog("open");
		aart.setDiv($(this).next("div"));
        var table = $(this).next("div").find("table");
        if (table.length === 0) {
        	//get the rosterId
        	var assessmentProgramId = $('#assessmentProgramSelect').val();
        	var testingProgramId = $('#testingProgramSelect').val();
        	var assessmentId = $('#assessmentSelect').val();

        	//TODO - The following variable need to be REMOVED once the pilot is complete!
        	var assessmentProgramName = $('#assessmentProgramSelect option:selected').text();

        	if (assessmentProgramId !== null && !isNaN(assessmentProgramId) && Number(assessmentProgramId) > 0) {
	        	var rosterId = $(this).children("input[type='hidden']").val();
	        	//make ajax call
	 		    var promise = getStudentTests(rosterId, assessmentProgramId, testingProgramId, assessmentId);
	 		    //create the table
	            promise.success(function(data) {
	                if (data.studentsTests != null && data.studentsTests !== undefined && data.studentsTests.length > 0) {
	                	var tests = [];
	                	var students = data.students;
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
	                				testSession: data.studentsTests[i].testSession
	                			};
	                			tests.push(temp);
	                		}
	                	}

	                	var newTable = $('<table></table>');
	                    var htmlString = "<tr><th></th>";

                        for (var i = 0, length = tests.length; i < length; i++) {
                        	//alert("Length of Tests" +tests[i].length + "Test Colletion Id of " + tests[i]+ tests[i].testCollectionId);
//                             htmlString += "<th class='column" + i +"'>" + tests[i].name + "<a href='getPDFForRosterAndTest.htm?testId=" + tests[i].id + "&rosterId=" + rosterId + "' class='pdfLink'><img src='images/pdf.png' alt='<fmt:message key='label.managetestsession.pdf'/>'/></a></th>";
							// DE2719 Black boxes display around PDF icons in IE8 FIX.
                        	htmlString += "<th class='column" + tests[i].testSession.id +"'><label class='sessionName'>" + tests[i].testSession.name + "</label><a href='getPDFForRosterAndTest.htm?assessmentProgramName="+ assessmentProgramName +"&testSessionId=" + tests[i].testSession.id + "&testCollectionId=" + tests[i].testCollectionId + "' class='pdfLink'><img src='images/pdf.png' style='border:0px solid;' alt='<fmt:message key='label.managetestsession.pdf'/>'/></a>";
                        	//The following IF statement needs to be removed once the pilot is complete!
                        	if ($.trim(assessmentProgramName).toLowerCase() !== "cpass" && $.trim(assessmentProgramName).toLowerCase() !== "career pathways collaborative") {
                        		htmlString += "<span class='cancelTestSession' testsession='" + tests[i].testSession.id + "'><img src='images/clipboard_report_bar_24_ns.png'/></span></th>";
                        	}
                        }
                        htmlString += "</tr>";
	                	
                		for (var i = 0, length = students.length; i < length; i++) {
	                		htmlString += "<tr>";
	                		htmlString += "<td class='student'>" + students[i].legalLastName + ", " + students[i].legalFirstName + "<input type='hidden' class='studentId' value='" + students[i].id +"'/></td>";

	                		if (tests !== null && tests !== undefined) {
	                			var isChecked = false;
	                			var index = -1;
	                			for (var k = 0, kLength = tests.length; k < kLength; k++) {
	                			    for (var j = 0, jLength = data.studentsTests.length; j < jLength; j++) {
		                				if (data.studentsTests[j].testSession.id === tests[k].testSession.id && students[i].id === data.studentsTests[j].studentId) {
		                					isChecked = true;
		                					index = j;
		                				}
		                			}
		                			htmlString += "<td class='column" + tests[k].testSession.id + "'>";
		                			if (isChecked && index >= 0) {
		                				isChecked = false;
		                				if (data.studentsTests[index].status === null || data.studentsTests[index].status !== null && data.studentsTests[index].status === data.notStartedStatus.id) {
		                					htmlString += "<input type='checkbox' checked='checked' testcollection='" + data.studentsTests[index].testCollectionId + "' class='studenttest' value='" + tests[k].testSession.id +"'/>";
		                				} else {
		                					htmlString += "<input type='checkbox' disabled='disabled' checked='checked' testcollection='" + data.studentsTests[index].testCollectionId + "' class='studenttest' value='" + tests[k].testSession.id +"'/>";
		                				}
		                			} else {
		                				htmlString += "<input type='checkbox' class='studenttest' testcollection='" + tests[k].testCollectionId + "' value='" + tests[k].testSession.id +"'/>";
		                			}
		                			htmlString += "</td>";
		                			index = -1;
		                		}
	                		}
	                		htmlString += "</tr>";
	                	}
	                	newTable.append(htmlString);
	                	aart.getDiv().append(newTable);
	                } else if (data.students === null && data.students === undefined && data.students.length <= 0) {
	                	$('body, html').animate({scrollTop:0}, 'slow');
	                	//print an error message saying there are no students for this roster.
	                	$('#no_stu_for_roster').show();
	                	setTimeout("aart.clearMessages()", 3000);
	                } else {                        
                        $('body, html').animate({scrollTop:0}, 'slow');
                        //print an error message saying that no valid tests could be found for this roster.
                        $('#no_test_for_roster').show();                                                                        
                        setTimeout("aart.clearMessages()", 3000);
                    }
	            });
        	} else {
        		$('body, html').animate({scrollTop:0}, 'slow');
        		// print an error message asking user to select the assesment program
        		$('#no_assess_prog').show();
                setTimeout("aart.clearMessages()", 3000);
        	}
        }
        $('#loading_overlay').dialog("close");
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

$('#assessmentProgramSelect').change(function() {
	
	//Clear the previous error messages
	setTimeout("aart.clearMessages()", 0);
	
	if(Number($(this).val()) != 0) {
		$('.testingProgramBlock').removeClass("hidden");
		$('.testingProgramBlock').show();
		$('.assessmentBlock').hide();
		var testingPrograms = createTestingProgramsDropdown(Number($(this).val())); 
		$("#accordionParent").accordion({
			active: false,
			collapisble: true
		});
		$('#accordionParent div table').remove();
	$('.manageTests').addClass("hidden");
	} else {
		$('#accordionParent div table').remove();
		$('.manageTests').hide();
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
		$("#accordionParent").accordion({
			active: false,
			collapisble: true
		});
		$('#accordionParent div table').remove();
		$('.manageTests').addClass("hidden");
	} else {
		$('#accordionParent div table').remove();
		$('.manageTests').hide();
		$('.assessmentBlock').hide();	    
	}
});

$('#assessmentSelect').change(function() {
	
	//Clear the previous error messages
	setTimeout("aart.clearMessages()", 0);
	
	if(Number($(this).val()) != -1) {
		//delete all of the tables from the accordion pane.
		$('.manageTests').removeClass("hidden");
		$("#no_test_for_roster, #no_stu_for_roster, #test_update_failed, #no_assess_prog").hide();
		$("#accordionParent").accordion({
			active: false,
			collapisble: true
		});
		$('#accordionParent div table').remove();
		$('.manageTests').show();
	} else {		
		$('#accordionParent div table').remove();
		$('.manageTests').hide();
	}
});

function cancelTestSession(testSession) {
	if (aart.isValidPositiveNumber(testSession)) {
		$.ajax({
			//url: "cancelTestSession.htm",
			url: "getResponses.htm",
			data: {
				testSessionId: testSession
			},
			datatype: 'json',
			type: "POST",
			success: function(data){
				if(data.successful) {
					//TODO - remove the test session from the page, or just mark it as closed.
					//display successful message to user.
					//$(".column" + testSession).remove();
					$('body, html').animate({scrollTop:0}, 'slow');
					$("#cancel_session_success").show();
					setTimeout("aart.clearMessages()", 3000);
				} else if (!data.successful && !data.retrieval) {
					// This means that canceling the test session was successful, but there was an error retrieving the student's
					// responses for this test session.
					$('body, html').animate({scrollTop:0}, 'slow');
					$('#cancel_success_retrieval_failed').show();
					//$('.column' + testSession).remove();
					setTimeout("aart.clearMessages()", 3000);
				} else {
					$('body, html').animate({scrollTop:0}, 'slow');
					//print an error message saying canceling the test session encountered an issue.
					$('#cancel_session_failed').show();
					setTimeout("aart.clearMessages()", 3000);
				}
			}
		});
	} else {
		$('body, html').animate({scrollTop:0}, 'slow');
		//print a generic error message saying that canceling the test session encountered an issue.
		$('#cancel_session_failed').show();
		setTimeout("aart.clearMessages()", 3000);
	}
}

function findTestsForStudent(studentId, studentsTests) {
	var st = [];
	if (studentId !== null && !isNaN(studentId) && Number(studentId) > 0
			&& studentsTests !== null && studentsTests !== undefined) {
		for (var i = 0, length = studentsTests.length; i < length; i++) {
			if (studentsTests[i].studentId === studentId) {
				st.push(studentsTests[i]);
			}
		}
	}
	return st;
}

<security:authorize access="hasRole('PERM_TESTSESSION_VIEW')">
function getStudentTests(rosterId, assessmentProgramId, testingProgramId, assessmentId) {
	if (rosterId !== null && !isNaN(rosterId) && Number(rosterId) > 0
            && assessmentProgramId !== null && !isNaN(assessmentProgramId) && Number(assessmentProgramId) > 0) {
		return $.ajax({
			url: 'getStudentsAndTestsForRoster.htm',
			data: {
				roster: rosterId,
				assessmentProgram: assessmentProgramId,
				testingProgramId: testingProgramId,
				assessmentId: assessmentId
			},
			dataType: 'json',
			type: "POST"
		});
	} else if (Number(assessmentProgramId) <= 0){
		$('body, html').animate({scrollTop:0}, 'slow');
		//print error message about selecting a testing program using the selector.
		$('#no_assess_prog').show();
        setTimeout("aart.clearMessages()", 3000);
    }
}
</security:authorize>
</script>