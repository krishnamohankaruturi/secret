function viewStudentsInitForCustomReports(){
		viewStudentLoadOnce = true;
		selectedStudentsArray=[];
		$('#viewStudentsOrgFilterCustomReports').orgFilter({
			containerClass: '',
			requiredLevels: [20,30,40,50,60,70]
		});
		
		$('#viewStudentsOrgFilterFormCustomReports').validate({ignore: ""});
		buildViewStudentsByOrgGrid();
		$('#viewStudentsButtonCustomReports').on("click",function(event) {
			if($('#viewStudentsOrgFilterFormCustomReports').valid()) {
				var $gridAuto = $("#viewStudentsByOrgTableCustomReports");
				$gridAuto[0].clearToolbar();
				$gridAuto.jqGrid('setGridParam',{
					datatype:"json", 
					url : 'getViewStudentInformationRecords.htm?q=1', 
					search: false, 
					postData: { "filters": ""}
				}).trigger("reloadGrid",[{page:1}]);
			}
		});	 
		$('#viewStudentsOrgFilterCustomReports').orgFilter('option','requiredLevels',[50]);
		
		filteringOrganizationSet($('#viewStudentsOrgFilterFormCustomReports'));
}

function buildViewStudentsByOrgGrid() {
var $gridAuto = $("#viewStudentsByOrgTableCustomReports");
//Unload the grid before each request.
$("#viewStudentsByOrgTableCustomReports").jqGrid('clearGridData');
$("#viewStudentsByOrgTableCustomReports").jqGrid("GridUnload");

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
	colNames : ['State ID','First Name', 'Middle Name', 'Last Name', 
	             'Current School Year', 'District Id', 'School Id', 'School Name', 'Grade','Grade Id','Roster Id',
	           ],
  	colModel :cmForStudentRecords,
	rowNum : 10,
	rowList : [ 5,10, 20, 30, 40, 60, 90 ],
	pager : '#pviewStudentsByOrgTableCustomReports',
	sortname : 'stateStudentIdentifier',
	multiselect : true,
    sortorder: 'asc',
	loadonce: false,
	viewable: false,
    beforeRequest: function() {
    	if(!$('#viewStudentsOrgFilterFormCustomReports').valid() && $(this).getGridParam('datatype') == 'json'){
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
        
        if($('#viewStudentsOrgFilterCustomReports').orgFilter('value') != null)  {
        	var orgs = new Array();
        	orgs.push($('#viewStudentsOrgFilterCustomReports').orgFilter('value'));
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
	                $('#jqg_'+tableid+'_'+ids[i]).attr('title', $(this).getCell(ids[i], 'legalFirstName') +' '+$(this).getCell(ids[i], 'legalLastName')+ ' Check Box');
	            }
	            $('input').removeAttr("aria-checked");
	            var objs= $( '#gbox_'+tableid).find('[id^=gs_]');
	             $('#cb_'+tableid).attr('title','User Grid All Check Box');
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

$(document).on("change", "#viewStudentsByOrgTableCustomReports tr[aria-selected]", function(ev){
	if($(this).attr('aria-selected') == "true"){
	if(_.indexOf(selectedStudentsArray, $(this).attr('id')) < 0){
		selectedStudentsArray.push($(this).attr('id'));
	}
	}else{
		if(_.indexOf(selectedStudentsArray, $(this).attr('id')) >= 0){
			selectedStudentsArray.splice(_.indexOf(selectedStudentsArray, $(this).attr('id')), 1);
		}
	}
	$('#viewStudentsGridCell #js-downloadLinkDiv').remove();
	if(selectedStudentsArray.length > 0 ){
		$("#studentReportDownloadLink").show();
		
	}else{
		$("#studentReportDownloadLink").hide();
	}
});
$(document).on("change", "#viewStudentsGridContainer table.ui-jqgrid-htable tr th input[type='checkbox']", function(ev){
	if($(this).attr('checked')){
		$.each($('#viewStudentsByOrgTableCustomReports').find('tr[aria-selected]'), function(i, val) {
            
            	if($(this).attr('aria-selected') == "true"){
            		if(_.indexOf(selectedStudentsArray, $(this).attr('id')) < 0){
            			selectedStudentsArray.push($(this).attr('id'));
            		}
            	}else{
            		if(_.indexOf(selectedStudentsArray, $(this).attr('id')) >= 0){
            			selectedStudentsArray.splice(_.indexOf(selectedStudentsArray, $(this).attr('id')), 1);
            		}
            	}
            
        });
	}else{
		selectedStudentsArray=[];
	}
	$('#viewStudentsGridCell #js-downloadLinkDiv').remove();
	if(selectedStudentsArray.length > 0 ){
		$("#studentReportDownloadLink").show();
		
	}else{
		$("#studentReportDownloadLink").hide();
	}
});

$(document).on("click", "#studentReportDownloadLink", function(ev){

	ev.preventDefault();
	var studentidString='';
	for(var i=0;i < selectedStudentsArray.length;i++){
		if(i==0){
			studentidString=selectedStudentsArray[i];
		}else{
			studentidString=studentidString+','+selectedStudentsArray[i]
		}
		
	}
	$('#miscMgmt-custReports #viewStudentsGridCell').append('<div id="js-downloadLinkDiv"><a href="downloadKAPCustomExtract.htm?studentIds='+studentidString+'">Click Here To Download Reports</a></div>');
	
})


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
	return '<a href="javascript:openViewStudentPopupForCustomReports(\'' + rowObject.id  + '\',\''  + escapeHtml(rowObject.stateStudentIdentifier) + '\','  + editLink +');">' + escapeHtml(cellvalue) + '</a>';
}

function viewStudentLinkUnFormatter(cellvalue, options, rowObject) {
    return;
}
function openViewStudentPopupForCustomReports(studentId,stateStudentIdentifier, editLink) {
	gridParam = window.localStorage.getItem(studentId);  
	var studentInfo = $.parseJSON(gridParam);
	//Decode for displaying
	//studentInfo.studentFirstName =  decodeURIComponent(studentInfo.studentFirstName);
	//studentInfo.studentLastName =  decodeURIComponent(studentInfo.studentLastName); 
	var dialogTitle = "View Student TestSession - " + studentInfo.studentFirstName + " ";
	if (studentInfo.studentMiddleName != null && studentInfo.studentMiddleName.length > 0 && studentInfo.studentMiddleName != '-'){
		dialogTitle += studentInfo.studentMiddleName + " ";
	}
	dialogTitle +=  studentInfo.studentLastName;
	var action = 'view';
	
	

}
function getFromSessionStorage(storageItemName) {
	var itemValue = window.sessionStorage.getItem(storageItemName);
	if(typeof itemValue != 'undefined' && itemValue != null) {
		return itemValue;
	}
	
	return null;
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
			$("#testSessionsTableIdCustomReports").closest(".ui-jqgrid").find('.loading').hide();
		
		}
		
	}
	//Custom formatter for edit test session link. 
	function editTestSessionLinkFormatter(cellvalue, options, rowObject) {
		var htmlString = "N.A";
		htmlString = '<a class="link" href="setupTestSession.htm?'+
				'testSessionId='+ rowObject[1] + '&testSessionName='+ escapeHtml(rowObject[3]) + '&highStakesFlag='+ rowObject[19] +
				'&testCollectionId='+ rowObject[5] + '&source='+ rowObject[21] + '&rosterId='+ rowObject[12] + '">' + escapeHtml(cellvalue) + '</a>';
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

