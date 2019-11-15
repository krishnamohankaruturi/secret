var objectId = 0;
var selectedOrg = new Array();
$(function() {
	$("#UnrollviewStudentsContinues").css({'margin-left':'560px'});
	$('#UnrollviewStudentsContinue').attr('disabled','disabled').addClass('ui-state-disabled');
	$('#UnrollviewStudentsContinues').attr('disabled','disabled').addClass('ui-state-disabled');
	$('#UnRollMessage').css({'color':'red'});
	$("#Confirmdialog").dialog({
		  autoOpen: false,
		  modal: true
	});
	$('.full_main #UnrollviewStudentsContinue, .full_main #UnrollviewStudentsContinues').on('click',function(){
		var objects = $('#UnrollviewStudentsByOrgTable').jqGrid ('getRowData', objectId);
		var stateStudentIdentifier = objects.stateStudentIdentifier;
		var editLink = false;
		if (typeof(objects.editStudentPermission) !== 'undefined'){
			editLink = true;
		}			
		openViewUnrollStudentPopup(objectId, stateStudentIdentifier, editLink, objects);
	});	
	
});

var Selectedvalid = false;
var ChosedDate = false;	

function UnrollStudentsInit(){
		StudentExitLoadOnce = true;
		$('#UnrollviewStudentsOrgFilter').orgFilter({
			containerClass: '',
			requiredLevels: [20,30,40,50,60,70]
		});
		if(globalUserLevel <= 70){
			$('#UnrollviewStudentsOrgFilter').orgFilter('option','requiredLevels',[70]);
		}
		
		$('#UnrollviewStudentsOrgFilterForm').validate({ignore: ""});
		
		buildUnrollViewStudentsByOrgGrid();
		//debugger;
		$('#UnrollviewStudentsButton').on("click",function(event) {
			if($('#UnrollviewStudentsOrgFilterForm').valid()) {
				var $gridAuto = $("#UnrollviewStudentsByOrgTable");
				$gridAuto[0].clearToolbar();
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getViewStudentInformationRecords.htm?q=1', 
					search: false, 
					postData: { "filters": ""}
				}).trigger("reloadGrid",[{page:1}]);
			}
		});	 
		
		filteringOrganizationSet($('#UnrollviewStudentsOrgFilterForm'));
		
}

function buildUnrollViewStudentsByOrgGrid() {
	var $gridAuto = $("#UnrollviewStudentsByOrgTable");
	//Unload the grid before each request.
	$("#UnrollviewStudentsByOrgTable").jqGrid('clearGridData');
	$("#UnrollviewStudentsByOrgTable").jqGrid("GridUnload");
	
	var gridWidthForVS = $gridAuto.parent().width();		
	if(gridWidthForVS < 750) {
		gridWidthForVS = 750;				
	}
	var cellWidthForVS = gridWidthForVS/5;
	colModelExitStudent = [ {name:'stateStudentIdentifier',index:'stateStudentIdentifier',  width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
                            {name:'legalFirstName',index:'legalFirstName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
                            {name:'legalMiddleName',index:'legalMiddleName',  width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
								formatter: escapeHtml},
                            {name:'legalLastName',index:'legalLastName', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false,
								formatter: escapeHtml},
                            {name:'dateOfBirth',index:'dateOfBirth', width:cellWidthForVS, sorttype : 'date', search : true, hidden: true, 
									formatter: 'date', formatoptions: { srcformat: 'U/1000', newformat:'m/d/Y' }
                            },
                            {name:'generationCode',index:'generationCode',label:'generationCodeexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true,
                            	formatter: escapeHtml},
                            {name:'genderString',index:'genderString',label:'genderStringexitStudent', width:cellWidthForVS, search : true, hidden: true, stype : 'select', searchoptions: { value : ':All;1:Male;0:Female', sopt:['eq'] }},
                            {name:'firstLanguage',index:'firstLanguage',label:'firstLanguageexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'comprehensiveRace',index:'comprehensiveRace',label:'comprehensiveRaceexitStudent', width:cellWidthForVS, sorttype : 'text', search : true,hidden: true},
                            {name:'primaryDisabilityCode',index:'primaryDisabilityCode',label:'primaryDisabilityCodeexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'currentSchoolYears',index:'currentSchoolYears',label:'currentSchoolYearsexitStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false},
                            {name:'localStudentIdentifiers',index:'localStudentIdentifiers',label:'localStudentIdentifiersexitStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false,
                            	formatter: escapeHtml},
                            {name:'residenceDistrictIdentifiers',index:'residenceDistrictIdentifiers',label:'residenceDistrictIdentifiersexitStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: false,
                            	formatter: escapeHtml},
                            {name:'attendanceSchoolDisplayIdentifiers',index:'attendanceSchoolDisplayIdentifiers',label:'attendanceSchoolDisplayIdentifiersexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'attendanceSchoolNames',index:'attendanceSchoolNames',label:'attendanceSchoolNamesexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'gradeCourseName',index:'gradeCourseName',label:'gradeCourseNameexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: false},
                            {name:'gradeCourseId',index:'gradeCourseId',label:'gradeCourseIdexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'rosterIds',index:'rosterIds',label:'rosterIdsexitStudent', width:cellWidthForVS, sorttype : 'int', search : true, hidden: true},
                            {name:'programName',index:'programName',label:'programNameexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                            {name:'accessProfileStatus',index:'accessProfileStatus',label:'accessProfileStatusexitStudent',formatter: accessProfileLinkFormatterForExit, unformat: accessProfileLinkUnFormatterForExit, width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;NO SETTINGS:NO SETTINGS;CUSTOM:CUSTOM', sopt:['eq'] }, hidden: false},
                            {name:'status',index:'status',label:'statusexitStudent', formatter: firstContactLinkFormatterForExit, unformat: firstContactLinkUnFormatterForExit,width:cellWidthForVS, sorttype : 'text', search : true, stype : 'select', searchoptions: { value : ':All;Not Applicable:Not Applicable;Not Started:Not Started; Completed:Completed;Ready to Submit:Ready to Submit;In Progress:In Progress', sopt:['eq'] }, hidden: true},
                            {name:'hispanicEthnicity',index:'hispanicEthnicity',label:'hispanicEthnicityexitStudent', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
                            {name:'giftedStudent',index:'giftedStudent',label:'giftedStudentexitStudent', width:cellWidthForVS, sorttype : 'text', search : false, hidden: true},
                            {name:'esolParticipationCode',index:'esolParticipationCode',label:'esolParticipationCodeexitStudent', width:cellWidthForVS, sorttype : 'text', search : true, hidden: true},
                          ];
	
	
	
	//JQGRID
	$gridAuto.scb({
		mtype: "POST",
		datatype : "local",
		width: gridWidthForVS,
		colNames : [ 'State ID', 'First Name', 'Middle Name', 'Last Name', 'Date of Birth', 'Generation', 'Gender', 'First Language',
		             'Comprehensive Race', 'Disability', 'Current School Year', 'Local Id', 'District Id', 'School Id', 'School Name',
		             'Grade','Grade Id','Roster Id', 'Assessment Program', 'PNP Profile', 'First Contact',
		             'Hispanic Ethnicity', 'Gifted Student', 'ESOL Participation'
		           ],
	
	    colModel :colModelExitStudent,           
		rowNum : 10,
		rowList : [ 5,10, 20, 30, 40, 60, 90 ],
		pager : '#previewViewStudentsByOrgTable',
		// F-820 Grids default sort order
		sortname : 'legalLastName,legalFirstName,legalMiddleName',
        sortorder: 'asc',
		loadonce: false,
		viewable: false,
	    beforeRequest: function() {
	    	//Set the page param to lastpage before sending the request when 
			//the user entered current page number is greater than lastpage number.
			var currentPage = $(this).getGridParam('page');
	        var lastPage = $(this).getGridParam('lastpage');

	        if (lastPage!= 0 && currentPage > lastPage) {
	        	$(this).setGridParam('page', lastPage);
	            $(this).setGridParam({postData: {page : lastPage}});
	        }
	        
	        if($('#UnrollviewStudentsOrgFilter').orgFilter('value') != null)  {
	        	var orgs = new Array();
	        	orgs.push($('#UnrollviewStudentsOrgFilter').orgFilter('value'));
	        	selectedOrg = orgs;
	        	$(this).setGridParam({postData: {'orgChildrenIds': function() {
						return orgs;
					}}});
	        } else if($(this).getGridParam('datatype') == 'json') {
        		return false;
	        }
	        $('#UnrollviewStudentsContinue').attr('disabled','disabled').addClass('ui-state-disabled');
	        $('#UnrollviewStudentsContinues').attr('disabled','disabled').addClass('ui-state-disabled');
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
	    },
	    onSelectRow: function(object) {
	    	objectId = object;
			$('#UnrollviewStudentsContinue').removeAttr('disabled').removeClass('ui-state-disabled');
			$('#UnrollviewStudentsContinues').removeAttr('disabled').removeClass('ui-state-disabled');
	    },
	    loadComplete:function(data){   
	    	 var tableid=$(this).attr('id');   
	    	 var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
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

}

//needed to maintain reference for call made via a link in row data
var localRef_accessProfileDetailsForExit = accessProfileDetailsForExit;

//Custom formatter for AccessProfile link. 
function accessProfileLinkFormatterForExit(cellvalue, options, rowObject) {
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
	return '<a href="javascript:localRef_accessProfileDetailsForExit(\'' + rowObject.id  + '\',\''  + rowObject.stateStudentIdentifier +'\',\''  + rowObject.programName +'\');">' + cellvalue + '</a>';
	}else{
		return cellvalue;
	}
}

//Custom unformatter for AccessProfile link.
function accessProfileLinkUnFormatterForExit(cellvalue, options, rowObject) {
    return;
}


var localRef_viewFirstContactDetailsForExit = viewFirstContactDetailsForExit;
//Custom formatter for FirstContact link. 
function firstContactLinkFormatterForExit(cellvalue, options, rowObject) {
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
			return '<a href="javascript:localRef_viewFirstContactDetailsForExit(\'' + rowObject.id + '\');">' + cellvalue + '</a>';
	}
	else if((typeof(viewFirstContactSurvey) !== 'undefined' && viewFirstContactSurvey))
	{
		if(cellvalue=='Not Applicable')
			return cellvalue;
		else
			return '<a href="javascript:localRef_viewFirstContactDetailsForExit(\'' + rowObject.id + '\');">' + cellvalue + '</a>';
	}
}

//Custom unformatter for FirstContact link.
function firstContactLinkUnFormatterForExit(cellvalue, options, rowObject) {
    return;
}

//This method takes the studentId and opens the dialog with PNP summary data 
//for which the user double clicks the row.
function accessProfileDetailsForExit(studentId,stateStudentIdentifier,assessmentProgramCode) {
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
	var viewAccessProfileForExitUrl = 'viewAccessProfile.htm?&studentId='+studentId+"&stateStudentIdentifier="+encodeURIComponent(stateStudentIdentifier.toString())+"&assessmentProgramCode="+encodeURIComponent(assmtPrgmCode.toString())+"&studentInfo="+encodeURIComponent(gridParam)+"&previewAccessProfile="+"noPreview";
	$('#accessProfileDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1087,
		height: 700,			
		title: escapeHtml(dialogTile),
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		},
		beforeClose: function() {
	
		}

	}).load(viewAccessProfileForExitUrl,function(){
		$('#accessProfileDiv').dialog('open');
	});	

}

//Show popup screen on clicking the FirstContact link.
function viewFirstContactDetailsForExit(studentId) {
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
		},
		beforeClose: function() {
			//$(this).dialog('close');
			//console.log('1');
			//   debugger;
			saveFirstContactResponsesOnClose();
		    $(this).html('');
		    if($('#viewStudentDetailsDiv').dialog("isOpen")) {
		    	$('#viewStudentDetailsDiv').dialog("close");
		    }
		    var $gridAuto = $("#viewStudentsByOrgTable");
		 
			$gridAuto.jqGrid('setGridParam',{
				datatype:"json", 
				url : 'getViewStudentInformationRecords.htm?q=1', 
				search: false, 
			}).trigger("reloadGrid");
		}
	}).load('firstContactResponseView.htm?&studentId='+studentId).dialog('open');	
}

function openViewUnrollStudentPopup(studentId,stateStudentIdentifier, editLink, objects) {
	
	var dialogTitle = "Select Date and Reason, then Click Exit Student"; 
	var action = 'exit';
	$('#unRollviewStudentDetailsDiv').dialog({
		autoOpen: false,
		modal: true,
		width: 1087,
		height: 700,			
		title: dialogTitle,
		beforeClose: function() {

		},
		create: function(event, ui) { 
		    var widget = $(this).dialog("widget");
		    $('.btn_close').on('click',function(){
		    	$('#UnrollviewStudentsContinue').attr('disabled','disabled').addClass('ui-state-disabled');
				$('#UnrollviewStudentsContinues').attr('disabled','disabled').addClass('ui-state-disabled');
			});			
		},
		close: function () {
			$(this).empty();
   			$("#UnrollviewStudentsByOrgTable").trigger("reloadGrid");
   			setTimeout(function() {
   			 $('#UnRollMessage').html(" ");
			}, 3000);
		}
	}).load('viewStudentDetails.htm',{'studentId':studentId,
									  'editLink':editLink,
									  'stateStudentIdentifier':stateStudentIdentifier,
									  'action':action,
									  'selectedOrg[]':selectedOrg}).dialog('open');
}