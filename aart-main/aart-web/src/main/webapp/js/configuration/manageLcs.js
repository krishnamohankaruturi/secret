function viewStudentsInitForStudentLCS(){
		viewStudentLoadOnce = true;
		$('#viewStudentsOrgFilterManageLCS').orgFilter({
			containerClass: '',
			requiredLevels: [20,30,40,50,60,70]
		});
		
		
		$('#viewStudentsOrgFilterFormManageLCS').validate({ignore: ""});
		buildViewStudentsByOrgGrid();
		
		$('#viewStudentsButton1').click(function(event) {
			if($('#viewStudentsOrgFilterFormManageLCS').valid()) {
				var $gridAuto = $("#viewStudentsByOrgTableManageLCS");
				$gridAuto[0].clearToolbar();
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getViewStudentInformationRecordsInLcs.htm?q=1', 
					search: false, 
					postData: { "filters": ""}
				}).trigger("reloadGrid",[{page:1}]);
			}
		});	 
		$('#viewStudentsOrgFilterManageLCS').orgFilter('option','requiredLevels',[50]);
		filteringOrganizationSet($('#viewStudentsOrgFilterFormManageLCS'));
		
}

function buildViewStudentsByOrgGrid() {
var $gridAuto = $("#viewStudentsByOrgTableManageLCS");
//Unload the grid before each request.
$("#viewStudentsByOrgTableManageLCS").jqGrid('clearGridData');
$("#viewStudentsByOrgTableManageLCS").jqGrid("GridUnload");

var gridWidthForVS = $gridAuto.parent().width();

if(gridWidthForVS < 800) {
	gridWidthForVS = 738;				
}
var cellWidthForVS = gridWidthForVS/5;
var cmForStudentRecords;
	cmForStudentRecords = [{name:'stateStudentIdentifier', formatter: viewStudentLinkFormatter, unformat: viewStudentLinkUnFormatter, width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
	                       {name:'legalFirstName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
                            {name:'legalMiddleName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
								formatter: escapeHtml},
                            {name:'legalLastName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
                            {name:'currentSchoolYears', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
                            {name:'residenceDistrictIdentifiers', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false,
                            	formatter: escapeHtml},
                            {name:'attendanceSchoolDisplayIdentifiers', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'attendanceSchoolNames', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'gradeCourseName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'gradeCourseId', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
                            {name:'rosterIds', width:cellWidthForVS, sorttype : 'int', search : true, hidden: true},
                           
                            
                          ];	
//JQGRID
$gridAuto.scb({
	mtype: "POST",
	datatype : "local",
	width: gridWidthForVS,
	colNames : ['State ID','First Name','Middle Name','Last Name','Current School Year', 
	             'District Id', 'School Id', 'School Name','Grade','Grade Id','Roster Id'
	           ],
  	colModel :cmForStudentRecords,
	rowNum : 10,
	rowList : [ 5,10, 20, 30, 40, 60, 90 ],
	pager : '#pviewStudentsByOrgTableManageLCS',
	sortname : 'stateStudentIdentifier',
    sortorder: 'asc',
	loadonce: false,
	viewable: false,
    beforeRequest: function() {
    	if(!$('#viewStudentsOrgFilterFormManageLCS').valid() && $(this).getGridParam('datatype') == 'json'){
    		return false;
    	} 
    	//Set the page param to lastpage before sending the request when 
		//the user entered current page number is greater than lastpage number.
		var currentPage = $(this).getGridParam('page');
        var lastPage = $(this).getGridParam('lastpage');

        if (lastPage!= 0 && currentPage > lastPage) {
        	$(this).setGridParam('page', lastPage);
            $(this).setGridParam({postData: {page : lastPage}});
        }
        
        if($('#viewStudentsOrgFilterManageLCS').orgFilter('value') != null)  {
        	var orgs = new Array();
        	orgs.push($('#viewStudentsOrgFilterManageLCS').orgFilter('value'));
        	selectedOrg = orgs;
        	$(this).setGridParam({postData: {'orgChildrenIds': function() {
					return orgs;
				}}});
        } else if($(this).getGridParam('datatype') == 'json') {
    		return false;
        }
    },localReader: {
        page: function (obj) {
            return obj.page !== undefined ? obj.page : "0";
        }
    },jsonReader: {
        page: function (obj) {
            return obj.page !== undefined ? obj.page : "0";
        },
        repeatitems:false,
    	root: function(obj) { 
    		return obj.rows;
    	} 
    },loadComplete: function() {	
          this.grid.hDiv.scrollLeft = this.grid.bDiv.scrollLeft;
          var tableid=$(this).attr('id'); 			           
          var objs= $( '#gbox_'+tableid).find('[id^=gs_]');			           
           $.each(objs, function(index, value) {         
            var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
                  $(value).attr('title',$(nm).text()+' filter');                          
                  });
    }
});
$gridAuto[0].clearToolbar();
$gridAuto.jqGrid('setGridParam',{
	postData: { "filters": ""}
}).trigger("reloadGrid",[{page:1}]);

setTimeout("aart.clearMessages()", 0);
}

function viewStudentLinkFormatter(cellvalue, options, rowObject) {
	// Save student info in local storage for reuse
	console.log(rowObject);
	var studentFirstName =  rowObject.legalFirstName; 
	var studentLastName = rowObject.legalLastName; 
	var studentMiddleName = rowObject.legalMiddleName; 
	var studentInfo = new Object();
	studentInfo.studentFirstName = studentFirstName;
	if(studentMiddleName)
		studentInfo.studentMiddleName = studentMiddleName;
	else
		studentInfo.studentMiddleName = "-";
	studentInfo.studentLastName = studentLastName;
	studentInfo.id = rowObject.id;
	studentInfo.stateStudentIdentifier = rowObject.stateStudentIdentifier;
	if(rowObject.gradeCourseName)
		studentInfo.gradeCourseName = rowObject.gradeCourseName;
	else
		studentInfo.gradeCourseName = "-";
	if(rowObject.dateOfBirthStr)
		studentInfo.dateOfBirth = rowObject.dateOfBirthStr;
	else
		studentInfo.dateOfBirth = "-";
	if(rowObject.genderString)
		studentInfo.gender = rowObject.genderString;
	else
		studentInfo.gender = "-";
	window.localStorage.setItem(rowObject.id, JSON.stringify(studentInfo));
	var editLink = false;
	if (typeof(editStudentPermission) !== 'undefined'){
		editLink = true;
	}
	return '<a href="javascript:openViewStudentPopupForManageLCS(\'' + rowObject.id  + '\',\''  + escapeHtml(rowObject.stateStudentIdentifier) + '\','  + editLink +');">' + escapeHtml(cellvalue) + '</a>';
}

function viewStudentLinkUnFormatter(cellvalue, options, rowObject) {
    return;
}
function openViewStudentPopupForManageLCS(studentId,stateStudentIdentifier, editLink) {
	gridParam = window.localStorage.getItem(studentId);  
	var studentInfo = $.parseJSON(gridParam);
	var dialogTitle = "Manage LCS Connections For - " + studentInfo.studentFirstName + " ";
	if (studentInfo.studentMiddleName != null && studentInfo.studentMiddleName.length > 0 && studentInfo.studentMiddleName != '-'){
		dialogTitle += studentInfo.studentMiddleName + " ";
	}
	dialogTitle +=  studentInfo.studentLastName;
	var action = 'view';
	$('#viewStudentDetailsDivManageLCS').attr('data-studentId',studentId);
	$('#viewStudentDetailsDivManageLCS').dialog({
		autoOpen: false,
		modal: true,
		width: 1087,
		height: 700,			
		title: dialogTitle,
		open: function(event, ui) { 
			$('#viewStudentDetailsDivManageLCS').prepend('<div type="hidden" data-studentid="'+$('#viewStudentDetailsDivManageLCS').attr('data-studentId')+'"></div>');
		    initTestManagement(studentId);
		}

	}).dialog('open');	

}
function initTestManagement(studentId){
			var $grid= $('#testSessionsTableIdManageLCS');
			var grid_width = $grid.parent().width();
			if(grid_width == 100 || grid_width == 0) {
				grid_width = 980;				
			}
			var cell_width =180;
			var gridParam;

			var cm=[ 
					{ name : 'testSessionRosterId', index : 'testSessionRosterId', width : cell_width,
							editable : true, editrules:{edithidden:true}, viewable: false,
							editoptions:{readonly:true,size:10},
							sorttype : 'int', search : true, hidden: true, hidedlg: true },

						{ name : 'testSessionId', index : 'testSessionId', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: false,
								editoptions:{readonly:true,size:10},
								sorttype : 'int', search : true, hidden: true, hidedlg: true },
								
								{ name: 'actions', width: 65, sortable: false, hidedlg: true, search: false, 
									formatter: function(cellValue, options, rowObject){
									return '<div class="jqactions breakelcsserver js-breakLCSInManageLCS"><span onclick="breakLCSInManageLCS('+false+','+false+','+studentId+','+rowObject[1]+','+null+')" class="ui-icon ui-icon-cancel" title="Breaking LCS"></span></div>';
								}
	                  	},
							
						{ name : 'testSessionName', index : 'testSessionName', width : cell_width,
									editable : true, editrules:{edithidden:true}, viewable: true,
									editoptions:{readonly:true,size:10},
									sortable : true, search : true, hidden : false, hidedlg : true,
									formatter: editTestSessionLinkFormatterForManage, unformat: editTestSessionLinkUnFormatter},

						{ name : 'printTicket', index : 'printTicket', width : cell_width-80,
											editable : true, editrules:{edithidden:true}, viewable: true,
											editoptions:{readonly:true,size:10}, sortable : false, search : false, hidden : true, hidedlg : true	   						
							,formatter: printTicketLinkFormatter, unformat: printTicketLinkUnFormatter },
						{ name : 'testInformation', index : 'testInformation', width : cell_width, editable : false, 
      						editrules:{edithidden:true}, sortable : false, search : false, hidden : true,viewable: false, 
							editoptions:{readonly:true,size:10}, formatter: testInfoLinkFormatter, unformat: testInfoLinkUnFormatter },
						{ name : 'testCollectionId', index : 'testCollectionId', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: false,
								editoptions:{readonly:true,size:10},
								sortable : true, search : true, hidden : true, hidedlg: true },

						{ name : 'assessmentName', index : 'assessmentName', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },
								
						{ name : 'testLetProgress', index : 'testLetProgress', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: false,
								editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

						{ name : 'tcGradeCourseName', index : 'tcGradeCourseName',
								width : cell_width, editable : true,
								editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },

					{ name : 'tcContentAreaName', index : 'tcContentAreaName', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: true,
									editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : false },

						{ name : 'programName', index : 'programName', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

						{ name : 'assessmentProgramName', index : 'assessmentProgramName', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },

						{ name : 'rosterId', index : 'rosterId', width : cell_width, editable : true,
								editrules:{edithidden:true}, viewable: false, editoptions:{readonly:true,size:10},
								summaryType : 'count', summaryTpl : '({0}) total',
								sortable : true, search : true,  hidden : true, hidedlg: true }, 
						
						{ name : 'courseSectionName', index : 'courseSectionName',
								width : cell_width, editable : true, editrules:{edithidden:true},
								viewable: true, editoptions:{readonly:true,size:10}, sortable : true,
								search : true, hidden : false, hidedlg : true },										

						{ name : 'attendanceSchoolIdentifier', index : 'attendanceSchoolIdentifier',
								width : cell_width, search : true,
								label: 'attendanceSchoolIdentifier', hidden : false },										
						
						{ name : 'attendanceSchoolName', index : 'attendanceSchoolName', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true}, sortable : true, search : true, hidden : false,
	                    	formatter: escapeHtml },

						{ name : 'educatorIdentifier', index : 'educatorIdentifier',
								width : cell_width, editable : true, editrules:{edithidden:true},
								viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true,
	                    	formatter: escapeHtml },
						
						{ name : 'educatorFirstName', index : 'educatorFirstName', width : cell_width,
								editable : true, editrules:{edithidden:true}, viewable: true,
								editoptions:{readonly:true,size:10}, search : true, hidden : true,
	                    	formatter: escapeHtml },
						
						{ name : 'educatorLastName', index : 'educatorLastName',
								width : cell_width, editable : true, editrules:{edithidden:true},
								viewable: true, editoptions:{readonly:true, size:10}, search : true, hidden : true,
	                    	formatter: escapeHtml },
								
			   			{ name : 'highStakesFlag', index : 'highStakesFlag', width : cell_width,
		  						editable : true, editrules:{edithidden:true}, viewable: false,
		  						editoptions:{readonly:true,size:10},
		  						sorttype : 'int', search : true, hidden: true, hidedlg: true },
		  						
			   			{ name : 'expiredFlag', index : 'expiredFlag', width : cell_width,
		  						editable : true, editrules:{edithidden:true}, viewable: false,
		  						editoptions:{readonly:true,size:10},
		  						sorttype : 'int', search : true, hidden: true, hidedlg: true },
		  				{ name : 'testSessionSource', index : 'testSessionSource', width : cell_width,
		  	  						viewable: false, sorttype : 'int', search : false, hidden: true, hidedlg: true } ,
		  	  						
						{ name : 'randomizationType', index : 'randomizationType',
			   					width : cell_width, editable : true, editrules:{edithidden:true},
			   					viewable: true, editoptions:{readonly:true,size:10}, sortable : true, search : true, hidden : true },
  	  					{ name : 'createdDate', index : 'createdDate', width : cell_width,
		  						editable : true, editrules:{edithidden:true}, viewable: true,
		  						editoptions:{readonly:true,size:10},
		  						sorttype : 'date', search : true, hidden: false},
			  			{ name : 'studentsTestStatus', index : 'studentsTestStatus', width : cell_width,
			  	  						viewable: true, sorttype : 'text', search : true, hidden: false}
					];			    
			
			//JQGRID   
			//getRosterStudentsByTeacher1
			$grid.scb({
				url : 'getTestsessionsForStudent.htm?q=1',
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
						'Test Information',
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
						'Randomization Type',
						'Created Date',
						'Status'
		   		           ],
		   		colModel : cm,
		   		rowNum : 10,//30,
				rowList : [ 5,10, 20, 30, 40, 60, 90 ],
				pager : '#ptestSessionsTableIdManageLCS',
		        sortname: 'testSessionId',
		       	sortorder: 'asc',
		       	columnChooser: false, 
		       	
				footerrow : true,
				userDataOnFooter : true,
				onSelectAll: function(id, status) {
					if(status) {
						//User checks the test session.
						for(var i=0; i<id.length; i++) {
							var gridRow = $('#testSessionsTableIdManageLCS').jqGrid('getRowData',id[i]);
							selectedTestMgtSessions.push(gridRow['testSessionId']);
							selectedTestMgtSessionRosterIds.push(id[i]);
							selectedTSAPMgtValues.push(gridRow['testSessionId'] + "-" + gridRow['assessmentProgramName']);
						}
					} else {
						//User unchecks the test session.
						for(var i=0; i<id.length; i++) {
							var gridRow = $('#testSessionsTableIdManageLCS').jqGrid('getRowData',id[i]);
							
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
				}
			});		

			$grid.jqGrid('navGrid', '#ptestSessionsTableIdManageLCS', {edit: false, add: false, del: false});

					//Clear the previous error messages
					setTimeout("aart.clearMessages()", 0);
				
			if(!isTeacher) {
				
		    	filteringOrganization($("#districtOrgs"));
		    	filteringOrganization($("#schoolOrgs"));
		    	
		    	populateSearchFiltersData();
		    	
		    	$('#searchFilterForm').validate({
		    		ignore: "",
		    		rules: {
		    			assessmentPrograms: {required: true},
		    			testingPrograms: {required: true},
		    				districtOrgs: {required: true},
		    			schoolOrgs: {required: true}
		    		}
		    	});
		    	loadTestMgmtTestSessions(true,studentId);
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
					
			});
			
		
	}
$(document).on('click','#js-breaklcswithstatestudentidentifier',function(ev){
	if(confirm("Please Confirm, This Action Can Not Be Undone")){
	ev.preventDefault();
	var ssiId=$('#state_student_identifier_id').val();
	$.ajax({
		url : 'breakLCSConnection.htm',
		data : {
			studentOnly : true,
			stateStudentIdentifier:ssiId
		},
		dataType : 'json',
		type : "POST"
	}).done(function(response) {
		if(response.errorMessage){
			$('#breaklcsmessage').text('Breaking LCS Connection failed : ' + response.errorMessage).css('color', 'red');
		}else{
			$('#breaklcsmessage').text('All LCS Entries for the Student with Student Identifier - ' +ssiId + ' have been broken').css('color', 'green');
		}
		$("#viewStudentsByOrgTableManageLCS").trigger("reloadGrid",[{page:1}]);
	});
}
})

$(document).on('click','#js-breaklcswithlcsid',function(ev){
	if(confirm("Please Confirm, This Action Can Not Be Undone")){
	ev.preventDefault();
	var lcsId=$('#lcs_id').val();
	$.ajax({
		url : 'breakLCSConnection.htm',
		data : {
			lcsOnly : true,
			lcsId:lcsId,
		},
		dataType : 'json',
		type : "POST"
	}).done(function(response) {
		if(response.errorMessage){
			$('#breaklcsmessage').text('Breaking LCS Connection failed : ' + response.errorMessage).css('color', 'red');
		}else{
			$('#breaklcsmessage').text('All LCS Entries for LCS with ID : ' +lcsId+' have been broken').css('color', 'green');
		}
		$("#viewStudentsByOrgTableManageLCS").trigger("reloadGrid",[{page:1}]);
	});
	}
})

function breakLCSInManageLCS(lcsOnly,studentOnly,studentId,testSessionId,lcsId){
	if(confirm("Please Confirm, This Action Can Not Be Undone")){
		$.ajax({
			url : 'breakLCSConnection.htm',
			data : {
				lcsOnly : lcsOnly,
				studentOnly : studentOnly,
				studentId:studentId,
				testSessionId:testSessionId,
				lcsId:lcsId
			},
			dataType : 'json',
			type : "POST"
		}).done(function(response) {
			$('#breaklcsgridmessage').show();
			if(response.errorMessage){
				$('#breaklcsgridmessage').text('Breaking LCS Connection failed : ' + response.errorMessage).css('color', 'red');
			}else{
				$('#breaklcsgridmessage').text('All LCS Entries have been broken').css('color', 'green');
			}
			$("#testSessionsTableIdManageLCS").trigger("reloadGrid",[{page:1}]);
			$("#viewStudentsByOrgTableManageLCS").trigger("reloadGrid",[{page:1}]);
		});	
	}
	
}

function populateSearchFiltersData() {
	var $grid = $("#testSessionsTableIdManageLCS");
	var pdata = $grid.getGridParam("postData");
	
	var apSelect = $('#assessmentPrograms'), optionText='';
	$('.messages').html('').hide();
	apSelect.find('option').filter(function(){return $(this).val() > 0}).remove().end();
	
	$.ajax({
		url: 'getAssessmentProgramsByUserSelected.htm',
		dataType: 'json',
		type: "POST"
	}).done(function(assessmentPrograms) {				
		if (assessmentPrograms !== undefined && assessmentPrograms !== null && assessmentPrograms.length > 0) {
			$.each(assessmentPrograms, function(i, assessmentProgram) {
				optionText = assessmentPrograms[i].programName;
				if(assessmentProgram.id == $('#hiddenCurrentAssessmentProgramId').val()){
					apSelect.append($('<option selected=\''+'selected'+'\'></option>').val(assessmentProgram.id).html(optionText));
				} else {
					apSelect.append($('<option></option>').val(assessmentProgram.id).html(optionText));
				}
			
			});
			apSelect.trigger('change');

			var filterSelectedValue = getFromSessionStorage("tmAssessmentProgramId");				
			if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
				apSelect.val(filterSelectedValue);
				apSelect.trigger('change');
			} else if (assessmentPrograms.length == 1) {
				apSelect.find('option:first').removeAttr('selected').next('option').attr('selected', 'selected');
				apSelect.trigger('change');
			}
			
		} else {
			$('body, html').animate({scrollTop:0}, 'slow');
			$('#searchFilterErrors').html("<fmt:message key='newreport.no.assessmentprogram'/>").show();
		}
		$('#grades, #testingPrograms, #contentAreas, #assessmentPrograms').trigger('change.select2');
	});
	
	
		var districtOrgSelect = $('#districtOrgs');

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
			        type: "POST" 
				}).done(function(schoolOrgs) {
		        	
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
		        });
			}
		});	
	
	
				
		var schoolOrgSelect = $('#schoolOrgs');

	$('#assessmentPrograms').on("change",function() {

		$('#testingPrograms').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#contentAreas').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#grades').find('option').filter(function(){return $(this).val() > 0;}).remove().end();
		$('#grades, #testingPrograms, #contentAreas').trigger('change.select2');
		var assessmentProgramId = $('#assessmentPrograms').val();

		if (assessmentProgramId != 0) {
			$.ajax({
		        url: 'getTestingPrograms.htm',
		        data: {
		        	assessmentProgramId: assessmentProgramId
		        	},
		        dataType: 'json',
		        type: "POST"
			}).done(function(testingPrograms) {
	        	
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
	        });
			
			$.ajax({
		        url: 'getContentAreasByAssessmentProgram.htm',
		        data: {
		        	assessmentProgramId: assessmentProgramId
		        	},
		        dataType: 'json',
		        type: "POST"
			}).done(function(contentAreas) {
	        	$('#contentAreas').html("");		
        		$('#contentAreas').append($('<option></option>').val('').html('Select'));			        	
				$.each(contentAreas, function(i, contentArea) {
					$('#contentAreas').append($('<option></option>').attr("value", contentArea.id).text(contentArea.name));
				});
				
				var filterSelectedValue = getFromSessionStorage("tmContentAreaId");				
				if(typeof filterSelectedValue  != 'undefined' && filterSelectedValue != null) {							
					$("#contentAreas").val(filterSelectedValue);
					$("#contentAreas").trigger('change');
				}
				
				if (contentAreas.length == 1) {
					$("#contentAreas option").removeAttr('selected').next('option').attr('selected', 'selected');
					$("#contentAreas").trigger('change');
				}
				$('#contentAreas').trigger('change.select2');
	        });					
		}
	});
	
}
function getFromSessionStorage(storageItemName) {
	var itemValue = window.sessionStorage.getItem(storageItemName);
	if(typeof itemValue != 'undefined' && itemValue != null) {
		return itemValue;
	}
	
	return null;
}
function loadTestMgmtTestSessions(backButtonFlag,studentId) {
	var assessmentProgramId = "";
	var testingProgramId = "";
	var contentAreaId = "";
	var gradeCourseId = "";
	var schoolOrgId = "";
	var showExpiredFlag = "";
	var completedFlag="";
	selectedTestMgtSessions = [];
	selectedTSAPMgtValues = [];
	selectedTestMgtSessionRosterIds = [];
	
	var criteriaValid = isTeacher; // teacher doesn't have search criteria, so it's automatically valid
	
		criteriaValid = assessmentProgramId != null && assessmentProgramId != '' &&
			testingProgramId != null && testingProgramId != '' &&
			schoolOrgId != null && schoolOrgId != '' &&
			contentAreaId != null && gradeCourseId != null;
	
		var $grid = $("#testSessionsTableIdManageLCS");
		//Clear the previous error messages
		setTimeout("aart.clearMessages()", 0);
			$grid.jqGrid('setGridParam',{
				datatype:"json",
				url : 'getTestsessionsForStudentLCS.htm', 
				search: false, 
				postData :  {
					filters: "",
					studentId:studentId
				}
			
			}).trigger("reloadGrid",[{page:1}]);	
		
}
	var isTeacher = false;
	function reloadTMData(){
		if(($('#assessmentPrograms').val()!='' && $('#testingPrograms').val()!='' 
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
			$("#testSessionsTableIdManageLCS").closest(".ui-jqgrid").find('.loading').hide();
		
		}
		
	}
	//Custom formatter for edit test session link. 
	function editTestSessionLinkFormatterForManage(cellvalue, options, rowObject) {
		var htmlString = "N.A";
		htmlString = '<p>' + escapeHtml(cellvalue) + '</p>';
	    return htmlString;	
	}

	//Custom unformatter for lastname link.
	function editTestSessionLinkUnFormatter(cellvalue, options, rowObject) {
	     return rowObject[3];
	}
	function printTicketLinkFormatter(cellvalue, options, rowObject) {
		var htmlString = '<div title="You do not have permission to view tickets."><img class="ui-state-disabled" alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png"></div>';
		
		htmlString = '<a class="pdfLink" href="getPDFTickets.htm?'+
				'assessmentProgramName=' + rowObject[12] + 
				'&testSessionId='+ rowObject[1] + 
				'&testCollectionId='+ rowObject[5] + 
				'">' + '<img alt="Test Session PDF" style="border:0px solid;" src="images/pdf.png">' + '</a>';
		
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
				lval = lval + '<a  target="_blank" href="' + mediaPath + iarr[0] + '">' + '<img alt="'+ iarr[1] +'" style="border:0px solid;" src="images/pdf.png">' + '</a>';
			else if(iarr[2] == 'UCB' || iarr[2] == 'UEB' || iarr[2] == 'EBAE')
				lval = lval + '<a  target="_blank" href="' + mediaPath + iarr[0] + '">' + '<img alt="'+ iarr[1] +'" style="border:0px solid;" src="images/test/braille_25_25.png">' + '</a>';
		
		});			
	} 
	return lval;	    
}


//Custom unformatter for lastname link.
function testInfoLinkUnFormatter(cellvalue, options, rowObject) {
    return;
}
