<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>
	
<div>	
	<c:if test="${!user.teacher}">
			<div id="searchFilterContainer">
				<form id="searchFilterForm" name="searchFilterForm" class="form">
					
						<div class="btn-bar">
							<div id="searchFilterErrors" class="error"></div>
							<div id="searchFilterMessage" style="padding:20px" class="hidden"></div> 
						</div>
						<c:if test="${user.accessLevel < 50 }">
						    <div class="form-fields">
								<label for="districtOrgs" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
								<select id="districtOrgs" class="bcg_select required" title="District" name="districtOrgs">
									<option value="">Select</option>
								</select>
							</div>
						</c:if>
						<div class="form-fields">
							<label for="schoolOrgs" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
							<select id="schoolOrgs" class="bcg_select required" title="School" name="schoolOrgs">
								<option value="">Select</option>
							</select>
						</div>					
				        <div class="form-fields">
				            <button class="btn_blue" id="searchBtn">Search</button> 			
				        </div>
				</form>
			</div>
	</c:if>	
	<!-- In below select component attribute "class="hidden" used to fix the IE8 browser issue. -->
	<select id="orgChildrenIdsDLM" title="Organiztion" multiple="multiple" class="hidden">
		<c:forEach var="orgChildrenId" items="${orgChildrenIdsDLM}">
		    <option value="${orgChildrenId}" selected="selected"></option>
		</c:forEach>
	</select>
		
	<!-- In below input component attribute "class="hidden" used to fix the IE8 browser issue. -->	
	<input id="loggedInUserId" value ="${user.id}" type="text" class="hidden" />	
	<div class="floatRightTopButton">
		<input id="setupITINextRosterButton" class="panel_btn nextButton setupITINextButton floatRightBottomButton" type="button" value="Next">
	</div>
	<div>
			<span class="error_message ui-state-error hidden" id="selectStudentRosterError"><fmt:message key="error.testsession.noStudents"/></span> <br />
			<span class="error_message ui-state-error hidden" id="selectStudentRosterFCSBandError"><fmt:message key="error.testsession.students.invalidfcsband"/></span> <br />
	</div>
	<div id="noITIReport" class="none" style="width:100%"></div>
	<div class="table_wrap"><div class="kite-table">
	<table id="studentsRostersITITableId" style="width:100%"></table>
	
	<div id="pstudentsRostersITITableId"   style="width:100%"></div>
	<div id="instructionalToolsSupportDiv" ></div>
	<div id="accessProfileDiv" ></div>
	<div id="itifirstContactViewDiv"></div>
	
</div>
</div>
</div>


<script>

var studentId;
var selectedStudentRadio;

var isTeacher = false;
<c:if test="${user.teacher}">
	isTeacher = true;
</c:if>
$( document ).ready(function() {
if(!isTeacher) {
	$('#searchFilterForm')[0].reset();
 	//toggle the componenet with class msg_body
	  $(".tmfilterheading").click(function(e) {
		  e.preventDefault();
		  
	    $("#searchFilterContainer").slideToggle(400);
	  });
	//$('.hidden', $('#searchFilterContainer')).hide();
	$('#districtOrgs, #schoolOrgs').select2({
		placeholder:'Select',
		multiple: false
	});
	
	//populateStudentsSearchFiltersData();
	
	$('#searchFilterForm').validate({
		ignore: "",
		errorPlacement: function(error, element){
			if(element.hasClass('required') || element.attr('type') == 'file'){
				error.insertAfter(element.next());
			} else {
				error.insertAfter(element);
			}
		},
		rules: {
			<c:if test="${user.accessLevel < 50 }">
				districtOrgs: {required: true},
			</c:if>
			schoolOrgs: {required: true}
		}
	});
	
	filteringOrganization($("#districtOrgs"));
	filteringOrganization($("#schoolOrgs"));

	$('#searchBtn').click(function(e) {
		e.preventDefault();
		$('#searchFilterErrors').html("");
		$('#searchFilterMessage').html("").hide();
		if($('#searchFilterForm').valid()){
			loadSetupITIStudentRosters();
		}
	});
	populateStudentsSearchFiltersData();
} else{
	$('#studentsRostersITITableId').parent().css('width','980');
	loadSetupITIStudentRosters();
} 
});
//loadSetupITIStudentRosters();
	function loadSetupITIStudentRosters(){
		$('#studentsRostersITITableId').jqGrid("GridUnload");
		$('#editUserRolesGrid').jqGrid('clearGridData');
		selectedStudentRadio = null;
		$("#EEDropdown").prop('selectedIndex',0);
		var grid_width = $('#studentsRostersITITableId').parent().width();
		var cell_width = 180;
		var mousePosition;

			<security:authorize  access="hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL')" >              		
				<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')" >
					//Need to set this in the session so that loggedInUserId would be used as a key to store the orgs.
					window.sessionStorage.setItem("loggedInUserId", $('#loggedInUserId').val() );
					var autoCompleteUrl = "getAutoCompleteStudentInformationRecords.htm";
					var cmForStudentRecordsForDLM;
					
					if((typeof(viewFirstContactSurvey) !== 'undefined' && !viewFirstContactSurvey) && (typeof(editFirstContactSurvey) !== 'undefined' && !editFirstContactSurvey))
					{
	
					cmForStudentRecordsForDLM = [ 
						{ name : 'id', formatter: idFormatter, unformat: idUnFormatter, width : 30, sortable : false, search : false,hidedlg : true, hidden: false, viewable:false,},
						{ name : 'student.stateStudentIdentifier', index:'stateStudentIdentifier',label:'studentRoster_stateStudentIdentifier',  width : 120, sortable : true, search : true, hidden : false, hidedlg : true,
							searchoptions:{ sopt:['cn'], dataInit: function(elem) {
								    $(elem).autocomplete({
								        source: autoCompleteUrl + '?fileterAttribute=stateStudentIdentifier'
								    });
								} 	
							}
						},
						{ name : 'student.legalLastName', index:'legalLastName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true },
						{ name : 'student.legalMiddleName', index:'legalMiddleName',  width : cell_width, sortable : true, search : false, hidden : true, hidedlg: true },
						{ name : 'student.legalFirstName', index:'legalFirstName', width : cell_width, sortable : true, search : false, hidden : false },
						{ name : 'stateSubjectArea.name', index:'stateSubjectAreaName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true },
						{ name : 'stateSubjectArea.abbreviatedName', index:'stateSubjectAbbreviatedName', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : true },
						{ name : 'gradeCourse.name', index:'gradeCourseName', width : 90, sortable : true, search : false, hidden : false, hidedlg : true},
						{ name : 'accessprofilestatus', index:'accessProfileStatus', width : 120, sortable : true, search : false, hidden : false, hidedlg: true 
							,formatter: accessProfileLinkFormatter, unformat: accessProfileLinkUnFormatter},
						{ name : 'firstContact', index:'firstContact', width : 120, sortable : true, search : false, 
								formatter: firstContactLinkFormatter, unformat: firstContactLinkUnFormatter, hidelg: true, hidden: true},
						{ name : 'roster.courseSectionName', index:'courseSectionName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true }, 						
						{ name : 'stateSubjectArea.id', index:'stateSubjectAreaId', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false },
						{ name : 'gradeCourse.abbreviatedName', index:'abbreviatedName', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false }, 
						{ name : 'attendanceSchool.displayIdentifier', index:'attendanceSchoolIdentifier', width : cell_width, viewable: false, stype:'orgtree', searchoptions: { url: 'getUsersOrgs.htm' }, label: 'attendanceSchoolIdentifier', hidden : true, hidedlg : false },										
						{ name : 'educator.surName', index:'educatorLastName', width : cell_width,  sortable : true, search : false, hidden : false },
						{ name : 'educator.firstName', index:'educatorFirstName', width : cell_width, search : false,  sortable : true, hidden : false },
						{ name : 'student.id', index:'studentId', width : cell_width, editable : true, sortable : true, search : false,  hidden : true }, 
						{ name : 'roster.id', index:'rosterId',  width : cell_width, editable : true, sortable : true, search : false,  hidden : true }, 
						{ name : 'educator.id', index:'educatorIdentifier',  width : cell_width,  sortable : true, search : false, hidden : true },	
						{ name : 'student.genderStr', index:'gender', width : cell_width,  sortable : true, search : false, hidden : true ,hidedlg : true },
						{ name : 'student.dateOfBirthStr', index:'dateOfBirth', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },
						{ name : 'student.finalElaBandId', index:'finalElaBandId', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },	
						{ name : 'student.finalMathBandId', index:'finalMathBandId', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },
						{ name: 'student.statePoolType', index:'statePoolType', width: cell_width, sortable: false, viewable: false, search : false, hidden : true, hidedlg : false}

					];
					
					}
					else
					{
						cmForStudentRecordsForDLM = [ 
						{ name : 'id', formatter: idFormatter, unformat: idUnFormatter, width : 30, sortable : false, search : false,hidedlg : true, hidden: false, viewable:false,},
						{ name : 'student.stateStudentIdentifier', index:'stateStudentIdentifier',label:'studentRoster_stateStudentIdentifier',   width : 120, sortable : true, search : true, hidden : false, hidedlg : true,
							searchoptions:{ sopt:['cn'], dataInit: function(elem) {
								    $(elem).autocomplete({
								        source: autoCompleteUrl + '?fileterAttribute=stateStudentIdentifier'
								    });
								} 	
							}
						},
						{ name : 'student.legalLastName', index:'legalLastName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true },
						{ name : 'student.legalMiddleName', index:'legalMiddleName',  width : cell_width, sortable : true, search : false, hidden : true, hidedlg: true },
						{ name : 'student.legalFirstName', index:'legalFirstName', width : cell_width, sortable : true, search : false, hidden : false },
						{ name : 'stateSubjectArea.name', index:'stateSubjectAreaName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true },
						{ name : 'stateSubjectArea.abbreviatedName', index:'stateSubjectAbbreviatedName', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : true },
						{ name : 'gradeCourse.name', index:'gradeCourseName', width : 90, sortable : true, search : false, hidden : false, hidedlg : true},
						{ name : 'accessprofilestatus', index:'accessProfileStatus', width : 120, sortable : true, search : false, hidden : false, hidedlg: true 
							,formatter: accessProfileLinkFormatter, unformat: accessProfileLinkUnFormatter},
						{ name : 'firstContact', index:'firstContact', width : 120, sortable : true, search : false, hidden : false, hidedlg: true 
								,formatter: firstContactLinkFormatter, unformat: firstContactLinkUnFormatter},
						{ name : 'roster.courseSectionName', index:'courseSectionName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true }, 						
						{ name : 'stateSubjectArea.id', index:'stateSubjectAreaId', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false },
						{ name : 'gradeCourse.abbreviatedName', index:'abbreviatedName', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false }, 
						{ name : 'attendanceSchool.displayIdentifier', index:'attendanceSchoolIdentifier', width : cell_width, viewable: false, stype:'orgtree', searchoptions: { url: 'getUsersOrgs.htm' }, label: 'attendanceSchoolIdentifier', hidden : true, hidedlg : false },										
						{ name : 'educator.surName', index:'educatorLastName', width : cell_width,  sortable : true, search : false, hidden : false },
						{ name : 'educator.firstName', index:'educatorFirstName', width : cell_width, search : false,  sortable : true, hidden : false },
						{ name : 'student.id', index:'studentId', width : cell_width, editable : true, sortable : true, search : false,  hidden : true }, 
						{ name : 'roster.id', index:'rosterId',  width : cell_width, editable : true, sortable : true, search : false,  hidden : true }, 
						{ name : 'educator.id', index:'educatorIdentifier',  width : cell_width,  sortable : true, search : false, hidden : true },	
						{ name : 'student.genderStr', index:'gender', width : cell_width,  sortable : true, search : false, hidden : true ,hidedlg : true },
						{ name : 'student.dateOfBirthStr', index:'dateOfBirth', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },
						{ name : 'student.finalElaBandId', index:'finalElaBandId', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },	
						{ name : 'student.finalMathBandId', index:'finalMathBandId', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },
						{ name: 'student.statePoolType', index:'statePoolType', width: cell_width, sortable: false, viewable: false, search : false, hidden : true, hidedlg : false}

						];
					}
					//Capture the mouse position for Loading message display.
					$(document).mousemove(function(e) {
						mousePosition = e.pageY;
					});
					$grid= $('#studentsRostersITITableId');           		
					myDefaultSearch = 'cn',
			        myColumnStateNameCheck = 'ColumnChooserAndLocalStorage.colStateForITI';
			        firstLoad = true;
			        
					jQuery("#studentsRostersITITableId").scb({
						url : 'getStudentInformationRecordsForDLM.htm?q=1',
						postData :  {
							orgChildrenIdsDLM : function() {
								return $('#orgChildrenIdsDLM').val();
							},
						},
						mtype: "POST",
						datatype : "json",
						width: grid_width,
						colNames : ['','State ID', 'Last Name','Middle Name', 'First Name','Subject','Subject Abbreviated Name', 'Grade','PNP Profile','First Contact', 'Roster Name', 'SubjectArea Id','Grade Code',
									'School ID', 'Educator Last Name', 'Educator First Name', 'Student ID', 'Roster ID', 'Educator ID', 'Gender' , 'Date of Birth', 'Final ELA Band ID','Final Math Band ID', 'State Pool Type'],
						colModel : cmForStudentRecordsForDLM,
						rowNum : 10, //30,
						rowList : [ 5,10, 20, 30, 40, 60, 90 ],
						height : 'auto',
						pager : '#pstudentsRostersITITableId',
					 	sortname: 'stateStudentIdentifier',
					    gridComplete: function() {
						    var recs = parseInt($("#studentsRostersITITableId").getGridParam("records"));
							if (isNaN(recs) || recs == 0) {
							     $("#gbox_studentsRostersITITableId").hide();
							 } else {
							     $("#gbox_studentsRostersITITableId").show();
							     $("#noITIReport").html('');
							 }
						},
						beforeSelectRow: function(rowid, e) {
							var selectedGrade;
							var selectedFirstName;
							var selectedLastName;
							var selectedEduFName;
							var selectedEduLName;
							var selectedStudentFCSBandCheck;
							var selectedStudent;
							var selectedStudentRosterId;
							var selectedStudentIdentifier;
			            	var radio = $(e.target).closest('tr').find('input[type="radio"]');
			            	radio.attr('checked', 'checked');
			            	if(radio[0] != null && radio[0] != undefined){
			            		selectedStudentRadio = radio[0].id;
				            	var rowData = jQuery("#studentsRostersITITableId").jqGrid('getRowData',selectedStudentRadio);
				            	var gridRow = $("#studentsRostersITITableId").jqGrid('getRowData',rowid);
				            	selectedFirstName = gridRow['student.legalFirstName'];
				            	selectedLastName = gridRow['student.legalLastName'];
				            	selectedEduLName = gridRow['educator.surName'];
				            	selectedEduFName = gridRow['educator.firstName'];
				            	selectedStudentRosterId = gridRow['roster.id'];
				            	if(gridRow['firstContact'] != 'Completed')
				            		selectedStudentFCSBandCheck = false;
				            	else
				            		selectedStudentFCSBandCheck = true;
 				            	selectedStudentIdentifier = gridRow['student.stateStudentIdentifier'];
 				            	$("#selectStudentRosterFCSBandError").hide();
			            	}
			            	var studentInfo = new Object();
			            	selectedStudent = rowid;
			            	studentId = gridRow['student.id'];
			        		studentInfo.studentFirstName = selectedFirstName;
			        		studentInfo.studentLastName = selectedLastName;			        	
 			        		studentInfo.stateStudentIdentifier = selectedStudentIdentifier;
			        		studentInfo.selectedEduFName = selectedEduFName;
			        		studentInfo.selectedEduLName = selectedEduLName;
			        		studentInfo.selectedStudentFCSBandCheck = selectedStudentFCSBandCheck;
			        		studentInfo.studentId = studentId;
			        		studentInfo.studentEnrlRosterId =  gridRow['id'];
			        		studentInfo.rosterId = selectedStudentRosterId;
			        		if(gridRow['student.legalMiddleName'])
			        			studentInfo.studentMiddleName = gridRow['student.legalMiddleName'];
			        		else
			        			studentInfo.studentMiddleName = "-";
			        		if(gridRow['gradeCourse.name'])
			        			studentInfo.gradeCourseName = gridRow['gradeCourse.name'];
			        		else
			        			studentInfo.gradeCourseName = "-";
			        		if(gridRow['gradeCourse.abbreviatedName'])
			        			studentInfo.gradeCourseCode = gridRow['gradeCourse.abbreviatedName'];
			        		else
			        			studentInfo.gradeCourseCode = "-";
			        		if(gridRow['stateSubjectArea.abbreviatedName'])
			        			studentInfo.subjectAreaCode = gridRow['stateSubjectArea.abbreviatedName'];
			        		else
			        			studentInfo.subjectAreaCode = "-";
			        		if(gridRow['student.dateOfBirthStr'])
			        			studentInfo.dateOfBirth = gridRow['student.dateOfBirthStr'];
			        		else
			        			studentInfo.dateOfBirth = "-";
			        		if(gridRow['student.genderStr'])
			        			studentInfo.gender = gridRow['student.genderStr'];
			        		else
			        			studentInfo.gender = "-";
			        		if(gridRow['stateSubjectArea.id'])
			        			studentInfo.stateSubjectAreaId = gridRow['stateSubjectArea.id'];
			        		else
			        			studentInfo.stateSubjectAreaId = '-';
			        		if(gridRow['stateSubjectArea.name'])
			        			studentInfo.subjectareaName = gridRow['stateSubjectArea.name'];
			        		else 
			        			studentInfo.subjectareaName= '-';
			        		if(gridRow['roster.courseSectionName'])
			        			studentInfo.rosterName = gridRow['roster.courseSectionName'];
			        		else 
			        			studentInfo.rosterName= '-';
			        		if(gridRow['student.statePoolType'])
			        			studentInfo.statePoolType = gridRow['student.statePoolType'];
			        		else 
			        			studentInfo.statePoolType= '-';
			        		if(window.sessionStorage.getItem('studentId') != studentId || window.sessionStorage.getItem('studentRosterId') != selectedStudentRosterId){
			        			$('#contentForm')[0].reset();
			        			$("#EEDropdown").prop('selectedIndex',0);			        			
			        			/* loadStudentInfoMessage(); */
			        		}
			        		window.sessionStorage.setItem('selectedStudentITI', JSON.stringify(studentInfo));
			        		window.sessionStorage.setItem('studentId', studentId);
			        		window.sessionStorage.setItem('studentRosterId', selectedStudentRosterId);
			        		window.sessionStorage.setItem('sameStudentProcess', false);
			            	return true; //allow row selection
			            },
			            resizeStop: function () {
			                saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateNameCheck);
			            },
			            onSelectRow: function(rowid, e) {	            
			            	selectedStudent = rowid;
						},
						beforeRequest: function () {
							var schoolOrgId = "";
							if(!isTeacher && $grid.getGridParam('datatype') == 'json'){
								if(!$('#searchFilterForm').valid()) {return false;}
									schoolOrgId = $('#schoolOrgs').val();				
							}
							var pdata = $grid.getGridParam("postData");
							if(typeof pdata  == 'undefined' || typeof pdata.filters == 'undefined' || pdata.filters == null) {
								$(this).setGridParam({postData: {filters:""}});
							}
							//Set the page param to lastpage before sending the request when 
							  //the user entered current page number is greater than lastpage number.
							var currentPage = $(this).getGridParam('page');
			                var lastPage = $(this).getGridParam('lastpage');
			                 if (lastPage!= 0 && currentPage > lastPage) {
			                	$(this).setGridParam('page', lastPage);
			                	$(this).setGridParam({postData: {page : lastPage}});
			                }
			                	$(this).setGridParam({postData: {schoolOrgId : schoolOrgId}});
						},
						 loadComplete: function (){
							 var ids = $(this).jqGrid('getDataIDs');         
					         var tableid=$(this).attr('id');      
					            for(var i=0;i<ids.length;i++)
					            {        
					                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'testCollectionId')+ ' Check Box');
					            }
							   var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
					             $.each(objs, function(index, value) {         
					              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
					                    $(value).attr('title',$(nm).text()+' filter');
					                    if ( $(value).is('select')) {
						                	   $(value).removeAttr("role");

						                    };
					                    });
					             $('.filter-tree-element').remove();
					             $('td[id^="view_"]').click(function(){
					       		  if($(".EditTable.ViewTable tbody").find('th').length==0){
					     			$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
					       		  }
					       		  });
						 },
						jsonReader: {
					        page: function (obj) {
					            return obj.page !== undefined ? obj.page : "0";
					        },
					        repeatitems:false,
					    	root: function(obj) { 
					    		return obj.rows;
					    	} 
					    }
					});
					//Clear the previous error messages
					setTimeout("aart.clearMessages()", 0);
				</security:authorize>
			</security:authorize>

		function viewStudentDetails(selectedStudent){
				$("#studentsRostersITITableId").jqGrid("viewGridRow", selectedStudent ,{width: 600});
		}

		//Custom formatter for AccessProfile link. 
		function accessProfileLinkFormatter(cellvalue, options, rowObject) {
			return '<a href="javascript:accessProfileDetails(\'' + rowObject.student.id  + '\',\''  + rowObject.student.stateStudentIdentifier +'\',\''+rowObject.student.studentAssessmentProgram +'\');">' + cellvalue + '</a>';
		}
		
		//Custom unformatter for AccessProfile link.
		function accessProfileLinkUnFormatter(cellvalue, options, rowObject) {
			return;
		}
			//Code to set the position of the AlertMod 
		//warning message (Please, select a row)
		var orgViewModal = $.jgrid.viewModal;
		$.extend($.jgrid,{
		    viewModal: function (selector,o){ 
		        if(selector == '#alertmod'){
		            var of = jQuery(o.gbox).offset();
		            var w = jQuery(o.gbox).width();
		            var h = jQuery(o.gbox).height();
		            var w1 = $(selector).width();
		            //var h1 = $(selector).height();
		            $(selector).css({
		                'top':of.top+(h - 300),
		                'left':of.left+((w-w1)/2)
		            });
		        }
		        orgViewModal.call(this, selector, o);
		    }
		}); 
	}

	 
	//Custom formatter for FirstContact link. 
	function firstContactLinkFormatter(cellvalue, options, rowObject) {
		// Save student info in local storage for reuse
		var studentFirstName =  rowObject.student.legalFirstName;
		var studentLastName = rowObject.student.legalLastName; 
		var studentMiddleName = rowObject.student.legalMiddleName;
		var studentId = rowObject.studentId;
		var studentInfo = new Object();
		studentInfo.studentFirstName = studentFirstName;
		studentInfo.studentMiddleName = studentMiddleName;
		studentInfo.studentLastName = studentLastName;
		studentInfo.studentId = studentId;

		window.sessionStorage.setItem('FC_'+studentId, JSON.stringify(studentInfo));
		if((typeof(editFirstContactSurvey) !== 'undefined' && editFirstContactSurvey))
		{
			if(cellvalue=='Not Applicable')
				return cellvalue;
			else
				return '<a href="javascript:viewFirstContactDetails(\'' + studentId + '\');">' + cellvalue + '</a>';
		}
		else if((typeof(viewFirstContactSurvey) !== 'undefined' && viewFirstContactSurvey))
		{
			if(cellvalue=='Not Applicable' || cellvalue=='NOT STARTED')
				return cellvalue;
			else
				return '<a href="javascript:viewFirstContactDetails(\'' + studentId + '\');">' + cellvalue + '</a>';
		}
	}
	
	//Custom unformatter for FirstContact link.
	function firstContactLinkUnFormatter(cellvalue, options, rowObject) {
	    return cellvalue;
	}
	
	//Show popup screen on clicking the FirstContact link.
	function viewFirstContactDetails(studentId) {
		gridParam = window.sessionStorage.getItem('FC_'+studentId);  
		var studentInfo = $.parseJSON(gridParam);
		var dialogTile = studentInfo.studentFirstName + " "+studentInfo.studentLastName;
		$('#itifirstContactViewDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 1110,
			height: 700,			
			title: dialogTile,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			beforeClose: function() {
			//$(this).dialog('close');
 			    var $gridAuto = $("#studentsRostersITITableId");
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getStudentInformationRecordsForDLM.htm?q=1', 
					search: false, 
				}).trigger("reloadGrid"); 
			}
		}).load('firstContactResponseView.htm?&studentId='+studentId).dialog('open');	
	}
	
	//This method takes the studentId and opens the dialog with PNP summary data 
	//for which the user double clicks the row.
	function accessProfileDetails(studentId,stateStudentIdentifier,assessmentProgramCode) {
		var gridParam = window.sessionStorage.getItem('selectedStudentITI');  
		var studentInfo = $.parseJSON(gridParam);
		var dialogTile = studentInfo.studentFirstName + " ";
		dialogTile += studentInfo.studentLastName;
		var assmtPrgmCode = '';
		if(assessmentProgramCode !== undefined && assessmentProgramCode !== null){
			assmtPrgmCode = assessmentProgramCode;
		}
		$('#accessProfileDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 1087,
			height: 700,			
			title: dialogTile,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			beforeClose: function() {
				//$(this).dialog('close');
			    var $gridAuto = $("#studentsRostersITITableId");
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getStudentInformationRecordsForDLM.htm?q=1', 
					search: false, 
				}).trigger("reloadGrid");
			}
		}).load('viewAccessProfile.htm?&studentId='+studentId+"&stateStudentIdentifier="+encodeURIComponent(stateStudentIdentifier.toString())+"&assessmentProgramCode="+encodeURIComponent(assmtPrgmCode.toString())+"&studentInfo="+encodeURIComponent(gridParam)+"&previewAccessProfile="+"noPreview").dialog('open');	

	}
	//Custom formatter for id column. 
	function idFormatter(cellvalue, options, rowObject) {	
		var htmlString = "";
			htmlString = '<input type="radio" id=' + rowObject.student.id  +'_'+rowObject.stateSubjectArea.id+ ' title="'+rowObject.student.legalFirstName+' '+rowObject.student.legalLastName+' '+rowObject.stateSubjectArea.name+' '+rowObject.gradeCourse.name +' " name="studentId" value="' + rowObject.student.id  + '"/>';				
	    return htmlString;
	}
	 
	
	//Custom unformatter for id column. 
	function idUnFormatter(cellvalue, options, rowObject) {
		return options.rowId;
	}
	 
	//This method takes the studentId and opens the dialog with PNP summary data 
	//for which the user double clicks the row.
	function instructionalToolsSupportDetails(studentId,stateStudentIdentifier) {
		gridParam = window.sessionStorage.getItem('selectedStudentITI');  
		var studentInfo = $.parseJSON(gridParam);
		var dialogTile = "View History ";
		
		$('#instructionalToolsSupportDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 700,
			height: 700,			
			title: dialogTile,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			beforeClose: function() {
				var $gridAuto = $("#studentsRostersITITableId");
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getStudentInformationRecordsForDLM.htm?q=1',
					search: false,
				}).trigger("reloadGrid"); 
			},
			close: function(ev, ui) {			    
				$(this).dialog("close");
			    $(this).html('');
			}

		}).load(encodeURI('viewStudentInstructionalPlans.htm?&studentId='+studentId+"&stateStudentIdentifier="+stateStudentIdentifier.toString()+"&studentInfo="+gridParam+"&sourcePage=studentRosterPage")).dialog('open');	

	}
	function confirmAssignment(){
		$('#instructionalToolsSupportDiv').dialog("close");
		 $('#setupInstructionalToolsSupportTabs').tabs("option", 'active', 3);
			$('#setupInstructionalToolsSupportTabs li a').eq(0).removeClass('active');
	 		$('#setupInstructionalToolsSupportTabs li a').eq(3).addClass('active');	
	}
	
	function validateFCSurveyComBand(){
		gridParam = window.sessionStorage.getItem("selectedStudentITI");  
		studentInfo = $.parseJSON(gridParam);
		return studentInfo.selectedStudentFCSBandCheck;
	}
	
	function populateStudentsSearchFiltersData() {
		
		<c:if test="${!user.teacher}">		
			var optionText='';
			$('.messages').html('').hide();
			
			<c:if test="${user.accessLevel < 50 }">
				var districtOrgSelect = $('#districtOrgs');
				districtOrgSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
				
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
							
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrors').html("No District Organizations Found for the current user").show();
						}
						$('#schoolOrgs, #districtOrgs').trigger('change.select2');
					}
				});
				
				$('#districtOrgs').change(function() {
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

								if (schoolOrgs !== undefined && schoolOrgs !== null && schoolOrgs.length == 1) {
									$('#schoolOrgs').val(schoolOrgs[0].id);
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
							
							if (schoolOrgs !== undefined && schoolOrgs !== null && schoolOrgs.length == 1) {
								$('#schoolOrgs').val(schoolOrgs[0].id);
							}
							
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrors').html("No School Organizations Found for the current user").show();
						}
						$('#schoolOrgs').trigger('change.select2');
					}
				});
			</c:if>
		</c:if>
	}	
	
	var viewFirstContactSurvey = false;
    <security:authorize access="hasRole('VIEW_FIRST_CONTACT_SURVEY')">
   		viewFirstContactSurvey = true; 
    </security:authorize>
	
	var editFirstContactSurvey = false;
    <security:authorize access="hasRole('EDIT_FIRST_CONTACT_SURVEY')">
   		editFirstContactSurvey = true; 
    </security:authorize>
</script>
