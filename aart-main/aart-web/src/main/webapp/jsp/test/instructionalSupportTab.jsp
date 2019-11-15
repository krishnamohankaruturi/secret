<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<c:if test="${!TMTabAccessFlag}">
	<div class="panel_full noBorder">
		<span id="test_mgmt_iti_no_access" class="info_message">
			${TMTabAccessFailedReason} </span>
	</div>
</c:if>

<c:if test="${TMTabAccessFlag}">
<div>
	<ul id="viewInstructionalSupport" class="nav nav-tabs sub-nav">
		<li class="nav-item">
			<a class="nav-link" href="#tabs_instructionalSupport" data-toggle="tab" role="tab"><fmt:message key="label.instructionalsupport" /></a>
		</li>	
	</ul>
	
	<div id="content" class="tab-content">
		<div id="tabs_instructionalSupport" class="tab-pane" role="tabpanel">
	
<div class="pageContent">	
	<c:if test="${!user.teacher}">
			<div id="searchFilterContainerIS">
				<form id="searchFilterFormIS" name="searchFilterFormIS" class="form">
					
						<div class="btn-bar">
							<div id="searchFilterErrorsIS" class="error"></div>
							<div id="searchFilterMessageIS" style="padding:20px" class="hidden"></div> 
						</div>
						<c:if test="${user.accessLevel < 50 }">
						    <div class="form-fields">
								<label for="districtOrgsIS" class="field-label">DISTRICT:<span class="lbl-required">*</span></label>			
								<select id="districtOrgsIS" title="District" class="bcg_select required" name="districtOrgsIS">
									<option value="">Select</option>
								</select>
							</div>
						</c:if>
						<div class="form-fields">
							<label for="schoolOrgsIS" class="field-label">SCHOOL:<span class="lbl-required">*</span></label>			
							<select id="schoolOrgsIS" title="School" class="bcg_select required" name="schoolOrgsIS">
								<option value="">Select</option>
							</select>
						</div>					
				        <div class="form-fields">
				            <button class="btn_blue" id="searchBtnIS">Search</button> 			
				        </div>
				</form>
			</div>
	</c:if>	
	<!-- In below select component attribute "class="hidden" used to fix the IE8 browser issue. -->
	<select id="orgChildrenIdsDLM" multiple="multiple" class="hidden">
		<c:forEach var="orgChildrenId" items="${orgChildrenIdsDLM}">
		    <option value="${orgChildrenId}" selected="selected"></option>
		</c:forEach>
	</select>
		
	<!-- In below input component attribute "class="hidden" used to fix the IE8 browser issue. -->	
	<input id="loggedInUserId" value ="${user.id}" type="text" class="hidden" />	
<!-- 	<input id="detailViewCaption" value ="View Student Detail" type="text" class="hidden" /> -->
	<input id="columnChooserGrid" value ="true" type="hidden" class="hidden" />	
<br><br><br><br>
	<div id="accessProfileDiv" ></div>
	<div id="itifirstContactViewDiv"></div>
	<div id="instructionalToolsSupportDiv" ></div>
	<div style="text-align:right;">
	<a  class="panel_btn" href="instructionalToolsSupport.htm" id="createNewIti">Add New Instructional Plan</a>
	</div>
	<div id="noITISReport" class="none" style="width:100%"></div>
	<div class="table_wrap"><div class="kite-table">
	<table id="rosterStudentsITIId" style="width:100%"></table>
	<div id="prosterStudentsITIId"   style="width:100%"></div>
</div>
</div>
</div>



<script>
$(function(){
	<%-- navigate to the first available tab --%>
	$('#viewInstructionalSupport li.nav-item:first a').tab('show');
	initITIStudentRostersIS();
	
	console.log('test 123');
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
</script>

<script>
$("#createNewIti").click(function() {
	window.sessionStorage.setItem('itiEditMode', true);
});

var flag = 1;
var isTeacher = false;
var isNotBack = true;
<c:if test="${user.teacher}">
	isTeacher = true;
</c:if>

//resetInstructionalToolsTab();

function initITIStudentRostersIS(){
	<c:if test="${user.teacher}">
		isTeacher = true;
	</c:if>
	if(!isTeacher) {
		$('#searchFilterFormIS')[0].reset();
	 	//toggle the componenet with class msg_body
		  $(".tmfilterheading").click(function(e) {
			  e.preventDefault();
			  
		    $("#searchFilterContainerIS").slideToggle(400);
		  });
		//$('.hidden', $('#searchFilterContainerIS')).hide();
		$('#districtOrgsIS, #schoolOrgsIS').select2({
			placeholder:'Select',
			multiple: false
		});
		
		filteringOrganization($("#districtOrgsIS"));
		filteringOrganization($("#schoolOrgsIS"));
		
		//populateStudentsSearchFiltersDataIS();
		
		$('#searchFilterFormIS').validate({
			ignore: "",
			rules: {
				<c:if test="${user.accessLevel < 50 }">
					districtOrgsIS: {required: true},
				</c:if>
				schoolOrgsIS: {required: true}
			}
		});
		
		
		
		$('#searchBtnIS').click(function(e) {
			e.preventDefault();
			$('#searchFilterErrorsIS').html("");
			$('#searchFilterMessageIS').html("").hide();
			if($('#searchFilterFormIS').valid()){
				loadSetupITIStudentRostersIS();
			}
		});
		populateStudentsSearchFiltersDataIS();
	} else{
		loadSetupITIStudentRostersIS();
	}   
	
}


 	function loadSetupITIStudentRostersIS(){
 		$('#rosterStudentsITIId').jqGrid("GridUnload");
		var grid_width = $('#rosterStudentsITIId').parent().width();
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
							{ name : 'id',  formatter: idFormatter, unformat: idUnFormatter, width : 30, sortable : false, search : false, hidden: true, hidedlg : true, viewable:false, },
							{ name : 'student.stateStudentIdentifier', index:'stateStudentIdentifier',  width : cell_width, sortable : true, search : true, hidden : false, hidedlg : false,
								searchoptions:{ sopt:['cn'], dataInit: function(elem) {
		           				    $(elem).autocomplete({
		           				        source: autoCompleteUrl + '?fileterAttribute=stateStudentIdentifier'
		           				    });
		           				} 	
								}
							},
							{ name : 'student.legalLastName', index:'legalLastName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : false },
							{ name : 'student.legalMiddleName', index:'legalMiddleName',  width : cell_width, sortable : true, search : false, hidden : false, hidedlg: false },
							{ name : 'student.legalFirstName', index:'legalFirstName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg: false },
							{ name : 'accessprofilestatus', index:'accessProfileStatus', width : cell_width, sortable : true, search : false, hidden : false, hidedlg: false 
								,formatter: accessProfileLinkFormatter, unformat: accessProfileLinkUnFormatter},
							{ name : 'firstContact', index:'firstContact', width : cell_width, sortable : true, search : false
								,formatter: firstContactLinkFormatterIti, unformat: firstContactLinkUnFormatterIti, hidelg: true, hidden: true},
							{ name : 'history', index:'history', width : cell_width, formatter: ITILinkFormatter, unformat: ITILinkUnFormatter, sortable : true, search : false, hidden : false, hidedlg : false }, 
							{ name : 'roster.courseSectionName', index:'courseSectionName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : false },
							{ name : 'stateSubjectArea.id', index:'stateSubjectAreaId', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false },
							{ name : 'stateSubjectArea.name', index:'stateSubjectAreaName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true },
							{ name : 'gradeCourse.name', index:'gradeCourseName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : false }, 
							{ name : 'gradeCourse.abbreviatedName', index:'abbreviatedName', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false }, 
							{ name : 'attendanceSchool.displayIdentifier', index:'attendanceSchoolIdentifier', width : cell_width, viewable: false, search : false, hidden : true, hidedlg : false },										
							{ name : 'educator.surName', index:'educatorLastName', width : cell_width,  sortable : true, search : false, hidden : false },
							{ name : 'educator.firstName', index:'educatorFirstName', width : cell_width, search : false,  sortable : true, hidden : false },
							{ name : 'student.id', index:'studentId', width : cell_width,  sortable : true, search : false,  hidden : true,  }, 
							{ name : 'roster.id', index:'rosterId',  width : cell_width,  sortable : true, search : false,  hidden : true,  }, 
							{ name : 'educator.id', index:'educatorIdentifier',  width : cell_width,  sortable : true, search : false, hidden : true,  },	
							{ name : 'student.genderStr', index:'gender', width : cell_width,  sortable : true, search : false, hidden : true ,hidedlg : true },
							{ name : 'student.dateOfBirthStr', index:'dateOfBirth', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },
							{ name: 'student.statePoolType', index:'statePoolType', width: cell_width, sortable: false, viewable: false, search : false, hidden : true, hidedlg : false}
						];
					
					}
					else
					{
					cmForStudentRecordsForDLM = [ 
							{ name : 'id',  formatter: idFormatter, unformat: idUnFormatter, width : 30, sortable : false, search : false, hidden: true, hidedlg : true, viewable:false, },
							{ name : 'student.stateStudentIdentifier', index:'stateStudentIdentifier', label:'stateStudentIdentifier',  width : cell_width, sortable : true, search : true, hidden : false, hidedlg : false,
								searchoptions:{ sopt:['cn'], dataInit: function(elem) {
		           				    $(elem).autocomplete({
		           				        source: autoCompleteUrl + '?fileterAttribute=stateStudentIdentifier'
		           				    });
		           				} 	
								}
							},
							{ name : 'student.legalLastName', index:'legalLastName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : false },
							{ name : 'student.legalMiddleName', index:'legalMiddleName',  width : cell_width, sortable : true, search : false, hidden : false, hidedlg: false },
							{ name : 'student.legalFirstName', index:'legalFirstName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg: false },
							{ name : 'accessprofilestatus', index:'accessProfileStatus', width : cell_width, sortable : true, search : false, hidden : false, hidedlg: false 
								,formatter: accessProfileLinkFormatter, unformat: accessProfileLinkUnFormatter},
							{ name : 'firstContact', index:'firstContact', width : cell_width, sortable : true, search : false, hidden : false, hidedlg: true 
								,formatter: firstContactLinkFormatterIti, unformat: firstContactLinkUnFormatterIti},
							{ name : 'history', index:'history', width : cell_width, formatter: ITILinkFormatter, unformat: ITILinkUnFormatter, sortable : true, search : false, hidden : false, hidedlg : false }, 
							{ name : 'roster.courseSectionName', index:'courseSectionName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : false },
							{ name : 'stateSubjectArea.id', index:'stateSubjectAreaId', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false },
							{ name : 'stateSubjectArea.name', index:'stateSubjectAreaName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : true },
							{ name : 'stateSubjectArea.abbreviatedName', index:'stateSubjectAbbreviatedName', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : true },
							{ name : 'gradeCourse.name', index:'gradeCourseName', width : cell_width, sortable : true, search : false, hidden : false, hidedlg : false }, 
							{ name : 'gradeCourse.abbreviatedName', index:'abbreviatedName', width : cell_width, sortable : true, search : false, hidden : true, hidedlg : false }, 
							{ name : 'attendanceSchool.displayIdentifier', index:'attendanceSchoolIdentifier', width : cell_width, viewable: false, search : false, hidden : true, hidedlg : false },										
							{ name : 'educator.surName', index:'educatorLastName', width : cell_width,  sortable : true, search : false, hidden : false },
							{ name : 'educator.firstName', index:'educatorFirstName', width : cell_width, search : false,  sortable : true, hidden : false },
							{ name : 'student.id', index:'studentId', width : cell_width,  sortable : true, search : false,  hidden : true,  }, 
							{ name : 'roster.id', index:'rosterId',  width : cell_width,  sortable : true, search : false,  hidden : true,  }, 
							{ name : 'educator.id', index:'educatorIdentifier',  width : cell_width,  sortable : true, search : false, hidden : true,  },	
							{ name : 'student.genderStr', index:'gender', width : cell_width,  sortable : true, search : false, hidden : true ,hidedlg : true },
							{ name : 'student.dateOfBirthStr', index:'dateOfBirth', width : cell_width, sortable : true, search : false, hidden : true ,hidedlg : true },
							{ name: 'student.statePoolType', index:'statePoolType', width: cell_width, sortable: false, viewable: false, search : false, hidden : true, hidedlg : false}
						];
					}
					//Capture the mouse position for Loading message display.
					$(document).mousemove(function(e) {
						mousePosition = e.pageY;
					});
					$grid= $('#rosterStudentsITIId');           		
					myDefaultSearch = 'cn',
			        myColumnStateNameCheck = 'ColumnChooserAndLocalStorage.colStateForITI';
			        firstLoad = true;
					//JQGRID   
					jQuery("#rosterStudentsITIId").scb({
						url : 'getStudentInformationRecordsForDLM.htm?q=1',
						postData :  {
							orgChildrenIdsDLM : function() {
								return $('#orgChildrenIdsDLM').val();
							},
							filters : "",
						},
						mtype: "POST",
						datatype : "json",
						width: grid_width,
						colNames : ['','State ID', 'Last Name','Middle Name', 'First Name','PNP Profile','First Contact','History','Roster Name', 'SubjectArea Id', 'Subject', 'Subject Abbreviated Name', 'Grade', 'Grade Code',
									'School ID', 'Educator Last Name', 'Educator First Name', 'Student ID', 'Roster ID', 'Educator ID', 'Gender' , 'Date of Birth', 'State Pool Type'],
						colModel : cmForStudentRecordsForDLM,
						rowNum : 10, //30,
						rowList : [ 5,10, 20, 30, 40, 60, 90 ],
						sortname: 'stateStudentIdentifier',
				       	sortorder: 'asc',
						height : 'auto',
						pager : '#prosterStudentsITIId',
						gridComplete: function() {
						    var recs = parseInt($("#rosterStudentsITIId").getGridParam("records"));
							if (isNaN(recs) || recs == 0) {
							     $("#gbox_rosterStudentsITIId").hide();
							     //$("#noITIReport").html('No student records found.');
							 } else {
							     $("#gbox_rosterStudentsITIId").show();
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
							var selectedRoster;
							var selectedStudentIdentifier;
							var selectedStudentRosterId;
			            	var radio = $(e.target).closest('tr').find('input[type="radio"]');
			            	radio.attr('checked', 'checked');
			            	if(radio[0] != null && radio[0] != undefined){
			            		 
			            		selectedStudentRadio = radio[0].id;
				            	var rowData = jQuery("#rosterStudentsITIId").jqGrid('getRowData',selectedStudentRadio);
				            	var gridRow = $("#rosterStudentsITIId").jqGrid('getRowData',rowid);
				            	selectedFirstName = gridRow['student.legalFirstName'];
				            	selectedLastName = gridRow['student.legalLastName'];
				            	selectedEduLName = gridRow['educator.surName'];
				            	selectedEduFName = gridRow['educator.firstName'];
				            	selectedRoster = gridRow['roster.courseSectionName'];
				            	selectedStudentIdentifier = gridRow['student.stateStudentIdentifier'];
				            	selectedStudentRosterId = gridRow['roster.id'];
			            	}
			            	var studentInfo = new Object();
			            	selectedStudent = rowid;
			            	studentId = gridRow['student.id'];
			        		studentInfo.studentFirstName = selectedFirstName;
			        		studentInfo.studentLastName = selectedLastName;			        	
 			        		studentInfo.stateStudentIdentifier = selectedStudentIdentifier;
			        		studentInfo.selectedEduFName = selectedEduFName;
			        		studentInfo.selectedEduLName = selectedEduLName;
			        		studentInfo.selectedRoster = selectedRoster;
			        		studentInfo.studentEnrlRosterId =  gridRow['id'];
			        		studentInfo.studentId = studentId;
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

			        		window.sessionStorage.setItem('selectedStudentITI', JSON.stringify(studentInfo));
 			            	return true; //allow row selection 
			            },
						beforeRequest: function () {
							var schoolOrgIdIS = "";
							if(!isTeacher && $grid.getGridParam('datatype') == 'json'){
								if(!$('#searchFilterFormIS').valid()) {return false;}
									schoolOrgIdIS = $('#schoolOrgsIS').val();				
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
			                	$(this).setGridParam({postData: {schoolOrgId : schoolOrgIdIS}});

						},
						   loadComplete:function(data){
							   $('#gs_stateStudentIdentifier').attr('title','State ID Filter');
						    
							   $('td[id^="view_"]').click(function(){
								 	$(".EditTable.ViewTable tbody").prepend("<tr style='border-bottom: 2pt solid #e9e9e9;'><th style='font-weight:bold'>Column Name</th><th style='font-weight:bold'>Column Value</th></tr>");	
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
				$("#rosterStudentsITIId").jqGrid("viewGridRow", selectedStudent ,{width: 600});
		}
	
		//Custom formatter for FirstContact link. 
		function firstContactLinkFormatterIti(cellvalue, options, rowObject) {
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
					return '<a href="javascript:viewFirstContactDetailsIti(\'' + studentId + '\');">' + cellvalue + '</a>';
			}
			else if((typeof(viewFirstContactSurvey) !== 'undefined' && viewFirstContactSurvey))
			{
				if(cellvalue=='Not Applicable')
					return cellvalue;
				else
					return '<a href="javascript:viewFirstContactDetailsIti(\'' + studentId + '\');">' + cellvalue + '</a>';
			}
		}
		
		//Custom unformatter for FirstContact link.
		function firstContactLinkUnFormatterIti(cellvalue, options, rowObject) {
		    return cellvalue;
		}
		
		
		//Custom formatter for AccessProfile link. 
		function accessProfileLinkFormatter(cellvalue, options, rowObject) {
			return '<a href="javascript:accessProfileDetailsIS(\'' + rowObject.student.id  + '\',\''  + rowObject.student.stateStudentIdentifier +'\',\''+rowObject.student.studentAssessmentProgram +'\');">' + cellvalue + '</a>';
		}
		
		//Custom unformatter for AccessProfile link.
		function accessProfileLinkUnFormatter(cellvalue, options, rowObject) {
		    return;
		}
		
		//Custom formatter for AccessProfile link. 
		function ITILinkFormatter(cellvalue, options, rowObject) {
			if(rowObject.history != null){
				return '<a href="javascript:instructionalToolsSupportDetails(\'' + rowObject.student.id  + '\',\''  + rowObject.student.stateStudentIdentifier +'\');">' + "History" + '</a>';
			}else{
				return 'No History';
			}
		}

		//Custom unformatter for AccessProfile link.
		function ITILinkUnFormatter(cellvalue, options, rowObject) {
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

 	//Show popup screen on clicking the FirstContact link.
	function viewFirstContactDetailsIti(studentId) {
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
				$(this).html('');
 			    var $gridAuto = $("#rosterStudentsITIId");
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
	function accessProfileDetailsIS(studentId,stateStudentIdentifier,assessmentProgramCode) {
		var gridParam = window.sessionStorage.getItem('selectedStudentITI');  
		var studentInfo = $.parseJSON(gridParam);
		var dialogTile = studentInfo.studentFirstName + " " + studentInfo.studentLastName;
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
			    $(this).html('');
 			    var $gridAuto = $("#rosterStudentsITIId");
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
			htmlString = '<input type="radio" id=' + rowObject.student.id +'_'+rowObject.stateSubjectArea.id+'_'+rowObject.gradeCourse.abbreviatedName +' name="studentId" title="'+rowObject.student.legalFirstName+' '+rowObject.student.legalLastName+' '+rowObject.stateSubjectArea.name+' '+rowObject.gradeCourse.name+' " value="' + rowObject.student.id + '"/>';				
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
		var dialogTile = "View Instructional Plan History";
		$('#instructionalToolsSupportDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 800,
			height: 700,			
			title: dialogTile,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			beforeClose: function() {
				$(this).html('');
				var $gridAuto = $("#rosterStudentsITIId");
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json",
					url : 'getStudentInformationRecordsForDLM.htm?q=1',
					search: false,
				}).trigger("reloadGrid");
			},
			close: function(ev, ui) {			    
				$(this).dialog("destroy");
			}

		}).load(encodeURI('viewStudentInstructionalPlans.htm?studentId='+studentId+"&rosterId=" + studentInfo.rosterId + "&sourcePage=studentRosterPage")).dialog('open');	

	}
	function confirmAssignment(){
		$('#instructionalToolsSupportDiv').dialog("close");
		 $('#setupInstructionalToolsSupportTabs').tabs("option", 'active', 3);
			$('#setupInstructionalToolsSupportTabs li a').eq(0).removeClass('active');
	 		$('#setupInstructionalToolsSupportTabs li a').eq(3).addClass('active');	
	}
	
	function resetInstructionalToolsTab(){
		var isthisTeacher = false;
		<c:if test="${user.teacher}">
		isthisTeacher = true;
		</c:if>
		if(!isthisTeacher) {
			$('#searchFilterFormIS')[0].reset();
			$('#districtOrgsIS').trigger('change');

			var schoolLengthForDTCRole = $('#schoolOrgsIS > option').filter( function(){ return ($.trim(this.value).length != 0) }).length;
			if(schoolLengthForDTCRole == 1){
				valueDs = $('#schoolOrgsIS > option').filter( function(){ return ($.trim(this.value).length != 0) }).val();
				$('#schoolOrgsIS').val(valueDs);
				$('#schoolOrgsIS').trigger('change.select2');
			}
		
		}else{
			loadSetupITIStudentRostersIS();
		}
		$("#rosterStudentsITIId").jqGrid("clearGridData", true);
	}
	
	function populateStudentsSearchFiltersDataIS() {
		
		<c:if test="${!user.teacher}">		
			var optionText='';
			$('.messages').html('').hide();
			
			<c:if test="${user.accessLevel < 50 }">
				var districtOrgSelectIS = $('#districtOrgsIS');
				districtOrgSelectIS.find('option').filter(function(){return $(this).val() > 0}).remove().end();
				console.log(${user.currentOrganizationId});
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
								districtOrgSelectIS.append($('<option></option>').val(districtOrg.id).html(optionText));
							});
							
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrorsIS').html("No District Organizations Found for the current user").show();
						}
						$('#schoolOrgsIS, #districtOrgsIS').trigger('change.select2');
					}
				});
				
				
				$('#districtOrgsIS').change(function() {
					$('#schoolOrgsIS').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
					$('#schoolOrgsIS').trigger('change.select2');
					var districtOrgId = $('#districtOrgsIS').val();
					console.log(districtOrgId);
					if (districtOrgId != 0) {
						$.ajax({
					        url: 'getChildOrgsWithParentForFilter.htm',
					        data: {
					        	orgId : districtOrgId,
					        	orgType:'SCH'
					        	},
					        dataType: 'json',
					        type: "POST",
					        success: function(schoolOrgsIS) {
					        					        	
								$.each(schoolOrgsIS, function(i, schoolOrg) {
									$('#schoolOrgsIS').append($('<option></option>').attr("value", schoolOrg.id).text(schoolOrg.organizationName));
								});
								
								if (schoolOrgsIS !== undefined && schoolOrgsIS !== null && schoolOrgsIS.length == 1) {
									$('#schoolOrgsIS').val(schoolOrgsIS[0].id);
								}
								
								$('#schoolOrgsIS').trigger('change.select2');
								
					        }
						});
					}
				});	
			</c:if>
			
			<c:if test="${user.accessLevel >= 50 && !user.teacher}">				
				var schoolOrgSelect = $('#schoolOrgsIS');
				console.log(${user.currentOrganizationId});
				$.ajax({
					url: 'getChildOrgsWithParentForFilter.htm',
					dataType: 'json',
					data: {
						orgId : ${user.currentOrganizationId},
			        	orgType:'SCH'
			        	},
					type: "POST",
					success: function(schoolOrgsIS) {				
						if (schoolOrgsIS !== undefined && schoolOrgsIS !== null && schoolOrgsIS.length > 0) {
							$.each(schoolOrgsIS, function(i, schoolOrg) {
								optionText = schoolOrgsIS[i].organizationName;
								schoolOrgSelect.append($('<option></option>').val(schoolOrg.id).html(optionText));
							});
							
							if (schoolOrgsIS !== undefined && schoolOrgsIS !== null && schoolOrgsIS.length == 1) {
								$('#schoolOrgsIS').val(schoolOrgsIS[0].id);
							}
							
						} else {
							$('body, html').animate({scrollTop:0}, 'slow');
							$('#searchFilterErrorsIS').html("No School Organizations Found for the current user").show();
						}
						$('#schoolOrgsIS').trigger('change.select2');
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

</c:if>
