/*$(document).ready(function(){
	viewStudentsInit();
});*/
var selectedOrg = new Array();
function viewStudentsInit(){
		viewStudentLoadOnce = true;
		$('#viewStudentsOrgFilter').orgFilter({
			containerClass: '',
			requiredLevels: [20,30,40,50,60,70]
		});
		if(globalUserLevel <= 50){
			$('#viewStudentsOrgFilter').orgFilter('option','requiredLevels',[50]);
		}
		
		$('#viewStudentsOrgFilterForm').validate({ignore: ""});
		
		buildViewStudentsByOrgGrid();
		
		$('#viewStudentsButton').on("click",function(event) {
			if($('#viewStudentsOrgFilterForm').valid()) {
				var $gridAuto = $("#viewStudentsByOrgTable");
				$gridAuto[0].clearToolbar();
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getViewStudentInformationRecords.htm?q=1', 
					search: false, 
					postData: { "filters": ""}
				}).trigger("reloadGrid",[{page:1}]);
			}
		});	 
		
		filteringOrganizationSet($('#viewStudentsOrgFilterForm'));
		
		$("#viewStudentDetailsDiv").dialog({
			autoOpen : false,
			modal : true
		});
		
}

function buildViewStudentsByOrgGrid() {
	var $gridAuto = $("#viewStudentsByOrgTable");
	//Unload the grid before each request.
	$("#viewStudentsByOrgTable").jqGrid('clearGridData');
	$("#viewStudentsByOrgTable").jqGrid("GridUnload");
	
	var gridWidthForVS = $gridAuto.parent().width();		
	if(gridWidthForVS < 740) {
		gridWidthForVS = 740;				
	}
	var cellWidthForVS = gridWidthForVS/5;
	
	var cmForStudentRecords;
	
	if((typeof(viewFirstContactSurvey) !== 'undefined' && !viewFirstContactSurvey) && (typeof(editFirstContactSurvey) !== 'undefined' && !editFirstContactSurvey))
	{
		cmForStudentRecords = [ {name:'stateStudentIdentifier', label:'viewStudent_stateStudentIdentifier', formatter: viewStudentLinkFormatter, unformat: viewStudentLinkUnFormatter, width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
	                            {name:'legalFirstName',label:'viewStudent_legalFirstName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
									formatter: escapeHtml},
	                            {name:'legalMiddleName',label:'viewStudent_legalMiddleName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
									formatter: escapeHtml},
	                            {name:'legalLastName',label:'viewStudent_legalLastName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
									formatter: escapeHtml},
	                            {name:'dateOfBirthStr',label:'viewStudent_dateOfBirthStr', width:cellWidthForVS, sorttype : 'date', search : true, hidden: true},
	                            {name:'generationCode',label:'viewStudent_generationCode', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
	                            	formatter: escapeHtml},
	                            {name:'genderString',label:'viewStudent_genderString', width:cellWidthForVS, search : true, hidden: true, stype : 'select', searchoptions: { value : ':All;1:Male;0:Female', sopt:['eq'] }},
	                            {name:'firstLanguage', notPresentFor: 'PLTW', label:'viewStudent_firstLanguage', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
	                            {name:'comprehensiveRace',label:'viewStudent_comprehensiveRace', width:cellWidthForVS, sorttype : 'text', search : true,hidden: true},
	                            {name:'primaryDisabilityCode',label:'viewStudent_primaryDisabilityCode', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
	                            {name:'currentSchoolYears',label:'viewStudent_currentSchoolYears', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
	                            {name:'localStudentIdentifiers',label:'viewStudent_localStudentIdentifiers', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false,
	                            	formatter: escapeHtml},
	                            {name:'residenceDistrictIdentifiers',label:'viewStudent_residenceDistrictIdentifiers', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false,
	                            	formatter: escapeHtml},
	                            {name:'attendanceSchoolDisplayIdentifiers',label:'viewStudent_attendanceSchoolDisplayIdentifiers', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
	                            {name:'attendanceSchoolNames',label:'viewStudent_attendanceSchoolNames', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
	                            {name:'gradeCourseName',label:'viewStudent_gradeCourseName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
	                            {name:'gradeCourseId',label:'viewStudent_gradeCourseId', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
	                            {name:'rosterIds',label:'viewStudent_rosterIds', width:cellWidthForVS, sorttype : 'int', search : true, hidden: true},
	                            {name:'rosterNames',label:'viewStudent_rosterNames', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},	                            
	                            {name:'programName',label:'viewStudent_programName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
	                            {name:'accessProfileStatus',label:'viewStudent_accessProfileStatus', formatter: accessProfileLinkFormatter, unformat: accessProfileLinkUnFormatter, width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;NO SETTINGS:NO SETTINGS;CUSTOM:CUSTOM', sopt:['eq'] }, hidden: false},
	                            {name:'status', notPresentFor: 'PLTW',label:'viewStudent_status', width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;Not Applicable:Not Applicable;Not Started:Not Started; Completed:Completed;Ready to Submit:Ready to Submit;In Progress:In Progress', sopt:['eq'] }, hidedlg: true, hidden:true},
	                            {name:'hispanicEthnicity',label:'viewStudent_hispanicEthnicity', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
	                            {name:'giftedStudent', notPresentFor: 'PLTW', label:'viewStudent_giftedStudent', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
	                            {name:'esolParticipationCode', notPresentFor: 'PLTW', label:'viewStudent_esolParticipationCode', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true}
	                          ];	
		
	}
	else
	{
		cmForStudentRecords = [ {name:'stateStudentIdentifier',label:'viewStudent_stateStudentIdentifier', formatter: viewStudentLinkFormatter, unformat: viewStudentLinkUnFormatter, width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
	                            {name:'legalFirstName',label:'viewStudent_legalFirstName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
									formatter: escapeHtml},
	                            {name:'legalMiddleName',label:'viewStudent_legalMiddleName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
									formatter: escapeHtml},
	                            {name:'legalLastName',label:'viewStudent_legalLastName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
									formatter: escapeHtml},
	                            {name:'dateOfBirthStr',label:'viewStudent_dateOfBirthStr', width:cellWidthForVS, sorttype : 'date', search : true, hidden: true},
	                            {name:'generationCode',label:'viewStudent_generationCode', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
	                            	formatter: escapeHtml},
	                            {name:'genderString',label:'viewStudent_genderString', width:cellWidthForVS, search : true, hidden: true, stype : 'select', searchoptions: { value : ':All;1:Male;0:Female', sopt:['eq'] }},
	                            {name:'firstLanguage',notPresentFor: 'PLTW',label:'viewStudent_firstLanguage', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
	                            {name:'comprehensiveRace',label:'viewStudent_comprehensiveRace', width:cellWidthForVS, sorttype : 'text', search : true,hidden: true},
	                            {name:'primaryDisabilityCode',label:'viewStudent_primaryDisabilityCode', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
	                            {name:'currentSchoolYears',label:'viewStudent_currentSchoolYears', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
	                            {name:'localStudentIdentifiers',label:'viewStudent_localStudentIdentifiers', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false,
	                            	formatter: escapeHtml},
	                            {name:'residenceDistrictIdentifiers',label:'viewStudent_residenceDistrictIdentifiers', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false,
	                            	formatter: escapeHtml},
	                            {name:'attendanceSchoolDisplayIdentifiers',label:'viewStudent_attendanceSchoolDisplayIdentifiers', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
	                            {name:'attendanceSchoolNames',label:'viewStudent_attendanceSchoolNames', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
	                            {name:'gradeCourseName',label:'viewStudent_gradeCourseName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
	                            {name:'gradeCourseId',label:'viewStudent_gradeCourseId', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
	                            {name:'rosterIds',label:'viewStudent_rosterIds', width:cellWidthForVS, sorttype : 'int', search : true, hidden: true},
	                            {name:'rosterNames',label:'viewStudent_rosterNames', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},	                            
	                            {name:'programName',label:'viewStudent_programName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
	                            {name:'accessProfileStatus',label:'viewStudent_accessProfileStatus', formatter: accessProfileLinkFormatter, unformat: accessProfileLinkUnFormatter, width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;NO SETTINGS:NO SETTINGS;CUSTOM:CUSTOM', sopt:['eq'] }, hidden: false},
	                            {name:'status', notPresentFor: 'PLTW',label:'viewStudent_status', formatter: firstContactLinkFormatter, unformat: firstContactLinkUnFormatter, width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;Not Applicable:Not Applicable;Not Started:Not Started; Completed:Completed;Ready to Submit:Ready to Submit;In Progress:In Progress', sopt:['eq'] }, hidden: false},
	                            {name:'hispanicEthnicity',label:'viewStudent_hispanicEthnicity', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
	                            {name:'giftedStudent',notPresentFor: 'PLTW',label:'viewStudent_giftedStudent', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
	                            {name:'esolParticipationCode',notPresentFor: 'PLTW',label:'viewStudent_esolParticipationCode', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true}
	                            
	                          ];	
	}
	
	var cnForStudentRecords = [ 'State ID', 'First Name', 'Middle Name', 'Last Name', 'Date of Birth', 'Generation', 'Gender', 'First Language',
        'Comprehensive Race', 'Disability', 'Current School Year', 'Local Id', 'District Id', 'School Id', 'School Name',
        'Grade','Grade Id','Roster Id', 'Roster Name', 'Assessment Program', 'PNP Profile', 'First Contact',
        'Hispanic Ethnicity', 'Gifted Student', 'ESOL Participation'
      ];
	
	var adjGridProps = ATSUtil.filterJQGridModelForAssessmentProgram($('#currentAssessmentProgram').val(), cmForStudentRecords, cnForStudentRecords);
	
	//JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVS,
		colModel: adjGridProps.colModel,
	  	colNames: adjGridProps.colNames,
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#pviewStudentsByOrgTable',
		// F-820 Grids default sort order
		sortname : 'legalLastName,legalFirstName,legalMiddleName',
        sortorder: 'asc',
		loadonce: false,
		viewable: false,
	    beforeRequest: function() {
	    	if(!$('#viewStudentsOrgFilterForm').valid() && $(this).getGridParam('datatype') == 'json'){
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
	        
	        if($('#viewStudentsOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#viewStudentsOrgFilter').orgFilter('value'));
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
	          var ids = $(this).jqGrid('getDataIDs');         
		         var tableid=$(this).attr('id'); 
	             
	             
		            for(var i=0;i<ids.length;i++)
		            {         
		                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'firstName') +' '+$(this).getCell(ids[i], 'lastName')+ ' Check Box');
		            }
		            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
		             $('#cb_'+tableid).attr('title','Student Grid All Check Box');
		             $.each(objs, function(index, value) {         
		              var nm=$('#jqgh_'+tableid+'_'+$(value).attr('name'));         
		                    $(value).attr('title',$(nm).text()+' filter');
		                    
		                   if ( $(value).is('select')) {
		                	   $(value).removeAttr("role");
		                	   $(value).css({"width": "100%"});
		                	   
		                    	$(value).select2({
		              	   		  placeholder:'Select',
		            	   		  multiple: false,
		            	      		  allowClear : true
		            	   		 });
		                    };
		                    });
	    }
	});
	$gridAuto[0].clearToolbar();
	$gridAuto.jqGrid('setGridParam',{
		postData: { "filters": ""}
	}).trigger("reloadGrid",[{page:1}]);
	if (typeof(editStudentPermission) !== 'undefined' && !editStudentPermission){
		$gridAuto.jqGrid('navButtonAdd', 'pviewStudentsByOrgTable', {
			id: 'editStudent',
			caption: "",
			title: "Edit Selected Student",
			buttonicon: "ui-icon-pencil",
			onClickButton: function(a) {
				var $this = $(this);
				var selectedRowId = $this.jqGrid('getGridParam', 'selrow');
				if (selectedRowId == null){
					$.jgrid.viewModal('#alertmod',
						{gbox: '#gbox_' + $.jgrid.jqID(this.p.id), jqm: true});
					$('#jqg_alrt_'+ $.jgrid.jqID(this.p.id)).focus();
					return;
				}
				var student = JSON.parse(window.localStorage.getItem(selectedRowId));
				var dialogTitle = 'Edit Student Record - ' + student.studentFirstName + ' ';
				if (student.studentMiddleName != null && student.studentMiddleName.length > 0 && student.studentMiddleName != '-'){
					dialogTitle += student.studentMiddleName + ' ';
				}
				dialogTitle += student.studentLastName;
				closeAndCallEditStudent(dialogTitle, selectedRowId);
			}
		});
	}
	setTimeout("aart.clearMessages()", 0);
}

function closeAndCallEditStudent(dialogTitle, studentId){
	$('#viewStudentDetailsDiv').dialog('close');
//	editStudentInit(studentId);
	callEditStudent(dialogTitle, studentId);	
}

function callEditStudent(dialogTitle, studentId){
	
	$('#editStudentDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1180,
		height: 650,
		title: escapeHtml(dialogTitle),
		create: function(event, ui){
		    var widget = $(this).dialog("widget");
		},
		beforeClose: function(){
		    $(this).html('');
		    var $gridAuto = $("#viewStudentsByOrgTable");
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url: 'getViewStudentInformationRecords.htm?q=1', 
				search: false, 
			}).trigger("reloadGrid");
			
			//find student
			 var $gridAuto = $("#findStudentsTableId");
				//$gridAuto[0].clearToolbar();
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'checkforstudentavailbility.htm?q=1',
					search: false, 
					postData: { "filters": "",
						        "requestFor":"view"}
			}).trigger("reloadGrid",[{page:1}]);
			
			
		}
	}).load('editStudent.htm', {studentId: studentId}).dialog('open');
}

function openViewStudentPopup(studentId,stateStudentIdentifier, editLink) {
	gridParam = window.localStorage.getItem(studentId);  
	var studentInfo = JSON.parse(gridParam);
	//Decode for displaying
	//studentInfo.studentFirstName =  decodeURIComponent(studentInfo.studentFirstName);
	//studentInfo.studentLastName =  decodeURIComponent(studentInfo.studentLastName); 
	var dialogTitle = "View Student Record - " + studentInfo.studentFirstName + " ";
	if (studentInfo.studentMiddleName != null && studentInfo.studentMiddleName.length > 0 && studentInfo.studentMiddleName != '-'){
		dialogTitle += studentInfo.studentMiddleName + " ";
	}
	dialogTitle +=  studentInfo.studentLastName;
	var action = 'view';
	

	$('#viewStudentDetailsDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1087,
		height: 700,			
		title: dialogTitle,
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
		},
		beforeClose: function() {
			//$(this).dialog('close');
		    $(this).html('');
		    var $gridAuto = $("#viewStudentsByOrgTable");
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getViewStudentInformationRecords.htm?q=1', 
				search: false, 
			}).trigger("reloadGrid");
		}

	}).load('viewStudentDetails.htm',{"studentId":studentId,
									  "editLink":editLink,
									  "action":action,
									  "selectedOrg[]":selectedOrg}).dialog('open');	

}

	//This method takes the studentId and opens the dialog with PNP summary data 
	//for which the user double clicks the row.

	function accessProfileDetails(studentId,stateStudentIdentifier, assessmentProgramCode) {
		gridParam = window.localStorage.getItem(studentId);  
		var studentInfo = JSON.parse(gridParam);
		//Decode for displaying
		//studentInfo.studentFirstName =  decodeURIComponent(studentInfo.studentFirstName);
		//studentInfo.studentLastName =  decodeURIComponent(studentInfo.studentLastName); 
		var dialogTile = studentInfo.studentFirstName + " ";
		dialogTile += studentInfo.studentLastName;
		var assmtPrgmCode = '';
		if(assessmentProgramCode !== undefined && assessmentProgramCode !== null){
			assmtPrgmCode = assessmentProgramCode;
		}
		var viewAccessProfileUrl ='viewAccessProfile.htm?&studentId='+studentId+"&assessmentProgramCode="+encodeURIComponent(assmtPrgmCode.toString())+"&stateStudentIdentifier="+encodeURIComponent(stateStudentIdentifier.toString())+"&studentInfo="+encodeURIComponent(gridParam)+"&previewAccessProfile="+"noPreview";
		$('#accessProfileDiv').dialog({
			autoOpen: false,
			modal: true,
			width: 1087,
			height: 700,			
			title: escapeHtml(dialogTile),
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
			},
			beforeClose: function() {
				var confirmText = 'You have not saved the PNP settings. Select Go Back to return to the PNP settings or select Exit to continue without saving.';
				var canExit = ATSUtil.dialogPromptToSave(this, confirmText);
				if(canExit){
					if(!$('#viewStudentDetailsDiv').dialog('isOpen')) {
						$("#viewStudentsByOrgTable").jqGrid('setGridParam',{
							datatype: 'json',
							url: 'getViewStudentInformationRecords.htm?q=1',
							search: false,
						}).trigger('reloadGrid');
					}
					$(this).html('');
					$('#viewStudentDetailsDiv').dialog('close');
					openViewStudentPopup(studentId,"", true);
					return true;
				}else{
					return false;
				} 
			}
		}).load(viewAccessProfileUrl, ATSUtil.dialogLockThisExitAfterChange).dialog('open');
	}
	//needed to maintain reference for call made via a link in row data
	var localRef_accessProfileDetails = accessProfileDetails;
	
	//Custom formatter for AccessProfile link. 
	function accessProfileLinkFormatter(cellvalue, options, rowObject) {
		// Save student info in local storage for reuse
		
		if(typeof(viewStudentPNPpermission) !== 'undefined' && viewStudentPNPpermission){
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
		return '<a href="javascript:localRef_accessProfileDetails(\'' + rowObject.id  + '\',\''  + rowObject.stateStudentIdentifier +'\',\''  + rowObject.programName +'\');">' + cellvalue + '</a>';
		}else{
			return cellvalue;
		}
	}
	
	//Custom unformatter for AccessProfile link.
	function accessProfileLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	function viewStudentLinkFormatter(cellvalue, options, rowObject) {
		// Save student info in local storage for reuse
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
		return '<a href="javascript:openViewStudentPopup(\'' + rowObject.id  + '\',\''  + escapeHtml(rowObject.stateStudentIdentifier) + '\','  + editLink +');">' + escapeHtml(cellvalue) + '</a>';
	}
	
	//Custom unformatter for AccessProfile link.
	function viewStudentLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	
	
	//Custom formatter for FirstContact link. 
	function firstContactLinkFormatter(cellvalue, options, rowObject) {
		// Save student info in local storage for reuse
		var studentFirstName =  rowObject.legalFirstName;
		var studentLastName = rowObject.legalLastName; 
		var studentMiddleName = rowObject.legalMiddleName;
		//encode the values for ",&,? characters
		//studentFirstName =  encodeURIComponent(studentFirstName);
		//studentLastName = encodeURIComponent(studentLastName); 
		//studentMiddleName = encodeURIComponent(studentMiddleName);
		var studentInfo = new Object();
		studentInfo.studentFirstName = studentFirstName;
		studentInfo.studentMiddleName = studentMiddleName;
		studentInfo.studentLastName = studentLastName;

		window.localStorage.setItem('FC_'+rowObject.id, JSON.stringify(studentInfo));
		if((typeof(editFirstContactSurvey) !== 'undefined' && editFirstContactSurvey))
		{
			if(cellvalue=='Not Applicable')
				return cellvalue;
			else
				return '<a href="javascript:localRef_viewFirstContactDetails(\'' + rowObject.id + '\');">' + cellvalue + '</a>';
		}
		else if((typeof(viewFirstContactSurvey) !== 'undefined' && viewFirstContactSurvey))
		{
			if(cellvalue=='Not Applicable' || cellvalue=='NOT STARTED')
				return cellvalue;
			else
				return '<a href="javascript:localRef_viewFirstContactDetails(\'' + rowObject.id + '\');">' + cellvalue + '</a>';
		}
	}
	
	//Custom unformatter for FirstContact link.
	function firstContactLinkUnFormatter(cellvalue, options, rowObject) {
	    return;
	}
	
	//Show popup screen on clicking the FirstContact link.
	function viewFirstContactDetails(studentId) {
		//alert(studentFirstName + " " + studentLastName);
		gridParam = window.localStorage.getItem('FC_'+studentId);  
		var studentInfo = JSON.parse(gridParam);
		var dialogTile = studentInfo.studentFirstName + " "+studentInfo.studentLastName;
		$('#firstContactViewDiv').dialog({
			autoOpen: false,
			resizable : false,
			modal: true,
			width: 1110,
			height: 700,			
			title: escapeHtml(dialogTile),
			create: function(event, ui) { 
			    var widget = $(this).dialog("widget");
			    $(".ui-dialog-titlebar-close span", widget).attr('title','Close');
			},
			beforeClose: function() {
				//$(this).dialog('close');
				saveFirstContactResponsesOnClose();
			    $(this).html('');
			    if($('#viewStudentDetailsDiv').dialog("isOpen")) {
			    	$('#viewStudentDetailsDiv').dialog("close");
			    }
			    var $gridAuto = $("#viewStudentsByOrgTable");
			    initializePageLabelArrays();
				lastPageTobeSaved = 0;
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getViewStudentInformationRecords.htm?q=1', 
					search: false, 
				}).trigger("reloadGrid");
			}
		}).load('firstContactResponseView.htm?&studentId='+studentId).dialog('open');	
	}
	var localRef_viewFirstContactDetails = viewFirstContactDetails;
	
	