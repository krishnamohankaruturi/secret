<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
	
<div>
		
	<!-- In below input component attribute "class="hidden" used to fix the IE8 browser issue. -->	
	<input id="loggedInUserId" value ="${user.id}" type="text" class="hidden" />	
	<!--  <input id="detailViewCaption" value ="View Student Detail" type="text" class="hidden" />  -->
	<input id="testSessionId" value ="${testSessionId}" type="text" class="hidden" />
	<input id="testSessionSource" value ="${testSessionSource}" type="text" class="hidden" />	
	<input id="testSessionName1" value ="${testSessionName}" type="text" class="hidden" />
	<input id="hasSpecialCircumstanceApprovalPermission" value = "${hasSpecialCircumstanceApprovalPermission}" type="hidden" />
	<input id="specialCircumstanceApprovalVisible" value = "${specialCircumstanceApprovalVisible}" type="hidden" />
	<input id="stateHasRestrictedCodes" value = "${stateHasRestrictedCodes}" type="hidden" />
	<div id="pnpSummaryViewDiv"></div>
		
	<div id="noReport" style="display:none;" ></div>
	
	<div class ="messages">
	  <security:authorize access="!hasRole('PERM_ROSTERRECORD_VIEW')">
				<div class="ui-state-error"><fmt:message key='error.common.permissiondenied.roster.view' /></div>
	  </security:authorize>
	  <security:authorize access="!hasRole('PERM_STUDENTRECORD_VIEW')">
				<div class="ui-state-error"><fmt:message key='error.common.permissiondenied.student.view' /></div>
	  </security:authorize>
	</div>

<!-- 
	<div class="full_side ">
		
		<h1 class="panel_head sub">Students</h1>
		
		<label for="assessmentProgramSelect"><fmt:message key='label.testsession.schoolid' /></label>
		<c:choose>
			<c:when test="${fn:length(assessmentPrograms) > 0 }">
				<select id="assessmentProgramSelect">
					<option value="">
						<fmt:message key="label.common.select" />
					</option>
					<c:forEach items="${assessmentPrograms }" var="assessmentProgram"
						varStatus="index">
						<option value="${assessmentProgram.id }">${assessmentProgram.assessmentProgram.programName
							}</option>
					</c:forEach>
				</select>
			</c:when>
			<c:otherwise>
				<span class="ui-state-error"><fmt:message
						key="error.tests.noassessmentprogramsfororg" /></span>
			</c:otherwise>
		</c:choose>

		<label for="grade"><fmt:message key='label.testsession.grade' /></label>
		<select id="grade">
			<option value="-1">
				<fmt:message key="label.common.select" />
			</option>
		</select>

		<label for="roster"><fmt:message key='label.testsession.roster' /></label>
		<select id="roster" >
			<option value="-1">
				<fmt:message key="label.common.select" />
			</option>
		</select>
		
		<br />
		
		<input type="button" id="studentsClearSelections" value="<fmt:message key='label.common.clearselections'/>" class="panel_btn" />
		
	</div>  --> <!-- / side panel -->	  
	<div>
		<%--Fixes for DE10690--%>
		<div class="top_info">

			<%-- <div class="head">
                <h1><fmt:message key='label.testsession.register.students' /></h1>
                <h2>
                    <div id="newInstructionsDiv"><fmt:message key='label.testsession.register.new.instructions' /></div>
                    <div id="editInstructionsDiv"><fmt:message key='label.testsession.register.edit.instructions' /></div>
                </h2>
            </div> --%>

			<!-- <div class="search">
                <input id="keywordInput" class="search_box" name="search">
                    <div class="search_btn">
                        <input id="submitKeywordInput" type="submit" value="">
                    </div>/search btn
            </div> -->

			<div id="nextButtonDivTop" class ="nextButtonDiv floatRightTopButton">
				<input type="button" id="studentsNextButtonTop" value="<fmt:message key='label.common.next'/>" class="next_btn nextButton studentsNextButton" style="float: right;"/>
			</div>
			<span class="error_message ui-state-error hidden selectStudentsError" id=selectStudentsError><fmt:message key="error.testsession.noStudents"/></span> <br />
		</div> <!-- /top_info -->



		<c:if test="${!user.teacher}">
			<div id="searchFilterContainer">
				<form id="searchFilterForm" name="searchFilterForm" class="form">

						<div class="btn-bar">
							<div id="searchFilterErrors" class="error"></div>
							<div id="searchFilterMessage" style="padding:20px" class="hidden"></div>
						</div>
						<c:if test="${user.accessLevel < 50 }">
						    <div class="form-fields">
								<label for="stssDistrictOrgs" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>
								<select id="stssDistrictOrgs" title="District" class="bcg_select required" name="stssDistrictOrgs">
									<option value="">Select</option>
								</select>
							</div>
						</c:if>
						<div class="form-fields">
							<label for="stssSchoolOrgs" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>
							<select id="stssSchoolOrgs" title="School" class="bcg_select required" name="stssSchoolOrgs">
								<option value="">Select</option>
							</select>
						</div>
				        <div class="form-fields">
				            <button class="btn_blue" id="stssSearchBtn">Search</button>
				        </div>
				</form>
			</div>
		</c:if>
		<div class="table_wrap table_wrap_overloaded">
		<div class="kite-table">
		
			<table id="rosterStudentsTableId"  class="responsive"></table>
			<div id="prosterStudentsTableId" class="responsive"></div>
		</div>
		</div>
		
		<div id="nextButtonDiv" class="nextButtonDiv floatRightDiv floatRightBottomButton">
			<input type="button" id="studentsNextButton1" value="<fmt:message key='label.common.next'/>" class="next_btn nextButton studentsNextButton"/>
		</div>
		<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE') || hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')
		|| hasRole('PERM_TESTSESSION_MODIFY')">
			<div id="saveButtonDiv">
				<input type="button" id="saveStudents" value="Save" class="next_btn saveButton studentsSaveButton" style="float: right;"/>
			</div>
		</security:authorize>
	</div>
	
	<br />
	
</div>
<div id="confirmDialog"></div>
<div id="confirmSaveSpecialCircumstanceDialog"  style="display:none;" title="Special Circumstance Code Selection Approval Required">
	<p>One or more of the Special Circumstance code selections require approval by a state-level administrator. 
		Application of the code selections for the following students will be held in Pending status while the 
		request is reviewed.
	</p>
	<p id="approvalRequiredStudents"></p>
	<p>Special Circumstance code selections for the students not listed will be saved immediately.</p>
	<p>Click the OK button to continue, or Cancel to clear all your Special Circumstance code selections and 
		return to the Test Monitor page.
	</p>
</div>
<script>
	function getSelectedStudentEnrollmentID(){
		var selectedStudentsEnrollmentID = [];
		selectedStudents.forEach(function(idSelected) {
			  selectedStudentsEnrollmentID.push($('#rosterStudentsTableId').getRowData(idSelected).id);
			});
		return selectedStudentsEnrollmentID;
	}
	//Global variable to capture the mouse position for 
	//"Loading...." message display.
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
	
    var isTeacher = false;
    <c:if test="${user.teacher}">
    	isTeacher = true;
    </c:if>
    
    var isEdit = false;    
    
	var mousePosition;
	var grid = $("#rosterStudentsTableId");
	//Global variable to store selected students on each page of the recordbrowser. 
	var selectedStudents = [];
	var totalEnrolledStudents = [];
	var unenrolledStudents = [];
	var selectedStudentsNum = 0;
	var disabledStudents = [];
	var checkgroup = new Boolean();
	var autoCompleteUrl =  "getAutoCompleteSelectRosterStudents.htm";
	var currentSchoolYear;            
	var firstLanguage;    	
	var comprehensiveRace;    	
	var primaryDisabilityCode;
	var gender;
	var generationCode; 
	var gradeCourse;
    var attendanceSchoolName;
    var rosterId;
    var courseEnrollmentStatus;
    var dlmStudentStatus;
    var index = 0;
    var studentSpecialCircumstances = [];
    var specialCircumstanceConfirmationList = [];
    var isApprovalsRequired = false;
    $(function() {
    	specialCircumstanceConfirmationList = [
			<c:forEach items="${specialCircumstanceList}" var="specialCircumstance">
				{id: "${specialCircumstance.id}", requireConfirmation: "${specialCircumstance.requireConfirmation}"},
			</c:forEach>
	     ];
	if(!isTeacher) {		
		//toggle the componenet with class msg_body
		  $(".tmfilterheading").on("click",function(e) {
			  e.preventDefault();
			  
		    $("#searchFilterContainer").slideToggle(400);
		  });
    	//$('.hidden', $('#searchFilterContainer')).hide();
    	$('#stssDistrictOrgs, #stssSchoolOrgs').select2({
    		placeholder:'Select',
    		multiple: false
    	});
		
    	filteringOrganization($("#stssDistrictOrgs"));
    	filteringOrganization($("#stssSchoolOrgs"));
    	
    	populateSTSSStudentsSearchFiltersData();
    	
    	$('#searchFilterForm').validate({
    		ignore: "",
    		rules: {
    			<c:if test="${user.accessLevel < 50 }">
    				stssDistrictOrgs: {required: true},
    			</c:if>
    			stssSchoolOrgs: {required: true}
    		}
    	});

		$('#stssSearchBtn').on("click",function(e) {
			
			e.preventDefault();
			$('#searchFilterErrors').html("");
			$('#searchFilterMessage').html("").hide();
			if($('#searchFilterForm').valid()){
				//loadStudentsData();
				loadGridStudents();
			}
		});
	}
    });
    
	//getValues();
	  function getValuesDropDown(){
		  var testSessionId = $("#testSessionId").val();
	    	$.ajax({
	            url: "getSelectRosterStudents.htm",            
	            dataType: 'json',
	            type: "GET",	           
	            data: {
					testSessionId : testSessionId,
					filters : null
				    },
				async: false
	    	}).done(function(data) {
            	currentSchoolYear = ":All";
           	 	for (i=0; i<data.currentSchoolYear.length; i++) {
           	 		//alert(data.currentSchoolYear[i]);
           	 	currentSchoolYear += ";" + data.currentSchoolYear[i] 
           	 			+ ":" + data.currentSchoolYear[i];
           	 	}
           	 	
           		dlmStudentStatus = ":All";
           	 	for (i=0; i<data.dlmStudentStatus.length; i++) {

           	 		dlmStudentStatus += ";" + data.dlmStudentStatus[i] 
           	 			+ ":" + data.dlmStudentStatus[i];
           	 	}
           	 	
           	 	firstLanguage = ":All";
        	 	for (i=0; i<data.firstLanguage.length; i++) {
        	 		firstLanguage += ";" + data.firstLanguage[i]
        	 			+ ":" + data.firstLanguage[i];
        	 	}
        	 	
        	 	comprehensiveRace = ":All";
           	 	for (i=0; i<data.comprehensiveRace.length; i++) {
           	 	comprehensiveRace += ";" + data.comprehensiveRace[i]
           	 			+ ":" + data.comprehensiveRace[i];
           	 	}
           	 	
           	 	primaryDisabilityCode = ":All";
        	 	for (i=0; i<data.primaryDisabilityCode.length; i++) {
        	 		primaryDisabilityCode += ";" + data.primaryDisabilityCode[i]
        	 			+ ":" + data.primaryDisabilityCode[i];
        	 	}
        	 	generationCode = ":All";
        	 	for (i=0; i<data.generationCode.length; i++) {
        	 		generationCode += ";" + data.generationCode[i]
        	 			+ ":" + data.generationCode[i];
        	 	}
        	 	gender = ":All";
           	 	for (i=0; i<data.gender.length; i++) {
           	 	gender += ";" + data.gender[i]
           	 			+ ":" + data.gender[i];
           	 	}
           	 	gradeCourse = ":All";
           	 	for (i=0; i<data.gradeCourse.length; i++) {
           	 	gradeCourse += ";" + data.gradeCourse[i]
           	 			+ ":" + data.gradeCourse[i];
           	 	}	           	 	
           	 	attendanceSchoolName = ":All";
           	 	for (i=0; i<data.attendanceSchoolName.length; i++) {
           	 	attendanceSchoolName += ";" + data.attendanceSchoolName[i]
           	 			+ ":" + data.attendanceSchoolName[i];
           	 	}
           	 	rosterId = ":All";
           	 	for (i=0; i<data.rosterId.length; i++) {
           	 	rosterId += ";" + data.rosterId[i]
           	 			+ ":" + data.rosterId[i];
           	 	}
           	 	courseEnrollmentStatus = ":All";
           	 	for (i=0; i<data.courseEnrollmentStatus.length; i++) {
           	 	courseEnrollmentStatus += ";" + data.courseEnrollmentStatus[i]
           	 			+ ":" + data.courseEnrollmentStatus[i];
           	 	}
	        });
	    }
	   
	 
	//Load students recordbrowser data only on Students tab selection.
	//This method call would be in setupTestSession.jsp.
	function loadStudentsData() {
		var testSessionId = $("#testSessionId").val();
	    if(testSessionId != null && testSessionId != "") {
			isEdit = true;
	    } else {
	    	isEdit = false;
	    }
		<security:authorize  access="hasRole('PERM_ROSTERRECORD_VIEW') and hasRole('PERM_STUDENTRECORD_VIEW')" >                		
	    
		var grid = $("#rosterStudentsTableId");
		//getValuesDropDown();
		var cmForStudentDetails = [ 
		               			{ name : 'id', key: true, index : 'id', width : cell_width, editable : true, 
		               				editrules:{edithidden:true}, viewable: false, editoptions:{readonly:true,size:10}, 
		               				sorttype : 'int', search : true, hidden: true, hidedlg : true
		               			},

		               			{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', width : cell_width, editable : true, 
		               				editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, 
		               				sortable : true, search : true, hidden : false, hidedlg : true
		               			},

		               			{ name : 'localStudentIdentifier', index : 'localStudentIdentifier', width : cell_width, 
		               				editable : true, editrules:{edithidden:true}, viewable: true, 
		               				editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true
		               			},

		               			{ name : 'legalFirstName', index : 'legalFirstName', width : cell_width, editable : true,
		               				editrules:{edithidden:true}, viewable: true, 
               						editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true 
		               			},

		               			{ name : 'legalMiddleName', index : 'legalMiddleName', width : cell_width, editable : true, 
		               				editrules:{edithidden:true}, viewable: true, 
		               				editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true
		               			},

		               			{ name : 'legalLastName', index : 'legalLastName', width : cell_width, editable : true, 
		               				editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, 
		               				hidden : false, hidedlg : true, formatter: lastNameLinkFormatter, unformat: lastNameLinkUnFormatter
		               			},

		               			{ name : 'generationCode', index : 'generationCode', width : cell_width, editable : true, 
		               				editrules:{edithidden:true}, viewable: true, 
		               				editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

		               			{ name : 'gender', index : 'gender', width : cell_width, editable : true, 
		               					editrules:{edithidden:true}, viewable: true, 
		               					editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

		               			{ name : 'dateOfBirth', index : 'dateOfBirth', width : cell_width, editable : true, 
		               						editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, 
		               						formatter: function (cellvalue, options, rowObject) {
		               				if(cellvalue != "Not Available") { var date = new Date(cellvalue);  return $.datepicker.formatDate("mm/dd/yy", date); } else { return cellvalue; } }, sortable : true, search : true, hidden : true },									

		               			{ name : 'gradeCourseName', index : 'gradeCourseName',label:'gradeCourseNameStudent', width : cell_width, 
		               					editable : true, editrules:{edithidden:true}, viewable: true, 
		               					editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false, hidedlg : true}, 

		               			{ name : 'courseSectionName', index : 'courseSectionName', width : cell_width, editable : true, 
		               				editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, 
		               				sortable : true, search : true, hidden : false, hidedlg : true
								},
								
								{ name : 'stateSubjectAreaName', index : 'stateSubjectAreaName', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : true, hidden : false },
								
								{ name : 'stateCourseName', index : 'stateCourseName', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : true, hidden : false },
		               			
		               			{ name : 'attendanceSchoolIdentifier', index : 'attendanceSchoolIdentifier',
		               					width : cell_width, search : true,
		               					label: 'attendanceSchoolIdentifier', hidden : false },
		               					
		               			{ name : 'attendanceSchoolName', index : 'attendanceSchoolName', width : cell_width, editable : true, 
		               						editrules:{edithidden:true}, viewable: true,  
		               						editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },		               						

		               			{ name : 'currentSchoolYear', index : 'currentSchoolYear', width : cell_width, editable : true, editrules:{edithidden:true}, sortable : true, 
	   									search : true, hidden : false,viewable: true, editoptions:{readonly:true,size:10} },

		               			{ name : 'firstLanguage', index : 'firstLanguage', width : cell_width, editable : true, editrules:{edithidden:true}, 
	   									viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

		               			{ name : 'comprehensiveRace', index : 'comprehensiveRace', width : cell_width, editable : true, editrules:{edithidden:true}, 
	   									viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

		               			{ name : 'residenceDistrictIdentifier', index : 'residenceDistrictIdentifier', width : cell_width, editable : true, 
	   									editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true
		               			},

		               			{ name : 'primaryDisabilityCode', index : 'primaryDisabilityCode', width : cell_width, editable : true, editrules:{edithidden:true}, 
		               					viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

		               			{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier', width : cell_width, editable : true, editrules:{edithidden:true}, 
		               					viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false		
		               			},

		               			{ name : 'educatorFirstName', index : 'educatorFirstName', width : cell_width, editable : true, editrules:{edithidden:true}, 
		               					viewable: true, editoptions:{readonly:true,size:10}, search : true, hidden : false
		               			},

		               			{ name : 'educatorLastName', index : 'educatorLastName', width : cell_width, editable : true, editrules:{edithidden:true}, 
		               					viewable: true, editoptions:{readonly:true, size:10}, search : true, hidden : false	
		               			},

		               			{ name : 'courseEnrollmentStatus', index : 'courseEnrollmentStatus', width : cell_width, editable : true, editrules:{edithidden:true}, 
		               					viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

		               			{ name : 'rosterId', index : 'rosterId', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: false, 
		               					editoptions:{readonly:true,size:10}, summaryType : 'count', summaryTpl : '({0}) total', 
		               					sortable : true, search : true,  hidden: true, hidedlg : true }, 
		               			
		               			{ name : 'testSessionId', index : 'testSessionId', width : cell_width, editable : true, editrules:{edithidden:true}, 
		               					viewable: false, editoptions:{readonly:true}, sortable : true, search : true, hidden: true, hidedlg : true },

		               			{ name : 'enrollmentStatus', index : 'enrollmentStatus', width : cell_width, editable : true, editrules:{edithidden:true}, 
		               					viewable: false, editoptions:{readonly:true}, sortable : true, search : true, hidden: true, hidedlg : true },

		               			{ name : 'status', index : 'status', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: false, 
		               					editoptions:{readonly:true}, sortable : true, search : true, hidden: true, hidedlg : true },
		               			
		               			{ name : 'assessmentPrograms', index : 'assessmentPrograms',  width : cell_width, edittype:'textarea', editable : true, 
		               					editrules:{edithidden:true}, viewable: true,  editoptions:{readonly:true}, sortable : true, 
		               					search : true, hidden : false, hidedlg : true}
			               		<c:if test="${testSessionId != null }">
	               					<c:if test="${fn:length(specialCircumstanceList) > 0 }">
										<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE') || hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
		               						,{ name : 'specialCircumstance', index : 'specialCircumstance', width : cell_width + 50, editable : true, 
			               						editrules:{edithidden:true}, sortable : false, search : false, hidden : ${!highStakesFlag}, viewable:${highStakesFlag}, 
												editoptions:{readonly:true}, formatter: specialCircumstanceFormatter, unformat: specialCircumstanceUnFormatter,hidedlg : true  },
											{ name : 'specialCircumstanceStatus', index : 'specialCircumstanceStatus', width : cell_width, editable : true, 
			               						editrules:{edithidden:true}, sortable : false, search : false, hidden : ${!highStakesFlag}, viewable:${highStakesFlag}, 
												editoptions:{readonly:true,size:10}, formatter: specialCircumstanceStatusFormatter, unformat: specialCircumstanceStatusUnFormatter ,hidedlg : true }
		               					</security:authorize>
		               					<c:if test="${specialCircumstanceApprovalVisible}">
			               					<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
			               						,{ name : 'specialCircumstanceApproval',  search: false, index : 'specialCircumstanceApproval', width : cell_width + 50, 
			               							formatter: specialCircumstanceApprovalFormatter, unformat: specialCircumstanceApprovalUnFormatter,
			               				 			sortable : false, hidden : ${!highStakesFlag} , hidedlg : true }
			               					</security:authorize>
		               					</c:if>
	               					</c:if>
	               				</c:if>	
		               		];
		$grid= $('#rosterStudentsTableId');           		
		myDefaultSearch = 'cn',
        
        myColumnStateName = 'ColumnChooserAndLocalStorage4.colState4';
        
        var myColumnsState;
        var isColState;
 
        firstLoad = true;

    myColumnsState = restoreColumnState(cmForStudentDetails, myColumnStateName);
    isColState = typeof (myColumnsState) !== 'undefined' && myColumnsState !== null;
		if(testSessionId == null  || testSessionId == "") {
			checkgroup = false;
			groupFieldName = 'rosterId';
			$('.nextButtonDiv').show();
			$('#saveButtonDiv').hide();
			$('#newInstructionsDiv').show();
			$('#editInstructionsDiv').hide();
		} else {
			checkgroup = true;
			groupFieldName = 'enrollmentStatus';
			var testSessionName1 = $("#testSessionName1").val();
			//checkgroup = true;
			$('#breadCrumMessage').text("View/Edit Test Session: "+testSessionName1);
			$('#breadCrumMessageTag').text("Choose the students to register to the selected test below. Click SAVE to continue");
			$("#gview_rosterStudentsTableId > .ui-jqgrid-titlebar").hide();
			
			$('#saveButtonDiv').show();
			
			$('.nextButtonDiv').hide();
			$('#newInstructionsDiv').hide();
			$('#editInstructionsDiv').show();
		}

		if($("#rosterStudentsTableId").getGridParam('reccount') == undefined || 
				$("#rosterStudentsTableId").getGridParam('reccount') < 1) {
			
			//Need to set this in the session so that loggedInUserId would be used as a key to store the orgs.
			window.sessionStorage.setItem("loggedInUserId", $('#loggedInUserId').val() );
			
			//Capture the mouse position for Loading message display.
			$(document).on("mousemove",function(e) {
				mousePosition = e.pageY;
			});

			/**
			  * Biyatpragyan Mohanty (bmohanty_sta@ku.edu) : DE4134: Next buttons are disabled if a test is previewed
			  * DE4421 : Record browser fails to load on Register Student page in IE8 after previewing a test
			  * somehow grid looses the width when pre view nodes overlay is opened. There is no other way to get the width,
			  * so added this condition, this will not affect any other conditions.
			  */
			var grid_width = $('.kite-table').width();
			if(grid_width == 100 || grid_width == 0){
				grid_width = 719;				
			}
			grid_width=1031;
			var cell_width = 250;
			 
			//JQGRID   
			jQuery("#rosterStudentsTableId").scb({
				mtype: "POST",
				datatype : "local",				
				width: grid_width,
				colNames : [
					'Enrollments Rosters ID', 'State Student Identifier', 'Local ID', 'First Name', 'Middle Name', 'Last Name', 'Generation', 'Gender', 
					'Date of Birth', 'Grade', 
					'Roster', 'Roster Subject', 'Roster Course', 'School ID', 'School Name', 'Current School Year', 'First Language', 'Comp Race', 'District ID', 'Disability', 'Educator ID', 
					'Educator First Name', 'Educator Last Name', 'Enrollment Status', 'Roster ID','Test Session ID','Status','Status Id', 
					'Assessment Program'
					<c:if test="${testSessionId != null }">
						<c:if test="${fn:length(specialCircumstanceList) > 0 }">
							<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE') || hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
								, 'Special Circumstance', 'Special Circumstance Status'
							</security:authorize>
							<c:if test="${specialCircumstanceApprovalVisible}">
								<security:authorize access="hasRole('HIGH_STAKES_SPL_CIRCUM_CODE_SEL')">
									, 'Special Circumstance Approval'
								</security:authorize>
							</c:if>
						</c:if>
					</c:if>	
				],
				colModel : cmForStudentDetails,
				
				rowNum : 30,
				rowList : [ 5,10, 20, 30, 40, 60, 90 ],
				height : 'auto',
				pager : '#prosterStudentsTableId',
				page: isColState ? myColumnsState.page : 1,
				search: isColState ? myColumnsState.search : false,
				sortname: isColState ? myColumnsState.sortname : 'legallastname,legalfirstname',
		    	sortorder: isColState ? myColumnsState.sortorder : 'asc',
			 		    		sortable: {
			 			        	update: function() {
			 			        		saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
			 			        	}
			 			        },
				viewrecords : true,
				multiselect: true,
				caption : "",
				grouping : checkgroup,
				shrinkToFit : false,
				groupingView : {
					groupField : [ groupFieldName ],
					groupColumnShow : [ false ],
					groupText : [ '<b>{0}</b>'],
					groupCollapse : false,
					groupOrder : [ 'asc' ],
					groupSummary : [ true ],
					groupDataSorted : true
				},
				footerrow : true,
				userDataOnFooter : true,
				loadBeforeSend: function () {
					//Code to position "Loading...." message.
			    	var gridIdAsSelector = $.jgrid.jqID(this.id),
			        $loadingDiv = $("#load_" + gridIdAsSelector),
			        $gbox = $("#gbox_" + gridIdAsSelector);
			    	$loadingDiv.show().css({
			    		top:  (Math.min($gbox.height(), mousePosition) - 200) + 'px',
			        	left: (Math.min($gbox.width(), $(window).width()) - $loadingDiv.outerWidth())/2 + 'px'
			    	});							    	
			    },
			    loadComplete: function () {
                    var $this = $(this);
                    if (firstLoad) {
                        firstLoad = false;
                        if (isColState) {
                            $this.jqGrid("remapColumns", myColumnsState.permutation, true);
                        }
                        if (typeof (this.ftoolbar) !== "boolean" || !this.ftoolbar) {
                            // create toolbar if needed
                            $this.jqGrid('filterToolbar',
                                {stringResult: true, searchOnEnter: true, defaultSearch: myDefaultSearch});
                        }
                    }
                 var ids = $(this).jqGrid('getDataIDs');         
   		         var tableid=$(this).attr('id');      
   		            for(var i=0;i<ids.length;i++)
   		            {         
   		                $('#jqg_'+tableid+'_'+ids[i]).attr('title',$($(this).getCell(ids[i], 'legalLastName')).text()+' '+ $(this).getCell(ids[i], 'legalFirstName')+ ' Check Box');
   		            }
   		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
   		             $('#cb_'+tableid).attr('title','Student Grid All Check Box');
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
                resizeStop: function () {
                    saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
                },
			    gridComplete: function() {
			    	//alert('dataType:'+ grid.getGridParam('datatype'));
			    	if(grid.getGridParam('datatype') == 'json'){
					    var recs = parseInt($("#rosterStudentsTableId").getGridParam("records"));
					 	
						if (isNaN(recs) || recs == 0) {
						     $("#gbox_rosterStudentsTableId").hide();
						     $("#noReport").show();
						     $("#noReport").html('No student records found.');
						 } else {
						     $("#gbox_rosterStudentsTableId").show();
						     $("#noReport").hide();
						     $("#noReport").html('');
						 }
	
						
			        	if(disabledStudents) {
			        		$.each(disabledStudents, function (index, value) {
			        			//$("#rosterStudentsTableId").setSelection(value, false);
			        			$("#jqg_rosterStudentsTableId_"+value).attr('disabled',true);
			        		});
			        	}
	
			        	//Selection of checkboxes if the student is enrolled in the test session
			    		
			    		var gridIds = $("#rosterStudentsTableId").jqGrid('getDataIDs');
			    		var exists = false;
			    	    for(var i = 0; i < gridIds.length; i++) {
			    	    	exists = false;
			    	    	if(unenrolledStudents) {
				    	    	$.each(unenrolledStudents, function (index, value) {							   
				        			if(value == gridIds[i]) {
				        				exists = true;
				        			}
				        		});
			    	    	}
				    	    if(!exists) {
				    	    	var rowid = gridIds[i];
				    	        var gridRow = grid.jqGrid('getRowData',rowid);
				    	        var testSession = gridRow['testSessionId'];
				    	        var statusId = gridRow['status'];
				    	        if(testSession != "Not Available") {
				    	        	$("#rosterStudentsTableId").setSelection(rowid, false);
				    	        	//Push all Enrolled students and disable the checkboxes for tests Completed and Inprogress
				    	        	//totalEnrolledStudents.push(gridRow['enrollmentsRostersId']);
				    	        	if(statusId == "In Progress" || statusId == "Complete") {
				    	        		$("#jqg_rosterStudentsTableId_"+rowid).attr('disabled',true);
				    	        		disabledStudents.push(gridRow['id']);
				    	        	} else {
				    	        		totalEnrolledStudents.push(gridRow['id']);
				    	        	}
				    	        }
			    	    	}
			    	    	if($("#testSessionSource").val() == 'BATCHAUTO'){
			    	    		$("#jqg_rosterStudentsTableId_"+rowid).attr('disabled',true);
			    	    	}
			    	    	<security:authorize access="hasRole('PERM_TESTSESSION_MODIFY') == false">
			    	    		$("#jqg_rosterStudentsTableId_"+rowid).attr('disabled',true);
			    	    	</security:authorize>
			    	    }
			    	    
			    	  	//Check SelectAllCheckbox header on rowNum change. 
			        	var recordCount = $("#rosterStudentsTableId").getGridParam('reccount');
			        	var checkboxChecckedCount = $("input[type='checkbox']:checked").length;
			        	if(recordCount == checkboxChecckedCount) {
			        		$("#cb_rosterStudentsTableId").attr("checked", "checked");
			        	}
			        	// Disable select all if there is no edit test session permission.
			        	<security:authorize access="hasRole('PERM_TESTSESSION_MODIFY') == false">
			        		$("#cb_rosterStudentsTableId").attr("disabled", true);
		        		</security:authorize>
			    	    if($("#testSessionSource").val() == 'BATCHAUTO'){
			    	    	$("#cb_rosterStudentsTableId").attr('disabled',true);
			    	    }
			        	
		                if (isNaN(recordCount) || recordCount == 0) {
		                     //Set min height of 1px on no records found
		                     $('.jqgfirstrow').css("height", "1px");
		                }			        	
			    	}			    	
				},
				beforeSelectRow: function(rowid, e) {
					// Disable select row if no edit test session permission.
					<security:authorize access="hasRole('PERM_TESTSESSION_MODIFY') == false">
				    return false;
				    </security:authorize>
				    // Disable select row if session is of type BATCHAUTO
				    if( $("#testSessionSource").val() == 'BATCHAUTO')
				    {
				    	return false;
				    }
				    return true;
				},
				onSelectRow: function(id, status) {
	        		//Add/remove items to/from selectedStudents array based on checkbox selection/deselection. 
	        		if(status) {

	        			var existing = false;
	        			
	        			//User checks the student from Other Students(Just making sure that 
	        			  //the student is not there initially in the Enrolled students).
			        	for(var i=0; i< totalEnrolledStudents.length; i++) {							   
		        			if(totalEnrolledStudents[i] == id) {
		        				existing = true;		        				
		        			}
		        		}
			        	if(jQuery.inArray(id, selectedStudents)==-1) {
			        		selectedStudents.push(id);
			        		selectedStudentsNum ++;
			        	}

			        	//User rechecks the student from Enrolled Students.			        	
        				unenrolledStudents= $.grep(unenrolledStudents, function(value) {
							  return value != id;
						});
			        	
					} else {
						
						//User unchecks the student from Other Students.
						selectedStudents = $.grep(selectedStudents, function(value) {
							  return value != id;
						});
						
						//User unchecks the student from Enrolled Students.
						$.each(totalEnrolledStudents, function (index, value) {							   
		        			if(value == id) {
		        				unenrolledStudents.push(id);
		        				selectedStudentsNum --;
		        			}
		        		});
					}					

	        		//Check SelectAllCheckbox header on rowNum change. 
		        	var recordCount = $("#rosterStudentsTableId").getGridParam('reccount');
		        	var checkboxChecckedCount = $("input[type='checkbox']:checked").length;
		        	if(recordCount == checkboxChecckedCount) {
		        		$("#cb_rosterStudentsTableId").attr("checked", "checked");
		        	}
		        	
	        		//Logic to enable the Next button after selecting atleast one student.
	        		changeButtonState(id, status);	        		     		
	        		
				},
				onSelectAll: function(id, status) {
	        		//Add/remove items to/from selectedStudents array based on checkbox selection/deselection.
	        		if(status) {
	        			var existing = false;
						for(var i=0; i<id.length; i++) {
							existing = false;
							//Add id's to selectedStudents that are not present in totalEnrolledStudents.
							$.each(totalEnrolledStudents, function(index, value) {
								if(value == id[i]) {
									existing = true;
								}								
	        				});
							
							if(!existing) {
								existing = false;
								//Check if the student exists in disabledStudents array,if not then add to selectedStudents
								$.each(disabledStudents, function(index, value) {
									if(value == id[i]) {
										existing = true;
									}								
		        				});
								
								if(jQuery.inArray(id[i], selectedStudents)==-1) {
									selectedStudents.push(id[i]);
									selectedStudentsNum ++;
								}
							}
						}
	        		} else {
	        			var existing = false;
	        			for(var i=0; i<id.length; i++) {
	        				
	        				existing = false;
	        				$.each(disabledStudents, function(index, value) {
								if(value == id[i]) {
									existing = true;
								}								
	        				});
	        				
	        				//Don't uncheck current id if its there in the disabledStudents array
	        				if(!existing) {
		        				existing = false;
		        				selectedStudents = $.grep(selectedStudents, function(value) {
									return value != id[i];
		        				});
		        				
		        				//User unchecks the student from Enrolled Students.
								$.each(totalEnrolledStudents, function (index, value) {							   
				        			if(value ==  id[i]) {
				        				existing = true;			        				
				        			}
				        		});
		        				
								if(existing) {
									unenrolledStudents.push(id[i]);
									selectedStudentsNum --;
								}
	        				} else {
	        					$("#rosterStudentsTableId").setSelection(id[i], false);
	        					$("#jqg_rosterStudentsTableId_"+id[i]).attr('disabled',true);
	        				}
	        			}
	        		}
	        		
	        		//Check SelectAllCheckbox header on rowNum change. 
		        	var recordCount = $("#rosterStudentsTableId").getGridParam('reccount');
		        	var checkboxChecckedCount = $("input[type='checkbox']:checked").length;
		        	if(recordCount == checkboxChecckedCount) {
		        		$("#cb_rosterStudentsTableId").attr("checked", "checked");
		        		
		        	}
		        	
	        		//Logic to enable the Next button after selecting atleast one student.
					changeButtonState();
	        		
				},
				beforeRequest: function () {
					
					if(!isTeacher && !isEdit && $grid.getGridParam('datatype') == 'json'){
						if(!$('#searchFilterForm').valid()) {return false;}
					}
					
					//Set the page param to lastpage before sending the request when 
					  //the user entered current page number is greater than lastpage number.
					var currentPage = $(this).getGridParam('page');
	                var lastPage = $(this).getGridParam('lastpage');
	                
	                 if (lastPage!= 0 && currentPage > lastPage) {
	                	 $(this).setGridParam('page', lastPage);
	                	$(this).setGridParam({postData: {testSessionId : testSessionId, page : lastPage}});
	                } else {
						//Set the testSessionId param before sending the request because
					  		//this is not the default param for recordbrowser. 
						$(this).setGridParam({postData: {testSessionId : testSessionId}});
	                }
					
				} //end of beforeRequest
				
			}); //end of jqgrid
			
			 
		} //end of jqgrid recordcount if condition
		</security:authorize>
		$.extend($.jgrid.search, {
		    multipleSearch: true,
		    multipleGroup: true,
		    recreateFilter: true,
		    closeOnEscape: true,
		    closeAfterSearch: true,
		    overlay: 0
		});
		$grid.jqGrid('navGrid', '#prosterStudentsTableId', {edit: false, add: false, del: false}); 
		
	    if(isEdit || isTeacher) {	    	
			$("#searchFilterContainer").hide();
			loadGridStudents();			
	    }		
	} //end of loadStudentsData() method
	
	//Custom formatter for lastname link. 
	function lastNameLinkFormatter(cellvalue, options, rowObject) {			
		return '<a href="javascript:viewRowDetails(\'' + options.rowId + '\');">' + cellvalue + '</a>';	    
	}


	//Custom unformatter for lastname link.
	function lastNameLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	function selectSpecialCircumstance(studentId, firstName, lastName, isApprovalAction){
		var scSelectedValue = $("#specialCircumstanceListSelect"+studentId).val();
		var requireConfirmation = false;
		var hasSpecialCircumstanceApprovalPermission = $('#hasSpecialCircumstanceApprovalPermission').val();
		// Only when selecting approval action
		if(isApprovalAction) {
			var scValue = $("#specialCircumstanceApprovalListSelect"+studentId).val();
			requireConfirmation=true;
			pushSpecialCircumstanceApprovalValue(studentId, scValue, requireConfirmation, isApprovalAction, scSelectedValue);
		} else {
			// When BTC, DTC, SAA, SSA selecting SC code
			for(var i = 0; i < specialCircumstanceConfirmationList.length; i++) {
				if(specialCircumstanceConfirmationList[i].id==scSelectedValue && specialCircumstanceConfirmationList[i].requireConfirmation=="true")
				{
					requireConfirmation = true;
					isApprovalsRequired = true;
					if($('#approvalRequiredStudents').find('#'+studentId) && $('#approvalRequiredStudents').find('#'+studentId).length > 0){
						// do nothing
					} else {
						$('#approvalRequiredStudents').append('<div id="'+studentId+'"><span style="margin-right:1%;">'+unescape(firstName)+'</span><span>'+unescape(lastName)+'</span></div>');
					}
					
				} else if(specialCircumstanceConfirmationList[i].id==scSelectedValue && specialCircumstanceConfirmationList[i].requireConfirmation=="false"){
					$('#approvalRequiredStudents').find('#'+studentId).remove();
				}
			}
			pushSpecialCircumstanceValue(studentId, scSelectedValue, requireConfirmation, isApprovalAction);
		}
		
	}
	function pushSpecialCircumstanceValue(studentId, scSelectedValue, requireConfirmation, isApprovalAction){
		if(studentSpecialCircumstances.length > 0){
			for(var index =0; index < studentSpecialCircumstances.length; index++){
				if(studentSpecialCircumstances[index].studentId === studentId){
					studentSpecialCircumstances[index].specialCircumstanceValue =  scSelectedValue;
					studentSpecialCircumstances[index].requireConfirmation = requireConfirmation;
					studentSpecialCircumstances[index].isApprovalAction = isApprovalAction;
				} else {
					studentSpecialCircumstances.push({"studentId":studentId ,
						"specialCircumstanceValue":scSelectedValue,
						"requireConfirmation": requireConfirmation,
						"isApprovalAction": isApprovalAction
					});
				}
			}
		} else {
			studentSpecialCircumstances.push({"studentId":studentId ,
				"specialCircumstanceValue":scSelectedValue,
				"requireConfirmation": requireConfirmation,
				"isApprovalAction": isApprovalAction
			});
		}
	}
	function pushSpecialCircumstanceApprovalValue(studentId, scSelectedApprovalValue, requireConfirmation, isApprovalAction, scSelectedValue){
		if(studentSpecialCircumstances.length > 0){
			for(var index =0; index < studentSpecialCircumstances.length; index++){
				if(studentSpecialCircumstances[index].studentId === studentId){
					studentSpecialCircumstances[index].specialCircumstanceApprovalValue =  scSelectedApprovalValue;
					studentSpecialCircumstances[index].requireConfirmation = requireConfirmation;
					studentSpecialCircumstances[index].isApprovalAction = isApprovalAction;
					studentSpecialCircumstances[index].specialCircumstanceValue =  scSelectedValue;
				} else {
					studentSpecialCircumstances.push({"studentId":studentId ,
						"specialCircumstanceApprovalValue":scSelectedApprovalValue,
						"requireConfirmation": requireConfirmation,
						"isApprovalAction": isApprovalAction,
						"specialCircumstanceValue": scSelectedValue
					});
				}
			}
		} else {
			studentSpecialCircumstances.push({"studentId":studentId ,
				"specialCircumstanceApprovalValue":scSelectedApprovalValue,
				"requireConfirmation": requireConfirmation,
				"isApprovalAction": isApprovalAction,
				"specialCircumstanceValue": scSelectedValue
			});
		}
	}
	function specialCircumstanceFormatter(cellvalue, options, rowObject) {
		var htmlString = "N.A.";
		var disabledAttrib = '';
		var statusName = rowObject[30];
		if(statusName === 'Approved'|| statusName === 'Not Approved'){
 			disabledAttrib = ' disabled="disabled" style="background-color:#e5e5e5;" ';
 	}
		if(cellvalue != null && cellvalue !== undefined && cellvalue !== '' && cellvalue !== 'Not Available'){
			if(isApprovalRequired(cellvalue, rowObject)){
			disabledAttrib = ' disabled="disabled" style="background-color:#e5e5e5;" ';
			}
		}
		if(rowObject[26] != null && rowObject[26] === 'Enrolled Students'){
			htmlString = '<div id="specialCircumstancesDiv'+rowObject[31]+'"><c:if test="${fn:length(specialCircumstanceList) > 0 }">'
			+'<select style="width:150px;" onChange="selectSpecialCircumstance('+rowObject[31]+',\''+escape(rowObject[3])+'\',\''+escape(rowObject[5])+'\', false);" data-cellvalue="'
			+cellvalue+'" title="Special Circumstance List '+rowObject[31]+'" id="specialCircumstanceListSelect'+rowObject[31]+'" name="specialCircumstanceList'+rowObject[31] 
			+ '" '+disabledAttrib +' title="" class="bcg_select">'+
			'<option value="" title="">Select</option><c:if test="${fn:length(specialCircumstanceList) > 0 }">'			
			+'<c:forEach items="${specialCircumstanceList}" var="specialCircumstance" varStatus="index">'
			+'<option value="${specialCircumstance.id}" title="${specialCircumstance.description}">${specialCircumstance.description}</option></c:forEach></c:if>'
			+'</select></c:if></div>';
			index++;
		}
		if(cellvalue!=''){
			if(htmlString.indexOf('<option value="'+cellvalue+'"')!=-1){
				htmlString = htmlString.replace('<option value="'+cellvalue+'"','<option value="'+cellvalue+'" selected ');
			}
		}
	    return htmlString;
	}

	//Custom unformatter for special circumstance link.
	function specialCircumstanceUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	function specialCircumstanceStatusFormatter(cellvalue, options, rowObject) {
		var htmlString = 'N.A.' + '<img alt="'+cellvalue+'" id="sscImg'+rowObject[31]+'" />';
		if(cellvalue === 'Saved') {
			htmlString = '<img id="sscImg'+ rowObject[31] +'" alt="'+cellvalue+'" class="specialCircumstanceStatus" src="images/saved.png"/>';
		} else if(cellvalue === 'Approved') {
			htmlString = '<img id="sscImg'+ rowObject[31] +'" alt="'+cellvalue+'" class="specialCircumstanceStatus" src="images/approved.png"/>';
		} else if(cellvalue === 'Not Approved') {
			htmlString = '<img id="sscImg'+ rowObject[31] +'" alt="'+cellvalue+'" class="specialCircumstanceStatus" src="images/notApproved.png"/>';
		} else if(cellvalue === 'Pending' || cellvalue === 'Pending Further Review') {
			htmlString = '<img id="sscImg'+ rowObject[31] +'" alt="'+cellvalue+'" class="specialCircumstanceStatus" src="images/pending.png"/>';
		}
	    return htmlString;
	}
	
	//Custom unformatter for special circumstance link.
	function specialCircumstanceStatusUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	function specialCircumstanceApprovalFormatter(cellvalue, options, rowObject) {
		var htmlString = "N.A.";
		var statusName = rowObject[30];
		var disabledAttrib = '';
		if(statusName === 'Not Available' || statusName === 'Saved'){
			disabledAttrib = ' disabled="disabled" style="background-color:#e5e5e5;" ';
		}
		if(rowObject[26] != null && rowObject[26] === 'Enrolled Students'){
			htmlString = '<div id="specialCircumstanceApprovalDiv'+rowObject[31]+'">	'
			+'<select style="width:150px;" onChange="selectSpecialCircumstance('+rowObject[31]+',\''+escape(rowObject[3])+'\',\''+escape(rowObject[5])+'\', true);" data-cellvalue="'
			+cellvalue+'" id="specialCircumstanceApprovalListSelect'+rowObject[31]+'" title="Special Circumstance Approval'+rowObject[31]+'" name="specialCircumstanceApprovalList'+rowObject[31]+'" class="bcg_select"  '+disabledAttrib +'>'+
			'<option value="" title="">Select</option><c:if test="${fn:length(specialCircumstanceApprovalList) > 0 }">'			
				+'<c:forEach items="${specialCircumstanceApprovalList}" var="specialCircumstanceApproval" varStatus="index">'
				+'<option value="${specialCircumstanceApproval.id}"'
				+' title="${specialCircumstanceApproval.categoryName}">${specialCircumstanceApproval.categoryName}</option>'
				+'</c:forEach></c:if></select></div>';
		}
		if(cellvalue!=''){
			if(htmlString.indexOf('<option value="'+rowObject[31]+'"')!=-1){
				htmlString = htmlString.replace('<option value="'+rowObject[31]+'"','<option value="'+rowObject[31]+'" selected ');
			}
		}
		index++;
	    return htmlString;
	}
	
	//Custom unformatter for special circumstance link.
	function specialCircumstanceApprovalUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	function isApprovalRequired(specialCircumstanceValue, rowObject){
		var statusName = rowObject[30];
		if(statusName !== null && statusName !== undefined){
			return false;
		}
		for(var i = 0; i < specialCircumstanceConfirmationList.length; i++) {
			if(specialCircumstanceConfirmationList[i].id==specialCircumstanceValue && specialCircumstanceConfirmationList[i].requireConfirmation=="true")
			{
				return true;
			}
		}
		return false;
	}
	
	//Show row details on clicking the lastname value.
	function viewRowDetails(rowId) {
		$("#rosterStudentsTableId").jqGrid('viewGridRow', rowId ,{width: 700,recreateForm:true});
		$("#pData").attr("title","Previous");
		$("#nData").attr("title","Next");
	}
	

	//Code to set the position of the AlertMod warning message (Please, select a row)
	var orgViewModal = $.jgrid.viewModal;
	$.extend($.jgrid,{
	    viewModal: function (selector,o){ 
	        if(selector == '#alertmod'){
	            var of = jQuery(o.gbox).offset();       
	            var w = jQuery(o.gbox).width();       
	            var h = jQuery(o.gbox).height(); 
	            var w1 = $(selector).width();	                     
	            $(selector).css({	            	
	                'top':of.top+(h - 300),	                
	                'left':of.left+((w-w1)/2)
	            });
	        }
	        orgViewModal.call(this, selector, o);
	    }
	});


	//Logic to enable the Next button after selecting atleast one student.
	function changeButtonState() {		
		if(selectedStudents.length > 0) {
			$('.studentsNextButton').prop('disabled', false);
		} else {        			
			//$('.studentsNextButton').attr('disabled','disabled');
		}   
		
		if(selectedStudents.length > 0) {
			$('.studentsNextButton1').prop('disabled', false);
		}
	}

	function editSession(){
		var unstulength = selectedStudents.length;
		var unenrolledStuLength = unenrolledStudents.length;
		if(unstulength > 0){
			$("#confirmDialog").html('You have unsaved changes, do you wish to continue?');
			$("#confirmDialog").dialog('open');
		}else if(unenrolledStuLength > 0){
			$("#confirmDialog").html('You have unsaved changes, do you wish to continue?');
			$("#confirmDialog").dialog('open');
		}else{
			window.location.href = 'viewTestSessions.htm?sourcePage=editSession&saveGrid=save&showPage=TM';
		}
	}
	//Logic to enable the Save button after selecting atleast one student.
	$("#saveStudents").on("click",function() {
		var studentsList;
		var unenrolledStudentsList;
		var testSessionId;
        var validParams = true;
		studentsList = getSelectedStudentEnrollmentID();
		unenrolledStudentsList = unenrolledStudents;
		testSessionId = $("#testSessionId").val();
		
		if ((studentsList == undefined ||  studentsList.length <= 0 || studentsList == "") && 
				(unenrolledStudentsList == undefined || unenrolledStudentsList.length <= 0 || unenrolledStudentsList == "")) {
	    	validParams = false;        		
		}
		
		if(studentSpecialCircumstances != null && studentSpecialCircumstances.length > 0){
			if(isApprovalsRequired){
				$( "#confirmSaveSpecialCircumstanceDialog" ).dialog({
			        resizable: false,
			        modal: true,
			        width: 700,
			        title: 'Special Circumstance Code Selection Approval Required',
			        buttons: {
			          "OK": function() {
			            $( this ).dialog( "close" );
			            saveStudentInfo(testSessionId, validParams);
			          },
			          Cancel: function() {
			            $( this ).dialog( "close" );
			            $('#specialCircumstancesDiv select.bcg_select').val('');
			            // clear the selections if canceled.
			            studentSpecialCircumstances = [];
			            $('#approvalRequiredStudents').html('');
			            $("#rosterStudentsTableId").trigger('reloadGrid');
			          }
			        }
		      	});
			} else {
				saveStudentInfo(testSessionId, validParams);
			}
			isApprovalsRequired = false;
		}
		// Do not save if no edit test session permission
		<security:authorize access="hasRole('PERM_TESTSESSION_MODIFY')">
			if(validParams) {
				if($("#testSessionSource").val() == 'Not Available' || $("#testSessionSource").val() == 'MANUAL'){
					$.ajax({
						url: 'editTestSession.htm',
						data: {
							students: studentsList,
							unenrolledStudents: unenrolledStudentsList,
							testSessionId: testSessionId
						},
						dataType: 'json',
						type: "POST"
					}).done(function(response) {
						    if (response.valid) {
						     	window.location.href = 'viewTestSessions.htm?sourcePage=setupTestSessionStudentDetails&showPage=TM';
						    } else {
					            $('#test_session_error').show();
						    }
						 setTimeout("aart.clearMessages()", 3000);
					});
				}
			}
		</security:authorize>
	});
	
	$('#confirmDialog').dialog({
		resizable: false,
		height: 200,
		width: 500,
		modal: true,
		autoOpen:false,			
		title: "Warning - Unsaved Changes",
		buttons: {
		    No: function() {
		    	 $(this).dialog("close");
		    },
		    Yes: function() {
		    	 $(this).dialog("close");
		    	 window.location.href = 'viewTestSessions.htm?sourcePage=editSession&showPage=TM';
		    }
		}
		
	});
	
	function saveStudentInfo(testSessionId, validParams){
		$.ajax({
			url: 'saveSpecialCircumstances.htm',
			data: {
				testSessionId: testSessionId,
				studentSpecialCircumstances: JSON.stringify(studentSpecialCircumstances),
				highStakesTest: ${highStakesFlag},
			},
			dataType: 'json',
			type: "POST"
		}).done(function(response) {
				if (response.valid) {
					// clear selections after successful update.
			     	studentSpecialCircumstances = [];
			     	$('#approvalRequiredStudents').html('');
					// stay on the same page.
					loadGridStudents();
			    } else {	   		                    
		            $('#test_session_error').show();				        
			    }
			 	setTimeout("aart.clearMessages()", 3000);
		});
	}
	
	function loadGridStudents() {
		var schoolOrgId = "";
		var assessmentProgramId = $('#stsaAssessmentPrograms').val();
		if(!isTeacher) {
			schoolOrgId = $('#stssSchoolOrgs').val();				
		}
			
		//Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
		
		var pdata = grid.getGridParam("postData");
		if(typeof pdata  == 'undefined' || typeof pdata.filters == 'undefined') {
			pdata = {
				filters: ""
			};			
		}
		pdata.schoolOrgId = schoolOrgId;
		pdata.assessmentProgramId = assessmentProgramId;
		$grid.jqGrid('setGridParam',{
			datatype:"json", 
			url : 'getRosterStudentsByTeacher.htm?q=1', 					
			search: false,
			postData: pdata,
			gridComplete: function() {
				
				var rec = $("#rosterStudentsTableId").jqGrid('getGridParam', 'reccount');
				if(rec==null || rec=='undefined' || rec==0){
				     $("#noReport").show();
				     $("#noReport").html('No student records found.');
				}else{
					  $("#noReport").hide();
					  $("#noReport").html('');
				}
				 var ids = $(this).jqGrid('getDataIDs');         
		         var tableid=$(this).attr('id');      
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName')+ ' Check Box');
		                $('#jqg_'+tableid+'_'+ids[i]).removeAttr('aria-checked');
		            }
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		             $('#cb_'+tableid).attr('title','Student Grid All Check Box');
		             $('#cb_'+tableid).removeAttr('aria-checked');
		             $.each(objs, function(index, value) {         
		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                    $(value).attr('title',$(nm).text()+' filter');                          
		                    });
			}
		
		}).trigger("reloadGrid",[{page:1}]);
	}
	
	function CodeNameforCategoryCode(){
		
		// CodeName for CategoryCode from the database 
		var comprehensiveRaceDataCName = [];
		var comprehensiveRaceDataCCode = [];
		var firstLanguageDataCName = [];
		var firstLanguageDataCCode = [];

		$.ajax({
		  url: 'getStudentInformationRecordsForCategoryCodeName.htm',
		  dataType: 'json',
		  type: 'POST'
		}).done(function (data) {
		    $.each(data.comprehensiveRaceList, function (index, value) {
		      comprehensiveRaceDataCName[index] = value.categoryName;
		      comprehensiveRaceDataCCode[index] = value.categoryCode;
		    });
		    $.each(data.firstLanguageList, function (index, value) {
		      firstLanguageDataCName[index] = value.categoryName;
		      firstLanguageDataCCode[index] = value.categoryCode;
		    });
		}).fail(function () {
			  console.log("Error Happended in Changing the Category Code to Category Name!");
		});
		setTimeout(function(){
			$.each($('#rosterStudentsTableId').jqGrid('getRowData'), function (index, value) {
				  var rowId = value.id;
				  var rowData = $('#rosterStudentsTableId').getRowData(rowId);
				  if (rowData.gender != 'Not Available') {
				    if (rowData.gender == 0) {
				      rowData.gender = 'Female'
				    } else if (rowData.gender == 1) {
				      rowData.gender = 'Male'
				    }
				  }
				  if (rowData.comprehensiveRace != 'Not Available') {
				  var cRaceIndexCodeName = $.inArray(rowData.comprehensiveRace, comprehensiveRaceDataCCode);
				  rowData.comprehensiveRace = comprehensiveRaceDataCName[cRaceIndexCodeName];
				  }
				  if (rowData.firstLanguage != 'Not Available') {
				  var fLanguageIndexCodeName = $.inArray(rowData.firstLanguage, firstLanguageDataCCode);
				  rowData.firstLanguage = firstLanguageDataCName[fLanguageIndexCodeName];
				  }
				  $('#rosterStudentsTableId').jqGrid('setRowData', rowId, rowData);
		   });			
		},1000);
	}
	
	function populateSTSSStudentsSearchFiltersData() {
		
		<c:if test="${!user.teacher}">		
			var optionText='';
			$('.messages').html('').hide();
			
			<c:if test="${user.accessLevel < 50 }">
				var stssDistrictOrgselect = $('#stssDistrictOrgs');				
				
				$.ajax({
					url: 'getChildOrgsWithParentForFilter.htm',
					dataType: 'json',
					data: {
						orgId : ${user.currentOrganizationId},
			        	orgType:'DT'
			        	},				
					type: "POST"
				}).done(function(stssDistrictOrgs) {				
						if (stssDistrictOrgs !== undefined && stssDistrictOrgs !== null && stssDistrictOrgs.length > 0) {
							$.each(stssDistrictOrgs, function(i, districtOrg) {
								optionText = stssDistrictOrgs[i].organizationName;
								stssDistrictOrgselect.append($('<option></option>').val(districtOrg.id).html(optionText));
							});
							
							if (stssDistrictOrgs.length == 1) {
								stssDistrictOrgselect.find('option:first').prop('selected', false).next('option').attr('selected', 'selected');
		    					$("#stssDistrictOrgs").trigger('change');
		    				}
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
						}
						$('#stssDistrictOrgs, #stssSchoolOrgs').trigger('change.select2');
				});
				
				$('#stssDistrictOrgs').on("change",function() {
					$('#stssSchoolOrgs').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					$('#stssSchoolOrgs').trigger('change.select2');
					var districtOrgId = $('#stssDistrictOrgs').val();
					if (districtOrgId != 0) {
						$.ajax({
					        url: 'getChildOrgsWithParentForFilter.htm',
					        data: {
					        	orgId : districtOrgId,
					        	orgType:'SCH'
					        	},
					        dataType: 'json',
					        type: "POST"
						}).done(function(stssSchoolOrgs) {
					        					        	
								$.each(stssSchoolOrgs, function(i, schoolOrg) {
									$('#stssSchoolOrgs').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
								});
								
								if (stssSchoolOrgs.length == 1) {
									$("#stssSchoolOrgs option").prop('selected', false).next('option').attr('selected', 'selected');
									$("#stssSchoolOrgs").trigger('change');
								}							
								$('#stssSchoolOrgs').trigger('change.select2');
						});
					}
				});	
			</c:if>
			
			<c:if test="${user.accessLevel >= 50 && !user.teacher}">				
				var stssSchoolOrgselect = $('#stssSchoolOrgs');
				$.ajax({
					url: 'getChildOrgsWithParentForFilter.htm',
					dataType: 'json',
					data: {
						orgId : ${user.currentOrganizationId},
			        	orgType:'SCH'
			        	},
					type: "POST"
				}).done(function(stssSchoolOrgs) {				
						if (stssSchoolOrgs !== undefined && stssSchoolOrgs !== null && stssSchoolOrgs.length > 0) {
							$.each(stssSchoolOrgs, function(i, schoolOrg) {
								optionText = stssSchoolOrgs[i].organizationName;
								stssSchoolOrgselect.append($('<option></option>').val(schoolOrg.id).html(optionText));
							});
							
							if (stssSchoolOrgs.length == 1) {
								stssSchoolOrgselect.find('option:first').prop('selected', false).next('option').attr('selected', 'selected');
								stssSchoolOrgselect.trigger('change');
		    				}
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrors').html("No School Organizations Found for the current user").show();
						}
						$('#stssSchoolOrgs').trigger('change.select2');
				});
			</c:if>
		</c:if>
	}	
</script>
