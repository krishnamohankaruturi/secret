<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/jsp/include.jsp"%>

<style>
		
.header {
	    float: left;
	    margin-right: 100px;
	    width:100%;
}

.leftmargin {
	margin-left:20px;
}

.rightMargin {
	float:right;
	margin-right:20px;
}


</style>
	
<div>
	<jsp:include page="../organizationFilter.jsp"/>
</div>
<br/> <br/>
					
<div class="pageContent">	
	<div class="header">
		<label id="title"><fmt:message key='label.students.studentrecords' /></label>		
	</div>
	<br/>
	<hr>
	<br/>	
	
	<!-- In below select component attribute "class="hidden" used to fix the IE8 browser issue. -->
	<select id="orgChildrenIds" multiple="multiple" class="hidden">
		<c:forEach var="orgChildrenId" items="${orgChildrenIds}">
		    <option value="${orgChildrenId}" selected="selected"></option>
		</c:forEach>
	</select>
		
	<!-- In below input component attribute "class="hidden" used to fix the IE8 browser issue. -->	
	<input id="loggedInUserId" value ="${user.id}" type="text" class="hidden" />	
	<input id="detailViewCaption" value ="View Student Detail" type="text" class="hidden" />	
	
	<div id="groupingDiv">
		<label for="chngroup" id="changeGroupLabel">
			Group By:
		</label>
		<select id="chngroup" onchange="changeGrouping()">
			<option value="clear">Remove Grouping</option>
			<option value="rosterId">Roster</option>
			<option value="studentId">Student</option>
			<option value="uniqueCommonIdentifier">Teacher</option> 
		</select>
	</div>
	
	<br />
	
	<div id="firstContactViewDiv" ></div>
	<div id="accessProfileDiv" ></div>
	
		
	<div id="noReport" class="none" style="width:100%"></div>
	<div class="table_wrap"><div class="kite-table">
	<table id="rosterStudentsTableId" style="width:100%"></table>
	
	<div id="prosterStudentsTableId"   style="width:100%"></div>
</div>
</div>
</div>


<script>

	//Global variable to capture the mouse position for 
	//"Loading...." message display.
	var mousePosition;
	var orgIds = [];
	
	
	$(function() {
		$('#groupingDiv').hide();
		$('.table_wrap').hide();
	});
	
	
	//On schoolid dropdown value change from stateAndDistrictCustomFilter.jsp
	$('#orgFilterSchoolId').on("change",function() {

		//Unload the grid before each request.
		$('#rosterStudentsTableId').jqGrid("GridUnload");
		
		if($('#orgFilterSchoolId').val() != 0) {
			orgIds = [];
			orgIds.push($('#orgFilterSchoolId').val());			
			$('#groupingDiv').show();
			$('.table_wrap').show();
			loadStudentRecords();
		} else {
			$('#groupingDiv').hide();
			$('.table_wrap').hide();
		}
	});
	
	
	//On districtId dropdown value change from stateAndDistrictCustomFilter.jsp
	$('#orgFilterDistrictId').on("change",function() {		
		//Unload the grid before each request.
		$('#rosterStudentsTableId').jqGrid("GridUnload");
		$('#groupingDiv').hide();
		$('.table_wrap').hide();
	});

	
	//On stateId dropdown value change from stateAndDistrictCustomFilter.jsp
	$('#orgFilterStateId').on("change",function() {
		//Unload the grid before each request.
		$('#rosterStudentsTableId').jqGrid("GridUnload");
		$('#groupingDiv').hide();
		$('.table_wrap').hide();
	});
	
	
	function changeGrouping() {
		var vl = $("#chngroup").val();
		if (vl) {
			if (vl == "clear") {
				jQuery("#rosterStudentsTableId").jqGrid(
						'groupingRemove', false);
			} else {
				jQuery("#rosterStudentsTableId").jqGrid(
						'groupingGroupBy', vl);
			}
		}
	}


	function loadStudentRecords() {
		<security:authorize  access="hasRole('PERM_ROSTERRECORD_VIEW') or hasRole('PERM_ROSTERRECORD_VIEWALL')" >              		
			<security:authorize access="hasRole('PERM_STUDENTRECORD_VIEW')" >
				//Need to set this in the session so that loggedInUserId would be used as a key to store the orgs.
				window.sessionStorage.setItem("loggedInUserId", $('#loggedInUserId').val() );
				var cmForStudentRecords = [ 
						{ name : 'enrollmentsRostersId', index : 'enrollmentsRostersId', label : 'enrollmentsRostersIdViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sorttype : 'int', search : false, hidden: true },
						
						{ name : 'stateStudentIdentifier', index : 'stateStudentIdentifier', label : 'stateStudentIdentifierViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : false, hidedlg : true },
						
						{ name : 'localStudentIdentifier', index : 'localStudentIdentifier', label : 'localStudentIdentifierViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'legalFirstName', index : 'legalFirstName', label : 'legalFirstNameViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : false },
						
						{ name : 'legalMiddleName', index : 'legalMiddleName', label : 'legalMiddleNameViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'legalLastName', index : 'legalLastName', label : 'legalLastNameViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : false, hidedlg : true },
						
						{ name : 'generation', index : 'generation', label : 'generationViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'gender', index : 'gender', label : 'genderViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'dateOfBirth', index : 'dateOfBirth', label : 'dateOfBirthViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, formatter: function (cellvalue, options, rowObject) {
							if(cellvalue != "Not Available") { var date = new Date(cellvalue);  return $.datepicker.formatDate("mm/dd/yy", date); } else { return cellvalue; } }, sortable : true, search : false, hidden : true },									

						{ name : 'gradeCourseName', index : 'gradeCourseName', label : 'gradeCourseNameViewStudents', width : cell_width, edittype:'textarea', editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : false, hidden : false, hidedlg : true }, 
						
						//{ name : 'stateSubjectAreaName', index : 'stateSubjectAreaName', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : false, hidden : false },									

						//{ name : 'stateCourseId', index : 'stateCourseId', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'courseSectionName', index : 'courseSectionName', label : 'courseSectionNameViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : false, hidedlg : true },										
						
						{ name : 'attendanceSchoolIdentifier', index : 'attendanceSchoolIdentifier', label : 'attendanceSchoolIdentifierViewStudents', width : cell_width, stype:'orgtree', searchoptions: { url: 'getUsersOrgs.htm' }, label: 'attendanceSchoolIdentifier', hidden : false, hidedlg : true },										
						
						{ name : 'attendanceSchoolName', index : 'attendanceSchoolName', label : 'attendanceSchoolNameViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : false, hidden : true },
						
						{ name : 'currentSchoolYear', index : 'currentSchoolYear', label : 'currentSchoolYearViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'firstLanguage', index : 'firstLanguage', label : 'firstLanguageViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'comprehensiveRace', index : 'comprehensiveRace', label : 'comprehensiveRaceViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'residenceDistrictIdentifier', index : 'residenceDistrictIdentifier', label : 'residenceDistrictIdentifierViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'primaryDisabilityCode', index : 'primaryDisabilityCode', label : 'primaryDisabilityCodeViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'uniqueCommonIdentifier', index : 'uniqueCommonIdentifier', label : 'uniqueCommonIdentifierViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : false },
						
						{ name : 'educatorFirstName', index : 'educatorFirstName', label : 'educatorFirstNameViewStudents',  width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, search : false, hidden : false },
						
						{ name : 'educatorLastName', index : 'educatorLastName', label : 'educatorLastNameViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true, size:10}, search : false, hidden : false },
						
						{ name : 'courseEnrollmentStatus', index : 'courseEnrollmentStatus', label : 'courseEnrollmentStatusViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },
						
						{ name : 'studentId', index : 'studentId', label : 'studentIdViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false,  hidden : true }, 
						
						{ name : 'enrollmentId', index : 'enrollmentId', label : 'enrollmentIdViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sorttype : 'int', search : false, hidden : true }, 
						
						{ name : 'rosterId', index : 'rosterId', label : 'rosterIdViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, summaryType : 'count', summaryTpl : '({0}) total', sortable : true, search : false,  hidden : true }, 
															
						{ name : 'teacherId', index : 'teacherId', label : 'teacherIdViewStudents', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : false, hidden : true },										
						
						{name : 'attendanceSchoolId', index : 'attendanceSchoolId', label : 'attendanceSchoolIdViewStudents', width : cell_width, sortable : true, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true,size:10}, formatter : 'int', searchtype:"number", search : false, hidden : true }, 									 

						//{ name : 'stateCourse', index : 'stateCourse', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : false, hidden : true }, 
						
					//	{ name : 'stateCourseName', index : 'stateCourseName', width : cell_width, editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : false, hidden : true }, 
						
						{ name : 'accessProfileStatus', index : 'accessProfileStatus', label : 'accessProfileStatusViewStudents', width : cell_width, edittype:'textarea', editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : false, hidden : false, hidedlg: true 
							,formatter: accessProfileLinkFormatter, unformat: accessProfileLinkUnFormatter },
						
						{ name : 'status', index : 'status', label : 'statusViewStudents', width : cell_width, edittype:'textarea', editable : true, editrules:{edithidden:true}, viewable: true, editoptions:{readonly:true}, sortable : true, search : false, hidden : false, hidedlg: true 
							,formatter: firstContactLinkFormatter, unformat: firstContactLinkUnFormatter }
					];
				//Capture the mouse position for Loading message display.
				$(document).mousemove(function(e) {
					mousePosition = e.pageY;
				}); 
				
				var grid_width = $('#rosterStudentsTableId').parent().width();
				var cell_width = 180;
				$grid= $('#rosterStudentsTableId');           		
				myDefaultSearch = 'cn',
		        
		        myColumnStateName = 'ColumnChooserAndLocalStorage7.colState7';
		        
		        var myColumnsState;
		        var isColState;
		 
		        firstLoad = true;

		    myColumnsState = restoreColumnState(cmForStudentRecords, myColumnStateName);
		    isColState = typeof (myColumnsState) !== 'undefined' && myColumnsState !== null;
				//JQGRID   
				jQuery("#rosterStudentsTableId").scb({
					url : 'getStudentInformationRecords.htm?q=1',
					postData :  {
						orgChildrenIds : function() {
							return orgIds;
						},
						filters : isColState ? myColumnsState.filters : null,
					},
					mtype: "POST",
					datatype : "json",
					width: grid_width,
					colNames : [
								'Enrollments Rosters ID','State ID', 'Local ID', 'First Name', 'Middle Name', 'Last Name', 'Generation', 'Gender', 'Date of Birth', 'Grade',
								'Roster', 'School ID', 'School Name', 'Current School Year', 'First Language', 'Comp Race', 
								'District ID', 'Disability', 'Educator State ID', 'Educator First Name', 'Educator Last Name', 'Enrollment Status',
								'Student ID', 'Enrollment ID', 'Roster ID', 'Educator ID', 'Attendance School ID', 'PNP Profile', 'First Contact'
					           ],
					colModel : cmForStudentRecords,
					rowNum : 10, //30,
					rowList : [ 5,10, 20, 30, 40, 60, 90 ],
					height : 'auto',
					pager : '#prosterStudentsTableId',
					page: isColState ? myColumnsState.page : 1,
				    search: isColState ? myColumnsState.search : false,
				 	sortname: isColState ? myColumnsState.sortname : 'studentId',
				    sortorder: isColState ? myColumnsState.sortorder : 'desc',
				 		    		sortable: {
				 			        	update: function() {
				 			        		saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
				 			        	}
				 			        },
					viewrecords : true,
					multiselect: true,
					caption : "",
					grouping : false,
					shrinkToFit : false,
					groupingView : {
						groupField : [ 'rosterId' ],
						groupColumnShow : [ false ],
						groupText : [ '<b>{0}</b>' ],
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
				    		//top: Math.round($gbox.height() - ($loadingDiv.outerHeight()*20))  + 'px' ,
				    		top:  (Math.min($gbox.height(), mousePosition) - 200) + 'px',
				        	left: (Math.min($gbox.width(), $(window).width()) - $loadingDiv.outerWidth())/2 + 'px'
				    	});
				    },
				    gridComplete: function() {
					    var recs = parseInt($("#rosterStudentsTableId").getGridParam("records"));
						if (isNaN(recs) || recs == 0) {
						     $("#gbox_rosterStudentsTableId").hide();
						     $("#noReport").html('No student records found.');
						     
						   	 //Set min height of 1px on no records found
						     $('.jqgfirstrow').css("height", "1px");
						 } else {
						     $("#gbox_rosterStudentsTableId").show();
						     $("#noReport").html('');
						 }
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
		                    refreshSerchingToolbar($this, myDefaultSearch);
		                    saveColumnState.call($this, this.p.remapColumns,myColumnStateName);
		                },
		                resizeStop: function () {
		                    saveColumnState.call($grid, $grid[0].p.remapColumns,myColumnStateName);
		                },
					ondblClickRow: function(rowid,iRow,iCol,e) {
						
						var studentId = $(this).getCell(rowid, 'studentId');
						var studentFirstName = $(this).getCell(rowid, 'legalFirstName');
						var studentLastName = $(this).getCell(rowid, 'legalLastName');
						var studentMiddleName = $(this).getCell(rowid, 'legalMiddleName');
						var gradeCourseName = $(this).getCell(rowid, 'gradeCourseName');
						var dateOfBirth= $(this).getCell(rowid,'dateOfBirth');
						var educatorFirstName = $(this).getCell(rowid, 'educatorFirstName');
						var educatorLastName = $(this).getCell(rowid, 'educatorLastName');
						
						
				       /*  openPNPSummaryViewDialog(studentId, studentFirstName, studentMiddleName,
				        		studentLastName, gradeCourseName); */
					},
					beforeRequest: function () {
						//Set the page param to lastpage before sending the request when 
						  //the user entered current page number is greater than lastpage number.
						var currentPage = $(this).getGridParam('page');
		                var lastPage = $(this).getGridParam('lastpage');
		                
		                 if (lastPage!= 0 && currentPage > lastPage) {
		                	 $(this).setGridParam('page', lastPage);
		                	$(this).setGridParam({postData: {page : lastPage}});
		                }
					}
				});
	
				jQuery("#rosterStudentsTableId").jqGrid('groupingRemove', false);
				 $.extend($.jgrid.search, {
					    multipleSearch: true,
					    multipleGroup: true,
					    recreateFilter: true,
					    closeOnEscape: true,
					    closeAfterSearch: true,
					    overlay: 0
					});
					$grid.jqGrid('navGrid', '#prosterStudentsTableId', {edit: false, add: false, del: false});  
				//Clear the previous error messages
				setTimeout("aart.clearMessages()", 0);
			</security:authorize>
		</security:authorize>
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

	//This method takes the studentId and opens the dialog with PNP summary data 
	//for which the user double clicks the row.

	function accessProfileDetails(studentId,stateStudentIdentifier) {
		gridParam = window.localStorage.getItem(studentId);  
		var studentInfo = $.parseJSON(gridParam);
		//Decode for displaying
		var studentFirstName =  decodeURIComponent(studentInfo.studentFirstName).replace("\\","");
		var studentLastName =  decodeURIComponent(studentInfo.studentLastName).replace("\\",""); 
		var dialogTile = studentFirstName + " ";
		dialogTile += studentLastName;
		

		$('#accessProfileDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 1087,
			height: 700,			
			title: dialogTile,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			close: function(ev, ui) {			    
			  //  window.location.href = 'studentRecords.htm';
			  		$('#rosterStudentsTableId').jqGrid("GridUnload");
					loadStudentRecords();
				$('#rosterStudentsTableId').trigger("reloadGrid");
			    $(this).html('');
			    $(this).dialog('close');
			}

		}).load(encodeURI('viewAccessProfile.htm?&studentId='+studentId+"&stateStudentIdentifier="+stateStudentIdentifier.toString()+"&studentInfo="+gridParam+"&previewAccessProfile="+"noPreview")).dialog('open');	

	}

	//Custom formatter for AccessProfile link. 
	function accessProfileLinkFormatter(cellvalue, options, rowObject) {
		// Save student info in local storage for reuse
		var studentFirstName =  rowObject[3].replace(/'/g, "\\'");
		var studentLastName = rowObject[5].replace(/'/g, "\\'"); 
		var studentMiddleName = rowObject[4].replace(/'/g, "\\'");
		//encode the values for ",&,? characters
		studentFirstName =  encodeURIComponent(studentFirstName).replace(/[!'()*]/g, escape);
		studentLastName = encodeURIComponent(studentLastName).replace(/[!'()*]/g, escape);
		studentMiddleName = encodeURIComponent(studentMiddleName).replace(/[!'()*]/g, escape);
		var studentInfo = new Object();
		studentInfo.studentFirstName = studentFirstName;
		studentInfo.studentMiddleName = studentMiddleName;
		studentInfo.studentLastName = studentLastName;
		studentInfo.stateStudentIdentifier = rowObject[1];
		studentInfo.gradeCourseName = rowObject[9];
		studentInfo.dateOfBirth = rowObject[8];
		studentInfo.educatorFirstName = rowObject[19];
		studentInfo.educatorLastName = rowObject[20];
		studentInfo.gender = rowObject[7];

		window.localStorage.setItem(rowObject[22], JSON.stringify(studentInfo));
		return '<a href="javascript:accessProfileDetails(\'' + rowObject[22]  + '\',\''  + rowObject[1] +'\');">' + cellvalue + '</a>';
	}
	
	//Custom unformatter for AccessProfile link.
	function accessProfileLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	//Custom formatter for FirstContact link. 
	function firstContactLinkFormatter(cellvalue, options, rowObject) {
		if(cellvalue=='Not Applicable')
			return cellvalue;
		else 
		return '<a href="javascript:viewFirstContactDetails(\'' + rowObject[22] + '\');">' + cellvalue + '</a>';
	}
	
	//Custom unformatter for FirstContact link.
	function firstContactLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	//Show popup screen on clicking the FirstContact link.
	function viewFirstContactDetails(studentId) {
		//alert(studentFirstName + " " + studentLastName);
		gridParam = window.localStorage.getItem(studentId);  
		var studentInfo = $.parseJSON(gridParam);
		var dialogTile = decodeURIComponent(studentInfo.studentFirstName).replace("\\","");
		/* if(studentMiddleName != "Not Available") {
			dialogTile += studentMiddleName + " ";
		} */
		dialogTile += decodeURIComponent(studentInfo.studentLastName).replace("\\","");
		$('#firstContactViewDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 1110,
			height: 700,			
			title: dialogTile,
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			},
			close: function(ev, ui) {
			// window.location.href = 'studentRecords.htm';
			$('#rosterStudentsTableId').jqGrid("GridUnload");
			loadStudentRecords();
				$('#rosterStudentsTableId').trigger("reloadGrid");
			    $(this).html('');
			    $(this).dialog('close');
			}
		}).load('firstContactResponseView.htm?&studentId='+studentId).dialog('open');	
	}

</script>
